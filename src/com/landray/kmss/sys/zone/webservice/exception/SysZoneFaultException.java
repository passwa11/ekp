package com.landray.kmss.sys.zone.webservice.exception;

import javax.xml.ws.WebFault;

@WebFault(name = "SysZoneFault")
public class SysZoneFaultException extends Exception {

	private static final long serialVersionUID = -6322653599478472146L;
	
	private SysZoneFault faultInfo;
	
	public SysZoneFaultException(String message) {
		super(message);
	}
	
	public SysZoneFaultException(String message, SysZoneFault faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
	}

	public SysZoneFaultException(String message, SysZoneFault faultInfo, Throwable cause) {
		super(message, cause);
		this.faultInfo = faultInfo;
	}

	public SysZoneFault getFaultInfo() {
		return faultInfo;
	}
}
