package com.landray.kmss.elec.device.client;

import java.util.HashMap;
import java.util.Map;

/**
*@author yucf
*@date  2019年10月24日
*@Description               动态查询参数
*/

public class ElecChannelQueryParam implements IElecChannelRequestMessage {

	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> paramMap;

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}
	
	public ElecChannelQueryParam addParam(String key, Object value){
		if(this.paramMap == null){
			this.paramMap = new HashMap<>();
		}
		
		this.paramMap.put(key, value);
		
		return this;
	}
}
