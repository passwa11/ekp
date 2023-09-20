package com.landray.kmss.km.calendar.model;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.calendar.forms.KmCalendarDetailsForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * 日程相关人详情表
 * 
 * @author
 * @version 1.0 2021.6.15
 */
@SuppressWarnings("serial")
public class KmCalendarDetails extends BaseModel {
	private static ModelToFormPropertyMap toFormPropertyMap;
	private KmCalendarMain fdCalendar; //所属日程
	private SysOrgPerson fdPerson;//日程相关人
	private Boolean fdIsDelete; //是否删除
	private String fdLabelId; //所属标签
	/**
	 *日程相关人
	 */
	public SysOrgPerson getFdPerson() {
		return fdPerson;
	}

	/**
	 *日程相关人
	 */
	public void setFdPerson(SysOrgPerson fdPerson) {
		this.fdPerson = fdPerson;
	}

	/**
	 * 所属日程
	 */
	public KmCalendarMain getFdCalendar() {
		return fdCalendar;
	}
	/**
	 * 所属日程
	 */
	public void setFdCalendar(KmCalendarMain fdCalendar) {
		this.fdCalendar = fdCalendar;
	}

	/**
	 * 是否删除
	 */
	public Boolean getFdIsDelete() {
		return this.fdIsDelete;
	}
	/**
	 * 是否删除
	 */
	public void setFdIsDelete(Boolean fdIsDelete) {
		this.fdIsDelete = fdIsDelete;
	}

	/**
	 * 所属labelId
	 * @return
	 */

	public String getFdLabelId() {
		return fdLabelId;
	}

	public void setFdLabelId(String fdLabelId) {
		this.fdLabelId = fdLabelId;
	}
	@Override
	public Class getFormClass() {
		return KmCalendarDetailsForm.class;
	}
	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdCalendar.Name", "fdCalendarName");
			toFormPropertyMap.put("fdCalendar.fdId", "fdCalendarId");
			toFormPropertyMap.put("fdPerson.fdName", "fdPersonName");
			toFormPropertyMap.put("fdPerson.fdId", "fdPersonId");
			}
			return toFormPropertyMap;
		}
}
