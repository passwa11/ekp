package com.landray.kmss.sys.attend.plugin;

import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryRule;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.engine.task.NodeInstanceUtils;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 销出差流程事件同步接口
 * 
 * @author linxiuxian
 *
 */
public class SysAttendBusinessResumeListener extends SysAttendListenerCommonImp
		implements IEventListener, IEventMulticasterAware,
		ApplicationListener<Event_Common> {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendBusinessResumeListener.class);



	@Override
	public void handleEvent(EventExecutionContext execution, String parameter)
			throws Exception {
		//流程标识
		String processFlag = execution.getProcessParameters().getInstanceParamValue(execution.getProcessInstance(),PROCESS_FLAG_KEY);
		if (PROCESS_FLAG_RUN_VALUE.equals(processFlag) || PROCESS_FLAG_ERROR_VALUE.equals(processFlag)) {
			//新流程事件不进老流程处理
			return;
		}
		logger.debug(
				"receive SysAttendBusinessResumeListener,parameter="
						+ parameter);
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
				List<SysAttendBusiness> list = getSysAttendBusinessService().findByProcessId(mainModel.getFdId());
				// 同一流程不会重复操作
				if (list.isEmpty()) {
					List<SysAttendBusiness> busList = getBusinessList(params,
							mainModel);
					if (busList == null || busList.isEmpty()) {
						logger.warn(
								"销出差流程数据配置不准确,忽略处理!parameter:"
										+ parameter);
						return;
					}
					if (busList != null && !busList.isEmpty()) {
						// 1.保存流程表单数据
						for (SysAttendBusiness bus : busList) {
							updateAttendBusinessByResume(bus);
						}
						// 2.重新统计
						reStatistics(busList,this.multicaster);
					}
				} else {
					logger.warn(
							"同个流程只执行一次,忽略此次操作!流程id:" + mainModel.getFdId()
									+ ";parameter=" + parameter);
				}
			}
		}
	}

	/**
	 * 重新更新出差数据
	 * @param resume
	 * @throws Exception
	 */
	public void updateAttendBusinessByResume(SysAttendBusiness resume)
			throws Exception {
		Date fdBusStartTime = resume.getFdBusStartTime();
		Date fdBusEndTime = resume.getFdBusEndTime();
		List<SysOrgPerson> personList = getSysOrgCoreService()
				.expandToPerson(resume.getFdTargets());
		List<String> orgIdList = new ArrayList<String>();
		for(SysOrgPerson p : personList){
			orgIdList.add(p.getFdId());
		}
		// 获取用户的出差流程
		List<Integer> fdTypes = new ArrayList<Integer>();
		fdTypes.add(4);
//		Date endTime = new Date(fdBusEndTime.getTime());
//		endTime.setSeconds(endTime.getSeconds() + 1);
		List<SysAttendBusiness> busList = this.getSysAttendBusinessService()
				.findBussList(orgIdList, fdBusStartTime,
						fdBusEndTime, fdTypes);
		for (SysOrgPerson person : personList) {
			List<SysAttendBusiness> userBusList = getUserBusList(busList,
					person);
			if (userBusList.isEmpty()) {
				logger.warn("用户销出差流程处理忽略,原因:该用户未找到出差记录!userName:"
						+ person.getFdName() + ";startTime:" + fdBusStartTime
						+ ";endTime:" + fdBusEndTime);
				continue;
			}
			for (SysAttendBusiness bus : userBusList) {
				// 实际销假时间区间
				Map<String, Object> dateMap = new HashMap<String, Object>();
				boolean isUpdate = convertBusinessInfo(bus, fdBusStartTime,
						fdBusEndTime, dateMap);
				if (isUpdate) {
					// 保存销出差流程
					SysAttendBusiness userResume = this.cloneBusBiz(resume);
					List<SysOrgElement> newTargets = new ArrayList<SysOrgElement>();
					newTargets.add(person);
					userResume.setFdTargets(newTargets);
					getSysAttendBusinessService().add(userResume);
					// 更新出差打卡记录
					updateSysAttendMain(userResume);
				} else {
					logger.warn("用户" + person.getFdName()
							+ "的销出差流程配置的时间区间没有找到对应的出差记录数据,忽略处理!销出差时间区间:"
							+ fdBusStartTime + "~" + fdBusEndTime);
				}
			}
		}
	}

	/**
	 * 根据本次流程业务数据 和 出差记录进行区间拆分
	 * @param bus 出差对象
	 * @param startTime  销出差开始时间
	 * @param endTime  销出差结束时间
	 * @param dateMap
	 * @return
	 * @throws Exception
	 */
	protected boolean checkConvertBusinessInfo(SysAttendBusiness bus,
										  Date startTime, Date endTime, Map<String, Object> dateMap)
			throws Exception {
		boolean result = false;
		Date fdBusStartTime = bus.getFdBusStartTime();
		Date fdBusEndTime = bus.getFdBusEndTime();
		// 实际销时间区间
		if (fdBusEndTime.getTime() > endTime.getTime()
				&& fdBusStartTime.getTime() < startTime.getTime()) {
			// 区间内拆分成两个区间
			dateMap.put("startTime", startTime);
			dateMap.put("endTime", AttendUtil.addDate(endTime,Calendar.SECOND,1));
			result = true;
		} else if (fdBusEndTime.getTime() > startTime.getTime()
				&& fdBusStartTime.getTime() < startTime.getTime()) {
			dateMap.put("startTime", startTime);
			dateMap.put("endTime", fdBusEndTime);
			result = true;
		} else if (fdBusEndTime.getTime() <= endTime.getTime()
				&& fdBusStartTime.getTime() >= startTime.getTime()) {
			dateMap.put("startTime", fdBusStartTime);
			dateMap.put("endTime", fdBusEndTime);
			result = true;
		} else if (fdBusEndTime.getTime() > endTime.getTime()
				&& fdBusStartTime.getTime() < endTime.getTime()) {
			dateMap.put("startTime", fdBusStartTime);
			dateMap.put("endTime", AttendUtil.addDate(endTime,Calendar.SECOND,1));
			result = true;
		}
		return result;
	}
	/**
	 * 根据本次流程业务数据 和 出差记录进行区间拆分
	 * @param bus 出差对象
	 * @param startTime  销出差开始时间
	 * @param endTime  销出差结束时间
	 * @param dateMap
	 * @return
	 * @throws Exception
	 */
	protected boolean convertBusinessInfo(SysAttendBusiness bus,
			Date startTime, Date endTime, Map<String, Object> dateMap)
			throws Exception {
		boolean result = false;
		Date fdBusStartTime = bus.getFdBusStartTime();
		Date fdBusEndTime = bus.getFdBusEndTime();
		// 实际销时间区间
		if (fdBusEndTime.getTime() > endTime.getTime()
				&& fdBusStartTime.getTime() < startTime.getTime()) {// 区间内拆分成两个区间
			SysAttendBusiness newBus = cloneBusBiz(bus);
			bus.setFdBusEndTime(startTime);
			getSysAttendBusinessService().update(bus);

			newBus.setFdBusStartTime(AttendUtil.addDate(endTime, Calendar.SECOND,1));
			getSysAttendBusinessService().add(newBus);
			dateMap.put("startTime", startTime);
			dateMap.put("endTime", endTime);
			result = true;
		} else if (fdBusEndTime.getTime() > startTime.getTime()
				&& fdBusStartTime.getTime() < startTime.getTime()) {//
			bus.setFdBusEndTime(startTime);
			getSysAttendBusinessService().update(bus);
			dateMap.put("startTime", startTime);
			dateMap.put("endTime", fdBusEndTime);
			result = true;
		} else if (fdBusEndTime.getTime() <= endTime.getTime()
				&& fdBusStartTime.getTime() >= startTime.getTime()) {
			bus.setFdDelFlag(1);
			getSysAttendBusinessService().update(bus);
			dateMap.put("startTime", fdBusStartTime);
			dateMap.put("endTime", fdBusEndTime);
			result = true;
		} else if (fdBusEndTime.getTime() > endTime.getTime()
				&& fdBusStartTime.getTime() < endTime.getTime()) {
			//拆分流程。将开始时间从新的结束时间+1秒开始
			bus.setFdBusStartTime(AttendUtil.addDate(endTime, Calendar.SECOND,1));
			getSysAttendBusinessService().update(bus);
			dateMap.put("startTime", fdBusStartTime);
			dateMap.put("endTime", endTime);
			result = true;
		}
		return result;
	}

	private SysAttendBusiness cloneBusBiz(SysAttendBusiness bus) {
		if (bus == null) {
			return null;
		}
		SysAttendBusiness sysAttendBusiness = new SysAttendBusiness();
		sysAttendBusiness.setFdBusStartTime(bus.getFdBusStartTime());
		sysAttendBusiness.setFdBusEndTime(bus.getFdBusEndTime());
		sysAttendBusiness.setFdProcessId(bus.getFdProcessId());
		sysAttendBusiness.setFdProcessName(bus.getFdProcessName());
		sysAttendBusiness.setDocUrl(bus.getDocUrl());
		List<SysOrgElement> newTargets = new ArrayList<SysOrgElement>();
		newTargets.addAll(bus.getFdTargets());
		sysAttendBusiness.setFdTargets(newTargets);
		sysAttendBusiness.setFdType(bus.getFdType());
		sysAttendBusiness.setDocCreateTime(new Date());
		return sysAttendBusiness;
	}

	/**
	 * 获取指定用户的出差流程,并过滤重复
	 * @param busList
	 * @param person
	 * @return
	 */
	protected List getUserBusList(List<SysAttendBusiness> busList,
			SysOrgElement person) {
		List<SysAttendBusiness> userBusList = new ArrayList<SysAttendBusiness>();
		if (busList == null || busList.isEmpty()) {
			return userBusList;
		}

		for (SysAttendBusiness bus : busList) {
			List<SysOrgElement> targets = bus.getFdTargets();
			if (targets.contains(person)) {
				userBusList.add(bus);
			}
		}
		// 过滤重复数据
		Map<String, SysAttendBusiness> maps = new HashMap<String, SysAttendBusiness>();
		for (SysAttendBusiness bus : userBusList) {
			Date startTime = bus.getFdBusStartTime();
			Date endTime = bus.getFdBusEndTime();
			// 根据时间参数判断是否重复
			String key = startTime.getTime() + "_" + endTime.getTime();
			maps.put(key, bus);
		}
		List<SysAttendBusiness> newUserBusList = new ArrayList<SysAttendBusiness>();
		for (String key : maps.keySet()) {
			newUserBusList.add(maps.get(key));
		}
		return newUserBusList;
	}

	private void updateSysAttendMain(SysAttendBusiness business)
			throws Exception {
		try {
			Date fdBusStartTime = business.getFdBusStartTime();
			Date fdBusEndTime = business.getFdBusEndTime();
			List<SysOrgElement> fdTargets = business.getFdTargets();
			List<SysOrgPerson> personList =  getSysOrgCoreService()
					.expandToPerson(fdTargets);
			Date today = AttendUtil.getDate(new Date(), 0);
			// 每个人
			for (SysOrgPerson person : personList) {
				List<Date> dateList = getDateList(fdBusStartTime, fdBusEndTime);
				if (dateList.size() < 2) {
					continue;
				}
				// 每天
				for (int i = 0; i < dateList.size() - 1; i++) {
					Date startTime = dateList.get(i);
					Date endTime = dateList.get(i + 1);
					Date date = AttendUtil.getDate(startTime, 0);

					List<SysAttendMain> recordList= getSysAttendMainList(person.getFdId(),4,
							AttendUtil.getDate(date, 1),
							AttendUtil.getDate(date, 2),
							AttendUtil.getDate(date, 0),
							AttendUtil.getDate(date, 1),
							AttendUtil.isZeroDay(startTime)
					);

					if (recordList.isEmpty()) {
						logger.warn("该用户没有查询到相应出差记录,销出差流程忽略!userName:"
								+ person.getFdName());
					}
					for (SysAttendMain record : recordList) {
						Integer fdStatus = record.getFdStatus();
						if (fdStatus != 4) {
							continue;// 非出差
						}
						// 标准上下班时间
						Date workDate = record.getFdWorkDate();
						boolean isNeedUpdate = checkAttendedRecordConditions(startTime, endTime, fdBusEndTime, record);
						if (isNeedUpdate) {
							// 处于出差区间
							if (AttendUtil.getDate(workDate, 0).before(today)) {
								// 历史数据
								record.setFdStatus(getAttendStatus(record));
								record.setFdBusiness(null);
								//休息日、节假日的缺卡记录置为无效
								if (record.getFdStatus() == 0
										&& record.getFdState() == null
										&& record.getFdDateType()!=null 
										&& !AttendConstant.FD_DATE_TYPE[0].equals(String.valueOf(record.getFdDateType()))) {
									record.setDocStatus(1);
									record.setFdAlterRecord(
											"销假流程同步事件置为无效记录");
								}
								getSysAttendMainService().getBaseDao()
										.update(record);
							} else if (today.equals(
									AttendUtil.getDate(workDate, 0))) {
								// 当天
								record.setFdStatus(getAttendStatus(record));
								record.setFdBusiness(null);
								if (record.getFdStatus() == 0
										&& record.getFdState() == null) {
									record.setDocStatus(1);
									record.setFdAlterRecord(
											"销出差流程同步事件置为无效记录");
									record.setDocAlterTime(new Date());
								}
								getSysAttendMainService().getBaseDao()
										.update(record);
							} else {
								// 将来
								record.setDocStatus(1);
								record.setFdAlterRecord(
										"销出差流程同步事件置为无效记录");
								record.setDocAlterTime(new Date());
								getSysAttendMainService().getBaseDao()
										.update(record);
							}
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("销出差同步考勤更新打卡记录出错:" + e.getMessage(), e);
		}
	}

	/**
	 * 校验考勤数据是否需要更新
	 * @param startTime
	 * 	考勤开始时间
	 * @param endTime
	 * 	考勤结束时间
	 * @param fdBusEndTime
	 * 	出差结束时间
	 * @param record
	 * 	当前考勤数据
	 * @return
	 * @throws Exception 
	 */
	private boolean checkAttendedRecordConditions(Date startTime, Date endTime, Date fdBusEndTime, SysAttendMain record) throws Exception {
		Date docCreateTime = record.getDocCreateTime();
		if(!AttendUtil.isZeroDay(startTime) && docCreateTime.before(startTime))
		{
			return false;
		}
		boolean isNeedUpdate = (!docCreateTime.before(startTime)
				&& !docCreateTime.after(endTime));
		if(!isNeedUpdate)
		{
			SysAttendCategory sysAttendCategory =CategoryUtil.getFdCategoryInfo(record);
			List<Map<String, Object>> signTimeConfigurations = getSysAttendCategoryService().getAttendSignTimes(sysAttendCategory,
					record.getFdWorkDate(), record.getDocCreator(), true);
			getSysAttendCategoryService().doWorkTimesRender(signTimeConfigurations, new ArrayList<SysAttendMain>(Arrays.asList(new SysAttendMain[] {record})));
			for(Map<String, Object> signTimeConfiguration : signTimeConfigurations){
				Date fdEndTime = (Date) signTimeConfiguration.get("fdEndTime");
				boolean isSameWorkTime = getSysAttendCategoryService().isSameWorkTime(signTimeConfiguration,
						record.getWorkTime() == null ? "" : record.getWorkTime().getFdId(),
								record.getFdWorkType(),
								record.getFdWorkKey());
				if(isSameWorkTime && isOverTimeType(signTimeConfiguration)){
					//判断结束时间是23:59:59秒 是流程结束，则把跨天的也处理掉
//					fdBusEndTime = AttendUtil.removeSecond(fdBusEndTime);
					if(AttendUtil.isZeroDay(fdBusEndTime)) {
						fdBusEndTime = AttendUtil.addDate(fdBusEndTime, 1);
					} else {
						//跨天排班的下班打卡时间
						Date lastOffworkTime = AttendUtil.joinYMDandHMS(fdBusEndTime, (Date)signTimeConfiguration.get("signTime"));
						//比较销假结束时间是否在跨天排班下班打卡时间之内
						if(fdBusEndTime.before(lastOffworkTime) && docCreateTime.after(fdBusEndTime)){
							return false;
						}
					}
					Date fdBusEndTime_ = AttendUtil.joinYMDandHMS(fdBusEndTime, fdEndTime);
					isNeedUpdate = docCreateTime.before(fdBusEndTime_);
				}
			}
		}
		return isNeedUpdate;
	}

	/**
	 * 重新计算打卡记录的状态
	 * 
	 * @param main
	 * @return
	 */
	private Integer getAttendStatus(SysAttendMain main) {
		try {
			SysAttendCategory category =CategoryUtil.getFdCategoryInfo(main);
			SysAttendCategoryRule rule = category.getFdRule().get(0);
			Boolean fdIsFlex = category.getFdIsFlex();
			Integer fdFlexTime = category.getFdFlexTime();
			Integer fdLeftTime = rule.getFdLeftTime();
			Integer fdLateTime = rule.getFdLateTime();
			Date doCreateTime = main.getDocCreateTime();
			Integer fdStatus = 1;
			if (StringUtil.isNull(main.getFdLocation())
					&& StringUtil.isNull(main.getFdWifiName())
					&& main.getFdAppName() == null) {// 缺卡
				fdStatus = 0;
				return fdStatus;
			}
			// 正常打卡时间点
			Date normalSignTime = null;
			if (main.getWorkTime() != null) {
				if (Integer.valueOf(0).equals(main.getFdWorkType())) {
					normalSignTime = main.getWorkTime().getFdStartTime();
				} else {
					normalSignTime = main.getWorkTime().getFdEndTime();
				}
			} else if (StringUtil.isNotNull(main.getFdWorkKey())) {// 排班
				Date date = AttendUtil.getDate(main.getDocCreateTime(), 0);
				List<Map<String, Object>> signTimeList = getSignTimeList(
						category, date, main.getDocCreator());
				if (!signTimeList.isEmpty()) {
					normalSignTime = getNormalSignTime(main, signTimeList);
				}
			}
			if (normalSignTime != null) {
				int signMins = AttendUtil.getHMinutes(doCreateTime);
				int normalMins = AttendUtil.getHMinutes(normalSignTime);
				List<Map<String, Object>> signTimeList = getSysAttendCategoryService().getAttendSignTimes(main.getDocCreator(), main.getFdWorkDate());
				getSysAttendCategoryService().doWorkTimesRender(signTimeList, new ArrayList<SysAttendMain>(Arrays.asList(new SysAttendMain[] {main})));
				signMins += (Boolean.TRUE.equals(main.getFdIsAcross()) ? 24 * 60 : 0);
				for(Map<String, Object> signTimeConfiguration : signTimeList){
					boolean isSameWorkTime = getSysAttendCategoryService().isSameWorkTime(signTimeConfiguration,
							main.getWorkTime() == null ? "" : main.getWorkTime().getFdId(),
									main.getFdWorkType(),
									main.getFdWorkKey());
					if(isSameWorkTime && isOverTimeType(signTimeConfiguration)){
						normalMins += 24 * 60;
						break;
					}
				}
				if (Integer.valueOf(0).equals(main.getFdWorkType())) {
					if (Boolean.TRUE.equals(fdIsFlex) && fdFlexTime != null) {
						if (signMins - normalMins > fdFlexTime) {
							fdStatus = 2;
						}
					} else if (fdLeftTime != null) {
						if (signMins - normalMins > fdLeftTime) {
							fdStatus = 2;
						}
					}
				} else if (Integer.valueOf(1).equals(main.getFdWorkType())) {
					if (fdLateTime != null) {
						if (normalMins - signMins > fdLateTime) {
							fdStatus = 3;
						}
					} else {
						if (normalMins > signMins) {
							fdStatus = 3;
						}
					}
				}
			}
			return fdStatus;
		} catch (Exception e) {
			return 0;
		}
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





	protected List<SysAttendBusiness> getBusinessList(JSONObject params,
			IBaseModel mainModel) throws Exception {
		List<SysAttendBusiness> busList = new ArrayList<SysAttendBusiness>();
		try {
			IExtendDataModel model = (IExtendDataModel) mainModel;
			Map<String, Object> modelData = model.getExtendDataModelInfo()
					.getModelData();

			String docSubject = (String) getSysMetadataParser().getFieldValue(mainModel, "docSubject", false);

			JSONObject startTimeJson = JSONObject
					.fromObject(params.get("fdBusStartTime"));
			JSONObject endTimeJson = JSONObject
					.fromObject(params.get("fdBusEndTime"));
			JSONObject targetsJson = JSONObject
					.fromObject(params.get("fdBusTargets"));

			String startFieldName = (String) startTimeJson.get("value");
			String endFieldName = (String) endTimeJson.get("value");
			String targetsFieldName = (String) targetsJson.get("value");

			String startFieldType = (String) startTimeJson.get("model");
			String endFieldType = (String) endTimeJson.get("model");

			String targetIds = null;
			if (modelData.containsKey(targetsFieldName)) {
				Object targetsObj = modelData.get(targetsFieldName);
				targetIds = BeanUtils.getProperty(targetsObj, "id");
			} else {
				SysOrgElement org = (SysOrgElement) PropertyUtils
						.getProperty(mainModel, targetsFieldName);
				targetIds = org.getFdId();
			}

			if (targetIds == null) {
				logger.warn("销出差流程同步考勤事件中出差人为空!");
				return null;
			}

			if ("Date[]".equals(startFieldType)
					|| "DateTime[]".equals(startFieldType)
					|| "Date[]".equals(endFieldType)
					|| "DateTime[]".equals(endFieldType)) {
				if (startFieldName.indexOf(".") == -1
						|| endFieldName.indexOf(".") == -1) {
					logger.warn(
							"销出差流程时间配置不准确,startFieldName:" + startFieldName
									+ ";endFieldName:" + endFieldName);
					return null;
				}
				String detailName = startFieldName.split("\\.")[0];
				String startName = startFieldName.split("\\.")[1];
				String endName = endFieldName.split("\\.")[1];
				List<HashMap<String, Object>> detailList = (ArrayList<HashMap<String, Object>>) modelData
						.get(detailName);

				for (int k = 0; k < detailList.size(); k++) {
					HashMap detail = detailList.get(k);
					Date startTime = (Date) detail.get(startName);
					Date endTime = (Date) detail.get(endName);

					if (startTime == null || endTime == null) {
						logger.warn(
								"销出差流程时间配置为空,startTime:" + startTime
										+ ";endTime:" + endTime);
						continue;
					}
					if ("Date[]".equals(endFieldType)) {
						endTime = AttendUtil.getDayLastTime(endTime);
					}
					if (startTime.getTime() >= endTime.getTime()) {
						logger.warn(
								"销出差流程时间配置开始时间大于结束时间,startTime:" + startTime
										+ ";endTime:" + endTime);
						continue;
					}
					busList.add(getBusinessModel(startTime, endTime, targetIds,
							model.getFdId(), docSubject, mainModel));
				}
			} else {
				Date fdBusStartTime = null;
				Date fdBusEndTime = null;

				if ("Date".equals(startFieldType)
						|| "DateTime".equals(startFieldType)) {
					if (modelData.containsKey(startFieldName)) {
						fdBusStartTime = (Date) modelData.get(startFieldName);
					} else {
						fdBusStartTime = (Date) PropertyUtils
								.getProperty(mainModel, startFieldName);
					}
				}

				if ("Date".equals(endFieldType)
						|| "DateTime".equals(endFieldType)) {

					if (modelData.containsKey(endFieldName)) {
						fdBusEndTime = (Date) modelData.get(endFieldName);
					} else {
						fdBusEndTime = (Date) PropertyUtils
								.getProperty(mainModel, endFieldName);
					}

					if ("Date".equals(endFieldType)) {
						fdBusEndTime = AttendUtil.getDayLastTime(fdBusEndTime);
					}
				}

				if (fdBusStartTime == null || fdBusEndTime == null
						|| targetIds == null
						|| fdBusStartTime.getTime() >= fdBusEndTime.getTime()) {
					logger.warn("出差流程时间配置不准确:fdBusStartTime" + fdBusStartTime
							+ ";" + fdBusEndTime);
					return null;
				}
				busList.add(getBusinessModel(fdBusStartTime, fdBusEndTime,
						targetIds, model.getFdId(), docSubject, mainModel));
			}
			return busList;
		} catch (Exception e) {
			logger.error("获取出差数据出错:" + e.getMessage());
			return null;
		}
	}

	private SysAttendBusiness getBusinessModel(Date fdBusStartTime,
			Date fdBusEndTime, String targetIds, String fdProcessId,
			String fdProcessName, IBaseModel mainModel)
			throws Exception {
		SysAttendBusiness sysAttendBusiness = new SysAttendBusiness();
		sysAttendBusiness.setFdBusStartTime(fdBusStartTime);
		sysAttendBusiness.setFdBusEndTime(fdBusEndTime);
		sysAttendBusiness.setFdProcessId(fdProcessId);
		sysAttendBusiness.setFdProcessName(fdProcessName);
		sysAttendBusiness.setDocUrl(AttendUtil.getDictUrl(mainModel,fdProcessId));
		sysAttendBusiness.setFdTargets(getSysOrgCoreService().findByPrimaryKeys(targetIds.split(";")));
		sysAttendBusiness.setFdType(9);
		sysAttendBusiness.setDocCreateTime(new Date());
		return sysAttendBusiness;
	}


	@Override
	public void onApplicationEvent(Event_Common event) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private IEventMulticaster multicaster;
	@Override
	public void setEventMulticaster(IEventMulticaster multicaster) {
		this.multicaster = multicaster;
	}
}
