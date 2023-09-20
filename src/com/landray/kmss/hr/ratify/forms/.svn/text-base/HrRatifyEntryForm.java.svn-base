package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.ratify.model.HrRatifyEntry;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoSettingNew;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 员工入职
  */
public class HrRatifyEntryForm extends HrRatifyMainForm {

    private static FormToModelPropertyMap toModelPropertyMap;

	private String fdHasWrite;

	private String fdIsRecruit;

	private String fdRecruitEntryId;

	private String fdRecruitEntryName;

    private String fdEntryName;

    private String fdLoginName;

    private String fdPassword;

	/*
	 * 编号
	 */
	private String fdNo;

    private String fdEntryDate;

    private String fdEntryTrialPeriod;

    private String fdStaffNo;

    private String fdSalaryAccountName;

    private String fdSalaryAccountId;

    private String fdSalaryAccountBank;

    private String fdFundAccount;

    private String fdSocialSecurityAccount;

    private String fdEntryDeptId;

    private String fdEntryDeptName;

	private String fdEntryPostIds;

	private String fdEntryPostNames;

	private String fdEntryLevelId;

	private String fdEntryLevelName;

    private String fdTeacherId;

    private String fdTeacherName;

    private String fdEntryLeaderId;

    private String fdEntryLeaderName;

	private String fdEntryStatus;

	private String fdEntryPeriodDate;

	private String fdEntryPeriodSalary;

	private String fdEntryPositiveSalary;

	private String fdEntryStaffTypeId;

	private String fdEntryStaffTypeName;

	private String fdEntryWorkAddress;

	private String fdEntryWorkDuty;

	// *************以下是入职员工基本信息字段*************** //
	// 曾用名
	private String fdNameUsedBefore;
	// 性别
	private String fdSex;
	// 出生日期
	private String fdDateOfBirth;
	// 籍贯
	private String fdNativePlace;
	// 婚姻状况
	private String fdMaritalStatusId;
	private String fdMaritalStatusName;
	// 民族
	private String fdNationId;
	private String fdNationName;
	// 政治面貌
	private String fdPoliticalLandscapeId;
	private String fdPoliticalLandscapeName;
	// 健康状况
	private String fdHealthId;
	private String fdHealthName;
	// 现居住地
	private String fdLivingPlace;
	// 身份证号码
	private String fdIdCard;
	// 学位
	private String fdHighestDegreeId;
	private String fdHighestDegreeName;
	// 学历
	private String fdHighestEducationId;
	private String fdHighestEducationName;
	// 专业
	private String fdProfession;
	// 参加工作时间
	private String fdWorkTime;
	// 入团日期
	private String fdDateOfGroup;
	// 入党日期
	private String fdDateOfParty;
	// 身高（厘米）
	private String fdStature;
	// 体重（千克）
	private String fdWeight;
	// 出生地
	private String fdHomeplace;
	// 户口性质
	private String fdAccountProperties;
	// 户口所在地
	private String fdRegisteredResidence;
	// 户口所在派出所
	private String fdResidencePoliceStation;
	// ************以上是入职员工基本信息字段*************** //

	// ************以下是入职员工联系信息字段*************** //
	// 邮箱地址
	private String fdEmail;
	// 手机
	private String fdMobileNo;
	// 紧急联系人
	private String fdEmergencyContact;
	// 紧急联系人电话
	private String fdEmergencyContactPhone;
	// 其他联系方式
	private String fdOtherContact;
	// ************以上是入职员工联系信息字段*************** //

	// *************以下是入职员工个人经历信息*************** //
	// 工作经历
	private AutoArrayList fdHistory_Form = new AutoArrayList(
			HrRatifyHistoryForm.class);
	//private String fdHistory_Flag = "0";
	// 教育记录
	private AutoArrayList fdEducations_Form = new AutoArrayList(
			HrRatifyEduExpForm.class);
	//private String fdEducations_Flag = "0";
	// 资格证书
	private AutoArrayList fdCertificate_Form = new AutoArrayList(
			HrRatifyCertifiForm.class);
	//private String fdCertificate_Flag = "0";
	// 奖惩信息
	private AutoArrayList fdRewardsPunishments_Form = new AutoArrayList(
			HrRatifyRewPuniForm.class);
	//private String fdRewardsPunishments_Flag = "0";
	// *************以上是入职员工个人经历信息*************** //

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdHasWrite = null;
		fdIsRecruit = null;
		fdRecruitEntryId = null;
		fdRecruitEntryName = null;
		fdEntryName = null;
        fdLoginName = null;
        fdPassword = null;
		fdNo = null;
        fdEntryDate = null;
        fdEntryTrialPeriod = null;
        fdStaffNo = null;
        fdSalaryAccountName = null;
        fdSalaryAccountId = null;
        fdSalaryAccountBank = null;
        fdFundAccount = null;
        fdSocialSecurityAccount = null;
        fdEntryDeptId = null;
        fdEntryDeptName = null;
		fdEntryPostIds = null;
		fdEntryPostNames = null;
		fdEntryLevelId = null;
		fdEntryLevelName = null;
        fdTeacherId = null;
        fdTeacherName = null;
        fdEntryLeaderId = null;
        fdEntryLeaderName = null;
		fdEntryStatus = null;
		fdEntryPeriodDate = null;
		fdEntryPeriodSalary = null;
		fdEntryPositiveSalary = null;
		fdEntryStaffTypeId = null;
		fdEntryStaffTypeName = null;
		fdEntryWorkAddress = null;
		fdEntryWorkDuty = null;
		fdNameUsedBefore = null;
		fdSex = null;
		fdDateOfBirth = null;
		fdNativePlace = null;
		fdMaritalStatusId = null;
		fdMaritalStatusName = null;
		fdNationId = null;
		fdNationName = null;
		fdPoliticalLandscapeId = null;
		fdPoliticalLandscapeName = null;
		fdHealthId = null;
		fdHealthName = null;
		fdLivingPlace = null;
		fdIdCard = null;
		fdHighestDegreeId = null;
		fdHighestDegreeName = null;
		fdHighestEducationId = null;
		fdHighestEducationName = null;
		fdProfession = null;
		fdWorkTime = null;
		fdDateOfGroup = null;
		fdDateOfParty = null;
		fdStature = null;
		fdWeight = null;
		fdHomeplace = null;
		fdAccountProperties = null;
		fdRegisteredResidence = null;
		fdResidencePoliceStation = null;
		fdEmail = null;
		fdMobileNo = null;
		fdEmergencyContact = null;
		fdEmergencyContactPhone = null;
		fdOtherContact = null;
		fdHistory_Form = new AutoArrayList(HrRatifyHistoryForm.class);
		//fdHistory_Flag = "0";
		fdEducations_Form = new AutoArrayList(HrRatifyEduExpForm.class);
		//fdEducations_Flag = "0";
		fdCertificate_Form = new AutoArrayList(HrRatifyCertifiForm.class);
		//fdCertificate_Flag = "0";
		fdRewardsPunishments_Form = new AutoArrayList(HrRatifyRewPuniForm.class);
		//fdRewardsPunishments_Flag = "0";
		fdHistory = new AutoArrayList(HrRatifyHistoryForm.class);
		fdEducations = new AutoArrayList(HrRatifyEduExpForm.class);
		fdCertificate = new AutoArrayList(HrRatifyCertifiForm.class);
		fdRewardsPunishments = new AutoArrayList(HrRatifyRewPuniForm.class);
        super.reset(mapping, request);
    }

	@Override
    public Class getModelClass() {
        return HrRatifyEntry.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdEntryDate", new FormConvertor_Common("fdEntryDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdEntryDeptId", new FormConvertor_IDToModel("fdEntryDept", SysOrgElement.class));
			toModelPropertyMap.put("fdEntryPostIds",
					new FormConvertor_IDsToModelList("fdEntryPosts",
							SysOrgPost.class));
			toModelPropertyMap.put("fdEntryLevelId",
					new FormConvertor_IDToModel("fdEntryLevel",
							SysOrganizationStaffingLevel.class));
            toModelPropertyMap.put("fdTeacherId", new FormConvertor_IDToModel("fdTeacher", SysOrgPerson.class));
            toModelPropertyMap.put("fdEntryLeaderId", new FormConvertor_IDToModel("fdEntryLeader", SysOrgPerson.class));
			toModelPropertyMap.put("fdEntryPeriodDate",
					new FormConvertor_Common("fdEntryPeriodDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("fdEntryStaffTypeId",
					new FormConvertor_IDToModel("fdEntryStaffType",
							HrStaffPersonInfoSettingNew.class));
			toModelPropertyMap.put("fdHistory_Form",
					new FormConvertor_FormListToModelList("fdHistory",
							"docMain"));
			toModelPropertyMap.put("fdEducations_Form",
					new FormConvertor_FormListToModelList("fdEducations",
							"docMain" ));
			toModelPropertyMap.put("fdCertificate_Form",
					new FormConvertor_FormListToModelList("fdCertificate",
							"docMain"));
			toModelPropertyMap.put("fdRewardsPunishments_Form",
					new FormConvertor_FormListToModelList(
							"fdRewardsPunishments", "docMain"
							));
			toModelPropertyMap.put("fdNationId", new FormConvertor_IDToModel(
					"fdNation", HrStaffPersonInfoSettingNew.class));
			toModelPropertyMap.put("fdPoliticalLandscapeId",
					new FormConvertor_IDToModel("fdPoliticalLandscape",
							HrStaffPersonInfoSettingNew.class));
			toModelPropertyMap.put("fdHighestEducationId",
					new FormConvertor_IDToModel("fdHighestEducation",
							HrStaffPersonInfoSettingNew.class));
			toModelPropertyMap.put("fdHighestDegreeId",
					new FormConvertor_IDToModel("fdHighestDegree",
							HrStaffPersonInfoSettingNew.class));
			toModelPropertyMap.put("fdMaritalStatusId",
					new FormConvertor_IDToModel("fdMaritalStatus",
							HrStaffPersonInfoSettingNew.class));
			toModelPropertyMap.put("fdHealthId", new FormConvertor_IDToModel(
					"fdHealth", HrStaffPersonInfoSettingNew.class));
        }
        return toModelPropertyMap;
    }

	public String getFdRecruitEntryId() {
		return this.fdRecruitEntryId;
	}

	public void setFdRecruitEntryId(String fdRecruitEntryId) {
		this.fdRecruitEntryId = fdRecruitEntryId;
	}

	public String getFdRecruitEntryName() {
		return this.fdRecruitEntryName;
	}

	public void setFdRecruitEntryName(String fdRecruitEntryName) {
		this.fdRecruitEntryName = fdRecruitEntryName;
	}

	public String getFdHasWrite() {
		return fdHasWrite;
	}

	public void setFdHasWrite(String fdHasWrite) {
		this.fdHasWrite = fdHasWrite;
	}

	public String getFdIsRecruit() {
		return fdIsRecruit;
	}

	public void setFdIsRecruit(String fdIsRecruit) {
		this.fdIsRecruit = fdIsRecruit;
	}

	/**
	 * 入职人员姓名
	 */
    public String getFdEntryName() {
        return this.fdEntryName;
    }

    /**
     * 入职人员姓名
     */
    public void setFdEntryName(String fdEntryName) {
        this.fdEntryName = fdEntryName;
    }

    /**
     * 入职人员系统注册帐号
     */
    public String getFdLoginName() {
        return this.fdLoginName;
    }

    /**
     * 入职人员系统注册帐号
     */
    public void setFdLoginName(String fdLoginName) {
        this.fdLoginName = fdLoginName;
    }

    /**
     * 入职人员系统注册帐号初始密码
     */
    public String getFdPassword() {
        return this.fdPassword;
    }

    /**
     * 入职人员系统注册帐号初始密码
     */
    public void setFdPassword(String fdPassword) {
        this.fdPassword = fdPassword;
    }

	/**
	 * 编号
	 */
	public String getFdNo() {
		return fdNo;
	}

	/**
	 * 编号
	 */
	public void setFdNo(String fdNo) {
		this.fdNo = fdNo;
	}

    /**
     * 入职日期
     */
    public String getFdEntryDate() {
        return this.fdEntryDate;
    }

    /**
     * 入职日期
     */
    public void setFdEntryDate(String fdEntryDate) {
        this.fdEntryDate = fdEntryDate;
    }

    /**
     * 试用期限（月）
     */
    public String getFdEntryTrialPeriod() {
        return this.fdEntryTrialPeriod;
    }

    /**
     * 试用期限（月）
     */
    public void setFdEntryTrialPeriod(String fdEntryTrialPeriod) {
        this.fdEntryTrialPeriod = fdEntryTrialPeriod;
    }

    /**
     * 工号
     */
    public String getFdStaffNo() {
        return this.fdStaffNo;
    }

    /**
     * 工号
     */
    public void setFdStaffNo(String fdStaffNo) {
        this.fdStaffNo = fdStaffNo;
    }

    /**
     * 工资账户名
     */
    public String getFdSalaryAccountName() {
        return this.fdSalaryAccountName;
    }

    /**
     * 工资账户名
     */
    public void setFdSalaryAccountName(String fdSalaryAccountName) {
        this.fdSalaryAccountName = fdSalaryAccountName;
    }

    /**
     * 工资账号
     */
    public String getFdSalaryAccountId() {
        return this.fdSalaryAccountId;
    }

    /**
     * 工资账号
     */
    public void setFdSalaryAccountId(String fdSalaryAccountId) {
        this.fdSalaryAccountId = fdSalaryAccountId;
    }

    /**
     * 工资银行
     */
    public String getFdSalaryAccountBank() {
        return this.fdSalaryAccountBank;
    }

    /**
     * 工资银行
     */
    public void setFdSalaryAccountBank(String fdSalaryAccountBank) {
        this.fdSalaryAccountBank = fdSalaryAccountBank;
    }

    /**
     * 公积金账号
     */
    public String getFdFundAccount() {
        return this.fdFundAccount;
    }

    /**
     * 公积金账号
     */
    public void setFdFundAccount(String fdFundAccount) {
        this.fdFundAccount = fdFundAccount;
    }

    /**
     * 社保账号
     */
    public String getFdSocialSecurityAccount() {
        return this.fdSocialSecurityAccount;
    }

    /**
     * 社保账号
     */
    public void setFdSocialSecurityAccount(String fdSocialSecurityAccount) {
        this.fdSocialSecurityAccount = fdSocialSecurityAccount;
    }

    /**
     * 入职部门
     */
    public String getFdEntryDeptId() {
        return this.fdEntryDeptId;
    }

    /**
     * 入职部门
     */
    public void setFdEntryDeptId(String fdEntryDeptId) {
        this.fdEntryDeptId = fdEntryDeptId;
    }

    /**
     * 入职部门
     */
    public String getFdEntryDeptName() {
        return this.fdEntryDeptName;
    }

    /**
     * 入职部门
     */
    public void setFdEntryDeptName(String fdEntryDeptName) {
        this.fdEntryDeptName = fdEntryDeptName;
    }

    /**
     * 入职岗位
     */
	public String getFdEntryPostIds() {
		return this.fdEntryPostIds;
    }

    /**
     * 入职岗位
     */
	public void setFdEntryPostIds(String fdEntryPostIds) {
		this.fdEntryPostIds = fdEntryPostIds;
    }

    /**
     * 入职岗位
     */
	public String getFdEntryPostNames() {
		return this.fdEntryPostNames;
    }

    /**
     * 入职岗位
     */
	public void setFdEntryPostNames(String fdEntryPostNames) {
		this.fdEntryPostNames = fdEntryPostNames;
    }

	public String getFdEntryLevelId() {
		return fdEntryLevelId;
	}

	public void setFdEntryLevelId(String fdEntryLevelId) {
		this.fdEntryLevelId = fdEntryLevelId;
	}

	public String getFdEntryLevelName() {
		return fdEntryLevelName;
	}

	public void setFdEntryLevelName(String fdEntryLevelName) {
		this.fdEntryLevelName = fdEntryLevelName;
	}

	/**
	 * 导师
	 */
    public String getFdTeacherId() {
        return this.fdTeacherId;
    }

    /**
     * 导师
     */
    public void setFdTeacherId(String fdTeacherId) {
        this.fdTeacherId = fdTeacherId;
    }

    /**
     * 导师
     */
    public String getFdTeacherName() {
        return this.fdTeacherName;
    }

    /**
     * 导师
     */
    public void setFdTeacherName(String fdTeacherName) {
        this.fdTeacherName = fdTeacherName;
    }

    /**
     * 直接上级
     */
    public String getFdEntryLeaderId() {
        return this.fdEntryLeaderId;
    }

    /**
     * 直接上级
     */
    public void setFdEntryLeaderId(String fdEntryLeaderId) {
        this.fdEntryLeaderId = fdEntryLeaderId;
    }

    /**
     * 直接上级
     */
    public String getFdEntryLeaderName() {
        return this.fdEntryLeaderName;
    }

    /**
     * 直接上级
     */
    public void setFdEntryLeaderName(String fdEntryLeaderName) {
        this.fdEntryLeaderName = fdEntryLeaderName;
    }

	public String getFdEntryStatus() {
		return fdEntryStatus;
	}

	public void setFdEntryStatus(String fdEntryStatus) {
		this.fdEntryStatus = fdEntryStatus;
	}

	public String getFdEntryPeriodDate() {
		return fdEntryPeriodDate;
	}

	public void setFdEntryPeriodDate(String fdEntryPeriodDate) {
		this.fdEntryPeriodDate = fdEntryPeriodDate;
	}

	public String getFdEntryPeriodSalary() {
		return HrRatifyUtil.doubleTrans(fdEntryPeriodSalary);
	}

	public void setFdEntryPeriodSalary(String fdEntryPeriodSalary) {
		this.fdEntryPeriodSalary = fdEntryPeriodSalary;
	}

	public String getFdEntryPositiveSalary() {
		return HrRatifyUtil.doubleTrans(fdEntryPositiveSalary);
	}

	public void setFdEntryPositiveSalary(String fdEntryPositiveSalary) {
		this.fdEntryPositiveSalary = fdEntryPositiveSalary;
	}

	public String getFdEntryStaffTypeId() {
		return fdEntryStaffTypeId;
	}

	public void setFdEntryStaffTypeId(String fdEntryStaffTypeId) {
		this.fdEntryStaffTypeId = fdEntryStaffTypeId;
	}

	public String getFdEntryStaffTypeName() {
		return fdEntryStaffTypeName;
	}

	public void setFdEntryStaffTypeName(String fdEntryStaffTypeName) {
		this.fdEntryStaffTypeName = fdEntryStaffTypeName;
	}

	public String getFdEntryWorkAddress() {
		return fdEntryWorkAddress;
	}

	public void setFdEntryWorkAddress(String fdEntryWorkAddress) {
		this.fdEntryWorkAddress = fdEntryWorkAddress;
	}

	public String getFdEntryWorkDuty() {
		return fdEntryWorkDuty;
	}

	public void setFdEntryWorkDuty(String fdEntryWorkDuty) {
		this.fdEntryWorkDuty = fdEntryWorkDuty;
	}

	public String getFdNameUsedBefore() {
		return fdNameUsedBefore;
	}

	public void setFdNameUsedBefore(String fdNameUsedBefore) {
		this.fdNameUsedBefore = fdNameUsedBefore;
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

	public String getFdNativePlace() {
		return fdNativePlace;
	}

	public void setFdNativePlace(String fdNativePlace) {
		this.fdNativePlace = fdNativePlace;
	}

	public String getFdMaritalStatusId() {
		return fdMaritalStatusId;
	}

	public void setFdMaritalStatusId(String fdMaritalStatusId) {
		this.fdMaritalStatusId = fdMaritalStatusId;
	}

	public String getFdMaritalStatusName() {
		return fdMaritalStatusName;
	}

	public void setFdMaritalStatusName(String fdMaritalStatusName) {
		this.fdMaritalStatusName = fdMaritalStatusName;
	}

	public String getFdNationId() {
		return fdNationId;
	}

	public void setFdNationId(String fdNationId) {
		this.fdNationId = fdNationId;
	}

	public String getFdNationName() {
		return fdNationName;
	}

	public void setFdNationName(String fdNationName) {
		this.fdNationName = fdNationName;
	}

	public String getFdPoliticalLandscapeId() {
		return fdPoliticalLandscapeId;
	}

	public void setFdPoliticalLandscapeId(String fdPoliticalLandscapeId) {
		this.fdPoliticalLandscapeId = fdPoliticalLandscapeId;
	}

	public String getFdPoliticalLandscapeName() {
		return fdPoliticalLandscapeName;
	}

	public void setFdPoliticalLandscapeName(String fdPoliticalLandscapeName) {
		this.fdPoliticalLandscapeName = fdPoliticalLandscapeName;
	}

	public String getFdHealthId() {
		return fdHealthId;
	}

	public void setFdHealthId(String fdHealthId) {
		this.fdHealthId = fdHealthId;
	}

	public String getFdHealthName() {
		return fdHealthName;
	}

	public void setFdHealthName(String fdHealthName) {
		this.fdHealthName = fdHealthName;
	}

	public String getFdLivingPlace() {
		return fdLivingPlace;
	}

	public void setFdLivingPlace(String fdLivingPlace) {
		this.fdLivingPlace = fdLivingPlace;
	}

	public String getFdIdCard() {
		return fdIdCard;
	}

	public void setFdIdCard(String fdIdCard) {
		this.fdIdCard = fdIdCard;
	}

	public String getFdHighestDegreeId() {
		return fdHighestDegreeId;
	}

	public void setFdHighestDegreeId(String fdHighestDegreeId) {
		this.fdHighestDegreeId = fdHighestDegreeId;
	}

	public String getFdHighestDegreeName() {
		return fdHighestDegreeName;
	}

	public void setFdHighestDegreeName(String fdHighestDegreeName) {
		this.fdHighestDegreeName = fdHighestDegreeName;
	}

	public String getFdHighestEducationId() {
		return fdHighestEducationId;
	}

	public void setFdHighestEducationId(String fdHighestEducationId) {
		this.fdHighestEducationId = fdHighestEducationId;
	}

	public String getFdHighestEducationName() {
		return fdHighestEducationName;
	}

	public void setFdHighestEducationName(String fdHighestEducationName) {
		this.fdHighestEducationName = fdHighestEducationName;
	}

	public String getFdProfession() {
		return fdProfession;
	}

	public void setFdProfession(String fdProfession) {
		this.fdProfession = fdProfession;
	}

	public String getFdWorkTime() {
		return fdWorkTime;
	}

	public void setFdWorkTime(String fdWorkTime) {
		this.fdWorkTime = fdWorkTime;
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

	public String getFdEmail() {
		return fdEmail;
	}

	public void setFdEmail(String fdEmail) {
		this.fdEmail = fdEmail;
	}

	public String getFdMobileNo() {
		return fdMobileNo;
	}

	public void setFdMobileNo(String fdMobileNo) {
		this.fdMobileNo = fdMobileNo;
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

	public AutoArrayList getFdHistory_Form() {
		return fdHistory_Form;
	}

	public void setFdHistory_Form(AutoArrayList fdHistory_Form) {
		this.fdHistory_Form = fdHistory_Form;
	}

	/*public String getFdHistory_Flag() {
		return fdHistory_Flag;
	}

	public void setFdHistory_Flag(String fdHistory_Flag) {
		this.fdHistory_Flag = fdHistory_Flag;
	}*/

	public AutoArrayList getFdEducations_Form() {
		return fdEducations_Form;
	}

	public void setFdEducations_Form(AutoArrayList fdEducations_Form) {
		this.fdEducations_Form = fdEducations_Form;
	}

/*	public String getFdEducations_Flag() {
		return fdEducations_Flag;
	}

	public void setFdEducations_Flag(String fdEducations_Flag) {
		this.fdEducations_Flag = fdEducations_Flag;
	}*/

	public AutoArrayList getFdCertificate_Form() {
		return fdCertificate_Form;
	}

	public void setFdCertificate_Form(AutoArrayList fdCertificate_Form) {
		this.fdCertificate_Form = fdCertificate_Form;
	}

	/*public String getFdCertificate_Flag() {
		return fdCertificate_Flag;
	}

	public void setFdCertificate_Flag(String fdCertificate_Flag) {
		this.fdCertificate_Flag = fdCertificate_Flag;
	}*/

	public AutoArrayList getFdRewardsPunishments_Form() {
		return fdRewardsPunishments_Form;
	}

	public void
			setFdRewardsPunishments_Form(
					AutoArrayList fdRewardsPunishments_Form) {
		this.fdRewardsPunishments_Form = fdRewardsPunishments_Form;
	}

	/*public String getFdRewardsPunishments_Flag() {
		return fdRewardsPunishments_Flag;
	}

	public void setFdRewardsPunishments_Flag(String fdRewardsPunishments_Flag) {
		this.fdRewardsPunishments_Flag = fdRewardsPunishments_Flag;
	}*/

	// 工作经历
	private AutoArrayList fdHistory = new AutoArrayList(HrRatifyHistoryForm.class);
	// 教育记录
	private AutoArrayList fdEducations = new AutoArrayList(HrRatifyEduExpForm.class);
	// 资格证书
	private AutoArrayList fdCertificate = new AutoArrayList(HrRatifyCertifiForm.class);
	// 奖惩信息
	private AutoArrayList fdRewardsPunishments = new AutoArrayList(HrRatifyRewPuniForm.class);

	public AutoArrayList getFdHistory() {
		return fdHistory;
	}

	public void setFdHistory(AutoArrayList fdHistory) {
		this.fdHistory = fdHistory;
		this.fdHistory_Form = fdHistory;
	}

	public AutoArrayList getFdEducations() {
		return fdEducations;
	}

	public void setFdEducations(AutoArrayList fdEducations) {
		this.fdEducations = fdEducations;
		this.fdEducations_Form = fdEducations;
	}

	public AutoArrayList getFdCertificate() {
		return fdCertificate;
	}

	public void setFdCertificate(AutoArrayList fdCertificate) {
		this.fdCertificate = fdCertificate;
		this.fdCertificate_Form = fdCertificate;
	}

	public AutoArrayList getFdRewardsPunishments() {
		return fdRewardsPunishments;
	}

	public void setFdRewardsPunishments(AutoArrayList fdRewardsPunishments) {
		this.fdRewardsPunishments = fdRewardsPunishments;
		this.fdRewardsPunishments_Form = fdRewardsPunishments;
	}

}
