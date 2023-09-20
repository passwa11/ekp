package com.landray.kmss.third.payment.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.payment.model.ThirdPaymentOrder;
import com.landray.kmss.third.payment.service.IThirdPaymentOrderService;
import com.landray.kmss.third.payment.util.ThirdPaymentUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import java.util.Date;
import java.util.List;

public class ThirdPaymentOrderServiceImp extends ExtendDataServiceImp implements IThirdPaymentOrderService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdPaymentOrder) {
            ThirdPaymentOrder thirdPaymentOrder = (ThirdPaymentOrder) model;
            thirdPaymentOrder.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdPaymentOrder thirdPaymentOrder = new ThirdPaymentOrder();
        thirdPaymentOrder.setDocCreateTime(new Date());
        thirdPaymentOrder.setDocAlterTime(new Date());
        thirdPaymentOrder.setDocCreator(UserUtil.getUser());
        ThirdPaymentUtil.initModelFromRequest(thirdPaymentOrder, requestContext);
        return thirdPaymentOrder;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdPaymentOrder thirdPaymentOrder = (ThirdPaymentOrder) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    @Override
    public ThirdPaymentOrder findOrder(String modelName, String modelId, String fdKey) throws Exception {
        HQLInfo info = new HQLInfo();
        String whereBlock = "fdModelName=:modelName and fdModelId=:modelId";
        info.setParameter("modelName",modelName);
        info.setParameter("modelId",modelId);
        if(StringUtil.isNotNull(fdKey)){
            whereBlock += " and fdKey=:fdKey";
            info.setParameter("fdKey",fdKey);
        }
        info.setWhereBlock(whereBlock);
        List list = findList(info);
        if(list==null || list.isEmpty()){
            return null;
        }
        if(list.size()>1){
            throw new Exception("存在重复的订单记录，请检查数据。");
        }
        return (ThirdPaymentOrder)list.get(0);
    }
}
