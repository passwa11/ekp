package com.landray.kmss.third.weixin.work.oms;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import net.sf.json.JSONObject;

/**
 * 同步下游企业组织数据到EKP
 */
public interface SynchroCorpgroupOrg2Ekp {
	
	/**
	 * 定时任务
	 */
	public void triggerSynchro(SysQuartzJobContext context);


	
}
