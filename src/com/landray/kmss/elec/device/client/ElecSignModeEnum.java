package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2019年12月18日
*@Description               签暑模式
*/

public enum ElecSignModeEnum {
	
	//嵌入式
	EMBEDDED(true),
	
	//非嵌入式
	NON_EMBEDDED(false)
	
	;
	
	private boolean value;
	
	private ElecSignModeEnum(boolean value){
		this.value = value;
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}
	
	

}
