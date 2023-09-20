package com.landray.kmss.km.imeeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.imeeting.model.KmImeetingSyncMapping;

/**
 * 会议同步映射关联 Form
 */
public class KmImeetingSyncMappingForm  extends ExtendForm  {

	/**
	 * 会议ID
	 */
	private String fdMeetingId;
	
	/**
	 * @return 会议ID
	 */
	public String getFdMeetingId() {
		return this.fdMeetingId;
	}
	
	/**
	 * @param fdMeetingId 会议ID
	 */
	public void setFdMeetingId(String fdMeetingId) {
		this.fdMeetingId = fdMeetingId;
	}
	
	/**
	 * 映射UUID
	 */
	private String fdAppUuid;
	
	/**
	 * @return 映射UUID
	 */
	public String getFdAppUuid() {
		return this.fdAppUuid;
	}
	
	/**
	 * @param fdAppUuid 映射UUID
	 */
	public void setFdAppUuid(String fdAppUuid) {
		this.fdAppUuid = fdAppUuid;
	}
	
	private String fdKey;

	public String getFdKey() {
		return fdKey;
	}

	public void setFdKey(String fdKey) {
		this.fdKey = fdKey;
	}

	private String extMsg;

	public String getExtMsg() {
		return extMsg;
	}

	public void setExtMsg(String extMsg) {
		this.extMsg = extMsg;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdMeetingId = null;
		fdAppUuid = null;
		super.reset(mapping, request);
	}

	@Override
    public Class<KmImeetingSyncMapping> getModelClass() {
		return KmImeetingSyncMapping.class;
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
