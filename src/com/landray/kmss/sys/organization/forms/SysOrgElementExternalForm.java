package com.landray.kmss.sys.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_FormToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgElementExternal;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 外部组织扩展
 *
 * @author 潘永辉 Mar 16, 2020
 */
public class SysOrgElementExternalForm extends ExtendForm {
	private static final long serialVersionUID = 1L;

	/**
	 * 所属组织
	 */
	private SysOrgOrgForm fdElement;

	/**
	 * 部门扩展动态表名
	 */
	private String fdDeptTable;

	/**
	 * 部门扩展属性集合
	 */
	private AutoArrayList fdDeptProps = new AutoArrayList(SysOrgElementExtPropForm.class);

	/**
	 * 人员扩展动态表名
	 */
	private String fdPersonTable;

	/**
	 * 人员扩展属性集合
	 */
	private AutoArrayList fdPersonProps = new AutoArrayList(SysOrgElementExtPropForm.class);

	/**
	 * 外部组织可使用人员
	 */
	protected String authReaderIds;
	protected String authReaderNames;

	@Override
	public Class<?> getModelClass() {
		return SysOrgElementExternal.class;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdDeptTable = null;
		fdPersonTable = null;
		authReaderIds = null;
		authReaderNames = null;
		fdElement = new SysOrgOrgForm();
		// 默认开启查看范围
		fdElement.setFdRange(new SysOrgElementRangeForm());
		fdElement.getFdRange().setFdIsOpenLimit("true");
		fdElement.getFdRange().setFdViewType("1");
		// 默认开启隐藏属性
		fdElement.setFdHideRange(new SysOrgElementHideRangeForm());
		fdElement.getFdHideRange().setFdIsOpenLimit("true");
		fdElement.getFdHideRange().setFdViewType("0");
		fdDeptProps.clear();
		fdPersonProps.clear();
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.put("fdElement", new FormConvertor_FormToModel("fdElement"));
			toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
			toModelPropertyMap.put("fdDeptProps", new FormConvertor_FormListToModelList("fdDeptProps", "fdExternal"));
			toModelPropertyMap.put("fdPersonProps", new FormConvertor_FormListToModelList("fdPersonProps", "fdExternal"));
		}
		return toModelPropertyMap;
	}

	public SysOrgOrgForm getFdElement() {
		return fdElement;
	}

	public void setFdElement(SysOrgOrgForm fdElement) {
		this.fdElement = fdElement;
	}

	public String getFdDeptTable() {
		return fdDeptTable;
	}

	public void setFdDeptTable(String fdDeptTable) {
		this.fdDeptTable = fdDeptTable;
	}

	public AutoArrayList getFdDeptProps() {
		return fdDeptProps;
	}

	public void setFdDeptProps(AutoArrayList fdDeptProps) {
		this.fdDeptProps = fdDeptProps;
	}

	public String getFdPersonTable() {
		return fdPersonTable;
	}

	public void setFdPersonTable(String fdPersonTable) {
		this.fdPersonTable = fdPersonTable;
	}

	public AutoArrayList getFdPersonProps() {
		return fdPersonProps;
	}

	public void setFdPersonProps(AutoArrayList fdPersonProps) {
		this.fdPersonProps = fdPersonProps;
	}

	public String getAuthReaderIds() {
		return authReaderIds;
	}

	public void setAuthReaderIds(String authReaderIds) {
		this.authReaderIds = authReaderIds;
	}

	public String getAuthReaderNames() {
		return authReaderNames;
	}

	public void setAuthReaderNames(String authReaderNames) {
		this.authReaderNames = authReaderNames;
	}

}
