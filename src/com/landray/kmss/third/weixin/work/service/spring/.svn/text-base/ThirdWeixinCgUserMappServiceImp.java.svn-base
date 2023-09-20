package com.landray.kmss.third.weixin.work.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinCgUserMappService;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinCgUserMapp;
import com.landray.kmss.common.model.IBaseModel;

public class ThirdWeixinCgUserMappServiceImp extends ExtendDataServiceImp implements IThirdWeixinCgUserMappService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWeixinCgUserMapp) {
            ThirdWeixinCgUserMapp thirdWeixinCgUserMapp = (ThirdWeixinCgUserMapp) model;
            thirdWeixinCgUserMapp.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWeixinCgUserMapp thirdWeixinCgUserMapp = new ThirdWeixinCgUserMapp();
        thirdWeixinCgUserMapp.setFdIsAvailable(Boolean.valueOf("true"));
        thirdWeixinCgUserMapp.setDocCreateTime(new Date());
        thirdWeixinCgUserMapp.setDocAlterTime(new Date());
        ThirdWeixinUtil.initModelFromRequest(thirdWeixinCgUserMapp, requestContext);
        return thirdWeixinCgUserMapp;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWeixinCgUserMapp thirdWeixinCgUserMapp = (ThirdWeixinCgUserMapp) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    @Override
    public ThirdWeixinCgUserMapp findByUserId(String corpId, String userId) throws Exception {
        HQLInfo info = new HQLInfo();
        info.setWhereBlock("fdCorpId =:corpId and fdUserId =:userId");
        info.setParameter("corpId",corpId);
        info.setParameter("userId",userId);
        List list = findList(info);
        if(list==null || list.isEmpty()){
            return null;
        }else if(list.size()>1){
            throw new Exception("用户映射数据存在重复，corpId:"+corpId+", userId:"+userId);
        }else{
            return (ThirdWeixinCgUserMapp)list.get(0);
        }
    }

    @Override
    public ThirdWeixinCgUserMapp findByEkpId(String ekpId) throws Exception {
        HQLInfo info = new HQLInfo();
        info.setWhereBlock("fdEkpId =:ekpId");
        info.setParameter("ekpId",ekpId);
        List list = findList(info);
        if(list==null || list.isEmpty()){
            return null;
        }else if(list.size()>1){
            throw new Exception("用户映射数据存在重复，ekpId:"+ekpId);
        }else{
            return (ThirdWeixinCgUserMapp)list.get(0);
        }
    }
}
