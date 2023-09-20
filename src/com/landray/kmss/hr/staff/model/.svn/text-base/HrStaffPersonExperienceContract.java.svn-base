package com.landray.kmss.hr.staff.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.staff.forms.HrStaffPersonExperienceContractForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

/**
 * 合同信息
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceContract extends
		HrStaffPersonExperienceBase implements IAttachment {
	private static final long serialVersionUID = 1L;

	// 合同名称
	private String fdName;

	@Override
	public Class getFormClass() {
		return HrStaffPersonExperienceContractForm.class;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	//长期合同
	private Boolean fdIsLongtermContract;

	// 合同类型
	private String fdContType;

	private HrStaffContractType fdStaffContType;

	// 签订标识
	private String fdSignType;

	// 合同办理时间
	private Date fdHandleDate;

	// 合同状态
	private String fdContStatus;


	// 合同期限
	private Integer fdContractYear;

	public Integer getFdContractYear() {
		return fdContractYear;
	}

	public void setFdContractYear(Integer fdContractYear) {
		this.fdContractYear = fdContractYear;
	}

	public Integer getFdContractMonth() {
		return fdContractMonth;
	}

	public void setFdContractMonth(Integer fdContractMonth) {
		this.fdContractMonth = fdContractMonth;
	}

	private Integer fdContractMonth;
	//合同所属单位
	private String fdContractUnit;

	// 合同附件
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	// 合同解除时间
	private Date fdCancelDate;

	// 解除原因
	private String fdCancelReason;

	// 签订流程
	private String fdCreateProcess;

	// 解除流程
	private String fdCancelProcess;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	@Override
	public String getFdContractUnit() {
		return fdContractUnit;
	}

	@Override
	public void setFdContractUnit(String fdContractUnit) {
		this.fdContractUnit = fdContractUnit;
	}

	public Boolean getFdIsLongtermContract() {
		return fdIsLongtermContract;
	}

	public void setFdIsLongtermContract(Boolean fdIsLongtermContract) {
		this.fdIsLongtermContract = fdIsLongtermContract;
	}

	public String getFdContType() {
		return fdContType;
	}

	public void setFdContType(String fdContType) {
		this.fdContType = fdContType;
	}

	public HrStaffContractType getFdStaffContType() {
		return fdStaffContType;
	}

	public void setFdStaffContType(HrStaffContractType fdStaffContType) {
		this.fdStaffContType = fdStaffContType;
	}

	public String getFdSignType() {
		return fdSignType;
	}

	public void setFdSignType(String fdSignType) {
		this.fdSignType = fdSignType;
	}

	public Date getFdHandleDate() {
		return fdHandleDate;
	}

	public void setFdHandleDate(Date fdHandleDate) {
		this.fdHandleDate = fdHandleDate;
	}

	public String getFdContStatus() {
		return fdContStatus;
	}

	public void setFdContStatus(String fdContStatus) {
		this.fdContStatus = fdContStatus;
	}

	@Override
	public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

	public Date getFdCancelDate() {
		return fdCancelDate;
	}

	public void setFdCancelDate(Date fdCancelDate) {
		this.fdCancelDate = fdCancelDate;
	}

	public String getFdCancelReason() {
		return fdCancelReason;
	}

	public void setFdCancelReason(String fdCancelReason) {
		this.fdCancelReason = fdCancelReason;
	}

	public String getFdCreateProcess() {
		return fdCreateProcess;
	}

	public void setFdCreateProcess(String fdCreateProcess) {
		this.fdCreateProcess = fdCreateProcess;
	}

	public String getFdCancelProcess() {
		return fdCancelProcess;
	}

	public void setFdCancelProcess(String fdCancelProcess) {
		this.fdCancelProcess = fdCancelProcess;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			// 合同类型
			toFormPropertyMap.put("fdStaffContType.fdId", "fdStaffContTypeId");
			toFormPropertyMap.put("fdStaffContType.fdName",
					"fdStaffContTypeName");
			// 合同期限
			toFormPropertyMap.put("fdContractPeriod",
					new ModelConvertor_Common("fdContractPeriod")
							.setDateTimeType(DateUtil.TYPE_DATE));
			// 合同办理时间
			toFormPropertyMap.put("fdHandleDate",
					new ModelConvertor_Common("fdHandleDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			// 合同解除时间
			toFormPropertyMap.put("fdCancelDate",
					new ModelConvertor_Common("fdCancelDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			// 取员工信息中的字段
			toFormPropertyMap.put("fdPersonInfo.fdSex", "fdSex");
			toFormPropertyMap.put("fdPersonInfo.fdDateOfBirth",
					new ModelConvertor_Common("fdDateOfBirth")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdPersonInfo.fdStaffNo", "fdStaffNo");
			toFormPropertyMap.put("fdPersonInfo.fdIdCard", "fdIdCard");
			toFormPropertyMap.put("fdPersonInfo.fdWorkTime",
					new ModelConvertor_Common("fdWorkTime")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdPersonInfo.fdTimeOfEnterprise",
					new ModelConvertor_Common("fdTimeOfEnterprise")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdPersonInfo.fdTrialExpirationTime",
					new ModelConvertor_Common("fdTrialExpirationTime")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdPersonInfo.fdTrialOperationPeriod",
					"fdTrialOperationPeriod");
			toFormPropertyMap.put("fdPersonInfo.fdEntryTime",
					new ModelConvertor_Common("fdEntryTime")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdPersonInfo.fdPositiveTime",
					new ModelConvertor_Common("fdPositiveTime")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdPersonInfo.fdActualPositiveTime",
					new ModelConvertor_Common("fdActualPositiveTime")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdPersonInfo.fdPositiveRemark",
					"fdPositiveRemark");
			toFormPropertyMap.put("fdPersonInfo.fdLeaveTime",
					new ModelConvertor_Common("fdLeaveTime")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdPersonInfo.fdIsRehire", "fdIsRehire");
			toFormPropertyMap.put("fdPersonInfo.fdRehireTime",
					new ModelConvertor_Common("fdRehireTime")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdPersonInfo.fdEmploymentPeriod",
					"fdEmploymentPeriod");
			toFormPropertyMap.put("fdPersonInfo.fdStaffType", "fdStaffType");
			toFormPropertyMap.put("fdPersonInfo.fdNameUsedBefore",
					"fdNameUsedBefore");
			toFormPropertyMap.put("fdPersonInfo.fdNation", "fdNation");
			toFormPropertyMap.put("fdPersonInfo.fdPoliticalLandscape",
					"fdPoliticalLandscape");
			toFormPropertyMap.put("fdPersonInfo.fdDateOfGroup",
					new ModelConvertor_Common("fdDateOfGroup")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdPersonInfo.fdDateOfParty",
					new ModelConvertor_Common("fdDateOfParty")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdPersonInfo.fdHighestEducation",
					"fdHighestEducation");
			toFormPropertyMap.put("fdPersonInfo.fdHighestDegree",
					"fdHighestDegree");
			toFormPropertyMap.put("fdPersonInfo.fdMaritalStatus",
					"fdMaritalStatus");
			toFormPropertyMap.put("fdPersonInfo.fdHealth", "fdHealth");
			toFormPropertyMap.put("fdPersonInfo.fdStature", "fdStature");
			toFormPropertyMap.put("fdPersonInfo.fdWeight", "fdWeight");
			toFormPropertyMap.put("fdPersonInfo.fdLivingPlace",
					"fdLivingPlace");
			toFormPropertyMap.put("fdPersonInfo.fdNativePlace",
					"fdNativePlace");
			toFormPropertyMap.put("fdPersonInfo.fdHomeplace", "fdHomeplace");
			toFormPropertyMap.put("fdPersonInfo.fdAccountProperties",
					"fdAccountProperties");
			toFormPropertyMap.put("fdPersonInfo.fdRegisteredResidence",
					"fdRegisteredResidence");
			toFormPropertyMap.put("fdPersonInfo.fdResidencePoliceStation",
					"fdResidencePoliceStation");
//			toFormPropertyMap.put("fdPersonInfo.fdStatus", "fdStatus");
			toFormPropertyMap.put("fdPersonInfo.fdMobileNo", "fdMobileNo");
			toFormPropertyMap.put("fdPersonInfo.fdEmail", "fdEmail");
			toFormPropertyMap.put("fdPersonInfo.fdOfficeLocation",
					"fdOfficeLocation");
			toFormPropertyMap.put("fdPersonInfo.fdWorkPhone", "fdWorkPhone");
			toFormPropertyMap.put("fdPersonInfo.fdEmergencyContact",
					"fdEmergencyContact");
			toFormPropertyMap.put("fdPersonInfo.fdEmergencyContactPhone",
					"fdEmergencyContactPhone");
			toFormPropertyMap.put("fdPersonInfo.fdOtherContact",
					"fdOtherContact");
			toFormPropertyMap.put("fdPersonInfo.fdOrgPerson.fdId",
					"fdOrgPersonId");
			toFormPropertyMap.put("fdPersonInfo.fdOrgPerson.fdName",
					"fdOrgPersonName");
			toFormPropertyMap.put("fdPersonInfo.fdOrgParentOrg.fdId",
					"fdOrgParentOrgId");
			toFormPropertyMap.put("fdPersonInfo.fdOrgParentOrg.fdName",
					"fdOrgParentOrgName");
			toFormPropertyMap.put("fdPersonInfo.fdOrgParent.fdId",
					"fdOrgParentId");
			toFormPropertyMap.put("fdPersonInfo.fdOrgParent.fdName",
					"fdOrgParentName");
			toFormPropertyMap.put("fdPersonInfo.fdStaffingLevel.fdId",
					"fdStaffingLevelId");
			toFormPropertyMap.put("fdPersonInfo.fdStaffingLevel.fdName",
					"fdStaffingLevelName");
			toFormPropertyMap.put("fdPersonInfo.fdOrgPosts",
					new ModelConvertor_ModelListToString(
							"fdOrgPostIds:fdOrgPostNames", "fdId:fdName"));
			toFormPropertyMap.put("fdPersonInfo.fdHierarchyId",
					"fdHierarchyId");
			toFormPropertyMap.put("fdPersonInfo.fdBirthdayOfYear",
					"fdBirthdayOfYear");
			toFormPropertyMap.put("fdPersonInfo.fdStaffEntry.fdId",
					"fdStaffEntryId");
			toFormPropertyMap.put("fdPersonInfo.fdStaffEntry.fdName",
					"fdStaffEntryName");
			toFormPropertyMap.put("fdPersonInfo.fdLeaveApplyDate",
					new ModelConvertor_Common("fdLeaveApplyDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdPersonInfo.fdLeavePlanDate",
					new ModelConvertor_Common("fdLeavePlanDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdPersonInfo.fdLeaveSalaryEndDate",
					new ModelConvertor_Common("fdLeaveSalaryEndDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdPersonInfo.fdLeaveReason",
					"fdLeaveReason");
			toFormPropertyMap.put("fdPersonInfo.fdLeaveRemark",
					"fdLeaveRemark");
			toFormPropertyMap.put("fdPersonInfo.fdNextCompany",
					"fdNextCompany");
			toFormPropertyMap.put("fdPersonInfo.fdNatureWork", "fdNatureWork");
			toFormPropertyMap.put("fdPersonInfo.fdLeaveStatus",
					"fdLeaveStatus");
			toFormPropertyMap.put("fdPersonInfo.fdReportLeader.fdId",
					"fdReportLeaderId");
			toFormPropertyMap.put("fdPersonInfo.fdReportLeader.fdName",
					"fdReportLeaderName");
			toFormPropertyMap.put("fdPersonInfo.fdWorkAddress",
					"fdWorkAddress");
			toFormPropertyMap.put("fdPersonInfo.fdLoginName", "fdLoginName");
			toFormPropertyMap.put("fdCreator.fdName", "fdCreator");
			toFormPropertyMap.put("fdPersonInfo.fdName", "fdPersonInfo");
			toFormPropertyMap.put("fdName", "docSubject");
		}
		return toFormPropertyMap;
	}

}
