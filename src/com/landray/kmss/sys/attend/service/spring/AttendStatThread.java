package com.landray.kmss.sys.attend.service.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.sql.DataSource;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.attend.service.*;
import com.landray.kmss.util.HQLUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendRestatLog;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

/**
 * 考勤重新统计
 * 
 * @author linxiuxian
 *
 */
public class AttendStatThread implements Runnable {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AttendStatThread.class);

	private ISysAttendStatJobService sysAttendStatJobService;
	private ISysAttendMainJobService sysAttendMainJobService;
	private ISysAttendStatMonthJobService sysAttendStatMonthJobService;
	private ISysAttendBusinessService sysAttendBusinessService;

	private ISysAttendSynDingService sysAttendSynDingService;

	public ISysAttendSynDingService getSysAttendSynDingService() {
		if(sysAttendSynDingService ==null){
			sysAttendSynDingService= (ISysAttendSynDingService) SpringBeanUtil.getBean("sysAttendSynDingService");
		}
		return sysAttendSynDingService;
	}

 	private ISysAttendListenerCommonService sysAttendListenerCommonService;

	public ISysAttendListenerCommonService getSysAttendListenerCommonService() {
		if (sysAttendListenerCommonService == null) {
			sysAttendListenerCommonService = (ISysAttendListenerCommonService) SpringBeanUtil
					.getBean("sysAttendListenerCommonService");
		}
		return sysAttendListenerCommonService;
	}

	public ISysAttendBusinessService getSysAttendBusinessService() {
		if (sysAttendBusinessService == null) {
			sysAttendBusinessService = (ISysAttendBusinessService) SpringBeanUtil
					.getBean("sysAttendBusinessService");
		}
		return sysAttendBusinessService;
	}
	/**
	 * 统计日志服务
	 */
	private ISysAttendRestatLogService sysAttendRestatLogService;
	public ISysAttendRestatLogService getSysAttendRestatLogService() {
		if (sysAttendRestatLogService == null) {
			sysAttendRestatLogService = (ISysAttendRestatLogService) SpringBeanUtil.getBean("sysAttendRestatLogService");
		}
		return sysAttendRestatLogService;
	}
	/**
	 * 用户集合
	 */
	private List<String> orgList = new ArrayList<String>();
	/**
	 * 统计日期集合
	 */
	private List<Date> dateList = new ArrayList<Date>();
	/**
	 * 操作类型
	 */
	private String fdMethod = null;
	/**
	 * 是否重新生成缺卡数据（手工重新统计时生效）
	 */
	private String fdIsCalMissed = null;
	/**
	 * 日志ID
	 */
	private String logId;
	/**
	 * 操作类型：
	 * stat 根据有效考勤统计，create,重新生成有效考勤记录
	 */
	private String fdOperateType = "stat";


	public ISysAttendStatJobService getSysAttendStatJobService() {
		if (sysAttendStatJobService == null) {
			sysAttendStatJobService = (ISysAttendStatJobService) SpringBeanUtil
					.getBean("sysAttendStatJobService");
		}
		return sysAttendStatJobService;
	}
	private ISysOrgElementService sysOrgElementService;

	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}
	public ISysAttendMainJobService getSysAttendMainJobService() {
		if (sysAttendMainJobService == null) {
			sysAttendMainJobService = (ISysAttendMainJobService) SpringBeanUtil
					.getBean("sysAttendMainJobService");
		}
		return sysAttendMainJobService;
	}

	public ISysAttendStatMonthJobService getSysAttendStatMonthJobService() {
		if (sysAttendStatMonthJobService == null) {
			sysAttendStatMonthJobService = (ISysAttendStatMonthJobService) SpringBeanUtil
					.getBean("sysAttendStatMonthJobService");
		}
		return sysAttendStatMonthJobService;
	}

	@Override
	public void run() {
		logger.debug("attendThread restat start...");
		boolean isException =false;
		SysAttendRestatLog restatLog =null;
		int fdStatus =0;
		try {
			if(StringUtil.isNotNull(logId)) {
				//重新生成有效考勤记录
				restatLog = (SysAttendRestatLog) getSysAttendRestatLogService().findByPrimaryKey(logId);
			}
			if ("create".equals(fdOperateType)) {
				TransactionStatus status = null;
				try {
					//重新生成有效考勤记录
					status = TransactionUtils.beginNewTransaction();
					logger.warn("重新生成有效考勤-清除有效记录-事务开始");
					for (Date date : dateList) {
						//删除对应日期、人 的有效考勤记录。重新生成
						getSysAttendListenerCommonService().deleteMainBatch(orgList, date);
					}
				} catch (Exception e) {
					isException = true;
					fdStatus =2;
				} finally {
					if (isException && status != null) {
						TransactionUtils.getTransactionManager().rollback(status);
					} else if (status != null) {
						fdStatus =1;
						TransactionUtils.getTransactionManager().commit(status);
					}
					logger.warn("重新生成有效考勤-清除有效记录-事务结束");
					isException = false;
				}
				if (isException) {
					fdStatus =2;
					throw new RuntimeException("重新生成-删除有效考勤异常");
				}
				try {
					status = TransactionUtils.beginNewTransaction();
					getSysAttendSynDingService().recalMergeClockAllStatus(dateList, orgList);
				} catch (Exception e) {
					fdStatus =2;
					isException = true;
					//e.printStackTrace();
					logger.error("重新生成有效考勤-生成考勤：dateList：" + dateList + ";orgList:"
							+ orgList, e);
				} finally {
					if (isException && status != null) {
						TransactionUtils.getTransactionManager().rollback(status);
					} else if (status != null) {
						fdStatus =1;
						TransactionUtils.getTransactionManager().commit(status);
					}
					logger.warn("重新生成有效考勤-生成考勤-事务结束");
					isException = false;
				}
				if (isException) {
					fdStatus =2;
					throw new RuntimeException("重新生成异常");
				}
				try {
					//重新生成有效考勤记录
					status = TransactionUtils.beginNewTransaction();
					logger.warn("重新生成有效考勤-流程生成考勤-事务开始");
					List<Integer> fdTypes = new ArrayList<Integer>();
					fdTypes.add(4);
					fdTypes.add(5);
					fdTypes.add(7);
					List<List> groupLists = new ArrayList<List>();
					// 用户组分割
					int maxCount = 100;
					if (orgList.size() <= maxCount) {
						groupLists.add(orgList);
					} else {
						groupLists = AttendUtil.splitList(orgList, maxCount);
					}
					//重新生成，删除未处理的缺卡的待办通知。然后删除有效考勤
					for (int i = 0; i < groupLists.size(); i++) {
						for (Date date : dateList) {
							Date nextDate =AttendUtil.getEndDate(date, 1);
							//拆分orgList
							// 出差/请假/外出记录
							List<SysAttendBusiness> busList = this.getSysAttendBusinessService().findBussList(groupLists.get(i), date, nextDate, fdTypes);
							if (CollectionUtils.isNotEmpty(busList)) {
								List startDateTime = new ArrayList<Date>(busList.size());
								List endDateTime = new ArrayList<Date>(busList.size());
								for (SysAttendBusiness business : busList) {
									startDateTime.add(business.getFdBusStartTime());
									endDateTime.add(business.getFdBusEndTime());
								}
								startDateTime.sort(new Comparator<Date>(){
									@Override
									public int compare(Date d1,Date d2){
										return (int) (d1.getTime()-d2.getTime());
										
									}
								});
								endDateTime.sort(new Comparator<Date>(){
									@Override
									public int compare(Date d1,Date d2){
										return (int) (d1.getTime()-d2.getTime());
										
									}
								});
								busList.sort(new Comparator<SysAttendBusiness>(){
									@Override
									public int compare(SysAttendBusiness d1,SysAttendBusiness d2){
										return (int) (d1.getFdBusStartTime().getTime()-d2.getFdBusStartTime().getTime());
										
									}
								});
								for (int j=0;j<busList.size();j++) {
									for (int k=j;k<busList.size()-1;k++) {
										if((busList.get(j).getFdBusType()==1||busList.get(j).getFdBusType()==13)&&(busList.get(k+1).getFdBusType()==1||busList.get(k+1).getFdBusType()==13)&&busList.get(j).getFdBusEndTime().getTime()<=busList.get(k+1).getFdBusStartTime().getTime()){
											busList.get(j).setFdBusEndTime(busList.get(k+1).getFdBusEndTime());
										}
									}
								}

								busList.sort(new Comparator<SysAttendBusiness>(){
									@Override
									public int compare(SysAttendBusiness d1,SysAttendBusiness d2){
										return (int) (d1.getFdBusEndTime().getTime()-d2.getFdBusEndTime().getTime());
										
									}
								});
								for (int j=busList.size()-1;j>=0;j--) {
									for (int k=busList.size()-1;k>0;k--) {
										if((busList.get(j).getFdBusType()==1||busList.get(j).getFdBusType()==13)&&(busList.get(k-1).getFdBusType()==1||busList.get(k-1).getFdBusType()==13)&&busList.get(j).getFdBusStartTime().getTime()<=busList.get(k-1).getFdBusEndTime().getTime()){
											busList.get(j).setFdBusStartTime(busList.get(k-1).getFdBusStartTime());
										}
									}
								}
								//根据流程（流程分类）来生成对应的有效考勤记录
								for (SysAttendBusiness business : busList) {
									if (Integer.valueOf(5).equals(business.getFdType())) {
										getSysAttendListenerCommonService().updateSysAttendMainByLeaveBis(business,date,nextDate);
									} else if (Integer.valueOf(7).equals(business.getFdType())) {
										getSysAttendListenerCommonService().updateSysAttendMainByOutgoing(business);
									} else if (Integer.valueOf(4).equals(business.getFdType())) {
										getSysAttendListenerCommonService().updateSysAttendMainByBusiness(business,date,nextDate);
									}
								}
							}
						}
					} 
					TransactionUtils.getTransactionManager().commit(status);
				} catch (Exception e) {
					isException = true;
					//e.printStackTrace();
					logger.error("重新生成有效考勤-流程生成考勤异常：dateList：" + dateList + ";orgList:"
							+ orgList, e);
					fdStatus =2;
				} finally {
					List<List> groupLists = new ArrayList<List>();
					// 用户组分割
					int maxCount = 100;
					if (orgList.size() <= maxCount) {
						groupLists.add(orgList);
					} else {
						groupLists = AttendUtil.splitList(orgList, maxCount);
					}
					
					if (isException && status != null) {
						TransactionUtils.getTransactionManager().rollback(status);
					} else if (status != null) {
						fdStatus =1;
					}
					logger.warn("重新生成有效考勤-流程生成考勤-事务结束");
					isException = false;
				}
			}
			if (isException) {
				fdStatus =2;
				throw new RuntimeException("重新生成流程数据异常");
			}
			TransactionStatus statusStat = null;
			boolean statMonth =false;
			Set<Long> monthsSet = new HashSet<Long>();
			try {
				logger.warn("重新统计事务开始");
				for (Date date : dateList) {
					long month = AttendUtil.getMonth(date, 0).getTime();
					monthsSet.add(month);
				}
				statusStat = TransactionUtils.beginNewTransaction();
				if ("restat".equals(fdMethod)) {
					//创建一个map存储人员本月的累计值
					//格式（key：人员id-value：（key:月累计属性-value值））
					Map<String, JSONObject> monthDataMap=new HashMap<>();
					if(CollectionUtils.isNotEmpty(dateList)){
						 monthDataMap=initMonthDataMap(orgList, dateList.get(0));
					}
					// 重新生成统计月数据
					for (Long month : monthsSet) {
						// 删除月统计数据
						this.getSysAttendStatMonthJobService().deletStat(null, new Date(month.longValue()), orgList);
					}
					for (Date date : dateList) {
						
						//删除每日统计
						this.getSysAttendStatJobService().deletStat(null, date, orgList);
						fdIsCalMissed = date.before(AttendUtil.getDate(new Date(), 0)) ? fdIsCalMissed : "false";
						for (Entry<String, JSONObject> stringJSONObjectEntry : monthDataMap.entrySet()) {
							stringJSONObjectEntry.getValue().put("tempRecountTimesNumber", stringJSONObjectEntry.getValue().getIntValue("fdRecountTimesNumber"));
							stringJSONObjectEntry.getValue().put("tempMonthLateNum", stringJSONObjectEntry.getValue().getIntValue("fdMonthLateNum"));
							stringJSONObjectEntry.getValue().put("tempMonthLateMinNum", stringJSONObjectEntry.getValue().getIntValue("fdMonthLateMinNum"));
						}
						//生成每日统计
						this.getSysAttendMainJobService().stat(date, null, "true".equals(fdIsCalMissed), false, orgList,monthDataMap);
						for (Entry<String, JSONObject> stringJSONObjectEntry : monthDataMap.entrySet()) {
							stringJSONObjectEntry.getValue().put("fdPreRecountTimesNumber", stringJSONObjectEntry.getValue().getIntValue("tempRecountTimesNumber"));
							stringJSONObjectEntry.getValue().put("fdPreMonthLateNum", stringJSONObjectEntry.getValue().getIntValue("tempMonthLateNum"));
							stringJSONObjectEntry.getValue().put("fdPreMonthLateMinNum", stringJSONObjectEntry.getValue().getIntValue("tempMonthLateMinNum"));
						}
					}
					statMonth =true;
				} else {
					getSysAttendStatJobService().stat(orgList, dateList);
				}
				TransactionUtils.getTransactionManager().commit(statusStat);
				fdStatus =1;
			} catch (Exception e) {
				isException = true;
				//e.printStackTrace();
				fdStatus =2;
				logger.error("重新统计-重新统计异常：dateList：", e);
			} finally {
				
				if (isException && statusStat != null) {
					TransactionUtils.getTransactionManager().rollback(statusStat);
				}
				logger.warn("重新统计-事务结束");
			}
			if(statMonth) {
				//事务统计以后 重新计算每月统计
				for (Long month : monthsSet) {
					getSysAttendStatMonthJobService().stat(null, new Date(month.longValue()), orgList);
				}
			}
		}catch (Exception e){
			fdStatus =2;
//			e.printStackTrace();
			logger.error("attendThread restat error..."+e.getMessage());
		}finally {
			for (int i = 0; i < orgList.size(); i++) {
				for (Date date : dateList) {
			 HQLInfo hqlInfo = new HQLInfo();
                ;
                String whereBlock = " docCreator=:person and docCreateTime>:statDate and docCreateTime<:endDate and docStatus=0";
                try {
					hqlInfo.setParameter("person",getSysOrgElementService().findByPrimaryKey(orgList.get(i)));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                hqlInfo.setParameter("statDate", date);
                hqlInfo.setParameter("endDate", AttendUtil.addDate(date, 1));
    			hqlInfo.setWhereBlock(whereBlock );
    			List<SysAttendMain> sysAttendMainList = null;
				try {
					sysAttendMainList = getSysAttendMainService().findList(hqlInfo);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			boolean flag = false;
    			boolean flag1 = false;
    			for(SysAttendMain sysAttendMain : sysAttendMainList){
	    	            SysAttendCategory category = null;
						try {
							category = CategoryUtil.getCategoryById(sysAttendMain.getFdHisCategory().getFdId());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    			if(category.getFdShiftType()==4){
    				if(sysAttendMain.getFdStatus()==0 && sysAttendMain.getFdWorkType()==0){
    					flag=true;
    					
    				}
    				if(sysAttendMain.getFdStatus()==0 && sysAttendMain.getFdWorkType()==1){
    					flag1=true;
    					
    				}
		    			}
    			}
    			for(SysAttendMain sysAttendMain : sysAttendMainList){
    				if(flag&&!flag1){
    					if(sysAttendMain.getFdStatus()==0 && sysAttendMain.getFdWorkType()==0){
    					try {
						PreparedStatement update = null;
						Connection conn = null;
						DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
						conn = dataSource.getConnection();
						conn.setAutoCommit(false);
						update = conn.prepareStatement(
								"update sys_attend_main set doc_status=? "
										+ "where fd_id =?");
						update.setInt(1, 1);
						update.setString(2, sysAttendMain.getFdId());
						update.execute();
						conn.commit();
//						getSysAttendMainService().update(sysAttendMain);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}}
//    				if(flag==2)
//    					try {
//						PreparedStatement update = null;
//						Connection conn = null;
//						DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
//						conn = dataSource.getConnection();
//						conn.setAutoCommit(false);
//						update = conn.prepareStatement(
//								"update sys_attend_main set doc_status=? "
//										+ "where fd_id =?");
//						update.setInt(1, 1);
//						update.setString(2, sysAttendMain.getFdId());
//						update.execute();
//						conn.commit();
////						getSysAttendMainService().update(sysAttendMain);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
    				if(sysAttendMain.getFdStatus()==1 && sysAttendMain.getFdWorkType()==1){
    					sysAttendMain.setFdWorkType(0);
    					try {
    						PreparedStatement update = null;
    						Connection conn = null;
    						DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
    						conn = dataSource.getConnection();
    						conn.setAutoCommit(false);
    						update = conn.prepareStatement(
    								"update sys_attend_main set fd_work_type=? "
    										+ "where fd_id =?");
    						update.setInt(1, 0);
    						update.setString(2, sysAttendMain.getFdId());
    						update.execute();
    						conn.commit();
//							getSysAttendMainService().update(sysAttendMain);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//    					try {
//							getSysAttendMainService().getBaseDao().flushHibernateSession();
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
    				}
    			}}
				}}
			//更新重新统计状态
			if(restatLog !=null){
				restatLog.setFdStatus(fdStatus);
				restatLog.setDocAlterTime(new Date());
				try {
					getSysAttendRestatLogService().update(restatLog);
				} catch (Exception e) {
                    logger.error("attendThread restat finish...",e);
				}
			}
		}
		logger.debug("attendThread restat finish...");

	}

	public List<String> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<String> orgList) {
		this.orgList = orgList;
	}

	public List<Date> getDateList() {
		return dateList;
	}

	public void setDateList(List<Date> dateList) {
		this.dateList = dateList;
	}

	public String getFdMethod() {
		return fdMethod;
	}

	public void setFdMethod(String fdMethod) {
		this.fdMethod = fdMethod;
	}

	public String getFdIsCalMissed() {
		return fdIsCalMissed;
	}

	public void setFdIsCalMissed(String fdIsCalMissed) {
		this.fdIsCalMissed = fdIsCalMissed;
	}

	public String getFdOperateType() {
		return fdOperateType;
	}

	public void setFdOperateType(String fdOperateType) {
		this.fdOperateType = fdOperateType;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}
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
		int month1 = c.get(Calendar.MONTH);
		c.add(Calendar.DATE, -1);
		int month2 = c.get(Calendar.MONTH);
		boolean flag = true;
		if(month1!=month2){
			c.add(Calendar.DATE, 1);
			flag=false;
		}
		Date newDate=c.getTime();
		String dateStr=dateFormat.format(newDate);
		String insql= HQLUtil.buildLogicIN("doc_creator_id", personIds);
		String sql="SELECT doc_creator_id,fd_month_late_num,fd_month_forger_num,fd_month_late_min_num,fd_delayed_time "
				+ "FROM sys_attend_stat_detail WHERE "+insql+" and fd_date like '"+dateStr+"%'";
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
				if(flag){
				data.put("fdMonthLateNum", fdMonthLateNum);
				data.put("fdMonthForgerNum", fdMonthForgerNum);
				data.put("fdMonthLateMinNum", fdMonthLateMinNum);
				data.put("fdOverTimeWithoutDeduct", fdOverTimeWithoutDeduct);
				}
				else{
					data.put("fdMonthLateNum", 0);
					data.put("fdMonthForgerNum", 0);
					data.put("fdMonthLateMinNum", 0);
					data.put("fdOverTimeWithoutDeduct", 0);
				}

				//add by liuyang at 20230218，记录月数据
				monthDataMap.put(personId,data);
			}
		}
		for (Map.Entry<String, Long> stringLongEntry : alreadyPatchNumberMap.entrySet()) {
			JSONObject jsonObject = monthDataMap.get(stringLongEntry.getKey());
			if(jsonObject==null){
				JSONObject data=new JSONObject();
				data.put("fdMonthAlreadyPatchNumber",stringLongEntry.getValue());
				//重新统计次数缓存
				data.put("isRecountTimes", dayNum == 1);
				data.put("fdRecountTimesNumber",0);
				data.put("fdPreRecountTimesNumber",0);

				if(monthCardDataMap.get(stringLongEntry.getKey())!=null){
					data.put("fdMonthCardDataMap",monthCardDataMap.get(stringLongEntry.getKey()));
				}
				monthDataMap.put(stringLongEntry.getKey(),data);
			}else{
				jsonObject.put("fdMonthAlreadyPatchNumber",stringLongEntry.getValue());
				//重新统计次数缓存
				jsonObject.put("isRecountTimes", dayNum == 1 );
				jsonObject.put("fdRecountTimesNumber",0);
				jsonObject.put("fdPreRecountTimesNumber",0);

				if(monthCardDataMap.get(stringLongEntry.getKey())!=null){
					jsonObject.put("fdMonthCardDataMap",monthCardDataMap.get(stringLongEntry.getKey()));
				}
			}
		}
		return monthDataMap;
	}
	private Map<String,Long> alreadyPatchNumber(List<String> personIds,Date date,Map<String, List<String>> monthCardDataMap) throws Exception {
		Map<String,Long> integerMap = new HashMap<>();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(" count(*) , sysAttendMain.docCreator.fdId ");
		StringBuffer whereBlock = new StringBuffer("1=1");
		Date startTime = AttendUtil.getMonth(date, 0);
		Date endTime = AttendUtil.getMonth(date, 1);
		whereBlock.append(
				" and sysAttendMain.docCreateTime>=:startTime and sysAttendMain.docCreateTime<:endTime");
		hqlInfo.setParameter("startTime", startTime);
		hqlInfo.setParameter("endTime", endTime);
		whereBlock.append(" and sysAttendMain.docCreator.fdId in (:docCreatorId) and (sysAttendMain.fdState=1 or sysAttendMain.fdState=2)");
		hqlInfo.setParameter("docCreatorId",personIds);
		whereBlock.append(" and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
		hqlInfo.setWhereBlock(whereBlock.toString()+" group by sysAttendMain.docCreator.fdId");

		String inPersonIdSql= HQLUtil.buildLogicIN("main.doc_creator_id", personIds);
		String exitSql = "select  count(exc.fd_id) as count2, LEFT(exc.fd_attend_time,10) as attend_time ,main.doc_creator_id  From sys_attend_main_exc exc,sys_attend_main main where main.fd_id = exc.fd_attend_id and "+inPersonIdSql+" and main.doc_Create_Time BETWEEN :startTime  and :endTime and  exc.doc_status = '30' and  (exc.fd_desc = '忘带工牌' or exc.fd_desc = '工牌丢失')   GROUP BY  attend_time, main.doc_creator_id having count2 = 2 ";
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
	private ISysAttendMainService sysAttendMainService;
	private ISysAttendMainService getSysAttendMainService() {
		if (sysAttendMainService == null) {
			sysAttendMainService = (ISysAttendMainService) SpringBeanUtil.getBean("sysAttendMainService");
		}
		return sysAttendMainService;
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
	private ISysAttendStatMonthService sysAttendStatMonthService;
	public ISysAttendStatMonthService getSysAttendStatMonthService() {
		if(sysAttendStatMonthService==null){
			sysAttendStatMonthService=(ISysAttendStatMonthService)SpringBeanUtil.getBean("sysAttendStatMonthService");
		}
		return sysAttendStatMonthService;
	}
}
