package com.landray.kmss.hr.ratify.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.ratify.model.HrRatifyAgendaConfig;
import com.landray.kmss.hr.ratify.model.HrRatifyFire;
import com.landray.kmss.hr.ratify.model.HrRatifyMKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.hr.ratify.model.HrRatifyTKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifyFireService;
import com.landray.kmss.hr.ratify.util.HrRatifyTitleUtil;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffRatifyLog;
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

public class HrRatifyFireServiceImp extends HrRatifyMainServiceImp
		implements IHrRatifyFireService, IArchFileDataService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrRatifyFire hrRatifyFire = new HrRatifyFire();
		hrRatifyFire.setDocCreateTime(new Date());
		hrRatifyFire.setDocCreator(UserUtil.getUser());
		hrRatifyFire.setFdDepartment(UserUtil.getUser().getFdParent());
		String templateId = requestContext.getParameter("i.docTemplate");
		if (com.landray.kmss.util.StringUtil.isNotNull(templateId)) {
			com.landray.kmss.hr.ratify.model.HrRatifyTemplate docTemplate = (com.landray.kmss.hr.ratify.model.HrRatifyTemplate) getHrRatifyTemplateService()
					.findByPrimaryKey(templateId);
			if (docTemplate != null) {
				hrRatifyFire.setDocTemplate(docTemplate);
				List<HrRatifyTKeyword> keyWordList = docTemplate
						.getDocKeyword();
				List tKeyword = new ArrayList();
				for (HrRatifyTKeyword keyWord : keyWordList) {
					HrRatifyMKeyword mKeyword = new HrRatifyMKeyword();
					mKeyword.setDocKeyword(keyWord.getDocKeyword());
					tKeyword.add(mKeyword);
				}
				hrRatifyFire.setDocKeyword(tKeyword);
			}
		}
        HrRatifyUtil.initModelFromRequest(hrRatifyFire, requestContext);
		if (hrRatifyFire.getDocTemplate() != null) {
			hrRatifyFire.setExtendFilePath(
					XFormUtil.getFileName(hrRatifyFire.getDocTemplate(),
							hrRatifyFire.getDocTemplate().getFdTempKey()));
			if (Boolean.FALSE.equals(
					hrRatifyFire.getDocTemplate().getDocUseXform())) {
				hrRatifyFire.setDocXform(
						hrRatifyFire.getDocTemplate().getDocXform());
			}
			hrRatifyFire.setDocUseXform(
					hrRatifyFire.getDocTemplate().getDocUseXform());
		}
        return hrRatifyFire;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrRatifyFire hrRatifyFire = (HrRatifyFire) model;
		if (hrRatifyFire.getDocTemplate() != null) {
			dispatchCoreService.initFormSetting(form,
					hrRatifyFire.getDocTemplate().getFdTempKey(),
					hrRatifyFire.getDocTemplate(),
					hrRatifyFire.getDocTemplate().getFdTempKey(),
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
		HrRatifyFire hrRatifyFire = (HrRatifyFire) mainModel;
		if (!"10".equals(mainModel.getDocStatus())) {
			String docNumber = getSysNumberFlowService()
					.generateFlowNumber(hrRatifyFire);
			hrRatifyFire.setDocNumber(docNumber);
		}
	}

	private void updatePerson(HrRatifyFire fire) throws Exception {
		//配置中开启了设置为无效时 把人员置为无效
		HrRatifyAgendaConfig config = new HrRatifyAgendaConfig();
		if("true".equals(config.getFdFalseSysOrg())) {
			SysOrgPerson fdFireStaff = fire.getFdFireStaff();
			String id = fdFireStaff.getFdId();
			getSysOrgPersonService().updateInvalidated(id, new RequestContext());
		}
	}


	private void updateStaff(HrRatifyFire fire) throws Exception {
		HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService()
				.findByPrimaryKey(fire.getFdFireStaff().getFdId());
		hrStaffPersonInfo.setFdStatus("dismissal");
		hrStaffPersonInfo.setFdLeaveTime(fire.getFdFireDate());
		getHrStaffPersonInfoService().update(hrStaffPersonInfo);
		String fdDetails = "通过员工解聘流程，修改了“" + fire.getFdFireStaff().getFdName()
				+ "”的员工信息：员工状态、离职日期。";
		addUpdateLog(fdDetails, hrStaffPersonInfo);
	}

	private void savePersonSchedulerJob(RequestContext requestContext,
			HrRatifyFire fire) throws Exception {
		Date fireDate = fire.getFdFireDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fireDate);
		String cron = HrRatifyUtil.getCronExpression(calendar);
		if (StringUtil.isNotNull(cron)) {
			SysQuartzModelContext quartzContext = new SysQuartzModelContext();
			quartzContext.setQuartzJobMethod("personSchedulerJob");
			quartzContext.setQuartzJobService("hrRatifyFireService");
			quartzContext.setQuartzKey("hrRatifyFireQuartzJob");
			quartzContext.setQuartzParameter(fire.getFdId().toString());
			quartzContext.setQuartzSubject(fire.getDocSubject());
			quartzContext.setQuartzRequired(true);
			quartzContext.setQuartzCronExpression(cron);
			getSysQuartzCoreService().saveScheduler(quartzContext, fire);
		}
	}

	@Override
	public void personSchedulerJob(SysQuartzJobContext context)
			throws Exception {
		HrRatifyFire fire = (HrRatifyFire) findByPrimaryKey(
				context.getParameter(), HrRatifyFire.class, true);
		this.updatePerson(fire);
		this.updateStaff(fire);
		getSysQuartzCoreService().delete(fire, "hrRatifyFireQuartzJob");
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null) {
			return;
		}
		Object obj = event.getSource();
		if (!(obj instanceof HrRatifyFire)) {
			return;
		}
		if (event instanceof Event_SysFlowFinish) {
			try {
				HrRatifyFire fire = (HrRatifyFire) obj;
				Date fdFireDate = fire.getFdFireDate();
				Date now = new Date();
				HrRatifyAgendaConfig config = new HrRatifyAgendaConfig();
				if (now.getTime() >= fdFireDate.getTime()) {
					if ("true".equals(config.getFdFalseHrStaff())) {
						this.updatePerson(fire);
						this.updateStaff(fire);
					}
				} else {
					if ("true".equals(config.getFdFalseHrStaff())) {
						savePersonSchedulerJob(new RequestContext(), fire);
					}
				}
				fire.setDocPublishTime(now);
				this.update(fire);
				feedbackNotify(fire);
				addLog(fire);
			} catch (Exception e1) {
				throw new KmssRuntimeException(e1);
			}
		} else if (event instanceof Event_SysFlowDiscard) {
			try {
				HrRatifyFire fire = (HrRatifyFire) obj;
				fire.setDocPublishTime(new Date());
				this.update(fire);
			} catch (Exception e2) {
				throw new KmssRuntimeException(e2);
			}
		}
	}

	@Override
	public void addLog(HrRatifyMain mainModel) throws Exception {
		HrRatifyFire fire = (HrRatifyFire) mainModel;
		HrStaffRatifyLog log = new HrStaffRatifyLog();
		log.setFdRatifyType("fire");
		log.setFdRatifyDept(fire.getFdFireDept());
		log.setFdRatifyDate(fire.getFdFireDate());
		JSONObject json = new JSONObject();
		json.put("docSubject", fire.getDocSubject());
		json.put("url", HrRatifyUtil.getUrl(fire));
		log.setFdRatifyProcess(json.toString());
		getHrStaffRatifyLogService().add(log);
	}

	@Override
	public void deleteEntity(HrRatifyMain mainModel) throws Exception {
		HrRatifyFire fireModel = (HrRatifyFire) findByPrimaryKey(
				mainModel.getFdId());
		super.deleteEntity(fireModel);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		HrRatifyFire hrRatifyFire = (HrRatifyFire) modelObj; 
		if (StringUtil.isNull(hrRatifyFire.getDocNumber())) {
			setDocNumber(hrRatifyFire);
		}
		HrRatifyTitleUtil.genTitle(hrRatifyFire);
		super.update(hrRatifyFire);
	}
	/*历史归档机制实现*/
	/*
	@Override
	public void addFileMainDoc(HttpServletRequest request, String fdId,
			KmArchivesFileTemplate fileTemplate) throws Exception {
		HrRatifyFire mainModel = (HrRatifyFire) findByPrimaryKey(fdId);
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
			HrRatifyFire mainModel, KmArchivesFileTemplate fileTemplate)
			throws Exception {
		KmArchivesMain kmArchivesMain = new KmArchivesMain();
		kmArchivesFileTemplateService.setFileField(kmArchivesMain, fileTemplate,
				mainModel);
		// 归档页面URL(若为多表单，暂时归档默认表单)
		int saveApproval = fileTemplate.getFdSaveApproval() != null
				&& fileTemplate.getFdSaveApproval() ? 1 : 0;
		String url = "/hr/ratify/hr_ratify_fire/hrRatifyFire.do?method=printFileDoc&fdId="
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
			HrRatifyFire hrRatifyFire= (HrRatifyFire) findByPrimaryKey(fdId.split(",")[0]);
			// 只有结束和已反馈的文档可以归档
			if (!"30".equals(hrRatifyFire.getDocStatus())
					&& !"31".equals(hrRatifyFire.getDocStatus())) {
				throw new KmssRuntimeException(
						new KmssMessage("sys-archives:file.notsupport"));
			}
			HrRatifyTemplate template = hrRatifyFire.getDocTemplate();
			ISysArchivesFileTemplateService sysArchivesFileTemplateService= (ISysArchivesFileTemplateService)SpringBeanUtil.getBean("sysArchivesFileTemplateService");
			SysArchivesFileTemplate fileTemp = sysArchivesFileTemplateService.getFileTemplate(template,null);
			if(fileTemp!=null){
				SysArchivesParamModel paramModel = new SysArchivesParamModel();
				paramModel.setAuto("0");
				paramModel.setFileName(hrRatifyFire.getDocSubject()+".html");
				// 归档页面URL(若为多表单，暂时归档默认表单)
				int saveApproval = fileTemp.getFdSaveApproval() != null
						&& fileTemp.getFdSaveApproval() ? 1 : 0;
				//流程自动归档
				String fdModelId = hrRatifyFire.getFdId();
				String url = "/hr/ratify/hr_ratify_fire/hrRatifyFire.do?method=printFileDoc&fdId="
						+ hrRatifyFire.getFdId() + "&s_xform=default&saveApproval="
						+ saveApproval;
				paramModel.setUrl(url);
				sysArchivesFileTemplateService.addArchFileModel(request,hrRatifyFire,paramModel,fileTemp);

				hrRatifyFire.setFdIsFiling(true);
				super.update(hrRatifyFire);
			}else{
				return;
			}

			if (UserOperHelper.allowLogOper("fileDoc", "*")) {
				UserOperContentHelper.putAdd(hrRatifyFire)
						.putSimple("docTemplate", fileTemp);
			}
		}
	}
}
