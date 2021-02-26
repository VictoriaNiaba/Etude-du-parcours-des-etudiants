package fr.univamu.epu.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.univamu.epu.model.step.Step;

@Service
@Repository
@Transactional
public class Dao {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	// DAO Step
	public Step addStep(Step s) {
		em.persist(s);
		// System.out.println("addStep witdh id=" + s.getStepCode());
		return s;
	}

	public Step findStep(String stepCode) {
		Step s = em.find(Step.class, stepCode);
		// System.out.println("findStep with id=" + s.getStepCode());
		return s;
	}

	/*
	public Step findStep(String name) {
		// System.out.println("find Step " + name);
		//
		Query query = em.createQuery("SELECT s.id FROM Step AS s WHERE StepName=?1").setParameter(1, name);
		if (query.getResultList().isEmpty())
			return null;
		// System.out.println(query.getResultList().get(0));
		long id = (long) query.getResultList().get(0);
		return findStep1(id);
	}*/

	public void updateStep(Step s) {
		em.merge(s);
		// System.out.println("updateStep with id=" + s.getStepCode());
	}

	public void removeStep(String stepCode) { // à tester
		Step s = findStep(stepCode);
		updateStep(s);
		em.remove(em.merge(s));
		// System.out.println("removeStep = " + s.getStepName());
	}

	public Collection<Step> getAllSteps() {
		Collection<Step> StepsLazy = em.createQuery("Select s from Step s", Step.class).getResultList();
		//TODO: gros doutes sur la nécéssité du lazy
		Collection<Step> Steps = new ArrayList<Step>();
		for (Step s : StepsLazy) {
			Steps.add(this.findStep(s.getStepCode()));
		}
		return Steps;
	}
	

}