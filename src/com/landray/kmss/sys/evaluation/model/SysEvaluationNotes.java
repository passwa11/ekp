package com.landray.kmss.sys.evaluation.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseCoreInnerModel;
import com.landray.kmss.sys.evaluation.forms.SysEvaluationNotesForm;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.praise.interfaces.ISysPraiseMain;

public class SysEvaluationNotes extends BaseCoreInnerModel 
							implements ISysNotifyModel,ISysPraiseMain{
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
	protected Date fdEvaluationTime;
	
	/**
	 * @return 点评时间
	 */
	public Date getFdEvaluationTime() {
		return fdEvaluationTime;
	}
	
	/**
	 * @param fdEvaluationTime 点评时间
	 */
	public void setFdEvaluationTime(Date fdEvaluationTime) {
		this.fdEvaluationTime = fdEvaluationTime;
	}
	
	/**
	 * 点评内容
	 */
	protected String fdEvaluationContent;
	
	/**
	 * @return 点评内容
	 */
	public String getFdEvaluationContent() {
		return fdEvaluationContent;
	}
	
	/**
	 * @param fdEvaluationContent 点评内容
	 */
	public void setFdEvaluationContent(String fdEvaluationContent) {
		this.fdEvaluationContent = fdEvaluationContent;
	}
	
	protected String docSubject;
	
	
	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	/**
	 * 文档ID
	 */
	protected String fdModelId;
	
	/**
	 * @return 文档ID
	 */
	@Override
    public String getFdModelId() {
		return fdModelId;
	}
	
	/**
	 * @param fdModelId 文档ID
	 */
	@Override
    public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}
	
	/**
	 * 模块名称
	 */
	protected String fdModelName;
	
	/**
	 * @return 模块名称
	 */
	@Override
    public String getFdModelName() {
		return fdModelName;
	}
	
	/**
	 * @param fdModelName 模块名称
	 */
	@Override
    public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}
	
	/**
	 * 创建人
	 */
	protected SysOrgElement fdEvaluator;
	
	/**
	 * @return 创建人
	 */
	public SysOrgElement getFdEvaluator() {
		return fdEvaluator;
	}
	
	/**
	 * @param fdEvaluator 创建人
	 */
	public void setFdEvaluator(SysOrgElement fdEvaluator) {
		this.fdEvaluator = fdEvaluator;
	}
	
	@Override
    public Class getFormClass() {
		return SysEvaluationNotesForm.class;
	}
	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdEvaluator.fdId", "fdEvaluatorId");
			toFormPropertyMap.put("fdEvaluator.fdName", "fdEvaluatorName");
		}
		return toFormPropertyMap;
	}
	
	/**
	 * 点赞统计
	 */
	protected Integer docPraiseCount = Integer.valueOf(0);

	@Override
    public Integer getDocPraiseCount() {
		return docPraiseCount;
	}
	@Override
    public void setDocPraiseCount(Integer docPraiseCount) {
		this.docPraiseCount = docPraiseCount;
	}
	
	/**
	 * 回复数
	 */
	protected Integer fdReplyCount = Integer.valueOf(0);

	public Integer getFdReplyCount() {
		return fdReplyCount;
	}
	public void setFdReplyCount(Integer fdReplyCount) {
		this.fdReplyCount = fdReplyCount;
	}
}
