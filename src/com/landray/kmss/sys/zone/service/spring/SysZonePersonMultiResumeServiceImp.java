package com.landray.kmss.sys.zone.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertQueueService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.zone.constant.SysZoneConstant;
import com.landray.kmss.sys.zone.forms.SysZonePersonMultiResumeForm;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.sys.zone.service.ISysZonePersonMultiResumeService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.sso.client.oracle.StringUtil;

public class SysZonePersonMultiResumeServiceImp extends BaseServiceImp
		implements ISysZonePersonMultiResumeService {

	@Override
	public String addResumes(IExtendForm form, RequestContext requestContext)
			throws Exception {
		SysZonePersonMultiResumeForm resumeForm = (SysZonePersonMultiResumeForm) form;
		String[] fdLoginNames = resumeForm.getFdLoginNames();
		IAttachmentForm attachmentForm = (IAttachmentForm) form;
		AutoHashMap atts = attachmentForm.getAttachmentForms();
		AttachmentDetailsForm attForm = (AttachmentDetailsForm) atts
				.get("multiResume");
		// 此时在sysAttMain已经产生 在提交文档的前一步时通过异步产生的
		String mainIds = attForm.getAttachmentIds();
		String[] attIds = mainIds.split(";");
		String modelName = "com.landray.kmss.sys.zone.model.SysZonePersonInfo";
		if (fdLoginNames != null && attIds != null) {
			ISysOrgCoreService service = (ISysOrgCoreService) SpringBeanUtil
					.getBean("sysOrgCoreService");
			ISysZonePersonInfoService sysZonePersonInfoService = (ISysZonePersonInfoService) SpringBeanUtil
					.getBean("sysZonePersonInfoService");
			ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
			ISysFileConvertQueueService queueService = (ISysFileConvertQueueService) SpringBeanUtil
					.getBean("sysFileConvertQueueService");
			for (int i = 0; i < fdLoginNames.length; i++) {
				if (StringUtil.isNull(fdLoginNames[i])) {
					continue;
				}
				SysOrgPerson person = service.findByLoginName(fdLoginNames[i]
						.trim());
				if (person != null) {
					sysZonePersonInfoService.updateGetPerson(person.getFdId());
					// 删除之前附件
					List attList = sysAttMainService.findByModelKey(modelName,
							person.getFdId(), SysZoneConstant.RESUME_KEY);
					if (!ArrayUtil.isEmpty(attList)){
						sysAttMainService.delete((IBaseModel) attList.get(0));
						if(UserOperHelper.allowLogOper("addResumes", "")){
							SysAttMain sysAttMain = (SysAttMain) attList.get(0);
							UserOperHelper.setModelNameAndModelDesc("com.landray.kmss.sys.attachment.model.SysAttMain");
							UserOperContentHelper.putAdd(sysAttMain.getFdId(), sysAttMain.getFdFileName(), sysAttMain.getFdModelName());
						}
					}

					SysAttMain sysAttMain = (SysAttMain) sysAttMainService
							.findByPrimaryKey(attIds[i]);
					sysAttMain.setFdKey(SysZoneConstant.RESUME_KEY);

					queueService.addQueue(sysAttMain.getFdFileId(),
							sysAttMain.getFdFileName(), modelName,
							person.getFdId(), null, sysAttMain.getFdId());
					sysAttMain.setFdModelId(person.getFdId());
					sysAttMain.setFdModelName(modelName);
					sysAttMain.setInputStream(null);
					sysAttMainService.update(sysAttMain);
					if(UserOperHelper.allowLogOper("addResumes", "")){
						UserOperHelper.setModelNameAndModelDesc("com.landray.kmss.sys.attachment.model.SysAttMain");
						UserOperContentHelper.putUpdate(sysAttMain.getFdId(), sysAttMain.getFdFileName(),null);
					}
				}
			}
		}
		return null;
	}

}
