package fr.univamu.epu.path.api;

import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
public class PathDto {
	@NonNull
	private String code;
	@NonNull
	private List<String> stepCodes;
}
