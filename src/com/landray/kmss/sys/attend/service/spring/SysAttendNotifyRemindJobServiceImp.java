package com.landray.kmss.sys.attend.service.spring;

import com.landray.kmss.common.concurrent.KMSSCommonThreadUtil;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendNotifyRemindLog;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.ISysAttendNotifyRemindJobService;
import com.landray.kmss.sys.attend.service.ISysAttendNotifyRemindLogService;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.log.model.SysLogJob;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.quartz.scheduler.SysQuartzJobContextImp;
import com.landray.kmss.sys.time.interfaces.ISysTimeCountService;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * 签到服务相关提醒定时任务
 * 
 * @author linxiuxian
 *
 */
public class SysAttendNotifyRemindJobServiceImp
		implements ISysAttendNotifyRemindJobService {
	private ISysAttendCategoryService sysAttendCategoryService;
	private ISysAttendCategoryService getSysAttendCategoryService(){
		if(sysAttendCategoryService ==null){
			sysAttendCategoryService= (ISysAttendCategoryService) SpringBeanUtil.getBean("sysAttendCategoryService");
		}
		return sysAttendCategoryService;
	}
	
	private ISysTimeCountService sysTimeCountService;
	private ISysTimeCountService getSysTimeCountService(){
		if(sysTimeCountService ==null){
			sysTimeCountService= (ISysTimeCountService) SpringBeanUtil.getBean("sysTimeCountService");
		}
		return sysTimeCountService;
	}
	private ISysNotifyMainCoreService sysNotifyMainCoreService;
	private ISysNotifyMainCoreService getSysNotifyMainCoreService(){
		if(sysNotifyMainCoreService ==null){
			sysNotifyMainCoreService= (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}
	private ISysAttendNotifyRemindLogService sysAttendNotifyRemindLogService;
	private ISysAttendNotifyRemindLogService getSysAttendNotifyRemindLogService(){
		if(sysAttendNotifyRemindLogService ==null){
			sysAttendNotifyRemindLogService= (ISysAttendNotifyRemindLogService) SpringBeanUtil.getBean("sysAttendNotifyRemindLogService");
		}
		return sysAttendNotifyRemindLogService;
	}
	private ISysAttendMainService sysAttendMainService;
	private ISysAttendMainService getSysAttendMainService(){
		if(sysAttendMainService ==null){
			sysAttendMainService= (ISysAttendMainService) SpringBeanUtil.getBean("sysAttendMainService");
		}
		return sysAttendMainService;
	}
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendNotifyRemindJobServiceImp.class);

	@Override
	public void execute(SysQuartzJobContext jobContext) throws Exception {
		 sendNotifyRemindOfTimeArea(jobContext);
	}

	// 排班制上下班提醒
	private void sendNotifyRemindOfTimeArea(SysQuartzJobContext jobContext) {
		logger.debug("排班考勤提醒定时任务启动...");
		try {
			List<SysAttendCategory> cateList = getSysAttendCategoryService().findList(
							"sysAttendCategory.fdStatus=1 and sysAttendCategory.fdType=1 and sysAttendCategory.fdShiftType=1",
							null);
			if (cateList.isEmpty()) {
				logger.debug("当前没有排班考勤组");
				return;
			}
			SysQuartzJobContextImp context = (SysQuartzJobContextImp) jobContext;
			SysLogJob logJob = context.getLogModel();
			Date fdStartTime = logJob.getFdStartTime();
			jobContext.logMessage("排班考勤提醒定时任务运行时间fdStartTime:" + fdStartTime);
			CountDownLatch latch=new CountDownLatch(cateList.size());
			for (SysAttendCategory categoryTemp : cateList) {
				SysAttendCategory category = CategoryUtil.getLastVersionCategoryFdId(categoryTemp.getFdId(),new Date());
				if(category ==null){
					logger.warn("请先同步历史数据:" + categoryTemp.getFdId());
					latch.countDown();
					continue;
				}

				if (!isSendNotifyRemind(category, null)) {
					jobContext.logMessage("当前排班考勤组不需要考勤上下班提醒,忽略处理!categoryId:"
							+ category.getFdId());
					latch.countDown();
					continue;
				} 
				SendNotifyReminTask task=new SendNotifyReminTask(category,jobContext,fdStartTime,latch);
				//异步处理
				KMSSCommonThreadUtil.execute(task);
			}
			latch.await();
			jobContext.logMessage("排班考勤提醒定时任务完成...");
		} catch (Exception e) {
			logger.error("排班制上下班提醒任务失败:" + e.getMessage(), e);
			jobContext.logMessage("排班制上下班提醒任务失败:" + e.getMessage());
		}
	}

	/**
	 * 发送待办的异步线程
	 */
	class SendNotifyReminTask implements Runnable{
		SysAttendCategory category;
		SysQuartzJobContext jobContext;
		Date fdStartTime;
		CountDownLatch latch;
		public SendNotifyReminTask(SysAttendCategory category, SysQuartzJobContext jobContext, Date fdStartTime,CountDownLatch latch) {
			this.category = category;
			this.jobContext = jobContext;
			this.fdStartTime = fdStartTime;
			this.latch = latch;
		}

		@Override
		public void run() {
			runNotifyRemin(category,fdStartTime,jobContext,latch);
		}
	}
	private void runNotifyRemin(SysAttendCategory category,Date fdStartTime,SysQuartzJobContext jobContext,CountDownLatch latch){
		TransactionStatus status = null;
		boolean isException =false;
		try {
			long totaltime = System.currentTimeMillis();
			long caltime = System.currentTimeMillis();
			//提醒的日期
			Date remindData = getTurthDay(category);
			List<SysOrgElement> orgList = getSysAttendCategoryService().getAttendPersons(category.getFdId(),remindData);

			jobContext.logMessage(String.format("考勤组：%s,人员数量：%s",category.getFdName(),orgList.size()));

			String temp = "获取考勤组" + category.getFdName() + "人员的信息耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
			jobContext.logMessage(temp);
			if (orgList.isEmpty()) {
				jobContext.logMessage("当前排班考勤组没有考勤人员,忽略处理!categoryId:"
						+ category.getFdId());
				return;
			}
			status = TransactionUtils.beginNewTransaction();

			// 区域组与考勤用户映射关系
			Map<String, List<SysOrgElement>> userTimeAreaMap = new HashMap<String, List<SysOrgElement>>();
			Set<SysTimeArea> areaSet = new HashSet<SysTimeArea>();
			caltime = System.currentTimeMillis();
			for (SysOrgElement ele : orgList) {
				SysTimeArea timeArea = getSysTimeCountService().getTimeArea(ele);
				if (timeArea == null) {
					jobContext.logMessage("当前排班考勤用户没有配置对应区域组,忽略处理!orgId:"
							+ ele.getFdId());
					continue;
				}
				areaSet.add(timeArea);
				if (!userTimeAreaMap.containsKey(timeArea.getFdId())) {
					userTimeAreaMap.put(timeArea.getFdId(), new ArrayList<SysOrgElement>());
				}
				List<SysOrgElement> userList = userTimeAreaMap
						.get(timeArea.getFdId());
				userList.add(ele);
			}
			temp = "获取区域组与考勤用户映射关系" + category.getFdName() + "的耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
			jobContext.logMessage(temp);
			for (SysTimeArea area : areaSet) {
				List<SysOrgElement> userList = userTimeAreaMap
						.get(area.getFdId());
				if (userList == null || userList.isEmpty()) {
					jobContext.logMessage("区域组没有相应人员!areaId:" + area.getFdId());
					continue;
				}
				// 按人员排班
				if (area.getFdIsBatchSchedule()) {
					caltime = System.currentTimeMillis();
					sendAttendRemindOfAreaBatchSchedule(area, userList,category, fdStartTime);
					temp = "发送区域组" + area.getFdName() + "人员的信息耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
					jobContext.logMessage(temp);
					continue;
				}
				List<Map<String, Object>> signTimes = getSysAttendCategoryService().getAttendSignTimes(category,remindData , userList.get(0));
				if (signTimes.isEmpty()) {
					jobContext.logMessage("区域组当天没有排班!areaId:" + area.getFdId()
							+ ";categoryId:" + category.getFdId());
					continue;
				}
				sendAttendRemindNotify(signTimes, category, userList,fdStartTime);
			}
			temp = "发送考勤组" + category.getFdName() + "_" + orgList.size() + "的信息总耗时(秒)：" + (System.currentTimeMillis() - totaltime) / 1000;
			jobContext.logMessage(temp);
		}catch (Exception e){
			isException =true;
		}finally {
			latch.countDown();
			if(status !=null && isException){
				TransactionUtils.rollback(status);
			}else if(status !=null){
				TransactionUtils.commit(status);
			}
		}
	}

	private void sendAttendRemindNotify(List<Map<String, Object>> signTimes,
			SysAttendCategory category, List<SysOrgElement> userList,
			Date fdRunTime) throws Exception {
		for (Map<String, Object> m : signTimes) {
			Date signTime = (Date) m.get("signTime");
			Integer fdWorkType = (Integer) m.get("fdWorkType");
			if (this.isSendNotifyRemind(category, fdWorkType)) {
				if (isNotifyRemindTime(category, fdWorkType, signTime,fdRunTime)) {
					// 用户组分割
					int maxCount = 100;
					List<List> groupLists = new ArrayList<List>();
					if (userList.size() <= maxCount) {
						groupLists.add(userList);
					} else {
						groupLists = AttendUtil.splitList(userList, maxCount);
					}
					for (int i = 0; i < groupLists.size(); i++) {
						// 已经打卡不发送通知
						Set<SysOrgElement> canNotify = genUserList(signTimes,groupLists.get(i), getTurthDay(category), signTime);
						if (canNotify != null && !canNotify.isEmpty()) {
							sendAttendNotifyRemind(category, fdWorkType, new ArrayList(canNotify),signTime);
						}
					}
					break;
				} else {
					logger.debug("当前班次此刻还未到提醒时间,忽略!categoryid:"
							+ category.getFdId() + ";fdWorkType:"
							+ fdWorkType.intValue() + ";signTime:"
							+ signTime);
				}
			} else {
				logger.debug("当前班次不需要打卡提醒!categoryid:"
						+ category.getFdId() + ";fdWorkType:"
						+ fdWorkType.intValue() + ";signTime:"
						+ signTime);
			}
		}
	}

	/**
	 * 筛选掉已经有打卡的用户
	 * 
	 * @param workTimesList
	 *            班次信息
	 * @param userList
	 *            用户信息
	 * @param fdRunTime
	 *            当前时间
	 * @param signTime
	 *            班次打卡时间
	 * @return
	 * @throws Exception
	 */
	private Set<SysOrgElement> genUserList(
			List<Map<String, Object>> workTimesList,
			List<SysOrgElement> userList, Date fdRunTime, Date signTime)
			throws Exception {
		Set<SysOrgElement> rtnList = new HashSet<>();
		List<Map<String, Object>> tempWorkTimeList = new ArrayList<>();
		tempWorkTimeList.addAll(workTimesList);
		if (userList == null || userList.isEmpty()) {
			return rtnList;
		}
		for (SysOrgElement ele : userList) {
			List<SysAttendMain> mainList = getSysAttendMainService().findList(ele.getFdId(), fdRunTime,fdRunTime);
			boolean isSign = false;
			if (mainList != null && !mainList.isEmpty()) {
				getSysAttendCategoryService().doWorkTimesRender(tempWorkTimeList,
						mainList);
				Map<String, Object> workTime = null;
				for (Map<String, Object> tempWorkTime : tempWorkTimeList) {
					Date tempSignTime = (Date) tempWorkTime.get("signTime");
					if (AttendUtil.getHMinutes(tempSignTime) == AttendUtil
							.getHMinutes(signTime)) {
						workTime = tempWorkTime;
					}
				}
				if (workTime == null) {
					rtnList.addAll(userList);
					return rtnList;
				}

				for (SysAttendMain main : mainList) {
					String fdWorkKey = StringUtil.isNotNull(main.getFdWorkKey())
							? main.getFdWorkKey() : "";
					String fdWorkId = (String) workTime.get("fdWorkTimeId");
					Integer fdWorkType = (Integer) workTime.get("fdWorkType");
					if (fdWorkKey.equals(fdWorkId)
							&& main.getFdWorkType().equals(fdWorkType)) {
						isSign = true;
						continue;
					}
				}
			}
			if (!isSign) {
				rtnList.add(ele);
			}
		}
		return rtnList;
	}

	/**
	 * 区域组排班为个人排班时
	 * 
	 * @throws Exception
	 */
	private void sendAttendRemindOfAreaBatchSchedule(SysTimeArea area,
			List<SysOrgElement> userList, SysAttendCategory category,
			Date fdRunTime)
			throws Exception {
		for (SysOrgElement org : userList) {
			List<Map<String, Object>> signTimes = sysAttendCategoryService
					.getAttendSignTimes(category, getTurthDay(category), org);
			if (signTimes.isEmpty()) {
				logger.warn("区域组用户当天没有排班!areaId:" + area.getFdId()
						+ ";categoryId:" + category.getFdId() + ";userId:"
						+ org.getFdId());
				continue;
			}
			List<SysOrgElement> tempList = new ArrayList<>();
			tempList.add(org);
			sendAttendRemindNotify(signTimes, category, tempList,fdRunTime);
		}
	}

	/**
	 * 获取是否跨天打卡的日期
	 * 
	 * @param category
	 * @return
	 */
	private Date getTurthDay(SysAttendCategory category) {
		Date startTime = category.getFdStartTime();
		Integer endDay = category.getFdEndDay();
		Date now = new Date();
		if (startTime == null || !Integer.valueOf(2).equals(endDay)) {
			return now;
		}
		int day = 0;
		// 超过最早打卡时间就不发送跨天的打卡消息了
		if (AttendUtil.getHMinutes(now) < AttendUtil
				.getHMinutes(startTime)) {
			day = -1;
		}
		return AttendUtil.addDate(now, day);
	}

	/**
	 * 根据时间判断是否发送通知
	 * 
	 * @param category
	 * @param fdWorkType
	 * @param signTime
	 * @param fdStartTime
	 * @return
	 */
	private boolean isNotifyRemindTime(SysAttendCategory category,
			int fdWorkType, Date signTime, Date fdStartTime) {
		int signTimeMins = AttendUtil.getHMinutes(signTime);
		if (fdWorkType == 0) {
			signTimeMins = signTimeMins - category.getFdNotifyOnTime();
		} else {
			signTimeMins = signTimeMins + category.getFdNotifyOffTime();
		}
		if (signTimeMins == AttendUtil.getHMinutes(fdStartTime)) {
			return true;
		}
		return false;
	}

	/**
	 * 发送提醒
	 * 
	 * @param category
	 * @param fdWorkType
	 * @param targets
	 * @throws Exception
	 */
	private void sendAttendNotifyRemind(SysAttendCategory category,int fdWorkType, List targets, Date signTime) {
		try {
			NotifyContext notifyContext = getSysNotifyMainCoreService().getContext(null);
			String subject = "";
			String signTimeTxt = DateUtil.convertDateToString(signTime,
					"HH:mm");
			Date workTime =new Date();
			workTime.setHours(signTime.getHours());
			if (fdWorkType == AttendConstant.SysAttendMain.FD_WORK_TYPE[0]) {
				subject = ResourceUtil.getString(
						"sysAttendCategory.notify.remind.onwork.subject",
						"sys-attend", null, new String[] { signTimeTxt,
								String.valueOf(category.getFdNotifyOnTime()) });
				workTime.setMinutes(signTime.getMinutes());
			} else if (fdWorkType == AttendConstant.SysAttendMain.FD_WORK_TYPE[1]) {
				subject = ResourceUtil.getString(
						"sysAttendCategory.notify.remind.offwork.subject",
						"sys-attend", null, new String[] { signTimeTxt, String
								.valueOf(category.getFdNotifyOffTime()) });
				Integer min=category.getFdNotifyOffTime()==null?0:category.getFdNotifyOffTime();
				workTime.setMinutes(signTime.getMinutes()+min*2);
			}
			notifyContext.setFdEndTime(workTime.getTime());
			String title = ResourceUtil.getString(
					"sysAttendCategory.notify.attend.remind.title",
					"sys-attend");
			addRemindLog(subject, targets, "todo", new Date(),
					category.getFdId(),
					category.getFdName());
			notifyContext.setSubject(subject);
			notifyContext.setContent(subject);
			notifyContext.setNotifyTarget(targets);
			notifyContext.setNotifyType("todo");
			// 钉钉提醒不消失，故改成待阅
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
			notifyContext.setLink("/sys/attend/mobile/index_forward.jsp");
			notifyContext.setFdAppType("weixin;ding;wxwork");
			notifyContext.setFdNotifyEKP(false);
			getSysNotifyMainCoreService().sendNotify(category, notifyContext, null);

			// kk消息单独发送
			notifyContext.setSubject(title);
			notifyContext.setFdAppType("kk");
			notifyContext.setFdAppReceiver("kk_system");
			logger.debug(
					"当前排班考勤组上下班提醒定时任务运行完成,categoryId:" + category.getFdId()
							+ ";workType:" + fdWorkType );
			getSysNotifyMainCoreService().sendNotify(category, notifyContext, null);
		} catch (Exception e) {
			logger.error("当前班次发送提醒失败:categoryId:" + category.getFdId()
					+ ";workType:" + fdWorkType, e);
		}

	}

	private void addRemindLog(String fdSubject, List<SysOrgElement> fdTargets,
			String fdNotifyType, Date fdTime, String fdCategoryId,
			String fdCategoryName) throws Exception {
		SysAttendNotifyRemindLog log = new SysAttendNotifyRemindLog();
		log.setFdId(IDGenerator.generateID());
		log.setFdSubject(fdSubject);
		log.setFdTargets(fdTargets);
		log.setFdNotifyType(fdNotifyType);
		log.setFdTime(fdTime);
		log.setFdCategoryId(fdCategoryId);
		log.setFdCategoryName(fdCategoryName);
		getSysAttendNotifyRemindLogService().add(log);
	}

	/**
	 * 判断是否发送通知提醒
	 * 
	 * @param category
	 * @param workType
	 *            班次
	 * @return
	 */
	private boolean isSendNotifyRemind(SysAttendCategory category,
			Integer workType) {
		Integer fdNotifyOnTime = category.getFdNotifyOnTime();
		Integer fdNotifyOffTime = category.getFdNotifyOffTime();
		fdNotifyOnTime = fdNotifyOnTime == null ? 0 : fdNotifyOnTime;
		fdNotifyOffTime = fdNotifyOffTime == null ? 0 : fdNotifyOffTime;

		if (workType == null) {
			if (fdNotifyOnTime > 0 || fdNotifyOffTime > 0) {
				return true;
			}
		}
		if (Integer.valueOf(0).equals(workType)) {
			if (fdNotifyOnTime > 0) {
				return true;
			}
		}
		if (Integer.valueOf(1).equals(workType)) {
			if (fdNotifyOffTime > 0) {
				return true;
			}
		}
		return false;
	}
}
