package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

import com.landray.kmss.common.forms.ExtendForm;

public abstract class HrStaffImportForm extends ExtendForm {
	private static final long serialVersionUID = 1L;
	// 上传的文件
	private FormFile file;

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.file = null;
	}

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

}
