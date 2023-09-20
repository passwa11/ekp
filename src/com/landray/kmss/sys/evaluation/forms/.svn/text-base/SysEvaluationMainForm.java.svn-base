package com.landray.kmss.sys.evaluation.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.BaseCoreInnerForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.evaluation.model.SysEvaluationMain;
import com.landray.kmss.sys.praise.forms.SysPraiseForm;
import com.landray.kmss.sys.praise.interfaces.ISysPraiseForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2006-九月-01
 * 
 * @author 叶中奇
 */
public class SysEvaluationMainForm extends BaseCoreInnerForm 
							implements ISysPraiseForm, IAttachmentForm {
	/*
	 * 通知类型
	 */
	private String fdNotifyType;

	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}

	/*
	 * 点评者名称
	 */
	private String fdEvaluatorName = null;

	/*
	 * 点评时间
	 */
	private String fdEvaluationTime = null;

	/*
	 * 点评分数
	 */
	private String fdEvaluationScore = null;

	/*
	 * 点评内容
	 */
	private String fdEvaluationContent = null;

	/*
	 * 是否为新版本
	 */
	private String fdIsNewVersion = null;

	public String getFdIsNewVersion() {
		return fdIsNewVersion;
	}

	public void setFdIsNewVersion(String isNewVersion) {
		this.fdIsNewVersion = isNewVersion;
	}

	/**
	 * @return 返回 点评时间
	 */
	public String getFdEvaluationTime() {
		return fdEvaluationTime;
	}

	/**
	 * @param fdEvaluationTime
	 *            要设置的 点评时间
	 */
	public void setFdEvaluationTime(String fdEvaluationTime) {
		this.fdEvaluationTime = fdEvaluationTime;
	}

	/**
	 * @return 返回 点评分数
	 */
	public String getFdEvaluationScore() {
		return fdEvaluationScore;
	}

	/**
	 * @param fdEvaluationScore
	 *            要设置的 点评分数
	 */
	public void setFdEvaluationScore(String fdEvaluationScore) {
		this.fdEvaluationScore = fdEvaluationScore;
	}

	/**
	 * @return 返回 点评内容
	 */
	public String getFdEvaluationContent() {
		return fdEvaluationContent;
	}

	/**
	 * @param fdEvaluationContent
	 *            要设置的 点评内容
	 */
	public void setFdEvaluationContent(String fdEvaluationContent) {
		this.fdEvaluationContent = fdEvaluationContent;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.landray.kmss.web.action.ActionForm#reset(com.landray.kmss.web.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdEvaluationTime = null;
		fdEvaluationScore = null;
		fdEvaluationContent = null;
		fdEvaluatorName = null;
		docPraiseCount = null;
		fdReplyCount = null;
		fdIp = null;
		setFdEvaluatorName(UserUtil.getUser().getFdName());
		setFdEvaluationTime(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, request.getLocale()));
		fdNotifyType = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysEvaluationMain.class;
	}

	public String getFdEvaluatorName() {
		return fdEvaluatorName;
	}

	public void setFdEvaluatorName(String fdEvaluatorName) {
		this.fdEvaluatorName = fdEvaluatorName;
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
	
	/**
	 * 操作ip
	 */
	protected String fdIp = null;

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
	
	private AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	
	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}
	
}
