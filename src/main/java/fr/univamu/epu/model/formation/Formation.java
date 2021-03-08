package fr.univamu.epu.model.formation;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import fr.univamu.epu.model.step.Step;

@Entity(name = "Formation")
public class Formation {

	@Id
	@Column(name = "formation_code", length = 20, nullable = false, unique = false)
	private String formationCode;

	@Basic(optional = true)
	@Column(name = "name", length = 150, nullable = true)
	private String name;

	@Basic(optional = true)
	@Column(name = "description", length = 4096, nullable = true)
	private String description;

	@Basic(optional = true)
	@Column(name = "type", length = 100, nullable = true)
	private String type;

	@Basic(optional = true)
	@Column(name = "url", length = 2083, nullable = true)
	private String url;

	@ManyToMany
	@JoinTable(name = "formation_step")
	private Set<Step> steps;

	public Formation() {
		super();
	}

	public Formation(String formationCode, String name, String description, String type, String url, Set<Step> steps) {
		super();
		this.formationCode = formationCode;
		this.name = name;
		this.description = description;
		this.type = type;
		this.url = url;
		this.steps = steps;
	}

	public String getFormationCode() {
		return formationCode;
	}

	public void setFormationCode(String formationCode) {
		this.formationCode = formationCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<Step> getSteps() {
		return steps;
	}

	// add step et gerer l'association

	public void setSteps(Set<Step> steps) {
		this.steps = steps;
	}

	@Override
	public String toString() {
		return "Formation [formationCode=" + formationCode + ", name=" + name + ", description=" + description
				+ ", type=" + type + ", url=" + url + ", steps=" + steps + "]";
	}

}
