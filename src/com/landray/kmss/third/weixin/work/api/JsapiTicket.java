package com.landray.kmss.third.weixin.work.api;

import com.alibaba.fastjson.JSONObject;

public class JsapiTicket {

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	private String corpId;

	private String ticket;

	private Long expireTime;

	public JsapiTicket(String corpId, String ticket, Long expireTime) {
		this.corpId = corpId;
		this.setTicket(ticket);
		this.expireTime = expireTime;
	}

	public boolean isExpired() {
		boolean result = System
				.currentTimeMillis() > this.expireTime;
		return result;
	}

	public synchronized void updateTicket(String ticket,
			int expiresInSeconds) {
		this.setTicket(ticket);
		this.expireTime = System.currentTimeMillis()
				+ (expiresInSeconds - 200) * 1000L;
	}

	public void expireJsapiTicket() {
		this.expireTime = 0L;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@Override
	public String toString(){
		return JSONObject.toJSON(this).toString();
	}

}
