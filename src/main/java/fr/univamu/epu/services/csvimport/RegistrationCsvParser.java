package fr.univamu.epu.services.csvimport;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univamu.epu.business.StepManager;
import fr.univamu.epu.dao.Dao;
import fr.univamu.epu.model.registration.Registration;
import fr.univamu.epu.model.registration.RegistrationId;
import fr.univamu.epu.model.step.Step;

@Service("registrationCsvParser")
public class RegistrationCsvParser implements CsvParser {
	
	@Autowired
	StepManager stepManager;

	private Set<Registration> registrations = new HashSet<>();	
	
	public RegistrationCsvParser() {
		super();
	}

	@Override
	public void parse(File file) {
		try (Scanner scanner = new Scanner(file);) {
		    while (scanner.hasNextLine()) {
		    	registrations.add(getRegistrationFromLine(scanner.nextLine()));
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private Registration getRegistrationFromLine(String line) {
	    try (Scanner rowScanner = new Scanner(line)) {
	        rowScanner.useDelimiter(";");
        	if(!rowScanner.hasNext()) return null;
        	String studentCode = rowScanner.next();
        	if(!rowScanner.hasNext()) return null;
        	String stepCode = rowScanner.next();
    		Step step = stepManager.find(stepCode);
    		if(step == null) {
    			System.out.println("CSV IA SCANNER: step not found: "+ stepCode);
    			//creer l'etape a la volée
    		}
        	if(!rowScanner.hasNext()) return null;
        	Integer year = Integer.parseInt(rowScanner.next());
        	Registration reg = new Registration( new RegistrationId(stepCode, studentCode, year), step);
        	System.out.println("la reg:"+reg);
        	return reg;
	    }
	}

	public Set<Registration> getRegistrations() {
		return registrations;
	}

}
