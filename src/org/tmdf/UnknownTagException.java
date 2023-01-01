package org.tmdf;

public final class UnknownTagException extends TmdfException {
	public UnknownTagException() {
		super();
	}

	public UnknownTagException(String message) {
		super(message);
	}

	public UnknownTagException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownTagException(Throwable cause) {
		super(cause);
	}

	protected UnknownTagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
