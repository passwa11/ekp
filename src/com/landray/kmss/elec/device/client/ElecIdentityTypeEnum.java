package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2019年6月27日
*@Description                证件类型
*/

public enum ElecIdentityTypeEnum {

	//居民身份证
	IDENTITY_CARD("0"),
	
	//护照
	PASSPORT("1"),
	
	//企业营业执照
	BUSINESS_LICENSE("8"),
	
	//港澳居民往来内地通行证
	HK_MO_MAINLAND_PASS("B"),
	
	//台湾居民来往大陆通行证
	TW_MAINLAND_PASS("C"),
	
	//事业单位法人证书
	SYDWFR_CERT("H"),
	
	//社会团体登记证书
	SHTT_REG_CERT("J"),
	
	//企业统一社会信用代码
	ENTERPRISE_CREDIT_CODE("N")
	
	;
	
	private String value;
	
	ElecIdentityTypeEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static ElecIdentityTypeEnum getIdentiType(String value){
		for(ElecIdentityTypeEnum identityType : ElecIdentityTypeEnum.values()){
			if(identityType.getValue().equals(value)){
				return identityType;
			}
		}
		
		return null;
	}

}
