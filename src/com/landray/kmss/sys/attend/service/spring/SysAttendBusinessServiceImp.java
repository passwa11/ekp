package com.landray.kmss.sys.attend.service.spring;


import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.service.ISysAttendBusinessService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendHisCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendStatJobService;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.authorization.model.SysAuthDefaultArea;
import com.landray.kmss.sys.authorization.service.ISysAuthDefaultAreaService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmount;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountItemService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveDetailService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SysAttendBusinessServiceImp extends BaseServiceImp
		implements ISysAttendBusinessService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendBusinessServiceImp.class);

	private ISysAttendCategoryService sysAttendCategoryService;

	private ISysAttendHisCategoryService sysAttendHisCategoryService;
	private ISysAttendHisCategoryService getSysAttendHisCategoryService(){
		if(sysAttendHisCategoryService ==null){
			sysAttendHisCategoryService = (ISysAttendHisCategoryService) SpringBeanUtil.getBean("sysAttendHisCategoryService");
		}
		return sysAttendHisCategoryService;
	}

	/**
	 * 根据流程ID 查询考勤的业务表数据
	 * @param processId 流程ID
	 * @return 返回考勤流程业务存储的数据
	 * @throws Exception
	 */
	@Override
	public List<SysAttendBusiness> findBusinessByProcessId(String processId,Integer overFlag)
			throws Exception {
		if(StringUtil.isNull(processId)) {
			return null;
		}
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer whereBlock = new StringBuffer("1=1 ");
		whereBlock.append(" and sysAttendBusiness.fdProcessId=:processId and sysAttendBusiness.fdOverFlag=:fdOverFlag ");
		if(AttendConstant.ATTEND_PROCESS_STATUS[0].equals(overFlag)){
			//如果查询是未完成的流程时。
			whereBlock.append(" and sysAttendBusiness.fdDelFlag=:fdDelFlag");
			hqlInfo.setParameter("fdDelFlag", AttendConstant.ATTEND_PROCESS_BUSINESS_DEL_FLAG[1]);
		} else {
			//其他查询完成标识。
			whereBlock.append(" and (sysAttendBusiness.fdDelFlag=:fdDelFlag or sysAttendBusiness.fdDelFlag is null)");
			hqlInfo.setParameter("fdDelFlag", AttendConstant.ATTEND_PROCESS_BUSINESS_DEL_FLAG[0]);
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setParameter("processId", processId);
		hqlInfo.setParameter("fdOverFlag", overFlag);
		return findList(hqlInfo);
	}

	@Override
	public List<SysAttendBusiness> findByProcessId(String processId)
			throws Exception {
		if(StringUtil.isNull(processId)) {
			return null;
		}
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer whereBlock = new StringBuffer("1=1 ");
		whereBlock.append(
				" and sysAttendBusiness.fdProcessId=:processId and (sysAttendBusiness.fdDelFlag=0 or sysAttendBusiness.fdDelFlag is null)");
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setParameter("processId", processId);
		List<SysAttendBusiness> list = findList(hqlInfo);
		return list;
	}

	@Override
	public List<SysAttendBusiness> findList(List orgIdList, Date date,
			Integer fdType)
			throws Exception {
		if (date == null) {
			date = new Date();
		}
		date = AttendUtil.getDate(date, 0);

		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer whereBlock = new StringBuffer("");
		whereBlock.append(
				"sysAttendBusiness.fdType=:fdType and (sysAttendBusiness.fdDelFlag=0 or sysAttendBusiness.fdDelFlag is null)");
		whereBlock.append(" and sysAttendBusiness.fdBusEndTime>=:fdStartTime");
		if (orgIdList != null && !orgIdList.isEmpty()) {
			whereBlock.append(" and " + HQLUtil.buildLogicIN(
					"sysAttendBusiness.fdTargets.fdId", orgIdList));
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setParameter("fdStartTime", date);
		hqlInfo.setParameter("fdType", fdType);
		hqlInfo.setOrderBy("sysAttendBusiness.docCreateTime asc");
		List<SysAttendBusiness> list = findList(hqlInfo);
		return list;
	}

	@Override
	public List<SysAttendBusiness> findLeaveList(List orgIdList, Date startTime,
			Date endTime) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock(
				"left join sysAttendBusiness.fdTargets target");
		StringBuffer whereBlock = new StringBuffer("1=1");
		whereBlock.append(" and "
				+ HQLUtil.buildLogicIN("target.fdId", orgIdList));
		whereBlock.append(
				" and sysAttendBusiness.fdType=5 and (sysAttendBusiness.fdDelFlag=0 or sysAttendBusiness.fdDelFlag is null) ");
		// fdStatType为null或3时，fdBusStartTime和fdBusEndTime为时间
		// fdStatType为1或2时，fdBusStartTime和fdBusEndTime为日期
		whereBlock.append(
				" and ("
						+ "(sysAttendBusiness.fdStatType is null or sysAttendBusiness.fdStatType=3) and sysAttendBusiness.fdBusStartTime<:endTime and sysAttendBusiness.fdBusEndTime>:startTime");
		whereBlock.append(
				" or (sysAttendBusiness.fdStatType=1 or sysAttendBusiness.fdStatType=2) and sysAttendBusiness.fdBusStartTime<:endTime and sysAttendBusiness.fdBusEndTime>=:startTime"
						+ ")");
		hqlInfo.setParameter("startTime", startTime);
		hqlInfo.setParameter("endTime", endTime);
		hqlInfo.setWhereBlock(whereBlock.toString());
		List<SysAttendBusiness> list = findList(hqlInfo);
		return list;
	}

	/**
	 * 根据人员和时间以及类型 查询出对应的流程数据，不包含在进行中的
	 * @param orgIdList
	 *            人员ID列表
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间(注:不包含该时间点)
	 * @param fdTypes
	 *            类型 出差,请假,外出等
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SysAttendBusiness> findBussList(List orgIdList, Date startTime,
			Date endTime, List fdTypes) throws Exception {
		return findBussList(orgIdList,startTime,endTime,fdTypes,false);
	}

	/**
	 * 根据人员和时间以及类型 查询出对应的流程数据。包含在途的
	 * @param orgIdList
	 *            人员ID列表
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间(注:不包含该时间点)
	 * @param fdTypes
	 *            类型 出差,请假,外出等
	 * @param isHaveIng 是否包含在流程中的
	 * @return
	 * @throws Exception
	 */
//	@Override
//	public List<SysAttendBusiness> findBussList(List orgIdList, Date startTime,
//												Date endTime, List fdTypes,boolean isHaveIng) throws Exception {
//		HQLInfo hqlInfo = new HQLInfo();
////		hqlInfo.setJoinBlock( "left join sysAttendBusiness.fdTargets target");
//		StringBuffer whereBlock = new StringBuffer("1=1");
//		whereBlock.append(" and " + HQLUtil.buildLogicIN("sysAttendBusiness.fdTargets.fdId", orgIdList));
//		// 类型
//		if (fdTypes != null && !fdTypes.isEmpty()) {
//			whereBlock.append(" and " + HQLUtil.buildLogicIN("sysAttendBusiness.fdType", fdTypes));
//		}
//		if(isHaveIng) {
//			whereBlock.append(" and (sysAttendBusiness.fdDelFlag=0 or sysAttendBusiness.fdDelFlag is null or sysAttendBusiness.fdOverFlag =0) ");
//		}else{
//			whereBlock.append(" and (sysAttendBusiness.fdDelFlag=0 or sysAttendBusiness.fdDelFlag is null) ");
//		}
//		String whereOne =whereBlock.toString() +" and sysAttendBusiness.fdBusStartTime<:endTime and sysAttendBusiness.fdBusEndTime>:startTime ";
//		hqlInfo.setParameter("startTime", startTime);
//		hqlInfo.setParameter("endTime", endTime);
////		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
//		hqlInfo.setWhereBlock(whereOne);
//		/***
//		 * 拆除2条语句查询
//		 */
//		return findList(hqlInfo);
//
//	}
	/**
	 * 根据人员和时间以及类型 查询出对应的流程数据。包含在途的
	 * @param orgIdList
	 *            人员ID列表
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间(注:不包含该时间点)
	 * @param fdTypes
	 *            类型 出差,请假,外出等
	 * @param isHaveIng 是否包含在流程中的
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SysAttendBusiness> findBussList(List orgIdList, Date startTime,
												Date endTime, List fdTypes,boolean isHaveIng) throws Exception {
		List<SysAttendBusiness> resultList = new ArrayList<>();
		List<List> groupLists = new ArrayList<List>();
		// 用户组分割
		int maxCount = 100;
		if (orgIdList.size() <= maxCount) {
			groupLists.add(orgIdList);
		} else {
			groupLists = AttendUtil.splitList(orgIdList, maxCount);
		}
		//重新生成，删除未处理的缺卡的待办通知。然后删除有效考勤
		for (int i = 0; i < groupLists.size(); i++) {
			TransactionStatus status = null;
			boolean isException = false;
			List searchList = groupLists.get(i);
			//拆分用户数量
			try {
				status = TransactionUtils.beginNewReadTransaction();
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setJoinBlock("left join sysAttendBusiness.fdTargets target");
				StringBuffer whereBlock = new StringBuffer("1=1");
				whereBlock.append(" and " + HQLUtil.buildLogicIN("target.fdId", searchList));
				// 类型
				if (fdTypes != null && !fdTypes.isEmpty()) {
					whereBlock.append(" and " + HQLUtil.buildLogicIN("sysAttendBusiness.fdType", fdTypes));
				}
				if (isHaveIng) {
					whereBlock.append(" and (sysAttendBusiness.fdDelFlag=0 or sysAttendBusiness.fdDelFlag is null or sysAttendBusiness.fdOverFlag =0) ");
				} else {
					whereBlock.append(" and (sysAttendBusiness.fdDelFlag=0 or sysAttendBusiness.fdDelFlag is null) ");
				}
				// fdStatType为null或3时，fdBusStartTime和fdBusEndTime为时间
				// fdStatType为1或2时，fdBusStartTime和fdBusEndTime为日期
				String whereOne = whereBlock.toString() + " and sysAttendBusiness.fdBusStartTime<:endTime and sysAttendBusiness.fdBusEndTime>:startTime and (sysAttendBusiness.fdStatType is null or sysAttendBusiness.fdStatType=3) ";
				String whereTwo = whereBlock.toString() + " and sysAttendBusiness.fdBusStartTime<:endTime and sysAttendBusiness.fdBusEndTime>=:startTime  and (sysAttendBusiness.fdStatType=1 or sysAttendBusiness.fdStatType=2)  ";

				hqlInfo.setParameter("startTime", startTime);
				hqlInfo.setParameter("endTime", endTime);
//			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
				hqlInfo.setWhereBlock(whereOne);

				List<SysAttendBusiness> list = findList(hqlInfo);
				if (CollectionUtils.isNotEmpty(list)) {
					resultList.addAll(list);
				}
				hqlInfo.setWhereBlock(whereTwo);
				List<SysAttendBusiness> listTwo = findList(hqlInfo);
				if (CollectionUtils.isNotEmpty(listTwo)) {
					resultList.addAll(listTwo);
				}
			} catch (Exception e) {
				isException = true;
				logger.error(e.getMessage());
			} finally {
				if (isException && status != null) {
					TransactionUtils.rollback(status);
				} else if (status != null) {
					TransactionUtils.commit(status);
				}
			}
		}
		return resultList;
	}

	/**
	 * 过滤当前用户列表某一时间段是否存在流程
	 * @param orgIdList
	 * @param startTime
	 * @param endTime
	 * @param fdTypes
	 * @param isHaveIng 是否正在进行中
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<String> findBussTargetList(List orgIdList, Date startTime,
												Date endTime, List fdTypes,boolean isHaveIng) throws Exception {
		List<String> resultList = new ArrayList<>();
		List<List> groupLists = new ArrayList<List>();
		// 用户组分割
		int maxCount = 100;
		if (orgIdList.size() <= maxCount) {
			groupLists.add(orgIdList);
		} else {
			groupLists = AttendUtil.splitList(orgIdList, maxCount);
		}
		//重新生成，删除未处理的缺卡的待办通知。然后删除有效考勤
		for (int i = 0; i < groupLists.size(); i++) {
			TransactionStatus status = null;
			boolean isException = false;
			List searchList = groupLists.get(i);
			//拆分用户数量
			try {
				status = TransactionUtils.beginNewReadTransaction();
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setJoinBlock("left join sysAttendBusiness.fdTargets target");
				StringBuffer whereBlock = new StringBuffer("1=1");
				whereBlock.append(" and " + HQLUtil.buildLogicIN("target.fdId", searchList));
				// 类型
				if (fdTypes != null && !fdTypes.isEmpty()) {
					whereBlock.append(" and " + HQLUtil.buildLogicIN("sysAttendBusiness.fdType", fdTypes));
				}
				if (isHaveIng) {
					whereBlock.append(" and (sysAttendBusiness.fdDelFlag=0 or sysAttendBusiness.fdDelFlag is null or sysAttendBusiness.fdOverFlag =0) ");
				} else {
					whereBlock.append(" and (sysAttendBusiness.fdDelFlag=0 or sysAttendBusiness.fdDelFlag is null) ");
				}
				whereBlock.append(" and sysAttendBusiness.fdBusStartTime< :endTime and sysAttendBusiness.fdBusEndTime> :startTime  ");

				hqlInfo.setParameter("startTime", startTime);
				hqlInfo.setParameter("endTime", endTime);
				hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
				hqlInfo.setWhereBlock(whereBlock.toString());
				hqlInfo.setSelectBlock("sysAttendBusiness.fdTargets.fdId");
				List<String> list = findValue(hqlInfo);
				if (CollectionUtils.isNotEmpty(list)) {
					resultList.addAll(list);
				}
			} catch (Exception e) {
				isException = true;
				logger.error("过滤当天的流程人出错：",e);
			} finally {
				if (isException && status != null) {
					TransactionUtils.rollback(status);
				} else if (status != null) {
					TransactionUtils.commit(status);
				}
			}
		}
		return resultList;
	}


	public void setSysAttendCategoryService(
			ISysAttendCategoryService sysAttendCategoryService) {
		this.sysAttendCategoryService = sysAttendCategoryService;
	}

	/**
	 * 获取有效外出流程信息
	 *
	 * @param docCreator
	 * @param busList
	 *            原始数据
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SysAttendBusiness> genUserBusiness(SysOrgElement docCreator,
												   Date date, List<SysAttendBusiness> busList) throws Exception {
		List<SysAttendBusiness> recordList = new ArrayList<SysAttendBusiness>();

		SysAttendCategory category =sysAttendCategoryService.getCategoryInfo(docCreator,date,true);
		if (category ==null) {
			// 没有考勤组
			logger.warn("用户:" + docCreator + "没有考勤组！");
			return new ArrayList<SysAttendBusiness>();
		}
		// 今天
		Date today = AttendUtil.addDate(date, 0);
		// 明天
		Date tomorrow = AttendUtil.addDate(date, 1);
		// 今天的排班信息
		List<Map<String, Object>> nowSigntimes = sysAttendCategoryService
				.getAttendSignTimes(docCreator, today);
		// 今天最近一天的排班信息
		List<Map<String, Object>> nowNextSigntimes = sysAttendCategoryService
				.getAttendSignTimes(category, today, docCreator, true);
		// 明天的排班信息
		List<Map<String, Object>> afterSigntimes = sysAttendCategoryService
				.getAttendSignTimes(docCreator, tomorrow);
		// 明天最近一天的排班信息
		List<Map<String, Object>> afterNextSigntimes = sysAttendCategoryService
				.getAttendSignTimes(category, tomorrow, docCreator, true);
		// 今天休息状态
		String nowRest = sysAttendCategoryService
				.getAttendCategory(docCreator, today);
		// 明天休息状态
		String afterRest = sysAttendCategoryService
				.getAttendCategory(docCreator, tomorrow);
		// 今天是否需要打卡
		boolean nowRestFlag = getRestType(nowSigntimes, nowRest);
		// 明天是否需要打卡
		boolean afterRestFlag = getRestType(afterSigntimes, afterRest);

		// 上班时间
		Date tempWorkTime = null;
		// 下班时间
		Date tempCloseTime = null;

		for (SysAttendBusiness bus : busList) {
			List<SysOrgElement> targets = bus.getFdTargets();
			if (targets != null && targets.contains(docCreator)) {
				Integer busType = bus.getFdType();
				// 流程开始时间
				Date busStartTime = bus.getFdBusStartTime();
				// 流程结束时间
				Date busEndTime = bus.getFdBusEndTime();
				// 外出
				if (AttendConstant.FD_ATTENDBUS_TYPE[7].equals(busType)) {
					tempWorkTime = AttendUtil.getDate(today, 0);
					tempCloseTime = AttendUtil.getDate(tomorrow, 0);
					// 今天不需要打卡
					if (!nowRestFlag) {
						if(!ArrayUtil.isEmpty(nowNextSigntimes)) {
							tempWorkTime = getWorkDate(nowNextSigntimes, 0,
									today);
							tempCloseTime = getWorkDate(nowNextSigntimes, 1,
									today);
						}
					} else {
						if(!ArrayUtil.isEmpty(nowSigntimes)) {
							// 上班时间
							tempWorkTime = getWorkDate(nowSigntimes, 0, today);
							// 下班时间
							tempCloseTime = getWorkDate(nowSigntimes, 1,
									today);
						}
					}
					// 外出的流程开始时间要在下班时间之前，流程结束时间要在上班时间之后
					if (busStartTime.before(tempCloseTime)
							&& busEndTime.after(tempWorkTime)) {
						recordList.add(bus);
						continue;
					}
					// 出差
				} else if (AttendConstant.FD_ATTENDBUS_TYPE[4]
						.equals(busType)) {
					// 上班时间
					tempWorkTime = null;
					// 下班时间
					tempCloseTime = null;

					// 出差如果开始时间00:00:00,结束时间23:59:59 是按天出差
					if (AttendUtil.getHMinutes(busStartTime) == 0
							&& (AttendUtil.getDate(busEndTime, 1).getTime()
									- busEndTime.getTime() <= 1000
									|| AttendUtil
											.getHMinutes(busEndTime) == 0)) {
						if (AttendUtil.getDate(busStartTime, 0)
								.before(AttendUtil.getDate(today, 1))
								&& AttendUtil.getDate(busEndTime, 1)
										.after(AttendUtil.getDate(today, 0))) {
							recordList.add(bus);
							continue;
						}
						// 按小时
					} else {
						tempCloseTime = AttendUtil.getDate(tomorrow, 0);
						tempWorkTime = AttendUtil.getDate(today, 0);
						// 今天需要打卡
						if (nowRestFlag) {
							if(!ArrayUtil.isEmpty(nowSigntimes)) {
								tempCloseTime = getWorkDate(nowSigntimes, 1, today);
								tempWorkTime = getWorkDate(nowSigntimes, 0, today);
							}
						} else {
							if(!ArrayUtil.isEmpty(nowNextSigntimes)) {
								// 今天休息日 则依照最近一次班次信息 否则算开始时间是今天的
								if (nowNextSigntimes != null) {
									tempCloseTime = getWorkDate(nowNextSigntimes, 1,
											today);
									tempWorkTime = getWorkDate(nowNextSigntimes, 0,
											today);
								}
							}
						}
						// 流程开始时间在结束时间之前
						if (busStartTime.before(tempCloseTime)
								&& busEndTime.after(tempWorkTime)) {
							recordList.add(bus);
							continue;
						}
					}
					// 请假
				} else if (AttendConstant.FD_ATTENDBUS_TYPE[5]
						.equals(busType)) {
					Integer fdStatType = bus.getFdStatType();
					// 按天，半天请假
					if (AttendConstant.FD_STAT_TYPE[1].equals(fdStatType)||AttendConstant.FD_STAT_TYPE[2]
							.equals(fdStatType)) {

						if (AttendUtil.getDate(busStartTime, 0)
								.before(AttendUtil.getDate(today, 1))
								&& AttendUtil.getDate(busEndTime, 1)
										.after(AttendUtil.getDate(today, 0))) {
							recordList.add(bus);
							continue;
						}
						// 按小时
					} else if (AttendConstant.FD_STAT_TYPE[3]
							.equals(fdStatType)) {
						tempCloseTime = AttendUtil.getDate(tomorrow, 0);
						tempWorkTime = AttendUtil.getDate(today, 0);
						// 今天需要打卡
						if (nowRestFlag) {
							if(!ArrayUtil.isEmpty(nowSigntimes)) {
								tempCloseTime = getWorkDate(nowSigntimes, 1, today);
								tempWorkTime = getWorkDate(nowSigntimes, 0, today);
							}
							// 流程开始时间在结束时间之前
						} else {
							if(!ArrayUtil.isEmpty(nowNextSigntimes)) {
								if (nowNextSigntimes != null) {
									tempCloseTime = getWorkDate(nowNextSigntimes, 1,
											today);
									tempWorkTime = getWorkDate(nowNextSigntimes, 0,
											today);
								}
							}
						}
						if (busStartTime.before(tempCloseTime)
								&& busEndTime.after(tempWorkTime)) {
							recordList.add(bus);
							continue;
						}
					}
					// 加班
				} else if (AttendConstant.FD_ATTENDBUS_TYPE[6]
						.equals(busType)) {
					tempCloseTime = AttendUtil.getDate(tomorrow, 0);
					tempWorkTime = AttendUtil.getDate(today, 0);
					if(logger.isDebugEnabled()){
						logger.debug("过滤加班流程：日期{},开始日期{},结束日期{}，今日是否需要打卡{},明日是否需要打卡{}",today,tempWorkTime,tempCloseTime,nowRestFlag,afterRestFlag);
					}
					if (nowRestFlag) {
						//今日是上班日 取今天的最早打卡时间。
						if(!ArrayUtil.isEmpty(nowSigntimes)) {
							tempWorkTime = getWorkDate(nowSigntimes, 2, today);
						}
					} else {
						//否则取今天最近一次排班的最早打卡时间。
						if(!ArrayUtil.isEmpty(nowNextSigntimes)) {
							tempWorkTime = getWorkDate(nowNextSigntimes, 2,
									today);
						}
					}
					//明天是否上班
					if (afterRestFlag) {
						//明天上班。则取明日的最早打卡时间
						if(!ArrayUtil.isEmpty(afterSigntimes)) {
							tempCloseTime = getWorkDate(afterSigntimes, 2,tomorrow);
						}
					} else {
						//明天休息，则取明天最近一次排班的最早打卡时间，为今日的最晚时间
						if(!ArrayUtil.isEmpty(afterNextSigntimes)) {
							tempCloseTime = getWorkDate(afterNextSigntimes, 2, tomorrow);
						}
					}
					if(logger.isDebugEnabled()){
						logger.debug("过滤加班流程：今日班次信息:{}",nowSigntimes);
						logger.debug("过滤加班流程：今日最近班次信息:{}",nowNextSigntimes);
						logger.debug("过滤加班流程：明日班次信息:{}",afterSigntimes);
						logger.debug("过滤加班流程：明日最近班次信息:{}",afterNextSigntimes);
						logger.debug("过滤加班流程：最终范围:{}-{}",tempWorkTime,tempCloseTime);
						logger.debug("过滤加班流程：最终结果:{}",busStartTime.before(tempCloseTime)
								&& busEndTime.before(tempCloseTime)
								&& busEndTime.after(tempWorkTime));
					}
					// 流程开始时间和结束时间都需要在明天最早打卡时间之前
					if (busStartTime.before(tempCloseTime)
							&& busEndTime.before(tempCloseTime)
							&& busEndTime.after(tempWorkTime)) {
						recordList.add(bus);
						continue;
					}

				}
			}
		}
		if(logger.isDebugEnabled()) {
			logger.debug("过滤加班流程：计算流程:{}", recordList);
		}
		return recordList;
	}

	/**
	 * 获取跨天排班状态
	 *
	 * @param signtimes
	 * @return
	 * @throws Exception
	 */
	private boolean getAcrossType(List<Map<String, Object>> signtimes)
			throws Exception {
		boolean rtn = false;
		if (signtimes != null && !signtimes.isEmpty()) {
			Map<String, Object> closeMap = signtimes
					.get(signtimes.size() - 1);
			rtn = AttendConstant.FD_OVERTIME_TYPE[2]
					.equals((Integer) PropertyUtils.getProperty(closeMap,
							"overTimeType"));
		}

		return rtn;
	}

	/**
	 * 获取当前是否需要打卡
	 *
	 * @param signtimes
	 * @param restStr
	 * @return
	 */
	private boolean getRestType(List<Map<String, Object>> signtimes,
			String restStr) {
		boolean rtn = false;
		if (signtimes != null && !signtimes.isEmpty()
				&& StringUtil.isNotNull(restStr)) {
			rtn = true;
		}
		return rtn;
	}

	/**
	 * 获取班次时间
	 *
	 * @param signtimes
	 * @param type
	 *            0 上班 1下班2最早打卡 3最晚打卡
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private Date getWorkDate(List<Map<String, Object>> signtimes, int type,
			Date date) throws Exception {
		Date rtn = null;
		if(ArrayUtil.isEmpty(signtimes)){
			return rtn;
		}
		Map<String, Object> workMap = signtimes.get(0);
		Map<String, Object> closeMap = signtimes
				.get(signtimes.size() - 1);
		boolean isAcross = getAcrossType(signtimes);

		switch (type) {
		case 0:
			rtn = (Date) workMap.get("signTime");
			rtn = AttendUtil.joinYMDandHMS(date, rtn);
			// 非跨天为当天00：00
			if (!isAcross) {
				rtn = AttendUtil.getDate(date, 0);
			}
			break;

		case 1:
			rtn = (Date) closeMap.get("signTime");
			Date temp = AttendUtil.addDate(date,
					isAcross ? 1 : 0);
			rtn = AttendUtil.joinYMDandHMS(temp, rtn);
			// 非跨天排班 结束时间为当天24：00
			if (!isAcross) {
				rtn = AttendUtil.getDate(date, 1);
			}
			break;
		case 2:
			rtn = (Date) workMap.get("fdStartTime");
			rtn = AttendUtil.joinYMDandHMS(date, rtn);
			break;
		case 3:
			rtn = (Date) closeMap.get("fdEndTime");
			Boolean isAcrossDay=(Boolean)workMap.get("isAcrossDay");
			Date tempDate = AttendUtil.addDate(date,
					Boolean.TRUE.equals(isAcrossDay) ? 1 : 0);
			rtn = AttendUtil.joinYMDandHMS(tempDate, rtn);
			break;
		}

		return rtn;
	}

	private ISysAttendStatJobService sysAttendStatJobService;
	private ISysTimeLeaveDetailService sysTimeLeaveDetailService;
	private ISysTimeLeaveRuleService sysTimeLeaveRuleService;
	private ISysOrgCoreService sysOrgCoreService;
	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
					.getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}
	public ISysTimeLeaveDetailService getSysTimeLeaveDetailService() {
		if (sysTimeLeaveDetailService == null) {
			sysTimeLeaveDetailService = (ISysTimeLeaveDetailService) SpringBeanUtil
					.getBean("sysTimeLeaveDetailService");
		}
		return sysTimeLeaveDetailService;
	}
	public ISysAttendStatJobService getSysAttendStatJobService() {
		if (sysAttendStatJobService == null) {
			sysAttendStatJobService = (ISysAttendStatJobService) SpringBeanUtil
					.getBean("sysAttendStatJobService");
		}
		return sysAttendStatJobService;
	}
	public ISysTimeLeaveRuleService getSysTimeLeaveRuleService() {
		if (sysTimeLeaveRuleService == null) {
			sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) SpringBeanUtil
					.getBean("sysTimeLeaveRuleService");
		}
		return sysTimeLeaveRuleService;
	}
	private ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService;

	public ISysTimeLeaveAmountItemService getSysTimeLeaveAmountItemService() {
		if (sysTimeLeaveAmountItemService == null) {
			sysTimeLeaveAmountItemService = (ISysTimeLeaveAmountItemService) SpringBeanUtil
					.getBean(
							"sysTimeLeaveAmountItemService");
		}
		return sysTimeLeaveAmountItemService;
	}
	private ISysTimeLeaveAmountService sysTimeLeaveAmountService;
	public ISysTimeLeaveAmountService getSysTimeLeaveAmountService() {
		if (sysTimeLeaveAmountService == null) {
			sysTimeLeaveAmountService = (ISysTimeLeaveAmountService) SpringBeanUtil
					.getBean("sysTimeLeaveAmountService");
		}
		return sysTimeLeaveAmountService;
	}
	private ISysAuthDefaultAreaService sysAuthDefaultAreaService;

	public ISysAuthDefaultAreaService getSysAuthDefaultAreaService() {
		if (sysAuthDefaultAreaService == null) {
			sysAuthDefaultAreaService = (ISysAuthDefaultAreaService) SpringBeanUtil.getBean(
					"sysAuthDefaultAreaService");
		}
		return sysAuthDefaultAreaService;
	}
	/**
	 * 设置用户的场所到单据中
	 * @param person
	 * @param sysTimeLeaveDetail
	 * @throws Exception
	 */
	protected void setUserAuthAtea(SysOrgElement person,SysTimeLeaveDetail sysTimeLeaveDetail) throws Exception {
		if(person !=null){
			SysAuthDefaultArea authDefArea = getSysAuthDefaultAreaService().findValue(person.getFdId());
			if (authDefArea != null) {
				sysTimeLeaveDetail.setAuthArea(authDefArea.getAuthArea());
			}
		}
	}
	/**
	 * 同步加班工时到假期额度
	 * @param bus 根据流程信息
	 * @param statMap 数据信息。主要获取当天人员总工时
	 * @throws Exception
	 */
	@Override
	public void addOvertime(SysAttendBusiness bus, JSONObject statMap) {
		TransactionStatus status = null;
		boolean isException = false;
		try {
			status = TransactionUtils.beginNewTransaction();
			//加班处理方式，1、调休，2、转加班费
			update(bus);
			SysTimeLeaveRule leaveRule = AttendUtil.getLeaveRuleByType(bus.getFdBusType());
			if (leaveRule == null) {
				logger.warn("找不到对应假期类型的规则,忽略假期转调休处理!请假fdId:{};fdBusType:{}", bus.getFdId(), bus.getFdBusType());
				return;
			}

			SysTimeLeaveDetail leaveDetail = null;
			String fdOverHandle = bus.getFdOverHandle();
			List<SysOrgElement> elementList = bus.getFdTargets();
			if (elementList != null && !elementList.isEmpty()) {
				for (int i = 0; i < elementList.size(); i++) {
					SysOrgElement element = elementList.get(i);
					boolean isAdd = false;
					Float oldTotleTime = 0f;
					leaveDetail = getSysTimeLeaveDetailService().findLeaveDetail(element.getFdId(), bus.getFdProcessId(), bus.getFdBusStartTime(), bus.getFdBusEndTime());
					if (leaveDetail == null) {
						leaveDetail = new SysTimeLeaveDetail();
						isAdd = true;
					} else {
						oldTotleTime = leaveDetail.getFdLeaveTime();
					}
					SysOrgPerson person = (SysOrgPerson) getSysOrgCoreService().format(element);
					leaveDetail.setFdPerson(person);
					leaveDetail.setFdType(2);
					leaveDetail.setFdStartTime(bus.getFdBusStartTime());
					leaveDetail.setFdEndTime(bus.getFdBusEndTime());
					//流程中加班时长、分钟数
					Integer overTimes = bus.getOverTime() == null ? 0 : bus.getOverTime();
					if ((oldTotleTime == null || Float.valueOf(0).equals(oldTotleTime)) && Integer.valueOf(0).equals(overTimes)) {
						//历史的加班时长为0.本次加班时长还是为0.则直接不处理
						continue;
					}
					Float fdConvertOverTime = null;
					if (statMap.containsKey("fdConvertOverTime")) {
						fdConvertOverTime = statMap.getFloat("fdConvertOverTime");
					}
					Float fdLeaveTime = 0F;
					Float fdTotalTime = 0F;
					Float fdCountHour = bus.getFdCountHour();
					if (fdCountHour != null && fdCountHour > 0.0f) {
						// 加班天数(兼容处理)
						fdLeaveTime = SysTimeUtil.getOverDays(fdCountHour, fdConvertOverTime);
						fdTotalTime = fdCountHour * 60;
					} else {
						fdLeaveTime = SysTimeUtil.getOvertimeDays(overTimes.floatValue(), fdConvertOverTime);
						fdTotalTime = overTimes.floatValue();
					}
					if (oldTotleTime != null && oldTotleTime.equals(fdTotalTime)) {
						//原来的计算时长 跟本次一致，则不处理
						continue;
					}
					//处理假期明细
					leaveDetail.setFdLeaveTime(fdLeaveTime);
					leaveDetail.setFdTotalTime(fdTotalTime);
					Calendar ca = Calendar.getInstance();
					ca.setTime(bus.getFdBusStartTime());
					if("1".equals(fdOverHandle)){
						//只有开启了假期额度，并且是转加班费，才开始
						if (Boolean.TRUE.equals(leaveRule.getFdIsAmount())) {
							//先处理假期额度，年份和假期类型明细
							addUserLeaveAmount(leaveRule, person, ca, leaveDetail.getFdLeaveTime(), oldTotleTime);
						}
						leaveDetail.setFdLeaveName(bus.getFdLeaveName());
						leaveDetail.setFdLeaveType(leaveRule != null ? leaveRule.getFdSerialNo() : null);
					}
					leaveDetail.setFdStatType(3);// 目前加班只支持小时方式,不支持跨天
					leaveDetail.setFdOprType(1);
					leaveDetail.setFdOprStatus(leaveRule == null ? 1 : 0);
					leaveDetail.setFdReviewName(bus.getFdProcessName());
					leaveDetail.setFdReviewId(bus.getFdProcessId());
					leaveDetail.setDocCreateTime(new Date());
					leaveDetail.setDocCreator(person);
					//判断是否开启额度
					if (!Boolean.TRUE.equals(leaveRule.getFdIsAmount())) {
						leaveDetail.setFdOprStatus(1);
					}
					if (isAdd) {
						setUserAuthAtea(person, leaveDetail);
						getSysTimeLeaveDetailService().add(leaveDetail);
					} else {
						leaveDetail.setFdOprStatus(1);
						getSysTimeLeaveDetailService().update(leaveDetail);
					}
				}
			}
		} catch (Exception e) {
			isException = true;
			e.printStackTrace();
			logger.error("计算加班时间异常:流程ID：" + bus.getFdProcessId() + " 错误信息：" + e.getMessage());
		} finally {
			if (isException && status != null) {
				TransactionUtils.rollback(status);
			} else if (status != null) {
				TransactionUtils.commit(status);
			}
		}
	}
	/**
	 * 转调休，如果假期额度数据为null，则自动添加假期额度数据
	 * @param leaveRule 转调休的假期类型
	 * @param person 人员
	 * @param ca 日期
	 * @param fdLeaveTime 本次加班的时长。转换天以后的
	 * @return
	 * @throws Exception
	 */
	private void addUserLeaveAmount(SysTimeLeaveRule leaveRule,SysOrgPerson person, Calendar ca,
									Float fdLeaveTime,Float oldTotleTime)
			throws Exception {
		//该人员本年度 本次假期类型是否存在
		SysTimeLeaveAmountItem userLeaveAmount = getSysTimeLeaveAmountService().getLeaveAmountItemByType(ca.get(Calendar.YEAR),person.getFdId(), leaveRule.getFdSerialNo());
		if (userLeaveAmount == null) {
			//不存在，则找当前年的 假期额度
			SysTimeLeaveAmount amount = getSysTimeLeaveAmountService().getLeaveAmount(ca.get(Calendar.YEAR), person.getFdId());
			logger.warn( "找不到用户对应假期额度信息,自动添加假期额度信息!userId:" + person.getFdId());
			if (amount == null) {
				amount = new SysTimeLeaveAmount();
				amount.setFdYear(ca.get(Calendar.YEAR));
				amount.setFdPerson(person);
				amount.setDocCreator(person);
				amount.setDocCreateTime(ca.getTime());
				amount.setFdOperator(person);
				getSysTimeLeaveAmountService().getBaseDao().add(amount);
			}
			//该假期类型的明细
			userLeaveAmount = new SysTimeLeaveAmountItem();
			userLeaveAmount.setFdIsAuto(false);
			userLeaveAmount.setFdLeaveType(leaveRule.getFdSerialNo());
			userLeaveAmount.setFdLeaveName(leaveRule.getFdName());
			userLeaveAmount.setFdIsAvail(true);
			userLeaveAmount.setFdIsAccumulate(false);
			userLeaveAmount.setFdUsedDay(0f);
			userLeaveAmount.setFdTotalDay(fdLeaveTime);
			userLeaveAmount.setFdRestDay(fdLeaveTime);
			userLeaveAmount.setFdAmount(amount);
			//查询上一年的 剩余额度加入到本年度
			SysTimeLeaveAmountItem upAmountItem =getSysTimeLeaveAmountItemService()
					.getAmountItem(person.getFdId(),ca.get(Calendar.YEAR)-1,leaveRule.getFdSerialNo() );
			if(upAmountItem !=null){
				//上一年的周期继承（新一年额度第一次新增才会赋值
				//上周期总额度
				userLeaveAmount.setFdLastTotalDay(upAmountItem.getFdTotalDay());
				//剩余额度
				userLeaveAmount.setFdLastRestDay(upAmountItem.getFdRestDay());
				//使用额度
				userLeaveAmount.setFdLastUsedDay(upAmountItem.getFdUsedDay());
				//有效期
				userLeaveAmount.setFdLastValidDate(upAmountItem.getFdValidDate());
				userLeaveAmount.setFdIsLastAvail(Boolean.TRUE);
				if(upAmountItem.getFdValidDate() !=null){
					if(SysTimeUtil.getDate(new Date(),0).getTime() > upAmountItem.getFdValidDate().getTime()){
						userLeaveAmount.setFdIsLastAvail(Boolean.FALSE);
					}
				}
			}
			getSysTimeLeaveAmountItemService().getBaseDao().add(userLeaveAmount);
		} else {
			oldTotleTime =oldTotleTime==null?0f:oldTotleTime;
			//如果存在，则直接加上本次 转调休的额度
			Float fdTotal = userLeaveAmount.getFdTotalDay() != null ? userLeaveAmount.getFdTotalDay() : 0f;
			Float fdRestDay = userLeaveAmount.getFdRestDay() != null ? userLeaveAmount.getFdRestDay() : 0f;

			//加班额度 剩余。可能第一次统计以后，后面又继续增加了，所以先减去上一次加的，在加上本次的。如果存在负数则直接以0结束
			Float restDay = fdRestDay - oldTotleTime + fdLeaveTime;
			//可能存在加班完以后。使用掉了。这时候 的加减可能为负数，不管历史额度使用记录。直接0
			Float total = fdTotal - oldTotleTime + fdLeaveTime;
			userLeaveAmount.setFdTotalDay(total < 0 ?0f:total);
			userLeaveAmount.setFdRestDay(restDay< 0 ?0f:restDay );

			getSysTimeLeaveAmountItemService().update(userLeaveAmount);
		}
	}

}
