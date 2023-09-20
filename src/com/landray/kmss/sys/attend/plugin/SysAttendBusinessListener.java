package com.landray.kmss.sys.attend.plugin;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.engine.task.NodeInstanceUtils;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzCoreService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzModelContext;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 出差流程结束监听事件
 * @author 王京
 */
public class SysAttendBusinessListener extends SysAttendListenerCommonImp
		implements IEventListener, IEventMulticasterAware,
		ApplicationListener<Event_Common> {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendBusinessListener.class);

	private ISysQuartzCoreService sysQuartzCoreService;
	public void setSysQuartzCoreService(
			ISysQuartzCoreService sysQuartzCoreService) {
		this.sysQuartzCoreService = sysQuartzCoreService;
	};

	private IEventMulticaster multicaster;

	@Override
	public void setEventMulticaster(IEventMulticaster multicaster) {
		this.multicaster = multicaster;

	}

	@Override
	public void handleEvent(EventExecutionContext execution, String parameter)
			throws Exception {
		logger.debug(
				"receive SysAttendBusinessListener,parameter=" + parameter);
		String routeType = execution.getNodeInstance().getFdRouteType();
		String docStatus = execution.getExecuteParameters()
				.getExpectMainModelStatus();
		String processFlag = execution.getProcessParameters().getInstanceParamValue(execution.getProcessInstance(),PROCESS_FLAG_KEY);
		if (PROCESS_FLAG_RUN_VALUE.equals(processFlag) || PROCESS_FLAG_ERROR_VALUE.equals(processFlag)) {
			//新流程事件不进老流程处理
			return;
		}
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
					List<SysAttendBusiness> busList = getBusinessList(params,
							mainModel);
					if (busList == null || busList.isEmpty()) {
						logger.warn(
								"出差流程数据配置不准确,忽略处理!parameter:"
										+ parameter);
						return;
					}

//					JSONArray arr = new JSONArray();
					if (busList != null && !busList.isEmpty()) {
						// 1.保存数据
						List<SysAttendBusiness> statBusiness=new ArrayList<>();
						for (SysAttendBusiness bus : busList) {
//							JSONObject busJson = new JSONObject();
//							String targetIds = "";
//							for(SysOrgElement ele : bus.getFdTargets()) {
//								targetIds += ";" + ele.getFdId();
//							}
							//验证是否有考勤组
							boolean isHave = checkUserHaveCategory(bus.getFdTargets(),bus.getFdBusStartTime());
//							targetIds = targetIds.replaceFirst(";", "");
//							busJson.accumulate("startTime", bus.getFdBusStartTime().getTime());
//							busJson.accumulate("endTime", bus.getFdBusEndTime().getTime());
//							busJson.accumulate("targetIds", targetIds);
//							arr.add(busJson);
							//在考勤组中的人员，出差才统计
							if(isHave) {
								statBusiness.add(bus);
								saveAttendBusiness(bus, execution.getMainModel());
							}else{
								logger.warn(
										"人员没有需要考勤的配置，忽略考勤处理:" + mainModel.getFdId() + ";parameter=" + parameter);
							}
						}
						// 2.重新统计
						if(CollectionUtils.isNotEmpty(statBusiness)) {
							reStatistics(busList,this.multicaster);
						}
					}
				} else {
					logger.warn(
							"同个流程只执行一次,忽略此次操作!流程id:" + mainModel.getFdId()
									+ ";parameter=" + parameter);
				}
			}
		}
	}

	private void schedulerJob(String json, SysAttendBusiness sysAttendBusiness) throws Exception {
		SysQuartzModelContext context = new SysQuartzModelContext();
		// 延后5分钟执行
		context.setQuartzCronExpression(new Date((new Date()).getTime()
				+ DateUtil.MINUTE * 5));
		context.setQuartzJobMethod("restat");
		context.setQuartzJobService("sysAttendStatJobService");
		context.setQuartzKey("restat");
		context.setQuartzParameter(json);
		context.setQuartzRequired(true);
		context.setQuartzSubject(ResourceUtil.getString("sysAttendBusiness.sysQuartz.business.to.attend", "sys-attend"));

		sysQuartzCoreService.addScheduler(context, sysAttendBusiness);

	}

	/**
	 * 保存出差流程业务数据到考勤
	 * @param bus
	 * @param model
	 * @throws Exception
	 */
	private void saveAttendBusiness(SysAttendBusiness bus, IBaseModel model) throws Exception {
		List<SysOrgElement> targets = getTargetsFromAttendBusiness(bus);
		Date fdBusStartTime = bus.getFdBusStartTime();
		Date fdBusEndTime = bus.getFdBusEndTime();
		List<SysOrgPerson> personList = getSysOrgCoreService().expandToPerson(targets);
		List<String> orgIdList = new ArrayList<String>();
		for(SysOrgPerson p : personList){
			orgIdList.add(p.getFdId());
		}
		// 获取用户的出差流程
		List<Integer> fdTypes = new ArrayList<Integer>();
		fdTypes.add(4);
		Date endTime = new Date(fdBusEndTime.getTime());
		endTime.setSeconds(endTime.getSeconds() + 1);
		List<SysAttendBusiness> busList = this.getSysAttendBusinessService()
				.findBussList(orgIdList, fdBusStartTime,
				endTime, fdTypes);
		for (SysOrgElement ele : targets) {
			SysAttendBusiness business = this.getBusinessModel(
					bus.getFdBusStartTime(), bus.getFdBusEndTime(),
					bus.getFdProcessId(), bus.getFdProcessName(), ele, null, model);
			List<SysAttendBusiness> userBusList = getUserBusList(busList,
					ele);
			boolean isUpdate=false;
			if (!userBusList.isEmpty()) {
				for (SysAttendBusiness sysAttendBusiness : userBusList) {
					isUpdate = checkRepeatUserBusiness(sysAttendBusiness, fdBusStartTime,
							fdBusEndTime);
					if (isUpdate) {
						break;
					}
				}
			}
			if (isUpdate) {
				logger.warn("出差时间重复!流程id:" + bus.getFdProcessId());
				business.setFdDelFlag(1);
				// 1.保存流程表单数据
				getSysAttendBusinessService().add(business);
				sendNotify(business,ele);
				continue;
			}
			// 1.保存流程表单数据
			getSysAttendBusinessService().add(business);
			// 2.更新打卡记录
			updateSysAttendMainByBusiness(business);
		}
	}

	/**
	 * 验证出差流程是否重复。
	 * @param bus
	 * @throws Exception 直接抛出异常
	 */
	protected boolean checkAttendBusiness(SysAttendBusiness bus) throws Exception {
		//当前流程的所有用户获取
		List<SysOrgElement> targets = getTargetsFromAttendBusiness(bus);
		Date fdBusStartTime = bus.getFdBusStartTime();
		Date fdBusEndTime = bus.getFdBusEndTime();
		List<SysOrgPerson> personList = getSysOrgCoreService().expandToPerson(targets);
		List<String> orgIdList = new ArrayList<String>();
		for(SysOrgPerson p : personList){
			orgIdList.add(p.getFdId());
		}
		// 获取用户的出差流程
		List<Integer> fdTypes = new ArrayList<Integer>();
		fdTypes.add(4);
		Date endTime = new Date(fdBusEndTime.getTime());
		endTime.setSeconds(endTime.getSeconds() + 1);
		List<SysAttendBusiness> busList = this.getSysAttendBusinessService()
				.findBussList(orgIdList, fdBusStartTime,
						endTime, fdTypes,true);
		boolean isRepeat=false;
		String content ="";
		//根据当前流程中的人员对比其这段时间内的出差流程
		for (SysOrgElement ele : targets) {
			List<SysAttendBusiness> userBusList = getUserBusList(busList,ele);
			if (!userBusList.isEmpty()) {
				for (SysAttendBusiness sysAttendBusiness : userBusList) {
					isRepeat = checkRepeatUserBusiness(sysAttendBusiness, fdBusStartTime,fdBusEndTime);
					if (isRepeat) {
						content =  String.format("%s(%s-%s)",sysAttendBusiness.getFdProcessName(),
								sysAttendBusiness.getFdBusStartTime(),
								sysAttendBusiness.getFdBusEndTime()
								);
						break;
					}
				}
			}
		}
		if (isRepeat) {
			//存在 则抛出异常
			String tip = ResourceUtil.getString("sys-attend:sysAttendBusiness.check.trip3");
			if (tip != null) {
				tip = tip.replace("%sys-attend:sysAttend.business.repeat%", content);
			}
			sendNotify(bus, UserUtil.getKMSSUser().getPerson(),tip,tip);
			return false;
		}

		return true;
	}

	/**
	 * 获取出差人员，包括随从，做了去重处理
	 * @param bus
	 * @return
	 */
	private List<SysOrgElement> getTargetsFromAttendBusiness(SysAttendBusiness bus) {
		Map<String, SysOrgElement> sysOrgElementMap = new HashMap<String, SysOrgElement>();
		for(SysOrgElement sysOrgElement : bus.getFdTargets()){
			sysOrgElementMap.put(sysOrgElement.getFdId(), sysOrgElement);
		}
		return new ArrayList<SysOrgElement>(sysOrgElementMap.values());
	}

	/**
	 * 检查出差流程时间重复
	 *
	 * @param bus
	 *            出差对象
	 * @param startTime
	 *            出差开始时间
	 * @param endTime
	 *            出差结束时间
	 * @return
	 * @throws Exception
	 */
	private boolean checkRepeatUserBusiness(SysAttendBusiness bus,
			Date startTime, Date endTime)
			throws Exception {
		boolean result = false;
		Date fdBusStartTime = bus.getFdBusStartTime();
		Date fdBusEndTime = bus.getFdBusEndTime();
		// 实际时间区间
		if (fdBusEndTime.getTime() > endTime.getTime()
				&& fdBusStartTime.getTime() < startTime.getTime()) {
			result = true;
		} else if (fdBusEndTime.getTime() > startTime.getTime()
				&& fdBusStartTime.getTime() < startTime.getTime()) {
			result = true;
		} else if (fdBusEndTime.getTime() <= endTime.getTime()
				&& fdBusStartTime.getTime() >= startTime.getTime()) {
			result = true;
		} else if (fdBusEndTime.getTime() > endTime.getTime()
				&& fdBusStartTime.getTime() < endTime.getTime()) {
			result = true;
		}
		return result;
	}

	// 获取指定用户的出差流程,并过滤重复
	private List getUserBusList(List<SysAttendBusiness> busList,
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

	private void sendNotify(SysAttendBusiness business,SysOrgElement ele) throws Exception {
		if (business == null) {
			logger.warn("发送出差数据失败通知：出差记录不存在");
			return;
		}
		logger.warn("发送出差数据失败通知：出差人：" +ele.getFdName()
				+ "，出差记录Id：" + business.getFdId());
		NotifyContext notifyContext = getSysNotifyMainCoreService()
				.getContext(null);
		notifyContext.setNotifyType("todo");
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		notifyContext.setKey("sysAttendBusinessHandel");
		List<SysOrgElement> list = new ArrayList<SysOrgElement>();
		list.add(ele);
		notifyContext.setNotifyTarget(list);
		notifyContext.setSubject(ResourceUtil
				.getString("sysAttendMain.busniess.notify", "sys-attend"));
		notifyContext.setContent(ResourceUtil
				.getString("sysAttendMain.busniess.notify", "sys-attend"));
		notifyContext.setLink(business.getDocUrl());
		getSysNotifyMainCoreService().sendNotify(business, notifyContext,null);
	}


	/**
	 * 获取相关人员
	 * @param targetsFieldName
	 * @param modelData
	 * @param mainModel
	 * @return
	 * @throws Exception
	 */
	private LinkedHashMap<String,Integer> getPersonIds(String targetsFieldName,Map<String, Object> modelData,IBaseModel mainModel) throws Exception{
		//结果以每个人在哪行存储，-1表示单独的控件存储。其他值表示明细表存储
		LinkedHashMap<String,Integer> personInfoMap=new LinkedHashMap<>();
		if (targetsFieldName.indexOf(".") == -1) {
			String targetIds = null;
			//普通表单内
			if (modelData.containsKey(targetsFieldName)) {
				Object targetsObj = modelData.get(targetsFieldName);
				targetIds = BeanUtils.getProperty(targetsObj, "id");
			} else {
				SysOrgElement org = (SysOrgElement) PropertyUtils.getProperty(mainModel, targetsFieldName);
				targetIds = org.getFdId();
			}
			if(StringUtil.isNotNull(targetIds)){
				String[] targetArr =targetIds.split(";");
				for (String targetItem: targetArr ) {
					personInfoMap.put(targetItem,-1);
				}
			}
		} else{
			//明细表单内人员
			String detailName = targetsFieldName.split("\\.")[0];
			String targetName = targetsFieldName.split("\\.")[1];
			List<HashMap<String, Object>> detailList = (ArrayList<HashMap<String, Object>>) modelData.get(detailName);
			for (int k = 0; k < detailList.size(); k++) {
				HashMap detail = detailList.get(k);
				Object targetsObj = detail.get(targetName);
				String targetIds = BeanUtils.getProperty(targetsObj, "id");
				if(StringUtil.isNotNull(targetIds)){
					String[] targetArr =targetIds.split(";");
					for (String targetItem: targetArr ) {
						personInfoMap.put(targetItem,k);
					}
				}
			}
		}
		return personInfoMap;
	}

	/**
	 * 获取出差日期的配置
	 * @param startFieldType
	 * @param startFieldName
	 * @param modelData
	 * @param mainModel
	 * @param isEnd
	 * @return
	 * @throws Exception
	 */
	private LinkedHashMap<Integer,Date> getDateInfo(String startFieldType,String startFieldName,Map<String, Object> modelData,IBaseModel mainModel,boolean isEnd) throws Exception {
		//结果以每行的值为存储，-1表示单独的控件存储。其他值表示明细表存储
		LinkedHashMap<Integer,Date> dateInfoMap=new LinkedHashMap<>();
		if ("Date[]".equals(startFieldType) || "DateTime[]".equals(startFieldType) ) {
			if (startFieldName.indexOf(".") == -1) {
				logger.warn("出差流程-时间配置不准确");
				return null;
			}
			String detailName = startFieldName.split("\\.")[0];
			String startName = startFieldName.split("\\.")[1];
			List<HashMap<String, Object>> detailList = (ArrayList<HashMap<String, Object>>) modelData.get(detailName);
			for (int k = 0; k < detailList.size(); k++) {
				HashMap detail = detailList.get(k);
				if (detail.get(startName) == null ) {
					logger.warn( "出差流程时间配置不准确:" );
					continue;
				}
				Date time = (Date) detail.get(startName);
				if(isEnd && "Date[]".equals(startFieldType)) {
					dateInfoMap.put(k,  AttendUtil.getDayLastTime(time));
				}else {
					dateInfoMap.put(k,  time);
				}
			}
		} else {
			Date fdBusStartTime = null;
			if ("Date".equals(startFieldType) || "DateTime".equals(startFieldType)) {
				if (modelData.containsKey(startFieldName)) {
					fdBusStartTime = (Date) modelData.get(startFieldName);
				} else {
					fdBusStartTime = (Date) PropertyUtils.getProperty(mainModel, startFieldName);
				}
				if (isEnd && "Date".equals(startFieldType)) {
					fdBusStartTime = AttendUtil.getDayLastTime(fdBusStartTime);
				}
				dateInfoMap.put(-1,fdBusStartTime);
			}
		}
		return dateInfoMap;
	}

	/**
	 * 出差事件的数据解析
	 * @param params
	 * @param mainModel
	 * @return
	 * @throws Exception
	 */
	protected List<SysAttendBusiness> getBusinessList(JSONObject params,
			IBaseModel mainModel) throws Exception {
		List<SysAttendBusiness> busList = new ArrayList<SysAttendBusiness>();
		try {
			IExtendDataModel model = (IExtendDataModel) mainModel;
			Map<String, Object> modelData = model.getExtendDataModelInfo().getModelData();
			String docSubject = (String) getSysMetadataParser().getFieldValue(mainModel, "docSubject", false);

			JSONObject startTimeJson = JSONObject.fromObject(params.get("fdBusStartTime"));
			JSONObject endTimeJson = JSONObject.fromObject(params.get("fdBusEndTime"));
			JSONObject targetsJson = JSONObject.fromObject(params.get("fdBusTargets"));
			/**出差人*/
			String targetsFieldName = (String) targetsJson.get("value");
			LinkedHashMap<String,Integer> targetUserMap= getPersonIds(targetsFieldName,modelData,mainModel);
			if(targetUserMap ==null || targetUserMap.size() ==0){
				logger.warn("出差流程-出差人配置不正确" );
				return null;
			}
			/***
			 * 同行人
			 */
			if(params.containsKey("fdBusOthers")) {
				String fdBusOthers=params.getString("fdBusOthers");
				if(StringUtil.isNotNull(fdBusOthers)) {
					JSONObject othersJson = JSONObject.fromObject(fdBusOthers);
					String othersFieldName = (String) othersJson.get("value");
					LinkedHashMap<String,Integer> otherTargetUserMap= getPersonIds(othersFieldName,modelData,mainModel);
					//合并出差人跟同行人
					if(targetUserMap !=null && targetUserMap.size() > 0){
						targetUserMap.putAll(otherTargetUserMap);
					}
				}
			}
			/***
			 * 出差开始时间
			 */
			String startFieldName = (String) startTimeJson.get("value");
			String startFieldType = (String) startTimeJson.get("model");
			LinkedHashMap<Integer,Date> beginDateMap=getDateInfo(startFieldType,startFieldName,modelData,mainModel,false);
			if(beginDateMap ==null || beginDateMap.size() ==0){
				logger.warn("出差流程-出差开始时间配置不正确" );
				return null;
			}
			/**
			 * 出差结束时间
			 */
			String endFieldType = (String) endTimeJson.get("model");
			String endFieldName = (String) endTimeJson.get("value");
			LinkedHashMap<Integer,Date> endDateMap=getDateInfo(endFieldType,endFieldName,modelData,mainModel,true);
			if(endDateMap ==null || endDateMap.size() ==0){
				logger.warn("出差流程-出差结束时间配置不正确" );
				return null;
			}
			/**
			 * 根据人员来组装数据
			 */
			final String [] keys={"userId","beginDate","endDate"};
			List<Map<String,Object> > personBusData =new ArrayList<>();
			for (Map.Entry<String,Integer> personMap: targetUserMap.entrySet()) {
				//人员ID
				String personId =personMap.getKey();
				//人员在表单中 行号，-1表示单独控件
				Integer personRow =personMap.getValue();
				Map<String,Object> personLastInfoMap =new HashMap<>();
				personLastInfoMap.put(keys[0],personId);
				if(personRow > -1){
					//处理明细表中的封装
					Date beginDate =beginDateMap.get(personRow);
					Date endDate =endDateMap.get(personRow);
					//如果开始时间-结束时间不是配置的明细表，则从单独表单中，单独表单中取不到则不处理
					if(beginDate ==null){
						beginDate =beginDateMap.get(-1);
					}
					if(endDate ==null){
						endDate =endDateMap.get(-1);
					}
					if (beginDate == null || endDate == null) {
						logger.warn("出差流程-时间配置不准确");
						continue;
					}
					personLastInfoMap.put(keys[1],beginDate);
					personLastInfoMap.put(keys[2],endDate);
					personBusData.add(personLastInfoMap);
				} else {
					//人员是单独控件配置
					Date beginDate =beginDateMap.get(personRow);
					Date endDate =endDateMap.get(personRow);

					if(beginDate !=null){
						personLastInfoMap.put(keys[1],beginDate);
						if(endDate !=null){
							//场景1，开始时间是控件中的，结束时间也是控件中的
							personLastInfoMap.put(keys[2],endDate);
							personBusData.add(personLastInfoMap);
						} else{
							//场景2，开始时间是控件中的，结束时间是明细表中的
							for (Map.Entry<Integer,Date> beginDateInfo: endDateMap.entrySet()) {
								personLastInfoMap =new HashMap<>();
								personLastInfoMap.put(keys[0],personId);
								personLastInfoMap.put(keys[1],beginDate);
								personLastInfoMap.put(keys[2],beginDateInfo.getValue());
								personBusData.add(personLastInfoMap);
							}
						}
					}else{
						for (Map.Entry<Integer, Date> beginDateInfo : beginDateMap.entrySet()) {
							personLastInfoMap = new HashMap<>();
							personLastInfoMap.put(keys[0], personId);
							personLastInfoMap.put(keys[1], beginDateInfo.getValue());
							//场景3，开始时间是明细表中的，结束时间是控件中的
							if(endDate !=null) {
								personLastInfoMap.put(keys[2], endDate);
							} else {
								//场景4，开始时间，结束时间 都是明细表中
								Date endDateTemp =endDateMap.get(beginDateInfo.getKey());
								if(endDateTemp ==null){
									continue;
								}
								personLastInfoMap.put(keys[2], endDateTemp);
							}
							personBusData.add(personLastInfoMap);
						}
					}
				}
			}
			if(CollectionUtils.isNotEmpty(personBusData)){
				for (Map<String,Object> personLastInfoMap : personBusData) {
					String personId = (String) personLastInfoMap.get(keys[0]);
					Object beginDateObj = personLastInfoMap.get(keys[1]);
					Object endDateObj = personLastInfoMap.get(keys[2]);
					if(beginDateObj ==null || endDateObj ==null || personId ==null){
						logger.warn("出差流程-参数配置不准确");
						continue;
					}
					Date beginDate =(Date)beginDateObj;
					Date endDate =(Date)endDateObj;
					if(beginDate.getTime() > endDate.getTime()){
						logger.warn("出差流程-参数配置不准确,开始时间不能大于结束时间");
						continue;
					}
					busList.add(getBusinessModel(beginDate,endDate,personId, model.getFdId(), docSubject, mainModel));
				}
			}
			return busList;
		} catch (Exception e) {
			logger.error("获取出差数据出错:" + e.getMessage());
			return null;
		}
	}

	private SysAttendBusiness getBusinessModel(Date fdBusStartTime,
			Date fdBusEndTime, String targetIds, String fdProcessId,
			String fdProcessName, IBaseModel fdModel)
			throws Exception {
		SysAttendBusiness sysAttendBusiness = getBusinessModel(fdBusStartTime,
				fdBusEndTime, fdProcessId, fdProcessName, null, targetIds, fdModel);
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
	private SysAttendBusiness getBusinessModel(Date fdBusStartTime,
			Date fdBusEndTime, String fdProcessId, String fdProcessName,
			SysOrgElement person, String targetIds, IBaseModel model)
			throws Exception {
		SysAttendBusiness sysAttendBusiness = new SysAttendBusiness();
		sysAttendBusiness.setFdBusStartTime(fdBusStartTime);
		sysAttendBusiness.setFdBusEndTime(fdBusEndTime);
		sysAttendBusiness.setFdProcessId(fdProcessId);
		sysAttendBusiness.setFdProcessName(fdProcessName);
		sysAttendBusiness.setDocUrl(AttendUtil.getDictUrl(model, fdProcessId));
		if (StringUtil.isNotNull(targetIds)) {
			sysAttendBusiness.setFdTargets(
					getSysOrgCoreService().findByPrimaryKeys(targetIds.split(";")));
		} else {
			List<SysOrgElement> targets = new ArrayList<SysOrgElement>();
			targets.add(person);
			sysAttendBusiness.setFdTargets(targets);
		}
		sysAttendBusiness.setFdType(4);
		sysAttendBusiness.setDocCreateTime(new Date());
		return sysAttendBusiness;
	}

	@Override
	public void onApplicationEvent(Event_Common event) {
		try {
			if ("regenUserAttendMain".equals(event.getSource().toString())) {
				getSysAttendCategoryService().clearSignTimesCache();
				logger.debug("receive regenUserAttendMain request...");
				Map params = ((Event_Common) event).getParams();
				String fdCategoryId = "";
				String fdTimeAreaChange = "";
				if (params != null && params.containsKey("fdCategoryId")) {
					fdCategoryId = (String) params.get("fdCategoryId");
				}
				if (params != null && params.containsKey("fdTimeAreaChange")) {
					fdTimeAreaChange = (String) params.get("fdTimeAreaChange");
				}
				// 排班制时,排班信息调整检测
				if ("true".equals(fdTimeAreaChange)) {
					List<SysAttendCategory> cateList = this.getSysAttendCategoryService()
							.findCategorysByTimeArea();
					if (cateList == null || cateList.isEmpty()) {
						logger.debug("没有排班考勤组,忽略处理!");
						return;
					}
					List<SysOrgElement> areaMembers = null;
					if (params != null && params.containsKey("areaMembers")) {
						areaMembers = (List<SysOrgElement>) params
								.get("areaMembers");
					}
					if (areaMembers != null) {
						List<String> cateIds = new ArrayList<>();
						for (SysAttendCategory category : cateList) {
							cateIds.add(category.getFdId());
						}
						this.addOrUpdateUserTrip(areaMembers, cateIds,
								new Date());
						logger.debug(
								"regenUserAttendMain finish,相应排班考勤组有:"
										+ cateList);
						return;
					}
					for (SysAttendCategory category : cateList) {
						this.addOrUpdateUserTrip(category.getFdId(),
								new Date());
					}
					logger.debug(
							"regenUserAttendMain finish,相应排班考勤组有:" + cateList);
					return;
				}

				this.addOrUpdateUserTrip(fdCategoryId, new Date());
				logger.debug("regenUserAttendMain finish...");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("重新生成出差数据事件报错:" + e.getMessage(), e);
		}
	}

	/**
	 * 重新生成用户出差记录
	 *
	 * @param categoryId
	 *            考勤组ID,为空则默认生成所有考勤组用户出差记录
	 * @param date
	 *            重新生成开始日期,目前只支持重新生成当天之后的出差记录
	 * @throws Exception
	 */
	public void addOrUpdateUserTrip(String categoryId, Date date)
			throws Exception {
		if (date == null) {
			date = new Date();
		}
		date = AttendUtil.getDate(date, 0);
		List<String> personList = null;
		if (StringUtil.isNotNull(categoryId)) {
			personList = this.getSysAttendCategoryService().getAttendPersonIds(categoryId,date);
		}
		List<SysAttendBusiness> bussList = getSysAttendBusinessService().findList(personList, date, 4);
		List<String> userList = getTripUsers(bussList, personList);
		if (userList == null || userList.isEmpty()) {
			return;
		}

		for (int i = 0; i < userList.size(); i++) {
			String docCreatorId = (String) userList.get(i);
			// 用户所有出差记录
			List<SysAttendMain> mainList = getUserTripAttendMainList(
					docCreatorId, date);
			List<SysAttendBusiness> busList = getUserTripBuss(bussList,
					docCreatorId);
			for (SysAttendBusiness buss : busList) {
				Date fdStartTime = buss.getFdBusStartTime();
				Date fdEndTime = buss.getFdBusEndTime();
				if (fdStartTime.before(date)) {
					fdStartTime = date;
				}
				if (fdEndTime.before(date)) {
					continue;
				}
				this.updateUserTrip(buss, fdStartTime, fdEndTime, mainList);
			}
		}
	}

	/**
	 * 重新生成用户出差记录
	 * @param areaMembers 区域组人员ID
	 * @param cateIds 考勤组ID,为空则默认生成所有考勤组用户出差记录
	 * @param date  重新生成开始日期,目前只支持重新生成当天之后的出差记录
	 * @throws Exception
	 */
	public void addOrUpdateUserTrip(List<SysOrgElement> areaMembers,
			List<String> cateIds, Date date)
			throws Exception {
		if (date == null) {
			date = new Date();
		}
		date = AttendUtil.getDate(date, 0);
		List<String> personList = null;
		if (cateIds != null) {
			personList = this.getSysAttendCategoryService().getTimeAreaAttendPersonIds(areaMembers, cateIds,date);
		}
		List<SysAttendBusiness> bussList = getSysAttendBusinessService().findList(personList, date, 4);
		List<String> userList = getTripUsers(bussList, personList);
		if (userList == null || userList.isEmpty()) {
			logger.warn("重新计算用户出差考勤记录时,用户记录为空,不需处理!");
			return;
		}

		for (int i = 0; i < userList.size(); i++) {
			String docCreatorId = (String) userList.get(i);
			// 用户所有出差记录
			List<SysAttendMain> mainList = getUserTripAttendMainList(docCreatorId, date);
			List<SysAttendBusiness> busList = getUserTripBuss(bussList,docCreatorId);
			for (SysAttendBusiness buss : busList) {
				Date fdStartTime = buss.getFdBusStartTime();
				Date fdEndTime = buss.getFdBusEndTime();
				if (fdStartTime.before(date)) {
					fdStartTime = date;
				}
				if (fdEndTime.before(date)) {
					continue;
				}
				this.updateUserTrip(buss, fdStartTime, fdEndTime, mainList);
			}
		}
	}

	private boolean updateUserTrip(SysAttendBusiness buss,
			Date fdStartTime, Date fdEndTime, List<SysAttendMain> mainList)
			throws Exception {
		if (mainList == null || mainList.isEmpty()) {
			return false;
		}
		SysOrgPerson person = mainList.get(0).getDocCreator();
		boolean fdTrip = AttendUtil.getAttendTrip();
		List<Date> dateList = getDateList(fdStartTime, fdEndTime);
		if (dateList.size() < 2) {
			return false;
		}
		// 每天
		for (int i = 0; i < dateList.size() - 1; i++) {
			Date startTime = dateList.get(i);
			SysAttendCategory category = getSysAttendCategoryService().getCategoryInfo(person, startTime, true);
			if(category==null) {
				logger.warn("该用户没有配置相应考勤组,同步数据到考勤考勤记录忽略!userId:"
						+ person.getFdId());
				continue;
			}
			String categoryId = category.getFdId();
			Date endTime = dateList.get(i + 1);
			Date nextEndTime = null;
			if((i+2) < dateList.size())
			{
				nextEndTime = dateList.get(i+2);
			}
			Date startDate = AttendUtil.getDate(startTime, 0);
			// 出差记录
			List<SysAttendMain> recordList = getUserTripByDay(mainList, startDate);
			if (!fdTrip) {// 按工作日计算
				boolean isRestDay = isRestDay(startDate, category,person);
				if (isRestDay) {
					if (!recordList.isEmpty()) {
						for (SysAttendMain main : recordList) {
							main.setDocStatus(1);
							main.setDocAlterTime(new Date());
							main.setFdAlterRecord("出差按工作日计算,出差数据置为无效");
							this.getSysAttendMainService().getBaseDao().update(main);
						}
						logger.warn(
								"出差按工作日计算,该用户对应考勤组当天为休息日,出差数据置为无效!userName:"
										+ person.getFdName() + ";date:"
										+ startDate + ";考勤组:"
										+ category.getFdName());
					}
					continue;// 只能工作日出差
				} else {
					if (!recordList.isEmpty()) {
						// 若切换了考勤组,则删除无效记录,需重新生成
						boolean isDel = this.deleteTrip(recordList, categoryId);
						// 存在记录则不再调整
						if (!isDel) {
							continue;
						}
					}
				}
			} else {
				// 按自然日
				if (!recordList.isEmpty()) {
					// 若切换了考勤组,则删除无效记录,需重新生成
					boolean isDel = this.deleteTrip(recordList, categoryId);
					if (!isDel) {
						continue;
					}
				}
			}

			// 用户打卡班次
			List<Map<String, Object>> signTimeList = getSignTimeList(category, startTime,  person,true);
			// 某个时间段内
			signTimeList = filterSignTimeList(signTimeList,
					startTime, endTime);
			for (Map<String, Object> signTimeConfiguration : signTimeList) {
				// 新增
				//跨天时，有可能来自于补昨天晚班晚班卡的需要
				if(this.isOverTimeType(signTimeConfiguration)){
					//如果结束时间为当天的零点，需要补今天晚班的跨天卡
					if(isLastSchedulingOfCurrentday(startDate, endTime, nextEndTime, signTimeConfiguration)){
						System.out.println("addMain -- 33333");
						addMain(buss, person, category, startDate, signTimeConfiguration,4);
					}
					//如果开始时间为出差开始时间并于上一天的末班下班时间内
					if(!AttendUtil.isZeroDay(startTime)
							&& isLastSchedulingOfYesterday(startTime, startDate, signTimeConfiguration)){
						System.out.println("addMain -- 44444");
						addMain(buss, person, category, AttendUtil.getDate(startDate, -1), signTimeConfiguration,4);
					}
				}
				else {
					System.out.println("addMain -- 55555");
					addMain(buss, person, category, startDate, signTimeConfiguration,4);
				}
			}

		}
		return false;
	}

	/**
	 * 获取有效出差用户集合
	 *
	 * @param bussList
	 *            出差流程记录
	 * @param personList
	 *            某考勤组对应的用户集合
	 * @return
	 */
	private List getTripUsers(List<SysAttendBusiness> bussList,
			List<String> personList) {
		if (personList == null) {
			personList = new ArrayList();
		}
		Set<String> userSet = new HashSet<String>();
		for (SysAttendBusiness buss : bussList) {
			List<SysOrgElement> targets = buss.getFdTargets();
			if (targets == null || targets.isEmpty()) {
				continue;
			}
			for (SysOrgElement org : targets) {
				if (personList.isEmpty()) {
					userSet.add(org.getFdId());
				} else {
					if (personList.contains(org.getFdId())) {
						userSet.add(org.getFdId());
					}
				}
			}
		}
		List<String> orgList = new ArrayList<String>(userSet);
		return orgList;
	}

	/**
	 * 切换考勤组时,删除出差记录
	 *
	 * @param recordList
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	private boolean deleteTrip(List<SysAttendMain> recordList,
			String categoryId)
			throws Exception {
		boolean isDel = false;
		for (SysAttendMain main : recordList) {
			if (!main.getFdHisCategory().getFdId()
					.equals(categoryId)) {
				main.setDocStatus(1);
				main.setDocAlterTime(new Date());
				main.setFdAlterRecord("用户考勤组变更出差数据置为无效");
				this.getSysAttendMainService().getBaseDao()
						.update(main);
				logger.warn(
						"用户考勤组变更出差数据置为无效!userId:"
								+ main.getDocCreator().getFdId() + ";date:"
								+ main.getDocCreateTime() + ";categoryId:"
								+ categoryId);
				isDel = true;
			}
		}
		return isDel;
	}

	/**
	 * 获取某天的出差记录
	 *
	 * @param mainList
	 * @param date
	 * @return
	 */
	private List getUserTripByDay(List<SysAttendMain> mainList, Date date) {
		Set<SysAttendMain> recordSet = new HashSet<SysAttendMain>();
		for (SysAttendMain main : mainList) {
			// 判断是否同一天
			if (AttendUtil.getDate(date, 0)
					.equals(AttendUtil.getDate(main.getDocCreateTime(), 0))) {
				recordSet.add(main);
			}
		}
		List<SysAttendMain> recordList = new ArrayList<SysAttendMain>(
				recordSet);
		return recordList;
	}

	private List getUserTripBuss(List<SysAttendBusiness> recordList,
			String docCreatorId) {
		Set<SysAttendBusiness> busSet = new HashSet<SysAttendBusiness>();
		for (SysAttendBusiness main : recordList) {
			List<SysOrgElement> targets = main.getFdTargets();
			if (targets == null || targets.isEmpty()) {
				continue;
			}
			for (SysOrgElement org : targets) {
				if (org.getFdId().contains(docCreatorId)) {
					busSet.add(main);
				}
			}
		}
		List<SysAttendBusiness> busList = new ArrayList<SysAttendBusiness>(
				busSet);
		return busList;
	}

	private List getUserTripAttendMainList(String docCreatorId, Date date)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer where = new StringBuffer();
		where.append("sysAttendMain.fdStatus=4");
		where.append(" and sysAttendMain.docCreateTime>=:docCreateTime");
		where.append(
				" and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
		where.append(" and sysAttendMain.docCreator.fdId=:docCreatorId");
		hqlInfo.setParameter("docCreateTime", date);
		hqlInfo.setParameter("docCreatorId", docCreatorId);
		hqlInfo.setWhereBlock(where.toString());
		hqlInfo.setOrderBy("sysAttendMain.docCreateTime asc");
		List<SysAttendMain> mainList = this.getSysAttendMainService()
				.findList(hqlInfo);
		return mainList;
	}

}
