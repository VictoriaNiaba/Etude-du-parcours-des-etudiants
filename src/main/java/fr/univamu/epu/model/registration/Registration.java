package fr.univamu.epu.model.registration;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import fr.univamu.epu.model.step.Step;

@Entity(name = "Registration")
public class Registration implements Serializable{
	

	@EmbeddedId RegistrationId id;
	
	@MapsId("stepCode")
	@OneToOne(cascade = CascadeType.ALL)
    private Step step;

	public Registration ( ) {
		 super();
	}
	
	public Registration(RegistrationId id, Step step) {
		super();
		this.id = id;
		this.step = step;
	}




	public String getStudentCode() {
		return id.studentCode;
	}

	public Integer getYear() {
		return id.year;
	}


	public Step getStep() {
		return step;
	}


	@Override
	public String toString() {
		return "Registration [studentCode=" + id.studentCode + ", year=" + id.year + ", step=" + step + "]";
	}
	
	
	
}
