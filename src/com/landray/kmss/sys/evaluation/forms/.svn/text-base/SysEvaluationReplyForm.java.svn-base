package com.landray.kmss.sys.evaluation.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.evaluation.model.SysEvaluationReply;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.praise.forms.SysPraiseForm;
import com.landray.kmss.sys.praise.interfaces.ISysPraiseForm;
import com.landray.kmss.web.action.ActionMapping;

public class SysEvaluationReplyForm extends ExtendForm
		implements ISysPraiseForm {
	/**
	 * 回复内容
	 */
	protected String docContent = null;

	/**
	 * @return 回复内容
	 */
	public String getDocContent() {
		return docContent;
	}

	/**
	 * @param docContent
	 *            回复内容
	 */
	public void setDocContent(String docContent) {
		this.docContent = docContent;
	}
	
	/**
	 * 回复时间
	 */
	protected String fdReplyTime = null;

	/**
	 * @return 回复时间
	 */
	public String getFdReplyTime() {
		return fdReplyTime;
	}

	/**
	 * @param fdReplyTime
	 *            回复时间
	 */
	public void setFdReplyTime(String fdReplyTime) {
		this.fdReplyTime = fdReplyTime;
	}
	
	protected String fdModelId = null;

	public String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}
	
	protected String fdModelName = null;

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}
	/**
	 * 回复者的ID
	 */
	protected String fdReplyerId = null;

	/**
	 * @return 回复者的ID
	 */
	public String getFdReplyerId() {
		return fdReplyerId;
	}

	/**
	 * @param fdReplyerId
	 *            回复者的ID
	 */
	public void setFdReplyerId(String fdReplyerId) {
		this.fdReplyerId = fdReplyerId;
	}

	/**
	 * 回复者的名称
	 */
	protected String fdReplyerName = null;

	/**
	 * @return 回复者的名称
	 */
	public String getFdReplyerName() {
		return fdReplyerName;
	}

	/**
	 * @param fdReplyerName
	 *            回复者的名称
	 */
	public void setFdReplyerName(String fdReplyerName) {
		this.fdReplyerName = fdReplyerName;
	}
	
	/**
	 * 父回复id
	 */
	protected String fdParentId = null;
	
	public String getFdParentId() {
		return fdParentId;
	}

	public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}
	
	/**
	 * 父回复的回复者ID
	 */
	protected String fdParentReplyerId = null;

	/**
	 * @return 父回复的回复者ID
	 */
	public String getFdParentReplyerId() {
		return fdParentReplyerId;
	}

	/**
	 * @param fdParentReplyerId
	 *            父回复的回复者ID
	 */
	public void setFdParentReplyerId(String fdParentReplyerId) {
		this.fdParentReplyerId = fdParentReplyerId;
	}

	/**
	 * 父回复的回复者名称
	 */
	protected String fdParentReplyerName = null;

	/**
	 * @return 父回复的回复者名称
	 */
	public String getFdParentReplyerName() {
		return fdParentReplyerName;
	}

	/**
	 * @param fdParentReplyerName
	 *            父回复的回复者名称
	 */
	public void setFdParentReplyerName(String fdParentReplyerName) {
		this.fdParentReplyerName = fdParentReplyerName;
	}
	/**
	 * 层级ID
	 */
	protected java.lang.String fdHierarchyId;

	/**
	 * @return 返回 层级ID
	 */
	public java.lang.String getFdHierarchyId() {
		return fdHierarchyId;
	}

	/**
	 * @param fdHierarchyId
	 *            要设置的 层级ID
	 */
	public void setFdHierarchyId(java.lang.String fdHierarchyId) {
		this.fdHierarchyId = fdHierarchyId;
	}

	/**
	 * 点赞统计
	 */
	protected String docPraiseCount = null;

	public String getDocPraiseCount() {
		return docPraiseCount;
	}

	public void setDocPraiseCount(String docPraiseCount) {
		this.docPraiseCount = docPraiseCount;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docContent = null;
		fdReplyTime = null;
		fdModelId = null;
		fdModelName = null;
		fdReplyerId = null;
		docPraiseCount = null;
		fdReplyerName = null;
		fdParentId = null;
		fdParentReplyerId = null;
		fdParentReplyerName = null;
		fdHierarchyId = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysEvaluationReply.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdReplyerId", new FormConvertor_IDToModel(
					"fdReplyer", SysOrgPerson.class));
			toModelPropertyMap.put("fdParentReplyerId", new FormConvertor_IDToModel(
					"fdParentReplyer", SysOrgPerson.class));
		}
		return toModelPropertyMap;
	}

	/*
	 * 点赞
	 */
	protected SysPraiseForm sysPraiseForm = new SysPraiseForm();

	@Override
    public SysPraiseForm getPraiseForm() {
		return sysPraiseForm;
	}

	public void setPraiseForm(SysPraiseForm sysPraiseForm) {
		this.sysPraiseForm = sysPraiseForm;
	}
}
