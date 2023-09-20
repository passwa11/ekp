package com.landray.kmss.sys.news.util;

import com.landray.kmss.sys.news.model.SysNewsMain;

public class SysNewsUtils {

	/**
	 * 获取图片URL，多用于移动端列表页面加载
	 */
	public static String getImgUrl(SysNewsMain sysNewsMain) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("/sys/news/sys_news_main/sysNewsMain.do");
		sb.append("?");
		sb.append("method=newsImg");
		sb.append("&");
		sb.append("modelName=" + sysNewsMain.getClass().getName());
		sb.append("&");
		sb.append("fdId=" + sysNewsMain.getFdId());
		return sb.toString();
	}
	
}
