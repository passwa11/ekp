package com.landray.kmss.third.ldap.oms.out;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgGroupService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.ldap.LdapConfig;
import com.landray.kmss.third.ldap.LdapDetailConfig;
import com.landray.kmss.third.ldap.apache.ApacheLdapService;
import com.landray.kmss.third.ldap.oms.in.LdapOmsConfig;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

public class SynchroOrg2LdapImp implements SynchroOrg2Ldap,
		SysOrgConstant {

	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(SynchroOrg2LdapImp.class);

	private SysQuartzJobContext jobContext = null;

	private Date lastSynchroTime = null;

	private Date currentSynchroTime = null;

	private ApacheLdapService ldapService;

	private static boolean locked = false;

	@Override
	public void triggerSynchro(SysQuartzJobContext context) {
		String temp = "";
		this.jobContext = context;
		if (locked) {
			temp = "存在运行中的LDAP组织架构同步任务，当前任务中断...";
			logger.info(temp);
			context.logMessage(temp);
			return;
		}
		String omsOut = null;
		try {
			omsOut = new LdapConfig().getValue("kmss.oms.out.ldap.enabled");
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}
		if (!"true".equals(omsOut)) {
			temp = "LDAP组织架构接出已经关闭，故不同步数据";
			logger.debug(temp);
			context.logMessage(temp);
			return;
		}

		try {
			// 新增部门、人员、岗位、群组，更新部门关系（判断有配置关系字段才执行），更新人员关系，更新群组关系，删除群组，删除岗位，删除人员，删除部门
			locked = true;
			ldapService = new ApacheLdapService();
			currentSynchroTime = new Date();
			LdapOmsConfig ldapOmsConfig = new LdapOmsConfig();
			lastSynchroTime = ldapOmsConfig.getLastSynchroOutTime();
			temp = "==========开始同步组织数据到LDAP===============同步时间戳为："
					+ lastSynchroTime;
			logger.debug(temp);
			context.logMessage(temp);
			context.logMessage("");

			long alltime = System.currentTimeMillis();
			// 同步基本信息
			syncBase();
			// 同步关联关系
			syncRelation();
			// 删除无效组织
			deleteInvalidElements();

			terminate();
			temp = "整个任务总耗时(毫秒)：" + (System.currentTimeMillis() - alltime);
			logger.debug(temp);
			context.logMessage(temp);
		} catch (Exception ex) {
			logger.error("LDAP组织架构同步任务失败" + ex);
			ex.printStackTrace();
			if (context != null) {
				context.logError(ex);
			}
		} finally {
			locked = false;
			try {
				ldapService.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private void syncBase() throws Exception {
		long caltime = System.currentTimeMillis();
		String temp = "";
		if (checkSync("dept")) {
			// 新增部门到LDAP
			caltime = System.currentTimeMillis();
			syncDepts();
			temp = "同步部门到LDAP耗时(毫秒)："
					+ (System.currentTimeMillis() - caltime);
			logger.debug(temp);
			jobContext.logMessage(temp);
			jobContext.logMessage("");
		}

		if (checkSync("person")) {
			// 同步人员到LDAP
			caltime = System.currentTimeMillis();
			syncPersons();
			temp = "同步人员到LDAP耗时(毫秒)："
					+ (System.currentTimeMillis() - caltime);
			logger.debug(temp);
			jobContext.logMessage(temp);
			jobContext.logMessage("");
		}

		if (checkSync("post")) {
			// 新增部门到LDAP
			caltime = System.currentTimeMillis();
			syncPosts();
			temp = "同步岗位到LDAP耗时(毫秒)："
					+ (System.currentTimeMillis() - caltime);
			logger.debug(temp);
			jobContext.logMessage(temp);
			jobContext.logMessage("");
		}

		if (checkSync("group")) {
			// 同步人员到LDAP
			caltime = System.currentTimeMillis();
			syncGroups();
			temp = "同步群组到LDAP耗时(毫秒)："
					+ (System.currentTimeMillis() - caltime);
			logger.debug(temp);
			jobContext.logMessage(temp);
			jobContext.logMessage("");
		}
	}

	private void syncRelation() throws Exception {
		long caltime = System.currentTimeMillis();
		String temp = "";
		LdapDetailConfig config = new LdapDetailConfig();
		if (checkSync("dept")) {
			String thisleader = config
					.getValue("kmss.ldap.type.dept.prop.thisleader");
			String superleader = config
					.getValue("kmss.ldap.type.dept.prop.superleader");
			if (StringUtil.isNotNull(thisleader)
					|| StringUtil.isNotNull(superleader)) {
				caltime = System.currentTimeMillis();
				syncDeptRelations();
				temp = "同步部门领导到LDAP耗时(毫秒)："
						+ (System.currentTimeMillis() - caltime);
				logger.debug(temp);
				jobContext.logMessage(temp);
				jobContext.logMessage("");
			}
		}

		if (checkSync("person")) {
			caltime = System.currentTimeMillis();
			String post = config.getValue("kmss.ldap.type.person.prop.post");
			if (StringUtil.isNotNull(post)) {
				syncPersonRelations();
				temp = "同步人员岗位关系耗时(毫秒)："
						+ (System.currentTimeMillis() - caltime);
				logger.debug(temp);
				jobContext.logMessage(temp);
				jobContext.logMessage("");
			}
		}

		if (checkSync("group")) {
			// 同步人员到LDAP
			caltime = System.currentTimeMillis();
			String member = config.getValue("kmss.ldap.type.group.prop.member");
			if (StringUtil.isNotNull(member)) {
				syncGroupRelations();
				temp = "同步群组成员信息到LDAP耗时(毫秒)："
						+ (System.currentTimeMillis() - caltime);
				logger.debug(temp);
				jobContext.logMessage(temp);
				jobContext.logMessage("");
			}
		}
	}

	private void deleteInvalidElements() throws Exception {
		long caltime = System.currentTimeMillis();
		String temp = "";

		if (checkSync("group")) {
			caltime = System.currentTimeMillis();
			deleteGroups();
			temp = "删除无效群组耗时(毫秒)："
					+ (System.currentTimeMillis() - caltime);
			logger.debug(temp);
			jobContext.logMessage(temp);
			jobContext.logMessage("");

		}

		if (checkSync("post")) {
			caltime = System.currentTimeMillis();
			deletePosts();
			temp = "删除无效群组耗时(毫秒)："
					+ (System.currentTimeMillis() - caltime);
			logger.debug(temp);
			jobContext.logMessage(temp);
			jobContext.logMessage("");

		}

		if (checkSync("person")) {
			caltime = System.currentTimeMillis();
			deletePersons();
			temp = "删除无效人员耗时(毫秒)："
					+ (System.currentTimeMillis() - caltime);
			logger.debug(temp);
			jobContext.logMessage(temp);
			jobContext.logMessage("");

		}

		if (checkSync("dept")) {
			caltime = System.currentTimeMillis();
			deleteDepts();
			temp = "删除无效部门耗时(毫秒)："
					+ (System.currentTimeMillis() - caltime);
			logger.debug(temp);
			jobContext.logMessage(temp);
			jobContext.logMessage("");
		}
	}

	private void handlePersonSync(Set<SysOrgPerson> persons) throws Exception {
		String size = "2000";
		int rowsize = Integer.parseInt(size);
		int count = persons.size() % rowsize == 0 ? persons.size() / rowsize
				: persons.size() / rowsize + 1;
		List<SysOrgPerson> allpersons = new ArrayList<SysOrgPerson>(persons);
		logger.debug("人员总数据:" + allpersons.size() + "条,将执行" + count
				+ "次人员分批同步,每次" + size + "条");
		CountDownLatch countDownLatch = new CountDownLatch(count);
		List<SysOrgPerson> temppersons = null;
		for (int i = 0; i < count; i++) {
			logger.debug("执行第" + (i + 1) + "批");
			if (persons.size() > rowsize * (i + 1)) {
				temppersons = allpersons.subList(rowsize * i,
						rowsize * (i + 1));
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

	private void syncDepts()
			throws Exception {
		TransactionStatus statu = null;
		Throwable t = null;
		try {
			statu = TransactionUtils
					.beginNewTransaction();
			List<SysOrgElement> depts = getData(ORG_TYPE_DEPT, true);
			Collections.sort(depts, new OrgTypeSort());
			SysOrgElement dept = null;
			for (int n = 0; n < depts.size(); n++) {
				dept = depts.get(n);
				try {
					ldapService.syncDept(dept);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					jobContext.logError(e.getMessage());
					throw e;
				}
			}
			String logInfo = "更新到LDAP的部门个数为:" + depts.size() + "条";
			logger.debug(logInfo);
			jobContext.logMessage(logInfo);
			TransactionUtils.getTransactionManager().commit(statu);
		} catch (Exception e) {
			t = e;
			throw e;
		} finally {
			if (t != null && statu != null) {
				TransactionUtils.rollback(statu);
			}
		}

	}

	private void syncPersons()
			throws Exception {
		if (!checkSync("person")) {
			return;
		}
		TransactionStatus statu = null;
		Throwable t = null;
		try {
			statu = TransactionUtils
					.beginNewTransaction();
			List<SysOrgElement> persons = getData(ORG_TYPE_PERSON, true);
			SysOrgPerson person = null;
			long count = 0L;

			for (int n = 0; n < persons.size(); n++) {
				person = (SysOrgPerson) persons.get(n);
				if("everyone".equals(person.getFdLoginName())||"elecvirtualperson".equals(person.getFdLoginName())||"admin".equals(person.getFdLoginName())){
					logger.info("内置账号不同步");
					continue;
				}
				try {
					ldapService.syncPerson(person);
					count++;
				} catch (Exception e) {
					logger.error("同步人员失败：", e);
				}
			}
			String logInfo = "同步到LDAP的人员个数为:" + count + "条";
			logger.debug(logInfo);
			jobContext.logMessage(logInfo);
			TransactionUtils.getTransactionManager().commit(statu);
		} catch (Exception e) {
			t = e;
			throw e;
		} finally {
			if (t != null && statu != null) {
				TransactionUtils.rollback(statu);
			}
		}
	}

	private void syncPosts()
			throws Exception {
		if (!checkSync("post")) {
			return;
		}
		TransactionStatus statu = null;
		Throwable t = null;
		try {
			statu = TransactionUtils
					.beginNewTransaction();
			List<SysOrgElement> posts = getData(ORG_TYPE_POST, true);
			SysOrgPost post = null;
			long count = 0L;

			for (int n = 0; n < posts.size(); n++) {
				post = (SysOrgPost) posts.get(n);
				try {
					ldapService.syncPost(post);
					count++;
				} catch (Exception e) {
					logger.error("同步岗位失败：", e);
				}
			}
			String logInfo = "同步到LDAP的岗位个数为:" + count + "条";
			logger.debug(logInfo);
			jobContext.logMessage(logInfo);
			TransactionUtils.getTransactionManager().commit(statu);
		} catch (Exception e) {
			t = e;
			throw e;
		} finally {
			if (t != null && statu != null) {
				TransactionUtils.rollback(statu);
			}
		}
	}

	private void syncGroups()
			throws Exception {
		if (!checkSync("group")) {
			return;
		}
		TransactionStatus statu = null;
		Throwable t = null;
		try {
			statu = TransactionUtils
					.beginNewTransaction();
			List<SysOrgElement> groups = getData(ORG_TYPE_GROUP, true);
			SysOrgGroup group = null;
			long count = 0L;

			for (int n = 0; n < groups.size(); n++) {
				group = (SysOrgGroup) groups.get(n);
				try {
					ldapService.syncGroup(group);
					count++;
				} catch (Exception e) {
					logger.error("同步群组失败：", e);
				}
			}
			String logInfo = "同步到LDAP的群组个数为:" + count + "条";
			logger.debug(logInfo);
			jobContext.logMessage(logInfo);
			TransactionUtils.getTransactionManager().commit(statu);
		} catch (Exception e) {
			t = e;
			throw e;
		} finally {
			if (t != null && statu != null) {
				TransactionUtils.rollback(statu);
			}
		}
	}

	private void terminate() throws Exception {

		if (currentSynchroTime != null) {
			LdapOmsConfig ldapOmsConfig = new LdapOmsConfig();
			ldapOmsConfig.setLastSynchroOutTime(currentSynchroTime);
			ldapOmsConfig.save();
		}
	}

	private void syncDeptRelations()
			throws Exception {
		List<SysOrgElement> depts = getData(ORG_TYPE_DEPT, true);
		Collections.sort(depts, new OrgTypeSort());
		SysOrgElement dept = null;
		for (int n = 0; n < depts.size(); n++) {
			dept = depts.get(n);
			try {
				ldapService.updateDeptRelation(dept);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				jobContext.logError(e.getMessage());
				throw e;
			}
		}
	}

	private void syncPersonRelations()
			throws Exception {
		List<SysOrgPerson> persons = getData(ORG_TYPE_PERSON, true);
		SysOrgPerson person = null;
		for (int n = 0; n < persons.size(); n++) {
			person = persons.get(n);
			if("everyone".equals(person.getFdLoginName())||"elecvirtualperson".equals(person.getFdLoginName())||"admin".equals(person.getFdLoginName())){
				logger.info("内置账号不同步");
				continue;
			}
			try {
				ldapService.updatePersonRelation(person);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				jobContext.logError(e.getMessage());
				throw e;
			}
		}
	}

	private void syncGroupRelations()
			throws Exception {
		List<SysOrgGroup> groups = getData(ORG_TYPE_GROUP, true);
		SysOrgGroup group = null;
		for (int n = 0; n < groups.size(); n++) {
			group = groups.get(n);
			try {
				ldapService.updateGroupRelation(group);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				jobContext.logError(e.getMessage());
				throw e;
			}
		}
	}

	class PersonRunner implements Runnable {
		private final List<SysOrgPerson> persons;

		public PersonRunner(List<SysOrgPerson> persons,
				Map<String, String> deptMapping) {
			this.persons = persons;
		}

		@Override
		public void run() {
			try {
				for (int n = 0; n < persons.size(); n++) {
					SysOrgPerson person = (SysOrgPerson) persons.get(n);
					ldapService.syncPerson(person);
				}
			} catch (Exception e) {
				logger.error("", e);
			} finally {
			}
		}
	}

	private void deleteDepts()
			throws Exception {
		TransactionStatus statu = null;
		Throwable t = null;
		try {
			statu = TransactionUtils
					.beginNewTransaction();
			List<SysOrgElement> depts = getData(ORG_TYPE_DEPT, false, true);
			// 下级部门先删
			Collections.sort(depts, new LdapDNSort());
			deleteElements(depts);
			TransactionUtils.getTransactionManager().commit(statu);
		} catch (Exception e) {
			t = e;
			throw e;
		} finally {
			if (t != null && statu != null) {
				TransactionUtils.rollback(statu);
			}
		}
	}

	private void deletePersons()
			throws Exception {
		TransactionStatus statu = null;
		Throwable t = null;
		try {
			statu = TransactionUtils
					.beginNewTransaction();
			List<SysOrgElement> persons = getData(ORG_TYPE_PERSON, false, true);
			// 下级部门先删
			deleteElements(persons);
			TransactionUtils.getTransactionManager().commit(statu);
		} catch (Exception e) {
			t = e;
			throw e;
		} finally {
			if (t != null && statu != null) {
				TransactionUtils.rollback(statu);
			}
		}
	}

	private void deletePosts()
			throws Exception {
		TransactionStatus statu = null;
		Throwable t = null;
		try {
			statu = TransactionUtils
					.beginNewTransaction();
			List<SysOrgElement> posts = getData(ORG_TYPE_POST, false, true);
			// 下级部门先删
			deleteElements(posts);
			TransactionUtils.getTransactionManager().commit(statu);
		}  catch (Exception e) {
			t = e;
			throw e;
		} finally {
			if (t != null && statu != null) {
				TransactionUtils.rollback(statu);
			}
		}
	}

	private void deleteGroups()
			throws Exception {
		TransactionStatus statu = null;
		Throwable t = null;
		try {
			statu = TransactionUtils
					.beginNewTransaction();
			List<SysOrgElement> groups = getData(ORG_TYPE_GROUP, false, true);
			// 下级部门先删
			deleteElements(groups);
			TransactionUtils.getTransactionManager().commit(statu);
		}  catch (Exception e) {
			t = e;
			throw e;
		} finally {
			if (t != null && statu != null) {
				TransactionUtils.rollback(statu);
			}
		}
	}

	private void deleteElements(List<SysOrgElement> eles) {
		SysOrgElement ele = null;
		for (int n = 0; n < eles.size(); n++) {
			ele = eles.get(n);
			try {
				ldapService.delete(ele);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				jobContext.logError(e.getMessage());
			}
		}
	}

	private List getData(int type, Boolean isAvailable) throws Exception {
		return getData(type, isAvailable, false);
	}

	/**
	 * @param type
	 * @return
	 * @throws Exception
	 *             根据传入的类型获取数据
	 */
	private List getData(int type, Boolean isAvailable, Boolean checkLdapDN)
			throws Exception {
		List rtnList = new ArrayList();
		HQLInfo info = new HQLInfo();
		String sql = "1=1";
		if (isAvailable != null) {
			sql += " and fdIsAvailable = " + (isAvailable ? "1" : "0");
		}
		if (checkLdapDN) {
			sql += " and fdLdapDN is not null";
		}
		if (lastSynchroTime != null) {
			sql += " and fdAlterTime>:beginTime";
			info.setParameter("beginTime", lastSynchroTime);
		}

		if (type == ORG_TYPE_PERSON) {
			info.setWhereBlock(
					sql);
			rtnList = sysOrgPersonService.findList(info);
		} else if (type == ORG_TYPE_ORG || type == ORG_TYPE_DEPT) {
			// info.setOrderBy("fdOrgType asc, LENGTH(fdHierarchyId) asc");
			info.setWhereBlock(sql + " and fdOrgType in (" + ORG_TYPE_ORG + ","
					+ ORG_TYPE_DEPT + ")");
			rtnList = sysOrgElementService.findList(info);
		} else if (type == ORG_TYPE_POST) {
			info.setWhereBlock(sql);
			rtnList = sysOrgPostService.findList(info);
		} else if (type == ORG_TYPE_GROUP) {
			// info.setOrderBy("fdOrgType asc, LENGTH(fdHierarchyId) asc");
			info.setWhereBlock(sql + " and fdOrgType =" + type);
			rtnList = sysOrgGroupService.findList(info);
		}
		return rtnList;
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

	private boolean checkSync(String type) throws Exception {
		LdapDetailConfig config = new LdapDetailConfig();
		String sync = config.getValue("kmss.ldap.config." + type + ".check");
		if ("true".equals(sync)) {
			return true;
		}
		return false;
	}

	private ISysOrgPostService sysOrgPostService;

	public ISysOrgPostService getSysOrgPostService() {
		return sysOrgPostService;
	}

	public void setSysOrgPostService(ISysOrgPostService sysOrgPostService) {
		this.sysOrgPostService = sysOrgPostService;
	}

	public ISysOrgGroupService getSysOrgGroupService() {
		return sysOrgGroupService;
	}

	public void setSysOrgGroupService(ISysOrgGroupService sysOrgGroupService) {
		this.sysOrgGroupService = sysOrgGroupService;
	}

	private ISysOrgGroupService sysOrgGroupService;

}
