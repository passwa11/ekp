package com.landray.kmss.third.feishu.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.feishu.dao.IThirdFeishuNotifyLogDao;
import com.landray.kmss.third.feishu.forms.ThirdFeishuNotifyLogForm;
import com.landray.kmss.third.feishu.model.ThirdFeishuNotifyLog;
import com.landray.kmss.third.feishu.service.IThirdFeishuNotifyLogService;
import com.landray.kmss.third.feishu.util.ThirdFeishuUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;

import java.util.Date;

public class ThirdFeishuNotifyLogServiceImp extends ExtendDataServiceImp implements IThirdFeishuNotifyLogService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdFeishuNotifyLog) {
            ThirdFeishuNotifyLog thirdFeishuNotifyLog = (ThirdFeishuNotifyLog) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdFeishuNotifyLog thirdFeishuNotifyLog = new ThirdFeishuNotifyLog();
        thirdFeishuNotifyLog.setDocCreateTime(new Date());
        ThirdFeishuUtil.initModelFromRequest(thirdFeishuNotifyLog, requestContext);
        return thirdFeishuNotifyLog;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdFeishuNotifyLog thirdFeishuNotifyLog = (ThirdFeishuNotifyLog) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public void clear(int days) throws Exception {
		((IThirdFeishuNotifyLogDao) getBaseDao()).clear(30);
	}

    @Override
    public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model,
                                         RequestContext requestContext) throws Exception {
        ThirdFeishuNotifyLogForm thirdFeishuNotifyLogForm = (ThirdFeishuNotifyLogForm) form;
        if(thirdFeishuNotifyLogForm.getFdType() == null){
            thirdFeishuNotifyLogForm.setFdType(1);
        }
        super.convertFormToModel(form, model, requestContext);
        return model;
    }

    @Override
    public IExtendForm convertModelToForm(IExtendForm form, IBaseModel model,
                                          RequestContext requestContext) throws Exception {
        ThirdFeishuNotifyLogForm thirdFeishuNotifyLogForm = (ThirdFeishuNotifyLogForm) form;
        super.convertModelToForm(thirdFeishuNotifyLogForm, model, requestContext);
        if(thirdFeishuNotifyLogForm.getFdType() == null){
            thirdFeishuNotifyLogForm.setFdType(1);
        }
        if(thirdFeishuNotifyLogForm.getFdType() == 1){
            thirdFeishuNotifyLogForm.setTypeText(ResourceUtil.getString("third-feishu:third.feishu.notify.log.type1"));
        }
        else if(thirdFeishuNotifyLogForm.getFdType() == 2){
            thirdFeishuNotifyLogForm.setTypeText(ResourceUtil.getString("third-feishu:third.feishu.notify.log.type2"));
        }
        return thirdFeishuNotifyLogForm;
    }
}
