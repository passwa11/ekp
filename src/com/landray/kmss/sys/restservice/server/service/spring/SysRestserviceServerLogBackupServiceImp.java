package com.landray.kmss.sys.restservice.server.service.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.restservice.server.dao.ISysRestserviceServerLogBackupDao;
import com.landray.kmss.sys.restservice.server.model.SysRestServiceBaseInfo;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerLogBackupService;

/**
 * RestService日志备份表业务接口实现
 * 
 * @author  
 */
public class SysRestserviceServerLogBackupServiceImp extends BaseServiceImp implements
		ISysRestserviceServerLogBackupService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysRestserviceServerLogBackupServiceImp.class);

	// 日志备份间隔
	private int daysOfBackupLog = 180;

	// 日志清理间隔
	private int daysOfClearLog = 540;

	public SysRestserviceServerLogBackupServiceImp() {
		try {
			// 获取基础设置中的参数
			SysRestServiceBaseInfo baseInfo = new SysRestServiceBaseInfo();
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
			SysRestServiceBaseInfo baseInfo = new SysRestServiceBaseInfo();

			String value = baseInfo.getDaysOfBackupLog();
			this.daysOfBackupLog = Integer.parseInt(value);

			value = baseInfo.getDaysOfClearLog();
			this.daysOfClearLog = Integer.parseInt(value);

			ISysRestserviceServerLogBackupDao dao = (ISysRestserviceServerLogBackupDao) getBaseDao();
			dao.backup(daysOfBackupLog);
			dao.clear(daysOfClearLog);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

}
