package com.landray.kmss.sys.oms.out.interfaces;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * OMS组织机构同步接入提供者接口
 * 
 * @author 吴兵
 * @version 1.0 2006-11-29
 */
public interface IOMSSynchroOutProvider {
	/**
	 * OMS组织机构同步统一入口
	 */
	public abstract void synchro(IOMSSynchroOutContext context,
			SysQuartzJobContext jobContext) throws Exception;
	
	public abstract boolean isSynchroOutEnable() throws Exception;
}
