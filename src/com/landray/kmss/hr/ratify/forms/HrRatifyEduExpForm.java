package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.ratify.model.HrRatifyEduExp;
import com.landray.kmss.hr.ratify.model.HrRatifyEntry;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoSettingNew;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 教育记录
 */
public class HrRatifyEduExpForm extends ExtendForm {

	private static FormToModelPropertyMap toModelPropertyMap;

	private String fdName;

	private String fdMajor;

	private String fdEntranceDate;

	private String fdGraduationDate;

	private String fdRemark;

	private String docMainId;

	private String docMainName;

	private String docIndex;

	private String fdAcadeRecordName;

	private String fdAcadeRecordId;

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdMajor = null;
		fdEntranceDate = null;
		fdGraduationDate = null;
		fdRemark = null;
		docIndex = null;
		fdAcadeRecordName = null;
		fdAcadeRecordId = null;
		super.reset(mapping, request);
	}

	@Override
	public Class<HrRatifyEduExp> getModelClass() {
		return HrRatifyEduExp.class;
	}

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdEntranceDate",
					new FormConvertor_Common("fdEntranceDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("fdGraduationDate",
					new FormConvertor_Common("fdGraduationDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel(
					"docMain", HrRatifyEntry.class));
			toModelPropertyMap.put("fdAcadeRecordId",
					new FormConvertor_IDToModel("fdAcadeRecord",
							HrStaffPersonInfoSettingNew.class));
		}
		return toModelPropertyMap;
	}

	/**
	 * 学位
	 */
	public String getFdAcadeRecordName() {
		return fdAcadeRecordName;
	}

	/**
	 * 学位
	 */
	public void setFdAcadeRecordName(String fdAcadeRecordName) {
		this.fdAcadeRecordName = fdAcadeRecordName;
	}

	/**
	 * 学位
	 */
	public String getFdAcadeRecordId() {
		return fdAcadeRecordId;
	}

	/**
	 * 学位
	 */
	public void setFdAcadeRecordId(String fdAcadeRecordId) {
		this.fdAcadeRecordId = fdAcadeRecordId;
	}

	/**
	 * 学校名称
	 */
	public String getFdName() {
		return this.fdName;
	}

	/**
	 * 学校名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 专业名称
	 */
	public String getFdMajor() {
		return this.fdMajor;
	}

	/**
	 * 专业名称
	 */
	public void setFdMajor(String fdMajor) {
		this.fdMajor = fdMajor;
	}

	/**
	 * 入学日期
	 */
	public String getFdEntranceDate() {
		return this.fdEntranceDate;
	}

	/**
	 * 入学日期
	 */
	public void setFdEntranceDate(String fdEntranceDate) {
		this.fdEntranceDate = fdEntranceDate;
	}

	/**
	 * 毕业日期
	 */
	public String getFdGraduationDate() {
		return this.fdGraduationDate;
	}

	/**
	 * 毕业日期
	 */
	public void setFdGraduationDate(String fdGraduationDate) {
		this.fdGraduationDate = fdGraduationDate;
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
