package fr.univamu.epu.business;

import java.io.File;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.Dao;
import fr.univamu.epu.model.step.Step;
import fr.univamu.epu.services.csvimport.RegistrationCsvParser;
import fr.univamu.epu.services.csvimport.StepCsvParser;


@Service("stepManager")
public class StepCsvInMemoryManager implements StepManager {
	
	@Autowired
	Dao dao;
	
	@Autowired
	StepCsvParser scp;
	
	public StepCsvInMemoryManager() {
		super();
	}

	@PostConstruct
	public void init() {
		System.out.println("PC step csv manager");
		if (dao.getAllSteps().isEmpty()){

			System.out.println("on appel le parse");
			scp.parse(new File("files/etapes.csv"));

			System.out.println("parse fini");
			for(Step s : scp.getSteps())
				dao.addStep(s);
		}
		
		System.out.println("init terminé:) step csv manager");
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
