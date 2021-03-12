package fr.univamu.epu.model.path;

import java.util.List;

public class MergedPath {
	
	private List<String> steps;
	
	private List<Double> registered;
	
	public MergedPath() {
		super();
	}

	public MergedPath(List<String> steps, List<Double> registered) {
		super();
		this.steps = steps;
		this.registered = registered;
	}

	public List<String> getSteps() {
		return steps;
	}

	public void setSteps(List<String> steps) {
		this.steps = steps;
	}

	public List<Double> getRegistered() {
		return registered;
	}

	public void setRegistered(List<Double> registered) {
		this.registered = registered;
	}

	@Override
	public String toString() {
		return "MergedPath [steps=" + steps + ", registered=" + registered + "]";
	}
	
}
