package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.staff.model.HrStaffEntry;
import com.landray.kmss.hr.staff.model.HrStaffRewPuni;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

public class HrStaffRewPuniForm extends ExtendForm {

	private static FormToModelPropertyMap toModelPropertyMap;

	private String fdName;

	private String fdDate;

	private String fdRemark;

	private String docMainId;

	private String docMainName;

	private String docIndex;

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdDate = null;
		fdRemark = null;
		docIndex = null;
		super.reset(mapping, request);
	}

	@Override
    public Class<HrStaffRewPuni> getModelClass() {
		return HrStaffRewPuni.class;
	}

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdDate", new FormConvertor_Common("fdDate")
					.setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel(
					"docMain", HrStaffEntry.class));
		}
		return toModelPropertyMap;
	}

	/**
	 * 奖惩名称
	 */
	public String getFdName() {
		return this.fdName;
	}

	/**
	 * 奖惩名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 奖惩日期
	 */
	public String getFdDate() {
		return this.fdDate;
	}

	/**
	 * 奖惩日期
	 */
	public void setFdDate(String fdDate) {
		this.fdDate = fdDate;
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
