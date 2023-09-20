package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.staff.model.HrStaffContractType;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 合同信息
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceContractForm extends
		HrStaffPersonExperienceBaseForm
		implements IAttachmentForm {
	private static final long serialVersionUID = 1L;

	// 合同名称
	private String fdName;

	// 合同期限
	private Integer fdContractYear;
	private Integer fdContractMonth;

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

	private String fdContractPeriod;
	//	//合同所属单位
	private String fdContractUnit;

	@Override
	public Class getModelClass() {
		return HrStaffPersonExperienceContract.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdStaffContTypeId",
					new FormConvertor_IDToModel("fdStaffContType",
							HrStaffContractType.class));
		}
		return toModelPropertyMap;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.fdName = null;
		this.fdIsLongtermContract= null;
		fdContractPeriod = null;
		fdContractUnit = null;
		this.fdContType = null;
		this.fdSignType = null;
		this.fdHandleDate = null;
		this.fdContStatus = "1";
		autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);
		this.fdCancelDate = null;
		this.fdCancelReason = null;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdContractPeriod() {
		return fdContractPeriod;
	}

	public void setFdContractPeriod(String fdContractPeriod) {
		this.fdContractPeriod = fdContractPeriod;
	}

	public String getFdContractUnit() {
		return fdContractUnit;
	}

	public void setFdContractUnit(String fdContractUnit) {
		this.fdContractUnit = fdContractUnit;
	}

	//长期合同
	private String fdIsLongtermContract;

	// 合同类型
	private String fdContType;

	private String fdStaffContTypeId;
	private String fdStaffContTypeName;

	// 签订标识
	private String fdSignType;

	// 合同办理时间
	private String fdHandleDate;

	// 合同状态
	private String fdContStatus;

	// 合同附件
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	// 合同解除时间
	private String fdCancelDate;

	// 解除原因
	private String fdCancelReason;

	// 签订流程
	private String fdCreateProcess;

	// 解除流程
	private String fdCancelProcess;

	public String getFdIsLongtermContract() {
		if("true".equals(this.fdIsLongtermContract)){
			return fdIsLongtermContract;
		}
		return null;
	}

	public void setFdIsLongtermContract(String fdIsLongtermContract) {
		this.fdIsLongtermContract = fdIsLongtermContract;
	}

	public String getFdContType() {
		return fdContType;
	}

	public void setFdContType(String fdContType) {
		this.fdContType = fdContType;
	}

	public String getFdStaffContTypeId() {
		return fdStaffContTypeId;
	}

	public void setFdStaffContTypeId(String fdStaffContTypeId) {
		this.fdStaffContTypeId = fdStaffContTypeId;
	}

	public String getFdStaffContTypeName() {
		return fdStaffContTypeName;
	}

	public void setFdStaffContTypeName(String fdStaffContTypeName) {
		this.fdStaffContTypeName = fdStaffContTypeName;
	}

	public String getFdSignType() {
		return fdSignType;
	}

	public void setFdSignType(String fdSignType) {
		this.fdSignType = fdSignType;
	}

	public String getFdHandleDate() {
		return fdHandleDate;
	}

	public void setFdHandleDate(String fdHandleDate) {
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

	public String getFdCancelDate() {
		return fdCancelDate;
	}

	public void setFdCancelDate(String fdCancelDate) {
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

	private String fdSex;
	private String fdDateOfBirth;
	private String fdStaffNo;
	private String fdIdCard;
	private String fdWorkTime;
	private String fdTimeOfEnterprise;
	private String fdTrialExpirationTime;
	private String fdTrialOperationPeriod;
	private String fdEntryTime;
	private String fdPositiveTime;
	private String fdActualPositiveTime;
	private String fdPositiveRemark;
	private String fdLeaveTime;
	private String fdIsRehire;
	private String fdRehireTime;
	private String fdEmploymentPeriod;
	private String fdStaffType;
	private String fdNameUsedBefore;
	private String fdNation;
	private String fdPoliticalLandscape;
	private String fdDateOfGroup;
	private String fdDateOfParty;
	private String fdHighestEducation;
	private String fdHighestDegree;
	private String fdMaritalStatus;
	private String fdHealth;
	private String fdStature;
	private String fdWeight;
	private String fdLivingPlace;
	private String fdNativePlace;
	private String fdHomeplace;
	private String fdAccountProperties;
	private String fdRegisteredResidence;
	private String fdResidencePoliceStation;
	private String fdStatus;
	private String fdMobileNo;
	private String fdEmail;
	private String fdOfficeLocation;
	private String fdWorkPhone;
	private String fdEmergencyContact;
	private String fdEmergencyContactPhone;
	private String fdOtherContact;
	private String fdOrgPersonId;
	private String fdOrgPersonName;
	private String fdOrgParentOrgId;
	private String fdOrgParentOrgName;
	private String fdOrgParentId;
	private String fdOrgParentName;
	private String fdStaffingLevelId;
	private String fdStaffingLevelName;
	private String fdOrgPostIds;
	private String fdOrgPostNames;
	private String fdHierarchyId;
	private String fdBirthdayOfYear;
	private String fdStaffEntryId;
	private String fdStaffEntryName;
	private String fdLeaveApplyDate;
	private String fdLeavePlanDate;
	private String fdLeaveSalaryEndDate;
	private String fdLeaveReason;
	private String fdLeaveRemark;
	private String fdNextCompany;
	private String fdNatureWork;
	private String fdLeaveStatus;
	private String fdReportLeaderId;
	private String fdReportLeaderName;
	private String fdWorkAddress;
	private String fdLoginName;

	public String getFdSex() {
		return fdSex;
	}

	public void setFdSex(String fdSex) {
		this.fdSex = fdSex;
	}

	public String getFdDateOfBirth() {
		return fdDateOfBirth;
	}

	public void setFdDateOfBirth(String fdDateOfBirth) {
		this.fdDateOfBirth = fdDateOfBirth;
	}

	public String getFdStaffNo() {
		return fdStaffNo;
	}

	public void setFdStaffNo(String fdStaffNo) {
		this.fdStaffNo = fdStaffNo;
	}

	public String getFdIdCard() {
		return fdIdCard;
	}

	public void setFdIdCard(String fdIdCard) {
		this.fdIdCard = fdIdCard;
	}

	public String getFdWorkTime() {
		return fdWorkTime;
	}

	public void setFdWorkTime(String fdWorkTime) {
		this.fdWorkTime = fdWorkTime;
	}

	public String getFdTimeOfEnterprise() {
		return fdTimeOfEnterprise;
	}

	public void setFdTimeOfEnterprise(String fdTimeOfEnterprise) {
		this.fdTimeOfEnterprise = fdTimeOfEnterprise;
	}

	public String getFdTrialExpirationTime() {
		return fdTrialExpirationTime;
	}

	public void setFdTrialExpirationTime(String fdTrialExpirationTime) {
		this.fdTrialExpirationTime = fdTrialExpirationTime;
	}

	public String getFdTrialOperationPeriod() {
		return fdTrialOperationPeriod;
	}

	public void setFdTrialOperationPeriod(String fdTrialOperationPeriod) {
		this.fdTrialOperationPeriod = fdTrialOperationPeriod;
	}

	public String getFdEntryTime() {
		return fdEntryTime;
	}

	public void setFdEntryTime(String fdEntryTime) {
		this.fdEntryTime = fdEntryTime;
	}

	public String getFdPositiveTime() {
		return fdPositiveTime;
	}

	public void setFdPositiveTime(String fdPositiveTime) {
		this.fdPositiveTime = fdPositiveTime;
	}

	public String getFdActualPositiveTime() {
		return fdActualPositiveTime;
	}

	public void setFdActualPositiveTime(String fdActualPositiveTime) {
		this.fdActualPositiveTime = fdActualPositiveTime;
	}

	public String getFdPositiveRemark() {
		return fdPositiveRemark;
	}

	public void setFdPositiveRemark(String fdPositiveRemark) {
		this.fdPositiveRemark = fdPositiveRemark;
	}

	public String getFdLeaveTime() {
		return fdLeaveTime;
	}

	public void setFdLeaveTime(String fdLeaveTime) {
		this.fdLeaveTime = fdLeaveTime;
	}

	public String getFdIsRehire() {
		return fdIsRehire;
	}

	public void setFdIsRehire(String fdIsRehire) {
		this.fdIsRehire = fdIsRehire;
	}

	public String getFdRehireTime() {
		return fdRehireTime;
	}

	public void setFdRehireTime(String fdRehireTime) {
		this.fdRehireTime = fdRehireTime;
	}

	public String getFdEmploymentPeriod() {
		return fdEmploymentPeriod;
	}

	public void setFdEmploymentPeriod(String fdEmploymentPeriod) {
		this.fdEmploymentPeriod = fdEmploymentPeriod;
	}

	public String getFdStaffType() {
		return fdStaffType;
	}

	public void setFdStaffType(String fdStaffType) {
		this.fdStaffType = fdStaffType;
	}

	public String getFdNameUsedBefore() {
		return fdNameUsedBefore;
	}

	public void setFdNameUsedBefore(String fdNameUsedBefore) {
		this.fdNameUsedBefore = fdNameUsedBefore;
	}

	public String getFdNation() {
		return fdNation;
	}

	public void setFdNation(String fdNation) {
		this.fdNation = fdNation;
	}

	public String getFdPoliticalLandscape() {
		return fdPoliticalLandscape;
	}

	public void setFdPoliticalLandscape(String fdPoliticalLandscape) {
		this.fdPoliticalLandscape = fdPoliticalLandscape;
	}

	public String getFdDateOfGroup() {
		return fdDateOfGroup;
	}

	public void setFdDateOfGroup(String fdDateOfGroup) {
		this.fdDateOfGroup = fdDateOfGroup;
	}

	public String getFdDateOfParty() {
		return fdDateOfParty;
	}

	public void setFdDateOfParty(String fdDateOfParty) {
		this.fdDateOfParty = fdDateOfParty;
	}

	public String getFdHighestEducation() {
		return fdHighestEducation;
	}

	public void setFdHighestEducation(String fdHighestEducation) {
		this.fdHighestEducation = fdHighestEducation;
	}

	public String getFdHighestDegree() {
		return fdHighestDegree;
	}

	public void setFdHighestDegree(String fdHighestDegree) {
		this.fdHighestDegree = fdHighestDegree;
	}

	public String getFdMaritalStatus() {
		return fdMaritalStatus;
	}

	public void setFdMaritalStatus(String fdMaritalStatus) {
		this.fdMaritalStatus = fdMaritalStatus;
	}

	public String getFdHealth() {
		return fdHealth;
	}

	public void setFdHealth(String fdHealth) {
		this.fdHealth = fdHealth;
	}

	public String getFdStature() {
		return fdStature;
	}

	public void setFdStature(String fdStature) {
		this.fdStature = fdStature;
	}

	public String getFdWeight() {
		return fdWeight;
	}

	public void setFdWeight(String fdWeight) {
		this.fdWeight = fdWeight;
	}

	public String getFdLivingPlace() {
		return fdLivingPlace;
	}

	public void setFdLivingPlace(String fdLivingPlace) {
		this.fdLivingPlace = fdLivingPlace;
	}

	public String getFdNativePlace() {
		return fdNativePlace;
	}

	public void setFdNativePlace(String fdNativePlace) {
		this.fdNativePlace = fdNativePlace;
	}

	public String getFdHomeplace() {
		return fdHomeplace;
	}

	public void setFdHomeplace(String fdHomeplace) {
		this.fdHomeplace = fdHomeplace;
	}

	public String getFdAccountProperties() {
		return fdAccountProperties;
	}

	public void setFdAccountProperties(String fdAccountProperties) {
		this.fdAccountProperties = fdAccountProperties;
	}

	public String getFdRegisteredResidence() {
		return fdRegisteredResidence;
	}

	public void setFdRegisteredResidence(String fdRegisteredResidence) {
		this.fdRegisteredResidence = fdRegisteredResidence;
	}

	public String getFdResidencePoliceStation() {
		return fdResidencePoliceStation;
	}

	public void setFdResidencePoliceStation(String fdResidencePoliceStation) {
		this.fdResidencePoliceStation = fdResidencePoliceStation;
	}

	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	public String getFdMobileNo() {
		return fdMobileNo;
	}

	public void setFdMobileNo(String fdMobileNo) {
		this.fdMobileNo = fdMobileNo;
	}

	public String getFdEmail() {
		return fdEmail;
	}

	public void setFdEmail(String fdEmail) {
		this.fdEmail = fdEmail;
	}

	public String getFdOfficeLocation() {
		return fdOfficeLocation;
	}

	public void setFdOfficeLocation(String fdOfficeLocation) {
		this.fdOfficeLocation = fdOfficeLocation;
	}

	public String getFdWorkPhone() {
		return fdWorkPhone;
	}

	public void setFdWorkPhone(String fdWorkPhone) {
		this.fdWorkPhone = fdWorkPhone;
	}

	public String getFdEmergencyContact() {
		return fdEmergencyContact;
	}

	public void setFdEmergencyContact(String fdEmergencyContact) {
		this.fdEmergencyContact = fdEmergencyContact;
	}

	public String getFdEmergencyContactPhone() {
		return fdEmergencyContactPhone;
	}

	public void setFdEmergencyContactPhone(String fdEmergencyContactPhone) {
		this.fdEmergencyContactPhone = fdEmergencyContactPhone;
	}

	public String getFdOtherContact() {
		return fdOtherContact;
	}

	public void setFdOtherContact(String fdOtherContact) {
		this.fdOtherContact = fdOtherContact;
	}

	public String getFdOrgPersonId() {
		return fdOrgPersonId;
	}

	public void setFdOrgPersonId(String fdOrgPersonId) {
		this.fdOrgPersonId = fdOrgPersonId;
	}

	public String getFdOrgPersonName() {
		return fdOrgPersonName;
	}

	public void setFdOrgPersonName(String fdOrgPersonName) {
		this.fdOrgPersonName = fdOrgPersonName;
	}

	public String getFdOrgParentOrgId() {
		return fdOrgParentOrgId;
	}

	public void setFdOrgParentOrgId(String fdOrgParentOrgId) {
		this.fdOrgParentOrgId = fdOrgParentOrgId;
	}

	public String getFdOrgParentOrgName() {
		return fdOrgParentOrgName;
	}

	public void setFdOrgParentOrgName(String fdOrgParentOrgName) {
		this.fdOrgParentOrgName = fdOrgParentOrgName;
	}

	public String getFdOrgParentId() {
		return fdOrgParentId;
	}

	public void setFdOrgParentId(String fdOrgParentId) {
		this.fdOrgParentId = fdOrgParentId;
	}

	public String getFdOrgParentName() {
		return fdOrgParentName;
	}

	public void setFdOrgParentName(String fdOrgParentName) {
		this.fdOrgParentName = fdOrgParentName;
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

	public String getFdOrgPostIds() {
		return fdOrgPostIds;
	}

	public void setFdOrgPostIds(String fdOrgPostIds) {
		this.fdOrgPostIds = fdOrgPostIds;
	}

	public String getFdOrgPostNames() {
		return fdOrgPostNames;
	}

	public void setFdOrgPostNames(String fdOrgPostNames) {
		this.fdOrgPostNames = fdOrgPostNames;
	}

	public String getFdHierarchyId() {
		return fdHierarchyId;
	}

	public void setFdHierarchyId(String fdHierarchyId) {
		this.fdHierarchyId = fdHierarchyId;
	}

	public String getFdBirthdayOfYear() {
		return fdBirthdayOfYear;
	}

	public void setFdBirthdayOfYear(String fdBirthdayOfYear) {
		this.fdBirthdayOfYear = fdBirthdayOfYear;
	}

	public String getFdStaffEntryId() {
		return fdStaffEntryId;
	}

	public void setFdStaffEntryId(String fdStaffEntryId) {
		this.fdStaffEntryId = fdStaffEntryId;
	}

	public String getFdStaffEntryName() {
		return fdStaffEntryName;
	}

	public void setFdStaffEntryName(String fdStaffEntryName) {
		this.fdStaffEntryName = fdStaffEntryName;
	}

	public String getFdLeaveApplyDate() {
		return fdLeaveApplyDate;
	}

	public void setFdLeaveApplyDate(String fdLeaveApplyDate) {
		this.fdLeaveApplyDate = fdLeaveApplyDate;
	}

	public String getFdLeavePlanDate() {
		return fdLeavePlanDate;
	}

	public void setFdLeavePlanDate(String fdLeavePlanDate) {
		this.fdLeavePlanDate = fdLeavePlanDate;
	}

	public String getFdLeaveSalaryEndDate() {
		return fdLeaveSalaryEndDate;
	}

	public void setFdLeaveSalaryEndDate(String fdLeaveSalaryEndDate) {
		this.fdLeaveSalaryEndDate = fdLeaveSalaryEndDate;
	}

	public String getFdLeaveReason() {
		return fdLeaveReason;
	}

	public void setFdLeaveReason(String fdLeaveReason) {
		this.fdLeaveReason = fdLeaveReason;
	}

	public String getFdLeaveRemark() {
		return fdLeaveRemark;
	}

	public void setFdLeaveRemark(String fdLeaveRemark) {
		this.fdLeaveRemark = fdLeaveRemark;
	}

	public String getFdNextCompany() {
		return fdNextCompany;
	}

	public void setFdNextCompany(String fdNextCompany) {
		this.fdNextCompany = fdNextCompany;
	}

	public String getFdNatureWork() {
		return fdNatureWork;
	}

	public void setFdNatureWork(String fdNatureWork) {
		this.fdNatureWork = fdNatureWork;
	}

	public String getFdLeaveStatus() {
		return fdLeaveStatus;
	}

	public void setFdLeaveStatus(String fdLeaveStatus) {
		this.fdLeaveStatus = fdLeaveStatus;
	}

	public String getFdReportLeaderId() {
		return fdReportLeaderId;
	}

	public void setFdReportLeaderId(String fdReportLeaderId) {
		this.fdReportLeaderId = fdReportLeaderId;
	}

	public String getFdReportLeaderName() {
		return fdReportLeaderName;
	}

	public void setFdReportLeaderName(String fdReportLeaderName) {
		this.fdReportLeaderName = fdReportLeaderName;
	}

	public String getFdWorkAddress() {
		return fdWorkAddress;
	}

	public void setFdWorkAddress(String fdWorkAddress) {
		this.fdWorkAddress = fdWorkAddress;
	}

	public String getFdLoginName() {
		return fdLoginName;
	}

	public void setFdLoginName(String fdLoginName) {
		this.fdLoginName = fdLoginName;
	}

	private String fdCreator;
	private String fdPersonInfo;

	public String getFdCreator() {
		return fdCreator;
	}

	public void setFdCreator(String fdCreator) {
		this.fdCreator = fdCreator;
	}

	public String getFdPersonInfo() {
		return fdPersonInfo;
	}

	public void setFdPersonInfo(String fdPersonInfo) {
		this.fdPersonInfo = fdPersonInfo;
	}

	private String docSubject;

	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

}
