package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

public class SysAttendImportForm extends ExtendAuthForm {
	private FormFile file;

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.file = null;
	}
	@Override
	public Class getModelClass() {
		return SysAttendImportForm.class;
	}

}
