package fr.univamu.epu.business;

import java.util.Collection;

import fr.univamu.epu.model.step.Step;


public interface StepManager {

	Collection<Step> findAll();

	void update(Step step);

	Step find(String stepCode);

	void add(Step step);

	void delete(String stepCode);
	
}
