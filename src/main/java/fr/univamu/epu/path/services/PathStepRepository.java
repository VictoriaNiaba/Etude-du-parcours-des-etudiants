package fr.univamu.epu.path.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.univamu.epu.path.model.PathStep;
import fr.univamu.epu.path.model.PathStepPK;

@Repository
public interface PathStepRepository extends JpaRepository<PathStep, PathStepPK> {
}
