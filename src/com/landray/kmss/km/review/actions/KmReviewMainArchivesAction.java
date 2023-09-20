package com.landray.kmss.km.review.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.sys.archives.service.ISysArchivesFileTemplateService;
import com.landray.kmss.sys.archives.service.ISysArchivesSignService;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.component.locker.interfaces.ComponentLockerVersionException;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.review.forms.KmReviewMainForm;
import com.landray.kmss.km.review.model.KmReviewTemplate;
import com.landray.kmss.km.review.service.IKmReviewFeedbackInfoService;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.km.review.service.IKmReviewTemplateService;
import com.landray.kmss.kms.multidoc.service.IKmsMultidocSubsideService;
import com.landray.kmss.sys.category.model.SysCategoryBaseModel;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmAuditNoteService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.print.interfaces.ISysPrintLogCoreService;
import com.landray.kmss.sys.print.interfaces.ISysPrintMainCoreService;
import com.landray.kmss.util.HtmlToMht;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SignUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 创建日期 2019-Aug-02
 * 
 * @author 简建红
 */
public class KmReviewMainArchivesAction extends ExtendAction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KmReviewMainArchivesAction.class);
	
	protected IKmReviewMainService kmReviewMainService;
	protected IKmReviewTemplateService kmReviewTemplateService;
	protected ISysOrgCoreService sysOrgCoreService;
	protected ILbpmAuditNoteService lbpmAuditNoteService;
	protected ISysPrintMainCoreService sysPrintMainCoreService;
	protected ISysPrintLogCoreService sysPrintLogCoreService;
	private IKmsMultidocSubsideService kmsMultidocSubsideService;
	private IKmReviewFeedbackInfoService kmReviewFeedbackInfoService;

	protected IKmsMultidocSubsideService getKmsMultidocSubsideService() {
		if (kmsMultidocSubsideService == null) {
			kmsMultidocSubsideService = (IKmsMultidocSubsideService) getBean(
					"kmsMultidocSubsideService");
		}
		return kmsMultidocSubsideService;
	}

	protected ILbpmAuditNoteService getLbpmAuditNoteService(
			HttpServletRequest request) {
		if (lbpmAuditNoteService == null) {
            lbpmAuditNoteService = (ILbpmAuditNoteService) getBean(
                    "lbpmAuditNoteService");
        }
		return lbpmAuditNoteService;
	}

	@Override
	protected IKmReviewMainService getServiceImp(HttpServletRequest request) {
		if (kmReviewMainService == null) {
            kmReviewMainService = (IKmReviewMainService) getBean(
                    "kmReviewMainService");
        }
		return kmReviewMainService;
	}

	public IKmReviewTemplateService getKmReviewTemplateService() {
		if (kmReviewTemplateService == null) {
            kmReviewTemplateService = (IKmReviewTemplateService) getBean(
                    "kmReviewTemplateService");
        }
		return kmReviewTemplateService;
	}

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
            sysOrgCoreService = (ISysOrgCoreService) getBean(
                    "sysOrgCoreService");
        }
		return sysOrgCoreService;
	}

	public ISysPrintMainCoreService getSysPrintMainCoreService() {
		if (sysPrintMainCoreService == null) {
            sysPrintMainCoreService = (ISysPrintMainCoreService) getBean(
                    "sysPrintMainCoreService");
        }
		return sysPrintMainCoreService;
	}

	public ISysPrintLogCoreService getSysPrintLogCoreService() {
		if (sysPrintLogCoreService == null) {
            sysPrintLogCoreService = (ISysPrintLogCoreService) getBean(
                    "sysPrintLogCoreService");
        }
		return sysPrintLogCoreService;
	}

	public IKmReviewFeedbackInfoService getKmReviewFeedbackInfoService() {
		if (kmReviewFeedbackInfoService == null) {
            kmReviewFeedbackInfoService = (IKmReviewFeedbackInfoService) getBean(
                    "kmReviewFeedbackInfoService");
        }
		return kmReviewFeedbackInfoService;
	}

	private ISysArchivesSignService sysArchivesSignService ;
	public ISysArchivesSignService getSysArchivesSignService() {
		if (sysArchivesSignService == null) {
			sysArchivesSignService = (ISysArchivesSignService) SpringBeanUtil.getBean("sysArchivesSignService");
		}
		return sysArchivesSignService;
	}

	public ActionForward printFileDocArchives(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String sign = request.getParameter("Signature");
			String fdId = request.getParameter("fdId");
			String expires = request.getParameter("Expires");
			if(!getSysArchivesSignService().validateArchivesSignature(expires, fdId, sign, logger)){
				messages.addError(new Exception("Signature is invailid."));
				return mapping.findForward("failure");
			}
			HtmlToMht.setLocaleWhenExport(request);
			loadActionForm(mapping, form, request, response);
			String saveApprovalStr = request.getParameter("saveApproval");
			boolean saveApproval = "1".equals(saveApprovalStr);
			request.setAttribute("saveApproval", saveApproval);
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("filePrint", mapping, form, request,
                    response);
        }
	}
	
	
	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id,
					null, true);
			if (model != null) {
                rtnForm = getServiceImp(request).convertModelToForm(
                        (IExtendForm) form, model, new RequestContext(request));
            }
			UserOperHelper.logFind(model);
		}
		if (rtnForm == null) {
			throw new NoRecordException();
		} else {
			KmReviewMainForm kmReviewMainForm = (KmReviewMainForm) rtnForm;
			String templateId = kmReviewMainForm.getFdTemplateId();
			if (StringUtil.isNotNull(templateId)) {
				KmReviewTemplate template = (KmReviewTemplate) getKmReviewTemplateService()
						.findByPrimaryKey(templateId);
				// 获取路径模板的分类路径
				if (template != null) {
					request.setAttribute("isTempAvailable",
							template.getFdIsAvailable());
					String templatePath = getTemplatePath(template);
					((KmReviewMainForm) form).setFdTemplateName(templatePath);
					if (!template.getFdIsAvailable()) {
						request.setAttribute("templatePath",
								getTemplatePathList(template));
					}
					// 如果同步时机为空，将模板的同步时机赋予主文档
					if (StringUtil.isNull(kmReviewMainForm
							.getSyncDataToCalendarTime())) {
						((KmReviewMainForm) form)
								.setSyncDataToCalendarTime(template
										.getSyncDataToCalendarTime());
					}
					String disableMobileForm = "false";
					if (template.getFdDisableMobileForm() != null) {
						disableMobileForm = template.getFdDisableMobileForm()
								.toString();
					}
					kmReviewMainForm.setFdDisableMobileForm(disableMobileForm);

					// 默认为支持移动端
					String isMobileCreate = "true";
					String isMobileApprove = "true";
					String fdIsMobileView = "true";
					// 是否可复制流程
					String fdIsCopyDoc = "true";
					if (template.getFdIsMobileCreate() != null) {
						isMobileCreate = template.getFdIsMobileCreate()
								.toString();
					}
					if (template.getFdIsMobileApprove() != null) {
						isMobileApprove = template.getFdIsMobileApprove()
								.toString();
					}
					if (template.getFdIsMobileView() != null) {
						fdIsMobileView = template.getFdIsMobileView()
								.toString();
					}
					if (template.getFdIsCopyDoc() != null) {
						fdIsCopyDoc = template.getFdIsCopyDoc().toString();
					}
					// 支持移动端新建
					kmReviewMainForm.setFdIsMobileCreate(isMobileCreate);
					// 支持移动端审批
					kmReviewMainForm.setFdIsMobileApprove(isMobileApprove);
					// 支持移动端查阅
					kmReviewMainForm.setFdIsMobileView(fdIsMobileView);
					// 是否可复制流程
					kmReviewMainForm.setFdIsCopyDoc(fdIsCopyDoc);

					// 获得反馈数
					int fdReviewFeedbackInfoCount = getKmReviewFeedbackInfoService()
							.getKmReviewFeedbackInfoCount(
									kmReviewMainForm.getFdId());
					if (fdReviewFeedbackInfoCount > 0) {
						kmReviewMainForm.setFdReviewFeedbackInfoCount(
								"(" + String.valueOf(fdReviewFeedbackInfoCount)
										+ ")");
					}
					
					Boolean fdIsImport = template.getFdIsImport();
					if (fdIsImport != null && fdIsImport){
						kmReviewMainForm.setFdIsImportXFormData("true");
					}else{
						kmReviewMainForm.setFdIsImportXFormData("false");
					}
				}
			}
		}
		request
				.setAttribute("pdaViewSubmitAction",
						"/km/review/km_review_main/kmReviewMain.do?method=publishDraft");
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}
	
	/**
	 * 发布草稿文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward publishDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmReviewMainForm mainForm = (KmReviewMainForm) form;
		mainForm.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);
		ActionForward af = super.update(mapping, form, request, response);

		// 下面的逻辑是在审批操作中，如果遇到版本冲突时，才会处理的逻辑，#66698
		KmssReturnPage returnPage = (KmssReturnPage) request
				.getAttribute("KMSS_RETURNPAGE");
		KmssMessages kmssMessages = returnPage.getMessages();
		if (kmssMessages != null && kmssMessages.hasError()) {
			List<KmssMessage> msgs = kmssMessages.getMessages();
			if (msgs != null && msgs.size() > 0) {
				KmssMessage msg = msgs.get(0);
				Throwable t = msg.getThrowable();
				if (t != null && t instanceof ComponentLockerVersionException) {
					String id = request.getParameter("fdId");
					if (!StringUtil.isNull(id)) {
						IBaseModel model = getServiceImp(request)
								.findByPrimaryKey(id, null, true);
						if (model != null) {
							// 先将页面的数据转为model
							getServiceImp(request).convertFormToModel(
									(IExtendForm) form, model,
									new RequestContext(request));
							// 再将model数据转为form
							getServiceImp(request).convertModelToForm(
									(IExtendForm) form, model,
									new RequestContext(request));
						}
					}

					// 版本冲突，这里判断的是审批操作，需要跳回到view页面
					af = getActionForward("view", mapping, form, request,
							response);
				}
			}
		}
		return af;
	}
	private List getTemplatePathList(KmReviewTemplate kmReviewTemplate) {
		List pathList = new ArrayList();
		if (kmReviewTemplate != null) {
			pathList.add(kmReviewTemplate.getFdName());
			SysCategoryMain sysCategoryMain = kmReviewTemplate.getDocCategory();
			pathList.add(sysCategoryMain.getFdName());
			SysCategoryBaseModel sysCategoryBaseModel = (SysCategoryBaseModel) sysCategoryMain
					.getFdParent();
			if (sysCategoryBaseModel != null) {
				do {
					pathList.add(sysCategoryBaseModel.getFdName());
					sysCategoryBaseModel = (SysCategoryBaseModel) sysCategoryBaseModel
							.getFdParent();
				} while (sysCategoryBaseModel != null);
			}
		}
		Collections.reverse(pathList);
		return pathList;
	}

	private String getTemplatePath(KmReviewTemplate kmReviewTemplate) {
		String templatePath = "";
		if (kmReviewTemplate != null) {
			SysCategoryMain sysCategoryMain = kmReviewTemplate.getDocCategory();
			SysCategoryBaseModel sysCategoryBaseModel = (SysCategoryBaseModel) sysCategoryMain
					.getFdParent();
			if (sysCategoryBaseModel != null) {
				do {
					templatePath = sysCategoryBaseModel.getFdName() + "/"
							+ templatePath;
					sysCategoryBaseModel = (SysCategoryBaseModel) sysCategoryBaseModel
							.getFdParent();
				} while (sysCategoryBaseModel != null);
				templatePath = templatePath + sysCategoryMain.getFdName() + "/"
						+ kmReviewTemplate.getFdName();
			} else {
				templatePath = sysCategoryMain.getFdName() + "/"
						+ kmReviewTemplate.getFdName();
			}

		}
		return templatePath;
	}
	
}
