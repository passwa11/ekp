package com.landray.kmss.sys.webservice2.dao.hibernate;

import java.util.Calendar;
import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.sys.webservice2.constant.SysWsConstant;
import com.landray.kmss.sys.webservice2.dao.ISysWebserviceLogBackupDao;
import com.landray.kmss.sys.webservice2.util.SysWsUtil;

/**
 * WebService日志备份表数据访问接口实现
 * 
 * @author Jeff
 */
public class SysWebserviceLogBackupDaoImp extends BaseDaoImp implements
		ISysWebserviceLogBackupDao {

	/**
	 * 备份日志
	 */
	@Override
    public void backup(int days) throws Exception {
		String backupHQL = new StringBuilder().append(
				"insert into SysWebserviceLogBackup(").append(
				SysWsConstant.LOG_FIELDS).append(") select ").append(
				SysWsConstant.LOG_FIELDS).append(
				" from SysWebserviceLog as log").append(
				" where fdStartTime < :backupDate").toString();
		String deleteLogHQL = "delete from SysWebserviceLog where fdStartTime < :backupDate";
		Date backupDate = SysWsUtil.getTime(Calendar.DAY_OF_MONTH, -days);

		super.getSession().createQuery(backupHQL).setDate("backupDate", backupDate)
				.executeUpdate();
		super.getSession().createQuery(deleteLogHQL)
				.setDate("backupDate", backupDate).executeUpdate();
	}

	/**
	 * 清除日志
	 */
	@Override
    public void clear(int days) throws Exception {
		String clearHQL = "delete from SysWebserviceLogBackup where fdStartTime < :clearDate";
		Date clearDate = SysWsUtil.getTime(Calendar.DAY_OF_MONTH, -days);

		super.getSession().createQuery(clearHQL).setDate("clearDate", clearDate)
				.executeUpdate();
	}
}
