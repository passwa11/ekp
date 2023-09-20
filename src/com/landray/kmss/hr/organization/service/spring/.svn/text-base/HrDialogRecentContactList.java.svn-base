package com.landray.kmss.hr.organization.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.hr.organization.service.IHrOrganizationRecentContactService;
import com.landray.kmss.hr.organization.util.HrOrgDialogUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class HrDialogRecentContactList implements IXMLDataBean, SysOrgConstant {
	private IHrOrganizationRecentContactService hrOrganizationRecentContactService;

	public void setSysOrganizationRecentContactService(
			IHrOrganizationRecentContactService hrOrganizationRecentContactService) {
		this.hrOrganizationRecentContactService = hrOrganizationRecentContactService;
	}

	public IHrOrganizationRecentContactService getHrOrganizationRecentContactService() {
		return hrOrganizationRecentContactService;
	}

	public ISysOrganizationStaffingLevelService getSysOrganizationStaffingLevelService() {
		return sysOrganizationStaffingLevelService;
	}

	public void setHrOrganizationRecentContactService(
			IHrOrganizationRecentContactService hrOrganizationRecentContactService) {
		this.hrOrganizationRecentContactService = hrOrganizationRecentContactService;
	}

	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}

	@Override
    @SuppressWarnings("unchecked")
	public List getDataList(RequestContext xmlContext) {
		List elemList = null;
		try {
			SysOrgPerson person = UserUtil.getUser();
			if (person == null) {
				return null;
			}
			String para = xmlContext.getParameter("orgType");
			String selectCount = xmlContext.getParameter("selectCount");
			int orgType = ORG_TYPE_DEFAULT;

			if (para != null && !"".equals(para)) {
				try {
					orgType = Integer.parseInt(para);
				} catch (NumberFormatException e) {
				}
			}


			if (StringUtil.isNotNull(selectCount) && selectCount.matches("\\d+")) {
				elemList = hrOrganizationRecentContactService.findRecentContacts(person.getFdId(), orgType,
						StringUtil.getIntFromString(selectCount, 10));
			} else {
				elemList = hrOrganizationRecentContactService.findRecentContacts(person.getFdId(), orgType);
			}

			elemList = sysOrganizationStaffingLevelService.getStaffingLevelFilterResult(elemList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			return HrOrgDialogUtil.getResultEntries(elemList, xmlContext.getContextPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
