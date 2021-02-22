package fr.univamu.epu.path.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter @Setter
@ToString
@EqualsAndHashCode
public class PathStepPK implements Serializable {
	private static final long serialVersionUID = 1L;
	private String pathCode;
	private String stepCode;
}