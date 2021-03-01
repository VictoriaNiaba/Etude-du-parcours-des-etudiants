package fr.univamu.epu.business;

import java.util.Collection;

import fr.univamu.epu.model.registration.Registration;
import fr.univamu.epu.model.registration.RegistrationId;


public interface RegistrationManager {

	Collection<Registration> findAll();

	void update(Registration registration);

	Registration find(RegistrationId registrationId);

	void add(Registration registration);

	void delete(RegistrationId registrationId);
	
}
