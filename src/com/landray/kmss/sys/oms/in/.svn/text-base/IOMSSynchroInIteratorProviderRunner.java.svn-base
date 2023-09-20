package com.landray.kmss.sys.oms.in;

import java.util.Date;
import java.util.List;

import com.landray.kmss.sys.oms.in.interfaces.IOMSSynchroInIteratorProvider;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public interface IOMSSynchroInIteratorProviderRunner {
	public abstract void synchro(IOMSSynchroInIteratorProvider provider,
								 SysQuartzJobContext jobContext) throws Exception;

	public List<String[]> getDeptcache();

	public SysQuartzJobContext getJobContext();

//	public Date getLastUpdateTime();
//	
//	public void setLastUpdateTime(Date lastUpdateTime);
}
