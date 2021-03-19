package fr.univamu.epu.business;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.Dao;
import fr.univamu.epu.model.path.MergedPath;
import fr.univamu.epu.model.path.Path;
import fr.univamu.epu.services.path.PathMerger;

@Service("pathManager")
public class PathCsvInMemoryManager implements PathManager {

	Dao<Path, Long> dao;

	@Autowired
	public void setDao(Dao<Path, Long> dao) {
		this.dao = dao;
		dao.setClazz(Path.class);
	}

	@Autowired
	PathMerger pm;

	@Override
	public Collection<Path> findAll() {
		return dao.findAll();
	}

	@Override
	public void addAll(Collection<Path> paths) {
		dao.addAll(paths);
	}

	@Override
	public Collection<MergedPath> find(String firststep, String laststep) {
		return pm.getMergedPath(firststep, laststep);
	}

}
