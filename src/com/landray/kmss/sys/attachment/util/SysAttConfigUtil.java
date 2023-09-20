package com.landray.kmss.sys.attachment.util;

import java.util.HashMap;
import java.util.Map;

import com.landray.kmss.sys.appconfig.model.BaseAppconfigCache;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil;
import com.landray.kmss.sys.attachment.model.SysAttConfig;
import com.landray.kmss.sys.filestore.service.ISysFileConvertConfigService;
import com.landray.kmss.sys.attachment.model.SysAttSignatureConfig;
import com.landray.kmss.util.SpringBeanUtil;
import org.apache.commons.lang3.StringUtils;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SysAttConfigUtil {
	private static final Logger logger = LoggerFactory.getLogger(SysAttConfigUtil.class);

	private static ISysFileConvertConfigService sysFileConvertConfigService;

	/***
	 * 预览编辑工具PC端
	 * 

	 * @return
	 * @throws Exception
	 */
	public static String getOnlineToolType() throws Exception {
		SysAttConfig config = new SysAttConfig();
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			String onlineToolType = (String) map.getOrDefault("onlineToolType", "0");
			return onlineToolType;
		} else {
			return "0";
		}
	}
	
	/***
	 * 预览编辑工具移动端
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getMobileOnlineToolType() throws Exception {
		
		SysAttConfig config = new SysAttConfig();
		Map map = config.getDataMap();
		String pc=SysAttConfigUtil.getOnlineToolType();//PC端预览方式
		String result="";
		
		switch(pc) {
			case SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_JG:
			{
				result=(String) map.get("onlineMobileToolTypeFirst");
				break;
			}
			case SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSCLOUD:
			{
				result=(String) map.get("onlineMobileToolTypeThird");
				break;
			}
			case SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSWPSOAASSIST:
			{
				result=(String) map.get("onlineMobileToolTypeSecond");
				break;
			}
			case SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSCENTER :
			{
				result=(String) map.get("onlineMobileToolTypeFourth");
				break;
			}
			default:
		}
		return result;
	}
	
	/***
	 * 协同写作工具
	 * 
	 * @return
	 * @throws Exception
	 */
	public static boolean isWritingByWps() throws Exception {
		boolean isWps = false;
		Map map = getCowritingPluginConfigs();
		if (!map.isEmpty()) {
			String writingTool = (String) map.getOrDefault("kms.cowriting.wordPlugin.writingtool", "wps");
			if ("wps".equals(writingTool)) {
				isWps = true;
			}
		}
		return isWps;
	}

    /**
     * 获取 CowritingPluginConfig 配置项
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getCowritingPluginConfigs() throws  Exception {
        ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.
                getBean("sysAppConfigService");
        Map<String, Object> map = sysAppConfigService.findByKey("com.landray.kmss.kms.cowriting.config.CowritingPluginConfig");
        return map;
    }

	/***
	 * 判断是否显示金格进度条
	 *
	 * @return
	 * @throws Exception
	 */
	public static String isShowWindow() throws Exception {
		SysAttConfig config = new SysAttConfig();
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			String showWindow = (String) map.get("showWindow");
			return showWindow;
		} else {
			return "1";
		}
	}
	
	/***
	 * 判断是否显示金格进度条
	 *
	 * @return
	 * @throws Exception
	 */
	public static String isReadPdf() throws Exception {
		SysAttConfig config = new SysAttConfig();
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			String readpdf = (String) map.get("readpdf");
			return readpdf;
		} else {
			return "1";
		}
	}
	
	/**
	 * 在线预览（移动端）
	 * 前提PC：金格控件
	 * 其次Mobile：KK+WPS移动版(0)     WPS移动版(1)   WPS云文档（自带WebOffice）(2) WPS中台(4)
	 * @return
	 * @throws Exception
	 */
	public static String isReadJGForMobile() throws Exception
	{
		SysAttConfig config = new SysAttConfig();
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			String readpdf = (String) map.get("onlineMobileToolTypeFirst");
			return readpdf;
		} else {
			return "3";
		}
	}
	
	/**
	 * 在线预览（移动端）
	 * 前提PC：WPS加载项(仅PC)
	 * 其次Mobile：WPS移动版(1)   WPS云文档（自带WebOffice）(2) WPS中台(4)
	 * @return
	 * @throws Exception
	 */
	public static String isReadWPSForMobile() throws Exception
	{
		SysAttConfig config = new SysAttConfig();
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			String readpdf = (String) map.get("onlineMobileToolTypeSecond");
			return readpdf;
		} else {
			return "1";
		}
	}
	
	/**
	 * 在线预览（移动端）
	 * 前提PC：WPS云文档
	 * 其次Mobile：WPS云文档（自带WebOffice）(2)   WPS移动版(1)  WPS中台(4)
	 * @return
	 * @throws Exception
	 */
	public static String isReadWPSCloudForMobile() throws Exception
	{
		SysAttConfig config = new SysAttConfig();
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			String readpdf = (String) map.get("onlineMobileToolTypeThird");
			return readpdf;
		} else {
			return "2";
		}
	}

	/**
	 * 在线预览（移动端）
	 * 前提PC：WPS中台
	 * 其次Mobile：WPS云文档（自带WebOffice）(2)   WPS移动版(1)  WPS中台(4)
	 * @return
	 * @throws Exception
	 */
	public static String isReadWPSCenterForMobile() throws Exception {
		SysAttConfig config = new SysAttConfig();
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			String readpdf = (String) map.get("onlineMobileToolTypeFourth");
			return readpdf;
		} else {
			return "2";
		}
	}

	/**
	 * 获取配置信息
	 * @param configName
	 * @return
	 * @throws Exception
	 */
	public static String getConfigInfo(String configName) throws Exception {
		SysAttConfig config = new SysAttConfig();
		Map map = config.getDataMap();
		String onlineToolType = "";
		if (!map.isEmpty()) {
			onlineToolType = (String) map.get(configName);
		} 
		return onlineToolType;
	}

	/**
	 * PDF预览编辑器使用福昕(PC端)
	 * @return
	 * @throws Exception
	 */
	public static boolean pdfReadByFoxitPC() throws Exception {
		SysAttConfig config = new SysAttConfig();
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			String readpdf = (String) map.get("foxitPCEnable");
			if("true".equals(readpdf)) {
				return true;
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * PDF预览编辑器使用福昕(移端)
	 * @return
	 * @throws Exception
	 */
	public static boolean pdfReadByFoxitMobile() throws Exception {
		SysAttConfig config = new SysAttConfig();
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			String readpdf = (String) map.get("foxitMobileEnable");
			if("true".equals(readpdf)) {
				return true;
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * PDF预览使用浏览器直接打开
	 * @return
	 * @throws Exception
	 */
	public static boolean pdfViewByBrowser() throws Exception {
		SysAttConfig config = new SysAttConfig();
		Map map = config.getDataMap();
        if(map.isEmpty()) {
        	return false;
		}

		String pdfViewBrowser = (String) map.get("pdfViewBrowser");
		if("true".equals(pdfViewBrowser)) {
			return true;
		}

		return false;

	}

	/**
	 * OFD预览使用浏览器直接打开
	 * @return
	 * @throws Exception
	 */
	public static boolean ofdViewByBrowser() throws Exception {
		SysAttConfig config = new SysAttConfig();
		Map map = config.getDataMap();
		if(map.isEmpty()) {
			return false;
		}

		String pdfViewBrowser = (String) map.get("ofdViewBrowser");
		if("true".equals(pdfViewBrowser)) {
			return true;
		}

		return false;

	}
	
	/***
	 * 是否强制使用在线预览阅读
	 *
	 * @return
	 * @throws Exception
	 */
	public static String useWpsLinuxView() throws Exception {
		SysAttConfig config = new SysAttConfig();
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			String useView = (String) map.get("useWpsLinuxView");
			boolean wpsPreviewIsLinux = SysAttWpsCloudUtil.checkWpsPreviewIsLinux();
			String onlineToolType = getOnlineToolType();
			if (SysAttConstant.TYPE_ON.equals(useView) && wpsPreviewIsLinux 
				&& (SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_JG.equals(onlineToolType)||SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSWPSOAASSIST.equals(onlineToolType))) {
				return Boolean.TRUE.toString();
			}else {
				return Boolean.FALSE.toString();
			}
		} else {
			return Boolean.FALSE.toString();
		}
	}

	public static Boolean isEnbaleDianju() {
		Map<String, String> dataMap = BaseAppconfigCache.getCacheData("com.landray.kmss.third.dianju.model.ThirdDianjuConfig");
		String config = "";
		if (!dataMap.isEmpty()) {
			config = (String) dataMap.get("thirdDianjuEnabled");
		}

		if(logger.isDebugEnabled()) {
			logger.debug("获取配置属性名：thirdDianjuEnabled,属性值：{}", config);
		}

		if ("true".equals(config)) {
			return true;
		}

		return false;
	}

	/**
	 * 获取在线预览配置（-1:未选择 1:ASPOSE 2:WPS在线预览 3:WPS云文档预览 4:点聚轻阅读 5:WPS文档中台预览）
	 * @return
	 * @throws Exception
	 */
	public static String getReadOLConfig() throws Exception {
		String v = new SysAttConfig().getDataMap().get("readOLConfig");
		if (StringUtils.isEmpty(v)) {
			v = "-1";
		}
		return v;
	}

	/**
	 * 是否启用aspose转换服务
	 * @return
	 */
	public static boolean isASPOSEEnabled() {
		if (sysFileConvertConfigService == null) {
			sysFileConvertConfigService = (ISysFileConvertConfigService) SpringBeanUtil.getBean("sysFileConvertConfigService");
		}
		return "true".equals(sysFileConvertConfigService.getGlobalConfigForm().getConverter_aspose());
	}

	/**
	 * 是否启用点聚轻阅读
	 * @return
	 */
	public static boolean isDianJuOLEnabled() {
		HashMap<String, String> map = new HashMap<>(BaseAppconfigCache.getCacheData("com.landray.kmss.third.dianju.model.ThirdDianjuReadOLConfig"));
		if (map == null || map.isEmpty()) {
			return false;
		}
		return StringUtils.equals("true",map.get("thirdDianjuReadOLEnabled"));
	}

	/**
	 * 是否启用wps在线预览
	 * @return
	 */
	public static boolean isWpsPreviewEnabled() {
		HashMap<String, String> map = new HashMap<>(BaseAppconfigCache.getCacheData("com.landray.kmss.third.wps.model.ThirdWpsConfig"));
		if (map == null || map.isEmpty()) {
			return false;
		}
		return StringUtils.equals("true",map.get("thirdWpsPreviewEnabled"));
	}

	public static boolean isWpsCenterEnabled() {
		HashMap<String, String> map = new HashMap<>(BaseAppconfigCache.getCacheData("com.landray.kmss.third.wps.model.ThirdWpsConfig"));
		if (map == null || map.isEmpty()) {
			return false;
		}
		return StringUtils.equals("true",map.get("thirdWpsCenterEnabled"));
	}

	/**
	 * 是否启用福昕轻阅读
	 * @return
	 */
	public static boolean isFoxitPreviewEnabled() {

		Map<String, String> dataMap = BaseAppconfigCache.getCacheData("com.landray.kmss.third.foxit.model.ThirdFoxitPreviewConfig");
		if(dataMap == null || dataMap.isEmpty()) {
			return false;
		}

		return StringUtils.equals("true",dataMap.get("thirdFoxitPreviewEnabled"));
	}
	/**
	 * 是否开启签章
	 * @return true: 是， false:否
	 * @throws Exception
	 */
	public static Boolean isEnableAttachmentSignature() throws Exception {
		SysAttSignatureConfig config = new SysAttSignatureConfig();
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			String enableAttachmentSignature = (String) map.get("enableAttachmentSignature");
			if("1".equals(enableAttachmentSignature)) {
				return true;
			}
		}

		return false;
	}

	/**
	 *  获取签章类型
	 * @return 1:金格签章  2：点聚签章
	 * @throws Exception
	 */
	public static String getAttachmentSignatureType() throws Exception {
		SysAttSignatureConfig config = new SysAttSignatureConfig();
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			String attachmentSignatureType = (String) map.get("attachementSignatureType");
			if (StringUtil.isNotNull(attachmentSignatureType)) {
				return attachmentSignatureType;
			}

		}
		return "";
	}
}
