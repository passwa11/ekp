package com.landray.kmss.sys.portal.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.portal.model.SysPortalNav;
import com.landray.kmss.web.action.ActionMapping;


/**
 * 系统导航 Form
 */
public class SysPortalNavForm extends ExtendForm implements ISysAuthAreaForm  {

	/**
	 * 名称
	 */
	protected String fdName = null;
	
	/**
	 * @return 名称
	 */
	public String getFdName() {
		return fdName;
	}
	
	/**
	 * @param fdName 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	
	/**
	 * 内容
	 */
	protected String fdContent = null;
	
	/**
	 * @return 内容
	 */
	public String getFdContent() {
		return fdContent;
	}
	
	/**
	 * @param fdContent 内容
	 */
	public void setFdContent(String fdContent) {
		this.fdContent = fdContent;
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
	 * @param docCreateTime 创建时间
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
	 * @param docAlterTime 最后修改时间
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
	 * @param docCreatorId 创建人的ID
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
	 * @param docCreatorName 创建人的名称
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
	 * @param docAlterorId 修改者的ID
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
	
	/**
	 * @param docAlterorName 修改者的名称
	 */
	public void setDocAlterorName(String docAlterorName) {
		this.docAlterorName = docAlterorName;
	}

	
	/**
	 * 页面ID
	 */
	protected String fdPageId;
	public String getFdPageId() {
		return fdPageId;
	}

	public void setFdPageId(String fdPageId) {
		this.fdPageId = fdPageId;
	}
	
	/*
	 * 可维护者
	 */
	private String fdEditorIds = null;
	private String fdEditorNames = null;
	public String getFdEditorIds() {
		return fdEditorIds;
	}

	public void setFdEditorIds(String fdEditorIds) {
		this.fdEditorIds = fdEditorIds;
	}

	public String getFdEditorNames() {
		return fdEditorNames;
	}

	public void setFdEditorNames(String fdEditorNames) {
		this.fdEditorNames = fdEditorNames;
	}
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdContent = null;
		docCreateTime = null;
		docAlterTime = null;
		docCreatorId = null;
		docCreatorName = null;
		docAlterorId = null;
		docAlterorName = null;
		fdEditorIds = null;
		fdEditorNames = null;
		authAreaId = null;
		authAreaName = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysPortalNav.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId",
					new FormConvertor_IDToModel("docCreator",
						SysOrgElement.class));
			toModelPropertyMap.put("docAlterorId",
					new FormConvertor_IDToModel("docAlteror",
						SysOrgElement.class));
		    toModelPropertyMap.put("authAreaId", new FormConvertor_IDToModel(
					"authArea", SysAuthArea.class));
			toModelPropertyMap.put("fdEditorIds",
					new FormConvertor_IDsToModelList("fdEditors",
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
	
	
	/*
	 * 匿名字段（0普通 1匿名）
	 * 
	 * @author 吴进 by 20191125
	 */
	private Boolean fdAnonymous = Boolean.FALSE;

	public Boolean getFdAnonymous() {
		return fdAnonymous;
	}

	public void setFdAnonymous(Boolean fdAnonymous) {
		this.fdAnonymous = fdAnonymous;
	}
}
