package com.landray.kmss.hr.ratify.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.hr.organization.model.HrOrganizationDept;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationOrg;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.organization.util.HrOrgUtil;
import com.landray.kmss.hr.ratify.model.HrRatifyAgendaConfig;
import com.landray.kmss.hr.ratify.model.HrRatifyMKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.hr.ratify.model.HrRatifySalary;
import com.landray.kmss.hr.ratify.model.HrRatifyTKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.model.HrRatifyTransfer;
import com.landray.kmss.hr.ratify.service.IHrRatifyMainService;
import com.landray.kmss.hr.ratify.service.IHrRatifySalaryService;
import com.landray.kmss.hr.ratify.service.IHrRatifyTransferService;
import com.landray.kmss.hr.ratify.util.HrRatifyTitleUtil;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.hr.staff.model.HrStaffEmolumentWelfareDetalied;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffRatifyLog;
import com.landray.kmss.hr.staff.model.HrStaffTrackRecord;
import com.landray.kmss.hr.staff.service.IHrStaffEmolumentWelfareDetaliedService;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
import com.landray.kmss.sys.archives.config.SysArchivesConfig;
import com.landray.kmss.sys.archives.interfaces.IArchFileDataService;
import com.landray.kmss.sys.archives.model.SysArchivesFileTemplate;
import com.landray.kmss.sys.archives.model.SysArchivesParamModel;
import com.landray.kmss.sys.archives.service.ISysArchivesFileTemplateService;
import com.landray.kmss.sys.archives.util.SysArchivesUtil;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessCurrentInfoService;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.log.model.SysLogOrganization;
import com.landray.kmss.sys.log.service.ISysLogOrganizationService;
import com.landray.kmss.sys.log.util.UserAgentUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzModelContext;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowDiscard;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowFinish;
import com.landray.kmss.sys.xform.interfaces.XFormUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ListSortUtil;
import com.landray.kmss.util.ObjectUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.excel.Column;
import com.landray.kmss.util.excel.Sheet;
import com.landray.kmss.util.excel.WorkBook;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HrRatifyTransferServiceImp extends HrRatifyMainServiceImp
		implements IHrRatifyTransferService, IArchFileDataService {
	private IHrOrganizationElementService hrOrganizationElementService;

	public IHrOrganizationElementService getHrOrganizationElementService() {
		if (this.hrOrganizationElementService == null) {
			this.hrOrganizationElementService = (IHrOrganizationElementService) SpringBeanUtil
					.getBean("hrOrganizationElementService");
		}
		return hrOrganizationElementService;
	}
    private ISysNotifyMainCoreService sysNotifyMainCoreService;

	private IHrStaffEmolumentWelfareDetaliedService hrStaffEmolumentWelfareDetaliedService;

	public void setHrStaffEmolumentWelfareDetaliedService(
			IHrStaffEmolumentWelfareDetaliedService hrStaffEmolumentWelfareDetaliedService) {
		this.hrStaffEmolumentWelfareDetaliedService = hrStaffEmolumentWelfareDetaliedService;
	}

	private IHrRatifyMainService hrRatifyMainService;

	public IHrRatifyMainService getHrRatifyMainServiceImp() {
		if (hrRatifyMainService == null) {
			hrRatifyMainService = (IHrRatifyMainService) SpringBeanUtil.getBean("hrRatifyMainService");
		}
		return hrRatifyMainService;
	}

	// 组织架构
	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	private IHrRatifySalaryService hrRatifySalaryService;

	public IHrRatifySalaryService getHrRatifySalaryService() {
		if (hrRatifySalaryService == null) {
			hrRatifySalaryService = (IHrRatifySalaryService) SpringBeanUtil.getBean("hrRatifySalaryService");
		}
		return hrRatifySalaryService;
	}

	private ISysOrgElementService sysOrgElementService;

	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			this.sysOrgElementService = (ISysOrgElementService) SpringBeanUtil
					.getBean("sysOrgElementService");
		}
		return this.sysOrgElementService;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrRatifyTransfer hrRatifyTransfer = new HrRatifyTransfer();
		hrRatifyTransfer.setDocCreateTime(new Date());
		hrRatifyTransfer.setDocCreator(UserUtil.getUser());
		hrRatifyTransfer.setFdDepartment(UserUtil.getUser().getFdParent());
		String templateId = requestContext.getParameter("i.docTemplate");
		if (com.landray.kmss.util.StringUtil.isNotNull(templateId)) {
			com.landray.kmss.hr.ratify.model.HrRatifyTemplate docTemplate = (com.landray.kmss.hr.ratify.model.HrRatifyTemplate) getHrRatifyTemplateService()
					.findByPrimaryKey(templateId);
			if (docTemplate != null) {
				hrRatifyTransfer.setDocTemplate(docTemplate);
				List<HrRatifyTKeyword> keyWordList = docTemplate
						.getDocKeyword();
				List tKeyword = new ArrayList();
				for (HrRatifyTKeyword keyWord : keyWordList) {
					HrRatifyMKeyword mKeyword = new HrRatifyMKeyword();
					mKeyword.setDocKeyword(keyWord.getDocKeyword());
					tKeyword.add(mKeyword);
				}
				hrRatifyTransfer.setDocKeyword(tKeyword);
			}
		}
        HrRatifyUtil.initModelFromRequest(hrRatifyTransfer, requestContext);
		if (hrRatifyTransfer.getDocTemplate() != null) {
			hrRatifyTransfer.setExtendFilePath(
					XFormUtil.getFileName(hrRatifyTransfer.getDocTemplate(),
							hrRatifyTransfer.getDocTemplate().getFdTempKey()));
			if (Boolean.FALSE.equals(
					hrRatifyTransfer.getDocTemplate().getDocUseXform())) {
				hrRatifyTransfer.setDocXform(
						hrRatifyTransfer.getDocTemplate().getDocXform());
			}
			hrRatifyTransfer.setDocUseXform(
					hrRatifyTransfer.getDocTemplate().getDocUseXform());
		}
        return hrRatifyTransfer;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrRatifyTransfer hrRatifyTransfer = (HrRatifyTransfer) model;
		if (hrRatifyTransfer.getDocTemplate() != null) {
			dispatchCoreService.initFormSetting(form,
					hrRatifyTransfer.getDocTemplate().getFdTempKey(),
					hrRatifyTransfer.getDocTemplate(),
					hrRatifyTransfer.getDocTemplate().getFdTempKey(),
					requestContext);
		}
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	/*private IKmArchivesFileTemplateService kmArchivesFileTemplateService;

	public void setKmArchivesFileTemplateService(
			IKmArchivesFileTemplateService kmArchivesFileTemplateService) {
		this.kmArchivesFileTemplateService = kmArchivesFileTemplateService;
	}*/

	@Override
	public void setDocNumber(HrRatifyMain mainModel) throws Exception {
		HrRatifyTransfer hrRatifyTransfer = (HrRatifyTransfer) mainModel;
		if (!"10".equals(mainModel.getDocStatus())) {
			String docNumber = getSysNumberFlowService()
					.generateFlowNumber(hrRatifyTransfer);
			hrRatifyTransfer.setDocNumber(docNumber);
		}
	}

	@Override
	public void schedulerJob(SysQuartzJobContext context) throws Exception {
		HrRatifyTransfer transfer = (HrRatifyTransfer) findByPrimaryKey(
				context.getParameter(), HrRatifyTransfer.class, true);
		updateStaff(transfer);
		getSysQuartzCoreService().delete(transfer, "hrRatifyTransferQuartzJob");
	}

	private void updateStaff(HrRatifyTransfer transfer) throws Exception {
		// 更新组织架构
		SysOrgPerson fdTransferStaff = transfer.getFdTransferStaff();
		/*fdTransferStaff.setFdParent(transfer.getFdTransferEnterDept());
		fdTransferStaff.setFdPosts(transfer.getFdTransferEnterPosts());
		fdTransferStaff.setFdStaffingLevel(transfer.getFdTransferNewLevel());
		getSysOrgPersonService().update(fdTransferStaff);*/
		String personId = fdTransferStaff.getFdId();
		SysOrgElement leavePost = transfer.getFdTransferLeavePosts()
				.get(0);
		SysOrgElement enterPost = transfer.getFdTransferEnterPosts()
				.get(0);
		// 替换EKP组织架构中的部门和岗位
		SysOrgPerson person = (SysOrgPerson) this.getSysOrgPersonService()
				.findByPrimaryKey(personId);
		person.setFdParent(transfer.getFdTransferEnterDept());
		List posts = person.getFdPosts();
		if (CollectionUtils.isNotEmpty(posts)) {
			//默认调入岗位是不存在的，后面通过该值判断是否需要加入到岗位列表
			List<SysOrgElement> newPosts = new ArrayList<SysOrgElement>();
			boolean enterPostExist = false;
			for(int i=0; i< posts.size();i++) {
				SysOrgElement tempElement = (SysOrgElement)posts.get(i);
				//如果跟调离岗位相同则把旧岗位删除
				if(tempElement.getFdId().equals(leavePost.getFdId())){
					continue;
				}else if(tempElement.getFdId().equals(enterPost.getFdId())) {
					//说明调入岗位原来是存在的
					enterPostExist = true;
				}else {
					newPosts.add(tempElement);
				}
			}
			//加入调入岗位
			if(!enterPostExist) {
				newPosts.add(enterPost);
			}
			person.setFdPosts(newPosts);
		}else {
			person.setFdPosts( transfer.getFdTransferEnterPosts());
		}
		// 更新人事档单
		HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService().findByPrimaryKey(fdTransferStaff.getFdId());
		/**
		 * 2022-08-15 职务的变更逻辑处理
		 */
		if(transfer.getFdTransferNewLevel() !=null){
			//调动后的职务存在的情况下。才处理
			person.setFdStaffingLevel(transfer.getFdTransferNewLevel());
			//理论上来说该值可以不维护。直接存储EKP组织架构后 该地方自动获取。
			hrStaffPersonInfo.setFdStaffingLevel(transfer.getFdTransferNewLevel());
		}
		getSysOrgPersonService().update(person);

		/*hrStaffPersonInfo.setFdOrgParent(transfer.getFdTransferEnterDept());
		hrStaffPersonInfo.setFdOrgPosts(transfer.getFdTransferEnterPosts());
		hrStaffPersonInfo.setFdStaffingLevel(transfer.getFdTransferNewLevel());
		getHrStaffPersonInfoService().update(hrStaffPersonInfo);*/

		HrStaffTrackRecord modelObj = new HrStaffTrackRecord();
		modelObj.setFdPersonInfo(hrStaffPersonInfo);
		modelObj.setFdRatifyDept(transfer.getFdTransferEnterDept());
		modelObj.setFdOrgPosts(transfer.getFdTransferEnterPosts());
		modelObj.setFdEntranceBeginDate(new Date());
		modelObj.setFdType("1");
		getHrStaffTrackRecordService().add(modelObj);
		String fdDetails = "通过员工调岗流程，修改了“" + fdTransferStaff.getFdName()
				+ "”的员工信息：员工部门、岗位、职级。";
		addUpdateLog(fdDetails, hrStaffPersonInfo);
		addTrackRecord(transfer);
		//修改transfer中的调岗、调薪生效状态
		transfer.setFdIsEffective(Boolean.TRUE);
		super.update(transfer);
	}

	private void saveSchedulerJob(RequestContext requestContext,
			HrRatifyTransfer transfer) throws Exception {
		Date formalDate = transfer.getFdTransferDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(formalDate);
		String cron = HrRatifyUtil.getCronExpression(calendar);
		if (StringUtil.isNotNull(cron)) {
			SysQuartzModelContext quartzContext = new SysQuartzModelContext();
			quartzContext.setQuartzJobMethod("schedulerJob");
			quartzContext.setQuartzJobService("hrRatifyTransferService");
			quartzContext.setQuartzKey("hrRatifyTransferQuartzJob");
			quartzContext.setQuartzParameter(transfer.getFdId().toString());
			quartzContext.setQuartzSubject(transfer.getDocSubject());
			quartzContext.setQuartzRequired(true);
			quartzContext.setQuartzCronExpression(cron);
			getSysQuartzCoreService().saveScheduler(quartzContext, transfer);
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null) {
			return;
		}
		Object obj = event.getSource();
		if (!(obj instanceof HrRatifyTransfer)) {
			return;
		}
		if (event instanceof Event_SysFlowFinish) {
			try {
				HrRatifyTransfer transfer = (HrRatifyTransfer) obj;
				Date fdTransferDate = transfer.getFdTransferDate();
				Date now = new Date();
				HrRatifyAgendaConfig config = new HrRatifyAgendaConfig();
				if ("true".equals(config.getFdUpdateSysOrg())) {
					if (now.getTime() >= fdTransferDate.getTime()) {
						updateStaff(transfer);
					} else {
						saveSchedulerJob(new RequestContext(), transfer);
					}
				}
				Double fdTransferLeaveSalary = transfer
						.getFdTransferLeaveSalary();
				Double fdTransferEnterSalary = transfer
						.getFdTransferEnterSalary();
				if (fdTransferLeaveSalary != null
						&& fdTransferEnterSalary != null) {
					HrStaffEmolumentWelfareDetalied detail = new HrStaffEmolumentWelfareDetalied();
					detail.setFdRelatedProcess(HrRatifyUtil.getUrl(transfer));
					detail.setFdAdjustDate(now);
					detail.setFdEffectTime(fdTransferDate);
					detail.setFdBeforeEmolument(fdTransferLeaveSalary);
					detail.setFdAdjustAmount(
							fdTransferEnterSalary - fdTransferLeaveSalary);
					detail.setFdAfterEmolument(fdTransferEnterSalary);
					detail.setFdPersonInfo(getHrStaffPersonInfoService()
							.findByOrgPersonId(
									transfer.getFdTransferStaff().getFdId()));
					detail.setFdCreator(UserUtil.getUser());
					detail.setFdCreateTime(now);
					hrStaffEmolumentWelfareDetaliedService.add(detail);
				}
				transfer.setDocPublishTime(now);
				super.update(transfer);
				feedbackNotify(transfer);
				addLog(transfer);
			} catch (Exception e1) {
				e1.printStackTrace();
				logger.error("hr流程事件出错", e1);
			}
		} else if (event instanceof Event_SysFlowDiscard) {
			try {
				HrRatifyTransfer transfer = (HrRatifyTransfer) obj;
				transfer.setDocPublishTime(new Date());
				super.update(transfer);
			} catch (Exception e2) {
				logger.error("",e2);
			}
		}
	}

	@Override
	public void addLog(HrRatifyMain mainModel) throws Exception {
		HrRatifyTransfer transfer = (HrRatifyTransfer) mainModel;
		HrStaffRatifyLog log = new HrStaffRatifyLog();
		log.setFdRatifyType("transfer");

		log.setFdOrgPerson(transfer.getFdTransferStaff());
		log.setFdRatifyOldDept(transfer.getFdTransferLeaveDept());
		log.setFdRatifyDept(transfer.getFdTransferEnterDept());

		List<SysOrgPost> enterPosts = new ArrayList<SysOrgPost>();
		enterPosts.addAll(transfer.getFdTransferEnterPosts());
		log.setFdRatifyPosts(enterPosts);

		List<SysOrgPost> leavePosts = new ArrayList<SysOrgPost>();
		leavePosts.addAll(transfer.getFdTransferLeavePosts());
		log.setFdRatifyOldPosts(leavePosts);

		log.setFdRatifyDate(transfer.getFdTransferDate());
		JSONObject json = new JSONObject();
		json.put("docSubject", transfer.getDocSubject());
		json.put("url", HrRatifyUtil.getUrl(transfer));
		log.setFdRatifyProcess(json.toString());
		getHrStaffRatifyLogService().add(log);
	}
	@Override
	public void addTrackRecord(HrRatifyMain mainModel) throws Exception {
		HrRatifyTransfer transfer = (HrRatifyTransfer) mainModel;
		String fdId = transfer.getFdTransferStaff().getFdId();
		HrStaffPersonInfo personInfo = getHrStaffTrackRecordService()
				.getPersonInfo(fdId);
		String trackRecordId = getHrStaffTrackRecordService()
				.getTrackRecordByPerson(personInfo.getFdId());
		Date transferDate =transfer.getFdTransferDate() ;
		if(transferDate ==null) {
			transferDate =transfer.getDocPublishTime();
		}

		// 如果有前一段任职记录，将结束日期写入
		if (StringUtil.isNotNull(trackRecordId)) {
			HrStaffTrackRecord track = (HrStaffTrackRecord) getHrStaffTrackRecordService()
					.findByPrimaryKey(trackRecordId);
			track.setFdEntranceEndDate(transferDate);
			getHrStaffTrackRecordService().update(track);
		}
		// 新增一条记录
		HrStaffTrackRecord trackRecord = new HrStaffTrackRecord();
		trackRecord.setFdOrgPerson(transfer.getFdTransferStaff());
		trackRecord.setFdRatifyDept(transfer.getFdTransferEnterDept());
		trackRecord.setFdEntranceBeginDate(transferDate);
		trackRecord.setFdStaffingLevel(transfer.getFdTransferNewLevel());
		//设置状态
		List<SysOrgPost> fdOrgPosts = new ArrayList<SysOrgPost>();

		if ( personInfo!= null) {
			trackRecord.setFdPersonInfo(personInfo);
		}

		fdOrgPosts.addAll(transfer.getFdTransferEnterPosts());
		trackRecord.setFdOrgPosts(fdOrgPosts);
		getHrStaffTrackRecordService().add(trackRecord);
	}

	@Override
	public void deleteEntity(HrRatifyMain mainModel) throws Exception {
		HrRatifyTransfer transferModel = (HrRatifyTransfer) findByPrimaryKey(
				mainModel.getFdId());
		super.deleteEntity(transferModel);
	}

	private void synchroOrgEkpToHr(HrRatifyTransfer hrRatifyTransfer)
			throws Exception {
		String logInfo = null;
		List<SysOrgElement> list = new ArrayList<SysOrgElement>();
		List<SysOrgElement> transferEnterDeptParents = hrRatifyTransfer
				.getFdTransferEnterDept().getAllParent(false);
		List<SysOrgElement> transferLeaveDeptParents = hrRatifyTransfer
				.getFdTransferLeaveDept().getAllParent(false);
		Collections.reverse(transferEnterDeptParents);
		Collections.reverse(transferLeaveDeptParents);
		list.addAll(transferEnterDeptParents);
		list.addAll(transferLeaveDeptParents);
		list.addAll(hrRatifyTransfer.getFdTransferLeavePosts());
		list.addAll(hrRatifyTransfer.getFdTransferEnterPosts());
		list.add(hrRatifyTransfer.getFdTransferStaff());
		//更新机构、部门、岗位、人员
		for (SysOrgElement element : list) {
			if(element==null) {
				continue;
			}
			HrOrganizationElement organizationElement =null;
			if(this.getHrOrganizationElementService().getBaseDao().isExist(HrOrganizationElement.class.getName(), element.getFdId())) {
				organizationElement = (HrOrganizationElement) this.getHrOrganizationElementService().findByPrimaryKey(element.getFdId(),HrOrganizationElement.class, true);
			}
			if (organizationElement == null) {// 新增
				String fdId = element.getFdId();
				if (element.getFdOrgType()
						.equals(SysOrgConstant.ORG_TYPE_ORG)) {
					organizationElement = new HrOrganizationOrg();
				} else if (element.getFdOrgType()
						.equals(SysOrgConstant.ORG_TYPE_DEPT)) {
					organizationElement = new HrOrganizationDept();
				} else if (element.getFdOrgType()
						.equals(SysOrgConstant.ORG_TYPE_PERSON)) {
					organizationElement = new HrStaffPersonInfo();
					element = (SysOrgElement) this.getSysOrgPersonService()
							.findByPrimaryKey(fdId, null, true);
				} else if (element.getFdOrgType()
						.equals(SysOrgConstant.ORG_TYPE_POST)) {
					organizationElement = new HrOrganizationPost();
				}
				HrOrgUtil.copyEkpOrgToHrOrg(organizationElement, element);
				this.getHrOrganizationElementService()
						.update(organizationElement);
				// 日志
				logInfo = "新增人事组织架构机构、部门、人员、岗位信息：fdName="
						+ organizationElement.getFdName() + ",fdId="
						+ organizationElement.getFdId() + ",成功！！！";
			} else {
					if (null == organizationElement.getFdAlterTime()
							|| element.getFdAlterTime().getTime() != organizationElement.getFdAlterTime().getTime()) {
						if (element.getFdOrgType().equals(SysOrgConstant.ORG_TYPE_PERSON)) {
							organizationElement = (HrStaffPersonInfo) this.getHrStaffPersonInfoService()
									.findByPrimaryKey(element.getFdId());
							element = (SysOrgElement) this.getSysOrgPersonService().findByPrimaryKey(element.getFdId(), null, true);
						} else {
						organizationElement = (HrOrganizationElement) this
								.getHrOrganizationElementService()
									.findByPrimaryKey(element.getFdId());
						}
						HrOrgUtil.setHierarchy(organizationElement, element);

					this.getHrOrganizationElementService()
							.update(organizationElement);

						//日志
						logInfo = "更新人事组织架构信息：fdName=" + organizationElement.getFdName() + ",fdId="
								+ organizationElement.getFdId() + ",成功！！！";
					}
			}
			logger.debug(logInfo);
		}
		this.addSysLog(hrRatifyTransfer);
	}

	private ISysLogOrganizationService sysLogOrganizationService;

	public ISysLogOrganizationService getSysLogOrganizationService() {
		if (sysLogOrganizationService == null) {
			sysLogOrganizationService = (ISysLogOrganizationService) SpringBeanUtil
					.getBean("sysLogOrganizationService");
		}
		return sysLogOrganizationService;
	}

	/********
	 * //添加调岗日志 by caoyong
	 * @param hrRatifyTransfer
	 */
	public void addSysLog(HrRatifyTransfer hrRatifyTransfer) throws Exception {

		String details = "";
		SysLogOrganization log = new SysLogOrganization();
		log.setFdCreateTime(new java.util.Date());
		log.setFdBrowser(UserAgentUtil.getBrowser());
		log.setFdEquipment(UserAgentUtil.getOperatingSystem());
		log.setFdOperator(UserUtil.getKMSSUser().getUserName());
		log.setFdOperatorId(UserUtil.getKMSSUser().getUserId());
		log.setFdParaMethod("update");
		StringBuffer sb = new StringBuffer();
		sb.append(ResourceUtil.getString("sysLogOaganization.operate.modify", "sys-log"));
		sb.append("【" + hrRatifyTransfer.getFdTransferStaff().getFdName() + "】");
		sb.append(ResourceUtil.getString("sysLogOaganization.info.person", "sys-log"));
		SysOrgElement outOrg = hrRatifyTransfer.getFdTransferLeaveDept();
		SysOrgElement inOrg = hrRatifyTransfer.getFdTransferEnterDept();
		//所属部门
		if(outOrg != null && inOrg !=null && !ObjectUtil.equals(outOrg.getFdId(),inOrg.getFdId())){
			details = StringUtil.linkString(details, " 、", ResourceUtil.getString("sysOrgPerson.fdParent", "sys-organization"));
		}

		SysOrganizationStaffingLevel oldLevel = hrRatifyTransfer.getFdTransferOldLevel();
		SysOrganizationStaffingLevel newLevel = hrRatifyTransfer.getFdTransferNewLevel();
		//职务
		if(oldLevel != null && newLevel !=null && !ObjectUtil.equals(oldLevel.getFdId(),newLevel.getFdId())){
			details = StringUtil.linkString(details, " 、", ResourceUtil.getString("sysOrgPerson.fdStaffingLevel", "sys-organization"));
		}
		List<SysOrgPost>  leavePosts = hrRatifyTransfer.getFdTransferLeavePosts();
		List<SysOrgPost>  enterPosts = hrRatifyTransfer.getFdTransferEnterPosts();
		if(leavePosts != null && enterPosts !=null){
			StringBuffer old = new StringBuffer();
			for(SysOrgPost oldpost : leavePosts){
				old.append(oldpost.getFdId()).append(",");
			}
			for(int i =0, len = enterPosts.size(); i < len ; i ++ ){
				if(old.toString().indexOf(enterPosts.get(i).getFdId()) == -1){
					//添加岗位调动信息
					details = StringUtil.linkString(details, " 、", ResourceUtil.getString("sysOrgPerson.fdPosts", "sys-organization"));
				    break;
				}
			}
		}

		if(StringUtil.isNotNull(details) ){
			log.setFdDetails(log.getFdOperator() +sb.toString() +  details + "。");// 设置详细信息
			log.setFdTargetId(hrRatifyTransfer.getFdTransferStaff().getFdId());
			getSysLogOrganizationService().add(log);
		}

	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		// 保存前先同步
		HrRatifyTransfer hrRatifyTransfer = (HrRatifyTransfer) modelObj;
		if(hrRatifyTransfer.getFdTransferStaff() ==null) {
			return null;
		}
		this.synchroOrgEkpToHr(hrRatifyTransfer);
		return super.add(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		HrRatifyTransfer transferModel = (HrRatifyTransfer) modelObj;
		if (StringUtil.isNull(transferModel.getDocNumber())) {
			setDocNumber(transferModel);
		}
		// 保存前先同步
		this.synchroOrgEkpToHr(transferModel);
		HrRatifyTitleUtil.genTitle(transferModel);
		super.update(transferModel);
	}

	/*历史归档机制*/
	/*@Override
	public void addFileMainDoc(HttpServletRequest request, String fdId,
			KmArchivesFileTemplate fileTemplate) throws Exception {
		HrRatifyTransfer mainModel = (HrRatifyTransfer) findByPrimaryKey(fdId);
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
			HrRatifyTransfer mainModel, KmArchivesFileTemplate fileTemplate)
			throws Exception {
		KmArchivesMain kmArchivesMain = new KmArchivesMain();
		kmArchivesFileTemplateService.setFileField(kmArchivesMain, fileTemplate,
				mainModel);
		// 归档页面URL(若为多表单，暂时归档默认表单)
		int saveApproval = fileTemplate.getFdSaveApproval() != null
				&& fileTemplate.getFdSaveApproval() ? 1 : 0;
		String url = "/hr/ratify/hr_ratify_transfer/hrRatifyTransfer.do?method=printFileDoc&fdId="
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
	public void addArchFileModel(HttpServletRequest request, String fdId) throws Exception {
		if("true".equals(SysArchivesConfig.newInstance().getSysArchEnabled())&& SysArchivesUtil.isStartFile("hr/ratify")){
			HrRatifyTransfer hrRatifyTransfer= (HrRatifyTransfer) findByPrimaryKey(fdId.split(",")[0]);
			// 只有结束和已反馈的文档可以归档
			if (!"30".equals(hrRatifyTransfer.getDocStatus())
					&& !"31".equals(hrRatifyTransfer.getDocStatus())) {
				throw new KmssRuntimeException(
						new KmssMessage("sys-archives:file.notsupport"));
			}
			HrRatifyTemplate template = hrRatifyTransfer.getDocTemplate();
			ISysArchivesFileTemplateService sysArchivesFileTemplateService= (ISysArchivesFileTemplateService)SpringBeanUtil.getBean("sysArchivesFileTemplateService");
			SysArchivesFileTemplate fileTemp = sysArchivesFileTemplateService.getFileTemplate(template,null);
			if(fileTemp!=null){
				SysArchivesParamModel paramModel = new SysArchivesParamModel();
				paramModel.setAuto("0");
				paramModel.setFileName(hrRatifyTransfer.getDocSubject()+".html");
				// 归档页面URL(若为多表单，暂时归档默认表单)
				int saveApproval = fileTemp.getFdSaveApproval() != null
						&& fileTemp.getFdSaveApproval() ? 1 : 0;
				//流程自动归档
				String fdModelId = hrRatifyTransfer.getFdId();
				String url = "/hr/ratify/hr_ratify_transfer/hrRatifyTransfer.do?method=printFileDoc&fdId="
						+ hrRatifyTransfer.getFdId() + "&s_xform=default&saveApproval="
						+ saveApproval;
				paramModel.setUrl(url);
				sysArchivesFileTemplateService.addArchFileModel(request,hrRatifyTransfer,paramModel,fileTemp);

				hrRatifyTransfer.setFdIsFiling(true);
				super.update(hrRatifyTransfer);
			}else{
				return;
			}

			if (UserOperHelper.allowLogOper("fileDoc", "*")) {
				UserOperContentHelper.putAdd(hrRatifyTransfer)
						.putSimple("docTemplate", fileTemp);
			}
		}
	}
	@Override
	public Page getTransferManagePage(HQLInfo hqlInfo, HttpServletRequest request) throws Exception {
		Page page = Page.getEmptyPage();
		/*String whereBlock = StringUtil.isNotNull(hqlInfo.getWhereBlock()) ? hqlInfo.getWhereBlock() : "1=1";
		whereBlock = whereBlock
				+ " and hrRatifyMain.fdSubclassModelname in('com.landray.kmss.hr.ratify.model.HrRatifyTransfer', 'com.landray.kmss.hr.ratify.model.HrRatifySalary')";

		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setSelectBlock(
				"hrRatifyMain.fdId, hrRatifyMain.fdSubclassModelname, hrRatifyMain.docStatus, hrRatifyMain.docNumber");
		List entryList = getHrRatifyMainServiceImp().findList(hqlInfo);*/

		//类型筛选
		String type = request.getParameter("type");

		//调薪流程
		List<HrRatifySalary> salaries = null;
		List<HrRatifyTransfer> transfers = null;
		List<HrStaffTrackRecord> trackRecords =null;
		List<HrStaffEmolumentWelfareDetalied> detalieds =null;
		int total = 0 ;
		if("post".equals(type)){
			//调动流程
			HQLInfo transfersInfo = new HQLInfo();
			changeFindPageHQLInfo(transfersInfo, request, "hrRatifyTransfer.fdTransferStaff","hrRatifyTransfer");
			transfersInfo.setOrderBy("hrRatifyTransfer.fdTransferDate desc,hrRatifyTransfer.fdId desc ");
			transfersInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
			transfers = this.findList(transfersInfo);

			//查询任职记录表记录
			HQLInfo trackRecorInfo = new HQLInfo();
			StringBuffer whereBlock = new StringBuffer();
			trackRecorInfo.setWhereBlock("hrStaffTrackRecord.fdSource =:fdSource");
			trackRecorInfo.setParameter("fdSource", "HrStaffConcern");
			changeFindPageHQLInfo(trackRecorInfo, request, "hrStaffTrackRecord.fdPersonInfo","hrStaffTrackRecord");
			trackRecorInfo.setOrderBy("hrStaffTrackRecord.fdEntranceBeginDate desc,hrStaffTrackRecord.fdId desc ");
			whereBlock.append(trackRecorInfo.getWhereBlock());
			whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffTrackRecord", trackRecorInfo);
			trackRecorInfo.setWhereBlock(whereBlock.toString());
			trackRecords = getHrStaffTrackRecordService().findList(trackRecorInfo);
			//岗位调动
			total = trackRecords.size() + transfers.size();
		}else if("salary".equals(type)){
			//调薪流程
			HQLInfo salariesInfo = new HQLInfo();
			changeFindPageHQLInfo(salariesInfo, request, "hrRatifySalary.fdSalaryStaff","hrRatifySalary");
			salariesInfo.setOrderBy("hrRatifySalary.fdSalaryDate desc,hrRatifySalary.fdId desc ");
			salariesInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.DEFAULT);
			salaries = getHrRatifySalaryService().findList(salariesInfo);
			//薪水调动
			//查询调薪明细表记录
			HQLInfo detaliedInfo = new HQLInfo();
			StringBuffer whereBlock = new StringBuffer();
			detaliedInfo.setWhereBlock("hrStaffEmolumentWelfareDetalied.fdSource =:fdSource");
			detaliedInfo.setParameter("fdSource", "HrStaffConcern");
			changeFindPageHQLInfo(detaliedInfo, request, "hrStaffEmolumentWelfareDetalied.fdPersonInfo","hrStaffEmolumentWelfareDetalied");
			detaliedInfo.setOrderBy("hrStaffEmolumentWelfareDetalied.fdAdjustDate desc,hrStaffEmolumentWelfareDetalied.fdId desc ");
			whereBlock.append(detaliedInfo.getWhereBlock());
			whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffEmolumentWelfareDetalied", detaliedInfo);
			detaliedInfo.setWhereBlock(whereBlock.toString());
			detalieds = hrStaffEmolumentWelfareDetaliedService.findList(detaliedInfo);
			total = salaries.size() + detalieds.size();
		}

		if (total > 0) {
			page.setRowsize(hqlInfo.getRowSize());
			page.setPageno(hqlInfo.getPageNo());
			page.setTotalrows(total);
			page.setOrderby(hqlInfo.getOrderBy());
			page.excecute();
			Map<String, String> map = null;
			List list = new ArrayList();
			Object[] o = null;
			if("post".equals(type)){
				if(CollectionUtils.isNotEmpty(transfers)) {
					for (HrRatifyTransfer transfer : transfers) {
						map = new HashMap<String, String>();
						HrStaffPersonInfo personInfo = getHrStaffPersonInfoService()
								.findByOrgPersonId(transfer.getFdTransferStaff().getFdId());
						map.put("fdId", transfer.getFdId());
						map.put("docStatus", transfer.getDocStatus()); //状态
						map.put("fdIsEffective",String.valueOf(transfer.getFdIsEffective())); //调动状态
						map.put("docNumber", transfer.getDocNumber()); //流程编号
						//调动
						map.put("fdId", transfer.getFdId());
						map.put("fdName", transfer.getFdTransferStaff().getFdName());
						map.put("fdStaffNo", transfer.getFdTransferStaff().getFdNo()); // 工号
						map.put("fdDeptName", null != transfer.getFdDepartment() ? transfer.getFdDepartment().getFdName() : ""); //部门
						map.put("fdOrgPostNames", getPostsName(transfer.getFdEntryPosts())); //岗位
						map.put("fdPostAfter",
								getPostsName(transfer.getFdTransferLeavePosts()));
						map.put("fdPostBefore",
								getPostsName(transfer.getFdTransferEnterPosts()));
						map.put("fdTransferType", "调动"); //调动类型
						map.put("fdTransferBefore", transfer.getFdTransferEnterDept().getFdName()); //调动后部门
						map.put("fdTransferAfter", transfer.getFdTransferLeaveDept().getFdName()); //调动前部门
						Double leaveSalary = transfer.getFdTransferLeaveSalary();
						Double enterSalary = transfer.getFdTransferEnterSalary();
						map.put("fdSalaryAfter", null != leaveSalary ? leaveSalary.toString() : null); //调动前薪资
						map.put("fdSalaryBefore", null != enterSalary ? enterSalary.toString() : null); //调动后薪资
						map.put("fdEffectTime",
								DateUtil.convertDateToString(transfer.getFdTransferDate(), DateUtil.PATTERN_DATE)); //生效日期
						map.put("personId", null != personInfo ? personInfo.getFdId() : "");
						map.put("delTrackMark", "false");
						list.add(map);
					}
				}
				if(CollectionUtils.isNotEmpty(trackRecords)) {
					for (HrStaffTrackRecord record : trackRecords) {
						//查询调动前的任职记录
						HrStaffTrackRecord oldRecord = new HrStaffTrackRecord();
						if(null != record) {
							HQLInfo info = new HQLInfo();
							info.setWhereBlock(" hrStaffTrackRecord.fdCreateTime<:fdCreateTime and hrStaffTrackRecord.fdPersonInfo.fdId=:fdId");
							info.setParameter("fdCreateTime", record.getFdCreateTime());
							info.setParameter("fdId", record.getFdPersonInfo().getFdId());
							info.setOrderBy(" hrStaffTrackRecord.fdCreateTime desc");
							List<HrStaffTrackRecord> list1 = getHrStaffTrackRecordService().findList(info);
							if(null != list1 && list1.size() >0) {
								oldRecord =list1.get(0);
							}
						}

						map = new HashMap<String, String>();
						HrStaffPersonInfo personInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService()
								.findByPrimaryKey(
										record.getFdPersonInfo().getFdId(),
										HrStaffPersonInfo.class, true);
						SysOrgElement fdOrgPerson = record.getFdOrgPerson();
						map.put("fdId", record.getFdId());
						map.put("fdName", fdOrgPerson != null ? fdOrgPerson.getFdName() : null);
						map.put("fdStaffNo", null != personInfo ? personInfo.getFdStaffNo() : null);
						map.put("fdDeptName",
								fdOrgPerson != null ? fdOrgPerson.getFdParentsName()
										: null);
						map.put("fdOrgPostNames", fdOrgPerson != null ? getPostsName(fdOrgPerson.getFdPosts()) : null);
						String postName1 = "";
						if(null != oldRecord.getFdOrgPosts() && oldRecord.getFdOrgPosts().size()>0) {
							for (SysOrgPost post : oldRecord.getFdOrgPosts()) {
								postName1 = postName1+post.getFdName()+";";
							}
						}
						String postName2 = null != oldRecord.getFdHrOrgPost() ? oldRecord.getFdHrOrgPost().getFdName() : null;

						map.put("fdPostAfter", StringUtil.isNotNull(postName1)?postName1:postName2);
						map.put("fdPostBefore",
								fdOrgPerson != null
										? getPostsName(record.getFdOrgPosts())
										: null);
						map.put("fdTransferType", record.getFdTransType());
						String deptName1 = null != oldRecord.getFdRatifyDept() ? oldRecord.getFdRatifyDept().getFdName() : null;
						String deptName2 = null != oldRecord.getFdHrOrgDept() ? oldRecord.getFdHrOrgDept().getFdName() : null;

						map.put("fdTransferAfter", null != deptName1 ? deptName1 : deptName2);
						map.put("fdTransferBefore",
								null != record.getFdRatifyDept() ? record.getFdRatifyDept().getFdName() : null);
						map.put("fdTransferType", "2"); //调动类型
						map.put("fdSalaryAfter", null);
						map.put("fdSalaryBefore", null);
						map.put("fdEffectTime", DateUtil.convertDateToString(record.getFdTransDate(), DateUtil.PATTERN_DATE));
						map.put("personId",
								null != personInfo ? personInfo.getFdId() : null);
						map.put("fdCreator", record.getFdCreator().getFdName());
						//1、任职中  2、已结束  3、待任职
						map.put("docStatus", "30");
						if("3".equals(record.getFdStatus())) {
							map.put("fdIsEffective","false");
						} else {
							map.put("fdIsEffective","true");
						}
						map.put("delTrackMark", "true");
						list.add(map);
					}
				}

			}
			else if("salary".equals(type)){
				if(CollectionUtils.isNotEmpty(salaries)) {
					//薪资调动
					for (HrRatifySalary salary : salaries) {
						map = new HashMap<String, String>();
						HrStaffPersonInfo personInfo = getHrStaffPersonInfoService().findByOrgPersonId(salary.getFdSalaryStaff().getFdId());
						map.put("fdId", salary.getFdId());
						map.put("docStatus", salary.getDocStatus()); //状态
						map.put("fdIsEffective",String.valueOf(salary.getFdIsEffective())); //调动状态
						map.put("docNumber", salary.getDocNumber()); //流程编号
						//调薪
						map.put("fdId", salary.getFdId());
						map.put("fdName", salary.getFdSalaryStaff().getFdName());
						map.put("fdStaffNo", salary.getFdSalaryStaff().getFdNo()); // 工号
						map.put("fdDeptName", null != salary.getFdSalaryStaff().getFdParent()
								? salary.getFdSalaryStaff().getFdParent().getFdName() : ""); //部门
						map.put("fdOrgPostNames", getPostsName(salary.getFdSalaryStaff().getFdPosts())); //岗位
						map.put("fdTransferType", "1"); //调动类型
						map.put("fdTransferAfter",
								null != salary.getFdSalaryDept() ? salary.getFdSalaryDept().getFdName() : null); //调动前部门
						map.put("fdTransferBefore",
								null != salary.getFdSalaryDept() ? salary.getFdSalaryDept().getFdName() : null); //调动后部门
						map.put("fdSalaryBefore", null != salary.getFdSalaryBefore() ? salary.getFdSalaryBefore().toString() : ""); //调动前薪资
						map.put("fdSalaryAfter", null != salary.getFdSalaryAfter() ? salary.getFdSalaryAfter().toString() : ""); //调动后薪资
						map.put("fdEffectTime", null != salary.getFdSalaryDate() ?DateUtil.convertDateToString(salary.getFdSalaryDate(), DateUtil.PATTERN_DATE) : ""); //生效日期
						map.put("personId", null != personInfo ? personInfo.getFdId() : "");
						map.put("delTrackMark", "false");
						list.add(map);
					}
				}
				if(CollectionUtils.isNotEmpty(detalieds)) {
					for (HrStaffEmolumentWelfareDetalied detalied : detalieds) {
						map = new HashMap<String, String>();
						HrStaffPersonInfo personInfo = getHrStaffPersonInfoService()
								.findByOrgPersonId(
										detalied.getFdPersonInfo().getFdId());
						if (null != personInfo) {
							SysOrgPerson orgPerson = personInfo.getFdOrgPerson();
							map.put("fdId", detalied.getFdId());
							map.put("fdName", personInfo.getFdName());
							map.put("fdStaffNo", personInfo.getFdStaffNo());
							map.put("fdDeptName", orgPerson != null ? orgPerson.getFdParentsName() : null);
							map.put("fdOrgPostNames", orgPerson != null ? getPostsName(orgPerson.getFdPosts()) : null);
							map.put("fdTransferType", null);
							map.put("fdTransferAfter", null);
							map.put("fdTransferBefore", null);
							map.put("fdTransferType", "1"); //调动类型
							map.put("fdSalaryAfter", null != detalied.getFdAfterEmolument() ? detalied.getFdAfterEmolument().toString() : null);
							map.put("fdEffectTime",DateUtil.convertDateToString(detalied.getFdEffectTime(), DateUtil.PATTERN_DATE));
							map.put("personId", detalied.getFdPersonInfo().getFdId());
							map.put("fdCreator", detalied.getFdCreator().getFdName());
							map.put("delSalarykMark", "true");
							map.put("docStatus", "30");
							//调动状态
							map.put("fdIsEffective",String.valueOf(detalied.getFdIsEffective()));
							list.add(map);
						}
					}
				}
			}
			if(CollectionUtils.isNotEmpty(list)) {
				//岗位调动
				ListSortUtil.sort(list, "fdEffectTime", true);
				int endIndex = list.size() > page.getStart() + page.getRowsize() ? page.getStart() + page.getRowsize() : list.size();
				list = list.subList(page.getStart(), endIndex);
				page.setList(list);
			}
		}
		return page;
	}

	private String getPostsName(List posts){
		String postsName = null;
		for(int i=0; i<posts.size(); i++){
			SysOrgPost orgPost = (SysOrgPost)posts.get(i);
			postsName = StringUtil.isNull(postsName) ? orgPost.getFdName() : postsName + "," + orgPost.getFdName();
		}
		return postsName;
	}

	private void changeFindPageHQLInfo(HQLInfo hqlInfo, HttpServletRequest request, String field,String modeName) throws Exception {
		String hqlWhere = hqlInfo.getWhereBlock();
		StringBuffer whereBlock = null;
		if (StringUtil.isNotNull(hqlWhere)) {
			whereBlock = new StringBuffer(hqlWhere);
		} else {
			whereBlock = new StringBuffer("1 = 1");
		}
		CriteriaValue cv = new CriteriaValue(request);
		String _fdKey = cv.poll("_fdKey");
		String[] _fdDepts = cv.polls("_fdDept");
		// 姓名、登录名、手机号或邮箱
		if (StringUtil.isNotNull(_fdKey)) {
			// 查询组织机构人员信息
			HQLInfo _hqlInfo = new HQLInfo();
			_hqlInfo.setSelectBlock("sysOrgPerson.fdId");
			_hqlInfo.setWhereBlock(
					"sysOrgPerson.fdName like :fdKey or sysOrgPerson.fdLoginName like :fdKey or sysOrgPerson.fdMobileNo like :fdKey or sysOrgPerson.fdEmail like :fdKey");
			_hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
			List<String> ids = getSysOrgPersonService().findValue(_hqlInfo);

			whereBlock.append(
					" and ((" + field + ".fdName like :fdKey or " + field + ".fdMobileNo like :fdKey or " + field
							+ ".fdEmail like :fdKey)");
			if (!ids.isEmpty()) {
				whereBlock.append(" or " + HQLUtil.buildLogicIN(field + ".fdId", ids));
			}
			whereBlock.append(")");
			hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
		}

		// 部门
		if (_fdDepts != null && _fdDepts.length > 0) {
			String staffWhereBlock = field + ".fdHierarchyId like :fdDept0";
			for (int i = 1; i < _fdDepts.length; i++) {
				staffWhereBlock = StringUtil.linkString(staffWhereBlock, " or ",
						field + ".fdHierarchyId like :fdDept" + i);
				hqlInfo.setParameter("fdDept" + i, "%" + _fdDepts[i] + "%");
			}
			whereBlock.append(" and (" + staffWhereBlock + ")");
			hqlInfo.setParameter("fdDept0", "%" + _fdDepts[0] + "%");
		}
		String ids = request.getParameter("ids");
		if(StringUtil.isNotNull(ids)) {
			List idList = Arrays.asList(ids.split(";"));
			whereBlock.append(" and " + HQLUtil.buildLogicIN(modeName + ".fdId", idList));
		}

		hqlInfo.setWhereBlock(whereBlock.toString());
	}

	@Override
	public WorkBook export(HQLInfo hqlInfo, HttpServletRequest request) throws Exception {
		String ids = request.getParameter("ids");
		List idList = Arrays.asList(ids.split(";"));
		String[] baseColumns = new String[] {
				getStr("mobile.hrStaffEntry.fdName"), //姓名
				getStr("hrRatifyEntry.fdStaffNo"), //工号
				getStr("hrRatifyPositive.fdPositiveDept.before"), // 异动前部门
				getStr("hrRatifyPositive.fdPositivePost.before"), // 异动前岗位
				getStr("hrRatifyPositive.fdPositiveDept.after"), // 异动后部门
				getStr("hrRatifyPositive.fdPositivePost.after"), // 异动后岗位
				getStr("hrRatify.concern.transfer.fdTransferType"), // 异动类型
				getStr("hrRatify.concern.transfer.fdEffectTime"), // 异动生效日期
				getStr("hrRatify.concern.transfer.fdDetail"), // 异动详情
				getStr("hrRatify.concern.transfer.flow.docStatus"), // 异动决策状态
				getStr("hrRatify.concern.transfer.docStatus"), // 异动状态
				getStr("hrRatify.concern.transfer.docNumber"), // 异动流程编号
				getStr("hrRatify.concern.transfer.handlerName") // 异动操作人
		};
		WorkBook wb = new WorkBook();
		String filename = "人事调动信息";
		wb.setLocale(request.getLocale());
		Sheet sheet = new Sheet();
		sheet.setTitle(filename);
		for (int i = 0; i < baseColumns.length; i++) {
			Column col = new Column();
			col.setTitle(baseColumns[i]);
			sheet.addColumn(col);
		}
		List contentList = new ArrayList();
		Object[] objs = null;
		hqlInfo.setRowSize(1000);
		Page page = getTransferManagePage(hqlInfo, request);
		List list = page.getList();
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = (Map<String, String>) list.get(i);
			String fdId = map.get("fdId");
			if (idList.contains(fdId)) {
				objs = new Object[sheet.getColumnList().size()];
				String docStatus = map.get("docStatus");
				objs[0] = map.get("fdName");
				objs[1] = map.get("fdStaffNo");
				objs[2] = map.get("fdTransferAfter"); // 异动前部门
				objs[3] = map.get("fdPostAfter");// 异动前岗位
				objs[4] = map.get("fdTransferBefore"); // 异动后部门
				objs[5] = map.get("fdPostBefore");// 异动后岗位
				String fdTransferType = map.get("fdTransferType");
				if (StringUtil.isNull(docStatus)) {
					switch (fdTransferType) {
						case "1":
							fdTransferType = "调薪";
							break;
						default:
							fdTransferType = "调动";
							break;
					}
				}
				objs[6] = fdTransferType;
				objs[7] = map.get("fdEffectTime");
				objs[8] = ""; // 异动详情
				String fdIsEffective = map.get("fdIsEffective");
				String fdIsEffectiveStr = "未生效";
				String docStatusValue = null;
				Integer status =0;
				if (null != docStatus) {
					status = Integer.valueOf(docStatus);
					if (status >= 30) {
						docStatusValue = "已完成";
					} else if (status >= 10 && status < 30) {
						docStatusValue = "进行中";
					} else {
						docStatusValue = "已撤销";
					}
				} else {
					docStatusValue = "已完成";
				}
				objs[9] = docStatusValue;
				//考虑历史数据 该字段是null，所以是null 默认是生效，然后流程状态是未完成的则设置未生效
				if(fdIsEffective !=null) {
					Boolean isEffective=Boolean.parseBoolean(fdIsEffective);
					if(isEffective) {
						docStatusValue ="已生效";
					}
				} else {
					docStatusValue ="已生效";
				}
				if(status < 30) {
					docStatusValue ="未生效";
				}
				objs[10] = docStatusValue;
				objs[11] = fdIsEffectiveStr;
				//流程编号不为空时，标示是
				if (map.get("docNumber") !=null) {
					objs[12] = getCurHanderNames(map.get("fdId"));
				} else {
					objs[12] = map.get("fdCreator");
				}
				contentList.add(objs);
			}
		}
		sheet.setContentList(contentList);
		wb.addSheet(sheet);
		wb.setFilename(filename);
		return wb;
	}

	private String getStr(String key) {
		return ResourceUtil.getString(key, "hr-ratify");
	}

	/**
	 * <p>获取当前流程节点操作人</p>
	 * @param id
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	private String getCurHanderNames(String id) throws Exception {
		String content = null;
		ILbpmProcessService lbpmProcessService = (ILbpmProcessService) SpringBeanUtil.getBean("lbpmProcessService");
		IBaseModel model = lbpmProcessService.findByPrimaryKey(id, null, true);
		content = ((ILbpmProcessCurrentInfoService) SpringBeanUtil.getBean("lbpmProcessCurrentInfoService"))
				.getCurHanderNames((LbpmProcess) model);
		return content;
	}

	@Override
	public JSONObject addImportData(InputStream inputStream, Locale locale) throws Exception {
		Workbook wb = null;
		org.apache.poi.ss.usermodel.Sheet sheet = null;
		JSONObject result = new JSONObject();
		JSONArray titles = new JSONArray(); // 标题头
		JSONArray errorRows = new JSONArray(); // 每个错误行（包含错误行号，错误列号，行的错误信息）
		JSONArray otherErrors = new JSONArray(); // 其他错误
		int columnSize = 7;
		int successCount = 0, failCount = 0;
		try {
			wb = WorkbookFactory.create(inputStream); // 抽象类创建Workbook 适合excel
			// 2003和2007以上
			sheet = wb.getSheetAt(0);

			// 数据必须大于columnSize-1列，且不能少于2行
			if (sheet.getLastRowNum() < 1 || sheet.getRow(0).getLastCellNum() < columnSize - 1) {
				otherErrors.add(getStr("hrRatify.concern.transfer.import.template.fileError"));
			} else {
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					int rowIndex = i + 1;
					JSONObject errorRow = new JSONObject();
					HrStaffTrackRecord trackRecord = new HrStaffTrackRecord();
					// 行不为空
					if (row == null) {
						continue;
					}
					// 每列都是空的行跳过
					int j = 0;
					for (; j < columnSize; j++) {
						if (StringUtil.isNotNull(HrRatifyUtil.getCellValue(row.getCell(j)))) {
							break;
						}
					}
					if (j == columnSize) {
						continue;
					}
					//姓名
					String nameValue = HrRatifyUtil.getCellValue(row.getCell(0));
					//工号
					String staffNoValue = HrRatifyUtil.getCellValue(row.getCell(1));
					//部门
					String deptValue = HrRatifyUtil.getCellValue(row.getCell(2));
					//岗位
					String postValue = HrRatifyUtil.getCellValue(row.getCell(3));
					//调动后岗位
					String newPostValue = HrRatifyUtil.getCellValue(row.getCell(4));
					//调动后部门
					String newDeptValue = HrRatifyUtil.getCellValue(row.getCell(5));
					//调动后薪酬
					String newSalaryValue = HrRatifyUtil.getCellValue(row.getCell(6));
					//异动生效时间
					String fdTransDate = HrRatifyUtil.getCellValue(row.getCell(7));

					SysOrgPerson orgPerson = null;
					if (StringUtil.isNotNull(staffNoValue)) {
						//通过工号查询员工
						HrStaffPersonInfo personInfo = getHrStaffPersonInfoService().findPersonInfoByStaffNo(staffNoValue);
						if (null != personInfo) {
							trackRecord.setFdPersonInfo(personInfo);
							orgPerson = personInfo.getFdOrgPerson();
						} else {
							addRowError(errorRow, rowIndex, 1,
									ResourceUtil.getString("hrRatify.concern.transfer.fdStaffNo.notnull", "hr-ratify"));
						}
					} else {
						addRowError(errorRow, rowIndex, 1,
								ResourceUtil.getString("hrRatify.concern.transfer.import.notnull", "hr-ratify", locale,
										getStr("hrStaffEntry.fdStaffNo")));
					}
					//调部门和调薪酬不能同时为空
					if ((StringUtil.isNull(newDeptValue) || StringUtil.isNull(newPostValue)) && StringUtil.isNull(newSalaryValue)) {
						addRowError(errorRow, rowIndex, 4,
								ResourceUtil.getString("hrRatify.concern.transfer.import.tips", "hr-ratify"));
					}
					String fdTransType = null, fdTransDetail = null, salaryDetail = null, deptDetail = null, parentsName = null,
							postDetail = null;
					if (StringUtil.isNotNull(newDeptValue)) {
						List<SysOrgElement> list = sysOrgCoreService.findByName(newDeptValue, SysOrgConstant.ORG_TYPE_DEPT);
						if (list == null || list.size() == 0) {
							addRowError(errorRow, rowIndex, 4,
									ResourceUtil.getString("hrRatify.concern.transfer.new.dept.notnull", "hr-ratify"));
						} else {
							trackRecord.setFdRatifyDept(list.get(0));
							fdTransType = ResourceUtil.getString("hrRatify.concern.transfer", "hr-ratify");
							deptDetail = ResourceUtil.getString("hrRatify.concern.transfer.dept.detail", "hr-ratify");
							parentsName = trackRecord.getFdRatifyDept().getFdParentsName() + "_" + trackRecord.getFdRatifyDept().getFdName();
						}
					}
					if (StringUtil.isNotNull(newPostValue)) {
						List list = sysOrgCoreService.findByName(newPostValue, SysOrgConstant.ORG_TYPE_POST);
						if (list == null || list.size() == 0) {
							addRowError(errorRow, rowIndex, 4,
									ResourceUtil.getString("hrRatify.concern.transfer.new.post.notnull", "hr-ratify"));
						} else {
							//根据导入时的部门信息剔除非该部门下面的岗位
							List<SysOrgPost> fdOrgPosts = list;
							List<SysOrgPost> fdOrgPostList = new ArrayList<SysOrgPost>();
							for (SysOrgPost sysOrgPost : fdOrgPosts) {
								if (null != sysOrgPost && StringUtil.isNotNull(sysOrgPost.getFdParentsName())) {
									if (sysOrgPost.getFdParentsName().equals(parentsName)) {
										fdOrgPostList.add(sysOrgPost);
									}
								}
							}
							trackRecord.setFdOrgPosts(fdOrgPostList);
							fdTransType = ResourceUtil.getString("hrRatify.concern.transfer", "hr-ratify");
							postDetail = ResourceUtil.getString("hrRatify.concern.transfer.post.detail", "hr-ratify");
						}
					}
					if (StringUtil.isNotNull(newSalaryValue)) {
						trackRecord.setFdTransSalary(Double.valueOf(newSalaryValue));
						fdTransType = StringUtil.isNull(fdTransType)
								? ResourceUtil.getString("hrRatify.concern.salary", "hr-ratify")
								: fdTransType + ResourceUtil.getString("hrRatify.concern.salary", "hr-ratify");
						salaryDetail = ResourceUtil.getString("hrRatify.concern.salary.detail", "hr-ratify") + newSalaryValue;
					}
					Date date = new Date();
					if (StringUtil.isNotNull(fdTransDate)) {
						date = DateUtil.convertStringToDate(fdTransDate, DateUtil.PATTERN_DATE);
					}
					if (null != trackRecord.getFdPersonInfo()) {
						fdTransDetail = ResourceUtil.getString("hrRatify.concern.transfer.detail", "hr-ratify", locale,
								new Object[]{UserUtil.getKMSSUser().getUserName(),
										trackRecord.getFdPersonInfo().getFdName(), fdTransType});
					}
					if (StringUtil.isNotNull(deptDetail)) {
						fdTransDetail = fdTransDetail + deptDetail;
					}
					if (StringUtil.isNotNull(postDetail)) {
						fdTransDetail = fdTransDetail + postDetail;
					}
					if (StringUtil.isNotNull(salaryDetail)) {
						fdTransDetail = fdTransDetail + salaryDetail;
					}
					trackRecord.setFdTransType(fdTransType);
					trackRecord.setFdMemo(fdTransDetail);
					trackRecord.setFdCreateTime(new Date());
					trackRecord.setFdEntranceBeginDate(new Date());
					trackRecord.setFdCreator(UserUtil.getUser());
					trackRecord.setFdOrgPerson(orgPerson);
					trackRecord.setFdTransDate(date);

					// 有错误
					if (errorRow.get("errRowNumber") != null) {
						// 当前行的内容
						JSONArray contents = new JSONArray();
						for (int k = 0; k < columnSize; k++) {
							String value = HrRatifyUtil.getCellValue(row.getCell(k));
							contents.add(value);
						}
						errorRow.put("contents", contents);
						errorRows.add(errorRow);
						failCount++;
					} else {
						try {
							getHrStaffTrackRecordService().addTransfer(trackRecord);
							successCount++;
						} catch (Exception e) {
							e.printStackTrace();
							otherErrors.add(e.getMessage());
							failCount++;
						}

					}
				}
				int hasError = 0;
				if (otherErrors.size() > 0 || errorRows.size() > 0) {
					hasError = 1;
				}
				result.put("hasError", hasError);
				result.put("errorRows", errorRows);
				if (hasError == 1) { // 有错误
					titles.add(getStr("hrRatify.concern.transfer.lineNumber")); // 行号
					titles.add(getStr("mobile.hrStaffEntry.fdName"));
					titles.add(getStr("hrStaffEntry.fdStaffNo"));
					titles.add(getStr("hrRatifySalary.fdSalaryDept"));
					titles.add(getStr("hrRatifySalary.fdSalaryPost"));
					titles.add(getStr("hrRatify.concern.transfer.new.post"));
					titles.add(getStr("hrRatify.concern.transfer.new.dept"));
					titles.add(getStr("hrRatify.concern.transfer.new.salary"));

					titles.add(getStr("hrRatify.concern.transfer.errorDetails")); // 错误详情
					result.put("titles", titles);
					String importMsg = ResourceUtil.getString("hrRatify.concern.transfer.import.format.msg", "hr-ratify",
							locale, new Object[]{successCount, failCount});
					result.put("importMsg", importMsg);
				} else { // 无错误
					String importMsg = ResourceUtil.getString("hrRatify.concern.transfer.import.format.msg.succ",
							"hr-ratify",
							locale, new Object[]{successCount});
					result.put("importMsg", importMsg);
				}
			}
		} catch (IOException e) {
			otherErrors.add(e.getMessage());
		} finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(inputStream);
		}
		result.put("otherErrors", otherErrors);
		return result;
	}

	private void addRowError(JSONObject errorRow, int rowIndex, int colIndex, String msg) {
		errorRow.put("errRowNumber", rowIndex);
		if (errorRow.get("errColNumbers") == null) {
			errorRow.put("errColNumbers", colIndex + "");
		} else {
			errorRow.put("errColNumbers", errorRow.getString("errColNumbers") + "," + colIndex);
		}
		if (errorRow.get("errInfos") == null) {
			errorRow.put("errInfos", msg);
		} else {
			errorRow.put("errInfos", errorRow.getString("errInfos") + "<br>" + msg);
		}
	}

	@Override
	public WorkBook buildTemplateWorkbook(HttpServletRequest request) throws Exception {
		String[] baseColumns = new String[] { getStr("mobile.hrStaffEntry.fdName"), //姓名
				getStr("hrStaffEntry.fdStaffNo"), // 工号
				getStr("hrRatifySalary.fdSalaryDept"), // 部门
				getStr("hrRatifySalary.fdSalaryPost"), // 岗位
				getStr("hrRatify.concern.transfer.new.post"), // 调动后岗位
				getStr("hrRatify.concern.transfer.new.dept"), // 调动后部门
				getStr("hrRatify.concern.transfer.new.salary"), // 调动后薪酬
				getStr("hrRatify.concern.transfer.fdEffectTime"), // 异动生效日期
		};
		// 必填的列的下标
		Integer[] notNullArr = new Integer[] { 1 };
		List notNullList = Arrays.asList(notNullArr);
		String filename = getStr("hrRatify.concern.transfer.import.templateFile");
		WorkBook wb = new WorkBook();
		wb.setLocale(request.getLocale());
		Sheet sheet = new Sheet();
		sheet.setTitle(filename);
		for (int i = 0; i < baseColumns.length; i++) {
			Column col = new Column();
			col.setTitle(baseColumns[i]);
			if (notNullList.contains(i)) {
				col.setRedFont(true);
			}
			sheet.addColumn(col);
		}
		List contentList = new ArrayList();
		// 样例数据
		Object[] objs = new Object[sheet.getColumnList().size()];
		objs[0] = "张三";
		objs[1] = "0001";
		objs[2] = "研发中心";
		objs[3] = "Java开发工程师";
		objs[4] = "测试工程师";
		objs[5] = "测试部";
		objs[6] = "12000";
		objs[7] = "2019-11-11";
		contentList.add(objs);
		sheet.setContentList(contentList);
		wb.addSheet(sheet);
		wb.setFilename(filename);
		return wb;
	}

}
