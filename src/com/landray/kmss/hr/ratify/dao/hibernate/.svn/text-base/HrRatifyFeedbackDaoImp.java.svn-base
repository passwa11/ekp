package com.landray.kmss.hr.ratify.dao.hibernate;

import java.util.Date;

import org.hibernate.Session;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.ratify.dao.IHrRatifyFeedbackDao;
import com.landray.kmss.hr.ratify.model.HrRatifyFeedback;
import com.landray.kmss.util.UserUtil;

public class HrRatifyFeedbackDaoImp extends BaseDaoImp implements IHrRatifyFeedbackDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        HrRatifyFeedback hrRatifyFeedback = (HrRatifyFeedback) modelObj;
        if (hrRatifyFeedback.getDocCreator() == null) {
            hrRatifyFeedback.setDocCreator(UserUtil.getUser());
        }
        if (hrRatifyFeedback.getDocCreateTime() == null) {
            hrRatifyFeedback.setDocCreateTime(new Date());
        }
        return super.add(hrRatifyFeedback);
    }

	@Override
	public int getKmReviewFeedbackInfoCount(String reviewId) {
		String whereStr = " where hrRatifyFeedback.fdDoc.fdId = '"
				+ reviewId + "'";
		Session session = super.getSession();
		int total = ((Long) session.createQuery(
				"select count(*) from com.landray.kmss.hr.ratify.model.HrRatifyFeedback"
						+ " hrRatifyFeedback " + whereStr)
				.iterate().next())
						.intValue();
		return total;
	}
}
