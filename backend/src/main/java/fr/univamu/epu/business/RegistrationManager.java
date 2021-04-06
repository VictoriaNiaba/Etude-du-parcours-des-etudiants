package fr.univamu.epu.business;

import java.io.InputStream;
import java.util.Collection;

import fr.univamu.epu.model.registration.Registration;
import fr.univamu.epu.model.registration.RegistrationYearInfo;

public interface RegistrationManager {

	void addAll(Collection<Registration> registrations);
	
	Collection<RegistrationYearInfo> getRegistrationYearInfos();

	void upload(InputStream inputStream);

}
