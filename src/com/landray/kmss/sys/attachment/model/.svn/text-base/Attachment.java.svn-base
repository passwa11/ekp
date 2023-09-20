package com.landray.kmss.sys.attachment.model;

import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.util.AutoHashMap;

public class Attachment implements IAttachment {
	public AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return attachmentForms;
	}

	public void setAttachmentForms(AutoHashMap attachmentForms) {
		this.attachmentForms = attachmentForms;
	}
}
