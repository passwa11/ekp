package com.landray.kmss.sys.attend.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.attend.forms.SysAttendSynDingForm;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaModel;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

/**
  * 打卡原始记录
  */
public class SysAttendSynDing extends BaseModel implements ISysNotifyModel,ISysAuthAreaModel, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

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
     * 钉钉ID
     */
    private String fdDingId;

    /**
     * 考勤组ID
     */
    private String fdGroupId;

    /**
     * 排班ID
     */
    private String fdPlanId;

    /**
     * 工作日
     */
    private Date fdWorkDate;
    
    /**
     * 用户ID
     */
    private String fdUserId;
    
    /**
     * 考勤类型，
     * OnDuty：上班
     * OffDuty：下班
     */
    private String fdCheckType;
    
    /**
     * 数据来源，
     * ATM：考勤机;
     * BEACON：IBeacon;
     * DING_ATM：钉钉考勤机;
     * USER：用户打卡;
     * BOSS：老板改签;
     * APPROVE：审批系统;
     * SYSTEM：考勤系统;
     * AUTO_CHECK：自动打卡
     */
    private String fdSourceType;
    
    /**
	 * 时间结果， Normal：正常; Early：早退; Late：迟到; SeriousLate：严重迟到； Absenteeism：旷工迟到；
	 * NotSigned：未打卡 Trip:出差 Leave:请假 Outgoing:外出
	 */
    private String fdTimeResult;
    
    /**
     * 位置结果，
     * Normal：范围内
     * Outside：范围外，外勤打卡时为这个值
     */
    private String fdLocationResult;
    
    /**
     * 关联的审批id
     */
    private String fdApproveId;

    /**
     * 关联的审批实例id
     */
    private String fdProcinstId;
    
    /**
     * 计算迟到和早退，基准时间；也可作为排班打卡时间
     */
    private Date fdBaseCheckTime;

    /**
     * 实际打卡时间
     */
    private Date fdUserCheckTime;

    /**
     * 考勤班次id，没有的话表示该次打卡不在排班内
     */
    private String fdClassId;
    
    /**
     * 是否合法，当timeResult和locationResult都为Normal时，该值为Y；否则为N
     */
    private Boolean fdIsLegal;
    
    /**
     * 定位方法
     */
    private String fdLocationMethod;

    /**
     * 设备id
     */
    private String fdDeviceId;

    /**
     * 用户打卡地址
     */
    private String fdUserAddress;
    
    /**
     * 用户打卡经度
     */
    private String fdUserLongitude;
    
    /**
     * 用户打卡纬度
     */
    private String fdUserLatitude;
    
    /**
     * 用户打卡定位精度
     */
    private String fdUserAccuracy;

    /**
     * 用户打卡wifi SSID
     */
    private String fdUserSsid;
    
    /**
     * 用户打卡wifi Mac地址
     */
    private String fdUserMacAddr;
    
    /**
     * 排班打卡时间
     */
    private Date fdPlanCheckTime;

    /**
     * 基准地址
     */
    private String fdBaseAddress;

    /**
     * 基准经度
     */
    private String fdBaseLongitude;
    
    /**
     * 基准纬度
     */
    private String fdBaseLatitude;
    
    /**
     * 基准定位精度
     */
    private String fdBaseAccuracy;
    
    /**
     * 基准wifi ssid
     */
    private String fdBaseSsid;
    
    /**
     * 基准 Mac 地址
     */
    private String fdBaseMacAddr;
    
    /**
     * 打卡备注
     */
    private String fdOutsideRemark;
    
	private String fdPersonId;
	
	/**
	 * 打卡规则名称
	 */
	private String fdGroupName;

	/**
	 * 打卡地点title
	 */
	private String fdLocationTitle;

	/**
	 * 打卡wifi名称
	 */
	private String fdWifiName;

	public String getFdGroupName() {
		return fdGroupName;
	}

	public void setFdGroupName(String fdGroupName) {
		this.fdGroupName = fdGroupName;
	}

	public String getFdLocationTitle() {
		return fdLocationTitle;
	}

	public void setFdLocationTitle(String fdLocationTitle) {
		this.fdLocationTitle = fdLocationTitle;
	}

	public String getFdWifiName() {
		return fdWifiName;
	}

	public void setFdWifiName(String fdWifiName) {
		this.fdWifiName = fdWifiName;
	}

    @Override
    public Class<SysAttendSynDingForm> getFormClass() {
        return SysAttendSynDingForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdWorkDate", new ModelConvertor_Common("fdWorkDate").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdBaseCheckTime", new ModelConvertor_Common("fdBaseCheckTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdUserCheckTime", new ModelConvertor_Common("fdUserCheckTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdPlanCheckTime", new ModelConvertor_Common("fdPlanCheckTime").setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 钉钉ID
     */
    public String getFdDingId() {
        return fdDingId;
    }

    /**
     * 钉钉ID
     */
    public void setFdDingId(String fdDingId) {
        this.fdDingId = fdDingId;
    }

    /**
     * 考勤组ID
     */
    public String getFdGroupId() {
        return fdGroupId;
    }

    /**
     * 考勤组ID
     */
    public void setFdGroupId(String fdGroupId) {
        this.fdGroupId = fdGroupId;
    }

    /**
     * 排班ID
     */
    public String getFdPlanId() {
        return fdPlanId;
    }

    /**
     * 排班ID
     */
    public void setFdPlanId(String fdPlanId) {
        this.fdPlanId = fdPlanId;
    }

    /**
     * 工作日
     */
    public Date getFdWorkDate() {
        return fdWorkDate;
    }

    /**
     * 工作日
     */
    public void setFdWorkDate(Date fdWorkDate) {
        this.fdWorkDate = fdWorkDate;
    }

    /**
     * 用户ID
     */
    public String getFdUserId() {
        return fdUserId;
    }

    /**
     * 用户ID
     */
    public void setFdUserId(String fdUserId) {
        this.fdUserId = fdUserId;
    }

    /**
     * 考勤类型，
     * OnDuty：上班
     * OffDuty：下班
     */
    public String getFdCheckType() {
        return fdCheckType;
    }

    /**
     * 考勤类型，
     * OnDuty：上班
     * OffDuty：下班
     */
    public void setFdCheckType(String fdCheckType) {
        this.fdCheckType = fdCheckType;
    }

    /**
     * 数据来源，
     * ATM：考勤机;
     * BEACON：IBeacon;
     * DING_ATM：钉钉考勤机;
     * USER：用户打卡;
     * BOSS：老板改签;
     * APPROVE：审批系统;
     * SYSTEM：考勤系统;
     * AUTO_CHECK：自动打卡
     */
    public String getFdSourceType() {
        return fdSourceType;
    }

    /**
     * 数据来源，
     * ATM：考勤机;
     * BEACON：IBeacon;
     * DING_ATM：钉钉考勤机;
     * USER：用户打卡;
     * BOSS：老板改签;
     * APPROVE：审批系统;
     * SYSTEM：考勤系统;
     * AUTO_CHECK：自动打卡
     */
    public void setFdSourceType(String fdSourceType) {
        this.fdSourceType = fdSourceType;
    }

    /**
	 * 时间结果， Normal：正常; Early：早退; Late：迟到; SeriousLate：严重迟到； Absenteeism：旷工迟到；
	 * NotSigned：未打卡 Trip:出差 Leave:请假 Outgoing:外出
	 */
    public String getFdTimeResult() {
        return fdTimeResult;
    }

    /**
	 * 时间结果， Normal：正常; Early：早退; Late：迟到; SeriousLate：严重迟到； Absenteeism：旷工迟到；
	 * NotSigned：未打卡 Trip:出差 Leave:请假 Outgoing:外出
	 */
    public void setFdTimeResult(String fdTimeResult) {
        this.fdTimeResult = fdTimeResult;
    }

    /**
     * 位置结果，
     * Normal：范围内
     * Outside：范围外，外勤打卡时为这个值
     */
    public String getFdLocationResult() {
        return fdLocationResult;
    }

    /**
     * 位置结果，
     * Normal：范围内
     * Outside：范围外，外勤打卡时为这个值
     */
    public void setFdLocationResult(String fdLocationResult) {
        this.fdLocationResult = fdLocationResult;
    }

    /**
     * 关联的审批id
     */
    public String getFdApproveId() {
        return fdApproveId;
    }

    /**
     * 关联的审批id
     */
    public void setFdApproveId(String fdApproveId) {
        this.fdApproveId = fdApproveId;
    }

    /**
     * 关联的审批实例id
     */
    public String getFdProcinstId() {
        return fdProcinstId;
    }

    /**
     * 关联的审批实例id
     */
    public void setFdProcinstId(String fdProcinstId) {
        this.fdProcinstId = fdProcinstId;
    }

    /**
     * 计算迟到和早退，基准时间；也可作为排班打卡时间
     */
    public Date getFdBaseCheckTime() {
        return fdBaseCheckTime;
    }

    /**
     * 计算迟到和早退，基准时间；也可作为排班打卡时间
     */
    public void setFdBaseCheckTime(Date fdBaseCheckTime) {
        this.fdBaseCheckTime = fdBaseCheckTime;
    }

    /**
     * 实际打卡时间
     */
    public Date getFdUserCheckTime() {
        return fdUserCheckTime;
    }

    /**
     * 实际打卡时间
     */
    public void setFdUserCheckTime(Date fdUserCheckTime) {
        this.fdUserCheckTime = fdUserCheckTime;
    }

    /**
     * 考勤班次id，没有的话表示该次打卡不在排班内
     */
    public String getFdClassId() {
        return fdClassId;
    }

    /**
     * 考勤班次id，没有的话表示该次打卡不在排班内
     */
    public void setFdClassId(String fdClassId) {
        this.fdClassId = fdClassId;
    }

    /**
     * 是否合法，当timeResult和locationResult都为Normal时，该值为Y；否则为N
     */
    public Boolean getFdIsLegal() {
        return fdIsLegal;
    }

    /**
     * 是否合法，当timeResult和locationResult都为Normal时，该值为Y；否则为N
     */
    public void setFdIsLegal(Boolean fdIsLegal) {
        this.fdIsLegal = fdIsLegal;
    }

    /**
     * 定位方法
     */
    public String getFdLocationMethod() {
        return fdLocationMethod;
    }

    /**
     * 定位方法
     */
    public void setFdLocationMethod(String fdLocationMethod) {
        this.fdLocationMethod = fdLocationMethod;
    }

    /**
     * 设备id
     */
    public String getFdDeviceId() {
        return fdDeviceId;
    }

    /**
     * 设备id
     */
    public void setFdDeviceId(String fdDeviceId) {
        this.fdDeviceId = fdDeviceId;
    }

    /**
     * 用户打卡地址
     */
    public String getFdUserAddress() {
        return fdUserAddress;
    }

    /**
     * 用户打卡地址
     */
    public void setFdUserAddress(String fdUserAddress) {
        this.fdUserAddress = fdUserAddress;
    }

    /**
     * 用户打卡经度
     */
    public String getFdUserLongitude() {
        return fdUserLongitude;
    }

    /**
     * 用户打卡经度
     */
    public void setFdUserLongitude(String fdUserLongitude) {
        this.fdUserLongitude = fdUserLongitude;
    }
    
    /**
     * 用户打卡纬度
     */
    public String getFdUserLatitude() {
        return fdUserLatitude;
    }

    /**
     * 用户打卡纬度
     */
    public void setFdUserLatitude(String fdUserLatitude) {
        this.fdUserLatitude = fdUserLatitude;
    }
    
    /**
     * 用户打卡定位精度
     */
    public String getFdUserAccuracy() {
        return fdUserAccuracy;
    }

    /**
     * 用户打卡定位精度
     */
    public void setFdUserAccuracy(String fdUserAccuracy) {
        this.fdUserAccuracy = fdUserAccuracy;
    }

    /**
     * 用户打卡wifi SSID
     */
    public String getFdUserSsid() {
        return fdUserSsid;
    }

    /**
     * 用户打卡wifi SSID
     */
    public void setFdUserSsid(String fdUserSsid) {
        this.fdUserSsid = fdUserSsid;
    }

    /**
     * 用户打卡wifi Mac地址
     */
    public String getFdUserMacAddr() {
        return fdUserMacAddr;
    }

    /**
     * 用户打卡wifi Mac地址
     */
    public void setFdUserMacAddr(String fdUserMacAddr) {
        this.fdUserMacAddr = fdUserMacAddr;
    }

    /**
     * 排班打卡时间
     */
    public Date getFdPlanCheckTime() {
        return fdPlanCheckTime;
    }

    /**
     * 排班打卡时间
     */
    public void setFdPlanCheckTime(Date fdPlanCheckTime) {
        this.fdPlanCheckTime = fdPlanCheckTime;
    }

    /**
     * 基准地址
     */
    public String getFdBaseAddress() {
        return fdBaseAddress;
    }

    /**
     * 基准地址
     */
    public void setFdBaseAddress(String fdBaseAddress) {
        this.fdBaseAddress = fdBaseAddress;
    }

    /**
     * 基准经度
     */
    public String getFdBaseLongitude() {
        return fdBaseLongitude;
    }

    /**
     * 基准经度
     */
    public void setFdBaseLongitude(String fdBaseLongitude) {
        this.fdBaseLongitude = fdBaseLongitude;
    }

    /**
     * 基准纬度
     */
    public String getFdBaseLatitude() {
        return fdBaseLatitude;
    }

    /**
     * 基准纬度
     */
    public void setFdBaseLatitude(String fdBaseLatitude) {
        this.fdBaseLatitude = fdBaseLatitude;
    }

    /**
     * 基准定位精度
     */
    public String getFdBaseAccuracy() {
        return fdBaseAccuracy;
    }

    /**
     * 基准定位精度
     */
    public void setFdBaseAccuracy(String fdBaseAccuracy) {
        this.fdBaseAccuracy = fdBaseAccuracy;
    }

    /**
     * 基准wifi ssid
     */
    public String getFdBaseSsid() {
        return fdBaseSsid;
    }

    /**
     * 基准wifi ssid
     */
    public void setFdBaseSsid(String fdBaseSsid) {
        this.fdBaseSsid = fdBaseSsid;
    }

    /**
     * 基准 Mac 地址
     */
    public String getFdBaseMacAddr() {
        return fdBaseMacAddr;
    }

    /**
     * 基准 Mac 地址
     */
    public void setFdBaseMacAddr(String fdBaseMacAddr) {
        this.fdBaseMacAddr = fdBaseMacAddr;
    }

    /**
     * 打卡备注
     */
    public String getFdOutsideRemark() {
        return fdOutsideRemark;
    }

    /**
     * 打卡备注
     */
    public void setFdOutsideRemark(String fdOutsideRemark) {
        this.fdOutsideRemark = fdOutsideRemark;
    }

	public String getFdPersonId() {
		return fdPersonId;
	}

	public void setFdPersonId(String fdPersonId) {
		this.fdPersonId = fdPersonId;
	}

	/**
	 * Security （安全导致的无效）和 Other （其他非安全原因导致的无效）
	 */
	private String fdInvalidRecordType;

	public String getFdInvalidRecordType() {
		return fdInvalidRecordType;
	}

	public void setFdInvalidRecordType(String fdInvalidRecordType) {
		this.fdInvalidRecordType = fdInvalidRecordType;
	}

	/**
	 * 系统来源
	 */
	private String fdAppName;

	public String getFdAppName() {
		return fdAppName;
	}

	public void setFdAppName(String fdAppName) {
		this.fdAppName = fdAppName;
	}

	// 经纬度坐标
	private String fdLatLng;

	public String getFdLatLng() {
		return fdLatLng;
	}

	public void setFdLatLng(String fdLatLng) {
		this.fdLatLng = fdLatLng;
	}

	// 日期类型（休息日1、工作日0）
	private Integer fdDateType;

	private Date docCreateTime;
	public Integer getFdDateType() {
		return fdDateType;
	}

	public void setFdDateType(Integer fdDateType) {
		this.fdDateType = fdDateType;
	}

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	private SysOrgElement docCreator;

	public SysOrgElement getDocCreator() {
		return docCreator;
	}

	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}

    /**
     * 附件信息
     */
    AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);
    @Override
    public AutoHashMap getAttachmentForms() {
        return autoHashMap;
    }
}
