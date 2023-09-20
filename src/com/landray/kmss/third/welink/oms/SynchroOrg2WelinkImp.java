package com.landray.kmss.third.welink.oms;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.welink.model.ThirdWelinkConfig;
import com.landray.kmss.third.welink.service.IThirdWelinkService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONArray;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.slf4j.Logger;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SynchroOrg2WelinkImp implements SynchroOrg2Welink,
		SysOrgConstant {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SynchroOrg2Welink.class);

	private SysQuartzJobContext jobContext = null;

	private String lastSynchroTime = null;

	private String currentSynchroTime = null;

	private IThirdWelinkService thirdWelinkService;


	// private ThreadPoolTaskExecutor taskExecutor;
	//
	// public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
	// this.taskExecutor = taskExecutor;
	// }

	private static boolean locked = false;

	@Override
	public void triggerSynchro(SysQuartzJobContext context) {
		String temp = "";
		this.jobContext = context;
		if (locked) {
			temp = "存在运行中的welink组织架构同步任务，当前任务中断...";
			logger.info(temp);
			context.logMessage(temp);
			return;
		}
		if (!"true"
				.equals(ThirdWelinkConfig.newInstance().getWelinkEnabled())) {
			temp = "welink集成已经关闭，故不同步数据";
			logger.info(temp);
			context.logMessage(temp);
			return;
		}

		if (!"true".equals(
				ThirdWelinkConfig.newInstance().getSynchroOrg2Welink())) {
			temp = "welink组织架构接出已经关闭，故不同步数据";
			logger.debug(temp);
			context.logMessage(temp);
			return;
		}

		try {
			locked = true;

			currentSynchroTime = DateUtil.convertDateToString(new Date(),
					"yyyy-MM-dd HH:mm:ss.SSS");
			WelinkOmsConfig welinkOmsConfig = new WelinkOmsConfig();
			lastSynchroTime = welinkOmsConfig.getLastSynchroTime();
			temp = "==========开始同步组织数据到welink===============同步时间戳为："
					+ lastSynchroTime;
			logger.debug(temp);
			context.logMessage(temp);
			context.logMessage("");

			long alltime = System.currentTimeMillis();

			long caltime = System.currentTimeMillis();
			Set<String> userIds_noSync = checkData(context);
			temp = "校验数据耗时："
					+ (System.currentTimeMillis() - caltime);
			logger.debug(temp);
			context.logMessage(temp);
			context.logMessage("");

			SynchroOrg2WelinkNewImp newImp = new SynchroOrg2WelinkNewImp();
			newImp.init();
			newImp.setExcludedUserIds(userIds_noSync);
			newImp.setTargetDepartments(getData(ORG_TYPE_DEPT));
			newImp.syncDepartments(context);
			/**
			List<String> toGetDeptSynchroStatus = new ArrayList<String>();
			// 新增部门到welink
			caltime = System.currentTimeMillis();
			// TransactionStatus addDepts = TransactionUtils
			// .beginNewTransaction();
			addDepts(toGetDeptSynchroStatus);
			// TransactionUtils.commit(addDepts);
			//IThirdWelinkDeptMappingService thirdWelinkDeptMappingService = (IThirdWelinkDeptMappingService) SpringBeanUtil
			//		.getBean("thirdWelinkDeptMappingService");
			//Session session = thirdWelinkDeptMappingService.getBaseDao()
			//		.getHibernateSession();
			//session.flush();
			//session.clear();

			// 获取部门同步结果
			if (!toGetDeptSynchroStatus.isEmpty()) {
				Thread.sleep(2000);
				JSONArray status = getDeptSyncStatus(toGetDeptSynchroStatus);
				temp = "新增部门结果："
						+ status.toString();
				logger.debug(temp);
				context.logMessage(temp);
			}
			 **/
			temp = "新增部门到welink耗时(毫秒)："
					+ (System.currentTimeMillis() - caltime);
			logger.debug(temp);
			context.logMessage(temp);
			context.logMessage("");

			newImp.setTargetPersons(getData(ORG_TYPE_PERSON));
			newImp.syncPersons(context);

			/**
			List<String> toGetUserSynchroStatus = new ArrayList<String>();
			// 添加welink的人员数据
			caltime = System.currentTimeMillis();
			syncPersons(toGetUserSynchroStatus, userIds_noSync);
			// 获取人员同步结果
			if (!toGetUserSynchroStatus.isEmpty()) {
				Thread.sleep(2000);
				try {
					JSONArray status = getUserSyncStatus(
							toGetUserSynchroStatus);
					temp = "人员同步结果："
						+ status.toString();
				} catch (Exception e) {
					logger.error("", e);
					temp = "获取人员同步结果失败，" + e.getMessage();
				}
				logger.debug(temp);
				context.logMessage(temp);
			}
		 	**/
			temp = "同步人员到welink耗时(毫秒)："
					+ (System.currentTimeMillis() - caltime);
			logger.debug(temp);
			context.logMessage(temp);
			context.logMessage("");

			/**
			Thread.sleep(2000);
			caltime = System.currentTimeMillis();
			updateWelinkUserIds(context);
			temp = "获取welink员工账号信息耗时(毫秒)："
					+ (System.currentTimeMillis() - caltime);
			logger.debug(temp);
			context.logMessage(temp);
			context.logMessage("");
			// 更新部门到welink
			List<String> toGetDeptUpdateStatus = new ArrayList<String>();
			caltime = System.currentTimeMillis();
			updateDepts(toGetDeptUpdateStatus);
			if (!toGetDeptUpdateStatus.isEmpty()) {
				Thread.sleep(2000);
				JSONArray status = getDeptSyncStatus(toGetDeptUpdateStatus);
				temp = "部门更新结果："
						+ status.toString();
				logger.debug(temp);
				context.logMessage(temp);
			}
			temp = "更新部门到welink耗时(毫秒)："
					+ (System.currentTimeMillis() - caltime);
			logger.debug(temp);
			context.logMessage(temp);
			context.logMessage("");
			*/
			terminate();
			temp = "整个任务总耗时(毫秒)：" + (System.currentTimeMillis() - alltime);
			logger.debug(temp);
			context.logMessage(temp);
		} catch (Exception ex) {
			logger.error("welink组织架构同步任务失败" + ex);
			ex.printStackTrace();
			if (context != null) {
				context.logError(ex);
			}
		} finally {
			locked = false;
		}
	}

	private void handlePersonSync(Set<SysOrgPerson> persons) throws Exception {
		String size = "2000";
		int rowsize = Integer.parseInt(size);
		int count = persons.size() % rowsize == 0 ? persons.size() / rowsize : persons.size() / rowsize + 1;
		List<SysOrgPerson> allpersons = new ArrayList<SysOrgPerson>(persons);
		logger.debug("人员总数据:" + allpersons.size() + "条,将执行" + count + "次人员分批同步,每次" + size + "条");
		CountDownLatch countDownLatch = new CountDownLatch(count);
		List<SysOrgPerson> temppersons = null;
		for (int i = 0; i < count; i++) {
			logger.debug("执行第" + (i + 1) + "批");
			if (persons.size() > rowsize * (i + 1)) {
				temppersons = allpersons.subList(rowsize * i, rowsize * (i + 1));
			} else {
				temppersons = allpersons.subList(rowsize * i, persons.size());
			}
			// taskExecutor.execute(new PersonRunner(temppersons));
			countDownLatch.countDown();
		}
		try {
			countDownLatch.await(3, TimeUnit.HOURS);
		} catch (InterruptedException exc) {
			exc.printStackTrace();
			logger.error(exc.toString());
		}
		logger.debug("本次共同步总人员数据:" + persons.size() + "条");
		jobContext.logMessage("本次共同步总人员数据:" + persons.size() + "条");
	}





	private void addDepts(List<String> toGetDeptSynchroStatus)
			throws Exception {
		List<SysOrgElement> depts = getData(ORG_TYPE_DEPT);
		// String logInfo = null;
		SysOrgElement dept = null;
		// System.out.println("部门数：" + depts.size());
		// long count = 0l;
		for (int n = 0; n < depts.size(); n++) {
			dept = depts.get(n);
			String corpDeptCode = thirdWelinkService.syncDept(dept, true);
			if (StringUtil.isNotNull(corpDeptCode)) {
				toGetDeptSynchroStatus.add(corpDeptCode);
			}
		}
	}

	private JSONArray getDeptSyncStatus(List<String> toGetDeptSynchroStatus)
			throws Exception {
		return thirdWelinkService.getDeptSyncStatus(toGetDeptSynchroStatus);

	}

	private JSONArray getUserSyncStatus(List<String> toGetUserSynchroStatus)
			throws Exception {
		return thirdWelinkService.getUserSyncStatus(toGetUserSynchroStatus);

	}

	private void updateDepts(List<String> toGetDeptUpdateStatus)
			throws Exception {
		List<SysOrgElement> depts = getData(ORG_TYPE_DEPT);
		String logInfo = null;
		SysOrgElement dept = null;
		long count = 0L;
		for (int n = 0; n < depts.size(); n++) {
			dept = depts.get(n);
			String corpDeptCode = thirdWelinkService.syncDept(dept, false);
			if (StringUtil.isNotNull(corpDeptCode)) {
				toGetDeptUpdateStatus.add(corpDeptCode);
			}
			count++;
		}
		logInfo = "更新到welink的部门个数为:" + count + "条";
		logger.debug(logInfo);
		jobContext.logMessage(logInfo);
	}

	private void syncPersons(List<String> toGetUserSynchroStatus,
			Set<String> userIds_noSync)
			throws Exception {
		List<SysOrgElement> persons = getData(ORG_TYPE_PERSON);
		String logInfo = null;
		SysOrgPerson person = null;
		long count = 0L;
		for (int n = 0; n < persons.size(); n++) {
			person = (SysOrgPerson) persons.get(n);
			try {
				if (userIds_noSync.contains(person.getFdId())) {
					continue;
				}
				String corpUserId = thirdWelinkService.syncPerson(person);
				if (StringUtil.isNotNull(corpUserId)) {
					toGetUserSynchroStatus.add(corpUserId);
				}
				count++;
			} catch (Exception e) {
				logger.error("同步人员失败：", e);
			}
		}
		logInfo = "同步到welink的人员个数为:" + count + "条";
		logger.debug(logInfo);
		jobContext.logMessage(logInfo);
	}


	private void terminate() throws Exception {

		if (StringUtil.isNotNull(currentSynchroTime)) {
			WelinkOmsConfig welinkOmsConfig = new WelinkOmsConfig();
			welinkOmsConfig.setLastSynchroTime(currentSynchroTime);
			welinkOmsConfig.save();
		}
	}


	class PersonRunner implements Runnable {
		private final List<SysOrgPerson> persons;

		public PersonRunner(List<SysOrgPerson> persons) {
			this.persons = persons;
		}

		@Override
		public void run() {
			try {
				for (int n = 0; n < persons.size(); n++) {
					SysOrgPerson person = (SysOrgPerson) persons.get(n);
					thirdWelinkService.syncPerson(person);
				}
			} catch (Exception e) {
				logger.error("", e);
			} finally {
			}
		}
	}


	/**
	 * @param type
	 * @return
	 * @throws Exception
	 *             根据传入的类型获取数据
	 */
	private List getData(int type) throws Exception {
		List rtnList = new ArrayList();
		HQLInfo info = new HQLInfo();
		String sql = "1=1";
		if (StringUtil.isNotNull(lastSynchroTime)) {
			Date date = DateUtil.convertStringToDate(lastSynchroTime,
					"yyyy-MM-dd HH:mm:ss.SSS");
			sql += " and fdAlterTime>:beginTime";
			info.setParameter("beginTime", date);
		}

		if (type == ORG_TYPE_PERSON) {
			info.setWhereBlock(
					sql + " and fdMobileNo is not null and fdMobileNo != ''");
			rtnList = sysOrgPersonService.findList(info);
		} else if (type == ORG_TYPE_ORG || type == ORG_TYPE_DEPT) {
			info.setOrderBy("fdOrgType asc, LENGTH(fdHierarchyId) asc");
			info.setWhereBlock(sql + " and fdOrgType in (" + ORG_TYPE_ORG + "," + ORG_TYPE_DEPT + ")");
			rtnList = sysOrgElementService.findList(info);
		}
		return rtnList;
	}

	public IThirdWelinkService getThirdWelinkService() {
		return thirdWelinkService;
	}

	public void setThirdWelinkService(IThirdWelinkService thirdWelinkService) {
		this.thirdWelinkService = thirdWelinkService;
	}

	private ISysOrgPersonService sysOrgPersonService;

	public void
			setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	public void
			setSysOrgElementService(
					ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	private ISysOrgElementService sysOrgElementService;

	private void updateWelinkUserIds(SysQuartzJobContext context)
			throws Exception {
		try {
			thirdWelinkService.updateWelinkUserIds();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			context.logError(e.getMessage());
		}
	}


	private Set<String> checkData(SysQuartzJobContext context)
			throws HibernateException, Exception {
		Set<String> users_no_syn = new HashSet<String>();
		String hql = "select a.fdId,a.fdName,a.fdLoginName,b.fdId,b.fdName,b.fdLoginName,b.fdMobileNo from SysOrgPerson a, "
				+ "SysOrgPerson b where a.fdId!=b.fdId and a.fdIsAvailable=1 and b.fdIsAvailable=1 and a.fdMobileNo is not null "
				+ "and b.fdMobileNo is not null and a.fdMobileNo!='' and b.fdMobileNo!='' and a.fdMobileNo = b.fdMobileNo";
		Query query = sysOrgPersonService.getBaseDao().getHibernateSession()
				.createQuery(hql);
		List list = query.list();
		for (int i = 0; i < list.size(); i++) {
			Object[] props = (Object[]) list.get(i);
			System.out.println(props[0] + "---" + props[1] + "---" + props[2]
					+ "---" + props[3] + "---" + props[4] + "---" + props[5]);
			String temp = "用户(名称:" + props[1] + "," + "登录名:" + props[2] + ",ID:"
					+ props[0] + ") 跟  " +
					"用户(名称:" + props[4] + "," + "登录名:" + props[5] + ",ID:"
					+ props[3] + ") 手机号重复，该用户不进行同步，手机号为: " + props[6];
			logger.warn(temp);
			context.logMessage(temp);
			users_no_syn.add((String) props[0]);
		}

		query = sysOrgPersonService.getBaseDao().getHibernateSession()
				.createQuery(hql);

		hql = "select a.fdId,a.fdName,a.fdLoginName,b.fdId,b.fdName,b.fdLoginName,b.fdEmail from SysOrgPerson a, "
				+ "SysOrgPerson b where a.fdId!=b.fdId and a.fdIsAvailable=1 and b.fdIsAvailable=1 and a.fdEmail is not null "
				+ "and b.fdEmail is not null and a.fdEmail!='' and b.fdEmail!='' and a.fdEmail = b.fdEmail";
		query = sysOrgPersonService.getBaseDao().getHibernateSession()
				.createQuery(hql);
		list = query.list();
		for (int i = 0; i < list.size(); i++) {
			Object[] props = (Object[]) list.get(i);
			System.out.println(props[0] + "---" + props[1] + "---" + props[2]
					+ "---" + props[3] + "---" + props[4] + "---" + props[5]);
			String temp = "用户(名称:" + props[1] + "," + "登录名:" + props[2] + ",ID:"
					+ props[0] + ") 跟 " +
					"用户(名称:" + props[4] + "," + "登录名:" + props[5] + ",ID:"
					+ props[3] + ") 邮箱地址重复，该用户不进行同步，邮箱地址为: " + props[6];
			logger.warn(temp);
			context.logMessage(temp);
			users_no_syn.add((String) props[0]);
		}
		return users_no_syn;
	}

}
