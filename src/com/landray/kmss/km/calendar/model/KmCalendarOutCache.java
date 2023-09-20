package com.landray.kmss.km.calendar.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.calendar.forms.KmCalendarOutCacheForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 日程接出缓存
 * 
 * @author 
 * @version 1.0 2013-10-14
 */
@SuppressWarnings("serial")
public class KmCalendarOutCache extends BaseModel {

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
	 * 同步到哪
	 */
	protected String fdAppKey;
	
	/**
	 * @return 同步到哪
	 */
	public String getFdAppKey() {
		return fdAppKey;
	}
	
	/**
	 * @param fdAppKey 同步到哪
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
	
	/**
	 * 操作类型
	 */
	protected String fdOperationType;
	
	/**
	 * @return 操作类型
	 */
	public String getFdOperationType() {
		return fdOperationType;
	}
	
	/**
	 * @param fdOperationType 操作类型
	 */
	public void setFdOperationType(String fdOperationType) {
		this.fdOperationType = fdOperationType;
	}
	
	/**
	 * 操作时间
	 */
	protected Date fdOperationDate;
	
	/**
	 * @return 操作时间
	 */
	public Date getFdOperationDate() {
		return fdOperationDate;
	}
	
	/**
	 * @param fdOperationDate 操作时间
	 */
	public void setFdOperationDate(Date fdOperationDate) {
		this.fdOperationDate = fdOperationDate;
	}
	
	/**
	 * 所属用户
	 */
	protected SysOrgElement fdOwner;
	
	/**
	 * @return 所属用户
	 */
	public SysOrgElement getFdOwner() {
		return fdOwner;
	}
	
	/**
	 * @param FdOwner 所属用户
	 */
	public void setFdOwner(SysOrgElement fdOwner) {
		this.fdOwner = fdOwner;
	}
	
	@Override
    @SuppressWarnings("unchecked")
	public Class getFormClass() {
		return KmCalendarOutCacheForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdOwner.fdId", "fdOwnerId");
			toFormPropertyMap.put("fdOwner.fdName", "fdOwnerName");
		}
		return toFormPropertyMap;
	}
}
