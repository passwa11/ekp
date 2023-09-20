package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_FormToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.staff.model.HrStaffEntry;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoSettingNew;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

public class HrStaffEntryForm extends ExtendForm {

	@Override
	public Class<HrStaffEntry> getModelClass() {
		return HrStaffEntry.class;
	}

	// *************以下是入职员工基本信息字段*************** //
	/*
	 * 是否可以修改
	 */
	private String fdIsAllowModify;
	// 姓名
	private String fdName;
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
			HrStaffHistoryForm.class);
	private String fdHistory_Flag = "0";
	// 教育记录
	private AutoArrayList fdEducations_Form = new AutoArrayList(
			HrStaffEduExpForm.class);
	private String fdEducations_Flag = "0";
	// 资格证书
	private AutoArrayList fdCertificate_Form = new AutoArrayList(
			HrStaffCertifiForm.class);
	private String fdCertificate_Flag = "0";
	// 奖惩信息
	private AutoArrayList fdRewardsPunishments_Form = new AutoArrayList(
			HrStaffRewPuniForm.class);
	private String fdRewardsPunishments_Flag = "0";
	// 家庭信息
	private AutoArrayList fdfamily_Form = new AutoArrayList(
			HrStaffPersonFamilyForm.class);
	private String fdfamily_Flag = "0";
	
	// *************以上是入职员工个人经历信息*************** //

	// *************以下是增加的字段*************** //
	// 数据来源
	private String fdDataFrom;
	// 最后更新时间
	private String fdLastModifiedTime;
	// 拟入职日期
	private String fdPlanEntryTime;
	// 拟入职部门
	private String fdPlanEntryDeptId;
	private String fdPlanEntryDeptName;
	// 状态
	private String fdStatus;
	// 更新人
	private String fdAlterorId;
	private String fdAlterorName;
	// 确认时间
	private String fdCheckDate;
	// 确认人
	private String fdCheckerId;
	private String fdCheckerName;
	// *************以下是增加的字段*************** //

	// 是否开通账号
	private String fdIsOpenOrg;
	// 是否关联组织架构账号
	private String fdIsLinkOrg;
	// 组织架构人员id
	private String fdOrgPersonId;
	// 组织架构人员name
	private String fdOrgPersonName;
	// 工号
	private String fdStaffNo;

	private HrStaffEmolumentWelfareForm hrStaffEmolumentWelfareForm;

	public HrStaffEmolumentWelfareForm getHrStaffEmolumentWelfareForm() {
		return hrStaffEmolumentWelfareForm;
	}

	public void setHrStaffEmolumentWelfareForm(
			HrStaffEmolumentWelfareForm hrStaffEmolumentWelfareForm) {
		this.hrStaffEmolumentWelfareForm = hrStaffEmolumentWelfareForm;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdIsAllowModify = "false";
		fdName = null;
		fdNameUsedBefore = null;
		// 性别
		fdSex = null;
		// 出生日期
		fdDateOfBirth = null;
		// 籍贯
		fdNativePlace = null;
		// 婚姻状况
		fdMaritalStatusId = null;
		fdMaritalStatusName = null;
		// 民族
		fdNationId = null;
		fdNationName = null;
		// 政治面貌
		fdPoliticalLandscapeId = null;
		fdPoliticalLandscapeName = null;
		// 健康状况
		fdHealthId = null;
		fdHealthName = null;
		// 现居住地
		fdLivingPlace = null;
		// 身份证号码
		fdIdCard = null;
		// 学位
		fdHighestDegreeId = null;
		fdHighestDegreeName = null;
		// 学历
		fdHighestEducationId = null;
		fdHighestEducationName = null;
		// 专业
		fdProfession = null;
		// 参加工作时间
		fdWorkTime = null;
		// 入团日期
		fdDateOfGroup = null;
		// 入党日期
		fdDateOfParty = null;
		// 身高（厘米）
		fdStature = null;
		// 体重（千克）
		fdWeight = null;
		// 出生地
		fdHomeplace = null;
		// 户口性质
		fdAccountProperties = null;
		// 户口所在地
		fdRegisteredResidence = null;
		// 户口所在派出所
		fdResidencePoliceStation = null;
		// ************以上是入职员工基本信息字段*************** //

		// ************以下是入职员工联系信息字段*************** //
		// 邮箱地址
		fdEmail = null;
		// 手机
		fdMobileNo = null;
		// 紧急联系人
		fdEmergencyContact = null;
		// 紧急联系人电话
		fdEmergencyContactPhone = null;
		// 其他联系方式
		fdOtherContact = null;
		// 数据来源
		fdDataFrom = null;
		// 最后更新时间
		fdLastModifiedTime = null;
		// 拟入职日期
		fdPlanEntryTime = null;
		// 拟入职部门
		fdPlanEntryDeptId = null;
		fdPlanEntryDeptName = null;
		// 状态
		fdStatus = null;
		// 更新人
		fdAlterorId = null;
		fdAlterorName = null;
		// 确认时间
		fdCheckDate = null;
		// 确认人
		fdCheckerId = null;
		fdCheckerName = null;
		// *************以下是增加的字段*************** //

		// 是否开通账号
		fdIsOpenOrg = null;
		// 是否关联组织架构账号
		fdIsLinkOrg = null;
		// 组织架构人员id
		fdOrgPersonId = null;
		// 组织架构人员name
		fdOrgPersonName = null;
		// 工号
		fdStaffNo = null;
		hrStaffEmolumentWelfareForm = new HrStaffEmolumentWelfareForm();
		super.reset(mapping, request);
	}

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdHistory_Form",
					new FormConvertor_FormListToModelList("fdHistory",
							"docMain", "fdHistory_Flag"));
			toModelPropertyMap.put("fdEducations_Form",
					new FormConvertor_FormListToModelList("fdEducations",
							"docMain", "fdEducations_Flag"));
			toModelPropertyMap.put("fdCertificate_Form",
					new FormConvertor_FormListToModelList("fdCertificate",
							"docMain", "fdCertificate_Flag"));
			toModelPropertyMap.put("fdRewardsPunishments_Form",
					new FormConvertor_FormListToModelList(
							"fdRewardsPunishments", "docMain",
							"fdRewardsPunishments_Flag"));
			// 家庭信息
			toModelPropertyMap.put("fdfamily_Form",
					new FormConvertor_FormListToModelList(
							"fdStaffPersonFamilies", "docMain",
							"fdfamily_Flag"));
						toModelPropertyMap.put("fdLastModifiedTime",
					new FormConvertor_Common("fdLastModifiedTime")
							.setDateTimeType(DateUtil.TYPE_DATETIME));
			toModelPropertyMap.put("fdCheckDate",
					new FormConvertor_Common("fdCheckDate")
							.setDateTimeType(DateUtil.TYPE_DATETIME));
			toModelPropertyMap.put("fdQRTime",
					new FormConvertor_Common("fdQRTime").setDateTimeType(DateUtil.TYPE_DATETIME));
			toModelPropertyMap.put("fdPlanEntryDeptId",
					new FormConvertor_IDToModel("fdPlanEntryDept",
							SysOrgElement.class));
			toModelPropertyMap.put("fdAlterorId", new FormConvertor_IDToModel(
					"fdAlteror", SysOrgElement.class));
			toModelPropertyMap.put("fdCheckerId", new FormConvertor_IDToModel(
					"fdChecker", SysOrgElement.class));
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
			toModelPropertyMap.put("fdOrgPersonId", new FormConvertor_IDToModel(
					"fdOrgPerson", SysOrgPerson.class));
			toModelPropertyMap.put("fdOrgPostIds", new FormConvertor_IDsToModelList("fdOrgPosts", SysOrgPost.class));
			toModelPropertyMap.put("hrStaffEmolumentWelfareForm",
					new FormConvertor_FormToModel(
							"hrStaffEmolumentWelfare"));
		}
		return toModelPropertyMap;
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

	public String getFdHistory_Flag() {
		return fdHistory_Flag;
	}

	public void setFdHistory_Flag(String fdHistory_Flag) {
		this.fdHistory_Flag = fdHistory_Flag;
	}

	public AutoArrayList getFdEducations_Form() {
		return fdEducations_Form;
	}

	public void setFdEducations_Form(AutoArrayList fdEducations_Form) {
		this.fdEducations_Form = fdEducations_Form;
	}

	public String getFdEducations_Flag() {
		return fdEducations_Flag;
	}

	public void setFdEducations_Flag(String fdEducations_Flag) {
		this.fdEducations_Flag = fdEducations_Flag;
	}

	public AutoArrayList getFdCertificate_Form() {
		return fdCertificate_Form;
	}

	public void setFdCertificate_Form(AutoArrayList fdCertificate_Form) {
		this.fdCertificate_Form = fdCertificate_Form;
	}

	public String getFdCertificate_Flag() {
		return fdCertificate_Flag;
	}

	public void setFdCertificate_Flag(String fdCertificate_Flag) {
		this.fdCertificate_Flag = fdCertificate_Flag;
	}

	// 奖惩信息
	public AutoArrayList getFdRewardsPunishments_Form() {
		return fdRewardsPunishments_Form;
	}

	public void
			setFdRewardsPunishments_Form(
					AutoArrayList fdRewardsPunishments_Form) {
		this.fdRewardsPunishments_Form = fdRewardsPunishments_Form;
	}

	public String getFdRewardsPunishments_Flag() {
		return fdRewardsPunishments_Flag;
	}

	public void setFdRewardsPunishments_Flag(String fdRewardsPunishments_Flag) {
		this.fdRewardsPunishments_Flag = fdRewardsPunishments_Flag;
	}

	// 家庭信息
	public AutoArrayList getFdfamily_Form() {
		return fdfamily_Form;
	}

	public void setFdfamily_Form(AutoArrayList fdfamily_Form) {
		this.fdfamily_Form = fdfamily_Form;
	}

	public String getFdfamily_Flag() {
		return fdfamily_Flag;
	}

	public void setFdfamily_Flag(String fdfamily_Flag) {
		this.fdfamily_Flag = fdfamily_Flag;
	}

	public String getFdDataFrom() {
		return fdDataFrom;
	}

	public void setFdDataFrom(String fdDataFrom) {
		this.fdDataFrom = fdDataFrom;
	}

	public String getFdLastModifiedTime() {
		return fdLastModifiedTime;
	}

	public void setFdLastModifiedTime(String fdLastModifiedTime) {
		this.fdLastModifiedTime = fdLastModifiedTime;
	}

	public String getFdPlanEntryTime() {
		return fdPlanEntryTime;
	}

	public void setFdPlanEntryTime(String fdPlanEntryTime) {
		this.fdPlanEntryTime = fdPlanEntryTime;
	}

	public String getFdPlanEntryDeptId() {
		return fdPlanEntryDeptId;
	}

	public void setFdPlanEntryDeptId(String fdPlanEntryDeptId) {
		this.fdPlanEntryDeptId = fdPlanEntryDeptId;
	}

	public String getFdPlanEntryDeptName() {
		return fdPlanEntryDeptName;
	}

	public void setFdPlanEntryDeptName(String fdPlanEntryDeptName) {
		this.fdPlanEntryDeptName = fdPlanEntryDeptName;
	}

	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	public String getFdAlterorId() {
		return fdAlterorId;
	}

	public void setFdAlterorId(String fdAlterorId) {
		this.fdAlterorId = fdAlterorId;
	}

	public String getFdAlterorName() {
		return fdAlterorName;
	}

	public void setFdAlterorName(String fdAlterorName) {
		this.fdAlterorName = fdAlterorName;
	}

	public String getFdCheckDate() {
		return fdCheckDate;
	}

	public void setFdCheckDate(String fdCheckDate) {
		this.fdCheckDate = fdCheckDate;
	}

	public String getFdCheckerId() {
		return fdCheckerId;
	}

	public void setFdCheckerId(String fdCheckerId) {
		this.fdCheckerId = fdCheckerId;
	}

	public String getFdCheckerName() {
		return fdCheckerName;
	}

	public void setFdCheckerName(String fdCheckerName) {
		this.fdCheckerName = fdCheckerName;
	}

	public String getFdIsOpenOrg() {
		return fdIsOpenOrg;
	}

	public void setFdIsOpenOrg(String fdIsOpenOrg) {
		this.fdIsOpenOrg = fdIsOpenOrg;
	}

	public String getFdIsLinkOrg() {
		return fdIsLinkOrg;
	}

	public void setFdIsLinkOrg(String fdIsLinkOrg) {
		this.fdIsLinkOrg = fdIsLinkOrg;
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

	public String getFdStaffNo() {
		return fdStaffNo;
	}

	public void setFdStaffNo(String fdStaffNo) {
		this.fdStaffNo = fdStaffNo;
	}

	//所属岗位
	private String fdOrgPostIds;
	private String fdOrgPostNames;

	public String getFdOrgPostIds() {
		return fdOrgPostIds;
	}

	public String getFdOrgPostNames() {
		return fdOrgPostNames;
	}

	public void setFdOrgPostIds(String fdOrgPostIds) {
		this.fdOrgPostIds = fdOrgPostIds;
	}

	public void setFdOrgPostNames(String fdOrgPostNames) {
		this.fdOrgPostNames = fdOrgPostNames;
	}

	//是否扫码录入员工信息
	private Boolean fdQRStatus;

	public Boolean getFdQRStatus() {
		return fdQRStatus;
	}

	public void setFdQRStatus(Boolean fdQRStatus) {
		this.fdQRStatus = fdQRStatus;
	}

	//扫码录入时间
	private String fdQRTime;

	public String getFdQRTime() {
		return fdQRTime;
	}

	public void setFdQRTime(String fdQRTime) {
		this.fdQRTime = fdQRTime;
	}

	/*
	 * 申请人ID
	 */
	private String docCreatorId = null;

	private String docCreatorName = null;

	/*
	 * 创建时间
	 */
	private String docCreateTime = null;

	/**
	 * @return 返回 申请人ID
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}

	public String getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	/**
	 * @param docCreatorId
	 *            要设置的 申请人ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * @return 返回 创建时间
	 */
	public String getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            要设置的 创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	// 入职日期
	private String fdEntryTime;

	public String getFdEntryTime() {
		return fdEntryTime;
	}

	public void setFdEntryTime(String fdEntryTime) {
		this.fdEntryTime = fdEntryTime;
	}

	// 员工状态
	private String fdPersonStatus;

	public String getFdPersonStatus() {
		return fdPersonStatus;
	}

	public void setFdPersonStatus(String fdPersonStatus) {
		this.fdPersonStatus = fdPersonStatus;
	}

	// 工作性质
	private String fdNatureWork;

	public String getFdNatureWork() {
		return fdNatureWork;
	}

	public void setFdNatureWork(String fdNatureWork) {
		this.fdNatureWork = fdNatureWork;
	}

	/*
	 * 登录名
	 */
	private String fdLoginName;

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

	// 上传的文件
	private FormFile file;

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}
	public String getFdIsAllowModify() {
		return fdIsAllowModify;
	}

	public void setFdIsAllowModify(String fdIsAllowModify) {
		this.fdIsAllowModify = fdIsAllowModify;
	}

}
