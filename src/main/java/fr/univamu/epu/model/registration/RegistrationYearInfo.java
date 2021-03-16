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
}
