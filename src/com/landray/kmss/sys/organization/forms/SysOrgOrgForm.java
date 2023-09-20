package com.landray.kmss.sys.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgOrg;

/**
 * Form bean for a Struts application.
 * 
 * @author 叶中奇
 */
public class SysOrgOrgForm extends SysOrgElementForm {

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
		authElementAdminIds = null;
		authElementAdminNames = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysOrgOrg.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdSuperLeaderId",
					new FormConvertor_IDToModel("hbmSuperLeader",
							SysOrgElement.class));
			toModelPropertyMap.put("authElementAdminIds",
					new FormConvertor_IDsToModelList("authElementAdmins",
							SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

	/*
	 * 岗位名称关联机构名称
	 */
	private String fdIsRelation;

	public String getFdIsRelation() {
		return fdIsRelation;
	}

	public void setFdIsRelation(String fdIsRelation) {
		this.fdIsRelation = fdIsRelation;
	}
}
