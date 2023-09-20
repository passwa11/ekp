package com.landray.kmss.third.weixin.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatGroup;
import com.landray.kmss.third.weixin.service.IThirdWeixinChatGroupService;
import com.landray.kmss.third.weixin.util.ThirdWeixinUtil;
import com.landray.kmss.util.MD5Util;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import java.util.Date;

public class ThirdWeixinChatGroupServiceImp extends ExtendDataServiceImp implements IThirdWeixinChatGroupService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdWeixinChatGroup) {
            ThirdWeixinChatGroup thirdWeixinChatGroup = (ThirdWeixinChatGroup) model;
            thirdWeixinChatGroup.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdWeixinChatGroup thirdWeixinChatGroup = new ThirdWeixinChatGroup();
        thirdWeixinChatGroup.setFdIsOut(Boolean.valueOf("false"));
        thirdWeixinChatGroup.setDocCreateTime(new Date());
        thirdWeixinChatGroup.setDocAlterTime(new Date());
        ThirdWeixinUtil.initModelFromRequest(thirdWeixinChatGroup, requestContext);
        return thirdWeixinChatGroup;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdWeixinChatGroup thirdWeixinChatGroup = (ThirdWeixinChatGroup) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    @Override
    public ThirdWeixinChatGroup findByMd5(String md5) throws Exception {
        HQLInfo info = new HQLInfo();
        info.setWhereBlock("fdMd5 = :md5");
        info.setParameter("md5",md5);
        return (ThirdWeixinChatGroup) findFirstOne(info);
    }

    @Override
    public String genRelateUserId(String fromId, String toId){
        String fdRelateUserId = "";
        int compare = fromId.compareTo(toId);
        if(compare<0){
            fdRelateUserId = "#"+fromId+"#"+toId;
        }else{
            fdRelateUserId = "#"+toId+"#"+fromId;
        }
        return fdRelateUserId;
    }

    @Override
    public String genMd5(String fromId, String toId, String roomId){
        if(StringUtil.isNotNull(roomId)){
            return MD5Util.getMD5String(roomId);
        }
        return MD5Util.getMD5String(genRelateUserId(fromId,toId));
    }

    @Override
    public ThirdWeixinChatGroup findByRoomId(String roomId) throws Exception {
        HQLInfo info = new HQLInfo();
        info.setWhereBlock("fdRoomId = :roomId");
        info.setParameter("roomId",roomId);
        return (ThirdWeixinChatGroup) findFirstOne(info);
    }
}
