package com.landray.kmss.sys.portal.model;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.authentication.ssoclient.Logger;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaModel;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.portal.forms.SysPortalPageForm;
import com.landray.kmss.sys.portal.service.ISysPortalPageService;
import com.landray.kmss.util.SpringBeanUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 主文档
 * 
 * @author
 * @version 1.0 2013-07-18
 */
public class SysPortalPage extends AuthModel implements ISysAuthAreaModel {

	/**
	 * 名称
	 */
	protected String fdName;
	/**
	 * 类型
	 */
	protected String fdType;
	/**
	 * 标题
	 */
	protected String fdTitle;
	protected String fdUrl;
	protected String fdTheme;
	protected String fdIcon;
	protected String fdImg;
	protected String fdUsePortal;

	public String getFdImg() {
		return fdImg;
	}

	public void setFdImg(String fdImg) {
		this.fdImg = fdImg;
	}

	/**
	 * 最后修改时间
	 */
	protected Date docAlterTime;

	/**
	 * @return 最后修改时间
	 */
	public Date getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            最后修改时间
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 修改者
	 */
	protected SysOrgElement docAlteror;

	/**
	 * @return 修改者
	 */
	public SysOrgElement getDocAlteror() {
		return docAlteror;
	}

	/**
	 * @param docAlteror
	 *            修改者
	 */
	public void setDocAlteror(SysOrgElement docAlteror) {
		this.docAlteror = docAlteror;
	}
	/*
	 * 可阅读者
	 */
	protected List defReaders = new ArrayList();

	public List getDefReaders() {
		return defReaders;
	}

	public void setDefReaders(List defReaders) {
		this.defReaders = defReaders;
	}
	public String getFdUsePortal() {
		return fdUsePortal;
	}

	public void setFdUsePortal(String fdUsePortal) {
		this.fdUsePortal = fdUsePortal;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdTitle() {
		return fdTitle;
	}

	public void setFdTitle(String fdTitle) {
		this.fdTitle = fdTitle;
	}

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public String getFdUrl() {
		return fdUrl;
	}

	public void setFdUrl(String fdUrl) {
		this.fdUrl = fdUrl;
	}

	protected List<SysPortalPageDetail> pageDetails = new ArrayList<SysPortalPageDetail>();

	public List<SysPortalPageDetail> getPageDetails() {
		return pageDetails;
	}

	public void setPageDetails(List<SysPortalPageDetail> pageDetails) {
		this.pageDetails = pageDetails;
	}
	
	protected List<SysPortalMainPage> sysPortalMainPageList = new ArrayList<SysPortalMainPage>();
	
	public List<SysPortalMainPage> getSysPortalMainPageList() {
		return sysPortalMainPageList;
	}

	public void setSysPortalMainPageList(
			List<SysPortalMainPage> sysPortalMainPageList) {
		this.sysPortalMainPageList = sysPortalMainPageList;
	}

	public String getFdIcon() {
		return fdIcon;
	}

	public void setFdIcon(String fdIcon) {
		this.fdIcon = fdIcon;
	}

	public String getFdTheme() {
		return fdTheme;
	}

	public void setFdTheme(String fdTheme) {
		this.fdTheme = fdTheme;
	}

	public String getFdPortalNames() {
		String temp = "";
		try {
			HQLInfo hqlInfo = new HQLInfo();
			ISysPortalPageService service = (ISysPortalPageService) SpringBeanUtil
					.getBean("sysPortalPageService");
			hqlInfo.setSelectBlock(" distinct sysPortalMainPage.sysPortalMain.fdName ");
			hqlInfo.setFromBlock(" SysPortalMainPage sysPortalMainPage left join sysPortalMainPage.sysPortalMain ");
			hqlInfo.setWhereBlock(" sysPortalMainPage.sysPortalPage.fdId = :fdId");
			hqlInfo.setParameter("fdId", this.getFdId());
			List list = service.getBaseDao().findValue(hqlInfo);
			for (int i = 0; i < list.size(); i++) {
				temp += "[" + list.get(i).toString() + "] ";
			}
		} catch (Exception e) {
			Logger.warn("获取所属门户信息错误",e);
		}
		return temp;
	}

	@Override
    public Class getFormClass() {
		return SysPortalPageForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
			toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
			toFormPropertyMap.put("authArea.fdId", "authAreaId");
			toFormPropertyMap.put("authArea.fdName", "authAreaName");
			toFormPropertyMap.put("pageDetails",
					new ModelConvertor_ModelListToFormList("pageDetails"));
			toFormPropertyMap.put("defReaders",
					new ModelConvertor_ModelListToString(
							"defReaderIds:defReaderNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}
	
	/*
	 * 所属场所
	 */
	protected SysAuthArea authArea;

	@Override
    public SysAuthArea getAuthArea() {
		return authArea;
	}

	@Override
    public void setAuthArea(SysAuthArea authArea) {
		this.authArea = authArea;
	}

	// 匿名（0普通 1匿名）@author 吴进 by 20191107
	private Boolean fdAnonymous = Boolean.FALSE;
	
	public Boolean getFdAnonymous() {
		return fdAnonymous;
	}

	public void setFdAnonymous(Boolean fdAnonymous) {
		this.fdAnonymous = fdAnonymous;
	}
}
