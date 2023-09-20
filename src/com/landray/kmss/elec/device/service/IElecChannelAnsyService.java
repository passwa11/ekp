package com.landray.kmss.elec.device.service;

import org.springframework.scheduling.annotation.Async;

import com.alibaba.fastjson.JSONObject;

/**
*@author yucf
*@date  2019年8月30日
*@Description              异步服务
*/

public interface IElecChannelAnsyService {
	
	/**
	 * 第三方回调后，通过扩展点进行异步业务处理
	 * @param json      第三方回调的报文(转化为JSON格式）
	 * @return
	 */
	@Async
	Object execute(JSONObject json) throws Exception;

}
