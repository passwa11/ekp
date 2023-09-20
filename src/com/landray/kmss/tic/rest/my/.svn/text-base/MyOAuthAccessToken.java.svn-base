package com.landray.kmss.tic.rest.my;

import java.util.HashMap;
import java.util.Map;

import com.landray.kmss.tic.rest.client.api.IOAuthAccessToken;

import net.sf.json.JSONObject;

public class MyOAuthAccessToken implements IOAuthAccessToken{
	@Override
    public String getAccessToken() {
		String url = "https://openapi.italent.cn/OAuth/Token";
		try {
			String app_id = "909";
			String tenant_id = "109089";
			String secret = "ebf94781fc0d4577a5600d28dc5824d4";

			String body = "app_id=" + app_id + "&tenant_id=" + tenant_id + "&grant_type=client_credentials&secret="
					+ secret;
			Map<String, String> map = new HashMap<String, String>();
			map.put("Content-Type", "application/x-www-form-urlencoded");
			JSONObject json = MyCustHttpClientUtil.httpPost(url, body, 5000, map);
			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
