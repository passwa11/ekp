package com.landray.kmss.km.imeeting.synchro.interfaces;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public interface IMeetingSynchroInService {

	public void synchroAll(SysQuartzJobContext context) throws Exception;

}
