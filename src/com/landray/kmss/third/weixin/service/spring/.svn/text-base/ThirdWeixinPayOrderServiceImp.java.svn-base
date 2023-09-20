package com.landray.kmss.third.weixin.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.service.IThirdWeixinPayOrderService;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.third.weixin.model.ThirdWeixinPayOrder;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.common.forms.IExtendForm;

public class ThirdWeixinPayOrderServiceImp extends ExtendDataServiceImp implements IThirdWeixinPayOrderService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWeixinPayOrder) {
            ThirdWeixinPayOrder thirdWeixinPayOrder = (ThirdWeixinPayOrder) model;
            thirdWeixinPayOrder.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWeixinPayOrder thirdWeixinPayOrder = new ThirdWeixinPayOrder();
        thirdWeixinPayOrder.setDocCreateTime(new Date());
        thirdWeixinPayOrder.setDocAlterTime(new Date());
        thirdWeixinPayOrder.setDocCreator(UserUtil.getUser());
        ThirdWeixinUtil.initModelFromRequest(thirdWeixinPayOrder, requestContext);
        return thirdWeixinPayOrder;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWeixinPayOrder thirdWeixinPayOrder = (ThirdWeixinPayOrder) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    @Override
    public ThirdWeixinPayOrder findOrder(String modelName, String modelId, String fdKey) throws Exception {
        HQLInfo info = new HQLInfo();
        String whereBlock = "fdModelName = :modelName and fdModelId = :modelId";
        if(StringUtil.isNotNull(fdKey)){
            whereBlock += " and fdKey = :fdKey";
        }
        info.setWhereBlock(whereBlock);
        info.setParameter("modelName",modelName);
        info.setParameter("modelId",modelId);
        if(StringUtil.isNotNull(fdKey)){
            info.setParameter("fdKey",fdKey);
        }
        List list = findList(info);
        if(list==null || list.isEmpty()){
            return null;
        }
        if(list.size()>1){
            throw new Exception("存在重复的订单记录，请检查数据。");
        }
        return (ThirdWeixinPayOrder)list.get(0);
    }
}
