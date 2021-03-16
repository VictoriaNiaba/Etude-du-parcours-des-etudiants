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
public class Registration implements Serializable {

	@EmbeddedId
	public RegistrationId id;

	public Registration() {
		super();
	}

	public Registration(RegistrationId id) {
		super();
		this.id = id;
	}

	public String getStudentCode() {
		return id.studentCode;
	}

	public Integer getYear() {
		return id.year;
	}
	
	public String getStepCode() {
		return id.stepCode;
	}
	
	@Override
	public String toString() {
		return "Registration [studentCode=" + id.studentCode + ", year=" + id.year + ", stepcode=" + id.stepCode + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Registration other = (Registration) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
