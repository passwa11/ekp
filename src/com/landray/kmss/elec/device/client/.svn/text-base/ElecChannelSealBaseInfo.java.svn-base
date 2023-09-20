package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2019年10月18日
*@Description                 印章基本信息
*/

public class ElecChannelSealBaseInfo implements IElecChannelRequestMessage {

	private static final long serialVersionUID = 1L;
	
	//印章ID
	private String sealId;
	
	//印章编号(硬件上的编号)
	private String sealNo;
	
	//印章名称
	private String sealName;
	
	public ElecChannelSealBaseInfo() {
		super();
	}

	public ElecChannelSealBaseInfo(String sealId, String sealNo,
			String sealName) {
		super();
		this.sealId = sealId;
		this.sealNo = sealNo;
		this.sealName = sealName;
	}

	public String getSealId() {
		return sealId;
	}

	public ElecChannelSealBaseInfo setSealId(String sealId) {
		this.sealId = sealId;
		return this;
	}

	public String getSealName() {
		return sealName;
	}

	public ElecChannelSealBaseInfo setSealName(String sealName) {
		this.sealName = sealName;
		return this;
	}
	
	public String getSealNo() {
		return sealNo;
	}

	public ElecChannelSealBaseInfo setSealNo(String sealNo) {
		this.sealNo = sealNo;
		return this;
	}

	@Override
	public String toString() {
		return "ElecChannelSealBaseInfo [sealId=" + sealId + ", sealNo="
				+ sealNo + ", sealName=" + sealName + "]";
	}
}
