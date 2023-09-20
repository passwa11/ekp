package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendSynDingNotify;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;



/**
 * 不合法考勤待阅消息表
 * 
 * @author
 * @version 1.0 2020-01-08
 */
public class SysAttendSynDingNotifyForm extends ExtendForm {

	private String docCreatorId;
	private String docCreatorName;
	private String docCreateTime;
	private String fdStatus;
	private String fdLink;
	private String docSubject;
	private String fdSysAttendSynDingId;
	private String fdReceiverId;
	private String fdReceiverName;
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdStatus = null;
		fdLink = null;
		docSubject = null;
		fdSysAttendSynDingId = null;
		docCreateTime = null;
		docCreatorId = null;
		docCreatorName = null;
		fdReceiverId = null;
		fdReceiverName = null;

		super.reset(mapping, request);
	}

	@Override
    public Class<SysAttendSynDingNotify> getModelClass() {
		return SysAttendSynDingNotify.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId",
					new FormConvertor_IDToModel("docCreator",
						SysOrgElement.class));
			toModelPropertyMap.put("fdReceiverId",
					new FormConvertor_IDToModel("fdReceiver",
							SysOrgPerson.class));
		}
		return toModelPropertyMap;
	}


	public String getDocCreatorId() {
		return docCreatorId;
	}

	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	public String getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/*
	 * 发送状态，0：未发送，1：已发送
	 */
	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	/*
	 * 跳转链接
	 */
	public String getFdLink() {
		return fdLink;
	}

	public void setFdLink(String fdLink) {
		this.fdLink = fdLink;
	}

	/*
	 * 消息标题
	 */
	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	public String getFdSysAttendSynDingId() {
		return fdSysAttendSynDingId;
	}

	public void setFdSysAttendSynDingId(String fdSysAttendSynDingId) {
		this.fdSysAttendSynDingId = fdSysAttendSynDingId;
	}

	/*
	 * 接收人
	 */
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
}
