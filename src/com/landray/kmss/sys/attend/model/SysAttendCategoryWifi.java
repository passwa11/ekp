package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendCategoryWifiForm;

/**
 * wifi签到配置
 * 
 * @author admin
 *
 */
public class SysAttendCategoryWifi extends BaseModel {

	private String fdName;

	private String fdMacIp;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdMacIp() {
		return fdMacIp;
	}

	public void setFdMacIp(String fdMacIp) {
		this.fdMacIp = fdMacIp;
	}

	private SysAttendCategory fdCategory;

	public SysAttendCategory getFdCategory() {
		return fdCategory;
	}

	public void setFdCategory(SysAttendCategory fdCategory) {
		this.fdCategory = fdCategory;
	}

	@Override
	public Class<SysAttendCategoryWifiForm> getFormClass() {
		return SysAttendCategoryWifiForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdCategory.fdId", "fdCategoryId");
		}
		return toFormPropertyMap;
	}

}
