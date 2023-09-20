package com.landray.kmss.km.imeeting.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.km.imeeting.integrate.aliyun.IMeetingAliyunPluginUtil;
import com.landray.kmss.km.imeeting.integrate.aliyun.interfaces.IMeetingAliyunPlugin;
import com.landray.kmss.km.imeeting.integrate.aliyun.interfaces.IMeetingAliyunProvider;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.StringUtil;

/**
 * 阿里云视频会议工具类
 * 
 * @author Butterball
 * @version 1.0 2020-08-17
 */
public class AliMeetingUtil {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AliMeetingUtil.class);

	private static IMeetingAliyunProvider getAliMeetingProvider() throws Exception {
		IMeetingAliyunProvider provider = null;
		List<IMeetingAliyunPlugin> plugins = IMeetingAliyunPluginUtil.getExtensionList();
		if (plugins != null && plugins.size() > 0) {
			for (IMeetingAliyunPlugin plugin : plugins) {
				String fdKey = plugin.getKey();
				if (StringUtil.isNotNull(fdKey) && "alimeeting".equals(fdKey)) {
					provider = plugin.getProvider();
					break;
				}
			}
		}
		return provider;
	}

	/**
	 * 是否开启阿里云视频会议
	 */
	public static boolean isAliyunEnable() throws Exception {
		try {
			IMeetingAliyunProvider provider = getAliMeetingProvider();
			if (provider != null) {
				return provider.isAliyunEnable();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取阿里云视频会议调用方式
	 */
	public static String getServiceType() throws Exception {
		String serviceType = "";
		try {
			IMeetingAliyunProvider provider = getAliMeetingProvider();
			if (provider != null) {
				serviceType = provider.getServiceType();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serviceType;
	}

	/**
	 * 获取阿里云服务器地址
	 */
	public static String getAliyunUrl() throws Exception {
		IMeetingAliyunProvider provider = getAliMeetingProvider();
		if (provider != null) {
			return provider.getUrl();
		}
		return "";
	}

	/**
	 * 获取阿里云数据中心的地域ID
	 */
	public static String getRegionID() throws Exception {
		IMeetingAliyunProvider provider = getAliMeetingProvider();
		if (provider != null) {
			return provider.getRegionID();
		}
		return "";
	}

	/**
	 * 获取阿里云账号
	 */
	public static String getAccessKeyId() throws Exception {
		IMeetingAliyunProvider provider = getAliMeetingProvider();
		if (provider != null) {
			return provider.getAccessKeyId();
		}
		return "";
	}

	/**
	 * 获取阿里云访问密钥
	 */
	public static String getAccessKeySecret() throws Exception {
		IMeetingAliyunProvider provider = getAliMeetingProvider();
		if (provider != null) {
			return provider.getAccessKeySecret();
		}
		return "";
	}

	/**
	 * 会议人员同步至阿里云
	 * 
	 * @param meetingPersonList 参会人员List
	 * @return 同步结果
	 * @throws Exception
	 */
	public static Boolean syncMeetingPersonToAliyun(List<SysOrgPerson> meetingPersonList) throws Exception {
		try {
			IMeetingAliyunProvider provider = getAliMeetingProvider();
			if (provider != null) {
				return provider.syncMeetingPersonToAliyun(meetingPersonList);
			}
		} catch (Exception e) {
			logger.error("会议人员同步至阿里云出现异常");
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}

	/**
	 * 创建阿里云视频会议
	 * 
	 * @param fdAliCreatorId    EKP会议主持人或者创建人ID（作为阿里云会议创建人ID）
	 * @param fdMeetingId       EKP会议ID
	 * @param fdMeetingName     EKP会议名称
	 * @param meetingPersonList 参会人员List
	 * @return
	 * @throws Exception
	 */
	public static Boolean createAliyunMeeting(String fdAliCreatorId, String fdMeetingId, String fdMeetingName,
			List<SysOrgElement> meetingPersonList) throws Exception {
		try {
			IMeetingAliyunProvider provider = getAliMeetingProvider();
			if (provider != null) {
				return provider.createAliyunMeeting(fdAliCreatorId, fdMeetingId, fdMeetingName, meetingPersonList);
			}
		} catch (Exception e) {
			logger.error("创建阿里云视频会议出现异常");
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}

	/**
	 * 获取阿里云视频会议口令
	 * 
	 * @param fdMeetingId EKP会议ID
	 * @return 阿里云视频会议口令
	 * @throws Exception
	 */
	public static String getAliMeetingCode(String fdMeetingId) throws Exception {
		try {
			IMeetingAliyunProvider provider = getAliMeetingProvider();
			if (provider != null) {
				return provider.getAliMeetingCode(fdMeetingId);
			}
		} catch (Exception e) {
			logger.error("获取阿里云视频会议口令出现异常");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取阿里云会议UUID信息
	 * 
	 * @param fdMeetingId EKP会议ID
	 * @return 阿里云会议信息
	 * @throws Exception
	 */
	public static String getAliMeetingInfo(String fdMeetingId) throws Exception {
		try {
			IMeetingAliyunProvider provider = getAliMeetingProvider();
			if (provider != null) {
				return provider.getAliMeetingInfo(fdMeetingId);
			}
		} catch (Exception e) {
			logger.error("获取阿里云视频会议UUID出现异常");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据EKP会议ID更新阿里云会议人员信息
	 * 
	 * @param fdMeetingId 阿里云会议信息
	 * @param meeetingPersons 云会议参会人员
	 * @return 操作结果
	 * @throws Exception
	 */
	public static boolean updateAliMeetingInfo(String fdMeetingId, List<SysOrgElement> meeetingPersons) throws Exception {
		try {
			IMeetingAliyunProvider provider = getAliMeetingProvider();
			if (provider != null) {
				return provider.updateAliMeetingInfoByMeetingId(fdMeetingId, meeetingPersons);
			}
		} catch (Exception e) {
			logger.error("获取阿里云视频会议口令出现异常");
			e.printStackTrace();
		}
		return false;
	}

}
