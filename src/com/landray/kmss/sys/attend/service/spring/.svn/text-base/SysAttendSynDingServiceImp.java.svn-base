package com.landray.kmss.sys.attend.service.spring;

import com.google.common.collect.Lists;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTimesheet;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.model.SysAttendImportLog;
import com.landray.kmss.sys.attend.model.SysAttendSynConfig;
import com.landray.kmss.sys.attend.model.SysAttendSynDing;
import com.landray.kmss.sys.attend.model.SysAttendSynDingNotify;
import com.landray.kmss.sys.attend.service.ISysAttendBusinessService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendImportLogService;
import com.landray.kmss.sys.attend.service.ISysAttendMainJobService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.ISysAttendStatJobService;
import com.landray.kmss.sys.attend.service.ISysAttendStatMonthJobService;
import com.landray.kmss.sys.attend.service.ISysAttendSynConfigService;
import com.landray.kmss.sys.attend.service.ISysAttendSynDingNotifyService;
import com.landray.kmss.sys.attend.service.ISysAttendSynDingQueueErrorService;
import com.landray.kmss.sys.attend.service.ISysAttendSynDingService;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendThreadPoolManager;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.attend.util.UploadResultBean;
import com.landray.kmss.sys.attend.webservice.ISysAttendWebService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ListSortUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.TransactionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SysAttendSynDingServiceImp extends ExtendDataServiceImp implements ISysAttendSynDingService,ApplicationContextAware {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendSynDingServiceImp.class);
	private static final Object lock = new Object();
	private ISysOrgPersonService sysOrgPersonService = null;
	private ApplicationContext applicationContext;

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	private ThreadPoolTaskExecutor taskExecutor;

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	private ISysAttendCategoryService sysAttendCategoryService;
	private ISysAttendMainService sysAttendMainService;

	public void setSysAttendCategoryService(
			ISysAttendCategoryService sysAttendCategoryService) {
		this.sysAttendCategoryService = sysAttendCategoryService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}


	private ISysOrgElementService sysOrgElementService;

	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	private ISysAttendSynConfigService sysAttendSynConfigService;

	public ISysAttendSynConfigService getSysAttendSynConfigService() {
		if(sysAttendSynConfigService == null){
			sysAttendSynConfigService = (ISysAttendSynConfigService) SpringBeanUtil.getBean("sysAttendSynConfigService");
		}
		return sysAttendSynConfigService;
	}

	private ISysAttendBusinessService sysAttendBusinessService;

	public ISysAttendBusinessService getSysAttendBusinessService() {
		if (sysAttendBusinessService == null) {
			sysAttendBusinessService = (ISysAttendBusinessService) SpringBeanUtil
					.getBean("sysAttendBusinessService");
		}
		return sysAttendBusinessService;
	}

	private ISysAttendSynDingQueueErrorService sysAttendSynDingQueueErrorService;

	public ISysAttendSynDingQueueErrorService getSysAttendSynDingQueueErrorService() {
		if(sysAttendSynDingQueueErrorService == null){
			sysAttendSynDingQueueErrorService = (ISysAttendSynDingQueueErrorService) SpringBeanUtil.getBean("sysAttendSynDingQueueErrorService");
		}
		return sysAttendSynDingQueueErrorService;
	}

	public void
	setSysAttendMainService(
			ISysAttendMainService sysAttendMainService) {
		this.sysAttendMainService = sysAttendMainService;
	}

	private ISysNotifyMainCoreService sysNotifyMainCoreService;
	private ISysAttendStatJobService sysAttendStatJobService;
	private ISysAttendStatMonthJobService sysAttendStatMonthJobService;
	private ISysAttendMainJobService sysAttendMainJobService;

	public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil
					.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}

	public ISysAttendStatJobService getSysAttendStatJobService() {
		if (sysAttendStatJobService == null) {
			sysAttendStatJobService = (ISysAttendStatJobService) SpringBeanUtil
					.getBean("sysAttendStatJobService");
		}
		return sysAttendStatJobService;
	}

	public ISysAttendStatMonthJobService getSysAttendStatMonthJobService() {
		if (sysAttendStatMonthJobService == null) {
			sysAttendStatMonthJobService = (ISysAttendStatMonthJobService) SpringBeanUtil
					.getBean("sysAttendStatMonthJobService");
		}
		return sysAttendStatMonthJobService;
	}

	public ISysAttendMainJobService getSysAttendMainJobService() {
		if (sysAttendMainJobService == null) {
			sysAttendMainJobService = (ISysAttendMainJobService) SpringBeanUtil
					.getBean(
							"sysAttendMainJobService");
		}
		return sysAttendMainJobService;
	}

	private ISysAttendSynDingNotifyService sysAttendSynDingNotifyService;

	public ISysAttendSynDingNotifyService getSysAttendSynDingNotifyService() {
		if (sysAttendSynDingNotifyService == null) {
			sysAttendSynDingNotifyService = (ISysAttendSynDingNotifyService) SpringBeanUtil
					.getBean("sysAttendSynDingNotifyService");
		}
		return sysAttendSynDingNotifyService;
	}

	private ISysAttendWebService sysAttendWebService;

	public ISysAttendWebService getSysAttendWebService() {
		if (sysAttendWebService == null) {
			sysAttendWebService = (ISysAttendWebService) SpringBeanUtil.getBean(
					"sysAttendWebService");
		}
		return sysAttendWebService;
	}

	private ISysAttendImportLogService sysAttendImportLogService;

	public void setSysAttendImportLogService(ISysAttendImportLogService sysAttendImportLogService) {
		this.sysAttendImportLogService = sysAttendImportLogService;
	}

	/**
	 * 时间格式要求
	 */

	private CountDownLatch countDownLatch;
	private List<String> clockUserIds = null;
	private static boolean locked = false;
	private SysAttendSynConfig config = null;
	private String appKey = "";
	private String appName = "";

	/**
	 * 同步微信考勤打卡记录(为区分运行日志方便)
	 *
	 * @param context
	 * @throws Exception
	 */
	@Override
	public void synchPersonWxClock(SysQuartzJobContext context) throws Exception {
		synchPersonClock(context);
	}

	/**
	 * 同步微信最近三天考勤打卡记录(为区分运行日志方便)
	 *
	 * @param context
	 * @throws Exception
	 */
	@Override
	public void synchPersonLastWxClock(SysQuartzJobContext context) throws Exception  {
		synchPersonLastClock(context);
	}

	@Override
	public void synchPersonClock(SysQuartzJobContext context) throws Exception {
		String temp = "";
		config = getSysAttendSynConfigService().getSysAttendSynConfig();
		if (null == config) {
			temp = "考勤同步未设置同步来源";
			logger.debug(temp);
			context.logMessage(temp);
			return;
		}
		if (config.getFdSynType().equals(AttendConstant.SYS_ATTEND_MAIN_FDAPPNAME)) {
			if (!AttendUtil.isEnableDingConfig()) {
				temp = "未开启钉钉集成,不执行打卡记录的同步";
				logger.debug(temp);
				context.logMessage(temp);
				return;
			}
			appKey = AttendConstant.SYS_ATTEND_MAIN_FDAPPNAME;
			appName = "钉钉";
		} else if (config.getFdSynType().equals(AttendConstant.SYS_ATTEND_MAIN_QIYEWEIXIN)) {
			if (!AttendUtil.isEnableWx()) {
				temp = "未开启企业微信集成,不执行打卡记录的同步";
				logger.debug(temp);
				context.logMessage(temp);
				return;
			}
			appKey = AttendConstant.SYS_ATTEND_MAIN_QIYEWEIXIN;
			appName = "企业微信";
		}
		if (locked) {
			temp = "已经存在运行中的打卡记录同步任务，当前任务中断...";
			logger.warn(temp);
			context.logMessage(temp);
			return;
		}
		if(null==config || false == config.getFdEnableRecord()){
			temp = "==========未开启" + appName + "打卡记录同步,不执行打卡记录的同步===============";
			logger.warn(temp);
			context.logMessage(temp);
			return;
		}
		//最后同步时间
		Date fdSyncTime = config.getFdSyncTime();
		//同步开始时间
		Date fdStartTime = config.getFdStartTime();
		//同步结束时间
		Date fdEndTime = config.getFdEndTime();
		//判断同步开始时间是否大于今天。如果是，则不同步
		if (fdStartTime != null
				&& fdStartTime.after(AttendUtil.getDate(new Date(), 1))) {
			temp = "" + appName + "考勤记录同步任务未到同步时间,不执行同步任务";
			logger.warn(temp);
			context.logMessage(temp);
			return;
		}

		if (fdStartTime == null) {
			fdStartTime = new Date();
		}
		if (fdEndTime == null) {
			fdEndTime = new Date();
		} else {
			//同步结束时间超过今天，则取今天
			if (!fdEndTime.before(AttendUtil.getDate(new Date(), 0))) {
				fdEndTime = new Date();
			}
		}
		//最后同步时间不为空，则将同步开始时间设置为最后同步时间
		if (fdSyncTime != null) {
			fdStartTime = fdSyncTime;
		}
		//判断同步开始时间是否大于结束时间。如果是，则不同步
		if (fdStartTime.after(fdEndTime)) {
			temp = "" + appName + "考勤记录同步任务开始时间大于结束时间(可检查最后同步时间),不执行同步任务";
			logger.warn(temp);
			context.logMessage(temp);
			return;
		}

		// 如:2019-01-01 00:00:00.0 2019-01-01 23:59:59.999
		fdStartTime = AttendUtil.getDate(fdStartTime, 0);
		fdEndTime = AttendUtil.getEndDate(fdEndTime, 0);
		//执行同步数据
		synchPersonClock(fdStartTime, fdEndTime, fdSyncTime, context);
	}

	/**
	 * 同步最近三天考勤打卡记录
	 *
	 * @param context
	 * @throws Exception
	 */
	@Override
	public void synchPersonLastClock(SysQuartzJobContext context)
			throws Exception {
		String temp = "";
		config = getSysAttendSynConfigService().getSysAttendSynConfig();
		if (null == config) {
			temp = "考勤同步未设置同步来源";
			logger.debug(temp);
			context.logMessage(temp);
			return;
		}
		if (config.getFdSynType().equals(AttendConstant.SYS_ATTEND_MAIN_FDAPPNAME)) {
			if (!AttendUtil.isEnableDingConfig()) {
				temp = "未开启钉钉集成,不执行打卡记录的同步";
				logger.debug(temp);
				context.logMessage(temp);
				return;
			}
			appKey = AttendConstant.SYS_ATTEND_MAIN_FDAPPNAME;
			appName = "钉钉";
		} else if (config.getFdSynType().equals(AttendConstant.SYS_ATTEND_MAIN_QIYEWEIXIN)) {
			if (!AttendUtil.isEnableWx()) {
				temp = "未开启企业微信集成,不执行打卡记录的同步";
				logger.debug(temp);
				context.logMessage(temp);
				return;
			}
			appKey = AttendConstant.SYS_ATTEND_MAIN_QIYEWEIXIN;
			appName = "企业微信";
		}
		if (locked) {
			temp = "已经存在运行中的" + appName + "打卡记录同步任务，当前任务中断...";
			logger.warn(temp);
			context.logMessage(temp);
			return;
		}
		if (null == config || false == config.getFdEnableRecord()) {
			temp = "==========未开启" + appName + "打卡记录同步,不执行打卡记录的同步===============";
			logger.warn(temp);
			context.logMessage(temp);
			return;
		}

		Date fdSyncTime = null;
		Date fdStartTime = AttendUtil.getDate(new Date(), -3);
		Date fdEndTime = AttendUtil.getEndDate(new Date(), -1);

		synchPersonClock(fdStartTime, fdEndTime, fdSyncTime, context);
		// 重新统计
		logger.debug("重新统计开始,fdStartTime:" + fdStartTime + ";fdEndTime:"
				+ fdEndTime);
		restat(fdStartTime, fdEndTime);
		logger.debug("重新统计完成...");
	}

	/**
	 * 指定日期，重新统计所有的数据
	 * @param fdStartTime
	 * @param fdEndTime
	 * @throws Exception
	 */
	private void restat(Date fdStartTime, Date fdEndTime) throws Exception {
		Date startTime = fdStartTime;
		Date endTime = fdEndTime;
		Set<Long> monthsSet = new HashSet<Long>();
		Set<String> cateSet = new HashSet<String>();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"sysAttendCategory.fdStatus=1 and sysAttendCategory.fdType=1");
		hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
		hqlInfo.setOrderBy("sysAttendCategory.docCreateTime desc");
		List<SysAttendCategory> categories = sysAttendCategoryService
				.findList(hqlInfo);
		while (startTime != null) {
			long month = AttendUtil.getMonth(startTime, 0).getTime();
			for (SysAttendCategory category : categories) {
				String cateId = category.getFdId();
				monthsSet.add(month);
				//根据原始考勤ID和时间来获取考勤组历史版本ID
				cateId =CategoryUtil.getLastVersionFdId(cateId,startTime);
				if(StringUtil.isNotNull(cateId)){
					cateSet.add(cateId);
					this.getSysAttendStatJobService().deletStat(cateId,
							startTime, null);

					// 重新生成缺卡数据,重新生成每月统计数据
					getSysAttendMainJobService().stat(startTime, cateId, true,
							false, null,null);
				}
			}
			startTime = AttendUtil.getDate(startTime, 1);
			if (startTime.after(endTime)) {
				startTime = null;
			}
		}
		// 重新生成统计月数据
		for (Long month : monthsSet) {
			for (String cateId : cateSet) {
				// 删除月统计数据
				this.getSysAttendStatMonthJobService().deletStat(cateId,
						new Date(month.longValue()));
				this.getSysAttendStatMonthJobService().stat(cateId,
						new Date(month.longValue()));
			}
		}
	}

	/**
	 * 	同步钉钉、微信数据，合并更新数据库中已有打卡数据
	 * @param fdStartTime
	 * 	开始同步时间
	 * @param fdEndTime
	 * 	结束同步时间
	 * @param fdSyncTime
	 * 	最近一次同步时间
	 * @param context
	 * 	定时任务上下文
	 * @throws Exception
	 */
	@Override
	public void synchPersonClock(Date fdStartTime, Date fdEndTime,
								 Date fdSyncTime, SysQuartzJobContext context) throws Exception {
		String temp = "==========开始同步(" + fdStartTime + "~" + fdEndTime + ")" + appName + "打卡记录===============";
		logger.debug(temp);
		setLogMessage(context, temp);

		try {
			locked = true;
			long alltime = System.currentTimeMillis();
			// 获取映射信息
			long caltime = System.currentTimeMillis();
			//获取映射用户ID信息, key为对应的App id，value为EKP id
			Map<String, String> originalClockInRecords = getDingUser();
			if (originalClockInRecords.isEmpty()) {
				logger.warn("" + appName + "考勤记录同步获取" + appName + "和EKP人员的映射信息为空,不执行打卡记录的同步");
				return;
			}
			temp = "获取" + appName + "和EKP人员的映射信息耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
			logger.debug(temp);
			setLogMessage(context, temp);

			//构建获取打卡详情的数据
			buildClockUserIds(originalClockInRecords);

			caltime = System.currentTimeMillis();
			// 获取钉钉、微信打卡信息
			getDingClock(fdStartTime, fdEndTime, fdSyncTime, originalClockInRecords);
			temp = "获取" + appName + "原始打卡记录耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
			logger.debug(temp);
			setLogMessage(context, temp);
			// 当天数据已经获取，同步时间更新
			// 更新最近同步的时间字段
			config.setFdSyncTime(new Date());
			getSysAttendSynConfigService().update(config);

			caltime = System.currentTimeMillis();
			// 生成有效的打卡数
			// 创建新打卡记录，合并并更新已有打卡数据
			mergeClock(fdStartTime, fdEndTime);
			temp = "根据" + appName + "原始打卡生成考勤日历耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
			logger.debug(temp);
			setLogMessage(context, temp);


			temp = "整个任务总耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000;
			logger.debug(temp);
			setLogMessage(context, temp);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (context != null) {
				context.logError(ex);
			}
		} finally {
			locked = false;
			clockUserIds = null;
		}
	}

	@Override
	public void reSynchPersonClock(Date fdStartTime, Date fdEndTime,
								   String fdUserIds) throws Exception {
		Map<String, String> map = getDingUser();
		List<String> users = new ArrayList<String>();
		users.add(fdUserIds);
		handleClockInfo(users, fdStartTime, fdEndTime, null, map);
	}

	private void setLogMessage(SysQuartzJobContext context,String message){
		if(context != null){
			context.logMessage(message);
		}
	}

	/**
	 * 	获取钉钉打卡原始数据
	 * @param fdStartTime
	 * 	同步开始时间
	 * @param fdEndTime
	 * 	同步结束时间
	 * @param fdSyncTime
	 * 	最近一次同步时间
	 * @param map
	 * 	数据存储变量
	 * @throws Exception
	 */
	private void getDingClock(Date fdStartTime, Date fdEndTime, Date fdSyncTime,
							  Map<String, String> map) throws Exception {
		if (clockUserIds == null || clockUserIds.size() == 0) {
			return;
		}
		int size = 20;
		int rs = clockUserIds.size() / size;
		if (clockUserIds.size() % size != 0) {
			rs += 1;
		}
		//多线程同步数据，每条线程同步20*50个人数据
		countDownLatch = new CountDownLatch(rs);
		logger.debug("" + appName + "考勤同步记录并发线程数为:" + rs);
		List<String> tempList = null;
		for (int i = 1; i <= rs; i++) {
			if (i * size > clockUserIds.size()) {
				tempList = clockUserIds.subList((i - 1) * size, clockUserIds.size());
			} else {
				tempList = clockUserIds.subList((i - 1) * size, i * size);
			}
			taskExecutor.execute(new PersonClockInfoRunner(tempList, fdStartTime, fdEndTime, fdSyncTime, map));
		}
		countDownLatch.await(6, TimeUnit.HOURS);
	}

	/**
	 * @throws Exception
	 *             获取中间表所有已经映射成功的人员数据
	 */
	private Map<String, String> getDingUser() throws Exception {
		String tableName = "oms_relation_model";
		String logName = "Ding";
		if (appKey.equals(AttendConstant.SYS_ATTEND_MAIN_QIYEWEIXIN)) {
			tableName = "wxwork_oms_relation_model";
			logName = "WeChat";
		}
		String sql = "select m.fd_ekp_id,m.fd_app_pk_id from " + tableName + " m,sys_org_person p where m.fd_ekp_id=p.fd_id";
		NativeQuery sqlQuery = getBaseDao().getHibernateSession().createNativeQuery(sql);
		List<Object[]> dlist = sqlQuery.list();
		Object[] o = null;
		Map<String, String> map = new HashMap<String, String>(dlist.size());
		for (int i = 0; i < dlist.size(); i++) {
			o = (Object[]) dlist.get(i);
			if (o[0] == null || o[1] == null) {
				logger.debug("EKP的fdId=" + o[0] == null ? "": o[0].toString() + "," + logName + "的UserId="+ o[1] == null ? "" : o[1].toString());
				continue;
			} else {
				map.put(o[1].toString(), o[0].toString());
			}
		}
		return map;
	}

	/**
	 * @throws Exception
	 *             构建获取打卡详情的数据
	 */
	private void buildClockUserIds(Map<String, String> map) throws Exception {

		clockUserIds = new ArrayList<String>();
		// 构建获取打卡详情的数据
		StringBuffer tids = new StringBuffer();
		int i=0;
		int size = map.keySet().size();
		for (String id:map.keySet()) {
			tids.append(id + ";");
			if ((i + 1) % 50 == 0 || (i == size - 1 && StringUtil.isNotNull(tids.toString()))) {
				clockUserIds.add(tids.toString());
				tids.setLength(0);
			}
			i++;
		}
	}

	/**
	 * 	同步钉钉/微信数据到数据库
	 * @param users
	 * 	需要同步的用户ID
	 * @param fdStartTime
	 * 	同步开始时间
	 * @param fdEndTime
	 * 	同步结束时间
	 * @param fdSyncTime
	 * 	最近一次的同步时间
	 * @param map
	 * 	数据存储变量
	 * @throws Exception
	 */
	private void handleClockInfo(List<String> users, Date fdStartTime,
								 Date fdEndTime, Date fdSyncTime, Map<String, String> map)
			throws Exception {
		if (users == null || users.isEmpty()) {
			logger.warn("获取的" + appName + "用户集合为空，直接退出");
			return;
		}
		String dingBeanName = "thirdDingClockService";
		String methodName = "getUserDingClock";
		if (!AttendUtil.isEnableDing()) {
			dingBeanName = "thirdLdingClockService";
		}
		if (appKey.equals(AttendConstant.SYS_ATTEND_MAIN_QIYEWEIXIN)) {
			// 企业微信
			dingBeanName = "thirdWxWorkClockService";
			methodName = "getUserWeChatClock";
		}

		for (String ids : users) {
			if (StringUtil.isNull(ids)) {
				logger.warn("获取的" + appName + "UserId为空，直接退出");
				return;
			}
			if (ids.endsWith(";")){
				ids = ids.substring(0, ids.length() - 1);
			}
			//分割日期，接口不能超过7天
			List<Date[]> dateList = splitDateByDay(fdStartTime, fdEndTime,
					fdSyncTime, 7);
			for(int i=0;i<dateList.size();i++){
				Date[] dates = dateList.get(i);
				Date startTime = dates[0];
				Date endTime = dates[1];
				try{
					Object apiService = SpringBeanUtil
							.getBean(dingBeanName);
					Class<?> clz = apiService.getClass();
					Method method = clz.getMethod(methodName, new Class[] { Date.class, Date.class, List.class });
					JSONArray resultArray = (JSONArray) method.invoke(apiService, startTime, endTime, Arrays.asList(ids.split("[;]")));
					if(resultArray !=null) {
						// 保存或更新数据
						long caltime = System.currentTimeMillis();
						List<SysAttendSynDing> recordList = removeExistsAttendDing(
								resultArray, fdStartTime, fdEndTime, fdSyncTime,
								map);
						saveAttendDingClock(recordList);
						String temp = "" + appName + "考勤同步记录一次更新记录数据耗时(秒)："
								+ (System.currentTimeMillis() - caltime) / 1000;
						logger.debug(temp);
						if (!recordList.isEmpty()) {
							for (SysAttendSynDing sysAttendSynDing : recordList) {
								if ("Security".equals(
										sysAttendSynDing.getFdInvalidRecordType())
										|| "Other".equals(sysAttendSynDing
										.getFdInvalidRecordType())) {
									saveInvalidRecordNotify(sysAttendSynDing);
								}
							}
						}
					}
				}catch(Exception e){
					logger.error("部分用户" + appName + "考勤记录同步线程运行报错,用户:" + users.toString()
									+ ";同步时间区间:" + startTime + "~" + endTime
									+ ";fdSyncTime:" + fdSyncTime,
							e);
					getSysAttendSynDingQueueErrorService().add(startTime,
							endTime, ids, e.getMessage());
					//请求失败后,请求暂停1秒
					e.printStackTrace();
				} finally {
					logger.info("部分用户" + appName + "考勤记录同步线程,用户:" + users.toString()
							+ ";同步时间区间:" + startTime + "~" + endTime
							+ ";fdSyncTime:" + fdSyncTime+ "  同步完成");
				}
			}
		}
	}

	private void saveAttendDingClock(List<SysAttendSynDing> recordList)
			throws Exception {
		if (recordList.isEmpty()) {
			return;
		}
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement statement = null;
		PreparedStatement insert = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			insert = conn
					.prepareStatement(
							"insert into sys_attend_syn_ding(fd_id,fd_ding_id,fd_group_id,fd_plan_id,fd_work_date,fd_user_id,fd_check_type,"
									+ "fd_source_type,fd_time_result,fd_location_result,fd_approve_id,fd_procinst_id,fd_base_check_time,fd_user_check_time,fd_class_id,fd_is_legal,"
									+ "fd_location_method,fd_device_id,fd_user_address,fd_user_longitude,fd_user_latitude,fd_user_accuracy,fd_user_ssid,fd_user_mac_addr,"
									+ "fd_plan_check_time,fd_base_address,fd_base_longitude,fd_base_latitude,fd_base_accuracy,fd_base_ssid,fd_base_mac_addr,fd_outside_remark,fd_person_id,fd_invalid_record_type,auth_area_id,fd_app_name,doc_create_time,doc_creator_id,fd_group_name,fd_location_title,fd_wifi_name) "
									+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			for (SysAttendSynDing record : recordList) {
				insert.setString(1, record.getFdId());
				insert.setString(2, record.getFdDingId());
				insert.setString(3, record.getFdGroupId());
				insert.setString(4, record.getFdPlanId());

				Date fdWorkDate = record.getFdWorkDate();
				Timestamp _fdWorkDate = null;
				if (fdWorkDate != null) {
					_fdWorkDate = new Timestamp(fdWorkDate.getTime());
				}
				insert.setTimestamp(5, _fdWorkDate);

				insert.setString(6, record.getFdUserId());
				insert.setString(7, record.getFdCheckType());
				insert.setString(8, record.getFdSourceType());
				insert.setString(9, record.getFdTimeResult());
				insert.setString(10, record.getFdLocationResult());
				insert.setString(11, record.getFdApproveId());
				insert.setString(12, record.getFdProcinstId());

				Date fdBaseCheckTime = record.getFdBaseCheckTime();
				Timestamp _fdBaseCheckTime = null;
				if (fdBaseCheckTime != null) {
					_fdBaseCheckTime = new Timestamp(fdBaseCheckTime.getTime());
				}
				insert.setTimestamp(13, _fdBaseCheckTime);

				Date fdUserCheckTime = record.getFdUserCheckTime();
				Timestamp _fdUserCheckTime = null;
				if (fdUserCheckTime != null) {
					_fdUserCheckTime = new Timestamp(fdUserCheckTime.getTime());
				}
				insert.setTimestamp(14, _fdUserCheckTime);

				insert.setString(15, record.getFdClassId());
				insert.setBoolean(16, record.getFdIsLegal());
				insert.setString(17, record.getFdLocationMethod());
				insert.setString(18, record.getFdDeviceId());
				insert.setString(19, record.getFdUserAddress());
				insert.setString(20, record.getFdUserLongitude());
				insert.setString(21, record.getFdUserLatitude());
				insert.setString(22, record.getFdUserAccuracy());
				insert.setString(23, record.getFdUserSsid());
				insert.setString(24, record.getFdUserMacAddr());

				Date fdPlanCheckTime = record.getFdPlanCheckTime();
				Timestamp _fdPlanCheckTime = null;
				if (fdPlanCheckTime != null) {
					_fdPlanCheckTime = new Timestamp(fdPlanCheckTime.getTime());
				}
				insert.setTimestamp(25, _fdPlanCheckTime);

				insert.setString(26, record.getFdBaseAddress());
				insert.setString(27, record.getFdBaseLongitude());
				insert.setString(28, record.getFdBaseLatitude());
				insert.setString(29, record.getFdBaseAccuracy());
				insert.setString(30, record.getFdBaseSsid());
				insert.setString(31, record.getFdBaseMacAddr());
				insert.setString(32, record.getFdOutsideRemark());
				insert.setString(33, record.getFdPersonId());
				insert.setString(34, record.getFdInvalidRecordType());
				insert.setString(35,
						SysTimeUtil.getUserAuthAreaId(record.getFdPersonId()));
				insert.setString(36, appKey);
				insert.setTimestamp(37, new Timestamp(new Date().getTime()));
				insert.setString(38, record.getFdPersonId());
				insert.setString(39, record.getFdGroupName());
				if(StringUtil.isNull(record.getFdUserMacAddr())){
					//WIFI MAC 是空的情况下，不使用Wifi名字。
					insert.setString(40, record.getFdLocationTitle()+"|"+AttendUtil.filterEmoji(record.getFdWifiName()));
					insert.setString(41, null);
				}else{
					insert.setString(40, record.getFdLocationTitle());
					insert.setString(41, AttendUtil.filterEmoji(record.getFdWifiName()));
				}

				insert.addBatch();
			}
			insert.executeBatch();
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage(), ex);
			conn.rollback();
			throw ex;
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(statement);
			// JdbcUtils.closeStatement(delete);
			JdbcUtils.closeStatement(insert);
			JdbcUtils.closeConnection(conn);
		}
	}

	private List<SysAttendSynDing> getDingClockList(JSONArray resultArray,
													Map<String, String> map, List<String> ekpUserList) {
		List<SysAttendSynDing> list = new ArrayList<SysAttendSynDing>();
		Set<String> orgIdSet = new HashSet<String>();
		if (resultArray == null || resultArray.isEmpty()) {
			return list;
		}
		for (int j = 0; j < resultArray.size(); j++) {
			JSONObject result = resultArray.getJSONObject(j);
			SysAttendSynDing doclock = new SysAttendSynDing();
			doclock.setFdDingId(result.get("id") + "");
			doclock.setFdGroupId(result.get("groupId") + "");
			doclock.setFdPlanId(result.get("groupId") + "");

			JSONObject workDate = result.getJSONObject("workDate");
			if (workDate != null && !workDate.isNullObject()) {
				doclock.setFdWorkDate(new Date(workDate.getLong("time")));
			}

			doclock.setFdUserId(result.get("userId") + "");
			doclock.setFdCheckType(result.get("checkType") + "");
			doclock.setFdSourceType(result.get("sourceType") + "");
			doclock.setFdTimeResult(result.get("timeResult") + "");
			doclock.setFdLocationResult(result.get("locationResult") + "");
			doclock.setFdApproveId(result.get("approveId") + "");
			doclock.setFdProcinstId(result.get("procInstId") + "");

			JSONObject baseCheckTime = result.getJSONObject("baseCheckTime");
			if (baseCheckTime != null && !baseCheckTime.isNullObject()) {
				doclock.setFdBaseCheckTime(
						new Date(baseCheckTime.getLong("time")));
			}

			JSONObject userCheckTime = result.getJSONObject("userCheckTime");
			if (userCheckTime != null && !userCheckTime.isNullObject()) {
				doclock.setFdUserCheckTime(
						new Date(userCheckTime.getLong("time")));
			}

			doclock.setFdClassId(result.get("classId") + "");
			if (result.get("isLegal") != null) {
				doclock.setFdIsLegal(
						"Y".equals(result.get("isLegal")) ? true : false);
			}
			doclock.setFdLocationMethod(result.get("locationMethod") + "");

			doclock.setFdDeviceId(result.get("deviceId") + "");
			doclock.setFdUserAddress(result.get("userAddress") + "");
			doclock.setFdUserLongitude(result.get("userLongitude") + "");
			doclock.setFdUserLatitude(result.get("userLatitude") + "");
			doclock.setFdUserAccuracy(result.get("userAccuracy") + "");
			doclock.setFdUserSsid(result.get("userSsid") + "");
			doclock.setFdUserMacAddr(result.get("userMacAddr") + "");
			//打卡类型如果是WIFI
			if("WIFI".equalsIgnoreCase(doclock.getFdLocationMethod())){
				doclock.setFdWifiName(doclock.getFdUserSsid());
			}
			JSONObject planCheckTime = result.getJSONObject("planCheckTime");
			if (planCheckTime != null && !planCheckTime.isNullObject()) {
				doclock.setFdPlanCheckTime(
						new Date(planCheckTime.getLong("time")));
			}

			doclock.setFdBaseAddress(result.get("baseAddress") + "");
			doclock.setFdBaseLongitude(result.get("baseLongitude") + "");
			doclock.setFdBaseLatitude(result.get("baseLatitude") + "");
			doclock.setFdBaseAccuracy(result.get("baseAccuracy") + "");
			doclock.setFdBaseSsid(result.get("baseSsid") + "");
			doclock.setFdBaseMacAddr(result.get("baseMacAddr") + "");
			doclock.setFdOutsideRemark(AttendUtil.filterEmoji(
					StringUtil.getString(result.get("outsideRemark") + "")));
			if (map.containsKey(result.get("userId"))) {
				String fdPersonId = (String) map.get(result.get("userId"));
				if (StringUtil.isNotNull(fdPersonId)) {
					orgIdSet.add(fdPersonId);
					doclock.setFdPersonId(fdPersonId);
				}
			}
			String fdInvalidRecordType = (String) result
					.get("invalidRecordType");
			doclock.setFdInvalidRecordType(fdInvalidRecordType);
			list.add(doclock);
		}
		ekpUserList.addAll(orgIdSet);
		return list;
	}

	private List<SysAttendSynDing> getWeChatClockList(JSONArray resultArray,
													  Map<String, String> map, List<String> ekpUserList) throws ParseException {
		List<SysAttendSynDing> list = new ArrayList<SysAttendSynDing>();
		Set<String> orgIdSet = new HashSet<String>();
		if (resultArray == null || resultArray.isEmpty()) {
			return list;
		}
		for (int j = 0; j < resultArray.size(); j++) {
			//获取所有返回的打卡记录
			JSONObject result = resultArray.getJSONObject(j);
			String exception_type=result.getString("exception_type");
			if (StringUtil.isNotNull(exception_type)&&exception_type.contains("未打卡")) {
				// 未打卡的记录不同步
				logger.warn("企业微信打卡记录未打卡，userid:"+result.getString("userid"));
				continue;
			}
			SysAttendSynDing doclock = new SysAttendSynDing();
			doclock.setFdUserId(result.get("userid") + "");
			String checkinType = result.get("checkin_type").toString();
			String fdCheckType = "GoOut";
			String exceptionType = result.get("exception_type").toString();

			String fdTimeResult ="Normal";
			String fdLocationResult ="Normal";
			if ("上班打卡".equals(checkinType)) {
				fdCheckType = "OnDuty";
				//上班时间异常，则是迟到
				if(StringUtil.isNotNull(exceptionType) && exceptionType.indexOf("时间异常") > -1){
					fdTimeResult ="Late";
				}
			} else if ("下班打卡".equals(checkinType)) {
				fdCheckType = "OffDuty";
				//下班时间异常，则是早退
				if(StringUtil.isNotNull(exceptionType) && exceptionType.indexOf("时间异常") > -1){
					fdTimeResult ="Early";
				}
			}
			if(StringUtil.isNotNull(exceptionType) && exceptionType.indexOf("地点异常") > -1){
				fdLocationResult ="Outside";
			}
			//默认值
			doclock.setFdTimeResult(fdTimeResult);
			doclock.setFdLocationResult(fdLocationResult);
			doclock.setFdCheckType(fdCheckType);
			doclock.setFdSourceType("USER");
			String userCheckTime = result.get("checkin_time").toString();
			if (userCheckTime != null) {
				long lt = new Long(userCheckTime) * 1000;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String fdWorkDate = sdf.format(new Date(lt));
				doclock.setFdDingId(result.get("userid") + userCheckTime);
				doclock.setFdWorkDate(sdf.parse(fdWorkDate));
				doclock.setFdUserCheckTime(new Date(lt));
			}
			doclock.setFdUserAddress(result.get("location_detail") + "");
			doclock.setFdUserMacAddr(result.get("wifimac") + "");
			doclock.setFdUserLongitude(result.get("lng") + "");
			doclock.setFdUserLatitude(result.get("lat") + "");
			doclock.setFdOutsideRemark(result.get("notes") + "");
			doclock.setFdGroupName(result.get("groupname") + "");
			doclock.setFdLocationTitle(result.get("location_title") + "");
			doclock.setFdWifiName(result.get("wifiname") + "");
			doclock.setFdIsLegal(true);
			if (map.containsKey(result.get("userid"))) {
				String fdPersonId = (String) map.get(result.get("userid"));
				if (StringUtil.isNotNull(fdPersonId)) {
					orgIdSet.add(fdPersonId);
					doclock.setFdPersonId(fdPersonId);
				}
			}
			list.add(doclock);
		}
		ekpUserList.addAll(orgIdSet);
		return list;
	}

	/**
	 * 	创建、合并打卡记录到
	 * @param startDate
	 * 	开始同步时间
	 * @param endDate
	 * 	结束同步时间
	 * @throws Exception
	 */
	private void mergeClock(Date startDate, Date endDate) throws Exception {
		// 防止跨天打卡，漏统计前一天数据
		startDate = AttendUtil.getDate(startDate, -1);
		// 创建时间区间，以天为单位，用于下面按天处理打卡数据
		List<Date> dateArea = splitByDate(startDate, endDate);
		for (Date currentDate : dateArea) {
			// 获取时间区间的所有用户
			List<String> orgUserIds = getAttendSynDingUser(AttendUtil.getDate(currentDate, 0), endDate);
			if (orgUserIds.isEmpty()) {
				continue;
			}
			convertOriginalRecordsIntoValidRecords(currentDate, orgUserIds,AttendConstant.SYS_ATTEND_MAIN_FDAPPNAME);
		}
	}

	/**
	 * 将原始记录转换为有效记录
	 * @param currentDate
	 * @param orgUserIds
	 * @throws Exception
	 */
	private void convertOriginalRecordsIntoValidRecords(Date currentDate, List<String> orgUserIds,String typeName) throws Exception {
		int maxCount = 500;
		List<List> orgUserIdGroups = new ArrayList<List>();
		if (orgUserIds.size() <= maxCount) {
			orgUserIdGroups.add(orgUserIds);
		} else {
			orgUserIdGroups = AttendUtil.splitList(orgUserIds, maxCount);
		}
		// 获取用户打卡记录
		for (List currentOrgUserIdGroup : orgUserIdGroups) {
			//获取用户某天钉钉打卡原始记录
			Map<String, List<JSONObject>> originalDingClockInRecords = getAttendDingSignedList(
					currentOrgUserIdGroup, AttendUtil.getDate(currentDate, 0),typeName);
			// 用户与考勤组,key为考勤组，value为考勤组下的用户Id和用户父类Id的Map集合
			Map<String, List<Map<String, String>>> categoryUserRelationships = getUserCategory(originalDingClockInRecords.keySet(),currentDate);

			for (String cateId : categoryUserRelationships.keySet()) {
				//查询当前考勤组Id的详情信息
				SysAttendCategory category = CategoryUtil.getCategoryById(cateId);

				// 非考勤机考勤组不同步考勤打卡记录
				if (!"exchange".equals(typeName) && !"resetAll".equals(typeName) && (category.getFdDingClock() == null
						|| !category.getFdDingClock())) {
					logger.debug("考勤组(" + category.getFdName()+ ")不需要同步" + appName + "考勤记录,忽略处理!");
					continue;
				}
				// 某个考勤组下所有用户
				List<Map<String, String>> userIds = categoryUserRelationships.get(cateId);
				// 构建、合并更新打卡记录
				Map<String, List<JSONObject>> userStatusData = getUserSignedInfo(
						originalDingClockInRecords, userIds, category, currentDate,typeName);
				logger.debug("getUserSignedInfo返回结果:"
						+ userStatusData.toString());
				// 保存用户打卡记录
				addBatch(userStatusData, userIds, currentDate);
			}
			//换班情况下，需要删除原有的缺卡记录
			if(categoryUserRelationships.isEmpty() && "exchange".equals(typeName)) {
				//删除缺卡的记录
				deleteMissed(currentDate, null, currentOrgUserIdGroup);
			}
		}
	}

	/**
	 * 删除缺卡的记录
	 *
	 * @param date
	 * @param category
	 * @throws Exception
	 */
	private void deleteMissed(Date date, SysAttendCategory category,
							  List<String> personList)
			throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			if (personList == null) {
				personList = new ArrayList<String>();
			}
			if (category != null) {
				List<String> orgList = this.sysAttendCategoryService.getAttendPersonIds(category.getFdId(),date);
				personList.addAll(orgList);
			}
			if (personList.isEmpty()) {
				return;
			}
			Date startDate = AttendUtil.getDate(date, 0);
			Date endDate = AttendUtil.getDate(date, 1);

			String sql = "update sys_attend_main set doc_status=1,fd_alter_record='换班删除无效缺卡记录',doc_alter_time=? where "
					+ HQLUtil.buildLogicIN("doc_creator_id", personList)
					+ " and (doc_create_time>=? and doc_create_time<? and (fd_is_across is null or fd_is_across=0) "
					+ " or fd_is_across=1 and doc_create_time>=? and doc_create_time<?)"
					+ " and fd_status=0 and fd_state is null";
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setTimestamp(1,
					new Timestamp(new Date().getTime()));
			statement.setTimestamp(2,
					new Timestamp(startDate.getTime()));
			statement.setTimestamp(3,
					new Timestamp(endDate.getTime()));
			statement.setTimestamp(4,
					new Timestamp(endDate.getTime()));
			statement.setTimestamp(5,
					new Timestamp(AttendUtil.getDate(date, 2).getTime()));
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(conn);
		}
	}

	@Override
	public void restat(SysQuartzJobContext context) throws Exception {
		// TODO Auto-generated method stub
		String param = context.getParameter();
		if(StringUtil.isNotNull(param)) {
			com.alibaba.fastjson.JSONArray arr = com.alibaba.fastjson.JSONArray.parseArray(param);

			List<String> statOrgs = getStatOrgList(arr);
			List<Date> statDates = getStatDateList(arr);

			recalMergeClock(statDates,statOrgs);
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
	}


	private List<String> getStatOrgList(com.alibaba.fastjson.JSONArray arr)
			throws Exception {
		List<String> idList = new ArrayList<String>();

		for(int i=0;i<arr.size();i++){
			com.alibaba.fastjson.JSONObject job = arr.getJSONObject(i);
			String targetIds = job.getString("targetIds");

			String[] ids = targetIds.split(";");
			for(String id : ids){
				if(!idList.contains(id)) {
					idList.add(id);
				}
			}
		}

		return idList;
	}

	private List<Date> getStatDateList(com.alibaba.fastjson.JSONArray arr)
			throws Exception {
		List<Date> statDates = new ArrayList<Date>();

		for(int i=0;i<arr.size();i++){
			com.alibaba.fastjson.JSONObject job = arr.getJSONObject(i);

			if(job.containsKey("startTime")) {
				String sTime = job.getString("startTime");
				Date startTime = AttendUtil.getDate(new Date(Long.valueOf(sTime)), 0);
				if(!statDates.contains(startTime)) {
					statDates.add(startTime);
				}
			}
			if(job.containsKey("endTime")) {
				String eTime = job.getString("endTime");
				Date endTime = AttendUtil.getDate(new Date(Long.valueOf(eTime)), 0);
				if(!statDates.contains(endTime)) {
					statDates.add(endTime);
				}
			}
		}
		return statDates;
	}

	/**
	 * 重新合并人员的有效记录 只統計缺卡狀態
	 * @param dates
	 * @param elementIds
	 * @throws Exception
	 */
	@Override
	public void recalMergeClock(List<Date> dates, List<String> elementIds) throws Exception {
		for (Date currentDate : dates) {
			if (elementIds.isEmpty()) {
				continue;
			}
			convertOriginalRecordsIntoValidRecords(currentDate, elementIds,"exchange");
		}
	}

	/**
	 * 重新合并人员的有效记录
	 * @param dates
	 * @param elementIds
	 * @throws Exception
	 */
	@Override
	public void recalMergeClockAllStatus(List<Date> dates, List<String> elementIds) throws Exception {
		for (Date currentDate : dates) {
			if (elementIds.isEmpty()) {
				continue;
			}
			convertOriginalRecordsIntoValidRecords(currentDate, elementIds,"resetAll");
		}
	}

	private void formatAttendMain(Map<String, List<JSONObject>> records,
								  SysAttendCategory category,
								  Map<String, List<JSONObject>> bussListMap, Date workDate) {
		try {
			if (records.isEmpty()) {
				return;
			}
			// 判断该日期是否需要考勤打卡
			Boolean isAttendNeeded = this.sysAttendCategoryService
					.isAttendNeeded(category, workDate);
			// 打卡当天类型
			String dateType = Boolean.TRUE.equals(isAttendNeeded)
					? AttendConstant.FD_DATE_TYPE[0]
					: AttendConstant.FD_DATE_TYPE[1];
			if (AttendConstant.FD_DATE_TYPE[1].equals(dateType)) {
				// 区分休息日/节假日
				boolean isHoliday = sysAttendCategoryService
						.isHoliday(category.getFdId(), workDate);
				if (isHoliday) {
					dateType = AttendConstant.FD_DATE_TYPE[2];
				}
			}

			for (String userKey : records.keySet()) {
				List<JSONObject> mainList = records.get(userKey);
				for (Iterator<JSONObject> iter= mainList.iterator();iter.hasNext();) {
					JSONObject record=iter.next();
					// 出差/请假/外出状态
					Long workTime = record.containsKey("fdBaseWorkTime")
							? record.getLong("fdBaseWorkTime") : null;
					Date fdBaseWorkTime = workTime != null
							? AttendUtil.joinYMDandHMS(workDate,
							new Date(workTime))
							: null;
					List<JSONObject> bussList = bussListMap != null
							? bussListMap.get(userKey) : null;
					logger.debug(fdBaseWorkTime+"的"+userKey+"的出差/请假/外出流程："+bussList);
					if (fdBaseWorkTime != null && bussList != null) {
						//应该打卡的时间
						long baseWorkTime = fdBaseWorkTime.getTime();
						for (JSONObject bus : bussList) {
							Long fdBusStartTime = (Long) bus
									.get("fdBusStartTime");
							Long fdBusEndTime = (Long) bus.get("fdBusEndTime");
							Integer fdBusType = (Integer) bus.get("fdBusType");// 业务类型
							String busId = (String) bus.get("fdBusId");
							Integer fdLeaveType = (Integer) bus
									.get("fdLeaveType");
							if (baseWorkTime >= fdBusStartTime
									&& baseWorkTime <= fdBusEndTime
							) {
								//如果 流程结束时间等于打卡时间 则 开始时间应该比 应该比 打卡时间小
								//如果流程开始时间等于打卡时间  则 结束时间应该比打卡时间大
								boolean startTimeCheck = baseWorkTime >= fdBusStartTime && baseWorkTime < fdBusEndTime;
								boolean endTimeCheck = baseWorkTime <= fdBusEndTime && baseWorkTime > fdBusStartTime;
								if (startTimeCheck && endTimeCheck) {
									int fdStatus = fdBusType == 7 ? 6 : fdBusType;
									record.put("fdStatus", fdStatus);
									record.put("fdBusId", busId);
									if (fdStatus == 5) {
										// 请假
										record.put("fdOffType", fdLeaveType);
									}
									break;
								}
							}
						}
					}
					if(!record.containsKey("dateType")) {
						record.put("dateType", dateType);
					}
					if (!AttendConstant.FD_DATE_TYPE[0].equals(dateType)) {
						Integer fdStatus = record.getInt("fdStatus");
						if (AttendUtil.getAttendTrip() && fdStatus == 4) {// 出差按自然日计算
						}else if(fdStatus==0) {
							iter.remove();
						}else if (!AttendUtil.isAttendBuss(fdStatus + "")) {
							record.put("fdStatus", 1);// 非工作日时,为自由打卡
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("判断该日期是否需要考勤打卡报错,workDate:" + category.getFdId(), e);
		}

	}

	/**
	 * 重新计算排班制时休息日打卡、状态等信息
	 *
	 * @param mainList
	 * @param isRest
	 * @throws Exception
	 */
	private void formatAttendMain(
			SysAttendCategory category,
			List<JSONObject> mainList,
								  boolean isRest,
								  List<JSONObject> bussList,
								  Date workDate,
								  SysOrgElement ele,
								  List<Map<String, Object>> signTimeConfigurations)
			throws Exception {
		// 打卡当天类型
		String dateType = !isRest ? AttendConstant.FD_DATE_TYPE[0] : AttendConstant.FD_DATE_TYPE[1];
		if (AttendConstant.FD_DATE_TYPE[1].equals(dateType)) {
			// 区分休息日/节假日
			boolean isHoliday = sysAttendCategoryService
					.isHoliday(null, workDate, ele, true);
			if (isHoliday) {
				dateType = AttendConstant.FD_DATE_TYPE[2];
			}
		}
		//上班
		Map<String, Object> tempWorkTime = signTimeConfigurations.get(0);
		//下班
		Map<String, Object> tempOffWorkTime = signTimeConfigurations.get(signTimeConfigurations.size() -1);
		//班次的最早打卡时间
		Date workBeginTime = (Date) tempWorkTime.get("fdStartTime");
		//班次的最晚打卡时间
		Date workOverTime = (Date) tempOffWorkTime.get("fdEndTime");

		if (workBeginTime != null && workOverTime != null) {
			workBeginTime = AttendUtil.joinYMDandHMS(workDate, workBeginTime);

			//下班的最晚打卡时间配置，是今日还是明日
			Integer yesterFdEndOverTimeType = (Integer) tempOffWorkTime.get("endOverTimeType");
			//如果昨日是最晚打卡时间是允许次日
			workOverTime = AttendUtil.joinYMDandHMS(Integer.valueOf(2).equals(yesterFdEndOverTimeType) ? AttendUtil.getDate(workDate,1) : workDate, workOverTime);
		}
		//该人员前后一天的考勤规则
		Date yesterDate =null;
		List<Map<String, Object>> yesterSignTimeConfigurations =null;
		Date tomorrowDate =null;
		List<Map<String, Object>> tomorrowSignTimeConfigurations =null;
		if(isRest) {
			yesterDate = AttendUtil.getDate(workDate, -1);
			//因为班次信息已经缓存，所以这里在循环里面直接调用
			yesterSignTimeConfigurations = sysAttendCategoryService.getAttendSignTimes(category, yesterDate, ele);
			//该人员后一天的考勤规则
			tomorrowDate = AttendUtil.getDate(workDate, 1);
			tomorrowSignTimeConfigurations = sysAttendCategoryService.getAttendSignTimes(category, tomorrowDate, ele);
		}
		for (Iterator<JSONObject> iter= mainList.iterator();iter.hasNext();) {
			JSONObject record=iter.next();
			//实际打卡时间
			Long fdUserCheckTime = record.containsKey("docCreateTime") ? record.getLong("docCreateTime") : null;
			//打卡时间，在标准的打卡时间范围内
			if (workBeginTime.getTime() <= fdUserCheckTime && fdUserCheckTime <= workOverTime.getTime()) {
				//标准的打卡日期
				if(isRest){
					//休息日，则把属于前一日的考勤时间范围内的删除
					if(checkRestDayAttendMain(fdUserCheckTime,yesterSignTimeConfigurations,yesterDate)){
						iter.remove();
						continue;
					}
					//属于后面一天的考勤范围内的删除
					if(checkRestDayAttendMain(fdUserCheckTime,tomorrowSignTimeConfigurations,tomorrowDate)){
						iter.remove();
						continue;
					}
				}
				Long workTime = record.containsKey("fdBaseWorkTime") ? record.getLong("fdBaseWorkTime") : null;
				Date fdBaseWorkTime = workTime != null ? new Date(workTime): null;
				if (fdBaseWorkTime != null && bussList != null) {
					long baseWorkTime = fdBaseWorkTime.getTime();
					for (JSONObject bus : bussList) {
						Long fdBusStartTime = (Long) bus.get("fdBusStartTime");
						Long fdBusEndTime = (Long) bus.get("fdBusEndTime");
						Integer fdBusType = (Integer) bus.get("fdBusType");// 业务类型
						String busId = (String) bus.get("fdBusId");
						Integer fdLeaveType = (Integer) bus.get("fdLeaveType");
						if (baseWorkTime >= fdBusStartTime && baseWorkTime <= fdBusEndTime) {
							int fdStatus = fdBusType == 7 ? 6 : fdBusType;
							record.put("fdStatus", fdStatus);
							record.put("fdBusId", busId);
							if (fdStatus == 5) {
								// 请假
								record.put("fdOffType", fdLeaveType);
							}
							break;
						}
					}
				}
				//如果已经设置了休息日和非休息日，则不在处理。主要是跨天场景，今天休息，昨天是工作日的场景
				if(!record.containsKey("dateType")) {
					record.put("dateType", dateType);
				}
			}
			if (!AttendConstant.FD_DATE_TYPE[0].equals(dateType)) {
				Integer fdStatus = record.getInt("fdStatus");
				if (AttendUtil.getAttendTrip() && fdStatus == 4) {
					// 出差按自然日计算
				} else if (fdStatus == 0) {
					iter.remove();
				} else if (!AttendUtil.isAttendBuss(fdStatus + "")) {
					record.put("fdStatus", 1);// 非工作日时,为自由打卡
				}
			}
		}
	}

	/**
	 * 验证如果是休息日，则过滤某一天排班规则的最晚打卡时间内的
	 * @param fdUserCheckTime 打卡时间
	 * @param signTimeConfigurations 昨天的考勤组排班信息
	 * @param yesterDate 统计日期
	 * @return
	 * @throws Exception
	 */
	private boolean checkRestDayAttendMain(Long fdUserCheckTime,List<Map<String, Object>> signTimeConfigurations ,Date yesterDate) throws Exception {
		//前一天的排班为空，则不处理
		if(CollectionUtils.isEmpty(signTimeConfigurations) || yesterDate ==null){
			return false;
		}
		Date checkReturnDate = sysAttendCategoryService.getTimeAreaDateOfDate(new Date(fdUserCheckTime),yesterDate,signTimeConfigurations);
		if(checkReturnDate !=null){
			return true;
		}
		return false;
	}

	/**
	 * 生成排班类型的打卡记录
	 * @param filteredDingClockInRecords 过滤后的原始打卡记录
	 * @param currentUserDingClockInRecords 重新统计的考勤记录
	 * @param category 考勤组
	 * @param currentDate 时间
	 * @param currentSysOrgElement 当前用户
	 * @param finalClockInRecords 打卡结果数据封装
	 * @param typeName 获取原始数据类型
	 * @throws Exception
	 */
	private List<JSONObject> genTimeRecords(Map<String, List<JSONObject>> filteredDingClockInRecords,
											SysAttendCategory category,
											Date currentDate,
											SysOrgElement currentSysOrgElement,
											Map<String, List<JSONObject>> finalClockInRecords,
											String typeName,
											List<JSONObject> currentUserDingClockInRecords

	) throws Exception {
		Set<String> userIdSet=new HashSet<>();
		userIdSet.add(currentSysOrgElement.getFdId());
		// 获取数据库中用户已打卡记录
		//获取数据库中原有的考勤数据，key为用户Id, value为该用户的原始打卡记录
		Map<String, List<JSONObject>> usersSignedWorkedRecords = getUserSignedList(userIdSet, currentDate,typeName);

		// 获取数据库中用户出差/请假/外出相关时间区间
		Map<String, List<JSONObject>> bussList = getUserBussList(userIdSet, currentDate);
		boolean isRest = false;
		// 排班信息
		// 获取指定用户某天的打卡时间点的考勤班次配置信息(可用于排班制）
		//班次上下班配置存储数据结构：上下班时间拆分成2个map对象存入list中，多个班次的上下班时间配置按顺序存储
		List<Map<String, Object>> signTimeConfigurations = sysAttendCategoryService.getAttendSignTimes(currentSysOrgElement, currentDate);
		if (signTimeConfigurations.isEmpty()) {
			isRest = true;
			// 休息日也允许同步数据
			signTimeConfigurations = sysAttendCategoryService.getAttendSignTimes(category, currentDate, currentSysOrgElement, true);
		}
		if (signTimeConfigurations.isEmpty()) {
			logger.warn("该用户过去和未来15天都没有排班信息," + appName + "考勤记录:用户名:" + currentSysOrgElement.getFdName()
					+ ";时间:" + currentDate);
			return null;
		}
		//用户主键
		String userKey =currentSysOrgElement.getFdId();
		if(CollectionUtils.isEmpty(currentUserDingClockInRecords)) {
			// 用户钉钉打卡记录
			currentUserDingClockInRecords = filteredDingClockInRecords.get(userKey);
		}
		// 数据库中用户已打卡记录
		List<JSONObject> userSignedWorkRecords = usersSignedWorkedRecords.get(userKey);
		// 构造用户最新打卡记录
		List<JSONObject> newestUserSignedWorkedRecords = new ArrayList<JSONObject>();
		//排班打卡处理
		genTimeAttendMain(currentUserDingClockInRecords, userSignedWorkRecords, newestUserSignedWorkedRecords, currentDate,
				signTimeConfigurations,currentSysOrgElement,finalClockInRecords,
				typeName);
		if (CollectionUtils.isNotEmpty(newestUserSignedWorkedRecords)) {
			formatAttendMain(
					category,
					newestUserSignedWorkedRecords,
					isRest,
					bussList.get(currentSysOrgElement.getFdId()),
					currentDate,
					currentSysOrgElement,
					signTimeConfigurations
			);
			finalClockInRecords.put(userKey, newestUserSignedWorkedRecords);
		}
		return newestUserSignedWorkedRecords;
	}
	private JSONArray getWorkTime(SysAttendCategory category, Date date) {
        JSONArray works = new JSONArray();
        List<SysAttendCategoryWorktime> workTimes = null;

        if ((Integer.valueOf(0).equals(category.getFdShiftType()) || Integer.valueOf(3).equals(category.getFdShiftType()))
                && Integer.valueOf(1).equals(category.getFdSameWorkTime())) {
            List<SysAttendCategoryTimesheet> tSheets = category.getFdTimeSheets();
            if (tSheets != null && !tSheets.isEmpty()) {
                for (SysAttendCategoryTimesheet tSheet : tSheets) {
                    if (StringUtil.isNotNull(tSheet.getFdWeek()) && tSheet.getFdWeek().indexOf(AttendUtil.getWeek(date) + "") > -1) {
                        workTimes = tSheet.getAvailWorkTime();
                        break;
                    }
                }
            }
        } else {
            workTimes = category.getAvailWorkTime();
        }

        if (workTimes == null || workTimes.isEmpty()) {
            if(category !=null && category.getFdShiftType() == 3 ){
                List<SysAttendCategoryWorktime> fdWorkTime = category.getFdWorkTime();
                if(fdWorkTime!=null && fdWorkTime.size()>0){
                    SysAttendCategoryWorktime workTime = fdWorkTime.get(0);
                    JSONObject json = new JSONObject();
                    json.put("fdWorkId", workTime.getFdId());
                    json.put("fdWorkType", 0);
                    if(workTime.getFdStartTime() != null){
                        json.put("signTime", workTime.getFdStartTime().getTime());
                    }else{
                        json.put("signTime", (1000 * 60 * 60 * 9));
                    }
                    works.add(json);
                    json = new JSONObject();
                    json.put("fdWorkId", workTime.getFdId());
                    json.put("fdWorkType", 1);
                    if(workTime.getFdEndTime()!=null){
                        json.put("signTime", workTime.getFdEndTime().getTime());
                    }else{
                        json.put("signTime", (1000 * 60 * 60 * 18));
                    }
                    works.add(json);
                }
            }

            return works;
        }
        if(category.getFdShiftType() == 4 && workTimes.size() > 0){
            SysAttendCategoryWorktime workTime = workTimes.get(0);
            JSONObject json = new JSONObject();
            json.put("fdWorkId", workTime.getFdId());
            json.put("fdWorkType", 0);
            if(workTime.getFdStartTime() != null){
                json.put("signTime", workTime.getFdStartTime().getTime());
            }else{
                json.put("signTime", 3600000L);
            }
            works.add(json);
            json = new JSONObject();
            json.put("fdWorkId", workTime.getFdId());
            json.put("fdWorkType", 1);
            if(workTime.getFdEndTime()!=null){
                json.put("signTime", workTime.getFdEndTime().getTime());
            }else{
                json.put("signTime", 36000000L);
            }
            works.add(json);
            //3600000
            //36000000
        }else{
            for (SysAttendCategoryWorktime workTime : workTimes) {
                JSONObject json = new JSONObject();
                json.put("fdWorkId", workTime.getFdId());
                json.put("fdWorkType", 0);
                json.put("signTime", workTime.getFdStartTime().getTime());
                works.add(json);
                json = new JSONObject();
                json.put("fdWorkId", workTime.getFdId());
                json.put("fdWorkType", 1);
                json.put("signTime", workTime.getFdEndTime().getTime());
                works.add(json);
            }
        }

        return works;
    }
	/**
	 * 	用户打卡状态相关信息
	 *
	 * @param originalDingClockInRecords
	 *            一天所有的打卡记录
	 * @param userIds
	 *            用户组(有效用户) Map中Key为用户id，value为用户层级Id
	 * @param category
	 * @param currentDate
	 *            打卡工作时间(非实际打卡时间)
	 * @param typeName
	 *            换班默认appName为null
	 * @return
	 * @throws Exception
	 */
	private Map getUserSignedInfo(Map<String, List<JSONObject>> originalDingClockInRecords,
								  List<Map<String, String>> userIds, SysAttendCategory category, Date currentDate,String typeName)
			throws Exception {
		//最终打卡信息
		Map<String, List<JSONObject>> finalClockInRecords = new HashMap<String, List<JSONObject>>();
		// 获取有效用户的钉钉打卡记录, 过滤不匹配的原始打卡数据
		Map<String, List<JSONObject>> filteredDingClockInRecords = getEffectiveUserAttendMain(originalDingClockInRecords, userIds);
		Set<String> userIdSet = filteredDingClockInRecords.keySet();
		// 区分考勤组排班制, 1为排班，0/2为固定周期/自定义
		if (Integer.valueOf(1).equals(category.getFdShiftType())) {
			String[] userIdArray = new String[]{};
			userIdArray = userIdSet.toArray(userIdArray);
			//查询用户详细信息
			List<SysOrgElement> sysOrgElements = sysOrgCoreService.findByPrimaryKeys(userIdArray);
			for (String userKey : userIdSet) {
				//找出匹配的用户详情信息
				SysOrgElement currentSysOrgElement = searchPerson(sysOrgElements, userKey);
				genTimeRecords(filteredDingClockInRecords, category, currentDate, currentSysOrgElement, finalClockInRecords, typeName, null);
			}
			return finalClockInRecords;
		}
		// 获取数据库中用户出差/请假/外出相关时间区间
		Map<String, List<JSONObject>> bussList = getUserBussList(userIdSet, currentDate);
		// 获取数据库中用户已打卡记录
		//获取数据库中原有的考勤数据，key为用户Id, value为该用户的原始打卡记录
		Map<String, List<JSONObject>> usersSignedWorkedRecords = getUserSignedList(userIdSet, currentDate, typeName);

		List works = sysAttendCategoryService.getAttendSignTimes(category, currentDate);
		JSONArray works1 = getWorkTime(category, currentDate);
		JSONObject json1 = (JSONObject) works1.get(0);
        String fdWorkId1 = json1.getString("fdWorkId");
		JSONObject json2 = (JSONObject) works1.get(1);
        String fdWorkId2 = json2.getString("fdWorkId");
		if (works.isEmpty()) {
			return finalClockInRecords;
		}
		// 班次信息
		boolean isOneWork = works.size() > 2 ? false : true;
		Map map1 = (Map) works.get(0);
		Map map2 = (Map) works.get(1);
		map1.put("fdWorkTimeId",fdWorkId1);
		map2.put("fdWorkTimeId",fdWorkId2);
		Map map3 = null, map4 = null;
		if (!isOneWork) {
			map3 = (Map) works.get(2);
			map4 = (Map) works.get(3);
		}

		for (String userKey : userIdSet) {
			List<JSONObject> dingList = filteredDingClockInRecords.get(userKey);
			if(CollectionUtils.isNotEmpty(dingList)) {
				ListSortUtil.sort(dingList,"fdUserCheckTime",false);
			}
			List<JSONObject> signedRecordList = usersSignedWorkedRecords.get(userKey);
			List<JSONObject> mainList = new ArrayList<JSONObject>();
			// 构造用户最新打卡记录
			if (isOneWork) {
				genAttendMain(dingList, signedRecordList, map1, map2, mainList, currentDate,category);
				logger.info("mainList"+userKey+":"+mainList.toString());
			} else {
				genAttendMain(dingList, signedRecordList, map1, map2, map3, map4, mainList, currentDate,category);
			}
			if (!mainList.isEmpty()) {
				finalClockInRecords.put(userKey, mainList);
			}

		}
		// 重新计算该日期是否工作日打卡
		formatAttendMain(finalClockInRecords, category, bussList, currentDate);

		return finalClockInRecords;
	}

	/**
	 * 	找出用户详情信息
	 * @param sysOrgElements
	 * 	用户信息数组
	 * @param fdId
	 * 	需要匹配的用户Id
	 * @return
	 */
	private SysOrgElement searchPerson(List<SysOrgElement> sysOrgElements,
									   String fdId) {
		for (SysOrgElement org : sysOrgElements) {
			if (org.getFdId().equals(fdId)) {
				return org;
			}
		}
		return null;
	}

	/**
	 * 	获取有效用户的钉钉打卡记录
	 * 	通过与用户Id做比较，过滤掉不匹配的用户原始打卡记录
	 *
	 * @param originalDingClockInRecords
	 *            钉钉打卡记录
	 * @param userIds
	 *            当前有效用户集合，Map中Key为用户id，value为用户层级Id
	 * @return Map<String, List<JSONObject>>
	 * 	  key为用户Id, value为该用户的原始打卡记录
	 */
	private Map<String, List<JSONObject>> getEffectiveUserAttendMain(
			Map<String, List<JSONObject>> originalDingClockInRecords, List<Map<String, String>> userIds) {
		Map<String, List<JSONObject>> filteredDingClockInRecords = new HashMap<String, List<JSONObject>>();
		for (String userKey : originalDingClockInRecords.keySet()) {
			for (int i = 0; i < userIds.size(); i++) {
				Map<String, String> m = (Map<String, String>) userIds.get(i);
				if (userKey.equals(m.get("orgId").toString())) {
					filteredDingClockInRecords.put(userKey,
							originalDingClockInRecords.get(userKey));
					break;
				}
			}
		}
		return filteredDingClockInRecords;
	}

	/**
	 * 	用于构建排班类型的打卡信息
	 * @param currentUserDingClockInRecords
	 * 	当前用户的钉钉、微信原始打卡信息
	 * @param userSignedWorkRecords
	 * 	当前用户数据库中的考勤记录
	 * @param newestUserSignedWorkedRecords
	 * 	用于存储最新的打卡记录
	 * @param currentDate
	 * 	正在处理某天的日期
	 * @param signTimeConfigurations
	 * 	该用户所属的考勤配置数据
	 * @throws Exception
	 */
	private void genTimeAttendMain(List<JSONObject> currentUserDingClockInRecords,
								   List<JSONObject> userSignedWorkRecords, List<JSONObject> newestUserSignedWorkedRecords,
								   Date currentDate, List<Map<String, Object>> signTimeConfigurations,SysOrgElement ele,
								   Map<String, List<JSONObject>> finalClockInRecords,
								   String typeName)
			throws Exception {
		//处理打卡班次信息，已添加跨天排班打卡处理
		this.sysAttendCategoryService.doWorkTimesRender(signTimeConfigurations,
				userSignedWorkRecords);
		if (signTimeConfigurations.isEmpty()) {
			return;
		} else {
			/**
			 * 根据排班班次的打卡配置 来封装成有效的打卡记录
			 */
			Boolean isTimeAreNew =Boolean.FALSE;
			/** 1) 根据所有的打卡记录遍历，在某个班次区间，则属于某个班次*/
			Map<String,Set<JSONObject>> workConfigAndSignDateMap=new HashMap<>(signTimeConfigurations.size());
			Map<String,List<Map<String, Object>>> workConfigMap=new HashMap<>();
			List<JSONObject> yesterdays=new ArrayList<>();
			List<Date> tempYesterdays=new ArrayList<>();
			/** 2) 封装每个班次内的打卡时间 */
			Date workDate = AttendUtil.getDate(currentDate, 0);
			//前一天所在的考勤组
			SysAttendCategory category =null;
			Date yesterDate = AttendUtil.getDate(currentDate, -1);
			List<Map<String, Object>> yesterSignTimeList =null;
			for(Map<String, Object> workConfig:signTimeConfigurations){
				isTimeAreNew =(Boolean) workConfig.get("isTimeAreNew");
				if(isTimeAreNew !=null && Boolean.TRUE.equals(isTimeAreNew)) {
					//班次的唯一标识
					String fdWorkTimeId = (String) workConfig.get("fdWorkTimeId");
					//同一个班次所有的打卡记录
					Set<JSONObject> workSignDateList =workConfigAndSignDateMap.get(fdWorkTimeId);
					if(workSignDateList ==null){
						workSignDateList = new HashSet<>();
					}
					//班次的最早打卡时间
					Date beginTime = (Date) workConfig.get("fdStartTime");
					//班次的最晚打卡时间
					Date overTime = (Date) workConfig.get("fdEndTime");
					if(beginTime ==null || overTime ==null){
						continue;
					}
					for (JSONObject dingClockInRecord : currentUserDingClockInRecords) {
						Date createTime = new Date(dingClockInRecord.getLong("fdUserCheckTime"));
						Date checkDate = sysAttendCategoryService.getTimeAreaDateOfDate(createTime,workDate, workConfig);
						if(checkDate !=null) {
							//在同一个最早最晚打卡时间，则认定为同一个班次
							if(!workSignDateList.contains(dingClockInRecord)) {
								workSignDateList.add(dingClockInRecord);
							}
							continue;
						}
						//如果班次是跨天。则计算在昨日的考勤中
						if(!tempYesterdays.contains(createTime)){
							String categoryId = sysAttendCategoryService.getAttendCategory(ele, yesterDate);
							if (StringUtil.isNotNull(categoryId)) {
								category = CategoryUtil.getCategoryById(categoryId);
								//查询人员昨日的排班情况
								yesterSignTimeList = sysAttendCategoryService.getAttendSignTimes(category, yesterDate, ele);
								if (yesterSignTimeList.isEmpty()) {
									continue;
								}
								Date yesterDay = sysAttendCategoryService.getTimeAreaDateOfDate(createTime, yesterDate, yesterSignTimeList);
								if (yesterDay != null) {
									//在昨日跨天的打卡范围内
									yesterdays.add(dingClockInRecord);
									tempYesterdays.add(createTime);
									continue;
								}
							}
						}
					}
					List<Map<String, Object>> workConfigListTemp =workConfigMap.get(fdWorkTimeId);
					if(workConfigListTemp ==null) {
						workConfigListTemp = new ArrayList<>();
					}
					workConfigListTemp.add(workConfig);
					//班次 的上下班配置
					workConfigMap.put(fdWorkTimeId,workConfigListTemp);
					//班次的所有打卡时间
					workConfigAndSignDateMap.put(fdWorkTimeId,workSignDateList);
				} else {
					break;
				}
			}
			if(CollectionUtils.isNotEmpty(yesterdays) && category !=null){
				//重新生成属于是昨日的考勤记录
				currentUserDingClockInRecords.removeAll(yesterdays);
				Map<String, List<JSONObject>> userSignDatas=new HashMap<>();
				userSignDatas.put(ele.getFdId(),yesterdays);
				//拿昨日的日期重新 计算昨日的
				List<JSONObject> yesterdaysRecords = genTimeRecords(userSignDatas,category , yesterDate, ele ,finalClockInRecords,typeName,yesterdays);
				if(CollectionUtils.isNotEmpty(yesterdaysRecords)){
					newestUserSignedWorkedRecords.addAll(yesterdaysRecords);
				}
			}
			if(Boolean.TRUE.equals(isTimeAreNew)) {
				/** 3） 根据班次以及对应的打卡记录 封装成有效考勤记录 */
				for (Map.Entry<String, List<Map<String, Object>>> workConfigTemp: workConfigMap.entrySet()) {
					//一个班次 范围内的所有上下班时间处理、该时间在上面封装的时候去重了。重复时间不处理
					Set<JSONObject> setDates = workConfigAndSignDateMap.get(workConfigTemp.getKey());
					if(CollectionUtils.isEmpty(setDates)){
						continue;
					}
					List<JSONObject> dates =Lists.newArrayList(setDates);
					//根据时间排序
					ListSortUtil.sort(dates,"fdUserCheckTime",false);

					//每个班次 的上下班为一个列表
					List<Map<String, Object>> tempList = workConfigTemp.getValue();
					if(CollectionUtils.isNotEmpty(tempList)){
						//一个班次只有上下班两个
						Map<String, Object> goWorkTime =null;
						//下班
						Map<String, Object> outWorkTime =null;
						for (Map<String, Object> workTime:tempList) {
							//0是上班，1是下班
							Integer fdWorkType = (Integer) workTime.get("fdWorkType");
							if (Integer.valueOf(0).equals(fdWorkType)) {
								goWorkTime =workTime;
							} else if (Integer.valueOf(1).equals(fdWorkType)) {
								outWorkTime =workTime;
							}
						}
						if(goWorkTime !=null && outWorkTime !=null){

							List<JSONObject> tempdates= Lists.newArrayList(dates);

							//已经存在的打卡记录，打卡记录过滤班次信息 上班时间
							JSONObject goWorkRecord = getSignRecord(userSignedWorkRecords, goWorkTime);
							if(goWorkRecord !=null) {
								removeLeaveTime(goWorkRecord, tempdates);
								putAttendMain(goWorkRecord, goWorkTime, newestUserSignedWorkedRecords,category);
							}
							//已经存在的打卡记录，打卡记录过滤班次信息  下班时间
							JSONObject outWorkRecord = getSignRecord(userSignedWorkRecords, outWorkTime);
							if(outWorkRecord !=null) {
								removeLeaveTime(outWorkRecord, tempdates);
								putAttendMain(outWorkRecord, outWorkTime, newestUserSignedWorkedRecords,category);
							}
							if(CollectionUtils.isEmpty(tempdates)){
								continue;
							}
							/** 拿时间计算*/
							//标准的上班时间
							int goWorkTimeMis = getShouldOnWorkTime(goWorkTime);
							//标准的下班时间
							int outSignTimeMin = getShouldOffWorkTime(null, outWorkTime);
							//当前班次是否是支持跨天
							boolean isAcrossDay = (Boolean) outWorkTime.get("isAcrossDay");
							//打卡时间是否属于上班班次
							boolean isHaveGoWorkTime =false;

							//计算上班的打卡时间--取最早的打卡时间
							JSONObject tempGoWorkTime = tempdates.get(0);
							Date createTime = new Date(tempGoWorkTime.getLong("fdUserCheckTime"));

							int _createTime = createTime.getHours() * 60 + createTime.getMinutes();

							//最早打卡时间
							Date fdStartTime = (Date) goWorkTime.get("fdStartTime");
							int _beginSignTime = fdStartTime.getHours() * 60 + fdStartTime.getMinutes();

							//最晚的一次打卡时间
							JSONObject tempOutWorkTime = tempdates.get(tempdates.size()-1);
							Date overTime = new Date(tempOutWorkTime.getLong("fdUserCheckTime"));

							int status =0;
							if(_createTime >= _beginSignTime && _createTime <= goWorkTimeMis){
								//大于等于最早开始时间，小于等于标准 上班时间，则认为是正常打卡
								status =1;
								isHaveGoWorkTime =true;
							} else if(_createTime > goWorkTimeMis && _createTime < outSignTimeMin ){
								//最早一次的打卡时间 大于标准上班时间，小于标准下班时间 则认定是迟到
								status =2;
								isHaveGoWorkTime =true;
							}
							if(isHaveGoWorkTime) {
								//上班打卡的有效记录封装
								addAttendMains(goWorkRecord, status, tempGoWorkTime, goWorkTime, newestUserSignedWorkedRecords,category);
								if (createTime.getTime() == overTime.getTime()) {
									//只有一条打卡时间记录时，上下班时间相同。并且归属上班时间范围内。则不处理下班时间
									continue;
								}
							}
							boolean isNextDay =false;
							//下班的打卡时间 计算
							int _overTime = overTime.getHours() * 60 + overTime.getMinutes();
							// 下班计算 跨天打卡 加上24小时
							if (isAcrossDay && overTime.getTime() >= AttendUtil.getDate(currentDate, 1).getTime()) {
								_overTime = _overTime + 24 * 60;
								isNextDay =true;
							}
							//下班时间必须在上班标准打卡时间之后
							if(_overTime > goWorkTimeMis) {
								if (isHaveGoWorkTime) {
									//主要计算 弹性时间
									//获取上班打卡的时间
									Date goWorkOverTime = getAttendMainSignTime(goWorkTime, newestUserSignedWorkedRecords);
									//下班标准打卡时间
									outSignTimeMin = getShouldOffWorkTime(goWorkOverTime, outWorkTime);
								}
								int outStatus = 0;
								//下班打卡时间在标准下班时间之后
								if (_overTime >= outSignTimeMin) {
									//正常下班
									outStatus = 1;
								}
								if ((isHaveGoWorkTime || isNextDay) && _overTime < outSignTimeMin) {
									//早退下班
									outStatus = 3;
								}
								if (outStatus > 0) {
									addAttendMains(outWorkRecord, outStatus, tempOutWorkTime, outWorkTime, newestUserSignedWorkedRecords,category);
								}
							}
						}
					}
				}
			}else {

				//第一个排班班次的上班配置时间
				Map<String, Object> workTimeConfiguration1 = signTimeConfigurations.get(0);
				//最后一个排班的下班配置时间
				Map<String, Object> lastWorkTimeConfiguration = signTimeConfigurations
						.get(signTimeConfigurations.size() - 1);
				//最后一个班次的上班配置时间
				Map<String, Object> lastWorkTimeOnConfiguration = signTimeConfigurations
						.get(signTimeConfigurations.size() - 2);
				//第一排班班次的下班打卡记录
				Map<String, Object> workTime2 = signTimeConfigurations.get(1);
				JSONObject offWorkUserSignedRecords2 = getSignRecord(userSignedWorkRecords, workTime2);
				//构建新下班打卡数据
				putAttendMain(offWorkUserSignedRecords2, workTime2, newestUserSignedWorkedRecords,category);
				//第二排班班次的上班打卡记录
				JSONObject userSignedWorkRecords3 = null;
				Map<String, Object> workTime3 = null;
				//第二排班班次的下班打卡记录
				JSONObject offWorkUserSignedRecord4 = null;
				Map<String, Object> workTime4 = null;
				//第三排班班次的上班打卡记录
				JSONObject userSignedWorkRecords5 = null;
				Map<String, Object> workTime5 = null;
				//第三排班班次的下班打卡记录
				JSONObject userSignedWorkRecords6 = null;
				Map<String, Object> workTime6 = null;

				if (signTimeConfigurations.size() >= 4) {
					/**
					 * 	二班次排班情况处理
					 */
					//第二排班班次的上班时间配置
					workTime3 = signTimeConfigurations.get(2);
					userSignedWorkRecords3 = getSignRecord(userSignedWorkRecords, workTime3);
					removeLeaveTime(userSignedWorkRecords3, currentUserDingClockInRecords);
					//构建新上班打卡数据
					putAttendMain(userSignedWorkRecords3, workTime3, newestUserSignedWorkedRecords,category);

					//第二排班班次的下班时间配置
					workTime4 = signTimeConfigurations.get(3);
					offWorkUserSignedRecord4 = getSignRecord(userSignedWorkRecords, workTime4);
					removeLeaveTime(offWorkUserSignedRecord4, currentUserDingClockInRecords);
					//构建新下班打卡数据
					putAttendMain(offWorkUserSignedRecord4, workTime4, newestUserSignedWorkedRecords,category);

					/**
					 * 	三班次排班情况处理
					 */
					//第三排班班次的上班时间配置
					workTime5 = signTimeConfigurations.size() >= 6
							? signTimeConfigurations.get(4) : null;
					userSignedWorkRecords5 = signTimeConfigurations.size() >= 6
							? getSignRecord(userSignedWorkRecords,
							workTime5)
							: null;
					removeLeaveTime(userSignedWorkRecords5, currentUserDingClockInRecords);
					//构建新上班打卡数据
					putAttendMain(userSignedWorkRecords5, workTime5, newestUserSignedWorkedRecords,category);

					//第三排班班次的下班时间配置
					workTime6 = signTimeConfigurations.size() >= 6
							? signTimeConfigurations.get(5) : null;
					userSignedWorkRecords6 = signTimeConfigurations.size() >= 6
							? getSignRecord(userSignedWorkRecords,
							workTime6)
							: null;
					removeLeaveTime(userSignedWorkRecords6, currentUserDingClockInRecords);
					//构建新下班打卡数据
					putAttendMain(userSignedWorkRecords6, workTime6, newestUserSignedWorkedRecords,category);
				}
				//用户第一个排班班次的上班打卡记录
				JSONObject userSignedWorkRecords1 = getSignRecord(userSignedWorkRecords, workTimeConfiguration1);
				//用户最后一个排班班次的下班打卡记录
				JSONObject lastOffWorkRecord = getSignRecord(userSignedWorkRecords, lastWorkTimeConfiguration);
				removeLeaveTime(userSignedWorkRecords1, currentUserDingClockInRecords);
				removeLeaveTime(lastOffWorkRecord, currentUserDingClockInRecords);

				//构建新的第一个班次上班打卡记录
				putAttendMain(userSignedWorkRecords1, workTimeConfiguration1, newestUserSignedWorkedRecords,category);
				//构建新的最后一个班次下班的打卡记录
				putAttendMain(lastOffWorkRecord, lastWorkTimeConfiguration, newestUserSignedWorkedRecords,category);
				// 迟到配置时间
				Integer fdLateTime = (Integer) workTimeConfiguration1.get("fdLateTime");
				//早退配置时间
				Integer fdLeftTime = (Integer) workTimeConfiguration1.get("fdLeftTime");
				fdLateTime = fdLateTime == null ? 0 : fdLateTime;
				fdLeftTime = fdLeftTime == null ? 0 : fdLeftTime;
				//第一个排班班次的上班打卡时间配置
				Date signTimeConfiguration1 = (Date) workTimeConfiguration1.get("signTime");
				int signTimeMinConfiguration1 = signTimeConfiguration1.getHours() * 60
						+ signTimeConfiguration1.getMinutes();
				//最后一个排班的下班配置时间下班打卡时间配置
				Date lastOffWorkSignTimeConfiguration = (Date) lastWorkTimeConfiguration.get("signTime");
				int lastOffWorkSignTimeMinConfiguration = lastOffWorkSignTimeConfiguration.getHours() * 60
						+ lastOffWorkSignTimeConfiguration.getMinutes();

				// 是否支持跨天
				boolean isAcrossDay = (Boolean) workTimeConfiguration1.get("isAcrossDay");
				// 最早/最晚打卡时间
				Date earliestWorkingClockInTimeCfg = (Date) workTimeConfiguration1.get("fdStartTime");
				Date endWorkingClockInTimeCfg = (Date) workTimeConfiguration1.get("fdEndTime");
				if (earliestWorkingClockInTimeCfg == null) {
					earliestWorkingClockInTimeCfg = AttendUtil.getDate(new Date(), 0);
				}
				if (endWorkingClockInTimeCfg == null) {
					endWorkingClockInTimeCfg = AttendUtil.getEndDate(new Date(), 0);
				}
				// 实际最早/最晚打卡时间戳
				// 将时间配置转化为当前具体日期的打卡时间配置
				earliestWorkingClockInTimeCfg = AttendUtil.joinYMDandHMS(currentDate, earliestWorkingClockInTimeCfg);
				endWorkingClockInTimeCfg = AttendUtil.joinYMDandHMS(
						AttendUtil.getDate(currentDate, isAcrossDay ? 1 : 0), endWorkingClockInTimeCfg);
				int earliestWorkingClockInTimeMinCfg = earliestWorkingClockInTimeCfg.getHours() * 60
						+ earliestWorkingClockInTimeCfg.getMinutes();
				for (JSONObject dingClockInRecord : currentUserDingClockInRecords) {
					//钉钉的打卡时间
					Date dingCreateTime = new Date(dingClockInRecord.getLong("fdUserCheckTime"));
					int dingCreateTimeMin = dingCreateTime.getHours() * 60 + dingCreateTime.getMinutes();
					//判断钉钉打卡时间是否在配置范围之内
					if (dingCreateTime.before(earliestWorkingClockInTimeCfg)
							|| dingCreateTime.after(endWorkingClockInTimeCfg)) {
						//不在范围之内，不处理
						logger.debug("非打卡时间范围内：" + dingCreateTime);
						continue;
					}
					//打卡时间如果超过了今天，就为跨天打卡
					if (isAcrossDay && !dingCreateTime.before(AttendUtil.getDate(currentDate, 1))) {
						dingCreateTimeMin = dingCreateTimeMin + 24 * 60;
					}

					//第一排班班次的最晚的上班打卡时间配置
					int latestWorkingClockInTimeCfg = getShouldOnWorkTime(workTimeConfiguration1);
					//获取用户最后排班班次的最晚下班打卡记录时间
					Date latestOffWorkTime = getAttendMainSignTime(lastWorkTimeOnConfiguration, newestUserSignedWorkedRecords);
					//获取最后排班班次的最早正常下班打卡时间
					lastOffWorkSignTimeMinConfiguration = getShouldOffWorkTime(latestOffWorkTime, lastWorkTimeConfiguration);

					if (dingCreateTimeMin <= latestWorkingClockInTimeCfg
							&& dingCreateTimeMin >= earliestWorkingClockInTimeMinCfg) {
						//上班时间
						// 判断该打卡记录为上班打卡记录
						addAttendMains(userSignedWorkRecords1, 1, dingClockInRecord, workTimeConfiguration1,
								newestUserSignedWorkedRecords,category);
					} else if (dingCreateTimeMin >= lastOffWorkSignTimeMinConfiguration) {
						//下班时间
						//判断该打卡记录为最后排班下班打卡记录
						addAttendMains(lastOffWorkRecord, 1, dingClockInRecord,
								lastWorkTimeConfiguration, newestUserSignedWorkedRecords,category);
					} else {
						if (signTimeConfigurations.size() == 2) {
							// 一班制
							// 上下班时间范围内
							addAttendMains(userSignedWorkRecords1, 2, dingClockInRecord, workTimeConfiguration1, newestUserSignedWorkedRecords,category);

							// 下班
							Date signedTime1 = getAttendMainSignTime(workTimeConfiguration1, newestUserSignedWorkedRecords);
							int nowStatus = 3;
							if (dingCreateTimeMin >= getShouldOffWorkTime(signedTime1, lastWorkTimeConfiguration)) {
								nowStatus = 1;
							}
							if (signedTime1 != null && !signedTime1.equals(dingCreateTime)) {
								addAttendMains(lastOffWorkRecord, nowStatus, dingClockInRecord,
										lastWorkTimeConfiguration, newestUserSignedWorkedRecords,category);
							}
						} else if (signTimeConfigurations.size() >= 4) {

							Date signTime2 = (Date) workTime2.get("signTime");
							int signTimeMin2 = signTime2.getHours() * 60
									+ signTime2.getMinutes();

							Date signTime3 = (Date) workTime3.get("signTime");
							int signTimeMin3 = signTime3.getHours() * 60
									+ signTime3.getMinutes();

							Date signTime4 = (Date) workTime4.get("signTime");
							int signTimeMin4 = signTime4.getHours() * 60
									+ signTime4.getMinutes();

							// 三班次
							Date signTime5 = signTimeConfigurations.size() >= 6
									? (Date) workTime5.get("signTime") : null;
							int signTimeMin5 = signTimeConfigurations.size() >= 6
									? (signTime5.getHours() * 60
									+ signTime5.getMinutes())
									: 0;

							Date signTime6 = signTimeConfigurations.size() >= 6
									? (Date) workTime6.get("signTime") : null;
							int signTimeMin6 = signTimeConfigurations.size() >= 6
									? (signTime6.getHours() * 60
									+ signTime6.getMinutes())
									: 0;

							if (dingCreateTimeMin > latestWorkingClockInTimeCfg
									&& dingCreateTimeMin < signTimeMin2) {// 早班时间区间
								addAttendMains(userSignedWorkRecords1, 2, dingClockInRecord,
										workTimeConfiguration1, newestUserSignedWorkedRecords,category);

								// 下班
								Date pSignTime = getAttendMainSignTime(workTimeConfiguration1,
										newestUserSignedWorkedRecords);
								int nowStatus = 3;
								if (dingCreateTimeMin >= getShouldOffWorkTime(pSignTime,
										workTime2)) {
									nowStatus = 1;
								}
								if (pSignTime != null
										&& !pSignTime.equals(dingCreateTime)) {
									addAttendMains(offWorkUserSignedRecords2, nowStatus, dingClockInRecord,
											workTime2, newestUserSignedWorkedRecords,category);
								}
							}
							if (dingCreateTimeMin >= signTimeMin2
									&& dingCreateTimeMin < signTimeMin3) {// 休息时间区间
								Date pSignTime = getAttendMainSignTime(workTimeConfiguration1,
										newestUserSignedWorkedRecords);
								int nowStatus = 3;
								if (dingCreateTimeMin >= getShouldOffWorkTime(pSignTime,
										workTime2)) {
									nowStatus = 1;
								}
								// 一班下班打卡状态
								Integer status2 = getAttendMainStatus(workTime2,
										newestUserSignedWorkedRecords);
								// 二班上班打卡状态
								Integer status3 = getAttendMainStatus(workTime3,
										newestUserSignedWorkedRecords);
								// 当前班次未打卡或者早退或者下一班打卡正常
								if (status2 == null || status2 == 0
										|| status2 == 3) {
									addAttendMains(offWorkUserSignedRecords2,
											nowStatus, dingClockInRecord,
											workTime2,
											newestUserSignedWorkedRecords,category);
								} else if (status3 != null && status3 == 1) {
									Date pSignTime3 = getAttendMainSignTime(
											workTime3, newestUserSignedWorkedRecords);
									if (pSignTime3 != null
											&& !pSignTime3.equals(dingCreateTime)) {
										addAttendMains(offWorkUserSignedRecords2,
												nowStatus, dingClockInRecord,
												workTime2,
												newestUserSignedWorkedRecords,category);
									}
								}
								// 午班上班
								Date pSignTime2 = getAttendMainSignTime(workTime2,
										newestUserSignedWorkedRecords);
								if (pSignTime2 != null
										&& !pSignTime2.equals(dingCreateTime)) {
									// 当前班次未打卡或者迟到
									if (status3 == null || status3 == 0
											|| status3 == 2) {
										addAttendMains(userSignedWorkRecords3, 1, dingClockInRecord,
												workTime3, newestUserSignedWorkedRecords,category);
									}
								}
							}
							if (signTimeConfigurations.size() == 4) {
								signTimeMin4 = getShouldOffWorkTime(signTime3,
										workTime4);
							}
							if (dingCreateTimeMin >= signTimeMin3
									&& dingCreateTimeMin < signTimeMin4) {
								// 晚班时间区间
								int nowStatus = 1;
								if (dingCreateTimeMin >= getShouldOnWorkTime(workTime3)) {
									nowStatus = 2;
								}
								addAttendMains(userSignedWorkRecords3, nowStatus,
										dingClockInRecord, workTime3, newestUserSignedWorkedRecords,category);

								// 下班打卡
								Date pSignTime3 = getAttendMainSignTime(workTime3,
										newestUserSignedWorkedRecords);
								int nowStatus4 = 3;
								if (dingCreateTimeMin >= getShouldOffWorkTime(pSignTime3,
										workTime4)) {
									nowStatus4 = 1;
								}
								if (pSignTime3 != null
										&& !pSignTime3.equals(dingCreateTime)) {
									addAttendMains(offWorkUserSignedRecord4, nowStatus4, dingClockInRecord,
											workTime4, newestUserSignedWorkedRecords,category);
								}
							}
							if (signTimeConfigurations.size() >= 6
									&& dingCreateTimeMin >= signTimeMin4
									&& dingCreateTimeMin < signTimeMin5) {// 三班次
								// 休息区间
								Date pSignTime = getSignedTime(userSignedWorkRecords3);
								int nowStatus = 3;
								if (dingCreateTimeMin >= getShouldOffWorkTime(pSignTime,
										workTime4)) {
									nowStatus = 1;
								}
								addAttendMains(offWorkUserSignedRecord4, nowStatus,
										dingClockInRecord, workTime4, newestUserSignedWorkedRecords,category);
								// 午班上班
								addAttendMains(userSignedWorkRecords5, 1, dingClockInRecord,
										workTime5, newestUserSignedWorkedRecords,category);
							}
							if (signTimeConfigurations.size() >= 6
									&& dingCreateTimeMin >= signTimeMin5
									&& dingCreateTimeMin < signTimeMin6) {
								// 晚班时间区间
								int nowStatus = 1;
								if (dingCreateTimeMin >= getShouldOnWorkTime(workTime5)) {
									nowStatus = 2;
								}
								addAttendMains(userSignedWorkRecords5, nowStatus, dingClockInRecord,
										workTime5, newestUserSignedWorkedRecords,category);

								// 下班打卡
								Date pSignTime5 = getSignedTime(userSignedWorkRecords5);
								int nowStatus6 = 3;
								if (dingCreateTimeMin >= getShouldOffWorkTime(pSignTime5,
										workTime6)) {
									nowStatus6 = 1;
								}
								addAttendMains(userSignedWorkRecords6, nowStatus6, dingClockInRecord,
										workTime6, newestUserSignedWorkedRecords,category);
							}
						}
					}
				}
				// 数据校验
				if (signTimeConfigurations.size() == 2) {
					Long createTime1 = getAttendMainCreateTime(workTimeConfiguration1, newestUserSignedWorkedRecords);
					Long createTime2 = getAttendMainCreateTime(signTimeConfigurations.get(1),
							newestUserSignedWorkedRecords);
					if (createTime1 != null && createTime2 != null
							&& createTime1.longValue() == createTime2.longValue()) {
						JSONObject workJson2 = getUserAttendMain(signTimeConfigurations.get(1),
								newestUserSignedWorkedRecords);
						newestUserSignedWorkedRecords.remove(workJson2);
					}
				}
				if (signTimeConfigurations.size() >= 4) {
					Long createTime1 = getAttendMainCreateTime(workTimeConfiguration1, newestUserSignedWorkedRecords);
					Long createTime2 = getAttendMainCreateTime(signTimeConfigurations.get(1),
							newestUserSignedWorkedRecords);
					Long createTime3 = getAttendMainCreateTime(signTimeConfigurations.get(2),
							newestUserSignedWorkedRecords);
					Long createTime4 = getAttendMainCreateTime(signTimeConfigurations.get(3),
							newestUserSignedWorkedRecords);

					Date signTime2 = (Date) workTime2.get("signTime");
					int signTimeMin2 = signTime2.getHours() * 60
							+ signTime2.getMinutes();
					Date signTime3 = (Date) workTime3.get("signTime");
					int signTimeMin3 = signTime3.getHours() * 60
							+ signTime3.getMinutes();

					Date signTime4 = (Date) workTime4.get("signTime");
					int signTimeMin4 = signTime4.getHours() * 60
							+ signTime4.getMinutes();
					Integer status1 = getAttendMainStatus(workTimeConfiguration1, newestUserSignedWorkedRecords);
					Integer status2 = getAttendMainStatus(workTime2, newestUserSignedWorkedRecords);
					Integer status3 = getAttendMainStatus(workTime3, newestUserSignedWorkedRecords);
					//当两班制时，如果一班制未打卡或请假/出差/外出，同步时则将下午的第一次打卡默认为第二班制的上班卡，而不是一班的下班卡。
					if (createTime2 != null && (((status1 == null || status1 == 0) && (status2 != null && status2 == 1)) || (AttendUtil.isAttendBuss(status1 + "") && AttendUtil.isAttendBuss(status2 + ""))) && (status3 == null || status3 == 0)) {
						Date createTime = new Date(createTime2);
						int _createTime = createTime.getHours() * 60
								+ createTime.getMinutes();
						if ((_createTime >= signTimeMin2
								&& _createTime <= signTimeMin3)) {
							//二班时间区间
							int nowStatus = 1;
							if (_createTime > getShouldOnWorkTime(workTime3)) {
								nowStatus = 2;
							}
							JSONObject json = null;
							for (JSONObject dingRecord : currentUserDingClockInRecords) {
								Long fdUserCheckTime = dingRecord.getLong("fdUserCheckTime");
								if (createTime2.equals(fdUserCheckTime)) {
									json = dingRecord;
									break;
								}
							}
							addAttendMains(userSignedWorkRecords3, nowStatus, json, workTime3,
									newestUserSignedWorkedRecords,category);
							JSONObject json2 = getUserAttendMain(workTime2, newestUserSignedWorkedRecords);
							if (json2 != null) {
								json2.put("docCreateTime",
										AttendUtil.joinYMDandHMS(currentDate, signTime2).getTime());
								json2.put("fdStatus",
										AttendUtil.isAttendBuss(status2 + "") ? status2 : 0);
							}
						}
					}
					createTime2 = getAttendMainCreateTime(workTime2, newestUserSignedWorkedRecords);
					createTime3 = getAttendMainCreateTime(workTime3, newestUserSignedWorkedRecords);

					JSONObject workJson1 = getUserAttendMain(workTimeConfiguration1, newestUserSignedWorkedRecords);
					JSONObject workJson2 = getUserAttendMain(signTimeConfigurations.get(1), newestUserSignedWorkedRecords);
					JSONObject workJson3 = getUserAttendMain(signTimeConfigurations.get(2),
							newestUserSignedWorkedRecords);
					JSONObject workJson4 = getUserAttendMain(signTimeConfigurations.get(3),
							newestUserSignedWorkedRecords);

					if (createTime1 != null && createTime2 != null
							&& createTime1.longValue() == createTime2.longValue()) {
						newestUserSignedWorkedRecords.remove(workJson2);
					}
					if (createTime3 != null && createTime4 != null
							&& createTime4.longValue() == createTime3.longValue()) {
						newestUserSignedWorkedRecords.remove(workJson4);
					}

					if (createTime2 != null && createTime3 != null
							&& createTime2.longValue() > createTime3.longValue()) {
						if (workJson2 != null) {
							workJson2.put("docCreateTime", createTime3);
						}
						if (workJson3 != null) {
							workJson3.put("docCreateTime", createTime2);
						}
					}
					if (createTime2 != null && createTime3 != null
							&& createTime2.longValue() == createTime3.longValue()) {
						if (status1 == null || status1 == 0) {
							newestUserSignedWorkedRecords.remove(workJson2);
							removePatchDate(currentUserDingClockInRecords,
									createTime2, workTime2,
									newestUserSignedWorkedRecords, currentDate,
									offWorkUserSignedRecords2,category);
						} else {
							newestUserSignedWorkedRecords.remove(workJson3);
						}
					}
					//
					if (signTimeConfigurations.size() >= 6) {
						Long createTime5 = getAttendMainCreateTime(
								signTimeConfigurations.get(4), newestUserSignedWorkedRecords);
						Long createTime6 = getAttendMainCreateTime(
								signTimeConfigurations.get(5), newestUserSignedWorkedRecords);
						JSONObject workJson5 = getUserAttendMain(signTimeConfigurations.get(4),
								newestUserSignedWorkedRecords);
						JSONObject workJson6 = getUserAttendMain(signTimeConfigurations.get(5),
								newestUserSignedWorkedRecords);
						if (createTime5 != null && createTime6 != null
								&& createTime5.longValue() == createTime6
								.longValue()) {
							newestUserSignedWorkedRecords.remove(workJson6);
						}
						if (createTime4 != null && createTime5 != null) {
							if (createTime4.longValue() > createTime5.longValue()) {
								if (workJson4 != null) {
									workJson4.put("docCreateTime", createTime5);
								}
								if (workJson5 != null) {
									workJson5.put("docCreateTime", createTime4);
								}
							}
						}

					}
				}
			}
		}
	}
	/**
	 * 为请假的时间大小标识
	 * @param record1
	 * @param dateList 打卡记录列表。一定是按照打卡顺序传递进来的。或者是原始打卡记录在前，流程打卡的记录时间再后
	 */
	private void LeaveTimeFlag(JSONObject record1,List<JSONObject> dateList){
		if(record1 !=null && CollectionUtils.isNotEmpty(dateList)) {
			Number fdStatus = (Number) record1.get("fdStatus");
			Long createTime = record1.getLong("docCreateTime");
			Integer levelStatusFlage = 5;
			if (levelStatusFlage.equals(fdStatus)) {
				//重复的时间删除
				List<JSONObject> indexs =new ArrayList<JSONObject>();
				for (JSONObject dingRecord : dateList) {
					Date dingCreateTime = new Date(dingRecord.getLong("fdUserCheckTime"));
					if(createTime !=null && createTime.equals(dingCreateTime.getTime())){
						dingRecord.put("leaveTimeFlag",Boolean.TRUE);
					}
				}
				dateList.removeAll(indexs);
			}
		}
	}
	/**
	 * 删除打卡时间是请假/出差生成的时间
	 * @param record1
	 * @param dateList 打卡记录列表。一定是按照打卡顺序传递进来的。或者是原始打卡记录在前，流程打卡的记录时间再后
	 */
	private void removeLeaveTime(JSONObject record1,List<JSONObject> dateList){
		if(record1 !=null && CollectionUtils.isNotEmpty(dateList)) {
			Number fdStatus = (Number) record1.get("fdStatus");
			Long createTime = record1.getLong("docCreateTime");
			Integer levelStatusFlage = 5;
			if (levelStatusFlage.equals(fdStatus)) {
				//重复的时间删除
				for (Iterator<JSONObject> iter= dateList.iterator();iter.hasNext();) {
					JSONObject dingRecord = iter.next();
					Date dingCreateTime = new Date(dingRecord.getLong("fdUserCheckTime"));
					if(createTime !=null && createTime.equals(dingCreateTime.getTime())){
						iter.remove();
					}
				}
			}
		}
	}

	/**
	 * @param dingList
	 *            用户当前打卡记录列表
	 * @param signedRecordList
	 *            已打卡列表
	 * @param workTime1
	 *            同班次早班信息
	 * @param workTime2 同班次晚班信息
	 * @param mainList
	 *            最终用户打卡记录信息
	 * @param date 日期
	 * @throws Exception
	 */
	private void genAttendMain(List<JSONObject> dingList,
							   List<JSONObject> signedRecordList, Map workTime1, Map workTime2,
							   List<JSONObject> mainList, Date date,SysAttendCategory category) throws Exception {
		JSONObject record1 = getSignRecord(signedRecordList, workTime1);
		JSONObject record2 = getSignRecord(signedRecordList, workTime2);
		removeLeaveTime(record1,dingList);
		removeLeaveTime(record2,dingList);
		putAttendMain(record1, workTime1, mainList,category);
		putAttendMain(record2, workTime2, mainList,category);
		if(logger.isDebugEnabled()) {
			logger.debug("打卡记录1：" + record1);
			logger.debug("打卡记录2：" + record2);
		}
		// 是否支持跨天
		boolean isAcrossDay = (Boolean) workTime1.get("isAcrossDay");
		// 最早/最晚打卡时间
		Date fdStartTime = (Date) workTime1.get("fdStartTime");
		Date fdEndTime = (Date) workTime1.get("fdEndTime");
		if (fdStartTime == null) {
			fdStartTime = AttendUtil.getDate(new Date(), 0);
		}
		if (fdEndTime == null) {
			fdEndTime = AttendUtil.getEndDate(new Date(), 0);
		}
		// 实际最早/最晚打卡时间戳
		fdStartTime = AttendUtil.joinYMDandHMS(date, fdStartTime);
		fdEndTime = AttendUtil.joinYMDandHMS(AttendUtil.getDate(date, isAcrossDay ? 1 : 0), fdEndTime);

		int fdStartTimeMin = fdStartTime.getHours() * 60 + fdStartTime.getMinutes();

		//调整 综合工时的基准打卡时间 根据第一次打开计算下班时间
		if(Integer.valueOf(3).equals(category.getFdShiftType())){
			if(dingList!=null && dingList.size() >0){
				Float fdTotalTime = category.getFdTotalTime();
				JSONObject objDing = dingList.get(0);
				long fdUserCheckTime = objDing.getLong("fdUserCheckTime");
				Date tempCreateTime =AttendUtil.removeSecond(new Date(fdUserCheckTime));
				workTime1.put("signTime",tempCreateTime);
				long totle = (long)(fdTotalTime * 1000 * 60 * 60);
				Date tempCreateTime2 =AttendUtil.removeSecond(new Date(fdUserCheckTime+totle));
				//判断是否跨天
				int Time1 = tempCreateTime.getHours() * 60 + tempCreateTime.getMinutes();
				int Time2 = tempCreateTime2.getHours() * 60 + tempCreateTime2.getMinutes();
				if(Time1>Time2){
					workTime2.put("signTime",category.getFdEndTime());
				}else{
					workTime2.put("signTime",tempCreateTime2);
				}

			}
//			workTime1.put("signTime",DateUtil.convertStringToDate("2023-01-01 09:00:00"));
//			workTime2.put("signTime",DateUtil.convertStringToDate("2023-01-01 18:00:00"));
		}


		for (JSONObject dingRecord : dingList) {
			Date createTime = new Date(dingRecord.getLong("fdUserCheckTime"));
			//去除秒
			Date tempCreateTime =AttendUtil.removeSecond(createTime);
			// 非打卡时间范围
			if (tempCreateTime.before(fdStartTime)
					|| tempCreateTime.after(fdEndTime)) {
				continue;
			}
			//打卡时间转成分钟数计算
			int _createTime = createTime.getHours() * 60 + createTime.getMinutes();
			//跨天打卡 加上24小时
			if (isAcrossDay && !createTime.before(AttendUtil.getDate(date, 1))) {
				_createTime = _createTime + 24 * 60;
			}
			//正常范围内的上班时间，分钟数
			int onTempDate = getShouldOnWorkTime(workTime1);
			//获取用户已经发生的上班的打卡时间
			Date signedTime1 = getAttendMainSignTime(workTime1, mainList);
			//正常范围内，下班时间 分钟数。包括计算弹性工作日
			int signTimeMin2 = getShouldOffWorkTime(signedTime1, workTime2);

			if(category.getFdShiftType() == 4){
				if (dingList.size()==1||_createTime <= onTempDate && _createTime >= fdStartTimeMin) 
					addAttendMains(record1, 1, dingRecord, workTime1, mainList,category);
				else
					addAttendMains(record1, 1, dingRecord, workTime2, mainList,category);
			}else{
				// 上班时间
				if (_createTime <= onTempDate && _createTime >= fdStartTimeMin) {
					//打卡时间，小于或者等于标准打卡时间(含弹性时间)。并且在最早打卡时间之后。
					addAttendMains(record1, 1, dingRecord, workTime1, mainList,category);
				} else if (_createTime >= signTimeMin2) {
					int fdStatus = 1;
					if(category.getFdShiftType() == 3){
						Float fdTotalTime = category.getFdTotalTime();
						if(signedTime1 !=null && ((createTime.getTime() -signedTime1.getTime())/1000/60/60) <fdTotalTime ){
							fdStatus = 3;
						}
					}
					//打卡时间在下班时间之后。则是下班
					addAttendMains(record2, fdStatus, dingRecord, workTime2,mainList,category);

				} else {
					int fdStatus = 2;
					if(category.getFdShiftType() == 3){
						fdStatus = 1;
					}
					// 上下班时间范围内
					addAttendMains(record1, fdStatus, dingRecord, workTime1, mainList,category);
					//上班打卡时间
					signedTime1 = getAttendMainSignTime(workTime1, mainList);
					//下班
					if (signedTime1 != null && !signedTime1.equals(createTime)) {
						fdStatus = 3;
						if(category.getFdShiftType() == 3){
							Float fdTotalTime = category.getFdTotalTime();
							if(signedTime1 !=null && ((createTime.getTime() -signedTime1.getTime())/1000/60/60) >=fdTotalTime ){
								//如果上班时间已经达到了标准工作时长那么不要设置早退
								fdStatus = 1;
							}
						}
						addAttendMains(record2, fdStatus, dingRecord, workTime2,mainList,category);
					}

				}
			}

		}

		Long createTime1 = getAttendMainCreateTime(workTime1, mainList);
		Long createTime2 = getAttendMainCreateTime(workTime2, mainList);
		// 打卡记录数据校验
		if (createTime1 != null && createTime2 != null
				&& createTime1.longValue() == createTime2.longValue()) {
			mainList.remove(getUserAttendMain(workTime2, mainList));
		}
	}

	/**
	 * 两班制打卡
	 *
	 * @param dateList
	 * @param signedRecordList
	 * @param workTime1
	 * @param workTime2
	 * @param mainList
	 * @param date
	 *            打卡工作时间(非实际打卡时间)
	 * @throws Exception
	 */
	private void genAttendMain(List<JSONObject> dingList,
							   List<JSONObject> signedRecordList, Map workTime1, Map workTime2,
							   Map workTime3, Map workTime4, List<JSONObject> mainList,
							   Date date, SysAttendCategory category) throws Exception {
		JSONObject record1 = getSignRecord(signedRecordList, workTime1);
		JSONObject record2 = getSignRecord(signedRecordList, workTime2);
		JSONObject record3 = getSignRecord(signedRecordList, workTime3);
		JSONObject record4 = getSignRecord(signedRecordList, workTime4);

		removeLeaveTime(record1,dingList);
		removeLeaveTime(record2,dingList);
		removeLeaveTime(record3,dingList);
		removeLeaveTime(record4,dingList);

		putAttendMain(record1, workTime1, mainList,category);
		putAttendMain(record2, workTime2, mainList,category);
		putAttendMain(record3, workTime3, mainList,category);
		putAttendMain(record4, workTime4, mainList,category);

		Integer fdLateTime = (Integer) workTime1.get("fdLateTime");
		Integer fdLeftTime = (Integer) workTime1.get("fdLeftTime");
		Boolean fdIsFlex = (Boolean) workTime1.get("fdIsFlex");
		Integer fdFlexTime = (Integer) workTime1.get("fdFlexTime");
		fdLateTime = fdLateTime == null ? 0 : fdLateTime;
		fdLeftTime = fdLeftTime == null ? 0 : fdLeftTime;
		fdIsFlex = fdIsFlex == null ? false : fdIsFlex.booleanValue();
		fdFlexTime = fdFlexTime == null ? 0 : fdFlexTime;

		Date signTime1 = (Date) workTime1.get("signTime");
		int signTimeMin1 = signTime1.getHours() * 60
				+ signTime1.getMinutes();
		Date signTime2 = (Date) workTime2.get("signTime");
		int signTimeMin2 = signTime2.getHours() * 60
				+ signTime2.getMinutes();
		Date signTime3 = (Date) workTime3.get("signTime");
		int signTimeMin3 = signTime3.getHours() * 60
				+ signTime3.getMinutes();
		Date signTime4 = (Date) workTime4.get("signTime");
		int signTimeMin4 = signTime4.getHours() * 60
				+ signTime4.getMinutes();
		// 是否支持跨天
		boolean isAcrossDay = (Boolean) workTime1.get("isAcrossDay");
		// 一班次最早/最晚打卡时间
		Date fdStartTime = (Date) workTime1.get("fdStartTime");
		Date fdEndTime1 = (Date) workTime1.get("fdEndTime1");
		// 二班次最早/最晚打卡时间
		Date fdStartTime2 = (Date) workTime1.get("fdStartTime2");
		Date fdEndTime = (Date) workTime1.get("fdEndTime");

		if (fdStartTime == null) {
			fdStartTime = AttendUtil.getDate(new Date(), 0);
		}
		if (fdEndTime == null) {
			fdEndTime = AttendUtil.getEndDate(new Date(), 0);
		}
		if (fdStartTime2 == null) {
			fdStartTime2 = signTime2;
		}
		if (fdEndTime1 == null) {
			fdEndTime1 = signTime3;
		}
		// 实际最早/最晚打卡时间戳
		fdStartTime = AttendUtil.joinYMDandHMS(date, fdStartTime);
		fdEndTime = AttendUtil.joinYMDandHMS(
				AttendUtil.getDate(date, isAcrossDay ? 1 : 0), fdEndTime);

		int fdStartTimeMin = fdStartTime.getHours() * 60
				+ fdStartTime.getMinutes();
		int fdEndTimeMin = fdEndTime.getHours() * 60 + fdEndTime.getMinutes();
		int fdStartTime2Min = fdStartTime2.getHours() * 60
				+ fdStartTime2.getMinutes();
		int fdEndTime1Min = fdEndTime1.getHours() * 60
				+ fdEndTime1.getMinutes();

		for (JSONObject dingRecord : dingList) {
			Date createTime = new Date(dingRecord.getLong("fdUserCheckTime"));
			int _createTime = createTime.getHours() * 60
					+ createTime.getMinutes();
			//去除秒
			Date tempCreateTime =AttendUtil.removeSecond(createTime);
			// 非打卡时间范围
			if (tempCreateTime.before(fdStartTime)
					|| tempCreateTime.after(fdEndTime)) {
				continue;
			}
			// 跨天打卡 加上24小时
			if (isAcrossDay && !createTime.before(AttendUtil.getDate(date, 1))) {
				_createTime = _createTime + 24 * 60;
			}
			//最晚正常上班打卡时间
			int onTempDate = getShouldOnWorkTime(workTime1);
			//取对应打卡时间
			Date onLastWorkTime = getAttendMainSignTime(workTime3, mainList);
			//最早正常下班打卡时间
			int offLastWork = getShouldOffWorkTime(onLastWorkTime, workTime4);
			if (_createTime <= onTempDate && _createTime >= fdStartTimeMin) {// 上班时间
				addAttendMains(record1, 1, dingRecord, workTime1, mainList,category);
			} else if (_createTime >= offLastWork) {// 下班时间
				int fdStatus = 1;
				addAttendMains(record4, fdStatus, dingRecord, workTime4, mainList,category);
			} else if (_createTime > onTempDate
					&& _createTime < signTimeMin2) {

				// 早班时间区间
				addAttendMains(record1, 2, dingRecord, workTime1,
						mainList,category);

				//下班
				Date pSignTime = getAttendMainSignTime(workTime1, mainList);
				int nowStatus = 3;
				if (_createTime >= getShouldOffWorkTime(pSignTime,
						workTime2)) {
					nowStatus = 1;
				}
				if (pSignTime != null && !pSignTime.equals(createTime)) {
					addAttendMains(record2, nowStatus, dingRecord,
							workTime2, mainList,category);
				}
			} else if (_createTime >= signTimeMin2
					&& _createTime < signTimeMin3) {
				// 休息时间区间
				Date pSignTime = getAttendMainSignTime(workTime1, mainList);
				int nowStatus = 3;
				if (_createTime >= getShouldOffWorkTime(pSignTime,
						workTime2)) {
					nowStatus = 1;
				}
				// 最晚打卡时间
				if (_createTime <= fdEndTime1Min) {
					// 第一班下班打卡状态
					Integer status2 = getAttendMainStatus(workTime2,
							mainList);
					Integer status3 = getAttendMainStatus(workTime3, mainList);
					// 当前班次未打卡或早退以及二班已打卡时
					if (status2 == null || status2 == 0 || (status2==3 && status3!=null && status3!=0)) {
						if (_createTime <= fdEndTime1Min) {
							addAttendMains(record2, nowStatus, dingRecord,
									workTime2, mainList,category);
						}
					}
				}
				// 最早上班时间
				if (_createTime >= fdStartTime2Min) {
					// 午班上班
					Date pSignTime2 = getAttendMainSignTime(workTime2,
							mainList);
					if (pSignTime2 ==null || (pSignTime2 != null && !pSignTime2.equals(createTime))) {
						addAttendMains(record3, 1, dingRecord, workTime3, mainList,category);
					}
				}
			} else if (_createTime >= signTimeMin3
					&& _createTime < offLastWork) {
				//晚班时间区间
				int nowStatus = 1;
				if (_createTime > getShouldOnWorkTime(workTime3)) {
					nowStatus = 2;
				}
				addAttendMains(record3, nowStatus, dingRecord, workTime3, mainList,category);

				// 下班打卡
				Date pSignTime3 = getAttendMainSignTime(workTime3, mainList);
				Integer nowStatus4 = 3;
				if (pSignTime3 != null && !pSignTime3.equals(createTime)) {
					addAttendMains(record4, nowStatus4, dingRecord, workTime4, mainList,category);
				}
			}
		}

		// 打卡数据校验
		Long createTime1 = getAttendMainCreateTime(workTime1, mainList);
		Long createTime2 = getAttendMainCreateTime(workTime2, mainList);
		Long createTime3 = getAttendMainCreateTime(workTime3, mainList);
		Long createTime4 = getAttendMainCreateTime(workTime4, mainList);

		Integer status1 = getAttendMainStatus(workTime1, mainList);
		Integer status2 = getAttendMainStatus(workTime2, mainList);
		Integer status3 = getAttendMainStatus(workTime3, mainList);
		//当两班制时，如果一班制未打卡或请假/出差/外出，同步时则将下午的第一次打卡默认为第二班制的上班卡，而不是一班的下班卡。
		if(createTime2 != null && (((status1==null || status1==0) && (status2!=null && status2==1)) || (AttendUtil.isAttendBuss(status1 + "") && AttendUtil.isAttendBuss(status2 + ""))) && (status3==null || status3==0)) {
			Date createTime =new Date(createTime2);
			int _createTime = createTime.getHours() * 60
					+ createTime.getMinutes();
			if (_createTime >= fdStartTime2Min
					&& _createTime <= signTimeMin3) {
				//晚班时间区间
				int nowStatus = 1;
				if (_createTime > getShouldOnWorkTime(workTime3)) {
					nowStatus = 2;
				}
				JSONObject json=null;
				for (JSONObject dingRecord : dingList) {
					Long fdUserCheckTime = dingRecord.getLong("fdUserCheckTime");
					if(createTime2.equals(fdUserCheckTime)) {
						json=dingRecord;
						break;
					}
				}
				addAttendMains(record3, nowStatus, json, workTime3,
						mainList,category);
				JSONObject json2= getUserAttendMain(workTime2, mainList);
				if(json2!=null) {
					json2.put("docCreateTime",
							AttendUtil.joinYMDandHMS(date, signTime2).getTime());
					json2.put("fdStatus",
							AttendUtil.isAttendBuss(status2 + "")?status2:0);
				}
			}
		}
		createTime2 = getAttendMainCreateTime(workTime2, mainList);
		createTime3 = getAttendMainCreateTime(workTime3, mainList);

		JSONObject workJson2 = getUserAttendMain(workTime2, mainList);
		JSONObject workJson3 = getUserAttendMain(workTime3, mainList);
		JSONObject workJson4 = getUserAttendMain(workTime4, mainList);

		if (createTime1 != null && createTime2 != null
				&& createTime1.longValue() == createTime2.longValue()) {
			mainList.remove(workJson2);
		}
		if (createTime3 != null && createTime4 != null
				&& createTime4.longValue() == createTime3.longValue()) {
			mainList.remove(workJson4);
		}

		if (createTime2 != null && createTime3 != null
				&& createTime2.longValue() > createTime3.longValue()) {
			if (workJson2 != null) {
				workJson2.put("docCreateTime", createTime3);
			}
			if (workJson3 != null) {
				workJson3.put("docCreateTime", createTime2);
			}
		}
		if (createTime2 != null && createTime3 != null
				&& createTime2.longValue() == createTime3.longValue()) {
			if (status1 == null || status1 == 0) {
				mainList.remove(workJson2);
				removePatchDate(dingList, createTime2, workTime2,
						mainList, date, record2,category);
			} else {
				mainList.remove(workJson3);
			}
		}
	}

	/**
	 * 移除一条最终结果的有效数据要添加回数据库原有记录
	 *
	 * @param dingList
	 *            钉钉原始数据
	 * @param createTime
	 *            打卡时间
	 * @param workTime
	 *            班次信息
	 * @param mainList
	 *            最新打卡记录
	 * @param date
	 *            打卡日期
	 * @param record
	 *            数据库打卡记录
	 */
	private void removePatchDate(List<JSONObject> dingList, Long createTime,
								 Map workTime, List<JSONObject> mainList,
								 Date date, JSONObject record, SysAttendCategory category) {
		if (record == null) {
			return;
		}
		JSONObject json = null;
		for (JSONObject dingRecord : dingList) {
			Long fdUserCheckTime = dingRecord.getLong("fdUserCheckTime");
			if (createTime.equals(fdUserCheckTime)) {
				json = dingRecord;
				break;
			}
		}
		Integer status = (Integer) record.get("fdStatus");
		addAttendMains(record, status, json, workTime, mainList,category);
		JSONObject json2 = getUserAttendMain(workTime, mainList);
		if (json2 != null) {
			Integer status2 = (Integer) json2.get("fdStatus");
			if (status2 == 0) {
				Date signTime = (Date) workTime.get("signTime");
				json2.put("docCreateTime",
						AttendUtil.joinYMDandHMS(date, signTime).getTime());
			}
		}
	}

	/**
	 * 获取日期与时间组合的时间戳
	 *
	 * @param date
	 *            日期
	 * @param dateTime
	 *            时间 忽略秒之后时间
	 * @return
	 */
	private Date getDateAndTime(Date date, Date dateTime) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.HOUR_OF_DAY, dateTime.getHours());
		ca.set(Calendar.MINUTE, dateTime.getMinutes());
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);
		return ca.getTime();
	}

	/**
	 * 	获取用户考勤记录
	 *
	 * @param userSet 用户列表
	 * @param date 日期
	 * @param typeName 过滤类型标识
	 * @return
	 * @throws Exception
	 */
	private Map getUserSignedList(Set<String> userSet, Date date,String typeName) throws Exception {
		boolean isException =false;
		TransactionStatus status =null;
		Map<String, List<JSONObject>> records = new HashMap<String, List<JSONObject>>();
		try {
			status = TransactionUtils.beginNewTransaction();
			List<Object> userList = Arrays.asList(userSet.toArray());
			String signedSql = "select fd_id,fd_status,fd_work_type,fd_work_id,fd_state,doc_creator_id,doc_create_time,fd_location,fd_source_type,fd_work_key,fd_outside,fd_app_name,fd_wifi_name,fd_is_across from sys_attend_main where " +
					" doc_creator_id in (:docCreatorIds) and (doc_status=0 or doc_status is null) and fd_work_type in(0,1) ";
			if("exchange".equals(typeName)) {
				signedSql += " and fd_status=0 ";
			}
			String searchSqlOne = " and doc_create_time>=:beginTime and doc_create_time<:endTime and (fd_is_across is null or fd_is_across=:fdIsAcross0) ";
			String searchSqlTwo ="  and fd_is_across=:fdIsAcross1 and doc_create_time>=:nextBegin and doc_create_time<:nextEnd  ";

			List signedListOne = this.sysAttendCategoryService.getBaseDao().getHibernateSession().createNativeQuery(signedSql + searchSqlOne)
					.setBoolean("fdIsAcross0", false)
					.setDate("beginTime", AttendUtil.getDate(date, 0))
					.setDate("endTime", AttendUtil.getDate(date, 1))
					.setParameterList("docCreatorIds", userList)
					.list();

			List signedListTwo = this.sysAttendCategoryService.getBaseDao().getHibernateSession().createNativeQuery(signedSql + searchSqlTwo)
					.setParameterList("docCreatorIds", userList)
					.setBoolean("fdIsAcross1", true)
					.setDate("nextBegin", AttendUtil.getDate(date, 1))
					.setDate("nextEnd", AttendUtil.getDate(date, 2))
					.list();

			List signedList=AttendUtil.unionList(signedListOne,signedListTwo);


			for (int i = 0; i < signedList.size(); i++) {
				Object[] record = (Object[]) signedList.get(i);
				JSONObject ret = new JSONObject();
				ret.put("fdId", (String) record[0]);
				Number fdStatus = (Number) record[1];
				ret.put("fdStatus", fdStatus == null ? null : fdStatus.intValue());
				Number fdWorkType = (Number) record[2];
				ret.put("fdWorkType",
						fdWorkType == null ? null : fdWorkType.intValue());
				ret.put("fdWorkId", (String) record[3]);
				ret.put("fdWorkKey", StringUtil.getString((String) record[9]));
				Number fdState = (Number) record[4];
				ret.put("fdState", fdState == null ? null : fdState.intValue());
				ret.put("docCreatorId", (String) record[5]);
				Date createTime = null;
				if(record[6] !=null){
					createTime = DateUtil.convertStringToDate(record[6].toString(),"yyyy-MM-dd HH:mm:ss");
				}
				ret.put("docCreateTime", createTime ==null?null:createTime.getTime());
				ret.put("fdLocation", StringUtil.getString((String) record[7]));
				ret.put("fdSourceType", StringUtil.getString((String) record[8]));
				Boolean fdOutside = AttendUtil.getBooleanField(record[10]);
				ret.put("fdOutside", Boolean.TRUE.equals(fdOutside));
				ret.put("fdAppName", StringUtil.getString((String) record[11]));
				ret.put("fdWifiName", StringUtil.getString((String) record[12]));

				Object fdIsAcrossTemp = record[13];
				if(fdIsAcrossTemp !=null) {
					if (fdIsAcrossTemp instanceof Boolean) {
						ret.put("fdIsAcross", fdIsAcrossTemp);
					} else {
						//兼容不同数据库对boolean的支持
						ret.put("fdIsAcross", "1".equals(fdIsAcrossTemp.toString()));
					}
				}
				String docCreatorId = (String) record[5];
				if (!records.containsKey(docCreatorId)) {
					records.put(docCreatorId, new ArrayList<JSONObject>());
				}
				List<JSONObject> recordList = records.get(docCreatorId);
				recordList.add(ret);
			}
			TransactionUtils.commit(status);
		} catch (Exception e) {
			isException =true;
			e.printStackTrace();
		} finally {
			if(status !=null && isException){
				TransactionUtils.rollback(status);
			}
		}
		return records;
	}

	/**
	 * 	获取用户出差,请假,外出等记录
	 *
	 * @param userIdSet
	 * 	用戶Id集
	 * @param currentDate
	 * @return
	 * @throws Exception
	 */
	private Map getUserBussList(Set<String> userIdSet, Date currentDate)
			throws Exception {
		List<Object> userList = Arrays.asList(userIdSet.toArray());
		List<Integer> fdTypes = new ArrayList<Integer>();
		fdTypes.add(4);
		fdTypes.add(5);
		fdTypes.add(7);
		// 出差/请假/外出记录
		List<SysAttendBusiness> busList = this.getSysAttendBusinessService()
				.findBussList(userList,  AttendUtil.getDate(currentDate, -1), AttendUtil.getDate(currentDate, 2),
						fdTypes);
		logger.debug("查询用户出差/请假/外出记录"+userList+",时间："+currentDate+"，记录信息："+busList);
		Map<String, List<JSONObject>> records = new HashMap<String, List<JSONObject>>();
		for (SysAttendBusiness bus : busList) {
			List<SysOrgElement> targets = bus.getFdTargets();
			for (SysOrgElement ele : targets) {
				JSONObject ret = new JSONObject();
				String docCreatorId = ele.getFdId();
				Long fdBusStartTime = getUserBusTime(bus, true);
				Long fdBusEndTime = getUserBusTime(bus, false);
				if (fdBusStartTime == null || fdBusEndTime == null) {
					continue;
				}
				ret.put("fdBusStartTime", fdBusStartTime);
				ret.put("fdBusEndTime", fdBusEndTime);
				ret.put("fdBusType", bus.getFdType());
				ret.put("fdBusId", bus.getFdId());
				ret.put("fdLeaveType", bus.getFdBusType());// 假期编号
				if (!records.containsKey(docCreatorId)) {
					records.put(docCreatorId, new ArrayList<JSONObject>());
				}
				List<JSONObject> recordList = records.get(docCreatorId);
				recordList.add(ret);
			}
		}
		return records;
	}

	/**
	 * 获取用户出差/请假/外出的开始或结束时间
	 *
	 * @param main
	 * @param isStartTime
	 * @return
	 */
	private Long getUserBusTime(SysAttendBusiness buss, boolean isStartTime) {
		if (buss == null) {
			return null;
		}
		Date startTime = buss.getFdBusStartTime();
		Date endTime = buss.getFdBusEndTime();
		if (Integer.valueOf(5).equals(buss.getFdType())) {
			// 请假
			if (!Integer.valueOf(3).equals(buss.getFdStatType())) {
				//不是按小时计算的
				startTime = AttendUtil.getDate(startTime, 0);
				endTime = AttendUtil.getEndDate(endTime, 0);
			}
			//按半天计算
			if (Integer.valueOf(2).equals(buss.getFdStatType())) {
				Integer startNoon = buss.getFdStartNoon();
				Integer endNoon = buss.getFdEndNoon();
				Calendar cal = Calendar.getInstance();
				if (Integer.valueOf(2).equals(startNoon)) {
					cal.setTime(startTime);
					cal.set(Calendar.HOUR_OF_DAY, 12);
					startTime = cal.getTime();
				}
				if (Integer.valueOf(1).equals(endNoon)) {
					cal.setTime(endTime);
					cal.set(Calendar.HOUR_OF_DAY, 12);
					endTime = cal.getTime();
				}
			}
		}
		if (isStartTime) {
			return startTime.getTime();
		}
		return endTime.getTime();
	}

	/**
	 * 	从钉钉、微信原始记录中提取用户。获取对应日期与考勤组的关系
	 * @param idSets   用户id集合
	 * @param currentDate 日期
	 * 	原始钉钉、微信打卡记录
	 * @return Map<String, List<Map<String, String>>>
	 * 	key为考勤组，value为考勤组下的用户Id和用户父类Id的Map集合
	 * @throws Exception
	 */
	private Map<String, List<Map<String, String>>> getUserCategory(Set<String> idSets,Date currentDate) throws Exception {
		Map<String, List<Map<String, String>>> cateMap = new HashMap<String, List<Map<String, String>>>();
		if(CollectionUtils.isEmpty(idSets)){
			return cateMap;
		}
//        String[] ids = new String[] {};
//        ids = idSets.toArray(ids);
		//查询人员ID和人员的组织架构ID
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setWhereBlock(HQLUtil.buildLogicIN(" sysOrgElement.fdId",Lists.newArrayList(idSets)));
		hqlInfo.setSelectBlock("sysOrgElement.fdId,sysOrgElement.fdHierarchyId,sysOrgElement.fdIsAvailable,sysOrgElement.fdPreDeptId");
		List<Object[]> eleList = getSysOrgElementService().findValue(hqlInfo);

		for (Object[] params : eleList) {
			SysOrgElement orgEle =new SysOrgElement();
			orgEle.setFdId(String.valueOf(params[0]));
			orgEle.setFdHierarchyId(String.valueOf(params[1]));
			orgEle.setFdPreDeptId(params[3] ==null?null:String.valueOf(params[3]));
			Object fdIsAvailableObj = params[2];
			if(fdIsAvailableObj !=null) {
				if (fdIsAvailableObj instanceof Boolean) {
					orgEle.setFdIsAvailable((Boolean) fdIsAvailableObj);
				} else {
					orgEle.setFdIsAvailable("0".equals(fdIsAvailableObj.toString()) ? false : true);
				}
			}
			String cateId = sysAttendCategoryService.getCategory(orgEle,currentDate,false);
			if (StringUtil.isNull(cateId)) {
				continue;
			}
			List idList = new ArrayList();
			if (!cateMap.containsKey(cateId)) {
				cateMap.put(cateId, idList);
			}
			idList = cateMap.get(cateId);
			Map<String, String> orgInfo = new HashMap<String, String>();
			orgInfo.put("orgId", orgEle.getFdId());
			orgInfo.put("orgHId", orgEle.getFdHierarchyId());
			idList.add(orgInfo);

		}
		return cateMap;
	}


	/**
	 * 用户组分割
	 */
	private List getUserGroup(List users) {
		int maxCount = 1000;
		List<List> groupLists = new ArrayList<List>();
		if (users.size() <= maxCount) {
			groupLists.add(users);
		} else {
			groupLists = AttendUtil.splitList(users, maxCount);
		}
		return groupLists;
	}

	/**
	 * 过滤某班次的打卡记录
	 *
	 * @param signedRecordList
	 * @param fdWorkId
	 * @param fdWorkType
	 * @return
	 * @throws Exception
	 */
	private JSONObject getSignRecord(List<JSONObject> signedRecordList,
									 Map workTime) throws Exception {
		if (signedRecordList == null || signedRecordList.isEmpty()) {
			return null;
		}
		List<JSONObject> signedList = new ArrayList<JSONObject>();
		Map<String, JSONObject> statusMap = new HashMap<String, JSONObject>();
		for (JSONObject json : signedRecordList) {
			if (sysAttendCategoryService.isSameWorkTime(workTime,
					(String) json.get("fdWorkId"),
					(Integer) json.get("fdWorkType"),
					(String) json.get("fdWorkKey"))) {
				signedList.add(json);
				Number fdStatus = (Number) json.get("fdStatus");
				if (fdStatus != null) {
					statusMap.put(fdStatus.toString(), json);
				}
			}
		}
		if (!signedList.isEmpty()) {
			// 同班次存在多条记录时,以状态不正常的优先
			if (statusMap.containsKey("0")) {
				return statusMap.get("0");
			} else if (statusMap.containsKey("2")) {
				return statusMap.get("2");
			} else if (statusMap.containsKey("3")) {
				return statusMap.get("3");
			} else {
				return signedList.get(0);
			}
		}
		return null;
	}

	private void putAttendMain(JSONObject record, Map workTime,
							   List<JSONObject> mainList , SysAttendCategory category) {
		if (record == null) {
			return;
		}
		Object _nextSignTime = (Object) workTime.get("nextSignTime");
		if (_nextSignTime != null
				&& StringUtil.isNotNull(_nextSignTime.toString())) {
			Date nextSignTime = (Date) _nextSignTime;
			Long createTime = record.getLong("docCreateTime");
			Date docCreateTime = new Date(createTime);
			nextSignTime = AttendUtil.joinYMDandHMS(docCreateTime,
					nextSignTime);
			//是否跨天排班处理
			Integer fdOverTimeType = (Integer) workTime.get("nextOverTimeType");
			if(fdOverTimeType == 2)
			{
				nextSignTime = AttendUtil.addDate(nextSignTime, 1);
			}
			if (!docCreateTime.before(nextSignTime)) {
				return;
			}
		}
		Number fdStatus = (Number) record.get("fdStatus");
		addAttendMains(record, fdStatus.intValue(), null, workTime, mainList,category);
	}

	/**
	 * 获取对应的打卡数据
	 * @param workTimeConfiguration 上班的班次信息
	 * @param userSignedWorkedRecords 	考勤数据
	 * @return 上班打卡记录
	 */
	private JSONObject getUserAttendMain(Map workTimeConfiguration, List<JSONObject> userSignedWorkedRecords) {
		//班次ID
		String fdWorkId = (String) workTimeConfiguration.get("fdWorkTimeId");
		//班次类型
		Integer fdWorkType = (Integer) workTimeConfiguration.get("fdWorkType");
		for (JSONObject json : userSignedWorkedRecords) {
			//取打卡时间在当日考勤范围内的
			//打卡记录中的班次
			String fdWorkKey = json.containsKey("fdWorkKey") ? json.getString("fdWorkKey") : "";
			String _fdWorkId = json.getString("fdWorkId");
			_fdWorkId = StringUtil.isNotNull(fdWorkKey) ? fdWorkKey : _fdWorkId;
			//打卡记录中属于上班班次的记录，并且是同一个班次。
			if (_fdWorkId.equals(fdWorkId) && json.getInt("fdWorkType") == fdWorkType) {
				return json;
			}
		}
		return null;
	}

	private Long getAttendMainCreateTime(Map workTime,
										 List<JSONObject> mainList) {
		JSONObject json = getUserAttendMain(workTime, mainList);
		if (json == null) {
			return null;
		}
		Long docCreateTime = json.getLong("docCreateTime");
		return docCreateTime;
	}

	/**
	 * 	获取对应打卡时间
	 * @param workTimeConfiguration 上下班时间配置
	 * @param userSignedWorkedRecords 考勤数据
	 * @return
	 */
	private Date getAttendMainSignTime(Map workTimeConfiguration,List<JSONObject> userSignedWorkedRecords) {
		JSONObject result = getUserAttendMain(workTimeConfiguration, userSignedWorkedRecords);
		if (result == null) {
			return null;
		}
		//打卡时间
		Long docCreateTime = result.getLong("docCreateTime");
		if (docCreateTime != null) {
			return new Date(docCreateTime);
		}
		return null;
	}

	/**
	 * 获取对应打卡状态
	 *
	 * @param workTime
	 * @param mainList
	 * @return
	 */
	private Integer getAttendMainStatus(Map workTime,
										List<JSONObject> mainList) {
		JSONObject json = getUserAttendMain(workTime, mainList);
		if (json == null) {
			return null;
		}
		Number fdStatus = (Number) json.get("fdStatus");
		if (fdStatus != null) {
			return fdStatus.intValue();
		}
		return null;
	}

	/**
	 * 	创建/合并更新打卡新记录
	 * @param oldReocrd
	 *            旧打卡记录
	 * @param fdStatus
	 *            考勤状态
	 * @param dingRecord
	 *            钉钉打卡数据(若为null,则打卡时间使用旧记录打卡时间)
	 * @param workTime
	 * @param mainList
	 */
	private void addAttendMains(JSONObject oldReocrd, Integer fdStatus,
								JSONObject dingRecord, Map workTime, List<JSONObject> mainList, SysAttendCategory category) {
		if ((oldReocrd == null && dingRecord == null) || workTime == null || mainList == null){
			return;
		}

		String fdId = "";
		Date docCreateTime = null;
		String fdSourceType = null;
		String fdLocation = null;
		Boolean fdOutside = false;
		String fdAppName = null;
		String fdWifiName = null;
		String fdUserMacAddr = null;
		String fdOutsideRemark=null;
		Integer fdState = null;
		//原始打卡记录 存在，并且状态值未指定
		if (oldReocrd != null ) {
			fdId = oldReocrd.getString("fdId");
			Long createTime = oldReocrd.getLong("docCreateTime");
			docCreateTime = new Date(createTime);
			fdSourceType = (String) oldReocrd.get("fdSourceType");
			fdLocation = (String) oldReocrd.get("fdLocation");
			fdAppName = (String) oldReocrd.get("fdAppName");
			fdWifiName = (String) oldReocrd.get("fdWifiName");
			fdUserMacAddr = (String) oldReocrd.get("fdUserMacAddr");
			fdOutsideRemark=(String) oldReocrd.get("fdOutsideRemark");
			fdState = oldReocrd.get("fdState") == null ? null
					: (Integer) oldReocrd.get("fdState");
			int status = getSignedStatus(oldReocrd);
			//异常处理或者流程状态。
			if (AttendUtil.isAttendBuss(status + "")
					|| Integer.valueOf(2).equals(fdState)) {
				fdStatus = status;
			}
		}

		String fdWorkId = (String) workTime.get("fdWorkTimeId");
		Integer fdWorkType = (Integer) workTime.get("fdWorkType");
		String fdCategoryId = (String) workTime.get("categoryId");
		String isTimeArea = (String) workTime.get("isTimeArea");
		//标准的打卡时间
		Date fdBaseWorkTime = (Date) workTime.get("signTime");
		//是否跨天打卡
		Integer fdOverTimeType = (Integer) workTime.get("overTimeType");
		// 是否支持跨天
		boolean isAcrossDay = (Boolean) workTime.get("isAcrossDay");
		long workDate = (Long) workTime.get("fdWorkDate");
		Date fdWorkDate = new Date(workDate);
		boolean fdIsAcross = false;
		// 钉钉传入数据
		if (dingRecord != null) {
			docCreateTime = new Date(
					dingRecord.getLong("fdUserCheckTime"));
			fdSourceType = (String) dingRecord.get("fdSourceType");
			fdLocation = (String) dingRecord.get("fdUserAddress");
			// Normal：范围内,Outside：范围外
			String fdLocationResult = (String) dingRecord
					.get("fdLocationResult");
			fdOutside = "Outside".equals(fdLocationResult);
			if(dingRecord.has("fdAppName")){
				fdAppName =dingRecord.getString("fdAppName");
			}else{
				fdAppName = appKey;
			}
			fdWifiName = (String) dingRecord.get("fdWifiName");
			fdUserMacAddr = (String) dingRecord.get("fdUserMacAddr");
			fdOutsideRemark=(String) dingRecord.get("fdOutsideRemark");
		}
		if (isAcrossDay) {
			if (!docCreateTime.before(AttendUtil.getDate(fdWorkDate, 1))) {
				fdIsAcross = true;
			}
		}
		//跨天排班，基准时间加一天
		if (AttendConstant.FD_OVERTIME_TYPE[2].equals(fdOverTimeType)  && Integer.valueOf(1).equals(fdWorkType)) {
			fdWorkDate=AttendUtil.getDate(fdWorkDate, 1);
		}
		fdBaseWorkTime = AttendUtil.joinYMDandHMS(fdWorkDate, fdBaseWorkTime);

		fdLocation = StringUtil.isNotNull(fdLocation) ? fdLocation : "";
		fdSourceType = StringUtil.isNotNull(fdSourceType) ? fdSourceType : "";
		fdAppName = StringUtil.isNotNull(fdAppName) ? fdAppName : "";
		fdWifiName = StringUtil.isNotNull(fdWifiName) ? fdWifiName : "";
		fdUserMacAddr = StringUtil.isNotNull(fdUserMacAddr) ? fdUserMacAddr : "";
		fdOutsideRemark = StringUtil.isNotNull(fdOutsideRemark) ? fdOutsideRemark : "";

		JSONObject main = new JSONObject();
		if (StringUtil.isNotNull(fdId)) {
			main.put("fdId", fdId);
		}
		//不定时工时 打卡状态为正常
		if(category !=null && category.getFdShiftType() == 4){
			fdStatus = 1;
		}
		main.put("fdStatus", fdStatus);
		main.put("docCreateTime", docCreateTime.getTime());
		main.put("fdWorkId", "true".equals(isTimeArea) ? "" : fdWorkId);
		main.put("fdWorkKey", "true".equals(isTimeArea) ? fdWorkId : "");
		main.put("fdWorkType", fdWorkType);
		main.put("fdCategoryId", fdCategoryId);
		main.put("fdLocation", fdLocation);
		main.put("fdSourceType", fdSourceType);
		main.put("fdOutside", fdOutside);
		main.put("fdBaseWorkTime", fdBaseWorkTime.getTime());
		main.put("fdIsAcross", fdIsAcross);
		main.put("fdAppName", fdAppName);
		main.put("fdWifiName", fdWifiName);
		main.put("fdUserMacAddr", fdUserMacAddr);
		main.put("fdOutsideRemark", fdOutsideRemark);
		main.put("overTimeType", fdOverTimeType);
		main.put("fdState", fdState);

		// 相同班次数据过滤
		for (JSONObject json : mainList) {
			Date _fdBaseWorkTime = new Date(json.getLong("fdBaseWorkTime"));
			String _fdWorkId = json.getString("fdWorkId");
			String _fdWorkKey = json.containsKey("fdWorkKey")
					? json.getString("fdWorkKey") : "";
			_fdWorkId = StringUtil.isNotNull(_fdWorkKey) ? _fdWorkKey
					: _fdWorkId;
			int _fdStatus = getSignedStatus(json);
			//相同班次。相同的标准打卡时间
			if (_fdBaseWorkTime.equals(fdBaseWorkTime) && _fdWorkId.equals(fdWorkId)
					&& json.getInt("fdWorkType") == fdWorkType) {

				Date createTime = new Date(json.getLong("docCreateTime"));
				//打卡时间
				if (fdWorkType == 0) {
					if (docCreateTime.after(createTime) && _fdStatus > 0) {
						if (AttendUtil.isAttendBuss(_fdStatus + "")
								&& createTime.equals(fdBaseWorkTime)) {
							// 出差/请假/外出(第一次要同步数据)
							mainList.remove(json);
						} else {
							main = null;
						}
					} else {
						mainList.remove(json);
					}
				}
				if (fdWorkType == 1) {
					if (docCreateTime.before(createTime) && _fdStatus > 0) {
						if (AttendUtil.isAttendBuss(_fdStatus + "")
								&& createTime.equals(fdBaseWorkTime)) {
							// 出差/请假/外出
							mainList.remove(json);
						} else {
							main = null;
						}
					}else {
						mainList.remove(json);
					}
				}
				break;
			}
		}
		if (main != null) {
			mainList.add(main);
		}

	}

	/**
	 * 	正常下班打卡时间
	 * @param pSignTime 同个班次上班用户打卡时间
	 * @param map  班次相关信息
	 * @return
	 */
	private int getShouldOffWorkTime(Date pSignTime, Map map) {
		int shouldSignTime = sysAttendCategoryService.getShouldOffWorkTime(pSignTime, map);
		return shouldSignTime;
	}

	/**
	 * 	正常上班最晚打卡时间
	 * @param workingTimeConfiguration 	上班时间配置
	 * @return 根据配置返回标准上班打卡时间的，最晚分钟数。
	 */
	private int getShouldOnWorkTime(Map workingTimeConfiguration) {
		int shouldSignTime = sysAttendCategoryService.getShouldOnWorkTime(workingTimeConfiguration);
		return shouldSignTime;
	}
	/**
	 * 保存有效考勤记录，数据来源为：webservice、考勤excel导入、app数据同步
	 * 方法之所有写在这里是因为代码一直在这里，本次只是统一了入口，没有做代码迁移的必要
	 * @param userStatusData 用户考勤同步记录
	 * @param appName 应用来源
	 * @param userList 用户列表
	 * @param date 考勤记录日期
	 * @param alterRecord 无效记录说明 eg.webservice接口同步置为无效打卡记录
	 * @return: void
	 * @author: wangjf
	 * @time: 2022/6/20 4:26 下午
	 */
	@Override
	public void addAttendMainBatch(Map<String, List<JSONObject>> userStatusData,String appName, List userList, Date date, String alterRecord)throws Exception{
		this.addBatch(userStatusData, userList, date, appName, alterRecord);
	}
	/**
	 * 保存有效考勤记录
	 * @param userStatusData
	 * @param userList
	 * @param date
	 * @return: void
	 */
	private void addBatch(Map<String, List<JSONObject>> userStatusData, List userList, Date date) throws Exception {
		this.addBatch(userStatusData, userList,date, this.appName,this.appName+"同步任务置为无效打卡记录");
	}
	/**
	 * 保存有效考勤记录
	 * @param userStatusData
	 * @param userList
	 * @param date
	 * @param appName
	 * @param alterRecord
	 * @return: void
	 */
	private void addBatch(Map<String, List<JSONObject>> userStatusData, List userList, Date date, String appName, String alterRecord) throws Exception {
		if (userStatusData.isEmpty()) {
			logger.warn("用户" + appName + "考勤同步记录为空:" + userList);
			return;
		}

		Set<String> userIdSet = userStatusData.keySet();
		List<String> orgIdList = new ArrayList<String>(userIdSet);
		Map<String, String> areaMap = SysTimeUtil.getUserAuthAreaMap(orgIdList);

		DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
		Connection conn = null;
		PreparedStatement insert = null;
		PreparedStatement update = null;
		PreparedStatement delete = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			// 获取用户已打卡记录fdid,且无效的记录
			List<String> userIdList = this.getUserAttendMainOfDel(conn, date, userStatusData);
			if (!userIdList.isEmpty()) {
				delete = conn.prepareStatement("update sys_attend_main set doc_status=1,doc_alter_time=?,fd_alter_record='" + alterRecord + "' where "
										+ HQLUtil.buildLogicIN("fd_id", userIdList));
				delete.setTimestamp(1, new Timestamp(new Date().getTime()));
				delete.addBatch();
				delete.executeBatch();
			}

			update = conn.prepareStatement(
							"update sys_attend_main set fd_status=?,doc_create_time=?,fd_location=?,fd_outside=?,"
									+ "doc_alter_time=?,fd_app_name=?,fd_work_id=?,fd_work_type=?,fd_work_key=?,fd_source_type=?,fd_category_his_id=?,fd_date_type=?,fd_base_work_time=?,fd_is_across=?,"
									+ "fd_business_id=?,fd_off_type=?,fd_wifi_name=?,fd_wifi_macIp=?,fd_desc=? "
									+ "where fd_id =?");
				insert = conn.prepareStatement(
							"insert into sys_attend_main(fd_id,fd_status,doc_create_time,fd_location,fd_work_type,fd_outside,fd_category_his_id,"
									+ "doc_creator_id,fd_work_id,fd_date_type,doc_creator_hid,fd_app_name,doc_status,fd_source_type,fd_work_key,fd_base_work_time,fd_is_across,"
									+ "fd_business_id,fd_off_type,auth_area_id,fd_wifi_name,fd_wifi_macIp,fd_desc) "
									+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			//插入和更新标记，用于分段提交
			boolean isInsert = false, isUpdate = false;
			// 分段提交
			int loop = 0 ;
			for (String key : userStatusData.keySet()) {
				List<JSONObject> records = userStatusData.get(key);
				for (JSONObject record : records) {

					//加入批量提交 ，每300条提交一次，项目上进行了验证，发生死锁的概率大大降低
					if (loop > 0 && (loop % 300 == 0)) {
						if (isUpdate) {
							update.executeBatch();
						}
						if (isInsert) {
							insert.executeBatch();
						}
						conn.commit();
						isUpdate = false;
						isInsert = false;
					}
					loop++;
					String fdId = record.containsKey("fdId") ? record.getString("fdId") : "";
					Integer fdStatus = record.getInt("fdStatus");
					Date docCreateTime = new Date(record.getLong("docCreateTime"));
					String fdCategoryId = record.getString("fdCategoryId");
					String fdWorkId = record.getString("fdWorkId");
					Integer fdWorkType = record.getInt("fdWorkType");
					String fdLocation = record.getString("fdLocation");
					String fdSourceType = record.containsKey("fdSourceType") ? record.getString("fdSourceType") : null;
					String fdWorkKey = record.containsKey("fdWorkKey") ? record.getString("fdWorkKey") : "";
					String fdDateType = (String) record.get("dateType");
					//判断是否是工作日，优先使用节假日判断
					fdDateType = AttendConstant.FD_DATE_TYPE[1].equals(fdDateType) || AttendConstant.FD_DATE_TYPE[2].equals(fdDateType) ? fdDateType : AttendConstant.FD_DATE_TYPE[0];
					Boolean fdOutside = record.containsKey("fdOutside") ? (Boolean)record.get("fdOutside") : false;
					Date fdBaseWorkTime = new Date(record.getLong("fdBaseWorkTime"));
					Boolean fdIsAcross = (Boolean) record.get("fdIsAcross");
					String fdAppName = record.getString("fdAppName");
					if ("__".equals(fdAppName) || StringUtil.isNull(fdAppName)) {
						fdAppName = appName;
					}
					Integer fdOffType = (Integer) record.get("fdOffType");
					String fdBusId = (String) record.get("fdBusId");
					String fdWifiName = record.containsKey("fdWifiName") ? record.getString("fdWifiName") : null;
					String fdUserMacAddr = record.containsKey("fdUserMacAddr") ? record.getString("fdUserMacAddr") : null;
					String fdOutsideRemark = record.containsKey("fdOutsideRemark") ? record.getString("fdOutsideRemark") : null;

					if (StringUtil.isNotNull(fdId)) {
						update.setInt(1, fdStatus);
						update.setTimestamp(2, new Timestamp(docCreateTime.getTime()));
						update.setString(3, fdLocation);
						update.setBoolean(4, Boolean.TRUE.equals(fdOutside));
						update.setTimestamp(5, new Timestamp(new Date().getTime()));
						update.setString(6, fdAppName);
						update.setString(7, StringUtil.isNotNull(fdWorkKey) ? null : fdWorkId);
						update.setInt(8, fdWorkType);
						update.setString(9, StringUtil.isNotNull(fdWorkKey) ? fdWorkKey : null);
						update.setString(10, fdSourceType);
						update.setString(11, fdCategoryId);
						update.setInt(12, Integer.valueOf(fdDateType));
						update.setTimestamp(13, new Timestamp(fdBaseWorkTime.getTime()));
						update.setBoolean(14, fdIsAcross);
						update.setString(15, fdBusId);
						update.setObject(16, fdOffType);
						update.setString(17, fdWifiName);
						update.setString(18, fdUserMacAddr);
						update.setString(19, fdOutsideRemark);
						update.setString(20, fdId);
						update.addBatch();
						isUpdate = true;
					} else {
						fdId = IDGenerator.generateID();
						insert.setString(1, fdId);
						insert.setInt(2, fdStatus);
						insert.setTimestamp(3, new Timestamp(docCreateTime.getTime()));
						insert.setString(4, fdLocation);
						insert.setInt(5, fdWorkType);
						insert.setBoolean(6, Boolean.TRUE.equals(fdOutside));
						insert.setString(7, fdCategoryId);
						insert.setString(8, key);
						insert.setString(9, StringUtil.isNotNull(fdWorkKey) ? null : fdWorkId);
						insert.setInt(10, Integer.valueOf(fdDateType));
						insert.setString(11, getUserHId(key, userList));
						insert.setString(12, fdAppName);
						insert.setInt(13, 0);
						insert.setString(14, fdSourceType);
						insert.setString(15, StringUtil.isNotNull(fdWorkKey) ? fdWorkKey : null);
						insert.setTimestamp(16, new Timestamp(fdBaseWorkTime.getTime()));
						insert.setBoolean(17, fdIsAcross);
						insert.setString(18, fdBusId);
						insert.setObject(19, fdOffType);
//						insert.setString(20, areaMap.get(key));
						insert.setString(20, "efg");
						insert.setString(21, fdWifiName);
						insert.setString(22, fdUserMacAddr);
						insert.setString(23, fdOutsideRemark);
						insert.addBatch();
						isInsert = true;
					}
				}
			}
			if (isUpdate) {
				update.executeBatch();
			}
			if (isInsert) {
				insert.executeBatch();
			}
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage(), ex);
			conn.rollback();
			throw ex;
		} finally {
			JdbcUtils.closeStatement(update);
			JdbcUtils.closeStatement(insert);
			JdbcUtils.closeStatement(delete);
			JdbcUtils.closeConnection(conn);
		}

	}

	/**
	 * 获取用户打卡记录中无效记录id
	 *
	 * @param conn
	 * @param workDate
	 * @param userMaps
	 * @return
	 * @throws Exception
	 */
	public List getUserAttendMainOfDel(Connection conn, Date workDate,
									   Map<String, List<JSONObject>> userMaps) throws Exception {
		Set<String> userIds = userMaps.keySet();
		List<String> userIdList = new ArrayList<String>(userIds);

		String where = HQLUtil.buildLogicIN("doc_creator_id", userIdList)
				+ " and (doc_status=0 or doc_status is null) and fd_work_type in(0,1)"
				+ " and (doc_create_time>=? and doc_create_time<? and (fd_is_across is null or fd_is_across=?) "
				+ " or fd_is_across=? and doc_create_time>=? and doc_create_time<?)";
		String orgSql = "select fd_id from sys_attend_main where "
				+ where;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<String> fdIdList = new ArrayList<String>();
		try {
			statement = conn.prepareStatement(orgSql);
			statement.setTimestamp(1,
					new Timestamp(AttendUtil.getDate(workDate, 0).getTime()));
			statement.setTimestamp(2,
					new Timestamp(AttendUtil.getDate(workDate, 1).getTime()));
			statement.setBoolean(3, false);
			statement.setBoolean(4, true);
			statement.setTimestamp(5,
					new Timestamp(AttendUtil.getDate(workDate, 1).getTime()));
			statement.setTimestamp(6,
					new Timestamp(AttendUtil.getDate(workDate, 2).getTime()));
			rs = statement.executeQuery();
			while (rs.next()) {
				fdIdList.add(rs.getString(1));
			}
			// 获取无效记录id
			for (String key : userMaps.keySet()) {
				List<JSONObject> records = userMaps.get(key);
				for (JSONObject record : records) {
					String fdId = record.containsKey("fdId")
							? record.getString("fdId") : "";
					if (StringUtil.isNull(fdId)) {
						continue;
					}
					if (fdIdList.contains(fdId)) {
						fdIdList.remove(fdId);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(statement);
		}

		return fdIdList;
	}

	/**
	 * 获取用户层级Id
	 *
	 * @param userId
	 * @param userList
	 * @return
	 */
	private String getUserHId(String userId, List userList) {
		for (int i = 0; i < userList.size(); i++) {
			Map<String, String> m = (Map<String, String>) userList.get(i);
			if (userId.equals(m.get("orgId").toString())) {
				return (String) m.get("orgHId");
			}
		}
		return "";
	}

	private int getSignedStatus(JSONObject record) {
		Integer fdStatus = (Integer) record.get("fdStatus");
		Integer fdState = (Integer) record.get("fdState");
		if (fdStatus != null) {
			return fdStatus.intValue();
		}
		return 0;
	}

	private Date getSignedTime(JSONObject record) {
		if (record == null) {
			return null;
		}
		Date createTime = new Date(record.getLong("docCreateTime"));
		return createTime;
	}

	/**
	 * 对打卡记录按天分组
	 *
	 * @param arrays
	 * @return
	 */
	private List<List<SysAttendSynDing>> groupByDate(List<SysAttendSynDing> list) {
		List<List<SysAttendSynDing>> resultList = new ArrayList<List<SysAttendSynDing>>();
		Map<String, List<SysAttendSynDing>> datas = new HashMap<String, List<SysAttendSynDing>>();
		for (SysAttendSynDing sysAttendSynDing : list) {
			Date dateTime = sysAttendSynDing.getFdUserCheckTime();;
			String createDate = DateUtil.convertDateToString(dateTime, DateUtil.TYPE_DATE, null);
			if (!datas.containsKey(createDate)) {
				datas.put(createDate, new ArrayList<SysAttendSynDing>());
			}
			List<SysAttendSynDing> records = datas.get(createDate);
			records.add(sysAttendSynDing);
		}
		for (String date : datas.keySet()) {
			List<SysAttendSynDing> tmpList = datas.get(date);
			if (tmpList != null && !tmpList.isEmpty()) {
				resultList.add(tmpList);
			}
		}
		return resultList;
	}

	class PersonClockInfoRunner implements Runnable {
		private final List<String> userIds;
		private final Date fdStartTime;
		private final Date fdEndTime;
		private final Date fdSyncTime;
		private final Map map;

		public PersonClockInfoRunner(List<String> userIds, Date fdStartTime,
									 Date fdEndTime, Date fdSyncTime, Map map) {
			this.userIds = userIds;
			this.fdStartTime = fdStartTime;
			this.fdEndTime = fdEndTime;
			this.fdSyncTime = fdSyncTime;
			this.map = map;
		}

		@Override
		public void run() {
			try {
				//每次请求暂停0.2秒，防止调用钉钉接口过于频繁每秒1000次
				//modify by wangjf本方法存在问题，因为已经是多线程了，所以当前线程休眠200毫秒是没意义的，故限速放在线程调用之前执行
				//Thread.sleep(200);
				handleClockInfo(userIds, fdStartTime, fdEndTime, fdSyncTime,map);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("部分用户" + appName + "考勤记录同步线程运行报错,用户:" + userIds, e);
			} finally {
				countDownLatch.countDown();
			}
		}
	}

	private List<Date[]> splitDateByDay(Date startDate, Date endDate,
										Date fdSyncTime, int frequency) throws Exception {
		Calendar caleEnd = Calendar.getInstance();
		caleEnd.setTime(endDate);
		// 中间变量
		Calendar caleMid = Calendar.getInstance();
		caleMid.setTime(startDate);
		List<Date[]> dateList = new ArrayList<Date[]>();
		while (caleMid.before(caleEnd) || caleMid.equals(caleEnd)) {
			Date[] dates = new Date[2];
			Date midStartDate = caleMid.getTime();
			caleMid.add(Calendar.DATE,frequency - 1);
			dates[0] = midStartDate;
			dates[1] = endDate.compareTo(caleMid.getTime()) <= 0 ? endDate : caleMid.getTime();
			dateList.add(dates);
			caleMid.add(Calendar.DATE,1);
		}
		return dateList;
	}

	/**
	 * 	创建时间区间数组，用于按天处理打卡数据
	 * @param startDate
	 * 	开始时间
	 * @param endDate
	 * 	结束时间
	 * @return
	 * @throws Exception
	 */
	private List<Date> splitByDate(Date startDate, Date endDate)
			throws Exception {
		Date startTime = AttendUtil.getDate(startDate, 0);
		Date endTime = AttendUtil.getDate(endDate, 0);
		List<Date> dateList = new ArrayList<Date>();
		if (startTime.equals(endTime)) {
			dateList.add(startTime);
			return dateList;
		}
		while (startTime != null) {
			dateList.add(startTime);
			startTime = AttendUtil.getDate(startTime, 1);
			if (startTime.after(endTime)) {
				startTime = null;
			}
		}
		return dateList;
	}

	/**
	 * 	从钉钉、微信同步的原始数据中获取用户ids
	 * @param beginTime
	 * 	开始时间
	 * @param endTime
	 * 	结束时间
	 * @return
	 * @throws Exception
	 */
	public List getAttendSynDingUser(Date beginTime, Date endTime)
			throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		String orgSql = "select DISTINCT fd_person_id from sys_attend_syn_ding where fd_work_date >=? and fd_work_date<=? and fd_ding_id is not null";
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<String> orgList = new ArrayList<String>();
		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(orgSql);
			statement.setTimestamp(1, new Timestamp(beginTime.getTime()));
			statement.setTimestamp(2, new Timestamp(endTime.getTime()));
			rs = statement.executeQuery();
			while (rs.next()) {
				if(StringUtil.isNotNull(rs.getString(1))) {
					orgList.add(rs.getString(1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(conn);
		}
		return orgList;
	}


	/**
	 * 	获取用户某天钉钉打卡原始记录
	 *
	 * @param orgIds
	 * 	用户Ids
	 * @param workedDate
	 * 	某天
	 * @return Map
	 * 	钉钉、微信原始打卡数据
	 * @throws Exception
	 */
	private Map getAttendDingSignedList(List<String> orgIds, Date workedDate,String typeName)
			throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");

		StringBuilder signedSql =new StringBuilder("select fd_work_date,fd_user_id,fd_check_type,fd_source_type,fd_time_result,fd_user_address,fd_base_check_time,"
				+ "fd_user_check_time,fd_person_id,fd_location_result,fd_invalid_record_type,fd_id,fd_wifi_name,fd_user_mac_addr,fd_outside_remark,fd_app_name from sys_attend_syn_ding where "
				+  " fd_user_check_time>=? and fd_user_check_time<? ");
		//排除 请假\出差\外出流程 的原始记录，或者是打卡的来源非空
		if("exchange".equals(typeName)) {
			signedSql.append(" and (fd_time_result not in ('Trip','Leave','Outgoing') or fd_time_result is null )");
		} else if("resetAll".equals(typeName)) {
			signedSql.append(" and fd_app_name is not null ");
		} else {
			signedSql.append(" and fd_ding_id is not null ");
		}
		Map<String, List<JSONObject>> records = new HashMap<String, List<JSONObject>>();
		if(CollectionUtils.isNotEmpty(orgIds)) {
			signedSql.append(" and ").append(HQLUtil.buildLogicIN("fd_person_id", orgIds));
		}else{
			//没有人员的情况下，不查询，避免数据量过大，系统宕机
			return records;
		}
		signedSql.append("  order by fd_user_check_time asc ");
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		List<String> invalidUserList = new ArrayList<String>();
		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(signedSql.toString());
			statement.setTimestamp(1, new Timestamp(AttendUtil.getDate(workedDate, 0).getTime()));
			statement.setTimestamp(2, new Timestamp(AttendUtil.getDate(workedDate, 2).getTime()));
			rs = statement.executeQuery();
			while (rs.next()) {
				JSONObject ret = new JSONObject();
				Timestamp fdWorkDate = rs.getTimestamp(1);
				ret.put("fdWorkDate",
						fdWorkDate != null ? fdWorkDate.getTime() : 0);
				ret.put("fdUserId", rs.getString(2));
				ret.put("fdCheckType", rs.getString(3));
				ret.put("fdSourceType", rs.getString(4));
				ret.put("fdTimeResult", rs.getString(5));
				ret.put("fdUserAddress", rs.getString(6));
				Timestamp fdBaseCheckTime = rs.getTimestamp(7);
				Timestamp fdUserCheckTime = rs.getTimestamp(8);
				ret.put("fdBaseCheckTime",
						fdBaseCheckTime != null ? fdBaseCheckTime.getTime()
								: 0);
				ret.put("fdUserCheckTime",
						fdUserCheckTime != null ? fdUserCheckTime.getTime()
								: 0);
				String fdPersonId = rs.getString(9);
				ret.put("fdPersonId", fdPersonId);
				ret.put("fdLocationResult", rs.getString(10));
				if (!records.containsKey(fdPersonId)) {
					records.put(fdPersonId, new ArrayList<JSONObject>());
				}
				// 打卡记录是否有效
				String fdInvalidRecordType = rs.getString(11);
				if ("Security".equals(fdInvalidRecordType)
						|| "Other".equals(fdInvalidRecordType)) {
					invalidUserList.add(fdPersonId);
					continue;
				}
				ret.put("fdWifiName", rs.getString(13));
				ret.put("fdUserMacAddr", rs.getString(14));
				ret.put("fdOutsideRemark", rs.getString(15));
				ret.put("fdAppName", rs.getString(16));
				List<JSONObject> recordList = records.get(fdPersonId);
				recordList.add(ret);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(conn);
		}
		if (!invalidUserList.isEmpty()) {
			logger.warn("" + appName + "考勤打卡记录存在安全问题导致打卡数据无效,包括如下用户ID:" + invalidUserList);
		}
		return records;
	}

	private void notifyRemindInvalidRecord(
			SysAttendSynDingNotify sysAttendSynDingNotify,
			SysAttendSynDing sysAttendSynDing)
			throws Exception {
		if (sysAttendSynDingNotify == null || sysAttendSynDing == null) {
			logger.debug("发送不合法考勤记录提示:没有不合法考勤记录");
			return;
		}
		try{
			NotifyContext notifyContext = getSysNotifyMainCoreService().getContext(null);
			// 设置通知方式
			notifyContext.setNotifyType("todo");
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
			List<SysOrgElement> targets = new ArrayList<>();
			targets.add(sysAttendSynDingNotify.getFdReceiver());
			// 设置发布通知人
			notifyContext
					.setNotifyTarget(targets);
			notifyContext.setSubject(sysAttendSynDingNotify.getDocSubject());
			notifyContext.setContent(sysAttendSynDingNotify.getDocSubject());
			notifyContext.setLink(sysAttendSynDingNotify.getFdLink());

			getSysNotifyMainCoreService().sendNotify(sysAttendSynDing,
					notifyContext,
					null);
			logger.debug("发送不合法考勤记录提示:" + sysAttendSynDingNotify.getDocSubject()
					+ "，用户："
					+ sysAttendSynDing.getFdPersonId()
					+ ",考勤记录id:"
					+ sysAttendSynDing.getFdId());
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取用户某天原始打卡记录dingid
	 *
	 * @param orgIds
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	private List<String> getAttendDingIds(List<String> orgIds, Date beginTime,
										  Date endTime)
			throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		String orgSql = "select DISTINCT fd_ding_id from sys_attend_syn_ding where fd_user_check_time >=? and fd_user_check_time<=? and fd_ding_id is not null and "
				+ HQLUtil.buildLogicIN("fd_person_id", orgIds);
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<String> dingIdList = new ArrayList<String>();
		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(orgSql);
			statement.setTimestamp(1, new Timestamp(beginTime.getTime()));
			statement.setTimestamp(2, new Timestamp(endTime.getTime()));
			rs = statement.executeQuery();
			while (rs.next()) {
				dingIdList.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(statement);
			JdbcUtils.closeConnection(conn);
		}

		return dingIdList;
	}

	/**
	 * 排除已存在的考勤记录
	 *
	 * @param result
	 * @param fdStartTime
	 * @param fdEndTime
	 * @param fdSyncTime
	 * @param map
	 * @return
	 * @throws Exception
	 */
	private List<SysAttendSynDing> removeExistsAttendDing(JSONArray result,
														  Date fdStartTime,
														  Date fdEndTime, Date fdSyncTime, Map map) throws Exception {
		List<String> ekpUserList = new ArrayList<String>();
		List<SysAttendSynDing> recordList=new ArrayList<SysAttendSynDing>();
		if (appKey.equals(AttendConstant.SYS_ATTEND_MAIN_FDAPPNAME)) {
			recordList = getDingClockList(result, map,ekpUserList);
		}
		else{
			recordList = getWeChatClockList(result, map,ekpUserList);
		}

		if (recordList.isEmpty() || ekpUserList.isEmpty()) {
			logger.debug("部分用户" + appName + "考勤记录同步提示:没有考勤记录,用户:" + ekpUserList.toString()
					+ ";同步时间区间:" + fdStartTime + "~" + fdEndTime
					+ ";fdSyncTime:" + fdSyncTime);
			return new ArrayList<SysAttendSynDing>();
		}
		List<SysAttendSynDing> dingRecordList = new ArrayList<SysAttendSynDing>();
		List<String> dingIds = getAttendDingIds(ekpUserList, fdStartTime,
				fdEndTime);
		Map<String,String> userCategoryCacheMap=new HashMap<>();
		// 排除已存在数据
		for (SysAttendSynDing record : recordList) {
			if (dingIds.contains(record.getFdDingId())) {
				continue;
			}
			String fdId = IDGenerator.generateID();
			record.setFdId(fdId);
			//人、和日期在这方法中缓存起来，避免一个人多次打卡查询多次
			String key=String.format("%s_%s",record.getFdPersonId(),AttendUtil.getDate(record.getFdUserCheckTime(),0));
			String categoryId =userCategoryCacheMap.get(key);
			if(StringUtil.isNull(categoryId)) {
				SysOrgElement element = sysOrgCoreService.findByPrimaryKey(record.getFdPersonId());
				//记录考勤组的ID
				categoryId = sysAttendCategoryService.getAttendCategory(element, record.getFdUserCheckTime());
				userCategoryCacheMap.put(key,categoryId);
			}
			record.setFdGroupId(categoryId);
			dingRecordList.add(record);
		}
		userCategoryCacheMap.clear();

		SysTimeUtil.initUserAuthAreaMap(ekpUserList);
		if (dingRecordList.isEmpty()) {
			logger.debug("部分用户" + appName + "考勤记录同步提示:考勤记录已同步,用户:" + ekpUserList.toString()
					+ ";同步时间区间:" + fdStartTime + "~" + fdEndTime
					+ ";fdSyncTime:" + fdSyncTime);
			return new ArrayList<SysAttendSynDing>();
		}
		return dingRecordList;
	}

	@Override
	public void sendInvalidRecordNotify(SysQuartzJobContext context)
			throws Exception {
		String whereblock="fdStatus=0";
		List<SysAttendSynDingNotify> recordList = getSysAttendSynDingNotifyService()
				.findList(whereblock, null);
		if(recordList!=null){
			for (SysAttendSynDingNotify sysAttendSynDingNotify : recordList) {
				SysAttendSynDing sysAttendSynDing = (SysAttendSynDing) this
						.findByPrimaryKey(sysAttendSynDingNotify
								.getFdSysAttendSynDingId());
				if (sysAttendSynDing != null) {
					notifyRemindInvalidRecord(sysAttendSynDingNotify,
							sysAttendSynDing);
					sysAttendSynDingNotify.setFdStatus(1);
					getSysAttendSynDingNotifyService()
							.update(sysAttendSynDingNotify);
				}
			}
		}
	}

	private void saveInvalidRecordNotify(SysAttendSynDing sysAttendSynDing)
			throws Exception {
		if (sysAttendSynDing == null) {
			logger.debug("插入不合法考勤记录待阅:没有考勤记录");
			return;
		}
		try {
			SysAttendSynDingNotify sysAttendSynDingNotify = new SysAttendSynDingNotify();
			sysAttendSynDingNotify.setDocCreateTime(new Date());
			sysAttendSynDingNotify
					.setDocCreator(UserUtil.getKMSSUser().getPerson());
			SysOrgElement fdPerson = sysOrgCoreService
					.findByPrimaryKey(sysAttendSynDing.getFdPersonId());
			sysAttendSynDingNotify.setFdReceiver(fdPerson);
			String fdUserCheckTime = DateUtil.convertDateToString(
					sysAttendSynDing.getFdUserCheckTime(), "yyyy-MM-dd HH:mm");
			String subject = ResourceUtil.getString(
					"sysAttendSynDing.invalid.attend",
					"sys-attend", UserUtil.getKMSSUser().getLocale(),
					new Object[] { fdUserCheckTime });
			sysAttendSynDingNotify.setDocSubject(subject);
			String fdLink = "/sys/attend/sys_attend_syn_ding/sysAttendSynDing.do?method=view&fdId="
					+ sysAttendSynDing.getFdId();
			sysAttendSynDingNotify.setFdLink(fdLink);
			sysAttendSynDingNotify.setFdStatus(0);
			sysAttendSynDingNotify
					.setFdSysAttendSynDingId(sysAttendSynDing.getFdId());
			getSysAttendSynDingNotifyService().add(sysAttendSynDingNotify);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void addImportData(InputStream inputStream,HttpServletRequest request,SysAttendImportLog sysAttendImportLog) throws Exception {
		POIFSFileSystem fs = new POIFSFileSystem(inputStream);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wb.getSheetAt(0);
		UploadResultBean result = new UploadResultBean();// 操作结果集
		logger.warn("原始记录导入 start...");
		// Excel行内容为空
		if (sheet.getLastRowNum() < 1) {
			String[] fdCodeArgs3 = {
					"" + 1,
					ResourceUtil.getString(
							"sysAttend.excel.import.fileIsEmpty",
							"sys-attend") };
			result.addErrorMsg(ResourceUtil.getString(
					"sysAttend.excel.import.messageIgore",
					"sys-attend",
					null,
					fdCodeArgs3));
		} else {
			boolean hasError = false;
			JSONArray jsonArray=new JSONArray();
			// 缓存用户名与用户信息
			Map<String, SysOrgPerson> personsMap = new HashMap<String, SysOrgPerson>();
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				hasError = false;
				HSSFRow row = sheet.getRow(i);
				// 行不为空
				if (row == null) {
					continue;
				}
				// 每列都是空的行跳过
				int j = 0;
				for (; j < 3; j++) {
					if (StringUtil.isNotNull(
							getCellValue(row.getCell(j)))) {
						break;
					}
				}
				if (j == 3) {
					continue;
				}
				String fdName=getCellValue(row.getCell(0));
				if(StringUtil.isNull(fdName)) {
					hasError=true;
					String[] fdCodeArgs2 = {
							"" + (i + 1),
							ResourceUtil.getString("sysAttend.import.fdName.notNull","sys-attend") };
					result.addErrorMsg(ResourceUtil.getString(
							"sysAttend.excel.import.messageIgore",
							"sys-attend",
							null, fdCodeArgs2));
					result.addIgoreCount();
					continue;
				}
				Cell loginNameCell = row.getCell(1);
				String loginName = getCellValue(loginNameCell);
				if(StringUtil.isNull(loginName)) {
					hasError=true;
					String[] fdCodeArgs2 = {
							"" + (i + 1),
							ResourceUtil.getString("sysAttend.import.error.loginName","sys-attend") };
					result.addErrorMsg(ResourceUtil.getString(
							"sysAttend.excel.import.messageIgore",
							"sys-attend",
							null, fdCodeArgs2));
					result.addIgoreCount();
					continue;
				}
				SysOrgPerson person=null;
				if(personsMap.containsKey(loginName)) {
					person=personsMap.get(loginName);
				}else {
					person= sysOrgCoreService.findByLoginName(loginName);
				}
				if(person!=null) {
					if(!personsMap.containsKey(loginName)) {
						personsMap.put(loginName, person);
					}
					if(!person.getFdName().equalsIgnoreCase(fdName)) {
						hasError=true;
						String[] fdCodeArgs2 = {
								"" + (i + 1),
								ResourceUtil.getString("sysAttend.excel.import.fdName.loginName.notequals","sys-attend") };
						result.addErrorMsg(ResourceUtil.getString(
								"sysAttend.excel.import.messageIgore",
								"sys-attend",
								null, fdCodeArgs2));
						result.addIgoreCount();
						continue;
					}
					String docCreateTimeStr= null;
					try {
						docCreateTimeStr=getCellValue(row.getCell(2));
						Date docCreateTime=DateUtil.convertStringToDate(docCreateTimeStr, AttendConstant.DATE_TIME_FORMAT_STRING);
						if(StringUtil.isNull(docCreateTimeStr)) {
							hasError=true;
							String[] fdCodeArgs2 = {
									"" + (i + 1),
									ResourceUtil.getString("sysAttend.import.signTime.notNull","sys-attend") };
							result.addErrorMsg(ResourceUtil.getString(
									"sysAttend.excel.import.messageIgore",
									"sys-attend",
									null, fdCodeArgs2));
							result.addIgoreCount();
							continue;
						}
					}catch (Exception e) {
						docCreateTimeStr= null;
						if(StringUtil.isNull(docCreateTimeStr)) {
							hasError=true;
							String[] fdCodeArgs2 = {
									"" + (i + 1),
									ResourceUtil.getString("sysAttend.import.signTime.format.fail","sys-attend",null,new Object[]{ResourceUtil.getString("sysAttend.date.format.datetime", "sys-attend", request.getLocale())}) };
							result.addErrorMsg(ResourceUtil.getString(
									"sysAttend.excel.import.messageIgore",
									"sys-attend",
									null, fdCodeArgs2));
							result.addIgoreCount();
							continue;
						}
					}
					JSONObject obj= new JSONObject();
					JSONObject docCreatorObj= new JSONObject();
					docCreatorObj.put("Id", person.getFdId());
					obj.put("docCreator", docCreatorObj);
					obj.put("createTime", docCreateTimeStr);
					jsonArray.add(obj);
				}else {
					hasError=true;
					String[] fdCodeArgs2 = {
							"" + (i + 1),
							ResourceUtil.getString(
									"sysAttend.import.error.loginName.notExist",
									"sys-attend") };
					result.addErrorMsg(ResourceUtil.getString(
							"sysAttend.excel.import.messageIgore",
							"sys-attend",
							null, fdCodeArgs2));
					result.addIgoreCount();
					continue;
				}
				if (hasError) {
					result.addFailCount();
				}
			}
			try {
				if(result.getFailCount()<=0 && result.getIgnoreCount()<=0&&jsonArray.size()>0) {
					result.setSuccessCount(jsonArray.size());
					sysAttendImportLog.setFdStatus(1);
					logger.warn("原始记录导入 calcing...");
					publishAttendEvent("EXCEL",jsonArray,"import",sysAttendImportLog);
					request.setAttribute("fdStatus", sysAttendImportLog.getFdStatus());
				}
				sysAttendImportLog.setFdResultMessage(result.toString());
				sysAttendImportLogService.update(sysAttendImportLog);
			}catch (Exception e) {
				result.addErrorMsg("打卡记录导入失败"+e.getMessage());
				logger.error("打卡记录导入失败",e);
			}
		}
		request.setAttribute("result", result);
		logger.warn("原始记录导入 end...");
	}
	private String getCellValue(Cell cell) {
		if(cell==null) {
			return null;
		}
		if (cell.getCellType()==org.apache.poi.ss.usermodel.CellType.NUMERIC
				&& org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {// 处理日期、时间格式
			SimpleDateFormat sdf = null;
			sdf = new SimpleDateFormat(AttendConstant.DATE_TIME_FORMAT_STRING);
			return sdf.format(cell.getDateCellValue());
		}
		return ImportUtil.getCellValue(cell);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	private void publishAttendEvent(String appName, JSONArray arrays,String operatorType,SysAttendImportLog sysAttendImportLog) {
		// 发送事件通知
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appName", appName);
		params.put("datas", arrays);
		params.put("operatorType", operatorType);
		params.put("sysAttendImportLog", sysAttendImportLog);
		applicationContext.publishEvent(new Event_Common(
				"importOriginAttendMain", params));
	}

	/**
	 * 导出原始记录
	 */
	@Override
	public HSSFWorkbook buildWorkBook(List list)
			throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(
				ResourceUtil.getString("sysAttendMain.export.filename.synDing",
						"sys-attend"));
		sheet.createFreezePane(0, 1);
		// 标题
		int titleIdx = buildTitle(workbook, sheet,0);
		// 内容
		buildContent(workbook,sheet,titleIdx + 1, list);
		return workbook;
	}

	private int buildTitle(HSSFWorkbook workbook, HSSFSheet sheet,
						   int rowStartIdx) throws Exception {
		if (workbook == null || sheet == null) {
			return -1;
		}
		int colNum = 10;

		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 7000);
		sheet.setColumnWidth(7, 6000);
		sheet.setColumnWidth(8, 3000);
		sheet.setColumnWidth(9, 5000);

		/* 标题行 */
		sheet.createFreezePane(0, 1);
		HSSFRow titlerow = sheet.createRow(rowStartIdx);
		titlerow.setHeight((short) 400);
		HSSFCellStyle titleCellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		titleCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		titleCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
		font.setBold(true);
		titleCellStyle.setFont(font);

		HSSFCell[] titleCells = new HSSFCell[colNum];
		for (int i = 0; i < titleCells.length; i++) {
			titleCells[i] = titlerow.createCell(i);
			titleCells[i].setCellStyle(titleCellStyle);
		}

		titleCells[0].setCellValue(ResourceUtil
				.getString(
						"sys-attend:sysAttendMain.docCreator"));
		titleCells[1].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.docCreatorDept"));
		titleCells[2].setCellValue(ResourceUtil
				.getString(
						"sys-attend:sysAttendMain.export.shouldTime.attend"));
		titleCells[3].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.signTime"));
		titleCells[4].setCellValue(ResourceUtil
				.getString(
						"sys-attend:sysAttendMain.export.fdSignType"));
		titleCells[5].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.fdAppName"));
		titleCells[6].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.fdLocation"));
		titleCells[7].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendCategory.attend"));
		titleCells[8].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.fdStatus"));
		titleCells[9].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendSynDing.docCreateTime"));

		return sheet.getLastRowNum();
	}

	private int buildContent(HSSFWorkbook workbook, HSSFSheet sheet,
							 int rowStartIdx, List list) throws Exception {
		try {
			if (workbook == null || sheet == null) {
				return -1;
			}
			int colNum = 10;

			/* 内容行 */
			HSSFCellStyle contentCellStyle = workbook.createCellStyle();
			contentCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
			contentCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
			contentCellStyle.setDataFormat(workbook.createDataFormat().getFormat("@"));

			if (list != null && !list.isEmpty()) {
				Map<String,SysAttendCategory> categoryMap=new HashMap<String,SysAttendCategory>();
				for (int i = 0; i < list.size(); i++) {
					HSSFRow contentrow = sheet.createRow(rowStartIdx++);
					contentrow.setHeight((short) 400);
					HSSFCell[] contentcells = new HSSFCell[colNum];
					for (int j = 0; j < contentcells.length; j++) {
						contentcells[j] = contentrow.createCell(j);
						contentcells[j].setCellStyle(contentCellStyle);
						contentcells[j].setCellType(CellType.STRING);
					}

					SysAttendSynDing sysAttendSynDing = (SysAttendSynDing) list.get(i);
					SysOrgElement sysOrgElement = sysAttendSynDing.getDocCreator();
					contentcells[0]
							.setCellValue(sysOrgElement == null ? "" : sysOrgElement.getFdName());
					contentcells[1]
							.setCellValue(
									sysOrgElement == null || sysOrgElement.getFdParent() == null
											? ""
											: sysOrgElement.getFdParent()
											.getFdName());
					Date shouldTime = sysAttendSynDing.getFdBaseCheckTime();
					contentcells[2]
							.setCellValue(DateUtil.convertDateToString(
									shouldTime, DateUtil.TYPE_DATETIME, null));
					Date signTime=sysAttendSynDing.getFdUserCheckTime();
					contentcells[3]
							.setCellValue(DateUtil.convertDateToString(
									signTime, DateUtil.TYPE_DATETIME, null));
					String fdStatusStr = EnumerationTypeUtil
							.getColumnEnumsLabel(
									"sysAttendMain_fdSourceType",
									String.valueOf(
											sysAttendSynDing.getFdSourceType()),
									null);
					contentcells[4].setCellValue(fdStatusStr);
					String appName = sysAttendSynDing.getFdAppName();
					appName = formatAppName(appName);
					contentcells[5].setCellValue(appName);
					contentcells[6].setCellValue(sysAttendSynDing.getFdUserAddress());
					SysAttendCategory category=null;

					String cateId = sysAttendSynDing.getFdGroupId();
					if(sysOrgElement != null && !categoryMap.containsKey(sysOrgElement.getFdId())) {
						if(StringUtil.isNull(cateId) ) {
							cateId = sysAttendCategoryService.getCategory(sysOrgElement, signTime);
						}
						category = CategoryUtil.getCategoryById(cateId);
						if(category !=null) {
							categoryMap.put(sysOrgElement.getFdId(), category);
						}
					} else {
						category=sysOrgElement == null ? null : categoryMap.get(sysOrgElement.getFdId());
					}
					contentcells[7]
							.setCellValue(
									category == null ? ""
											: category.getFdName());
					String fdStatus = formatStatus(sysAttendSynDing);
					contentcells[8].setCellValue(fdStatus);
					contentcells[9].setCellValue(DateUtil.convertDateToString(
							sysAttendSynDing.getDocCreateTime(), DateUtil.TYPE_DATETIME,
							null));
				}
			}

			return sheet.getLastRowNum();
		} catch (Exception e) {
			logger.error("buildAttendContent Error:" + e.getMessage(), e);
			throw e;
		}
	}

	private String formatStatus(SysAttendSynDing sysAttendSynDing) {
		if(sysAttendSynDing==null) {
			return "";
		}
		String timeResult=sysAttendSynDing.getFdTimeResult();
		String fdStatus="";
		if(StringUtil.isNotNull(sysAttendSynDing.getFdInvalidRecordType())) {
			fdStatus=ResourceUtil.getString(
					"sys-attend:sysAttendMain.fdStatus.invalid");
		}else if("Normal".equals(timeResult)&&"Outside".equals(sysAttendSynDing.getFdLocationResult())) {
			fdStatus=ResourceUtil.getString(
					"sys-attend:sysAttendMain.outside");
		}else if("Late".equals(timeResult)||"SeriousLate".equals(timeResult)||"Absenteeism".equals(timeResult)) {
			fdStatus=ResourceUtil.getString(
					"sys-attend:sysAttendMain.fdStatus.late");
		} else if("Early".equals(timeResult)) {
			fdStatus=ResourceUtil.getString(
					"sys-attend:sysAttendMain.fdStatus.left");
		}else if("Normal".equals(timeResult)) {
			fdStatus=ResourceUtil.getString(
					"sys-attend:sysAttendMain.fdStatus.ok");
		}else if("NotSigned".equals(timeResult)) {
			fdStatus=ResourceUtil.getString(
					"sys-attend:sysAttendMain.fdStatus.unSign");
		}else if("Trip".equals(timeResult)) {
			fdStatus=ResourceUtil.getString(
					"sys-attend:sysAttendMain.fdStatus.business");
		}else if("Leave".equals(timeResult)) {
			fdStatus=ResourceUtil.getString(
					"sys-attend:sysAttendMain.fdStatus.askforleave");
		}else if("Outgoing".equals(timeResult)) {
			fdStatus=ResourceUtil.getString(
					"sys-attend:sysAttendMain.fdStatus.outgoing");
		}
		return fdStatus;
	}

	private String formatAppName(String appName) {
		if ("dingding".equals(appName)) {
			appName = ResourceUtil.getString(
					"sys-attend:sysAttendMain.fdAppName.dingDing");
		} else if ("qywx".equals(appName)) {
			appName = ResourceUtil.getString(
					"sys-attend:sysAttendMain.fdAppName.qywx");
		}else if (StringUtil.isNull(appName)) {
			appName = ResourceUtil.getString(
					"sys-attend:sysAttendMain.fdAppName.ekp");
		}
		return appName;
	}
}
