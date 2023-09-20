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
import com.landray.kmss.sys.portal.model.SysPortalPage;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 主文档 Form
 * 
 * @author
 * @version 1.0 2013-07-18
 */
public class SysPortalPageForm extends AuthForm implements ISysAuthAreaForm {

	/**
	 * 名称
	 */
	protected String fdName = null;
	/**
	 * 标题
	 */
	protected String fdTitle = null;
	/**
	 * 类型
	 */
	protected String fdType = null;
	protected String fdUrl;
	protected String fdTheme;
	protected String fdIcon;
	protected String fdImg; //素材图片
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

	protected String fdUsePortal;

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


	public String getFdImg() {
		return fdImg;
	}

	public void setFdImg(String fdImg) {
		this.fdImg = fdImg;
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

	public String getFdThemeName() {
		if (SysUiPluginUtil.getThemeById(this.fdTheme) != null) {
			return SysUiPluginUtil.getThemeById(this.fdTheme).getFdName();
		} else {
			return "";
		}
	}

	//
	private List pageDetails = new AutoArrayList(SysPortalPageDetailForm.class);

	public List getPageDetails() {
		return pageDetails;
	}

	public void setPageDetails(List pageDetails) {
		this.pageDetails = pageDetails;
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
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdTitle = null;
		fdType = null;
		fdTheme = null;
		fdIcon = null;
		fdImg=null;
		fdUrl = null;
		defReaderIds = null;
		defReaderNames = null;
		docCreateTime = null;
		docAlterTime = null;
		docCreatorId = null;
		docCreatorName = null;
		docAlterorId = null;
		docAlterorName = null;
		authAreaId = null;
		authAreaName = null;
		pageDetails.clear();
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysPortalPage.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());

			toModelPropertyMap.put("pageDetails",
					new FormConvertor_FormListToModelList("pageDetails",
							"sysPortalPage"));
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
