package com.landray.kmss.third.ding.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingCardConfig;
import com.landray.kmss.third.ding.model.ThirdDingCardLog;
import com.landray.kmss.third.ding.model.ThirdDingCardMapping;
import com.landray.kmss.third.ding.service.IThirdDingCardConfigService;
import com.landray.kmss.third.ding.service.IThirdDingCardLogService;
import com.landray.kmss.third.ding.service.IThirdDingCardMappingService;
import com.landray.kmss.third.ding.util.DingInteractivecardUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ThirdDingCardConfigServiceImp extends ExtendDataServiceImp implements IThirdDingCardConfigService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingCardConfigServiceImp.class);

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingCardConfig) {
            ThirdDingCardConfig thirdDingCardConfig = (ThirdDingCardConfig) model;
            thirdDingCardConfig.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingCardConfig thirdDingCardConfig = new ThirdDingCardConfig();
        thirdDingCardConfig.setDocCreateTime(new Date());
        thirdDingCardConfig.setDocAlterTime(new Date());
        thirdDingCardConfig.setFdType(String.valueOf("IM"));
        thirdDingCardConfig.setFdStatus(String.valueOf("1"));
        ThirdDingUtil.initModelFromRequest(thirdDingCardConfig, requestContext);
        return thirdDingCardConfig;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingCardConfig thirdDingCardConfig = (ThirdDingCardConfig) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    private IThirdDingCardLogService thirdDingCardLogService = null;
    public IThirdDingCardLogService getThirdDingCardLogService(){
        if(thirdDingCardLogService==null){
            thirdDingCardLogService = (IThirdDingCardLogService) SpringBeanUtil.getBean("thirdDingCardLogService");
        }
        return thirdDingCardLogService;
    }

    private IThirdDingCardMappingService thirdDingCardMappingService=null;
    public IThirdDingCardMappingService getThirdDingCardMappingService(){
        if(thirdDingCardMappingService==null){
            thirdDingCardMappingService = (IThirdDingCardMappingService) SpringBeanUtil.getBean("thirdDingCardMappingService");
        }
        return thirdDingCardMappingService;
    }

    @Override
    public void addInteractivecard(JSONObject request,String title, String receiver,String modelName,String modelId,String from,String outTrackId) throws Exception {
        if(!"true".equals(DingConfig.newInstance().getInteractiveCardEnable())){
            logger.warn("---------钉钉集成配置中互动卡片开关未开启，禁止推送----------");
            return;
        }
        //记录日志
        ThirdDingCardLog cardLog = new ThirdDingCardLog();
        cardLog.setFdCardId(request.getString("cardTemplateId"));
        cardLog.setFdName(title);
        cardLog.setFdModelId(modelId);
        cardLog.setFdModelName(modelName);
        try {
            cardLog.setFdRequest(request.toString());
            cardLog.setFdRequestTime(new Date());
            Long beforeTime = System.currentTimeMillis();
            JSONObject result = DingUtils.getDingApiService().sendInteractiveCard(request);
            cardLog.setFdConsumingTime(System.currentTimeMillis()-beforeTime);
            cardLog.setFdResponseTime(new Date());
            cardLog.setFdResponse(result==null?"":result.toString());
            cardLog.setFdRequestUrl(DingConstant.DING_API_PREFIX+"/v1.0/im/interactiveCards/send");
            if(result!=null&&result.containsKey("success")&&result.getBoolean("success")){
                cardLog.setFdStatus(true);
                logger.info("--建立映射关系--");
                //建立映射关系
                saveMappingRelation(modelName,modelId,receiver,request.getString("cardTemplateId"),title,from,outTrackId);
            }else{
                cardLog.setFdStatus(false);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally {
            getThirdDingCardLogService().add(cardLog);
        }
    }

    private void saveMappingRelation(String modelName, String modelId, String receiver,String cardId,String title,String from,String outTrackId) {
        try {
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setWhereBlock("fdOutTrackId=:fdOutTrackId");
            hqlInfo.setParameter("fdOutTrackId",outTrackId);
            ThirdDingCardMapping thirdDingCardMapping = (ThirdDingCardMapping) getThirdDingCardMappingService().findFirstOne(hqlInfo);
            if(thirdDingCardMapping != null){
                //历史接收人
                String localUsers   = thirdDingCardMapping.getFdReceiverUsers();
                if(StringUtil.isNotNull(localUsers)){
                    //去重
                    List<String> users = Arrays.asList(localUsers.split(";"));
                    if(users.contains(receiver)){
                        return; //之前发送过卡片了
                    }else{
                        localUsers+=";"+receiver;
                    }
                }else{
                    localUsers=receiver;
                }
                thirdDingCardMapping.setFdReceiverUsers(localUsers);
                thirdDingCardMapping.setDocAlterTime(new Date());
                getThirdDingCardMappingService().update(thirdDingCardMapping);
            }else{
                thirdDingCardMapping=new ThirdDingCardMapping();
                thirdDingCardMapping.setFdCardId(cardId);
                thirdDingCardMapping.setFdName(title);
                thirdDingCardMapping.setFdModelId(modelId);
                thirdDingCardMapping.setFdModelName(modelName);
                thirdDingCardMapping.setDocCreateTime(new Date());
                thirdDingCardMapping.setFdOutTrackId(outTrackId);
                thirdDingCardMapping.setFdFrom(from);
                thirdDingCardMapping.setFdReceiverUsers(receiver);
                getThirdDingCardMappingService().add(thirdDingCardMapping);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Override
    public void updateInteractivecard(JSONObject request,String title,String modelName,String modelId) throws Exception {
        if(!"true".equals(DingConfig.newInstance().getInteractiveCardEnable())){
            logger.warn("---------钉钉集成配置中互动卡片开关未开启，禁止推送----------");
            return;
        }
        ThirdDingCardLog cardLog = new ThirdDingCardLog();
        cardLog.setFdName(title);
        cardLog.setFdModelId(modelId);
        cardLog.setFdModelName(modelName);
        try {
            cardLog.setFdRequest(request.toString());
            cardLog.setFdRequestTime(new Date());
            Long beforeTime = System.currentTimeMillis();
            JSONObject result = DingUtils.getDingApiService().updateInteractiveCard(request);;
            cardLog.setFdConsumingTime(System.currentTimeMillis()-beforeTime);
            cardLog.setFdResponseTime(new Date());
            cardLog.setFdResponse(result==null?"":result.toString());
            cardLog.setFdRequestUrl(DingConstant.DING_API_PREFIX+"/v1.0/im/interactiveCards");
            if(result!=null&&result.containsKey("success")&&result.getBoolean("success")){
                cardLog.setFdStatus(true);
            }else{
                cardLog.setFdStatus(false);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally {
            getThirdDingCardLogService().add(cardLog);
        }
    }

    @Override
    public ThirdDingCardConfig getCardByModel(String modelName, String mainId) {
        HQLInfo hqlInfo= new HQLInfo();
        hqlInfo.setWhereBlock("fdModelName=:fdModelName and fdStatus=:fdStatus");
        hqlInfo.setParameter("fdModelName",modelName);
        hqlInfo.setParameter("fdStatus","1");
        hqlInfo.setOrderBy("docCreateTime asc");
        try {
            List<ThirdDingCardConfig> list =findList(hqlInfo);
            if(list!=null&&list.size()>0){
                if("com.landray.kmss.km.review.model.KmReviewMain".equals(modelName)){
                    //获取主文档信息
                    String templateId=DingInteractivecardUtil.getKmReviewTemplateId(mainId);
                    ThirdDingCardConfig defaultConfig=null;
                    for (int i=0;i<list.size();i++){
                        ThirdDingCardConfig config = list.get(i);
                        if(StringUtil.isNull(config.getFdTemplateId())){
                            defaultConfig = list.get(i);
                        }
                        if(StringUtil.isNotNull(templateId)&&templateId.equals(config.getFdTemplateId())){
                            return list.get(i);
                        }
                    }
                    return defaultConfig;

                }else{
                    return list.get(0);
                }
            }else {
                return null;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

}
