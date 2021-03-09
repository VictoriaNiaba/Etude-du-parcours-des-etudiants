package fr.univamu.epu.services.csvimport;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.springframework.stereotype.Service;

import fr.univamu.epu.model.registration.Registration;
import fr.univamu.epu.model.registration.RegistrationId;

@Service("registrationCsvParser")
public class RegistrationCsvParser implements CsvParser<Registration> {

	public RegistrationCsvParser() {
		super();
	}

	@Override
	public Set<Registration> parse(InputStream inputStream) {
		Set<Registration> registrations = new HashSet<>();
		
		try (Scanner scanner = new Scanner(inputStream)) {
			while (scanner.hasNextLine()) {
				registrations.add(getRegistrationFromLine(scanner.nextLine()));
			}
		}
			
		return registrations;
	}

	private Registration getRegistrationFromLine(String line) {
		try (Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter(";");
			if (!rowScanner.hasNext())
				return null;
			String studentCode = rowScanner.next();
			if (!rowScanner.hasNext())
				return null;
			String stepCode = rowScanner.next();
			/*
			 * Step step = stepManager.find(stepCode); if(step == null) {
			 * System.out.println("CSV IA SCANNER: step not found: "+ stepCode); //creer
			 * l'etape a la volée }
			 */
			if (!rowScanner.hasNext())
				return null;
			Integer year = Integer.parseInt(rowScanner.next());
			Registration reg = new Registration(new RegistrationId(stepCode, studentCode, year));
			return reg;
		}
	}

}
