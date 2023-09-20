package com.landray.kmss.km.review.model;

import com.landray.kmss.sys.right.interfaces.TemporaryAuthMain;


public class KmReviewTempAuthMain extends TemporaryAuthMain {

	private KmReviewMain fdMain;

	public KmReviewMain getFdMain() {
		return fdMain;
	}

	public void setFdMain(KmReviewMain fdMain) {
		this.fdMain = fdMain;
	}

	@Override
	public Class getFormClass() {
		return null;
	}
}
