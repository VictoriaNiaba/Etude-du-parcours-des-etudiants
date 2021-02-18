package fr.univamu.epu.step.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.univamu.epu.step.model.Step;

@Repository
public interface StepRepository extends JpaRepository<Step, String> {
}
