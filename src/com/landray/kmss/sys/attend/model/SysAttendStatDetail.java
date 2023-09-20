package com.landray.kmss.sys.attend.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendStatDetailForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;



/**
 * 人员统计详情
 * 
 * @author 
 * @version 1.0 2017-07-27
 */
public class SysAttendStatDetail  extends BaseModel {
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
	 *月异常考勤，1异常否则正常
	 */
	private Integer fdAttendResult;
	
	public Integer getFdAttendResult() {
		return fdAttendResult;
	}

	public void setFdAttendResult(Integer fdAttendResult) {
		this.fdAttendResult = fdAttendResult;
	}

	//======================
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

	private Long fdOverTime;

	public Long getFdOverTime() {
		return fdOverTime;
	}

	public void setFdOverTime(Long fdOverTime) {
		this.fdOverTime = fdOverTime;
	}

	private Long fdOverApplyTime;

	/**
	 * 加班申请总时长
	 * @return
	 */
	public Long getFdOverApplyTime() {
		return fdOverApplyTime;
	}

	public void setFdOverApplyTime(Long fdOverApplyTime) {
		this.fdOverApplyTime = fdOverApplyTime;
	}

	public Integer fdDateType;

	public Integer getFdDateType() {
		return fdDateType;
	}

	public void setFdDateType(Integer fdDateType) {
		this.fdDateType = fdDateType;
	}

	/**
	 * 外出工时
	 */
	private Float fdOutgoingTime;

	public Float getFdOutgoingTime() {
		return fdOutgoingTime;
	}

	public void setFdOutgoingTime(Float fdOutgoingTime) {
		this.fdOutgoingTime = fdOutgoingTime;
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
	 * 打卡时间1
	 */
	private Date fdSignTime;
	
	/**
	 * @return 打卡时间1
	 */
	public Date getFdSignTime() {
		return this.fdSignTime;
	}
	
	/**
	 * @param fdSignTime 打卡时间1
	 */
	public void setFdSignTime(Date fdSignTime) {
		this.fdSignTime = fdSignTime;
	}
	
	/**
	 * 打卡状态1
	 */
	private Integer docStatus;
	
	/**
	 * @return 打卡状态1
	 */
	public Integer getDocStatus() {
		return this.docStatus;
	}
	
	/**
	 * @param docStatus 打卡状态1
	 */
	public void setDocStatus(Integer docStatus) {
		this.docStatus = docStatus;
	}
	
	/**
	 * 是否外勤1
	 */
	private Boolean fdOutside;
	
	/**
	 * @return 是否外勤1
	 */
	public Boolean getFdOutside() {
		return this.fdOutside;
	}
	
	/**
	 * @param fdOutside 是否外勤1
	 */
	public void setFdOutside(Boolean fdOutside) {
		this.fdOutside = fdOutside;
	}
	
	/**
	 * 异常状态1
	 */
	private Integer fdState;
	
	/**
	 * @return 异常状态1
	 */
	public Integer getFdState() {
		return this.fdState;
	}
	
	/**
	 * @param fdState 异常状态1
	 */
	public void setFdState(Integer fdState) {
		this.fdState = fdState;
	}
	
	/**
	 * 打卡时间2
	 */
	private Date fdSignTime2;
	
	/**
	 * @return 打卡时间2
	 */
	public Date getFdSignTime2() {
		return this.fdSignTime2;
	}
	
	/**
	 * @param fdSignTime2 打卡时间2
	 */
	public void setFdSignTime2(Date fdSignTime2) {
		this.fdSignTime2 = fdSignTime2;
	}
	
	/**
	 * 打卡状态2
	 */
	private Integer docStatus2;
	
	/**
	 * @return 打卡状态2
	 */
	public Integer getDocStatus2() {
		return this.docStatus2;
	}
	
	/**
	 * @param docStatus2 打卡状态2
	 */
	public void setDocStatus2(Integer docStatus2) {
		this.docStatus2 = docStatus2;
	}
	
	/**
	 * 是否外勤2
	 */
	private Boolean fdOutside2;
	
	/**
	 * @return 是否外勤2
	 */
	public Boolean getFdOutside2() {
		return this.fdOutside2;
	}
	
	/**
	 * @param fdOutside2 是否外勤2
	 */
	public void setFdOutside2(Boolean fdOutside2) {
		this.fdOutside2 = fdOutside2;
	}
	
	/**
	 * 异常状态2
	 */
	private Integer fdState2;
	
	/**
	 * @return 异常状态2
	 */
	public Integer getFdState2() {
		return this.fdState2;
	}
	
	/**
	 * @param fdState2 异常状态2
	 */
	public void setFdState2(Integer fdState2) {
		this.fdState2 = fdState2;
	}
	
	/**
	 * 打卡时间3
	 */
	private Date fdSignTime3;
	
	/**
	 * @return 打卡时间3
	 */
	public Date getFdSignTime3() {
		return this.fdSignTime3;
	}
	
	/**
	 * @param fdSignTime3 打卡时间3
	 */
	public void setFdSignTime3(Date fdSignTime3) {
		this.fdSignTime3 = fdSignTime3;
	}
	
	/**
	 * 打卡状态3
	 */
	private Integer docStatus3;
	
	/**
	 * @return 打卡状态3
	 */
	public Integer getDocStatus3() {
		return this.docStatus3;
	}
	
	/**
	 * @param docStatus3 打卡状态3
	 */
	public void setDocStatus3(Integer docStatus3) {
		this.docStatus3 = docStatus3;
	}
	
	/**
	 * 是否外勤3
	 */
	private Boolean fdOutside3;
	
	/**
	 * @return 是否外勤3
	 */
	public Boolean getFdOutside3() {
		return this.fdOutside3;
	}
	
	/**
	 * @param fdOutside3 是否外勤3
	 */
	public void setFdOutside3(Boolean fdOutside3) {
		this.fdOutside3 = fdOutside3;
	}
	
	/**
	 * 异常状态3
	 */
	private Integer fdState3;
	
	/**
	 * @return 异常状态3
	 */
	public Integer getFdState3() {
		return this.fdState3;
	}
	
	/**
	 * @param fdState3 异常状态3
	 */
	public void setFdState3(Integer fdState3) {
		this.fdState3 = fdState3;
	}
	
	/**
	 * 打卡时间4
	 */
	private Date fdSignTime4;
	
	/**
	 * @return 打卡时间4
	 */
	public Date getFdSignTime4() {
		return this.fdSignTime4;
	}
	
	/**
	 * @param fdSignTime4 打卡时间4
	 */
	public void setFdSignTime4(Date fdSignTime4) {
		this.fdSignTime4 = fdSignTime4;
	}
	
	/**
	 * 打卡状态4
	 */
	private Integer docStatus4;
	
	/**
	 * @return 打卡状态4
	 */
	public Integer getDocStatus4() {
		return this.docStatus4;
	}
	
	/**
	 * @param docStatus4 打卡状态4
	 */
	public void setDocStatus4(Integer docStatus4) {
		this.docStatus4 = docStatus4;
	}
	
	/**
	 * 是否外勤4
	 */
	private Boolean fdOutside4;
	
	/**
	 * @return 是否外勤4
	 */
	public Boolean getFdOutside4() {
		return this.fdOutside4;
	}
	
	/**
	 * @param fdOutside4 是否外勤4
	 */
	public void setFdOutside4(Boolean fdOutside4) {
		this.fdOutside4 = fdOutside4;
	}
	
	/**
	 * 异常状态4
	 */
	private Integer fdState4;
	
	/**
	 * @return 异常状态4
	 */
	public Integer getFdState4() {
		return this.fdState4;
	}
	
	/**
	 * @param fdState4 异常状态4
	 */
	public void setFdState4(Integer fdState4) {
		this.fdState4 = fdState4;
	}
	
	private String docCreatorHId;

	public String getDocCreatorHId() {
		return docCreatorHId;
	}

	public void setDocCreatorHId(String docCreatorHId) {
		this.docCreatorHId = docCreatorHId;
	}

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

	@Override
    public Class<SysAttendStatDetailForm> getFormClass() {
		return SysAttendStatDetailForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}
}
