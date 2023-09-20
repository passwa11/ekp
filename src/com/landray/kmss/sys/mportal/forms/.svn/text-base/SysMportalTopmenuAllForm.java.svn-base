package com.landray.kmss.sys.mportal.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.util.AutoArrayList;

public class SysMportalTopmenuAllForm extends ExtendForm {

	@Override
	public Class getModelClass() {
		return null;
	}
	
	private AutoArrayList topmenus = new AutoArrayList(
			SysMportalTopmenuForm.class);

	public AutoArrayList getTopmenus() {
		return topmenus;
	}

	public void setTopmenus(AutoArrayList topmenus) {
		this.topmenus = topmenus;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.topmenus.clear();
	}
}
