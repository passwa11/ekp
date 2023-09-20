package com.landray.kmss.third.weixin.work.util;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.work.action.ThirdWeixinWorkLivingAction;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinWorkLiving;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.model.api.WxLiving;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinWorkLivingService;
import com.landray.kmss.third.weixin.work.spi.model.WxworkOmsRelationModel;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;

/**
 * 企业微信直播工具类
 */
public class WxLivingUtils {

    private static final Logger logger = org.slf4j.LoggerFactory
            .getLogger(ThirdWeixinWorkLivingAction.class);

    private static IThirdWeixinWorkLivingService thirdWeixinWorkLivingService;

    private static IWxworkOmsRelationService wxworkOmsRelationService;

    private static IWxworkOmsRelationService getWxworkOmsRelationService(){
        if (wxworkOmsRelationService == null) {
            wxworkOmsRelationService = (IWxworkOmsRelationService) SpringBeanUtil.getBean("wxworkOmsRelationService");
        }
        return wxworkOmsRelationService;
    }

    private static IThirdWeixinWorkLivingService getThirdWeixinWorkLivingService() {
        if (thirdWeixinWorkLivingService == null) {
            thirdWeixinWorkLivingService = (IThirdWeixinWorkLivingService) SpringBeanUtil.getBean("thirdWeixinWorkLivingService");
        }
        return thirdWeixinWorkLivingService;
    }

    /*
     *创建预约直播
     */
    public static JSONObject createLiving(JSONObject living) throws Exception {
        logger.debug("创建直播预约原始入参："+living);
        //校验
        JSONObject error = new JSONObject();
        error.put("errcode", -1);
        boolean isFlag = checkField(living, error);
        if (!isFlag) {
            return error;
        }
        WxLiving wxLiving = new WxLiving();
        WxworkOmsRelationModel relationModel = getWxworkOmsRelationService().findByEkpId(living.getString("anchor_userid"));
        if (relationModel != null) {
            //发起人
            wxLiving.setAnchorUserid(relationModel.getFdAppPkId());
            //描述
            if (living.containsKey("description")) {
                String description = living.getString("description");
                if (description.length() > 300) {
                    description = description.substring(0, 280) + "...";
                }
                wxLiving.setDescription(description);
            }
            //类型：0：通用直播，1：小班课，2：大班课，3：企业培训，4：活动直播，默认 0
            if (living.containsKey("type")) {
                String type = living.getString("type");
                wxLiving.setType(Integer.valueOf(type));
            }

            //标题
            String theme = living.getString("theme");
            if (theme.length() > 60) {
                theme = theme.substring(0, 55) + "...";
            }
            wxLiving.setTheme(theme);
            //直播开始时间
            Long startTime = DateUtil.convertStringToDate(living.getString("living_start"), "yyyy-MM-dd HH:mm:ss").getTime();
            wxLiving.setLivingStart(startTime / 1000);
            //直播持续时间
            wxLiving.setLivingDuration(Integer.valueOf(living.getString("living_duration")));
            //提醒时间
            if (living.containsKey("remind_time")&&StringUtil.isNotNull(living.getString("remind_time"))) {
                wxLiving.setRemind_time(Integer.valueOf(living.getString("remind_time")));
            }
            JSONObject result = WxworkUtils.getWxworkApiService().createLiving(wxLiving);
            logger.warn("直播创建结果：" + result);
            //建立映射关系
            if (result != null && result.getInteger("errcode") == 0) {
                ThirdWeixinWorkLiving thirdWeixinWorkLiving = new ThirdWeixinWorkLiving(theme, living.getString("modelName"),living.getString("modelId") , result.getString("livingid"));
                getThirdWeixinWorkLivingService().add(thirdWeixinWorkLiving);
            }
            return result;
        } else {
            error.put("result", "fail:直播发起人(id:" + living.getString("anchor_userid") + ")的映射关系异常");
            return error;
        }
    }

    private static boolean checkField(JSONObject living, JSONObject error) {
        if (!canLiving()){
            error.put("result", "fail:直播功能已关闭");
            return false;
        }
        StringBuffer str = new StringBuffer();
        boolean accessFlag=true;
        if( !living.containsKey("modelName") || StringUtil.isNull(living.getString("modelName"))){
            str.append("模块modelName为空;");
            accessFlag=false;
        }
        if( !living.containsKey("modelId") || StringUtil.isNull(living.getString("modelId"))){
            str.append("模块modelId为空;");
            accessFlag=false;
        }
        if( !living.containsKey("anchor_userid") || StringUtil.isNull(living.getString("anchor_userid"))){
            str.append("发起人anchor_userid为空;");
            accessFlag=false;
        }
        if( !living.containsKey("theme") || StringUtil.isNull(living.getString("theme"))){
            str.append("标题theme为空;");
            accessFlag=false;
        }
        if( !living.containsKey("living_start") || StringUtil.isNull(living.getString("living_start"))){
            str.append("开始时间living_start为空;");
            accessFlag=false;
        }
        if( !living.containsKey("living_duration") || StringUtil.isNull(living.getString("living_duration"))){
            str.append("直播持续时长living_duration为空;");
            accessFlag=false;
        }
        if(!accessFlag) {
            error.put("result", "fail:" + str.toString());
        }
        return accessFlag;
    }
    /*
     *修改预约直播
     */
    public static JSONObject modifyLiving(JSONObject living) throws Exception{
        JSONObject error = new JSONObject();
        error.put("errcode", -1);
        if(!living.containsKey("livingId")){
            error.put("result", "fail:直播参数livingId为空");
            return error;
        }
        WxLiving wxLiving = new WxLiving();
        wxLiving.setLivingid(living.getString("livingId"));
        if(living.containsKey("theme")){
            //标题
            String theme = living.getString("theme");
            if (theme.length() > 60) {
                theme = theme.substring(0, 55) + "...";
            }
            wxLiving.setTheme(theme);
        }
        //描述
        if (living.containsKey("description")) {
            String description = living.getString("description");
            if (description.length() > 300) {
                description = description.substring(0, 280) + "...";
            }
            wxLiving.setDescription(description);
        }

        if(living.containsKey("living_start")){
            //直播开始时间
            Long startTime = DateUtil.convertStringToDate(living.getString("living_start"), "yyyy-MM-dd HH:mm:ss").getTime();
            wxLiving.setLivingStart(startTime / 1000);
        }

        if(living.containsKey("living_duration")){
            //直播持续时间
            wxLiving.setLivingDuration(Integer.valueOf(living.getString("living_duration")));
        }

        //提醒时间
        if (living.containsKey("remind_time")) {
            if(StringUtil.isNotNull(living.getString("remind_time"))){
                wxLiving.setRemind_time(Integer.valueOf(living.getString("remind_time")));
            }else {
                wxLiving.setRemind_time(0);
            }
        }
        JSONObject result = WxworkUtils.getWxworkApiService().modifyLiving(wxLiving);
        return result;
    }

    /*
     *取消预约直播
     */
    public static JSONObject cancelLiving(String livingId) throws Exception{
        JSONObject error = new JSONObject();
        error.put("errcode", -1);
        if(StringUtil.isNull(livingId)){
            error.put("result", "fail:直播参数livingId为空");
            return error;
        }
        JSONObject result = WxworkUtils.getWxworkApiService().cancelLiving(livingId);
        if(result!=null && result.getInteger("errcode")==0){
            try {
                getThirdWeixinWorkLivingService().deleteByLivingId(livingId);
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
        }
        return result;
    }

    /*
     *删除直播回放
     */
    public static JSONObject deleteReplayDataLiving(String livingId) throws Exception{
        JSONObject error = new JSONObject();
        error.put("errcode", -1);
        if(StringUtil.isNull(livingId)){
            error.put("result", "fail:直播参数livingId为空");
            return error;
        }
        return WxworkUtils.getWxworkApiService().deleteReplayDataLiving(livingId);
    }

    /*
     *判断能否直播
     */
    public static boolean canLiving() {
        String wxEnable= WeixinWorkConfig.newInstance().getWxEnabled();
        String wxLivingEnabled=WeixinWorkConfig.newInstance().getWxLivingEnabled();
        if(StringUtil.isNotNull(wxEnable)&&"true".equalsIgnoreCase(wxEnable)&&
                StringUtil.isNotNull(wxLivingEnabled)&&"true".equalsIgnoreCase(wxLivingEnabled)){
            return true;
        }else{
            return false;
        }
    }

    /*
     *获取直播livingId
     */
    public static String getLiving(String fdModelId) throws Exception{
        try {
            if(StringUtil.isNull(fdModelId)) {
                return null;
            }
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setWhereBlock("fdModelId=:fdModelId");
            hqlInfo.setParameter("fdModelId",fdModelId);
            hqlInfo.setOrderBy("docCreateTime desc");
            ThirdWeixinWorkLiving model = (ThirdWeixinWorkLiving) getThirdWeixinWorkLivingService().findFirstOne(hqlInfo);
            if(model != null){
                //判断最新直播记录的状态
               Integer status = getLivingStatus(model.getFdLivingId());
               if(status!=null && status==1){
                   return model.getFdLivingId();
               }
            }else{
                logger.debug("---不存在对应的直播----");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    private static Integer getLivingStatus(String fdLivingId) {
        if(StringUtil.isNull(fdLivingId)){
            return null;
        }
        try {
            JSONObject livingInfo = WxworkUtils.getWxworkApiService().getLivingInfo(fdLivingId);
            if(livingInfo!=null && livingInfo.getInteger("errcode")==0){
                return livingInfo.getJSONObject("living_info").getInteger("status");
            }else{
                logger.warn("获取直播异常(livingId:{})：{}",fdLivingId,livingInfo);
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(),e);
        }
        return null;
    }
}


