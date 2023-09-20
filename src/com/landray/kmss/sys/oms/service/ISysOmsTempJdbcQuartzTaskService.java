package com.landray.kmss.sys.oms.service;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public interface ISysOmsTempJdbcQuartzTaskService {

	public void execute(SysQuartzJobContext context) throws Exception;
	
	public void runJob();
}
