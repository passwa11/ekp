package com.landray.kmss.third.ekp.java.cluster;

import java.util.Map;

import com.landray.kmss.sys.cluster.interfaces.message.IMessage;

public class EkpJavaConfigMessage implements IMessage {

	private static final long serialVersionUID = 137872510150025444L;
	
	private String messageType = null;
	
	private Map<String, String> setting = null;

	public EkpJavaConfigMessage(String messageType) {
		this.setMessageType(messageType);
	}
	
	public EkpJavaConfigMessage(String messageType, Map<String, String> setting) {
		this.setMessageType(messageType);
		this.setting = setting;
	}

	public EkpJavaConfigMessage() {
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setSetting(Map<String, String> setting) {
		this.setting = setting;
	}

	public Map<String, String> getSetting() {
		return setting;
	}
}
