package com.landray.kmss.sys.organization.actions;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrganizationRecentContact;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrganizationRecentContactService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.sso.client.util.StringUtil;

/**
 * 最近联系人 Action
 * 
 * @author
 * @version 1.0 2015-08-03
 */
public class SysOrganizationRecentContactAction extends ExtendAction {
	protected ISysOrganizationRecentContactService sysOrganizationRecentContactService;

	protected ISysOrgElementService sysOrgElementService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysOrganizationRecentContactService == null) {
            sysOrganizationRecentContactService = (ISysOrganizationRecentContactService) getBean("sysOrganizationRecentContactService");
        }
		return sysOrganizationRecentContactService;
	}

	public ActionForward testAddContacts(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ISysOrgElementService sysOrgElementService = (ISysOrgElementService) SpringBeanUtil
				.getBean("sysOrgElementService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysOrgElement.fdOrgType < 16");
		List<SysOrgElement> elements = sysOrgElementService
				.findList(hqlInfo);
		Calendar c = Calendar.getInstance();
		// c.set(Calendar.HOUR_OF_DAY, 11);
		// c.set(Calendar.MINUTE, 28);
		// c.set(Calendar.SECOND, 29);

		for (SysOrgElement e : elements) {
			SysOrganizationRecentContact contact = new SysOrganizationRecentContact();
			contact.setDocCreateTime(c.getTime());
			c.add(Calendar.SECOND, 1);
			contact.setFdUser(UserUtil.getUser());
			contact.setFdContact(e);
			((ISysOrganizationRecentContactService) getServiceImp(request))
					.addContact(contact);
		}

		return null;
	}

	private void response(HttpServletResponse response, String message)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.getOutputStream().write(message.getBytes("UTF-8"));
	}

	public ActionForward addContacts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-addContacts", true, getClass());
		SysOrgPerson person = UserUtil.getUser();
		if (person == null || person.isAnonymous()) {
			response(response, "true");
			return null;
		}
		String contactIds = request.getParameter("contactIds");
		if (StringUtil.isNull(contactIds)) {
			response(response, "true");
			return null;
		}

		String[] contactIdArray = contactIds.split(";");
		if (contactIdArray == null || contactIdArray.length == 0) {
			response(response, "true");
			return null;
		}
		try {
			((ISysOrganizationRecentContactService) getServiceImp(request))
					.addContacts(person, contactIdArray);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			response(response, e.getMessage());
			return null;
		}

		TimeCounter.logCurrentTime("Action-addContacts", false, getClass());

		response(response, "true");
		return null;
	}

	public ActionForward delContacts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-delContacts", true, getClass());
		SysOrgPerson person = UserUtil.getUser();
		if (person == null || person.isAnonymous()) {
			response(response, "true");
			return null;
		}
		String[] contactIdArray = null;
		String contactIds = request.getParameter("contactIds");
		
		if (StringUtil.isNotNull(contactIds)) {
			contactIdArray = contactIds.split(";");
		}
		try {
			((ISysOrganizationRecentContactService) getServiceImp(request))
					.deleteContacts(person, contactIdArray);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			response(response, e.getMessage());
			return null;
		}

		TimeCounter.logCurrentTime("Action-delContacts", false, getClass());

		response(response, "true");
		return null;
	}

	public ActionForward getContacts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int orgType = SysOrgConstant.ORG_FLAG_ALL;
		String type = request.getParameter("type");
		if (StringUtil.isNotNull(type)) {
			orgType = Integer.parseInt(type);
		}
		((ISysOrganizationRecentContactService) getServiceImp(request))
				.findRecentContacts(UserUtil.getUser().getFdId(), orgType);

		return null;
	}

}
