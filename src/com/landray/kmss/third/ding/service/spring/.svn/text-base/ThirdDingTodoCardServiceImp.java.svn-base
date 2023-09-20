package com.landray.kmss.third.ding.service.spring;

import java.util.Date;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.third.ding.service.IThirdDingTodoCardService;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.ding.model.ThirdDingTodoCard;
import com.landray.kmss.common.model.IBaseModel;

public class ThirdDingTodoCardServiceImp extends ExtendDataServiceImp implements IThirdDingTodoCardService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingTodoCard) {
            ThirdDingTodoCard thirdDingTodoCard = (ThirdDingTodoCard) model;
            thirdDingTodoCard.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingTodoCard thirdDingTodoCard = new ThirdDingTodoCard();
        thirdDingTodoCard.setDocCreateTime(new Date());
        thirdDingTodoCard.setDocAlterTime(new Date());
        ThirdDingUtil.initModelFromRequest(thirdDingTodoCard, requestContext);
        return thirdDingTodoCard;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingTodoCard thirdDingTodoCard = (ThirdDingTodoCard) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
