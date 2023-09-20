package com.landray.kmss.sys.evaluation.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.BaseCoreInnerForm;
import com.landray.kmss.sys.evaluation.model.SysEvaluationNotes;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.praise.forms.SysPraiseForm;
import com.landray.kmss.sys.praise.interfaces.ISysPraiseForm;

public class SysEvaluationNotesForm extends BaseCoreInnerForm 
						implements ISysPraiseForm{
	/**
	 * 通知类型
	 */
	private String fdNotifyType;

	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}
	/**
	 * 点评时间
	 */
	protected String fdEvaluationTime = null;

	/**
	 * @return 点评时间
	 */
	public String getFdEvaluationTime() {
		return fdEvaluationTime;
	}

	/**
	 * @param fdEvaluationTime
	 *            点评时间
	 */
	public void setFdEvaluationTime(String fdEvaluationTime) {
		this.fdEvaluationTime = fdEvaluationTime;
	}

	/**
	 * 点评内容
	 */
	protected String fdEvaluationContent = null;

	/**
	 * @return 点评内容
	 */
	public String getFdEvaluationContent() {
		return fdEvaluationContent;
	}

	/**
	 * @param fdEvaluationContent
	 *            点评内容
	 */
	public void setFdEvaluationContent(String fdEvaluationContent) {
		this.fdEvaluationContent = fdEvaluationContent;
	}

	/**
	 * 文档ID
	 */
	protected String fdModelId = null;

	/**
	 * @return 文档ID
	 */
	@Override
    public String getFdModelId() {
		return fdModelId;
	}

	/**
	 * @param fdModelId
	 *            文档ID
	 */
	@Override
    public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}
	/**
	 * 模块名称
	 */
	protected String fdModelName = null;

	/**
	 * @return 模块名称
	 */
	@Override
    public String getFdModelName() {
		return fdModelName;
	}

	/**
	 * @param fdModelName
	 *            模块名称
	 */
	@Override
    public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	/**
	 * 创建人的ID
	 */
	protected String fdEvaluatorId = null;

	/**
	 * @return 创建人的ID
	 */
	public String getFdEvaluatorId() {
		return fdEvaluatorId;
	}

	/**
	 * @param fdEvaluatorId
	 *            创建人的ID
	 */
	public void setFdEvaluatorId(String fdEvaluatorId) {
		this.fdEvaluatorId = fdEvaluatorId;
	}

	/**
	 * 创建人的名称
	 */
	protected String fdEvaluatorName = null;

	/**
	 * @return 创建人的名称
	 */
	public String getFdEvaluatorName() {
		return fdEvaluatorName;
	}

	/**
	 * @param fdEvaluatorName
	 *            创建人的名称
	 */
	public void setFdEvaluatorName(String fdEvaluatorName) {
		this.fdEvaluatorName = fdEvaluatorName;
	}
	
	protected String docSubject = null;
	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}
	
	/**
	 * 回复数
	 */
	protected String fdReplyCount = null;

	public String getFdReplyCount() {
		return fdReplyCount;
	}

	public void setFdReplyCount(String fdReplyCount) {
		this.fdReplyCount = fdReplyCount;
	}
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdEvaluationTime = null;
		fdEvaluationContent = null;
		fdModelId = null;
		fdModelName = null;
		fdEvaluatorId = null;
		fdEvaluatorName = null;
		docSubject = null;
		fdReplyCount = null;
		docPraiseCount = null;
		fdNotifyType = null;
		super.reset(mapping, request);
	}
	
	@Override
    public Class getModelClass() {
		return SysEvaluationNotes.class;
	}
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdEvaluatorId",
					new FormConvertor_IDToModel("fdEvaluator",
							SysOrgElement.class));
		}
		return toModelPropertyMap;
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
