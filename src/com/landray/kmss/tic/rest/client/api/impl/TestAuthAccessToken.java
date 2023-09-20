package com.landray.kmss.tic.rest.client.api.impl;

import com.landray.kmss.tic.rest.client.api.IOAuthAccessToken;

public class TestAuthAccessToken implements IOAuthAccessToken {

	@Override
	public String getAccessToken() {
		// TODO Auto-generated method stub
		return "{\"access_token\":\"abc\",\"expires_in\":720}";
	}

}
