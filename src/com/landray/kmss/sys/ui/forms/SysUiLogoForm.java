package com.landray.kmss.sys.ui.forms;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.upload.FormFile;

public class SysUiLogoForm extends ActionForm {
	private FormFile file;

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}
}
