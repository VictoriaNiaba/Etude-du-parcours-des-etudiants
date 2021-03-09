package fr.univamu.epu.services.csvimport;

import java.io.InputStream;
import java.util.Set;

public interface CsvParser<T> {	
	public Set<T> parse(InputStream inputStream);
}
