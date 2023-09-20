package com.landray.kmss.sys.organization.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.forms.SysOrgElementExtPropEnumForm;

/**
 * 外部组织扩展属性枚举
 *
 * @author 潘永辉 Mar 16, 2020
 */
public class SysOrgElementExtPropEnum extends BaseModel {

	/**
	 * 所属属性
	 */
	protected SysOrgElementExtProp fdExtProp;

	/**
	 * 排序号
	 */
	private Integer fdOrder;

	/**
	 * 显示名称
	 */
	private String fdName;

	/**
	 * 枚举值
	 */
	private String fdValue;

	@Override
	public Class getFormClass() {
		return SysOrgElementExtPropEnumForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.put("fdExtProp.fdId", "fdExtPropId");
			toFormPropertyMap.put("fdExtProp.fdName", "fdExtPropName");
		}
		return toFormPropertyMap;
	}

	public SysOrgElementExtProp getFdExtProp() {
		return fdExtProp;
	}

	public void setFdExtProp(SysOrgElementExtProp fdExtProp) {
		this.fdExtProp = fdExtProp;
	}

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	public String getFdName() {
		return SysLangUtil.getPropValue(this, "fdName", fdName);
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}

	public String getFdNameOri() {
		return fdName;
	}

	public String getFdValue() {
		return fdValue;
	}

	public void setFdValue(String fdValue) {
		this.fdValue = fdValue;
	}

}
