package fr.univamu.epu.business;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.Dao;
import fr.univamu.epu.errorhandler.UploadException;
import fr.univamu.epu.errorhandler.NotFoundException;
import fr.univamu.epu.model.step.Step;
import fr.univamu.epu.services.csvimport.CsvParser;
import fr.univamu.epu.services.step.StepFormationLinker;

@Service("stepManager")
@DependsOn("formationManager")
public class StepCsvInMemoryManager implements StepManager {

	@Autowired
	Dao<Step> dao;

	@Autowired
	CsvParser<Step> scp;
	
	@Autowired
	StepFormationLinker fsl;

	@PostConstruct
	public void init() throws FileNotFoundException {
		if (findAll().isEmpty()) {
			upload(new FileInputStream("files/etapes.csv"));
		}
	}

	@Override
	public Collection<Step> findAll() {
		return dao.findAll(Step.class);
	}

	@Override
	public Step update(Step step) {
		return dao.update(step);
	}

	@Override
	public Step find(String code) {
		Step step = dao.find(Step.class, code);
		if(step == null) {
			throw new NotFoundException("L'étape n'a pas été trouvée avec le code " + code);
		}
		return step;
	}

	@Override
	public Step add(Step step) {
		return dao.add(step);
	}

	@Override
	public void addAll(Collection<Step> steps) {
		dao.addAll(steps);
	}

	@Override
	public void remove(String code) {
		dao.remove(Step.class, code);
	}

	@Override
	public void upload(InputStream inputStream) {
		Set<Step> steps = scp.parse(inputStream);
		try {
			fsl.linkAndAddAll(steps);
		} catch (ConstraintViolationException | DataIntegrityViolationException e) {
			throw new UploadException("Une partie des étapes fournies existent déjà en base de données");
		}		
	}

}
