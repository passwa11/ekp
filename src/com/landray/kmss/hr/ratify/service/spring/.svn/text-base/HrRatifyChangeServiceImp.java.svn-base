package com.landray.kmss.hr.ratify.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.ratify.model.HrRatifyAgendaConfig;
import com.landray.kmss.hr.ratify.model.HrRatifyChange;
import com.landray.kmss.hr.ratify.model.HrRatifyMKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.hr.ratify.model.HrRatifyTKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifyChangeService;
import com.landray.kmss.hr.ratify.util.HrRatifyTitleUtil;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract;
import com.landray.kmss.hr.staff.model.HrStaffRatifyLog;
import com.landray.kmss.sys.archives.config.SysArchivesConfig;
import com.landray.kmss.sys.archives.interfaces.IArchFileDataService;
import com.landray.kmss.sys.archives.model.SysArchivesFileTemplate;
import com.landray.kmss.sys.archives.model.SysArchivesParamModel;
import com.landray.kmss.sys.archives.service.ISysArchivesFileTemplateService;
import com.landray.kmss.sys.archives.util.SysArchivesUtil;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
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
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HrRatifyChangeServiceImp extends HrRatifyMainServiceImp
		implements IHrRatifyChangeService , IArchFileDataService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;
	private ISysAttMainCoreInnerService sysAttMainCoreInnerService;

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrRatifyChange hrRatifyChange = new HrRatifyChange();
		hrRatifyChange.setDocCreateTime(new Date());
		hrRatifyChange.setDocCreator(UserUtil.getUser());
		hrRatifyChange.setFdDepartment(UserUtil.getUser().getFdParent());
		String templateId = requestContext.getParameter("i.docTemplate");
		if (com.landray.kmss.util.StringUtil.isNotNull(templateId)) {
			com.landray.kmss.hr.ratify.model.HrRatifyTemplate docTemplate = (com.landray.kmss.hr.ratify.model.HrRatifyTemplate) getHrRatifyTemplateService()
					.findByPrimaryKey(templateId);
			if (docTemplate != null) {
				hrRatifyChange.setDocTemplate(docTemplate);
				List<HrRatifyTKeyword> keyWordList = docTemplate
						.getDocKeyword();
				List tKeyword = new ArrayList();
				for (HrRatifyTKeyword keyWord : keyWordList) {
					HrRatifyMKeyword mKeyword = new HrRatifyMKeyword();
					mKeyword.setDocKeyword(keyWord.getDocKeyword());
					tKeyword.add(mKeyword);
				}
				hrRatifyChange.setDocKeyword(tKeyword);
			}
		}
        HrRatifyUtil.initModelFromRequest(hrRatifyChange, requestContext);
		if (hrRatifyChange.getDocTemplate() != null) {
			hrRatifyChange.setExtendFilePath(
					XFormUtil.getFileName(hrRatifyChange.getDocTemplate(),
							hrRatifyChange.getDocTemplate().getFdTempKey()));
			if (Boolean.FALSE.equals(
					hrRatifyChange.getDocTemplate().getDocUseXform())) {
				hrRatifyChange.setDocXform(
						hrRatifyChange.getDocTemplate().getDocXform());
			}
			hrRatifyChange.setDocUseXform(
					hrRatifyChange.getDocTemplate().getDocUseXform());
		}
        return hrRatifyChange;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrRatifyChange hrRatifyChange = (HrRatifyChange) model;
		if (hrRatifyChange.getDocTemplate() != null) {
			dispatchCoreService.initFormSetting(form,
					hrRatifyChange.getDocTemplate().getFdTempKey(),
					hrRatifyChange.getDocTemplate(),
					hrRatifyChange.getDocTemplate().getFdTempKey(),
					requestContext);
		}
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	public ISysAttMainCoreInnerService getSysAttMainCoreInnerService() {
		if (sysAttMainCoreInnerService == null) {
			sysAttMainCoreInnerService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
		}
		return sysAttMainCoreInnerService;
	}

	/*private IKmArchivesFileTemplateService kmArchivesFileTemplateService;

	public void setKmArchivesFileTemplateService(
			IKmArchivesFileTemplateService kmArchivesFileTemplateService) {
		this.kmArchivesFileTemplateService = kmArchivesFileTemplateService;
	}*/

	@Override
	public void setDocNumber(HrRatifyMain mainModel) throws Exception {
		HrRatifyChange hrRatifyChange = (HrRatifyChange) mainModel;
		if (!"10".equals(mainModel.getDocStatus())) {
			String docNumber = getSysNumberFlowService()
					.generateFlowNumber(hrRatifyChange);
			hrRatifyChange.setDocNumber(docNumber);
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null) {
			return;
		}
		Object obj = event.getSource();
		if (!(obj instanceof HrRatifyChange)) {
			return;
		}
		if (event instanceof Event_SysFlowFinish) {
			try {
				HrRatifyChange change = (HrRatifyChange) obj;
				HrRatifyAgendaConfig config = new HrRatifyAgendaConfig();
				if ("true".equals(config.getFdHrStaffCont())) {
					HrStaffPersonExperienceContract contract = change
							.getFdContract();
					if (change.getFdChangeBeginDate() != null) {
						contract.setFdBeginDate(change.getFdChangeBeginDate());
					}
					if (Boolean.TRUE.equals(change.getFdIsLongtermContract())) {
						contract.setFdIsLongtermContract(change.getFdIsLongtermContract());
						contract.setFdEndDate(change.getFdChangeEndDate());
					} else {
						contract.setFdIsLongtermContract(change.getFdIsLongtermContract());
						if (change.getFdChangeEndDate() != null) {
							contract.setFdEndDate(change.getFdChangeEndDate());
						}
					}
					if (StringUtil.isNotNull(change.getFdChangeRemark())) {
						contract.setFdMemo(change.getFdChangeRemark());
					}
					getHrStaffPersonExperienceContractService()
							.update(contract);

					// 附件获取
					List<SysAttMain> sysAttMainList = getSysAttMainCoreInnerService()
							.findByModelKey(HrRatifyChange.class.getName(),
									change.getFdId(), "attHrExpCont");

					if (sysAttMainList.size() > 0) {
						// 清除
						List<SysAttMain> oldSysAttMainList = getSysAttMainCoreInnerService()
								.findByModelKey(HrStaffPersonExperienceContract.class.getName(),
										contract.getFdId(), "attHrExpCont");
						List<String> ids = new ArrayList<>();

						for(SysAttMain old:oldSysAttMainList) {
							ids.add(old.getFdId());
						}
						if (ids.size() > 0) {
							String[] idArr = new String[ids.size()];
							ids.toArray(idArr);
							getSysAttMainCoreInnerService().delete(idArr);
						}
					}
					// 保存附件信息
					SysAttMain newSysAttMain = new SysAttMain();
					for (SysAttMain sam : sysAttMainList) {
						newSysAttMain = new SysAttMain();
						BeanUtils.copyProperties(sam, newSysAttMain);
						newSysAttMain.setFdId(null);
						newSysAttMain
								.setFdModelId(contract.getFdId());
						newSysAttMain
								.setFdModelName(
										HrStaffPersonExperienceContract.class
												.getName());
						getSysAttMainCoreInnerService()
								.saveMainModel(newSysAttMain);
					}

				}
				change.setDocPublishTime(new Date());
				this.update(change);
				feedbackNotify(change);
				addLog(change);
			} catch (Exception e1) {
				throw new KmssRuntimeException(e1);
			}
		} else if (event instanceof Event_SysFlowDiscard) {
			try {
				HrRatifyChange change = (HrRatifyChange) obj;
				change.setDocPublishTime(new Date());
				this.update(change);
			} catch (Exception e2) {
				throw new KmssRuntimeException(e2);
			}
		}
	}

	@Override
	public void addLog(HrRatifyMain mainModel) throws Exception {
		HrRatifyChange change = (HrRatifyChange) mainModel;
		HrStaffRatifyLog log = new HrStaffRatifyLog();
		log.setFdRatifyType("leave");
		log.setFdRatifyDept(change.getFdChangeDept());
		log.setFdRatifyDate(change.getDocPublishTime());
		JSONObject json = new JSONObject();
		json.put("docSubject", change.getDocSubject());
		json.put("url", HrRatifyUtil.getUrl(change));
		log.setFdRatifyProcess(json.toString());
		getHrStaffRatifyLogService().add(log);
	}

	@Override
	public void deleteEntity(HrRatifyMain mainModel) throws Exception {
		HrRatifyChange changeModel = (HrRatifyChange) findByPrimaryKey(
				mainModel.getFdId());
		super.deleteEntity(changeModel);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		HrRatifyChange hrRatifyChange = (HrRatifyChange) modelObj; 
		if (StringUtil.isNull(hrRatifyChange.getDocNumber())) {
			setDocNumber(hrRatifyChange);
		}
		HrRatifyTitleUtil.genTitle(hrRatifyChange);
		super.update(hrRatifyChange);
	}
    /*历史归档代码*/
	/*@Override
	public void addFileMainDoc(HttpServletRequest request, String fdId,
			KmArchivesFileTemplate fileTemplate) throws Exception {
		HrRatifyChange mainModel = (HrRatifyChange) findByPrimaryKey(fdId);
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
			HrRatifyChange mainModel, KmArchivesFileTemplate fileTemplate)
			throws Exception {
		KmArchivesMain kmArchivesMain = new KmArchivesMain();
		kmArchivesFileTemplateService.setFileField(kmArchivesMain, fileTemplate,
				mainModel);
		// 归档页面URL(若为多表单，暂时归档默认表单)
		int saveApproval = fileTemplate.getFdSaveApproval() != null
				&& fileTemplate.getFdSaveApproval() ? 1 : 0;
		String url = "/hr/ratify/hr_ratify_change/hrRatifyChange.do?method=printFileDoc&fdId="
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
			HrRatifyChange hrRatifyChange= (HrRatifyChange) findByPrimaryKey(fdId.split(",")[0]);
			// 只有结束和已反馈的文档可以归档
			if (!"30".equals(hrRatifyChange.getDocStatus())
					&& !"31".equals(hrRatifyChange.getDocStatus())) {
				throw new KmssRuntimeException(
						new KmssMessage("sys-archives:file.notsupport"));
			}
			HrRatifyTemplate template = hrRatifyChange.getDocTemplate();
			ISysArchivesFileTemplateService sysArchivesFileTemplateService= (ISysArchivesFileTemplateService)SpringBeanUtil.getBean("sysArchivesFileTemplateService");
			SysArchivesFileTemplate fileTemp = sysArchivesFileTemplateService.getFileTemplate(template,null);
			if(fileTemp!=null){
				SysArchivesParamModel paramModel = new SysArchivesParamModel();
				paramModel.setAuto("0");
				paramModel.setFileName(hrRatifyChange.getDocSubject()+".html");
				// 归档页面URL(若为多表单，暂时归档默认表单)
				int saveApproval = fileTemp.getFdSaveApproval() != null
						&& fileTemp.getFdSaveApproval() ? 1 : 0;
				//流程自动归档
				String fdModelId = hrRatifyChange.getFdId();
				String url = "/hr/ratify/hr_ratify_change/hrRatifyChange.do?method=printFileDoc&fdId="
						+ hrRatifyChange.getFdId() + "&s_xform=default&saveApproval="
						+ saveApproval;
				paramModel.setUrl(url);
				sysArchivesFileTemplateService.addArchFileModel(request,hrRatifyChange,paramModel,fileTemp);

				hrRatifyChange.setFdIsFiling(true);
				super.update(hrRatifyChange);
			}else{
				return;
			}

			if (UserOperHelper.allowLogOper("fileDoc", "*")) {
				UserOperContentHelper.putAdd(hrRatifyChange)
						.putSimple("docTemplate", fileTemp);
			}
		}
	}
}
