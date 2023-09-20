package com.landray.kmss.third.weixin.work.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.third.weixin.work.model.ThirdWeixinCgUserMapp;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinCgDeptMappService;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinCgDeptMapp;
import com.landray.kmss.common.model.IBaseModel;

public class ThirdWeixinCgDeptMappServiceImp extends ExtendDataServiceImp implements IThirdWeixinCgDeptMappService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWeixinCgDeptMapp) {
            ThirdWeixinCgDeptMapp thirdWeixinCgDeptMapp = (ThirdWeixinCgDeptMapp) model;
            thirdWeixinCgDeptMapp.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWeixinCgDeptMapp thirdWeixinCgDeptMapp = new ThirdWeixinCgDeptMapp();
        thirdWeixinCgDeptMapp.setFdIsAvailable(Boolean.valueOf("true"));
        thirdWeixinCgDeptMapp.setDocCreateTime(new Date());
        thirdWeixinCgDeptMapp.setDocAlterTime(new Date());
        ThirdWeixinUtil.initModelFromRequest(thirdWeixinCgDeptMapp, requestContext);
        return thirdWeixinCgDeptMapp;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWeixinCgDeptMapp thirdWeixinCgDeptMapp = (ThirdWeixinCgDeptMapp) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    @Override
    public ThirdWeixinCgDeptMapp findByDeptId(String corpId, String deptId) throws Exception {
        HQLInfo info = new HQLInfo();
        info.setWhereBlock("fdCorpId =:corpId and fdWxDeptId =:deptId");
        info.setParameter("corpId",corpId);
        info.setParameter("deptId",deptId);
        List list = findList(info);
        if(list==null || list.isEmpty()){
            return null;
        }else if(list.size()>1){
            throw new Exception("部门映射数据存在重复，corpId:"+corpId+", deptId:"+deptId);
        }else{
            return (ThirdWeixinCgDeptMapp)list.get(0);
        }
    }
}
