package com.landray.kmss.third.feishu.cluster;

import java.util.Map;

import com.landray.kmss.sys.cluster.interfaces.message.IMessage;

public class ThirdFeishuConfigMessage implements IMessage {

	private static final long serialVersionUID = 137872510150025444L;
	
	private String messageType = null;
	
	private Map<String, String> setting = null;

	public ThirdFeishuConfigMessage(String messageType) {
		this.setMessageType(messageType);
	}
	
	public ThirdFeishuConfigMessage(String messageType, Map<String, String> setting) {
		this.setMessageType(messageType);
		this.setting = setting;
	}

	public ThirdFeishuConfigMessage() {
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
