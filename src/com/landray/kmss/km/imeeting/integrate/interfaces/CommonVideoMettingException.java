package com.landray.kmss.km.imeeting.integrate.interfaces;

public class CommonVideoMettingException extends Exception {

	private static final long serialVersionUID = 8365812241894439394L;

	public CommonVideoMettingException(String key) {
		super();
		this.key = key;
	}

	public CommonVideoMettingException(String key, String message) {
		super(message);
		this.key = key;
	}

	public CommonVideoMettingException(String key, Throwable throwable) {
		super(throwable);
		this.key = key;
	}

	public CommonVideoMettingException(String key, String message,
			Throwable throwable) {
		super(message, throwable);
		this.key = key;
	}

	/**
	 * 
	 * 50502 会议开始时间小于当前时间 <BR/>
	 * 50501 时长超过24小时 <BR/>
	 * 50503 预约会议失败 <BR/>
	 * 50505 开始时间大于6个月时间 <BR/>
	 * 50801 编辑会议失败 <BR/>
	 * 50805 会议不存在 <BR/>
	 * 50806 会议已结束 <BR/>
	 * 50802 要变更的会议已过期 <BR/>
	 * 50803 开始时间小于当前时间 <BR/>
	 * 50804 会议正在召开不能变更时间和时长 <BR/>
	 * 50901 取消会议失败 <BR/>
	 * 50902 会议不存在 <BR/>
	 * 50903 要取消的会议正在召开
	 * 
	 */
	private String key;

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
