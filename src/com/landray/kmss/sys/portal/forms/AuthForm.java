package com.landray.kmss.sys.portal.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;

public abstract class AuthForm extends ExtendForm {

	/*
	 * 可阅读者
	 */
	protected String authReaderIds = null;

	public String getAuthReaderIds() {
		return authReaderIds;
	}

	public void setAuthReaderIds(String authReaderIds) {
		this.authReaderIds = authReaderIds;
	}

	/*
	 * 可阅读者名称
	 */
	protected String authReaderNames = null;

	public String getAuthReaderNames() {
		return authReaderNames;
	}

	public void setAuthReaderNames(String authReaderNames) {
		this.authReaderNames = authReaderNames;
	}

	/*
	 * 可编辑者
	 */
	protected String authEditorIds = null;

	public String getAuthEditorIds() {
		return authEditorIds;
	}

	public void setAuthEditorIds(String authEditorIds) {
		this.authEditorIds = authEditorIds;
	}

	/*
	 * 可编辑者名称
	 */
	protected String authEditorNames = null;

	public String getAuthEditorNames() {
		return authEditorNames;
	}

	public void setAuthEditorNames(String authEditorNames) {
		this.authEditorNames = authEditorNames;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {

		authReaderIds = null;
		authReaderNames = null;
		authEditorIds = null;
		authEditorNames = null;

		super.reset(mapping, request);
	}

	private FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("authReaderIds",
					new FormConvertor_IDsToModelList("authReaders",
							SysOrgElement.class));
			toModelPropertyMap.put("authEditorIds",
					new FormConvertor_IDsToModelList("authEditors",
							SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

}
