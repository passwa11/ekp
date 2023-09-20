package com.landray.kmss.hr.staff.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.Lists;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustDetail;
import com.landray.kmss.hr.organization.interfaces.IHrOrgPerson;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationRank;
import com.landray.kmss.hr.staff.forms.HrStaffPersonInfoForm;
import com.landray.kmss.hr.staff.util.HrStaffDateUtil;
import com.landray.kmss.hr.staff.util.HrStaffPersonUtil;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzModel;
import com.landray.kmss.sys.tag.interfaces.ISysTagMainModel;
import com.landray.kmss.sys.tag.model.SysTagMain;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.elasticsearch.common.base.Joiner;

/**
 * 鍛樺伐淇℃伅
 * 
 * @author 娼樻案杈� 2016-12-26
 * 
 */

public class HrStaffPersonInfo extends HrOrganizationElement
		implements ISysNotifyModel,
		ISysTagMainModel, IAttachment, ISysQuartzModel, IHrOrgPerson {
	private static final long serialVersionUID = 1L;

	public HrStaffPersonInfo() {
		super();
		setFdOrgType(new Integer(HR_TYPE_PERSON));
	}
	private ISysOrgElementService sysOrgElementService;

	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean(
					"sysOrgElementService");
		}
		return sysOrgElementService;
	}
	private String fdContType;
	private Date fdBeginDate;
	// 结束日期
	private Date fdEndDate;
	
	private String fdContractUnit;
	public String getFdContType() {
		return fdContType;
	}

	public void setFdContType(String fdContType) {
		this.fdContType = fdContType;
	}

	public Date getFdBeginDate() {
		return fdBeginDate;
	}

	public void setFdBeginDate(Date fdBeginDate) {
		this.fdBeginDate = fdBeginDate;
	}
	private String fdContractPeriod;
	
	public String getFdContractPeriod() {
		if(fdContractYear != null)
			return ""+fdContractYear+"年"+fdContractMonth+"月";
		else 
			return "";
	}

	public void setFdContractPeriod(String fdContractPeriod) {
		this.fdContractPeriod = fdContractPeriod;
	}

	public Date getFdEndDate() {
		return fdEndDate;
	}

	public void setFdEndDate(Date fdEndDate) {
		this.fdEndDate = fdEndDate;
	}
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

	public String getFdContractUnit() {
		return fdContractUnit;
	}

	public void setFdContractUnit(String fdContractUnit) {
		this.fdContractUnit = fdContractUnit;
	}

	@Override
	public Class getFormClass() {
		return HrStaffPersonInfoForm.class;
	}

	// 缁勭粐鏋舵瀯浜哄憳
	private SysOrgPerson fdOrgPerson;
	// 鏈烘瀯锛氳嫢寮曠敤缁勭粐鏋舵瀯锛屽垯鍙杅dOrgPerson鏁版嵁
	private SysOrgElement fdOrgParentOrg;
	// 閮ㄩ棬锛氳嫢寮曠敤缁勭粐鏋舵瀯锛屽垯鍙杅dOrgPerson鏁版嵁
	private SysOrgElement fdOrgParent;
	// 瀹屾暣鐨勯儴闂ㄥ悕绉�
	private String fdOrgParentsName;
	// 绂昏亴浜哄瀹屾暣鐨勯儴闂ㄥ悕绉�
	private String fdLeavelOrgParentsName;
	// 鑱屽姟锛氳嫢寮曠敤缁勭粐鏋舵瀯锛屽垯鍙杅dOrgPerson鏁版嵁
	private SysOrganizationStaffingLevel fdStaffingLevel;
	private String fdStaffingLevel1;
	private String fdNatureOfWork;
	private String fdOAAccount;
	private String fdResignationType;
	private Date fdResignationDate;
	private String fdReasonForResignation;
	private String fdCostAttribution;
	private String fdProbationPeriod;
	private String fdHomeAddressAreaName;
	private String area;
	private String fdEnterpriseAge;
	private String fdStaffAge;
	public String getFdStaffAge() {
		return fdStaffAge;
	}

	public void setFdStaffAge(String fdStaffAge) {
		this.fdStaffAge = fdStaffAge;
	}

	public String getArea() {
		return area;
	}

	public String getFdEnterpriseAge() {
		return fdEnterpriseAge;
	}

	public void setFdEnterpriseAge(String fdEnterpriseAge) {
		this.fdEnterpriseAge = fdEnterpriseAge;
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

	private Date fdProposedEmploymentConfirmationDate;
	public Date getFdProposedEmploymentConfirmationDate() {
		return fdProposedEmploymentConfirmationDate;
	}

	public void setFdProposedEmploymentConfirmationDate(
			Date fdProposedEmploymentConfirmationDate) {
		this.fdProposedEmploymentConfirmationDate = fdProposedEmploymentConfirmationDate;
	}

	public String getFdProbationPeriod() {
		return fdProbationPeriod;
	}

	public void setFdProbationPeriod(String fdProbationPeriod) {
		this.fdProbationPeriod = fdProbationPeriod;
	}

	public String getFdCostAttribution() {
		return fdCostAttribution;
	}

	public void setFdCostAttribution(String fdCostAttribution) {
		this.fdCostAttribution = fdCostAttribution;
	}

	public String getFdReasonForResignation() {
		return fdReasonForResignation;
	}

	public void setFdReasonForResignation(String fdReasonForResignation) {
		this.fdReasonForResignation = fdReasonForResignation;
	}

	public Date getFdResignationDate() {
		return fdResignationDate;
	}

	public void setFdResignationDate(Date fdResignationDate) {
		this.fdResignationDate = fdResignationDate;
	}

	public String getFdResignationType() {
		return fdResignationType;
	}

	public void setFdResignationType(String fdResignationType) {
		this.fdResignationType = fdResignationType;
	}

	public String getFdOAAccount() {
		return fdOAAccount;
	}

	public void setFdOAAccount(String fdOAAccount) {
		this.fdOAAccount = fdOAAccount;
	}

	public String getFdNatureOfWork() {
		return fdNatureOfWork;
	}

	public void setFdNatureOfWork(String fdNatureOfWork) {
		this.fdNatureOfWork = fdNatureOfWork;
	}

	public String getFdStaffingLevel1() {
		return fdStaffingLevel1;
	}

	public void setFdStaffingLevel1(String fdStaffingLevel1) {
		this.fdStaffingLevel1 = fdStaffingLevel1;
	}

	private String fdOrgParentDeptName;
	// 濮撳悕锛氳嫢寮曠敤缁勭粐鏋舵瀯锛屽垯鍙杅dOrgPerson鏁版嵁
	private String fdName;
	// 鎬у埆锛氳嫢寮曠敤缁勭粐鏋舵瀯锛屽垯鍙杅dOrgPerson鏁版嵁
	private String fdSex;
	// 鍑虹敓鏃ユ湡
	private Date fdDateOfBirth;
	// 鐢熸棩(涓�骞翠腑鐨勭鍑犲ぉ)锛屼富瑕佺敤浜庣敓鏃ユ彁閱�
	private Integer fdBirthdayOfYear;
	// 骞撮緞
	private int fdAge;
	// 宸ュ彿
	private String fdStaffNo;
	// 韬唤璇佸彿鐮�
	private String fdIdCard;
	// 鍙傚姞宸ヤ綔鏃堕棿
	private Date fdWorkTime;
	// 杩炵画宸ラ緞
	private String fdUninterruptedWorkTime;
	private String fdUninterruptedEntryTime;
	private Integer fdUninterruptedWorkTimeValue;
	// 鍒版湰鍗曚綅鏃堕棿
	private Date fdTimeOfEnterprise;
	// 鏈紒涓氬伐榫�
	private String fdWorkingYears;
	private Integer fdWorkingYearsValue;
	//杩炵画宸ラ緞宸��
	private Integer fdWorkTimeDiff;
	private Integer fdEntryTimeDiff;
	private String fdOrgParentId;
    public String getFdOrgParentId() {
		return fdOrgParentId;
	}

	public void setFdOrgParentId(String fdOrgParentId) {
		this.fdOrgParentId = fdOrgParentId;
	}

	public Integer getFdEntryTimeDiff() {
		return fdEntryTimeDiff;
	}

	public void setFdEntryTimeDiff(Integer fdEntryTimeDiff) {
		this.fdEntryTimeDiff = fdEntryTimeDiff;
	}

	//鏈紒涓氬伐榫勫樊鍊�
	private Integer fdWorkingYearsDiff;
	// 璇曠敤鍒版湡鏃堕棿
	private Date fdTrialExpirationTime;
	// 鐢ㄥ伐鏈熼檺锛堝勾锛�
	private Integer fdEmploymentPeriod;
	// 浜哄憳绫诲埆锛氬悗鍙伴厤缃」
	private String fdStaffType;
	// 鏇剧敤鍚�
	private String fdNameUsedBefore;
	// 姘戞棌锛氬悗鍙伴厤缃」
	private String fdNation;
	// 鏀挎不闈㈣矊锛氬悗鍙伴厤缃」
	private String fdPoliticalLandscape;
	// 鍏ュ洟鏃ユ湡
	private Date fdDateOfGroup;
	// 鍏ュ厷鏃ユ湡
	private Date fdDateOfParty;
	// 鏈�楂樺鍘嗭細鍚庡彴閰嶇疆椤�
	private String fdHighestEducation;
	// 鏈�楂樺浣嶏細鍚庡彴閰嶇疆椤�
	private String fdHighestDegree;
	// 濠氬Щ鎯呭喌锛氬悗鍙伴厤缃」
	private String fdMaritalStatus;
	// 鍋ュ悍鎯呭喌锛氬悗鍙伴厤缃」
	private String fdHealth;
	// 韬珮锛堝帢绫筹級
	private Integer fdStature;
	// 浣撻噸锛堝崈鍏嬶級
	private Integer fdWeight;
	// 鐜板眳鍦�
	private String fdPostalAddress;
	private String fdPostalAddressCityId;
	private String fdPostalAddressAreaId;
	private String fdHomeAddressProvinceId;
	private String fdHomeAddressCityId;
	private String fdHomeAddressAreaId;
	private String fdPostalAddressProvinceName;
	private String fdPostalAddressCityName;
	private String fdPostalAddressAreaName;
	private String fdHomeAddressProvinceName;
	private String fdHomeAddressCityName;
	private String fdOfficeAreaCityId;
	private String fdOfficeAreaProvinceId;
	private String fdOfficeAreaAreaId;
	private String fdOfficeAreaCityName;

	public String getFdOfficeAreaCityId() {
		return fdOfficeAreaCityId;
	}

	public void setFdOfficeAreaCityId(String fdOfficeAreaCityId) {
		this.fdOfficeAreaCityId = fdOfficeAreaCityId;
	}

	public String getFdOfficeAreaProvinceId() {
		return fdOfficeAreaProvinceId;
	}

	public void setFdOfficeAreaProvinceId(String fdOfficeAreaProvinceId) {
		this.fdOfficeAreaProvinceId = fdOfficeAreaProvinceId;
	}

	

	

	public String getFdOfficeAreaCityName() {
		return fdOfficeAreaCityName;
	}

	public void setFdOfficeAreaCityName(String fdOfficeAreaCityName) {
		this.fdOfficeAreaCityName = fdOfficeAreaCityName;
	}

	public String getFdOfficeAreaProvinceName() {
		return fdOfficeAreaProvinceName;
	}

	public void setFdOfficeAreaProvinceName(String fdOfficeAreaProvinceName) {
		this.fdOfficeAreaProvinceName = fdOfficeAreaProvinceName;
	}

	

	

	private String fdOfficeAreaProvinceName;
	private String fdOfficeAreaAreaName;
	public String getFdHomeAddressProvinceName() {
		return fdHomeAddressProvinceName;
	}

	public String getFdOfficeAreaAreaId() {
		return fdOfficeAreaAreaId;
	}

	public void setFdOfficeAreaAreaId(String fdOfficeAreaAreaId) {
		this.fdOfficeAreaAreaId = fdOfficeAreaAreaId;
	}

	public String getFdOfficeAreaAreaName() {
		return fdOfficeAreaAreaName;
	}

	public void setFdOfficeAreaAreaName(String fdOfficeAreaAreaName) {
		this.fdOfficeAreaAreaName = fdOfficeAreaAreaName;
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

	public String getFdPostalAddressCityId() {
		return fdPostalAddressCityId;
	}

	public void setFdPostalAddressCityId(String fdPostalAddressCityId) {
		this.fdPostalAddressCityId = fdPostalAddressCityId;
	}

	private SysOrgElement fdFirstLevelDepartment;
	private SysOrgElement fdSecondLevelDepartment;
	private SysOrgElement fdThirdLevelDepartment;
	private String fdAffiliatedCompany;
	private String fdTimeCardNo;
	private String fdCategory;
	private String fdDirectSuperiorJobNumber;

	

	private String fdPrincipalIdentification;
	private String fdFixedShift;
	private String fdPlaceOfInsurancePayment;
	private Date fdDepartureTime;
	private String fdOfficeArea;
	private String fdOfficeLine;


	private String fdOfficeExtension;
	private String fdPrivateMailbox;
	private String fdRelationsOfEmergencyContactAndEmployee;
	private String fdEmergencyContactAddress;
	private String fdIsAttendance;
	private String fdOrgPostIds;
	private SysOrgElement fdOrgPost;
	
	public void setFdOrgPost(SysOrgElement fdOrgPost) {
		this.fdOrgPost = fdOrgPost;
	}

	public SysOrgElement getFdOrgPost() {
		return fdOrgPost;
	}

	public void setFdOrgPostId(SysOrgElement fdOrgPost) {
		this.fdOrgPost = fdOrgPost;
	}

	public String getFdOrgPostIds() {
		return fdOrgPostIds;
	}

	public void setFdOrgPostIds(String fdOrgPostIds) {
		this.fdOrgPostIds = fdOrgPostIds;
	}

	public String getFdIsAttendance() {
		return fdIsAttendance;
	}

	public void setFdIsAttendance(String fdIsAttendance) {
		this.fdIsAttendance = fdIsAttendance;
	}

	public String getFdPostalAddressProvinceId() {
		return fdPostalAddressProvinceId;
	}

	public void setFdPostalAddressProvinceId(String fdPostalAddressProvinceId) {
		this.fdPostalAddressProvinceId = fdPostalAddressProvinceId;
	}

	private String fdPostalAddressProvinceId;
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

	public Date getFdDepartureTime() {
		return fdDepartureTime;
	}

	public void setFdDepartureTime(Date fdDepartureTime) {
		this.fdDepartureTime = fdDepartureTime;
	}

	private String fdIsOAUser;

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

	public String getFdPrincipalIdentification() {
		return fdPrincipalIdentification;
	}

	public void setFdPrincipalIdentification(String fdPrincipalIdentification) {
		this.fdPrincipalIdentification = fdPrincipalIdentification;
	}

	public SysOrgElement getFdHeadOfFirstLevelDepartment() throws Exception {
		SysOrgElement sysOrgElement = this.getFdOrgParent();
//		SysOrgElement sysOrgElement = this.fdOrgParent;
		if(sysOrgElement==null)return null;
		List<SysOrgElement> allParent = sysOrgElement.getAllParent(true);

		SysOrgElement sysOrgElement2 = null;
		if(allParent.size()!=1)
		sysOrgElement2 = allParent.get(allParent.size() - 2);
		else sysOrgElement2 = sysOrgElement;
		JSONObject object2 = new JSONObject();
		SysOrgElement sysOrgElement3 = null;
		if(sysOrgElement2.getHbmThisLeader()!=null)
		sysOrgElement3 = (SysOrgElement) getSysOrgElementService()
				.findByPrimaryKey(
						sysOrgElement2.getHbmThisLeader().getFdId());
		
		return sysOrgElement3;
	}

	public void
			setFdHeadOfFirstLevelDepartment(
					SysOrgElement fdHeadOfFirstLevelDepartment) {
		this.fdHeadOfFirstLevelDepartment = fdHeadOfFirstLevelDepartment;
	}

	public SysOrgElement getFdDepartmentHead() {
		return fdDepartmentHead;
	}

	public void setFdDepartmentHead(SysOrgElement fdDepartmentHead) {
		this.fdDepartmentHead = fdDepartmentHead;
	}

	public String getFdDirectSuperiorJobNumber() {
		return fdDirectSuperiorJobNumber;
	}

	public void setFdDirectSuperiorJobNumber(String fdDirectSuperiorJobNumber) {
		
		this.fdDirectSuperiorJobNumber = fdDirectSuperiorJobNumber;
	}

	public String getFdCategory() {
		return fdCategory;
	}

	public void setFdCategory(String fdCategory) {
		this.fdCategory = fdCategory;
	}

	public String getFdTimeCardNo() {
		return fdTimeCardNo;
	}

	public void setFdTimeCardNo(String fdTimeCardNo) {
		this.fdTimeCardNo = fdTimeCardNo;
	}

	public String getFdAffiliatedCompany() {
		return fdAffiliatedCompany;
	}

	public void setFdAffiliatedCompany(String fdAffiliatedCompany) {
		this.fdAffiliatedCompany = fdAffiliatedCompany;
	}

	public SysOrgElement getFdThirdLevelDepartment() {
		return fdThirdLevelDepartment;
	}

	public void setFdThirdLevelDepartment(SysOrgElement fdThirdLevelDepartment) {
		this.fdThirdLevelDepartment = fdThirdLevelDepartment;
	}

	public SysOrgElement getFdSecondLevelDepartment() {
		return fdSecondLevelDepartment;
	}

	public void setFdSecondLevelDepartment(SysOrgElement fdSecondLevelDepartment) {
		this.fdSecondLevelDepartment = fdSecondLevelDepartment;
	}

	public SysOrgElement getFdFirstLevelDepartment() {
		return fdFirstLevelDepartment;
	}

	public void setFdFirstLevelDepartment(SysOrgElement fdFirstLevelDepartment) {
		this.fdFirstLevelDepartment = fdFirstLevelDepartment;
	}

	private String fdHomeAddress;
	private String fdForeignLanguageLevel;
	private String fdIsRetiredSoldier;

	public String getFdIsRetiredSoldier() {
		return fdIsRetiredSoldier;
	}

	public void setFdIsRetiredSoldier(String fdIsRetiredSoldier) {
		this.fdIsRetiredSoldier = fdIsRetiredSoldier;
	}

	public String getFdForeignLanguageLevel() {
		return fdForeignLanguageLevel;
	}

	public void setFdForeignLanguageLevel(String fdForeignLanguageLevel) {
		this.fdForeignLanguageLevel = fdForeignLanguageLevel;
	}

	public String getFdHomeAddress() {
		return fdHomeAddress;
	}

	public void setFdHomeAddress(String fdHomeAddress) {
		this.fdHomeAddress = fdHomeAddress;
	}

	public String getFdPostalAddress() {
		return fdPostalAddress;
	}

	public void setFdPostalAddress(String fdPostalAddress) {
		this.fdPostalAddress = fdPostalAddress;
	}

	private String fdLivingPlace;
	// 绫嶈疮
	private String fdNativePlace;
	// 鍑虹敓鍦�
	private String fdHomeplace;
	// 鎴峰彛鎬ц川
	private String fdAccountProperties;
	// 鎴峰彛鎵�鍦ㄥ湴
	private String fdRegisteredResidence;
	// 鎴峰彛鎵�鍦ㄦ淳鍑烘墍
	private String fdResidencePoliceStation;

	// 鎵嬫満锛氳嫢寮曠敤缁勭粐鏋舵瀯锛屽垯涓嶅彲淇敼
	private String fdMobileNo;
	// 閭锛氳嫢寮曠敤缁勭粐鏋舵瀯锛屽垯涓嶅彲淇敼
	private String fdEmail;
	// 鍔炲叕鍦扮偣
	private String fdOfficeLocation;
	// 鍔炲叕鐢佃瘽锛氳嫢寮曠敤缁勭粐鏋舵瀯锛屽垯涓嶅彲淇敼
	private String fdWorkPhone;
	// 绱ф�ヨ仈绯讳汉
	private String fdEmergencyContact;
	// 绱ф�ヨ仈绯讳汉鐢佃瘽
	private String fdEmergencyContactPhone;
	// 鍏朵粬鑱旂郴鏂瑰紡
	private String fdOtherContact;
	// 鐩稿叧娴佺▼
	private String fdRelatedProcess;

	// 鐘舵�侊紙鍙栫粍缁囨灦鏋勩�傚湪鑱岋細鏈夋晥鐢ㄦ埛锛岀鑱岋細鏃犳晥鐢ㄦ埛锛�
	// 濡傛灉鏄墜宸ユ柊澧炵殑鍛樺伐锛屽湪姝ゅ瓧娈典繚瀛樻暟鎹�
	private String fdStatus;

	// 灞傜骇ID
	private String fdHierarchyId;

	// 寰呯‘璁ゅ憳宸ョ‘璁�
	private HrStaffEntry fdStaffEntry;

	// 瀵嗙爜锛氳嫢寮曠敤缁勭粐鏋舵瀯锛屽垯涓嶅彲淇敼
	private String fdNewPassword;

	/**
	 * 鍛樺伐钖祫
	 */
	private Double fdSalary;
	/**
	 * 鑱岀骇
	 */
	private HrOrganizationRank fdOrgRank;
	private String fdOrgRank1;

	public String getFdOrgRank1() {
		return fdOrgRank1;
	}

	public void setFdOrgRank1(String fdOrgRank1) {
		this.fdOrgRank1 = fdOrgRank1;
	}

	public HrOrganizationRank getFdOrgRank() {
		return fdOrgRank;
	}

	public void setFdOrgRank(HrOrganizationRank fdOrgRank) {
		this.fdOrgRank = fdOrgRank;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;
	/**
	 * 鐢ㄤ簬鏃ュ織璁板綍鐩稿叧
	 */
	private RequestContext requestContext;
	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());

			toFormPropertyMap.put("fdOrgPosts",
					new ModelConvertor_ModelListToString(
							"fdOrgPostIds:fdOrgPostNames", "fdId:fdName"));
			toFormPropertyMap.put("fdOrgParentOrg.fdId", "fdOrgParentOrgId");
			toFormPropertyMap.put("fdOrgParentOrg.fdName",
					"fdOrgParentOrgName");
			toFormPropertyMap.put("fdOrgRank.fdId", "fdOrgRankId");
			toFormPropertyMap.put("fdOrgRank.fdName",
					"fdOrgRankName");
			toFormPropertyMap.put("fdOrgPost.fdId", "fdOrgPostId");
			toFormPropertyMap.put("fdOrgPost.fdName",
					"fdOrgRankName1");
			toFormPropertyMap.put("fdOrgParent.fdId", "fdOrgParentId");
			toFormPropertyMap.put("fdOrgParent.deptLevelNames", "fdOrgParentName");
			toFormPropertyMap.put("fdStaffingLevel.fdId", "fdStaffingLevelId");
			toFormPropertyMap.put("fdStaffingLevel.fdName",
					"fdStaffingLevelName");
			toFormPropertyMap.put("fdOrgPerson.fdId", "fdOrgPersonId");

			toFormPropertyMap.put("fdReportLeader.fdNo", "fdDirectSuperiorJobNumber");
			// 鐢变簬鐣岄潰娌℃湁datetime閫夋嫨锛屽彧浣跨敤date
			toFormPropertyMap.put("fdDateOfBirth", new ModelConvertor_Common(
					"fdDateOfBirth").setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdWorkTime", new ModelConvertor_Common(
					"fdWorkTime").setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdTimeOfEnterprise",
					new ModelConvertor_Common("fdTimeOfEnterprise")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdResignationDate",
					new ModelConvertor_Common("fdResignationDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdProposedEmploymentConfirmationDate",
					new ModelConvertor_Common(
							"fdProposedEmploymentConfirmationDate")
									.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdTrialExpirationTime",
					new ModelConvertor_Common("fdTrialExpirationTime")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdDateOfGroup", new ModelConvertor_Common(
					"fdDateOfGroup").setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdDateOfParty", new ModelConvertor_Common(
					"fdDateOfParty").setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdDepartureTime", new ModelConvertor_Common(
					"fdDepartureTime").setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdEntryTime",
					new ModelConvertor_Common("fdEntryTime")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdPositiveTime",
					new ModelConvertor_Common("fdPositiveTime")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdActualPositiveTime",
					new ModelConvertor_Common("fdActualPositiveTime")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdLeaveTime",
					new ModelConvertor_Common("fdLeaveTime")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdActualLeaveTime",
					new ModelConvertor_Common("fdActualLeaveTime")
							.setDateTimeType(DateUtil.TYPE_DATE));
			
			toFormPropertyMap.put("fdStaffEntry.fdId", "fdStaffEntryId");
			toFormPropertyMap.put("fdStaffEntry.fdName", "fdStaffEntryName");
			toFormPropertyMap.put("fdReportLeader.fdId", "fdReportLeaderId");
			toFormPropertyMap.put("fdHeadOfFirstLevelDepartment.fdId", "fdHeadOfFirstLevelDepartmentId");
			toFormPropertyMap.put("fdDepartmentHead.fdId", "fdDepartmentHeadId");
			toFormPropertyMap.put("fdThirdLevelDepartment.fdId", "fdThirdLevelDepartmentId");
			toFormPropertyMap.put("fdSecondLevelDepartment.fdId", "fdSecondLevelDepartmentId");
			toFormPropertyMap.put("fdFirstLevelDepartment.fdId", "fdFirstLevelDepartmentId");
			toFormPropertyMap.put("fdHeadOfFirstLevelDepartment.fdName",
					"fdHeadOfFirstLevelDepartmentName");
			toFormPropertyMap.put("fdReportLeader.fdName",
					"fdReportLeaderName");
			toFormPropertyMap.put("fdDepartmentHead.fdName",
					"fdDepartmentHeadName");
			toFormPropertyMap.put("fdThirdLevelDepartment.fdName",
					"fdThirdLevelDepartmentName");
			toFormPropertyMap.put("fdSecondLevelDepartment.fdName",
					"fdSecondLevelDepartmentName");
			toFormPropertyMap.put("fdFirstLevelDepartment.fdName",
					"fdFirstLevelDepartmentName");
//			toFormPropertyMap.put("fdOrgPosts",
//					new ModelConvertor_ModelListToString("fdPostIds:fdPostNames", "fdId:fdName"));
			toFormPropertyMap.put("fdHrReportLeader.fdId", "fdHrReportLeaderId");
			toFormPropertyMap.put("fdHrReportLeader.fdName", "fdHrReportLeaderName");
		}

		return toFormPropertyMap;
	}

	public SysOrgPerson getFdOrgPerson() {
		return fdOrgPerson;
	}

	public void setFdOrgPerson(SysOrgPerson fdOrgPerson) {
		this.fdOrgPerson = fdOrgPerson;
	}

	public SysOrgElement getFdOrgParentOrg() {
		// 濡傛灉鏄粍缁囨灦鏋勪汉鍛橈紝鍒欒幏鍙栫粍缁囨灦鏋勪俊鎭�
		if (this.fdOrgPerson != null) {
			fdOrgParentOrg = this.fdOrgPerson.getFdParentOrg();
		}
		return fdOrgParentOrg;
	}

	public void setFdOrgParentOrg(SysOrgElement fdOrgParentOrg) {
		this.fdOrgParentOrg = fdOrgParentOrg;
	}

	public SysOrgElement getFdOrgParent() {
		// 濡傛灉鏄粍缁囨灦鏋勪汉鍛橈紝鍒欒幏鍙栫粍缁囨灦鏋勪俊鎭�
		if (this.fdOrgPerson != null) {
			fdOrgParent = this.fdOrgPerson.getFdParent();
		}
		return fdOrgParent;
	}

	public void setFdOrgParent(SysOrgElement fdOrgParent) {
		this.fdOrgParent = fdOrgParent;
	}

	/**
	 * 鑾峰彇绂昏亴浜哄憳鐨勯儴闂�
	 * @return
	 */
	public String getFdLeavelOrgParentsName() {
		return fdLeavelOrgParentsName;
	}

	public void setFdLeavelOrgParentsName(String fdLeavelOrgParentsName) {
		this.fdLeavelOrgParentsName = fdLeavelOrgParentsName;
	}

	/**
	 * 所处岗位名称
	 */
	public String fdSysPostName;

	public String getFdSysPostName() {
		if (CollectionUtils.isNotEmpty(fdOrgPosts)) {
			List<String> names = Lists.newArrayList();
			for (Object orgPost : fdOrgPosts) {
				names.add(((SysOrgPost) orgPost).getFdName());
			}
			fdSysPostName = Joiner.on(";").join(names);
		}
		return fdSysPostName;
	}

	public void setFdSysPostName(String fdSysPostName) {
		this.fdSysPostName = fdSysPostName;
	}

	/**
	 * 获取人员的商机部门
	 * @return
	 */
	public String getFdOrgParentsName() {
		if (fdOrgParentsName == null) {
			fdOrgParentsName = HrStaffPersonUtil
					.getFdOrgParentsName(getFdOrgParent());
		}
		return fdOrgParentsName;
	}

	public void setFdOrgParentsName(String fdOrgParentsName) {
		this.fdOrgParentsName = fdOrgParentsName;
	}

	@Override
	public List<SysOrgPost> getFdOrgPosts() {
		// 濡傛灉鏄粍缁囨灦鏋勪汉鍛橈紝鍒欒幏鍙栫粍缁囨灦鏋勪俊鎭�
		if (fdOrgPosts ==null && this.fdOrgPerson != null) {
			if(CollectionUtils.isNotEmpty(this.fdOrgPerson.getFdPosts())) {
				//浜轰簨缁勭粐鏋舵瀯涓汉鍛樺矖浣嶆槸1涓�
				fdOrgPosts = Lists.newArrayList(this.fdOrgPerson.getFdPosts().get(0));
			}
		}
		return fdOrgPosts;
	}

	@Override
	public void setFdOrgPosts(List fdOrgPosts) {
		this.fdOrgPosts = fdOrgPosts;
	}

	public SysOrganizationStaffingLevel getFdStaffingLevel() {
		// 濡傛灉鏄粍缁囨灦鏋勪汉鍛橈紝鍒欒幏鍙栫粍缁囨灦鏋勪俊鎭�
		if (this.fdOrgPerson != null) {
			fdStaffingLevel = this.fdOrgPerson.getFdStaffingLevel();
		}
		return fdStaffingLevel;
	}

	public void setFdStaffingLevel(SysOrganizationStaffingLevel fdStaffingLevel) {
		this.fdStaffingLevel = fdStaffingLevel;
	}

	@Override
    public String getFdName() {
		// 濡傛灉鏄粍缁囨灦鏋勪汉鍛橈紝鍒欒幏鍙栫粍缁囨灦鏋勪俊鎭�
		if (this.fdOrgPerson != null) {
			fdName = this.fdOrgPerson.getFdName();
		}
		return fdName;
	}

	@Override
    public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdSex() {
		// 濡傛灉鏄粍缁囨灦鏋勪汉鍛橈紝鍒欒幏鍙栫粍缁囨灦鏋勪俊鎭�
		if (this.fdOrgPerson != null) {
			this.fdSex = this.fdOrgPerson.getFdSex();
		}
		return fdSex;
	}

	public void setFdSex(String fdSex) {
		this.fdSex = fdSex;
	}

	public Integer getFdBirthdayOfYear() {
		//鐢熸棩娌″�硷紝鍒欓粯璁ゆ槸0
		if (fdDateOfBirth != null) {
			fdBirthdayOfYear =HrStaffDateUtil.dateToFdBirthdayOfYear(fdDateOfBirth);
		}else {
			fdBirthdayOfYear =0;
		}
		return fdBirthdayOfYear;
	}

	public void setFdBirthdayOfYear(Integer fdBirthdayOfYear) {
		this.fdBirthdayOfYear = fdBirthdayOfYear;
	}

	public Date getFdDateOfBirth() {
		return fdDateOfBirth;
	}

	public Date getFdActualPositiveTime() {
		return fdActualPositiveTime;
	}

	public void setFdActualPositiveTime(Date fdActualPositiveTime) {
		this.fdActualPositiveTime = fdActualPositiveTime;
	}

	public String getFdPositiveRemark() {
		return fdPositiveRemark;
	}

	public void setFdPositiveRemark(String fdPositiveRemark) {
		this.fdPositiveRemark = fdPositiveRemark;
	}

	public void setFdDateOfBirth(Date fdDateOfBirth) {
		this.fdDateOfBirth = fdDateOfBirth;
		if (fdDateOfBirth != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(fdDateOfBirth);
			if (cal.get(Calendar.YEAR) % 4 == 0) {
				setFdBirthdayOfYear(cal.get(Calendar.DAY_OF_YEAR) - 1);
			} else {
				setFdBirthdayOfYear(cal.get(Calendar.DAY_OF_YEAR));
			}
		} else {
			setFdBirthdayOfYear(null);
		}
	}

	public int getFdAge() {
		// 鏍规嵁鐢熸棩鑷姩璁＄畻
		if (fdDateOfBirth != null) {
			fdAge = HrStaffDateUtil.getNeturalAge(fdDateOfBirth, new Date())[0];
		}
		return fdAge;
	}

	public void setFdAge(int fdAge) {
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

	public Date getFdWorkTime() {
		return fdWorkTime;
	}

	public void setFdWorkTime(Date fdWorkTime) {
		this.fdWorkTime = fdWorkTime;
	}


	public String getFdUninterruptedEntryTime() {
		// 鏍规嵁鍙傚姞宸ヤ綔鏃堕棿鑷姩璁＄畻
		if (fdEntryTime != null) {
			Integer diff = getFdEntryTimeDiff();
			int[] _time = HrStaffDateUtil.getNeturalAge(fdEntryTime, new Date(),
					diff);
			fdUninterruptedEntryTime = ResourceUtil.getString(
					"hrStaffPersonInfo.fdEntryingYears.show", "hr-staff", null,
					new Object[] { _time[0], _time[1] });
		}
		return fdUninterruptedEntryTime;
	}


	public String getFdUninterruptedWorkTime() {
		// 鏍规嵁鍙傚姞宸ヤ綔鏃堕棿鑷姩璁＄畻
		if (fdWorkTime != null) {
			Integer diff = getFdWorkTimeDiff();
			int[] _time = HrStaffDateUtil.getNeturalAge(fdWorkTime, new Date(),
					diff);
			fdUninterruptedWorkTime = ResourceUtil.getString(
					"hrStaffPersonInfo.fdWorkingYears.show", "hr-staff", null,
					new Object[] { _time[0], _time[1] });
			fdUninterruptedWorkTime=""+_time[0];
		}
		return fdUninterruptedWorkTime;
	}

	public void setFdUninterruptedWorkTime(String fdUninterruptedWorkTime) {
		this.fdUninterruptedWorkTime = fdUninterruptedWorkTime;
	}

	public void
			setFdUninterruptedEntryTime(String fdUninterruptedEntryTime) {
		this.fdUninterruptedEntryTime = fdUninterruptedEntryTime;
	}

	public Integer getFdUninterruptedWorkTimeValue() {
		// 鏍规嵁鍙傚姞宸ヤ綔鏃堕棿鑷姩璁＄畻
		if (fdWorkTime != null) {
			int[] _time = HrStaffDateUtil.getNeturalAge(fdWorkTime, new Date());
			int year = _time[0]>0?_time[0]:0;
			int month = _time[1]>0?_time[1]:0;
			fdUninterruptedWorkTimeValue = year*12+month;
		}
		return fdUninterruptedWorkTimeValue;
	}
	public void setFdUninterruptedWorkTimeValue(Integer fdUninterruptedWorkTimeValue) {
		this.fdUninterruptedWorkTimeValue = fdUninterruptedWorkTimeValue;
	}



	public Date getFdTimeOfEnterprise() {
		return fdTimeOfEnterprise;
	}

	public void setFdTimeOfEnterprise(Date fdTimeOfEnterprise) {
		this.fdTimeOfEnterprise = fdTimeOfEnterprise;
	}

	public String getFdWorkingYears() {
		// 鏍规嵁鍒版湰鍗曚綅鏃堕棿鑷姩璁＄畻
		if (fdEntryTime != null) {
			Date currentdate = new Date(); // 鑾峰彇褰撳墠鏃堕棿
			if (fdEntryTime.getTime() > currentdate.getTime()) {
				fdWorkingYears = ResourceUtil.getString(
						"hrStaffPersonInfo.fdWorkingYears.show", "hr-staff",
						null,
						new Object[] { 0, 0 });
			} else {
				Integer diff = getFdWorkingYearsDiff();
				int[] _time = HrStaffDateUtil.getNeturalAge(fdEntryTime,
						currentdate,diff);
				fdWorkingYears = ResourceUtil.getString(
						"hrStaffPersonInfo.fdWorkingYears.show", "hr-staff",
						null,
						new Object[] { _time[0], _time[1] });
				fdWorkingYears=""+_time[0];
			}
		}
		return fdWorkingYears;
	}

	public void setFdWorkingYears(String fdWorkingYears) {
		this.fdWorkingYears = fdWorkingYears;
	}
	
	public Integer getFdWorkingYearsValue() {
		// 鏍规嵁鍒版湰鍗曚綅鏃堕棿鑷姩璁＄畻
		if (fdEntryTime != null) {
			Date currentdate = new Date(); // 鑾峰彇褰撳墠鏃堕棿
			if (fdEntryTime.getTime() > currentdate.getTime()) {
				fdWorkingYearsValue = 0;
			} else {
				int[] _time = HrStaffDateUtil.getNeturalAge(fdEntryTime,
						currentdate);
				int year = _time[0]>0?_time[0]:0;
				int month = _time[1]>0?_time[1]:0;
				fdWorkingYearsValue = year*12+month;
			}
		}
		return fdWorkingYearsValue;
	}

	public void setFdWorkingYearsValue(Integer fdWorkingYearsValue) {
		this.fdWorkingYearsValue = fdWorkingYearsValue;
	}

	public Date getFdTrialExpirationTime() {
		if (null == this.fdTrialExpirationTime && null != getFdEntryTime()
				&& StringUtil.isNotNull(this.fdTrialOperationPeriod)) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getFdEntryTime());
			if (Double.valueOf(this.fdTrialOperationPeriod).intValue() <= 36){
				cal.add(Calendar.MONTH,
						Double.valueOf(this.fdTrialOperationPeriod).intValue());
			}
			return cal.getTime();
		}
		return fdTrialExpirationTime;
	}

	public void setFdTrialExpirationTime(Date fdTrialExpirationTime) {
		this.fdTrialExpirationTime = fdTrialExpirationTime;
	}

	public Integer getFdEmploymentPeriod() {
		return fdEmploymentPeriod;
	}

	public void setFdEmploymentPeriod(Integer fdEmploymentPeriod) {
		this.fdEmploymentPeriod = fdEmploymentPeriod;
	}

	public String getFdStaffType() throws Exception {
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
		return fdNation;
	}

	public void setFdNation(String fdNation) {
		this.fdNation = fdNation;
	}

	public String getFdPoliticalLandscape() throws Exception {
		return fdPoliticalLandscape;
	}

	public void setFdPoliticalLandscape(String fdPoliticalLandscape) {
		this.fdPoliticalLandscape = fdPoliticalLandscape;
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

	public String getFdHighestEducation() throws Exception {
		return fdHighestEducation;
	}

	public void setFdHighestEducation(String fdHighestEducation) {
		this.fdHighestEducation = fdHighestEducation;
	}

	public String getFdHighestDegree() throws Exception {
		return fdHighestDegree;
	}

	public void setFdHighestDegree(String fdHighestDegree) {
		this.fdHighestDegree = fdHighestDegree;
	}

	public String getFdMaritalStatus() throws Exception {
		return fdMaritalStatus;
	}

	public void setFdMaritalStatus(String fdMaritalStatus) {
		this.fdMaritalStatus = fdMaritalStatus;
	}

	public String getFdHealth() throws Exception {
		return fdHealth;
	}

	public void setFdHealth(String fdHealth) {
		this.fdHealth = fdHealth;
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
		if (this.fdOrgPerson != null) {
			// 璁剧疆鍛樺伐鐘�
			if (StringUtil.isNull(this.fdStatus)) {
				if (this.fdOrgPerson.getFdIsAvailable()) {
					return "official";
				} else {
					return "leave";
				}
			}
		}
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	@Override
    public String getFdMobileNo() {
		// 濡傛灉鏄粍缁囨灦鏋勪汉鍛橈紝鍒欒幏鍙栫粍缁囨灦鏋勪俊鎭�
		if (this.fdOrgPerson != null) {
			fdMobileNo = this.fdOrgPerson.getFdMobileNo();
		}
		return fdMobileNo;
	}

	public void setFdMobileNo(String fdMobileNo) {
		this.fdMobileNo = fdMobileNo;
	}

	public String getFdNewPassword() {
		// 濡傛灉鏄粍缁囨灦鏋勪汉鍛橈紝鍒欒幏鍙栫粍缁囨灦鏋勪俊鎭�
		if (this.fdOrgPerson != null) {
			fdNewPassword = this.fdOrgPerson.getFdPassword();
		}
		return fdNewPassword;
	}

	public void setFdNewPassword(String fdNewPassword) {
		this.fdNewPassword = fdNewPassword;
	}

	@Override
    public String getFdEmail() {
		// 濡傛灉鏄粍缁囨灦鏋勪汉鍛橈紝鍒欒幏鍙栫粍缁囨灦鏋勪俊鎭�
		if (this.fdOrgPerson != null) {
			fdEmail = this.fdOrgPerson.getFdEmail();
		}
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
		// 濡傛灉鏄粍缁囨灦鏋勪汉鍛橈紝鍒欒幏鍙栫粍缁囨灦鏋勪俊鎭�
		if (this.fdOrgPerson != null) {
			fdWorkPhone = this.fdOrgPerson.getFdWorkPhone();
		}
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
	public String getFdRelatedProcess() {
		return fdRelatedProcess;
	}

	public void setFdRelatedProcess(String fdRelatedProcess) {
		this.fdRelatedProcess = fdRelatedProcess;
	}
	/**
	 * 杩斿洖濮撳悕锛堣处鍙凤級锛屽垪琛ㄩ〉闈娇鐢�
	 * 
	 * @return
	 */
	public String getNameAccount() {
		if (this.fdOrgPerson != null) {
			return this.fdOrgPerson.getFdName() + "("
					+ this.fdOrgPerson.getFdLoginName() + ")";
		} else {
			return this.fdName;
		}
	}

	private Date docCreateTime;

	@Override
	public Date getDocCreateTime() {
		return docCreateTime;
	}

	@Override
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	private SysOrgPerson docCreator;

	@Override
	public SysOrgPerson getDocCreator() {
		return docCreator;
	}

	@Override
	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	private SysTagMain sysTagMain = null;

	@Override
	public SysTagMain getSysTagMain() {
		return sysTagMain;
	}

	@Override
	public void setSysTagMain(SysTagMain sysTagMain) {
		this.sysTagMain = sysTagMain;
	}

	public String getDocStatus() {
		return "30";
	}

	@Override
    public String getFdHierarchyId() {
		return fdHierarchyId;
	}

	@Override
    public void setFdHierarchyId(String fdHierarchyId) {
		this.fdHierarchyId = fdHierarchyId;
	}

	 AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);
		@Override
        public AutoHashMap getAttachmentForms() {
			return autoHashMap;
		}

	//璇曠敤鏈熼檺
	private String fdTrialOperationPeriod;

	public void setFdTrialOperationPeriod(String fdTrialOperationPeriod) {
		this.fdTrialOperationPeriod = fdTrialOperationPeriod;
	}

	public String getFdTrialOperationPeriod() {
		return fdTrialOperationPeriod;
	}



	// 鍏ヨ亴鏃ユ湡
	private Date fdEntryTime;

	// 杞鏃ユ湡
	private Date fdPositiveTime;

	// 瀹為檯杞鏃ユ湡
	private Date fdActualPositiveTime;

	// 杞澶囨敞
	private String fdPositiveRemark;

	// 绂昏亴鏃ユ湡
	private Date fdLeaveTime;
	
	// 瀹為檯绂昏亴鏃ユ湡
	private Date fdActualLeaveTime;

	public Date getFdEntryTime() {
		return fdEntryTime;
	}

	public void setFdEntryTime(Date fdEntryTime) {
		this.fdTimeOfEnterprise = fdEntryTime;
		this.fdEntryTime = fdEntryTime;
	}

	public Date getFdPositiveTime() {
		return fdPositiveTime;
	}

	public void setFdPositiveTime(Date fdPositiveTime) {
		this.fdPositiveTime = fdPositiveTime;
	}

	public Date getFdLeaveTime() {
		return fdLeaveTime;
	}

	public void setFdLeaveTime(Date fdLeaveTime) {
		this.fdLeaveTime = fdLeaveTime;
	}
	
	public Date getFdActualLeaveTime() {
		return fdActualLeaveTime;
	}

	public void setFdActualLeaveTime(Date fdActualLeaveTime) {
		this.fdActualLeaveTime = fdActualLeaveTime;
	}

	private Boolean fdIsRehire;

	private Date fdRehireTime;

	public Boolean getFdIsRehire() {
		return fdIsRehire;
	}

	public void setFdIsRehire(Boolean fdIsRehire) {
		this.fdIsRehire = fdIsRehire;
	}

	public Date getFdRehireTime() {
		return fdRehireTime;
	}

	public void setFdRehireTime(Date fdRehireTime) {
		this.fdRehireTime = fdRehireTime;
	}

	public HrStaffEntry getFdStaffEntry() {
		return fdStaffEntry;
	}

	public void setFdStaffEntry(HrStaffEntry fdStaffEntry) {
		this.fdStaffEntry = fdStaffEntry;
	}

	// 绂昏亴鐢宠鏃ユ湡
	private Date fdLeaveApplyDate;

	// 璁″垝绂昏亴鏃ユ湡
	private Date fdLeavePlanDate;

	// 钖叕缁撶畻鏃ユ湡
	private Date fdLeaveSalaryEndDate;

	// 绂昏亴鍘熷洜
	private String fdLeaveReason;

	// 绂昏亴澶囨敞
	private String fdLeaveRemark;

	// 绂昏亴鍘诲悜
	private String fdNextCompany;

	private String fdLeaveStatus;

	public String getFdLeaveStatus() {
		return fdLeaveStatus;
	}

	public void setFdLeaveStatus(String fdLeaveStatus) {
		this.fdLeaveStatus = fdLeaveStatus;
	}

	public Date getFdLeaveApplyDate() {
		return fdLeaveApplyDate;
	}

	public void setFdLeaveApplyDate(Date fdLeaveApplyDate) {
		this.fdLeaveApplyDate = fdLeaveApplyDate;
	}

	public Date getFdLeavePlanDate() {
		return fdLeavePlanDate;
	}

	public void setFdLeavePlanDate(Date fdLeavePlanDate) {
		this.fdLeavePlanDate = fdLeavePlanDate;
	}

	public Date getFdLeaveSalaryEndDate() {
		return fdLeaveSalaryEndDate;
	}

	public void setFdLeaveSalaryEndDate(Date fdLeaveSalaryEndDate) {
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

	// 宸ヤ綔鎬ц川
	private String fdNatureWork;

	public String getFdNatureWork() throws Exception {
		return fdNatureWork;
	}

	public void setFdNatureWork(String fdNatureWork) {
		this.fdNatureWork = fdNatureWork;
	}

	// 姹囨姤涓婄骇
	private SysOrgElement fdReportLeader;
	private SysOrgElement fdHeadOfFirstLevelDepartment;

	private SysOrgElement fdDepartmentHead;
	private HrOrganizationElement fdHrReportLeader;

	// 宸ヤ綔鍦扮偣
	private String fdWorkAddress;

	// 绯荤粺璐﹀彿
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

	public SysOrgElement getFdReportLeader() {
		return fdReportLeader;
	}

	public void setFdReportLeader(SysOrgElement fdReportLeader) {
		this.fdReportLeader = fdReportLeader;
	}

	public String getFdWorkAddress() throws Exception {
		return fdWorkAddress;
	}

	public void setFdWorkAddress(String fdWorkAddress) {
		this.fdWorkAddress = fdWorkAddress;
	}

	@Override
    public String getFdLoginName() {
		if (fdOrgPerson != null) {
			fdLoginName = fdOrgPerson.getFdLoginName();
		}
		return fdLoginName;
	}

	public void setFdLoginName(String fdLoginName) {
		this.fdLoginName = fdLoginName;
	}

	public String getFdOrgParentDeptName() {
		return fdOrgParentDeptName;
	}

	public void setFdOrgParentDeptName(String fdOrgParentDeptName) {
		this.fdOrgParentDeptName = fdOrgParentDeptName;
	}

	public Double getFdSalary() {
		return fdSalary;
	}

	public void setFdSalary(Double fdSalary) {
		this.fdSalary = fdSalary;
	}

	public HrOrganizationElement getFdHrReportLeader() {
		return fdHrReportLeader;
	}

	public void setFdHrReportLeader(HrOrganizationElement fdHrReportLeader) {
		this.fdHrReportLeader = fdHrReportLeader;
	}

	public Integer getFdWorkTimeDiff() {
		return fdWorkTimeDiff;
	}

	public void setFdWorkTimeDiff(Integer fdWorkTimeDiff) {
		this.fdWorkTimeDiff = fdWorkTimeDiff;
	}

	public Integer getFdWorkingYearsDiff() {
		return fdWorkingYearsDiff;
	}

	public void setFdWorkingYearsDiff(Integer fdWorkingYearsDiff) {
		this.fdWorkingYearsDiff = fdWorkingYearsDiff;
	}

	public RequestContext getRequestContext() {
		return requestContext;
	}

	public void setRequestContext(RequestContext requestContext) {
		this.requestContext = requestContext;
	}

	/**
	 * 鏄惁鐧诲綍绯荤粺
	 */
	private Boolean fdCanLogin;

	public Boolean getFdCanLogin() {
		if (fdCanLogin == null) {
			fdCanLogin = true;
		}
		return fdCanLogin;
	}

	public void setFdCanLogin(Boolean fdCanLogin) {
		this.fdCanLogin = fdCanLogin;
	}
}
