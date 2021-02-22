package fr.univamu.epu.step.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "step")
@Getter @Setter
public class Step {

	@Id
	@Column(name = "step_code")
	private String code;
	@Column
	private String name;
	@Column
	private Short year;
}
