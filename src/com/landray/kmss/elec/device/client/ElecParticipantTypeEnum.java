package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2019年7月23日
*@Description               参与者类型
*/

public enum ElecParticipantTypeEnum {
	
	PERSON("个人性质"),
	ENTERPRISE("企业性质")
	;
	
	
	private String desc;
	
	ElecParticipantTypeEnum(String desc){
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	
	public String getName(){
		return this.name();
	}
}
