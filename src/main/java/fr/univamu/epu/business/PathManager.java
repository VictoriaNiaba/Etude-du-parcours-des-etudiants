package fr.univamu.epu.business;

import java.util.Collection;

import fr.univamu.epu.model.path.MergedPath;
import fr.univamu.epu.model.path.Path;

public interface PathManager {

	void addAll(Collection<Path> paths);

	Collection<Path> findAll();

	Collection<MergedPath> find(String firststep, String laststep);

}
