package com.landray.kmss.sys.organization.webservice.org;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.landray.kmss.sys.organization.webservice.SysOrgWebserviceConstant;

public class SysSynchroStaffingLevelInfo implements SysOrgWebserviceConstant {

	private JSONObject data;

	public SysSynchroStaffingLevelInfo(JSONObject orgData) {
		this.data = orgData;
	}

	public String getId() {
		return (String) this.data.get(ID);
	}

	public String getName() {
		return (String) this.data.get(NAME);
	}

	public Integer getLevel() {
		return Integer.valueOf(this.data.get(LEVEL)+"");
	}

	public Boolean getIsDefault() {
		return (Boolean) this.data.get(IS_DEFAULT);
	}

	public String getDescription() {
		return (String) this.data.get(DESCRIPTION);
	}

	public List getPersons() {
		return (JSONArray) this.data.get(PERSONS);
	}

}
