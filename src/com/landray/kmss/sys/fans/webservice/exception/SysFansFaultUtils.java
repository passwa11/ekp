package com.landray.kmss.sys.fans.webservice.exception;

public class SysFansFaultUtils {
	private static final int APPLICATION_ERROR_CODE = 5000;
	private static final String REQUEST_PROCESS_ERROR_MSG = "Request processing failure.";
	private static final String VALIDATION_ERROR_MSG = "Invalid input.";
	private static final String APPLICATION_ERROR_FAULT_MSG = "\u7cfb\u7edf\u5185\u90e8\u9519\u8bef";

	/**
	 * 抛出系统异常
	 * 
	 * @throws SysFansFaultException
	 */
	public static void throwApplicationError() throws SysFansFaultException {
		throw new SysFansFaultException(REQUEST_PROCESS_ERROR_MSG, createSysFansFault());
	}

	/**
	 * 抛出验证异常
	 * 
	 * @param code
	 * @param details
	 * @throws SysFansFaultException
	 */
	public static void throwValidationException(int code, String details)
			throws SysFansFaultException {
		SysFansFault sysFansFault = createSysFansFault(code, details);
		throw new SysFansFaultException(VALIDATION_ERROR_MSG, sysFansFault);
	}

	private static SysFansFault createSysFansFault() {
		return createSysFansFault(APPLICATION_ERROR_CODE,
				APPLICATION_ERROR_FAULT_MSG);
	}

	private static SysFansFault createSysFansFault(int code, String details) {
		SysFansFault fault = new SysFansFault();
		fault.setSysFansFaultCode(String.valueOf(code));
		fault.setSysFansFaultMessage(details);
		return fault;
	}
}
