package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2020年2月13日
*@Description                       权限类型
*/

public enum ElecChannelAuthorizationTypeEnum {
	
	USER_ROLE(1),   //用户角色
	ROLE_RIGHT(2),  //角色权限
	SEAL_USER(3)   //印章用户
	
	;
	
	private int value;
	
	ElecChannelAuthorizationTypeEnum(int value){
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	

}
