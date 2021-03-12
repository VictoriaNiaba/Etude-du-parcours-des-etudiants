package fr.univamu.epu.business;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.Dao;
import fr.univamu.epu.errorhandler.UploadException;
import fr.univamu.epu.model.registration.Registration;
import fr.univamu.epu.services.csvimport.RegistrationCsvParser;
import fr.univamu.epu.services.path.PathBuilder;

@Service("registrationManager")
@DependsOn("stepManager")
public class RegistrationCsvInMemoryManager implements RegistrationManager {

	@Autowired
	Dao<Registration> dao;

	@Autowired
	RegistrationCsvParser rcp;

	@Autowired
	PathBuilder pb;
	
	@PostConstruct
	public void init() throws FileNotFoundException {
		if (dao.findAll(Registration.class).isEmpty()) {
			upload(new FileInputStream("files/IA.csv"));
		}
		
		pb.buildPaths();//depends on step
		//System.out.println(pm.getMergedPath(null, null));
	}

	@Override
	public void addAll(Collection<Registration> registrations) {
		dao.addAll(registrations);
	}

	@Override
	public Collection<Registration> findAll() {
		return dao.findAll(Registration.class);
	}

	@Override
	public void upload(InputStream inputStream) {
		Set<Registration> registrations = rcp.parse(inputStream);
		try {
			addAll(registrations);
		} catch (ConstraintViolationException | DataIntegrityViolationException e) {
			throw new UploadException(
					"Une partie des inscriptions administratives fournies existent déjà en base de données");
		}
	}

}
