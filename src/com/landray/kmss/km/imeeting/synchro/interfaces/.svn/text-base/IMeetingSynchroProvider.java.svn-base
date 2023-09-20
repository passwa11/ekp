package com.landray.kmss.km.imeeting.synchro.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IMeetingSynchroProvider {

	/**
	 * 新增会议
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sendMeeting(SynchroCommonMetting synchroCommonMetting)
			throws Exception;

	/**
	 * 更新会议
	 * 
	 * @return
	 * @throws Exception
	 */
	public void updateMeeting(String uuid,
			SynchroCommonMetting synchroCommonMetting) throws Exception;

	/**
	 * 更新指定人员的会议
	 * 
	 * @throws Exception
	 */
	// public void updateMeeting(String uuid,
	// SynchroCommonMetting synchroCommonMetting, SysOrgPerson sysOrgPerson)
	// throws Exception;
	/**
	 * 取消会议
	 * 
	 * @return
	 * @throws Exception
	 */
	public void cancelMeeting(String uuid,
			SynchroCommonMetting synchroCommonMetting,
			String cancellationMessageText) throws Exception;

	/**
	 * 删除会议
	 * 
	 * @return
	 * @throws Exception
	 */
	public void deleteMeeting(String uuid,
			SynchroCommonMetting synchroCommonMetting) throws Exception;

	/**
	 * 获取某个会议的接受情况
	 * 
	 * @param creatorId
	 * @param uuid
	 * @return 返回一个map，key值为用户id，value为接受情况
	 */
	public Map<String, IMeetingResponseType> getMeetingResponse(
			String creatorId, String uuid);

	/**
	 * 获取某个会议的接受情况
	 * 
	 * @param creatorId
	 * @param uuid
	 * @return 返回SynchroMeetingResponse的列表
	 */
	public List<SynchroMeetingResponse> getMeetingResponseList(
			String creatorId,
			String uuid);

	/**
	 * 是否启用会议同步
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean isSynchroEnable() throws Exception;

	public List<SynchroCommonMetting> getMeetings(String personId, Date date)
			throws Exception;

	public List<String> getPersonIdsToSyncro();

	public boolean isNeedSyncro(String personId);

	public List<String> getDeletedMeetings(String personId,
			Date date) throws Exception;

	public IMeetingSynchroProvider getNewInstance(String personId);

}
