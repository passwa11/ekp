package com.landray.kmss.sys.mportal;

import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.mportal.forms.SysMportalPersonForm;

public class MobilePortalCache {

	private static final KmssCache mportalCache = new KmssCache(
			MobilePortalCache.class, 100);

	public boolean has(String key) {
		return (mportalCache.get(key) != null);
	}

	public void put(String key, SysMportalPersonForm person) {
		this.put(key, person ,false);
	}
	
	
	public void put(String key, SysMportalPersonForm person, Boolean isClear) {
		if(isClear == null ) {
			isClear = false;
		}
		if(isClear) {
			this.remove(key);
		}
		mportalCache.put(key, person);
	}

	public SysMportalPersonForm get(String key) {
		return (SysMportalPersonForm) mportalCache.get(key);
	}

	public void clearNoCluster() {
		mportalCache.clear(true);
	}

	public void clear() {
		mportalCache.clear();
	}
	
	public void remove(String key) {
		mportalCache.remove(key);
	}
}
