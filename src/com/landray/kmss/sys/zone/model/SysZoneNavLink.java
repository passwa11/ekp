/**
 * 
 */
package com.landray.kmss.sys.zone.model;

import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.person.model.BaseNavLink;
import com.landray.kmss.sys.zone.forms.SysZoneNavLinkForm;
import com.landray.kmss.sys.zone.util.SysZoneConfigUtil;
import com.landray.kmss.util.StringUtil;

/**
 * @author 傅游翔
 */
@SuppressWarnings("serial")
public class SysZoneNavLink extends BaseNavLink<SysZoneNavigation> {

	@Override
    public Class<?> getFormClass() {
		return SysZoneNavLinkForm.class;
	}
	
	private String fdServerKey;


	public String getFdServerKey() {
		return fdServerKey;
	}

	public void setFdServerKey(String fdServerKey) {
		this.fdServerKey = fdServerKey;
	}

	public String getServerName() {
		return SysZoneConfigUtil.getServerName(getFdServerKey());
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

	/**
	 * 子系统url前缀
	 * @return
	 */
	public String getServerPath() {
		return SysZoneConfigUtil.getServerUrl(getFdServerKey());
	}
	
	private String fdTarget;
	
	@Override
    public void setFdTarget(String fdTarget) {
		this.fdTarget = fdTarget;
	}

	@Override
    public String getFdTarget() {
		if(StringUtil.isNull(fdTarget)) {
			this.fdTarget = "_self";
		}
		return fdTarget;
	}
	
	
	private Boolean fdIsUserDef;


	public Boolean getFdIsUserDef() {
		return fdIsUserDef;
	}

	public void setFdIsUserDef(Boolean fdIsUserDef) {
		this.fdIsUserDef = fdIsUserDef;
	}

	/**
	 * 按多语言获取名称
	 */
	@Override
	public String getFdName() {
		// 按多语言获取对应的数据
		return SysLangUtil.getPropValue(this, "fdName", super.getFdName());
	}

	/**
	 * 按多语言设置名称
	 */
	@Override
	public void setFdName(String fdName) {
		super.setFdName(fdName);
		// 按多语言保存对应的数据
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}

	/**
	 * 获取原始名称
	 * @return
	 */
	public String getFdNameOri() {
		return super.getFdName();
	}
}
