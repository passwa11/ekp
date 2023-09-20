package com.landray.kmss.sys.portal.model;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.portal.forms.SysPortalPageDetailForm;

/**
 * 主文档
 * 
 * @author
 * @version 1.0 2013-07-18
 */
public class SysPortalPageDetail extends BaseModel implements IBaseModel,
		InterceptFieldEnabled {
	protected SysPortalPage sysPortalPage;
	protected String fdType;
	protected String docContent;
	protected String fdMd5;
	protected String fdJsp;
	protected String fdHeader;
	protected String fdFooter;
	protected String fdLogo;
	protected String fdHeaderVars;
	protected String fdFooterVars;
	protected String fdGuide;
	protected String fdGuideCfg;

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public SysPortalPage getSysPortalPage() {
		return sysPortalPage;
	}

	public void setSysPortalPage(SysPortalPage sysPortalPage) {
		this.sysPortalPage = sysPortalPage;
	}

	/**
	 * @return 内容
	 */
	public String getDocContent() {
		return (String) readLazyField("docContent", docContent);
	}

	/**
	 * @param docContent
	 *            内容
	 */
	public void setDocContent(String docContent) {
		this.docContent = (String) writeLazyField("docContent",
				this.docContent, docContent);
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
		return (String) readLazyField("fdJsp", fdJsp);
	}

	public void setFdJsp(String fdJsp) {
		this.fdJsp = (String) writeLazyField("fdJsp", this.fdJsp, fdJsp);
	}

	public String getFdMd5() {
		return fdMd5;
	}

	public void setFdMd5(String fdMd5) {
		this.fdMd5 = fdMd5;
	}

	public String getFdGuide() {
		return fdGuide;
	}

	public void setFdGuide(String fdGuide) {
		this.fdGuide = fdGuide;
	}

	public String getFdGuideCfg() {
		return (String) readLazyField("fdGuideCfg", fdGuideCfg);
	}

	public void setFdGuideCfg(String fdGuideCfg) {
		this.fdGuideCfg = (String) writeLazyField("fdGuideCfg",
				this.fdGuideCfg, fdGuideCfg);
	}

	@Override
    public Class getFormClass() {
		return SysPortalPageDetailForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("sysPortalPage.fdId", "sysPortalPageId");
		}
		return toFormPropertyMap;
	}
}
