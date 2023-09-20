package com.landray.kmss.km.imeeting.integrate.aliyun.interfaces;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

public interface IMeetingAliyunProvider {
	
	/**
	 * 是否开启阿里云视频会议
	 */
	public boolean isAliyunEnable() throws Exception;
	
	/**
	 * 获取调用方式  "0"==KK, "1"==会议管理
	 */
	public String getServiceType() throws Exception;

	/**
	 * 获取阿里云视频会议系统地址
	 */
	public String getUrl() throws Exception;

	/**
	 * 获取阿里云地域Region ID
	 */
	public String getRegionID() throws Exception;

	/**
	 * 获取阿里云账号
	 */
	public String getAccessKeyId() throws Exception;

	/**
	 * 获取阿里云访问密钥
	 */
	public String getAccessKeySecret() throws Exception;
	
	/**
	 * 会议人员同步至阿里云
	 * 
	 * @param meetingPersonList 参会人员List
	 * @return 同步结果
	 * @throws Exception
	 */
	public Boolean syncMeetingPersonToAliyun(List<SysOrgPerson> meetingPersonList) throws Exception;
	
	/**
	 * 创建阿里云视频会议
	 * 
	 * @param fdAliCreatorId EKP会议主持人或者创建人ID（作为阿里云会议创建人ID）
	 * @param fdMeetingId EKP会议ID
	 * @param fdMeetingName EKP会议名称
	 * @param meetingPersonList 参会人员List
	 * @return
	 * @throws Exception
	 */
	public Boolean createAliyunMeeting(String fdAliCreatorId, String fdMeetingId,
			String fdMeetingName, List<SysOrgElement> meetingPersonList) throws Exception;
	
	/**
	 * 进入阿里云视频会议
	 * 
	 * @param fdMeetingId EKP会议ID
	 * @param fdMeetingCode 阿里云视频会议口令
	 * @return
	 * @throws Exception
	 */
	public JSON joinAliyunMeeting(String fdMeetingId, String fdMeetingCode) throws Exception;
	
	/**
	 * 获取阿里云视频会议口令
	 * 
	 * @param fdMeetingId EKP会议ID
	 * @return 会议口令
	 * @throws Exception
	 */
	public String getAliMeetingCode(String fdMeetingId) throws Exception;
	
	/**
	 * 获取阿里云会议信息
	 * 
	 * @param fdMeetingId EKP会议ID
	 * @return 阿里云会议信息
	 * @throws Exception
	 */
	public String getAliMeetingInfo(String fdMeetingId) throws Exception;
	
	/**
	 * 根据EKP会议ID更新阿里云会议人员信息
	 * @param fdMeetingId EKP会议ID
	 * @param meetingPersons 会议参会人
	 * @return 操作结果
	 * @throws Exception
	 */
	public boolean updateAliMeetingInfoByMeetingId(String fdMeetingId, List<SysOrgElement> meetingPersons) throws Exception;
	
}
