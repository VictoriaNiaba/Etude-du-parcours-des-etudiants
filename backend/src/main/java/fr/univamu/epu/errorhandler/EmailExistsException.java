package fr.univamu.epu.errorhandler;

public class EmailExistsException extends Throwable {

	private static final long serialVersionUID = 1L;

	public EmailExistsException(final String message) {
		super(message);
	}

}
