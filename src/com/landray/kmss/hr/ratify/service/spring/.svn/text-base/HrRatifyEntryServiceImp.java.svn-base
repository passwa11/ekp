package com.landray.kmss.hr.ratify.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.ICheckUniqueBean;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.hr.ratify.model.HrRatifyCertifi;
import com.landray.kmss.hr.ratify.model.HrRatifyEduExp;
import com.landray.kmss.hr.ratify.model.HrRatifyEntry;
import com.landray.kmss.hr.ratify.model.HrRatifyHistory;
import com.landray.kmss.hr.ratify.model.HrRatifyMKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.hr.ratify.model.HrRatifyRewPuni;
import com.landray.kmss.hr.ratify.model.HrRatifyTKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifyEntryService;
import com.landray.kmss.hr.ratify.util.HrRatifyTitleUtil;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.hr.staff.event.HrStaffPersonInfoEvent;
import com.landray.kmss.hr.staff.model.HrStaffCertifi;
import com.landray.kmss.hr.staff.model.HrStaffEduExp;
import com.landray.kmss.hr.staff.model.HrStaffEmolumentWelfare;
import com.landray.kmss.hr.staff.model.HrStaffEntry;
import com.landray.kmss.hr.staff.model.HrStaffHistory;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceBonusMalus;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceEducation;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceQualification;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceWork;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoLog;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoSettingNew;
import com.landray.kmss.hr.staff.model.HrStaffRatifyLog;
import com.landray.kmss.hr.staff.model.HrStaffRewPuni;
import com.landray.kmss.hr.staff.model.HrStaffTrackRecord;
import com.landray.kmss.hr.staff.service.IHrStaffEmolumentWelfareService;
import com.landray.kmss.hr.staff.service.IHrStaffEntryService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceBonusMalusService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceEducationService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceQualificationService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceTrainingService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceWorkService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.archives.config.SysArchivesConfig;
import com.landray.kmss.sys.archives.interfaces.IArchFileDataService;
import com.landray.kmss.sys.archives.model.SysArchivesFileTemplate;
import com.landray.kmss.sys.archives.model.SysArchivesParamModel;
import com.landray.kmss.sys.archives.service.ISysArchivesFileTemplateService;
import com.landray.kmss.sys.archives.util.SysArchivesUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowDiscard;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowFinish;
import com.landray.kmss.sys.xform.interfaces.XFormUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HibernateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HrRatifyEntryServiceImp extends HrRatifyMainServiceImp
		implements IHrRatifyEntryService, ICheckUniqueBean,
		ApplicationContextAware, IArchFileDataService {
	protected final Logger logger = org.slf4j.LoggerFactory
			.getLogger(HrRatifyEntryServiceImp.class);

	public ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	private IHrStaffEntryService hrStaffEntryService;
	private IHrStaffPersonInfoService hrStaffPersonInfoService;
	@Override
    public void
			setHrStaffEntryService(IHrStaffEntryService hrStaffEntryService) {
		this.hrStaffEntryService = hrStaffEntryService;
	}

	@Override
	public IHrStaffEntryService getHrStaffEntryService() {
		return hrStaffEntryService;
	}

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

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

	@Override
    public IHrStaffPersonInfoService getHrStaffPersonInfoService() {
		return hrStaffPersonInfoService;
	}

	@Override
    public void setHrStaffPersonInfoService(
			IHrStaffPersonInfoService hrStaffPersonInfoService) {
		this.hrStaffPersonInfoService = hrStaffPersonInfoService;
	}

	private IHrStaffPersonExperienceTrainingService hrStaffPersonExperienceTrainingService;

	public void setHrStaffPersonExperienceTrainingService(
			IHrStaffPersonExperienceTrainingService hrStaffPersonExperienceTrainingService) {
		this.hrStaffPersonExperienceTrainingService = hrStaffPersonExperienceTrainingService;
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

	private IHrStaffEmolumentWelfareService hrStaffEmolumentWelfareService;

	public void setHrStaffEmolumentWelfareService(
			IHrStaffEmolumentWelfareService hrStaffEmolumentWelfareService) {
		this.hrStaffEmolumentWelfareService = hrStaffEmolumentWelfareService;
	}

	/*private IKmArchivesFileTemplateService kmArchivesFileTemplateService;

	public void setKmArchivesFileTemplateService(
			IKmArchivesFileTemplateService kmArchivesFileTemplateService) {
		this.kmArchivesFileTemplateService = kmArchivesFileTemplateService;
	}*/

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrRatifyEntry hrRatifyEntry = new HrRatifyEntry();
		hrRatifyEntry.setDocCreateTime(new Date());
		hrRatifyEntry.setDocCreator(UserUtil.getUser());
		hrRatifyEntry.setFdDepartment(UserUtil.getUser().getFdParent());
		String templateId = requestContext.getParameter("i.docTemplate");
		if (com.landray.kmss.util.StringUtil.isNotNull(templateId)) {
			com.landray.kmss.hr.ratify.model.HrRatifyTemplate docTemplate = (com.landray.kmss.hr.ratify.model.HrRatifyTemplate) getHrRatifyTemplateService()
					.findByPrimaryKey(templateId);
			if (docTemplate != null) {
				hrRatifyEntry.setDocTemplate(docTemplate);
				List<HrRatifyTKeyword> keyWordList = docTemplate
						.getDocKeyword();
				List tKeyword = new ArrayList();
				for (HrRatifyTKeyword keyWord : keyWordList) {
					HrRatifyMKeyword mKeyword = new HrRatifyMKeyword();
					mKeyword.setDocKeyword(keyWord.getDocKeyword());
					tKeyword.add(mKeyword);
				}
				hrRatifyEntry.setDocKeyword(tKeyword);
			}
		}
        HrRatifyUtil.initModelFromRequest(hrRatifyEntry, requestContext);
		if (hrRatifyEntry.getDocTemplate() != null) {
			hrRatifyEntry.setExtendFilePath(
					XFormUtil.getFileName(hrRatifyEntry.getDocTemplate(),
							hrRatifyEntry.getDocTemplate().getFdTempKey()));
			if (Boolean.FALSE
					.equals(hrRatifyEntry.getDocTemplate().getDocUseXform())) {
				hrRatifyEntry.setDocXform(
						hrRatifyEntry.getDocTemplate().getDocXform());
			}
			hrRatifyEntry.setDocUseXform(
					hrRatifyEntry.getDocTemplate().getDocUseXform());
		}
		String fdEntryId = requestContext.getParameter("fdEntryId");
		if (StringUtil.isNotNull(fdEntryId)) {
			HrStaffEntry hrStaffEntry = (HrStaffEntry) hrStaffEntryService
					.findByPrimaryKey(fdEntryId);
			hrRatifyEntry.setFdRecruitEntryId(fdEntryId);
			List<SysOrgPost> fdEntryPosts = new ArrayList<SysOrgPost>();
			fdEntryPosts.addAll(hrStaffEntry.getFdOrgPosts());
			hrRatifyEntry.setFdEntryPosts(fdEntryPosts);
			hrRatifyEntry.setFdStaffNo(hrStaffEntry.getFdStaffNo());
			hrRatifyEntry.setFdEntryName(hrStaffEntry.getFdName());
			hrRatifyEntry
					.setFdNameUsedBefore(hrStaffEntry.getFdNameUsedBefore());
			hrRatifyEntry.setFdSex(hrStaffEntry.getFdSex());
			hrRatifyEntry.setFdDateOfBirth(hrStaffEntry.getFdDateOfBirth());
			hrRatifyEntry.setFdNativePlace(hrStaffEntry.getFdNativePlace());
			hrRatifyEntry.setFdMaritalStatus(hrStaffEntry.getFdMaritalStatus());
			hrRatifyEntry.setFdNation(hrStaffEntry.getFdNation());
			hrRatifyEntry.setFdPoliticalLandscape(
					hrStaffEntry.getFdPoliticalLandscape());
			hrRatifyEntry.setFdHealth(hrStaffEntry.getFdHealth());
			hrRatifyEntry.setFdLivingPlace(hrStaffEntry.getFdLivingPlace());
			hrRatifyEntry.setFdIdCard(hrStaffEntry.getFdIdCard());
			hrRatifyEntry.setFdHighestDegree(hrStaffEntry.getFdHighestDegree());
			hrRatifyEntry.setFdHighestEducation(
					hrStaffEntry.getFdHighestEducation());
			hrRatifyEntry.setFdProfession(hrStaffEntry.getFdProfession());
			hrRatifyEntry.setFdWorkTime(hrStaffEntry.getFdWorkTime());
			hrRatifyEntry.setFdDateOfGroup(hrStaffEntry.getFdDateOfGroup());
			hrRatifyEntry.setFdDateOfParty(hrStaffEntry.getFdDateOfParty());
			hrRatifyEntry.setFdStature(hrStaffEntry.getFdStature());
			hrRatifyEntry.setFdWeight(hrStaffEntry.getFdWeight());
			hrRatifyEntry.setFdHomeplace(hrStaffEntry.getFdHomeplace());
			hrRatifyEntry.setFdAccountProperties(
					hrStaffEntry.getFdAccountProperties());
			hrRatifyEntry.setFdRegisteredResidence(
					hrStaffEntry.getFdRegisteredResidence());
			hrRatifyEntry.setFdResidencePoliceStation(
					hrStaffEntry.getFdResidencePoliceStation());
			hrRatifyEntry.setFdEmail(hrStaffEntry.getFdEmail());
			hrRatifyEntry.setFdMobileNo(hrStaffEntry.getFdMobileNo());
			hrRatifyEntry.setFdEmergencyContact(
					hrStaffEntry.getFdEmergencyContact());
			hrRatifyEntry.setFdEmergencyContactPhone(
					hrStaffEntry.getFdEmergencyContactPhone());
			hrRatifyEntry.setFdOtherContact(hrStaffEntry.getFdOtherContact());
			hrRatifyEntry.setFdEntryDate(hrStaffEntry.getFdPlanEntryTime());
			hrRatifyEntry.setFdEntryDept(hrStaffEntry.getFdPlanEntryDept());
			List<HrRatifyHistory> fdHistory = new ArrayList<HrRatifyHistory>();
			List<HrStaffHistory> sFdHistotry = hrStaffEntry.getFdHistory();
			for (HrStaffHistory sh : sFdHistotry) {
				HrRatifyHistory history = new HrRatifyHistory();
				history.setFdDesc(sh.getFdDesc());
				history.setFdEndDate(sh.getFdEndDate());
				history.setFdLeaveReason(sh.getFdLeaveReason());
				history.setFdName(sh.getFdName());
				history.setFdPost(sh.getFdPost());
				history.setFdStartDate(sh.getFdStartDate());
				history.setDocIndex(sh.getDocIndex());
				history.setDocMain(hrRatifyEntry);
				fdHistory.add(history);
			}
			hrRatifyEntry.setFdHistory(fdHistory);
			List<HrRatifyEduExp> fdEducations = new ArrayList<HrRatifyEduExp>();
			List<HrStaffEduExp> sFdEducations = hrStaffEntry.getFdEducations();
			for (HrStaffEduExp se : sFdEducations) {
				HrRatifyEduExp eduExp = new HrRatifyEduExp();
				eduExp.setFdAcadeRecord(se.getFdAcadeRecord());
				eduExp.setFdEntranceDate(se.getFdEntranceDate());
				eduExp.setFdGraduationDate(se.getFdGraduationDate());
				eduExp.setFdMajor(se.getFdMajor());
				eduExp.setFdName(se.getFdName());
				eduExp.setFdRemark(se.getFdRemark());
				eduExp.setDocIndex(se.getDocIndex());
				eduExp.setDocMain(hrRatifyEntry);
				fdEducations.add(eduExp);
			}
			hrRatifyEntry.setFdEducations(fdEducations);
			List<HrRatifyCertifi> fdCertificate = new ArrayList<HrRatifyCertifi>();
			List<HrStaffCertifi> sFdCertificate = hrStaffEntry
					.getFdCertificate();
			for (HrStaffCertifi sc : sFdCertificate) {
				HrRatifyCertifi cert = new HrRatifyCertifi();
				cert.setFdInvalidDate(sc.getFdInvalidDate());
				cert.setFdIssueDate(sc.getFdIssueDate());
				cert.setFdIssuingUnit(sc.getFdIssuingUnit());
				cert.setFdName(sc.getFdName());
				cert.setFdRemark(sc.getFdRemark());
				cert.setDocIndex(sc.getDocIndex());
				cert.setDocMain(hrRatifyEntry);
				fdCertificate.add(cert);
			}
			hrRatifyEntry.setFdCertificate(fdCertificate);
			List<HrRatifyRewPuni> fdRewardsPunishments = new ArrayList<HrRatifyRewPuni>();
			List<HrStaffRewPuni> sFdRewardsPunishments = hrStaffEntry
					.getFdRewardsPunishments();
			for (HrStaffRewPuni sp : sFdRewardsPunishments) {
				HrRatifyRewPuni rewPuni = new HrRatifyRewPuni();
				rewPuni.setFdDate(sp.getFdDate());
				rewPuni.setFdName(sp.getFdName());
				rewPuni.setFdRemark(sp.getFdRemark());
				rewPuni.setDocIndex(sp.getDocIndex());
				rewPuni.setDocMain(hrRatifyEntry);
				fdRewardsPunishments.add(rewPuni);
			}
			hrRatifyEntry.setFdRewardsPunishments(fdRewardsPunishments);
		}
        return hrRatifyEntry;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrRatifyEntry hrRatifyEntry = (HrRatifyEntry) model;
		if (hrRatifyEntry.getDocTemplate() != null) {
			dispatchCoreService.initFormSetting(form,
					hrRatifyEntry.getDocTemplate().getFdTempKey(),
					hrRatifyEntry.getDocTemplate(),
					hrRatifyEntry.getDocTemplate().getFdTempKey(),
					requestContext);
		}
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public void setDocNumber(HrRatifyMain mainModel) throws Exception {
		HrRatifyEntry hrRatifyEntry = (HrRatifyEntry) mainModel;
		if (!"10".equals(hrRatifyEntry.getDocStatus())) {
			String docNumber = getSysNumberFlowService()
					.generateFlowNumber(hrRatifyEntry);
			hrRatifyEntry.setDocNumber(docNumber);
		}
	}

	private HrStaffPersonInfo modifyPersonInfo(HrRatifyEntry entry,
			HrStaffPersonInfo personInfo) throws Exception {
		personInfo.setFdName(entry.getFdEntryName());
		personInfo.setFdOrgParent(entry.getFdEntryDept());
		List<SysOrgPost> fdOrgPosts = new ArrayList<>();
		fdOrgPosts.addAll(entry.getFdEntryPosts());
		personInfo.setFdOrgPosts(fdOrgPosts);
		personInfo.setFdStaffingLevel(entry.getFdEntryLevel());
		personInfo.setFdNameUsedBefore(
				entry.getFdNameUsedBefore());
		personInfo.setFdSex(entry.getFdSex());
		personInfo.setFdDateOfBirth(entry.getFdDateOfBirth());
		personInfo.setFdNativePlace(entry.getFdNativePlace());
		HrStaffPersonInfoSettingNew fdMaritalStatus = entry
				.getFdMaritalStatus();
		if (fdMaritalStatus != null) {
			personInfo.setFdMaritalStatus(
					fdMaritalStatus.getFdName());
		}
		HrStaffPersonInfoSettingNew fdNation = entry
				.getFdNation();
		if (fdNation != null) {
			personInfo.setFdNation(fdNation.getFdName());
		}
		HrStaffPersonInfoSettingNew fdPoliticalLandscape = entry
				.getFdPoliticalLandscape();
		if (fdPoliticalLandscape != null) {
			personInfo.setFdPoliticalLandscape(
					fdPoliticalLandscape.getFdName());
		}
		HrStaffPersonInfoSettingNew fdHealth = entry
				.getFdHealth();
		if (fdHealth != null) {
			personInfo.setFdHealth(fdHealth.getFdName());
		}
		personInfo.setFdLivingPlace(entry.getFdLivingPlace());
		personInfo.setFdIdCard(entry.getFdIdCard());
		HrStaffPersonInfoSettingNew fdHighestDegree = entry
				.getFdHighestDegree();
		if (fdHighestDegree != null) {
			personInfo.setFdHighestDegree(
					fdHighestDegree.getFdName());
		}
		HrStaffPersonInfoSettingNew fdHighestEducation = entry
				.getFdHighestEducation();
		if (fdHighestEducation != null) {
			personInfo.setFdHighestEducation(
					fdHighestEducation.getFdName());
		}
		personInfo.setFdWorkTime(entry.getFdWorkTime());
		personInfo.setFdDateOfGroup(entry.getFdDateOfGroup());
		personInfo.setFdDateOfParty(entry.getFdDateOfParty());
		personInfo.setFdStature(entry.getFdStature());
		personInfo.setFdWeight(entry.getFdWeight());
		personInfo.setFdHomeplace(entry.getFdHomeplace());
		personInfo.setFdAccountProperties(
				entry.getFdAccountProperties());
		personInfo.setFdRegisteredResidence(
				entry.getFdRegisteredResidence());
		personInfo.setFdResidencePoliceStation(
				entry.getFdResidencePoliceStation());
		personInfo.setFdEmail(entry.getFdEmail());
		personInfo.setFdMobileNo(entry.getFdMobileNo());
		personInfo.setFdEmergencyContact(
				entry.getFdEmergencyContact());
		personInfo.setFdEmergencyContactPhone(
				entry.getFdEmergencyContactPhone());
		personInfo.setFdOtherContact(entry.getFdOtherContact());
		personInfo.setFdStatus(entry.getFdEntryStatus());
		personInfo.setFdStaffNo(entry.getFdStaffNo());
		personInfo
				.setFdTimeOfEnterprise(entry.getFdEntryDate());
		personInfo.setFdEntryTime(entry.getFdEntryDate());
		HrStaffPersonInfoSettingNew fdEntryStaffType = entry
				.getFdEntryStaffType();
		if (fdEntryStaffType != null) {
			personInfo.setFdStaffType(fdEntryStaffType.getFdName());
		}
		personInfo.setFdTrialOperationPeriod(
				entry.getFdEntryTrialPeriod());
		personInfo.setFdOfficeLocation(
				entry.getFdEntryWorkAddress());
		personInfo.setFdTrialExpirationTime(
				entry.getFdEntryPeriodDate());
		String fdRecruitEntryId = entry.getFdRecruitEntryId();
		if (StringUtil.isNotNull(fdRecruitEntryId)) {
			HrStaffEntry fdStaffEntry = (HrStaffEntry) hrStaffEntryService
					.findByPrimaryKey(fdRecruitEntryId);
			personInfo.setFdStaffEntry(fdStaffEntry);
		}
		//getHrStaffEntryService().cl
		return personInfo;
	}

	@Override
	public void addOrgPerson(HrRatifyEntry entry) throws Exception {
		logger.info("开始了...");
		SysOrgPerson person = new SysOrgPerson();
		SysOrgPerson fdOrgPerson = new SysOrgPerson();
		String fdOrgId = "";
		String fdMobileNo = entry.getFdMobileNo();
		Boolean isExternal = isExternalMobile(fdMobileNo);
		logger.info("判断1开始了...");
		// 外部人员转成内部
		if (isExternal) {
			fdOrgPerson = isExternal(fdMobileNo);
			fdOrgPerson.setFdIsExternal(false);
			fdOrgPerson.setFdOrgType(8);
			fdOrgPerson.setFdName(entry.getFdEntryName());
			fdOrgPerson.setFdLoginName(entry.getFdLoginName());
			fdOrgPerson.setFdPassword(entry.getFdPassword());
			fdOrgPerson.setFdParent(entry.getFdEntryDept());
			fdOrgPerson.setFdPosts(entry.getFdEntryPosts());
			fdOrgPerson.setFdNo(entry.getFdNo());
			fdOrgPerson.setFdStaffingLevel(entry.getFdEntryLevel());
			fdOrgPerson.setFdSex(entry.getFdSex());
			fdOrgPerson.setFdEmail(entry.getFdEmail());
			fdOrgId = fdOrgPerson.getFdId();
			SysOrgPerson old = SysOrgEcoUtil.cloneEcoOrg(fdOrgPerson);
			SysOrgUtil.paraMethod.set(ResourceUtil.getString(
					"sys-organization:sysOrgElementExternal.outToIn"));
			getSysOrgPersonService().update(fdOrgPerson);
		} else {
			person.setFdOrgType(8);
			person.setFdName(entry.getFdEntryName());
			person.setFdLoginName(entry.getFdLoginName());
			person.setFdNewPassword(entry.getFdPassword());
			person.setFdNo(entry.getFdNo());
			person.setFdParent(entry.getFdEntryDept());
			person.setFdPosts(entry.getFdEntryPosts());
			person.setFdStaffingLevel(entry.getFdEntryLevel());
			person.setFdSex(entry.getFdSex());
			person.setFdMobileNo(entry.getFdMobileNo());
			person.setFdEmail(entry.getFdEmail());
			fdOrgId = getSysOrgPersonService().add(person);
		}
		logger.info("判断1结束了...");
		HrStaffPersonInfo personInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService()
				.findByPrimaryKey(fdOrgId, HrStaffPersonInfo.class, true);
		logger.info("判断2开始了...");
		if (personInfo == null) {
			personInfo = new HrStaffPersonInfo();
			personInfo.setFdId(fdOrgId);
			if (isExternal) {
				personInfo.setFdOrgPerson(fdOrgPerson);
			} else {
				personInfo.setFdOrgPerson(person);
			}
			personInfo.setFdStatus("trial");
			personInfo = modifyPersonInfo(entry, personInfo);
			//新增人员的时候，判断该人员来源是招聘信息 则把简历附件加上
			if(StringUtil.isNotNull(entry.getFdRecruitEntryId())) {
				hrStaffEntryService.clonHrApplicantAtt(personInfo, entry.getFdRecruitEntryId());
			}
			getHrStaffPersonInfoService().add(personInfo);
			List<HrRatifyHistory> fdHistory = entry.getFdHistory();
			logger.info("判断3开始了...");
			for (HrRatifyHistory history : fdHistory) {
				HrStaffPersonExperienceWork work = new HrStaffPersonExperienceWork();
				work.setFdCompany(history.getFdName());
				work.setFdPosition(history.getFdPost());
				work.setFdDescription(history.getFdDesc());
				work.setFdReasons(history.getFdLeaveReason());
				work.setFdBeginDate(history.getFdStartDate());
				work.setFdEndDate(history.getFdEndDate());
				work.setFdCreateTime(new Date());
				work.setFdRelatedProcess(
						HrRatifyUtil.getUrl(entry));
				work.setFdCreator(UserUtil.getUser());
				work.setFdPersonInfo(personInfo);
				hrStaffPersonExperienceWorkService.add(work);
			}
			logger.info("判断3结束了...");
			List<HrRatifyEduExp> fdEducations = entry
					.getFdEducations();
			logger.info("判断4开始了...");
			for (HrRatifyEduExp eduExp : fdEducations) {
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
				education.setFdRelatedProcess(
						HrRatifyUtil.getUrl(entry));
				education.setFdCreator(UserUtil.getUser());
				education.setFdPersonInfo(personInfo);
				hrStaffPersonExperienceEducationService
						.add(education);
			}
			logger.info("判断4结束了...");
			List<HrRatifyCertifi> certifi = entry
					.getFdCertificate();
			logger.info("判断5开始了...");
			for (HrRatifyCertifi cert : certifi) {
				HrStaffPersonExperienceQualification qualification = new HrStaffPersonExperienceQualification();
				qualification
						.setFdCertificateName(cert.getFdName());
				qualification
						.setFdAwardUnit(cert.getFdIssuingUnit());
				qualification.setFdBeginDate(cert.getFdIssueDate());
				qualification.setFdEndDate(cert.getFdInvalidDate());
				qualification.setFdCreateTime(new Date());
				qualification.setFdRelatedProcess(
						HrRatifyUtil.getUrl(entry));
				qualification.setFdCreator(UserUtil.getUser());
				qualification.setFdPersonInfo(personInfo);
				hrStaffPersonExperienceQualificationService
						.add(qualification);
			}
			logger.info("判断5开始了...");
			List<HrRatifyRewPuni> rewPuni = entry
					.getFdRewardsPunishments();
			logger.info("判断6开始了...");
			for (HrRatifyRewPuni rew : rewPuni) {
				HrStaffPersonExperienceBonusMalus bonusMalus = new HrStaffPersonExperienceBonusMalus();
				bonusMalus.setFdBonusMalusName(rew.getFdName());
				bonusMalus.setFdBonusMalusDate(rew.getFdDate());
				bonusMalus.setFdMemo(rew.getFdRemark());
				bonusMalus.setFdCreateTime(new Date());
				bonusMalus.setFdRelatedProcess(
						HrRatifyUtil.getUrl(entry));
				bonusMalus.setFdCreator(UserUtil.getUser());
				bonusMalus.setFdPersonInfo(personInfo);
				hrStaffPersonExperienceBonusMalusService
						.add(bonusMalus);
			}
			logger.info("判断6结束了...");
			String fdSalaryAccountName = entry.getFdSalaryAccountName();
			String fdSalaryAccountId = entry.getFdSalaryAccountId();
			logger.info("判断7开始了...");
			if (StringUtil.isNotNull(fdSalaryAccountId)
					&& StringUtil.isNotNull(fdSalaryAccountName)) {
				HrStaffEmolumentWelfare welfare = new HrStaffEmolumentWelfare();
				welfare.setFdPayrollName(fdSalaryAccountName);
				welfare.setFdPayrollBank(entry.getFdSalaryAccountBank());
				welfare.setFdPayrollAccount(fdSalaryAccountId);
				welfare.setFdSurplusAccount(entry.getFdFundAccount());
				welfare.setFdSocialSecurityNumber(
						entry.getFdSocialSecurityAccount());
				welfare.setFdCreateTime(new Date());
				welfare.setFdCreator(UserUtil.getUser());
				welfare.setFdPersonInfo(personInfo);
				hrStaffEmolumentWelfareService.add(welfare);
				logger.info("判断7结束了...");
			}
			String fdDetails = "通过员工入职流程，新增员工“" + entry.getFdEntryName() + "”。";
			HrStaffPersonInfoLog log = getHrStaffPersonInfoLogService()
					.buildPersonInfoLog("save", fdDetails);
			List<HrStaffPersonInfo> fdTargets = new ArrayList<HrStaffPersonInfo>();
			fdTargets.add(personInfo);
			log.setFdTargets(fdTargets);
			getHrStaffPersonInfoLogService().add(log);
		} else {
			personInfo = modifyPersonInfo(entry, personInfo);
			getHrStaffPersonInfoService().update(personInfo);
		}
		logger.info("判断2结束了...");
		applicationContext.publishEvent(new HrStaffPersonInfoEvent(personInfo,
				new RequestContext(Plugin.currentRequest())));
		entry.setFdHasWrite(new Boolean(true));
		if (isExternal) {
			entry.setFdOrgPerson(fdOrgPerson);
		} else {
			entry.setFdOrgPerson(person);
		}
		update(entry);
		logger.info("结束了...");
	}
	private SysOrgPerson isExternal(String fdMobileNo) throws Exception {
		SysOrgPerson fdPerson = new SysOrgPerson();
		HQLInfo hqlInfo = new HQLInfo();
		// 检查组织机构有没有相同的手机号
		hqlInfo.setWhereBlock(
				"sysOrgPerson.fdMobileNo = :fdMobileNo and sysOrgPerson.fdIsAvailable = " + HibernateUtil.toBooleanValueString(true));
		hqlInfo.setParameter("fdMobileNo", fdMobileNo);
		List<SysOrgPerson> lists = getSysOrgPersonService().findList(hqlInfo);
		if ((lists != null) && (!lists.isEmpty()) && (lists.size() > 0)) {
			fdPerson = lists.get(0);
		}
		return fdPerson;
	}


	private Boolean isExternalMobile(String fdMobileNo) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		Boolean isExternal = false;
		// 检查组织机构有没有相同的手机号
		hqlInfo.setWhereBlock(
				" sysOrgPerson.fdMobileNo = :fdMobileNo and sysOrgPerson.fdIsExternal = " + HibernateUtil.toBooleanValueString(true));
		hqlInfo.setParameter("fdMobileNo", fdMobileNo);
		List<SysOrgPerson> lists = getSysOrgPersonService().findList(hqlInfo);
		SysOrgPerson sysOrgPerson = null;
		if ((lists != null) && (!lists.isEmpty()) && (lists.size() > 0)) {
			sysOrgPerson = lists.get(0);
			isExternal = sysOrgPerson.getFdIsExternal();
		}
		return isExternal;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null) {
			return;
		}
		Object obj = event.getSource();
		if (!(obj instanceof HrRatifyEntry)) {
			return;
		}
		if (event instanceof Event_SysFlowFinish) {
			try {
				HrRatifyEntry entry = (HrRatifyEntry) obj;
				entry.setDocPublishTime(new Date());
				HrRatifyEntry noLazyEntry = (HrRatifyEntry) findByPrimaryKey(
						entry.getFdId(), HrRatifyEntry.class, true);
				Boolean fdHasWrite = noLazyEntry.getFdHasWrite();
				String fdStatus = fdHasWrite != null
						&& fdHasWrite.booleanValue() ? "2" : null;
				updateStaffEntry(entry, fdStatus);
				this.update(entry);
				feedbackNotify(entry);
				addLog(entry);
				addTrackRecord(entry);
			} catch (Exception e1) {
				// throw new KmssRuntimeException(e1);
				// e1.printStackTrace();
				logger.error("流程结束执行入职业务出错", e1);
			}
		} else if (event instanceof Event_SysFlowDiscard) {
			try {
				HrRatifyEntry entry = (HrRatifyEntry) obj;
				entry.setDocPublishTime(new Date());
				updateStaffEntry(entry, "3");
				this.update(entry);
			} catch (Exception e2) {
				throw new KmssRuntimeException(e2);
			}
		}
	}

	@Override
	public void addLog(HrRatifyMain mainModel) throws Exception {
		HrRatifyEntry entry = (HrRatifyEntry) mainModel;
		HrStaffRatifyLog log = new HrStaffRatifyLog();
		log.setFdRatifyType("entry");
		log.setFdRatifyDept(entry.getFdEntryDept());
		List<SysOrgPost> posts = new ArrayList<SysOrgPost>();
		posts.addAll(entry.getFdEntryPosts());
		log.setFdRatifyPosts(posts);
		log.setFdRatifyDate(entry.getFdEntryDate());
		JSONObject json = new JSONObject();
		json.put("docSubject", entry.getDocSubject());
		json.put("url", HrRatifyUtil.getUrl(entry));
		log.setFdRatifyProcess(json.toString());
		getHrStaffRatifyLogService().add(log);
	}

	@Override
	public void addTrackRecord(HrRatifyMain mainModel) throws Exception {
		HrRatifyEntry entry = (HrRatifyEntry) mainModel;
		String fdStaffNO = entry.getFdStaffNo();
		HrStaffPersonInfo personinfo = getHrStaffPersonInfoService()
				.findPersonInfoByStaffNo(fdStaffNO);
		if (personinfo != null) {
			HrStaffTrackRecord trackRecord = new HrStaffTrackRecord();
			trackRecord.setFdPersonInfo(personinfo);
			trackRecord.setFdRatifyDept(entry.getFdEntryDept());
			trackRecord.setFdEntranceBeginDate(entry.getFdEntryDate());
			trackRecord.setFdStaffingLevel(entry.getFdEntryLevel());
			List<SysOrgPost> fdOrgPosts = new ArrayList<SysOrgPost>();
			fdOrgPosts.addAll(entry.getFdEntryPosts());
			trackRecord.setFdOrgPosts(fdOrgPosts);
			trackRecord.setFdRelatedProcess("test");
			getHrStaffTrackRecordService().add(trackRecord);
		}
	}

	@Override
	public void deleteEntity(HrRatifyMain mainModel) throws Exception {
		HrRatifyEntry entryModel = (HrRatifyEntry) findByPrimaryKey(
				mainModel.getFdId());
		super.deleteEntity(entryModel);
	}

	private void updateStaffEntry(HrRatifyEntry entry, String fdStatus)
			throws Exception {
		String fdRecruitEntryId = entry.getFdRecruitEntryId();
		if (StringUtil.isNotNull(fdRecruitEntryId)) {
			HrStaffEntry staffEntry = (HrStaffEntry) hrStaffEntryService
					.findByPrimaryKey(fdRecruitEntryId);
			if (StringUtil.isNotNull(fdStatus)) {
				staffEntry.setFdStatus(fdStatus);
			}
			staffEntry.setFdOrgPerson(entry.getFdOrgPerson());
			staffEntry.setFdStaffNo(entry.getFdStaffNo());
			staffEntry.setFdChecker(UserUtil.getUser());
			staffEntry.setFdCheckDate(new Date());
			staffEntry.setFdLastModifiedTime(new Date());
			// 以下更新待入职员工信息
			staffEntry.setFdNameUsedBefore(entry.getFdNameUsedBefore());
			staffEntry.setFdSex(entry.getFdSex());
			staffEntry.setFdDateOfBirth(entry.getFdDateOfBirth());
			staffEntry.setFdNativePlace(entry.getFdNativePlace());
			staffEntry.setFdMaritalStatus(entry.getFdMaritalStatus());
			staffEntry.setFdNation(entry.getFdNation());
			staffEntry.setFdPoliticalLandscape(entry.getFdPoliticalLandscape());
			staffEntry.setFdHealth(entry.getFdHealth());
			staffEntry.setFdLivingPlace(entry.getFdLivingPlace());
			staffEntry.setFdIdCard(entry.getFdIdCard());
			staffEntry.setFdHighestDegree(entry.getFdHighestDegree());
			staffEntry.setFdHighestEducation(entry.getFdHighestEducation());
			staffEntry.setFdProfession(entry.getFdProfession());
			staffEntry.setFdWorkTime(entry.getFdWorkTime());
			staffEntry.setFdDateOfGroup(entry.getFdDateOfGroup());
			staffEntry.setFdDateOfParty(entry.getFdDateOfParty());
			staffEntry.setFdStature(entry.getFdStature());
			staffEntry.setFdWeight(entry.getFdWeight());
			staffEntry.setFdHomeplace(entry.getFdHomeplace());
			staffEntry.setFdAccountProperties(entry.getFdAccountProperties());
			staffEntry
					.setFdRegisteredResidence(entry.getFdRegisteredResidence());
			staffEntry.setFdResidencePoliceStation(
					entry.getFdResidencePoliceStation());
			// 以下是联系信息
			staffEntry.setFdEmail(entry.getFdEmail());
			staffEntry.setFdMobileNo(entry.getFdMobileNo());
			staffEntry.setFdEmergencyContact(entry.getFdEmergencyContact());
			staffEntry.setFdEmergencyContactPhone(
					entry.getFdEmergencyContactPhone());
			staffEntry.setFdOtherContact(entry.getFdOtherContact());
			// 以下是工作经历
			List<HrStaffHistory> sFdHistotry = staffEntry.getFdHistory();
			if (sFdHistotry != null) {
				sFdHistotry.clear();
			} else {
				sFdHistotry = new ArrayList<HrStaffHistory>();
			}
			List<HrRatifyHistory> fdHistory = entry.getFdHistory();
			for (HrRatifyHistory history : fdHistory) {
				HrStaffHistory sHistory = new HrStaffHistory();
				sHistory.setDocIndex(history.getDocIndex());
				sHistory.setDocMain(staffEntry);
				sHistory.setFdDesc(history.getFdDesc());
				sHistory.setFdEndDate(history.getFdEndDate());
				sHistory.setFdLeaveReason(history.getFdLeaveReason());
				sHistory.setFdName(history.getFdName());
				sHistory.setFdPost(history.getFdPost());
				sHistory.setFdStartDate(history.getFdStartDate());
				sFdHistotry.add(sHistory);
			}
			staffEntry.setFdHistory(sFdHistotry);
			// 以下是教育记录
			List<HrStaffEduExp> sFdEducations = staffEntry.getFdEducations();
			if (sFdEducations != null) {
				sFdEducations.clear();
			} else {
				sFdEducations = new ArrayList<HrStaffEduExp>();
			}
			List<HrRatifyEduExp> fdEducations = entry.getFdEducations();
			for (HrRatifyEduExp educations : fdEducations) {
				HrStaffEduExp sEducations = new HrStaffEduExp();
				sEducations.setDocIndex(educations.getDocIndex());
				sEducations.setDocMain(staffEntry);
				sEducations.setFdAcadeRecord(educations.getFdAcadeRecord());
				sEducations.setFdEntranceDate(educations.getFdEntranceDate());
				sEducations
						.setFdGraduationDate(educations.getFdGraduationDate());
				sEducations.setFdMajor(educations.getFdMajor());
				sEducations.setFdName(educations.getFdName());
				sEducations.setFdRemark(educations.getFdRemark());
				sFdEducations.add(sEducations);
			}
			staffEntry.setFdEducations(sFdEducations);
			// 以下是资格证书
			List<HrStaffCertifi> sFdCertifi = staffEntry.getFdCertificate();
			if (sFdCertifi != null) {
				sFdCertifi.clear();
			} else {
				sFdCertifi = new ArrayList<HrStaffCertifi>();
			}
			List<HrRatifyCertifi> certifi = entry.getFdCertificate();
			for (HrRatifyCertifi cert : certifi) {
				HrStaffCertifi sCert = new HrStaffCertifi();
				sCert.setDocIndex(cert.getDocIndex());
				sCert.setDocMain(staffEntry);
				sCert.setFdInvalidDate(cert.getFdInvalidDate());
				sCert.setFdIssueDate(cert.getFdIssueDate());
				sCert.setFdIssuingUnit(cert.getFdIssuingUnit());
				sCert.setFdName(cert.getFdName());
				sCert.setFdRemark(cert.getFdRemark());
				sFdCertifi.add(sCert);
			}
			staffEntry.setFdCertificate(sFdCertifi);
			if (staffEntry.getFdOrgPerson() != null) {
				HrStaffPersonInfo hrStaffPersonInfo = hrStaffPersonInfoService
						.findByOrgPersonId(
								staffEntry.getFdOrgPerson().getFdId());
				hrStaffPersonInfo = modifyPersonInfo(entry, hrStaffPersonInfo);
				hrStaffPersonInfoService.update(hrStaffPersonInfo);
				// 以下是薪酬福利
				String fdSalaryAccountName = entry.getFdSalaryAccountName();
				String fdSalaryAccountId = entry.getFdSalaryAccountId();
				if (StringUtil.isNotNull(fdSalaryAccountId)
						&& StringUtil.isNotNull(fdSalaryAccountName)) {
					List<HrStaffEmolumentWelfare> list = hrStaffEmolumentWelfareService
							.getEmolumentWelfareByPerson(
									staffEntry.getFdOrgPerson().getFdId());
					if (!ArrayUtil.isEmpty(list)) {
						HrStaffEmolumentWelfare welfare = list.get(0);
						welfare.setFdPayrollName(fdSalaryAccountName);
						welfare.setFdPayrollBank(
								entry.getFdSalaryAccountBank());
						welfare.setFdPayrollAccount(fdSalaryAccountId);
						welfare.setFdSurplusAccount(entry.getFdFundAccount());
						welfare.setFdSocialSecurityNumber(
								entry.getFdSocialSecurityAccount());
						hrStaffEmolumentWelfareService.update(welfare);
					}
				}
			}

			// 以下奖惩信息
			List<HrStaffRewPuni> sFdRewPuni = staffEntry
					.getFdRewardsPunishments();
			if (sFdRewPuni != null) {
				sFdRewPuni.clear();
			} else {
				sFdRewPuni = new ArrayList<HrStaffRewPuni>();
			}
			List<HrRatifyRewPuni> rewPuni = entry.getFdRewardsPunishments();
			for (HrRatifyRewPuni rp : rewPuni) {
				HrStaffRewPuni sRp = new HrStaffRewPuni();
				sRp.setDocIndex(rp.getDocIndex());
				sRp.setDocMain(staffEntry);
				sRp.setFdDate(rp.getFdDate());
				sRp.setFdName(rp.getFdName());
				sRp.setFdRemark(rp.getFdRemark());
				sFdRewPuni.add(sRp);
			}
			staffEntry.setFdRewardsPunishments(sFdRewPuni);
			hrStaffEntryService.update(staffEntry);
		}
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		HrRatifyEntry hrRatifyEntry = (HrRatifyEntry) modelObj; 
		if (StringUtil.isNull(hrRatifyEntry.getDocNumber())) {
			setDocNumber(hrRatifyEntry);
		}
		HrRatifyTitleUtil.genTitle(hrRatifyEntry);
		super.update(hrRatifyEntry);
	}

	/*历史归档机制代码*/
	/*@Override
	public void addFileMainDoc(HttpServletRequest request, String fdId,
			KmArchivesFileTemplate fileTemplate) throws Exception {
		HrRatifyEntry mainModel = (HrRatifyEntry) findByPrimaryKey(fdId);
		// 只有结束和已反馈的文档可以归档
		if (!"30".equals(mainModel.getDocStatus())
				&& !"31".equals(mainModel.getDocStatus())) {
			throw new KmssRuntimeException(
					new KmssMessage("km-archives:file.notsupport"));
		}
		HrRatifyTemplate tempalte = mainModel.getDocTemplate();
		// 模块支持归档
		if (KmArchivesUtil.isStartFile("hr/ratify")) {
			KmArchivesFileTemplate fTemplate = kmArchivesFileTemplateService
					.getFileTemplate(tempalte, null);
			// 有归档模板
			if (fTemplate != null) {
				addArchives(request, mainModel, fTemplate);
			} else if ("1".equals(request.getParameter("userSetting"))) { // 支持用户级配置
				addArchives(request, mainModel, fileTemplate);
			}
		}
		mainModel.setFdIsFiling(true);
		if (UserOperHelper.allowLogOper("fileDoc", "*")
				|| UserOperHelper.allowLogOper("fileDocAll", "*")) {
			UserOperContentHelper.putUpdate(mainModel);
		}
		super.update(mainModel);
	}

	private void addArchives(HttpServletRequest request,
			HrRatifyEntry mainModel, KmArchivesFileTemplate fileTemplate)
			throws Exception {
		KmArchivesMain kmArchivesMain = new KmArchivesMain();
		kmArchivesFileTemplateService.setFileField(kmArchivesMain, fileTemplate,
				mainModel);
		// 归档页面URL(若为多表单，暂时归档默认表单)
		int saveApproval = fileTemplate.getFdSaveApproval() != null
				&& fileTemplate.getFdSaveApproval() ? 1 : 0;
		String url = "/hr/ratify/hr_ratify_entry/hrRatifyEntry.do?method=printFileDoc&fdId="
				+ mainModel.getFdId() + "&s_xform=default&saveApproval="
				+ saveApproval;
		String fileName = mainModel.getDocSubject() + ".html";
		kmArchivesFileTemplateService.setFilePrintPage(kmArchivesMain, request,
				url, fileName);
		// 找到与主文档绑定的所有附件
		kmArchivesFileTemplateService.setFileAttachement(kmArchivesMain,
				mainModel);
		kmArchivesFileTemplateService.addFileArchives(kmArchivesMain);
		if (UserOperHelper.allowLogOper("fileDoc", "*")) {
			UserOperContentHelper.putAdd(kmArchivesMain)
					.putSimple("docTemplate", fileTemplate);
		}
	}*/

	@Override
	public String checkUnique(RequestContext requestInfo) throws Exception {
		String fdId = requestInfo.getParameter("fdId");
		String mobileNo = requestInfo.getParameter("mobileNo");
		String staffNo = requestInfo.getParameter("staffNo");
		String fdRecruitEntryId = requestInfo.getParameter("fdRecruitEntryId");
		String result = "";
		if (StringUtil.isNotNull(mobileNo)) {
			result = checkMobileNoUnique(fdId, mobileNo, fdRecruitEntryId);
		}
		if (StringUtil.isNotNull(staffNo)) {
			result = checkStaffNoUnique(fdId, staffNo);
		}
		String loginName = requestInfo.getParameter("loginName");
		String checkType = requestInfo.getParameter("checkType");
		if (StringUtil.isNotNull(loginName)) {
			result = checkLoginName(fdId, loginName, checkType);
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
				" hrStaffPersonInfo.fdStaffNo = :fdStaffNo");
		hqlInfo.setParameter("fdStaffNo", staffNo);
		List<HrStaffPersonInfo> lists = getHrStaffPersonInfoService()
				.findList(hqlInfo);
		HrStaffPersonInfo hrStaffPersonInfo=null;
		if ((lists != null) && (!lists.isEmpty()) && (lists.size() > 0)) {
			hrStaffPersonInfo=lists.get(0);
			result = hrStaffPersonInfo.getFdStaffNo();
		}
		HrRatifyEntry hrRatifyEntry = (HrRatifyEntry) this.findByPrimaryKey(fdId, null, true);
		if(hrStaffPersonInfo!=null && hrRatifyEntry!=null && hrStaffPersonInfo.getFdOrgPerson()!=null && hrRatifyEntry.getFdOrgPerson()!=null
				&&hrStaffPersonInfo.getFdOrgPerson().equals(hrRatifyEntry.getFdOrgPerson())) {
			result="";
		}
		hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				" hrRatifyEntry.fdStaffNo = :fdStaffNo and hrRatifyEntry.fdId != :fdId and hrRatifyEntry.docStatus!=:docStatus ");
		hqlInfo.setParameter("fdStaffNo", staffNo);
		hqlInfo.setParameter("fdId", fdId);
		hqlInfo.setParameter("docStatus", SysDocConstant.DOC_STATUS_DISCARD);
		List<HrRatifyEntry> entryLists = findList(hqlInfo);
		if ((entryLists != null) && (!entryLists.isEmpty())
				&& (entryLists.size() > 0)) {
			result = entryLists.get(0).getFdStaffNo();
		}
		return result;
	}

	private String checkLoginName(String fdId, String loginName,
			String checkType) throws Exception {
		String result = "";
		if (StringUtil.isNull(loginName)) {
			return result;
		}
		HrRatifyEntry entry = (HrRatifyEntry) findByPrimaryKey(fdId,
				HrRatifyEntry.class, true);
		if ((entry != null) && (loginName.equals(entry.getFdLoginName()))) {
			// 编辑用户并且登录名没有改动过则 无需校验无效部分是否重名
			return result;
		}
		HQLInfo hqlInfo = new HQLInfo();
		String hql = " sysOrgPerson.fdLoginName=:fdLoginName and ";
		if ("unique".equals(checkType)) {
			hql = hql + " sysOrgPerson.fdIsAvailable=:fdIsAvailable "; // 1 表示有效的登录名
			hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		} else {
			// 检测无效部分是否重名
			hql = hql + " sysOrgPerson.fdIsAvailable=:fdIsAvailable ";// 0 表示无效的登录名
			hqlInfo.setParameter("fdIsAvailable", Boolean.FALSE);
		}
		hqlInfo.setWhereBlock(hql);
		hqlInfo.setParameter("fdLoginName", loginName);
		List<SysOrgPerson> lists = getSysOrgPersonService().findList(hqlInfo);

		if ((lists != null) && (!lists.isEmpty()) && (lists.size() > 0)) {
			result += lists.get(0).getFdLoginName();
		}
		return result;
	}

	/**
	 *
	 * @param fdId
	 * @param mobileNo
	 * @param fdRecruitEntryId 待确认人事档案id
	 * @return: java.lang.String
	 * @author: wangjf
	 * @time: 2022/5/24 4:03 下午
	 */
	private String checkMobileNoUnique(String fdId, String mobileNo, String fdRecruitEntryId)
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
				" sysOrgPerson.fdMobileNo = :fdMobileNo and sysOrgPerson.fdId != :fdId and sysOrgPerson.fdIsAvailable = :fdIsAvailable");
		hqlInfo.setParameter("fdMobileNo", mobileNo);
		hqlInfo.setParameter("fdId", fdId);
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		List<SysOrgPerson> lists = getSysOrgPersonService().findList(hqlInfo);
		SysOrgPerson sysOrgPerson=null;
		if ((lists != null) && (!lists.isEmpty()) && (lists.size() > 0)) {
			sysOrgPerson=lists.get(0);
			result += lists.get(0).getFdLoginName();
			// 如果是外部的人员 手机号校验过
			Boolean FdIsExternal = lists.get(0).getFdIsExternal();
			if (FdIsExternal) {
				result = "";
			}
		}
		HrRatifyEntry hrRatifyEntry = (HrRatifyEntry) this.findByPrimaryKey(fdId, null, true);
		if(sysOrgPerson!=null && hrRatifyEntry!=null && hrRatifyEntry.getFdOrgPerson()!=null
				&&sysOrgPerson.getFdId().equals(hrRatifyEntry.getFdOrgPerson().getFdId())) {
			result="";
		}
		// 检查人事入职流程有没有相同的手机号
		if (StringUtil.isNull(result)) {
			hqlInfo = new HQLInfo();
			hqlInfo.setParameter("fdMobileNo", mobileNo);
			hqlInfo.setParameter("fdId", fdId);
			List<String> docStatusList = new ArrayList<>();
			//流程中的状态包括两个一个是审批中，一个是驳回
			docStatusList.add(SysDocConstant.DOC_STATUS_EXAMINE);
			docStatusList.add(SysDocConstant.DOC_STATUS_REFUSE);
			hqlInfo.setParameter("docStatus", docStatusList);
			hqlInfo.setWhereBlock(
					" hrRatifyEntry.fdMobileNo = :fdMobileNo and hrRatifyEntry.fdId != :fdId and hrRatifyEntry.docStatus in(:docStatus)");
			HrRatifyEntry dbHrRatifyEntry = (HrRatifyEntry)findFirstOne(hqlInfo);
			if (dbHrRatifyEntry != null) {
				result += dbHrRatifyEntry.getFdLoginName();
			}
		}
		if (StringUtil.isNull(result)) {
			// 检查人事档案的电话号码是否存在
			HQLInfo staffPersonInfoHqlInfo = new HQLInfo();
			staffPersonInfoHqlInfo.setWhereBlock("fdMobileNo = :fdMobileNo");
			staffPersonInfoHqlInfo.setParameter("fdMobileNo", mobileNo);
			HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService().findFirstOne(staffPersonInfoHqlInfo);
			if (hrStaffPersonInfo != null) {
				result += hrStaffPersonInfo.getFdLoginName();
			}
		}
		if (StringUtil.isNull(result)) {
			//检查人事确认档案中的电话号码是否存在
			HQLInfo staffEntryHqlInfo = new HQLInfo();
			String where = "fdMobileNo = :fdMobileNo";
			staffEntryHqlInfo.setParameter("fdMobileNo", mobileNo);
			// 如果是从待确认人事档案过来的数据，则需要剔除自身id的手机号码
			if(StringUtil.isNotNull(fdRecruitEntryId)){
				where += " and fdId !=:fdId";
				staffEntryHqlInfo.setParameter("fdId", fdRecruitEntryId);
			}
			staffEntryHqlInfo.setWhereBlock(where);
			HrStaffEntry hrStaffEntry = (HrStaffEntry) getHrStaffEntryService().findFirstOne(staffEntryHqlInfo);
			if (hrStaffEntry != null) {
				result += hrStaffEntry.getFdName();
			}
		}
		return result;
	}

	@Override
	public void addArchFileModel(HttpServletRequest request, String fdId) throws Exception {
		if("true".equals(SysArchivesConfig.newInstance().getSysArchEnabled())&& SysArchivesUtil.isStartFile("hr/ratify")){
			HrRatifyEntry hrRatifyEntry= (HrRatifyEntry) findByPrimaryKey(fdId.split(",")[0]);
			// 只有结束和已反馈的文档可以归档
			if (!"30".equals(hrRatifyEntry.getDocStatus())
					&& !"31".equals(hrRatifyEntry.getDocStatus())) {
				throw new KmssRuntimeException(
						new KmssMessage("sys-archives:file.notsupport"));
			}
			HrRatifyTemplate template = hrRatifyEntry.getDocTemplate();
			ISysArchivesFileTemplateService sysArchivesFileTemplateService= (ISysArchivesFileTemplateService)SpringBeanUtil.getBean("sysArchivesFileTemplateService");
			SysArchivesFileTemplate fileTemp = sysArchivesFileTemplateService.getFileTemplate(template,null);
			if(fileTemp!=null){
				SysArchivesParamModel paramModel = new SysArchivesParamModel();
				paramModel.setAuto("0");
				paramModel.setFileName(hrRatifyEntry.getDocSubject()+".html");
				// 归档页面URL(若为多表单，暂时归档默认表单)
				int saveApproval = fileTemp.getFdSaveApproval() != null
						&& fileTemp.getFdSaveApproval() ? 1 : 0;
				//流程自动归档
				String fdModelId = hrRatifyEntry.getFdId();
				String url = "/hr/ratify/hr_ratify_entry/hrRatifyEntry.do?method=printFileDoc&fdId="
						+ hrRatifyEntry.getFdId() + "&s_xform=default&saveApproval="
						+ saveApproval;
				paramModel.setUrl(url);
				sysArchivesFileTemplateService.addArchFileModel(request,hrRatifyEntry,paramModel,fileTemp);

				hrRatifyEntry.setFdIsFiling(true);
				super.update(hrRatifyEntry);
			}else{
				return;
			}

			if (UserOperHelper.allowLogOper("fileDoc", "*")) {
				UserOperContentHelper.putAdd(hrRatifyEntry)
						.putSimple("docTemplate", fileTemp);
			}
		}	}
}
