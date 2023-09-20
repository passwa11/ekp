package com.landray.kmss.elec.device.service;

/**
*@author yucf
*@date  2019年7月10日
*@Description               渠道服务是否可用
*/

public interface IElecChannelHeartbeatService {
	
	/**
	 * 服务是否可用
	 * @return
	 * @throws Exception
	 */
	boolean isAvailable() throws Exception;

}
