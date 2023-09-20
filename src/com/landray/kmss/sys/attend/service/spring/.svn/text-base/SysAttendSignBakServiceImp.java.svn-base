package com.landray.kmss.sys.attend.service.spring;

import com.landray.kmss.sys.attend.model.SysAttendConfig;
import com.landray.kmss.sys.attend.service.ISysAttendConfigService;
import com.landray.kmss.sys.attend.service.ISysAttendSignBakService;
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
public class SysAttendSignBakServiceImp extends ExtendDataServiceImp implements ISysAttendSignBakService {
    private ISysAttendConfigService sysAttendConfigService;

    public ISysAttendConfigService getSysAttendConfigServiceImp() {
        if (sysAttendConfigService == null) {
            sysAttendConfigService = (ISysAttendConfigService) SpringBeanUtil.getBean("sysAttendConfigService");
        }
        return sysAttendConfigService;
    }
    /**
     * 定期将历史表删除
     * @param context
     * @throws Exception
     */
    @Override
    public void deleteSignHisLogDelete(SysQuartzJobContext context) throws Exception {
        //读取配置的时间
        SysAttendConfig attendConfig =getSysAttendConfigServiceImp().getSysAttendConfig();
        Integer fdSignLogToHisDay =365;
        if(attendConfig !=null) {
            fdSignLogToHisDay = attendConfig.getFdSignLogToDeleteDay();
        }
        Date toHisDate = AttendUtil.getDate(new Date(),0-fdSignLogToHisDay);

        //转存以后，清理数据
        String deleteInfo ="DELETE FROM sys_attend_sign_bak WHERE doc_create_time < :toHisDate";
        int row = super.getBaseDao().getHibernateSession().createSQLQuery(deleteInfo).setParameter("toHisDate",toHisDate)
                .executeUpdate();
        //记录日志
        context.logMessage(String.format("清理日期：%s之前的数据；总共清理数据：%s条", DateUtil.convertDateToString(toHisDate,"yyyy-MM-dd"),row));

    }
}
