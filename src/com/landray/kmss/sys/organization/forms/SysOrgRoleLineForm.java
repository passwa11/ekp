package com.landray.kmss.sys.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgRoleConf;
import com.landray.kmss.sys.organization.model.SysOrgRoleLine;

/**
 * 创建日期 2008-十一月-21
 * 
 * @author 陈亮
 */
public class SysOrgRoleLineForm extends ExtendForm {
	/*
	 * 角色线名称
	 */
	private String fdName = null;

	/*
	 * 成员名称
	 */
	private String fdMemberId = null;
	/*
	 * 成员名称
	 */
	private String fdMemberName = null;
	/*
	 * 成员名称
	 */
	private String fdMemberType = null;	

	/*
	 * 排序号
	 */
	private String fdOrder = null;

	/*
	 * 上级名称ID
	 */
	private String fdParentId = null;

	/*
	 * 上级名称
	 */
	private String fdParentName = null;

	/*
	 * 所属角色线配置
	 */
	private String fdConfId = null;

	/*
	 * 是否有子节点
	 */
	private String fdNotHasChildren = null;

	/**
	 * @return 返回 角色线名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            要设置的 角色线名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * @return 返回 成员名称
	 */
	public String getFdMemberId() {
		return fdMemberId;
	}

	/**
	 * @param fdMemberId
	 *            要设置的 成员名称
	 */
	public void setFdMemberId(String fdMemberId) {
		this.fdMemberId = fdMemberId;
	}

	/**
	 * @return 返回 排序号
	 */
	public String getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            要设置的 排序号
	 */
	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	public String getFdMemberName() {
		return fdMemberName;
	}

	public void setFdMemberName(String fdMemberName) {
		this.fdMemberName = fdMemberName;
	}

	public String getFdConfId() {
		return fdConfId;
	}

	public void setFdConfId(String fdConfId) {
		this.fdConfId = fdConfId;
	}

	public String getFdParentId() {
		return fdParentId;
	}

	public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}

	public String getFdParentName() {
		return fdParentName;
	}

	public void setFdParentName(String fdParentName) {
		this.fdParentName = fdParentName;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @seecom.landray.kmss.web.action.ActionForm#reset(org.apache.struts.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdMemberId = null;
		fdOrder = null;
		// fdParentId = null;
		// fdParentName = null;
		fdMemberId = null;
		fdMemberName = null;
		fdMemberType = null;
		// fdConfId = null;
		fdNotHasChildren = null;
		fdHasChild = "true";
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel(
					"fdParent", SysOrgRoleLine.class));
			toModelPropertyMap.put("fdMemberId", new FormConvertor_IDToModel(
					"sysOrgRoleMember", SysOrgElement.class));
			toModelPropertyMap.put("fdConfId", new FormConvertor_IDToModel(
					"sysOrgRoleConf", SysOrgRoleConf.class));
		}
		return toModelPropertyMap;
	}

	@Override
    public Class getModelClass() {
		return SysOrgRoleLine.class;
	}

	public String getFdNotHasChildren() {
		return fdNotHasChildren;
	}

	public void setFdNotHasChildren(String fdHasChildren) {
		this.fdNotHasChildren = fdHasChildren;
	}
	
	private String fdHasChild = "true";

	public String getFdHasChild() {
		return fdHasChild;
	}

	public void setFdHasChild(String fdHasChild) {
		this.fdHasChild = fdHasChild;
	}

	public String getFdMemberType() {
		return fdMemberType;
	}

	public void setFdMemberType(String fdMemberType) {
		this.fdMemberType = fdMemberType;
	}
}
