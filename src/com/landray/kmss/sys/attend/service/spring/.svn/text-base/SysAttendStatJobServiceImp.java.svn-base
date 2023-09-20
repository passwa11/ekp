
package com.landray.kmss.sys.attend.service.spring;

import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.sql.DataSource;

import com.landray.elasticsearch.rest.util.CollectionUtil;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.attend.service.*;
import jodd.util.ArraysUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.jdbc.support.JdbcUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.framework.spring.event.interfaces.IEventCallBack;
import com.landray.kmss.framework.spring.event.transaction.EventOfTransactionCommit;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.report.HrCurrencyParams;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTimesheet;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.model.SysAttendStatMonth;
import com.landray.kmss.sys.attend.model.SysAttendSyncSetting;
import com.landray.kmss.sys.attend.util.AttendComparableTime;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendOverTimeUtil;
import com.landray.kmss.sys.attend.util.AttendPersonUtil;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.time.interfaces.ISysTimeCountService;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.model.SysTimeOrgElementTime;
import com.landray.kmss.sys.time.model.SysTimeWork;
import com.landray.kmss.sys.time.service.ISysTimeLeaveDetailService;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class SysAttendStatJobServiceImp
		implements ISysAttendStatJobService, IEventMulticasterAware {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendStatJobServiceImp.class);
	private static final Object lock = new Object();
	private ISysAttendStatMonthJobService sysAttendStatMonthJobService;
	private ISysOrgCoreService sysOrgCoreService;
	private ISysAttendBusinessService sysAttendBusinessService;
	private SysQuartzJobContext jobContext = null;
	private IEventMulticaster multicaster;
	private final ReentrantReadWriteLock countLock = new ReentrantReadWriteLock();
	private ThreadLocal<Integer> countThreadLocal =  new ThreadLocal<Integer>();
	private ThreadLocal<Connection> connThreadLocal =  new ThreadLocal<Connection>();
	private ThreadLocal<PreparedStatement> searchSysAttendMainPreparedStatementThreadLocal =  new ThreadLocal<PreparedStatement>();
	private ThreadLocal<PreparedStatement> searchSysAttendStatPreparedStatementThreadLocal =  new ThreadLocal<PreparedStatement>();
	private ThreadLocal<PreparedStatement> searchSysAttendDetailStatPreparedStatementThreadLocal =  new ThreadLocal<PreparedStatement>();
	private ThreadLocal<PreparedStatement> updateSysAttendStatPreparedStatementThreadLocal =  new ThreadLocal<PreparedStatement>();
	private ThreadLocal<PreparedStatement> deleteSysAttendStatPreparedStatementThreadLocal =  new ThreadLocal<PreparedStatement>();
	private ThreadLocal<PreparedStatement> insertSysAttendStatPreparedStatementThreadLocal =  new ThreadLocal<PreparedStatement>();
	private ThreadLocal<PreparedStatement> updateSysAttendDetailStatPreparedStatementThreadLocal =  new ThreadLocal<PreparedStatement>();
	private ThreadLocal<PreparedStatement> insertSysAttendDetailStatPreparedStatementThreadLocal =  new ThreadLocal<PreparedStatement>();
	private ThreadLocal<PreparedStatement> deleteSysAttendDetailStatPreparedStatementThreadLocal =  new ThreadLocal<PreparedStatement>();

	private ISysAppConfigService sysAppConfigService;
	public ISysAppConfigService getSysAppConfigService() {
		if (sysAppConfigService == null) {
			sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean
					("sysAppConfigService");
		}
		return sysAppConfigService;
	}

	private ISysTimeCountService sysTimeCountService;
	public void setSysTimeCountService(
			ISysTimeCountService sysTimeCountService) {
		this.sysTimeCountService = sysTimeCountService;
	}
    private ISysAttendCategoryService sysAttendCategoryService;

	protected ISysAttendCategoryService getSysAttendCategoryService() {
        if (sysAttendCategoryService == null) {
			sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil.getBean("sysAttendCategoryService");
        }
        return sysAttendCategoryService;
    }
	private ISysAttendMainService sysAttendMainService;
	protected ISysAttendMainService getSysAttendMainService() {
		if (sysAttendMainService == null) {
			sysAttendMainService = (ISysAttendMainService) SpringBeanUtil.getBean("sysAttendMainService");
		}
		return sysAttendMainService;
	}
	private ISysAttendMainJobService sysAttendMainJobService;
	protected ISysAttendMainJobService getSysAttendMainJobService() {
		if (sysAttendMainJobService == null) {
			sysAttendMainJobService = (ISysAttendMainJobService) SpringBeanUtil.getBean("sysAttendMainJobService");
		}
		return sysAttendMainJobService;
	}
	private ISysOrgPersonService sysOrgPersonService;
	protected ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}
	private ISysOrgElementService sysOrgElementService;

    public ISysOrgElementService getSysOrgElementService() {
        if (sysOrgElementService == null) {
            sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
        }
        return sysOrgElementService;
    }
	private ISysAttendStatMonthService sysAttendStatMonthService;
	public ISysAttendStatMonthService getSysAttendStatMonthService() {
		if(sysAttendStatMonthService==null){
			sysAttendStatMonthService=(ISysAttendStatMonthService)SpringBeanUtil.getBean("sysAttendStatMonthService");
		}
		return sysAttendStatMonthService;
	}
	private IHrStaffPersonInfoService hrStaffPersonInfoService;
	public IHrStaffPersonInfoService getHrStaffPersonInfoService() {
		if(hrStaffPersonInfoService==null){
			hrStaffPersonInfoService=(IHrStaffPersonInfoService)SpringBeanUtil.getBean("hrStaffPersonInfoService");
		}
		return hrStaffPersonInfoService;
	}
	
	
	
	@Override
	public void setEventMulticaster(IEventMulticaster multicaster) {
		this.multicaster = multicaster;
	}

	public void setSysAttendCategoryService(
			ISysAttendCategoryService sysAttendCategoryService) {
		this.sysAttendCategoryService = sysAttendCategoryService;
	}

	public void setSysAttendStatMonthJobService(
			ISysAttendStatMonthJobService sysAttendStatMonthJobService) {
		this.sysAttendStatMonthJobService = sysAttendStatMonthJobService;
	}

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	public void setSysAttendBusinessService(
			ISysAttendBusinessService sysAttendBusinessService) {
		this.sysAttendBusinessService = sysAttendBusinessService;
	}
	/**
	 * 排班管理的请假明细服务类
	 */
	private ISysTimeLeaveDetailService sysTimeLeaveDetailService;
	private ISysTimeLeaveDetailService getSysTimeLeaveDetailService(){
		if(sysTimeLeaveDetailService ==null){
			sysTimeLeaveDetailService = (ISysTimeLeaveDetailService) SpringBeanUtil.getBean("sysTimeLeaveDetailService");
		}
		return sysTimeLeaveDetailService;
	}
	
	private ISysAttendMainExcService sysAttendMainExcService;
	private ISysAttendMainExcService getSysAttendMainExcService(){
		if(sysAttendMainExcService ==null){
			sysAttendMainExcService = (ISysAttendMainExcService) SpringBeanUtil.getBean("sysAttendMainExcService");
		}
		return sysAttendMainExcService;
	}

	public static Timestamp staticDocCreateTime=null;
	public static String staticBusinessId=null;
	
	/**
	 * 统计某一天的考勤记录。所有人
	 * 统计完以后。统计月
	 * @param date
	 * @throws Exception
	 */
	@Override
	public void stat(Date date) throws Exception {
		final Date beginTime = AttendUtil.getDate(date, 0);
		Date endTime = AttendUtil.getDate(date, 1);
		List<String> personIds = sysAttendCategoryService.getAttendPersonIds(endTime);
		if(CollectionUtils.isNotEmpty(personIds)) {
			Map<String, JSONObject> monthDataMap=initMonthDataMap(personIds, date);
			// 统计数据
			statByPersonIds(beginTime, endTime, personIds,monthDataMap);
			multicaster.attatchEvent(
					new EventOfTransactionCommit(StringUtils.EMPTY),
					new IEventCallBack() {
						@Override
						public void execute(ApplicationEvent arg0)
								throws Throwable {
							sysAttendStatMonthJobService.stat(beginTime);
						}
					});
		}
	}

	/**
	 * 这个月内已经提交了多少次补卡
	 * @param
	 * @return
	 * @throws Exception
	 */
	private Map<String,Long> alreadyPatchNumber(List<String> personIds,Date date,Map<String, List<String>> monthCardDataMap) throws Exception {
		Map<String,Long> integerMap = new HashMap<>();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock(" count(*) , sysAttendMain.docCreator.fdId ");
			StringBuffer whereBlock = new StringBuffer("1=1");
			Date startTime = AttendUtil.getMonth(date, 0);
			Date endTime = AttendUtil.getMonth(date, 1);
			whereBlock.append( " and sysAttendMain.docCreateTime>=:startTime and sysAttendMain.docCreateTime<:endTime");
			hqlInfo.setParameter("startTime", startTime);
			hqlInfo.setParameter("endTime", endTime);

			whereBlock.append(" and sysAttendMain.docCreator.fdId in (:docCreatorId) and (sysAttendMain.fdState=1 or sysAttendMain.fdState=2)");
			hqlInfo.setParameter("docCreatorId",personIds);

			whereBlock.append(" and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
			hqlInfo.setWhereBlock(whereBlock.toString()+" group by sysAttendMain.docCreator.fdId");
			// select  count(1), LEFT(fd_attend_time,10) as attend_time From sys_attend_main_exc where fd_desc = '忘带工牌' or fd_desc ='工牌丢失'   GROUP BY  attend_time
			//统计数量需要排除当天 忘记工牌和工牌丢失 仅计算一天
		String inPersonIdSql= HQLUtil.buildLogicIN("main.doc_creator_id", personIds);
		String exitSql = "select  count(exc.fd_id) as count2, LEFT(exc.fd_attend_time,10) as attend_time ,main.doc_creator_id  From sys_attend_main_exc exc,sys_attend_main main where main.fd_id = exc.fd_attend_id and "+inPersonIdSql+" and main.doc_Create_Time BETWEEN :startTime  and :endTime and  exc.doc_status = '30' and   (exc.fd_desc = '忘带工牌' or exc.fd_desc = '工牌丢失')   GROUP BY  attend_time, main.doc_creator_id having count2 = 2 ";
		List<Object[]> attLis = getSysAttendMainService().getBaseDao().getHibernateSession().createSQLQuery(exitSql)
				.setParameter("startTime",startTime)
				.setParameter("endTime",endTime)
				.list();
		Map<String,Long> exitIntegerMap = new HashMap<>();
		if (!attLis.isEmpty()) {
			for (Object[] obj : attLis) {
				String attendTime = (String)  obj[1];
				String pid = (String)  obj[2];
				exitIntegerMap.merge(pid, 1L, Long::sum);
				if(monthCardDataMap.get(pid) == null){
					List<String> arrayList = new ArrayList<>();
					arrayList.add(attendTime);
					monthCardDataMap.put(pid,arrayList);
				}else{
					List<String> list = monthCardDataMap.get(pid);
					list.add(attendTime);
				}
			}
		}
			List countList = getSysAttendMainService().findValue(hqlInfo);
			if (!countList.isEmpty()) {
				for (Object objCount : countList) {
					Object[] obj = (Object[]) objCount;
					Long count = (Long)  obj[0];
					String pid = (String)  obj[1];
					if(exitIntegerMap.get(pid) !=null){
						count -= exitIntegerMap.get(pid);
					};
					integerMap.put(pid,count);
				}
			}

			return integerMap;
	}

	/**
	 * 初始化累计数据
	 * @param personIds
	 * @param date
	 * @return
	 * @throws Exception 
	 */
	private Map<String, JSONObject> initMonthDataMap(List<String> personIds,Date date) throws Exception{
		Map<String, JSONObject> monthDataMap=new HashMap<String, JSONObject>();
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
	        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
	        c.setTime(date);
	        int dayNum = c.get(Calendar.DATE); 
		if(personIds.isEmpty()){//如果没人获取时间为月初
			return monthDataMap;
		}
		//月度两次忘记工牌时间
		Map<String, List<String>> monthCardDataMap= new HashMap<>();

		Map<String, Long> alreadyPatchNumberMap = alreadyPatchNumber(personIds, date,monthCardDataMap);
		c.add(Calendar.DATE, -1);
		Date newDate=c.getTime();
		String dateStr=dateFormat.format(newDate);
		String insql=HQLUtil.buildLogicIN("doc_creator_id", personIds);
		String sql="SELECT doc_creator_id,fd_month_late_num,fd_month_forger_num,fd_month_late_min_num,fd_delayed_time,fd_Attend_result "
				+ "FROM sys_attend_stat_detail WHERE "+insql+" and fd_date like '%"+dateStr+"%'";
		//根据人员查询上一天的每日汇总数据
		List<Object[]> attList=getSysAttendStatMonthService().getBaseDao().getHibernateSession().createSQLQuery(sql).list();
		for (Object[] objects : attList) {
			if(objects[0]!=null){
				JSONObject data=new JSONObject();
				String personId=(String) objects[0];
				Integer fdMonthLateNum=objToInt(objects[1]);//月迟到次数
				Integer fdMonthForgerNum=objToInt(objects[2]);//月忘打卡次数
				Integer fdMonthLateMinNum=objToInt(objects[3]);//月迟到分钟数
				Integer fdOverTimeWithoutDeduct=objToInt(objects[4]);//延时加班时长
				Integer fdAttendResult=objToInt(objects[5]);//月异常考勤次数

				data.put("fdMonthLateNum", fdMonthLateNum);
				data.put("fdMonthForgerNum", fdMonthForgerNum);
				data.put("fdMonthLateMinNum", fdMonthLateMinNum);
				data.put("fdOverTimeWithoutDeduct", fdOverTimeWithoutDeduct);
				data.put("fdAttendResult", fdAttendResult);

				monthDataMap.put(personId, data);
			}
		}
		for (Map.Entry<String, Long> stringLongEntry : alreadyPatchNumberMap.entrySet()) {
			JSONObject jsonObject = monthDataMap.get(stringLongEntry.getKey());
			if(jsonObject==null){
				JSONObject data=new JSONObject();
				data.put("fdMonthAlreadyPatchNumber",stringLongEntry.getValue());
				//重新统计次数缓存
				data.put("isRecountTimes", dayNum == 1 );
				data.put("fdRecountTimesNumber",0);
				//add by shuh at 20230309, 记录月忘记工牌补卡日期
				if(monthCardDataMap.get(stringLongEntry.getKey())!=null){
					data.put("fdMonthCardDataMap",monthCardDataMap.get(stringLongEntry.getKey()));
				}
				monthDataMap.put(stringLongEntry.getKey(),data);
			}else{
				jsonObject.put("fdMonthAlreadyPatchNumber",stringLongEntry.getValue());
				//重新统计次数缓存
				jsonObject.put("isRecountTimes", dayNum == 1 );
				jsonObject.put("fdRecountTimesNumber",0);
				//add by shuh at 20230309, 记录月忘记工牌补卡日期
				if(monthCardDataMap.get(stringLongEntry.getKey())!=null){
					jsonObject.put("fdMonthCardDataMap",monthCardDataMap.get(stringLongEntry.getKey()));
				}
			}
		}
		return monthDataMap;
	}
	
	private Integer objToInt(Object obj){
		Integer result=0;
		if(obj!=null){
			if(obj instanceof Integer){
				result=(Integer)obj;
			}
			if(obj instanceof String){
				try {
					result=Integer.valueOf((String)obj);
				} catch (Exception e) {
				}
			}
		}
		return result;
	}

	@Override
	public void stat( Date date, Boolean isStatMonth,
			final List<String> orgList,Map<String, JSONObject> monthDataMap) throws Exception {
		final Date beginTime = AttendUtil.getDate(date, 0);
		Date endTime = AttendUtil.getDate(date, 1);
		if(CollectionUtils.isNotEmpty(orgList)) {
			// 统计数据
			statByPersonIds(beginTime, endTime,  orgList,monthDataMap);
			// 统计月汇总
			if (Boolean.TRUE.equals(isStatMonth)) {

				multicaster.attatchEvent(
						new EventOfTransactionCommit(StringUtils.EMPTY),
						new IEventCallBack() {
							@Override
							public void execute(ApplicationEvent arg0)
									throws Throwable {
								sysAttendStatMonthJobService.stat(null, beginTime, orgList);
							}
						});
			}
		}
	}

	@Override
	public void execute(SysQuartzJobContext jobContext) throws Exception {
		/**
		 * 查询上次执行和本次执行之间有考勤记录的人员ID
		 */
		SysAttendSyncSetting syncSetting = new SysAttendSyncSetting();
		String oldUpdateTime = syncSetting.getStatJobTime();
		String lastUpdateTime = null;
		String newUdpateTime =null;
		try {
			// 实时统计当天数据
			this.jobContext = jobContext;
			Date beginTime = DateUtil.getDate(0);
			Date endTime = DateUtil.getDate(1);
			List statPersonIdList =new ArrayList();
			lastUpdateTime = oldUpdateTime;
			if(StringUtil.isNull(lastUpdateTime)){
				//如果上一次同步时间为空，则取今天日期之后的。
				lastUpdateTime=DateUtil.convertDateToString(beginTime, "yyyy-MM-dd HH:mm:ss.SSS");
			}
			newUdpateTime = DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
			//取上一次同步时间之后有考勤记录的人员ID 取考勤记录的人员、取流程记录中的人员
			statPersonIdList.addAll(getRecordAndBusinessUserIds(DateUtil.convertStringToDate(lastUpdateTime)));
			if(CollectionUtils.isNotEmpty(statPersonIdList)) {
				Map<String, JSONObject> monthDataMap=initMonthDataMap(statPersonIdList, beginTime);
				statByPersonIds(beginTime, endTime,  statPersonIdList,monthDataMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			newUdpateTime =oldUpdateTime;
			logger.error("考勤统计异常："+e.getMessage());
		} finally {
			//记录最后同步时间
			syncSetting.setStatJobTime(newUdpateTime);
			syncSetting.save();
		}
	}

	/**
	 * 获取某个时间点以后所有的考勤记录人员ID
	 * @param beginTime
	 * @return
	 * @throws SQLException
	 */
	private Set<String> getRecordAndBusinessUserIds(Date beginTime) throws SQLException {

		DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Set<String> orgList = new HashSet<>();
		try {
			Date dayBeginTime = DateUtil.getDate(-1);
			//同步时间最小时间为前一天
			if(dayBeginTime.getTime() > beginTime.getTime()){
				beginTime =dayBeginTime;
			} else {
				Calendar cal = Calendar.getInstance();
				cal.setTime(beginTime);
				cal.add(Calendar.SECOND, -6);
			}
			//为了避免增量时间，只能限制是今天开始
			String SELECT_SQL = " select fd_person_id from sys_attend_syn_ding where doc_create_time >=? and fd_person_id is not null  group by fd_person_id ";
			conn = dataSource.getConnection();
			try {
				statement = conn.prepareStatement(SELECT_SQL);
				statement.setTimestamp(1, new Timestamp(beginTime.getTime()));
				rs = statement.executeQuery();
				while (rs.next()) {
					String temp =rs.getString(1);
					if(StringUtil.isNotNull(temp) && !"null".equalsIgnoreCase(temp)) {
						orgList.add(temp);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				JdbcUtils.closeResultSet(rs);
				JdbcUtils.closeStatement(statement);
			}
			try {
				SELECT_SQL ="select t.fd_org_id from sys_attend_business b INNER JOIN sys_attend_business_target t on b.fd_id=t.fd_business_id where b.doc_create_time >=? and t.fd_org_id  is not null  group by t.fd_org_id ";
				statement = conn.prepareStatement(SELECT_SQL);
				statement.setTimestamp(1, new Timestamp(beginTime.getTime()));
				rs = statement.executeQuery();
				while (rs.next()) {
					String temp =rs.getString(1);
					if(StringUtil.isNotNull(temp) && !"null".equalsIgnoreCase(temp)) {
						orgList.add(temp);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				JdbcUtils.closeResultSet(rs);
				JdbcUtils.closeStatement(statement);
			}

			//为了避免增量时间，只能限制是今天开始
			String SELECT_MAIN_SQL = " select doc_creator_id from sys_attend_main where doc_alter_time >=? and doc_alter_time < ? and doc_creator_id is not null group by doc_creator_id ";
			try {
				statement = conn.prepareStatement(SELECT_MAIN_SQL);
				statement.setTimestamp(1, new Timestamp(beginTime.getTime()));
				statement.setTimestamp(2, new Timestamp(AttendUtil.getDate(beginTime,1).getTime()));
				rs = statement.executeQuery();
				while (rs.next()) {
					String temp =rs.getString(1);
					if(StringUtil.isNotNull(temp) && !"null".equalsIgnoreCase(temp)) {
						orgList.add(temp);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				JdbcUtils.closeResultSet(rs);
				JdbcUtils.closeStatement(statement);
			}

			//为了避免增量时间，只能限制是今天开始
			String SELECT_MAIN_TWO_SQL = " select doc_creator_id from sys_attend_main where doc_create_time >=? and doc_create_time < ? and doc_creator_id is not null  group by doc_creator_id ";
			try {
				statement = conn.prepareStatement(SELECT_MAIN_TWO_SQL);
				statement.setTimestamp(1, new Timestamp(beginTime.getTime()));
				statement.setTimestamp(2, new Timestamp(AttendUtil.getDate(beginTime,1).getTime()));
				rs = statement.executeQuery();
				while (rs.next()) {
					String temp =rs.getString(1);
					if(StringUtil.isNotNull(temp) && !"null".equalsIgnoreCase(temp)) {
						orgList.add(temp);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				JdbcUtils.closeResultSet(rs);
				JdbcUtils.closeStatement(statement);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConnection(conn);
		}
		return orgList;
	}

	private void refreshSession(IBaseDao baseDao) {
		baseDao.getHibernateSession().flush();
		baseDao.getHibernateSession().clear();
	}

	/**
	 * 统计单人的考勤记录
	 * @param orgElement 人员
	 * @param date 日期
	 * @throws Exception
	 */
	@Override
	public void stat(SysOrgElement orgElement, Date date,Map<String, JSONObject> monthDataMap) throws Exception {
		if(monthDataMap==null){
			List<String> personIds=new ArrayList<>();
			personIds.add(orgElement.getFdId());
			 monthDataMap=initMonthDataMap(personIds,date);
		}
		logger.debug("SysAttendStatJob orgElement start,userName:"
				+ orgElement.getFdName() + ";date:" + date);
		stat(orgElement, date, true,monthDataMap);
	}

	/**
	 * 单组织对象 统计
	 * @param orgElement
	 * @param date
	 * @param isStatMonth  是否需要重新统计月度数据
	 * @throws Exception
	 */
	@Override
	public void stat(SysOrgElement orgElement, Date date, Boolean isStatMonth,Map<String, JSONObject> monthDataMap)
			throws Exception {
		try {
			this.initCount();
			// hibernate与jdbc同表操作引起数据不一致
			IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
			refreshSession(baseDao);
			// 防止延迟加载失败
			orgElement = sysOrgCoreService.findByPrimaryKey(orgElement.getFdId());
			Date beginTime = AttendUtil.getDate(date, 0);
			Date endTime = AttendUtil.getDate(date, 1);
			List<String> tmpList = new ArrayList<String>();
			tmpList.add(orgElement.getFdId());
			Map<String,List<Object[]>> recordList = this.getSignedRecord(convertOrgEleIds2Map(tmpList), beginTime, endTime);
			Map<String, JSONObject> statMap = new HashMap<String, JSONObject>();
			Map<String, SysAttendCategory> catesMap = new HashMap<String, SysAttendCategory>();
			// 获取用户具体信息
			List<SysOrgElement> eleList = new ArrayList<SysOrgElement>();
			eleList.add(orgElement);
			List<String> orgIdList = new ArrayList<String>();
			orgIdList.add(orgElement.getFdId());
			statUserInfo(recordList, catesMap, statMap, eleList);
			putTotalData(statMap, monthDataMap, date);
			recalUserInfo(recordList, eleList, orgIdList, statMap, catesMap, beginTime, endTime,monthDataMap);
			//putTotalData(statMap, monthDataMap, date);
			// 插入用户统计信息
			List<String> orgIdsList = new ArrayList(statMap.keySet());
			addBatch(statMap, beginTime, endTime,
					SysTimeUtil.getUserAuthAreaMap(orgIdsList));
			addDetailBatch(statMap, beginTime, endTime);
			logger.debug("SysAttendStatJob orgElement end...");

			logger.debug("SysAttendStatJob:statAcross orgElement start...");
			// 另外对跨天数据做统计，防止statMap混乱
			this.statAcross(orgElement, date,monthDataMap);
			logger.debug("SysAttendStatJob:statAcross orgElement end...");
			if (Boolean.TRUE.equals(isStatMonth)) {
				final SysOrgElement ele = orgElement;
				final Date newDate = date;
				multicaster.attatchEvent(
						new EventOfTransactionCommit(StringUtils.EMPTY),
						new IEventCallBack() {
							@Override
							public void execute(ApplicationEvent arg0)
									throws Throwable {
								sysAttendStatMonthJobService.stat(ele, newDate);
							}
						});
			}
		}catch (Exception e) {
			// TODO: handle exception
			logger.debug("SysAttendStatJob error..."+e.getMessage());
		}finally {
			this.release();
		}

	}

	@Override
	public void stat(List<String> orgIdList, List<Date> dateList)
			throws Exception {
		//需要统计的人员是空，则不处理
		if(CollectionUtils.isNotEmpty(orgIdList)) {
			Set<Date> monthSet = new HashSet<Date>();
			for (Date date : dateList) {
				Calendar ca = Calendar.getInstance();
				ca.setTime(date);
				ca.set(Calendar.DAY_OF_MONTH, 1);
				monthSet.add(AttendUtil.getDate(ca.getTime(), 0));
			}
			// 性能优化，不根据用户统计
			for (Date date : dateList) {
				Map<String, JSONObject> monthDataMap=initMonthDataMap(orgIdList, date);
				stat( date, false, orgIdList,monthDataMap);
			}
			// 重新统计月数据
			final List<Date> monthList = new ArrayList<Date>();
			monthList.addAll(monthSet);
			final List<String> eleList = new ArrayList<String>();
			eleList.addAll(orgIdList);
			multicaster.attatchEvent(
					new EventOfTransactionCommit(StringUtils.EMPTY),
					new IEventCallBack() {
						@Override
						public void execute(ApplicationEvent arg0)
								throws Throwable {
							sysAttendStatMonthJobService.statMonth(eleList, monthList);
						}
					});
		}
	}

	/**
	 * 统计具体的人员考勤
	 * @param beginTime
	 * @param endTime
	 * @param personList
	 * @throws Exception
	 */
	private void statByPersonIds(Date beginTime, Date endTime, List<String> personList,Map<String, JSONObject> monthDataMap)
			throws Exception {
		if(monthDataMap==null){
			System.out.println("数据是null？？？");
		}
		try {
			logger.debug("SysAttendStatJob start...");
			this.initCount();
			//统计非跨天的数据
			Set<String> orgList = new HashSet<>();
			if (personList != null) {
				orgList.addAll(personList);
			}
			List<String> totalPerson = Lists.newArrayList(orgList);
			//处理非跨天的考勤统计
			if(CollectionUtils.isNotEmpty(totalPerson)) {
				this.startProcess(beginTime, endTime, totalPerson,monthDataMap);
				for (Entry<String, JSONObject> stringJSONObjectEntry : monthDataMap.entrySet()) {
					stringJSONObjectEntry.getValue().put("preRecountTimesNumber", stringJSONObjectEntry.getValue().getIntValue("fdRecountTimesNumber"));
					stringJSONObjectEntry.getValue().put("preMonthLateNum", stringJSONObjectEntry.getValue().getIntValue("fdMonthLateNum"));
				}
				/**
				 * 跨天的人员列表
				 * 先根据日期和人员过滤有跨天打卡的人员。在根据人员重新统计，
				 * 减少程序执行的内容 增加性能
				 */

				//当天有流程的人，可能是跨昨天的排班，这些人的昨天数据在统计一次
				List<String> acrossProcessPerson = sysAttendBusinessService.findBussTargetList(totalPerson, beginTime, endTime, null, false);
				
				//今天是否有跨天打卡的标识人员
				List<String> acrossPerson = hasAcrossRecord(totalPerson,beginTime);
				beginTime = AttendUtil.getDate(beginTime, -1);
				endTime = AttendUtil.getDate(endTime, -1);
				try{
				if(acrossProcessPerson!=null && acrossProcessPerson.size()!=0)
				for(String personId : acrossProcessPerson){
					HQLInfo hqlInfo =new HQLInfo();
					StringBuffer whereBlock = new StringBuffer("1=1");
					whereBlock.append(" and docCreator=:person and docCreateTime>:beginTime and docCreateTime<:endTime");
					hqlInfo.setWhereBlock(whereBlock.toString());
					SysOrgPerson person = (SysOrgPerson) getSysOrgPersonService().findByPrimaryKey(personId);
					hqlInfo.setParameter("person", person);
					hqlInfo.setParameter("beginTime", beginTime);
					hqlInfo.setParameter("endTime", endTime);
					hqlInfo.setSelectBlock("fdIsAcross");
					List<Boolean> list = getSysAttendMainService().findValue(hqlInfo);
					if(list!=null && !list.get(0))
					acrossProcessPerson.remove(personId);
				}
				}catch(Exception e){
					e.printStackTrace();
				}
				if(CollectionUtils.isNotEmpty(acrossPerson) || CollectionUtils.isNotEmpty(acrossProcessPerson)){
					List<String> totalYesterdayPerson =Lists.newArrayList();
					if(CollectionUtils.isNotEmpty(acrossPerson)){
						totalYesterdayPerson.addAll(acrossPerson);
					}
					if(CollectionUtils.isNotEmpty(acrossProcessPerson)){
						totalYesterdayPerson.addAll(acrossProcessPerson);
					}
					try{
					for (Entry<String, JSONObject> stringJSONObjectEntry : monthDataMap.entrySet()) {
						for(String personId : acrossProcessPerson){
							if(stringJSONObjectEntry.getKey().equals(personId)){
							stringJSONObjectEntry.getValue().put("fdRecountTimesNumber", stringJSONObjectEntry.getValue().getIntValue("fdPreRecountTimesNumber"));
							stringJSONObjectEntry.getValue().put("fdMonthLateNum", stringJSONObjectEntry.getValue().getIntValue("fdPreMonthLateNum"));;
							stringJSONObjectEntry.getValue().put("fdMonthLateMinNum", stringJSONObjectEntry.getValue().getIntValue("fdPreMonthLateMinNum"));
							}
						}
						for(String personId : acrossPerson){
							if(stringJSONObjectEntry.getKey().equals(personId)){
							stringJSONObjectEntry.getValue().put("fdRecountTimesNumber", stringJSONObjectEntry.getValue().getIntValue("fdPreRecountTimesNumber"));
							stringJSONObjectEntry.getValue().put("fdMonthLateNum", stringJSONObjectEntry.getValue().getIntValue("fdPreMonthLateNum"));
							stringJSONObjectEntry.getValue().put("fdMonthLateMinNum", stringJSONObjectEntry.getValue().getIntValue("fdPreMonthLateMinNum"));
							}
						}
					}
					}catch(Exception e){
						e.printStackTrace();
					}
					this.startProcess(beginTime, endTime, totalYesterdayPerson,monthDataMap);
					beginTime = AttendUtil.getDate(beginTime, 1);
					endTime = AttendUtil.getDate(endTime, 1);
					this.startProcess(beginTime, endTime, totalYesterdayPerson,monthDataMap);
//					for (Entry<String, JSONObject> stringJSONObjectEntry : monthDataMap.entrySet()) {
//						stringJSONObjectEntry.getValue().put("fdRecountTimesNumber", stringJSONObjectEntry.getValue().getIntValue("preRecountTimesNumber"));
//						stringJSONObjectEntry.getValue().put("fdMonthLateNum", stringJSONObjectEntry.getValue().getIntValue("preMonthLateNum"));
//					}
				}
			}
			logger.debug("SysAttendStatJob end...");
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("SysAttendStatJob error...",e);
			throw new Exception(e);
		}finally {
			this.release();
			AttendPersonUtil.release();
		}
	}

	/**
	 * 开始统计
	 * @param beginTime 统计的开始时间
	 * @param endTime 统计的结束时间
	 * @param orgList 统计的人员
	 * @throws Exception
	 */
	private void startProcess(Date beginTime, Date endTime, List<String> orgList,Map<String, JSONObject> monthDataMap) throws Exception {
		if (!orgList.isEmpty()) {
			// 用户组分割
			int maxCount = 1000;
			List<List> groupLists = new ArrayList<List>();
			
			Set<String> orgSet = new HashSet<String>(orgList);
			orgList.clear();
			orgList.addAll(orgSet);
			if (orgList.size() <= maxCount) {
				groupLists.add(orgList);
			} else {
				groupLists = AttendUtil.splitList(orgList, maxCount);
			}
			// 缓存考勤组信息
			Map<String, SysAttendCategory> catesMap = new HashMap<String, SysAttendCategory>();
			// 3.统计每个用户信息
			Map<String, JSONObject> statMap = new HashMap<String, JSONObject>(orgList.size() * 2);
			// 2.用户组的考勤记录 用户分页处理
			for (int i = 0; i < groupLists.size(); i++) {
				
				//使用多线程来进行统计。1000个人1个线程,加快人员多时的处理速度

				Map<String,List<Object[]>>  recordList = this.getSignedRecord(convertOrgEleIds2Map(groupLists.get(i)), beginTime, endTime);
				//获取用户对象
				List<SysOrgElement> eleList = AttendPersonUtil.expandToPersonSimple(groupLists.get(i));
				if(CollectionUtils.isNotEmpty(eleList)) {
					//打卡记录不存在
					if(MapUtils.isNotEmpty(recordList)) {
						statUserInfo(recordList, catesMap, statMap, eleList);
					}
					// 3.填入累计数据
					putTotalData(statMap, monthDataMap, beginTime);
					// 4.重新计算
					recalUserInfo(recordList, eleList, groupLists.get(i), statMap, catesMap, beginTime, endTime,monthDataMap);
					
					
					// 5.插入用户统计信息
//					addBatch(statMap, beginTime, endTime, SysTimeUtil.getUserAuthAreaMap(orgList));
					addBatch(statMap, monthDataMap,beginTime, endTime, SysTimeUtil.getUserAuthAreaMap(orgList));
//					addDetailBatch(statMap, beginTime, endTime);
					addDetailBatch(statMap,monthDataMap, beginTime, endTime);
					statMap.clear();
				}
			}
		}
	}
	
	/**
	 * 
	 * @param statMap 每个用户信息（每日数据）（迟到次数-fdLateCount、月忘打卡次数、月迟到分钟数、延时加班时长）
	 * @param monthDataMap 每个用户需要汇总的数据（迟到次数、月忘打卡次数、月迟到分钟数、延时加班时长）
	 * @throws Exception 
	 */
	private void putTotalData(Map<String, JSONObject> statMap,Map<String, JSONObject> monthDataMap,Date date) throws Exception {
		if (statMap != null && monthDataMap != null) {
			//获取统计人当前时间申请的忘打卡记录
			Map<String, Integer> fdMonthForgerMap = getSysAttendMainExcService().getNumByPerson(new ArrayList<>(statMap.keySet()), date);
			for (String personId : statMap.keySet()) {
				JSONObject personDayData = statMap.get(personId);
				JSONObject personMonthData = monthDataMap.get(personId);
				if (personDayData == null) {
					continue;
				}
				
				if (personMonthData == null) {
					logger.info(personId);
					StackTraceElement[] stackElements = new Throwable().getStackTrace();
			        if(stackElements != null)
			        {
			            for(int i = 0; i < stackElements.length; i++)
			            {
			                logger.info(""+ stackElements[i]); 
			            }
			        }
					if("1821e4edc7002767a5b666642d6b49f3".equals(personId))logger.info("李楚丹");
					personMonthData = new JSONObject();
				}
				//月迟到次数
				int fdLateCount = objToInt(personDayData.getInteger("fdLateCount"));//迟到次数
				int fdMonthLateNum = objToInt(personMonthData.getInteger("fdMonthLateNum")) + fdLateCount;
				personMonthData.put("fdMonthLateNum", fdMonthLateNum);
				personDayData.put("fdMonthLateNum", fdMonthLateNum);
				//月忘打卡次数
				int fdMonthForgerNum = objToInt(fdMonthForgerMap.get(personId));
				personMonthData.put("fdMonthForgerNum", fdMonthForgerNum);
				personDayData.put("fdMonthForgerNum", fdMonthForgerNum);
				//月迟到分钟数fdLateTime
				int fdLateTime = objToInt(personDayData.getInteger("fdLateTime"));//迟到次数
				int fdMonthLateMinNum = objToInt(personMonthData.getInteger("fdMonthLateMinNum")) + fdLateTime;
				logger.info(personId);
					logger.info("fdMonthLateMinNum:"+fdMonthLateMinNum);
					logger.info("fdLateTime:"+fdLateTime);
					logger.info("personMonthData:"+personMonthData.toJSONString());
				personMonthData.put("fdMonthLateMinNum", fdMonthLateMinNum);
				personDayData.put("fdMonthLateMinNum", fdMonthLateMinNum);
				//延时加班时长fdOverTime
				int fdOverTime = objToInt(personDayData.getInteger("fdOverTime"));//迟到次数
				int fdOverTimeWithoutDeduct = objToInt(personMonthData.getInteger("fdOverTimeWithoutDeduct"));
				personMonthData.put("fdOverTimeWithoutDeduct", fdOverTime + fdOverTimeWithoutDeduct);
				personDayData.put("fdOverTimeWithoutDeduct", fdOverTime + fdOverTimeWithoutDeduct);
				//考勤结果
//				1、如果当天考勤上午或下午的“状态”出现早退、旷工，考勤结果显示为异常；
//				2、如果当天考勤上午或下午出现“迟到”，则需要判断迟到是否在允许范围内。本月迟到还未达到3次或没有超过30分钟，则考勤结果为正常，如果本月迟到达到4次以上或超过30分钟，则考勤结果显示为异常。
				//状态为0:缺卡，2:迟到，3：早退
				int fdAttendResult = 0;//默认为正常

				List<Map<String, Object>> record = (List<Map<String, Object>>) personDayData.get("record");//班次信息。
				if (record != null && record.size() > 0) {
					if(CategoryUtil.getCategoryById((String) personDayData.get("fdCategoryId")).getFdShiftType()==4){
						boolean first = false,second = false;
						int i=0;
						for (Map<String, Object> map : record) {
							Integer fdStatus = (Integer) map.get("fdStatus");
							Integer fdState = (Integer) map.get("fdState");
							i++;
							if (fdState == null || fdState != 2) {
								if (0 == fdStatus || 3 == fdStatus) {
									if(i==1)
										first=true;
									if(i==2)
										second=true;
								}
								if (2 == fdStatus && (fdMonthLateNum > 3 || fdMonthLateMinNum > 30)) {//如果迟到需要校验迟到次数
									fdAttendResult = 1;
									break;
								}
							}

						}
						if(first&&second)fdAttendResult = 1;
					}else
					for (Map<String, Object> map : record) {
						Integer fdStatus = (Integer) map.get("fdStatus");
						Integer fdState = (Integer) map.get("fdState");
						if (fdState == null || fdState != 2) {
							if (0 == fdStatus || 3 == fdStatus) {
								fdAttendResult = 1;
								break;
							}
							if (2 == fdStatus && (fdMonthLateNum > 3 || fdMonthLateMinNum > 30)) {//如果迟到需要校验迟到次数
								fdAttendResult = 1;
								break;
							}
						}

					}
				} else {
					fdAttendResult = 1;
				}
				personDayData.put("fdAttendResult", fdAttendResult);
				monthDataMap.put(personId, personMonthData);
			}
		}
	}

	/**
	 * 计算人员的考勤信息
	 * @param recordList 考勤记录列表
	 * @param catesMap 人员跟考勤组的封装数据
	 * @param statMap 返回数据的封装
	 * @param eleList 人员列表
	 */
	private void statUserInfo(Map<String,List<Object[]>>  recordList,
			Map<String, SysAttendCategory> catesMap,
			Map<String, JSONObject> statMap, List<SysOrgElement> eleList) {
		/**
		 * 根据考勤记录来计算人员的考勤数据
		 */
		for(Map.Entry<String,List<Object[]>> records: recordList.entrySet()) {
			//每个人的打卡记录
			List<Object[]> recordListObj = records.getValue();
			if(CollectionUtils.isEmpty(recordListObj)) {
				continue;
			}
			for(Object[] record : recordListObj) {
				Timestamp docCreateTime = (Timestamp) record[2];
				Number fdWorkType = (Number) record[3];
				Timestamp fd_base_work_time = (Timestamp) record[18];
				String fd_business_id = (String) record[17];
				if(fdWorkType.intValue()==0){
					staticDocCreateTime=fd_base_work_time;
					staticBusinessId=fd_business_id;
				}
			}
		}
		for(Map.Entry<String,List<Object[]>> records: recordList.entrySet()) {
			//每个人的打卡记录
			List<Object[]> recordListObj = records.getValue();
			if(CollectionUtils.isEmpty(recordListObj)) {
				continue;
			}
			int flag = 0;
			for(Object[] record : recordListObj) {
				try {	
					String fdCategoryId = (String) record[5];
					SysAttendCategory sysAttendCategory1 = CategoryUtil.getCategoryById(fdCategoryId);
					Number fdState = (Number) record[7];
					Number fdStatus = (Number) record[1];
					if(fdState != null && fdState.intValue() == 2 &&sysAttendCategory1.getFdShiftType()==4 || fdStatus!=null && fdStatus.intValue()!=0 &&sysAttendCategory1.getFdShiftType()==4)
						flag++;
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			for(Object[] record : recordListObj) {
				//record 其实就是sys_attend_main 对象
				//考勤人员
				String docCreatorId = null;
				try {
					docCreatorId = (String) record[0];
					//打卡状态
					Number fdStatus = (Number) record[1];
					//打卡时间
					Timestamp docCreateTime = (Timestamp) record[2];
					Number fdWorkType = (Number) record[3];
					//是否外勤
					Boolean fdOutside = getBooleanField(record[4]);
					//考勤组
					String fdCategoryId = (String) record[5];
					String fdWorkId = (String) record[6];
					Number fdState = (Number) record[7];
					Number fdDateType = (Number) record[8];
					Number fdOffType = (Number) record[9];
					String docCreatorHId = (String) record[10];
					Boolean fdIsAcross = getBooleanField(record[11]);
					String fdWorkKey = (String) record[12];
					//打卡地点
					String fdLocation = (String) record[13];
					//打卡Wifi名称
					String fdWifiName = (String) record[14];
					String fdAppName = (String) record[15];
					
					String fd_business_id = (String) record[17];
					Timestamp fd_base_work_time = (Timestamp) record[18];
					
					// 排班制
					fdWorkId = StringUtil.isNotNull(fdWorkId) ? fdWorkId : fdWorkKey;
					int fdStatusValue = fdStatus.intValue();
					
//					String categoryId = sysAttendCategoryService.getAttendCategory((SysOrgElement)sysOrgElementService.findByPrimaryKey(docCreatorId),new Date(fd_base_work_time.getTime()));
//					SysAttendCategory sysAttendCategory1 = (SysAttendCategory) sysAttendCategoryService.findByPrimaryKey(categoryId);
//					if(sysAttendCategory1.getFdShiftType()==4&&fdStatusValue==1)
//						flag++;
					// 异常审批通过为true
					boolean isOk = (fdState != null && fdState.intValue() == 2) ? true : false;
					SysAttendCategory sysAttendCategory1 = CategoryUtil.getCategoryById(fdCategoryId);
					if(sysAttendCategory1.getFdShiftType()==4&&fdStatusValue==1)
						flag++;
					
					// TODO: 2021-8-19 因为人员考勤组变更，然后这里只会统计当前考勤组，这里需要改造。
					// TODO: 有效考勤中的打卡考勤组，应该找当时所在考勤组的信息，而不是找现在所在考勤组
					// 2021-09-30改造获取考勤组
					// 考勤组信息
					if (!catesMap.containsKey(fdCategoryId)) {
						SysAttendCategory sysAttendCategory = CategoryUtil.getCategoryById(fdCategoryId);
						catesMap.put(fdCategoryId, sysAttendCategory);
					}
					SysAttendCategory cate = catesMap.get(fdCategoryId);
					if(cate ==null){
						logger.error("无法获取用户考勤组:"+fdCategoryId);
						if (jobContext != null) {
							jobContext.logError("无法获取用户考勤组:"+fdCategoryId);
						}
						continue;
					}
					// 用户考勤记录信息
					if (!statMap.containsKey(docCreatorId)) {
						statMap.put(docCreatorId, new JSONObject());
					}
					JSONObject userInfo = statMap.get(docCreatorId);
					// 考勤状态
					if(flag!=0&&sysAttendCategory1.getFdShiftType()==4)
						fdStatusValue=1; 
					if(sysAttendCategory1.getFdShiftType()==4)
					statStatus(true,userInfo, fdStatusValue, isOk, fdOutside);
					else
					statStatus(false,userInfo, fdStatusValue, isOk, fdOutside);
					
					
					// 考勤组
					userInfo.put("fdCategoryId", fdCategoryId);
					userInfo.put("fdCategoryName", cate.getFdName());
					SysOrgElement docCreator =this.getAttendPerson(eleList, docCreatorId);
					if("0".equals(docCreatorHId) && null!=docCreator) {
						docCreatorHId=docCreator.getFdHierarchyId();
					}
					if (StringUtil.isNotNull(docCreatorHId)&&!"0".equals(docCreatorHId)) {
						userInfo.put("docCreatorHId", docCreatorHId);
					}
					//重新计算该日是休息日还是工作日
					// 日期类型
					userInfo.put("fdDateType",
							fdDateType != null ? fdDateType.intValue() : 0);
					// 考勤记录
					statRecordDetail(userInfo, fdWorkId, fdWorkType, fdStatus,
							docCreateTime, fdOutside, fdState, fdLocation,
							fdWifiName, docCreator,
							fdIsAcross, cate, fdAppName);
					// 迟到/早退时间
					statLateOrLeftTime(userInfo, fdWorkId, fdWorkType, fdStatus, docCreateTime, cate, fdState, docCreator, fdIsAcross, fd_base_work_time, fd_business_id);
					// 午休时间
					statRestTime(userInfo, cate, docCreateTime, fdIsAcross, fdWorkId, fdWorkType, docCreator);
					// 总工时
					statTotalTime(userInfo, fdWorkId, fdWorkType, fdStatus, docCreateTime, fdState, cate, fdLocation, fdWifiName, fdAppName);
					// 加班工时 这里不计算。由重新计算方法完成
					//statOverTime(userInfo, fdWorkId, fdWorkType, fdStatus, docCreateTime, cate, fdDateType, fdState, fdIsAcross, docCreator);
					//综合工时计算早退时间


					// 请假记录个数
					statOffCountDetail(userInfo, fdStatusValue, fdOffType);


				} catch (Exception e) {
					e.printStackTrace();
					logger.error("非法数据忽略处理,用户id:" + docCreatorId, e);
					if (jobContext != null) {
						jobContext.logError("非法数据,忽略处理", e);
					}
				}
			}
		}
	}
	/**
	 * 重新计算该日期是否是工作日还是节假日还是休息日
	 * @param category
	 * @param userInfo
	 * @param ele
	 * @param beginTime
	 * @param endTime
	 * @throws Exception
	 */
	private void genDateType(SysAttendCategory category ,JSONObject userInfo , SysOrgElement ele, Date beginTime,
							 Date endTime) throws Exception {
		boolean isTimeArea = Integer.valueOf(1).equals(category.getFdShiftType());
		Integer fdDateType = 0;
		boolean isHoliday=false;
		if(isTimeArea){
			String categoryId = sysAttendCategoryService.getAttendCategory(ele,
					beginTime);
			isHoliday=sysAttendCategoryService.isHoliday(categoryId, beginTime, ele, isTimeArea);
			fdDateType = StringUtil.isNotNull(categoryId)?0:1;
		}else{
			Boolean isNeeded = sysAttendCategoryService.isAttendNeeded(category,
					beginTime);
			isHoliday=sysAttendCategoryService.isHoliday(category.getFdId(), beginTime, ele, isTimeArea);
			fdDateType = Boolean.TRUE.equals(isNeeded) ? 0 : 1;
		}
		if(isHoliday) {
			fdDateType=2;
		}
		userInfo.put("fdDateType", fdDateType);
	}
	/**
	 * 统计后重新计算人员的考勤
	 * @param recordList 考勤数据
	 * @param eleList 人员列表
	 * @param eleIdList 人员ID
	 * @param statMap 考勤相关封装数据
	 * @param catesMap 考勤组的分类封装数据
	 * @param beginTime 统计开始时间
	 * @param endTime 统计结束时间
	 * @throws Exception
	 */
	private void recalUserInfo(Map<String,List<Object[]>>  recordList,
							   List<SysOrgElement> eleList,
			List<String> eleIdList, Map<String, JSONObject> statMap,
			Map<String, SysAttendCategory> catesMap, Date beginTime,
			Date endTime,Map<String, JSONObject> monthDataMap) throws Exception {
		//获取用户组请假数据
		List leaveList = getLeaveRecord(eleIdList, beginTime, AttendUtil.getDate(endTime, 1));
		List<Integer> fdTypes = new ArrayList<Integer>();
		fdTypes.add(7);//外出
		fdTypes.add(6);//加班
		//获取销假和加班的流程信息进行比较
		List<SysAttendBusiness> busList = sysAttendBusinessService.findBussList(eleIdList, beginTime, AttendUtil.getDate(endTime, 1),fdTypes);
		if(logger.isDebugEnabled()) {
			logger.debug("获取加班和外出的流程列表:"+ busList.size());
		}
		Date beginDate = AttendUtil.getDate(beginTime, 0);
		for (SysOrgElement ele : eleList) {
			try {
				String docCreatorId = ele.getFdId();
				//获取当前人员月汇总数据，里面有本月迟到时长和迟到次数beginTime
				//20221208
				JSONObject monthData=null;
				if(monthDataMap!=null&&monthDataMap.get(docCreatorId)!=null){
					monthData=monthDataMap.get(docCreatorId);
				}
				
				boolean setDateType =false;
				if (!statMap.containsKey(docCreatorId)) {
					// 补全统计数据，防止某个人没有打卡记录不生成其统计数据
					genUserInfo(catesMap, statMap, ele, beginTime);
				} else {
					setDateType =true;
				}
				JSONObject json = statMap.get(docCreatorId);
				
				
				String fdCategoryId = (String) json.get("fdCategoryId");
				if (StringUtil.isNull(fdCategoryId)) {
					continue;
				}
				if (!catesMap.containsKey(fdCategoryId)) {
					SysAttendCategory sysAttendCategory = CategoryUtil.getCategoryById(fdCategoryId);
					catesMap.put(fdCategoryId, sysAttendCategory);
				}
				SysAttendCategory category = catesMap.get(fdCategoryId);

				if(setDateType) {
					//重新计算的时候，计算该日是休息日还是工作日。前面根据打卡数据来计算 可能不准确。
					genDateType(category, json, ele, beginTime, endTime);
				}
 				int fdLateTime = json.getInteger("fdLateTime");
				int fdLeftTime = json.getInteger("fdLeftTime");
				int fdTripCount = json.getInteger("fdTripCount");
				JSONObject offCountDetail = json.getJSONObject("fdOffCountDetail");
				boolean fdAbsent = json.getInteger("fdAbsent") == 1 ? true : false;//是否旷工
				boolean fdMissed = json.getInteger("fdMissed") == 1 ? true : false;//是否缺卡
				int fdMissedCount = json.getInteger("fdMissedCount");//缺卡次数
				int fdMissedExcCount = json.getInteger("fdMissedExcCount");//补卡次数
				//fdMissedCount=fdMissedCount - fdMissedExcCount;


				int fdDateType = json.getInteger("fdDateType");
				boolean isRestDay = fdDateType == 1 || fdDateType == 2;//休息日、节假日

				//当前人员有效的流程数据
				List<SysAttendBusiness> busRecordList = sysAttendBusinessService.genUserBusiness(ele, beginDate, busList);
				// 外出时间
				float fdOutgoingTime = recalOutgoingTime(busRecordList, ele, beginDate);
				json.put("fdOutgoingTime", fdOutgoingTime);

				if (!json.containsKey("fdOverTimeWithoutDeduct")) {
					json.put("fdOverTimeWithoutDeduct", 0);
				}
				if(logger.isDebugEnabled()) {
					logger.debug("加班转换假期额度配置：{}",category.getFdConvertOverTimeHour());
				}
				//标准的工作时长
				statStandWorkTime(json,category,ele,beginDate);
				//加班多少小时转换成1天
				json.put("fdConvertOverTime",category.getFdConvertOverTimeHour());
				//每个人的打卡记录分组
				List<Object[]> records =recordList.get(ele.getFdId());
				//是否是出差
				boolean fdTrip = false;
				if(json.containsKey("fdTrip") && json.getInteger("fdTrip") == 1){
					fdTrip = true;
				}
				//计算流程加班申请时长
				int _fdOverApplyTime = recalOverApplytime(category, busRecordList, ele, beginDate, isRestDay, null);
				//计算流程加班转调休时长
				int _fdOverTurnApplyTime = recalOverApplytime(category, busRecordList, ele, beginDate, isRestDay, "1");
				//计算流程加班加班费时长
				int _fdOverPayApplyTime = recalOverApplytime(category, busRecordList, ele, beginDate, isRestDay, "2");
				//加班时间计算
				int _fdOverTime =  recalOvertime(category, busRecordList, records, ele, beginDate, isRestDay, null);
				//加班转调休时长计算
				int _fdOverTurnTime = recalOvertime(category, busRecordList, records, ele, beginDate, isRestDay, "1");
				//加班加班费时长计算
				int _fdOverPayTime = recalOvertime(category, busRecordList, records, ele, beginDate, isRestDay, "2");
				//周末出差加班申请时间超过8小时，实际加班时间固定8小时
				if (fdTrip && isRestDay) {
					_fdOverTime = _fdOverApplyTime >= 480 ? 480 : _fdOverApplyTime;
					_fdOverTurnTime = _fdOverTurnApplyTime >= 480 ? 480 : _fdOverTurnApplyTime;
					_fdOverPayTime = _fdOverPayApplyTime >= 480 ? 480 : _fdOverPayApplyTime;
				}
				logger.debug("加班时间:{},加班转调休时间:{},加班转加班费时间:{},加班申请时间:{},加班转调休申请时间:{},加班转加班费申请时间:{}",
						_fdOverTime, _fdOverTurnTime, _fdOverPayTime, _fdOverApplyTime, _fdOverTurnApplyTime, _fdOverPayApplyTime);
				//加班时间
				json.put("fdOverTime", AttendUtil.floor(_fdOverTime));
				//加班转调休时间
				json.put("fdOverTurnTime", AttendUtil.floor(_fdOverTurnTime));
				//加班转加班费时间
				json.put("fdOverPayTime", AttendUtil.floor(_fdOverPayTime));
				//加班申请时间
				json.put("fdOverApplyTime", AttendUtil.floor(_fdOverApplyTime));
				//加班转调休申请时间
				json.put("fdOverTurnApplyTime", AttendUtil.floor(_fdOverTurnApplyTime));
				//加班转加班费申请时间
				json.put("fdOverPayApplyTime", AttendUtil.floor(_fdOverPayApplyTime));
				/**
				 * 重新同步假期额度
				 * 考勤统计加班时间大于0 开始同步
				 * 2021-07-19
				 */
				if(CollectionUtils.isNotEmpty(busRecordList)) {
					//存在加班流程。则处理假期额度
					for (SysAttendBusiness business : busRecordList) {
						if(Integer.valueOf(6).equals(business.getFdType())) {
							sysAttendBusinessService.addOvertime(business,json);
						}
					}
				}
				Boolean fdIsPatch = category.getFdIsPatch();//是否允许补卡
				Integer fdPatchTimes = category.getFdPatchTimes() == null ? 0 : category.getFdPatchTimes();
				// 旷工天数
				float fdAbsentDays=(float) (fdMissedCount*0.5);//旷工天数=缺卡次数*0.5
				//事假
				float fdPersonalLeaveDays = recalAbsentDays(fdLateTime, fdLeftTime,
						category.getFdLateToAbsentTime(),
						category.getFdLeftToAbsentTime(),
						category.getFdLateToFullAbsTime(),
						category.getFdLeftToFullAbsTime(), fdAbsent,monthData,category.getFdLateTotalTime(),category.getFdLateNumTotalTime());

				fdPersonalLeaveDays = fdMissedCount== 2 ? 0 :  fdPersonalLeaveDays;

				/*if(fdMissed&&fdMissedCount>=1){
					fdAbsentDays=(float) (fdMissedCount*0.5);//旷工天数=缺卡次数*0.5
				}else{
					//事假
					fdPersonalLeaveDays = recalAbsentDays(fdLateTime, fdLeftTime,
								category.getFdLateToAbsentTime(),
								category.getFdLeftToAbsentTime(),
								category.getFdLateToFullAbsTime(),
								category.getFdLeftToFullAbsTime(), fdAbsent,monthData,category.getFdLateTotalTime(),category.getFdLateNumTotalTime());
				}*/
				/*json.put("fdPersonalLeave", !fdMissed&&!fdAbsent);
				if(!fdMissed&&!fdAbsent){//如果不缺卡且不旷工，那么转事假
					json.put("fdPersonalLeaveDays", fdAbsentDays);
					json.put("fdAbsentDays", 0);
				}else{
					if(fdMissed && category.getFdShiftType() == 4 && fdMissedCount >= 1){//如果有缺卡,并且是不定时工作制 直接旷工一天
						if(fdMissedCount > 1 && fdMissedExcCount ==0 ){
							fdAbsentDays = 1;
						}else if(fdMissedExcCount > 0){
							fdAbsentDays = 0;
						}
					}
					json.put("fdAbsentDays", fdAbsentDays);
					json.put("fdPersonalLeaveDays", 0);
				}*/
				json.put("fdAbsentDays", fdAbsentDays);
				json.put("fdPersonalLeaveDays", fdPersonalLeaveDays);
				if(category != null && category.getFdShiftType() == 4 && fdMissedCount >= 1){//如果有缺卡,并且是不定时工作制 直接旷工一天
					if(fdMissedCount > 1 && fdMissedExcCount ==0 ){
						fdAbsentDays = 1f;
						json.put("authAreaId", "orgId");
					}else{
						fdAbsentDays = 0f;
						json.put("fdPersonalLeaveDays", 0);
					}
					json.put("fdAbsentDays", fdAbsentDays);
				}

				//补卡超过次数 转事假
				if(fdIsPatch && monthData != null){
					Integer fdMonthAlreadyPatchNumber = monthData.getInteger("fdMonthAlreadyPatchNumber") ==null ? 0 : monthData.getInteger("fdMonthAlreadyPatchNumber");
					//如果是每月一号 开始统计前几次数据
					Boolean isRecountTimes = monthData.getBoolean("isRecountTimes") == null ? false :monthData.getBoolean("isRecountTimes");
					Integer fdRecountTimesNumber = monthData.getInteger("fdRecountTimesNumber");
					if(isRecountTimes && fdMonthAlreadyPatchNumber > fdPatchTimes &&   fdRecountTimesNumber < fdPatchTimes){

							String martBeginTime = DateUtil.convertDateToString(beginTime, "yyyy-MM-dd");
							//如果当前两次忘记工牌 记0.5天 beginTime
							List<String> monthCardData = (List<String>) monthData.get("fdMonthCardDataMap");
							if (monthCardData != null && monthCardData.contains(martBeginTime)) {
								monthData.put("fdRecountTimesNumber", fdRecountTimesNumber + 1);
								json.put("fdPersonalLeaveDays",0);
							} else {
								if((fdRecountTimesNumber + fdMissedExcCount) > fdPatchTimes){
									json.put("fdPersonalLeaveDays",0.5);
								}else{
									monthData.put("fdRecountTimesNumber", fdRecountTimesNumber + fdMissedExcCount);
									json.put("fdPersonalLeaveDays",0);
								}
							}
					}else{
						//临界值 兼容当天已有缺卡和旷工情况
						if( fdMonthAlreadyPatchNumber  > fdPatchTimes && fdMissedExcCount > 0){
							if(fdMonthAlreadyPatchNumber.equals((fdPatchTimes+1)) && fdMissedExcCount == 2){
								json.put("fdPersonalLeaveDays",0.5);
							}else{
								String martBeginTime = DateUtil.convertDateToString(beginTime, "yyyy-MM-dd");
								//如果当前两次忘记工牌 记0.5天 beginTime
								List<String> monthCardData = (List<String>)monthData.get("fdMonthCardDataMap");
								if(monthCardData !=null && monthCardData.contains(martBeginTime)){
									json.put("fdPersonalLeaveDays",0.5);
								}else{
									json.put("fdPersonalLeaveDays",(fdMissedExcCount*0.5));
								}
							}
						}
					}
				}

				// 出差天数
				int fdWorkTimeSize = getWorkTimes(category,
						AttendUtil.getDate(beginTime, 0), ele).size();
				fdWorkTimeSize = fdWorkTimeSize <= 0 ? 1 : fdWorkTimeSize;
				float fdTripDays = recalTripDays(fdTripCount, fdWorkTimeSize);
				json.put("fdTripDays", fdTripDays);
				// 获取请假数据
				List userLeaveList = sysAttendBusinessService.genUserBusiness(ele, beginDate, leaveList);
				// 统计请假数据
				Map<String, JSONObject> offDataMap = recalOffDayAndTime(
						offCountDetail, fdWorkTimeSize, userLeaveList, beginDate,
						isRestDay, category, ele,json);
				Map map;
				Float standWorkTime = null;
				if(Integer.valueOf(1).equals(category.getFdShiftType()))
				try {
					map = getSysAppConfigService()
							.findByKey(
									"com.landray.kmss.sys.time.model.SysTimeLeaveConfig");
					String str = (String) map.get("dayConvertTime");
					standWorkTime=Float.valueOf(str).floatValue();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				else
					standWorkTime=(Float) json.get("fdStandWorkTime");
				JSONObject offDataJson = formatOffDataJson(standWorkTime,offDataMap);
				// 请假总天数
				double fdOffDays = offDataJson.containsKey("totalDay")
						? offDataJson.getDouble("totalDay") : 0f;
				json.put("fdOffDays", fdOffDays);
				// 请假总小时数
				double fdOffTime = offDataJson.containsKey("totalHour")
						? offDataJson.getDouble("totalHour") : 0f;
				json.put("fdOffTime", fdOffTime);
				// 详细请假数据，格式如{"1":{count:2, statType:3}, "NaN":{count:1.0, statType:1}}, NaN表示请假类型为空
				json.put("fdOffCountDetail", offDataJson);
				if (json.containsKey("fdIsNoRecord")
						&& json.getBoolean("fdIsNoRecord")
						&& (fdOffDays > 0 || fdOffTime > 0)) {
					json.put("fdOff", 1);
				}

				//

			} catch (Exception e) {
				e.printStackTrace();
				logger.error(
						"重新计算用户每日考勤统计数据出错,忽略处理!userName:" + ele.getFdName(), e);
				if (jobContext != null) {
					jobContext.logError(
							"重新计算用户每日考勤统计数据出错,忽略处理!userName:" + ele.getFdName(),
							e);
				}
			}
		}
	}

	/**
	 * 标准的工作时间
	 * @param userInfo 统计信息
	 * @param category 考勤组
	 * @param docCreator 人员
	 * @param date 日期
	 * @throws Exception
	 */
	private void statStandWorkTime(JSONObject userInfo,SysAttendCategory category,SysOrgElement docCreator,Date date) throws Exception {
		//排班类型
		if (AttendConstant.FD_SHIFT_TYPE[1].equals(category.getFdShiftType())) {
			List<Map<String, Object>> signTimeList = sysAttendCategoryService.getAttendSignTimes(category,date,docCreator);
			if(CollectionUtils.isNotEmpty(signTimeList)){
				int workMis = SysTimeUtil.getStandWorkTime(docCreator.getFdId(),date,signTimeList);
				userInfo.put("fdStandWorkTime", Float.valueOf(workMis / 60F));
			} else {
				userInfo.put("fdStandWorkTime", 0F);
			}
		} else {
			//获取用户的班次信息，班次信息中存储了每天的总工时。
			List<Map<String, Object>> signTimeList = sysAttendCategoryService.getAttendSignTimes(category,date,docCreator);
			if(CollectionUtils.isNotEmpty(signTimeList)){
				Map<String, Object> signTimeInfo =signTimeList.get(0);
				userInfo.put("fdStandWorkTime", signTimeInfo.get("fdWorkTimeHour"));
			}else{
				userInfo.put("fdStandWorkTime", category.getFdTotalTime());
			}
		}
	}

	/**
	 * 补全统计数据
	 * 防止某人没有打卡，并且没有排班情况下计算加班的场景
	 *
	 * @param catesMap
	 * @param statMap
	 * @param ele
	 * @param beginTime
	 * @throws Exception
	 */
	private void genUserInfo(Map<String, SysAttendCategory> catesMap,
			Map<String, JSONObject> statMap, SysOrgElement ele, Date beginTime) throws Exception {
		String docCreatorId = ele.getFdId();
		statMap.put(docCreatorId, new JSONObject());
		JSONObject userInfo = statMap.get(docCreatorId);
		//获取用户的考勤组，不过滤排班情况的考勤组
		String fdCategoryId = sysAttendCategoryService.getCategory(ele,beginTime);
		// 用户没有考勤组
		if (StringUtil.isNull(fdCategoryId)) {
			logger.warn("用户没有分配考勤组,忽略该用户统计!userName:" + ele.getFdName() +" :beginTime:"+beginTime);
			return;
		}
		if (!catesMap.containsKey(fdCategoryId)) {
			SysAttendCategory sysAttendCategory = CategoryUtil.getCategoryById(fdCategoryId);
			catesMap.put(fdCategoryId, sysAttendCategory);
		}
		SysAttendCategory category = catesMap.get(fdCategoryId);
		//排班类型
		boolean isTimeArea = Integer.valueOf(1).equals(category.getFdShiftType());
		//默认是休息日
		Integer fdDateType = 1;
		boolean isHoliday=false;

		if(isTimeArea){
			isHoliday=sysAttendCategoryService.isHoliday(fdCategoryId, beginTime, ele, isTimeArea);
		} else {
			isHoliday=sysAttendCategoryService.isHoliday(category.getFdId(), beginTime, ele, isTimeArea);
		}
		if(isHoliday) {
			fdDateType=2;
		}else {
			Boolean isNeeded = sysAttendCategoryService.isNeedSign(category,beginTime,beginTime.getTime(),beginTime.getTime(),ele);
			fdDateType = Boolean.TRUE.equals(isNeeded) ? 0 : 1;
		}
		userInfo.put("fdCategoryId", fdCategoryId);
		userInfo.put("fdCategoryName", category.getFdName());
		userInfo.put("docCreatorHId", ele.getFdHierarchyId());
		userInfo.put("fdStatus", 0);
		userInfo.put("fdOutside", 0);
		userInfo.put("fdLate", 0);
		userInfo.put("fdLeft", 0);
		userInfo.put("fdMissed", 1);
		userInfo.put("fdTrip", 0);
		userInfo.put("fdOff", 0);
		userInfo.put("fdAbsent", 0);
		userInfo.put("fdMissedCount", 0);
		userInfo.put("fdOutsideCount", 0);
		userInfo.put("fdLateCount", 0);
		userInfo.put("fdLeftCount", 0);
		userInfo.put("fdTripCount", 0);
		userInfo.put("fdOffCount", 0);
		userInfo.put("fdOffDays", 0);
		userInfo.put("fdOffTime", 0f);
		userInfo.put("fdMissedExcCount", 0);
		userInfo.put("fdLateExcCount", 0);
		userInfo.put("fdLeftExcCount", 0);
		userInfo.put("fdRestTime", 0);
		userInfo.put("restMinsDefault", 0);
		userInfo.put("fdLateTime", 0);
		userInfo.put("fdLeftTime", 0);
		userInfo.put("fdTotalTime", 0);
		userInfo.put("fdOverTime", 0);
		userInfo.put("fdAbsentDays", 0);
		userInfo.put("fdPersonalLeave", 0);
		userInfo.put("fdPersonalLeaveDays", 0);
		userInfo.put("fdOutgoingTime", 0);
		userInfo.put("fdWorkTimeSize", 1);
		userInfo.put("record", new JSONArray());
		userInfo.put("fdDateType", fdDateType);
		userInfo.put("fdOffCountDetail", new JSONObject());
		userInfo.put("fdIsNoRecord", true);
	}

	public List getStatUsers(Date beginTime, Date endTime) throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		String orgSql = "select DISTINCT doc_creator_id from sys_attend_stat where fd_date >=? and fd_date<?";
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<String> orgList = new ArrayList<String>();
		try {
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
			logger.error(e.getMessage(), e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(conn);
		}

		return orgList;
	}

	public List getStatDetailUsers(Date beginTime, Date endTime)
			throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		String orgSql = "select DISTINCT doc_creator_id from sys_attend_stat_detail where fd_date >=? and fd_date<?";
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<String> orgList = new ArrayList<String>();
		try {
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
			logger.error(e.getMessage(), e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(conn);
		}

		return orgList;
	}

	/**
	 * 统计午休时间的分钟数
	 * @param userInfo
	 * @param cate
	 * @param docCreateTime
	 * @param fdIsAcross
	 * @param fdWorkId
	 * @param fdWorkType
	 * @param docCreator
	 * @throws Exception
	 */
	private void statRestTime(JSONObject userInfo, SysAttendCategory cate,
			Timestamp docCreateTime, Boolean fdIsAcross, String fdWorkId,
			Number fdWorkType, SysOrgElement docCreator) throws Exception {
		if (!userInfo.containsKey("fdRestTime")) {
			userInfo.put("fdRestTime", 0);
		}
		if (!userInfo.containsKey("restMinsDefault")) {
			userInfo.put("restMinsDefault", 0);
		}
		JSONArray works = (JSONArray) userInfo.get("record");
		if (works == null || works.isEmpty()) {
			return;
		}
		Date signTime = new Date(docCreateTime.getTime());
		Date date = AttendUtil.getDate(signTime, 0);

		if (Boolean.TRUE.equals(fdIsAcross)) {
			date = AttendUtil.getDate(signTime, -1);
		}
		if (Integer.valueOf(0).equals(cate.getFdShiftType())
				&& Integer.valueOf(1).equals(cate.getFdSameWorkTime())) {
			List<SysAttendCategoryTimesheet> tSheets = cate.getFdTimeSheets();
			for (SysAttendCategoryTimesheet tSheet : tSheets) {
				if (StringUtil.isNotNull(tSheet.getFdWeek())
						&& tSheet.getFdWeek().indexOf(AttendUtil.getWeek(date) + "") > -1) {
					if (tSheet.getAvailWorkTime() != null
							&& tSheet.getAvailWorkTime().size() == 1 ) {
						getRestTimeMins(works,userInfo,fdWorkId,fdWorkType,signTime,
								tSheet.getFdRestStartTime(),
								tSheet.getFdRestEndTime(),
								tSheet.getFdRestStartType(),
								tSheet.getFdRestEndType(),date);
					}
					break;
				}
			}
		} else if (Integer.valueOf(1).equals(cate.getFdShiftType())) {
			// 排班制
			List<Map<String, Object>> signTimeList = this.sysAttendCategoryService
					.getAttendSignTimes(cate, date, docCreator,true);
			// 只有一个班制才有午休时间
			if (CollectionUtils.isNotEmpty(signTimeList) && signTimeList.size() == 2) {
				Map<String, Object> map = signTimeList.get(0);
				Date fdRestStartTime = null;
				Date fdRestEndTime = null;
				Integer fdRestEndType = 1;
				if (map.containsKey("fdRestStartTime")
						&& map.containsKey("fdRestEndTime")) {
					fdRestStartTime = (Date) map.get("fdRestStartTime");
					fdRestEndTime = (Date) map.get("fdRestEndTime");
				}
				Integer fdRestStartType = 1;
				if (map.containsKey("fdRestStartType")){
					fdRestStartType =  (Integer) map.get("fdRestStartType");
				}
				if (map.containsKey("fdRestEndType")){
					fdRestEndType =  (Integer) map.get("fdRestEndType");
				}
				getRestTimeMins(works,userInfo,fdWorkId,fdWorkType,signTime,
						fdRestStartTime,
						fdRestEndTime,
						fdRestStartType,
						fdRestEndType,
						date);
			}
		} else {
			if (cate.getAvailWorkTime() != null
					&& cate.getAvailWorkTime().size() == 1 ) {
				getRestTimeMins(works,userInfo,fdWorkId,fdWorkType,signTime,cate.getFdRestStartTime(),
						cate.getFdRestEndTime(),
						cate.getFdRestStartType(),
						cate.getFdRestEndType(),date);
			}
		}
	}
	private void statRestTime(JSONObject userInfo, SysAttendCategory cate,
			Timestamp docCreateTime, Boolean fdIsAcross, String fdWorkId,
			Number fdWorkType, SysOrgElement docCreator,String businessId) throws Exception {
		if (!userInfo.containsKey("fdRestTime")) {
			userInfo.put("fdRestTime", 0);
		}
		if (!userInfo.containsKey("restMinsDefault")) {
			userInfo.put("restMinsDefault", 0);
		}
		JSONArray works = (JSONArray) userInfo.get("record");
		if (works == null || works.isEmpty()) {
			return;
		}
		Date signTime = new Date(docCreateTime.getTime());
		Date date = AttendUtil.getDate(signTime, 0);

		if (Boolean.TRUE.equals(fdIsAcross)) {
			date = AttendUtil.getDate(signTime, -1);
		}
		if (Integer.valueOf(0).equals(cate.getFdShiftType())
				&& Integer.valueOf(1).equals(cate.getFdSameWorkTime())) {
			List<SysAttendCategoryTimesheet> tSheets = cate.getFdTimeSheets();
			for (SysAttendCategoryTimesheet tSheet : tSheets) {
				if (StringUtil.isNotNull(tSheet.getFdWeek())
						&& tSheet.getFdWeek().indexOf(AttendUtil.getWeek(date) + "") > -1) {
					if (tSheet.getAvailWorkTime() != null
							&& tSheet.getAvailWorkTime().size() == 1 ) {
						getRestTimeMins(works,userInfo,fdWorkId,fdWorkType,signTime,
								tSheet.getFdRestStartTime(),
								tSheet.getFdRestEndTime(),
								tSheet.getFdRestStartType(),
								tSheet.getFdRestEndType(),date,businessId);
					}
					break;
				}
			}
		} else if (Integer.valueOf(1).equals(cate.getFdShiftType())) {
			// 排班制
			List<Map<String, Object>> signTimeList = this.sysAttendCategoryService
					.getAttendSignTimes(cate, date, docCreator,true);
			// 只有一个班制才有午休时间
			if (CollectionUtils.isNotEmpty(signTimeList) && signTimeList.size() == 2) {
				Map<String, Object> map = signTimeList.get(0);
				Date fdRestStartTime = null;
				Date fdRestEndTime = null;
				Integer fdRestEndType = 1;
				if (map.containsKey("fdRestStartTime")
						&& map.containsKey("fdRestEndTime")) {
					fdRestStartTime = (Date) map.get("fdRestStartTime");
					fdRestEndTime = (Date) map.get("fdRestEndTime");
				}
				Integer fdRestStartType = 1;
				if (map.containsKey("fdRestStartType")){
					fdRestStartType =  (Integer) map.get("fdRestStartType");
				}
				if (map.containsKey("fdRestEndType")){
					fdRestEndType =  (Integer) map.get("fdRestEndType");
				}
				getRestTimeMins(works,userInfo,fdWorkId,fdWorkType,signTime,
						fdRestStartTime,
						fdRestEndTime,
						fdRestStartType,
						fdRestEndType,
						date,businessId);
			}
		} else {
			if (cate.getAvailWorkTime() != null
					&& cate.getAvailWorkTime().size() == 1 ) {
				getRestTimeMins(works,userInfo,fdWorkId,fdWorkType,signTime,cate.getFdRestStartTime(),
						cate.getFdRestEndTime(),
						cate.getFdRestStartType(),
						cate.getFdRestEndType(),date,businessId);
			}
		}
	}

	/**
	 * 获取打卡时间占午休时间的分钟数
	 * @param works 班次信息
	 * @param userInfo 返回封装的map
	 * @param fdWorkId 班次id
	 * @param fdWorkType 班次类型，1下班，0上班
	 * @param signTime 打卡时间
	 * @param fdRestStartTime 设置的午休开始时间
	 * @param fdRestEndTime 设置的午休结束时间
	 * @param fdRestEndType 午休结束时间的标识1是当日，2是次日
	 * @param date 统计日期
	 */
	private void getRestTimeMins(JSONArray works,
								 JSONObject userInfo,
								 String fdWorkId,
								  Number fdWorkType,
								  Date signTime,
								  Date fdRestStartTime,
								  Date fdRestEndTime,
								  Integer fdRestStartType,
								  Integer fdRestEndType ,
								  Date date ){
		if(fdRestStartTime ==null || fdRestEndTime ==null){
			return;
		}
		//午休开始时间 如果是次日 则加1天，否则就是 日期时间组合的 日期
		Date tempRestStartTime = AttendUtil.joinYMDandHMS(date,fdRestStartTime);
		Date restStart = Integer.valueOf(2).equals(fdRestStartType)?AttendUtil.addDate(tempRestStartTime,1):tempRestStartTime;
		//午休结束时间  如果是次日 则加1天，否则就是 日期时间组合的 日期
		Date tempRestEndTime = AttendUtil.joinYMDandHMS(date,fdRestEndTime);
		Date restEnd = Integer.valueOf(2).equals(fdRestEndType)?AttendUtil.addDate(tempRestEndTime,1):tempRestEndTime;

		if(restEnd.getTime() > restStart.getTime()) {
			JSONObject record = getSameWork(works, fdWorkId, fdWorkType.intValue());
			if (record == null) {
				return;
			}
			Long createTime = (Long) record.get("docCreateTime");
			// 上下班时间
			long onTime = fdWorkType.intValue() == 0 ? signTime.getTime() : createTime;
			//
			long offTime = fdWorkType.intValue() == 0 ? createTime : signTime.getTime();

			// 休息开始/结束时间
			long _restBegin;
			long _restEnd;
			if (onTime < restStart.getTime()) {
				_restBegin = restStart.getTime();
			} else if (onTime >= restStart.getTime()
					&& onTime < restEnd.getTime()) {// 上班落在午休时间内
				_restBegin = onTime;
			} else {
				_restBegin = restEnd.getTime();
			}

			if (offTime < restStart.getTime()) {
				_restEnd = restStart.getTime();
			} else if (offTime >= restStart.getTime() && offTime < restEnd.getTime()) {
				// 下班落在午休时间内
				_restEnd = offTime;
			} else {
				_restEnd = restEnd.getTime();
			}

			int restMins = (int) (_restEnd - _restBegin) / 60000;
			
			int restMinsDefault = (int) (restEnd.getTime() - restStart.getTime()) / 60000;

			userInfo.put("restEnd11", restEnd);
			userInfo.put("restStart11", restStart);
			userInfo.put("fdRestTime", restMins);
			userInfo.put("restMinsDefault", restMinsDefault);
		}
	}
	private void getRestTimeMins(JSONArray works,
			 JSONObject userInfo,
			 String fdWorkId,
			  Number fdWorkType,
			  Date signTime,
			  Date fdRestStartTime,
			  Date fdRestEndTime,
			  Integer fdRestStartType,
			  Integer fdRestEndType ,
			  Date date,String  businessId){
if(fdRestStartTime ==null || fdRestEndTime ==null){
return;
}
//午休开始时间 如果是次日 则加1天，否则就是 日期时间组合的 日期
Date tempRestStartTime = AttendUtil.joinYMDandHMS(date,fdRestStartTime);
Date restStart = Integer.valueOf(2).equals(fdRestStartType)?AttendUtil.addDate(tempRestStartTime,1):tempRestStartTime;
//午休结束时间  如果是次日 则加1天，否则就是 日期时间组合的 日期
Date tempRestEndTime = AttendUtil.joinYMDandHMS(date,fdRestEndTime);
Date restEnd = Integer.valueOf(2).equals(fdRestEndType)?AttendUtil.addDate(tempRestEndTime,1):tempRestEndTime;

if(restEnd.getTime() > restStart.getTime()) {
JSONObject record = getSameWork(works, fdWorkId, fdWorkType.intValue());
if (record == null) {
return;
}
Long createTime = (Long) record.get("docCreateTime");
// 上下班时间
long onTime = fdWorkType.intValue() == 0 ? signTime.getTime() : createTime;
//
long offTime = fdWorkType.intValue() == 0 ? createTime : signTime.getTime();

// 休息开始/结束时间
long _restBegin;
long _restEnd;
if (onTime < restStart.getTime()) {
_restBegin = restStart.getTime();
} else if (onTime >= restStart.getTime()
&& onTime < restEnd.getTime()) {// 上班落在午休时间内
_restBegin = onTime;
} else {
_restBegin = restEnd.getTime();
}

if (offTime < restStart.getTime()) {
_restEnd = restStart.getTime();
} else if (offTime >= restStart.getTime() && offTime < restEnd.getTime()) {
// 下班落在午休时间内
_restEnd = offTime;
} else {
_restEnd = restEnd.getTime();
}

int restMins = (int) (_restEnd - _restBegin) / 60000;
try {
	SysAttendBusiness sysAttendBusiness = (SysAttendBusiness) sysAttendBusinessService.findByPrimaryKey(businessId);
	int businessTime = 0;
	if(sysAttendBusiness.getFdBusEndTime().getTime()<onTime||sysAttendBusiness.getFdBusStartTime().getTime()>offTime)
		businessTime=(int) (sysAttendBusiness.getFdBusEndTime().getTime() - sysAttendBusiness.getFdBusStartTime().getTime()) / 60000;
	else if(sysAttendBusiness.getFdBusStartTime().getTime()<_restBegin&&sysAttendBusiness.getFdBusStartTime().getTime()<_restEnd)
		businessTime=(int) (_restBegin - sysAttendBusiness.getFdBusStartTime().getTime()) / 60000;
	else if(sysAttendBusiness.getFdBusStartTime().getTime()>_restBegin&&sysAttendBusiness.getFdBusEndTime().getTime()>_restEnd)
		businessTime=(int) (sysAttendBusiness.getFdBusEndTime().getTime() - _restEnd) / 60000;
	else if(sysAttendBusiness.getFdBusStartTime().getTime()<_restBegin&&sysAttendBusiness.getFdBusEndTime().getTime()>_restEnd)
		businessTime=(int) (sysAttendBusiness.getFdBusEndTime().getTime() - _restEnd+sysAttendBusiness.getFdBusStartTime().getTime()-_restBegin) / 60000;
		userInfo.put("businessTime", businessTime);
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
int restMinsDefault = (int) (restEnd.getTime() - restStart.getTime()) / 60000;
userInfo.put("fdRestTime", restMins);
userInfo.put("onTime11", onTime);
userInfo.put("offTime12", offTime);
userInfo.put("restMinsDefault", restMinsDefault);
}
}
	/**
	 * 获取同班次记录
	 *
	 * @param works
	 * @param fdWorkId
	 * @param fdWorkType
	 */
	private JSONObject getSameWork(JSONArray works, String fdWorkId,
			int fdWorkType) {
		for (int i = 0; i < works.size(); i++) {
			JSONObject item = (JSONObject) works.get(i);
			int workType = (Integer) item.get("fdWorkType");
			String workId = (String) item.get("fdWorkId");
			if (workType != fdWorkType && workId.equals(fdWorkId)) {
				return item;
			}
		}
		return null;
	}

	private void statStatus(boolean flag,JSONObject userInfo, int fdStatusValue,
			boolean isOk, Boolean fdOutside) {

		if (!userInfo.containsKey("fdOutside")) {
			userInfo.put("fdOutside", fdOutside ? 1 : 0);
		} else {
			int __fdOutside = (Integer) userInfo
					.get("fdOutside");
			userInfo.put("fdOutside",
					__fdOutside == 1 || fdOutside ? 1 : 0);
		}

		if (!userInfo.containsKey("fdLate")) {
			userInfo.put("fdLate",
					fdStatusValue == 2 && !isOk ? 1 : 0);
			//迟到
		} else {
			int __fdLate = (Integer) userInfo.get("fdLate");
			userInfo.put("fdLate",
					__fdLate == 1 || (fdStatusValue == 2 && !isOk)
							? 1
							: 0);
		}

		if (!userInfo.containsKey("fdLeft")) {
			userInfo.put("fdLeft",
					fdStatusValue == 3 && !isOk ? 1 : 0);
			//早退
		} else {
			int __fdLeft = (Integer) userInfo.get("fdLeft");
			userInfo.put("fdLeft",
					__fdLeft == 1 || (fdStatusValue == 3 && !isOk)
							? 1 : 0);
		}
		// 缺卡
		if (!userInfo.containsKey("fdMissed")) {
			userInfo.put("fdMissed",
					fdStatusValue == 0 && !isOk ? 1 : 0);
			userInfo.put("fdMissedValue",
					fdStatusValue == 0 && !isOk ? "1" : "0");
		} else {
			String __fdMissedValue = userInfo
					.getString("fdMissedValue");
			__fdMissedValue += (fdStatusValue == 0 && !isOk ? "1"
					: "0");
			userInfo.put("fdMissedValue", __fdMissedValue);
//			if (__fdMissedValue.indexOf("1") > -1
//					&& __fdMissedValue.indexOf("0") > -1) {
			if (__fdMissedValue.indexOf("1") > -1) {
				userInfo.put("fdMissed", 1);
			} else {
				userInfo.put("fdMissed", 0);
			}
		}

		// 出差
		if (!userInfo.containsKey("fdTrip")) {
			userInfo.put("fdTrip",
					fdStatusValue == 4 ? 1 : 0);// 流程写入出差记录时会删除异常，故不需判断isOk
		} else {
			int __fdTrip = (Integer) userInfo.get("fdTrip");
			userInfo.put("fdTrip",
					__fdTrip == 1 || fdStatusValue == 4 ? 1 : 0);
		}
		// 请假
		if (!userInfo.containsKey("fdOff")) {
			userInfo.put("fdOff",
					fdStatusValue == 5 ? 1 : 0);
		} else {
			int __fdOff = (Integer) userInfo.get("fdOff");
			userInfo.put("fdOff",
					__fdOff == 1 || fdStatusValue == 5 ? 1 : 0);
		}

		if (!userInfo.containsKey("fdAbsent")) {
			userInfo.put("fdAbsent",
					fdStatusValue == 0 && !isOk ? 1 : 0);
		} else {
			int __fdAbsent = (Integer) userInfo.get("fdAbsent");
			userInfo.put("fdAbsent",
					__fdAbsent == 1 && fdStatusValue == 0 && !isOk
							? 1 : 0);
		}
		// 计算正常状态(打卡正常),非缺卡/旷工/迟到/早退/外勤
		if (!userInfo.containsKey("fdStatus")) {
			userInfo.put("fdStatusEnum", fdStatusValue + ",");
			boolean isBus = fdStatusValue == 4 || fdStatusValue == 5
					|| fdStatusValue == 6;
			userInfo.put("fdStatus",
					(fdStatusValue == 1 || isOk) && !fdOutside || isBus ? 1
							: 0);
		} else {
			String fdStatusEnum = (String) userInfo.get("fdStatusEnum");
			fdStatusEnum = fdStatusEnum + fdStatusValue + ",";
			userInfo.put("fdStatusEnum", fdStatusEnum);

			int fdMissed = (Integer) userInfo.get("fdMissed");
			int fdLate = (Integer) userInfo.get("fdLate");
			int fdLeft = (Integer) userInfo.get("fdLeft");
			int fdAbsent = (Integer) userInfo.get("fdAbsent");
			int outside = (Integer) userInfo.get("fdOutside");
			int status = 1;
			if ((fdMissed == 1&&!flag) || fdLate == 1 || fdLeft == 1 || fdAbsent == 1
					|| outside == 1) {
				status = 0;
			} else {
				if (fdStatusEnum.indexOf("0") > -1
						|| fdStatusEnum.indexOf("1") > -1
						|| fdStatusEnum.indexOf("2") > -1
						|| fdStatusEnum.indexOf("3") > -1) {
				} else {
					// 全天出差/请假/外出
					status = 1;
				}
			}
			userInfo.put("fdStatus", status);
		}

		// 缺卡次数
		if (!userInfo.containsKey("fdMissedCount")) {
			int count = fdStatusValue == 0 && !isOk ? 1 : 0;
			userInfo.put("fdMissedCount", count);
			userInfo.put("_tmp_fdMissedCount", count);
		} else {
			int __tmpFdMissedCount = (Integer) userInfo
					.get("_tmp_fdMissedCount");
			int fdMissed = (Integer) userInfo.get("fdMissed");
			int count = fdStatusValue == 0 && !isOk ? 1 : 0;
			userInfo.put("_tmp_fdMissedCount", __tmpFdMissedCount + count);
			if (fdMissed == 1) {
				userInfo.put("fdMissedCount", __tmpFdMissedCount + count);
			} else {
				userInfo.put("fdMissedCount", 0);
			}
		}

		// 外勤次数
		if (!userInfo.containsKey("fdOutsideCount")) {
			userInfo.put("fdOutsideCount", fdOutside ? 1 : 0);
		} else {
			int __fdOutsideCount = (Integer) userInfo.get("fdOutsideCount");
			__fdOutsideCount = __fdOutsideCount + (fdOutside ? 1 : 0);
			userInfo.put("fdOutsideCount", __fdOutsideCount);
		}
		// 迟到次数
		if (!userInfo.containsKey("fdLateCount")) {
			userInfo.put("fdLateCount", fdStatusValue == 2 && !isOk ? 1 : 0);
		} else {
			int __fdLateCount = (Integer) userInfo.get("fdLateCount");
			__fdLateCount = __fdLateCount
					+ ((fdStatusValue == 2 && !isOk) ? 1 : 0);
			userInfo.put("fdLateCount", __fdLateCount);
		}
		// 早退次数
		if (!userInfo.containsKey("fdLeftCount")) {
			userInfo.put("fdLeftCount", fdStatusValue == 3 && !isOk ? 1 : 0);
		} else {
			int __fdLeftCount = (Integer) userInfo.get("fdLeftCount");
			__fdLeftCount = __fdLeftCount
					+ ((fdStatusValue == 3 && !isOk) ? 1 : 0);
			userInfo.put("fdLeftCount", __fdLeftCount);
		}

		// 出差次数
		if (!userInfo.containsKey("fdTripCount")) {
			userInfo.put("fdTripCount", fdStatusValue == 4 ? 1 : 0);
		} else {
			int __fdTripCount = userInfo.getInteger("fdTripCount");
			__fdTripCount = __fdTripCount
					+ (fdStatusValue == 4 ? 1 : 0);
			userInfo.put("fdTripCount", __fdTripCount);
		}
		// 请假次数
		if (!userInfo.containsKey("fdOffCount")) {
			userInfo.put("fdOffCount", fdStatusValue == 5 ? 1 : 0);
		} else {
			int __fdOffCount = userInfo.getInteger("fdOffCount");
			__fdOffCount = __fdOffCount
					+ (fdStatusValue == 5 ? 1 : 0);
			userInfo.put("fdOffCount", __fdOffCount);
		}
		// 缺卡补卡次数
		if (!userInfo.containsKey("fdMissedExcCount")) {
			userInfo.put("fdMissedExcCount",
					isOk ? 1 : 0);
//			userInfo.put("fdMissedExcCount",
//					fdStatusValue == 0 && isOk ? 1 : 0);
		} else {
			int __fdMissedExcCount = (Integer) userInfo.get("fdMissedExcCount");
			__fdMissedExcCount = __fdMissedExcCount
					+ (isOk ? 1 : 0);
//			__fdMissedExcCount = __fdMissedExcCount
//					+ (fdStatusValue == 0 && isOk ? 1 : 0);
			userInfo.put("fdMissedExcCount", __fdMissedExcCount);
		}
		// 迟到补卡次数
		if (!userInfo.containsKey("fdLateExcCount")) {
			userInfo.put("fdLateExcCount",
					fdStatusValue == 2 && isOk ? 1 : 0);
		} else {
			int __fdLateExcCount = (Integer) userInfo.get("fdLateExcCount");
			__fdLateExcCount = __fdLateExcCount
					+ (fdStatusValue == 2 && isOk ? 1 : 0);
			userInfo.put("fdLateExcCount", __fdLateExcCount);
		}
		// 早退补卡次数
		if (!userInfo.containsKey("fdLeftExcCount")) {
			userInfo.put("fdLeftExcCount",
					fdStatusValue == 3 && isOk ? 1 : 0);
		} else {
			int __fdLeftExcCount = (Integer) userInfo.get("fdLeftExcCount");
			__fdLeftExcCount = __fdLeftExcCount
					+ (fdStatusValue == 3 && isOk ? 1 : 0);
			userInfo.put("fdLeftExcCount", __fdLeftExcCount);
		}

	}
	private void statLateOrLeftTime(JSONObject userInfo, String fdWorkId,
			Number fdWorkType, Number fdStatus, Timestamp docCreateTime,
			SysAttendCategory cate, Number fdState, SysOrgElement docCreator,
			Boolean fdIsAcross, Timestamp fd_base_work_time, String fd_business_id)
			throws Exception {


		Date restStartTime = null;
		Date restEndTime = null;
		List signList = this.sysAttendCategoryService.getAttendSignTimes(docCreator, docCreateTime);
		for (Iterator<Map<String, Object>> it = signList.iterator(); it.hasNext();){
			Map<String, Object> signTimeConfiguration = (Map<String, Object>) it.next();
				restStartTime = (Date) signTimeConfiguration.get("fdRestStartTime");
				restEndTime = (Date) signTimeConfiguration.get("fdRestEndTime");
			}
		// 迟到时间
		if (!userInfo.containsKey("fdLateTime")) {
			userInfo.put("fdLateTime", 0);
		}
		// 早退时间
		if (!userInfo.containsKey("fdLeftTime")) {
			userInfo.put("fdLeftTime", 0);
		}

		// 异常审批通过为true
		boolean isExcOk = (fdState != null && fdState.intValue() == 2)
				? true : false;
		Date workDate = AttendUtil.getDate(docCreateTime, 0);
		if (Boolean.TRUE.equals(fdIsAcross)) {
			workDate = AttendUtil.getDate(docCreateTime, -1);
		}
		if (!isExcOk
				&& (fdStatus.intValue() == 2 || fdStatus.intValue() == 3)) {
			
			//迟到或者早退
			SysAttendCategoryWorktime workTime = this.getWorkTime(cate,
					fdWorkId, fdWorkType.intValue(), docCreator, docCreateTime,
					workDate);
			if (workTime == null) {
				logger.warn("计算用户迟到时间出错,无法找到用户班次信息:用户名:"
						+ docCreator.getFdName() + ";打卡时间:"
						+ docCreateTime);
				return;
			}

			// 是否跨天排班
			Integer fdOvertimeType = workTime.getFdOverTimeType();
			boolean isOverTime = false;
			if (fdOvertimeType != null && (fdOvertimeType == 2)) {
				isOverTime = true;
			}
			Date _signTime = new Date(docCreateTime.getTime());
			if (fdWorkType.intValue() == 0) { //上班
				if (fdStatus.intValue() == 2) { //迟到
					
					Date fdStartTime = workTime.getFdStartTime();
					// 上班时间不能跨天
					_signTime = AttendUtil.getDate(workDate, 0);
					_signTime.setHours(fdStartTime.getHours());
					_signTime.setMinutes(fdStartTime.getMinutes());
					
					SysAttendBusiness sysAttendBusiness = (SysAttendBusiness) sysAttendBusinessService.findByPrimaryKey(fd_business_id);
					if(sysAttendBusiness != null){
						if(sysAttendBusiness.getFdType() == 5){
//							sunny
							_signTime = sysAttendBusiness.getFdBusEndTime();
//							_signTime = fd_base_work_time;
						}
					}
					
					// 迟到
					long _fdLateTime = 0L;
					String isSchedule = "false";
					SysTimeArea sysTimeArea = this.sysTimeCountService.getTimeArea(docCreator);
					if(sysTimeArea!=null){
					List<SysTimeOrgElementTime> orgElementTimeList = sysTimeArea.getOrgElementTimeList();
					for(SysTimeOrgElementTime orgElementTime : orgElementTimeList) {
						List<SysTimeWork> sysTimeWorkList = orgElementTime.getSysTimeWorkList();
						if(sysTimeWorkList!=null && !sysTimeWorkList.isEmpty()) {
							for(SysTimeWork work : sysTimeWorkList) {
								if(work.getFdScheduleDate().getTime()==workDate.getTime()) {
									isSchedule=work.getSysTimeCommonTime().getIsSchedule();
								}
							}
							}
					}
					}
					if(isSchedule!=null && isSchedule.equals("true")){
						_fdLateTime = docCreateTime.getTime()
								- _signTime.getTime()
								- 30 * 60000;
						_fdLateTime = _fdLateTime >= 0 ? _fdLateTime
								: docCreateTime.getTime() - _signTime.getTime();
					}else
					if (Boolean.TRUE.equals(cate.getFdIsFlex())
							&& cate.getFdFlexTime() != null) {// 弹性上下班
						_fdLateTime = docCreateTime.getTime()
								- _signTime.getTime()
								- cate.getFdFlexTime() * 60000;
						_fdLateTime = _fdLateTime >= 0 ? _fdLateTime
								: docCreateTime.getTime() - _signTime.getTime();
					} else {
						if(restStartTime!=null && docCreateTime.getTime()>restStartTime.getTime())
							_fdLateTime = restStartTime.getTime()
							- _signTime.getTime();else
						_fdLateTime = docCreateTime.getTime()
								- _signTime.getTime();
					}
					// 分钟数
					_fdLateTime = _fdLateTime >= 0 ? _fdLateTime
							: 0;
					_fdLateTime = _fdLateTime / 60000;
					int __fdLateTime = (Integer) userInfo
							.get("fdLateTime");
					userInfo.put("fdLateTime",
							__fdLateTime + (int) _fdLateTime);
				}

			} else {//下班
				if (fdStatus.intValue() == 3) {//早退
					//综合工时计算动态下班时间
					Date fdEndTime = workTime.getFdEndTime();

					if(cate.getFdShiftType() == 3){
						//上班打卡时间 + 总工时
						JSONArray works = (JSONArray) userInfo.get("record");
						for (int j = 0; j < works.size(); j++) {
							JSONObject r = (JSONObject) works.get(j);
							// 同班次计算工时
							if (fdWorkId.equals(r.getString("fdWorkId"))
									&& fdWorkType.intValue() != r.getInteger("fdWorkType")) {
								Timestamp docCreateTime2 = new Timestamp(r.getLong("docCreateTime"));
								if(docCreateTime2 != null){
									Float shiftTotalTime = cate.getFdTotalTime() * 60 * 60 * 1000;
									BigDecimal decimal = new BigDecimal(shiftTotalTime.toString());
									Calendar calendar = Calendar.getInstance();
									calendar.setTimeInMillis(decimal.longValue()+docCreateTime2.getTime());
									fdEndTime = calendar.getTime();
								}
							}
						}
					}

					if (isOverTime) {
						fdEndTime = AttendUtil.joinYMDandHMS(
								AttendUtil.addDate(workDate, 1), fdEndTime);
					} else {
						fdEndTime = AttendUtil.joinYMDandHMS(
								AttendUtil.addDate(workDate, 0), fdEndTime);
					}
					Date fdStartTime = workTime.getFdStartTime();
					fdStartTime = AttendUtil.joinYMDandHMS(workDate,
							fdStartTime);
					_signTime.setHours(fdEndTime.getHours());
					_signTime.setMinutes(fdEndTime.getMinutes());
// 早退
					long _fdLeftTime = 0L;
					SysAttendBusiness sysAttendBusiness = null;
					if(fd_business_id!=null)
					 sysAttendBusiness = (SysAttendBusiness) sysAttendBusinessService.findByPrimaryKey(fd_business_id);
					SysAttendBusiness sysAttendBusiness1 = null;
					if(staticBusinessId!=null)
					 sysAttendBusiness1 = (SysAttendBusiness) sysAttendBusinessService.findByPrimaryKey(staticBusinessId);
					if(sysAttendBusiness != null){
						if(sysAttendBusiness.getFdType() == 5){
//							sunny
							_signTime = fd_base_work_time;
							long internal = 0;
							
						}
					}		
					
					if (Boolean.TRUE.equals(cate.getFdIsFlex())
							&& cate.getFdFlexTime() != null) {// 弹性上下班
						// 找出同班次的上班时间
						JSONArray works = (JSONArray) userInfo.get("record");
						Date onTime = null;
						for (int j = 0; j < works.size(); j++) {
							JSONObject r = (JSONObject) works.get(j);
							if (fdWorkId.equals(r.getString("fdWorkId"))
									&& r.getInteger("fdWorkType") == 0) {
								onTime = new Date(r.getLong("docCreateTime"));
								break;
							}
						}
						if(staticBusinessId!=null){
							if( Boolean.TRUE.equals(cate.getFdIsFlex()&&sysAttendBusiness1.getFdBusStartTime().getTime()>staticDocCreateTime.getTime()))
								_fdLeftTime=sysAttendBusiness1.getFdBusStartTime().getTime()-staticDocCreateTime.getTime();
							if(_signTime.getTime()>docCreateTime.getTime())
							_fdLeftTime += _signTime.getTime()
									- docCreateTime.getTime();
						}
						else if (onTime != null) {
							long flexTime = cate.getFdFlexTime() * 60000;
							long tempTime = 0L;
							// 迟到
							if (onTime.getTime() > fdStartTime.getTime()) {
								if (onTime.getTime()
										- fdStartTime.getTime() <= flexTime) {
									tempTime = onTime.getTime()
											- fdStartTime.getTime();
								} else {
									tempTime = flexTime;
								}
								// 早到包含正常
							} else {
								//浮动打卡修改为 基准打卡之后,禁用浮动之前
								if (fdStartTime.getTime()
										- onTime.getTime() <= flexTime) {
									/*tempTime = onTime.getTime()
											- fdStartTime.getTime();*/
								} else {
									tempTime = 0L - flexTime;
								}

							}
							if(fdEndTime.getTime()>docCreateTime.getTime()){
								if(docCreateTime.getTime()<restEndTime.getTime())
									_fdLeftTime=fdEndTime.getTime()-restEndTime.getTime();
								else
							_fdLeftTime = fdEndTime.getTime()
									- docCreateTime.getTime() + tempTime;
							}
							else{
								_fdLeftTime=tempTime;
							_fdLeftTime = _fdLeftTime >= 0 ? _fdLeftTime
									: _signTime.getTime()
											- docCreateTime.getTime();
							}
						} else {// 上班缺卡
							if(_signTime.getTime()>docCreateTime.getTime())
							_fdLeftTime += _signTime.getTime()
									- docCreateTime.getTime();
						}
					} else {
						if(restStartTime!=null && docCreateTime.getTime()<restStartTime.getTime())
							_fdLeftTime += _signTime.getTime()
							- docCreateTime.getTime()+restStartTime.getTime()-restEndTime.getTime();
						else if(restEndTime!=null && docCreateTime.getTime()<restEndTime.getTime())
							_fdLeftTime += _signTime.getTime()
							- restEndTime.getTime();
						
						else
						_fdLeftTime += _signTime.getTime()
								- docCreateTime.getTime();
					}
					_fdLeftTime = _fdLeftTime >= 0 ? _fdLeftTime
							: 0;
					_fdLeftTime = _fdLeftTime / 60000;
					int ___fdLeftTime = (Integer) userInfo
							.get("fdLeftTime");
					userInfo.put("fdLeftTime",
							___fdLeftTime + (int) _fdLeftTime);
				}
			}
		}
	}

	/**
	 * 计算加班时间
	 * @param userInfo
	 * @param fdWorkId
	 * @param fdWorkType 1是一班制、2是2班制
	 * @param fdStatus 当前考勤状态
	 * @param docCreateTime 需要计算的加班结束时间
	 * @param cate 考勤组
	 * @param fdDateType 休息日 节假日 工作日
	 * @param fdState 补卡状态
	 * @param fdIsAcross 是否跨天
	 * @param docCreator
	 * @throws Exception
	 */
	private void statOverTime(JSONObject userInfo, String fdWorkId,
			Number fdWorkType, Number fdStatus, Timestamp docCreateTime,
			SysAttendCategory cate, Number fdDateType, Number fdState,
			Boolean fdIsAcross, SysOrgElement docCreator) throws Exception {
		if (!userInfo.containsKey("fdOverTime")) {// 实际加班工时
			userInfo.put("fdOverTime", 0);
		}
		if (!userInfo.containsKey("fdBeforeOverTime")) {// 取整前实际加班工时
			userInfo.put("fdBeforeOverTime", 0);
		}
		// 没有扣除加班时常的加班数，用于比较与审批单时长比较
		if (!userInfo.containsKey("fdOverTimeWithoutDeduct")) {
			userInfo.put("fdOverTimeWithoutDeduct", 0);
		}

		int fdDateTypeValue = fdDateType == null ? 0
				: fdDateType.intValue();

		boolean isOvertimeDeduct = cate.getFdIsOvertimeDeduct() == null ? false : true;//是否扣除加班 时间
		int fdOvtDeductType = cate.getFdOvtDeductType() == null ? 0 : cate.getFdOvtDeductType(); // 扣除类型 0按时间 1满减
		Date workDate = AttendUtil.getDate(docCreateTime, 0);
		//是否跨天 跨天取打卡时间的前一天
		if (Boolean.TRUE.equals(fdIsAcross)) {
			workDate = AttendUtil.getDate(docCreateTime, -1);
		}

		// 加班统计最小加班单位取整
		if (Boolean.TRUE.equals(cate.getFdIsOvertime())) {
			// 计算实际加班工时 工作日
			if (fdDateTypeValue == 0) {
				//打卡人所在考勤组中的排班时间信息
				SysAttendCategoryWorktime workTime = this.getWorkTime(cate, fdWorkId, fdWorkType.intValue(), docCreator, docCreateTime, workDate);
				if (workTime == null) {
					logger.warn("计算用户加班工时出错,无法找到用户班次信息,将由重新计算取决:用户名:"
							+ docCreator.getFdName() + ";打卡时间:"
							+ docCreateTime);
					return;
				}
				//跨天
				boolean isOverTimeRule = false;
				//下班所在日，是当天还是次日。默认是当天
				if (workTime.getFdOverTimeType() != null && workTime.getFdOverTimeType() == 2) {
					isOverTimeRule = true;
				}
				Date fdEndTime =null;
				if(fdWorkType.intValue() == 0){
					fdEndTime = AttendUtil.joinYMDandHMS(docCreateTime,
							workTime.getFdStartTime());
				} else {
					fdEndTime = AttendUtil.joinYMDandHMS(docCreateTime,
							workTime.getFdEndTime());
				}
				//考勤组设置的下班时间
				if(logger.isDebugEnabled()){
					logger.debug("计算用户加班工时,用户名:" + docCreator + ";打卡时间:" + docCreateTime+";要求考勤时间："+fdEndTime +" ;上班下班标识："+fdWorkType);
				}
				if (fdIsAcross && !isOverTimeRule) {
					// 跨天打卡不跨天排班的下班时间是前一天
					fdEndTime = AttendUtil.addDate(fdEndTime, -1);
				}
				// 异常审批通过为true
				boolean isOk = fdState != null && fdState.intValue() == 2;
				//一班次的打卡时间大于设置的下班时间
				if (fdStatus.intValue() == 4 || fdStatus.intValue() == 1 || isOk) {
					//计算的最后打卡（加班单）时间，减去配置的时间
					long fdOverTimeMis =0;
					if(fdWorkType.intValue() == 1 && docCreateTime.getTime() > fdEndTime.getTime()){
						//如果是下班标识 计算加班时间是：打卡时间 减去 下班要求时间
						fdOverTimeMis = docCreateTime.getTime() - fdEndTime.getTime();
					}
					else if(fdWorkType.intValue() ==0 && docCreateTime.getTime() < fdEndTime.getTime()){
						//如果是上班标识 计算加班时间是： 打卡要求时间 - 打卡时间
						fdOverTimeMis = fdEndTime.getTime() -docCreateTime.getTime();
					}
					if(fdOverTimeMis ==0){
						return;
					}
					//转成分钟计算
					long fdOverTime = (long) Math.ceil(fdOverTimeMis / 60000d);
					long tempFdOverTime = fdOverTime;
					int _fdOverTimeWithoutDeduct = 0;
					if(userInfo.containsKey("fdOverTimeWithoutDeduct")){
						_fdOverTimeWithoutDeduct =Integer.parseInt(userInfo.get("fdOverTimeWithoutDeduct").toString());
					}
					int _fdBeforeOverTime = 0;
					if(userInfo.containsKey("fdBeforeOverTime")){
						_fdBeforeOverTime =Integer.parseInt(userInfo.get("fdBeforeOverTime").toString());
					}
					int deductMins = 0;
					int mjOverTime=0;
					// 扣除加班 (非手工单才计算)
					if (isOvertimeDeduct) {
						switch (fdOvtDeductType) {
						case 0:// 时间段
							deductMins = getDeductMinsForPeriods(docCreateTime,
									cate,
									fdIsAcross, fdEndTime, isOverTimeRule);
							break;
						case 1:// 满减
							deductMins = AttendOverTimeUtil.getDeductMinsForThreahold(cate,
									_fdOverTimeWithoutDeduct + tempFdOverTime);
							int beforeOverTime = _fdOverTimeWithoutDeduct + (int) tempFdOverTime - deductMins;
							userInfo.put("fdBeforeOverTime", beforeOverTime);
							mjOverTime = AttendUtil.getOverTime(cate, beforeOverTime);
							userInfo.put("fdOverTime", mjOverTime);
							// 保存没有扣除加班的时长
							userInfo.put("fdOverTimeWithoutDeduct",
									_fdOverTimeWithoutDeduct
											+ (int) tempFdOverTime);
							return;
						}
					}
					if(logger.isDebugEnabled()){
						logger.debug("计算用户加班工时,用户名:" + docCreator + ";加班时间:" + fdOverTime+";扣除时间："+deductMins);
					}
					fdOverTime = fdOverTime - deductMins < 0 ? 0 : fdOverTime - deductMins;

					userInfo.put("fdBeforeOverTime", (int)fdOverTime);
					fdOverTime = AttendUtil.getOverTime(cate, (int) (_fdBeforeOverTime + fdOverTime));
					userInfo.put("fdOverTime", fdOverTime);

					// 保存没有扣除加班的时长
					userInfo.put("fdOverTimeWithoutDeduct", _fdOverTimeWithoutDeduct + (int) tempFdOverTime);
				}
			} else if (fdDateTypeValue == 1 || fdDateTypeValue == 2) {
				// 休息日，节假日等同于计算总工时
				JSONArray works = (JSONArray) userInfo.get("record");

				for (int j = 0; j < works.size(); j++) {
					JSONObject r = (JSONObject) works.get(j);
					// 同班次计算工时
					if (fdWorkId.equals(r.getString("fdWorkId"))
							&& fdWorkType.intValue() != r
									.getInteger("fdWorkType")) {
						int _fdStatus1 = fdStatus.intValue();
						int _fdStatus2 = r.getInteger("fdStatus");

						Number fdState2 = (Number) r.get("fdState");

						Timestamp docCreateTime2 = new Timestamp(
								r.getLong("docCreateTime"));
						// 异常审批通过为true
						boolean isExcOk1 = fdState != null
								&& fdState.intValue() == 2;
						boolean isExcOk2 = fdState2 != null
								&& fdState2.intValue() == 2;
						if (!isExcOk1 && _fdStatus1 == 0
								|| !isExcOk2 && _fdStatus2 == 0) {
							// 缺卡不计算工时
							continue;
						}
						boolean ret1 = isExcOk1 || _fdStatus1 == 1
								|| _fdStatus1 == 2
								|| _fdStatus1 == 3 || _fdStatus1 == 4;
						boolean ret2 = isExcOk2 || _fdStatus2 == 1
								|| _fdStatus2 == 2
								|| _fdStatus2 == 3 || _fdStatus2 == 4;
						if (ret1 && ret2) {
							docCreateTime.setSeconds(0);
							docCreateTime2.setSeconds(0);
							// 可计算工时
							long overTime = 0;
							overTime = Math.abs(docCreateTime.getTime()
									- docCreateTime2.getTime());
							overTime = overTime >= 0 ? overTime / 60000
									: 0;
							long tempFdOverTime = overTime;

							int __fdBeforeOverTime = userInfo
									.getInteger("fdBeforeOverTime");
							int _fdOverTimeWithoutDeduct = (Integer) userInfo
									.get("fdOverTimeWithoutDeduct");

							int deductMins = 0;
							int mjOverTime = 0;

							// 扣除加班
							if (isOvertimeDeduct) {
								switch (fdOvtDeductType) {
								case 0:// 时间段
									List<JSONObject> periods = new ArrayList<>();
									JSONObject period = new JSONObject();
									// 兼容测试用接口打卡时，打卡时间与插入到数据库的顺序不一致的问题
									if (docCreateTime2.getTime() > docCreateTime.getTime()) {
										logger.warn("计算加班扣除数据可能出错了，可忽略。用户名：" + docCreator.getFdName() +
												", 上班打卡:" + docCreateTime2.getTime() +
												", 下班打卡：" + docCreateTime.getTime());
										Timestamp temp = docCreateTime2;
										docCreateTime2 = docCreateTime;
										docCreateTime = temp;
									}
									period.put("fdStartTime",
											docCreateTime2.getTime());
									period.put("fdEndTime",
											docCreateTime.getTime());
									periods.add(period);
									deductMins = AttendOverTimeUtil.getDeductMins(cate, periods);
									break;
								case 1:// 满减
									deductMins = AttendOverTimeUtil.getDeductMinsForThreahold(cate,
											_fdOverTimeWithoutDeduct
													+ (int) tempFdOverTime);
									int beforeOverTime = _fdOverTimeWithoutDeduct + (int) tempFdOverTime - deductMins;
									userInfo.put("fdBeforeOverTime", beforeOverTime);
									mjOverTime = AttendUtil.getOverTime(cate, beforeOverTime);
									userInfo.put("fdOverTime", mjOverTime);
									// 保存没有扣除加班的时长
									userInfo.put("fdOverTimeWithoutDeduct",
											_fdOverTimeWithoutDeduct
													+ (int) tempFdOverTime);
									return;
								}
							}


							overTime = overTime - deductMins < 0 ? 0
									: overTime - deductMins;

							userInfo.put("fdBeforeOverTime", overTime);
							overTime = AttendUtil.getOverTime(cate, (int) (__fdBeforeOverTime + overTime));
							userInfo.put("fdOverTime", overTime);

							// 保存没有扣除加班的时长
							userInfo.put("fdOverTimeWithoutDeduct",
									_fdOverTimeWithoutDeduct
											+ (int) tempFdOverTime);
						}
					}
				}
			}
		}
	}

	private int getDeductMinsForPeriods(Timestamp docCreateTime,
			SysAttendCategory cate,
			Boolean fdIsAcross, Date fdEndTime, Boolean isOverTimeRule) {
		List<JSONObject> periods = new ArrayList<>();
		if (fdIsAcross && !isOverTimeRule) {
			// 夸天将时间拆成两段进行比较
			// 加班时间为18:00~次日4:00 则将加班时间拆为18:00~23:59:59和00:00~4:00
			JSONObject period1 = new JSONObject();
			period1.put("fdStartTime", fdEndTime.getTime());
			period1.put("fdEndTime",
					AttendUtil.getEndDate(fdEndTime, 0).getTime());
			JSONObject period2 = new JSONObject();
			period2.put("fdStartTime",
					AttendUtil.getDate(docCreateTime, 0).getTime());
			period2.put("fdEndTime", docCreateTime.getTime());
			periods.add(period1);
			periods.add(period2);
		} else {
			JSONObject period = new JSONObject();
			period.put("fdStartTime", fdEndTime.getTime());
			period.put("fdEndTime", docCreateTime.getTime());
			periods.add(period);
		}
		int deductMins = AttendOverTimeUtil.getDeductMins(cate, periods);
		return deductMins;
	}



	private Date ignoreSecond(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}



	private void statOffCountDetail(JSONObject userInfo, int fdStatusValue,
			Number fdOffType) {
		if (!userInfo.containsKey("fdOffCountDetail")) {
			JSONObject detail = new JSONObject();
			if (fdStatusValue == 5) {
				String offKey = fdOffType != null ? String.valueOf(fdOffType)
						: "NaN";
				detail.put(offKey, 1);
			}
			userInfo.put("fdOffCountDetail", detail);
		} else {
			JSONObject detail = (JSONObject) userInfo.get("fdOffCountDetail");
			if (fdStatusValue == 5) {
				String offKey = fdOffType != null ? String.valueOf(fdOffType)
						: "NaN";
				if (detail.containsKey(offKey)) {
					detail.put(offKey, detail.getInteger(offKey) + 1);
				} else {
					detail.put(offKey, 1);
				}
			}
			userInfo.put("fdOffCountDetail", detail);
		}
	}

	/**
	 * 封装考勤记录
	 * @param userInfo
	 * @param fdWorkId
	 * @param fdWorkType
	 * @param fdStatus
	 * @param docCreateTime
	 * @param fdOutside
	 * @param fdState
	 * @param fdLocation
	 * @param fdWifiName
	 * @param docCreator
	 * @param fdIsAcross
	 * @param cate
	 * @param fdAppName
	 * @throws Exception
	 */
	private void statRecordDetail(JSONObject userInfo, String fdWorkId,
			Number fdWorkType, Number fdStatus, Timestamp docCreateTime,
			Boolean fdOutside, Number fdState, String fdLocation,
			String fdWifiName, SysOrgElement docCreator,
			Boolean fdIsAcross, SysAttendCategory cate, String fdAppName)
			throws Exception {
		if (!userInfo.containsKey("record")) {
			userInfo.put("record", new JSONArray());
		}
		Date workDate = new Date(docCreateTime.getTime());
		if (fdIsAcross) {
			workDate = AttendUtil.getDate(workDate, -1);
		}
		SysAttendCategoryWorktime workTime = this.getWorkTime(cate,
				fdWorkId, fdWorkType.intValue(), docCreator, docCreateTime,
				workDate);
		Integer fdOverTimeType = 1;
		if (workTime != null) {
			fdOverTimeType = workTime.getFdOverTimeType();
		}
		JSONArray works = (JSONArray) userInfo.get("record");
		JSONObject work = new JSONObject();
		work.put("fdStatus", fdStatus.intValue());
		work.put("docCreateTime", docCreateTime.getTime());
		work.put("fdWorkType", fdWorkType.intValue());
		work.put("fdOutside", fdOutside ? 1 : 0);
		work.put("fdWorkId", fdWorkId);
		work.put("fdState", fdState);
		work.put("fdLocation",
				StringUtil.isNotNull(fdLocation) ? fdLocation : "");
		work.put("fdOverTimeType", fdOverTimeType == null ? 1 : fdOverTimeType);
		work.put("fdWifiName",
				StringUtil.isNotNull(fdWifiName) ? fdWifiName : "");
		work.put("fdAppName", StringUtil.isNotNull(fdAppName) ? fdAppName : "");
		works.add(work);
	}

	// 统计总工时
	private void statTotalTime(JSONObject userInfo, String fdWorkId,
			Number fdWorkType, Number fdStatus, Timestamp docCreateTime,
			Number fdState, SysAttendCategory cate, String fdLocation,
			String fdWifiName, String fdAppName) {
		JSONArray works = (JSONArray) userInfo.get("record");
		if (!userInfo.containsKey("fdTotalTime")) {
			userInfo.put("fdTotalTime", 0);
		}
		for (int j = 0; j < works.size(); j++) {
			JSONObject r = (JSONObject) works.get(j);
			// 同班次计算工时
			if (fdWorkId.equals(r.getString("fdWorkId"))
					&& fdWorkType.intValue() != r
							.getInteger("fdWorkType")) {
				int _fdStatus1 = fdStatus.intValue();
				int _fdStatus2 = r.getInteger("fdStatus");

				Number fdState2 = (Number) r.get("fdState");

				Timestamp docCreateTime2 = new Timestamp(
						r.getLong("docCreateTime"));
				String fdLocation2 = r.containsKey("fdLocation")
						? r.getString("fdLocation") : "";
				String fdWifiName2 = r.containsKey("fdWifiName")
						? r.getString("fdWifiName") : "";
				String fdAppName2 = r.containsKey("fdAppName")
						? r.getString("fdAppName") : "";

				// 异常审批通过为true
				boolean isExcOk1 = fdState != null && fdState.intValue() == 2;
				boolean isExcOk2 = fdState2 != null && fdState2.intValue() == 2;
				if (!isExcOk1 && _fdStatus1 == 0
						|| !isExcOk2 && _fdStatus2 == 0) {
					// 缺卡不计算工时
					continue;
				}
				if (_fdStatus1 == 5 && _fdStatus2 == 5) {
					// 整天请假不计算工时
					continue;
				}
//				if (_fdStatus1 == 6 && _fdStatus2 == 6) {
//					// 整天外出不计算工时
//					continue;
//				}
				boolean ret1 = isExcOk1 || _fdStatus1 == 1 || _fdStatus1 == 2
						|| _fdStatus1 == 3 || _fdStatus1 == 4 || _fdStatus1 == 5
						|| _fdStatus1 == 6;
				boolean ret2 = isExcOk2 || _fdStatus2 == 1 || _fdStatus2 == 2
						|| _fdStatus2 == 3 || _fdStatus2 == 4 || _fdStatus2 == 5
						|| _fdStatus2 == 6;
				if (ret1 && ret2) {
					// 可计算工时
					long totalTime = 0;
					boolean isAttended = StringUtil.isNull(fdLocation)
							&& StringUtil.isNull(fdWifiName)
							&& StringUtil.isNull(fdAppName)
							&& (_fdStatus1 != 1 && !isExcOk1)
							|| (StringUtil.isNull(fdLocation2)
									&& StringUtil.isNull(fdWifiName2)
									&& StringUtil.isNull(fdAppName2)
									&& (_fdStatus2 != 1 && !isExcOk2));

					if (_fdStatus1 == 5 || _fdStatus2 == 5) {
						if (isAttended) {
							// 用户没有打卡,则不计算工时
							continue;
						}
					}
					// 忽略秒
					docCreateTime =new Timestamp(AttendUtil.removeSecond(docCreateTime).getTime());
					docCreateTime2 =new Timestamp(AttendUtil.removeSecond(docCreateTime2).getTime());
					if(docCreateTime.getTime()>docCreateTime2.getTime()){
					userInfo.put("docCreateTime11",
							docCreateTime);
					userInfo.put("docCreateTime21",
							docCreateTime2);
					}else
					{
						userInfo.put("docCreateTime21",
								docCreateTime);
						userInfo.put("docCreateTime11",
								docCreateTime2);	
					}
					totalTime = Math.abs(docCreateTime.getTime() - docCreateTime2.getTime());
					totalTime = (int)Math.ceil( totalTime / 60000 );
					totalTime = totalTime >= 0 ? totalTime : 0;
					int __fdTotalTime = userInfo
							.getInteger("fdTotalTime");
					userInfo.put("fdTotalTime",
							__fdTotalTime + (int) totalTime);

					//重新计算综合工时下班时间

				}
				break;
			}
		}
	}

	private void addBatch(Map<String, JSONObject> statMap,Map<String, JSONObject> monthDataMap,  Date beginTime,
			Date endTime, Map<String, String> areaMap)
			throws Exception {
		synchronized (lock) {
			ResultSet rs = null;
			try {
				StackTraceElement[] stackElements = new Throwable().getStackTrace();
		        if(stackElements != null)
		        {
		            for(int i = 0; i < stackElements.length; i++)
		            {
		                logger.info(""+ stackElements[i]); 
		            }
		        }
				// 判断是否已统计。放在同一个事务里，防止同一时刻执行统计导致有重复数据
				PreparedStatement statement = this.getSysAttendStatSelectPreparedStatement();
				statement.setTimestamp(1, new Timestamp(beginTime.getTime()));
				statement.setTimestamp(2, new Timestamp(endTime.getTime()));
				rs = statement.executeQuery();
				Map<String, String> updatedOrgs = new HashMap<String, String>();
				Map<String, List<String>> deleteKeys = new HashMap<String, List<String>>();
				while (rs.next()) {
					String key = rs.getString(1);
					if(updatedOrgs.containsKey(key)){
						List<String> deleteIds = deleteKeys.get(key);
						if(CollectionUtils.isEmpty(deleteIds)){
							deleteIds=new ArrayList<>();
						}
						deleteIds.add(rs.getString(2));
						continue;
					}
					updatedOrgs.put(key, rs.getString(2));
				}
				PreparedStatement update = this.getSysAttendStatUpdatePreparedStatement();
				PreparedStatement delete = this.getSysAttendStatDeletePreparedStatement();
				PreparedStatement insert = this.getSysAttendStatInsertPreparedStatement();
				boolean isInsert = false, isUpdate = false,isDelete = false;
				this.getConnection().setAutoCommit(false);
				for (String key : statMap.keySet()) {
					JSONObject json = statMap.get(key);

					JSONObject json1 = monthDataMap.get(key);
					if (json ==null) {
						logger.warn("该用户统计异常:" + key);
						continue;
					}
					String fdCategoryId = (String) json.get("fdCategoryId");
					SysOrgElement sysOrgElement=(SysOrgElement) getSysOrgElementService().findByPrimaryKey(key);
					List signList = this.sysAttendCategoryService.getAttendSignTimes(sysOrgElement, beginTime);
					String sysAttendCategory = this.sysAttendCategoryService.getAttendCategory(sysOrgElement, beginTime);
					String categoryId = sysAttendCategoryService.getAttendCategory(sysOrgElement, beginTime);
					String sql2 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(DateUtil.convertDateTimeToString(beginTime,"YYYY-MM-DD HH:mm:ss", null)).split(" ")[0]+"'"
							+ "and doc_creator_id='"+key+"' and doc_Status=0";
					List list11 = HrCurrencyParams.getListBySql(sql2);
					if((list11==null||list11.size()==0)&&(signList==null||signList.size()==0))
						continue;
					SysAttendCategory sysAttendCategory1 = CategoryUtil.getCategoryById(fdCategoryId);
					if(list11==null&&categoryId == null && !Integer.valueOf(1).equals(sysAttendCategory1.getFdShiftType()))
						continue;
//					if(!"1880a73ba69beffb702402546eea3e0a".equals(fdCategoryId)&&!"1880a76607111d6bdf50cc24ba89ba7a".equals(fdCategoryId)){
					
//					Boolean isAttendNeeded = this.sysAttendCategoryService
//							.isAttendNeeded(category, beginTime);
//					if(!isAttendNeeded)continue;
					if (StringUtil.isNull(fdCategoryId)) {
						logger.warn("该用户没有考勤组,忽略处理!userId:" + key);
						continue;
					}
//					}
					List<SysAttendCategory> list=new ArrayList<SysAttendCategory>();
					list.add(sysAttendCategory1);
					com.alibaba.fastjson.JSONArray datas = getSysAttendCategoryService().filterAttendCategory(list,
							beginTime, true, sysOrgElement);
					if((list11==null||list11.isEmpty())&&(datas==null || datas.isEmpty()))
						continue;
					String fdCategoryName = json.getString("fdCategoryName");
					
					int restMinsDefault = json.getInteger("restMinsDefault");
					int fdRestTime = json.getInteger("fdRestTime");
					int businessTime = 0;
//					List<SysTimeLeaveDetail> sysTimeLeaveDetailList = (List<SysTimeLeaveDetail>) getSysTimeLeaveDetailService().findLeaveDetail(key,beginTime,endTime,"2");
//					List<SysTimeLeaveDetail> sysTimeLeaveDetailList1 = (List<SysTimeLeaveDetail>) getSysTimeLeaveDetailService().findLeaveDetail(key,beginTime,endTime,"3");
//					List<SysTimeLeaveDetail> sysTimeLeaveDetailList1 = (List<SysTimeLeaveDetail>) getSysTimeLeaveDetailService().findLeaveDetail(key,beginTime,endTime,"13");
					HQLInfo hqlInfo = new HQLInfo();
					String whereBlock=" fdPerson=:person and fdStartTime>=:startTime and fdEndTime<=:endTime";
					hqlInfo.setWhereBlock(whereBlock);
					hqlInfo.setParameter("startTime", beginTime);
					hqlInfo.setParameter("endTime", endTime);
					hqlInfo.setParameter("person", sysOrgElement);
					List<SysTimeLeaveDetail> sysTimeLeaveDetailList = (List<SysTimeLeaveDetail>) getSysTimeLeaveDetailService().findList(hqlInfo);
//					for(SysTimeLeaveDetail sysTimeLeaveDetail : sysTimeLeaveDetailList1){
//						sysTimeLeaveDetailList.add(sysTimeLeaveDetail);
//					}
					try{
					for(SysTimeLeaveDetail sysTimeLeaveDetail : sysTimeLeaveDetailList){
						Date docCreateTime11 = (Date) json.get("docCreateTime11");
						Date docCreateTime21 = (Date) json.get("docCreateTime21");
						Date restEnd = (Date) json.get("restEnd11");
						Date restStart = (Date) json.get("restStart11");
						if(sysTimeLeaveDetail.getFdEndTime().getTime()>docCreateTime11.getTime())
							sysTimeLeaveDetail.setFdEndTime(docCreateTime11);
						if(sysTimeLeaveDetail.getFdStartTime().getTime()<docCreateTime21.getTime())
							sysTimeLeaveDetail.setFdStartTime(docCreateTime21);
						
//								if(sysTimeLeaveDetail.getFdEndTime().getTime()<restStart.getTime())
//									businessTime+=(int)(sysTimeLeaveDetail.getFdEndTime().getTime()-docCreateTime21.getTime())/60000;
//								else if(sysTimeLeaveDetail.getFdEndTime().getTime()<restEnd.getTime())
//									businessTime+=(int)(restStart.getTime()-docCreateTime21.getTime())/60000;
//								else if
//									businessTime+=(int)(restStart.getTime()-docCreateTime21.getTime())/60000+(int)(sysTimeLeaveDetail.getFdEndTime().getTime()-restEnd.getTime())/60000;
//							else
//								businessTime+=sysTimeLeaveDetail.getFdTotalTime().intValue();
//							
//						}else{
								if(sysTimeLeaveDetail.getFdStartTime().getTime()<restStart.getTime()){
								if(sysTimeLeaveDetail.getFdEndTime().getTime()>restEnd.getTime())
									businessTime+=(int)(sysTimeLeaveDetail.getFdEndTime().getTime()-sysTimeLeaveDetail.getFdStartTime().getTime()+restStart.getTime()-restEnd.getTime())/60000;
								else if(sysTimeLeaveDetail.getFdEndTime().getTime()>restStart.getTime())
									businessTime+=(int)(restStart.getTime()-sysTimeLeaveDetail.getFdStartTime().getTime())/60000;
								else
									businessTime+=sysTimeLeaveDetail.getFdTotalTime().intValue();
								}else
									if
										(sysTimeLeaveDetail.getFdStartTime().getTime()<restEnd.getTime())
										businessTime+=(int)(sysTimeLeaveDetail.getFdEndTime().getTime()-restEnd.getTime())/60000;
									
									else
										businessTime+=sysTimeLeaveDetail.getFdTotalTime().intValue();
								
						}
//					}
					}catch(Exception e){
						e.printStackTrace();
					}
					if(json.containsKey("businessTime"))
					businessTime=json.getInteger("businessTime")>0?json.getInteger("businessTime"):0;
						int fdTotalTime = json.getInteger("fdTotalTime");
					// 总工时减去午休时间
					fdTotalTime = fdRestTime >= fdTotalTime ? fdTotalTime
							: (fdTotalTime - fdRestTime-businessTime);
					int fdLateTime = json.getInteger("fdLateTime");
					int fdLeftTime = json.getInteger("fdLeftTime");
					boolean fdStatus = json.getInteger("fdStatus") == 1 ? true
							: false;
					boolean fdOutside = json.getInteger("fdOutside") == 1 ? true
							: false;
					boolean fdLate = json.getInteger("fdLate") == 1 ? true : false;
					boolean fdLeft = json.getInteger("fdLeft") == 1 ? true : false;
					boolean fdMissed = json.getInteger("fdMissed") == 1 ? true
							: false;
					boolean fdAbsent = json.getInteger("fdAbsent") == 1 ? true
							: false;
					boolean fdTrip = json.getInteger("fdTrip") == 1 ? true : false;
					boolean fdOff = json.getInteger("fdOff") == 1 ? true : false;
					int fdMissedCount = json.getInteger("fdMissedCount");
					String authAreaId = "abcd";
					if (json.containsKey("authAreaId") && !StringUtil
							.isNull(json.getString("authAreaId"))) {
						authAreaId = json.getString("authAreaId");
					}
					int fdOutsideCount = json.getInteger("fdOutsideCount");
					int fdLateCount = json.getInteger("fdLateCount");
					int fdLeftCount = json.getInteger("fdLeftCount");
					float fdTripDays = 0;
					float fdOffDays = 0;
					float fdOffTime = 0;
					if (json.containsKey("fdTripDays") && !StringUtil
							.isNull(json.getString("fdTripDays"))) {
						fdTripDays = json.getFloatValue("fdTripDays");
					}
					if (json.containsKey("fdOffDays") && !StringUtil
							.isNull(json.getString("fdOffDays"))) {
						fdOffDays = json.getFloatValue("fdOffDays");
					}
					if (json.containsKey("fdOffTime") && !StringUtil
							.isNull(json.getString("fdOffTime"))) {
						fdOffTime = json.getFloatValue("fdOffTime");
					}
					int fdOverTime = 0;
					if(json.containsKey("fdOverTime")) {
						fdOverTime = json.getInteger("fdOverTime");
					}
					int fdOverTurnTime = 0;
					if(json.containsKey("fdOverTurnTime")) {
						fdOverTurnTime = json.getInteger("fdOverTurnTime");
					}
					int fdOverPayTime = 0;
					if(json.containsKey("fdOverPayTime")) {
						fdOverPayTime = json.getInteger("fdOverPayTime");
					}
					int fdOverApplyTime = 0;
					if(json.containsKey("fdOverApplyTime")) {
						fdOverApplyTime = json.getInteger("fdOverApplyTime");
					}
					int fdOverTurnApplyTime = 0;
					if(json.containsKey("fdOverTurnApplyTime")) {
						fdOverTurnApplyTime = json.getInteger("fdOverTurnApplyTime");
					}
					int fdOverPayApplyTime = 0;
					if(json.containsKey("fdOverPayApplyTime")) {
						fdOverPayApplyTime = json.getInteger("fdOverPayApplyTime");
					}
					int fdAttendResult = 0;
					if(json.containsKey("fdAttendResult")) {
						fdAttendResult = json.getInteger("fdAttendResult");
					}
					int fdMonthLateNum = 0;
					if(json.containsKey("fdMonthLateNum")) {
						fdMonthLateNum = json.getInteger("fdMonthLateNum");
					}else{
						if(json1!=null)
							fdMonthLateNum = json1.getInteger("fdMonthLateNum");
						else
							logger.info(key);
					}
					int fdMonthForgerNum = 0;
					if(json.containsKey("fdMonthForgerNum")) {
						fdMonthForgerNum = json.getInteger("fdMonthForgerNum");
					}
					int fdMonthLateMinNum = 0;
					if(json.containsKey("fdMonthLateMinNum")) {
						fdMonthLateMinNum = json.getInteger("fdMonthLateMinNum");
					}else{
						if(json1!=null)
							fdMonthLateMinNum = json1.getInteger("fdMonthLateMinNum");
						else
							logger.info(key);
					}
					Float standWorkTime =0F;
					if (json.containsKey("fdStandWorkTime")) {
						standWorkTime =json.getFloat("fdStandWorkTime") == null ? 8f :  json.getFloat("fdStandWorkTime");
					}
					
					int fdOverTimeWithoutDeduct =0;
					if (json.containsKey("fdOverTimeWithoutDeduct")) {
						fdOverTimeWithoutDeduct =json.getInteger("fdOverTimeWithoutDeduct");
					}
					int fdDateType = json.getInteger("fdDateType");
					int fdMissedExcCount = json.getInteger("fdMissedExcCount");
					int fdLateExcCount = json.getInteger("fdLateExcCount");
					int fdLeftExcCount = json.getInteger("fdLeftExcCount");
					JSONObject offDaysDetail = json
							.getJSONObject("fdOffCountDetail");
					String fdOffCountDetail = offDaysDetail.isEmpty() ? null
							: offDaysDetail.toString();
					String docCreatorHId = json
							.containsKey("docCreatorHId")
									? json.getString("docCreatorHId") : null;
					float fdAbsentDays = 0;
					float fdPersonalLeaveDays = 0;
					float fdOutgoingTime = 0;
					if (json.containsKey("fdPersonalLeaveDays") && !StringUtil
							.isNull(json.getString("fdPersonalLeaveDays"))) {
						fdPersonalLeaveDays = json.getFloatValue("fdPersonalLeaveDays");
					}
					if (json.containsKey("fdAbsentDays") && !StringUtil
							.isNull(json.getString("fdAbsentDays"))) {
						fdAbsentDays = json.getFloatValue("fdAbsentDays");
					}
					if (json.containsKey("fdOutgoingTime") && !StringUtil
							.isNull(json.getString("fdOutgoingTime"))) {
						fdOutgoingTime = json.getFloatValue("fdOutgoingTime");
					}
					fdOutgoingTime = fdOutgoingTime > 24f ? 24f
							: fdOutgoingTime;
					boolean fdIsNoRecord = json.containsKey("fdIsNoRecord")
							? json.getBoolean("fdIsNoRecord") : false;

					float fdWorkTime = 0;
					if (json.containsKey("fdStandWorkTime") && !StringUtil
							.isNull(json.getString("fdStandWorkTime"))) {
						fdWorkTime = json.getFloatValue("fdStandWorkTime");
					}
					List<String> deleteIds=deleteKeys.get(key);
					if(CollectionUtils.isNotEmpty(deleteIds)){
						for (String delId:deleteIds) {
							delete.setString(1,delId);
							delete.addBatch();
							isDelete = true;
						}
					}
					// 判断是否已统计
					if (updatedOrgs.containsKey(key)) {
						update.setInt(1, fdTotalTime);
						update.setInt(2, fdLateTime);
						update.setInt(3, fdLeftTime);
						update.setBoolean(4, fdStatus);
						update.setBoolean(5, fdOutside);
						update.setBoolean(6, fdLate);
						update.setBoolean(7, fdLeft);
						update.setBoolean(8, fdMissed);
						update.setBoolean(9, fdAbsent);
						update.setInt(10, fdMissedCount);
						update.setInt(11, fdOutsideCount);
						update.setInt(12, fdLateCount);
						update.setInt(13, fdLeftCount);
						update.setBoolean(14, fdTrip);
						update.setBoolean(15, fdOff);
						update.setFloat(16, fdTripDays);
						update.setFloat(17, fdOffDays);
						update.setInt(18, fdOverTime);
						update.setInt(19, fdDateType);
						update.setInt(20, fdMissedExcCount);
						update.setInt(21, fdLateExcCount);
						update.setInt(22, fdLeftExcCount);
						update.setString(23, fdOffCountDetail);
						update.setString(24, docCreatorHId);
						update.setFloat(25, fdAbsentDays);
						update.setFloat(26, fdOutgoingTime);
						update.setInt(27, (int) fdOffTime);
						update.setBoolean(28, fdIsNoRecord);
						update.setString(29, fdCategoryId);
						update.setString(30, fdCategoryName);

						update.setFloat(31, fdOffTime);
						update.setFloat(32, fdPersonalLeaveDays);
						update.setFloat(33, fdWorkTime);
						//通过创建人id查找对应人事数据
						HrStaffPersonInfo person=getHrStaffPersonInfoService().findByOrgPersonId(key);
						update.setString(34, person==null||person.getFdFirstLevelDepartment()==null?"":person.getFdFirstLevelDepartment().getFdName());
						update.setString(35, person==null||person.getFdSecondLevelDepartment()==null?"":person.getFdSecondLevelDepartment().getFdName());
						update.setString(36, person==null||person.getFdThirdLevelDepartment()==null?"":person.getFdThirdLevelDepartment().getFdName());
						//午休时间
						update.setInt(37, restMinsDefault);
						//考勤标准工作时长
						update.setFloat(38, standWorkTime);
						//迟到次数
						update.setInt(39, fdMonthLateNum);
						//月忘打卡次数
						update.setInt(40, fdMonthForgerNum);
						//月迟到分钟数
						update.setInt(41, fdMonthLateMinNum);
						//延时加班时长
						update.setInt(42, fdOverTimeWithoutDeduct);
						//月异常考勤次数
						update.setInt(43, fdAttendResult);
						

						update.setInt(44, fdOverTurnTime);
						update.setInt(45, fdOverPayTime);
						update.setInt(46, fdOverApplyTime);
						update.setInt(47, fdOverTurnApplyTime);
						update.setInt(48, fdOverPayApplyTime);

						update.setString(49, updatedOrgs.get(key));
						update.addBatch();
						isUpdate = true;

					} else {
						String fdId = IDGenerator.generateID();
						insert.setString(1, fdId);
						insert.setTimestamp(2,
								new Timestamp(beginTime.getTime()));
						insert.setInt(3, fdTotalTime);
						insert.setTimestamp(4,
								new Timestamp(new Date().getTime()));
						insert.setInt(5, fdLateTime);
						insert.setInt(6, fdLeftTime);
						insert.setBoolean(7, fdStatus);
						insert.setBoolean(8, fdOutside);
						insert.setString(9, fdCategoryId);
						insert.setString(10, fdCategoryName);
						insert.setString(11, key);
						insert.setBoolean(12, fdLate);
						insert.setBoolean(13, fdLeft);
						insert.setBoolean(14, fdMissed);
						insert.setBoolean(15, fdAbsent);
						insert.setInt(16, fdMissedCount);
						insert.setInt(17, fdOutsideCount);
						insert.setInt(18, fdLateCount);
						insert.setInt(19, fdLeftCount);
						insert.setBoolean(20, fdTrip);
						insert.setBoolean(21, fdOff);
						insert.setFloat(22, fdTripDays);
						insert.setFloat(23, fdOffDays);
						insert.setInt(24, fdOverTime);
						insert.setInt(25, fdDateType);
						insert.setInt(26, fdMissedExcCount);
						insert.setInt(27, fdLateExcCount);
						insert.setInt(28, fdLeftExcCount);
						insert.setString(29, fdOffCountDetail);
						insert.setString(30, docCreatorHId);
						insert.setFloat(31, fdAbsentDays);
						insert.setFloat(32, fdOutgoingTime);
						insert.setInt(33, (int) fdOffTime);
						insert.setBoolean(34, fdIsNoRecord);
						insert.setFloat(35, fdOffTime);
						insert.setString(36, authAreaId);
//						insert.setString(36, areaMap.get(key));
						insert.setFloat(37, fdWorkTime);
						insert.setFloat(38, fdPersonalLeaveDays);
						//通过创建人id查找对应人事数据
						HrStaffPersonInfo person=getHrStaffPersonInfoService().findByOrgPersonId(key);
						insert.setString(39, person==null||person.getFdFirstLevelDepartment()==null?"":person.getFdFirstLevelDepartment().getFdName());
						insert.setString(40, person==null||person.getFdSecondLevelDepartment()==null?"":person.getFdSecondLevelDepartment().getFdName());
						insert.setString(41, person==null||person.getFdThirdLevelDepartment()==null?"":person.getFdThirdLevelDepartment().getFdName());
						
						//午休时间
						insert.setInt(42, restMinsDefault);
						//考勤标准工作时长
						insert.setFloat(43, standWorkTime);
						//迟到次数
						insert.setInt(44, fdMonthLateNum);
						//月忘打卡次数
						insert.setInt(45, fdMonthForgerNum);
						//月迟到分钟数
						insert.setInt(46, fdMonthLateMinNum);
						//延时加班时长
						insert.setInt(47, fdOverTimeWithoutDeduct);
						//月异常考勤次数
						insert.setInt(48, fdAttendResult);

						insert.setInt(49,fdOverTurnTime);
						insert.setInt(50,fdOverPayTime);
						insert.setInt(51,fdOverApplyTime);
						insert.setInt(52,fdOverTurnApplyTime);
						insert.setInt(53,fdOverPayApplyTime);
						insert.addBatch();
						isInsert = true;
					}
				}
				if (isUpdate || isInsert || isDelete) {
					if(isDelete){
						delete.executeBatch();
					}
					if (isUpdate) {
						update.executeBatch();
					}
					if (isInsert) {
						insert.executeBatch();
					}
					this.getConnection().commit();
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex.getMessage(), ex);
				this.getConnection().rollback();
				throw ex;
			} finally {
				JdbcUtils.closeResultSet(rs);
			}
		}
	}
	private void addBatch(Map<String, JSONObject> statMap, Date beginTime,
			Date endTime, Map<String, String> areaMap)
			throws Exception {
		synchronized (lock) {
			ResultSet rs = null;
			try {
				StackTraceElement[] stackElements = new Throwable().getStackTrace();
		        if(stackElements != null)
		        {
		            for(int i = 0; i < stackElements.length; i++)
		            {
		                logger.info(""+ stackElements[i]); 
		            }
		        }
				// 判断是否已统计。放在同一个事务里，防止同一时刻执行统计导致有重复数据
				PreparedStatement statement = this.getSysAttendStatSelectPreparedStatement();
				statement.setTimestamp(1, new Timestamp(beginTime.getTime()));
				statement.setTimestamp(2, new Timestamp(endTime.getTime()));
				rs = statement.executeQuery();
				Map<String, String> updatedOrgs = new HashMap<String, String>();
				Map<String, List<String>> deleteKeys = new HashMap<String, List<String>>();
				while (rs.next()) {
					String key = rs.getString(1);
					if(updatedOrgs.containsKey(key)){
						List<String> deleteIds = deleteKeys.get(key);
						if(CollectionUtils.isEmpty(deleteIds)){
							deleteIds=new ArrayList<>();
						}
						deleteIds.add(rs.getString(2));
						continue;
					}
					updatedOrgs.put(key, rs.getString(2));
				}
				PreparedStatement update = this.getSysAttendStatUpdatePreparedStatement();
				PreparedStatement delete = this.getSysAttendStatDeletePreparedStatement();
				PreparedStatement insert = this.getSysAttendStatInsertPreparedStatement();
				boolean isInsert = false, isUpdate = false,isDelete = false;
				this.getConnection().setAutoCommit(false);
				for (String key : statMap.keySet()) {
					JSONObject json = statMap.get(key);
					if (json ==null) {
						logger.warn("该用户统计异常:" + key);
						continue;
					}
					String fdCategoryId = (String) json.get("fdCategoryId");
					SysOrgElement sysOrgElement=(SysOrgElement) getSysOrgElementService().findByPrimaryKey(key);
					List signList = this.sysAttendCategoryService.getAttendSignTimes(sysOrgElement, beginTime);
					String categoryId = sysAttendCategoryService.getAttendCategory(sysOrgElement, beginTime);
					String sql2 = "select fd_base_work_time from sys_attend_main where substring(fd_base_work_time,1,10) ='"+(DateUtil.convertDateTimeToString(beginTime,"YYYY-MM-DD HH:mm:ss", null)).split(" ")[0]+"'"
							+ "and doc_creator_id='"+key+"' and doc_Status=0";
					List list11 = HrCurrencyParams.getListBySql(sql2);
					if((list11==null||list11.size()==0)&&(signList==null||signList.size()==0))
						continue;
//					if(categoryId == null)
//						continue;
//					if(!"1880a73ba69beffb702402546eea3e0a".equals(fdCategoryId)&&!"1880a76607111d6bdf50cc24ba89ba7a".equals(fdCategoryId)){
//					SysAttendCategory category = CategoryUtil.getCategoryById(fdCategoryId);
//					Boolean isAttendNeeded = this.sysAttendCategoryService
//							.isAttendNeeded(category, beginTime);
//					if(!isAttendNeeded)continue;
					if (StringUtil.isNull(fdCategoryId)) {
						logger.warn("该用户没有考勤组,忽略处理!userId:" + key);
						continue;
					}
//					}
					String fdCategoryName = json.getString("fdCategoryName");
					
					int restMinsDefault = json.getInteger("restMinsDefault");
					int fdRestTime = json.getInteger("fdRestTime");
					int businessTime = 0;
					List<SysTimeLeaveDetail> sysTimeLeaveDetailList = (List<SysTimeLeaveDetail>) getSysTimeLeaveDetailService().findLeaveDetail(key,beginTime,endTime,"2");
					try{
					for(SysTimeLeaveDetail sysTimeLeaveDetail : sysTimeLeaveDetailList){
						Date docCreateTime11 = (Date) json.get("docCreateTime11");
						Date docCreateTime21 = (Date) json.get("docCreateTime21");
						Date restEnd = (Date) json.get("restEnd11");
						Date restStart = (Date) json.get("restStart11");
						if(sysTimeLeaveDetail.getFdStartTime().getTime()<docCreateTime21.getTime()){
							if(sysTimeLeaveDetail.getFdEndTime().getTime()<docCreateTime11.getTime()){
								if(sysTimeLeaveDetail.getFdEndTime().getTime()<restStart.getTime())
									businessTime=(int)(sysTimeLeaveDetail.getFdEndTime().getTime()-docCreateTime21.getTime())/60000;
								else if(sysTimeLeaveDetail.getFdEndTime().getTime()<restEnd.getTime())
									businessTime=(int)(restStart.getTime()-docCreateTime21.getTime())/60000;
								else
									businessTime+=(int)(restStart.getTime()-docCreateTime21.getTime())/60000+(int)(sysTimeLeaveDetail.getFdEndTime().getTime()-restEnd.getTime())/60000;
							}else{
								businessTime=sysTimeLeaveDetail.getFdTotalTime().intValue();
							}
						}else{
							if(sysTimeLeaveDetail.getFdEndTime().getTime()<docCreateTime11.getTime())
								if(sysTimeLeaveDetail.getFdEndTime().getTime()>restEnd.getTime())
									businessTime=(int)(sysTimeLeaveDetail.getFdEndTime().getTime()-sysTimeLeaveDetail.getFdStartTime().getTime()+restStart.getTime()-restEnd.getTime())/60000;
								else if(sysTimeLeaveDetail.getFdEndTime().getTime()>restStart.getTime())
									businessTime=(int)(restStart.getTime()-sysTimeLeaveDetail.getFdStartTime().getTime())/60000;
								else
									businessTime=sysTimeLeaveDetail.getFdTotalTime().intValue();
						}
					}
					}catch(Exception e){
						e.printStackTrace();
					}
					if(json.containsKey("businessTime"))
					businessTime=json.getInteger("businessTime");
						int fdTotalTime = json.getInteger("fdTotalTime");

					// 总工时减去午休时间
					fdTotalTime = fdRestTime >= fdTotalTime ? fdTotalTime
							: (fdTotalTime - fdRestTime-businessTime);
					int fdLateTime = json.getInteger("fdLateTime");
					int fdLeftTime = json.getInteger("fdLeftTime");
					boolean fdStatus = json.getInteger("fdStatus") == 1 ? true
							: false;
					boolean fdOutside = json.getInteger("fdOutside") == 1 ? true
							: false;
					boolean fdLate = json.getInteger("fdLate") == 1 ? true : false;
					boolean fdLeft = json.getInteger("fdLeft") == 1 ? true : false;
					boolean fdMissed = json.getInteger("fdMissed") == 1 ? true
							: false;
					boolean fdAbsent = json.getInteger("fdAbsent") == 1 ? true
							: false;
					boolean fdTrip = json.getInteger("fdTrip") == 1 ? true : false;
					boolean fdOff = json.getInteger("fdOff") == 1 ? true : false;
					int fdMissedCount = json.getInteger("fdMissedCount");
					String authAreaId = "abcd";
					if (json.containsKey("authAreaId") && !StringUtil
							.isNull(json.getString("authAreaId"))) {
						authAreaId = json.getString("authAreaId");
					}
					int fdOutsideCount = json.getInteger("fdOutsideCount");
					int fdLateCount = json.getInteger("fdLateCount");
					int fdLeftCount = json.getInteger("fdLeftCount");
					float fdTripDays = 0;
					float fdOffDays = 0;
					float fdOffTime = 0;
					if (json.containsKey("fdTripDays") && !StringUtil
							.isNull(json.getString("fdTripDays"))) {
						fdTripDays = json.getFloatValue("fdTripDays");
					}
					if (json.containsKey("fdOffDays") && !StringUtil
							.isNull(json.getString("fdOffDays"))) {
						fdOffDays = json.getFloatValue("fdOffDays");
					}
					if (json.containsKey("fdOffTime") && !StringUtil
							.isNull(json.getString("fdOffTime"))) {
						fdOffTime = json.getFloatValue("fdOffTime");
					}
					int fdOverTime = 0;
					if(json.containsKey("fdOverTime")) {
						fdOverTime = json.getInteger("fdOverTime");
					}
					int fdOverTurnTime = 0;
					if(json.containsKey("fdOverTurnTime")) {
						fdOverTurnTime = json.getInteger("fdOverTurnTime");
					}
					int fdOverPayTime = 0;
					if(json.containsKey("fdOverPayTime")) {
						fdOverPayTime = json.getInteger("fdOverPayTime");
					}
					int fdOverApplyTime = 0;
					if(json.containsKey("fdOverApplyTime")) {
						fdOverApplyTime = json.getInteger("fdOverApplyTime");
					}
					int fdOverTurnApplyTime = 0;
					if(json.containsKey("fdOverTurnApplyTime")) {
						fdOverTurnApplyTime = json.getInteger("fdOverTurnApplyTime");
					}
					int fdOverPayApplyTime = 0;
					if(json.containsKey("fdOverPayApplyTime")) {
						fdOverPayApplyTime = json.getInteger("fdOverPayApplyTime");
					}
					int fdAttendResult = 0;
					if(json.containsKey("fdAttendResult")) {
						fdAttendResult = json.getInteger("fdAttendResult");
					}
					int fdMonthLateNum = 0;
					if(json.containsKey("fdMonthLateNum")) {
						fdMonthLateNum = json.getInteger("fdMonthLateNum");
					}
					int fdMonthForgerNum = 0;
					if(json.containsKey("fdMonthForgerNum")) {
						fdMonthForgerNum = json.getInteger("fdMonthForgerNum");
					}
					int fdMonthLateMinNum = 0;
					if(json.containsKey("fdMonthLateMinNum")) {
						fdMonthLateMinNum = json.getInteger("fdMonthLateMinNum");
					}
					Float standWorkTime =0F;
					if (json.containsKey("fdStandWorkTime")) {
						standWorkTime =json.getFloat("fdStandWorkTime") == null ? 8f :  json.getFloat("fdStandWorkTime");
					}
					
					int fdOverTimeWithoutDeduct =0;
					if (json.containsKey("fdOverTimeWithoutDeduct")) {
						fdOverTimeWithoutDeduct =json.getInteger("fdOverTimeWithoutDeduct");
					}
					int fdDateType = json.getInteger("fdDateType");
					int fdMissedExcCount = json.getInteger("fdMissedExcCount");
					int fdLateExcCount = json.getInteger("fdLateExcCount");
					int fdLeftExcCount = json.getInteger("fdLeftExcCount");
					JSONObject offDaysDetail = json
							.getJSONObject("fdOffCountDetail");
					String fdOffCountDetail = offDaysDetail.isEmpty() ? null
							: offDaysDetail.toString();
					String docCreatorHId = json
							.containsKey("docCreatorHId")
									? json.getString("docCreatorHId") : null;
					float fdAbsentDays = 0;
					float fdPersonalLeaveDays = 0;
					float fdOutgoingTime = 0;
					if (json.containsKey("fdPersonalLeaveDays") && !StringUtil
							.isNull(json.getString("fdPersonalLeaveDays"))) {
						fdPersonalLeaveDays = json.getFloatValue("fdPersonalLeaveDays");
					}
					if (json.containsKey("fdAbsentDays") && !StringUtil
							.isNull(json.getString("fdAbsentDays"))) {
						fdAbsentDays = json.getFloatValue("fdAbsentDays");
					}
					if (json.containsKey("fdOutgoingTime") && !StringUtil
							.isNull(json.getString("fdOutgoingTime"))) {
						fdOutgoingTime = json.getFloatValue("fdOutgoingTime");
					}
					fdOutgoingTime = fdOutgoingTime > 24f ? 24f
							: fdOutgoingTime;
					boolean fdIsNoRecord = json.containsKey("fdIsNoRecord")
							? json.getBoolean("fdIsNoRecord") : false;

					float fdWorkTime = 0;
					if (json.containsKey("fdStandWorkTime") && !StringUtil
							.isNull(json.getString("fdStandWorkTime"))) {
						fdWorkTime = json.getFloatValue("fdStandWorkTime");
					}
					List<String> deleteIds=deleteKeys.get(key);
					if(CollectionUtils.isNotEmpty(deleteIds)){
						for (String delId:deleteIds) {
							delete.setString(1,delId);
							delete.addBatch();
							isDelete = true;
						}
					}
					// 判断是否已统计
					if (updatedOrgs.containsKey(key)) {
						update.setInt(1, fdTotalTime);
						update.setInt(2, fdLateTime);
						update.setInt(3, fdLeftTime);
						update.setBoolean(4, fdStatus);
						update.setBoolean(5, fdOutside);
						update.setBoolean(6, fdLate);
						update.setBoolean(7, fdLeft);
						update.setBoolean(8, fdMissed);
						update.setBoolean(9, fdAbsent);
						update.setInt(10, fdMissedCount);
						update.setInt(11, fdOutsideCount);
						update.setInt(12, fdLateCount);
						update.setInt(13, fdLeftCount);
						update.setBoolean(14, fdTrip);
						update.setBoolean(15, fdOff);
						update.setFloat(16, fdTripDays);
						update.setFloat(17, fdOffDays);
						update.setInt(18, fdOverTime);
						update.setInt(19, fdDateType);
						update.setInt(20, fdMissedExcCount);
						update.setInt(21, fdLateExcCount);
						update.setInt(22, fdLeftExcCount);
						update.setString(23, fdOffCountDetail);
						update.setString(24, docCreatorHId);
						update.setFloat(25, fdAbsentDays);
						update.setFloat(26, fdOutgoingTime);
						update.setInt(27, (int) fdOffTime);
						update.setBoolean(28, fdIsNoRecord);
						update.setString(29, fdCategoryId);
						update.setString(30, fdCategoryName);

						update.setFloat(31, fdOffTime);
						update.setFloat(32, fdPersonalLeaveDays);
						update.setFloat(33, fdWorkTime);
						//通过创建人id查找对应人事数据
						HrStaffPersonInfo person=getHrStaffPersonInfoService().findByOrgPersonId(key);
						update.setString(34, person==null||person.getFdFirstLevelDepartment()==null?"":person.getFdFirstLevelDepartment().getFdName());
						update.setString(35, person==null||person.getFdSecondLevelDepartment()==null?"":person.getFdSecondLevelDepartment().getFdName());
						update.setString(36, person==null||person.getFdThirdLevelDepartment()==null?"":person.getFdThirdLevelDepartment().getFdName());
						//午休时间
						update.setInt(37, restMinsDefault);
						//考勤标准工作时长
						update.setFloat(38, standWorkTime);
						//迟到次数
						update.setInt(39, fdMonthLateNum);
						//月忘打卡次数
						update.setInt(40, fdMonthForgerNum);
						//月迟到分钟数
						update.setInt(41, fdMonthLateMinNum);
						//延时加班时长
						update.setInt(42, fdOverTimeWithoutDeduct);
						//月异常考勤次数
						update.setInt(43, fdAttendResult);
						

						update.setInt(44, fdOverTurnTime);
						update.setInt(45, fdOverPayTime);
						update.setInt(46, fdOverApplyTime);
						update.setInt(47, fdOverTurnApplyTime);
						update.setInt(48, fdOverPayApplyTime);

						update.setString(49, updatedOrgs.get(key));
						update.addBatch();
						isUpdate = true;

					} else {
						String fdId = IDGenerator.generateID();
						insert.setString(1, fdId);
						insert.setTimestamp(2,
								new Timestamp(beginTime.getTime()));
						insert.setInt(3, fdTotalTime);
						insert.setTimestamp(4,
								new Timestamp(new Date().getTime()));
						insert.setInt(5, fdLateTime);
						insert.setInt(6, fdLeftTime);
						insert.setBoolean(7, fdStatus);
						insert.setBoolean(8, fdOutside);
						insert.setString(9, fdCategoryId);
						insert.setString(10, fdCategoryName);
						insert.setString(11, key);
						insert.setBoolean(12, fdLate);
						insert.setBoolean(13, fdLeft);
						insert.setBoolean(14, fdMissed);
						insert.setBoolean(15, fdAbsent);
						insert.setInt(16, fdMissedCount);
						insert.setInt(17, fdOutsideCount);
						insert.setInt(18, fdLateCount);
						insert.setInt(19, fdLeftCount);
						insert.setBoolean(20, fdTrip);
						insert.setBoolean(21, fdOff);
						insert.setFloat(22, fdTripDays);
						insert.setFloat(23, fdOffDays);
						insert.setInt(24, fdOverTime);
						insert.setInt(25, fdDateType);
						insert.setInt(26, fdMissedExcCount);
						insert.setInt(27, fdLateExcCount);
						insert.setInt(28, fdLeftExcCount);
						insert.setString(29, fdOffCountDetail);
						insert.setString(30, docCreatorHId);
						insert.setFloat(31, fdAbsentDays);
						insert.setFloat(32, fdOutgoingTime);
						insert.setInt(33, (int) fdOffTime);
						insert.setBoolean(34, fdIsNoRecord);
						insert.setFloat(35, fdOffTime);
						insert.setString(36, authAreaId);
//						insert.setString(36, areaMap.get(key));
						insert.setFloat(37, fdWorkTime);
						insert.setFloat(38, fdPersonalLeaveDays);
						//通过创建人id查找对应人事数据
						HrStaffPersonInfo person=getHrStaffPersonInfoService().findByOrgPersonId(key);
						insert.setString(39, person==null||person.getFdFirstLevelDepartment()==null?"":person.getFdFirstLevelDepartment().getFdName());
						insert.setString(40, person==null||person.getFdSecondLevelDepartment()==null?"":person.getFdSecondLevelDepartment().getFdName());
						insert.setString(41, person==null||person.getFdThirdLevelDepartment()==null?"":person.getFdThirdLevelDepartment().getFdName());
						
						//午休时间
						insert.setInt(42, restMinsDefault);
						//考勤标准工作时长
						insert.setFloat(43, standWorkTime);
						//迟到次数
						insert.setInt(44, fdMonthLateNum);
						//月忘打卡次数
						insert.setInt(45, fdMonthForgerNum);
						//月迟到分钟数
						insert.setInt(46, fdMonthLateMinNum);
						//延时加班时长
						insert.setInt(47, fdOverTimeWithoutDeduct);
						//月异常考勤次数
						insert.setInt(48, fdAttendResult);

						insert.setInt(49,fdOverTurnTime);
						insert.setInt(50,fdOverPayTime);
						insert.setInt(51,fdOverApplyTime);
						insert.setInt(52,fdOverTurnApplyTime);
						insert.setInt(53,fdOverPayApplyTime);
						insert.addBatch();
						isInsert = true;
					}
				}
				if (isUpdate || isInsert || isDelete) {
					if(isDelete){
						delete.executeBatch();
					}
					if (isUpdate) {
						update.executeBatch();
					}
					if (isInsert) {
						insert.executeBatch();
					}
					this.getConnection().commit();
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex.getMessage(), ex);
				this.getConnection().rollback();
				throw ex;
			} finally {
				JdbcUtils.closeResultSet(rs);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void addDetailBatch(Map<String, JSONObject> statMap,Map<String, JSONObject> monthDataMap, Date beginTime,
			Date endTime)
			throws Exception {
		synchronized (lock) {
			ResultSet rs = null;
			try {
				// 判断是否已统计。放在同一个事务里，防止同一时刻执行统计导致有重复数据
				PreparedStatement statement = this.getSysAttendDetailStatSelectPreparedStatement();
				statement.setTimestamp(1, new Timestamp(beginTime.getTime()));
				statement.setTimestamp(2, new Timestamp(endTime.getTime()));
				rs = statement.executeQuery();
				Map<String, String> updatedOrgs = new HashMap<String, String>();
				Map<String, List<String>> deleteKeys = new HashMap<String, List<String>>();
				while (rs.next()) {
					String key = rs.getString(1);
					if(updatedOrgs.containsKey(key)){
						List<String> deleteIds = deleteKeys.get(key);
						if(CollectionUtils.isEmpty(deleteIds)){
							deleteIds=new ArrayList<>();
						}
						deleteIds.add(rs.getString(2));
						continue;
					}
					updatedOrgs.put(key, rs.getString(2));
				}

				PreparedStatement update = this.getSysAttendDetailStatUpdatePreparedStatement();
				PreparedStatement delete = this.getSysAttendDetailStatDeletePreparedStatement();
				PreparedStatement insert = this.getSysAttendDetailStatInsertPreparedStatement();
				this.getConnection().setAutoCommit(false);
				boolean isInsert = false, isUpdate = false, isDelete =false;
				for (String key : statMap.keySet()) {
					JSONObject json = statMap.get(key);
					JSONObject json1 = monthDataMap.get(key);
					String fdCategoryId = (String) json.get("fdCategoryId");
					if (StringUtil.isNull(fdCategoryId)) {
						logger.warn("该用户没有考勤组,忽略处理!userId:" + key);
						continue;
					}
					
					int restMinsDefault = json.getInteger("restMinsDefault");

					int fdTotalTime = json.getInteger("fdTotalTime");
					int fdRestTime = json.getInteger("fdRestTime");
					fdTotalTime = fdRestTime >= fdTotalTime ? 0
							: (fdTotalTime - fdRestTime);

					int fdMonthLateNum = 0;
					if(json.containsKey("fdMonthLateNum")) {
						fdMonthLateNum = json.getInteger("fdMonthLateNum");
					}else{
						if(json1!=null)
							fdMonthLateNum = json1.getInteger("fdMonthLateNum");
						else
							logger.info(key);
					}
					int fdMonthForgerNum = 0;
					if(json.containsKey("fdMonthForgerNum")) {
						fdMonthForgerNum = json.getInteger("fdMonthForgerNum");
					}
					int fdMonthLateMinNum = 0;
					if(json.containsKey("fdMonthLateMinNum")) {
						fdMonthLateMinNum = json.getInteger("fdMonthLateMinNum");
					}else{
						if(json1!=null)
						fdMonthLateMinNum = json1.getInteger("fdMonthLateMinNum");
						else
							logger.info(key);
					}
					Float standWorkTime =0F;
					if (json.containsKey("fdStandWorkTime")) {
						standWorkTime =json.getFloat("fdStandWorkTime") == null ? 8f : json.getFloat("fdStandWorkTime");
					}
					int fdOverTimeWithoutDeduct =0;
					if (json.containsKey("fdOverTimeWithoutDeduct")) {
						fdOverTimeWithoutDeduct =json.getInteger("fdOverTimeWithoutDeduct");
					}
					int fdOverTime = 0;
					if (json.containsKey("fdOverTime")) {
						fdOverTime =json.getInteger("fdOverTime");
					}
					int fdDateType = json.getInteger("fdDateType");
					List works =  Arrays.asList(((JSONArray) json.get("record")).toArray());
					Collections.sort(works, new Comparator<JSONObject>() {
						@Override
						public int compare(JSONObject o1, JSONObject o2) {
							Long arg1 = o1.getLong("docCreateTime");
							Long arg2 = o2.getLong("docCreateTime");
							return arg1.compareTo(arg2);
						}
					});

					JSONObject work1 = null;
					JSONObject work2 = null;
					JSONObject work3 = null;
					JSONObject work4 = null;

					String fdWorkId1 = null;
					String fdWorkId2 = null;

					Map<String, List> workReocords = new HashMap<String, List>();
					for (int i = 0; i < works.size(); i++) {
						JSONObject r = (JSONObject) works.get(i);
						String fdWorkId = r.getString("fdWorkId");
						if (i == 0) {
							fdWorkId1 = fdWorkId;
						}
						if (i == 2) {
							fdWorkId2 = fdWorkId;
						}
						if (!workReocords.containsKey(fdWorkId)) {
							workReocords.put(fdWorkId, new ArrayList());
						}
						List records = workReocords.get(fdWorkId);
						records.add(r);
					}

					work1 = getWork(workReocords, fdWorkId1, "0");
					work2 = getWork(workReocords, fdWorkId1, "1");
					work3 = getWork(workReocords, fdWorkId2, "0");
					work4 = getWork(workReocords, fdWorkId2, "1");

					Timestamp signTime1 = work1 == null ? null
							: new Timestamp(work1.getLong("docCreateTime"));
					Integer fdStatus1 = work1 == null ? null
							: work1.getInteger("fdStatus");
					Boolean fdOutside1 = work1 == null ? null
							: (work1.getInteger("fdOutside") == 1 ? true : false);
					Number fdState1 = (work1 == null) ? null
							: (Number) work1.get("fdState");
					fdState1 = fdState1 == null ? null : fdState1.intValue();
					Timestamp signTime2 = work2 == null ? null
							: new Timestamp(work2.getLong("docCreateTime"));
					Integer fdStatus2 = work2 == null ? null
							: work2.getInteger("fdStatus");
					Boolean fdOutside2 = work2 == null ? null
							: (work2.getInteger("fdOutside") == 1 ? true : false);
					Number fdState2 = (work2 == null) ? null
							: (Number) work2.get("fdState");
					fdState2 = fdState2 == null ? null : fdState2.intValue();
					Timestamp signTime3 = work3 == null ? null
							: new Timestamp(work3.getLong("docCreateTime"));
					Integer fdStatus3 = work3 == null ? null
							: work3.getInteger("fdStatus");
					Boolean fdOutside3 = work3 == null ? null
							: (work3.getInteger("fdOutside") == 1 ? true : false);
					Number fdState3 = (work3 == null) ? null
							: (Number) work3.get("fdState");
					fdState3 = fdState3 == null ? null : fdState3.intValue();
					Timestamp signTime4 = work4 == null ? null
							: new Timestamp(work4.getLong("docCreateTime"));
					Integer fdStatus4 = work4 == null ? null
							: work4.getInteger("fdStatus");
					Boolean fdOutside4 = work4 == null ? null
							: (work4.getInteger("fdOutside") == 1 ? true : false);
					Number fdState4 = (work4 == null) ? null
							: (Number) work4.get("fdState");
					fdState4 = fdState4 == null ? null : fdState4.intValue();

					String docCreatorHId = json
							.containsKey("docCreatorHId")
							? json.getString("docCreatorHId") : null;
					float fdTripDays = 0;
					float fdOffDays = 0;
					float fdOutgoingTime = 0;
					if (json.containsKey("fdTripDays") && !StringUtil
							.isNull(json.getString("fdTripDays"))) {
						fdTripDays = json.getFloatValue("fdTripDays");
					}
					if (json.containsKey("fdOffDays") && !StringUtil
							.isNull(json.getString("fdOffDays"))) {
						fdOffDays = json.getFloatValue("fdOffDays");
					}
					if (json.containsKey("fdOutgoingTime") && !StringUtil
							.isNull(json.getString("fdOutgoingTime"))) {
						fdOutgoingTime = json.getFloatValue("fdOutgoingTime");
					}
					fdOutgoingTime = fdOutgoingTime > 24f ? 24f
							: fdOutgoingTime;
					List<String> deleteIds=deleteKeys.get(key);
					if(CollectionUtils.isNotEmpty(deleteIds)){
						for (String delId:deleteIds) {
							delete.setString(1,delId);
							delete.addBatch();
							isDelete = true;
						}
					}
					// 判断是否已统计
					if (updatedOrgs.containsKey(key)) {
						update.setTimestamp(1, signTime1);
						update.setObject(2, fdStatus1);
						update.setObject(3, fdOutside1);
						update.setObject(4, fdState1);

						update.setTimestamp(5, signTime2);
						update.setObject(6, fdStatus2);
						update.setObject(7, fdOutside2);
						update.setObject(8, fdState2);

						update.setTimestamp(9, signTime3);
						update.setObject(10, fdStatus3);
						update.setObject(11, fdOutside3);
						update.setObject(12, fdState3);

						update.setTimestamp(13, signTime4);
						update.setObject(14, fdStatus4);
						update.setObject(15, fdOutside4);
						update.setObject(16, fdState4);

						update.setInt(17, fdTotalTime);
						update.setString(18, fdCategoryId);
						update.setInt(19, fdOverTime);
						update.setInt(20, fdDateType);
						update.setString(21, docCreatorHId);
						update.setFloat(22, fdTripDays);
						update.setFloat(23, fdOffDays);
						update.setFloat(24, fdOutgoingTime);
						//通过创建人id查找对应人事数据
						HrStaffPersonInfo person=getHrStaffPersonInfoService().findByOrgPersonId(key);
						update.setString(25, person==null||person.getFdFirstLevelDepartment()==null?"":person.getFdFirstLevelDepartment().getFdName());
						update.setString(26, person==null||person.getFdSecondLevelDepartment()==null?"":person.getFdSecondLevelDepartment().getFdName());
						update.setString(27, person==null||person.getFdThirdLevelDepartment()==null?"":person.getFdThirdLevelDepartment().getFdName());
						//午休时间
						update.setInt(28, restMinsDefault);
						//考勤标准工作时长
						update.setFloat(29, standWorkTime);
						//迟到次数
						update.setInt(30, fdMonthLateNum);
						//月忘打卡次数
						update.setInt(31, fdMonthForgerNum);
						//月迟到分钟数
						update.setInt(32, fdMonthLateMinNum);
						//延时加班时长
						update.setInt(33, fdOverTimeWithoutDeduct);
						//id
						update.setString(34, updatedOrgs.get(key));

						update.addBatch();
						isUpdate = true;
					} else {
						insert.setString(1, IDGenerator.generateID());
						insert.setTimestamp(2, signTime1);
						insert.setObject(3, fdStatus1);
						insert.setObject(4, fdOutside1);
						insert.setObject(5, fdState1);

						insert.setTimestamp(6, signTime2);
						insert.setObject(7, fdStatus2);
						insert.setObject(8, fdOutside2);
						insert.setObject(9, fdState2);

						insert.setTimestamp(10, signTime3);
						insert.setObject(11, fdStatus3);
						insert.setObject(12, fdOutside3);
						insert.setObject(13, fdState3);

						insert.setTimestamp(14, signTime4);
						insert.setObject(15, fdStatus4);
						insert.setObject(16, fdOutside4);
						insert.setObject(17, fdState4);
						insert.setTimestamp(18,
								new Timestamp(beginTime.getTime()));
						insert.setInt(19, fdTotalTime);
						insert.setTimestamp(20,
								new Timestamp(new Date().getTime()));
						insert.setString(21, fdCategoryId);
						insert.setString(22, key);//创建人id
						insert.setInt(23, fdOverTime);
						insert.setInt(24, fdDateType);
						insert.setString(25, docCreatorHId);
						insert.setFloat(26, fdTripDays);
						insert.setFloat(27, fdOffDays);
						insert.setFloat(28, fdOutgoingTime);
						//通过创建人id查找对应人事数据
						HrStaffPersonInfo person=getHrStaffPersonInfoService().findByOrgPersonId(key);
						insert.setString(29, person==null||person.getFdFirstLevelDepartment()==null?"":person.getFdFirstLevelDepartment().getFdName());
						insert.setString(30, person==null||person.getFdSecondLevelDepartment()==null?"":person.getFdSecondLevelDepartment().getFdName());
						insert.setString(31, person==null||person.getFdThirdLevelDepartment()==null?"":person.getFdThirdLevelDepartment().getFdName());
						//午休时间
						insert.setInt(32,restMinsDefault);
						//考勤标准工作时长
						insert.setFloat(33, standWorkTime);
						//迟到次数
						insert.setInt(34, fdMonthLateNum);
						//月忘打卡次数
						insert.setInt(35, fdMonthForgerNum);
						//月迟到分钟数
						insert.setInt(36, fdMonthLateMinNum);
						//延时加班时长
						insert.setInt(37, fdOverTimeWithoutDeduct);
						insert.addBatch();
						isInsert = true;
					}

				}
				if (isUpdate || isInsert || isDelete) {
					if (isDelete) {
						delete.executeBatch();
					}
					if (isUpdate) {
						update.executeBatch();
					}
					if (isInsert) {
						insert.executeBatch();
					}
					this.getConnection().commit();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex.getMessage(), ex);
				this.getConnection().rollback();
				throw ex;
			} finally {
				JdbcUtils.closeResultSet(rs);
			}
		}
	}
	@SuppressWarnings("unchecked")
	private void addDetailBatch(Map<String, JSONObject> statMap, Date beginTime,
			Date endTime)
			throws Exception {
		synchronized (lock) {
			ResultSet rs = null;
			try {
				// 判断是否已统计。放在同一个事务里，防止同一时刻执行统计导致有重复数据
				PreparedStatement statement = this.getSysAttendDetailStatSelectPreparedStatement();
				statement.setTimestamp(1, new Timestamp(beginTime.getTime()));
				statement.setTimestamp(2, new Timestamp(endTime.getTime()));
				rs = statement.executeQuery();
				Map<String, String> updatedOrgs = new HashMap<String, String>();
				Map<String, List<String>> deleteKeys = new HashMap<String, List<String>>();
				while (rs.next()) {
					String key = rs.getString(1);
					if(updatedOrgs.containsKey(key)){
						List<String> deleteIds = deleteKeys.get(key);
						if(CollectionUtils.isEmpty(deleteIds)){
							deleteIds=new ArrayList<>();
						}
						deleteIds.add(rs.getString(2));
						continue;
					}
					updatedOrgs.put(key, rs.getString(2));
				}

				PreparedStatement update = this.getSysAttendDetailStatUpdatePreparedStatement();
				PreparedStatement delete = this.getSysAttendDetailStatDeletePreparedStatement();
				PreparedStatement insert = this.getSysAttendDetailStatInsertPreparedStatement();
				this.getConnection().setAutoCommit(false);
				boolean isInsert = false, isUpdate = false, isDelete =false;
				for (String key : statMap.keySet()) {
					JSONObject json = statMap.get(key);
					String fdCategoryId = (String) json.get("fdCategoryId");
					if (StringUtil.isNull(fdCategoryId)) {
						logger.warn("该用户没有考勤组,忽略处理!userId:" + key);
						continue;
					}
					
					int restMinsDefault = json.getInteger("restMinsDefault");

					int fdTotalTime = json.getInteger("fdTotalTime");
					int fdRestTime = json.getInteger("fdRestTime");
					fdTotalTime = fdRestTime >= fdTotalTime ? 0
							: (fdTotalTime - fdRestTime);

					int fdMonthLateNum = 0;
					if(json.containsKey("fdMonthLateNum")) {
						fdMonthLateNum = json.getInteger("fdMonthLateNum");
					}
					int fdMonthForgerNum = 0;
					if(json.containsKey("fdMonthForgerNum")) {
						fdMonthForgerNum = json.getInteger("fdMonthForgerNum");
					}
					int fdMonthLateMinNum = 0;
					if(json.containsKey("fdMonthLateMinNum")) {
						fdMonthLateMinNum = json.getInteger("fdMonthLateMinNum");
					}
					Float standWorkTime =0F;
					if (json.containsKey("fdStandWorkTime")) {
						standWorkTime =json.getFloat("fdStandWorkTime") == null ? 8f : json.getFloat("fdStandWorkTime");
					}
					int fdOverTimeWithoutDeduct =0;
					if (json.containsKey("fdOverTimeWithoutDeduct")) {
						fdOverTimeWithoutDeduct =json.getInteger("fdOverTimeWithoutDeduct");
					}
					int fdOverTime = 0;
					if (json.containsKey("fdOverTime")) {
						fdOverTime =json.getInteger("fdOverTime");
					}
					int fdDateType = json.getInteger("fdDateType");
					List works =  Arrays.asList(((JSONArray) json.get("record")).toArray());
					Collections.sort(works, new Comparator<JSONObject>() {
						@Override
						public int compare(JSONObject o1, JSONObject o2) {
							Long arg1 = o1.getLong("docCreateTime");
							Long arg2 = o2.getLong("docCreateTime");
							return arg1.compareTo(arg2);
						}
					});

					JSONObject work1 = null;
					JSONObject work2 = null;
					JSONObject work3 = null;
					JSONObject work4 = null;

					String fdWorkId1 = null;
					String fdWorkId2 = null;

					Map<String, List> workReocords = new HashMap<String, List>();
					for (int i = 0; i < works.size(); i++) {
						JSONObject r = (JSONObject) works.get(i);
						String fdWorkId = r.getString("fdWorkId");
						if (i == 0) {
							fdWorkId1 = fdWorkId;
						}
						if (i == 2) {
							fdWorkId2 = fdWorkId;
						}
						if (!workReocords.containsKey(fdWorkId)) {
							workReocords.put(fdWorkId, new ArrayList());
						}
						List records = workReocords.get(fdWorkId);
						records.add(r);
					}

					work1 = getWork(workReocords, fdWorkId1, "0");
					work2 = getWork(workReocords, fdWorkId1, "1");
					work3 = getWork(workReocords, fdWorkId2, "0");
					work4 = getWork(workReocords, fdWorkId2, "1");

					Timestamp signTime1 = work1 == null ? null
							: new Timestamp(work1.getLong("docCreateTime"));
					Integer fdStatus1 = work1 == null ? null
							: work1.getInteger("fdStatus");
					Boolean fdOutside1 = work1 == null ? null
							: (work1.getInteger("fdOutside") == 1 ? true : false);
					Number fdState1 = (work1 == null) ? null
							: (Number) work1.get("fdState");
					fdState1 = fdState1 == null ? null : fdState1.intValue();
					Timestamp signTime2 = work2 == null ? null
							: new Timestamp(work2.getLong("docCreateTime"));
					Integer fdStatus2 = work2 == null ? null
							: work2.getInteger("fdStatus");
					Boolean fdOutside2 = work2 == null ? null
							: (work2.getInteger("fdOutside") == 1 ? true : false);
					Number fdState2 = (work2 == null) ? null
							: (Number) work2.get("fdState");
					fdState2 = fdState2 == null ? null : fdState2.intValue();
					Timestamp signTime3 = work3 == null ? null
							: new Timestamp(work3.getLong("docCreateTime"));
					Integer fdStatus3 = work3 == null ? null
							: work3.getInteger("fdStatus");
					Boolean fdOutside3 = work3 == null ? null
							: (work3.getInteger("fdOutside") == 1 ? true : false);
					Number fdState3 = (work3 == null) ? null
							: (Number) work3.get("fdState");
					fdState3 = fdState3 == null ? null : fdState3.intValue();
					Timestamp signTime4 = work4 == null ? null
							: new Timestamp(work4.getLong("docCreateTime"));
					Integer fdStatus4 = work4 == null ? null
							: work4.getInteger("fdStatus");
					Boolean fdOutside4 = work4 == null ? null
							: (work4.getInteger("fdOutside") == 1 ? true : false);
					Number fdState4 = (work4 == null) ? null
							: (Number) work4.get("fdState");
					fdState4 = fdState4 == null ? null : fdState4.intValue();

					String docCreatorHId = json
							.containsKey("docCreatorHId")
							? json.getString("docCreatorHId") : null;
					float fdTripDays = 0;
					float fdOffDays = 0;
					float fdOutgoingTime = 0;
					if (json.containsKey("fdTripDays") && !StringUtil
							.isNull(json.getString("fdTripDays"))) {
						fdTripDays = json.getFloatValue("fdTripDays");
					}
					if (json.containsKey("fdOffDays") && !StringUtil
							.isNull(json.getString("fdOffDays"))) {
						fdOffDays = json.getFloatValue("fdOffDays");
					}
					if (json.containsKey("fdOutgoingTime") && !StringUtil
							.isNull(json.getString("fdOutgoingTime"))) {
						fdOutgoingTime = json.getFloatValue("fdOutgoingTime");
					}
					fdOutgoingTime = fdOutgoingTime > 24f ? 24f
							: fdOutgoingTime;
					List<String> deleteIds=deleteKeys.get(key);
					if(CollectionUtils.isNotEmpty(deleteIds)){
						for (String delId:deleteIds) {
							delete.setString(1,delId);
							delete.addBatch();
							isDelete = true;
						}
					}
					// 判断是否已统计
					if (updatedOrgs.containsKey(key)) {
						update.setTimestamp(1, signTime1);
						update.setObject(2, fdStatus1);
						update.setObject(3, fdOutside1);
						update.setObject(4, fdState1);

						update.setTimestamp(5, signTime2);
						update.setObject(6, fdStatus2);
						update.setObject(7, fdOutside2);
						update.setObject(8, fdState2);

						update.setTimestamp(9, signTime3);
						update.setObject(10, fdStatus3);
						update.setObject(11, fdOutside3);
						update.setObject(12, fdState3);

						update.setTimestamp(13, signTime4);
						update.setObject(14, fdStatus4);
						update.setObject(15, fdOutside4);
						update.setObject(16, fdState4);

						update.setInt(17, fdTotalTime);
						update.setString(18, fdCategoryId);
						update.setInt(19, fdOverTime);
						update.setInt(20, fdDateType);
						update.setString(21, docCreatorHId);
						update.setFloat(22, fdTripDays);
						update.setFloat(23, fdOffDays);
						update.setFloat(24, fdOutgoingTime);
						//通过创建人id查找对应人事数据
						HrStaffPersonInfo person=getHrStaffPersonInfoService().findByOrgPersonId(key);
						update.setString(25, person==null||person.getFdFirstLevelDepartment()==null?"":person.getFdFirstLevelDepartment().getFdName());
						update.setString(26, person==null||person.getFdSecondLevelDepartment()==null?"":person.getFdSecondLevelDepartment().getFdName());
						update.setString(27, person==null||person.getFdThirdLevelDepartment()==null?"":person.getFdThirdLevelDepartment().getFdName());
						//午休时间
						update.setInt(28, restMinsDefault);
						//考勤标准工作时长
						update.setFloat(29, standWorkTime);
						//迟到次数
						update.setInt(30, fdMonthLateNum);
						//月忘打卡次数
						update.setInt(31, fdMonthForgerNum);
						//月迟到分钟数
						update.setInt(32, fdMonthLateMinNum);
						//延时加班时长
						update.setInt(33, fdOverTimeWithoutDeduct);
						//id
						update.setString(34, updatedOrgs.get(key));

						update.addBatch();
						isUpdate = true;
					} else {
						insert.setString(1, IDGenerator.generateID());
						insert.setTimestamp(2, signTime1);
						insert.setObject(3, fdStatus1);
						insert.setObject(4, fdOutside1);
						insert.setObject(5, fdState1);

						insert.setTimestamp(6, signTime2);
						insert.setObject(7, fdStatus2);
						insert.setObject(8, fdOutside2);
						insert.setObject(9, fdState2);

						insert.setTimestamp(10, signTime3);
						insert.setObject(11, fdStatus3);
						insert.setObject(12, fdOutside3);
						insert.setObject(13, fdState3);

						insert.setTimestamp(14, signTime4);
						insert.setObject(15, fdStatus4);
						insert.setObject(16, fdOutside4);
						insert.setObject(17, fdState4);
						insert.setTimestamp(18,
								new Timestamp(beginTime.getTime()));
						insert.setInt(19, fdTotalTime);
						insert.setTimestamp(20,
								new Timestamp(new Date().getTime()));
						insert.setString(21, fdCategoryId);
						insert.setString(22, key);//创建人id
						insert.setInt(23, fdOverTime);
						insert.setInt(24, fdDateType);
						insert.setString(25, docCreatorHId);
						insert.setFloat(26, fdTripDays);
						insert.setFloat(27, fdOffDays);
						insert.setFloat(28, fdOutgoingTime);
						//通过创建人id查找对应人事数据
						HrStaffPersonInfo person=getHrStaffPersonInfoService().findByOrgPersonId(key);
						insert.setString(29, person==null||person.getFdFirstLevelDepartment()==null?"":person.getFdFirstLevelDepartment().getFdName());
						insert.setString(30, person==null||person.getFdSecondLevelDepartment()==null?"":person.getFdSecondLevelDepartment().getFdName());
						insert.setString(31, person==null||person.getFdThirdLevelDepartment()==null?"":person.getFdThirdLevelDepartment().getFdName());
						//午休时间
						insert.setInt(32,restMinsDefault);
						//考勤标准工作时长
						insert.setFloat(33, standWorkTime);
						//迟到次数
						insert.setInt(34, fdMonthLateNum);
						//月忘打卡次数
						insert.setInt(35, fdMonthForgerNum);
						//月迟到分钟数
						insert.setInt(36, fdMonthLateMinNum);
						//延时加班时长
						insert.setInt(37, fdOverTimeWithoutDeduct);
						insert.addBatch();
						isInsert = true;
					}

				}
				if (isUpdate || isInsert || isDelete) {
					if (isDelete) {
						delete.executeBatch();
					}
					if (isUpdate) {
						update.executeBatch();
					}
					if (isInsert) {
						insert.executeBatch();
					}
					this.getConnection().commit();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex.getMessage(), ex);
				this.getConnection().rollback();
				throw ex;
			} finally {
				JdbcUtils.closeResultSet(rs);
			}
		}
	}

	
	private JSONObject getWork(Map<String, List> workReocords, String fdWorkId,
			String workType) {
		if (workReocords.isEmpty() || StringUtil.isNull(fdWorkId)
				|| StringUtil.isNull(workType)) {
			return null;
		}
		List records = workReocords.get(fdWorkId);
		JSONObject record = null;
		for (int i = 0; i < records.size(); i++) {
			JSONObject obj = (JSONObject) records.get(i);
			String fdWorkType = obj.getString("fdWorkType");
			if (workType.equals(fdWorkType)) {
				record = obj;
				break;
			}
		}
		return record;
	}

	/**
	 * 计算出差天数
	 *
	 * @param fdTripCount
	 * @param fdWorkTimeSize
	 * @return
	 */
	private float recalTripDays(int fdTripCount, int fdWorkTimeSize) {
		float fdTripDays = 0;
		if (fdTripCount > 0) {
			if (fdTripCount <= fdWorkTimeSize) {
				fdTripDays = 0.5f;
			} else {
				fdTripDays = 1f;
			}
		}
		return fdTripDays;
	}

	/**
	 * 获取请假时长
	 * @param noonType 上午 1 /下午 2
	 * @param signTimeList 班次信息
	 * @return
	 */
	private static long getLeaveMinsNew( Integer noonType,
									  Date date,
									  List<Map<String, Object>> signTimeList) {
		//上午、多班次取班次1，1班次取开始时间到午休时间
		//下午、多班次取班次2，1班次取午休结束时间到下班时间
		long totalHourMin =0;
		//开始、结束同一天，开始半天是上午，结束半天是下午。则取一整天。
		if(CollectionUtils.isNotEmpty(signTimeList)){
			//班次
			int workClass =0;
			for (int i = 0; i < signTimeList.size() / 2; i++) {
				workClass++;
				// 上班
				Map<String, Object> startMap = signTimeList.get(2 * i);
				// 下班
				Map<String, Object> endMap = signTimeList.get(2 * i + 1);
				//标准的上班打卡时间
				Date startSignTime = (Date) startMap.get("signTime");
				//标准的下班打卡时间
				Date endSignTime = (Date) endMap.get("signTime");
				// 跨天排班 下班时间要加一天
				Integer overTimeType = (Integer) endMap.get("overTimeType");
				Date endTempDate = AttendUtil.getDate(date, 0);
				if (Integer.valueOf(2).equals(overTimeType)) {
					endTempDate = AttendUtil.getDate(date, 1);
				}
				startSignTime = AttendUtil.joinYMDandHMS(date, startSignTime);
				endSignTime = AttendUtil.joinYMDandHMS(endTempDate, endSignTime);
				//开始结束时间
				if(signTimeList.size() ==2){
					//单班次
					// 只有一个班次时，才有午休时间
					Map<String, Object> map = signTimeList.get(0);
					Date restStart = null;
					Date restEnd = null;
					if (map.containsKey("fdRestStartTime") && map.containsKey("fdRestEndTime")) {
						restStart = (Date) map.get("fdRestStartTime");
						restEnd = (Date) map.get("fdRestEndTime");
					}
					if (restStart != null && restEnd != null) {
						//午休时间组装
						restStart = AttendUtil.joinYMDandHMS(date, restStart);
						restEnd = AttendUtil.joinYMDandHMS(date, restEnd);
						if(Integer.valueOf(1).equals(noonType)){
							//上午的话，开始时间到午休的开始时间
							totalHourMin = SysTimeUtil.getTotalMins(startSignTime,restStart);
						}else if(Integer.valueOf(2).equals(noonType)){
							//下午，午休结束时间到下班打卡时间
							totalHourMin = SysTimeUtil.getTotalMins(restEnd,endSignTime);
						}
					}
				} else {
					//如果是下午，并且当前是班次1.则找下个班次
					if(Integer.valueOf(2).equals(noonType) && workClass==1){
						//如果
						continue;
					}
					//多班次
					totalHourMin = SysTimeUtil.getTotalMins(startSignTime,endSignTime);
				}
				if(Integer.valueOf(1).equals(noonType)){
					break;
				}
			}
		}
		return totalHourMin;
	}
	/**
	 * 计算请假天数，请假小时
	 *
	 * @param offCountDetail
	 *            请假记录的个数
	 * @param fdWorkTimeSize
	 *            班次数
	 * @param leaveList
	 *            流程表单的请假数据
	 * @param isRestDay
	 *            休息日
	 * @param category
	 * @param ele
	 * @return
	 * @throws Exception
	 */
	private Map<String, JSONObject> recalOffDayAndTime(
			JSONObject offCountDetail, int fdWorkTimeSize, List leaveList,
			Date date, boolean isRestDay, SysAttendCategory category,
			SysOrgElement ele,JSONObject statJson) throws Exception {
		Map<String, JSONObject> offDataMap = new HashMap<String, JSONObject>();
		if (leaveList.isEmpty()) {
			return offDataMap;
		}
		Float standWorkTime =0F;
		if (statJson.containsKey("fdStandWorkTime")) {
			standWorkTime =statJson.getFloat("fdStandWorkTime");
		}

		// 不考虑时间重叠的情况
		for (int i = 0; i < leaveList.size(); i++) {
			SysAttendBusiness sysAttendBusiness = (SysAttendBusiness) leaveList.get(i);

			// 获取请假当天的开始时间和结束时间
			Map<String, Object> leaveMap = getLeaveTime(
					sysAttendBusiness.getFdStatType(),
					sysAttendBusiness.getFdBusStartTime(),
					sysAttendBusiness.getFdBusEndTime(),
					sysAttendBusiness.getFdStartNoon(),
					sysAttendBusiness.getFdEndNoon(), date);

			if (leaveMap.isEmpty()) {
					continue;
			}

			Date leaveStart = (Date) leaveMap.get("leaveStart");
			Date leaveEnd = (Date) leaveMap.get("leaveEnd");

			if (leaveStart == null || leaveEnd == null
					|| leaveStart.getTime() > leaveEnd.getTime()) {
				continue;
			}

			Float offDay = 0f;
			Float offTime = 0f;

			Integer fdStatType = sysAttendBusiness.getFdStatType();
			Integer fdBusType = sysAttendBusiness.getFdBusType();
			Integer fdStatDayType = Integer.valueOf(2);// 统计日期默认自然日

			SysTimeLeaveRule sysTimeLeaveRule = null;
			if (fdBusType != null) {
				// 根据请假类型获取相关配置
				sysTimeLeaveRule = AttendUtil.getLeaveRuleByType(fdBusType);
				if (sysTimeLeaveRule != null) {
					fdStatDayType = sysTimeLeaveRule.getFdStatDayType();
				}
			}
			//非休息日  或者 非工作日
			if (!isRestDay
					|| !Integer.valueOf(1).equals(fdStatDayType)) {

				if (fdStatType == null) {
					// 以前数据、本次更新后的数据，暂不考虑这种历史数据。
					int offCount = 0;
					String leaveType = fdBusType == null ? "NaN"
							: fdBusType + "";
					if (offCountDetail.containsKey(leaveType)) {
						offCount = offCountDetail.getInteger(leaveType);
					}
					if (offCount > 0) {// 有请假记录
						if (offCount <= fdWorkTimeSize) {
							offDay = 0.5f;
						} else {
							offDay = 1f;
						}
					} else {// 没有请假记录
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						cal.set(Calendar.HOUR_OF_DAY, 12);
						Date noon = cal.getTime();
						if (leaveStart.getTime() <= noon.getTime()
								&& leaveEnd.getTime() >= noon.getTime()) {// 包含中午12点则算一天，否则半天
							offDay = 1f;
						} else {
							offDay = 0.5f;
						}
					}
				} else {
					long totalHourMin = SysTimeUtil.getTotalMins(leaveStart, leaveEnd);
					//自然日
					boolean need = Integer.valueOf(2).equals(fdStatDayType);
					List<Map<String, Object>>  signTimeList = sysAttendCategoryService.getAttendSignTimes(category, date,ele,need);
					if (fdStatType == 1) {
						// 按天
						if (totalHourMin >= 24 * 60) {
							//一整天
							offDay = 1F;
							offTime = standWorkTime;
						}
					} else if (fdStatType == 2) {
						// 按半天
						if (totalHourMin > 0 && totalHourMin <= 12 * 60) {
							Calendar cal = Calendar.getInstance();
							cal.setTime(date);
							cal.set(Calendar.HOUR_OF_DAY, 12);
							//结束时间等于12点。则标识是上午。否则就是下午
							long leaveMins = getLeaveMinsNew(leaveEnd.getTime() ==cal.getTimeInMillis()?1:2,date,signTimeList);
							offTime = leaveMins / 60F;
							offDay = 0.5F;
						} else if (totalHourMin >= 24 * 60) {
							//一整天
							offTime = standWorkTime;
							offDay = 1F;
						}
					} else if (fdStatType == 3) {
						// 按小时
						if (totalHourMin > 0) {
							try {
								// 跨天排班 并且按小时请假
								if (signTimeList != null && signTimeList.size() >= 2) {
									Map<String, Object> offWorkMap = signTimeList.get(signTimeList.size() - 1);
									Integer overTimeType = (Integer) offWorkMap.get("overTimeType");
									if (AttendConstant.FD_OVERTIME_TYPE[2].equals(overTimeType)) {
										leaveEnd = sysAttendBusiness.getFdBusEndTime();
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							// 分钟数
							int leaveHourMin = SysTimeUtil.getLeaveMins1(leaveStart, leaveEnd, date, signTimeList);
							offTime = leaveHourMin / 60f;
							if(offTime>1)
								if((offTime-1)%0.5!=0)
							offTime=(float) (offTime-(offTime-1)%0.5+0.5);
							//如果按照时间的，转换到统计表中，转换为天。存储单天转换的天数值
							offDay = offTime / standWorkTime ;
						}
					}
				}
			}
			if (offDay > 0f || offTime > 0) {
				String offKey = fdBusType == null ? "NaN" : fdBusType + "";
				if (!offDataMap.containsKey(offKey) || fdStatType == null) {
					// fdStatTyp为null是根据考勤记录个数统计的，数据不能加起来
					JSONObject json = new JSONObject();
					json.put("offDay", offDay);
					json.put("offTime", offTime);
					json.put("statType", fdStatType == null ? 2 : fdStatType);
					offDataMap.put(offKey, json);
				} else {
					JSONObject json = offDataMap.get(offKey);
					int _statType = json.getInteger("statType");
					float _offDay = 0;
					float _offTime = 0;
					if (json.containsKey("offDay") && !StringUtil.isNull(json.getString("offDay"))) {
						_offDay = json.getFloatValue("offDay");
					}
					if (json.containsKey("offTime") && !StringUtil.isNull(json.getString("offTime"))) {
						_offTime = json.getFloatValue("offTime");
					}
					if (Integer.valueOf(_statType).equals(fdStatType)) {
						_offTime += offTime;
						_offDay += offDay;
						json.put("offDay", _offDay);
						json.put("offTime", _offTime);
						json.put("statType", fdStatType == null ? 2 : fdStatType);
						offDataMap.put(offKey, json);
					}
				}
			}
		}
		return offDataMap;
	}

	private JSONObject formatOffDataJson(Float fdStandWorkTime,Map<String, JSONObject> offDataMap) {
		JSONObject offDataJson = new JSONObject();
		if (offDataMap.isEmpty()) {
			return offDataJson;
		}
		float totalDay = 0f;
		float totalHour = 0f;
		for (String offKey : offDataMap.keySet()) {
			JSONObject json = offDataMap.get(offKey);
			int statType = json.getInteger("statType");
			float offDay = 0;
			float offTime = 0;
			if (json.containsKey("offDay") && !StringUtil
					.isNull(json.getString("offDay"))) {
				offDay = json.getFloatValue("offDay");
			}
			if (json.containsKey("offTime") && !StringUtil
					.isNull(json.getString("offTime"))) {
				offTime = json.getFloatValue("offTime");
			}
			offDay = offDay > 1.0f ? 1.0f : offDay;
			offTime = offTime > 24f ? 24f : offTime;
			JSONObject offJson = new JSONObject();
			offJson.put("count", statType == 3 ? offTime : offDay);
			offJson.put("statType", statType);
			offDataJson.put(offKey, offJson);
			BigDecimal bigDecimal = new BigDecimal(offDay);

		
			
			BigDecimal bigDecimal1 = new BigDecimal(offTime/fdStandWorkTime);

			totalDay += offDay;
//			totalDay += bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();;
			totalHour += bigDecimal1.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();;
		}
		totalDay = totalDay > 1.0f ? 1.0f : totalDay;
		totalHour = totalHour > 24f ? 24f : totalHour;
		offDataJson.put("totalDay", totalDay);
		offDataJson.put("totalHour", totalHour);
		return offDataJson;
	}
	/**
	 * 获取人员的请假流程数据
	 * @param docCreatorIdList
	 * @param fdStartTime
	 * @param fdEndTime
	 * @return
	 */
	private List<SysAttendBusiness> getLeaveRecord(List<String> docCreatorIdList, Date fdStartTime,
			Date fdEndTime) {
		List recordList = new ArrayList();
		logger.debug("getLeaveRecord start....");
		try {
			List<SysAttendBusiness> list = sysAttendBusinessService.findLeaveList(docCreatorIdList, fdStartTime, fdEndTime);
			// 过滤无效数据
			for (SysAttendBusiness leaveBus : list) {
				String leaveId = leaveBus.getFdBusDetailId();
				if (StringUtil.isNull(leaveId)) {
					recordList.add(leaveBus);
					continue;
				}
				SysTimeLeaveDetail leaveDetail = (SysTimeLeaveDetail) getSysTimeLeaveDetailService().findByPrimaryKey(leaveId, null, true);
				if (leaveDetail != null) {
					if (leaveDetail.getFdTotalTime() > 0) {
						recordList.add(leaveBus);
					}
				}
			}
			logger.debug("getLeaveRecord end....");
			return recordList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取请假数据:" + e.getMessage(), e);
		}
		return recordList;
	}

	/**
	 * 加班时长的计算
	 *
	 * @param category          考勤组
	 * @param businessListAll   流程列表
	 * @param sysAttendMainList 打卡列表
	 * @param docCreate         人员
	 * @param workDate          统计日
	 * @return
	 * @throws Exception
	 */
	private int recalOvertime(SysAttendCategory category,
							  List<SysAttendBusiness> businessListAll,
							  List<Object[]> sysAttendMainList,
							  SysOrgElement docCreate,
							  Date workDate, boolean isRestDay, String fdOverHandle) throws Exception {
		//考勤组是否允许统计加班
		Boolean fdIsOvertime = category.getFdIsOvertime();
		//加班选项
		Integer fdOvtReviewType = category.getFdOvtReviewType();
		if (Boolean.FALSE.equals(fdIsOvertime)) {
			return 0;
		}
		if (fdOvtReviewType == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("加班规则为空，考勤组:{}", category.getFdName());
			}
			return 0;
		}
//		List<SysAttendBusiness> businessList = new ArrayList<>();
//		//加班流程。其他流程不处理
//		for (SysAttendBusiness business : businessListAll) {
//			if (Integer.valueOf(6).equals(business.getFdType())) {
//				businessList.add(business);
//			}
//		}

		for (SysAttendBusiness business : businessListAll) {
			if (!Integer.valueOf(6).equals(business.getFdType())) {
				businessListAll.remove(business);
			}
		}
		//加班规则是2 3 4 都需要加班单 才计算。否则直接返回0
		boolean isOvtType = Integer.valueOf(1).equals(fdOvtReviewType) || Integer.valueOf(2).equals(fdOvtReviewType) || Integer.valueOf(3).equals(fdOvtReviewType);
		if (isOvtType && CollectionUtils.isEmpty(businessListAll)) {
			return 0;
		}
		List<Map<String, Object>> signTimeList = null;
		if (!isRestDay) {
			//非休息日的 当天的排班情况
			signTimeList = sysAttendCategoryService.getAttendSignTimes(category, workDate, docCreate);
		}
		//该天最早最晚打卡时间集合。以每个班次为Key
		Map<String, Map<String, Date>> workSignTimeMap = null;

		//打卡加班时长
		int signOverTime = 0;
		//流程加班时长
		int businessOverTime = 0;
		//返回加班时长
		int resut = 0;
		//获取排班的打卡时间区间列表
		List<AttendComparableTime> signTimeComparabeList = AttendOverTimeUtil.getStandardSignTime(signTimeList, workDate,
				category.getFdIsCalculateOvertime(),
				category.getFdBeforeWorkOverTime()
		);
		//获取排除时间的区间列表
		List<AttendComparableTime> deductTimeComparabeList = AttendOverTimeUtil.getDeductTime(category, workDate);
		//上班时间区间+排除时间区间
		signTimeComparabeList.addAll(deductTimeComparabeList);
		//取打卡的加班时间
		if (!Integer.valueOf(1).equals(fdOvtReviewType)) {
			//以审批单为准，则不需要打卡记录
			workSignTimeMap = AttendOverTimeUtil.getSignTime(sysAttendMainList, docCreate.getFdId());
		}

		if (fdOvtReviewType == 0 || fdOvtReviewType == 2) {
			//加班规则选项1.完全需要根据打卡时间来计算
			if (MapUtils.isNotEmpty(workSignTimeMap)) {
				//根据打卡时间来取加班时长
				for (Map.Entry<String, Map<String, Date>> workTimeInfo : workSignTimeMap.entrySet()) {
					//每个班次的打卡时间
					Map<String, Date> everyWorkRuleSignTime = workTimeInfo.getValue();
					Date signStartTime = everyWorkRuleSignTime.get("startTime");
					Date endTime = everyWorkRuleSignTime.get("endTime");
					AttendComparableTime baseComparableTime = new AttendComparableTime(signStartTime, endTime);
					if (logger.isDebugEnabled()) {
						logger.debug("打卡时间:{}-{}", signStartTime, endTime);
					}
					signOverTime += AttendOverTimeUtil.differencesMis(baseComparableTime, signTimeComparabeList);
				}
			}
		}
		//流程跟加班时长的关系
		Map<String, Integer> businessOverTimeMap = new HashMap<String, Integer>();
		if (fdOvtReviewType == 1 || fdOvtReviewType == 2) {
			//规则2.加班以审批单为准。和规则3 审批单和打卡记录谁大取谁
			if (CollectionUtils.isNotEmpty(businessListAll)) {
				//每个流程扣除标准上下班时间和扣除时间区间。得到的加班时长结果
				for (SysAttendBusiness business : businessListAll) {
					//每个班次的打卡时间
					Date signStartTime = business.getFdBusStartTime();
					Date endTime = business.getFdBusEndTime();
					if(business.getFdActualOverBeginTime()!=null){
						signStartTime = business.getFdActualOverBeginTime();
						endTime = business.getFdActualOverEndTime();
					}
					if (logger.isDebugEnabled()) {
						logger.debug("计算审批时间:{}-{}", signStartTime, endTime);
					}
					AttendComparableTime baseComparableTime = new AttendComparableTime(signStartTime, endTime);
					int tempOverTime = AttendOverTimeUtil.differencesMis(baseComparableTime, signTimeComparabeList);
					//获取流程中的用餐时间
					int fdMealTimes = StringUtil.isNotNull(business.getFdMealTimes())?Integer.parseInt(business.getFdMealTimes()):0;
					if(tempOverTime > fdMealTimes){
						tempOverTime = tempOverTime - fdMealTimes;
					}else{
						tempOverTime = 0;
					}
					//定制修改，增加加班处理方式
					if(StringUtil.isNotNull(fdOverHandle) && !fdOverHandle.equals(business.getFdOverHandle())){
						continue;
					}else{
						businessOverTime += tempOverTime;
					}
					if (fdOvtReviewType == 1) {
						//审批单为准的，则记录每次的加班时长。
						businessOverTimeMap.put(business.getFdId(), tempOverTime);
					}
				}
				resut = businessOverTime;
			}
			if (fdOvtReviewType == 1 && resut == 0) {
				//审批单为准。如果得到的结果是0.则直接返回
				return resut;
			}
			if (fdOvtReviewType == 2 && signOverTime == 0) {
				//规则3，打卡时长为0.则直接返回。
				return 0;
			}
		}
		if (fdOvtReviewType == 2) {
			//实际打卡的加班时长 和 流程单的加班时长。谁大就取谁
			resut = signOverTime > businessOverTime ? signOverTime : businessOverTime;
		} else if (fdOvtReviewType == 3) {
			//打卡时间和加班审批单取交集
			if (MapUtils.isNotEmpty(workSignTimeMap)) {
				//根据打卡时间来取加班时长
				for (Map.Entry<String, Map<String, Date>> workTimeInfo : workSignTimeMap.entrySet()) {
					//每个班次的打卡时间
					Map<String, Date> everyWorkRuleSignTime = workTimeInfo.getValue();
					Date startOvertime = everyWorkRuleSignTime.get("startTime");
					Date endOvertime = everyWorkRuleSignTime.get("endTime");
					for (SysAttendBusiness business : businessListAll) {
						int tempOverTime = 0;
						
						Date fdStartTime = business.getFdBusStartTime();
						Date fdEndTime = business.getFdBusEndTime();
						
					
		            	if(business.getFdActualOverBeginTime()!=null) {
							fdStartTime = business.getFdActualOverBeginTime();
							fdEndTime = business.getFdActualOverEndTime();
							long actualOverEndTime = business.getFdActualOverEndTime().getTime();
			            	long actualOverBeginTime = business.getFdActualOverBeginTime().getTime();
			            	long busStartTime = business.getFdBusStartTime().getTime();
			            	long busEndTime = business.getFdBusEndTime().getTime();
			            	long unionTime = 0;
			            	if(actualOverEndTime<=busEndTime&&actualOverEndTime>busStartTime&&actualOverBeginTime<busStartTime)
			            		unionTime=actualOverEndTime-busStartTime;
			            	if(actualOverEndTime<=busEndTime&&actualOverEndTime>busStartTime&&actualOverBeginTime>=busStartTime)
			            		unionTime=actualOverEndTime-actualOverBeginTime;
			            	if(actualOverBeginTime<busEndTime&&actualOverEndTime>=busEndTime&&actualOverBeginTime>=busStartTime)
			            		unionTime=busEndTime-actualOverBeginTime;

			            	if(busEndTime<=actualOverEndTime&&busEndTime>actualOverBeginTime&&busStartTime<=actualOverBeginTime)
			            		unionTime=busEndTime-actualOverBeginTime;
			            	if(busEndTime<=actualOverEndTime&&busEndTime>actualOverBeginTime&&busStartTime>=actualOverBeginTime)
			            		unionTime=busEndTime-busStartTime;
			            	if(busStartTime<actualOverEndTime&&busEndTime>=actualOverEndTime&&busStartTime>=actualOverBeginTime)
			            		unionTime=actualOverEndTime-busStartTime;
			            	tempOverTime = (int) (unionTime/60000-Integer.parseInt(business.getFdMealTimes()));
						}
						//流程中的开始结束时间不符合要求的，不统计
						if (fdStartTime == null || fdEndTime == null || fdStartTime.getTime() > fdEndTime.getTime()) {
							continue;
						}
						if (fdEndTime.getTime() < startOvertime.getTime()) {
							//流程的结束时间，在打卡的开始之前。则不处理
							continue;
						}
						if (fdStartTime.getTime() > endOvertime.getTime()) {
							//流程的开始时间，大于班次的结束打卡时间。则不处理
							continue;
						}
						//取交集，必须打卡开始结束时间都存在。
						if (startOvertime != null && fdEndTime != null) {
							//默认开始结束时间为流程填写的开始结束时间
							//流程加班开始时间小于 打卡开始时间。则以打卡开始时间为准
							if (fdStartTime.getTime() < startOvertime.getTime()) {
								fdStartTime = new Date(startOvertime.getTime());
							}
							//流程加班结束时间大于 打卡最晚时间。则以打卡最晚时间为准
							if (endOvertime != null && fdEndTime.getTime() > endOvertime.getTime()) {
								fdEndTime = new Date(endOvertime.getTime());
							}
							if (logger.isDebugEnabled()) {
								logger.debug("交集结果时间:{}-{}", fdStartTime, fdEndTime);
							}
							AttendComparableTime baseComparableTime = new AttendComparableTime(fdStartTime, fdEndTime);
//							int tempOverTime = AttendOverTimeUtil.differencesMis(baseComparableTime, signTimeComparabeList);
//							//获取流程中的用餐时间
//							int fdMealTimes = StringUtil.isNotNull(business.getFdMealTimes())?Integer.parseInt(business.getFdMealTimes()):0;
//							if(tempOverTime > fdMealTimes){
//								tempOverTime = tempOverTime - fdMealTimes;
//							}else{
//								tempOverTime = 0;
//							}
							//定制修改，增加加班处理方式
							if(StringUtil.isNotNull(fdOverHandle) && !fdOverHandle.equals(business.getFdOverHandle())){
								continue;
							}else{
								businessOverTime += tempOverTime;
							}
							long flexTime = 0l;
							if(signTimeList!=null&&category.getFdIsFlex() == true && category.getFdFlexTime() > 0 && signTimeList.size() > 0){
								flexTime = AttendUtil.getFlexTimes(category,startOvertime,signTimeList.get(0));
							}
							//实际加班时间
							if(signTimeComparabeList != null && signTimeComparabeList.size() > 0){
								Date[] dates = AttendUtil.getOverDate(baseComparableTime,signTimeComparabeList.get(0),flexTime);
								logger.warn("加班开始时间：{}，结束时间：{}", dates[0],dates[1]);
								if(dates != null){
									business.setFdActualOverBeginTime(dates[0]);
									//加班实际结束时间，直接去下班打卡时间
									business.setFdActualOverEndTime(endOvertime);
								}
							}else{
								business.setFdActualOverBeginTime(startOvertime);
								business.setFdActualOverEndTime(endOvertime);
							}
							business.setFdOverTimes(AttendUtil.floor(tempOverTime) / 60.0);
							//流程跟打卡取交集以后每个流程的加班时长
							businessOverTimeMap.put(business.getFdId(), tempOverTime);
						}
					}
				}
				resut = businessOverTime;
			} else {
				//没有打卡记录 则直接返回
				return 0;
			}
		}
		int fdLeastOverTime = 0;
		//最小加班时长分钟数，如果该值为空 则使用原来按小时的配置计算
		if (category.getFdMinOverTime() == null) {
			//考勤组配置的-最小加班时间（兼容历史数据）
			fdLeastOverTime = category.getFdMinHour() != null ? category.getFdMinHour().intValue() * 60 : 0;
		} else {
			//如果配置了，则直接使用最小加班的分钟数计算
			fdLeastOverTime = category.getFdMinOverTime().intValue();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("加班时长:{}，最小加班配置：{}", resut, fdLeastOverTime);
		}
		//加班时长小于加班最小时长为0
		if (resut < fdLeastOverTime) {
			return 0;
		}
		//固定满减扣除的分钟数
		int deductMins = AttendOverTimeUtil.getTimeDeductMins(category, resut);
		if (logger.isDebugEnabled()) {
			logger.debug("固定扣减时长:{}", deductMins);
		}
		//加班时间减去扣减时间 之后的加班时间
		resut = resut - deductMins < 0 ? 0 : resut - deductMins;
		if (logger.isDebugEnabled()) {
			logger.debug("取整前加班时长:{}", resut);
		}
		// 取整后时长
		resut = AttendUtil.getOverTime(category, resut);
		if (logger.isDebugEnabled()) {
			logger.debug("取整后加班时长:{}", resut);
		}
		if (CollectionUtils.isNotEmpty(businessListAll)) {
			//规则1，规则3，如果审批单存在。则使用第一个审批单把额度加入 写到假期额度。
			if (fdOvtReviewType == 0 || fdOvtReviewType == 2) {
				//加班时长为0，或者审批单为空。则直接返回
				businessListAll.get(0).setOverTime(resut);
			} else if (fdOvtReviewType == 1 || fdOvtReviewType == 3) {
				//规则2，规则4 各个流程的加班时长
				for (SysAttendBusiness business : businessListAll) {
					//每个流程计算出来的结果
					Integer overTimeValue = businessOverTimeMap.get(business.getFdId());
					business.setOverTime(overTimeValue == null ? 0 : overTimeValue);
				}
				if (deductMins > 0) {
					//不够扣的数量
					int negativeOverTime = 0;
					for (SysAttendBusiness business : businessListAll) {
						//扣减剩余额度
						int businessLastOverTime = -1;
						if (negativeOverTime < 0) {
							//上一个流程不够减的。则用本流程扣减
							businessLastOverTime = business.getOverTime() - Math.abs(negativeOverTime);
						} else {
							businessLastOverTime = business.getOverTime() - deductMins;
						}
						//扣减额度足够的情况下，则不继续执行
						if (businessLastOverTime > -1) {
							business.setOverTime(businessLastOverTime);
							break;
						} else {
							//扣减额度不足，则把剩余需要扣减的数量，用于下一个审批单扣减
							business.setOverTime(0);
							negativeOverTime = businessLastOverTime;
						}
					}
				}
			}
		}
		return resut;
	}

	/**
	 * 计算加班申请时长，以流程申请的为主
	 *
	 * @param category        考勤组
	 * @param businessListAll 流程列表
	 * @param docCreate       人员
	 * @param workDate        统计日
	 * @return
	 * @throws Exception
	 */
	private int recalOverApplytime(SysAttendCategory category,
								   List<SysAttendBusiness> businessListAll,
								   SysOrgElement docCreate,
								   Date workDate, boolean isRestDay, String fdOverHandle) throws Exception {
		//加班流程。其他流程不处理
		try{
		for (SysAttendBusiness business : businessListAll) {
			if (!Integer.valueOf(6).equals(business.getFdType())) {
				businessListAll.remove(business);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		List<Map<String, Object>> signTimeList = null;
		if (!isRestDay) {
			//非休息日的 当天的排班情况
			signTimeList = sysAttendCategoryService.getAttendSignTimes(category, workDate, docCreate);
		}
		//流程加班时长
		int businessOverTime = 0;
		//返回加班时长
		int resut = 0;
		//获取排班的打卡时间区间列表
		List<AttendComparableTime> signTimeComparabeList = AttendOverTimeUtil.getStandardSignTime(signTimeList, workDate,
				category.getFdIsCalculateOvertime(),
				category.getFdBeforeWorkOverTime()
		);
		//获取排除时间的区间列表
		List<AttendComparableTime> deductTimeComparabeList = AttendOverTimeUtil.getDeductTime(category, workDate);
		//上班时间区间+排除时间区间
		signTimeComparabeList.addAll(deductTimeComparabeList);
		//流程跟加班时长的关系
		if (CollectionUtils.isNotEmpty(businessListAll)) {
			//每个流程扣除标准上下班时间和扣除时间区间。得到的加班时长结果
			for (SysAttendBusiness business : businessListAll) {
				//每个班次的打卡时间
				Date signStartTime = business.getFdBusStartTime();
				Date endTime = business.getFdBusEndTime();
//				if(business.getFdActualOverBeginTime()!=null){
//					signStartTime = business.getFdActualOverBeginTime();
//					endTime = business.getFdActualOverEndTime();
//			}
				logger.info("计算审批时间:{}-{}", signStartTime, endTime);
//				AttendComparableTime baseComparableTime = new AttendComparableTime(signStartTime, endTime);
//				int tempOverTime = AttendOverTimeUtil.differencesMis(baseComparableTime, signTimeComparabeList);
				//获取分钟数，直接使用流程中结束时间减去开始时间
				int tempOverTime = (int) ((endTime.getTime() - signStartTime.getTime()) / 60000);
				//获取流程中的用餐时间
				int fdMealTimes = StringUtil.isNotNull(business.getFdMealTimes()) ? Integer.parseInt(business.getFdMealTimes()) : 0;
				if (tempOverTime > fdMealTimes) {
					tempOverTime = tempOverTime - fdMealTimes;
				} else {
					tempOverTime = 0;
				}
				//定制修改，增加加班处理方式
				if (StringUtil.isNotNull(fdOverHandle) && !fdOverHandle.equals(business.getFdOverHandle())) {
					continue;
				} else {
					businessOverTime += tempOverTime;
				}
				business.setFdOverApplyTimes(AttendUtil.floor(tempOverTime) / 60.0);
			}
			resut = businessOverTime;
		}
		// 取整后时长
		resut = AttendUtil.getOverTime(category, resut);
		logger.debug("取整后加班时长:{}", resut);
		return resut;
	}


//	/**
//	 * 重新统计加班时间
//	 * @param category 考勤组
//	 * @param fdBeforeOverTime  取整前实际加班工时
//	 * @param fdOverTime 实际加班工時
//	 * @param fdLeastOverTime 最小加班工時（考勤組配置的值）
//	 * @param fdIsOvertime 是否統計加班。FLASE 直接返回0
//	 * @param fdOvtReviewType 加班统计规则，【0、加班不审批，工时自动计算，1、加班需审批，工时计算：以审批单为准，2、加班需审批，工时计算：实际加班工时小于审批单时，以审批单为准；实际加班工时大于审批单时，以实际打卡为准，3、加班需审批，工时计算：实际打卡时间和申请加班时间取交集】
//	 * @param recordList 流程记录列表
//	 * @param fdMissed 是否缺卡
//	 * @param overtimeWithoutDeduct 扣减工时
//	 * @param sysAttendMainList 打卡记录列表
//	 * @param docCreatorId 统计的人员
//	 * @return
//	 */
//	private int recalOvertime(SysAttendCategory category, int fdBeforeOverTime, int fdOverTime,
//			int fdLeastOverTime,boolean fdIsOvertime, int fdOvtReviewType, List<SysAttendBusiness> recordList,boolean fdMissed, int overtimeWithoutDeduct,
//			List<Object[]> sysAttendMainList,  String docCreatorId
//	) throws Exception {
//
//		if (Boolean.FALSE.equals(fdIsOvertime)) {
//			return 0;
//		}
//		if (logger.isDebugEnabled()) {
//			logger.debug("category:" + category.getFdName()
//					+ ", 没有扣减的工时overtimeWithoutDeduct:" + overtimeWithoutDeduct
//					+ ", 考勤计算fdOverTime:" + fdOverTime + ",考勤加班时间：" + fdBeforeOverTime + ",最小加班时间：" + fdLeastOverTime);
//		}
//		//是否扣除加班时长
//		boolean isOvertimeDeduct = category.getFdIsOvertimeDeduct() == null ? false : category.getFdIsOvertimeDeduct();
//		//扣减规则，0是时间段、1是满减
//		int fdOvtDeductType = category.getFdOvtDeductType() == null ? 0 : category.getFdOvtDeductType();
//		int deductMins = 0;
//		int overTime = 0;
//		int resut = 0;
//		//最早打卡开始时间
//		Timestamp startOvertime = null;
//		//最晚打卡结束时间
//		Timestamp endOvertime = null;
//		List<SysAttendBusiness> tempList = new ArrayList<>();
//		if (fdOvtReviewType == 0) {
//			//加班工时不审批自动计算
//			if (fdBeforeOverTime < fdLeastOverTime) {
//				resut = 0;
//			}else {
//				resut = fdOverTime >= fdLeastOverTime ? fdOverTime : 0;
//			}
//			//如果有审批单，则写入假期额度
//			if(CollectionUtils.isNotEmpty(recordList) && resut > 0){
//				//如果同一天有多个审批单，则分摊到各个审批单中。平均分配
//				int avgMinute= (int) Math.ceil(resut/recordList.size());
//				for (SysAttendBusiness business:recordList) {
//					business.setOverTime(avgMinute);
//				}
//			}
//			return resut;
//		} else {
//			//加班类型 以打卡时间相关
//			if (fdOvtReviewType == 3) {
//				//根据打卡记录和流程记录来过滤。
//				for (Object obj : sysAttendMainList) {
//					Object[] record = (Object[]) obj;
//					//考勤组
//					String statCreatorId = (String) record[0];
//					if (statCreatorId != null && statCreatorId.equals(docCreatorId)) {
//						Timestamp createTime = (Timestamp) record[2];
//						if (startOvertime == null || createTime.getTime() < startOvertime.getTime()) {
//							startOvertime = createTime;
//						}
//						if (endOvertime == null || createTime.getTime() > endOvertime.getTime()) {
//							endOvertime = createTime;
//						}
//					}
//					Boolean fdIsAcross = getBooleanField(record[11]);
//					String fdWorkId = (String) record[6];
//					Number fdWorkType = (Number) record[3];
//					//打卡记录是上班的记录
//					if (fdWorkType.intValue() == 0) {
//						for (Iterator<SysAttendBusiness> it = recordList.iterator(); it.hasNext(); ) {
//							SysAttendBusiness business = (SysAttendBusiness) it.next();
//							//是加班流程的单据时才处理
//							if (Integer.valueOf(6).equals(business.getFdType())) {
//								SysOrgElement ele = business.getFdTargets().get(0);
//								Date docCreateTime = business.getFdBusEndTime();
//								Date workDate = AttendUtil.getDate(docCreateTime, 0);
//								//是否跨天 跨天取打卡时间的前一天
//								if (Boolean.TRUE.equals(fdIsAcross)) {
//									workDate = AttendUtil.getDate(docCreateTime, -1);
//								}
//								SysAttendCategoryWorktime workTime = this.getWorkTime(category, fdWorkId, fdWorkType.intValue(),
//										ele,
//										business.getFdBusEndTime(),
//										workDate);
//								//没有排班的情况，休息日加班处理
//								if (workTime != null && workTime.getFdStartTime() != null) {
//									Date tempFdStartTime = AttendUtil.joinYMDandHMS(docCreateTime,
//											workTime.getFdStartTime());
//									//如果流程的结束时间，比 要求考勤的时间 小
//									if (tempFdStartTime.getTime() >= docCreateTime.getTime()) {
//										it.remove();
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//			//循环流程记录
//			for (Object obj : recordList) {
//				SysAttendBusiness business = (SysAttendBusiness) obj;
//				Integer fdType = business.getFdType();
//				if (!Integer.valueOf(6).equals(fdType)) {
//					//6为加班
//					continue;
//				}
//				Date fdStartTime = business.getFdBusStartTime();
//				Date fdEndTime = business.getFdBusEndTime();
//				//流程中的开始结束时间不符合要求的，不统计
//				if (fdStartTime == null || fdEndTime == null || fdStartTime.getTime() > fdEndTime.getTime()) {
//					continue;
//				}
//				//取交集，必须打卡开始结束时间都存在。
//				if (fdOvtReviewType == 3 && startOvertime != null && fdEndTime != null) {
//					//默认开始结束时间为流程填写的开始结束时间
//					//流程加班开始时间小于 打卡开始时间。则以打卡开始时间为准
//					if (fdStartTime.getTime() < startOvertime.getTime()) {
//						fdStartTime = new Date(startOvertime.getTime());
//					}
//					//流程加班结束时间大于 打卡最晚时间。则已打卡最晚时间为准
//					if (endOvertime != null && fdEndTime.getTime() > endOvertime.getTime()) {
//						fdEndTime = new Date(endOvertime.getTime());
//					}
//				}
//				// 按时间段扣除加班
//				if (isOvertimeDeduct && fdOvtDeductType == 0) {
//					List<JSONObject> periods = new ArrayList<>();
//					JSONObject period = new JSONObject();
//					period.put("fdStartTime", fdStartTime.getTime());
//					period.put("fdEndTime", fdEndTime.getTime());
//					periods.add(period);
//					deductMins += AttendOverTimeUtil.getDeductMins(category, periods);
//				}
//				int tempOverTime = 0;
//				//如果fdCountHour等于空，代表是自动计算。有值代表手动填写
//				if (business.getFdCountHour() == null
//						|| business.getFdCountHour() <= 0f) {
//					tempOverTime = (int) Math.ceil(((fdEndTime.getTime() - fdStartTime.getTime()) / 1000d / 60d));
//				} else {
//					int intValue = business.getFdCountHour().intValue();
//					tempOverTime = intValue * 60;
//				}
//				overTime += tempOverTime;
//				//流程已经写入到请假明细，则不处理计算加班时间的重新计算
//				SysTimeLeaveDetail leaveDetail = getSysTimeLeaveDetailService().findLeaveDetail(docCreatorId, business.getFdProcessId(), business.getFdBusStartTime(), business.getFdBusEndTime());
//				if (leaveDetail == null || leaveDetail.getFdLeaveTime() == null || leaveDetail.getFdLeaveTime() <= 0) {
//					//写入假期额度的时间标识
//					business.setOverTime(tempOverTime);
//					tempList.add(business);
//				}
//			}
//			//按加班工时扣除
//			if (isOvertimeDeduct && fdOvtDeductType == 1) {
//				// 按满减扣除加班
//				deductMins = AttendOverTimeUtil.getDeductMinsForThreahold(category, overTime);
//			}
//			if (logger.isDebugEnabled()) {
//				logger.debug("category:" + category.getFdName()
//						+ ", 没有扣减的工时overtimeWithoutDeduct:" + overtimeWithoutDeduct
//						+ ", 考勤计算fdOverTime:" + fdOverTime + ", 审批单总时长：" + overTime
//						+ ", 扣除deductMins：" + deductMins);
//			}
//		}
//		//加班时间减去扣减时间 之后的加班时间
//		int lastOverTime= overTime - deductMins < 0 ? 0 : overTime - deductMins;
//		//如果类型是以审批单为准，则不需要考虑打卡记录
//		if(fdOvtReviewType == 1){
//			resut = lastOverTime;
//		} else if(fdOvtReviewType == 2 || fdOvtReviewType == 3){
//			if (fdMissed == Boolean.FALSE) {
//				// 缺卡必须补卡后才能统计
//				if (fdOvtReviewType == 2) {
//					resut = overtimeWithoutDeduct > overTime ? fdOverTime : lastOverTime;
//				} else if (fdOvtReviewType == 3) {
////					resut = overtimeWithoutDeduct > overTime ? lastOverTime : fdOverTime;
//					resut = lastOverTime;
//				}
//			} else {
//				logger.warn("缺卡必须补卡后才能统计");
//			}
//		}
//		// 取整后时长
//		resut = AttendUtil.getOverTime(category, resut);
//		// 加班时长小于加班最小时长为0
//		resut = resut < fdLeastOverTime ? 0 : resut;
//		//统计的是最终的所有加班单的加班分钟时间，将原始加班总分钟 减去 扣减的分钟。然后再平均分配到各个流程中
//		int diffTime =overTime -resut;
//		if(diffTime > 0 && CollectionUtils.isNotEmpty(tempList)){
//			int avgMinute= (int) Math.ceil(diffTime/tempList.size());
//			for (SysAttendBusiness business:tempList) {
//				business.setOverTime(business.getOverTime()-avgMinute > 0 ?business.getOverTime()-avgMinute:0);
//			}
//		}
//		return resut;
//	}

	/**
	 * 重新计算旷工天数（需要改动。。加个最大时长判断）
	 *
	 * @param fdLateTime
	 * @param fdLeftTime//早退时间
	 * @param fdLateToAbsentTime
	 * @param fdLeftToAbsentTime
	 * @param fdLateToFullAbsTime
	 * @param fdLeftToFullAbsTime
	 * @param fdAbsent
	 * @return
	 */
	private float recalAbsentDays(Integer fdLateTime, Integer fdLeftTime,
			Integer fdLateToAbsentTime, Integer fdLeftToAbsentTime,
			Integer fdLateToFullAbsTime, Integer fdLeftToFullAbsTime,
			boolean fdAbsent,JSONObject monthData,Integer fdLateTotalTime,Integer fdLateNumTotalTime) {
		//获取当前人员
		if (fdLateTime <= 0 && fdLeftTime <= 0 && fdAbsent) {
			return 1;
		}
		float absentDays = 0;
		boolean doLate = false;//计算迟到转事前？
		if (monthData != null) {//如果有月数据
			Integer monthLateTime = monthData.getInteger("fdMonthLateMinNum") == null ? 0 : monthData.getInteger("fdMonthLateMinNum");//获取这个月的迟到总时长
			Integer monthLateNum = monthData.getInteger("fdMonthLateNum") == null ? 0 : monthData.getInteger("fdMonthLateNum");//获取这个月迟到次数的
			fdLateTotalTime = fdLateTotalTime == null ? 0 : fdLateTotalTime;
			fdLateNumTotalTime = fdLateNumTotalTime == null ? 0 : fdLateNumTotalTime;
			if (monthLateTime >= fdLateTotalTime || monthLateNum >= fdLateNumTotalTime) {
				//如果本月累计超过配置阈值，那么需要匹配事假
				doLate = true;
			}
		}

		if (fdLateTime > 0 && doLate) {
			if (fdLateToFullAbsTime != null && fdLateToFullAbsTime > 0
					&& fdLateTime >= fdLateToFullAbsTime) {
				return 1;
			} else if (fdLateToAbsentTime != null && fdLateToAbsentTime > 0
					&& fdLateTime >= fdLateToAbsentTime) {
				absentDays = absentDays + 0.5f;
			}
		}

		if (fdLeftTime > 0) {
			if (fdLeftToFullAbsTime != null && fdLeftToFullAbsTime > 0
					&& fdLeftTime >= fdLeftToFullAbsTime) {
				return 1;
			} else if (fdLeftToAbsentTime != null && fdLeftToAbsentTime > 0
					&& fdLeftTime >= fdLeftToAbsentTime) {
				absentDays = absentDays + 0.5f;
			}
		}
		return absentDays;
	}

	/**
	 * 重新计算外出工时
	 *
	 * @param recordList
	 * @param recordList
	 * @return
	 * @throws Exception
	 */
	private float recalOutgoingTime(List recordList, SysOrgElement ele,
			Date date)
			throws Exception {
		float outGoingTime = 0f;
		for (Object obj : recordList) {
			SysAttendBusiness business = (SysAttendBusiness) obj;
			Integer fdType = business.getFdType();
			Float fdCountHour = business.getFdCountHour();
			if (fdType == null || fdType.intValue() != 7
					|| fdCountHour == null) {// 7为外出
				continue;
			}
			Date startTime = business.getFdBusStartTime();
			Date endTime  = business.getFdBusEndTime();
			List<Map<String, Object>> signTimeList = sysAttendCategoryService.getAttendSignTimes(ele, date);
			outGoingTime += business.getFdCountHour();
//			outGoingTime += SysTimeUtil.getLeaveMins(startTime, endTime, date, signTimeList);
		}
		return outGoingTime;
//		return outGoingTime >= 0 ? outGoingTime / 60f : 0;
	}

	/**
	 * 获取所有考勤用户
	 *
	 * @param fdCategoryId
	 * @return
	 * @throws Exception
	 */
	private List getSignUser(String fdCategoryId,Date date)
			throws Exception {
		if (StringUtil.isNotNull(fdCategoryId)) {
			return sysAttendCategoryService.getAttendPersonIds(fdCategoryId,date);
		} else {
			return sysAttendCategoryService.getAttendPersonIds(date);
		}
	}

	/**
	 * 获取跨天打卡的考勤用户
	 *
	 * @param beginTime
	 * @param endTime
	 * @param fdCategoryId
	 * @return
	 * @throws Exception
	 */
	private List getSignAcrossUser(Date beginTime, Date endTime,
			String fdCategoryId)
			throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		String orgSql = "select DISTINCT doc_creator_id from sys_attend_main "
				+ "where doc_create_time >=? and doc_create_time<? "
				+ "and (fd_work_id is not null or fd_work_key is not null) "
				+ "and fd_is_across=?";
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<String> orgList = new ArrayList<String>();
		try {
			conn = dataSource.getConnection();
			if (StringUtil.isNotNull(fdCategoryId)) {
				List<String> orgIds = sysAttendCategoryService.getAttendPersonIds(fdCategoryId,new Date());
				if (!orgIds.isEmpty()) {
					orgSql = orgSql + " and "
							+ HQLUtil.buildLogicIN("doc_creator_id", orgIds);
				}
			}
			statement = conn.prepareStatement(orgSql);
			statement.setTimestamp(1, new Timestamp(beginTime.getTime()));
			statement.setTimestamp(2, new Timestamp(endTime.getTime()));
			statement.setBoolean(3, true);
			rs = statement.executeQuery();
			while (rs.next()) {
				orgList.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(conn);
		}

		return orgList;
	}

	/**
	 * 获取人员的考勤记录，
	 * @param orgEleIds
	 * @param beginTime
	 * @param endTime
	 * @param isAcross
	 * @return 以每个人员的数据为一组
	 * @throws SQLException
	 */
	private Map<String,List<Object[]>> getSignedRecord(Map<String, Integer> orgEleIds, Date beginTime, Date endTime,Boolean isAcross) throws SQLException {
		ResultSet rs = null;
		PreparedStatement statement = null;
		ResultSet rs2 = null;
		PreparedStatement statement2 = null;
		Map<String,List<Object[]>> resultMap = new HashMap<>();
		List<String> exitList = new ArrayList<>();
		try {
			statement =getSysAttendMainSelectPreparedStatement(Arrays.asList(orgEleIds.keySet().toArray()),isAcross?2:1);
			statement.setTimestamp(1, new Timestamp(beginTime.getTime()));
			statement.setTimestamp(2, new Timestamp(endTime.getTime()));
			statement.setInt(3, isAcross ? 1 : 0);
			
			rs = statement.executeQuery();
			int index=0;
			while (rs.next()) {
				Object[] obj = new Object[] {
						rs.getString(1),
						rs.getObject(2),
						rs.getTimestamp(3),
						rs.getObject(4),
						rs.getBoolean(5),
						rs.getString(6),
						rs.getString(7),
						rs.getObject(8),
						rs.getObject(9),
						rs.getObject(10),
						rs.getString(11),
						rs.getBoolean(12),
						rs.getString(13),
						rs.getString(14),
						rs.getString(15),
						rs.getString(16),
						rs.getString(17),
						rs.getString(18),
						rs.getTimestamp(19)
						//sunny 加了 17.18
				};
				String userId = (String) obj[0];
				List<Object[]> recordList =resultMap.get(userId);
				if(recordList != null){
					recordList.add(obj);
				}else{
					recordList = new ArrayList<>();
					recordList.add(obj);
				}
				resultMap.put(userId,recordList);
			}

			//补录数据
			Set<Map.Entry<String, List<Object[]>>> entries = resultMap.entrySet();
			for (Map.Entry<String, List<Object[]>> entry : entries) {
				if(entry.getValue().size()==1){
					exitList.add(entry.getKey());
					resultMap.get(entry.getKey()).clear();
				}
			}

			statement2 =getSysAttendMainSelectPreparedStatement(exitList,isAcross?2:1);
			statement2.setTimestamp(1, new Timestamp(beginTime.getTime()));
			statement2.setTimestamp(2, new Timestamp(endTime.getTime()));
			statement2.setInt(3, isAcross ? 1 : 0);

			rs2 = statement2.executeQuery();
			while (rs2.next()) {
				Object[] obj2 = new Object[] {
						rs2.getString(1),
						rs2.getObject(2),
						rs2.getTimestamp(3),
						rs2.getObject(4),
						rs2.getBoolean(5),
						rs2.getString(6),
						rs2.getString(7),
						rs2.getObject(8),
						rs2.getObject(9),
						rs2.getObject(10),
						rs2.getString(11),
						rs2.getBoolean(12),
						rs2.getString(13),
						rs2.getString(14),
						rs2.getString(15),
						rs2.getString(16),
						rs2.getString(17),
						rs2.getString(18),
						rs2.getTimestamp(19)
				};
				String userId = (String) obj2[0];
				List<Object[]> recordList =resultMap.get(userId);
				if(recordList != null){
					recordList.add(obj2);
				}else{
					recordList = new ArrayList<>();
					recordList.add(obj2);
				}
				resultMap.put(userId,recordList);
			}


		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			JdbcUtils.closeResultSet(rs2);
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(statement2);
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(getConnection());
		}



		return resultMap;
	}
	/**
	 * 获取考勤记录
	 * @param orgEleIds 人员
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 * @throws Exception
	 */
	private Map<String,List<Object[]>> getSignedRecord(Map<String, Integer> orgEleIds, Date beginTime, Date endTime) throws Exception {
		Map<String,List<Object[]>> resultList = new HashMap<>(orgEleIds.size());
		//当天非跨天的打卡数据
		Map<String,List<Object[]>> resultOne =getSignedRecord(orgEleIds,beginTime,endTime,false);
		//当日跨天的数据，指(属于某天的第2天的打卡数据)
		Map<String,List<Object[]>> resultTwo =getSignedRecord(orgEleIds,AttendUtil.getDate(beginTime, 1),AttendUtil.getDate(endTime,1),true);
		for(Map.Entry<String, Integer> temp:orgEleIds.entrySet()){
			String userId =temp.getKey();
			//每个人的打卡记录
			List<Object[]> allList=new ArrayList<>();
			List<Object[]> recordOneList = resultOne.get(userId);
			if(CollectionUtils.isNotEmpty(recordOneList)){
				allList.addAll(recordOneList);
			}
			List<Object[]> recordTwoList = resultTwo.get(userId);
			if(CollectionUtils.isNotEmpty(recordTwoList)){
				allList.addAll(recordTwoList);
			}
			resultList.put(userId,allList);
		}
		return resultList;
	}

	/**
	 * 统计单人跨天的考勤数据
	 * @param orgElement 人员
	 * @param date 日期
	 * @throws Exception
	 */
	private void statAcross(Date beginTime, Date endTime, String fdCategoryId,
			List<String> personList,Map<String, JSONObject> monthDataMap)
			throws Exception {
		logger.debug("SysAttendStatJob:statAcross start...");
		// 1.跨天打卡的考勤用户
		Set<String> orgList = new HashSet<>();
		if (StringUtil.isNotNull(fdCategoryId)) {
			orgList.addAll(this.getSignAcrossUser(beginTime, endTime, fdCategoryId));
		}
		if(personList!=null){
			orgList.addAll(personList);
		}
		// 跨天的数据实际属于前一天
		beginTime = AttendUtil.getDate(beginTime, -1);
		endTime = AttendUtil.getDate(endTime, -1);
		startProcess(beginTime, endTime, Lists.newArrayList(orgList),monthDataMap);
	}

	private void statAcross(SysOrgElement orgElement, Date date,Map<String, JSONObject> monthDataMap)
			throws Exception {
		logger.debug("SysAttendStatJob:statAcross orgElement start,userName:"
				+ orgElement.getFdName() + ";date:" + date);
		// 防止延迟加载失败
		orgElement = sysOrgCoreService.findByPrimaryKey(orgElement.getFdId());
		IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		refreshSession(baseDao);
		// 跨天的数据实际属于前一天
		Date beginTime = AttendUtil.getDate(date, -1);
		Date endTime = AttendUtil.getDate(date, 0);
		Date nextDate = AttendUtil.getDate(date, 1);

		//当天有流程的人，可能是跨昨天的排班，这些人的昨天数据在统计一次
		List<String> acrossProcessPerson = sysAttendBusinessService.findBussTargetList(Lists.newArrayList(orgElement.getFdId()), beginTime, endTime, null, false);

		// 判断这天是否有跨天打卡数据，避免每次执行都统计两天的数据
		if (!hasAcrossRecord(orgElement, date) && CollectionUtils.isEmpty(acrossProcessPerson)) {
			logger.warn("SysAttendStatJob:statAcross orgElement:hasAcrossRecord is false");
			return;
		}

		String recordSql ="select doc_creator_id,fd_status,doc_create_time,fd_work_type,fd_outside,fd_category_his_id,fd_work_id,fd_state,fd_date_type,fd_off_type,doc_creator_hid,fd_is_across,fd_work_key,fd_location,fd_wifi_name,fd_app_name"
				+ " from sys_attend_main where doc_creator_id =:docCreatorId " +
				" and (fd_work_id is not null or fd_work_key is not null) " +
				" and (doc_status=0 or doc_status is null)";

		StringBuilder queryOne =new StringBuilder(recordSql);
		queryOne.append(" and doc_create_time >=:beginTime and doc_create_time<:endTime and (fd_is_across is null or fd_is_across=:fdIsAcross0)");

		StringBuilder queryTwo =new StringBuilder(recordSql);
		queryTwo.append( " and fd_is_across=:fdIsAcross1 and doc_create_time >=:nextBegin and doc_create_time<:nextEnd ");

		List resultList =new ArrayList();

		List resultOnwList = baseDao.getHibernateSession().createNativeQuery(queryOne.toString())
				.setString("docCreatorId", orgElement.getFdId())
				.setTimestamp("beginTime", beginTime)
				.setTimestamp("endTime", endTime)
				.setBoolean("fdIsAcross0", false)
				.list();
		if(CollectionUtils.isNotEmpty(resultOnwList)){
			resultList.addAll(resultOnwList);
		}

		List resultTwoList = baseDao.getHibernateSession().createNativeQuery(queryTwo.toString())
				.setString("docCreatorId", orgElement.getFdId())
				.setBoolean("fdIsAcross1", true)
				.setTimestamp("nextBegin", endTime)
				.setTimestamp("nextEnd", nextDate)
				.list();

		if(CollectionUtils.isNotEmpty(resultTwoList)){
			resultList.addAll(resultTwoList);
		}
		if (resultList.isEmpty()) {
			logger.debug(
					"SysAttendStatJob:statAcross orgElement:recordList is empty");
			return;
		}
		//人员打卡信息统计
		Map<String,List<Object[]>> recordList =new HashMap<String,List<Object[]>>(1);
		recordList.put(orgElement.getFdId(),resultList);

		Map<String, JSONObject> statMap = new HashMap<String, JSONObject>();
		Map<String, SysAttendCategory> catesMap = new HashMap<String, SysAttendCategory>();
		// 获取用户具体信息
		List<SysOrgElement> eleList = new ArrayList<SysOrgElement>();
		eleList.add(orgElement);
		List<String> orgIdList = new ArrayList<String>();
		orgIdList.add(orgElement.getFdId());
		statUserInfo(recordList, catesMap, statMap, eleList);
		recalUserInfo(recordList, eleList, orgIdList, statMap, catesMap, beginTime, endTime,monthDataMap);
		List<String> orgIdsList = new ArrayList(statMap.keySet());
		addBatch(statMap, beginTime, endTime, SysTimeUtil.getUserAuthAreaMap(orgIdsList));
		addDetailBatch(statMap, beginTime, endTime);
		logger.debug("SysAttendStatJob:statAcross orgElement end...");
	}

	/**
	 * 某人某天是否有跨天打卡的数据
	 * @param personIds
	 * @param date
	 * @return
	 */
	private List<String> hasAcrossRecord(List<String> personIds, Date date) {
		IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		String recordSql = "select doc_creator_id from sys_attend_main "
				+ " where doc_create_time >=:beginTime and doc_create_time<:endTime "
				+ " and fd_is_across=:fdIsAcross1 and (fd_work_id is not null or fd_work_key is not null) and (doc_status=0 or doc_status is null)";
		recordSql +=" and "+ HQLUtil.buildLogicIN("doc_creator_id", personIds);
		List<String>  list = baseDao.getHibernateSession().createNativeQuery(recordSql).setBoolean("fdIsAcross1", true).setTimestamp("beginTime", AttendUtil.getDate(date, 0)).setTimestamp("endTime", AttendUtil.getDate(date, 1)).list();

		return list;
	}

	/**
	 * 某人某天是否有跨天打卡的数据
	 *
	 * @param orgElement
	 * @param date
	 * @return
	 */
	private boolean hasAcrossRecord(SysOrgElement orgElement, Date date) {
		IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		String recordSql = "select count(fd_id) from sys_attend_main "
				+ "where doc_creator_id=:docCreatorId and doc_create_time >=:beginTime and doc_create_time<:endTime "
				+ "and fd_is_across=:fdIsAcross1 and (fd_work_id is not null or fd_work_key is not null) and (doc_status=0 or doc_status is null)";
		List recordList = baseDao.getHibernateSession().createNativeQuery(recordSql).setBoolean("fdIsAcross1", true).setTimestamp("beginTime", AttendUtil.getDate(date, 0)).setTimestamp("endTime", AttendUtil.getDate(date, 1)).setString("docCreatorId", orgElement.getFdId()).list();
		if(recordList.isEmpty()){
			return ((Number) recordList.get(0)).intValue() > 0;
		} else {
			return ((Number) recordList.get(0)).intValue() > 0;
		}
	}

	/**
	 * 获取考勤组的考勤时间设置的信息
	 * @param cate 考勤组
	 * @param fdWorkId 时间班次ID
	 * @param fdWorkType  上下班类型
	 * @param docCreator 打卡人
	 * @param docCreateTime 创建时间
	 * @param workDate 根据当前日期获取其在该天的打卡信息
	 * @return
	 * @throws Exception
	 */
	private SysAttendCategoryWorktime getWorkTime(SysAttendCategory cate,
			String fdWorkId, Integer fdWorkType, SysOrgElement docCreator,
			Date docCreateTime, Date workDate)
			throws Exception {
		if (Integer.valueOf(1).equals(cate.getFdShiftType())) {
			// 排班制
			List<Map<String, Object>> list = this.sysAttendCategoryService.getAttendSignTimes(cate, workDate, docCreator);
			SysAttendCategoryWorktime workTime = this.sysAttendCategoryService
					.getWorkTimeByRecord(list, docCreateTime, fdWorkType);
			return workTime;
		}
		List<SysAttendCategoryWorktime> workTimes = cate.getAllWorkTime();
		SysAttendCategoryWorktime workTime = null;

		if(workTimes.size() == 0 && cate.getFdShiftType() == 3 ){
			workTime = new SysAttendCategoryWorktime();
			workTime.setFdIsAvailable(true);
			workTime.setFdStartTime(DateUtil.getTimeByNubmer((1000 * 60 * 60 * 9)));
			workTime.setFdEndTime(DateUtil.getTimeByNubmer((1000 * 60 * 60 * 18)));
			workTime.setFdOverTimeType(1);
			return workTime;
		}

		if(workTimes!=null && workTimes.size()==1) {
			workTime=workTimes.get(0);
			return workTime;
		}
		for (SysAttendCategoryWorktime work : workTimes) {
			if (work.getFdId().equals(fdWorkId)) {
				workTime = work;
				break;
			}
		}

		return workTime;
	}

	private List<SysAttendCategoryWorktime> getWorkTimes(
			SysAttendCategory category, Date date, SysOrgElement element)
			throws Exception {
		List<SysAttendCategoryWorktime> workTimes = new ArrayList<SysAttendCategoryWorktime>();
		if (category == null || date == null) {
			return workTimes;
		}
		Integer fdShiftType = category.getFdShiftType();
		Integer fdSameWTime = category.getFdSameWorkTime();
		if (Integer.valueOf(0).equals(fdShiftType)
				&& Integer.valueOf(1).equals(fdSameWTime)) {
			List<SysAttendCategoryTimesheet> tSheets = category
					.getFdTimeSheets();
			for (SysAttendCategoryTimesheet tSheet : tSheets) {
				if (StringUtil.isNotNull(tSheet.getFdWeek())
						&& tSheet.getFdWeek()
								.indexOf(AttendUtil.getWeek(date) + "") > -1) {
					workTimes = tSheet.getAvailWorkTime();
					break;
				}
			}
		} else if (Integer.valueOf(1).equals(fdShiftType) && element != null) {
			workTimes = sysAttendCategoryService.getWorkTimeOfTimeArea(element, date);
		} else {
			workTimes = category.getAvailWorkTime();
		}
		return workTimes;
	}

	@Override
	public void deletStat(String fdCategoryId, Date date, List<String> orgList)
			throws Exception {
		if (date == null) {
			return;
		}
		if (StringUtil.isNull(fdCategoryId)
				&& (orgList == null || orgList.isEmpty())) {
			return;
		}
		Thread.sleep(1000);
		List<String> orgIds =new ArrayList<>();
		if(fdCategoryId !=null) {
			List<String> tempOrgIds = sysAttendCategoryService.getAttendPersonIds(fdCategoryId, date);
			if(CollectionUtils.isNotEmpty(tempOrgIds)){
				orgIds.addAll(tempOrgIds);
			}
		}
		if (orgList != null && !orgList.isEmpty()) {
			orgIds.addAll(orgList);
		}
		if (orgIds.isEmpty()) {
			return;
		}
		synchronized (lock) {
			DataSource dataSource = (DataSource) SpringBeanUtil
					.getBean("dataSource");
			Connection conn = null;
			PreparedStatement statStm = null;
			PreparedStatement detailStm = null;
			PreparedStatement listStm = null;
			PreparedStatement listStm2 = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			try {
				Date startTime = AttendUtil.getDate(date, 0);
				Date endTime = AttendUtil.getDate(date, 1);

				conn = dataSource.getConnection();
				String listSql = "select fd_id from sys_attend_stat where fd_date >=? and fd_date<? and "
						+ HQLUtil.buildLogicIN("doc_creator_id", orgIds);
				listStm = conn.prepareStatement(listSql);
				listStm.setTimestamp(1,
						new Timestamp(startTime.getTime()));
				listStm.setTimestamp(2,
						new Timestamp(endTime.getTime()));
				rs = listStm.executeQuery();
				List<String> idList = new ArrayList<String>();
				while (rs.next()) {
					idList.add(rs.getString(1));
				}
				if (!idList.isEmpty()) {
					String delSql = "delete from sys_attend_stat where "
							+ HQLUtil.buildLogicIN("fd_id", idList);
					statStm = conn.prepareStatement(delSql);
					statStm.executeUpdate();
				}
				// 删除详细
				listSql = "select fd_id from sys_attend_stat_detail where fd_date >=? and fd_date<? and "
						+ HQLUtil.buildLogicIN("doc_creator_id", orgIds);
				listStm2 = conn.prepareStatement(listSql);
				listStm2.setTimestamp(1,
						new Timestamp(startTime.getTime()));
				listStm2.setTimestamp(2,
						new Timestamp(endTime.getTime()));
				rs2 = listStm2.executeQuery();
				idList = new ArrayList<String>();
				while (rs2.next()) {
					idList.add(rs2.getString(1));
				}
				if (!idList.isEmpty()) {
					String detailSql = "delete from sys_attend_stat_detail where "
							+ HQLUtil.buildLogicIN("fd_id", idList);
					detailStm = conn.prepareStatement(detailSql);
					detailStm.executeUpdate();
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				JdbcUtils.closeResultSet(rs);
				JdbcUtils.closeResultSet(rs2);
				JdbcUtils.closeStatement(listStm);
				JdbcUtils.closeStatement(listStm2);
				JdbcUtils.closeStatement(statStm);
				JdbcUtils.closeStatement(detailStm);
				JdbcUtils.closeConnection(conn);
			}
		}

	}

	private boolean getBooleanField(Object bValue) {
		if (bValue == null) {
			return false;
		}
		if (bValue instanceof Number) {
			return ((Number) bValue).intValue() == 1;
		}
		if (bValue instanceof Boolean) {
			return ((Boolean) bValue).booleanValue();
		}
		return false;
	}

	private SysOrgElement getAttendPerson(List<SysOrgElement> eleList,
			String docCreatorId) {
		if (eleList == null || eleList.isEmpty()) {
			return null;
		}
		for (SysOrgElement ele : eleList) {
			if (ele.getFdId().equals(docCreatorId)) {
				return ele;
			}
		}
		return null;
	}

	/**
	 * 获取请假当天的开始时间可结束时间
	 *
	 * @param fdStatType
	 * @param fdBusStartTime
	 * @param fdBusEndTime
	 * @param fdStartNoon
	 * @param fdEndNoon
	 * @param date
	 * @return
	 */
	private Map<String, Object> getLeaveTime(Integer fdStatType,
			Date fdBusStartTime,
			Date fdBusEndTime, Integer fdStartNoon, Integer fdEndNoon,
			Date date) {
		Map<String, Object> leaveMap = new HashMap<String, Object>();
		List<Date> dateList = SysTimeUtil.getDateList(fdStatType,
				fdBusStartTime, fdBusEndTime, fdStartNoon, fdEndNoon);
		// 按小时请假特殊处理
		if (AttendConstant.FD_STAT_TYPE[3].equals(fdStatType)) {
			leaveMap.put("leaveStart", fdBusStartTime);
			leaveMap.put("leaveEnd", fdBusEndTime);
			return leaveMap;
		}

		if (!dateList.isEmpty() && dateList.size() >= 2) {
			for (int i = 0; i < dateList.size() - 1; i++) {
				Date leaveStart = dateList.get(i);
				if (AttendUtil.isSameDate(leaveStart, date)) {
					Date leaveEnd = dateList.get(i + 1);
					leaveMap.put("leaveStart", leaveStart);
					leaveMap.put("leaveEnd", leaveEnd);
					break;
				}
			}
		}
		return leaveMap;
	}

	@Override
	public void restat(SysQuartzJobContext context) throws Exception {
		// TODO Auto-generated method stub
		String param = context.getParameter();
		if(StringUtil.isNotNull(param)) {
			JSONArray arr = JSONArray.parseArray(param);

			List<String> statOrgs = getStatOrgList(arr);
			List<Date> statDates = getStatDateList(arr);

			stat(statOrgs,statDates);
		}

	}


	private List<String> getStatOrgList(JSONArray arr)
			throws Exception {
		List<String> idList = new ArrayList<String>();

		for(int i=0;i<arr.size();i++){
			 JSONObject job = arr.getJSONObject(i);
			 String targetIds = job.getString("targetIds");

			 String[] ids = targetIds.split(";");
			 for(String id : ids){
				 idList.add(id);
			 }
		}

		return idList;
	}

	private List<Date> getStatDateList(JSONArray arr)
			throws Exception {
		List<Date> statDates = new ArrayList<Date>();

		for(int i=0;i<arr.size();i++){
			JSONObject job = arr.getJSONObject(i);
			String sTime = job.getString("startTime");
			String eTime = job.getString("endTime");
			Date startTime = AttendUtil.getDate(new Date(Long.valueOf(sTime)), 0);
			Date endTime = AttendUtil.getDate(new Date(Long.valueOf(eTime)), 0);
			Calendar cal = Calendar.getInstance();
			for (cal.setTime(startTime); cal.getTime()
					.compareTo(endTime) <= 0; cal.add(Calendar.DATE, 1)) {
				statDates.add(cal.getTime());
			}
		}
		return statDates;
	}

//	/**
//	 * 重新计算加班时间
//	 * @param recordList 考勤记录
//	 * @param eleList 人员列表
//	 * @param eleIdList 人员ID列表
//	 * @param statMap 统计结果
//	 * @param catesMap 人员考勤封装诗句
//	 * @param beginTime 开始时间
//	 * @param endTime 结束时间
//	 * @param bus 流程数据对象
//	 * @throws Exception
//	 */
//	private void recalOvertime(Map<String,List<Object[]>> recordList,List<SysOrgElement> eleList,
//			List<String> eleIdList, Map<String, JSONObject> statMap,
//			Map<String, SysAttendCategory> catesMap, Date beginTime,
//			Date endTime,SysAttendBusiness bus) throws Exception {
//
//		List<Integer> fdTypes = new ArrayList<Integer>();
//		fdTypes.add(6);
//		List<SysAttendBusiness> busList = sysAttendBusinessService.findBussList(eleIdList, beginTime, AttendUtil.getEndDate(endTime, 1),fdTypes);
//
//		for (SysOrgElement ele : eleList) {
//			try {
//				String docCreatorId = ele.getFdId();
//				JSONObject json = statMap.get(docCreatorId);
//				String fdCategoryId = (String) json.get("fdCategoryId");
//				if (StringUtil.isNull(fdCategoryId)) {
//					continue;
//				}
//				List<Object[]> records =recordList.get(ele.getFdId());
//				if (!statMap.containsKey(docCreatorId)) {
//					// 补全统计数据，防止某个人没有打卡记录不生成其统计数据
//					genUserInfo(catesMap, statMap, ele, beginTime);
//				}
//
//				int fdOverTime = json.getInteger("fdOverTime");
//				int fdBeforeOverTime = json.containsKey("fdBeforeOverTime") ? json.getInteger("fdBeforeOverTime") : 0;
//				boolean fdMissed = json.getInteger("fdMissed") == 1 ? true : false;
//
//				if (!catesMap.containsKey(fdCategoryId)) {
//					SysAttendCategory sysAttendCategory = (SysAttendCategory) sysAttendCategoryService
//							.findByPrimaryKey(fdCategoryId);
//					catesMap.put(fdCategoryId, sysAttendCategory);
//				}
//				SysAttendCategory category = catesMap.get(fdCategoryId);
//
//				int fdLeastOverTime = category.getFdMinHour() != null
//						? category.getFdMinHour().intValue() * 60 : 0;
//				boolean fdIsOvertime =  Boolean.TRUE.equals(category.getFdIsOvertime());
//				int fdOvtReviewType = category.getFdOvtReviewType() != null
//						? category.getFdOvtReviewType() : 0;
//
//				if (!json.containsKey("fdOverTimeWithoutDeduct")) {
//					json.put("fdOverTimeWithoutDeduct", 0);
//				}
//				int overtimeWithoutDeduct = (int) json
//						.get("fdOverTimeWithoutDeduct");
//				Date date = AttendUtil.getDate(beginTime, 0);
//				List busRecordList = sysAttendBusinessService
//						.genUserBusiness(ele, date, busList);
//				//当天的所有加班流程计算加班时间
//				int __fdOverTime = recalOvertime(category, fdBeforeOverTime, fdOverTime,
//						fdLeastOverTime,
//						fdIsOvertime, fdOvtReviewType, busRecordList, fdMissed,
//						overtimeWithoutDeduct, records, docCreatorId);
//				logger.warn("当天加班时间:"+__fdOverTime);
//				busRecordList.remove(bus);
//				// 加班时间  当前加班流程计算加班时间
//				int _fdOverTime=0;
//				if (!busRecordList.isEmpty()) {
//					// 加班时间  当前加班流程计算加班时间
//					_fdOverTime = recalOvertime(category, fdBeforeOverTime, fdOverTime,
//							fdLeastOverTime,
//							fdIsOvertime, fdOvtReviewType, busRecordList, fdMissed,
//							overtimeWithoutDeduct, records, docCreatorId);
//				}
//				logger.warn("除当前加班流程的当天加班时间:"+_fdOverTime);
//
//				json.put("fdOverTime", __fdOverTime-_fdOverTime);
//				json.put("fdWorkDate", AttendUtil.getDate(beginTime, 0).getTime());
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.error(
//						"计算用户加班数据出错,忽略处理!userName:" + ele.getFdName(), e);
//				if (jobContext != null) {
//					jobContext.logError(
//							"计算用户加班数据出错,忽略处理!userName:" + ele.getFdName(),
//							e);
//				}
//			}
//		}
//	}

//	private Map<String, JSONObject> statAcrossForOvertime(Date beginTime, Date endTime,
//			Map<String, Integer> orgEleIdMap, List personList,SysAttendBusiness bus)
//			throws Exception {
//		Map<String, SysAttendCategory> catesMap = new HashMap<String, SysAttendCategory>();
//		beginTime = AttendUtil.getDate(beginTime, -1);
//		endTime = AttendUtil.getDate(endTime, -1);
//		Map<String,List<Object[]>> recordList = this.getSignedRecord(orgEleIdMap, beginTime, endTime);
//		// 3.统计每个用户信息
//		Map<String, JSONObject> statMap = new HashMap<String, JSONObject>();
//		if(recordList !=null && recordList.size() > 0) {
//			// 获取用户具体信息
//			List<SysOrgElement> eleList = this.sysOrgCoreService.expandToPerson(personList);
//			statUserInfo(recordList, catesMap, statMap, eleList);
//			recalOvertime(recordList, eleList, personList, statMap, catesMap, beginTime, endTime, bus);
//			if(logger.isDebugEnabled()) {
//				logger.debug("SysAttendStatJob:statAcrossForOvertime orgElement end...");
//			}
//		}else{
//			if(logger.isDebugEnabled()){
//				logger.debug("SysAttendStatJob:statAcrossForOvertime 没有考勤记录...");
//			}
//		}
//		return statMap;
//	}

	/**
	 * 将人员ID和顺序记录在map中
	 * @param orgEleIds 人员ID对象
	 * @return
	 */
	private Map<String, Integer> convertOrgEleIds2Map(List<String> orgEleIds){
		Map<String, Integer> orgEleIdMap = new HashMap<String, Integer>(orgEleIds.size());
		int index=0;
		for(String orgEleId : orgEleIds){
			orgEleIdMap.put(orgEleId, index++);
		}
		return orgEleIdMap;
	}

	private void initCount() {
		this.countLock.writeLock().lock();
		Integer count = countThreadLocal.get();
		if(count == null){
			countThreadLocal.set(Integer.valueOf(0));
			count = countThreadLocal.get();

		}
		count++;
		this.sysAttendCategoryService.initThreadLocalConfig();
		this.countLock.writeLock().unlock();
	}

	/**
	 * 获取数据库连接
	 * @return
	 * @throws SQLException
	 */
	private Connection getConnection() throws SQLException {
		Connection connection = connThreadLocal.get();
		if(connection == null || connection.isClosed())
		{
			DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
			connThreadLocal.set(dataSource.getConnection());
			connection = connThreadLocal.get();
			connection.setAutoCommit(false);
		}
		return connection;
	}




	private PreparedStatement getSysAttendMainSelectPreparedStatement(List orgList,int searchType) throws SQLException {
		String SELECT_SQL = "SELECT doc_creator_id,fd_status,doc_create_time,fd_work_type,fd_outside,fd_category_his_id,fd_work_id,fd_state,"
				+ "fd_date_type,fd_off_type,doc_creator_hid,fd_is_across,fd_work_key,fd_location,fd_wifi_name,fd_app_name,fd_id,fd_business_id,fd_base_work_time "
				+ " FROM sys_attend_main "
				+ " WHERE (fd_work_id is not null OR fd_work_key is not null) "
				+ " AND (doc_status=0 OR doc_status is null) AND " + HQLUtil.buildLogicIN("doc_creator_id", orgList);
		if(searchType ==1){
			SELECT_SQL +=" and doc_create_time >=? AND doc_create_time<?  AND (fd_is_across is null OR fd_is_across=?) ";
		}else if(searchType ==2){
			SELECT_SQL +=" AND doc_create_time >=? AND doc_create_time<? and fd_is_across=? ";
		}
		SELECT_SQL += " ORDER BY doc_creator_id ";
		Connection connection = getConnection();
		connection.setAutoCommit(true);
		return connection.prepareStatement(SELECT_SQL);
	}

	private PreparedStatement getSysAttendStatSelectPreparedStatement() throws SQLException {
		PreparedStatement preparedStatement = searchSysAttendStatPreparedStatementThreadLocal.get();
		if(preparedStatement == null || preparedStatement.isClosed())
		{
			String SELECT_SQL = "SELECT doc_creator_id,fd_id FROM sys_attend_stat WHERE fd_date >=? and fd_date<? order by doc_create_time asc";
			searchSysAttendStatPreparedStatementThreadLocal.set(getConnection().prepareStatement(SELECT_SQL));
			preparedStatement = searchSysAttendStatPreparedStatementThreadLocal.get();
		}
		return preparedStatement;
	}

	private PreparedStatement getSysAttendDetailStatSelectPreparedStatement() throws SQLException {
		PreparedStatement preparedStatement = searchSysAttendDetailStatPreparedStatementThreadLocal.get();
		if(preparedStatement == null || preparedStatement.isClosed())
		{
			String SELECT_SQL = "SELECT doc_creator_id,fd_id FROM sys_attend_stat_detail WHERE fd_date >=? and fd_date<?  order by doc_create_time asc ";
			searchSysAttendDetailStatPreparedStatementThreadLocal.set(getConnection().prepareStatement(SELECT_SQL));
			preparedStatement = searchSysAttendDetailStatPreparedStatementThreadLocal.get();
		}
		return preparedStatement;
	}

	private PreparedStatement getSysAttendStatUpdatePreparedStatement() throws SQLException {
		PreparedStatement preparedStatement = updateSysAttendStatPreparedStatementThreadLocal.get();
		if(preparedStatement == null || preparedStatement.isClosed())
		{
			String UPDATE_SQL = "UPDATE sys_attend_stat "
					+ "SET fd_total_time=?,fd_late_time=?,fd_left_time=?,fd_status=?,fd_outside=?,"
					+ "fd_late=?,fd_left=?,fd_missed=?,fd_absent=?,fd_missed_count=?,fd_outside_count=?,fd_late_count=?,"
					+ "fd_left_count=?,fd_trip=?,fd_off=?,fd_trip_days=?,fd_off_days=?,fd_over_time=?,fd_date_type=?,"
					+ "fd_missed_exc_count=?,fd_late_exc_count=?,fd_left_exc_count=?,fd_off_count_detail=?,doc_creator_hid=?,"
					+ "fd_absent_days=?,fd_outgoing_time=?,fd_off_time=?,fd_is_norecord=?,fd_category_id=?,fd_category_name=?,"
					+ "fd_off_time_hour=?,fd_personal_leave_days=? "
					+ ",fd_work_time=?, "
					+ "fd_first_dept_name=?,fd_second_dept_name=?,fd_third_dept_name=?,fd_rest_time=?,"
					+ "fd_stand_work_time=?,fd_month_late_num=?,fd_month_forger_num=?,fd_month_late_min_num=?,fd_delayed_time=?,fd_Attend_result=?,"
					+ "fd_over_turn_time=?,fd_over_pay_time=?,fd_over_apply_time=?,fd_over_turn_apply_time=?,fd_over_pay_apply_time=? "
					+ "WHERE fd_id =?";
			updateSysAttendStatPreparedStatementThreadLocal.set(getConnection().prepareStatement(UPDATE_SQL));
			preparedStatement = updateSysAttendStatPreparedStatementThreadLocal.get();
		}
		return preparedStatement;
	}
	private PreparedStatement getSysAttendStatDeletePreparedStatement() throws SQLException {
		PreparedStatement preparedStatement = deleteSysAttendStatPreparedStatementThreadLocal.get();
		if(preparedStatement == null || preparedStatement.isClosed()){
			String UPDATE_SQL = "delete from sys_attend_stat WHERE fd_id =?";
			deleteSysAttendStatPreparedStatementThreadLocal.set(getConnection().prepareStatement(UPDATE_SQL));
			preparedStatement = deleteSysAttendStatPreparedStatementThreadLocal.get();
		}
		return preparedStatement;
	}

	private PreparedStatement getSysAttendDetailStatDeletePreparedStatement() throws SQLException {
		PreparedStatement preparedStatement = deleteSysAttendDetailStatPreparedStatementThreadLocal.get();
		if(preparedStatement == null || preparedStatement.isClosed())
		{
			String UPDATE_SQL = "delete from sys_attend_stat_detail  WHERE fd_id =?";
			deleteSysAttendDetailStatPreparedStatementThreadLocal.set(getConnection().prepareStatement(UPDATE_SQL));
			preparedStatement = deleteSysAttendDetailStatPreparedStatementThreadLocal.get();
		}
		return preparedStatement;
	}

	private PreparedStatement getSysAttendDetailStatUpdatePreparedStatement() throws SQLException {
		PreparedStatement preparedStatement = updateSysAttendDetailStatPreparedStatementThreadLocal.get();
		if(preparedStatement == null || preparedStatement.isClosed())
		{
			String UPDATE_SQL = "UPDATE sys_attend_stat_detail set fd_sign_time=?,doc_status=?,fd_outside=?,fd_state=?,fd_sign_time2=?,"
					+ "doc_status2=?,fd_outside2=?,fd_state2=?,fd_sign_time3=?,doc_status3=?,fd_outside3=?,fd_state3=?,"
					+ "fd_sign_time4=?,doc_status4=?,fd_outside4=?,fd_state4=?,fd_total_time=?,fd_category_id=?,fd_over_time=?,"
					+ "fd_date_type=?,doc_creator_hid=?,fd_trip_days=?,fd_off_days=?,fd_outgoing_time=?,"
					+ "fd_first_dept_name=?,fd_second_dept_name=?,fd_third_dept_name=?,fd_rest_time=?,"
					+ "fd_stand_work_time=?,fd_month_late_num=?,fd_month_forger_num=?,fd_month_late_min_num=?,fd_delayed_time=?  "
				+ " WHERE fd_id =?";
			updateSysAttendDetailStatPreparedStatementThreadLocal.set(getConnection().prepareStatement(UPDATE_SQL));
			preparedStatement = updateSysAttendDetailStatPreparedStatementThreadLocal.get();
		}
		return preparedStatement;
	}

	private PreparedStatement getSysAttendStatInsertPreparedStatement() throws SQLException {
		PreparedStatement preparedStatement = insertSysAttendStatPreparedStatementThreadLocal.get();
		if(preparedStatement == null || preparedStatement.isClosed())
		{
			String INSERT_SQL = "INSERT INTO sys_attend_stat(fd_id,fd_date,fd_total_time,doc_create_time,fd_late_time,fd_left_time,fd_status,"
							+ "fd_outside,fd_category_id,fd_category_name,doc_creator_id,fd_late,fd_left,fd_missed,fd_absent,fd_missed_count,"
							+ "fd_outside_count,fd_late_count,fd_left_count,fd_trip,fd_off,fd_trip_days,fd_off_days,fd_over_time,fd_date_type,"
							+ "fd_missed_exc_count,fd_late_exc_count,fd_left_exc_count,fd_off_count_detail,doc_creator_hid,fd_absent_days,"
							+ "fd_outgoing_time,fd_off_time,fd_is_norecord,fd_off_time_hour,auth_area_id,fd_work_time,fd_personal_leave_days,"
							+ "fd_first_dept_name,fd_second_dept_name,fd_third_dept_name,fd_rest_time,"
							+ "fd_stand_work_time,fd_month_late_num,fd_month_forger_num,fd_month_late_min_num,fd_delayed_time,fd_Attend_result,fd_over_turn_time,fd_over_pay_time,fd_over_apply_time,fd_over_turn_apply_time,fd_over_pay_apply_time) "
							+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			insertSysAttendStatPreparedStatementThreadLocal.set(getConnection().prepareStatement(INSERT_SQL));
			preparedStatement = insertSysAttendStatPreparedStatementThreadLocal.get();
		}
		return preparedStatement;
	}

	private PreparedStatement getSysAttendDetailStatInsertPreparedStatement() throws SQLException {
		PreparedStatement preparedStatement = insertSysAttendDetailStatPreparedStatementThreadLocal.get();
		if(preparedStatement == null || preparedStatement.isClosed())
		{
			String INSERT_SQL = "INSERT INTO sys_attend_stat_detail(fd_id,fd_sign_time,doc_status,fd_outside,fd_state,fd_sign_time2,doc_status2,"
					+ "fd_outside2,fd_state2,fd_sign_time3,doc_status3,fd_outside3,fd_state3,fd_sign_time4,doc_status4,"
					+ "fd_outside4,fd_state4,fd_date,fd_total_time,doc_create_time,fd_category_id,doc_creator_id,fd_over_time,"
					+ "fd_date_type,doc_creator_hid,fd_trip_days,fd_off_days,fd_outgoing_time,"
					+ "fd_first_dept_name,fd_second_dept_name,fd_third_dept_name,fd_rest_time,"
					+ "fd_stand_work_time,fd_month_late_num,fd_month_forger_num,fd_month_late_min_num,fd_delayed_time) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			insertSysAttendDetailStatPreparedStatementThreadLocal.set(getConnection().prepareStatement(INSERT_SQL));
			preparedStatement = insertSysAttendDetailStatPreparedStatementThreadLocal.get();
		}
		return preparedStatement;
	}

	/**
	 * 释放数据库连接资源
	 */
	private void release() {
		countLock.readLock().lock();
		Integer count = countThreadLocal.get();
		count -= 1;
		if(count <= 0)
		{
			try {
				try {
					JdbcUtils.closeStatement(searchSysAttendDetailStatPreparedStatementThreadLocal.get());
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					logger.error("SysAttendStatJob:close ERROR..1."+e.getMessage());
				} finally {
					searchSysAttendDetailStatPreparedStatementThreadLocal.remove();
					searchSysAttendDetailStatPreparedStatementThreadLocal.set(null);
				}
				try {
					JdbcUtils.closeStatement(searchSysAttendMainPreparedStatementThreadLocal.get());
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("SysAttendStatJob:close ERROR..2."+e.getMessage());
				} finally {
					searchSysAttendMainPreparedStatementThreadLocal.remove();
					searchSysAttendMainPreparedStatementThreadLocal.set(null);
				}
				try {
					JdbcUtils.closeStatement(insertSysAttendStatPreparedStatementThreadLocal.get());
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("SysAttendStatJob:close ERROR..3."+e.getMessage());
				} finally {
					insertSysAttendStatPreparedStatementThreadLocal.remove();
					insertSysAttendStatPreparedStatementThreadLocal.set(null);
				}
				try {
					JdbcUtils.closeStatement(insertSysAttendDetailStatPreparedStatementThreadLocal.get());
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("SysAttendStatJob:close ERROR..4."+e.getMessage());
				} finally {
					insertSysAttendDetailStatPreparedStatementThreadLocal.remove();
					insertSysAttendDetailStatPreparedStatementThreadLocal.set(null);
				}
				try {
					JdbcUtils.closeStatement(updateSysAttendStatPreparedStatementThreadLocal.get());
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("SysAttendStatJob:close ERROR..5."+e.getMessage());
				} finally {
					updateSysAttendStatPreparedStatementThreadLocal.remove();
					updateSysAttendStatPreparedStatementThreadLocal.set(null);
				}
				try {
					JdbcUtils.closeStatement(deleteSysAttendStatPreparedStatementThreadLocal.get());
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("SysAttendStatJob:close ERROR..5."+e.getMessage());
				} finally {
					deleteSysAttendStatPreparedStatementThreadLocal.remove();
					deleteSysAttendStatPreparedStatementThreadLocal.set(null);
				}
				try {
					JdbcUtils.closeStatement(updateSysAttendDetailStatPreparedStatementThreadLocal.get());
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("SysAttendStatJob:close ERROR..6."+e.getMessage());
				} finally {
					updateSysAttendDetailStatPreparedStatementThreadLocal.remove();
					updateSysAttendDetailStatPreparedStatementThreadLocal.set(null);
				}
				try {
					JdbcUtils.closeStatement(deleteSysAttendDetailStatPreparedStatementThreadLocal.get());
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("SysAttendStatJob:close ERROR..5."+e.getMessage());
				} finally {
					deleteSysAttendDetailStatPreparedStatementThreadLocal.remove();
					deleteSysAttendDetailStatPreparedStatementThreadLocal.set(null);
				}
				try {
					JdbcUtils.closeStatement(searchSysAttendStatPreparedStatementThreadLocal.get());
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("SysAttendStatJob:close ERROR..7."+e.getMessage());
				} finally {
					searchSysAttendStatPreparedStatementThreadLocal.remove();
					searchSysAttendStatPreparedStatementThreadLocal.set(null);
				}
			} catch(Exception e){
				logger.error("SysAttendStatJob:close ERROR..Over."+e.getMessage());
			}finally {
				try {
					JdbcUtils.closeConnection(connThreadLocal.get());
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("SysAttendStatJob:close ERROR..7."+e.getMessage());
				} finally {
					connThreadLocal.remove();
					connThreadLocal.set(null);
				}
				// TODO: handle finally clause
				this.sysAttendCategoryService.releaseThreadLocalConfig();
			}
		}
		countLock.readLock().unlock();
	}
}