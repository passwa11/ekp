package com.landray.kmss.sys.fans.webservice.exception;

import javax.xml.ws.WebFault;

@WebFault(name = "SysFansFault")
public class SysFansFaultException extends Exception {
	private static final long serialVersionUID = -2265064087155529333L;
	private SysFansFault faultInfo;
	
	public SysFansFaultException(String message) {
		super(message);
	}
	
	public SysFansFaultException(String message, SysFansFault faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
	}

	public SysFansFaultException(String message, SysFansFault faultInfo, Throwable cause) {
		super(message, cause);
		this.faultInfo = faultInfo;
	}

	public SysFansFault getFaultInfo() {
		return faultInfo;
	}
}
