package fr.univamu.epu.controllers;

import java.io.IOException;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import fr.univamu.epu.model.step.Step;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@Tag(name = "Gestion des étapes")
public interface StepControllerSpecification {

	@Operation(summary = "Ajoute une étape non-existante", //
			description = "", //
			tags = { "étapes" })
	public ResponseEntity<Step> add(@Valid Step step);

	@Operation(summary = "Met à jour une étape à partir de son code", //
			description = "", //
			tags = { "étapes" })
	public ResponseEntity<Step> update(@Valid Step step, String code);

	@Operation(summary = "Supprime une étape à partir de son code", //
			description = "", //
			tags = { "étapes" })
	public ResponseEntity<Void> remove(String code);

	@Operation(summary = "Récupère une étape en fonction de son code", //
			description = "", //
			tags = { "étapes" })
	public ResponseEntity<Step> find(String code);

	@Operation(summary = "Récupère toutes les étapes", //
			description = "", //
			tags = { "étapes" })
	public ResponseEntity<Collection<Step>> findAll();

	@Operation(summary = "Upload un lot d'étapes", //
			description = "Cette route supporte le format CSV uniquement.", //
			tags = { "étapes" })
	public ResponseEntity<String> upload(MultipartFile csvfile) throws IOException;
}
