package com.landray.kmss.sys.filestore.location.log.service;

import com.landray.kmss.sys.filestore.location.log.model.SysFileLocationLog;
import com.landray.kmss.util.SpringBeanUtil;

public abstract class SysFileLocationLogWriter {

	/**
	 * 封装日志明细，具体调用的地方实现
	 * 
	 * @param log
	 */
	public abstract void bulidLog(SysFileLocationLog log);

	/**
	 * 写日志
	 * 
	 * @throws Exception
	 */
	public void write() throws Exception {

		// 待扩展，现在默认为log4j实现
		ISysFileLocationLogService logService =
				(ISysFileLocationLogService) SpringBeanUtil
						.getBean("sysFileLocationLogByLog4jService");

		if (!logService.isEnable()) {
			return;
		}

		SysFileLocationLog log = new SysFileLocationLog();
		bulidLog(log);

		logService.write(log);
	}
}
