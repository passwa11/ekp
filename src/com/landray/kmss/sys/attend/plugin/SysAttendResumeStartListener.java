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
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import java.util.Date;
import java.util.List;


/**
 * @description: 请假销假流程起草事件监听
 * @author: wangjf
 * @time: 2022/2/23 5:52 下午
 * @version: 1.0
 */
public class SysAttendResumeStartListener extends SysAttendResumeListener implements IEventListener {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendResumeStartListener.class);

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
                    SysAttendBusiness tempBusiness = new SysAttendBusiness();
                    tempBusiness.setFdProcessId(mainModel.getFdId());
                    tempBusiness.setFdProcessName((String) getSysMetadataParser().getFieldValue(mainModel, "docSubject", false));
                    tempBusiness.setDocUrl(AttendUtil.getDictUrl(mainModel, mainModel.getFdId()));
                    sendNotify(tempBusiness, UserUtil.getKMSSUser().getPerson(),
                            String.format("%s_%s", tempBusiness.getFdProcessName(), "流程重启了"), ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip1"));
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
                    SysAttendBusiness tempBusiness = new SysAttendBusiness();
                    tempBusiness.setFdProcessId(mainModel.getFdId());
                    tempBusiness.setFdProcessName((String) getSysMetadataParser().getFieldValue(mainModel, "docSubject", false));
                    tempBusiness.setDocUrl(AttendUtil.getDictUrl(mainModel, mainModel.getFdId()));
                    sendNotify(tempBusiness, UserUtil.getKMSSUser().getPerson(),
                            String.format("%s_%s", tempBusiness.getFdProcessName(), "流程数据配置不正确"), ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip1"));
                    //重置流程状态
                    resetFlowStatus(execution);
                    return;
                }

                List<SysAttendBusiness> busList = null;
                try {
                    busList = getBusinessList(params, mainModel);
                } catch (Exception e) {
                    SysAttendBusiness tempBusiness = new SysAttendBusiness();
                    tempBusiness.setFdProcessId(mainModel.getFdId());
                    tempBusiness.setFdProcessName((String) getSysMetadataParser().getFieldValue(mainModel, "docSubject", false));
                    tempBusiness.setDocUrl(AttendUtil.getDictUrl(mainModel, mainModel.getFdId()));
                    sendNotify(tempBusiness, UserUtil.getKMSSUser().getPerson(),
                            String.format("%s_%s", tempBusiness.getFdProcessName(), "销假流程数据配置不准确"), ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip1"));
                    //重置流程状态
                    resetFlowStatus(execution);
                    return;
                }
                //获取流程中的配置数据，如果无法获取配置数据则抛异常，并通知流程起草人
                if (!checkLeaveData(parameter, busList, mainModel)) {
                    //重置流程状态
                    resetFlowStatus(execution);
                    return;
                }
                try {
                    for (SysAttendBusiness bus : busList) {
                        //默认配合删除标识和流程标识来兼容历史数据.因为其他地方查询计算流程有效性都是通过fdDelFlag为0。在流程没结束之前。该数据不用于计算
                        bus.setFdDelFlag(AttendConstant.ATTEND_PROCESS_BUSINESS_DEL_FLAG[1]);
                        bus.setFdOverFlag(AttendConstant.ATTEND_PROCESS_STATUS[0]);
                        //保存流程表单数据
                        getSysAttendBusinessService().add(bus);
                    }
                } catch (Exception e) {
                    String docSubject = (String) getSysMetadataParser().getFieldValue(mainModel, "docSubject", false);
                    String docUrl = AttendUtil.getDictUrl(mainModel, mainModel.getFdId());
                    logger.error("销假流程报错,流程名称:{},URL:{}parameter:{}", docSubject, docUrl, parameter, e);
                    SysAttendBusiness tempBusiness = new SysAttendBusiness();
                    tempBusiness.setFdProcessId(mainModel.getFdId());
                    tempBusiness.setFdProcessName(docSubject);
                    tempBusiness.setDocUrl(docUrl);
                    sendNotify(tempBusiness, UserUtil.getKMSSUser().getPerson(),
                            String.format("%s_%s", docSubject, "销假程报错保存数据时报错"), ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip1"));
                    resetFlowStatus(execution);
                    return;
                }

                if (CollectionUtils.isNotEmpty(busList)) {
                    execution.getProcessParameters().addInstanceParamValue(execution.getProcessInstance(), PROCESS_FLAG_KEY, PROCESS_FLAG_RUN_VALUE);
                }
            }
        }
    }

    /**
     * @param parameter
     * @param busList
     * @param mainModel
     * @description: 流程提交后校验数据
     * @return: boolean
     * @author: wangjf
     * @time: 2022/2/23 6:06 下午
     */
    private boolean checkLeaveData(String parameter, List<SysAttendBusiness> busList, IBaseModel mainModel) throws Exception {

        String docSubject = (String) getSysMetadataParser().getFieldValue(mainModel, "docSubject", false);
        String docUrl = AttendUtil.getDictUrl(mainModel, mainModel.getFdId());
        //明细数据为空
        if (CollectionUtils.isEmpty(busList)) {
            logger.warn("请假流程明细数据不准确!parameter:{}", parameter);
            SysAttendBusiness tempBusiness = new SysAttendBusiness();
            tempBusiness.setFdProcessId(mainModel.getFdId());
            tempBusiness.setFdProcessName(docSubject);
            tempBusiness.setDocUrl(docUrl);
            logger.error("销假流程前置校验不通过，流程ID:{},流程名称:{},流程URL:{}", mainModel.getFdId(), docSubject, docUrl);
            sendNotify(tempBusiness, UserUtil.getKMSSUser().getPerson(),
                    String.format("%s_%s", docSubject, "销假流程前置校验不通过"), ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip1"));
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
                    sendNotify(sysAttendBusiness, UserUtil.getKMSSUser().getPerson(), String.format("%s_%s", docSubject, "销假流程中请假类型无法找到匹配规则"),
                            ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip1"));
                    break;
                }
            }
        }
        if (!checkBusType) {
            logger.warn("请假流程中请假类型无法找到匹配规则!parameter:{}", parameter);
            return false;
        }

        //验证流程对应的数据
        boolean checkData = true;
        for (SysAttendBusiness sysAttendBusiness : busList) {

            List<SysOrgPerson> personList = getSysOrgCoreService().expandToPerson(sysAttendBusiness.getFdTargets());
            SysTimeLeaveRule leaveRule = AttendUtil.getLeaveRuleByType(sysAttendBusiness.getFdBusType());
            Date startTime = AttendUtil.getDate(sysAttendBusiness.getFdBusStartTime(), 0);
            Date endTime = AttendUtil.getDate(sysAttendBusiness.getFdBusStartTime(), 1);
            for (SysOrgPerson person : personList) {
                //获取请假流程中的数据
                List leaveList = getLeaveBiz(person.getFdId(), startTime, endTime, sysAttendBusiness.getFdBusType());
                if (CollectionUtils.isEmpty(leaveList)) {
                    logger.warn("销假流程没法找到用户:{},对应的考勤请假流程记录!流程名称:{},流程URL:{}", person.getFdName(), docSubject, docUrl);
                    checkData = false;
                    sysAttendBusiness.setDocUrl(docUrl);
                    sendNotify(sysAttendBusiness, UserUtil.getKMSSUser().getPerson(), docSubject+"_销假流程未找到用户'"+person.getFdName()+"'对应的请假流程记录",
                            ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip1"));
                    break;
                }
                //获取请假明细
                List<SysTimeLeaveDetail> leaveDetailList = getLeaveDetail(person.getFdId(), startTime, endTime, leaveRule.getFdSerialNo());
                if (CollectionUtils.isEmpty(leaveDetailList)) {
                    logger.warn("销假流程没法找到用户:{},对应的请假明细记录!流程名称:{},流程URL:{}", person.getFdName(), docSubject, docUrl);
                    checkData = false;
                    sysAttendBusiness.setDocUrl(docUrl);
                    sendNotify(sysAttendBusiness, UserUtil.getKMSSUser().getPerson(), docSubject+"_销假流程未找到用户'"+person.getFdName()+"'对应的请假明细记录",
                            ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip1"));
                    break;
                }
            }
        }
        if (!checkData) {
            logger.warn("销假流程中数据验证失败!parameter:{}!流程名称:{},流程URL:{}", parameter, docSubject, docUrl);
            return false;
        }

        return true;
    }


}
