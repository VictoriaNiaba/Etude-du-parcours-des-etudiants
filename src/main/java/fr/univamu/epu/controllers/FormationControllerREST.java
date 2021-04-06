package fr.univamu.epu.controllers;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.univamu.epu.business.FormationManager;
import fr.univamu.epu.errorhandler.UploadException;
import fr.univamu.epu.model.formation.Formation;

@RestController
@RequestMapping("/formations")
public class FormationControllerREST implements FormationControllerSpecification {
	@Autowired
	private FormationManager formationManager;

	@Override
	@PostMapping
	public ResponseEntity<Formation> add(@RequestBody Formation formation) {
		return ResponseEntity.status(HttpStatus.CREATED).body(formationManager.add(formation));
	}

	@Override
	@PutMapping("/{code}")
	public ResponseEntity<Formation> update(@RequestBody Formation formation, @PathVariable String code) {
		return ResponseEntity.ok(formationManager.update(formation));
	}

	@Override
	@DeleteMapping("/{code}")
	public ResponseEntity<Void> remove(@PathVariable String code) {
		formationManager.remove(code);
		return ResponseEntity.noContent().build();
	}

	@Override
	@GetMapping("/{code}")
	public ResponseEntity<Formation> find(@PathVariable String code) {
		return ResponseEntity.ok(formationManager.find(code));
	}

	@Override
	@GetMapping
	public ResponseEntity<Collection<Formation>> findAll() {
		return ResponseEntity.ok(formationManager.findAll());
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

		formationManager.upload(csvfile.getInputStream());
		return ResponseEntity.ok("Le fichier a été uploadé avec succès");
	}

	private boolean isCSVFile(MultipartFile file) {
		String extension = file.getOriginalFilename().split("\\.")[1];
		return extension.equals("csv");
	}
}
