package com.landray.kmss.sys.portal.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.portal.model.SysPortalMain;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 主文档 Form
 * 
 * @author
 * @version 1.0 2013-07-18
 */
public class SysPortalMainForm extends AuthForm implements ISysAuthAreaForm {

	/**
	 * 名称
	 */
	protected String fdName = null;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 层级ID
	 */
	protected String fdHierarchyId = null;

	/**
	 * @return 层级ID
	 */
	public String getFdHierarchyId() {
		return fdHierarchyId;
	}

	/**
	 * @param fdHierarchyId
	 *            层级ID
	 */
	public void setFdHierarchyId(String fdHierarchyId) {
		this.fdHierarchyId = fdHierarchyId;
	}

	/**
	 * 排序号
	 */
	protected String fdOrder = null;

	/**
	 * @return 排序号
	 */
	public String getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}
	
	/**
	 * 语言
	 */
	protected String fdLang;
	
	public String getFdLang() {
		return fdLang;
	}

	public void setFdLang(String fdLang) {
		this.fdLang = fdLang;
	}

	/**
	 * 布尔
	 */
	protected String fdEnabled = null;

	/**
	 * @return 布尔
	 */
	public String getFdEnabled() {
		return fdEnabled;
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

	/**
	 * @param fdEnabled
	 *            布尔
	 */
	public void setFdEnabled(String fdEnabled) {
		this.fdEnabled = fdEnabled;
	}

	/**
	 * 上级分类的ID
	 */
	protected String fdParentId = null;

	/**
	 * @return 上级分类的ID
	 */
	public String getFdParentId() {
		return fdParentId;
	}

	/**
	 * @param fdParentId
	 *            上级分类的ID
	 */
	public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}

	/**
	 * 上级分类的名称
	 */
	protected String fdParentName = null;

	/**
	 * @return 上级分类的名称
	 */
	public String getFdParentName() {
		return fdParentName;
	}

	/**
	 * @param fdParentName
	 *            上级分类的名称
	 */
	public void setFdParentName(String fdParentName) {
		this.fdParentName = fdParentName;
	}

	protected String fdTarget;

	public String getFdTarget() {
		return fdTarget;
	}

	public void setFdTarget(String fdTarget) {
		this.fdTarget = fdTarget;
	}

	protected String fdIcon;

	public String getFdIcon() {
		return fdIcon;
	}

	public void setFdIcon(String fdIcon) {
		this.fdIcon = fdIcon;
	}

	protected String fdImg; //素材图片

	public String getFdImg() {
		return fdImg;
	}

	public void setFdImg(String fdImg) {
		this.fdImg = fdImg;
	}


	private List pages = new AutoArrayList(SysPortalMainPageForm.class);

	public List getPages() {
		return pages;
	}

	public void setPages(List pages) {
		this.pages = pages;
	}
	/**
	 * 创建时间
	 */
	protected String docCreateTime = null;

	/**
	 * @return 创建时间
	 */
	public String getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 最后修改时间
	 */
	protected String docAlterTime = null;

	/**
	 * @return 最后修改时间
	 */
	public String getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            最后修改时间
	 */
	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 创建人的ID
	 */
	protected String docCreatorId = null;

	/**
	 * @return 创建人的ID
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}

	/**
	 * @param docCreatorId
	 *            创建人的ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * 创建人的名称
	 */
	protected String docCreatorName = null;

	/**
	 * @return 创建人的名称
	 */
	public String getDocCreatorName() {
		return docCreatorName;
	}

	/**
	 * @param docCreatorName
	 *            创建人的名称
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	/**
	 * 修改者的ID
	 */
	protected String docAlterorId = null;

	/**
	 * @return 修改者的ID
	 */
	public String getDocAlterorId() {
		return docAlterorId;
	}

	/**
	 * @param docAlterorId
	 *            修改者的ID
	 */
	public void setDocAlterorId(String docAlterorId) {
		this.docAlterorId = docAlterorId;
	}

	/**
	 * 修改者的名称
	 */
	protected String docAlterorName = null;

	/**
	 * @return 修改者的名称
	 */
	public String getDocAlterorName() {
		return docAlterorName;
	}
	public void setDocAlterorName(String docAlterorName) {
		this.docAlterorName = docAlterorName;
	}

	/*
	 * 默认可访问者
	 */
	protected String defReaderIds = null;

	protected String defReaderNames = null;

	public String getDefReaderIds() {
		return defReaderIds;
	}

	public void setDefReaderIds(String defReaderIds) {
		this.defReaderIds = defReaderIds;
	}

	public String getDefReaderNames() {
		return defReaderNames;
	}

	public void setDefReaderNames(String defReaderNames) {
		this.defReaderNames = defReaderNames;
	}
	public String getFdThemeName() {
		if (SysUiPluginUtil.getThemeById(this.fdTheme) != null) {
			return SysUiPluginUtil.getThemeById(this.fdTheme).getFdName();
		} else {
			return "";
		}
	}
	protected String fdLogo;
	protected String fdTheme;
	protected String fdHeaderId;
	protected String fdHeaderVars;
	protected String fdFooterId;
	protected String fdFooterVars;

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

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdHierarchyId = null;
		fdOrder = null;
		fdEnabled = null;
		fdIcon = null;
		fdImg = null;
		fdParentId = null;
		fdParentName = null;
		fdTarget = null;
		defReaderIds = null;
		defReaderNames = null;
		pages.clear();
		fdLogo = null;
		fdTheme = null;
		fdHeaderId = null;
		fdHeaderVars = null;
		fdFooterId = null;
		fdFooterVars = null;
		docCreateTime = null;
		docAlterTime = null;
		docCreatorId = null;
		docCreatorName = null;
		docAlterorId = null;
		docAlterorName = null;
		authAreaId = null;
		authAreaName = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysPortalMain.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel(
					"fdParent", SysPortalMain.class));
			toModelPropertyMap.put("pages",
					new FormConvertor_FormListToModelList("pages",
							"sysPortalMain"));
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgElement.class));
			toModelPropertyMap.put("docAlterorId", new FormConvertor_IDToModel(
					"docAlteror", SysOrgElement.class));
            toModelPropertyMap.put("authAreaId", new FormConvertor_IDToModel(
					"authArea", SysAuthArea.class));
			toModelPropertyMap.put("defReaderIds",
					new FormConvertor_IDsToModelList("defReaders",
							SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
	
		// 所属场所ID
	protected String authAreaId = null;

	@Override
    public String getAuthAreaId() {
		return authAreaId;
	}

	@Override
    public void setAuthAreaId(String authAreaId) {
		this.authAreaId = authAreaId;
	}

	// 所属场所名称
	protected String authAreaName = null;

	@Override
    public String getAuthAreaName() {
		return authAreaName;
	}

	@Override
    public void setAuthAreaName(String authAreaName) {
		this.authAreaName = authAreaName;
	}

	// 匿名（0普通 1匿名）@author 吴进 by 20191107
	private String fdAnonymous;

	public String getFdAnonymous() {
		return fdAnonymous;
	}

	public void setFdAnonymous(String fdAnonymous) {
		this.fdAnonymous = fdAnonymous;
	}
}
