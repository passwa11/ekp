package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.staff.model.HrStaffTrackRecord;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 
 * 任职记录
 */
public class HrStaffTrackRecordForm extends HrStaffBaseForm {
	private static final long serialVersionUID = 1L;

	// 相关流程
	private String fdRelatedProcess;
	// 任职开始日期
	private String fdEntranceBeginDate;
	// 任职结束日期
	private String fdEntranceEndDate;
	// 人员
	private String fdOrgPersonName;
	private String fdOrgPersonId;
	// 部门
	private String fdRatifyDeptId;
	private String fdRatifyDeptName;

	// 岗位
	private String fdOrgPostsIds;
	private String fdOrgPostsNames;

	// 职务
	private String fdStaffingLevelId;
	private String fdStaffingLevelName;

	private String fdMemo;

	private String fdType;

	private String fdStatus;

	/**
	 * HR地址本
	 */
	private String fdHrOrgPostId;
	private String fdHrOrgPostName;
	private String fdHrOrgDeptId;
	private String fdHrOrgDeptName;

	private String fdStartDateOfInternship;

	private String fdEndDateOfInternship;

	private String fdIsInspection;

	private String fdAppointmentCategory;
	private String fdIsSecondEntry;
	private String fdInternshipEndDate;
	private String fdInternshipStartDate;
	private String fdContractChangeRecord;

	public String getFdInternshipStartDate() {
		return fdInternshipStartDate;
	}

	public void setFdInternshipStartDate(String fdInternshipStartDate) {
		this.fdInternshipStartDate = fdInternshipStartDate;
	}

	private String fdChangeType;

	public String getFdChangeType() {
		return fdChangeType;
	}

	public void setFdChangeType(String fdChangeType) {
		this.fdChangeType = fdChangeType;
	}

	public String getFdContractChangeRecord() {
		return fdContractChangeRecord;
	}

	public void setFdContractChangeRecord(String fdContractChangeRecord) {
		this.fdContractChangeRecord = fdContractChangeRecord;
	}

	public String getFdInternshipEndDate() {
		return fdInternshipEndDate;
	}

	public void setFdInternshipEndDate(String fdInternshipEndDate) {
		this.fdInternshipEndDate = fdInternshipEndDate;
	}

	public String getFdIsSecondEntry() {
		return fdIsSecondEntry;
	}

	public void setFdIsSecondEntry(String fdIsSecondEntry) {
		this.fdIsSecondEntry = fdIsSecondEntry;
	}

	public String getFdAppointmentCategory() {
		return fdAppointmentCategory;
	}

	public void setFdAppointmentCategory(String fdAppointmentCategory) {
		this.fdAppointmentCategory = fdAppointmentCategory;
	}
	public String getFdIsInspection() {
		return fdIsInspection;
	}

	public void setFdIsInspection(String fdIsInspection) {
		this.fdIsInspection = fdIsInspection;
	}
	public String getFdEndDateOfInternship() {
		return fdEndDateOfInternship;
	}

	public void setFdEndDateOfInternship(String fdEndDateOfInternship) {
		this.fdEndDateOfInternship = fdEndDateOfInternship;
	}

	public String getFdStartDateOfInternship() {
		return fdStartDateOfInternship;
	}

	public void setFdStartDateOfInternship(String fdStartDateOfInternship) {
		this.fdStartDateOfInternship = fdStartDateOfInternship;
	}

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public String getFdOrgPersonName() {
		return fdOrgPersonName;
	}

	public void setFdOrgPersonName(String fdOrgPersonName) {
		this.fdOrgPersonName = fdOrgPersonName;
	}

	public String getFdOrgPersonId() {
		return fdOrgPersonId;
	}

	public void setFdOrgPersonId(String fdOrgPersonId) {
		this.fdOrgPersonId = fdOrgPersonId;
	}

	public String getFdRatifyDeptId() {
		return fdRatifyDeptId;
	}

	public void setFdRatifyDeptId(String fdRatifyDeptId) {
		this.fdRatifyDeptId = fdRatifyDeptId;
	}

	public String getFdRatifyDeptName() {
		return fdRatifyDeptName;
	}

	public void setFdRatifyDeptName(String fdRatifyDeptName) {
		this.fdRatifyDeptName = fdRatifyDeptName;
	}


	public String getFdStaffingLevelId() {
		return fdStaffingLevelId;
	}

	public void setFdStaffingLevelId(String fdStaffingLevelId) {
		this.fdStaffingLevelId = fdStaffingLevelId;
	}

	public String getFdStaffingLevelName() {
		return fdStaffingLevelName;
	}

	public void setFdStaffingLevelName(String fdStaffingLevelName) {
		this.fdStaffingLevelName = fdStaffingLevelName;
	}

	public String getFdMemo() {
		return fdMemo;
	}

	public void setFdMemo(String fdMemo) {
		this.fdMemo = fdMemo;
	}

	public String getFdOrgPostsIds() {
		return fdOrgPostsIds;
	}

	public void setFdOrgPostsIds(String fdOrgPostsIds) {
		this.fdOrgPostsIds = fdOrgPostsIds;
	}

	public String getFdOrgPostsNames() {
		return fdOrgPostsNames;
	}

	public void setFdOrgPostsNames(String fdOrgPostsNames) {
		this.fdOrgPostsNames = fdOrgPostsNames;
	}

	@Override
	public Class getModelClass() {
		return HrStaffTrackRecord.class;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.fdRelatedProcess = null;
		this.fdEntranceBeginDate = null;
		this.fdStartDateOfInternship = null;
		this.fdEndDateOfInternship = null;
		this.fdEntranceEndDate = null;
		this.fdInternshipEndDate = null;
		this.fdInternshipStartDate = null;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdStaffingLevelId",
					new FormConvertor_IDToModel("fdStaffingLevel",
							SysOrganizationStaffingLevel.class));
			toModelPropertyMap.put("fdOrgPostsIds",
					new FormConvertor_IDsToModelList(
					"fdOrgPosts", SysOrgPost.class));
			toModelPropertyMap.put("fdRatifyDeptId",
					new FormConvertor_IDToModel("fdRatifyDept",
							SysOrgElement.class));
			toModelPropertyMap.put("fdOrgPersonId", new FormConvertor_IDToModel("fdOrgPerson", SysOrgElement.class));

			toModelPropertyMap.put("fdHrOrgPostId",
					new FormConvertor_IDToModel("fdHrOrgPost", HrOrganizationPost.class));
			toModelPropertyMap.put("fdHrOrgDeptId",
					new FormConvertor_IDToModel("fdHrOrgDept", HrOrganizationElement.class));
		}
		return toModelPropertyMap;
	}

	public String getFdRelatedProcess() {
		return fdRelatedProcess;
	}

	public void setFdRelatedProcess(String fdRelatedProcess) {
		this.fdRelatedProcess = fdRelatedProcess;
	}

	public String getFdEntranceBeginDate() {
		return fdEntranceBeginDate;
	}

	public void setFdEntranceBeginDate(String fdEntranceBeginDate) {
		this.fdEntranceBeginDate = fdEntranceBeginDate;
	}

	public String getFdEntranceEndDate() {
		return fdEntranceEndDate;
	}

	public void setFdEntranceEndDate(String fdEntranceEndDate) {
		this.fdEntranceEndDate = fdEntranceEndDate;
	}

	/**
	 * 异动后薪资
	 */
	private Double fdTransSalary;

	/**
	 * 薪资类型：1、月薪。2、年薪
	 */
	private String fdSalaryType;

	/**
	 * 异动类型
	 */
	private String fdTransType;

	/**
	 * 生效日期
	 */
	private String fdTransDate;

	public Double getFdTransSalary() {
		return fdTransSalary;
	}

	public String getFdTransType() {
		return fdTransType;
	}

	public String getFdTransDate() {
		return fdTransDate;
	}

	public void setFdTransSalary(Double fdTransSalary) {
		this.fdTransSalary = fdTransSalary;
	}

	public void setFdTransType(String fdTransType) {
		this.fdTransType = fdTransType;
	}

	public void setFdTransDate(String fdTransDate) {
		this.fdTransDate = fdTransDate;
	}

	public String getFdSalaryType() {
		return fdSalaryType;
	}

	public void setFdSalaryType(String fdSalaryType) {
		this.fdSalaryType = fdSalaryType;
	}
	
	/**
	 * 异动前薪资
	 */
	private Double fdBeforSalary;
	
	public Double getFdBeforSalary() {
		return fdBeforSalary;
	}

	public void setFdBeforSalary(Double fdBeforSalary) {
		this.fdBeforSalary = fdBeforSalary;
	}

	public String getFdHrOrgPostId() {
		return fdHrOrgPostId;
	}

	public String getFdHrOrgPostName() {
		return fdHrOrgPostName;
	}

	public String getFdHrOrgDeptId() {
		return fdHrOrgDeptId;
	}

	public String getFdHrOrgDeptName() {
		return fdHrOrgDeptName;
	}

	public void setFdHrOrgPostId(String fdHrOrgPostId) {
		this.fdHrOrgPostId = fdHrOrgPostId;
	}

	public void setFdHrOrgPostName(String fdHrOrgPostName) {
		this.fdHrOrgPostName = fdHrOrgPostName;
	}

	public void setFdHrOrgDeptId(String fdHrOrgDeptId) {
		this.fdHrOrgDeptId = fdHrOrgDeptId;
	}

	public void setFdHrOrgDeptName(String fdHrOrgDeptName) {
		this.fdHrOrgDeptName = fdHrOrgDeptName;
	}

	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

}
