package com.landray.kmss.hr.ratify.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.hr.ratify.model.HrRatifyAgendaConfig;
import com.landray.kmss.hr.ratify.model.HrRatifyMKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.hr.ratify.model.HrRatifyRehire;
import com.landray.kmss.hr.ratify.model.HrRatifyTKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifyRehireService;
import com.landray.kmss.hr.ratify.util.HrRatifyTitleUtil;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.hr.staff.event.HrStaffPersonInfoEvent;
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
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowDiscard;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowFinish;
import com.landray.kmss.sys.xform.interfaces.XFormUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HrRatifyRehireServiceImp extends HrRatifyMainServiceImp
		implements IHrRatifyRehireService, IArchFileDataService,
		ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrRatifyRehire hrRatifyRehire = new HrRatifyRehire();
		hrRatifyRehire.setDocCreateTime(new Date());
		hrRatifyRehire.setDocCreator(UserUtil.getUser());
		hrRatifyRehire.setFdDepartment(UserUtil.getUser().getFdParent());
		String templateId = requestContext.getParameter("i.docTemplate");
		if (com.landray.kmss.util.StringUtil.isNotNull(templateId)) {
			com.landray.kmss.hr.ratify.model.HrRatifyTemplate docTemplate = (com.landray.kmss.hr.ratify.model.HrRatifyTemplate) getHrRatifyTemplateService()
					.findByPrimaryKey(templateId);
			if (docTemplate != null) {
				hrRatifyRehire.setDocTemplate(docTemplate);
				List<HrRatifyTKeyword> keyWordList = docTemplate
						.getDocKeyword();
				List tKeyword = new ArrayList();
				for (HrRatifyTKeyword keyWord : keyWordList) {
					HrRatifyMKeyword mKeyword = new HrRatifyMKeyword();
					mKeyword.setDocKeyword(keyWord.getDocKeyword());
					tKeyword.add(mKeyword);
				}
				hrRatifyRehire.setDocKeyword(tKeyword);
			}
		}
        HrRatifyUtil.initModelFromRequest(hrRatifyRehire, requestContext);
		if (hrRatifyRehire.getDocTemplate() != null) {
			hrRatifyRehire.setExtendFilePath(
					XFormUtil.getFileName(hrRatifyRehire.getDocTemplate(),
							hrRatifyRehire.getDocTemplate().getFdTempKey()));
			if (Boolean.FALSE.equals(
					hrRatifyRehire.getDocTemplate().getDocUseXform())) {
				hrRatifyRehire.setDocXform(
						hrRatifyRehire.getDocTemplate().getDocXform());
			}
			hrRatifyRehire.setDocUseXform(
					hrRatifyRehire.getDocTemplate().getDocUseXform());
		}
        return hrRatifyRehire;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrRatifyRehire hrRatifyRehire = (HrRatifyRehire) model;
		if (hrRatifyRehire.getDocTemplate() != null) {
			dispatchCoreService.initFormSetting(form,
					hrRatifyRehire.getDocTemplate().getFdTempKey(),
					hrRatifyRehire.getDocTemplate(),
					hrRatifyRehire.getDocTemplate().getFdTempKey(),
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
		HrRatifyRehire hrRatifyRehire = (HrRatifyRehire) mainModel;
		if (!"10".equals(mainModel.getDocStatus())) {
			String docNumber = getSysNumberFlowService()
					.generateFlowNumber(hrRatifyRehire);
			hrRatifyRehire.setDocNumber(docNumber);
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null) {
			return;
		}
		Object obj = event.getSource();
		if (!(obj instanceof HrRatifyRehire)) {
			return;
		}
		if (event instanceof Event_SysFlowFinish) {
			try {
				HrRatifyRehire rehire = (HrRatifyRehire) obj;
				HrRatifyAgendaConfig config = new HrRatifyAgendaConfig();
				if ("true".equals(config.getFdTrueSysOrg())) {
					SysOrgPerson fdRehireStaff = rehire.getFdRehireStaff();
					fdRehireStaff.setFdIsAvailable(new Boolean(true));
					fdRehireStaff.setFdParent(rehire.getFdRehireDept());
					fdRehireStaff.setFdPosts(rehire.getFdRehirePosts());
					getSysOrgPersonService().update(fdRehireStaff);
					HrStaffPersonInfo personInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService()
							.findByPrimaryKey(
									rehire.getFdRehireStaff().getFdId(),
									HrStaffPersonInfo.class, true);
					if (personInfo != null) {
						personInfo.setFdStatus("official");
						personInfo.setFdIsRehire(new Boolean(true));
						personInfo.setFdRehireTime(rehire.getFdRehireDate());
						//重新设置入职日期
						personInfo.setFdEntryTime(rehire.getFdRehireDate());
						getHrStaffPersonInfoService().update(personInfo);
						String fdDetails = "通过员工返聘流程，修改了“"
								+ fdRehireStaff.getFdName()
								+ "”的员工信息：员工状态、是否返聘、返聘日期。";
						addUpdateLog(fdDetails, personInfo);
						applicationContext.publishEvent(
								new HrStaffPersonInfoEvent(personInfo,
										new RequestContext(
												Plugin.currentRequest())));
					}
				}
				rehire.setDocPublishTime(new Date());
				this.update(rehire);
				feedbackNotify(rehire);
				addLog(rehire);
			} catch (Exception e1) {
				throw new KmssRuntimeException(e1);
			}
		} else if (event instanceof Event_SysFlowDiscard) {
			try {
				HrRatifyRehire rehire = (HrRatifyRehire) obj;
				rehire.setDocPublishTime(new Date());
				this.update(rehire);
			} catch (Exception e2) {
				throw new KmssRuntimeException(e2);
			}
		}
	}

	@Override
	public void addLog(HrRatifyMain mainModel) throws Exception {
		HrRatifyRehire rehire = (HrRatifyRehire) mainModel;
		HrStaffRatifyLog log = new HrStaffRatifyLog();
		log.setFdRatifyType("leave");
		log.setFdRatifyDept(rehire.getFdRehireDept());
		List<SysOrgPost> rehirePosts = new ArrayList<SysOrgPost>();
		rehirePosts.addAll(rehire.getFdRehirePosts());
		log.setFdRatifyPosts(rehirePosts);
		log.setFdRatifyDate(rehire.getFdRehireDate());
		JSONObject json = new JSONObject();
		json.put("docSubject", rehire.getDocSubject());
		json.put("url", HrRatifyUtil.getUrl(rehire));
		log.setFdRatifyProcess(json.toString());
		getHrStaffRatifyLogService().add(log);
	}

	@Override
	public void deleteEntity(HrRatifyMain mainModel) throws Exception {
		HrRatifyRehire rehireModel = (HrRatifyRehire) findByPrimaryKey(
				mainModel.getFdId());
		super.deleteEntity(rehireModel);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		HrRatifyRehire rehireModel = (HrRatifyRehire) modelObj; 
		if (StringUtil.isNull(rehireModel.getDocNumber())) {
			setDocNumber(rehireModel);
		}
		HrRatifyTitleUtil.genTitle(rehireModel);
		super.update(rehireModel);
	}

	/*历史归档机制*/
	/*@Override
	public void addFileMainDoc(HttpServletRequest request, String fdId,
			KmArchivesFileTemplate fileTemplate) throws Exception {
		HrRatifyRehire mainModel = (HrRatifyRehire) findByPrimaryKey(fdId);
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
			HrRatifyRehire mainModel, KmArchivesFileTemplate fileTemplate)
			throws Exception {
		KmArchivesMain kmArchivesMain = new KmArchivesMain();
		kmArchivesFileTemplateService.setFileField(kmArchivesMain, fileTemplate,
				mainModel);
		// 归档页面URL(若为多表单，暂时归档默认表单)
		int saveApproval = fileTemplate.getFdSaveApproval() != null
				&& fileTemplate.getFdSaveApproval() ? 1 : 0;
		String url = "/hr/ratify/hr_ratify_rehire/hrRatifyRehire.do?method=printFileDoc&fdId="
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
			HrRatifyRehire mainModel= (HrRatifyRehire) findByPrimaryKey(fdId.split(",")[0]);
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
				String url = "/hr/ratify/hr_ratify_rehire/hrRatifyRehire.do?method=printFileDoc&fdId="
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
