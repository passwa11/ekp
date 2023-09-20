package com.landray.kmss.km.imeeting.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.imeeting.forms.KmImeetingSyncMappingForm;
import com.landray.kmss.util.StringUtil;

/**
 * 会议同步映射关联
 */
public class KmImeetingSyncMapping  extends BaseModel {

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
		// 解决兼容性问题，因为在刚开始做映射表时没添加fdkey字段，导致所有的exchange映射数据的key值为空，这里默认设置一下
		if (StringUtil.isNull(fdKey)) {
			fdKey = "exchangeMeeting";
		}
		return fdKey;
	}

	public void setFdKey(String fdKey) {
		this.fdKey = fdKey;
	}

	/**
	 * 额外信息，以JSON形式保存
	 */
	private String extMsg;

	public String getExtMsg() {
		return (String) readLazyField("extMsg", extMsg);
	}

	public void setExtMsg(String extMsg) {
		this.extMsg = (String) writeLazyField("extMsg", this.extMsg, extMsg);
	}

	@Override
    public Class<KmImeetingSyncMappingForm> getFormClass() {
		return KmImeetingSyncMappingForm.class;
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

	public String getFdAppIcalId() {
		return fdAppIcalId;
	}

	public void setFdAppIcalId(String fdAppIcalId) {
		this.fdAppIcalId = fdAppIcalId;
	}

	private String fdAppIcalId;
}
