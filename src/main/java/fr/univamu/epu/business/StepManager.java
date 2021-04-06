package fr.univamu.epu.business;

import java.io.InputStream;
import java.util.Collection;

import fr.univamu.epu.model.step.Step;

public interface StepManager {

	Step add(Step step);
	
	void addAll(Collection<Step> steps);

	Step update(Step step);

	void remove(String code);
	
	Step find(String code);

	Collection<Step> findAll();
	
	void upload(InputStream inputStream);

}
