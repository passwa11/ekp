package com.landray.kmss.hr.ratify.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.ratify.forms.HrRatifyStaffConcernConfigForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;

public class HrRatifyStaffConcernConfig extends BaseModel {

	private Boolean fdEntry;

	private Boolean fdLeave;

	private Boolean fdPositive;

	private Boolean fdTransfer;

	private Boolean fdContract;

	private List<SysOrgElement> fdManagers;

	public Boolean getFdEntry() {
		return fdEntry;
	}

	public void setFdEntry(Boolean fdEntry) {
		this.fdEntry = fdEntry;
	}

	public Boolean getFdLeave() {
		return fdLeave;
	}

	public void setFdLeave(Boolean fdLeave) {
		this.fdLeave = fdLeave;
	}

	public Boolean getFdPositive() {
		return fdPositive;
	}

	public void setFdPositive(Boolean fdPositive) {
		this.fdPositive = fdPositive;
	}

	public Boolean getFdTransfer() {
		return fdTransfer;
	}

	public void setFdTransfer(Boolean fdTransfer) {
		this.fdTransfer = fdTransfer;
	}

	public Boolean getFdContract() {
		return fdContract;
	}

	public void setFdContract(Boolean fdContract) {
		this.fdContract = fdContract;
	}

	public List<SysOrgElement> getFdManagers() {
		return fdManagers;
	}

	public void setFdManagers(List<SysOrgElement> fdManagers) {
		this.fdManagers = fdManagers;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdManagers",
					new ModelConvertor_ModelListToString(
							"fdManagerIds:fdManagerNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}

	@Override
	public Class getFormClass() {
		return HrRatifyStaffConcernConfigForm.class;
	}

}
