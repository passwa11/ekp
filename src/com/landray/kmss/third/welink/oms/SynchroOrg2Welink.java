package com.landray.kmss.third.welink.oms;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public interface SynchroOrg2Welink {

	public void triggerSynchro(SysQuartzJobContext context);

}
