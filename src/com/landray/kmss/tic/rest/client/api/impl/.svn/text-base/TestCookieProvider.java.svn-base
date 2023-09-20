package com.landray.kmss.tic.rest.client.api.impl;

import com.alibaba.fastjson.JSONObject;
import org.json.simple.JSONArray;

import com.landray.kmss.tic.rest.client.api.ICookieProvider;

public class TestCookieProvider implements ICookieProvider {

	@Override
	public String getCookies(String restMainId, JSONObject in, String url) {
		// TODO Auto-generated method stub
		// [{"domain":"java.landray.com.cn","name":"route","path":"/","value":"40217fab86629be630f69fd2ef4aab3c"}.{"domain":"java.landray.com.cn","name":"JSESSIONID","path":"/","value":"15A281143604B2D847A0A28B80DF46E4"}]
		JSONArray array = new JSONArray();
		JSONObject o = new JSONObject();
		o.put("domain", "java.landray.com.cn");
		o.put("name", "route");
		o.put("path", "/");
		o.put("value", "40217fab86629be630f69fd2ef4aab3c");
		array.add(o);

		o = new JSONObject();
		o.put("domain", "java.landray.com.cn");
		o.put("name", "JSESSIONID");
		o.put("path", "/");
		o.put("value", "15A281143604B2D847A0A28B80DF46E4");
		array.add(o);

		return array.toString();
	}

}
