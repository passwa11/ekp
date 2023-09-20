package com.landray.kmss.sys.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgRoleConf;
import com.landray.kmss.sys.organization.model.SysOrgRoleLineDefaultRole;

/**
 * 创建日期 2008-十一月-27
 * 
 * @author 叶中奇
 */
public class SysOrgRoleLineDefaultRoleForm extends ExtendForm {
	/*
	 * 所属角色线配置
	 */
	private String fdRoleLineConfId = null;
	/*
	 * 人员
	 */
	private String fdPersonId = null;
	private String fdPersonName = null;
	/*
	 * 默认岗位
	 */
	private String fdPostId = null;
	private String fdPostName = null;
	/*
	 * 岗位集合
	 */
	private String fdPostIds = null;
	private String fdPostNames = null;

	public String getFdPostIds() {
		return fdPostIds;
	}

	public void setFdPostIds(String fdPostIds) {
		this.fdPostIds = fdPostIds;
	}

	public String getFdPostNames() {
		return fdPostNames;
	}

	public void setFdPostNames(String fdPostNames) {
		this.fdPostNames = fdPostNames;
	}

	public String getFdPersonId() {
		return fdPersonId;
	}

	public void setFdPersonId(String fdPersonId) {
		this.fdPersonId = fdPersonId;
	}

	public String getFdPersonName() {
		return fdPersonName;
	}

	public void setFdPersonName(String fdPersonName) {
		this.fdPersonName = fdPersonName;
	}

	public String getFdPostId() {
		return fdPostId;
	}

	public void setFdPostId(String fdPostId) {
		this.fdPostId = fdPostId;
	}

	public String getFdPostName() {
		return fdPostName;
	}

	public void setFdPostName(String fdPostName) {
		this.fdPostName = fdPostName;
	}

	/**
	 * @return 返回 所属角色线配置
	 */
	public String getFdRoleLineConfId() {
		return fdRoleLineConfId;
	}

	/**
	 * @param fdRoleLineConfId
	 *            要设置的 所属角色线配置
	 */
	public void setFdRoleLineConfId(String fdRoleLineConfId) {
		this.fdRoleLineConfId = fdRoleLineConfId;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @seecom.landray.kmss.web.action.ActionForm#reset(org.apache.struts.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdRoleLineConfId = null;
		fdPostId = null;
		fdPostName = null;
		fdPersonId = null;
		fdPersonName = null;
		fdPostIds = null;
		fdPostNames = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdPersonId", new FormConvertor_IDToModel(
					"fdPerson", SysOrgElement.class));
			toModelPropertyMap.put("fdPostId", new FormConvertor_IDToModel(
					"fdPost", SysOrgElement.class));
			toModelPropertyMap.put("fdRoleLineConfId",
					new FormConvertor_IDToModel("sysOrgRoleConf",
							SysOrgRoleConf.class));
		}
		return toModelPropertyMap;
	}

	@Override
    public Class getModelClass() {
		return SysOrgRoleLineDefaultRole.class;
	}
}
