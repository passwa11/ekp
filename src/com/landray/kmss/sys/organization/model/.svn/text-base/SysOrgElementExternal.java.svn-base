package com.landray.kmss.sys.organization.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelConvertor_ModelToForm;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.forms.SysOrgElementExternalForm;

/**
 * 外部组织扩展
 *
 * @author 潘永辉 Mar 16, 2020
 */
public class SysOrgElementExternal extends BaseModel {
	/**
	 * 所属组织(对应机构)
	 */
	private SysOrgOrg fdElement;

	/**
	 * 部门扩展动态表名
	 */
	private String fdDeptTable;

	/**
	 * 部门扩展属性集合
	 */
	private List<SysOrgElementExtProp> fdDeptProps;

	/**
	 * 人员扩展动态表名
	 */
	private String fdPersonTable;

	/**
	 * 人员扩展属性集合
	 */
	private List<SysOrgElementExtProp> fdPersonProps;

	/**
	 * 外部组织可使用人员
	 */
	protected List<SysOrgElement> authReaders;

	@Override
	public Class getFormClass() {
		return SysOrgElementExternalForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.put("fdElement", new ModelConvertor_ModelToForm("fdElement"));
			toFormPropertyMap.put("fdDeptProps", new ModelConvertor_ModelListToFormList("fdDeptProps"));
			toFormPropertyMap.put("fdPersonProps", new ModelConvertor_ModelListToFormList("fdPersonProps"));
			toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}

	public SysOrgOrg getFdElement() {
		return fdElement;
	}

	public void setFdElement(SysOrgOrg fdElement) {
		this.fdElement = fdElement;
	}

	public String getFdDeptTable() {
		return fdDeptTable;
	}

	public void setFdDeptTable(String fdDeptTable) {
		this.fdDeptTable = fdDeptTable;
	}

	public String getFdPersonTable() {
		return fdPersonTable;
	}

	public void setFdPersonTable(String fdPersonTable) {
		this.fdPersonTable = fdPersonTable;
	}

	public List<SysOrgElementExtProp> getFdDeptProps() {
		return fdDeptProps;
	}

	public void setFdDeptProps(List<SysOrgElementExtProp> fdDeptProps) {
		this.fdDeptProps = fdDeptProps;
	}

	public List<SysOrgElementExtProp> getFdPersonProps() {
		return fdPersonProps;
	}

	public void setFdPersonProps(List<SysOrgElementExtProp> fdPersonProps) {
		this.fdPersonProps = fdPersonProps;
	}

	public List<SysOrgElement> getAuthReaders() {
		return authReaders;
	}

	public void setAuthReaders(List<SysOrgElement> authReaders) {
		this.authReaders = authReaders;
	}

}
