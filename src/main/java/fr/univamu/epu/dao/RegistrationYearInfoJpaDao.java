package fr.univamu.epu.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.univamu.epu.model.registration.RegistrationYearInfo;

@Repository
@Transactional
public class RegistrationYearInfoJpaDao extends GenericJpaDao<RegistrationYearInfo, Integer>
		implements RegistrationYearInfoDao {

	RegistrationYearInfoJpaDao() {
		setClazz(RegistrationYearInfo.class);
	}

}
