package com.landray.kmss.hr.ratify.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.ratify.model.HrRatifyAgendaConfig;
import com.landray.kmss.hr.ratify.model.HrRatifyMKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.hr.ratify.model.HrRatifySign;
import com.landray.kmss.hr.ratify.model.HrRatifyTKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifySignService;
import com.landray.kmss.hr.ratify.util.HrRatifyTitleUtil;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.hr.staff.model.HrStaffContractType;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
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

public class HrRatifySignServiceImp extends HrRatifyMainServiceImp
		implements IHrRatifySignService, IArchFileDataService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

	private ISysAttMainCoreInnerService sysAttMainCoreInnerService;

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrRatifySign hrRatifySign = new HrRatifySign();
		hrRatifySign.setDocCreateTime(new Date());
		hrRatifySign.setDocCreator(UserUtil.getUser());
		hrRatifySign.setFdDepartment(UserUtil.getUser().getFdParent());
		String templateId = requestContext.getParameter("i.docTemplate");
		if (com.landray.kmss.util.StringUtil.isNotNull(templateId)) {
			com.landray.kmss.hr.ratify.model.HrRatifyTemplate docTemplate = (com.landray.kmss.hr.ratify.model.HrRatifyTemplate) getHrRatifyTemplateService()
					.findByPrimaryKey(templateId);
			if (docTemplate != null) {
				hrRatifySign.setDocTemplate(docTemplate);
				List<HrRatifyTKeyword> keyWordList = docTemplate
						.getDocKeyword();
				List tKeyword = new ArrayList();
				for (HrRatifyTKeyword keyWord : keyWordList) {
					HrRatifyMKeyword mKeyword = new HrRatifyMKeyword();
					mKeyword.setDocKeyword(keyWord.getDocKeyword());
					tKeyword.add(mKeyword);
				}
				hrRatifySign.setDocKeyword(tKeyword);
			}
		}
        HrRatifyUtil.initModelFromRequest(hrRatifySign, requestContext);
		if (hrRatifySign.getDocTemplate() != null) {
			hrRatifySign.setExtendFilePath(
					XFormUtil.getFileName(hrRatifySign.getDocTemplate(),
							hrRatifySign.getDocTemplate().getFdTempKey()));
			if (Boolean.FALSE.equals(
					hrRatifySign.getDocTemplate().getDocUseXform())) {
				hrRatifySign.setDocXform(
						hrRatifySign.getDocTemplate().getDocXform());
			}
			hrRatifySign.setDocUseXform(
					hrRatifySign.getDocTemplate().getDocUseXform());
		}
        return hrRatifySign;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrRatifySign hrRatifySign = (HrRatifySign) model;
		if (hrRatifySign.getDocTemplate() != null) {
			dispatchCoreService.initFormSetting(form,
					hrRatifySign.getDocTemplate().getFdTempKey(),
					hrRatifySign.getDocTemplate(),
					hrRatifySign.getDocTemplate().getFdTempKey(),
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

	@Override
	public void setDocNumber(HrRatifyMain mainModel) throws Exception {
		HrRatifySign hrRatifySign = (HrRatifySign) mainModel;
		if (!"10".equals(mainModel.getDocStatus())) {
			String docNumber = getSysNumberFlowService()
					.generateFlowNumber(hrRatifySign);
			hrRatifySign.setDocNumber(docNumber);
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null) {
			return;
		}
		Object obj = event.getSource();
		if (!(obj instanceof HrRatifySign)) {
			return;
		}

		if (event instanceof Event_SysFlowFinish) {
			try {
				HrRatifySign hrRatifySign = (HrRatifySign) obj;
				HrRatifyAgendaConfig config = new HrRatifyAgendaConfig();
				if ("true".equals(config.getFdHrStaffCont())) {
					HrStaffPersonInfo personInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService()
							.findByPrimaryKey(
									hrRatifySign.getFdSignStaff().getFdId());
					HrStaffPersonExperienceContract contract = new HrStaffPersonExperienceContract();
					contract.setFdName(hrRatifySign.getFdSignName());
					HrStaffContractType contType = hrRatifySign
							.getFdSignStaffContType();
					if (contType != null) {
						contract.setFdStaffContType(contType);
						contract.setFdContType(contType.getFdName());
					}
					contract.setFdContStatus("1");
					contract.setFdSignType(hrRatifySign.getFdSignType());
					contract.setFdBeginDate(hrRatifySign.getFdSignBeginDate());
					if (Boolean.TRUE.equals(hrRatifySign.getFdIsLongtermContract())) {
						contract.setFdIsLongtermContract(hrRatifySign.getFdIsLongtermContract());
					} else {
						contract.setFdEndDate(hrRatifySign.getFdSignEndDate());
					}
					contract.setFdHandleDate(
							hrRatifySign.getFdSignHandleDate());
					contract.setFdMemo(hrRatifySign.getFdSignRemark());
					contract.setFdRelatedProcess(
							HrRatifyUtil.getUrl(hrRatifySign));
					contract.setFdPersonInfo(personInfo);
					getHrStaffPersonExperienceContractService().add(contract);
					// 附件获取
						List<SysAttMain> sysAttMainList = getSysAttMainCoreInnerService().findByModelKey(HrRatifySign.class.getName(), 
									hrRatifySign.getFdId(), "attHrExpCont");
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
				hrRatifySign.setDocPublishTime(new Date());
				this.update(hrRatifySign);
				feedbackNotify(hrRatifySign);
				addLog((HrRatifySign) obj);
			} catch (Exception e1) {
				throw new KmssRuntimeException(e1);
			}
		} else if (event instanceof Event_SysFlowDiscard) {
			try {
				HrRatifySign hrRatifySign = (HrRatifySign) obj;
				hrRatifySign.setDocPublishTime(new Date());
				this.update(hrRatifySign);
			} catch (Exception e2) {
				throw new KmssRuntimeException(e2);
			}
		}
	}

	@Override
	public void addLog(HrRatifyMain mainModel) throws Exception {
		HrRatifySign sign = (HrRatifySign) mainModel;
		HrStaffRatifyLog log = new HrStaffRatifyLog();
		log.setFdRatifyType("leave");
		log.setFdRatifyDept(sign.getFdSignDept());
		log.setFdRatifyDate(sign.getFdSignHandleDate());
		JSONObject json = new JSONObject();
		json.put("docSubject", sign.getDocSubject());
		json.put("url", HrRatifyUtil.getUrl(sign));
		log.setFdRatifyProcess(json.toString());
		getHrStaffRatifyLogService().add(log);
	}

	@Override
	public void deleteEntity(HrRatifyMain mainModel) throws Exception {
		HrRatifySign signModel = (HrRatifySign) findByPrimaryKey(
				mainModel.getFdId());
		super.deleteEntity(signModel);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		HrRatifySign signModel = (HrRatifySign) modelObj; 
		if (StringUtil.isNull(signModel.getDocNumber())) {
			setDocNumber(signModel);
		}
		HrStaffContractType contType = signModel.getFdSignStaffContType();
		if (contType != null) {
			signModel.setFdSignContType(contType.getFdName());
		}
		HrRatifyTitleUtil.genTitle(signModel);
		super.update(signModel);
	}

	/*历史归档机制*/
	/*@Override
	public void addFileMainDoc(HttpServletRequest request, String fdId,
			KmArchivesFileTemplate fileTemplate) throws Exception {
		HrRatifySign mainModel = (HrRatifySign) findByPrimaryKey(fdId);
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
			HrRatifySign mainModel, KmArchivesFileTemplate fileTemplate)
			throws Exception {
		KmArchivesMain kmArchivesMain = new KmArchivesMain();
		kmArchivesFileTemplateService.setFileField(kmArchivesMain, fileTemplate,
				mainModel);
		// 归档页面URL(若为多表单，暂时归档默认表单)
		int saveApproval = fileTemplate.getFdSaveApproval() != null
				&& fileTemplate.getFdSaveApproval() ? 1 : 0;
		String url = "/hr/ratify/hr_ratify_sign/hrRatifySign.do?method=printFileDoc&fdId="
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
			HrRatifySign mainModel= (HrRatifySign) findByPrimaryKey(fdId.split(",")[0]);
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
				String url = "/hr/ratify/hr_ratify_leave/hrRatifyLeave.do?method=printFileDoc&fdId="
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

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		HrRatifySign hrRatifySign = (HrRatifySign) modelObj;
		HrStaffContractType contType = hrRatifySign.getFdSignStaffContType();
		if (contType != null) {
			hrRatifySign.setFdSignContType(contType.getFdName());
		}
		return super.add(hrRatifySign);
	}
}
