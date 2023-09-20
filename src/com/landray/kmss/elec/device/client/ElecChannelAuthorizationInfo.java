package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2020年2月13日
*@Description                     权限信息,可用于用户角色/角色权限/印章用户等权限关联信息包装
*/

public class ElecChannelAuthorizationInfo implements IElecChannelRequestMessage {

	private static final long serialVersionUID = 1L;
	
	//2个关联实体的ID
	
	private String sourceId;
	
	private String targetId;
	
	//权限类型
	private ElecChannelAuthorizationTypeEnum type;

	public String getSourceId() {
		return sourceId;
	}

	public ElecChannelAuthorizationInfo setSourceId(String sourceId) {
		this.sourceId = sourceId;
		return this;
	}

	public String getTargetId() {
		return targetId;
	}

	public ElecChannelAuthorizationInfo setTargetId(String targetId) {
		this.targetId = targetId;
		return this;
	}

	public ElecChannelAuthorizationTypeEnum getType() {
		return type;
	}

	public ElecChannelAuthorizationInfo setType(ElecChannelAuthorizationTypeEnum type) {
		this.type = type;
		return this;
	}

	@Override
	public String toString() {
		return "ElecChannelAuthorizationInfo [sourceId=" + sourceId
				+ ", targetId=" + targetId + ", type=" + type + "]";
	}
	
	
}
