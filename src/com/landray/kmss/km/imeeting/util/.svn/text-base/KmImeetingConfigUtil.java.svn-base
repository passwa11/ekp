package com.landray.kmss.km.imeeting.util;

import java.util.Map;

import com.landray.kmss.km.imeeting.model.KmImeetingConfig;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;

public class KmImeetingConfigUtil{

	private static final String TOPIC_MNG_ON = "2";
	private static final String TOPIC_MNG_OFF = "1";
	private static final String USE_CLOUD_ON = "2";
	private static final String USE_CLOUD_OFF = "1";
	private static final String TOPIC_ACCEPT_REPEAT_ON = "2";
	private static final String TOPIC_ACCEPT_REPEAT_OFF = "1";

	private static final String VIDEO_MEETING_ON = "2";
	private static final String VIDEO_MEETING_OFF = "1";

	private static final String USE_CYCLICITY_NO = "1";
	private static final String USE_CYCLICITY_ALL = "2";
	private static final String USE_CYCLICITY_OTHER = "3";


	
	public static Boolean isBoenEnable() throws Exception {
		return BoenUtil.isBoenEnable();
	}

	public static String getBoenUrl() throws Exception {

		return BoenUtil.getBoenUrl();
	}

	public static JSONObject getTopOrg() throws Exception {

		return BoenUtil.getTopOrg();
	}

	public static String getUnitAdmin() throws Exception {

		return BoenUtil.getUnitAdmin();
	}

	public static String getBoenToken() throws Exception {

		return BoenUtil.getBoenToken();
	}

	public static String isTopicMng() throws Exception {
		KmImeetingConfig config = new KmImeetingConfig();
		Boolean flag = Boolean.FALSE;
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			String topicMng = (String) map.get("topicMng");
			if (KmImeetingConfigUtil.TOPIC_MNG_ON.equals(topicMng)) {
				flag = Boolean.TRUE;
			}
		}
		if (BoenUtil.isBoenEnable()) {
			flag = Boolean.TRUE;
		}
		return flag.toString();
	}

	public static String isTopicAcceptRepeat() throws Exception {
		KmImeetingConfig config = new KmImeetingConfig();
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			String topicAcceptRepeat = (String) map.get("topicAcceptRepeat");
			if (KmImeetingConfigUtil.TOPIC_ACCEPT_REPEAT_ON.equals(topicAcceptRepeat)) {
				return Boolean.TRUE.toString();
			} else if (KmImeetingConfigUtil.TOPIC_ACCEPT_REPEAT_OFF.equals(topicAcceptRepeat)) {
				return Boolean.FALSE.toString();
			}
		} else {
			return Boolean.FALSE.toString();
		}
		return Boolean.FALSE.toString();
	}

	/**
	 * 暂时隐藏视频会议
	 */
	public static Boolean isVideoMeetingEnable() throws Exception {
//		KmImeetingConfig config = new KmImeetingConfig();
//		Map map = config.getDataMap();
//		if (!map.isEmpty()) {
//			String kkVideoMeeting = (String) map.get("videoMeeting");
//			if (KmImeetingConfigUtil.VIDEO_MEETING_ON.equals(kkVideoMeeting)) {
//				return Boolean.TRUE;
//			} else if (KmImeetingConfigUtil.VIDEO_MEETING_OFF.equals(kkVideoMeeting)) {
//				return Boolean.FALSE;
//			}
//		} else {
//			return Boolean.FALSE;
//		}
		return Boolean.FALSE;
	}


	public static String isUseCloudMng() throws Exception {
		KmImeetingConfig config = new KmImeetingConfig();
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			String useCloudMng = (String) map.get("useCloudMng");
			if (KmImeetingConfigUtil.USE_CLOUD_ON.equals(useCloudMng)) {
				return Boolean.TRUE.toString();
			} else if (KmImeetingConfigUtil.USE_CLOUD_OFF.equals(useCloudMng)) {
				return Boolean.FALSE.toString();
			}
		} else {
			return Boolean.FALSE.toString();
		}
		return Boolean.FALSE.toString();
	}

	/**
	 * 是否开启周期性会议
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String isCycle() throws Exception {
		KmImeetingConfig config = new KmImeetingConfig();
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			String useCyclicity = (String) map.get("useCyclicity");
			String useCyclicityPersonId = (String) map.get("useCyclicityPersonId");
			if (KmImeetingConfigUtil.USE_CYCLICITY_NO.equals(useCyclicity)) {
				return Boolean.FALSE.toString();
			} else if (KmImeetingConfigUtil.USE_CYCLICITY_ALL
					.equals(useCyclicity)) {
				return Boolean.TRUE.toString();
			} else if (KmImeetingConfigUtil.USE_CYCLICITY_OTHER
					.equals(useCyclicity)) {
				if (StringUtil.isNotNull(useCyclicityPersonId)
						&& useCyclicityPersonId
								.contains(UserUtil.getUser().getFdId())) {
					return Boolean.TRUE.toString();
				}
			}
		} else {
			return Boolean.FALSE.toString();
		}
		return Boolean.FALSE.toString();
	}
}
