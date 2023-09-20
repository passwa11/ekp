package com.landray.kmss.sys.attend.model;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendConfigForm;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzModel;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

public class SysAttendConfig extends BaseModel
		implements InterceptFieldEnabled, ISysQuartzModel {

	private String fdExcTargetIds;
	private String fdExcTargetNames;


	public String getFdExcTargetIds() {
		return (String) readLazyField("fdExcTargetIds", fdExcTargetIds);
	}

	public void setFdExcTargetIds(String fdExcTargetIds) {
		this.fdExcTargetIds = (String) writeLazyField("fdExcTargetIds",
				this.fdExcTargetIds, fdExcTargetIds);
	}

	public String getFdExcTargetNames() {
		return (String) readLazyField("fdExcTargetNames", fdExcTargetNames);
	}

	public void setFdExcTargetNames(String fdExcTargetNames) {
		this.fdExcTargetNames = (String) writeLazyField("fdExcTargetNames",
				this.fdExcTargetNames, fdExcTargetNames);
	}

	/*
	 * 极速打卡
	 */
	private Boolean fdSpeedAttend;
	/*
	 * 极速打卡开始时间
	 */
	private Date fdSpeedStartTime;
	/*
	 * 极速打卡结束时间
	 */
	private Date fdSpeedEndTime;

	public Boolean getFdSpeedAttend() {
		return fdSpeedAttend;
	}

	public void setFdSpeedAttend(Boolean fdSpeedAttend) {
		this.fdSpeedAttend = fdSpeedAttend;
	}

	public Date getFdSpeedStartTime() {
		return fdSpeedStartTime;
	}

	public void setFdSpeedStartTime(Date fdSpeedStartTime) {
		this.fdSpeedStartTime = fdSpeedStartTime;
	}

	public Date getFdSpeedEndTime() {
		return fdSpeedEndTime;
	}

	public void setFdSpeedEndTime(Date fdSpeedEndTime) {
		this.fdSpeedEndTime = fdSpeedEndTime;
	}

	private Boolean fdPushLeader;
	private Integer fdPushDate;
	private Date fdPushTime;
	// 考勤客户端限制
	private Boolean fdClientLimit;
	// 客户端类型(kk,ding)
	private String fdClient;
	// 考勤打卡设备限制
	private Boolean fdDeviceLimit;
	// 设备限制数量
	private Integer fdDeviceCount = 1;
	// 设备异常处理方式(camera:拍照备注,face:刷脸验证)
	private String fdDeviceExcMode;
	// 不允许同一设备多用户打卡
	private Boolean fdSameDeviceLimit;
	// 出差按工作日计算
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
		if(fdSignLogToHisDay ==null){
			fdSignLogToHisDay =120;
		}
		return fdSignLogToHisDay;
	}

	public void setFdSignLogToHisDay(Integer fdSignLogToHisDay) {
		this.fdSignLogToHisDay = fdSignLogToHisDay;
	}

	public Integer getFdSignLogToDeleteDay() {
		if(fdSignLogToDeleteDay ==null){
			fdSignLogToDeleteDay =365;
		}
		return fdSignLogToDeleteDay;
	}

	public void setFdSignLogToDeleteDay(Integer fdSignLogToDeleteDay) {
		this.fdSignLogToDeleteDay = fdSignLogToDeleteDay;
	}

	public Boolean getFdPushLeader() {
		return fdPushLeader;
	}

	public void setFdPushLeader(Boolean fdPushLeader) {
		this.fdPushLeader = fdPushLeader;
	}

	public Integer getFdPushDate() {
		return fdPushDate;
	}

	public void setFdPushDate(Integer fdPushDate) {
		this.fdPushDate = fdPushDate;
	}

	public Date getFdPushTime() {
		return fdPushTime;
	}

	public void setFdPushTime(Date fdPushTime) {
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

	private Boolean fdIsRemain;

	public Boolean getFdIsRemain() {
		return fdIsRemain;
	}

	public void setFdIsRemain(Boolean fdIsRemain) {
		this.fdIsRemain = fdIsRemain;
	}

	private Integer fdRemainMonth;

	public Integer getFdRemainMonth() {
		return fdRemainMonth;
	}

	public void setFdRemainMonth(Integer fdRemainMonth) {
		this.fdRemainMonth = fdRemainMonth;
	}

	public Boolean getFdSameDeviceLimit() {
		return fdSameDeviceLimit;
	}

	public void setFdSameDeviceLimit(Boolean fdSameDeviceLimit) {
		this.fdSameDeviceLimit = fdSameDeviceLimit;
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
	public Class getFormClass() {
		return SysAttendConfigForm.class;
	}

}
