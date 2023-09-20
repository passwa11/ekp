package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2019年7月2日
*@Description                    合同签暑状态
*/

public enum ElecSignmentStateEnum {
	
	//未签署
	NOT_SIGNED(0),
	
	//已签署
	SIGNED(1),
	
	//已拒绝
	REJECTED(2),
	
	//锁定待签
	TO_BE_SIGNED(3),
	
	//抄送
	CC(9)
	;
	
	private int value;
	
	ElecSignmentStateEnum(int value){
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public static ElecSignmentStateEnum getSignmentState(int state){
		
		for(ElecSignmentStateEnum contractState : ElecSignmentStateEnum.values()){
			if(contractState.getValue() == state){
				return contractState;
			}
		}
		
		return null;
	}
	
}
