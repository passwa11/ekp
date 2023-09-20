package com.landray.kmss.hr.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationOrg;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 机构信息
  */
public class HrOrganizationOrgForm extends HrOrganizationElementForm {

	/*
	 * 上级领导
	 */
	private String fdSuperLeaderId = null;

	public String getFdSuperLeaderId() {
		return fdSuperLeaderId;
	}

	public void setFdSuperLeaderId(String fdSuperLeaderId) {
		this.fdSuperLeaderId = fdSuperLeaderId;
	}

	private String fdSuperLeaderName = null;

	public String getFdSuperLeaderName() {
		return fdSuperLeaderName;
	}

	public void setFdSuperLeaderName(String fdSuperLeaderName) {
		this.fdSuperLeaderName = fdSuperLeaderName;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdSuperLeaderId = null;
		fdSuperLeaderName = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return HrOrganizationOrg.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdSuperLeaderId",
					new FormConvertor_IDToModel("hbmSuperLeader", HrOrganizationElement.class));
		}
		return toModelPropertyMap;
	}

}
