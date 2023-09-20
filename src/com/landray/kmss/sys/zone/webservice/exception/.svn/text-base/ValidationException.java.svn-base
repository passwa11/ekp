package com.landray.kmss.sys.zone.webservice.exception;

public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = 3080532099104058709L;
	private int m_errorCode;

	public ValidationException(String errorMessage, int errorCode,
			Throwable cause) {
		super(errorMessage, cause);
		m_errorCode = errorCode;
	}

	public ValidationException(String errorMessage, int errorCode) {
		this(errorMessage, errorCode, null);
	}

	public int getErrorCode() {
		return m_errorCode;
	}
}
