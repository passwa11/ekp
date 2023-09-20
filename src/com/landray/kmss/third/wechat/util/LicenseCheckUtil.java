package com.landray.kmss.third.wechat.util;

import com.landray.kmss.lic.SystemParameter;
import com.landray.kmss.third.wechat.model.WechatMainConfig;

/**
 * @author:kezm
 * @date :2014-11-27上午11:36:01
 */
public class LicenseCheckUtil {
	public static String SYSLICENSE = "";

//	public static Properties properties = new Properties();
//
//	public static InputStream confStream=null;
//	static {
//		confStream= HttpClientUtil.class
//				.getResourceAsStream("wechatconfig.properties");
//		try {
//			properties.load(confStream);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}finally {
//			if (confStream != null) {
//				try {
//					confStream.close();
//				} catch (IOException e) {
//					confStream = null;
//				}
//			}
//		}
//	}

	/**
	 * 检查license的取值方式
	 * @return
	 */
	public static String checkLicense() {
		try {
			WechatMainConfig config = new WechatMainConfig();
			SYSLICENSE = (config.getLwechat_license() == null || "".equals(config.getLwechat_license())) ? SystemParameter.get("license-customer-id"):config.getLwechat_license();
//			return SYSLICENSE = (properties.getProperty("lwechat.license") == null || "".equals(properties.getProperty("lwechat.license"))) ? SystemParameter.get("license-customer-id"):properties
//					.getProperty("lwechat.license"); 
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		// System.out.println(SYSLICENSE);
		return SYSLICENSE;
	}

//	/**
//	 * 获取指定属性名的值
//	 * @param properName
//	 * @return
//	 */
//	public static String getPropertiesValue(String properName) {
//		return properties.getProperty(properName);
//	}
//	
//	/**
//	 * 获取所有属性参数
//	 * @return
//	 */
//	public static Map<String,String> getPropertiesAttr(){
//		Map<String,String> resultMap = new HashMap<String,String>();
//		Enumeration<Object> keys=properties.keys();
//		while(keys.hasMoreElements()){
//			String key = (String) keys.nextElement();
//			String value = properties.getProperty(key);
//			
//			key = key.trim();
//			value = value.trim();
//			resultMap.put(key, value);
//		}
//		return resultMap;
//	}
//	
//	public static void writeProperties(Map<String,String>map){
//		OutputStream fos = null;
//		try {
//			String locationPath = "com/landray/kmss/third/wechat/util/wechatconfig.properties";
//			String path = Thread.currentThread().getContextClassLoader()
//					.getResource(locationPath).getPath();
//
//			Set set = map.keySet();
//			Iterator it = set.iterator();
//			while (it.hasNext()) {
//				String key = (String) it.next();
//				String value = map.get(key);
//				if(StringUtils.isEmpty(value)){
//					value="";
//				}
//				properties.setProperty(key, value);
//			}
//			fos = new FileOutputStream(path);
//			properties.store(fos, null);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (fos != null) {
//				try {
//					fos.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//	
//	
//	public static Map test(){
//		Map<String,String> resultMap = new HashMap<String,String>();
//		Enumeration<Object> keys=properties.keys();
//		while(keys.hasMoreElements()){
//			String key = (String) keys.nextElement();
//			String value = properties.getProperty(key);
//			
//			key = key.trim();
//			value = value.trim();
//			resultMap.put(key, value);
//		}
//		return resultMap;
//	}
}
