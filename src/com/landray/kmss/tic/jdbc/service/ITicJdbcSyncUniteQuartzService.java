package com.landray.kmss.tic.jdbc.service;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public interface ITicJdbcSyncUniteQuartzService {

	public void methodJob(SysQuartzJobContext sysQuartzJobContext) throws Exception;
}
