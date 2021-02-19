package fr.univamu.epu.path.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "path")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Path {

	@Id
	@Column(name = "path_code")
	private String code;
	
	@Column
	private double avgStudentCountPerYear;

    @OneToMany(mappedBy = "path")
    private Set<PathStep> pathSteps;
}
