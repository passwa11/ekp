package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2019年7月12日
*@Description                    交易码转换(一般通过名字匹配)
*/

public interface IElecChannelTxCode {
	
	String getValue();

	void setValue(String value);
	
	IElecChannelTxCode getTxCode(String code);
	
	IElecChannelTxCode convertTo(ElecChannelTxCodeEnum channelTxCode);

}
