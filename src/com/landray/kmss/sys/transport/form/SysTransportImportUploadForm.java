package com.landray.kmss.sys.transport.form;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.forms.BaseForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

public class SysTransportImportUploadForm extends BaseForm {
	private FormFile file;

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.file = null;
		super.reset(mapping, request);
	}
}
