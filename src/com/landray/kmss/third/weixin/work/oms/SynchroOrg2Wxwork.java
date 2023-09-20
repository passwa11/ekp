package com.landray.kmss.third.weixin.work.oms;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public interface SynchroOrg2Wxwork {

	public void triggerSynchro(SysQuartzJobContext context);

	public String deleteWxUserByEkpId(String fdId);
}
