package com.landray.kmss.sys.attend.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendSynDingBak;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 打卡原始记录
  */
public class SysAttendSynDingBakForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

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
    private String fdWorkDate;
    
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
     * 时间结果，
     * Normal：正常;
     * Early：早退;
     * Late：迟到;
     * SeriousLate：严重迟到；
     * Absenteeism：旷工迟到；
     * NotSigned：未打卡
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
    private String fdBaseCheckTime;

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
    private String fdIsLegal;
    
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

    private String fdPersonName;
    
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
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdDingId = null;
        fdGroupId = null;
        fdPlanId = null;
        fdApproveId = null;
        fdProcinstId = null;
        fdUserId = null;
        fdWorkDate = null;
        fdBaseCheckTime = null;
        fdUserCheckTime = null;
        fdCheckType = null;
        fdTimeResult = null;
        fdLocationResult = null;
        fdIsLegal = null;
        fdDeviceId = null;
        fdUserAddress = null;
        fdPlanCheckTime = null;
        fdOutsideRemark = null;
        fdPersonId = null;
        fdPersonName = null;
		fdInvalidRecordType = null;
        super.reset(mapping, request);
    }

	@Override
    public Class<SysAttendSynDingBak> getModelClass() {
		return SysAttendSynDingBak.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdWorkDate", new FormConvertor_Common("fdWorkDate").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdBaseCheckTime", new FormConvertor_Common("fdBaseCheckTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdUserCheckTime", new FormConvertor_Common("fdUserCheckTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdPlanCheckTime", new FormConvertor_Common("fdPlanCheckTime").setDateTimeType(DateUtil.TYPE_DATE));
        }
        return toModelPropertyMap;
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
    public String getFdWorkDate() {
        return fdWorkDate;
    }

    /**
     * 工作日
     */
    public void setFdWorkDate(String fdWorkDate) {
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
     * 时间结果，
     * Normal：正常;
     * Early：早退;
     * Late：迟到;
     * SeriousLate：严重迟到；
     * Absenteeism：旷工迟到；
     * NotSigned：未打卡
     */
    public String getFdTimeResult() {
        return fdTimeResult;
    }

    /**
     * 时间结果，
     * Normal：正常;
     * Early：早退;
     * Late：迟到;
     * SeriousLate：严重迟到；
     * Absenteeism：旷工迟到；
     * NotSigned：未打卡
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
    public String getFdBaseCheckTime() {
        return fdBaseCheckTime;
    }

    /**
     * 计算迟到和早退，基准时间；也可作为排班打卡时间
     */
    public void setFdBaseCheckTime(String fdBaseCheckTime) {
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
    public String getFdIsLegal() {
        return fdIsLegal;
    }

    /**
     * 是否合法，当timeResult和locationResult都为Normal时，该值为Y；否则为N
     */
    public void setFdIsLegal(String fdIsLegal) {
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

    /**
     * 员工
     */
    public String getFdPersonId() {
        return this.fdPersonId;
    }

    /**
     * 员工
     */
    public void setFdPersonId(String fdPersonId) {
        this.fdPersonId = fdPersonId;
    }

    /**
     * 员工
     */
    public String getFdPersonName() {
        return this.fdPersonName;
    }

    /**
     * 员工
     */
    public void setFdPersonName(String fdPersonName) {
        this.fdPersonName = fdPersonName;
    }

	private String fdInvalidRecordType;
	private String fdAppName;
	private String fdDateType;
	private String fdLatLng;
	public String getFdInvalidRecordType() {
		return fdInvalidRecordType;
	}

	public void setFdInvalidRecordType(String fdInvalidRecordType) {
		this.fdInvalidRecordType = fdInvalidRecordType;
	}

	public String getFdAppName() {
		return fdAppName;
	}

	public void setFdAppName(String fdAppName) {
		this.fdAppName = fdAppName;
	}

	public String getFdDateType() {
		return fdDateType;
	}

	public void setFdDateType(String fdDateType) {
		this.fdDateType = fdDateType;
	}

	public String getFdLatLng() {
		return fdLatLng;
	}

	public void setFdLatLng(String fdLatLng) {
		this.fdLatLng = fdLatLng;
	}

}
