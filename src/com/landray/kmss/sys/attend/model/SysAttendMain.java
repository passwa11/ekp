package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.attend.forms.SysAttendMainForm;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaModel;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.StringUtil;

import java.util.Date;

/**
 * 签到表
 * 
 * @author
 * @version 1.0 2017-05-24
 */
public class SysAttendMain extends BaseModel implements IAttachment, ISysNotifyModel, ISysAuthAreaModel {

	/*
	 * 所属场所
	 */
	protected SysAuthArea authArea;

	@Override
	public SysAuthArea getAuthArea() {
		return authArea;
	}

	@Override
	public void setAuthArea(SysAuthArea authArea) {
		this.authArea = authArea;
	}

	/**
	 * 签到状态
	 * 【
	 * 1是正常
	 * 2是迟到
	 * 3是早退
	 * 4是出差
	 * 5是请假
	 * 6是外出
	 * 0是缺卡
	 * 11是外勤
	 * 】
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
	private SysOrgPerson docAlteror;

	public SysOrgPerson getDocAlteror() {
		return docAlteror;
	}

	public void setDocAlteror(SysOrgPerson docAlteror) {
		this.docAlteror = docAlteror;
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
	private SysAttendCategory fdCategory;

	/**
	 * @return 签到事项ID
	 */
	public SysAttendCategory getFdCategory() {
		return this.fdCategory;
	}
	/**
	 * @param fdCategory
	 *            签到事项ID
	 */
	public void setFdCategory(SysAttendCategory fdCategory) {
		this.fdCategory = fdCategory;
	}

	/**
	 * 签到人员
	 */
	private SysOrgPerson docCreator;

	/**
	 * @return 签到人员
	 */
	public SysOrgPerson getDocCreator() {
		return this.docCreator;
	}

	/**
	 * @param docCreator
	 *            签到人员
	 */
	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	/**
	 * 固定班次
	 */
	private SysAttendCategoryWorktime workTime;

	public SysAttendCategoryWorktime getWorkTime() {
		return workTime;
	}

	public void setWorkTime(SysAttendCategoryWorktime workTime) {
		this.workTime = workTime;
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

	private SysAttendBusiness fdBusiness;

	public SysAttendBusiness getFdBusiness() {
		return fdBusiness;
	}

	public void setFdBusiness(SysAttendBusiness fdBusiness) {
		this.fdBusiness = fdBusiness;
	}

	// 日期类型（休息日1、工作日0）
	private Integer fdDateType;
	// 签到是否范围外人员（ekp内部人员）
	private Boolean fdOutTarget;

	public Boolean getFdOutTarget() {
		return fdOutTarget;
	}

	public void setFdOutTarget(Boolean fdOutTarget) {
		this.fdOutTarget = fdOutTarget;
	}

	/**
	 * 会议签到ekp外部人员
	 */
	private SysAttendOutPerson fdOutPerson;
	// 数据来源
	private String fdSourceType;

	public String getFdSourceType() {
		return fdSourceType;
	}

	public void setFdSourceType(String fdSourceType) {
		this.fdSourceType = fdSourceType;
	}

	public SysAttendOutPerson getFdOutPerson() {
		return fdOutPerson;
	}

	public void setFdOutPerson(SysAttendOutPerson fdOutPerson) {
		this.fdOutPerson = fdOutPerson;
	}

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
	 * 具体请假类型
	 */
	private Integer fdOffType;

	public Integer getFdOffType() {
		return fdOffType;
	}

	public void setFdOffType(Integer fdOffType) {
		this.fdOffType = fdOffType;
	}

	// 打卡人层级ID，防止人员置为无效时层级信息丢失
	private String docCreatorHId;

	public String getDocCreatorHId() {
		return docCreatorHId;
	}

	public void setDocCreatorHId(String docCreatorHId) {
		this.docCreatorHId = docCreatorHId;
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
		if (StringUtil.isNull(fdLatLng)) {
			if (StringUtil.isNotNull(this.fdLat) && StringUtil.isNotNull(this.fdLng)) {
				return this.getFdLat() + "," + this.getFdLng();
			}
		}
		return fdLatLng;
	}

	public void setFdLatLng(String fdLatLng) {
		this.fdLatLng = fdLatLng;
	}
	// 签到来源系统
	private String fdAppName;
	// 记录状态 1:表示该打卡记录无效
	private Integer docStatus = 0;
	// 排班班次标识 同班次标识相同
	private String fdWorkKey;
	// 实际考勤日期
	private Date fdWorkDate;
	// 基准上下班时间
	private Date fdBaseWorkTime;

	public Date getFdWorkDate() {
		if (this.fdWorkDate == null) {
			fdWorkDate = this.docCreateTime;
			if (Boolean.TRUE.equals(this.fdIsAcross)) {
				fdWorkDate = AttendUtil.getDate(docCreateTime, -1);
			}
		}
		return AttendUtil.getDate(fdWorkDate, 0);
	}

	public void setFdWorkDate(Date fdWorkDate) {
		this.fdWorkDate = fdWorkDate;
	}

	/**
	 * 获取打卡基准时间
	 * 
	 * @return
	 */
	public Date getFdBaseWorkTime() {
		if (fdBaseWorkTime == null) {
			if (this.workTime != null) {
				Date workDate=getFdWorkDate();
				if (this.fdWorkType == 1) {
					fdBaseWorkTime = workTime.getFdEndTime();
					if(this.workTime.getFdOverTimeType()!=null && Integer.valueOf(2).equals(this.workTime.getFdOverTimeType())) {
						workDate=AttendUtil.getDate(workDate, 1);
					}
				} else {
					fdBaseWorkTime = workTime.getFdStartTime();
				}
				Date date = AttendUtil.joinYMDandHMS(workDate, fdBaseWorkTime);
				return date == null ? fdBaseWorkTime : date;
			}
			if (StringUtil.isNotNull(this.fdWorkKey) && this.fdHisCategory != null) {
				return null;
			}
		}
		Date workDate=getFdWorkDate();
		Date baseWorkDate=AttendUtil.getDate(fdBaseWorkTime, 0);
		if (workDate!=null && AttendUtil.getDate(workDate, 1).equals(baseWorkDate)) {
			workDate=baseWorkDate;
		}
		Date date = AttendUtil.joinYMDandHMS(workDate, fdBaseWorkTime);
		return date == null ? fdBaseWorkTime : date;
	}

	public void setFdBaseWorkTime(Date fdBaseWorkTime) {
		this.fdBaseWorkTime = fdBaseWorkTime;
	}

	public Integer getDocStatus() {
		return docStatus;
	}

	public void setDocStatus(Integer docStatus) {
		this.docStatus = docStatus;
	}

	public String getFdWorkKey() {
		return fdWorkKey;
	}

	public void setFdWorkKey(String fdWorkKey) {
		this.fdWorkKey = fdWorkKey;
	}

	public String getFdAppName() {
		return fdAppName;
	}

	public void setFdAppName(String fdAppName) {
		this.fdAppName = fdAppName;
	}

	private SysAttendSignPatch fdSignPatch;

	public SysAttendSignPatch getFdSignPatch() {
		return fdSignPatch;
	}

	public void setFdSignPatch(SysAttendSignPatch fdSignPatch) {
		this.fdSignPatch = fdSignPatch;
	}

	@Override
	public Class<SysAttendMainForm> getFormClass() {
		return SysAttendMainForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdHisCategory.fdId", "fdCategoryId");
			toFormPropertyMap.put("fdHisCategory.fdName", "fdCategoryName");
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
			toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
			toFormPropertyMap.put("docCreator.fdParent.deptLevelNames", "docCreatorDept");
//			toFormPropertyMap.put("workTime.fdId", "fdWorkTimeId");
			toFormPropertyMap.put("fdBusiness.docUrl", "fdBusinessUrl");
			toFormPropertyMap.put("fdOutPerson.fdId", "fdOutPersonId");
			toFormPropertyMap.put("fdOutPerson.fdName", "fdOutPersonName");
		}
		return toFormPropertyMap;
	}

	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
	public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}


	/**
	 * 签到事项的历史版本ID
	 */
	private SysAttendHisCategory fdHisCategory;

	public SysAttendHisCategory getFdHisCategory() {
		return fdHisCategory;
	}

	public void setFdHisCategory(SysAttendHisCategory fdHisCategory) {
		this.fdHisCategory = fdHisCategory;
	}
}
