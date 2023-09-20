package com.landray.kmss.sys.attend.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attend.dao.ISysAttendRestatLogDao;
import com.landray.kmss.sys.attend.model.SysAttendRestatLog;
import com.landray.kmss.util.UserUtil;

import java.util.Date;
/**
 * 重新统计日志表
 * @author 王京
 * @date 2021-01-14
 */
public class SysAttendRestatLogDaoImp extends BaseDaoImp implements ISysAttendRestatLogDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysAttendRestatLog sysAttendRestatLog = (SysAttendRestatLog) modelObj;
        if (sysAttendRestatLog.getDocCreator() == null) {
            sysAttendRestatLog.setDocCreator(UserUtil.getUser());
        }
        if (sysAttendRestatLog.getDocCreateTime() == null) {
            sysAttendRestatLog.setDocCreateTime(new Date());
        }
        return super.add(sysAttendRestatLog);
    }
}
