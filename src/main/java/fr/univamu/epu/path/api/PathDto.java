package fr.univamu.epu.path.api;

import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
public class PathDto {
	@NonNull
	private List<String> steps;
	@NonNull
	private List<Integer> registered;
}
