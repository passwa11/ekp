package com.landray.kmss.sys.portal.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.portal.model.SysPortalLink;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 快捷方式 Form
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class SysPortalLinkForm extends ExtendForm implements ISysAuthAreaForm {

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
	 * @param fdName
	 *            名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 连接类型
	 */
	protected String fdType = null;

	/**
	 * @return 连接类型
	 */
	public String getFdType() {
		return fdType;
	}

	/**
	 * @param fdType
	 *            连接类型
	 */
	public void setFdType(String fdType) {
		this.fdType = fdType;
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

	/**
	 * @param docAlterorName
	 *            修改者的名称
	 */
	public void setDocAlterorName(String docAlterorName) {
		this.docAlterorName = docAlterorName;
	}

	//
	private List fdLinks = new AutoArrayList(SysPortalLinkDetailForm.class);

	public List getFdLinks() {
		return fdLinks;
	}

	public void setFdLinks(List fdLinks) {
		this.fdLinks = fdLinks;
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
		fdType = null;
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
		fdLinks.clear();
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysPortalLink.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel("docCreator", SysOrgElement.class));
			toModelPropertyMap.put("docAlterorId", new FormConvertor_IDToModel("docAlteror", SysOrgElement.class));

			toModelPropertyMap.put("fdLinks", new FormConvertor_FormListToModelList("fdLinks", "sysPortalLink"));
			toModelPropertyMap.put("authAreaId", new FormConvertor_IDToModel("authArea", SysAuthArea.class));
			toModelPropertyMap.put("fdEditorIds", new FormConvertor_IDsToModelList("fdEditors", SysOrgElement.class));

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
	 * @author 吴进 by 20191115
	 */
	private Boolean fdAnonymous = Boolean.FALSE;

	public Boolean getFdAnonymous() {
		return fdAnonymous;
	}

	public void setFdAnonymous(Boolean fdAnonymous) {
		this.fdAnonymous = fdAnonymous;
	}
}
