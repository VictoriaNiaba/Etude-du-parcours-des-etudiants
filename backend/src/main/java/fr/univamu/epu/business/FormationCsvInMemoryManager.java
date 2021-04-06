package fr.univamu.epu.business;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.FormationDao;
import fr.univamu.epu.errorhandler.NotFoundException;
import fr.univamu.epu.model.formation.Formation;
import fr.univamu.epu.services.csvimport.CsvParser;
import fr.univamu.epu.services.formation.FormationCleaner;

@Service("formationManager")
public class FormationCsvInMemoryManager implements FormationManager {
	@Autowired
	private FormationDao formationDao;

	@Autowired
	private CsvParser<Formation> fcp;

	@Autowired
	private FormationCleaner fc;

	@PostConstruct
	public void init() throws FileNotFoundException {
		if (formationDao.findAll().isEmpty()) {
			upload(new FileInputStream("files/formations.csv"));
		}
	}

	@Override
	public Collection<Formation> findAll() {
		return formationDao.findAll();
	}

	@Override
	public Formation update(Formation formation) {
		return formationDao.update(formation);
	}

	@Override
	public Formation find(String code) {
		Formation formation = formationDao.find(code);
		if (formation == null) {
			throw new NotFoundException("La formation n'a pas été trouvée avec le code " + code);
		}
		return formation;
	}

	@Override
	public Formation add(Formation formation) {
		return formationDao.add(formation);
	}

	@Override
	public void addAll(Collection<Formation> formations) {
		formationDao.addAll(formations);
	}

	@Override
	public void remove(String code) {
		formationDao.remove(code);
	}

	@Override
	public void upload(InputStream inputStream) {
		Set<Formation> formations = fcp.parse(inputStream);
		formations = fc.clean(formations);
		System.out.println("uploading " + formations.size() + " formations");
		formationDao.saveAll(formations);
		System.out.println("uploaded formations");
	}

}
