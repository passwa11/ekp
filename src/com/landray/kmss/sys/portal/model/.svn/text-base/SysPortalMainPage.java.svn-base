package com.landray.kmss.sys.portal.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.portal.forms.SysPortalMainPageForm;

public class SysPortalMainPage extends BaseModel implements IBaseModel {
	protected SysPortalMain sysPortalMain;
	protected SysPortalPage sysPortalPage;
	protected String fdName;
	protected Integer fdOrder;
	protected String fdIcon;
	protected String fdImg;
	protected boolean fdEnabled;
	protected String fdTarget;

	public String getFdTarget() {
		return fdTarget;
	}

	public void setFdTarget(String fdTarget) {
		this.fdTarget = fdTarget;
	}

	public String getFdIcon() {
		return fdIcon;
	}

	public void setFdIcon(String fdIcon) {
		this.fdIcon = fdIcon;
	}

	public String getFdImg() { return fdImg; }

	public void setFdImg(String fdImg) { this.fdImg = fdImg; }

	public boolean getFdEnabled() {
		return fdEnabled;
	}

	public void setFdEnabled(boolean fdEnabled) {
		this.fdEnabled = fdEnabled;
	}

	public SysPortalMain getSysPortalMain() {
		return sysPortalMain;
	}

	public void setSysPortalMain(SysPortalMain sysPortalMain) {
		this.sysPortalMain = sysPortalMain;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	public SysPortalPage getSysPortalPage() {
		return sysPortalPage;
	}

	public void setSysPortalPage(SysPortalPage sysPortalPage) {
		this.sysPortalPage = sysPortalPage;
	}

	@Override
    public Class getFormClass() {
		return SysPortalMainPageForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("sysPortalMain.fdId", "sysPortalMainId");
			toFormPropertyMap.put("sysPortalMain.fdName", "sysPortalMainName");
			toFormPropertyMap.put("sysPortalPage.fdId", "sysPortalPageId");
			toFormPropertyMap.put("sysPortalPage.fdName", "sysPortalPageName");
			toFormPropertyMap.put("sysPortalPage.fdType", "fdType");
		}
		return toFormPropertyMap;
	}
}
