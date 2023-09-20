package com.landray.kmss.km.reviewext.listener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.staff.report.HrCurrencyParams;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendMainExc;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendMainExcService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

public class KmReviewHrAttendJiTiJiaBanListener implements IEventListener {

	private static final Logger logger = LoggerFactory.getLogger(KmReviewHrAttendJiTiJiaBanListener.class);

	private ISysNotifyMainCoreService sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil
			.getBean("sysNotifyMainCoreService");;

	private ISysAttendMainExcService sysAttendMainExcService = (ISysAttendMainExcService) SpringBeanUtil
			.getBean("sysAttendMainExcService");
	
	private ISysAttendCategoryService sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil
			.getBean("sysAttendCategoryService");

	private ISysAttendMainService sysAttendMainService = (ISysAttendMainService) SpringBeanUtil
			.getBean("sysAttendMainService");

	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		if (execution.getMainModel() instanceof KmReviewMain) {
			logger.debug("receive KmReviewHrAttendJiTiJiaBanListener,parameter=" + parameter);
			DateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
			JSONObject params = JSONObject.parseObject(parameter);
			KmReviewMain mainModel = (KmReviewMain) execution.getMainModel();
			Map<String, Object> map = mainModel.getExtendDataModelInfo().getModelData();
			// 集体归属月
			int month_sel = Float.valueOf(map.get("fd_3b34b14dc7f032") + "").intValue();
			DateFormat sdf_y_m_d = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat sdf_h_m_s = new SimpleDateFormat("HH:mm:ss");

			Date start_date = null;
			Date end_date = null;
			Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
			ca.setTime(new Date()); // 设置时间为当前时间

			// 获取归属月1日
			ca.set(Calendar.MONTH, month_sel + 1);
			ca.set(Calendar.DAY_OF_MONTH, 1);// 1日
			start_date = ca.getTime();
			System.out.println(sdf_y_m_d.format(start_date));

			// 获取归属月31日
			ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
			end_date = ca.getTime();
			System.out.println(sdf_y_m_d.format(end_date));

			// 申请类型：1加班2签卡3假期
			int jiti_type = Float.valueOf(map.get("fd_3b397a437b3168") + "").intValue();

			String detail_table_id = "";

			if (jiti_type == 1) {
				// fd_3b142127f54434 加班明细表
				detail_table_id = "fd_3b142127f54434";

			} else if (jiti_type == 2) {
				// fd_3b395adbd90a90 签卡明细表

				detail_table_id = "fd_3b395adbd90a90";
				List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();

				childList = (List<Map<String, Object>>) map.get(detail_table_id);

				if (childList.size() > 0) {
					for (int i = 0; i < childList.size(); i++) {
						// 取出每行补卡请求
						Map<String, Object> detailMap = childList.get(i);

						// 离职人员fd_staff_no_leave
						Map<String, Object> personMap = (Map<String, Object>) detailMap.get("fd_3b395b9514e7c4");
						String staff_person_fd_id = personMap.get("id").toString();

						// 刷卡日期 fd_3b395b9e7de34c
						String riqi = detailMap.get("fd_3b395b9e7de34c") + "";
						// 刷卡时间 fd_3b395ba4bd2d2e
						String shijian = detailMap.get("fd_3b395ba4bd2d2e") + "";
						// 取出这个人的异常考勤
						StringBuffer sb = new StringBuffer();

						String date_start = "date_sub('" + sdf_y_m_d.format(sdf.parse(riqi)) + " "
								+ sdf_h_m_s.format(sdf.parse(shijian)) + "', interval 2 hour)";
						String date_end = "date_add('" + sdf_y_m_d.format(sdf.parse(riqi)) + " "
								+ sdf_h_m_s.format(sdf.parse(shijian)) + "', interval 2 hour)";

						sb.append("select m.* from sys_attend_main m left join ");
						sb.append(" sys_attend_his_category c on m.fd_category_his_id=c.fd_id ");
						sb.append(" where m.doc_creator_id='" + staff_person_fd_id + "' ");
						sb.append(" and (m.doc_status=0 or m.doc_status is null) and ");
						sb.append(" ( (m.fd_status=0 or m.fd_status=2 or m.fd_status=3 ) ");
						sb.append(" or (m.fd_status=0  and m.fd_outside=0 and c.fd_osd_review_type=1) )");
						sb.append(" and (fd_base_work_time between " + date_start + " and " + date_end + " )");
						qiankaUpdateStatus(HrCurrencyParams.getListBySql(sb.toString()));

					}
				}

				detail_table_id = "fd_3b395adbd90a90";
			} else if (jiti_type == 3) {
				// fd_3b395add7a76e2 假期明细表
				detail_table_id = "fd_3b395add7a76e2";
			}

		}
	}

	public void qiankaUpdateStatus(List<Map<String, Object>> mainList) throws Exception {

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
					Boolean oldOutside = main.getFdOutside();
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
					main.setFdAlterRecord(ResourceUtil.getString("sysAttendMain.fdAlterRecord.content", "sys-attend")
							.replace("%status1%",
									oldStatus == 1 && Boolean.TRUE.equals(oldOutside)
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
								.putSimple("fdOutside", oldOutside, main.getFdOutside())
								.putSimple("docAlteror", null, main.getDocAlteror())
								.putSimple("docAlterTime", null, main.getDocAlterTime())
								.putSimple("fdAlterRecord", null, main.getFdAlterRecord());
					}
				}
				sysAttendMainService.resetUpdateDayReport(main.getDocCreator(), main.getDocCreateTime());
			}
		}
	}
}
