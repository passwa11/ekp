package com.landray.kmss.sys.attend.plugin;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.hr.staff.report.HrCurrencyParams;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.model.SysAttendImportLog;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendMainExc;
import com.landray.kmss.sys.attend.model.SysAttendSynDing;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendImportLogService;
import com.landray.kmss.sys.attend.service.ISysAttendMainExcService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.ISysAttendSynDingService;
import com.landray.kmss.sys.attend.service.spring.AttendStatThread;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendThreadPoolManager;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.engine.task.NodeInstanceUtils;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 补卡流程结束事件监听处理类
 * 
 * @author sunny
 * @version 1.0 2022-10-29
 */
public class SysAttendAddWeekDayCardListener extends SysAttendListenerCommonImp
		implements IEventListener, IEventMulticasterAware, ApplicationContextAware {
	SimpleDateFormat sdf = new SimpleDateFormat(AttendConstant.DATE_TIME_FORMAT_STRING);
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendAddWeekDayCardListener.class);

	private List<String> orgList = null;
	private List<Date> dateList = null;

	protected JSONArray getApplyList(JSONObject params, IBaseModel mainModel) throws Exception {
		JSONArray resultArray = new JSONArray();
		
		try {
			IExtendDataModel model = (IExtendDataModel) mainModel;
			Map<String, Object> modelData = model.getExtendDataModelInfo().getModelData();

			JSONObject targetsJson = JSONObject.fromObject(params.get("fdCardTargets"));
			JSONObject fdStartTimeJson = JSONObject.fromObject(params.get("fdStartTime"));
			JSONObject fdEndTimeJson = JSONObject.fromObject(params.get("fdEndTime"));

			String targetsFieldName = (String) targetsJson.get("value");
			String fdStartTimeFieldName = (String) fdStartTimeJson.get("value");
			String fdEndTimeFieldName = (String) fdEndTimeJson.get("value");

			// 日期
			Date fdStartTime = (Date) getSysMetadataParser().getFieldValue(mainModel, fdStartTimeFieldName, false);
			// 时间
			Date fdEndTime = (Date) getSysMetadataParser().getFieldValue(mainModel, fdEndTimeFieldName, false);

			// 人员
			String targetId = null;
			if (modelData.containsKey(targetsFieldName)) {
				Object targetsObj = modelData.get(targetsFieldName);
				targetId = BeanUtils.getProperty(targetsObj, "id");
			} else {
				SysOrgElement org = (SysOrgElement) PropertyUtils.getProperty(mainModel, targetsFieldName);
				targetId = org.getFdId();
			}
			if (targetId == null) {
				logger.warn("签卡流程同步考勤事件中签卡人为空!");
				throw new Exception("签卡流程同步考勤事件中签卡人为空!");
			}
			if (fdStartTime == null || fdStartTime == null) {
				throw new Exception("签卡流程同步考勤事件中日期为空!");
			}

			JSONObject obj1 = new JSONObject();
			JSONObject docCreatorObj = new JSONObject();
			docCreatorObj.put("Id", targetId);
			obj1.put("docCreator", docCreatorObj);
			obj1.put("createTime", sdf.format(fdStartTime));
			resultArray.add(obj1);

			orgList.add(targetId);
			dateList.add(fdStartTime);
			if (fdEndTime == null || fdEndTime == null) {

			} else {

				JSONObject obj2 = new JSONObject();
				docCreatorObj.put("Id", targetId);
				obj2.put("docCreator", docCreatorObj);
				obj2.put("createTime", sdf.format(fdEndTime));
				resultArray.add(obj2);
			}
			return resultArray;
		} catch (Exception e) {
			logger.error("签卡流程同步考勤事件数据出错:" + e.getMessage(), e);
			throw new Exception("签卡流程同步考勤事件数据出错:" + e.getMessage());
		}
	}

	private void publishAttendEvent(JSONArray arrays) {
		// 发送事件通知
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appName", "补卡流程");
		params.put("datas", arrays);
		params.put("operatorType", "import");
		params.put("sysAttendImportLog", null);
		applicationContext.publishEvent(new Event_Common("importOriginAttendMain", params));
	}

	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		logger.debug("receive SysAttendAddCardListener:parameter=" + parameter);
		
		orgList = new ArrayList<String>();
		dateList = new ArrayList<Date>();
		
		String routeType = execution.getNodeInstance().getFdRouteType();
		String docStatus = execution.getExecuteParameters().getExpectMainModelStatus();
		if ((NodeInstanceUtils.ROUTE_TYPE_NORMAL.equals(routeType)
				|| NodeInstanceUtils.ROUTE_TYPE_JUMP.equals(routeType))
				&& !SysDocConstant.DOC_STATUS_DISCARD.equals(docStatus)
				&& !SysDocConstant.DOC_STATUS_DRAFT.equals(docStatus)) {
			JSONObject params = JSONObject.fromObject(parameter);
			IBaseModel mainModel = execution.getMainModel();
			if (mainModel instanceof IExtendDataModel) {
				getApplyList(params, mainModel);

				publishAttendEvent(getApplyList(params, mainModel));

				AttendStatThread task = new AttendStatThread();
				task.setDateList(dateList);
				task.setOrgList(orgList);
				task.setFdMethod("restat");
				task.setFdIsCalMissed("true");
				task.setFdOperateType("create");// restat create
				AttendThreadPoolManager manager = AttendThreadPoolManager.getInstance();
				if (!manager.isStarted()) {
					manager.start();
				}
				manager.submit(task);
			}
		}

	}

	private ISysNotifyMainCoreService sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil
			.getBean("sysNotifyMainCoreService");;

	private ISysAttendMainExcService sysAttendMainExcService = (ISysAttendMainExcService) SpringBeanUtil
			.getBean("sysAttendMainExcService");

	private ISysAttendCategoryService sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil
			.getBean("sysAttendCategoryService");

	private ISysAttendMainService sysAttendMainService = (ISysAttendMainService) SpringBeanUtil
			.getBean("sysAttendMainService");

	@Override
	public void setEventMulticaster(IEventMulticaster arg0) {
	}

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	private ISysOrgElementService sysOrgElementService = (ISysOrgElementService) SpringBeanUtil
			.getBean("sysOrgElementService");
	private ISysAttendSynDingService sysAttendSynDingService = (ISysAttendSynDingService) SpringBeanUtil
			.getBean("sysAttendSynDingService");

}
