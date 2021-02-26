package fr.univamu.epu.business;

import java.io.File;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.Dao;
import fr.univamu.epu.model.step.Step;
import fr.univamu.epu.services.csvimport.StepCsvParser;


@Service("stepManager")
public class CsvInMemoryStepManager implements StepManager {
	
	@Autowired
	Dao dao;
	
	public CsvInMemoryStepManager() {
		super();
	}

	@PostConstruct
	public void init() {
		if (dao.getAllSteps().isEmpty()){
			StepCsvParser scp = new StepCsvParser();
			scp.parse(new File("files/etapes.csv"));
			for(Step s : scp.getSteps())
				dao.addStep(s);
		}
		
		System.out.println("init terminé:)");
		System.out.println(dao.getAllSteps());
	}
	
	@Override
	public Collection<Step> findAll() {
		return dao.getAllSteps();
	}

	@Override
	public void update(Step step) {
		dao.updateStep(step);
	}

	@Override
	public Step find(String stepCode) {
		return dao.findStep(stepCode);
	}

	@Override
	public void add(Step step) {
		dao.addStep(step);
	}

	@Override
	public void delete(String stepCode) {
		dao.removeStep(stepCode);
	}
	
}
