package com.landray.kmss.sys.attend.model;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;

/**
 * 打卡日志
 *
 * @author cuiwj
 * @version 1.0 2019-01-21
 */
public class SysAttendMainLog extends BaseModel {

	/**
	 * add or update
	 */
	private String fdMethod;

	public String getFdMethod() {
		return fdMethod;
	}

	public void setFdMethod(String fdMethod) {
		this.fdMethod = fdMethod;
	}

	private Date fdCreateTime;

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	/**
	 * 签到状态
	 */
	private Integer fdStatus = 0;

	/**
	 * @return 签到状态
	 */
	public Integer getFdStatus() {
		return this.fdStatus;
	}

	/**
	 * @param fdStatus
	 *            签到状态
	 */
	public void setFdStatus(Integer fdStatus) {
		this.fdStatus = fdStatus;
	}

	/**
	 * 异常处理状态
	 */
	private Integer fdState;

	public Integer getFdState() {
		return fdState;
	}

	public void setFdState(Integer fdState) {
		this.fdState = fdState;
	}

	/**
	 * 创建时间
	 */
	private Date docCreateTime;

	/**
	 * @return 创建时间
	 */
	public Date getDocCreateTime() {
		return this.docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            创建时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 修改时间
	 */
	private Date docAlterTime;

	/**
	 * @return 修改时间
	 */
	public Date getDocAlterTime() {
		return this.docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            修改时间
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 最后修改人
	 */
	private String docAlterorId;

	public String getDocAlterorId() {
		return docAlterorId;
	}

	public void setDocAlterorId(String docAlterorId) {
		this.docAlterorId = docAlterorId;
	}

	private String fdAlterRecord;

	public String getFdAlterRecord() {
		return fdAlterRecord;
	}

	public void setFdAlterRecord(String fdAlterRecord) {
		this.fdAlterRecord = fdAlterRecord;
	}

	/**
	 * 备注
	 */
	private String fdDesc;

	/**
	 * @return 备注
	 */
	public String getFdDesc() {
		return this.fdDesc;
	}

	/**
	 * @param fdDesc
	 *            备注
	 */
	public void setFdDesc(String fdDesc) {
		this.fdDesc = fdDesc;
	}

	/**
	 * 签到纬度
	 */
	private String fdLng;

	/**
	 * @return 签到纬度
	 */
	public String getFdLng() {
		return this.fdLng;
	}

	/**
	 * @param fdLng
	 *            签到纬度
	 */
	public void setFdLng(String fdLng) {
		this.fdLng = fdLng;
	}

	/**
	 * 签到经度
	 */
	private String fdLat;

	/**
	 * @return 签到经度
	 */
	public String getFdLat() {
		return this.fdLat;
	}

	/**
	 * @param fdLat
	 *            签到经度
	 */
	public void setFdLat(String fdLat) {
		this.fdLat = fdLat;
	}

	/**
	 * 签到地点
	 */
	private String fdLocation;

	/**
	 * @return 签到地点
	 */
	public String getFdLocation() {
		return this.fdLocation;
	}

	/**
	 * @param fdLocation
	 *            签到地点
	 */
	public void setFdLocation(String fdLocation) {
		this.fdLocation = fdLocation;
	}

	/**
	 * @param fdAddress
	 *            签到地址
	 */
	private String fdAddress;

	public String getFdAddress() {
		return fdAddress;
	}

	public void setFdAddress(String fdAddress) {
		this.fdAddress = fdAddress;
	}

	/**
	 * 签到事项ID
	 */
	private String fdCategoryId;

	public String getFdCategoryId() {
		return fdCategoryId;
	}

	public void setFdCategoryId(String fdCategoryId) {
		this.fdCategoryId = fdCategoryId;
	}

	/**
	 * 签到人员
	 */
	private String docCreatorId;

	public String getDocCreatorId() {
		return docCreatorId;
	}

	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * 固定班次
	 */
	private String fdWorkId;

	public String getFdWorkId() {
		return fdWorkId;
	}

	public void setFdWorkId(String fdWorkId) {
		this.fdWorkId = fdWorkId;
	}

	// 上下班类型
	private Integer fdWorkType;
	// 是否外勤
	private Boolean fdOutside;

	public Integer getFdWorkType() {
		return fdWorkType;
	}

	public void setFdWorkType(Integer fdWorkType) {
		this.fdWorkType = fdWorkType;
	}

	public Boolean getFdOutside() {
		return fdOutside;
	}

	public void setFdOutside(Boolean fdOutside) {
		this.fdOutside = fdOutside;
	}

	// 日期类型（休息日1、工作日0）
	private Integer fdDateType;

	public Integer getFdDateType() {
		return fdDateType;
	}

	public void setFdDateType(Integer fdDateType) {
		this.fdDateType = fdDateType;
	}

	/**
	 * 考勤组配置的wifi名字
	 */
	private String fdWifiName;

	public String getFdWifiName() {
		return fdWifiName;
	}

	public void setFdWifiName(String fdWifiName) {
		this.fdWifiName = fdWifiName;
	}

	/**
	 * wifi的MAC地址
	 */
	private String fdWifiMacIp;

	public String getFdWifiMacIp() {
		return fdWifiMacIp;
	}

	public void setFdWifiMacIp(String fdWifiMacIp) {
		this.fdWifiMacIp = fdWifiMacIp;
	}

	/**
	 * 设备信息：操作系统，（设备号）
	 */
	private String fdDeviceInfo;

	public String getFdDeviceInfo() {
		return fdDeviceInfo;
	}

	public void setFdDeviceInfo(String fdDeviceInfo) {
		this.fdDeviceInfo = fdDeviceInfo;
	}

	private String fdDeviceId;

	public String getFdDeviceId() {
		return fdDeviceId;
	}

	public void setFdDeviceId(String fdDeviceId) {
		this.fdDeviceId = fdDeviceId;
	}

	/**
	 * 客户端信息：KK，钉钉，微信，或具体浏览器信息
	 */
	private String fdClientInfo;

	public String getFdClientInfo() {
		return fdClientInfo;
	}

	public void setFdClientInfo(String fdClientInfo) {
		this.fdClientInfo = fdClientInfo;
	}

	/**
	 * 是否跨天打卡的数据
	 */
	private Boolean fdIsAcross;

	public Boolean getFdIsAcross() {
		return fdIsAcross;
	}

	public void setFdIsAcross(Boolean fdIsAcross) {
		this.fdIsAcross = fdIsAcross;
	}

	// 经纬度坐标
	private String fdLatLng;

	public String getFdLatLng() {
		return fdLatLng;
	}

	public void setFdLatLng(String fdLatLng) {
		this.fdLatLng = fdLatLng;
	}

	// 排班班次标识 同班次标识相同
	private String fdWorkKey;

	public String getFdWorkKey() {
		return fdWorkKey;
	}

	public void setFdWorkKey(String fdWorkKey) {
		this.fdWorkKey = fdWorkKey;
	}

	@Override
	public Class getFormClass() {
		return null;
	}

}
