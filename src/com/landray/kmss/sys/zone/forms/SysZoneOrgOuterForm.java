package com.landray.kmss.sys.zone.forms;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.zone.model.SysZoneOrgRelation;

@SuppressWarnings("rawtypes")
public class SysZoneOrgOuterForm extends ExtendForm {

	private static final long serialVersionUID = 5673104367381034504L;

	@Override
	public Class getModelClass() {
		return SysZoneOrgRelation.class;
	}

	private String fdOrgId;
	private String fdOrgType;
	private String fdCategoryId;
	private String fdOrgName;
	private String fdOrgMemo;
	private String fdOrder;

	public String getFdOrgId() {
		return fdOrgId;
	}

	public void setFdOrgId(String fdOrgId) {
		this.fdOrgId = fdOrgId;
	}

	public String getFdOrgType() {
		return fdOrgType;
	}

	public void setFdOrgType(String fdOrgType) {
		this.fdOrgType = fdOrgType;
	}

	public String getFdCategoryId() {
		return fdCategoryId;
	}

	public void setFdCategoryId(String fdCategoryId) {
		this.fdCategoryId = fdCategoryId;
	}

	public String getFdOrgName() {
		return fdOrgName;
	}

	public void setFdOrgName(String fdOrgName) {
		this.fdOrgName = fdOrgName;
	}

	public String getFdOrgMemo() {
		return fdOrgMemo;
	}

	public void setFdOrgMemo(String fdOrgMemo) {
		this.fdOrgMemo = fdOrgMemo;
	}

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

}
