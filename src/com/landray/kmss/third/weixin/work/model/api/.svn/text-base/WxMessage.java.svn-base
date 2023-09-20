package com.landray.kmss.third.weixin.work.model.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;

public class WxMessage {
	@JSONField(name = "touser")
	private String toUser;
	@JSONField(name = "toparty")
	private String toParty;
	@JSONField(name = "totag")
	private String toTag;
	@JSONField(name = "agentid")
	private String agentId;
	@JSONField(name = "msgtype")
	private String msgType;
	private String content;
	@JSONField(name = "media_id")
	private String mediaId;
	@JSONField(name = "thumb_media_id")
	private String thumbMediaId;
	private String title;
	private String description;
	private List<WxArticle> articles = new ArrayList();
	private WxArticle textcard = null;
	private Map<String, Object> news = null;

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getToParty() {
		return toParty;
	}

	public void setToParty(String toParty) {
		this.toParty = toParty;
	}

	public String getToTag() {
		return toTag;
	}

	public void setToTag(String toTag) {
		this.toTag = toTag;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<WxArticle> getArticles() {
		return articles;
	}

	public void setArticles(List<WxArticle> articles) {
		this.articles = articles;
	}

	public WxArticle getTextcard() {
		return textcard;
	}

	public void setTextcard(WxArticle textcard) {
		this.textcard = textcard;
	}

	public Map<String, Object> getNews() {
		return news;
	}

	public void setNews(Map<String, Object> news) {
		this.news = news;
	}

	public boolean isToAll() {
		return isToAll;
	}

	public void setToAll(boolean isToAll) {
		this.isToAll = isToAll;
	}

	public List<String> getUserid_list() {
		return userid_list;
	}

	public void setUserid_list(List<String> userid_list) {
		this.userid_list = userid_list;
	}

	private boolean isToAll = true;

	private List<String> userid_list = new ArrayList<String>();

	public Map<String, List<String>> getCorpgroup_userIds_map() {
		return corpgroup_userIds_map;
	}

	public void setCorpgroup_userIds_map(Map<String, List<String>> corpgroup_userIds_map) {
		this.corpgroup_userIds_map = corpgroup_userIds_map;
	}

	private Map<String,List<String>> corpgroup_userIds_map;

}
