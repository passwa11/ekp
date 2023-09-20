package com.landray.kmss.km.imeeting.service.spring;

import java.util.Date;

import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingSeatPlan;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.km.imeeting.service.IKmImeetingSeatPlanService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class KmImeetingSeatPlanValidator
		implements IAuthenticationValidator {

	private IKmImeetingMainService kmImeetingMainService;

	public void setKmImeetingMainService(
			IKmImeetingMainService kmImeetingMainService) {
		this.kmImeetingMainService = kmImeetingMainService;
	}

	private IKmImeetingSeatPlanService kmImeetingSeatPlanService;

	public void setKmImeetingSeatPlanService(
			IKmImeetingSeatPlanService kmImeetingSeatPlanService) {
		this.kmImeetingSeatPlanService = kmImeetingSeatPlanService;
	}

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		boolean check = false;
		KmImeetingMain mainModel = null;
		String meetingId = validatorContext.getParameter("fdImeetingMainId");
		if (StringUtil.isNull(meetingId)) {
			String fdId = validatorContext.getParameter("fdId");
			KmImeetingSeatPlan seatPlan = (KmImeetingSeatPlan) kmImeetingSeatPlanService
					.findByPrimaryKey(fdId);
			mainModel = seatPlan.getFdImeetingMain();
		} else {
			mainModel = (KmImeetingMain) kmImeetingMainService
					.findByPrimaryKey(meetingId);
		}

		if(mainModel != null){
			Date hold = mainModel.getFdHoldDate();
			Date current = new Date();
			if (hold.getTime() > current.getTime()) {
				SysOrgPerson currentUser = UserUtil.getUser();
				SysOrgPerson docCreator = mainModel.getDocCreator();

				SysOrgElement fdEmcee = mainModel.getFdEmcee();
				if (fdEmcee != null) {
					SysOrgPerson emcee = UserUtil.getUser(fdEmcee.getFdId());
					check = currentUser.equals(docCreator)
							|| currentUser.equals(emcee);
				} else {
					check = currentUser.equals(docCreator);
				}
			}
		}
		return check;
	}

}
