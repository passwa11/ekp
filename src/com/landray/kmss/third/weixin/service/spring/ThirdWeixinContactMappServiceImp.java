package com.landray.kmss.third.weixin.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.service.IThirdWeixinContactMappService;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.third.weixin.model.ThirdWeixinContactMapp;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.common.forms.IExtendForm;

public class ThirdWeixinContactMappServiceImp extends ExtendDataServiceImp implements IThirdWeixinContactMappService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWeixinContactMapp) {
            ThirdWeixinContactMapp thirdWeixinContactMapp = (ThirdWeixinContactMapp) model;
            thirdWeixinContactMapp.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWeixinContactMapp thirdWeixinContactMapp = new ThirdWeixinContactMapp();
        thirdWeixinContactMapp.setDocCreateTime(new Date());
        thirdWeixinContactMapp.setDocAlterTime(new Date());
        thirdWeixinContactMapp.setDocCreator(UserUtil.getUser());
        ThirdWeixinUtil.initModelFromRequest(thirdWeixinContactMapp, requestContext);
        return thirdWeixinContactMapp;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWeixinContactMapp thirdWeixinContactMapp = (ThirdWeixinContactMapp) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    @Override
    public List<ThirdWeixinContactMapp> findByContactId(String contactId) throws Exception {
        return findList("fdContactUserId='"+contactId+"'",null);
    }

    @Override
    public ThirdWeixinContactMapp findByContactAndOrgType(String contactId, String orgTypeId) throws Exception {
        List list = findList("fdContactUserId='"+contactId+"' and fdOrgTypeId='"+orgTypeId+"'",null);
        if(list==null || list.isEmpty()){
            return null;
        }
        if(list.size()==1){
            return (ThirdWeixinContactMapp)list.get(0);
        }else{
            throw new Exception("映射表数据重复，在同一个组织类型（"+orgTypeId+"）下存在多个客户（"+orgTypeId+"）");
        }
    }

    @Override
    public Long getMappRecordCount() throws Exception {
        HQLInfo info = new HQLInfo();
        info.setGettingCount(true);
        Long count = 0L;
        List<Long> list = findValue(info);
        if (list.size() > 0) {
            count = list.get(0);
        }
        return count;
    }

    @Override
    public List<ThirdWeixinContactMapp> findContactMapp(String orgTypeId, String tagId) throws Exception {
        List list = findList("fdTagId like '%"+tagId+";%' and fdOrgTypeId='"+orgTypeId+"'",null);
        return list;
    }
}
