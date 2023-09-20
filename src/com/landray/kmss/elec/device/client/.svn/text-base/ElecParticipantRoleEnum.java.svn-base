package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2019年7月23日
*@Description                   参与者角色
*/

public enum ElecParticipantRoleEnum {
	
	SIGNER("签署者", "具有签署行为"),
	CHECKER("审核者", "没有签署行为，但可以审核通过或不通过"),
	CC_USER("抄送人", "类似于邮件抄送");
	
	private String name;
	
	private String desc;
	
	ElecParticipantRoleEnum(String name, String desc){
		this.desc = desc;
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}
	
	public String getName(){
		return name;
	}
}
