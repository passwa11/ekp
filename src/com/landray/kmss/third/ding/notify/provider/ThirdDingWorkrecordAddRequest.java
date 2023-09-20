package com.landray.kmss.third.ding.notify.provider;

import java.util.Map;

import com.dingtalk.api.request.OapiWorkrecordAddRequest;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdDingWorkrecordAddRequest extends OapiWorkrecordAddRequest {

	private String bizId;

	@Override
    public String getBizId() {
		return bizId;
	}

	@Override
    public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	
	private String pcUrl;
	private Long pcOpenType;
	private String sourceName;

	@Override
    public String getPcUrl() {
		return pcUrl;
	}

	@Override
    public void setPcUrl(String pcUrl) {
		this.pcUrl = pcUrl;
	}

	@Override
    public Long getPcOpenType() {
		return pcOpenType;
	}

	@Override
    public void setPcOpenType(Long pcOpenType) {
		this.pcOpenType = pcOpenType;
	}

	@Override
    public String getSourceName() {
		return sourceName;
	}

	@Override
    public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	@Override
    public Map<String, String> getTextParams() {
		Map<String, String> txtParams = super.getTextParams();
		if (StringUtil.isNotNull(bizId)) {
			txtParams.put("biz_id", this.bizId);
		}
		if (StringUtil.isNotNull(pcUrl)) {
			txtParams.put("pcUrl", this.pcUrl);
		}
		if (pcOpenType != null) {
			txtParams.put("pc_open_type", pcOpenType.toString());
		}
		if (StringUtil.isNotNull(sourceName)) {
			txtParams.put("source_name", this.sourceName);
		}
		return txtParams;
	}

	@Override
	public String toString() {
		JSONObject data = new JSONObject();
		data.put("userId", getUserid());
		data.put("createTime", getCreateTime());
		data.put("title", getTitle());
		data.put("url", getUrl());
		data.put("pcUrl", getPcUrl());
		data.put("pcOpenType", getPcOpenType());
		data.put("sourceName", getSourceName());
		String itemsStr = getFormItemList();
		JSONArray items = JSONArray.fromObject(itemsStr);
		data.put("itemList", items);
		return data.toString();
	}

}
