package com.landray.kmss.sys.attachment.integrate.wps.util;

import com.landray.kmss.sys.appconfig.model.BaseAppconfigCache;
import com.landray.kmss.sys.attachment.integrate.wps.interfaces.ISysAttachmentWpsCenterOfficeProvider;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.util.SysAttConfigUtil;
import com.landray.kmss.sys.attachment.util.SysAttConstant;
import com.landray.kmss.sys.filestore.scheduler.third.wps.WpsUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONObject;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class SysAttWpsCenterUtil {

	/**
	 * 保存wpstoken
	 *
	 * @param wpsToken
	 * @return
	 * @throws Exception
	 */
	public static void saveWpsToken(String wpsToken)
			throws Exception {

		ISysAttachmentWpsCenterOfficeProvider wpsCenterProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
				.getBean("wpsCenterProvider");
		if (wpsCenterProvider != null) {
			wpsCenterProvider.saveWpsToken(wpsToken);
		}
	}

	/**
	 * 获取保存好的wpstoken
	 *
	 * @return
	 */
	public static String getWpsToken() throws Exception {
		ISysAttachmentWpsCenterOfficeProvider wpsCenterProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
				.getBean("wpsCenterProvider");
		if (wpsCenterProvider != null) {
			return wpsCenterProvider.getWpsToken();
		} else {
			return null;
		}
	}

	public static String getLongCallBackToken() throws Exception {
		ISysAttachmentWpsCenterOfficeProvider wpsCenterProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil.getBean("wpsCenterProvider");
		if (wpsCenterProvider != null) {
			return wpsCenterProvider.getLongCallBackToken(UserUtil.getUser().getFdId(),getWpsToken(),true);
		} else {
			return null;
		}
	}

	public static Long getLongTokenTimeOut(){
		Long timeOut = 1000 * 60 * 60L;
		Map<String, String> dataMap = BaseAppconfigCache.getCacheData("com.landray.kmss.third.wps.model.ThirdWpsConfig");
		if (!dataMap.isEmpty() && StringUtil.isNotNull(dataMap.get("thirdWpsLongTokenTimeOut"))) {
			timeOut = Integer.valueOf(dataMap.get("thirdWpsLongTokenTimeOut")) * 60L * 1000;
		}
		return timeOut;
	}

	public static void setSingure(Map<String, String> header, String shaUrl, Map parameter) throws Exception {
		ISysAttachmentWpsCenterOfficeProvider wpsCenterProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
				.getBean("wpsCenterProvider");
		if (wpsCenterProvider != null) {
			wpsCenterProvider.setSingure(header, shaUrl, parameter);
		}
	}

	public static JSONObject getWpsCenterViewAndEditUrl(String fdAttMainId, String fdMode) throws Exception {
		ISysAttachmentWpsCenterOfficeProvider wpsCenterProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
				.getBean("wpsCenterProvider");
		if (wpsCenterProvider != null) {
			return wpsCenterProvider.getWpsCenterViewAndEditUrl(fdAttMainId, fdMode);
		} else {
			return null;
		}
	}

	public static JSONObject getWpsCenterEditUrl(HttpServletRequest request) throws Exception {
		ISysAttachmentWpsCenterOfficeProvider wpsCenterProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
				.getBean("wpsCenterProvider");
		if (wpsCenterProvider != null) {
			return wpsCenterProvider.getWpsCenterEditUrl(request);
		} else {
			return null;
		}
	}

	/**
	 * 清稿
	 *
	 * @param fileInfos
	 * @param fillDatas
	 * @param fdModelId
	 * @param fdModelName
	 * @param fdAttMainId
	 * @param type
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public static String wpsCenterWrapHeader(List<Map<String, Object>> fileInfos, List<Map<String, Object>> fillDatas,
											 String fdModelId, String fdModelName, String fdAttMainId, String type, String userId) throws Exception {
		ISysAttachmentWpsCenterOfficeProvider wpsCenterProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
				.getBean("wpsCenterProvider");
		if (wpsCenterProvider != null) {
			return wpsCenterProvider.wpsCenterWrapHeader(fileInfos, fillDatas, fdModelId, fdModelName, fdAttMainId, type, userId);
		} else {
			return null;
		}
	}

	/**
	 * 清稿
	 *
	 * @return
	 * @throws Exception
	 */
	public static String wpsCenterOperateClean(SysAttMain sysAttMain, String userId, String type) throws Exception {
		ISysAttachmentWpsCenterOfficeProvider wpsCenterProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
				.getBean("wpsCenterProvider");
		if (wpsCenterProvider != null) {
			return wpsCenterProvider.wpsCenterOperateClean(sysAttMain, userId, type);
		} else {
			return null;
		}
	}

	/**
	 * 在线预览
	 *
	 * @param fdAttMainId
	 * @param fdMode
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getWpsCenterPreviewUrl(String fdAttMainId, String fdMode) throws Exception {
		ISysAttachmentWpsCenterOfficeProvider wpsCenterProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
				.getBean("wpsCenterProvider");
		if (wpsCenterProvider != null) {
			return wpsCenterProvider.getWpsCenterPreviewUrl(fdAttMainId, fdMode);
		} else {
			return null;
		}
	}

	/**
	 * PC端是否使用WPS中台
	 *
	 * @return
	 * @throws Exception
	 */
	public static Boolean isEnable() throws Exception {
		Boolean flag = false;

		if ("4".equals(SysAttConfigUtil.getOnlineToolType())
				&& "true".equals(WpsUtil.configInfo("thirdWpsCenterEnabled"))) {

			flag = true;
		}

		return flag;
	}

	/**
	 * WPS中台移动版
	 *
	 * @param isMobile
	 * @return
	 * @throws Exception true:WPS移动版查看和 编辑
	 */
	public static Boolean isWPSCenterEnableMobile(boolean isMobile) throws Exception {
		Boolean wpsCenter = false; //是否支持移动端去查看文档

		if (isMobile && SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_JG.equals(SysAttConfigUtil.getOnlineToolType())
				&& "4".equals(SysAttConfigUtil.isReadJGForMobile())) { //PC：金格，移动：WPS中台移动版
			wpsCenter = true;
		} else if (isMobile && SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSWPSOAASSIST.equals(SysAttConfigUtil.getOnlineToolType())
				&& "4".equals(SysAttConfigUtil.isReadWPSForMobile())) { //PC：WPS加载项  移动是： WPS中台移动版
			wpsCenter = true;
		} else if (isMobile && SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSCENTER.equals(SysAttConfigUtil.getOnlineToolType())
				&& "4".equals(SysAttConfigUtil.isReadWPSCenterForMobile())) { //PC：WPS中台  移动是： WPS中台移动版
			wpsCenter = true;
		} else if (isMobile && SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSCLOUD.equals(SysAttConfigUtil.getOnlineToolType())
				&& "4".equals(SysAttConfigUtil.isReadWPSCloudForMobile())) { //PC:WPS云文档  移动是：WPS中台移动版
			wpsCenter = true;
		}

		return wpsCenter;
	}

	/**
	 * WPS中台操作回调后的下载地址
	 *
	 * @param taskID
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getCovertDownload(String taskID) throws Exception {

		ISysAttachmentWpsCenterOfficeProvider wpsCenterProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
				.getBean("wpsCenterProvider");
		if (wpsCenterProvider != null) {
			return wpsCenterProvider.getCovertDownload(taskID);
		} else {
			return null;
		}

	}

	/**
	 * WPS中台操作回调后的下载地址
	 *
	 * @param fdModelId
	 * @param fdModelName
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getCovertDownload(String fdModelId, String fdModelName,
										String type) throws Exception {
		ISysAttachmentWpsCenterOfficeProvider wpsCenterProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
				.getBean("wpsCenterProvider");
		if (wpsCenterProvider != null) {
			return wpsCenterProvider.getCovertDownload(fdModelId, fdModelName, type);
		} else {
			return null;
		}
	}

	public static InputStream downloadByTaskId(String taskId) throws Exception {
		ISysAttachmentWpsCenterOfficeProvider wpsCenterProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
				.getBean("wpsCenterProvider");
		if (wpsCenterProvider != null) {
			return wpsCenterProvider.downloadByTaskId(taskId);
		} else {
			return null;
		}
	}

	public static String downloadUrlByTaskId(String taskId) throws Exception {
		ISysAttachmentWpsCenterOfficeProvider wpsCenterProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
				.getBean("wpsCenterProvider");
		if (wpsCenterProvider != null) {
			return wpsCenterProvider.downloadUrlByTaskId(taskId);
		} else {
			return null;
		}
	}

	public static InputStream downloadByUrl(String wpsUrl) throws Exception {
		ISysAttachmentWpsCenterOfficeProvider wpsCenterProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
				.getBean("wpsCenterProvider");
		if (wpsCenterProvider != null) {
			return wpsCenterProvider.downloadWithUrl(wpsUrl);
		} else {
			return null;
		}
	}

    /**
     * 获取附件下载地址
     *
     * @param fdAttMainId
     * @param userId
     * @return
     * @throws Exception
     */
    public static String getWpsDownloadUrl(String fdAttMainId, String userId) throws Exception {
        ISysAttachmentWpsCenterOfficeProvider wpsCenterProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
                .getBean("wpsCenterProvider");
        if (wpsCenterProvider != null) {
            return wpsCenterProvider.generateDownloadUrl(fdAttMainId,userId);
        } else {
            return null;
        }
    }

}
