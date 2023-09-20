package com.landray.kmss.third.ding.service.spring;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.calendar.model.KmCalendarMain;
import com.landray.kmss.km.calendar.model.KmCalendarSyncMapping;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.km.calendar.service.IKmCalendarSyncMappingService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.ding.model.ThirdDingCalendarLog;
import com.landray.kmss.third.ding.service.IThirdDingCalendarLogService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.*;

public class ThirdDingCalendarLogServiceImp extends ExtendDataServiceImp implements IThirdDingCalendarLogService, ApplicationContextAware {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingCalendarLogServiceImp.class);

    private final String SYN_TO_DING= "1";
    private final String SYN_FROM_DING= "2";
    private final String OPT_ADD= "add";
    private final String OPT_UPDATE= "update";
    private final String OPT_DELETE= "delete";
    private final String PREFIX_V3_API= "$V3API$";

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingCalendarLog) {
            ThirdDingCalendarLog thirdDingCalendarLog = (ThirdDingCalendarLog) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingCalendarLog thirdDingCalendarLog = new ThirdDingCalendarLog();
        thirdDingCalendarLog.setFdStatus(Boolean.valueOf("true"));
        thirdDingCalendarLog.setFdReqStartTime(new Date());
        ThirdDingUtil.initModelFromRequest(thirdDingCalendarLog, requestContext);
        return thirdDingCalendarLog;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingCalendarLog thirdDingCalendarLog = (ThirdDingCalendarLog) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    private IThirdDingCalendarLogService thirdDingCalendarLogService = null;

    public IThirdDingCalendarLogService getThirdDingCalendarLogService() {
        if (thirdDingCalendarLogService == null) {
            thirdDingCalendarLogService = (IThirdDingCalendarLogService) SpringBeanUtil
                    .getBean("thirdDingCalendarLogService");
        }
        return thirdDingCalendarLogService;
    }

    private IKmCalendarMainService kmCalendarMainService;

    public IKmCalendarMainService getKmCalendarMainService() {
        if (kmCalendarMainService == null) {
            kmCalendarMainService = (IKmCalendarMainService) SpringBeanUtil
                    .getBean("kmCalendarMainService");
        }
        return kmCalendarMainService;
    }


    private IKmCalendarSyncMappingService kmCalendarSyncMappingService;

    public IKmCalendarSyncMappingService getKmCalendarSyncMappingService() {
        if (kmCalendarSyncMappingService == null) {
            kmCalendarSyncMappingService = (IKmCalendarSyncMappingService) SpringBeanUtil
                    .getBean("kmCalendarSyncMappingService");
        }
        return kmCalendarSyncMappingService;
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void updateCallbackCalendar(JSONObject content) {
        logger.warn("日程回调内容："+content);
        boolean synEnable = true;
        String changeType = content.getString("ChangeType");
        ThirdDingCalendarLog calendarLog = new ThirdDingCalendarLog();
        calendarLog.setFdSynWay(SYN_FROM_DING);
        calendarLog.setFdReqStartTime(new Date());
        if("created".equals(changeType)){
            calendarLog.setFdOptType(OPT_ADD);
        }else if("updated".equals(changeType)){
            calendarLog.setFdOptType(OPT_UPDATE);
        }else if("cancelled".equals(changeType)){
            calendarLog.setFdOptType(OPT_DELETE);
        }

        // 发送事件通知
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appKey","dingCalendar");

        String uninonId = (String) content.getJSONArray("UnionIdList").get(0);
        logger.debug("uninonId:"+uninonId);
        String calendarId = content.getString("CalendarEventId");
        logger.debug("CalendarEventId:"+calendarId);
        try {
            JSONObject calenderInfo =  DingUtils.getDingApiService().getDingCalendars(uninonId,calendarId);
            logger.debug("日常详情："+calenderInfo);
            if(calenderInfo!=null&&!calenderInfo.isEmpty()&&calenderInfo.containsKey("id")){
                //过滤循环日程
                if(calenderInfo.containsKey("recurrence") || (calenderInfo.containsKey("seriesMasterId") && StringUtil.isNotNull(calenderInfo.getString("seriesMasterId")))){
                    logger.debug("过滤重复日程：{} ，id:{}",calenderInfo.getString("summary"),calenderInfo.getString("id"));
                    return;
                }
                calendarLog.setFdStatus(true);
                calendarLog.setFdName(calenderInfo.getString("summary"));
                params.put("subject",calenderInfo.getString("summary"));
                params.put("content",calenderInfo.getString("summary"));
                params.put("isAllDayEvent",calenderInfo.getBoolean("isAllDay"));

                if(calenderInfo.containsKey("description")){
                    params.put("fdDecs",calenderInfo.getString("description"));
                }else{
                    params.put("fdDecs","");
                }
                Date startDate = null;
                Date endDate = null;
                if(calenderInfo.getBoolean("isAllDay")){
                    //全天
                    String _startDate = calenderInfo.getJSONObject("start").getString("date");
                    String _endDate = calenderInfo.getJSONObject("end").getString("date");
                    startDate = DateUtil.convertStringToDate(_startDate,"yyyy-MM-dd");
                    endDate = DateUtil.getNextDay(DateUtil.convertStringToDate(_endDate,"yyyy-MM-dd"),-1);  //要减少一天，机制不一样
                }else{
                    //非全天
                    String _startDate = calenderInfo.getJSONObject("start").getString("dateTime");
                    String _endDate = calenderInfo.getJSONObject("end").getString("dateTime");
                    logger.debug("开始时间："+_startDate+"    结束时间："+_endDate);
                    startDate = DateUtil.convertStringToDate(_startDate,"yyyy-MM-dd'T'HH:mm:ssXXX");
                    endDate = DateUtil.convertStringToDate(_endDate,"yyyy-MM-dd'T'HH:mm:ssXXX");
                }
                params.put("eventStartTime",startDate);
                params.put("eventFinishTime",endDate);
                // 保留外部人员
                List<String> outPersonIds = new ArrayList<String>();

                //uuid
                String dingCalendarId = calenderInfo.getString("id");
                if("created".equals(changeType)){
                    dingCalendarId=PREFIX_V3_API+dingCalendarId;
                }else{
                    String updateCalendarId = null;
                    boolean recordFlag = false;
                    //非新增
                    HQLInfo hqlInfo = new HQLInfo();
                    hqlInfo.setWhereBlock("(fdEkpCalendarId !='' or fdEkpCalendarId is not null)  and fdDingCalendarId like :calId");
                    hqlInfo.setParameter("calId","%"+dingCalendarId);
                    ThirdDingCalendarLog log = (ThirdDingCalendarLog) getThirdDingCalendarLogService().findFirstOne(hqlInfo);
                    if(log != null){
                        dingCalendarId =  log.getFdDingCalendarId();
                        calendarLog.setFdEkpCalendarId(log.getFdEkpCalendarId());
                        params.put("calendarId",log.getFdEkpCalendarId());
                        updateCalendarId = log.getFdEkpCalendarId();
                        recordFlag = true;
                    }else{
                        //兼容旧数据
                        //钉钉日志找不到，尝试根据LegacyCalendarEventId去查日志，如果也没有，再查一下日程的表，都没有则当作是新增处理
                        if(content.containsKey("LegacyCalendarEventId")){
                            hqlInfo = new HQLInfo();
                            hqlInfo.setWhereBlock("(fdEkpCalendarId !='' or fdEkpCalendarId is not null)  and fdDingCalendarId like :calId");
                            hqlInfo.setParameter("calId","%"+content.getString("LegacyCalendarEventId"));
                            log = (ThirdDingCalendarLog) getThirdDingCalendarLogService().findFirstOne(hqlInfo);
                            if(log != null){
                                recordFlag = true;
                                dingCalendarId =  log.getFdDingCalendarId();
                                calendarLog.setFdEkpCalendarId(log.getFdEkpCalendarId());
                                params.put("calendarId", log.getFdEkpCalendarId());
                                updateCalendarId = log.getFdEkpCalendarId();
                            }else{
                                hqlInfo = new HQLInfo();
                                hqlInfo.setWhereBlock("fdAppKey=:fdAppKey and (fdAppUuid like :appUuid or fdAppUuid like :legId)");
                                hqlInfo.setParameter("fdAppKey","dingCalendar");
                                hqlInfo.setParameter("appUuid","%"+dingCalendarId);
                                hqlInfo.setParameter("legId","%"+content.getString("LegacyCalendarEventId"));
                                KmCalendarSyncMapping synMap = (KmCalendarSyncMapping) getKmCalendarSyncMappingService().findFirstOne(hqlInfo);
                                if(synMap != null){
                                    //映射表查到了相关的记录
                                    recordFlag = true;
                                    params.put("calendarId",synMap.getFdCalendarId());
                                    dingCalendarId = synMap.getFdAppUuid();
                                    updateCalendarId = synMap.getFdCalendarId();
                                }
                            }
                        }
                        if(!recordFlag){
                            //查不到相关的记录，更新可作为新增处理，删除操作则忽略
                            logger.warn("-------无法适配旧数据，更新可作为新增处理，删除操作则忽略--------");
                            if("updated".equals(changeType)){
                                logger.warn("------------ 更新-》新增 -------------");
                                calendarLog.setFdErrorMsg("-------无法适配旧数据，更新-》新增--------");
                                changeType = "created";
                                dingCalendarId=PREFIX_V3_API+dingCalendarId;
                            }else if("cancelled".equals(changeType)){
                                logger.warn("------------ 删除日程忽略 -------------");
                                calendarLog.setFdStatus(false);
                                calendarLog.setFdErrorMsg("-------无法适配旧数据，删除操作则忽略--------");
                                synEnable = false;
                            }
                        }
                    }
                    if ("updated".equals(changeType) && recordFlag
                            && StringUtil.isNotNull(updateCalendarId)) {
                        KmCalendarMain kmCalendarMain = (KmCalendarMain) getKmCalendarMainService()
                                .findByPrimaryKey(updateCalendarId, null, true);
                        if (kmCalendarMain != null) {
                            List<SysOrgPerson> reList = kmCalendarMain
                                    .getFdRelatedPersons();
                            if (reList != null && reList.size() > 0) {
                                List<String> relationList = DingUtil.getAllReltion();
                                boolean flag = false;
                                if (relationList != null && relationList.size() > 0) {
                                    flag = true;
                                }
                                for (SysOrgPerson per : reList) {
                                    logger.debug("更新相关人员：" + per.getFdId() + "  "+ per.getFdName());
                                    if (!flag || !relationList.contains(per.getFdId())) {
                                        logger.info("映射表没有对应映射关系："+ per.getFdName());
                                        outPersonIds.add(per.getFdId());
                                    }
                                }


                            }

                        }
                    }
                }

                params.put("uuid",dingCalendarId);

                //地点
                if(calenderInfo.containsKey("location")&&calenderInfo.getJSONObject("location").containsKey("displayName")){
                    params.put("eventLocation",calenderInfo.getJSONObject("location").getString("displayName"));
                }

                //创建人
                String creatorUnionId = calenderInfo.getJSONObject("organizer").getString("id");
                String creatorId =  DingUtil.getEkpIdByUnionid(creatorUnionId);
                if(StringUtil.isNull(calendarId)){
                    synEnable = false;
                    calendarLog.setFdStatus(false);
                    calendarLog.setFdErrorMsg("-------创建人不在映射表中，回调忽略--------");
                }
                params.put("personId",creatorId);
                params.put("creatorId",creatorId);

                //参与者
                List<String> relatedPersonIds = new ArrayList<String> ();
                JSONArray attendees = calenderInfo.getJSONArray("attendees");
                for(int i=0;i<attendees.size();i++){
                    String attenderUnionid =  attendees.getJSONObject(i).getString("id");
                    if(attenderUnionid.equals(creatorUnionId)) {
                        continue;
                    }
                    String attenderId =  DingUtil.getEkpIdByUnionid(attenderUnionid);
                    if(StringUtil.isNotNull(attenderId)){
                        relatedPersonIds.add(attenderId);
                    }
                }
                // 相关人保留外部人员
                logger.debug("outPersonIds:" + outPersonIds);
                if (outPersonIds.size() > 0) {
                    relatedPersonIds.addAll(outPersonIds);
                }
                params.put("relatedPersonIds",relatedPersonIds);

                calendarLog.setFdDingCalendarId(dingCalendarId);
                calendarLog.setFdResult(calenderInfo.toString());
                params.put("changeType",changeType);
                //发布事件
                if(synEnable){
                    try {
                        calendarLog.setFdReqParam(JSON.toJSON(params).toString());
                        applicationContext.publishEvent(new Event_Common(
                                "dingCalendarChange", params));
                    } catch (Exception e) {
                        //事件异常
                        calendarLog.setFdStatus(false);
                        calendarLog.setFdErrorMsg(e.toString());
                        logger.error("****日程处理异常****"+e.getMessage(),e);
                    }
                }

            }else {
                logger.error("获取日程详情失败："+calenderInfo);
                calendarLog.setFdStatus(false);
                calendarLog.setFdResult(calenderInfo==null?null:calenderInfo.toString());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        try {
            calendarLog.setFdResponseStartTime(new Date());
            getThirdDingCalendarLogService().add(calendarLog);
        } catch (Exception e) {
            logger.error("添加日程回调日志失败！"+e.getMessage(),e);
        }
    }
}
