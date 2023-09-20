package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.sys.cluster.interfaces.message.IMessage;

public class SysOrgMessage implements IMessage {

	private static final long serialVersionUID = -6853316164519000106L;

	private String messageType = null;

	private boolean isOrgVisibleEnable = false;

	public SysOrgMessage(String messageType) {
		this.setMessageType(messageType);
	}

	public SysOrgMessage() {
	}

	public void setOrgVisibleEnable(boolean isOrgVisibleEnable) {
		this.isOrgVisibleEnable = isOrgVisibleEnable;
	}

	public boolean isOrgVisibleEnable() {
		return isOrgVisibleEnable;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageType() {
		return messageType;
	}
}
