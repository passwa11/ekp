package com.landray.kmss.km.imeeting.service.spring;

import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.UserUtil;

public class KmImeetingMainFeedbackValidator
		implements IAuthenticationValidator {

	private IKmImeetingMainService kmImeetingMainService;

	public void setKmImeetingMainService(
			IKmImeetingMainService kmImeetingMainService) {
		this.kmImeetingMainService = kmImeetingMainService;
	}

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		boolean check = false;
		String meetingId = validatorContext.getParameter("meetingId");
		KmImeetingMain mainModel = (KmImeetingMain) kmImeetingMainService
				.findByPrimaryKey(meetingId);
		SysOrgPerson currentUser = UserUtil.getUser();
		SysOrgPerson docCreator = mainModel.getDocCreator();
		SysOrgElement fdEmcee = mainModel.getFdEmcee();
		if (fdEmcee != null) {
			SysOrgPerson emcee = UserUtil.getUser(fdEmcee.getFdId());
			check = currentUser.equals(docCreator) || currentUser.equals(emcee);
		} else {
			check = currentUser.equals(docCreator);
		}
		return check;
	}

}
