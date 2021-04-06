package fr.univamu.epu.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.univamu.epu.model.step.Step;

@Repository
@Transactional
public class StepJpaDao extends GenericJpaDao<Step, String> implements StepDao {

	StepJpaDao() {
		setClazz(Step.class);
	}

}
