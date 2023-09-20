package com.landray.kmss.km.forum.forms;

import com.landray.kmss.common.forms.BaseForm;

/**
 * 创建日期 2006-Aug-31
 * 
 * @author 吴兵
 */
public class KmForumConfigForm extends BaseForm

{

	/**
	 * 是否允许匿名发文
	 */
	private String anonymous = null;

	public String getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}

	/**
	 * 热帖开关
	 */
	public Integer hotReplyCount;

	public Integer getHotReplyCount() {
		return hotReplyCount;
	}

	public void setHotReplyCount(Integer hotReplyCount) {
		this.hotReplyCount = hotReplyCount;
	}

	/**
	 * 发帖时间间隔
	 */
	public String replyTimeInterval;

	public String getReplyTimeInterval() {
		return replyTimeInterval;
	}

	public void setReplyTimeInterval(String replyTimeInterval) {
		this.replyTimeInterval = replyTimeInterval;
	}

	/**
	 * 是否允许用户修改个人信息
	 */
	private String canModifyRight = null;

	public String getCanModifyRight() {
		return canModifyRight;
	}

	public void setCanModifyRight(String canModifyRight) {
		this.canModifyRight = canModifyRight;
	}

	/**
	 * 是否允许使用昵称
	 */
	private String canModifyNickname = null;

	public String getCanModifyNickname() {
		return canModifyNickname;
	}

	public void setCanModifyNickname(String canModifyNickname) {
		this.canModifyNickname = canModifyNickname;
	}

	/**
	 * 用户等级
	 */
	private String level = null;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * 敏感词检测开关
	 * 
	 */
	private String isWordCheck = null;
	/**
	 * 敏感词
	 */

	private String words = null;

	public String getIsWordCheck() {
		return isWordCheck;
	}

	public void setIsWordCheck(String isWordCheck) {
		this.isWordCheck = isWordCheck;
	}

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	/**
	 * webservice默认版块
	 */
	public String webServiceDefForumId;
	public String webServiceDefForumName;

	public String getWebServiceDefForumId() {
		return webServiceDefForumId;
	}

	public void setWebServiceDefForumId(String webServiceDefForumId) {
		this.webServiceDefForumId = webServiceDefForumId;
	}

	public String getWebServiceDefForumName() {
		return webServiceDefForumName;
	}

	public void setWebServiceDefForumName(String webServiceDefForumName) {
		this.webServiceDefForumName = webServiceDefForumName;
	}

}
