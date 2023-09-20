package com.landray.kmss.sys.attend.webservice;

public class SysAttendResult {
	/**
	 * 		返回状态
	 * 说明:
	 * 		0未操作,1失败,2成功
	 */
	private int returnState = SysAttendWebServiceConstant.RETURN_CONSTANT_STATUS_NOOPT;
	
	private String message;
	
	public int getReturnState() {
		return returnState;
	}

	public void setReturnState(int returnState) {
		this.returnState = returnState;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
