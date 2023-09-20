package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.ratify.model.HrRatifyEntry;
import com.landray.kmss.hr.ratify.model.HrRatifyTrain;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 培训记录
 */
public class HrRatifyTrainForm extends ExtendForm {

	private static FormToModelPropertyMap toModelPropertyMap;

	private String fdName;

	private String fdStartDate;

	private String fdEndDate;

	private String fdTrainCompany;

	private String fdRemark;

	private String docMainId;

	private String docMainName;

	private String docIndex;

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdStartDate = null;
		fdEndDate = null;
		fdTrainCompany = null;
		fdRemark = null;
		docIndex = null;
		super.reset(mapping, request);
	}

	@Override
    public Class<HrRatifyTrain> getModelClass() {
		return HrRatifyTrain.class;
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
	 * 名称
	 */
	public String getFdName() {
		return this.fdName;
	}

	/**
	 * 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 开始时间
	 */
	public String getFdStartDate() {
		return this.fdStartDate;
	}

	/**
	 * 开始时间
	 */
	public void setFdStartDate(String fdStartDate) {
		this.fdStartDate = fdStartDate;
	}

	/**
	 * 结束时间
	 */
	public String getFdEndDate() {
		return this.fdEndDate;
	}

	/**
	 * 结束时间
	 */
	public void setFdEndDate(String fdEndDate) {
		this.fdEndDate = fdEndDate;
	}

	/**
	 * 培训单位
	 */
	public String getFdTrainCompany() {
		return this.fdTrainCompany;
	}

	/**
	 * 培训单位
	 */
	public void setFdTrainCompany(String fdTrainCompany) {
		this.fdTrainCompany = fdTrainCompany;
	}

	/**
	 * 备注
	 */
	public String getFdRemark() {
		return this.fdRemark;
	}

	/**
	 * 备注
	 */
	public void setFdRemark(String fdRemark) {
		this.fdRemark = fdRemark;
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
