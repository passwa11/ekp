package com.landray.kmss.sys.attend.plugin;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.util.SpringBeanUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import java.util.Date;
import java.util.List;

/**
 * 流程结束事件
 * 销假流程数据处理
 *
 * @author 王进府
 * @date 2022-02-22
 */
public class SysAttendResumeOverListener extends SysAttendListenerCommonImp implements IEventListener, IEventMulticasterAware {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendResumeOverListener.class);

    private IEventMulticaster multicaster;
    @Override
    public void setEventMulticaster(IEventMulticaster multicaster) {
        this.multicaster = multicaster;
    }

    private SysAttendResumeListener getSysAttendResumeListener() {
        return (SysAttendResumeListener)SpringBeanUtil.getBean("sysAttendResumeListener");
    }

    /**
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
        if (!PROCESS_FLAG_RUN_VALUE.equals(processFlag) && !PROCESS_FLAG_ERROR_VALUE.equals(processFlag)) {
            //流程在考勤模块不存在，则不继续执行
            return;
        }
        if (!this.checkProcessIsHave(mainModel.getFdId(), AttendConstant.ATTEND_PROCESS_TYPE[8])) {
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("开始执行流程结束事件，流程ID：{}", mainModel.getFdId());
        }
        //docStatus =10是提交状态，代表撤回，00 代表是作废，30代表结束。
        String docStatus = execution.getExecuteParameters().getExpectMainModelStatus();
        //该流程对应正在进行中的流程列表。先清除。在重新新增
        List<SysAttendBusiness> businessIngList = getSysAttendBusinessService().findBusinessByProcessId(mainModel.getFdId(), AttendConstant.ATTEND_PROCESS_STATUS[0]);
        if (CollectionUtils.isNotEmpty(businessIngList)) {
            // 撤销 、作废  需要归还假期
            if (SysDocConstant.DOC_STATUS_DRAFT.equals(docStatus) || SysDocConstant.DOC_STATUS_DISCARD.equals(docStatus)) {
                //草稿状态、撤回、删除流程
                for (SysAttendBusiness businessIng : businessIngList) {
                    businessIng.setFdDelFlag(AttendConstant.ATTEND_PROCESS_BUSINESS_DEL_FLAG[1]);
                    businessIng.setFdOverFlag(AttendConstant.ATTEND_PROCESS_STATUS[1]);
                    getSysAttendBusinessService().update(businessIng);
                    logger.warn("流程作废撤销，流程ID：{},考勤流程业务id:{}", mainModel.getFdId(), businessIng.getFdId());
                }
            } else {
                //发布状态，流程结束
                for (SysAttendBusiness businessIng : businessIngList) {
                    getSysAttendResumeListener().updateLeaveBiz(businessIng, false, null);
                    businessIng.setFdDelFlag(AttendConstant.ATTEND_PROCESS_BUSINESS_DEL_FLAG[0]);
                    businessIng.setFdOverFlag(AttendConstant.ATTEND_PROCESS_STATUS[1]);
                    getSysAttendBusinessService().update(businessIng);
                    if (logger.isDebugEnabled()) {
                        logger.debug("流程完成，流程ID：{},考勤流程业务id:{}", mainModel.getFdId(), businessIng.getFdId());
                    }
                    //流程创建时间 因为有统计是根据此时间 来统计
                    businessIng.setDocCreateTime(new Date());
                }
                //执行统计
                reStatistics(businessIngList, this.multicaster);
            }
        }
    }

}
