/**
 * 
 */
package com.landray.kmss.sys.zone.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.sys.person.forms.BaseNavCategoryForm;
import com.landray.kmss.sys.zone.model.SysZoneNavigation;

/**
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class SysZoneNavigationForm extends
		BaseNavCategoryForm<SysZoneNavLinkForm> {

	@Override
    public Class<?> getModelClass() {
		return SysZoneNavigation.class;
	}

	@Override
	protected Class<SysZoneNavLinkForm> getLinkClass() {
		return SysZoneNavLinkForm.class;
	}

	private String fdShowType;
	
	public String getFdShowType() {
		return fdShowType;
	}

	public void setFdShowType(String fdShowType) {
		this.fdShowType = fdShowType;
	}
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.fdShowType = null;
	}
}
