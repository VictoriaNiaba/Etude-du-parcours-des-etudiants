package fr.univamu.epu.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.univamu.epu.dao.Dao;
import fr.univamu.epu.message.Message;
import fr.univamu.epu.message.Response;
import fr.univamu.epu.model.step.Step;
import fr.univamu.epu.services.csvimport.CsvParser;

@CrossOrigin("*")
@RestController
@RequestMapping("/steps")
public class StepControllerREST {
	@Autowired
	private CsvParser<Step> stepCsvParser;
	@Autowired
	private Dao dao;

	@PostMapping("/_upload")
	public Response uploadSingleCSVFile(@RequestParam("csvfile") MultipartFile csvfile) {

		Response response = new Response();

		// Checking the upload-file's name before processing
		if (csvfile.getOriginalFilename().isEmpty()) {
			response.addMessage(new Message(csvfile.getOriginalFilename(),
					"No selected file to upload! Please do the checking", "fail"));

			return response;
		}

		// checking the upload file's type is CSV or NOT
		if (!isCSVFile(csvfile)) {
			response.addMessage(new Message(
					csvfile.getOriginalFilename(),
					"Error: this is not a CSV file!", "fail"));
			return response;
		}

		try {
			// save file data to database
			Set<Step> steps = stepCsvParser.parse(csvfile.getInputStream());
			for(Step step : steps) {
				dao.addStep(step);
			}	
			response.addMessage(new Message(csvfile.getOriginalFilename(), "Upload File Successfully!", "ok"));
		} catch (Exception e) {
			response.addMessage(new Message(csvfile.getOriginalFilename(), e.getMessage(), "fail"));
		}

		return response;
	}

	public static boolean isCSVFile(MultipartFile file) {
		String extension = file.getOriginalFilename().split("\\.")[1];

		if (!extension.equals("csv")) {
			return false;
		}

		return true;
	}
}