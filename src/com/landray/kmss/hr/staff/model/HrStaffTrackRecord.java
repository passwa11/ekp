package com.landray.kmss.hr.staff.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.staff.forms.HrStaffTrackRecordForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzModel;
import com.landray.kmss.util.DateUtil;

/**
 * 任职记录
 */
public class HrStaffTrackRecord extends HrStaffBaseModel implements ISysQuartzModel {
	private static final long serialVersionUID = 1L;

	private static ModelToFormPropertyMap toFormPropertyMap;
	// 相关流程
	private String fdRelatedProcess;
	// 人员
	private SysOrgElement fdOrgPerson;
	// 部门
	private SysOrgElement fdRatifyDept;
	// 岗位
	private List<SysOrgPost> fdOrgPosts;
	// 职务
	private SysOrganizationStaffingLevel fdStaffingLevel;

	//人事组织地址本岗位
	private HrOrganizationPost fdHrOrgPost;

	//人事组织地址本部门
	private HrOrganizationElement fdHrOrgDept;

	// 任职开始日期
	private Date fdEntranceBeginDate;
	private Date fdInternshipStartDate;

	public Date getFdInternshipStartDate() {
		return fdInternshipStartDate;
	}

	public void setFdInternshipStartDate(Date fdInternshipStartDate) {
		this.fdInternshipStartDate = fdInternshipStartDate;
	}

	// 任职结束日期
	private Date fdEntranceEndDate;

	// 备注
	private String fdMemo;

	//调薪来源
	private String fdSource;

	/**
	 * 任职类型  1、主岗 (主岗需唯一)  2、兼岗
	 */
	private String fdType;
	private String fdIsInspection;
	private String fdAppointmentCategory;
	private String fdIsSecondEntry;
	private Date fdInternshipEndDate;
	private String fdContractChangeRecord;

	public String getFdContractChangeRecord() {
		return fdContractChangeRecord;
	}

	public void setFdContractChangeRecord(String fdContractChangeRecord) {
		this.fdContractChangeRecord = fdContractChangeRecord;
	}

	private String fdChangeType;

	public String getFdChangeType() {
		return fdChangeType;
	}

	public void setFdChangeType(String fdChangeType) {
		this.fdChangeType = fdChangeType;
	}

	public Date getFdInternshipEndDate() {
		return fdInternshipEndDate;
	}

	public void setFdInternshipEndDate(Date fdInternshipEndDate) {
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

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	/**
	 * 任职状态 1、任职中  2、已结束  3、待任职
	 */
	private String fdStatus;

	/*
	 * 排序号
	 */
	private Integer fdOrder;
	private Date fdEndDateOfInternship;

	public Date getFdEndDateOfInternship() {
		return fdEndDateOfInternship;
	}

	public void setFdEndDateOfInternship(Date fdEndDateOfInternship) {
		this.fdEndDateOfInternship = fdEndDateOfInternship;
	}

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 异动后薪资
	 */
	private Double fdTransSalary;

	/**
	 * 异动类型
	 */
	private String fdTransType;

	/**
	 * 生效日期
	 */
	private Date fdTransDate;
	private Date fdStartDateOfInternship;

	public Date getFdStartDateOfInternship() {
		return fdStartDateOfInternship;
	}

	public void setFdStartDateOfInternship(Date fdStartDateOfInternship) {
		this.fdStartDateOfInternship = fdStartDateOfInternship;
	}

	public String getFdRelatedProcess() {
		return fdRelatedProcess;
	}

	public void setFdRelatedProcess(String fdRelatedProcess) {
		this.fdRelatedProcess = fdRelatedProcess;
	}

	public SysOrgElement getFdOrgPerson() {
		return fdOrgPerson;
	}

	public void setFdOrgPerson(SysOrgElement fdOrgPerson) {
		this.fdOrgPerson = fdOrgPerson;
	}

	public SysOrgElement getFdRatifyDept() {
		return fdRatifyDept;
	}

	public void setFdRatifyDept(SysOrgElement fdRatifyDept) {
		this.fdRatifyDept = fdRatifyDept;
	}

	public Date getFdEntranceBeginDate() {
		return fdEntranceBeginDate;
	}

	public void setFdEntranceBeginDate(Date fdEntranceBeginDate) {
		this.fdEntranceBeginDate = fdEntranceBeginDate;
	}

	public Date getFdEntranceEndDate() {
		return fdEntranceEndDate;
	}

	public void setFdEntranceEndDate(Date fdEntranceEndDate) {
		this.fdEntranceEndDate = fdEntranceEndDate;
	}

	@Override
	public Class getFormClass() {
		return HrStaffTrackRecordForm.class;
	}

	public List<SysOrgPost> getFdOrgPosts() {
		return fdOrgPosts;
	}

	public void setFdOrgPosts(List<SysOrgPost> fdOrgPosts) {
		this.fdOrgPosts = fdOrgPosts;
	}

	public SysOrganizationStaffingLevel getFdStaffingLevel() {
		return fdStaffingLevel;
	}

	public void
			setFdStaffingLevel(SysOrganizationStaffingLevel fdStaffingLevel) {
		this.fdStaffingLevel = fdStaffingLevel;
	}

	public String getFdMemo() {
		return fdMemo;
	}

	public void setFdMemo(String fdMemo) {
		this.fdMemo = fdMemo;
	}

	public Double getFdTransSalary() {
		return fdTransSalary;
	}

	public String getFdTransType() {
		return fdTransType;
	}

	public Date getFdTransDate() {
		return fdTransDate;
	}

	public void setFdTransSalary(Double fdTransSalary) {
		this.fdTransSalary = fdTransSalary;
	}

	public void setFdTransType(String fdTransType) {
		this.fdTransType = fdTransType;
	}

	public void setFdTransDate(Date fdTransDate) {
		this.fdTransDate = fdTransDate;
	}

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdOrgPerson.fdId", "fdOrgPersonId");
			toFormPropertyMap.put("fdOrgPerson.fdName",
					"fdOrgPersonName");
			toFormPropertyMap.put("fdRatifyDept.fdId", "fdRatifyDeptId");
			toFormPropertyMap.put("fdRatifyDept.fdName", "fdRatifyDeptName");
			toFormPropertyMap.put("fdStaffingLevel.fdId", "fdStaffingLevelId");
			toFormPropertyMap.put("fdStaffingLevel.fdName",
					"fdStaffingLevelName");
			toFormPropertyMap.put("fdOrgPosts",
					new ModelConvertor_ModelListToString(
							"fdOrgPostsIds:fdOrgPostsNames", "fdId:fdName"));
			toFormPropertyMap.put("fdEntranceBeginDate",
					new ModelConvertor_Common("fdEntranceBeginDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdInternshipStartDate",
					new ModelConvertor_Common("fdInternshipStartDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdStartDateOfInternship",
					new ModelConvertor_Common("fdStartDateOfInternship")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdEndDateOfInternship",
					new ModelConvertor_Common("fdEndDateOfInternship")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdEntranceEndDate",
					new ModelConvertor_Common("fdEntranceEndDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdInternshipEndDate",
					new ModelConvertor_Common("fdInternshipEndDate")
							.setDateTimeType(DateUtil.TYPE_DATE));

			toFormPropertyMap.put("fdHrOrgPost.fdId", "fdHrOrgPostId");
			toFormPropertyMap.put("fdHrOrgPost.fdName", "fdHrOrgPostName");
			toFormPropertyMap.put("fdHrOrgDept.fdId", "fdHrOrgDeptId");
			toFormPropertyMap.put("fdHrOrgDept.fdName", "fdHrOrgDeptName");
		}
		return toFormPropertyMap;
	}

	public String getFdSource() {
		return fdSource;
	}

	public void setFdSource(String fdSource) {
		this.fdSource = fdSource;
	}

	public HrOrganizationPost getFdHrOrgPost() {
		return fdHrOrgPost;
	}

	public HrOrganizationElement getFdHrOrgDept() {
		return fdHrOrgDept;
	}

	public void setFdHrOrgPost(HrOrganizationPost fdHrOrgPost) {
		this.fdHrOrgPost = fdHrOrgPost;
	}

	public void setFdHrOrgDept(HrOrganizationElement fdHrOrgDept) {
		this.fdHrOrgDept = fdHrOrgDept;
	}

	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

}
