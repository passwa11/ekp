package com.landray.kmss.km.imeeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;

/**
 * 会议催办 Form
 */
public class KmImeetingMainHastenForm extends ExtendForm {

	/**
	 * 提醒方式
	 */
	private String fdNotifyType = null;

	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}

	/**
	 * 催办通知人
	 */
	private String hastenNotifyPersonIds = null;

	private String hastenNotifyPersonNames = null;

	public String getHastenNotifyPersonIds() {
		return hastenNotifyPersonIds;
	}

	public void setHastenNotifyPersonIds(String hastenNotifyPersonIds) {
		this.hastenNotifyPersonIds = hastenNotifyPersonIds;
	}

	public String getHastenNotifyPersonNames() {
		return hastenNotifyPersonNames;
	}

	public void setHastenNotifyPersonNames(String hastenNotifyPersonNames) {
		this.hastenNotifyPersonNames = hastenNotifyPersonNames;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdNotifyType = null;
		hastenNotifyPersonIds = null;
		hastenNotifyPersonNames = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return null;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
		}
		return toModelPropertyMap;
	}
}
