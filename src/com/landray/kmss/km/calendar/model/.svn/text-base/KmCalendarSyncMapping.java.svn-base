package com.landray.kmss.km.calendar.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.calendar.forms.KmCalendarSyncMappingForm;

/**
 * 同步映射关联
 * 
 * @author 
 * @version 1.0 2013-10-14
 */
@SuppressWarnings("serial")
public class KmCalendarSyncMapping extends BaseModel {

	/**
	 * 日程ID
	 */
	protected String fdCalendarId;
	
	/**
	 * @return 日程ID
	 */
	public String getFdCalendarId() {
		return fdCalendarId;
	}
	
	/**
	 * @param fdCalendarId 日程ID
	 */
	public void setFdCalendarId(String fdCalendarId) {
		this.fdCalendarId = fdCalendarId;
	}
	
	/**
	 * 所属应用（模块）
	 */
	protected String fdAppKey;
	
	/**
	 * @return 所属应用（模块）
	 */
	public String getFdAppKey() {
		return fdAppKey;
	}
	
	/**
	 * @param fdAppKey 所属应用（模块）
	 */
	public void setFdAppKey(String fdAppKey) {
		this.fdAppKey = fdAppKey;
	}
	
	/**
	 * 映射UUID
	 */
	protected String fdAppUuid;
	
	/**
	 * @return 映射UUID
	 */
	public String getFdAppUuid() {
		return fdAppUuid;
	}
	
	/**
	 * @param fdAppUuid 映射UUID
	 */
	public void setFdAppUuid(String fdAppUuid) {
		this.fdAppUuid = fdAppUuid;
	}
	
	@Override
    @SuppressWarnings("unchecked")
	public Class getFormClass() {
		return KmCalendarSyncMappingForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}
}
