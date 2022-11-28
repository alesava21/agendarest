package it.prova.agenda.web.api.exception;

public class PermisNegatedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PermisNegatedException(String message) {
		super(message);
	}

}
