package com.landray.kmss.sys.attend.plugin;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.lbpm.engine.builder.NodeInstance;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.engine.task.NodeInstanceUtils;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程提交人提交事件
 * 加班数据校验
 * @author 王京
 * @date 2022-01-18
 */
public class SysAttendOvertimeStartListener extends SysAttendOvertimeListener
        implements IEventListener {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendOvertimeStartListener.class);

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
                removeNotify(mainModel.getFdId(), UserUtil.getKMSSUser().getPerson());

                boolean isException = false;
                List<SysAttendBusiness> busList =null;
                //只有起草节点结束事件才解析，否则提示异常，发送待办
                if(execution.getEventSource() instanceof NodeInstance) {
                    JSONObject params = JSONObject.fromObject(parameter);
                    JSONObject ovtTimeJson = params.containsKey("fdOvtTime") ? JSONObject.fromObject(params.get("fdOvtTime")) : new JSONObject();
                    //新旧数据标识
                    boolean isNewEmpty = false;
                    if (ovtTimeJson.isEmpty()) {
                        isNewEmpty = true;
                    }
                    busList = getBusinessList(params, mainModel,isNewEmpty);
                }else{
                    isException =true;
                }
                if (CollectionUtils.isEmpty(busList)) {
                    logger.warn("出差流程数据配置不准确,忽略处理!parameter:" + parameter);
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
                // 1.保存数据、状态标记为待处理考勤
                List<SysAttendBusiness> statBusiness = new ArrayList<>();
                List<SysAttendBusiness> checkBusinessList = new ArrayList<>();
                for (SysAttendBusiness bus : busList) {
                    //验证流程的重复性。因为存在可能是多个的情况下，这里只有在验证通过以后 才继续执行。
                    if (checkUserCategory(bus) && checkDateRepeat(bus,AttendConstant.ATTEND_PROCESS_TYPE[6],true)) {
                        checkBusinessList.add(bus);
                    } else{
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
                // 2.存储流程标识
                if (CollectionUtils.isNotEmpty(statBusiness)) {
                    execution.getProcessParameters().addInstanceParamValue(execution.getProcessInstance(), PROCESS_FLAG_KEY, PROCESS_FLAG_RUN_VALUE);
                }
            }
        }
    }
}
