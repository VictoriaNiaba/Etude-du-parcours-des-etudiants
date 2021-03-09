package fr.univamu.epu.business;

import java.io.InputStream;
import java.util.Collection;

import fr.univamu.epu.model.registration.Registration;

public interface RegistrationManager {

	void addAll(Collection<Registration> registrations);
	
	Collection<Registration> findAll();

	void upload(InputStream inputStream);

}
