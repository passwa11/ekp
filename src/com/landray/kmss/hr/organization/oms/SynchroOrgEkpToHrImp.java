package com.landray.kmss.hr.organization.oms;

import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.hr.organization.constant.HrOrgConstant;
import com.landray.kmss.hr.organization.model.HrOrganizationDept;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationOrg;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoLog;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoLogService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.authorization.util.TripartiteAdminUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <P>
 * 定时任务同步组织架构数据
 * @version 1.0 2019年12月5日
 * 重构EKP组织架构数据 同步HR组织架构，仅同步 机构，部门，岗位，人员
 * @version 2.0 2021-07-07 王京
 */
public class SynchroOrgEkpToHrImp extends SynchroOrgCommon implements SysOrgConstant, HrOrgConstant {

	private static boolean locked = false;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SynchroOrgEkpToHrImp.class);

	private SysQuartzJobContext jobContext = null;

	private String lastUpdateTime = null;
	private List<String> errorList;
	private IHrStaffPersonInfoLogService hrStaffPersonInfoLogService;
	
	public void setHrStaffPersonInfoLogService(IHrStaffPersonInfoLogService hrStaffPersonInfoLogService) {
		this.hrStaffPersonInfoLogService = hrStaffPersonInfoLogService;
	}

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

	private List<HrStaffPersonInfo> logPersonInfo;
	private HrOrganizationSyncSetting syncSetting = null;

	private final int rowsize = 5000;
	AtomicInteger allCount = new AtomicInteger(0);

	private List<Integer> orgNotPersonType=null;

	private Map<String,Integer> updateOrgType=null;
	public void synchroEkpToHr(SysQuartzJobContext context) {
		String temp = null;
		this.jobContext = context;
		if (locked) {
			temp = "存在运行中的EKP组织架构到人事组织架构同步任务，当前任务中断...";
			logger.error(temp);
			jobContext.logError(temp);
			return;
		}
		locked = true;
		log("开始到EKP组织架构人事组织架构同步....");
		try {
			errorList= Collections.synchronizedList(new ArrayList());
			if(orgNotPersonType ==null){
				orgNotPersonType =new ArrayList();
				orgNotPersonType.add(HR_TYPE_ORG);
				orgNotPersonType.add(HR_TYPE_DEPT);
				orgNotPersonType.add(HR_TYPE_POST);
			}
			logPersonInfo =null;
			updateOrgType =new ConcurrentHashMap<String,Integer>();
			/**
			 * 获取上次同步的时间
			 */
			syncSetting = new HrOrganizationSyncSetting();
			lastUpdateTime = syncSetting.getLastUpdateTime();
			/***
			 * 查询上次同步之后需要新增和修改的数据
			 * 1）非人员的数据先同步，再执行人员的同步
			 */
			// 需要更新的HR的数据主键
			List<String> needAddOrgData = getOrgAddData(true);
			// 需要更新的组织
			List<String> needupdateOrgData = getOrgUpdateData(true);

			List<String> updateAll=new ArrayList();
			long alltime = System.currentTimeMillis();
			long caltime = System.currentTimeMillis();
			if (CollectionUtils.isEmpty(needAddOrgData) && CollectionUtils.isEmpty(needupdateOrgData)) {
				temp ="EKP同步到HR===没有需要同步的组织数据";
				logger.debug(temp);
				context.logMessage(temp);
			} else {
				temp = "==========开始同步(" + lastUpdateTime + ")EKP组织架构到人事组织数据==============="; 
				// 新增人事组织架构部门数据到EKP组织架构
				if (CollectionUtils.isNotEmpty(needAddOrgData)) {
					caltime = System.currentTimeMillis();
					syncOrgElement(needAddOrgData, true);
					temp = "添加HR组织架构的非人员数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
					logger.debug(temp);
					context.logMessage(temp);
					updateAll.addAll(needAddOrgData);
				} 
				// 更新数据 新增和修改的数据一起全部修改 主要是修改组织的所属层次
				if (CollectionUtils.isNotEmpty(needupdateOrgData)) {
					updateAll.addAll(needupdateOrgData); 
				}
			}
			/**
			 * 2）处理人员的数据 因为一些依赖组织所以组织没有同步可能会出现失败的情况
			 */
			List<String> needAddPersonData = getOrgAddData(false);
			List<String> needupdatePersonData = getOrgUpdateData(false);
			//处理人员中不需要同步的人员信息：例如：admin、everyone、anonymous和三员管理员
			needAddPersonData = getAllNeedPerson(needAddPersonData);
			needupdatePersonData = getAllNeedPerson(needupdatePersonData);
			if (CollectionUtils.isEmpty(needAddPersonData) && CollectionUtils.isEmpty(needupdatePersonData)) {
				temp ="EKP同步到HR===没有需要同步的人员数据";
				logger.debug(temp);
				context.logMessage(temp);
			} else {
				// 添加HR人员--》EKP组织架构的人员
				if (CollectionUtils.isNotEmpty(needAddPersonData)) {
					caltime = System.currentTimeMillis();
					syncOrgElement(needAddPersonData, true);
					temp = "添加HR组织架构的人员数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
					logger.debug(temp);
					context.logMessage(temp);
					updateAll.addAll(needAddPersonData);  
				}
				// 更新数据
				if (CollectionUtils.isNotEmpty(needupdatePersonData)) {
					updateAll.addAll(needupdatePersonData);  
				}
			}  
			if (CollectionUtils.isNotEmpty(updateAll)) {
				caltime = System.currentTimeMillis();
				syncOrgElement(updateAll, false);
				temp = "修改HR组织架构的数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
				logger.debug(temp);
				context.logMessage(temp);
			}
			if(!updateOrgType.isEmpty()){
				/***
				 * 单独修改组织架构的类型（主要针对组织架构类型，不一致的情况下同步）
				 * 组织类型hibernate设计禁止修改，为了不影响其他场景，用Sql语句来修改。
				 */
				for(Map.Entry<String, Integer> sysOrgElement :updateOrgType.entrySet()){
					String key=sysOrgElement.getKey();
					Integer value=sysOrgElement.getValue();
					if(StringUtil.isNotNull(key) && value !=null) {
						hrOrganizationElementService.updateHrOrgType(sysOrgElement.getKey(), sysOrgElement.getValue());
					}
				}
			}

			if (errorList.size() > 0) {
				temp = "执行错误数量：" +errorList.size();
				logger.debug(temp);
				context.logMessage(temp);
			}
			// 首次更新以后，记录更新时间
			if (lastUpdateTime == null) {
				lastUpdateTime = DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
			}
			terminate(context);
			temp = "整个任务总耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000;
			logger.debug(temp);
			context.logMessage(temp);

		} catch (Exception ex) {
			ex.printStackTrace();
			if (context != null) {
				context.logError(ex);
			}
		} finally {
			locked = false;
		}
	}
	
	/**
	 * 同步人事档案不存在的 组织架构信息和人员
	 * @param context
	 */
	public void synchroAddPersonEkpToHr(SysQuartzJobContext context) {
		String temp = null;
		this.jobContext = context;
		if (locked) {
			temp = "存在运行中的EKP组织架构到人事组织架构同步任务，当前任务中断...";
			logger.error(temp);
			if(context !=null) {
				jobContext.logError(temp);
			}
			return;
		}
		locked = true;
		log("开始同步EKP人员信息到人事档案同步....");
		try {
			errorList= Collections.synchronizedList(new ArrayList());
			logPersonInfo= Collections.synchronizedList(new ArrayList<HrStaffPersonInfo>());
			lastUpdateTime =null;
			 
			/**
			 * 处理人员的数据 因为一些依赖组织所以组织没有同步可能会出现失败的情况
			 */
			List<String> needAddPersonData = getOrgAddData(false);
			needAddPersonData = getAllNeedPerson(needAddPersonData);
			List<String> updateAll=new ArrayList();
			long alltime = System.currentTimeMillis();
			long caltime = System.currentTimeMillis();
			  
			if (CollectionUtils.isEmpty(needAddPersonData)) {
				logger.debug("EKP同步到HR===没有需要同步的人员数据");
				if(context !=null) {
					context.logMessage("EKP同步到HR===没有需要同步的人员数据");
				}
			} else {
				// 添加HR人员--》EKP组织架构的人员
				if (CollectionUtils.isNotEmpty(needAddPersonData)) {
					caltime = System.currentTimeMillis();
					syncOrgElement(needAddPersonData, true);
					if(context !=null) {
						temp = "添加HR组织架构的人员数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
						logger.debug(temp);
						context.logMessage(temp);
					}
					updateAll.addAll(needAddPersonData);
				} 
			} 
			/**
			 * 所有新添加的重新修改一遍，主要是修改其的上下层关系
			 */
			if (CollectionUtils.isNotEmpty(updateAll)) {
				caltime = System.currentTimeMillis();
				syncOrgElement(updateAll, false);
				if(context !=null) {
					temp = "修改HR组织架构的数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
					logger.debug(temp);
					context.logMessage(temp);
				}
			}
			if(context !=null) {
				if (errorList.size() > 0) { 
					temp = "执行错误数量：" +errorList.size();
					logger.debug(temp);
				}
				temp = "整个任务总耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000;
				logger.debug(temp);
				context.logMessage(temp);
			}
			if(logPersonInfo !=null) {
				buildLog(logPersonInfo);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (context != null) {
				context.logError(ex);
			}
		} finally {
			locked = false;
		}
	}
	private void buildLog(List<HrStaffPersonInfo> fdPersonInfos) throws Exception {

		String syncString   = "系统自动同步 " + fdPersonInfos.size() + " 位员工。";
		HrStaffPersonInfoLog log = hrStaffPersonInfoLogService.buildPersonInfoLog("sync", syncString);
		log.setFdTargets(fdPersonInfos);
		log.setFdIp("-");
		log.setFdBrowser("-");
		log.setFdEquipment("-"); 
		hrStaffPersonInfoLogService.add(log);
	}

	/**
	 * 同步数据
	 * @param elements 人员
	 * @param isAdd 是否新增的标识
	 * @throws Exception
	 */
	private void syncOrgElement(List<String> elements, Boolean isAdd) throws Exception {
		allCount = new AtomicInteger(0);
		String logInfo = null;
		int count = elements.size() % rowsize == 0 ? elements.size() / rowsize : elements.size() / rowsize + 1;
		String logOperateName = "修改";
		if (isAdd) {
			logOperateName = "新增";
		}
		logInfo = String.format("本次%s总数据:%s条,将执行%s次分批同步,每次%s条", logOperateName, elements.size(), count, rowsize);
		logger.debug(logInfo);
		if(jobContext !=null) {
			jobContext.logMessage(logInfo);
		}
		CountDownLatch countDownLatch = new CountDownLatch(count);
		List<String> temppersons = null;
		for (int i = 0; i < count; i++) {
			logger.debug(logOperateName + "执行第" + (i + 1) + "批");
			if (elements.size() > rowsize * (i + 1)) {
				temppersons = elements.subList(rowsize * i, rowsize * (i + 1));
			} else {
				temppersons = elements.subList(rowsize * i, elements.size());
			}
			taskExecutor.execute(new SysSyncRunner(temppersons, countDownLatch, isAdd));
		}
		try {
			countDownLatch.await(1, TimeUnit.HOURS);
		} catch (InterruptedException exc) {
			exc.printStackTrace();
			logger.error("",exc);
		}
		if(jobContext !=null) {
			jobContext.logMessage("本次共新增总人事组织架构数据:" + allCount + "条");
		}
	}

	class SysSyncRunner implements Runnable {
		private final List<String> elementIds;
		private CountDownLatch addDownLatch;
		private Boolean isAdd = false;

		public SysSyncRunner(List<String> elementIds, CountDownLatch downLatch, Boolean isAdd) {
			this.elementIds = elementIds;
			this.addDownLatch = downLatch;
			this.isAdd = isAdd;
		}

		@Override
		public void run() {
			logger.debug("同步HR-EKP 启动线程：" + Thread.currentThread().getName());
			try {
				if (isAdd) {
					// 新增
					addSysOrg(elementIds);
				} else {
					// 更新人员
					updateSysOrg(elementIds);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("",e);
			} finally {
				addDownLatch.countDown();
				logger.debug("线程" + Thread.currentThread().getName() + "执行完成!");

			}
		}
	}

	/**
	 * 新增
	 * @param elementIds
	 * @throws Exception
	 */
	private void addSysOrg(List<String> elementIds) throws Exception {
		HrOrganizationElement hrOrganizationElement = null;
		TransactionStatus status = null;
		for (String hrFdId : elementIds) {
			boolean  isException =false;
			try {
				status = TransactionUtils.beginNewTransaction();
				// 查询当前需要同步的组织
				SysOrgElement element = (SysOrgElement) sysOrgElementService.findByPrimaryKey(hrFdId, null, true);
				if (element != null) {
					if (element.getFdOrgType().equals(ORG_TYPE_ORG)) {
						hrOrganizationElement = new HrOrganizationOrg();
					} else if (element.getFdOrgType().equals(ORG_TYPE_DEPT)) {
						hrOrganizationElement = new HrOrganizationDept();
					} else if (element.getFdOrgType().equals(ORG_TYPE_PERSON)) {
						hrOrganizationElement = new HrStaffPersonInfo();
					} else if (element.getFdOrgType().equals(ORG_TYPE_POST)) {
						hrOrganizationElement = new HrOrganizationPost();
					}
					if (null != hrOrganizationElement) {
						copyEkpOrgToHrOrg(hrOrganizationElement, element);
						hrOrganizationElementService.add(hrOrganizationElement);
						if(logPersonInfo !=null && element.getFdOrgType().equals(ORG_TYPE_PERSON)) {
							logPersonInfo.add((HrStaffPersonInfo)hrOrganizationElement);
						}
						if(jobContext !=null) {
							// 日志
							jobContext.logMessage("新增HR组织架构数据：fdName=" + element.getFdName() + ",fdId=" + element.getFdId());
						}
						allCount.incrementAndGet();
					}
					if (errorList.size() == 0) {
						//取最后一条记录为最后更新日期 (循环覆盖)
						if(lastUpdateTime ==null){
							lastUpdateTime = DateUtil.convertDateToString(element.getFdAlterTime(), "yyy-MM-dd HH:mm:ss.SSS");
						}
						if( DateUtil.convertStringToDate(lastUpdateTime).before(element.getFdAlterTime())) {
							lastUpdateTime = DateUtil.convertDateToString(element.getFdAlterTime(), "yyy-MM-dd HH:mm:ss.SSS");
						}
					}
				} 
				TransactionUtils.getTransactionManager().commit(status);
			} catch (Exception e) {
				isException =true;
				e.printStackTrace();
				errorList.add(hrFdId);
				logger.error("新增EKP组织架构", e);
			} finally {
				if (status != null && isException) {
					TransactionUtils.getTransactionManager().rollback(status);
				}
			}
		}
	}

	/**
	 * 更新组织信息
	 * 
	 * @param hrOrgElements
	 * @throws Exception
	 */
	private void updateSysOrg(List<String> hrOrgElements) throws Exception {
		TransactionStatus status = null;
		// 更新机构、部门、岗位
		for (String hrFdId : hrOrgElements) {
			boolean  isException =false;
			try {
				status = TransactionUtils.beginNewTransaction();
				// 查询当前需要更新的组织详情
				SysOrgElement sysOrgElement = (SysOrgElement) sysOrgElementService.findByPrimaryKey(hrFdId, null, true); 
				if (sysOrgElement == null) {
					logger.error("更新HR组织架构-数据不存在 sysOrgElement fdId:" + hrFdId);
				} else {
					HrOrganizationElement hrElement = null;
					if (sysOrgElement.getFdOrgType().equals(HR_TYPE_PERSON)) {
						// 查人员表信息
						sysOrgElement = (SysOrgElement) sysOrgPersonService.findByPrimaryKey(hrFdId, null, true);
						hrElement = (HrOrganizationElement) hrStaffPersonInfoService.findByPrimaryKey(hrFdId, null, true);
					} else if (
							sysOrgElement.getFdOrgType().equals(ORG_TYPE_DEPT)
									|| sysOrgElement.getFdOrgType().equals(ORG_TYPE_POST)
									|| sysOrgElement.getFdOrgType().equals(ORG_TYPE_ORG)
					) {
						//部门岗位机构
						hrElement = (HrOrganizationElement) hrOrganizationElementService.findByPrimaryKey(hrFdId, null, true);
					}
					if (hrElement != null) {
						setHierarchy(hrElement, sysOrgElement);
						hrOrganizationElementService.update(hrElement);
						// 日志
						if (jobContext != null) {
							jobContext.logMessage("更新HR组织架构信息：fdName=" + sysOrgElement.getFdName() + ",fdId=" + sysOrgElement.getFdId());
						}
					}
					/**组织类型hibernate设计禁止修改，为了不影响其他场景，用Sql语句来修改。*/
					if(!hrElement.getFdOrgType().equals(sysOrgElement.getFdOrgType())) {
						//组织类型同步
						updateOrgType.put(sysOrgElement.getFdId(),sysOrgElement.getFdOrgType());
					}
				}
				TransactionUtils.getTransactionManager().commit(status);
				if (errorList.size() == 0) {
					//取最后一条记录为最后更新日期 (循环覆盖)
					lastUpdateTime = DateUtil.convertDateToString(sysOrgElement.getFdAlterTime(), "yyyy-MM-dd HH:mm:ss.SSS");
				}
			} catch (Exception e) {
				isException =true;
				e.printStackTrace();
				errorList.add(hrFdId);
				logger.error("更新HR组织架构" + hrFdId, e);
			} finally {
				if (status != null && isException) {
					TransactionUtils.getTransactionManager().rollback(status);
				}
			}
		}
	}


	/**
	 * 查询EKP中更新时间内需要新增的HR表的fdId
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<String> getOrgAddData(Boolean isNotPerson) {

		StringBuilder sql = new StringBuilder();
		sql.append(" select e.fd_id from sys_org_element e left join hr_org_element se on e.fd_id=se.fd_id");
		sql.append(" where e.fd_is_available=? and se.fd_id is null ");// 表示HR组织中没有的数据，则时新增
		sql.append(" and e.fd_is_external !=? ");//过滤掉外部组织
		List params = new ArrayList();
		params.add(Boolean.TRUE);
		params.add(Boolean.TRUE);
		if (StringUtil.isNotNull(lastUpdateTime)) {
			sql.append(" and e.fd_alter_time >? ");
			Date date = DateUtil.convertStringToDate(lastUpdateTime, "yyyy-MM-dd HH:mm:ss.SSS");
			params.add(date);
		}
		// 非人员
		if (isNotPerson) {
			//机构，部门，岗位
			sql.append(" and e.fd_org_type in (?,?,?) ");
			params.addAll(orgNotPersonType);
		} else {
			sql.append(" and e.fd_org_type in (?) ");
			params.add(HR_TYPE_PERSON);
		}
		sql.append(" order by e.fd_alter_time asc ");
		return hrOrganizationElementService.getIdByJdbc(sql.toString(), params);
	}

	/**
	 * 查询EKP中更新时间内需要修改的HR表的fdId
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<String> getOrgUpdateData(Boolean isNotPerson) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select e.fd_id from sys_org_element e left join hr_org_element se on e.fd_id=se.fd_id");
		sql.append(" where se.fd_id is not null ");// 表示EKP组织中已经存在，这时间内要更新的数据
		sql.append(" and e.fd_is_external !=? ");//过滤掉外部组织
		List params = new ArrayList();
		params.add(Boolean.TRUE);
		if (StringUtil.isNotNull(lastUpdateTime)) {
			sql.append(" and e.fd_alter_time >? ");
			Date date = DateUtil.convertStringToDate(lastUpdateTime, "yyyy-MM-dd HH:mm:ss.SSS");
			params.add(date);
		}
		// 非人员
		if (isNotPerson) {
			sql.append(" and e.fd_org_type in (?,?,?) ");
			params.addAll(orgNotPersonType);
		} else {
			sql.append(" and e.fd_org_type in (?) ");
			params.add(HR_TYPE_PERSON);
		}
		sql.append(" order by e.fd_alter_time asc ");
		return hrOrganizationElementService.getIdByJdbc(sql.toString(), params);
	}

	private void log(String msg) {
		logger.debug("【EKP组织架构同步到人事组织架构】" + msg);
		if (this.jobContext != null) {
			jobContext.logMessage(msg);
		}
	}

	private void terminate(SysQuartzJobContext context) throws Exception {
		if (context !=null && StringUtil.isNotNull(lastUpdateTime)) {
			syncSetting.setLastUpdateTime(lastUpdateTime);
			syncSetting.save();
		}
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
