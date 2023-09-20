package com.landray.kmss.third.feishu.model;

import net.sf.json.JSONObject;

public class ThirdFeishuToken {

	private String tokenStr;

	public String getTokenStr() {
		return tokenStr;
	}

	public void setTokenStr(String tokenStr) {
		this.tokenStr = tokenStr;
	}

	private Long expireIn;

	private Long createdAt;

	private Long expireAt;

	private int tokenType;

	public ThirdFeishuToken(String token, Long expireIn, int tokenType) {
		this.tokenStr = token;
		this.expireIn = expireIn;
		this.tokenType = tokenType;
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
		o.put("tokenStr", tokenStr);
		o.put("tokenType", tokenType);
		o.put("expireAt", expireAt);
		o.put("createdAt", createdAt);
		return o.toString();
	}

}
