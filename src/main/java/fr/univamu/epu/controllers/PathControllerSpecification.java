package fr.univamu.epu.controllers;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import fr.univamu.epu.model.path.MergedPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Gestion des cheminements")
public interface PathControllerSpecification {

	@Operation(summary = "Récupère les 50 cheminements les plus populaires, possiblement filtrés avec firststep et laststep", //
			description = "", //
			tags = { "path" })
	public ResponseEntity<Collection<MergedPath>> find(@RequestParam(required = false) String firststep, @RequestParam(required = false) String laststep);
}
