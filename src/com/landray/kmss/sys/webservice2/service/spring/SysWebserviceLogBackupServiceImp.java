package com.landray.kmss.sys.webservice2.service.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.webservice2.dao.ISysWebserviceLogBackupDao;
import com.landray.kmss.sys.webservice2.model.SysWebServiceBaseInfo;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceLogBackupService;

/**
 * WebService日志备份表业务接口实现
 * 
 * @author Jeff
 */
public class SysWebserviceLogBackupServiceImp extends BaseServiceImp implements
		ISysWebserviceLogBackupService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysWebserviceLogBackupServiceImp.class);

	// 日志备份间隔
	private int daysOfBackupLog = 180;

	// 日志清理间隔
	private int daysOfClearLog = 540;

	public SysWebserviceLogBackupServiceImp() {
		try {
			// 获取基础设置中的参数
			SysWebServiceBaseInfo baseInfo = new SysWebServiceBaseInfo();
			String value = baseInfo.getDaysOfBackupLog();
			this.daysOfBackupLog = Integer.parseInt(value);

			value = baseInfo.getDaysOfClearLog();
			this.daysOfClearLog = Integer.parseInt(value);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	@Override
    public void backup() throws Exception {
		try {
			// 获取基础设置中的参数
			SysWebServiceBaseInfo baseInfo = new SysWebServiceBaseInfo();

			String value = baseInfo.getDaysOfBackupLog();
			this.daysOfBackupLog = Integer.parseInt(value);

			value = baseInfo.getDaysOfClearLog();
			this.daysOfClearLog = Integer.parseInt(value);

			ISysWebserviceLogBackupDao dao = (ISysWebserviceLogBackupDao) getBaseDao();
			dao.backup(daysOfBackupLog);
			dao.clear(daysOfClearLog);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

}
