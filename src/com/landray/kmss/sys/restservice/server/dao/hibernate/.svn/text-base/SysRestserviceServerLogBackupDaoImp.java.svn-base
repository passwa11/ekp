package com.landray.kmss.sys.restservice.server.dao.hibernate;

import java.util.Calendar;
import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.sys.restservice.server.constant.SysRsConstant;
import com.landray.kmss.sys.restservice.server.dao.ISysRestserviceServerLogBackupDao;
import com.landray.kmss.sys.restservice.server.util.SysRsUtil;

/**
 * RestService日志备份表数据访问接口实现
 * 
 * @author  
 */
public class SysRestserviceServerLogBackupDaoImp extends BaseDaoImp implements
		ISysRestserviceServerLogBackupDao {

	/**
	 * 备份日志
	 */
	@Override
	public void backup(int days) throws Exception {
		String backupHQL = new StringBuilder().append(
				"insert into SysRestserviceServerLogBackup(").append(
				SysRsConstant.LOG_FIELDS).append(") select ").append(
				SysRsConstant.LOG_FIELDS).append(
				" from SysRestserviceServerLog as log").append(
				" where fdStartTime < :backupDate").toString();
		String deleteLogHQL = "delete from SysRestserviceServerLog where fdStartTime < :backupDate";
		Date backupDate = SysRsUtil.getTime(Calendar.DAY_OF_MONTH, -days);

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
		String clearHQL = "delete from SysRestserviceServerLogBackup where fdStartTime < :clearDate";
		Date clearDate = SysRsUtil.getTime(Calendar.DAY_OF_MONTH, -days);

		super.getSession().createQuery(clearHQL).setDate("clearDate", clearDate)
				.executeUpdate();
	}
}
