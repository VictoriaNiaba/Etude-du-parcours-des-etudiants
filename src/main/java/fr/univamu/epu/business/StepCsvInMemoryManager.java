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

import fr.univamu.epu.dao.StepDao;
import fr.univamu.epu.errorhandler.NotFoundException;
import fr.univamu.epu.errorhandler.UploadException;
import fr.univamu.epu.model.step.Step;
import fr.univamu.epu.services.csvimport.CsvParser;
import fr.univamu.epu.services.step.StepFormationLinker;

@Service("stepManager")
@DependsOn("formationManager")
public class StepCsvInMemoryManager implements StepManager {
	@Autowired
	private StepDao stepDao;

	@Autowired
	private CsvParser<Step> scp;

	@Autowired
	private StepFormationLinker fsl;

	@PostConstruct
	public void init() throws FileNotFoundException {
		if (findAll().isEmpty()) {
			upload(new FileInputStream("files/etapes.csv"));
		}
	}

	@Override
	public Collection<Step> findAll() {
		return stepDao.findAll();
	}

	@Override
	public Step update(Step step) {
		return stepDao.update(step);
	}

	@Override
	public Step find(String code) {
		Step step = stepDao.find(code);

		if (step == null) {
			throw new NotFoundException("L'étape n'a pas été trouvée avec le code " + code);
		}
		return step;
	}

	@Override
	public Step add(Step step) {
		return stepDao.add(step);
	}

	@Override
	public void addAll(Collection<Step> steps) {
		stepDao.addAll(steps);
	}

	@Override
	public void remove(String code) {
		stepDao.remove(code);
	}

	@Override
	public void upload(InputStream inputStream) {
		Set<Step> steps = scp.parse(inputStream);

		System.out.println("uploading " + steps.size() + " steps");
		fsl.linkAndSaveAll(steps);
		System.out.println("uploaded steps");
	}

}
