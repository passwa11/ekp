package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.ratify.model.HrRatifyStaffConcernConfig;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.web.action.ActionMapping;

public class HrRatifyStaffConcernConfigForm extends ExtendForm {

	private String fdEntry;

	private String fdLeave;

	private String fdPositive;

	private String fdTransfer;

	private String fdContract;

	private String fdManagerIds;

	private String fdManagerNames;

	public String getFdEntry() {
		return fdEntry;
	}

	public void setFdEntry(String fdEntry) {
		this.fdEntry = fdEntry;
	}

	public String getFdLeave() {
		return fdLeave;
	}

	public void setFdLeave(String fdLeave) {
		this.fdLeave = fdLeave;
	}

	public String getFdPositive() {
		return fdPositive;
	}

	public void setFdPositive(String fdPositive) {
		this.fdPositive = fdPositive;
	}

	public String getFdTransfer() {
		return fdTransfer;
	}

	public void setFdTransfer(String fdTransfer) {
		this.fdTransfer = fdTransfer;
	}

	public String getFdContract() {
		return fdContract;
	}

	public void setFdContract(String fdContract) {
		this.fdContract = fdContract;
	}

	public String getFdManagerIds() {
		return fdManagerIds;
	}

	public void setFdManagerIds(String fdManagerIds) {
		this.fdManagerIds = fdManagerIds;
	}

	public String getFdManagerNames() {
		return fdManagerNames;
	}

	public void setFdManagerNames(String fdManagerNames) {
		this.fdManagerNames = fdManagerNames;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdEntry = "true";
		fdLeave = "true";
		fdPositive = "true";
		fdTransfer = "true";
		fdContract = "true";
		fdManagerIds = "true";
		fdManagerNames = "true";
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdManagerIds",
					new FormConvertor_IDsToModelList("fdManagers",
							SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

	@Override
	public Class getModelClass() {
		return HrRatifyStaffConcernConfig.class;
	}

}
