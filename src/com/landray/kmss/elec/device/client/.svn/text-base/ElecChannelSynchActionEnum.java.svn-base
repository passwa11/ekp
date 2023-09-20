package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2019年10月18日
*@Description                  系统同步动作
*/

public enum ElecChannelSynchActionEnum {
	
	//添加
	ADD(0),
	//更新
	UPDATE(1),
	//删除
	DELETE(2)
	;
	
	private int value;
	
	ElecChannelSynchActionEnum(int value){
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public static ElecChannelSynchActionEnum getSynchAction(int action){
		
		for(ElecChannelSynchActionEnum contractState : ElecChannelSynchActionEnum.values()){
			if(contractState.getValue() == action){
				return contractState;
			}
		}
		
		return null;
	}

}
