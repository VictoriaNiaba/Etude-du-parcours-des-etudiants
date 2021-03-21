package fr.univamu.epu.dao;

import fr.univamu.epu.model.registration.Registration;
import fr.univamu.epu.model.registration.RegistrationId;

public interface RegistrationDao extends GenericDao<Registration, RegistrationId> {

	void deleteAllByYear(int year);

}
