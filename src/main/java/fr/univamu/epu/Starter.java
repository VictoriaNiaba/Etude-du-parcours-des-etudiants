package fr.univamu.epu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import fr.univamu.epu.dao.SpringDAOConfiguration;

@SpringBootApplication
@Import({SpringDAOConfiguration.class })
public class Starter {

	public static void main(String[] args) {
		SpringApplication.run(Starter.class, args);
	}

}
