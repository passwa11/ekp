package com.landray.kmss.sys.portal.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.portal.model.SysPortalPage;
import com.landray.kmss.sys.portal.model.SysPortalPageDetail;

public class SysPortalPageDetailForm extends ExtendForm {
	protected String fdType;
	protected String docContent;
	protected String fdMd5;
	protected String fdJsp;
	protected String fdHeader;
	protected String fdFooter;
	protected String fdLogo;
	protected String fdHeaderVars;
	protected String fdFooterVars;

	protected String sysPortalPageId;

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public String getSysPortalPageId() {
		return sysPortalPageId;
	}

	public void setSysPortalPageId(String sysPortalPageId) {
		this.sysPortalPageId = sysPortalPageId;
	}

	/**
	 * @return 内容
	 */
	public String getDocContent() {
		return docContent;
	}

	/**
	 * @param docContent
	 *            内容
	 */
	public void setDocContent(String docContent) {
		this.docContent = docContent;
	}

	public String getFdHeaderVars() {
		return fdHeaderVars;
	}

	public void setFdHeaderVars(String fdHeaderVars) {
		this.fdHeaderVars = fdHeaderVars;
	}

	public String getFdFooterVars() {
		return fdFooterVars;
	}

	public void setFdFooterVars(String fdFooterVars) {
		this.fdFooterVars = fdFooterVars;
	}

	public String getFdLogo() {
		return fdLogo;
	}

	public void setFdLogo(String fdLogo) {
		this.fdLogo = fdLogo;
	}

	public String getFdHeader() {
		return fdHeader;
	}

	public void setFdHeader(String fdHeader) {
		this.fdHeader = fdHeader;
	}

	public String getFdFooter() {
		return fdFooter;
	}

	public void setFdFooter(String fdFooter) {
		this.fdFooter = fdFooter;
	}

	public String getFdJsp() {
		return fdJsp;
	}

	public void setFdJsp(String fdJsp) {
		this.fdJsp = fdJsp;
	}

	public String getFdMd5() {
		return fdMd5;
	}

	public void setFdMd5(String fdMd5) {
		this.fdMd5 = fdMd5;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdType = null;
		docContent = null;
		fdMd5 = null;
		fdJsp = null;
		fdHeader = null;
		fdFooter = null;
		fdLogo = null;
		fdHeaderVars = null;
		fdFooterVars = null;
		sysPortalPageId = null;
		
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysPortalPageDetail.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("sysPortalPageId",
					new FormConvertor_IDToModel("sysPortalPage",
							SysPortalPage.class));
		}
		return toModelPropertyMap;
	}

}
