package fr.univamu.epu.path.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import fr.univamu.epu.step.model.Step;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "path_step")
@Getter @Setter
public class PathStep {

	@EmbeddedId
	private PathStepPK primaryKey = new PathStepPK();

	@ManyToOne
	@MapsId("pathCode")
	@JoinColumn(name = "path_code", insertable = false, updatable = false)
	private Path path;

	@ManyToOne
	@MapsId("stepCode")
	@JoinColumn(name = "step_code", insertable = false, updatable = false)
	private Step step;

	@Column
	private short stepPosition;
}