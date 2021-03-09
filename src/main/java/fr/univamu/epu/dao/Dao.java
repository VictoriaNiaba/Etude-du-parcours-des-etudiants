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

import fr.univamu.epu.model.registration.Registration;
import fr.univamu.epu.model.registration.RegistrationId;
import fr.univamu.epu.model.step.Step;

@Service
@Repository
@Transactional
public class Dao {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	// DAO STEP
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
		Collection<Step> Steps = em.createQuery("Select s from Step s", Step.class).getResultList();
		return Steps;
	}
	
	// DAO REGISTRATION
	public Registration addRegistration(Registration r) {
		em.persist(r);
		// System.out.println("addRegistration witdh id=" + r.getRegistrationCode());
		return r;
	}
	
	public void batchInsertRegistration(Collection<Registration> regs){
		int count = 0;
		for(Registration r : regs) {
			if(count % 10000 == 0) {
				System.out.println(count/10000 + "/" + regs.size()/10000);
				em.flush();
				em.clear();
			}
			em.persist(r);
			++count;
		}	
	}
	
	

	public Registration findRegistration(RegistrationId RegistrationId) {
		Registration r = em.find(Registration.class, RegistrationId);
		// System.out.println("findRegistration with id=" + r.getRegistrationCode());
		return r;
	}
	public void updateRegistration(Registration r) {
		em.merge(r);
		// System.out.println("updateRegistration with id=" + r.getRegistrationCode());
	}

	public void removeRegistration(RegistrationId RegistrationId) { // à tester
		Registration r = findRegistration(RegistrationId);
		updateRegistration(r);
		em.remove(em.merge(r));
		// System.out.println("removeRegistration = " + r.getRegistrationName());
	}

	public Collection<Registration> getAllRegistrations() {
		Collection<Registration> Registrations = em.createQuery("Select r from Registration r", Registration.class).getResultList();
		return Registrations;
	}
	
	
}