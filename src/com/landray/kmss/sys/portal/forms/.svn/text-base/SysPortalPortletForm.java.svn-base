package com.landray.kmss.sys.portal.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.portal.model.SysPortalPortlet;

/**
 * 系统部件 Form
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class SysPortalPortletForm extends ExtendForm {

	protected String fdFormat = null;
	protected String fdSource = null;

	public String getFdSource() {
		return fdSource;
	}

	public void setFdSource(String fdSource) {
		this.fdSource = fdSource;
	}

	public String getFdFormat() {
		return fdFormat;
	}

	public void setFdFormat(String fdFormat) {
		this.fdFormat = fdFormat;
	}

	/**
	 * 名称
	 */
	protected String fdName = null;

	/**
	 * @return 名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdModule = null;
		fdFormat = null;
		fdSource = null;
		super.reset(mapping, request);
	}

	protected String fdModule;

	public String getFdModule() {
		return fdModule;
	}

	public void setFdModule(String fdModule) {
		this.fdModule = fdModule;
	}
	
	private Boolean fdAnonymous = Boolean.FALSE;
	
	public Boolean getFdAnonymous() {
		return fdAnonymous;
	}

	public void setFdAnonymous(Boolean fdAnonymous) {
		this.fdAnonymous = fdAnonymous;
	}

	@Override
    public Class getModelClass() {
		return SysPortalPortlet.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}
}
