package com.landray.kmss.sys.attend.plugin;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.engine.task.NodeInstanceUtils;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 补卡流程结束事件监听处理类
 * @author cuiwj
 * @version 1.0 2018-05-29
 */
public class SysAttendCardListener extends SysAttendListenerCommonImp
		implements IEventListener, IEventMulticasterAware {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendCardListener.class);

	private IEventMulticaster multicaster;

	@Override
	public void setEventMulticaster(IEventMulticaster multicaster) {
		this.multicaster = multicaster;

	}

	@Override
	public void handleEvent(EventExecutionContext execution, String parameter)
			throws Exception {
		logger.debug(
				"receive SysAttendOutgoingListener:parameter=" + parameter);
		try {
			String processFlag = execution.getProcessParameters().getInstanceParamValue(execution.getProcessInstance(),PROCESS_FLAG_KEY);
			if (PROCESS_FLAG_RUN_VALUE.equals(processFlag) || PROCESS_FLAG_ERROR_VALUE.equals(processFlag)) {
				//新流程事件不进老流程处理
				return;
			}
			String routeType = execution.getNodeInstance().getFdRouteType();
			String docStatus = execution.getExecuteParameters()
					.getExpectMainModelStatus();
			if ((NodeInstanceUtils.ROUTE_TYPE_NORMAL.equals(routeType)
					|| NodeInstanceUtils.ROUTE_TYPE_JUMP.equals(routeType))
					&& !SysDocConstant.DOC_STATUS_DISCARD.equals(docStatus)
					&& !SysDocConstant.DOC_STATUS_DRAFT.equals(docStatus)) {
				JSONObject params = JSONObject.fromObject(parameter);
				IBaseModel mainModel = execution.getMainModel();
				if (mainModel instanceof IExtendDataModel) {
					List<SysAttendBusiness> list = getSysAttendBusinessService()
							.findByProcessId(mainModel.getFdId());
					// 同一流程不会重复操作
					if (list.isEmpty()) {
						List<SysAttendBusiness> busList = getBusinessList(
								params, mainModel);
						if (busList == null || busList.isEmpty()) {
							logger.warn(
									"外出流程数据为空,请查看外出事件配置是否准确!parameter:"
											+ parameter);
							return;
						}
						for (Iterator<SysAttendBusiness> it = busList
								.iterator(); it.hasNext();) {
							SysAttendBusiness bus = it.next();
							// 是否时间重叠
							if (checkDateRepeat(bus)) {
								logger.warn("申请外出失败：外出时间重叠");
								it.remove();
							}
						}
						List<SysAttendBusiness> restatList =new ArrayList<>();
						// 1.保存流程表单数据
						for (SysAttendBusiness bus : busList) {
							// 是否时间重叠
							if (checkDateRepeat(bus)) {
								logger.warn("申请外出失败：外出时间重叠");
								continue;
							}
							getSysAttendBusinessService().add(bus);
							// 2.更新打卡记录
							updateSysAttendMainByOutgoing(bus);
							restatList.add(bus);
						}
						if(CollectionUtils.isNotEmpty(restatList)) {
							// 3.重新统计
							restat(restatList);
						}
					} else {
						logger.warn(
								"同个流程只执行一次,忽略此次操作!流程id:" + mainModel.getFdId()
										+ ";parameter=" + parameter);
					}
				}
			}
		} catch (Exception e) {
			logger.error("外出流程事件同步考勤失败:" + parameter, e);
		}

	}

	/**
	 * 验证外出时间重叠
	 * @param bus
	 * @return
	 */
	private Boolean checkDateRepeat(SysAttendBusiness bus) {
		try{
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("sysAttendBusiness.fdId");
			hqlInfo.setJoinBlock(
					"left join sysAttendBusiness.fdTargets target");
			hqlInfo.setWhereBlock(
					"target.fdId=:targetId" +
							" and sysAttendBusiness.fdBusStartTime<:endTime and sysAttendBusiness.fdBusEndTime>:startTime"
							+ " and sysAttendBusiness.fdType=7 and (sysAttendBusiness.fdDelFlag=0 or sysAttendBusiness.fdDelFlag is null)");
			hqlInfo.setParameter("targetId",
					bus.getFdTargets().get(0).getFdId());
			hqlInfo.setParameter("startTime", bus.getFdBusStartTime());
			hqlInfo.setParameter("endTime", bus.getFdBusEndTime());
			List list = getSysAttendBusinessService().findList(hqlInfo);
			return !list.isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		}
		return false;
	}


	protected List<SysAttendBusiness> getBusinessList(JSONObject params,
			IBaseModel mainModel) throws Exception {
		try {
			List<SysAttendBusiness> busList = new ArrayList<SysAttendBusiness>();
			IExtendDataModel model = (IExtendDataModel) mainModel;
			Map<String, Object> modelData = model.getExtendDataModelInfo()
					.getModelData();

			String docSubject = (String) getSysMetadataParser()
					.getFieldValue(mainModel, "docSubject", false);

			JSONObject targetsJson = JSONObject
					.fromObject(params.get("fdOutTargets"));
			
			
			JSONObject dateJson = JSONObject
					.fromObject(params.get("fdOutDate"));
			JSONObject startTimeJson = JSONObject
					.fromObject(params.get("fdOutStartTime"));
			JSONObject endTimeJson = JSONObject
					.fromObject(params.get("fdOutEndTime"));
			boolean isCountHourConfig = params.containsKey("fdCountHour")
					&& params.get("fdCountHour") instanceof JSONObject;
			//其他外出人员
			String otherIds = null;
			if(params.containsKey("fdOutOthers")) {
				String fdOutOthers=params.getString("fdOutOthers");
				if(StringUtil.isNotNull(fdOutOthers)) {
					JSONObject othersJson = JSONObject
							.fromObject(fdOutOthers);
					String othersFieldName = (String) othersJson.get("value");
					if (modelData.containsKey(othersFieldName)) {
						Object othersObj = modelData.get(othersFieldName);
						otherIds = BeanUtils.getProperty(othersObj, "id");
					} else {
						SysOrgElement org = (SysOrgElement) PropertyUtils
								.getProperty(mainModel, othersFieldName);
						otherIds = org.getFdId();
					}
				}
			}

			String targetsFieldName = (String) targetsJson.get("value");
			String dateFieldName = (String) dateJson.get("value");
			String startFieldName = (String) startTimeJson.get("value");
			String endFieldName = (String) endTimeJson.get("value");

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
			
			if(targetIds == null && otherIds==null) {
				logger.warn("出差流程同步考勤事件中出差人为空!");
				return null;
			}
			if(targetIds != null && otherIds!=null) {
				targetIds+=";"+otherIds;
			}
			if(targetIds == null && otherIds!=null) {
				targetIds=otherIds;
			}
			
			// 是否明细表
			boolean isDateList = dateFieldName.indexOf(".") >= 0;
			boolean isStartList = startFieldName.indexOf(".") >= 0;
			boolean isEndList = endFieldName.indexOf(".") >= 0;

			if (isDateList && isStartList && isEndList) {
				String detailName = dateFieldName.split("\\.")[0];
				String dateName = dateFieldName.split("\\.")[1];
				String startName = startFieldName.split("\\.")[1];
				String endName = endFieldName.split("\\.")[1];
				List<HashMap<String, Object>> detailList = (ArrayList<HashMap<String, Object>>) modelData
						.get(detailName);

				for (int k = 0; k < detailList.size(); k++) {
					HashMap detail = detailList.get(k);
					// 日期
					Date date = (Date) detail.get(dateName);
					// 开始时间
					Date startTime = (Date) detail.get(startName);
					// 结束时间
					Date endTime = (Date) detail.get(endName);

					if (date == null || startTime == null || endTime == null
							|| startTime.getTime() >= endTime.getTime()) {
						continue;
					}

					Date fdStartTime = AttendUtil.joinYMDandHMS(date,
							startTime);
					Date fdEndTime = AttendUtil.joinYMDandHMS(date, endTime);

					// 外出工时
					Float fdCountHour = null;
					if (isCountHourConfig) {
						JSONObject fdCountHourJson = JSONObject
								.fromObject(params.get("fdCountHour"));
						String fdCountHourName = (String) fdCountHourJson
								.get("value");
						String fdCountHourType = (String) fdCountHourJson
								.get("model");

						if ("BigDecimal[]".equals(fdCountHourType)
								|| "Double[]".equals(fdCountHourType)) {
							List fdCountHourList = (ArrayList) getSysMetadataParser()
									.getFieldValue(
											mainModel,
											fdCountHourName, false);
							if (fdCountHourList != null
									&& !fdCountHourList.isEmpty()) {
								fdCountHour = getFdCountHour(
										fdCountHourList.get(k));
							}
						}
					}

					if (fdCountHour == null) {
						fdCountHour = (fdEndTime.getTime()
								- fdStartTime.getTime()) / 3600000.0f;
					}

					busList.add(
							getBusinessModel(fdStartTime, fdEndTime, targetIds,
									model.getFdId(), fdCountHour, docSubject, model));
				}

			} else {
				// 日期
				Date date = (Date) getSysMetadataParser().getFieldValue(mainModel,
						dateFieldName, false);
				// 开始时间
				Date startTime = (Date) getSysMetadataParser()
						.getFieldValue(mainModel, startFieldName, false);
				// 结束时间
				Date endTime = (Date) getSysMetadataParser()
						.getFieldValue(mainModel, endFieldName, false);

				if (date == null || startTime == null || endTime == null
						|| startTime.getTime() >= endTime.getTime()) {
					return null;
				}

				Date fdStartTime = AttendUtil.joinYMDandHMS(date, startTime);
				Date fdEndTime = AttendUtil.joinYMDandHMS(date, endTime);

				// 外出工时
				Float fdCountHour = null;
				if (isCountHourConfig) {
					JSONObject fdCountHourJson = JSONObject
							.fromObject(params.get("fdCountHour"));
					String fdCountHourName = (String) fdCountHourJson
							.get("value");
					String fdCountHourType = (String) fdCountHourJson
							.get("model");

					if ("BigDecimal".equals(fdCountHourType)
							|| "Double".equals(fdCountHourType)) {
						Object countHour = getSysMetadataParser()
								.getFieldValue(
										mainModel,
										fdCountHourName, false);
						fdCountHour = getFdCountHour(
								countHour);
					}
				}

				if (fdCountHour == null) {
					fdCountHour = (fdEndTime.getTime()
							- fdStartTime.getTime()) / 3600000.0f;
				}

				busList.add(getBusinessModel(fdStartTime, fdEndTime, targetIds,
						model.getFdId(), fdCountHour, docSubject, model));
			}

			return busList;
		} catch (Exception e) {
			logger.error("获取外出数据出错:" + e.getMessage(), e);
			return null;
		}
	}

	private SysAttendBusiness getBusinessModel(Date fdBusStartTime,
			Date fdBusEndTime, String targetIds, String fdProcessId,
			Float fdCountHour, String fdProcessName, IBaseModel model)
			throws Exception {
		SysAttendBusiness sysAttendBusiness = new SysAttendBusiness();
		sysAttendBusiness.setFdBusStartTime(fdBusStartTime);
		sysAttendBusiness.setFdBusEndTime(fdBusEndTime);
		sysAttendBusiness.setFdProcessId(fdProcessId);
		sysAttendBusiness.setFdProcessName(fdProcessName);
		sysAttendBusiness.setDocUrl(AttendUtil.getDictUrl(model, fdProcessId));
		//去重
		String[] ids=targetIds.split(";");
		List<String> list = Arrays.asList(ids);
        Set set = new HashSet(list);
        String [] sIds=(String [])set.toArray(new String[0]);
		sysAttendBusiness.setFdTargets(getSysOrgCoreService()
				.findByPrimaryKeys(sIds));
		// 7为外出
		sysAttendBusiness.setFdType(7);
		sysAttendBusiness.setFdCountHour(fdCountHour);
		sysAttendBusiness.setDocCreateTime(new Date());
		return sysAttendBusiness;
	}

	private Float getFdCountHour(Object value) {
		if (value != null) {
			if (value instanceof Double) {
				return ((Double) value).floatValue();
			}
			if (value instanceof BigDecimal) {
				return ((BigDecimal) value).floatValue();
			}
		}
		return null;
	}

	/**
	 * 重新统计
	 * 
	 * @param busList
	 */
	private void restat(List<SysAttendBusiness> busList) {
		try {
			reStatistics(busList,multicaster);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("外出重新统计出错" + e.getMessage(), e);
		}
	}

	private List<SysOrgElement> getStatOrgList(List<SysAttendBusiness> busList)
			throws Exception {
		List<SysOrgElement> statOrgs = new ArrayList<SysOrgElement>();
		Set<SysOrgElement> orgs = new HashSet<SysOrgElement>();
		for (SysAttendBusiness bus : busList) {
			orgs.addAll(
					getSysOrgCoreService().expandToPerson(bus.getFdTargets()));
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
