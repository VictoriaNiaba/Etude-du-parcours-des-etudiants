package fr.univamu.epu.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.univamu.epu.model.formation.Formation;

@Repository
@Transactional
public class FormationJpaDao extends GenericJpaDao<Formation, String> implements FormationDao {

	FormationJpaDao() {
		setClazz(Formation.class);
	}

}
