package fr.univamu.epu.path.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import fr.univamu.epu.step.model.Step;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "path")
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Path {

	@Id
	@Column(name = "path_code")
	private String code;
	@Column
	private double avgStudentCountPerYear;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "path_step", joinColumns = {
			@JoinColumn(name = "path_code")
	}, inverseJoinColumns = {
			@JoinColumn(name = "step_code")
	})
	private Collection<Step> steps;

}
