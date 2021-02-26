package fr.univamu.epu.model.registration;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import fr.univamu.epu.model.step.Step;

@Entity(name = "Registration")
@IdClass(RegistrationId.class)
public class Registration implements Serializable{
	

	@Id
	@Column(name = "student_code", length = 10, nullable = false, unique = false)
	private String studentCode;

	@Id
	@Column(name = "year", nullable = false, unique = false)
	private Integer year;
	
	@Id
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "step_code")
    private Step step;

	public Registration ( ) {
		 super();
	}


	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
}
