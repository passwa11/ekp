package com.landray.kmss.sys.portal.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.portal.forms.SysPortalMapTplNavForm;

/**
 * 系统导航复合对象
 * 
 * @author
 *
 */
public class SysPortalMapTplNav extends BaseModel {

	private static final long serialVersionUID = 1L;

	private SysPortalNav fdNav;

	private SysPortalMapTpl fdMain;

	private String fdAttachmentId;

	public SysPortalNav getFdNav() {
		return fdNav;
	}

	public void setFdNav(SysPortalNav fdNav) {
		this.fdNav = fdNav;
	}


	public SysPortalMapTpl getFdMain() {
		return fdMain;
	}

	public void setFdMain(SysPortalMapTpl fdMain) {
		this.fdMain = fdMain;
	}

	public String getFdAttachmentId() {
		return fdAttachmentId;
	}

	public void setFdAttachmentId(String fdAttachmentId) {
		this.fdAttachmentId = fdAttachmentId;
	}

	@Override
	public Class<SysPortalMapTplNavForm> getFormClass() {
		return SysPortalMapTplNavForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.addNoConvertProperty("authReaderFlag");
			toFormPropertyMap.addNoConvertProperty("authEditorFlag");
			toFormPropertyMap.put("fdNav.fdName", "fdNavName");
			toFormPropertyMap.put("fdNav.fdId", "fdNavId");
		}
		return toFormPropertyMap;
	}

}
