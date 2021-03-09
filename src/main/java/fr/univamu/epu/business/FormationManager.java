package fr.univamu.epu.business;

import java.io.InputStream;
import java.util.Collection;

import fr.univamu.epu.model.formation.Formation;

public interface FormationManager {

	Formation add(Formation formation);
	
	void addAll(Collection<Formation> formations);

	Formation update(Formation formation);

	void remove(String code);
	
	Formation find(String code);

	Collection<Formation> findAll();
	
	void upload(InputStream inputStream);

}
