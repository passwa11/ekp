package com.landray.kmss.sys.time.service.robot;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.lbpm.engine.manager.task.TaskExecutionContext;
import com.landray.kmss.sys.lbpmservice.node.robotnode.support.AbstractRobotNodeServiceImp;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountItemService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.hibernate.query.Query;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 机器人节点，设置年假失效时间
 *
 * @author liuyang
 * @date 2022/10/23
 */
public class SysTimeRuleProlongExpireService extends AbstractRobotNodeServiceImp {
    private final Logger logger = LoggerFactory.getLogger(SysTimeRuleProlongExpireService.class);

    private ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService;

    private ISysTimeLeaveAmountItemService getSysTimeLeaveAmountItemService() {
        if (sysTimeLeaveAmountItemService == null) {
            sysTimeLeaveAmountItemService = (ISysTimeLeaveAmountItemService) SpringBeanUtil.getBean("sysTimeLeaveAmountItemService");
        }
        return sysTimeLeaveAmountItemService;
    }

    @Override
    public void execute(TaskExecutionContext context) throws Exception {
        JSONObject json = (JSONObject) JSONValue.parse(getConfigContent(context));
        updateExpireTime(context, json);
    }

    private void updateExpireTime(TaskExecutionContext context, JSONObject json) throws Exception {
        JSONObject parameters = (JSONObject) json.get("params");
        IBaseModel mainModel = context.getMainModel();
        if (mainModel instanceof IExtendDataModel) {
            String fieldValue = (String) parameters.get("fdProLongDate");
            IExtendDataModel model = (IExtendDataModel) mainModel;
            Map<String, Object> modelData = model.getExtendDataModelInfo().getModelData();
            //获取两段key值
            String[] fieldValues = fieldValue.split("\\.");
            if (fieldValues.length == 2) {
                //先由map中由第一层key值获取最外层的有效数据
                List<Map<String, Object>> fileList = (List) modelData.get(fieldValues[0]);
                if (null != fileList && fileList.size() > 0) {
                    for (Map<String, Object> map : fileList) {
                        //延长时间
                        Date proLongDate = (Date) map.get(parameters.get("fdProLongDate").toString().replace(fieldValues[0] + ".", ""));
                        //人员
                        Map person = (Map) map.get(parameters.get("fdPerson").toString().replace(fieldValues[0] + ".", ""));
                        //假期类型
                        String leaveType = (String) map.get(parameters.get("fdLeaveType").toString().replace(fieldValues[0] + ".", ""));
                        //年度
                        String year = (String) map.get(parameters.get("fdYear").toString().replace(fieldValues[0] + ".", ""));
                        if (proLongDate == null || person == null || StringUtil.isNull(leaveType) || StringUtil.isNull(year)) {
                            continue;
                        }
                        String fdPersonId = (String) person.get("id");
                        logger.info("姓名:{},id:{},年度：{},假期类型:{}",person.get("name"),fdPersonId,year,leaveType);
                        SysTimeLeaveAmountItem item = getSysTimeLeaveAmountItemService().getAmountItem(fdPersonId, Integer.parseInt(year), leaveType);
                        if (item != null) {
                            item.setFdValidDate(proLongDate);
                            getSysTimeLeaveAmountItemService().update(item);
                        }
                    }
                }
            }
        }
    }

    /**
     * 定时更新调休假的失效时间
     */
    public void updateValidDate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
        calendar.set(Calendar.MONTH, 2);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        String sql = "update sys_time_leave_aitem set fd_valid_date=? where fd_leave_type='13' and fd_is_avail='1' and fd_total_day > 0 ";
        Query query = getSysTimeLeaveAmountItemService().getBaseDao().getHibernateSession().createSQLQuery(sql).setParameter(0, calendar.getTime());
        query.executeUpdate();
    }
}
