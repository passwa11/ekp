package com.landray.kmss.hr.staff.quartz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendRestatLog;
import com.landray.kmss.sys.attend.model.SysAttendStat;
import com.landray.kmss.sys.attend.service.ISysAttendBusinessService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
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
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveDetailService;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;

/**
 * 监控签到模块每日汇总，若是
 * 
 * @author sunny
 * @version 创建时间：2022年11月2日上午10:01:03
 */
public class HrAttendLactationMonitor1 {
	private final int fd_leave_type_buru = 10;// 哺乳假期
	private final int fd_leave_type_chanqian = 6;// 产前工间休息
	private String printLog = "";

	List<Map<String, Object>> main_list = null;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void monitor(SysQuartzJobContext context) throws Exception {
		context.logMessage("产前工间休息员工和哺乳假期员工，缺卡不做假期处理");

		// context.logMessage(createPersonReport());

		// 取出哺乳假期和产前工间休息人员
		// 查他日考勤汇总有无早退或迟到的有效考勤数据
		// 早退或迟到的日期有无在报备范围
		// 若在报备范围，修改有效考勤记录为正常

		// 哺乳假期，每天1小时，可分两次休息，这里只监控迟到和早退的
		// 先看 迟到且早退的情况，迟到30分钟以内，计为正常考勤，迟到超30分钟以上，打开时间 倒退30分钟，其他认为迟到
		// 有迟到无早退 与有早退无迟到 ，迟到或早退
		// 时间低于1小时的情况，视为正常考勤，超过1小时，则打卡时间减1小时，视为迟到或早退，该情况与产前工间休息同一个逻辑

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
				// context.logMessage("员工:" + person_name);
				String start_time = sdf.format(sdf.parse(map.get("start_time") + ""));
				String end_time = sdf.format(sdf.parse(map.get("end_time") + ""));
				int fd_jiaqi_type = Double.valueOf(map.get("fd_jiaqi_type") + "").intValue();

				String fdProcessId = map.get("fd_id") + "";// 后期若要关联可用

				if (!orgList.contains(person_id)) {
					orgList.add(person_id);
				}
				orgNameList.add(person_name);

				KmReviewMain kmModel = (KmReviewMain) kmReviewMainService.findByPrimaryKey(fdProcessId);

				if (!kmModel.getDocStatus().equals("30")) {
					continue;
				}
				String sql_sys_attend_stat = "select * from sys_attend_stat where (fd_late_time>0 or fd_left_time>0) and fd_date >= '"
						+ start_time + "' and fd_date<'" + end_time + "' and doc_creator_id='" + person_id
						+ "' order by fd_date desc";
				List<Map<String, Object>> list_sys_attend_stat = new ArrayList<>();
				list_sys_attend_stat = HrCurrencyParams.getListBySql(sql_sys_attend_stat);

				if (list_sys_attend_stat.size() == 0) {
					context.logMessage(
							person_name + ":" + person_id + " 在 " + start_time + " 与 " + end_time + "期间无迟到早退记录");
					continue;
				}

				context.logMessage("==========考勤开始统计：" + person_name + ":" + person_id + " 在 " + start_time + " 与 "
						+ end_time + (fd_jiaqi_type == 6 ? "产前工间休息" : "哺乳假期"));

				for (Map<String, Object> map_sys_attend_stat : list_sys_attend_stat) {
					SysAttendStat statDetail = (SysAttendStat) sysAttendStatService
							.findByPrimaryKey(map_sys_attend_stat.get("fd_id") + "");
					if (statDetail == null) {
						throw new NoRecordException();
					}

					String is_ok = is_totaol_ok(statDetail, fd_jiaqi_type);
					// 他今天是否有哺乳假或产间休息
					if (is_ok.length() > 0) { // 说明系统已经做了这个人关于两个假期的请假处理，真的迟到早退了
						context.logMessage(sdf.format(statDetail.getFdDate()) + is_ok + "，无符合条件的");
						continue;
					} else {
						context.logMessage(sdf.format(statDetail.getFdDate()) + is_ok + "，开始计算");
						List<SysAttendBusiness> tmpList = new ArrayList<SysAttendBusiness>();

						// 未处理的迟到早退
						tmpList = getSysAttendMainChiDaoZaoTui(statDetail, fd_jiaqi_type, kmModel);

						if (tmpList.size() > 0) {
							context.logMessage(printLog);
							context.logMessage(sdf.format(statDetail.getFdDate()));
							for (SysAttendBusiness bus : tmpList) {

								// 1.新建请假明细
								SysTimeLeaveDetail leaveDetail = addLeaveDetail(bus);
								// 3.保存流程表单数据
								bus.setFdBusDetailId(leaveDetail.getFdId());
								getSysAttendBusinessService().add(bus);
								SysTimeLeaveDetail leaveDetail_new = (SysTimeLeaveDetail) getSysTimeLeaveDetailService()
										.findByPrimaryKey(leaveDetail.getFdId());

								leaveDetail_new.setFdUpdateAttendStatus(1);
								getSysTimeLeaveDetailService().update(leaveDetail_new);

								if (main_list.size() > 0) {
									for (Map<String, Object> mapaa : main_list) {
										String busID = mapaa.get("busID") + "";
										if (!busID.equals(bus.getFdId())) {
											continue;
										}
										String mainID = mapaa.get("mainID") + "";
										Date baseWorkTime = sdfymdhms.parse(mapaa.get("baseWorkTime") + "");
										SysAttendMain sysAttendMain = (SysAttendMain) sysAttendMainService
												.findByPrimaryKey(mainID);
										SysAttendBusiness sysAttendBusiness = (SysAttendBusiness) sysAttendBusinessService
												.findByPrimaryKey(busID);

										Date qj_start_time = sdfymdhms.parse(mapaa.get("qj_start_time") + "");
										Date qj_end_time = sdfymdhms.parse(mapaa.get("qj_end_time") + "");

										// String logDesc = person_name +
										// "baseWorkTime:"
										// +
										// sdfymdhms.format(sysAttendMain.getFdBaseWorkTime())
										// + " 改为 "
										// + sdfymdhms.format(baseWorkTime) +
										// "，用户签卡:"
										// +
										// sdfymdhms.format(sysAttendMain.getDocCreateTime());

										String logDesc = person_name + "开始时间:" + sdfymdhms.format(qj_start_time)
												+ " 结束时间 " + sdfymdhms.format(qj_end_time) + "，用户签卡:"
												+ sdfymdhms.format(sysAttendMain.getDocCreateTime());

										context.logMessage(logDesc);
										// sysAttendMain.setFdState(2);
										// sysAttendMain.setFdBaseWorkTime(baseWorkTime);

										sysAttendMain.setFdBusiness(sysAttendBusiness);
										// sysAttendMain.setFdDesc(logDesc);

										context.logMessage("==========" + logDesc);

										sysAttendMainService.getBaseDao().update(sysAttendMain);

									}
								}

							}
						} else {
							context.logMessage(sdf.format(statDetail.getFdDate()) + ",未找到未处理的迟到早退:" + printLog);
						}
						dateList.add(statDetail.getFdDate());

					}
				}
				// -------------------------------每个人处理完了------------
			}

			// 2.重新统计
			// AttendStatThread task = new AttendStatThread();
			// task.setDateList(dateList);
			// task.setOrgList(orgList);
			// task.setFdMethod("restat");
			// task.setFdIsCalMissed("true");
			// task.setFdOperateType("restat");//restat create
			// AttendThreadPoolManager manager =
			// AttendThreadPoolManager.getInstance();
			// if (!manager.isStarted()) {
			// manager.start();
			// }
			// manager.submit(task);
		}

		context.logMessage(createPersonReport());
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
	 * 返回跳过原因
	 * 
	 * @param statDetail
	 * @param type
	 * @return
	 * @throws Exception
	 */
	protected String is_totaol_ok(SysAttendStat statDetail, int type) throws Exception {
		List<SysAttendBusiness> busList = new ArrayList<SysAttendBusiness>();
		busList = getgetBusinessListByPersonDay(statDetail, 5);
		StringBuffer sb = new StringBuffer();
		if (type == fd_leave_type_buru) {
			if (busList.size() == 2) {
				sb.append("哺乳假跳过，当天已有两次假期");
			} else if (busList.size() == 1) {
				float qingjia_shijian = 0;
				SysAttendBusiness sab = busList.get(0);
				qingjia_shijian = (sab.getFdBusEndTime().getTime() - sab.getFdBusStartTime().getTime()) / 60000.0f;

				if (qingjia_shijian > 30) {
					sb.append("哺乳假跳过，当天已有1次假期:" + sdfymdhms.format(sab.getFdBusStartTime()) + "至"
							+ sdfymdhms.format(sab.getFdBusEndTime()));
				}

			}
		}

		if (type == fd_leave_type_chanqian) {

			if (busList.size() == 1) {
				SysAttendBusiness sab = busList.get(0);
				sb.append("产前工间假跳过，当天已有1次假期" + sdfymdhms.format(sab.getFdBusStartTime()) + "至"
						+ sdfymdhms.format(sab.getFdBusEndTime()));
			}
		}

		return sb.toString();
	}

	protected List<SysAttendBusiness> getgetBusinessListByPersonDay(SysAttendStat statDetail, int type)
			throws Exception {
		List<SysAttendBusiness> busList = new ArrayList<SysAttendBusiness>();
		List<String> orgIdList = new ArrayList<String>();
		orgIdList.add(statDetail.getDocCreator().getFdId());
		// 获取用户的哺乳假期或产前工间休息
		List<Integer> fdTypes = new ArrayList<Integer>();
		fdTypes.add(type);

		Date endTime = AttendUtil.getDate(statDetail.getFdDate(), 1);
		busList = this.getSysAttendBusinessService().findBussList(orgIdList, statDetail.getFdDate(), endTime, fdTypes);

		return busList;
	}

	/**
	 * 日汇总记录处理
	 * 
	 * @param statDetail
	 * @param fd_jiaqi_type
	 * @param fdProcessId
	 * @throws Exception
	 */
	public List<SysAttendBusiness> getSysAttendMainChiDaoZaoTui(SysAttendStat statDetail, int fdLeaveType,
			KmReviewMain kmModel) throws Exception {
		System.out.println("开始" + sdfymdhms.format(statDetail.getFdDate()));

		printLog = "";
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
		whereBlock.append(" and (sysAttendMain.fdStatus=2 or sysAttendMain.fdStatus=3) ");
		whereBlock.append(" and (sysAttendMain.fdState=null or sysAttendMain.fdState=0)");
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
		// 上班0 下班1
		hqlInfo.setOrderBy("sysAttendMain.fdWorkType asc");
		List mainList = new ArrayList<>();
		mainList = sysAttendMainService.findList(hqlInfo);

		List<SysAttendBusiness> busList = new ArrayList<SysAttendBusiness>();

		List<SysAttendMain> mainList2 = new ArrayList<SysAttendMain>();

		float lei_ji_day_fenzhong = 0;// 每天累计时长
		List<SysAttendBusiness> busList_yiCunZai = getgetBusinessListByPersonDay(statDetail, 5);
		int cishu = busList_yiCunZai.size();
		if (mainList != null && !mainList.isEmpty()) {
			int cishu_chushihua = 0;
			float lei_ji_day_fenzhong_chushihua = 0;

			for (Object obj : mainList) {
				SysAttendMain main = (SysAttendMain) obj;
				if (main.getFdBusiness() != null) {
					if (cishu > 0) {
						cishu_chushihua = cishu;
					} else {
						cishu_chushihua++;
					}
					lei_ji_day_fenzhong_chushihua = lei_ji_day_fenzhong_chushihua
							+ main.getFdBusiness().getFdCountHour();

					if (lei_ji_day_fenzhong_chushihua >= 1) {
						return busList;
					}

					if (fdLeaveType == fd_leave_type_chanqian) {// 产前工间休息每天1次
						if (cishu_chushihua >= 1) {
							return busList;
						}
					}

					if (fdLeaveType == fd_leave_type_buru) {// 哺乳假每天两次
						if (cishu_chushihua >= 2 || lei_ji_day_fenzhong_chushihua >= 1) {
							return busList;
						}
					}

				}
				mainList2.add(main);

			}
		}

		if (mainList2 != null && !mainList2.isEmpty()) {
			for (SysAttendMain main : mainList2) {
				long daka = main.getDocCreateTime().getTime() / 60 / 1000;// 打卡时间
				long biaozhun = main.getFdBaseWorkTime().getTime() / 60 / 1000;// 标准打卡时间

				float shicha = 0;

				int fdTotalTime = 0;// 请假分钟数
				Date new_time = null;
				Date qj_start_time = null;
				Date qj_end_time = null;
				Date work_time = main.getFdBaseWorkTime();
				if (main.getFdBusiness() != null) {
					continue;// 如果已经请了其他假期，则跳过
				}

				if (lei_ji_day_fenzhong >= 60) {
					continue;
				}
				if (main.getFdWorkType() == AttendConstant.SysAttendMain.FD_WORK_TYPE[0]) {

					printLog = "标准签卡时间" + sdfymdhms.format(work_time) + ";上班，";
					// 上班签到记录（计算迟到）
					shicha = daka - biaozhun;
					qj_start_time = work_time;
					Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
					ca.setTime(work_time);
					if (fdLeaveType == fd_leave_type_buru) {// 哺乳假每天两次

						if (cishu == 0) {// 可以休半小时或1小时

							if (shicha > 30) {
								fdTotalTime = 60;
								lei_ji_day_fenzhong = lei_ji_day_fenzhong + 60;
								ca.set(Calendar.MINUTE, ca.get(Calendar.MINUTE) + 60);// 时间+1小时
							} else {
								fdTotalTime = 30;
								lei_ji_day_fenzhong = lei_ji_day_fenzhong + 30;
								ca.set(Calendar.MINUTE, ca.get(Calendar.MINUTE) + 30);// 时间+半小时
							}
							printLog = printLog + "第一次休哺乳假期时长" + fdTotalTime + "分钟";
						} else if (cishu == 1 && lei_ji_day_fenzhong <= 30) {// 可以再休半小时
							fdTotalTime = 30;
							lei_ji_day_fenzhong = lei_ji_day_fenzhong + 30;
							ca.set(Calendar.MINUTE, ca.get(Calendar.MINUTE) + 30);// 时间+半小时

							printLog = printLog + "第二次休哺乳假期时长" + fdTotalTime + "分钟";
						} else {
							continue;
						}
					} else if (fdLeaveType == fd_leave_type_chanqian) {// 产前工间休息每天一次
						if (cishu == 0 && lei_ji_day_fenzhong == 0) {
							fdTotalTime = 60;
							lei_ji_day_fenzhong = lei_ji_day_fenzhong + 60;
							ca.set(Calendar.MINUTE, ca.get(Calendar.MINUTE) + 60);// 时间+半小时

							printLog = printLog + "产前工间假期时长" + fdTotalTime + "分钟";
						} else {
							continue;
						}
					} else {
						continue;
					}
					new_time = ca.getTime();
					qj_end_time = new_time;

				} else if (main.getFdWorkType() == AttendConstant.SysAttendMain.FD_WORK_TYPE[1]) {
					printLog = "标准签卡时间" + sdfymdhms.format(work_time) + ";下班，";
					Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
					ca.setTime(work_time);

					// 下班签到记录（计算早退）
					shicha = biaozhun - daka;
					if (fdLeaveType == fd_leave_type_buru) {// 哺乳假每天两次

						if (cishu == 0) {// 可以休半小时或1小时

							if (shicha > 30) {
								fdTotalTime = 60;
								lei_ji_day_fenzhong = lei_ji_day_fenzhong + 60;
								ca.set(Calendar.MINUTE, ca.get(Calendar.MINUTE) - 60);// 时间-1小时
							} else {
								fdTotalTime = 30;
								lei_ji_day_fenzhong = lei_ji_day_fenzhong + 30;
								ca.set(Calendar.MINUTE, ca.get(Calendar.MINUTE) - 30);// 时间-半小时
							}
							printLog = printLog + "第一次休哺乳假期时长" + fdTotalTime + "分钟";
						} else if (cishu == 1 && lei_ji_day_fenzhong <= 30) {// 可以再休半小时
							fdTotalTime = 30;
							lei_ji_day_fenzhong = lei_ji_day_fenzhong + 30;
							ca.set(Calendar.MINUTE, ca.get(Calendar.MINUTE) - 30);// 时间-半小时
							printLog = printLog + "第二次休哺乳假期时长" + fdTotalTime + "分钟";
						} else {
							continue;
						}
					} else if (fdLeaveType == fd_leave_type_chanqian) {// 产前工间休息每天一次
						if (cishu == 0 && lei_ji_day_fenzhong == 0) {
							fdTotalTime = 60;
							lei_ji_day_fenzhong = lei_ji_day_fenzhong + 60;
							ca.set(Calendar.MINUTE, ca.get(Calendar.MINUTE) - 60);// 时间-半小时
							printLog = printLog + "产前工间假期时长" + fdTotalTime + "分钟";
						} else {
							continue;
						}
					} else {
						continue;
					}
					new_time = ca.getTime();
					qj_start_time = new_time;// ca.getTime();
					qj_end_time = work_time;
				} else {
					continue;
				}
				cishu++;

				SysAttendBusiness fdBusiness = getBusinessModel(qj_start_time, qj_end_time, main.getDocCreator(),
						kmModel, fdLeaveType , fdTotalTime);

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("mainID", main.getFdId());
				map.put("busID", fdBusiness.getFdId());
				map.put("baseWorkTime", sdfymdhms.format(new_time));
				map.put("qj_start_time", sdfymdhms.format(qj_start_time));
				map.put("qj_end_time", sdfymdhms.format(qj_end_time));
				main_list.add(map);

				busList.add(fdBusiness);

			}
		}

		System.out.println("结束" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(statDetail.getFdDate()));
		return busList;
	}

	private SysAttendBusiness getBusinessModel(Date fdBusStartTime, Date fdBusEndTime, SysOrgPerson target,
			KmReviewMain kmModel, int fdLeaveType,int fdTotalTime) throws Exception {
		SysAttendBusiness sysAttendBusiness = new SysAttendBusiness();
		sysAttendBusiness.setFdBusStartTime(fdBusStartTime);
		sysAttendBusiness.setFdBusEndTime(fdBusEndTime);
		sysAttendBusiness.setFdProcessId(kmModel.getFdId());
		String[] targets = { target.getFdId() };
		sysAttendBusiness.setFdTargets(getSysOrgCoreService().findByPrimaryKeys(targets));
		sysAttendBusiness.setFdType(5);
		sysAttendBusiness.setDocUrl(AttendUtil.getDictUrl(kmModel, kmModel.getFdId()));
		SysTimeLeaveRule sysTimeLeaveRule = AttendUtil.getLeaveRuleByType(fdLeaveType);

		sysAttendBusiness.setFdBusType(fdLeaveType);

		if (fdTotalTime == 30) {
			sysAttendBusiness.setFdCountHour(Float.valueOf("0.5"));
		} else if (fdTotalTime == 60) {
			sysAttendBusiness.setFdCountHour(Float.valueOf("1.0"));
		}
		sysAttendBusiness.setFdStatType(3);// 固定按小时
		sysAttendBusiness.setDocCreateTime(new Date());
		sysAttendBusiness.setFdLeaveName(sysTimeLeaveRule.getFdName());
		sysAttendBusiness.setFdProcessName(kmModel.getDocSubject());
		return sysAttendBusiness;
	}

	/**
	 * 新建请假明细
	 *
	 * @param business
	 * @return
	 * @throws Exception
	 */
	protected SysTimeLeaveDetail addLeaveDetail(SysAttendBusiness business) throws Exception {
		// System.out.println("fdBusiness-11111111------leaveDetail ----------:"
		// + business.getFdId());
		SysTimeLeaveDetail leaveDetail = new SysTimeLeaveDetail();
		leaveDetail.setFdId(business.getFdId());
		List<SysOrgPerson> personList = getSysOrgCoreService().expandToPerson(business.getFdTargets());
		SysOrgPerson person = personList.get(0);
		leaveDetail.setFdLeaveName(business.getFdLeaveName());
		SysTimeLeaveRule leaveRule = AttendUtil.getLeaveRuleByType(business.getFdBusType());
		String fdLeaveType = "";
		if (leaveRule != null) {
			fdLeaveType = leaveRule.getFdSerialNo();
		}
		leaveDetail.setFdLeaveType(fdLeaveType);
		leaveDetail.setFdPerson(person);
		leaveDetail.setFdStartTime(business.getFdBusStartTime());
		leaveDetail.setFdEndTime(business.getFdBusEndTime());
		leaveDetail.setFdLeaveTime(Float.valueOf(business.getFdCountHour() / 24 / 60));// 请假/加班天数
		leaveDetail.setFdTotalTime(business.getFdCountHour());// 请假/加班分钟数(代替fdLeaveTime)
		leaveDetail.setFdOprType(1);
		leaveDetail.setFdOprStatus(2);// 扣减失败
		leaveDetail.setFdOprDesc(business.getFdLeaveName());
		leaveDetail.setFdReviewId(business.getFdProcessId());
		leaveDetail.setFdReviewName(business.getFdProcessName());
		leaveDetail.setFdStatType(AttendConstant.FD_STAT_TYPE[3]);// 按小时请假
		leaveDetail.setDocCreateTime(new Date());
		leaveDetail.setDocCreator(UserUtil.getUser());
		leaveDetail.setFdIsUpdateAttend(true);
		// 设置场所
		leaveDetail.setAuthArea(business.getAuthArea());
		System.out.println("fdBusiness-222222-----leaveDetail -----:" + business.getFdId());
		getSysTimeLeaveDetailService().add(leaveDetail);
		return leaveDetail;
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

	private String createPersonReport() throws Exception {

		List<Map<String, Object>> list = new ArrayList<>();
		list = getTeShuPerson();
		StringBuffer sb = new StringBuffer();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			List<String> orgList = new ArrayList<String>();

			orgList.add(map.get("fd_person_id") + "");

			String start_time = map.get("start_time") + "";
			String end_time = map.get("end_time") + "";

			Calendar ca_start = Calendar.getInstance();
			ca_start.setTime(sdf.parse(start_time));

			List<Date> dateList = new ArrayList<Date>();

			Calendar ca_end = Calendar.getInstance();
			ca_end.setTime(sdf.parse(end_time));

			Calendar ca_today = Calendar.getInstance();
			ca_today.setTime(new Date());

			if (ca_start.after(ca_end)) {
				sb.append(map.get("fd_person_id") + " 假期开始时间结束时间有问题（" + start_time + " 至 " + end_time + "），跳过");
				break;
			} else {

				dateList.add(sdf.parse(start_time));
				sb.append(map.get("fd_person_id") + ",假期:" + start_time);
				do {
					ca_start.add(Calendar.DATE, +1);

					sb.append("," + sdf.format(ca_start.getTime()));
					dateList.add(sdf.parse(sdf.format(ca_start.getTime())));

				} while (!sdf.format(ca_start.getTime()).equals(sdf.format(ca_today.getTime())));

				dateList.add(sdf.parse(end_time));
				sb.append("," + end_time);
			}

			// 2.重新统计
			AttendStatThread task = new AttendStatThread();
			task.setDateList(dateList);
			task.setOrgList(orgList);
			task.setFdMethod("restat");
			task.setFdIsCalMissed("false");
			task.setFdOperateType("create");// restat create
			AttendThreadPoolManager manager = AttendThreadPoolManager.getInstance();
			if (!manager.isStarted()) {
				manager.start();
			}
			manager.submit(task);
		}
		return sb.toString();
	}

	private ISysAttendBusinessService sysAttendBusinessService;

	protected ISysAttendBusinessService getSysAttendBusinessService() {
		if (sysAttendBusinessService == null) {
			sysAttendBusinessService = (ISysAttendBusinessService) SpringBeanUtil.getBean("sysAttendBusinessService");
		}
		return sysAttendBusinessService;
	}

}
