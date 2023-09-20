package com.landray.kmss.km.calendar.webservice;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.km.calendar.constant.KmCalendarConstant;
import com.landray.kmss.km.calendar.model.KmCalendarAuth;
import com.landray.kmss.km.calendar.model.KmCalendarDetails;
import com.landray.kmss.km.calendar.model.KmCalendarLabel;
import com.landray.kmss.km.calendar.model.KmCalendarMain;
import com.landray.kmss.km.calendar.model.KmCalendarSyncMapping;
import com.landray.kmss.km.calendar.service.IKmCalendarAuthService;
import com.landray.kmss.km.calendar.service.IKmCalendarDetailsService;
import com.landray.kmss.km.calendar.service.IKmCalendarLabelService;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.km.calendar.service.IKmCalendarSyncMappingService;
import com.landray.kmss.km.calendar.util.CalendarQueryContext;
import com.landray.kmss.km.calendar.util.Rfc2445Util;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.notify.model.SysNotifyRemindMain;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.webservice2.forms.AttachmentForm;
import com.landray.kmss.sys.webservice2.interfaces.ISysWsAttService;
import com.landray.kmss.sys.webservice2.interfaces.ISysWsOrgService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.Runner;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.annotation.RestApi;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RestApi(docUrl = "/km/calendar/restservice/kmCalendarWebServiceHelp.jsp", name = "kmCalendarRestService", resourceKey = "km-calendar:kmCalendarRestService.title")
@RequestMapping(value = "/api/km-calendar/kmCalendarRestService", method = RequestMethod.POST)
public class KmCalendarWebserviceServiceImpl implements
        IKmCalendarWebserviceService, KmCalendarWebServiceConstant,
        SysOrgConstant {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(KmCalendarWebserviceServiceImpl.class);

    private ISysWsOrgService sysWsOrgService;

    private ISysOrgCoreService sysOrgCoreService;

    private ISysWsAttService sysWsAttService;

    private IKmCalendarMainService kmCalendarMainService;// 日程服务

    private IBackgroundAuthService backgroundAuthService;

    private IKmCalendarSyncMappingService kmCalendarSyncMappingService;// 同步服务

    private IKmCalendarLabelService kmCalendarLabelService;// 标签服务

    public void setSysWsOrgService(ISysWsOrgService sysWsOrgService) {
        this.sysWsOrgService = sysWsOrgService;
    }

    public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
        this.sysOrgCoreService = sysOrgCoreService;
    }

    public void setSysWsAttService(ISysWsAttService sysWsAttService) {
        this.sysWsAttService = sysWsAttService;
    }

    public void setKmCalendarMainService(
            IKmCalendarMainService kmCalendarMainService) {
        this.kmCalendarMainService = kmCalendarMainService;
    }

    public void setBackgroundAuthService(
            IBackgroundAuthService backgroundAuthService) {
        this.backgroundAuthService = backgroundAuthService;
    }

    public void setKmCalendarSyncMappingService(
            IKmCalendarSyncMappingService kmCalendarSyncMappingService) {
        this.kmCalendarSyncMappingService = kmCalendarSyncMappingService;
    }

    public void setKmCalendarLabelService(
            IKmCalendarLabelService kmCalendarLabelService) {
        this.kmCalendarLabelService = kmCalendarLabelService;
    }

    private IKmCalendarDetailsService kmCalendarDetailsService;

    public void setKmCalendarDetailsService(IKmCalendarDetailsService kmCalendarDetailsService) {
        this.kmCalendarDetailsService = kmCalendarDetailsService;
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/addCalendar")
    public KmCalendarResult addCalendar(
            @ModelAttribute KmCalendarParamterForm kmCalendarParamterForm)
            throws Exception {
        // 切换当前用户
        SysOrgElement creator = null;
        try {
            creator = sysWsOrgService
                    .findSysOrgElement(kmCalendarParamterForm.getDocCreator());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (creator == null) {
            KmCalendarResult result = new KmCalendarResult();// 返回结果
            result.setMessage(kmCalendarParamterForm.getDocCreator() + "用户不存在");
            result.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
            return result;
        }
        // 修改切换用户的方法
        return (KmCalendarResult) backgroundAuthService.switchUserById(creator
                .getFdId(), new Runner() {
            @Override
            public Object run(Object parameter) throws Exception {
                KmCalendarParamterForm form = (KmCalendarParamterForm) parameter;
                KmCalendarResult result = new KmCalendarResult();// 返回结果
                String objName = "event";
                if ("note".equals(form.getFdType())) {
                    objName = "note";
                }
                // 对象字段校验
                if (!checkNullIfNecessary(form, "addCalendar", objName, result)) {
                    result.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
                    return result;
                }
                KmCalendarMain model = calendarForm2Model(form, result);
                if (RETURN_CONSTANT_STATUS_FAIL == result.getReturnState()) {
                    return result;
                }
                // 提醒设置
                if (StringUtil.isNotNull(form.getNotifys())) {
                    JSONArray jsonArray = JSONArray.fromObject(form
                            .getNotifys());
                    List<SysNotifyRemindMain> sysNotifyRemindMainList = new ArrayList<SysNotifyRemindMain>();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        SysNotifyRemindMain mainModel = notifyObj2Model(object);
                        mainModel.setFdModelName(form.getFdAppKey());
                        sysNotifyRemindMainList.add(mainModel);
                    }
                    model
                            .setSysNotifyRemindMainContextModel(sysNotifyRemindMainList);
                }
                // 日程要初始化权限
                if (!"note".equals(form.getFdType())) {
                    initAuth(model);
                }
                // 保存日程或笔记
                String modelId = kmCalendarMainService.add(model);
                String modelName = ModelUtil.getModelClassName(model);

                // 同步映射关联
                KmCalendarSyncMapping mapping = new KmCalendarSyncMapping();
                mapping.setFdAppKey(form.getFdAppKey());
                if (StringUtil.isNotNull(form.getFdAppUUId())) {
                    mapping.setFdAppUuid(form.getFdAppUUId());
                } else {
                    mapping.setFdAppUuid(modelId);
                }
                mapping.setFdCalendarId(modelId);
                kmCalendarSyncMappingService.add(mapping);

                // 笔记附件上传
                if (form.getAttachmentForms().size() > 0) {
                    List<AttachmentForm> attForms = form.getAttachmentForms();
                    sysWsAttService.validateAttSize(attForms);// 校验附件大小
                    sysWsAttService.save(attForms, modelId, modelName);
                }
                JSONObject message = new JSONObject();
                message.put("fdId", model.getFdId());
                result.setMessage(message.toString());
                result.setReturnState(RETURN_CONSTANT_STATUS_SUCESS);
                return result;
            }
        }, kmCalendarParamterForm);
    }

    private void initAuth(KmCalendarMain kmCalendarMain) throws Exception {
        IKmCalendarAuthService kmCalendarAuthService = (IKmCalendarAuthService) SpringBeanUtil
                .getBean("kmCalendarAuthService");
        KmCalendarAuth auth = kmCalendarAuthService.findByPerson(kmCalendarMain
                .getDocOwner()
                .getFdId());
        if (auth != null) {
            List authReader = kmCalendarMain.getAuthReaders();
            if (authReader == null) {
                authReader = new ArrayList();
                kmCalendarMain.setAuthReaders(authReader);
            }
            authReader.addAll(auth.getAuthReaders());
            List authEditor = kmCalendarMain.getAuthEditors();
            if (authEditor == null) {
                authEditor = new ArrayList();
                kmCalendarMain.setAuthEditors(authEditor);
            }
            authEditor.addAll(auth.getAuthModifiers());
        }
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/updateCalendar")
    public KmCalendarResult updateCalendar(
            @ModelAttribute KmCalendarParamterForm kmCalendarParamterForm)
            throws Exception {
        // 切换当前用户
        SysOrgElement creator = null;
        try {
            creator = sysWsOrgService
                    .findSysOrgElement(kmCalendarParamterForm.getDocCreator());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (creator == null) {
            KmCalendarResult result = new KmCalendarResult();// 返回结果
            result.setMessage(kmCalendarParamterForm.getDocCreator() + "用户不存在");
            result.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
            return result;
        }
        final String creatorId = creator.getFdId();
        return (KmCalendarResult) backgroundAuthService.switchUserById(creator
                .getFdId(), new Runner() {
            @Override
            public Object run(Object parameter) throws Exception {
                KmCalendarParamterForm form = (KmCalendarParamterForm) parameter;
                KmCalendarResult result = new KmCalendarResult();// 返回结果
                String objName = "event";
                if ("note".equals(form.getFdType())) {
                    objName = "note";
                }
                if (!checkNullIfNecessary(form, "updateCalendar", objName,
                        result)) {
                    result.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
                    return result;
                }
                KmCalendarMain model = calendarForm2Model(form, result);
                if (RETURN_CONSTANT_STATUS_FAIL == result.getReturnState()) {
                    return result;
                }
                // 提醒设置
                if (StringUtil.isNotNull(form.getNotifys())) {
                    JSONArray jsonArray = JSONArray.fromObject(form
                            .getNotifys());
                    String modelName = ModelUtil.getModelClassName(model);
                    List<SysNotifyRemindMain> sysNotifyRemindMainList = new ArrayList<SysNotifyRemindMain>();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        SysNotifyRemindMain mainModel = notifyObj2Model(object);
                        mainModel.setFdModelName(modelName);
                        sysNotifyRemindMainList.add(mainModel);
                    }
                    model.setSysNotifyRemindMainContextModel(sysNotifyRemindMainList);
                }
                kmCalendarMainService.update(model);
                result.setReturnState(RETURN_CONSTANT_STATUS_SUCESS);
                return result;
            }
        }, kmCalendarParamterForm);
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/listCalendar")
    public KmCalendarResult listCalendar(
            @RequestBody
                    KmCalendarWsQueryContext kmCalendarWsQueryContext) throws Exception {
        KmCalendarResult result = new KmCalendarResult();// 返回结果
        if (StringUtil.isNull(kmCalendarWsQueryContext.getDocStartTime())
                || StringUtil
                .isNull(kmCalendarWsQueryContext.getDocFinishTime())) {
            logger.error("调用日程列表接口失败,开始/结束时间为空!");
            result.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
            return result;
        }

        // 构造日程查询上下文
        CalendarQueryContext context = new CalendarQueryContext();
        Date rangeStart = DateUtil.convertStringToDate(kmCalendarWsQueryContext
                .getDocStartTime(), DateUtil.PATTERN_DATETIME);
        Date rangeEnd = DateUtil.convertStringToDate(kmCalendarWsQueryContext
                .getDocFinishTime(), DateUtil.PATTERN_DATETIME);
        context.setRangeStart(rangeStart);
        context.setRangeEnd(rangeEnd);
        context.setCalType(kmCalendarWsQueryContext.getFdType());
        context.setFdAppkey(kmCalendarWsQueryContext.getAppKey());
        context.setIfAuth(false);
        context.setIncludeRecurrence(false);
        String personsIds = "";
        List<SysOrgElement> persons = parseOrgToPersons(kmCalendarWsQueryContext
                .getPersons());
        if (persons == null || persons.isEmpty()) {
            logger.error("调用日程列表接口失败,用户信息为空!persons:" + kmCalendarWsQueryContext
                    .getPersons());
            result.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
            return result;
        }
        if (persons != null) {
            for (SysOrgElement person : persons) {
                personsIds += person.getFdId() + ";";
            }
        }
        context.setPersonsIds(personsIds);

        List<KmCalendarMain> calendars = new ArrayList<KmCalendarMain>();
        List<KmCalendarMain> rangeCalendars = kmCalendarMainService
                .getRangeCalendars(context);// 获取非重复日程
        List<KmCalendarMain> recurrenceCalendars = kmCalendarMainService
                .getRecurrenceCalendars(context);// 获取重复日程
        if (!ArrayUtil.isEmpty(rangeCalendars)) {
            calendars.addAll(rangeCalendars);
        }
        if (!ArrayUtil.isEmpty(recurrenceCalendars)) {
            calendars.addAll(recurrenceCalendars);
        }
        result.setMessage(formatList(calendars));
        result.setReturnState(RETURN_CONSTANT_STATUS_SUCESS);
        return result;
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/viewCalendar")
    public KmCalendarParamterForm viewCalendar(
            @RequestParam("fdAppUUId") String fdAppUUId,
            @RequestParam("appKey") String appKey)
            throws Exception {
        KmCalendarSyncMapping mapping = getKmCalendarSyncMapping(fdAppUUId,
                appKey);
        if (mapping != null) {
            String calendarId = mapping.getFdCalendarId();// 日程在EKP中的ID
            KmCalendarMain kmCalendarMain = (KmCalendarMain) kmCalendarMainService
                    .findByPrimaryKey(calendarId);
            KmCalendarParamterForm form = calendarModel2Form(kmCalendarMain);
            form.setFdAppKey(mapping.getFdAppKey());
            form.setFdAppUUId(mapping.getFdAppUuid());
            return form;
        } else {
            return null;
        }
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/deleteCalendar")
    public KmCalendarResult deleteCalendar(
            @RequestParam("fdAppUUId") String fdAppUUId,
            @RequestParam("appKey") String appKey,
            @RequestParam(value = "operator", required = false, defaultValue = "") String operator)
            throws Exception {
        KmCalendarResult result = new KmCalendarResult();// 返回结果
        KmCalendarSyncMapping mapping = getKmCalendarSyncMapping(fdAppUUId, appKey);
        if (mapping != null) {
            String calendarId = mapping.getFdCalendarId();// 日程在EKP中的ID
            SysOrgElement creator = null;
            if (StringUtil.isNotNull(operator)) {
                creator = sysWsOrgService.findSysOrgElement(operator);
                if (creator != null) {
                    //参与者
                    HQLInfo hqlInfo = new HQLInfo();
                    hqlInfo.setWhereBlock("kmCalendarDetails.fdCalendar.fdId=:fdCalendarId and kmCalendarDetails.fdPerson.fdId =:currentId");
                    hqlInfo.setParameter("fdCalendarId", calendarId);
                    hqlInfo.setParameter("currentId", creator.getFdId());
                    List<KmCalendarDetails> list = kmCalendarDetailsService.findList(hqlInfo);

                    KmCalendarMain kmCalendarMain = (KmCalendarMain) kmCalendarMainService.findByPrimaryKey(calendarId, KmCalendarMain.class, true);
                    //如果是创建着或者拥有者或者是参与者
                    if (kmCalendarMain != null && (creator.getFdId().equals(kmCalendarMain.getDocCreator().getFdId())
                            || creator.getFdId().equals(kmCalendarMain.getDocOwner().getFdId()) || CollectionUtils.isNotEmpty(list))) {
                        return (KmCalendarResult) backgroundAuthService.switchUserById(creator
                                .getFdId(), new Runner() {
                            @Override
                            public Object run(Object parameter) throws Exception {
                                // 返回结果
                                KmCalendarResult result = new KmCalendarResult();
                                kmCalendarMainService.delete((String) calendarId);
                                result.setMessage("1");
                                result.setReturnState(RETURN_CONSTANT_STATUS_SUCESS);
                                return result;
                            }
                        }, calendarId);
                    } else {
                        result.setMessage("无权限删除");
                        result.setReturnState(RETURN_CONSTANT_STATUS_SUCESS);
                    }
                } else {
                    result.setMessage(operator + "用户不存在");
                    result.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
                }
            } else {
                result.setMessage("删除日程需要提供操作者");
                result.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
            }
        } else {
            result.setMessage("fdAppUUId:" + fdAppUUId + ",appKey:" + appKey + "无法找到日程信息");
            result.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
        }
        return result;
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/conflictCheck")
    public KmCalendarResult conflictCheck(
            @RequestBody KmCalendarCheckContext kmCalendarCheckContext)
            throws Exception {
        KmCalendarResult result = new KmCalendarResult();// 返回结果
        String objName = OBJ_CONSTANT_NAME_CHECK;
        if (!checkNullIfNecessary(kmCalendarCheckContext, "conflictCheck",
                objName, result)) {
            result.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
            return result;
        }
        Date start = DateUtil.convertStringToDate(kmCalendarCheckContext
                .getDocStartTime(), DateUtil.PATTERN_DATETIME);
        Date end = DateUtil.convertStringToDate(kmCalendarCheckContext
                .getDocFinishTime(), DateUtil.PATTERN_DATETIME);
        SysOrgElement person = sysWsOrgService
                .findSysOrgElement(kmCalendarCheckContext.getPerson());
        String personId = person.getFdId();

        // 构建日程查询上下文
        CalendarQueryContext context = new CalendarQueryContext();
        context.setRangeStart(start);
        context.setRangeEnd(end);
        context.setPersonsIds(personId);
        context.setIncludeRecurrence(false);
        context.setCalType("event");
        context.setIfAuth(false);

        List<KmCalendarMain> rangeCalendars = kmCalendarMainService
                .getRangeCalendars(context);// 获取指定时间内的日程
        List<KmCalendarMain> recurrenceCalendars = kmCalendarMainService
                .getRecurrenceCalendars(context);// 获取重复日程
        List<KmCalendarMain> calendars = new ArrayList<KmCalendarMain>();
        if (!ArrayUtil.isEmpty(rangeCalendars)) {
            calendars.addAll(rangeCalendars);
        }
        if (!ArrayUtil.isEmpty(recurrenceCalendars)) {
            calendars.addAll(recurrenceCalendars);
        }
        result.setMessage(getConflictTimes(calendars).toString());// 将日程列表转化为冲突列表（JSON格式返回）
        result.setReturnState(RETURN_CONSTANT_STATUS_SUCESS);
        return result;
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/setNotify")
    public KmCalendarResult setNotify(
            @RequestParam("fdAppUUId") String fdAppUUId,
            @RequestParam("appKey") String appKey,
            @RequestParam("kmCalendarNotify") String kmCalendarNotify)
            throws Exception {
        KmCalendarSyncMapping mapping = getKmCalendarSyncMapping(fdAppUUId,
                appKey);
        String calendarId = mapping.getFdCalendarId();
        KmCalendarResult result = new KmCalendarResult();// 返回结果
        List<SysNotifyRemindMain> sysNotifyRemindMainList = new ArrayList<SysNotifyRemindMain>();
        KmCalendarMain kmCalendarMain = (KmCalendarMain) kmCalendarMainService
                .findByPrimaryKey(calendarId);// 日程对象
        String modelName = ModelUtil.getModelClassName(kmCalendarMain);

        JSONArray jsonArray = JSONArray.fromObject(kmCalendarNotify);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            SysNotifyRemindMain mainModel = notifyObj2Model(object);
            mainModel.setFdModelName(modelName);
            sysNotifyRemindMainList.add(mainModel);
        }
        kmCalendarMain
                .setSysNotifyRemindMainContextModel(sysNotifyRemindMainList);
        kmCalendarMainService.update(kmCalendarMain);
        result.setReturnState(RETURN_CONSTANT_STATUS_SUCESS);
        return result;
    }

    /**
     * 根据UUID获取同步映射对象
     *
     * @param fdAppUUId
     */
    private KmCalendarSyncMapping getKmCalendarSyncMapping(String fdAppUUId,
                                                           String appKey) throws Exception {
        if (fdAppUUId.indexOf("###") > 0) {
            fdAppUUId = fdAppUUId.substring(fdAppUUId.indexOf("###") + 3);
        }
        HQLInfo hqlInfo = new HQLInfo();
        String whereBlock = " kmCalendarSyncMapping.fdAppUuid=:fdAppUUId";
        hqlInfo.setParameter("fdAppUUId", fdAppUUId);
        if (StringUtil.isNotNull(appKey)) {
            whereBlock += " and kmCalendarSyncMapping.fdAppKey=:fdAppKey";
            hqlInfo.setParameter("fdAppKey", appKey);
        }
        hqlInfo.setWhereBlock(whereBlock);
        List<KmCalendarSyncMapping> mappings = (List<KmCalendarSyncMapping>) kmCalendarSyncMappingService
                .findList(hqlInfo);
        if (mappings.isEmpty()) {
            return null;
        } else {
            return mappings.get(0);
        }
    }

    /**
     * 将分页对象转化为JSON格式
     *
     * @param queryPage
     */
    private String formatPage(Page queryPage) throws Exception {
        JSONObject message = new JSONObject();
        if (queryPage != null && queryPage.getTotalrows() > 0) {
            message.accumulate("total", queryPage.getTotal()); // 所有页数
            message.accumulate("pageno", queryPage.getPageno()); // 当前页码
            message.accumulate("totalRows", queryPage.getTotalrows()); // 文档总数
            // 日程数据
            message.accumulate("datas", list2JSON(queryPage.getList()));
        }
        return message.toString();
    }

    /**
     * 将List对象转化为JSON格式
     *
     * @param list
     * @return
     */
    private String formatList(List list) throws Exception {
        JSONObject message = new JSONObject();
        if (!list.isEmpty()) {
            message.accumulate("datas", list2JSON(list));
        }
        return message.toString();
    }

    private JSONArray list2JSON(List list) throws Exception {
        JSONArray datas = new JSONArray();
        for (Object model : list) {
            JSONObject obj = new JSONObject();
            KmCalendarMain kmCalendarMain = (KmCalendarMain) model;
            obj.accumulate("fdId", kmCalendarMain.getFdId());
            obj.accumulate("docStartTime", DateUtil
                    .convertDateToString(kmCalendarMain.getDocStartTime(),
                            DateUtil.PATTERN_DATETIME));
            obj.accumulate("docFinishTime", DateUtil.convertDateToString(
                    kmCalendarMain.getDocFinishTime(),
                    DateUtil.PATTERN_DATETIME));
            obj.accumulate("docSubject", kmCalendarMain.getDocSubject());
            if ("note".equals(kmCalendarMain.getFdType())) {
                obj.accumulate("docContent", kmCalendarMain.getDocContent());
            }
            JSONObject person = new JSONObject();
            person.put("loginName", kmCalendarMain.getDocOwner()
                    .getFdLoginName());
            obj.accumulate("person", person.toString());

            // appkey
            String fdAppKey = KmCalendarMain.class.getName();
            String fdAppUUId = kmCalendarMain.getFdId();
            List<KmCalendarSyncMapping> mappings = kmCalendarSyncMappingService
                    .findByCalendarId(kmCalendarMain.getFdId());
            if (mappings != null && !mappings.isEmpty()) {
                KmCalendarSyncMapping mapping = mappings.get(0);
                fdAppKey = mapping.getFdAppKey();
                fdAppUUId = mapping.getFdAppUuid();
            }
            obj.accumulate("fdAppKey", fdAppKey);
            obj.accumulate("fdAppUUId", fdAppUUId);
            datas.add(obj);
        }
        return datas;
    }

    /**
     * 返回冲突时间段
     */
    private JSONArray getConflictTimes(List list) throws Exception {
        JSONArray datas = new JSONArray();
        for (Object model : list) {
            JSONObject obj = new JSONObject();
            KmCalendarMain kmCalendarMain = (KmCalendarMain) model;
            String conflictTime = DateUtil.convertDateToString(kmCalendarMain
                    .getDocStartTime(), DateUtil.PATTERN_DATETIME)
                    + "-"
                    + DateUtil.convertDateToString(kmCalendarMain
                    .getDocFinishTime(), DateUtil.PATTERN_DATETIME);
            obj.accumulate("conflictTime", conflictTime);
            obj.accumulate("docSuject", kmCalendarMain.getDocSubject());
            datas.add(obj);
        }
        return datas;
    }

    /**
     * 将WebService中定义的日程Form转化为日程model
     *
     * @param form
     * @return
     */
    private KmCalendarMain calendarForm2Model(KmCalendarParamterForm form, KmCalendarResult result)
            throws Exception {
        KmCalendarMain model = new KmCalendarMain();
        if (StringUtil.isNotNull(form.getFdAppUUId())) {
            KmCalendarSyncMapping mapping = getKmCalendarSyncMapping(form
                    .getFdAppUUId(), form.getFdAppKey());
            if (mapping != null) {
                KmCalendarMain tempModel = (KmCalendarMain) kmCalendarMainService
                        .findByPrimaryKey(mapping.getFdCalendarId());
                if (tempModel != null) {
                    model = tempModel;
                }
            }
        }

        // if (StringUtil.isNotNull(form.getFdId())) {
        // KmCalendarMain tempModel = (KmCalendarMain) kmCalendarMainService
        // .findByPrimaryKey(form.getFdId());
        // if (tempModel != null) {
        // model = tempModel;
        // }
        // }
        String tmpStr = form.getDocSubject();
        model.setDocSubject(tmpStr);

        tmpStr = form.getDocContent();
        model.setDocContent(tmpStr);

        tmpStr = form.getFdIsAlldayevent();
        String datepattern = DateUtil.PATTERN_DATETIME;
        if (StringUtil.isNotNull(tmpStr)) {
            if ("true".equals(tmpStr)) {
                model.setFdIsAlldayevent(true);
                datepattern = DateUtil.PATTERN_DATE;
            } else {
                model.setFdIsAlldayevent(false);
            }
        }

        tmpStr = form.getDocStartTime();
        model
                .setDocStartTime(DateUtil.convertStringToDate(tmpStr,
                        datepattern));

        tmpStr = form.getDocFinishTime();
        model.setDocFinishTime(DateUtil
                .convertStringToDate(tmpStr, datepattern));

        tmpStr = form.getFdAuthorityType();
        model.setFdAuthorityType(tmpStr);

        tmpStr = form.getFdLocation();
        model.setFdLocation(tmpStr);

        tmpStr = form.getFdRelationUrl();
        model.setFdRelationUrl(tmpStr);

        tmpStr = form.getFdType();
        model.setFdType(tmpStr);

        SysOrgPerson creator = (SysOrgPerson) sysWsOrgService.findSysOrgElement(form.getDocCreator());
        SysOrgPerson owner = null;
        try {
            owner = (SysOrgPerson) sysWsOrgService.findSysOrgElement(form.getDocOwner());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (owner == null) {
            result.setMessage("日程所属人" + form.getDocOwner() + "不存在");
            result.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
            return model;
        }
        //说明是修改日程
        if (model.getDocCreator() != null || model.getDocOwner() != null) {
            //是否允许修改，只允许修改自身创建的日程或者自身拥有的日程
            boolean update = false;
            //说明是修改日程创建者，说明可以进行日程的修改
            if (model.getDocCreator() != null && creator != null && model.getDocCreator().getFdId().equals(creator.getFdId())) {
                update = true;
            }
            //说明是日程的拥有者，说明可以修改日程
            if (model.getDocOwner() != null && owner != null && model.getDocOwner().getFdId().equals(owner.getFdId())) {
                update = true;
            }
            if (!update) {
                result.setMessage("日程不允许修改");
                result.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
                return model;
            }
        }
        model.setDocCreator(creator);
        model.setDocOwner(owner);

        // tmpStr=form.getFdLabelId();
        KmCalendarLabel label = findLabel(form.getFdAppKey(), owner.getFdId());
        model.setDocLabel(label);

        model.setFdIsLunar(false);

        List authReaders = parseOrgToPersons(form.getAuthReaders());
        model.setAuthReaders(authReaders);

        List authEditors = parseOrgToPersons(form.getAuthEditors());
        model.setAuthEditors(authEditors);

        String recurrenceStr = buildRecurrenceStr(form);
        model.setFdRecurrenceStr(recurrenceStr);

        return model;
    }

    /**
     * 将日程model转化为WebService中定义的日程
     *
     * @param model
     * @return
     */
    private KmCalendarParamterForm calendarModel2Form(KmCalendarMain model) {
        KmCalendarParamterForm form = new KmCalendarParamterForm();
        String tmpStr = model.getFdId();
        form.setFdId(tmpStr);

        tmpStr = model.getDocSubject();
        form.setDocSubject(tmpStr);

        tmpStr = model.getDocContent();
        form.setDocContent(tmpStr);

        tmpStr = DateUtil.convertDateToString(model.getDocStartTime(),
                DateUtil.PATTERN_DATETIME);
        form.setDocStartTime(tmpStr);

        tmpStr = DateUtil.convertDateToString(model.getDocFinishTime(),
                DateUtil.PATTERN_DATETIME);
        form.setDocFinishTime(tmpStr);

        Boolean isAlldayevnet = model.getFdIsAlldayevent();
        form.setFdIsAlldayevent(isAlldayevnet.toString());

        tmpStr = model.getFdAuthorityType();
        form.setFdAuthorityType(tmpStr);

        tmpStr = model.getFdLocation();
        form.setFdLocation(tmpStr);

        tmpStr = model.getFdRelationUrl();
        form.setFdRelationUrl(tmpStr);

        tmpStr = model.getFdType();
        form.setFdType(tmpStr);

        JSONObject creator = new JSONObject();
        creator.put("loginName", model.getDocCreator().getFdLoginName());
        form.setDocCreator(creator.toString());

        JSONObject owner = new JSONObject();
        owner.put("loginName", model.getDocOwner().getFdLoginName());
        form.setDocOwner(owner.toString());

        // tmpStr=form.getFdLabelId();
        // 可阅读者
        JSONArray authReaders = new JSONArray();
        for (Object obj : model.getAuthAllReaders()) {
            JSONObject reader = new JSONObject();
            reader.put("loginName", ((SysOrgPerson) obj).getFdLoginName());
            authReaders.add(reader);
        }
        form.setAuthReaders(authReaders.toString());

        JSONArray authEditors = new JSONArray();
        for (Object obj : model.getAuthAllEditors()) {
            JSONObject editor = new JSONObject();
            editor.put("loginName", ((SysOrgPerson) obj).getFdLoginName());
            authEditors.add(editor);
        }
        form.setAuthEditors(authEditors.toString());

        tmpStr = model.getFdRecurrenceStr();
        parseRecurrenceStr(tmpStr, form);

        // 提醒
        List<SysNotifyRemindMain> notifys = model
                .getSysNotifyRemindMainContextModel()
                .getSysNotifyRemindMainList();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < notifys.size(); i++) {
            jsonArray.add(i, notifyModel2Obj(notifys.get(i)));
        }
        form.setNotifys(jsonArray.toString());

        return form;
    }

    private SysNotifyRemindMain notifyObj2Model(JSONObject object) {
        SysNotifyRemindMain model = new SysNotifyRemindMain();

        String tmpStr = object.getString("fdNotifyType");
        model.setFdNotifyType(tmpStr);

        tmpStr = object.getString("fdBeforeTime");
        model.setFdBeforeTime(tmpStr);

        tmpStr = object.getString("fdTimeUnit");
        model.setFdTimeUnit(tmpStr);
        return model;
    }

    private JSONObject notifyModel2Obj(SysNotifyRemindMain model) {
        JSONObject obj = new JSONObject();
        obj.put("fdNotifyType", model.getFdNotifyType());
        obj.put("fdBeforeTime", model.getFdBeforeTime());
        obj.put("fdTimeUnit", model.getFdTimeUnit());
        return obj;
    }

    /**
     * 查询指定标签
     */
    private KmCalendarLabel findLabel(String modelName, String userId)
            throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo
                .setWhereBlock("kmCalendarLabel.fdCreator.fdId=:fdId and kmCalendarLabel.fdModelName=:fdModelName");
        hqlInfo.setParameter("fdId", userId);
        hqlInfo.setParameter("fdModelName", modelName);
        List<KmCalendarLabel> labels = kmCalendarLabelService.findList(hqlInfo);
        if (!labels.isEmpty()) {
            return labels.get(0);
        } else {
            return kmCalendarLabelService.addAgendaLabel(modelName, userId);
        }
    }

    /**
     * 构建日程重复信息
     */
    private String buildRecurrenceStr(KmCalendarParamterForm form) {
        String freq = form.getRecurrenceFreq();
        String recurrenceStr = null;
        // 重复类型不为"不重复"
        if (!KmCalendarConstant.RECURRENCE_FREQ_NO.equals(freq)) {
            String byday = null;
            if (KmCalendarConstant.RECURRENCE_FREQ_WEEKLY.equals(freq)) {
                byday = form.getRecurrenceWeeks().replaceAll(";", ",");
            } else if (KmCalendarConstant.RECURRENCE_FREQ_MONTHLY.equals(freq)) {
                if (KmCalendarConstant.RECURRENCE_MONTH_TYPE_WEEK.equals(form
                        .getRecurrenceMonthType())) {
                    Date startDate = DateUtil.convertStringToDate(form
                            .getDocStartTime(), DateUtil.PATTERN_DATE);
                    Calendar c = Calendar.getInstance();
                    c.setFirstDayOfWeek(Calendar.SUNDAY);
                    c.setTime(startDate);
                    int weekOfMonth = c.get(Calendar.WEEK_OF_MONTH);
                    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                    String weekStr = weeks[dayOfWeek - 1];
                    byday = weekOfMonth + weekStr;
                }
            }
            recurrenceStr = Rfc2445Util.buildRecurrenceStr(freq, form
                    .getRecurrenceInterval(), form.getRecurrenceEndType(), form
                    .getRecurrenceCount(), form.getRecurrenceUntil(), byday);
        }
        return recurrenceStr;
    }

    /**
     * 解析日程重复信息
     */
    private void parseRecurrenceStr(String recurrenceStr,
                                    KmCalendarParamterForm form) {
        if (StringUtil.isNotNull(recurrenceStr)) {
            Map<String, String> result = Rfc2445Util
                    .parseRecurrenceStr(recurrenceStr);
            String freq = result.get(KmCalendarConstant.RECURRENCE_FREQ);
            String interval = result
                    .get(KmCalendarConstant.RECURRENCE_INTERVAL);
            String endType = result.get(KmCalendarConstant.RECURRENCE_END_TYPE);

            form.setRecurrenceFreq(freq);// 重复类型
            form.setRecurrenceInterval(interval);// 重复频率
            form.setRecurrenceEndType(endType);// 结束条件

            if (KmCalendarConstant.RECURRENCE_FREQ_WEEKLY.equals(freq)) {
                String week = result.get(KmCalendarConstant.RECURRENCE_WEEKS);
                form.setRecurrenceWeeks(week);// 重复时间(周)
            } else if (KmCalendarConstant.RECURRENCE_FREQ_MONTHLY.equals(freq)) {
                String monthType = result
                        .get(KmCalendarConstant.RECURRENCE_MONTH_TYPE);
                form.setRecurrenceMonthType(monthType);// 重复时间(月)
            }

            if (KmCalendarConstant.RECURRENCE_END_TYPE_COUNT.equals(endType)) {
                String count = result.get(KmCalendarConstant.RECURRENCE_COUNT);
                form.setRecurrenceCount(count);// 重复次数
            } else if (KmCalendarConstant.RECURRENCE_END_TYPE_UNTIL
                    .equals(endType)) {
                String until = result.get(KmCalendarConstant.RECURRENCE_UNTIL);
                until = until.substring(0, 4) + "-" + until.substring(4, 6)
                        + "-" + until.substring(6);
                form.setRecurrenceUntil(until);// 重复直到
            }
        }
    }

    private List<SysOrgElement> parseOrgToPersons(String jsonPerson)
            throws Exception {
        if (StringUtil.isNull(jsonPerson)) {
            return null;
        }
        List<SysOrgElement> orgList = null;
        if (jsonPerson.indexOf("[") > -1) {
            orgList = sysWsOrgService.findSysOrgList(jsonPerson);
        } else {
            orgList = new ArrayList();
            SysOrgElement tmpOrg = sysWsOrgService
                    .findSysOrgElement(jsonPerson);
            if (tmpOrg != null) {
                orgList.add(tmpOrg);
            }
        }
        boolean isPerson = true;
        if (orgList != null && !orgList.isEmpty()) {
            for (int i = 0; i < orgList.size(); i++) {
                SysOrgElement org = (SysOrgElement) orgList.get(i);
                if (org.getFdOrgType() != ORG_TYPE_PERSON) {
                    isPerson = false;
                    break;
                }
            }
            if (!isPerson) {
                orgList = sysOrgCoreService.expandToPerson(orgList);
            }
        }
        return orgList;
    }

    /**
     * 对象校验
     */
    private boolean checkNullIfNecessary(Object obj, String methodKey,
                                         String objName, KmCalendarResult result) throws Exception {
        if (null == obj) {
            result.setMessage(objName + "不能为空!");
            logger.debug(objName + "不能为空!");
            return false;
        }
        String fields = "";
        // 日程必填字段
        if (OBJ_CONSTANT_NAME_EVENT.equals(objName)) {
            fields += "docSubject;docStartTime;fdIsAlldayevent;fdAuthorityType;fdType;docCreator;docOwner;";
        }
        // 笔记必填字段
        if (OBJ_CONSTANT_NAME_NOTE.equals(objName)) {
            fields += "docSubject;docContent;docStartTime;fdType;docCreator;";
        }
        if (METHOD_CONSTANT_NAME_UPDATE.equals(methodKey)) {
            fields += "fdAppUUId";
        }
        // 冲突检测上下文必填字段
        if (OBJ_CONSTANT_NAME_CHECK.equals(objName)) {
            fields += "person;docStartTime;docFinishTime";
        }
        // 提醒必填字段
        if (OBJ_CONSTANT_NAME_NOTIFY.equals(objName)) {
            fields += "fdNotifyType;fdBeforeTime;fdTimeUnit";
        }
        String[] filedArr = fields.split(";");
        for (int i = 0; i < filedArr.length; i++) {
            if (isNullProperty(obj, filedArr[i])) {
                result.setMessage("方法" + methodKey + "中,不允许对象" + objName
                        + "中的\"" + filedArr[i] + "\"为空!");
                logger.debug("方法" + methodKey + "中,不允许对象" + objName + "中的\""
                        + filedArr[i] + "\"为空!");
                return false;
            }
        }
        return true;
    }

    /**
     * 对象指定字段非空校验
     */
    private boolean isNullProperty(Object obj, String name) throws Exception {
        Object tmpObj = PropertyUtils.getProperty(obj, name);
        if (tmpObj instanceof String) {
            return StringUtil.isNull((String) tmpObj);
        } else if (tmpObj instanceof Integer) {
            return ((Integer) tmpObj) == 0;
        } else {
            return tmpObj == null;
        }
    }

}
