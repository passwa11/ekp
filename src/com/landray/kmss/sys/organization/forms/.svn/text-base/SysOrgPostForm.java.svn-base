package com.landray.kmss.sys.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.web.action.ActionMapping;

/**
 * Form bean for a Struts application.
 * 
 * @author 叶中奇
 */
public class SysOrgPostForm extends SysOrgElementForm {
	/*
	 * 个人列表
	 */
	private String fdPersonIds;
	
	private String fdOldPersonIds;

	private String fdPersonNames;

	public String getFdPersonIds() {
		return fdPersonIds;
	}

	public void setFdPersonIds(String fdPersonIds) {
		this.fdPersonIds = fdPersonIds;
	}

	public String getFdPersonNames() {
		return fdPersonNames;
	}

	public void setFdPersonNames(String fdPersonNames) {
		this.fdPersonNames = fdPersonNames;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdPersonIds = null;
		fdPersonNames = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysOrgPost.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdPersonIds",
					new FormConvertor_IDsToModelList("fdPersons",
							SysOrgPerson.class));
		}
		return toModelPropertyMap;
	}

	public String getFdOldPersonIds() {
		return fdOldPersonIds;
	}

	public void setFdOldPersonIds(String fdOldPersonIds) {
		this.fdOldPersonIds = fdOldPersonIds;
	}
}
