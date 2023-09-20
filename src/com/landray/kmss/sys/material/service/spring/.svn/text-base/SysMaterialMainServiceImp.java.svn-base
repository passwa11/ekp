package com.landray.kmss.sys.material.service.spring;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.material.forms.SysMaterialMainForm;
import com.landray.kmss.sys.material.model.SysMaterialMain;
import com.landray.kmss.sys.material.service.ISysMaterialMainService;
import com.landray.kmss.util.UserUtil;

public class SysMaterialMainServiceImp extends BaseServiceImp implements
		ISysMaterialMainService {

	@Override
    public void saveData(IExtendForm form) throws Exception {
		SysMaterialMainForm mainForm = (SysMaterialMainForm) form;
		Map<?, ?> map = mainForm.getAttachmentForms();
		for (Entry<?, ?> entry : map.entrySet()) {
			addModel(mainForm, entry);
		}
	}

	private void addModel(SysMaterialMainForm mainForm, Entry<?, ?> entry)
			throws Exception {
		String key = (String) entry.getKey();
		AttachmentDetailsForm attachmentForm = (AttachmentDetailsForm) entry.getValue();
		String ids[] = attachmentForm.getAttachmentIds().split(";");
		for (int i = 0; i < ids.length; i++) {
			if (ids[i] != null && ids[i].trim().length() != 0) {
				SysMaterialMain model = new SysMaterialMain();
				model.setDocCreateTime(new Date());
				model.setDocCreator(UserUtil.getUser());
				model.setFdModelName(mainForm.getFdModelName());
				model.setFdModelTitle(mainForm.getFdModelTitle());
				model.setFdType(mainForm.getFdType());
				model.setFdAttId(ids[i]);
				AttachmentDetailsForm attachment = new AttachmentDetailsForm();
				attachment.setAttachmentIds(ids[i]);
				model.getAttachmentForms().put(key, attachment);
				super.add(model);
			}
		}
	}

}
