package com.landray.kmss.km.imeeting.model;

import com.landray.kmss.common.model.BaseModel;

/**
 * 对接出错Model
 */
public class KmImeetingIntegrateError extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 会议ID
	 */
	private String meetingId;

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	/**
	 * 异常key
	 */
	private String errorKey;

	public String getErrorKey() {
		return errorKey;
	}

	public void setErrorKey(String errorKey) {
		this.errorKey = errorKey;
	}

	/**
	 * 异常信息
	 */
	private String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * 修复链接
	 */
	private String fixUrl;

	public String getFixUrl() {
		return fixUrl;
	}

	public void setFixUrl(String fixUrl) {
		this.fixUrl = fixUrl;
	}

	/**
	 * 拓展字段
	 */
	private String extendJSON;

	public String getExtendJSON() {
		return (String) readLazyField("extendJSON", extendJSON);
	}

	public void setExtendJSON(String extendJSON) {
		this.extendJSON = (String) writeLazyField("extendJSON",
				this.extendJSON, extendJSON);
	}

	@Override
	public Class getFormClass() {
		return null;
	}
}
