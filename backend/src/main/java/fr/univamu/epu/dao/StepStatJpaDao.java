package fr.univamu.epu.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.univamu.epu.model.step.StepStat;

@Repository
@Transactional
public class StepStatJpaDao extends GenericJpaDao<StepStat, Long> implements StepStatDao {

	StepStatJpaDao() {
		setClazz(StepStat.class);
	}

}
