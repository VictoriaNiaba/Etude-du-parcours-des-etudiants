package fr.univamu.epu.services.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univamu.epu.dao.Dao;
import fr.univamu.epu.model.registration.Registration;

@Service("pathBuilder")
public class PathBuilder {
	
	@Autowired
	Dao dao;
	
	public void generatePaths() {
		System.out.println("CREATIONS DES CHEMINEMENTS");
		
		List<Registration> regs = new ArrayList<Registration>(dao.getAllRegistrations());
		Collections.sort(regs,new Comparator<Registration>() {
			public int compare(Registration o1, Registration o2) {
				return o1.getStudentCode().compareTo(o2.getStudentCode());
			}
		});
		
		
	}
	
}
