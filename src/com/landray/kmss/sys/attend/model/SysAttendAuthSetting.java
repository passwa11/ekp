package com.landray.kmss.sys.attend.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendAuthSettingForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-08-28
 */
public class SysAttendAuthSetting extends BaseModel {

	/**
	 * 人员
	 */
	private List<SysOrgElement> fdElements;

	/**
	 * 有权限查看的范围
	 */
	private List<SysOrgElement> fdAuthList;

	public List<SysOrgElement> getFdElements() {
		return fdElements;
	}

	public void setFdElements(List<SysOrgElement> fdElements) {
		this.fdElements = fdElements;
	}

	public List<SysOrgElement> getFdAuthList() {
		return fdAuthList;
	}

	public void setFdAuthList(List<SysOrgElement> fdAuthList) {
		this.fdAuthList = fdAuthList;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdElements",
					new ModelConvertor_ModelListToString(
							"fdElementIds:fdElementNames", "fdId:fdName"));
			toFormPropertyMap.put("fdAuthList",
					new ModelConvertor_ModelListToString(
							"fdAuthIds:fdAuthNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}

	@Override
	public Class getFormClass() {
		return SysAttendAuthSettingForm.class;
	}

}
