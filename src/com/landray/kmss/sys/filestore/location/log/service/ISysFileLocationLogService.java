package com.landray.kmss.sys.filestore.location.log.service;

import com.landray.kmss.sys.filestore.location.log.model.SysFileLocationLog;

/**
 * 调用日志写入接口，预留扩展
 *
 */
public interface ISysFileLocationLogService {

	/**
	 * 是否启用
	 * 
	 * @return
	 */
	public Boolean isEnable();

	/**
	 * 写日志方法
	 * 
	 * @param log
	 * @throws Exception
	 */
	public void write(SysFileLocationLog log) throws Exception;

}
