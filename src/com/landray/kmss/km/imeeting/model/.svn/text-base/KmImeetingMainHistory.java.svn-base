package com.landray.kmss.km.imeeting.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.imeeting.forms.KmImeetingMainHistoryForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 会议时间轴
 */
public class KmImeetingMainHistory extends BaseModel {

	/**
	 * 操作类型
	 */
	protected String fdOptType;

	/**
	 * @return 操作类型
	 */
	public String getFdOptType() {
		return fdOptType;
	}

	/**
	 * @param fdOptType
	 *            操作类型
	 */
	public void setFdOptType(String fdOptType) {
		this.fdOptType = fdOptType;
	}

	/**
	 * 操作内容
	 */
	protected String fdOptContent;

	/**
	 * @return 操作内容
	 */
	public String getFdOptContent() {
		return (String) readLazyField("fdOptContent", fdOptContent);
	}

	/**
	 * @param fdOptContent
	 *            操作内容
	 */
	public void setFdOptContent(String fdOptContent) {
		this.fdOptContent = (String) writeLazyField("fdOptContent",
				this.fdOptContent, fdOptContent);
	}

	/**
	 * 操作人员
	 */
	protected SysOrgElement fdOptPerson;

	public SysOrgElement getFdOptPerson() {
		return fdOptPerson;
	}

	public void setFdOptPerson(SysOrgElement fdOptPerson) {
		this.fdOptPerson = fdOptPerson;
	}

	/**
	 * 操作日期
	 */
	protected Date fdOptDate;

	/**
	 * @return 操作日期
	 */
	public Date getFdOptDate() {
		return fdOptDate;
	}

	/**
	 * @param fdOptDate
	 *            操作日期
	 */
	public void setFdOptDate(Date fdOptDate) {
		this.fdOptDate = fdOptDate;
	}

	/**
	 * 所属会议
	 */
	protected KmImeetingMain fdMeeting;

	/**
	 * @return 所属会议
	 */
	public KmImeetingMain getFdMeeting() {
		return fdMeeting;
	}

	/**
	 * @param fdMeeting
	 *            所属会议
	 */
	public void setFdMeeting(KmImeetingMain fdMeeting) {
		this.fdMeeting = fdMeeting;
	}

	@Override
    public Class getFormClass() {
		return KmImeetingMainHistoryForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdMeeting.fdId", "fdMeetingId");
			toFormPropertyMap.put("fdMeeting.fdId", "fdMeetingName");
		}
		return toFormPropertyMap;
	}
}
