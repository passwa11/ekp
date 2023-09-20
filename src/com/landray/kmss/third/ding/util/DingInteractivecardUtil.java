package com.landray.kmss.third.ding.util;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.formula.provider.ModelVarProvider;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingCardConfig;
import com.landray.kmss.third.ding.model.ThirdDingCardMapping;
import com.landray.kmss.third.ding.oms.DingOmsConfig;
import com.landray.kmss.third.ding.service.IThirdDingCardConfigService;
import com.landray.kmss.third.ding.service.IThirdDingCardMappingService;
import com.landray.kmss.third.ding.service.IThirdDingTodoTemplateService;
import com.landray.kmss.third.ding.xform.util.ThirdDingXFormTemplateUtil;
import com.landray.kmss.util.*;
import com.sunbor.web.tag.enums.ValueLabel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DingInteractivecardUtil {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingInteractivecardUtil.class);

    private static IThirdDingCardConfigService thirdDingCardConfigService= (IThirdDingCardConfigService) SpringBeanUtil.getBean("thirdDingCardConfigService");

    private static IThirdDingCardMappingService thirdDingCardMappingService= (IThirdDingCardMappingService) SpringBeanUtil.getBean("thirdDingCardMappingService");

    /**
     *  业务调用：把私有数据封装好并传递
     * @param baseModel 模块主文档
     * @param title  标题
     * @param privateData 私有数据，格式如下：
     *  {
     * 	"handler": {
     * 		"审批人fdId": {"canOperate": "Y"}
     *    },
     * 	"other": {
     * 		"showStatus": "Y"
     * 	}
     * }
     * @param cardDataCardParamMap 公有数据，格式如下：
     *  {
     *     "key" : "value"
     *  }
     * @param users 卡片接收人fdId
     */
    public static void sendCardByModel(BaseModel baseModel,String title, JSONObject privateData, JSONObject cardDataCardParamMap, List<String> users,String from){

        if(!"true".equals(DingConfig.newInstance().getInteractiveCardEnable())){
            logger.warn("---------钉钉集成配置中互动卡片开关未开启，禁止推送----------");
            return;
        }
        if(baseModel==null){
            logger.warn("-----model不能为空----");
            return;
        }
        if(users==null||users.size()==0){
            //取私有数据的人员id
            if(privateData==null||privateData.size()==0||!privateData.containsKey("handler") || privateData.getJSONObject("handler").size()==0){
                logger.warn("-----卡片接收人不能为空----");
                return;
            }
            users=new ArrayList<>(privateData.getJSONObject("handler").keySet());
        }
        String modelName = baseModel.getClass().getName();
        //根据模块和主文档去匹配卡片模块
        ThirdDingCardConfig thirdDingCardConfig = thirdDingCardConfigService.getCardByModel(modelName,baseModel.getFdId());
        if (thirdDingCardConfig==null){
            logger.warn("还没有在钉钉集成中配置模块({},文档ID:{})的卡片模板或者模板已被禁用，不支持推送",modelName,baseModel.getFdId());
            return;
        }
        Map<String,String> receiversMap = getUserMap(users);
        if(receiversMap==null || receiversMap.size()==0){
            return;
        }

        //根据配置获取公有数据
        Map<String,String>  publicCardData = getPublicCardData(baseModel,thirdDingCardConfig);
        if(cardDataCardParamMap!=null&&cardDataCardParamMap.size()>0){
            publicCardData.putAll(cardDataCardParamMap);
        }
        JSONObject pulicdatas = new JSONObject();
        for(String key:publicCardData.keySet()){
            pulicdatas.put(key,publicCardData.get(key));
        }
        //添加内置参数 pcUrl,mUrl,agreeText(肯定按钮的文案),refuseText(否定按钮的文案),displayText(状态按钮的文案)
        setInnerParam(pulicdatas,baseModel);
        logger.info("公有数据：{}",pulicdatas.toString());

        JSONObject dingPrivateDate = updatePrivateDateByModel(privateData, receiversMap,baseModel);

        //生成Md5卡片唯一ID
        String outTrackId = MD5Util.getMD5String(baseModel.getClass().getName()+baseModel.getFdId());
        logger.warn("outTrackId:"+outTrackId);

        //由于单聊单次只能发送一人，所以需要拆分多次发送，且最后需要更新一次
        for(String userid:receiversMap.values()){
            //由于第二个人起接收到卡片时发送时，和第一张卡片是一样的，所以私有数据一律不传，发送成功后会更新一下卡片
            sendCard(thirdDingCardConfig.getFdCardId(),null,pulicdatas,userid,baseModel.getFdId(),title,baseModel.getClass().getName(),from,outTrackId);
        }
        //更新卡片
        updateCard(baseModel,title,dingPrivateDate,pulicdatas,false,false,users);
    }

    private static Map<String, String> getUserMap(List<String> users) {
        Map<String,String> receiversMap = new HashMap<>();
        for(String fdId:users){
            String userid= DingUtil.getUseridByEkpId(fdId);
            if(StringUtil.isNotNull(userid)){
                receiversMap.put(fdId,userid);
            }else{
                logger.warn("用户：{} 在映射表没有相关数据，请先维护映射表数据",fdId);
                continue;
            }
        }
        return receiversMap;
    }

    /**
     *  业务调用
     * @param baseModel
     * @param privateData
     * @param cardDataCard
     */
    public static void updateCard(BaseModel baseModel,String title,JSONObject privateData,JSONObject cardDataCard, List<String> users){
        if(!"true".equals(DingConfig.newInstance().getInteractiveCardEnable())){
            logger.warn("---------钉钉集成配置中互动卡片开关未开启，禁止推送----------");
            return;
        }
        updateCard(baseModel,title,privateData,cardDataCard,true,true,users);
    }

    /**
     *
     * @param privateData
     *  {
     * 	  "handler": {
     * 		"处理人01-fdId": {
     * 			"canOperate": "Y"
     *                },
     * 		"处理人02-fdId": {
     * 			"showStatus": "Y"
     *        }
     *    },
     * 	 "other": {
     * 		"showStatus": "Y"
     * 	  }
     * }
     * @param receiversMap 如果为空，则全部接收人更新；若不为空，则仅更新map里面的人员私有数据
     * @param baseModel
     * @return
     */
    private static JSONObject updatePrivateDateByModel(JSONObject privateData, Map<String, String> receiversMap,BaseModel baseModel) {
        if(privateData==null||privateData.size()==0){
            return null;
        }
        JSONObject resultData = new JSONObject();
        if(privateData.containsKey("handler")){
            JSONObject approvers = privateData.getJSONObject("handler");
            for (Object key:approvers.keySet()){
                String fdId = (String)key;
                String userid = DingUtil.getUseridByEkpId(fdId);
                if (StringUtil.isNotNull(userid)) {
                    add2ResultData(resultData,userid,approvers.getJSONObject(fdId));
                }
            }
        }
        //非审批节点的用户私有数据
        if(privateData.containsKey("other")){
            //从接收人解析
            if(receiversMap!=null&&receiversMap.size()>0){
                logger.info("--从接收人更新私有数据---");
                for (String userid:receiversMap.values()){
                    if(!resultData.containsKey(userid)){
                        add2ResultData(resultData,userid,privateData.getJSONObject("other"));
                    }
                }
            }else{
                //根据模块和主文档去匹配卡片模块
                ThirdDingCardMapping thirdDingCardMapping = thirdDingCardMappingService.getMappingByModel(baseModel.getClass().getName(),baseModel.getFdId());
                if(thirdDingCardMapping != null){
                    //历史处理人
                    logger.info("--从历史处理人更新私有数据---");
                    String fdReceiverUsers = thirdDingCardMapping.getFdReceiverUsers();
                    if(StringUtil.isNotNull(fdReceiverUsers)){
                        List<String> hadReceicers = Arrays.asList(fdReceiverUsers.split(";"));
                        hadReceicers.forEach(id->{
                            if(!resultData.containsKey(id)){
                                add2ResultData(resultData,id,privateData.getJSONObject("other"));
                            }
                        });
                    }
                }
            }
        }
        return resultData;
    }

    private static void add2ResultData(JSONObject resultData,String userid, JSONObject privateData) {
        JSONObject cardParamMap = new JSONObject();
        cardParamMap.put("cardParamMap",privateData);
        resultData.put(userid,cardParamMap);
    }

    /*
     设置内置参数:pcUrl(pc端地址),mUrl(移动端地址),agreeText(肯定按钮的文案),refuseText(否定按钮的文案),displayText(状态按钮的文案)
     */
    private static void setInnerParam(JSONObject pulicdatas,BaseModel baseModel) {
        if(!pulicdatas.containsKey("pcUrl")){
            SysDictModel model = SysDataDict.getInstance().getModel(baseModel.getClass().getName());
            String url =DingUtil.getDingDomin()+formatModelUrl(baseModel.getFdId(), model.getUrl());
            String pcUrl =url;
            try {
                pcUrl = DingUtil.getDingDomin() + "/third/ding/pc/web_wnd.jsp?url="+ URLEncoder.encode(url,"UTF-8");
                pcUrl="dingtalk://dingtalkclient/page/link?url="+ URLEncoder.encode(pcUrl, "UTF-8")+"&pc_slide=true";
            } catch (UnsupportedEncodingException e) {
                logger.warn(e.getMessage(),e);
            }

            pulicdatas.put("pcUrl",pcUrl);
            pulicdatas.put("mUrl",url);
        }
        if(!pulicdatas.containsKey("agreeText")){
            pulicdatas.put("agreeText","同意");
        }
        if(!pulicdatas.containsKey("refuseText")){
            pulicdatas.put("refuseText","拒绝");
        }
        if(!pulicdatas.containsKey("displayText")){
            pulicdatas.put("displayText","审批中");
        }
    }

    private static String formatModelUrl(String value, String url) {
        if (StringUtil.isNull(url)) {
            return null;
        }
        Pattern p = Pattern.compile("\\$\\{([^}]+)\\}");
        Matcher m = p.matcher(url);
        while (m.find()) {
            String property = m.group(1);
            try {
                url = StringUtil.replace(url, "${" + property + "}", value);
            } catch (Exception e) {
            }
        }
        return url;
    }

    /*
    * 获取公有数据
    */
    public static Map<String,String> getPublicCardData(BaseModel baseModel, ThirdDingCardConfig cardConfig) {

        JSONArray data = JSONObject.fromObject(cardConfig.getFdConfig()).getJSONArray("data");
        JSONObject item =null;
        List kmReviewDatalist = null;
        Map<String,String> dataMap = new HashMap<String,String>();
        SysDictModel model = SysDataDict.getInstance().getModel(baseModel.getClass().getName());
        Object main = baseModel;
        for (int i=0;i<data.size();i++){
             item = data.getJSONObject(i);
             String fromForm = item.getString("fromForm");
             if("true".equals(fromForm)){
                if(kmReviewDatalist == null){
                    try {
                        IThirdDingTodoTemplateService thirdDingTodoTemplateService = (IThirdDingTodoTemplateService) SpringBeanUtil
                                .getBean("thirdDingTodoTemplateService");
                        kmReviewDatalist = thirdDingTodoTemplateService.getDataList(null,cardConfig.getFdTemplateId(),"com.landray.kmss.km.review.model.KmReviewTemplate");
                    } catch (Exception e) {
                        logger.error(e.getMessage(),e);
                    }
                }
                String value= getFormValue(baseModel,item.getString("key"),kmReviewDatalist);
                dataMap.put(item.getString("param"),value);
             }else{
                 if("$constant$".equals(item.getString("key"))){
                     //常量
                     dataMap.put(item.getString("param"),item.getString("constant"));
                 }else{
                     //模块字段
                     String value= getModelValue(model,main,item.getString("key"));
                     logger.debug("模块字段：{}，值为：{}",item.getString("key"),value);
                     dataMap.put(item.getString("param"),value);
                 }

             }
        }
        return dataMap;
    }

    /*
     获取模块的值
     */
    private static String getModelValue(SysDictModel model, Object main, String key) {
        try {
            if(model==null||main==null){
                return null;
            }
            Map<String, SysDictCommonProperty> map = model.getPropertyMap();
            Object value = "";
            String enumType = null;
            String type = null;
            Class clazz = null;
            if(key.contains(".")){
                String[] keyArray = key.split("\\.");
                Map<String, SysDictCommonProperty> map2 = null;
                SysDictCommonProperty property = null;
                SysDictModel model2 = null;
                SysDictCommonProperty property2=null;
                property2 = map.get(keyArray[0]);
                type = property2.getType();
                clazz = main.getClass();
                value=main;
                for(int j=0;j<keyArray.length;j++){
                    if(value!=null) {
                        if (j != 0) {
                            clazz = value.getClass();
                        }
                        String temp_key = keyArray[j];
                        temp_key = "get" + keyArray[j].substring(0, 1).toUpperCase()
                                + keyArray[j].substring(1);
                        logger.debug("======_key=======:" + temp_key);

                        Method method = clazz.getMethod(temp_key.trim());
                        value = method.invoke(value);
                    }

                    //对象
                    if(j<keyArray.length-1){
                        model2 = SysDataDict.getInstance().getModel(type);
                        map2 = model2.getPropertyMap();
                        property2 = map2.get(keyArray[j+1]);
                        type = property2.getType();
                    }
                }
                enumType = property2.getEnumType();

            }else{
                value = com.opensymphony.util.BeanUtils.getValue(main, key);
                logger.debug("key为简单类型：" +key);
                SysDictCommonProperty property = map.get(key);
                enumType = property.getEnumType();
                type = property.getType();
            }
            logger.debug("是否枚举："+enumType);
            String finalValue = "";

            if(value==null || "".equals(value)){
                return "";
            }

            // 枚举进行转换
            if (StringUtil.isNotNull(enumType)) {
                finalValue = (String) value;
                List enumList = EnumerationTypeUtil.getColumnEnumsByType(enumType);
                for (int k = 0; k < enumList.size(); k++) {
                    ValueLabel valueLabel = (ValueLabel) enumList.get(k);
                    if (finalValue.equals(valueLabel.getValue())) {
                        finalValue = DingUtil.getValueByLang(
                                valueLabel.getLabelKey(),
                                valueLabel.getBundle(),
                                null);
                        logger.debug("枚举转换：" + finalValue + "("
                                + enumType + ")");
                    }
                }
            }else{
                //类型转换
                if("DateTime".equals(type)){
                    finalValue = DateUtil.convertDateToString((Date) value,"yyyy-MM-dd HH:mm");
                }else if("Date".equals(type)){
                    finalValue = DateUtil.convertDateToString((Date) value,"yyyy-MM-dd");
                }else if("Time".equals(type)){
                    finalValue = DateUtil.convertDateToString((Date) value,"HH:mm");
                }else if("Integer".equals(type)){
                    finalValue = String.valueOf(value);
                }else{
                    finalValue = (String) value;
                }
            }
            return finalValue;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    //获取流程文档字段
    public static String getFormValue(Object kmReviewMainObject,String key,List list) {
        String value = null;
        try {
            if (kmReviewMainObject == null || list==null) {
                return null;
            }
            if (key.contains(".")) {
                logger.debug("表单字段为对象类型  " + key);
                String[] keyArray = key.split("\\.");
                if (keyArray != null && keyArray.length > 0) {
                    Object object = new ModelVarProvider()
                            .getValue(kmReviewMainObject, keyArray[0]);
                    Class clazz;
                    String _key;
                    for (int i = 1; i < keyArray.length; i++) {
                        clazz = object.getClass();
                        _key = "get" + keyArray[i].substring(0, 1).toUpperCase()
                                + keyArray[i].substring(1);
                        logger.debug("======_key=======:" + _key);
                        Method method = clazz.getMethod(_key.trim());
                        object = method.invoke(object);
                    }
                    logger.debug("======object=======:" + object);
                    return object == null ? null : object.toString();

                }

            }else if (key.indexOf("$suiteTable$") > -1) {
                // 套件字段
                return ThirdDingXFormTemplateUtil .getSuiteValue((IBaseModel) kmReviewMainObject, key);
            } else {
                logger.debug("表单字段为基本类型  " + key);
                String type;
                String name;
                Boolean isEnum = false;
                String enumString = null;
                for (int i = 0; i < list.size(); i++) {
                    Object[] object = (Object[]) list.get(i);
                    if (object[0].toString().equals(key)) {
                        type = (String) object[2];
                        name = (String) object[1];
                        isEnum = (Boolean) object[3];
                        enumString = (String) object[4];
                        logger.debug("==type:" + type + " " + name
                                + " " + isEnum + " " + enumString);
                    }

                }
                Object object = new ModelVarProvider()
                        .getValue(kmReviewMainObject, key);
                logger.debug("主文档表单object：" + object);
                if (object == null) {
                    logger.warn("object为null,key:" + key);
                    return null;
                }
                // 枚举类型
                if (isEnum) {
                    logger.debug("是枚举类型" + enumString);
                    String[] enumValueKey = enumString.split(";");
                    for (int j = 0; j < enumValueKey.length; j++) {
                        String vk = enumValueKey[j];
                        logger.debug("-----vk:" + vk);
                        if (com.landray.sso.client.oracle.StringUtil.isNotNull(vk) && vk.contains("|")) {
                            String[] singleVk = vk.split("\\|");
                            logger.debug(
                                    "-----singleVk:" + singleVk.toString());
                            if ((object.toString().trim())
                                    .equals(singleVk[1].trim())) {
                                return singleVk[0];
                            }
                        }
                    }

                }
                return object == null ? null : object.toString();
            }
        } catch (Exception e) {
            logger.error("获取表单字段数据发生异常！key:" + key);
        }
        return null;
    }

    /**
     * 发送卡片
     * @param cardTemplateId:卡片模板ID
     * @param privateData 私有数据，格式如下：
     * {
     *     "用户userid" : {
     *       "cardParamMap" : {
     *          "私有key" : "私有值"
     *       }
     *     }
     * }
     * @param cardDataCardParamMap 公有数据，格式如下：
     *  {
     *     "key" : "value"
     *  }
     * @param receiver: 钉钉userid
     * @param outTrackId: 卡片唯一标识
     */
    private static void sendCard(String cardTemplateId,JSONObject privateData,JSONObject cardDataCardParamMap,String receiver,String modelId,String title,String modelName,String from,String outTrackId){

        JSONObject req = new JSONObject();
        req.put("cardTemplateId", cardTemplateId);
        req.put("receiverUserIdList", java.util.Arrays.asList(receiver));
        req.put("outTrackId",outTrackId);
        req.put("conversationType", "0");
        String routeKey = getCallbackRouteKey(false);
        logger.info("routeKey:{}",routeKey);
        req.put("callbackRouteKey",routeKey );
        JSONObject cardData = new JSONObject();
        cardData.put("cardParamMap",cardDataCardParamMap);
        req.put("cardData", cardData);
        if(privateData != null) {
            req.put("privateData",privateData);
        }
        try {
            thirdDingCardConfigService.addInteractivecard(req,title,receiver,modelName,modelId,from,outTrackId);
        } catch (Exception e) {
            logger.warn(e.getMessage(),e);
        }
    }


    /**
     *
     * @param baseModel
     * @param privateData
     * @param cardDataCard
     * @param isFetchPlubicData 是否需要从模块配置里获取公有数据
     */
    private static void updateCard(BaseModel baseModel,String title,JSONObject privateData,JSONObject cardDataCard,boolean isFetchPlubicData,boolean needConfigPrivateData, List<String> users){

        if(baseModel==null){
            return;
        }
        //根据模块和主文档去匹配卡片模块
        ThirdDingCardConfig thirdDingCardConfig = thirdDingCardConfigService.getCardByModel(baseModel.getClass().getName(),baseModel.getFdId());
        if (thirdDingCardConfig==null){
            logger.warn("还没有在钉钉集成中配置模块({},文档ID:{})的卡片模板，不支持推送",baseModel.getClass().getName(),baseModel.getFdId());
            return;
        }
        //根据配置获取公有数据
        Map<String,String>  publicCardData = null;
        JSONObject pulicdatas = null;
        if(isFetchPlubicData){
            publicCardData = getPublicCardData(baseModel,thirdDingCardConfig);
            pulicdatas  = new JSONObject();
            if(cardDataCard!=null&&cardDataCard.size()>0){
                publicCardData.putAll(cardDataCard);
            }
            for(String key:publicCardData.keySet()){
                pulicdatas.put(key,publicCardData.get(key));
            }
        }else {
            pulicdatas = cardDataCard;
        }
        logger.debug("公有数据：{}",pulicdatas.toString());
        JSONObject cardData = new JSONObject();
        cardData.put("cardParamMap",pulicdatas);

        JSONObject req = new JSONObject();
        String outTrackId = MD5Util.getMD5String(baseModel.getClass().getName()+baseModel.getFdId());
        req.put("outTrackId",outTrackId);
        req.put("cardData",cardData);
        if(!needConfigPrivateData){
            req.put("privateData",privateData);
        }else {
            //重新构建私有数据
            if (users != null && users.size()>0){
                //根据接收人局部更新
                Map<String, String> userMap = getUserMap(users);
                //映射表有才更新
                if(userMap!=null&&userMap.size()>0){
                    req.put("privateData",updatePrivateDateByModel(privateData,userMap,baseModel));
                }
            }else{
                //全部接收人更新
                req.put("privateData",updatePrivateDateByModel(privateData,null,baseModel));
            }

        }

        JSONObject cardOptions = new JSONObject();
        cardOptions.put("updateCardDataByKey",true);
        cardOptions.put("updatePrivateDataByKey",false);
        req.put("cardOptions", cardOptions);
        try {
            thirdDingCardConfigService.updateInteractivecard(req,title,baseModel.getClass().getName(),baseModel.getFdId());
        } catch (Exception e) {
            logger.error("更新卡片失败："+e.getMessage(),e);
        }
    }



    /**
     * 注册/更新卡片回调
     * @return
     */
    public static String getCallbackRouteKey(boolean forceUpdate) {
        String callbackRouteKey=null;
        try {
            DingOmsConfig config = new DingOmsConfig();
            callbackRouteKey=config.getCallbackRouteKey();
        } catch (Exception e) {
           logger.error(e.getMessage(),e);
        }
        String callbackUrl=DingConfig.newInstance().getDingCallbackurl();
        if(StringUtil.isNotNull(callbackRouteKey)){
            if(forceUpdate){
                return updateCardCallback(callbackUrl,callbackRouteKey);
            }
            return callbackRouteKey;
        }else {
            //注册回调
            callbackRouteKey = IDGenerator.generateID();
            return updateCardCallback(callbackUrl,callbackRouteKey);
        }
    }

    private static String updateCardCallback(String callbackUrl, String callbackRouteKey) {
        try {
            DingOmsConfig config = new DingOmsConfig();
            String callbackApiSecrect = config.getCallbackApiSecrect();
            if(StringUtil.isNull(callbackApiSecrect)){
                callbackApiSecrect = IDGenerator.generateID();
            }
            JSONObject result = DingUtils.getDingApiService().registerCardCallback(callbackUrl, callbackRouteKey,callbackApiSecrect);
            if(result!=null && result.containsKey("errcode") && result.getLong("errcode")==0){
                DingOmsConfig dingOmsConfig = new DingOmsConfig();
                dingOmsConfig.setCallbackRouteKey(callbackRouteKey);
                dingOmsConfig.setCallbackApiSecrect(callbackApiSecrect);
                dingOmsConfig.save();
                return callbackRouteKey;
            }
        } catch (Exception e) {
            logger.error("注册/更新卡片回调失败："+e.getMessage(),e);
        }
        return null;
    }

    public static String getKmReviewTemplateId(String reviewMainId){
        try {
            IBaseService obj = (IBaseService) SpringBeanUtil
                    .getBean("kmReviewMainService");
            Object kmReviewMainObject = obj
                    .findByPrimaryKey(reviewMainId);
            Class clazz = kmReviewMainObject.getClass();
            Method method = clazz.getMethod("getFdTemplate");
            Object templateObj = method.invoke(kmReviewMainObject);
            clazz = templateObj.getClass();
            Object templateId = clazz.getMethod("getFdId")
                    .invoke(templateObj);
            return templateId.toString();  //ekp表单模板ID
        } catch (Exception e) {
            logger.error("获取流程模板Id失败"+e.getMessage(),e);
        }
        return null;
    }
}
