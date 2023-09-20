package com.landray.kmss.sys.filestore.util;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.MessageDigest;
import java.util.*;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.filestore.service.ISysFileConvertConfigService;
import org.slf4j.Logger;

import com.landray.kmss.sys.cache.CacheConfig;
import com.landray.kmss.sys.cache.CacheLoader;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.cluster.interfaces.DispatcherCenter;
import com.landray.kmss.sys.cluster.model.SysClusterParameter;
import com.landray.kmss.sys.cluster.model.SysClusterServer;
import com.landray.kmss.sys.cluster.remoting.protocol.Command;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.filestore.model.SysFileConvertConfig;
import com.landray.kmss.sys.filestore.model.SysFileConvertUrlConfig;
import com.landray.kmss.sys.filestore.scheduler.impl.SysFileConvertScheduler;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import edu.emory.mathcs.backport.java.util.Collections;

public class SysFileStoreUtil {

	static {
		CacheConfig config = CacheConfig.get(SysFileStoreUtil.class);
		config.returnNullWhenLoading = false;
		config.setCacheLoader(new CacheLoader() {
			@Override
			public Object load(String key) throws Exception {
				return loadConvertConfigs();
			}
		});
	}

	private static KmssCache configCache = new KmssCache(SysFileStoreUtil.class);

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysFileStoreUtil.class);

	private static ISysFileConvertDataService convertDataService = (ISysFileConvertDataService) SpringBeanUtil
			.getBean("sysFileConvertDataService");

	public static void updateConfigCache() {
		configCache.update("configs");
	}

	private ISysFileConvertConfigService sysFileConvertConfigService;
	public ISysFileConvertConfigService getSysFileConvertConfigService() {
		if(sysFileConvertConfigService == null) {
			sysFileConvertConfigService = (ISysFileConvertConfigService) SpringBeanUtil
					.getBean("sysFileConvertConfigService");
		}

		return sysFileConvertConfigService;
	}

	@SuppressWarnings("unchecked")
	private static List<FileConverter> loadConvertConfigs() throws Exception {
		List<FileConverter> converters = new ArrayList<FileConverter>();
		try {
			List<SysFileConvertConfig> configs = convertDataService.getConfigService().findList("", "");
			FileConverter fileConverter = null;
			for (SysFileConvertConfig config : configs) {
				fileConverter = new FileConverter();
				fileConverter.setConverterKey(config.getFdConverterKey());
				fileConverter.setExtName(config.getFdFileExtName());
				fileConverter.setModelName(config.getFdModelName());
				fileConverter.setDispenser(config.getFdDispenser());
				fileConverter.setFdHighFidelity(config.getFdHighFidelity());
				fileConverter.setConverterType(config.getFdConverterType());
				fileConverter.setPicResolution(config.getFdPicResolution());
				fileConverter.setPicRectangle(config.getFdPicRectangle());
				fileConverter.setEnabled("1".equals(config.getFdStatus()) ? Boolean.TRUE : Boolean.FALSE);
				converters.add(fileConverter);
			}
			return converters;
		} catch (Exception e) {
			logger.error("error", e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<FileConverter> getConvertConfigs() {
		return (List<FileConverter>) configCache.get("configs");
	}

	/**
	 * 根据扩展名和模块名称找到该模块这个文档需要做转换的转换器
	 * 
	 * @param fileExtName
	 * @param fileModelName
	 * @return
	 * @throws Exception
	 */
	public static List<FileConverter> getFileConverters(String fileExtName, String fileModelName,
			boolean showAllConfig) {
		List<FileConverter> converters = new ArrayList<>();
		Set<String> existSet = new HashSet<>();
		if (!isAttConvertEnable() && !showAllConfig) {
			return converters;
		}

		List<FileConverter> confs = getConvertConfigs();
		for (int i = 0; i < confs.size(); i++) {
			FileConverter config = confs.get(i);
			if(config == null) {
				continue;
			}

			String configExtName = config.getExtName();
			String convertType  = StringUtil.isNull(config.getConverterType()) ?
					"aspose" : config.getConverterType();

			 // 含有多个附件扩展名，以 、 隔开
			if (configExtName.indexOf("、") > 0) {
				String[] configExtNames = configExtName.split("、");
				List<String> arrExtNames = new ArrayList<String>();
				Collections.addAll(arrExtNames, configExtNames);
				if (arrExtNames.contains(fileExtName.toLowerCase())
						&& isMatchModelName(config.getModelName(), fileModelName)
						&& (showAllConfig || config.isEnabled())) {
					String key = convertType + "_" + fileExtName.toLowerCase() + "_" + config.getConverterKey();
					if (!existSet.contains(key)) {
						existSet.add(key);
						converters.add(config);
					}
				}

				continue;
			}

			// 单个附件扩展名
			if (config.getExtName().equals(fileExtName.toLowerCase())
					&& isMatchModelName(config.getModelName(), fileModelName)
					&& (showAllConfig || config.isEnabled())) {
				String key = convertType + "_" + fileExtName.toLowerCase() + "_" + config.getConverterKey();
				if (!existSet.contains(key)) {
					existSet.add(key);
					converters.add(config);
				}
			}
				
		}

		if(logger.isDebugEnabled()) {
			logger.debug("获取到的扩展名信息：{}", JSONObject.toJSONString(converters));
		}

		return converters;
	}

	public static List<FileConverter> getFileConverters(String fileExtName, String fileModelName) {
		return getFileConverters(fileExtName, fileModelName, false);
	}

	private static boolean isMatchModelName(String config, String modelName) {
		boolean isMatch = false;
		if(config.contains("、"))
		{
			String[] suffix = config.split("、");
			for(String suf : suffix)
			{
				suf = suf.replaceAll("\\.", "\\\\.");
				suf = suf.replaceAll("\\*", "\\.\\*");
				modelName = StringUtil.isNotNull(modelName) ? modelName : "";
				Pattern pattern = Pattern.compile(suf, Pattern.CASE_INSENSITIVE);
				isMatch = pattern.matcher(modelName).find();
				if(isMatch) {
                    break;
                }
			}
			
		}else
		{
			config = config.replaceAll("\\.", "\\\\.");
			config = config.replaceAll("\\*", "\\.\\*");
			modelName = StringUtil.isNotNull(modelName) ? modelName : "";
			Pattern pattern = Pattern.compile(config, Pattern.CASE_INSENSITIVE);
			isMatch = pattern.matcher(modelName).find();
		}
		
		return isMatch;
	}

	public static class FileConverter implements Serializable{
		private String modelName;
		private String extName;
		private String converterKey;
		private String dispenser;
		private String fdHighFidelity;
		private String converterType;
		private String picResolution;
		private String picRectangle;
		private Boolean enabled;

		public String getDispenser() {
			return dispenser;
		}

		public void setDispenser(String dispenser) {
			this.dispenser = dispenser;
		}

		public String getConverterKey() {
			return converterKey;
		}

		public void setConverterKey(String converterKey) {
			this.converterKey = converterKey;
		}

		public String getModelName() {
			return modelName;
		}

		public void setModelName(String modelName) {
			this.modelName = modelName;
		}

		public String getExtName() {
			return extName;
		}

		public void setExtName(String extName) {
			this.extName = extName;
		}

		public String getConverterType() {
			return converterType;
		}

		public void setConverterType(String converterType) {
			this.converterType = converterType;
		}

		public String getFdHighFidelity() {
			return fdHighFidelity;
		}

		public void setFdHighFidelity(String fdHighFidelity) {
			this.fdHighFidelity = fdHighFidelity;
		}

		public String getPicResolution() {
			return picResolution;
		}

		public void setPicResolution(String picResolution) {
			this.picResolution = picResolution;
		}

		public String getPicRectangle() {
			return picRectangle;
		}

		public void setPicRectangle(String picRectangle) {
			this.picRectangle = picRectangle;
		}

		public Boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(Boolean enabled) {
			this.enabled = enabled;
		}

	}

	public static void resetConvertInfo() throws Exception {
		convertDataService.resetConvertInfo();
	}

	public static String getBigImageWidth() {
		String bWidth = ResourceUtil.getKmssConfigString("sys.att.bigImageWidth");
		return StringUtil.isNull(bWidth) ? "1024" : bWidth;
	}

	public static String getSmallImageWidth() {
		String sWidth = ResourceUtil.getKmssConfigString("sys.att.smallImageWidth");
		return StringUtil.isNull(sWidth) ? "512" : sWidth;
	}

	public static int getClientConnectPort() {
		String connectPort = ResourceUtil.getKmssConfigString("sys.att.convert.listenport");
		return Integer.valueOf(StringUtil.isNull(connectPort) ? "5665" : connectPort).intValue();
	}
	
	public static int getClientRegisterPort() {
		String connectPort = ResourceUtil.getKmssConfigString("sys.att.convert.register.listenport");
		return Integer.valueOf(StringUtil.isNull(connectPort) ? "5664" : connectPort).intValue();
	}

	public static int getReceiveMessageHandlerCode() {
		String requestCommandCode = ResourceUtil.getKmssConfigString("sys.att.convert.processport");
		return Integer.valueOf(StringUtil.isNull(requestCommandCode) ? "5656" : requestCommandCode).intValue();
	}

	public static String getModule(String attMainId) {
		return convertDataService.getModuleName(attMainId);
	}

	public static String[] getQueueHelpFul(String modelId, String modelName, String fileName) {
		String[] resultHelpFull = new String[2];
		if (StringUtil.isNotNull(modelId)) {
			SysDictModel dictModel = SysDataDict.getInstance().getModel(modelName);
			if (dictModel != null) {
				String modelUrl = dictModel.getUrl();
				if (StringUtil.isNotNull(modelUrl)) {
					resultHelpFull[1] = modelUrl.replace("${fdId}", modelId.toString());
				}
			}
		}
		resultHelpFull[0] = fileName;
		return resultHelpFull;
	}

	public static void convertedToServer(String attMainId, String fileId, String convertedFileName, byte[] body) {

	}

	public static boolean isAttConvertEnable() {
		return convertDataService.getConfigService().isAttConvertEnable();
	}

	public static boolean isOldConvertSuccessUseHTML() {
		return convertDataService.getConfigService().isOldConvertSuccessUseHTML();
	}

	public static void reloadDistribute() {
		SysFileConvertScheduler scheduler = (SysFileConvertScheduler) SpringBeanUtil.getBean("sysFileConvertScheduler");
		scheduler.reDistribute();
	}

	public static boolean isFileConvertDispatcher() {
		boolean result = false;
		String key = DispatcherCenter.getInstance().getDispatcherServerKey("sysFileConvertDispatcher");
		List<String> keys = new ArrayList<String>();
		keys.add(key);
		List<SysClusterServer> serversList = SysClusterParameter.getInstance().getServerByKey(keys);
		if (!serversList.isEmpty()) {
			if (serversList.get(0).isLocal()) {
				result = true;
			}
		}
		return result;
	}

	public static void enableDefaultConfig() {
		try {
			convertDataService.enableDefaultConvertConfig();
		} catch (Throwable throwable) {
			//
		}
	}

	public static boolean isCommandSuccess(Command cmd) {
		return cmd != null && "true".equals(cmd.getExtFields().get("success"));
	}

	public static boolean isPortUsed(int listenPort) {
		boolean result = false;
		Socket clientBind = null;
		try {
			clientBind = new Socket();
			SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", listenPort);
			clientBind.connect(socketAddress, 1500);
			result = true;
		} catch (Exception e) {
			result = false;
		} finally {
			if (clientBind != null) {
				try {
					clientBind.close();
				} catch (IOException e1) {
					//
				}
			}
		}
		return result;
	}

	public static String getSuWellConvertUrl() {
		SysFileConvertUrlConfig config;
		String suwellConvertUrl = "";
		try {
			config = new SysFileConvertUrlConfig();
			Map map = config.getDataMap();
			if (!map.isEmpty()) {
				suwellConvertUrl = (String) map.get("suwellConvertUrl");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return suwellConvertUrl;
	}

	public static String getSuWellResultPath() {
		SysFileConvertUrlConfig config;
		String suwellResultPath = "";
		try {
			config = new SysFileConvertUrlConfig();
			Map map = config.getDataMap();
			if (!map.isEmpty()) {
				suwellResultPath = (String) map.get("suwellResultPath");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return suwellResultPath;
	}
	/**
	 * 获取文本的MD5信息
	 * @return 32位的MD5数
	 */
	public static  String getMD5(String string) {
 
        MessageDigest md5=null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
        	logger.error("获取文本的MD5信息异常：" + e);
            return null;
        }
        char[] charArray = string.toCharArray(); //将字符串转换为字符数组
        byte[] byteArray = new byte[charArray.length]; //创建字节数组
 
        for (int i = 0; i < charArray.length; i++){
        	byteArray[i] = (byte) charArray[i];
        }
 
        //将得到的字节数组进行MD5运算
         byte[] md5Bytes = md5.digest(byteArray);
        
        StringBuffer md5Str= new StringBuffer();
 
        for (int i = 0; i < md5Bytes.length; i++){
        	if (Integer.toHexString(0xFF & md5Bytes[i]).length() == 1) {
                md5Str.append("0").append(Integer.toHexString(0xFF & md5Bytes[i]));
            } else {
                md5Str.append(Integer.toHexString(0xFF & md5Bytes[i]));
            }
        }
 
        return md5Str.toString();
    }

	/**
	 * 是否开启了指定的服务
	 * @param server
	 * @return
	 */
	public Boolean isChosenConvertServer(String server){
		try {
			switch (server) {
				case "aspose" :
				{
					String isConverterAspose = getSysFileConvertConfigService().findAppConfigValue("fdKey ='attconvert.converter.type.aspose'", "false");
				    return dealString(isConverterAspose);
				}
				case "wps" :
				{
					String isConverterWPS = getSysFileConvertConfigService().findAppConfigValue("fdKey ='attconvert.converter.type.wps'", "false");
					return dealString(isConverterWPS);
				}
				case "skofd":
				{
					String isConverterSKOFD = getSysFileConvertConfigService().findAppConfigValue("fdKey ='attconvert.converter.type.skofd'", "false");
					return dealString(isConverterSKOFD);
				}
				case "wpsCenter":
				{
					String isConverterWPSCenter = getSysFileConvertConfigService().findAppConfigValue("fdKey ='attconvert.converter.type.wpsCenter'", "false");
					return dealString(isConverterWPSCenter);
				}
				case "dianju":
				{
					String isConverterDianju = getSysFileConvertConfigService().findAppConfigValue("fdKey ='attconvert.converter.type.dianju'", "false");
					return dealString(isConverterDianju);
				}
				case "foxit":
				{
					String isConverterFoxit = getSysFileConvertConfigService().findAppConfigValue("fdKey ='attconvert.converter.type.foxit'", "false");
					return dealString(isConverterFoxit);
				}
				default:
					return false;
			}

		} catch (Exception e) {
			logger.error("e:", e.getStackTrace());
		}

		return false;
	}

	public Boolean dealString(String str) {
		if("true".equals(str)) {
			return true;
		}
		return false;
	}
}
