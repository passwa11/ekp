package com.landray.kmss.km.imeeting.dao;

import com.landray.kmss.common.dao.IBaseDao;

/**
 * 会议回执数据访问接口
 */
public interface IKmImeetingMainFeedbackDao extends IBaseDao {

	/**
	 * 批量删除会议回执
	 */
	public void deleteFeedbackByMeeting(String meetingId) throws Exception;

	/**
	 * 根据会议ID获取参加会议人数
	 */
	public Long getAttendCountByMeeting(String meetingId) throws Exception;

	/**
	 * 根据会议ID和回执类型取得数量
	 */
	public Long getCountByMeetingAndType(String meetingId, String optType)
			throws Exception;

	/**
	 * 根据会议ID获取不参加会议人数
	 */
	public Long getUnAttendCountByMeeting(String meetingId) throws Exception;

	/**
	 * 根据会议ID获取回执总人数
	 */
	public Long getFeedbackCountByMeeting(String meetingId) throws Exception;

}
