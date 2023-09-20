package com.landray.kmss.third.weixin.mutil.spi.model;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.weixin.mutil.spi.forms.WxworkMenuForm;

public class WxworkMutilMenuModel extends BaseModel {
	protected String fdAgentId;

	public String getFdAgentId() {
		return fdAgentId;
	}

	public void setFdAgentId(String fdAgentId) {
		this.fdAgentId = fdAgentId;
	}

	protected String fdAgentName;

	public String getFdAgentName() {
		return fdAgentName;
	}

	public void setFdAgentName(String fdAgentName) {
		this.fdAgentName = fdAgentName;
	}

	protected String fdMenuJson;

	public String getFdMenuJson() {
		return (String) readLazyField("fdMenuJson", fdMenuJson);
	}

	public void setFdMenuJson(String fdMenuJson) {
		this.fdMenuJson = (String) writeLazyField("fdMenuJson", this.fdMenuJson, fdMenuJson);
	}

	public String fdPublished;

	public void setFdPublished(String fdPublished) {
		this.fdPublished = fdPublished;
	}

	public String getFdPublished() {
		return fdPublished;
	}

	@Override
    public Class getFormClass() {
		return WxworkMenuForm.class;
	}

	/**
	 * 所属企业微信标识
	 */
	public String fdWxKey;

	public String getFdWxKey() {
		return fdWxKey;
	}

	public void setFdWxKey(String fdWxKey) {
		this.fdWxKey = fdWxKey;
	}
}
