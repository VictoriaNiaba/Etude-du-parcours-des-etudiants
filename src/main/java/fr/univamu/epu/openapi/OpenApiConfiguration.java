package fr.univamu.epu.openapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfiguration {
	
	@Bean
	public OpenAPI customOpenAPI(@Value("${epu-backend.version}") String appVersion) {
		 return new OpenAPI()
		      .info(new Info()
		      .title("API REST du projet Étude du parcours des étudiants")
		      .version(appVersion)
		      .description("Il s'agit de la documentation de l'API REST d'un prototype permettant d'analyser les cheminements empruntés par des étudiants de l'université d'Aix-Marseille. Le code source et un descriptif du projet sont disponibles [ici](https://etulab.univ-amu.fr/pfe-epu).")
		      .license(new License().name("MIT").url("https://etulab.univ-amu.fr/pfe-epu/epu-backend/-/blob/main/LICENSE.md")));
	}
}