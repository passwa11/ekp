package com.landray.kmss.sys.attachment.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attachment.dao.ISysAttPlayLogDao;
import com.landray.kmss.sys.attachment.model.SysAttPlayLog;
import com.landray.kmss.util.UserUtil;

public class SysAttPlayLogDaoImp extends BaseDaoImp implements ISysAttPlayLogDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysAttPlayLog sysAttPlayLog = (SysAttPlayLog) modelObj;
        if (sysAttPlayLog.getDocCreator() == null) {
        	sysAttPlayLog.setDocCreator(UserUtil.getUser());
        }
        if (sysAttPlayLog.getDocCreateTime() == null) {
        	sysAttPlayLog.setDocCreateTime(new Date());
        }
        return super.add(sysAttPlayLog);
    }
}
