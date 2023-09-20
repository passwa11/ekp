package com.landray.kmss.sys.portal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaModel;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.portal.forms.SysPortalTopicForm;
import com.landray.kmss.util.AutoHashMap;

/** 
* @author 	陈经纬 
* @date 	2017年8月4日 下午4:14:34  
*/
public class SysPortalTopic extends BaseModel  implements IAttachment,ISysAuthAreaModel {
	
	/**
	 * 名称
	 */
	protected String fdName;

	/**
	 * @return 名称
	 */
	public String getFdNameOri() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            名称
	 */
	public void setFdNameOri(String fdName) {
		this.fdName = fdName;
	}
	
	public String getFdName() {
		return SysLangUtil.getPropValue(this, "fdName", this.fdName);
	}
	
	public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}

	/**
	 * 门户Id
	 */
	protected String fdPortalId;

	public String getFdPortalId() {
		return fdPortalId;
	}

	public void setFdPortalId(String fdPortalId) {
		this.fdPortalId = fdPortalId;
	}

	/**
	 * 素材库图片url
	 */
	protected String fdImg;

	public String getFdImg() {
		return fdImg;
	}

	public void setFdImg(String fdImg) {
		this.fdImg = fdImg;
	}


	/**
	 * 头条链接
	 */
	protected String fdTopUrl;

	/**
	 * @return 头条链接
	 */
	public String getFdTopUrl() {
		return fdTopUrl;
	}

	/**
	 * @param fdTopUrl
	 *            头条链接
	 */
	public void setFdTopUrl(String fdTopUrl) {
		this.fdTopUrl = fdTopUrl;
	}


	/**
	 * 更新时间
	 */
	protected Date docAlterTime;

	/**
	 * @return 更新时间
	 */
	public Date getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            更新时间
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 创建时间
	 */
	protected Date docCreateTime;

	/**
	 * @return 创建时间
	 */
	public Date getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            创建时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 创建者
	 */
	protected SysOrgElement docCreator;

	/**
	 * @return 创建者
	 */
	public SysOrgElement getDocCreator() {
		return docCreator;
	}

	/**
	 * @param docCreator
	 *            创建者
	 */
	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
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
	 * 可维护人员
	 */
	private List fdEditors = new ArrayList();
	public List getFdEditors() {
		return fdEditors;
	}

	public void setFdEditors(List fdEditors) {
		this.fdEditors = fdEditors;
	}

	@Override
    public Class getFormClass() {
		return SysPortalTopicForm.class;
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
			toFormPropertyMap.put("fdEditors",
					new ModelConvertor_ModelListToString(
							"fdEditorIds:fdEditorNames", "fdId:fdName"));
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
	
	private Boolean fdAnonymous = Boolean.FALSE;
	
	public Boolean getFdAnonymous() {
		return fdAnonymous;
	}

	public void setFdAnonymous(Boolean fdAnonymous) {
		this.fdAnonymous = fdAnonymous;
	}



}
