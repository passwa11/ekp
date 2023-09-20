package com.landray.kmss.sys.attend.forms;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryATemplate;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTemplate;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.model.SysTimeHoliday;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


/**
 * 签到事项 Form
 *
 * @author
 * @version 1.0 2017-05-24
 */
public class SysAttendCategoryForm extends SysAttendImportForm {

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
    private String fdOrder;

    /**
     * @return 排序号
     */
    public String getFdOrder() {
        return this.fdOrder;
    }

    /**
     * @param fdOrder 排序号
     */
    public void setFdOrder(String fdOrder) {
        this.fdOrder = fdOrder;
    }

    /**
     * 第一班次：最早打卡
     */
    private String fdStartTime;

    public String getFdStartTime() {
        return this.fdStartTime;
    }

    public void setFdStartTime(String fdStartTime) {
        this.fdStartTime = fdStartTime;
    }

    /**
     * 第一班次：最晚打卡
     */
    private String fdEndTime1;

    public String getFdEndTime1() {
        return fdEndTime1;
    }

    public void setFdEndTime1(String fdEndTime1) {
        this.fdEndTime1 = fdEndTime1;
    }

    /**
     * 第二班次：最早打卡
     */
    private String fdStartTime2;

    public String getFdStartTime2() {
        return fdStartTime2;
    }

    public void setFdStartTime2(String fdStartTime2) {
        this.fdStartTime2 = fdStartTime2;
    }

    /**
     * 第二班次：最晚打卡
     */
    private String fdEndTime;

    public String getFdEndTime() {
        return this.fdEndTime;
    }

    public void setFdEndTime(String fdEndTime) {
        this.fdEndTime = fdEndTime;
    }

    public String getFdEndTime2() {
        return getFdEndTime();
    }

    public void setFdEndTime2(String fdEndTime2) {
        setFdEndTime(fdEndTime2);
    }

    /**
     * 关闭打卡日期，当天还是第二天
     */
    private String fdEndDay;

    public String getFdEndDay() {
        return fdEndDay;
    }

    public void setFdEndDay(String fdEndDay) {
        this.fdEndDay = fdEndDay;
    }

    /**
     * 综合工时 开始时间
     */
    private String fdStartTimeComprehensive;
    /**
     * 综合工时 结束时间
     */
    private String fdEndTimeComprehensive;

    public String getFdStartTimeComprehensive() {
        return fdStartTimeComprehensive;
    }

    public void setFdStartTimeComprehensive(String fdStartTimeComprehensive) {
        this.fdStartTimeComprehensive = fdStartTimeComprehensive;
    }

    public String getFdEndTimeComprehensive() {
        return fdEndTimeComprehensive;
    }

    public void setFdEndTimeComprehensive(String fdEndTimeComprehensive) {
        this.fdEndTimeComprehensive = fdEndTimeComprehensive;
    }

    /**
     * 签到类型
     */
    private String fdType;

    /**
     * @return 签到类型
     */
    public String getFdType() {
        return this.fdType;
    }

    /**
     * @param fdType 签到类型
     */
    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

    /**
     * 签到周期类型
     */
    private String fdPeriodType;

    /**
     * @return 签到周期类型
     */
    public String getFdPeriodType() {
        return this.fdPeriodType;
    }

    /**
     * @param fdPeriodType 签到周期类型
     */
    public void setFdPeriodType(String fdPeriodType) {
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
     * @return 星期名
     */
    public String getFdWeekNames() {
        String fdWeekNames = "";
        if (StringUtil.isNotNull(fdWeek)) {
            String[] weekArr = fdWeek.split("[,;]");
            if (weekArr != null) {
                List<String> weekList = ArrayUtil.convertArrayToList(weekArr);
                Collections.sort(weekList, new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        try {
                            if (StringUtil.isNotNull(s1)
                                    && StringUtil.isNotNull(s2)) {
                                return Integer.valueOf(s1) - Integer
                                        .valueOf(s2);
                            } else {
                                return 0;
                            }
                        } catch (Exception e) {
                            return 0;
                        }
                    }
                });
                for (String week : weekList) {
                    if (StringUtil.isNotNull(week)) {
                        try {
                            String weekName = EnumerationTypeUtil
                                    .getColumnEnumsLabel(
                                            "sysAttendCategory_fdWeek", week);
                            fdWeekNames += (StringUtil.isNull(fdWeekNames) ? ""
                                    : "、") + weekName;
                        } catch (Exception e) {
                            continue;
                        }
                    }
                }
            }
        }
        return fdWeekNames;
    }

    /**
     * @param fdWeek 签到周期
     */
    public void setFdWeek(String fdWeek) {
        this.fdWeek = fdWeek;
    }

    /**
     * 应用
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
    private String fdStatus;

    /**
     * @return 状态
     */
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * @param fdStatus 状态
     */
    public void setFdStatus(String fdStatus) {
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
    private String docAlterTime;

    /**
     * @return 最后修改时间
     */
    public String getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * @param docAlterTime 最后修改时间
     */
    public void setDocAlterTime(String docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    /**
     * 创建时间
     */
    private String docCreateTime;

    /**
     * @return 创建时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * @param docCreateTime 创建时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 考勤班制
     */
    private String fdWork;

    /**
     * @return 考勤班制
     */
    public String getFdWork() {
        return this.fdWork;
    }

    /**
     * @param fdWork 考勤班制
     */
    public void setFdWork(String fdWork) {
        this.fdWork = fdWork;
    }

    /**
     * 修改人的ID
     */
    private String docAlterorId;

    /**
     * @return 修改人的ID
     */
    public String getDocAlterorId() {
        return this.docAlterorId;
    }

    /**
     * @param docAlterorId 修改人的ID
     */
    public void setDocAlterorId(String docAlterorId) {
        this.docAlterorId = docAlterorId;
    }

    /**
     * 修改人的名称
     */
    private String docAlterorName;

    /**
     * @return 修改人的名称
     */
    public String getDocAlterorName() {
        return this.docAlterorName;
    }

    /**
     * @param docAlterorName 修改人的名称
     */
    public void setDocAlterorName(String docAlterorName) {
        this.docAlterorName = docAlterorName;
    }

    /**
     * 创建者的ID
     */
    private String docCreatorId;

    /**
     * @return 创建者的ID
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * @param docCreatorId 创建者的ID
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 创建者的名称
     */
    private String docCreatorName;

    /**
     * @return 创建者的名称
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * @param docCreatorName 创建者的名称
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    /**
     * 负责人的ID
     */
    private String fdManagerId;

    public String getFdManagerId() {
        return fdManagerId;
    }

    public void setFdManagerId(String fdManagerId) {
        this.fdManagerId = fdManagerId;
    }

    public String getFdManagerName() {
        return fdManagerName;
    }

    public void setFdManagerName(String fdManagerName) {
        this.fdManagerName = fdManagerName;
    }

    /**
     * 负责人的名称
     */
    private String fdManagerName;

    /**
     * 签到对象的ID列表
     */
    private String fdTargetIds;

    /**
     * @return 签到对象的ID列表
     */
    public String getFdTargetIds() {
        return this.fdTargetIds;
    }

    /**
     * @param fdTargetIds 签到对象的ID列表
     */
    public void setFdTargetIds(String fdTargetIds) {
        this.fdTargetIds = fdTargetIds;
    }

    /**
     * 签到对象的名称列表
     */
    private String fdTargetNames;

    /**
     * @return 签到对象的名称列表
     */
    public String getFdTargetNames() {
        return this.fdTargetNames;
    }

    /**
     * @param fdTargetNames 签到对象的名称列表
     */
    public void setFdTargetNames(String fdTargetNames) {
        this.fdTargetNames = fdTargetNames;
    }

    /**
     * 例外对象的ID列表
     */
    private String fdExcTargetIds;

    /**
     * @return 例外对象的ID列表
     */
    public String getFdExcTargetIds() {
        return this.fdExcTargetIds;
    }

    /**
     * @param fdExcTargetIds 例外对象的ID列表
     */
    public void setFdExcTargetIds(String fdExcTargetIds) {
        this.fdExcTargetIds = fdExcTargetIds;
    }

    /**
     * 例外对象的名称列表
     */
    private String fdExcTargetNames;

    /**
     * @return 例外对象的名称列表
     */
    public String getFdExcTargetNames() {
        return this.fdExcTargetNames;
    }

    /**
     * @param fdExcTargetNames 例外对象的名称列表
     */
    public void setFdExcTargetNames(String fdExcTargetNames) {
        this.fdExcTargetNames = fdExcTargetNames;
    }

    /**
     * 签到点
     */
    private AutoArrayList<SysAttendCategoryLocationForm> fdLocations = new AutoArrayList(
            SysAttendCategoryLocationForm.class);

    public AutoArrayList<SysAttendCategoryLocationForm> getFdLocations() {
        return fdLocations;
    }

    public void setFdLocations(AutoArrayList<SysAttendCategoryLocationForm> fdLocations) {
        this.fdLocations = fdLocations;
    }

    /**
     * 考勤班次
     */
    private AutoArrayList<SysAttendCategoryWorktimeForm> fdWorkTime = new AutoArrayList(
            SysAttendCategoryWorktimeForm.class);

    public AutoArrayList<SysAttendCategoryWorktimeForm> getFdWorkTime() {
        return fdWorkTime;
    }

    public void setFdWorkTime(AutoArrayList<SysAttendCategoryWorktimeForm> fdWorkTime) {
        this.fdWorkTime = fdWorkTime;
    }

    /**
     * 追加日期/自定义日期
     */
    private AutoArrayList<SysAttendCategoryTimeForm> fdTimes = new AutoArrayList(
            SysAttendCategoryTimeForm.class);

    @SuppressWarnings("unchecked")
    public AutoArrayList<SysAttendCategoryTimeForm> getFdTimes() {
        if (fdTimes != null && !fdTimes.isEmpty()) {
            Collections.sort(fdTimes,
                    new Comparator<SysAttendCategoryTimeForm>() {
                        @Override
                        public int compare(SysAttendCategoryTimeForm form1,
                                           SysAttendCategoryTimeForm form2) {
                            if (form1 != null && form2 != null
                                    && StringUtil.isNotNull(form1.getFdTime())
                                    && StringUtil
                                    .isNotNull(form2.getFdTime())) {
                                Date date1 = DateUtil.convertStringToDate(
                                        form1.getFdTime(),
                                        DateUtil.TYPE_DATETIME, null);
                                Date date2 = DateUtil.convertStringToDate(
                                        form2.getFdTime(),
                                        DateUtil.TYPE_DATETIME, null);
                                if (date1 != null && date2 != null) {
                                    return date1.compareTo(date2);
                                }
                            }
                            return 0;
                        }
                    });
        }
        return fdTimes;
    }

    public void setFdTimes(AutoArrayList<SysAttendCategoryTimeForm> fdTimes) {
        this.fdTimes = fdTimes;
    }

    /**
     * 追加日期，用于日期多选控件
     */
    public String getFdTimesStr() {
        String timeStr = "";
        AutoArrayList<SysAttendCategoryTimeForm> fdTimes = getFdTimes();
        if (fdTimes != null && !fdTimes.isEmpty()) {
            for (int i = 0; i < fdTimes.size(); i++) {
                SysAttendCategoryTimeForm timeForm = (SysAttendCategoryTimeForm) fdTimes
                        .get(i);
                if (StringUtil.isNotNull(timeForm.getFdTime())) {
                    String prefix = StringUtil.isNull(timeStr) ? "" : ",";
                    String formatTime = DateUtil.convertDateToString(
                            DateUtil.convertStringToDate(timeForm.getFdTime(),
                                    DateUtil.TYPE_DATETIME, null),
                            DateUtil.TYPE_DATE, null);
                    timeStr += prefix + formatTime;
                }
            }
        }
        return timeStr;
    }

    public void setFdTimesStr(String fdTimesStr) {
        AutoArrayList<SysAttendCategoryTimeForm> fdTimes = new AutoArrayList(
                SysAttendCategoryTimeForm.class);
        if (StringUtil.isNotNull(fdTimesStr)) {
            String[] timeArr = fdTimesStr.split("[,;]");
            for (int i = 0; i < timeArr.length; i++) {
                if (StringUtil.isNotNull(timeArr[i])) {
                    SysAttendCategoryTimeForm timeForm = new SysAttendCategoryTimeForm();
                    timeForm.setFdId(IDGenerator.generateID());
                    timeForm.setFdTime(timeArr[i]);
                    fdTimes.add(timeForm);
                }
            }
        }
        setFdTimes(fdTimes);
    }

    /**
     * 自定义日期，用于日期多选控件
     */
    public String getFdCustomDateStr() {
        return getFdTimesStr();
    }

    public void setFdCustomDateStr(String fdCustomDateStr) {
        setFdTimesStr(fdCustomDateStr);
    }

    /**
     * 排除日期
     */
    private AutoArrayList<SysAttendCategoryExctimeForm> fdExcTimes = new AutoArrayList(
            SysAttendCategoryExctimeForm.class);

    @SuppressWarnings("unchecked")
    public AutoArrayList<SysAttendCategoryExctimeForm> getFdExcTimes() {
        if (fdExcTimes != null && !fdExcTimes.isEmpty()) {
            Collections.sort(fdExcTimes,
                    new Comparator<SysAttendCategoryExctimeForm>() {
                        @Override
                        public int compare(SysAttendCategoryExctimeForm form1,
                                           SysAttendCategoryExctimeForm form2) {
                            if (form1 != null && form2 != null
                                    && StringUtil
                                    .isNotNull(form1.getFdExcTime())
                                    && StringUtil
                                    .isNotNull(form2.getFdExcTime())) {
                                Date date1 = DateUtil.convertStringToDate(
                                        form1.getFdExcTime(),
                                        DateUtil.TYPE_DATETIME, null);
                                Date date2 = DateUtil.convertStringToDate(
                                        form2.getFdExcTime(),
                                        DateUtil.TYPE_DATETIME, null);
                                if (date1 != null && date2 != null) {
                                    return date1.compareTo(date2);
                                }
                            }
                            return 0;
                        }
                    });
        }
        return fdExcTimes;
    }

    public void setFdExcTimes(AutoArrayList<SysAttendCategoryExctimeForm> fdExcTimes) {
        this.fdExcTimes = fdExcTimes;
    }

    /**
     * 排除日期，用于日期多选控件
     */
    public String getFdExcTimesStr() {
        String timeStr = "";
        AutoArrayList<SysAttendCategoryExctimeForm> fdExcTimes = getFdExcTimes();
        if (fdExcTimes != null && !fdExcTimes.isEmpty()) {
            for (int i = 0; i < fdExcTimes.size(); i++) {
                SysAttendCategoryExctimeForm timeForm = (SysAttendCategoryExctimeForm) fdExcTimes
                        .get(i);
                if (StringUtil.isNotNull(timeForm.getFdExcTime())) {
                    String prefix = StringUtil.isNull(timeStr) ? "" : ",";
                    String formatTime = DateUtil.convertDateToString(
                            DateUtil.convertStringToDate(
                                    timeForm.getFdExcTime(),
                                    DateUtil.TYPE_DATETIME, null),
                            DateUtil.TYPE_DATE, null);
                    timeStr += prefix + formatTime;
                }
            }
        }
        return timeStr;
    }

    public void setFdExcTimesStr(String fdExcTimesStr) {
        AutoArrayList<SysAttendCategoryExctimeForm> fdExcTimes = new AutoArrayList(
                SysAttendCategoryExctimeForm.class);
        if (StringUtil.isNotNull(fdExcTimesStr)) {
            String[] timeArr = fdExcTimesStr.split("[,;]");
            for (int i = 0; i < timeArr.length; i++) {
                if (StringUtil.isNotNull(timeArr[i])) {
                    SysAttendCategoryExctimeForm timeForm = new SysAttendCategoryExctimeForm();
                    timeForm.setFdId(IDGenerator.generateID());
                    timeForm.setFdExcTime(timeArr[i]);
                    fdExcTimes.add(timeForm);
                }
            }
        }
        setFdExcTimes(fdExcTimes);
    }

    /**
     * 签到规则
     */
    private AutoArrayList<SysAttendCategoryRuleForm> fdRule = new AutoArrayList(
            SysAttendCategoryRuleForm.class);

    private AutoArrayList<SysAttendCategoryBusinessForm> busSettingForms = new AutoArrayList(
            SysAttendCategoryBusinessForm.class);

    /**
     * wifi列表
     */
    private AutoArrayList<SysAttendCategoryWifiForm> fdWifiConfigs = new AutoArrayList(
            SysAttendCategoryWifiForm.class);

    public AutoArrayList<SysAttendCategoryRuleForm> getFdRule() {
        return fdRule;
    }

    public void setFdRule(AutoArrayList<SysAttendCategoryRuleForm> fdRule) {
        this.fdRule = fdRule;
    }

    public AutoArrayList<SysAttendCategoryBusinessForm> getBusSettingForms() {
        return busSettingForms;
    }

    public void setBusSettingForms(AutoArrayList<SysAttendCategoryBusinessForm> busSettingForms) {
        this.busSettingForms = busSettingForms;
    }

    public AutoArrayList<SysAttendCategoryWifiForm> getFdWifiConfigs() {
        return fdWifiConfigs;
    }

    public void setFdWifiConfigs(AutoArrayList<SysAttendCategoryWifiForm> fdWifiConfigs) {
        this.fdWifiConfigs = fdWifiConfigs;
    }

    private String fdNotifyOnTime;

    public String getFdNotifyOnTime() {
        return fdNotifyOnTime;
    }

    public void setFdNotifyOnTime(String fdNotifyOnTime) {
        this.fdNotifyOnTime = fdNotifyOnTime;
    }

    private String fdNotifyOffTime;

    public String getFdNotifyOffTime() {
        return fdNotifyOffTime;
    }

    public void setFdNotifyOffTime(String fdNotifyOffTime) {
        this.fdNotifyOffTime = fdNotifyOffTime;
    }

    private String fdNotifyResult;

    public String getFdNotifyResult() {
        return fdNotifyResult;
    }

    public void setFdNotifyResult(String fdNotifyResult) {
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
    // 最小加班时长
    private String fdMinHour;

    // 统计取整规则
    private Integer fdRoundingType;

    public Integer getFdRoundingType() {
        return fdRoundingType;
    }

    public void setFdRoundingType(Integer fdRoundingType) {
        this.fdRoundingType = fdRoundingType;
    }

    // 加班统计最小单位(小时)
    private String fdMinUnitHour;

    public String getFdMinUnitHour() {
        if (StringUtil.isNotNull(fdMinUnitHour)) {
            fdMinUnitHour = com.landray.kmss.util.NumberUtil
                    .roundDecimal(Float.valueOf(fdMinUnitHour));
        }
        return fdMinUnitHour;
    }

    public void setFdMinUnitHour(String fdMinUnitHour) {
        this.fdMinUnitHour = fdMinUnitHour;
    }

    private String fdUnlimitTarget;
    private String fdQRCodeTime;

    public String getFdQRCodeTime() {
        return fdQRCodeTime;
    }

    public void setFdQRCodeTime(String fdQRCodeTime) {
        this.fdQRCodeTime = fdQRCodeTime;
    }

    public String getFdMinHour() {
        if (StringUtil.isNotNull(fdMinHour)) {
            fdMinHour = com.landray.kmss.util.NumberUtil
                    .roundDecimal(Float.valueOf(fdMinHour));
        }
        return fdMinHour;
    }

    public void setFdMinHour(String fdMinHour) {
        this.fdMinHour = fdMinHour;
    }


    public String getFdUnlimitTarget() {
        return fdUnlimitTarget;
    }

    public void setFdUnlimitTarget(String fdUnlimitTarget) {
        this.fdUnlimitTarget = fdUnlimitTarget;
    }

    private String fdUnlimitOuter;

    public String getFdUnlimitOuter() {
        return fdUnlimitOuter;
    }

    public void setFdUnlimitOuter(String fdUnlimitOuter) {
        this.fdUnlimitOuter = fdUnlimitOuter;
    }

    private String fdTemplateId;

    public String getFdTemplateId() {
        return fdTemplateId;
    }

    public void setFdTemplateId(String fdTemplateId) {
        this.fdTemplateId = fdTemplateId;
    }

    private String fdTemplateName;

    public String getFdTemplateName() {
        return fdTemplateName;
    }

    public void setFdTemplateName(String fdTemplateName) {
        this.fdTemplateName = fdTemplateName;
    }

    private String fdATemplateId;

    public String getFdATemplateId() {
        return fdATemplateId;
    }

    public void setFdATemplateId(String fdATemplateId) {
        this.fdATemplateId = fdATemplateId;
    }

    private String fdATemplateName;

    public String getFdATemplateName() {
        return fdATemplateName;
    }

    public void setFdATemplateName(String fdATemplateName) {
        this.fdATemplateName = fdATemplateName;
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

    @Override
    public String getAuthReaderNoteFlag() {
        return "2";
    }

    /**
     * 是否弹性上下班
     */
    private String fdIsFlex;

    /**
     * 弹性上下班时间
     */
    private String fdFlexTime;

    public String getFdIsFlex() {
        return fdIsFlex;
    }

    public void setFdIsFlex(String fdIsFlex) {
        this.fdIsFlex = fdIsFlex;
    }

    public String getFdFlexTime() {
        return fdFlexTime;
    }

    public void setFdFlexTime(String fdFlexTime) {
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
     * 迟到多少分钟算旷工一天
     */
    private Integer fdLeftToFullAbsTime;
    /**
     * 生效日期
     */
    private String fdEffectTime;

    public Integer getFdLateToAbsentTime() {
        return fdLateToAbsentTime;
    }

    public void setFdLateToAbsentTime(Integer fdLateToAbsentTime) {
        this.fdLateToAbsentTime = fdLateToAbsentTime;
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

    private String fdOsdReviewType;

    public String getFdOsdReviewType() {
        return StringUtil.isNull(fdOsdReviewType) ? "0" : fdOsdReviewType;
    }

    public void setFdOsdReviewType(String fdOsdReviewType) {
        this.fdOsdReviewType = fdOsdReviewType;
    }

    //
    private String fdOsdReviewIsUpload;

    public String getFdOsdReviewIsUpload() {
        return StringUtil.isNull(fdOsdReviewIsUpload) ? "0"
                : fdOsdReviewIsUpload;
    }

    public void setFdOsdReviewIsUpload(String fdOsdReviewIsUpload) {
        this.fdOsdReviewIsUpload = fdOsdReviewIsUpload;
    }

    public String getFdEffectTime() {
        if (StringUtil.isNull(fdEffectTime)) {
            return this.docCreateTime;
        }
        return fdEffectTime;
    }

    public void setFdEffectTime(String fdEffectTime) {
        this.fdEffectTime = fdEffectTime;
    }

    /**
     * 班制类型。0固定班制，1排版，2自定义, 3 综合工时 4 不定时工作制
     */
    private String fdShiftType;

    public String getFdShiftType() {
        return fdShiftType;
    }

    public void setFdShiftType(String fdShiftType) {
        this.fdShiftType = fdShiftType;
    }

    /**
     * 一周内相同工作时间。0相同，1不相同
     */
    private String fdSameWorkTime;

    public String getFdSameWorkTime() {
        return fdSameWorkTime;
    }

    public void setFdSameWorkTime(String fdSameWorkTime) {
        this.fdSameWorkTime = fdSameWorkTime;
    }

    /**
     * 午休开始时间
     */
    private String fdRestStartTime;

    public String getFdRestStartTime() {
        return fdRestStartTime;
    }

    public void setFdRestStartTime(String fdRestStartTime) {
        this.fdRestStartTime = fdRestStartTime;
    }

    /**
     * 午休结束时间
     */
    private String fdRestEndTime;

    public String getFdRestEndTime() {
        return fdRestEndTime;
    }

    public void setFdRestEndTime(String fdRestEndTime) {
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
     * 总工时
     */
    private String fdTotalTime;

    public String getFdTotalTime() {
        if (StringUtil.isNotNull(fdTotalTime)) {
            try {
                DecimalFormat df = new DecimalFormat("#.##");
                return df.format(Float.parseFloat(fdTotalTime));
            } catch (Exception e) {
                return fdTotalTime;
            }
        }
        return fdTotalTime;
    }

    public void setFdTotalTime(String fdTotalTime) {
        this.fdTotalTime = fdTotalTime;
    }

    private String fdHolidayId = null;
    private String fdHolidayName = null;

    public String getFdHolidayId() {
        return fdHolidayId;
    }

    public void setFdHolidayId(String fdHolidayId) {
        this.fdHolidayId = fdHolidayId;
    }

    public String getFdHolidayName() {
        return fdHolidayName;
    }

    public void setFdHolidayName(String fdHolidayName) {
        this.fdHolidayName = fdHolidayName;
    }

    /**
     * 工作时间设置，用于一周不同工作时间
     */
    private AutoArrayList<SysAttendCategoryTimesheetForm> fdTimeSheets = new AutoArrayList(
            SysAttendCategoryTimesheetForm.class);

    public AutoArrayList<SysAttendCategoryTimesheetForm> getFdTimeSheets() {
        return fdTimeSheets;
    }

    public void setFdTimeSheets(AutoArrayList<SysAttendCategoryTimesheetForm> fdTimeSheets) {
        this.fdTimeSheets = fdTimeSheets;
    }

    // 是否重新统计当天数据
    private String fdRestat;
    private String fdSecurityMode;
    private String fdAreaStartTime;// 排班最早打卡时间
    private String fdAreaEndTime;// 排班最晚打卡时间

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

    public String getFdAreaStartTime() {
        return this.getFdStartTime();
    }

    public String getFdAreaEndTime() {
        return this.getFdEndTime();
    }

    private String fdIsAllowView;

    public String getFdIsAllowView() {
        return fdIsAllowView;
    }

    public void setFdIsAllowView(String fdIsAllowView) {
        this.fdIsAllowView = fdIsAllowView;
    }

    private String fdDingClock;

    public String getFdDingClock() {
        return fdDingClock;
    }

    public void setFdDingClock(String fdDingClock) {
        this.fdDingClock = fdDingClock;
    }

    private String fdCanMap;

    public String getFdCanMap() {
        return fdCanMap;
    }

    public void setFdCanMap(String fdCanMap) {
        this.fdCanMap = fdCanMap;
    }

    private String fdCanWifi;

    public String getFdCanWifi() {
        return fdCanWifi;
    }

    public void setFdCanWifi(String fdCanWifi) {
        this.fdCanWifi = fdCanWifi;
    }

    /**
     * 是否允许补卡
     */
    private String fdIsPatch;

    /**
     * 限制补卡次数
     */
    private String fdPatchTimes;

    /**
     * 限制补卡天数
     */
    private String fdPatchDay;

    public String getFdIsPatch() {
        return fdIsPatch;
    }

    public void setFdIsPatch(String fdIsPatch) {
        this.fdIsPatch = fdIsPatch;
    }

    public String getFdPatchTimes() {
        return fdPatchTimes;
    }

    public void setFdPatchTimes(String fdPatchTimes) {
        this.fdPatchTimes = fdPatchTimes;
    }

    public String getFdPatchDay() {
        return fdPatchDay;
    }

    public void setFdPatchDay(String fdPatchDay) {
        this.fdPatchDay = fdPatchDay;
    }

    // 加班是否扣除休息时间
    private Boolean fdIsOvertimeDeduct;

    // 加班扣除休息时间类型（ 0休息时间段 1按加班时常扣除）
    private Integer fdOvtDeductType;

    private AutoArrayList<SysAttendCategoryDeductForm> overtimeDeducts = new AutoArrayList(
            SysAttendCategoryDeductForm.class);

    public Boolean getFdIsOvertimeDeduct() {
        return fdIsOvertimeDeduct;
    }

    public void setFdIsOvertimeDeduct(Boolean fdIsOvertimeDeduct) {
        this.fdIsOvertimeDeduct = fdIsOvertimeDeduct;
    }

    public Integer getFdOvtDeductType() {
        return fdOvtDeductType;
    }

    public void setFdOvtDeductType(Integer fdOvtDeductType) {
        this.fdOvtDeductType = fdOvtDeductType;
    }

    public AutoArrayList<SysAttendCategoryDeductForm> getOvertimeDeducts() {
        return overtimeDeducts;
    }

    public void setOvertimeDeducts(AutoArrayList<SysAttendCategoryDeductForm> overtimeDeducts) {
        this.overtimeDeducts = overtimeDeducts;
    }

    /**
     * 通知打卡结果
     */
    private String fdNotifyAttend;

    public String getFdNotifyAttend() {
        return fdNotifyAttend;
    }

    /**
     * 通知打卡结果
     *
     * @param fdNotifyAttend
     */
    public void setFdNotifyAttend(String fdNotifyAttend) {
        this.fdNotifyAttend = fdNotifyAttend;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdOrder = null;
        fdStartTime = null;
        fdEndTime = null;
        fdType = null;
        fdPeriodType = null;
        fdWeek = null;
        fdAppId = null;
        fdAppName = null;
        fdStatus = null;
        fdAppUrl = null;
        docAlterTime = null;
        docCreateTime = null;
        fdWork = null;
        docAlterorId = null;
        docAlterorName = null;
        docCreatorId = null;
        docCreatorName = null;
        fdManagerId = null;
        fdManagerName = null;
        fdTargetIds = null;
        fdTargetNames = null;
        fdExcTargetIds = null;
        fdExcTargetNames = null;
        fdNotifyOnTime = null;
        fdNotifyOffTime = null;
        fdNotifyResult = null;
        fdPermState = null;
        fdMinHour = null;
        fdMinUnitHour = null;
        fdRoundingType = null;
        fdTemplateId = null;
        fdTemplateName = null;
        fdATemplateId = null;
        fdATemplateName = null;
        fdUnlimitTarget = null;
        fdUnlimitOuter = null;
        fdQRCodeTime = null;
        fdEndDay = null;
        fdIsOvertime = null;
        fdOvtReviewType = null;
        fdIsFlex = null;
        fdFlexTime = null;
        fdLateTotalTime=null;
        fdLateNumTotalTime=null;
        fdLateToAbsentTime = null;
        fdLeftToAbsentTime = null;
        fdLateToFullAbsTime = null;
        fdLeftToFullAbsTime = null;
        fdOsdReviewType = null;
        fdOsdReviewIsUpload = null;
        fdShiftType = null;
        fdSameWorkTime = null;
        fdEndTime1 = null;
        fdStartTime2 = null;
        fdRestStartTime = null;
        fdRestEndTime = null;
        fdTotalTime = null;
        fdIsAllowView = null;
        docStatus = SysDocConstant.DOC_STATUS_PUBLISH;
        fdIsPatch = null;
        fdPatchTimes = null;
        fdPatchDay = null;
        fdConvertOverTimeHour =null;
        fdLocations = new AutoArrayList(
                SysAttendCategoryLocationForm.class);
        fdWorkTime = new AutoArrayList(
                SysAttendCategoryWorktimeForm.class);
        fdTimes = new AutoArrayList(
                SysAttendCategoryTimeForm.class);
        fdExcTimes = new AutoArrayList(
                SysAttendCategoryExctimeForm.class);
        fdRule = new AutoArrayList(
                SysAttendCategoryRuleForm.class);
        busSettingForms = new AutoArrayList(
                SysAttendCategoryBusinessForm.class);
        fdWifiConfigs = new AutoArrayList(
                SysAttendCategoryWifiForm.class);
        fdEffectTime = null;
        fdTimeSheets = new AutoArrayList(
                SysAttendCategoryTimesheetForm.class);

        fdRestat = null;
        fdSecurityMode = null;

        fdDingClock = null;
        fdCanMap = null;
        fdCanWifi = null;

        fdIsOvertimeDeduct = null;
        fdOvtDeductType = null;
        overtimeDeducts = new AutoArrayList(
                SysAttendCategoryDeductForm.class);
        fdNotifyAttend = null;
        fdIsCalculateOvertime = Boolean.FALSE;
        fdOldStatusFlag =null;
        fdBeforeWorkOverTime = Boolean.FALSE;
        fdRestEndType =1;
        fdRestStartType =1;
        super.reset(mapping, request);
    }

    @Override
    public Class<SysAttendCategory> getModelClass() {
        return SysAttendCategory.class;
    }

    private static FormToModelPropertyMap toModelPropertyMap;

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("docAlterorId",
                    new FormConvertor_IDToModel("docAlteror",
                            SysOrgElement.class));
            toModelPropertyMap.put("docCreatorId",
                    new FormConvertor_IDToModel("docCreator",
                            SysOrgPerson.class));
            toModelPropertyMap.put("fdManagerId",
                    new FormConvertor_IDToModel("fdManager",
                            SysOrgElement.class));
            toModelPropertyMap.put("fdHolidayId", new FormConvertor_IDToModel(
                    "fdHoliday", SysTimeHoliday.class));
            toModelPropertyMap.put("fdTemplateId", new FormConvertor_IDToModel(
                    "fdTemplate", SysAttendCategoryTemplate.class));
            toModelPropertyMap.put("fdATemplateId", new FormConvertor_IDToModel(
                    "fdATemplate", SysAttendCategoryATemplate.class));
            toModelPropertyMap.put("fdTargetIds", new FormConvertor_IDsToModelList(
                    "fdTargets", SysOrgElement.class));
            toModelPropertyMap.put("fdExcTargetIds", new FormConvertor_IDsToModelList(
                    "fdExcTargets", SysOrgElement.class));
            toModelPropertyMap.put("fdLocations",
                    new FormConvertor_FormListToModelList("fdLocations",
                            "fdCategory"));
            toModelPropertyMap.put("fdWorkTime",
                    new FormConvertor_FormListToModelList("fdWorkTime",
                            "fdCategory"));
            toModelPropertyMap.put("fdTimes",
                    new FormConvertor_FormListToModelList("fdTimes",
                            "fdCategory"));
            toModelPropertyMap.put("fdExcTimes",
                    new FormConvertor_FormListToModelList("fdExcTimes",
                            "fdCategory"));
            toModelPropertyMap.put("fdRule",
                    new FormConvertor_FormListToModelList("fdRule",
                            "fdCategory"));
            toModelPropertyMap.put("busSettingForms",
                    new FormConvertor_FormListToModelList("busSetting",
                            "fdCategory"));
            toModelPropertyMap.put("fdWifiConfigs",
                    new FormConvertor_FormListToModelList("fdWifiConfigs",
                            "fdCategory"));
            toModelPropertyMap.put("fdTimeSheets",
                    new FormConvertor_FormListToModelList("fdTimeSheets",
                            "fdCategory"));

            toModelPropertyMap.put("overtimeDeducts",
                    new FormConvertor_FormListToModelList("overtimeDeducts",
                            "fdCategory"));

        }
        return toModelPropertyMap;
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


}
