package com.landray.kmss.sys.time.model;


import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.time.forms.SysTimeLeaveAmountForm;
import com.landray.kmss.sys.time.forms.SysTimeLeaveRuleListForm;

/**
 *
 * @author 郑鑫
 * @version 1.0 2018-12-12
 */
public class SysTimeLeaveRuleList extends BaseModel {
	
	/**
	 * 入职是否满一年	1：不满一年、2：一年以上
	 */
	private String fdEntryType;

	/**
	 * 额度类型	1:固定额度、2：逐渐递增、3：公式计算
	 */
	private String fdQuotaType;
	
	/**
	 * 假期天数
	 */
	private String fdHolidayDays;

	/**
	 * 递增天数
	 */
	private String fdIncreaseDays;
	
	/**
	 * 公式常量
	 */
	private String fdCountDays;
	
	/**
	 * 开始司龄
	 */
	private String fdStartEntry;
	
	/**
	 * 结束司龄
	 */
	private String fdEndEntry;
	
	/**
	 * 假期规则
	 */
	private SysTimeLeaveRule fdRules;
	

	public String getFdEntryType() {
		return fdEntryType;
	}

	public void setFdEntryType(String fdEntryType) {
		this.fdEntryType = fdEntryType;
	}

	public String getFdQuotaType() {
		return fdQuotaType;
	}

	public void setFdQuotaType(String fdQuotaType) {
		this.fdQuotaType = fdQuotaType;
	}

	public String getFdHolidayDays() {
		return fdHolidayDays;
	}

	public void setFdHolidayDays(String fdHolidayDays) {
		this.fdHolidayDays = fdHolidayDays;
	}

	public String getFdIncreaseDays() {
		return fdIncreaseDays;
	}

	public void setFdIncreaseDays(String fdIncreaseDays) {
		this.fdIncreaseDays = fdIncreaseDays;
	}

	public String getFdCountDays() {
		return fdCountDays;
	}

	public void setFdCountDays(String fdCountDays) {
		this.fdCountDays = fdCountDays;
	}

	public String getFdStartEntry() {
		return fdStartEntry;
	}

	public void setFdStartEntry(String fdStartEntry) {
		this.fdStartEntry = fdStartEntry;
	}

	public String getFdEndEntry() {
		return fdEndEntry;
	}

	public void setFdEndEntry(String fdEndEntry) {
		this.fdEndEntry = fdEndEntry;
	}

	public SysTimeLeaveRule getFdRules() {
		return fdRules;
	}

	public void setFdRules(SysTimeLeaveRule fdRules) {
		this.fdRules = fdRules;
	}

	public static void setToFormPropertyMap(ModelToFormPropertyMap toFormPropertyMap) {
		SysTimeLeaveRuleList.toFormPropertyMap = toFormPropertyMap;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdRules.fdId", "fdRulesId");
		}
		return toFormPropertyMap;
	}

	@Override
    public Class getFormClass() {
		return SysTimeLeaveRuleListForm.class;
	}

}
