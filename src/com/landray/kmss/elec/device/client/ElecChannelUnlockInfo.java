package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2019年8月9日
*@Description            设备解锁信息
*/

public class ElecChannelUnlockInfo implements IElecChannelRequestMessage, IElecChannelResponseMessage {

	private static final long serialVersionUID = 1L;
	
	//认证ID
	private String tokenId;
	
	//授权ID
	private String auditId;

	public String getTokenId() {
		return tokenId;
	}

	public ElecChannelUnlockInfo setTokenId(String tokenId) {
		this.tokenId = tokenId;
		return this;
	}

	public String getAuditId() {
		return auditId;
	}

	public ElecChannelUnlockInfo setAuditId(String auditId) {
		this.auditId = auditId;
		return this;
	}

	@Override
	public String toString() {
		return "ElecChannelUnlockInfo [tokenId=" + tokenId + ", auditId="
				+ auditId + "]";
	}
}
