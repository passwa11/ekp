package com.landray.kmss.sys.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElementExtProp;
import com.landray.kmss.sys.organization.model.SysOrgElementExternal;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 外部组织扩展属性
 *
 * @author 潘永辉 Mar 16, 2020
 */
public class SysOrgElementExtPropForm extends ExtendForm {
	private static final long serialVersionUID = 1L;

	/**
	 * 所属扩展
	 */
	private String fdExternalId;

	/**
	 * 属性类型（部门/人员）(dept/person)
	 */
	private String fdType;

	/**
	 * 显示名称
	 */
	private String fdName;

	/**
	 * 排序号
	 */
	private String fdOrder;

	/**
	 * 属性名称
	 */
	private String fdFieldName;

	/**
	 * 字段名称
	 */
	private String fdColumnName;

	/**
	 * 对应数据类型：字符串(java.lang.String)、整数(java.lang.Integer)、浮点(java.lang.Double)、日期(java.util.Date)
	 */
	private String fdFieldType;

	/**
	 * 字段长度
	 */
	private String fdFieldLength;

	/**
	 * 精度，适用于浮点类型
	 */
	private String fdScale;

	/**
	 * 是否必填
	 */
	private String fdRequired;

	/**
	 * 是否启用，默认：启用
	 */
	private String fdStatus;

	/**
	 * 是否列表展示
	 */
	private String fdShowList;

	// 显示的类型：
	// 字符串：单行文本框(text)、多行文本框(textarea)、单选按钮(radio)、复选框(checkbox)、下拉列表(select)
	// 整数: 单行文本框(text)、单选按钮(radio)、下拉列表(select)
	// 浮点: 单行文本框(text)
	// 日期: 日期时间(datetime)、日期(date)、时间(time)
	private String fdDisplayType;

	/**
	 * 枚举集合
	 */
	private AutoArrayList fdFieldEnums = new AutoArrayList(SysOrgElementExtPropEnumForm.class);

	@Override
	public Class<?> getModelClass() {
		return SysOrgElementExtProp.class;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdExternalId = null;
		fdType = null;
		fdName = null;
		fdOrder = null;
		fdFieldName = null;
		fdColumnName = null;
		fdFieldType = null;
		fdFieldLength = null;
		fdScale = null;
		fdRequired = null;
		fdStatus = null;
		fdShowList = null;
		fdDisplayType = null;
		fdFieldEnums.clear();
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.put("fdExternalId", new FormConvertor_IDToModel("fdExternal", SysOrgElementExternal.class));
			toModelPropertyMap.put("fdFieldEnums", new FormConvertor_FormListToModelList("fdFieldEnums", "fdExtProp"));
		}
		return toModelPropertyMap;
	}

	public String getFdExternalId() {
		return fdExternalId;
	}

	public void setFdExternalId(String fdExternalId) {
		this.fdExternalId = fdExternalId;
	}

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	public String getFdFieldName() {
		return fdFieldName;
	}

	public void setFdFieldName(String fdFieldName) {
		this.fdFieldName = fdFieldName;
	}

	public String getFdColumnName() {
		return fdColumnName;
	}

	public void setFdColumnName(String fdColumnName) {
		this.fdColumnName = fdColumnName;
	}

	public String getFdFieldType() {
		return fdFieldType;
	}

	public void setFdFieldType(String fdFieldType) {
		this.fdFieldType = fdFieldType;
	}

	public String getFdFieldLength() {
		return fdFieldLength;
	}

	public void setFdFieldLength(String fdFieldLength) {
		this.fdFieldLength = fdFieldLength;
	}

	public String getFdScale() {
		return fdScale;
	}

	public void setFdScale(String fdScale) {
		this.fdScale = fdScale;
	}

	public String getFdRequired() {
		return fdRequired;
	}

	public void setFdRequired(String fdRequired) {
		this.fdRequired = fdRequired;
	}

	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	public String getFdShowList() {
		return fdShowList;
	}

	public void setFdShowList(String fdShowList) {
		this.fdShowList = fdShowList;
	}

	public String getFdDisplayType() {
		return fdDisplayType;
	}

	public void setFdDisplayType(String fdDisplayType) {
		this.fdDisplayType = fdDisplayType;
	}

	public AutoArrayList getFdFieldEnums() {
		return fdFieldEnums;
	}

	public void setFdFieldEnums(AutoArrayList fdFieldEnums) {
		this.fdFieldEnums = fdFieldEnums;
	}

}
