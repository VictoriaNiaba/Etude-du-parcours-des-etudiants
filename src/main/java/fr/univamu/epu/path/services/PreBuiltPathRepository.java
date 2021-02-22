package fr.univamu.epu.path.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.univamu.epu.path.model.PreBuiltPath;

@Repository
public interface PreBuiltPathRepository extends JpaRepository<PreBuiltPath, String> {
}
