package com.landray.kmss.km.imeeting.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingMainFeedback;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 会议回执业务对象接口
 */
public interface IKmImeetingMainFeedbackService extends IBaseService {

	/**
	 * 新建会议时初始化会议回执
	 * 
	 * @param kmImeetingMain
	 *            会议安排
	 */
	public void saveFeedbacks(KmImeetingMain kmImeetingMain) throws Exception;

	/**
	 * 删除会议回执
	 * 
	 * @param kmImeetingMain
	 *            会议安排
	 */
	public void deleteFeedbacks(KmImeetingMain kmImeetingMain) throws Exception;

	/**
	 * 根据会议ID和人员ID获取回执操作
	 * 
	 * @param meetingId
	 *            会议ID
	 * @param personId
	 *            回执人ID
	 */
	public String getOptTypeByPerson(String meetingId, String personId)
			throws Exception;

	/**
	 * 根据回执类型获得人员列表
	 * 
	 * @param meetingId
	 *            会议ID
	 * @param optType
	 *            回执类型（参加？不参加？找人代理？）
	 */
	public List<SysOrgElement> getPersonsByOptType(String meetingId,
			String optType) throws Exception;

	/**
	 * 根据回执类型获得人员Id列表
	 * 
	 * @param meetingId
	 *            会议ID
	 * @param optType
	 *            回执类型（参加？不参加？找人代理？）
	 */
	public List<String> getPersonIdsByOptType(String meetingId, String optType)
			throws Exception;

	/**
	 * 根据会议ID和回执类型返回人数
	 */
	public Long getCountByMeetingAndType(String meetingId, String optType)
			throws Exception;

	/**
	 * 根据会议ID统计参加人数
	 * 
	 * @param meeting
	 *            会议ID
	 */
	public Long getAttendCountByMeeting(String meeting) throws Exception;

	/**
	 * 根据会议ID统计不参加人数
	 * 
	 * @param meeting
	 *            会议ID
	 */
	public Long getUnAttendCountByMeeting(String meetingId) throws Exception;

	/**
	 * 根据会议ID统计回执数
	 * 
	 * @param meeting
	 *            会议ID
	 */
	public Long getFeedbackCountByMeeting(String meetingId) throws Exception;

	/**
	 * 会议回执名单导出
	 * 
	 * @param kmImeetingMain
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getFeedBackExport(KmImeetingMain kmImeetingMain,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * 根据类型查找
	 * 
	 * @param fdMeetingId
	 * @param fdType
	 * @param fdAgendaId
	 * @param fdOperateType
	 * @return
	 * @throws Exception
	 */
	public List findFeedBackByType(String fdMeetingId, String fdType,
			String fdAgendaId, String fdOperateType) throws Exception;

	public KmImeetingMainFeedback findFeedBackByElement(String fdMeetingId,
			String elementId) throws Exception;

	public void addFeedBack(KmImeetingMain kmImeetingMain, SysOrgElement sysOrgElement, String type, String adgenId) throws Exception;

	/**
	 * 校验回执的参与人是否在其他会议中有时间上的冲突
	 * @return
	 */
	public String checkIsSameTime(String fdMeetingId, String fdPersonIds) throws Exception;
}
