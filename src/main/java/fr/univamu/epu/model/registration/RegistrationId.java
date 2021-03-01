package fr.univamu.epu.model.registration;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

@Embeddable
public class RegistrationId implements Serializable {

	@JoinColumn(name = "step", referencedColumnName = "step_code")
    String stepCode;
    
    @Column(name = "student_code", length = 10, nullable = false, unique = false)
    String studentCode;

	@Column(name = "year", nullable = false, unique = false)
    Integer year;

	public RegistrationId(String stepCode, String studentCode, Integer year) {
		super();
		this.stepCode = stepCode;
		this.studentCode = studentCode;
		this.year = year;
	}
	
}
