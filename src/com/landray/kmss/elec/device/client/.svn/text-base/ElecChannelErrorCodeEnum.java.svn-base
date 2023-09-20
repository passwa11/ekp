package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2019年7月9日
*@Description        渠道响应码
*/

public enum ElecChannelErrorCodeEnum {
	
	SUCCESS("0000","SUCCESS"),
	
	//业务类异常
	ILLEGAL_PARAM("0001", "非法参数！"),
	
	//不支持或未开通此功能！
	IS_NOT_SUPPORTED_OR_ACTIVATED("0002", "不支持或未开通此功能！"),
	
	//第三方异常
	THIRD_PARTY_RESPONSE_EXCEPTION("3001", "第三方响应异常"),
	
	//未知异常
	UNKNOWN_EXCEPTION("500", "unknow exception！"),
	
	//服务暂不能使用
	SERVICE_IS_UNAVAILABLE("400","服务暂不能使用！");
	
	
	ElecChannelErrorCodeEnum(String code, String desc){
		this.code = code;
		this.desc = desc;
	}
	
	private String code;
	
	private String desc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
