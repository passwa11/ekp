package com.landray.kmss.km.review.dao.hibernate;

import org.hibernate.Session;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.km.review.dao.IKmReviewFeedbackInfoDao;


/**
 * 创建日期 2007-Aug-30
 * @author 舒斌
 * 反馈信息数据访问接口实现
 */
public class KmReviewFeedbackInfoDaoImp extends BaseDaoImp implements IKmReviewFeedbackInfoDao {

	@Override
	public int getKmReviewFeedbackInfoCount(String reviewId) {
		String whereStr = " where kmReviewFeedbackInfo.kmReviewMain.fdId = '"
				+ reviewId + "'";
		Session session = super.getSession();
		int total = ((Long) session.createQuery(
				"select count(*) from com.landray.kmss.km.review.model.KmReviewFeedbackInfo"
						+ " kmReviewFeedbackInfo " + whereStr)
				.iterate().next())
						.intValue();
		return total;
	}
}
