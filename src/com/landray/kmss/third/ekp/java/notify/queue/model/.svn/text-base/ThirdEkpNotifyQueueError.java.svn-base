package com.landray.kmss.third.ekp.java.notify.queue.model;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

public class ThirdEkpNotifyQueueError extends BaseModel implements
		InterceptFieldEnabled {

	private String fdSubject;
	private String fdAppName;
	private String fdModelName;
	private String fdModelId;
	private String fdKey;

	/**
	 * 推送方法
	 */
	private String fdMethod;

	/**
	 * 消息内容
	 */
	private String fdJson;

	/**
	 * 当前操作人员
	 */
	private String fdUserId;

	/**
	 * 异常信息
	 */
	private String fdErrorMsg;

	/**
	 * 重复处理次数
	 */
	private String fdRepeatHandle;

	/**
	 * 处理标识(失败:1,发送中:0)
	 */
	private String fdFlag;
	/**
	 * 待办唯一标识
	 */
	private String fdMD5;

	private Date fdCreateTime;

	/**
	 * 发送时间
	 */
	private Date fdSendTime;

	@Override
	public Class getFormClass() {
		return null;
	}

	public String getFdSubject() {
		return fdSubject;
	}

	public void setFdSubject(String fdSubject) {
		this.fdSubject = fdSubject;
	}

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	public String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	public String getFdMethod() {
		return fdMethod;
	}

	public void setFdMethod(String fdMethod) {
		this.fdMethod = fdMethod;
	}

	public String getFdJson() {
		return (String) readLazyField("fdJson", fdJson);
	}

	public void setFdJson(String fdJson) {
		this.fdJson = (String) writeLazyField("fdJson", this.fdJson, fdJson);
	}

	public String getFdUserId() {
		return fdUserId;
	}

	public void setFdUserId(String fdUserId) {
		this.fdUserId = fdUserId;
	}

	public String getFdErrorMsg() {
		return fdErrorMsg;
	}

	public void setFdErrorMsg(String fdErrorMsg) {
		this.fdErrorMsg = fdErrorMsg;
	}

	public String getFdRepeatHandle() {
		return fdRepeatHandle;
	}

	public void setFdRepeatHandle(String fdRepeatHandle) {
		this.fdRepeatHandle = fdRepeatHandle;
	}

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	public String getFdAppName() {
		return fdAppName;
	}

	public void setFdAppName(String fdAppName) {
		this.fdAppName = fdAppName;
	}

	public String getFdKey() {
		return fdKey;
	}

	public void setFdKey(String fdKey) {
		this.fdKey = fdKey;
	}

	public String getFdMD5() {
		return fdMD5;
	}

	public void setFdMD5(String fdMD5) {
		this.fdMD5 = fdMD5;
	}

	public String getFdFlag() {
		return fdFlag;
	}

	public void setFdFlag(String fdFlag) {
		this.fdFlag = fdFlag;
	}

	public Date getFdSendTime() {
		return fdSendTime;
	}

	public void setFdSendTime(Date fdSendTime) {
		this.fdSendTime = fdSendTime;
	}

}
