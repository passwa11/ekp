package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2019年10月18日
*@Description               印章类型
*/

public class ElecChannelSealType implements IElecChannelRequestMessage {

	private static final long serialVersionUID = 1L;
	
	private String sealTypeId;
	
	private String sealTypeName;
	
	public ElecChannelSealType() {
		super();
	}

	public ElecChannelSealType(String sealTypeId, String sealTypeName) {
		super();
		this.sealTypeId = sealTypeId;
		this.sealTypeName = sealTypeName;
	}

	public String getSealTypeId() {
		return sealTypeId;
	}

	public void setSealTypeId(String sealTypeId) {
		this.sealTypeId = sealTypeId;
	}

	public String getSealTypeName() {
		return sealTypeName;
	}

	public void setSealTypeName(String sealTypeName) {
		this.sealTypeName = sealTypeName;
	}
}
