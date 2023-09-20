package com.landray.kmss.sys.organization.webservice.in;

public class SysSynchroSetResult {
	
	public SysSynchroSetResult(){
		
	}
	
	public SysSynchroSetResult(int returnState){
		this.returnState = returnState;
	}
	
	public SysSynchroSetResult(int returnState, String message){
		this.returnState = returnState;
		this.message  = message;
		
	}
	/**
	 * 		返回状态
	 * 说明:
	 * 		0未操作,1失败,2成功
	 */
	private int returnState;
	
	
	/**
	 * 		返回有关信息
	 * 说明:
	 * 		返回状态值为0时，该值返回空。
	 * 		返回状态值为1时，该值错误信息。
	 * 		返回状态值为2时，该值返回空。
	 */
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
