package fr.univamu.epu.errorhandler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import fr.univamu.epu.dtos.RestApiError;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<RestApiError> handleAllExceptions(Exception e, HttpServletRequest request) {

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		String title = "Erreur interne du serveur";
		String detail = e.getMessage();
		String path = request.getRequestURI();

		RestApiError apiError = new RestApiError(status, title, detail, path);

		return ResponseEntity.status(status).body(apiError);
	}

	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<RestApiError> handleNotFoundException(NotFoundException e, HttpServletRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		String title = "Ressource non-trouvée";
		String detail = e.getMessage();
		String path = request.getRequestURI();

		RestApiError apiError = new RestApiError(status, title, detail, path);

		return ResponseEntity.status(status).body(apiError);
	}

	@ExceptionHandler({ UploadException.class, MultipartException.class })
	public final ResponseEntity<RestApiError> handleUploadException(UploadException e,
			HttpServletRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		String title = "Mauvaise requête";
		String detail = e.getMessage();
		String path = request.getRequestURI();

		RestApiError apiError = new RestApiError(status, title, detail, path);

		return ResponseEntity.status(status).body(apiError);
	}
}