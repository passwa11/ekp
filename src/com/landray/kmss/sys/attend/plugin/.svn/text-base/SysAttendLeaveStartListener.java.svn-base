package com.landray.kmss.sys.attend.plugin;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.lbpm.engine.builder.NodeInstance;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.engine.task.NodeInstanceUtils;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountItemService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveDetailService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveLastAmountService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 流程提交人提交事件
 * 请假启动校验
 * 请假预扣减
 *
 * @author 王进府
 * @date 2022-02-21
 */
public class SysAttendLeaveStartListener extends SysAttendLeaveListener implements IEventListener {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendLeaveStartListener.class);

    private ISysTimeLeaveDetailService sysTimeLeaveDetailService;

    @Override
    public ISysTimeLeaveDetailService getSysTimeLeaveDetailService() {
        if (sysTimeLeaveDetailService == null) {
            sysTimeLeaveDetailService = (ISysTimeLeaveDetailService) SpringBeanUtil.getBean("sysTimeLeaveDetailService");
        }
        return sysTimeLeaveDetailService;
    }

    private ISysTimeLeaveAmountService sysTimeLeaveAmountService;

    public ISysTimeLeaveAmountService getSysTimeLeaveAmountService() {
        if (sysTimeLeaveAmountService == null) {
            sysTimeLeaveAmountService = (ISysTimeLeaveAmountService) SpringBeanUtil.getBean("sysTimeLeaveAmountService");
        }
        return sysTimeLeaveAmountService;
    }

    private ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService;

    public ISysTimeLeaveAmountItemService getSysTimeLeaveAmountItemService() {
        if (sysTimeLeaveAmountItemService == null) {
            sysTimeLeaveAmountItemService = (ISysTimeLeaveAmountItemService) SpringBeanUtil.getBean("sysTimeLeaveAmountItemService");
        }
        return sysTimeLeaveAmountItemService;
    }

    //上一周扣减情况
    private ISysTimeLeaveLastAmountService getSysTimeLeaveLastAmountService(){
        return (ISysTimeLeaveLastAmountService) SpringBeanUtil.getBean("sysTimeLeaveLastAmountService");
    }


    /**
     * 请假业务处理
     * 1、验证数据的正确
     * 2、验证流程数据是否重复
     * 3、把数据存储至数据库，并进行假期的扣减
     * 数据验证全部放在前面，只有全部验证通过后数据才开始入库，否则数据不允许进行update或者add，如果数据发生异常则需要把信息以通知的方式流程起草人
     *
     * @param execution
     * @param parameter
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2022/2/21 2:57 下午
     */
    @Override
    public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {

        //代表流程驳回以后，当前事件 则不在允许本次提交时执行。
        if (execution.getProcessInstance().getTempData().containsKey(PROCESS_FLAG_RETURN_KEY)) {
            return;
        }

        //根据流程状态来处理不同逻辑，只处理正常流程过来的数据
        String routeType = execution.getNodeInstance().getFdRouteType();
        IBaseModel mainModel = execution.getMainModel();
        if (NodeInstanceUtils.ROUTE_TYPE_NORMAL.equals(routeType)) {

            //正常流程流入
            if (mainModel instanceof IExtendDataModel) {
                //先清除待办。
                removeNotify(mainModel.getFdId(), UserUtil.getKMSSUser().getPerson());

                //验证该流程数据是否已经写入过，并且流程未结束或者已经结束
                List<SysAttendBusiness> list = getSysAttendBusinessService().findBusinessByProcessId(mainModel.getFdId(), AttendConstant.ATTEND_PROCESS_STATUS[1]);
                //如果存在该数据则说明提交过流程数据，则不处理（主要针对重启的流程）。
                if (CollectionUtils.isNotEmpty(list)) {
                    logger.warn("流程已结束忽略处理:{},parameter:{}" + mainModel.getFdId(), parameter);
                    sendNotifyEx(mainModel, "流程重启了");
                    //重置流程状态
                    resetFlowStatus(execution);
                    return;
                }

                //该流程对应正在进行中的流程列表。先清除。在重新新增
                List<SysAttendBusiness> businessIngList = getSysAttendBusinessService().findBusinessByProcessId(mainModel.getFdId(), AttendConstant.ATTEND_PROCESS_STATUS[0]);
                if (CollectionUtils.isNotEmpty(businessIngList)) {
                    for (SysAttendBusiness businessIng : businessIngList) {
                        //先设置为无效。
                        businessIng.setFdDelFlag(AttendConstant.ATTEND_PROCESS_BUSINESS_DEL_FLAG[1]);
                        businessIng.setFdOverFlag(AttendConstant.ATTEND_PROCESS_STATUS[1]);
                        getSysAttendBusinessService().update(businessIng);
                    }
                }

                JSONObject params = JSONObject.fromObject(parameter);
                //只有起草节点结束事件才解析，否则提示异常，发送待办
                if (!(execution.getEventSource() instanceof NodeInstance)) {
                    sendNotifyEx(mainModel, "流程数据配置不正确");
                    //重置流程状态
                    resetFlowStatus(execution);
                    return;
                }
                //获取流程中的配置数据，如果无法获取配置数据则抛异常，并通知流程起草人
                List<SysAttendBusiness> busList = null;
                try {
                    busList = getBusinessList(params, mainModel);
                } catch (Exception e) {
                    sendNotifyEx(mainModel, "请假流程数据配置不准确");
                    //重置流程状态
                    resetFlowStatus(execution);
                    return;
                }
                // 验证数据
                if (!checkLeaveData(parameter, busList, mainModel)) {
                    //重置流程状态
                    resetFlowStatus(execution);
                    return;
                }

                // 1.保存数据、状态标记为待处理考勤
                List<SysAttendBusiness> statBusiness = new ArrayList<>();
                try {
                    List<SysTimeLeaveDetail> sysTimeLeaveDetailList = new ArrayList<>();
                    for (SysAttendBusiness sysAttendBusiness : busList) {
                        SysTimeLeaveDetail leaveDetail = sysAttendBusinessTransformToSysTimeLeaveDetail(sysAttendBusiness);
                        sysAttendBusiness.setFdBusDetailId(leaveDetail.getFdId());
                        sysTimeLeaveDetailList.add(leaveDetail);
                    }

                    //排序，按照请假结束时间从低到高排序
                    Collections.sort(sysTimeLeaveDetailList, new Comparator<SysTimeLeaveDetail>() {
                        @Override
                        public int compare(SysTimeLeaveDetail temp1, SysTimeLeaveDetail temp2) {
                            return temp1.getFdEndTime().before(temp2.getFdEndTime()) ? -1 : 1;
                        }
                    });
                    //保存请假明细数据
                    for (SysTimeLeaveDetail sysTimeLeaveDetail : sysTimeLeaveDetailList) {
                        addLeaveDetailEx(sysTimeLeaveDetail);
                    }

                    for (SysAttendBusiness bus : busList) {
                        //验证是否有考勤组
                        //boolean isHave = checkUserHaveCategory(bus.getFdTargets(), bus.getFdBusStartTime());
                        //在考勤组中的人员，请假才统计。否则默认流程通过，仅仅是用于流程。不计算考勤
                        //if (isHave) {
                            statBusiness.add(bus);
                            //默认配合删除标识和流程标识来兼容历史数据.因为其他地方查询计算流程有效性都是通过fdDelFlag为0。在流程没结束之前。该数据不用于计算
                            bus.setFdDelFlag(AttendConstant.ATTEND_PROCESS_BUSINESS_DEL_FLAG[1]);
                            bus.setFdOverFlag(AttendConstant.ATTEND_PROCESS_STATUS[0]);
                            //保存流程表单数据
                            getSysAttendBusinessService().add(bus);
//                        } else {
//                            logger.warn("人员没有需要考勤的配置，忽略考勤处理:{},parameter:{}", mainModel.getFdId(), parameter);
//                        }
                    }
                } catch (Exception e) {
                    sendNotifyEx(mainModel, "请假流程报错保存数据时报错");
                    //重置流程状态
                    resetFlowStatus(execution);
                    logger.error(e.getMessage(),e);
                    return;
                }
                // 2.存储流程标识
                if (CollectionUtils.isNotEmpty(statBusiness)) {
                    execution.getProcessParameters().addInstanceParamValue(execution.getProcessInstance(), PROCESS_FLAG_KEY, PROCESS_FLAG_RUN_VALUE);
                }
            }
        }
    }

    /**
     * @param mainModel
     * @param tipMsg
     * @description: 发送待办
     * @return: void
     * @author: wangjf
     * @time: 2022/3/3 4:56 下午
     */
    private void sendNotifyEx(IBaseModel mainModel, String tipMsg) throws Exception {
        SysAttendBusiness tempBusiness = new SysAttendBusiness();
        tempBusiness.setFdProcessId(mainModel.getFdId());
        tempBusiness.setFdProcessName((String) getSysMetadataParser().getFieldValue(mainModel, "docSubject", false));
        tempBusiness.setDocUrl(AttendUtil.getDictUrl(mainModel, mainModel.getFdId()));
        sendNotify(tempBusiness, UserUtil.getKMSSUser().getPerson(),
                String.format("%s_%s", tempBusiness.getFdProcessName(), tipMsg), ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip1"));
    }

    /**
     * 假期额度扣减
     * @param leaveDetail
     * @throws Exception
     */
    private void addLeaveDetailEx(SysTimeLeaveDetail leaveDetail) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Integer nowYear = calendar.get(Calendar.YEAR);
        //把当前年度和上一年的假期数据查询出来，不存在上上年度的数据
        SysTimeLeaveAmountItem currentYearAmount = null;
        currentYearAmount = getSysTimeLeaveAmountService().getLeaveAmountItem(nowYear, leaveDetail.getFdPerson().getFdId(), leaveDetail.getFdLeaveType());
        if (currentYearAmount == null) {
            currentYearAmount = getSysTimeLeaveAmountService().getLeaveAmountItem(nowYear - 1, leaveDetail.getFdPerson().getFdId(), leaveDetail.getFdLeaveType());
        }
        //假期可用
        if (currentYearAmount != null) {
            //假期类型
            SysTimeLeaveRule leaveRule = getSysTimeLeaveDetailService().getLeaveaRule(leaveDetail.getFdLeaveType());
            if (Boolean.TRUE.equals(leaveRule.getFdIsAmount())) {
                getSysTimeLeaveDetailService().updateAmountItem(currentYearAmount,leaveDetail.getFdLeaveTime(),leaveDetail,leaveRule,false);
                //请假预扣设置状态为5
                leaveDetail.setFdOprDesc("请假预扣假期");
                leaveDetail.setFdOprStatus(5);
            }else{
                leaveDetail.setFdOprDesc(ResourceUtil.getString("sys-time:sysTimeLeaveDetail.reason.notSetAmount"));
                leaveDetail.setFdOprStatus(2);
            }
        } else {
            leaveDetail.setFdOprDesc("未开启假期额度");
            leaveDetail.setFdOprStatus(2);
        }
        leaveDetail.setFdCanUpdateAttend(false);
        getSysTimeLeaveDetailService().add(leaveDetail);
    }
    /*
    *//**
     * @param leaveDetail
     * @description: 请假明细扩展
     * @return: void
     * @author: wangjf
     * @time: 2022/2/23 10:30 上午
     *//*
    private void addLeaveDetailEx(SysTimeLeaveDetail leaveDetail) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Integer nowYear = calendar.get(Calendar.YEAR);
        //把当前年度和上一年的假期数据查询出来，不存在上上年度的数据
        SysTimeLeaveAmountItem currentYearAmount = null;
        currentYearAmount = getSysTimeLeaveAmountService().getLeaveAmountItem(nowYear, leaveDetail.getFdPerson().getFdId(), leaveDetail.getFdLeaveType());
        if (currentYearAmount == null) {
            currentYearAmount = getSysTimeLeaveAmountService().getLeaveAmountItem(nowYear - 1, leaveDetail.getFdPerson().getFdId(), leaveDetail.getFdLeaveType());
        }
        int currentYearRestDay = 0;
        int previousYearRestDay = 0;
        Date fdLastValidDate = null;
        Date fdValidDate = null;
        //假期可用
        if (currentYearAmount != null) {
            if (Boolean.TRUE.equals(currentYearAmount.getFdIsAvail())) {
                currentYearRestDay = currentYearAmount.getFdRestDayMin() == null ? 0 : currentYearAmount.getFdRestDayMin();
            }
            if (Boolean.TRUE.equals(currentYearAmount.getFdIsLastAvail())) {
                previousYearRestDay = currentYearAmount.getFdLastRestDayMin() == null ? 0 : currentYearAmount.getFdLastRestDayMin();
            }
            fdLastValidDate =currentYearAmount.getFdLastValidDate() !=null ?AttendUtil.getDate(currentYearAmount.getFdLastValidDate(),0):null;
            fdValidDate =currentYearAmount.getFdValidDate() !=null ?AttendUtil.getDate(currentYearAmount.getFdValidDate(),0):null;
            Date tempEndTime =AttendUtil.getDate(leaveDetail.getFdEndTime(),0);
            int tempLeaveTime = leaveDetail.getFdTotalTime().intValue();
            int lastUsedDay = currentYearAmount.getFdLastUsedDayMin()==null? 0:currentYearAmount.getFdLastUsedDayMin().intValue();
            int usedDay = currentYearAmount.getFdUsedDayMin() ==null?0:currentYearAmount.getFdUsedDayMin().intValue();

            // 按请假时间进行排序，把小的时间放在前面，这样可以从去年的假期进行扣减
            //上一年存在假期并且请假时间在上一年过期范围内
            if (previousYearRestDay > 0F && fdLastValidDate != null && tempEndTime.getTime() <= fdLastValidDate.getTime()) {
                if (tempLeaveTime == previousYearRestDay) {
                    //如果上一年度假期与请假时间相同，则设置上一年度的假期额度为0
                    leaveDetail.setFdPreviousYearAmount((float)tempLeaveTime);
                    leaveDetail.setFdCurrentYearAmount(0F);
                    currentYearAmount.setFdLastRestDayMin(0);
                    //已使用天数
                    currentYearAmount.setFdLastUsedDayMin(lastUsedDay + tempLeaveTime);
                } else if (tempLeaveTime <= previousYearRestDay) {
                    leaveDetail.setFdPreviousYearAmount((float)tempLeaveTime);
                    leaveDetail.setFdCurrentYearAmount(0F);
                    currentYearAmount.setFdLastRestDayMin(previousYearRestDay - tempLeaveTime);
                    currentYearAmount.setFdLastUsedDayMin(lastUsedDay + tempLeaveTime);
                } else if (leaveDetail.getFdLeaveTime() > previousYearRestDay) {
                    //如果上一年度的假期不够则对比本年度的假期，并且进行扣减
                    if (currentYearRestDay > 0F && currentYearAmount.getFdValidDate() != null && leaveDetail.getFdEndTime().before(currentYearAmount.getFdValidDate())) {
                        leaveDetail.setFdPreviousYearAmount((float)previousYearRestDay);
                        //计算后的请假天数
                        int leaveDays=(tempLeaveTime - previousYearRestDay);
                        leaveDetail.setFdCurrentYearAmount((float)leaveDays);
                        currentYearAmount.setFdLastRestDayMin(0);
                        currentYearAmount.setFdRestDayMin(currentYearRestDay - leaveDays);
                        currentYearAmount.setFdUsedDayMin(usedDay + leaveDays);
                        currentYearAmount.setFdLastUsedDayMin(lastUsedDay + previousYearRestDay);
                    }
                }
            } else if (currentYearRestDay > 0F && fdValidDate != null  && tempEndTime.getTime() <= fdValidDate.getTime()) {

                //上一年度假期不够或者请假时间不在上一年度的假期范围，则需要使用本年度的假期
                if (currentYearRestDay == tempLeaveTime) {
                    leaveDetail.setFdPreviousYearAmount(0F);
                    leaveDetail.setFdCurrentYearAmount((float) tempLeaveTime);
                    currentYearAmount.setFdRestDayMin(0);
                    currentYearAmount.setFdUsedDayMin(usedDay + tempLeaveTime);
                } else if (currentYearRestDay >= tempLeaveTime) {
                    leaveDetail.setFdPreviousYearAmount(0F);
                    leaveDetail.setFdCurrentYearAmount((float)tempLeaveTime);
                    currentYearAmount.setFdRestDayMin(currentYearRestDay - tempLeaveTime);
                    currentYearAmount.setFdUsedDayMin(usedDay + tempLeaveTime);
                }
            }
            getSysTimeLeaveAmountItemService().update(currentYearAmount);
            leaveDetail.setSysTimeLeaveAmountItemId(currentYearAmount.getFdId());
        }
        leaveDetail.setFdOprDesc("请假预扣假期");
        //请假预扣设置状态为5
        leaveDetail.setFdOprStatus(5);
        leaveDetail.setFdCanUpdateAttend(false);
        getSysTimeLeaveDetailService().add(leaveDetail);
        //如果存在上周期请假的需要把数据写入 SysTimeLeaveLastAmount表中，并且一条记录最多是一天，如果是多天的情况则需要分离成多条数据
        if(leaveDetail.getFdPreviousYearAmount() != null && leaveDetail.getFdPreviousYearAmount() > 0F) {
            getSysTimeLeaveDetailService().saveLeaveLastAmount(leaveDetail);
        }
    }*/

    /**
     * 验证请假流程数据
     *
     * @param parameter
     * @param busList
     * @param mainModel
     * @description:
     * @return: boolean
     * @author: wangjf
     * @time: 2022/2/22 3:35 下午
     */
    private boolean checkLeaveData(String parameter, List<SysAttendBusiness> busList, IBaseModel mainModel) throws Exception {
        String docSubject = (String) getSysMetadataParser().getFieldValue(mainModel, "docSubject", false);
        String docUrl = AttendUtil.getDictUrl(mainModel, mainModel.getFdId());
        //明细数据为空
        if (CollectionUtils.isEmpty(busList)) {
            logger.warn("请假流程明细数据不准确!parameter:{}", parameter);
            sendNotifyEx(mainModel, "请假流程明细数据不准确");
            return false;
        }
        //验证假期类型
        boolean checkBusType = true;
        for (SysAttendBusiness sysAttendBusiness : busList) {
            if (sysAttendBusiness.getFdBusType() != null) {
                SysTimeLeaveRule sysTimeLeaveRule = AttendUtil.getLeaveRuleByType(sysAttendBusiness.getFdBusType());
                if (sysTimeLeaveRule == null || Boolean.FALSE.equals(sysTimeLeaveRule.getFdIsAvailable())) {
                    logger.warn("请假流程中请假类型无法找到匹配规则!fdBussType:{}", sysAttendBusiness.getFdBusType());
                    checkBusType = false;
                    sysAttendBusiness.setDocUrl(docUrl);
                    sendNotify(sysAttendBusiness, UserUtil.getKMSSUser().getPerson(), String.format("%s_%s", docSubject, "请假流程中请假类型无法找到匹配规则"),
                            ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip1"));
                    break;
                }
            }
        }
        if (!checkBusType) {
            logger.warn("请假流程中请假类型无法找到匹配规则!parameter:{}", parameter);
            return false;
        }

        //验证请假时间的重复性
        boolean checkRepeat = true;
        for (SysAttendBusiness sysAttendBusiness : busList) {
            String result = checkAttendLeaveRepeat(sysAttendBusiness);
            if (StringUtil.isNotNull(result)) {
                checkRepeat = false;
                sysAttendBusiness.setDocUrl(docUrl);
                sendNotify(sysAttendBusiness, UserUtil.getKMSSUser().getPerson(), "您提交的流程时间与"+result+"_有重复，请核对后再提交!",
                        ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip1"));
                break;
            }
        }
        if (!checkRepeat) {
            logger.warn("请假流程中请假时间之前已经请过!parameter:{}", parameter);
            return false;
        }
        //验证假期数据的有效性
        boolean checkLeaveEffect = true;
        for (SysAttendBusiness sysAttendBusiness : busList) {
            SysTimeLeaveDetail leaveDetail = sysAttendBusinessTransformToSysTimeLeaveDetail(sysAttendBusiness);
            //验证假期是否有效，并且请假转换成天数
            if (!getSysTimeLeaveDetailService().checkLeaveEffect(leaveDetail)) {
                checkLeaveEffect = false;
                sysAttendBusiness.setDocUrl(docUrl);
                sendNotify(sysAttendBusiness, UserUtil.getKMSSUser().getPerson(), String.format("%s_%s", docSubject, "请假流程中请假时间假期数据无效"),
                        ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip1"));
                break;
            }

            SysTimeLeaveRule sysTimeLeaveRule = AttendUtil.getLeaveRuleByType(sysAttendBusiness.getFdBusType());
            //检查是否开启额度限制，如果没有开启额度限制的话直接下一个循环，如果开启了额度限制的话则验证额度是否够
            if (!Boolean.TRUE.equals(sysTimeLeaveRule.getFdIsAmount())) {
                continue;
            }
            //把请假人员全部展开
            List<SysOrgElement> targets = getTargetsFromAttendBusiness(sysAttendBusiness);
            if (CollectionUtils.isEmpty(targets)) {
                checkLeaveEffect = false;
                // 请假人为空，流程不正确
                sysAttendBusiness.setDocUrl(docUrl);
                sendNotify(sysAttendBusiness, UserUtil.getKMSSUser().getPerson(), String.format("%s_%s", docSubject, "请假流程中请假存在为空的情况"),
                        ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip1"));
                break;
            }

            //把人员请假信息进行合并，验证假期是否能满足请假需求
            for (SysOrgElement target : targets) {
                boolean isOk = checkLeaveAmount(target.getFdId(),leaveDetail.getFdLeaveType(),leaveDetail,sysTimeLeaveRule);
                if(!isOk){
                    sendNotify(sysAttendBusiness, UserUtil.getKMSSUser().getPerson(), String.format("%s_%s", docSubject, "您提交的请假流程中的假期,额度不足"),
                            ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip1"));
                    logger.warn("请假流程中额度不足!人员id:{},请假类型:{}", target.getFdId(),leaveDetail.getFdLeaveType());
                    return false;
                }
            }
        }
        if (!checkLeaveEffect) {
            logger.warn("请假流程中请假存在为空或者假期数据无效!parameter:{}", parameter);
            return false;
        }
        return true;
    }

    /**
     * 验证假期有效额度
     * @param personId     请假人ID
     * @param leaveType    假期类型
     * @param leaveDetail 请假详情
     * @param leaveRule 请假详情
     * @description: 验证请假额度是否够请假
     * @return: boolean true说明假期够 , false说明超出假期
     * @author: 王京
     * @time: 2022/08/21
     */
    private boolean checkLeaveAmount(String personId, String leaveType, SysTimeLeaveDetail leaveDetail,SysTimeLeaveRule leaveRule) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Integer nowYear = calendar.get(Calendar.YEAR);
        //把当前年度和上一年的假期数据查询出来，不存在上上年度的数据
        SysTimeLeaveAmountItem amountItem = getSysTimeLeaveAmountService().getLeaveAmountItem(nowYear, personId, leaveType);
        if (amountItem == null) {
            amountItem = getSysTimeLeaveAmountService().getLeaveAmountItem(nowYear - 1, personId, leaveType);
        }
        boolean checkLeave = true;
        //假期可用
        if (amountItem != null) {
            //有效期内的上周期的剩余额度
            Float lastRestDay = amountItem.getValidLastRestDay(leaveDetail,leaveRule);
            Float restday = amountItem.getValidRestDay(leaveDetail, leaveRule);
            //上周期和本有效时间范围内的时长大于等于本次扣减的时长
            if (lastRestDay.floatValue() + restday.floatValue() >= leaveDetail.getFdLeaveTime()) {
                checkLeave =true;
            }else{
                checkLeave =false;
            }
        }
        return checkLeave;
    }
    /**
     * @param personId     请假人ID
     * @param leaveType    假期类型
     * @param leaveDayList 请假详情
     * @description: 验证请假额度是否够请假
     * @return: boolean true说明假期够 , false说明超出假期
     * @author: wangjf
     * @time: 2022/2/22 1:42 下午
     *//*
    private boolean checkLeaveAmount(String personId, String leaveType, List<LeaveDay> leaveDayList) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Integer nowYear = calendar.get(Calendar.YEAR);
        //把当前年度和上一年的假期数据查询出来，不存在上上年度的数据
        SysTimeLeaveAmountItem currentYearAmount = null;currentYearAmount = getSysTimeLeaveAmountService().getLeaveAmountItem(nowYear, personId, leaveType);
        if (currentYearAmount == null) {
            currentYearAmount = getSysTimeLeaveAmountService().getLeaveAmountItem(nowYear - 1, personId, leaveType);
        }
        Float currentYearRestDay = 0F;
        Float previousYearRestDay = 0F;
        boolean checkLeave = true;
        //假期可用
        if (currentYearAmount != null) {
            if (Boolean.TRUE.equals(currentYearAmount.getFdIsAvail())) {
                currentYearRestDay = currentYearAmount.getFdRestDayMin() == null ? 0F : currentYearAmount.getFdRestDayMin();
                currentYearRestDay = SysTimeUtil.roundString(currentYearRestDay,3);
            }
            if (Boolean.TRUE.equals(currentYearAmount.getFdIsLastAvail())) {
                previousYearRestDay = currentYearAmount.getFdLastRestDayMin() == null ? 0F : currentYearAmount.getFdLastRestDayMin();
                previousYearRestDay = SysTimeUtil.roundString(previousYearRestDay,3);
            }
            // 按请假时间进行排序，把小的时间放在前面，这样可以从去年的假期进行扣减
            Collections.sort(leaveDayList);

            Date fdLastValidDate =currentYearAmount.getFdLastValidDate() !=null ?AttendUtil.getDate(currentYearAmount.getFdLastValidDate(),0):null;
            Date fdValidDate =currentYearAmount.getFdValidDate() !=null ?AttendUtil.getDate(currentYearAmount.getFdValidDate(),0):null;

            for (LeaveDay leaveDay : leaveDayList) {
                Float tempLeaveDay = SysTimeUtil.roundString(leaveDay.getLeaveDay(),3);
                Date tempEndTime =AttendUtil.getDate(leaveDay.getEndDate(),0);
                //上一年存在假期并且请假时间在上一年过期范围内
                if (previousYearRestDay > 0F && fdLastValidDate != null && tempEndTime.getTime() <=fdLastValidDate.getTime()) {
                    if (tempLeaveDay.equals(previousYearRestDay)) {
                        //如果上一年度假期与请假时间相同，则设置上一年度的假期额度为0
                        previousYearRestDay = 0F;
                    } else if (tempLeaveDay > previousYearRestDay) {
                        //如果上一年度的假期不够则对比本年度的假期，并且进行扣减
                        if (currentYearRestDay > 0F && currentYearAmount.getFdValidDate() != null && leaveDay.getEndDate().before(currentYearAmount.getFdValidDate())) {
                            currentYearRestDay = currentYearRestDay - (tempLeaveDay - previousYearRestDay);
                            previousYearRestDay = 0F;
                        } else {
                            checkLeave = false;
                            break;
                        }
                    } else if (tempLeaveDay < previousYearRestDay) {
                        previousYearRestDay = previousYearRestDay - tempLeaveDay;
                    }
                } else if (currentYearRestDay > 0F && fdValidDate != null && tempEndTime.getTime() <=fdValidDate.getTime()) {
                    //上一年度假期不够或者请假时间不在上一年度的假期范围，则需要使用本年度的假期
                    if (currentYearRestDay.equals(tempLeaveDay)) {
                        currentYearRestDay = 0F;
                    } else if (currentYearRestDay > tempLeaveDay) {
                        currentYearRestDay = currentYearRestDay - tempLeaveDay;
                    } else {
                        checkLeave = false;
                        break;
                    }
                } else {
                    checkLeave = false;
                    break;
                }
            }
        }
        if (currentYearRestDay.intValue() ==0 && previousYearRestDay.intValue()==0) {
            return false;
        }
        return checkLeave;
    }*/

    class LeaveDay implements Comparable<LeaveDay> {
        private Date endDate;
        private Float leaveDay;

        public LeaveDay(Date endDate, Float leaveDay) {
            this.endDate = endDate;
            this.leaveDay = leaveDay;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public Float getLeaveDay() {
            return leaveDay;
        }

        public void setLeaveDay(Float leaveDay) {
            this.leaveDay = leaveDay;
        }

        @Override
        public int compareTo(LeaveDay leaveDay) {
            //把时间小的方在前面
            return leaveDay.getEndDate().before(this.endDate) ? -1 : 1;
        }
    }

}
