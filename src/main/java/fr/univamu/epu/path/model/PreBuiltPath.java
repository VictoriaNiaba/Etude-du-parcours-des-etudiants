package fr.univamu.epu.path.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import lombok.Getter;
import lombok.Setter;

@Entity
@Immutable
@Table(name = "pre_built_path")
@Subselect("select uuid() as code, p.* from pre_built_path p")
@Getter @Setter
public class PreBuiltPath {

	@Id
	private String code;
	@Column
	private String stepSequence;
	@Column
	private double avgStudentCountPerYear;

}
