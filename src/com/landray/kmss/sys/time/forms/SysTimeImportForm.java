package com.landray.kmss.sys.time.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-29
 */
public abstract class SysTimeImportForm extends ExtendForm {

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

}
