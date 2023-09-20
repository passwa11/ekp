package com.landray.kmss.sys.time.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmount;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-13
 */
public class SysTimeLeaveAmountItemForm extends ExtendForm {

	/**
	 * 是否无限制（暂时没用）
	 */
	private String fdIsNoLimit;

	/**
	 * 是否累加
	 */
	private String fdIsAccumulate;

	/**
	 * 是否自动发放
	 */
	private String fdIsAuto;
	/**
	 * 假期名称
	 */
	private String fdLeaveName;

	private String fdLeaveType;

	/**
	 * 总天数
	 */
	private String fdTotalDay;
	private String fdTotalDayMin;

	/**
	 * 剩余天数
	 */
	private String fdRestDay;
	private String fdRestDayMin;

	/**
	 * 已使用天数
	 */
	private String fdUsedDay;
	private String fdUsedDayMin;

	/**
	 * 有效期
	 */
	private String fdValidDate;

	/**
	 * 是否有效
	 */
	private String fdIsAvail;

	/**
	 * 总天数（上个周期）
	 */
	private String fdLastTotalDay;
	private String fdLastTotalDayMin;

	/**
	 * 剩余天数（上个周期）
	 */
	private String fdLastRestDay;
	private String fdLastRestDayMin;

	/**
	 * 已使用天数（上个周期）
	 */
	private String fdLastUsedDay;
	private String fdLastUsedDayMin;

	/**
	 * 有效期（上个周期）
	 */
	private String fdLastValidDate;

	/**
	 * 上个周期是否有效
	 */
	private String fdIsLastAvail;

	/**
	 * 按天，按半天，按小时：1,2,3
	 */
	private String fdStatType;

	public String getFdStatType() {
		return fdStatType;
	}

	public void setFdStatType(String fdStatType) {
		this.fdStatType = fdStatType;
	}

	/**
	 * 剩余总天数
	 */
	public String getFdRestTotalDay() {
		Float restTotal = 0f;
		try {
			if (StringUtil.isNotNull(getFdRestDay())
					&& "true".equals(getFdIsAvail())) {
				restTotal += Float.parseFloat(getFdRestDay());
			}
			if (StringUtil.isNotNull(getFdLastRestDay())
					&& "true".equals(getFdIsLastAvail())) {
				restTotal += Float.parseFloat(getFdLastRestDay());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return restTotal + "";
	}

	public void setFdRestTotalDay(String fdRestTotalDay) {
	}

	private String fdAmountId;
	private String fdSourceType;

	public String getFdIsNoLimit() {
		return fdIsNoLimit;
	}

	public void setFdIsNoLimit(String fdIsNoLimit) {
		this.fdIsNoLimit = fdIsNoLimit;
	}

	public String getFdIsAccumulate() {
		return fdIsAccumulate;
	}

	public void setFdIsAccumulate(String fdIsAccumulate) {
		this.fdIsAccumulate = fdIsAccumulate;
	}

	public String getFdIsAuto() {
		return fdIsAuto;
	}

	public void setFdIsAuto(String fdIsAuto) {
		this.fdIsAuto = fdIsAuto;
	}

	public String getFdLeaveName() {
		return fdLeaveName;
	}

	public void setFdLeaveName(String fdLeaveName) {
		this.fdLeaveName = fdLeaveName;
	}

	public String getFdTotalDay() {
		return fdTotalDay;
	}

	public void setFdTotalDay(String fdTotalDay) {
		this.fdTotalDay = fdTotalDay;
	}

	public String getFdRestDay() {
		return fdRestDay;
	}

	public void setFdRestDay(String fdRestDay) {
		this.fdRestDay = fdRestDay;
	}

	public String getFdUsedDay() {
		return fdUsedDay;
	}

	public void setFdUsedDay(String fdUsedDay) {
		this.fdUsedDay = fdUsedDay;
	}

	public String getFdValidDate() {
		return fdValidDate;
	}

	public void setFdValidDate(String fdValidDate) {
		this.fdValidDate = fdValidDate;
	}

	public String getFdLastTotalDay() {
		return fdLastTotalDay;
	}

	public void setFdLastTotalDay(String fdLastTotalDay) {
		this.fdLastTotalDay = fdLastTotalDay;
	}

	public String getFdLastRestDay() {
		return fdLastRestDay;
	}

	public void setFdLastRestDay(String fdLastRestDay) {
		this.fdLastRestDay = fdLastRestDay;
	}

	public String getFdLastUsedDay() {
		return fdLastUsedDay;
	}

	public void setFdLastUsedDay(String fdLastUsedDay) {
		this.fdLastUsedDay = fdLastUsedDay;
	}

	public String getFdLastValidDate() {
		return fdLastValidDate;
	}

	public void setFdLastValidDate(String fdLastValidDate) {
		this.fdLastValidDate = fdLastValidDate;
	}

	public String getFdAmountId() {
		return fdAmountId;
	}

	public void setFdAmountId(String fdAmountId) {
		this.fdAmountId = fdAmountId;
	}

	public String getFdIsAvail() {
		return fdIsAvail;
	}

	public void setFdIsAvail(String fdIsAvail) {
		this.fdIsAvail = fdIsAvail;
	}

	public String getFdIsLastAvail() {
		return fdIsLastAvail;
	}

	public void setFdIsLastAvail(String fdIsLastAvail) {
		this.fdIsLastAvail = fdIsLastAvail;
	}

	public String getFdLeaveType() {
		return fdLeaveType;
	}

	public void setFdLeaveType(String fdLeaveType) {
		this.fdLeaveType = fdLeaveType;
	}

	public String getFdTotalDayMin() {
		return fdTotalDayMin;
	}

	public void setFdTotalDayMin(String fdTotalDayMin) {
		this.fdTotalDayMin = fdTotalDayMin;
	}

	public String getFdRestDayMin() {
		return fdRestDayMin;
	}

	public void setFdRestDayMin(String fdRestDayMin) {
		this.fdRestDayMin = fdRestDayMin;
	}

	public String getFdUsedDayMin() {
		return fdUsedDayMin;
	}

	public void setFdUsedDayMin(String fdUsedDayMin) {
		this.fdUsedDayMin = fdUsedDayMin;
	}

	public String getFdLastTotalDayMin() {
		return fdLastTotalDayMin;
	}

	public void setFdLastTotalDayMin(String fdLastTotalDayMin) {
		this.fdLastTotalDayMin = fdLastTotalDayMin;
	}

	public String getFdLastRestDayMin() {
		return fdLastRestDayMin;
	}

	public void setFdLastRestDayMin(String fdLastRestDayMin) {
		this.fdLastRestDayMin = fdLastRestDayMin;
	}

	public String getFdLastUsedDayMin() {
		return fdLastUsedDayMin;
	}

	public void setFdLastUsedDayMin(String fdLastUsedDayMin) {
		this.fdLastUsedDayMin = fdLastUsedDayMin;
	}

	public String getFdSourceType() {
		return fdSourceType;
	}

	public void setFdSourceType(String fdSourceType) {
		this.fdSourceType = fdSourceType;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdLeaveName = null;
		fdTotalDay = null;
		fdRestDay = null;
		fdUsedDay = null;
		fdValidDate = null;
		fdLastTotalDay = null;
		fdLastRestDay = null;
		fdLastUsedDay = null;
		fdLastValidDate = null;
		fdAmountId = null;
		fdIsAccumulate = null;
		fdIsNoLimit = null;
		fdLeaveType = null;
		fdTotalDayMin = null;
		fdRestDayMin = null;
		fdUsedDayMin = null;
		fdLastTotalDayMin = null;
		fdLastRestDayMin = null;
		fdLastUsedDayMin = null;
		fdSourceType = null;

		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdAmountId", new FormConvertor_IDToModel(
					"fdAmount", SysTimeLeaveAmount.class));
		}
		return toModelPropertyMap;
	}

	@Override
    public Class getModelClass() {
		return SysTimeLeaveAmountItem.class;
	}

	// 排序号(用于页面呈现)
	private Integer fdOrder;
	private Date ruleCreateTime;

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	public Date getRuleCreateTime() {
		return ruleCreateTime;
	}

	public void setRuleCreateTime(Date ruleCreateTime) {
		this.ruleCreateTime = ruleCreateTime;
	}

}
