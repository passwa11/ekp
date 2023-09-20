package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendReportMonthForm;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaModel;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import net.sf.json.JSONObject;

import java.util.Date;

public class SysAttendReportMonth extends BaseModel implements ISysAuthAreaModel {

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
	 * 月份
	 */
	private Date fdMonth = new Date();

	public Date getFdMonth() {
		return fdMonth;
	}

	public void setFdMonth(Date fdMonth) {
		this.fdMonth = fdMonth;
	}

	/**
	 * 考勤组ID
	 */
	private String fdCategoryId;

	/**
	 * @return 考勤组ID
	 */
	public String getFdCategoryId() {
		return this.fdCategoryId;
	}

	/**
	 * @param fdCategoryId
	 *            考勤组ID
	 */
	public void setFdCategoryId(String fdCategoryId) {
		this.fdCategoryId = fdCategoryId;
	}

	/**
	 * 考勤组名称
	 */
	private String fdCategoryName;

	public String getFdCategoryName() {
		return fdCategoryName;
	}

	public void setFdCategoryName(String fdCategoryName) {
		this.fdCategoryName = fdCategoryName;
	}

	/**
	 * 总工时
	 */
	private Long fdTotalTime;

	/**
	 * @return 总工时
	 */
	public Long getFdTotalTime() {
		return this.fdTotalTime;
	}

	/**
	 * @param fdTotalTime
	 *            总工时
	 */
	public void setFdTotalTime(Long fdTotalTime) {
		this.fdTotalTime = fdTotalTime;
	}

	/**
	 * 加班总工时
	 */
	private Long fdOverTime;

	public Long getFdOverTime() {
		return fdOverTime;
	}

	public void setFdOverTime(Long fdOverTime) {
		this.fdOverTime = fdOverTime;
	}

	/**
	 * 工作日加班工时
	 */
	private Long fdWorkOverTime;

	/**
	 * 休息日加班工时
	 */
	private Long fdOffOverTime;

	/**
	 * 节假日加班工时
	 */
	private Long fdHolidayOverTime;

	public Long getFdWorkOverTime() {
		return fdWorkOverTime;
	}

	public void setFdWorkOverTime(Long fdWorkOverTime) {
		this.fdWorkOverTime = fdWorkOverTime;
	}

	public Long getFdOffOverTime() {
		return fdOffOverTime;
	}

	public void setFdOffOverTime(Long fdOffOverTime) {
		this.fdOffOverTime = fdOffOverTime;
	}

	public Long getFdHolidayOverTime() {
		return fdHolidayOverTime;
	}

	public void setFdHolidayOverTime(Long fdHolidayOverTime) {
		this.fdHolidayOverTime = fdHolidayOverTime;
	}

	private Float fdOutgoingTime;

	public Float getFdOutgoingTime() {
		return fdOutgoingTime;
	}

	public void setFdOutgoingTime(Float fdOutgoingTime) {
		this.fdOutgoingTime = fdOutgoingTime;
	}

	/**
	 * 外出的天数
	 */
	private Float fdOutgoingDay;

	public Float getFdOutgoingDay() {
		return fdOutgoingDay;
	}

	public void setFdOutgoingDay(Float fdOutgoingDay) {
		this.fdOutgoingDay = fdOutgoingDay;
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
	 * 迟到时间
	 */
	private Integer fdLateTime;

	/**
	 * @return 迟到时间
	 */
	public Integer getFdLateTime() {
		return this.fdLateTime;
	}

	/**
	 * @param fdLateTime
	 *            迟到时间
	 */
	public void setFdLateTime(Integer fdLateTime) {
		this.fdLateTime = fdLateTime;
	}

	/**
	 * 早退时间
	 */
	private Integer fdLeftTime;

	/**
	 * @return 早退时间
	 */
	public Integer getFdLeftTime() {
		return this.fdLeftTime;
	}

	/**
	 * @param fdLeftTime
	 *            早退时间
	 */
	public void setFdLeftTime(Integer fdLeftTime) {
		this.fdLeftTime = fdLeftTime;
	}

	private Boolean fdLate;

	private Boolean fdLeft;
	// 缺卡
	private Boolean fdMissed;
	// 旷工
	private Boolean fdAbsent;
	// 正常
	private Boolean fdStatus;
	// 出差
	private Boolean fdTrip;
	// 正常出勤天数
	private Float fdStatusDays;
	// 旷工天数，因原来的字段不支持小数故弃用
	@Deprecated
	private Integer fdAbsentDays;
	// 旷工天数
	private Float fdAbsentDaysCount;
	// 缺卡次数
	private Integer fdMissedCount;
	// 外勤次数
	private Integer fdOutsideCount;
	// 迟到次数
	private Integer fdLateCount;
	// 早退次数
	private Integer fdLeftCount;
	// 应出勤天数
	private Float fdShouldDays;
	// 工作日出勤天数
	private Float fdWorkDateDays;
	// 实际出勤天数
	private Float fdActualDays;
	// 出差天数
	private Float fdTripDays;
	// 请假天数
	private Float fdOffDays;
	// 请假小时
	private Integer fdOffTime;
	// 请假小时(单位小时) 替换fdOffTime(支持分钟数)
	private Float fdOffTimeHour;
	// 缺卡补卡次数
	private Integer fdMissedExcCount;
	// 迟到补卡次数
	private Integer fdLateExcCount;
	// 早退补卡次数
	private Integer fdLeftExcCount;
	// 请假天数细分，格式为Json，类型为String
	private String fdOffDaysDetail;
	// 请假天数细分，整理格式化后，类型为JSON
	private JSONObject fdOffDaysDetailJson;
	// 防止人员置为无效后层级关系丢失
	private String docCreatorHId;

	public Float getFdTripDays() {
		return fdTripDays;
	}

	public void setFdTripDays(Float fdTripDays) {
		this.fdTripDays = fdTripDays;
	}

	public Float getFdOffDays() {
		return fdOffDays;
	}

	public void setFdOffDays(Float fdOffDays) {
		this.fdOffDays = fdOffDays;
	}

	public Integer getFdOffTime() {
		return fdOffTime;
	}

	public void setFdOffTime(Integer fdOffTime) {
		this.fdOffTime = fdOffTime;
	}

	public Float getFdOffTimeHour() {
		if (fdOffTimeHour == null) {
			return fdOffTime != null ? fdOffTime.floatValue() : 0f;
		}
		return fdOffTimeHour;
	}

	public void setFdOffTimeHour(Float fdOffTimeHour) {
		this.fdOffTimeHour = fdOffTimeHour;
	}

	@Deprecated
	public Integer getFdAbsentDays() {
		return fdAbsentDays;
	}

	@Deprecated
	public void setFdAbsentDays(Integer fdAbsentDays) {
		this.fdAbsentDays = fdAbsentDays;
	}

	public Integer getFdMissedCount() {
		return fdMissedCount;
	}

	public void setFdMissedCount(Integer fdMissedCount) {
		this.fdMissedCount = fdMissedCount;
	}

	public Integer getFdOutsideCount() {
		return fdOutsideCount;
	}

	public void setFdOutsideCount(Integer fdOutsideCount) {
		this.fdOutsideCount = fdOutsideCount;
	}

	public Integer getFdLateCount() {
		return fdLateCount;
	}

	public void setFdLateCount(Integer fdLateCount) {
		this.fdLateCount = fdLateCount;
	}

	public Integer getFdLeftCount() {
		return fdLeftCount;
	}

	public void setFdLeftCount(Integer fdLeftCount) {
		this.fdLeftCount = fdLeftCount;
	}

	public Boolean getFdLate() {
		return fdLate;
	}

	public void setFdLate(Boolean fdLate) {
		this.fdLate = fdLate;
	}

	public Boolean getFdLeft() {
		return fdLeft;
	}

	public void setFdLeft(Boolean fdLeft) {
		this.fdLeft = fdLeft;
	}

	public Boolean getFdMissed() {
		return fdMissed;
	}

	public void setFdMissed(Boolean fdMissed) {
		this.fdMissed = fdMissed;
	}

	public Boolean getFdAbsent() {
		return fdAbsent;
	}

	public void setFdAbsent(Boolean fdAbsent) {
		this.fdAbsent = fdAbsent;
	}

	public Boolean getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(Boolean fdStatus) {
		this.fdStatus = fdStatus;
	}

	public Boolean getFdTrip() {
		return fdTrip;
	}

	public void setFdTrip(Boolean fdTrip) {
		this.fdTrip = fdTrip;
	}

	public Integer getFdMissedExcCount() {
		return fdMissedExcCount;
	}

	public void setFdMissedExcCount(Integer fdMissedExcCount) {
		this.fdMissedExcCount = fdMissedExcCount;
	}

	public Integer getFdLateExcCount() {
		return fdLateExcCount;
	}

	public void setFdLateExcCount(Integer fdLateExcCount) {
		this.fdLateExcCount = fdLateExcCount;
	}

	public Integer getFdLeftExcCount() {
		return fdLeftExcCount;
	}

	public void setFdLeftExcCount(Integer fdLeftExcCount) {
		this.fdLeftExcCount = fdLeftExcCount;
	}

	/**
	 * 是否外勤
	 */
	private Boolean fdOutside;

	/**
	 * @return 是否外勤
	 */
	public Boolean getFdOutside() {
		return this.fdOutside;
	}

	/**
	 * @param fdOutside
	 *            是否外勤
	 */
	public void setFdOutside(Boolean fdOutside) {
		this.fdOutside = fdOutside;
	}

	/**
	 * 创建者
	 */
	private SysOrgPerson docCreator;

	/**
	 * @return 创建者
	 */
	public SysOrgPerson getDocCreator() {
		return this.docCreator;
	}

	/**
	 * @param docCreator
	 *            创建者
	 */
	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	public String getFdOffDaysDetail() {
		return fdOffDaysDetail;
	}

	public void setFdOffDaysDetail(String fdOffDaysDetail) {
		this.fdOffDaysDetail = fdOffDaysDetail;
	}

	public JSONObject getFdOffDaysDetailJson() {
		return fdOffDaysDetailJson;
	}

	public void setFdOffDaysDetailJson(JSONObject fdOffDaysDetailJson) {
		this.fdOffDaysDetailJson = fdOffDaysDetailJson;
	}

	public String getDocCreatorHId() {
		return docCreatorHId;
	}

	public void setDocCreatorHId(String docCreatorHId) {
		this.docCreatorHId = docCreatorHId;
	}

	public Float getFdAbsentDaysCount() {
		return fdAbsentDaysCount;
	}

	public void setFdAbsentDaysCount(Float fdAbsentDaysCount) {
		this.fdAbsentDaysCount = fdAbsentDaysCount;
	}

	private String fdReportId;

	public String getFdReportId() {
		return fdReportId;
	}

	public void setFdReportId(String fdReportId) {
		this.fdReportId = fdReportId;
	}
	
	public Integer fdHolidays;

	/**
	 * 节假日天数
	 * @return
	 */
	public Integer getFdHolidays() {
		if(fdHolidays ==null){
			fdHolidays =0;
		}
		return fdHolidays;
	}

	/**
	 * 节假日天数
	 * @param fdHolidays
	 */
	public void setFdHolidays(Integer fdHolidays) {
		this.fdHolidays = fdHolidays;
	}

	public Float getFdStatusDays() {
		return fdStatusDays;
	}

	public void setFdStatusDays(Float fdStatusDays) {
		this.fdStatusDays = fdStatusDays;
	}

	public Float getFdShouldDays() {
		return fdShouldDays;
	}

	public void setFdShouldDays(Float fdShouldDays) {
		this.fdShouldDays = fdShouldDays;
	}

	public Float getFdWorkDateDays() {
		return fdWorkDateDays;
	}

	public void setFdWorkDateDays(Float fdWorkDateDays) {
		this.fdWorkDateDays = fdWorkDateDays;
	}

	public Float getFdActualDays() {
		return fdActualDays;
	}

	public void setFdActualDays(Float fdActualDays) {
		this.fdActualDays = fdActualDays;
	}

	@Override
	public Class getFormClass() {
		return SysAttendReportMonthForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
		}
		return toFormPropertyMap;
	}

	/**
	 * 请假的天数(全部转换成天的合计)
	 */
	private Float fdLeaveDays;

	public Float getFdLeaveDays() {
		if(fdLeaveDays ==null){
			fdLeaveDays =0F;
		}
		return fdLeaveDays;
	}

	public void setFdLeaveDays(Float fdLeaveDays) {
		this.fdLeaveDays = fdLeaveDays;
	}
}
