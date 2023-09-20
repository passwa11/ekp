package com.landray.kmss.sys.evaluation.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseCoreInnerModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.evaluation.forms.SysEvaluationMainForm;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.praise.interfaces.ISysPraiseMain;
import com.landray.kmss.util.AutoHashMap;

/**
 * 创建日期 2006-九月-01
 * 
 * @author 叶中奇 点评机制
 */
public class SysEvaluationMain extends BaseCoreInnerModel 
						implements ISysNotifyModel,ISysPraiseMain,IAttachment {
	/*
	 * 点评时间
	 */
	protected java.util.Date fdEvaluationTime = new Date();

	/*
	 * 点评分数
	 */
	protected java.lang.Long fdEvaluationScore;

	/*
	 * 点评内容
	 */
	protected java.lang.String fdEvaluationContent;

	/*
	 * 点评者
	 */
	protected SysOrgElement fdEvaluator = null;
	/*
	 * 是否最新版本
	 */
	private Boolean fdIsNewVersion = null;

	public Boolean getFdIsNewVersion() {
		return fdIsNewVersion;
	}

	public void setFdIsNewVersion(Boolean fdIsNewVersion) {
		this.fdIsNewVersion = fdIsNewVersion;
	}

	public SysEvaluationMain() {
		super();
	}

	/**
	 * @return 返回 点评时间
	 */
	public java.util.Date getFdEvaluationTime() {
		return fdEvaluationTime;
	}

	/**
	 * @param fdEvaluationTime
	 *            要设置的 点评时间
	 */
	public void setFdEvaluationTime(java.util.Date fdEvaluationTime) {
		this.fdEvaluationTime = fdEvaluationTime;
	}

	/**
	 * @return 返回 点评分数
	 */
	public java.lang.Long getFdEvaluationScore() {
		return fdEvaluationScore;
	}

	/**
	 * @param fdEvaluationScore
	 *            要设置的 点评分数
	 */
	public void setFdEvaluationScore(java.lang.Long fdEvaluationScore) {
		this.fdEvaluationScore = fdEvaluationScore;
	}

	/**
	 * @return 返回 点评内容
	 */
	public java.lang.String getFdEvaluationContent() {
		return fdEvaluationContent;
	}

	/**
	 * @param fdEvaluationContent
	 *            要设置的 点评内容
	 */
	public void setFdEvaluationContent(java.lang.String fdEvaluationContent) {
		this.fdEvaluationContent = fdEvaluationContent;
	}

	public SysOrgElement getFdEvaluator() {
		return fdEvaluator;
	}

	public void setFdEvaluator(SysOrgElement fdEvaluator) {
		this.fdEvaluator = fdEvaluator;
	}

	@Override
    public Class getFormClass() {
		return SysEvaluationMainForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
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
	
	/**
	 * 操作ip
	 */
	protected String fdIp;
	
	/**
	 * @return 操作ip
	 */
	public String getFdIp() {
		return fdIp;
	}
	
	/**
	 * @param fdIp
	 *            操作ip
	 */
	public void setFdIp(String fdIp) {
		this.fdIp = fdIp;
	}
	
	protected String fdParentId;

	public String getFdParentId() {
		return fdParentId;
	}

	public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}

	
	/**
	 * 附件实现
	 */
	private AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	
	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}
}
