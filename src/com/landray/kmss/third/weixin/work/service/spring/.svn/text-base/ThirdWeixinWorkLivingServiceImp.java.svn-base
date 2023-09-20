package com.landray.kmss.third.weixin.work.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkLiving;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinWorkLivingService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.common.forms.IExtendForm;

public class ThirdWeixinWorkLivingServiceImp extends ExtendDataServiceImp implements IThirdWeixinWorkLivingService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWeixinWorkLiving) {
            ThirdWeixinWorkLiving thirdWeixinWorkLiving = (ThirdWeixinWorkLiving) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWeixinWorkLiving thirdWeixinWorkLiving = new ThirdWeixinWorkLiving();
        thirdWeixinWorkLiving.setDocCreateTime(new Date());
        thirdWeixinWorkLiving.setDocCreator(UserUtil.getUser());
        ThirdWeixinUtil.initModelFromRequest(thirdWeixinWorkLiving, requestContext);
        return thirdWeixinWorkLiving;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWeixinWorkLiving thirdWeixinWorkLiving = (ThirdWeixinWorkLiving) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    @Override
    public void deleteByLivingId(String fdLivingId) throws Exception{
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fdLivingId=:fdLivingId");
        hqlInfo.setParameter("fdLivingId",fdLivingId);
        List<ThirdWeixinWorkLiving> list =getBaseDao().findList(hqlInfo);
        for(ThirdWeixinWorkLiving living:list){
            getBaseDao().delete(living);
        }
    }
}
