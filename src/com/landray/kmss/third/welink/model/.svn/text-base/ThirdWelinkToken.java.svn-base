package com.landray.kmss.third.welink.model;

import net.sf.json.JSONObject;

public class ThirdWelinkToken {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	private Long expireIn;

	private Long createdAt;

	private Long expireAt;


	public ThirdWelinkToken(String token, Long expireIn) {
		this.token = token;
		this.expireIn = expireIn;
		this.createdAt = System.currentTimeMillis();
		// 提前10分钟过期
		this.expireAt = System.currentTimeMillis() + ((expireIn - 600) * 1000L);
	}

	public boolean isExpired() {
		return System.currentTimeMillis() > expireAt;
	}

	@Override
	public String toString() {
		JSONObject o = new JSONObject();
		o.put("token", token);
		o.put("expireAt", expireAt);
		o.put("createdAt", createdAt);
		return o.toString();
	}

}
