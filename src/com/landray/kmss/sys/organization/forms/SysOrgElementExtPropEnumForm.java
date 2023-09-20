package com.landray.kmss.sys.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElementExtProp;
import com.landray.kmss.sys.organization.model.SysOrgElementExtPropEnum;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 外部组织扩展属性
 *
 * @author 潘永辉 Mar 16, 2020
 */
public class SysOrgElementExtPropEnumForm extends ExtendForm {
	private static final long serialVersionUID = 1L;

	/**
	 * 所属属性
	 */
	private String fdExtPropId;
	private String fdExtPropName;

	/**
	 * 排序号
	 */
	private String fdOrder;

	/**
	 * 显示名称
	 */
	private String fdName;

	/**
	 * 枚举值
	 */
	private String fdValue;


	@Override
	public Class<?> getModelClass() {
		return SysOrgElementExtPropEnum.class;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdExtPropId = null;
		fdExtPropName = null;
		fdValue = null;
		fdName = null;
		fdOrder = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.put("fdExtPropId", new FormConvertor_IDToModel("fdExtProp", SysOrgElementExtProp.class));
		}
		return toModelPropertyMap;
	}

	public String getFdExtPropId() {
		return fdExtPropId;
	}

	public void setFdExtPropId(String fdExtPropId) {
		this.fdExtPropId = fdExtPropId;
	}

	public String getFdExtPropName() {
		return fdExtPropName;
	}

	public void setFdExtPropName(String fdExtPropName) {
		this.fdExtPropName = fdExtPropName;
	}

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdValue() {
		return fdValue;
	}

	public void setFdValue(String fdValue) {
		this.fdValue = fdValue;
	}

}
