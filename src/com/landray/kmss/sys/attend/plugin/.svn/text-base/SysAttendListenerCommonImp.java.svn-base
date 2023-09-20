package com.landray.kmss.sys.attend.plugin;

import com.google.common.collect.Lists;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.model.SysAttendConfig;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryWorktimeService;
import com.landray.kmss.sys.attend.service.ISysAttendConfigService;
import com.landray.kmss.sys.attend.service.ISysAttendListenerCommonService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.spring.SysAttendMainJobServiceImp;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendThreadPoolManager;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.attend.util.DateTimeFormatUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.util.SysTimeLeaveTimeDto;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 流程中公共服务方法
 * 
 * @author 王京
 */
public class SysAttendListenerCommonImp extends SysAttendListenerCommon implements ISysAttendListenerCommonService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendListenerCommonImp.class);

	private ISysAttendConfigService sysAttendConfigService;

	public ISysAttendConfigService getSysAttendConfigService() {
		if (sysAttendConfigService == null) {
			sysAttendConfigService = (ISysAttendConfigService) SpringBeanUtil.getBean("sysAttendConfigService");
		}
		return sysAttendConfigService;
	}

	private ISysAttendCategoryWorktimeService sysAttendCategoryWorktimeService;

	public ISysAttendCategoryWorktimeService getSysAttendCategoryWorktimeService() {
		if (sysAttendCategoryWorktimeService == null) {
			sysAttendCategoryWorktimeService = (ISysAttendCategoryWorktimeService) SpringBeanUtil
					.getBean("sysAttendCategoryWorktimeService");
		}
		return sysAttendCategoryWorktimeService;
	}

	/**
	 * 根据请假流程记录生成有效考勤记录
	 *
	 * @param business
	 * @throws Exception
	 */
	@Override
	public void updateSysAttendMainByLeaveBis(SysAttendBusiness business) throws Exception {
		updateSysAttendMainByLeaveBis(business, null, null);
	}

	/**
	 * 根据请假流程记录生成有效考勤记录
	 * 
	 * @param business
	 * @throws Exception
	 */
	@Override
	public void updateSysAttendMainByLeaveBis(SysAttendBusiness business, Date statBeginDate, Date statEndDate)
			throws Exception {
		try {
			Date fdBusStartTime = business.getFdBusStartTime();
			Date fdBusEndTime = business.getFdBusEndTime();
			Integer fdStartNoon = business.getFdStartNoon();
			Integer fdEndNoon = business.getFdEndNoon();
			List<SysOrgPerson> personList = getSysOrgCoreService().expandToPerson(business.getFdTargets());
			Integer fdStatType = business.getFdStatType();
			Integer fdLeaveType = business.getFdBusType();
			// 每个人
			for (SysOrgPerson person : personList) {
				// 分割成每天
				List<Date> dateList = new ArrayList<Date>();

				if (fdStatType == null) {
					// 兼容以前数据
					dateList = AttendUtil.getDateListByTime(fdBusStartTime, fdBusEndTime);
				} else if (fdStatType == 1) {
					// 按天
					dateList = AttendUtil.getDateListByDay(fdBusStartTime, fdBusEndTime);
				} else if (fdStatType == 2) {
					// 按半天
					dateList = AttendUtil.getDateListByHalfDay(fdBusStartTime, fdBusEndTime, fdStartNoon, fdEndNoon);
				} else if (fdStatType == 3) {
					// 按小时
					dateList = AttendUtil.getDateListByTime(fdBusStartTime, fdBusEndTime);
				}
				if (dateList.size() < 2) {
					logger.error("根据请假流程更新考勤记录出错：日期有误");
					continue;
				}
				// 每天
				List<Map<String, Object>> allSignTimeList = Lists.newArrayList();
				for (int i = 0; i < dateList.size() - 1; i++) {
					Date startTime = dateList.get(i);
					// 只统计某个时间范围内的请假记录
					boolean update = true;
					if (statBeginDate != null && statEndDate != null) {
						if (startTime.getTime() >= statBeginDate.getTime()
								&& startTime.getTime() <= statEndDate.getTime()) {
							update = true;
						} else {
							update = false;
						}
					}
					if (update) {
						if (checkUserHaveCategory(Lists.newArrayList(person), startTime)) {
							SysAttendCategory category = getUserCategory(person, startTime);
							if (category == null) {
								logger.error("根据请假流程更新考勤记录出错：没有考勤组");
								continue;
							}
							Date endTime = dateList.get(i + 1);
							Date searchEndTime = endTime;
							if (fdStatType == 2 || fdStatType == 1) {
								// 第1条采用流程的上下午，其余采用默认上午开始
								if (i != 0) {
									fdStartNoon = 1;
								}
								searchEndTime = startTime;
								// 最后一条才采用原有的上下午，否则取默认下午
								if ((i + 1) != (dateList.size() - 1)) {
									fdEndNoon = 2;
								}
							}
							/** 新生成考勤计算逻辑 start */
							// 判定时间区间内的请假时间是否超过0.如果为0.表示为休息日
							SysTimeLeaveTimeDto dto = getLeaveTimes(person, fdLeaveType, startTime, searchEndTime,
									fdStatType, fdStartNoon, fdEndNoon);
							int leaveTimes = dto.getLeaveTimeMins();
							if (leaveTimes == 0) {
								logger.warn("休息日不需请假");
								continue;
							}
							// 根据时间过滤所有的班次
							List<Map<String, Object>> signTimeList = filterSignTimeByLeave(person, category, startTime,
									endTime, fdStatType);
							if (CollectionUtils.isNotEmpty(signTimeList)) {
								allSignTimeList.addAll(signTimeList);
							}
							/** 新生成考勤计算逻辑 end */
							/*
							 * 原请假生成考勤逻辑 if (isRestDay(startDate, category,
							 * person)) { if (fdLeaveType != null) {
							 * SysTimeLeaveRule sysTimeLeaveRule =
							 * AttendUtil.getLeaveRuleByType(fdLeaveType); if
							 * (sysTimeLeaveRule != null) { if
							 * (Integer.valueOf(1).equals(sysTimeLeaveRule.
							 * getFdStatDayType())) { // 只统计工作日，且这天是休息日
							 * logger.warn("休息日不需请假"); continue; } } } }
							 * updateAttendMain(person, category, startTime,
							 * endTime, nextEndTime, business, 5);
							 */
						}
					}
				}
				if (CollectionUtils.isNotEmpty(allSignTimeList)) {
					// 每个班次的有效考勤读取
					updateAttendMain(allSignTimeList, person, business, AttendConstant.ATTEND_PROCESS_TYPE[5]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("请假更新打卡记录出错:" + e.getMessage());
		}
	}

	private void updateAttendMain(List<Map<String, Object>> signTimeList, SysOrgPerson person,
			SysAttendBusiness business, Integer status) throws Exception {
		// 获取所有需要查询有效考勤的日期
		Set<Date> mainDay = new HashSet<>();
		List<SysAttendMain> signRecord = Lists.newArrayList();
		for (Iterator<Map<String, Object>> it = signTimeList.iterator(); it.hasNext();) {
			Map<String, Object> signTimeConfiguration = (Map<String, Object>) it.next();
			Long fdWorkDateLongTime = (Long) signTimeConfiguration.get("fdWorkDate");
			Date workDate = AttendUtil.getDate(new Date(fdWorkDateLongTime), 0);
			if (mainDay.contains(workDate)) {
				continue;
			}
			mainDay.add(workDate);
			// 查询这一天的跨天考勤以及不跨天考勤
			List<SysAttendMain> recordList = getSysAttendMainList(person.getFdId(), null,
					AttendUtil.getDate(workDate, 1), AttendUtil.getDate(workDate, 2), workDate,
					AttendUtil.getDate(workDate, 1), true);

			if (CollectionUtils.isNotEmpty(recordList)) {
				signRecord.addAll(recordList);
			}
		}
		if (CollectionUtils.isNotEmpty(signRecord)) {
			// 重新渲染班次id.主要是排班类型，班次id每次是新生成的，需要跟已打卡的记录统一为1个班次
			getSysAttendCategoryService().doWorkTimesRender(signTimeList, signRecord);
		}
		for (Iterator<Map<String, Object>> it = signTimeList.iterator(); it.hasNext();) {
			Map<String, Object> signTimeConfiguration = (Map<String, Object>) it.next();
			// 获取标准的打卡时间
			Date signTime = (Date) signTimeConfiguration.get("signTime");
			Long fdWorkDateLongTime = (Long) signTimeConfiguration.get("fdWorkDate");
			Date workDate = new Date(fdWorkDateLongTime);
			// 打卡日和打卡时间组合
			signTime = this.overTimeTypeProcess(signTimeConfiguration, AttendUtil.joinYMDandHMS(workDate, signTime));

			String categoryId = getSysAttendCategoryService().getCategory(person, workDate);
			if (StringUtil.isNull(categoryId)) {
				logger.warn("用户{}，日期{} 无考勤组", person.getFdId(), workDate);
				continue;
			}
			SysAttendCategory category = CategoryUtil.getCategoryById(categoryId);
			if (category == null) {
				logger.warn("用户{}，日期{} 无考勤组", person.getFdId(), workDate);
				continue;
			}
			boolean isCreate = true;
			if (CollectionUtils.isNotEmpty(signRecord)) {
				// 修改已经存在的考勤信息 为请假状态
				for (SysAttendMain record : signRecord) {
					System.out.println("SysAttendListenerCommonImp updateMain -- 11111" + person.getFdName()
							+ ymdhm.format(signTime));
					boolean update = false;

					if (record.getFdBaseWorkTime().getTime() == signTime.getTime()) {
						System.out.println("SysAttendListenerCommonImp updateMain -- 11111" + person.getFdName()
								+ ymdhm.format(workDate));
						updateMain(record, business, person, workDate, signTimeConfiguration, status, category);
						// 删除异常
						deleteAttendExc(record);
						// 待办置为已办
						setAttendNotifyToDone(record);
						if(record.getDocStatus()==1)
							isCreate = true;
						else
						isCreate = false;
					

					if (record.getFdBusiness() != null) {
						if (business.getFdId().equals(record.getFdBusiness().getFdId())) {
							Integer workType = (Integer) signTimeConfiguration.get("fdWorkType");

							if (workType == AttendConstant.SysAttendMain.FD_WORK_TYPE[0]) {// 上班
								signTime = business.getFdBusStartTime();
							}

							if (workType == AttendConstant.SysAttendMain.FD_WORK_TYPE[1]) {// 下班
								signTime = business.getFdBusEndTime();
							}

							System.out.println("SysAttendListenerCommonImp updateMain -- 11111" + person.getFdName()
									+ ymdhm.format(workDate));
							updateMain(record, business, person, workDate, signTimeConfiguration, status, category);
							// 删除异常
							deleteAttendExc(record);
							// 待办置为已办
							setAttendNotifyToDone(record);
							isCreate = false;
						}
					}
					}
				}
			}
			if (isCreate) {
				System.out.println(
						"SysAttendListenerCommonImp addMain -- 11111" + person.getFdName() + ymdhm.format(workDate));
				// 新增
				addMain(business, person, category, workDate, signTimeConfiguration, status);
			}
		}
	}

	/**
	 * 根据请假时间过滤请假时间所在的班次
	 * 
	 * @param person
	 *            人员
	 * @param category
	 *            考勤组
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param statType
	 *            请假类型，按天，按小时，按半天
	 * @throws Exception
	 */
	private List<Map<String, Object>> filterSignTimeByLeave(SysOrgPerson person, SysAttendCategory category,
			Date startTime, Date endTime, Integer statType) throws Exception {
		// 测试场景.跨天 20:00 次日8:00 按日、按小时
		// 打卡非跨天9:00 18:00 按日、按半天、按小时
		// 其他打卡时间非跨天，按日、按小时
		Date startDate = AttendUtil.getDate(startTime, 0);
		List<Map<String, Object>> allSignTimeList = Lists.newArrayList();
		List<Map<String, Object>> signTimeList = getSignTimeList(category, startTime, person, false);
		if (AttendConstant.FD_STAT_TYPE[1].equals(statType)) {
			// 如果是按天，则当天全部班次都返回
			return signTimeList;
		} else if (AttendConstant.FD_STAT_TYPE[2].equals(statType)) {
			// 如果是按半天。
			filterSignTime(signTimeList, startDate, startTime, endTime, statType);
			return signTimeList;
		} else {
			boolean isAll = false;
			if (CollectionUtils.isNotEmpty(signTimeList)) {
				// 获取最早最晚
				Date beginTime = AttendUtil.getWorkConfigFdBeginTime(signTimeList.get(0), startTime);
				Date overTime = AttendUtil.getWorkConfigFdOverTime(signTimeList.get(signTimeList.size() - 1),
						startTime);
				if (startTime.getTime() <= beginTime.getTime() && overTime.getTime() <= endTime.getTime()) {
					// 开始结束时间包含了整个当日的班次。则直接返回
					allSignTimeList.addAll(signTimeList);
					isAll = true;
				}
			}
			if (!isAll) {
				// 开始时间在当日班次的范围内
				Date onWorkDate = getSysAttendCategoryService().getTimeAreaDateOfDate(startTime, startDate,
						signTimeList);
				// 结束在当日的班次范围内
				Date offWorkDate = getSysAttendCategoryService().getTimeAreaDateOfDate(endTime, startDate,
						signTimeList);
				if (onWorkDate != null && offWorkDate != null) {
					filterSignTime(signTimeList, startDate, startTime, endTime, statType);
					allSignTimeList.addAll(signTimeList);
				} else {

					if (onWorkDate != null || offWorkDate != null) {
						filterSignTime(signTimeList, startDate, startTime, endTime, statType);
						allSignTimeList.addAll(signTimeList);
					}
					Date yesterday = AttendUtil.getDate(startTime, -1);
					// 昨日的班次
					List<Map<String, Object>> yesterSignTimeList = getSignTimeList(category, yesterday, person, false);
					// 结束时间在今日打卡范围内，开始时间不在今日打卡范围内
					onWorkDate = getSysAttendCategoryService().getTimeAreaDateOfDate(startTime, yesterday,
							yesterSignTimeList);
					// 结束时间在今日打卡范围内，开始时间不在今日打卡范围内
					offWorkDate = getSysAttendCategoryService().getTimeAreaDateOfDate(endTime, yesterday,
							yesterSignTimeList);
					if (onWorkDate != null || offWorkDate != null) {
						// 排除昨日的班次信息
						filterSignTime(yesterSignTimeList, yesterday, startTime, endTime, statType);
						allSignTimeList.addAll(yesterSignTimeList);
					}
				}
			}
		}
		return allSignTimeList;
	}

	/**
	 * 过滤班次
	 * 
	 * @param signTimeList
	 *            班次列表
	 * @param startDate
	 *            统计日期
	 * @param startTime
	 *            排除开始时间
	 * @param endTime
	 *            排除结束时间
	 */
	private void filterSignTime(List<Map<String, Object>> signTimeList, Date startDate, Date startTime, Date endTime,
			Integer fdStatType) {

		boolean isOneWork = signTimeList.size() > 2 ? false : true;
		int workNumber = 0;
		for (Iterator<Map<String, Object>> it = signTimeList.iterator(); it.hasNext();) {
			workNumber++;
			Map<String, Object> signTimeConfiguration = (Map<String, Object>) it.next();
			Date signTime = (Date) signTimeConfiguration.get("signTime");
			Integer workType = (Integer) signTimeConfiguration.get("fdWorkType");
			Boolean isNeedSign = (Boolean) signTimeConfiguration.get("isNeedSign");
			String categoryId = (String) signTimeConfiguration.get("categoryId");
			if (Boolean.TRUE.equals(isNeedSign)) {
				if (AttendConstant.FD_STAT_TYPE[2].equals(fdStatType)) {
					// 如果是按半天。
					if (AttendUtil.isZeroDay(startTime) && AttendUtil.isZeroDay(endTime)) {
						// 00 00 标识是整天
						break;
					} else if (AttendUtil.isZeroDay(startTime) && AttendUtil.isHalfDay(endTime)) {
						// 00 12 标识是上午
						// 如果是1班次，则剔除下午的，如果是多班次，则剔除顺序为3，4的班次
						if (isOneWork) {
							if (workType == 1) {
								it.remove();
							}
						} else {
							if (workNumber == 3 || workNumber == 4) {
								it.remove();
							}
						}
					} else if (AttendUtil.isZeroDay(endTime) && AttendUtil.isHalfDay(startTime)) {
						// 12 -00标识是下午
						if (isOneWork) {
							if (workType == 0) {
								it.remove();
							}
						} else {
							if (workNumber == 1 || workNumber == 2) {
								it.remove();
							}
						}
					}
				} else {
					// 判断跨天，增加1天
					signTime = this.overTimeTypeProcess(signTimeConfiguration,
							AttendUtil.joinYMDandHMS(startDate, signTime));
					if (workType == 0) {
						// 上班的班次。开始时间大于 标准打卡时间 则剔除。或者结束时间小于 标准打卡时间剔除
						try {
							if(CategoryUtil.getCategoryById(categoryId).getFdIsFlex()){
								if (startTime.getTime() > (signTime.getTime()+CategoryUtil.getCategoryById(categoryId).getFdFlexTime()*60*1000) || endTime.getTime() <= signTime.getTime()) {
									it.remove();
								}
								}else{
									if (startTime.getTime() > signTime.getTime() || endTime.getTime() <= signTime.getTime()) {
										it.remove();
									}
								}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						// 下班的班次。打卡时间大于 请假的结束时间 则剔除。或者请假开始时间 在下班打卡时间之前。
						if (signTime.getTime() > endTime.getTime() || signTime.getTime() <= startTime.getTime()) {
							it.remove();
						}
					}
				}
			} else {
				// 当天不需要打卡，班次都剔除
				it.remove();
			}
		}
	}

	/**
	 * 出差和请假的更新有效考勤的逻辑
	 * 
	 * @param person
	 *            人员
	 * @param category
	 *            考勤组
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param nextEndTime
	 * @param business
	 *            单证
	 * @param fdStatus
	 *            业务状态
	 * @throws Exception
	 */
	private void updateAttendMain(SysOrgPerson person, SysAttendCategory category, Date startTime, Date endTime,
			Date nextEndTime, SysAttendBusiness business, Integer fdStatus

	) throws Exception {
		// 开始时间
		Date startDate = AttendUtil.getDate(startTime, 0);
		String categoryId = category.getFdId();
		// 请假的时候，必须获取当天班次
		List<Map<String, Object>> signTimeList = getSignTimeList(category, startTime, person,
				Integer.valueOf(5).equals(fdStatus) ? false : true);
		if (signTimeList.isEmpty() && Integer.valueOf(1).equals(category.getFdShiftType())) {
			signTimeList = getAttendAreaRestSignTimes(category, AttendUtil.getDate(startDate, 1), person);
		}
		if (signTimeList.isEmpty()) {
			logger.error("更新记录出错：获取打卡时间点失败");
			return;
		}
		// 开始时间大于当天班次的最晚打卡时间点，则不查昨日的。否则就查昨日的
		Date workDate = getSysAttendCategoryService().getTimeAreaDateOfDate(startTime, startDate, signTimeList);
		boolean searchDay = true;
		if (workDate == null) {
			searchDay = false;
		}
		// 查一天的有效数据
		List<SysAttendMain> recordList = getUserAttendMainByDay(person, startTime, endTime, searchDay);
		for (Iterator it = recordList.iterator(); it.hasNext();) {
			SysAttendMain record = (SysAttendMain) it.next();
			String fdCategoryId = record.getFdHisCategory() != null ? record.getFdHisCategory().getFdId() : null;
			// 换了考勤组
			if (!categoryId.equals(fdCategoryId)) {
				// 删除异常
				deleteAttendExc(record);
				// 删除原有记录
				record.setDocStatus(1);
				record.setFdAlterRecord("考勤组变更,请假流程同步事件置为无效记录");
				record.setDocAlterTime(new Date());
				getSysAttendMainService().getBaseDao().update(record);
				// 待办置为已办
				setAttendNotifyToDone(record);
				it.remove();
			}
		}
		updateAttendMainExcetion(person, category, recordList, signTimeList, startTime, endTime, nextEndTime, business,
				fdStatus);
	}

	/**
	 * 更新出差的有效考勤记录
	 *
	 * @param business
	 * @throws Exception
	 */
	@Override
	public void updateSysAttendMainByBusiness(SysAttendBusiness business) throws Exception {
		updateSysAttendMainByBusiness(business, null, null);
	}

	/**
	 * 更新出差的有效考勤记录
	 *
	 * @param business
	 * @throws Exception
	 */
	@Override
	public void updateSysAttendMainByBusiness(SysAttendBusiness business, Date statBeginDate, Date statEndDate)
			throws Exception {
		Date fdBusStartTime = business.getFdBusStartTime();
		Date fdBusEndTime = business.getFdBusEndTime();
		List<SysOrgElement> fdTargets = business.getFdTargets();
		List<SysOrgPerson> personList = getSysOrgCoreService().expandToPerson(fdTargets);
		SysAttendConfig attendConfig = getSysAttendConfigService().getSysAttendConfig();
		// 每个人
		for (SysOrgPerson person : personList) {
			List<Date> dateList = getDateList(fdBusStartTime, fdBusEndTime);
			if (dateList.size() < 2) {
				continue;
			}
			// 每天
			for (int i = 0; i < dateList.size() - 1; i++) {
				Date startTime = dateList.get(i);

				if (statBeginDate != null && statEndDate != null) {
					// 指定只统计某天的日期
					if (startTime.getTime() >= statBeginDate.getTime()
							&& startTime.getTime() <= statEndDate.getTime()) {
						updateSysAttendMainByBusiness(person, startTime, dateList, i, attendConfig, business);
					}
				} else {
					// 默认统计流程中所有的天
					updateSysAttendMainByBusiness(person, startTime, dateList, i, attendConfig, business);
				}
			}
		}
	}

	/**
	 * 出差的流程处理 私有方法
	 *
	 * @param person
	 * @param startTime
	 * @param dateList
	 * @param i
	 * @param attendConfig
	 * @param business
	 * @throws Exception
	 */
	private void updateSysAttendMainByBusiness(SysOrgPerson person, Date startTime, List<Date> dateList, int i,
			SysAttendConfig attendConfig, SysAttendBusiness business) throws Exception {
		SysAttendCategory category = getSysAttendCategoryService().getCategoryInfo(person, startTime, true);
		if (category == null) {
			logger.warn("该用户没有配置相应考勤组,同步数据到考勤考勤记录忽略!userId:" + person.getFdId());
			return;
		}
		String categoryId = category.getFdId();

		Date endTime = dateList.get(i + 1);
		Date nextEndTime = null;
		if ((i + 2) < dateList.size()) {
			nextEndTime = dateList.get(i + 2);
		}
		Date startDate = AttendUtil.getDate(startTime, 0);

		if (StringUtil.isNotNull(categoryId)) {
			boolean isWork = false;// 按工作日计算
			// 自然日计算
			if (attendConfig == null || attendConfig.getFdTrip() == null
					|| Boolean.TRUE.equals(attendConfig.getFdTrip())) {
				isWork = true;
			}
			if (!isWork) {
				boolean isRestDay = isRestDay(startDate, category, person);
				if (isRestDay) {
					logger.warn(
							"出差按工作日计算,该用户对应考勤组当天为休息日,同步数据到考勤考勤记录忽略!userId:" + person.getFdId() + ";date:" + startDate);
					return;// 只能工作日出差
				}
			}
			updateAttendMain(person, category, startTime, endTime, nextEndTime, business, 4);
		}
	}

	/**
	 * 更新有效考勤记录的执行
	 *
	 * @param person
	 *            人员
	 * @param category
	 *            考勤组
	 * @param recordList
	 *            打卡记录
	 * @param signTimeList
	 *            应打卡时间列表
	 * @param startTime
	 *            计算开始时间
	 * @param endTime
	 *            计算结束时间
	 * @param nextEndTime
	 *            结束时间增加一天
	 * @param business
	 *            流程数据
	 * @param fdStatus
	 *            状态【4 出差,5 请假，6外出】
	 * @throws Exception
	 */
	protected void updateAttendMainExcetion(SysOrgElement person, SysAttendCategory category,
			List<SysAttendMain> recordList, List<Map<String, Object>> signTimeList, Date startTime, Date endTime,
			Date nextEndTime, SysAttendBusiness business, Integer fdStatus) throws Exception {
		Date startDate = AttendUtil.getDate(startTime, 0);
		if (recordList.isEmpty()) {
			// 某个时间段内
			signTimeList = filterSignTimeList(signTimeList, startTime, endTime);
			// 新增
			for (Map<String, Object> signTimeConfiguration : signTimeList) {
				// 跨天时，有可能来自于补昨天晚班晚班卡的需要。
				if (this.isOverTimeType(signTimeConfiguration)) {
					// 如果结束时间为当天的零点，需要补今天晚班的跨天卡
					if (isLastSchedulingOfCurrentday(startDate, endTime, nextEndTime, signTimeConfiguration)) {
						System.out.println("SysAttendListenerCommonImp addMain -- 22222" + person.getFdName()
								+ ymdhm.format(startDate));
						addMain(business, person, category, startDate, signTimeConfiguration, fdStatus);
					}
					// 如果开始时间为出差开始时间并于上一天的末班下班时间内
					if (!AttendUtil.isZeroDay(startTime)
							&& isLastSchedulingOfYesterday(startTime, startDate, signTimeConfiguration)) {
						System.out.println("SysAttendListenerCommonImp addMain -- 33333" + person.getFdName()
								+ ymdhm.format(startDate));
						addMain(business, person, category, AttendUtil.getDate(startDate, -1), signTimeConfiguration,
								fdStatus);
					}
				} else {
					System.out.println("SysAttendListenerCommonImp addMain -- 44444" + person.getFdName()
							+ ymdhm.format(startDate));
					addMain(business, person, category, startDate, signTimeConfiguration, fdStatus);
				}
			}
		} else {
			getSysAttendCategoryService().doWorkTimesRender(signTimeList, recordList);
			// 某个时间段内
			signTimeList = filterSignTimeList(signTimeList, startTime, endTime);
			filterOutScopeAttendRecords(startTime, recordList, signTimeList);
			for (Map<String, Object> signTimeConfiguration : signTimeList) {
				boolean isCreateNew = this.isOverTimeType(signTimeConfiguration) ? AttendUtil.isZeroDay(endTime) : true;
				// 是否创建昨日最晚打卡范围时间
				boolean isCreateLastScheduling = !AttendUtil.isZeroDay(startTime)
						&& this.isOverTimeType(signTimeConfiguration);
				for (SysAttendMain record : recordList) {
//					if(record.getFdStatus()==0){
//						record.setDocStatus(1);
//							getSysAttendMainService().getBaseDao().update(record);
////							addMain(business, person, category, startDate, signTimeConfiguration, fdStatus);
//							continue;
//					}
					if (getSysAttendCategoryService().isSameWorkTime(signTimeConfiguration,
							record.getWorkTime() == null ? "" : record.getWorkTime().getFdId(), record.getFdWorkType(),
							record.getFdWorkKey())) {
						// 更新
						if (this.isOverTimeType(signTimeConfiguration)) {
							// 该数据为属于今天的跨天数据
							if (record.getDocCreateTime().after(AttendUtil.getDate(startDate, 1))) {
								if (isLastSchedulingOfCurrentday(startDate, endTime, nextEndTime,
										signTimeConfiguration)) {
									System.out.println("SysAttendListenerCommonImp updateMain -- 44444");
									updateMain(record, business, person, startDate, signTimeConfiguration, fdStatus,
											category);
									isCreateLastScheduling = false;
								}
								isCreateNew = false;
							}
							// 该数据为昨天的跨天数据，只有在最开始时，才会包含昨天的数据
							else if (isLastSchedulingOfYesterday(startTime, startDate, signTimeConfiguration)) {
								System.out.println("SysAttendListenerCommonImp updateMain -- 33333");
								updateMain(record, business, person, AttendUtil.getDate(startDate, -1),
										signTimeConfiguration, fdStatus, category);
								isCreateLastScheduling = false;
							}
						} else {
							System.out.println("SysAttendListenerCommonImp updateMain -- 22222");
							updateMain(record, business, person, startDate, signTimeConfiguration, fdStatus, category);
							isCreateNew = false;
						}
						// 删除异常
						deleteAttendExc(record);
						// 待办置为已办
						setAttendNotifyToDone(record);
						break;
					} else if (!AttendUtil.isZeroDay(startTime) && Boolean.TRUE.equals(record.getFdIsAcross())
							&& isOverTimeType(signTimeConfiguration)) {
						Date preStartTime = AttendUtil.addDate(startTime, -1);
						Date preEndTime = AttendUtil.addDate(endTime, -1);
						List<Map<String, Object>> preSignTimeList = getSignTimeList(category, preStartTime, person,
								true);
						getSysAttendCategoryService().doWorkTimesRender(preSignTimeList,
								new ArrayList<SysAttendMain>(Arrays.asList(new SysAttendMain[] { record })));
						preSignTimeList = filterSignTimeList(preSignTimeList, preStartTime, preEndTime);
						for (Map<String, Object> preSignTimeConfiguration : preSignTimeList) {
							if (this.isOverTimeType(preSignTimeConfiguration)
									&& getSysAttendCategoryService().isSameWorkTime(preSignTimeConfiguration,
											record.getWorkTime() == null ? "" : record.getWorkTime().getFdId(),
											record.getFdWorkType(), record.getFdWorkKey())) {
								if (isLastSchedulingOfYesterday(startTime, startDate, preSignTimeConfiguration)) {
									System.out.println("SysAttendListenerCommonImp updateMain -- 6666"
											+ person.getFdName() + ymdhm.format(startDate));
									// 如果出差开始时间位于上一天的末班下班时间内
									updateMain(record, business, person, AttendUtil.getDate(startDate, -1),
											preSignTimeConfiguration, fdStatus, category);
									isCreateLastScheduling = false;
								}
								break;
							}
						}
					}
				}
				if (isCreateNew) {
					if (this.isOverTimeType(signTimeConfiguration)) {
						if (isLastSchedulingOfCurrentday(startDate, endTime, nextEndTime, signTimeConfiguration)) {
							System.out.println("SysAttendListenerCommonImp addMain -- 55555" + person.getFdName()
									+ ymdhm.format(startDate));
							addMain(business, person, category, startDate, signTimeConfiguration, fdStatus);
						}
					} else {
						// 新增
						addMain(business, person, category, startDate, signTimeConfiguration, fdStatus);
					}
				}
				// 如果出差开始时间位于上一天的末班下班时间内
				if (isCreateLastScheduling
						&& isLastSchedulingOfYesterday(startTime, startDate, signTimeConfiguration)) {
					System.out.println("SysAttendListenerCommonImp addMain -- 66666" + person.getFdName()
							+ ymdhm.format(startDate));
					addMain(business, person, category, AttendUtil.getDate(startDate, -1), signTimeConfiguration,
							fdStatus);
				}
			}
		}
	}

	/**
	 * 根据人员时间查询对应的有效考勤记录
	 *
	 * @param person
	 * @param startTime
	 * @param endTime
	 * @param searchDay
	 *            是否查询跨天的打卡数据( true是查询,false是不查询)
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SysAttendMain> getUserAttendMainByDay(SysOrgElement person, Date startTime, Date endTime,
			boolean searchDay) throws Exception {
		Date startDate = AttendUtil.getDate(startTime, 0);
		boolean endIsZeroDay = AttendUtil.isZeroDay(endTime);
		return getSysAttendMainList(person.getFdId(), null, endIsZeroDay ? AttendUtil.getDate(startDate, 1) : null,
				endIsZeroDay ? AttendUtil.getDate(startDate, 2) : null, startDate, AttendUtil.getDate(startDate, 1),
				searchDay);
	}

	/**
	 * 新增打卡记录
	 *
	 * @param business
	 *            出差记录
	 * @param person
	 *            出差人
	 * @param category
	 *            考勤组
	 * @param startDate
	 *            出差开始时间
	 * @param signTimeConfiguration
	 *            打卡时间配置
	 * @throws Exception
	 */
	protected void addMain(SysAttendBusiness business, SysOrgElement person, SysAttendCategory category, Date startDate,
			Map<String, Object> signTimeConfiguration, Integer fdStatus) throws Exception {
		Integer dateType = this.isRestDay(startDate, category, person) ? 1 : 0;
		String workTimeId = (String) signTimeConfiguration.get("fdWorkTimeId");
		Integer workType = (Integer) signTimeConfiguration.get("fdWorkType");
		Date signTime = AttendUtil.joinYMDandHMS(startDate, (Date) signTimeConfiguration.get("signTime"));
		// 标准打卡时间
		Date baseSignTime = overTimeTypeProcess(signTimeConfiguration, signTime);
		// 生成的打卡时间
		Date signTimeNew = baseSignTime;
		Date date1 = AttendUtil.getDayLastTime(baseSignTime);
		Date date2 = AttendUtil.getDayLastTime(AttendUtil.getDate(baseSignTime,-1));
		if(workType==0&&fdStatus!=4){
			if(AttendUtil.removeSecond(business.getFdBusStartTime()).getTime()>date2.getTime())
			signTimeNew = business.getFdBusStartTime();
		}
		if(workType==1&&fdStatus!=4){
			if(AttendUtil.removeSecond(business.getFdBusEndTime()).getTime()<date1.getTime())
				signTimeNew = business.getFdBusEndTime();
//					signTimeNew=AttendUtil.joinYMDandHMS(date1,signTime);
		}
		/*
		 * //如果是按小时的类型 if(Integer.valueOf(3).equals(business.getFdStatType())){
		 * //如果是上班，请假开始时间 >标准上班时间，<= 弹性最大时间。则使用弹性时间
		 * if(Integer.valueOf(0).equals(workType)) { Date lastSignTIme =
		 * getSysAttendCategoryService().getShouldOnWorkTime(
		 * signTimeConfiguration,signTimeNew); if(startDate.getTime() >
		 * signTimeNew.getTime() && startDate.getTime()<=
		 * lastSignTIme.getTime()){ //取弹性的打卡时间为 请假的时间 signTimeNew =
		 * lastSignTIme; } }else if(Integer.valueOf(1).equals(workType)) {
		 * //如果是下班，请假开始时间 < 标准上班时间，>=弹性最大时间。则使用弹性时间 Date lastSignTIme =
		 * getSysAttendCategoryService().getShouldOffWorkTime(
		 * signTimeConfiguration,signTimeNew); if(startDate.getTime() <
		 * signTimeNew.getTime() && startDate.getTime() >=
		 * lastSignTIme.getTime()){ //取弹性的打卡时间为 请假的时间 signTimeNew =
		 * lastSignTIme; } } }
		 */
		System.out.println("SysAttendListenerCommonImp addMain -- 8777" + person.getFdName() + ymdhm.format(startDate)
				+ ymdhm.format(signTimeNew));
		SysAttendMain main = new SysAttendMain();
		main.setFdId(IDGenerator.generateID());
		main.setDocCreateTime(signTimeNew);
		main.setDocAlterTime(new Date());
		person = getSysOrgCoreService().format(person);
		main.setDocCreator((SysOrgPerson) person);
		if (StringUtil.isNotNull(workTimeId)) {
			SysAttendCategoryWorktime workTime = (SysAttendCategoryWorktime) getSysAttendCategoryWorktimeService()
					.findByPrimaryKey(workTimeId, null, true);
			if (workTime != null) {
				main.setWorkTime(workTime);
			} else {
				main.setFdWorkKey(workTimeId);
			}
		}
		main.setFdHisCategory(CategoryUtil.getHisCategoryById(category.getFdId()));
		main.setFdOffType(business.getFdBusType());
		main.setDocCreatorHId(person.getFdHierarchyId());
		main.setFdIsAcross(isOverTimeType(signTimeConfiguration));
		main.setFdOutside(false);
		main.setFdDateType(dateType);
		//设置标准打卡时间
//		main.setFdBaseWorkTime(signTime);
		main.setFdBaseWorkTime(baseSignTime);
		//设置有效考勤状态，为出差状态
		main.setFdStatus(fdStatus);
//		if (business != null) {
//			if (workType.equals(AttendConstant.SysAttendMain.FD_WORK_TYPE[0])) {// 上班
//				//main.setFdBaseWorkTime(business.getFdBusEndTime());
//				if (signTimeNew.getTime() > business.getFdBusStartTime().getTime()) {
//					main.setFdStatus(2);// 迟到
//				} else {
//					main.setFdStatus(fdStatus);
//				}
//			}
//
//			if (workType.equals(AttendConstant.SysAttendMain.FD_WORK_TYPE[1])) {// 下班
//				//main.setFdBaseWorkTime(business.getFdBusStartTime());
//				if (signTimeNew.getTime() < business.getFdBusEndTime().getTime()) {
//					main.setFdStatus(3);// 早退
//				} else {
//					main.setFdStatus(fdStatus);
//				}
//			}
//		} else {
//			main.setFdBaseWorkTime(signTimeNew);
//			main.setFdStatus(fdStatus);
//		}

		main.setFdWorkType(workType);
		main.setFdBusiness(business);
		main.setFdOffType(business.getFdBusType());
		main.setDocStatus(0);
		getSysAttendMainService().getBaseDao().add(main);
	}

	SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/**
	 * 更新打卡记录
	 *
	 * @param attendRecord
	 *            考勤记录
	 * @param business
	 *            出差记录
	 * @param person
	 *            出差人
	 * @param startDate
	 *            出差开始时间
	 * @param signTimeConfiguration
	 *            打卡配置
	 * @throws Exception
	 */
	protected void updateMain(SysAttendMain attendRecord, SysAttendBusiness business, SysOrgElement person,
			Date startDate, Map<String, Object> signTimeConfiguration, Integer fdStatus, SysAttendCategory category)
			throws Exception {

		attendRecord.setFdHisCategory(CategoryUtil.getHisCategoryById(category.getFdId()));
		StringBuffer dec = new StringBuffer();

		dec.append("签卡时间" + ymdhm.format(attendRecord.getDocCreateTime()) + "， ");
		Date signTime = AttendUtil.joinYMDandHMS(startDate, (Date) signTimeConfiguration.get("signTime"));
		Boolean fdIsFlex = (Boolean) signTimeConfiguration.get("fdIsFlex");
		Date restStart = (Date) signTimeConfiguration.get("fdRestStartTime");
		Date restEnd = (Date) signTimeConfiguration.get("fdRestEndTime");
		Date signTimeNew = overTimeTypeProcess(signTimeConfiguration, signTime);
		long internal = 0;
		if( fdIsFlex&&business.getFdBusStartTime().getTime()>signTime.getTime())internal=business.getFdBusStartTime().getTime()-signTime.getTime();
		if (business != null) {
			List<SysAttendCategory> list=new ArrayList<SysAttendCategory>();
			list.add(category);
			com.alibaba.fastjson.JSONArray datas = getSysAttendCategoryService().filterAttendCategory(list,
					attendRecord.getDocCreateTime(), true, attendRecord.getDocCreator());
			
				
			if (attendRecord.getFdWorkType() == AttendConstant.SysAttendMain.FD_WORK_TYPE[0]) {// 上班
				if(datas==null||datas.isEmpty())
					attendRecord.setFdBaseWorkTime(business.getFdBusStartTime());
//				attendRecord.setFdBaseWorkTime(business.getFdBusEndTime());
//				dec.append(
//						"基准时间由" + ymdhm.format(signTimeNew) + "改为" + ymdhm.format(business.getFdBusEndTime()) + "， ");
				if (restStart.getTime()>AttendUtil.removeSecond(attendRecord.getDocCreateTime()).getTime()&&AttendUtil.removeSecond(attendRecord.getDocCreateTime()).getTime() > AttendUtil.removeSecond(business.getFdBusEndTime()).getTime()) {
					attendRecord.setFdStatus(2);// 迟到
					attendRecord.setFdDesc(dec.toString() + "出差/请假/外出之后，迟到");
				}else if(attendRecord.getFdBusiness()==null && AttendUtil.removeSecond(attendRecord.getDocCreateTime()).getTime()<=AttendUtil.removeSecond(attendRecord.getFdBaseWorkTime()).getTime())attendRecord.setFdStatus(1);
				else {
					attendRecord.setFdStatus(fdStatus);
					attendRecord.setFdDesc(dec.toString() + "出差/请假/外出之后，正常");
				}
				if(business.getFdBusEndTime().getTime()<restStart.getTime())
					attendRecord.setFdBusiness(business);
				attendRecord.setFdOffType(business.getFdBusType());
			}

			if (attendRecord.getFdWorkType() == AttendConstant.SysAttendMain.FD_WORK_TYPE[1]) {// 下班
//				attendRecord.setFdBaseWorkTime(business.getFdBusStartTime());
//				dec.append(
//						"基准时间由" + ymdhm.format(signTimeNew) + "改为" + ymdhm.format(business.getFdBusStartTime()) + "， ");
				if(datas==null||datas.isEmpty())
					attendRecord.setFdBaseWorkTime(business.getFdBusEndTime());
				Date startDate1 = attendRecord.getDocCreateTime();
				if(startDate1.getTime()>restStart.getTime()&&startDate1.getTime()<restEnd.getTime())
					startDate1=restEnd;
				if(business.getFdBusEndTime().getTime()<=restEnd.getTime())attendRecord.setFdStatus(1);
				else if(business.getFdBusStartTime().getTime()<=attendRecord.getFdBaseWorkTime().getTime()&&business.getFdBusEndTime().getTime()>=attendRecord.getFdBaseWorkTime().getTime()&&startDate1.getTime()==restEnd.getTime())
				{
					attendRecord.setFdStatus(fdStatus);
					attendRecord.setFdDesc(dec.toString() + "出差/请假/外出之后，正常");

				}
				else if (attendRecord.getDocCreateTime().getTime()>restEnd.getTime()&&(attendRecord.getDocCreateTime().getTime()<(internal+attendRecord.getFdBaseWorkTime().getTime())&&startDate1.getTime() < business.getFdBusStartTime().getTime())) {
					attendRecord.setFdStatus(3);// 早退
					attendRecord.setFdDesc(dec.toString() + "出差/请假/外出之后，早退");
				}
				else {
					attendRecord.setFdStatus(fdStatus);
					attendRecord.setFdDesc(dec.toString() + "出差/请假/外出之后，正常");
				}
				if(AttendUtil.removeSecond(business.getFdBusStartTime()).getTime()>=AttendUtil.removeSecond(restEnd).getTime())
					attendRecord.setFdBusiness(business);
					attendRecord.setFdOffType(business.getFdBusType());
			}
		} else {
//			attendRecord.setFdBaseWorkTime(signTimeNew);
		}
		if(fdStatus==4)
		attendRecord.setFdStatus(fdStatus);
		attendRecord.setFdWorkType(attendRecord.getFdWorkType());
		attendRecord.setDocCreatorHId(person.getFdHierarchyId());
		attendRecord.setFdIsAcross(isOverTimeType(signTimeConfiguration));
		attendRecord.setDocAlterTime(new Date());
		getSysAttendMainService().getBaseDao().update(attendRecord);
	}

	/**
	 * 过滤掉属于前一天的打卡时间
	 *
	 * @param startTime
	 *            出差开始时间
	 * @param attendRecords
	 *            考勤记录
	 * @param signTimeConfigurations
	 *            打卡配置信息
	 */
	protected void filterOutScopeAttendRecords(Date startTime, List<SysAttendMain> attendRecords,
			List<Map<String, Object>> signTimeConfigurations) {
		// 如果是按天出差，则过滤前天考勤数据
		if (!signTimeConfigurations.isEmpty()) {
			if (AttendUtil.isZeroDay(startTime)) {
				Date fdStartTime = (Date) signTimeConfigurations.get(0).get("fdStartTime");
				fdStartTime = AttendUtil.joinYMDandHMS(startTime, fdStartTime);
				Iterator<SysAttendMain> iterator = attendRecords.iterator();
				while (iterator.hasNext()) {
					SysAttendMain attendRecord = iterator.next();
					if (attendRecord.getDocCreateTime().before(fdStartTime)) {
						iterator.remove();
					}
				}
			} else {
				for (Map<String, Object> signTimeConfiguration : signTimeConfigurations) {
					if (this.isOverTimeType(signTimeConfiguration)) {
						Date signTime = (Date) signTimeConfiguration.get("signTime");
						signTime = AttendUtil.joinYMDandHMS(startTime, signTime);
						if (startTime.compareTo(signTime) > 0) {
							Iterator<SysAttendMain> iterator = attendRecords.iterator();
							while (iterator.hasNext()) {
								SysAttendMain attendRecord = iterator.next();
								if (attendRecord.getFdBaseWorkTime() != null
										&& attendRecord.getFdBaseWorkTime().before(startTime)) {
									iterator.remove();
								}
							}
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * 更新外出的有效考勤记录
	 *
	 * @param business
	 * @throws Exception
	 */
	@Override
	public void updateSysAttendMainByOutgoing(SysAttendBusiness business) throws Exception {

		try {
			// 验证是否有考勤组
			checkUserCategory(business.getFdTargets(), business.getFdBusStartTime());
			Date fdBusStartTime = business.getFdBusStartTime();
			Date fdBusEndTime = business.getFdBusEndTime();
			List<SysOrgElement> fdTargets = business.getFdTargets();
			List<SysOrgPerson> personList = getSysOrgCoreService().expandToPerson(fdTargets);

			// 每个人
			for (SysOrgPerson person : personList) {
				// 因为外出人员目前是只支持单日
				String categoryId = getSysAttendCategoryService().getCategory(person, fdBusStartTime);
				if (StringUtil.isNull(categoryId)) {
					continue;
				}
				updateOutgoingMain(business, person, categoryId, fdBusStartTime, fdBusEndTime, true);
			}
		} catch (Exception e) {
			logger.error("外出更新打卡记录出错:" + e.getMessage(), e);
			throw new RuntimeException("流程事件同步考勤失败:" + e.getMessage());
		}
	}

	/**
	 * 更新外出打卡记录
	 * 
	 * @param business
	 * @param person
	 * @param categoryId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	protected Boolean updateOutgoingMain(SysAttendBusiness business, SysOrgPerson person, String categoryId,
			Date startTime, Date endTime, boolean isSave) throws Exception {
		if (StringUtil.isNotNull(categoryId)) {
			SysAttendCategory category = CategoryUtil.getCategoryById(categoryId);
			if (category == null) {
				throw new Exception("no category");
			}
			SysAttendHisCategory hisCategory = CategoryUtil.getHisCategoryById(categoryId);
			// 计算日期
			Date date = AttendUtil.getDate(endTime, 0);
			Date yesterday = AttendUtil.getDate(endTime, -1);
			// 外出流程有外出排班
			List<Map<String, Object>> signTimeList = getSysAttendCategoryService().getAttendSignTimes(category, endTime,
					person);
			List<SysAttendCategory> list=new ArrayList<SysAttendCategory>();
			list.add(category);
			com.alibaba.fastjson.JSONArray datas = getSysAttendCategoryService().filterAttendCategory(list,
					endTime, true, person);
			boolean flag = false;
			if(datas == null || datas.isEmpty())
				flag=true;
			if (signTimeList.isEmpty()) {
				// 外出流程前一天有排班
				signTimeList = getSysAttendCategoryService().getAttendSignTimes(category, yesterday, person);
			}
			if (signTimeList.isEmpty()) {
				logger.warn("外出流程同步到考勤事件失败,忽略处理!原因:用户在该天没有排班.userName:" + person.getFdName() + ";date:" + date);
				return false;
			}
			// 排班类型 查找结束时间 最晚是否在昨日的排班范围内
			Date baseWorkTime = getSysAttendCategoryService().getTimeAreaDateOfDate(endTime, date, category, person);
			if (baseWorkTime == null) {
				baseWorkTime = getSysAttendCategoryService().getTimeAreaDateOfDate(endTime, yesterday, category,
						person);
				// 流程的结束时间 在昨日的最早最晚打卡时间范围内
				if (baseWorkTime != null) {
					date = AttendUtil.getDate(date, -1);
				} else {
					// 外出时间不落在任何一个班次上，则结束
					logger.warn("外出流程同步到考勤事件失败,忽略处理!原因:用户在该天没有排班.userName:" + person.getFdName() + ";date:" + date);
					return false;
				}
			}

			List<SysAttendMain> recordList = getSysAttendMainList(person.getFdId(), null, AttendUtil.getDate(date, 1),
					AttendUtil.getDate(date, 2), AttendUtil.getDate(date, 0), AttendUtil.getDate(date, 1),
					AttendUtil.isZeroDay(startTime));
			if (isSave) {
				for (Iterator it = recordList.iterator(); it.hasNext();) {
					SysAttendMain record = (SysAttendMain) it.next();
					String fdCategoryId = record.getFdHisCategory() != null ? record.getFdHisCategory().getFdId()
							: null;
					// 换了考勤组
					if (!categoryId.equals(fdCategoryId)) {
						// 删除异常
						deleteAttendExc(record);
						// 删除原有记录
						record.setDocStatus(1);
						record.setFdAlterRecord("外出流程同步事件置为无效记录");
						record.setDocAlterTime(new Date());
						getSysAttendMainService().getBaseDao().update(record);
						// 待办置为已办
						setAttendNotifyToDone(record);
						it.remove();
					}
				}
			}
			Integer fdDateType = isRestDay(date, category, person) ? 1 : 0;
			if (recordList.isEmpty()) {
				// 某个时间段内
				signTimeList = filterSignTimeListOfOutgoing(flag,signTimeList, startTime, endTime, date);
				if (signTimeList.isEmpty()) {
					logger.warn("外出流程同步到考勤事件失败,忽略处理!原因:用户在外出时间区间没有排班.userName:" + person.getFdName() + ";date:" + date);
					return false;
				}
				if (isSave) {
					for (Map<String, Object> m : signTimeList) {
						String fdWorkTimeId = (String) m.get("fdWorkTimeId");
						Integer fdWorkType = (Integer) m.get("fdWorkType");
						Date signTime = (Date) m.get("signTime");
						Integer overTimeType = (Integer) m.get("overTimeType");
						signTime = AttendUtil.joinYMDandHMS(date, signTime);
						// 新增
						addOutGoingMain(business, person, hisCategory, fdWorkTimeId, fdWorkType, signTime, fdDateType,
								overTimeType);
					}
				}
			} else {
				getSysAttendCategoryService().doWorkTimesRender(signTimeList, recordList);
				// 某个时间段内
				signTimeList = filterSignTimeListOfOutgoing(flag,signTimeList, startTime, endTime, date);
				if (signTimeList.isEmpty()) {
					logger.warn("外出流程同步到考勤事件失败,忽略处理!原因:用户在外出时间区间没有排班.userName:" + person.getFdName() + ";date:" + date);
					return false;
				}
				if (isSave) {
					for (Map<String, Object> m : signTimeList) {
						String fdWorkTimeId = (String) m.get("fdWorkTimeId");
						Integer fdWorkType = (Integer) m.get("fdWorkType");
						Date signTime = (Date) m.get("signTime");
						Integer overTimeType = (Integer) m.get("overTimeType");
						boolean hasFind = false;
						for (SysAttendMain record : recordList) {
							if (getSysAttendCategoryService().isSameWorkTime(m,
									record.getWorkTime() == null ? "" : record.getWorkTime().getFdId(),
									record.getFdWorkType(), record.getFdWorkKey())) {
								// 更新

								updateOutgoingMain(record, business, person,fdWorkType);
								// 删除异常
								deleteAttendExc(record);
								// 待办置为已办
								setAttendNotifyToDone(record);
								hasFind = true;
								break;
							}
						}
						if (!hasFind) {
							// 新增
							signTime = AttendUtil.joinYMDandHMS(date, signTime);
							addOutGoingMain(business, person, hisCategory, fdWorkTimeId, fdWorkType, signTime,
									fdDateType, overTimeType);
						}
					}
				}
			}

		}
		return true;
	}

	/**
	 * 新增打卡记录
	 *
	 * @param business
	 * @param person
	 * @param category
	 * @param workTimeId
	 * @param workType
	 * @param signTime
	 * @throws Exception
	 */
	private void addOutGoingMain(SysAttendBusiness business, SysOrgPerson person, SysAttendHisCategory category,
			String workTimeId, Integer workType, Date signTime, Integer dateType, Integer overTimeType)
			throws Exception {
		// #TODO 有待优化，跟其他添加有效考勤记录逻辑应该保持一直
		SysAttendMain main = new SysAttendMain();
		main.setFdId(IDGenerator.generateID());
		Boolean fdIsAcross = false;
		// 如果是跨天，则将打卡时间加一天
		if (Integer.valueOf(2).equals(overTimeType)) {
			signTime = AttendUtil.addDate(signTime, 1);
			fdIsAcross = true;
		}
		//定制开始，修改外出流程的打卡记录,add by liuyang
		Date createTime = signTime;
		//修改上班打卡记录
		if(workType == 0 && createTime.getTime() > business.getFdBusStartTime().getTime()){
			createTime = business.getFdBusStartTime();
		}
		//修改下班打卡记录
		if(workType == 1 && createTime.getTime() < business.getFdBusEndTime().getTime()){
			createTime = business.getFdBusEndTime();
		}
		main.setDocCreateTime(createTime);
		//定制结束，修改外出流程的打卡记录,add by liuyang
		main.setDocCreator(person);
		main.setFdStatus(6);// 6 为外出，此处为打卡记录的状态
		main.setFdBusiness(business);
		main.setFdWorkType(workType);
		if (StringUtil.isNotNull(workTimeId)) {
			SysAttendCategoryWorktime workTime = (SysAttendCategoryWorktime) getSysAttendCategoryWorktimeService()
					.findByPrimaryKey(workTimeId, null, true);
			if (workTime != null) {
				main.setWorkTime(workTime);
			} else {
				main.setFdWorkKey(workTimeId);
			}
		}
		main.setFdHisCategory(category);
		main.setDocCreatorHId(person.getFdHierarchyId());
		main.setFdIsAcross(fdIsAcross);
		main.setFdOutside(false);
		main.setFdDateType(dateType);
		List<SysAttendCategory> list=new ArrayList<SysAttendCategory>();
		list.add(main.getFdCategory());
		if(main.getDocCreateTime()!=null&&main.getDocCreator()!=null&&main.getFdCategory()!=null){
			com.alibaba.fastjson.JSONArray datas = getSysAttendCategoryService().filterAttendCategory(list,
					main.getDocCreateTime(), true, main.getDocCreator());
			
				
				if(datas==null||datas.isEmpty())
					main.setFdBaseWorkTime(createTime);
			}
		if(main.getFdBaseWorkTime()==null)
		main.setFdBaseWorkTime(signTime);
		main.setDocStatus(0);
		getSysAttendMainService().getBaseDao().add(main);
	}

	/**
	 * 更新打卡记录
	 *
	 * @param record
	 * @param business
	 * @param person
	 * @throws Exception
	 */
	private void updateOutgoingMain(SysAttendMain record, SysAttendBusiness business, SysOrgPerson person,int workType)
			throws Exception {
		// #TODO 有待优化，跟其他添加有效考勤记录逻辑应该保持一直
		record.setFdStatus(6);
		record.setFdBusiness(business);
		record.setDocCreatorHId(person.getFdHierarchyId());
		record.setFdState(null);
		record.setFdOutside(false);
		record.setFdOffType(null);
		record.setDocStatus(0);
		List<SysAttendCategory> list=new ArrayList<SysAttendCategory>();
		list.add(record.getFdCategory());
		
		//定制开始，修改外出流程的打卡记录,add by liuyang
		Date createTime = record.getDocCreateTime();
		//修改上班打卡记录
		if(workType == 0 && createTime.getTime() > business.getFdBusStartTime().getTime()){
			createTime = business.getFdBusStartTime();
		}
		//修改下班打卡记录
		if(workType == 1 && createTime.getTime() < business.getFdBusEndTime().getTime()){
			createTime = business.getFdBusEndTime();
		}
		if(record.getDocCreateTime()!=null&&record.getDocCreator()!=null&&record.getFdCategory()!=null){
			com.alibaba.fastjson.JSONArray datas = getSysAttendCategoryService().filterAttendCategory(list,
					record.getDocCreateTime(), true, record.getDocCreator());
			
				
				if(datas==null||datas.isEmpty())
					record.setFdBaseWorkTime(createTime);
			}
		record.setDocCreateTime(createTime);
		//定制结束，修改外出流程的打卡记录,add by liuyang
		getSysAttendMainService().getBaseDao().update(record);
	}

	/**
	 * 查询流程是否在考勤模块存在
	 * 
	 * @param processId
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean checkProcessIsHave(String processId, Integer... processType) throws Exception {
		if (StringUtil.isNull(processId)) {
			return false;
		}
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer whereBlock = new StringBuffer();
		whereBlock.append(" sysAttendBusiness.fdProcessId=:processId");
		if (processType != null && processType.length > 0) {
			List<Integer> searchParams = Arrays.asList(processType);
			whereBlock.append(" and ").append(HQLUtil.buildLogicIN("sysAttendBusiness.fdType", searchParams));
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setParameter("processId", processId);
		hqlInfo.setSelectBlock("sysAttendBusiness.fdId");
		List<Object> list = getSysAttendBusinessService().findValue(hqlInfo);
		return CollectionUtils.isNotEmpty(list) ? true : false;
	}

	/**
	 * 重新生成有效考勤记录时，先删除当天人员所有的有效考勤记录。重新生成
	 * 
	 * @param userIdList
	 *            人员列表
	 * @param date
	 *            日期
	 * @throws Exception
	 */
	@Override
	public void deleteMainBatch(List<String> userIdList, Date date) throws Exception {
		if (userIdList.isEmpty()) {
			logger.warn("重新生成有效考勤记录，人员为空");
			return;
		}
		DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
		Connection conn = null;
		PreparedStatement statement = null;
		PreparedStatement delete = null;
		PreparedStatement delete2 = null;

		List<String> mainIdList = new ArrayList<>();
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			List<List> groupLists = new ArrayList<List>();
			// 用户组分割
			int maxCount = 500;
			if (userIdList.size() <= maxCount) {
				groupLists.add(userIdList);
			} else {
				groupLists = AttendUtil.splitList(userIdList, maxCount);
			}
			// 重新生成，删除未处理的缺卡的待办通知。然后删除有效考勤
			for (int i = 0; i < groupLists.size(); i++) {
				ResultSet result = null;
				PreparedStatement queryMiss = null;

				try {
					// 查询缺卡记录。删除待办
					String querySql = "select fd_id from sys_attend_main where fd_status=0 and fd_state is null "
							+ "and (doc_status=0 or doc_status is null ) and (fd_is_across=0 or fd_is_across is null) "
							+ "and doc_create_time >=? and doc_create_time < ? and "
							+ HQLUtil.buildLogicIN("doc_creator_id", groupLists.get(i));
					queryMiss = conn.prepareStatement(querySql);
					queryMiss.setTimestamp(1, new Timestamp(AttendUtil.getDate(date, 0).getTime()));
					queryMiss.setTimestamp(2, new Timestamp(AttendUtil.getDate(date, 1).getTime()));
					result = queryMiss.executeQuery();
					while (result.next()) {
						mainIdList.add(result.getString(1));
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					JdbcUtils.closeResultSet(result);
					JdbcUtils.closeStatement(queryMiss);
				}
				ResultSet result2 = null;
				PreparedStatement queryMiss2 = null;
				try {
					// 查询缺卡记录。删除待办
					String querySql = "select fd_id from sys_attend_main where fd_status=0 and fd_state is null and (doc_status=0 or doc_status is null ) and fd_is_across = ? and doc_create_time >=? and doc_create_time < ? and "
							+ HQLUtil.buildLogicIN("doc_creator_id", groupLists.get(i));
					queryMiss2 = conn.prepareStatement(querySql);
					queryMiss2.setBoolean(1, true);
					queryMiss2.setTimestamp(2, new Timestamp(AttendUtil.getDate(date, 0).getTime()));
					queryMiss2.setTimestamp(3, new Timestamp(AttendUtil.getDate(date, 1).getTime()));
					result2 = queryMiss2.executeQuery();
					while (result2.next()) {
						mainIdList.add(result2.getString(1));
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					JdbcUtils.closeResultSet(result2);
					JdbcUtils.closeStatement(queryMiss2);
				}
				try {
					// 不跨天 不是补异常的考勤
					String sql = "update sys_attend_main set doc_status=1,doc_alter_time=?,fd_alter_record='系统重新生成有效记录，设置为无效' "
							+ "where fd_state is null and (doc_status=0 or doc_status is null ) and (fd_is_across=0 or fd_is_across is null) and doc_create_time >=? and doc_create_time < ? and "
							+ HQLUtil.buildLogicIN("doc_creator_id", groupLists.get(i));
					delete = conn.prepareStatement(sql);
					delete.setTimestamp(1, new Timestamp(new Date().getTime()));
					delete.setTimestamp(2, new Timestamp(AttendUtil.getDate(date, 0).getTime()));
					delete.setTimestamp(3, new Timestamp(AttendUtil.getDate(date, 1).getTime()));
					delete.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					JdbcUtils.closeStatement(delete);
				}
				try {
					// 跨天
					String sql = "update sys_attend_main set doc_status=1,doc_alter_time=?,fd_alter_record='系统重新生成有效记录，设置为无效' "
							+ "where fd_state is null and (doc_status=0 or doc_status is null ) and fd_is_across=? and doc_create_time >=? and doc_create_time < ? and "
							+ HQLUtil.buildLogicIN("doc_creator_id", groupLists.get(i));
					delete2 = conn.prepareStatement(sql);
					delete2.setTimestamp(1, new Timestamp(new Date().getTime()));
					delete2.setBoolean(2, true);
					delete2.setTimestamp(3, new Timestamp(AttendUtil.getDate(date, 1).getTime()));
					delete2.setTimestamp(4, new Timestamp(AttendUtil.getDate(date, 2).getTime()));
					delete2.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					JdbcUtils.closeStatement(delete2);
				}
			}
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage(), ex);
			conn.rollback();
			throw ex;
		} finally {
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(conn);
		}
		if (CollectionUtils.isNotEmpty(mainIdList)) {
			removeUnSignNotify(mainIdList);
		}
	}

	/**
	 * 异步清除待办
	 * 
	 * @param mainIds
	 *            有效考勤记录主文档ID
	 */
	@Override
	public void removeUnSignNotify(List<String> mainIds) {
		try {
			RemoveUnSignNotifyTask task = new RemoveUnSignNotifyTask(getSysAttendMainService(), mainIds);
			AttendThreadPoolManager manager = AttendThreadPoolManager.getInstance();
			if (!manager.isStarted()) {
				manager.start();
			}
			manager.submit(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class RemoveUnSignNotifyTask implements Runnable {
		ISysAttendMainService sysAttendMainService;
		List<String> mainIdList;

		public RemoveUnSignNotifyTask(ISysAttendMainService sysAttendMainService, List<String> mainIdList) {
			this.sysAttendMainService = sysAttendMainService;
			this.mainIdList = mainIdList;
		}

		@Override
		public void run() {
			if (CollectionUtils.isNotEmpty(mainIdList)) {
				try {
					// 异步去将待办设置为已办
					String[] ids = mainIdList.toArray(new String[0]);
					List<SysAttendMain> mainList = sysAttendMainService.findByPrimaryKeys(ids);
					if (CollectionUtils.isNotEmpty(mainList)) {
						for (SysAttendMain main : mainList) {
							setAttendNotifyToDone(main);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
