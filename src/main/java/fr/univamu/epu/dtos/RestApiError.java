package fr.univamu.epu.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RestApiError implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private final LocalDateTime timestamp;
	private String title;
	private String detail;
	private int status;
	private String path;
	
	public RestApiError(HttpStatus status, String title, String detail, String path) {
		this.timestamp = LocalDateTime.now();
		this.title = title;
		this.status = status.value();
		this.detail = detail;
		this.path = path;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return "RestApiError [timestamp=" + timestamp + ", title=" + title + ", status=" + status + ", path=" + path
				+ "]";
	}
}
