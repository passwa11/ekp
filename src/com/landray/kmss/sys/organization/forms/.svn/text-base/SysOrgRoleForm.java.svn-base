package com.landray.kmss.sys.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.organization.model.SysOrgRole;
import com.landray.kmss.sys.organization.model.SysOrgRoleConf;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 创建日期 2008-十一月-21
 * 
 * @author 陈亮
 */
public class SysOrgRoleForm extends SysOrgElementForm {
	/*
	 * 所属角色线配置
	 */
	private String fdConfId = null;
	private String fdConfName = null;
	/*
	 * 程序名
	 */
	private String fdPlugin = null;
	/*
	 * 是否多选
	 */
	private String fdIsMultiple = null;

	/*
	 * 参数
	 */
	private String fdParameter = null;
	/*
	 * 返回值
	 */
	private String fdRtnValue = null;

	public String getFdConfId() {
		return fdConfId;
	}

	public void setFdConfId(String fdConfId) {
		this.fdConfId = fdConfId;
	}

	public String getFdConfName() {
		return fdConfName;
	}

	public void setFdConfName(String fdConfName) {
		this.fdConfName = fdConfName;
	}

	/**
	 * @return 返回 程序名
	 */
	public String getFdPlugin() {
		return fdPlugin;
	}

	/**
	 * @param fdPlugin
	 *            要设置的 程序名
	 */
	public void setFdPlugin(String fdPlugin) {
		this.fdPlugin = fdPlugin;
	}

	/**
	 * @return 返回 是否多选
	 */
	public String getFdIsMultiple() {
		return fdIsMultiple;
	}

	/**
	 * @param fdIsMultiple
	 *            要设置的 是否多选
	 */
	public void setFdIsMultiple(String fdIsMultiple) {
		this.fdIsMultiple = fdIsMultiple;
	}

	/**
	 * @return 返回 参数
	 */
	public String getFdParameter() {
		return fdParameter;
	}

	/**
	 * @param fdParameter
	 *            要设置的 参数
	 */
	public void setFdParameter(String fdParameter) {
		this.fdParameter = fdParameter;
	}

	/**
	 * @return 返回 返回值
	 */
	public String getFdRtnValue() {
		return fdRtnValue;
	}

	/**
	 * @param fdRtnValue
	 *            要设置的 返回值
	 */
	public void setFdRtnValue(String fdRtnValue) {
		this.fdRtnValue = fdRtnValue;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @seecom.landray.kmss.web.action.ActionForm#reset(org.apache.struts.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdConfId = null;
		fdConfName = null;
		fdPlugin = null;
		fdIsMultiple = "false";
		fdParameter = null;
		fdRtnValue = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdConfId", new FormConvertor_IDToModel(
					"fdRoleConf", SysOrgRoleConf.class));
		}
		return toModelPropertyMap;
	}

	@Override
    public Class getModelClass() {
		return SysOrgRole.class;
	}
}
