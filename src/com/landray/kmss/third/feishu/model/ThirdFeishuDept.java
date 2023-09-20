package com.landray.kmss.third.feishu.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ThirdFeishuDept {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String gethName() {
		return hName;
	}

	public void sethName(String hName) {
		this.hName = hName;
	}

	public String gethId() {
		return hId;
	}

	public void sethId(String hId) {
		this.hId = hId;
	}

	private String name;

	private String parentid;

	private String hName;

	private String hId;

	@Override
    public String toString() {
		return JSONObject.toJSON(this).toString();
	}

}
