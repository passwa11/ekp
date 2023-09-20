package com.landray.kmss.sys.organization.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.organization.forms.SysOrgRoleForm;
import com.landray.kmss.util.StringUtil;

public class SysOrgRole extends SysOrgElement {
	public SysOrgRole() {
		super();
		setFdOrgType(new Integer(ORG_TYPE_ROLE));
	}

	/**
	 * 程序名
	 */
	private String fdPlugin;

	public String getFdPlugin() {
		return fdPlugin;
	}

	public void setFdPlugin(String fdPlugin) {
		this.fdPlugin = fdPlugin;
	}

	/**
	 * 参数
	 */
	private String fdParameter;

	public String getFdParameter() {
		return fdParameter;
	}

	public void setFdParameter(String fdParameter) {
		this.fdParameter = fdParameter;
	}

	public String getParameter(String name) {
		return StringUtil.getParameter(fdParameter, name);
	}

	/**
	 * 返回值是否多值
	 */
	private Boolean fdIsMultiple = new Boolean(true);

	public Boolean getFdIsMultiple() {
		if (fdIsMultiple == null) {
            return new Boolean(false);
        }
		return fdIsMultiple;
	}

	public void setFdIsMultiple(Boolean fdIsMultiple) {
		this.fdIsMultiple = fdIsMultiple;
	}

	/**
	 * 返回值
	 */
	private String fdRtnValue;

	public String getFdRtnValue() {
		return fdRtnValue;
	}

	public void setFdRtnValue(String fdRtnValue) {
		this.fdRtnValue = fdRtnValue;
	}

	/**
	 * 所属角色线配置
	 */
	private SysOrgRoleConf fdRoleConf;

	public SysOrgRoleConf getFdRoleConf() {
		return fdRoleConf;
	}

	public void setFdRoleConf(SysOrgRoleConf fdRoleConf) {
		this.fdRoleConf = fdRoleConf;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdRoleConf.fdName", "fdConfName");
			toFormPropertyMap.put("fdRoleConf.fdId", "fdConfId");
		}
		return toFormPropertyMap;
	}
	
	@Override
	public Class getFormClass() {
		return SysOrgRoleForm.class;
	}
}
