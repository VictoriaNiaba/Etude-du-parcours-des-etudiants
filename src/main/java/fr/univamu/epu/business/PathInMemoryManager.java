package fr.univamu.epu.business;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.Dao;
import fr.univamu.epu.model.path.Path;

@Service("pathManager")
public class PathInMemoryManager implements PathManager {

	@Autowired
	Dao<Path> dao;

	@Override
	public Collection<Path> findAll() {
		return dao.findAll(Path.class);
	}

	@Override
	public void addAll(Collection<Path> paths) {
		dao.addAll(paths);
	}

}
