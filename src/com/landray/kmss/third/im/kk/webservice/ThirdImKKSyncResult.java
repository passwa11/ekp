package com.landray.kmss.third.im.kk.webservice;

/**
 * EKP接口返回信息
 * @author 孙佳
 * @date 2017年8月14日
 */
public class ThirdImKKSyncResult {

	/**
	 * 0未操作，1失败，2成功
	 */
	private int returnState;

	/**
	 * 返回状态值为0时，该值返回空。返回状态值为1时，该值返回错误信息。返回状态值为2时，该值返回空
	 */
	private String message;


	/*
	 * public ThirdImKKSyncResult(Integer returnState, String message) {
	 * this.returnState = returnState; this.message = message; }
	 */

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
