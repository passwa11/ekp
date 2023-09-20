package com.landray.kmss.sys.oms.in;
 
import com.landray.kmss.sys.oms.in.interfaces.IOMSSynchroInIncrementProvider;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public interface IOMSSynchroInIncrementProviderRunner {
	public abstract void synchro(IOMSSynchroInIncrementProvider provider,
			SysQuartzJobContext jobContext) throws Exception;
}
