package com.landray.kmss.sys.organization.webservice.org;

import org.json.simple.JSONObject;

import com.landray.kmss.sys.organization.webservice.SysOrgWebserviceConstant;

public class SysSynchroElementBaseInfo implements SysOrgWebserviceConstant {

	private JSONObject data;

	public SysSynchroElementBaseInfo(JSONObject orgData) {
		this.data = orgData;
	}

	public String getId() {
		return (String) this.data.get(ID);
	}

	public String getLunid() {
		return (String) this.data.get(LUNID);
	}

	public String getName() {
		return (String) this.data.get(NAME);
	}

	public String getType() {
		Object type = this.data.get(TYPE);
		if (type != null) {
            return "" + type;
        }
		return null;
	}

	public String getOrder() {
		Object order = this.data.get(ORDER);
		if (order != null) {
            return "" + order;
        }
		return null;
	}

	public String getNo() {
		Object no = this.data.get(NO);
		if (no != null) {
            return "" + no;
        }
		return null;
	}

	public String getKeyword() {
		return (String) this.data.get(KEYWORD);
	}
}
