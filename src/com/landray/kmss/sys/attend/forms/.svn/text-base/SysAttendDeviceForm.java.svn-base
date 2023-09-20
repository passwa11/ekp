package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendDevice;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.web.action.ActionMapping;

public class SysAttendDeviceForm extends ExtendForm {

	private String fdDeviceIds;

	private String fdClientType;

	private String docCreatorId;

	private String docCreatorName;

	private String docCreateTime;
	private String docAlterTime;

	public String getFdDeviceIds() {
		return fdDeviceIds;
	}

	public void setFdDeviceIds(String fdDeviceIds) {
		this.fdDeviceIds = fdDeviceIds;
	}

	public String getFdClientType() {
		return fdClientType;
	}

	public void setFdClientType(String fdClientType) {
		this.fdClientType = fdClientType;
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

	public String getDocAlterTime() {
		return docAlterTime;
	}

	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdClientType = null;
		fdDeviceIds = null;
		docCreatorName = null;
		docCreatorId = null;
		docCreateTime = null;
		docAlterTime = null;
	}

	@Override
	public Class getModelClass() {
		return SysAttendDevice.class;
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
		}
		return toModelPropertyMap;
	}

}
