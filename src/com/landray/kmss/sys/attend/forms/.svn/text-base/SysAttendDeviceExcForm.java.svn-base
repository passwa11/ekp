package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.attend.model.SysAttendDeviceExc;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

public class SysAttendDeviceExcForm extends ExtendForm
		implements IAttachmentForm {

	private String fdClientType;

	private String fdDeviceExcMode;

	private String docCreatorId;

	private String docCreatorName;

	private String docCreateTime;
	private String fdMainId;

	public String getFdClientType() {
		return fdClientType;
	}

	public void setFdClientType(String fdClientType) {
		this.fdClientType = fdClientType;
	}

	public String getFdDeviceExcMode() {
		return fdDeviceExcMode;
	}

	public void setFdDeviceExcMode(String fdDeviceExcMode) {
		this.fdDeviceExcMode = fdDeviceExcMode;
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

	public String getFdMainId() {
		return fdMainId;
	}

	public void setFdMainId(String fdMainId) {
		this.fdMainId = fdMainId;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdClientType = null;
		fdDeviceExcMode = null;
		docCreatorName = null;
		docCreatorId = null;
		docCreateTime = null;
		fdMainId = null;
	}

	@Override
	public Class getModelClass() {
		return SysAttendDeviceExc.class;
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

	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}
}
