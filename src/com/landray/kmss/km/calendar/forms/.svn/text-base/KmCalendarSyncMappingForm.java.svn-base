package com.landray.kmss.km.calendar.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.calendar.model.KmCalendarSyncMapping;

/**
 * 同步映射关联 Form
 * 
 * @author 
 * @version 1.0 2013-10-14
 */
@SuppressWarnings("serial")
public class KmCalendarSyncMappingForm extends ExtendForm {

	/**
	 * 日程ID
	 */
	protected String fdCalendarId = null;
	
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
	protected String fdAppKey = null;
	
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
	protected String fdAppUuid = null;
	
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
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdCalendarId = null;
		fdAppKey = null;
		fdAppUuid = null;
		
		super.reset(mapping, request);
	}

	@Override
    @SuppressWarnings("unchecked")
	public Class getModelClass() {
		return KmCalendarSyncMapping.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}
}
