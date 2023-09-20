/**
 * 
 */
package com.landray.kmss.sys.zone.model;

import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.person.model.BaseNavCategory;
import com.landray.kmss.sys.zone.forms.SysZoneNavigationForm;

/**
 * @author 傅游翔
 */
@SuppressWarnings("serial")
public class SysZoneNavigation extends BaseNavCategory<SysZoneNavLink> {

	private String fdShowType;

	public String getFdShowType() {
		return fdShowType;
	}

	public void setFdShowType(String fdShowType) {
		this.fdShowType = fdShowType;
	}

	@Override
    public Class<?> getFormClass() {
		return SysZoneNavigationForm.class;
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
