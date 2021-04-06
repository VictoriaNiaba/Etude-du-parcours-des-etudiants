package fr.univamu.epu.model.registration;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "registrationYearInfo")
public class RegistrationYearInfo {

	@Id
	@Column(name = "year", nullable = false, unique = false)
	private Integer year;

	@Column(name = "registrationCount", nullable = false, unique = false)
	private Integer registrationCount;

	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeStamp;

	public RegistrationYearInfo() {
		super();
	}

	public RegistrationYearInfo(Integer year, Integer registrationCount, Date timeStamp) {
		super();
		this.year = year;
		this.registrationCount = registrationCount;
		this.timeStamp = timeStamp;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getRegistrationCount() {
		return registrationCount;
	}

	public void setRegistrationCount(Integer registrationCount) {
		this.registrationCount = registrationCount;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "RegistrationYearInfo [year=" + year + ", registrationCount=" + registrationCount + ", timeStamp="
				+ timeStamp + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((registrationCount == null) ? 0 : registrationCount.hashCode());
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
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
		RegistrationYearInfo other = (RegistrationYearInfo) obj;
		if (registrationCount == null) {
			if (other.registrationCount != null)
				return false;
		} else if (!registrationCount.equals(other.registrationCount))
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}
	
}
