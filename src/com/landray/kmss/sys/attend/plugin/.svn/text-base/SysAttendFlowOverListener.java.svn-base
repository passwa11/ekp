package com.landray.kmss.sys.attend.plugin;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.lbpm.engine.manager.ProcessParameters;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.engine.persistence.AccessManager;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpm.engine.service.Event_BeforeProcessDelete;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
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
 * 出差/销出差/外出/加班数据处理
 * @author 王京
 * @date 2022-01-18
 */
public class SysAttendFlowOverListener extends SysAttendListenerCommonImp implements IEventListener, IEventMulticasterAware, ApplicationListener<ApplicationEvent> {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendFlowOverListener.class);

    /**
     * 监听出差/销出差/外出/加班 的流程结束时间。
     * 包含提交人作废，提交人撤回，流程结束，流程作废
     * @param execution
     * @param parameter
     *            前端侦听器配置信息
     * @throws Exception
     */
    @Override
    public void handleEvent(EventExecutionContext execution, String parameter)
            throws Exception {
        //根据流程状态来处理不同逻辑
        IBaseModel mainModel = execution.getMainModel();
        //流程标识
        String processFlag = execution.getProcessParameters().getInstanceParamValue(execution.getProcessInstance(),PROCESS_FLAG_KEY);
        //非考勤标识的流程。不支持
        if(!PROCESS_FLAG_RUN_VALUE.equals(processFlag) && !PROCESS_FLAG_ERROR_VALUE.equals(processFlag)){
            //流程在考勤模块不存在，则不继续执行
            return;
        }
        Object object = PropertyUtils.getProperty(mainModel, "docCreator");
        //将本流程中的业务对象待办清除。
        if(object !=null){
            removeNotify(mainModel.getFdId(), (SysOrgPerson) object);
        }
        if(!checkProcessIsHave(mainModel.getFdId(),
                AttendConstant.ATTEND_PROCESS_TYPE[4],
                AttendConstant.ATTEND_PROCESS_TYPE[6],
                AttendConstant.ATTEND_PROCESS_TYPE[7],
                AttendConstant.ATTEND_PROCESS_TYPE[9] )){
            logger.debug(String.format("开始执行流程结束事件，流程ID：%s 考勤无该流程数据",mainModel.getFdId()));
            return;
        }
        if(logger.isDebugEnabled()){
            logger.debug(String.format("开始执行流程结束事件，流程ID：%s",mainModel.getFdId()));
        }
        String docStatus = execution.getExecuteParameters().getExpectMainModelStatus();
        //该流程对应正在进行中的流程列表。先清除。在重新新增
        List<SysAttendBusiness> businessIngList = getSysAttendBusinessService().findBusinessByProcessId(mainModel.getFdId(), AttendConstant.ATTEND_PROCESS_STATUS[0]);
        if(CollectionUtils.isNotEmpty(businessIngList)){
            // 撤销 、作废
            if(SysDocConstant.DOC_STATUS_DRAFT.equals(docStatus) || SysDocConstant.DOC_STATUS_DISCARD.equals(docStatus)){
                //草稿状态、撤回、作废流程
                for (SysAttendBusiness businessIng:businessIngList ) {
                    businessIng.setFdDelFlag(AttendConstant.ATTEND_PROCESS_BUSINESS_DEL_FLAG[1]);
                    businessIng.setFdOverFlag(AttendConstant.ATTEND_PROCESS_STATUS[1]);
                    getSysAttendBusinessService().update(businessIng);
                    if(logger.isDebugEnabled()){
                        logger.debug(String.format("流程作废撤销，流程ID：%s,考勤流程业务id:%s",mainModel.getFdId(),businessIng.getFdId()));
                    }
                }
            } else {
                //发布状态，流程结束
                Integer processType =businessIngList.get(0).getFdType();
                if(AttendConstant.ATTEND_PROCESS_TYPE[4].equals(processType)){
                    //出差
                    executeBusiness(businessIngList);
                } else if (AttendConstant.ATTEND_PROCESS_TYPE[9].equals(processType)) {
                    //销出差
                    executeResumeBusiness(businessIngList);
                } else if (AttendConstant.ATTEND_PROCESS_TYPE[7].equals(processType)) {
                    //外出
                    executeOutgoing(businessIngList);
                } else if (AttendConstant.ATTEND_PROCESS_TYPE[6].equals(processType)){
                    //加班
                    executeOverTime(businessIngList);
                } else {
                    return;
                }
            }
        }

    }
    /**
     * 执行外出的发布事件逻辑
     * @param businessIngList
     * @throws Exception
     */
    private void executeOutgoing(List<SysAttendBusiness> businessIngList) throws Exception {
        //外出
        for (SysAttendBusiness businessIng:businessIngList ) {
            businessIng.setFdDelFlag(AttendConstant.ATTEND_PROCESS_BUSINESS_DEL_FLAG[0]);
            businessIng.setFdOverFlag(AttendConstant.ATTEND_PROCESS_STATUS[1]);
            getSysAttendBusinessService().update(businessIng);
            if(logger.isDebugEnabled()){
                logger.debug(String.format("流程完成，流程ID：%s,考勤流程业务id:%s",businessIng.getFdProcessId(),businessIng.getFdId()));
            }
            //流程创建时间 因为有统计是根据此时间 来统计。这里重新赋值。最理想的是加个修改时间字段......
            businessIng.setDocCreateTime(new Date());
            //更新打卡记录
            updateSysAttendMainByOutgoing(businessIng);
        }
        //执行统计
        reStatistics(businessIngList, this.multicaster);
    }

    /**
     * 执行销出差的发布事件逻辑
     * @param businessIngList
     * @throws Exception
     */
    private void executeResumeBusiness(List<SysAttendBusiness> businessIngList) throws Exception {
        //销出差
        for (SysAttendBusiness businessIng:businessIngList ) {
            businessIng.setFdOverFlag(AttendConstant.ATTEND_PROCESS_STATUS[1]);
            //流程创建时间 因为有统计是根据此时间 来统计。这里重新赋值。最理想的是加个修改时间字段......
            businessIng.setDocCreateTime(new Date());
            getSysAttendBusinessService().update(businessIng);
            if(logger.isDebugEnabled()){
                logger.debug(String.format("流程完成，流程ID：%s,考勤流程业务id:%s",businessIng.getFdProcessId(),businessIng.getFdId()));
            }
            //更新打卡记录
            getSysAttendBusinessResumeListener().updateAttendBusinessByResume(businessIng);
        }
        //执行统计
        reStatistics(businessIngList, this.multicaster);
    }

    /**
     * 执行发布加班事件
     * @param businessIngList
     * @throws Exception
     */
    private void executeOverTime(List<SysAttendBusiness> businessIngList) throws Exception {
        //加班
        for (SysAttendBusiness businessIng:businessIngList ) {
            businessIng.setFdDelFlag(AttendConstant.ATTEND_PROCESS_BUSINESS_DEL_FLAG[0]);
            businessIng.setFdOverFlag(AttendConstant.ATTEND_PROCESS_STATUS[1]);
            //流程创建时间 因为有统计是根据此时间 来统计。这里重新赋值。最理想的是加个修改时间字段......
            businessIng.setDocCreateTime(new Date());
            getSysAttendBusinessService().update(businessIng);
            if(logger.isDebugEnabled()){
                logger.debug(String.format("流程完成，流程ID：%s,考勤流程业务id:%s",businessIng.getFdProcessId(),businessIng.getFdId()));
            }
            //写入加班明细中。写一个默认值，不需要加班工时
            getSysAttendOvertimeListener().addOvertime(businessIng);
        }
        //执行统计 写入额度等操作
        reStatistics(businessIngList, this.multicaster);
    }
    /**
     * 执行发布出差事件
     * @param businessIngList
     * @throws Exception
     */
    private void executeBusiness(List<SysAttendBusiness> businessIngList) throws Exception {
        //出差
        for (SysAttendBusiness businessIng:businessIngList ) {
            businessIng.setFdDelFlag(AttendConstant.ATTEND_PROCESS_BUSINESS_DEL_FLAG[0]);
            businessIng.setFdOverFlag(AttendConstant.ATTEND_PROCESS_STATUS[1]);
            //流程创建时间 因为有统计是根据此时间 来统计。这里重新赋值。最理想的是加个修改时间字段......
            businessIng.setDocCreateTime(new Date());
            getSysAttendBusinessService().update(businessIng);
            if(logger.isDebugEnabled()){
                logger.debug(String.format("流程完成，流程ID：%s,考勤流程业务id:%s",businessIng.getFdProcessId(),businessIng.getFdId()));
            }
            //更新打卡记录
            updateSysAttendMainByBusiness(businessIng);
        }
        //执行统计
        reStatistics(businessIngList, this.multicaster);
    }

    /***
     * 加班的原事件
     */
    private SysAttendOvertimeListener sysAttendOvertimeListener;

    public SysAttendOvertimeListener getSysAttendOvertimeListener() {
        if(sysAttendOvertimeListener ==null){
            sysAttendOvertimeListener = (SysAttendOvertimeListener) SpringBeanUtil.getBean("sysAttendOvertimeListener");
        }
        return sysAttendOvertimeListener;
    }


    /***
     * 销出差的原事件
     */
    private SysAttendBusinessResumeListener sysAttendBusinessResumeListener;

    public SysAttendBusinessResumeListener getSysAttendBusinessResumeListener() {
        if(sysAttendBusinessResumeListener ==null){
            sysAttendBusinessResumeListener = (SysAttendBusinessResumeListener) SpringBeanUtil.getBean("sysAttendBusinessResumeListener");
        }
        return sysAttendBusinessResumeListener;
    }

    /**
     * 监听流程结束事件，把相关的流程设置为无效
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event == null) {
            return;
        }
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
                ProcessParameters processParameters =new ProcessParameters(accessManager,subProcess);
                String processFlag = processParameters.getInstanceParamValue(subProcess,PROCESS_FLAG_KEY);
                if(!PROCESS_FLAG_RUN_VALUE.equals(processFlag) && !PROCESS_FLAG_ERROR_VALUE.equals(processFlag)){
                    //流程在考勤模块不存在，则不继续执行
                    return;
                }
                //将本流程中的业务对象待办清除。
                if(subProcess.getFdCreator() !=null){
                    removeNotify(modelId, (SysOrgPerson) subProcess.getFdCreator());
                }

                if(!checkProcessIsHave(modelId,
                        AttendConstant.ATTEND_PROCESS_TYPE[4],
                        AttendConstant.ATTEND_PROCESS_TYPE[6],
                        AttendConstant.ATTEND_PROCESS_TYPE[7],
                        AttendConstant.ATTEND_PROCESS_TYPE[8],
                        AttendConstant.ATTEND_PROCESS_TYPE[9] )){
                    logger.debug(String.format("开始执行流程结束事件，流程ID：%s 考勤无该流程数据",modelId));
                    return;
                }
                //该流程对应正在进行中的流程列表。先清除。在重新新增
                List<SysAttendBusiness> businessIngList = getSysAttendBusinessService().findBusinessByProcessId(modelId, AttendConstant.ATTEND_PROCESS_STATUS[0]);
                for (SysAttendBusiness businessIng:businessIngList ) {
                    //businessIng.setFdDelFlag(AttendConstant.ATTEND_PROCESS_BUSINESS_DEL_FLAG[1]);
                    //businessIng.setFdOverFlag(AttendConstant.ATTEND_PROCESS_STATUS[1]);
                    getSysAttendBusinessService().delete(businessIng);
                    if(logger.isDebugEnabled()){
                        logger.debug(String.format("流程删除，流程ID：%s,考勤流程业务id:%s",modelId,businessIng.getFdId()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private IEventMulticaster multicaster;
    @Override
    public void setEventMulticaster(IEventMulticaster multicaster) {
        this.multicaster = multicaster;
    }

}
