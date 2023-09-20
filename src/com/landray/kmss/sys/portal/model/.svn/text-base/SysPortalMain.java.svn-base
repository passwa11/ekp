package com.landray.kmss.sys.portal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.IBaseTreeModel;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaModel;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.portal.forms.SysPortalMainForm;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ObjectUtil;

public class SysPortalMain extends AuthModel implements IBaseTreeModel,
		ISysAuthAreaModel {
	/**
	 * 名称
	 */
	protected String fdName;

	/**
	 * 是否启用
	 */
	protected Boolean fdEnabled;

	/**
	 * 排序号
	 */
	protected Integer fdOrder;
	
	/**
	 * 语言
	 */
	protected String fdLang;
	
	protected String fdIcon;
	protected String fdImg;
	protected String fdTarget;
	
	protected String fdLogo;
	protected String fdTheme;
	protected String fdHeaderId;
	protected String fdHeaderVars;
	protected String fdFooterId;
	protected String fdFooterVars;


	/**
	 * 最后修改时间
	 */
	protected Date docAlterTime;


    public String getFdImg() {
        return fdImg;
    }

    public void setFdImg(String fdImg) {
        this.fdImg = fdImg;
    }

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

	public String getFdLogo() {
		return fdLogo;
	}

	public void setFdLogo(String fdLogo) {
		this.fdLogo = fdLogo;
	}

	public String getFdTheme() {
		return fdTheme;
	}

	public void setFdTheme(String fdTheme) {
		this.fdTheme = fdTheme;
	}

	public String getFdHeaderId() {
		return fdHeaderId;
	}

	public void setFdHeaderId(String fdHeaderId) {
		this.fdHeaderId = fdHeaderId;
	}

	public String getFdHeaderVars() {
		return fdHeaderVars;
	}

	public void setFdHeaderVars(String fdHeaderVars) {
		this.fdHeaderVars = fdHeaderVars;
	}

	public String getFdFooterId() {
		return fdFooterId;
	}

	public void setFdFooterId(String fdFooterId) {
		this.fdFooterId = fdFooterId;
	}

	public String getFdFooterVars() {
		return fdFooterVars;
	}

	public void setFdFooterVars(String fdFooterVars) {
		this.fdFooterVars = fdFooterVars;
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
	
	protected List<SysPortalMainPage> pages = new ArrayList<SysPortalMainPage>();

	public List<SysPortalMainPage> getPages() {
		return pages;
	}

	public void setPages(List<SysPortalMainPage> pages) {
		this.pages = pages;
	}

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}
	
	public String getFdLang() {
		return fdLang;
	}

	public void setFdLang(String fdLang) {
		this.fdLang = fdLang;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public Boolean getFdEnabled() {
		return fdEnabled;
	}

	public void setFdEnabled(Boolean fdEnabled) {
		this.fdEnabled = fdEnabled;
	}

	/**
	 * 是否极简模式
	 */
	protected Boolean fdIsQuick;

	public Boolean getFdIsQuick() {
		return fdIsQuick;
	}

	public void setFdIsQuick(Boolean fdIsQuick) {
		this.fdIsQuick = fdIsQuick;
	}

	protected List fdChildren;

	public List getFdChildren() {
		return fdChildren;
	}

	public void setFdChildren(List fdChildren) {
		this.fdChildren = fdChildren;
	}
	@Override
    public Class getFormClass() {
		return SysPortalMainForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdParent.fdId", "fdParentId");
			toFormPropertyMap.put("fdParent.fdName", "fdParentName");
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
			toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
			toFormPropertyMap.put("authArea.fdId", "authAreaId");
			toFormPropertyMap.put("authArea.fdName", "authAreaName");
			toFormPropertyMap.put("pages",
					new ModelConvertor_ModelListToFormList("pages"));

			toFormPropertyMap.put("defReaders",
					new ModelConvertor_ModelListToString(
							"defReaderIds:defReaderNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}

	// basetree
	protected String fdHierarchyId = BaseTreeConstant.HIERARCHY_ID_SPLIT
			+ getFdId() + BaseTreeConstant.HIERARCHY_ID_SPLIT;

	@Override
    public String getFdHierarchyId() {
		return fdHierarchyId;
	}

	@Override
    public void setFdHierarchyId(String fdHierarchyId) {
		this.fdHierarchyId = fdHierarchyId;
	}

	protected IBaseTreeModel fdParent;

	@Override
    public IBaseTreeModel getFdParent() {
		return fdParent;
	}

	public void setFdParent(IBaseTreeModel parent) {
		IBaseTreeModel oldParent = getHbmParent();
		if (!ObjectUtil.equals(oldParent, parent)) {
			ModelUtil.checkTreeCycle(this, parent, "fdParent");
			setHbmParent(parent);
		}
	}

	public IBaseTreeModel getHbmParent() {
		return fdParent;
	}

	public void setHbmParent(IBaseTreeModel parent) {
		this.fdParent = parent;
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
