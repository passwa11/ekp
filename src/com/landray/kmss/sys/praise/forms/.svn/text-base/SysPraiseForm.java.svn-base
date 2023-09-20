package com.landray.kmss.sys.praise.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.BaseCoreInnerForm;
import com.landray.kmss.sys.praise.model.SysPraiseMain;

public class SysPraiseForm extends BaseCoreInnerForm {
	/*
	 * 是否显示
	 */
	private String fdIsShow;
	/*
	 * 点赞次数
	 */
	private String fdPraiseCount = "0";

	@Override
    public Class getModelClass() {
		return SysPraiseMain.class;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdIsShow = null;
		super.reset(mapping, request);
	}

	public String getFdPraiseCount() {
		return fdPraiseCount;
	}

	public void setFdPraiseCount(String fdPraiseCount) {
		this.fdPraiseCount = fdPraiseCount;
	}

	public String getFdIsShow() {
		return fdIsShow;
	}

	public void setFdIsShow(String fdIsShow) {
		this.fdIsShow = fdIsShow;
	}
}
