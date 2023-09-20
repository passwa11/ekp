package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.organization.forms.HrOrganizationElementForm;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.organization.model.HrOrganizationRank;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.tag.forms.SysTagMainForm;
import com.landray.kmss.sys.tag.interfaces.ISysTagMainForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
 * 员工信息
 * 
 * @author 潘永辉 2016-12-26
 * 
 */
public class HrStaffPersonInfoForm extends HrOrganizationElementForm implements
		ISysTagMainForm,IAttachmentForm {
	private static final long serialVersionUID = 1L;

	/*private IHrStaffPersonInfoSettingNewService hrStaffPersonInfoSettingNewService;
	
	public IHrStaffPersonInfoSettingNewService getHrStaffPersonInfoSettingNewServiceImp() {
		if (hrStaffPersonInfoSettingNewService == null) {
			hrStaffPersonInfoSettingNewService = (IHrStaffPersonInfoSettingNewService) SpringBeanUtil
					.getBean("hrStaffPersonInfoSetNewService");
		}
		return hrStaffPersonInfoSettingNewService;
	}*/

	@Override
	public Class getModelClass() {
		return HrStaffPersonInfo.class;
	}

	// 组织架构人员
	private String fdOrgPersonId;

	// 机构：若引用组织架构，则取fdOrgPerson数据
	private String fdOrgParentOrgId;
	private String fdOrgParentOrgName;

	// 部门：若引用组织架构，则取fdOrgPerson数据
	private String fdOrgParentId;
	private String fdOrgParentName;
	// 完整的部门名称
	private String fdOrgParentsName;

	// 岗位：若引用组织架构，则取fdOrgPerson数据
	private String fdOrgPostIds;
	private String fdOrgPostNames;

	// 职务：若引用组织架构，则取fdOrgPerson数据
	private String fdStaffingLevelId;
	private String fdStaffingLevelName;

	private String fdOrgParentDeptName;

	// 姓名：若引用组织架构，则不可修改
	private String fdName;
	// 性别：若引用组织架构，则取fdOrgPerson数据
	private String fdSex;
	// 出生日期
	private String fdDateOfBirth;
	// 年龄
	private String fdAge;
	// 工号
	private String fdStaffNo;
	// 身份证号码
	private String fdIdCard;
	// 参加工作时间
	private String fdWorkTime;
	// 连续工龄
	private String fdUninterruptedWorkTime;
	private String fdUninterruptedWorkTimeValue;
	private String fdUninterruptedWorkTimeYear;
	private String fdUninterruptedWorkTimeMonth;
	// 到本单位时间
	private String fdTimeOfEnterprise;
	// 本企业工龄
	private String fdWorkingYears;
	private String fdWorkingYearsValue;
	private String fdWorkingYearsYear;
	private String fdWorkingYearsMonth;
	//连续工龄差值
	private String fdWorkTimeDiff;
	private String fdEntryTimeDiff;
	//本企业工龄差值
	private String fdWorkingYearsDiff;
	// 试用到期时间
	private String fdTrialExpirationTime;
	// 用工期限
	private String fdEmploymentPeriod;
	// 人员类别：后台配置项
	private String fdStaffType;
	// 曾用名
	private String fdNameUsedBefore;
	// 民族：后台配置项
	private String fdNation;
	// 政治面貌：后台配置项
	private String fdPoliticalLandscape;
	// 入团日期
	private String fdDateOfGroup;
	// 入党日期
	private String fdDateOfParty;
	// 最高学历：后台配置项
	private String fdHighestEducation;
	// 最高学位：后台配置项
	private String fdHighestDegree;
	// 婚姻情况：后台配置项
	private String fdMaritalStatus;
	// 健康情况：后台配置项
	private String fdHealth;
	// 身高（厘米）
	private String fdStature;
	// 体重（千克）
	private String fdWeight;
	// 现居地
	private String fdLivingPlace;
	// 籍贯
	private String fdNativePlace;
	// 出生地
	private String fdHomeplace;
	// 户口性质
	private String fdAccountProperties;
	// 户口所在地
	private String fdRegisteredResidence;
	// 户口所在派出所
	private String fdResidencePoliceStation;

	// 手机：若引用组织架构，则不可修改
	private String fdMobileNo;
	// 邮箱：若引用组织架构，则不可修改
	private String fdEmail;
	// 办公地点
	private String fdOfficeLocation;
	// 办公电话
	private String fdWorkPhone;
	// 紧急联系人
	private String fdEmergencyContact;
	// 紧急联系人电话
	private String fdEmergencyContactPhone;
	// 其他联系方式
	private String fdOtherContact;
	private String fdOrgPostId;
	private String fdOrgPostName;
	public String getFdOrgPostId() {
		return fdOrgPostId;
	}

	public void setFdOrgPostId(String fdOrgPostId) {
		this.fdOrgPostId = fdOrgPostId;
	}

	public String getFdOrgPostName() {
		return fdOrgPostName;
	}

	public void setFdOrgPostName(String fdOrgPostName) {
		this.fdOrgPostName = fdOrgPostName;
	}

	// 状态（取组织架构。在职：有效用户，离职：无效用户）
	// 如果是手工新增的员工，在此字段保存数据
	private String fdStatus;

	// 上传的文件
	private FormFile file;

	// 待确认员工
	private String fdStaffEntryId;
	private String fdStaffEntryName;
	private String fdNatureOfWork;
	private String fdOAAccount;
	private String fdResignationType;
	private String fdResignationDate;
	private String fdReasonForResignation;
	private String fdCostAttribution;
	private String fdProbationPeriod;
	private String fdProposedEmploymentConfirmationDate;

	private String fdHomeAddressProvinceName;
	private String fdHomeAddressCityName;

	public String getFdHomeAddressProvinceName() {
		return fdHomeAddressProvinceName;
	}

	public void setFdHomeAddressProvinceName(String fdHomeAddressProvinceName) {
		this.fdHomeAddressProvinceName = fdHomeAddressProvinceName;
	}

	public String getFdHomeAddressCityName() {
		return fdHomeAddressCityName;
	}

	public void setFdHomeAddressCityName(String fdHomeAddressCityName) {
		this.fdHomeAddressCityName = fdHomeAddressCityName;
	}

	public String getFdNatureOfWork() {
		return fdNatureOfWork;
	}

	public void setFdNatureOfWork(String fdNatureOfWork) {
		this.fdNatureOfWork = fdNatureOfWork;
	}

	public String getFdOAAccount() {
		return fdOAAccount;
	}

	public void setFdOAAccount(String fdOAAccount) {
		this.fdOAAccount = fdOAAccount;
	}

	public String getFdResignationType() {
		return fdResignationType;
	}

	public void setFdResignationType(String fdResignationType) {
		this.fdResignationType = fdResignationType;
	}

	public String getFdResignationDate() {
		return fdResignationDate;
	}

	public void setFdResignationDate(String fdResignationDate) {
		this.fdResignationDate = fdResignationDate;
	}

	public String getFdReasonForResignation() {
		return fdReasonForResignation;
	}

	public void setFdReasonForResignation(String fdReasonForResignation) {
		this.fdReasonForResignation = fdReasonForResignation;
	}

	public String getFdCostAttribution() {
		return fdCostAttribution;
	}

	public void setFdCostAttribution(String fdCostAttribution) {
		this.fdCostAttribution = fdCostAttribution;
	}

	public String getFdProbationPeriod() {
		return fdProbationPeriod;
	}

	public void setFdProbationPeriod(String fdProbationPeriod) {
		this.fdProbationPeriod = fdProbationPeriod;
	}

	public String getFdProposedEmploymentConfirmationDate() {
		return fdProposedEmploymentConfirmationDate;
	}

	public void setFdProposedEmploymentConfirmationDate(
			String fdProposedEmploymentConfirmationDate) {
		this.fdProposedEmploymentConfirmationDate = fdProposedEmploymentConfirmationDate;
	}

	private String fdOrgRankId;
	private String fdOrgRankName;
	private String fdIsAttendance;

	public String getFdOfficeAreaProvinceName() {
		return fdOfficeAreaProvinceName;
	}

	public void setFdOfficeAreaProvinceName(String fdOfficeAreaProvinceName) {
		this.fdOfficeAreaProvinceName = fdOfficeAreaProvinceName;
	}

	public String getFdOfficeAreaCityName() {
		return fdOfficeAreaCityName;
	}

	public void setFdOfficeAreaCityName(String fdOfficeAreaCityName) {
		this.fdOfficeAreaCityName = fdOfficeAreaCityName;
	}

	public String getFdOfficeAreaAreaName() {
		return fdOfficeAreaAreaName;
	}

	public void setFdOfficeAreaAreaName(String fdOfficeAreaAreaName) {
		this.fdOfficeAreaAreaName = fdOfficeAreaAreaName;
	}

	public String getFdOfficeAreaProvinceId() {
		return fdOfficeAreaProvinceId;
	}

	public void setFdOfficeAreaProvinceId(String fdOfficeAreaProvinceId) {
		this.fdOfficeAreaProvinceId = fdOfficeAreaProvinceId;
	}

	public String getFdOfficeAreaCityId() {
		return fdOfficeAreaCityId;
	}

	public void setFdOfficeAreaCityId(String fdOfficeAreaCityId) {
		this.fdOfficeAreaCityId = fdOfficeAreaCityId;
	}

	public String getFdOfficeAreaAreaId() {
		return fdOfficeAreaAreaId;
	}

	public void setFdOfficeAreaAreaId(String fdOfficeAreaAreaId) {
		this.fdOfficeAreaAreaId = fdOfficeAreaAreaId;
	}

	private String fdDepartmentHeadId;
	public String getFdDepartmentHeadName() {
		return fdDepartmentHeadName;
	}

	public void setFdDepartmentHeadName(String fdDepartmentHeadName) {
		this.fdDepartmentHeadName = fdDepartmentHeadName;
	}

	private String fdDepartmentHeadName;
	private String fdOfficeAreaProvinceName;
	private String fdOfficeAreaCityName;
	private String fdOfficeAreaAreaName;
	private String fdOfficeAreaProvinceId;
	private String fdOfficeAreaCityId;
	private String fdOfficeAreaAreaId;
	public String getFdDepartmentHeadId() {
		return fdDepartmentHeadId;
	}

	public void setFdDepartmentHeadId(String fdDepartmentHeadId) {
		this.fdDepartmentHeadId = fdDepartmentHeadId;
	}

	public String getFdIsAttendance() {
		return fdIsAttendance;
	}

	public void setFdIsAttendance(String fdIsAttendance) {
		this.fdIsAttendance = fdIsAttendance;
	}

	/**
	 * 用于日志记录相关
	 */
	private RequestContext requestContext;
	public String getFdOrgRankId() {
		return fdOrgRankId;
	}

	public void setFdOrgRankId(String fdOrgRankId) {
		this.fdOrgRankId = fdOrgRankId;
	}

	public String getFdOrgRankName() {
		return fdOrgRankName;
	}

	public void setFdOrgRankName(String fdOrgRankName) {
		this.fdOrgRankName = fdOrgRankName;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());

			toModelPropertyMap.put("fdOrgPostIds",
					new FormConvertor_IDsToModelList("fdOrgPosts",
							SysOrgPost.class));
			toModelPropertyMap.put("fdOrgRankId",
					new FormConvertor_IDToModel("fdOrgRank",
							HrOrganizationRank.class));
			toModelPropertyMap.put("fdOrgPostId",
					new FormConvertor_IDToModel("fdOrgPost",
							SysOrgElement.class));
			toModelPropertyMap.put("fdOrgParentId",
					new FormConvertor_IDToModel("fdOrgParent",
							SysOrgElement.class));
			toModelPropertyMap.put("fdDepartmentHeadId",
					new FormConvertor_IDToModel("fdDepartmentHead",
							SysOrgElement.class));
			toModelPropertyMap.put("fdThirdLevelDepartmentId",
					new FormConvertor_IDToModel("fdThirdLevelDepartment",
							SysOrgElement.class));
			toModelPropertyMap.put("fdDepartmentHeadId",
					new FormConvertor_IDToModel("fdDepartmentHead",
							SysOrgElement.class));
			toModelPropertyMap.put("fdSecondLevelDepartmentId",
					new FormConvertor_IDToModel("fdSecondLevelDepartment",
							SysOrgElement.class));
			toModelPropertyMap.put("fdFirstLevelDepartmentId",
					new FormConvertor_IDToModel("fdFirstLevelDepartment",
							SysOrgElement.class));
			toModelPropertyMap.put("fdStaffingLevelId",
					new FormConvertor_IDToModel("fdStaffingLevel",
							SysOrganizationStaffingLevel.class));
			toModelPropertyMap.put("fdReportLeaderId",
					new FormConvertor_IDToModel("fdReportLeader",
							SysOrgElement.class));
			toModelPropertyMap.put("fdHeadOfFirstLevelDepartmentId",
					new FormConvertor_IDToModel("fdHeadOfFirstLevelDepartment",
							SysOrgElement.class));
			toModelPropertyMap.put("fdOrgParentOrgId",
					new FormConvertor_IDToModel("fdOrgParentOrg",
							SysOrgElement.class));
			toModelPropertyMap.put("fdPostIds", new FormConvertor_IDsToModelList("fdOrgPosts", SysOrgPost.class));
			toModelPropertyMap.put("fdHrReportLeaderId",
					new FormConvertor_IDToModel("fdHrReportLeader", HrOrganizationElement.class));
		}
		return toModelPropertyMap;
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

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.fdOrgPersonId = null;
		this.fdDepartmentHeadId = null;
		this.fdThirdLevelDepartmentId = null;
		this.fdSecondLevelDepartmentId = null;
		this.fdFirstLevelDepartmentId = null;
		this.fdOrgParentId = null;
		this.fdOrgParentOrgId = null;
		this.fdOrgParentOrgName = null;
		this.fdOrgParentName = null;
		this.fdOrgParentsName = null;
		this.fdOrgPostIds = null;
		this.fdOrgPostNames = null;
		this.fdStaffingLevelId = null;
		this.fdStaffingLevelName = null;
		this.fdName = null;
		this.fdSex = null;
		this.fdDateOfBirth = null;
		this.fdAge = null;
		this.fdStaffNo = null;
		this.fdIdCard = null;
		this.fdWorkTime = null;
		this.fdUninterruptedWorkTime = null;
		this.fdTimeOfEnterprise = null;
		this.fdWorkingYears = null;
		this.fdTrialExpirationTime = null;
		this.fdEmploymentPeriod = null;
		this.fdStaffType = null;
		this.fdNameUsedBefore = null;
		this.fdNation = null;
		this.fdPoliticalLandscape = null;
		this.fdDateOfGroup = null;
		this.fdDepartureTime = null;
		this.fdDateOfParty = null;
		this.fdHighestEducation = null;
		this.fdHighestDegree = null;
		this.fdMaritalStatus = null;
		this.fdHealth = null;
		this.fdStature = null;
		this.fdWeight = null;
		this.fdLivingPlace = null;
		this.fdNativePlace = null;
		this.fdHomeplace = null;
		this.fdAccountProperties = null;
		this.fdRegisteredResidence = null;
		this.fdResidencePoliceStation = null;
		this.fdMobileNo = null;
		this.fdEmail = null;
		this.fdOfficeLocation = null;
		this.fdWorkPhone = null;
		this.fdEmergencyContact = null;
		this.fdEmergencyContactPhone = null;
		this.fdOtherContact = null;
		this.fdStatus = null;
		this.fdTrialOperationPeriod = null;
		this.fdEntryTime = null;
		this.fdPositiveTime = null;
		this.fdPositiveRemark = null;
		this.fdActualPositiveTime = null;
		this.fdLeaveTime = null;
		this.fdActualLeaveTime = null;
		this.fdIsRehire = "false";
		this.fdRehireTime = null;
		this.fdStaffEntryId = null;
		this.fdStaffEntryName = null;
		this.fdLeaveApplyDate = null;
		this.fdLeavePlanDate = null;
		this.fdLeaveReason = null;
		this.fdLeaveRemark = null;
		this.fdLeaveSalaryEndDate = null;
		this.fdNextCompany = null;
		this.autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);
		sysTagMainForm = new SysTagMainForm();
		this.fdWorkTimeDiff = null;
		this.fdEntryTimeDiff = null;
		this.fdWorkingYearsDiff = null;
		this.setFdUninterruptedWorkTimeValue(null);
		this.setFdWorkingYearsValue(null);
		this.fdUninterruptedWorkTimeYear = null;
		this.fdUninterruptedWorkTimeMonth = null;
		this.fdWorkingYearsYear = null;
		this.fdWorkingYearsMonth = null;
		this.fdCanLogin = true;
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

	public String getFdOrgParentsName() {
		return fdOrgParentsName;
	}

	public void setFdOrgParentsName(String fdOrgParentsName) {
		this.fdOrgParentsName = fdOrgParentsName;
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

	@Override
    public String getFdName() {
		return fdName;
	}

	@Override
    public void setFdName(String fdName) {
		this.fdName = fdName;
	}

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

	public String getFdAge() {
		return fdAge;
	}

	public void setFdAge(String fdAge) {
		this.fdAge = fdAge;
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

	public String getFdUninterruptedWorkTime() {
		return fdUninterruptedWorkTime;
	}

	public void setFdUninterruptedWorkTime(String fdUninterruptedWorkTime) {
		this.fdUninterruptedWorkTime = fdUninterruptedWorkTime;
	}

	public String getFdTimeOfEnterprise() {
		return fdTimeOfEnterprise;
	}

	public void setFdTimeOfEnterprise(String fdTimeOfEnterprise) {
		this.fdTimeOfEnterprise = fdTimeOfEnterprise;
	}

	public String getFdWorkingYears() {
		return fdWorkingYears;
	}

	public void setFdWorkingYears(String fdWorkingYears) {
		this.fdWorkingYears = fdWorkingYears;
	}

	public String getFdTrialExpirationTime() {
		return fdTrialExpirationTime;
	}

	public void setFdTrialExpirationTime(String fdTrialExpirationTime) {
		this.fdTrialExpirationTime = fdTrialExpirationTime;
	}

	public String getFdEmploymentPeriod() {
		return fdEmploymentPeriod;
	}

	public void setFdEmploymentPeriod(String fdEmploymentPeriod) {
		this.fdEmploymentPeriod = fdEmploymentPeriod;
	}

	public String getFdStaffType() throws Exception {
		/*HrStaffPersonInfoSettingNew settingNew = getHrStaffPersonInfoSettingNewServiceImp().getByType("fdStaffType",
				this.fdStaffType);
		if (null != settingNew) {
			return settingNew.getFdName();
		}*/
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

	public String getFdNation() throws Exception {
		/*HrStaffPersonInfoSettingNew settingNew = getHrStaffPersonInfoSettingNewServiceImp().getByType("fdNation",
				this.fdNation);
		if (null != settingNew) {
			return settingNew.getFdName();
		}*/
		return fdNation;
	}

	public void setFdNation(String fdNation) {
		this.fdNation = fdNation;
	}

	public String getFdPoliticalLandscape() throws Exception {
		/*HrStaffPersonInfoSettingNew settingNew = getHrStaffPersonInfoSettingNewServiceImp()
				.getByType("fdPoliticalLandscape", this.fdPoliticalLandscape);
		if (null != settingNew) {
			return settingNew.getFdName();
		}*/
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

	public String getFdHighestEducation() throws Exception {
		/*HrStaffPersonInfoSettingNew settingNew = getHrStaffPersonInfoSettingNewServiceImp()
				.getByType("fdHighestEducation", this.fdHighestEducation);
		if (null != settingNew) {
			return settingNew.getFdName();
		}*/
		return fdHighestEducation;
	}

	public void setFdHighestEducation(String fdHighestEducation) {
		this.fdHighestEducation = fdHighestEducation;
	}

	public String getFdHighestDegree() throws Exception {
		/*HrStaffPersonInfoSettingNew settingNew = getHrStaffPersonInfoSettingNewServiceImp().getByType("fdHighestDegree",
				this.fdHighestDegree);
		if (null != settingNew) {
			return settingNew.getFdName();
		}*/
		return fdHighestDegree;
	}

	public void setFdHighestDegree(String fdHighestDegree) {
		this.fdHighestDegree = fdHighestDegree;
	}

	public String getFdMaritalStatus() throws Exception {
		/*HrStaffPersonInfoSettingNew settingNew = getHrStaffPersonInfoSettingNewServiceImp().getByType("fdMaritalStatus",
				this.fdMaritalStatus);
		if (null != settingNew) {
			return settingNew.getFdName();
		}*/
		return fdMaritalStatus;
	}

	public void setFdMaritalStatus(String fdMaritalStatus) {
		this.fdMaritalStatus = fdMaritalStatus;
	}

	public String getFdHealth() throws Exception {
		/*HrStaffPersonInfoSettingNew settingNew = getHrStaffPersonInfoSettingNewServiceImp().getByType("fdHealth",
				this.fdHealth);
		if (null != settingNew) {
			return settingNew.getFdName();
		}*/
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

	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	@Override
    public FormFile getFile() {
		return file;
	}

	@Override
    public void setFile(FormFile file) {
		this.file = file;
	}

	// 初始化，防止查看时拋异常
	private SysTagMainForm sysTagMainForm = new SysTagMainForm();

	@Override
	public SysTagMainForm getSysTagMainForm() {
		return sysTagMainForm;
	}

	public void setSysTagMainForm(SysTagMainForm sysTagMainForm) {
		this.sysTagMainForm = sysTagMainForm;
	}

	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);
	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

	//试用期限
	private String fdTrialOperationPeriod;

	public String getFdTrialOperationPeriod() {
		return fdTrialOperationPeriod;
	}

	public void setFdTrialOperationPeriod(String fdTrialOperationPeriod) {
		this.fdTrialOperationPeriod = fdTrialOperationPeriod;
	}

	// 入职日期
	private String fdEntryTime;

	// 转正日期
	private String fdPositiveTime;

	// 实际转正日期
	private String fdActualPositiveTime;

	// 转正备注
	private String fdPositiveRemark;

	// 离职日期
	private String fdLeaveTime;
	
	// 实际离职日期
	private String fdActualLeaveTime;

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

	public String getFdLeaveTime() {
		return fdLeaveTime;
	}

	public void setFdLeaveTime(String fdLeaveTime) {
		this.fdLeaveTime = fdLeaveTime;
	}
	
	public String getFdActualLeaveTime() {
		return fdActualLeaveTime;
	}

	public void setFdActualLeaveTime(String fdActualLeaveTime) {
		this.fdActualLeaveTime = fdActualLeaveTime;
	}

	// 是否返聘
	private String fdIsRehire;

	// 返聘日期
	private String fdRehireTime;

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

	// 离职申请日期
	private String fdLeaveApplyDate;

	// 计划离职日期
	private String fdLeavePlanDate;

	// 薪酬结算日期
	private String fdLeaveSalaryEndDate;

	// 离职原因
	private String fdLeaveReason;

	// 离职备注
	private String fdLeaveRemark;

	// 离职去向
	private String fdNextCompany;

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

	// 工作性质
	private String fdNatureWork;

	public String getFdNatureWork() throws Exception {
		/*HrStaffPersonInfoSettingNew settingNew = getHrStaffPersonInfoSettingNewServiceImp().getByType("fdNatureWork",
				this.fdNatureWork);
		if (null != settingNew) {
			return settingNew.getFdName();
		}*/
		return fdNatureWork;
	}

	public void setFdNatureWork(String fdNatureWork) {
		this.fdNatureWork = fdNatureWork;
	}

	// 汇报上级
	private String fdReportLeaderId;
	private String fdReportLeaderName;
	public String getFdHeadOfFirstLevelDepartmentName() {
		return fdHeadOfFirstLevelDepartmentName;
	}

	public void setFdHeadOfFirstLevelDepartmentName(String fdHeadOfFirstLevelDepartmentName) {
		this.fdHeadOfFirstLevelDepartmentName = fdHeadOfFirstLevelDepartmentName;
	}

	private String fdHeadOfFirstLevelDepartmentName;
	private String fdHrReportLeaderId;
	private String fdHrReportLeaderName;
	private String fdPostalAddress;
	private String fdDirectSuperiorJobNumber;

	private String fdHeadOfFirstLevelDepartmentId;
	public String getFdHeadOfFirstLevelDepartmentId() {
		return fdHeadOfFirstLevelDepartmentId;
	}

	public void setFdHeadOfFirstLevelDepartmentId(String fdHeadOfFirstLevelDepartmentId) {
		this.fdHeadOfFirstLevelDepartmentId = fdHeadOfFirstLevelDepartmentId;
	}

	private String fdFixedShift;

	private String fdPlaceOfInsurancePayment;
	private String fdIsOAUser;

	private String fdDepartureTime;

	public void setFdDepartureTime(String fdDepartureTime) {
		this.fdDepartureTime = fdDepartureTime;
	}

	private String fdOfficeArea;

	private String fdOfficeLine;

	private String fdOfficeExtension;

	private String fdPrivateMailbox;

	private String fdRelationsOfEmergencyContactAndEmployee;

	private String fdEmergencyContactAddress;

	public String getFdEmergencyContactAddress() {
		return fdEmergencyContactAddress;
	}

	public void setFdEmergencyContactAddress(String fdEmergencyContactAddress) {
		this.fdEmergencyContactAddress = fdEmergencyContactAddress;
	}
	public String getFdRelationsOfEmergencyContactAndEmployee() {
		return fdRelationsOfEmergencyContactAndEmployee;
	}

	public void setFdRelationsOfEmergencyContactAndEmployee(
			String fdRelationsOfEmergencyContactAndEmployee) {
		this.fdRelationsOfEmergencyContactAndEmployee = fdRelationsOfEmergencyContactAndEmployee;
	}
	public String getFdPrivateMailbox() {
		return fdPrivateMailbox;
	}

	public void setFdPrivateMailbox(String fdPrivateMailbox) {
		this.fdPrivateMailbox = fdPrivateMailbox;
	}
	public String getFdOfficeExtension() {
		return fdOfficeExtension;
	}

	public void setFdOfficeExtension(String fdOfficeExtension) {
		this.fdOfficeExtension = fdOfficeExtension;
	}
	public String getFdOfficeLine() {
		return fdOfficeLine;
	}

	public void setFdOfficeLine(String fdOfficeLine) {
		this.fdOfficeLine = fdOfficeLine;
	}
	public String getFdOfficeArea() {
		return fdOfficeArea;
	}

	public void setFdOfficeArea(String fdOfficeArea) {
		this.fdOfficeArea = fdOfficeArea;
	}

	public String getFdDepartureTime() {
		return fdDepartureTime;
	}



	public String getFdIsOAUser() {
		return fdIsOAUser;
	}

	public void setFdIsOAUser(String fdIsOAUser) {
		this.fdIsOAUser = fdIsOAUser;
	}

	public String getFdPlaceOfInsurancePayment() {
		return fdPlaceOfInsurancePayment;
	}

	public void setFdPlaceOfInsurancePayment(String fdPlaceOfInsurancePayment) {
		this.fdPlaceOfInsurancePayment = fdPlaceOfInsurancePayment;
	}

	public String getFdFixedShift() {
		return fdFixedShift;
	}

	public void setFdFixedShift(String fdFixedShift) {
		this.fdFixedShift = fdFixedShift;
	}

	private String fdPrincipalIdentification;

	public String getFdPrincipalIdentification() {
		return fdPrincipalIdentification;
	}

	public void setFdPrincipalIdentification(String fdPrincipalIdentification) {
		this.fdPrincipalIdentification = fdPrincipalIdentification;
	}

	private String fdHeadOfFirstLevelDepartment;

	public String getFdHeadOfFirstLevelDepartment() {
		return fdHeadOfFirstLevelDepartment;
	}

	public void
			setFdHeadOfFirstLevelDepartment(
					String fdHeadOfFirstLevelDepartment) {
		this.fdHeadOfFirstLevelDepartment = fdHeadOfFirstLevelDepartment;
	}

	private String fdDepartmentHead;

	public String getFdDepartmentHead() {
		return fdDepartmentHead;
	}

	public void setFdDepartmentHead(String fdDepartmentHead) {
		this.fdDepartmentHead = fdDepartmentHead;
	}

	public String getFdDirectSuperiorJobNumber() {
		return fdDirectSuperiorJobNumber;
	}

	public void setFdDirectSuperiorJobNumber(String fdDirectSuperiorJobNumber) {
		this.fdDirectSuperiorJobNumber = fdDirectSuperiorJobNumber;
	}

	private String fdThirdLevelDepartmentId;
	private String fdThirdLevelDepartmentName;

	private String fdCategory;

	private String fdOrgRank1;

	public String getFdOrgRank1() {
		return fdOrgRank1;
	}

	public void setFdOrgRank1(String fdOrgRank1) {
		this.fdOrgRank1 = fdOrgRank1;
	}

	public String getFdCategory() {
		return fdCategory;
	}

	public void setFdCategory(String fdCategory) {
		this.fdCategory = fdCategory;
	}

	private String fdTimeCardNo;

	public String getFdTimeCardNo() {
		return fdTimeCardNo;
	}

	public void setFdTimeCardNo(String fdTimeCardNo) {
		this.fdTimeCardNo = fdTimeCardNo;
	}

	private String fdAffiliatedCompany;

	public String getFdAffiliatedCompany() {
		return fdAffiliatedCompany;
	}

	public void setFdAffiliatedCompany(String fdAffiliatedCompany) {
		this.fdAffiliatedCompany = fdAffiliatedCompany;
	}

	
	public String getFdThirdLevelDepartmentId() {
		return fdThirdLevelDepartmentId;
	}

	public void setFdThirdLevelDepartmentId(String fdThirdLevelDepartmentId) {
		this.fdThirdLevelDepartmentId = fdThirdLevelDepartmentId;
	}

	public String getFdThirdLevelDepartmentName() {
		return fdThirdLevelDepartmentName;
	}

	public void setFdThirdLevelDepartmentName(String fdThirdLevelDepartmentName) {
		this.fdThirdLevelDepartmentName = fdThirdLevelDepartmentName;
	}

	private String fdSecondLevelDepartmentId;
	private String fdSecondLevelDepartmentName;

	

	public String getFdSecondLevelDepartmentId() {
		return fdSecondLevelDepartmentId;
	}

	public void setFdSecondLevelDepartmentId(String fdSecondLevelDepartmentId) {
		this.fdSecondLevelDepartmentId = fdSecondLevelDepartmentId;
	}

	public String getFdSecondLevelDepartmentName() {
		return fdSecondLevelDepartmentName;
	}

	public void setFdSecondLevelDepartmentName(String fdSecondLevelDepartmentName) {
		this.fdSecondLevelDepartmentName = fdSecondLevelDepartmentName;
	}

	private String fdFirstLevelDepartmentId;
	private String fdFirstLevelDepartmentName;

	private String fdStaffingLevel1;

	public String getFdStaffingLevel1() {
		return fdStaffingLevel1;
	}

	public void setFdStaffingLevel1(String fdStaffingLevel1) {
		this.fdStaffingLevel1 = fdStaffingLevel1;
	}

	private String fdPostalAddressAreaId;

	private String fdHomeAddressProvinceId;
	private String fdHomeAddressCityId;
	private String fdHomeAddressAreaId;
	private String fdHomeAddressAreaName;
	private String fdPostalAddressProvinceName;
	private String area;
	private String fdPostalAddressCityName;
	private String fdPostalAddressAreaName;

	public String getFdPostalAddressAreaName() {
		return fdPostalAddressAreaName;
	}

	public void setFdPostalAddressAreaName(String fdPostalAddressAreaName) {
		this.fdPostalAddressAreaName = fdPostalAddressAreaName;
	}

	public String getFdPostalAddressCityName() {
		return fdPostalAddressCityName;
	}

	public void setFdPostalAddressCityName(String fdPostalAddressCityName) {
		this.fdPostalAddressCityName = fdPostalAddressCityName;
	}

	public String getFdPostalAddressProvinceName() {
		return fdPostalAddressProvinceName;
	}

	public void
			setFdPostalAddressProvinceName(String fdPostalAddressProvinceName) {
		this.fdPostalAddressProvinceName = fdPostalAddressProvinceName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFdHomeAddressAreaName() {
		return fdHomeAddressAreaName;
	}

	public void setFdHomeAddressAreaName(String fdHomeAddressAreaName) {
		this.fdHomeAddressAreaName = fdHomeAddressAreaName;
	}

	public String getFdHomeAddressProvinceId() {
		return fdHomeAddressProvinceId;
	}

	public void setFdHomeAddressProvinceId(String fdHomeAddressProvinceId) {
		this.fdHomeAddressProvinceId = fdHomeAddressProvinceId;
	}

	public String getFdHomeAddressCityId() {
		return fdHomeAddressCityId;
	}

	public void setFdHomeAddressCityId(String fdHomeAddressCityId) {
		this.fdHomeAddressCityId = fdHomeAddressCityId;
	}

	public String getFdHomeAddressAreaId() {
		return fdHomeAddressAreaId;
	}

	public void setFdHomeAddressAreaId(String fdHomeAddressAreaId) {
		this.fdHomeAddressAreaId = fdHomeAddressAreaId;
	}

	public String getFdPostalAddressAreaId() {
		return fdPostalAddressAreaId;
	}

	public void setFdPostalAddressAreaId(String fdPostalAddressAreaId) {
		this.fdPostalAddressAreaId = fdPostalAddressAreaId;
	}



	public String getFdFirstLevelDepartmentId() {
		return fdFirstLevelDepartmentId;
	}

	public void setFdFirstLevelDepartmentId(String fdFirstLevelDepartmentId) {
		this.fdFirstLevelDepartmentId = fdFirstLevelDepartmentId;
	}

	public String getFdFirstLevelDepartmentName() {
		return fdFirstLevelDepartmentName;
	}

	public void setFdFirstLevelDepartmentName(String fdFirstLevelDepartmentName) {
		this.fdFirstLevelDepartmentName = fdFirstLevelDepartmentName;
	}

	public String getFdIsRetiredSoldier() {
		return fdIsRetiredSoldier;
	}

	public void setFdIsRetiredSoldier(String fdIsRetiredSoldier) {
		this.fdIsRetiredSoldier = fdIsRetiredSoldier;
	}

	private String fdPostalAddressCityId;

	public String getFdPostalAddressCityId() {
		return fdPostalAddressCityId;
	}

	public void setFdPostalAddressCityId(String fdPostalAddressCityId) {
		this.fdPostalAddressCityId = fdPostalAddressCityId;
	}

	private String fdPostalAddressProvinceId;

	public String getFdPostalAddressProvinceId() {
		return fdPostalAddressProvinceId;
	}

	public void setFdPostalAddressProvinceId(String fdPostalAddressProvinceId) {
		this.fdPostalAddressProvinceId = fdPostalAddressProvinceId;
	}

	private String fdIsRetiredSoldier;
	private String fdForeignLanguageLevel;

	public String getFdForeignLanguageLevel() {
		return fdForeignLanguageLevel;
	}

	public void setFdForeignLanguageLevel(String fdForeignLanguageLevel) {
		this.fdForeignLanguageLevel = fdForeignLanguageLevel;
	}

	private String fdHomeAddress;
	public String getFdPostalAddress() {
		return fdPostalAddress;
	}

	public String getFdHomeAddress() {
		return fdHomeAddress;
	}

	public void setFdHomeAddress(String fdHomeAddress) {
		this.fdHomeAddress = fdHomeAddress;
	}

	public void setFdPostalAddress(String fdPostalAddress) {
		this.fdPostalAddress = fdPostalAddress;
	}

	// 工作地点
	private String fdWorkAddress;
	// 系统账号
	private String fdLoginName;
	private String fdSocialSecurityParticipatingCompany;

	public String getFdSocialSecurityParticipatingCompany() {
		return fdSocialSecurityParticipatingCompany;
	}

	public void setFdSocialSecurityParticipatingCompany(
			String fdSocialSecurityParticipatingCompany) {
		this.fdSocialSecurityParticipatingCompany = fdSocialSecurityParticipatingCompany;
	}

	public String getFdProvidentFundInsuranceCompany() {
		return fdProvidentFundInsuranceCompany;
	}

	public void setFdProvidentFundInsuranceCompany(
			String fdProvidentFundInsuranceCompany) {
		this.fdProvidentFundInsuranceCompany = fdProvidentFundInsuranceCompany;
	}

	private String fdProvidentFundInsuranceCompany;
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

	public String getFdWorkAddress() throws Exception {
		/*HrStaffPersonInfoSettingNew settingNew = getHrStaffPersonInfoSettingNewServiceImp().getByType("fdWorkAddress",
				this.fdWorkAddress);
		if (null != settingNew) {
			return settingNew.getFdName();
		}*/
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

	/*
	 * 密码，仅页面展现用
	 */
	private String fdNewPassword;

	public String getFdNewPassword() {
		return fdNewPassword;
	}

	public void setFdNewPassword(String fdNewPassword) {
		this.fdNewPassword = fdNewPassword;
	}

	public String getFdOrgParentDeptName() {
		return fdOrgParentDeptName;
	}

	public void setFdOrgParentDeptName(String fdOrgParentDeptName) {
		this.fdOrgParentDeptName = fdOrgParentDeptName;
	}

	/*
	 * 岗位列表
	 */
	private String fdPostIds = null;

	private String fdPostNames = null;

	public String getFdPostIds() {
		return fdPostIds;
	}

	public void setFdPostIds(String fdPostIds) {
		this.fdPostIds = fdPostIds;
	}

	public String getFdPostNames() {
		return fdPostNames;
	}

	public void setFdPostNames(String fdPostNames) {
		this.fdPostNames = fdPostNames;
	}

	private String fdSource;

	public String getFdSource() {
		return fdSource;
	}

	public void setFdSource(String fdSource) {
		this.fdSource = fdSource;
	}

	public String getFdHrReportLeaderId() {
		return fdHrReportLeaderId;
	}

	public String getFdHrReportLeaderName() {
		return fdHrReportLeaderName;
	}

	public void setFdHrReportLeaderId(String fdHrReportLeaderId) {
		this.fdHrReportLeaderId = fdHrReportLeaderId;
	}

	public void setFdHrReportLeaderName(String fdHrReportLeaderName) {
		this.fdHrReportLeaderName = fdHrReportLeaderName;
	}

	/**
	 * 标记是否启用、禁用账号
	 */
	private String fdAccountFlag;

	public String getFdAccountFlag() {
		return fdAccountFlag;
	}

	public void setFdAccountFlag(String fdAccountFlag) {
		this.fdAccountFlag = fdAccountFlag;
	}

	public String getFdWorkTimeDiff() {
		return fdWorkTimeDiff;
	}

	public String getFdEntryTimeDiff() {
		return fdEntryTimeDiff;
	}
	public void setFdWorkTimeDiff(String fdWorkTimeDiff) {
		this.fdEntryTimeDiff = fdEntryTimeDiff;
	}

	public String getFdWorkingYearsDiff() {
		return fdWorkingYearsDiff;
	}

	public void setFdWorkingYearsDiff(String fdWorkingYearsDiff) {
		this.fdWorkingYearsDiff = fdWorkingYearsDiff;
	}

	public String getFdUninterruptedWorkTimeValue() {
		return fdUninterruptedWorkTimeValue;
	}

	public void setFdUninterruptedWorkTimeValue(String fdUninterruptedWorkTimeValue) {
		this.fdUninterruptedWorkTimeValue = fdUninterruptedWorkTimeValue;
	}

	public String getFdWorkingYearsValue() {
		return fdWorkingYearsValue;
	}

	public void setFdWorkingYearsValue(String fdWorkingYearsValue) {
		this.fdWorkingYearsValue = fdWorkingYearsValue;
	}

	public String getFdUninterruptedWorkTimeYear() {
		if(StringUtil.isNotNull(this.fdUninterruptedWorkTimeValue)) {
			Integer value =Integer.valueOf(this.fdUninterruptedWorkTimeValue);
			if(StringUtil.isNotNull(this.fdWorkTimeDiff)) {
				value+=Integer.valueOf(this.fdWorkTimeDiff);
			}
			this.fdUninterruptedWorkTimeYear = String.valueOf(value / 12);
		}
		return fdUninterruptedWorkTimeYear;
	}

	public void setFdUninterruptedWorkTimeYear(String fdUninterruptedWorkTimeYear) {
		this.fdUninterruptedWorkTimeYear = fdUninterruptedWorkTimeYear;
	}

	public String getFdUninterruptedWorkTimeMonth() {
		if(StringUtil.isNotNull(this.fdUninterruptedWorkTimeValue)) {
			Integer value =Integer.valueOf(this.fdUninterruptedWorkTimeValue);
			if(StringUtil.isNotNull(this.fdWorkTimeDiff)) {
				value+=Integer.valueOf(this.fdWorkTimeDiff);
			}
			this.fdUninterruptedWorkTimeMonth = String.valueOf(value % 12);
		}
		return fdUninterruptedWorkTimeMonth;
	}

	public void setFdUninterruptedWorkTimeMonth(String fdUninterruptedWorkTimeMonth) {
		this.fdUninterruptedWorkTimeMonth = fdUninterruptedWorkTimeMonth;
	}

	public String getFdWorkingYearsYear() {
		if(StringUtil.isNotNull(this.fdWorkingYearsValue)) {
			Integer value =Integer.valueOf(this.fdWorkingYearsValue);
			if(StringUtil.isNotNull(this.fdWorkingYearsDiff)) {
				value+=Integer.valueOf(this.fdWorkingYearsDiff);
			}
			this.fdWorkingYearsYear = String.valueOf(value / 12);
		}
		return fdWorkingYearsYear;
	}

	public void setFdWorkingYearsYear(String fdWorkingYearsYear) {
		this.fdWorkingYearsYear = fdWorkingYearsYear;
	}

	public String getFdWorkingYearsMonth() {
		if(StringUtil.isNotNull(this.fdWorkingYearsValue)) {
			Integer value =Integer.valueOf(this.fdWorkingYearsValue);
			if(StringUtil.isNotNull(this.fdWorkingYearsDiff)) {
				value+=Integer.valueOf(this.fdWorkingYearsDiff);
			}
			this.fdWorkingYearsMonth = String.valueOf(value % 12);
		}
		return fdWorkingYearsMonth;
	}

	public void setFdWorkingYearsMonth(String fdWorkingYearsMonth) {
		this.fdWorkingYearsMonth = fdWorkingYearsMonth;
	}

	public RequestContext getRequestContext() {
		return requestContext;
	}

	public void setRequestContext(RequestContext requestContext) {
		this.requestContext = requestContext;
	}

	/**
	 * 是否登录系统
	 */
	private Boolean fdCanLogin;

	public Boolean getFdCanLogin() {
		return fdCanLogin;
	}

	public void setFdCanLogin(Boolean fdCanLogin) {
		this.fdCanLogin = fdCanLogin;
	}
}
