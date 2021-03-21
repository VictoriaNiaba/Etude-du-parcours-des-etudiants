package fr.univamu.epu.business;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.RegistrationDao;
import fr.univamu.epu.dao.RegistrationYearInfoDao;
import fr.univamu.epu.errorhandler.UploadException;
import fr.univamu.epu.model.registration.Registration;
import fr.univamu.epu.model.registration.RegistrationYearInfo;
import fr.univamu.epu.services.csvimport.RegistrationCsvParser;
import fr.univamu.epu.services.path.PathBuilder;

@Service("registrationManager")
@DependsOn("stepManager")
public class RegistrationCsvInMemoryManager implements RegistrationManager {
	@Autowired
	private RegistrationYearInfoDao regYearInfoDao;
	@Autowired
	private RegistrationDao registrationDao;

	@Autowired
	private RegistrationCsvParser rcp;

	@Autowired
	private PathBuilder pb;

	@PostConstruct
	public void init() throws FileNotFoundException {
		if (registrationDao.findAll().isEmpty()) {
			upload(new FileInputStream("files/IA.csv"));
		}
	}

	@Override
	public void addAll(Collection<Registration> registrations) {
		registrationDao.addAll(registrations);
	}

	@Override
	public Collection<RegistrationYearInfo> getRegistrationYearInfos() {
		return regYearInfoDao.findAll();
	}

	@Override
	public void upload(InputStream inputStream) {
		Set<Registration> registrations = rcp.parse(inputStream);
		// logique d'upload: suppression des années importées
		System.out.println("uploading " + registrations.size() + " registrations");
		List<Integer> yearsUploaded = new ArrayList<Integer>();

		List<RegistrationYearInfo> infos = new ArrayList<RegistrationYearInfo>();

		for (Registration reg : registrations) {
			if (!yearsUploaded.contains(reg.getYear())) {
				yearsUploaded.add(reg.getYear());
				infos.add(new RegistrationYearInfo(reg.getYear(), 1, new Date()));
			} else {
				for (RegistrationYearInfo info : infos) {
					if (info.getYear().equals(reg.getYear())) {
						infos.remove(info);
						info.setRegistrationCount(info.getRegistrationCount() + 1);
						info.setTimeStamp(new Date());
						infos.add(info);
						break;
					}
				}
			}
		}

		for (int year : yearsUploaded) {
			registrationDao.deleteAllByYear(year);
			regYearInfoDao.deleteAllByYear(year);
		}

		addAll(registrations);
		regYearInfoDao.addAll(infos);
		System.out.println("uploaded regs");

		// gen paths
		pb.buildPaths();// depends on step
	}
}
