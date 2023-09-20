package com.landray.kmss.sys.attachment.integrate.wps.cache;

import com.landray.kmss.sys.cache.CacheConfig;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.util.StringUtil;

/**
 * 加载项Token的缓存
 * @author 
 *
 */
public class WpsOfficeTokenCache {
	private static CacheConfig config = CacheConfig.get(WpsOfficeTokenCache.class);
	private static KmssCache cache = new KmssCache(WpsOfficeTokenCache.class,config.setCacheType(CacheConfig.TYPE_REDIS));

	static class Singleton{
		private static WpsOfficeTokenCache instance = new WpsOfficeTokenCache();
	}
	
	public static WpsOfficeTokenCache getInstance() {
		return Singleton.instance;
	}
	
	
	public void set(String key, String value) {
		cache.put(key, value);
	}
	
	public String get(String key) {
		return (String) cache.get(key);
	}
	
	public void delete(String key) {
		cache.remove(key);
	}
	
	public Boolean isExist(String key) {
		String value = (String) cache.get(key);
		if(StringUtil.isNotNull(value)) {
			return true;
		}
		return false;
	}
	
}
