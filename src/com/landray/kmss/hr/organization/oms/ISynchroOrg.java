package com.landray.kmss.hr.organization.oms;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * <P>定时任务同步组织架构数据</P>
 * @version 1.0 2019年12月5日
 */
public interface ISynchroOrg {

	/**
	 * <p>人事组织架构和EKP组织架构同步</p>
	 * @param context
	 * @author sunj
	 */
	public void synchroOrg(SysQuartzJobContext context);
	
	/**
	 * EKP人员同步到HR人事档案
	 * @param context
	 * @author 王京
	 */
	public void synchroPerson(SysQuartzJobContext context);

}
