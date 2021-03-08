package fr.univamu.epu.business;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.Dao;
import fr.univamu.epu.errorhandler.NotFoundException;
import fr.univamu.epu.errorhandler.UploadException;
import fr.univamu.epu.model.formation.Formation;
import fr.univamu.epu.services.csvimport.CsvParser;

@Service("formationManager")
public class FormationCsvInMemoryManager implements FormationManager {
	
	@Autowired
	Dao<Formation> dao;

	@Autowired
	CsvParser<Formation> fcp;
	
	@PostConstruct
	public void init() throws FileNotFoundException {
		if (dao.findAll(Formation.class).isEmpty()) {
			upload(new FileInputStream("files/formations.csv"));
		}
	}
	
	@Override
	public Collection<Formation> findAll() {
		return dao.findAll(Formation.class);
	}

	@Override
	public Formation update(Formation formation) {
		return dao.update(formation);
	}

	@Override
	public Formation find(String code) {
		Formation formation = dao.find(Formation.class, code);
		if (formation == null) {
			throw new NotFoundException("La formation n'a pas été trouvée avec le code " + code);
		}
		return formation;
	}

	@Override
	public Formation add(Formation formation) {
		return dao.add(formation);
	}

	@Override
	public void addAll(Collection<Formation> formations) {
		dao.addAll(formations);
	}

	@Override
	public void remove(String code) {
		dao.remove(Formation.class, code);
	}

	@Override
	public void upload(InputStream inputStream) {
		Set<Formation> formations = fcp.parse(inputStream);
		try {
			addAll(formations);
		} catch (ConstraintViolationException | DataIntegrityViolationException e) {
			throw new UploadException("Une partie des formations fournies existent déjà en base de données");
		}	
	}

}
