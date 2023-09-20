package com.landray.kmss.tic.rest.client.config;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * access token.
 *
 */
public class RestAccessToken implements Serializable {
	private String accessToken;

	private int expiresIn = -1;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public static RestAccessToken fromJson(String json) {
		RestAccessToken token = new RestAccessToken();
		JSONObject o = JSONObject.fromObject(json);
		token.setAccessToken(o.getString("access_token"));
		if(o.containsKey("expires_in")) {
			token.setExpiresIn(o.getInt("expires_in"));
		}else {
			token.setExpiresIn(7200);//统一为2小时
		}
		return token;
	}

}
