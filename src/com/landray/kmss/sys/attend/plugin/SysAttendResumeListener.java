package com.landray.kmss.sys.attend.plugin;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.framework.spring.event.interfaces.IEventCallBack;
import com.landray.kmss.framework.spring.event.transaction.EventOfTransactionCommit;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.engine.task.NodeInstanceUtils;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveResume;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveDetailService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveResumeService;
import com.landray.kmss.sys.time.util.SysTimeLeaveTimeDto;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.TransactionStatus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 销假事件监听
 * @author cuiwj
 * @version 1.0 2018-08-07
 */
public class SysAttendResumeListener extends SysAttendListenerCommonImp
		implements IEventListener, IEventMulticasterAware, ApplicationContextAware,
		ApplicationListener<Event_Common> {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendResumeListener.class);

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	private IEventMulticaster multicaster;

	@Override
	public void setEventMulticaster(IEventMulticaster multicaster) {
		this.multicaster = multicaster;
	}

	private ISysTimeLeaveDetailService sysTimeLeaveDetailService;

	public void setSysTimeLeaveDetailService(
			ISysTimeLeaveDetailService sysTimeLeaveDetailService) {
		this.sysTimeLeaveDetailService = sysTimeLeaveDetailService;
	}

	public ISysTimeLeaveDetailService getSysTimeLeaveDetailService() {
		if (sysTimeLeaveDetailService == null) {
			sysTimeLeaveDetailService = (ISysTimeLeaveDetailService) SpringBeanUtil.getBean("sysTimeLeaveDetailService");
		}
		return sysTimeLeaveDetailService;
	}

	private ISysTimeLeaveResumeService sysTimeLeaveResumeService;

	public void setSysTimeLeaveResumeService(
			ISysTimeLeaveResumeService sysTimeLeaveResumeService) {
		this.sysTimeLeaveResumeService = sysTimeLeaveResumeService;
	}

	public ISysTimeLeaveResumeService getSysTimeLeaveResumeService() {
		if (sysTimeLeaveResumeService == null) {
			sysTimeLeaveResumeService = (ISysTimeLeaveResumeService) SpringBeanUtil.getBean("sysTimeLeaveResumeService");
		}
		return sysTimeLeaveResumeService;
	}

	@Override
	public void handleEvent(EventExecutionContext execution, String parameter)
			throws Exception {

		String processFlag = execution.getProcessParameters().getInstanceParamValue(execution.getProcessInstance(),PROCESS_FLAG_KEY);
		if (PROCESS_FLAG_RUN_VALUE.equals(processFlag) || PROCESS_FLAG_ERROR_VALUE.equals(processFlag)) {
			//非考勤标识的流程。不支持
			//新流程时间不进老流程处理
			return;
		}

		logger.debug(
				"receive SysAttendResumeListener:parameter=" + parameter);
		try {
			String routeType = execution.getNodeInstance().getFdRouteType();
			String docStatus = execution.getExecuteParameters()
					.getExpectMainModelStatus();
			if ((NodeInstanceUtils.ROUTE_TYPE_NORMAL.equals(routeType)
					|| NodeInstanceUtils.ROUTE_TYPE_JUMP.equals(routeType))
					&& !SysDocConstant.DOC_STATUS_DISCARD.equals(docStatus)
					&& !SysDocConstant.DOC_STATUS_DRAFT.equals(docStatus)) {
				logger.debug("start handleEvent...");
				JSONObject params = JSONObject.fromObject(parameter);
				IBaseModel mainModel = execution.getMainModel();
				if (mainModel instanceof IExtendDataModel) {
					List<SysAttendBusiness> list = getSysAttendBusinessService()
							.findByProcessId(mainModel.getFdId());
					// 同一流程不会重复操作
					if (list.isEmpty()) {
						List<SysAttendBusiness> busList = getBusinessList(
								params,
								mainModel);
						if (busList == null || busList.isEmpty()) {
							logger.warn(
									"销假流程没有找到相应的销假数据,请查看销假事件配置是否准确!parameter:"
											+ parameter);
							return;
						}
						TransactionStatus status = null;
						try {
							status = TransactionUtils.beginNewTransaction();
							for (SysAttendBusiness bus : busList) {
								// 修改请假相关信息
								updateLeaveBiz(bus, false, null);
							}
							TransactionUtils.commit(status);
							// 重新统计
							//restat(busList);
						} catch (Exception ex) {
							logger.error(
									"销假流程事件同步到考勤失败,事务回滚!" + ex.getMessage(),
									ex);
							if (status != null) {
								TransactionUtils.getTransactionManager()
										.rollback(status);
							}
						}

					} else {
						logger.warn(
								"同个流程只执行一次,忽略此次操作!流程id:"
										+ mainModel.getFdId()
										+ ";parameter=" + parameter);
					}
				}

			}
		} catch (Exception e) {
			logger.error("销假流程事件同步到考勤失败!" + parameter, e);
		}
	}

	/**
	 * 增加销假记录,修改请假明细等信息
	 * 
	 * @param leaveDetail
	 *            请假明细
	 * @throws Exception
	 */
	private String addResumeDetail(SysTimeLeaveDetail leaveDetail,
			SysAttendBusiness resumeBus, SysOrgPerson person,
			Map<String, Object> dateMap) throws Exception {
		Date fdBusStartTime = resumeBus.getFdBusStartTime();
		Date fdBusEndTime = resumeBus.getFdBusEndTime();

		boolean isDay = leaveDetail.getFdStatType() == 1
				|| leaveDetail.getFdStatType() == 2;
		Date startTime = !isDay ? fdBusStartTime
				: AttendUtil.getDate(fdBusStartTime, 0);
		Date endTime = !isDay ? fdBusEndTime
				: AttendUtil.getDate(fdBusEndTime, 0);
		SysTimeLeaveResume leaveResume = new SysTimeLeaveResume();
		leaveResume.setFdId(IDGenerator.generateID());
		leaveResume.setFdLeaveDetail(leaveDetail);
		leaveResume.setFdStartTime(startTime);
		leaveResume.setFdEndTime(endTime);

		// 实际销假区间
		Integer statType = (Integer) dateMap.get("statType");
		Date tmpStartTime = (Date) dateMap.get("startTime");
		Date tmpEndTime = (Date) dateMap.get("endTime");
		Integer fdStartNoon = null, fdEndNoon = null;
		if (Integer.valueOf(2).equals(statType)) {
			fdStartNoon = (Integer) dateMap.get("startNoon");
			fdEndNoon = (Integer) dateMap.get("endNoon");
		}
		// 计算销假时长必须以有效请假时间区间计算
		SysTimeLeaveTimeDto dto = getLeaveTimes(person, resumeBus.getFdBusType(),
				tmpStartTime, tmpEndTime, leaveDetail.getFdStatType(),
				fdStartNoon, fdEndNoon);
		leaveResume.setFdTotalTime(dto.getLeaveTimeMins().floatValue());
		leaveResume.setFdLeaveTime(dto.getLeaveTimeDays());
		leaveResume.setFdOprType(1);
		leaveResume.setFdOprStatus(0);
		leaveResume.setFdPerson(person);
		leaveResume.setFdIsUpdateAttend(true);
		leaveResume.setFdUpdateAttendStatus(1);
		leaveResume.setFdReviewId(resumeBus.getFdProcessId());
		leaveResume.setFdReviewName(resumeBus.getFdProcessName());
		leaveResume.setFdStartNoon(resumeBus.getFdStartNoon());
		leaveResume.setFdEndNoon(resumeBus.getFdEndNoon());
		leaveResume.setFdResumeType(resumeBus.getFdBusType());
		leaveResume.setDocCreateTime(new Date());
		leaveResume.setDocCreator(UserUtil.getUser());
		String id = getSysTimeLeaveResumeService().getBaseDao().add(leaveResume);
		return id;
	}

	@Override
	public void onApplicationEvent(Event_Common event) {
		try {
			if ("updateResume".equals(event.getSource().toString())) {
				Map params = ((Event_Common) event).getParams();
				if (null == params || params.size() <= 0) {
					return;
				}
				String leaveResumeId = (String) params.get("leaveResumeId");
				SysTimeLeaveResume leaveResume = (SysTimeLeaveResume) getSysTimeLeaveResumeService()
						.findByPrimaryKey(leaveResumeId, null, true);
				SysTimeLeaveDetail leaveDetail = leaveResume.getFdLeaveDetail();
				if (leaveDetail != null && Integer.valueOf(1)
						.equals(leaveDetail.getFdUpdateAttendStatus())
						&& !Integer.valueOf(1)
						.equals(leaveResume.getFdUpdateAttendStatus())
				) {// 请假明细更新到了考勤，才允许销假
					Date startTime = leaveResume.getFdStartTime();
					Date endTime = leaveResume.getFdEndTime();
					String personIds = leaveResume.getFdPerson().getFdId();
					String processId = leaveResume.getFdReviewId();
					String processName = leaveResume.getFdReviewName();
					SysAttendBusiness business = getBusinessModel(startTime,
							endTime, personIds, processId,
							Integer.valueOf(leaveDetail.getFdLeaveType()),
							leaveDetail.getFdStatType(),
							leaveResume.getFdStartNoon(),
							leaveResume.getFdEndNoon(),
							leaveDetail.getFdLeaveName(), processName, null);

					List<SysAttendBusiness> list = new ArrayList<SysAttendBusiness>();
					list.add(business);
					// 更新成功
					leaveResume.setFdUpdateAttendStatus(1);
					getSysTimeLeaveResumeService().update(leaveResume);

					// 更新请假信息
					updateLeaveBiz(business, true, leaveDetail);

					// 重新统计
					//restat(list);
				}
			}
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
	}

	private void updateLeaveByHand() {

	}

	private Float getBetweenDays(Date startTime, Date endTime) {
		return (endTime.getTime() - startTime.getTime())
				/ (60 * 60 * 24 * 1000f);
	}

	/**
	 * 获取请假明细
	 * 
	 * @param personId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	protected List getLeaveDetail(String personId, Date startTime, Date endTime,
			String fdLeaveType) {
		List recordList = new ArrayList();
		try {
			return getSysTimeLeaveDetailService().findLeaveDetail(personId,
					startTime, endTime, fdLeaveType);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取销假流程中对应请假明细失败,personId=" + personId, e);
		}
		return recordList;
	}

	/**
	 * 修改考勤流程记录表与请假明细记录表信息
	 * 
	 * @param business
	 * @param isHand
	 *            是否手工销假
	 * @throws Exception
	 */
	protected void updateLeaveBiz(SysAttendBusiness business, boolean isHand,
			SysTimeLeaveDetail resumeLeaveDetail)
			throws Exception {
		// 日期或时间戳
		Date fdBusStartTime = business.getFdBusStartTime();
		Date fdBusEndTime = business.getFdBusEndTime();
		Integer startNoon = business.getFdStartNoon();
		Integer endNoon = business.getFdEndNoon();

		List<SysOrgPerson> personList = getSysOrgCoreService()
				.expandToPerson(business.getFdTargets());
		SysTimeLeaveRule leaveRule = AttendUtil.getLeaveRuleByType(business.getFdBusType());

		for (SysOrgPerson person : personList) {
			Date startTime = AttendUtil.getDate(fdBusStartTime, 0);
			Date endTime = AttendUtil.getDate(fdBusEndTime, 1);

			// 注意:时间以天为单们,获取用户时间区间内的所有考勤流程记录列表(存在不是该次销假对应的请假记录)
			List leaveList = getLeaveBiz(person.getFdId(), startTime, endTime,
					business.getFdBusType());
			if (leaveList.isEmpty()) {
				String error = "当前销假流程没法找到用户" + person.getFdName()
						+ "对应的考勤请假流程记录!";
				logger.warn(error);
				throw new Exception(error);
			}
			// 获取请假明细记录
			if (leaveRule == null) {
				String error = "当前销假流程没法找到用户" + person.getFdName()
						+ "对应的请假类型!fdBusType:" + business.getFdBusType();
				logger.warn(error);
				throw new Exception(error);
			}
			List<SysTimeLeaveDetail> leaveDetailList = getLeaveDetail(
					person.getFdId(), startTime,
					endTime, leaveRule.getFdSerialNo());
			if (isHand) {// 手工销假时,不需重新获取请假明细
				leaveDetailList.clear();
				leaveDetailList.add(resumeLeaveDetail);
			}
			if (leaveDetailList.isEmpty()) {
				logger.warn("当前销假流程没法找到用户" + person.getFdName() + "对应的请假明细记录!");
				throw new Exception();
			}
			for (int k = 0; k < leaveList.size(); k++) {
				SysAttendBusiness leave = (SysAttendBusiness) leaveList.get(k);

				// 过滤非该次销假的请假数据
				Date leaveStartTime = leave.getFdBusStartTime();
				Date leaveEndTime = leave.getFdBusEndTime();
				Integer leaveStartNoon = leave.getFdStartNoon();
				Integer leaveEndNoon = leave.getFdEndNoon();
				Integer leaveStatType = leave.getFdStatType();
				Map<String, Date> leaveStartAndEndMap = SysTimeUtil
						.getStartAndEndTime(
								leaveStartTime, leaveEndTime, leaveStatType,
								leaveStartNoon, leaveEndNoon);
				Date leaveStart = leaveStartAndEndMap.get("startTime");
				Date leaveEnd = leaveStartAndEndMap.get("endTime");
				// 实际销假时间
				Map<String, Date> startAndEndMap = SysTimeUtil
						.getStartAndEndTime(
								fdBusStartTime, fdBusEndTime, leaveStatType,
								startNoon, endNoon);
				Date busStartTime = startAndEndMap.get("startTime");
				Date busEndTime = startAndEndMap.get("endTime");
				if (busEndTime.after(leaveStart)
						&& busStartTime.before(leaveEnd)) {
					// 销假区间在请假时间内
				} else {
					// 销假区间不在请假时间内
					logger.warn(
							"当前销假流程时间区间不在请假时间区间内,忽略处理!销假开始时间:" + busStartTime
									+ ";销假结束时间:" + busEndTime + ";请假开始时间:"
									+ leaveStart + ";结束时间:" + leaveEnd
									+ ";fdId:" + leave.getFdId());
					continue;
				}

				SysTimeLeaveDetail leaveDetail = getLeaveDeail(leaveDetailList,
						leave, isHand);
				if (leaveDetail == null) {
					logger.warn("当前销假流程没法找到用户" + person.getFdName()
							+ "对应的考勤流程记录与请假明细记录相匹配数据!fdBussId:"
							+ leave.getFdId());
					continue;
				}
				boolean isUpdate = false;
				// 实际销假时间区间
				Map<String, Object> dateMap = new HashMap<String, Object>();
				dateMap.put("person", person);
				//自然日还是工作日
				dateMap.put("statDayType", leaveRule.getFdStatDayType());
				// 0.更新请假流程申请记录信息
				if (leave.getFdStatType() == 1) {
					// 按天销假
					isUpdate = updateLeaveBizByDay(leave,
							AttendUtil.getDate(fdBusStartTime, 0),
							AttendUtil.getDate(fdBusEndTime, 0), dateMap);
				} else if (leave.getFdStatType() == 2) {
					// 按半天销假
					isUpdate = updateLeaveBizByHalfDay(leave,
							AttendUtil.getDate(fdBusStartTime, 0),
							AttendUtil.getDate(fdBusEndTime, 0), startNoon,
							endNoon, dateMap);
				} else if (leave.getFdStatType() == 3) {
					// 按小时销假
					isUpdate = updateLeaveBizByTime(leave, fdBusStartTime,
							fdBusEndTime, dateMap);
				} else if (leave.getFdStatType() == null) {
					// 按小时销假
					isUpdate = updateLeaveBizByTime(leave, fdBusStartTime,
							fdBusEndTime, dateMap);
				}
				//重新计算的请假流程的业务列表
				List<SysAttendBusiness> businessList = new ArrayList<>();
				businessList.add(leave);
				if (dateMap.get("newLeave") != null) {
					businessList.add((SysAttendBusiness) dateMap.get("newLeave"));
				}

				// 更新请假明细
				if (isUpdate) {
					// 1.考勤流程记录表,增加销假记录
					getSysAttendBusinessService().add(business);
					if (!isHand) {
						// 3.增加销假明细记录
						String resumeId = addResumeDetail(leaveDetail, business, person, dateMap);
						// 4.更新请假明细记录,假期额度
						getSysTimeLeaveResumeService().updateLeave(resumeId,
								dateMap);
					}
					//2、先删除考勤
					updateSysAttendMain(person, dateMap, leave);
					// 根据请假明细来更新考勤原始记录
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("businessList", businessList);
					params.put("dateList", dateMap.get("dateList"));
					List<String> personIds = new ArrayList<>();
					personIds.add(person.getFdId());
					params.put("personIds", personIds);
					//当前事务结束以后。重新更新考勤记录
					multicaster.attatchEvent(
							new EventOfTransactionCommit(StringUtils.EMPTY),
							new IEventCallBack() {
								@Override
								public void execute(ApplicationEvent arg0)
										throws Throwable {
									applicationContext.publishEvent(new Event_Common("resetLeaveInfo", params));
								}
							});
				} else {
					logger.warn("用户" + person.getFdName()
							+ "的销假流程配置的时间区间没有找到对应的请假记录数据,忽略处理!销假时间区间:"
							+ business.getFdBusStartTime() + "~"
							+ business.getFdBusEndTime() + ";startNoon:"
							+ business.getFdStartNoon() +
							",endNoon:" + business.getFdEndNoon() + ";销假类型:"
							+ business.getFdBusType());
				}
			}
		}
	}

	/**
	 * @param leaveList
	 * @param buss
	 * @param isHand
	 * @return
	 */
	private SysTimeLeaveDetail getLeaveDeail(List<SysTimeLeaveDetail> leaveList,
			SysAttendBusiness buss, boolean isHand) {
		for(SysTimeLeaveDetail leaveDetail : leaveList){
			String fdBusType = buss.getFdBusType() != null
					? buss.getFdBusType().toString() : "";
			String fdLeaveType = leaveDetail.getFdLeaveType() != null
					? Integer.valueOf(leaveDetail.getFdLeaveType()).toString()
					: "";
			String fdLeaveId = leaveDetail.getFdId();
			if (StringUtil.isNotNull(buss.getFdBusDetailId())) {
				if (leaveDetail.getFdLeaveTime() > 0
						&& fdLeaveType.equals(fdBusType)
						&& fdLeaveId.equals(buss.getFdBusDetailId())) {
					return leaveDetail;
				}
				if (isHand && fdLeaveType.equals(fdBusType)
						&& fdLeaveId.equals(buss.getFdBusDetailId())) {
					return leaveDetail;
				}
			} else {
				if (leaveDetail.getFdLeaveTime() > 0
						&& fdLeaveType.equals(fdBusType)) {
					return leaveDetail;
				}
				if (isHand && fdLeaveType.equals(fdBusType)) {
					return leaveDetail;
				}
			}

		}
		return null;
	}

	/**
	 * 修改请假信息，按时间
	 * 
	 * @param leave
	 * @param startTime
	 *            销假开始时间
	 * @param endTime
	 *            销假结束时间
	 * @throws Exception
	 */
	private boolean updateLeaveBizByTime(SysAttendBusiness leave,
			Date startTime, Date endTime, Map<String, Object> dateMap)
			throws Exception {
		boolean result = false;
		// 请假时间区间
		Date fdBusStartTime = leave.getFdBusStartTime();
		Date fdBusEndTime = leave.getFdBusEndTime();
		SysOrgPerson person = (SysOrgPerson) dateMap.get("person");
		Integer statDayType = (Integer) dateMap.get("statDayType");
		// 实际销假时间区间
		if (fdBusEndTime.getTime() > endTime.getTime() && fdBusStartTime.getTime() < startTime.getTime()) {
			// 区间内拆分成两个请假区间
			SysAttendBusiness newLeave = cloneLeaveBiz(leave);
			leave.setFdBusEndTime(startTime);
			newLeave.setFdBusStartTime(endTime);
			addNewBusinessInfo(endTime,fdBusEndTime,newLeave,dateMap);
			result = true;
			dateMap.put("startTime", startTime);
			dateMap.put("endTime", endTime);
		} else if (fdBusEndTime.getTime() > startTime.getTime()
				&& fdBusStartTime.getTime() < startTime.getTime()) {
			//请假的结束时间大于销假的开始时间 并且 请假的开始时间小于 销假的开始时间
			leave.setFdBusEndTime(startTime);
			dateMap.put("startTime", startTime);
			dateMap.put("endTime", fdBusEndTime);
			result = true;
		} else if (fdBusEndTime.getTime() <= endTime.getTime()
				&& fdBusStartTime.getTime() >= startTime.getTime()) {
			leave.setFdDelFlag(1);
			dateMap.put("startTime", fdBusStartTime);
			dateMap.put("endTime", fdBusEndTime);
			result = true;
		} else if (fdBusEndTime.getTime() > endTime.getTime()
				&& fdBusStartTime.getTime() < endTime.getTime()) {
			//如果截止时间正好是打卡时间。这样拆分。会使下个开始时间有在前1个班次范围内。暂时先加2秒钟
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endTime);
			calendar.add(Calendar.SECOND, 2);
			endTime=calendar.getTime();
			//如果按照新的结束时间为开始时间计算得到请假时长。则处理。否则删除
			SysTimeLeaveTimeDto dto= SysTimeUtil.getLeaveTimes(person,
					endTime,
					leave.getFdBusEndTime(),
					leave.getFdStartNoon(),
					leave.getFdEndNoon(), statDayType,
					leave.getFdStatType(),String.valueOf(leave.getFdBusType()));
			int leaveTimes =dto.getLeaveTimeMins();
			if(leaveTimes > 0){
				//如果结束时间不在工作时间内，
				leave.setFdBusStartTime(endTime);
			} else {
				leave.setFdDelFlag(1);
			}
			result = true;
			dateMap.put("startTime", fdBusStartTime);
			dateMap.put("endTime", endTime);
		}
		updateBusinessInfo(result,leave,person,statDayType);
		if (!dateMap.isEmpty()) {
			dateMap.put("statType", 3);
		}
		return result;
	}

	/**
	 * 拆分请假业务单据后的事件是否能算出请假时长，如果算不出请假时长，则不增加
	 * @param endTime 最新的结束事件
	 * @param fdBusEndTime 原请假单的结束事件
	 * @param newLeave 新增加的对象
	 * @param dateMap 数据
	 */
	private void addNewBusinessInfo(Date endTime,Date fdBusEndTime,SysAttendBusiness newLeave,Map<String, Object> dateMap) throws Exception {
		SysOrgPerson person = (SysOrgPerson) dateMap.get("person");
		//如果结束时间不在请假结束时间内，
		if(newLeave.getFdBusStartTime() ==null ) {
			newLeave.setFdBusStartTime(endTime);
		}
		//如果截止时间正好是打卡时间。这样拆分。会使下个开始时间有在前1个班次范围内。暂时先加2秒钟
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(newLeave.getFdBusStartTime());
		calendar.add(Calendar.SECOND, 2);
		newLeave.setFdBusStartTime(calendar.getTime());
		Integer statDayType = (Integer) dateMap.get("statDayType");
		SysTimeLeaveTimeDto dto = SysTimeUtil.getLeaveTimes(person,
				newLeave.getFdBusStartTime(),
				newLeave.getFdBusEndTime(),
				newLeave.getFdStartNoon(),
				newLeave.getFdEndNoon(), statDayType,
				newLeave.getFdStatType(),String.valueOf(newLeave.getFdBusType()));
		int leaveTimes=dto.getLeaveTimeMins();
		if(leaveTimes > 0){
			getSysAttendBusinessService().add(newLeave);
			dateMap.put("newLeave", newLeave);
		}
	}
	/**
	 * 如果请假周期 有请假时长，则更新，否则把请假单据设置为无效
	 * @param result 是否需要更新
	 * @param leave 请假业务单据
	 * @param person 人员
	 * @throws Exception
	 */
	private void updateBusinessInfo(boolean result,SysAttendBusiness leave,SysOrgPerson person,Integer statDayType) throws Exception {
		if(result){
			if(!Integer.valueOf(1).equals(leave.getFdDelFlag())) {
				//判断该请假信息的请假时长，如果等于0 则把请假单据失效
				SysTimeLeaveTimeDto dto = SysTimeUtil.getLeaveTimes(person,
						leave.getFdBusStartTime(),
						leave.getFdBusEndTime(),
						leave.getFdStartNoon(),
						leave.getFdEndNoon(), statDayType,
						leave.getFdStatType(),String.valueOf(leave.getFdBusType()));
				if (dto.getLeaveTimeMins() <=0 ) {
					leave.setFdDelFlag(1);
				}
			}
			getSysAttendBusinessService().update(leave);
		}
	}


	/**
	 * 修改请假信息，按天
	 * 
	 * @param leave
	 *            请假对象
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */
	private boolean updateLeaveBizByDay(SysAttendBusiness leave, Date startTime,
			Date endTime, Map<String, Object> dateMap) throws Exception {
		boolean result = false;
		Date fdBusStartTime = leave.getFdBusStartTime();
		Date fdBusEndTime = leave.getFdBusEndTime();
		SysOrgPerson person = (SysOrgPerson) dateMap.get("person");
		// 实际销假时间区间
		if (fdBusEndTime.getTime() > endTime.getTime()
				&& fdBusStartTime.getTime() < startTime.getTime()) {// 请假时间包含销假时间
			SysAttendBusiness newLeave = cloneLeaveBiz(leave);
			leave.setFdBusEndTime(AttendUtil.getDate(startTime, -1));
			Date tempEndTime =AttendUtil.getDate(endTime, 1);
			newLeave.setFdBusStartTime(tempEndTime);
			addNewBusinessInfo(tempEndTime,fdBusEndTime,newLeave,dateMap);
			result = true;
			dateMap.put("startTime", startTime);
			dateMap.put("endTime", endTime);
		} else if (fdBusEndTime.getTime() >= startTime.getTime()
				&& fdBusStartTime.getTime() < startTime.getTime()) {
			leave.setFdBusEndTime(AttendUtil.getDate(startTime, -1));
			result = true;
			dateMap.put("startTime", startTime);
			dateMap.put("endTime", fdBusEndTime);
		} else if (fdBusEndTime.getTime() <= endTime.getTime()
				&& fdBusStartTime.getTime() >= startTime.getTime()) {// 销假时间包括请假时间
			leave.setFdDelFlag(1);
			dateMap.put("startTime", fdBusStartTime);
			dateMap.put("endTime", fdBusEndTime);
			result = true;
		} else if (fdBusEndTime.getTime() > endTime.getTime()
				&& fdBusStartTime.getTime() <= endTime.getTime()) {// 销假结束时间在请假时间区间内
			leave.setFdBusStartTime(AttendUtil.getDate(endTime, 1));
			result = true;
			dateMap.put("startTime", fdBusStartTime);
			dateMap.put("endTime", endTime);
		}
		Integer statDayType = (Integer) dateMap.get("statDayType");
		updateBusinessInfo(result,leave,person,statDayType);
		if (!dateMap.isEmpty()) {
			dateMap.put("statType", 1);
		}
		return result;
	}

	/**
	 * 修改请假信息，按半天
	 * 
	 * @param leave
	 * @param startTime
	 *            销假开始日期
	 * @param endTime
	 *            销假结束日期
	 * @param startNoon
	 *            上午标识
	 * @param endNoon
	 *            下午标识
	 * @throws Exception
	 */
	private boolean updateLeaveBizByHalfDay(SysAttendBusiness leave,
			Date startTime, Date endTime, Integer startNoon, Integer endNoon,
			Map<String, Object> dateMap)
			throws Exception {
		boolean result = false;
		Date fdBusStartTime = leave.getFdBusStartTime();
		Date fdBusEndTime = leave.getFdBusEndTime();

		Date _fdBusStartTime = this.getStartTime(fdBusStartTime,
				leave.getFdStartNoon());
		Date _fdBusEndTime = this.getEndTime(fdBusEndTime,
				leave.getFdEndNoon());

		Date _startTime = this.getStartTime(startTime, startNoon);
		Date _endTime = this.getEndTime(endTime, endNoon);

		SysOrgPerson person = (SysOrgPerson) dateMap.get("person");

		// 实际销假时间区间
		if (_fdBusEndTime.getTime() > _endTime.getTime()
				&& _fdBusStartTime.getTime() < _startTime.getTime()) {
			SysAttendBusiness newLeave = cloneLeaveBiz(leave);
			//拆分请假流程的时间区间。。原 请假开始时间 到本次销假的开始时间为一个周期；
			//本次销假的开始时间为上午，则把原来的请假时间结束设置为昨天下午
			if (startNoon == 1) {
				leave.setFdEndNoon(2);
				leave.setFdBusEndTime(AttendUtil.getDate(startTime, -1));
			} else {
				//销假的开始时间为下午 结束时间为今天上午
				leave.setFdEndNoon(1);
				leave.setFdBusEndTime(this.getStartTime(startTime, 1));
			}
			if (leave.getFdStartNoon() == null) {
				leave.setFdStartNoon(1);
			}
			if (leave.getFdEndNoon() == null) {
				leave.setFdEndNoon(2);
			}
			//新的开始时间：结束标识为上午，则从结束日期的下午开始。
			if (endNoon == 1) {
				newLeave.setFdStartNoon(2);
				newLeave.setFdBusStartTime(this.getStartTime(endTime, 1));
			} else {
				//结束标识为下午，则从第二天的上午开始
				newLeave.setFdStartNoon(1);
				newLeave.setFdBusStartTime( AttendUtil.getDate(endTime, 1));
			}

			addNewBusinessInfo(_endTime,fdBusEndTime,newLeave,dateMap);
			dateMap.put("startTime", startTime);
			dateMap.put("endTime", endTime);
			dateMap.put("startNoon", startNoon);
			dateMap.put("endNoon", endNoon);
			result = true;
		} else if (_fdBusEndTime.getTime() >= _startTime.getTime()
				&& _fdBusStartTime.getTime() < _startTime.getTime()) {
			Date tmpEndTime = AttendUtil.getDate(startTime, -1);
			Integer leaveEndNoon = leave.getFdEndNoon();
			if (startNoon == 1) {
				leave.setFdEndNoon(2);
			} else {
				tmpEndTime = AttendUtil.getDate(startTime, 0);
				leave.setFdEndNoon(1);
			}
			leave.setFdBusEndTime(tmpEndTime);
			if (leave.getFdStartNoon() == null) {
				leave.setFdStartNoon(1);
			}
			dateMap.put("startTime", startTime);
			dateMap.put("endTime", fdBusEndTime);
			dateMap.put("startNoon", startNoon);
			dateMap.put("endNoon", leaveEndNoon);
			result = true;
		} else if (_fdBusEndTime.getTime() <= _endTime.getTime()
				&& _fdBusStartTime.getTime() >= _startTime.getTime()) {
			leave.setFdDelFlag(1);
			dateMap.put("startTime", fdBusStartTime);
			dateMap.put("endTime", fdBusEndTime);
			dateMap.put("startNoon", leave.getFdStartNoon());
			dateMap.put("endNoon", leave.getFdEndNoon());
			result = true;
		} else if (_fdBusEndTime.getTime() > _endTime.getTime()
				&& _fdBusStartTime.getTime() <= _endTime.getTime()) {
			//原请假的结束时间大于销假的结束时间，并且原开始时间，小于等于本次销假的结束时间
			Date tmpEndTime = null;
			Integer leaveStartNoon = leave.getFdStartNoon();
			if (endNoon == 1) {
				//本次销假结束是从上午结束，则原请假单开始从下午开始
				tmpEndTime = AttendUtil.getDate(endTime, 0);
				leave.setFdStartNoon(2);
			} else {
				//本次销假结束是从下午开始，则原请假单从第二天的下午上午开始
				tmpEndTime = AttendUtil.getDate(endTime, 1);
				leave.setFdStartNoon(1);
			}
			leave.setFdBusStartTime(tmpEndTime);
			if (leave.getFdEndNoon() == null) {
				leave.setFdEndNoon(2);
			}
			Date _newStartTime = this.getStartTime(tmpEndTime, leave.getFdStartNoon());
			//新产生的开始时间，大于原来的结束时间。则删除
			if(_newStartTime.getTime() > _fdBusEndTime.getTime()){
				leave.setFdDelFlag(1);
			}
			dateMap.put("startTime", fdBusStartTime);
			dateMap.put("endTime", endTime);
			dateMap.put("startNoon", leaveStartNoon);
			dateMap.put("endNoon", endNoon);
			result = true;
		}
		Integer statDayType = (Integer) dateMap.get("statDayType");
		updateBusinessInfo(result,leave,person,statDayType);
		if (!dateMap.isEmpty()) {
			dateMap.put("statType", 2);
		}
		return result;
	}

	private Date getStartTime(Date startTime, Integer startNoon) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(startTime);
		if (startNoon != null && startNoon == 2) {
			ca.set(Calendar.HOUR_OF_DAY, 12);
		}
		return ca.getTime();
	}

	private Date getEndTime(Date endTime, Integer endNoon) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(endTime);
		if (endNoon != null && endNoon == 1) {
			ca.set(Calendar.HOUR_OF_DAY, 12);
		} else {
			ca.add(Calendar.DATE, 1);
		}
		return ca.getTime();
	}

	private SysAttendBusiness cloneLeaveBiz(SysAttendBusiness leave) {
		if (leave == null) {
			return null;
		}
		SysAttendBusiness newLeave = new SysAttendBusiness();
		newLeave.setFdId(IDGenerator.generateID());
		newLeave.setFdBusStartTime(leave.getFdBusStartTime());
		newLeave.setFdBusEndTime(leave.getFdBusEndTime());
		newLeave.setFdProcessId(leave.getFdProcessId());
		newLeave.setDocUrl(leave.getDocUrl());
		List<SysOrgElement> newTargets = new ArrayList<SysOrgElement>();
		newTargets.addAll(leave.getFdTargets());
		newLeave.setFdTargets(newTargets);
		newLeave.setFdType(leave.getFdType());
		newLeave.setFdBusType(leave.getFdBusType());
		newLeave.setFdStatType(leave.getFdStatType());
		newLeave.setFdStartNoon(leave.getFdStartNoon());
		newLeave.setFdEndNoon(leave.getFdEndNoon());
		newLeave.setDocCreateTime(leave.getDocCreateTime());
		newLeave.setFdProcessName(leave.getFdProcessName());
		newLeave.setFdLeaveName(leave.getFdLeaveName());
		newLeave.setFdBusDetailId(leave.getFdBusDetailId());
		return newLeave;
	}

	/**
	 * 获取请假数据
	 * 
	 * @param docCreatorId
	 * @param fdStartTime
	 * @param fdEndTime
	 * @param fdBusType
	 *            假期编号
	 * @return
	 */
	protected List getLeaveBiz(String docCreatorId, Date fdStartTime,
			Date fdEndTime, Integer fdBusType) {
		List recordList = new ArrayList();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setJoinBlock(
					"left join sysAttendBusiness.fdTargets target");
			StringBuffer whereBlock = new StringBuffer("1=1");
			whereBlock.append(
					" and target.fdId=:docCreatorId and (sysAttendBusiness.fdDelFlag=0 or sysAttendBusiness.fdDelFlag is null)");
			whereBlock.append(" and sysAttendBusiness.fdType=5");
			if (fdBusType != null) {
				whereBlock
						.append(" and sysAttendBusiness.fdBusType =:fdBusType");
				hqlInfo.setParameter("fdBusType", fdBusType);
			}
			// fdStatType为null或3时，fdBusStartTime和fdBusEndTime为时间
			// fdStatType为1或2时，fdBusStartTime和fdBusEndTime为日期
			whereBlock.append(
					" and ("
							+ "(sysAttendBusiness.fdStatType is null or sysAttendBusiness.fdStatType=3) and sysAttendBusiness.fdBusStartTime<:endTime and sysAttendBusiness.fdBusEndTime>:startTime");
			whereBlock.append(
					" or (sysAttendBusiness.fdStatType=1 or sysAttendBusiness.fdStatType=2) and sysAttendBusiness.fdBusStartTime<:endTime and sysAttendBusiness.fdBusEndTime>=:startTime"
							+ ")");
			hqlInfo.setParameter("docCreatorId", docCreatorId);
			hqlInfo.setParameter("startTime", fdStartTime);
			hqlInfo.setParameter("endTime", fdEndTime);
			hqlInfo.setWhereBlock(whereBlock.toString());
			hqlInfo.setOrderBy("sysAttendBusiness.docCreateTime desc");
			return getSysAttendBusinessService().findList(hqlInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取请假数据:" + e.getMessage(), e);
		}
		return recordList;
	}



	/**
	 * 更新有效考勤记录
	 * @param person 单个人为维度
	 * @param dateMap 实现销假时间区间
	 * @throws Exception
	 */
	private void updateSysAttendMain(SysOrgElement person, Map<String, Object> dateMap,SysAttendBusiness leave) throws Exception {
		List<Date> dateList = new ArrayList<Date>();
		Integer statType = (Integer) dateMap.get("statType");
		Date startTime = (Date) dateMap.get("startTime");
		Date endTime = (Date) dateMap.get("endTime");
		Integer fdStartNoon = null, fdEndNoon = null;
		if (Integer.valueOf(2).equals(statType)) {
			fdStartNoon = (Integer) dateMap.get("startNoon");
			fdEndNoon = (Integer) dateMap.get("endNoon");
		}
		dateList = getDateList(statType, startTime, endTime,
				fdStartNoon, fdEndNoon);
		if (CollectionUtils.isEmpty(dateList)) {
			return;
		}
		Set<Date> everyDay=new HashSet<>();
		// 每天
		for (int i = 0; i < dateList.size() - 1; i++) {
			if(i==0){
				//所有的都把昨天的都执行一下。防止跨天
				everyDay.add(AttendUtil.getDate(dateList.get(i), -1));
			}
			//按小时 直接取时间
			Date startDate =AttendUtil.getDate(dateList.get(i), 0);
			if(everyDay.contains(startDate)){
				continue;
			}
			everyDay.add(startDate);
			/*
			List<SysAttendMain> operateList= getSysAttendMainList(person.getFdId(),5,
					AttendUtil.getDate(startDate, 1),
					AttendUtil.getDate(startDate, 2),
					startDate,
					AttendUtil.getDate(startDate, 1),
					true
			);
			if(CollectionUtils.isNotEmpty(operateList)){
				//考勤记录中，如果
				for (int k = 0; k < operateList.size(); k++) {
					//删除所有的有效考勤记录。重新根据原始考勤记录生成
					SysAttendMain main = (SysAttendMain) operateList.get(k);
					if(leave !=null && main !=null
							&& main.getFdBusiness() !=null && leave.getFdId().equals(main.getFdBusiness().getFdId())) {
						//开始时间
						getSysAttendMainService().getBaseDao().delete(main);
					}
				}
			}*/
		}
		List<Date> totalDateList = new ArrayList<>();
		totalDateList.addAll(everyDay);
		dateMap.put("dateList",totalDateList);

	}

	protected List<SysAttendBusiness> getBusinessList(JSONObject params,
			IBaseModel mainModel) throws Exception {
		try {
			List<SysAttendBusiness> busList = new ArrayList<SysAttendBusiness>();
			IExtendDataModel model = (IExtendDataModel) mainModel;
			Map<String, Object> modelData = model.getExtendDataModelInfo()
					.getModelData();

			JSONObject targetsJson = JSONObject
					.fromObject(params.get("fdResumeTargets"));
			String targetsFieldName = (String) targetsJson.get("value");
			// 人员
			String targetIds = null;
			if (modelData.containsKey(targetsFieldName)) {
				Object targetsObj = modelData.get(targetsFieldName);
				targetIds = BeanUtils.getProperty(targetsObj, "id");
			} else {
				SysOrgElement org = (SysOrgElement) PropertyUtils
						.getProperty(mainModel, targetsFieldName);
				targetIds = org.getFdId();
			}

			if (StringUtil.isNull(targetIds)) {
				logger.error("获取销假流程中销假人员出错,targetIds为空");
				return null;
			}
			boolean isResumeTypeConfig = params.containsKey("fdResumeType")
					&& params.get("fdResumeType") instanceof JSONObject;
			if (isResumeTypeConfig) {

				JSONObject fdResumeTypeJson = JSONObject
						.fromObject(params.get("fdResumeType"));
				String fdResumeFeildName = (String) fdResumeTypeJson
						.get("value");
				String fdResumeFieldType = (String) fdResumeTypeJson
						.get("model");

				if ("String".equals(fdResumeFieldType)
						|| "Double".equals(fdResumeFieldType)) {// 不是明细表
					Object tmpFdResumeFType = getSysMetadataParser()
							.getFieldValue(mainModel, fdResumeFeildName, false);
					Integer resumeType = getCheckboxFieldValue(
							tmpFdResumeFType);
					addBizWithResumeType(targetIds, params, model, busList,
							resumeType);

				} else if ("String[]".equals(fdResumeFieldType)
						|| "Double[]".equals(fdResumeFieldType)) { // 明细表
					String detailName = fdResumeFeildName.split("\\.")[0];
					String resumeTypeName = fdResumeFeildName.split("\\.")[1];
					List<HashMap<String, Object>> detailList = (ArrayList<HashMap<String, Object>>) modelData
							.get(detailName);
					for (int k = 0; k < detailList.size(); k++) {
						HashMap detail = detailList.get(k);
						Integer leaveType = getCheckboxFieldValue(
								detail.get(resumeTypeName));
						params.put("detailName", detailName);
						params.put("detailIdx", k);
						addBizWithResumeType(targetIds, params, model, busList,
								leaveType);
					}
				} else {
					logger.error("获取销假数据出错：销假类型字段必须是单选框或下拉框");
				}
			} else {
				// 需要兼容旧配置数据
				addBizWithResumeType(targetIds, params, model, busList, null);
			}
			return busList;
		} catch (Exception e) {
			logger.error("获取销假数据出错:" + e.getMessage(), e);
			return null;
		}
	}

	private void addBizWithResumeType(String targetIds, JSONObject params,
			IBaseModel mainModel, List<SysAttendBusiness> busList,
			Integer resumeType) throws Exception {
		IExtendDataModel model = (IExtendDataModel) mainModel;
		Map<String, Object> modelData = model.getExtendDataModelInfo()
				.getModelData();
		if (resumeType != null) {
			SysTimeLeaveRule sysTimeLeaveRule = AttendUtil
					.getLeaveRuleByType(resumeType);
			if (sysTimeLeaveRule != null
					&& Boolean.TRUE.equals(sysTimeLeaveRule.getFdIsAvailable())) {
				String fdLeaveName = sysTimeLeaveRule.getFdName();
				addAttendBusiness(targetIds, params, model, busList, resumeType,
						fdLeaveName, sysTimeLeaveRule.getFdStatType());
			} else {
				logger.error("无法找到该销假类型,resumeType:" + resumeType
						+ ",mainModel:" + mainModel.getFdId());
			}
		} else {// 没有填销假类型
			addAttendBusiness(targetIds, params, model, busList, null, null,
					null);
		}

	}


	/**
	 * 获取销假时间配置
	 * 
	 * @param params
	 * @param mainModel
	 * @return
	 * @throws Exception
	 */
	private Map getXFormBussConfigTime(JSONObject params,
			IBaseModel mainModel, Integer fdLeaveStatType) throws Exception {
		IExtendDataModel model = (IExtendDataModel) mainModel;
		Map<String, Object> modelData = model.getExtendDataModelInfo()
				.getModelData();
		Map<String, Object> result = new HashMap<String, Object>();
		Date startTime = null, endTime = null;
		String startPropName = null, endPropName = null;
		JSONObject startJson = null, endJson = null;
		String startNoonPropName = null, endNoonPropName = null;
		Integer startNoon = null, endNoon = null;

		boolean isDayConfig = params.containsKey("day_startDate")
				&& params.get("day_startDate") instanceof JSONObject &&
				params.containsKey("day_endDate")
				&& params.get("day_endDate") instanceof JSONObject;
		boolean isHalfConfig = params.containsKey("half_startDate")
				&& params.get("half_startDate") instanceof JSONObject
				&& params.containsKey("half_endDate")
				&& params.get("half_endDate") instanceof JSONObject;
		boolean isHourConfig = params.containsKey("hour_startTime")
				&& params.get("hour_startTime") instanceof JSONObject &&
				params.containsKey("hour_endTime")
				&& params.get("hour_endTime") instanceof JSONObject;
		boolean isStartNoonConfig = fdLeaveStatType == 2
				&& params.containsKey("half_startNoon")
				&& params.get("half_startNoon") instanceof JSONObject;
		boolean isEndNoonConfig = fdLeaveStatType == 2
				&& params.containsKey("half_endNoon")
				&& params.get("half_endNoon") instanceof JSONObject;
		if (Integer.valueOf(1).equals(fdLeaveStatType) && isDayConfig) {
			startJson = JSONObject
					.fromObject(params.get("day_startDate"));
			endJson = JSONObject
					.fromObject(params.get("day_endDate"));
		} else if (Integer.valueOf(2).equals(fdLeaveStatType) && isHalfConfig) {
			startJson = JSONObject
					.fromObject(params.get("half_startDate"));
			endJson = JSONObject
					.fromObject(params.get("half_endDate"));
			// 上下午标识
			JSONObject startNoonJson = null, endNoonJson = null;
			if (isStartNoonConfig) {
				startNoonJson = JSONObject
						.fromObject(params.get("half_startNoon"));
				startNoonPropName = (String) startNoonJson.get("value");
			}
			if (isEndNoonConfig) {
				endNoonJson = JSONObject.fromObject(params.get("half_endNoon"));
				endNoonPropName = (String) endNoonJson.get("value");
			}

		} else if (Integer.valueOf(3).equals(fdLeaveStatType) && isHourConfig) {
			startJson = JSONObject
					.fromObject(params.get("hour_startTime"));
			endJson = JSONObject
					.fromObject(params.get("hour_endTime"));
		} else if (fdLeaveStatType == null) {
			// 兼容历史配置(按天销假)
			JSONObject startTimeJson = JSONObject
					.fromObject(params.get("fdResumeStartDate"));
			JSONObject endTimeJson = JSONObject
					.fromObject(params.get("fdResumeEndDate"));
			String startFieldName = (String) startTimeJson.get("value");
			String endFieldName = (String) endTimeJson.get("value");
			// 是否明细表
			boolean isStartList = startFieldName.indexOf(".") >= 0;
			boolean isEndList = endFieldName.indexOf(".") >= 0;
			if (isStartList && isEndList) {
				String detailName = startFieldName.split("\\.")[0];
				String startName = startFieldName.split("\\.")[1];
				String endName = endFieldName.split("\\.")[1];
				List<HashMap<String, Object>> detailList = (ArrayList<HashMap<String, Object>>) modelData
						.get(detailName);
				for (int k = 0; k < detailList.size(); k++) {
					HashMap detail = detailList.get(k);
					// 开始日期
					startTime = (Date) detail.get(startName);
					// 结束日期
					endTime = (Date) detail.get(endName);
				}
			} else if (!isStartList && !isEndList) {
				// 开始日期
				startTime = (Date) getSysMetadataParser()
						.getFieldValue(mainModel, startFieldName, false);
				// 结束日期
				endTime = (Date) getSysMetadataParser().getFieldValue(mainModel,
						endFieldName, false);
			}
			if (startTime == null || endTime == null
					|| startTime.getTime() > endTime.getTime()) {
				logger.warn("销假流程中的销假开始与结束时间从文档中获取对应值不准确,startTime:" + startTime
						+ ";endTime:" + endTime);
				return result;
			}
			startTime = AttendUtil.getDate(startTime, 0);
			endTime = AttendUtil.getDate(endTime, 0);
			result.put("startTime", startTime);
			result.put("endTime", endTime);
			result.put("startNoon", startNoon);
			result.put("endNoon", endNoon);
			return result;
		} else {
			logger.warn("无法根据销假类型找到对应的销假方式,请查看配置是否准确!");
			return result;
		}

		if (startJson != null && endJson != null) {
			startPropName = (String) startJson.get("value");
			endPropName = (String) endJson.get("value");
		}
		if (StringUtil.isNull(startPropName)
				|| StringUtil.isNull(endPropName)) {
			logger.warn("无法获取销假流程中的销假开始与结束时间,请查看配置是否准确!");
			return result;
		}

		if (startPropName.indexOf(".") == -1
				&& endPropName.indexOf(".") == -1) {// 是否明细表
			// 开始日期，结束日期
			startTime = (Date) getSysMetadataParser()
					.getFieldValue(mainModel, startPropName, false);
			endTime = (Date) getSysMetadataParser().getFieldValue(mainModel,
					endPropName, false);
			// 开始上下午标识
			if (isStartNoonConfig) {
				Object obj = getSysMetadataParser().getFieldValue(mainModel,
						startNoonPropName, false);
				startNoon = getCheckboxFieldValue(obj);
			}
			// 结束上下午标识
			if (isEndNoonConfig) {
				Object obj = getSysMetadataParser().getFieldValue(mainModel,
						endNoonPropName, false);
				endNoon = getCheckboxFieldValue(obj);
			}
		} else {// 明细表
			String detailName = startPropName.split("\\.")[0];
			String leaveTypeDetailName = (String) params
					.get("detailName");
			Integer detailIdx = (Integer) params.get("detailIdx");
			if (detailName.equals(leaveTypeDetailName)
					&& detailIdx != null) {
				List<HashMap<String, Object>> detailList = (ArrayList<HashMap<String, Object>>) modelData
						.get(detailName);
				HashMap detail = detailList.get(detailIdx);
				// 开始日期结束日期
				startTime = (Date) detail
						.get(startPropName.split("\\.")[1]);
				endTime = (Date) detail.get(endPropName.split("\\.")[1]);
				// 开始上下午标识
				if (isStartNoonConfig) {
					Object obj = detail.get(startNoonPropName.split("\\.")[1]);
					startNoon = getCheckboxFieldValue(obj);
				}
				// 结束上下午标识
				if (isEndNoonConfig) {
					Object obj = detail.get(endNoonPropName.split("\\.")[1]);
					endNoon = getCheckboxFieldValue(obj);
				}
			}
		}
		if (startTime == null || endTime == null
				|| startTime.getTime() > endTime.getTime()) {
			logger.warn("销假流程中的销假开始与结束时间从文档中获取对应值不准确,startTime:" + startTime
					+ ";endTime:" + endTime);
			return result;
		}
		if (startNoon == null || endNoon == null) {
			startNoon = null;
			endNoon = null;
		}
		result.put("startTime", startTime);
		result.put("endTime", endTime);
		result.put("startNoon", startNoon);
		result.put("endNoon", endNoon);
		return result;
	}

	/**
	 * @param targetIds
	 * @param params
	 * @param mainModel
	 * @param busList
	 * @param resumeType
	 * @param resumeName
	 * @param fdLeaveStatType
	 *            销假类型绑定的时间单位(天,半天,小时)
	 * @throws Exception
	 */
	private void addAttendBusiness(String targetIds, JSONObject params,
			IBaseModel mainModel, List<SysAttendBusiness> busList,
			Integer resumeType, String resumeName, Integer fdLeaveStatType)
			throws Exception {

		IExtendDataModel model = (IExtendDataModel) mainModel;
		Map<String, Object> modelData = model.getExtendDataModelInfo()
				.getModelData();
		String docSubject = (String) getSysMetadataParser()
				.getFieldValue(mainModel, "docSubject", false);

		Map map = getXFormBussConfigTime(params, mainModel, fdLeaveStatType);
		if (map.isEmpty()) {
			return;
		}
		Date startTime = (Date) map.get("startTime");
		Date endTime = (Date) map.get("endTime");
		Integer startNoon = (Integer) (map.containsKey("startNoon")
				? map.get("startNoon") : null);
		Integer endNoon = (Integer) (map.containsKey("endNoon")
				? map.get("endNoon") : null);

		busList.add(getBusinessModel(startTime, endTime, targetIds,
				mainModel.getFdId(), resumeType, null, startNoon, endNoon,
				resumeName, docSubject, model));
	}

	private SysAttendBusiness getBusinessModel(Date fdBusStartTime,
			Date fdBusEndTime, String targetIds, String fdProcessId,
			Integer fdBusType, Integer fdStatType, Integer fdStartNoon,
			Integer fdEndNoon, String fdLeaveName, String fdProcessName, IBaseModel model)
			throws Exception {
		SysAttendBusiness sysAttendBusiness = new SysAttendBusiness();
		sysAttendBusiness.setFdId(IDGenerator.generateID());
		sysAttendBusiness.setFdBusStartTime(fdBusStartTime);
		sysAttendBusiness.setFdBusEndTime(fdBusEndTime);
		sysAttendBusiness.setFdProcessId(fdProcessId);
		if (StringUtil.isNotNull(fdProcessId)) {
			sysAttendBusiness.setDocUrl(AttendUtil.getDictUrl(model,fdProcessId));
		}
		sysAttendBusiness.setFdTargets(getSysOrgCoreService()
				.findByPrimaryKeys(targetIds.split(";")));
		sysAttendBusiness.setFdType(8);
		sysAttendBusiness.setFdBusType(fdBusType);
		sysAttendBusiness.setFdStatType(fdStatType);
		sysAttendBusiness.setFdStartNoon(fdStartNoon);
		sysAttendBusiness.setFdEndNoon(fdEndNoon);
		sysAttendBusiness.setDocCreateTime(new Date());
		sysAttendBusiness.setFdLeaveName(fdLeaveName);
		sysAttendBusiness.setFdProcessName(fdProcessName);
		return sysAttendBusiness;
	}

	private Integer getCheckboxFieldValue(Object value) {
		if (value != null) {
			try {
				if (value instanceof String) {
					if("am".equalsIgnoreCase((String) value)) {
						value="1";
					}
					if("pm".equalsIgnoreCase((String) value)) {
						value="2";
					}
					return Integer.parseInt((String) value);
				} else if (value instanceof Number) {
					return ((Number) value).intValue();
				} else {
					return null;
				}
			} catch (Exception e) {
				logger.error("销假类型字段获取数据出错:" + value);
			}
		}
		return null;
	}

	/**
	 * 获取班次信息
	 * 
	 * @param category
	 * @param date
	 * @param org
	 * @return
	 * @throws Exception
	 */
	private List<Map<String, Object>> getSignTimeList(
			SysAttendCategory category, Date date, SysOrgElement org)
			throws Exception {
		List<Map<String, Object>> signTimeList = new ArrayList<Map<String, Object>>();
		if (category == null || date == null || org == null) {
			return signTimeList;
		}

		Date signDate = AttendUtil.getDate(date, 0);
		signTimeList = getSysAttendCategoryService().getAttendSignTimes(category,
				signDate, org);

		if (signTimeList.isEmpty()
				&& Integer.valueOf(1).equals(category.getFdShiftType())) {
			for (int i = 0; i < 30; i++) {// 尝试获取最近一次的班次信息
				signTimeList = getSysAttendCategoryService()
						.getAttendSignTimes(category,
								AttendUtil.getDate(signDate, 1), org);
				if (!signTimeList.isEmpty()) {
					return signTimeList;
				}
			}
		}
		return signTimeList;
	}

	/**
	 * 获取正常的签到点
	 * 
	 * @param main
	 * @param signTimeList
	 * @return
	 */
	private Date getNormalSignTime(SysAttendMain main,
			List<Map<String, Object>> signTimeList) {
		Date normalSignTime = null;

		Integer fdWorkType = main.getFdWorkType();
		Timestamp fdSignedTime = new Timestamp(
				main.getDocCreateTime().getTime());

		int fdSignedTimeMins = AttendUtil.getHMinutes(fdSignedTime);
		int workCount = signTimeList.size() / 2;
		for (int i = 0; i < workCount; i++) {
			Map<String, Object> startMap = signTimeList.get(2 * i); // 上班
			Map<String, Object> endMap = signTimeList.get(2 * i + 1); // 下班
			Date startSignTime = (Date) startMap.get("signTime");
			Date endSignTime = (Date) endMap.get("signTime");

			if (Integer.valueOf(0).equals(fdWorkType)) {
				if (fdSignedTimeMins < AttendUtil
						.getHMinutes(endSignTime)) {
					normalSignTime = startSignTime;
				}
			} else {
				if ((i + 1) < workCount) {// 是否存在下班次
					Map<String, Object> nStartMap = signTimeList
							.get(2 * (i + 1));
					Date nStartSignTime = (Date) nStartMap.get("signTime");
					if (fdSignedTimeMins > AttendUtil
							.getHMinutes(startSignTime)
							&& fdSignedTimeMins < AttendUtil.getHMinutes(
									nStartSignTime)) {
						normalSignTime = endSignTime;
						break;
					}
				} else {
					if (fdSignedTimeMins > AttendUtil.getHMinutes(
							startSignTime)) {
						normalSignTime = endSignTime;
						break;
					}
				}
			}
		}

		return normalSignTime;
	}

	private void restat(List<SysAttendBusiness> busList) {
		try {
			reStatistics(busList,multicaster);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("销假重新统计出错" + e.getMessage(), e);
		}
	}

	private List<SysOrgElement> getStatOrgList(List<SysAttendBusiness> busList)
			throws Exception {
		List<SysOrgElement> statOrgs = new ArrayList<SysOrgElement>();
		Set<SysOrgElement> orgs = new HashSet<SysOrgElement>();
		for (SysAttendBusiness bus : busList) {
			List<SysOrgElement> targets = bus.getFdTargets();
			if (targets != null && !targets.isEmpty()) {
				List personList = getSysOrgCoreService().expandToPerson(targets);
				orgs.addAll(personList);
			}
		}
		statOrgs.addAll(orgs);
		return statOrgs;
	}

	private List<Date> getStatDateList(List<SysAttendBusiness> busList)
			throws Exception {
		List<Date> statDates = new ArrayList<Date>();
		for (SysAttendBusiness bus : busList) {
			Date startTime = AttendUtil.getDate(bus.getFdBusStartTime(), 0);
			Date endTime = AttendUtil.getDate(bus.getFdBusEndTime(), 0);
			Calendar cal = Calendar.getInstance();
			for (cal.setTime(startTime); cal.getTime()
					.compareTo(endTime) <= 0; cal.add(Calendar.DATE, 1)) {
				statDates.add(cal.getTime());
			}
		}
		return statDates;
	}
}
