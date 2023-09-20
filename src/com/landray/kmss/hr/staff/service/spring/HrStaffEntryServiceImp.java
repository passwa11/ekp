package com.landray.kmss.hr.staff.service.spring;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.ICheckUniqueBean;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting;
import com.landray.kmss.hr.staff.event.HrStaffEntryEvent;
import com.landray.kmss.hr.staff.event.HrStaffPersonInfoEvent;
import com.landray.kmss.hr.staff.forms.HrStaffEntryForm;
import com.landray.kmss.hr.staff.model.HrStaffCertifi;
import com.landray.kmss.hr.staff.model.HrStaffEduExp;
import com.landray.kmss.hr.staff.model.HrStaffEmolumentWelfare;
import com.landray.kmss.hr.staff.model.HrStaffEntry;
import com.landray.kmss.hr.staff.model.HrStaffHistory;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceBonusMalus;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceEducation;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceQualification;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceWork;
import com.landray.kmss.hr.staff.model.HrStaffPersonFamily;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoSettingNew;
import com.landray.kmss.hr.staff.model.HrStaffRewPuni;
import com.landray.kmss.hr.staff.model.HrStaffTrackRecord;
import com.landray.kmss.hr.staff.service.IHrStaffEntryService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceBonusMalusService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceEducationService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceQualificationService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceWorkService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.service.IHrStaffTrackRecordService;
import com.landray.kmss.hr.staff.util.HrStaffImportUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.sys.organization.webservice.eco.ISysSynchroEcoWebService;
import com.landray.kmss.sys.profile.service.ISysOrgImportService;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.HibernateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.PhoneUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.excel.Column;
import com.landray.kmss.util.excel.WorkBook;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class HrStaffEntryServiceImp extends ExtendDataServiceImp
		implements IHrStaffEntryService, ICheckUniqueBean, SysOrgConstant,
		ApplicationContextAware {

	private ISysOrgPersonService sysOrgPersonService;
	private ISysSynchroEcoWebService sysSynchroEcoWebService;

	private IHrStaffTrackRecordService hrStaffTrackRecordService;
	private ApplicationContext applicationContext;
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HrStaffEntryServiceImp.class);

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	public void
			setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	private ISysOrgImportService sysOrgImportService;

	public void setSysSynchroEcoWebService(
			ISysSynchroEcoWebService sysSynchroEcoWebService) {
		this.sysSynchroEcoWebService = sysSynchroEcoWebService;
	}

	public void
			setSysOrgImportService(ISysOrgImportService sysOrgImportService) {
		this.sysOrgImportService = sysOrgImportService;
	}

	public IHrStaffTrackRecordService getHrStaffTrackRecordService() {
		return hrStaffTrackRecordService;
	}

	public void setHrStaffTrackRecordService(
			IHrStaffTrackRecordService hrStaffTrackRecordService) {
		this.hrStaffTrackRecordService = hrStaffTrackRecordService;
	}

	@Override
	public HrStaffEntry findByFdMobileNo(String fdMobileNo) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("hrStaffEntry.fdMobileNo=:fdMobileNo");
		hqlInfo.setParameter("fdMobileNo", fdMobileNo);
		List<HrStaffEntry> list = findList(hqlInfo);
		if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
		return null;
	}

	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		HrStaffEntryForm entryForm = (HrStaffEntryForm) form;
		entryForm.setFdDataFrom("2");
		SysOrgPerson curUser = UserUtil.getUser();
		entryForm.setFdAlterorId(curUser.getFdId());
		entryForm.setFdAlterorName(curUser.getFdName());
		String now = DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATETIME, requestContext.getLocale());
		entryForm.setFdLastModifiedTime(now);
		entryForm.setDocCreateTime(now);

		return super.add(form, requestContext);
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		HrStaffEntry entry = (HrStaffEntry) modelObj;
		// 家庭信息设置创建时间和创建人
		List<HrStaffPersonFamily> list = entry.getFdStaffPersonFamilies();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				HrStaffPersonFamily family = (HrStaffPersonFamily) list
						.get(i);
				family.setFdCreateTime(new Date());
				family.setFdCreator(UserUtil.getUser());
			}
		}
		// 福利
		entry.setHrStaffEmolumentWelfare(null);
		return super.add(modelObj);
	}
	@Override
	public void update(IExtendForm form, RequestContext requestContext) throws Exception {
		HrStaffEntryForm entryForm = (HrStaffEntryForm) form;
		String now = DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATETIME, requestContext.getLocale());
		entryForm.setFdLastModifiedTime(now);
		super.update(form, requestContext);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		HrStaffEntry entry = (HrStaffEntry) modelObj;
		// 家庭信息设置创建时间和创建人
		List<HrStaffPersonFamily> list = entry.getFdStaffPersonFamilies();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				HrStaffPersonFamily family = (HrStaffPersonFamily) list
						.get(i);
				family.setFdCreateTime(new Date());
				family.setFdCreator(UserUtil.getUser());
			}
		}
		// 福利
		HrStaffEmolumentWelfare welfare = entry.getHrStaffEmolumentWelfare();
		if (welfare != null && StringUtil.isNotNull(welfare.getFdPayrollName())) {
			welfare.setDocMain(entry);
			welfare.setFdCreateTime(new Date());
			welfare.setFdCreator(UserUtil.getUser());
		}else{
			entry.setHrStaffEmolumentWelfare(null);
		}
		super.update(modelObj);
		// 查询人事档案和人事流程中是否存在关联，如果有关联则修改人事流程和人事档案中的电话号码
		if(entry.getFdOrgPerson() != null){
			HrStaffPersonInfo byOrgPersonId = hrStaffPersonInfoService.findByOrgPersonId(entry.getFdOrgPerson().getFdId());
			if(byOrgPersonId != null){
				//人事档案电话号码的修改
				if(!entry.getFdMobileNo().equals(byOrgPersonId.getFdMobileNo())) {
					byOrgPersonId.setFdMobileNo(entry.getFdMobileNo());
					hrStaffPersonInfoService.update(byOrgPersonId);
				}
			}
		}
	}

	private ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	public void setHrStaffPersonInfoService(
			IHrStaffPersonInfoService hrStaffPersonInfoService) {
		this.hrStaffPersonInfoService = hrStaffPersonInfoService;
	}

	private IHrStaffPersonExperienceWorkService hrStaffPersonExperienceWorkService;

	public void setHrStaffPersonExperienceWorkService(
			IHrStaffPersonExperienceWorkService hrStaffPersonExperienceWorkService) {
		this.hrStaffPersonExperienceWorkService = hrStaffPersonExperienceWorkService;
	}

	private IHrStaffPersonExperienceEducationService hrStaffPersonExperienceEducationService;

	public void setHrStaffPersonExperienceEducationService(
			IHrStaffPersonExperienceEducationService hrStaffPersonExperienceEducationService) {
		this.hrStaffPersonExperienceEducationService = hrStaffPersonExperienceEducationService;
	}

	public IHrStaffPersonExperienceQualificationService hrStaffPersonExperienceQualificationService;

	public void setHrStaffPersonExperienceQualificationService(
			IHrStaffPersonExperienceQualificationService hrStaffPersonExperienceQualificationService) {
		this.hrStaffPersonExperienceQualificationService = hrStaffPersonExperienceQualificationService;
	}

	private IHrStaffPersonExperienceBonusMalusService hrStaffPersonExperienceBonusMalusService;

	public void setHrStaffPersonExperienceBonusMalusService(
			IHrStaffPersonExperienceBonusMalusService hrStaffPersonExperienceBonusMalusService) {
		this.hrStaffPersonExperienceBonusMalusService = hrStaffPersonExperienceBonusMalusService;
	}

	private void addOther(HrStaffPersonInfo personInfo, HrStaffEntry entry)
			throws Exception {
		List<HrStaffHistory> fdHistory = entry.getFdHistory();
		for (HrStaffHistory history : fdHistory) {
			HrStaffPersonExperienceWork work = new HrStaffPersonExperienceWork();
			work.setFdCompany(history.getFdName());
			work.setFdPosition(history.getFdPost());
			work.setFdDescription(history.getFdDesc());
			work.setFdReasons(history.getFdLeaveReason());
			work.setFdBeginDate(history.getFdStartDate());
			work.setFdEndDate(history.getFdEndDate());
			work.setFdCreateTime(new Date());
			work.setFdCreator(UserUtil.getUser());
			work.setFdPersonInfo(personInfo);
			hrStaffPersonExperienceWorkService.add(work);
		}
		List<HrStaffEduExp> fdEducations = entry.getFdEducations();
		for (HrStaffEduExp eduExp : fdEducations) {
			HrStaffPersonExperienceEducation education = new HrStaffPersonExperienceEducation();
			education.setFdSchoolName(eduExp.getFdName());
			education.setFdMajor(eduExp.getFdMajor());
			if (eduExp.getFdAcadeRecord() != null) {
                education.setFdDegree(
                        eduExp.getFdAcadeRecord().getFdName());
            }
			education
					.setFdBeginDate(eduExp.getFdEntranceDate());
			education
					.setFdEndDate(eduExp.getFdGraduationDate());
			education.setFdMemo(eduExp.getFdRemark());
			education.setFdCreateTime(new Date());
			education.setFdCreator(UserUtil.getUser());
			education.setFdPersonInfo(personInfo);
			hrStaffPersonExperienceEducationService
					.add(education);
		}
		List<HrStaffCertifi> certifi = entry.getFdCertificate();
		for (HrStaffCertifi cert : certifi) {
			HrStaffPersonExperienceQualification qualification = new HrStaffPersonExperienceQualification();
			qualification
					.setFdCertificateName(cert.getFdName());
			qualification
					.setFdAwardUnit(cert.getFdIssuingUnit());
			qualification.setFdBeginDate(cert.getFdIssueDate());
			qualification.setFdEndDate(cert.getFdInvalidDate());
			qualification.setFdCreateTime(new Date());
			qualification.setFdCreator(UserUtil.getUser());
			qualification.setFdPersonInfo(personInfo);
			hrStaffPersonExperienceQualificationService
					.add(qualification);
		}
		List<HrStaffRewPuni> rewPuni = entry
				.getFdRewardsPunishments();
		for (HrStaffRewPuni rew : rewPuni) {
			HrStaffPersonExperienceBonusMalus bonusMalus = new HrStaffPersonExperienceBonusMalus();
			bonusMalus.setFdBonusMalusName(rew.getFdName());
			bonusMalus.setFdBonusMalusDate(rew.getFdDate());
			bonusMalus.setFdMemo(rew.getFdRemark());
			bonusMalus.setFdCreateTime(new Date());
			bonusMalus.setFdCreator(UserUtil.getUser());
			bonusMalus.setFdPersonInfo(personInfo);
			hrStaffPersonExperienceBonusMalusService
					.add(bonusMalus);
		}
	}

	private HrStaffPersonInfo setPersonInfo(HrStaffPersonInfo personInfo,
			HrStaffEntry entry) throws Exception {
		//姓名
		personInfo.setFdName(entry.getFdName());
		//曾用名
		personInfo.setFdNameUsedBefore(entry.getFdNameUsedBefore());
		//性别
		personInfo.setFdSex(entry.getFdSex());
		//生日
		personInfo.setFdDateOfBirth(entry.getFdDateOfBirth());
		//籍贯
		personInfo.setFdNativePlace(entry.getFdNativePlace());
		//婚姻状况
		HrStaffPersonInfoSettingNew fdMaritalStatus = entry.getFdMaritalStatus();
		if (fdMaritalStatus != null) {
            personInfo.setFdMaritalStatus(fdMaritalStatus.getFdName());
        }
		//民族
		HrStaffPersonInfoSettingNew fdNation = entry.getFdNation();
		if (fdNation != null) {
            personInfo.setFdNation(fdNation.getFdName());
        }
		//政治面貌
		HrStaffPersonInfoSettingNew fdPoliticalLandscape = entry
				.getFdPoliticalLandscape();
		if (fdPoliticalLandscape != null) {
            personInfo
                    .setFdPoliticalLandscape(fdPoliticalLandscape.getFdName());
        }
		//健康状态
		HrStaffPersonInfoSettingNew fdHealth = entry.getFdHealth();
		if (fdHealth != null) {
            personInfo.setFdHealth(fdHealth.getFdName());
        }
		//居住地
		personInfo.setFdLivingPlace(entry.getFdLivingPlace());
		//身份证
		personInfo.setFdIdCard(entry.getFdIdCard());
		//最高学位
		HrStaffPersonInfoSettingNew fdHighestDegree = entry
				.getFdHighestDegree();
		if (fdHighestDegree != null) {
            personInfo.setFdHighestDegree(fdHighestDegree.getFdName());
        }
		//最高学历
		HrStaffPersonInfoSettingNew fdHighestEducation = entry .getFdHighestEducation();
		if (fdHighestEducation != null) {
            personInfo.setFdHighestEducation(fdHighestEducation.getFdName());
        }
		//参加工作时间
		personInfo.setFdWorkTime(entry.getFdWorkTime());
		//入团日期
		personInfo.setFdDateOfGroup(entry.getFdDateOfGroup());
		//入党日期
		personInfo.setFdDateOfParty(entry.getFdDateOfParty());
		//身高
		personInfo.setFdStature(entry.getFdStature());
		//体重
		personInfo.setFdWeight(entry.getFdWeight());
		//出生地
		personInfo.setFdHomeplace(entry.getFdHomeplace());
		//户口性质
		personInfo.setFdAccountProperties(entry.getFdAccountProperties());
		//户口所在地
		personInfo.setFdRegisteredResidence(entry.getFdRegisteredResidence());
		//户口所在派出所
		personInfo.setFdResidencePoliceStation(entry.getFdResidencePoliceStation());
		//邮箱
		personInfo.setFdEmail(entry.getFdEmail());
		//电话
		personInfo.setFdMobileNo(entry.getFdMobileNo());
		//紧急联系人
		personInfo.setFdEmergencyContact(entry.getFdEmergencyContact());
		//紧急联系人电话
		personInfo.setFdEmergencyContactPhone(entry.getFdEmergencyContactPhone());
		//其他联系方式
		personInfo.setFdOtherContact(entry.getFdOtherContact());
		//系统岗位
		personInfo.setFdOrgPosts(entry.getFdOrgPosts());
		personInfo.setFdPosts(entry.getFdOrgPosts());
		return personInfo;
	}

	@Override
	public void updateCheck(HrStaffEntryForm entryForm) throws Exception {
		HrStaffEntry entry = (HrStaffEntry) findByPrimaryKey(
				entryForm.getFdId());
		String fdName = entryForm.getFdName();
		String fdMobileNo = entryForm.getFdMobileNo();
		//确认状态
		entry.setFdStatus("2");
		entry.setFdMobileNo(fdMobileNo);
		SysOrgElement fdPlanEntryDept = null;
		//根据入职部门获取组织架构中的部门
		String fdPlanEntryDeptId = entryForm.getFdPlanEntryDeptId();
		if (StringUtil.isNotNull(fdPlanEntryDeptId)) {
			fdPlanEntryDept = (SysOrgElement) sysOrgElementService.findByPrimaryKey(fdPlanEntryDeptId);
		}
		entry.setFdPlanEntryDept(fdPlanEntryDept);
		//根据入职岗位获取组织架构中的岗位
		String fdOrgPostIds = entryForm.getFdOrgPostIds();
		List<SysOrgPost> fdOrgPosts = null;
		if (StringUtil.isNotNull(fdOrgPostIds)) {
			String[] ids = fdOrgPostIds.split(";");
			fdOrgPosts = sysOrgElementService.findByPrimaryKeys(ids);
		}
		entry.setFdOrgPosts(fdOrgPosts);
		//计划入职时间
		Date fdPlanEntryTime = DateUtil.convertStringToDate(entryForm.getFdPlanEntryTime());
		entry.setFdPlanEntryTime(fdPlanEntryTime);
		//入职时间
		Date fdEntryTime = DateUtil.convertStringToDate(entryForm.getFdEntryTime());
		//工号
		String fdStaffNo = entryForm.getFdStaffNo();
		HrStaffPersonInfo personInfo = null;
		boolean addPerson=false;
		boolean updatePerson=false;
		String sysOrgPersonId =null;
		String fdLoginName = entryForm.getFdLoginName();
		String fdNewPassword = entryForm.getFdNewPassword();
		SysOrgPerson fdOrgPerson =null;
		// 开通账号 并关联了已有账号
		if ("true".equals(entryForm.getFdIsOpenOrg())) {
			if ("true".equals(entryForm.getFdIsLinkOrg())) {
				String fdOrgPersonId = entryForm.getFdOrgPersonId();
				fdOrgPerson = (SysOrgPerson) sysOrgElementService
						.findByPrimaryKey(fdOrgPersonId, SysOrgPerson.class,
								true);
				//如果组织架构中不存在，则以普通员工入人事档案
				if(fdOrgPerson !=null) {
					// 如果关联的系统账号和此人名字和号码不相同 则去替换组织架构的姓名和手机号
					fdOrgPerson.setFdMobileNo(entryForm.getFdMobileNo());
					fdOrgPerson.setFdName(entryForm.getFdName());
					//部门
					fdOrgPerson.setFdParent(fdPlanEntryDept);
					//岗位
					fdOrgPerson.setFdPosts(fdOrgPosts);
					// 如果是生态的人员 确认到岗->转为内部人员
					if (fdOrgPerson.getFdIsExternal()) {
						updateOutToIn(fdOrgPerson, null, null);
					}
					sysOrgPersonId = fdOrgPersonId;
					personInfo = hrStaffPersonInfoService.findByOrgPersonId(fdOrgPersonId);
					addPerson = true;
					if (personInfo != null) {
						updatePerson =true;
					} else {
						personInfo = new HrStaffPersonInfo();
					}
					sysOrgPersonService.update(fdOrgPerson);
					entry.setFdOrgPerson(fdOrgPerson);
					personInfo.setFdOrgPerson(fdOrgPerson);
				} else {
					addPerson = true;
				}
			} else {

				// 不关联组织架构账号 填写账号密码
				fdOrgPerson = new SysOrgPerson();
				// 如果是生态的人员 确认到岗->转为内部人员
				//如果手机号码是外部人员的手机号
				SysOrgPerson fdPerson = isExternal(fdMobileNo);
				if (fdPerson.getFdIsExternal()) {
					updateOutToIn(fdPerson, fdLoginName, fdNewPassword);
				} else {
					fdOrgPerson.setFdNo(fdStaffNo);
					fdOrgPerson.setFdSex(entry.getFdSex());
					fdOrgPerson.setFdName(fdName);
					fdOrgPerson.setFdLoginName(fdLoginName);
					fdOrgPerson.setFdNewPassword(fdNewPassword);
					fdOrgPerson.setFdMobileNo(fdMobileNo);
					fdOrgPerson.setFdParent(fdPlanEntryDept);
					fdOrgPerson.setFdPosts(fdOrgPosts);
					fdOrgPerson.setFdEmail(entry.getFdEmail());
				}
				sysOrgPersonService.add(fdOrgPerson);
				entry.setFdOrgPerson(fdOrgPerson);
				sysOrgPersonId =fdOrgPerson.getFdId();
				addPerson =true;
			}
		} else {
			addPerson =true;
		}
		/**添加信息到人事档案*/
		if(addPerson){
			if(personInfo ==null){
				personInfo = new HrStaffPersonInfo();
			}
			if(StringUtil.isNotNull(sysOrgPersonId)){
				personInfo.setFdId(sysOrgPersonId);
			}
			personInfo = setPersonInfo(personInfo, entry);
			if(entry.getFdOrgPerson() !=null){
				personInfo.setFdOrgPerson(entry.getFdOrgPerson());
			}
			personInfo.setFdLoginName(fdLoginName); 
			//姓名
			if(StringUtil.isNotNull(fdName)) {
				personInfo.setFdName(fdName);
			}
			//手机号码
			personInfo.setFdMobileNo(fdMobileNo);
			//工号
			personInfo.setFdStaffNo(fdStaffNo);
			//状态
			personInfo.setFdStatus(entryForm.getFdPersonStatus());
			//待入职实体
			personInfo.setFdStaffEntry(entry);
			//入职时间
			personInfo.setFdEntryTime(fdEntryTime);
			//到本单位日期
			personInfo.setFdTimeOfEnterprise(fdEntryTime);
			//工作性质
			personInfo.setFdNatureWork(entryForm.getFdNatureWork());
			//地址本中的入职部门
			personInfo.setFdOrgParent(fdPlanEntryDept);
			//入职岗位
			personInfo.setFdOrgPosts(fdOrgPosts);
			personInfo.setFdPosts(fdOrgPosts);
			//从招聘信息中克隆附件
			clonHrApplicantAtt(personInfo,entry.getFdId());
			if(updatePerson){
				hrStaffPersonInfoService.update(personInfo);
				applicationContext.publishEvent(new HrStaffPersonInfoEvent(personInfo, new RequestContext(Plugin.currentRequest())));
			} else {
				hrStaffPersonInfoService.add(personInfo);
			}

			addOther(personInfo, entry);
		} 

		entry.setFdStaffNo(entryForm.getFdStaffNo());
		entry.setFdChecker(UserUtil.getUser());
		entry.setFdCheckDate(new Date());
		// 家庭信息
		List<HrStaffPersonFamily> list = entry.getFdStaffPersonFamilies();
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				HrStaffPersonFamily family = (HrStaffPersonFamily) list.get(i);
				family.setFdPersonInfo(personInfo);
			}
		}
		// 薪酬福利
		HrStaffEmolumentWelfare hrStaffEmolumentWelfare = entry
				.getHrStaffEmolumentWelfare();
		if (hrStaffEmolumentWelfare != null) {
			hrStaffEmolumentWelfare.setFdPersonInfo(personInfo);
		}
		update(entry);

	}

	/**
	 * 复制招聘申请里面的简历附件到人事档案
	 * 并且更新招聘模块人员状态为已入职
	 * @param hrStaffPersonInfoTemp
	 * @param fdId
	 * @throws Exception
	 */
	@Override
	public void clonHrApplicantAtt(HrStaffPersonInfo hrStaffPersonInfoTemp, String fdId) {
		if(hrStaffPersonInfoTemp !=null){
			try {
				//如果集成了招聘模块
				RequestContext requestContext = new RequestContext();
				requestContext.setParameter("fdApplicantId",fdId);
				requestContext.setParameter("type","getApplicatinAttachment");
				requestContext.setParameter("targetModelName",HrStaffPersonInfo.class.getName());
				requestContext.setAttribute("targetModel", hrStaffPersonInfoTemp);
				IXMLDataBean dataInfo = (IXMLDataBean) SpringBeanUtil.getBean("hrRecruitApplicantService");
				if (dataInfo != null) {
					List list = dataInfo.getDataList(requestContext);
					if (CollectionUtils.isNotEmpty(list)) {
						Object obj = list.get(0);
						if (obj != null) {
							Map attMap = (Map) obj;
							hrStaffPersonInfoTemp.getAttachmentForms().putAll(attMap);
						}
					}
				}
			} catch (Exception e) {
				logger.error("从招聘信息中获取附件错误："+ e.getMessage());
			}
		}
	}

	private SysOrgPerson isExternal(String fdMobileNo) throws Exception {
		SysOrgPerson fdPerson = new SysOrgPerson();
		HQLInfo hqlInfo = new HQLInfo();
		// 检查组织机构有没有相同的手机号
		hqlInfo.setWhereBlock(
				"sysOrgPerson.fdMobileNo = :fdMobileNo and sysOrgPerson.fdIsAvailable = " + HibernateUtil.toBooleanValueString(true));
		hqlInfo.setParameter("fdMobileNo", fdMobileNo);
		List<SysOrgPerson> lists = sysOrgPersonService.findList(hqlInfo);
		if ((lists != null) && (!lists.isEmpty()) && (lists.size() > 0)) {
			fdPerson = lists.get(0);
		}
		return fdPerson;
	}
	private void updateOutToIn(SysOrgPerson fdOrgPerson, String fdLoginName,
			String fdNewPassword) throws Exception {
		if (fdOrgPerson != null) {
			SysOrgPerson old = SysOrgEcoUtil.cloneEcoOrg(fdOrgPerson);
			fdOrgPerson.setFdIsExternal(false);
			fdOrgPerson.setFdIsAvailable(true);
			fdOrgPerson.setFdParent(null);
			if (StringUtil.isNotNull(fdLoginName)
					&& StringUtil.isNotNull(fdNewPassword)) {
				fdOrgPerson.setFdLoginName(fdLoginName);
				fdOrgPerson.setFdPassword(fdNewPassword);
			}
			SysOrgUtil.paraMethod.set(ResourceUtil.getString(
					"sys-organization:sysOrgElementExternal.outToIn"));
			try {
				SysOrgEcoUtil.addOrgModifyLog(old, fdOrgPerson, null, null,
						false);
			} catch (Exception e) {
				logger.error("确认到岗内转外失败：", e);
			}
			sysOrgPersonService.update(fdOrgPerson);
		}
	}
	private HrStaffTrackRecord createTrackRecord(HrStaffPersonInfo personInfo) {
		HrStaffTrackRecord modelObj = new HrStaffTrackRecord();
		Date currDate = new Date();
		modelObj.setFdPersonInfo(personInfo);
		Date fdEntranceBeginDate = personInfo.getFdEntryTime() != null
				? personInfo.getFdEntryTime() : currDate;
		modelObj.setFdEntranceBeginDate(fdEntranceBeginDate);
		modelObj.setFdStaffingLevel(personInfo.getFdStaffingLevel());
		modelObj.setFdType("1");
		modelObj.setFdStatus("1");
		modelObj.setFdCreateTime(currDate);
		return modelObj;
	}

	private void addTrackRecord(HrStaffEntryForm entryForm,
			HrStaffPersonInfo person) throws Exception {
		HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
		HrStaffTrackRecord track = null;
		if (null != person.getFdParent()
				&& "true".equals(syncSetting.getHrToEkpEnable())) {
			track = createTrackRecord(person);
			track.setFdHrOrgDept(person.getFdParent());
			if (!ArrayUtil.isEmpty(person.getFdPosts())) {
				track.setFdHrOrgPost(
						(HrOrganizationPost) person.getFdPosts().get(0));
			}
			hrStaffTrackRecordService.add(track);
		} else {
			if (person.getFdOrgParent() != null) {
				track = createTrackRecord(person);
				track.setFdRatifyDept(person.getFdOrgParent());
				List posts = new ArrayList();
				posts.addAll(person.getFdOrgPosts());
				track.setFdOrgPosts(posts);
				hrStaffTrackRecordService.add(track);
			}
		}
	}

	@Override
	public String checkUnique(RequestContext requestInfo) throws Exception {
		String fdId = requestInfo.getParameter("fdId");
		String isEntry = requestInfo.getParameter("isEntry");
		String fdOrgPersonId = requestInfo.getParameter("fdOrgPersonId");
		String mobileNo = requestInfo.getParameter("mobileNo");
		String staffNo = requestInfo.getParameter("staffNo");
		String loginName = requestInfo.getParameter("loginName");
		String result = "";
		if (StringUtil.isNotNull(mobileNo)) {
			if ("true".equals(isEntry) && StringUtil.isNotNull(fdOrgPersonId)) {
				result = checkMobileNoforEntry(fdId, mobileNo, fdOrgPersonId);
			} else {
			result = checkMobileNoUnique(fdId, mobileNo);
			}
		}
		if (StringUtil.isNotNull(staffNo)) {
			result = checkStaffNoUnique(fdId, staffNo);
		}
		if (StringUtil.isNotNull(loginName)) {
			result = checkLoaginName(fdId, loginName);
		}
		return result;
	}

	// 校验确认到岗关联组织架构账号的手机号唯一
	private String checkMobileNoforEntry(String fdId, String mobileNo,
			String fdOrgPersonId)
			throws Exception {
		String result = "";
		if (StringUtil.isNull(mobileNo)) {
			return result;
		}
		if (mobileNo.startsWith("x")) {
			mobileNo = mobileNo.replace("x", "+");
		}
		HQLInfo hqlInfo = new HQLInfo();
		// 检查组织机构有没有相同的手机号
		hqlInfo.setWhereBlock(
				"sysOrgPerson.fdMobileNo = :fdMobileNo and sysOrgPerson.fdIsAvailable = :fdIsAvailable");
		hqlInfo.setParameter("fdMobileNo", mobileNo);
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		List<SysOrgPerson> lists = sysOrgPersonService.findList(hqlInfo);
		if ((lists != null) && (!lists.isEmpty()) && (lists.size() > 0)) {
			result += lists.get(0).getFdLoginName();
			// 这个手机号的人员Id
			String entryFdId = lists.get(0).getFdId();
			// 关联的账号ID 与手机号的拥有者ID相同则放开校验
			if (entryFdId.equals(fdOrgPersonId)) {
				result = "";
			}
		}
		result = checkEntryMobile(result, mobileNo, fdId);
		return result;
	}

	private String checkLoaginName(String personInfoId, String loginName)
			throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock(
				"sysOrgPerson.fdLoginName= :fdLoginName and sysOrgPerson.fdIsAvailable=:fdIsAvailable");
		hql.setParameter("fdLoginName", loginName);
		hql.setParameter("fdIsAvailable", Boolean.TRUE);
		List<SysOrgPerson> lists = sysOrgPersonService.findList(hql);
		if (StringUtil.isNotNull(personInfoId)) {
			HrStaffPersonInfo personInfo = (HrStaffPersonInfo) hrStaffPersonInfoService
					.findByPrimaryKey(personInfoId);
			String loginAccount = personInfo.getFdLoginName();
			if(StringUtil.isNull(loginAccount)){
				return "true";
			}
			if (loginAccount.equals(loginName)) {
				return "true";
			}
		}
		if (lists.size() > 0) {
			return "";
		} else {
			return "true";
		}
	}

	private String checkMobileNoUnique(String fdId, String mobileNo)
			throws Exception {
		String result = "";
		if (StringUtil.isNull(mobileNo)) {
			return result;
		}
		if (mobileNo.startsWith("x")) {
			mobileNo = mobileNo.replace("x", "+");
		}
		HQLInfo hqlInfo = new HQLInfo();
		// 检查组织机构有没有相同的手机号
		hqlInfo.setWhereBlock(
				"sysOrgPerson.fdMobileNo = :fdMobileNo and sysOrgPerson.fdIsAvailable = :fdIsAvailable");
		hqlInfo.setParameter("fdMobileNo", mobileNo);
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		List<SysOrgPerson> lists = sysOrgPersonService.findList(hqlInfo);
		if ((lists != null) && (!lists.isEmpty()) && (lists.size() > 0)) {
			result += lists.get(0).getFdLoginName();
			// 如果是外部的人员 手机号校验过
			Boolean FdIsExternal = lists.get(0).getFdIsExternal();
			if (FdIsExternal) {
				result = "";
			}
		}
		// 检查招聘管理手机号
		result = checkEntryMobile(result, mobileNo, fdId);
		// 检查人事档案手机号
		result = checkHrStaffMobile(result, mobileNo, fdId);
		return result;
	}

	// 检查人事档案有没有相同的手机号
	private String checkHrStaffMobile(String result, String mobileNo,
			String fdId) throws Exception {
		if (StringUtil.isNull(result)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setParameter("fdMobileNo", mobileNo);
			hqlInfo.setParameter("fdId", fdId);
			hqlInfo.setWhereBlock(
					"hrStaffPersonInfo.fdMobileNo = :fdMobileNo and hrStaffPersonInfo.fdStaffEntry.fdId != :fdId");
			List<HrStaffPersonInfo> lists3 = hrStaffPersonInfoService
					.findList(hqlInfo);
			if ((lists3 != null) && (!lists3.isEmpty())
					&& (lists3.size() > 0)) {
				result += lists3.get(0).getFdName();
			}
		}
		return result;
	}
	// 检查hrStaffEntry 中有没有相同的手机号
	private String checkEntryMobile(String result, String mobileNo, String fdId)
			throws Exception {
		if (StringUtil.isNull(result)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setParameter("fdMobileNo", mobileNo);
			hqlInfo.setParameter("fdId", fdId);
			hqlInfo.setWhereBlock(
					"hrStaffEntry.fdMobileNo = :fdMobileNo and hrStaffEntry.fdId != :fdId");
			List<HrStaffEntry> lists2 = findList(hqlInfo);
			if ((lists2 != null) && (!lists2.isEmpty())
					&& (lists2.size() > 0)) {
				result += lists2.get(0).getFdName();
			}
		}
		return result;
	}
	private String checkStaffNoUnique(String fdId, String staffNo)
			throws Exception {
		String result = "";
		if (StringUtil.isNull(staffNo)) {
			return result;
		}
		HQLInfo hqlInfo = new HQLInfo();
		// 检查人事档案中有没有相同的工号
		hqlInfo.setWhereBlock(
				" hrStaffPersonInfo.fdStaffNo = :fdStaffNo and hrStaffPersonInfo.fdStaffEntry.fdId != :fdId");
		hqlInfo.setParameter("fdStaffNo", staffNo);
		hqlInfo.setParameter("fdId", fdId);
		List<HrStaffPersonInfo> lists = hrStaffPersonInfoService
				.findList(hqlInfo);
		if ((lists != null) && (!lists.isEmpty()) && (lists.size() > 0)) {
			result = lists.get(0).getFdStaffNo();
		}
		hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				" hrStaffEntry.fdStaffNo = :fdStaffNo and hrStaffEntry.fdId != :fdId and hrStaffEntry.fdStatus='1'");
		hqlInfo.setParameter("fdStaffNo", staffNo);
		hqlInfo.setParameter("fdId", fdId);
		List<HrStaffEntry> entryLists = findList(hqlInfo);
		if ((entryLists != null) && (!entryLists.isEmpty())
				&& (entryLists.size() > 0)) {
			result = entryLists.get(0).getFdStaffNo();
		}
		return result;
	}

	@Override
	public HSSFWorkbook buildTempletWorkBook() throws Exception {
		List<String> itemNodes = HrStaffImportUtil.getEntryItemNode();
		return HrStaffImportUtil.buildEntryTempletWorkBook(itemNodes);
	}

	@Override
	public KmssMessage saveImportData(HrStaffEntryForm entryForm)
			throws Exception {
		Workbook wb = null;
		Sheet sheet = null;
		InputStream inputStream = null;
		int count = 0;
		StringBuffer errorMsg = new StringBuffer();
		try {
			inputStream = entryForm.getFile().getInputStream();
			// 抽象类创建Workbook，适合excel 2003和2007以上
			wb = WorkbookFactory.create(inputStream);
			sheet = wb.getSheetAt(0);

			// 一份标准的模板，最少包含2行，第一行是字段名称，导入的数据应该是从第二行开始
			int rowNum = sheet.getLastRowNum();
			if (rowNum < 1) {
				throw new RuntimeException(ResourceUtil.getString(
						"hrStaff.import.empty", "hr-staff"));
			}
			if (sheet.getRow(0).getLastCellNum() != 5) {
				throw new RuntimeException(ResourceUtil.getString(
						"hrStaff.import.errFile", "hr-staff"));
			}
			String[] propertys = new String[5];
			propertys[0] = "fdName";
			propertys[1] = "fdMobileNo";
			propertys[2] = "fdPlanEntryDept";
			propertys[3] = "fdPlanEntryTime";
			propertys[4] = "fdOrgPosts";
			KmssMessages messages = null;
			// 从第二行开始取数据
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				HrStaffEntry entry = new HrStaffEntry();
				entry.setFdStatus("1");
				entry.setFdDataFrom("1");
				messages = new KmssMessages();
				Row row = sheet.getRow(i);
				if (row == null) { // 跳过空行
					continue;
				}
				// 获取列数
				int cellNum = row.getLastCellNum();
				for (int j = 0; j < cellNum; j++) {
					String value = ImportUtil.getCellValue(row.getCell(j));
					if (StringUtil.isNull(value) && j < cellNum - 1) {
						messages.addError(new KmssMessage(ResourceUtil.getString(
								"hrStaff.import.error.notNull", "hr-staff", null,
								ResourceUtil.getString(
										"hr-staff:hrStaffEntry." + propertys[j]))));
						continue;
					}
					if (j == 0) {
                        BeanUtils.setProperty(entry, propertys[j], value);
                    }
					if (j == 1) {

						if (!PhoneUtil.checkFormat(value)) {
							String tip = getMobileErrResult(value);
							messages.addError(new KmssMessage(tip));
							continue;
						}

						String result = checkMobileNoUnique(entry.getFdId(), value);
						if (StringUtil.isNull(result)) {
							BeanUtils.setProperty(entry, propertys[j], value);
						} else {
							messages.addError(new KmssMessage(ResourceUtil
									.getString("hrStaffEntry.fdMobileNo.import.tip",
											"hr-staff")));
						}
					}
					if (j == 2) {
						SysOrgElement fdParent = sysOrgImportService
								.getSysOrgElementByName(value, ORG_TYPE_ORG,
										ORG_TYPE_DEPT);
						if (fdParent != null) {
							BeanUtils.setProperty(entry, propertys[j], fdParent);
						} else {
							messages.addError(
									new KmssMessage(addNotExistMsg(
											ResourceUtil.getString(
													"hr-staff:hrStaffEntry."
															+ propertys[j]),
											value)));
						}
					}
					if (j == 3) {
						Date date = null;
						try {
							date = DateUtil.convertStringToDate(value,
									DateUtil.TYPE_DATE, null);
						} catch (Exception e) {

						}
						if (date != null) {
							BeanUtils.setProperty(entry, propertys[j], date);
						} else {
							StringBuilder title = new StringBuilder(ResourceUtil.getString("hr-staff:hrStaffEntry.fdPlanEntryTime"))
									.append("[")
									.append(value)
									.append("]")
									.append(ResourceUtil.getString("calendar.msg.timeFormat.error"))
									.append(", ");

							messages.addError(new KmssMessage(title.toString()));
						}
					}
					if (j == 4 && StringUtil.isNotNull(value)) {
						List<SysOrgElement> posts = new ArrayList<SysOrgElement>();
						String[] postNames = value.split(";");
						StringBuffer error = new StringBuffer();
						for (String postName : postNames) {
							SysOrgElement _post = sysOrgImportService
									.getSysOrgElementByName(postName,
											ORG_TYPE_POST);
							if (_post != null) {
								posts.add(_post);
							} else {
								error.append(",").append(postName);
							}
						}
						if (error.length() > 0) {
							error.deleteCharAt(0);
							messages.addError(
									new KmssMessage(addNotExistMsg(
											ResourceUtil.getString(
													"hr-staff:hrStaffEntry."
															+ propertys[j]),
											error.toString())));
						} else {
							BeanUtils.setProperty(entry, propertys[j], posts);
						}
					}
				}
				// 如果有错误，就不进行导入
				if (!messages.hasError()) {
					super.add(entry);
					count++;
				} else {
					errorMsg.append(ResourceUtil.getString(
							"hrStaff.import.error.num", "hr-staff", null, i));
					// 解析错误信息
					for (KmssMessage message : messages.getMessages()) {
						errorMsg.append(message.getMessageKey());
					}
					errorMsg.append("<br>");
				}
			}
			KmssMessage message = null;
			if (errorMsg.length() > 0) {
				errorMsg.insert(0, ResourceUtil.getString(
						"hrStaff.import.portion.failed", "hr-staff", null, count)
						+ "<br>");
				message = new KmssMessage(errorMsg.toString());
				message.setMessageType(KmssMessage.MESSAGE_ERROR);
			} else {
				message = new KmssMessage(ResourceUtil.getString(
						"hrStaff.import.success", "hr-staff", null, count));
				message.setMessageType(KmssMessage.MESSAGE_COMMON);
			}
			return message;
		} catch (Exception e) {
			throw new RuntimeException(ResourceUtil.getString(
					"hrStaff.import.error", "hr-staff"));
		} finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(inputStream);
		}

	}
	private  String getMobileErrResult(String  value){
		String tip = ResourceUtil.getString("hr-staff:hrStaffEntry.fdMobileNo.import.tip2");
		tip = tip.replace("%s", value);
		return tip;
		
	}

	/**
	 * 记录不存在
	 * @param field
	 * @param value
	 * @return
	 */
	private String addNotExistMsg(String field,
			String value) {
		StringBuffer sb = new StringBuffer();
		sb.append(field).append("[").append(value).append("]")
				.append(ResourceUtil
						.getString("sys-profile:sys.profile.orgImport.error7"));
		return sb.toString();
	}

	@Override
	public WorkBook export(List<String> ids, String fdStatus) throws Exception {
		String[] baseColumns = null;
		String fileName = "";
		if ("1".equals(fdStatus)) {
			baseColumns = new String[] { getStr("hrStaffEntry.fdName"),
					getStr("hrStaffEntry.fdPlanEntryDept"),
					getStr("hrStaffEntry.fdOrgPosts"),
					getStr("hrStaffEntry.fdPlanEntryTime"),
					getStr("hrStaffEntry.fdMobileNo"),
					getStr("hrStaffEntry.fdEmail"),
					getStr("hrStaffEntry.fdQRStatus"),
					getStr("hrStaffEntry.docCreator") };
			fileName = "待入职人员信息";
		} else if ("2".equals(fdStatus)) {
			baseColumns = new String[] { getStr("hrStaffEntry.fdName"),
					getStr("hrStaffEntry.fdStaffNo"),
					ResourceUtil.getString("hrRatifyEntry.fdEntryDept", "hr-ratify"),
					ResourceUtil.getString("hrRatifyEntry.fdEntryPosts", "hr-ratify"),
					getStr("hrStaffPersonInfo.workStatus"),
					getStr("hrStaffPersonInfo.fdEntryTime"),
					getStr("hrStaffEntry.fdMobileNo"),
					getStr("hrStaffEntry.fdEmail") };
			fileName = "最近入职人员信息";
		} else {
			baseColumns = new String[] { getStr("hrStaffEntry.fdName"),
					getStr("hrStaffEntry.fdPlanEntryDept"),
					getStr("hrStaffEntry.fdOrgPosts"),
					getStr("hrStaffEntry.fdPlanEntryTime"),
					getStr("hrStaffEntry.fdMobileNo"),
					getStr("hrStaffEntry.fdEmail"),
					getStr("hrStaffEntry.fdAbandonReason"),
					getStr("hrStaffEntry.fdAbandonRemark"),
					getStr("hrStaffEntry.docCreator"),
					getStr("hrStaffEntry.docCreateTime") };
			fileName = "已放弃入职人员信息";
		}
		WorkBook wb = new WorkBook();
		com.landray.kmss.util.excel.Sheet sheet = new com.landray.kmss.util.excel.Sheet();
		sheet.setTitle(fileName);
		for (int i = 0; i < baseColumns.length; i++) {
			Column col = new Column();
			col.setTitle(baseColumns[i]);
			sheet.addColumn(col);
		}
		List contentList = new ArrayList();
		Object[] objs = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				StringUtil.linkString(HQLUtil.buildLogicIN("hrStaffEntry", ids),
						" and ", "hrStaffEntry.fdStatus=:fdStatus"));
		hqlInfo.setParameter("fdStatus", fdStatus);
		List<HrStaffEntry> list = findList(hqlInfo);
		for (HrStaffEntry entry : list) {
			objs = new Object[sheet.getColumnList().size()];
			if ("1".equals(fdStatus)) {
				objs[0] = entry.getFdName();
				objs[1] = entry.getFdPlanEntryDept()==null?"":entry.getFdPlanEntryDept().getFdName();
				objs[2] = ArrayUtil.joinProperty(entry.getFdOrgPosts(),
						"fdName", ";")[0];
				objs[3] = DateUtil.convertDateToString(
						entry.getFdPlanEntryTime(), DateUtil.TYPE_DATE, null);
				objs[4] = entry.getFdMobileNo();
				objs[5] = entry.getFdEmail();
				objs[6] = entry.getFdQRStatus() != null
						&& entry.getFdQRStatus().booleanValue() ? "是" : "否";
				objs[7] = entry.getDocCreator().getFdName();
			} else if ("2".equals(fdStatus)) {
				objs[0] = entry.getFdName();
				objs[1] = entry.getFdStaffNo();
				objs[2] = entry.getFdPlanEntryDept()==null?"":entry.getFdPlanEntryDept().getFdName();
				objs[3] = ArrayUtil.joinProperty(entry.getFdOrgPosts(),
						"fdName", ";")[0];
				HrStaffPersonInfo info = hrStaffPersonInfoService
						.findByStaffEntryId(entry.getFdId());
				if (info != null) {
					objs[4] = EnumerationTypeUtil.getColumnEnumsLabel(
							"hrStaffPersonInfo_fdStatus", info.getFdStatus());
					objs[5] = DateUtil.convertDateToString(
							info.getFdEntryTime(),
							DateUtil.TYPE_DATE, null);
				} else {
					objs[4] = null;
					objs[5] = null;
				}
				objs[6] = entry.getFdMobileNo();
				objs[7] = entry.getFdEmail();
			} else {
				objs[0] = entry.getFdName();
				objs[1] = entry.getFdPlanEntryDept()==null?"":entry.getFdPlanEntryDept().getFdName();
				objs[2] = ArrayUtil.joinProperty(entry.getFdOrgPosts(),
						"fdName", ";")[0];
				objs[3] = DateUtil.convertDateToString(
						entry.getFdPlanEntryTime(), DateUtil.TYPE_DATE, null);
				objs[4] = entry.getFdMobileNo();
				objs[5] = entry.getFdEmail();
				objs[6] = entry.getFdAbandonReason();
				objs[7] = entry.getFdAbandonRemark();
				objs[8] = entry.getDocCreator().getFdName();
				objs[9] = DateUtil.convertDateToString(entry.getDocCreateTime(),
						DateUtil.TYPE_DATETIME, null);
			}
			contentList.add(objs);
		}
		sheet.setContentList(contentList);
		wb.addSheet(sheet);
		wb.setFilename(fileName);
		return wb;
	}

	private String getStr(String key) {
		return ResourceUtil.getString(key, "hr-staff");
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		this.applicationContext.publishEvent(new HrStaffEntryEvent(
				(HrStaffEntry) modelObj, new RequestContext()));
		super.delete(modelObj);
	}

	@Override
	public Long getCountByDept(String deptId) throws Exception {
		if (StringUtil.isNotNull(deptId)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setGettingCount(true);
			StringBuffer whereBlock = null;
			whereBlock = new StringBuffer(
					"hrStaffEntry.fdPlanEntryDept.fdId=:id");
			hqlInfo.setParameter("id", deptId);
			hqlInfo.setWhereBlock(whereBlock.toString());
			List<Long> entry = findValue(hqlInfo);
			return entry.get(0);
		}
		return null;
	}
	@Override
	public String getPersonInfo(String fdId) throws Exception {
		SysOrgPerson fdEntryPerson = (SysOrgPerson) sysOrgPersonService
				.findByPrimaryKey(fdId);
		String MobileNo = fdEntryPerson.getFdMobileNo();
		return MobileNo;
	}
}
