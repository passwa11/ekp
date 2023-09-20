package com.landray.kmss.third.weixin.mutil.api;

public class AccessToken {
	private String accessToken = null;
	private long expiresTime = 0;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public long getExpiresTime() {
		return expiresTime;
	}

	public void setExpiresTime(long expiresTime) {
		this.expiresTime = expiresTime;
	}
}
