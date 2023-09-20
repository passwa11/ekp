package com.landray.kmss.sys.news.actions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil;
import com.landray.kmss.sys.attachment.model.Attachment;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.news.constant.SysNewsConstant;
import com.landray.kmss.sys.news.forms.SysNewsTemplateForm;
import com.landray.kmss.sys.news.service.ISysNewsTemplateService;
import com.landray.kmss.sys.simplecategory.actions.SysSimpleCategoryAction;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2007-Sep-17
 * 
 * @author 舒斌
 */
public class SysNewsTemplateAction extends SysSimpleCategoryAction

{
	protected ISysNewsTemplateService sysNewsTemplateService;
	protected ISysAttMainCoreInnerService sysAttMainService;
	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysNewsTemplateService == null) {
            sysNewsTemplateService = (ISysNewsTemplateService) getBean("sysNewsTemplateService");
        }
		return sysNewsTemplateService;
	}

	private ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}
	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysNewsTemplateForm templateForm = (SysNewsTemplateForm) super
				.createNewForm(mapping, form, request, response);
		templateForm.setFdImportance("3");
		templateForm.setDocContent(null);
		templateForm.setDocKeywordIds(null);
		templateForm.setDocKeywordNames(null);
		templateForm.setFdContentType(SysNewsConstant.FDCONTENTTYPE_RTF);
		// 设置场所
		if (form instanceof ISysAuthAreaForm) {
			ISysAuthAreaForm sysAuthAreaForm = (ISysAuthAreaForm) form;
			KMSSUser user = UserUtil.getKMSSUser();
			sysAuthAreaForm.setAuthAreaId(user.getAuthAreaId());
			sysAuthAreaForm.setAuthAreaName(user.getAuthAreaName());
		}
		
		//WPS加载项使用
		if(SysAttWpsoaassistUtil.isEnable()) {
			Date currTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String date = sdf.format(currTime);
			String docuName = "新闻管理模板" + date;
			SysAttMain sam = new SysAttMain();
			sam.setFdModelId(templateForm.getFdId());
			sam.setFdModelName("com.landray.kmss.sys.news.model.SysNewsTemplate");
            sam.setFdKey("editonline");
			sam.setFdFileName(docuName);
			SysAttMain attMainFile = getSysAttMainService().addWpsOaassistOnlineFile(sam);
			setAttForm(templateForm,attMainFile,"editonline");

		}
		return templateForm;
	}
	public void setAttForm(SysNewsTemplateForm templateForm, SysAttMain sysAttMain, String settingKey)
			throws Exception {
		IAttachment att = new Attachment();
		Map attForms = att.getAttachmentForms();
		AttachmentDetailsForm attForm = (AttachmentDetailsForm) attForms.get(settingKey);
		attForm.setFdModelId("");
		attForm.setFdModelName("com.landray.kmss.sys.news.model.SysNewsTemplate");
		attForm.setFdKey(settingKey);
		if (!attForm.getAttachments().contains(sysAttMain)) {
			attForm.getAttachments().add(sysAttMain);
		}
		String attids = attForm.getAttachmentIds();
		if (StringUtil.isNull(attids)) {
			attForm.setAttachmentIds(sysAttMain.getFdId());
		} else {
			attForm.setAttachmentIds(sysAttMain.getFdId() + ";" + attids);
		}
		attForms = att.getAttachmentForms();
		Map newAttForms = new HashMap();
		newAttForms.put(settingKey, attForms.get(settingKey));
		
		templateForm.getAttachmentForms().putAll(newAttForms);
	}
	// public ActionForward delete(ActionMapping mapping, ActionForm form,
	// HttpServletRequest request, HttpServletResponse response)
	// throws Exception {
	// KmssMessages messages = new KmssMessages();
	// try {
	// if (!request.getMethod().equals("GET"))  
	// throw new UnexpectedRequestException();
	// String id = request.getParameter("fdId");
	// if (StringUtil.isNull(id))
	// messages.addError(new NoRecordException());
	// else
	// getServiceImp(request).delete(id);
	// } catch (Exception e) {
	// messages.setHasError();
	// SysNewsTemplate template = (SysNewsTemplate) getServiceImp(request)
	// .findByPrimaryKey(request.getParameter("fdId"));
	// messages
	// .addMsg(new KmssMessage(
	// "sys-news:sysNewsTemplate.delete.tip", template
	// .getFdName()));
	// }
	// KmssReturnPage.getInstance(request).addMessages(messages).addButton(
	// KmssReturnPage.BUTTON_RETURN).save(request);
	// if (messages.hasError())
	// return mapping.findForward("failure");
	// else
	// return mapping.findForward("success");
	// }
	//
	// public ActionForward deleteall(ActionMapping mapping, ActionForm form,
	// HttpServletRequest request, HttpServletResponse response)
	// throws Exception {
	// KmssMessages messages = new KmssMessages();
	// try {
	// if (!request.getMethod().equals("POST"))
	// throw new UnexpectedRequestException();
	// String[] ids = request.getParameterValues("List_Selected");
	// if (ids != null)
	// getServiceImp(request).delete(ids);
	// } catch (Exception e) {
	// messages.setHasError();
	// messages.addMsg(new KmssMessage(
	// "sys-news:sysNewsTemplate.deleteall.tip"));
	// }
	// KmssReturnPage.getInstance(request).addMessages(messages).addButton(
	// KmssReturnPage.BUTTON_RETURN).save(request);
	// if (messages.hasError())
	// return mapping.findForward("failure");
	// else
	// return mapping.findForward("success");
	// }

	protected String getParentProperty() {
		return "hbmParent";
	}

	
	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//WPS加载项使用
		if(SysAttWpsoaassistUtil.isEnable()) {
			SysNewsTemplateForm templateForm = (SysNewsTemplateForm) form;
			String id = request.getParameter("fdId");
			Date currTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String date = sdf.format(currTime);
			String docuName = "新闻管理模板" + date;
			SysAttMain sam = new SysAttMain();
			sam.setFdModelId(id);
			sam.setFdModelName("com.landray.kmss.sys.news.model.SysNewsTemplate");
            sam.setFdKey("editonline");
			sam.setFdFileName(docuName);
			SysAttMain attMainFile = getSysAttMainService().addWpsOaassistOnlineFile(sam);
			setAttForm(templateForm,attMainFile,docuName);
		}
		return super.edit(mapping, form, request, response);
	}
}
