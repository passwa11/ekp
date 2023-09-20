package com.landray.kmss.km.imeeting.integrate.boen.interfaces;

import net.sf.json.JSONObject;

public interface IMeetingBoenProvider {
	
	public boolean isEnabled() throws Exception;

	public String getUrl() throws Exception;

	public JSONObject getTopOrg() throws Exception;

	public String getToken() throws Exception;

	public String getAdmin() throws Exception;
	
	public String getAppKey() throws Exception;
	
	public String getAppSecret() throws Exception;

}
