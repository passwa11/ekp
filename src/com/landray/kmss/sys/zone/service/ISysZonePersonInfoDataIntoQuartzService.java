package com.landray.kmss.sys.zone.service;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public interface ISysZonePersonInfoDataIntoQuartzService {
	public void updateInfoData(SysQuartzJobContext context) throws Exception;
}
