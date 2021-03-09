package fr.univamu.epu.controllers;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import fr.univamu.epu.model.registration.Registration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Gestion des inscriptions administratives")
public interface RegistrationControllerSpecification {

	@Operation(summary = "Récupère toutes les inscriptions administratives", //
			description = "", //
			tags = { "Inscription administrative" })
	public ResponseEntity<Collection<Registration>> findAll();

	@Operation(summary = "Upload un lot d'inscriptions administratives", //
			description = "Cette route supporte le format CSV uniquement.", //
			tags = { "Inscription administrative" })
	public ResponseEntity<String> upload(MultipartFile csvfile) throws IOException;
}
