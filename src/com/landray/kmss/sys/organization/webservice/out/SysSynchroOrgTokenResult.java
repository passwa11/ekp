package com.landray.kmss.sys.organization.webservice.out;

public class SysSynchroOrgTokenResult {
	
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
	 * 		返回状态值为2时，该值返回数据结果,格式为json数组.如:
	 * 		[{"id":"1","type":"8","name":"test01"}]
	 * 
	 */
	private String message;
	
	/**
	 *  返回数据的总条目数
	 *  为0或小于请求的count数,表示数据已请求完.
	 */
	private int count;


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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	private String token;

	
}
