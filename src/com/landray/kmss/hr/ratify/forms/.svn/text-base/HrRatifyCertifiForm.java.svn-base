package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.ratify.model.HrRatifyCertifi;
import com.landray.kmss.hr.ratify.model.HrRatifyEntry;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 资格证书
 */
public class HrRatifyCertifiForm extends ExtendForm {

	private static FormToModelPropertyMap toModelPropertyMap;

	private String fdName;

	private String fdIssuingUnit;

	private String fdIssueDate;

	private String fdInvalidDate;

	private String fdRemark;

	private String docMainId;

	private String docMainName;

	private String docIndex;

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdIssuingUnit = null;
		fdIssueDate = null;
		fdInvalidDate = null;
		fdRemark = null;
		docIndex = null;
		super.reset(mapping, request);
	}

	@Override
    public Class<HrRatifyCertifi> getModelClass() {
		return HrRatifyCertifi.class;
	}

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdIssueDate",
					new FormConvertor_Common("fdIssueDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("fdInvalidDate",
					new FormConvertor_Common("fdInvalidDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel(
					"docMain", HrRatifyEntry.class));
		}
		return toModelPropertyMap;
	}

	/**
	 * 证书名称
	 */
	public String getFdName() {
		return this.fdName;
	}

	/**
	 * 证书名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 颁发单位
	 */
	public String getFdIssuingUnit() {
		return this.fdIssuingUnit;
	}

	/**
	 * 颁发单位
	 */
	public void setFdIssuingUnit(String fdIssuingUnit) {
		this.fdIssuingUnit = fdIssuingUnit;
	}

	/**
	 * 颁发日期
	 */
	public String getFdIssueDate() {
		return this.fdIssueDate;
	}

	/**
	 * 颁发日期
	 */
	public void setFdIssueDate(String fdIssueDate) {
		this.fdIssueDate = fdIssueDate;
	}

	/**
	 * 失效日期
	 */
	public String getFdInvalidDate() {
		return this.fdInvalidDate;
	}

	/**
	 * 失效日期
	 */
	public void setFdInvalidDate(String fdInvalidDate) {
		this.fdInvalidDate = fdInvalidDate;
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
