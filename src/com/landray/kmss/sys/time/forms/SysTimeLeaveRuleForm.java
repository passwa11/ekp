package com.landray.kmss.sys.time.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 请假规则
 *
 * @author cuiwj
 * @version 1.0 2018-08-31
 */
public class SysTimeLeaveRuleForm extends ExtendForm {

	/**
	 * 假期名称
	 */
	private String fdName;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 假期编码，唯一
	 */
	private String fdSerialNo;

	public String getFdSerialNo() {
		return fdSerialNo;
	}

	public void setFdSerialNo(String fdSerialNo) {
		this.fdSerialNo = fdSerialNo;
	}

	/**
	 * 1：按天，2：按半天，3，按小时
	 */
	private String fdStatType;

	public String getFdStatType() {
		return fdStatType;
	}

	public void setFdStatType(String fdStatType) {
		this.fdStatType = fdStatType;
	}

	/**
	 * 是否启用
	 */
	private String fdIsAvailable;

	public String getFdIsAvailable() {
		return fdIsAvailable;
	}

	public void setFdIsAvailable(String fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}

	/**
	 * 排序号
	 */
	private String fdOrder;

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 1：按工作日计算，2：按自然日计算
	 */
	private String fdStatDayType;

	public String getFdStatDayType() {
		return fdStatDayType;
	}

	public void setFdStatDayType(String fdStatDayType) {
		this.fdStatDayType = fdStatDayType;
	}

	/**
	 * 一天换算为多少小时，选择按小时统计时显示
	 */
	private String fdDayConvertTime;

	public String getFdDayConvertTime() {
		return fdDayConvertTime;
	}

	public void setFdDayConvertTime(String fdDayConvertTime) {
		this.fdDayConvertTime = fdDayConvertTime;
	}

	/**
	 * 是否开启额度
	 */
	private String fdIsAmount;
	// 是否加班转假期额度
	private String fdOvertimeLeaveFlag;

	public String getFdIsAmount() {
		return fdIsAmount;
	}

	public void setFdIsAmount(String fdIsAmount) {
		this.fdIsAmount = fdIsAmount;
	}

	public String getFdOvertimeLeaveFlag() {
		return fdOvertimeLeaveFlag;
	}

	public void setFdOvertimeLeaveFlag(String fdOvertimeLeaveFlag) {
		this.fdOvertimeLeaveFlag = fdOvertimeLeaveFlag;
	}

	/**
	 * 手动发放：1，自动发放：2,按规则自动发放：3
	 */
	private String fdAmountType;

	public String getFdAmountType() {
		return fdAmountType;
	}

	public void setFdAmountType(String fdAmountType) {
		this.fdAmountType = fdAmountType;
	}

	/**
	 * 自动发放天数
	 */
	private String fdAutoAmount;

	public String getFdAutoAmount() {
		return fdAutoAmount;
	}

	public void setFdAutoAmount(String fdAutoAmount) {
		this.fdAutoAmount = fdAutoAmount;
	}

	/**
	 * 计算额度的方式
	 */
	private String fdAmountCalType;

	public String getFdAmountCalType() {
		return fdAmountCalType;
	}

	public void setFdAmountCalType(String fdAmountCalType) {
		this.fdAmountCalType = fdAmountCalType;
	}

	/**
	 * 延长有效期的天数
	 */
	private String fdValidDays;

	public String getFdValidDays() {
		return fdValidDays;
	}

	public void setFdValidDays(String fdValidDays) {
		this.fdValidDays = fdValidDays;
	}
	
	/**
	 * 假期管理明细表单
	 */
	protected AutoArrayList sysTimeLeaveRuleList = new AutoArrayList(
			SysTimeLeaveRuleListForm.class);
	

	public AutoArrayList getSysTimeLeaveRuleList() {
		return sysTimeLeaveRuleList;
	}

	public void setSysTimeLeaveRuleList(AutoArrayList sysTimeLeaveRuleList) {
		this.sysTimeLeaveRuleList = sysTimeLeaveRuleList;
	}

	private String docCreatorId;

	private String docAlterorId;

	private String docCreateTime;

	private String docAlterTime;

	public String getDocCreatorId() {
		return docCreatorId;
	}

	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	public String getDocAlterorId() {
		return docAlterorId;
	}

	public void setDocAlterorId(String docAlterorId) {
		this.docAlterorId = docAlterorId;
	}

	public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getDocAlterTime() {
		return docAlterTime;
	}

	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 是否更新额度数据
	 */
	private String isUpdateAmount;

	public String getIsUpdateAmount() {
		return isUpdateAmount;
	}

	public void setIsUpdateAmount(String isUpdateAmount) {
		this.isUpdateAmount = isUpdateAmount;
	}
	
	// 额度计算规则
	private String fdAmountCalRule;

	/**
	 * 额度计算规则 ，1：按本企业工龄计算，2：按连续工龄计算
	 * 
	 * @return
	 */
	public String getFdAmountCalRule() {
		return fdAmountCalRule;
	}

	public void setFdAmountCalRule(String fdAmountCalRule) {
		this.fdAmountCalRule = fdAmountCalRule;
	}

	@Override
	public Class getModelClass() {
		return SysTimeLeaveRule.class;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdSerialNo = null;
		fdStatType = null;
		fdIsAvailable = null;
		fdOrder = null;
		fdStatDayType = null;
		fdDayConvertTime = null;
		fdIsAmount = null;
		fdAmountType = null;
		fdAutoAmount = null;
		fdAmountCalType = null;
		fdValidDays = null;
		docCreatorId = null;
		docAlterorId = null;
		docCreateTime= null;
		docAlterTime = null;
		fdOvertimeLeaveFlag = null;
		fdAmountCalRule = null;
		sysTimeLeaveRuleList= new AutoArrayList(SysTimeLeaveRuleListForm.class);

		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgPerson.class));
			toModelPropertyMap.put("docAlterorId", new FormConvertor_IDToModel(
					"docAlteror", SysOrgPerson.class));
			toModelPropertyMap.put("sysTimeLeaveRuleList",
					new FormConvertor_FormListToModelList("sysTimeLeaveRuleList",
							"fdRulesId"));
		}
		return toModelPropertyMap;
	}

}
