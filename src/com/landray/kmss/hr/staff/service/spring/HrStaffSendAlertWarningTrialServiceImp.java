/**
 * 
 */
package com.landray.kmss.hr.staff.service.spring;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.util.HrStaffDateUtil;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.notify.constant.SysNotifyConstants;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

/**
 * @author 陈经纬
 * @creation 2017-1-1
 */
public class HrStaffSendAlertWarningTrialServiceImp {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HrStaffSendAlertWarningTrialServiceImp.class);

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	private ISysAppConfigService sysAppConfigService;

	public void setSysAppConfigService(ISysAppConfigService sysAppConfigService) {
		this.sysAppConfigService = sysAppConfigService;
	}

	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	public void setHrStaffPersonInfoService(
			IHrStaffPersonInfoService hrStaffPersonInfoService) {
		this.hrStaffPersonInfoService = hrStaffPersonInfoService;
	}

	public void sendAlertWarning() throws Exception {
		Map map = sysAppConfigService
				.findByKey("com.landray.kmss.hr.staff.model.HrStaffAlertWarningTrial");
		String alertDate = (String) map.get("cycleReminder");
		String staffReminder = (String) map.get("staffReminder");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		if ("true".equals(staffReminder)) {
			Calendar cal = Calendar.getInstance();
			if ("week".equals(alertDate)) {
				int dow = cal.get(Calendar.DAY_OF_WEEK);
				if (2 == dow) {
					if (StringUtil.isNotNull(getTrialNames(alertDate))){
						String startDate= formatter.format(HrStaffDateUtil.getTimesWeekmorning());
						String endDate=formatter.format(HrStaffDateUtil.getTimesWeeknight());
						sendAlert(map, getTrialNames(alertDate), ResourceUtil.getString("hr.staff.notify.week", "hr-staff"),startDate,endDate);
					}
				}
			} else if ("month".equals(alertDate)) {

				int day = cal.get(Calendar.DATE);
				if (1 == day) {
					if (StringUtil.isNotNull(getTrialNames(alertDate))){
						String startDate= formatter.format(HrStaffDateUtil.getTimesMonthmorning());
						String endDate=formatter.format(HrStaffDateUtil.getTimesMonthnight());
						sendAlert(map, getTrialNames(alertDate), ResourceUtil.getString("hr.staff.notify.month", "hr-staff"),startDate,endDate);
					}
				}

			} else if ("twoMonth".equals(alertDate)) {
				int month = cal.get(Calendar.MONTH) + 1;
				int day = cal.get(Calendar.DATE);
				if ((1 == month || 3 == month || 5 == month || 7 == month || 9 == month || 11 == month) && 1 == day) {
					String sendNames =getTrialNames(alertDate);
					if (StringUtil.isNotNull(sendNames)){
						String startDate= formatter.format(HrStaffDateUtil.getTimesMonthmorning());
						String endDate=formatter.format(HrStaffDateUtil.getTimeLastMonthLast());
						sendAlert(map, sendNames, ResourceUtil.getString("hr.staff.notify.twoMonth","hr-staff"),startDate,endDate);
						
					}
				}

			} else if ("quarter".equals(alertDate)) {
				int month = cal.get(Calendar.MONTH) + 1;
				int day = cal.get(Calendar.DATE);
				if ((1 == month || 4 == month || 7 == month || 10 == month) && 1 == day) {
					String sendNames =getTrialNames(alertDate);
					if (StringUtil.isNotNull(sendNames)){
						String startDate= formatter.format(HrStaffDateUtil.getFirstDayOfQuarter());
						String endDate=formatter.format(HrStaffDateUtil.getLastDayOfQuarter());
						sendAlert(map, sendNames, ResourceUtil.getString("hr.staff.notify.quarter","hr-staff"),startDate,endDate);
				
					
					}
				}
			}
		}

	}

	public String getTrialNames(String alertDate) {
		List<HrStaffPersonInfo> list = null;
		try {
			list = hrStaffPersonInfoService.findByTrial(alertDate);
		} catch (Exception e) {
			logger.error("", e);
		}
		String names = "";
		if (list != null) {

			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) == list.get(0)) {
					names = (list.get(i)).getFdName();
				} else {
					names = (list.get(i)).getFdName() + "、" + names;
				}
			}
		}
		return names;
	}

	public void sendAlert(Map map, String names, String dateFlag,String startDate,String endDate)
			throws Exception {
		String element = (String) map.get("personReminderId");
		if(StringUtil.isNotNull(element)){
			String a[] = element.split(";");
			int i;
			for(i=0;i<a.length;i++){
				// 接收人
				HrStaffPersonInfo hrStaffPersonInfo = hrStaffPersonInfoService.findByOrgPersonId(a[i]);
				if (hrStaffPersonInfo != null) {
					sendTodoFromResource(hrStaffPersonInfo, names, dateFlag, startDate, endDate);
				} else {
					List<HrStaffPersonInfo> hrStaffPersonInfos = hrStaffPersonInfoService.findByPost(a[i]);
					if (hrStaffPersonInfos != null && hrStaffPersonInfos.size() > 0) {
						for (HrStaffPersonInfo hrStaffPersonInfo2 : hrStaffPersonInfos) {
							sendTodoFromResource(hrStaffPersonInfo2, names, dateFlag, startDate, endDate);
						}
					} else {
						HQLInfo hqlInfo = new HQLInfo();
						Page page = hrStaffPersonInfoService.obtainPersons(hqlInfo, a[i], "");
						if (page != null) {
							List<SysOrgPerson> sysOrgPersons = page.getList();
							if (sysOrgPersons.size() > 0) {
								for (SysOrgPerson sysOrgPerson : sysOrgPersons) {
									HrStaffPersonInfo hrStaffPersonInfoTemp = (HrStaffPersonInfo) hrStaffPersonInfoService
											.findByPrimaryKey(sysOrgPerson.getFdId());
									if (hrStaffPersonInfoTemp != null) {
										sendTodoFromResource(hrStaffPersonInfoTemp, names, dateFlag, startDate,
												endDate);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void sendTodoFromResource(HrStaffPersonInfo hrStaffPersonInfo,
			String names, String dateFlag,String startDate,String endDate) throws Exception {
		//获取上下文
		NotifyContext notifyContext = sysNotifyMainCoreService
				.getContext(null);
		//获取通知方式
		notifyContext.setNotifyType("todo");
		// 设置发布类型为“待办”（默认为待阅）
		  //“待办”消息发送出去后，需要到某事件发生后才变成已办，如审批通过等
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		// 设置发布KEY值，为后面的删除准备
		notifyContext.setKey("sendFromResource");
		   //获取通知人
		List targets = new ArrayList();
		
		targets.add(hrStaffPersonInfo.getFdOrgPerson());
		  //设置发布通知人
		notifyContext.setNotifyTarget(targets);
		
		notifyContext.setLink("/hr/staff/hr_staff_person_info/index.jsp?type=warningTrial#cri.m=true&cri.q=_fdStatus:trial;_fdStatus:practice;_fdStatus:temporary;_fdStatus:trialDelay;_fdStatus:retire;fdTrialExpirationTime:"+startDate+";fdTrialExpirationTime:"+endDate);
		
		String subject="人事提醒，"+dateFlag+"试用期到期员工有："+names;
		
		notifyContext.setSubject(subject);
		notifyContext.setContent(subject);
		notifyContext.setParameter1(SysNotifyConstants.SUPPORT_MORETIMES_SEND_TODO);
		sysNotifyMainCoreService.sendNotify(hrStaffPersonInfo, notifyContext,null);
		}


	/*
	 * 取消由sendTodoFromResource发出去的待办
	 */
	public void cancelTodo(HrStaffPersonInfo hrStaffPersonInfo)
			throws Exception {
		sysNotifyMainCoreService.getTodoProvider().remove(hrStaffPersonInfo,
				"sendFromResource");
	}

	public HashMap getReplaceMap(String names, String dateFlag) {
		HashMap replaceMap = new HashMap();
		replaceMap.put("hr-staff:hr.staff.trial.notify.trial.date", dateFlag);
		replaceMap.put("hr-staff:hr.staff.trial.notify.trial.person.fdName",
				names);

		return replaceMap;
	}
}
