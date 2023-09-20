package com.landray.kmss.km.smissive.actions;

import java.net.URLDecoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.smissive.forms.KmSmissiveTemplateForm;
import com.landray.kmss.km.smissive.service.IKmSmissiveTemplateService;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil;
import com.landray.kmss.sys.attachment.model.Attachment;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.simplecategory.actions.SysSimpleCategoryAction;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 创建日期 2010-五月-04
 * 
 * @author 张鹏xn
 */
public class KmSmissiveTemplateAction extends SysSimpleCategoryAction

{
	protected IKmSmissiveTemplateService kmSmissiveTemplateService;
	private ISysAttMainCoreInnerService sysAttMainService = null;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmSmissiveTemplateService == null) {
            kmSmissiveTemplateService = (IKmSmissiveTemplateService) getBean("kmSmissiveTemplateService");
        }
		return kmSmissiveTemplateService;
	}

	
		
	private ISysAttMainCoreInnerService getSysAttMainService() {
			if (sysAttMainService == null) {
				sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
						.getBean("sysAttMainService");
			}
			return sysAttMainService;
		}
	
	protected String getParentProperty() {
		return "hbmParent";
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		KmSmissiveTemplateForm kmSmissiveTemplateForm = (KmSmissiveTemplateForm) super
				.createNewForm(mapping, form, request, response);
		kmSmissiveTemplateForm.setFdCodePre(null);
		kmSmissiveTemplateForm.setFdCurNo("0");
		Calendar calendar = Calendar.getInstance();
		kmSmissiveTemplateForm.setFdYear(String.valueOf(calendar
				.get(Calendar.YEAR)));
		if(SysAttWpsoaassistUtil.isEnable()) {
			SysAttMain sam = new SysAttMain();
			sam.setFdModelId(kmSmissiveTemplateForm.getFdId());
			sam.setFdModelName("com.landray.kmss.km.smissive.model.KmSmissiveTemplate");
            sam.setFdKey("mainContent");
			sam.setFdFileName("editonline");
			SysAttMain attMainFile = getSysAttMainService().addWpsOaassistOnlineFile(sam);
			setAttForm(kmSmissiveTemplateForm,attMainFile,"mainContent");
		}
		return kmSmissiveTemplateForm;
	}
	
	public void setAttForm(KmSmissiveTemplateForm templateForm, SysAttMain sysAttMain, String settingKey)
			throws Exception {
		IAttachment att = new Attachment();
		Map attForms = att.getAttachmentForms();
		AttachmentDetailsForm attForm = (AttachmentDetailsForm) attForms.get(settingKey);
		attForm.setFdModelId("");
		attForm.setFdModelName("c");
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

	public void checkUniqueCodePre(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		String fdCodePre = StringUtil.getString(request
				.getParameter("fdCodePre"));
		String fdId = StringUtil.getString(request.getParameter("fdId"));
		fdCodePre = URLDecoder.decode(fdCodePre, "UTF-8");
		
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmSmissiveTemplate.fdId");
		StringBuilder sb = new StringBuilder();
		sb.append(" kmSmissiveTemplate.fdId <> :fdId and ");
		sb.append(" kmSmissiveTemplate.fdCodePre=:fdCodePre ");
		hqlInfo.setParameter("fdId", fdId);
		hqlInfo.setParameter("fdCodePre", fdCodePre);
		hqlInfo.setWhereBlock(sb.toString());
		List<?> list = getServiceImp(request).findValue(hqlInfo);
		if (list.isEmpty()) {
			response.getWriter().print("true");
		} else {
			response.getWriter().print("false");
		}
	}

	@Override
	public ActionForward saveadd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								 HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-saveadd", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).add((IExtendForm) form, new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-saveadd", false, getClass());
		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		if (messages.hasError()) {
            return getActionForward("edit", mapping, form, request, response);
        } else {
			KmSmissiveTemplateForm newForm = (KmSmissiveTemplateForm) form;
			newForm.getSysNumberMainMappForm().setFdNumberId("");
			return add(mapping, form, request, response);
		}
	}
}
