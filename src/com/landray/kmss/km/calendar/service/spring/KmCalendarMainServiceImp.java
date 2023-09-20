package com.landray.kmss.km.calendar.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.km.calendar.cms.CMSPlugin;
import com.landray.kmss.km.calendar.cms.CMSPluginData;
import com.landray.kmss.km.calendar.cms.CMSThreadPoolManager;
import com.landray.kmss.km.calendar.cms.interfaces.ICMSProvider;
import com.landray.kmss.km.calendar.constant.KmCalendarConstant;
import com.landray.kmss.km.calendar.dao.IKmCalendarMainDao;
import com.landray.kmss.km.calendar.forms.KmCalendarMainForm;
import com.landray.kmss.km.calendar.model.*;
import com.landray.kmss.km.calendar.service.*;
import com.landray.kmss.km.calendar.util.CalendarQueryContext;
import com.landray.kmss.km.calendar.util.LunarRecurrenceUtil;
import com.landray.kmss.km.calendar.util.Rfc2445Util;
import com.landray.kmss.sys.agenda.interfaces.CommonCal;
import com.landray.kmss.sys.agenda.interfaces.IAgendaSyncroService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.notify.constant.SysNotifyConstants;
import com.landray.kmss.sys.notify.interfaces.*;
import com.landray.kmss.sys.notify.model.SysNotifyRemindCommon;
import com.landray.kmss.sys.notify.model.SysNotifyRemindMain;
import com.landray.kmss.sys.notify.service.ISysNotifyRemindCommonService;
import com.landray.kmss.sys.notify.service.ISysNotifyRemindMainService;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.notify.util.SysNotifyRemindUtil;
import com.landray.kmss.sys.notify.util.SysNotifyTypeEnum;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.portal.cloud.util.ListDataUtil;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzCoreService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzModelContext;
import com.landray.kmss.sys.quartz.scheduler.CronExpression;
import com.landray.kmss.util.*;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.TransactionStatus;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * 日程管理主文档业务接口实现
 *
 * @author
 * @version 1.0 2013-10-14
 */
public class KmCalendarMainServiceImp extends BaseServiceImp implements
        IKmCalendarMainService, IXMLDataBean, KmCalendarConstant,
        IAgendaSyncroService, ApplicationListener {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(KmCalendarMainServiceImp.class);


    private String contextPath;

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    private ISysOrgCoreService sysOrgCoreService = null;

    public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
        this.sysOrgCoreService = sysOrgCoreService;
    }

    private ISysOrgPersonService sysOrgPersonService = null;

    public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
        this.sysOrgPersonService = sysOrgPersonService;
    }

    private IKmCalendarSyncMappingService kmCalendarSyncMappingService = null;

    public void setKmCalendarSyncMappingService(
            IKmCalendarSyncMappingService kmCalendarSyncMappingService) {
        this.kmCalendarSyncMappingService = kmCalendarSyncMappingService;
    }

    private IKmCalendarOutCacheService kmCalendarOutCacheService;

    public IKmCalendarOutCacheService getKmCalendarOutCacheService() {
        return kmCalendarOutCacheService;
    }

    public void setKmCalendarOutCacheService(
            IKmCalendarOutCacheService kmCalendarOutCacheService) {
        this.kmCalendarOutCacheService = kmCalendarOutCacheService;
    }

    private IKmCalendarSyncBindService kmCalendarSyncBindService;

    public IKmCalendarSyncBindService getKmCalendarSyncBindService() {
        return kmCalendarSyncBindService;
    }

    public void setKmCalendarSyncBindService(
            IKmCalendarSyncBindService kmCalendarSyncBindService) {
        this.kmCalendarSyncBindService = kmCalendarSyncBindService;
    }

    private IKmCalendarLabelService kmCalendarLabelService;

    public IKmCalendarLabelService getKmCalendarLabelService() {
        return kmCalendarLabelService;
    }

    public void setKmCalendarLabelService(
            IKmCalendarLabelService kmCalendarLabelService) {
        this.kmCalendarLabelService = kmCalendarLabelService;
    }

    private IKmCalendarMainGroupService kmCalendarMainGroupService;

    public IKmCalendarMainGroupService getKmCalendarMainGroupService() {
        return kmCalendarMainGroupService;
    }

    public void setKmCalendarMainGroupService(
            IKmCalendarMainGroupService kmCalendarMainGroupService) {
        this.kmCalendarMainGroupService = kmCalendarMainGroupService;
    }

    private IKmCalendarAuthService kmCalendarAuthService;

    public void setKmCalendarAuthService(
            IKmCalendarAuthService kmCalendarAuthService) {
        this.kmCalendarAuthService = kmCalendarAuthService;
    }

    private IKmCalendarDetailsService kmCalendarDetailsService;

    public void setKmCalendarDetailsService(IKmCalendarDetailsService kmCalendarDetailsService) {
        this.kmCalendarDetailsService = kmCalendarDetailsService;
    }

    @Override
    public JSONArray getData(RequestContext requestContext) throws Exception {
        return null;
    }


    @Override
    public List<Map<String, String>> getDataList(RequestContext requestInfo)
            throws Exception {
        setContextPath(requestInfo.getContextPath());
        List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
        return rtnList;
    }

    @Override
    public void addCalElement(CommonCal cal) throws Exception {
        KmCalendarMain kmCalendarMain = new KmCalendarMain();
        // 同步提醒信息
        kmCalendarMain.setFdId(IDGenerator.generateID());
        kmCalendarMain.setDocCreateTime(new Date());
        updateKmCalendarMain(cal, kmCalendarMain);
        // 初始化同步日程权限
        initAuthData(kmCalendarMain);
        add(kmCalendarMain, cal);
        KmCalendarSyncMapping mapping = new KmCalendarSyncMapping();
        mapping.setFdAppKey(cal.getModelName());
        mapping.setFdAppUuid(cal.getDocId());
        mapping.setFdCalendarId(kmCalendarMain.getFdId());
        mapping.setFdId(IDGenerator.generateID());
        kmCalendarSyncMappingService.add(mapping);
    }

    /**
     * 同步相关人到日程可阅读者的表
     *
     * @param kmCalendarMain
     */
    private void addAuthData(KmCalendarMain kmCalendarMain) {
        List<SysOrgPerson> relatedPersons = kmCalendarMain.getFdRelatedPersons();
        List<SysOrgPerson> authReaders = kmCalendarMain.getAuthReaders();
        if (!ArrayUtil.isEmpty(relatedPersons)) {
            for (SysOrgPerson person : relatedPersons) {
                authReaders.add(person);
            }
            kmCalendarMain.setAuthReaders(authReaders);
        }
    }

    // 初始化同步日程的权限
    private void initAuthData(KmCalendarMain kmCalendarMain) throws Exception {
        IKmCalendarAuthService kmCalendarAuthService = (IKmCalendarAuthService) SpringBeanUtil
                .getBean("kmCalendarAuthService");
        KmCalendarAuth kmCalendarAuth = kmCalendarAuthService
                .findByPerson(kmCalendarMain.getDocOwner().getFdId());
        if (kmCalendarAuth != null) {
            for (Object obj : kmCalendarAuth.getAuthReaders()) {
                // 增加可阅读者
                kmCalendarMain.getAuthReaders().add(obj);
            }
            for (Object obj : kmCalendarAuth.getAuthModifiers()) {
                // //增加可维护者
                kmCalendarMain.getAuthEditors().add(obj);
            }
        }
    }

    @Override
    public void deleteCalElement(String modelName, String docId)
            throws Exception {
        List<String> calendarIds = kmCalendarSyncMappingService
                .findCalendarIds(modelName, docId);
        delete((String[]) calendarIds.toArray(new String[0]));
        // 删除映射数据
        kmCalendarSyncMappingService.delete(modelName, docId);
    }

    @Override
    public void deleteCalElement(String modelName, String docId, String personId)
            throws Exception {
        if (StringUtil.isNull(personId)) {
            deleteCalElement(modelName, docId);
            return;
        }
        KmCalendarMain calendar = findCalendar(modelName, docId, personId);
        if (calendar != null) {
            delete(calendar);
            kmCalendarSyncMappingService.delete(modelName, docId, calendar
                    .getFdId());
        }
    }

    @Override
    public List<String> getCalPersonIds(String modelName, String docId)
            throws Exception {
        return kmCalendarSyncMappingService.getOwnerIds(modelName, docId);
    }

    @Override
    public CommonCal getCommonCal(String modelName, String docId,
                                  String personId) throws Exception {
        KmCalendarMain calendar = findCalendar(modelName, docId, personId);
        if (calendar != null) {
            CommonCal cal = new CommonCal();
            cal.setCalType(calendar.getFdType());
            cal.setDocContent(calendar.getDocContent());
            cal.setDocId(docId);
            cal.setDocSubject(calendar.getDocSubject());
            cal.setEventFinishTime(calendar.getDocFinishTime());
            cal.setEventLocation(calendar.getFdLocation());
            cal.setEventStartTime(calendar.getDocStartTime());
            cal.setLunar(calendar.getFdIsLunar());
            cal.setPersonId(calendar.getDocCreator().getFdId());
            return cal;
        }
        return null;
    }

    private SysNotifyRemindMainContextModel genNotifyRemindMainContextModel(
            CommonCal cal, KmCalendarMain kmCalendarMain) throws Exception {
        List<SysNotifyRemindMain> sysNotifyRemindMainList = sysNotifyRemindMainService
                .getSysNotifyRemindMainList(cal.getSysAgendaMainModel()
                        .getFdId());
        /*员工活动的“活动提交即同步”不生效，是因为新建的时候上面的查询不生效，毕竟还没在数据库生成记录，故在下面加多一个处理 start by 朱国荣 2016-7-04*/
        if (sysNotifyRemindMainList.isEmpty()) {
            if (cal != null && cal.getSysAgendaMainModel() != null && cal.getSysAgendaMainModel().getSysNotifyRemindMainContextModel() != null) {
                sysNotifyRemindMainList = cal.getSysAgendaMainModel().getSysNotifyRemindMainContextModel().getSysNotifyRemindMainList();
            }
        }
        /*end*/
        SysNotifyRemindMainContextModel notifyRemindMainContextModel_new = new SysNotifyRemindMainContextModel();
        List<SysNotifyRemindMain> sysNotifyRemindMainList_new = new ArrayList<SysNotifyRemindMain>();
        if (sysNotifyRemindMainList != null
                && sysNotifyRemindMainList.size() > 0) {
            for (SysNotifyRemindMain sysNotifyRemindMain : sysNotifyRemindMainList) {
                SysNotifyRemindMain sysNotifyRemindMain_new = new SysNotifyRemindMain();
                sysNotifyRemindMain_new.setFdBeforeTime(sysNotifyRemindMain
                        .getFdBeforeTime());
                sysNotifyRemindMain_new.setFdId(IDGenerator.generateID());
                sysNotifyRemindMain_new.setFdKey(null);
                sysNotifyRemindMain_new.setFdModelId(kmCalendarMain.getFdId());
                sysNotifyRemindMain_new.setFdModelName(kmCalendarMain
                        .getClass().getName());
                sysNotifyRemindMain_new.setFdNotifyType(sysNotifyRemindMain
                        .getFdNotifyType());
                sysNotifyRemindMain_new.setFdTimeUnit(sysNotifyRemindMain
                        .getFdTimeUnit());
                sysNotifyRemindMainList_new.add(sysNotifyRemindMain_new);
            }
            notifyRemindMainContextModel_new
                    .setSysNotifyRemindMainList(sysNotifyRemindMainList_new);
        }
        return notifyRemindMainContextModel_new;
    }

    @Override
    public void updateKmCalendarMain(CommonCal cal,
                                     KmCalendarMain kmCalendarMain) throws Exception {
        kmCalendarMain.setDocContent(cal.getDocContent());
        SysOrgPerson person = (SysOrgPerson) (sysOrgPersonService
                .findByPrimaryKey(cal.getPersonId()));
        kmCalendarMain.setDocCreator(person);
        kmCalendarMain.setDocOwner(person);
        Date start = cal.getEventStartTime();
        Date end = cal.getEventFinishTime();
        kmCalendarMain.setDocSubject(cal.getDocSubject());
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
        kmCalendarMain.setDocStartTime(start);
        kmCalendarMain.setDocFinishTime(end);

        kmCalendarMain.setFdIsAlldayevent(isAllDay);
        kmCalendarMain.setFdIsLunar(cal.isLunar());
        kmCalendarMain.setFdLastModifiedTime(new Date());
        kmCalendarMain.setFdLocation(cal.getEventLocation());
        kmCalendarMain.setFdRelationUrl(cal.getDocUrl());
        kmCalendarMain.setFdType(cal.getCalType());
        kmCalendarMain
                .setFdAuthorityType(KmCalendarConstant.AUTHORITY_TYPE_DEFAULT);
        kmCalendarMain
                .setSysNotifyRemindMainContextModel(genNotifyRemindMainContextModel(
                        cal, kmCalendarMain));
        kmCalendarMain.setFdModelName(cal.getModelName()); //modelName
        //创建人的label
        KmCalendarLabel kmCalendarLabel = kmCalendarLabelService.findLabel(cal
                .getModelName(), cal.getPersonId());
        if (kmCalendarLabel == null) { //标签
            kmCalendarLabel = kmCalendarLabelService.addAgendaLabel(cal
                    .getModelName(), cal.getPersonId());
        }
        kmCalendarMain.setDocLabel(kmCalendarLabel);
        //处理相关人的标签字段
        List<SysOrgPerson> relPersons =  kmCalendarMain.getFdRelatedPersons();
        if(!ArrayUtil.isEmpty(relPersons)){ //如果有相关人
            for(SysOrgPerson relPerson: relPersons){
                String fdId = relPerson.getFdId();
                KmCalendarLabel calendarLabelModel= kmCalendarLabelService.findLabel(cal
                        .getModelName(), fdId);
                if (calendarLabelModel == null) { //标签
                    calendarLabelModel = kmCalendarLabelService.addAgendaLabel(cal
                            .getModelName(), fdId);
                }
            }
        }

    }

    @Override
    public void updateCalElement(CommonCal cal) throws Exception {
        if (StringUtil.isNotNull(cal.getPersonId())) {
            KmCalendarMain kmCalendarMain = findCalendar(cal.getModelName(),
                    cal.getDocId(), cal.getPersonId());
            if (kmCalendarMain != null) {
                kmCalendarMain.setDocAlterTime(new Date());
                updateKmCalendarMain(cal, kmCalendarMain);
                update(kmCalendarMain);
            } else {
                addCalElement(cal);
            }
        } else {
            List<KmCalendarMain> kmCalendarMains = findCalendars(cal
                    .getModelName(), cal.getDocId());
            for (KmCalendarMain kmCalendarMain : kmCalendarMains) {
                kmCalendarMain.setDocAlterTime(new Date());
                updateKmCalendarMain(cal, kmCalendarMain);
                update(kmCalendarMain);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public String findCalendarId(String modelName, String docId, String personId)
            throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock("kmCalendarMain.fdCalendarId");
        hqlInfo
                .setFromBlock("KmCalendarMain kmCalendarMain ,KmCalendarSyncMapping kmCalendarSyncMapping");
        hqlInfo
                .setWhereBlock("kmCalendarMain.docCreator.fdId=:personId and kmCalendarSyncMapping.fdAppKey=:fdAppKey and kmCalendarSyncMapping.fdAppUuid=:fdAppUuid");
        hqlInfo.setParameter("personId", personId);
        hqlInfo.setParameter("fdAppKey", modelName);
        hqlInfo.setParameter("fdAppUuid", docId);
        List<String> list = (List<String>) findList(hqlInfo);
        return (list != null && list.size() > 0) ? list.get(0) : null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public KmCalendarMain findCalendar(String modelName, String docId,
                                       String personId) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo
                .setFromBlock("KmCalendarMain kmCalendarMain ,KmCalendarSyncMapping kmCalendarSyncMapping");
        hqlInfo
                .setWhereBlock("kmCalendarMain.fdId=kmCalendarSyncMapping.fdCalendarId and kmCalendarMain.docOwner.fdId=:personId and kmCalendarSyncMapping.fdAppKey=:fdAppKey and kmCalendarSyncMapping.fdAppUuid=:fdAppUuid");
        hqlInfo.setParameter("personId", personId);
        hqlInfo.setParameter("fdAppKey", modelName);
        hqlInfo.setParameter("fdAppUuid", docId);
        List<KmCalendarMain> list = (List<KmCalendarMain>) findList(hqlInfo);
        return (list != null && list.size() > 0) ? list.get(0) : null;
    }

    @SuppressWarnings("unchecked")
    public List<KmCalendarMain> findCalendars(String modelName, String docId)
            throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo
                .setFromBlock("KmCalendarMain kmCalendarMain, KmCalendarSyncMapping kmCalendarSyncMapping");
        hqlInfo
                .setWhereBlock("kmCalendarMain.fdId=kmCalendarSyncMapping.fdCalendarId and kmCalendarSyncMapping.fdAppKey=:fdAppKey and kmCalendarSyncMapping.fdAppUuid=:fdAppUuid");
        hqlInfo.setParameter("fdAppKey", modelName);
        hqlInfo.setParameter("fdAppUuid", docId);
        List<KmCalendarMain> list = (List<KmCalendarMain>) findList(hqlInfo);
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public KmCalendarMain findCalendarByCacheOut(String appKey, String appUuid)
            throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo
                .setFromBlock("KmCalendarMain kmCalendarMain, KmCalendarOutCache kmCalendarOutCache");
        hqlInfo
                .setWhereBlock("kmCalendarMain.fdId=kmCalendarOutCache.fdCalendarId and kmCalendarOutCache.fdAppKey=:fdAppKey and kmCalendarOutCache.fdAppUuid=:fdAppUuid");
        hqlInfo.setParameter("fdAppKey", appKey);
        hqlInfo.setParameter("fdAppUuid", appUuid);
        List<KmCalendarMain> list = (List<KmCalendarMain>) findList(hqlInfo);
        return (list != null && list.size() != 0) ? list.get(0) : null;
    }

    @Override
    public void updateSelf(IBaseModel modelObj) throws Exception {
        getBaseDao().update(modelObj);
        if (dispatchCoreService != null) {
            dispatchCoreService.update(modelObj);
        }
    }

    @Override
    public String addSelf(IBaseModel modelObj) throws Exception {
        KmCalendarMain kmCalendarMain = (KmCalendarMain) modelObj;
        kmCalendarMain
                .setFdAuthorityType(KmCalendarConstant.AUTHORITY_TYPE_DEFAULT);
        if (StringUtil.isNotNull(kmCalendarMain.getFdRecurrenceStr())) {
            // kmCalendarMain.getFdRecurrenceLastStart()
            setRecurrenceLastDate(kmCalendarMain);
        }
        String rtnVal = getBaseDao().add(modelObj);
        if (dispatchCoreService != null) {
            dispatchCoreService.add(modelObj);
        }
        return rtnVal;
    }

    @Override
    public void deleteSelf(IBaseModel modelObj) throws Exception {
        if (dispatchCoreService != null) {
            dispatchCoreService.delete(modelObj);
        }
        getBaseDao().delete(modelObj);
    }

    @Override
    protected IBaseModel convertBizFormToModel(IExtendForm form,
                                               IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);

        KmCalendarMain kmCalendarModel = (KmCalendarMain) model;
        KmCalendarMainForm kmCalendarForm = (KmCalendarMainForm) form;

        if (KmCalendarConstant.CALENDAR_TYPE_EVENT.equals(kmCalendarForm
                .getFdType())) {
            // 如果重复类型为不重复，将原来的重复信息置为空
            if (kmCalendarModel.getFdIsLunar()) {
                if (KmCalendarConstant.RECURRENCE_FREQ_NO.equals(kmCalendarForm
                        .getRECURRENCE_FREQ_LUNAR())) {
                    kmCalendarModel.setFdRecurrenceStr(null);
                }
            } else {
                if (KmCalendarConstant.RECURRENCE_FREQ_NO.equals(kmCalendarForm
                        .getRECURRENCE_FREQ())) {
                    kmCalendarModel.setFdRecurrenceStr(null);
                }
            }
        }

        return kmCalendarModel;
    }


    private void updateCalendarValue(KmCalendarMain kmCalendarMain)
            throws Exception {
        if (kmCalendarMain.getFdType().equals(
                KmCalendarConstant.CALENDAR_TYPE_NOTE)) { //类型是笔记
            kmCalendarMain.setDocStartTime(kmCalendarMain.getDocStartTime());
            kmCalendarMain.setDocFinishTime(kmCalendarMain.getDocStartTime());
        } else if (kmCalendarMain.getFdType().equals(
                KmCalendarConstant.CALENDAR_TYPE_EVENT)) { //类型是event
            Date finish = kmCalendarMain.getDocFinishTime();
            if (finish == null) {
                finish = kmCalendarMain.getDocStartTime();
            }
            if (kmCalendarMain.getFdIsAlldayevent()) {
                Calendar c = Calendar.getInstance();
                c.setTime(finish);
                c.set(Calendar.HOUR_OF_DAY, 23);
                c.set(Calendar.MINUTE, 59);
                c.set(Calendar.SECOND, 59);
                kmCalendarMain.setDocFinishTime(c.getTime());
            }
            String recurrenceStr = kmCalendarMain.getFdRecurrenceStr();
            if (StringUtil.isNotNull(recurrenceStr)) {
                // #5619 月重复日程的开始时间修改后，同步修改重复信息
                Map<String, String> recurrenceStrParam = Rfc2445Util
                        .parseRecurrenceStr(kmCalendarMain.getFdRecurrenceStr());
                if (recurrenceStrParam.get(KmCalendarConstant.RECURRENCE_BYDAY) != null
                        && KmCalendarConstant.RECURRENCE_FREQ_MONTHLY
                        .equals(recurrenceStrParam
                                .get(KmCalendarConstant.RECURRENCE_FREQ))) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(kmCalendarMain.getDocStartTime());
                    String[] weeks = {"SU", "MO", "TU", "WE", "TH", "FR", "SA"};
                    String byDay = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)
                            + weeks[calendar.get(Calendar.DAY_OF_WEEK) - 1];
                    kmCalendarMain.setFdRecurrenceStr(Rfc2445Util
                            .setRecurrenceParam(kmCalendarMain
                                    .getFdRecurrenceStr(), "BYDAY", byDay));
                }
                // 同步修改重复日程开始时间
                setKmCalendarDate(kmCalendarMain);
                // 同步修改最后重复日期
                setRecurrenceLastDate(kmCalendarMain);
            }
        }
    }

    /**
     * 修改重复日程开始时间 针对如下特殊情况： 周重复日程开始时间为星期一，重复时间为星期二、星期三时，将重复日程开始时间修改为星期二
     */
    private void setKmCalendarDate(KmCalendarMain kmCalendarMain)
            throws Exception {
        String recurrenceStr = kmCalendarMain.getFdRecurrenceStr();
        Boolean isLunar = kmCalendarMain.getFdIsLunar();
        if (isLunar == false) {
            long diff = kmCalendarMain.getDocFinishTime().getTime()
                    - kmCalendarMain.getDocStartTime().getTime();
            List<Date> excuteDateList = Rfc2445Util.getExcuteDateList(
                    recurrenceStr, kmCalendarMain.getDocStartTime(), 1);
            if (!excuteDateList.isEmpty()
                    && excuteDateList.get(0).getTime() > kmCalendarMain
                    .getDocStartTime().getTime()) {
                kmCalendarMain.setDocStartTime(excuteDateList.get(0));
                if (kmCalendarMain.getDocFinishTime() != null) {
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(kmCalendarMain.getDocStartTime()
                            .getTime()
                            + diff);
                    kmCalendarMain.setDocFinishTime(c.getTime());
                }
            }
        }
    }

    /**
     * 设置重复日历的最后时间
     *
     * @param kmCalendarMain
     * @throws ParseException
     */
    @Override
    public void setRecurrenceLastDate(KmCalendarMain kmCalendarMain)
            throws ParseException {
        String recurrenceStr = kmCalendarMain.getFdRecurrenceStr();
        Date recurrenceLastStart = null;
        if (kmCalendarMain.getFdIsLunar()) {
            recurrenceLastStart = LunarRecurrenceUtil.getLastSolarDate(
                    kmCalendarMain.getDocStartTime(), recurrenceStr);
        } else {
            recurrenceLastStart = Rfc2445Util.getLastedExecuteDate(
                    recurrenceStr, kmCalendarMain.getDocStartTime());
        }
        if (recurrenceLastStart != null) {
            Long recurrenceLastEndLong = recurrenceLastStart.getTime();
            recurrenceLastEndLong = recurrenceLastEndLong
                    + kmCalendarMain.getDocFinishTime().getTime()
                    - kmCalendarMain.getDocStartTime().getTime();
            Date recurrenceLastEnd = new Date(recurrenceLastEndLong);
            kmCalendarMain.setFdRecurrenceLastStart(recurrenceLastStart);
            kmCalendarMain.setFdRecurrenceLastEnd(recurrenceLastEnd);
        } else {
            throw new ParseException(recurrenceStr, 0);
        }
    }

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        return addByAppKey(modelObj, null);
    }

    public String add(IBaseModel modelObj, CommonCal cal) throws Exception {
        return addByAppKey(modelObj, null, cal);

    }

    @Override
    public String addByAppKey(IBaseModel modelObj, String appKey, CommonCal cal) throws Exception {
        return this.addByAppKey(modelObj, appKey, cal, true);
    }

    /**
     * @param modelObj
     * @param appKey
     * @param cal
     * @param updateOutCache 钉钉同步回ekp的数据不需要新增到KmcalendarOutCache
     * @return
     * @throws Exception
     */
    @Override
    public String addByAppKey(IBaseModel modelObj, String appKey, CommonCal cal, boolean updateOutCache) throws Exception {
        KmCalendarMain kmCalendarMain = (KmCalendarMain) modelObj;
        // kmCalendarMain.setDocStatus("30");
        Date current = new Date();
        kmCalendarMain.setDocCreateTime(current);
        kmCalendarMain.setDocAlterTime(current);
        if (kmCalendarMain.getDocOwner() == null) {
            kmCalendarMain.setDocOwner(UserUtil.getUser());
        }
        if (kmCalendarMain.getDocCreator() == null) {
            kmCalendarMain.setDocCreator(UserUtil.getUser());
        }
        if (kmCalendarMain.getFdType().equals(
                KmCalendarConstant.CALENDAR_TYPE_EVENT)) {
            updateCalendarValue(kmCalendarMain);
        } else if (kmCalendarMain.getFdType().equals(
                KmCalendarConstant.CALENDAR_TYPE_NOTE)) {
            Date docStartTime = kmCalendarMain.getDocStartTime();
            Calendar c = Calendar.getInstance();
            if (docStartTime == null) {
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                docStartTime = c.getTime();
                kmCalendarMain.setDocStartTime(c.getTime());
            }

            c.setTime(docStartTime);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            kmCalendarMain.setDocFinishTime(c.getTime());
            kmCalendarMain.setFdIsAlldayevent(true);
        }
        //新增数据到人员详情表
        if (!ArrayUtil.isEmpty(kmCalendarMain.getFdRelatedPersons())) {
            addCalendarDetails(kmCalendarMain);
        }
        // setAuthData(kmCalendarMain);
        String rtnVal = getBaseDao().add(kmCalendarMain);

        if (updateOutCache) {
            // 增加接出cache
            kmCalendarOutCacheService.updateCalendarOutCaches(kmCalendarMain,
                    KmCalendarConstant.OPERATION_TYPE_ADD, kmCalendarMain
                            .getDocOwner().getFdId(),
                    appKey);
        } else {
            // 增加接出cache
            kmCalendarOutCacheService.updateCalendarOutCaches(kmCalendarMain,
                    KmCalendarConstant.OPERATION_TYPE_UPDATE, kmCalendarMain
                            .getDocOwner().getFdId(),
                    appKey);
        }

        if (dispatchCoreService != null) {
            dispatchCoreService.add(modelObj);
        }

        // 增加提醒定时任务
        ISysNotifyRemindQuartzModelContext context = initSysNotifyRemindQuartzModelContext(kmCalendarMain);
        addScheduler(context, kmCalendarMain, cal);
        return rtnVal;

    }

    /**
     * 新增日程相关人信息到详情表
     *
     * @param kmCalendarMain
     */
    @Override
    public void addCalendarDetails(KmCalendarMain kmCalendarMain) throws Exception {
        List<SysOrgPerson> fdRelatedPersons = kmCalendarMain.getFdRelatedPersons(); //获取日程相关人
        if (!ArrayUtil.isEmpty(fdRelatedPersons)) {
            for (SysOrgPerson person : fdRelatedPersons) {
                String personId = person.getFdId();
                KmCalendarLabel kmCalendarLabel = kmCalendarLabelService.findLabel(kmCalendarMain
                        .getFdModelName(),personId);
                KmCalendarDetails detail = new KmCalendarDetails();
                detail.setFdCalendar(kmCalendarMain);
                detail.setFdPerson(person);
                detail.setFdIsDelete(Boolean.FALSE);
                if(kmCalendarLabel!=null) {
                    detail.setFdLabelId(kmCalendarLabel.getFdId()); //设置labelID
                }
                //添加
                kmCalendarDetailsService.add(detail);
            }
        }
    }


    @Override
    public String addByAppKey(IBaseModel modelObj, String appKey) throws Exception {
        return addByAppKey(modelObj, appKey, null);

    }

    @Override
    public void update(IBaseModel modelObj) throws Exception {
        updateByAppKey(modelObj, null);
    }

    @Override
    public void updateByAppKey(IBaseModel modelObj, String appKey) throws Exception {

        KmCalendarMain kmCalendarMain = (KmCalendarMain) modelObj;
        if (kmCalendarMain.getFdIsGroup() == null
                || !kmCalendarMain.getFdIsGroup()) {
            deleteMg(kmCalendarMain.getFdId());
        }
        // kmCalendarMain.setDocStatus("30");
        kmCalendarMain.setDocAlterTime(new Date()); //修改时间
        // kmCalendarMain.setDocCreator(UserUtil.getUser());
        if (kmCalendarMain.getFdType().equals(KmCalendarConstant.CALENDAR_TYPE_EVENT)) { //修改类型为-event
            updateCalendarValue(kmCalendarMain);
        } else {
            kmCalendarMain.setFdIsAlldayevent(true);
        }
        // setAuthData(kmCalendarMain);

        getBaseDao().update(modelObj);
        //更新人员详情表
        updateClaendarDetails(kmCalendarMain);
        // 更新接出cache
        kmCalendarOutCacheService.updateCalendarOutCaches(kmCalendarMain, KmCalendarConstant.OPERATION_TYPE_UPDATE,
                kmCalendarMain.getDocOwner().getFdId(), appKey);

        if (dispatchCoreService != null) {
            dispatchCoreService.update(modelObj);
        }

        // 更新提醒定时任务
        ISysNotifyRemindQuartzModelContext context = initSysNotifyRemindQuartzModelContext(kmCalendarMain);
        updateScheduler(context, kmCalendarMain);

    }

    private void deleteMg(String fdId) throws Exception {
        String sql = "delete from km_calendar_mg where fd_calendar_main_id=?";
        NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(sql);
        query.addSynchronizedQuerySpace("km_calendar_mg");
        query.setString(0, fdId);
        query.executeUpdate();
    }

    //删除日程
    @Override
    public void delete(IBaseModel modelObj) throws Exception {
        KmCalendarMain kmCalendarMain = (KmCalendarMain) modelObj;
        SysOrgPerson docOwner = kmCalendarMain.getDocOwner();//获取日程的拥有者
        List<SysOrgElement> authEditors = sysOrgCoreService
                .expandToPerson(kmCalendarMain.getAuthEditors());//获取可维护者
        SysOrgPerson curUser = UserUtil.getUser();
        if (curUser.equals(docOwner)
                || authEditors.contains(curUser)) {//删除人是创建人,删除人是可维护者
            UserOperHelper.logDelete(modelObj);
            deleteByAppKey(modelObj, null);
        } else {
            //删除人不是创建人，那么只设置日程详情表该用户的日程显示删除
            updateISdelete(kmCalendarMain.getFdId(), docOwner.getFdId());
        }
    }

    /***
     * 设置相关人详情表 删除标识
     * @param fdCalendarId
     * @param currentId
     */
    private void updateISdelete(String fdCalendarId, String currentId) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("kmCalendarDetails.fdCalendar.fdId=:fdCalendarId and kmCalendarDetails.fdPerson.fdId =:currentId");
        hqlInfo.setParameter("fdCalendarId", fdCalendarId);
        hqlInfo.setParameter("currentId", currentId);
        List<KmCalendarDetails> list = kmCalendarDetailsService.findList(hqlInfo);
        if (!ArrayUtil.isEmpty(list)) {
            KmCalendarDetails kmCalendarDetails = list.get(0);
            kmCalendarDetails.setFdIsDelete(Boolean.TRUE);
            kmCalendarDetailsService.update(kmCalendarDetails);
        }
    }

    /**
     * 钉钉取消日程 同步删除EKP日程
     */
    @Override
    public void deleteCalendar(IBaseModel modelObj) throws Exception {
        KmCalendarMain kmCalendarMain = (KmCalendarMain) modelObj;
        if (kmCalendarMain != null) {
            //删除日程（包括删除主文档及删除同步映射表及相关人详情表）
            deleteByAppKey(modelObj, null);
        }
    }

    /**
     * 更新日程详情表的数据
     *
     * @param kmCalendarMain
     */
    private void updateClaendarDetails(KmCalendarMain kmCalendarMain) throws Exception {
        String modeleName = kmCalendarMain.getFdModelName();
        //删除之前的相关人
        if (kmCalendarMain != null) {
            deleteCalendarDetails(kmCalendarMain);
        }
        List<SysOrgPerson> fdRelatedPersons = kmCalendarMain.getFdRelatedPersons();
        //添加新的相关人
        if (!ArrayUtil.isEmpty(fdRelatedPersons)) {
            for (SysOrgPerson person : fdRelatedPersons) {
                KmCalendarDetails detail = new KmCalendarDetails();
                //如果标签不为空
                if(kmCalendarMain.getDocLabel()!=null && StringUtil.isNotNull(modeleName)){
                    KmCalendarLabel kmCalendarLabel = kmCalendarLabelService.findLabel(modeleName,person.getFdId());
                    detail.setFdLabelId(kmCalendarLabel.getFdId());
                }
                detail.setFdCalendar(kmCalendarMain);
                detail.setFdPerson(person);
                detail.setFdIsDelete(Boolean.FALSE);
                //添加
                kmCalendarDetailsService.add(detail);
            }
        }
    }

    @Override
    public void deleteByAppKey(IBaseModel modelObj, String appKey) throws Exception {
        if (dispatchCoreService != null) {
            dispatchCoreService.delete(modelObj);
        }
        KmCalendarMain kmCalendarMain = (KmCalendarMain) modelObj;
        deleteMg(kmCalendarMain.getFdId());
        // 更新接出cache
        kmCalendarOutCacheService.updateCalendarOutCaches(kmCalendarMain, KmCalendarConstant.OPERATION_TYPE_DELETE,
                kmCalendarMain.getDocOwner().getFdId(), appKey);

        // 删除映射表
        kmCalendarSyncMappingService.deleteByCalendarId(kmCalendarMain.getFdId());

        // 删除提醒定时任务
        deleteScheduler(kmCalendarMain);

        //删除日程相关人详情表
        deleteCalendarDetails(kmCalendarMain);
        getBaseDao().delete(modelObj);

    }

    /**
     * 删除日程相关人详情表信息
     *
     * @param kmCalendarMain
     * @throws Exception
     */
    @Override
    public void deleteCalendarDetails(KmCalendarMain kmCalendarMain) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock("kmCalendarDetails.fdId");
        hqlInfo.setWhereBlock("kmCalendarDetails.fdCalendar.fdId=:calendarId ");
        hqlInfo.setParameter("calendarId", kmCalendarMain.getFdId());
        List list = kmCalendarDetailsService.findValue(hqlInfo);
        Object[] obj = list.toArray();
        String[] ids = ArrayUtil.toStringArray(obj);
        //删除
        kmCalendarDetailsService.delete(ids);
    }

    private SysNotifyRemindQuartzModelContext initSysNotifyRemindQuartzModelContext(
            KmCalendarMain kmCalendarMain) throws Exception {
        Date eventStartTime = kmCalendarMain.getDocStartTime();
        List<SysOrgElement> notifyTarget = new ArrayList<SysOrgElement>();
        //创建人
        notifyTarget.add(kmCalendarMain.getDocOwner());
        //相关人也加入到提醒中
        List<SysOrgPerson> relatedPersonsList= kmCalendarMain.getFdRelatedPersons();
        if(!ArrayUtil.isEmpty(relatedPersonsList)){
            for(SysOrgPerson person:relatedPersonsList){
                notifyTarget.add(person);
            }
        }
        SysNotifyRemindQuartzModelContext remindQuartzContext = new SysNotifyRemindQuartzModelContext();
        remindQuartzContext.setEventStartTime(eventStartTime);
        remindQuartzContext.setNotifyTarget(notifyTarget);
        remindQuartzContext.setSubject(ResourceUtil.getStringValue(
                "kmCalendarMain.schedule", "km-calendar",
                ResourceUtil.getLocale(
                        kmCalendarMain.getDocOwner().getFdDefaultLang()))
                + kmCalendarMain.getDocSubject());
        remindQuartzContext.setLink(
                "/km/calendar/index_calendar.jsp?fdId="
                        + kmCalendarMain.getFdId());
        return remindQuartzContext;
    }

    @Override
    public List<KmCalendarMain> findCalendarsByLabel(String labelId)
            throws Exception {
        HQLInfo info = new HQLInfo();
        info
                .setWhereBlock("kmCalendarMain.docLabel.fdId = :labelId and kmCalendarMain.docOwner.fdId = :personId");
        info.setParameter("labelId", labelId);
        info.setParameter("personId", UserUtil.getUser().getFdId());

        List<KmCalendarMain> list = findList(info);
        return list;

    }

    @Override
    public void clearCalendarLabel(String labelId) throws Exception {
        ((IKmCalendarMainDao) getBaseDao()).clearCalendarLabel(labelId);
    }

    @Override
    public void updateBatchClearCalendarLabel(List<String> labels) throws Exception {
        ((IKmCalendarMainDao) getBaseDao()).batchClearCalendarLabel(labels);
    }

    /**
     * 获取日程所有者过滤的where子句
     */
    private String getPersonBlock(String personIds) {
        String personBlock = "";
        if (StringUtil.isNotNull(personIds)) {
            String inStr = "";
            String[] ids = personIds.split(";");
            for (String personId : ids) {
                personId = personId.trim();
                if (StringUtil.isNotNull(personId)) {
                    inStr += "'" + personId + "',";
                }
            }
            if (!"".equals(inStr)) {
                inStr = inStr.substring(0, inStr.length() - 1);
            }
            List<String> calendarId = getRelatedPersonCal(inStr);
            if (ArrayUtil.isEmpty(calendarId)) {
                personBlock += " (kmCalendarMain.docOwner.fdId in (" + inStr
                        + "))";
            } else {
                personBlock += " (kmCalendarMain.docOwner.fdId in (" + inStr
                        + ") or " + HQLUtil.buildLogicIN("kmCalendarMain.fdId", calendarId) + " ) ";
            }
        }
        return personBlock;
    }

    @Override
    public List<String> getRelatedPersonCal(String inStr) {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock("fdCalendar.fdId");
        hqlInfo.setWhereBlock("kmCalendarDetails.fdIsDelete=:fdIsDelete and kmCalendarDetails.fdPerson.fdId in ( " + inStr + " ) ");
        hqlInfo.setParameter("fdIsDelete", Boolean.FALSE);
        List list = null;
        try {
            list = kmCalendarDetailsService.findList(hqlInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取标签过滤的where子句
     *
     * @param labelIds
     * @return
     */
    private String getLabelBlock(String labelIds, HQLInfo info) {
        String labelBlock = "1=2";
        if (StringUtil.isNotNull(labelIds)) {
            String inStr = "";
            boolean includeMyEvent = false;
            boolean includeMyNote = false;
            boolean includeMyGroupEvent = false;
            boolean isNoEvent = false;
            String[] ids = labelIds.split(",");
            for (String labelId : ids) {
                labelId = labelId.trim();
                if (StringUtil.isNotNull(labelId)) {
                    if (KmCalendarConstant.CALENDAR_MY_EVENT.equals(labelId)) {
                        includeMyEvent = true;
                    } else if (KmCalendarConstant.CALENDAR_MY_NOTE
                            .equals(labelId)) {
                        includeMyNote = true;
                    } else if (KmCalendarConstant.CALENDAR_NO_EVENT
                            .equals(labelId)) {
                        isNoEvent = true;
                    } else if (KmCalendarConstant.CALENDAR_MY_GROUP_EVENT
                            .equals(labelId)) {
                        includeMyGroupEvent = true;
                    } else {
                        inStr += "'" + labelId + "',";
                    }
                }
            }
            //查询自定义标签
            if (inStr.length() > 1) {
                inStr = inStr.substring(0, inStr.length() - 1);
                String mainIds = "";
                String sql1 = "select fd_id from km_calendar_main where doc_label_id in (" + inStr + ")";
                String sql2  = "select fd_calendar_id from km_calendar_details where fd_person_id =:fdPersonId and fd_label_id in (" + inStr + ")";
                NativeQuery query1 = getBaseDao().getHibernateSession().createNativeQuery(sql1);
                query1.setCacheable(true);
                query1.setCacheMode(CacheMode.NORMAL);
                query1.setCacheRegion("km-calendar");
                query1.addScalar("fd_id", StandardBasicTypes.STRING);
                NativeQuery query2 = getBaseDao().getHibernateSession().createNativeQuery(sql2);
                query2.setCacheable(true);
                query2.setCacheMode(CacheMode.NORMAL);
                query2.setCacheRegion("km-calendar");
                query2.addScalar("fd_calendar_id", StandardBasicTypes.STRING);
                query2.setParameter("fdPersonId",UserUtil.getKMSSUser().getUserId());
                List<String> list1 = query1.list();
                if (!ArrayUtil.isEmpty(list1)) {
                    for (String fdId : list1) {
                        mainIds += "'" + fdId + "',";
                    }
                }
                List<String> list2 = query2.list();
                if (!ArrayUtil.isEmpty(list2)) {
                    for (String fdId : list2) {
                        mainIds += "'" + fdId + "',";
                    }
                }
                if(StringUtil.isNotNull(mainIds)) {
                    mainIds = mainIds.substring(0, mainIds.length() - 1);
                    labelBlock += " or (kmCalendarMain.fdId in (" + mainIds
                            + "))";
                }
            }
            if (includeMyEvent) { //我的日历
                labelBlock += " or (kmCalendarMain.docLabel is null and kmCalendarMain.fdType = 'event' and (kmCalendarMain.fdIsGroup is null or kmCalendarMain.fdIsGroup = :fdIsGroup1))";
                info.setParameter("fdIsGroup1", false);
            }
            if (includeMyNote) { //我的笔记
                labelBlock += " or (kmCalendarMain.docLabel is null and kmCalendarMain.fdType = 'note')";
            }
            if (includeMyGroupEvent) { //群组日程
                labelBlock += " or (kmCalendarMain.docLabel is null and kmCalendarMain.fdType = 'event' and kmCalendarMain.fdIsGroup = :fdIsGroup2)";
                info.setParameter("fdIsGroup2", true);
            }
            if (isNoEvent) {
                labelBlock = " 1=2 ";
            }
            labelBlock = " and (" + labelBlock + ")";
        }
        return labelBlock;
    }

    @Override
    public int getRangeCalendarsCount(CalendarQueryContext context) {
        HQLInfo hqlInfo = getRangeCalendarsHQLInfo(context);
        try {
            Page page = findPage(hqlInfo);
            return page.getTotalrows();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<KmCalendarMain> getRangeCalendars(CalendarQueryContext context) {
        HQLInfo hqlInfo = getRangeCalendarsHQLInfo(context);
        try {
            String whereBlock = hqlInfo.getWhereBlock();
            // 如果群组日程是勾选了  则传true，否则传false
            if (StringUtil.isNotNull(context.getLabelIds()) && context.getLabelIds().indexOf(KmCalendarConstant.CALENDAR_MY_GROUP_EVENT) > -1) {
                //hqlInfo.setParameter("fdIsGroup", Boolean.TRUE);
            } else {
                // 带权限查询普通日程
                if(!"true".equals(context.getIsShare())){
                    hqlInfo.setWhereBlock(StringUtil.linkString(whereBlock, " and ", "(kmCalendarMain.fdIsGroup is null or kmCalendarMain.fdIsGroup = :fdIsGroup)"));
                    hqlInfo.setParameter("fdIsGroup", Boolean.FALSE);
                }
            }

            List<KmCalendarMain> matchedKmCalendars = findPage(hqlInfo).getList();
            if (StringUtil.isNotNull(context.getGroupId())) {
                // 不带权限查询群组日程
                hqlInfo.setJoinBlock(", com.landray.kmss.km.calendar.model.KmCalendarMainGroup mainGroup left join mainGroup.fdMainList fdMainList left join mainGroup.fdGroup fdGroup");
                hqlInfo.setWhereBlock(StringUtil.linkString(whereBlock, " and ", "kmCalendarMain.fdIsGroup = :fdIsGroup and kmCalendarMain.fdId = fdMainList.fdId and fdGroup.fdId = :groupId"));
                hqlInfo.setParameter("groupId", context.getGroupId());
                hqlInfo.setParameter("fdIsGroup", Boolean.TRUE);
                hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AllCheck.NO);
                List<KmCalendarMain> calendars = findPage(hqlInfo).getList();
                ArrayUtil.concatTwoList(calendars, matchedKmCalendars);
            }
            return matchedKmCalendars;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<KmCalendarMain> getRangeCalendars(Date rangeStart,
                                                  Date rangeEnd, String calType, boolean includeRecurrence,
                                                  String personIds, String labelIds) {
        CalendarQueryContext context = new CalendarQueryContext();
        context.setRangeStart(rangeStart); //开始时间
        context.setRangeEnd(rangeEnd); //结束时间
        context.setCalType(calType); ////类型:笔记？日程？
        context.setIncludeRecurrence(includeRecurrence); //是否包含重复日程
        context.setPersonsIds(personIds); //查询人员
        context.setLabelIds(labelIds); ////查询标签
        HttpServletRequest request = Plugin.currentRequest();
        if (request != null) {
            String groupId = request.getParameter("personGroupId");
            context.setGroupId(groupId); //群组
        }

        return getRangeCalendars(context);
    }

    private HQLInfo getRangeCalendarsHQLInfo(CalendarQueryContext context) {
        String whereBlock = "";
        Calendar c = Calendar.getInstance();
        HQLInfo info = new HQLInfo();
        // 开始时间
        if (context.getRangeStart() != null) {
            Date rangeStart = context.getRangeStart();
            c.setTime(rangeStart);
            c.set(Calendar.MILLISECOND, 0);
            rangeStart = c.getTime();
            whereBlock = StringUtil.linkString(
                    "kmCalendarMain.docFinishTime >= :rangeStart", " and ",
                    whereBlock);
            info.setParameter("rangeStart", rangeStart);
        }
        // 结束时间
        if (context.getRangeEnd() != null) {
            Date rangeEnd = context.getRangeEnd();
            c.setTime(rangeEnd);
            c.set(Calendar.MILLISECOND, 0);
            rangeEnd = c.getTime();
            whereBlock = StringUtil.linkString(
                    "kmCalendarMain.docStartTime < :rangeEnd", " and ",
                    whereBlock);
            info.setParameter("rangeEnd", rangeEnd);
        }
        // String whereBlock =
        // "(kmCalendarMain.docFinishTime >= :rangeStart and kmCalendarMain.docStartTime < :rangeEnd)";
        // 查询指定来源
        if (StringUtil.isNotNull(context.getFdAppkey())) {
            info
                    .setFromBlock("KmCalendarMain kmCalendarMain, KmCalendarSyncMapping kmCalendarSyncMapping");
            whereBlock = StringUtil
                    .linkString(
                            "kmCalendarSyncMapping.fdAppKey=:fdAppKey and kmCalendarMain.fdId=kmCalendarSyncMapping.fdCalendarId",
                            " and ", whereBlock);
            info.setParameter("fdAppKey", context.getFdAppkey());
        }
        // 查询指定人员
        if (StringUtil.isNotNull(context.getPersonsIds())) {
            whereBlock = StringUtil.linkString(getPersonBlock(context.getPersonsIds()), " and ", whereBlock);
        }
        // 查询指定标签
        if (StringUtil.isNotNull(context.getLabelIds())) {
            whereBlock += getLabelBlock(context.getLabelIds(), info);
        }
        // 日程类型:笔记？日程？
        if (StringUtil.isNotNull(context.getCalType())) {
            whereBlock = StringUtil.linkString("kmCalendarMain.fdType=:fdType",
                    " and ", whereBlock);
            info.setParameter("fdType", context.getCalType());
        }
        // 是否包含重复日程
        if (!context.isIncludeRecurrence()) {
            whereBlock = StringUtil.linkString(
                    "(kmCalendarMain.fdRecurrenceStr is null or kmCalendarMain.fdRecurrenceStr='NO')", " and ",
                    whereBlock);
        }
        // 是否作权限过滤
        if (context.isIfAuth()) {
            info.setAuthCheckType(SysAuthConstant.AUTH_CHECK_READER);
        } else {
            info.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
        }
        info.setWhereBlock(whereBlock);
        // 分页
        if (context.getPageno() != null) {
            info.setPageNo(context.getPageno());
        }
        if (context.getRowsize() != null) {
            info.setRowSize(context.getRowsize());
        } else {
            info.setRowSize(Integer.MAX_VALUE);
        }
        info.setDistinctType(HQLInfo.DISTINCT_YES);
        return info;
    }


    @Override
    public List<KmCalendarMain>
    getHistoryCalendars(CalendarQueryContext context) {
        HQLInfo hqlInfo = getHistoryCalendarsHQLInfo(context);
        try {
            List<KmCalendarMain> matchedKmCalendars = findPage(hqlInfo)
                    .getList();
            return matchedKmCalendars;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<KmCalendarMain> getHistoryCalendars(Date queryDate,
                                                    String calType, boolean includeRecurrence, String personIds,
                                                    String labelIds) {
        CalendarQueryContext context = new CalendarQueryContext();
        context.setRangeStart(queryDate);
        context.setRangeEnd(null);
        context.setCalType(calType);
        context.setIncludeRecurrence(includeRecurrence);
        context.setPersonsIds(personIds);
        context.setLabelIds(labelIds);
        return getHistoryCalendars(context);
    }

    private HQLInfo getHistoryCalendarsHQLInfo(CalendarQueryContext context) {
        String whereBlock = "";
        Calendar c = Calendar.getInstance();
        HQLInfo info = new HQLInfo();
        // 开始时间
        if (context.getRangeStart() != null) {
            Date rangeStart = context.getRangeStart();
            c.setTime(rangeStart);
            c.set(Calendar.MILLISECOND, 0);
            rangeStart = c.getTime();
            whereBlock = StringUtil.linkString(
                    "kmCalendarMain.docStartTime >= :rangeStart", " and ",
                    whereBlock);
            info.setParameter("rangeStart", rangeStart);
        }
        // 结束时间
        if (context.getRangeEnd() != null) {
        }
        // 查询指定来源
        if (StringUtil.isNotNull(context.getFdAppkey())) {
            info
                    .setFromBlock(
                            "KmCalendarMain kmCalendarMain, KmCalendarSyncMapping kmCalendarSyncMapping");
            whereBlock = StringUtil
                    .linkString(
                            "kmCalendarSyncMapping.fdAppKey=:fdAppKey and kmCalendarMain.fdId=kmCalendarSyncMapping.fdCalendarId ",
                            " and ", whereBlock);
            info.setParameter("fdAppKey", context.getFdAppkey());
        }
        // 查询指定人员
        whereBlock = StringUtil.linkString(
                getPersonBlock(context.getPersonsIds()), " and ", whereBlock);
        // 查询指定标签
        if (StringUtil.isNotNull(context.getLabelIds())) {
            whereBlock += getLabelBlock(context.getLabelIds(), info);
        }
        // 日程类型:笔记？日程？
        if (StringUtil.isNotNull(context.getCalType())) {
            whereBlock = StringUtil.linkString("kmCalendarMain.fdType=:fdType",
                    " and ", whereBlock);
            info.setParameter("fdType", context.getCalType());
        }
        // 是否包含重复日程
        if (!context.isIncludeRecurrence()) {
            whereBlock = StringUtil.linkString(
                    "(kmCalendarMain.fdRecurrenceStr is null or kmCalendarMain.fdRecurrenceStr='NO')",
                    " and ",
                    whereBlock);
        }
        // 是否作权限过滤
        if (context.isIfAuth()) {
            info.setAuthCheckType(SysAuthConstant.AUTH_CHECK_READER);
        } else {
            info.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
        }
        info.setWhereBlock(whereBlock);
        // 分页
        if (context.getPageno() != null) {
            info.setPageNo(context.getPageno());
        }
        if (context.getRowsize() != null) {
            info.setRowSize(context.getRowsize());
        } else {
            info.setRowSize(Integer.MAX_VALUE);
        }
        return info;
    }

    /**
     * 获取某个时间区域内的重复日历
     */
    @Override
    public List<KmCalendarMain> getRecurrenceCalendars(
            CalendarQueryContext context) {
        Date rangeStart = context.getRangeStart();
        Date rangeEnd = context.getRangeEnd();
        Calendar c = Calendar.getInstance();
        c.setTime(rangeStart);
        c.set(Calendar.MILLISECOND, 0);
        rangeStart = c.getTime();
        c.setTime(rangeEnd);
        c.set(Calendar.MILLISECOND, 0);
        rangeEnd = c.getTime();
        HQLInfo info = new HQLInfo();
        // 查询指定时间段
        String whereBlock = "kmCalendarMain.fdType = 'event' "
                + " and (kmCalendarMain.fdRecurrenceLastEnd >= :rangeStart and kmCalendarMain.docStartTime < :rangeEnd) "
                + "and kmCalendarMain.fdRecurrenceStr is not null and kmCalendarMain.fdRecurrenceStr!='NO'";
        // 查询指定来源
        if (StringUtil.isNotNull(context.getFdAppkey())) {
            info
                    .setFromBlock("KmCalendarMain kmCalendarMain, KmCalendarSyncMapping kmCalendarSyncMapping");
            whereBlock += " and kmCalendarSyncMapping.fdAppKey=:fdAppKey and kmCalendarMain.fdId=kmCalendarSyncMapping.fdCalendarId ";
            info.setParameter("fdAppKey", context.getFdAppkey());
        }
        // 查询指定人员
        whereBlock = StringUtil.linkString(getPersonBlock(context.getPersonsIds()), " and ", whereBlock);
        // 查询指定标签
        if (StringUtil.isNotNull(context.getLabelIds())) {
            whereBlock += getLabelBlock(context.getLabelIds(), info);
        }
        info.setWhereBlock(whereBlock);
        info.setParameter("rangeStart", rangeStart);
        info.setParameter("rangeEnd", rangeEnd);
        // 是否作权限过滤
        if (context.isIfAuth()) {
            info.setAuthCheckType(SysAuthConstant.AUTH_CHECK_READER);
        }
        info.setRowSize(Integer.MAX_VALUE);
        List<KmCalendarMain> matchedKmCalendars = new ArrayList<KmCalendarMain>();
        List<KmCalendarMain> result = new ArrayList<KmCalendarMain>();
        try {
            whereBlock = info.getWhereBlock();
            // 如果群组日程是勾选了  则传true，否则传false
            if (StringUtil.isNotNull(context.getLabelIds()) && context.getLabelIds().indexOf(KmCalendarConstant.CALENDAR_MY_GROUP_EVENT) > -1) {
                //info.setParameter("fdIsGroup", Boolean.TRUE);
            } else {
                // 带权限查询普通日程
                info.setWhereBlock(StringUtil.linkString(whereBlock, " and ", "(kmCalendarMain.fdIsGroup is null or kmCalendarMain.fdIsGroup = :fdIsGroup)"));
                info.setParameter("fdIsGroup", Boolean.FALSE);
            }
            matchedKmCalendars = findPage(info).getList();
            if (StringUtil.isNotNull(context.getGroupId())) {
                // 不带权限查询群组日程
                info.setJoinBlock(", com.landray.kmss.km.calendar.model.KmCalendarMainGroup mainGroup left join mainGroup.fdMainList fdMainList left join mainGroup.fdGroup fdGroup");
                info.setWhereBlock(StringUtil.linkString(whereBlock, " and ", "kmCalendarMain.fdIsGroup = :fdIsGroup and kmCalendarMain.fdId = fdMainList.fdId and fdGroup.fdId = :groupId"));
                info.setParameter("groupId", context.getGroupId());
                info.setParameter("fdIsGroup", Boolean.TRUE);
                info.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AllCheck.NO);
                List<KmCalendarMain> calendars = findPage(info).getList();
                ArrayUtil.concatTwoList(calendars, matchedKmCalendars);
            }
            Long eventDelta;
            Date docStartTime = null;
            List<Date> excuteDateList = null;
            for (KmCalendarMain kmCalendarMain : matchedKmCalendars) {
                docStartTime = kmCalendarMain.getDocStartTime();
                eventDelta = kmCalendarMain.getDocFinishTime().getTime()
                        - docStartTime.getTime();

                String recurrenceStr = kmCalendarMain.getFdRecurrenceStr();
                Boolean isLunar = kmCalendarMain.getFdIsLunar(); //是否农历
                if (isLunar) {
                    excuteDateList = LunarRecurrenceUtil
                            .getExecueSolarDateList(docStartTime, recurrenceStr);
                    excuteDateList.add(0, docStartTime);
                } else {
                    //获取重复时间的集合
                    excuteDateList = Rfc2445Util.getExcuteDateList(
                            recurrenceStr, docStartTime, eventDelta,
                            rangeStart, rangeEnd);
                    // #66711 全天重复日程,日程多重复了一天
                    try {
                        if (StringUtil.isNotNull(recurrenceStr)) {
                            int size = excuteDateList.size();
                            int index = recurrenceStr.indexOf("UNTIL=");
                            if (size > 0 && index > -1
                                    && kmCalendarMain.getFdIsAlldayevent()) {
                                String strDate = recurrenceStr.split("UNTIL=")[1]
                                        .substring(0, 9);
                                Date endDate = DateUtil.convertStringToDate(strDate,
                                        "yyyyMMdd");
                                Date lastDate = excuteDateList.get(size - 1);
                                if (lastDate.getTime() > endDate.getTime()) {
                                    excuteDateList.remove(lastDate);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (excuteDateList != null) {
                    for (Date date : excuteDateList) {
                        Date eventStart = date;
                        Date eventEnd = new Date(eventStart.getTime()
                                + eventDelta);
                        long eventStart_long = eventStart.getTime();
                        long rangeStart_long = rangeStart.getTime();
                        long eventEnd_long = eventEnd.getTime();
                        long rangeEnd_long = rangeEnd.getTime();
                        if ((eventStart_long >= rangeStart_long && eventStart_long < rangeEnd_long)
                                || (eventEnd_long >= rangeStart.getTime() && eventEnd_long < rangeEnd_long)
                                || (eventEnd_long >= rangeStart_long && eventStart_long < rangeStart_long)) {
                            kmCalendarMain = cloneEvent(kmCalendarMain);
                            kmCalendarMain.setDocStartTime(date);
                            kmCalendarMain.setDocFinishTime(new Date(date
                                    .getTime()
                                    + eventDelta));
                            result.add(kmCalendarMain);
                        } else {
                            if (date.after(rangeEnd)) {
                                break;
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<KmCalendarMain> getRecurrenceCalendars(Date rangeStart,
                                                       Date rangeEnd, String personIds, String labelIds) {
        CalendarQueryContext context = new CalendarQueryContext();
        context.setRangeStart(rangeStart);
        context.setRangeEnd(rangeEnd);
        context.setPersonsIds(personIds);
        context.setLabelIds(labelIds);
        HttpServletRequest request = Plugin.currentRequest();
        if (request != null) {
            String groupId = request.getParameter("personGroupId");
            context.setGroupId(groupId);
        }
        return getRecurrenceCalendars(context);
    }

    private KmCalendarMain cloneEvent(KmCalendarMain kmCalendarMain) {
        KmCalendarMain newKmCalendarMain = new KmCalendarMain();
        newKmCalendarMain.setFdId(kmCalendarMain.getFdId());
        newKmCalendarMain.setDocSubject(kmCalendarMain.getDocSubject());
        newKmCalendarMain.setFdIsAlldayevent(kmCalendarMain
                .getFdIsAlldayevent());
        newKmCalendarMain.setFdIsLunar(kmCalendarMain.getFdIsLunar());
        newKmCalendarMain.setIsShared(kmCalendarMain.getIsShared());
        newKmCalendarMain.setCreatedFrom(kmCalendarMain.getCreatedFrom());
        newKmCalendarMain.setDocLabel(kmCalendarMain.getDocLabel());
        newKmCalendarMain.setDocOwner(kmCalendarMain.getDocOwner());
        newKmCalendarMain.setDocCreator(kmCalendarMain.getDocCreator());
        newKmCalendarMain.setFdLocation(kmCalendarMain.getFdLocation());
        newKmCalendarMain.setFdRelationUrl(kmCalendarMain.getFdRelationUrl());
        newKmCalendarMain.setDocCreateTime(kmCalendarMain.getDocCreateTime());
        newKmCalendarMain.setFdAuthorityType(kmCalendarMain
                .getFdAuthorityType());
        newKmCalendarMain.setFdRecurrenceStr(kmCalendarMain
                .getFdRecurrenceStr());
        //是否群组日程(前台正确展示标签类型数据) #170202
        newKmCalendarMain.setFdIsGroup(kmCalendarMain.getFdIsGroup());
        return newKmCalendarMain;

    }

    private static List<String> thirdAppKeys = null;

    public static List<String> getThirdAppKeys() throws Exception {
        if (thirdAppKeys == null) {
            thirdAppKeys = new ArrayList<String>();
            List<CMSPluginData> CMSPluginDatas = CMSPlugin.getExtensionList();
            for (CMSPluginData data : CMSPluginDatas) {
                ICMSProvider provider = data.getCmsProvider();
                if (!provider.isSynchroEnable()) {
                    continue;
                }
                thirdAppKeys.add(data.getAppKey());
            }
        }
        return thirdAppKeys;
    }

    // 定时提醒功能
    // ==============================================================================

    private ISysQuartzCoreService sysQuartzCoreService = null;

    public void setSysQuartzCoreService(
            ISysQuartzCoreService sysQuartzCoreService) {
        this.sysQuartzCoreService = sysQuartzCoreService;
    }

    private ISysNotifyRemindCommonService sysNotifyRemindCommonService;

    public ISysNotifyRemindCommonService getSysNotifyRemindCommonService() {
        return sysNotifyRemindCommonService;
    }

    public void setSysNotifyRemindCommonService(
            ISysNotifyRemindCommonService sysNotifyRemindCommonService) {
        this.sysNotifyRemindCommonService = sysNotifyRemindCommonService;
    }

    private ISysNotifyTodoService sysNotifyTodoService;

    public void setSysNotifyTodoService(
            ISysNotifyTodoService sysNotifyTodoService) {
        this.sysNotifyTodoService = sysNotifyTodoService;
    }

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        return sysNotifyMainCoreService;
    }

    public void setSysNotifyMainCoreService(
            ISysNotifyMainCoreService sysNotifyMainCoreService) {
        this.sysNotifyMainCoreService = sysNotifyMainCoreService;
    }

    private ISysNotifyRemindMainService sysNotifyRemindMainService;

    public ISysNotifyRemindMainService getSysNotifyRemindMainService() {
        return sysNotifyRemindMainService;
    }

    public void setSysNotifyRemindMainService(
            ISysNotifyRemindMainService sysNotifyRemindMainService) {
        this.sysNotifyRemindMainService = sysNotifyRemindMainService;
    }

    private List<SysOrgElement> buildNotifyTargets(
            SysNotifyRemindCommon sysNotifyRemindCommon) throws Exception {
        List<SysOrgElement> notifyTargets = new ArrayList<SysOrgElement>();
        String fdNotifyMemberIds = sysNotifyRemindCommon.getFdNotifyMemberIds();
        if (StringUtils.isNotEmpty(fdNotifyMemberIds)) {
            String[] arrayFdNotifyMemberIds = fdNotifyMemberIds.split(";");
            int length = (arrayFdNotifyMemberIds != null) ? arrayFdNotifyMemberIds.length
                    : 0;
            for (int i = 0; i < length; i++) {
                SysOrgElement findSysOrgElement = sysOrgCoreService
                        .findByPrimaryKey(arrayFdNotifyMemberIds[i]);
                if (findSysOrgElement != null) {
                    notifyTargets.add(findSysOrgElement);
                }
            }
        }
        return notifyTargets;
    }

    // 发送消息待办API方法
    private void sendFromSysNotifyRemindMain(KmCalendarMain kmCalendarMain,
                                             SysNotifyRemindMain sysNotifyRemindMain,
                                             SysNotifyRemindCommon sysNotifyRemindCommon) throws Exception {

        NotifyContext notifyContext = sysNotifyMainCoreService.getContext(null);

        // 获取通知方式
        notifyContext.setNotifyType(sysNotifyRemindMain.getFdNotifyType());
        // 设置发布类型为“待办”（默认为待阅）
        // “待办”消息发送出去后，需要到某事件发生后才变成已办，如审批通过等
        notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
        // 设置发布KEY值，为后面的删除准备
        // notifyContext.setKey(sysNotifyRemindMain.getFdKey());
        // notifyContext.setModelId(sysNotifyRemindMain.getFdModelId());
        // notifyContext.setModelName(sysNotifyRemindMain.getFdModelName());
        // 设置发布通知人
        notifyContext
                .setNotifyTarget(buildNotifyTargets(sysNotifyRemindCommon));
        notifyContext.setSubject(sysNotifyRemindCommon.getSubject());
        notifyContext.setContent(sysNotifyRemindCommon.getContent());
        notifyContext.setLink(sysNotifyRemindCommon.getLink() + "&r="
                + DateUtil.getDateTimeNumber(new Date(), new Date()));
        //notifyContext.setLink("/km/calendar");
        notifyContext.setDocCreator(kmCalendarMain.getDocCreator());
        // 标识同一个人支持多次发送待办(用于同一个人执行多次待办提醒)
        notifyContext
                .setParameter1(SysNotifyConstants.SUPPORT_MORETIMES_SEND_TODO);
        sysNotifyMainCoreService.sendNotify(kmCalendarMain, notifyContext,
                null);
    }

    // 发送消息待办入口方法
    @Override
    public void notify(SysQuartzJobContext sysQuartzJobContext)
            throws Exception {
        try {
            String paraStr = sysQuartzJobContext.getParameter();
            JSONObject jsonObject = JSONObject.fromObject(paraStr);
            CalendarNotifyJobParamBean bean = (CalendarNotifyJobParamBean) JSONObject
                    .toBean(jsonObject, CalendarNotifyJobParamBean.class);
            String recurrenceStr = bean.getRecurrenceStr();
            String remindMainId = bean.getRemindMainId();
            SysNotifyRemindMain sysNotifyRemindMain = (SysNotifyRemindMain) sysNotifyRemindMainService
                    .findSysNotifyRemindMain(remindMainId);
            if (StringUtil.isNotNull(recurrenceStr)) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.HOUR_OF_DAY, -10);
                Date temp = c.getTime();
                Date endDate = DateUtil.convertStringToDate(bean.getEndDate(),
                        DateUtil.PATTERN_DATETIME);
                Date startDate = DateUtil.convertStringToDate(bean
                        .getStartDate(), DateUtil.PATTERN_DATETIME);
                /*
                 * if (new Date().before(startDate)) { return; }
                 */
                if (temp.after(endDate)) {
                    // 删除定时任务
                    sysQuartzCoreService.delete(sysNotifyRemindMain, null);
                    return;
                } else {
                    boolean isLunar = bean.isLunar();
                    if (isLunar) {
                        sendMsg(sysNotifyRemindMain);
                        Date executeDate = DateUtil.convertStringToDate(bean
                                .getExecutedDate(), DateUtil.PATTERN_DATETIME);

                        Date next = LunarRecurrenceUtil.getNextExecueSolarDate(
                                executeDate, bean.getLunarFreq(), Integer
                                        .parseInt(bean.getLunarInterval()));

                        bean.setExecutedDate(DateUtil.convertDateToString(next,
                                DateUtil.PATTERN_DATETIME));

                        String cron = SysQuartzModelContext
                                .getCronExpression(next);

                        SysQuartzModelContext quartzContext = new SysQuartzModelContext();
                        quartzContext.setQuartzCronExpression(cron);
                        quartzContext.setQuartzJobMethod("notify");
                        quartzContext
                                .setQuartzJobService("kmCalendarMainService");
                        JSONObject object = new JSONObject();
                        quartzContext.setQuartzParameter(JSONObject
                                .fromObject(bean).toString());
                        quartzContext.setQuartzEnabled(true);
                        sysQuartzCoreService.saveScheduler(quartzContext,
                                sysNotifyRemindMain);

                        return;
                    } else {
                        Date executeDate = DateUtil.convertStringToDate(bean
                                .getExecutedDate(), DateUtil.PATTERN_DATETIME);
                        if (executeDate != null && !new Date().before(executeDate) && !KmCalendarConstant.RECURRENCE_FREQ_NO.equals(recurrenceStr)) {
                            sendMsg(sysNotifyRemindMain);
                            String quartzCronExpression = Rfc2445Util.getCronExpression(
                                    executeDate, recurrenceStr);
                            SysQuartzModelContext quartzContext = new SysQuartzModelContext();
                            String fdKey = "kmCalendar";
                            CalendarNotifyJobParamBean beanJob = new CalendarNotifyJobParamBean(
                                    sysNotifyRemindMain.getFdId(), false, DateUtil
                                    .convertDateToString(startDate,
                                            DateUtil.PATTERN_DATETIME));
                            beanJob.setRecurrenceStr(recurrenceStr);
                            try {
                                Date lastExcute = Rfc2445Util.getLastedExecuteDate(
                                        recurrenceStr, startDate);
                                beanJob.setEndDate(DateUtil.convertDateToString(lastExcute,
                                        DateUtil.PATTERN_DATETIME));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            JSONObject object = new JSONObject();
                            quartzContext.setQuartzParameter(JSONObject.fromObject(beanJob).toString());

                            quartzContext.setQuartzCronExpression(quartzCronExpression);
                            quartzContext.setQuartzKey(fdKey);

                            quartzContext.setQuartzJobMethod("notify");
                            quartzContext.setQuartzJobService("kmCalendarMainService");
                            quartzContext.setQuartzLink("");
                            if (sysNotifyRemindMain != null) {
                                String fdModelId = sysNotifyRemindMain.getFdModelId();
                                KmCalendarMain kmCalendarMain = (KmCalendarMain) findByPrimaryKey(fdModelId, null, true);
                                if (kmCalendarMain != null) {
                                    String docSubjet = "时间管理提醒：" + kmCalendarMain.getDocSubject();
                                    quartzContext.setQuartzSubject(docSubjet);
                                    quartzContext.setQuartzEnabled(true);
                                    sysQuartzCoreService.saveScheduler(quartzContext,
                                            kmCalendarMain);
                                }
                            }

                            return;
                        }
                    }
                }
            }
            sendMsg(sysNotifyRemindMain);
        } catch (Exception e) {
            sysQuartzJobContext.logError(e);
        }
    }

    private void sendMsg(SysNotifyRemindMain sysNotifyRemindMain)
            throws Exception {
        if (sysNotifyRemindMain != null) {
            String fdModelId = sysNotifyRemindMain.getFdModelId();
            String fdModelName = sysNotifyRemindMain.getFdModelName();
            SysNotifyRemindCommon sysNotifyRemindCommon = (SysNotifyRemindCommon) sysNotifyRemindCommonService
                    .findSysNotifyRemindCommon(fdModelName, fdModelId);
            KmCalendarMain kmCalendarMain = (KmCalendarMain) findByPrimaryKey(fdModelId);
            if (sysNotifyRemindCommon != null) {
                sendFromSysNotifyRemindMain(kmCalendarMain,
                        sysNotifyRemindMain, sysNotifyRemindCommon);
            }
        }
    }

    private boolean checkRemindTimeAvailable(Date eventStartTime,
                                             String fdBeforeTime, String fdTimeUnit) {
        Long executeTimeStamp = SysNotifyRemindUtil
                .getTimeInMillisByFdTimeUnit(eventStartTime.getTime(), 0,
                        Double.parseDouble(fdBeforeTime),
                        SysNotifyTypeEnum.fdTimeUnit.getList().get(fdTimeUnit),
                        0);
        if (executeTimeStamp <= new Date().getTime()) {
            return false;
        }
        return true;
    }

    /**
     * 构建CronExpression
     *
     * @param kmCalendarMain
     * @param sysNotifyRemindMainItem
     * @return 当执行时间比当前时间早的时候，返回null
     */
    private String buildCronExpression(KmCalendarMain kmCalendarMain,
                                       SysNotifyRemindMain sysNotifyRemindMainItem) {
        // Long distanceTime = remindQuartzContext.getDistanceTime();
        // Long times = remindQuartzContext.getTimes();
        Date eventStartTime = kmCalendarMain.getDocStartTime();
        String fdBeforeTime = sysNotifyRemindMainItem.getFdBeforeTime();
        String fdTimeUnit = sysNotifyRemindMainItem.getFdTimeUnit();
        String recurrenceStr = kmCalendarMain.getFdRecurrenceStr();
        String quartzCronExpression = "";
        boolean isLunar = kmCalendarMain.getFdIsLunar();
        boolean result = true;
        result = checkRemindTimeAvailable(eventStartTime, fdBeforeTime,
                fdTimeUnit);
        if (!result) {
            return null;
        }
        quartzCronExpression = SysNotifyRemindUtil.getCronExpression(
                eventStartTime, null, null, fdBeforeTime, fdTimeUnit);
        return quartzCronExpression;
    }

    public SysQuartzModelContext initSysQuartzModelContext(
            String quartzCronExpression, KmCalendarMain kmCalendarMain,
            SysNotifyRemindMain sysNotifyRemindMainItem) {
        SysQuartzModelContext quartzContext = new SysQuartzModelContext();
        String fdKey = "kmCalendar";
        Date eventStartTime = kmCalendarMain.getDocStartTime();
        String recurrenceStr = kmCalendarMain.getFdRecurrenceStr();
        CalendarNotifyJobParamBean bean = new CalendarNotifyJobParamBean(
                sysNotifyRemindMainItem.getFdId(), false, DateUtil
                .convertDateToString(eventStartTime,
                        DateUtil.PATTERN_DATETIME));
        if (StringUtil.isNotNull(recurrenceStr)
                && !KmCalendarConstant.RECURRENCE_FREQ_NO.equals(recurrenceStr)) {

            boolean isLunar = kmCalendarMain.getFdIsLunar();
            bean.setRecurrenceStr(recurrenceStr);
            if (isLunar) {
                Date lastExcute = LunarRecurrenceUtil.getLastSolarDate(
                        eventStartTime, recurrenceStr);
                Map<String, String> result = Rfc2445Util
                        .parseRecurrenceStr(recurrenceStr);
                String freq = result.get(KmCalendarConstant.RECURRENCE_FREQ);
                String interval = result
                        .get(KmCalendarConstant.RECURRENCE_INTERVAL);
                bean.setLunar(true);
                bean.setLunarFreq(freq);
                bean.setLunarInterval(interval);
                bean.setEndDate(DateUtil.convertDateToString(lastExcute,
                        DateUtil.PATTERN_DATETIME));
                try {
                    CronExpression expression = new CronExpression(
                            quartzCronExpression);
                    Date excuteTime = expression
                            .getNextValidTimeAfter(new Date());
                    bean.setExecutedDate(DateUtil.convertDateToString(
                            excuteTime, DateUtil.PATTERN_DATETIME));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    Date lastExcute = Rfc2445Util.getLastedExecuteDate(
                            recurrenceStr, eventStartTime);
                    bean.setEndDate(DateUtil.convertDateToString(lastExcute,
                            DateUtil.PATTERN_DATETIME));
                    CronExpression expression = new CronExpression(
                            quartzCronExpression);
                    Date excuteTime = expression
                            .getNextValidTimeAfter(new Date());
                    bean.setExecutedDate(DateUtil.convertDateToString(
                            excuteTime, DateUtil.PATTERN_DATETIME));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        JSONObject object = new JSONObject();
        quartzContext.setQuartzParameter(JSONObject.fromObject(bean).toString());

        quartzContext.setQuartzCronExpression(quartzCronExpression);
        quartzContext.setQuartzKey(fdKey);

        quartzContext.setQuartzJobMethod("notify");
        quartzContext.setQuartzJobService("kmCalendarMainService");
        // quartzContext.setQuartzLink(ModelUtil.getModelUrl(kmCalendarMain));
        quartzContext.setQuartzLink("");
        // String key = "sysNotify.remindMain." + fdNotifyType +
        // ".job.subject";
        // String bundle = "sys-notify";
        // String subject = ResourceUtil.getString(key, bundle);
        quartzContext.setQuartzSubject("时间管理提醒："
                + kmCalendarMain.getDocSubject());
        return quartzContext;
    }

    private void notifyRemindFailure(KmCalendarMain kmCalendarMain,
                                     SysNotifyRemindMain sysNotifyRemindMainItem) throws Exception {
        List<SysOrgElement> elements = new ArrayList<SysOrgElement>();
        elements.add(kmCalendarMain.getDocCreator());

        String beforeTime = sysNotifyRemindMainItem.getFdBeforeTime();
        String timeUnit = sysNotifyRemindMainItem.getFdTimeUnit();

        String key = "notifyRemindFailure";
        List list = sysNotifyTodoService.getCoreModels(kmCalendarMain, key);
        if (list == null || list.isEmpty()) {
            NotifyContext notifyContext = sysNotifyMainCoreService
                    .getContext(null);
            notifyContext.setKey(key);
            // 获取通知方式
            notifyContext.setNotifyType("todo");
            notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
            notifyContext.setNotifyTarget(elements);
            String errMsg1 = ResourceUtil.getString("kmCalendar.label.table.calender", "km-calendar");
            String errMsg2 = ResourceUtil.getString("kmCalendar.notify.error.msg1", "km-calendar");
            String errMsg3 = ResourceUtil.getString("kmCalendar.notify.error.msg2", "km-calendar");
            String subject = errMsg1 + " “" + kmCalendarMain.getDocSubject()
                    + "”" + errMsg2 + beforeTime
                    + SysNotifyRemindUtil.getTimeUnitName(timeUnit)
                    + errMsg3;

            notifyContext.setSubject(subject);
            notifyContext.setContent(subject);
            notifyContext.setLink("/km/calendar/index_calendar.jsp?fdId="
                    + kmCalendarMain.getFdId() + "&r="
                    + DateUtil.getDateTimeNumber(new Date(), new Date()));

            sysNotifyMainCoreService.sendNotify(kmCalendarMain, notifyContext,
                    null);
        }
    }

    public void addOrSaveScheduler(
            ISysNotifyRemindQuartzModelContext remindQuartzContext,
            KmCalendarMain kmCalendarMain, String method, CommonCal cal) throws Exception {
        String fdModelId = kmCalendarMain.getFdId();
        String fdModelName = ModelUtil.getModelClassName(kmCalendarMain);
        List sysNotifyRemindMainList = sysNotifyRemindMainService
                .getSysNotifyRemindMainList(fdModelId);
        if (sysNotifyRemindMainList != null
                && sysNotifyRemindMainList.size() > 0) {
            if ("saveScheduler".equals(method)) {
                sysQuartzCoreService.delete(kmCalendarMain, null);
            }
            for (int i = 0; i < sysNotifyRemindMainList.size(); i++) {
                SysNotifyRemindMain sysNotifyRemindMainItem = (SysNotifyRemindMain) sysNotifyRemindMainList
                        .get(i);
                //保证fdKey和fdId相同，否则更改提醒任务时，fdKey和fdId不一致会导致修改后的提醒时间定时任务执行失败
                sysNotifyRemindMainItem.setFdKey(sysNotifyRemindMainItem.getFdId());
                if (StringUtil.isNotNull(sysNotifyRemindMainItem
                        .getFdModelName())) {
                    String quartzCronExpression = buildCronExpression(
                            kmCalendarMain, sysNotifyRemindMainItem);
                    if (StringUtil.isNull(quartzCronExpression)) {
                        boolean sendNotifyFlag = true;
                        if (cal != null) {
                            sendNotifyFlag = cal.isSendNotifyFlag();
                        }
                        if (sendNotifyFlag) {
                            // 发送代办
                            notifyRemindFailure(kmCalendarMain,
                                    sysNotifyRemindMainItem);
                        } else {
                            logger.warn("是否发送待办标志为不发送待办。sendNotifyFlag："
                                    + sendNotifyFlag);
                        }
                        continue;
                    }
                    SysQuartzModelContext quartzContext = initSysQuartzModelContext(
                            quartzCronExpression, kmCalendarMain,
                            sysNotifyRemindMainItem);
                    sysQuartzCoreService.addScheduler(quartzContext,
                            kmCalendarMain);

                }
            }
            sysNotifyRemindCommonService.saveSysNotifyRemindCommon(
                    remindQuartzContext, fdModelId, fdModelName);
        } else {
            sysNotifyRemindCommonService.deleteCoreModels(kmCalendarMain);
        }

    }

    // (新增定时任务)
    public void addScheduler(
            ISysNotifyRemindQuartzModelContext remindQuartzContext,
            KmCalendarMain kmCalendarMain, CommonCal cal) throws Exception {
        addOrSaveScheduler(remindQuartzContext, kmCalendarMain, "addScheduler", cal);
    }

    // (更新定时任务)
    public void updateScheduler(
            ISysNotifyRemindQuartzModelContext remindQuartzContext,
            KmCalendarMain kmCalendarMain) throws Exception {
        addOrSaveScheduler(remindQuartzContext, kmCalendarMain, "saveScheduler", null);
    }

    // (删除定时任务)
    @Override
    public void deleteScheduler(KmCalendarMain kmCalendarMain) throws Exception {
        if (kmCalendarMain instanceof ISysNotifyRemindMainModel) {
            sysQuartzCoreService.delete(kmCalendarMain, null);
            sysNotifyRemindCommonService.deleteCoreModels(kmCalendarMain);
        }
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event == null) {
            return;
        }
        Object source = event.getSource();
        if (!(source instanceof String)) {
            return;
        }
        if (!"OAuthBind".equals(source)) {
            return;
        }
        if (event instanceof Event_Common) {
            Map params = ((Event_Common) event).getParams();
            String message = (String) params.get("message");
            String personId = (String) params.get("personId");
            String appKey = (String) params.get("appKey");
            if ("bind new account".equals(message)) {
                try {
                    // kmCalendarSyncBindService.updateSyncroDate(personId,
                    // appKey, null);
                    kmCalendarSyncBindService
                            .deleteSyncroData(personId, appKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<KmCalendarMain> getCalendarByRefererId(String refererId,
                                                       String userId)
            throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("kmCalendarMain.fdRefererId =:fdRefererId");
        if (StringUtil.isNotNull(userId)) {
            String whereblock = StringUtil.linkString(
                    "kmCalendarMain.docCreator.fdId=:userId", " and ",
                    hqlInfo.getWhereBlock());
            hqlInfo.setWhereBlock(whereblock);
            hqlInfo.setParameter("userId", userId);
        }
        hqlInfo.setParameter("fdRefererId", refererId);
        List<KmCalendarMain> calendars = findValue(hqlInfo);
        return calendars;
    }


    @Override
    public void addToread2Owner(KmCalendarMain kmCalendarMain)
            throws Exception {


        NotifyContext notifyContext = sysNotifyMainCoreService.getContext(null);

        // 获取通知方式
        notifyContext.setNotifyType("todo");
        notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
        // notifyContext
        // .setParameter1(SysNotifyConstants.SUPPORT_MORETIMES_SEND_TODO);
        List targets = new ArrayList();
        targets.add(kmCalendarMain.getDocOwner());
        notifyContext
                .setNotifyTarget(targets);
        String subject = ResourceUtil.getString(
                "km.calendar.remindOwner.subject",
                "km-calendar", UserUtil.getKMSSUser().getLocale(),
                new Object[]{kmCalendarMain.getDocCreator().getFdName(),
                        kmCalendarMain.getDocSubject()});
        notifyContext.setSubject(subject);
        notifyContext.setContent(kmCalendarMain.getDocContent());

        notifyContext.setLink("/km/calendar/index_calendar.jsp?fdId="
                + kmCalendarMain.getFdId() + "&r="
                + DateUtil.getDateTimeNumber(new Date(), new Date()));
        notifyContext.setDocCreator(kmCalendarMain.getDocCreator());
        sysNotifyMainCoreService.sendNotify(kmCalendarMain, notifyContext,
                null);

    }

    private static final int THRESHOLD = 50;

    private static final int MAX_DEAL = 100;

    //删除会议同步日程的数据
    @Override
    public void deleteImeetingCal(String modelName, String docId, String personId) throws Exception {
        KmCalendarMain calendar = findCalendar(modelName, docId, personId);
        if (calendar != null) {
            delete(calendar); //删除日程
            kmCalendarSyncMappingService.delete(modelName, docId, calendar
                    .getFdId());
        }
    }

    @Override
    public void addCalElement(CommonCal cal, List<String> ownerIdList)
            throws Exception {
        int ownerSize = ownerIdList.size();
        List<String> notifyPersonIds = cal.getSendNotifyPersonIds();
        if (ownerSize > THRESHOLD) {
            int times = (ownerSize + MAX_DEAL - 1) / MAX_DEAL;
            CMSThreadPoolManager manager = CMSThreadPoolManager.getInstance();
            manager.setTHREAD_POOL_SIZE(times);
            manager.start();
            for (int i = 0; i < times; i++) {
                List<String> subList = ownerIdList.subList(i * MAX_DEAL,
                        i == times - 1 ? ownerIdList.size()
                                : (i + 1) * MAX_DEAL);
                int subOwnerSize = subList.size();
                final CountDownLatch latch = new CountDownLatch(subOwnerSize);
                for (int j = 0; j < subOwnerSize; j++) {
                    String personId = subList.get(j);
                    // 日程提醒 都过期 要通知人是否为空
                    if (notifyPersonIds != null && !notifyPersonIds.isEmpty()) {
                        if (notifyPersonIds.contains(personId)) {
                            cal.setSendNotifyFlag(true);// 给通知人生成日程并发送失败提醒
                        } else {
                            cal.setSendNotifyFlag(false);
                        }
                    } else {// 存在未过期提醒，只给未过期提醒创建提醒
                        cal.setSendNotifyFlag(false);
                    }
                    cal.setPersonId(personId);
                    final CommonCal _cal = (CommonCal) ModelUtil.clone(cal);
                    manager.submit(new Runnable() {
                        @Override
                        public void run() {
                            TransactionStatus status = TransactionUtils
                                    .beginTransaction();
                            try {
                                addCalElement(_cal);
                                TransactionUtils.getTransactionManager()
                                        .commit(status);
                            } catch (Exception e) {
                                TransactionUtils.getTransactionManager()
                                        .rollback(status);
                                e.printStackTrace();
                            } finally {
                                latch.countDown();
                            }
                        }
                    });
                }
                latch.await();
            }
            manager.shutdown();
        } else {
            for (String personId : ownerIdList) {
                // 日程提醒 都过期 要通知人是否为空
                if (notifyPersonIds != null && !notifyPersonIds.isEmpty()) {
                    if (notifyPersonIds.contains(personId)) {
                        cal.setSendNotifyFlag(true);// 给通知人生成日程并发送失败提醒
                    } else {
                        cal.setSendNotifyFlag(false);
                    }
                } else {// 存在未过期提醒，只给未过期提醒创建提醒
                    cal.setSendNotifyFlag(false);
                }
                cal.setPersonId(personId);
                addCalElement(cal);
            }
        }
    }

    /**
     * 同步会议到日程的新逻辑
     *
     * @param cal
     * @param ownerIdList 会议同步到日程的相关人员
     * @throws Exception
     */
    @Override
    public void addImeetingCalElement(CommonCal cal, List<String> ownerIdList)
            throws Exception {
        int ownerSize = ownerIdList.size(); //获取会议相关人的数量
        KmCalendarMain kmCalendarMain = new KmCalendarMain();
        kmCalendarMain.setFdId(IDGenerator.generateID());
        kmCalendarMain.setDocCreateTime(new Date());
        ArrayList relatedPersonList = new ArrayList();
        if (!ArrayUtil.isEmpty(ownerIdList)) {
            for (String ownerId : ownerIdList) {
                relatedPersonList.add(sysOrgPersonService.findByPrimaryKey(ownerId));
            }
            kmCalendarMain.setFdRelatedPersons(relatedPersonList);
        }
        //初始化日程相关信息
        updateKmCalendarMain(cal, kmCalendarMain);
        // 初始化同步日程权限
        initAuthData(kmCalendarMain);
        //添加相关人到可阅读者的表
        addAuthData(kmCalendarMain);
        add(kmCalendarMain, cal);
        //初始化日程映射表
        KmCalendarSyncMapping mapping = new KmCalendarSyncMapping();
        mapping.setFdAppKey(cal.getModelName());
        mapping.setFdAppUuid(cal.getDocId());
        mapping.setFdCalendarId(kmCalendarMain.getFdId());
        mapping.setFdId(IDGenerator.generateID());
        kmCalendarSyncMappingService.add(mapping);

    }

    @Override
    public void updateCalElement(CommonCal cal, List<String> ownerIdList)
            throws Exception {
        int ownerSize = ownerIdList.size();
        if (ownerSize > THRESHOLD) {
            int times = (ownerSize + MAX_DEAL - 1) / MAX_DEAL;
            CMSThreadPoolManager manager = CMSThreadPoolManager.getInstance();
            manager.setTHREAD_POOL_SIZE(times);
            manager.start();
            for (int i = 0; i < times; i++) {
                List<String> subList = ownerIdList.subList(i * MAX_DEAL,
                        i == times - 1 ? ownerIdList.size()
                                : (i + 1) * MAX_DEAL);
                int subOwnerSize = subList.size();
                final CountDownLatch latch = new CountDownLatch(subOwnerSize);
                for (int j = 0; j < subOwnerSize; j++) {
                    String personId = subList.get(j);
                    cal.setPersonId(personId);
                    final CommonCal _cal = (CommonCal) ModelUtil.clone(cal);
                    manager.submit(new Runnable() {
                        @Override
                        public void run() {
                            TransactionStatus status = TransactionUtils
                                    .beginTransaction();
                            try {
                                updateCalElement(_cal);
                                TransactionUtils.getTransactionManager()
                                        .commit(status);
                            } catch (Exception e) {
                                TransactionUtils.getTransactionManager()
                                        .rollback(status);
                                e.printStackTrace();
                            } finally {
                                latch.countDown();
                            }
                        }
                    });
                }
                latch.await();
            }
            manager.shutdown();
        } else {
            for (String personId : ownerIdList) {
                cal.setPersonId(personId);
                updateCalElement(cal);
            }
        }
    }

    @Override
    public void deleteCalElement(String modelName, String docId,
                                 List<String> ownerIdList) throws Exception {
        if (ownerIdList.size() > THRESHOLD) {
            int times = (ownerIdList.size() + MAX_DEAL - 1) / MAX_DEAL;
            CMSThreadPoolManager manager = CMSThreadPoolManager.getInstance();
            manager.setTHREAD_POOL_SIZE(times);
            manager.start();
            for (int i = 0; i < times; i++) {
                List<String> subList = ownerIdList.subList(i * MAX_DEAL,
                        i == times - 1 ? ownerIdList.size()
                                : (i + 1) * MAX_DEAL);
                manager.submit(new SyncDataToCalendarThread(modelName, docId,
                        subList, "delete"));
            }
            manager.shutdown();
        } else {
            for (String personId : ownerIdList) {
                deleteCalElement(modelName, docId, personId);
            }
        }
    }

    @Override
    public List<KmCalendarMain> findCalendars(String modelName, String docId,
                                              List<String> ownerIdList) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo
                .setFromBlock(
                        "KmCalendarMain kmCalendarMain, KmCalendarSyncMapping kmCalendarSyncMapping");
        hqlInfo
                .setWhereBlock(
                        "kmCalendarMain.fdId=kmCalendarSyncMapping.fdCalendarId and kmCalendarSyncMapping.fdAppKey=:fdAppKey and kmCalendarSyncMapping.fdAppUuid=:fdAppUuid and kmCalendarMain.docOwner.fdId in (:ownerIdList)");
        hqlInfo.setParameter("fdAppKey", modelName);
        hqlInfo.setParameter("fdAppUuid", docId);
        hqlInfo.setParameter("ownerIdList", ownerIdList);
        List<KmCalendarMain> list = (List<KmCalendarMain>) findList(hqlInfo);
        return list;
    }

    @Override
    public void updateGroupEvent(KmCalendarMainForm kmCalendarMainForm,
                                 KmCalendarMainGroup mainGroup, RequestContext requestContext)
            throws Exception {
        List<KmCalendarMain> mainModelList = mainGroup.getFdMainList();
        for (KmCalendarMain mainModel : mainModelList) {
            kmCalendarMainForm.setFdId(mainModel.getFdId());
            kmCalendarMainForm.setDocOwnerId(mainModel.getDocOwner().getFdId());
            update(kmCalendarMainForm, requestContext);
        }
    }

    @Override
    public void deletePersonGroupEvent(
            KmCalendarPersonGroup kmCalendarPersonGroup,
            List<SysOrgElement> ownerList) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setModelName(KmCalendarMainGroup.class.getName());
        hqlInfo.setWhereBlock("kmCalendarMainGroup.fdGroup=:fdGroup");
        hqlInfo.setParameter("fdGroup", kmCalendarPersonGroup);
        List<KmCalendarMainGroup> mainGroupList = kmCalendarMainGroupService
                .findList(hqlInfo);
        for (KmCalendarMainGroup mainGroup : mainGroupList) {
            List<KmCalendarMain> mainModelList = mainGroup.getFdMainList();
            for (KmCalendarMain mainModel : mainModelList) {
                if (mainModel.getDocStartTime().after(new Date())
                        && ownerList.contains(mainModel.getDocOwner())) {
                    delete(mainModel);
                }
            }
        }
    }

    @Override
    public JSONArray getEventsByRange(RequestContext request) throws Exception {
        JSONArray datas = new JSONArray();
        String type = request.getParameter("type");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String personsId = request.getParameter("personsId");
        if (StringUtil.isNull(personsId)) {
            personsId = UserUtil.getUser().getFdId();
        }
        Date rangeStart = null;
        if (StringUtil.isNotNull(startTime)) {
            if (request.isCloud()) {
                rangeStart = new Date(Long.parseLong(startTime));
            } else {
                rangeStart = DateUtil.convertStringToDate(startTime,
                        DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
            }
        } else {
            rangeStart = DateUtil.getBeginDayOfMonth();
        }
        Date rangeEnd = null;
        if (StringUtil.isNotNull(endTime)) {
            if (request.isCloud()) {
                rangeEnd = new Date(Long.parseLong(endTime));
            } else {
                rangeEnd = DateUtil.convertStringToDate(endTime,
                        DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
            }
        } else {
            rangeEnd = DateUtil.getEndDayOfMonth();
        }
        String[] eventsOfEverDay = new String[getDayRange(rangeStart,
                rangeEnd) + 1];
        List<KmCalendarMain> calendars = new ArrayList<KmCalendarMain>();// 全部日程
        List<KmCalendarMain> matchedKmCalendars = null;
        if ("all".equals(type)) {
            matchedKmCalendars = getRangeCalendars(rangeStart, rangeEnd,
                    null, false,
                    personsId, null);// 非重复日程
        } else {
            matchedKmCalendars = getRangeCalendars(rangeStart, rangeEnd,
                    KmCalendarConstant.CALENDAR_TYPE_EVENT, false,
                    personsId, null);// 非重复日程
        }
        if (matchedKmCalendars != null && !matchedKmCalendars.isEmpty()) {
            calendars.addAll(matchedKmCalendars);
        }
        List<KmCalendarMain> recurrenceCalendars = getRecurrenceCalendars(
                rangeStart, rangeEnd,
                personsId, null);
        if (recurrenceCalendars != null
                && !recurrenceCalendars.isEmpty()) {
            calendars.addAll(recurrenceCalendars);
        }
        for (KmCalendarMain calendar : calendars) {
            Date startTmp = rangeStart.getTime() > calendar
                    .getDocStartTime().getTime() ? rangeStart
                    : calendar.getDocStartTime();
            Date endTmp = rangeEnd.getTime() < calendar
                    .getDocFinishTime().getTime() ? rangeEnd : calendar
                    .getDocFinishTime();
            for (int j = getDayRange(rangeStart,
                    startTmp), i = 0; i <= getDayRange(
                    startTmp, endTmp); j++, i++) {
                eventsOfEverDay[j] = "1";
            }
        }
        for (int i = 0; i < eventsOfEverDay.length; i++) {
            if ("1".equals(eventsOfEverDay[i])) {
                datas.add(true);
            } else {
                datas.add(false);
            }
        }
        return datas;
    }

    /**
     * 计算两个日期之间相差的天数
     */
    private int getDayRange(Date start, Date end) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTERN_DATE);
        start = sdf.parse(sdf.format(start));
        end = sdf.parse(sdf.format(end));
        Calendar c = Calendar.getInstance();
        c.setTime(start);
        long time1 = c.getTimeInMillis();
        c.setTime(end);
        long time2 = c.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    @Override
    public JSONArray data(RequestContext request) throws Exception {
        JSONArray datas = new JSONArray();
        String startTime = request.getParameter("fdStart"); // 开始时间
        String endTime = request.getParameter("fdEnd"); // 结束时间
        String labelIds = request.getParameter("labelIds");
        String subject = request.getParameter("subject");
        String calType = null;
        Date docStartTime = new Date();
        Date docFinishTime = new Date();
        if (StringUtil.isNotNull(startTime)) {
            if (request.isCloud()) {
                docStartTime = new Date(Long.parseLong(startTime));
            } else {
                docStartTime = DateUtil.convertStringToDate(startTime,
                        DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
            }
        } else {
            docStartTime = DateUtil.getBeginDayOfMonth();
        }
        if (StringUtil.isNotNull(endTime)) {
            if (request.isCloud()) {
                docFinishTime = new Date(Long.parseLong(endTime));
            } else {
                docFinishTime = DateUtil.convertStringToDate(endTime,
                        DateUtil.TYPE_DATE, UserUtil.getKMSSUser().getLocale());
            }
        } else {
            docFinishTime = DateUtil.getEndDayOfMonth();
        }
        if (StringUtil.isNull(labelIds)) {
            // 如果没有指定查询的标签，需要过滤不显示的标签
            labelIds = buildLabelIds();
        }

        // 非重复日程
        List<KmCalendarMain> kmCalendars = getRangeCalendars(docStartTime,
                docFinishTime, calType,
                false, UserUtil.getUser().getFdId(), labelIds);

        // 重复日程
        List<KmCalendarMain> recurrenceCalendars = getRecurrenceCalendars(
                docStartTime, docFinishTime,
                UserUtil.getUser().getFdId(), labelIds);

        kmCalendars.addAll(recurrenceCalendars);
        // 记录日志
        UserOperHelper.logFindAll(kmCalendars, getModelName());
        UserOperHelper.setOperSuccess(true);
        // 过滤数据AI
        if (StringUtil.isNotNull(subject)) {
            List<KmCalendarMain> allCalendar = new ArrayList<KmCalendarMain>();
            for (KmCalendarMain main : kmCalendars) {
                if (StringUtil.isNotNull(main.getDocSubject())
                        && main.getDocSubject().indexOf(subject) > -1) {
                    allCalendar.add(main);
                }
            }
            kmCalendars = allCalendar;
        }
        Collections.sort(kmCalendars, new CalendarComparator());
        int index = kmCalendars.size();
        for (KmCalendarMain kmCalendarMain : kmCalendars) {
            JSONObject data = genCalendarData(kmCalendarMain, request);
            if (StringUtil.isNull(kmCalendarMain.getFdRecurrenceStr())
                    || "NO".equals(kmCalendarMain.getFdRecurrenceStr())) {
                data.put("isRecurrence", false);
            } else {
                data.put("isRecurrence", true);
            }
            data.put("priority", index--);
            datas.add(data);
        }
        return datas;
    }

    /**
     * 构建选中标签ids
     */
    private String buildLabelIds() throws Exception {
        String labelIds = null;
        List<KmCalendarLabel> labels = getKmCalendarLabelService()
                .getLabelsByPerson(UserUtil.getUser().getFdId());
        List<String> labelIdlist = new ArrayList<String>();
        //三固定标签
        labelIdlist.add(KmCalendarConstant.CALENDAR_MY_EVENT);
        labelIdlist.add(KmCalendarConstant.CALENDAR_MY_NOTE);
        labelIdlist.add(KmCalendarConstant.CALENDAR_MY_GROUP_EVENT);
        if (labels != null && labels.size() > 0) {
            for (KmCalendarLabel label : labels) {
                //如果通用标签，且不选中要移除该固定标签（历史原因，必须加上以上三个固定标签）-fdName(eg.myEvent)
                if (StringUtil.isNotNull(label.getFdCommonFlag()) && label.getFdCommonFlag().startsWith("1")
                        && (!BooleanUtils.isTrue(label.getFdSelectedFlag()))) {
                    String[] split = label.getFdCommonFlag().split("[|]");
                    if (split.length > 1) {
                        labelIdlist.remove(split[1]);
                    }
                }
                //如果自定义标签，不选中-跳过(空默认选中)
                else {
                    if (label.getFdSelectedFlag() != null && !BooleanUtils.isTrue(label.getFdSelectedFlag())) {
                        continue;
                    }
                    labelIdlist.add(label.getFdId());
                }

            }
        }
        if (labelIdlist.size() > 0) {
            labelIds = "";
            for (String labelId : labelIdlist) {
                labelIds += labelId + ",";
            }
        } else {
            labelIds = KmCalendarConstant.CALENDAR_NO_EVENT;
        }
        return labelIds;
    }

    /**
     * 日程对象转为JSON
     */
    private JSONObject genCalendarData(KmCalendarMain kmCalendarMain,
                                       RequestContext request)
            throws Exception {
        JSONObject data = new JSONObject();
        String viewUrl = "";
        Date docStartTime = kmCalendarMain.getDocStartTime();
        Date docEndTime = kmCalendarMain.getDocFinishTime();
        long startTimeNum = docStartTime.getTime();
        long endTimeNum = docEndTime.getTime();
        if (startTimeNum == endTimeNum) {
            endTimeNum = endTimeNum + (60 * 1000);
            data.put("isChangeEndTime", "true");
        } else {
            data.put("isChangeEndTime", "false");
        }
        docStartTime = DateUtil.getCalendar(startTimeNum).getTime();
        docEndTime = DateUtil.getCalendar(endTimeNum).getTime();
        if (request.isCloud()) {
            viewUrl = "/km/calendar/";
            data.put("text", kmCalendarMain.getDocSubject());
            data.put("startDate", docStartTime.getTime());
            if (kmCalendarMain.getDocFinishTime() != null) {
                data.put("endDate",
                        docEndTime.getTime());
            }
            data.put("creator",
                    ListDataUtil.buildCreator(kmCalendarMain.getDocOwner()));
            KmCalendarLabel label = kmCalendarMain.getDocLabel();
            if (label != null) {
                String cateName = label.getFdName();
                if (KmCalendarConstant.CALENDAR_MY_EVENT.equals(cateName)) {
                    cateName = ResourceUtil.getString("km-calendar:kmCalendar.nav.title");
                } else if (KmCalendarConstant.CALENDAR_MY_GROUP_EVENT.equals(cateName)) {
                    cateName = ResourceUtil.getString("km-calendar:kmCalendarMain.group.header.title");
                } else if (KmCalendarConstant.CALENDAR_MY_NOTE.equals(cateName)) {
                    cateName = ResourceUtil.getString("km-calendar:module.km.calendar.tree.my.note");
                }
                data.put("cateName", cateName);
                data.put("cateColor", label.getFdColor());
            }
        } else {
            viewUrl = "/km/calendar/km_calendar_main/kmCalendarMain.do?method=view&fdId="
                    + kmCalendarMain.getFdId();
            data.put("id", kmCalendarMain.getFdId());
            data.put("title", kmCalendarMain.getDocSubject());
            String type = DateUtil.TYPE_DATETIME;
            Boolean isAlldayevent = kmCalendarMain.getFdIsAlldayevent();
            if (isAlldayevent == null || isAlldayevent) {
                type = DateUtil.TYPE_DATE;
            }
            String statDate = DateUtil.convertDateToString(
                    docStartTime, type, null);
            data.put("start", statDate);
            if (kmCalendarMain.getDocFinishTime() != null) {
                String endDate = DateUtil.convertDateToString(
                        docEndTime, type, null);
                data.put("end", endDate);
            }
            KmCalendarLabel kmCalendarLabel = kmCalendarMain.getDocLabel();
            if (kmCalendarLabel != null) {
                data.put("labelId", kmCalendarMain.getDocLabel().getFdId());

                // 获取模块名称
                SysDictModel sysDict = SysDataDict.getInstance().getModel(kmCalendarMain.getDocLabel().getFdModelName());
                String labelName = (sysDict == null ? kmCalendarMain.getDocLabel().getFdName() : ResourceUtil.getString(sysDict.getMessageKey()));
                if (KmCalendarConstant.CALENDAR_MY_EVENT.equals(labelName)) {
                    labelName = ResourceUtil.getString("km-calendar:kmCalendar.nav.title");
                } else if (KmCalendarConstant.CALENDAR_MY_GROUP_EVENT.equals(labelName)) {
                    labelName = ResourceUtil.getString("km-calendar:kmCalendarMain.group.header.title");
                } else if (KmCalendarConstant.CALENDAR_MY_NOTE.equals(labelName)) {
                    labelName = ResourceUtil.getString("km-calendar:module.km.calendar.tree.my.note");
                }
                data.put("labelName", labelName);
                data.put("color", kmCalendarMain.getDocLabel().getFdColor());
            } else {
                data.put("labelId", "myEvent");
                String labelName = ResourceUtil.getString("module.km.calendar.tree.my.calendar", "km-calendar");
                if ("note".equalsIgnoreCase(kmCalendarMain.getFdType())) {
                    labelName = ResourceUtil.getString("module.km.calendar.tree.my.note", "km-calendar");
                }
                data.put("labelName", labelName);
                data.put("color", "rgb(193, 156, 83)");
            }
            if (kmCalendarMain.getDocContent() != null) {
                data.put("content", kmCalendarMain.getDocContent());
            }
            data.put("type", kmCalendarMain.getFdType());
            // 头像
            if (kmCalendarMain.getDocOwner() != null) {
                String img = PersonInfoServiceGetter
                        .getPersonHeadimageUrl(kmCalendarMain.getDocOwner()
                                .getFdId());
                if (!PersonInfoServiceGetter.isFullPath(img)) {
                    img = getContextPath() + img;
                }
                data.put("img", img);
                data.put("owner", kmCalendarMain.getDocOwner().getFdName());
                if (kmCalendarMain.getDocCreator() != kmCalendarMain
                        .getDocOwner()) {
                    data.put("ownerId", kmCalendarMain.getDocOwner().getFdId());
                } else {
                    data.put("ownerId", kmCalendarMain.getDocOwner().getFdId());
                }
            }
            Map<String, Boolean> map = getAuth(kmCalendarMain.getDocOwner(),
                    UserUtil.getUser());
            data.put("canRead", map.get("canRead"));
            data.put("canEditor", map.get("canEditor"));
            data.put("canModifier", map.get("canModifier"));
            // 判断是否为群组日程
            Boolean fdIsGroup = kmCalendarMain.getFdIsGroup();
            if (fdIsGroup != null) {
                if (fdIsGroup.booleanValue()) {
                    data.put("labelId", "myGroupEvent");
                    data.put("isGroup", kmCalendarMain.getFdIsGroup());
                    data.put("color", "#00ccff");
                    KmCalendarMainGroup mainGroupId = (KmCalendarMainGroup) kmCalendarMainGroupService
                            .findMainGroupByMainId(kmCalendarMain.getFdId());
                    StringBuffer sb = new StringBuffer("");
                    List<KmCalendarMain> calendars = new ArrayList<KmCalendarMain>();
                    String groupId = "";
                    if (mainGroupId != null) {
                        groupId = mainGroupId.getFdId();
                        calendars = mainGroupId.getFdMainList();
                    }
                    data.put("mainGroupId", groupId);

                    for (KmCalendarMain cal : calendars) {
                        sb.append(cal.getDocOwner().getFdName() + ";");
                    }
                    String personNames = "";
                    if (sb.length() > 0) {
                        personNames = sb.substring(0, sb.length() - 1);
                    }
                    data.put("personNames", personNames);
                }
            }
            // 判断日程是否设置了提醒
            try {
                List sysNotifyRemindMainList = getSysNotifyRemindMainService()
                        .getCoreModels(kmCalendarMain, null);
                if (sysNotifyRemindMainList != null
                        && !sysNotifyRemindMainList.isEmpty()) {
                    data.put("hasSettedRemind", "true");
                } else {
                    data.put("hasSettedRemind", "false");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String relationUrl = kmCalendarMain.getFdRelationUrl();
            if (StringUtil.isNotNull(relationUrl)) {
                data.put("relationUrl", relationUrl);
            }
            String fdLocation = kmCalendarMain.getFdLocation();
            if (StringUtil.isNotNull(fdLocation)) {
                data.put("fdLocation", fdLocation);
            }
            String fdAuthorityType = kmCalendarMain.getFdAuthorityType();
            if (StringUtil.isNotNull(fdAuthorityType)) {
                data.put("isPrivate", fdAuthorityType
                        .equals(KmCalendarConstant.AUTHORITY_TYPE_PRIVATE));
            }
        }
        data.put("allDay", kmCalendarMain.getFdIsAlldayevent());
        // 有关联URL跳到关联url
        String href = StringUtil.isNotNull(kmCalendarMain.getFdRelationUrl())
                ? kmCalendarMain.getFdRelationUrl()
                : viewUrl;
        data.put("href", href);
        return data;
    }

    private Map<String, Boolean> getAuth(SysOrgElement person,
                                         SysOrgPerson curUser) throws Exception {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        if (person.equals(curUser)) {
            map.put("canRead", true);
            map.put("canEditor", true);
            map.put("canModifier", true);
        } else {
            KmCalendarAuth auth = kmCalendarAuthService
                    .findByPerson(person.getFdId());
            if (auth != null) {
                List<SysOrgElement> authReaders = sysOrgCoreService
                        .expandToPerson(auth.getAuthReaders());
                map.put("canRead", authReaders.contains(curUser));
                List<SysOrgElement> authEditors = sysOrgCoreService
                        .expandToPerson(auth.getAuthEditors());
                map.put("canEditor", authEditors.contains(curUser));
                List<SysOrgElement> authModifiers = sysOrgCoreService
                        .expandToPerson(auth.getAuthModifiers());
                map.put("canModifier", authModifiers.contains(curUser));
            }
        }
        return map;
    }

    private class CalendarComparator implements Comparator<KmCalendarMain> {
        @Override
        public int compare(KmCalendarMain o1, KmCalendarMain o2) {
            int result = 0;
            if (o1.getDocStartTime().getTime() > o2.getDocStartTime()
                    .getTime()) {
                result = 1;
            } else if (o1.getDocStartTime().getTime() < o2.getDocStartTime()
                    .getTime()) {
                result = -1;
            } else {
                if (o1.getDocCreateTime().getTime() > o2.getDocCreateTime()
                        .getTime()) {
                    result = 1;
                } else if (o1.getDocCreateTime().getTime() < o2
                        .getDocCreateTime().getTime()) {
                    result = -1;
                }
            }
            return result;
        }
    }

    @Override
    public JSONObject getKKConfig() throws Exception {
        JSONObject json = new JSONObject();
        boolean enableTransfer = false;
        String appCode = "";
        try {
            Object service = SpringBeanUtil
                    .getBean("kkImConfigService");
            Class<?> clz = service.getClass();
            Method method = clz.getMethod("getValuebyKey",
                    new Class[]{String.class});
            String value = (String) method.invoke(service, "imTransferEnable");
            enableTransfer = StringUtil.isNull(value) || "true".equals(value);
            // appcode
            method = clz.getMethod("getAppCode",
                    new Class[]{String.class});
            appCode = (String) method.invoke(service, "km/calendar");
        } catch (Exception e) {
            e.printStackTrace();
        }
        json.put("enableTransfer", enableTransfer);
        json.put("appCode", appCode == null ? "" : appCode);
        return json;
    }

    @Override
    public JSONObject checkRemind(String fdId) {
        JSONObject json = new JSONObject();
        try {
            KmCalendarMain main = (KmCalendarMain) findByPrimaryKey(fdId);
            if (main != null) {
                StringBuffer msg = new StringBuffer();
                String fdModelId = main.getFdId();
                List<SysNotifyRemindMain> remindMainList = sysNotifyRemindMainService.getSysNotifyRemindMainList(fdModelId);
                if (CollectionUtils.isNotEmpty(remindMainList)) {
                    for (SysNotifyRemindMain remindMain : remindMainList) {
                        if (StringUtil.isNotNull(remindMain.getFdModelName())) {
                            String quartzCronExpression = buildCronExpression(main, remindMain);
                            if (StringUtil.isNull(quartzCronExpression)) {
                                // 检查不通过
                                msg.append("<li>")
                                        .append(ResourceUtil.getString("km-calendar:kmCalendarNotifyRemaindMain.early"))
                                        .append(remindMain.getFdBeforeTime())
                                        .append(ResourceUtil.getString("km-calendar:" + remindMain.getFdTimeUnit()))
                                        .append("</li>");

                                continue;
                            }
                        }
                    }
                }
                if (msg.length() > 0) {
                    json.put("success", false);
                    json.put("msg", "以下提醒设置早于当前时间，请修改后重新提交：<br><ol style='text-align: left;list-style: decimal;'>" + msg
                            + "</ol>");
                } else {
                    json.put("success", true);
                }
            } else {
                json.put("success", false);
                json.put("msg", "检查失败：无法获取日程信息！");
            }
        } catch (Exception e) {
            json.put("success", false);
            json.put("msg", "检查失败：" + e.getMessage());
        }
        return json;
    }

    @Override
    public List<KmCalendarMain> getGroupCalendars(Date rangeStart, Date rangeEnd, String personGroupId)
            throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setModelName(KmCalendarPersonGroup.class.getName());
        hqlInfo.setSelectBlock("count(kmCalendarPersonGroup.fdId)");
        hqlInfo.setJoinBlock(" left join kmCalendarPersonGroup.authReaders readers left join kmCalendarPersonGroup.authEditors editors");
        hqlInfo.setWhereBlock(" (readers.fdId in (:orgIds) or editors.fdId in (:orgIds)) and kmCalendarPersonGroup.fdId = :fdId");
        hqlInfo.setParameter("fdId", personGroupId);
        hqlInfo.setParameter("orgIds", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
        List<Object> value = findValue(hqlInfo);
        if (Integer.parseInt(value.get(0).toString()) > 0) {
            String whereBlock = null;
            // 该用户是阅读者或维护者
            HQLInfo info = new HQLInfo();
            info.setModelName(KmCalendarMainGroup.class.getName());
            info.setSelectBlock("fdMainList");
            info.setJoinBlock(" left join kmCalendarMainGroup.fdGroup fdGroup left join kmCalendarMainGroup.fdMainList fdMainList");
            whereBlock = "fdGroup.fdId = :groupId";
            info.setParameter("groupId", personGroupId);

            Calendar c = Calendar.getInstance();
            // 开始时间
            if (rangeStart != null) {
                c.setTime(rangeStart);
                c.set(Calendar.MILLISECOND, 0);
                rangeStart = c.getTime();
                whereBlock = StringUtil.linkString("fdMainList.docFinishTime >= :rangeStart", " and ", whereBlock);
                info.setParameter("rangeStart", rangeStart);
            }
            // 结束时间
            if (rangeEnd != null) {
                c.setTime(rangeEnd);
                c.set(Calendar.MILLISECOND, 0);
                rangeEnd = c.getTime();
                whereBlock = StringUtil.linkString("fdMainList.docStartTime < :rangeEnd", " and ", whereBlock);
                info.setParameter("rangeEnd", rangeEnd);
            }
            info.setWhereBlock(whereBlock);
            return findList(info);
        }
        return null;
    }
}
