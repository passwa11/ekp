package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendStatForm;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaModel;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

import java.util.Date;

/**
 * 人员统计表
 * 
 * @author
 * @version 1.0 2017-07-27
 */
public class SysAttendStat extends BaseModel implements ISysAuthAreaModel {

	//================2022.11.03新增字段
		/**
		 * 一级部门
		 */
		private String fdFirstLevelDepartmentName;
		
		public String getFdFirstLevelDepartmentName() {
			return fdFirstLevelDepartmentName;
		}

		public void setFdFirstLevelDepartmentName(String fdFirstLevelDepartmentName) {
			this.fdFirstLevelDepartmentName = fdFirstLevelDepartmentName;
		}

		/**
		 * 二级部门
		 */
		private String fdSecondLevelDepartmentName;
		
		public String getFdSecondLevelDepartmentName() {
			return fdSecondLevelDepartmentName;
		}

		public void setFdSecondLevelDepartmentName(String fdSecondLevelDepartmentName) {
			this.fdSecondLevelDepartmentName = fdSecondLevelDepartmentName;
		}
		
		/**
		 * 三级部门
		 */
		private String fdThirdLevelDepartmentName;
		
		public String getFdThirdLevelDepartmentName() {
			return fdThirdLevelDepartmentName;
		}

		public void setFdThirdLevelDepartmentName(String fdThirdLevelDepartmentName) {
			this.fdThirdLevelDepartmentName = fdThirdLevelDepartmentName;
		}
		/**
		 * 午休时间
		 */
		private Integer fdRestTime;
		
	
		public Integer getFdRestTime() {
			return fdRestTime;
		}

		public void setFdRestTime(Integer fdRestTime) {
			this.fdRestTime = fdRestTime;
		}
		/**
		 * 考勤标准工作时长
		 */
		private Float fdStandWorkTime;
		
		public Float getFdStandWorkTime() {
			return fdStandWorkTime;
		}

		public void setFdStandWorkTime(Float fdStandWorkTime) {
			this.fdStandWorkTime = fdStandWorkTime;
		}
		
		/**
		 * 月迟到次数
		 */
		private Integer fdMonthLateNum;
		
		
		public Integer getFdMonthLateNum() {
			return fdMonthLateNum;
		}

		public void setFdMonthLateNum(Integer fdMonthLateNum) {
			this.fdMonthLateNum = fdMonthLateNum;
		}
		
		
		/**
		 * 月忘打卡次数
		 */
		private Integer fdMonthForgerNum;
		
		
		public Integer getFdMonthForgerNum() {
			return fdMonthForgerNum;
		}

		public void setFdMonthForgerNum(Integer fdMonthForgerNum) {
			this.fdMonthForgerNum = fdMonthForgerNum;
		}

		/**
		 * 月迟到分钟
		 */
		private Integer fdMonthLateMinNum;
		

		public Integer getFdMonthLateMinNum() {
			return fdMonthLateMinNum;
		}

		public void setFdMonthLateMinNum(Integer fdMonthLateMinNum) {
			this.fdMonthLateMinNum = fdMonthLateMinNum;
		}
		
		/**
		 *延时加班时长 （是指实际下班打卡时间与计划下班时间之间的时长）fdOverTimeWithoutDeduct
		 */
		private Integer fdDelayedTime;
		

		public Integer getFdDelayedTime() {
			return fdDelayedTime;
		}

		public void setFdDelayedTime(Integer fdDelayedTime) {
			this.fdDelayedTime = fdDelayedTime;
		}
		
		
		/**
		 *月异常考勤次数； 处理方法：隐藏每日汇总中“处理情况”字段，添加“考勤结果”字段，考勤结果显示为“正常”或“异常”，1天只有一个考勤结果
		 */
		private Integer fdAttendResult;
		
		public Integer getFdAttendResult() {
			return fdAttendResult;
		}

		public void setFdAttendResult(Integer fdAttendResult) {
			this.fdAttendResult = fdAttendResult;
		}

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
	 * 日期
	 */
	private Date fdDate = new Date();

	/**
	 * @return 日期
	 */
	public Date getFdDate() {
		return this.fdDate;
	}

	/**
	 * @param fdDate
	 *            日期
	 */
	public void setFdDate(Date fdDate) {
		this.fdDate = fdDate;
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
	 * 分配的当天应工作的时间，分钟数
	 */
	private Float fdWorkTime;

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

	public Integer fdDateType;

	public Integer getFdDateType() {
		return fdDateType;
	}

	public void setFdDateType(Integer fdDateType) {
		this.fdDateType = fdDateType;
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
	// 旷工天数
	private Float fdAbsentDays;

	// 事假
	private Boolean fdPersonalLeave;
	// 事假天数
	private Float fdPersonalLeaveDays;

	// 正常
	private Boolean fdStatus;
	// 出差
	private Boolean fdTrip;
	// 请假
	private Boolean fdOff;
	// 外出
	private Boolean fdOutgoing;
	// 缺卡次数
	private Integer fdMissedCount;
	// 外勤次数
	private Integer fdOutsideCount;
	// 迟到次数
	private Integer fdLateCount;
	// 早退次数
	private Integer fdLeftCount;
	// 出差次数
	private Integer fdTripCount;
	// 请假次数
	private Integer fdOffCount;
	// 出差天数
	private Float fdTripDays;
	// 请假天数
	private Float fdOffDays;
	// 缺卡补卡次数
	private Integer fdMissedExcCount;
	// 迟到补卡次数
	private Integer fdLateExcCount;
	// 早退补卡次数
	private Integer fdLeftExcCount;
	// 请假天数细分，格式为Json
	private String fdOffCountDetail;
	// 外出工时(单位小时)
	private Float fdOutgoingTime;
	// 请假小时(单位小时)
	private Integer fdOffTime;
	// 请假小时(单位小时) 替换fdOffTime(支持分钟数)
	private Float fdOffTimeHour;
	private String startTime;
	private String endTime;
	private Integer shiftType;
	
	public Integer getShiftType() {
		return shiftType;
	}

	public void setShiftType(Integer shiftType) {
		this.shiftType = shiftType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

	public Integer getFdTripCount() {
		return fdTripCount;
	}

	public void setFdTripCount(Integer fdTripCount) {
		this.fdTripCount = fdTripCount;
	}

	public Integer getFdOffCount() {
		return fdOffCount;
	}

	public void setFdOffCount(Integer fdOffCount) {
		this.fdOffCount = fdOffCount;
	}

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

	public Boolean getFdOff() {
		return fdOff;
	}

	public void setFdOff(Boolean fdOff) {
		this.fdOff = fdOff;
	}

	public Boolean getFdOutgoing() {
		return fdOutgoing;
	}

	public void setFdOutgoing(Boolean fdOutgoing) {
		this.fdOutgoing = fdOutgoing;
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

	public String getFdOffCountDetail() {
		return fdOffCountDetail;
	}

	public void setFdOffCountDetail(String fdOffCountDetail) {
		this.fdOffCountDetail = fdOffCountDetail;
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
	 * 考勤组名称
	 */
	private String fdCategoryName;

	/**
	 * @return 考勤组名称
	 */
	public String getFdCategoryName() {
		return this.fdCategoryName;
	}

	/**
	 * @param fdCategoryName
	 *            考勤组名称
	 */
	public void setFdCategoryName(String fdCategoryName) {
		this.fdCategoryName = fdCategoryName;
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

	private Date fdStartTime;
	private Date fdEndTime;

	public Date getFdStartTime() {
		return fdStartTime;
	}

	public void setFdStartTime(Date fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	public Date getFdEndTime() {
		return fdEndTime;
	}

	public void setFdEndTime(Date fdEndTime) {
		this.fdEndTime = fdEndTime;
	}

	private String docCreatorHId;

	public String getDocCreatorHId() {
		return docCreatorHId;
	}

	public void setDocCreatorHId(String docCreatorHId) {
		this.docCreatorHId = docCreatorHId;
	}

	public Float getFdAbsentDays() {
		return fdAbsentDays;
	}

	public void setFdAbsentDays(Float fdAbsentDays) {
		this.fdAbsentDays = fdAbsentDays;
	}

	public Float getFdOutgoingTime() {
		return fdOutgoingTime;
	}

	public void setFdOutgoingTime(Float fdOutgoingTime) {
		this.fdOutgoingTime = fdOutgoingTime;
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

	/**
	 * 这天是否没有打卡记录
	 */
	private Boolean fdIsNoRecord;

	public Boolean getFdIsNoRecord() {
		return fdIsNoRecord;
	}

	public void setFdIsNoRecord(Boolean fdIsNoRecord) {
		this.fdIsNoRecord = fdIsNoRecord;
	}

	@Override
    public Class<SysAttendStatForm> getFormClass() {
		return SysAttendStatForm.class;
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

	public Float getFdWorkTime() {
		return fdWorkTime;
	}

	public void setFdWorkTime(Float fdWorkTime) {
		this.fdWorkTime = fdWorkTime;
	}

	/**
	 * 事假
	 */
	public Boolean getFdPersonalLeave() {
		return fdPersonalLeave;
	}
	/**
	 * 事假
	 */
	public void setFdPersonalLeave(Boolean fdPersonalLeave) {
		this.fdPersonalLeave = fdPersonalLeave;
	}
	/**
	 * 事假天数
	 */
	public Float getFdPersonalLeaveDays() {
		return fdPersonalLeaveDays;
	}
	/**
	 * 事假天数
	 */
	public void setFdPersonalLeaveDays(Float fdPersonalLeaveDays) {
		this.fdPersonalLeaveDays = fdPersonalLeaveDays;
	}

	//定制新增，add by liuyang
	/**
	 * 加班申请工时
	 * @return
	 */
	private Long fdOverApplyTime;

	/**
	 * 加班转调休申请工时
	 */
	private Long fdOverTurnApplyTime;

	/**
	 * 加班转调休实际工时
	 */
	private Long fdOverTurnTime;

	/**
	 * 加班转加班费申请工时
	 */
	private Long fdOverPayApplyTime;

	/**
	 * 加班转加班费实际工时
	 */
	private Long fdOverPayTime;

	public Long getFdOverApplyTime() {
		return fdOverApplyTime;
	}

	public void setFdOverApplyTime(Long fdOverApplyTime) {
		this.fdOverApplyTime = fdOverApplyTime;
	}

	public Long getFdOverTurnApplyTime() {
		return fdOverTurnApplyTime;
	}

	public void setFdOverTurnApplyTime(Long fdOverTurnApplyTime) {
		this.fdOverTurnApplyTime = fdOverTurnApplyTime;
	}

	public Long getFdOverTurnTime() {
		return fdOverTurnTime;
	}

	public void setFdOverTurnTime(Long fdOverTurnTime) {
		this.fdOverTurnTime = fdOverTurnTime;
	}

	public Long getFdOverPayApplyTime() {
		return fdOverPayApplyTime;
	}

	public void setFdOverPayApplyTime(Long fdOverPayApplyTime) {
		this.fdOverPayApplyTime = fdOverPayApplyTime;
	}

	public Long getFdOverPayTime() {
		return fdOverPayTime;
	}

	public void setFdOverPayTime(Long fdOverPayTime) {
		this.fdOverPayTime = fdOverPayTime;
	}
}
