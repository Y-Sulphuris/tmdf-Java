package org.tmdf;

public class TmdfException extends RuntimeException {
	public TmdfException() {
		super();
	}

	public TmdfException(String message) {
		super(message);
	}

	public TmdfException(String message, Throwable cause) {
		super(message, cause);
	}

	public TmdfException(Throwable cause) {
		super(cause);
	}

	protected TmdfException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
