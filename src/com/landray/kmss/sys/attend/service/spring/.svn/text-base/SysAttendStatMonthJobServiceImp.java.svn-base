package com.landray.kmss.sys.attend.service.spring;

import com.google.common.collect.Lists;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.hr.config.service.IHrConfigOvertimeConfigService;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryExctime;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTime;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTimesheet;
import com.landray.kmss.sys.attend.model.SysAttendStat;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.ISysAttendStatMonthJobService;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendPersonUtil;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.time.model.SysTimeLeaveConfig;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeHolidayService;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.NumberUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.TransactionStatus;

import javax.sql.DataSource;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author linxiuxian
 *
 */
public class SysAttendStatMonthJobServiceImp
		implements ISysAttendStatMonthJobService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendStatMonthJobServiceImp.class);
	private static final Object lock = new Object();
	private ISysAttendCategoryService sysAttendCategoryService;

	private ISysTimeHolidayService sysTimeHolidayService;

	private IBaseDao baseDao =null;

	public IBaseDao getBaseDao() {
		if(baseDao ==null){
			baseDao =(IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		}
		return baseDao;
	}

	private ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
			.getBean("sysOrgCoreService");

	private IHrConfigOvertimeConfigService hrConfigOvertimeConfigService = (IHrConfigOvertimeConfigService) SpringBeanUtil
			.getBean("hrConfigOvertimeConfigService");

	@Override
	public void execute(SysQuartzJobContext jobContext) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 统计所有考勤组某个月
	 * 
	 * @param date
	 * @throws Exception
	 */
	@Override
	public void stat(Date date) throws Exception {
		Date beginTime = getMonth(date, 0);
		Date endTime = getMonth(date, 1);
		endTime = endTime.compareTo(new Date())>=0?AttendUtil.getDate(new Date(), 1):endTime;
		// 统计时间区间数据
		stat(null, beginTime, endTime, null, null);
	}

	/**
	 * 统计某个考勤组某个月
	 * 
	 * @param fdCategoryId
	 * @param date
	 * @throws Exception
	 */
	@Override
	public void stat(String fdCategoryId, Date date, List<String> orgList)
			throws Exception {
		Date beginTime = getMonth(date, 0);
		Date endTime = getMonth(date, 1);
		endTime = endTime.compareTo(new Date())>=0?AttendUtil.getDate(new Date(), 1):endTime;
		// 统计时间区间数据
		stat(null, beginTime, endTime, fdCategoryId, orgList);
	}

	@Override
	public void stat(String fdCategoryId, Date date) throws Exception {
		List<String> orgList = new ArrayList<String>();
		stat(fdCategoryId, date, orgList);
	}

	/**
	 * 统计某个人某个月
	 * 
	 * @param orgElement
	 * @param date
	 * @throws Exception
	 */
	@Override
	public void stat(SysOrgElement orgElement, Date date) throws Exception {
		logger.debug("SysAttendStatMonthJob:orgElement month start...");
		Date beginTime = getMonth(date, 0);
		Date endTime = getMonth(date, 1);
		endTime = endTime.compareTo(new Date())>=0?AttendUtil.getDate(new Date(), 1):endTime;
		List<String> orgList = new ArrayList<String>();
		orgList.add(orgElement.getFdId());
		List recordList = this.getStatRecord(orgList, beginTime, endTime);
		if (recordList.isEmpty()) {
			return;
		}
		//月度两次忘记工牌次数
		Map<String, Long> alreadyPatchNumberMap = alreadyPatchNumber(orgList, beginTime);

		// 统计每个用户信息
		Map<String, JSONObject> statMap = new HashMap<String, JSONObject>();
		Map<String, SysAttendCategory> cateMap = new HashMap<String, SysAttendCategory>();
		Map<String, SysOrgElement> userMap = new HashMap<String, SysOrgElement>();
		statUserInfo(recordList, statMap, cateMap, userMap);
		endTime = getMonth(date, 1);
		recalUserInfo(statMap, cateMap, userMap, beginTime, endTime);
		// 插入用户统计信息
		List<String> orgIdsList = new ArrayList(statMap.keySet());
		addBatch(statMap, getMonth(beginTime, 0),
				SysTimeUtil.getUserAuthAreaMap(orgIdsList),null);
		logger.debug("SysAttendStatMonthJob:orgElement month end...");
	}

	@Override
	public void stat(List<SysOrgElement> eleList, List<Date> monthList)
			throws Exception {
		for (Date month : monthList) {
			stat("", month);
		}

	}
	
	/**
	 * 统计某些人某个月
	 * @param eleList
	 * @param monthList
	 * @throws Exception
	 */
	@Override
	public void statMonth(List<String> eleList, List<Date> monthList)
			throws Exception {
		for (Date month : monthList) {
			stat("", month,eleList);
		}
	}

	/**
	 * 统计某个人某个时间段（从某天到某天）
	 * 
	 * @param orgElement
	 * @param beginTime
	 * @param endTime
	 * @throws Exception
	 */
	@Override
	public void stat(SysOrgElement orgElement, Date beginTime, Date endTime)
			throws Exception {
		logger.debug("SysAttendStatMonthJob:orgElement start...");
		// 1.用户列表
		List<String> orgList = new ArrayList<String>();
		List<String> tmpOrgList = new ArrayList<String>();
		tmpOrgList.add(orgElement.getFdId());
		orgList = AttendPersonUtil.expandToPersonIds(tmpOrgList);
		// 用户组分割
		if (orgList.isEmpty()) {
			return;
		}
		int maxCount = 500;
		List<List> groupLists = new ArrayList<List>();
		if (orgList.size() <= maxCount) {
			groupLists.add(orgList);
		} else {
			groupLists = AttendUtil.splitList(orgList, maxCount);
		}
		// 2.用户组的统计记录
		Date beginDate = AttendUtil.getDate(beginTime, 0);
		Date endDate = AttendUtil.getDate(endTime, 1);
		for (int i = 0; i < groupLists.size(); i++) {
			List tmpList = groupLists.get(i);
			List recordList = this.getStatRecord(tmpList, beginDate, endDate,orgElement,null);
			if (recordList.isEmpty()) {
				continue;
			}
			// 3.统计每个用户信息
			Map<String, JSONObject> statMap = new HashMap<String, JSONObject>();
			Map<String, SysAttendCategory> cateMap = new HashMap<String, SysAttendCategory>();
			Map<String, SysOrgElement> userMap = new HashMap<String, SysOrgElement>();
			statUserInfo(recordList, statMap, cateMap, userMap);
			recalUserInfo(statMap, cateMap, userMap, beginDate, endDate);
			// 4.插入用户统计信息
			addBatchPeriod(statMap, beginDate, endDate);
		}
		logger.debug("SysAttendStatMonthJob:orgElement end...");
	}

	/**
	 * 统计某个考勤组某个时间段（从某天到某天）
	 * 
	 * @param fdCategoryId
	 * @param beginTime
	 * @param endTime
	 * @throws Exception
	 */
	@Override
	public void stat(String fdCategoryId, Date beginTime, Date endTime)
			throws Exception {
		logger.debug("SysAttendStatMonthJob:fdCategoryId start...");
		// 1.用户列表
		List orgList = getStatUser(beginTime, endTime, fdCategoryId);
		if (orgList.isEmpty()) {
			return;
		}
		// 用户组分割
		int maxCount = 1000;
		List<List> groupLists = new ArrayList<List>();
		if (orgList.size() <= maxCount) {
			groupLists.add(orgList);
		} else {
			groupLists = AttendUtil.splitList(orgList, maxCount);
		}
		// 2.用户组的统计记录
		Date beginDate = AttendUtil.getDate(beginTime, 0);
		Date endDate = AttendUtil.getDate(endTime, 1);
		for (int i = 0; i < groupLists.size(); i++) {
			List tmpList = groupLists.get(i);
			List recordList = this.getStatRecord(tmpList, beginDate, endDate,null,fdCategoryId);
			if (recordList.isEmpty()) {
				continue;
			}
			// 3.统计每个用户信息
			Map<String, JSONObject> statMap = new HashMap<String, JSONObject>();
			Map<String, SysAttendCategory> cateMap = new HashMap<String, SysAttendCategory>();
			Map<String, SysOrgElement> userMap = new HashMap<String, SysOrgElement>();
			//计算出勤天数详情
			statUserInfo(recordList, statMap, cateMap, userMap);
			//先计算应出勤天数
			recalUserInfo(statMap, cateMap, userMap, beginDate, endDate);

			// 4.插入用户统计信息
			addBatchPeriod(statMap, beginDate, endDate);
		}
		logger.debug("SysAttendStatMonthJob:fdCategoryId end...");
	}

	/**
	 * 排班管理人员每日是按一天，还是半天算的缓存
	 */
	private Map<String,Double> elementWorkDayCatch =new HashMap<>();

	/**
	 * 统计每月数据
	 * @param jobContext
	 * @param beginTime
	 * @param endTime
	 * @param fdCategoryId
	 * @param personList
	 * @throws Exception
	 */
	private void stat(SysQuartzJobContext jobContext, Date beginTime,
			Date endTime, String fdCategoryId, List<String> personList)
			throws Exception {
		logger.debug("SysAttendStatMonthJob start,fdCategoryId:" + fdCategoryId
				+ ";personList:" + personList);
		// 1.用户列表
		List<String> orgList = new ArrayList<String>();
		if (StringUtil.isNotNull(fdCategoryId) || (StringUtil.isNull(fdCategoryId)&&personList==null)) {
			orgList = getStatUser(beginTime, endTime, fdCategoryId);
		}
		if (personList != null) {
			orgList.addAll(personList);
		}
		if (orgList.isEmpty()) {
			return;
		}
		// 用户组分割
		int maxCount = 1000;
		List<List> groupLists = new ArrayList<List>();
		if (orgList.size() <= maxCount) {
			groupLists.add(orgList);
		} else {
			groupLists = AttendUtil.splitList(orgList, maxCount);
		}
		// 2.用户组的统计记录
		for (int i = 0; i < groupLists.size(); i++) {
			List tmpList = groupLists.get(i);
			List recordList = this.getStatRecord(tmpList, beginTime, endTime);
			if (recordList.isEmpty()) {
				continue;
			}

			//月度两次忘记工牌次数
			Map<String, Long> alreadyPatchNumberMap = alreadyPatchNumber(orgList, beginTime);

			// 3.统计每个用户信息
			Map<String, JSONObject> statMap = new HashMap<String, JSONObject>();
			Map<String, SysAttendCategory> cateMap = new HashMap<String, SysAttendCategory>();
			Map<String, SysOrgElement> userMap = new HashMap<String, SysOrgElement>();
			statUserInfo(recordList, statMap, cateMap, userMap);
			endTime = getMonth(beginTime, 1);
			recalUserInfo(statMap, cateMap, userMap, beginTime, endTime);

			// 4.插入用户统计信息
			List<String> orgIdsList = new ArrayList(statMap.keySet());
			addBatch(statMap, getMonth(beginTime, 0),
					SysTimeUtil.getUserAuthAreaMap(orgIdsList),alreadyPatchNumberMap);
		}

		logger.debug("SysAttendStatMonthJob end...");
	}

	private long[] cutNum(long num1,long num2){
		long[] nums=new long[2];
		if(num1>=num2){//扣除平时调休
			num1-=num2;
			num2=0;
		}else{
			num2-=num1;
			num1=0;
		}
		nums[0]=num1;
		nums[1]=num2;
		return nums;
		
	}
	
	private void addBatch(Map<String, JSONObject> statMap, Date fdMonth,
			Map<String, String> areaMap,Map<String, Long> alreadyPatchNumberMap)
			throws Exception {
		synchronized (lock) {
			Date beginMonthTime = getMonth(fdMonth, 0);
			Date endMonthTime = getMonth(fdMonth, 1);
			endMonthTime = endMonthTime.compareTo(new Date())>=0?AttendUtil.getDate(new Date(), 1):endMonthTime;
			List orgList = getStatMonthUsers(beginMonthTime, endMonthTime);
			DataSource dataSource = (DataSource) SpringBeanUtil
					.getBean("dataSource");
			Connection conn = null;
			PreparedStatement insert = null;
			PreparedStatement update = null;
			try {
				conn = dataSource.getConnection();
				conn.setAutoCommit(false);

				update = conn.prepareStatement(getUpdateSql(false));
				insert = conn
						.prepareStatement(
								"insert into sys_attend_stat_month(fd_id,fd_month,fd_total_time,doc_create_time,fd_late_time,fd_left_time,fd_status,"
										+ "fd_late,fd_left,fd_missed,fd_absent,fd_outside,fd_trip,doc_creator_id,fd_status_days,fd_absent_days,fd_missed_count,fd_outside_count,"
										+ "fd_late_count,fd_left_count,fd_category_id,fd_should_days,fd_actual_days,fd_trip_days,fd_off_days,fd_over_time,fd_work_over_time,fd_off_over_time,fd_holiday_over_time,"
										+ "fd_missed_exc_count,fd_late_exc_count,fd_left_exc_count,fd_off_days_detail,doc_creator_hid,fd_absent_days_count,fd_outgoing_time,fd_off_time,fd_off_time_hour,fd_work_date_days,auth_area_id,"
										+ "fd_holidays,fd_outgoing_day,fd_leave_days,fd_personal_leave_days,"
										+ "fd_over_apply_time,fd_work_over_apply_time,fd_off_over_apply_time,fd_holiday_over_apply_time,"
										+ "fd_over_turn_apply_time,fd_work_over_turn_apply_time,fd_off_over_turn_apply_time,fd_holiday_over_turn_apply_time,"
										+ "fd_over_turn_time,fd_work_over_turn_time,fd_off_over_turn_time,fd_holiday_over_turn_time,"
										+ "fd_over_pay_apply_time,fd_work_over_pay_apply_time,fd_off_over_pay_apply_time,fd_holiday_over_pay_apply_time,"
										+ "fd_over_pay_time,fd_work_over_pay_time,fd_off_over_pay_time,fd_holiday_over_pay_time,"
										+ "fd_over_rest_time,fd_work_over_rest_time,fd_off_over_rest_time,fd_holiday_over_rest_time, "
										+ "fd_rest_turn_time,fd_work_rest_turn_time,fd_off_rest_turn_time,fd_holiday_rest_turn_time) "
										+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				boolean isInsert = false, isUpdate = false;
				for (String key : statMap.keySet()) {
					JSONObject json = statMap.get(key);
					long fdTotalTime = json.getLong("fdTotalTime");
					int fdLateTime = json.getInt("fdLateTime");
					int fdLeftTime = json.getInt("fdLeftTime");
					boolean fdStatus = json.getBoolean("fdStatus");
					boolean fdLate = json.getBoolean("fdLate");
					boolean fdLeft = json.getBoolean("fdLeft");
					boolean fdMissed = json.getBoolean("fdMissed");
					boolean fdAbsent = json.getBoolean("fdAbsent");
					boolean fdOutside = json.getBoolean("fdOutside");
					boolean fdTrip = json.getBoolean("fdTrip");

					int fdAbsentDays = json.getInt("fdAbsentDays");

					int fdMissedCount = json.getInt("fdMissedCount");
					int fdOutsideCount = json.getInt("fdOutsideCount");
					int fdLateCount = json.getInt("fdLateCount");
					int fdLeftCount = json.getInt("fdLeftCount");
					String fdCategoryId = json.getString("fdCategoryId");

					int fdHolidays = json.getInt("fdHolidays");
					//正常天数
					float fdStatusDays =0F;
					if(json.get("fdStatusDays") !=null) {
						fdStatusDays =Float.valueOf(json.get("fdStatusDays").toString());
					}

					//应出勤天
					float fdShouldDays =0F;
					if(json.get("fdShouldDays") !=null) {
						fdShouldDays =Float.valueOf(json.get("fdShouldDays").toString());
					}
					//实际出勤天
					float fdActualDays =0F;
					if(json.get("fdActualDays") !=null) {
						fdActualDays =Float.valueOf(json.get("fdActualDays").toString());
					}
					//请假明细
					JSONObject fdOffDaysDetail = json.getJSONObject("fdOffCountDetail");
					float fdAbsentDaysCount = (float) json.getDouble("fdAbsentDaysCount");
					//事假
					float personCount = 0f;
					if(fdOffDaysDetail.containsKey("2")){
						JSONObject fdPersonJSON = fdOffDaysDetail.getJSONObject("2");
						personCount = Float.valueOf(fdPersonJSON.get("count").toString());
					}
					//病假
					float sickCount = 0f;
					if(fdOffDaysDetail.containsKey("3")){
						JSONObject fdSickJSON = fdOffDaysDetail.getJSONObject("3");
						sickCount = Double.valueOf(fdSickJSON.getDouble("count")).floatValue();
					}

					//因为事假，病假都是以小时计算，所以直接获取小时数
					Float convertTime = SysTimeUtil.getConvertTime();
					DecimalFormat df = new DecimalFormat("##0.00");

//					Double count = Math.ceil((personCount + sickCount) * 100 / convertTime);
					float count1 = personCount / convertTime;
					float count2 = sickCount / convertTime;
					BigDecimal bigDecimal = new BigDecimal(count1);
					BigDecimal bigDecimal1 = new BigDecimal(count2);
					//实际出勤天数减去事假，病假天数，旷工天数
					float actualDay = fdActualDays - bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue()-bigDecimal1.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
					fdActualDays = actualDay > 0 ? actualDay : 0;

					//工作日天
					float fdWorkDateDays =0F;
					if(json.get("fdWorkDateDays") !=null) {
						fdWorkDateDays =Float.valueOf(json.get("fdWorkDateDays").toString());
					}

					float fdTripDays = (float) json.getDouble("fdTripDays");
					float fdOffDays = (float) json.getDouble("fdOffDays");

					int fdMissedExcCount = json.getInt("fdMissedExcCount");
					//修改月统计补卡同一天两次忘带工牌和工牌丢失 只算一次缺卡
					if(alreadyPatchNumberMap !=null && alreadyPatchNumberMap.get(key) !=null){
						fdMissedExcCount -=alreadyPatchNumberMap.get(key);
					}

					int fdLateExcCount = json.getInt("fdLateExcCount");
					int fdLeftExcCount = json.getInt("fdLeftExcCount");
					String docCreatorHId = json
							.containsKey("docCreatorHId")
									? json.getString("docCreatorHId") : null;
					float fdPersonalLeaveDays = (float)json.getDouble("fdPersonalLeaveDays");

					float fdOutgoingTime = (float) json
							.getDouble("fdOutgoingTime");
					float fdOffTime = (float) json.getDouble("fdOffTime");
					//外出，按天统计
					float fdOutgoingDay =json.containsKey("fdOutgoingDay")?(float)json.getDouble("fdOutgoingDay"):0F;
					//请假，按天统计
					float fdLeaveDays =json.containsKey("fdLeaveDays")?(float)json.getDouble("fdLeaveDays"):0F;
					long fdOverTime = 0;
					//加班实际时长
					try{
						fdOverTime = json.getLong("fdOverTime");
					}catch(Exception e){
						if(json.get("fdOverTime")!=null){
							fdOverTime = Long.parseLong((String) json.get("fdOverTime"));
							logger.info(json.get("fdOverTime").toString());
							logger.info(json.get("fdOverTime").getClass().toString());
						}
						
					}
					long fdWorkOverTime = json.containsKey("fdWorkOverTime") ? json.getLong("fdWorkOverTime") : 0;
					long fdOffOverTime = json.containsKey("fdOffOverTime") ? json.getLong("fdOffOverTime") : 0;
					long fdHolidayOverTime = json.containsKey("fdHolidayOverPayTime") ? json.getLong("fdHolidayOverPayTime") : 0;
//					long fdHolidayOverTime = json.containsKey("fdHolidayOverTime") ? json.getLong("fdHolidayOverTime") : 0;
					//加班申请时长
					long fdOverApplyTime = json.containsKey("fdOverApplyTime")? json.getLong("fdOverApplyTime"):0;
					long fdWorkOverApplyTime = json.containsKey("fdWorkOverApplyTime") ? json.getLong("fdWorkOverApplyTime") : 0;
					long fdOffOverApplyTime = json.containsKey("fdOffOverApplyTime") ? json.getLong("fdOffOverApplyTime") : 0;
					long fdHolidayOverApplyTime = json.containsKey("fdHolidayOverApplyTime") ? json.getLong("fdHolidayOverApplyTime") : 0;
					//加班转调休申请时长
					long fdOverTurnApplyTime = json.containsKey("fdOverTurnApplyTime")? json.getLong("fdOverTurnApplyTime"):0;
					long fdWorkOverTurnApplyTime = json.containsKey("fdWorkOverTurnApplyTime") ? json.getLong("fdWorkOverTurnApplyTime") : 0;
					long fdOffOverTurnApplyTime = json.containsKey("fdOffOverTurnApplyTime") ? json.getLong("fdOffOverTurnApplyTime") : 0;
					long fdHolidayOverTurnApplyTime = json.containsKey("fdHolidayOverTurnApplyTime") ? json.getLong("fdHolidayOverTurnApplyTime") : 0;
					//加班转调休实际时长
					long fdOverTurnTime = json.containsKey("fdOverTurnTime")? json.getLong("fdOverTurnTime"):0;
					long fdWorkOverTurnTime = json.containsKey("fdWorkOverTurnTime") ? json.getLong("fdWorkOverTurnTime") : 0;
					long fdOffOverTurnTime = json.containsKey("fdOffOverTurnTime") ? json.getLong("fdOffOverTurnTime") : 0;
					long fdHolidayOverTurnTime = json.containsKey("fdHolidayOverTurnTime") ? json.getLong("fdHolidayOverTurnTime") : 0;
					//加班加班费申请时长
					long fdOverPayApplyTime = json.containsKey("fdOverPayApplyTime")? json.getLong("fdOverPayApplyTime"):0;
					long fdWorkOverPayApplyTime = json.containsKey("fdWorkOverPayApplyTime") ? json.getLong("fdWorkOverPayApplyTime") : 0;
					long fdOffOverPayApplyTime = json.containsKey("fdOffOverPayApplyTime") ? json.getLong("fdOffOverPayApplyTime") : 0;
					long fdHolidayOverPayApplyTime = json.containsKey("fdHolidayOverPayApplyTime") ? json.getLong("fdHolidayOverPayApplyTime") : 0;
					//加班加班费时长
					long fdOverPayTime = json.containsKey("fdOverPayTime")? json.getLong("fdOverPayTime"):0;
					long fdWorkOverPayTime = json.containsKey("fdWorkOverPayTime") ? json.getLong("fdWorkOverPayTime") : 0;
					long fdOffOverPayTime = json.containsKey("fdOffOverPayTime") ? json.getLong("fdOffOverPayTime") : 0;
					long fdHolidayOverPayTime = json.containsKey("fdHolidayOverPayTime") ? json.getLong("fdHolidayOverPayTime") : 0;
					//加班结转时长
					SysAttendCategory sysAttendCategory = CategoryUtil.getCategoryById(fdCategoryId);
					long fdOverRestTime = 0;
					long fdWorkOverRestTime = 0;
					long fdOffOverRestTime = 0;
					long fdHolidayOverRestTime = fdHolidayOverTime;
					//结转调休
					long fdRestTurnTime = 0;
					long fdWorkRestTurnTime = 0;
					long fdOffRestTurnTime = 0;
					long fdHolidayRestTurnTime =fdHolidayOverTurnTime;
					if(sysAttendCategory != null&&!sysAttendCategory.getFdName().contains("周末")) {
						fdWorkOverRestTime = fdWorkOverPayTime;
						 fdOffOverRestTime = fdOffOverPayTime;
						 
						 fdWorkRestTurnTime = fdWorkOverTurnTime;
						 fdOffRestTurnTime = fdOffOverTurnTime;
						//加班结转逻辑
						//排班班次小于8小时的，需补足每天8小时的工作时间以后再计算加班，
						//即加班结转小时数=实际加班小时数-应出勤天数*（8-班次上班时长）。
						//优先扣除平日加班小时，再扣除周末加班小时，节假日加班不扣减
						//总工时
						Float fdTotal = sysAttendCategory.getFdTotalTime() == null ? 8f : sysAttendCategory.getFdTotalTime();
						if (8 > fdTotal) {//排班班次小于8小时的，需补足每天8小时的工作时间以后再计算加班，
							//应抵扣的加班时长
							long fdKouJianTime = (long) (fdShouldDays * (8 - fdTotal) * 60);
							//比如员工标准工作时长7.5小时，本月应出勤天数为22天，那么应抵扣的加班小时为11小时。
//							如果平时调休小时3小时，周末调休小时5小时，平时加班10小时，周末加班20小时，
//							那么先扣除平时调休3小时，再扣除周末调休5小时，再扣除平时加班3小时，
//							最后员工的调休结转小时数为平时0、周末0，发定0，加班费结转小时为平时7，周末20，法定0
							long[] nums=cutNum(fdKouJianTime, fdWorkRestTurnTime);//扣除平时调休
							fdKouJianTime=nums[0];
							fdWorkRestTurnTime=nums[1];
							
							nums=cutNum(fdKouJianTime, fdOffRestTurnTime);//扣除周末调休
							fdKouJianTime=nums[0];
							fdOffRestTurnTime=nums[1];
							
							nums=cutNum(fdKouJianTime, fdWorkOverRestTime);//扣除平时加班
							fdKouJianTime=nums[0];
							fdWorkOverRestTime=nums[1];
							
							nums=cutNum(fdKouJianTime, fdOffOverRestTime);//扣除周末加班
							fdKouJianTime=nums[0];
							fdOffOverRestTime=nums[1];
							
//							//加班结转小时数=实际加班小时数-应出勤天数*（8-班次上班时长）
//							fdOverRestTime = fdOverTime > fdKouJianTime ? fdOverTime - fdKouJianTime : 0;
//							//工作日结转时长
//							fdWorkOverRestTime = fdWorkOverTime > fdKouJianTime ? fdWorkOverTime - fdKouJianTime : 0;
//							//休息日结转时长
//							Long fdWorkRestTime = fdKouJianTime - fdWorkOverTime;
//							if (fdWorkRestTime > 0) {
//								fdOffOverRestTime = fdOffOverTime - fdWorkRestTime > 0 ? fdOffOverTime - fdWorkRestTime : 0;
//							}
						}
						fdRestTurnTime=fdWorkRestTurnTime+fdOffRestTurnTime+fdHolidayRestTurnTime;
						fdOverRestTime=fdWorkOverRestTime+fdOffOverRestTime+fdHolidayOverRestTime;
					}
					// 判断是否已统计
					if (orgList != null && orgList.contains(key)) {
						update.setLong(1, fdTotalTime);
						update.setInt(2, fdLateTime);
						update.setInt(3, fdLeftTime);
						update.setBoolean(4, fdStatus);
						update.setBoolean(5, fdLate);
						update.setBoolean(6, fdLeft);
						update.setBoolean(7, fdMissed);
						update.setBoolean(8, fdAbsent);
						update.setBoolean(9, fdOutside);
						update.setBoolean(10, fdTrip);
						update.setFloat(11, fdStatusDays);
						update.setInt(12, fdAbsentDays);
						update.setInt(13, fdMissedCount);
						update.setInt(14, fdOutsideCount);
						update.setInt(15, fdLateCount);
						update.setInt(16, fdLeftCount);
						update.setString(17, fdCategoryId);
						update.setFloat(18, fdShouldDays);
						update.setFloat(19, fdActualDays);
						update.setFloat(20, fdTripDays);
						update.setFloat(21, fdOffDays);
						update.setLong(22, fdOverTime);
						update.setLong(23, fdWorkOverTime);
						update.setLong(24, fdOffOverTime);
						update.setLong(25, fdHolidayOverTime);
						update.setLong(26, fdMissedExcCount);
						update.setLong(27, fdLateExcCount);
						update.setLong(28, fdLeftExcCount);
						update.setString(29, fdOffDaysDetail.isEmpty() ? null : fdOffDaysDetail.toString());
						update.setString(30, docCreatorHId);
						update.setFloat(31, fdAbsentDaysCount);
						update.setFloat(32, fdOutgoingTime);
						update.setInt(33, (int) fdOffTime);
						update.setFloat(34, fdOffTime);
						update.setFloat(35, fdWorkDateDays);
						update.setInt(36, fdHolidays);
						update.setFloat(37, fdOutgoingDay);
						update.setFloat(38, fdLeaveDays);
						update.setFloat(39, fdPersonalLeaveDays);

						update.setLong(40, fdOverApplyTime);
						update.setLong(41, fdWorkOverApplyTime);
						update.setLong(42, fdOffOverApplyTime);
						update.setLong(43, fdHolidayOverApplyTime);

						update.setLong(44, fdOverTurnApplyTime);
						update.setLong(45, fdWorkOverTurnApplyTime);
						update.setLong(46, fdOffOverTurnApplyTime);
						update.setLong(47, fdHolidayOverTurnApplyTime);

						update.setLong(48, fdOverTurnTime);
						update.setLong(49, fdWorkOverTurnTime);
						update.setLong(50, fdOffOverTurnTime);
						update.setLong(51, fdHolidayOverTurnTime);

						update.setLong(52, fdOverPayApplyTime);
						update.setLong(53, fdWorkOverPayApplyTime);
						update.setLong(54, fdOffOverPayApplyTime);
						update.setLong(55, fdHolidayOverPayApplyTime);

						update.setLong(56, fdOverPayTime);
						update.setLong(57, fdWorkOverPayTime);
						update.setLong(58, fdOffOverPayTime);
						update.setLong(59, fdHolidayOverPayTime);
						//加班结转时长
						update.setLong(60, fdOverRestTime);
						update.setLong(61, fdWorkOverRestTime);
						update.setLong(62, fdOffOverRestTime);
						update.setLong(63, fdHolidayOverRestTime);
						//结转调休
						update.setLong(64, fdRestTurnTime);
						update.setLong(65, fdWorkRestTurnTime);
						update.setLong(66, fdOffRestTurnTime);
						update.setLong(67, fdHolidayRestTurnTime);

						update.setTimestamp(68, new Timestamp(beginMonthTime.getTime()));
						update.setTimestamp(69, new Timestamp(endMonthTime.getTime()));
						update.setString(70, key);


						update.addBatch();
						isUpdate = true;
					} else {
						String fdId = IDGenerator.generateID();
						insert.setString(1, fdId);
						insert.setTimestamp(2, new Timestamp(beginMonthTime.getTime()));
						insert.setLong(3, fdTotalTime);
						insert.setTimestamp(4, new Timestamp(new Date().getTime()));
						insert.setInt(5, fdLateTime);
						insert.setInt(6, fdLeftTime);
						insert.setBoolean(7, fdStatus);
						insert.setBoolean(8, fdLate);
						insert.setBoolean(9, fdLeft);
						insert.setBoolean(10, fdMissed);
						insert.setBoolean(11, fdAbsent);
						insert.setBoolean(12, fdOutside);
						insert.setBoolean(13, fdTrip);
						insert.setString(14, key);
						insert.setFloat(15, fdStatusDays);
						insert.setInt(16, fdAbsentDays);
						insert.setInt(17, fdMissedCount);
						insert.setInt(18, fdOutsideCount);
						insert.setInt(19, fdLateCount);
						insert.setInt(20, fdLeftCount);
						insert.setString(21, fdCategoryId);
						insert.setFloat(22, fdShouldDays);
						insert.setFloat(23, fdActualDays);
						insert.setFloat(24, fdTripDays);
						insert.setFloat(25, fdOffDays);
						insert.setLong(26, fdOverTime);
						insert.setLong(27, fdWorkOverTime);
						insert.setLong(28, fdOffOverTime);
						insert.setLong(29, fdHolidayOverTime);
						insert.setInt(30, fdMissedExcCount);
						insert.setInt(31, fdLateExcCount);
						insert.setInt(32, fdLeftExcCount);
						insert.setString(33, fdOffDaysDetail.isEmpty() ? null : fdOffDaysDetail.toString());
						insert.setString(34, docCreatorHId);
						insert.setFloat(35, fdAbsentDaysCount);
						insert.setFloat(36, fdOutgoingTime);
						insert.setInt(37, (int) fdOffTime);
						insert.setFloat(38, fdOffTime);
						insert.setFloat(39, fdWorkDateDays);
						insert.setString(40, areaMap.get(key));
						insert.setInt(41, fdHolidays);
						insert.setFloat(42, fdOutgoingDay);
						insert.setFloat(43, fdLeaveDays);
						insert.setFloat(44, fdPersonalLeaveDays);

						insert.setLong(45, fdOverApplyTime);
						insert.setLong(46, fdWorkOverApplyTime);
						insert.setLong(47, fdOffOverApplyTime);
						insert.setLong(48, fdHolidayOverApplyTime);

						insert.setLong(49, fdOverTurnApplyTime);
						insert.setLong(50, fdWorkOverTurnApplyTime);
						insert.setLong(51, fdOffOverTurnApplyTime);
						insert.setLong(52, fdHolidayOverTurnApplyTime);

						insert.setLong(53, fdOverTurnTime);
						insert.setLong(54, fdWorkOverTurnTime);
						insert.setLong(55, fdOffOverTurnTime);
						insert.setLong(56, fdHolidayOverTurnTime);

						insert.setLong(57, fdOverPayApplyTime);
						insert.setLong(58, fdWorkOverPayApplyTime);
						insert.setLong(59, fdOffOverPayApplyTime);
						insert.setLong(60, fdHolidayOverPayApplyTime);

						insert.setLong(61, fdOverPayTime);
						insert.setLong(62, fdWorkOverPayTime);
						insert.setLong(63, fdOffOverPayTime);
						insert.setLong(64, fdHolidayOverPayTime);

						insert.setLong(65, fdOverRestTime);
						insert.setLong(66, fdWorkOverRestTime);
						insert.setLong(67, fdOffOverRestTime);
						insert.setLong(68, fdHolidayOverRestTime);
						
						insert.setLong(69, fdRestTurnTime);
						insert.setLong(70, fdWorkRestTurnTime);
						insert.setLong(71, fdOffRestTurnTime);
						insert.setLong(72, fdHolidayRestTurnTime);

						insert.addBatch();
						isInsert = true;
					}
				}
				if (isUpdate) {
					update.executeBatch();
				}
				if (isInsert){
					insert.executeBatch();
				}
				conn.commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("月份统计失败:" + ex.getMessage(), ex);
				conn.rollback();
				throw ex;
			} finally {
				JdbcUtils.closeStatement(update);
				JdbcUtils.closeStatement(insert);
				JdbcUtils.closeConnection(conn);
			}
		}
	}

	private List getStatPeriodList(Date beginTime,Date endTime){

		List orgList = null;
		TransactionStatus status= null;
		boolean isExpcetion =false;
		try {
			status = TransactionUtils.beginNewReadTransaction();
			String monthSql = "select DISTINCT doc_creator_id from sys_attend_stat_period "
					+ "where fd_start_time>=:startFirst and fd_start_time<:startLast "
					+ "and fd_end_time>=:endFirst and fd_end_time<:endLast";
			orgList = getBaseDao().getHibernateSession().createNativeQuery(monthSql).setTimestamp("startFirst", new Timestamp(beginTime.getTime())).setTimestamp("startLast", new Timestamp(
					AttendUtil.getDate(beginTime, 1).getTime()))
					.setTimestamp("endFirst", new Timestamp(
							AttendUtil.getDate(endTime, -1).getTime()))
					.setTimestamp("endLast", new Timestamp(endTime.getTime()))
					.list();
		} catch (Exception e) {
			isExpcetion =true;
			e.printStackTrace();
		} finally {
			if(isExpcetion && status !=null){
				TransactionUtils.rollback(status);
			}else if(status !=null){
				TransactionUtils.commit(status);
			}
		}
		return orgList;
	}

	private String getUpdateSql(boolean isToday) {
		String uSql = "update sys_attend_stat_month set fd_total_time=:fdTotalTime,fd_late_time=:fdLateTime,fd_left_time=:fdLeftTime,fd_status=?,"
				+ "fd_late=?,fd_left=?,fd_missed=?,fd_absent=?,fd_outside=?,fd_trip=?,fd_status_days=:fdStatusDays,fd_absent_days=:fdAbsentDays,"
				+ "fd_missed_count=:fdMissedCount,fd_outside_count=:fdOutsideCount,fd_late_count=:fdLateCount,fd_left_count=:fdLeftCount,fd_category_id=?,"
				+ "fd_should_days=?,fd_actual_days=?,fd_trip_days=?,fd_off_days=?,"
				+ "fd_over_time=:fdOverTime,fd_work_over_time=:fdWorkOverTime,fd_off_over_time=:fdOffOverTime,fd_holiday_over_time=:fdHolidayOverTime,"
				+ "fd_missed_exc_count=:fdMissedExcCount,fd_late_exc_count=:fdLateExcCount,fd_left_exc_count=:fdLeftExcCount,fd_off_days_detail=?,"
				+ "doc_creator_hid=?,fd_absent_days_count=:fdAbsDaysCount,fd_outgoing_time=:fdOutgoingTime,fd_off_time=:fdOffTime,fd_off_time_hour=:offTimeHour,"
				+ "fd_work_date_days=?,fd_holidays=?, fd_outgoing_day=? ,fd_leave_days =? ,fd_personal_leave_days=?,"
				+ "fd_over_apply_time=?,fd_work_over_apply_time=?,fd_off_over_apply_time=?,fd_holiday_over_apply_time=?,"
				+ "fd_over_turn_apply_time=?,fd_work_over_turn_apply_time=?,fd_off_over_turn_apply_time=?,fd_holiday_over_turn_apply_time=?,"
				+ "fd_over_turn_time=?,fd_work_over_turn_time=?,fd_off_over_turn_time=?,fd_holiday_over_turn_time=?,"
				+ "fd_over_pay_apply_time=?,fd_work_over_pay_apply_time=?,fd_off_over_pay_apply_time=?,fd_holiday_over_pay_apply_time=?,"
				+ "fd_over_pay_time=?,fd_work_over_pay_time=?,fd_off_over_pay_time=?,fd_holiday_over_pay_time=?,"
				+ "fd_over_rest_time=?,fd_work_over_rest_time=?,fd_off_over_rest_time=?,fd_holiday_over_rest_time=?,"
				+ "fd_rest_turn_time=?,fd_work_rest_turn_time=?,fd_off_rest_turn_time=?,fd_holiday_rest_turn_time=? "
				+ "where fd_month >=? and fd_month<? and doc_creator_id =?";
		if (isToday) {
			// uSql = uSql.replace(":fdTotalTime", "fd_total_time+?")
			// .replace(":fdLateTime", "fd_late_time+?")
			// .replace(":fdLeftTime", "fd_left_time+?")
			// .replace(":fdStatusDays", "fd_status_days+?")
			// .replace(":fdAbsentDays", "fd_absent_days+?")
			// .replace(":fdMissedCount", "fd_missed_count+?")
			// .replace(":fdOutsideCount", "fd_outside_count+?")
			// .replace(":fdLateCount", "fd_late_count+?")
			// .replace(":fdLeftCount", "fd_left_count+?")
			// .replace(":fdOverTime", "fd_over_time+?")
			// .replace(":fdWorkOverTime", "fd_work_over_time+?")
			// .replace(":fdOffOverTime", "fd_off_over_time+?")
			// .replace(":fdHolidayOverTime", "fd_holiday_over_time+?")
			// .replace(":fdMissedExcCount", "fd_missed_exc_count+?")
			// .replace(":fdLateExcCount", "fd_late_exc_count+?")
			// .replace(":fdLeftExcCount", "fd_left_exc_count+?")
			// .replace(":fdAbsDaysCount", "fd_absent_days_count+?")
			// .replace(":fdOutgoingTime", "fd_outgoing_time+?")
			// .replace(":fdOffTime", "fd_off_time+?")
			// .replace(":offTimeHour", "fd_off_time_hour+?");
		} else {
			uSql = uSql.replace(":fdTotalTime", "?").replace(":fdLateTime", "?")
					.replace(":fdLeftTime", "?")
					.replace(":fdStatusDays", "?").replace(":fdAbsentDays", "?")
					.replace(":fdMissedCount", "?")
					.replace(":fdOutsideCount", "?")
					.replace(":fdLateCount", "?").replace(":fdLeftCount", "?")
					.replace(":fdOverTime", "?").replace(":fdWorkOverTime", "?")
					.replace(":fdOffOverTime", "?")
					.replace(":fdHolidayOverTime", "?")
					.replace(":fdMissedExcCount", "?")
					.replace(":fdLateExcCount", "?")
					.replace(":fdLeftExcCount", "?")
					.replace(":fdAbsDaysCount", "?")
					.replace(":fdPersonalLeaveDays", "?")
					.replace(":fdOutgoingTime", "?")
					.replace(":fdOffTime", "?")
					.replace(":offTimeHour", "?");
		}
		return uSql;
	}

	private void addBatchPeriod(Map<String, JSONObject> statMap, Date beginTime,
			Date endTime)
			throws Exception {
		List orgList = getStatPeriodList(beginTime,endTime);
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement insert = null;
		PreparedStatement update = null;

		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			update = conn.prepareStatement(
					"update sys_attend_stat_period set fd_total_time=?,fd_late_time=?,fd_left_time=?,fd_status=?,"
							+ "fd_late=?,fd_left=?,fd_missed=?,fd_absent=?,fd_outside=?,fd_trip=?,fd_status_days=?,fd_absent_days=?,"
							+ "fd_missed_count=?,fd_outside_count=?,fd_late_count=?,fd_left_count=?,fd_category_id=?,"
							+ "fd_should_days=?,fd_actual_days=?,fd_trip_days=?,fd_off_days=?,"
							+ "fd_over_time=?,fd_work_over_time=?,fd_off_over_time=?,fd_holiday_over_time=?,"
							+ "fd_missed_exc_count=?,fd_late_exc_count=?,fd_left_exc_count=?,fd_off_days_detail=?,doc_creator_hid=?,fd_absent_days_count=?,fd_outgoing_time=?,fd_off_time=?,fd_off_time_hour=?, "
							+ "fd_work_date_days=?,fd_holidays=? ,fd_outgoing_day=?,fd_leave_days=?,fd_personal_leave_days=?"
							+ "where fd_start_time>=? and fd_start_time<? and fd_end_time>=? and fd_end_time<? and doc_creator_id =?");
			insert = conn
					.prepareStatement(
							"insert into sys_attend_stat_period(fd_id,fd_start_time,fd_end_time,fd_total_time,doc_create_time,fd_late_time,fd_left_time,fd_status,"
									+ "fd_late,fd_left,fd_missed,fd_absent,fd_outside,fd_trip,doc_creator_id,fd_status_days,fd_absent_days,fd_missed_count,fd_outside_count,"
									+ "fd_late_count,fd_left_count,fd_category_id,fd_should_days,fd_actual_days,fd_trip_days,fd_off_days,fd_over_time,fd_work_over_time,fd_off_over_time,fd_holiday_over_time,"
									+ "fd_missed_exc_count,fd_late_exc_count,fd_left_exc_count,fd_off_days_detail,doc_creator_hid,fd_absent_days_count,fd_outgoing_time,fd_off_time,fd_off_time_hour,fd_work_date_days,fd_holidays,fd_outgoing_day,fd_leave_days,fd_personal_leave_days) "
									+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			boolean isInsert = false, isUpdate = false;
			for (String key : statMap.keySet()) {
				JSONObject json = statMap.get(key);
				long fdTotalTime = json.getLong("fdTotalTime");
				int fdLateTime = json.getInt("fdLateTime");
				int fdLeftTime = json.getInt("fdLeftTime");
				boolean fdStatus = json.getBoolean("fdStatus");
				boolean fdLate = json.getBoolean("fdLate");
				boolean fdLeft = json.getBoolean("fdLeft");
				boolean fdMissed = json.getBoolean("fdMissed");
				boolean fdAbsent = json.getBoolean("fdAbsent");
				boolean fdOutside = json.getBoolean("fdOutside");
				boolean fdTrip = json.getBoolean("fdTrip");

				int fdAbsentDays = json.getInt("fdAbsentDays");

				int fdMissedCount = json.getInt("fdMissedCount");
				int fdOutsideCount = json.getInt("fdOutsideCount");
				int fdLateCount = json.getInt("fdLateCount");
				int fdLeftCount = json.getInt("fdLeftCount");
				String fdCategoryId = json.getString("fdCategoryId");

				int fdHolidays = json.getInt("fdHolidays");

				//正常天数
				float fdStatusDays =0F;
				if(json.get("fdStatusDays") !=null) {
					fdStatusDays =Float.valueOf(json.get("fdStatusDays").toString());
				}
				//应出勤天
				float fdShouldDays =0F;
				if(json.get("fdShouldDays") !=null) {
					fdShouldDays =Float.valueOf(json.get("fdShouldDays").toString());
				}
				//实际出勤天
				float fdActualDays =0F;
				if(json.get("fdActualDays") !=null) {
					fdActualDays =Float.valueOf(json.get("fdActualDays").toString());
				}
				//工作日天
				float fdWorkDateDays =0F;
				if(json.get("fdWorkDateDays") !=null) {
					fdWorkDateDays =Float.valueOf(json.get("fdWorkDateDays").toString());
				}

				float fdTripDays = (float) json.getDouble("fdTripDays");
				float fdOffDays = (float) json.getDouble("fdOffDays");
				long fdOverTime = json.getLong("fdOverTime");
				long fdWorkOverTime = json.containsKey("fdWorkOverTime")
						? json.getLong("fdWorkOverTime") : 0;
				long fdOffOverTime = json.containsKey("fdOffOverTime")
						? json.getLong("fdOffOverTime") : 0;
				long fdHolidayOverTime = json.containsKey("fdHolidayOverTime")
						? json.getLong("fdHolidayOverTime") : 0;
				int fdMissedExcCount = json.getInt("fdMissedExcCount");
				int fdLateExcCount = json.getInt("fdLateExcCount");
				int fdLeftExcCount = json.getInt("fdLeftExcCount");
				JSONObject fdOffDaysDetail = json
						.getJSONObject("fdOffCountDetail");
				String docCreatorHId = json
						.containsKey("docCreatorHId")
								? json.getString("docCreatorHId") : null;
				float fdAbsentDaysCount = (float) json
						.getDouble("fdAbsentDaysCount");
				float fdPersonalLeaveDays = (float) json.getDouble("fdPersonalLeaveDays");
				float fdOutgoingTime = (float) json.getDouble("fdOutgoingTime");
				float fdOffTime = (float) json.getDouble("fdOffTime");
				//外出，按天统计
				float fdOutgoingDay =json.containsKey("fdOutgoingDay")?(float)json.getDouble("fdOutgoingDay"):0F;
				//请假，按天统计
				float fdLeaveDays =json.containsKey("fdLeaveDays")?(float)json.getDouble("fdLeaveDays"):0F;
				// 判断是否已统计
				if (orgList != null && orgList.contains(key)) {
					update.setLong(1, fdTotalTime);
					update.setInt(2, fdLateTime);
					update.setInt(3, fdLeftTime);
					update.setBoolean(4, fdStatus);
					update.setBoolean(5, fdLate);
					update.setBoolean(6, fdLeft);
					update.setBoolean(7, fdMissed);
					update.setBoolean(8, fdAbsent);
					update.setBoolean(9, fdOutside);
					update.setBoolean(10, fdTrip);
					update.setFloat(11, fdStatusDays);
					update.setInt(12, fdAbsentDays);
					update.setInt(13, fdMissedCount);
					update.setInt(14, fdOutsideCount);
					update.setInt(15, fdLateCount);
					update.setInt(16, fdLeftCount);
					update.setString(17, fdCategoryId);
					update.setFloat(18, fdShouldDays);
					update.setFloat(19, fdActualDays);
					update.setFloat(20, fdTripDays);
					update.setFloat(21, fdOffDays);
					update.setLong(22, fdOverTime);
					update.setLong(23, fdWorkOverTime);
					update.setLong(24, fdOffOverTime);
					update.setLong(25, fdHolidayOverTime);
					update.setLong(26, fdMissedExcCount);
					update.setLong(27, fdLateExcCount);
					update.setLong(28, fdLeftExcCount);
					update.setString(29, fdOffDaysDetail.isEmpty() ? null
							: fdOffDaysDetail.toString());
					update.setString(30, docCreatorHId);
					update.setFloat(31, fdAbsentDaysCount);
					update.setFloat(32, fdOutgoingTime);
					update.setInt(33, (int) fdOffTime);
					update.setFloat(34, fdOffTime);
					update.setFloat(35, fdWorkDateDays);
					update.setInt(36, fdHolidays);
					update.setFloat(37, fdOutgoingDay);
					update.setFloat(38, fdLeaveDays);
					update.setFloat(39, fdPersonalLeaveDays);
					update.setTimestamp(40,
							new Timestamp(beginTime.getTime()));
					update.setTimestamp(41, new Timestamp(
							AttendUtil.getDate(beginTime, 1).getTime()));
					update.setTimestamp(42, new Timestamp(
							AttendUtil.getDate(endTime, -1).getTime()));
					update.setTimestamp(43,
							new Timestamp(endTime.getTime()));
					update.setString(44, key);
					update.addBatch();
					isUpdate = true;
				} else {
					String fdId = IDGenerator.generateID();
					insert.setString(1, fdId);
					insert.setTimestamp(2, new Timestamp(
							AttendUtil.getDate(beginTime, 0).getTime()));
					insert.setTimestamp(3, new Timestamp(
							AttendUtil.getDate(endTime, -1).getTime()));
					insert.setLong(4, fdTotalTime);
					insert.setTimestamp(5, new Timestamp(new Date().getTime()));
					insert.setInt(6, fdLateTime);
					insert.setInt(7, fdLeftTime);
					insert.setBoolean(8, fdStatus);
					insert.setBoolean(9, fdLate);
					insert.setBoolean(10, fdLeft);
					insert.setBoolean(11, fdMissed);
					insert.setBoolean(12, fdAbsent);
					insert.setBoolean(13, fdOutside);
					insert.setBoolean(14, fdTrip);
					insert.setString(15, key);
					insert.setFloat(16, fdStatusDays);
					insert.setInt(17, fdAbsentDays);
					insert.setInt(18, fdMissedCount);
					insert.setInt(19, fdOutsideCount);
					insert.setInt(20, fdLateCount);
					insert.setInt(21, fdLeftCount);
					insert.setString(22, fdCategoryId);
					insert.setFloat(23, fdShouldDays);
					insert.setFloat(24, fdActualDays);
					insert.setFloat(25, fdTripDays);
					insert.setFloat(26, fdOffDays);
					insert.setLong(27, fdOverTime);
					insert.setLong(28, fdWorkOverTime);
					insert.setLong(29, fdOffOverTime);
					insert.setLong(30, fdHolidayOverTime);
					insert.setInt(31, fdMissedExcCount);
					insert.setInt(32, fdLateExcCount);
					insert.setInt(33, fdLeftExcCount);
					insert.setString(34, fdOffDaysDetail.isEmpty() ? null
							: fdOffDaysDetail.toString());
					insert.setString(35, docCreatorHId);
					insert.setFloat(36, fdAbsentDaysCount);
					insert.setFloat(37, fdOutgoingTime);
					insert.setInt(38, (int) fdOffTime);
					insert.setFloat(39, fdOffTime);
					insert.setFloat(40, fdWorkDateDays);
					insert.setInt(41, fdHolidays);
					insert.setFloat(42, fdOutgoingDay);
					insert.setFloat(43, fdLeaveDays);
					insert.setFloat(44, fdPersonalLeaveDays);
					insert.addBatch();
					isInsert = true;
				}
			}
			if (isUpdate) {
				update.executeBatch();
			}
			if (isInsert) {
				insert.executeBatch();
			}
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("生成日期区间统计数据出错:" + ex.getMessage(), ex);
			conn.rollback();
			throw ex;
		} finally {
			JdbcUtils.closeStatement(update);
			JdbcUtils.closeStatement(insert);
			JdbcUtils.closeConnection(conn);
		}

	}

	private void statUserInfo(List<SysAttendStat> recordList,
			Map<String, JSONObject> statMap,
			Map<String, SysAttendCategory> cateMap,
			Map<String, SysOrgElement> userMap) throws Exception {
		elementWorkDayCatch =new HashMap<>();
		Map<String, SysTimeLeaveRule> leaveRuleMap = new HashMap<String, SysTimeLeaveRule>();
		SysTimeLeaveConfig leaveConfig = null;
		try {
			leaveConfig = new SysTimeLeaveConfig();
		} catch (Exception e) {
			logger.error(e.toString());
		}
		for (int k = 0; k < recordList.size(); k++) {
			SysAttendStat record = recordList.get(k);
			SysOrgElement docCreator = record.getDocCreator();
			try {
				Number fdTotalTime = (Number) record.getFdTotalTime();
				Number fdLateTime = (Number) record.getFdLateTime();
				Number fdLeftTime = (Number) record.getFdLeftTime();
				Boolean fdStatus = getBooleanField(record.getFdStatus());
				Boolean fdOutside = getBooleanField(record.getFdOutside());
				String docCreatorId = record.getDocCreator().getFdId();
				Boolean fdLate = getBooleanField(record.getFdLate());
				Boolean fdLeft = getBooleanField(record.getFdLeft());
				Boolean fdMissed = getBooleanField(record.getFdMissed());
				Boolean fdAbsent = getBooleanField(record.getFdAbsent());
				Boolean fdTrip = getBooleanField(record.getFdTrip());
				String fdCategoryId = record.getFdCategoryId();
				Number fdMissedCount = (Number) record.getFdMissedCount();
				Number fdOutsideCount = (Number) record.getFdOutsideCount();
				Number fdLateCount = (Number) record.getFdLateCount();
				Number fdLeftCount = (Number) record.getFdLeftCount();
				Number fdTripDays = (Number) record.getFdTripDays();
				Number fdOffDays = (Number) record.getFdOffDays();
				Boolean fdOff = getBooleanField(record.getFdOff());
				Number fdOverTime = (Number) record.getFdOverTime();
				//定制修改
				Number fdOverApplyTime = (Number) record.getFdOverApplyTime();
				Number fdOverTurnApplyTime = (Number) record.getFdOverTurnApplyTime();
				Number fdOverTurnTime = (Number) record.getFdOverTurnTime();
				Number fdOverPayApplyTime = (Number) record.getFdOverPayApplyTime();
				Number fdOverPayTime = (Number) record.getFdOverPayTime();
				//定制结束
				Number fdDateType = (Number) record.getFdDateType();
				Number fdMissedExcCount = (Number) record.getFdMissedExcCount();
				Number fdLateExcCount = (Number) record.getFdLateExcCount();
				Number fdLeftExcCount = (Number) record.getFdLeftExcCount();
				String fdOffCountDetail = (String) record.getFdOffCountDetail();
				String docCreatorHId = (String) record.getDocCreatorHId();

				Number fdAbsentDaysCount= (Number) record.getFdAbsentDays();

				Number fdPersonalLeaveDays = (Number)record.getFdPersonalLeaveDays();
				Number fdOutgoingTime = (Number) record.getFdOutgoingTime();
				Timestamp fdDate = (Timestamp) record.getFdDate();
				Number fdOffTime = (Number) record.getFdOffTimeHour();
				Boolean fdIsNoRecord = getBooleanField(record.getFdIsNoRecord());
				Date _fdDate = fdDate != null ? new Date(fdDate.getTime()) : null;
				boolean _fdStatus = fdStatus == null ? false
						: fdStatus.booleanValue();
				boolean _fdOutside = fdOutside == null ? false
						: fdOutside.booleanValue();
				boolean _fdLate = fdLate == null ? false
						: fdLate.booleanValue();
				boolean _fdLeft = fdLeft == null ? false
						: fdLeft.booleanValue();
				boolean _fdMissed = fdMissed == null ? false
						: fdMissed.booleanValue();
				//是否旷工
				boolean _fdAbsent = fdAbsent == null ? false
						: fdAbsent.booleanValue();
				// 是否外出
				boolean _fdOutgoing = fdOutgoingTime != null
						&& fdOutgoingTime.intValue() > 0 ? true : false;
				boolean _fdTrip = fdTrip == null ? false
						: fdTrip.booleanValue();
				long _fdTotalTime = fdTotalTime == null ? 0L
						: fdTotalTime.longValue();
				int _fdLateTime = fdLateTime == null ? 0
						: fdLateTime.intValue();
				int _fdLeftTime = fdLeftTime == null ? 0
						: fdLeftTime.intValue();
				boolean _fdOff = fdOff == null ? false : fdOff.booleanValue();
				long _fdOverTime = fdOverTime == null ? 0
						: fdOverTime.longValue();
				//定制开始
				long _fdOverApplyTime = fdOverApplyTime == null ? 0 : fdOverApplyTime.longValue();
				long _fdOverTurnApplyTime = fdOverTurnApplyTime == null ? 0 : fdOverTurnApplyTime.longValue();
				long _fdOverTurnTime = fdOverTurnTime == null ? 0 : fdOverTurnTime.longValue();
				long _fdOverPayApplyTime = fdOverPayApplyTime == null ? 0 : fdOverPayApplyTime.longValue();
				long _fdOverPayTime = fdOverPayTime == null ? 0 : fdOverPayTime.longValue();
				//int _fdDateType = fdDateType == null ? 0 : fdDateType.intValue();
				//根据_fdDate字段判断当天天数类型
				String fdDateStr = DateUtil.convertDateToString(_fdDate, "yyyy-MM-dd hh:mm");
				JSONObject result = hrConfigOvertimeConfigService.getOvertimeType(docCreatorId,fdDateStr);
				int _fdDateType;
				if(result.containsKey("type")){
					_fdDateType = Integer.parseInt(result.getString("type")) - 1;
				}else{
					_fdDateType = fdDateType == null ? 0 : fdDateType.intValue();
				}
				//定制结束
				boolean _fdIsNoRecord = Boolean.TRUE.equals(fdIsNoRecord);
				boolean isSigned = _fdStatus || _fdOutside || _fdLate || _fdLeft
						|| _fdTrip || _fdOutgoing || _fdMissed;

				float _fdStatusDays = _fdStatus ? 1F : 0F;

				float _fdAbsentDays = _fdAbsent ? 1F : 0F;

				int _fdMissedCount = fdMissedCount == null ? 0
						: fdMissedCount.intValue();
				int _fdOutsideCount = fdOutsideCount == null ? 0
						: fdOutsideCount.intValue();
				int _fdLateCount = fdLateCount == null ? 0
						: fdLateCount.intValue();
				int _fdLeftCount = fdLeftCount == null ? 0
						: fdLeftCount.intValue();
				float _fdTripDays = fdTripDays == null ? 0
						: fdTripDays.floatValue();

				// 考勤组信息
				if (!cateMap.containsKey(fdCategoryId)) {
					//SysAttendCategory sysAttendCategory = (SysAttendCategory) sysAttendCategoryService.findByPrimaryKey(fdCategoryId);
					SysAttendCategory sysAttendCategory = CategoryUtil.getCategoryById(fdCategoryId);
					cateMap.put(fdCategoryId, sysAttendCategory);
				}
				userMap.put(docCreatorId, docCreator);
				// 用户考勤记录信息
				if (!statMap.containsKey(docCreatorId)) {
					statMap.put(docCreatorId, new JSONObject());
				}
				JSONObject userInfo = statMap.get(docCreatorId);
				//请假天数
				float _fdOffDays = fdOffDays == null ? 0 : fdOffDays.floatValue();
				//请假小时(新版本所有的时长全部统计在小时上)
				float _fdOffTime = fdOffTime == null ? 0F : fdOffTime.floatValue();
				//标准工作时长
				Float fdWorkTime = record.getFdWorkTime() ==null?0F:record.getFdWorkTime();

				//旷工天数
				float _fdAbsentDaysCount = fdAbsentDaysCount == null ? 0 : fdAbsentDaysCount.floatValue();
				//事假天数
				float _fdPersonalLeaveDays = fdPersonalLeaveDays == null ? 0 : fdPersonalLeaveDays.floatValue();
				//该天应工作的时间，单位天
				Double workDays =  getUserWorkDays(cateMap.get(fdCategoryId),docCreator,_fdDate);
				if(workDays ==null){
					workDays =1D;
				}
				//实际出勤天数：有记录，并且 有打卡,不包含请假，并且不是旷工的。
				float _fdActualDays = (sysAttendCategoryService.isHoliday(fdCategoryId,fdDate)&&!sysAttendCategoryService.isPatchHolidayDay(fdCategoryId,fdDate))||!_fdIsNoRecord && isSigned && !_fdAbsent?workDays.floatValue():0F;
				BigDecimal bigDecimal = new BigDecimal(_fdPersonalLeaveDays);
				_fdActualDays=_fdActualDays-bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
				// 工作日出勤天数 工作日。有记录，并且 有打卡，并且不是旷工的。包含请假天，后面会减去请假天
				float _fdWorkDateDays = _fdDateType == 0 && !_fdIsNoRecord && (isSigned || _fdOff ) && !_fdAbsent ? workDays.floatValue() : 0F;
				if(_fdAbsent == false){
					_fdActualDays = _fdActualDays - _fdAbsentDaysCount > 0 ? _fdActualDays - _fdAbsentDaysCount : 0;
					_fdWorkDateDays = _fdWorkDateDays - _fdAbsentDaysCount > 0 ? _fdWorkDateDays - _fdAbsentDaysCount : 0;
				}

				int _fdMissedExcCount = fdMissedExcCount == null ? 0
						: fdMissedExcCount.intValue();
				int _fdLateExcCount = fdLateExcCount == null ? 0
						: fdLateExcCount.intValue();
				int _fdLeftExcCount = fdLeftExcCount == null ? 0
						: fdLeftExcCount.intValue();

				float _fdOutgoingTime = fdOutgoingTime == null ? 0
						: fdOutgoingTime.floatValue();

				if("0".equals(docCreatorHId)) {
					docCreatorHId=docCreator.getFdHierarchyId();
				}
				if (StringUtil.isNotNull(docCreatorHId)&&!"0".equals(docCreatorHId)) {
					userInfo.put("docCreatorHId", docCreatorHId);
				}

				if (!userInfo.containsKey("fdStatus")) {
					userInfo.put("fdStatus", _fdStatus);
				} else {
					boolean __fdStatus = userInfo.getBoolean("fdStatus");
					userInfo.put("fdStatus",
							__fdStatus || _fdStatus ? true : false);
				}

				if (!userInfo.containsKey("fdOutside")) {
					userInfo.put("fdOutside", _fdOutside);
				} else {
					boolean __fdOutside = userInfo.getBoolean("fdOutside");
					userInfo.put("fdOutside",
							__fdOutside || _fdOutside ? true : false);
				}

				if (!userInfo.containsKey("fdLate")) {
					userInfo.put("fdLate", _fdLate);
				} else {
					boolean __fdLate = userInfo.getBoolean("fdLate");
					userInfo.put("fdLate",
							__fdLate || _fdLate ? true : false);
				}

				if (!userInfo.containsKey("fdLeft")) {
					userInfo.put("fdLeft", _fdLeft);
				} else {
					boolean __fdLeft = userInfo.getBoolean("fdLeft");
					userInfo.put("fdLeft",
							__fdLeft || _fdLeft ? true : false);
				}

				if (!userInfo.containsKey("fdMissed")) {
					userInfo.put("fdMissed", _fdMissed);
				} else {
					boolean __fdMissed = userInfo.getBoolean("fdMissed");
					userInfo.put("fdMissed",
							__fdMissed || _fdMissed ? true : false);
				}

				if (!userInfo.containsKey("fdAbsent")) {
					userInfo.put("fdAbsent", _fdAbsent);
				} else {
					boolean __fdAbsent = userInfo.getBoolean("fdAbsent");
					userInfo.put("fdAbsent",
							__fdAbsent || _fdAbsent ? true : false);
				}

				if (!userInfo.containsKey("fdTrip")) {
					userInfo.put("fdTrip", _fdTrip);
				} else {
					boolean __fdTrip = userInfo.getBoolean("fdTrip");
					userInfo.put("fdTrip",
							__fdTrip || _fdTrip ? true : false);
				}

				// 注意:若是统计当天数据,则忽略部分字段处理
				if (!userInfo.containsKey("fdTotalTime")) {
					userInfo.put("fdTotalTime", _fdTotalTime);
				} else {
					long __fdTotalTime = userInfo.getLong("fdTotalTime");
					userInfo.put("fdTotalTime", __fdTotalTime + _fdTotalTime);
				}

				// 加班总工时
				if (_fdDateType == 1){
					if(fdWorkTime>0.00001)
				if (!userInfo.containsKey("fdOverTime")) {
					userInfo.put("fdOverTime", _fdOverTime);
				} else {
					long __fdOverTime = userInfo.getLong("fdOverTime");
					userInfo.put("fdOverTime", __fdOverTime + _fdOverTime);
				}

				}
				// 加班申请总工时
				if (!userInfo.containsKey("fdOverApplyTime")) {
					userInfo.put("fdOverApplyTime", _fdOverApplyTime);
				} else {
					long __fdOverApplyTime = userInfo.getLong("fdOverApplyTime");
					userInfo.put("fdOverApplyTime", __fdOverApplyTime + _fdOverApplyTime);
				}
				// 加班转调休申请工时
				if (!userInfo.containsKey("fdOverTurnApplyTime")) {
					userInfo.put("fdOverTurnApplyTime", _fdOverTurnApplyTime);
				} else {
					long __fdOverTurnApplyTime = userInfo.getLong("fdOverTurnApplyTime");
					userInfo.put("fdOverTurnApplyTime", __fdOverTurnApplyTime + _fdOverTurnApplyTime);
				}

				//加班转调休实际工时
				if (!userInfo.containsKey("fdOverTurnTime")) {
					userInfo.put("fdOverTurnTime", _fdOverTurnTime);
				} else {
					long __fdOverTurnTime = userInfo.getLong("fdOverTurnTime");
					userInfo.put("fdOverTurnTime", __fdOverTurnTime + _fdOverTurnTime);
				}

				// 加班转加班费申请工时
				if (!userInfo.containsKey("fdOverPayApplyTime")) {
					userInfo.put("fdOverPayApplyTime", _fdOverPayApplyTime);
				} else {
					long __fdOverPayApplyTime = userInfo.getLong("fdOverPayApplyTime");
					userInfo.put("fdOverPayApplyTime", __fdOverPayApplyTime + _fdOverPayApplyTime);
				}

				//加班转加班费实际工时
				if (!userInfo.containsKey("fdOverPayTime")) {
					userInfo.put("fdOverPayTime", _fdOverPayTime);
				} else {
					long __fdOverPayTime = userInfo.getLong("fdOverPayTime");
					userInfo.put("fdOverPayTime", __fdOverPayTime + _fdOverPayTime);
				}

				// 外出工时
				if (!userInfo.containsKey("fdOutgoingTime")) {
					userInfo.put("fdOutgoingTime", _fdOutgoingTime);
				} else {
					double __fdOutgoingTime = userInfo
							.getDouble("fdOutgoingTime");
					userInfo.put("fdOutgoingTime",
							__fdOutgoingTime + _fdOutgoingTime);
				}
				if(fdWorkTime !=null && fdWorkTime > 0){
					//外出工时转成换天。
					double _fdOutGoingDay =_fdOutgoingTime/fdWorkTime;
					//如果等于空的情况，则不处理
					if (!userInfo.containsKey("fdOutgoingDay")) {
						userInfo.put("fdOutgoingDay", _fdOutGoingDay);
					} else {
						double __fdOutGoingDay = userInfo.getDouble("fdOutgoingDay");
						userInfo.put("fdOutgoingDay",_fdOutGoingDay + __fdOutGoingDay);
					}
				}

				if (_fdDateType == 0) {
					// 工作日加班工时
					if(fdWorkTime>0.00001)
					if (!userInfo.containsKey("fdWorkOverTime")) {
						userInfo.put("fdWorkOverTime", _fdOverTime);
					} else {
						long __fdOverTime = userInfo.getLong("fdWorkOverTime");
						userInfo.put("fdWorkOverTime", __fdOverTime + _fdOverTime);
					}
					//工作日加班申请工时
					if (!userInfo.containsKey("fdWorkOverApplyTime")) {
						userInfo.put("fdWorkOverApplyTime", _fdOverApplyTime);
					} else {
						long __fdOverApplyTime = userInfo.getLong("fdWorkOverApplyTime");
						userInfo.put("fdWorkOverApplyTime", __fdOverApplyTime + _fdOverApplyTime);
					}
					//工作日转调休申请工时
					if (!userInfo.containsKey("fdWorkOverTurnApplyTime")) {
						userInfo.put("fdWorkOverTurnApplyTime", _fdOverTurnApplyTime);
					} else {
						long __fdOverTurnApplyTime = userInfo.getLong("fdWorkOverTurnApplyTime");
						userInfo.put("fdWorkOverTurnApplyTime", __fdOverTurnApplyTime + _fdOverTurnApplyTime);
					}

					//工作日转调休实际工时
					if (!userInfo.containsKey("fdWorkOverTurnTime")) {
						userInfo.put("fdWorkOverTurnTime", _fdOverTurnTime);
					} else {
						long __fdOverTurnTime = userInfo.getLong("fdWorkOverTurnTime");
						userInfo.put("fdWorkOverTurnTime", __fdOverTurnTime + _fdOverTurnTime);
					}

					//工作日加班转加班费申请工时
					if (!userInfo.containsKey("fdWorkOverPayApplyTime")) {
						userInfo.put("fdWorkOverPayApplyTime", _fdOverPayApplyTime);
					} else {
						long __fdOverPayApplyTime = userInfo.getLong("fdWorkOverPayApplyTime");
						userInfo.put("fdWorkOverPayApplyTime", __fdOverPayApplyTime + _fdOverPayApplyTime);
					}
					//工作日加班转加班费工时
					if (!userInfo.containsKey("fdWorkOverPayTime")) {
						userInfo.put("fdWorkOverPayTime", _fdOverPayApplyTime);
					} else {
						long __fdOverPayTime = userInfo.getLong("fdWorkOverPayTime");
						userInfo.put("fdWorkOverPayTime", __fdOverPayTime + _fdOverPayTime);
					}
				} else if (_fdDateType == 1) {
					// 休息日加班工时
					if(fdWorkTime>0.00001)
					if (!userInfo.containsKey("fdOffOverTime")) {
						userInfo.put("fdOffOverTime", _fdOverTime);
					} else {
						long __fdOverTime = userInfo.getLong("fdOffOverTime");
						userInfo.put("fdOffOverTime", __fdOverTime + _fdOverTime);
					}

					//休息日加班申请工时
					if (!userInfo.containsKey("fdOffOverApplyTime")) {
						userInfo.put("fdOffOverApplyTime", _fdOverApplyTime);
					} else {
						long __fdOverApplyTime = userInfo.getLong("fdOffOverApplyTime");
						userInfo.put("fdOffOverApplyTime", __fdOverApplyTime + _fdOverApplyTime);
					}

					//休息日转调休申请工时
					if (!userInfo.containsKey("fdOffOverTurnApplyTime")) {
						userInfo.put("fdOffOverTurnApplyTime", _fdOverTurnApplyTime);
					} else {
						long __fdOverTurnApplyTime = userInfo.getLong("fdOffOverTurnApplyTime");
						userInfo.put("fdOffOverTurnApplyTime", __fdOverTurnApplyTime + _fdOverTurnApplyTime);
					}

					//休息日转调休实际工时
					if (!userInfo.containsKey("fdOffOverTurnTime")) {
						userInfo.put("fdOffOverTurnTime", _fdOverTurnTime);
					} else {
						long __fdOverTurnTime = userInfo.getLong("fdOffOverTurnTime");
						userInfo.put("fdOffOverTurnTime", __fdOverTurnTime + _fdOverTurnTime);
					}

					//休息日加班转加班费申请工时
					if (!userInfo.containsKey("fdOffOverPayApplyTime")) {
						userInfo.put("fdOffOverPayApplyTime", _fdOverPayApplyTime);
					} else {
						long __fdOverPayApplyTime = userInfo.getLong("fdOffOverPayApplyTime");
						userInfo.put("fdOffOverPayApplyTime", __fdOverPayApplyTime + _fdOverPayApplyTime);
					}

					//休息日加班转加班费工时
					if (!userInfo.containsKey("fdOffOverPayTime")) {
						userInfo.put("fdOffOverPayTime", _fdOverPayApplyTime);
					} else {
						long __fdOverPayTime = userInfo.getLong("fdOffOverPayTime");
						userInfo.put("fdOffOverPayTime", __fdOverPayTime + _fdOverPayTime);
					}
				} else if (_fdDateType == 2) {
					// 节假日加班工时
//					if(fdWorkTime>0.00001)
					if (!userInfo.containsKey("fdHolidayOverTime")) {
						userInfo.put("fdHolidayOverTime", _fdOverTime);
					} else {
						long __fdOverTime = userInfo
								.getLong("fdHolidayOverTime");
						userInfo.put("fdHolidayOverTime",
								__fdOverTime + _fdOverTime);
					}

					//节假日加班申请工时
					if (!userInfo.containsKey("fdHolidayOverApplyTime")) {
						userInfo.put("fdHolidayOverApplyTime", _fdOverApplyTime);
					} else {
						long __fdOverApplyTime = userInfo.getLong("fdHolidayOverApplyTime");
						userInfo.put("fdHolidayOverApplyTime", __fdOverApplyTime + _fdOverApplyTime);
					}
					//节假日转调休申请工时
					if (!userInfo.containsKey("fdHolidayOverTurnApplyTime")) {
						userInfo.put("fdHolidayOverTurnApplyTime", _fdOverTurnApplyTime);
					} else {
						long __fdOverTurnApplyTime = userInfo.getLong("fdHolidayOverTurnApplyTime");
						userInfo.put("fdHolidayOverTurnApplyTime", __fdOverTurnApplyTime + _fdOverTurnApplyTime);
					}

					//节假日转调休实际工时
					if (!userInfo.containsKey("fdHolidayOverTurnTime")) {
						userInfo.put("fdHolidayOverTurnTime", _fdOverTurnTime);
					} else {
						long __fdOverTurnTime = userInfo.getLong("fdHolidayOverTurnTime");
						userInfo.put("fdHolidayOverTurnTime", __fdOverTurnTime + _fdOverTurnTime);
					}

					//节假日加班转加班费申请工时
					if (!userInfo.containsKey("fdHolidayOverPayApplyTime")) {
						userInfo.put("fdHolidayOverPayApplyTime", _fdOverPayApplyTime);
					} else {
						long __fdOverPayApplyTime = userInfo.getLong("fdHolidayOverPayApplyTime");
						userInfo.put("fdHolidayOverPayApplyTime", __fdOverPayApplyTime + _fdOverPayApplyTime);
					}

					//节假日加班转加班费工时
					if (!userInfo.containsKey("fdHolidayOverPayTime")) {
						userInfo.put("fdHolidayOverPayTime", _fdOverPayTime);
					} else {
						long __fdOverPayTime = userInfo.getLong("fdHolidayOverPayTime");
						userInfo.put("fdHolidayOverPayTime", __fdOverPayTime + _fdOverPayTime);
					}
				}

				if (!userInfo.containsKey("fdLateTime")) {
					userInfo.put("fdLateTime", _fdLateTime);
				} else {
					int __fdLateTime = userInfo.getInt("fdLateTime");
					userInfo.put("fdLateTime", __fdLateTime + _fdLateTime);
				}

				if (!userInfo.containsKey("fdLeftTime")) {
					userInfo.put("fdLeftTime", _fdLeftTime);
				} else {
					int __fdLeftTime = userInfo.getInt("fdLeftTime");
					userInfo.put("fdLeftTime", __fdLeftTime + _fdLeftTime);
				}
				// 正常出勤天数
				if (!userInfo.containsKey("fdStatusDays")) {
					userInfo.put("fdStatusDays", _fdStatusDays);
				} else {
					float __fdStatusDays = Float.valueOf(userInfo.get("fdStatusDays").toString());
					userInfo.put("fdStatusDays",
							__fdStatusDays + _fdStatusDays);
				}
				// 实际出勤天数
				if (!userInfo.containsKey("fdActualDays")) {
					userInfo.put("fdActualDays", _fdActualDays);
				} else {
					float __fdActualDays =Float.valueOf(userInfo.get("fdActualDays").toString());
					userInfo.put("fdActualDays",
							__fdActualDays + _fdActualDays);
				}
				// 工作日出勤天数
				if (!userInfo.containsKey("fdWorkDateDays")) {
					userInfo.put("fdWorkDateDays", _fdWorkDateDays);
				} else {
					float __fdWorkDateDays = Float.valueOf(userInfo.get("fdWorkDateDays").toString());
					userInfo.put("fdWorkDateDays",
							__fdWorkDateDays + _fdWorkDateDays);
				}
				// 旷工天数，该字段已弃用
				if (!userInfo.containsKey("fdAbsentDays")) {
					userInfo.put("fdAbsentDays", _fdAbsentDays);
				} else {
					int __fdAbsentDays = userInfo.getInt("fdAbsentDays");
					userInfo.put("fdAbsentDays",
							__fdAbsentDays + _fdAbsentDays);
				}
				// 旷工天数
				if (!userInfo.containsKey("fdAbsentDaysCount")) {
					userInfo.put("fdAbsentDaysCount", _fdAbsentDaysCount);
				} else {
					//不定时工作制旷工为1天

					double __fdAbsentDaysCount = userInfo
							.getDouble("fdAbsentDaysCount");
					userInfo.put("fdAbsentDaysCount",
							__fdAbsentDaysCount + (double) _fdAbsentDaysCount);
				}
				// 事假天数
				if (!userInfo.containsKey("fdPersonalLeaveDays")) {
					userInfo.put("fdPersonalLeaveDays", _fdPersonalLeaveDays);
				} else {
					double __fdPersonalLeaveDays = userInfo
							.getDouble("fdPersonalLeaveDays");
					userInfo.put("fdPersonalLeaveDays",
							__fdPersonalLeaveDays + (double) _fdPersonalLeaveDays);
				}
				// 出差天数
				if (!userInfo.containsKey("fdTripDays")) {
					userInfo.put("fdTripDays", _fdTripDays);
				} else {
					double __fdTripCount = userInfo.getDouble("fdTripDays");
					userInfo.put("fdTripDays",
							__fdTripCount + _fdTripDays);
				}

				userInfo.put("fdCategoryId", fdCategoryId);

				if (!userInfo.containsKey("fdMissedCount")) {
					userInfo.put("fdMissedCount", _fdMissedCount);
				} else {
					int __fdMissedCount = userInfo.getInt("fdMissedCount");
					userInfo.put("fdMissedCount",
							__fdMissedCount + _fdMissedCount);
				}

				if (!userInfo.containsKey("fdOutsideCount")) {
					userInfo.put("fdOutsideCount", _fdOutsideCount);
				} else {
					int __fdOutsideCount = userInfo.getInt("fdOutsideCount");
					userInfo.put("fdOutsideCount",
							__fdOutsideCount + _fdOutsideCount);
				}

				if (!userInfo.containsKey("fdLateCount")) {
					userInfo.put("fdLateCount", _fdLateCount);
				} else {
					int __fdLateCount = userInfo.getInt("fdLateCount");
					userInfo.put("fdLateCount",
							__fdLateCount + _fdLateCount);
				}

				if (!userInfo.containsKey("fdLeftCount")) {
					userInfo.put("fdLeftCount", _fdLeftCount);
				} else {
					int __fdOutsideCount = userInfo.getInt("fdLeftCount");
					userInfo.put("fdLeftCount",
							__fdOutsideCount + _fdLeftCount);
				}

				if (!userInfo.containsKey("fdMissedExcCount")) {
					userInfo.put("fdMissedExcCount", _fdMissedExcCount);
				} else {
					int __fdMissedExcCount = userInfo
							.getInt("fdMissedExcCount");
					userInfo.put("fdMissedExcCount",
							__fdMissedExcCount + _fdMissedExcCount);
				}
				if (!userInfo.containsKey("fdLateExcCount")) {
					userInfo.put("fdLateExcCount", _fdLateExcCount);
				} else {
					int __fdLateExcCount = userInfo.getInt("fdLateExcCount");
					userInfo.put("fdLateExcCount",
							__fdLateExcCount + _fdLateExcCount);
				}
				if (!userInfo.containsKey("fdLeftExcCount")) {
					userInfo.put("fdLeftExcCount", _fdLeftExcCount);
				} else {
					int __fdLeftExcCount = userInfo.getInt("fdLeftExcCount");
					userInfo.put("fdLeftExcCount",
							__fdLeftExcCount + _fdLeftExcCount);
				}
				//请假月统计，小时+天的统计成天的汇总
				float _fdLeaveDays =0F;
				if(fdWorkTime !=null && fdWorkTime > 0){
					//新版本的请假天 =请假小时数/标准时长
					_fdLeaveDays = _fdOffTime/fdWorkTime;
					// 请假天数(小时+天的汇总)
					if (!userInfo.containsKey("fdLeaveDays")) {
						userInfo.put("fdLeaveDays", _fdLeaveDays);
					} else {
						double __fdLeaveDays = userInfo.getDouble("fdLeaveDays");
						userInfo.put("fdLeaveDays",__fdLeaveDays + _fdLeaveDays);
					}
				}
				// 请假天数
				if (!userInfo.containsKey("fdOffDays")) {
					userInfo.put("fdOffDays", _fdOffDays);
				} else {
					double __fdOffDays = userInfo.getDouble("fdOffDays");
					userInfo.put("fdOffDays",__fdOffDays + _fdOffDays);
				}
				// 请假小时
				if (!userInfo.containsKey("fdOffTime")) {
					userInfo.put("fdOffTime", _fdOffTime);
				} else {
					double __fdOffTime = userInfo.getDouble("fdOffTime");
					userInfo.put("fdOffTime",__fdOffTime + _fdOffTime);
				}
				//休息日的请假汇总
				if(_fdDateType ==0 ){
					// 请假天数
					if (!userInfo.containsKey("fdOffDaysWork")) {
						userInfo.put("fdOffDaysWork", _fdOffDays);
					} else {
						double __fdOffDays = userInfo.getDouble("fdOffDaysWork");
						userInfo.put("fdOffDaysWork",
								__fdOffDays + _fdOffDays);
					}
					// 请假小时
					if (!userInfo.containsKey("fdOffTimeWork")) {
						userInfo.put("fdOffTimeWork", _fdOffTime);
					} else {
						double __fdOffTime = userInfo.getDouble("fdOffTimeWork");
						userInfo.put("fdOffTimeWork", __fdOffTime + _fdOffTime);
					}
				}

				// 请假数据统计
				statOffCount(userInfo, fdOffCountDetail, leaveRuleMap, leaveConfig);

				// 更换考勤组，需记录各个考勤组相应的天数
				JSONObject json = new JSONObject();
				if (userInfo.containsKey("dateRecord")) {
					json = userInfo.getJSONObject("dateRecord");
				}
				String dateKey = AttendUtil.getDate(_fdDate, 0).getTime() + "";
				if (!json.containsKey(dateKey)) {
					// 休息日生成的统计数据对应当前用户的考勤组信息
					// 工作日计算
					//或者是节假日计算,add by liuyang at 2023/04/16
					if (_fdDateType == 0 || _fdDateType == 2) {
						JSONObject dateJson = new JSONObject();
						dateJson.put("statDate", _fdDate.getTime());
						dateJson.put("fdCategoryId", fdCategoryId);
						json.put(dateKey, dateJson);
					}
				} else {
					//或者是节假日计算,add by liuyang at 2023/04/16
					if (_fdDateType == 0 || _fdDateType == 2) {
						JSONObject dateJson = json.getJSONObject(dateKey);
						dateJson.put("statDate", _fdDate.getTime());
						dateJson.put("fdCategoryId", fdCategoryId);
						json.put(dateKey, dateJson);
					}
				}
				userInfo.put("dateRecord", json);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("非法数据,统计用户信息报错,忽略处理!userId:" + docCreator.getFdId()
						+ ";sysattendstatId:" + record.getFdId(), e);
			}

		}

		for (Map.Entry<String,SysOrgElement> userKeyInfo :userMap.entrySet()) {
			JSONObject userInfo = statMap.get(userKeyInfo.getKey());
			if(userInfo !=null){
				//请假天数
				float __fdOffDays =0F;
				if(userInfo.containsKey("fdLeaveDays")){
					//有统计后的总计天。则直接使用
					__fdOffDays =Float.valueOf(userInfo.get("fdLeaveDays").toString());
				} else {
					//如果标准工时 不存在，则使用天加小时的组合。（老数据）
					if (userInfo.containsKey("fdOffDaysWork")) {
						__fdOffDays = Float.valueOf(userInfo.get("fdOffDaysWork").toString());
					}
					float __fdOffHour = 0F;
					if (userInfo.containsKey("fdOffTimeWork")) {
						__fdOffHour = Float.valueOf(userInfo.get("fdOffTimeWork").toString());
					}
					__fdOffDays = SysTimeUtil.formatLeaveTimeToDay(__fdOffDays, __fdOffHour);
				}
				//工作日出勤 = 工作日应出勤天数 不包含休息日 - 请假天数
				float fdWorkDateDays =0F;
				if (userInfo.containsKey("fdWorkDateDays")) {
					fdWorkDateDays = Float.valueOf(userInfo.get("fdWorkDateDays").toString());
				}
				if(fdWorkDateDays > __fdOffDays){
					userInfo.put("fdWorkDateDays",Float.valueOf(NumberUtil.roundDecimal(fdWorkDateDays - __fdOffDays, 2)));
				}else{
					userInfo.put("fdWorkDateDays",0F);
				}
			}
		}
	}

	/**
	 * 请假数据统计
	 * 
	 * @param userInfo
	 * @param fdOffCountDetail
	 * @param leaveRuleMap
	 * @param leaveConfig
	 */
	private void statOffCount(JSONObject userInfo, String fdOffCountDetail,
			Map<String, SysTimeLeaveRule> leaveRuleMap,
			SysTimeLeaveConfig leaveConfig) {
		if (!userInfo.containsKey("fdOffCountDetail")) {
			JSONObject statMonthJson = new JSONObject();
			if (StringUtil.isNotNull(fdOffCountDetail)) {
				JSONObject statJson = JSONObject.fromObject(fdOffCountDetail);
				statMonthJson = getOffStatJson(statJson, leaveRuleMap,
						leaveConfig);
			}
			userInfo.put("fdOffCountDetail", statMonthJson);
		} else {
			JSONObject statMonthJson = userInfo.getJSONObject("fdOffCountDetail");
			if (StringUtil.isNotNull(fdOffCountDetail)) {
				JSONObject statJson = JSONObject.fromObject(fdOffCountDetail);
				statMonthJson = mergeOffStatJson(statMonthJson, statJson, leaveRuleMap, leaveConfig);
			}
			userInfo.put("fdOffCountDetail", statMonthJson);
		}
	}

	private JSONObject getOffStatJson(JSONObject statJson,
			Map<String, SysTimeLeaveRule> leaveRuleMap,
			SysTimeLeaveConfig leaveConfig) {
		JSONObject statMonthJson = new JSONObject();
		Iterator keys = statJson.keys();
		while (keys.hasNext()) {
			String offKey = (String) keys.next();
			Object countObj = statJson.get(offKey);
			JSONObject countJson = formatOffCountJson(offKey, countObj,
					leaveRuleMap, leaveConfig);
			if (countJson != null) {
				statMonthJson.put(offKey, countJson);
			}
		}
		return statMonthJson;
	}

	private JSONObject mergeOffStatJson(JSONObject statMonthJson,
			JSONObject statJson,
			Map<String, SysTimeLeaveRule> leaveRuleMap,
			SysTimeLeaveConfig leaveConfig) {
		Iterator keys = statJson.keys();
		while (keys.hasNext()) {
			String offKey = (String) keys.next();
			Object countObj = statJson.get(offKey);
			if (statMonthJson.containsKey(offKey)) {
				JSONObject statCountJson = formatOffCountJson(offKey, countObj,
						leaveRuleMap, leaveConfig);
				JSONObject monthCountJson = (JSONObject) statMonthJson
						.get(offKey);
				if (statCountJson != null && monthCountJson != null) {
					int statType1 = statCountJson.getInt("statType");
					int statType2 = monthCountJson.getInt("statType");
					if (statType1 == statType2) {
						if (statType1 == 3) {
							monthCountJson.put("count",
									statCountJson.getDouble("count")
											+ monthCountJson
													.getDouble("count"));
						} else {
							monthCountJson.put("count",
									statCountJson.getDouble("count")
											+ monthCountJson
													.getDouble("count"));
						}
					}
					statMonthJson.put(offKey, monthCountJson);
				}
			} else {
				JSONObject countJson = formatOffCountJson(offKey, countObj,
						leaveRuleMap, leaveConfig);
				if (countJson != null) {
					statMonthJson.put(offKey, countJson);
				}
			}
		}
		return statMonthJson;
	}

	private JSONObject formatOffCountJson(String offKey, Object countObj,
			Map<String, SysTimeLeaveRule> leaveRuleMap,
			SysTimeLeaveConfig leaveConfig) {
		if (countObj instanceof Number) {// 兼容以前数据
			if (!"totalDay".equals(offKey) && !"totalHour".equals(offKey)) {
				JSONObject json = new JSONObject();
				json.put("count", countObj);
				json.put("statType", 2);
				return json;
			}
		} else if (countObj instanceof JSONObject) {
			JSONObject json = (JSONObject) countObj;
			Integer statType = json.getInt("statType");
			Number count = (Number) json.get("count");

			if ("NaN".equals(offKey)) {// 请假类型为空
				if (Integer.valueOf(3).equals(json.getInt("statType"))) {
					// 小时转化为天
					if (count.intValue() >= 8) {// 工时换算默认8小时
						json.put("count", 1);
						json.put("statType", 2);
					}
				}
				return json;
			} else {
				// 缓存请假规则
				if (!leaveRuleMap.containsKey(offKey)) {
					leaveRuleMap.put(offKey,
							AttendUtil.getLeaveRuleByType(Integer.valueOf(offKey)));
				}
				SysTimeLeaveRule sysTimeLeaveRule = leaveRuleMap
						.get(offKey);
				if (sysTimeLeaveRule != null) {
					Integer fdStatType = sysTimeLeaveRule
							.getFdStatType();
					Float covertTime = SysTimeUtil.getConvertTime();

					if (fdStatType == 1) {// 按天统计，把小时转化为天
						if (Integer.valueOf(3).equals(statType)) {
							if (count.intValue() >= covertTime) {
								json.put("statType", 1);
								json.put("count", 1);
							}
						}
					} else if (fdStatType == 2) {// 按半天统计，把小时转化为天
						if (Integer.valueOf(3).equals(statType)) {
							if (count.intValue() > 0 && count
									.intValue() < covertTime) {
								json.put("statType", 2);
								json.put("count", 0.5);
							} else if (count
									.intValue() >= covertTime) {
								json.put("statType", 2);
								json.put("count", 1);
							}
						}
					} else if (fdStatType == 3) {// 按小时统计，把天转化为小时
						if (!Integer.valueOf(3).equals(statType)) {
							if (count.intValue() > 0) {
								json.put("statType", 3);
								json.put("count", count.floatValue()
										* covertTime);
							}
						}
					}
					return json;
				}
			}
		}
		return null;
	}

	/**
	 * 计算每个月的应工作天数 和 假期天数
	 * @param statMap
	 * @param cateMap
	 * @param userMap
	 * @param beginTime
	 * @param endTime
	 */
	private void recalUserInfo(Map<String, JSONObject> statMap,
			Map<String, SysAttendCategory> cateMap,
			Map<String, SysOrgElement> userMap, Date beginTime, Date endTime) {
		// 应出勤天数规则配置
		boolean shouldDayCfg = AttendUtil.getAttendShouldDayCfg();
		for (String key : statMap.keySet()) {
			JSONObject json = statMap.get(key);
			// 默认值
			float fdShouldDays = 0F;
			Integer fdHolidays=0;
			try {
				if (!userMap.containsKey(key) || userMap.get(key) == null) {
					continue;
				}
				SysOrgElement docCreator = userMap.get(key);
				JSONObject dateRecord = json.containsKey("dateRecord")
						? json.getJSONObject("dateRecord") : null;
				// 计算是否更换过考勤组
				Set<String> cateSet = new HashSet<String>();
				List<Long> dateList = new ArrayList<Long>();
				if (dateRecord != null) {
					for (Iterator it = dateRecord.keys(); it.hasNext();) {
						String dateKey = (String) it.next();
						JSONObject dateJson = dateRecord
								.getJSONObject(dateKey);
						String fdCategoryId = (String) dateJson
								.get("fdCategoryId");
						dateList.add(Long.valueOf(dateKey));
						if (StringUtil.isNotNull(fdCategoryId)) {
							cateSet.add(fdCategoryId);
						}
					}
				}
				if (cateSet.size() > 1 && !shouldDayCfg) {
					// 更换了考勤组，应出勤天数相应变化
					JSONArray dateJsonArr = new JSONArray();
					for (Iterator it = dateRecord.keys(); it.hasNext();) {
						String dateKey = (String) it.next();
						JSONObject dateJson = dateRecord.getJSONObject(dateKey);
						JSONObject tmpJson = new JSONObject();
						tmpJson.put("categoryId", dateJson.get("fdCategoryId"));
						tmpJson.put("statDate", dateJson.getLong("statDate"));
						dateJsonArr.add(tmpJson);
					}
					// 按升序排序
					Collections.sort(dateJsonArr, new Comparator<JSONObject>() {
						@Override
						public int compare(JSONObject arg0, JSONObject arg1) {
							return Long.valueOf(arg0.getLong("statDate"))
									.compareTo(Long.valueOf(
											arg1.getLong("statDate")));
						}
					});
					logger.warn("userName:" + docCreator.getFdName()
							+ "dateJsonArr:" + dateJsonArr.toString());
					for (int i = 0; i < dateJsonArr.size(); i++) {
						JSONObject tmpJson = (JSONObject) dateJsonArr.get(i);
						String categoryId = tmpJson.getString("categoryId");
						if (!cateMap.containsKey(categoryId)
								|| cateMap.get(categoryId) == null) {
							continue;
						}
						SysAttendCategory category = cateMap.get(categoryId);
						Date startDate = AttendUtil.getDate(
								new Date(tmpJson.getLong("statDate")), 0);
						Date endDate = startDate;
						if (i == dateJsonArr.size() - 1) {
							endDate = AttendUtil.getDate(endTime, -1);
						}
						JSONObject jsonDays=getFdShouldDays(category, docCreator,
								startDate, endDate);

						if(jsonDays.get("fdShouldDays") !=null) {
							fdShouldDays +=Float.valueOf(jsonDays.get("fdShouldDays").toString());
						}
						fdHolidays += jsonDays.getInt("fdHolidays");
					}
				} else {
					String fdCategoryId = json.getString("fdCategoryId");
					if (!cateMap.containsKey(fdCategoryId)
							|| cateMap.get(fdCategoryId) == null) {
						continue;
					}
					SysAttendCategory category = cateMap.get(fdCategoryId);

					Date startDate = AttendUtil.getDate(beginTime, 0);
					Date endDate = AttendUtil.getDate(endTime, -1);
					JSONObject jsonDays=getFdShouldDays(category, docCreator,
							startDate, endDate);
					if(jsonDays.get("fdShouldDays") !=null) {
						fdShouldDays =Float.valueOf(jsonDays.get("fdShouldDays").toString());
					}
					fdHolidays = jsonDays.getInt("fdHolidays");
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("用户非法数据,计算应出勤天数忽略!userId:" + key, e);
				fdHolidays =0;
				fdShouldDays=0;
			}finally {
				// 应出勤天数
				json.put("fdShouldDays", fdShouldDays);
				//节假日天数
				json.put("fdHolidays", fdHolidays);
			}
		}
		//每次执行完清理
		elementWorkDayCatch.clear();
	}

	private List getStatUser(Date beginTime, Date endTime, String fdCategoryId)
			throws Exception {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<String> orgList = new ArrayList<String>();
		try {
			DataSource dataSource = (DataSource) SpringBeanUtil
					.getBean("dataSource");
			String orgSql = "select DISTINCT doc_creator_id from sys_attend_stat where fd_date >=? and fd_date<? ";
			if (StringUtil.isNotNull(fdCategoryId)) {
				SysAttendCategory category =CategoryUtil.getCategoryById(fdCategoryId);
				List<String> orgIds =null;
				if(category ==null){
					orgIds = sysAttendCategoryService.getAttendPersonIds(Lists.newArrayList(fdCategoryId),beginTime,true);
				}else{
					orgIds = sysAttendCategoryService.getAttendPersonIds(category.getFdId(),beginTime);
				}
				if (!orgIds.isEmpty()) {
					orgSql = orgSql + " and "
							+ HQLUtil.buildLogicIN("doc_creator_id", orgIds);
				}
			}
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(orgSql);
			statement.setTimestamp(1, new Timestamp(beginTime.getTime()));
			statement.setTimestamp(2, new Timestamp(endTime.getTime()));
			rs = statement.executeQuery();
			while (rs.next()) {
				orgList.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取月统计用户失败:" + e.getMessage(), e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(conn);
		}

		return orgList;
	}

	private List getStatRecord(List orgList, Date beginTime, Date endTime)
			throws Exception {
		List<SysAttendStat> recordList = new ArrayList<SysAttendStat>();
		TransactionStatus status = null;
		boolean isException =false;
		try {
			status =TransactionUtils.beginNewReadTransaction();
			String recordSql = "select * from sys_attend_stat "
					+ " where fd_date >=:beginDate and fd_date<:endDate and "
					+ HQLUtil.buildLogicIN("doc_creator_id", orgList);
			recordSql += " order by fd_date asc";
			NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(recordSql).addEntity(SysAttendStat.class);
			query.setTimestamp("beginDate", new Timestamp(beginTime.getTime()));
			query.setTimestamp("endDate", new Timestamp(endTime.getTime()));
			recordList = query.list();
		} catch (Exception e) {
			isException =true;
			e.printStackTrace();
			logger.error("获取月份统计信息失败:" + e.getMessage(), e);
			throw e;
		} finally {
			if(isException && status !=null){
				TransactionUtils.rollback(status);
			}else if(status !=null){
				TransactionUtils.commit(status);
			}
		}
		return recordList;
	}
	
	private List getStatRecord(List orgList, Date beginTime, Date endTime,SysOrgElement sysOrgElement,String fdCategoryId)
			throws Exception {
		List<SysAttendStat> recordList = new ArrayList<SysAttendStat>();
		TransactionStatus status = null;
		boolean isException =false;
		try {
			status =TransactionUtils.beginNewReadTransaction();
			String recordSql = "select * from sys_attend_stat "
					+ " where fd_date >=:beginDate and fd_date<:endDate ";
			if(null!=sysOrgElement || StringUtil.isNotNull(fdCategoryId)) {
				recordSql+=" and ("+ HQLUtil.buildLogicIN("doc_creator_id", orgList);
				if(null!=sysOrgElement) {
					recordSql+=" or doc_creator_hid like :docCreatorHId ";
				}
				if(StringUtil.isNotNull(fdCategoryId)) {
					recordSql+=" or fd_category_id =:fdCategoryId ";
				}
				recordSql+=")";
			}else {
				recordSql+=" and "+ HQLUtil.buildLogicIN("doc_creator_id", orgList);
			}
			recordSql += " order by fd_date asc";
			NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(recordSql).addEntity(SysAttendStat.class);
			query.setTimestamp("beginDate", new Timestamp(beginTime.getTime()));
			query.setTimestamp("endDate", new Timestamp(endTime.getTime()));
			if(null!=sysOrgElement) {
				query.setString("docCreatorHId", "%"+sysOrgElement.getFdId()+"%");
			}
			if(StringUtil.isNotNull(fdCategoryId)) {
				query.setString("fdCategoryId", fdCategoryId);
			}
			recordList = query.list();
		} catch (Exception e) {
			isException =true;
			e.printStackTrace();
			logger.error("获取月份统计信息失败:" + e.getMessage(), e);
			throw e;
		} finally {
			if(isException && status !=null){
				TransactionUtils.rollback(status);
			}else if(status !=null){
				TransactionUtils.commit(status);
			}
		}
		return recordList;
	}

	/**
	 * 获取统计月的用户列表。因为外部可能是jdbc处理。然后这里启用一个新查询事务查询。
	 * @param beginMonthTime
	 * @param endMonthTime
	 * @return
	 */
	public List getStatMonthUsers(Date beginMonthTime, Date endMonthTime) {
		List orgList =null;
		TransactionStatus status = null;
		boolean isException =false;
		try {
			status = TransactionUtils.beginNewReadTransaction();
			String monthSql = "select DISTINCT doc_creator_id from sys_attend_stat_month "
					+ "where fd_month>=:beginMonthTime and fd_month<:endMonthTime";
			orgList = getBaseDao().getHibernateSession().createNativeQuery(monthSql).setTimestamp("beginMonthTime", beginMonthTime).setTimestamp("endMonthTime", endMonthTime).list();
		} catch (Exception e) {
			isException =true;
			e.printStackTrace();
		} finally {
			if(isException && status !=null){
				TransactionUtils.rollback(status);
			}else if(status !=null){
				TransactionUtils.commit(status);
			}
		}
		return orgList;
	}

	public Date getMonth(Date date, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public Date getDate(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取应考勤天数
	 * @param category
	 * @param docCreator
	 * @param fdStartTime
	 * @param fdEndTime
	 * @return
	 * @throws Exception
	 */
	public JSONObject getFdShouldDays(SysAttendCategory category,
			SysOrgElement docCreator, Date fdStartTime, Date fdEndTime)
			throws Exception {
		Double count = 0D;
		int fdHolidays=0;
		JSONObject json=new JSONObject();
		if (category == null || docCreator == null || fdStartTime == null
				|| fdEndTime == null) {
			json.put("fdShouldDays", 0);
			json.put("fdHolidays", 0);
			return json;
		}
		logger.debug("getFdShouldDays,userName:" + docCreator.getFdId()
				+ ";categoryId:" + category.getFdId() + ";fdStartTime:"
				+ fdStartTime + ";fdEndTime" + fdEndTime);
		Date cateCreateTime = category.getDocCreateTime();
		Date cateEffectTime = category.getFdEffectTime();
		SysOrgPerson person = (SysOrgPerson) sysOrgCoreService
				.format(docCreator);
		Date personCreateTime = person.getFdHiredate() == null
				? docCreator.getFdCreateTime()
				: person.getFdHiredate();
		//用户离职日期
		Date personAlterTime = Boolean.FALSE.equals(person.getFdIsAvailable()) && person.getFdLeaveDate() == null
						? docCreator.getFdAlterTime() : person.getFdLeaveDate();

		Date startDate = AttendUtil.getDate(fdStartTime, 0);
		Date endDate = AttendUtil.getDate(fdEndTime, 0);
		
		// 考勤组生效开始算
		if (cateEffectTime != null
				&& startDate.getTime() <= cateEffectTime.getTime()) {
			startDate = AttendUtil.getDate(cateEffectTime, 0);
		}else if (cateEffectTime == null && cateCreateTime != null
				&& startDate.getTime() <= cateCreateTime.getTime()) {
			// 考勤组新建开始算
			startDate = AttendUtil.getDate(cateCreateTime, 0);
		}
		// 人员入职开始算
		if (personCreateTime != null
				&& startDate.getTime() <= personCreateTime.getTime()) {
			startDate = AttendUtil.getDate(personCreateTime, 0);
		}
		
		if(personAlterTime!=null && AttendUtil.getDate(personAlterTime, 0).before(endDate)) {
			endDate=AttendUtil.getDate(personAlterTime, 0);
		}
		if(logger.isDebugEnabled()) {
			logger.debug("getFdShouldDays,userid:" + docCreator.getFdId()
			+ ";categoryId:" + category.getFdId() + ";startDate:"
			+ startDate + ";endDate:" + endDate);
		}
		Integer fdShiftType = category.getFdShiftType();
		Integer fdSameWTime = category.getFdSameWorkTime();
		Integer fdPeriodType = category.getFdPeriodType();
		logger.warn("getFdShouldDays,userName:" + docCreator.getFdName()
				+ ";categoryId:" + category.getFdId() + ";startDate:"
				+ startDate + ";endDate:" + endDate + ";shiftType:" + fdShiftType);
		if (AttendConstant.FD_SHIFT_TYPE[0].equals(fdShiftType)
				|| AttendConstant.FDPERIODTYPE_WEEK
						.equals(String.valueOf(fdPeriodType))
				|| AttendConstant.FD_SHIFT_TYPE[1].equals(fdShiftType)) {
			// 工作日
			Map<String,Double> workWeek = new HashMap<>();
			List<String> workDays = new ArrayList<String>();
			if (fdSameWTime == null
					|| AttendConstant.FD_SAMEWTIME_TYPE[0]
							.equals(fdSameWTime)) {
				String fdWeek = category.getFdWeek();
				if (StringUtil.isNotNull(fdWeek)) {
					List<String> days= ArrayUtil.convertArrayToList(fdWeek.split(";"));
					for (String day:days ) {
						workWeek.put(day,1D);
						workDays.add(day);
					}
				}
			} else if (AttendConstant.FD_SAMEWTIME_TYPE[1].equals(fdSameWTime)) { 
				// 一周不同上下班
				List<SysAttendCategoryTimesheet> tSheets = category.getFdTimeSheets();
				if (tSheets != null && !tSheets.isEmpty()) {
					for (SysAttendCategoryTimesheet tSheet : tSheets) {
						String fdWeek = tSheet.getFdWeek();
						if (StringUtil.isNotNull(fdWeek)) {
							//当天的统计是按照半天还是一天来计算
							Float totalTime = tSheet.getFdTotalDay();
							List<String> days= ArrayUtil.convertArrayToList(fdWeek.split(";"));
							for (String day:days ) {
								workWeek.put(day,totalTime.doubleValue());
								workDays.add(day);
							}
						}
					}
				}
			}
			// 休息日
			List<String> offWeek = ArrayUtil.convertArrayToList(new String[] { "1", "2", "3", "4", "5", "6","7" });
			offWeek.removeAll(workDays);

			// 是否排班制
			boolean isTimeArea = AttendConstant.FD_SHIFT_TYPE[1]
					.equals(fdShiftType);
			Calendar cal = Calendar.getInstance();
			for (cal.setTime(startDate); cal.getTime().compareTo(endDate) <= 0; cal.add(Calendar.DATE, 1)) {
				Date date = cal.getTime();
				//排班类型的单独处理
				if (isTimeArea) {
					double tempNUmber = recalShouldDaysOfTimeArea(date,category, docCreator);
					count += tempNUmber;
					if(sysAttendCategoryService.isHoliday(category.getFdId(), date, docCreator, isTimeArea)) {
						//如果是补假
						if(!sysAttendCategoryService.isPatchHolidayDay(category.getFdId(), date)){
							fdHolidays++;
						}
						//fdHolidays++;
					}
					continue;
				}
				String dateWeek = String.valueOf(AttendUtil.getWeek(date));
				Double workDayNumber =0D;
				if (workDays.contains(dateWeek)) {
					// 工作日
					// 排除日期 or 节假日
					if (isExDate(category, date)) {
						// not add
					}
					else if (sysAttendCategoryService.isHolidayPatchDay(category.getFdId(), date)) {
						//节假日补班
						workDayNumber =1D;
					}
					else if (sysAttendCategoryService.isHoliday(category.getFdId(), date)) {
						if(!sysAttendCategoryService.isPatchHolidayDay(category.getFdId(), date)){
							fdHolidays++;
						}
						//fdHolidays++;
					} else {
						workDayNumber =workWeek.get(dateWeek);
					}
				} else if (offWeek.contains(dateWeek)) {
					// 休息日
					// 追加日期 or 补班
					if (isAddDate(category, date) || sysAttendCategoryService.isHolidayPatchDay(category.getFdId(),date)) {
						workDayNumber =1D;
					} else {
						// not add
					}
					if (sysAttendCategoryService.isHoliday(category.getFdId(), date)) {
						if(!sysAttendCategoryService.isPatchHolidayDay(category.getFdId(), date)){
							fdHolidays++;
						}
						//fdHolidays++;
					}
				}
				count +=workDayNumber;
			}
		} else if (AttendConstant.FD_SHIFT_TYPE[2].equals(fdShiftType)
				|| (AttendConstant.FDPERIODTYPE_CUST
						.equals(String.valueOf(fdPeriodType))
						&& fdShiftType == null)) {
			//签到
			if (category.getFdTimes() != null) {
				for (SysAttendCategoryTime time : category
						.getFdTimes()) {
					if (startDate.compareTo(time.getFdTime()) <= 0
							&& endDate.compareTo(time.getFdTime()) >= 0) {
						count++;
					}
				}
			}
		}
		json.put("fdShouldDays", count);
		json.put("fdHolidays", fdHolidays);
		return json;
	}

	/**
	 * 根据考勤组和日期 判定该日的统计 是按天 还是半天
	 * @param category 考勤组
	 * @param docCreator 人员
	 * @param date 日期
	 * @return 如果不存在或者其他场景，默认是按照1天计算
	 * @throws Exception
	 */
	private Double getUserWorkDays(SysAttendCategory category, SysOrgElement docCreator,  Date date)
			throws Exception {
		Double workDayNumber = 1D;
		if (category == null || docCreator ==null || date ==null) {
			return workDayNumber;
		}
		String key=String.format("%s_%s",docCreator.getFdId(), DateUtil.convertDateToString(date,"yyyy-MM-dd"));
		//缓存每个人每日按多少天算。避免重复查询计算
		if(elementWorkDayCatch.get(key) !=null){
			return elementWorkDayCatch.get(key);
		}
		//判断该日如果是节假日补班，则按照1天来算。
		if(sysAttendCategoryService.isHolidayPatchDay(category.getFdId(),date)){
			elementWorkDayCatch.put(key, 1D);
			return elementWorkDayCatch.get(key);
		}
		/**
		 * 排班类型
		 */
		if(AttendConstant.FD_SHIFT_TYPE[1].equals(category.getFdShiftType())){
			//查找这个人对应这天的排班是按天还是按半天
			workDayNumber =sysAttendCategoryService.getWorkTimeAreaTotalDay(docCreator,date).doubleValue();
			elementWorkDayCatch.put(key,workDayNumber);
			return elementWorkDayCatch.get(key);
		}
		Integer fdSameWTime = category.getFdSameWorkTime();
		//根据日期获取今天星期几
		String dateWeek = String.valueOf(AttendUtil.getWeek(date));
		//一周内相同工作时间。0相同，1不相同
		if (AttendConstant.FD_SAMEWTIME_TYPE[1].equals(fdSameWTime)) {
			// 一周不同上下班
			List<SysAttendCategoryTimesheet> tSheets = category.getFdTimeSheets();
			if (tSheets != null && !tSheets.isEmpty()) {
				for (SysAttendCategoryTimesheet tSheet : tSheets) {
					String fdWeek = tSheet.getFdWeek();
					if (StringUtil.isNotNull(fdWeek)) {
						//当天的统计是按照半天还是一天来计算
						List<String> days = ArrayUtil.convertArrayToList(fdWeek.split(";"));
						for (String day : days) {
							if (dateWeek.equals(day)) {
								elementWorkDayCatch.put(key, tSheet.getFdTotalDay().doubleValue());
								return elementWorkDayCatch.get(key);
							}
						}
					}
				}
			}
		}
		return workDayNumber;
	}


	private Double recalShouldDaysOfTimeArea(Date date,
			SysAttendCategory category, SysOrgElement docCreator)
			throws Exception {
		List signTimes = this.sysAttendCategoryService.getAttendSignTimes(category, date, docCreator);
		if (signTimes.isEmpty()) {
			return 0D;
		}
		return getUserWorkDays(category,docCreator,date);
	}

	private boolean isExDate(SysAttendCategory category, Date date) {
		boolean isExDate = false;
		if (category.getFdExcTimes() != null) {
			for (SysAttendCategoryExctime exTime : category
					.getFdExcTimes()) {
				Date exDate = exTime.getFdExcTime();
				if (AttendUtil.isSameDate(date, exDate)) {
					isExDate = true;
					break;
				}
			}
		}
		return isExDate;
	}

	private boolean isAddDate(SysAttendCategory category, Date date) {
		boolean isAddDate = false;
		if (category.getFdTimes() != null) {
			for (SysAttendCategoryTime time : category
					.getFdTimes()) {
				Date fdTime = time.getFdTime();
				if (AttendUtil.isSameDate(date, fdTime)) {
					isAddDate = true;
					break;
				}

			}
		}
		return isAddDate;
	}

	public Boolean getBooleanField(Object field) {
		if (field == null) {
			return null;
		}
		if (field instanceof Number) {
			return ((Number) field).intValue() == 1;
		} else if (field instanceof Boolean) {
			return ((Boolean) field).booleanValue();
		}
		return false;
	}

	@Override
	public void deletStat(String fdCategoryId, Date date,List<String> orgList) throws Exception {
		if ((StringUtil.isNull(fdCategoryId)
				&& (orgList == null || orgList.isEmpty()) || date == null)) {
			return;
		}
		if (orgList == null) {
			orgList = new ArrayList<String>();
		}
		if (StringUtil.isNotNull(fdCategoryId)) {
			List<String> orgIds = sysAttendCategoryService.getAttendPersonIds(fdCategoryId,date);
			orgList.addAll(orgIds);
		}

		if (orgList.isEmpty()) {
			return;
		}
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement statStm = null;
		PreparedStatement delStm = null;
		ResultSet rs = null;
		try {
			Date startTime = AttendUtil.getMonth(date, 0);
			Date endTime = AttendUtil.getMonth(date, 1);
			conn = dataSource.getConnection();
			String listSql = "select fd_id from sys_attend_stat_month where fd_month >=? and fd_month<? and "
					+ HQLUtil.buildLogicIN("doc_creator_id", orgList);
			statStm = conn.prepareStatement(listSql);
			statStm.setTimestamp(1,
					new Timestamp(startTime.getTime()));
			statStm.setTimestamp(2,
					new Timestamp(endTime.getTime()));
			rs = statStm.executeQuery();
			List<String> idList = new ArrayList<String>();
			while (rs.next()) {
				idList.add(rs.getString(1));
			}

			if (!idList.isEmpty()) {
				String sql = "delete from sys_attend_stat_month where "
						+ HQLUtil.buildLogicIN("fd_id", idList);
				delStm = conn.prepareStatement(sql);
				delStm.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			if (delStm != null) {
				JdbcUtils.closeStatement(delStm);
			}
			JdbcUtils.closeStatement(statStm);
			JdbcUtils.closeConnection(conn);
		}
	}

	@Override
	public void deletStat(String fdCategoryId, Date date) throws Exception {
		deletStat(fdCategoryId, date, null);
	}

	public void setSysAttendCategoryService(
			ISysAttendCategoryService sysAttendCategoryService) {
		this.sysAttendCategoryService = sysAttendCategoryService;
	}

	public void
			setSysTimeHolidayService(
					ISysTimeHolidayService sysTimeHolidayService) {

		this.sysTimeHolidayService = sysTimeHolidayService;
	}

	private Map<String,Long> alreadyPatchNumber(List<String> personIds,Date date) throws Exception {
		Date startTime = AttendUtil.getMonth(date, 0);
		Date endTime = AttendUtil.getMonth(date, 1);
		TransactionStatus transactionStatus = TransactionUtils.beginNewReadTransaction();
		Map<String,Long> exitIntegerMap = new HashMap<>();
		try {
			String inPersonIdSql= HQLUtil.buildLogicIN("main.doc_creator_id", personIds);
			String exitSql = "select  count(exc.fd_id) as count2, LEFT(exc.fd_attend_time,10) as attend_time ,main.doc_creator_id  From sys_attend_main_exc exc,sys_attend_main main where main.fd_id = exc.fd_attend_id and "+inPersonIdSql+" and main.doc_Create_Time BETWEEN :startTime  and :endTime and  exc.doc_status = '30' and  (exc.fd_desc = '忘带工牌' or exc.fd_desc = '工牌丢失')   GROUP BY  attend_time, main.doc_creator_id having count2 = 2 ";
			List<Object[]> attLis = getSysAttendMainService().getBaseDao().getHibernateSession().createSQLQuery(exitSql)
					.setParameter("startTime",startTime)
					.setParameter("endTime",endTime)
					.list();
			if (!attLis.isEmpty()) {
				for (Object[] obj : attLis) {
					String pid = (String)  obj[2];
					exitIntegerMap.merge(pid, 1L, Long::sum);
				}
			}
			TransactionUtils.commit(transactionStatus);
		}catch (Exception e){
			logger.error("alreadyPatchNumber(List<String> personIds,Date date) 失败:"+e.getMessage(),e);
			TransactionUtils.rollback(transactionStatus);
		}

		return exitIntegerMap;
	}
	private ISysAttendMainService sysAttendMainService;
	private ISysAttendMainService getSysAttendMainService(){
		if(sysAttendMainService ==null){
			sysAttendMainService= (ISysAttendMainService) SpringBeanUtil.getBean("sysAttendMainService");
		}
		return sysAttendMainService;
	}
}
