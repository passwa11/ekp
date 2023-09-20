package com.landray.kmss.km.imeeting.integrate.interfaces;

import java.util.Map;

public interface IMeetingVideoProvider {
	
	public boolean isEnabled() throws Exception;

	/**
	 * 预约视频会议
	 * 
	 * @return 对于全时，会返回一下4个信息<br>
	 *         pcode1（主持人密码）:2014297995<br>
	 *         pcode2（参会人密码）:2014297994<br>
	 *         conferenceId（会议ID号）:589317531<br>
	 *         conferenceUrl（进入会议的URL地址）:http://cloud.landray.com.cn:7000
	 *         /Quanshi/Conference/join/u/b1b11f4bffcd160ebd655ffc037a5741<br>
	 * @throws Exception
	 */
	public Map<String, String> orderVideoMeeting(
			CommonVideoMetting commonVideoMetting) throws Exception;

	/**
	 * 更新会议信息
	 * 
	 * @param synchroCommonMetting
	 * @return 成功则返回“success”
	 * @throws Exception
	 */
	public String updateVideoMeeting(String confrencereId,
			CommonVideoMetting commonVideoMetting) throws Exception;

	/**
	 * 取消会议
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public void cancelVideoMeeting(String confrencereId, String CancelMemo)
			throws Exception;

	/**
	 * 获取进入视频会议的链接
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getVideoMeetingUrl(String conferenceUrl,
			CommonVideoMettingPerson attendee) throws Exception;

}
