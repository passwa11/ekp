package com.landray.kmss.third.ldap.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.BaseForm;

public class LdapSettingForm extends BaseForm {
	private Map map = new HashMap();

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Object getValue(String key) {
		return map.get(key);
	}

	public void setValue(String key, Object value) {
		map.put(key, value);
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		map.clear();
	}
}
