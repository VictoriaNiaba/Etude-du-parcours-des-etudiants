package fr.univamu.epu.model.step;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "StepStat")
public class StepStat implements Serializable {
	
	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long stepStatId;

	@ManyToOne
    @JoinColumn(name="step_in_of", nullable=true)
	private Step step_in_of;
	
	@ManyToOne
    @JoinColumn(name="step_out_of", nullable=true)
	private Step step_out_of;
	
	@Column(name = "step_code", length = 10, nullable = true, unique = false)
	private String step_code;
	
	@Column(name = "number")
	private Double number;

	public StepStat() {
		super();
	}
	
	public StepStat(String stepCode, Double number) {
		super();
		this.step_code = stepCode;
		this.number = number;
	}
	
	public long getStepStatId() {
		return stepStatId;
	}

	public String getStep_code() {
		return step_code;
	}

	public void setStep_code(String step_code) {
		this.step_code = step_code;
	}

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	public void setStepInOf(Step stepInOf) {
		this.step_in_of = stepInOf;
	}

	public void setStepOutOf(Step stepOutOf) {
		this.step_out_of = stepOutOf;
	}
	
}
