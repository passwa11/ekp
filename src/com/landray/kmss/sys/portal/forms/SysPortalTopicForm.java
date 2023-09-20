package com.landray.kmss.sys.portal.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.portal.model.SysPortalTopic;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/** 
* @author 	陈经纬 
* @date 	2017年8月7日 上午11:28:34  
*/
public class SysPortalTopicForm  extends ExtendForm  implements
IAttachmentForm  {

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
	 * 门户Id
	 */
	protected String fdPortalId;

	public String getFdPortalId() {
		return fdPortalId;
	}

	public void setFdPortalId(String fdPortalId) {
		this.fdPortalId = fdPortalId;
	}

	public String getFdImg() {
		return fdImg;
	}

	public void setFdImg(String fdImg) {
		this.fdImg = fdImg;
	}

	protected String fdImg;

	/**
	 * 头条链接
	 */
	protected String fdTopUrl = null;

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
	protected String docAlterTime = null;

	/**
	 * @return 更新时间
	 */
	public String getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            更新时间
	 */
	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
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
	 * 创建者的ID
	 */
	protected String docCreatorId = null;

	/**
	 * @return 创建者的ID
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}

	/**
	 * @param docCreatorId
	 *            创建者的ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * 创建者的名称
	 */
	protected String docCreatorName = null;

	/**
	 * @return 创建者的名称
	 */
	public String getDocCreatorName() {
		return docCreatorName;
	}

	/**
	 * @param docCreatorName
	 *            创建者的名称
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
	
	private Boolean fdAnonymous = Boolean.FALSE;
	
	public Boolean getFdAnonymous() {
		return fdAnonymous;
	}

	public void setFdAnonymous(Boolean fdAnonymous) {
		this.fdAnonymous = fdAnonymous;
	}
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdTopUrl = null;
		docAlterTime = null;
		docCreateTime = null;
		docCreatorId = null;
		docCreatorName = null;
		docAlterorId = null;
		docAlterorName = null;
		fdPortalId = null;
		fdImg = null;
		fdEditorIds = null;
		fdEditorNames = null;
		autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysPortalTopic.class;
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
			toModelPropertyMap.put("fdEditorIds",
					new FormConvertor_IDsToModelList("fdEditors",
							SysOrgElement.class));	
		}
		return toModelPropertyMap;
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
	
}
