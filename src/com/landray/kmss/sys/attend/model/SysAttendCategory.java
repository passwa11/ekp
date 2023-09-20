package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.attend.forms.SysAttendCategoryForm;
import com.landray.kmss.sys.attend.util.AttendPlugin;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzModel;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.sys.time.model.SysTimeHoliday;
import com.landray.kmss.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 签到事项
 *
 * @author
 * @version 1.0 2017-05-24
 */
public class SysAttendCategory extends ExtendAuthModel
        implements ISysQuartzModel, ISysNotifyModel {

    /**
     * 签到事项名
     */
    private String fdName;

    /**
     * @return 签到事项名
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * @param fdName 签到事项名
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 排序号
     */
    private Integer fdOrder;

    /**
     * @return 排序号
     */
    public Integer getFdOrder() {
        return this.fdOrder;
    }

    /**
     * @param fdOrder 排序号
     */
    public void setFdOrder(Integer fdOrder) {
        this.fdOrder = fdOrder;
    }

    /**
     * 综合工时 开始时间
     */
    private Date fdStartTimeComprehensive;
    /**
     * 综合工时 结束时间
     */
    private Date fdEndTimeComprehensive;

    public Date getFdStartTimeComprehensive() {
        return fdStartTimeComprehensive;
    }

    public void setFdStartTimeComprehensive(Date fdStartTimeComprehensive) {
        this.fdStartTimeComprehensive = fdStartTimeComprehensive;
    }

    public Date getFdEndTimeComprehensive() {
        return fdEndTimeComprehensive;
    }

    public void setFdEndTimeComprehensive(Date fdEndTimeComprehensive) {
        this.fdEndTimeComprehensive = fdEndTimeComprehensive;
    }

    /**
     * 第一班次：最早打卡
     */
    private Date fdStartTime;

    public Date getFdStartTime() {
        return this.fdStartTime;
    }

    public void setFdStartTime(Date fdStartTime) {
        this.fdStartTime = fdStartTime;
    }

    /**
     * 第一班次：最晚打卡
     */
    private Date fdEndTime1;

    public Date getFdEndTime1() {
        return fdEndTime1;
    }

    public void setFdEndTime1(Date fdEndTime1) {
        this.fdEndTime1 = fdEndTime1;
    }

    /**
     * 第二班次：最早打卡
     */
    private Date fdStartTime2;

    public Date getFdStartTime2() {
        return fdStartTime2;
    }

    public void setFdStartTime2(Date fdStartTime2) {
        this.fdStartTime2 = fdStartTime2;
    }


    /**
     * 第二班次：最晚打卡
     */
    private Date fdEndTime;

    public Date getFdEndTime() {
        return this.fdEndTime;
    }

    public void setFdEndTime(Date fdEndTime) {
        this.fdEndTime = fdEndTime;
    }

    /**
     * 关闭打卡日期，当天还是第二天
     */
    private Integer fdEndDay;

    public Integer getFdEndDay() {
        return fdEndDay;
    }

    public void setFdEndDay(Integer fdEndDay) {
        this.fdEndDay = fdEndDay;
    }

    /**
     * 签到类型
     */
    private Integer fdType;

    /**
     * @return 签到类型
     */
    public Integer getFdType() {
        return this.fdType;
    }

    /**
     * @param fdType 签到类型
     */
    public void setFdType(Integer fdType) {
        this.fdType = fdType;
    }

    /**
     * 签到周期类型
     */
    private Integer fdPeriodType;

    /**
     * @return 签到周期类型
     */
    public Integer getFdPeriodType() {
        return this.fdPeriodType;
    }

    /**
     * @param fdPeriodType 签到周期类型
     */
    public void setFdPeriodType(Integer fdPeriodType) {
        this.fdPeriodType = fdPeriodType;
    }

    /**
     * 签到周期
     */
    private String fdWeek;

    /**
     * @return 签到周期
     */
    public String getFdWeek() {
        return this.fdWeek;
    }

    /**
     * @param fdWeek 签到周期
     */
    public void setFdWeek(String fdWeek) {
        this.fdWeek = fdWeek;
    }

    /**
     * 应用/模块Id
     */
    private String fdAppId;

    public String getFdAppId() {
        return fdAppId;
    }

    public void setFdAppId(String fdAppId) {
        this.fdAppId = fdAppId;
    }

    /**
     * 应用
     */
    private String fdAppName;

    /**
     * @return 应用
     */
    public String getFdAppName() {
        if (StringUtil.isNotNull(getFdAppKey())) {
            IExtension extension = AttendPlugin.getExtensionByModelKey(getFdAppKey());
            if (extension != null) {
                return Plugin.getParamValueString(extension, "moduleName");
            }
        }
        return this.fdAppName;
    }

    /**
     * @param fdAppName 应用
     */
    public void setFdAppName(String fdAppName) {
        this.fdAppName = fdAppName;
    }

    private String fdAppKey;

    public String getFdAppKey() {
        return fdAppKey;
    }

    public void setFdAppKey(String fdAppKey) {
        this.fdAppKey = fdAppKey;
    }

    /**
     * 状态
     */
    private Integer fdStatus;

    /**
     * @return 状态
     */
    public Integer getFdStatus() {
        return this.fdStatus;
    }

    /**
     * @param fdStatus 状态
     */
    public void setFdStatus(Integer fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 应用url
     */
    private String fdAppUrl;

    /**
     * @return 应用url
     */
    public String getFdAppUrl() {
        return this.fdAppUrl;
    }

    /**
     * @param fdAppUrl 应用url
     */
    public void setFdAppUrl(String fdAppUrl) {
        this.fdAppUrl = fdAppUrl;
    }

    /**
     * 最后修改时间
     */
    private Date docAlterTime;

    /**
     * @return 最后修改时间
     */
    public Date getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * @param docAlterTime 最后修改时间
     */
    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    /**
     * 创建时间
     */
    private Date docCreateTime;

    /**
     * @return 创建时间
     */
    @Override
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * @param docCreateTime 创建时间
     */
    @Override
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 考勤班制
     */
    private Integer fdWork;

    /**
     * @return 考勤班制
     */
    public Integer getFdWork() {
        return this.fdWork;
    }

    /**
     * @param fdWork 考勤班制
     */
    public void setFdWork(Integer fdWork) {
        this.fdWork = fdWork;
    }

    /**
     * 修改人
     */
    private SysOrgElement docAlteror;

    /**
     * @return 修改人
     */
    public SysOrgElement getDocAlteror() {
        return this.docAlteror;
    }

    /**
     * @param docAlteror 修改人
     */
    public void setDocAlteror(SysOrgElement docAlteror) {
        this.docAlteror = docAlteror;
    }

    /**
     * 创建者
     */
    private SysOrgPerson docCreator;

    /**
     * @return 创建者
     */
    @Override
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * @param docCreator 创建者
     */
    @Override
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

    /**
     * 负责人
     */
    private SysOrgElement fdManager;

    public SysOrgElement getFdManager() {
        return fdManager;
    }

    public void setFdManager(SysOrgElement fdManager) {
        this.fdManager = fdManager;
    }

    /**
     * 签到规则
     */
    private List<SysAttendCategoryRule> fdRule;

    /**
     * @return 签到规则
     */
    public List<SysAttendCategoryRule> getFdRule() {
        return this.fdRule;
    }

    /**
     * @param fdRule 签到规则
     */
    public void setFdRule(List<SysAttendCategoryRule> fdRule) {
        this.fdRule = fdRule;
    }

    /**
     * 追加日期
     */
    private List<SysAttendCategoryTime> fdTimes = new ArrayList<SysAttendCategoryTime>();

    /**
     * @return 追加日期
     */
    public List<SysAttendCategoryTime> getFdTimes() {
        return this.fdTimes;
    }

    /**
     * @param fdTimes 追加日期
     */
    public void setFdTimes(List<SysAttendCategoryTime> fdTimes) {
        this.fdTimes = fdTimes;
    }

    /**
     * 例外日期
     */
    private List<SysAttendCategoryExctime> fdExcTimes = new ArrayList<SysAttendCategoryExctime>();

    /**
     * @return 例外日期
     */
    public List<SysAttendCategoryExctime> getFdExcTimes() {
        return this.fdExcTimes;
    }

    /**
     * @param fdExcTimes 例外日期
     */
    public void setFdExcTimes(List<SysAttendCategoryExctime> fdExcTimes) {
        this.fdExcTimes = fdExcTimes;
    }

    /**
     * 签到对象
     */
    private List<SysOrgElement> fdTargets;

    /**
     * @return 签到对象
     */
    public List<SysOrgElement> getFdTargets() {
        return this.fdTargets;
    }

    /**
     * @param fdTargets 签到对象
     */
    public void setFdTargets(List<SysOrgElement> fdTargets) {
        this.fdTargets = fdTargets;
    }

    /**
     * 例外对象
     */
    private List<SysOrgElement> fdExcTargets;

    /**
     * @return 例外对象
     */
    public List<SysOrgElement> getFdExcTargets() {
        return this.fdExcTargets;
    }

    /**
     * @param fdExcTargets 例外对象
     */
    public void setFdExcTargets(List<SysOrgElement> fdExcTargets) {
        this.fdExcTargets = fdExcTargets;
    }

    /**
     * 考勤班次
     */
    private List<SysAttendCategoryWorktime> fdWorkTime = new ArrayList<SysAttendCategoryWorktime>();


    /**
     * 可能包含无效的班制，不建议使用
     *
     * @return
     */
    @Deprecated
    public List<SysAttendCategoryWorktime> getFdWorkTime() {
        return fdWorkTime;
    }

    /**
     * 去除无效的班制，建议使用
     *
     * @return
     */
    public List<SysAttendCategoryWorktime> getAvailWorkTime() {
        List<SysAttendCategoryWorktime> tmpWorkTime = new ArrayList<SysAttendCategoryWorktime>();
        for (int i = 0; i < fdWorkTime.size(); i++) {
            SysAttendCategoryWorktime work = fdWorkTime.get(i);
            if (!Boolean.TRUE.equals(work.getFdIsAvailable())){
                tmpWorkTime.remove(work);
            }else{
                tmpWorkTime.add(work);
            }
        }
        return tmpWorkTime;
    }

    /**
     * 获取所有有效的班制，包含“一周不同上下班”里的所有班制(该方法不适用排班制,排班制只能通过用户去获取班制)
     *
     * @return
     */
    public List<SysAttendCategoryWorktime> getAllWorkTime() {
        List<SysAttendCategoryWorktime> workTimes = getAvailWorkTime();
        if (Integer.valueOf(0).equals(fdShiftType)
                && Integer.valueOf(1).equals(fdSameWorkTime)) {
            List<SysAttendCategoryTimesheet> tSheets = getFdTimeSheets();
            for (SysAttendCategoryTimesheet tSheet : tSheets) {
                workTimes.addAll(tSheet.getAvailWorkTime());
            }
        }
        return workTimes;
    }

    /**
     * @param fdWorkTime 考勤班次
     */
    public void setFdWorkTime(List<SysAttendCategoryWorktime> fdWorkTime) {
        this.fdWorkTime = fdWorkTime;
    }

    /**
     * 签到点
     */
    private List<SysAttendCategoryLocation> fdLocations;

    /**
     * @return 签到点
     */
    public List<SysAttendCategoryLocation> getFdLocations() {
        return this.fdLocations;
    }

    /**
     * @param fdLocations 签到点
     */
    public void setFdLocations(List<SysAttendCategoryLocation> fdLocations) {
        this.fdLocations = fdLocations;
    }

    private List<SysAttendCategoryWifi> fdWifiConfigs;

    public List<SysAttendCategoryWifi> getFdWifiConfigs() {
        return fdWifiConfigs;
    }

    public void setFdWifiConfigs(List<SysAttendCategoryWifi> fdWifiConfigs) {
        this.fdWifiConfigs = fdWifiConfigs;
    }

    /**
     * 出差设置明细表
     */
    private List<SysAttendCategoryBusiness> busSetting;

    public List<SysAttendCategoryBusiness> getBusSetting() {
        return busSetting;
    }

    public void setBusSetting(List<SysAttendCategoryBusiness> busSetting) {
        this.busSetting = busSetting;
    }

    private Integer fdNotifyOnTime;

    public Integer getFdNotifyOnTime() {
        return fdNotifyOnTime;
    }

    public void setFdNotifyOnTime(Integer fdNotifyOnTime) {
        this.fdNotifyOnTime = fdNotifyOnTime;
    }

    private Integer fdNotifyOffTime;

    public Integer getFdNotifyOffTime() {
        return fdNotifyOffTime;
    }

    public void setFdNotifyOffTime(Integer fdNotifyOffTime) {
        this.fdNotifyOffTime = fdNotifyOffTime;
    }

    private Boolean fdNotifyResult;

    public Boolean getFdNotifyResult() {
        return fdNotifyResult;
    }

    public void setFdNotifyResult(Boolean fdNotifyResult) {
        this.fdNotifyResult = fdNotifyResult;
    }

    private String fdPermState;

    public String getFdPermState() {
        return fdPermState;
    }

    public void setFdPermState(String fdPermState) {
        this.fdPermState = fdPermState;
    }

    // 是否加班
    private Boolean fdIsOvertime;
    // 加班规则类型
    private Integer fdOvtReviewType;
    /**
     * 最小加班时长 单位为小时
     * （兼容历史数据，单位为分钟的新增字段）
     */
    private Float fdMinHour;

    // 统计取整规则
    private Integer fdRoundingType;

    public Integer getFdRoundingType() {
        return fdRoundingType;
    }

    public void setFdRoundingType(Integer fdRoundingType) {
        this.fdRoundingType = fdRoundingType;
    }

    // 加班统计最小单位(小时)
    private Double fdMinUnitHour;

    public Double getFdMinUnitHour() {
        return fdMinUnitHour;
    }

    public void setFdMinUnitHour(Double fdMinUnitHour) {
        this.fdMinUnitHour = fdMinUnitHour;
    }

    // 是否允许非会议参与人员（EKP内部人员）签到
    private Boolean fdUnlimitTarget = Boolean.FALSE;
    private Integer fdQRCodeTime;

    public Integer getFdQRCodeTime() {
        return fdQRCodeTime;
    }

    public void setFdQRCodeTime(Integer fdQRCodeTime) {
        this.fdQRCodeTime = fdQRCodeTime;
    }

    public Boolean getFdUnlimitTarget() {
        return fdUnlimitTarget;
    }

    public void setFdUnlimitTarget(Boolean fdUnlimitTarget) {
        this.fdUnlimitTarget = fdUnlimitTarget;
    }

    // 是否允许EKP外部人员签到
    private Boolean fdUnlimitOuter = Boolean.FALSE;

    public Boolean getFdUnlimitOuter() {
        return fdUnlimitOuter;
    }

    public void setFdUnlimitOuter(Boolean fdUnlimitOuter) {
        this.fdUnlimitOuter = fdUnlimitOuter;
    }

    public Float getFdMinHour() {
        return fdMinHour;
    }

    public void setFdMinHour(Float fdMinHour) {
        this.fdMinHour = fdMinHour;
    }

    public Boolean getFdIsOvertime() {
        return fdIsOvertime;
    }

    public void setFdIsOvertime(Boolean fdIsOvertime) {
        this.fdIsOvertime = fdIsOvertime;
    }

    public Integer getFdOvtReviewType() {
        return fdOvtReviewType;
    }

    public void setFdOvtReviewType(Integer fdOvtReviewType) {
        this.fdOvtReviewType = fdOvtReviewType;
    }

    /**
     * 签到分类
     */
    private SysAttendCategoryTemplate fdTemplate;

    public SysAttendCategoryTemplate getFdTemplate() {
        return fdTemplate;
    }

    public void setFdTemplate(SysAttendCategoryTemplate fdTemplate) {
        this.fdTemplate = fdTemplate;
    }

    /**
     * 考勤组分类
     */
    private SysAttendCategoryATemplate fdATemplate;

    public SysAttendCategoryATemplate getFdATemplate() {
        return fdATemplate;
    }

    public void setFdATemplate(SysAttendCategoryATemplate fdATemplate) {
        this.fdATemplate = fdATemplate;
    }

    @Override
    public Class<SysAttendCategoryForm> getFormClass() {
        return SysAttendCategoryForm.class;
    }

    @Override
    public Boolean getAuthReaderFlag() {
        return new Boolean(false);
    }

    /**
     * 是否弹性上下班
     */
    private Boolean fdIsFlex;

    public Boolean getFdIsFlex() {
        return fdIsFlex;
    }

    public void setFdIsFlex(Boolean fdIsFlex) {
        this.fdIsFlex = fdIsFlex;
    }

    /**
     * 弹性上下班浮动时间
     */
    private Integer fdFlexTime;

    public Integer getFdFlexTime() {
        return fdFlexTime;
    }

    public void setFdFlexTime(Integer fdFlexTime) {
        this.fdFlexTime = fdFlexTime;
    }

    /**
     * 迟到累计多少分钟开始算旷工
     */
    private Integer fdLateTotalTime;
    
    /**
     * 迟到累计多少次开始算旷工
     */
    private Integer fdLateNumTotalTime;

    
    /**
     * 迟到多少分钟算旷工半天
     */
    private Integer fdLateToAbsentTime;

    /**
     * 早退多少分钟算旷工半天
     */
    private Integer fdLeftToAbsentTime;

    /**
     * 迟到多少分钟算旷工一天
     */
    private Integer fdLateToFullAbsTime;

    /**
     * 早退多少分钟算旷工一天
     */
    private Integer fdLeftToFullAbsTime;

    public Integer getFdLateToAbsentTime() {
        return fdLateToAbsentTime;
    }

    public void setFdLateToAbsentTime(Integer fdLateToAbsentTime) {
        this.fdLateToAbsentTime = fdLateToAbsentTime;
    }

    public Integer getFdLeftToAbsentTime() {
        return fdLeftToAbsentTime;
    }

    public void setFdLeftToAbsentTime(Integer fdLeftToAbsentTime) {
        this.fdLeftToAbsentTime = fdLeftToAbsentTime;
    }

    public Integer getFdLateToFullAbsTime() {
        return fdLateToFullAbsTime;
    }

    public void setFdLateToFullAbsTime(Integer fdLateToFullAbsTime) {
        this.fdLateToFullAbsTime = fdLateToFullAbsTime;
    }

    public Integer getFdLeftToFullAbsTime() {
        return fdLeftToFullAbsTime;
    }

    public void setFdLeftToFullAbsTime(Integer fdLeftToFullAbsTime) {
        this.fdLeftToFullAbsTime = fdLeftToFullAbsTime;
    }

    /**
     * 外勤是否需审批。0不审批，1需审批
     */
    private Integer fdOsdReviewType;
    // 生效日期
    private Date fdEffectTime;

    public Integer getFdOsdReviewType() {
        return fdOsdReviewType;
    }

    public void setFdOsdReviewType(Integer fdOsdReviewType) {
        this.fdOsdReviewType = fdOsdReviewType;
    }

    //
    private Integer fdOsdReviewIsUpload;

    public Integer getFdOsdReviewIsUpload() {
        return fdOsdReviewIsUpload;
    }

    public void setFdOsdReviewIsUpload(Integer fdOsdReviewIsUpload) {
        this.fdOsdReviewIsUpload = fdOsdReviewIsUpload;
    }

    public Date getFdEffectTime() {
        if (fdEffectTime == null) {
            return this.docCreateTime;
        }
        return fdEffectTime;
    }

    public void setFdEffectTime(Date fdEffectTime) {
        this.fdEffectTime = fdEffectTime;
    }

    /**
     * 班制类型。0固定班制，1排版，2自定义, 3 综合工时 ,4 不定时工作制
     */
    private Integer fdShiftType;

    public Integer getFdShiftType() {
        return fdShiftType;
    }

    public void setFdShiftType(Integer fdShiftType) {
        this.fdShiftType = fdShiftType;
    }

    /**
     * 一周内相同工作时间。0相同，1不相同
     */
    private Integer fdSameWorkTime;

    public Integer getFdSameWorkTime() {
        return fdSameWorkTime;
    }

    public void setFdSameWorkTime(Integer fdSameWorkTime) {
        this.fdSameWorkTime = fdSameWorkTime;
    }

    /**
     * 午休开始时间
     */
    private Date fdRestStartTime;

    public Date getFdRestStartTime() {
        return fdRestStartTime;
    }

    public void setFdRestStartTime(Date fdRestStartTime) {
        this.fdRestStartTime = fdRestStartTime;
    }

    /**
     * 午休结束时间
     */
    private Date fdRestEndTime;

    public Date getFdRestEndTime() {
        return fdRestEndTime;
    }

    public void setFdRestEndTime(Date fdRestEndTime) {
        this.fdRestEndTime = fdRestEndTime;
    }

    /**
     * 午休开始时间类型
     * 1,当日
     * 2,次日
     */
    private Integer fdRestStartType;

    public Integer getFdRestStartType() {
        //兼容历史没设置过的，默认是当日
        if(fdRestStartType ==null){
            fdRestStartType =1;
        }
        return fdRestStartType;
    }

    public void setFdRestStartType(Integer fdRestStartType) {
        this.fdRestStartType = fdRestStartType;
    }

    /**
     * 午休结束时间类型
     * 1,当日
     * 2,次日
     */
    private Integer fdRestEndType;

    public Integer getFdRestEndType() {
        //兼容历史没设置过的，默认是当日
        if(fdRestEndType ==null){
            fdRestEndType =1;
        }
        return fdRestEndType;
    }

    public void setFdRestEndType(Integer fdRestEndType) {
        this.fdRestEndType = fdRestEndType;
    }

    /**
     * 总工时，单位：小时
     */
    private Float fdTotalTime;

    public Float getFdTotalTime() {
        return fdTotalTime;
    }

    public void setFdTotalTime(Float fdTotalTime) {
        this.fdTotalTime = fdTotalTime;
    }

    /**
     * 节假日
     */
    private SysTimeHoliday fdHoliday;

    public SysTimeHoliday getFdHoliday() {
        return fdHoliday;
    }

    public void setFdHoliday(SysTimeHoliday fdHoliday) {
        this.fdHoliday = fdHoliday;
    }

    /**
     * 工作时间设置，用于一周不同工作时间
     */
    private List<SysAttendCategoryTimesheet> fdTimeSheets;

    public List<SysAttendCategoryTimesheet> getFdTimeSheets() {
        return fdTimeSheets;
    }

    public void setFdTimeSheets(List<SysAttendCategoryTimesheet> fdTimeSheets) {
        this.fdTimeSheets = fdTimeSheets;
    }

    private String fdRestat;
    // 安全验证:camera:拍照打卡,face:刷脸打卡
    private String fdSecurityMode;

    public String getFdRestat() {
        return fdRestat;
    }

    public void setFdRestat(String fdRestat) {
        this.fdRestat = fdRestat;
    }

    public String getFdSecurityMode() {
        return fdSecurityMode;
    }

    public void setFdSecurityMode(String fdSecurityMode) {
        this.fdSecurityMode = fdSecurityMode;
    }

    @Override
    public String getDocSubject() {
        return this.fdName;
    }

    /**
     * 签到组是否允许组内查看
     */
    private Boolean fdIsAllowView;

    public Boolean getFdIsAllowView() {
        return fdIsAllowView;
    }

    public void setFdIsAllowView(Boolean fdIsAllowView) {
        this.fdIsAllowView = fdIsAllowView;
    }

    private Boolean fdDingClock;

    public Boolean getFdDingClock() {
        return fdDingClock;
    }

    public void setFdDingClock(Boolean fdDingClock) {
        this.fdDingClock = fdDingClock;
    }

    private Boolean fdCanMap;

    public Boolean getFdCanMap() {
        return fdCanMap;
    }

    public void setFdCanMap(Boolean fdCanMap) {
        this.fdCanMap = fdCanMap;
    }

    private Boolean fdCanWifi;

    public Boolean getFdCanWifi() {
        return fdCanWifi;
    }

    public void setFdCanWifi(Boolean fdCanWifi) {
        this.fdCanWifi = fdCanWifi;
    }

    /**
     * 是否允许补卡
     */
    private Boolean fdIsPatch;

    /**
     * 限制补卡次数
     */
    private Integer fdPatchTimes;

    /**
     * 限制补卡天数
     */
    private Integer fdPatchDay;

    public Boolean getFdIsPatch() {
        return fdIsPatch;
    }

    public void setFdIsPatch(Boolean fdIsPatch) {
        this.fdIsPatch = fdIsPatch;
    }

    public Integer getFdPatchTimes() {
        return fdPatchTimes;
    }

    public void setFdPatchTimes(Integer fdPatchTimes) {
        this.fdPatchTimes = fdPatchTimes;
    }

    public Integer getFdPatchDay() {
        return fdPatchDay;
    }

    public void setFdPatchDay(Integer fdPatchDay) {
        this.fdPatchDay = fdPatchDay;
    }

    // 加班是否扣除休息时间
    private Boolean fdIsOvertimeDeduct;

    // 加班扣除休息时间类型（ 0休息时间段 1按加班时常扣除）
    private Integer fdOvtDeductType;

    // 加班扣除休息时间对象集合
    private List<SysAttendCategoryDeduct> overtimeDeducts;

    /**
     * 是否开启加班扣除
     *
     * @return
     */
    public Boolean getFdIsOvertimeDeduct() {
        return fdIsOvertimeDeduct;
    }

    public void setFdIsOvertimeDeduct(Boolean fdIsOvertimeDeduct) {
        this.fdIsOvertimeDeduct = fdIsOvertimeDeduct;
    }

    /**
     * 扣除类型
     * 0、设置休息时间段
     * 1、按加班时长扣除
     *
     * @return
     */
    public Integer getFdOvtDeductType() {
        return fdOvtDeductType;
    }

    public void setFdOvtDeductType(Integer fdOvtDeductType) {
        this.fdOvtDeductType = fdOvtDeductType;
    }

    public List<SysAttendCategoryDeduct> getOvertimeDeducts() {
        return overtimeDeducts;
    }

    public void setOvertimeDeducts(
            List<SysAttendCategoryDeduct> overtimeDeducts) {
        this.overtimeDeducts = overtimeDeducts;
    }

    /**
     * 通知打卡结果
     */
    private Boolean fdNotifyAttend;

    public Boolean getFdNotifyAttend() {
        return fdNotifyAttend;
    }

    /**
     * 通知打卡结果
     *
     * @param fdNotifyAttend
     */
    public void setFdNotifyAttend(Boolean fdNotifyAttend) {
        this.fdNotifyAttend = fdNotifyAttend;
    }

    private static ModelToFormPropertyMap toFormPropertyMap;

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("fdManager.fdId", "fdManagerId");
            toFormPropertyMap.put("fdManager.fdName", "fdManagerName");
            toFormPropertyMap.put("fdHoliday.fdId", "fdHolidayId");
            toFormPropertyMap.put("fdHoliday.fdName", "fdHolidayName");
            toFormPropertyMap.put("fdTemplate.fdId", "fdTemplateId");
            toFormPropertyMap.put("fdTemplate.fdName", "fdTemplateName");
            toFormPropertyMap.put("fdATemplate.fdId", "fdATemplateId");
            toFormPropertyMap.put("fdATemplate.fdName", "fdATemplateName");
            toFormPropertyMap.put("fdTargets",
                    new ModelConvertor_ModelListToString(
                            "fdTargetIds:fdTargetNames", "fdId:fdName"));
            toFormPropertyMap.put("fdExcTargets",
                    new ModelConvertor_ModelListToString(
                            "fdExcTargetIds:fdExcTargetNames", "fdId:fdName"));
            toFormPropertyMap.put("fdLocations",
                    new ModelConvertor_ModelListToFormList("fdLocations"));
            toFormPropertyMap.put("fdTimes",
                    new ModelConvertor_ModelListToFormList("fdTimes"));
            toFormPropertyMap.put("fdExcTimes",
                    new ModelConvertor_ModelListToFormList("fdExcTimes"));
            toFormPropertyMap.put("fdWorkTime",
                    new ModelConvertor_ModelListToFormList("fdWorkTime"));
            toFormPropertyMap.put("fdRule",
                    new ModelConvertor_ModelListToFormList("fdRule"));
            toFormPropertyMap.put("busSetting",
                    new ModelConvertor_ModelListToFormList("busSettingForms"));
            toFormPropertyMap.put("fdWifiConfigs",
                    new ModelConvertor_ModelListToFormList("fdWifiConfigs"));
            toFormPropertyMap.put("fdTimeSheets",
                    new ModelConvertor_ModelListToFormList("fdTimeSheets"));

            toFormPropertyMap.put("overtimeDeducts",
                    new ModelConvertor_ModelListToFormList("overtimeDeducts"));

        }
        return toFormPropertyMap;
    }
    /**
     * 原状态
     */
    private Integer fdOldStatusFlag;

    public Integer getFdOldStatusFlag() {
        return fdOldStatusFlag;
    }

    public void setFdOldStatusFlag(Integer fdOldStatusFlag) {
        this.fdOldStatusFlag = fdOldStatusFlag;
    }

    /**
     * 新版本
     */
    private String fdStatusFlag;

    public String getFdStatusFlag() {
        return fdStatusFlag;
    }

    public void setFdStatusFlag(String fdStatusFlag) {
        this.fdStatusFlag = fdStatusFlag;
    }

    /**
     * 加班时长转换成天的计算
     */
    private Float fdConvertOverTimeHour;

    public Float getFdConvertOverTimeHour() {
        return fdConvertOverTimeHour;
    }

    public void setFdConvertOverTimeHour(Float fdConvertOverTimeHour) {
        this.fdConvertOverTimeHour = fdConvertOverTimeHour;
    }

    /**
     * 上班前计算加班。默认值不计算
     */
    private Boolean fdBeforeWorkOverTime;

    public Boolean getFdBeforeWorkOverTime() {
        if(fdBeforeWorkOverTime ==null){
            return Boolean.FALSE;
        }
        return fdBeforeWorkOverTime;
    }

    public void setFdBeforeWorkOverTime(Boolean fdBeforWorkOverTime) {
        this.fdBeforeWorkOverTime = fdBeforWorkOverTime;
    }

    /**
     * 多班次休息间隔是否统计工时
     */
    private Boolean fdIsCalculateOvertime;

    public Boolean getFdIsCalculateOvertime() {
        if(fdIsCalculateOvertime ==null){
            return Boolean.FALSE;
        }
        return fdIsCalculateOvertime;
    }

    public void setFdIsCalculateOvertime(Boolean fdIsCalculateOvertime) {
        this.fdIsCalculateOvertime = fdIsCalculateOvertime;
    }
    /**
     * 最小加班时长
     * 单位为分钟
     */
    private Integer fdMinOverTime;

    public Integer getFdMinOverTime() {
        return fdMinOverTime;
    }

    public void setFdMinOverTime(Integer fdMinOverTime) {
        this.fdMinOverTime = fdMinOverTime;
    }

	public Integer getFdLateTotalTime() {
		return fdLateTotalTime;
	}

	public void setFdLateTotalTime(Integer fdLateTotalTime) {
		this.fdLateTotalTime = fdLateTotalTime;
	}

	public Integer getFdLateNumTotalTime() {
		return fdLateNumTotalTime;
	}

	public void setFdLateNumTotalTime(Integer fdLateNumTotalTime) {
		this.fdLateNumTotalTime = fdLateNumTotalTime;
	}
    
    
}
