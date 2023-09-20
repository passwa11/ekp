package com.landray.kmss.km.calendar.cms.interfaces;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public interface ICMSSynchroService {

	public void synchroAll(SysQuartzJobContext context) throws Exception;

}
