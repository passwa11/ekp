package com.landray.kmss.sys.remind.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.remind.forms.SysRemindMainTaskLogForm;

/**
 * 提醒任务日志
 * 
 * @author panyh
 * @date Jun 23, 2020
 */
public class SysRemindMainTaskLog extends BaseModel {

	/**
	 * 通知类型
	 */
	private String fdNotifyType;

	/**
	 * 发送人
	 */
	private SysOrgPerson fdSender;

	/**
	 * 接收者
	 */
	private SysOrgPerson fdReceiver;

	/**
	 * 创建时间
	 */
	private Date fdCreateTime;

	/**
	 * 是否成功
	 */
	private Boolean fdIsSuccess;

	/**
	 * 失败原因
	 */
	private String fdMessage;

	/**
	 * 所属任务
	 */
	private SysRemindMainTask fdTask;

	@Override
	public Class getFormClass() {
		return SysRemindMainTaskLogForm.class;
	}

	private ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdTask.fdId", "fdTaskId");
			toFormPropertyMap.put("fdSender.fdId", "fdSenderId");
			toFormPropertyMap.put("fdSender.fdName", "fdSenderName");
			toFormPropertyMap.put("fdReceiver.fdId", "fdReceiverId");
			toFormPropertyMap.put("fdReceiver.fdName", "fdReceiverName");
		}

		return toFormPropertyMap;
	}

	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}

	public SysOrgPerson getFdSender() {
		return fdSender;
	}

	public void setFdSender(SysOrgPerson fdSender) {
		this.fdSender = fdSender;
	}

	public SysOrgPerson getFdReceiver() {
		return fdReceiver;
	}

	public void setFdReceiver(SysOrgPerson fdReceiver) {
		this.fdReceiver = fdReceiver;
	}

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	public Boolean getFdIsSuccess() {
		return fdIsSuccess;
	}

	public void setFdIsSuccess(Boolean fdIsSuccess) {
		this.fdIsSuccess = fdIsSuccess;
	}

	public String getFdMessage() {
		return fdMessage;
	}

	public void setFdMessage(String fdMessage) {
		this.fdMessage = fdMessage;
	}

	public SysRemindMainTask getFdTask() {
		return fdTask;
	}

	public void setFdTask(SysRemindMainTask fdTask) {
		this.fdTask = fdTask;
	}

}
