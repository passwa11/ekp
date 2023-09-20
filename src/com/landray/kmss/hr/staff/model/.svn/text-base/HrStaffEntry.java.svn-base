package com.landray.kmss.hr.staff.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelConvertor_ModelToForm;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseCreateInfoModel;
import com.landray.kmss.hr.staff.forms.HrStaffEntryForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.util.DateUtil;

public class HrStaffEntry extends BaseCreateInfoModel {

	@Override
	public Class<HrStaffEntryForm> getFormClass() {
		return HrStaffEntryForm.class;
	}

	// *************以下是入职员工基本信息字段*************** //
	// 姓名
	private String fdName;
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
	/*
	 * 是否可以修改
	 */
	protected Boolean fdIsAllowModify;

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

	// *************以下是入职员工个人经历信息*************** //
	// 工作经历
	private List<HrStaffHistory> fdHistory;
	// 教育记录
	private List<HrStaffEduExp> fdEducations;
	// 资格证书
	private List<HrStaffCertifi> fdCertificate;
	// 奖惩信息
	private List<HrStaffRewPuni> fdRewardsPunishments;
	// 家庭信息
	private List<HrStaffPersonFamily> fdStaffPersonFamilies;
	// 薪酬福利
	private HrStaffEmolumentWelfare hrStaffEmolumentWelfare;

	// *************以上是入职员工个人经历信息*************** //

	// *************以下是增加的字段*************** //
	// 数据来源
	private String fdDataFrom;
	// 最后更新时间
	private Date fdLastModifiedTime;
	// 拟入职日期
	private Date fdPlanEntryTime;
	// 拟入职部门
	private SysOrgElement fdPlanEntryDept;
	// 状态
	private String fdStatus;
	// 更新人
	private SysOrgPerson fdAlteror;
	// 确认时间
	private Date fdCheckDate;
	// 确认人
	private SysOrgElement fdChecker;
	// *************以下是增加的字段*************** //

	// 是否开通账号
	private Boolean fdIsOpenOrg;
	// 是否关联组织架构账号
	private Boolean fdIsLinkOrg;
	// 组织架构人员
	private SysOrgPerson fdOrgPerson;
	// 工号
	private String fdStaffNo;

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
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
					new ModelConvertor_ModelListToFormList("fdHistory_Form"));
			toFormPropertyMap.put("fdEducations",
					new ModelConvertor_ModelListToFormList(
							"fdEducations_Form"));
			toFormPropertyMap.put("fdCertificate",
					new ModelConvertor_ModelListToFormList(
							"fdCertificate_Form"));
			toFormPropertyMap.put("fdRewardsPunishments",
					new ModelConvertor_ModelListToFormList(
							"fdRewardsPunishments_Form"));
			toFormPropertyMap.put("fdStaffPersonFamilies",
					new ModelConvertor_ModelListToFormList(
							"fdfamily_Form"));
			toFormPropertyMap.put("fdLastModifiedTime",
					new ModelConvertor_Common("fdLastModifiedTime")
							.setDateTimeType(DateUtil.TYPE_DATETIME));
			toFormPropertyMap.put("fdPlanEntryTime",
					new ModelConvertor_Common("fdPlanEntryTime")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdCheckDate",
					new ModelConvertor_Common("fdCheckDate")
							.setDateTimeType(DateUtil.TYPE_DATETIME));
			toFormPropertyMap.put("fdPlanEntryDept.fdId", "fdPlanEntryDeptId");
			toFormPropertyMap.put("fdPlanEntryDept.fdName",
					"fdPlanEntryDeptName");
			toFormPropertyMap.put("fdAlteror.fdId", "fdAlterorId");
			toFormPropertyMap.put("fdAlteror.fdName", "fdAlterorName");
			toFormPropertyMap.put("fdChecker.fdId", "fdCheckerId");
			toFormPropertyMap.put("fdChecker.fdName", "fdCheckerName");
			toFormPropertyMap.put("fdOrgPerson.fdId", "fdOrgPersonId");
			toFormPropertyMap.put("fdOrgPerson.fdName", "fdOrgPersonName");
			toFormPropertyMap.put("fdOrgPosts",
					new ModelConvertor_ModelListToString("fdOrgPostIds:fdOrgPostNames", "fdId:fdName"));
			// 创建人
			toFormPropertyMap.put("docCreator.fdId", new ModelConvertor_Common(
					"docCreatorId"));
			toFormPropertyMap.put("docCreator.fdName",
					new ModelConvertor_Common("docCreatorName"));
			// 创建时间
			toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common(
					"docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
			toFormPropertyMap.put("hrStaffEmolumentWelfare",
					new ModelConvertor_ModelToForm(
							"hrStaffEmolumentWelfareForm"));
		}
		return toFormPropertyMap;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
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

	public HrStaffEmolumentWelfare getHrStaffEmolumentWelfare() {
		return hrStaffEmolumentWelfare;
	}

	public void setHrStaffEmolumentWelfare(
			HrStaffEmolumentWelfare hrStaffEmolumentWelfare) {
		this.hrStaffEmolumentWelfare = hrStaffEmolumentWelfare;
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

	public List<HrStaffHistory> getFdHistory() {
		return fdHistory;
	}

	public void setFdHistory(List<HrStaffHistory> fdHistory) {
		this.fdHistory = fdHistory;
	}

	public List<HrStaffEduExp> getFdEducations() {
		return fdEducations;
	}

	public void setFdEducations(List<HrStaffEduExp> fdEducations) {
		this.fdEducations = fdEducations;
	}

	public List<HrStaffCertifi> getFdCertificate() {
		return fdCertificate;
	}

	public void setFdCertificate(List<HrStaffCertifi> fdCertificate) {
		this.fdCertificate = fdCertificate;
	}

	public List<HrStaffRewPuni> getFdRewardsPunishments() {
		return fdRewardsPunishments;
	}

	public void
			setFdRewardsPunishments(
					List<HrStaffRewPuni> fdRewardsPunishments) {
		this.fdRewardsPunishments = fdRewardsPunishments;
	}

	public List<HrStaffPersonFamily> getFdStaffPersonFamilies() {
		return fdStaffPersonFamilies;
	}

	public void setFdStaffPersonFamilies(
			List<HrStaffPersonFamily> fdStaffPersonFamilies) {
		this.fdStaffPersonFamilies = fdStaffPersonFamilies;
	}

	public String getFdDataFrom() {
		return fdDataFrom;
	}

	public void setFdDataFrom(String fdDataFrom) {
		this.fdDataFrom = fdDataFrom;
	}

	public Date getFdLastModifiedTime() {
		return fdLastModifiedTime;
	}

	public void setFdLastModifiedTime(Date fdLastModifiedTime) {
		this.fdLastModifiedTime = fdLastModifiedTime;
	}

	public Date getFdPlanEntryTime() {
		return fdPlanEntryTime;
	}

	public void setFdPlanEntryTime(Date fdPlanEntryTime) {
		this.fdPlanEntryTime = fdPlanEntryTime;
	}

	public SysOrgElement getFdPlanEntryDept() {
		return fdPlanEntryDept;
	}

	public void setFdPlanEntryDept(SysOrgElement fdPlanEntryDept) {
		this.fdPlanEntryDept = fdPlanEntryDept;
	}

	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	public SysOrgPerson getFdAlteror() {
		return fdAlteror;
	}

	public void setFdAlteror(SysOrgPerson fdAlteror) {
		this.fdAlteror = fdAlteror;
	}

	public Date getFdCheckDate() {
		return fdCheckDate;
	}

	public void setFdCheckDate(Date fdCheckDate) {
		this.fdCheckDate = fdCheckDate;
	}

	public SysOrgElement getFdChecker() {
		return fdChecker;
	}

	public void setFdChecker(SysOrgElement fdChecker) {
		this.fdChecker = fdChecker;
	}

	public Boolean getFdIsOpenOrg() {
		return fdIsOpenOrg;
	}

	public void setFdIsOpenOrg(Boolean fdIsOpenOrg) {
		this.fdIsOpenOrg = fdIsOpenOrg;
	}

	public Boolean getFdIsLinkOrg() {
		return fdIsLinkOrg;
	}

	public void setFdIsLinkOrg(Boolean fdIsLinkOrg) {
		this.fdIsLinkOrg = fdIsLinkOrg;
	}

	public SysOrgPerson getFdOrgPerson() {
		return fdOrgPerson;
	}

	public void setFdOrgPerson(SysOrgPerson fdOrgPerson) {
		this.fdOrgPerson = fdOrgPerson;
	}

	public String getFdStaffNo() {
		return fdStaffNo;
	}

	public void setFdStaffNo(String fdStaffNo) {
		this.fdStaffNo = fdStaffNo;
	}

	//所属岗位
	private List<SysOrgPost> fdOrgPosts;

	public List<SysOrgPost> getFdOrgPosts() {
		return fdOrgPosts;
	}

	public void setFdOrgPosts(List<SysOrgPost> fdOrgPosts) {
		this.fdOrgPosts = fdOrgPosts;
	}

	//是否扫码录入员工信息
	private Boolean fdQRStatus;

	//扫码录入时间
	private Date fdQRTime;

	public Boolean getFdQRStatus() {
		return fdQRStatus;
	}

	public void setFdQRStatus(Boolean fdQRStatus) {
		this.fdQRStatus = fdQRStatus;
	}

	public Date getFdQRTime() {
		return fdQRTime;
	}

	public void setFdQRTime(Date fdQRTime) {
		this.fdQRTime = fdQRTime;
	}

	// 放弃入职原因
	private String fdAbandonReason;

	// 放弃入职备注
	private String fdAbandonRemark;

	public String getFdAbandonReason() {
		return fdAbandonReason;
	}

	public void setFdAbandonReason(String fdAbandonReason) {
		this.fdAbandonReason = fdAbandonReason;
	}

	public String getFdAbandonRemark() {
		return fdAbandonRemark;
	}

	public void setFdAbandonRemark(String fdAbandonRemark) {
		this.fdAbandonRemark = fdAbandonRemark;
	}

	// offer协定薪资
	private String fdCorrectionSalary;

	/**
	 * 转正薪资
	 */
	public String getFdCorrectionSalary() {
		return this.fdCorrectionSalary;
	}

	/**
	 * 转正薪资
	 */
	public void setFdCorrectionSalary(String fdCorrectionSalary) {
		this.fdCorrectionSalary = fdCorrectionSalary;
	}
	/**
	 * @return 返回 是否可以修改
	 */
	public Boolean getFdIsAllowModify() {
		return fdIsAllowModify;
	}

	/**
	 * @param fdIsAllowModify
	 *            要设置的 是否可以修改
	 */
	public void setFdIsAllowModify(Boolean fdIsAllowModify) {
		this.fdIsAllowModify = fdIsAllowModify;
	}
}
