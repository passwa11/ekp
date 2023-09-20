package com.landray.kmss.third.weixin.model.api;

import com.alibaba.fastjson.annotation.JSONField;

public class WxAgent {
	private String agentid;
	private String name;
	@JSONField(name = "square_logo_url")
	private String squareLogoUrl;

	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSquareLogoUrl() {
		return squareLogoUrl;
	}

	public void setSquareLogoUrl(String squareLogoUrl) {
		this.squareLogoUrl = squareLogoUrl;
	}
}
