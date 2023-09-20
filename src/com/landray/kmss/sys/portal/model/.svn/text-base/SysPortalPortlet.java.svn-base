package com.landray.kmss.sys.portal.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.portal.forms.SysPortalPortletForm;

/**
 * 系统部件
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class SysPortalPortlet extends BaseModel {
	protected String fdDescription;
	protected String fdFormat;

	public String getFdDescription() {
		return fdDescription;
	}

	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
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
	protected String fdName;

	/**
	 * 模块
	 */
	protected String fdModule;
	/**
	 * 数据
	 */
	protected String fdSource;

	public String getFdSource() {
		return fdSource;
	}

	public void setFdSource(String fdSource) {
		this.fdSource = fdSource;
	}

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
    public Class getFormClass() {
		return SysPortalPortletForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}
}
