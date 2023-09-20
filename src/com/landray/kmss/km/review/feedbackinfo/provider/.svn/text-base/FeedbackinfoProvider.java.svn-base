package com.landray.kmss.km.review.feedbackinfo.provider;

import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.sys.mobile.configBusiness.extInterface.IFeedbackInfoConfig;
import com.landray.kmss.util.SpringBeanUtil;

public class FeedbackinfoProvider implements IFeedbackInfoConfig {


    @Override
    public Boolean isFeedbackinfoAuthEnabled(String fdId) throws Exception {
        IKmReviewMainService  kmReviewMainService = (IKmReviewMainService) SpringBeanUtil.getBean("kmReviewMainService");

        KmReviewMain kmReviewMain = (KmReviewMain) kmReviewMainService.findByPrimaryKey(fdId);
        if("30".equals(kmReviewMain.getDocStatus()) || "31".equals(kmReviewMain.getDocStatus())) {
            return true;
        }
        return false;
    }

}
