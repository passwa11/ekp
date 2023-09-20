package com.landray.kmss.third.weixin.cluster;

import com.landray.kmss.sys.cluster.interfaces.message.IMessage;

import java.util.Map;

public class ThirdWeixinConfigMessage implements IMessage {

	private static final long serialVersionUID = 137872510150025444L;
	
	private String messageType = null;
	
	private Map<String, String> setting = null;

	public ThirdWeixinConfigMessage(String messageType) {
		this.setMessageType(messageType);
	}
	
	public ThirdWeixinConfigMessage(String messageType, Map<String, String> setting) {
		this.setMessageType(messageType);
		this.setting = setting;
	}

	public ThirdWeixinConfigMessage() {
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
