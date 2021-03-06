package fr.univamu.epu.model.step;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import fr.univamu.epu.model.formation.Formation;

@Entity(name = "Step")
public class Step {

	@Id
	@Column(name = "step_code", length = 10, nullable = false, unique = true)
	private String step_code;

	@Basic(optional = true)
	@Column(name = "name", length = 100, nullable = true)
	private String step_name;

	@ManyToMany(mappedBy = "steps", fetch = FetchType.EAGER)
	private Set<Formation> formations;

	@OneToMany(mappedBy = "step_in_of")
	private List<StepStat> steps_in;

	@OneToMany(mappedBy = "step_out_of")
	private List<StepStat> steps_out;

	@Column(name = "average_repeat")
	private Double average_repeat;

	public Step() {
		super();
	}

	public Step(String stepCode, String name) {
		super();
		this.step_code = stepCode;
		this.step_name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((step_code == null) ? 0 : step_code.hashCode());
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
		Step other = (Step) obj;
		if (step_code == null) {
			if (other.step_code != null)
				return false;
		} else if (!step_code.equals(other.step_code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Step [stepCode=" + step_code + ", name=" + step_name + "]";
	}

	public String getStep_code() {
		return step_code;
	}

	public void setStep_code(String step_code) {
		this.step_code = step_code;
	}

	public String getStep_name() {
		return step_name;
	}

	public void setStep_name(String step_name) {
		this.step_name = step_name;
	}

	public void addFormation(Formation f) {
		if (formations == null) {
			formations = new HashSet<Formation>();
		}
		formations.add(f);
	}

	public void setFormations(Set<Formation> formations) {
		this.formations = formations;
	}

	public List<StepStat> getSteps_in() {
		return steps_in;
	}

	public void setSteps_in(List<StepStat> steps_in) {
		this.steps_in = steps_in;
	}

	public List<StepStat> getSteps_out() {
		return steps_out;
	}

	public void setSteps_out(List<StepStat> steps_out) {
		this.steps_out = steps_out;
	}

	public Double getAverage_repeat() {
		return average_repeat;
	}

	public void setAverage_repeat(Double average_repeat) {
		this.average_repeat = average_repeat;
	}

}
