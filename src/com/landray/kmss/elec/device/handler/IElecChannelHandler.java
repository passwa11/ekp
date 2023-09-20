package com.landray.kmss.elec.device.handler;

import com.landray.kmss.elec.device.client.ElecAdditionalInfo;
import com.landray.kmss.elec.device.client.ElecChannelResponseMessage;
import com.landray.kmss.elec.device.client.IElecChannelRequestMessage;


/**
*@author yucf
*@date  2019年6月27日
*@Description                 渠道业务处理接口
*/

public interface IElecChannelHandler {	
	
	//平台序列号
	static final String PLAT_SERIAL_NUM_KEY = "platSn"; 
	
	//第三方交易号
	static final String THIRD_PARTY_TRANS_NO = "transNo";
	
	
	/**
	 * 数据检验
	 * @param channelRequestInfo   请求数据
	 * @param additionalInfo       额外数据
	 * @throws Exception
	 */
	void validate(IElecChannelRequestMessage channelRequestInfo, ElecAdditionalInfo additionalInfo) throws IllegalArgumentException;
	

	/**
	 * 组装报文
	 * @param channelRequestInfo    请求数据   
	 * @param additionalInfo        额外数据
	 * @return
	 * @throws Exception
	 */
	Object assembleTxMessage(IElecChannelRequestMessage channelRequestInfo, ElecAdditionalInfo additionalInfo) throws Exception;

	/**
	 * 报文转换
	 * @param channelRequestInfo     请求数据
	 * @param respObj                第三方响应数据
	 * @param additionalInfo
	 * @return
	 * @throws Exception
	 */
	ElecChannelResponseMessage<?> transformResponseMessage(IElecChannelRequestMessage channelRequestInfo, Object respObj, ElecAdditionalInfo additionalInfo) throws Exception;

}
