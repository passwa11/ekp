package com.landray.kmss.sys.attend.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendMainJobService;
import com.landray.kmss.sys.attend.service.ISysAttendStatJobService;
import com.landray.kmss.sys.attend.service.ISysAttendStatMonthJobService;
import com.landray.kmss.sys.attend.service.ISysAttendSynDingService;
import com.landray.kmss.sys.attend.util.AttendThreadPoolManager;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.engine.task.NodeInstanceUtils;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.metadata.interfaces.ISysMetadataParser;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzCoreService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzModelContext;
import com.landray.kmss.sys.time.interfaces.ISysTimeCountService;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.model.SysTimeBusinessEx;
import com.landray.kmss.sys.time.model.SysTimeElementEx;
import com.landray.kmss.sys.time.model.SysTimeOrgElementTime;
import com.landray.kmss.sys.time.model.SysTimePatchwork;
import com.landray.kmss.sys.time.model.SysTimeVacation;
import com.landray.kmss.sys.time.model.SysTimeWork;
import com.landray.kmss.sys.time.service.ISysTimeBusinessExService;
import com.landray.kmss.sys.time.service.ISysTimeElementExService;
import com.landray.kmss.sys.time.service.ISysTimePatchworkService;
import com.landray.kmss.sys.time.service.ISysTimeVacationService;
import com.landray.kmss.sys.time.service.ISysTimeWorkService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;


public class SysAttendExchangeListener
		implements IEventListener {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendExchangeListener.class);
	private ISysAttendCategoryService sysAttendCategoryService;

	
	private ISysNotifyMainCoreService sysNotifyMainCoreService;
	private ISysTimeBusinessExService sysTimeBusinessExService;
	private ISysTimeElementExService sysTimeElementExService;
	private ISysTimeCountService sysTimeCountService;
	private ISysTimeWorkService sysTimeWorkService;
	private ISysTimePatchworkService sysTimePatchworkService;
	private ISysTimeVacationService sysTimeVacationService;
	private ISysAttendSynDingService sysAttendSynDingService;
	private ISysAttendMainJobService sysAttendMainJobService;
	private ISysAttendStatMonthJobService sysAttendStatMonthJobService;

	public void setSysNotifyMainCoreService(ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	private ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
			.getBean("sysOrgCoreService");

	private ISysAttendStatJobService sysAttendStatJobService;
	private ISysQuartzCoreService sysQuartzCoreService;

	private ISysMetadataParser sysMetadataParser = (ISysMetadataParser) SpringBeanUtil
			.getBean("sysMetadataParser");
	
	public ISysAttendMainJobService getSysAttendMainJobService() {
		if (sysAttendMainJobService == null) {
			sysAttendMainJobService = (ISysAttendMainJobService) SpringBeanUtil
					.getBean("sysAttendMainJobService");
		}
		return sysAttendMainJobService;
	}
	
	public void setSysQuartzCoreService(
			ISysQuartzCoreService sysQuartzCoreService) {
		this.sysQuartzCoreService = sysQuartzCoreService;
	};

	public ISysAttendStatMonthJobService getSysAttendStatMonthJobService() {
		if (sysAttendStatMonthJobService == null) {
			sysAttendStatMonthJobService = (ISysAttendStatMonthJobService) SpringBeanUtil
					.getBean("sysAttendStatMonthJobService");
		}
		return sysAttendStatMonthJobService;
	}
	
	@Override
	public void handleEvent(EventExecutionContext execution, String parameter)
			throws Exception {
		logger.debug(
				"receive SysAttendBusinessListener,parameter=" + parameter);
		String routeType = execution.getNodeInstance().getFdRouteType();
		String docStatus = execution.getExecuteParameters()
				.getExpectMainModelStatus();
		if ((NodeInstanceUtils.ROUTE_TYPE_NORMAL.equals(routeType)
				|| NodeInstanceUtils.ROUTE_TYPE_JUMP.equals(routeType))
				&& !SysDocConstant.DOC_STATUS_DISCARD.equals(docStatus)
				&& !SysDocConstant.DOC_STATUS_DRAFT.equals(docStatus)) {
			logger.debug("start handleEvent...");
			JSONObject params = JSONObject.parseObject(parameter);
			IBaseModel mainModel = execution.getMainModel();
			if (mainModel instanceof IExtendDataModel) {
				List<SysTimeBusinessEx> list = sysTimeBusinessExService
						.findByFlowId(mainModel.getFdId());
				// 同一流程不会重复操作
				if (list.isEmpty()) {
					List<SysTimeBusinessEx> busList = getBusinessList(params,
							mainModel);
					if (busList == null || busList.isEmpty()) {
						logger.warn(
								"换班流程数据配置不准确,忽略处理!parameter:"
										+ parameter);
						return;
					}
					JSONArray arr = new JSONArray();
					if (busList != null && !busList.isEmpty()) {
						// 1.保存数据
						for (SysTimeBusinessEx bus : busList) {
							saveTimeBusiness(bus, execution.getMainModel());
							JSONObject busJson = new JSONObject();
							String targetIds = "";
							targetIds+=bus.getFdApplyPerson().getFdId()+";"+bus.getFdExchangePerson().getFdId();
							busJson.put("targetIds", targetIds);
							Date startTime = AttendUtil.getDate(bus.getFdExchangeDate(), 0);
							Date endTime = AttendUtil.getDate(bus.getFdReturnDate(), 0);
							//大于当前时间不重新计算有效记录
							if(startTime != null && startTime.getTime()<=new Date().getTime()) {
								busJson.put("startTime", startTime.getTime());
							}
							//大于当前时间不重新计算有效记录
							if(endTime != null && endTime.getTime()<=new Date().getTime()) {
								busJson.put("endTime", endTime.getTime());
							}
							
							arr.add(busJson);
						}
						// 2.重新计算有效记录
						//restat(busList);
						schedulerJob(arr.toString(),(SysTimeBusinessEx)busList.get(0));
					}
				} else {
					logger.warn(
							"同个流程只执行一次,忽略此次操作!流程id:" + mainModel.getFdId()
									+ ";parameter=" + parameter);
				}
			}
		}
	}
	
	private void saveTimeBusiness(SysTimeBusinessEx bus, IBaseModel model) throws Exception {
		Date fdReturnDate = AttendUtil.getDate(bus.getFdReturnDate(), 0);
		Date fdExChangeDate = AttendUtil.getDate(bus.getFdExchangeDate(), 0);
		SysTimeBusinessEx business = this.getBusinessModel(
				fdExChangeDate,fdReturnDate, 
				bus.getFdFlowId(), bus.getFdFlowName(), null, bus.getFdApplyPerson().getFdId(),bus.getFdExchangePerson().getFdId(), model);
		Boolean canExchange = false;
		String errorMessage = null;
		try {
			//检验是否可以换班，并返回失败原因
			SysAttendExchangeResult result = this.sysAttendCategoryService.validatorCanExchangeWorkTime(bus.getFdApplyPerson(), bus.getFdExchangePerson(),fdExChangeDate , fdReturnDate);
			canExchange = result.getReturnState();
			errorMessage = result.getMessage();
		}catch(Exception e) {
			errorMessage = e.getMessage();
			logger.error("检验是否可以换班报错", e);
		}
		if (!canExchange) {
			business.setFdStatus(2);
			business.setFdMsg(errorMessage);
			bus.setFdStatus(2);
			// 1.保存流程表单数据
			sysTimeBusinessExService.add(business);
			sendNotifyForFail(business);
			return;
		}
		business.setFdStatus(1);
		bus.setFdStatus(1);
		// 1.保存流程表单数据
		sysTimeBusinessExService.add(business);
		//2、保存申请人和替换人的排班信息
		addBusinessExchange(business);
		//3、发送待阅给申请人和替换人
		sendNotify(business);
	}

	private void addBusinessExchange(SysTimeBusinessEx business)
			throws Exception {
		Map<String,SysOrgElement> targetMap = new HashMap<String,SysOrgElement>();
		targetMap.put("apply", business.getFdApplyPerson());
		targetMap.put("exchange", business.getFdExchangePerson());
		SysTimeOrgElementTime applyOrgElementTime = null;//申请人的排班时间
		SysTimePatchwork applyPatchwork = null;//申请人的补班时间
		SysTimeWork applyWork = null;//申请人的工作日时间
		SysTimeVacation applyVacation = null;//申请人的节假日时间
		SysTimeOrgElementTime exchangeOrgElementTime = null;//替换人的排班时间
		SysTimePatchwork exchangePatchwork = null;//替换人的补班时间
		SysTimeWork exchangeWork = null;//替换人的工作日时间
		SysTimeVacation exchangeVacation = null;//替换人的节假日时间
		//自己与自己换班
		Boolean isSelf = business.getFdApplyPerson().equals(business.getFdExchangePerson()) ? true : false;
		Date applySchduleDate = isSelf ? business.getFdReturnDate() : business.getFdExchangeDate();
		Date exchangeSchduleDate = business.getFdExchangeDate();
		for(String key : targetMap.keySet()) {
			SysOrgElement sysOrgElement = targetMap.get(key);
			Integer type = 1;//type : 1、申请，2、替换
			if("exchange".equals(key)) {
				type = 2;
			}
			Date fdApplyDate = business.getFdExchangeDate();
			
			//自己与自己 替换人时取还班日期
			if(isSelf && Integer.valueOf(2).equals(type)) {
				fdApplyDate = business.getFdReturnDate();
			}
			fdApplyDate = AttendUtil.getDate(fdApplyDate, 0);
			SysTimeElementEx sysTimeElementEx = new SysTimeElementEx();
			sysTimeElementEx.setFdType(type);
			sysTimeElementEx.setFdSchduleDate(fdApplyDate);
			sysTimeElementEx.setFdTimeBusiness(business);
			sysTimeElementEx.setDocCreateTime(new Date());
			
			SysTimeArea sysTimeArea = this.sysTimeCountService.getTimeArea(sysOrgElement);
			List<SysTimeOrgElementTime> orgElementTimeList = sysTimeArea.getOrgElementTimeList();
			for(SysTimeOrgElementTime orgElementTime : orgElementTimeList) {
				if(orgElementTime.getSysOrgElement().getFdId().equals(sysOrgElement.getFdId())) {
					sysTimeElementEx.setFdOrgElementTime(orgElementTime);
					if(Integer.valueOf(1).equals(type)) {
						applyOrgElementTime = orgElementTime;
					}else {
						exchangeOrgElementTime = orgElementTime;
					}
					List<SysTimeWork> sysTimeWorkList = orgElementTime.getSysTimeWorkList();
					List<SysTimePatchwork> sysTimePatchworkList = orgElementTime.getSysTimePatchworkList();
					List<SysTimeVacation> sysTimeVacationList = orgElementTime.getSysTimeVacationList();
					//补班班次
					if(sysTimePatchworkList!=null && !sysTimePatchworkList.isEmpty()) {
						for(SysTimePatchwork patchWork : sysTimePatchworkList) {
							if(patchWork.getFdScheduleDate().getTime()==fdApplyDate.getTime()) {
								sysTimeElementEx.setFdPatchwork(patchWork);
								sysTimeElementEx.setFdWorkType(3);
								if(Integer.valueOf(1).equals(type)) {
									applyPatchwork = patchWork;
								}else {
									exchangePatchwork = patchWork;
								}
								break;
							}
						}
					}
					//节假日
					if(sysTimeVacationList!=null && !sysTimeVacationList.isEmpty()) {
						for(SysTimeVacation vacation : sysTimeVacationList) {
							if(vacation.getFdScheduleDate().getTime()==fdApplyDate.getTime()) {
								sysTimeElementEx.setFdVacation(vacation);
								sysTimeElementEx.setFdWorkType(2);
								if(Integer.valueOf(1).equals(type)) {
									applyVacation = vacation;
								}else {
									exchangeVacation = vacation;
								}
								break;
							}
						}
					}
					//工作日班次
					if(sysTimeWorkList!=null && !sysTimeWorkList.isEmpty()) {
						for(SysTimeWork work : sysTimeWorkList) {
							if(work.getFdScheduleDate().getTime()==fdApplyDate.getTime()) {
								sysTimeElementEx.setFdWork(work);
								sysTimeElementEx.setFdWorkType(1);
								if(Integer.valueOf(1).equals(type)) {
									applyWork = work;
								}else {
									exchangeWork = work;
								}
								break;
							}
						}
					}
				}
			}
			sysTimeElementExService.add(sysTimeElementEx);
		}
		applySchduleDate = AttendUtil.getDate(applySchduleDate, 0);
		exchangeSchduleDate = AttendUtil.getDate(exchangeSchduleDate, 0);
		//如果有替换人的排班信息，则将申请人的排班信息改为替换人的排班信息
		if(exchangeOrgElementTime != null) {
			if(applyWork!=null) {
				applyWork.setFdScheduleDate(applySchduleDate);
				applyWork.setSysTimeOrgElementTime(exchangeOrgElementTime);
				sysTimeWorkService.update(applyWork);
			}
			if(applyPatchwork!=null) {
				applyPatchwork.setFdScheduleDate(applySchduleDate);
				applyPatchwork.setSysTimeOrgElementTime(exchangeOrgElementTime);
				sysTimePatchworkService.getBaseDao().update(applyPatchwork);
			}
			if(applyVacation != null) {
				applyVacation.setFdScheduleDate(applySchduleDate);
				applyVacation.setSysTimeOrgElementTime(exchangeOrgElementTime);
				sysTimeVacationService.update(applyVacation);
			}
		}
		//如果有申请人的排班信息，则将替换人的排班信息改为申请人的排班信息
		if(applyOrgElementTime != null) {
			if(exchangeWork!=null) {
				exchangeWork.setFdScheduleDate(exchangeSchduleDate);
				exchangeWork.setSysTimeOrgElementTime(applyOrgElementTime);
				sysTimeWorkService.update(exchangeWork);
			}
			if(exchangePatchwork!=null) {
				exchangePatchwork.setFdScheduleDate(exchangeSchduleDate);
				exchangePatchwork.setSysTimeOrgElementTime(applyOrgElementTime);
				sysTimePatchworkService.getBaseDao().update(exchangePatchwork);
			}
			if(exchangeVacation != null) {
				exchangeVacation.setFdScheduleDate(exchangeSchduleDate);
				exchangeVacation.setSysTimeOrgElementTime(applyOrgElementTime);
				sysTimeVacationService.update(exchangeVacation);
			}
		}
	}
	
	private void sendNotify(SysTimeBusinessEx business) throws Exception {
		if (business == null) {
			logger.warn("发送换班数据成功通知：换班记录不存在");
			return;
		}
		NotifyContext notifyContext = sysNotifyMainCoreService
				.getContext(null);
		notifyContext.setNotifyType("todo");
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		notifyContext.setKey("sysTimeBusinessExHandel");
		List<SysOrgElement> list = new ArrayList<SysOrgElement>();
		list.add(business.getFdApplyPerson());
		list.add(business.getFdExchangePerson());
		notifyContext.setNotifyTarget(list);
		String docSubject = DateUtil.convertDateToString(business.getFdExchangeDate(), "yyyy-MM-dd") + "换班成功，请知悉！";
		notifyContext.setSubject(docSubject);
		notifyContext.setContent(docSubject);
		notifyContext.setLink(business.getFdDocUrl());
		sysNotifyMainCoreService.sendNotify(business, notifyContext,null);
	}
	
	private void sendNotifyForFail(SysTimeBusinessEx business) throws Exception {
		if (business == null) {
			logger.warn("发送换班数据失败通知：换班记录不存在");
			return;
		}
		logger.warn("发送换班数据失败通知：申请人：" +business.getFdApplyPerson().getFdId());
		NotifyContext notifyContext = sysNotifyMainCoreService
				.getContext(null);
		notifyContext.setNotifyType("todo");
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		notifyContext.setKey("sysTimeBusinessExHandel");
		List<SysOrgElement> list = new ArrayList<SysOrgElement>();
		list.add(business.getFdApplyPerson());
		notifyContext.setNotifyTarget(list);
		String docSubject = DateUtil.convertDateToString(business.getFdExchangeDate(), "yyyy-MM-dd") + "换班失败，失败原因："+business.getFdMsg();
		notifyContext.setSubject(docSubject);
		notifyContext.setContent(docSubject);
		notifyContext.setLink(business.getFdDocUrl());
		sysNotifyMainCoreService.sendNotify(business, notifyContext,null);
	}
	
	private void schedulerJob(String json, SysTimeBusinessEx sysTimeBusinessEx) throws Exception {
		SysQuartzModelContext context = new SysQuartzModelContext();
		// 延后1分钟执行
		
		context.setQuartzCronExpression(new Date((new Date()).getTime()
				+ DateUtil.MINUTE * 1));
		context.setQuartzJobMethod("restat");
		context.setQuartzJobService("sysAttendSynDingService");
		context.setQuartzKey("sysAttendRecalMergeClock");
		context.setQuartzParameter(json);
		context.setQuartzRequired(true);
		context.setQuartzSubject(ResourceUtil.getString("sysAttendCategory.quartz.exchange.subject", "sys-attend"));
		
		sysQuartzCoreService.addScheduler(context, sysTimeBusinessEx);

	}
	
	/**
	 * 重新计算有效记录
	 * 
	 * @param busList
	 * @throws Exception 
	 */
	private void restat(List<SysTimeBusinessEx> busList) throws Exception {
			List<String> statOrgs = getStatOrgList(busList);
			List<Date> statDates = getStatDateList(busList);
			if (statDates.isEmpty() || statOrgs.isEmpty()) {
				return;
			}
			if(logger.isDebugEnabled()) {
				logger.debug("换班重新计算有效考勤记录:statOrgs:"+statOrgs+";statDates:"+statDates);
			}
			logger.warn("换班重新计算有效考勤记录开始");
			sysAttendSynDingService.recalMergeClock(statDates,
					statOrgs);
			logger.warn("换班重新计算有效考勤记录结束");
			//重新生成每日汇总和每月汇总数据
			AttendStatThread task = new AttendStatThread();
			task.setDateList(statDates);
			task.setOrgList(statOrgs);
			task.setFdMethod("restat");
			task.setFdIsCalMissed("true");
			AttendThreadPoolManager manager = AttendThreadPoolManager
					.getInstance();
			if (!manager.isStarted()) {
				manager.start();
			}
			manager.submit(task);
	}

	private List<String> getStatOrgList(List<SysTimeBusinessEx> busList)
			throws Exception {
		List<String> idList = new ArrayList<String>();
		for (SysTimeBusinessEx bus : busList) {
			if(Integer.valueOf(1).equals(bus.getFdStatus())) {
				List<SysOrgElement> orgs = new ArrayList<>();
				orgs.add(bus.getFdApplyPerson());
				orgs.add(bus.getFdExchangePerson());
				List<String> tmpList = sysOrgCoreService
						.expandToPersonIds(orgs);
				idList.addAll(tmpList);
			}
		}
		return idList;
	}

	private List<Date> getStatDateList(List<SysTimeBusinessEx> busList)
			throws Exception {
		List<Date> statDates = new ArrayList<Date>();
		for (SysTimeBusinessEx bus : busList) {
			if(Integer.valueOf(1).equals(bus.getFdStatus())) {
				Date startTime = AttendUtil.getDate(bus.getFdExchangeDate(), 0);
				Date endTime = AttendUtil.getDate(bus.getFdReturnDate(), 0);
				//大于当前时间不重新计算有效记录
				if(startTime != null && startTime.getTime()<=new Date().getTime() && !statDates.contains(startTime)) {
					statDates.add(startTime);
				}
				//大于当前时间不重新计算有效记录
				if(endTime != null && endTime.getTime()<=new Date().getTime() && !statDates.contains(endTime)) {
					statDates.add(endTime);
				}
			}
		}
		return statDates;
	}

	private List<SysTimeBusinessEx> getBusinessList(JSONObject params,
			IBaseModel mainModel) throws Exception {
		List<SysTimeBusinessEx> busList = new ArrayList<SysTimeBusinessEx>();
		try {
			IExtendDataModel model = (IExtendDataModel) mainModel;
			Map<String, Object> modelData = model.getExtendDataModelInfo()
					.getModelData();

			String docSubject = (String) sysMetadataParser
					.getFieldValue(mainModel, "docSubject", false);
			JSONObject returnDateJson = JSONObject
					.parseObject(params.getString("fdReturnDate"));
			JSONObject exchangeDateJson = JSONObject
					.parseObject(params.getString("fdExchangeDate"));
			JSONObject applyPersonJson = JSONObject
					.parseObject(params.getString("fdApplyPerson"));
			JSONObject exchangePersonJson = JSONObject
					.parseObject(params.getString("fdExchangePerson"));

			String returnDateFieldName = (String) returnDateJson.get("value");
			String exchangeDateFieldName = (String) exchangeDateJson.get("value");
			String applyPersonFieldName = (String) applyPersonJson.get("value");
			String exchangePersonFieldName = (String) exchangePersonJson.get("value");

			String returnDateFieldType = (String) returnDateJson.get("model");
			String exchangeDateFieldType = (String) exchangeDateJson.get("model");

			String applyPersonId = null;
			if (modelData.containsKey(applyPersonFieldName)) {
				Object targetsObj = modelData.get(applyPersonFieldName);
				applyPersonId = BeanUtils.getProperty(targetsObj, "id");
			} else {
				SysOrgElement org = (SysOrgElement) PropertyUtils
						.getProperty(mainModel, applyPersonFieldName);
				applyPersonId = org.getFdId();
			}
			
			String exchangePersonId = null;
			if (modelData.containsKey(exchangePersonFieldName)) {
				Object targetsObj = modelData.get(exchangePersonFieldName);
				exchangePersonId = BeanUtils.getProperty(targetsObj, "id");
			} else {
				SysOrgElement org = (SysOrgElement) PropertyUtils
						.getProperty(mainModel, exchangePersonFieldName);
				exchangePersonId = org.getFdId();
			}
			
			if(applyPersonId == null || exchangePersonId==null) {
				logger.warn("换班流程同步考勤事件中申请人或替班人为空!");
				return null;
			}

			Date fdReturnDate = null;
			Date fdExchangeDate = null;

			if ("Date".equals(returnDateFieldType)) {
				if (modelData.containsKey(returnDateFieldName)) {
					fdReturnDate = (Date) modelData.get(returnDateFieldName);
				} else {
					fdReturnDate = (Date) PropertyUtils
							.getProperty(mainModel, returnDateFieldName);
				}
			}

			if ("Date".equals(exchangeDateFieldType)) {

				if (modelData.containsKey(exchangeDateFieldName)) {
					fdExchangeDate = (Date) modelData.get(exchangeDateFieldName);
				} else {
					fdExchangeDate = (Date) PropertyUtils
							.getProperty(mainModel, exchangeDateFieldName);
				}
			}

			if (fdExchangeDate == null) {
				logger.warn("换班流程时间配置不准确:fdExchangeDate：" + fdExchangeDate);
				return null;
			}
			busList.add(getBusinessModel(fdExchangeDate,fdReturnDate, 
					applyPersonId,exchangePersonId, model.getFdId(), docSubject, mainModel));
			return busList;
		} catch (Exception e) {
			logger.error("获取出差数据出错:" + e.getMessage());
			return null;
		}
	}

	private SysTimeBusinessEx getBusinessModel(
			Date fdExchangeDate,Date fdReturnDate, String applyPersonId,String exchangePersonId, String fdProcessId,
			String fdProcessName, IBaseModel fdModel)
			throws Exception {
		SysTimeBusinessEx sysAttendBusiness = getBusinessModel(
				fdExchangeDate,fdReturnDate, fdProcessId, fdProcessName, null, applyPersonId,exchangePersonId, fdModel);
		return sysAttendBusiness;
	}

	/**
	 * @param fdBusStartTime
	 * @param fdBusEndTime
	 * @param fdProcessId
	 * @param fdProcessName
	 * @param person
	 *            指定某个人员
	 * @param targetIds
	 *            人员集合列表 与person参数互斥
	 * @return
	 * @throws Exception
	 */
	private SysTimeBusinessEx getBusinessModel(
			Date fdExchangeDate,Date fdReturnDate, String fdProcessId, String fdProcessName,
			SysOrgElement person, String applyPersonId,String exchangePersonId, IBaseModel model)
			throws Exception {
		SysTimeBusinessEx sysTimeBusinessEx = new SysTimeBusinessEx();
		sysTimeBusinessEx.setFdReturnDate(fdReturnDate);
		sysTimeBusinessEx.setFdExchangeDate(fdExchangeDate);
		sysTimeBusinessEx.setFdFlowId(fdProcessId);
		sysTimeBusinessEx.setFdFlowName(fdProcessName);
		sysTimeBusinessEx.setFdDocUrl(AttendUtil.getDictUrl(model, fdProcessId));
		if (StringUtil.isNotNull(applyPersonId)) {
			sysTimeBusinessEx.setFdApplyPerson(
					sysOrgCoreService.findByPrimaryKey(applyPersonId));
		}
		
		if (StringUtil.isNotNull(exchangePersonId)) {
			sysTimeBusinessEx.setFdExchangePerson(
					sysOrgCoreService.findByPrimaryKey(exchangePersonId));
		}
		sysTimeBusinessEx.setFdChangeType(1);
		SysOrgPerson docCreator = (SysOrgPerson) sysMetadataParser
				.getFieldValue(model, "docCreator", false);
		sysTimeBusinessEx.setDocCreator(docCreator);
		return sysTimeBusinessEx;
	}

	public void setSysAttendCategoryService(
			ISysAttendCategoryService sysAttendCategoryService) {
		this.sysAttendCategoryService = sysAttendCategoryService;
	}

	public void setSysAttendStatJobService(
			ISysAttendStatJobService sysAttendStatJobService) {
		this.sysAttendStatJobService = sysAttendStatJobService;
	}
	
	public void setSysTimeBusinessExService(
			ISysTimeBusinessExService sysTimeBusinessExService) {
		this.sysTimeBusinessExService =sysTimeBusinessExService;
	}
	public void setSysTimeElementExService(
			ISysTimeElementExService sysTimeElementExService) {
		this.sysTimeElementExService = sysTimeElementExService;
	}
	public void setSysTimeCountService(
			ISysTimeCountService sysTimeCountService) {
		this.sysTimeCountService = sysTimeCountService;
	}

	public void setSysTimeWorkService(ISysTimeWorkService sysTimeWorkService) {
		this.sysTimeWorkService = sysTimeWorkService;
	}

	public void setSysTimePatchworkService(ISysTimePatchworkService sysTimePatchworkService) {
		this.sysTimePatchworkService = sysTimePatchworkService;
	}

	public void setSysTimeVacationService(ISysTimeVacationService sysTimeVacationService) {
		this.sysTimeVacationService = sysTimeVacationService;
	}

	public void setSysAttendSynDingService(ISysAttendSynDingService sysAttendSynDingService) {
		this.sysAttendSynDingService = sysAttendSynDingService;
	}

}
