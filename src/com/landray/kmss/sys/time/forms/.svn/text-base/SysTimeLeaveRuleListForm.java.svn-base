package com.landray.kmss.sys.time.forms;


import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.model.SysTimeLeaveRuleList;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author 郑鑫
 * @version 1.0 2019-08-23
 */
public class SysTimeLeaveRuleListForm extends SysTimeImportForm {

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
	private String fdRulesId;
	
	/**
	 * 假期规则
	 */
	private AutoArrayList fdRulesItems = new AutoArrayList(
			SysTimeLeaveRuleForm.class);
	

	public AutoArrayList getFdRulesItems() {
		return fdRulesItems;
	}

	public void setFdRulesItems(AutoArrayList fdRulesItems) {
		this.fdRulesItems = fdRulesItems;
	}

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

	public String getFdRulesId() {
		return fdRulesId;
	}

	public void setFdRulesId(String fdRulesId) {
		this.fdRulesId = fdRulesId;
	}
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		
		fdEntryType = null;
		fdQuotaType = null;
		fdHolidayDays = null;
		fdIncreaseDays = null;
		fdCountDays = null;
		fdStartEntry = null;
		fdEndEntry = null;
		fdRulesId = null;
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;
	
	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdRulesId", new FormConvertor_IDToModel(
					"fdRules", SysTimeLeaveRule.class));
		}
		return toModelPropertyMap;
	}

	@Override
    public Class getModelClass() {
		return SysTimeLeaveRuleList.class;
	}

}
