package com.landray.kmss.sys.portal.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.portal.model.SysPortalMain;
import com.landray.kmss.sys.portal.model.SysPortalMainPage;
import com.landray.kmss.sys.portal.model.SysPortalPage;

public class SysPortalMainPageForm extends ExtendForm {
	protected String sysPortalMainId;
	protected String sysPortalMainName;
	protected String sysPortalPageId;
	protected String sysPortalPageName;
	protected String fdName;
	protected String fdOrder;
	protected String fdType;
	protected String fdIcon;
	protected String fdImg;
	protected String fdEnabled;
	protected String fdTarget;

	public String getSysPortalMainName() {
		return sysPortalMainName;
	}

	public void setSysPortalMainName(String sysPortalMainName) {
		this.sysPortalMainName = sysPortalMainName;
	}

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

	public String getFdEnabled() {
		return fdEnabled;
	}

	public void setFdEnabled(String fdEnabled) {
		this.fdEnabled = fdEnabled;
	}

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public String getSysPortalMainId() {
		return sysPortalMainId;
	}

	public void setSysPortalMainId(String sysPortalMainId) {
		this.sysPortalMainId = sysPortalMainId;
	}

	public String getSysPortalPageId() {
		return sysPortalPageId;
	}

	public void setSysPortalPageId(String sysPortalPageId) {
		this.sysPortalPageId = sysPortalPageId;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	public String getSysPortalPageName() {
		return sysPortalPageName;
	}

	public void setSysPortalPageName(String sysPortalPageName) {
		this.sysPortalPageName = sysPortalPageName;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdOrder = null;
		fdEnabled = null;
		fdIcon = null;
		fdImg = null;
		fdName = null;
		sysPortalMainId = null;
		sysPortalPageId = null;
		fdTarget = null;
		sysPortalMainName = null;
		sysPortalPageName = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysPortalMainPage.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("sysPortalMainId",
					new FormConvertor_IDToModel("sysPortalMain",
							SysPortalMain.class));

			toModelPropertyMap.put("sysPortalPageId",
					new FormConvertor_IDToModel("sysPortalPage",
							SysPortalPage.class));
		}
		return toModelPropertyMap;
	}
}
