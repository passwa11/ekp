package com.landray.kmss.sys.attachment.service.spring;

import java.util.Date;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attachment.model.SysAttachmentWps;
import com.landray.kmss.sys.attachment.service.ISysAttachmentWpsService;
import com.landray.kmss.sys.attachment.util.SysAttachmentUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

public class SysAttachmentWpsServiceImp extends ExtendDataServiceImp implements ISysAttachmentWpsService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysAttachmentWps) {
            SysAttachmentWps sysAttachmentWps = (SysAttachmentWps) model;
            sysAttachmentWps.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        SysAttachmentWps sysAttachmentWps = new SysAttachmentWps();
        sysAttachmentWps.setDocCreateTime(new Date());
        sysAttachmentWps.setDocAlterTime(new Date());
		sysAttachmentWps.setDocCreator(UserUtil.getUser());
        SysAttachmentUtil.initModelFromRequest(sysAttachmentWps, requestContext);
        return sysAttachmentWps;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysAttachmentWps sysAttachmentWps = (SysAttachmentWps) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
