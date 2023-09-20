package com.landray.kmss.sys.attend.plugin;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.engine.task.NodeInstanceUtils;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountItemService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveDetailService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 加班结束事件。
 * （历史事件，使用新事件可不使用该事件）
 * @author 王京
 */
public class SysAttendOvertimeListener extends SysAttendListenerCommonImp
        implements IEventListener, IEventMulticasterAware {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendOvertimeListener.class);

    private ISysTimeLeaveRuleService sysTimeLeaveRuleService;
    private ISysTimeLeaveDetailService sysTimeLeaveDetailService;
    private ISysTimeLeaveAmountService sysTimeLeaveAmountService;

    public ISysTimeLeaveRuleService getSysTimeLeaveRuleService() {
        if (sysTimeLeaveRuleService == null) {
            sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) SpringBeanUtil
                    .getBean("sysTimeLeaveRuleService");
        }
        return sysTimeLeaveRuleService;
    }

    public ISysTimeLeaveDetailService getSysTimeLeaveDetailService() {
        if (sysTimeLeaveDetailService == null) {
            sysTimeLeaveDetailService = (ISysTimeLeaveDetailService) SpringBeanUtil
                    .getBean("sysTimeLeaveDetailService");
        }
        return sysTimeLeaveDetailService;
    }

    public ISysTimeLeaveAmountService getSysTimeLeaveAmountService() {
        if (sysTimeLeaveAmountService == null) {
            sysTimeLeaveAmountService = (ISysTimeLeaveAmountService) SpringBeanUtil
                    .getBean("sysTimeLeaveAmountService");
        }
        return sysTimeLeaveAmountService;
    }

    private ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService;

    public ISysTimeLeaveAmountItemService getSysTimeLeaveAmountItemService() {
        if (sysTimeLeaveAmountItemService == null) {
            sysTimeLeaveAmountItemService = (ISysTimeLeaveAmountItemService) SpringBeanUtil
                    .getBean(
                            "sysTimeLeaveAmountItemService");
        }
        return sysTimeLeaveAmountItemService;
    }

    private IEventMulticaster multicaster;

    @Override
    public void setEventMulticaster(IEventMulticaster multicaster) {
        this.multicaster = multicaster;

    }

    @Override
    public void handleEvent(EventExecutionContext execution, String parameter)
            throws Exception {
        logger.debug(
                "receive SysAttendOvertimeListener:parameter=" + parameter);
        try {
            String processFlag = execution.getProcessParameters().getInstanceParamValue(execution.getProcessInstance(),PROCESS_FLAG_KEY);
            if (PROCESS_FLAG_RUN_VALUE.equals(processFlag) || PROCESS_FLAG_ERROR_VALUE.equals(processFlag)) {
                //新流程事件不进老流程处理
                return;
            }
            String routeType = execution.getNodeInstance().getFdRouteType();
            String docStatus = execution.getExecuteParameters()
                    .getExpectMainModelStatus();
            if ((NodeInstanceUtils.ROUTE_TYPE_NORMAL.equals(routeType)
                    || NodeInstanceUtils.ROUTE_TYPE_JUMP.equals(routeType))
                    && !SysDocConstant.DOC_STATUS_DISCARD.equals(docStatus)
                    && !SysDocConstant.DOC_STATUS_DRAFT.equals(docStatus)) {
                logger.debug("start handleEvent...");
                JSONObject params = JSONObject.fromObject(parameter);
                IBaseModel mainModel = execution.getMainModel();
                if (mainModel instanceof IExtendDataModel) {
                    List<SysAttendBusiness> list = getSysAttendBusinessService()
                            .findByProcessId(mainModel.getFdId());
                    // 同一流程不会重复操作
                    if (list.isEmpty()) {

                        JSONObject ovtTimeJson = params.containsKey("fdOvtTime") ? JSONObject.fromObject(params.get("fdOvtTime")) : new JSONObject();
                        //新旧数据标识
                        boolean isNewEmpty = false;
                        if (ovtTimeJson.isEmpty()) {
                            isNewEmpty = true;
                        }
                        List<SysAttendBusiness> busList = getBusinessList(
                                params,
                                mainModel, isNewEmpty);
                        if (busList == null || busList.isEmpty()) {
                            logger.warn(
                                    "加班流程数据为空,请查看加班事件配置是否准确!parameter:"
                                            + parameter);
                            throw new RuntimeException("加班流程数据为空,请查看加班事件配置是否准确");

                        }
                        List<SysAttendBusiness> statList =new ArrayList<>();
                        for (SysAttendBusiness bus : busList) {
                            checkUserCategory(bus.getFdTargets(),bus.getFdBusStartTime());
                            // 是否时间重叠
                            if (!checkDateRepeat(bus,6,false)) {
                                logger.warn("申请加班失败：加班时间重叠");
                                throw new RuntimeException("申请加班失败：加班时间重叠");
                            }
                            getSysAttendBusinessService().add(bus);
                            statList.add(bus);
                            addOvertime(bus);
                        }
                        if(CollectionUtils.isNotEmpty(statList)) {
                            // 2.重新统计
                            restat(statList);
                        }
                    } else {
                        logger.warn(
                                "同个流程只执行一次,忽略此次操作!流程id:" + mainModel.getFdId()
                                        + ";parameter=" + parameter);
                        throw new RuntimeException("同个流程只执行一次,忽略此次操作!");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("加班流程事件同步考勤失败:" + parameter, e);
            throw new RuntimeException("流程事件同步考勤失败:"+e.getMessage());
        }
    }

    /**
     * 结束时间如果是0点0分。则默认增加一天
     * @param endDateList 结束时间列表
     */
    private List<Date> convertEndTime(List<Date> endDateList) {
        List<Date> endDateListNew=new ArrayList<>();
        if (CollectionUtils.isNotEmpty(endDateList)) {
            for (Date date : endDateList) {
                endDateListNew.add(convertEndTime(date));
            }
        }
        return endDateListNew;
    }
    /**
     * 开始时间结束时间是在同一天
     * 结束时间如果是0点0分。则默认增加一天
     * @param endDate 结束时间
     */
    private Date convertEndTime(Date endDate,Date startDate) {
        if (null != endDate) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.get(Calendar.DATE);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(startDate);
            endCalendar.get(Calendar.DATE);
            if(calendar.get(Calendar.DATE) == endCalendar.get(Calendar.DATE)){
                return convertEndTime(endDate);
            }
        }
        return endDate;
    }
    /**
     * 结束时间如果是0点0分。则默认增加一天
     * @param endDate 结束时间
     */
    private Date convertEndTime(Date endDate) {
        if (null != endDate) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endDate);
            //小时
            int day = calendar.get(Calendar.HOUR_OF_DAY);
            //分钟
            int minute = calendar.get(Calendar.MINUTE);
            //判断如果是0点0分，则自动加一天
            if (day == 0 && minute == 0) {
                Integer twelve = 24 * 60 * 60 * 1000 - 100;
                calendar.add(Calendar.MILLISECOND,twelve);
            }
            return calendar.getTime();
        }
        return endDate;
    }

    /**
     * 封装加班时间转业务数据
     * @param params
     * @param mainModel
     * @param isNewEmpty
     * @return
     * @throws Exception
     */
    protected List<SysAttendBusiness> getBusinessList(JSONObject params,
                                                    IBaseModel mainModel, boolean isNewEmpty) throws Exception {
        try {
            List<SysAttendBusiness> busList = new ArrayList<SysAttendBusiness>();
            IExtendDataModel model = (IExtendDataModel) mainModel;
            Map<String, Object> modelData = model.getExtendDataModelInfo()
                    .getModelData();

            String docSubject = (String) getSysMetadataParser()
                    .getFieldValue(mainModel, "docSubject", false);
            JSONObject targetsJson = JSONObject
                    .fromObject(params.get("fdOvtTargets"));
            JSONObject startTimeJson = JSONObject
                    .fromObject(params.get("fdOvtStartTime"));
            JSONObject endTimeJson = JSONObject
                    .fromObject(params.get("fdOvtEndTime"));
            // 获取假期类型
            //如果没配置调休假类型，则默认调休，编号13
            String fdLeaveType = params.containsKey("fdLeaveType") ? params.getString("fdLeaveType") : "13";
            // 加班处理方式
            JSONObject overHandlJson = JSONObject.fromObject(params.getString("fdOverHandle"));
            //用餐时间
            JSONObject mealTimesJson = JSONObject.fromObject(params.getString("fdMealTimes"));
            SysTimeLeaveRule leaveRule = null;
            if (StringUtil.isNotNull(fdLeaveType)) {
                leaveRule = AttendUtil.getLeaveRuleByType(Integer.parseInt(fdLeaveType));
                if (leaveRule == null) {
                    logger.warn("加班流程中获取假期类型为空,忽略补休操作!fdLeaveType:" + fdLeaveType);
                }
            }

            String dateFieldName = null;
            String dateFieldType = null;
            String ovtFieldName = null;
            String ovtFieldType = null;
            if (isNewEmpty) {
                JSONObject dateJson = JSONObject.fromObject(params.get("fdOvtDate"));
                dateFieldName = (String) dateJson.get("value");
                dateFieldType = (String) dateJson.get("model");
            } else {
                JSONObject ovtTimeJson = JSONObject.fromObject(params.get("fdOvtTime"));
                ovtFieldName = (String) ovtTimeJson.get("value");
                ovtFieldType = (String) ovtTimeJson.get("model");
            }
            String targetsFieldName = (String) targetsJson.get("value");
            String startFieldName = (String) startTimeJson.get("value");
            String endFieldName = (String) endTimeJson.get("value");
            String startFieldType = (String) startTimeJson.get("model");
            String endFieldType = (String) endTimeJson.get("model");

            // 人员
            Object targetIds = null;
            List targetIdsList = new ArrayList();
            int detailSize = 0;
            boolean isDetail1 = false;
            if (targetsFieldName.indexOf(".") == -1) {
                if (modelData.containsKey(targetsFieldName)) {
                    Object targetsObj = modelData.get(targetsFieldName);
                    targetIds = BeanUtils.getProperty(targetsObj, "id");
                } else {// 申请人等字段
                    SysOrgElement org = (SysOrgElement) PropertyUtils
                            .getProperty(mainModel, targetsFieldName);
                    targetIds = org.getFdId();
                }
            } else {
                isDetail1 = true;
                String detailName = targetsFieldName.split("\\.")[0];
                String targetName = targetsFieldName.split("\\.")[1];
                List<HashMap<String, Object>> detailList = (ArrayList<HashMap<String, Object>>) modelData
                        .get(detailName);
                for (int k = 0; k < detailList.size(); k++) {
                    HashMap detail = detailList.get(k);
                    Object targetsObj = detail.get(targetName);
                    targetIds = BeanUtils.getProperty(targetsObj, "id");
                    targetIdsList.add(targetIds);
                }
                detailSize = targetIdsList.size();
            }

            // 加班日期
            Object date = null;
            List dateList = new ArrayList();
            boolean isDetail2 = false;
            if ("Date".equals(dateFieldType)) {
                date = getSysMetadataParser().getFieldValue(mainModel,
                        dateFieldName, false);
            } else if ("Date[]".equals(dateFieldType)) {
                isDetail2 = true;
                dateList = (ArrayList) getSysMetadataParser()
                        .getFieldValue(mainModel, dateFieldName, false);
                detailSize = dateList.size();
            }
            //新旧数据标识
            boolean isNew = true;
            // 加班开始时间
            Object startTime = null;
            List startTimeList = new ArrayList();
            boolean isDetail3 = false;

            // 加班结束时间
            Object endTime = null;
            List endTimeList = new ArrayList();
            boolean isDetail4 = false;
            //如果等于true代表是旧数据。
            if (isNewEmpty) {
                isNew = false;
                if ("Time".equals(startFieldType)) {
                    startTime = getSysMetadataParser().getFieldValue(mainModel,
                            startFieldName, false);
                } else if ("Time[]".equals(startFieldType)) {
                    isDetail3 = true;
                    startTimeList = (ArrayList) getSysMetadataParser()
                            .getFieldValue(mainModel, startFieldName, false);
                    detailSize = startTimeList.size();
                }


                if ("Time".equals(endFieldType)) {
                    Object endTimeObj = getSysMetadataParser().getFieldValue(mainModel,
                            endFieldName, false);
                    if(endTimeObj instanceof Date){
                        endTime=convertEndTime((Date) endTimeObj);
                    }
                } else if ("Time[]".equals(endFieldType)) {
                    isDetail4 = true;
                    Object endTimeListObj = getSysMetadataParser().getFieldValue(mainModel, endFieldName, false);
                    if(endTimeListObj instanceof ArrayList) {
                        endTimeList = convertEndTime((List<Date>) endTimeListObj);
                    }
                    detailSize = endTimeList.size();
                }
                logger.warn("Old data");
            } else {

                //新加班开始时间
                if ("Date".equals(startFieldType) || "DateTime".equals(startFieldType)) {
                    startTime = getSysMetadataParser().getFieldValue(mainModel,
                            startFieldName, false);
                } else if ("Date[]".equals(startFieldType) || "DateTime[]".equals(startFieldType)) {
                    isDetail3 = true;
                    startTimeList = (ArrayList) getSysMetadataParser()
                            .getFieldValue(mainModel, startFieldName, false);
                    detailSize = startTimeList.size();
                }

                //新加班结束时间
                if ("Date".equals(endFieldType) || "DateTime".equals(endFieldType)) {
                    Object endTimeObj = getSysMetadataParser().getFieldValue(mainModel,
                            endFieldName, false);
                    if(endTime instanceof Date){
                        endTime=convertEndTime((Date) endTimeObj);
                    }
                } else if ("Date[]".equals(endFieldType) || "DateTime[]".equals(endFieldType)) {
                    isDetail4 = true;
                    Object endTimeListObj = (ArrayList) getSysMetadataParser()
                            .getFieldValue(mainModel, endFieldName, false);
                    if(endTimeListObj instanceof ArrayList) {
                        endTimeList = convertEndTime((List<Date>) endTimeListObj);
                    }
                    detailSize = endTimeList.size();
                }
                logger.warn("New data");
            }


            // 加班工时
            Object ovtTime = null;
            List ovtTimeList = new ArrayList();
            boolean isDetail5 = false;
            if ("BigDecimal".equals(ovtFieldType) || "Double".equals(ovtFieldType)) {
                ovtTime = getSysMetadataParser().getFieldValue(mainModel,
                        ovtFieldName, false);
            } else if ("BigDecimal[]".equals(ovtFieldType) || "Double[]".equals(ovtFieldType)) {
                isDetail5 = true;
                ovtTimeList = (ArrayList) getSysMetadataParser()
                        .getFieldValue(mainModel, ovtFieldName, false);
                detailSize = ovtTimeList.size();
            }

            //加班处理方式
            String overHandleType = overHandlJson.getString("model");
            String overHandleName = overHandlJson.getString("value");
            String overHandle = null;
            List overHandleList = new ArrayList();
            boolean isDetail6 = false;
            if ("String".equals(overHandleType)) {
                overHandle = (String) getSysMetadataParser().getFieldValue(mainModel, overHandleName, false);
            } else if ("String[]".equals(overHandleType) ) {
                isDetail6 = true;
                overHandleList = (ArrayList) getSysMetadataParser().getFieldValue(mainModel, overHandleName, false);
                detailSize = overHandleList.size();
            }

            //用餐时间
            String mealTimesType = mealTimesJson.getString("model");
            String mealTimesName = mealTimesJson.getString("value");
            String mealTimes = null;
            List mealTimesList = new ArrayList();
            boolean isDetail7 = false;
            if ("String".equals(mealTimesType)) {
                mealTimes = (String) getSysMetadataParser().getFieldValue(mainModel, mealTimesName, false);
            } else if ("String[]".equals(mealTimesType) ) {
                isDetail7 = true;
                mealTimesList = (ArrayList) getSysMetadataParser().getFieldValue(mainModel, mealTimesName, false);
                detailSize = mealTimesList.size();
            }

            if (isDetail1 || isDetail2 || isDetail3 || isDetail4 || isDetail5 || isDetail6 || isDetail7) {

                //支持旧数据做判断
                if (isNew) {
                    for (int i = 0; i < detailSize; i++) {
                        String tmpTargetIds = isDetail1
                                ? (String) targetIdsList.get(i)
                                : (String) targetIds;
                        Date tmpStart = isDetail3 ? (Date) startTimeList.get(i)
                                : (Date) startTime;
                        Date tmpEnd = isDetail4 ? (Date) endTimeList.get(i)
                                : (Date) endTime;
                        //工时
                        Number tmpTime = isDetail5 ? (Number) ovtTimeList.get(i)
                                : (Number) ovtTime;
                        //加班处理方式
                        String fdOverHandle = isDetail6 ? (String) overHandleList.get(i) : overHandle;
                        //用餐时间
                        String fdMealTimes = isDetail7 ? (String) mealTimesList.get(i) : mealTimes;
                        if (tmpTargetIds == null || tmpTime == null
                                || tmpStart == null
                                || tmpEnd == null) {
                            logger.warn("字段值为空");
                            continue;
                        } else if (tmpStart.getTime() > tmpEnd.getTime()) {
                            logger.warn("开始时间超过结束时间");
                            continue;
                        } else {
                            float tmpTimeFloat = tmpTime.floatValue();
                            busList.add(getBusiness(tmpStart, tmpEnd,
                                    tmpTargetIds, mainModel.getFdId(), docSubject,
                                    leaveRule, tmpTimeFloat, model,fdOverHandle,fdMealTimes));
                        }
                    }
                } else {
                    for (int i = 0; i < detailSize; i++) {
                        String tmpTargetIds = isDetail1
                                ? (String) targetIdsList.get(i)
                                : (String) targetIds;
                        Date tmpDate = isDetail2 ? (Date) dateList.get(i)
                                : (Date) date;
                        Date tmpStart = isDetail3 ? (Date) startTimeList.get(i)
                                : (Date) startTime;
                        Date tmpEnd = isDetail4 ? (Date) endTimeList.get(i)
                                : (Date) endTime;
                        //加班处理方式
                        String fdOverHandle = isDetail6 ? (String) overHandleList.get(i) : overHandle;
                        //用餐时间
                        String fdMealTimes = isDetail7 ? (String) mealTimesList.get(i) : mealTimes;
                        if (tmpTargetIds == null || tmpDate == null
                                || tmpStart == null
                                || tmpEnd == null) {
                            logger.warn("字段值为空");
                            continue;
                        } else if (tmpStart.getTime() >= tmpEnd.getTime()) {
                            logger.warn("开始时间超过结束时间");
                            continue;
                        } else {
                            busList.add(getBusiness(
                                    AttendUtil.joinYMDandHMS(tmpDate,
                                            tmpStart),
                                    AttendUtil.joinYMDandHMS(tmpDate,
                                            tmpEnd),
                                    tmpTargetIds, mainModel.getFdId(), docSubject,
                                    leaveRule, 0.0f, model,fdOverHandle,fdMealTimes));
                        }
                    }
                }
            } else {

                //支持旧数据做判断
                if (isNew) {
                    if (targetIds == null || ovtTime == null || startTime == null
                            || endTime == null) {
                        logger.warn("字段值为空");
                        return null;
                    } else if (((Date) startTime).getTime() > ((Date) endTime)
                            .getTime()) {
                        logger.warn("开始时间超过结束时间");
                        return null;
                    } else {
                        busList.add(getBusiness((Date) startTime, (Date) endTime, (String) targetIds, mainModel.getFdId(),
                                docSubject, leaveRule, Float.valueOf(ovtTime.toString()), model,overHandle,mealTimes));
                    }
                } else {
                    if (targetIds == null || date == null || startTime == null
                            || endTime == null) {
                        logger.warn("字段值为空");
                        return null;
                    } else if (((Date) startTime).getTime() >= ((Date) endTime)
                            .getTime()) {
                        logger.warn("开始时间超过结束时间");
                        return null;
                    } else {
                        busList.add(getBusiness(
                                AttendUtil.joinYMDandHMS((Date) date,
                                        (Date) startTime),
                                AttendUtil.joinYMDandHMS((Date) date,
                                        (Date) endTime),
                                (String) targetIds, mainModel.getFdId(),
                                docSubject, leaveRule, 0.0f, model,overHandle,mealTimes));
                    }
                }
            }
            return busList;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取加班数据出错:" + e.getMessage());
            return null;
        }
    }

    private SysAttendBusiness getBusiness(Date fdBusStartTime,
                                          Date fdBusEndTime, String targetIds, String fdProcessId,
                                          String fdProcessName, SysTimeLeaveRule leaveRule, Float tmpTime,
                                          IBaseModel model, String fdOverHandle , String fdMealTimes)
            throws Exception {
        SysAttendBusiness sysAttendBusiness = new SysAttendBusiness();
        sysAttendBusiness.setFdBusStartTime(fdBusStartTime);
        //开始结束时间再同一天，结束时间是0点0分。则结束时间增加一天
        sysAttendBusiness.setFdBusEndTime(convertEndTime(fdBusEndTime,fdBusStartTime));

        sysAttendBusiness.setFdProcessId(fdProcessId);
        sysAttendBusiness.setFdProcessName(fdProcessName);
        sysAttendBusiness.setFdCountHour(tmpTime);
        sysAttendBusiness.setDocUrl(AttendUtil.getDictUrl(model, fdProcessId));

        List<SysOrgElement> users =getSysOrgCoreService()
                .findByPrimaryKeys(targetIds.split(";"));
        sysAttendBusiness.setFdTargets(users);
        sysAttendBusiness.setFdType(6);
        sysAttendBusiness.setDocCreateTime(new Date());
        if(StringUtil.isNotNull(fdOverHandle)){
            sysAttendBusiness.setFdOverHandle(fdOverHandle);
        }
        //设置用餐时间
        sysAttendBusiness.setFdMealTimes(fdMealTimes);
        if (leaveRule != null) {
            sysAttendBusiness
                    .setFdBusType(Integer.valueOf(leaveRule.getFdSerialNo()));
            sysAttendBusiness.setFdLeaveName(leaveRule.getFdName());
            sysAttendBusiness.setFdStatType(leaveRule.getFdStatType());
        }
        return sysAttendBusiness;
    }

    /**
     * 重新统计
     *
     * @param busList
     */
    private void restat(List<SysAttendBusiness> busList) {
        try {
            reStatistics(busList,multicaster);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("加班重新统计出错" + e.getMessage(), e);
        }
    }

    private List<SysOrgElement> getStatOrgList(List<SysAttendBusiness> busList)
            throws Exception {
        List<SysOrgElement> statOrgs = new ArrayList<SysOrgElement>();
        Set<SysOrgElement> orgs = new HashSet<SysOrgElement>();
        for (SysAttendBusiness bus : busList) {
            orgs.addAll(
                    getSysOrgCoreService().expandToPerson(bus.getFdTargets()));
        }
        statOrgs.addAll(orgs);
        return statOrgs;
    }

    private List<Date> getStatDateList(List<SysAttendBusiness> busList)
            throws Exception {
        List<Date> statDates = new ArrayList<Date>();
        for (SysAttendBusiness bus : busList) {
            Date startTime = AttendUtil.getDate(bus.getFdBusStartTime(), 0);
            Date endTime = AttendUtil.getDate(bus.getFdBusEndTime(), 0);
            Calendar cal = Calendar.getInstance();
            for (cal.setTime(startTime); cal.getTime()
                    .compareTo(endTime) <= 0; cal.add(Calendar.DATE, 1)) {
                statDates.add(cal.getTime());
            }
        }
        return statDates;
    }

    /**
     * 添加一个默认的加班明细。后台统计时候填充加班工时
     * @param bus
     * @throws Exception
     */
    protected void addOvertime(SysAttendBusiness bus) throws Exception {
        SysTimeLeaveRule leaveRule = AttendUtil
                .getLeaveRuleByType(bus.getFdBusType());
        List<SysOrgElement> elementList = bus.getFdTargets();
        if (elementList != null && !elementList.isEmpty()) {
            for (int i = 0; i < elementList.size(); i++) {
                SysTimeLeaveDetail leaveDetail = new SysTimeLeaveDetail();
                SysOrgElement element = elementList.get(i);
                SysOrgPerson person = (SysOrgPerson) getSysOrgCoreService().format(element);
                leaveDetail.setFdPerson(person);
                leaveDetail.setFdType(2);
                leaveDetail.setFdStartTime(bus.getFdBusStartTime());
                leaveDetail.setFdEndTime(bus.getFdBusEndTime());
                leaveDetail.setFdLeaveName(bus.getFdLeaveName());
                leaveDetail.setFdLeaveType(leaveRule != null ? leaveRule.getFdSerialNo() : null);
                leaveDetail.setFdStatType(3);// 目前加班只支持小时方式,不支持跨天
                leaveDetail.setFdOprType(1);
                leaveDetail.setFdOprStatus(0);
                leaveDetail.setFdReviewName(bus.getFdProcessName());
                leaveDetail.setFdReviewId(bus.getFdProcessId());
                leaveDetail.setDocCreateTime(new Date());
                leaveDetail.setDocCreator(person);
                leaveDetail.setAuthArea(bus.getAuthArea());
                getSysTimeLeaveDetailService().add(leaveDetail);
            }
        }
    }

}
