package com.landray.kmss.third.feishu.oms;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.landray.kmss.third.feishu.model.ThirdFeishuDeptMapping;
import com.landray.kmss.third.feishu.model.ThirdFeishuPersonMapping;
import com.landray.kmss.third.feishu.service.IThirdFeishuPersonMappingService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.feishu.interfaces.ApiException;
import com.landray.kmss.third.feishu.model.ThirdFeishuConfig;
import com.landray.kmss.third.feishu.service.IThirdFeishuDeptMappingService;
import com.landray.kmss.third.feishu.service.IThirdFeishuService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;

public class SynchroOrg2FeishuImp implements SynchroOrg2Feishu,
		SysOrgConstant {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SynchroOrg2FeishuImp.class);

	private SysQuartzJobContext jobContext = null;

	private String lastSynchroTime = null;

	private String currentSynchroTime = null;

	private IThirdFeishuService thirdFeishuService;

	private IThirdFeishuDeptMappingService thirdFeishuDeptMappingService;

	public void setThirdFeishuPersonMappingService(IThirdFeishuPersonMappingService thirdFeishuPersonMappingService) {
		this.thirdFeishuPersonMappingService = thirdFeishuPersonMappingService;
	}

	private IThirdFeishuPersonMappingService thirdFeishuPersonMappingService;

	/**
	 * 需要同步的顶级组织，key为组织fdId，value为组织层级id
	 */
	private Map<String,String> syncRootIdsMap = new HashMap<>();

	// private ThreadPoolTaskExecutor taskExecutor;
	//
	// public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
	// this.taskExecutor = taskExecutor;
	// }

	public IThirdFeishuDeptMappingService getThirdFeishuDeptMappingService() {
		return thirdFeishuDeptMappingService;
	}

	public void setThirdFeishuDeptMappingService(
			IThirdFeishuDeptMappingService thirdFeishuDeptMappingService) {
		this.thirdFeishuDeptMappingService = thirdFeishuDeptMappingService;
	}

	private static boolean locked = false;

	@Override
	public void triggerSynchro(SysQuartzJobContext context) {
		String temp = "";
		this.jobContext = context;
		if (locked) {
			temp = "存在运行中的飞书组织架构同步任务，当前任务中断...";
			logger.info(temp);
			context.logMessage(temp);
			return;
		}
		if (!"true"
				.equals(ThirdFeishuConfig.newInstance().getFeishuEnabled())) {
			temp = "飞书集成已经关闭，故不同步数据";
			logger.info(temp);
			context.logMessage(temp);
			return;
		}

		if (!"true".equals(
				ThirdFeishuConfig.newInstance().getSynchroOrg2Feishu())) {
			temp = "飞书组织架构接出已经关闭，故不同步数据";
			logger.debug(temp);
			context.logMessage(temp);
			return;
		}

		try {
			locked = true;

			currentSynchroTime = DateUtil.convertDateToString(new Date(),
					"yyyy-MM-dd HH:mm:ss.SSS");
			FeishuOmsConfig feishuOmsConfig = new FeishuOmsConfig();
			lastSynchroTime = feishuOmsConfig.getLastSynchroTime();
			temp = "==========开始同步组织数据到飞书===============同步时间戳为："
					+ lastSynchroTime;
			logger.debug(temp);
			context.logMessage(temp);
			context.logMessage("");

			long alltime = System.currentTimeMillis();

			long caltime = System.currentTimeMillis();
			Set<String> userIds_noSync = checkData(context);
			temp = "校验数据耗时(毫秒)："
					+ (System.currentTimeMillis() - caltime);
			logger.debug(temp);
			context.logMessage(temp);
			context.logMessage("");

			syncRootIdsMap = thirdFeishuService.getAllSyncRootIdsMap();

			//处理范围外的数据，先删除范围外的部门映射（否则处理一人多部门时，部门id会关联到范围外的部门），如果范围外人员设置的删除或禁用
			JSONArray outRangeDept = outRangeHandle();

			Map<String, String> deptMapping = new HashMap<String, String>();

			List<Object[]> mapps = thirdFeishuDeptMappingService
					.findValue("fdEkp.fdId,fdFeishuId", "", null);
			for (Object[] mapp : mapps) {
				String ekpId = (String) mapp[0];
				String feishuId = (String) mapp[1];
				deptMapping.put(ekpId, feishuId);
			}

			// 新增部门到飞书
			caltime = System.currentTimeMillis();
			addDepts(deptMapping);
			temp = "新增部门到飞书耗时(毫秒)："
					+ (System.currentTimeMillis() - caltime);
			logger.debug(temp);
			context.logMessage(temp);
			context.logMessage("");

			// 更新飞书的人员数据
			caltime = System.currentTimeMillis();
			syncPersons(userIds_noSync, deptMapping);
			temp = "同步人员到飞书耗时(毫秒)："
					+ (System.currentTimeMillis() - caltime);
			logger.debug(temp);
			context.logMessage(temp);
			context.logMessage("");

			caltime = System.currentTimeMillis();
			temp = "获取飞书员工账号信息耗时(毫秒)："
					+ (System.currentTimeMillis() - caltime);
			logger.debug(temp);
			context.logMessage(temp);
			context.logMessage("");

			// 更新部门到飞书
			caltime = System.currentTimeMillis();
			updateDepts(deptMapping);
			temp = "更新部门到飞书耗时(毫秒)："
					+ (System.currentTimeMillis() - caltime);
			logger.debug(temp);
			context.logMessage(temp);
			context.logMessage("");

			caltime = System.currentTimeMillis();
			deleteDepts(outRangeDept);
			temp = "删除部门耗时(毫秒)："
					+ (System.currentTimeMillis() - caltime);
			logger.debug(temp);
			context.logMessage(temp);
			context.logMessage("");

			terminate();
			temp = "整个任务总耗时(毫秒)：" + (System.currentTimeMillis() - alltime);
			logger.debug(temp);
			context.logMessage(temp);
		} catch (Exception ex) {
			logger.error("飞书组织架构同步任务失败" + ex);
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

	private void addDepts(Map<String, String> deptMapping)
			throws Exception {
		List<SysOrgElement> depts = getData(ORG_TYPE_DEPT, true);
		// String logInfo = null;
		SysOrgElement dept = null;
		// System.out.println("部门数：" + depts.size());
		// long count = 0l;
		for (int n = 0; n < depts.size(); n++) {
			dept = depts.get(n);
			if (!checkRootNeedSync(dept.getFdId())) {
				continue;
			}
			try {
				thirdFeishuService.syncDept(dept, true, deptMapping);
			} catch (Exception e) {
				logger.error("", e);
				jobContext.logError(e.getMessage());
			}
		}
	}


	private void updateDepts(Map<String, String> deptMapping)
			throws Exception {
		List<SysOrgElement> depts = getData(ORG_TYPE_DEPT, true);
		String logInfo = null;
		SysOrgElement dept = null;
		long count = 0L;
		for (int n = 0; n < depts.size(); n++) {
			dept = depts.get(n);
			if (!checkRootNeedSync(dept.getFdId())) {
				continue;
			}
			thirdFeishuService.syncDept(dept, false, deptMapping);
			count++;
		}
		logInfo = "更新到飞书的部门个数为:" + count + "条";
		logger.debug(logInfo);
		jobContext.logMessage(logInfo);
	}

	private void deleteDepts(JSONArray outRangeDept) throws Exception {
		List<Object> depts = getData(ORG_TYPE_DEPT, false);
		deleteDeptsCircul(depts);

		if(outRangeDept!=null){
			List<Object> feishuDeptIds = new ArrayList<>();
			for(int i=0;i<outRangeDept.size();i++){
				feishuDeptIds.add(outRangeDept.getJSONObject(i).getString("feishuId"));
			}
			deleteDeptsCircul(feishuDeptIds);
		}
	}

	private void deleteDeptsCircul(List<Object> depts) throws Exception {
		String logInfo = null;
		Object dept = null;
		long count = 0L;
		List<Object> synchroFails = new ArrayList<Object>();
		for (int i = 0; i < 8; i++) {
			if (depts == null || depts.isEmpty()) {
				break;
			}
			for (int n = 0; n < depts.size(); n++) {
				dept = depts.get(n);
				try {
					if(dept instanceof SysOrgElement) {
						thirdFeishuService.delDept((SysOrgElement) dept);
					}else if(dept instanceof String){
						thirdFeishuService.delDept((String) dept);
					}
					count++;
				} catch (ApiException e) {
					synchroFails.add(dept);
				} catch (Exception e) {
					throw e;
				}
			}
			depts = synchroFails;
			synchroFails = new ArrayList<Object>();
		}
		logInfo = "删除的部门个数为:" + count + "条";
		logger.debug(logInfo);
		jobContext.logMessage(logInfo);
	}

	private void syncPersons(Set<String> userIds_noSync,
			Map<String, String> deptMapping)
			throws Exception {
		List<SysOrgElement> persons = getData(ORG_TYPE_PERSON, null);
		String logInfo = null;
		SysOrgPerson person = null;
		long count = 0L;
		
		for (int n = 0; n < persons.size(); n++) {
			person = (SysOrgPerson) persons.get(n);
			try {
				if (userIds_noSync.contains(person.getFdId())) {
					continue;
				}
				thirdFeishuService.syncPerson(person, deptMapping);
				count++;
			} catch (ApiException e) {
				logger.error("同步人员失败：", e);
			}
		}
		logInfo = "同步到飞书的人员个数为:" + count + "条";
		logger.debug(logInfo);
		jobContext.logMessage(logInfo);
	}


	private void terminate() throws Exception {
		if (StringUtil.isNotNull(currentSynchroTime)) {
			FeishuOmsConfig feishuOmsConfig = new FeishuOmsConfig();
			feishuOmsConfig.setLastSynchroTime(currentSynchroTime);
			ThirdFeishuConfig config = new ThirdFeishuConfig();
			String currentSynchroEkpRootId = config.getSynchroOrg2FeishuEkpRootId();
			feishuOmsConfig.setLastSynchroEkpRootId(currentSynchroEkpRootId);
			feishuOmsConfig.save();
		}
	}


	class PersonRunner implements Runnable {
		private final List<SysOrgPerson> persons;
		private final Map<String, String> deptMapping;

		public PersonRunner(List<SysOrgPerson> persons,
				Map<String, String> deptMapping) {
			this.persons = persons;
			this.deptMapping = deptMapping;
		}

		@Override
		public void run() {
			try {
				for (int n = 0; n < persons.size(); n++) {
					SysOrgPerson person = (SysOrgPerson) persons.get(n);
					thirdFeishuService.syncPerson(person, deptMapping);
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
	private List getData(int type, Boolean isAvailable) throws Exception {
		List rtnList = new ArrayList();
		HQLInfo info = new HQLInfo();
		String sql = "fdIsExternal=0";
		if (isAvailable != null) {
			sql += " and fdIsAvailable = " + (isAvailable ? "1" : "0");
		}

		if(isAvailable==null || isAvailable==true){
			String scopeLikeBlock = null;
			if (type == ORG_TYPE_PERSON) {
				scopeLikeBlock = buildSynchroPersonScopeBlock(info);
			}else if (type == ORG_TYPE_DEPT) {
				scopeLikeBlock = buildSynchroDeptScopeBlock();
			}
			if(StringUtil.isNotNull(scopeLikeBlock)){
				sql += " and "+scopeLikeBlock;
			}
		}
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

	public IThirdFeishuService getThirdFeishuService() {
		return thirdFeishuService;
	}

	public void setThirdFeishuService(IThirdFeishuService thirdFeishuService) {
		this.thirdFeishuService = thirdFeishuService;
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

	/**
	 * 获取所有根组织
	 * @throws Exception
	 */
	private Set<String> findRootDepts() throws Exception {
		if(syncRootIdsMap!=null && !syncRootIdsMap.isEmpty()){
			return syncRootIdsMap.keySet();
		}
		// 查询所有根机构
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdId");
		hqlInfo.setWhereBlock(" fd_parentid = null and fd_is_available = 1 and fd_is_business = 1 and (fd_org_type = 1 or fd_org_type = 2)");
		List<String> depts = sysOrgElementService.findList(hqlInfo);
		return new HashSet<>(depts);
	}

	/**
	 * 判断是否是顶级部门，以及是否启用了顶级部门同步
	 * @param rootId
	 * @return
	 */
	private boolean checkRootNeedSync(String rootId) {
		if(syncRootIdsMap==null || syncRootIdsMap.isEmpty()){
			return true;
		}
		if ("true".equals(ThirdFeishuConfig.newInstance().getSynchroOrg2FeishuEkpRootSync())) {
			return true;
		} else if (ThirdFeishuConfig.newInstance().getSynchroOrg2FeishuEkpRootId().contains(rootId)) {
			return false;
		}
		return true;
	}


	/**
	 * 构建同步范围where条件，范围外的无效记录也需要更新
	 * @return
	 * @throws Exception
	 */
	private String buildSynchroPersonScopeBlock(HQLInfo info) throws Exception {
		if(syncRootIdsMap==null || syncRootIdsMap.isEmpty()){
			return "";
		}
		String likeBlock = "";
		for(String hierId:syncRootIdsMap.values()){
			likeBlock += " or fdHierarchyId like '"+hierId+"%'";
		}
		likeBlock = "("+likeBlock.substring(4)+" or fdIsAvailable = :avail)";
		info.setParameter("avail",false);
		return likeBlock;
	}

	private String buildSynchroDeptScopeBlock() throws Exception {
		if(syncRootIdsMap==null || syncRootIdsMap.isEmpty()){
			return "";
		}
		String likeBlock = "";
		for(String hierId:syncRootIdsMap.values()){
			likeBlock += " or fdHierarchyId like '"+hierId+"%'";
		}
		likeBlock = "("+likeBlock.substring(4)+")";
		return likeBlock;
	}

	/**
	 * 同步范围是否发生了改变
	 * @return
	 * @throws Exception
	 */
	private boolean isSynchroRangeChange() throws Exception {
		FeishuOmsConfig omsConfig = new FeishuOmsConfig();
		ThirdFeishuConfig config = new ThirdFeishuConfig();
		String lastSynchroEkpRootId = omsConfig.getLastSynchroEkpRootId();
		String currentSynchroEkpRootId = config.getSynchroOrg2FeishuEkpRootId();
		if(StringUtil.isNull(lastSynchroEkpRootId)){
			if(StringUtil.isNull(currentSynchroEkpRootId)){
				return false;
			}else{
				return true;
			}
		}else{
			if(StringUtil.isNull(currentSynchroEkpRootId)){
				return true;
			}else{
				String[] lastSynchroEkpRootIds = lastSynchroEkpRootId.split(";");
				String[] currentSynchroEkpRootIds = currentSynchroEkpRootId.split(";");
				if(lastSynchroEkpRootIds.length != currentSynchroEkpRootIds.length){
					return true;
				}
				Set<String> lastSynchroEkpRootIdsSet = new HashSet<>(Arrays.asList(lastSynchroEkpRootIds));
				Set<String> currentSynchroEkpRootIdsSet = new HashSet<>(Arrays.asList(currentSynchroEkpRootIds));
				if(lastSynchroEkpRootIdsSet.size()!=currentSynchroEkpRootIdsSet.size()){
					return true;
				}
				for(String rootId:currentSynchroEkpRootIdsSet){
					if(!lastSynchroEkpRootIdsSet.contains(rootId)){
						return true;
					}
				}
				return false;
			}
		}
	}

	private List<ThirdFeishuPersonMapping> getOutRangePersonMappings() throws Exception {
		String scopeBlock = buildSynchroDeptScopeBlock();
		//如果当前没有设置范围，则不存在需要删除的范围外记录
		if(StringUtil.isNull(scopeBlock)){
			return new ArrayList();
		}
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdEkp.fdId not in (select fdId from com.landray.kmss.sys.organization.model.SysOrgElement sysOrgElement where fd_org_type=8 and "+scopeBlock+")");
		List<ThirdFeishuPersonMapping> mapps = thirdFeishuPersonMappingService.findList(info);
		return mapps;
	}

	private JSONArray outRangeHandle() throws Exception {
		ThirdFeishuConfig config = ThirdFeishuConfig.newInstance();
		String outRangeHandle = config.getSynchroOrg2FeishuOutRangePersonHandle();
		if(StringUtil.isNull(outRangeHandle) || "0".equals(outRangeHandle)){
			return null;
		}else if("1".equals(outRangeHandle)){
			if(isSynchroRangeChange()){
				// 同步范围改变，执行全量同步
				lastSynchroTime = null;
			}
			if(lastSynchroTime==null){
				List<ThirdFeishuPersonMapping> mapps = getOutRangePersonMappings();
				for(ThirdFeishuPersonMapping mapp:mapps){
					thirdFeishuService.disablePerson(mapp);
				}
			}
		}else if("2".equals(outRangeHandle)){
			if(isSynchroRangeChange()){
				// 同步范围改变，执行全量同步
				lastSynchroTime = null;
			}
			if(lastSynchroTime==null){
				List<ThirdFeishuPersonMapping> mapps = getOutRangePersonMappings();
				for(ThirdFeishuPersonMapping mapp:mapps){
					thirdFeishuService.delPerson(mapp);
				}
				//先删除映射表，最后才执行删除部门，因为需要先把部门下的数据迁移出来
				JSONArray array = new JSONArray();
				List<ThirdFeishuDeptMapping> deptMapps = getOutRangeDeptMappings();
				for(ThirdFeishuDeptMapping deptMapping:deptMapps){
					JSONObject o = new JSONObject();
					o.put("feishuId",deptMapping.getFdFeishuId());
					o.put("feishuName",deptMapping.getFdFeishuName());
					o.put("ekpId",deptMapping.getFdEkp().getFdId());
					array.add(o);
					thirdFeishuDeptMappingService.delete(deptMapping);
				}
				return array;
			}
		}
		return null;
	}

	private List<ThirdFeishuDeptMapping> getOutRangeDeptMappings() throws Exception {
		String scopeBlock = buildSynchroDeptScopeBlock();
		//如果当前没有设置范围，则不存在需要删除的范围外记录
		if(StringUtil.isNull(scopeBlock)){
			return new ArrayList();
		}
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdEkp.fdId not in (select fdId from com.landray.kmss.sys.organization.model.SysOrgElement sysOrgElement where (fd_org_type=1 or fd_org_type=2) and "+scopeBlock+")");
		List<ThirdFeishuDeptMapping> mapps = thirdFeishuDeptMappingService.findList(info);
		return mapps;
	}

}
