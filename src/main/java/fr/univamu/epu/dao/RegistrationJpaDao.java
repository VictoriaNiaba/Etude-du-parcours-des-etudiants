package fr.univamu.epu.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.univamu.epu.model.registration.Registration;
import fr.univamu.epu.model.registration.RegistrationId;

@Repository
@Transactional
public class RegistrationJpaDao extends GenericJpaDao<Registration, RegistrationId>
		implements RegistrationDao {

	RegistrationJpaDao() {
		setClazz(Registration.class);
	}

}
