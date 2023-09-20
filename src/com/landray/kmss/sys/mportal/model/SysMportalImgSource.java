package com.landray.kmss.sys.mportal.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.mportal.forms.SysMportalImgSourceForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.AutoHashMap;

/*
 * @author zhuhq
 * @version 1.0 2017-01-13
 */
public class SysMportalImgSource extends BaseModel implements IAttachment {

	/**
	 * 名称
	 */
	protected String fdName;


	/**
	 * 描述
	 */
	protected String fdContent;


	/**
	 * 链接
	 */
	protected String fdUrl;

	
	/**
	 * 标题
	 */
	protected String fdSubject;
	
	/**
	 * 创建者
	 */
	protected SysOrgElement docCreator;
	
	/**
	 * 创建时间
	 */
	protected Date docCreateTime;
	
	/*
	 * 可编辑者
	 */
	protected List authEditors = new ArrayList();

	public List getAuthEditors() {
		return authEditors;
	}

	public void setAuthEditors(List authEditors) {
		this.authEditors = authEditors;
	}

	/**
	 * 修改者
	 */
	protected SysOrgElement docAlteror;

	/**
	 * 更新时间
	 */
	protected Date docAlterTime;


	@Override
    public Class getFormClass() {
		return SysMportalImgSourceForm.class;
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
			toFormPropertyMap.put("authEditors",
					new ModelConvertor_ModelListToString(
							"authEditorIds:authEditorNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
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

	/**
	 * @param fdName
	 *            名称
	 */
	public String getFdName() {
		return SysLangUtil.getPropValue(this, "fdName", this.fdName);
	}
	
	/**
	 * @param fdName
	 *            名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
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

	public SysOrgElement getDocCreator() {
		return docCreator;
	}

	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public SysOrgElement getDocAlteror() {
		return docAlteror;
	}

	public void setDocAlteror(SysOrgElement docAlteror) {
		this.docAlteror = docAlteror;
	}

	public Date getDocAlterTime() {
		return docAlterTime;
	}

	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}
	
	
}

