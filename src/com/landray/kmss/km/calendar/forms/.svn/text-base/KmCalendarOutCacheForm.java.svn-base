package com.landray.kmss.km.calendar.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.calendar.model.KmCalendarOutCache;
import com.landray.kmss.sys.organization.model.SysOrgElement;


/**
 * 日程接出缓存 Form
 * 
 * @author 
 * @version 1.0 2013-10-14
 */
@SuppressWarnings("serial")
public class KmCalendarOutCacheForm extends ExtendForm {

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
	 * 同步到哪
	 */
	protected String fdAppKey = null;
	
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
	
	/**
	 * 操作类型
	 */
	protected String fdOperationType = null;
	
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
	protected String fdOperationDate = null;
	
	/**
	 * @return 操作时间
	 */
	public String getFdOperationDate() {
		return fdOperationDate;
	}
	
	/**
	 * @param fdOperationDate 操作时间
	 */
	public void setFdOperationDate(String fdOperationDate) {
		this.fdOperationDate = fdOperationDate;
	}
	
	/**
	 * 所属用户的ID
	 */
	protected String fdOwnerId = null;
	
	/**
	 * @return 所属用户的ID
	 */
	public String getFdOwnerId() {
		return fdOwnerId;
	}
	
	/**
	 * @param fdOwnerId 所属用户的ID
	 */
	public void setFdOwnerId(String fdOwnerId) {
		this.fdOwnerId = fdOwnerId;
	}
	
	/**
	 * 所属用户的名称
	 */
	protected String fdOwnerName = null;
	
	/**
	 * @return 所属用户的名称
	 */
	public String getFdOwnerName() {
		return fdOwnerName;
	}
	
	/**
	 * @param fdOwnerName 所属用户的名称
	 */
	public void setFdOwnerName(String fdOwnerName) {
		this.fdOwnerName = fdOwnerName;
	}
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdCalendarId = null;
		fdAppKey = null;
		fdAppUuid = null;
		fdOperationType = null;
		fdOperationDate = null;
		fdOwnerId = null;
		fdOwnerName = null;
		
		super.reset(mapping, request);
	}

	@Override
    @SuppressWarnings("unchecked")
	public Class getModelClass() {
		return KmCalendarOutCache.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdOwnerId",new FormConvertor_IDToModel("fdOwner",SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
}
