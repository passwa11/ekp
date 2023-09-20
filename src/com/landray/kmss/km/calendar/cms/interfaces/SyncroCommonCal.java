package com.landray.kmss.km.calendar.cms.interfaces;

import java.util.Date;
import java.util.List;

import com.landray.kmss.sys.attachment.model.SysAttMain;

public class SyncroCommonCal {

	/*
	 * 第三方应用key值
	 */
	private String appKey;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	/*
	 * 第三方日程记录UUID
	 */
	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/*
	 * EKP日程记录ID
	 */
	private String calendarId;

	public String getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}

	/*
	 * EKP日程标签ID
	 */
	private String labelId;

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	/*
	 * 活动标题
	 */
	private String subject;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	/*
	 * 活动内容
	 */
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/*
	 * 是否全天事件
	 */
	private boolean isAllDayEvent;

	public boolean isAllDayEvent() {
		return isAllDayEvent;
	}

	public void setAllDayEvent(boolean isAllDayEvent) {
		this.isAllDayEvent = isAllDayEvent;
	}

	/*
	 * 事件开始时间
	 */
	private Date eventStartTime;

	public Date getEventStartTime() {
		return eventStartTime;
	}

	public void setEventStartTime(Date eventStartTime) {
		this.eventStartTime = eventStartTime;
	}

	/*
	 * 事件结束时间
	 */
	private Date eventFinishTime;

	public Date getEventFinishTime() {
		return eventFinishTime;
	}

	public void setEventFinishTime(Date eventFinishTime) {
		this.eventFinishTime = eventFinishTime;
	}

	/*
	 * 用户ID
	 */
	private String personId;

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	/*
	 * 创建者ID
	 */
	private String creatorId;

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	/*
	 * 日程相关人
	 */
	private List relatedPersonIds;

	public List<String> getRelatedPersonIds() {
		return relatedPersonIds;
	}

	public void setRelatedPersonIds(List<String> relatedPersonIds) {
		this.relatedPersonIds = relatedPersonIds;
	}

	/*
	 * 描述
	 */
	private String fdDecs;

	public String getFdDecs() {
		return fdDecs;
	}

	public void setFdDecs(String fdDecs) {
		this.fdDecs = fdDecs;
	}


	/*
	 * 创建时间
	 */
	private Date createTime;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/*
	 * 更新时间
	 */
	private Date updateTime;

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/*
	 * 事件地址
	 */
	private String eventLocation;

	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	/*
	 * 关联链接
	 */
	private String relationUrl = null;

	public String getRelationUrl() {
		return relationUrl;
	}

	public void setRelationUrl(String relationUrl) {
		this.relationUrl = relationUrl;
	}

	/*
	 * 循环设置(rfc2445规范)
	 */
	private String recurrentStr;

	public String getRecurrentStr() {
		return recurrentStr;
	}

	public void setRecurrentStr(String recurrentStr) {
		this.recurrentStr = recurrentStr;
	}

	/*
	 * 是否为共享日程
	 */
	private Boolean isShared;

	public Boolean getIsShared() {
		return isShared;
	}

	public void setIsShared(Boolean isShared) {
		this.isShared = isShared;
	}

	private String createdFrom;

	public String getCreatedFrom() {
		return createdFrom;
	}

	public void setCreatedFrom(String createdFrom) {
		this.createdFrom = createdFrom;
	}

	/*
	 * 活动性质
	 */
	private String fdAuthorityType;

	public String getFdAuthorityType() {
		return fdAuthorityType;
	}

	public void setFdAuthorityType(String fdAuthorityType) {
		this.fdAuthorityType = fdAuthorityType;
	}

	/*
	 * 是否农历
	 */
	private boolean isLunar = false;

	public boolean isLunar() {
		return isLunar;
	}

	public void setLunar(boolean isLunar) {
		this.isLunar = isLunar;
	}

	private String calType;

	public String getCalType() {
		return calType;
	}

	public void setCalType(String calType) {
		this.calType = calType;
	}

	/*
	 * 附件
	 */
	private List<SysAttMain> attachments;

	public List<SysAttMain> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<SysAttMain> attachments) {
		this.attachments = attachments;
	}

	/*
	 * 可阅读者
	 */
	private List authReaders;

	public List getAuthReaders() {
		return authReaders;
	}

	public void setAuthReaders(List authReaders) {
		this.authReaders = authReaders;
	}

	/*
	 * 可编辑者
	 */
	private List authEditors;

	public List getAuthEditors() {
		return authEditors;
	}

	public void setAuthEditors(List authEditors) {
		this.authEditors = authEditors;
	}

	// 数据兼容状态
	private String compatibleState;

	public void setCompatibleState(String compatibleState) {
		this.compatibleState = compatibleState;
	}

	public String getCompatibleState() {
		return compatibleState;
	}

	@Override
    public String toString() {
		return uuid + " " + subject + " " + createTime + " " + updateTime;
	}
}
