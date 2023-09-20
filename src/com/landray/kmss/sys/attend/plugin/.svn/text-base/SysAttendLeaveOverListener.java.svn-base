package com.landray.kmss.sys.attend.plugin;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.lbpm.engine.manager.ProcessParameters;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.engine.persistence.AccessManager;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpm.engine.service.Event_BeforeProcessDelete;
import com.landray.kmss.sys.lbpmservice.operation.admin.LbpmAdminJumpEvent;
import com.landray.kmss.sys.lbpmservice.operation.handler.LbpmHandlerRefuseEvent;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveLastAmount;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountItemService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveDetailService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveLastAmountService;
import com.landray.kmss.util.SpringBeanUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.Date;
import java.util.List;

/**
 * 流程结束事件
 * 请假流程数据处理
 *
 * @author 王进府
 * @date 2022-02-22
 */
public class SysAttendLeaveOverListener extends SysAttendListenerCommonImp implements IEventListener, IEventMulticasterAware, ApplicationListener<ApplicationEvent> {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendLeaveOverListener.class);

    private ISysTimeLeaveDetailService sysTimeLeaveDetailService;

    public ISysTimeLeaveDetailService getSysTimeLeaveDetailService() {
        if (sysTimeLeaveDetailService == null) {
            sysTimeLeaveDetailService = (ISysTimeLeaveDetailService) SpringBeanUtil.getBean("sysTimeLeaveDetailService");
        }
        return sysTimeLeaveDetailService;
    }

    private ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService;

    public ISysTimeLeaveAmountItemService getSysTimeLeaveAmountItemService() {
        if (sysTimeLeaveAmountItemService == null) {
            sysTimeLeaveAmountItemService = (ISysTimeLeaveAmountItemService) SpringBeanUtil.getBean("sysTimeLeaveAmountItemService");
        }
        return sysTimeLeaveAmountItemService;
    }

    private ISysTimeLeaveLastAmountService getSysTimeLeaveLastAmountService(){
        return (ISysTimeLeaveLastAmountService) SpringBeanUtil.getBean("sysTimeLeaveLastAmountService");
    }

    /**
     * 监听出差的流程结束时间。
     * 包含提交人作废，提交人撤回，流程结束，流程作废
     *
     * @param execution
     * @param parameter 前端侦听器配置信息
     * @throws Exception
     */
    @Override
    public void handleEvent(EventExecutionContext execution, String parameter)
            throws Exception {
        //根据流程状态来处理不同逻辑
        IBaseModel mainModel = execution.getMainModel();
        //流程标识
        String processFlag = execution.getProcessParameters().getInstanceParamValue(execution.getProcessInstance(), PROCESS_FLAG_KEY);
        //非考勤标识的流程。不支持
        if (!PROCESS_FLAG_RUN_VALUE.equals(processFlag) && !PROCESS_FLAG_ERROR_VALUE.equals(processFlag)) {
            //流程在考勤模块不存在，则不继续执行
            return;
        }


        Object object = PropertyUtils.getProperty(mainModel, "docCreator");
        //将本流程中的业务对象待办清除。
        if (object != null) {
            removeNotify(mainModel.getFdId(), (SysOrgPerson) object);
        }
        if (!this.checkProcessIsHave(mainModel.getFdId(), AttendConstant.ATTEND_PROCESS_TYPE[5])) {
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("开始执行流程结束事件，流程ID：{}", mainModel.getFdId());
        }
        //该流程对应正在进行中的流程列表。先清除。在重新新增
        List<SysAttendBusiness> businessIngList = getSysAttendBusinessService().findBusinessByProcessId(mainModel.getFdId(), AttendConstant.ATTEND_PROCESS_STATUS[0]);

        //请假驳回到起草事件，需要归还假期
        if (execution.getEvent() instanceof LbpmHandlerRefuseEvent || execution.getEvent() instanceof LbpmAdminJumpEvent) {
            String node = null;
            if (execution.getEvent() instanceof LbpmHandlerRefuseEvent) {
                LbpmHandlerRefuseEvent lbpmHandlerRefuseEvent = (LbpmHandlerRefuseEvent) execution.getEvent();
                node = lbpmHandlerRefuseEvent.getJumpToNodeId();
            } else if (execution.getEvent() instanceof LbpmAdminJumpEvent) {
                LbpmAdminJumpEvent lbpmAdminJumpEvent = (LbpmAdminJumpEvent) execution.getEvent();
                node = lbpmAdminJumpEvent.getJumpToNodeId();
            }
            if ("N2".equals(node)) {
                if (CollectionUtils.isNotEmpty(businessIngList)) {
                    for (SysAttendBusiness businessIng : businessIngList) {
                        businessIng.setFdDelFlag(AttendConstant.ATTEND_PROCESS_BUSINESS_DEL_FLAG[1]);
                        businessIng.setFdOverFlag(AttendConstant.ATTEND_PROCESS_STATUS[1]);
                        getSysAttendBusinessService().update(businessIng);
                        if (logger.isDebugEnabled()) {
                            logger.debug("流程作废撤销，流程ID：{},考勤流程业务id:{}", mainModel.getFdId(), businessIng.getFdId());
                        }
                    }
                }
                //需要更新请假明细数据，并且归还假期
                List<SysTimeLeaveDetail> sysTimeLeaveDetailList = getSysTimeLeaveDetailList(mainModel.getFdId());
                if (CollectionUtils.isNotEmpty(sysTimeLeaveDetailList)) {
                    for (SysTimeLeaveDetail sysTimeLeaveDetail : sysTimeLeaveDetailList) {
                        updateSysTimeLeaveAmountItem(sysTimeLeaveDetail);
                    }
                }
                return;
            }
        }


        //docStatus =10是提交状态，代表撤回，00 代表是作废，30代表结束。
        String docStatus = execution.getExecuteParameters().getExpectMainModelStatus();

        // 撤销 、作废  需要归还假期
        if (SysDocConstant.DOC_STATUS_DRAFT.equals(docStatus) || SysDocConstant.DOC_STATUS_DISCARD.equals(docStatus)) {
            if (CollectionUtils.isNotEmpty(businessIngList)) {
                //草稿状态、撤回、删除流程
                for (SysAttendBusiness businessIng : businessIngList) {
                    businessIng.setFdDelFlag(AttendConstant.ATTEND_PROCESS_BUSINESS_DEL_FLAG[1]);
                    businessIng.setFdOverFlag(AttendConstant.ATTEND_PROCESS_STATUS[1]);
                    getSysAttendBusinessService().update(businessIng);
                    if (logger.isDebugEnabled()) {
                        logger.debug("流程作废撤销，流程ID：{},考勤流程业务id:{}", mainModel.getFdId(), businessIng.getFdId());
                    }
                }
            }
            //需要更新请假明细数据，并且归还假期
            List<SysTimeLeaveDetail> sysTimeLeaveDetailList = getSysTimeLeaveDetailList(mainModel.getFdId());
            if (CollectionUtils.isNotEmpty(sysTimeLeaveDetailList)) {
                for (SysTimeLeaveDetail sysTimeLeaveDetail : sysTimeLeaveDetailList) {
                    updateSysTimeLeaveAmountItem(sysTimeLeaveDetail);
                }
            }
        } else {
            //发布状态，流程结束
            //需要更新请假明细数据，并且把SysAttendBusiness中的数据进行更新
            List<SysTimeLeaveDetail> sysTimeLeaveDetailList = getSysTimeLeaveDetailList(mainModel.getFdId());
            if (CollectionUtils.isNotEmpty(sysTimeLeaveDetailList)) {
                for (SysTimeLeaveDetail sysTimeLeaveDetail : sysTimeLeaveDetailList) {
                    updateSysTimeLeaveDetail(sysTimeLeaveDetail);
                }
            }
            if (CollectionUtils.isNotEmpty(businessIngList)) {
                //发布状态，流程结束
                for (SysAttendBusiness businessIng : businessIngList) {
                    businessIng.setFdDelFlag(AttendConstant.ATTEND_PROCESS_BUSINESS_DEL_FLAG[0]);
                    businessIng.setFdOverFlag(AttendConstant.ATTEND_PROCESS_STATUS[1]);
                    getSysAttendBusinessService().update(businessIng);
                    if (logger.isDebugEnabled()) {
                        logger.debug("流程完成，流程ID：{},考勤流程业务id:{}", mainModel.getFdId(), businessIng.getFdId());
                    }
                    //流程创建时间 因为有统计是根据此时间 来统计。这里重新赋值。最理想的是加个修改时间字段......
                    businessIng.setDocCreateTime(new Date());
                    //更新打卡记录
                    updateSysAttendMainByLeaveBis(businessIng);
                }
                //执行统计
                reStatistics(businessIngList, this.multicaster);

            }
        }
    }

    /**
     * @param reviewId 流程ID
     * @description: 通过流程ID获取
     * @return: java.util.List<com.landray.kmss.sys.time.model.SysTimeLeaveDetail>
     * @author: wangjf
     * @time: 2022/2/23 2:19 下午
     */
    private List<SysTimeLeaveDetail> getSysTimeLeaveDetailList(String reviewId) throws Exception {
        List<SysTimeLeaveDetail> list = getSysTimeLeaveDetailService().findList("sysTimeLeaveDetail.fdReviewId='" + reviewId + "'", "");
        return list;
    }

    /**
     * @param sysTimeLeaveDetail
     * @description: 更新请假数据，把预扣减标志变为成功扣减标志，并且允许更新考勤
     * @return: void
     * @author: wangjf
     * @time: 2022/2/23 2:26 下午
     */
    private void updateSysTimeLeaveDetail(SysTimeLeaveDetail sysTimeLeaveDetail) throws Exception {
        if(sysTimeLeaveDetail.getFdOprStatus()==5) {
            //预减的额度。则成功，其他的不变
            sysTimeLeaveDetail.setFdOprStatus(1);
        }
        sysTimeLeaveDetail.setFdIsUpdateAttend(true);
        sysTimeLeaveDetail.setFdOprDesc(sysTimeLeaveDetail.getFdOprDesc() + "->流程结束执行");
        getSysTimeLeaveDetailService().update(sysTimeLeaveDetail);
    }

    /**
     * 假期归还
     *
     * @param sysTimeLeaveDetail
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2022/2/23 3:35 下午
     */
    private void updateSysTimeLeaveAmountItem(SysTimeLeaveDetail sysTimeLeaveDetail) throws Exception {
        SysTimeLeaveAmountItem sysTimeLeaveAmountItem = (SysTimeLeaveAmountItem) getSysTimeLeaveAmountItemService().
                findByPrimaryKey(sysTimeLeaveDetail.getSysTimeLeaveAmountItemId(), SysTimeLeaveAmountItem.class, true);
        if (sysTimeLeaveAmountItem != null) {
            if (!AttendUtil.isEquals(sysTimeLeaveDetail.getFdPreviousYearAmount(), 0F)) {
                sysTimeLeaveAmountItem.setFdLastRestDay(sysTimeLeaveAmountItem.getFdLastRestDay() + sysTimeLeaveDetail.getFdPreviousYearAmount());
                sysTimeLeaveAmountItem.setFdLastUsedDay(sysTimeLeaveAmountItem.getFdLastUsedDay() - sysTimeLeaveDetail.getFdPreviousYearAmount());
            }
            if (!AttendUtil.isEquals(sysTimeLeaveDetail.getFdCurrentYearAmount(), 0F)) {
                sysTimeLeaveAmountItem.setFdRestDay(sysTimeLeaveAmountItem.getFdRestDay() + sysTimeLeaveDetail.getFdCurrentYearAmount());
                sysTimeLeaveAmountItem.setFdUsedDay(sysTimeLeaveAmountItem.getFdUsedDay() - sysTimeLeaveDetail.getFdCurrentYearAmount());
            }
            getSysTimeLeaveAmountItemService().update(sysTimeLeaveAmountItem);
        }
        //删除上周期扣减的额度情况
        List<SysTimeLeaveLastAmount> userLeaveList = getSysTimeLeaveLastAmountService().findUserLeaveList(sysTimeLeaveDetail.getFdPerson().getFdId(), sysTimeLeaveDetail.getFdId());
        if(CollectionUtils.isNotEmpty(userLeaveList)){
            for (SysTimeLeaveLastAmount sysTimeLeaveLastAmount : userLeaveList) {
                getSysTimeLeaveLastAmountService().delete(sysTimeLeaveLastAmount);
            }
        }

        getSysTimeLeaveDetailService().delete(sysTimeLeaveDetail);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        Object obj = event.getSource();
        if (!(obj instanceof LbpmProcess)) {
            return;
        }
        if (event instanceof Event_BeforeProcessDelete) {
            LbpmProcess subProcess = (LbpmProcess) event.getSource();
            String modelId = subProcess.getFdId();
            //流程实例ID 是否在考勤模块存在
            try {
                AccessManager accessManager = getAccessManager();
                ProcessParameters processParameters = new ProcessParameters(accessManager, subProcess);
                String processFlag = processParameters.getInstanceParamValue(subProcess, PROCESS_FLAG_KEY);
                if (!PROCESS_FLAG_RUN_VALUE.equals(processFlag) && !PROCESS_FLAG_ERROR_VALUE.equals(processFlag)) {
                    //流程在考勤模块不存在，则不继续执行
                    return;
                }
                //将本流程中的业务对象待办清除。
                if (subProcess.getFdCreator() != null) {
                    removeNotify(modelId, (SysOrgPerson) subProcess.getFdCreator());
                }
                if (!checkProcessIsHave(modelId, AttendConstant.ATTEND_PROCESS_TYPE[5])) {
                    logger.debug("开始执行流程结束事件，流程ID：{} 考勤无该流程数据", modelId);
                    return;
                }
                //该流程对应正在进行中的流程列表。先清除。在重新新增
                List<SysAttendBusiness> businessIngList = getSysAttendBusinessService().findBusinessByProcessId(modelId, AttendConstant.ATTEND_PROCESS_STATUS[0]);
                for (SysAttendBusiness businessIng : businessIngList) {
                    businessIng.setFdDelFlag(AttendConstant.ATTEND_PROCESS_BUSINESS_DEL_FLAG[1]);
                    businessIng.setFdOverFlag(AttendConstant.ATTEND_PROCESS_STATUS[1]);
                    getSysAttendBusinessService().update(businessIng);
                    if (logger.isDebugEnabled()) {
                        logger.debug("流程删除，流程ID：{},考勤流程业务id:{}", modelId, businessIng.getFdId());
                    }
                }
                //需要更新请假明细数据，并且归还假期
                List<SysTimeLeaveDetail> sysTimeLeaveDetailList = getSysTimeLeaveDetailList(modelId);
                if (CollectionUtils.isNotEmpty(sysTimeLeaveDetailList)) {
                    for (SysTimeLeaveDetail sysTimeLeaveDetail : sysTimeLeaveDetailList) {
                        updateSysTimeLeaveAmountItem(sysTimeLeaveDetail);
                    }
                }
            } catch (Exception e) {
                logger.error("请假流程监听流程删除时间出错，流程ID:{}", modelId, e);
            }
        }
    }

    private IEventMulticaster multicaster;
    @Override
    public void setEventMulticaster(IEventMulticaster multicaster) {
        this.multicaster = multicaster;
    }
}
