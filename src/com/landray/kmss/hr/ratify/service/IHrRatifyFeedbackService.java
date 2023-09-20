package com.landray.kmss.hr.ratify.service;

import java.util.List;

import com.landray.kmss.hr.ratify.model.HrRatifyFeedback;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IHrRatifyFeedbackService extends IExtendDataService {

    public abstract List<HrRatifyFeedback> findByFdDoc(HrRatifyMain fdDoc) throws Exception;

	/**
	 * 通过流程id得到反馈数
	 * 
	 * @param reviewId
	 * @return
	 * @throws Exception
	 */
	public int getKmReviewFeedbackInfoCount(String reviewId) throws Exception;
}
