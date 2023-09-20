package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2019年6月27日
*@Description                合同类型
*/

public enum ElecContractTypeEnum {
	
	//买卖合同
	MM_CONT("MM"),
	
	//供用电水气热力合同
	GY_CONT("GY"),
	
	//赠与合同
	ZY_CONT("ZY"),
	
	//借款合同
	JK_CONT("JK"),
	
	//租赁合同
	ZL_CONT("ZL"),
	
	//融资租赁合同
	RZZL_CONT("RZZL"),
	
	//承揽合同
	CL_CONT("CL"),
	
	//建设工程合同
	JSGC_CONT("JSGC"),
	
	//运输合同
	YS_CONT("YS"),
	
	//技术合同
	JS_CONT("JS"),
	
	//保管合同
	BG_CONT("BG"),
	
	//仓储合同
	CC_CONT("CC"),
	
	//委托合同
	WT_CONT("WT"),
	
	//行纪合同
	XJ_CONT("XJ"),
	
	//居间合同
	JJ_CONT("JJ"),
	
	//劳动合同
	LD_CONT("LD"),
	
	//其他合同
	QT_CONT("QT");
	
	;
	
	private String value;
	
	ElecContractTypeEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static ElecContractTypeEnum getContractType(String value){
		for(ElecContractTypeEnum contractType : ElecContractTypeEnum.values()){
			if(contractType.getValue().equals(value)){
				return contractType;
			}
		}
		
		return null;
	}

}
