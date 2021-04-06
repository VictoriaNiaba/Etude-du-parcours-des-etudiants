package fr.univamu.epu.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.univamu.epu.model.path.Path;

@Repository
@Transactional
public class PathJpaDao extends GenericJpaDao<Path, Long> implements PathDao {

	PathJpaDao() {
		setClazz(Path.class);
	}

}
