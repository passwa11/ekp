package com.landray.kmss.elec.device.client;

/**
*@author yucf
*@date  2019年10月18日
*@Description                    渠道部门/公司信息
*/

public class ElecChannelOrgInfo implements IElecChannelRequestMessage {

	private static final long serialVersionUID = 1L;
	
	private String orgId;
	
	private String orgName;
	
	private String parentId;
	
	//0公司1部门
	private String type = "1";

	public String getOrgId() {
		return orgId;
	}

	public ElecChannelOrgInfo setOrgId(String orgId) {
		this.orgId = orgId;
		return this;
	}

	public String getOrgName() {
		return orgName;
	}

	public ElecChannelOrgInfo setOrgName(String orgName) {
		this.orgName = orgName;
		return this;
	}

	public String getParentId() {
		return parentId;
	}

	public ElecChannelOrgInfo setParentId(String parentId) {
		this.parentId = parentId;
		return this;
	}

	public String getType() {
		return type;
	}

	public ElecChannelOrgInfo setType(String type) {
		this.type = type;
		return this;
	}

	@Override
	public String toString() {
		return "ElecChannelOrgInfo [orgId=" + orgId + ", orgName=" + orgName
				+ ", parentId=" + parentId + ", type=" + type + "]";
	}
}
