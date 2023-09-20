package com.landray.kmss.sys.evaluation.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.BaseCoreInnerForm;
import com.landray.kmss.sys.evaluation.model.SysEvaluationMain;

/**
 * 创建日期 2006-九月-21
 * 
 * @author 郭峰
 */
public class EvaluationForm extends BaseCoreInnerForm {
	/*
	 * 点评数
	 */
	private String fdEvaluateCount = null;
	
	/*
	 * 点评分数
	 */
	private String fdEvaluateScore = null;

	/*
	 * 是否已显示
	 */
	private String fdIsShow = null;

	/*
	 * 是否是新版本
	 */
	private String fdIsNewVersion = "true";

	public String getFdIsShow() {
		return fdIsShow;
	}

	public void setFdIsShow(String fdIsShow) {
		this.fdIsShow = fdIsShow;
	}
	
	/**
	 * 当前人是否已经评论过
	 */
	protected String fdIsCommented = "false";
	
	
	public String getFdIsCommented() {
		return fdIsCommented;
	}

	public void setFdIsCommented(String fdIsCommented) {
		this.fdIsCommented = fdIsCommented;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.landray.kmss.web.action.ActionForm#reset(com.landray.kmss.web.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdIsShow = null;
		fdIsNewVersion = "true";
		fdEvaluateCount = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysEvaluationMain.class;
	}

	public String getFdEvaluateCount() {
		return fdEvaluateCount;
	}
	
	public String getFdEvaluateScore() {
		return fdEvaluateScore;
	}

	public void setFdEvaluateScore(String fdEvaluateScore) {
		this.fdEvaluateScore = fdEvaluateScore;
	}

	public void setFdEvaluateCount(String fdEvaluateCount) {
		this.fdEvaluateCount = fdEvaluateCount;
	}

	public String getFdIsNewVersion() {
		return fdIsNewVersion;
	}

	public void setFdIsNewVersion(String fdIsNewVersion) {
		this.fdIsNewVersion = fdIsNewVersion;
	}
}
