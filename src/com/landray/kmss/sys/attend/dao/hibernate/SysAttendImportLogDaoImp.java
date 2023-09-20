package com.landray.kmss.sys.attend.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attend.dao.ISysAttendImportLogDao;
import com.landray.kmss.sys.attend.model.SysAttendImportLog;
import com.landray.kmss.util.UserUtil;

public class SysAttendImportLogDaoImp extends BaseDaoImp
		implements ISysAttendImportLogDao {
	@Override
    public String add(IBaseModel modelObj) throws Exception {
		SysAttendImportLog sysAttendImportLog = (SysAttendImportLog) modelObj;
        if (sysAttendImportLog.getDocCreator() == null) {
        	sysAttendImportLog.setDocCreator(UserUtil.getUser());
        }
        if (sysAttendImportLog.getDocCreateTime() == null) {
        	sysAttendImportLog.setDocCreateTime(new Date());
        }
        return super.add(sysAttendImportLog);
    }
	
	@Override
    public void update(IBaseModel modelObj) throws Exception {
		SysAttendImportLog sysAttendImportLog = (SysAttendImportLog) modelObj;
        if (sysAttendImportLog.getDocAlterTime() == null) {
        	sysAttendImportLog.setDocAlterTime(new Date());
        }
        super.update(sysAttendImportLog);
    }
}
