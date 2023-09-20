package com.landray.kmss.fssc.fee.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.fssc.fee.service.IFsscFeeMappService;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import java.util.List;
import com.landray.kmss.fssc.fee.model.FsscFeeMapp;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.fssc.fee.util.FsscFeeUtil;
import com.landray.kmss.common.actions.RequestContext;

public class FsscFeeMappServiceImp extends ExtendDataServiceImp implements IFsscFeeMappService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscFeeMapp) {
            FsscFeeMapp fsscFeeMapp = (FsscFeeMapp) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscFeeMapp fsscFeeMapp = new FsscFeeMapp();
        FsscFeeUtil.initModelFromRequest(fsscFeeMapp, requestContext);
        return fsscFeeMapp;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscFeeMapp fsscFeeMapp = (FsscFeeMapp) model;
    }

    @Override
    public List<FsscFeeMapp> findByFdTemplateId(FsscFeeTemplate fdTemplateId) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscFeeMapp.fdTemplateId.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdTemplateId.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
