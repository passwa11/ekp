package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendStat;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;

/**
 * 人员统计表 Form
 * 
 * @author
 * @version 1.0 2017-07-27
 */
public class SysAttendStatForm extends ExtendForm implements ISysAuthAreaForm {
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
		private String fdRestTime;
		
	
		public String getFdRestTime() {
			return fdRestTime;
		}

		public void setFdRestTime(String fdRestTime) {
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
		 *延时加班时长 （是指实际下班打卡时间与计划下班时间之间的时长）
		 */
		private Integer fdDelayedTime;
		

		public Integer getFdDelayedTime() {
			return fdDelayedTime;
		}

		public void setFdDelayedTime(Integer fdDelayedTime) {
			this.fdDelayedTime = fdDelayedTime;
		}
		
		/**
		 *考勤结果； 处理方法：隐藏每日汇总中“处理情况”字段，添加“考勤结果”字段，考勤结果显示为“正常”或“异常”，1天只有一个考勤结果
		 */
		private String fdAttendResult;
		
		public String getFdAttendResult() {
			return fdAttendResult;
		}

		public void setFdAttendResult(String fdAttendResult) {
			this.fdAttendResult = fdAttendResult;
		}
		


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
	 * 日期
	 */
	private String fdDate;

	/**
	 * @return 日期
	 */
	public String getFdDate() {
		return this.fdDate;
	}

	/**
	 * @param fdDate
	 *            日期
	 */
	public void setFdDate(String fdDate) {
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

	private String fdOverTime;

	public String getFdOverTime() {
		return fdOverTime;
	}

	public void setFdOverTime(String fdOverTime) {
		this.fdOverTime = fdOverTime;
	}

	private String fdDateType;

	public String getFdDateType() {
		return fdDateType;
	}

	public void setFdDateType(String fdDateType) {
		this.fdDateType = fdDateType;
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
	// 请假
	private String fdOff;
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

	public String getFdOff() {
		return fdOff;
	}

	public void setFdOff(String fdOff) {
		this.fdOff = fdOff;
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

	private String fdStartTime;
	private String fdEndTime;

	public String getFdStartTime() {
		return fdStartTime;
	}

	public void setFdStartTime(String fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	public String getFdEndTime() {
		return fdEndTime;
	}

	public void setFdEndTime(String fdEndTime) {
		this.fdEndTime = fdEndTime;
	}

	/**
	 * 加班申请时长
	 */
	private String fdOverApplyTime;

	/**
	 * 加班是否转其他假期
	 */
	private String fdOverHandle;

	public String getFdOverApplyTime() {
		return fdOverApplyTime;
	}

	public String getFdPersonalLeave() {
		return fdPersonalLeave;
	}

	public void setFdPersonalLeave(String fdPersonalLeave) {
		this.fdPersonalLeave = fdPersonalLeave;
	}

	public void setFdOverApplyTime(String fdOverApplyTime) {
		this.fdOverApplyTime = fdOverApplyTime;
	}

	public String getFdOverHandle() {
		return fdOverHandle;
	}

	public void setFdOverHandle(String fdOverHandle) {
		this.fdOverHandle = fdOverHandle;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdDate = null;
		fdCategoryId = null;
		fdTotalTime = null;
		fdOverTime = null;
		fdDateType = null;
		docCreateTime = null;
		fdLateTime = null;
		fdLeftTime = null;
		fdStatus = null;
		fdOutside = null;

		fdCategoryName = null;
		docCreatorId = null;
		docCreatorName = null;
		fdLate = null;
		fdLeft = null;
		fdAbsent = null;
		fdPersonalLeave = null;
		fdMissed = null;
		fdTrip = null;
		fdOff = null;
		fdMissedCount = null;
		fdOutsideCount = null;
		fdLateCount = null;
		fdLeftCount = null;
		fdTripCount = null;
		fdOffCount = null;

		fdStartTime = null;
		fdEndTime = null;
		fdStandWorkTime=null;
		fdMonthLateNum=null;
		fdMonthForgerNum=null;
		fdMonthLateMinNum=null;
		fdOverApplyTime = null;
		fdOverHandle = null;
		super.reset(mapping, request);
	}

	@Override
    public Class<SysAttendStat> getModelClass() {
		return SysAttendStat.class;
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
