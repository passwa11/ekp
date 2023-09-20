package com.landray.kmss.elec.device.service;

import com.landray.kmss.elec.device.client.ElecAdditionalInfo;
import com.landray.kmss.elec.device.client.ElecChannelResponseMessage;
import com.landray.kmss.elec.device.client.IElecChannelRequestMessage;

/**
*@author yucf
*@date  2020年1月3日
*@Description                 渠道单点服务
*/

public interface IElecChannelSsoService {
	
	/**
	 * 系统持有方与第三方帐号绑定
	 * @param reqMsg
	 * @param additionalInfo
	 * @return
	 * @throws Exception
	 */
	 ElecChannelResponseMessage<?> bindAccount(IElecChannelRequestMessage reqMsg, ElecAdditionalInfo additionalInfo) throws Exception;
	
	/**
	 * 系统持有方与第三方帐号解绑
	 * @param reqMsg
	 * @param additionalInfo
	 * @return
	 * @throws Exception
	 */
	 ElecChannelResponseMessage<?> unBindAccount(IElecChannelRequestMessage reqMsg, ElecAdditionalInfo additionalInfo) throws Exception;
	
	/**
	 * 跳转到第三方指定位置
	 * @param reqMsg
	 * @param additionalInfo
	 * @return
	 * @throws Exception
	 */
	ElecChannelResponseMessage<?> redirect(IElecChannelRequestMessage reqMsg, ElecAdditionalInfo additionalInfo) throws Exception;

}
