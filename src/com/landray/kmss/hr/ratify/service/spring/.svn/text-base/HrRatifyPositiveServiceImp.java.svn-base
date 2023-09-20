package com.landray.kmss.hr.ratify.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.ratify.model.HrRatifyAgendaConfig;
import com.landray.kmss.hr.ratify.model.HrRatifyMKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.hr.ratify.model.HrRatifyPositive;
import com.landray.kmss.hr.ratify.model.HrRatifyTKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifyPositiveService;
import com.landray.kmss.hr.ratify.util.HrRatifyTitleUtil;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffRatifyLog;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.archives.config.SysArchivesConfig;
import com.landray.kmss.sys.archives.interfaces.IArchFileDataService;
import com.landray.kmss.sys.archives.model.SysArchivesFileTemplate;
import com.landray.kmss.sys.archives.model.SysArchivesParamModel;
import com.landray.kmss.sys.archives.service.ISysArchivesFileTemplateService;
import com.landray.kmss.sys.archives.util.SysArchivesUtil;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessCurrentInfoService;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzModelContext;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowDiscard;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowFinish;
import com.landray.kmss.sys.xform.interfaces.XFormUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.excel.Column;
import com.landray.kmss.util.excel.Sheet;
import com.landray.kmss.util.excel.WorkBook;
import net.sf.json.JSONObject;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HrRatifyPositiveServiceImp extends HrRatifyMainServiceImp
		implements IHrRatifyPositiveService, IArchFileDataService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	protected IHrStaffPersonInfoService getHrStaffPersonInfoServiceImp() {
		if (hrStaffPersonInfoService == null) {
			hrStaffPersonInfoService = (IHrStaffPersonInfoService) SpringBeanUtil.getBean("hrStaffPersonInfoService");
		}
		return hrStaffPersonInfoService;
	}

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrRatifyPositive hrRatifyPositive = new HrRatifyPositive();
		hrRatifyPositive.setDocCreateTime(new Date());
		hrRatifyPositive.setDocCreator(UserUtil.getUser());
		hrRatifyPositive.setFdDepartment(UserUtil.getUser().getFdParent());
		String templateId = requestContext.getParameter("i.docTemplate");
		if (com.landray.kmss.util.StringUtil.isNotNull(templateId)) {
			com.landray.kmss.hr.ratify.model.HrRatifyTemplate docTemplate = (com.landray.kmss.hr.ratify.model.HrRatifyTemplate) getHrRatifyTemplateService()
					.findByPrimaryKey(templateId);
			if (docTemplate != null) {
				hrRatifyPositive.setDocTemplate(docTemplate);
				List<HrRatifyTKeyword> keyWordList = docTemplate
						.getDocKeyword();
				List tKeyword = new ArrayList();
				for (HrRatifyTKeyword keyWord : keyWordList) {
					HrRatifyMKeyword mKeyword = new HrRatifyMKeyword();
					mKeyword.setDocKeyword(keyWord.getDocKeyword());
					tKeyword.add(mKeyword);
				}
				hrRatifyPositive.setDocKeyword(tKeyword);
			}
		}
        HrRatifyUtil.initModelFromRequest(hrRatifyPositive, requestContext);
		if (hrRatifyPositive.getDocTemplate() != null) {
			hrRatifyPositive.setExtendFilePath(
					XFormUtil.getFileName(hrRatifyPositive.getDocTemplate(),
							hrRatifyPositive.getDocTemplate().getFdTempKey()));
			if (Boolean.FALSE.equals(
					hrRatifyPositive.getDocTemplate().getDocUseXform())) {
				hrRatifyPositive.setDocXform(
						hrRatifyPositive.getDocTemplate().getDocXform());
			}
			hrRatifyPositive.setDocUseXform(
					hrRatifyPositive.getDocTemplate().getDocUseXform());
		}
        return hrRatifyPositive;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrRatifyPositive hrRatifyPositive = (HrRatifyPositive) model;
		if (hrRatifyPositive.getDocTemplate() != null) {
			dispatchCoreService.initFormSetting(form,
					hrRatifyPositive.getDocTemplate().getFdTempKey(),
					hrRatifyPositive.getDocTemplate(),
					hrRatifyPositive.getDocTemplate().getFdTempKey(),
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
		HrRatifyPositive hrRatifyPositive = (HrRatifyPositive) mainModel;
		if (!"10".equals(mainModel.getDocStatus())) {
			String docNumber = getSysNumberFlowService()
					.generateFlowNumber(hrRatifyPositive);
			hrRatifyPositive.setDocNumber(docNumber);
		}
	}

	private void saveSchedulerJob(RequestContext requestContext,
			HrRatifyPositive positive) throws Exception {
		Date formalDate = positive.getFdPositiveFormalDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(formalDate);
		String cron = HrRatifyUtil.getCronExpression(calendar);
		if (StringUtil.isNotNull(cron)) {
			SysQuartzModelContext quartzContext = new SysQuartzModelContext();
			quartzContext.setQuartzJobMethod("schedulerOfficial");
			quartzContext.setQuartzJobService("hrRatifyPositiveService");
			quartzContext.setQuartzKey("hrRatifyPositiveQuartzOfficial");
			quartzContext.setQuartzParameter(positive.getFdId().toString());
			quartzContext.setQuartzSubject(positive.getDocSubject());
			quartzContext.setQuartzRequired(true);
			quartzContext.setQuartzCronExpression(cron);
			getSysQuartzCoreService().saveScheduler(quartzContext, positive);
		}
	}

	@Override
	public void schedulerOfficial(SysQuartzJobContext context)
			throws Exception {
		HrRatifyPositive positive = (HrRatifyPositive) findByPrimaryKey(
				context.getParameter(), HrRatifyPositive.class, true);
		HrStaffPersonInfo hrStaffPersonInfo = getHrStaffPersonInfoService()
				.findByOrgPersonId(positive.getFdPositiveStaff().getFdId());
		schedulerOfficial(positive, hrStaffPersonInfo);
		getSysQuartzCoreService().delete(positive,
				"hrRatifyPositiveQuartzOfficial");
	}

	public void schedulerOfficial(HrRatifyPositive positive,
			HrStaffPersonInfo hrStaffPersonInfo) throws Exception {
		hrStaffPersonInfo.setFdStatus("official");
		hrStaffPersonInfo.setFdPositiveTime(positive.getFdPositiveFormalDate());
		hrStaffPersonInfo
				.setFdTrialOperationPeriod(positive.getFdPositiveTrialPeriod());
		getHrStaffPersonInfoService().update(hrStaffPersonInfo);
		String fdDetails = "通过员工转正流程，修改了“"
				+ positive.getFdPositiveStaff().getFdName()
				+ "”的员工信息：员工状态、转正日期、试用期限（月）。";
		addUpdateLog(fdDetails, hrStaffPersonInfo);
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null) {
			return;
		}
		Object obj = event.getSource();
		if (!(obj instanceof HrRatifyPositive)) {
			//如果
			return;
		}
		if (event instanceof Event_SysFlowFinish) {
			try {
				HrRatifyPositive positive = (HrRatifyPositive) obj;
				Date now = new Date();
				HrRatifyAgendaConfig config = new HrRatifyAgendaConfig();
				if ("true".equals(config.getFdHrStaffStatus())) {
					Date formalDate = positive.getFdPositiveFormalDate();
					if(null == formalDate) {
						if(null != positive.getFdPositivePeriodDate()) {
							formalDate = positive.getFdPositivePeriodDate();
						}else {
							formalDate = now ;
						}
						positive.setFdPositiveFormalDate(formalDate);
					}
					if (now.getTime() >= formalDate.getTime()) {
						HrStaffPersonInfo hrStaffPersonInfo = getHrStaffPersonInfoService()
								.findByOrgPersonId(
										positive.getFdPositiveStaff()
												.getFdId());
						schedulerOfficial(positive, hrStaffPersonInfo);
					} else {
						saveSchedulerJob(new RequestContext(), positive);
					}
				}
				positive.setDocPublishTime(now);
				this.update(positive);
				feedbackNotify(positive);
				addLog(positive);
			} catch (Exception e1) {
				throw new KmssRuntimeException(e1);
			}
		} else if (event instanceof Event_SysFlowDiscard) {
			try {
				HrRatifyPositive positive = (HrRatifyPositive) obj;
				Date now = new Date();
				positive.setDocPublishTime(now);
				this.update(positive);
			} catch (Exception e1) {
				throw new KmssRuntimeException(e1);
			}
		}
	}

	@Override
	public void addLog(HrRatifyMain mainModel) throws Exception {
		HrRatifyPositive positive = (HrRatifyPositive) mainModel;
		HrStaffRatifyLog log = new HrStaffRatifyLog();
		log.setFdRatifyType("positive");
		log.setFdRatifyDept(positive.getFdPositiveDept());
		log.setFdRatifyDate(positive.getFdPositiveFormalDate());
		JSONObject json = new JSONObject();
		json.put("docSubject", positive.getDocSubject());
		json.put("url", HrRatifyUtil.getUrl(positive));
		log.setFdRatifyProcess(json.toString());
		getHrStaffRatifyLogService().add(log);
	}

	@Override
	public void deleteEntity(HrRatifyMain mainModel) throws Exception {
		HrRatifyPositive positiveModel = (HrRatifyPositive) findByPrimaryKey(
				mainModel.getFdId());
		super.deleteEntity(positiveModel);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		HrRatifyPositive positiveModel = (HrRatifyPositive) modelObj; 
		if (StringUtil.isNull(positiveModel.getDocNumber())) {
			setDocNumber(positiveModel); 
		}
		HrRatifyTitleUtil.genTitle(positiveModel);
		super.update(positiveModel);
	}

	/*历史归档机制*/
	/*@Override
	public void addFileMainDoc(HttpServletRequest request, String fdId,
			KmArchivesFileTemplate fileTemplate) throws Exception {
		HrRatifyPositive mainModel = (HrRatifyPositive) findByPrimaryKey(fdId);
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
			HrRatifyPositive mainModel, KmArchivesFileTemplate fileTemplate)
			throws Exception {
		KmArchivesMain kmArchivesMain = new KmArchivesMain();
		kmArchivesFileTemplateService.setFileField(kmArchivesMain, fileTemplate,
				mainModel);
		// 归档页面URL(若为多表单，暂时归档默认表单)
		int saveApproval = fileTemplate.getFdSaveApproval() != null
				&& fileTemplate.getFdSaveApproval() ? 1 : 0;
		String url = "/hr/ratify/hr_ratify_positive/hrRatifyPositive.do?method=printFileDoc&fdId="
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
	public WorkBook export(List<HrStaffPersonInfo> personInfos, HttpServletRequest request) throws Exception {
		String[] baseColumns = new String[] { getStr("mobile.hrStaffEntry.fdName"), //姓名
				getStr("hrRatifyEntry.fdStaffNo"), //工号
				getStr("hrRatifyPositive.fdPositiveDept"), // 所在部门
				getStr("hrRatifyPositive.fdPositivePost"), // 所属岗位
				getStr("hrRatifyPositive.fdStatus"), // 员工状态
				getStr("hrRatifyLeave.fdLeaveEnterDate"), // 入职日期
				getStr("hrRatifyEntry.fdEntryTrialPeriod"), // 使用期限
				getStr("hrRatifyPositive.fdPositiveTime"), // 转正日期
				getStr("hrRatifyPositive.positiveDocNumber"), // 转正审批号
				getStr("hrRatifyPositive.lbpm_main_listcolumn_node") // 审核状态
		};
		WorkBook wb = new WorkBook();
		String filename = "转正人员信息";
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
		for (HrStaffPersonInfo personInfo : personInfos) {
			objs = new Object[sheet.getColumnList().size()];
			objs[0] = personInfo.getFdName();
			objs[1] = personInfo.getFdStaffNo();
			objs[2] = personInfo.getFdOrgParentsName();
			//岗位
			String orgPostName = null;
			for(SysOrgPost orgPost : personInfo.getFdOrgPosts()){
				orgPostName = orgPostName == null ? orgPost.getFdName() : orgPostName + "," + orgPost.getFdName();
			}
			objs[3] = orgPostName;
			objs[4] = "试用";
			objs[5] = personInfo.getFdEntryTime();
			objs[6] = personInfo.getFdTrialOperationPeriod();
			objs[7] = personInfo.getFdPositiveTime();
			//转正流程
			List<HrRatifyPositive> list = null;
			if (null != personInfo.getFdOrgPerson()) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("hrRatifyPositive.fdPositiveStaff.fdId =:fdId");
				hqlInfo.setParameter("fdId", personInfo.getFdOrgPerson().getFdId());
				list = this.findList(hqlInfo);
			}
			if (!ArrayUtil.isEmpty(list)) {
				objs[8] = list.get(0).getDocNumber();
				objs[9] = findFlowStatus(list.get(0).getFdId());
			} else {
				objs[8] = "无";
				objs[9] = "无";
			}
			contentList.add(objs);
		}
		sheet.setContentList(contentList);
		wb.addSheet(sheet);
		wb.setFilename(filename);
		return wb;
	}

	private String getStr(String key) {
		return ResourceUtil.getString(key, "hr-ratify");
	}

	private String findFlowStatus(String id) throws Exception {
		String content = null;
		ILbpmProcessService lbpmProcessService = (ILbpmProcessService) SpringBeanUtil.getBean("lbpmProcessService");
		IBaseModel model = lbpmProcessService.findByPrimaryKey(id, null, true);
		content = ((ILbpmProcessCurrentInfoService) SpringBeanUtil.getBean("lbpmProcessCurrentInfoService"))
				.getCurNodesName((LbpmProcess) model);
		return content;
	}

	@Override
	public void updatePositiveDate(String fdIds, Date date) throws Exception {
		String[] fdId = fdIds.split(",");
		for (int i = 0; i < fdId.length; i++) {
			HrStaffPersonInfo modelObj = (HrStaffPersonInfo) getHrStaffPersonInfoService().findByPrimaryKey(fdId[i]);
			modelObj.setFdPositiveTime(date);
			getHrStaffPersonInfoService().update(modelObj);
		}
	}

	@Override
	public void updatePositiveTrialPeriod(String fdIds, String fdPositiveTrialPeriod) throws Exception {
		String[] fdId = fdIds.split(",");
		for (int i = 0; i < fdId.length; i++) {
			HrStaffPersonInfo modelObj = (HrStaffPersonInfo) getHrStaffPersonInfoService().findByPrimaryKey(fdId[i]);
			modelObj.setFdTrialOperationPeriod(fdPositiveTrialPeriod);
			Date date = null;
			if(null != modelObj.getFdEntryTime()){
				date = modelObj.getFdTimeOfEnterprise(); //到本单位时间
			}
			if(null != modelObj.getFdEntryTime()){
				date = modelObj.getFdEntryTime(); //入职日期
			}
			if (null != date) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(modelObj.getFdEntryTime());
				cal.add(Calendar.MONTH, Integer.valueOf(fdPositiveTrialPeriod));
				modelObj.setFdPositiveTime(cal.getTime());
			}
			getHrStaffPersonInfoService().update(modelObj);
		}
	}

	@Override
	public void saveTransactionPositive(String personId, Date fdActualPositiveTime, String fdPositiveRemark)
			throws Exception {
		HrStaffPersonInfo modelObj = (HrStaffPersonInfo) getHrStaffPersonInfoService().findByPrimaryKey(personId);
		modelObj.setFdActualPositiveTime(fdActualPositiveTime);
		modelObj.setFdPositiveTime(fdActualPositiveTime);
		modelObj.setFdPositiveRemark(fdPositiveRemark);
		modelObj.setFdStatus("official");//正式
		getHrStaffPersonInfoService().update(modelObj);
	}

	@Override
	public void addArchFileModel(HttpServletRequest request, String fdId) throws Exception {
		if("true".equals(SysArchivesConfig.newInstance().getSysArchEnabled())&& SysArchivesUtil.isStartFile("hr/ratify")){
			HrRatifyPositive hrRatifyPositive= (HrRatifyPositive) findByPrimaryKey(fdId.split(",")[0]);
			// 只有结束和已反馈的文档可以归档
			if (!"30".equals(hrRatifyPositive.getDocStatus())
					&& !"31".equals(hrRatifyPositive.getDocStatus())) {
				throw new KmssRuntimeException(
						new KmssMessage("sys-archives:file.notsupport"));
			}
			HrRatifyTemplate template = hrRatifyPositive.getDocTemplate();
			ISysArchivesFileTemplateService sysArchivesFileTemplateService= (ISysArchivesFileTemplateService)SpringBeanUtil.getBean("sysArchivesFileTemplateService");
			SysArchivesFileTemplate fileTemp = sysArchivesFileTemplateService.getFileTemplate(template,null);
			if(fileTemp!=null){
				SysArchivesParamModel paramModel = new SysArchivesParamModel();
				paramModel.setAuto("0");
				paramModel.setFileName(hrRatifyPositive.getDocSubject()+".html");
				// 归档页面URL(若为多表单，暂时归档默认表单)
				int saveApproval = fileTemp.getFdSaveApproval() != null
						&& fileTemp.getFdSaveApproval() ? 1 : 0;
				//流程自动归档
				String fdModelId = hrRatifyPositive.getFdId();
				String url = "/hr/ratify/hr_ratify_positive/hrRatifyPositive.do?method=printFileDoc&fdId="
						+ hrRatifyPositive.getFdId() + "&s_xform=default&saveApproval="
						+ saveApproval;
				paramModel.setUrl(url);
				sysArchivesFileTemplateService.addArchFileModel(request,hrRatifyPositive,paramModel,fileTemp);

				hrRatifyPositive.setFdIsFiling(true);
				super.update(hrRatifyPositive);
			}else{
				return;
			}

			if (UserOperHelper.allowLogOper("fileDoc", "*")) {
				UserOperContentHelper.putAdd(hrRatifyPositive)
						.putSimple("docTemplate", fileTemp);
			}
		}
	}
}
