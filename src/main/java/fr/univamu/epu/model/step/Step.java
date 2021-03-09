package fr.univamu.epu.model.step;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity(name = "Step")
public class Step implements Serializable {

	@Id
	@Column(name = "step_code", length = 10, nullable = false, unique = false)
	private String stepCode;
	
	@Basic(optional = false)
	@Column(name = "name", length = 100, nullable = true)
	private String name;
	
	public Step() {
		super();
	}

	public Step(String stepCode, String name) {
		super();
		this.stepCode = stepCode;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Step [stepCode=" + stepCode + ", name=" + name + "]";
	}

	public String getStepCode() {
		return stepCode;
	}

	public void setStepCode(String stepCode) {
		this.stepCode = stepCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	
}
