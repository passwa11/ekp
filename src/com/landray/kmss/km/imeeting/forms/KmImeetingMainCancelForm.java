package com.landray.kmss.km.imeeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 会议取消 Form
 */
public class KmImeetingMainCancelForm extends ExtendForm {

	/**
	 * 提醒方式
	 */
	private String fdNotifyType = null;

	/**
	 * 催办通知人
	 */
	private String cancelNotifyPersonIds = null;

	private String cancelNotifyPersonNames = null;

	/**
	 * 取消原因
	 */
	private String cancelReason = null;

	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}

	public String getCancelNotifyPersonIds() {
		return cancelNotifyPersonIds;
	}

	public void setCancelNotifyPersonIds(String cancelNotifyPersonIds) {
		this.cancelNotifyPersonIds = cancelNotifyPersonIds;
	}

	public String getCancelNotifyPersonNames() {
		return cancelNotifyPersonNames;
	}

	public void setCancelNotifyPersonNames(String cancelNotifyPersonNames) {
		this.cancelNotifyPersonNames = cancelNotifyPersonNames;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	private String fdCancelType;

	public String getFdCancelType() {
		return fdCancelType;
	}

	public void setFdCancelType(String fdCancelType) {
		this.fdCancelType = fdCancelType;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdNotifyType = null;
		cancelNotifyPersonIds = null;
		cancelNotifyPersonNames = null;
		cancelReason = null;
		fdCancelType = null;
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
