package com.landray.kmss.sys.zone.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.zone.forms.SysZonePrivateChangeForm;
import com.landray.kmss.sys.zone.model.SysZonePersonInfo;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.sys.zone.service.ISysZonePrivateChangeService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;

public class SysZonePrivateChangeAction extends BaseAction {
	
	protected ISysZonePrivateChangeService service;
	
	public  ISysZonePrivateChangeService getServiceImp(HttpServletRequest request) {
		if(null == service) {
			service = (ISysZonePrivateChangeService)this.getBean("sysZonePrivateChangeService");
		}
		return service;
	}
	
	protected ISysOrgPersonService personService;
	
	public ISysOrgPersonService getPersonService() {
		if(null == personService) {
			personService = (ISysOrgPersonService)this.getBean("sysOrgPersonService");
		}
		return personService;
	}
	
	protected ISysZonePersonInfoService personInfoService;
	
	public ISysZonePersonInfoService getPersonInfoService() {
		if(null == personInfoService) {
			personInfoService = (ISysZonePersonInfoService)this.getBean("sysZonePersonInfoService");
		}
		return personInfoService;
	}
	
	public ActionForward editPrivate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-editPrivate", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			form.reset(mapping, request);
			SysZonePrivateChangeForm changeForm = (SysZonePrivateChangeForm)form;
			String fdIds = request.getParameter("fdIds");
			if(StringUtil.isNull(fdIds)) {
				throw new UnexpectedRequestException();
			}
			
			String[] ids = fdIds.split(";");
			List<String> idList = ArrayUtil.convertArrayToList(ids);
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("sysOrgPerson.fdId, sysOrgPerson.fdName");
			hqlInfo.setWhereBlock("sysOrgPerson.fdId in(:ids)");
			hqlInfo.setParameter("ids", idList);
			List<Object[]> list = this.getPersonService().findList(hqlInfo);
			if(!ArrayUtil.isEmpty(list)) {
				if(list.size() == 1) {
					SysZonePersonInfo info =
									(SysZonePersonInfo)this.getPersonInfoService()
											.updateGetPerson(list.get(0)[0].toString());
					changeForm
						.setIsContactPrivate(info.getIsContactPrivate() != null && info.getIsContactPrivate() ? "1" : "0");
					changeForm
						.setIsDepInfoPrivate(info.getIsDepInfoPrivate() != null && info.getIsDepInfoPrivate() ? "1" : "0");
					changeForm
						.setIsRelationshipPrivate(info.getIsRelationshipPrivate() != null && info.getIsRelationshipPrivate() ? "1" :"0");
					changeForm
						.setIsWorkmatePrivate(info.getIsWorkmatePrivate() != null && info.getIsWorkmatePrivate() ? "1" : "0");
				}
				String personNames = null;
				String personIds = null;
				for(Object[] obj : list) {
					personIds = StringUtil.linkString(personIds, ";", obj[0].toString());
					personNames = StringUtil.linkString(personNames , ";", obj[1].toString());
				}
				changeForm.setFdIds(personIds);
				changeForm.setFdNames(personNames);
			}
			request.setAttribute("sysZonePrivateChangeForm", changeForm);
			
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-editPrivate", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("editPrivate");
		}
	}
	
	public ActionForward updatePrivate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-updatePrivate", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			this.getServiceImp(request).updatePrivate((IExtendForm)form, new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-updatePrivate", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return mapping.findForward("editPrivate");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return  mapping.findForward("success");
		}
	}
	
}
