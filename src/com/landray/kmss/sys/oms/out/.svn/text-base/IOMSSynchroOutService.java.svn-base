package com.landray.kmss.sys.oms.out;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * OMS组织机构同步接入接口
 * 
 * @author 吴兵
 * @version 1.0 2006-11-29
 */
public interface IOMSSynchroOutService {

	/**
	 * 增量同步更新
	 */
	public abstract void incSynchro(SysQuartzJobContext context)
			throws Exception;

	/**
	 * 全部同步更新
	 */
	public abstract void allSynchro(String appName) throws Exception;

}
