package com.landray.kmss.third.wechat.provider;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.notify.constant.SysNotifyConstants;
import com.landray.kmss.sys.notify.interfaces.ProviderContext;

/**
 * @author:kezm
 * @date :2014-9-23下午02:57:03
 */
public class WechatNotifyContext implements ProviderContext {

	@Override
	public String getType() {
		return "wechat";
	}

	@Override
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.accumulate("type", getType());
		json.accumulate("asynch", isAsynch());
		return json;
	}

	@Override
	public void fromJson(JSONObject json) {
		setAsynch(json.getBoolean("asynch"));
	}

	@Override
	public boolean isAsynch() {
		return asynch;
	}

	/**
	 * 默认为同步
	 */
	private boolean asynch = false;

	@Override
	public void setAsynch(boolean asynch) {
		this.asynch = asynch;
	}

}
