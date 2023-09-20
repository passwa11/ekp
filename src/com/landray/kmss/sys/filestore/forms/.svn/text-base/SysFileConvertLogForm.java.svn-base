package com.landray.kmss.sys.filestore.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.filestore.model.SysFileConvertLog;

public class SysFileConvertLogForm extends ExtendForm {
	private static final long serialVersionUID = 750985991267138421L;

	private String fdQueueId;

	private String fdConvertKey;

	private String fdConvertStatus;

	private String fdStatusTime;
	
	private String fdStatusInfo;
	
	
	
	public String getFdQueueId() {
		return fdQueueId;
	}


	public void setFdQueueId(String fdQueueId) {
		this.fdQueueId = fdQueueId;
	}


	public String getFdConvertKey() {
		return fdConvertKey;
	}


	public void setFdConvertKey(String fdConvertKey) {
		this.fdConvertKey = fdConvertKey;
	}


	public String getFdConvertStatus() {
		return fdConvertStatus;
	}


	public void setFdConvertStatus(String fdConvertStatus) {
		this.fdConvertStatus = fdConvertStatus;
	}


	public String getFdStatusTime() {
		return fdStatusTime;
	}


	public void setFdStatusTime(String fdStatusTime) {
		this.fdStatusTime = fdStatusTime;
	}


	public String getFdStatusInfo() {
		return fdStatusInfo;
	}


	public void setFdStatusInfo(String fdStatusInfo) {
		this.fdStatusInfo = fdStatusInfo;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Class getModelClass() {
		return SysFileConvertLog.class;
	}

	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.fdConvertStatus=null;
		this.fdStatusTime=null;
		super.reset(mapping, request);
	}


}
