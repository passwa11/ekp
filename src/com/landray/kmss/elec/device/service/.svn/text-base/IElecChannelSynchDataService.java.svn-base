package com.landray.kmss.elec.device.service;

import com.landray.kmss.elec.device.client.ElecAdditionalInfo;
import com.landray.kmss.elec.device.client.ElecChannelAuthorizationInfo;
import com.landray.kmss.elec.device.client.ElecChannelOrgInfo;
import com.landray.kmss.elec.device.client.ElecChannelPhysicalDeviceInfo;
import com.landray.kmss.elec.device.client.ElecChannelResponseMessage;
import com.landray.kmss.elec.device.client.ElecChannelSealInfo;
import com.landray.kmss.elec.device.client.ElecChannelUserInfo;

/**
*@author yucf
*@date  2019年10月18日
*@Description             同步数据到第三方平台
*/

public interface IElecChannelSynchDataService {
	
	/**
	 * 同步用户信息
	 * @param userInfo          用户信息
	 * @param additionalInfo    附加信息
	 * @return
	 * @throws Exception
	 */
	ElecChannelResponseMessage<String> synchUser(ElecChannelUserInfo userInfo, ElecAdditionalInfo additionalInfo)throws Exception;
	
	/**
	 * 同步部门/公司信息
	 * @param orgInfo           部门/公司信息
	 * @param additionalInfo    附加信息
	 * @return
	 * @throws Exception
	 */
	ElecChannelResponseMessage<String> synchOrg(ElecChannelOrgInfo orgInfo, ElecAdditionalInfo additionalInfo)throws Exception;
	
	/**
	 * 同步印章信息
	 * @param sealInfo           印章信息
	 * @param additionalInfo     附加信息
	 * @return
	 * @throws Exception
	 */
	ElecChannelResponseMessage<String> synchSeal(ElecChannelSealInfo sealInfo, ElecAdditionalInfo additionalInfo) throws Exception;
	

	/**
	 * 同步设备
	 * @param deviceInfo        设备信息
	 * @param additionalInfo    附加信息
	 * @return
	 * @throws Exception
	 */
	ElecChannelResponseMessage<String> synchDevice(ElecChannelPhysicalDeviceInfo deviceInfo, ElecAdditionalInfo additionalInfo) throws Exception;
	

	/**
	 * 同步权限
	 * @param authorizationInfo
	 * @param additionalInfo
	 * @return
	 * @throws Exception
	 */
	ElecChannelResponseMessage<String> synchAuthorization(ElecChannelAuthorizationInfo authorizationInfo, ElecAdditionalInfo additionalInfo) throws Exception;
}
