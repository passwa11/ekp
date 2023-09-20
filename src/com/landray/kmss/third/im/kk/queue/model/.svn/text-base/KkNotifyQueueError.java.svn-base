package com.landray.kmss.third.im.kk.queue.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.im.kk.queue.constant.KkNotifyQueueErrorConstants;
import com.landray.kmss.third.im.kk.queue.forms.KkNotifyQueueErrorForm;
import com.landray.kmss.util.DateUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

@SuppressWarnings("serial")
public class KkNotifyQueueError extends BaseModel implements
		InterceptFieldEnabled {

	private String fdSubject;
	private String fdAppName;

	/**
	 * 推送地址
	 */
	private String fdUrl;

	/**
	 * 消息内容
	 */
	private String fdJson;

	/**
	 * 异常信息
	 */
	private String fdErrorMsg;

	/**
	 * 重复处理次数
	 */
	private Integer fdRepeatHandle;

	/**
	 * 处理标识(失败:1,发送中:0)
	 */
	private String fdFlag;


	/**
	 * 创建时间
	 */
	private Date fdCreateTime;

	/**
	 * 发送时间
	 */
	private Date fdSendTime;

	public String getFdUrl() {
		return fdUrl;
	}

	public KkNotifyQueueError(String fdSubject, String fdAppName, String fdUrl, String fdJson, String fdErrorMsg, String fdTodoId) {
		this.fdSubject = fdSubject;
		this.fdAppName = fdAppName;
		this.fdUrl = fdUrl;
		this.fdJson = fdJson;
		this.fdErrorMsg = fdErrorMsg;
		this.fdTodoId = fdTodoId;
		this.fdCreateTime = new Date();
		this.fdFlag=KkNotifyQueueErrorConstants.NOTIFY_ERROR_FDFLAG_ERROR;
		this.fdRepeatHandle = KkNotifyQueueErrorConstants.NOTIFY_ERROR_REPEAT;
	}

	public KkNotifyQueueError() {
		super();
		this.fdCreateTime = new Date();
		this.fdRepeatHandle = KkNotifyQueueErrorConstants.NOTIFY_ERROR_REPEAT;
	}

	public void setFdUrl(String fdUrl) {
		this.fdUrl = fdUrl;
	}

	public String getFdJson() {
		return (String) readLazyField("fdJson", fdJson);
	}

	public void setFdJson(String fdJson) {
		this.fdJson = (String) writeLazyField("fdJson", this.fdJson, fdJson);
	}

	public String getFdErrorMsg() {
		return fdErrorMsg;
	}

	public void setFdErrorMsg(String fdErrorMsg) {
		this.fdErrorMsg = fdErrorMsg;
	}

	public Integer getFdRepeatHandle() {
		return fdRepeatHandle;
	}

	public void setFdRepeatHandle(Integer fdRepeatHandle) {
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

	public String getFdSubject() {
		return fdSubject;
	}

	public void setFdSubject(String fdSubject) {
		this.fdSubject = fdSubject;
	}

	public String getFdTodoId() {
		return fdTodoId;
	}

	public void setFdTodoId(String fdTodoId) {
		this.fdTodoId = fdTodoId;
	}

	private String fdTodoId;

	@Override
    public Class<KkNotifyQueueErrorForm> getFormClass() {
		return KkNotifyQueueErrorForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdCreateTime",
					new ModelConvertor_Common("fdCreateTime")
							.setDateTimeType(DateUtil.TYPE_DATETIME));
			toFormPropertyMap.put("fdSendTime",
					new ModelConvertor_Common("fdSendTime")
							.setDateTimeType(DateUtil.TYPE_DATETIME));
		}
		return toFormPropertyMap;
	}
}
