package com.landray.kmss.sys.organization.service.spring;

import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrganizationRecentContactService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.RestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/data/sys-organization/orgDialogRecentContactList", method = RequestMethod.POST)
public class OrgDialogRecentContactList implements IXMLDataBean, SysOrgConstant {
	private ISysOrganizationRecentContactService sysOrganizationRecentContactService;

	public ISysOrganizationRecentContactService getSysOrganizationRecentContactService() {
		return sysOrganizationRecentContactService;
	}

	public void setSysOrganizationRecentContactService(
			ISysOrganizationRecentContactService sysOrganizationRecentContactService) {
		this.sysOrganizationRecentContactService = sysOrganizationRecentContactService;
	}

	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	public ISysOrganizationStaffingLevelService getSysOrganizationStaffingLevelService() {
		return sysOrganizationStaffingLevelService;
	}

	public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}

	@ResponseBody
	@RequestMapping("getDataList")
	public RestResponse<?> getDataList(@RequestBody Map<String, Object> vo, HttpServletRequest request) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
		return RestResponse.ok(getDataList(new RequestContext(wrapper)));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List getDataList(RequestContext xmlContext) throws Exception {
		SysOrgPerson person = UserUtil.getUser();
		if (person == null) {
			return null;
		}
		String para = xmlContext.getParameter("orgType");
		String selectCount = xmlContext.getParameter("selectCount");
		String isExternal = xmlContext.getParameter("isExternal");
		int orgType = ORG_TYPE_DEFAULT;

		if (para != null && !"".equals(para)) {
			try {
				orgType = Integer.parseInt(para);
			} catch (NumberFormatException e) {
			}
		}
		List elemList = null;
		Boolean _isExternal = null;
		if (StringUtil.isNotNull(isExternal) && !"null".equals(isExternal) && !"undefined".equals(isExternal)) {
			_isExternal = Boolean.valueOf(isExternal);
		}

		if (StringUtil.isNotNull(selectCount) && selectCount.matches("\\d+")) {
			elemList = sysOrganizationRecentContactService.findRecentContacts(
					person.getFdId(), orgType, StringUtil.getIntFromString(selectCount, 10), _isExternal);
		} else {
			elemList = sysOrganizationRecentContactService
					.findRecentContacts(person.getFdId(), orgType, _isExternal);
		}

		elemList = sysOrganizationStaffingLevelService
				.getStaffingLevelFilterResult(elemList);

		return OrgDialogUtil.getResultEntries(elemList, xmlContext
				.getContextPath());
	}

}
