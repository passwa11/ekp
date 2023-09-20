package com.landray.kmss.sys.organization.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseTreeModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.forms.SysOrgRoleLineForm;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 创建日期 2008-十一月-21
 * 
 * @author 陈亮 角色线
 */
public class SysOrgRoleLine extends BaseTreeModel {

	/*
	 * 角色线名称
	 */
	protected String fdName;

	/*
	 * 排序号
	 */
	protected Long fdOrder;

	/*
	 * 组织架构
	 */
	protected SysOrgElement sysOrgRoleMember = null;

	/*
	 * 角色线配置
	 */
	protected SysOrgRoleConf sysOrgRoleConf = null;
	/*
	 * 创建时间
	 */
	private Date fdCreateTime = new Date();

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	public SysOrgRoleLine() {
		super();
	}

	/**
	 * @return 返回 角色线名称
	 */
	public String getFdName() {
		return SysLangUtil.getPropValue(this, "fdName", this.fdName);
	}

	/**
	 * @param fdName
	 *            要设置的 角色线名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}

	public String getFdNameOri() {
		return fdName;
	}

	public void setFdNameOri(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * @return 返回 排序号
	 */
	public Long getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            要设置的 排序号
	 */
	public void setFdOrder(Long fdOrder) {
		this.fdOrder = fdOrder;
	}

	/*
	 * 角色线所有下级
	 */
	protected List children = null;

	public List getHbmChildren() {
		return children;
	}

	public void setHbmChildren(List children) {
		this.children = children;
	}

	public SysOrgElement getSysOrgRoleMember() {
		return sysOrgRoleMember;
	}

	public void setSysOrgRoleMember(SysOrgElement sysOrgRoleMember) {
		this.sysOrgRoleMember = sysOrgRoleMember;
	}

	/**
	 * @return 返回 角色线配置
	 */
	public SysOrgRoleConf getSysOrgRoleConf() {
		return sysOrgRoleConf;
	}

	/**
	 * @param sysOrgRoleConf
	 *            要设置的 角色线配置
	 */
	public void setSysOrgRoleConf(SysOrgRoleConf sysOrgRoleConf) {
		this.sysOrgRoleConf = sysOrgRoleConf;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdParent.disName", "fdParentName");
			toFormPropertyMap.put("fdParent.fdId", "fdParentId");
			toFormPropertyMap.put("sysOrgRoleMember.fdOrgType", "fdMemberType");
			toFormPropertyMap.put("sysOrgRoleMember.fdName", "fdMemberName");
			toFormPropertyMap.put("sysOrgRoleMember.fdId", "fdMemberId");
			toFormPropertyMap.put("sysOrgRoleConf.fdId", "fdConfId");
			toFormPropertyMap.put("hbmChildren.empty", "fdNotHasChildren");
		}
		return toFormPropertyMap;
	}

	@Override
	public Class getFormClass() {
		return SysOrgRoleLineForm.class;
	}

	public String getDisName() {
		if (sysOrgRoleMember != null) {
			String disName = sysOrgRoleMember.getFdName();
			if (sysOrgRoleMember.getFdIsAvailable() != null && !sysOrgRoleMember.getFdIsAvailable()) // 无效
            {
                disName += "(" + ResourceUtil.getString("sysOrg.address.info.disable", "sys-organization") + ")";
            }
			if (StringUtil.isNotNull(fdName)) {
                disName = fdName + "(" + disName + ")";
            }

			return disName;
		} else {
			return getFdName();
		}
	}

	/*
	 * 更新时间
	 */
	private Date fdAlterTime = new Date();

	public void setFdAlterTime(Date fdAlterTime) {
		this.fdAlterTime = fdAlterTime;
	}

	public Date getFdAlterTime() {
		return fdAlterTime;
	}
	
	private Boolean fdHasChild;

	public Boolean getFdHasChild() {
		if (fdHasChild == null) {
            fdHasChild = Boolean.TRUE;
        }
		return fdHasChild;
	}

	public void setFdHasChild(Boolean fdHasChild) {
		this.fdHasChild = fdHasChild;
	}
}
