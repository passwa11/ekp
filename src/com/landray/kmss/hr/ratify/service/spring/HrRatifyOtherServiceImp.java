package com.landray.kmss.hr.ratify.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.ratify.model.HrRatifyMKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.hr.ratify.model.HrRatifyOther;
import com.landray.kmss.hr.ratify.model.HrRatifyTKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifyOtherService;
import com.landray.kmss.hr.ratify.util.HrRatifyTitleUtil;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.sys.archives.config.SysArchivesConfig;
import com.landray.kmss.sys.archives.interfaces.IArchFileDataService;
import com.landray.kmss.sys.archives.model.SysArchivesFileTemplate;
import com.landray.kmss.sys.archives.model.SysArchivesParamModel;
import com.landray.kmss.sys.archives.service.ISysArchivesFileTemplateService;
import com.landray.kmss.sys.archives.util.SysArchivesUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowDiscard;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowFinish;
import com.landray.kmss.sys.xform.interfaces.XFormUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HrRatifyOtherServiceImp extends HrRatifyMainServiceImp
		implements IHrRatifyOtherService, IArchFileDataService {

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil
					.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}

	@Override
	public IBaseModel initBizModelSetting(RequestContext requestContext)
			throws Exception {
		HrRatifyOther hrRatifyOther = new HrRatifyOther();
		hrRatifyOther.setDocCreateTime(new Date());
		hrRatifyOther.setDocCreator(UserUtil.getUser());
		hrRatifyOther.setFdDepartment(UserUtil.getUser().getFdParent());
		String templateId = requestContext.getParameter("i.docTemplate");
		if (com.landray.kmss.util.StringUtil.isNotNull(templateId)) {
			com.landray.kmss.hr.ratify.model.HrRatifyTemplate docTemplate = (com.landray.kmss.hr.ratify.model.HrRatifyTemplate) getHrRatifyTemplateService()
					.findByPrimaryKey(templateId);
			if (docTemplate != null) {
				hrRatifyOther.setDocTemplate(docTemplate);
				List<HrRatifyTKeyword> keyWordList = docTemplate
						.getDocKeyword();
				List tKeyword = new ArrayList();
				for (HrRatifyTKeyword keyWord : keyWordList) {
					HrRatifyMKeyword mKeyword = new HrRatifyMKeyword();
					mKeyword.setDocKeyword(keyWord.getDocKeyword());
					tKeyword.add(mKeyword);
				}
				hrRatifyOther.setDocKeyword(tKeyword);
			}
		}
		HrRatifyUtil.initModelFromRequest(hrRatifyOther, requestContext);
		if (hrRatifyOther.getDocTemplate() != null) {
			hrRatifyOther.setExtendFilePath(
					XFormUtil.getFileName(hrRatifyOther.getDocTemplate(),
							hrRatifyOther.getDocTemplate().getFdTempKey()));
			if (Boolean.FALSE.equals(
					hrRatifyOther.getDocTemplate().getDocUseXform())) {
				hrRatifyOther.setDocXform(
						hrRatifyOther.getDocTemplate().getDocXform());
			}
			hrRatifyOther.setDocUseXform(
					hrRatifyOther.getDocTemplate().getDocUseXform());
		}
		return hrRatifyOther;
	}

	@Override
	public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
		HrRatifyOther hrRatifyOther = (HrRatifyOther) model;
		if (hrRatifyOther.getDocTemplate() != null) {
			dispatchCoreService.initFormSetting(form,
					hrRatifyOther.getDocTemplate().getFdTempKey(),
					hrRatifyOther.getDocTemplate(),
					hrRatifyOther.getDocTemplate().getFdTempKey(),
					requestContext);
		}
	}

	@Override
	public void setDocNumber(HrRatifyMain mainModel) throws Exception {
		HrRatifyOther hrRatifyOther = (HrRatifyOther) mainModel;
		if (!"10".equals(mainModel.getDocStatus())) {
			String docNumber = getSysNumberFlowService()
					.generateFlowNumber(hrRatifyOther);
			hrRatifyOther.setDocNumber(docNumber);
		}
	}

	@Override
	public void deleteEntity(HrRatifyMain mainModel) throws Exception {
		HrRatifyOther hrRatifyOther = (HrRatifyOther) findByPrimaryKey(
				mainModel.getFdId());
		super.deleteEntity(hrRatifyOther);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		HrRatifyOther hrRatifyOther = (HrRatifyOther) modelObj; 
		if (StringUtil.isNull(hrRatifyOther.getDocNumber())) {
            setDocNumber(hrRatifyOther);
        }
		HrRatifyTitleUtil.genTitle(hrRatifyOther);
		super.update(hrRatifyOther);
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null) {
            return;
        }
		Object obj = event.getSource();
		if (!(obj instanceof HrRatifyOther)) {
            return;
        }
		if (event instanceof Event_SysFlowFinish) {
			HrRatifyOther other = (HrRatifyOther) obj;
			other.setDocPublishTime(new Date());
			try {
				this.update(other);
				feedbackNotify(other);
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new KmssRuntimeException(e1);
			}
		} else if (event instanceof Event_SysFlowDiscard) {
			HrRatifyOther other = (HrRatifyOther) obj;
			other.setDocPublishTime(new Date());
			try {
				this.update(other);
			} catch (Exception e1) {
				throw new KmssRuntimeException(e1);
			}
		}
	}

	/*历史归档机制*/
	/*@Override
	public void addFileMainDoc(HttpServletRequest request, String fdId,
			KmArchivesFileTemplate fileTemplate) throws Exception {
		HrRatifyOther mainModel = (HrRatifyOther) findByPrimaryKey(fdId);
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
			HrRatifyOther mainModel, KmArchivesFileTemplate fileTemplate)
			throws Exception {
		KmArchivesMain kmArchivesMain = new KmArchivesMain();
		kmArchivesFileTemplateService.setFileField(kmArchivesMain, fileTemplate,
				mainModel);
		// 归档页面URL(若为多表单，暂时归档默认表单)
		int saveApproval = fileTemplate.getFdSaveApproval() != null
				&& fileTemplate.getFdSaveApproval() ? 1 : 0;
		String url = "/hr/ratify/hr_ratify_other/hrRatifyOther.do?method=printFileDoc&fdId="
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
			HrRatifyOther mainModel= (HrRatifyOther) findByPrimaryKey(fdId.split(",")[0]);
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
				String url = "/hr/ratify/hr_ratify_other/hrRatifyOther.do?method=printFileDoc&fdId="
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
