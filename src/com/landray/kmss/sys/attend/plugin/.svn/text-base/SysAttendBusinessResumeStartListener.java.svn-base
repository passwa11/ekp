package com.landray.kmss.sys.attend.plugin;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.lbpm.engine.builder.NodeInstance;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.engine.task.NodeInstanceUtils;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程提交人提交事件
 * 销出差数据校验
 * @author 王京
 * @date 2022-01-18
 */
public class SysAttendBusinessResumeStartListener extends SysAttendBusinessResumeListener
        implements IEventListener {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendBusinessResumeStartListener.class);
    
    @Override
    public void handleEvent(EventExecutionContext execution, String parameter)
            throws Exception {
        //代表流程驳回以后，当前事件 则不在允许本次提交时执行。
        if(execution.getProcessInstance().getTempData().containsKey(PROCESS_FLAG_RETURN_KEY)){
            return;
        }
        //根据流程状态来处理不同逻辑
        String routeType = execution.getNodeInstance().getFdRouteType();
        IBaseModel mainModel = execution.getMainModel();

        if (NodeInstanceUtils.ROUTE_TYPE_NORMAL.equals(routeType)){
            //正常流程流入
            if (mainModel instanceof IExtendDataModel) {
                //先清除待办。（有可能之前有错误，如果没有就无视）
                removeNotify(mainModel.getFdId(),UserUtil.getKMSSUser().getPerson());
                boolean isException = false;
                List<SysAttendBusiness> busList =null;
                //只有起草节点结束事件才解析，否则提示异常，发送待办
                if(execution.getEventSource() instanceof NodeInstance) {
                    JSONObject params = JSONObject.fromObject(parameter);
                    busList = getBusinessList(params, mainModel);
                }else{
                    isException =true;
                }
                if (CollectionUtils.isEmpty(busList)) {
                    logger.warn("销出差流程数据配置不准确,忽略处理!parameter:" + parameter);
                    isException =true;
                }
                if(isException){
                    SysAttendBusiness tempBusiness =getTempBusinessData(mainModel);
                    sendNotify(tempBusiness, UserUtil.getKMSSUser().getPerson(),
                            String.format("%s_%s",tempBusiness.getFdProcessName(), ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip1")),
                            ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip1")
                    );
                    //重置流程状态
                    resetFlowStatus(execution);
                    return;
                }
                //验证该流程数据是否已经写入过
                List<SysAttendBusiness> list = getSysAttendBusinessService().findBusinessByProcessId(mainModel.getFdId(),AttendConstant.ATTEND_PROCESS_STATUS[1]);
                //如果该流程已经结束。则不处理。主要针对重启的流程。
                if(CollectionUtils.isNotEmpty(list)){
                    logger.warn("流程已结束忽略处理:" + mainModel.getFdId() + ";parameter=" + parameter);
                    return;
                }
                //该流程对应正在进行中的流程列表。先清除。在重新新增
                List<SysAttendBusiness> businessIngList = getSysAttendBusinessService().findBusinessByProcessId(mainModel.getFdId(),AttendConstant.ATTEND_PROCESS_STATUS[0]);
                if(CollectionUtils.isNotEmpty(businessIngList)){
                    for (SysAttendBusiness businessIng:businessIngList ) {
                        //先设置为无效。
                        businessIng.setFdDelFlag(AttendConstant.ATTEND_PROCESS_BUSINESS_DEL_FLAG[1]);
                        businessIng.setFdOverFlag(AttendConstant.ATTEND_PROCESS_STATUS[1]);
                        getSysAttendBusinessService().update(businessIng);
                    }
                }
                //记录写入数据库中的考勤流程数据列表
                List<SysAttendBusiness> statBusiness = new ArrayList<>();
                //验证结果正确的考勤流程数据列表
                List<SysAttendBusiness> checkBusinessList = new ArrayList<>();
                for (SysAttendBusiness bus : busList) {
                    //验证 一条有错误，全部结束
                    if(checkAttendBusiness(bus)){
                        checkBusinessList.add(bus);
                    }else{
                        break;
                    }
                }
                if(CollectionUtils.isNotEmpty(checkBusinessList)){
                    for (SysAttendBusiness bus : checkBusinessList) {
                        //验证成功，写入考勤流程中
                        statBusiness.add(bus);
                        //默认配合删除标识和流程标识来兼容历史数据.因为其他地方查询计算流程有效性都是通过fdDelFlag为0。在流程没结束之前。该数据不用于计算
                        bus.setFdDelFlag(AttendConstant.ATTEND_PROCESS_BUSINESS_DEL_FLAG[1]);
                        bus.setFdOverFlag(AttendConstant.ATTEND_PROCESS_STATUS[0]);
                        //保存流程表单数据
                        getSysAttendBusinessService().add(bus);
                    }
                }else{
                    //重置流程状态
                    resetFlowStatus(execution);
                    return;
                }
                //存储流程标识
                if (CollectionUtils.isNotEmpty(statBusiness)) {
                    execution.getProcessParameters().addInstanceParamValue(execution.getProcessInstance(), PROCESS_FLAG_KEY, PROCESS_FLAG_RUN_VALUE);
                }
            }
        }
    }

    /**
     * 验证销出差的时间
     * @param resume 本次流程数据
     * @throws Exception
     */
    private boolean checkAttendBusiness(SysAttendBusiness resume)
            throws Exception {
        Date fdBusStartTime = resume.getFdBusStartTime();
        Date fdBusEndTime = resume.getFdBusEndTime();
        List<SysOrgPerson> personList = getSysOrgCoreService()
                .expandToPerson(resume.getFdTargets());
        List<String> orgIdList = new ArrayList<String>();
        for(SysOrgPerson p : personList){
            orgIdList.add(p.getFdId());
        }
        // 获取用户的出差流程
        List<Integer> fdTypes = new ArrayList<Integer>();
        fdTypes.add(4);
//        Date endTime = new Date(fdBusEndTime.getTime());
//        endTime.setSeconds(endTime.getSeconds() + 1);
        List<SysAttendBusiness> busList = this.getSysAttendBusinessService()
                .findBussList(orgIdList, fdBusStartTime,
                        fdBusEndTime, fdTypes);
       boolean isRepeat =false;
        for (SysOrgPerson person : personList) {
            List<SysAttendBusiness> userBusList = getUserBusList(busList,
                    person);
            if (userBusList.isEmpty()) {
                logger.warn("用户销出差流程处理忽略,原因:该用户未找到出差记录!userName:"
                        + person.getFdName() + ";startTime:" + fdBusStartTime
                        + ";endTime:" + fdBusEndTime);
                isRepeat =true;
                break;
            }
            for (SysAttendBusiness bus : userBusList) {
                // 实际销假时间区间
                Map<String, Object> dateMap = new HashMap<String, Object>();
                boolean isUpdate = checkConvertBusinessInfo(bus, fdBusStartTime,
                        fdBusEndTime, dateMap);
                if (!isUpdate) {
                    isRepeat =true;
                    break;
                }
            }
        }
        if (isRepeat) {
            //存在 则抛出异常
            String tip = ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip4");
            //发送待办
            sendNotify(resume, UserUtil.getKMSSUser().getPerson(),
                    String.format("%s_%s",resume.getFdProcessName(),
                            ResourceUtil.getString("sysAttendBusiness.check.trip4", "sys-attend")),
                    tip);
            return false;
        }
        return true;
    }
}
