/**
 * 
 */
package com.landray.kmss.sys.zone.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.sys.person.forms.BaseNavLinkForm;
import com.landray.kmss.sys.zone.model.SysZoneNavLink;
import com.landray.kmss.sys.zone.util.SysZoneConfigUtil;

/**
 * @author 傅游翔
 */
@SuppressWarnings("serial")
public class SysZoneNavLinkForm extends BaseNavLinkForm {

	@Override
    public Class<?> getModelClass() {
		return SysZoneNavLink.class;
	}
	
	private String fdServerKey;
	

	public String getFdServerKey() {
		return fdServerKey;
	}

	public void setFdServerKey(String fdServerKey) {
		this.fdServerKey = fdServerKey;
	}
	
	/**
	 * 移动端或是pc端
	 */
	private String fdShowType;
	
	
	public String getFdShowType() {
		return fdShowType;
	}

	public void setFdShowType(String fdShowType) {
		this.fdShowType = fdShowType;
	}
	
	public String getServerName() {
		return SysZoneConfigUtil.getServerName(getFdServerKey());
	}
	
	
	private String  fdIsUserDef;
	

	public String getFdIsUserDef() {
		return fdIsUserDef;
	}

	public void setFdIsUserDef(String fdIsUserDef) {
		this.fdIsUserDef = fdIsUserDef;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.fdServerKey = null;
		this.fdShowType = null;
		this.fdIsUserDef = null;
	}
}
