package com.landray.kmss.hr.organization.oms;

import com.google.common.base.Joiner;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.hr.organization.constant.HrOrgConstant;
import com.landray.kmss.hr.organization.model.HrOrganizationDept;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationOrg;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.authorization.util.TripartiteAdminUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.TransactionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <P>同步所有EKP组织架构数据。手动触发</P>
 * @author sunj
 * @version 1.0 2020年3月31日
 */
public class SynchroAllEkpToHrImp  extends SynchroOrgCommon implements SysOrgConstant, HrOrgConstant {

	private static boolean locked = false;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SynchroAllEkpToHrImp.class);

	private SysQuartzJobContext jobContext = null;

	private IHrOrganizationElementService hrOrganizationElementService;

	public void setHrOrganizationElementService(IHrOrganizationElementService hrOrganizationElementService) {
		this.hrOrganizationElementService = hrOrganizationElementService;
	}

	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	public void setHrStaffPersonInfoService(IHrStaffPersonInfoService hrStaffPersonInfoService) {
		this.hrStaffPersonInfoService = hrStaffPersonInfoService;
	}

	private ISysOrgPersonService sysOrgPersonService;

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	private ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	private ThreadPoolTaskExecutor taskExecutor;

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	
	private CountDownLatch addNotPesonLatch;//新增非人员(组织、架构、岗位)的线程的计数器
	private CountDownLatch updateNotPesonLatch;//更新非人员(组织、架构、岗位)数据的计数器
	private CountDownLatch addPesonLatch;//新增人员的线程的计数器
	private CountDownLatch updatePesonLatch;//更新人员数据的计数器
	
	//List<RunStatusData> runExeptionList = new CopyOnWriteArrayList<RunStatusData>();
	
	long addNotPesonStartTime = 0,updateNotPesonStartTime = 0,addPesonStartTime = 0,updatePesonStartTime = 0;
	private Map<String,String> mapLog = new ConcurrentHashMap<String,String>();//记录各个阶段的日志
	int rowsize = 5000;
	AtomicInteger allCount = new AtomicInteger(0);

	/**********************************
	 * 执行不同任务类型的线程
	 * orgAdd,orgUpdate,PersonAdd,PersonUpdate	
	 * @author caoyong
	 *
	 */
	class SyncTask implements Runnable {
		private final List<SysOrgElement> elements;
		String runStatusData;

		public SyncTask(List<SysOrgElement> elements,String runStatusData) {
			this.elements = elements;
			this.runStatusData = runStatusData;
		}

	
		@Override
		public void run() {
			try {
				logger.info("启动线程"+ runStatusData +"线程名:" + Thread.currentThread().getName());
				if("orgAdd".equals(runStatusData)) {
					addNotPesonStartTime = addNotPesonStartTime ==0 ? System.currentTimeMillis() : addNotPesonStartTime;
					addArchitectureData(this.elements,true);
				}else if("orgUpdate".equals(runStatusData)) {
					updateNotPesonStartTime = updateNotPesonStartTime ==0 ? System.currentTimeMillis() : updateNotPesonStartTime;
					logTime("orgAdd");//记录一下新增完成的时间
					updateArchitectureData(this.elements,true);
				}else if("personAdd".equals(runStatusData)) {
					logTime("orgUpdate");//记录一下更新的时间
					addPesonStartTime = addPesonStartTime ==0 ? System.currentTimeMillis() : addPesonStartTime;
					addArchitectureData(this.elements,false);
				}else if("personUpdate".equals(runStatusData)) {
					updatePesonStartTime = updatePesonStartTime ==0 ? System.currentTimeMillis() : updatePesonStartTime;
					logTime("personAdd");//记录一下更新的时间
					updateArchitectureData(this.elements,false);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("", e);
			} finally {
				if("orgAdd".equals(runStatusData)) {
					addNotPesonLatch.countDown();//不管是对是错都减1
				}else if("orgUpdate".equals(runStatusData)) {
					updateNotPesonLatch.countDown();
				}else if("personAdd".equals(runStatusData)) {
					addPesonLatch.countDown();
				}else if("personUpdate".equals(runStatusData)) {
					updatePesonLatch.countDown();
				}
				logger.info("线程" + Thread.currentThread().getName() + "执行完成!");
			}
		}
	}
	
	/********************************
	 * 每当上一个阶段的线程执行完毕记录一次耗时 
	 * A B C D E 5个类型
	 *   A B C D 对应记录时间
	 * A阶段执行完到B阶段
	 * @param currRunType
	 */
	public void logTime(String currRunType) {
		String temp ="";
		if("orgAdd".equals(currRunType)) {
			if(addNotPesonStartTime != 0) {//这个时间存在的话
				temp = "本次新增非人员（组织、架构、部门、岗位）数据耗时(秒)：" + (System.currentTimeMillis() - addNotPesonStartTime) / 1000;
			}
		}else if("orgUpdate".equals(currRunType)) {
			if(updateNotPesonStartTime != 0) {
				temp = "本次更新非人员（组织、架构、部门、岗位）数据耗时(秒)：" + (System.currentTimeMillis() - updateNotPesonStartTime) / 1000;
			}
		}else if("personAdd".equals(currRunType)) {
			if(addPesonStartTime != 0) {//这个时间存在的话
				temp = "本次共新增人员数据耗时(秒)：" + ( System.currentTimeMillis() - addPesonStartTime) / 1000;
			}
		}else if("personUpdate".equals(currRunType)) {
			if(updatePesonStartTime != 0) {//这个时间存在的话
				temp = "本次共更新人员数据耗时(秒)：" + (System.currentTimeMillis() - updatePesonStartTime) / 1000;
			}
		}
		if(mapLog.get(currRunType) ==null && !"".equals(temp) ) {
			mapLog.put(currRunType, temp);
			logger.info(temp);
			jobContext.logMessage(temp);
		}
	}
	private List<Integer> orgElementNoPersonTypes =null;
	/***
	 * 全量同步ekp的组织和人员
	 * 第一步：同步组织，考虑到外键等因素，先全部插入新增的数据，并且将有各种外键的数据添加到一个list中
	 * 然后对这个list 一起去做个更新
	 * 
	 * 第二步：在第一步完成后，同步人员
	 * @param context
	 */
	public void synchroEkpToHr(SysQuartzJobContext context) {
		String temp = null;
		this.jobContext = context;
		if (locked) {
			temp = "存在运行中的EKP组织架构到人事组织架构同步任务，当前任务中断...";
			logger.error(temp);
			jobContext.logError(temp);
			return;
		}
		relaxSource();
		locked = true;
		log("开始EKP组织架构到人事组织架构同步....");
		try {
			temp = "==========开始同步EKP组织数据到人事组织架构===============";
			logger.info(temp);
			context.logMessage(temp);
			long startTime = System.currentTimeMillis();
			if(orgElementNoPersonTypes ==null){
				orgElementNoPersonTypes =new ArrayList<>();
				orgElementNoPersonTypes.add(SysOrgConstant.ORG_TYPE_ORG);
				orgElementNoPersonTypes.add(SysOrgConstant.ORG_TYPE_DEPT);
				orgElementNoPersonTypes.add(SysOrgConstant.ORG_TYPE_POST);
			}
			//第一步 同步非人员(组织、架构、岗位)
			synchroEkpToHrOrg(context);
			//第二步 同步人员
			synchroEkpToHrPerson(context);
			temp = "整个任务总耗时(秒)：" + (System.currentTimeMillis() - startTime) / 1000;
			logger.info(temp);
			context.logMessage(temp);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("", ex);
			if (context != null) {
				context.logError(ex);
			}
		} finally {
			locked = false;
		}
	}
	
	/***
	 * 全量同步ekp的组织和人员第一步：同步组织，考虑到外键等因素，先全部插入新增的数据，并且
	 * 将有各种外键的数据添加到一个list中然后对这个list 一起去做个更新
	 * @param context
	 */
	public void synchroEkpToHrOrg(SysQuartzJobContext context) throws Exception{
		try {
			//获取需要新增EKP组织数据
			List<SysOrgElement> needAddNotPersonList = getNeedAddListData(true);
			List<List<SysOrgElement>> needOrgAddGroup = splitList(needAddNotPersonList,rowsize);
			
			List<SysOrgElement> needUpdateNotPerSonList = getSysElementsData(true, "1=1");
			List<List<SysOrgElement>> needOrgUpdateGroup = splitList(needUpdateNotPerSonList,rowsize);
			int addNum = needOrgAddGroup.size(),updateNum = needOrgUpdateGroup.size();

			String logInfo = "本次新增组织、架构、部门、岗位 数据:" + needAddNotPersonList.size() + "条,将执行" + addNum + "次分批同步,每次" + rowsize + "条";
			if(needAddNotPersonList.size() > 0) {
				logger.info(logInfo);
				jobContext.logMessage(logInfo);
				addNotPesonLatch = new CountDownLatch(addNum);//计数器初始化
				for (int i = 0; i < addNum; i++) {
					taskExecutor.execute(new SyncTask(needOrgAddGroup.get(i),"orgAdd"));
				}
				addNotPesonLatch.await();
				logTime("orgAdd");//记录一下更新的时间
			}
			if(needUpdateNotPerSonList.size() > 0) {
				logInfo = "本次更新组织、架构、部门、岗位 数据:" + needUpdateNotPerSonList.size() + "条,将执行" + updateNum + "次分批同步,每次" + rowsize + "条";
				logger.info(logInfo);
				jobContext.logMessage(logInfo);
				updateNotPesonLatch = new CountDownLatch(updateNum);//计数器初始化
				for (int j = 0; j < updateNum; j++) {
					taskExecutor.execute(new SyncTask(needOrgUpdateGroup.get(j),"orgUpdate"));
				}
				updateNotPesonLatch.await();
				logTime("orgUpdate");//记录一下更新的时间
			}
			
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.error("", ex);
			if (context != null) {
				context.logError(ex);
			}
			throw ex;
		} 
	}
	
	
	/***
	 * 第二步：在第一步完成后，同步人员
	 * @param context
	 */
	public void synchroEkpToHrPerson(SysQuartzJobContext context) throws Exception {
		try {
			//获取需要新增EKP组织数据
			List<SysOrgElement> needAddPersonList = getNeedAddListData(false);
			List<List<SysOrgElement>> needPersonAddGroup = splitList(needAddPersonList,rowsize);
			
			List<SysOrgElement> needUpdatePerSonList = getSysElementsData(false, "1=1");
			List<List<SysOrgElement>> needPersonUpdateGroup = splitList(needUpdatePerSonList,rowsize);
			int addNum = needPersonAddGroup.size(),updateNum = needPersonUpdateGroup.size();


			String logInfo = "本次新增人员数据:" + needAddPersonList.size() + "条,将执行" + addNum + "次分批同步,每次" + rowsize + "条";
			if(needAddPersonList.size() > 0) {
				logger.info(logInfo);
				jobContext.logMessage(logInfo);
				addPesonLatch = new CountDownLatch(addNum);//计数器初始化
				for (int i = 0; i < addNum; i++) {
					taskExecutor.execute(new SyncTask(needPersonAddGroup.get(i),"personAdd"));
				}
				addPesonLatch.await();
				//最后执行一下记录结束更新时间
				logTime("personAdd");//记录一下更新的时间
			}
			if(needUpdatePerSonList.size() > 0) {
				logInfo = "本次更新人员数据:" + needUpdatePerSonList.size() + "条,将执行" + updateNum + "次分批同步,每次" + rowsize + "条";
				logger.info(logInfo);
				jobContext.logMessage(logInfo);
				updatePesonLatch = new CountDownLatch(updateNum);//计数器初始化
				for (int j = 0; j < updateNum; j++) {
					taskExecutor.execute(new SyncTask(needPersonUpdateGroup.get(j),"personUpdate"));
				}
				updatePesonLatch.await();
				//最后执行一下记录结束更新时间
				logTime("personUpdate");//记录一下更新的时间
			}

		}catch (Exception ex) {
			ex.printStackTrace();
			logger.error("", ex);
			if (context != null) {
				context.logError(ex);
			}
			throw ex;
		} 
	}


	/**
	 * 查询EKP中更新时间内需要新增的HR表的fdId
	 * @param isNotPerson 是否是非人员(组织、架构、岗位)
	 * @return
	 * @throws Exception
	 */
	private List<SysOrgElement> getNeedAddListData(boolean isNotPerson) throws Exception {
		long startTime = System.currentTimeMillis();
		String temp = "获取需要新增人员数据耗时(秒)：" ;
		List<SysOrgElement> needAddOrgList = new ArrayList<SysOrgElement>();
		StringBuilder sql = new StringBuilder();
		sql.append(" select e.fd_id from sys_org_element e left join hr_org_element se on e.fd_id=se.fd_id");
		sql.append(" where se.fd_id is null ");// 表示HR组织中没有的数据，则时新增
		sql.append(" and e.fd_is_external !=true ");//过滤掉外部组织
		boolean isPerson= false;
		if (isNotPerson) {
			sql.append(" and e.fd_org_type in ("+Joiner.on(",").join(orgElementNoPersonTypes)+") ");//查询出非人员(组织、架构、岗位)类型的
			temp  = "获取需要新增非人员（组织、架构、部门、岗位）数据耗时(秒)：" ;
		}else {
			sql.append(" and e.fd_org_type in ("+SysOrgConstant.ORG_TYPE_PERSON+") ");
			isPerson = true;
		}
		sql.append(" order by e.fd_alter_time asc ");
		List<String> idsList = hrOrganizationElementService.getIdByJdbc(sql.toString(),new ArrayList<>());
		if(isPerson){
			//排除不需要同步的人员
			idsList =getAllNeedPerson(idsList);
		}
		if(CollectionUtils.isNotEmpty(idsList)) {
			String[] ids = idsList.toArray(new String[idsList.size()]);
			needAddOrgList  =sysOrgElementService.findByPrimaryKeys(ids);
		}
		if (CollectionUtils.isEmpty(needAddOrgList)) {
			needAddOrgList =new ArrayList<SysOrgElement>();
		}else {
			temp = temp + (System.currentTimeMillis() - startTime) / 1000;
			logger.info(temp);
			jobContext.logMessage(temp);
		}
		return needAddOrgList;
	} 
	
	private List<List<SysOrgElement>> splitList(List<SysOrgElement> messagesList, int groupSize) {
	        int length = messagesList.size();
	        // 计算可以分成多少组
	        int num = (length + groupSize - 1) / groupSize; // TODO
	        List<List<SysOrgElement>> newList = new ArrayList<>(num);
	        for (int i = 0; i < num; i++) {
	            // 开始位置
	            int fromIndex = i * groupSize;
	            // 结束位置
	            int toIndex = (i + 1) * groupSize < length ? (i + 1) * groupSize : length;
	            newList.add(messagesList.subList(fromIndex, toIndex));
	        }
	        return newList;
	}


	/*************************************************************************************
	 * <p>新增人事组织数据</p>
	 * @param elements 元素
	 * @param isNotPerson 是否是非人员(组织、架构、岗位)数据
	 * @throws Exception
	 * @author caoy
	 *******************************************************************************/
	private void addArchitectureData(List<SysOrgElement> elements,boolean isNotPerson) throws Exception {

		HrOrganizationElement hrOrganization = null;
		if (!ArrayUtil.isEmpty(elements)) {
			int size = elements.size();
			for (int i = 0; i < size; i++) {
				TransactionStatus status = null;
				SysOrgElement element = elements.get(i);
				String decerption = "人员:";
				boolean isException =false;
				try {
					if (null == element) {
						jobContext.logMessage("EKP组织架构数据为空，不执行新增！");
						continue;
					}
					status = TransactionUtils.beginNewTransaction();

					if(isNotPerson) {//非人员(组织、架构、岗位)
						if (element.getFdOrgType().equals(ORG_TYPE_ORG)) {
							hrOrganization = new HrOrganizationOrg();
						} else if (element.getFdOrgType().equals(ORG_TYPE_DEPT)) {
							hrOrganization = new HrOrganizationDept();
						} else if (element.getFdOrgType().equals(ORG_TYPE_POST)) {
							hrOrganization = new HrOrganizationPost();
						} 
						decerption = "机构、部门、岗位:";
					}else {//人员
						if (element.getFdOrgType().equals(ORG_TYPE_PERSON)) {
							hrOrganization = new HrStaffPersonInfo();
							element = (SysOrgElement) sysOrgPersonService.findByPrimaryKey(element.getFdId(), null, true);
						}
					}
                    if(hrOrganization != null) {
                    	setHierarchy(hrOrganization, element);
                    	hrOrganizationElementService.update(hrOrganization);
                    	jobContext.logMessage("增加数据"+ decerption + " fdName=" + element.getFdName() + ",fdId=" + element.getFdId());
                    	allCount.incrementAndGet();
                    }
					TransactionUtils.getTransactionManager().commit(status);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(Thread.currentThread().getName() + "新增人事组织架构", e);
					jobContext.logMessage(Thread.currentThread().getName() + "增加数据" + decerption + element.getFdNameOri() + "执行更新报错！id为：" + element.getFdId());
					isException =true;
					throw e;//异常抛出
				}finally {
					if (status != null && isException) {
						TransactionUtils.getTransactionManager().rollback(status);//回滚了
					}
				}
			}
		}
	}

	/************************************************************************************************************
	 * 更新组织架构的数据
	 * @param elements 集合
	 * @param isNotPerson 是否是非人员(组织、架构、岗位)的更新
	 * @throws Exception
	 ***********************************************************************************************************/
	private void updateArchitectureData(List<SysOrgElement> elements,boolean isNotPerson) throws Exception{
		logger.info("开始执行线程：" + Thread.currentThread().getName());
		int size = elements.size();
		for (int i = 0; i < size; i++) {
			TransactionStatus status = null;
			HrOrganizationElement hrOrganization = null;
			SysOrgElement element = elements.get(i);
			String decerption = "人员:";
			boolean isException =false;
			try {
				if (null == element) {
					jobContext.logMessage("EKP组织架构数据为空，不执行更新！");
					continue;
				}
				status = TransactionUtils.beginNewTransaction();
				if(isNotPerson) {//非人员(组织、架构、岗位)
					hrOrganization = (HrOrganizationElement) hrOrganizationElementService.findByPrimaryKey(element.getFdId());
					decerption = "机构、部门、岗位:";
				}else {
					hrOrganization = (HrStaffPersonInfo) hrStaffPersonInfoService.findByPrimaryKey(element.getFdId());
					element = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(element.getFdId());
				}
				if (null != hrOrganization) {
					setHierarchy(hrOrganization, element);
					hrOrganizationElementService.update(hrOrganization);
					jobContext.logMessage("更新数据"+ decerption + " fdName=" + element.getFdName() + ",fdId=" + element.getFdId());
				} else { 
					jobContext.logMessage("未找到对应的人事组织数据，不执行更新！");
				}
				TransactionUtils.getTransactionManager().commit(status);
			} catch (Exception e) {
				isException =true;
				e.printStackTrace();
				logger.error("更新人事组织架构", e);
				jobContext.logMessage("更新数据" + decerption + element.getFdNameOri() + "执行更新报错！id为：" + element.getFdId());

				throw e;
			}finally {
				if (isException && status != null) {
					TransactionUtils.getTransactionManager().rollback(status);
				}
			}
		}
	}

	private List getSysElementsData(boolean isNotPerson, String sql) throws Exception{
		List rtnList = new ArrayList();
		HQLInfo info = new HQLInfo();
		sql += " and fdIsExternal != true ";//过滤掉外部组织
		info.setOrderBy("fdAlterTime desc");
		long startTime = System.currentTimeMillis();
		String temp = "获取需要更新非人员（组织、架构、部门、岗位）数据耗时(秒):" ;
		if(isNotPerson) {
			info.setWhereBlock(sql + " and fdOrgType in(" + Joiner.on(",").join(orgElementNoPersonTypes) + ")");
		}else {
			temp ="获取需要更新人员数据耗时(秒):";
			info.setWhereBlock(sql + " and fdOrgType in(" + SysOrgConstant.ORG_TYPE_PERSON + ")");
		}
		rtnList = sysOrgElementService.findList(info);
		if (CollectionUtils.isEmpty(rtnList)) {
			rtnList = new ArrayList();
		}else {
			temp = temp + (System.currentTimeMillis() - startTime) / 1000;
			logger.info(temp);
			jobContext.logMessage(temp);
		}
		return rtnList;
	}

	private void log(String msg) {
		logger.debug("【EKP组织架构同步到人事组织架构】" + msg);
		if (this.jobContext != null) {
			jobContext.logMessage(msg);
		}
	}

	public void relaxSource(){
		locked = false;
		addNotPesonStartTime = 0;
		updateNotPesonStartTime = 0;
		addPesonStartTime = 0;
		updatePesonStartTime = 0;
		mapLog = new HashMap<String,String>();//记录各个阶段的日志
	}
	/**
	 * 排除不需要同步的人员信息，例如：admin、everyone、anonymous和三员管理员
	 * @param personLists
	 * @return
	 */
	private List<String> getAllNeedPerson(List<String> personLists) throws Exception {
		List<String> personList = new ArrayList<>();
		List<String> exceptFdidList = new ArrayList<>();
		exceptFdidList.add("1183b0b84ee4f581bba001c47a78b2d9");//管理员
		exceptFdidList.add("1183b0b84ee4f581bba001c47a78b39d");//everyone
		exceptFdidList.add("17ca5cc9e477c18f16400644e02af91b");//anonymous
		if(!ArrayUtil.isEmpty(personLists)){
			for (String personFdId:personLists){
				SysOrgPerson sysOrgPerson = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(personFdId);
				String loginName = sysOrgPerson.getFdLoginName();
				//当返回1时，表示该用户是普通用户，可以同步，如果返回不是1，表示是管理员，不需要同步
				//admin、everyone、anonymous三个用户也不用同步
				if(!exceptFdidList.contains(personFdId)){
					personList.add(personFdId);
				}
			}
		}
		return personList;
	}
}
