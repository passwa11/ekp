package com.landray.kmss.third.ding.provider;

import com.landray.kmss.sys.notify.interfaces.ProviderContext;

import net.sf.json.JSONObject;

public class SendDINGNotifyContext implements ProviderContext {

	@Override
	public String getType() {
		return "ding";
	}

	/**
	 * 默认为同步
	 */
	private boolean asynch = false;

	@Override
	public void setAsynch(boolean asynch) {
		this.asynch = asynch;
	}

	@Override
	public boolean isAsynch() {
		return asynch;
	}

	@Override
	public void fromJson(JSONObject json) {
		setAsynch(json.getBoolean("asynch"));
	}

	@Override
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.accumulate("type", getType());
		json.accumulate("asynch", isAsynch());
		return json;
	}

}
