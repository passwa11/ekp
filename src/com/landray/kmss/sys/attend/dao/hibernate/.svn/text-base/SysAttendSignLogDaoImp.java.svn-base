package com.landray.kmss.sys.attend.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.sys.attend.dao.ISysAttendSignLogDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.sys.attend.model.SysAttendSignLog;
import com.landray.kmss.common.dao.BaseDaoImp;

/**
 * 签到记录日志
 * @author wj
 * @date 2021-10-19
 */
public class SysAttendSignLogDaoImp extends BaseDaoImp implements ISysAttendSignLogDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysAttendSignLog sysAttendSignLog = (SysAttendSignLog) modelObj;
        if (sysAttendSignLog.getDocCreator() == null) {
            sysAttendSignLog.setDocCreator(UserUtil.getUser());
        }
        if (sysAttendSignLog.getDocCreateTime() == null) {
            sysAttendSignLog.setDocCreateTime(new Date());
        }
        return super.add(sysAttendSignLog);
    }
}
