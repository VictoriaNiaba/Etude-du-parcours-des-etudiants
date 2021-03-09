package fr.univamu.epu.services.csvimport;

import java.io.InputStream;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import fr.univamu.epu.model.formation.Formation;

@Service("formationCsvParser")
public class FormationCsvParser implements CsvParser<Formation> {

	@Override
	public Set<Formation> parse(InputStream inputStream) {
		throw new NotImplementedException("Cette methode n'est pas encore implémentée!");
	}

}
