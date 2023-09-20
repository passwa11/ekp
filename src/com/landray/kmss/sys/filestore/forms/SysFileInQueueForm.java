package com.landray.kmss.sys.filestore.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.BaseForm;

public class SysFileInQueueForm extends BaseForm {
	private static final long serialVersionUID = -8388308550534250361L;
	/**
	 * 入队类型
	 */
	private String fdInqueueType;

	public String getFdInqueueType() {
		return fdInqueueType;
	}

	public void setFdInqueueType(String fdInqueueType) {
		this.fdInqueueType = fdInqueueType;
	}

	/**
	 * 范围（应用模块）
	 */
	private String[] fdScope;

	public String[] getFdScope() {
		return fdScope;
	}

	public void setFdScope(String[] fdScope) {
		this.fdScope = fdScope;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdInqueueType = "1";
		fdScope = null;
		super.reset(mapping, request);
	}
}
