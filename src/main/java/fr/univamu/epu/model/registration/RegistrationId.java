package fr.univamu.epu.model.registration;

import java.io.Serializable;

public class RegistrationId implements Serializable {
    private String stepCode;

    private String studentCode;
    
    private Integer year;

	public RegistrationId(String stepCode, String studentCode, Integer year) {
		super();
		this.stepCode = stepCode;
		this.studentCode = studentCode;
		this.year = year;
	}

}
