package com.landray.kmss.km.calendar.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.calendar.forms.KmCalendarSyncBindForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 同步绑定信息
 * 
 * @author
 * @version 1.0 2013-10-14
 */
@SuppressWarnings("serial")
public class KmCalendarSyncBind extends BaseModel {

	/**
	 * 所属应用
	 */
	protected String fdAppKey;

	public String getFdAppKey() {
		return fdAppKey;
	}

	public void setFdAppKey(String fdAppKey) {
		this.fdAppKey = fdAppKey;
	}

	/**
	 * 同步时间戳
	 */
	protected Date fdSyncTimestamp;

	/**
	 * @return 同步时间戳
	 */
	public Date getFdSyncTimestamp() {
		return fdSyncTimestamp;
	}

	/**
	 * @param fdSyncTimestamp
	 *            同步时间戳
	 */
	public void setFdSyncTimestamp(Date fdSyncTimestamp) {
		this.fdSyncTimestamp = fdSyncTimestamp;
	}

	/**
	 * 所属人员
	 */
	protected SysOrgElement fdOwner;

	public SysOrgElement getFdOwner() {
		return fdOwner;
	}

	public void setFdOwner(SysOrgElement fdOwner) {
		this.fdOwner = fdOwner;
	}

	@Override
    @SuppressWarnings("unchecked")
	public Class getFormClass() {
		return KmCalendarSyncBindForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdCreator.fdId", "fdCreatorId");
			toFormPropertyMap.put("fdCreator.fdName", "fdCreatorName");
		}
		return toFormPropertyMap;
	}
}
