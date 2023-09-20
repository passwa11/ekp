package com.landray.kmss.third.ldap.oms.out;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public interface SynchroOrg2Ldap {

	public void triggerSynchro(SysQuartzJobContext context);

}
