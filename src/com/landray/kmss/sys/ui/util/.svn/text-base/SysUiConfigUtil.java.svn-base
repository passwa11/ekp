package com.landray.kmss.sys.ui.util;

import com.landray.kmss.sys.ui.model.SysUiConfig;
import com.landray.kmss.util.StringUtil;

public class SysUiConfigUtil {

	/**
	 * 页面宽度限定
	 */
	public static String getFdMaxWidth() throws Exception {
		SysUiConfig config = new SysUiConfig();
		String fdMaxWidth = (String) config.getDataMap().get("fdMaxWidth");
		if (StringUtil.isNull(fdMaxWidth)) {
			fdMaxWidth = "1200px";
		}
		return fdMaxWidth+"px";
	}
	
	/**
	 * 页面宽度
	 */
	public static String getFdWidth() throws Exception {
		SysUiConfig config = new SysUiConfig();
		String fdWidth = (String) config.getDataMap().get("fdWidth");
		if (StringUtil.isNull(fdWidth)) {
			fdWidth = "980px";
		}
		return fdWidth;
	}

	/**
	 * 个人模板左则列宽度
	 */
	public static String getFdPersonLeftSide() throws Exception {
		SysUiConfig config = new SysUiConfig();
		String fdPersonLeftSide = (String) config.getDataMap().get("fdPersonLeftSide");
		if (StringUtil.isNull(fdPersonLeftSide)) {
			fdPersonLeftSide = "175";
		}
		return fdPersonLeftSide;
	}
	
	/**
	 * 用户登录门户设置（用户进行登录时，是否直接登录到用户设置的默认场所下的门户）
	 */
	public static boolean getIsLoginDefaultAreaPortal() throws Exception {
		boolean bool = false;
		SysUiConfig config = new SysUiConfig();
		String loginDefaultAreaPortal = (String) config.getDataMap().get("loginDefaultAreaPortal");
		if (StringUtil.isNotNull(loginDefaultAreaPortal)) {
			bool = "true".equals(loginDefaultAreaPortal)?true:false;
		}
		return bool;
	}

	/**
	 * 用户漫游到其它场所的同时切换到该场所下的门户
	 */
	public static boolean getIsRoamSwitchPortal() throws Exception {
		boolean bool = false;
		SysUiConfig config = new SysUiConfig();
		String roamSwitchPortal = (String) config.getDataMap().get("roamSwitchPortal");
		if (StringUtil.isNotNull(roamSwitchPortal)) {
			bool = "true".equals(roamSwitchPortal)?true:false;
		}
		return bool;
	}

	/**
	 * 获取后台配置logo
	 */
	public static String getProfileLogoTitle() throws Exception {
		SysUiConfig config = new SysUiConfig();
		String title = (String) config.getDataMap()
				.get("logoTitle");
		if (StringUtil.isNull(title)) {
			title = "/sys/profile/resource/images/logo.png";
		}
		return title;
	}
	
	
	/**
	 * 是否全站置为灰色，用于哀悼
	 */
	public static String getFdIsSysMourning() throws Exception {
		SysUiConfig config = new SysUiConfig();
		String fdIsSysMourning = (String) config.getDataMap().get("fdIsSysMourning");
		if (StringUtil.isNull(fdIsSysMourning)) {
			fdIsSysMourning = "false";
		}
		return fdIsSysMourning;
	}
}
