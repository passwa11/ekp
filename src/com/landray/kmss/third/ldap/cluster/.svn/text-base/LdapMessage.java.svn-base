package com.landray.kmss.third.ldap.cluster;

import java.util.Map;

import com.landray.kmss.sys.cluster.interfaces.message.IMessage;

public class LdapMessage implements IMessage {

	private static final long serialVersionUID = -6853316164519000106L;

	private String messageType = null;

	private Map<String, String> dataMap = null;

	public LdapMessage(String messageType, Map<String, String> dataMap) {
		this.setMessageType(messageType);
		this.setDataMap(dataMap);
	}

	public LdapMessage() {
	}


	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setDataMap(Map<String, String> dataMap) {
		this.dataMap = dataMap;
	}

	public Map<String, String> getDataMap() {
		return dataMap;
	}
}
