package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2019年7月23日
*@Description               确认签暑时的认证类型
*/

public enum ElecSignAuthTypeEnum {

	SMS_VERIFICATION_CODE("短信验证码"),
	
	EMAIL_VERIFICATION_CODE("邮箱验证码"),
	
	SIGN_PASSWORD("签署密码"),

	LIVING_AUTHENTICATE("活体认证"),
	
	USBKEY_AUTHENTICATE("usbkey认证"),
	
	PHONE_SHIELD_AUTHENTICATE("手机盾认证")
	
	;
	
	
	private String desc;
	
	ElecSignAuthTypeEnum(String desc){
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	
	public String getName(){
		return this.name();
	}
}
