package com.landray.kmss.km.calendar.cms;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.km.calendar.cms.interfaces.IDing2CalendarProvider;
import com.landray.kmss.km.calendar.cms.interfaces.SyncroCommonCal;
import com.landray.kmss.km.calendar.constant.KmCalendarConstant;
import com.landray.kmss.km.calendar.model.KmCalendarDetails;
import com.landray.kmss.km.calendar.model.KmCalendarMain;
import com.landray.kmss.km.calendar.model.KmCalendarSyncMapping;
import com.landray.kmss.km.calendar.service.IKmCalendarDetailsService;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.km.calendar.service.IKmCalendarSyncMappingService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;

import java.util.*;

public class Ding2CalendarListener implements IDing2CalendarProvider,ApplicationListener<Event_Common>{

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Ding2CalendarListener.class);
    private ISysOrgPersonService sysOrgPersonService;

    public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
        this.sysOrgPersonService = sysOrgPersonService;
    }

    private IKmCalendarMainService kmCalendarMainService;
    public void setKmCalendarMainService(IKmCalendarMainService kmCalendarMainService) {
        this.kmCalendarMainService = kmCalendarMainService;
    }

    private IKmCalendarDetailsService kmCalendarDetailsService;

    public void setKmCalendarDetailsService(IKmCalendarDetailsService kmCalendarDetailsService) {
        this.kmCalendarDetailsService = kmCalendarDetailsService;
    }

    private IKmCalendarSyncMappingService kmCalendarSyncMappingService;

    public void setKmCalendarSyncMappingService(IKmCalendarSyncMappingService kmCalendarSyncMappingService) {
        this.kmCalendarSyncMappingService = kmCalendarSyncMappingService;
    }


    /**
     * 钉钉同步日程到ekp
     * @param personId 创建人ID
     * @param cal 钉钉日程
     * @throws Exception
     */
    @Override
    public void addDingToCalendar(String personId, SyncroCommonCal cal) throws Exception {
        //查询同步映射表是否存在这条数据
        String appKey = cal.getAppKey(); //appkey
        String uuid = cal.getUuid(); //uuid
        String calendarId = findCalendarId(appKey,uuid);
        KmCalendarMain kmCalendarMain = new KmCalendarMain();
        //把SyncroCommonCal赋kmCalendarMain对象
        updateKmCalendarMain(cal, kmCalendarMain);
        //同步映射表
        KmCalendarSyncMapping kmCalendarSyncMapping =new KmCalendarSyncMapping();
        kmCalendarSyncMapping.setFdAppUuid(uuid);
        kmCalendarSyncMapping.setFdAppKey(appKey);
        kmCalendarSyncMapping.setFdCalendarId(kmCalendarMain.getFdId());
        if(StringUtil.isNull(calendarId)){ //新增
            logger.info("新增日程start...");
            //kmCalendarMainService.add(kmCalendarMain); //新增人员
            //新增同步映射关联
            kmCalendarSyncMappingService.add(kmCalendarSyncMapping);
            kmCalendarMainService.addByAppKey(kmCalendarMain,null,null,false); //新增人员

            logger.info("新增日程end...");
        }else{ //更新
            kmCalendarMain.setFdId(calendarId);
            logger.info("更新日程start...");
            kmCalendarMainService.update(kmCalendarMain);
            kmCalendarSyncMappingService.update(kmCalendarSyncMapping);
            //更新日程相关人详情表
            updateKmCalendarDetail(kmCalendarMain);
            logger.info("更新日程end...");
        }
    }

    private void updateKmCalendarDetail(KmCalendarMain kmCalendarMain) throws Exception {
        String calendarId = kmCalendarMain.getFdId(); //日程id
        HQLInfo hqlInfo =new HQLInfo();
        hqlInfo.setWhereBlock("kmCalendarDetails.fdCalendar.fdId=:calendarId");
        hqlInfo.setParameter("calendarId",calendarId);
        List list = kmCalendarDetailsService.findList(hqlInfo);
        //1、判断人员详情表是否有相关人
        if (ArrayUtil.isEmpty(list)){ //新增
            kmCalendarMainService.addCalendarDetails(kmCalendarMain);
        }else{//更新
            kmCalendarMainService.deleteCalendarDetails(kmCalendarMain); //删除日程相关人详情表
            kmCalendarMainService.addCalendarDetails(kmCalendarMain); //新增
        }
    }

    private String findCalendarId(String appKey, String uuid) throws Exception {
        HQLInfo hqlInfo =new HQLInfo();
        hqlInfo.setSelectBlock("kmCalendarSyncMapping.fdCalendarId");
        hqlInfo.setWhereBlock("kmCalendarSyncMapping.fdAppKey =:appKey and kmCalendarSyncMapping.fdAppUuid =:uuid");
        hqlInfo.setParameter("appKey",appKey);
        hqlInfo.setParameter("uuid",uuid);
        List list = kmCalendarSyncMappingService.findValue(hqlInfo);
        if(!ArrayUtil.isEmpty(list)){
            return (String) list.get(0);
        }
        return "";
    }

    /**
     * 钉钉更新日程同步ekp
     * @param personId 更新人id
     * @param cal 更新日程
     * @throws Exception
     */
    @Override
    public void updateDingToCalendar(String personId, SyncroCommonCal cal) throws Exception {
        if(StringUtil.isNotNull(personId)){ //只有日程拥有者可以修改
            KmCalendarMain kmCalendarMain = (KmCalendarMain) kmCalendarMainService.findByPrimaryKey(cal.getCalendarId());
            if (kmCalendarMain != null) { //更新
                //同步映射表
                KmCalendarSyncMapping kmCalendarSyncMapping =new KmCalendarSyncMapping();
                kmCalendarSyncMapping.setFdAppUuid(cal.getUuid());
                kmCalendarSyncMapping.setFdAppKey(cal.getAppKey());
                kmCalendarSyncMapping.setFdCalendarId(kmCalendarMain.getFdId());
                //将cal对象赋给kmCalendarMain
                updateKmCalendarMain(cal, kmCalendarMain);
                //把SyncroCommonCal的数据同步到kmCalendarMain对象
                kmCalendarMainService.update(kmCalendarMain);
                //更新同步映射表
                kmCalendarSyncMappingService.update(kmCalendarSyncMapping);
                //更新日程相关人详情表
                updateKmCalendarDetail(kmCalendarMain);
            }else{//新增
                kmCalendarMainService.add(kmCalendarMain);
            }
        }
    }
    /**
     * 钉钉取消日程同步ekp
     * @param personId
     * @param cal
     * @throws Exception
     */
    @Override
    public void deleteDingToCalendar(String personId, SyncroCommonCal cal) throws Exception {
        KmCalendarMain kmCalendarMain= (KmCalendarMain) kmCalendarMainService.findByPrimaryKey(cal.getCalendarId());
        String uuid = cal.getUuid();
        String appKey = cal.getAppKey();
        //1、判断取消日程人员是否是拥有者（只有拥有者可以取消日程）
        logger.info("删除日程start...");
        if(cal.getCreatorId().equals(personId)){
            //删除日程
            kmCalendarMainService.deleteCalendar(kmCalendarMain);
        }else{
            //取消人不是创建人，那么只设置日程详情表该用户的日程显示删除
            updateClaendarDetails(kmCalendarMain);
        }
        logger.info("删除日程end...");
    }

    /**
     * 新增日程相关人到详情表
     * @param kmCalendarMain
     * @throws Exception
     */
    public void addCalendarDetails(KmCalendarMain kmCalendarMain) throws Exception {
        List<SysOrgPerson> fdRelatedPersons= kmCalendarMain.getFdRelatedPersons(); //获取日程相关人
        for(SysOrgPerson person:fdRelatedPersons){
            KmCalendarDetails detail = new KmCalendarDetails();
            detail.setFdCalendar(kmCalendarMain);
            detail.setFdPerson(person);
            detail.setFdIsDelete(Boolean.FALSE);
            //添加
            kmCalendarDetailsService.add(detail);
        }
    }

    /**
     * 更新日程相关人详情表数据
     * @param kmCalendarMain
     */
    private void updateClaendarDetails(KmCalendarMain kmCalendarMain) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("kmCalendarDetails.fdCalendar.fdId =:calendarId and kmCalendarDetails.fdPerson.fdId =:personId");
        hqlInfo.setParameter("calendarId", kmCalendarMain.getFdId());
        hqlInfo.setParameter("personId", UserUtil.getUser().getFdId());
        List<KmCalendarDetails> details = kmCalendarDetailsService.findList(hqlInfo);
        for (KmCalendarDetails detail : details) {
            detail.setFdIsDelete(Boolean.TRUE);
            kmCalendarDetailsService.update(detail);
        }
    }

    public void updateKmCalendarMain(SyncroCommonCal cal,
                                     KmCalendarMain kmCalendarMain) throws Exception {
        SysOrgPerson person = (SysOrgPerson) (sysOrgPersonService
                .findByPrimaryKey(cal.getPersonId()));
        kmCalendarMain.setDocCreator(person); //创建者
        kmCalendarMain.setDocOwner(person);  //拥有者
        List<String> relatedPersonIds =cal.getRelatedPersonIds();
        ArrayList<SysOrgPerson> relatedPersonList = new ArrayList();
        for(String relatedPersonId:relatedPersonIds){
            SysOrgPerson relatedPerson = (SysOrgPerson)sysOrgPersonService.findByPrimaryKey(relatedPersonId);
            relatedPersonList.add(relatedPerson);
        }
        kmCalendarMain.setFdRelatedPersons(relatedPersonList); //日程相关人
        Date start = cal.getEventStartTime();
        Date end = cal.getEventFinishTime();
        kmCalendarMain.setDocSubject(cal.getSubject()); //日程名
        boolean isAllDay = false;
        Calendar c1 = Calendar.getInstance();
        c1.setTime(start);
        if (c1.get(Calendar.HOUR_OF_DAY) == 0 && c1.get(Calendar.MINUTE) == 0
                && c1.get(Calendar.SECOND) == 0) {
            if (end != null) {
                Calendar c2 = Calendar.getInstance();
                c2.setTime(end);
                if (c2.get(Calendar.HOUR_OF_DAY) == 0
                        && c2.get(Calendar.MINUTE) == 0
                        && c2.get(Calendar.SECOND) == 0) {
                    isAllDay = true;
                }
            } else {
                isAllDay = true;
                c1.set(Calendar.HOUR_OF_DAY, 23);
                c1.set(Calendar.MINUTE, 59);
                c1.set(Calendar.SECOND, 59);
                end = c1.getTime();
            }
        }
        if (end == null) {
            end = start;
        }
        kmCalendarMain.setDocStartTime(start); //开始时间
        kmCalendarMain.setDocFinishTime(end);  //结束时间
        kmCalendarMain.setFdIsAlldayevent(isAllDay); //是否全天
        kmCalendarMain.setFdIsLunar(Boolean.FALSE);  //是否农历（钉钉没有这个字段,所以是否）
        kmCalendarMain.setFdLastModifiedTime(new Date());  //最后更新时间
        kmCalendarMain.setFdLocation(cal.getEventLocation()); //地点
        kmCalendarMain.setFdRelationUrl(cal.getRelationUrl()); //url
        kmCalendarMain.setFdType(KmCalendarConstant.CALENDAR_TYPE_EVENT); //事件类型（钉钉没有这个字段，取默认）
        kmCalendarMain.setFdAuthorityType(KmCalendarConstant.AUTHORITY_TYPE_DEFAULT); //活动性质（钉钉没有这个字段，取默认）
        kmCalendarMain.setFdDesc(cal.getFdDecs()); //描述
        kmCalendarMain.setFdIsGroup(Boolean.FALSE);
    }

    /**
     * 接收钉钉日程事件
     * @param event
     */
    @Override
    public void onApplicationEvent(Event_Common event) {
        if ("dingCalendarChange".equals(event.getSource().toString())) {//监听钉钉同步ekp日程
            Map params = ((Event_Common) event).getParams();
            if (null == params || params.size() <= 0) {
                return;
            }
                SyncroCommonCal syncroCommonCal = buildSyncroCommonCal(params); //构建SyncroCommonCal对象
                String changeType = (String) params.get("changeType");
                String personId = (String) params.get("personId");
                try {
                    if ("created".equals(changeType)) { //新增
                        logger.info("======钉钉新增日程同步ekp======");
                        addDingToCalendar(personId, syncroCommonCal);
                    } else if ("updated".equals(changeType)) { //更新
                        logger.info("======钉钉更新日程同步ekp======");
                        updateDingToCalendar(personId, syncroCommonCal);
                    } else if ("cancelled".equals(changeType)) { //删除
                        logger.info("======钉钉删除日程同步ekp======");
                        deleteDingToCalendar(personId, syncroCommonCal);
                    }
                } catch (Exception e) {
                    logger.error("钉钉同步日程到ekp出错", e);
                }
            }
        }


    private SyncroCommonCal buildSyncroCommonCal(Map params) {
        SyncroCommonCal syncroCommonCal =new SyncroCommonCal();
        syncroCommonCal.setPersonId((String) params.get("personId")); //用户id
        syncroCommonCal.setCreatorId((String) params.get("creatorId")); //创建者id
        syncroCommonCal.setUuid((String) params.get("uuid")); //uuId
        syncroCommonCal.setAppKey((String) params.get("appKey"));//appKey
        syncroCommonCal.setCalendarId((String) params.get("calendarId")); //日程ID
        syncroCommonCal.setSubject((String) params.get("subject")); //活动名称
        syncroCommonCal.setContent((String) params.get("content"));//活动内容
        syncroCommonCal.setAllDayEvent((Boolean) params.get("isAllDayEvent"));//是否全天
        syncroCommonCal.setEventStartTime((Date) params.get("eventStartTime")); //开始时间
        syncroCommonCal.setEventFinishTime((Date) params.get("eventFinishTime"));//结束时间
        syncroCommonCal.setEventLocation((String) params.get("eventLocation"));//地点
        syncroCommonCal.setRecurrentStr((String) params.get("recurrentStr")); //循环设置
        syncroCommonCal.setRelatedPersonIds((List) params.get("relatedPersonIds"));  //日程相关人
        syncroCommonCal.setFdDecs((String) params.get("fdDecs")); //描述
        syncroCommonCal.setCreateTime((Date) params.get("createTime")); //创建时间
        syncroCommonCal.setUpdateTime((Date) params.get("updateTime")); //更新时间
        return syncroCommonCal;
    }
}
