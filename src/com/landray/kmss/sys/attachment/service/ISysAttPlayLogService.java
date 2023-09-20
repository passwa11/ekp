package com.landray.kmss.sys.attachment.service;

import com.landray.kmss.sys.attachment.model.SysAttPlayLog;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface ISysAttPlayLogService extends IExtendDataService {

	/**
	 * 根据附件主键获取播放日志
	 * 
	 * @param fdAttId
	 * @return
	 * @throws Exception
	 */
	public SysAttPlayLog viewByAttId(String fdAttId) throws Exception;

	/**
	 * 更新附件日志
	 * 
	 * @param fdId
	 * @param fdParam
	 * @throws Exception
	 */
	public void updateParam(String fdId, String fdParam) throws Exception;

	/**
	 * 新增附件日志
	 * 
	 * @param fdAttId
	 * @param fdParam
	 * @param fdType
	 * @return
	 * @throws Exception
	 */
	public String addParam(String fdAttId, String fdParam, String fdType)
			throws Exception;
}
