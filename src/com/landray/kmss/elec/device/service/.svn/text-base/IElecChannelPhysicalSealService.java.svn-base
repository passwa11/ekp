package com.landray.kmss.elec.device.service;

import java.util.List;

import com.landray.kmss.elec.device.client.ElecAdditionalInfo;
import com.landray.kmss.elec.device.client.ElecChannelResponseMessage;
import com.landray.kmss.elec.device.client.ElecChannelSealInfo;
import com.landray.kmss.elec.device.client.ElecChannelUserInfo;
import com.landray.kmss.elec.device.client.IElecChannelRequestMessage;

/**
*@author yucf
*@date  2019年8月2日
*@Description            渠道用印服务（硬件）
*/

public interface IElecChannelPhysicalSealService {
	
	/**
	 * 申请用印
	 * @param applySealInfo
	 * @param additionalInfo
	 * @return
	 * @throws Exception
	 */
	ElecChannelResponseMessage<?> applyUseSeal(IElecChannelRequestMessage applySealInfo, ElecAdditionalInfo additionalInfo) throws Exception;

	/**
	 * 解锁设备
	 * @param unlockInfo
	 * @param additionalInfo
	 * @return
	 * @throws Exception
	 */
	ElecChannelResponseMessage<?> unlock(IElecChannelRequestMessage unlockInfo, ElecAdditionalInfo additionalInfo) throws Exception;
	
	/**
	 * 查询三方用户信息
	 * @param channelUserInfo
	 * @param additionalInfo
	 * @return
	 * @throws Exception
	 */
	ElecChannelResponseMessage<List<ElecChannelUserInfo>> queryUser(ElecChannelUserInfo channelUserInfo, ElecAdditionalInfo additionalInfo) throws Exception;


	/**
	 * 查询三方印章信息
	 * @param channelSealInfo
	 * @param additionalInfo
	 * @return
	 * @throws Exception
	 */
	ElecChannelResponseMessage<List<ElecChannelSealInfo>> querySeal(ElecChannelSealInfo channelSealInfo, ElecAdditionalInfo additionalInfo) throws Exception;
	
	
	/**
	 * 查询用印日志
	 * @param channelReq
	 * @param additionalInfo
	 * @return
	 * @throws Exception
	 */
	ElecChannelResponseMessage<?> queryUseSeal(IElecChannelRequestMessage channelReq, ElecAdditionalInfo additionalInfo) throws Exception;
	
	
	/**
	 * 结束用印任务
	 * @param channelReq
	 * @param additionalInfo
	 * @return
	 * @throws Exception
	 */
	ElecChannelResponseMessage<?> endSealTask(IElecChannelRequestMessage channelReq, ElecAdditionalInfo additionalInfo) throws Exception;

	
}
