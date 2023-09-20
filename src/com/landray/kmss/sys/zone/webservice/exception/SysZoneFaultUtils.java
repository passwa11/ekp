package com.landray.kmss.sys.zone.webservice.exception;


public class SysZoneFaultUtils {
	private static final int APPLICATION_ERROR_CODE = 5000;
	private static final String REQUEST_PROCESS_ERROR_MSG = "Request processing failure.";
	private static final String VALIDATION_ERROR_MSG = "Invalid input.";
	private static final String APPLICATION_ERROR_FAULT_MSG = "\u7cfb\u7edf\u5185\u90e8\u9519\u8bef";

	/**
	 * 抛出系统异常
	 * 
	 * @throws SysZoneFaultException
	 */
	public static void throwApplicationError() throws SysZoneFaultException {
		throw new SysZoneFaultException(REQUEST_PROCESS_ERROR_MSG, createSysZoneFault());
	}

	/**
	 * 抛出验证异常
	 * 
	 * @param code
	 * @param details
	 * @throws SysZoneFaultException
	 */
	public static void throwValidationException(int code, String details)
			throws SysZoneFaultException {
		SysZoneFault sysZoneFault = createSysZoneFault(code, details);
		throw new SysZoneFaultException(VALIDATION_ERROR_MSG, sysZoneFault);
	}

	private static SysZoneFault createSysZoneFault() {
		return createSysZoneFault(APPLICATION_ERROR_CODE,
				APPLICATION_ERROR_FAULT_MSG);
	}

	private static SysZoneFault createSysZoneFault(int code, String details) {
		SysZoneFault fault = new SysZoneFault();
		fault.setSysZoneFaultCode(String.valueOf(code));
		fault.setSysZoneFaultMessage(details);
		return fault;
	}
}
