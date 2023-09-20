package com.landray.kmss.sys.organization.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.forms.SysOrgElementExtPropForm;

/**
 * 外部组织扩展属性
 *
 * @author 潘永辉 Mar 16, 2020
 */
public class SysOrgElementExtProp extends BaseModel implements Comparable<SysOrgElementExtProp> {
	/**
	 * 部门
	 */
	public static final String TYPE_DEPT = "dept";
	/**
	 * 人员
	 */
	public static final String TYPE_PERSON = "person";

	/**
	 * 所属扩展
	 */
	protected SysOrgElementExternal fdExternal;

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
	private Integer fdOrder;

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
	private Integer fdFieldLength;

	/**
	 * 精度，适用于浮点类型
	 */
	private Integer fdScale;

	/**
	 * 是否必填
	 */
	private Boolean fdRequired;

	/**
	 * 是否启用，默认：启用
	 */
	private Boolean fdStatus;

	/**
	 * 是否列表展示
	 */
	private Boolean fdShowList;

	// 显示的类型：
	// 字符串：单行文本框(text)、多行文本框(textarea)、单选按钮(radio)、复选框(checkbox)、下拉列表(select)
	// 整数: 单行文本框(text)、单选按钮(radio)、下拉列表(select)
	// 浮点: 单行文本框(text)
	// 日期: 日期时间(datetime)、日期(date)、时间(time)
	private String fdDisplayType;

	/**
	 * 枚举集合
	 */
	private List<SysOrgElementExtPropEnum> fdFieldEnums;

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.put("fdExternal.fdId", "fdExternalId");
			toFormPropertyMap.put("fdFieldEnums", new ModelConvertor_ModelListToFormList("fdFieldEnums"));
		}
		return toFormPropertyMap;
	}

	public SysOrgElementExternal getFdExternal() {
		return fdExternal;
	}

	public void setFdExternal(SysOrgElementExternal fdExternal) {
		this.fdExternal = fdExternal;
	}

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
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

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	public String getFdFieldName() {
		return fdFieldName;
	}

	public void setFdFieldName(String fdFieldName) {
		this.fdFieldName = fdFieldName;
	}

	public String getFdColumnName() {
		if (fdColumnName != null) {
			fdColumnName = fdColumnName.toLowerCase();
		}
		return fdColumnName;
	}

	public void setFdColumnName(String fdColumnName) {
		this.fdColumnName = fdColumnName;
	}

	public String getFdFieldType() {
		if (fdFieldType == null) {
			fdFieldType = "java.lang.String";
		}
		return fdFieldType;
	}

	public void setFdFieldType(String fdFieldType) {
		this.fdFieldType = fdFieldType;
	}

	public Integer getFdFieldLength() {
		if (fdFieldLength == null) {
			fdFieldLength = 0;
		}
		return fdFieldLength;
	}

	public void setFdFieldLength(Integer fdFieldLength) {
		this.fdFieldLength = fdFieldLength;
	}

	public Integer getFdScale() {
		if (fdScale == null) {
			fdScale = 2;
		}
		return fdScale;
	}

	public void setFdScale(Integer fdScale) {
		this.fdScale = fdScale;
	}

	public Boolean getFdRequired() {
		if (fdRequired == null) {
			fdRequired = true;
		}
		return fdRequired;
	}

	public void setFdRequired(Boolean fdRequired) {
		this.fdRequired = fdRequired;
	}

	public Boolean getFdStatus() {
		if (fdStatus == null) {
			fdStatus = true;
		}
		return fdStatus;
	}

	public void setFdStatus(Boolean fdStatus) {
		this.fdStatus = fdStatus;
	}

	public String getFdDisplayType() {
		if (fdDisplayType == null) {
			fdDisplayType = "text";
		}
		return fdDisplayType;
	}

	public Boolean getFdShowList() {
		if (fdShowList == null) {
			fdShowList = false;
		}
		return fdShowList;
	}

	public void setFdShowList(Boolean fdShowList) {
		this.fdShowList = fdShowList;
	}

	public void setFdDisplayType(String fdDisplayType) {
		this.fdDisplayType = fdDisplayType;
	}

	public List<SysOrgElementExtPropEnum> getFdFieldEnums() {
		return fdFieldEnums;
	}

	public void setFdFieldEnums(List<SysOrgElementExtPropEnum> fdFieldEnums) {
		this.fdFieldEnums = fdFieldEnums;
	}

	@Override
	public Class getFormClass() {
		return SysOrgElementExtPropForm.class;
	}

	@Override
	public int compareTo(SysOrgElementExtProp prop) {
		int idx1 = this.getFdOrder() == null ? 0 : this.getFdOrder();
		int idx2 = prop.getFdOrder() == null ? 0 : prop.getFdOrder();
		return idx1 - idx2;
	}

}
