package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendStatDetail;
import com.landray.kmss.sys.organization.model.SysOrgElement;



/**
 * 人员统计详情 Form
 * 
 * @author 
 * @version 1.0 2017-07-27
 */
public class SysAttendStatDetailForm  extends ExtendForm  {
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

	private Integer fdDateType;

	public Integer getFdDateType() {
		return fdDateType;
	}

	public void setFdDateType(Integer fdDateType) {
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

	/**
	 * 打卡时间1
	 */
	private String fdSignTime;
	
	/**
	 * @return 打卡时间1
	 */
	public String getFdSignTime() {
		return this.fdSignTime;
	}
	
	/**
	 * @param fdSignTime 打卡时间1
	 */
	public void setFdSignTime(String fdSignTime) {
		this.fdSignTime = fdSignTime;
	}
	
	/**
	 * 打卡状态1
	 */
	private String docStatus;
	
	/**
	 * @return 打卡状态1
	 */
	public String getDocStatus() {
		return this.docStatus;
	}
	
	/**
	 * @param docStatus 打卡状态1
	 */
	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}
	
	/**
	 * 是否外勤1
	 */
	private String fdOutside;
	
	/**
	 * @return 是否外勤1
	 */
	public String getFdOutside() {
		return this.fdOutside;
	}
	
	/**
	 * @param fdOutside 是否外勤1
	 */
	public void setFdOutside(String fdOutside) {
		this.fdOutside = fdOutside;
	}
	
	/**
	 * 异常状态1
	 */
	private String fdState;
	
	/**
	 * @return 异常状态1
	 */
	public String getFdState() {
		return this.fdState;
	}
	
	/**
	 * @param fdState 异常状态1
	 */
	public void setFdState(String fdState) {
		this.fdState = fdState;
	}
	
	/**
	 * 打卡时间2
	 */
	private String fdSignTime2;
	
	/**
	 * @return 打卡时间2
	 */
	public String getFdSignTime2() {
		return this.fdSignTime2;
	}
	
	/**
	 * @param fdSignTime2 打卡时间2
	 */
	public void setFdSignTime2(String fdSignTime2) {
		this.fdSignTime2 = fdSignTime2;
	}
	
	/**
	 * 打卡状态2
	 */
	private String docStatus2;
	
	/**
	 * @return 打卡状态2
	 */
	public String getDocStatus2() {
		return this.docStatus2;
	}
	
	/**
	 * @param docStatus2 打卡状态2
	 */
	public void setDocStatus2(String docStatus2) {
		this.docStatus2 = docStatus2;
	}
	
	/**
	 * 是否外勤2
	 */
	private String fdOutside2;
	
	/**
	 * @return 是否外勤2
	 */
	public String getFdOutside2() {
		return this.fdOutside2;
	}
	
	/**
	 * @param fdOutside2 是否外勤2
	 */
	public void setFdOutside2(String fdOutside2) {
		this.fdOutside2 = fdOutside2;
	}
	
	/**
	 * 异常状态2
	 */
	private String fdState2;
	
	/**
	 * @return 异常状态2
	 */
	public String getFdState2() {
		return this.fdState2;
	}
	
	/**
	 * @param fdState2 异常状态2
	 */
	public void setFdState2(String fdState2) {
		this.fdState2 = fdState2;
	}
	
	/**
	 * 打卡时间3
	 */
	private String fdSignTime3;
	
	/**
	 * @return 打卡时间3
	 */
	public String getFdSignTime3() {
		return this.fdSignTime3;
	}
	
	/**
	 * @param fdSignTime3 打卡时间3
	 */
	public void setFdSignTime3(String fdSignTime3) {
		this.fdSignTime3 = fdSignTime3;
	}
	
	/**
	 * 打卡状态3
	 */
	private String docStatus3;
	
	/**
	 * @return 打卡状态3
	 */
	public String getDocStatus3() {
		return this.docStatus3;
	}
	
	/**
	 * @param docStatus3 打卡状态3
	 */
	public void setDocStatus3(String docStatus3) {
		this.docStatus3 = docStatus3;
	}
	
	/**
	 * 是否外勤3
	 */
	private String fdOutside3;
	
	/**
	 * @return 是否外勤3
	 */
	public String getFdOutside3() {
		return this.fdOutside3;
	}
	
	/**
	 * @param fdOutside3 是否外勤3
	 */
	public void setFdOutside3(String fdOutside3) {
		this.fdOutside3 = fdOutside3;
	}
	
	/**
	 * 异常状态3
	 */
	private String fdState3;
	
	/**
	 * @return 异常状态3
	 */
	public String getFdState3() {
		return this.fdState3;
	}
	
	/**
	 * @param fdState3 异常状态3
	 */
	public void setFdState3(String fdState3) {
		this.fdState3 = fdState3;
	}
	
	/**
	 * 打卡时间4
	 */
	private String fdSignTime4;
	
	/**
	 * @return 打卡时间4
	 */
	public String getFdSignTime4() {
		return this.fdSignTime4;
	}
	
	/**
	 * @param fdSignTime4 打卡时间4
	 */
	public void setFdSignTime4(String fdSignTime4) {
		this.fdSignTime4 = fdSignTime4;
	}
	
	/**
	 * 打卡状态4
	 */
	private String docStatus4;
	
	/**
	 * @return 打卡状态4
	 */
	public String getDocStatus4() {
		return this.docStatus4;
	}
	
	/**
	 * @param docStatus4 打卡状态4
	 */
	public void setDocStatus4(String docStatus4) {
		this.docStatus4 = docStatus4;
	}
	
	/**
	 * 是否外勤4
	 */
	private String fdOutside4;
	
	/**
	 * @return 是否外勤4
	 */
	public String getFdOutside4() {
		return this.fdOutside4;
	}
	
	/**
	 * @param fdOutside4 是否外勤4
	 */
	public void setFdOutside4(String fdOutside4) {
		this.fdOutside4 = fdOutside4;
	}
	
	/**
	 * 异常状态4
	 */
	private String fdState4;
	
	/**
	 * @return 异常状态4
	 */
	public String getFdState4() {
		return this.fdState4;
	}
	
	/**
	 * @param fdState4 异常状态4
	 */
	public void setFdState4(String fdState4) {
		this.fdState4 = fdState4;
	}
	
	//机制开始 
	//机制结束

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdDate = null;
		fdCategoryId = null;
		fdTotalTime = null;
		fdOverTime = null;
		fdDateType = null;
		docCreateTime = null;
		docCreatorId = null;
		docCreatorName = null;

		fdSignTime = null;
		docStatus = null;
		fdOutside = null;
		fdState = null;
		fdSignTime2 = null;
		docStatus2 = null;
		fdOutside2 = null;
		fdState2 = null;
		fdSignTime3 = null;
		docStatus3 = null;
		fdOutside3 = null;
		fdState3 = null;
		fdSignTime4 = null;
		docStatus4 = null;
		fdOutside4 = null;
		fdState4 = null;
		
		fdRestTime=null;
		fdStandWorkTime=null;
		fdMonthLateNum=null;
		fdMonthForgerNum=null;
		fdMonthLateMinNum=null;
		super.reset(mapping, request);
	}

	@Override
    public Class<SysAttendStatDetail> getModelClass() {
		return SysAttendStatDetail.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId",
					new FormConvertor_IDToModel("docCreator",
							SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
}
