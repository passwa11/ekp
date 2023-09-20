package com.landray.kmss.sys.remind.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.remind.model.SysRemindMainTask;
import com.landray.kmss.sys.remind.model.SysRemindMainTaskLog;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 提醒任务日志
 * 
 * @author panyh
 * @date Jun 27, 2020
 */
public class SysRemindMainTaskLogForm extends ExtendForm {

	/**
	 * 通知类型
	 */
	private String fdNotifyType;

	/**
	 * 发送人
	 */
	private String fdSenderId;
	private String fdSenderName;

	/**
	 * 接收者
	 */
	private String fdReceiverId;
	private String fdReceiverName;

	/**
	 * 创建时间
	 */
	private Date fdCreateTime;

	/**
	 * 是否成功
	 */
	private String fdIsSuccess;

	/**
	 * 失败原因
	 */
	private String fdMessage;

	/**
	 * 所属任务
	 */
	private String fdTaskId;

	@Override
	public Class getModelClass() {
		return SysRemindMainTaskLog.class;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.fdNotifyType = null;
		this.fdSenderId = null;
		this.fdSenderName = null;
		this.fdReceiverId = null;
		this.fdReceiverName = null;
		this.fdCreateTime = null;
		this.fdIsSuccess = null;
		this.fdMessage = null;
		this.fdTaskId = null;
		super.reset(mapping, request);
	}

	private FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdTaskId", new FormConvertor_IDToModel("fdTask", SysRemindMainTask.class));
			toModelPropertyMap.put("fdSenderId", new FormConvertor_IDToModel("fdSender", SysOrgPerson.class));
			toModelPropertyMap.put("fdReceiverId", new FormConvertor_IDToModel("fdReceiver", SysOrgPerson.class));
		}
		return toModelPropertyMap;
	}

	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}

	public String getFdSenderId() {
		return fdSenderId;
	}

	public void setFdSenderId(String fdSenderId) {
		this.fdSenderId = fdSenderId;
	}

	public String getFdSenderName() {
		return fdSenderName;
	}

	public void setFdSenderName(String fdSenderName) {
		this.fdSenderName = fdSenderName;
	}

	public String getFdReceiverId() {
		return fdReceiverId;
	}

	public void setFdReceiverId(String fdReceiverId) {
		this.fdReceiverId = fdReceiverId;
	}

	public String getFdReceiverName() {
		return fdReceiverName;
	}

	public void setFdReceiverName(String fdReceiverName) {
		this.fdReceiverName = fdReceiverName;
	}

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	public String getFdIsSuccess() {
		return fdIsSuccess;
	}

	public void setFdIsSuccess(String fdIsSuccess) {
		this.fdIsSuccess = fdIsSuccess;
	}

	public String getFdMessage() {
		return fdMessage;
	}

	public void setFdMessage(String fdMessage) {
		this.fdMessage = fdMessage;
	}

	public String getFdTaskId() {
		return fdTaskId;
	}

	public void setFdTaskId(String fdTaskId) {
		this.fdTaskId = fdTaskId;
	}

}
