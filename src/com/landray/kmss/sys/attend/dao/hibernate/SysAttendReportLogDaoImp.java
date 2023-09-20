package com.landray.kmss.sys.attend.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.sys.attend.dao.ISysAttendReportLogDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.sys.attend.model.SysAttendReportLog;
import com.landray.kmss.common.dao.BaseDaoImp;

/**
 * 考勤记录导出记录表
 * @author 王京
 * @date 2021-10-13
 */
public class SysAttendReportLogDaoImp extends BaseDaoImp implements ISysAttendReportLogDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysAttendReportLog sysAttendReportLog = (SysAttendReportLog) modelObj;
        if (sysAttendReportLog.getDocCreator() == null) {
            sysAttendReportLog.setDocCreator(UserUtil.getUser());
        }
        if (sysAttendReportLog.getDocCreateTime() == null) {
            sysAttendReportLog.setDocCreateTime(new Date());
        }
        return super.add(sysAttendReportLog);
    }
}
