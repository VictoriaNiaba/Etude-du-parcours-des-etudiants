package fr.univamu.epu.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.Dao;
import fr.univamu.epu.model.step.Step;
import fr.univamu.epu.services.csvimport.CsvParser;


@Service("stepManager")
public class StepCsvInMemoryManager implements StepManager {
	
	@Autowired
	Dao dao;
	
	@Autowired
	CsvParser<Step> scp;
	
	public StepCsvInMemoryManager() {
		super();
	}

	@PostConstruct
	public void init() throws FileNotFoundException {
		System.out.println("==init StepCsvManager");
		if (dao.getAllSteps().isEmpty()){
			System.out.println("on appel le parse");
			Set<Step> steps = scp.parse(new FileInputStream(new File("files/etapes.csv")));
			System.out.println("parse fini on met tout dans le DAO");
			for(Step step : steps) {
				dao.addStep(step);
			}	
		}
		System.out.println("steps dans le dao! size:" + dao.getAllSteps().size());
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
