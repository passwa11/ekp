package com.landray.kmss.km.imeeting.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.km.imeeting.dao.IKmImeetingMainFeedbackDao;
import org.hibernate.CacheMode;
import org.hibernate.query.Query;

import java.util.List;

/**
 * 会议回执数据访问接口实现
 */
public class KmImeetingMainFeedbackDaoImp extends BaseDaoImp implements
		IKmImeetingMainFeedbackDao {

	@Override
	public void deleteFeedbackByMeeting(String meetingId) throws Exception {
		String hql = "delete from KmImeetingMainFeedback kmImeetingMainFeedback where kmImeetingMainFeedback.fdMeeting.fdId=:fdId";
		Query query = getHibernateSession().createQuery(hql);
		query.setString("fdId", meetingId);
		query.executeUpdate();
	}

	@Override
	public Long getCountByMeetingAndType(String meetingId, String optType)
			throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("select count(*) from KmImeetingMainFeedback kmImeetingMainFeedback ");
		hql.append(" where kmImeetingMainFeedback.fdMeeting.fdId=:fdId and kmImeetingMainFeedback.fdOperateType=:optType ");
		Query query = getHibernateSession().createQuery(hql.toString());
		query.setString("fdId", meetingId);
		query.setString("optType", optType);
		List<Object> result = query.list();
		if (!result.isEmpty()) {
			return (Long) result.get(0);
		}
		return null;
	}

	@Override
	public Long getAttendCountByMeeting(String meetingId) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql
				.append("select count(*) from KmImeetingMainFeedback kmImeetingMainFeedback ");
		hql.append(" where kmImeetingMainFeedback.fdMeeting.fdId=:fdId and kmImeetingMainFeedback.fdOperateType='01' ");
		Query query = getHibernateSession().createQuery(hql.toString());
		query.setString("fdId", meetingId);
		List<Object> result = query.list();
		if (!result.isEmpty()) {
			return (Long) result.get(0);
		}
		return null;
	}

	@Override
	public Long getFeedbackCountByMeeting(String meetingId) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql
				.append("select count(*) from KmImeetingMainFeedback kmImeetingMainFeedback ");
		hql.append(" where kmImeetingMainFeedback.fdMeeting.fdId=:fdId ");
		Query query = getHibernateSession().createQuery(hql.toString());
		query.setString("fdId", meetingId);
		List<Object> result = query.list();
		if (!result.isEmpty()) {
			return (Long) result.get(0);
		}
		return null;
	}

	@Override
	public Long getUnAttendCountByMeeting(String meetingId) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql
				.append("select count(*) from KmImeetingMainFeedback kmImeetingMainFeedback ");
		hql
				.append(" where kmImeetingMainFeedback.fdMeeting.fdId=:fdId and kmImeetingMainFeedback.fdOperateType='02' ");
		Query query = getHibernateSession().createQuery(hql.toString());
		query.setString("fdId", meetingId);
		List<Object> result = query.list();
		if (!result.isEmpty()) {
			return (Long) result.get(0);
		}
		return null;
	}

}
