package com.landray.kmss.third.ding.notify.queue.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.ding.notify.queue.forms.ThirdDingNotifyQueueErrorForm;
import com.landray.kmss.util.DateUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

@SuppressWarnings("serial")
public class ThirdDingNotifyQueueError extends BaseModel implements
		InterceptFieldEnabled {

	private String fdSubject;
	private String fdAppName;
	// private String fdModelName;
	// private String fdModelId;
	// private String fdKey;

	/**
	 * 推送方法
	 */
	private String fdMethod;

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
	 * 待办唯一标识
	 */
	private String fdMD5;

	private Date fdCreateTime;

	/**
	 * 发送时间
	 */
	private Date fdSendTime;



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

	public String getFdDingUserId() {
		return fdDingUserId;
	}

	public void setFdDingUserId(String fdDingUserId) {
		this.fdDingUserId = fdDingUserId;
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

	private String fdDingUserId;

	private String fdTodoId;

	private SysOrgPerson fdUser;

	@Override
    public Class<ThirdDingNotifyQueueErrorForm> getFormClass() {
		return ThirdDingNotifyQueueErrorForm.class;
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
			toFormPropertyMap.put("fdUser.fdName", "fdUserName");
			toFormPropertyMap.put("fdUser.fdId", "fdUserId");
		}
		return toFormPropertyMap;
	}

	public SysOrgPerson getFdUser() {
		return fdUser;
	}

	public void setFdUser(SysOrgPerson fdUser) {
		this.fdUser = fdUser;
	}

	public String getFdSystemId() {
		return fdSystemId;
	}

	public void setFdSystemId(String fdSystemId) {
		this.fdSystemId = fdSystemId;
	}

	private String fdSystemId;

	public Boolean getFdDeleteMapping() {
		return fdDeleteMapping;
	}

	public void setFdDeleteMapping(Boolean fdDeleteMapping) {
		this.fdDeleteMapping = fdDeleteMapping;
	}

	private Boolean fdDeleteMapping = false;

}
