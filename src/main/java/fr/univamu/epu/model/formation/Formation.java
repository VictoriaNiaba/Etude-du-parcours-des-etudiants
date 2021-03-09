package fr.univamu.epu.model.formation;

import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import fr.univamu.epu.model.step.Step;

@Entity(name = "Formation")
public class Formation {

	@Id
	@Column(name = "formation_code", length = 20, nullable = false, unique = false)
	private String formation_code;

	@Basic(optional = true)
	@Column(name = "name", length = 150, nullable = true)
	private String formation_name;

	@Basic(optional = true)
	@Column(name = "description", length = 4096, nullable = true)
	private String description;

	@Basic(optional = true)
	@Column(name = "type", length = 100, nullable = true)
	private String type;

	@Basic(optional = true)
	@Column(name = "url", length = 2083, nullable = true)
	private String url;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Date add_date;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Date last_modification;

	@ManyToMany
	@JoinTable(name = "formation_step")
	private Set<Step> steps;

	public Formation() {
		super();
	}

	public Formation(String formationCode, String name, String description, String type, String url, Set<Step> steps) {
		super();
		this.formation_code = formationCode;
		this.formation_name = name;
		this.description = description;
		this.type = type;
		this.url = url;
		this.steps = steps;
		this.add_date = new Date();
		this.last_modification = new Date();
	}
	
	public String getFormation_code() {
		return formation_code;
	}

	public void setFormation_code(String formation_code) {
		this.formation_code = formation_code;
	}

	public String getFormation_name() {
		return formation_name;
	}

	public void setFormation_name(String formation_name) {
		this.formation_name = formation_name;
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
	
	public Date getAdd_date() {
		return add_date;
	}

	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}

	public Date getLast_modification() {
		return last_modification;
	}

	public void setLast_modification(Date last_modification) {
		this.last_modification = last_modification;
	}

	@Override
	public String toString() {
		return "Formation [formation_code=" + formation_code + ", formation_name=" + formation_name + ", description="
				+ description + ", type=" + type + ", url=" + url + ", add_date=" + add_date + ", last_modification="
				+ last_modification + ", steps=" + steps + "]";
	}

}
