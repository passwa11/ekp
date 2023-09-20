package com.landray.kmss.sys.attend.service.spring;

import com.landray.kmss.sys.attend.model.SysAttendConfig;
import com.landray.kmss.sys.attend.service.ISysAttendConfigService;
import com.landray.kmss.sys.attend.service.ISysAttendSignLogService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;

import java.util.Date;

/**
 * 签到记录日志
 * @author wj
 * @date 2021-10-19
 */
public class SysAttendSignLogServiceImp extends ExtendDataServiceImp implements ISysAttendSignLogService {

    private ISysAttendConfigService sysAttendConfigService;

    public ISysAttendConfigService getSysAttendConfigServiceImp() {
        if (sysAttendConfigService == null) {
            sysAttendConfigService = (ISysAttendConfigService) SpringBeanUtil.getBean("sysAttendConfigService");
        }
        return sysAttendConfigService;
    }

    /**
     * 定期将签到记录转为历史表数据
     * @param context
     * @throws Exception
     */
    @Override
    public void syncSignLogToHis(SysQuartzJobContext context) throws Exception {
        //读取配置的时间
        SysAttendConfig attendConfig =getSysAttendConfigServiceImp().getSysAttendConfig();
        Integer fdSignLogToHisDay =120;
        if(attendConfig !=null) {
            fdSignLogToHisDay = attendConfig.getFdSignLogToHisDay();
        }
        Date toHisDate = AttendUtil.getDate(new Date(),0-fdSignLogToHisDay);
        //将指定日期数据转存到历史表
        String insertInto ="INSERT INTO sys_attend_sign_bak (fd_id,doc_create_time,fd_address,fd_wifi_name,fd_type,fd_is_available,doc_creator_id,fd_group_id,fd_base_date)" +
                " SELECT fd_id,doc_create_time,fd_address,fd_wifi_name,fd_type,fd_is_available,doc_creator_id,fd_group_id,fd_base_date FROM sys_attend_sign_log " +
                " where doc_create_time < :toHisDate ";
        //转存数据
        int row = super.getBaseDao().getHibernateSession().createSQLQuery(insertInto).setParameter("toHisDate",toHisDate)
                .executeUpdate();
        //转存以后，清理数据
        String deleteInfo ="DELETE FROM sys_attend_sign_log WHERE doc_create_time < :toHisDate";
        row = super.getBaseDao().getHibernateSession().createSQLQuery(deleteInfo).setParameter("toHisDate",toHisDate)
                .executeUpdate();
        //记录日志
        context.logMessage(String.format("转存，从日期：%s之前的数据；总共转存数据：%s条", DateUtil.convertDateToString(toHisDate,"yyyy-MM-dd"),row));
    }
}
