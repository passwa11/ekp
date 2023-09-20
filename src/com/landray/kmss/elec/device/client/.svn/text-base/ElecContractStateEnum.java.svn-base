package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2019年7月2日
*@Description                  合同状态
*/

public enum ElecContractStateEnum {
	
	//未完成
	INCOMPLETE(0),
	
	//已完成
	COMPLETED(1),
	
	//已拒绝
	REJECTED(2),
	
	//正在签署
	SIGNING(3),
	
	//锁定待签
	TO_BE_SIGNED(4),
	
	//已过期
	EXPIRED(9)
	;
	
	private int value;
	
	ElecContractStateEnum(int value){
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public static ElecContractStateEnum getContractState(int state){
		
		for(ElecContractStateEnum contractState : ElecContractStateEnum.values()){
			if(contractState.getValue() == state){
				return contractState;
			}
		}
		
		return null;
	}
	

}
