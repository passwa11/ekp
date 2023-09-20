package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.ratify.model.HrRatifyEntry;
import com.landray.kmss.hr.ratify.model.HrRatifyHistory;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 工作经历
 *
 */
public class HrRatifyHistoryForm extends ExtendForm {

	private static FormToModelPropertyMap toModelPropertyMap;

	private String fdName;

	private String fdPost;

	private String fdStartDate;

	private String fdEndDate;

	private String fdDesc;

	private String fdLeaveReason;

	private String docMainId;

	private String docMainName;

	private String docIndex;

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdPost = null;
		fdStartDate = null;
		fdEndDate = null;
		fdDesc = null;
		fdLeaveReason = null;
		docIndex = null;
		super.reset(mapping, request);
	}

	@Override
	public Class<HrRatifyHistory> getModelClass() {
		return HrRatifyHistory.class;
	}

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdStartDate",
					new FormConvertor_Common("fdStartDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("fdEndDate",
					new FormConvertor_Common("fdEndDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel(
					"docMain", HrRatifyEntry.class));
		}
		return toModelPropertyMap;
	}

	/**
	 * 公司名称
	 */
	public String getFdName() {
		return this.fdName;
	}

	/**
	 * 公司名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 职位
	 */
	public String getFdPost() {
		return this.fdPost;
	}

	/**
	 * 职位
	 */
	public void setFdPost(String fdPost) {
		this.fdPost = fdPost;
	}

	/**
	 * 开始日期
	 */
	public String getFdStartDate() {
		return this.fdStartDate;
	}

	/**
	 * 开始日期
	 */
	public void setFdStartDate(String fdStartDate) {
		this.fdStartDate = fdStartDate;
	}

	/**
	 * 结束日期
	 */
	public String getFdEndDate() {
		return this.fdEndDate;
	}

	/**
	 * 结束日期
	 */
	public void setFdEndDate(String fdEndDate) {
		this.fdEndDate = fdEndDate;
	}

	/**
	 * 工作描述
	 */
	public String getFdDesc() {
		return this.fdDesc;
	}

	/**
	 * 工作描述
	 */
	public void setFdDesc(String fdDesc) {
		this.fdDesc = fdDesc;
	}

	/**
	 * 离职原因
	 */
	public String getFdLeaveReason() {
		return this.fdLeaveReason;
	}

	/**
	 * 离职原因
	 */
	public void setFdLeaveReason(String fdLeaveReason) {
		this.fdLeaveReason = fdLeaveReason;
	}

	public String getDocMainId() {
		return this.docMainId;
	}

	public void setDocMainId(String docMainId) {
		this.docMainId = docMainId;
	}

	public String getDocMainName() {
		return this.docMainName;
	}

	public void setDocMainName(String docMainName) {
		this.docMainName = docMainName;
	}

	public String getDocIndex() {
		return this.docIndex;
	}

	public void setDocIndex(String docIndex) {
		this.docIndex = docIndex;
	}

}
