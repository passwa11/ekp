package com.landray.kmss.hr.organization.oms;

import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.hr.organization.constant.HrOrgConstant;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.service.IHrStaffTrackRecordService;
import com.landray.kmss.sys.organization.model.*;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.TransactionStatus;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 定时同步 HR组织架构到 EKP组织架构
 * 
 * @author 王京 2020-12-31 重构
 *
 */
public class SynchroOrgHrToEkpImp implements HrOrgConstant, SysOrgConstant {

	private static boolean locked = false;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SynchroOrgHrToEkpImp.class);

	private SysQuartzJobContext jobContext = null;

	private long syncTime = 0L;

	private String lastUpdateTime = null;
	private List<String> errorList= Collections.synchronizedList(new ArrayList());

	private IHrOrganizationElementService hrOrganizationElementService;

	public void setHrOrganizationElementService(IHrOrganizationElementService hrOrganizationElementService) {
		this.hrOrganizationElementService = hrOrganizationElementService;
	}

	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	public void setHrStaffPersonInfoService(IHrStaffPersonInfoService hrStaffPersonInfoService) {
		this.hrStaffPersonInfoService = hrStaffPersonInfoService;
	}

	private IHrStaffTrackRecordService hrStaffTrackRecordService;

	public void setHrStaffTrackRecordService(IHrStaffTrackRecordService hrStaffTrackRecordService) {
		this.hrStaffTrackRecordService = hrStaffTrackRecordService;
	}

	private ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	private ISysOrgPersonService sysOrgPersonService;

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	private ThreadPoolTaskExecutor taskExecutor;

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	private HrOrganizationSyncSetting syncSetting = null;

	private final int rowsize = 5000;
	AtomicInteger allCount = new AtomicInteger(0);

	public void synchroHrToEkp(SysQuartzJobContext context) {
		String temp = null;
		this.jobContext = context;
		if (locked) {
			temp = "存在运行中的人事组织架构到EKP组织架构同步任务，当前任务中断...";
			logger.error(temp);
			jobContext.logError(temp);
			return;
		}
		locked = true;
		log("开始人事组织架构到EKP组织架构同步....");
		try {
			/**
			 * 获取上次同步的时间
			 */
			syncSetting = new HrOrganizationSyncSetting();
			lastUpdateTime = syncSetting.getLastUpdateTime();
			errorList= Collections.synchronizedList(new ArrayList());
			/***
			 * 查询上次同步之后需要新增和修改的数据 1）非人员的数据先同步，再执行人员的同步
			 */
			// 需要更新的HR的数据主键
			List<String> needAddOrgData = getHrAddData(true);
			// 需要更新的组织
			List<String> needupdateOrgData = getHrUpdateData(true);
			/**
			 * 2）处理人员的数据 因为一些依赖组织所以组织没有同步可能会出现失败的情况
			 */ 
			List<String> needAddPersonData = getHrAddData(false); 
			List<String> needupdatePersonData = getHrUpdateData(false); 

			long alltime = System.currentTimeMillis();
			long caltime = System.currentTimeMillis();
			List<String> updateAll=new ArrayList();
			if (CollectionUtils.isEmpty(needAddOrgData) && CollectionUtils.isEmpty(needupdateOrgData)) {
				logger.debug("HR同步到EKP===没有需要同步的组织数据");
			} else {
				temp = "==========开始同步(" + lastUpdateTime + ")人事组织数据到EKP组织架构===============";
				
				// 新增人事组织架构部门数据到EKP组织架构
				if (CollectionUtils.isNotEmpty(needAddOrgData)) {
					caltime = System.currentTimeMillis();
					syncOrgElement(needAddOrgData,true);
					temp = "添加EKP组织架构的非人员数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
					logger.debug(temp);
					context.logMessage(temp);
					updateAll.addAll(needAddOrgData);
				}
				// 更新数据 新增和修改的数据一起全部修改 主要是修改组织的所属层次
				if (CollectionUtils.isNotEmpty(needupdateOrgData)) {
					updateAll.addAll(needupdateOrgData); 
				} 
			} 
			if (CollectionUtils.isEmpty(needAddPersonData) && CollectionUtils.isEmpty(needupdatePersonData)) {
				logger.debug("HR同步到EKP===没有需要同步的人员数据");
			} else {
				// 添加HR人员--》EKP组织架构的人员
				if (CollectionUtils.isNotEmpty(needAddPersonData)) {
					caltime = System.currentTimeMillis();
					syncOrgElement(needAddPersonData,true);
					temp = "添加EKP组织架构的人员数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
					logger.debug(temp);
					context.logMessage(temp);
				} 
			}
			if (CollectionUtils.isNotEmpty(updateAll)) {
				caltime = System.currentTimeMillis();
				syncOrgElement(updateAll,false);
				temp = "修改EKP组织架构的非人员数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
				logger.debug(temp);
				context.logMessage(temp);
			}
			// 更新数据
			if (CollectionUtils.isNotEmpty(needupdatePersonData)) {
				caltime = System.currentTimeMillis();
				syncOrgElement(needupdatePersonData,false);
				temp = "修改EKP组织架构的人员数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
				logger.debug(temp);
				context.logMessage(temp);
			}
			// 首次更新以后，记录更新时间
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
	 * 处理需要新增的HR数据
	 * 
	 * @param orgs
	 * @param persons
	 * @throws Exception
	 */
	private void syncOrgElement(List<String> elements,Boolean isAdd) throws Exception {
		allCount = new AtomicInteger(0);
		String logInfo = null;
		int count = elements.size() % rowsize == 0 ? elements.size() / rowsize : elements.size() / rowsize + 1;
		String logOperateName="修改";
		if(isAdd) {
			logOperateName="新增";
		}
		logInfo = String.format("本次%s总数据:%s条,将执行%s次分批同步,每次%s条", logOperateName,elements.size(),count,rowsize);
		logger.debug(logInfo);
		jobContext.logMessage(logInfo);

		CountDownLatch countDownLatch = new CountDownLatch(count);
		List<String> temppersons = null;
		for (int i = 0; i < count; i++) {
			logger.debug(logOperateName+"执行第" + (i + 1) + "批");
			if (elements.size() > rowsize * (i + 1)) {
				temppersons = elements.subList(rowsize * i, rowsize * (i + 1));
			} else {
				temppersons = elements.subList(rowsize * i, elements.size());
			}
			taskExecutor.execute(new SysSyncRunner(temppersons, countDownLatch,isAdd));
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException exc) {
			exc.printStackTrace();
			logger.error("",exc);
		}
		jobContext.logMessage("本次共新增总人事组织架构数据:" + allCount + "条");
	}

	class SysSyncRunner implements Runnable {
		private final List<String> elementIds;
		private CountDownLatch addDownLatch;
		private Boolean isAdd =false;
		public SysSyncRunner(List<String> elementIds, CountDownLatch downLatch,Boolean isAdd) {
			this.elementIds = elementIds;
			this.addDownLatch = downLatch;
			this.isAdd=isAdd;
		}

		@Override
		public void run() {
			logger.debug("同步HR-EKP 启动线程：" + Thread.currentThread().getName());
			try {
				if(isAdd) {
					//新增
					addSysOrg(elementIds);
				}else {
					//更新人员
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
	 * <p>
	 * 新增EKP机构、部门数据
	 * </p>
	 * 
	 * @param elements
	 * @author sunj
	 * @throws Exception
	 */
	private void addSysOrg(List<String> elementIds) throws Exception {

		for (String hrFdId : elementIds) {
			boolean isException =false;
			SysOrgElement orgElement = null;
			TransactionStatus status = null;
			try {
				status = TransactionUtils.beginNewTransaction();
				// 查询当前需要同步的组织
				HrOrganizationElement hrOrgelement = (HrOrganizationElement) hrOrganizationElementService
						.findByPrimaryKey(hrFdId, null, true);
				if (hrOrgelement != null) {
					if (hrOrgelement.getFdOrgType().equals(HR_TYPE_ORG)) {
						orgElement = new SysOrgOrg();
					} else if (hrOrgelement.getFdOrgType().equals(HR_TYPE_DEPT)) {
						orgElement = new SysOrgDept();
					} else if (hrOrgelement.getFdOrgType().equals(HR_TYPE_POST)) {
						orgElement = new SysOrgPost();
					} else if (hrOrgelement.getFdOrgType().equals(HR_TYPE_PERSON)) {
						orgElement = new SysOrgPerson();
						//如果是人员，人员系统账号是空，则不同步
						HrStaffPersonInfo personInfo = (HrStaffPersonInfo) hrOrgelement;
						if(StringUtil.isNull(personInfo.getFdLoginName())){
							jobContext.logMessage("人员系统账号为空，不增加：fdName=" + orgElement.getFdName() + ",fdId=" + orgElement.getFdId());
							continue;
						}
					}
					if (null != orgElement) {
						copyHrOrgToEkpOrg(orgElement, hrOrgelement);
						sysOrgElementService.add(orgElement);
						// 日志
						jobContext.logMessage("新增EKP组织架构数据：fdName=" + orgElement.getFdName() + ",fdId=" + orgElement.getFdId());
						allCount.incrementAndGet();
					}
					if (errorList.size() == 0) {
						//取最后一条记录为最后更新日期 (循环覆盖)
						lastUpdateTime = DateUtil.convertDateToString(hrOrgelement.getFdAlterTime(), "yyyy-MM-dd HH:mm:ss.SSS");
					}
				} 
				TransactionUtils.getTransactionManager().commit(status);
			} catch (Exception e) {
				isException =true;
				e.printStackTrace();
				logger.error("新增EKP组织架构", e);
				errorList.add(hrFdId);

			}finally {
				if (isException && status != null) {
					TransactionUtils.getTransactionManager().rollback(status);
				}
			}
		}
	} 

	/**
	 * 更新组织信息
	 * @param hrOrgElements
	 * @throws Exception
	 */
	private void updateSysOrg(List<String> hrOrgElements) throws Exception {

		// 更新机构、部门、岗位 
		for (String hrFdId : hrOrgElements) {
			boolean isException =false;
			TransactionStatus status = null;
			try {
				status = TransactionUtils.beginNewTransaction();
				// 查询当前需要更新的组织详情
				HrOrganizationElement element = (HrOrganizationElement) hrOrganizationElementService.findByPrimaryKey(hrFdId, null, true);
				if(element==null) {
					logger.error("更新EKP组织架构-数据不存在 HrOrganizationElement fdId:"+hrFdId);
				}else {
					SysOrgElement sysOrgElement = null;
					if (element.getFdOrgType().equals(HR_TYPE_PERSON)) {
						//查人员表信息
						sysOrgElement = (SysOrgElement) sysOrgPersonService.findByPrimaryKey(hrFdId, null, true);
						element = (HrOrganizationElement) hrStaffPersonInfoService.findByPrimaryKey(hrFdId, null, true);
					} else {
						sysOrgElement = (SysOrgElement) sysOrgElementService.findByPrimaryKey(hrFdId, null, true);
					}
					if (sysOrgElement != null) {
						setHierarchy(sysOrgElement, element);
						sysOrgElementService.setNotToUpdateHierarchy(true);
						sysOrgElementService.setNotToUpdateRelation(true);
						sysOrgElementService.update(sysOrgElement);
						// 日志
						jobContext.logMessage("更新EKP组织架构信息：fdName=" + sysOrgElement.getFdName() + ",fdId=" + sysOrgElement.getFdId());

					}
				}
				TransactionUtils.getTransactionManager().commit(status);
				//取最后一条记录为最后更新日期 (循环覆盖)
				if (errorList.size() == 0) {
					//取最后一条记录为最后更新日期 (循环覆盖)
					lastUpdateTime = DateUtil.convertDateToString(element.getFdAlterTime(), "yyyy-MM-dd HH:mm:ss.SSS");
				}
			} catch (Exception e) {
				isException =true;
				e.printStackTrace();
				logger.error("更新EKP组织架构"+hrFdId, e);
				errorList.add(hrFdId);

			}finally {
				if (isException && status != null) {
					TransactionUtils.getTransactionManager().rollback(status);
				}
				sysOrgElementService.setNotToUpdateHierarchy(false);
				sysOrgElementService.setNotToUpdateRelation(false);
			}
		}
	} 
	private void setHierarchy(SysOrgElement orgElement, HrOrganizationElement hrOrgelement) throws Exception {

		copyHrOrgToEkpOrg(orgElement, hrOrgelement);
		if (null != hrOrgelement.getFdParent()) {
			SysOrgElement fdParent = (SysOrgElement) sysOrgElementService
					.findByPrimaryKey(hrOrgelement.getFdParent().getFdId());
			if (null != fdParent) {
				orgElement.setFdParent(fdParent);
			}
		} else {
			orgElement.setFdParent(null);
		}
		if (null != hrOrgelement.getHbmParentOrg()) {
			if (sysOrgElementService.getBaseDao().isExist(SysOrgElement.class.getName(), hrOrgelement.getHbmParentOrg().getFdId())) {
				SysOrgElement fdParentOrg = (SysOrgElement) sysOrgElementService
						.findByPrimaryKey(hrOrgelement.getHbmParentOrg().getFdId());
				if (null != fdParentOrg) {
					orgElement.setHbmParentOrg(fdParentOrg);
				}
			}
		} else {
			orgElement.setHbmParentOrg(null);
		}
		if (null != hrOrgelement.getHbmThisLeader()) {
			// 本级领导
			if (sysOrgElementService.getBaseDao().isExist(SysOrgElement.class.getName(), hrOrgelement.getHbmThisLeader().getFdId())) {
				SysOrgElement hbmThisLeader = (SysOrgElement) sysOrgElementService
						.findByPrimaryKey(hrOrgelement.getHbmThisLeader().getFdId());
				if (null != hbmThisLeader) {
					orgElement.setHbmThisLeader(hbmThisLeader);
				}
			}
		} else {
			orgElement.setHbmThisLeader(null);
		}
		// 上级领导为空时取分管领导同步#114914
		if (null != hrOrgelement.getFdBranLeader()) {
			if (sysOrgElementService.getBaseDao().isExist(SysOrgElement.class.getName(), hrOrgelement.getFdBranLeader().getFdId())) {
				SysOrgElement hbmBranLeader = (SysOrgElement) sysOrgElementService
						.findByPrimaryKey(hrOrgelement.getFdBranLeader().getFdId(), null, true);
				if (null != hbmBranLeader) {
					orgElement.setHbmSuperLeader(hbmBranLeader);
				}
			}
		} else {
			orgElement.setHbmSuperLeader(null);
		}
		// 最后更新时间
		orgElement.setFdAlterTime(hrOrgelement.getFdAlterTime());
	}

	/**
	 * <p>
	 * 新增 复制基础属性
	 * </p>
	 * 
	 * @param orgElement
	 * @param hrOrgelement
	 * @author sunj
	 */
	private void copyHrOrgToEkpOrg(SysOrgElement orgElement, HrOrganizationElement hrOrgelement) {

		orgElement.setFdId(hrOrgelement.getFdId());
		orgElement.setFdOrgType(hrOrgelement.getFdOrgType());
		orgElement.setFdName(hrOrgelement.getFdName());
		orgElement.setFdNamePinYin(hrOrgelement.getFdNamePinYin());
		orgElement.setFdNameSimplePinyin(hrOrgelement.getFdNameSimplePinyin());
		orgElement.setFdOrder(hrOrgelement.getFdOrder());
		orgElement.setFdNo(hrOrgelement.getFdNo());
		orgElement.setFdKeyword(hrOrgelement.getFdKeyword());
		orgElement.setFdIsAvailable(hrOrgelement.getFdIsAvailable());
		orgElement.setFdIsAbandon(hrOrgelement.getFdIsAbandon());
		orgElement.setFdIsBusiness(hrOrgelement.getFdIsBusiness());
		orgElement.setFdMemo(hrOrgelement.getFdMemo());
		// 如果是人员
		if (hrOrgelement.getFdOrgType().equals(HR_TYPE_PERSON)) {
			SysOrgPerson orgPerson = (SysOrgPerson) orgElement;
			HrStaffPersonInfo personInfo = (HrStaffPersonInfo) hrOrgelement;
			orgPerson.setFdMobileNo(personInfo.getFdMobileNo());
			orgPerson.setFdEmail(personInfo.getFdEmail());
			orgPerson.setFdLoginName(personInfo.getFdLoginName());
			if (StringUtil.isNull(orgPerson.getFdPassword())) {
				orgPerson.setFdNewPassword("1"); // 新增初始化密码为“1”
			}
			orgPerson.setFdSex(personInfo.getFdSex()); 
			//人员岗位的处理
			orgPerson.setFdPosts(getPersonPost(personInfo));
			//如果是人员。工号等于组织架构的编号
			orgElement.setFdNo(personInfo.getFdStaffNo());
			//是否登录系统
			orgPerson.setFdCanLogin(personInfo.getFdCanLogin());
		}
		orgElement.setFdCreateTime(hrOrgelement.getFdCreateTime());
	}
	
	/**
	 * HR中人员岗位列表获取
	 * @param hrOrgelement
	 * @return
	 */
	private List<SysOrgElement>  getPersonPost(HrStaffPersonInfo hrOrgelement) {
		List<SysOrgElement> rtnVal = new ArrayList();
		try { 
			List<String> postIds = new ArrayList(); 
			if (CollectionUtils.isNotEmpty(hrOrgelement.getFdPosts())) {
				List<String> mainPostIds = new ArrayList();
				// 当前人员所属岗位
				for (Object post : hrOrgelement.getFdPosts()) {
					if (post != null && post instanceof HrOrganizationPost) {
						HrOrganizationPost postInfo = (HrOrganizationPost) post;
						mainPostIds.add(postInfo.getFdId());
					}
				}
				if(CollectionUtils.isNotEmpty(mainPostIds)) {
					postIds.addAll(mainPostIds);
				}
			} 
			// 当前人员所有任职中的兼岗
			StringBuilder sql = new StringBuilder();
			sql.append(" select fd_hr_org_post from hr_Staff_Track_Record ")
					.append(" where fd_type=2 and fd_status='1' ").append(" and fd_hr_org_post is not null  ")
					.append(" and fd_person_info_id='").append(hrOrgelement.getFdId()).append("' ");
			List<String> recordPostIds = hrOrganizationElementService.getIdByJdbc(sql.toString(), null); 
			if(CollectionUtils.isNotEmpty(recordPostIds)) {
				postIds.addAll(recordPostIds);
			}
			Map<String, Boolean> setOrgElement = new HashMap<String, Boolean>();
			if (CollectionUtils.isNotEmpty(postIds)) {
				for (String postId : postIds) {
					if(sysOrgElementService.getBaseDao().isExist(SysOrgElement.class.getName(), postId)) {
						SysOrgElement element = (SysOrgElement) sysOrgElementService.findByPrimaryKey(postId, null, true);
						if (null != element) {
							if (setOrgElement.get(element.getFdId()) == null) {
								rtnVal.add(element);
							}
							setOrgElement.put(element.getFdId(), true);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询人员岗位出错：" + e);
		}
		return rtnVal;
	}

	/**
	 * 查询EKP中更新时间内需要新增的HR表的fdId
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<String> getHrAddData(Boolean isNotPerson){

		StringBuilder sql = new StringBuilder();
		sql.append(" select e.fd_id from hr_org_element e left join sys_org_element se on e.fd_id=se.fd_id");
		sql.append(" where  e.fd_is_available=? and se.fd_id is null ");// 表示EKP组织中没有的数据，则时新增
		List params = new ArrayList();
		params.add(Boolean.TRUE);
		if (StringUtil.isNotNull(lastUpdateTime)) {
			sql.append(" and e.fd_alter_time >? ");
			Date date = DateUtil.convertStringToDate(lastUpdateTime, "yyyy-MM-dd HH:mm:ss.SSS");
			params.add(date);
		}
		// 非人员
		if (isNotPerson) {
			sql.append(" and e.fd_org_type not in (?) ");
			params.add(HR_TYPE_PERSON);
		} else {
			sql.append(" and e.fd_org_type in (?) ");
			params.add(HR_TYPE_PERSON);
		}
		sql.append(" order by e.fd_alter_time asc ");
		return hrOrganizationElementService.getIdByJdbc(sql.toString(), params);
	}

	/**
	 * 查询EKP中更新时间内需要修改的HR表的fdId 
	 * @return
	 * @throws Exception
	 */
	private List<String> getHrUpdateData(Boolean isNotPerson){
		StringBuilder sql = new StringBuilder();
		sql.append(" select e.fd_id from hr_org_element e left join sys_org_element se on e.fd_id=se.fd_id");
		sql.append(" where se.fd_id is not null ");// 表示EKP组织中已经存在，这时间内要更新的数据
		sql.append(" and se.fd_is_external !=? ");//过滤掉外部组织
		List params = new ArrayList();
		params.add(Boolean.TRUE);
		if (StringUtil.isNotNull(lastUpdateTime)) {
			sql.append(" and e.fd_alter_time >? ");
			Date date = DateUtil.convertStringToDate(lastUpdateTime, "yyyy-MM-dd HH:mm:ss.SSS");
			params.add(date);
		} 
		// 非人员
		if (isNotPerson) {
			sql.append(" and e.fd_org_type not in (?) ");
			params.add(HR_TYPE_PERSON);
		} else {
			sql.append(" and e.fd_org_type in (?) ");
			params.add(HR_TYPE_PERSON);
		}
		sql.append(" order by e.fd_alter_time asc ");
		return hrOrganizationElementService.getIdByJdbc(sql.toString(), params);
	}

	private void log(String msg) {
		logger.debug("【人事组织架构同步到EKP组织架构】" + msg);
		if (this.jobContext != null) {
			jobContext.logMessage(msg);
		}
	}

	private void terminate(SysQuartzJobContext context) throws Exception {
		if (StringUtil.isNotNull(lastUpdateTime)) {
			syncSetting.setLastUpdateTime(lastUpdateTime);
			syncSetting.save();
		}
	}

}
