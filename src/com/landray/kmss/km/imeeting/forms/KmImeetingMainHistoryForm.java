package com.landray.kmss.km.imeeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingMainHistory;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 会议时间轴 Form
 * 
 * @author
 * @version 1.0 2014-07-21
 */
public class KmImeetingMainHistoryForm extends ExtendForm {

	/**
	 * 操作类型
	 */
	protected String fdOptType = null;

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
	protected String fdOptContent = null;

	/**
	 * @return 操作内容
	 */
	public String getFdOptContent() {
		return fdOptContent;
	}

	/**
	 * @param fdOptContent
	 *            操作内容
	 */
	public void setFdOptContent(String fdOptContent) {
		this.fdOptContent = fdOptContent;
	}

	/**
	 * 操作日期
	 */
	protected String fdOptDate = null;

	/**
	 * @return 操作日期
	 */
	public String getFdOptDate() {
		return fdOptDate;
	}

	/**
	 * @param fdOptDate
	 *            操作日期
	 */
	public void setFdOptDate(String fdOptDate) {
		this.fdOptDate = fdOptDate;
	}

	/**
	 * 操作人员ID
	 */
	protected String fdOptPersonId = null;

	public String getFdOptPersonId() {
		return fdOptPersonId;
	}

	public void setFdOptPersonId(String fdOptPersonId) {
		this.fdOptPersonId = fdOptPersonId;
	}

	/**
	 * 操作人员Name
	 */
	protected String fdOptPersonName = null;

	public String getFdOptPersonName() {
		return fdOptPersonName;
	}

	public void setFdOptPersonName(String fdOptPersonName) {
		this.fdOptPersonName = fdOptPersonName;
	}

	/**
	 * 所属会议的ID
	 */
	protected String fdMeetingId = null;

	/**
	 * @return 所属会议的ID
	 */
	public String getFdMeetingId() {
		return fdMeetingId;
	}

	/**
	 * @param fdMeetingId
	 *            所属会议的ID
	 */
	public void setFdMeetingId(String fdMeetingId) {
		this.fdMeetingId = fdMeetingId;
	}

	/**
	 * 所属会议的名称
	 */
	protected String fdMeetingName = null;

	/**
	 * @return 所属会议的名称
	 */
	public String getFdMeetingName() {
		return fdMeetingName;
	}

	/**
	 * @param fdMeetingName
	 *            所属会议的名称
	 */
	public void setFdMeetingName(String fdMeetingName) {
		this.fdMeetingName = fdMeetingName;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdOptType = null;
		fdOptContent = null;
		fdOptDate = null;
		fdOptPersonId = null;
		fdOptPersonName = null;
		fdMeetingId = null;
		fdMeetingName = null;

		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmImeetingMainHistory.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdMeetingId", new FormConvertor_IDToModel(
					"fdMeeting", KmImeetingMain.class));
			toModelPropertyMap.put("fdOptPersonId",
					new FormConvertor_IDsToModelList("fdOptPerson",
							SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
}
