package fr.univamu.epu.business;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univamu.epu.services.path.PathBuilder;

@Service("pathManager")
public class PathInMemoryManager {
	
	
	
	public PathInMemoryManager() {
		super();
	}

	@PostConstruct
	public void init() {
		System.out.println("==pathManager init QUE DALLE");
	}
	
}
