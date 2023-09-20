package com.landray.kmss.km.calendar.cms;

import com.landray.kmss.km.calendar.cms.interfaces.ICMSProvider;
import com.landray.kmss.km.calendar.cms.interfaces.IOAuthProvider;

public class CMSPluginData implements Comparable {

	private String appKey;

	private String name;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	private ICMSProvider cmsProvider;

	public ICMSProvider getCmsProvider() {
		return cmsProvider;
	}

	public void setCmsProvider(ICMSProvider cmsProvider) {
		this.cmsProvider = cmsProvider;
	}

	public IOAuthProvider getOAuthProvider() {
		return OAuthProvider;
	}

	public void setOAuthProvider(IOAuthProvider authProvider) {
		OAuthProvider = authProvider;
	}

	private IOAuthProvider OAuthProvider;

	private int order;

	@Override
	public int compareTo(Object o) {
		CMSPluginData data = (CMSPluginData) o;
		if (this.order > data.order) {
            return 1;
        } else if (this.order == data.order) {
            return 0;
        } else {
            return -1;
        }
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setBindPageUrl(String bindPageUrl) {
		this.bindPageUrl = bindPageUrl;
	}

	public String getBindPageUrl() {
		return bindPageUrl;
	}

	private String bindPageUrl;

	private Boolean syncNow;

	public Boolean getSyncNow() {
		return syncNow;
	}

	public void setSyncNow(Boolean syncNow) {
		this.syncNow = syncNow;
	}
	
	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	private String originName;

}
