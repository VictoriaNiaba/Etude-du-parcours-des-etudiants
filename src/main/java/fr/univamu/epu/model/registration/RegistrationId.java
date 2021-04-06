package fr.univamu.epu.model.registration;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

@Embeddable
public class RegistrationId implements Serializable {

	@Column(name = "step_code", length = 10, nullable = false, unique = false)
    String stepCode;
    
    @Column(name = "student_code", length = 10, nullable = false, unique = false)
    String studentCode;

	@Column(name = "year", nullable = false, unique = false)
    Integer year;

	public RegistrationId() {
		super();
	}

	public RegistrationId(String stepCode, String studentCode, Integer year) {
		super();
		this.stepCode = stepCode;
		this.studentCode = studentCode;
		this.year = year;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stepCode == null) ? 0 : stepCode.hashCode());
		result = prime * result + ((studentCode == null) ? 0 : studentCode.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
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
		RegistrationId other = (RegistrationId) obj;
		if (stepCode == null) {
			if (other.stepCode != null)
				return false;
		} else if (!stepCode.equals(other.stepCode))
			return false;
		if (studentCode == null) {
			if (other.studentCode != null)
				return false;
		} else if (!studentCode.equals(other.studentCode))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}
	
	
	
}
