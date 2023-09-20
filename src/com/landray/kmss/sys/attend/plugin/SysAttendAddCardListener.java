package com.landray.kmss.sys.attend.plugin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.hr.staff.report.HrCurrencyParams;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendMainExc;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendMainExcService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.spring.AttendStatThread;
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
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;

/**
 * 补卡流程结束事件监听处理类
 * 
 * @author sunny
 * @version 1.0 2022-10-29
 */
public class SysAttendAddCardListener extends SysAttendListenerCommonImp
		implements IEventListener, IEventMulticasterAware {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendAddCardListener.class);

	protected List<Map<String, Object>> getApplyList(JSONObject params, IBaseModel mainModel) throws Exception {
		try {
			IExtendDataModel model = (IExtendDataModel) mainModel;
			Map<String, Object> modelData = model.getExtendDataModelInfo().getModelData();
			List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
			String docSubject = (String) getSysMetadataParser().getFieldValue(mainModel, "docSubject", false);

			JSONObject targetsJson = JSONObject.fromObject(params.get("fdCardTargets"));
			JSONObject dateJson = JSONObject.fromObject(params.get("fdCardDate"));
			JSONObject timeJson = JSONObject.fromObject(params.get("fdCardTime"));

			String targetsFieldName = (String) targetsJson.get("value");
			String dateFieldName = (String) dateJson.get("value");
			String timeFieldName = (String) timeJson.get("value");

			// 是否明细表
			boolean isDateList = dateFieldName.indexOf(".") >= 0;
			boolean isTimeList = timeFieldName.indexOf(".") >= 0;

			if (isDateList && isTimeList) {
				String detailName = dateFieldName.split("\\.")[0];
				String dateName = dateFieldName.split("\\.")[1];
				String tagetName = targetsFieldName.split("\\.")[1];
				String timeName = timeFieldName.split("\\.")[1];
				List<HashMap<String, Object>> detailList = (ArrayList<HashMap<String, Object>>) modelData
						.get(detailName);

				for (int k = 0; k < detailList.size(); k++) {
					HashMap detail = detailList.get(k);
					// 日期
					Date date = (Date) detail.get(dateName);
					// 签卡时间
					Date time = (Date) detail.get(timeName);

					// 人员
					String targetIds = BeanUtils.getProperty(detail.get(tagetName), "id");
					
					if (targetIds == null) {
						logger.warn("签卡流程同步考勤事件中外出人为空!");
						throw new Exception("签卡流程同步考勤事件中外出人为空!");
					}
					if (date == null || time == null) {
						continue;
					}
					Date fdDateTime = AttendUtil.joinYMDandHMS(date, time);
					getApplyMapData(targetIds, fdDateTime, docSubject);
					
					if(!orgList.contains(targetIds)){
						orgList.add(targetIds);
					}
					
					if(!dateList.contains(fdDateTime)){
						dateList.add(fdDateTime);
					}
					
				}

			} else {
				// 日期
				Date date = (Date) getSysMetadataParser().getFieldValue(mainModel, dateFieldName, false);
				// 时间
				Date time = (Date) getSysMetadataParser().getFieldValue(mainModel, timeFieldName, false);

				// 人员
				String targetIds = null;
				if (modelData.containsKey(targetsFieldName)) {
					Object targetsObj = modelData.get(targetsFieldName);
					targetIds = BeanUtils.getProperty(targetsObj, "id");
				} else {
					SysOrgElement org = (SysOrgElement) PropertyUtils.getProperty(mainModel, targetsFieldName);
					targetIds = org.getFdId();
				}
				if (targetIds == null) {
					logger.warn("签卡流程同步考勤事件中外出人为空!");
					throw new Exception("签卡流程同步考勤事件中外出人为空!");
				}
				if (date == null || date == null) {
					throw new Exception("签卡流程同步考勤事件中日期为空!");
				}

				Date fdDateTime = AttendUtil.joinYMDandHMS(date, time);
				orgList.add(targetIds);
				dateList.add(fdDateTime);
				
				getApplyMapData(targetIds, fdDateTime, docSubject);
			}

			return childList;
		} catch (Exception e) {
			logger.error("获取外出数据出错:" + e.getMessage(), e);
			throw new Exception("获取外出数据出错:" + e.getMessage());
		}
	}

	private List<String> orgList = null;
	private List<Date> dateList = null;
	
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
				
				AttendStatThread task = new AttendStatThread();
				task.setDateList(dateList);
				task.setOrgList(orgList);
				task.setFdMethod("restat");
				task.setFdIsCalMissed("true");
				task.setFdOperateType("create");//restat create
				AttendThreadPoolManager manager = AttendThreadPoolManager.getInstance();
				if (!manager.isStarted()) {
					manager.start();
				}
				manager.submit(task);
			}
		}

	}

	private void getApplyMapData(String target, Date date, String docSubject) throws Exception {
		DateFormat sdf_y_m_d = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// 取出这个人的异常考勤
		StringBuffer sb = new StringBuffer();

		String date_start = "date_sub('" + sdf_y_m_d.format(date) + "', interval 2 hour)";
		String date_end = "date_add('" + sdf_y_m_d.format(date) + "', interval 2 hour)";

		sb.append("select m.* from sys_attend_main m left join ");
		sb.append(" sys_attend_his_category c on m.fd_category_his_id=c.fd_id ");
		sb.append(" where m.doc_creator_id='" + target + "' ");
		sb.append(" and (m.doc_status=0 or m.doc_status is null) and ");
		sb.append(" ( (m.fd_status=0 or m.fd_status=2 or m.fd_status=3 ) ");
		sb.append(" or (m.fd_status=0  and m.fd_outside=0 and c.fd_osd_review_type=1) )");
		sb.append(" and (fd_base_work_time between " + date_start + " and " + date_end + " )");
		qiankaUpdateStatus(HrCurrencyParams.getListBySql(sb.toString()), docSubject);
	}
	
	public void qiankaUpdateStatus(List<Map<String, Object>> mainList, String docSubject) throws Exception {
		if (mainList != null && !mainList.isEmpty()) {
			for (Map<String, Object> obj : mainList) {

				SysAttendMain main = (SysAttendMain) sysAttendMainService.findByPrimaryKey(obj.get("fd_id") + "");
				SysAttendCategory category = CategoryUtil.getFdCategoryInfo(main);
				if ((main.getFdStatus() == 0 || main.getFdStatus() == 2 || main.getFdStatus() == 3
						|| Integer.valueOf(1).equals(category.getFdOsdReviewType())
								&& Boolean.TRUE.equals(main.getFdOutside()) && main.getFdStatus() == 1)
						&& (main.getFdState() == null || main.getFdState() != 2)) {
					Integer oldStatus = main.getFdStatus();
					Date oldCreateTime = main.getDocCreateTime();
					Boolean oldCardside = main.getFdOutside();
					HQLInfo excHqlInfo = new HQLInfo();
					excHqlInfo.setWhereBlock("sysAttendMainExc.fdAttendMain.fdId=:fdAttendMainId");
					excHqlInfo.setParameter("fdAttendMainId", main.getFdId());

					List excList = sysAttendMainExcService.findList(excHqlInfo);
					boolean isToOk = false;
					if (excList != null && !excList.isEmpty()) {
						// 已提异常单
						for (int k = 0; k < excList.size(); k++) {
							SysAttendMainExc exc = (SysAttendMainExc) excList.get(k);
							try {
								// 特权通过
								sysAttendMainExcService.passByAdmin(exc.getFdId());
								main.setFdState(2);
							} catch (Exception e) {
								// 特权通过失败
								main.setFdState(3);
								isToOk = true;
							}
						}
					} else {
						// 未提异常单
						main.setFdStatus(main.getFdStatus());
						main.setFdState(2);
						isToOk = true;
					}
					if (isToOk) {
						// 用户打卡时间恢复为上下班时间
						if (Integer.valueOf(0).equals(oldStatus) || Integer.valueOf(2).equals(oldStatus)
								|| Integer.valueOf(3).equals(oldStatus)) {
							List signList = sysAttendCategoryService.getAttendSignTimes(category, main.getFdWorkDate(),
									main.getDocCreator());
							SysAttendCategoryWorktime workTime = this.sysAttendCategoryService
									.getWorkTimeByRecord(signList, main.getDocCreateTime(), main.getFdWorkType());
							if (workTime != null) {
								Date _signTime = workTime.getFdStartTime();
								Date fdDate = main.getFdWorkDate();
								if (Integer.valueOf(1).equals(main.getFdWorkType())) {
									_signTime = workTime.getFdEndTime();
									Integer overTimeType = workTime.getFdOverTimeType();
									// 跨天排班打卡时间要加一天
									if (Integer.valueOf(2).equals(overTimeType)) {
										fdDate = AttendUtil.addDate(fdDate, 1);
									}
								}
								main.setDocCreateTime(AttendUtil.joinYMDandHMS(fdDate, _signTime));
								logger.warn("用户考勤异常状态置为正常操作通过!打卡时间调整为:" + oldCreateTime + "--->"
										+ main.getDocCreateTime() + ";userName:" + main.getDocCreator());
							}
						}
					}
					// 保存打卡记录信息
					main.setFdAppName(ResourceUtil.getString("sysAttendMain.fdAppName.ekp", "sys-attend"));
					main.setFdOutside(false);
					main.setDocAlteror(UserUtil.getUser());
					main.setDocAlterTime(new Date());
					main.setFdDesc(docSubject);
					main.setFdAlterRecord(ResourceUtil.getString("sysAttendMain.fdAlterRecord.content", "sys-attend")
							.replace("%status1%",
									oldStatus == 1 && Boolean.TRUE.equals(oldCardside)
											? ResourceUtil.getString("sysAttendMain.outside", "sys-attend")
											: EnumerationTypeUtil.getColumnEnumsLabel("sysAttendMain_fdStatus",
													oldStatus + ""))
							.replace("%status2%", ResourceUtil.getString("sysAttendMain.fdStatus.ok", "sys-attend")));
					sysAttendMainService.getBaseDao().update(main);
					// 删除待办
					sysNotifyMainCoreService.getTodoProvider().clearTodoPersons(main, "sendUnSignNotify", null, null);
					// 添加日志信息
					if (UserOperHelper.allowLogOper("updateStatus", "KmReviewHrAttendJiTiJiaBanListener")) {

						UserOperContentHelper.putUpdate(main).putSimple("fdStatus", oldStatus, main.getFdStatus())
								.putSimple("fdCardside", oldCardside, main.getFdOutside())
								.putSimple("docAlteror", null, main.getDocAlteror())
								.putSimple("docAlterTime", null, main.getDocAlterTime())
								.putSimple("fdAlterRecord", null, main.getFdAlterRecord());
					}
				}
				//重新统计日汇总
				sysAttendMainService.resetUpdateDayReport(main.getDocCreator(), main.getDocCreateTime());
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
	
}
