package com.landray.kmss.sys.attend.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTimesheet;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendMainNotifyJobService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendPersonUtil;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.attend.util.DateTimeFormatUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.quartz.model.SysQuartzJob;
import com.landray.kmss.sys.quartz.service.ISysQuartzJobService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysAttendMainNotifyJobServiceImp
		implements ISysAttendMainNotifyJobService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendMainNotifyJobServiceImp.class);
	private ISysAttendCategoryService sysAttendCategoryService;
	private ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
			.getBean("sysOrgCoreService");
	private ISysNotifyMainCoreService sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil
			.getBean("sysNotifyMainCoreService");
	private ISysQuartzJobService sysQuartzJobService = (ISysQuartzJobService) SpringBeanUtil
			.getBean(
					"sysQuartzJobService");
	private IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");

	private ISysAttendMainService sysAttendMainService = (ISysAttendMainService) SpringBeanUtil
			.getBean("sysAttendMainService");

	@Override
	public void execute(SysQuartzJobContext jobContext) throws Exception {
		Date statDate = DateUtil.getDate(-1);

		//查询所有有效考勤组
		HQLInfo hqlInfo = new HQLInfo();
		// 1.获取用户考勤组
		List<String> cateList = new ArrayList<String>();
		String whereBlock = "sysAttendCategory.fdStatus=1 and sysAttendCategory.fdType=1 and sysAttendCategory.fdNotifyResult=:fdNotifyResult";
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("fdNotifyResult", true);
		hqlInfo.setSelectBlock("sysAttendCategory.fdId");
		cateList = sysAttendCategoryService.findValue(hqlInfo);
		if (cateList.isEmpty()) {
			logger.debug("no attend category to notify...");
			return;
		}
		for (String categoryId:cateList) {
			String hisId = CategoryUtil.getLastVersionFdId(categoryId,statDate);
			if(StringUtil.isNotNull(hisId)) {
				executeSend(statDate, hisId);
			}else{
				logger.warn("考勤组、日期无法找到历史考勤组版本："+categoryId+" ｜ " + statDate);
			}
		}
	}

	@Override
	@Deprecated
	public void executeAcross(SysQuartzJobContext context) throws Exception {
		//该定时任务应该是作废了。
		JSONObject param = JSONObject.fromObject(context.getParameter());
		if (!param.containsKey("fdCategoryId")) {
			return;
		}
		String fdCategoryId = param.getString("fdCategoryId");
		if (StringUtil.isNull(fdCategoryId)) {
			return;
		}
		// 防止在数据库手动删除考勤组后定时任务仍然执行
		SysAttendCategory category = (SysAttendCategory) sysAttendCategoryService
				.findByPrimaryKey(fdCategoryId, null, true);
		if (category == null) {
			List<SysQuartzJob> jobList = sysQuartzJobService.findList(
							"fdModelName='com.landray.kmss.sys.attend.model.SysAttendCategory' and fdModelId='"
									+ fdCategoryId + "'",
							null);
			if (jobList != null && !jobList.isEmpty()) {
				for (SysQuartzJob job : jobList) {
					sysQuartzJobService.delete(job);
				}
			}
			return;
		}
		Date statDate = DateUtil.getDate(-1);
		String hisId = CategoryUtil.getLastVersionFdId(fdCategoryId,statDate);
		executeSend(statDate, hisId);
	}

	/**
	 * 执行缺卡通知 发送
	 * @param date 日期
	 * @param categoryId 考勤组
	 * @throws Exception
	 */
	public void executeSend(Date date, String categoryId)
			throws Exception {
		try {
			Date beginTime = AttendUtil.getDate(date, 0);
			Date endTime = AttendUtil.getDate(date, 1);
			// 1.获取用户考勤组
			SysAttendCategory category = CategoryUtil.getCategoryById(categoryId);
			if (category == null) {
				logger.debug("no attend category to notify...");
				return;
			}
			List<String> orgList = sysAttendCategoryService.getAttendPersonIds(category.getFdId(), date);
			if (orgList.isEmpty()) {
				return;
			}
			if (Integer.valueOf(1).equals(category.getFdShiftType())) {
				// 排班缺卡
				sendNotifyOfTimeArea(category, date);
				return;
			}
			// 班次
			JSONArray works = getWorkTime(category, date);
			if (works.isEmpty()) {
				return;
			}

			// 2.用户组分割
			int maxCount = 500;
			List<List> groupLists = new ArrayList<List>();
			if (orgList.size() <= maxCount) {
				groupLists.add(orgList);
			} else {
				groupLists = AttendUtil.splitList(orgList, maxCount);
			}

			for (int k = 0; k < works.size(); k++) {
				JSONObject json = (JSONObject) works.get(k);
				String fdWorkId = json.getString("fdWorkId");
				Integer fdWorkType = json.getInt("fdWorkType");

				for (int i = 0; i < groupLists.size(); i++) {
					List<String> tmpOrgList = groupLists.get(i);
					// 3.哪些人员缺卡
					String where = " fd_category_his_id=:fdCategoryId and fd_work_id=:fdWorkId and fd_work_type=:fdWorkType "
							+ " and doc_creator_id in (:docCreatorId)"
							+ " and doc_create_time>=:beginTime and doc_create_time<:endTime and (fd_is_across is null or fd_is_across=0)"
							+ " and (doc_status=0 or doc_status is null) "
							+ " and fd_status=0 and (fd_state is null or fd_state=0)";

					String whereTwo = " fd_category_his_id=:fdCategoryId and fd_work_id=:fdWorkId and fd_work_type=:fdWorkType "
							+ " and doc_creator_id in (:docCreatorId)"
							+ " and fd_is_across=1 and doc_create_time>=:nextBegin and doc_create_time<:nextEnd"
							+ " and (doc_status=0 or doc_status is null) "
							+ " and fd_status=0 and (fd_state is null or fd_state=0)";

					String recordSql = "select distinct fd_id,doc_creator_id from sys_attend_main where "
							+ where;
					List signedListOne = baseDao.getHibernateSession().createNativeQuery(recordSql)
							.setString("fdCategoryId", category.getFdId())
							.setString("fdWorkId", fdWorkId)
							.setDate("beginTime", beginTime)
							.setDate("endTime", endTime)
							.setParameterList("docCreatorId", tmpOrgList)
							.setInteger("fdWorkType", fdWorkType).list();

					String recordTwoSql = "select distinct fd_id,doc_creator_id from sys_attend_main where "
							+ whereTwo;
					List signedListTwo = baseDao.getHibernateSession()
							.createNativeQuery(recordTwoSql)
							.setString("fdCategoryId", category.getFdId())
							.setString("fdWorkId", fdWorkId)
							.setDate("nextBegin", AttendUtil.getDate(date, 1))
							.setDate("nextEnd", AttendUtil.getDate(date, 2))
							.setParameterList("docCreatorId", tmpOrgList)
							.setInteger("fdWorkType", fdWorkType).list();

					List signedList =new ArrayList();
					if(CollectionUtils.isNotEmpty(signedListOne)){
						signedList.addAll(signedListOne);
					}
					if(CollectionUtils.isNotEmpty(signedListTwo)){
						signedList.addAll(signedListTwo);
					}
					if (signedList.isEmpty()) {
						continue;
					}

					for (int j = 0; j < signedList.size(); j++) {
						Object[] record = (Object[]) signedList.get(j);
						String fdId = (String) record[0];
						String docCreatorId = (String) record[1];
						List<String> userIds = new ArrayList<String>();
						userIds.add(docCreatorId);
						if (StringUtil.isNull(fdId)) {
							continue;
						}
						SysAttendMain main = (SysAttendMain) sysAttendMainService
								.findByPrimaryKey(fdId);
						// 4.发待办
						sendResultNotify(main, fdWorkType, userIds,
								beginTime);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("考勤缺卡结果通知失败:" + e.getMessage(), e);
		}
	}

	private JSONArray getWorkTime(SysAttendCategory category, Date date) {
		JSONArray works = new JSONArray();
		List<SysAttendCategoryWorktime> workTimes = null;
		if (Integer.valueOf(0).equals(category.getFdShiftType())
				&& Integer.valueOf(1).equals(category.getFdSameWorkTime())) {
			List<SysAttendCategoryTimesheet> tSheets = category
					.getFdTimeSheets();
			if (tSheets != null && !tSheets.isEmpty()) {
				for (SysAttendCategoryTimesheet tSheet : tSheets) {
					if (StringUtil.isNotNull(tSheet.getFdWeek()) && tSheet
							.getFdWeek()
							.indexOf(AttendUtil.getWeek(date) + "") > -1) {
						workTimes = tSheet.getAvailWorkTime();
						break;
					}
				}
			}
		} else {
			workTimes = category.getAvailWorkTime();
		}
		if (workTimes == null || workTimes.isEmpty()) {
			return works;
		}
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
		return works;
	}

	/**
	 * 发送缺卡的待办定时任务
	 * @param main
	 * @param fdWorkType
	 * @param targetIds
	 * @param signTime
	 * @throws Exception
	 */
	public void sendResultNotify(SysAttendMain main, int fdWorkType,
			List<String> targetIds, Date signTime) throws Exception {
		try {
			if (targetIds.isEmpty()) {
				return;
			}
			NotifyContext notifyContext = sysNotifyMainCoreService
					.getContext(null);
			String __signTime = new DateTimeFormatUtil().getDateTime(signTime,
					"M'" + ResourceUtil.getString(
							"sys-attend:sysAttendMain.month")
							+ "'d'"
							+ ResourceUtil.getString(
									"sys-attend:sysAttendMain.day")
							+ "'");
			String subject = __signTime + " ";
			if (fdWorkType == AttendConstant.SysAttendMain.FD_WORK_TYPE[0]) {
				subject = subject + ResourceUtil.getString(
						"sys-attend:sysAttendMain.fdWorkType.onwork");
			} else if (fdWorkType == AttendConstant.SysAttendMain.FD_WORK_TYPE[1]) {
				subject = subject + ResourceUtil.getString(
						"sys-attend:sysAttendMain.fdWorkType.offwork");
			}
			subject = subject + ResourceUtil.getString(
					"sys-attend:sysAttendCategory.notify.remind.unsign.subject");
			notifyContext.setSubject(subject);
			notifyContext.setContent(subject);
			List<SysOrgElement> targets = AttendPersonUtil.getSysOrgElementById(targetIds);
			notifyContext.setNotifyTarget(targets);
			notifyContext.setNotifyType("todo");
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
			notifyContext.setLink(
					"/sys/attend/sys_attend_main/sysAttendMain_calendar_toward.jsp?categoryId="
							+ main.getFdHisCategory().getFdId() + "&fdId="
							+ main.getFdId());

			notifyContext.setKey("sendUnSignNotify");
			sysNotifyMainCoreService.sendNotify(main, notifyContext, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}

	}

	private void sendNotifyOfTimeArea(SysAttendCategory category, Date date)
			throws Exception {
		Date beginTime = AttendUtil.getDate(date, 0);
		Date endTime = AttendUtil.getDate(date, 1);
		List<String> orgList = sysAttendCategoryService.getAttendPersonIds(category.getFdId(),date);
		if (orgList.isEmpty()) {
			return;
		}
		// 用户组分割
		int maxCount = 500;
		List<List> groupLists = new ArrayList<List>();
		if (orgList.size() <= maxCount) {
			groupLists.add(orgList);
		} else {
			groupLists = AttendUtil.splitList(orgList, maxCount);
		}
		for (int i = 0; i < groupLists.size(); i++) {
			List<String> tmpOrgList = groupLists.get(i);
			// 3.哪些人员缺卡
			String recordSql = "select doc_creator_id,fd_work_type,doc_create_time,fd_id from sys_attend_main where "
					+ " doc_create_time>=:beginTime and doc_create_time<:endTime and (fd_is_across is null or fd_is_across=0)"
					+ " and fd_status=0 and (fd_state is null or fd_state=0)"
					+ " and doc_creator_id in (:docCreatorId) and fd_category_his_id=:fdCategoryId"
					+ " and fd_work_type in(0,1)"
					+ " and (doc_status=0 or doc_status is null)";
			List signedListOne = baseDao.getHibernateSession().createNativeQuery(recordSql)
					.setTimestamp("beginTime", beginTime)
					.setTimestamp("endTime", endTime)
					.setParameterList("docCreatorId", tmpOrgList)
					.setString("fdCategoryId", category.getFdId()).list();

			String recordSqlTwo = "select doc_creator_id,fd_work_type,doc_create_time,fd_id from sys_attend_main where "
					+ " fd_is_across=1 and doc_create_time>=:nextBegin and doc_create_time<:nextEnd "
					+ " and fd_status=0 and (fd_state is null or fd_state=0)"
					+ " and doc_creator_id in (:docCreatorId) and fd_category_his_id=:fdCategoryId"
					+ " and fd_work_type in(0,1)"
					+ " and (doc_status=0 or doc_status is null)";
			List signedListTwo = baseDao.getHibernateSession().createNativeQuery(recordSqlTwo)
					.setTimestamp("nextBegin", AttendUtil.getDate(date, 1))// 跨天加班的数据
					.setTimestamp("nextEnd", AttendUtil.getDate(date, 2))
					.setParameterList("docCreatorId", tmpOrgList)
					.setString("fdCategoryId", category.getFdId()).list();
			List signedList =new ArrayList();
			if(CollectionUtils.isNotEmpty(signedListOne)){
				signedList.addAll(signedListOne);
			}
			if(CollectionUtils.isNotEmpty(signedListTwo)){
				signedList.addAll(signedListTwo);
			}
			if (signedList.isEmpty()) {
				continue;
			}
			// 上班用户打卡(打卡时间点与用户关系)
			Map<Integer, Map<String, List<String>>> userOnList = new HashMap<Integer, Map<String, List<String>>>();
			// 下班用户打卡
			Map<Integer, Map<String, List<String>>> userOffList = new HashMap<Integer, Map<String, List<String>>>();

			for (int k = 0; k < signedList.size(); k++) {
				Object[] record = (Object[]) signedList.get(k);
				String docCreatorId = (String) record[0];
				Number fdWorkType = (Number) record[1];
				Timestamp docCreateTime = (Timestamp) record[2];
				String fdId = (String) record[3];
				int signTimeMin = AttendUtil
						.getHMinutes(new Date(docCreateTime.getTime()));
				if (fdWorkType.intValue() == 0) {
					if (!userOnList.containsKey(signTimeMin)) {
						userOnList.put(signTimeMin,
								new HashMap<String, List<String>>());
					}
					Map<String, List<String>> mainMap = userOnList
							.get(signTimeMin);
					if (!mainMap.containsKey(fdId)) {
						mainMap.put(fdId, new ArrayList<String>());
					}
					List<String> userIds = mainMap.get(fdId);
					userIds.add(docCreatorId);

				} else {
					if (!userOffList.containsKey(signTimeMin)) {
						userOffList.put(signTimeMin,
								new HashMap<String, List<String>>());
					}
					Map<String, List<String>> mainMap = userOffList
							.get(signTimeMin);
					if (!mainMap.containsKey(fdId)) {
						mainMap.put(fdId, new ArrayList<String>());
					}
					List<String> userIds = mainMap.get(fdId);
					userIds.add(docCreatorId);

				}
			}
			for (Map.Entry<Integer, Map<String, List<String>>> entry : userOnList
					.entrySet()) {
				if (entry.getValue().isEmpty()) {
					continue;
				}
				for (Map.Entry<String, List<String>> mainentry : entry
						.getValue().entrySet()) {
					SysAttendMain main = (SysAttendMain) sysAttendMainService
							.findByPrimaryKey(mainentry.getKey());
					sendResultNotify(main, 0, mainentry.getValue(), beginTime);
				}
			}
			for (Map.Entry<Integer, Map<String, List<String>>> entry : userOffList
					.entrySet()) {
				if (entry.getValue().isEmpty()) {
					continue;
				}
				for (Map.Entry<String, List<String>> mainentry : entry
						.getValue().entrySet()) {
					SysAttendMain main = (SysAttendMain) sysAttendMainService
							.findByPrimaryKey(mainentry.getKey());
					sendResultNotify(main, 1, mainentry.getValue(), beginTime);
				}
			}
		}
	}

	public void setSysAttendCategoryService(
			ISysAttendCategoryService sysAttendCategoryService) {
		this.sysAttendCategoryService = sysAttendCategoryService;
	}

}
