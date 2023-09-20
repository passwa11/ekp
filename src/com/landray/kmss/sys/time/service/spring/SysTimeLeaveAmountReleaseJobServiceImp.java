package com.landray.kmss.sys.time.service.spring;

import com.google.common.collect.Lists;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountItemService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountReleaseJobService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author cuiwj
 * @version 1.0 2019-01-25
 */
public class SysTimeLeaveAmountReleaseJobServiceImp
		implements ISysTimeLeaveAmountReleaseJobService {

	private ISysTimeLeaveRuleService sysTimeLeaveRuleService;

	public void setSysTimeLeaveRuleService(
			ISysTimeLeaveRuleService sysTimeLeaveRuleService) {
		this.sysTimeLeaveRuleService = sysTimeLeaveRuleService;
	}

	private ISysTimeLeaveAmountService sysTimeLeaveAmountService;
	
	public void setSysTimeLeaveAmountService(
			ISysTimeLeaveAmountService sysTimeLeaveAmountService) {
		this.sysTimeLeaveAmountService = sysTimeLeaveAmountService;
	}

	private ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService;
	
	public void setSysTimeLeaveAmountItemService(
			ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService) {
		this.sysTimeLeaveAmountItemService = sysTimeLeaveAmountItemService;
	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysTimeLeaveAmountReleaseJobServiceImp.class);

	/**
	 * 获取所有有效人员
	 * 
	 * @return
	 */
	private List<String> getPersonIds() {
		try {
			IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
			return baseDao.getHibernateSession().createNativeQuery(
					"select fd_id from sys_org_element where fd_is_available=? and fd_is_business=? and fd_org_type=? "
							+ "and fd_id in (select fd_id from sys_org_person)")
					.setParameter(0, Boolean.TRUE)
					.setParameter(1, Boolean.TRUE).setParameter(2,
							SysOrgConstant.ORG_TYPE_PERSON)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return new ArrayList<String>();
	}

	/**
	 * 获取所有的假期类型
	 * 单独事务查询
	 * @return
	 */
	private List<SysTimeLeaveRule> getTimeLeaveRuleList() {
		boolean isException =false;
		TransactionStatus status = null;
		try {
			status = TransactionUtils.beginNewReadTransaction();
			List list= sysTimeLeaveRuleService.findList("", "");
			return list;
		} catch (Exception e) {
			isException =true;
			logger.error(e.getMessage(), e);
		}finally {
			if(isException && status !=null) {
				TransactionUtils.rollback(status);
			}else if(status !=null) {
				TransactionUtils.commit(status);
			}

		}
		return null;
	}
	@Override
	public void execute(SysQuartzJobContext jobContext) throws Exception {
		//假期类型
		List<SysTimeLeaveRule> list = getTimeLeaveRuleList();
		logger.debug("假期管理规则List："+list);
		//同步的假期ID
		Set<String> syncRuleIds=new HashSet<String>();
		//单独事务进行处理
		boolean isExceptionSave =false;
		TransactionStatus saveStatus = null;
		/**
		 * 单独事务，先删所有失效的假期类型
		 */
		try {
			saveStatus = TransactionUtils.beginNewTransaction();
			for (SysTimeLeaveRule leaveRule : list) {
				if (Boolean.TRUE.equals(leaveRule.getFdIsAvailable()) && Boolean.TRUE.equals(leaveRule.getFdIsAmount())) {
					logger.debug("假期管理规则Update方法："+leaveRule);
					syncRuleIds.add(leaveRule.getFdId());
				} else {
					logger.debug("假期管理规则delete方法："+leaveRule);
					sysTimeLeaveAmountItemService.deleteByLeaveType(leaveRule.getFdSerialNo());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			isExceptionSave =true;
			logger.error("额度发放失败："+e.getMessage());
		} finally {
			if(isExceptionSave && saveStatus !=null) {
				TransactionUtils.rollback(saveStatus);
			}else if(saveStatus !=null) {
				TransactionUtils.commit(saveStatus);
			}
		}
		/**
		 * 处理开启额度管理的假期类型
		 */
		if(!isExceptionSave) {
			if (CollectionUtils.isNotEmpty(syncRuleIds)) {
				//所有有效的人员
				List<String> personIds =getPersonIds();
				if(CollectionUtils.isNotEmpty(personIds) ) {
					//所有类型查询一遍人员即可，并且人员去重
					Set<String> setPersonIds = new HashSet<String>();
					setPersonIds.addAll(personIds);
					jobContext.logMessage("本次任务处理人数：" + personIds.size() + ",处理假期类型个数:" + syncRuleIds.size());
					sysTimeLeaveAmountService.updateOrAddAmomunt(syncRuleIds, Lists.newArrayList(setPersonIds));
				}else{
					jobContext.logMessage("没有需要发放的人员");
				}
			}else{
				jobContext.logMessage("没有需要处理的假期类型");
			}
		}
	}
}
