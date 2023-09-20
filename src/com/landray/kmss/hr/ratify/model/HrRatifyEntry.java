package com.landray.kmss.hr.ratify.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.ratify.forms.HrRatifyEntryForm;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoSettingNew;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.util.DateUtil;

/**
  * 员工入职
  */
public class HrRatifyEntry extends HrRatifyMain {

    private static ModelToFormPropertyMap toFormPropertyMap;

	private Boolean fdHasWrite;
	/**
	 * 人事档案、待确认的表的fdId
	 */
	private String fdRecruitEntryId;

    private String fdEntryName;

    private String fdLoginName;

    private String fdPassword;

	/*
	 * 编号
	 */
	private String fdNo;

    private Date fdEntryDate;

    private String fdEntryTrialPeriod;

    private String fdStaffNo;

    private String fdSalaryAccountName;

    private String fdSalaryAccountId;

    private String fdSalaryAccountBank;

    private String fdFundAccount;

    private String fdSocialSecurityAccount;

    private SysOrgElement fdEntryDept;

	private SysOrganizationStaffingLevel fdEntryLevel;

    private SysOrgPerson fdTeacher;

    private SysOrgPerson fdEntryLeader;

	private String fdEntryStatus;

	private Date fdEntryPeriodDate;

	private Double fdEntryPeriodSalary;

	private Double fdEntryPositiveSalary;

	private HrStaffPersonInfoSettingNew fdEntryStaffType;

	private String fdEntryWorkAddress;

	private String fdEntryWorkDuty;

	// *************以下是入职员工基本信息字段*************** //
	// 曾用名
	private String fdNameUsedBefore;
	// 性别
	private String fdSex;
	// 出生日期
	private Date fdDateOfBirth;
	// 籍贯
	private String fdNativePlace;
	// 婚姻状况
	private HrStaffPersonInfoSettingNew fdMaritalStatus;
	// 民族
	private HrStaffPersonInfoSettingNew fdNation;
	// 政治面貌
	private HrStaffPersonInfoSettingNew fdPoliticalLandscape;
	// 健康状况
	private HrStaffPersonInfoSettingNew fdHealth;
	// 现居住地
	private String fdLivingPlace;
	// 身份证号码
	private String fdIdCard;
	// 学位
	private HrStaffPersonInfoSettingNew fdHighestDegree;
	// 学历
	private HrStaffPersonInfoSettingNew fdHighestEducation;
	// 专业
	private String fdProfession;
	// 参加工作时间
	private Date fdWorkTime;
	// 入团日期
	private Date fdDateOfGroup;
	// 入党日期
	private Date fdDateOfParty;
	// 身高（厘米）
	private Integer fdStature;
	// 体重（千克）
	private Integer fdWeight;
	// 出生地
	private String fdHomeplace;
	// 户口性质
	private String fdAccountProperties;
	// 户口所在地
	private String fdRegisteredResidence;
	// 户口所在派出所
	private String fdResidencePoliceStation;
	// *************以上是入职员工基本信息字段*************** //

	// *************以下是入职员工联系信息字段*************** //
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
	// ************以上是入职员工联系信息字段**************** //

	private SysOrgPerson fdOrgPerson;

	private Boolean fdIsRecruit;

	@Override
    public Class getFormClass() {
        return HrRatifyEntryForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdEntryDate", new ModelConvertor_Common("fdEntryDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdEntryDept.deptLevelNames", "fdEntryDeptName");
            toFormPropertyMap.put("fdEntryDept.fdId", "fdEntryDeptId");
			toFormPropertyMap.put("fdEntryPosts",
					new ModelConvertor_ModelListToString(
							"fdEntryPostIds:fdEntryPostNames", "fdId:fdName"));
			toFormPropertyMap.put("fdEntryLevel.fdId", "fdEntryLevelId");
			toFormPropertyMap.put("fdEntryLevel.fdName", "fdEntryLevelName");
            toFormPropertyMap.put("fdTeacher.fdName", "fdTeacherName");
            toFormPropertyMap.put("fdTeacher.fdId", "fdTeacherId");
            toFormPropertyMap.put("fdEntryLeader.fdName", "fdEntryLeaderName");
            toFormPropertyMap.put("fdEntryLeader.fdId", "fdEntryLeaderId");
			toFormPropertyMap.put("fdEntryPeriodDate",
					new ModelConvertor_Common("fdEntryPeriodDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdEntryStaffType.fdId",
					"fdEntryStaffTypeId");
			toFormPropertyMap.put("fdEntryStaffType.fdName",
					"fdEntryStaffTypeName");
			toFormPropertyMap.put("fdDateOfBirth",
					new ModelConvertor_Common("fdDateOfBirth")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdMaritalStatus.fdId", "fdMaritalStatusId");
			toFormPropertyMap.put("fdMaritalStatus.fdName",
					"fdMaritalStatusName");
			toFormPropertyMap.put("fdNation.fdId", "fdNationId");
			toFormPropertyMap.put("fdNation.fdName", "fdNationName");
			toFormPropertyMap.put("fdPoliticalLandscape.fdId",
					"fdPoliticalLandscapeId");
			toFormPropertyMap.put("fdPoliticalLandscape.fdName",
					"fdPoliticalLandscapeName");
			toFormPropertyMap.put("fdHealth.fdId", "fdHealthId");
			toFormPropertyMap.put("fdHealth.fdName", "fdHealthName");
			toFormPropertyMap.put("fdHighestDegree.fdId", "fdHighestDegreeId");
			toFormPropertyMap.put("fdHighestDegree.fdName",
					"fdHighestDegreeName");
			toFormPropertyMap.put("fdHighestEducation.fdId",
					"fdHighestEducationId");
			toFormPropertyMap.put("fdHighestEducation.fdName",
					"fdHighestEducationName");
			toFormPropertyMap.put("fdWorkTime",
					new ModelConvertor_Common("fdWorkTime")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdDateOfGroup",
					new ModelConvertor_Common("fdDateOfGroup")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdDateOfParty",
					new ModelConvertor_Common("fdDateOfParty")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdHistory",
					new ModelConvertor_ModelListToFormList("fdHistory"));
			toFormPropertyMap.put("fdEducations",
					new ModelConvertor_ModelListToFormList(
							"fdEducations"));
			toFormPropertyMap.put("fdCertificate",
					new ModelConvertor_ModelListToFormList(
							"fdCertificate"));
			toFormPropertyMap.put("fdRewardsPunishments",
					new ModelConvertor_ModelListToFormList(
							"fdRewardsPunishments"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

	public Boolean getFdHasWrite() {
		return fdHasWrite;
	}

	public void setFdHasWrite(Boolean fdHasWrite) {
		this.fdHasWrite = fdHasWrite;
	}

	public String getFdRecruitEntryId() {
		return this.fdRecruitEntryId;
	}

	public void setFdRecruitEntryId(String fdRecruitEntryId) {
		this.fdRecruitEntryId = fdRecruitEntryId;
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
    public Date getFdEntryDate() {
        return this.fdEntryDate;
    }

    /**
     * 入职日期
     */
    public void setFdEntryDate(Date fdEntryDate) {
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
    public SysOrgElement getFdEntryDept() {
        return this.fdEntryDept;
    }

    /**
     * 入职部门
     */
    public void setFdEntryDept(SysOrgElement fdEntryDept) {
        this.fdEntryDept = fdEntryDept;
    }

	/**
	 * 职务
	 */
	public SysOrganizationStaffingLevel getFdEntryLevel() {
		return fdEntryLevel;
	}

	public void setFdEntryLevel(SysOrganizationStaffingLevel fdEntryLevel) {
		this.fdEntryLevel = fdEntryLevel;
	}

	/**
	 * 导师
	 */
    public SysOrgPerson getFdTeacher() {
        return this.fdTeacher;
    }

    /**
     * 导师
     */
    public void setFdTeacher(SysOrgPerson fdTeacher) {
        this.fdTeacher = fdTeacher;
    }

    /**
     * 直接上级
     */
    public SysOrgPerson getFdEntryLeader() {
        return this.fdEntryLeader;
    }

    /**
     * 直接上级
     */
    public void setFdEntryLeader(SysOrgPerson fdEntryLeader) {
        this.fdEntryLeader = fdEntryLeader;
    }

	public String getFdEntryStatus() {
		return this.fdEntryStatus;
	}

	public void setFdEntryStatus(String fdEntryStatus) {
		this.fdEntryStatus = fdEntryStatus;
	}

	public Date getFdEntryPeriodDate() {
		return fdEntryPeriodDate;
	}

	public void setFdEntryPeriodDate(Date fdEntryPeriodDate) {
		this.fdEntryPeriodDate = fdEntryPeriodDate;
	}

	public Double getFdEntryPeriodSalary() {
		return fdEntryPeriodSalary;
	}

	public void setFdEntryPeriodSalary(Double fdEntryPeriodSalary) {
		this.fdEntryPeriodSalary = fdEntryPeriodSalary;
	}

	public Double getFdEntryPositiveSalary() {
		return fdEntryPositiveSalary;
	}

	public void setFdEntryPositiveSalary(Double fdEntryPositiveSalary) {
		this.fdEntryPositiveSalary = fdEntryPositiveSalary;
	}

	public HrStaffPersonInfoSettingNew getFdEntryStaffType() {
		return fdEntryStaffType;
	}

	public void
			setFdEntryStaffType(HrStaffPersonInfoSettingNew fdEntryStaffType) {
		this.fdEntryStaffType = fdEntryStaffType;
	}

	public String getFdEntryWorkAddress() {
		return fdEntryWorkAddress;
	}

	public void setFdEntryWorkAddress(String fdEntryWorkAddress) {
		this.fdEntryWorkAddress = fdEntryWorkAddress;
	}

	public String getFdEntryWorkDuty() {
		return (String) readLazyField("fdEntryWorkDuty", this.fdEntryWorkDuty);
	}

	public void setFdEntryWorkDuty(String fdEntryWorkDuty) {
		this.fdEntryWorkDuty = (String) writeLazyField("fdEntryWorkDuty",
				this.fdEntryWorkDuty, fdEntryWorkDuty);
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

	public Date getFdDateOfBirth() {
		return fdDateOfBirth;
	}

	public void setFdDateOfBirth(Date fdDateOfBirth) {
		this.fdDateOfBirth = fdDateOfBirth;
	}

	public String getFdNativePlace() {
		return fdNativePlace;
	}

	public void setFdNativePlace(String fdNativePlace) {
		this.fdNativePlace = fdNativePlace;
	}

	public HrStaffPersonInfoSettingNew getFdMaritalStatus() {
		return fdMaritalStatus;
	}

	public void
			setFdMaritalStatus(HrStaffPersonInfoSettingNew fdMaritalStatus) {
		this.fdMaritalStatus = fdMaritalStatus;
	}

	public HrStaffPersonInfoSettingNew getFdNation() {
		return fdNation;
	}

	public void setFdNation(HrStaffPersonInfoSettingNew fdNation) {
		this.fdNation = fdNation;
	}

	public HrStaffPersonInfoSettingNew getFdPoliticalLandscape() {
		return fdPoliticalLandscape;
	}

	public void setFdPoliticalLandscape(
			HrStaffPersonInfoSettingNew fdPoliticalLandscape) {
		this.fdPoliticalLandscape = fdPoliticalLandscape;
	}

	public HrStaffPersonInfoSettingNew getFdHealth() {
		return fdHealth;
	}

	public void setFdHealth(HrStaffPersonInfoSettingNew fdHealth) {
		this.fdHealth = fdHealth;
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

	public HrStaffPersonInfoSettingNew getFdHighestDegree() {
		return fdHighestDegree;
	}

	public void
			setFdHighestDegree(HrStaffPersonInfoSettingNew fdHighestDegree) {
		this.fdHighestDegree = fdHighestDegree;
	}

	public HrStaffPersonInfoSettingNew getFdHighestEducation() {
		return fdHighestEducation;
	}

	public void
			setFdHighestEducation(
					HrStaffPersonInfoSettingNew fdHighestEducation) {
		this.fdHighestEducation = fdHighestEducation;
	}

	public String getFdProfession() {
		return fdProfession;
	}

	public void setFdProfession(String fdProfession) {
		this.fdProfession = fdProfession;
	}

	public Date getFdWorkTime() {
		return fdWorkTime;
	}

	public void setFdWorkTime(Date fdWorkTime) {
		this.fdWorkTime = fdWorkTime;
	}

	public Date getFdDateOfGroup() {
		return fdDateOfGroup;
	}

	public void setFdDateOfGroup(Date fdDateOfGroup) {
		this.fdDateOfGroup = fdDateOfGroup;
	}

	public Date getFdDateOfParty() {
		return fdDateOfParty;
	}

	public void setFdDateOfParty(Date fdDateOfParty) {
		this.fdDateOfParty = fdDateOfParty;
	}

	public Integer getFdStature() {
		return fdStature;
	}

	public void setFdStature(Integer fdStature) {
		this.fdStature = fdStature;
	}

	public Integer getFdWeight() {
		return fdWeight;
	}

	public void setFdWeight(Integer fdWeight) {
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

	public SysOrgPerson getFdOrgPerson() {
		return fdOrgPerson;
	}

	public void setFdOrgPerson(SysOrgPerson fdOrgPerson) {
		this.fdOrgPerson = fdOrgPerson;
	}

	/**
	 * 是否引入待确认员工档案
	 */
	public Boolean getFdIsRecruit() {
		return fdIsRecruit;
	}

	/**
	 * 是否引入待确认员工档案
	 */
	public void setFdIsRecruit(Boolean fdIsRecruit) {
		this.fdIsRecruit = fdIsRecruit;
	}

}
