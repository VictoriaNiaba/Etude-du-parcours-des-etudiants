package fr.univamu.epu.model.registration;

import java.io.Serializable;

import fr.univamu.epu.model.step.Step;

public class RegistrationId implements Serializable {
    private Step step;

    private String studentCode;
    
    private Integer year;

	public RegistrationId(Step step, String studentCode, Integer year) {
		super();
		this.step = step;
		this.studentCode = studentCode;
		this.year = year;
	}

}
