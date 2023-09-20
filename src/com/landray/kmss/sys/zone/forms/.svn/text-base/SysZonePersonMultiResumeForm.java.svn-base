package com.landray.kmss.sys.zone.forms;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.util.AutoHashMap;

public class SysZonePersonMultiResumeForm extends ExtendForm implements
		IAttachmentForm {


	@Override
	public Class getModelClass() {
		return null;
	}
	
	
	private String [] fdLoginNames;
	
	public String [] getFdLoginNames() {
		return fdLoginNames;
	}

	public void setFdLoginNames(String[] fdLoginNames) {
		this.fdLoginNames = fdLoginNames;
	}


	/*
	 * 附件机制
	 */
	private AutoHashMap attachmentForms = new AutoHashMap(
			AttachmentDetailsForm.class);
	
	@Override
	public AutoHashMap getAttachmentForms() {
		return attachmentForms;
	}

}
