package com.landray.kmss.sys.praise.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.praise.model.SysPraiseInfoConfig;

public class SysPraiseInfoConfigForm extends ExtendForm {

	@Override
	public Class getModelClass() {
		return SysPraiseInfoConfig.class;
	}

	/**
	 * 模块路径
	 */
	private String fdModuleUrlPrefix = null;

	/**
	 * 模块key值（用于获取模块名称）
	 */
	private String fdModuleKey = null;

	/**
	 * 是否开启
	 */
	private String fdIsUsed = null;

	public String getFdIsUsed() {
		return fdIsUsed;
	}

	public void setFdIsUsed(String fdIsUsed) {
		this.fdIsUsed = fdIsUsed;
	}

	public String getFdModuleUrlPrefix() {
		return fdModuleUrlPrefix;
	}

	public void setFdModuleUrlPrefix(String fdModuleUrlPrefix) {
		this.fdModuleUrlPrefix = fdModuleUrlPrefix;
	}

	public String getFdModuleKey() {
		return fdModuleKey;
	}

	public void setFdModuleKey(String fdModuleKey) {
		this.fdModuleKey = fdModuleKey;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdModuleUrlPrefix = null;
		fdIsUsed = null;
		fdModuleKey = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}
}
