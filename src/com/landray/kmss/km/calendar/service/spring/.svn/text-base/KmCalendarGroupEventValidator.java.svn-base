package com.landray.kmss.km.calendar.service.spring;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.landray.kmss.km.calendar.model.KmCalendarPersonGroup;
import com.landray.kmss.km.calendar.service.IKmCalendarPersonGroupService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class KmCalendarGroupEventValidator implements IAuthenticationValidator {

	private IKmCalendarPersonGroupService kmCalendarPersonGroupService;

	public void setKmCalendarPersonGroupService(
			IKmCalendarPersonGroupService kmCalendarPersonGroupService) {
		this.kmCalendarPersonGroupService = kmCalendarPersonGroupService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		String recid = validatorContext.getValidatorParaValue("recid");
		if (StringUtil.isNotNull(recid)) {
			KmCalendarPersonGroup personGroup = (KmCalendarPersonGroup) kmCalendarPersonGroupService
					.findByPrimaryKey(recid);
			Set<String> authEditors = new HashSet<String>();
			String type = validatorContext.getValidatorPara("type");
			List<String> readers = sysOrgCoreService
					.expandToPersonIds(personGroup.getAuthReaders());
			List<String> editors = sysOrgCoreService
					.expandToPersonIds(personGroup.getAuthEditors());
			List<String> mems = sysOrgCoreService
					.expandToPersonIds(personGroup.getFdPersonGroup());
			if ("reader".equals(type)) {
				authEditors.addAll(editors);
				authEditors.addAll(readers);
				authEditors.addAll(mems);
			}
			if ("editor".equals(type)) {
				authEditors.addAll(editors);
			}
			SysOrgPerson curUser = UserUtil.getUser();
			if (authEditors.contains(curUser.getFdId())) {
				return true;
			}
		}
		return false;
	}
}
