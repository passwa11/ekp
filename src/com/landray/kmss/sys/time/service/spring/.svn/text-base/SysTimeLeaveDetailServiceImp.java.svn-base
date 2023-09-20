package com.landray.kmss.sys.time.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.component.locker.interfaces.AcquireLockFailException;
import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.component.locker.interfaces.VersionInconsistencyException;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.framework.spring.event.interfaces.IEventCallBack;
import com.landray.kmss.framework.spring.event.transaction.EventOfTransactionCommit;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.forms.SysTimeImportForm;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountFlow;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveLastAmount;
import com.landray.kmss.sys.time.model.SysTimeLeaveResume;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeImportService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountFlowService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountItemService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveDetailService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveLastAmountService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveResumeService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.sys.time.util.SysTimeImportUtil;
import com.landray.kmss.sys.time.util.SysTimeLeaveTimeDto;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.transaction.TransactionStatus;

import javax.persistence.OptimisticLockException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-24
 */
public class SysTimeLeaveDetailServiceImp extends BaseServiceImp
		implements ISysTimeLeaveDetailService, ISysTimeImportService,
		ApplicationContextAware, IEventMulticasterAware {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysTimeLeaveDetailServiceImp.class);

	private ISysTimeLeaveAmountService sysTimeLeaveAmountService;
	private ISysTimeLeaveAmountFlowService sysTimeLeaveAmountFlowService;

	public void setSysTimeLeaveAmountService(
			ISysTimeLeaveAmountService sysTimeLeaveAmountService) {
		this.sysTimeLeaveAmountService = sysTimeLeaveAmountService;
	}

	private ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService;

	public void setSysTimeLeaveAmountItemService(
			ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService) {
		this.sysTimeLeaveAmountItemService = sysTimeLeaveAmountItemService;
	}
	
	private ISysTimeLeaveRuleService sysTimeLeaveRuleService;
	
	public void setSysTimeLeaveRuleService(
			ISysTimeLeaveRuleService sysTimeLeaveRuleService) {
		this.sysTimeLeaveRuleService = sysTimeLeaveRuleService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setSysTimeLeaveAmountFlowService(
			ISysTimeLeaveAmountFlowService sysTimeLeaveAmountFlowService) {
		this.sysTimeLeaveAmountFlowService = sysTimeLeaveAmountFlowService;
	}

	private ISysTimeLeaveLastAmountService sysTimeLeaveLastAmountService;

	public ISysTimeLeaveLastAmountService getSysTimeLeaveLastAmountService() {
		if (sysTimeLeaveLastAmountService == null) {
			sysTimeLeaveLastAmountService = (ISysTimeLeaveLastAmountService) SpringBeanUtil
					.getBean("sysTimeLeaveLastAmountService");
		}
		return sysTimeLeaveLastAmountService;
	}

	private ISysTimeLeaveResumeService sysTimeLeaveResumeService;

	public ISysTimeLeaveResumeService getSysTimeLeaveResumeService() {
		if (sysTimeLeaveResumeService == null) {
			sysTimeLeaveResumeService = (ISysTimeLeaveResumeService) SpringBeanUtil
					.getBean("sysTimeLeaveResumeService");
		}
		return sysTimeLeaveResumeService;
	}
	IComponentLockService componentLockService;
	private IComponentLockService getGlobalLockService(){
		if(componentLockService ==null) {
			componentLockService = (IComponentLockService) SpringBeanUtil.getBean("componentLockService");
		}
		return componentLockService;
	}
	/**
	 * 更新考勤数据
	 * 
	 * @param id
	 * @throws Exception
	 */
	@Override
	public void updateAttend(String id) throws Exception {
		SysTimeLeaveDetail leaveDetail = (SysTimeLeaveDetail) findByPrimaryKey(
				id);
		if (leaveDetail == null) {
			throw new Exception("找不到假期明细");
		}
		// 扣减成功，或者，允许更新考勤
		if (Boolean.TRUE.equals(leaveDetail.getFdCanUpdateAttend() )
				|| Integer.valueOf(1).equals(leaveDetail.getFdOprStatus())) {
			Integer updateStatus = leaveDetail.getFdUpdateAttendStatus();
			if (Integer.valueOf(1).equals(updateStatus)) {
				// 已更新过一次的，不再更新
				return;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("leaveDetailId", leaveDetail.getFdId());
			applicationContext
					.publishEvent(new Event_Common("updateAttend", params));
		}
	}

	/**
	 * 批量更新考勤
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@Override
	public void updateAttend(String[] ids) throws Exception {
		for (int i = 0; i < ids.length; i++) {
			updateAttend(ids[i]);
		}
	}

	/**
	 * 批量扣减
	 * 
	 * @param ids
	 * @throws Exception
	 */
	@Override
	public void updateDeduct(String[] ids) throws Exception {
		for (int i = 0; i < ids.length; i++) {
			updateDeduct(ids[i]);
		}
	}

	private static Map<String,Integer> TRY_LOCK_LOG_MAP =new HashMap<>();
	private static Integer MAX_TRY_COUNT =50;
	@Override
	public String updateDeduct(String id) throws Exception {
		return updateDeduct((SysTimeLeaveDetail) findByPrimaryKey(id));
	}

	/**
	 * 扣减额度
	 * 
	 * @param leaveDetail
	 * @throws Exception
	 */
	@Override
	public String updateDeduct(SysTimeLeaveDetail leaveDetail) throws Exception {
		return updateDeduct(leaveDetail,2);
	}

	@Override
	public String updateDeduct(SysTimeLeaveDetail leaveDetail, int status) throws Exception {
		if (leaveDetail == null) {
			throw new Exception("找不到假期明细");
		}
		String msg =null;
		Date startTime = leaveDetail.getFdStartTime();
		Date endTime = leaveDetail.getFdEndTime();
		String leaveType = leaveDetail.getFdLeaveType();
		SysOrgPerson person = leaveDetail.getFdPerson();
		if (person == null || startTime == null || endTime == null
				|| startTime.getTime() > endTime.getTime()) {
			throw new Exception("人员或时间数据有误");
		}
		if (Integer.valueOf(1).equals(leaveDetail.getFdOprStatus())) {
			// 扣减成功的不能再扣减
			return msg;
		}

		if (checkDateRepeat(leaveDetail)) {
			msg =ResourceUtil.getString("sys-time:sysTimeLeaveDetail.reason.dateRepeat");
			// 扣减失败，日期重复
			updateLeaveDetail(leaveDetail, status, false, msg);
			return msg;
		}
		if (StringUtil.isNull(leaveType)) {
			msg =ResourceUtil.getString("sys-time:sysTimeLeaveDetail.reason.leaveNull");
			// 扣减失败，请假类型为空
			updateLeaveDetail(leaveDetail, status, false, msg);
			return msg;
		}
		SysTimeLeaveRule leaveRule = getLeaveaRule(leaveType);
		if (leaveRule == null) {
			msg =ResourceUtil.getString("sys-time:sysTimeLeaveDetail.reason.leaveNotFound");
			// 扣减失败，找不到该假期类型
			updateLeaveDetail(leaveDetail, status, false, msg);
			return msg;
		}
		if (SysTimeUtil.compareDecimal(leaveDetail.getFdTotalTime(), 0f) == 0) {
			// 请假时长为0
			// 查询是否被安排补班，重新计算时间
			SysTimeLeaveRule rule = sysTimeLeaveRuleService
					.getLeaveRuleByType(leaveDetail.getFdLeaveType());
			Integer statDayType = rule.getFdStatDayType();
			SysTimeLeaveTimeDto dto = SysTimeUtil.getLeaveTimes(person,
					startTime,
					endTime, leaveDetail.getFdStartNoon(),
					leaveDetail.getFdEndNoon(), statDayType,
					leaveDetail.getFdStatType(),leaveType);
			int leaveTimes =dto.getLeaveTimeMins();
			if (leaveTimes > 0) {
				leaveDetail.setFdTotalTime((float) leaveTimes);
				leaveDetail.setFdLeaveTime(dto.getLeaveTimeDays());
			} else {
				msg  =ResourceUtil.getString("sys-time:sysTimeLeaveDetail.fdLeaveTime.tip");
				updateLeaveDetail(leaveDetail, status, false,msg);
				return msg;
			}
		}
		if (Boolean.FALSE.equals(leaveRule.getFdIsAvailable())) {
			msg  =ResourceUtil.getString("sys-time:sysTimeLeaveDetail.reason.leaveNotAvail");
			// 扣减失败，假期类型已失效
			updateLeaveDetail(leaveDetail, status, false, msg);
			return msg;
		}
		if (!Boolean.TRUE.equals(leaveRule.getFdIsAmount())) {
			msg  =ResourceUtil.getString("sys-time:sysTimeLeaveDetail.reason.notSetAmount");
			// 扣减失败，未开启额度管理，但可以更新考勤
			updateLeaveDetail(leaveDetail, status, true, msg);
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(endTime);
		Integer endYear = cal.get(Calendar.YEAR);
		cal.setTime(new Date());
		Integer nowYear = cal.get(Calendar.YEAR);
		int year = Math.max(nowYear, endYear);
		Float days = leaveDetail.getFdLeaveTime();
		if (days != null && days > 0f) {
			msg =limitDeduction(person.getFdId(),year,leaveRule,leaveDetail);
		}
		return msg;
	}

	@Override
	public boolean checkLeaveEffect(SysTimeLeaveDetail leaveDetail) throws Exception{

		if (leaveDetail == null) {
			return false;
		}
		Date startTime = leaveDetail.getFdStartTime();
		Date endTime = leaveDetail.getFdEndTime();
		String leaveType = leaveDetail.getFdLeaveType();
		SysOrgPerson person = leaveDetail.getFdPerson();
		if (person == null || startTime == null || endTime == null || startTime.getTime() > endTime.getTime()) {
			return false;
		}
		// 扣减成功的不能再扣减
		if (Integer.valueOf(1).equals(leaveDetail.getFdOprStatus())) {
			return false;
		}
		//假期类型不能为空
		if (StringUtil.isNull(leaveType)) {
			return false;
		}
		//假期是否存在
		SysTimeLeaveRule leaveRule = getLeaveaRule(leaveType);
		if (leaveRule == null) {
			return false;
		}
		if (Boolean.FALSE.equals(leaveRule.getFdIsAvailable())) {
			return false;
		}
		// 验证请假是否重复
		if (checkDateRepeat(leaveDetail)) {
			return false;
		}
		if (SysTimeUtil.compareDecimal(leaveDetail.getFdTotalTime(), 0f) == 0) {
			// 请假时长为0
			// 查询是否被安排补班，重新计算时间
			SysTimeLeaveRule rule = sysTimeLeaveRuleService.getLeaveRuleByType(leaveDetail.getFdLeaveType());
			Integer statDayType = rule.getFdStatDayType();
			SysTimeLeaveTimeDto dto = SysTimeUtil.getLeaveTimes(person,startTime,endTime, leaveDetail.getFdStartNoon(),
					leaveDetail.getFdEndNoon(), statDayType,leaveDetail.getFdStatType(),rule.getFdSerialNo());
			int leaveTimes = dto.getLeaveTimeMins();
			if (leaveTimes > 0) {
				leaveDetail.setFdTotalTime((float) leaveTimes);
				leaveDetail.setFdLeaveTime(dto.getLeaveTimeDays());
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 使用sql查询最新的假期额度信息 进行操作
	 * @param fdId
	 * @return
	 */
	private void getSysTimeLeaveAmountItemInfo(String fdId,SysTimeLeaveAmountItem item){
		TransactionStatus status =TransactionUtils.beginNewReadTransaction();
		boolean haveExcetion=false;
		try {
			String sql="select  fd_total_day,  fd_rest_day,  fd_used_day, fd_ltotal_day,fd_lrest_day,fd_lused_day " +
					" from sys_time_leave_aitem " +
					" where fd_id=:fdId";
			List<Object[]> results = this.sysTimeLeaveAmountItemService.getBaseDao().getHibernateSession().createNativeQuery(sql)
					.setParameter("fdId",fdId).list();
			if(CollectionUtils.isNotEmpty(results)){
				Object[] jdbcResult = results.get(0);
				Float restDay =convertObjectToFloat(jdbcResult[1]);
				//数据库中的值，比当前事务获取的额度大，则不赋值。
				if(restDay < item.getFdRestDay()) {
					item.setFdTotalDay(convertObjectToFloat(jdbcResult[0]));
					item.setFdRestDay(convertObjectToFloat(jdbcResult[1]));
					item.setFdUsedDay(convertObjectToFloat(jdbcResult[2]));
					item.setFdLastTotalDay(convertObjectToFloat(jdbcResult[3]));
					item.setFdLastRestDay(convertObjectToFloat(jdbcResult[4]));
					item.setFdLastUsedDay(convertObjectToFloat(jdbcResult[5]));
				}
			}
			TransactionUtils.commit(status);
		} catch (Exception e) {
			e.printStackTrace();
			haveExcetion =true;
		} finally {
			if(status !=null && haveExcetion){
				TransactionUtils.rollback(status);
			}
		}

	}

	/**
	 * JDBC查询结果转换成数字类型
	 * @param obj
	 * @return
	 */
	private Float convertObjectToFloat(Object obj) {
		if(obj !=null) {
			if(StringUtil.isNotNull(obj.toString())){
				return Float.parseFloat(obj.toString());
			}
		}
		return null;
	}
	private IEventMulticaster multicaster;

	@Override
	public void setEventMulticaster(IEventMulticaster multicaster) {
		this.multicaster = multicaster;

	}
	/**
	 * 限额扣除
	 * @param personId 人员
	 * @param year 年份
	 * @param leaveRule 假期规则
	 * @param leaveDetail 请假明细
	 * @return 返回扣减成功与否
	 * @throws Exception
	 */
	private String limitDeduction(String personId,int year,SysTimeLeaveRule leaveRule,SysTimeLeaveDetail leaveDetail) throws Exception {
		Float days = leaveDetail.getFdLeaveTime();
		String id =null;
		boolean trackException=false;
		SysTimeLeaveAmountItem item  = getAmountItem(personId, year, leaveRule);
		if (item == null) {
			// 当前年没有额度信息,则取去年的额度
			item = getAmountItem(personId, year - 1, leaveRule);
		}

		if (item != null) {
			//每个人的假期额度主键作为锁主键
			id = item.getFdId();
			if(logger.isDebugEnabled()) {
				logger.info("执行扣减操作:" + id + "  剩余额度：" + item.getFdRestDay());
			}
			//使用jdbc查询相关额度信息，来进行筛检
			getSysTimeLeaveAmountItemInfo(id,item);
			if(logger.isDebugEnabled()) {
				logger.info("JDBC查询执行扣减操作:" + id + "  剩余额度：" + item.getFdRestDay());
			}
			try {
				//锁定对象 默认解锁时间为10秒
				getGlobalLockService().tryLock(item, "refresh", 20 * 1000);
				updateAmountItem(item, days, leaveDetail, leaveRule,true);
				TRY_LOCK_LOG_MAP.remove(id);
			} catch (Exception e) {
				if (e instanceof OptimisticLockException
						|| e instanceof AcquireLockFailException || e instanceof  VersionInconsistencyException){
					trackException =true;
					if(logger.isDebugEnabled()) {
						logger.info("并发锁定:" + id + " 剩余额度：" + item.getFdRestDay());
					}
				} else {
					e.printStackTrace();
					return e.getMessage();
				}
			} finally {
				//非锁定异常，事务提交以后则解锁
				if(!trackException) {
					SysTimeLeaveAmountItem finalItem = item;
					multicaster.attatchEvent(
							new EventOfTransactionCommit(StringUtils.EMPTY),
							new IEventCallBack() {
								@Override
								public void execute(ApplicationEvent applicationEvent) throws Throwable {
									getGlobalLockService().unLock(finalItem);
								}
							});
				}
			}
		} else {
			return ResourceUtil.getString("sys-time:sysTimeLeaveDetail.reason.notSetAmount.item");
		}
		if(trackException) {
			Integer tryNumber = TRY_LOCK_LOG_MAP.get(id);
			//如果是数据锁异常则重试
			tryNumber = tryNumber == null ? 0 : tryNumber;
			TRY_LOCK_LOG_MAP.put(id, tryNumber + 1);
			if (tryNumber < MAX_TRY_COUNT) {
				Thread.sleep(200);
				sysTimeLeaveAmountItemService.flushHibernateSession();
				if(logger.isDebugEnabled()) {
					logger.info("并发锁定后重试:" + id + "  次数：" + tryNumber);
				}
				limitDeduction(personId, year, leaveRule, leaveDetail);
			}else{
				return ResourceUtil.getString("sys-time:sysTimeLeaveDetail.reason.notSetAmount.item");
			}
		}else{
			if(null !=id) {
				TRY_LOCK_LOG_MAP.remove(id);
			}
		}
		return null;
	}

	/**
	 * 验证请假日期是否重复
	 * @param leaveDetail 请假明细
	 * @return
	 */
	private Boolean checkDateRepeat(SysTimeLeaveDetail leaveDetail) {
		try {
			HQLInfo hqlInfo = new HQLInfo();
			StringBuffer whereBlock = new StringBuffer();
			whereBlock.append(
					"sysTimeLeaveDetail.fdPerson.fdId=:personId and (sysTimeLeaveDetail.fdType is null or sysTimeLeaveDetail.fdType=:fdType) ");
			whereBlock.append(
					" and ((sysTimeLeaveDetail.fdStatType is null or sysTimeLeaveDetail.fdStatType=3) and sysTimeLeaveDetail.fdStartTime<:endTime and sysTimeLeaveDetail.fdEndTime>:startTime");
			whereBlock.append(
					" or (sysTimeLeaveDetail.fdStatType=1 or sysTimeLeaveDetail.fdStatType=2) and sysTimeLeaveDetail.fdStartTime<:endTime and sysTimeLeaveDetail.fdEndTime>=:startTime)");
			whereBlock.append(
					" and sysTimeLeaveDetail.fdId != :exceptId "
							+ "and (sysTimeLeaveDetail.fdOprStatus in(:oprStatus) or sysTimeLeaveDetail.fdCanUpdateAttend=1) "
							+ "and sysTimeLeaveDetail.fdLeaveTime > 0");
			Integer statType = leaveDetail.getFdStatType();
			Date startTime = null;
			Date endTime = null;
			if (Integer.valueOf(1).equals(statType)
					|| Integer.valueOf(2).equals(statType)) {
				startTime = SysTimeUtil.getDate(leaveDetail.getFdStartTime(),
						0);
				endTime = SysTimeUtil.getDate(leaveDetail.getFdEndTime(), 1);
			} else {
				// 注意按天请假类型查询(开始与结束时间可能相同,如:2019-09-04 00:00:00)
				startTime = SysTimeUtil.getDate(leaveDetail.getFdStartTime(),
						0);
				endTime = leaveDetail.getFdEndTime();
			}
			hqlInfo.setParameter("personId",
					leaveDetail.getFdPerson().getFdId());
			hqlInfo.setParameter("fdType", 1);
			hqlInfo.setParameter("startTime", startTime);
			hqlInfo.setParameter("endTime", endTime);
			hqlInfo.setParameter("exceptId", leaveDetail.getFdId());
			List<Integer> oprStatusList = new ArrayList<>();
			//正式请假
			oprStatusList.add(1);
			//预请假
			oprStatusList.add(5);
			hqlInfo.setParameter("oprStatus", oprStatusList);
			hqlInfo.setWhereBlock(whereBlock.toString());
			List<SysTimeLeaveDetail> list = findList(hqlInfo);
			Map<String, Date> map = getStartAndEndTime(
					leaveDetail.getFdStartTime(), leaveDetail.getFdEndTime(),
					leaveDetail.getFdStatType(), leaveDetail.getFdStartNoon(),
					leaveDetail.getFdEndNoon());
			// 当前请假时间区间
			Date leaveStart = (Date) map.get("leaveStart");
			Date leaveEnd = (Date) map.get("leaveEnd");
			boolean isRepeat = false;
			if (!list.isEmpty()) {
				for (SysTimeLeaveDetail leave : list) {
					Map<String, Date> _map = getStartAndEndTime(
							leave.getFdStartTime(),
							leave.getFdEndTime(), leave.getFdStatType(),
							leave.getFdStartNoon(), leave.getFdEndNoon());
					// 实际请假时间区间
					Date _leaveStart = (Date) _map.get("leaveStart");
					Date _leaveEnd = (Date) _map.get("leaveEnd");
					// 计算请假剩余区间
					List<Map> currentLeaveList = new ArrayList<Map>();
					currentLeaveList.add(_map);
					if (leaveEnd.getTime() > _leaveStart.getTime()
							&& leaveStart.getTime() < _leaveEnd.getTime()) {
						// 判断该请假是否存在销假记录
						List<SysTimeLeaveResume> resumeList = getSysTimeLeaveResumeService()
								.findResumeList(
										leave.getFdPerson().getFdId(),
										leave.getFdId());
						boolean isResume = false;

						if (resumeList != null && !resumeList.isEmpty()) {
							for (SysTimeLeaveResume resume : resumeList) {
								// 销假时间区间
								Map<String, Date> resumeMap = getResumeStartAndEndTime(resume.getFdPerson(),
										resume.getFdStartTime(),
										resume.getFdEndTime(),
										resume.getFdLeaveDetail()
												.getFdStatType(),
										resume.getFdStartNoon(),
										resume.getFdEndNoon());
								currentLeaveList = getNewLeaveList(
										currentLeaveList, resumeMap);

							}
						}

						if (!currentLeaveList.isEmpty()) {
							for (Map leaveMap : currentLeaveList) {
								// 剩余假期
								Date leaveRestStart = (Date) leaveMap
										.get("leaveStart");
								Date leaveRestEnd = (Date) leaveMap
										.get("leaveEnd");
								if (leaveStart.getTime() < leaveRestEnd
										.getTime()
										&& leaveEnd.getTime() > leaveRestStart
												.getTime()) {
									isRepeat = true;
									break;
								}
							}
						}

					}

				} // end for
			}
			return isRepeat;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		return false;
	}

	/**
	 * 获取销假后剩余请假信息
	 * 
	 * @param leaveBetween
	 * @param resumeMap
	 * @return
	 */
	private List<Map> getNewLeaveList(List<Map> leaveBetween,
			Map<String, Date> resumeMap) {
		List<Map> newMapList = new ArrayList<Map>();
		for (Map leaveMap : leaveBetween) {
			Date _leaveStart = (Date) leaveMap.get("leaveStart");
			Date _leaveEnd = (Date) leaveMap.get("leaveEnd");

			// 销假区间
			Date resumeStart = (Date) resumeMap.get("leaveStart");
			Date resumeEnd = (Date) resumeMap.get("leaveEnd");
			if (resumeStart.getTime() <= _leaveStart.getTime()
					&& resumeEnd.getTime() >= _leaveEnd.getTime()) {
				// 销假区间包含请假区间
			} else if (resumeStart.getTime() > _leaveStart.getTime()
					&& resumeEnd.getTime() < _leaveEnd.getTime()) {
				// 销假区间为请假区间子集
				Map<String, Date> tmpLeave1 = new HashMap<String, Date>();
				Map<String, Date> tmpLeave2 = new HashMap<String, Date>();
				tmpLeave1.put("leaveStart", _leaveStart);
				tmpLeave1.put("leaveEnd", resumeStart);
				tmpLeave2.put("leaveStart", resumeEnd);
				tmpLeave2.put("leaveEnd", _leaveEnd);
				newMapList.add(tmpLeave1);
				newMapList.add(tmpLeave2);
			} else if (resumeEnd.getTime() > _leaveStart.getTime()
					&& resumeEnd.getTime() < _leaveEnd.getTime()) {
				// 销假区间为请假左侧区间
				Map<String, Date> tmpLeave1 = new HashMap<String, Date>();
				tmpLeave1.put("leaveStart", resumeEnd);
				tmpLeave1.put("leaveEnd", _leaveEnd);
				newMapList.add(tmpLeave1);
			} else if (resumeStart.getTime() > _leaveStart.getTime()
					&& resumeStart.getTime() < _leaveEnd.getTime()) {
				// 销假区间为请假右侧区间
				Map<String, Date> tmpLeave1 = new HashMap<String, Date>();
				tmpLeave1.put("leaveStart", _leaveStart);
				tmpLeave1.put("leaveEnd", resumeStart);
				newMapList.add(tmpLeave1);
			} else {
				newMapList.add(leaveMap);
			}
		}
		return newMapList;
	}

	/**
	 * 获取销假开始和结束时间，主要是为了计算小时请假的开始和结束区间
	 * @description:
	 * @param fdPerson 销假用户
	 * @param startTime
	 * @param endTime
	 * @param statType
	 * @param startNoon
	 * @param endNoon
	 * @return: java.util.Map<java.lang.String,java.util.Date>
	 * @author: wangjf
	 * @time: 2022/3/7 5:07 下午
	 */
	private Map<String,Date> getResumeStartAndEndTime(SysOrgPerson fdPerson, Date startTime, Date endTime,
													  Integer statType, Integer startNoon, Integer endNoon) throws Exception{
		//如果是按照小时销假，需要把销假的前后时间区间加上验证
		if(statType == 3){
			Map<String, Date> timeMap = new HashMap<String, Date>();
			timeMap.put("leaveStart", startTime);
			timeMap.put("leaveEnd", endTime);
			List<Map<String, Object>> endSignTimeList = SysTimeUtil.getAttendSignTimeList(fdPerson,endTime,2);
			if (CollectionUtils.isNotEmpty(endSignTimeList) && endSignTimeList.size() >= 2) {
				//如果结束时间超出打卡时间，则用当天的结束时间进行赋值，否则返回给到的endTime
				Object signObject = endSignTimeList.get(endSignTimeList.size()-1).get("signTime");
				if(signObject!=null && signObject instanceof Date){
					Date signTime = SysTimeUtil.joinYMDandHMS(startTime, (Date) signObject);
					if(endTime.getTime() >= signTime.getTime()){
						timeMap.put("leaveEnd", SysTimeUtil.getDate(endTime, 1));
					}
				}
			}
			return timeMap;

		}
		return getStartAndEndTime(startTime, endTime, statType, startNoon, endNoon);
	}


	private Map<String, Date> getStartAndEndTime(Date startTime, Date endTime,
			Integer statType, Integer startNoon, Integer endNoon) {
		Map<String, Date> timeMap = new HashMap<String, Date>();
		Date leaveStart = null;
		Date leaveEnd = null;
		//按小时计算
		if(statType == null || statType==3){
			leaveStart = startTime;
			leaveEnd = endTime;
		} else if(statType == 1){
			leaveStart = SysTimeUtil.getDate(startTime, 0);
			leaveEnd = SysTimeUtil.getDate(endTime, 1);
		} else if(statType == 2){
			Calendar cal = Calendar.getInstance();
			if (startNoon != null && endNoon != null) {
				leaveStart = SysTimeUtil.getDate(startTime, 0);
				if (startNoon == 2) {
					cal.setTime(leaveStart);
					cal.set(Calendar.HOUR_OF_DAY, 12);
					leaveStart = cal.getTime();
				}
				leaveEnd = SysTimeUtil.getDate(endTime, 0);
				if (endNoon == 1) {
					cal.setTime(leaveEnd);
					cal.set(Calendar.HOUR_OF_DAY, 12);
					leaveEnd = cal.getTime();
				} else {
					cal.setTime(leaveEnd);
					cal.add(Calendar.DATE, 1);
					leaveEnd = cal.getTime();
				}
			} else {
				leaveStart = SysTimeUtil.getDate(startTime, 0);
				leaveEnd = SysTimeUtil.getDate(endTime, 1);
			}
		}
		timeMap.put("leaveStart", leaveStart);
		timeMap.put("leaveEnd", leaveEnd);
		return timeMap;
	}

	private Boolean checkValidDate(SysTimeLeaveDetail leaveDetail,
			SysTimeLeaveAmountItem amountItem) {
		Date endTime = leaveDetail.getFdEndTime();
		if (endTime != null) {
			if ( Boolean.TRUE.equals(amountItem.getFdIsLastAvail())) {
				Date date = amountItem.getFdLastValidDate();
				if (date != null) {
					if (endTime.getTime() < SysTimeUtil.getDate(date, 1)
							.getTime()) {
						return true;
					}
				} else {
					return true;
				}
			}
			if ( Boolean.TRUE.equals(amountItem.getFdIsAvail())) {
				Date date = amountItem.getFdValidDate();
				if (date != null) {
					return endTime.getTime() < SysTimeUtil.getDate(date, 1)
							.getTime();
				} else {
					return true;
				}
			}
		}
		return false;
	}

	private SysTimeLeaveAmountItem getAmountItem(String personId, Integer year,
			SysTimeLeaveRule leaveRule) throws Exception {
		if (year != null && StringUtil.isNotNull(leaveRule.getFdSerialNo())
				&& StringUtil.isNotNull(personId)) {
			return sysTimeLeaveAmountService.getLeaveAmountItem(year, personId,
					leaveRule.getFdSerialNo());
		}
		return null;
	}

	@Override
	public void updateAmountItem(SysTimeLeaveAmountItem amountItem, Float days,
			SysTimeLeaveDetail leaveDetail,
			SysTimeLeaveRule leaveRule,Boolean updateStatus) throws Exception {
		if (amountItem == null) {
			// 扣减失败，找不到额度信息
			//updateLeaveDetail(leaveDetail, 2, false, ResourceUtil.getString("sys-time:sysTimeLeaveDetail.reason.noAmount"));
			throw new Exception(ResourceUtil.getString("sys-time:sysTimeLeaveDetail.reason.noAmount"));
		}
		if (!checkValidDate(leaveDetail, amountItem)) {
			// 扣减失败，日期已失效
//			updateLeaveDetail(leaveDetail, 2, false, ResourceUtil.getString(
//					"sys-time:sysTimeLeaveDetail.reason.dateInvalid"));
			throw new Exception(ResourceUtil.getString("sys-time:sysTimeLeaveDetail.reason.dateInvalid"));
		}
		// 原始值
		Float _lastRestDay = amountItem.getFdLastRestDay() != null
				? amountItem.getFdLastRestDay() : 0;
		Float _restday = amountItem.getFdRestDay() != null
				? amountItem.getFdRestDay() : 0;
		// 有效值
		Float lastRestDay = amountItem.getValidLastRestDay(leaveDetail,
				leaveRule);
		Float restday = amountItem.getValidRestDay(leaveDetail, leaveRule);
		Float lastUsedDay = amountItem.getFdLastUsedDay() == null ? 0
				: amountItem.getFdLastUsedDay();
		Float usedDay = amountItem.getFdUsedDay() == null ? 0
				: amountItem.getFdUsedDay();

		if (lastRestDay + restday >= days) {
			leaveDetail.setFdCurrentYearAmount(0F);

			leaveDetail.setFdPreviousYearAmount(0F);

			boolean isUsedLast = false;
			Float lastDays = 0f;
			// 上周期扣减的时长
			if (lastRestDay >= days) {
				amountItem.setFdLastRestDay(_lastRestDay - days);
				amountItem.setFdLastUsedDay(lastUsedDay + days);

				isUsedLast = true;
				lastDays = days;

				//上周期扣减数量记录
				leaveDetail.setFdPreviousYearAmount(days);

			} else if (lastRestDay > 0) {
				//本周期的扣除和上周期的扣除一起扣除
				amountItem.setFdLastRestDay(_lastRestDay - lastRestDay);
				amountItem.setFdLastUsedDay(lastUsedDay + lastRestDay);
				amountItem.setFdRestDay(_restday + lastRestDay - days);
				amountItem.setFdUsedDay(usedDay + days - lastRestDay);

				//上周期扣了多少
				leaveDetail.setFdPreviousYearAmount(lastRestDay.floatValue());
				//本周期扣多少 = 本次扣减额度 - 上周期的扣减额度
				leaveDetail.setFdCurrentYearAmount(days-lastRestDay);
				
				isUsedLast = true;
				lastDays = lastRestDay;
			} else {
				amountItem.setFdRestDay(_restday - days);
				amountItem.setFdUsedDay(usedDay + days);
				leaveDetail.setFdCurrentYearAmount(days.floatValue() );
			}
			if(logger.isDebugEnabled()) {
				logger.debug("amountItemId:{},剩余：{},已使用：{}", amountItem.getFdId(), amountItem.getFdRestDayMin(), amountItem.getFdUsedDayMin());
			}
			leaveDetail.setSysTimeLeaveAmountItemId(amountItem.getFdId());
			if (isUsedLast) {
				// 请假时已使用上周期的假期额度,保存相关信息
				saveLeaveLastAmount(leaveDetail, lastDays, leaveRule);
			}
			sysTimeLeaveAmountItemService.update(amountItem);
			if(Boolean.TRUE.equals(updateStatus)) {
				// 扣减成功
				updateLeaveDetail(leaveDetail, 1, true, null);
			}
			sysTimeLeaveAmountItemService.flushHibernateSession();
		} else {
			// 扣减失败，额度不足
//			updateLeaveDetail(leaveDetail, 2, false, ResourceUtil.getString(
//					"sys-time:sysTimeLeaveDetail.reason.notEnough"));
			throw new Exception(ResourceUtil.getString("sys-time:sysTimeLeaveDetail.reason.notEnough"));
		}
	}

	/**
	 * 请假额度变更
	 * 
	 * @param amountItem
	 * @param days
	 *            小于0时表示增加相应假期额度,否则扣减相应额度
	 * @param leaveDetail
	 * @throws Exception
	 */
	private void updateUserLeaveAmount(SysTimeLeaveAmountItem amountItem,
			Float days, SysTimeLeaveDetail leaveDetail,
			SysTimeLeaveRule leaveRule, Date startTime) throws Exception {
		// 原始值
		Float _lastRestDay = amountItem.getFdLastRestDay() != null
				? amountItem.getFdLastRestDay() : 0;
		Float _restday = amountItem.getFdRestDay() != null
				? amountItem.getFdRestDay() : 0;
		// 有效值
		Float lastRestDay = amountItem.getValidLastRestDay(leaveDetail,
				leaveRule);
		Float restday = amountItem.getValidRestDay(leaveDetail, leaveRule);
		Float lastUsedDay = amountItem.getFdLastUsedDay() == null ? 0
				: amountItem.getFdLastUsedDay();
		Float usedDay = amountItem.getFdUsedDay() == null ? 0
				: amountItem.getFdUsedDay();

		if (days < 0) {
			// 增加额度
			amountItem.setFdRestDay(_restday + Math.abs(days));
			amountItem.setFdUsedDay(usedDay - Math.abs(days));
		} else {
			// 扣减
			boolean isUsedLast = false;
			Float lastDays = 0f;// 上周期扣减的时长
			if (lastRestDay > 0) {
				// 优先扣减
				if (lastRestDay >= Math.abs(days)) {
					amountItem.setFdLastRestDay(_lastRestDay - Math.abs(days));
					amountItem.setFdLastUsedDay(lastUsedDay + Math.abs(days));
					isUsedLast = true;
					lastDays = Math.abs(days);
				} else {
					amountItem.setFdLastRestDay(_lastRestDay - lastRestDay);
					amountItem.setFdLastUsedDay(lastUsedDay + lastRestDay);
					amountItem.setFdRestDay(
							_restday + lastRestDay - Math.abs(days));
					amountItem.setFdUsedDay(
							usedDay + Math.abs(days) - lastRestDay);

					isUsedLast = true;
					lastDays = lastRestDay;
				}
			} else {
				amountItem.setFdRestDay(_restday - Math.abs(days));
				amountItem.setFdUsedDay(usedDay + Math.abs(days));
			}
			if (isUsedLast) {
				// 请假时已使用上周期的假期额度,保存相关信息
				saveLeaveLastAmount(leaveDetail, lastDays, leaveRule);
			}

		}
		sysTimeLeaveAmountItemService.update(amountItem);
	}

	/**
	 *
	 * @description: 保存假期上一年度扣减情况
	 * @param leaveDetail
	 * @return: void
	 * @author: wangjf
	 * @time: 2022/3/11 11:17 下午
	 */
	@Override
	public void saveLeaveLastAmount(SysTimeLeaveDetail leaveDetail) throws Exception {
		saveLeaveLastAmount(leaveDetail, leaveDetail.getFdPreviousYearAmount(), sysTimeLeaveRuleService.getLeaveRuleByType(leaveDetail.getFdLeaveType()));
	}

	/**
	 * 保存上一周期扣减情况
	 * @param leaveDetail
	 * @param lastDays
	 * @param leaveRule
	 */
	private void saveLeaveLastAmount(SysTimeLeaveDetail leaveDetail,
			Float lastDays, SysTimeLeaveRule leaveRule) throws Exception {
		// 请假时已使用上周期的假期额度,保存相关信息
		Map<Long, Float> dateMap = getLeaveLastAmount(leaveDetail,
				lastDays, leaveRule);
		if (!dateMap.isEmpty()) {
			for (Long key : dateMap.keySet()) {
				Float value = dateMap.get(key);
				String valueDay = SysTimeUtil.getLeaveDays(value.intValue(),
						leaveDetail.getFdStatType());
				SysTimeLeaveLastAmount lastAmount = new SysTimeLeaveLastAmount();
				lastAmount.setFdPerson(leaveDetail.getFdPerson());
				lastAmount.setFdLeaveId(leaveDetail.getFdId());
				lastAmount.setFdStartDate(new Date(key));
				lastAmount.setFdEndDate(new Date(key));
				// lastAmount.setFdTotalTime(days);
				lastAmount.setFdTotalDay(Float.valueOf(valueDay));
				lastAmount.setDocCreateTime(new Date());
				lastAmount.setDocCreator(UserUtil.getUser());
				this.getSysTimeLeaveLastAmountService().add(lastAmount);
			}
		}
	}

	/**
	 * 获取扣减上周期额度时对应的日期区间
	 * 
	 * @param leaveDetail
	 * @param lastDays
	 *            上周期扣减额度(天数)
	 * @param rule
	 * @throws Exception
	 */
	private Map getLeaveLastAmount(SysTimeLeaveDetail leaveDetail,
			Float lastDays, SysTimeLeaveRule rule) throws Exception {
		Integer fdStatType = leaveDetail.getFdStatType();
		Date fdStartDate = leaveDetail.getFdStartTime();
		Date fdEndDate = leaveDetail.getFdEndTime();
		SysOrgPerson person = leaveDetail.getFdPerson();
		Integer startNoon = leaveDetail.getFdStartNoon();
		Integer endNoon = leaveDetail.getFdEndNoon();
		Map<Long, Float> dateMap = new HashMap<Long, Float>();

		List<Date> dateList = SysTimeUtil.getDateList(fdStatType,
				leaveDetail.getFdStartTime(), leaveDetail.getFdEndTime(),
				startNoon, endNoon);
		if (dateList.size() < 2) {
			return dateMap;
		}
		Float currentDay = 0f;
		for (int i = 0; i < dateList.size() - 1; i++) {
			Date startTime = dateList.get(i);
			Date date = SysTimeUtil.getDate(startTime, 0);
			Date endTime = SysTimeUtil.getEndDate(date, 0);
			boolean isStartDate = i == 0;
			boolean isEndDate = date.equals(SysTimeUtil.getDate(fdEndDate, 0));
			endTime = isEndDate ? fdEndDate : endTime;
			// 计算当天请假对应额度
			SysTimeLeaveTimeDto dto = SysTimeUtil.getLeaveTimes(person, startTime,
					endTime, isStartDate && fdStatType == 2 ? startNoon : 1,
					isEndDate && fdStatType == 2 ? endNoon : 2,
					rule.getFdStatDayType(),
					fdStatType,rule.getFdSerialNo());

			int todayMins =dto.getLeaveTimeMins();
			Float todayDays =dto.getLeaveTimeDays();

			if (todayMins > 0) {
				if (currentDay + todayDays > lastDays) {
					Float days = lastDays - currentDay;
					Float mins = (float) SysTimeUtil.getLeaveMins(days,
							fdStatType);
					dateMap.put(date.getTime(), mins);
				} else {
					dateMap.put(date.getTime(), Float.valueOf(todayMins));
				}
				currentDay += todayDays;
			}
			if (currentDay >= lastDays.floatValue()) {
				break;
			}
		}

		return dateMap;
	}

	private void updateLeaveDetail(SysTimeLeaveDetail leaveDetail,
			Integer status, Boolean canUpdateAttend, String desc)
			throws Exception {
		leaveDetail.setFdOprStatus(status);
		leaveDetail.setFdCanUpdateAttend(canUpdateAttend);
		leaveDetail.setFdOprDesc(desc);
		super.update(leaveDetail);
	}

	@Override
	public HSSFWorkbook buildTempletWorkBook() throws Exception {
		// 第一步，创建一个workbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(ResourceUtil
				.getString("sys-time:sysTime.import.sheet1.title"));
		sheet.setDefaultColumnWidth(25); // 设置宽度
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) (20 * 20));
		// 第四步，创建单元格
		HSSFCell cell = null;

		// 定义普通字体样式
		HSSFFont font1 = wb.createFont();
		font1.setBold(true); // 字体增粗
		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER); // 水平布局：居中
		style1.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
		style1.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
		style1.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.PALE_BLUE.index);
		style1.setFont(font1);

		// 定义必填字体样式
		HSSFFont font2 = wb.createFont();
		font2.setBold(true); // 字体增粗
		font2.setColor(org.apache.poi.ss.usermodel.IndexedColors.RED.index); // 字体颜色
		HSSFCellStyle style2 = wb.createCellStyle();
		style2.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER); // 水平布局：居中
		style2.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
		style2.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
		style2.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.PALE_BLUE.index);
		style2.setFont(font2);

		/********** 设置头部内容 **********/
		// 登录账号
		int colIndex = 0;
		cell = row.createCell(colIndex++);
		cell.setCellValue(ResourceUtil
				.getString("sys-time:sysTimeLeaveAmount.loginName"));
		cell.setCellStyle(style2);

		// 请假类型
		cell = row.createCell(colIndex++);
		cell.setCellValue(ResourceUtil
				.getString("sys-time:sysTimeLeaveDetail.fdLeaveName"));
		cell.setCellStyle(style2);

		// 请假开始日期
		cell = row.createCell(colIndex++);
		cell.setCellValue(ResourceUtil
				.getString("sys-time:sysTimeLeaveDetail.fdStartTime"));
		cell.setCellStyle(style2);

		// 开始上下午标识
		cell = row.createCell(colIndex++);
		cell.setCellValue(ResourceUtil
				.getString("sys-time:sysTimeLeaveDetail.fdStartNoon"));
		cell.setCellStyle(style2);

		// 请假结束日期
		cell = row.createCell(colIndex++);
		cell.setCellValue(ResourceUtil
				.getString("sys-time:sysTimeLeaveDetail.fdEndTime"));
		cell.setCellStyle(style2);

		// 结束上下午标识
		cell = row.createCell(colIndex++);
		cell.setCellValue(ResourceUtil
				.getString("sys-time:sysTimeLeaveDetail.fdEndNoon"));
		cell.setCellStyle(style2);

		// 请假时长
		cell = row.createCell(colIndex++);
		cell.setCellValue(ResourceUtil
				.getString("sys-time:sysTimeLeaveDetail.fdLeaveTime"));
		cell.setCellStyle(style2);

		// 流程ID
		cell = row.createCell(colIndex++);
		cell.setCellValue(ResourceUtil
				.getString("sys-time:sysTimeLeaveDetail.fdReview.id"));
		cell.setCellStyle(style1);

		// 流程主题
		cell = row.createCell(colIndex++);
		cell.setCellValue(ResourceUtil
				.getString("sys-time:sysTimeLeaveDetail.fdReview.name"));
		cell.setCellStyle(style1);

		// 是否扣减成功后更新考勤
		cell = row.createCell(colIndex++);
		cell.setCellValue(ResourceUtil
				.getString("sys-time:sysTimeLeaveDetail.fdIsUpdateAttend"));
		cell.setCellStyle(style1);

		// 注意事项
		List<String> itemNodes = new ArrayList<String>();
		itemNodes.add(ResourceUtil
				.getString("sys-time:sysTime.import.sheet2.item.node1"));
		itemNodes.add(ResourceUtil
				.getString("sys-time:sysTime.import.sheet2.item.node2"));
		itemNodes.add(ResourceUtil
				.getString("sys-time:sysTime.import.sheet2.item.node4"));
		itemNodes.add(ResourceUtil
				.getString("sys-time:sysTime.import.sheet2.item.node5"));
		itemNodes.add(ResourceUtil
				.getString("sys-time:sysTime.import.sheet2.item.node6"));
		itemNodes.add(ResourceUtil
				.getString("sys-time:sysTime.import.sheet2.item.node7"));
		if (itemNodes != null && !itemNodes.isEmpty()) {
			HSSFSheet sheet2 = wb.createSheet(ResourceUtil
					.getString("sys-time:sysTime.import.sheet2.title"));
			sheet2.setColumnWidth(0, 35 * 80); // 第一列宽度
			sheet2.setColumnWidth(1, 35 * 500); // 第二列宽度
			HSSFRow row2 = null;
			HSSFCell cell2 = null;

			row2 = sheet2.createRow((int) 0);
			row2.setHeight((short) (20 * 20));
			cell2 = row2.createCell(0);
			cell2.setCellValue(ResourceUtil
					.getString("sys-time:sysTime.import.sheet2.serial"));
			cell2.setCellStyle(style1);

			cell2 = row2.createCell(1);
			cell2.setCellValue(ResourceUtil
					.getString("sys-time:sysTime.import.sheet2.item"));
			cell2.setCellStyle(style1);

			// 单元格样式
			HSSFCellStyle style2_1 = wb.createCellStyle();
			style2_1.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER); // 水平居中
			style2_1.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
			HSSFCellStyle style2_2 = wb.createCellStyle();
			style2_2.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
			for (int i = 0; i < itemNodes.size(); i++) {
				row2 = sheet2.createRow((int) (i + 1));
				row2.setHeight((short) (20 * 20));
				row.setHeight((short) (20 * 20));
				cell2 = row2.createCell(0);
				cell2.setCellValue(String.valueOf(i + 1));
				cell2.setCellStyle(style2_1);

				cell2 = row2.createCell(1);
				cell2.setCellValue(itemNodes.get(i));
				cell2.setCellStyle(style2_2);
			}
		}
		return wb;
	}

	@Override
	public KmssMessage saveImportData(SysTimeImportForm importForm,
			boolean isRollBack) throws Exception {
		Workbook wb = null;
		Sheet sheet = null;
		InputStream inputStream = null;
		try {
			inputStream = importForm.getFile().getInputStream();

			// 抽象类创建Workbook，适合excel 2003和2007以上
			wb = WorkbookFactory.create(inputStream);
			sheet = wb.getSheetAt(0);

			// 一份标准的模板，最少包含2行，第一行是字段名称，导入的数据应该是从第二行开始
			int rowNum = sheet.getLastRowNum();
			if (rowNum < 1) {
				throw new RuntimeException(
						ResourceUtil.getString("sys-time:sysTime.import.empty"));
			}
			Row titleRow = sheet.getRow(0);
			if (titleRow.getLastCellNum() == 6) {
				throw new RuntimeException(
						ResourceUtil.getString("sys-time:sysTime.import.errFile"));
			}

			int count = 0;
			KmssMessages messages = null;
			StringBuffer errorMsg = new StringBuffer();
			SysTimeLeaveDetail leaveDetail = null;
			List<SysTimeLeaveRule> leaveRuleList = sysTimeLeaveRuleService
					.findList("sysTimeLeaveRule.fdIsAvailable = true", "");
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				messages = new KmssMessages();
				Row row = sheet.getRow(i);
				if (row == null) { // 跳过空行
					continue;
				}
				int colIndex = 0;
				leaveDetail = new SysTimeLeaveDetail();
				// 登录名
				String loginName = SysTimeImportUtil
						.getCellValue(row.getCell(colIndex++));
				if (StringUtil.isNull(loginName)) {
					messages.addError(new KmssMessage(ResourceUtil
							.getString("sys-time:sysTime.import.error.loginName")));
				} else {
					SysOrgPerson person = sysOrgCoreService
							.findByLoginName(loginName);
					if (person == null || person.getFdIsExternal()) {
						messages.addError(new KmssMessage(ResourceUtil.getString(
								"sys-time:sysTime.import.error.loginName.notExist")));
					} else {
						leaveDetail.setFdPerson(person);
					}
				}
				// 请假类型
				String leaveName = SysTimeImportUtil
						.getCellValue(row.getCell(colIndex++));
				SysTimeLeaveRule leaveRule = getLeaveaRule(leaveRuleList,
						leaveName);
				if (leaveRule != null) {
					leaveDetail.setFdLeaveName(leaveName);
					leaveDetail.setFdStatType(leaveRule.getFdStatType());
					leaveDetail.setFdLeaveType(leaveRule.getFdSerialNo());
				} else {
					messages.addError(new KmssMessage(ResourceUtil.getString(
							"sysTime.import.error.noLeaveRule", "sys-time", null,
							StringUtil.XMLEscape(leaveName))));
				}
				// 请假开始时间
				String startTime = SysTimeImportUtil
						.getCellValue(row.getCell(colIndex++));
				String dateType = "Date";
				if (leaveRule != null
						&& Integer.valueOf(3).equals(leaveRule.getFdStatType())) {
					dateType = "DateTime";
				}
				if (validateStartTime(startTime, dateType, messages)) {
					leaveDetail
							.setFdStartTime(DateUtil.convertStringToDate(startTime,
									"yyyy-MM-dd", null));
				}
				// 开始上下午标识
				String startNoon = SysTimeImportUtil
						.getCellValue(row.getCell(colIndex++));
				if (leaveRule != null
						&& Integer.valueOf(2).equals(leaveRule.getFdStatType())) {
					if (StringUtil.isNotNull(startNoon)) {
						if (startNoon.equals(ResourceUtil.getString(
								"sys-time:sysTimeLeaveDetail.morning"))) {
							leaveDetail.setFdStartNoon(1);
						} else if (startNoon.equals(ResourceUtil.getString(
								"sys-time:sysTimeLeaveDetail.afterNoon"))) {
							leaveDetail.setFdStartNoon(2);
						} else {
							messages.addError(new KmssMessage(ResourceUtil.getString(
									"sys-time:sysTime.import.error.startNoonFormat")));
						}
					} else {
						messages.addError(new KmssMessage(ResourceUtil.getString(
								"sys-time:sysTime.import.error.startNoonNull")));
					}
				}
				// 请假结束时间
				String endTime = SysTimeImportUtil
						.getCellValue(row.getCell(colIndex++));
				if (validateEndTime(startTime, endTime, dateType, messages)) {
					leaveDetail.setFdEndTime(DateUtil.convertStringToDate(endTime,
							"yyyy-MM-dd", null));
				}
				// 结束上下午标识
				String endNoon = SysTimeImportUtil
						.getCellValue(row.getCell(colIndex++));
				if (leaveRule != null
						&& Integer.valueOf(2).equals(leaveRule.getFdStatType())) {
					if (StringUtil.isNotNull(endNoon)) {
						if (endNoon.equals(ResourceUtil.getString(
								"sys-time:sysTimeLeaveDetail.morning"))) {
							leaveDetail.setFdEndNoon(1);
						} else if (endNoon.equals(ResourceUtil.getString(
								"sys-time:sysTimeLeaveDetail.afterNoon"))) {
							leaveDetail.setFdEndNoon(2);
						} else {
							messages.addError(
									new KmssMessage(ResourceUtil.getString(
											"sys-time:sysTime.import.error.endNoonFormat")));
						}
					} else {
						messages.addError(new KmssMessage(ResourceUtil.getString(
								"sys-time:sysTime.import.error.endNoonNull")));
					}
				}
				// 时长
				String leaveTime = SysTimeImportUtil
						.getCellValue(row.getCell(colIndex++));
				if (validateLeaveTime(leaveTime, messages, leaveDetail)) {
					leaveDetail.setFdLeaveTime(Float.parseFloat(leaveTime));
				}
				leaveDetail.setFdId(IDGenerator.generateID());
				// 流程ID
				String reviewId = SysTimeImportUtil
						.getCellValue(row.getCell(colIndex++));
				leaveDetail.setFdReviewId(reviewId);
				// 流程主题
				String reviewName = SysTimeImportUtil
						.getCellValue(row.getCell(colIndex++));
				leaveDetail.setFdReviewName(reviewName);
				leaveDetail.setDocCreateTime(new Date());
				leaveDetail.setDocCreator(UserUtil.getUser());
				leaveDetail.setFdOprStatus(0);
				leaveDetail.setFdOprDesc(ResourceUtil
						.getString("sys-time:sysTimeLeaveDetail.not.reduct"));
				String isUpdateAttend = SysTimeImportUtil
						.getCellValue(row.getCell(colIndex++));
				if (StringUtil.isNotNull(isUpdateAttend)
						&& isUpdateAttend.equals(ResourceUtil.getString(
						"sys-time:sysTimeLeaveDetail.fdIsUpdateAttend.yes"))) {
					leaveDetail.setFdIsUpdateAttend(true);
				}
				// 批量导入
				leaveDetail.setFdOprType(3);
				// 如果有错误，就不进行导入
				if (!messages.hasError()) {
					add(leaveDetail);
					updateDeduct(leaveDetail.getFdId());
					if (Boolean.TRUE.equals(leaveDetail.getFdIsUpdateAttend())) {
						updateAttend(leaveDetail.getFdId());
					}
					count++;
				} else {
					errorMsg.append(ResourceUtil.getString(
							"sysTime.import.error.num", "sys-time", null, i));
					// 解析错误信息
					for (KmssMessage message : messages.getMessages()) {
						errorMsg.append(message.getMessageKey());
					}
					errorMsg.append("<br>");
				}
			}

			KmssMessage message = null;
			if (errorMsg.length() > 0) {
				if (isRollBack) {
					// 如果有一条数据校验失败，则需要数据回滚。
					throw new RuntimeException(errorMsg.toString());
				} else {
					errorMsg.insert(0, ResourceUtil.getString(
							"sysTime.import.portion.failed", "sys-time", null,
							count)
							+ "<br>");
					message = new KmssMessage(errorMsg.toString());
					message.setMessageType(KmssMessage.MESSAGE_ERROR);
				}
			} else {
				message = new KmssMessage(ResourceUtil.getString(
						"sysTime.import.success", "sys-time", null, count));
				message.setMessageType(KmssMessage.MESSAGE_COMMON);
			}
			return message;

		} catch (Exception e) {
			throw new RuntimeException(
					ResourceUtil.getString("sys-time:sysTime.import.error"));
		} finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(inputStream);
		}
	}
	
	private boolean validateStartTime(String startTimeStr, String dateType,
			KmssMessages messages) {
		boolean result = true;
		if (StringUtil.isNotNull(startTimeStr)) {
			try {
				Date startTime = null;
				if ("Date".equals(dateType)) {
					startTime = DateUtil.convertStringToDate(startTimeStr,
							"yyyy-MM-dd");
				} else {
					startTime = DateUtil.convertStringToDate(startTimeStr,
							"yyyy-MM-dd HH:mm");
				}
				if (startTime == null) {
					result = false;
					messages.addError(new KmssMessage(ResourceUtil.getString(
							"sys-time:sysTime.import.error.start.null")));
				}
			} catch (Exception e) {
				result = false;
				messages.addError(new KmssMessage(ResourceUtil.getString(
						"sys-time:sysTime.import.error.start.format")));
			}
		} else {
			result = false;
			messages.addError(new KmssMessage(ResourceUtil.getString(
					"sys-time:sysTime.import.error.start.null")));
		}
		return result;
	}

	private boolean validateEndTime(String startTimeStr, String endTimeStr,
			String dateType, KmssMessages messages) {
		boolean result = true;
		if (StringUtil.isNotNull(endTimeStr)) {
			try {
				Date endTime = null;
				if ("Date".equals(dateType)) {
					endTime = DateUtil.convertStringToDate(endTimeStr,
							"yyyy-MM-dd");
				} else {
					endTime = DateUtil.convertStringToDate(endTimeStr,
							"yyyy-MM-dd HH:mm");
				}
				Date startTime = null;
				try {
					if ("Date".equals(dateType)) {
						startTime = DateUtil.convertStringToDate(startTimeStr,
								"yyyy-MM-dd");
					} else {
						startTime = DateUtil.convertStringToDate(startTimeStr,
								"yyyy-MM-dd HH:mm");
					}
				} catch (Exception e) {
				}
				if (startTime != null && endTime != null) {
					if (endTime.getTime() < startTime.getTime()) {
						result = false;
						messages.addError(
								new KmssMessage(ResourceUtil.getString(
										"sys-time:sysTime.import.error.end.after")));
					} else if ((endTime.getTime() - startTime.getTime())
							/ (60 * 60 * 24 * 1000f) > 365f) {
						result = false;
						messages.addError(
								new KmssMessage(ResourceUtil.getString(
										"sys-time:sysTime.import.error.end.range")));
					}
				}
			} catch (Exception e) {
				result = false;
				messages.addError(new KmssMessage(ResourceUtil.getString(
						"sys-time:sysTime.import.error.end.format")));
			}
		} else {
			result = false;
			messages.addError(new KmssMessage(ResourceUtil.getString(
					"sys-time:sysTime.import.error.end.null")));
		}
		return result;
	}

	private boolean validateLeaveTime(String leaveTimeStr,
			KmssMessages messages, SysTimeLeaveDetail leaveDetail) {
		boolean result = true;
		Float leaveTime = 0f;
		if (StringUtil.isNotNull(leaveTimeStr)) {
			try {
				leaveTime = Float.parseFloat(leaveTimeStr);
				if (leaveTime < 0 || leaveTime > 365) {
					result = false;
					messages.addError(new KmssMessage(ResourceUtil.getString(
							"sys-time:sysTime.import.error.leavetime.range")));
				}
				if (leaveDetail != null && !messages.hasError()) {
					String fdLeaveType = leaveDetail.getFdLeaveType();
					SysTimeLeaveRule rule = sysTimeLeaveRuleService
							.getLeaveRuleByType(fdLeaveType);

					SysTimeLeaveTimeDto dto = SysTimeUtil.getLeaveTimes(
							leaveDetail.getFdPerson(),
							leaveDetail.getFdStartTime(),
							leaveDetail.getFdEndTime(),
							leaveDetail.getFdStartNoon(),
							leaveDetail.getFdEndNoon(), rule.getFdStatDayType(),
							leaveDetail.getFdStatType(),fdLeaveType);

					Float days = dto.getLeaveTimeDays();
					if (!days.equals(leaveTime)) {
						result = false;
						messages.addError(
								new KmssMessage(ResourceUtil.getString(
										"sys-time:sysTime.import.error.leavetime.invalid")));
					}
				}
			} catch (Exception e) {
				result = false;
				messages.addError(new KmssMessage(ResourceUtil.getString(
						"sys-time:sysTime.import.error.leavetime.format")));
			}
		} else {
			result = false;
			messages.addError(new KmssMessage(ResourceUtil.getString(
					"sys-time:sysTime.import.error.leavetime.null")));
		}
		return result;
	}

	@Override
	public SysTimeLeaveRule getLeaveaRule(String leaveType) {
		try {
			if (StringUtil.isNotNull(leaveType)) {
				SysTimeLeaveRule rule = sysTimeLeaveRuleService.getLeaveRuleByType(leaveType);
				if(rule==null){
					//兼容处理
					List<SysTimeLeaveRule> ruleList = sysTimeLeaveRuleService.getLeaveRuleList("");
					for (SysTimeLeaveRule leaveRule : ruleList) {
						if (Integer.valueOf(leaveType).equals(
								Integer.valueOf(leaveRule.getFdSerialNo()))) {
							return leaveRule;
						}
					}
				}
				
				return rule;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private SysTimeLeaveRule getLeaveaRule(List<SysTimeLeaveRule> leaveRuleList,
			String leaveName) {
		for (SysTimeLeaveRule leavaRule : leaveRuleList) {
			if (leaveName.equals(leavaRule.getFdName())) {
				return leavaRule;
			}
		}
		return null;
	}

	@Override
	public List findLeaveDetail(String personId, Date startTime, Date endTime,
								String leaveType) throws Exception {
		List recordList = this.findLeaveDetail(personId, startTime, endTime,
				leaveType, null);
		return recordList;
	}

	/**
	 * 根据人员和流程ID 加班、请假明细
	 * @param personId 人员id
	 * @param kmReviewId 流程id
	 * @param beginDate 流程单据的开始时间
	 * @param endDate 流程单据的结束时间
	 * @return
	 * @throws Exception
	 */
	@Override
	public SysTimeLeaveDetail findLeaveDetail(String personId,String kmReviewId,Date beginDate,Date endDate) throws Exception {

		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysTimeLeaveDetail.fdReviewId =:fdReviewId and sysTimeLeaveDetail.fdStartTime=:fdStartTime and sysTimeLeaveDetail.fdEndTime =:fdEndTime and sysTimeLeaveDetail.fdPerson.fdId= :fdPersonId ");
		hqlInfo.setParameter("fdReviewId",kmReviewId);
		hqlInfo.setParameter("fdStartTime",beginDate);
		hqlInfo.setParameter("fdEndTime",endDate);
		hqlInfo.setParameter("fdPersonId",personId);
		SysTimeLeaveDetail sysTimeLeaveDetail = (SysTimeLeaveDetail) this.findFirstOne(hqlInfo);
		if(sysTimeLeaveDetail != null){
			return sysTimeLeaveDetail;
		}
		return null;
	}

	@Override
	public List findLeaveDetail(String personId, Date startTime,
			Boolean isUpdateAttend) throws Exception {
		List recordList = this.findLeaveDetail(personId, startTime, null, null,
				isUpdateAttend);
		return recordList;
	}

	private List findLeaveDetail(String personId, Date startTime, Date endTime,
			String leaveType, Boolean isUpdateAttend) throws Exception {
		List<SysTimeLeaveDetail> recordList = new ArrayList<SysTimeLeaveDetail>();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			StringBuffer whereBlock = new StringBuffer();
			whereBlock.append(
					"sysTimeLeaveDetail.fdPerson.fdId=:personId and (sysTimeLeaveDetail.fdType is null or sysTimeLeaveDetail.fdType=:fdType) ");
			whereBlock.append(
					" and (sysTimeLeaveDetail.fdTotalTime>0 or sysTimeLeaveDetail.fdLeaveTime>0 or (sysTimeLeaveDetail.fdTotalTime=0 and (sysTimeLeaveDetail.fdResumeTime=0 or sysTimeLeaveDetail.fdResumeTime is null)))");
			if (StringUtil.isNotNull(leaveType)) {
				whereBlock.append(
						" and sysTimeLeaveDetail.fdLeaveType in (:fdLeaveType)");
				Set<String> leaveTypeSet = new HashSet<String>();
				leaveTypeSet.add(leaveType);
				leaveTypeSet.add(Integer.valueOf(leaveType).toString());
				hqlInfo.setParameter("fdLeaveType", leaveTypeSet);
			}
			if (isUpdateAttend != null && Boolean.TRUE.equals(isUpdateAttend)) {
				// whereBlock.append(" and
				// sysTimeLeaveDetail.fdIsUpdateAttend=1");
			}

			whereBlock.append(
					" and ((sysTimeLeaveDetail.fdStatType is null or sysTimeLeaveDetail.fdStatType=3) and sysTimeLeaveDetail.fdEndTime>:startTime");
			if (endTime != null) {
				whereBlock
						.append(" and sysTimeLeaveDetail.fdStartTime<:endTime");
			}
			whereBlock.append(
					" or (sysTimeLeaveDetail.fdStatType=1 or sysTimeLeaveDetail.fdStatType=2) and sysTimeLeaveDetail.fdEndTime>=:startTime");
			if (endTime != null) {
				whereBlock
						.append(" and sysTimeLeaveDetail.fdStartTime<:endTime");
			}
			whereBlock.append(")");
			hqlInfo.setWhereBlock(whereBlock.toString());
			hqlInfo.setParameter("personId", personId);
			hqlInfo.setParameter("fdType", 1);
			hqlInfo.setParameter("startTime", startTime);
			if (endTime != null) {
				hqlInfo.setParameter("endTime", endTime);
			}
			return this.findList(hqlInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取用户对应请假明细失败,personId=" + personId, e);
		}
		return recordList;
	}

	@Override
	public void updateLeaveDetail(String leaveDetailId, int newTotalTime,
			Integer statType, Date startTime) throws Exception {
		if (StringUtil.isNull(leaveDetailId)) {
			throw new Exception("找不到假期明细");
		}
		SysTimeLeaveDetail leaveDetail = (SysTimeLeaveDetail) findByPrimaryKey(
				leaveDetailId);
		if (leaveDetail == null) {
			throw new Exception("找不到假期明细");
		}
		Float fdTotalTime = leaveDetail.getFdTotalTime();
		if (fdTotalTime.intValue() == newTotalTime) {
			return;
		}
		String days = SysTimeUtil.getLeaveDays(newTotalTime, statType);
		leaveDetail.setFdTotalTime((float) newTotalTime);
		leaveDetail.setFdLeaveTime(Float.valueOf(days));
		update(leaveDetail);
		// 判断用户额度
		SysTimeLeaveRule leaveRule = getLeaveaRule(
				leaveDetail.getFdLeaveType());
		if (leaveRule == null) {
			logger.error("找不到假期类型,编号为:" + leaveDetail.getFdLeaveType());
			throw new Exception("找不到假期类型,编号为:" + leaveDetail.getFdLeaveType());
		}
		// 是否开启额度
		if (!Boolean.TRUE.equals(leaveRule.getFdIsAmount())) {
			return;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(leaveDetail.getFdEndTime());
		Integer endYear = cal.get(Calendar.YEAR);
		cal.setTime(new Date());
		Integer nowYear = cal.get(Calendar.YEAR);
		int year = Math.max(nowYear, endYear);

		Float fdLeaveTime = leaveDetail.getFdLeaveTime();
		if (fdLeaveTime != null && fdLeaveTime >= 0f) {
			int changeValue = newTotalTime - fdTotalTime.intValue();
			SysTimeLeaveAmountItem item = getAmountItem(
					leaveDetail.getFdPerson().getFdId(),
					year, leaveRule);
			if (item == null) {
				// 当前年没有额度信息,则取去年的额度
				item = getAmountItem(leaveDetail.getFdPerson().getFdId(),
						year - 1, leaveRule);
			}
			// 更新用户假期额度
			String newDays = SysTimeUtil.getLeaveDays(changeValue, statType);
			updateUserLeaveAmount(item, Float.valueOf(newDays), leaveDetail,
					leaveRule, startTime);
			// 增加流水记录
			SysTimeLeaveAmountFlow amountFlow = new SysTimeLeaveAmountFlow();
			amountFlow.setDocCreateTime(new Date());
			amountFlow.setDocCreator(UserUtil.getUser());
			amountFlow.setFdBusType("5");// 请假
			amountFlow.setFdDesc("");
			amountFlow.setFdLeaveType(leaveDetail.getFdLeaveType());
			amountFlow.setFdPerson(leaveDetail.getFdPerson());
			amountFlow.setFdStatType(statType);
			amountFlow.setFdDayConvertTime(SysTimeUtil.getConvertTime());
			amountFlow.setFdTotalTime(Math.abs(Float.valueOf(newDays))
					* SysTimeUtil.getConvertTime() * 60);
			String fdMethod = "add";
			if (changeValue > 0) {
				fdMethod = "sub";
			}
			amountFlow.setFdMethod(fdMethod);
			sysTimeLeaveAmountFlowService.add(amountFlow);
		}
	}

}
