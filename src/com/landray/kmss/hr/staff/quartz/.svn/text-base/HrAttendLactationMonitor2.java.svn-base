package com.landray.kmss.hr.staff.quartz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.hr.staff.report.HrCurrencyParams;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.model.SysAttendLactationDetail;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendRestatLog;
import com.landray.kmss.sys.attend.model.SysAttendStat;
import com.landray.kmss.sys.attend.service.ISysAttendBusinessService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendLactationDetailService;
import com.landray.kmss.sys.attend.service.ISysAttendMainExcService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.ISysAttendRestatLogService;
import com.landray.kmss.sys.attend.service.ISysAttendStatService;
import com.landray.kmss.sys.attend.service.spring.AttendStatThread;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendThreadPoolManager;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.time.service.ISysTimeLeaveDetailService;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 监控签到模块每日汇总，第二种实现方式
 * 
 * @author sunny
 * @version 创建时间：2022年11月2日上午10:01:03
 */
public class HrAttendLactationMonitor2 {
	private final int fd_leave_type_buru = 10;// 哺乳假期
	private final int fd_leave_type_chanqian = 6;// 产前工间休息

	List<Map<String, Object>> main_list = null;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void monitor(SysQuartzJobContext context) throws Exception {
		// 取出哺乳假期和产前工间休息人员
		// 查他日考勤汇总有无早退或迟到的有效考勤数据
		// 早退或迟到的日期有无在报备范围
		// 若在报备范围，修改有效考勤记录为正常

		// 哺乳假期，每天1小时，可分两次休息，这里只监控迟到和早退的
		// 先看 迟到且早退的情况，迟到30分钟以内，计为正常考勤，迟到超30分钟以上，打开时间 倒退30分钟，其他认为迟到
		// 有迟到无早退 与有早退无迟到 ，迟到或早退
		// 时间低于1小时的情况，视为正常考勤，超过1小时，则打卡时间减1小时，视为迟到或早退，该情况与产前工间休息同一个逻辑
		context.logMessage("产前工间休息员工和哺乳假期员工，缺卡不做假期处理");
		List<Map<String, Object>> person_list = new ArrayList<>();
		person_list = getTeShuPerson();

		List<String> orgList = new ArrayList<String>();
		List<Date> dateList = new ArrayList<Date>();
		List<String> orgNameList = new ArrayList<String>();

		if (person_list.size() > 0) {
			context.logMessage("正在享受产前工间休息和哺乳假期的员工有:" + person_list.size() + "人");
			main_list = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> map : person_list) {

				String person_id = map.get("fd_person_id") + "";
				String person_name = getSysOrgCoreService().findByPrimaryKey(person_id).getFdName();
				String start_time = sdf.format(sdf.parse(map.get("start_time") + ""));
				String end_time = sdf.format(sdf.parse(map.get("end_time") + ""));
				int fd_jiaqi_type = Double.valueOf(map.get("fd_jiaqi_type") + "").intValue();

				String fdProcessId = map.get("fd_id") + "";// 后期若要关联可用
				orgList.add(person_id);
				orgNameList.add(person_name);

				KmReviewMain kmModel = (KmReviewMain) kmReviewMainService.findByPrimaryKey(fdProcessId);
				if (!kmModel.getDocStatus().equals("30")) {
					continue;
				}
				String sql_sys_attend_stat = "select * from sys_attend_stat where (fd_late_time>0 or fd_left_time>0) and fd_date >= '"
						+ start_time + "' and fd_date<'" + end_time + "' and doc_creator_id='" + person_id + "'";
				List<Map<String, Object>> list_sys_attend_stat = new ArrayList<>();
				list_sys_attend_stat = HrCurrencyParams.getListBySql(sql_sys_attend_stat);

				if (list_sys_attend_stat.size() == 0) {
					context.logMessage(
							person_name + ":" + person_id + " 在 " + start_time + " 与 " + end_time + "期间无迟到早退记录");
					continue;
				}
				context.logMessage(person_name + ":" + person_id + " 在 " + start_time + " 与 " + end_time + "考勤开始--统计："
						+ (fd_jiaqi_type == 6 ? "产前工间休息" : "哺乳假期"));

				for (Map<String, Object> map_sys_attend_stat : list_sys_attend_stat) {
					SysAttendStat statDetail = (SysAttendStat) sysAttendStatService
							.findByPrimaryKey(map_sys_attend_stat.get("fd_id") + "");
					if (statDetail == null) {
						throw new NoRecordException();
					}
					if (is_totaol_ok(statDetail, fd_jiaqi_type)) { // 说明系统已经做了这个人关于两个假期的请假处理，真的迟到早退了
						continue;
					} else {
						// 未处理的迟到早退
						context.logMessage(getSysAttendMainChiDaoZaoTui(statDetail, fd_jiaqi_type, kmModel));
						dateList.add(statDetail.getFdDate());
					}
				}
			}
		}

		// 重新统计日志表数据
		SysAttendRestatLog restatLog = new SysAttendRestatLog();
		restatLog.setFdBeginDate(new Date());
		restatLog.setFdEndDate(new Date());
		restatLog.setFdStatus(0);
		restatLog.setFdCreateMiss(true);
		restatLog.setDocSubject("restat");
		restatLog.setFdStatUserNames(orgNameList.toString());
		// 保存日志信息
		String log_id = getSysAttendRestatLogService().getBaseDao().add(restatLog);

		// 2.重新统计
		AttendStatThread task = new AttendStatThread();
		task.setDateList(dateList);
		task.setOrgList(orgList);
		task.setFdMethod("restat");
		task.setFdIsCalMissed("true");
		task.setFdOperateType("restat");
		task.setLogId(log_id);
		AttendThreadPoolManager manager = AttendThreadPoolManager.getInstance();
		if (!manager.isStarted()) {
			manager.start();
		}
		manager.submit(task);
	}

	protected boolean is_totaol_ok(SysAttendStat statDetail, int type) throws Exception {
		List<SysAttendBusiness> busList = new ArrayList<SysAttendBusiness>();
		boolean is_tiao_guo = false;
		if (type == fd_leave_type_buru) {
			if (busList.size() == 2) {
				is_tiao_guo = true;
			} else if (busList.size() == 1) {
				float qingjia_shijian = 0;
				SysAttendBusiness sab = busList.get(0);
				qingjia_shijian = (sab.getFdBusEndTime().getTime() - sab.getFdBusStartTime().getTime()) / 3600000.0f;
				is_tiao_guo = qingjia_shijian > 30 ? true : false;
			}
		}

		if (type == fd_leave_type_chanqian) {
			is_tiao_guo = busList.size() == 1 ? true : false;
		}

		return is_tiao_guo;
	}

	/**
	 * 统计日志服务
	 */
	private ISysAttendRestatLogService sysAttendRestatLogService;

	public ISysAttendRestatLogService getSysAttendRestatLogService() {
		if (sysAttendRestatLogService == null) {
			sysAttendRestatLogService = (ISysAttendRestatLogService) SpringBeanUtil
					.getBean("sysAttendRestatLogService");
		}
		return sysAttendRestatLogService;
	}

	/**
	 * 日汇总记录处理
	 * 
	 * @param statDetail
	 * @param fd_jiaqi_type
	 * @param fdProcessId
	 * @throws Exception
	 */
	public String getSysAttendMainChiDaoZaoTui(SysAttendStat statDetail, int fdLeaveType, KmReviewMain kmModel)
			throws Exception {
		System.out.println("开始" + sdfymdhms.format(statDetail.getFdDate()));
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer whereBlock = new StringBuffer("1=1");
		if (statDetail.getDocCreator() != null) {
			whereBlock.append(" and sysAttendMain.docCreator.fdId=:docCretorId");
			hqlInfo.setParameter("docCretorId", statDetail.getDocCreator().getFdId());
		} else {
			throw new NoRecordException();
		}
		if (StringUtil.isNotNull(statDetail.getFdCategoryId())) {
			whereBlock.append(" and sysAttendMain.fdHisCategory.fdId=:fdCategoryId");
			hqlInfo.setParameter("fdCategoryId", statDetail.getFdCategoryId());
		} else {
			throw new NoRecordException();
		}
		// 不包含打卡无效的数据
		whereBlock.append(" and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
		// 异常状态是迟到早退（缺卡不处理）
		whereBlock.append(
				" and (sysAttendMain.fdStatus=2 or sysAttendMain.fdStatus=3) and (sysAttendMain.fdState=null or sysAttendMain.fdState=0)");

		StringBuffer selectOne = new StringBuffer();
		if (statDetail.getFdDate() != null) {
			selectOne.append(whereBlock);
			selectOne.append(" and sysAttendMain.docCreateTime>=:startTime and sysAttendMain.docCreateTime<:endTime");
			selectOne.append(" and (sysAttendMain.fdIsAcross is null or sysAttendMain.fdIsAcross=:fdIsAcross0) ");

			Date startTime = AttendUtil.getDate(statDetail.getFdDate(), 0);
			Date endTime = AttendUtil.getDate(statDetail.getFdDate(), 1);

			System.out.println("---------考勤开始时间" + sdfymdhms.format(startTime) + "至" + sdfymdhms.format(endTime));
			hqlInfo.setParameter("fdIsAcross0", false);
			hqlInfo.setParameter("startTime", startTime);
			hqlInfo.setParameter("endTime", endTime);
			hqlInfo.setWhereBlock(selectOne.toString());
		} else {
			throw new NoRecordException();
		}
		hqlInfo.setOrderBy("sysAttendMain.fdWorkType asc");
		List mainList = new ArrayList<>();
		mainList = sysAttendMainService.findList(hqlInfo);
		int cishu = 0;
		StringBuffer sb_return = new StringBuffer();

		if (mainList != null && !mainList.isEmpty()) {
			for (int h = 0; h < mainList.size(); h++) {
				SysAttendMain main = (SysAttendMain) mainList.get(h);
				long daka = main.getDocCreateTime().getTime() / 60 / 1000;// 打卡时间
				long biaozhun = main.getFdBaseWorkTime().getTime() / 60 / 1000;// 标准打卡时间

				float shicha = 0;

				Date new_work_time = null;
				Date qj_start_time = null;
				Date qj_end_time = null;
				Date work_time = main.getFdBaseWorkTime();
				Date doc_time = main.getDocCreateTime();
				if (main.getFdBusiness() != null) {
					continue;// 如果已经请了其他假期，则跳过
				}
				boolean is_wenti = false;
				int hourCount = 0;

				if (main.getFdWorkType() == AttendConstant.SysAttendMain.FD_WORK_TYPE[0] && main.getFdStatus() == 2) {// 上班且迟到（2）
					shicha = daka - biaozhun;
					qj_start_time = work_time;

					int fdStatus = 1;
					Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
					ca.setTime(work_time);
					if (fdLeaveType == fd_leave_type_buru && cishu < 2) {// 哺乳假每天两次

						if (cishu == 0) {// 可以休半小时或1小时
							if (shicha > 30) {
								hourCount = 60;
								ca.set(Calendar.MINUTE, ca.get(Calendar.MINUTE) + 60);// 时间+1小时
							} else {
								hourCount = 30;
								ca.set(Calendar.MINUTE, ca.get(Calendar.MINUTE) + 30);// 时间+半小时
							}
						} else if (cishu == 1) {// 可以再休半小时
							hourCount = 30;
							ca.set(Calendar.MINUTE, ca.get(Calendar.MINUTE) + 30);// 时间+半小时
						} else {
							continue;
						}
					} else if (fdLeaveType == fd_leave_type_chanqian && cishu < 1) {// 产前工间休息每天一次
						hourCount = 60;
						ca.set(Calendar.MINUTE, ca.get(Calendar.MINUTE) + 60);// 时间+半小时
					} else {
						continue;
					}
					new_work_time = ca.getTime();
					qj_end_time = new_work_time;

					// 针对该情况，基准上班8:30，请假1小时，即9:30，员工 9:45打卡的，那么算迟到，
					if (doc_time.getTime() > new_work_time.getTime()) {
						main.setFdStatus(2);
					} else {
						main.setFdStatus(1);
					}
					sb_return.append("上班" + sdfymdhms.format(doc_time) + ",请假：" + hourCount + "分钟,基准打卡："
							+ sdfymdhms.format(work_time) + ",新基准打卡：" + sdfymdhms.format(new_work_time));
				} else if (main.getFdWorkType() == AttendConstant.SysAttendMain.FD_WORK_TYPE[1]
						&& main.getFdStatus() == 3) {// 上班且 早退（3）
					Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
					ca.setTime(work_time);

					// 下班签到记录（计算早退）
					shicha = biaozhun - daka;
					if (fdLeaveType == fd_leave_type_buru && cishu < 2) {// 哺乳假每天两次

						if (cishu == 0) {// 可以休半小时或1小时

							if (shicha > 30) {
								hourCount = 60;
								ca.set(Calendar.MINUTE, ca.get(Calendar.MINUTE) - 60);// 时间+1小时
							} else {
								hourCount = 30;
								ca.set(Calendar.MINUTE, ca.get(Calendar.MINUTE) - 30);// 时间+半小时
							}
						} else if (cishu == 1) {// 可以再休半小时
							hourCount = 30;
							ca.set(Calendar.MINUTE, ca.get(Calendar.MINUTE) - 30);// 时间+半小时
						} else {
							continue;
						}
					} else if (fdLeaveType == fd_leave_type_chanqian && cishu < 1) {// 产前工间休息每天一次
						hourCount = 60;
						ca.set(Calendar.MINUTE, ca.get(Calendar.MINUTE) - 60);// 时间+半小时
					} else {
						continue;
					}
					new_work_time = ca.getTime();
					qj_start_time = new_work_time;// ca.getTime();
					qj_end_time = work_time;

					// 针对该情况，基准下班18:00，请假1小时，即17:00下班，员工 16:45打卡的，那么算早退
					if (doc_time.getTime() < new_work_time.getTime()) {
						main.setFdStatus(3);
					} else {
						main.setFdStatus(1);
					}
					sb_return.append("下班" + sdfymdhms.format(doc_time) + ",请假：" + hourCount + "分钟,基准打卡："
							+ sdfymdhms.format(work_time) + ",新基准打卡：" + sdfymdhms.format(new_work_time));
				} else {
					continue;
				}
				addSysAttendLactationDetail(main.getDocCreator(), main.getFdWorkDate(), fdLeaveType, qj_start_time,
						qj_end_time, hourCount);
				main.setFdState(2);
				sysAttendMainService.getBaseDao().update(main);
				cishu++;
			}
		}
		return sb_return.toString();
	}

	protected void addSysAttendLactationDetail(SysOrgPerson person, Date date, int type, Date start_time, Date end_time,
			int hourCount) throws Exception {
		SysAttendLactationDetail sald = new SysAttendLactationDetail();
		sald.setFdId(IDGenerator.generateID());
		sald.setDocCreateTime(new Date());
		sald.setFdStartTime(start_time);
		sald.setFdEndTime(end_time);
		sald.setFdDate(date);
		sald.setDocCreator(person);
		sald.setFdType(type);
		sysTimeLeaveDetailService.getBaseDao().add(sald);
	}

	private ISysTimeLeaveDetailService sysTimeLeaveDetailService;

	public void setSysTimeLeaveDetailService(

			ISysTimeLeaveDetailService sysTimeLeaveDetailService) {
		this.sysTimeLeaveDetailService = sysTimeLeaveDetailService;
	}

	public ISysTimeLeaveDetailService getSysTimeLeaveDetailService() {
		if (sysTimeLeaveDetailService == null) {
			sysTimeLeaveDetailService = (ISysTimeLeaveDetailService) SpringBeanUtil
					.getBean("sysTimeLeaveDetailService");
		}
		return sysTimeLeaveDetailService;
	}

	private ISysAttendLactationDetailService sysAttendLactationDetailService;

	public void setSysAttendLactationDetailService(

			ISysAttendLactationDetailService sysAttendLactationDetailService) {
		this.sysAttendLactationDetailService = sysAttendLactationDetailService;
	}

	public ISysAttendLactationDetailService getSysAttendLactationDetailService() {
		if (sysAttendLactationDetailService == null) {
			sysAttendLactationDetailService = (ISysAttendLactationDetailService) SpringBeanUtil
					.getBean("sysAttendLactationDetailService");
		}
		return sysAttendLactationDetailService;
	}

	private ISysOrgCoreService sysOrgCoreService = null;

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	private static final Log log = LogFactory.getLog(HrAttendLactationMonitor.class);

	private IKmReviewMainService kmReviewMainService = (IKmReviewMainService) SpringBeanUtil
			.getBean("kmReviewMainService");

	private ISysNotifyMainCoreService sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil
			.getBean("sysNotifyMainCoreService");

	private ISysAttendMainExcService sysAttendMainExcService = (ISysAttendMainExcService) SpringBeanUtil
			.getBean("sysAttendMainExcService");

	private ISysAttendCategoryService sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil
			.getBean("sysAttendCategoryService");

	private ISysAttendStatService sysAttendStatService = (ISysAttendStatService) SpringBeanUtil
			.getBean("sysAttendStatService");

	private ISysAttendMainService sysAttendMainService = (ISysAttendMainService) SpringBeanUtil
			.getBean("sysAttendMainService");

	private List<Map<String, Object>> getTeShuPerson() throws Exception {

		List<Map<String, Object>> list = new ArrayList<>();
		StringBuffer sb = new StringBuffer();

		// select * from sys_attend_stat where fd_late_time>0 or fd_left_time>0

		// fd_late_time //迟到
		// fd_left_time //早退
		// ID fd_id
		// 申请人1 fd_person_id
		// 假期类型 fd_jiaqi_type
		// 哺乳假开始日期 fd_buru_start_time
		// 哺乳假结束日期 fd_buru_end_time
		// 假期开始日期 fd_gongjian_start_time
		// 假期结束日期 fd_gongjian_end_time
		sb.append("select fd_id,fd_person_id,fd_jiaqi_type,fd_buru_start_time as start_time");
		sb.append(", date_add(fd_buru_end_time, INTERVAL 2 DAY) as end_time ");
		sb.append(" from ekp_lactation_work where fd_jiaqi_type=" + fd_leave_type_buru);
		sb.append(" and fd_buru_start_time <= CURDATE() ");
		sb.append(" and date_add(fd_buru_end_time, INTERVAL 2 DAY) >= CURDATE() ");

		sb.append(" union ");

		// 为什么结束要加2天，因为今天考勤明天才能算出最终的统计
		sb.append("select fd_id,fd_person_id,fd_jiaqi_type,fd_gongjian_start_time as start_time");
		sb.append(", date_add(fd_gongjian_end_time, INTERVAL 2 DAY) as end_time ");
		sb.append(" from ekp_lactation_work where fd_jiaqi_type=" + fd_leave_type_chanqian);
		sb.append(" and fd_gongjian_start_time <= CURDATE() ");
		sb.append(" and date_add(fd_gongjian_end_time, INTERVAL 2 DAY) >= CURDATE() ");

		list = HrCurrencyParams.getListBySql(sb.toString());
		return list;
	}

	private ISysAttendBusinessService sysAttendBusinessService;

	protected ISysAttendBusinessService getSysAttendBusinessService() {
		if (sysAttendBusinessService == null) {
			sysAttendBusinessService = (ISysAttendBusinessService) SpringBeanUtil.getBean("sysAttendBusinessService");
		}
		return sysAttendBusinessService;
	}

}
