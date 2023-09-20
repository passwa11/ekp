package com.landray.kmss.sys.time.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.component.locker.interfaces.ComponentLockable;
import com.landray.kmss.sys.time.forms.SysTimeLeaveAmountItemForm;
import com.landray.kmss.sys.time.util.SysTimeLeaveTimeDto;
import com.landray.kmss.sys.time.util.SysTimeUtil;

import java.util.Date;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-13
 */
public class SysTimeLeaveAmountItem extends BaseModel implements ComponentLockable {

	/**
	 * 是否无限制（暂时没用）
	 */
	private Boolean fdIsNoLimit;

	/**
	 * 是否累加
	 */
	private Boolean fdIsAccumulate;

	/**
	 * 是否自动发放
	 */
	private Boolean fdIsAuto;
	/**
	 * 假期名称(废弃)
	 */
	private String fdLeaveName;
	/**
	 * 假期编号
	 */
	private String fdLeaveType;

	/**
	 * 总天数
	 */
	private Float fdTotalDay;
	// 总分钟数(替换fdTotalDay)
	private Float fdTotalDayMin;

	/**
	 * 剩余天数
	 */
	private Float fdRestDay;
	// 剩余分钟数(替换fdRestDay)（暂时没用）
	private Float fdRestDayMin;

	/**
	 * 已使用天数
	 */
	private Float fdUsedDay;
	// 已使用分钟数(替换fdUsedDay)（暂时没用）
	private Float fdUsedDayMin;

	/**
	 * 有效期
	 */
	private Date fdValidDate;
	/**
	 * 是否有效
	 */
	private Boolean fdIsAvail;
	/**
	 * 总天数（上个周期）
	 */
	private Float fdLastTotalDay;
	// 上个周期总分钟数(替换fdLastTotalDayMin)（暂时没用）
	private Float fdLastTotalDayMin;

	/**
	 * 剩余天数（上个周期）
	 */
	private Float fdLastRestDay;
	// 上个周期剩余分钟数(替换fdLastRestDay)（暂时没用）
	private Float fdLastRestDayMin;

	/**
	 * 已使用天数（上个周期）
	 */
	private Float fdLastUsedDay;
	// 上个周期已使用分钟数(替换fdLastUsedDay)（暂时没用）
	private Float fdLastUsedDayMin;

	/**
	 * 有效期（上个周期）
	 */
	private Date fdLastValidDate;
	/**
	 * 上个周期是否有效
	 */
	private Boolean fdIsLastAvail;

	private SysTimeLeaveAmount fdAmount;
	private String fdSourceType;// 来源标识 hr:表示从人事档案迁移的数据

	public Boolean getFdIsNoLimit() {
		return fdIsNoLimit;
	}

	public void setFdIsNoLimit(Boolean fdIsNoLimit) {
		this.fdIsNoLimit = fdIsNoLimit;
	}

	public Boolean getFdIsAccumulate() {
		return fdIsAccumulate;
	}

	public void setFdIsAccumulate(Boolean fdIsAccumulate) {
		this.fdIsAccumulate = fdIsAccumulate;
	}

	public Boolean getFdIsAuto() {
		return fdIsAuto;
	}

	public void setFdIsAuto(Boolean fdIsAuto) {
		this.fdIsAuto = fdIsAuto;
	}

	public String getFdLeaveName() {
		return fdLeaveName;
	}

	public void setFdLeaveName(String fdLeaveName) {
		this.fdLeaveName = fdLeaveName;
	}

	public Float getFdTotalDay() {
		return fdTotalDay;
	}

	public void setFdTotalDay(Float fdTotalDay) {
		this.fdTotalDay = fdTotalDay;
	}

	public Float getFdUsedDay() {
		return fdUsedDay;
	}

	public void setFdUsedDay(Float fdUsedDay) {
		this.fdUsedDay = fdUsedDay;
	}

	public Date getFdValidDate() {
		return fdValidDate;
	}

	public void setFdValidDate(Date fdValidDate) {
		this.fdValidDate = fdValidDate;
	}

	public Float getFdLastTotalDay() {
		return fdLastTotalDay;
	}

	public void setFdLastTotalDay(Float fdLastTotalDay) {
		this.fdLastTotalDay = fdLastTotalDay;
	}



	/**
	 * 根据请假区间获取该假期类型上周期有效的剩余假期天数
	 * 
	 * @param leaveDetail 请假详情
	 * @param rule  假期规则
	 * @return
	 */
	public Float getValidLastRestDay(SysTimeLeaveDetail leaveDetail,SysTimeLeaveRule rule) {
		Float lastRestDay = Boolean.TRUE.equals(getFdIsLastAvail())
				&& getFdLastRestDay() != null
						? getFdLastRestDay() : 0;
		if(lastRestDay==0){
			return lastRestDay;
		}
		if (this.getFdLastValidDate() == null) {
			return lastRestDay;
		}
		if (leaveDetail.getFdEndTime()
				.before(SysTimeUtil.getDate(this.getFdLastValidDate(), 1))) {
			return lastRestDay;
		} else if (leaveDetail.getFdStartTime()
				.after(SysTimeUtil.getEndDate(getFdLastValidDate(), 0))) {
			return 0f;
		} else {
			// 请假区间溢出有效期时
			try {

				SysTimeLeaveTimeDto dto =  SysTimeUtil.getLeaveTimes(leaveDetail.getFdPerson(),
						leaveDetail.getFdStartTime(),
						SysTimeUtil.getEndDate(getFdLastValidDate(), 0),
						leaveDetail.getFdStartNoon(),
						2,
						rule.getFdStatDayType(), leaveDetail.getFdStatType(),rule.getFdSerialNo());
				Float days = dto.getLeaveTimeDays();
				return Math.min(days, lastRestDay);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0f;
	}

	public Float getFdLastRestDay() {
		return fdLastRestDay;
	}
	public void setFdLastRestDay(Float fdLastRestDay) {
		this.fdLastRestDay = fdLastRestDay;
	}

	/**
	 * 根据请假区间获取该假期类型本周期有效的剩余假期天数
	 * 
	 * @param leaveDetail 请假详情
	 * @param rule 假期类型规则
	 * @return
	 */
	public Float getValidRestDay(SysTimeLeaveDetail leaveDetail,
			SysTimeLeaveRule rule) {
		Float restDay = Boolean.TRUE.equals(getFdIsAvail())
				&& getFdRestDay() != null
						? getFdRestDay() : 0;
		if (restDay == 0) {
			return restDay;
		}
		if (this.getFdValidDate() == null) {
			return restDay;
		}
		if (leaveDetail.getFdEndTime()
				.before(SysTimeUtil.getDate(this.getFdValidDate(), 1))) {
			return restDay;
		} else if (leaveDetail.getFdStartTime()
				.after(SysTimeUtil.getEndDate(getFdValidDate(), 0))) {
			return 0f;
		} else {
			// 请假区间溢出有效期时
			try {
				SysTimeLeaveTimeDto dto =SysTimeUtil.getLeaveTimes(leaveDetail.getFdPerson(),
						leaveDetail.getFdStartTime(),
						SysTimeUtil.getEndDate(getFdValidDate(), 0),
						leaveDetail.getFdStartNoon(),
						2,
						rule.getFdStatDayType(), leaveDetail.getFdStatType(),rule.getFdSerialNo());
				Float days = dto.getLeaveTimeDays();
				return Math.min(days, restDay);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0f;
	}
	public Float getFdRestDay() {
		return fdRestDay;
	}

	public void setFdRestDay(Float fdRestDay) {
		this.fdRestDay = fdRestDay;
	}
	public Float getFdLastUsedDay() {
		return fdLastUsedDay;
	}

	public void setFdLastUsedDay(Float fdLastUsedDay) {
		this.fdLastUsedDay = fdLastUsedDay;
	}

	public Date getFdLastValidDate() {
		return fdLastValidDate;
	}

	public void setFdLastValidDate(Date fdLastValidDate) {
		this.fdLastValidDate = fdLastValidDate;
	}

	public SysTimeLeaveAmount getFdAmount() {
		return fdAmount;
	}

	public void setFdAmount(SysTimeLeaveAmount fdAmount) {
		this.fdAmount = fdAmount;
	}

	public Boolean getFdIsAvail() {
		if(fdIsAvail ==null){
			fdIsAvail =Boolean.TRUE;
		}
		return fdIsAvail;
	}

	public void setFdIsAvail(Boolean fdIsAvail) {
		this.fdIsAvail = fdIsAvail;
	}

	public Boolean getFdIsLastAvail() {
		return fdIsLastAvail;
	}

	public void setFdIsLastAvail(Boolean fdIsLastAvail) {
		this.fdIsLastAvail = fdIsLastAvail;
	}

	public String getFdLeaveType() {
		return fdLeaveType;
	}

	public void setFdLeaveType(String fdLeaveType) {
		this.fdLeaveType = fdLeaveType;
	}

	public Float getFdTotalDayMin() {
		return fdTotalDayMin;
	}

	public void setFdTotalDayMin(Float fdTotalDayMin) {
		this.fdTotalDayMin = fdTotalDayMin;
	}

	public Float getFdRestDayMin() {
		return fdRestDayMin;
	}

	public void setFdRestDayMin(Float fdRestDayMin) {
		this.fdRestDayMin = fdRestDayMin;
	}

	public Float getFdUsedDayMin() {
		return fdUsedDayMin;
	}

	public void setFdUsedDayMin(Float fdUsedDayMin) {
		this.fdUsedDayMin = fdUsedDayMin;
	}

	public Float getFdLastTotalDayMin() {
		return fdLastTotalDayMin;
	}

	public void setFdLastTotalDayMin(Float fdLastTotalDayMin) {
		this.fdLastTotalDayMin = fdLastTotalDayMin;
	}

	public Float getFdLastRestDayMin() {
		return fdLastRestDayMin;
	}

	public void setFdLastRestDayMin(Float fdLastRestDayMin) {
		this.fdLastRestDayMin = fdLastRestDayMin;
	}

	public Float getFdLastUsedDayMin() {
		return fdLastUsedDayMin;
	}

	public void setFdLastUsedDayMin(Float fdLastUsedDayMin) {
		this.fdLastUsedDayMin = fdLastUsedDayMin;
	}

	public String getFdSourceType() {
		return fdSourceType;
	}

	public void setFdSourceType(String fdSourceType) {
		this.fdSourceType = fdSourceType;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdAmount.fdId", "fdAmountId");
		}
		return toFormPropertyMap;
	}

	@Override
	public Class getFormClass() {
		return SysTimeLeaveAmountItemForm.class;
	}

	@Override
	public void setVersion(Integer version) {

	}

	@Override
	public Integer getVersion() {
		return 1;
	}
}
