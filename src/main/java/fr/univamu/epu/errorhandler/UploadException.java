package fr.univamu.epu.errorhandler;

public class UploadException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UploadException() {
		super();
	}

	public UploadException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public UploadException(final String message) {
		super(message);
	}

	public UploadException(final Throwable cause) {
		super(cause);
	}
}