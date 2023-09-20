package com.landray.kmss.sys.time.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-08-31
 */
public interface ISysTimeLeaveRuleService extends IBaseService {
	/**
	 * 新增假期类型，执行假期额度发放
	 * @param leaveRule
	 * @param state
	 */
	public void executionAmountTask(SysTimeLeaveRule leaveRule,String state);
	public void addAmount(SysTimeLeaveRule leaveRule) throws Exception;

	public void updateAmount(SysTimeLeaveRule leaveRule) throws Exception;

	/**
	 * 已废弃,不建议使用
	 * 
	 * @param leaveName
	 *            假期类型名称
	 * @return
	 */
	public SysTimeLeaveRule getLeaveRuleByName(String leaveName);

	/**
	 * 根据假期编号获取对应假期类型规则
	 * 
	 * @param leaveType
	 * @return
	 */
	public SysTimeLeaveRule getLeaveRuleByType(String leaveType);

	/**
	 * 根据假期编号获取对应假期类型规则(兼容处理)
	 * 
	 * @param leaveType
	 * @return
	 */
	public SysTimeLeaveRule getLeaveRuleByType(Integer leaveType);

	/**
	 * 获取可用的假期类型列表
	 * 
	 * @param leaveType
	 *            为空,则获取所有列表
	 * @return
	 */
	public List<SysTimeLeaveRule> getLeaveRuleList(String leaveType);

	/**
	 * 获取允许加班自动转假期的假期类型列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<SysTimeLeaveRule> getLeaveRuleByOvertimeToLeave()
			throws Exception;
}
