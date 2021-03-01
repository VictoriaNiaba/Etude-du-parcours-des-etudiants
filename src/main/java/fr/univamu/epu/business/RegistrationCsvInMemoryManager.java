package fr.univamu.epu.business;

import java.io.File;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.Dao;
import fr.univamu.epu.model.registration.Registration;
import fr.univamu.epu.model.registration.RegistrationId;
import fr.univamu.epu.services.csvimport.RegistrationCsvParser;


@Service("registrationManager")
public class RegistrationCsvInMemoryManager implements RegistrationManager {
	
	@Autowired
	Dao dao;
	
	@Autowired
	RegistrationCsvParser rcp;
	
	public RegistrationCsvInMemoryManager() {
		super();
	}

	@PostConstruct
	public void init() {
		System.out.println("PC IA csv manager");
		if (dao.getAllRegistrations().isEmpty()){
			rcp.parse(new File("files/IA.csv"));
			for(Registration r : rcp.getRegistrations()) {
				System.out.println("reg  dao add:"+r);
				dao.addRegistration(r);
			}
		}
		
		System.out.println("init terminé:) registration manager");
		System.out.println(dao.getAllRegistrations());
	}
	
	@Override
	public Collection<Registration> findAll() {
		return dao.getAllRegistrations();
	}

	@Override
	public void update(Registration registration) {
		dao.updateRegistration(registration);
	}

	@Override
	public Registration find(RegistrationId registrationId) {
		return dao.findRegistration(registrationId);
	}

	@Override
	public void add(Registration registration) {
		dao.addRegistration(registration);
	}

	@Override
	public void delete(RegistrationId registrationId) {
		dao.removeRegistration(registrationId);
	}
	
}
