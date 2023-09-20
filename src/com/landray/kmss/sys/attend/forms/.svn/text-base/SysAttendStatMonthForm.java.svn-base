package com.landray.kmss.sys.attend.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendStatMonth;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 人员统计表 Form
 * 
 * @author
 * @version 1.0 2017-07-27
 */
public class SysAttendStatMonthForm extends ExtendForm implements ISysAuthAreaForm {

	// 所属场所ID
		protected String authAreaId = null;

		@Override
		public String getAuthAreaId() {
			return authAreaId;
		}

		@Override
		public void setAuthAreaId(String authAreaId) {
			this.authAreaId = authAreaId;
		}

		// 所属场所名称
		protected String authAreaName = null;

		@Override
		public String getAuthAreaName() {
			return authAreaName;
		}

		@Override
		public void setAuthAreaName(String authAreaName) {
			this.authAreaName = authAreaName;
		}
		
	/**
	 * 月份
	 */
	private String fdMonth;

	public String getFdMonth() {
		return fdMonth;
	}

	public void setFdMonth(String fdMonth) {
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
	 * 总工时
	 */
	private String fdTotalTime;

	/**
	 * @return 总工时
	 */
	public String getFdTotalTime() {
		return this.fdTotalTime;
	}

	/**
	 * @param fdTotalTime
	 *            总工时
	 */
	public void setFdTotalTime(String fdTotalTime) {
		this.fdTotalTime = fdTotalTime;
	}

	/**
	 * 加班总工时
	 */
	private String fdOverTime;

	public String getFdOverTime() {
		return fdOverTime;
	}

	public void setFdOverTime(String fdOverTime) {
		this.fdOverTime = fdOverTime;
	}

	/**
	 * 工作日加班工时
	 */
	private String fdWorkOverTime;

	/**
	 * 休息日加班工时
	 */
	private String fdOffOverTime;

	/**
	 * 节假日加班工时
	 */
	private String fdHolidayOverTime;

	public String getFdWorkOverTime() {
		return fdWorkOverTime;
	}

	public void setFdWorkOverTime(String fdWorkOverTime) {
		this.fdWorkOverTime = fdWorkOverTime;
	}

	public String getFdOffOverTime() {
		return fdOffOverTime;
	}

	public void setFdOffOverTime(String fdOffOverTime) {
		this.fdOffOverTime = fdOffOverTime;
	}

	public String getFdHolidayOverTime() {
		return fdHolidayOverTime;
	}

	public void setFdHolidayOverTime(String fdHolidayOverTime) {
		this.fdHolidayOverTime = fdHolidayOverTime;
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
	 * @param docCreateTime
	 *            创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 迟到时间
	 */
	private String fdLateTime;

	/**
	 * @return 迟到时间
	 */
	public String getFdLateTime() {
		return this.fdLateTime;
	}

	/**
	 * @param fdLateTime
	 *            迟到时间
	 */
	public void setFdLateTime(String fdLateTime) {
		this.fdLateTime = fdLateTime;
	}

	/**
	 * 早退时间
	 */
	private String fdLeftTime;

	/**
	 * @return 早退时间
	 */
	public String getFdLeftTime() {
		return this.fdLeftTime;
	}

	/**
	 * @param fdLeftTime
	 *            早退时间
	 */
	public void setFdLeftTime(String fdLeftTime) {
		this.fdLeftTime = fdLeftTime;
	}

	private String fdLate;

	private String fdLeft;
	// 缺卡
	private String fdMissed;
	// 旷工
	private String fdAbsent;
	// 事假
	private String fdPersonalLeave;
	// 正常
	private String fdStatus;
	// 出差
	private String fdTrip;
	// 正常天数
	private Float fdStatusDays;
	// 旷工天数
	private Integer fdAbsentDays;
	// 事假天数
	private Integer fdPersonalLeaveDays;
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
	// 实际出勤天数
	private Float fdActualDays;
	// 工作日出勤天数
	private Float fdWorkDateDays;
	// 出差天数
	private Float fdTripDays;
	// 请假天数
	private Float fdOffDays;

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

	public String getFdPersonalLeave() {
		return fdPersonalLeave;
	}

	public void setFdPersonalLeave(String fdPersonalLeave) {
		this.fdPersonalLeave = fdPersonalLeave;
	}

	public Integer getFdPersonalLeaveDays() {
		return fdPersonalLeaveDays;
	}

	public void setFdPersonalLeaveDays(Integer fdPersonalLeaveDays) {
		this.fdPersonalLeaveDays = fdPersonalLeaveDays;
	}

	public Integer getFdAbsentDays() {
		return fdAbsentDays;
	}

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

	public String getFdLate() {
		return fdLate;
	}

	public void setFdLate(String fdLate) {
		this.fdLate = fdLate;
	}

	public String getFdLeft() {
		return fdLeft;
	}

	public void setFdLeft(String fdLeft) {
		this.fdLeft = fdLeft;
	}

	public String getFdMissed() {
		return fdMissed;
	}

	public void setFdMissed(String fdMissed) {
		this.fdMissed = fdMissed;
	}

	public String getFdAbsent() {
		return fdAbsent;
	}

	public void setFdAbsent(String fdAbsent) {
		this.fdAbsent = fdAbsent;
	}

	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	public String getFdTrip() {
		return fdTrip;
	}

	public void setFdTrip(String fdTrip) {
		this.fdTrip = fdTrip;
	}

	/**
	 * 是否外勤
	 */
	private String fdOutside;

	/**
	 * @return 是否外勤
	 */
	public String getFdOutside() {
		return this.fdOutside;
	}

	/**
	 * @param fdOutside
	 *            是否外勤
	 */
	public void setFdOutside(String fdOutside) {
		this.fdOutside = fdOutside;
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
	 * @param docCreatorId
	 *            创建者的ID
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
	 * @param docCreatorName
	 *            创建者的名称
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}
	
	public Integer fdHolidays;

	/**
	 * 节假日天数
	 * @return
	 */
	public Integer getFdHolidays() {
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

	public Float getFdActualDays() {
		return fdActualDays;
	}

	public void setFdActualDays(Float fdActualDays) {
		this.fdActualDays = fdActualDays;
	}

	public Float getFdWorkDateDays() {
		return fdWorkDateDays;
	}

	public void setFdWorkDateDays(Float fdWorkDateDays) {
		this.fdWorkDateDays = fdWorkDateDays;
	}
	
	// 机制开始
	// 机制结束

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdMonth = null;
		fdTotalTime = null;
		fdOverTime = null;
		fdWorkOverTime = null;
		fdOffOverTime = null;
		fdHolidayOverTime = null;
		docCreateTime = null;
		fdLateTime = null;
		fdLeftTime = null;
		fdStatus = null;
		fdOutside = null;
		docCreatorId = null;
		docCreatorName = null;
		fdLate = null;
		fdLeft = null;
		fdAbsent = null;
		fdMissed = null;
		fdTrip = null;
		fdCategoryId = null;
		fdStatusDays = null;
		fdAbsentDays = null;
		fdPersonalLeaveDays=null;
		fdPersonalLeave=null;
		fdMissedCount = null;
		fdOutsideCount = null;
		fdLateCount = null;
		fdLeftCount = null;
		fdShouldDays = null;
		fdActualDays = null;
		fdTripDays = null;
		fdOffDays = null;
		fdWorkDateDays = null;
		fdHolidays=null;
		super.reset(mapping, request);
	}

	@Override
	public Class<SysAttendStatMonth> getModelClass() {
		return SysAttendStatMonth.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel("docCreator", SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
}
