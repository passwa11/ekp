package com.landray.kmss.km.imeeting.synchro.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class SynchroCommonMetting {

	public SynchroCommonMetting(String subject, Date start, Date end,
			Set<String> requiredAttendees, String creatorId) {
		this.subject = subject;
		this.start = start;
		this.end = end;
		this.requiredAttendees = requiredAttendees;
		this.creatorId = creatorId;
	}

	/**
	 * 记录唯一标识
	 */
	// private String uuid;
	//
	// public String getUuid() {
	// return uuid;
	// }
	//
	// public void setUuid(String uuid) {
	// this.uuid = uuid;
	// }
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Set<String> getRequiredAttendees() {
		return requiredAttendees;
	}

	public void setRequiredAttendees(Set<String> requiredAttendees) {
		this.requiredAttendees = requiredAttendees;
	}

	public Set<String> getOptionalAttendees() {
		return optionalAttendees;
	}

	public void setOptionalAttendees(Set<String> optionalAttendees) {
		this.optionalAttendees = optionalAttendees;
	}

	public Set<String> getResourceAttendees() {
		return resourceAttendees;
	}

	public void setResourceAttendees(Set<String> resourceAttendees) {
		this.resourceAttendees = resourceAttendees;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setIsReminderSet(Boolean isReminderSet) {
		this.isReminderSet = isReminderSet;
	}

	public Boolean getIsReminderSet() {
		return isReminderSet;
	}

	public void setReminderMinutesBeforeStart(Integer reminderMinutesBeforeStart) {
		this.reminderMinutesBeforeStart = reminderMinutesBeforeStart;
	}

	public Integer getReminderMinutesBeforeStart() {
		return reminderMinutesBeforeStart;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<SynchroMeetingResponse> getMeetingResponseList() {
		return meetingResponseList;
	}

	public void setMeetingResponseList(
			List<SynchroMeetingResponse> meetingResponseList) {
		this.meetingResponseList = meetingResponseList;
	}

	/**
	 * 标题
	 */
	private String subject;

	/**
	 * 会议地点
	 */
	private String location;

	/**
	 * 会议内容
	 */
	private String body;

	/**
	 * 会议开始时间
	 */
	private Date start;

	/**
	 * 会议结束时间
	 */
	private Date end;

	/**
	 * 必须出席人的列表（用户id）
	 */
	private Set<String> requiredAttendees;

	/**
	 * 可选出席人的列表（用户id）
	 */
	private Set<String> optionalAttendees;

	/**
	 * 资源列表（用户id）
	 */
	private Set<String> resourceAttendees;

	/**
	 * 关键字
	 */
	private String key;

	/**
	 * 是否提醒
	 */
	private Boolean isReminderSet = new Boolean(false);

	/**
	 * 开会前提醒时间（分钟）
	 */
	private Integer reminderMinutesBeforeStart = 30;

	/**
	 * 创建者id
	 */
	private String creatorId;

	private String uuid;

	private List<SynchroMeetingResponse> meetingResponseList;

	public String getFdAppIcalId() {
		return fdAppIcalId;
	}

	public void setFdAppIcalId(String fdAppIcalId) {
		this.fdAppIcalId = fdAppIcalId;
	}

	private String fdAppIcalId;

}
