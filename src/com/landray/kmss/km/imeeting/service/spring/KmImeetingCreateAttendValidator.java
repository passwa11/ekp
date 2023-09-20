package com.landray.kmss.km.imeeting.service.spring;

import java.util.Date;

import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;

/**
 * @author linxiuxian
 *
 */
public class KmImeetingCreateAttendValidator
		implements IAuthenticationValidator {


	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		KmImeetingMain kmImeetingMain = (KmImeetingMain) validatorContext
				.getRecModel();
		Date now = new Date();
		boolean isEnd = false;
		if (kmImeetingMain != null && kmImeetingMain.getFdHoldDate() != null
				&& kmImeetingMain.getFdFinishDate() != null) {
			if (kmImeetingMain.getFdFinishDate().getTime() < now.getTime()) {
				isEnd = true;
			}
		}
		if (kmImeetingMain != null) {
			if ("30".equals(kmImeetingMain.getDocStatus()) && !isEnd) {
				return true;
			}
		}

		return false;
	}

}
