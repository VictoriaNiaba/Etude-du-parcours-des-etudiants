package fr.univamu.epu.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.Dao;
import fr.univamu.epu.model.registration.Registration;
import fr.univamu.epu.model.registration.RegistrationId;
import fr.univamu.epu.services.csvimport.RegistrationCsvParser;
import fr.univamu.epu.services.path.PathBuilder;


@Service("registrationManager")
public class RegistrationCsvInMemoryManager implements RegistrationManager {
	
	@Autowired
	Dao dao;
	
	@Autowired
	PathBuilder pb;
	
	@Autowired
	RegistrationCsvParser rcp;
	
	public RegistrationCsvInMemoryManager() {
		super();
	}

	@PostConstruct
	public void init() throws FileNotFoundException {
		System.out.println("==init RegistrationCsvManager");
		if (dao.getAllRegistrations().isEmpty()){
			System.out.println("on appel le parse");
			Set<Registration> registrations = rcp.parse(new FileInputStream(new File("files/IA.csv")));
			System.out.println("parse fini on met tout dans le DAO");	
			dao.batchInsertRegistration(registrations);
		}
		System.out.println("reg daoé:" + dao.getAllRegistrations().size());
		System.out.println("generations des cheminements  RegManager");
		pb.buildPaths();
		
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
