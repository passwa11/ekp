package com.landray.kmss.sys.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgGroupCate;

/**
 * 群组类别
 * 
 * @author 叶中奇
 */
public class SysOrgGroupCateForm extends ExtendForm {
	/*
	 * 名称
	 */
	private String fdName;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/*
	 * 父类别
	 */
	private String fdParentId;

	private String fdParentName;

	public String getFdParentId() {
		return fdParentId;
	}

	public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}

	public String getFdParentName() {
		return fdParentName;
	}

	public void setFdParentName(String fdParentName) {
		this.fdParentName = fdParentName;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdParentId = null;
		fdParentName = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysOrgGroupCate.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel(
					"fdParent", SysOrgGroupCate.class));
		}
		return toModelPropertyMap;
	}
}
