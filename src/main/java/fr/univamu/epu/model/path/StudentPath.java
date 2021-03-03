package fr.univamu.epu.model.path;

import java.util.List;

public class StudentPath {
	
	private String studentCode;
	private List<String> stepCodes;
	
	public StudentPath() {
		super();
	}
	
	public StudentPath(String studentCode, List<String> stepCodes) {
		super();
		this.studentCode = studentCode;
		this.stepCodes = stepCodes;
	}

	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	public List<String> getStepCodes() {
		return stepCodes;
	}
	public void setStepCodes(List<String> stepCodes) {
		this.stepCodes = stepCodes;
	}
}
