package com.landray.kmss.sys.time.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.forms.SysTimeLeaveRuleForm;

/**
 * 请假规则
 *
 * @author cuiwj
 * @version 1.0 2018-08-31
 */
public class SysTimeLeaveRule extends BaseModel {

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
	private Integer fdStatType;

	public Integer getFdStatType() {
		return fdStatType;
	}

	public void setFdStatType(Integer fdStatType) {
		this.fdStatType = fdStatType;
	}

	/**
	 * 是否启用
	 */
	private Boolean fdIsAvailable;

	public Boolean getFdIsAvailable() {
		return fdIsAvailable;
	}

	public void setFdIsAvailable(Boolean fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}

	/**
	 * 排序号
	 */
	private Integer fdOrder;

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 1：按工作日计算，2：按自然日计算
	 */
	private Integer fdStatDayType;

	public Integer getFdStatDayType() {
		return fdStatDayType;
	}

	public void setFdStatDayType(Integer fdStatDayType) {
		this.fdStatDayType = fdStatDayType;
	}

	/**
	 * 一天换算为多少小时，选择按小时统计时显示(以全局配置参数为准?)
	 */
	private Integer fdDayConvertTime;

	public Integer getFdDayConvertTime() {
		return fdDayConvertTime;
	}

	public void setFdDayConvertTime(Integer fdDayConvertTime) {
		this.fdDayConvertTime = fdDayConvertTime;
	}

	private SysOrgPerson docCreator;
	// 是否允许加班天数自动转假期额度
	private Boolean fdOvertimeLeaveFlag = false;

	public Boolean getFdOvertimeLeaveFlag() {
		return fdOvertimeLeaveFlag;
	}

	public void setFdOvertimeLeaveFlag(Boolean fdOvertimeLeaveFlag) {
		this.fdOvertimeLeaveFlag = fdOvertimeLeaveFlag;
	}

	public SysOrgPerson getDocCreator() {
		return docCreator;
	}

	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	private Date docCreateTime;

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	private SysOrgPerson docAlteror;

	public SysOrgPerson getDocAlteror() {
		return docAlteror;
	}

	public void setDocAlteror(SysOrgPerson docAlteror) {
		this.docAlteror = docAlteror;
	}

	private Date docAlterTime;

	public Date getDocAlterTime() {
		return docAlterTime;
	}

	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 是否开启额度
	 */
	private Boolean fdIsAmount;

	public Boolean getFdIsAmount() {
		return fdIsAmount;
	}

	public void setFdIsAmount(Boolean fdIsAmount) {
		this.fdIsAmount = fdIsAmount;
	}

	/**
	 * 手动发放：1，自动发放：2,按规则发放(1月1日开始计算)：3,按规则发放(入职日期计算)：4
	 */
	private Integer fdAmountType;

	/**
	 * 手动发放：1，自动发放：2,按规则发放(1月1日开始计算)：3,按规则发放(入职日期计算)：4
	 */
	public Integer getFdAmountType() {
		return fdAmountType;
	}
	
	/**
	 * 手动发放：1，自动发放：2,按规则发放(1月1日开始计算)：3,按规则发放(入职日期计算)：4
	 */
	public void setFdAmountType(Integer fdAmountType) {
		this.fdAmountType = fdAmountType;
	}

	/**
	 * 自动发放天数
	 */
	private Float fdAutoAmount;

	public Float getFdAutoAmount() {
		return fdAutoAmount;
	}

	public void setFdAutoAmount(Float fdAutoAmount) {
		this.fdAutoAmount = fdAutoAmount;
	}

	/**
	 * 计算额度的方式
	 */
	private Integer fdAmountCalType;

	public Integer getFdAmountCalType() {
		return fdAmountCalType;
	}

	public void setFdAmountCalType(Integer fdAmountCalType) {
		this.fdAmountCalType = fdAmountCalType;
	}

	/**
	 * 规则明细
	 */
	private List<SysTimeLeaveRuleList> sysTimeLeaveRuleList;
	
	public List<SysTimeLeaveRuleList> getSysTimeLeaveRuleList() {
		return sysTimeLeaveRuleList;
	}

	public void setSysTimeLeaveRuleList(List<SysTimeLeaveRuleList> sysTimeLeaveRuleList) {
		this.sysTimeLeaveRuleList = sysTimeLeaveRuleList;
	}

	/**
	 * 延长有效期的天数
	 */
	private Integer fdValidDays;

	public Integer getFdValidDays() {
		return fdValidDays;
	}

	public void setFdValidDays(Integer fdValidDays) {
		this.fdValidDays = fdValidDays;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
			toFormPropertyMap.put("sysTimeLeaveRuleList",
					new ModelConvertor_ModelListToFormList(
							"sysTimeLeaveRuleList"));
		}
		return toFormPropertyMap;
	}

	@Override
	public Class getFormClass() {
		return SysTimeLeaveRuleForm.class;
	}

	// 额度计算规则
	private Integer fdAmountCalRule = 1;

	/**
	 * 额度计算规则 ，1：按本企业工龄计算，2：按连续工龄计算
	 * 
	 * @return
	 */
	public Integer getFdAmountCalRule() {
		return fdAmountCalRule == null ? 1 : fdAmountCalRule;
	}

	public void setFdAmountCalRule(Integer fdAmountCalRule) {
		this.fdAmountCalRule = fdAmountCalRule;
	}

}
