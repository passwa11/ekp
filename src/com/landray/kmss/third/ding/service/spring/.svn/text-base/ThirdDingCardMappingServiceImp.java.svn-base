package com.landray.kmss.third.ding.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.ding.model.ThirdDingCardMapping;
import com.landray.kmss.third.ding.service.IThirdDingCardMappingService;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Date;
import java.util.List;

public class ThirdDingCardMappingServiceImp extends ExtendDataServiceImp implements IThirdDingCardMappingService, ApplicationContextAware {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingCardMappingServiceImp.class);

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingCardMapping) {
            ThirdDingCardMapping thirdDingCardMapping = (ThirdDingCardMapping) model;
            thirdDingCardMapping.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingCardMapping thirdDingCardMapping = new ThirdDingCardMapping();
        thirdDingCardMapping.setDocCreateTime(new Date());
        thirdDingCardMapping.setDocAlterTime(new Date());
        ThirdDingUtil.initModelFromRequest(thirdDingCardMapping, requestContext);
        return thirdDingCardMapping;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingCardMapping thirdDingCardMapping = (ThirdDingCardMapping) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    @Override
    public ThirdDingCardMapping getMappingByModel(String modelName, String modelId) {
        HQLInfo hqlInfo= new HQLInfo();
        hqlInfo.setWhereBlock("fdModelName=:fdModelName and fdModelId=:fdModelId");
        hqlInfo.setParameter("fdModelName",modelName);
        hqlInfo.setParameter("fdModelId",modelId);
        try {
            return (ThirdDingCardMapping) findFirstOne(hqlInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    @Override
    public void updateDealWithCardCallback(String outTrackId, String fdId, JSONObject action) {
        //根据outTrackId找对应的映射
        HQLInfo hqlInfo= new HQLInfo();
        hqlInfo.setWhereBlock("fdOutTrackId=:fdOutTrackId");
        hqlInfo.setParameter("fdOutTrackId",outTrackId);
        try {
            List<ThirdDingCardMapping> list =findList(hqlInfo);
            if(list!=null&&list.size()>0){
                if(list.size()==1){
                    JSONObject params = new JSONObject();
                    params.put("fdModelId",list.get(0).getFdModelId());
                    params.put("fdModelName",list.get(0).getFdModelName());
                    params.put("userid",fdId);
                    String from="";
                    if(StringUtil.isNotNull(list.get(0).getFdFrom())){
                        from=list.get(0).getFdFrom();
                    }
                    params.put("from",from);
                    if(action!=null&&action.containsKey("params")){
                        params.put("action",action.getJSONObject("params"));
                    }else{
                        params.put("action",action);
                    }
                    logger.info("发送卡片回调：{}",params);
                    applicationContext.publishEvent(new Event_Common(
                            "dingInteractiveCardCallBack", params));
                }else{
                    logger.warn("存在多条映射关系，回调忽略，outTrackId：{}",outTrackId);
                }
            }else {
                logger.warn("没有找到映射，回调动作忽略，outTrackId：{}",outTrackId);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }
}
