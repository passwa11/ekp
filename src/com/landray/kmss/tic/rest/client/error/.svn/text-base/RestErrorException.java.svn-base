package com.landray.kmss.tic.rest.client.error;

public class RestErrorException extends Exception {
	private static final long serialVersionUID = -6357149550353160810L;

	private RestError error;

	public RestErrorException(RestError error) {
		super(error.toString());
		this.error = error;
	}

	public RestErrorException(RestError error, Throwable cause) {
		super(error.toString(), cause);
		this.error = error;
	}

	public RestError getError() {
		return this.error;
	}

}
