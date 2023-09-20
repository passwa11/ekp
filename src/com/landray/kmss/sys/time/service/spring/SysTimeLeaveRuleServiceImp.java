package com.landray.kmss.sys.time.service.spring;

import com.landray.kmss.common.concurrent.KMSSCommonThreadUtil;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.framework.spring.event.interfaces.IEventCallBack;
import com.landray.kmss.framework.spring.event.transaction.EventOfTransactionCommit;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountItemService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-08-31
 */
public class SysTimeLeaveRuleServiceImp extends BaseServiceImp
		implements ISysTimeLeaveRuleService, IEventMulticasterAware {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysTimeLeaveRuleServiceImp.class);

	private ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService;

	public void setSysTimeLeaveAmountItemService(
			ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService) {
		this.sysTimeLeaveAmountItemService = sysTimeLeaveAmountItemService;
	}

	private ISysTimeLeaveAmountService sysTimeLeaveAmountService;

	public ISysTimeLeaveAmountService getSysTimeLeaveAmountService() {
		if (sysTimeLeaveAmountService == null) {
			sysTimeLeaveAmountService = (ISysTimeLeaveAmountService) SpringBeanUtil
					.getBean("sysTimeLeaveAmountService");
		}
		return sysTimeLeaveAmountService;
	}

	@Override
	public SysTimeLeaveRule getLeaveRuleByName(String leaveName) {
		SysTimeLeaveRule sysTimeLeaveRule = null;
		try {
			if (StringUtil.isNotNull(leaveName)) {
				HQLInfo hqlInfo = new HQLInfo();
				String whereBlock = "sysTimeLeaveRule.fdName=:leaveName and sysTimeLeaveRule.fdIsAvailable=:isAvailable";
				hqlInfo.setParameter("leaveName", leaveName);
				hqlInfo.setParameter("isAvailable", true);
				hqlInfo.setWhereBlock(whereBlock);
				hqlInfo.setOrderBy("sysTimeLeaveRule.docCreateTime");
				SysTimeLeaveRule leaveRuleList = (SysTimeLeaveRule) findFirstOne(hqlInfo);
				if (leaveRuleList != null) {
					return leaveRuleList;
				}
			}
		} catch (Exception e) {
		}
		return sysTimeLeaveRule;
	}

	@Override
	public SysTimeLeaveRule getLeaveRuleByType(String leaveType) {
		SysTimeLeaveRule sysTimeLeaveRule = null;
		try {
			if (StringUtil.isNotNull(leaveType)) {
				HQLInfo hqlInfo = new HQLInfo();
				String whereBlock = "sysTimeLeaveRule.fdSerialNo=:leaveType and sysTimeLeaveRule.fdIsAvailable=:isAvailable";
				hqlInfo.setParameter("leaveType", leaveType);
				hqlInfo.setParameter("isAvailable", true);
				hqlInfo.setWhereBlock(whereBlock);
				hqlInfo.setOrderBy("sysTimeLeaveRule.docCreateTime");
				SysTimeLeaveRule leaveRuleList = (SysTimeLeaveRule) findFirstOne(hqlInfo);
				if (leaveRuleList != null) {
					return leaveRuleList;
				}
			}
		} catch (Exception e) {
		}
		return sysTimeLeaveRule;
	}

	@Override
	public SysTimeLeaveRule getLeaveRuleByType(Integer leaveType) {
		if (leaveType == null) {
			return null;
		}
		List<SysTimeLeaveRule> ruleList = getLeaveRuleList("");
		for (SysTimeLeaveRule rule : ruleList) {
			if (leaveType.equals(Integer.valueOf(rule.getFdSerialNo()))) {
				return rule;
			}
		}
		return null;
	}


	@Override
	public List<SysTimeLeaveRule> getLeaveRuleList(String leaveType) {
		List<SysTimeLeaveRule> leaveRuleList = new ArrayList<SysTimeLeaveRule>();
		try {
				HQLInfo hqlInfo = new HQLInfo();
				String whereBlock = " sysTimeLeaveRule.fdIsAvailable=:isAvailable";
				if (StringUtil.isNotNull(leaveType)) {
					whereBlock += " and sysTimeLeaveRule.fdSerialNo like :leaveType";
					hqlInfo.setParameter("leaveType", "%" + leaveType + "%");
				}
				hqlInfo.setParameter("isAvailable", true);
				hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setOrderBy("sysTimeLeaveRule.docCreateTime");
				leaveRuleList = findList(hqlInfo);
		} catch (Exception e) {
		}
		return leaveRuleList;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysTimeLeaveRule sysTimeLeaveRule = (SysTimeLeaveRule) modelObj;
		sysTimeLeaveRule.setDocCreateTime(new Date());
		sysTimeLeaveRule.setDocCreator(UserUtil.getUser());
		return super.add(sysTimeLeaveRule);
	}

	private IEventMulticaster multicaster;

	@Override
	public void setEventMulticaster(IEventMulticaster multicaster) {
		this.multicaster = multicaster;
	}

	/**
	 * 新增假期类型，执行假期额度发放
	 * @param leaveRule
	 * @param state
	 */
	@Override
	public void executionAmountTask(SysTimeLeaveRule leaveRule,String state){
		multicaster.attatchEvent(
				new EventOfTransactionCommit(StringUtils.EMPTY),
				new IEventCallBack() {
					@Override
					public void execute(ApplicationEvent arg0)
							throws Throwable {
						AddOrUpdateAmount addOrUpdateAmount=new AddOrUpdateAmount(state,leaveRule);
						KMSSCommonThreadUtil.execute(addOrUpdateAmount);
					}
				});
	}
	/**
	 * 线程批量提交内部类
	 *
	 */
	private class AddOrUpdateAmount implements Runnable {
		SysTimeLeaveRule leaveRule;
		String state;
		public AddOrUpdateAmount(String state,SysTimeLeaveRule leaveRule) {
			this.state=state;
			this.leaveRule=leaveRule;
		}

		@Override
		public synchronized  void run() {
			try {
				if ("add".equals(state)) {
					addAmount(leaveRule);
				}
				if ("update".equals(state)) {
					updateAmount(leaveRule);
				}
			} catch (Exception e) {
				logger.error("页面执行发放假期额度失败",e);
			}
		}
	}
	/**
	 * 新增额度数据
	 * @param leaveRule
	 * @throws Exception
	 */
	@Override
	public void addAmount(SysTimeLeaveRule leaveRule) throws Exception {
		if (leaveRule == null) {
			throw new Exception("假期类型为空");
		}
		if (Boolean.TRUE.equals(leaveRule.getFdIsAvailable())
				&& Boolean.TRUE.equals(leaveRule.getFdIsAmount())
				&& Integer.valueOf(2).equals(leaveRule.getFdAmountType())
				&& leaveRule.getFdAutoAmount() != null) {// 有效，开启额度，自动发放的
			getSysTimeLeaveAmountService().updateOrAddAmomunt(leaveRule.getFdId());
		}
	}

	/**
	 * 更新额度数据
	 * @param leaveRule
	 */
	@Override
	public void updateAmount(SysTimeLeaveRule leaveRule) throws Exception {
		if (leaveRule == null) {
			throw new Exception("假期类型为空");
		}
		if (Boolean.TRUE.equals(leaveRule.getFdIsAvailable())
				&& Boolean.TRUE.equals(leaveRule.getFdIsAmount())) {
			// 有效，开启额度
			getSysTimeLeaveAmountService().updateOrAddAmomunt(leaveRule.getFdId());
		}
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		SysTimeLeaveRule sysTimeLeaveRule = (SysTimeLeaveRule) modelObj;
		if (!Boolean.TRUE.equals(sysTimeLeaveRule.getFdIsAvailable())
				|| !Boolean.TRUE.equals(sysTimeLeaveRule.getFdIsAmount())) {
			// 删除额度数据
			sysTimeLeaveAmountItemService
					.deleteByLeaveType(sysTimeLeaveRule.getFdSerialNo());
		}
		sysTimeLeaveRule.setDocAlterTime(new Date());
		sysTimeLeaveRule.setDocAlteror(UserUtil.getUser());
		super.update(sysTimeLeaveRule);
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		SysTimeLeaveRule sysTimeLeaveRule = (SysTimeLeaveRule) modelObj;
		// 删除额度数据
		sysTimeLeaveAmountItemService
				.deleteByLeaveType(sysTimeLeaveRule.getFdSerialNo());
		super.delete(modelObj);
	}

	@Override
	public List<SysTimeLeaveRule> getLeaveRuleByOvertimeToLeave()
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "sysTimeLeaveRule.fdIsAmount=:fdIsAmount and sysTimeLeaveRule.fdOvertimeLeaveFlag=:fdOvertimeLeaveFlag and sysTimeLeaveRule.fdIsAvailable=:isAvailable ";
		hqlInfo.setParameter("isAvailable", true);
		hqlInfo.setParameter("fdIsAmount", true);
		hqlInfo.setParameter("fdOvertimeLeaveFlag", true);
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy("sysTimeLeaveRule.docCreateTime");
		List<SysTimeLeaveRule> leaveRuleList = findList(hqlInfo);
		if (leaveRuleList == null || leaveRuleList.isEmpty()) {
			return new ArrayList<SysTimeLeaveRule>();
		}
		return leaveRuleList;
	}

}
