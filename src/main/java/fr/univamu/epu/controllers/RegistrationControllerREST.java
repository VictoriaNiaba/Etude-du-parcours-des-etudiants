package fr.univamu.epu.controllers;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.univamu.epu.business.RegistrationManager;
import fr.univamu.epu.errorhandler.UploadException;
import fr.univamu.epu.model.registration.Registration;

@RestController
@RequestMapping("/registrations")

public class RegistrationControllerREST implements RegistrationControllerSpecification {
	@Autowired
	private RegistrationManager registrationManager;

	@Override
	@GetMapping
	public ResponseEntity<Collection<Registration>> findAll() {
		return ResponseEntity.ok(registrationManager.findAll());
	}

	@Override
	@PostMapping(path = "/_upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> upload(@RequestParam("csvfile") MultipartFile csvfile)
			throws IOException {
		// Vérification de la présence d'un fichier
		if (csvfile.getOriginalFilename().isEmpty()) {
			throw new UploadException("Aucun fichier n'a été fourni");
		}

		// Vérification de l'extension du fichier
		if (!isCSVFile(csvfile)) {
			throw new UploadException("Le fichier fourni n'est pas au format CSV");
		}

		registrationManager.upload(csvfile.getInputStream());
		return ResponseEntity.ok("Le fichier a été uploadé avec succès");
	}

	private boolean isCSVFile(MultipartFile file) {
		String extension = file.getOriginalFilename().split("\\.")[1];
		return extension.equals("csv");
	}
}
