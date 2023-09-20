package com.landray.kmss.km.review.util;

import com.landray.kmss.km.review.model.KmReviewConfigNotify;
import com.landray.kmss.km.review.service.IKmReviewTemplateService;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * 创建日期 2010-七月-20
 * 
 * @author zhuangwl
 */
public class KmReviewUtil {

	/**
	 * 将“aaa/r/nbbb/r/nccc”的字符串转换为SQL使用的“'aaa','bbb','ccc' ”
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceToSQLString(String str) {
		if (str == null) {
            return null;
        }
		String rtnVal = str.trim();
		if (rtnVal.length() == 0) {
            return str;
        }
		rtnVal = rtnVal.replaceAll("\r\n", "','");
		return "'" + rtnVal + "'";
	}

	/**
	 * 得到当前路径
	 * 
	 * @param sysCategoryMain
	 * @param sPath
	 * @return
	 */
	public static String getSPath(SysCategoryMain sysCategoryMain, String sPath) {
		sPath = StringUtil.linkString(sysCategoryMain.getFdName(), ">>", sPath);
		if (sysCategoryMain.getFdParent() != null) {
            sPath = getSPath((SysCategoryMain) sysCategoryMain.getFdParent(),
                    sPath);
        } else {
            sPath = StringUtil.linkString("按类别", ">>", sPath);
        }
		return sPath;
	}

	/**
	 * 分类下的模版转移（用于钉钉高级审批）
	 * 
	 * @param oldCategoryId
	 * @param newCategoryId
	 * @return
	 */
	public static boolean moveTemplate2OtherCategory(String oldCategoryId,
			String newCategoryId) {
		try {
			IKmReviewTemplateService kmReviewTemplateService = (IKmReviewTemplateService) SpringBeanUtil
					.getBean("kmReviewTemplateService");
			return kmReviewTemplateService
					.updateTemplate2OtherCategory(oldCategoryId, newCategoryId);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static JSONObject getEnableModule() {
		JSONObject json = new JSONObject();
		try {
			KmReviewConfigNotify config = new KmReviewConfigNotify();
			json.put("enableSysRelation", config.getEnableSysRelation());
			json.put("enableSysAgenda", config.getEnableSysAgenda());
			json.put("enableSysPrint", config.getEnableSysPrint());
			json.put("enableKmArchives", config.getEnableKmArchives());
			json.put("enableKmsMultidoc", config.getEnableKmsMultidoc());
			json.put("enableSysRule", config.getEnableSysRule());
			json.put("enableSysRemind", config.getEnableSysRemind());
			json.put("enableSysIassister", config.getEnableSysIassister());
			json.put("enableKmCollaborate", config.getEnableKmCollaborate());
			json.put("enableKmSupervise", config.getEnableKmSupervise());
			json.put("enableSysCirculation", config.getEnableSysCirculation());
			json.put("enableSysBookmark", config.getEnableSysBookmark());
			json.put("enableSysReadlog", config.getEnableSysReadlog());
			json.put("enableDbcenterEcharts",
					config.getEnableDbcenterEcharts());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
