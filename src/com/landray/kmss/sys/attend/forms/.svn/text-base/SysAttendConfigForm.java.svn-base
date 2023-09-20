package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendConfig;
import com.landray.kmss.web.action.ActionMapping;

public class SysAttendConfigForm extends ExtendForm {

	private String fdExcTargetIds;
	private String fdExcTargetNames;



	public String getFdExcTargetIds() {
		return fdExcTargetIds;
	}

	public void setFdExcTargetIds(String fdExcTargetIds) {
		this.fdExcTargetIds = fdExcTargetIds;
	}

	public String getFdExcTargetNames() {
		return fdExcTargetNames;
	}

	public void setFdExcTargetNames(String fdExcTargetNames) {
		this.fdExcTargetNames = fdExcTargetNames;
	}

	/*
	 * 极速打卡
	 */
	private String fdSpeedAttend;
	/*
	 * 极速打卡开始时间
	 */
	private String fdSpeedStartTime;
	/*
	 * 极速打卡结束时间
	 */
	private String fdSpeedEndTime;

	public String getFdSpeedAttend() {
		return fdSpeedAttend;
	}

	public void setFdSpeedAttend(String fdSpeedAttend) {
		this.fdSpeedAttend = fdSpeedAttend;
	}

	public String getFdSpeedStartTime() {
		return fdSpeedStartTime;
	}

	public void setFdSpeedStartTime(String fdSpeedStartTime) {
		this.fdSpeedStartTime = fdSpeedStartTime;
	}

	public String getFdSpeedEndTime() {
		return fdSpeedEndTime;
	}

	public void setFdSpeedEndTime(String fdSpeedEndTime) {
		this.fdSpeedEndTime = fdSpeedEndTime;
	}

	private String fdPushLeader;
	private String fdPushDate;
	private String fdPushTime;
	// 考勤客户端限制
	private Boolean fdClientLimit;
	// 客户端类型(kk,ding)
	private String fdClient;
	// 考勤打卡设备限制
	private Boolean fdDeviceLimit;
	// 设备限制数量
	private Integer fdDeviceCount = 1;
	// 设备异常处理方式(1:拍照备注,2:刷脸验证)
	private String fdDeviceExcMode;
	// 不允许同一设备多人打卡
	private Boolean fdSameDeviceLimit;
	private Boolean fdTrip;
	// 应出勤天数计算规则标识
	private Boolean fdShouldDayCfg;

	/**
	 * 考勤记录转历史表周期
	 */
	private Integer fdSignLogToHisDay;

	/**
	 * 考勤历史表删除周期
	 */
	private Integer fdSignLogToDeleteDay;

	public Integer getFdSignLogToHisDay() {
		return fdSignLogToHisDay;
	}

	public void setFdSignLogToHisDay(Integer fdSignLogToHisDay) {
		this.fdSignLogToHisDay = fdSignLogToHisDay;
	}

	public Integer getFdSignLogToDeleteDay() {
		return fdSignLogToDeleteDay;
	}

	public void setFdSignLogToDeleteDay(Integer fdSignLogToDeleteDay) {
		this.fdSignLogToDeleteDay = fdSignLogToDeleteDay;
	}

	public Boolean getFdSameDeviceLimit() {
		return fdSameDeviceLimit;
	}

	public void setFdSameDeviceLimit(Boolean fdSameDeviceLimit) {
		this.fdSameDeviceLimit = fdSameDeviceLimit;
	}

	public String getFdPushLeader() {
		return fdPushLeader;
	}

	public void setFdPushLeader(String fdPushLeader) {
		this.fdPushLeader = fdPushLeader;
	}

	public String getFdPushDate() {
		return fdPushDate;
	}

	public void setFdPushDate(String fdPushDate) {
		this.fdPushDate = fdPushDate;
	}

	public String getFdPushTime() {
		return fdPushTime;
	}

	public void setFdPushTime(String fdPushTime) {
		this.fdPushTime = fdPushTime;
	}

	private String fdOffType;

	public String getFdOffType() {
		return fdOffType;
	}

	public void setFdOffType(String fdOffType) {
		this.fdOffType = fdOffType;
	}

	public Boolean getFdClientLimit() {
		return fdClientLimit;
	}

	public void setFdClientLimit(Boolean fdClientLimit) {
		this.fdClientLimit = fdClientLimit;
	}

	public String getFdClient() {
		return fdClient;
	}

	public void setFdClient(String fdClient) {
		this.fdClient = fdClient;
	}

	public Boolean getFdDeviceLimit() {
		return fdDeviceLimit;
	}

	public void setFdDeviceLimit(Boolean fdDeviceLimit) {
		this.fdDeviceLimit = fdDeviceLimit;
	}

	public Integer getFdDeviceCount() {
		return fdDeviceCount;
	}

	public void setFdDeviceCount(Integer fdDeviceCount) {
		this.fdDeviceCount = fdDeviceCount;
	}

	public String getFdDeviceExcMode() {
		return fdDeviceExcMode;
	}

	public void setFdDeviceExcMode(String fdDeviceExcMode) {
		this.fdDeviceExcMode = fdDeviceExcMode;
	}

	private String fdIsRemain;

	public String getFdIsRemain() {
		return fdIsRemain;
	}

	public void setFdIsRemain(String fdIsRemain) {
		this.fdIsRemain = fdIsRemain;
	}

	private String fdRemainMonth;

	public String getFdRemainMonth() {
		return fdRemainMonth;
	}

	public void setFdRemainMonth(String fdRemainMonth) {
		this.fdRemainMonth = fdRemainMonth;
	}

	public Boolean getFdTrip() {
		return fdTrip;
	}

	public void setFdTrip(Boolean fdTrip) {
		this.fdTrip = fdTrip;
	}
	public Boolean getFdShouldDayCfg() {
		return fdShouldDayCfg;
	}

	public void setFdShouldDayCfg(Boolean fdShouldDayCfg) {
		this.fdShouldDayCfg = fdShouldDayCfg;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.fdExcTargetIds = null;
		this.fdExcTargetNames = null;
		this.fdSpeedAttend = null;
		this.fdSpeedStartTime = null;
		this.fdSpeedEndTime = null;
		this.fdPushLeader = null;
		this.fdPushDate = null;
		this.fdPushTime = null;
		this.fdOffType = null;
		this.fdClientLimit = null;
		this.fdClient = null;
		this.fdDeviceLimit = null;
		this.fdDeviceCount = null;
		this.fdDeviceExcMode = null;
		this.fdIsRemain = null;
		this.fdRemainMonth = null;
		this.fdSameDeviceLimit = null;
		this.fdTrip = null;
		this.fdShouldDayCfg = null;
		this.fdSignLogToDeleteDay =null;
		this.fdSignLogToHisDay =null;
		super.reset(mapping, request);
	}

	@Override
	public Class getModelClass() {
		return SysAttendConfig.class;
	}

}
