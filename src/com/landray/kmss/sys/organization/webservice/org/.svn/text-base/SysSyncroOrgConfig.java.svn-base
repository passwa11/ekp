package com.landray.kmss.sys.organization.webservice.org;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.landray.kmss.sys.organization.webservice.SysOrgWebserviceConstant;
import com.landray.kmss.util.StringUtil;

public class SysSyncroOrgConfig implements SysOrgWebserviceConstant {
	private List org;

	private List dept;

	private List group;

	private List post;

	private List person;

	public SysSyncroOrgConfig() {
		this(ORG_WEB_SET_CONFIG_DEFAULT);
	}

	public SysSyncroOrgConfig(String jsonConf) {
		initConfig(jsonConf);
	}

	private void initConfig(String jsonConf) {
		JSONObject confs = null;
		if (StringUtil.isNotNull(jsonConf)) {
			confs = (JSONObject) JSONValue.parse(jsonConf);
		} else {
			confs = (JSONObject) JSONValue
					.parse(ORG_WEB_SET_CONFIG_DEFAULT);
		}
		this.org = (JSONArray)confs.get(ORG_WEB_TYPE_ORG);
		this.dept = (JSONArray)confs.get(ORG_WEB_TYPE_DEPT);
		this.group = (JSONArray)confs.get(ORG_WEB_TYPE_GROUP);
		this.post = (JSONArray)confs.get(ORG_WEB_TYPE_POST);
		this.person = (JSONArray)confs.get(ORG_WEB_TYPE_PERSON);
	}


	public List getOrg() {
		return org;
	}

	public void setOrg(List org) {
		this.org = org;
	}

	public List getDept() {
		return dept;
	}

	public void setDept(List dept) {
		this.dept = dept;
	}

	public List getGroup() {
		return group;
	}

	public void setGroup(List group) {
		this.group = group;
	}

	public List getPost() {
		return post;
	}

	public void setPost(List post) {
		this.post = post;
	}

	public List getPerson() {
		return person;
	}

	public void setPerson(List person) {
		this.person = person;
	}
}
