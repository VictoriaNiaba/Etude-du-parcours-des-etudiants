package fr.univamu.epu.model.path;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Path")
public class Path implements Serializable{

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long pathId;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> stepCodes;
	
	@Basic(optional = false)
	@Column(name = "avgStudentCountPerYear")
	private Double avgStudentCountPerYear;
	
	public Path() {
		super();
	}

	public Path(List<String> stepCodes, double avgStudentCountPerYear) {
		super();
		this.stepCodes = stepCodes;
		this.avgStudentCountPerYear = avgStudentCountPerYear;
	}

	public List<String> getStepCodes() {
		return stepCodes;
	}

	public void setStepCodes(List<String> stepCodes) {
		this.stepCodes = stepCodes;
	}

	public Double getAvgStudentCountPerYear() {
		return avgStudentCountPerYear;
	}

	public void setAvgStudentCountPerYear(Double avgStudentCountPerYear) {
		this.avgStudentCountPerYear = avgStudentCountPerYear;
	}

	@Override
	public String toString() {
		return "Path [pathId=" + pathId + ", stepCodes=" + stepCodes + ", avgStudentCountPerYear="
				+ avgStudentCountPerYear + "]";
	}

	
	
}
