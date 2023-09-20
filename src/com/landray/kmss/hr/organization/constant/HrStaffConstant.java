package com.landray.kmss.hr.organization.constant;

public interface HrStaffConstant {

	/**
	 * 试用、实习、正式、临时、试用延期、解聘、离职、退休、兼职
	 */
	enum AllStaffStatus {
		trial, practice, official, temporary, trialDelay, dismissal, leave, retire, parttime;
	}

	/**
	 * 解聘、离职、退休
	 */
	enum LeaveStaffStatus {
		dismissal, leave, retire;
	}

}
