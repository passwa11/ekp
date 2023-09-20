package com.landray.kmss.km.smissive.util;

import java.util.Map;

import com.landray.kmss.km.smissive.forms.KmSmissiveMainForm;
import com.landray.kmss.km.smissive.model.KmSmissiveConfig;

/****
 * 获取公文配置工具类
 * 
 */
public class KmSmissiveConfigUtil {

	private static final String TYPE_PUBLIC_STATUS = "30";

	private static final String TYPE_ALL_STATUS = "001011203040";

	private static final String TYPE_OFF = "1";

	private static final String TYPE_PUBLIC = "2";

	private static final String TYPE_ALL = "3";
	
	private static final String LOADTYPE_ALLPAGE = "1";

	private static final String LOADTYPE_PAGING = "2";
	

	/****
	 * 判断是否需要转图片
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String isToImg() throws Exception {
		KmSmissiveConfig config = new KmSmissiveConfig();
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			String showImgDoc = (String) map.get("showImgDoc");
			if (KmSmissiveConfigUtil.TYPE_OFF.equals(showImgDoc)) {
				return Boolean.FALSE.toString();
			} else {
				return Boolean.TRUE.toString();
			}
		} else {
			return Boolean.FALSE.toString();
		}
	}
	
	public static String getLoadType() throws Exception {
		KmSmissiveConfig config = new KmSmissiveConfig();
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			String loadType = (String) map.get("loadType");
			if (KmSmissiveConfigUtil.LOADTYPE_ALLPAGE.equals(loadType)) {
				return "allpage";
			} else if (KmSmissiveConfigUtil.LOADTYPE_PAGING.equals(loadType)) {
				return "paging";
			}
		} else {
			return "allpage";
		}
		return "allpage";
	}

	/***
	 * 判断是否显示图片
	 * 
	 * @param docStatus
	 * @return
	 * @throws Exception
	 */
	public static String isShowImg(KmSmissiveMainForm kmSmissiveMainForm)
			throws Exception {
		KmSmissiveConfig config = new KmSmissiveConfig();
		String docStatus = kmSmissiveMainForm.getDocStatus();
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			String showImgDoc = (String) map.get("showImgDoc");
			if (KmSmissiveConfigUtil.TYPE_OFF.equals(showImgDoc)) {
				return Boolean.FALSE.toString();
			} else if (KmSmissiveConfigUtil.TYPE_PUBLIC.equals(showImgDoc)) {
				if (KmSmissiveConfigUtil.TYPE_PUBLIC_STATUS.equals(docStatus)) {
					return Boolean.TRUE.toString();
				} else {
					return Boolean.FALSE.toString();
				}
			} else if (KmSmissiveConfigUtil.TYPE_ALL.equals(showImgDoc)) {
				if (KmSmissiveConfigUtil.TYPE_ALL_STATUS.indexOf(docStatus) > -1) {
					return Boolean.TRUE.toString();
				} else {
					return Boolean.FALSE.toString();
				}
			}
		} else {
			return Boolean.FALSE.toString();
		}
		return Boolean.TRUE.toString();
	}
}
