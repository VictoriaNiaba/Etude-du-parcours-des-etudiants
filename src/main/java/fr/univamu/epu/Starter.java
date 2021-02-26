package fr.univamu.epu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import fr.univamu.epu.business.SpringBusinessConfig;
import fr.univamu.epu.dao.SpringDAOConfig;

@SpringBootApplication
@Import({SpringDAOConfig.class, SpringBusinessConfig.class })
public class Starter {

	public static void main(String[] args) {
		SpringApplication.run(Starter.class, args);
	}

}
