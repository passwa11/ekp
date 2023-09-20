package com.landray.kmss.sys.attend.service.spring;

public class SysAttendExchangeResult {
	/**
	 * 		返回状态
	 */
	private boolean returnState;
	
	private String message;
	
	public boolean getReturnState() {
		return returnState;
	}

	public void setReturnState(boolean returnState) {
		this.returnState = returnState;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
