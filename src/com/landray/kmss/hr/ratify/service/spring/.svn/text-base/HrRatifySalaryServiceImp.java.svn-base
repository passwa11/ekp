package com.landray.kmss.hr.ratify.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.ratify.model.HrRatifyAgendaConfig;
import com.landray.kmss.hr.ratify.model.HrRatifyMKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.hr.ratify.model.HrRatifySalary;
import com.landray.kmss.hr.ratify.model.HrRatifyTKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifySalaryService;
import com.landray.kmss.hr.ratify.util.HrRatifyTitleUtil;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.hr.staff.model.HrStaffEmolumentWelfareDetalied;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffRatifyLog;
import com.landray.kmss.hr.staff.service.IHrStaffEmolumentWelfareDetaliedService;
import com.landray.kmss.hr.staff.util.HrStaffDateUtil;
import com.landray.kmss.sys.archives.config.SysArchivesConfig;
import com.landray.kmss.sys.archives.interfaces.IArchFileDataService;
import com.landray.kmss.sys.archives.model.SysArchivesFileTemplate;
import com.landray.kmss.sys.archives.model.SysArchivesParamModel;
import com.landray.kmss.sys.archives.service.ISysArchivesFileTemplateService;
import com.landray.kmss.sys.archives.util.SysArchivesUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzModelContext;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowDiscard;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowFinish;
import com.landray.kmss.sys.xform.interfaces.XFormUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONObject;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HrRatifySalaryServiceImp extends HrRatifyMainServiceImp
		implements IHrRatifySalaryService, IArchFileDataService {

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	private IHrStaffEmolumentWelfareDetaliedService hrStaffEmolumentWelfareDetaliedService;

	public void setHrStaffEmolumentWelfareDetaliedService(
			IHrStaffEmolumentWelfareDetaliedService hrStaffEmolumentWelfareDetaliedService) {
		this.hrStaffEmolumentWelfareDetaliedService = hrStaffEmolumentWelfareDetaliedService;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		HrRatifySalary hrRatifySalary = new HrRatifySalary();
		hrRatifySalary.setDocCreateTime(new Date());
		hrRatifySalary.setDocCreator(UserUtil.getUser());
		hrRatifySalary.setFdDepartment(UserUtil.getUser().getFdParent());
		String templateId = requestContext.getParameter("i.docTemplate");
		if (com.landray.kmss.util.StringUtil.isNotNull(templateId)) {
			com.landray.kmss.hr.ratify.model.HrRatifyTemplate docTemplate = (com.landray.kmss.hr.ratify.model.HrRatifyTemplate) getHrRatifyTemplateService()
					.findByPrimaryKey(templateId);
			if (docTemplate != null) {
				hrRatifySalary.setDocTemplate(docTemplate);
				List<HrRatifyTKeyword> keyWordList = docTemplate
						.getDocKeyword();
				List tKeyword = new ArrayList();
				for (HrRatifyTKeyword keyWord : keyWordList) {
					HrRatifyMKeyword mKeyword = new HrRatifyMKeyword();
					mKeyword.setDocKeyword(keyWord.getDocKeyword());
					tKeyword.add(mKeyword);
				}
				hrRatifySalary.setDocKeyword(tKeyword);
			}
		}
		HrRatifyUtil.initModelFromRequest(hrRatifySalary, requestContext);
		if (hrRatifySalary.getDocTemplate() != null) {
			hrRatifySalary.setExtendFilePath(
					XFormUtil.getFileName(hrRatifySalary.getDocTemplate(),
							hrRatifySalary.getDocTemplate().getFdTempKey()));
			if (Boolean.FALSE.equals(
					hrRatifySalary.getDocTemplate().getDocUseXform())) {
				hrRatifySalary.setDocXform(
						hrRatifySalary.getDocTemplate().getDocXform());
			}
			hrRatifySalary.setDocUseXform(
					hrRatifySalary.getDocTemplate().getDocUseXform());
		}
		return hrRatifySalary;
	}

	@Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
		HrRatifySalary hrRatifySalary = (HrRatifySalary) model;
		if (hrRatifySalary.getDocTemplate() != null) {
			dispatchCoreService.initFormSetting(form,
					hrRatifySalary.getDocTemplate().getFdTempKey(),
					hrRatifySalary.getDocTemplate(),
					hrRatifySalary.getDocTemplate().getFdTempKey(),
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
		HrRatifySalary hrRatifySalary = (HrRatifySalary) mainModel;
		if (!"10".equals(mainModel.getDocStatus())) {
			String docNumber = getSysNumberFlowService()
					.generateFlowNumber(hrRatifySalary);
			hrRatifySalary.setDocNumber(docNumber);
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null) {
			return;
		}
		Object obj = event.getSource();
		if (!(obj instanceof HrRatifySalary)) {
			return;
		}
		if (event instanceof Event_SysFlowFinish) {
			try {
				String flowFlag = "finish";
				updateSalary(obj, flowFlag);
				feedbackNotify((HrRatifySalary) obj);
				addLog((HrRatifySalary) obj);
			} catch (Exception e1) {
				throw new KmssRuntimeException(e1);
			}
		} else if (event instanceof Event_SysFlowDiscard) {
			try {
				String flowFlag = "discard";
				//#133210 流程废弃不需要写入薪酬明细福利
				//updateSalary(obj, flowFlag);
				HrRatifySalary salary = (HrRatifySalary) obj;
				Date now = new Date();
				salary.setDocPublishTime(now);
			} catch (Exception e2) {
				throw new KmssRuntimeException(e2);
			}
		}
	}

	private void updateSalary(Object obj,String flowFlag) throws Exception {
		HrRatifySalary salary = (HrRatifySalary) obj;
		salary.setFdIsEffective(Boolean.TRUE);
		Date now = new Date();
		HrRatifyAgendaConfig config = new HrRatifyAgendaConfig();
		if ("true".equals(config.getFdHrStaffSalary())) {
			HrStaffEmolumentWelfareDetalied detail = new HrStaffEmolumentWelfareDetalied();
			detail.setFdRelatedProcess(HrRatifyUtil.getUrl(salary));
			detail.setFdAdjustDate(now);
			detail.setFdEffectTime(salary.getFdSalaryDate());
			detail.setFdBeforeEmolument(salary.getFdSalaryBefore());
			detail.setFdAdjustAmount(salary.getFdSalaryAfter() - salary.getFdSalaryBefore());
			detail.setFdAfterEmolument(salary.getFdSalaryAfter());
			detail.setFdPersonInfo(getHrStaffPersonInfoService().findByOrgPersonId(salary.getFdSalaryStaff().getFdId()));
			detail.setFdCreator(UserUtil.getUser());
			detail.setFdCreateTime(now);
			if("finish".equals(flowFlag)){//流程通过时
				if(salary.getFdSalaryDate().after(now)){
					detail.setFdIsEffective(Boolean.FALSE);
					salary.setFdIsEffective(Boolean.FALSE);
					//设置定时任务
					updateStaffPersonInfoJob(salary.getFdSalaryDate(), salary.getFdSalaryStaff().getFdId(), detail.getFdId(),salary.getFdId());
				}else{
					HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo)getHrStaffPersonInfoService().findByPrimaryKey(salary.getFdSalaryStaff().getFdId());
					hrStaffPersonInfo.setFdSalary(salary.getFdSalaryAfter());
					detail.setFdIsEffective(Boolean.TRUE);
				}
			}
			hrStaffEmolumentWelfareDetaliedService.add(detail);
		}
		salary.setDocPublishTime(now);
		super.update(salary);
	}

	/**
	 * 人员调薪的定时任务
	 */
	@Override
    public void setSalaryJob(SysQuartzJobContext context){
		try {
			String parameter = context.getParameter();
			//第一个要人员ID，第二个是薪酬ID，第3个是调薪流程ID
			String[] strArr = parameter.split(";");
			getHrStaffPersonInfoService().setSalarySchedulerJob(context);
			HrRatifySalary salary =(HrRatifySalary)findByPrimaryKey(strArr[2]);
			salary.setFdIsEffective(Boolean.TRUE);
			super.update(salary);
		}catch (Exception e) {
			logger.error("设置员工薪资操作出错", e);
		}
	}

	private void updateStaffPersonInfoJob(Date fdEffectTime, String fdId, String detailId,String salaryId) throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fdEffectTime);
		String cron = HrStaffDateUtil.getCronExpression(calendar);
		HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo)getHrStaffPersonInfoService().findByPrimaryKey(fdId);
		if (StringUtil.isNotNull(cron)) {
			SysQuartzModelContext quartzContext = new SysQuartzModelContext();
			quartzContext.setQuartzJobMethod("setSalaryJob");
			quartzContext.setQuartzJobService("hrRatifySalaryService");
			quartzContext.setQuartzKey("setSalaryJob");
			quartzContext.setQuartzParameter(String.format("%s;%s;%s", fdId,detailId,salaryId));
			quartzContext.setQuartzSubject(hrStaffPersonInfo.getFdName() + "的调薪流程通过后，薪资变更定时任务");
			quartzContext.setQuartzRequired(true);
			quartzContext.setQuartzCronExpression(cron);
			getSysQuartzCoreService().saveScheduler(quartzContext, hrStaffPersonInfo);
		}

	}

	@Override
	public void addLog(HrRatifyMain mainModel) throws Exception {
		HrRatifySalary salary = (HrRatifySalary) mainModel;
		HrStaffRatifyLog log = new HrStaffRatifyLog();
		log.setFdRatifyType("leave");
		log.setFdRatifyDept(salary.getFdSalaryDept());
		log.setFdRatifyDate(salary.getFdSalaryDate());
		JSONObject json = new JSONObject();
		json.put("docSubject", salary.getDocSubject());
		json.put("url", HrRatifyUtil.getUrl(salary));
		log.setFdRatifyProcess(json.toString());
		getHrStaffRatifyLogService().add(log);
	}

	@Override
	public void deleteEntity(HrRatifyMain mainModel) throws Exception {
		HrRatifySalary salaryModel = (HrRatifySalary) findByPrimaryKey(
				mainModel.getFdId());
		super.deleteEntity(salaryModel);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		HrRatifySalary salaryModel = (HrRatifySalary) modelObj;
		if (StringUtil.isNull(salaryModel.getDocNumber())) {
			setDocNumber(salaryModel);
		}
		HrRatifyTitleUtil.genTitle(salaryModel);
		super.update(salaryModel);
	}

	/*历史归档机制*/
	/*@Override
	public void addFileMainDoc(HttpServletRequest request, String fdId,
							   KmArchivesFileTemplate fileTemplate) throws Exception {
		HrRatifySalary mainModel = (HrRatifySalary) findByPrimaryKey(fdId);
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
							 HrRatifySalary mainModel, KmArchivesFileTemplate fileTemplate)
			throws Exception {
		KmArchivesMain kmArchivesMain = new KmArchivesMain();
		kmArchivesFileTemplateService.setFileField(kmArchivesMain, fileTemplate,
				mainModel);
		// 归档页面URL(若为多表单，暂时归档默认表单)
		int saveApproval = fileTemplate.getFdSaveApproval() != null
				&& fileTemplate.getFdSaveApproval() ? 1 : 0;
		String url = "/hr/ratify/hr_ratify_salary/hrRatifySalary.do?method=printFileDoc&fdId="
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
			HrRatifySalary mainModel= (HrRatifySalary) findByPrimaryKey(fdId.split(",")[0]);
			// 只有结束和已反馈的文档可以归档
			if (!"30".equals(mainModel.getDocStatus())
					&& !"31".equals(mainModel.getDocStatus())) {
				throw new KmssRuntimeException(
						new KmssMessage("sys-archives:file.notsupport"));
			}
			HrRatifyTemplate template = mainModel.getDocTemplate();
			ISysArchivesFileTemplateService sysArchivesFileTemplateService= (ISysArchivesFileTemplateService)SpringBeanUtil.getBean("sysArchivesFileTemplateService");
			SysArchivesFileTemplate fileTemp = sysArchivesFileTemplateService.getFileTemplate(template,null);
			if(fileTemp!=null){
				SysArchivesParamModel paramModel = new SysArchivesParamModel();
				paramModel.setAuto("0");
				paramModel.setFileName(mainModel.getDocSubject()+".html");
				// 归档页面URL(若为多表单，暂时归档默认表单)
				int saveApproval = fileTemp.getFdSaveApproval() != null
						&& fileTemp.getFdSaveApproval() ? 1 : 0;
				//流程自动归档
				String fdModelId = mainModel.getFdId();
				String url = "/hr/ratify/hr_ratify_salary/hrRatifySalary.do?method=printFileDoc&fdId="
						+ mainModel.getFdId() + "&s_xform=default&saveApproval="
						+ saveApproval;
				paramModel.setUrl(url);
				sysArchivesFileTemplateService.addArchFileModel(request,mainModel,paramModel,fileTemp);

				mainModel.setFdIsFiling(true);
				super.update(mainModel);
			}else{
				return;
			}

			if (UserOperHelper.allowLogOper("fileDoc", "*")) {
				UserOperContentHelper.putAdd(mainModel)
						.putSimple("docTemplate", fileTemp);
			}
		}
	}
}
