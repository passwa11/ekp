package com.landray.kmss.sys.mportal.forms;


import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.mportal.model.SysMportalImgSource;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.AutoHashMap;

/*
 * @author zhuhq
 * @version 1.0 2011-09-20
 */
public class SysMportalImgSourceForm extends ExtendForm implements
		IAttachmentForm {

	/**
	 * 名称
	 */
	protected String fdName = null;

	

	/**
	 * 描述
	 */
	protected String fdContent = null;


	/**
	 * 链接
	 */
	protected String fdUrl = null;


	/**
	 * 推荐头条
	 */
	protected String fdSubject = null;


	/**
	 * 创建者的ID
	 */
	protected String docCreatorId = null;

	/**
	 * 创建时间
	 */
	protected String docCreateTime = null;

	/**
	 * 创建者的名称
	 */
	protected String docCreatorName = null;

	/**
	 * 修改者的ID
	 */
	protected String docAlterorId = null;

	/**
	 * 修改者的名称
	 */
	protected String docAlterorName = null;

	/**
	 * 更新时间
	 */
	protected String docAlterTime = null;

	

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdContent = null;
		fdUrl = null;
		fdSubject = null;
		docAlterTime = null;
		docCreateTime = null;
		docCreatorId = null;
		docCreatorName = null;
		docAlterorId = null;
		docAlterorName = null;
		autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysMportalImgSource.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgElement.class));
			toModelPropertyMap.put("docAlterorId", new FormConvertor_IDToModel(
					"docAlteror", SysOrgElement.class));
			toModelPropertyMap.put("authEditorIds",
					new FormConvertor_IDsToModelList("authEditors",
							SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

	/*
	 * 可编辑者
	 */
	protected String authEditorIds = null;

	public String getAuthEditorIds() {
		return authEditorIds;
	}

	public void setAuthEditorIds(String authEditorIds) {
		this.authEditorIds = authEditorIds;
	}

	/*
	 * 可编辑者名称
	 */
	protected String authEditorNames = null;

	public String getAuthEditorNames() {
		return authEditorNames;
	}

	public void setAuthEditorNames(String authEditorNames) {
		this.authEditorNames = authEditorNames;
	}

	/*
	 * 附件机制
	 */
	protected AutoHashMap autoHashMap = new AutoHashMap(
			AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdContent() {
		return fdContent;
	}

	public void setFdContent(String fdContent) {
		this.fdContent = fdContent;
	}

	public String getFdUrl() {
		return fdUrl;
	}

	public void setFdUrl(String fdUrl) {
		this.fdUrl = fdUrl;
	}

	public String getFdSubject() {
		return fdSubject;
	}

	public void setFdSubject(String fdSubject) {
		this.fdSubject = fdSubject;
	}

	public String getDocCreatorId() {
		return docCreatorId;
	}

	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	public String getDocAlterorId() {
		return docAlterorId;
	}

	public void setDocAlterorId(String docAlterorId) {
		this.docAlterorId = docAlterorId;
	}

	public String getDocAlterorName() {
		return docAlterorName;
	}

	public void setDocAlterorName(String docAlterorName) {
		this.docAlterorName = docAlterorName;
	}

	public String getDocAlterTime() {
		return docAlterTime;
	}

	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}
	
	
}

