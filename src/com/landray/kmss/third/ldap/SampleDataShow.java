package com.landray.kmss.third.ldap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SampleDataShow {

	private SampleDataShowBaseImp showImp = null;

	private SampleDataShowBaseImp getSampleDataShowImp() throws Exception {
		Map<String, String> config = new LdapDetailConfig().getDataMap();
		String url = config.get("kmss.ldap.config.url");
		if (url.startsWith("ldaps")) {
			return new SampleDataShowApacheImp();
		}
		return new SampleDataShowDefaultImp();
	}

	public String getOtherInfo() {
		return showImp.getOtherInfo();
	}

	public String getDeptInfo() {
		return showImp.getDeptInfo();
	}

	public String getPersonInfo() {
		return showImp.getPersonInfo();
	}

	public String getGroupInfo() {
		return showImp.getGroupInfo();
	}

	public String getPostInfo() {
		return showImp.getPostInfo();
	}

	public void handle(Map<String, String> dataMap) throws Exception {
		List records = new ArrayList();
		// BaseLdapService service = getService();
		showImp = getSampleDataShowImp();
		try {
			showImp.handle();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}



}
