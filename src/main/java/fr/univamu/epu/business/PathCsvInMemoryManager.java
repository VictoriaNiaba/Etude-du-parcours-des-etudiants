package fr.univamu.epu.business;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.PathDao;
import fr.univamu.epu.model.path.MergedPath;
import fr.univamu.epu.model.path.Path;
import fr.univamu.epu.services.path.PathMerger;

@Service("pathManager")
public class PathCsvInMemoryManager implements PathManager {
	@Autowired
	private PathDao pathDao;

	@Autowired
	private PathMerger pm;

	@Override
	public Collection<Path> findAll() {
		return pathDao.findAll();
	}

	@Override
	public void addAll(Collection<Path> paths) {
		pathDao.addAll(paths);
	}

	@Override
	public Collection<MergedPath> find(String firststep, String laststep) {
		return pm.getMergedPath(firststep, laststep);
	}

}
