package com.landray.kmss.sys.portal.util;

import java.util.Date;

import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.StringUtil;

public class EmotionUtil {
	private static String emotions = "";
	private static boolean isEmotionInit = false;

	public static String getEmotionDatas() {
		if (!isEmotionInit) {
			String path = ConfigLocationsUtil.getWebContentPath();
			String fileName = "/sys/portal/sys_portal_portlet/emotion.txt";
			try {
				emotions = FileUtil.getFileString(path + fileName,
						"utf-8");
			} catch (Exception e) {
				emotions = "";
			}
			isEmotionInit = true;
		}
		return emotions;
	}

	/**
	 * 判断当前是否在时间区间内
	 * 
	 * @param startTime
	 *            如:08:00
	 * @param endTime
	 * @param now
	 * @return
	 */
	public static boolean isBetweenTime(String startTime, String endTime) {
		if (StringUtil.isNull(startTime) || StringUtil.isNull(endTime)) {
			return false;
		}
		String[] startTimes = startTime.split(":");
		String[] endTimes = endTime.split(":");

		int start = Integer.valueOf(startTimes[0]) * 60
				+ Integer.valueOf(startTimes[1]);
		int end = Integer.valueOf(endTimes[0]) * 60
				+ Integer.valueOf(endTimes[1]);
		Date date = new Date();
		int now = date.getHours() * 60 + date.getMinutes();
		if (start <= end) {
			if (now >= start && now <= end) {
				return true;
			}
		} else {
			int startTime1 = start;
			int endTime1 = 23 * 60 + 59;
			int startTime2 = 0;
			int endTime2 = end;
			if (now >= startTime1 && now <= endTime1
					|| now >= startTime2 && now <= endTime2) {
				return true;
			}
		}
		return false;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// getEmotionDatas();

	}

}
