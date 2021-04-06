package fr.univamu.epu.dao;

import javax.persistence.TypedQuery;

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

	@Override
	public void deleteAllByYear(int year) {
		em.createQuery("DELETE FROM registration r WHERE r.id.year = :year")
				.setParameter("year", year)
				.executeUpdate();
	}

	@Override
	public long countByYear(int year) {
		TypedQuery<Long> query = em.createQuery(
				"SELECT COUNT(r) FROM registration r WHERE r.id.year = :year", Long.class)
				.setParameter("year", year);
		return query.getSingleResult();
	}
}
