package com.landray.kmss.sys.attend.plugin;

import com.google.common.collect.Lists;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.interfaces.IEventCallBack;
import com.landray.kmss.framework.spring.event.transaction.EventOfTransactionCommit;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendMainExc;
import com.landray.kmss.sys.attend.service.ISysAttendBusinessService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendHisCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendMainExcService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.ISysAttendStatJobService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.persistence.AccessManager;
import com.landray.kmss.sys.metadata.interfaces.ISysMetadataParser;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.util.SysTimeLeaveTimeDto;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 签到服务、流程结束监听后的公共类
 *
 * @author 王京
 */
public class SysAttendListenerCommon {
	/**
	 * 流程驳回的标识
	 */
	protected String PROCESS_FLAG_RETURN_KEY="SYSATTEND_FLOW_RETURN_FLAG";
	/**
	 * 流程正常提交的标识
	 */
	protected String PROCESS_FLAG_KEY="SYSATTEND_FLOW_FLAG";
	/**
	 * 流程正常提交的值
	 */
	protected String PROCESS_FLAG_RUN_VALUE="RUN";
	/**
	 * 流程错误提交的值
	 */
	protected String PROCESS_FLAG_ERROR_VALUE="ERROR";


	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendListenerCommon.class);
	private ISysMetadataParser sysMetadataParser;

	public ISysMetadataParser getSysMetadataParser() {
		if(sysMetadataParser ==null){
			sysMetadataParser = (ISysMetadataParser) SpringBeanUtil.getBean("sysMetadataParser");
		}
		return sysMetadataParser;
	}
	private ISysOrgCoreService sysOrgCoreService =null;

	public ISysOrgCoreService getSysOrgCoreService() {
		if(sysOrgCoreService ==null){
			sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
					.getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	private ISysAttendMainExcService sysAttendMainExcService;

	protected ISysAttendMainExcService getSysAttendMainExcService() {
		if(sysAttendMainExcService ==null){
			sysAttendMainExcService = (ISysAttendMainExcService) SpringBeanUtil
					.getBean("sysAttendMainExcService");
		}
		return sysAttendMainExcService;
	}

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	protected ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if(sysNotifyMainCoreService ==null){
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil
					.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
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

	private ISysAttendBusinessService sysAttendBusinessService;

	protected ISysAttendBusinessService getSysAttendBusinessService() {
		if (sysAttendBusinessService == null) {
			sysAttendBusinessService = (ISysAttendBusinessService) SpringBeanUtil.getBean("sysAttendBusinessService");
		}
		return sysAttendBusinessService;
	}

	private ISysAttendStatJobService sysAttendStatJobService;
	protected ISysAttendStatJobService getSysAttendStatJobService() {
		if (sysAttendStatJobService == null) {
			sysAttendStatJobService = (ISysAttendStatJobService) SpringBeanUtil.getBean("sysAttendStatJobService");
		}
		return sysAttendStatJobService;
	}

	private ISysAttendHisCategoryService sysAttendHisCategoryService;
	protected ISysAttendHisCategoryService getSysAttendHisCategoryService(){
		if(sysAttendHisCategoryService ==null){
			sysAttendHisCategoryService = (ISysAttendHisCategoryService) SpringBeanUtil.getBean("sysAttendHisCategoryService");
		}
		return sysAttendHisCategoryService;
	}
	private AccessManager accessManager;

	public AccessManager getAccessManager() {
		if(accessManager ==null){
			accessManager = (AccessManager) SpringBeanUtil.getBean("accessManager");
		}
		return accessManager;
	}

	/**
	 * 验证用户所在的考勤组
	 * @param elements 组织
	 * @param date 日期
	 * @return
	 * @throws Exception 不存在直接抛运行异常
	 */
	protected void checkUserCategory(List<SysOrgElement> elements,Date date ) throws Exception {
		for (SysOrgElement element:elements) {
			String categoryId =getSysAttendCategoryService().getCategory(element,date);
			if(StringUtil.isNull(categoryId)){
				String tip = ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip5");
//				throw new RuntimeException(tip);
			}
		}
	}
	/**
	 * 验证用户所在的考勤组
	 * @param elements 组织
	 * @param date 日期
	 * @return
	 * @throws Exception 不存在直接抛运行异常
	 */
	protected boolean checkUserHaveCategory(List<SysOrgElement> elements,Date date ) throws Exception {
		for (SysOrgElement element:elements) {
			String categoryId =getSysAttendCategoryService().getCategory(element,date);
			if(StringUtil.isNull(categoryId)){

				return false;
			}
		}
		return true;
	}
	/**
	 * 获取组织所在的考勤组。
	 * 实时获取当前组织所在的考勤组
	 * @param element 组织
	 * @param date 日期
	 * @return
	 * @throws Exception
	 */
	protected SysAttendCategory getUserCategory(SysOrgElement element,Date date) throws Exception {
		SysAttendCategory category = getSysAttendCategoryService().getCategoryInfo(element, date, true);
		if(category ==null){
			throw new RuntimeException("未找到对应日期的考勤组");
		}
		return category;
	}

	/**
	 * 获取某人某个时间点应该打卡的时间点。
	 * @param category 考勤组
	 * @param startTime 开始时间
	 * @param org 人员
	 * @param needed 当天没有打卡配置则找过去时间
	 * @return 打卡时间点列表
	 * @throws Exception
	 */
    protected List<Map<String, Object>> getSignTimeList(
            SysAttendCategory category, Date startTime, SysOrgElement org,boolean needed) throws Exception {
        List<Map<String, Object>> signTimeList = new ArrayList<Map<String, Object>>();
        if (category == null || startTime == null) {
            return signTimeList;
        }

        Date date = AttendUtil.getDate(startTime, 0);
        if(needed) {
			signTimeList = getSysAttendCategoryService().getAttendSignTimes(category, date, org, true);
		}else{
			signTimeList = getSysAttendCategoryService().getAttendSignTimes(category, date, org);
		}
        return signTimeList;
    }

	/**
	 * 取某个时间段的签到记录
	 *
	 * @param signTimeList
	 * @return
	 * @throws Exception
	 */
	protected List<Map<String, Object>> filterSignTimeListOfOutgoing(boolean flag,
			List<Map<String, Object>> signTimeList,
			Date startTime,
			Date endTime,
			Date date) throws Exception {
		if (signTimeList != null && !signTimeList.isEmpty()
				&& startTime != null && endTime != null&& date != null) {
			for (Iterator it = signTimeList.iterator(); it.hasNext();) {
				Map<String, Object> map = (Map<String, Object>) it.next();
				Date signTime = (Date) map.get("signTime");
				Integer overTimeType=(Integer)map.get("overTimeType");
				signTime = AttendUtil.joinYMDandHMS(date, signTime);
				//如果是跨天，打卡时间加一天
				if(Integer.valueOf(2).equals(overTimeType)) {
					signTime = AttendUtil.addDate(signTime, 1);
				}
				//最早最晚的范围内
				Date workTime = sysAttendCategoryService.getTimeAreaDateOfDate(endTime, date, map);
				Date startWorkTime = sysAttendCategoryService.getTimeAreaDateOfDate(startTime, date, map);
				if(workTime ==null && startWorkTime ==null) {
					//不在这个排班的最早最晚范围内则剔除
					it.remove();
				}else {
					//相等返回0，大于返回1，小于返回-1.
					//标准打卡时间 比 流程开始时间 小
					//标准打卡时间 比 流程结束时间 大
					//打卡时间，在开始结束范围内 时间落在标准打卡时间的范围才更新。否则不更新有效考勤
					if (!flag&&(signTime.compareTo(startTime) < 0 || signTime.compareTo(endTime) > 0)) {
						it.remove();
					}
					if(flag&&signTime.compareTo(endTime)>0)map.put("signTime",endTime);
					if(flag&&signTime.compareTo(startTime) < 0)map.put("signTime",startTime);
				}

			}
		}
		return signTimeList;
	}

	/**
	 * 过滤班次信息
	 * @param signTimeList 班次列表
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param workTime
	 * @return
	 */
	protected List<Map<String, Object>> filterSignTimeList(
			List<Map<String, Object>> signTimeList,
			Date startTime,
			Date endTime,Date workTime) {
		if (signTimeList != null && !signTimeList.isEmpty()
				&& startTime != null && endTime != null) {
			Date startDate = AttendUtil.getDate(startTime, 0);
			boolean startTimeIsZeroDay = AttendUtil.isZeroDay(startTime);
			boolean endTimeIsZeroDay = AttendUtil.isZeroDay(endTime);
			//按天处理,则包含全段排班时间
			if(startTimeIsZeroDay && endTimeIsZeroDay){
				return signTimeList;
			}
			for (Iterator<Map<String, Object>> it = signTimeList.iterator(); it.hasNext();) {
				Map<String, Object> signTimeConfiguration = (Map<String, Object>) it.next();
				Date signTime = (Date) signTimeConfiguration.get("signTime");
				Integer workType = (Integer) signTimeConfiguration.get("fdWorkType");
				//判断跨天，增加1天 。如果传进来的参数是属于前1天。则用前1天的跨天加1
				signTime = this.overTimeTypeProcess(signTimeConfiguration, AttendUtil.joinYMDandHMS(workTime ==null?startDate:workTime, signTime));

				//开始时间非零点，则剔除小于上班时间
				if(!startTimeIsZeroDay && endTimeIsZeroDay){
					//上班的班次。开始时间小于
					if(workType==0){
						if (signTime.compareTo(startTime) < 0) {
							it.remove();
						}
					} else {
						//下班的班次，开始时间小于等于打卡时间，则剔除
						if (signTime.compareTo(startTime) <= 0) {
							it.remove();
						}
						//签到时间大于请假结束时间的也剔除
						else if (signTime.getTime() > endTime.getTime()) {
							it.remove();
						}
					}
				}
				//结束时间非零点，则剔除大于下班时间
				else if(startTimeIsZeroDay && !endTimeIsZeroDay){
					//跨天打卡时间，将会被剔除
					if(workType==0){
						//如果结束时间落在上班的开始时间。则剔除
						if (signTime.compareTo(endTime) >= 0) {
							it.remove();
						}
					}else {
						//打卡时间大于结束结束时间的则剔除
						if (signTime.compareTo(endTime) > 0) {
							it.remove();
						}
					}
				}
				//开始时间和结束时间非零点，剔除不在区间内
				else{
					if (signTime.compareTo(startTime) < 0
							|| signTime.compareTo(endTime) > 0) {
						//如果请假开始时间开始于昨天的晚班下班打卡时间内，则需要保留该时间配置
						if(!isLastSchedulingOfYesterday(startTime, startDate, signTimeConfiguration)) {
							it.remove();
						}else if(isLastSchedulingOfYesterday(startTime, startDate, signTimeConfiguration)
								&& isLastSchedulingOfYesterday(endTime, startDate, signTimeConfiguration)) {
							//如果请假开始时间和请假结束时间在昨天的晚班下班打卡时间内，则剔除
							it.remove();
						}
					}
				}
			}
		}
		return signTimeList;
	}

	/**
	 * 取某个时间段的签到记录
	 *
	 * @param signTimeList
	 * @return
	 */
	protected List<Map<String, Object>> filterSignTimeList(
			List<Map<String, Object>> signTimeList,
			Date startTime,
			Date endTime) {
		if (signTimeList != null && !signTimeList.isEmpty()
				&& startTime != null && endTime != null) {
			Date startDate = AttendUtil.getDate(startTime, 0);
			boolean startTimeIsZeroDay = AttendUtil.isZeroDay(startTime);
			boolean endTimeIsZeroDay = AttendUtil.isZeroDay(endTime);
			//按天处理,则包含全段排班时间
			if(startTimeIsZeroDay && endTimeIsZeroDay){
				return signTimeList;
			}
			for (Iterator<Map<String, Object>> it = signTimeList.iterator(); it.hasNext();) {
				Map<String, Object> signTimeConfiguration = (Map<String, Object>) it.next();
				Date signTime = (Date) signTimeConfiguration.get("signTime");
				Integer workType = (Integer) signTimeConfiguration.get("fdWorkType");
				//判断跨天，增加1天
				signTime = this.overTimeTypeProcess(signTimeConfiguration, AttendUtil.joinYMDandHMS(startDate, signTime));

				//开始时间非零点，则剔除小于上班时间
				if(!startTimeIsZeroDay && endTimeIsZeroDay){
					//上班的班次。开始时间小于
					if(workType==0){
						if (signTime.compareTo(startTime) < 0) {
							it.remove();
						}
					} else {
						//下班的班次，开始时间小于等于打卡时间，则剔除
						if (signTime.compareTo(startTime) <= 0) {
							it.remove();
						}
						/*//签到时间大于请假结束时间的也剔除
						else if (signTime.getTime() > endTime.getTime()) {
							it.remove();
						}*/
					}
				}
				//结束时间非零点，则剔除大于下班时间
				else if(startTimeIsZeroDay && !endTimeIsZeroDay){
					//跨天打卡时间，将会被剔除
					if(workType==0){
						//如果结束时间落在上班的开始时间。则剔除
						if (signTime.compareTo(endTime) >= 0) {
							it.remove();
						}
					}else {
						//打卡时间大于结束结束时间的则剔除
						if (signTime.compareTo(endTime) > 0) {
							it.remove();
						}
					}
				}
				//开始时间和结束时间非零点，剔除不在区间内
				else{
					if (signTime.compareTo(startTime) < 0
							|| signTime.compareTo(endTime) > 0) {
						//如果请假开始时间开始于昨天的晚班下班打卡时间内，则需要保留该时间配置
						if(!isLastSchedulingOfYesterday(startTime, startDate, signTimeConfiguration)) {
							it.remove();
						}else if(isLastSchedulingOfYesterday(startTime, startDate, signTimeConfiguration)
								&& isLastSchedulingOfYesterday(endTime, startDate, signTimeConfiguration)) {
							//如果请假开始时间和请假结束时间在昨天的晚班下班打卡时间内，则剔除
							it.remove();
						}
					}
				}
			}
		}
		return signTimeList;
	}
	protected List<Date> getDateList(Integer fdStatType, Date startTime,
									 Date endTime, Integer fdStartNoon, Integer fdEndNoon) {
		List<Date> dateList = new ArrayList<Date>();
		if (fdStatType == null) {// 兼容以前数据
			dateList = AttendUtil.getDateListByTime(startTime,
					endTime);
		} else if (fdStatType == 1) {// 按天
			dateList = AttendUtil.getDateListByDay(startTime,
					endTime);
		} else if (fdStatType == 2) {// 按半天
			dateList = AttendUtil.getDateListByHalfDay(startTime,
					endTime, fdStartNoon, fdEndNoon);
		} else if (fdStatType == 3) {// 按小时
			dateList = AttendUtil.getDateListByTime(startTime,
					endTime);
		}
		return dateList;
	}
	/**
	 * 判断当前结束是否为零点并且下一个结束时间大于下班时间
	 * @param endTime
	 * @param nextEndTime
	 * @return
	 */
	protected boolean isLastSchedulingOfCurrentday(Date startDate, Date endTime, Date nextEndTime, Map<String, Object> signTimeConfiguration){
		if(AttendUtil.isZeroDay(endTime)){
			Date signTime = AttendUtil.joinYMDandHMS(startDate, (Date) signTimeConfiguration.get("signTime"));
			signTime = overTimeTypeProcess(signTimeConfiguration, signTime);
			if(nextEndTime != null && nextEndTime.compareTo(signTime) < 0)
			{
				return false;
			}
		}
		return true;
	}
	/**
	 * 	跨天排班打卡处理
	 * @param signTimeConfiguration
	 * 	排班时间配置
	 * @param signTime
	 * 	该排班配置的打卡时间
	 * @return
	 */
	protected Date overTimeTypeProcess(Map<String, Object> signTimeConfiguration, Date signTime) {
		if(isOverTimeType(signTimeConfiguration))
		{
			signTime = AttendUtil.addDate(signTime, 1);
		}
		return signTime;
	}


	/**
	 * 判断当前时间是否属于昨天的晚班下班时间
	 * @param compareTime 需要判断的时间
	 * @param startDate
	 * @param signTimeConfiguration
	 * @return
	 */
	protected boolean isLastSchedulingOfYesterday(Date compareTime, Date startDate,
												Map<String, Object> signTimeConfiguration) {
		//特殊情况，如果出差申请开始时间位于前一天的末班下班打卡内
		if (!AttendUtil.isZeroDay(compareTime) && this.isOverTimeType(signTimeConfiguration)
				&& compareTime.compareTo(AttendUtil.joinYMDandHMS(startDate,
				(Date) signTimeConfiguration.get("signTime"))) <= 0) {
			return true;
		}
		return false;
	}
	/**
	 * 	判断是否为跨天排班打卡
	 * @param signTimeConfiguration
	 * @return
	 */
	protected boolean isOverTimeType(Map<String, Object> signTimeConfiguration) {
		Integer fdOverTimeType = signTimeConfiguration.get("overTimeType") == null ? 1
				: (Integer) signTimeConfiguration.get("overTimeType");
		return fdOverTimeType == 2;
	}

	/**
	 * 获取排班休息日的班次信息
	 * @param category 考勤组
	 * @param date 日期
	 * @param ele 人员
	 * @return
	 * @throws Exception
	 */
	protected List<Map<String, Object>> getAttendAreaRestSignTimes(SysAttendCategory category, Date date, SysOrgElement ele) throws Exception {
		for (int i = 0; i < 30; i++) {
			// 尝试获取最近一次的班次信息
			List<Map<String, Object>> list = getSysAttendCategoryService().getAttendSignTimes(category, AttendUtil.getDate(date, i), ele);
			if (!list.isEmpty()) {
				return list;
			}
		}
		return new ArrayList();
	}


	/**
	 * 是否休息日（包含节假日）
	 * @param date 日期
	 * @param category 考勤组
	 * @param org 人员
	 * @return
	 * @throws Exception
	 */
	protected boolean isRestDay(Date date, SysAttendCategory category, SysOrgElement org) throws Exception {
		List<SysAttendCategory> list = new ArrayList<SysAttendCategory>();
		list.add(category);
		com.alibaba.fastjson.JSONArray datas = getSysAttendCategoryService().filterAttendCategory(list, date, true, org);
		return datas == null || datas.isEmpty();
	}



	/**
	 * 获取有效考勤记录
	 * @param personId 人员
	 * @param fdStatus 考勤状态，如果为null 则不过滤
	 * @param nextForm 跨天考勤的开始时间
	 * @param nextTo 跨天考勤的结束时间
	 * @param startTime 正常考勤的开始时间
	 * @param endTime 正常考勤的结束时间
	 * @param startTimeIsZeroDay 是否只查考勤天的非跨天数据
	 * @return
	 * @throws Exception
	 */
	protected List<SysAttendMain> getSysAttendMainList(
			String personId,
			Integer fdStatus,
			Date nextForm,
			Date nextTo,
			Date startTime,
			Date endTime,
			boolean startTimeIsZeroDay
	) throws Exception {
		StringBuffer baseWhereBlock =new StringBuffer();
		baseWhereBlock.append(" sysAttendMain.docCreator.fdId=:personId ");
		if(fdStatus !=null) {
			baseWhereBlock.append(" and sysAttendMain.fdStatus=:fdStatus ");
		}
		baseWhereBlock.append(" and sysAttendMain.fdWorkType is not null ");
		baseWhereBlock.append(" and (sysAttendMain.fdWorkKey is not null or sysAttendMain.workTime is not null)");
		baseWhereBlock.append(" and (sysAttendMain.docStatus=:docStatus or sysAttendMain.docStatus is null) ");
		List<SysAttendMain> list =null;
		if(nextForm !=null && nextTo !=null) {
			StringBuffer whereBlockOne = new StringBuffer(baseWhereBlock);
			whereBlockOne.append("  and sysAttendMain.fdIsAcross=:fdIsAcross1 and sysAttendMain.docCreateTime>=:nextForm and sysAttendMain.docCreateTime<:nextTo ");
			//查询跨天的考勤记录
			HQLInfo hqlInfo = new HQLInfo();
			// 如果考勤记录是请假考勤
			if (fdStatus != null) {
				hqlInfo.setParameter("fdStatus", 0);
			}
			hqlInfo.setParameter("docStatus", 0);
			hqlInfo.setParameter("fdIsAcross1", true);
			hqlInfo.setParameter("personId", personId);
			hqlInfo.setParameter("nextForm", nextForm);
			hqlInfo.setParameter("nextTo", nextTo);
			hqlInfo.setWhereBlock(whereBlockOne.toString());
			hqlInfo.setOrderBy("sysAttendMain.docCreateTime asc");
			list = getSysAttendMainService().findList(hqlInfo);
		}
		List<SysAttendMain> listTwo =null;
		if(startTime !=null && endTime !=null) {
			//因为性能问题，Or的部分两次查询
			StringBuffer whereBlockTwo = new StringBuffer(baseWhereBlock);
			whereBlockTwo.append("   and sysAttendMain.docCreateTime>=:startTime and sysAttendMain.docCreateTime<:endTime ");
			HQLInfo hqlInfoTwo = new HQLInfo();
			//指定考勤数据类型 查询
			if (fdStatus != null) {
				hqlInfoTwo.setParameter("fdStatus", fdStatus);
			}
			hqlInfoTwo.setParameter("docStatus", 0);
			hqlInfoTwo.setParameter("personId", personId);
			hqlInfoTwo.setOrderBy("sysAttendMain.docCreateTime asc");
			if (startTimeIsZeroDay) {
				whereBlockTwo.append(" and (sysAttendMain.fdIsAcross is null or sysAttendMain.fdIsAcross=:fdIsAcross0) ");
				hqlInfoTwo.setParameter("fdIsAcross0", Boolean.FALSE);
			}
			hqlInfoTwo.setParameter("startTime", startTime);
			hqlInfoTwo.setParameter("endTime", endTime);
			hqlInfoTwo.setWhereBlock(whereBlockTwo.toString());
			listTwo = getSysAttendMainService().findList(hqlInfoTwo);
		}
		return AttendUtil.unionList(list,listTwo);
	}


	/**
	 * 删除考勤异常
	 *
	 * @param main
	 * @throws Exception
	 */
	protected void deleteAttendExc(SysAttendMain main) throws Exception {
		if (main != null) {
			List excList = getSysAttendMainExcService()
					.findList("sysAttendMainExc.fdAttendMain.fdId='"
							+ main.getFdId() + "'", "");
			if (excList != null && !excList.isEmpty()) {
				for (Object exc : excList) {
					//删除待办
					getSysNotifyMainCoreService().getTodoProvider().remove((SysAttendMainExc)exc, ((SysAttendMainExc) exc).getFdId());
					getSysAttendMainExcService().delete((SysAttendMainExc) exc);
				}
			}
		}
	}

	/**
	 * 缺卡通知置为已办
	 *
	 * @param main
	 * @throws Exception
	 */
	protected void setAttendNotifyToDone(SysAttendMain main) throws Exception {
		if (main != null && main.getDocCreator()!=null) {
			getSysNotifyMainCoreService().getTodoProvider().removePerson(main, "sendUnSignNotify",main.getDocCreator());
		}
	}

	/**
	 * 把一段时间分割成每一天
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	protected List<Date> getDateList(Date startTime, Date endTime) {
		List<Date> dates = new ArrayList<Date>();
		dates.add(startTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DATE, 1);
		for (; cal.getTime().compareTo(endTime) < 0; cal.add(Calendar.DATE,
				1)) {
			dates.add(cal.getTime());
		}
		dates.add(endTime);
		return dates;
	}
	/**
	 * 验证是否在考勤组
	 * @param business
	 * @return
	 */
	protected Boolean checkUserCategory(SysAttendBusiness business) throws Exception {
		String tip = ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip6");
		try{
			checkUserCategory(business.getFdTargets(),business.getFdBusStartTime());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			sendNotify(business, UserUtil.getKMSSUser().getPerson(),tip,tip);
			return false;
		}
		return true;
	}
	/**
	 * 根据流程获取是否有时间重复
	 * @param bus
	 * @return
	 */
	protected Boolean checkDateRepeat(SysAttendBusiness bus,Integer fdType,boolean sendNotify) {
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setJoinBlock(
					"left join sysAttendBusiness.fdTargets target");
			hqlInfo.setWhereBlock(
					"target.fdId=:targetId" +
							" and sysAttendBusiness.fdBusStartTime<:endTime and sysAttendBusiness.fdBusEndTime>:startTime"
							+ " and sysAttendBusiness.fdType=:fdType and (sysAttendBusiness.fdDelFlag=0 or sysAttendBusiness.fdDelFlag is null  or sysAttendBusiness.fdOverFlag =0)");
			hqlInfo.setParameter("targetId",
					bus.getFdTargets().get(0).getFdId());
			hqlInfo.setParameter("startTime", bus.getFdBusStartTime());
			hqlInfo.setParameter("endTime", bus.getFdBusEndTime());
			hqlInfo.setParameter("fdType", fdType);
			SysAttendBusiness list = (SysAttendBusiness) getSysAttendBusinessService().findFirstOne(hqlInfo);
			if(sendNotify){
				String content="";
				if(list != null){
					SysAttendBusiness sysAttendBusiness=list;
					content =  String.format("%s(%s-%s)",sysAttendBusiness.getFdProcessName(),
							sysAttendBusiness.getFdBusStartTime(),
							sysAttendBusiness.getFdBusEndTime()
					);
					//存在 则抛出异常
					String tip = ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip3");
					if (tip != null) {
						tip = tip.replace("%sys-attend:sysAttend.business.repeat%", content);
					}
					//发送待办
					sendNotify(bus, UserUtil.getKMSSUser().getPerson(),tip,tip);
					return false;
				}
			}
			if(list != null){
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 发送待办
	 * @param business 该对象为非数据库存储对象。不然会引发错误
	 * @param ele
	 * @throws Exception
	 */
	protected void sendNotify(SysAttendBusiness business,SysOrgElement ele,String title,String content) throws Exception {
		if (business == null) {
			logger.error("发送流程数据失败通知：出差记录不存在");
			return;
		}
		//因为每个流程的待办为一条。
		business.setFdId(business.getFdProcessId());

		NotifyContext notifyContext = getSysNotifyMainCoreService().getContext(null);
		notifyContext.setNotifyType("todo");
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
		notifyContext.setKey("sysAttendBusinessHandel");
		List<SysOrgElement> list = new ArrayList<SysOrgElement>();
		list.add(ele);
		notifyContext.setNotifyTarget(list);
		notifyContext.setSubject(title);
		notifyContext.setContent(content);
		notifyContext.setLink(business.getDocUrl());
		getSysNotifyMainCoreService().sendNotify(business, notifyContext,null);
	}
	/**
	 * 获取流程临时的对象，用于发送待办
	 * @param mainModel
	 * @return
	 * @throws Exception
	 */
	protected SysAttendBusiness getTempBusinessData(IBaseModel mainModel) throws Exception {
		//流程不正确，发送待办给起草人
		String docSubject = (String) getSysMetadataParser().getFieldValue(mainModel, "docSubject", false);
		SysAttendBusiness tempBusiness=new SysAttendBusiness();
		tempBusiness.setFdProcessId(mainModel.getFdId());
		tempBusiness.setFdProcessName(docSubject);
		tempBusiness.setDocUrl(AttendUtil.getDictUrl(mainModel, mainModel.getFdId()));

		return tempBusiness;
	}
	/**
	 * 待办变成已办
	 * @param flowId
	 * @param ele
	 * @throws Exception
	 */
	protected void removeNotify(String flowId, SysOrgPerson ele) throws Exception {
		SysAttendBusiness business=new SysAttendBusiness();
		business.setFdId(flowId);
		getSysNotifyMainCoreService().getTodoProvider().removePerson(business, "sysAttendBusinessHandel",ele);
	}

	/**
	 * 重置流程状态到起草人节点
	 * @param execution
	 * @throws Exception
	 */
	protected void resetFlowStatus(EventExecutionContext execution) throws Exception {
		execution.takeNodeId("N2");
		execution.getExecuteParameters().setExpectMainModelStatus(SysDocConstant.DOC_STATUS_DRAFT);
		// 更新流程实例状态为10
		execution.getProcessInstance().setFdStatus(SysDocConstant.DOC_STATUS_DRAFT);
		execution.getProcessInstance().getTempData().put(PROCESS_FLAG_RETURN_KEY,Boolean.TRUE);
		execution.getProcessParameters().addInstanceParamValue(execution.getProcessInstance(), PROCESS_FLAG_KEY, PROCESS_FLAG_ERROR_VALUE);

	}

	/**
	 * 执行重新统计到 每日汇总
	 * @param busList
	 */
	protected void reStatistics(List<SysAttendBusiness> busList, IEventMulticaster multicaster) {
		try {
			Set<String> statOrgs = getStatOrgList(busList);
			//处理天不重复
			Set<Date> statDates = getStatDateList(busList);

			if(CollectionUtils.isNotEmpty(statOrgs) && CollectionUtils.isNotEmpty(statDates)) {
				multicaster.attatchEvent(
						new EventOfTransactionCommit(StringUtils.EMPTY),
						new IEventCallBack() {
							@Override
							public void execute(ApplicationEvent arg0)
									throws Throwable {
								getSysAttendStatJobService().stat(Lists.newArrayList(statOrgs), Lists.newArrayList(statDates));
							}
						});
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("出差重新统计出错" + e.getMessage(), e);
		}
	}

	/**
	 * 获取流程的人员对象ID
	 * @param busList
	 * @return
	 * @throws Exception
	 */
	private Set<String> getStatOrgList(List<SysAttendBusiness> busList)
			throws Exception {
		Set<String> idList = new HashSet<String>();
		for (SysAttendBusiness bus : busList) {
			List<String> tmpList =  getSysOrgCoreService().expandToPersonIds(bus.getFdTargets());
			idList.addAll(tmpList);
		}
		return idList;
	}

	/**
	 * 根据流程业务获取统计的日期
	 * @param busList
	 * @return
	 * @throws Exception
	 */
	private Set<Date> getStatDateList(List<SysAttendBusiness> busList)
			throws Exception {
		Set<Date> statDates = new HashSet<>();
		for (SysAttendBusiness bus : busList) {
			Date startTime = AttendUtil.getDate(bus.getFdBusStartTime(), 0);
			Date endTime = AttendUtil.getDate(bus.getFdBusEndTime(), 0);
			Calendar cal = Calendar.getInstance();
			for (cal.setTime(startTime); cal.getTime()
					.compareTo(endTime) <= 0; cal.add(Calendar.DATE, 1)) {
				statDates.add(cal.getTime());
			}
			//默认处理流程前1天的统计。防止跨天
//			statDates.add( AttendUtil.getDate(bus.getFdBusStartTime(), -1));
		}
		return statDates;
	}

	protected SysTimeLeaveTimeDto getLeaveTimes(SysOrgPerson person, Integer leaveType,
											  Date startTime, Date endTime, Integer statType, Integer startNoon,
											  Integer endNoon) throws Exception {
		if (statType == null) {
			SysTimeLeaveTimeDto dto =new SysTimeLeaveTimeDto();
			int leaveMins = getLeaveDays(person, leaveType, startTime, endTime, statType,
					startNoon, endNoon);
			dto.setLeaveTimeMins(leaveMins);
			dto.setLeaveTimeDays(Float.valueOf(leaveMins/24/60));
			return dto;
		}
		SysTimeLeaveRule sysTimeLeaveRule = null;
		Integer fdStatDayType = Integer.valueOf(2);// 默认自然日
		if (leaveType != null) {// 根据请假类型获取相关配置
			sysTimeLeaveRule = AttendUtil.getLeaveRuleByType(leaveType);
			if (sysTimeLeaveRule != null) {
				fdStatDayType = sysTimeLeaveRule.getFdStatDayType();
			}
		}
		return SysTimeUtil.getLeaveTimes(person, startTime, endTime,
				startNoon, endNoon, fdStatDayType, statType,String.valueOf(leaveType));
	}

	/**
	 * 获取请假分钟数(兼容历史问题)
	 * @param startTime
	 * @param endTime
	 * @param statType
	 * @param startNoon
	 * @param endNoon
	 * @return
	 * @throws Exception
	 */
	private int getLeaveDays(SysOrgPerson person, Integer leaveType,
							 Date startTime, Date endTime, Integer statType, Integer startNoon,
							 Integer endNoon) throws Exception {
		if (startTime == null || endTime == null) {
			return 0;
		}
		Float leaveDays = 0f;
		List<Date> dateList = getDateList(statType, startTime, endTime,
				startNoon, endNoon);
		if (dateList.size() < 2) {
			return 0;
		}
		for (int i = 0; i < dateList.size() - 1; i++) {
			Date leaveStart = dateList.get(i);
			Date leaveEnd = dateList.get(i + 1);
			Date date = AttendUtil.getDate(leaveStart, 0);
			Float days = 0f;

			if (leaveStart == null || leaveEnd == null
					|| leaveStart.getTime() > leaveEnd.getTime()) {
				continue;
			}

			Integer fdStatDayType = Integer.valueOf(2);// 默认自然日
			SysTimeLeaveRule sysTimeLeaveRule = null;
			if (leaveType != null) {// 根据请假类型获取相关配置
				sysTimeLeaveRule = AttendUtil.getLeaveRuleByType(leaveType);
				if (sysTimeLeaveRule != null) {
					fdStatDayType = sysTimeLeaveRule.getFdStatDayType();
				} else {
					logger.error("获取假期类型为空,userName:" + person.getFdName()
							+ ",leaveType:" + leaveType);
				}
			}

			String categoryId = getSysAttendCategoryService().getAttendCategory(person);
			SysAttendCategory category = null;
			if (StringUtil.isNotNull(categoryId)) {
				category = (SysAttendCategory) getSysAttendCategoryService().findByPrimaryKey(categoryId, null, true);
			}
			if (category == null) {
				logger.error("获取请假天数：没有考勤组");
				continue;
			}
			if (!isRestDay(date, category, person)
					|| !Integer.valueOf(1).equals(fdStatDayType)) {
				if (statType == null) {// 旧数据
					// 获取打卡点
					List<Map<String, Object>> signTimeList = getSignTimeList(category, startTime, person, true);
					if (signTimeList.isEmpty() && Integer.valueOf(1)
							.equals(category.getFdShiftType())) {
						signTimeList = getAttendAreaRestSignTimes(category,
								AttendUtil.getDate(date, 1), person);
					}

					if (signTimeList.isEmpty()) {
						logger.error("获取请假天数：：获取打卡时间点失败");
						continue;
					}
					int totalCount = signTimeList.size();
					signTimeList = filterSignTimeList(signTimeList,
							startTime, endTime);
					int signCount = signTimeList.size();
					if (signCount > 0) {// 有请假记录
						if (signCount <= totalCount) {
							days = 0.5f;
						} else {
							days = 1f;
						}
					} else {// 没有请假记录
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						cal.set(Calendar.HOUR_OF_DAY, 12);
						Date noon = cal.getTime();
						if (leaveStart.getTime() <= noon.getTime()
								&& leaveEnd.getTime() >= noon.getTime()) {// 包含中午12点则算一天，否则半天
							days = 1f;
						} else {
							days = 0.5f;
						}
					}
				}
			}
			leaveDays += days;
		}
		return (int) (leaveDays * 24 * 60);
	}
}
