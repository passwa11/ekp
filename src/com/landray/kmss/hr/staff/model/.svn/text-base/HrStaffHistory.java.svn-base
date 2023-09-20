package com.landray.kmss.hr.staff.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.staff.forms.HrStaffHistoryForm;
import com.landray.kmss.util.DateUtil;

public class HrStaffHistory extends BaseModel {
	private static ModelToFormPropertyMap toFormPropertyMap;

	private String fdName;

	private String fdPost;

	private Date fdStartDate;

	private Date fdEndDate;

	private String fdDesc;

	private String fdLeaveReason;

	private HrStaffEntry docMain;

	private Integer docIndex;

	@Override
	public Class<HrStaffHistoryForm> getFormClass() {
		return HrStaffHistoryForm.class;
	}

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdStartDate",
					new ModelConvertor_Common("fdStartDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdEndDate",
					new ModelConvertor_Common("fdEndDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("docMain.fdName", "docMainName");
			toFormPropertyMap.put("docMain.fdId", "docMainId");
		}
		return toFormPropertyMap;
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
	public Date getFdStartDate() {
		return this.fdStartDate;
	}

	/**
	 * 开始日期
	 */
	public void setFdStartDate(Date fdStartDate) {
		this.fdStartDate = fdStartDate;
	}

	/**
	 * 结束日期
	 */
	public Date getFdEndDate() {
		return this.fdEndDate;
	}

	/**
	 * 结束日期
	 */
	public void setFdEndDate(Date fdEndDate) {
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

	public HrStaffEntry getDocMain() {
		return docMain;
	}

	public void setDocMain(HrStaffEntry docMain) {
		this.docMain = docMain;
	}

	public Integer getDocIndex() {
		return docIndex;
	}

	public void setDocIndex(Integer docIndex) {
		this.docIndex = docIndex;
	}
}
