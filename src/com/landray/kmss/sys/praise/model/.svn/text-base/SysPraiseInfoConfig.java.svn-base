package com.landray.kmss.sys.praise.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.praise.forms.SysPraiseInfoConfigForm;

public class SysPraiseInfoConfig extends BaseModel {

	@Override
	public Class getFormClass() {
		return SysPraiseInfoConfigForm.class;
	}

	/**
	 * 模块路径
	 */
	private String fdModuleUrlPrefix;

	/**
	 * 模块key值（用于获取模块名称）
	 */
	private String fdModuleKey;

	/**
	 * 是否开启
	 */
	private String fdIsUsed;

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

	 

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}
}
