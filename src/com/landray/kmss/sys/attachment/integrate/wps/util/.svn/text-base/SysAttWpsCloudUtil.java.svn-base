package com.landray.kmss.sys.attachment.integrate.wps.util;

import java.util.List;
import java.util.Map;

import com.landray.kmss.sys.appconfig.model.BaseAppconfigCache;
import com.landray.kmss.sys.attachment.integrate.wps.SysAttWpsWebOfficePlugin;
import com.landray.kmss.sys.attachment.integrate.wps.interfaces.ISysAttachmentWpsCloudOfficeProvider;
import com.landray.kmss.sys.attachment.integrate.wps.interfaces.ISysAttachmentWpsWebOfficeProvider;
import com.landray.kmss.sys.attachment.util.SysAttConfigUtil;
import com.landray.kmss.sys.attachment.util.SysAttConstant;
import com.landray.kmss.sys.filestore.scheduler.third.wps.WpsUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class SysAttWpsCloudUtil {

	public static Boolean isEnable() throws Exception {
		Boolean flag = false;
		List<SysAttWpsWebOfficePlugin> plugins = SysAttWpsWebOfficePluginUtil.getExtensionList();
		if (plugins != null && plugins.size() > 0) {
			for (SysAttWpsWebOfficePlugin plugin : plugins) {
				String fdKey = plugin.getKey();
				if (StringUtil.isNotNull(fdKey)
						&& "wpsCloudOffice".equals(fdKey)) {
					ISysAttachmentWpsWebOfficeProvider provider = plugin.getProvider();
					flag = provider.isEnabled();
				}
			}
		}
		return flag && SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSCLOUD.equals(SysAttConfigUtil.getOnlineToolType());
	}
	
	public static Boolean isEnableWpsWebOffice() throws Exception {
		
		return SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSWPSOAASSIST.equals(SysAttConfigUtil.getOnlineToolType());
	}
	
	/**
	 * 移动端编辑云文档处理   WPS云文档(自带WebOffice) 
	 * @param isMobile
	 * @return
	 * @throws Exception true:使用WPS云文档查看和 编辑
	 */
	public static Boolean isEnable(boolean isMobile) throws Exception {
		Boolean flag = false;
		List<SysAttWpsWebOfficePlugin> plugins = SysAttWpsWebOfficePluginUtil.getExtensionList();
		if (plugins != null && plugins.size() > 0) {
			for (SysAttWpsWebOfficePlugin plugin : plugins) {
				String fdKey = plugin.getKey();
				if (StringUtil.isNotNull(fdKey)
						&& "wpsCloudOffice".equals(fdKey)) {
					ISysAttachmentWpsWebOfficeProvider provider = plugin.getProvider();
					flag = provider.isEnabled();
				}
			}
		}
		
		Boolean wpsCloud = false; //是否支持移动端去查看文档
		//PC：金格，移动：WPS云文档(自带WebOffice)
		if(isMobile && SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_JG.equals(SysAttConfigUtil.getOnlineToolType())
				&& "2".equals(SysAttConfigUtil.isReadJGForMobile()))
		{
			wpsCloud = true;
		}
		//PC：WPS加载项   移动：WPS云文档(自带WebOffice)
		else if(isMobile && SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSWPSOAASSIST.equals(SysAttConfigUtil.getOnlineToolType())
				&& "2".equals(SysAttConfigUtil.isReadWPSForMobile()))
		{
			wpsCloud = true;
		}
		//PC：WPS中台   移动：WPS云文档(自带WebOffice)
		else if(isMobile && SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSCENTER.equals(SysAttConfigUtil.getOnlineToolType())
				&& "2".equals(SysAttConfigUtil.isReadWPSForMobile()))
		{
			wpsCloud = true;
		}
		//PC和移动都是：WPS云文档(自带WebOffice)
		else if(isMobile && SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSCLOUD.equals(SysAttConfigUtil.getOnlineToolType())
				&& "2".equals(SysAttConfigUtil.isReadWPSCloudForMobile()))
		{
			wpsCloud = true;
		}
		return flag && wpsCloud;
	//	return flag
		//		&& SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSCLOUD.equals(SysAttConfigUtil.getOnlineToolType());
	}
	
	/**
	 * WPS移动版
	 * @param isMobile
	 * @return
	 * @throws Exception true:WPS移动版查看和 编辑
	 */
	public static Boolean isEnableMobile(boolean isMobile) throws Exception {
		
		Boolean wpsCloud = false; //是否支持移动端去查看文档
		//PC：金格，移动：WPS移动版
		if(isMobile && SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_JG.equals(SysAttConfigUtil.getOnlineToolType())
				&& "1".equals(SysAttConfigUtil.isReadJGForMobile()))
		{
			wpsCloud = true;
		}
		//PC：WPS加载项  移动是： WPS移动版
		else if(isMobile && SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSWPSOAASSIST.equals(SysAttConfigUtil.getOnlineToolType())
				&& "1".equals(SysAttConfigUtil.isReadWPSForMobile()))
		{
			wpsCloud = true;
		}
		//PC：WPS中台  移动是： WPS移动版
		else if(isMobile && SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSCENTER.equals(SysAttConfigUtil.getOnlineToolType())
				&& "1".equals(SysAttConfigUtil.isReadWPSCenterForMobile()))
		{
			wpsCloud = true;
		}
		//PC:WPS云文档  移动是：WPS移动版
		else if(isMobile && SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSCLOUD.equals(SysAttConfigUtil.getOnlineToolType())
				&& "1".equals(SysAttConfigUtil.isReadWPSCloudForMobile()))
		{
			wpsCloud = true;
		}
		return wpsCloud;
	}



	//是否开启KK的移动端
	public static Boolean isKKMobileEnabel() throws Exception
	{
		Boolean kkMobile = false;
		if(SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_JG.equals(SysAttConfigUtil.getOnlineToolType())
				&& ("0".equals(SysAttConfigUtil.isReadJGForMobile())))
		{
			kkMobile = true;
		}
		return kkMobile;
	}

	public static String getUrl() throws Exception {

		List<SysAttWpsWebOfficePlugin> plugins = SysAttWpsWebOfficePluginUtil.getExtensionList();
		if (plugins != null && plugins.size() > 0) {
			for (SysAttWpsWebOfficePlugin plugin : plugins) {
				String fdKey = plugin.getKey();
				if (StringUtil.isNotNull(fdKey)
						&& "wpsCloudOffice".equals(fdKey)) {
					ISysAttachmentWpsWebOfficeProvider provider = plugin.getProvider();
					if (StringUtil.isNotNull(provider.getUrl())) {
						String Url = provider.getUrl();
						if (Url.endsWith("/")) {
							Url = Url.substring(0, Url.length() - 1);
						}
						return Url;
					}
				}
			}
		}

		return null;
	}
	
	public static String getAppid() throws Exception {
		List<SysAttWpsWebOfficePlugin> plugins = SysAttWpsWebOfficePluginUtil.getExtensionList();
		if (plugins != null && plugins.size() > 0) {
			for (SysAttWpsWebOfficePlugin plugin : plugins) {
				String fdKey = plugin.getKey();
				if (StringUtil.isNotNull(fdKey)
						&& "wpsCloudOffice".equals(fdKey)) {
					ISysAttachmentWpsWebOfficeProvider provider = plugin.getProvider();
					return provider.getAppid();
				}
			}
		}
		return null;
	}

	public static String getAppkey() throws Exception {
		List<SysAttWpsWebOfficePlugin> plugins = SysAttWpsWebOfficePluginUtil.getExtensionList();
		if (plugins != null && plugins.size() > 0) {
			for (SysAttWpsWebOfficePlugin plugin : plugins) {
				String fdKey = plugin.getKey();
				if (StringUtil.isNotNull(fdKey)
						&& "wpsCloudOffice".equals(fdKey)) {
					ISysAttachmentWpsWebOfficeProvider provider = plugin.getProvider();
					return provider.getAppkey();
				}
			}
		}
		return null;
	}
	
	/**
	 * 检测附件是否进行过同步
	 * @param fdFileId
	 * @return
	 * @throws Exception
	 */
	public static Boolean isAttHadSyncByAttMainId(String fdFileId)
			throws Exception {
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider)SpringBeanUtil.getBean("wpsCloudProvider");
		if(wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.isAttHadSyncByAttMainId(fdFileId);
		}
		return false;
	}
	
	/**
	 * 进行文件同步增加，文件同步到云文档
	 * @param fdAttMainId
	 * @return
	 * @throws Exception
	 */
	public static void syncAttToAddByMainId(String fdAttMainId) throws Exception {
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider)SpringBeanUtil.getBean("wpsCloudProvider");
		if(wpsCloudOfficeProvider != null) {
			wpsCloudOfficeProvider.syncAttToAddByMainId(fdAttMainId);
		}
	}
	
	/**
	 * 进行文件同步更新
	 * @param fdMainId
	 * @throws Exception
	 */
	public static void  syncAttToUpdateByMainId(String fdMainId) throws Exception {
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider)SpringBeanUtil.getBean("wpsCloudProvider");
		if(wpsCloudOfficeProvider != null) {
			wpsCloudOfficeProvider.syncAttToUpdateByMainId(fdMainId);
		}
	}

	/**
	 * 进行文件同步更新,云文档同步下来
	 * 
	 * @param fdMainId
	 * @throws Exception
	 */
	public static void updateAttByMainId(String fdMainId) throws Exception {
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			wpsCloudOfficeProvider.updateAttByMainId(fdMainId);
		}
	}

	public static JSONObject getWpsCloudViewUrl(String fdAttMainId,
			boolean canEdit, String history) throws Exception {
		// TODO Auto-generated method stub
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.getWpsCloudViewUrl(fdAttMainId,
					canEdit, history);
		} else {
			return null;
		}
	}
	
	public static JSONObject getWpsCloudViewUrl(String fdAttMainId,
			boolean canEdit, String history,boolean contentFlag) throws Exception {
		// TODO Auto-generated method stub
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.getWpsCloudViewUrl(fdAttMainId,
					canEdit, history,contentFlag);
		} else {
			return null;
		}
	}
	
	
	public static JSONObject getWpsCloudViewParam(String fdAttMainId,String mode) throws Exception {
		// TODO Auto-generated method stub
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.getWpsCloudViewParam(fdAttMainId,mode);
		} else {
			return null;
		}
	}

	/**
	 * 查询结果
	 * 
	 * @param taskID
	 * @return
	 * @throws Exception
	 */
	public static String getResult(String taskID) throws Exception {
		// TODO Auto-generated method stub
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.getResult(taskID);
		} else {
			return null;
		}
	}

	/**
	 * 清稿
	 * 
	 * @param downurl
	 *            清稿文档下载地址
	 * @param fileId
	 *            文件ID
	 * @param ext
	 *            文件扩展名
	 * @return
	 * @throws Exception
	 */
	public static String opRevisions(String downurl, String fileId, String ext)
			throws Exception {
		// TODO Auto-generated method stub
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.opRevisions(downurl, fileId, ext);
		} else {
			return null;
		}
	}

	/**
	 * 清稿
	 * 
	 * @param downurl
	 *            清稿文档下载地址
	 * @param fileId
	 *            文件ID
	 * @param ext
	 *            文件扩展名
	 * @return
	 * @throws Exception
	 */
	public static String opRevisions(String downurl, String fileId, String ext,
			String fdModelId, String fdModelName)
			throws Exception {
		// TODO Auto-generated method stub
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.opRevisions(downurl, fileId, ext,
					fdModelId, fdModelName);
		} else {
			return null;
		}
	}


	/**
	 * 清稿
	 * 
	 * @param downurl
	 *            清稿文档下载地址
	 * @param fileId
	 *            文件ID
	 * @param ext
	 *            文件扩展名
	 * @param fdModelId
	 *            主文档ID
	 * @param fdModelName 主文档
	 * @param fdAttMainId 附件Id
	 * @return
	 * @throws Exception
	 */
	public static String opRevisions(String downurl, String fileId, String ext,
			String fdModelId, String fdModelName, String fdAttMainId,
			String type)
			throws Exception {
		// TODO Auto-generated method stub
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.opRevisions(downurl, fileId, ext,
					fdModelId, fdModelName, fdAttMainId, type);
		} else {
			return null;
		}
	}

	public static String opRevisions(String downurl, String fileId, String ext,
			String fdModelId, String fdModelName, String fdAttMainId)
			throws Exception {
		// TODO Auto-generated method stub
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.opRevisions(downurl, fileId, ext,
					fdModelId, fdModelName, fdAttMainId, "1");
		} else {
			return null;
		}
	}


	/**
	 * 套红
	 * 
	 * @param fileInfos
	 * @param fillDatas
	 * @return
	 * @throws Exception
	 *             List<Map<String, Object>> fileInfos = new ArrayList<>();
	 *             fileInfo.put("location", downurl);downurl：套红模板下载地址
	 *             fileInfo.put("fileID", fileID);fileID：文件ID
	 *             fileInfo.put("ext", "docx");文件后缀 fileInfo.put("sourceType",
	 *             1);1-http文件下载地址（Content-Type：application/json）
	 *             fileInfo.put("args", new HashMap<>());
	 *             fileInfos.add(fileInfo); List<Map<String, Object>> fillDatas
	 *             = new ArrayList<>(); Map<String, Object> fillData = new
	 *             HashMap<>(); fillData.put("bookmark", "Telephone");书签名称
	 *             fillData.put("content", "AAAAAAABBBB");填充内容
	 *             fillData.put("type", 0);填充类型，0-文字，1-文档 fillData.put("ext",
	 *             "docx");type=1时有效，为文件的后缀 fillDatas.add(fillData);
	 */
	public static String setRed(List<Map<String, Object>> fileInfos,
			List<Map<String, Object>> fillDatas) throws Exception {
		// TODO Auto-generated method stub
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.setRed(fileInfos, fillDatas);
		} else {
			return null;
		}
	}

	/**
	 * 套红
	 * 
	 * @param fileInfos
	 * @param fillDatas
	 * @return
	 * @throws Exception
	 *             List<Map<String, Object>> fileInfos = new ArrayList<>();
	 *             fileInfo.put("location", downurl);downurl：套红模板下载地址
	 *             fileInfo.put("fileID", fileID);fileID：文件ID
	 *             fileInfo.put("ext", "docx");文件后缀 fileInfo.put("sourceType",
	 *             1);1-http文件下载地址（Content-Type：application/json）
	 *             fileInfo.put("args", new HashMap<>());
	 *             fileInfos.add(fileInfo); List<Map<String, Object>> fillDatas
	 *             = new ArrayList<>(); Map<String, Object> fillData = new
	 *             HashMap<>(); fillData.put("bookmark", "Telephone");书签名称
	 *             fillData.put("content", "AAAAAAABBBB");填充内容
	 *             fillData.put("type", 0);填充类型，0-文字，1-文档 fillData.put("ext",
	 *             "docx");type=1时有效，为文件的后缀 fillDatas.add(fillData);
	 */
	public static String setRed(List<Map<String, Object>> fileInfos,
			List<Map<String, Object>> fillDatas, String fdModelId,
			String fdModelName) throws Exception {
		// TODO Auto-generated method stub
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.setRed(fileInfos, fillDatas,
					fdModelId, fdModelName);
		} else {
			return null;
		}
	}

	public static String setRed(List<Map<String, Object>> fileInfos,
			List<Map<String, Object>> fillDatas, String fdModelId,
			String fdModelName, String fdAttMainId, String type)
			throws Exception {
		// TODO Auto-generated method stub
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.setRed(fileInfos, fillDatas,
					fdModelId, fdModelName, fdAttMainId, type);
		} else {
			return null;
		}
	}

	public static String setRed(List<Map<String, Object>> fileInfos,
			List<Map<String, Object>> fillDatas, String fdModelId,
			String fdModelName, String fdAttMainId)
			throws Exception {
		// TODO Auto-generated method stub
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.setRed(fileInfos, fillDatas,
					fdModelId, fdModelName, fdAttMainId, "2");
		} else {
			return null;
		}
	}

	//表单值映射回word
	//linux在线预览使用
	public static String setFormMappingWord(List<Map<String, Object>> fileInfos,
								List<Map<String, Object>> fillDatas, String fdModelId,
								String fdModelName, String fdAttMainId,String fileName)
			throws Exception {
		// TODO Auto-generated method stub
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.setFormMappingWord(fileInfos, fillDatas,
					fdModelId, fdModelName, fdAttMainId,fileName, "3");
		} else {
			return null;
		}
	}


	public static JSONObject getCovertDownload(String fdModelId,
			String fdModelName, String type) throws Exception {
		// TODO Auto-generated method stub
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.getCovertDownload(fdModelId,
					fdModelName, type);
		} else {
			return null;
		}
	}

	public static JSONObject getCovertDownload(String taskID) throws Exception {
		// TODO Auto-generated method stub
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.getCovertDownload(taskID);
		} else {
			return null;
		}
	}

	/**
	 * 获取wps下载地址
	 * 
	 * @param fdMainId
	 * @return
	 * @throws Exception
	 */
	public static String getWpsDownloadUrl(String fdMainId) throws Exception {
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.getWpsDownloadUrl(fdMainId);
		} else {
			return null;
		}
	}

	/**
	 * 获取wps修改人fdId
	 * 
	 * @param fdMainId
	 * @return
	 * @throws Exception
	 */
	public static String getUpdateSysOrgPersonFdId(String fdMainId)
			throws Exception {
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.getUpdateSysOrgPersonFdId(fdMainId);
		} else {
			return null;
		}
	}

	/**
	 * 判断云文档wps文件与本地是否一致
	 * 
	 * @param fdMainId
	 * @return
	 * @throws Exception
	 */
	public static Boolean checkWpsVersion(String fdMainId)
			throws Exception {
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.checkWpsVersion(fdMainId);
		} else {
			return null;
		}
	}

	/**
	 * 判断wps在线预览是否是windows
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Boolean checkWpsPreviewIsWindows()
			throws Exception {
		String isOn = WpsUtil.configInfo("thirdWpsPreviewEnabled");
		if (StringUtil.isNotNull(isOn) && "true".equals(isOn)) {
			String thirdWpsOS = WpsUtil.configInfo("thirdWpsOS");
			if (StringUtil.isNotNull(thirdWpsOS)
					&& "windows".equals(thirdWpsOS)) {
                return true;
            }
		}
		return false;

	}

	/**
	 * 判断wps在线预览是否是linux
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Boolean checkWpsPreviewIsLinux()
			throws Exception {
		String isOn = WpsUtil.configInfo("thirdWpsPreviewEnabled");
		if (StringUtil.isNotNull(isOn) && "true".equals(isOn)) {
			String thirdWpsOS = WpsUtil.configInfo("thirdWpsOS");
			if (StringUtil.isNotNull(thirdWpsOS) && "linux".equals(thirdWpsOS)) {
                return true;
            }
		}
		return false;

	}

	/**
	 * 根据modelname判断是否开启自动保存
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Boolean checkWpsCloueAutoSaveByModelName(String modeName)
			throws Exception {
		Boolean flag = false;
		Map<String, String> dataMap = BaseAppconfigCache.getCacheData(
				"com.landray.kmss.sys.attachment.model.SysAttConfig");
		String wpsCloudAutosave = "";
		if (!dataMap.isEmpty()&& dataMap.get("wpsCloudAutosave") != null) {
			wpsCloudAutosave = (String) dataMap.get("wpsCloudAutosave");
		}
		if ("1".equals(wpsCloudAutosave)) {
            return true;
        }
		if ("0".equals(wpsCloudAutosave)&& !dataMap.isEmpty()
				&& dataMap.get("wpsCloudAutoSaveExcept") != null) {
			String wpsCloudAutoSaveExcept = (String) dataMap
					.get("wpsCloudAutoSaveExcept");
			String[] exceptArray = wpsCloudAutoSaveExcept.split(";");
			for (int i = 0; i < exceptArray.length; i++) {
				String model = exceptArray[i];
				if (model.startsWith("/")) {
                    model = model.substring(1, model.length());
                }
				if (StringUtil.isNotNull(model)) {
					model = "com.landray.kmss." + model.replace("/", ".");
					if (modeName.startsWith(model)) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;

	}
	
	/**
	 * 获取window版本在线预览地址
	 * 
	 * @param fdMainId
	 * @return
	 * @throws Exception
	 */
	public static String getWpsWindowPreviewUrl(String fdMainId)
			throws Exception {
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.getWpsWindowPreviewUrl(fdMainId);
		} else {
			return null;
		}
	}
	/**
	 * 获取linux版本在线预览地址
	 * 
	 * @param fdMainId
	 * @return
	 * @throws Exception
	 */
	public static String getWpsLinuxPreviewUrl(String fdMainId)
			throws Exception {
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.getWpsLinuxPreviewUrl(fdMainId);
		} else {
			return null;
		}
	}
	
	/**
	 * 获取wps云文档下载地址
	 * 业务模块的一些特殊场景，不受附件机制的自动保存配置项控制
	 * @param fdMainId
	 * @return
	 * @throws Exception
	 */
	public static String getWpsOnlineDownloadUrl(String fdMainId) throws Exception {
		ISysAttachmentWpsCloudOfficeProvider wpsCloudOfficeProvider = (ISysAttachmentWpsCloudOfficeProvider) SpringBeanUtil
				.getBean("wpsCloudProvider");
		if (wpsCloudOfficeProvider != null) {
			return wpsCloudOfficeProvider.getWpsOnlineDownloadUrl(fdMainId);
		} else {
			return null;
		}
	}
	
	/***
	 * 是否强制使用在线预览阅读
	 *
	 * @return
	 * @throws Exception
	 */
	public static Boolean getUseWpsLinuxView() throws Exception {
		String readOlCOnfig = SysAttConfigUtil.getReadOLConfig();
		if("2".equals(readOlCOnfig) || "3".equals(readOlCOnfig) || "4".equals(readOlCOnfig) || "5".equals(readOlCOnfig) || "6".equals(readOlCOnfig)){
			return new Boolean(true);
		}
		return new Boolean(false);
	}
}
