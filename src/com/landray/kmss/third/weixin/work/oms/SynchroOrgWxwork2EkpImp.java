package com.landray.kmss.third.weixin.work.oms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.landray.kmss.component.locker.interfaces.ConcurrencyException;
import com.landray.kmss.sys.organization.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.service.IKmssPasswordEncoder;
import com.landray.kmss.sys.organization.service.ISysOrgDeptService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgOrgService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.sys.organization.util.PasswordUtil;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.constant.WxworkConstant;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.model.api.WxDepart;
import com.landray.kmss.third.weixin.work.model.api.WxUser;
import com.landray.kmss.third.weixin.work.model.api.WxUser.Attr;
import com.landray.kmss.third.weixin.work.model.api.WxUser.ExtAttrsClass;
import com.landray.kmss.third.weixin.work.spi.model.WxworkOmsRelationModel;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.SecureUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.opensymphony.util.BeanUtils;

import net.sf.json.JSONObject;

/**
 * 企业微信到EKP组织架构同步（触发全量更新、启动增量同步）
 * 
 * @TODO 性能优化,按部门作事务提交
 */
public class SynchroOrgWxwork2EkpImp
		implements SynchroOrgWxwork2Ekp, WxworkConstant, SysOrgConstant {

	private static boolean locked = false;

	private static final Log logger = LogFactory
			.getLog(SynchroOrgWxwork2EkpImp.class);

	private WxworkApiService wxworkApiService = null;

	private IWxworkOmsRelationService wxworkOmsRelationService;

	public void setWxworkOmsRelationService(
			IWxworkOmsRelationService wxworkOmsRelationService) {
		this.wxworkOmsRelationService = wxworkOmsRelationService;
	}

	private SysQuartzJobContext jobContext = null;

	private Map<String, String> relationMap = new HashMap<String, String>();
	private Map<String, SysOrgPerson> loginNameMap = new HashMap<String, SysOrgPerson>();
	private Map<String, SysOrgPerson> mobileMap = new HashMap<String, SysOrgPerson>();
	private Map<String, SysOrgPerson> personMap = new HashMap<String, SysOrgPerson>();
	private Map<String, SysOrgPost> postMap = new HashMap<String, SysOrgPost>();
	private Map<String, Map> wxPersonDeptMap = new HashMap<String, Map>(); // 记录人员的部门信息，方便更新部门所属部门信息、一人多部门、人员排序号等信息

	private Set<String> wxAllIds = new HashSet<String>();
	private String initPassword;

	private long WX_ROOT_DEPT_ID = 1;
	private String wxOrgId2ekp = ""; // 企业微信同步根部门id
	private String ekpRootOrgId2ekp = ""; // 同步到ekp的根部门id

	private String importInfoPre = "com.landray.kmss.third.weixin.work.oms.SynchroOrgWxwork2EkpImp";

	private ISysOrgPostService sysOrgPostService = null;

	public ISysOrgPostService getSysOrgPostService() {
		if (sysOrgPostService == null) {
			sysOrgPostService = (ISysOrgPostService) SpringBeanUtil
					.getBean("sysOrgPostService");
		}
		return sysOrgPostService;
	}

	private IKmssPasswordEncoder passwordEncoder;

	public IKmssPasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(IKmssPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	private ISysOrgPersonService sysOrgPersonService;

	public void
			setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	private ISysOrgDeptService sysOrgDeptService;

	public void setSysOrgDeptService(ISysOrgDeptService sysOrgDeptService) {
		this.sysOrgDeptService = sysOrgDeptService;
	}

	private ISysOrgOrgService sysOrgOrgService;

	public void setSysOrgOrgService(ISysOrgOrgService sysOrgOrgService) {
		this.sysOrgOrgService = sysOrgOrgService;
	}

	private ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	IComponentLockService componentLockService = null;

	private IComponentLockService getComponentLockService() {
		if (componentLockService == null) {
			componentLockService = (IComponentLockService) SpringBeanUtil
					.getBean("componentLockService");
		}
		return componentLockService;
	}

	@Override
	public void triggerSynchro(SysQuartzJobContext context) {
		String temp = "存在运行中的企业微信到EKP组织架构同步任务，当前任务中断...";
		this.jobContext = context;
		if (locked) {
			logger.error(temp);
			jobContext.logError(temp);
			return;
		}
		locked = true;
		WxSynchroInModel model = new WxSynchroInModel();
		try {
			if (!"true".equals(WeixinWorkConfig.newInstance().getWxEnabled())) {
				logger.info("企业微信集成已经关闭，故不同步数据");
				context.logMessage("企业微信集成已经关闭，故不同步数据");
				locked = false;
				return;
			}
			if (!"fromWx".equals(WeixinWorkConfig.newInstance().getSyncSelection())) {
				logger.info("企业微信组织架构接入已经关闭，故不同步数据");
				context.logMessage("企业微信组织架构接入已经关闭，故不同步数据");
				locked = false;
				return;
			}
			long time = System.currentTimeMillis();
			getComponentLockService().tryLock(model, "omsIn");

			// 初始化数据
			long caltime = System.currentTimeMillis();
			init(true);
			temp = "初始化数据总耗时(秒)："
					+ (System.currentTimeMillis() - caltime) / 1000;
			logger.info(temp);
			context.logMessage(temp);

			// 处理企业微信到EKP组织架构
			caltime = System.currentTimeMillis();
			updateSyncOrgElements();
			temp = "处理企业微信到EKP组织架构耗时(秒)："
					+ (System.currentTimeMillis() - caltime) / 1000;
			logger.info(temp);
			context.logMessage(temp);

			caltime = System.currentTimeMillis();
			updatePersons(); // 更新和部门相关的字段
			temp = "更新人员详细信息耗时(秒)："
					+ (System.currentTimeMillis() - caltime) / 1000;
			logger.info(temp);
			context.logMessage(temp);

			// 删除不在同步范围内(对照表中存在而又不在本次同步的范围)的部门和人员信息
			caltime = System.currentTimeMillis();
			Set<String> ekpIds = Sets.newHashSet(relationMap.keySet());
			SetView<String> diff_ekpIds = Sets.difference(ekpIds, wxAllIds);
			logger.warn("清理微信端不存在的映射关系处理:" + diff_ekpIds);
			log("清理微信端不存在的映射关系处理:" + diff_ekpIds);
			for (String wxId : diff_ekpIds) {
				cleanData(wxId);
			}
			temp = "删除不在同步范围内(对照表中存在而又不在本次同步的范围)的部门和人员信息处理完毕，耗时(秒)："
					+ (System.currentTimeMillis() - caltime) / 1000;
			logger.info(temp);
			log(temp);

			// 清理ekp本系统新建的与业务无关的组织架构信息
			String handleType = WeixinWorkConfig.newInstance()
					.getWx2ekpEkpOrgHandle();
			if ("autoDisable".equals(handleType)) {
				// 自动禁用
				try {
					deleteEkpOrg();
				} catch (Exception e) {
					logger.error(
							"自动禁用ekp本系统新建的与业务无关的组织架构信息失败：" + e.getMessage(), e);
				}
			}

			temp = "整个任务总耗时(秒)：" + (System.currentTimeMillis() - time) / 1000;
			logger.warn(temp);
			log(temp);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			log("【同步异常】" + e.getMessage());
		} finally {
			locked = false;
			clearDataMap();
			getComponentLockService().unLock(model);
		}
	}

	private void clearDataMap() {
		try {
			logger.debug("--------------------即将清空Map--------------------");
			relationMap.clear();
			mobileMap.clear();
			loginNameMap.clear();
			personMap.clear();
			postMap.clear();
			wxPersonDeptMap.clear();
			wxAllIds.clear();
			logger.debug("--------------------完成清空Map--------------------");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public void deleteEkpOrg() throws Exception {
		String sql = "SELECT fd_id FROM sys_org_element WHERE fd_id NOT IN (SELECT fd_ekp_id FROM wxwork_oms_relation_model) AND fd_org_type in(1,2,8) AND fd_is_available=1 and fd_name_pinyin != 'guanliyuan'  AND fd_is_business=1 ORDER BY fd_hierarchy_id DESC";
		SQLQuery sqlQuery = wxworkOmsRelationService.getBaseDao()
				.getHibernateSession()
				.createSQLQuery(sql);
		List orgList = sqlQuery.list();
		if (orgList.size() > 0) {
			logger.warn("ekp端存在多余数据，将执行禁用操作！！！");
			log("ekp端存在非企业微信同步过来的且与业务相关的多余数据，将执行禁用操作！！！");
			SysOrgElement org;
			// 如果ekp端有自定义的顶层部门
			// 获取根目录id
			String orgId = WeixinWorkConfig.newInstance().getWx2ekpEkpOrgId();
			String hierarchyId = "";
			Boolean hasOrg = false;
			if (StringUtil.isNotNull(orgId)) {
				hasOrg = true;
				String orgsql = "SELECT fd_hierarchy_id FROM sys_org_element WHERE fd_id='"
						+ orgId + "'";
				SQLQuery orgsqlQuery = wxworkOmsRelationService.getBaseDao()
						.getHibernateSession()
						.createSQLQuery(orgsql)
						.setMaxResults(1);
				if (orgsqlQuery.list().size() > 0) {
					hierarchyId = (String) orgsqlQuery.list().get(0);
				}
			}
			for (int i = 0; i < orgList.size(); i++) {
				String id = (String) orgList.get(i);
				logger.debug(" 处理id为：" + id);
				if (hasOrg) {
					if (StringUtil.isNotNull(hierarchyId)) {
						if (hierarchyId.contains(id)) {
							logger.debug("id: " + id + " 是顶层id或顶层的父级！");
							continue;
						}
					}
				}
				try {
					org = (SysOrgElement) sysOrgElementService
							.findByPrimaryKey(id, null, true);
					org.setFdIsAvailable(false);
					org.setFdHierarchyId("0");
					org.setFdAlterTime(new Date());
					sysOrgElementService.update(org);
					log("自动禁用：" + org.getFdName());
				} catch (Exception e) {
					logger.error(" 删除失败，事务回滚...", e);
					e.printStackTrace();
				}

			}
		} else {
			logger.debug("ekp端没有需要删除的数据");
		}
	}

	/*
	 * 清理不在同步范围内的部门和人员等信息
	 */
	private void cleanData(String wxId) throws Exception {
		SysOrgElement ele = null;
		try {
			ele = (SysOrgElement) sysOrgElementService
					.findByPrimaryKey(relationMap.get(wxId), null, true);
			if (ele != null) {
				if (ele.getFdOrgType() == 8) {
					if (ele != null) {
						ele.setFdIsAvailable(new Boolean(false));
						sysOrgElementService.update(ele);
					}
				} else {
					// 如果当前部门下有存在多余的子部门/人员和岗位则移到根目录
					String leaderPost = importInfoPre + "_post_2_"
							+ wxId.split("[|]")[0];
					String personPost = importInfoPre + "_post_8_"
							+ wxId.split("[|]")[0];
					List<SysOrgElement> subEles = sysOrgElementService
							.findList("hbmParent.fdId='" + ele.getFdId() + "'",
									null);
					for (SysOrgElement subele : subEles) {
						if (subele.getFdOrgType() == 4 && (leaderPost
								.equalsIgnoreCase(subele.getFdImportInfo())
								|| personPost.equalsIgnoreCase(
										subele.getFdImportInfo()))) {
							subele.setFdIsAvailable(false);
							if (subele.getHbmPersons() != null) {
								subele.getHbmPersons().clear();
							}
							sysOrgElementService.update(subele);
						} else {
							subele.setFdParent(null);
							sysOrgElementService.update(subele);
						}
					}
					ele.setFdIsAvailable(false);
					sysOrgElementService.update(ele);
				}
				wxworkOmsRelationService.deleteByKey(relationMap.get(wxId),
						getAppKey());
			} else {
				wxworkOmsRelationService.deleteByKey(relationMap.get(wxId),
						getAppKey());
			}

		} catch (Exception e) {
			logger.error("清理企业微信端不存在的映射关系失败", e);
			log("清理企业微信端不存在的映射关系失败:" + e.getMessage());
		}
	}

	/*
	 * 更新人员信息：所属部门，排序，一人多部门等(主要更新和部门相关的字段)
	 */
	private void updatePersons() throws Exception {

		if(wxPersonDeptMap.isEmpty()) {
			return;
		}
		boolean isCreate;
		Long[] departIds;
		Long[] orderInDepts;
		Long[] isLeaderInDept;
		Long mainDepartment;
		WeixinWorkConfig config = WeixinWorkConfig.newInstance();
		for(String userid:wxPersonDeptMap.keySet()){
			if(!relationMap.containsKey(userid+"|8")){
				logger.error("----无法找到 userid："+userid+"对应的ekp人员fdId-----");
				continue;
			}
			String ekpUserId = relationMap.get(userid + "|8");
			SysOrgPerson person = (SysOrgPerson) sysOrgPersonService
					.findByPrimaryKey(ekpUserId,SysOrgPerson.class,true);

			Map wxMap = wxPersonDeptMap.get(userid);
			isCreate = (boolean) wxMap.get("isCreate");
			departIds = (Long[]) wxMap.get("department");
			orderInDepts = (Long[]) wxMap.get("order");
			isLeaderInDept = (Long[]) wxMap.get("is_leader_in_dept");
			mainDepartment = (Long) wxMap.get("main_department");
			
			Long realDept = null; // ekp人员对应的真正的微信部门id

			// 同步人员所属部门
			String deptSynWay = config.getWx2ekpDepartmentSynWay();
			if ("syn".equalsIgnoreCase(deptSynWay)
					|| ("addSyn".equalsIgnoreCase(deptSynWay)
							&& isCreate)) {
				String deptSynKey = config.getWx2ekpDepartment();

				// 只是同步主部门
				SysOrgDept sysOrgDept = null;
				if (relationMap.containsKey(mainDepartment + "|2")) {
					sysOrgDept = (SysOrgDept) sysOrgDeptService
							.findByPrimaryKey(relationMap.get(mainDepartment + "|2"),null,true);
				}
				if (sysOrgDept == null) {
					logger.warn(
							"ekp中找不到人员的主部门 mainDepartment：" + mainDepartment
									+ " ,尝试从人员的微信部门中随机同步一个部门作为人员所属部门");
					for (Long did : departIds) {
						if (relationMap.containsKey(did + "|2")) {
							sysOrgDept = (SysOrgDept) sysOrgDeptService
									.findByPrimaryKey(relationMap.get(did + "|2"),null,true);
							if (sysOrgDept != null) {
								realDept = did;
								break;
							}
						}

					}
				} else {
					realDept = mainDepartment;
				}
				if (sysOrgDept == null && StringUtil.isNotNull(ekpRootOrgId2ekp)) {
					// 人员的部门都找不到，选择配置的根部门
					SysOrgElement element = (SysOrgElement) sysOrgElementService.findByPrimaryKey(relationMap.get(ekpRootOrgId2ekp),null,true);
					if(element != null){
						person.setFdParent(element);
					}
				}else if (sysOrgDept != null) {
					person.setFdParent(sysOrgDept);
				}

				if ("muilDept".equals(deptSynKey)) {
					updatePersonPost(person, departIds, "8",
							wxMap.get("name").toString(), isLeaderInDept);
				}

			}

			// 部门领导(上级)的处理
			String deptLeaderSynWay = WeixinWorkConfig.newInstance()
					.getWx2ekpDeptLeaderSynWay();
			if ("syn".equalsIgnoreCase(deptLeaderSynWay)
					|| ("addSyn".equalsIgnoreCase(deptLeaderSynWay)
							&& isCreate)) {
				updatePersonPost(person, departIds, "2",
						wxMap.get("name").toString(), isLeaderInDept);
			}

			// 人员排序
			if (realDept != null) {
				String orderSynWay = config.getWx2ekpOrderInMainDeptSynWay();
				if ("syn".equalsIgnoreCase(orderSynWay)
						|| ("addSyn".equalsIgnoreCase(orderSynWay)
								&& isCreate)) {

					// 找到人员在微信对应的部门中的排序
					Long order = 0L;
					for (int i = 0; i < departIds.length; i++) {
						if (realDept.equals(departIds[i])) {
							order = orderInDepts[i];
							break;
						}
					}
					String orderSynKey = config.getWx2ekpOrderInMainDept();
					if ("desc".equals(orderSynKey)) { // 逆序
						order = 2147483647L - order; // 避免排序号有负数，出现异常
					}
					person.setFdOrder(order.intValue());
				}

			}

			sysOrgPersonService.update(person);

		}
		
	}

	/*
	 * 更新人员的一人多部门以及部门上级的岗位信息
	 */
	private void updatePersonPost(SysOrgPerson person, Long[] departIds,
			String type, String name, Long[] isLeaderInDept) throws Exception {

		// 一人多部门、部门领导
		List<SysOrgPost> postList = new ArrayList<SysOrgPost>();

		String postType = "成员";
		if ("2".equals(type)) {
			postType = "上级";
		}
		// 保留非企业微信同步的岗位以及部门上级岗位
		List<SysOrgPost> fdPosts = person.getFdPosts();
		if (fdPosts != null && fdPosts.size() > 0) {
			for (SysOrgPost p : fdPosts) {
				if (StringUtil.isNull(p.getFdImportInfo())
						|| !p.getFdImportInfo()
								.contains(importInfoPre
										+ "_post_" + type + "_")) {
					postList.add(p);
				}
			}
		}

		// 根据postMap获取对应的岗位
		for (Long deptId : departIds) {
			if (relationMap.containsKey(deptId + "|2")) {
				SysOrgPost currentPost = null;
				if (postMap.containsKey(
						importInfoPre + "_post_" + type + "_" + deptId)) {
					currentPost = postMap.get(
							importInfoPre + "_post_" + type + "_" + deptId);
				} else {
					// 没有对应的部门_成员的岗位，创建一个
					currentPost = new SysOrgPost();
					SysOrgDept dept = (SysOrgDept) sysOrgDeptService
							.findByPrimaryKey(relationMap.get(deptId + "|2"),null,true);
					currentPost.setFdName(dept.getFdName() + "_" + postType);
					currentPost.setFdParent(dept);
					currentPost.setFdImportInfo(
							importInfoPre + "_post_" + type + "_" + deptId);
					sysOrgPostService.add(currentPost);
					postMap.put(currentPost.getFdImportInfo(),
							currentPost);
				}

				if ("2".equals(type)
						&& isLeader(deptId, departIds, isLeaderInDept)
						|| "8".equals(type)) {
					postList.add(currentPost);
				}

			} else {
				logger.warn("人员  " + name + "的部门(Id)："
						+ deptId + " 不在同步范围内，故不创建对应的岗位关系");
				continue;
			}

		}
		person.setFdPosts(postList);
	}

	private boolean isLeader(Long deptId, Long[] departIds,
			Long[] isLeaderInDept) {
		if (isLeaderInDept != null && isLeaderInDept.length > 0) {
			for (int i = 0; i < departIds.length; i++) {
				if (deptId.equals(departIds[i])) {
					return isLeaderInDept[i] == 1 ? true : false;
				}
			}
		}
		return false;
	}

	/*
	 * 处理需要同步到ekp的企业微信组织信息(部门)
	 */
	private void updateSyncOrgElements() throws Exception {
		if (StringUtil.isNotNull(wxOrgId2ekp)) {
			logger.warn("配置了企业微信根部门：" + wxOrgId2ekp);
			String[] orgIdsArr = wxOrgId2ekp.split(";");
			for (String rootDeptId : orgIdsArr) {
				List<WxDepart> departsList = wxworkApiService
						.departGet(rootDeptId);
				if (departsList == null || departsList.size() == 0) {
					throw new Exception("获取部门列表异常 id：" + rootDeptId);
				}
				updateOrgElements(rootDeptId, departsList);
			}

		} else {
			logger.warn("没有配置企业微信根部门，默认同步企业微信的全部组织架构信息");
			List<WxDepart> departsList = wxworkApiService
					.departGet(String.valueOf(WX_ROOT_DEPT_ID));
			if (departsList == null || departsList.size() == 0) {
				throw new Exception("获取部门列表异常 id：" + WX_ROOT_DEPT_ID);
			}
			updateOrgElements(String.valueOf(WX_ROOT_DEPT_ID), departsList);
		}

	}

	/*
	 * 根据上级部门rootDeptId处理该部门以及其下级部门
	 */
	private void updateOrgElements(String rootDeptId,
			List<WxDepart> departsList) throws Exception {

		// 先创建上级部门，departsList含有上级部门
		WxDepart rootDept = null;
		for (WxDepart dept : departsList) {
			if (rootDeptId.equals(dept.getId().toString())) {
				rootDept = dept;
				break;
			}
		}
		if (rootDept == null) {
			throw new Exception("部门列表找不到根部门id:" + rootDeptId);
		}

		if (departsList == null || departsList.size() == 0) {
			throw new Exception("部门列表为空：  根部门" + rootDeptId);
		}
		//机构或者部门
		SysOrgElement ekpDept = getEkpDeptByWxId(rootDeptId);
		boolean createFlag = false;
		String temp;
		if (ekpDept == null) {
			// 新增部门
			logger.debug("----新增部门----" + rootDeptId);
			ekpDept = new SysOrgDept();
			ekpDept.setFdImportInfo(importInfoPre + "_dept_" + rootDept.getId());
			createFlag = true;
			temp = "新增部门:" + rootDept.getName();
		} else {
			// 更新部门
			logger.debug("----更新部门----" + ekpDept.getFdName());
			temp = "更新部门:" + rootDept.getName();
		}
		setDeptInfo(ekpDept, rootDept, createFlag);
		if(ekpDept instanceof SysOrgOrg){
			//机构
			sysOrgOrgService.update(ekpDept);
		}else {
			sysOrgDeptService.update(ekpDept);
		}
		logWhenDebug(temp + " ok");
		// 处理部门领导字段
		// 部门领导，此处只是创建一个部门领导的岗位，岗位的人员在更新人员时赋值
		logger.debug(
				"部门：" + rootDept.getName() + "  排序号：" + rootDept.getOrder());
		String deptLeaderSynWay = WeixinWorkConfig.newInstance()
				.getWx2ekpDeptLeaderSynWay();
		if ("syn".equalsIgnoreCase(deptLeaderSynWay)
				|| ("addSyn".equalsIgnoreCase(deptLeaderSynWay)
						&& createFlag)) {
			// 判断postMap有没有对应的岗位，如果没有则新增
			if (postMap.containsKey(
					importInfoPre + "_post_2_" + rootDept.getId())) {
				ekpDept.setHbmThisLeader(postMap
						.get(importInfoPre + "_post_2_" + rootDept.getId()));
			} else {
				SysOrgPost post = new SysOrgPost();
				post.setFdName(ekpDept.getFdName() + "_上级");
				post.setFdParent(ekpDept);
				post.setFdImportInfo(
						importInfoPre + "_post_2_" + rootDept.getId());
				sysOrgPostService.add(post);
				ekpDept.setHbmThisLeader(post);
				postMap.put(post.getFdImportInfo(),
						post);
			}

		}

		// 处理当前部门的人员信息
		updatePersonInfo(rootDept);

		String fdId = ekpDept.getFdId();
		logger.warn("部门fdId：" + fdId);
		if (!relationMap.containsKey(rootDeptId + "|2")) {
			relationMap.put(rootDeptId + "|2", fdId);
			//保存到映射表中
			WxworkOmsRelationModel model = new WxworkOmsRelationModel();
			logger.warn("rootDept:" + rootDept.getId().toString() + "  ");
			model.setFdAppPkId(rootDeptId);
			model.setFdEkpId(fdId);
			model.setFdAppKey(getAppKey());
			wxworkOmsRelationService.add(model);
			
		}
		// logger.warn("部门id:" + ekpDept);
		if (departsList.size() >= 2) {
			for (WxDepart dept : departsList) {
				if (rootDeptId.equals(dept.getId().toString())) {
					continue;
				}
				updateOrgElements(String.valueOf(dept.getId()), wxworkApiService
						.departGet(String.valueOf(dept.getId())));
			}

		}

	}

	/*
	 * 处理单个部门信息
	 */
	private void updatePersonInfo(WxDepart rootDept) throws Exception {
		List<WxUser> userList = wxworkApiService.userList(rootDept.getId(),
				false, null);
		if (userList == null || userList.size() == 0) {
			logger.warn("部门：" + rootDept.getName() + "  下没有员工！");
			return;
		}
		long caltime = System.currentTimeMillis();
		logger.info("同步部门 " + rootDept.getName() + " 下的员工开始...");
		for (WxUser wxuser : userList) {
			String msg = "\t开始同步用户${name}(userid=${userid},mobile=${mobileNo})..."
					.replace("${name}", wxuser.getName())
					.replace("${userid}", wxuser.getUserId())
					.replace("${mobileNo}", wxuser.getMobile()==null?"":wxuser.getMobile());
			logger.debug(msg);
			// 禁用的用户直接删除
			if (wxuser.getStatus() == 2) {
				cleanData(wxuser.getUserId());
				log("！！！ 用户  " + wxuser.getName() + " 处于禁用状态，ekp中将置为无效");
				logger.warn(
						"！！！ 用户  " + wxuser.getName() + " 处于禁用状态，ekp中将置为无效");
				continue;
			}
			SysOrgPerson person = getSysOrgPerson(wxuser.getUserId(),
					wxuser.getName(), wxuser.getMobile());
			String addOrUpdate = "新增";
			if (person != null) {
				// 更新
				addOrUpdate = "更新";
				setUserInfo(person, wxuser, false);
			}

			if (person == null) {
				msg += "根据匹配原则(企业微信映射信息/EKP人员同步信息/手机号)无法匹配对应人员，直接新增人员${name}"
						.replace("${name}", wxuser.getName());
				logger.debug(msg);
				person = new SysOrgPerson();
				setUserInfo(person, wxuser, true);
			}

			if (person != null) {
				sysOrgPersonService.getBaseDao().getHibernateSession()
						.merge(person);
				logWhenDebug(
						"【" + rootDept.getName() + "】" + addOrUpdate + "人员("
							+ wxuser.getName() + ")信息,处理  ok");
				// 建立人员映射关系
				updateRelation(wxuser.getUserId(), person.getFdId(), "8");

			}

		}
		logger.info("同步部门 " + rootDept.getName() + " 下的员工结束，耗时："
				+ (System.currentTimeMillis() - caltime) / 1000);
	}

	/*
	 * 获取个人信息
	 */
	private void setUserInfo(SysOrgPerson person, WxUser wxuser,
			boolean isCreate) {
        
		//记录人员-部门关系
		if (!wxPersonDeptMap.containsKey(wxuser.getUserId()) || isCreate) {
			// 添加人员-部门关系
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", wxuser.getName());
			map.put("is_leader_in_dept", wxuser.getIsLeaderInDept());
			map.put("main_department", wxuser.getMainDepartment());
			map.put("department", wxuser.getDepartIds());
			map.put("order", wxuser.getOrder());
			map.put("isCreate", isCreate);
			wxPersonDeptMap.put(wxuser.getUserId(), map);
		}
		person.setFdIsAvailable(true);
		person.setFdImportInfo(importInfoPre + "_person_" + wxuser.getUserId());
		wxAllIds.add(wxuser.getUserId() + "|8");
		WeixinWorkConfig config = WeixinWorkConfig.newInstance();

		// 名称
		String dnameSynWay = config.getWx2ekpNameSynWay();
		if ("syn".equalsIgnoreCase(dnameSynWay)
				|| ("addSyn".equalsIgnoreCase(dnameSynWay)
						&& isCreate)) {
			person.setFdName(wxuser.getName());
		}

		// 登录名
		String loginNameSynWay = config.getWx2ekpLoginNameSynWay();
		if ("syn".equalsIgnoreCase(loginNameSynWay)
				|| ("addSyn".equalsIgnoreCase(loginNameSynWay)
						&& isCreate)) {
			String loginNameSynKey = config.getWx2ekpLoginName();
			if ("mobile".equals(loginNameSynKey)
					&& StringUtil.isNotNull(wxuser.getMobile())) {
				person.setFdLoginName(wxuser.getMobile());
			} else {
				person.setFdLoginName(wxuser.getUserId());
			}
		}

		// 手机号
		String mobileSynWay = config.getWx2ekpMobileSynWay();
		if ("syn".equalsIgnoreCase(mobileSynWay)
				|| ("addSyn".equalsIgnoreCase(mobileSynWay)
						&& isCreate)) {
			person.setFdMobileNo(wxuser.getMobile());
		}

		// 昵称
		String aliasSynWay = config.getWx2ekpAliasSynWay();
		if ("syn".equalsIgnoreCase(aliasSynWay)
				|| ("addSyn".equalsIgnoreCase(aliasSynWay)
						&& isCreate)) {
			String aliasSynKey = config.getWx2ekpAlias();
			logger.debug("aliasSynKey:" + aliasSynKey);
			if (StringUtil.isNotNull(aliasSynKey)) {
				String val = (String) BeanUtils.getValue(wxuser, aliasSynKey);
				logger.debug("昵称的值：" + val);
				person.setFdNickName(val);
			}

		}

		// 性别
		String sexSynWay = config.getWx2ekpSexSynWay();
		if ("syn".equalsIgnoreCase(sexSynWay)
				|| ("addSyn".equalsIgnoreCase(sexSynWay)
						&& isCreate)) {
			String sexSynKey = config.getWx2ekpSex();
			logger.debug("sexSynKey:" + sexSynKey);
			if (StringUtil.isNotNull(sexSynKey)) {
				String val = (String) BeanUtils.getValue(wxuser, sexSynKey);
				logger.debug("性别的值：" + val);
				if (StringUtil.isNotNull(val)) {
					if ("1".equals(val)) {
						person.setFdSex("M");
					} else if ("2".equals(val)) {
						person.setFdSex("F");
					}
				}
			}

		}

		// 座机
		String telSynWay = config.getWx2ekpTelSynWay();
		if ("syn".equalsIgnoreCase(telSynWay)
				|| ("addSyn".equalsIgnoreCase(telSynWay)
						&& isCreate)) {
			String telSynKey = config.getWx2ekpTel();
			logger.debug("telSynKey:" + telSynKey);
			if (StringUtil.isNotNull(telSynKey)) {
				String val = (String) BeanUtils.getValue(wxuser, telSynKey);
				logger.debug("座机的值：" + val);
				person.setFdWorkPhone(val);
			}

		}

		// 邮箱
		String emailSynWay = config.getWx2ekpEmailSynWay();
		if ("syn".equalsIgnoreCase(emailSynWay)
				|| ("addSyn".equalsIgnoreCase(emailSynWay)
						&& isCreate)) {
			String emailSynKey = config.getWx2ekpEmail();
			logger.debug("emailSynKey:" + emailSynKey);
			if (StringUtil.isNotNull(emailSynKey)) {
				String val = (String) BeanUtils.getValue(wxuser, emailSynKey);
				logger.debug("邮箱的值：" + val);
				person.setFdEmail(val);
			}

		}

		// 人员密码处理 只有新增才修改
		if (isCreate) {
			if (StringUtil.isNotNull(initPassword)) {
				String password = passwordEncoder.encodePassword(initPassword);
				logger.debug("password:" + password);
				person.setFdPassword(password);
				person.setFdInitPassword(
						PasswordUtil.desEncrypt(SecureUtil
								.BASE64Decoder(initPassword)));
			}
		}

		// 拓展字段
		ExtAttrsClass extattr = wxuser.getExtattr();
		List<Attr> attrs = (extattr == null ? null : extattr.getAttrs());
		// 默认语言
		String defaultLangSynWay = config.getWx2ekpDefaultLangSynWay();
		if ("syn".equalsIgnoreCase(defaultLangSynWay)
				|| ("addSyn".equalsIgnoreCase(defaultLangSynWay)
						&& isCreate)) {
			String defaultLangSynKey = config.getWx2ekpDefaultLang();
			logger.debug("defaultLangSynKey:" + defaultLangSynKey);
			// 拓展字段
			if (StringUtil.isNotNull(defaultLangSynKey) && attrs != null
					&& attrs.size() > 0) {
				String val = null;
				boolean existFlag = false;
				for (Attr attr : attrs) {
					if (defaultLangSynKey.equals(attr.getName())) {
						existFlag = true;
						if (attr.getType() == 1) {
							// 网页
							val = attr.getWeb() == null ? null
									: attr.getWeb().getUrl();
						} else {
							val = attr.getText() == null ? null
									: attr.getText().getValue();
						}
						break;
					}
				}
				if (existFlag) {
					if (StringUtil.isNotNull(val)) {
						// 0/1 true/false 是/否
						if (val.contains("-")) {
							person.setFdDefaultLang(val);
						} else if ("中文".equals(val) || "中文(简体)".equals(val)) {
							person.setFdDefaultLang("zh-CN");
						} else if ("英文".equals(val)) {
							person.setFdDefaultLang("en-US");
						} else if ("日语".equals(val)) {
							person.setFdDefaultLang("ja-JP");
						} else if ("中文(香港)".equals(val) || "香港".equals(val)) {
							person.setFdDefaultLang("zh-HK");
						} else if ("默认语言".equals(val) || "默认".equals(val)) {
							person.setFdDefaultLang(null);
						}
					} else {
						person.setFdDefaultLang(null);
					}
				} else {
					logger.warn("不存在默认语言对应的拓展字段：" + defaultLangSynKey);
				}

			}
			logger.warn("extattr:" + extattr);

		}
		// 关键字
		String keywordSynWay = config.getWx2ekpKeywordSynWay();
		if ("syn".equalsIgnoreCase(keywordSynWay)
				|| ("addSyn".equalsIgnoreCase(keywordSynWay)
						&& isCreate)) {
			String keywordSynKey = config.getWx2ekpKeyword();
			logger.debug("keywordSynKey:" + keywordSynKey);
			if (StringUtil.isNotNull(keywordSynKey) && attrs != null
					&& attrs.size() > 0) {
				String val = null;
				boolean existFlag = false;
				for (Attr attr : attrs) {
					if (keywordSynKey.equals(attr.getName())) {
						existFlag = true;
						if (attr.getType() == 1) {
							// 网页
							val = attr.getWeb() == null ? null
									: attr.getWeb().getUrl();
						} else {
							val = attr.getText() == null ? null
									: attr.getText().getValue();
						}
						break;
					}
				}
				if (existFlag) {
					if (StringUtil.isNotNull(val)) {
						person.setFdKeyword(val);
					} else {
						person.setFdKeyword("");
					}

				} else {
					logger.warn("不存在关键字对应的拓展字段：" + keywordSynKey);
				}
			}
		}

		// 短号
		String shortSynWay = config.getWx2ekpShortSynWay();
		if ("syn".equalsIgnoreCase(shortSynWay)
				|| ("addSyn".equalsIgnoreCase(shortSynWay)
						&& isCreate)) {
			String shortSynKey = config.getWx2ekpShort();
			logger.debug("shortSynKey:" + shortSynKey);
			if (StringUtil.isNotNull(shortSynKey) && attrs != null
					&& attrs.size() > 0) {
				String val = null;
				boolean existFlag = false;
				for (Attr attr : attrs) {
					if (shortSynKey.equals(attr.getName())) {
						existFlag = true;
						if (attr.getType() == 1) {
							// 网页
							val = attr.getWeb() == null ? null
									: attr.getWeb().getUrl();
						} else {
							val = attr.getText() == null ? null
									: attr.getText().getValue();
						}
						break;
					}
				}
				if (existFlag) {
					if (StringUtil.isNotNull(val)) {
						person.setFdShortNo(val);
					} else {
						person.setFdShortNo("");
					}

				} else {
					logger.warn("不存在短号对应的拓展字段：" + shortSynKey);
				}
			}
		}
		// 是否业务相关
		String isBussinessSynWay = config.getWx2ekpIsBussinessSynWay();
		if ("syn".equalsIgnoreCase(isBussinessSynWay)
				|| ("addSyn".equalsIgnoreCase(isBussinessSynWay)
						&& isCreate)) {
			String isBussinessSynKey = config.getWx2ekpIsBussiness();
			logger.debug("isBussinessSynKey:" + isBussinessSynKey);
			if (StringUtil.isNotNull(isBussinessSynKey) && attrs != null
					&& attrs.size() > 0) {
				String val = null;
				boolean existFlag = false;
				for (Attr attr : attrs) {
					if (isBussinessSynKey.equals(attr.getName())) {
						existFlag = true;
						if (attr.getType() == 1) {
							// 网页
							val = attr.getWeb() == null ? null
									: attr.getWeb().getUrl();
						} else {
							val = attr.getText() == null ? null
									: attr.getText().getValue();
						}
						break;
					}
				}
				if (existFlag) {
					if (StringUtil.isNotNull(val)) {
						if ("1".equals(val) || "true".equalsIgnoreCase(val)
								|| "是".equals(val)) {
							person.setFdIsBusiness(true);
						} else if ("0".equals(val)
								|| "false".equalsIgnoreCase(val)
								|| "否".equals(val)) {
							person.setFdIsBusiness(false);
						}
					}
				} else {
					logger.warn("不存在是否业务相关对应的拓展字段：" + isBussinessSynKey);
				}
			}
		}

		// 备注
		String remarkSynWay = config.getWx2ekpremarkSynWay();
		if ("syn".equalsIgnoreCase(remarkSynWay)
				|| ("addSyn".equalsIgnoreCase(remarkSynWay)
						&& isCreate)) {
			String remarkSynKey = config.getWx2ekpremark();
			logger.debug("remarkSynKey:" + remarkSynKey);
			if (StringUtil.isNotNull(remarkSynKey) && attrs != null
					&& attrs.size() > 0) {
				String val = null;
				boolean existFlag = false;
				for (Attr attr : attrs) {
					if (remarkSynKey.equals(attr.getName())) {
						existFlag = true;
						if (attr.getType() == 1) {
							// 网页
							val = attr.getWeb() == null ? null
									: attr.getWeb().getUrl();
						} else {
							val = attr.getText() == null ? null
									: attr.getText().getValue();
						}
						break;
					}
				}
				if (existFlag) {
					if (StringUtil.isNotNull(val)) {
						person.setFdMemo(val);
					} else {
						person.setFdMemo("");
					}

				} else {
					logger.warn("不存在备注对应的拓展字段：" + remarkSynKey);
				}
			}
		}


	}


	/*
	 * 更新对照表信息
	 */
	private void updateRelation(String fdAppId, String ekpId, String type)
			throws Exception {
		if (!relationMap.containsKey(fdAppId + "|" + type)) {
			log("\t userid =" + fdAppId + "的数据关联已建立");
			WxworkOmsRelationModel model = new WxworkOmsRelationModel();
			model.setFdAppPkId(fdAppId);
			model.setFdEkpId(ekpId);
			model.setFdAppKey(getAppKey());
			wxworkOmsRelationService.add(model);
			relationMap.put(model.getFdAppPkId() + "|" + type,
					model.getFdEkpId());
		} else if (relationMap.containsKey(fdAppId + "|" + type)
				&& !relationMap.get(fdAppId + "|" + type).equals(ekpId)) {
			// 对照表有记录，但是对应的ekp的其他用户
			String sql = "";
			if ("2".equals(type)) {
				sql = "select m.fd_id,m.fd_ekp_id,m.fd_app_pk_id,d.fd_name from wxwork_oms_relation_model m,sys_org_element d where m.fd_ekp_id=d.fd_id and d.fd_org_type in (1,2) and m.fd_app_pk_id='"+fdAppId+"'";
			} else {
				sql = "select m.fd_id,m.fd_ekp_id,m.fd_app_pk_id,d.fd_name,p.fd_login_name from wxwork_oms_relation_model m,sys_org_person p,sys_org_element d where m.fd_ekp_id=p.fd_id and m.fd_ekp_id=d.fd_id and m.fd_app_pk_id='"+fdAppId+"'";
			}
			SQLQuery sqlQuery = wxworkOmsRelationService.getBaseDao().getHibernateSession()
					.createSQLQuery(sql);
			int total = sqlQuery.list().size();
			if (total > 0) {
				List dlist = sqlQuery.list();
				Object[] o = null;
				if(total == 1){
					for (int i = 0; i < dlist.size(); i++) {
						o = (Object[]) dlist.get(i);
						WxworkOmsRelationModel model  = (WxworkOmsRelationModel) wxworkOmsRelationService.findByPrimaryKey(o[0].toString(),null,true);
						model.setFdEkpId(ekpId);
						wxworkOmsRelationService.update(model);
					}
				}else{
					logger.warn("有多个映射关系，先删除所有映射，后新增(微信id:"+fdAppId+")");
					for (int i = 0; i < dlist.size(); i++) {
						o = (Object[]) dlist.get(i);
						wxworkOmsRelationService.delete(o[0].toString());
					}
					WxworkOmsRelationModel model = new WxworkOmsRelationModel();
					model.setFdAppPkId(fdAppId);
					model.setFdEkpId(ekpId);
					model.setFdAppKey(getAppKey());
					wxworkOmsRelationService.add(model);
					relationMap.put(model.getFdAppPkId() + "|" + type,
							model.getFdEkpId());
					relationMap.put(fdAppId + "|" + type,ekpId);
				}
			}
		}
	}

	// 获取人员数据
	private SysOrgPerson getSysOrgPerson(String userid, String name,
			String mobileNo) throws Exception {
		SysOrgPerson person = null;
		String msg = "\t";
		logger.debug("relationMap 大小："+relationMap.size());
		logger.debug("mobileMap 大小："+mobileMap.size());
		logger.debug("loginNameMap 大小："+loginNameMap.size());
		if (person == null && relationMap.containsKey(userid + "|8")) {
			String ekpid = relationMap.get(userid + "|8");
			person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(ekpid,
					null, true);
			if (person != null) {
				msg += "根据中间表映射信息${userid}完成用户${name}匹配，直接更新该用户信息"
						.replace("${userid}", userid).replace("${name}",
								name);
			}
			return person;
		}
		if (person == null && StringUtil.isNotNull(userid)
				&& loginNameMap.containsKey(userid)) {
			person = loginNameMap.get(userid);
			if (person != null) {
				msg += "根据企业微信userId信息${no}完成用户${name}匹配，直接更新该用户信息"
						.replace("${no}", userid).replace("${name}", name);
			}
		}
		if (person == null && StringUtil.isNotNull(mobileNo)
				&& mobileMap.containsKey(mobileNo)) {
			person = mobileMap.get(mobileNo);
			if (person != null) {
				msg += "根据企业微信电话号码信息${mobileNo}完成用户${name}匹配，直接更新该用户信息"
						.replace("${mobileNo}", mobileNo)
						.replace("${name}", name);
			}
		}
		if (person == null && StringUtil.isNotNull(userid)
				&& personMap.containsKey(userid)) {
			person = personMap.get(userid);
			if (person != null) {
				msg += "根据组织架构人员信息${importInfo}完成用户${name}匹配，直接更新该用户信息"
						.replace("${importInfo}",
								importInfoPre + "_person_" + userid)
						.replace("${name}", name);
			}
		}
		if (StringUtil.isNotNull(msg)) {
			if (logger.isDebugEnabled()) {
				log(msg);
			}
			logger.debug(msg);
		}

		return person;
	}

	private String getAppKey() {
		return StringUtil.isNull(WXWORK_OMS_APP_KEY)
				? "default"
				: WXWORK_OMS_APP_KEY;
	}
	/*
	 * 处理单个部门的同步信息 createFlag true:新增；false：更新
	 */
	private void setDeptInfo(SysOrgElement ekpDept, WxDepart rootDept,
			boolean createFlag) throws Exception {

		wxAllIds.add(rootDept.getId() + "|2");
		WeixinWorkConfig config = WeixinWorkConfig.newInstance();
		// 部门名称
		String dnameSynWay = config.getWx2ekpDeptNameSynWay();
		if ("syn".equalsIgnoreCase(dnameSynWay)
				|| ("addSyn".equalsIgnoreCase(dnameSynWay)
						&& createFlag)) {
			ekpDept.setFdName(rootDept.getName());
		}

		// 上级部门
		String parentDeptSynWay = config.getWx2ekpDeptParentDeptSynWay();
		if ("syn".equalsIgnoreCase(parentDeptSynWay)
				|| ("addSyn".equalsIgnoreCase(parentDeptSynWay)
						&& createFlag)) {
			String pid = rootDept.getParentid().toString();
			if (StringUtil.isNotNull(pid)) {
				if (relationMap.containsKey(pid + "|2")) {
					String ekpPid = relationMap.get(pid + "|2");
					if(!ekpPid.equals(ekpDept.getFdId())){
						//避免嵌套，自身的上级是自身
						SysOrgElement parent = (SysOrgElement) sysOrgElementService.findByPrimaryKey(ekpPid,null,true);
						if (parent != null) {
							ekpDept.setFdParent(parent);
						}
					}
				} else {
					if (StringUtil.isNotNull(ekpRootOrgId2ekp) && !ekpRootOrgId2ekp.equals(ekpDept.getFdId())) {
						SysOrgElement parent = (SysOrgElement) sysOrgElementService.findByPrimaryKey(ekpRootOrgId2ekp,null,true);
						if (parent != null) {
							ekpDept.setFdParent(parent);
						}
					}
				}
			} else {
				logger.warn(
						"部门：" + rootDept.getName() + " 的上级部门为空：" + rootDept);
			}
		}

		// 排序
		logger.info("部门：" + rootDept.getName() + "  排序号：" + rootDept.getOrder());
		String deptOrderSynWay = config.getWx2ekpDeptOrderSynWay();
		if ("syn".equalsIgnoreCase(deptOrderSynWay)
				|| ("addSyn".equalsIgnoreCase(deptOrderSynWay)
						&& createFlag)) {
			String deptOrderKey = config.getWx2ekpDeptOrder();
			Long order = rootDept.getOrder();
			if ("desc".equals(deptOrderKey)) { // 逆序
				order = 2147483647L - order; // 避免排序号有负数，出现异常
			}
			ekpDept.setFdOrder(order.intValue());

		}


	}

	private SysOrgElement getEkpDeptByWxId(String rootDeptId) throws Exception {
		if (relationMap.containsKey(rootDeptId + "|2")) {
			//机构或者部门
			SysOrgDept dept = (SysOrgDept) sysOrgDeptService.findByPrimaryKey(relationMap.get(rootDeptId + "|2"),null,true);
			if(dept == null){
				return (SysOrgOrg) sysOrgOrgService.findByPrimaryKey(relationMap.get(rootDeptId + "|2"),null,true);
			}else {
				return dept;
			}
		}
		return null;
	}


	/*
	 * 同步前进行数据初始化
	 */
	private void init(boolean initPersonInfo) throws Exception {

		wxworkApiService = WxworkUtils.getWxworkApiService();

		// 微信根部门
		WeixinWorkConfig config = WeixinWorkConfig.newInstance();
		wxOrgId2ekp = config.getWx2ekpWxRootId();
		// 根部门检查
		if (StringUtil.isNotNull(wxOrgId2ekp)) {
			String[] orgIdsArr = wxOrgId2ekp.split(";");
			for (String rootDeptId : orgIdsArr) {
				List<WxDepart> departsList = wxworkApiService
						.departGet(rootDeptId);
				if (departsList == null || departsList.size() == 0) {
					throw new Exception(
							"企业微信配置的根部门信息异常：" + rootDeptId + " ,请检查其部门信息是否正确");
				}
			}
		}

		// 同步到ekp的根部门
		ekpRootOrgId2ekp = config.getWx2ekpEkpOrgId();

		long caltime = System.currentTimeMillis();
		// 获取映射关系relationMap
		List<Map> deptList = wxworkOmsRelationService.getListByType("dept");
		if (deptList != null && deptList.size() > 0) {
			for (Map m : deptList) {
				relationMap.put(m.get("fdAppPKId") + "|2",
						m.get("fdEkpId") + "");
			}
		}

		String temp = "【初始化】获取部门映射关系耗时(秒)："
				+ (System.currentTimeMillis() - caltime) / 1000;
		logger.debug(temp);

		caltime = System.currentTimeMillis();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdOrgType=4 and fdImportInfo like :info");
		hqlInfo.setParameter("info", importInfoPre + "_post_%");
		hqlInfo.setOrderBy("fdIsAvailable");
		List<SysOrgPost> postList = getSysOrgPostService().findList(hqlInfo);
		for (SysOrgPost post : postList) {
			postMap.put(post.getFdImportInfo(), post);
		}
		temp = "【初始化】获取系统中已映射的岗位(上级/成员)数耗时(秒)："
				+ (System.currentTimeMillis() - caltime) / 1000;
		log(temp + "    " + "系统中已映射的岗位(上级/成员)数:" + postMap.size());
		logger.debug(temp);

		if (initPersonInfo) {
			// 初始化密码
			setInitPassword();
			caltime = System.currentTimeMillis();
			List<Map> perList = wxworkOmsRelationService
					.getListByType("person");
			if (perList != null && perList.size() > 0) {
				for (Map m : perList) {
					relationMap.put(m.get("fdAppPKId") + "|8",
							m.get("fdEkpId") + "");
				}
			}
			temp = "【初始化】获取人员映射关系耗时(秒)："
					+ (System.currentTimeMillis() - caltime) / 1000;
			logger.debug(temp);
			// 获取组织架构的登录名缓存，用于根据loginName判断人员是否存在于本系统
			hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"fdIsAvailable = :fdIsAvailable and fdOrgType=8");
			hqlInfo.setParameter("fdIsAvailable", true);
			List<SysOrgPerson> personList = sysOrgPersonService
					.findList(hqlInfo);
			for (SysOrgPerson ele : personList) {
				if (StringUtil.isNotNull(ele.getFdLoginName())) {
					loginNameMap.put(ele.getFdLoginName(), ele);
				}
			}
			logger.debug("系统中已填写登录名的用户数:" + loginNameMap.size());
			temp = "【初始化】获取系统中已填写登录名的用户数耗时(秒)："
					+ (System.currentTimeMillis() - caltime) / 1000;
			logger.debug(temp);
			caltime = System.currentTimeMillis();

			log(temp + "    " + "系统中已填写登录名的用户数:" + loginNameMap.size());

			// 获取组织架构的人员手机号缓存，用于根据mobile判断人员是否存在于本系统
			hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"sysOrgPerson.fdIsAvailable = :fdIsAvailable and sysOrgPerson.fdMobileNo is not null");
			hqlInfo.setParameter("fdIsAvailable", true);
			personList = sysOrgPersonService.findList(hqlInfo);
			for (SysOrgPerson person : personList) {
				if (StringUtil.isNotNull(person.getFdMobileNo())) {
					mobileMap.put(person.getFdMobileNo(), person);
				}
			}
			temp = "【初始化】获取系统中已填写手机号的用户数耗时(秒)："
					+ (System.currentTimeMillis() - caltime) / 1000;
			caltime = System.currentTimeMillis();
			logger.debug("系统中已填写手机号的用户数:" + mobileMap.size());
			log(temp + "    " + "系统中已填写手机号的用户数:" + mobileMap.size());
			logger.info(temp);

			hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdOrgType=8 and fdImportInfo like :info");
			hqlInfo.setParameter("info", importInfoPre + "_person_%");
			hqlInfo.setOrderBy("fdIsAvailable");
			personList = sysOrgPersonService.findList(hqlInfo);
			String imkey = null;
			for (SysOrgPerson person : personList) {
				imkey = person.getFdImportInfo().replace(
						importInfoPre + "_person_",
						"");
				if (StringUtil.isNotNull(imkey)) {
					personMap.put(imkey, person);
				}
			}
			temp = "【初始化】获取从企业微信同步的人员数耗时(秒)："
					+ (System.currentTimeMillis() - caltime) / 1000;

			log(temp + "    " + "系统中已映射(从企业微信同步的)的人员数:" + personMap.size());
			logger.info(temp);

		}

	}

	private void setInitPassword() {
		// 查询初始密码
		try {
			ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil
					.getBean("sysAppConfigService");
			Map orgMap = sysAppConfigService.findByKey(
					"com.landray.kmss.sys.organization.model.SysOrgDefaultConfig");
			if (orgMap != null && orgMap.containsKey("orgDefaultPassword")) {
				Object pwd = orgMap.get("orgDefaultPassword");
				if (pwd != null && StringUtil.isNotNull(pwd.toString())) {
					initPassword = pwd.toString().trim();
					logger.debug("人员初始密码为：" + initPassword);
				} else {
					initPassword = "";
				}
			}
		} catch (Exception e) {
			logger.error("设置初始密码发生异常！", e);
		}
	}

	/*
	 * 打印日志到定时任务
	 */
	private void log(String info) {
		if (jobContext != null) {
			jobContext.logMessage(info);
		}
	}

	/*
	 * debug状态下才打印日志到定时任务
	 */
	private void logWhenDebug(String info) {
		if (jobContext != null && logger.isDebugEnabled()) {
			jobContext.logMessage(info);
		}
	}

	/*
	 * 处理企业微信回调的部门新增以及更新
	 */
	@Override
	public void saveOrUpdateCallbackDept(JSONObject element, boolean isAddFlag)
			throws Exception {
		logger.debug("【企业微信回调】部门新增、更新：" + element + "  isAddFlag:" + isAddFlag);
		WxSynchroInModel wxSynchroInModel = new WxSynchroInModel();
		try {
			getComponentLockService().tryLock(wxSynchroInModel, "omsIn");
			init(false); // 初始化
			// String ekpDeptId =
			// getEkpDeptFdId(Integer.valueOf(element.getString("Id")));
			// 仅更新对照表中的部门，其他当作新增处理
			String fdId = wxworkOmsRelationService.findEkpfdIdByWxId(element.getString("Id"), "dept");
			logger.debug("ekpDeptId:" + fdId);

			// {"ToUserName":"ww16f86a85afc7907e","FromUserName":"sys","CreateTime":"1617794238","MsgType":"event",
			// "Event":"change_contact","ChangeType":"create_party",
			// "Id":"24","Name":"555","ParentId":"14","Order":"99998000"}
			WxDepart depart = null;
			if (isAddFlag) {
				depart = new WxDepart();
				depart.setId(Long.valueOf(element.getString("Id")));
				depart.setName(element.getString("Name"));
				depart.setParentid(Long.valueOf(element.getString("ParentId")));
				depart.setOrder(Long.valueOf(element.getString("Order")));
			} else {
				List<WxDepart> departs = wxworkApiService
						.departGet(element.getString("Id"));
				for (WxDepart d : departs) {
					if (d.getId().toString().equals(element.getString("Id"))) {
						depart = d;
					}
				}
			}

			SysOrgElement dept = null;
			boolean createFlag = false;
			if (StringUtil.isNotNull(fdId)) {
				// 更新
				logger.info("更新部门：" + depart.getName());
				dept = (SysOrgElement) sysOrgElementService.findByPrimaryKey(fdId, null, true);
				if (dept != null) {
					setDeptInfo(dept, depart, false);
					if (dept.getFdOrgType() == 2) {
						sysOrgDeptService.update(dept);
					} else if (dept.getFdOrgType() == 1) {
						sysOrgOrgService.update(dept);
					}
				}
			}

			if (dept == null) {
				// 新增
				createFlag = true;
				logger.info("新增部门：" + depart.getName());
				dept = new SysOrgDept();
				setDeptInfo(dept, depart, true);
				sysOrgDeptService.add(dept);
				WxworkOmsRelationModel model = new WxworkOmsRelationModel();
				model.setFdAppPkId(depart.getId().toString());
				model.setFdEkpId(dept.getFdId());
				model.setFdAppKey(getAppKey());
				wxworkOmsRelationService.add(model);
			}

			String deptLeaderSynWay = WeixinWorkConfig.newInstance()
					.getWx2ekpDeptLeaderSynWay();
			if ("syn".equalsIgnoreCase(deptLeaderSynWay)
					|| ("addSyn".equalsIgnoreCase(deptLeaderSynWay)
					&& createFlag)) {
				// 判断postMap有没有对应的岗位，如果没有则新增
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("fdOrgType=4 and fdImportInfo =:import");
				hqlInfo.setParameter("import",
						importInfoPre + "_post_2_" + depart.getId());
				List<SysOrgPost> postList = getSysOrgPostService()
						.findList(hqlInfo);
				if (postList == null || postList.size() == 0) {
					SysOrgPost post = new SysOrgPost();
					post.setFdName(dept.getFdName() + "_上级");
					post.setFdParent(dept);
					post.setFdImportInfo(
							importInfoPre + "_post_2_" + depart.getId());
					sysOrgPostService.add(post);
					dept.setHbmThisLeader(post);
				}
			}
			clearDataMap();

		} catch (ConcurrencyException e) {
			logger.warn("---定时任务正在执行，回调将被过滤掉，以定时任务为准---部门信息:"+element);
			throw new Exception("组织架构同步定时任务正在执行，回调将被过滤掉");
		} finally {
			getComponentLockService().unLock(wxSynchroInModel);
		}
	}

	@Override
	public void deleteCallbackDept(Integer deptId) throws Exception {
		WxSynchroInModel model = new WxSynchroInModel();
		try {
			getComponentLockService().tryLock(model, "omsIn");
			long time = System.currentTimeMillis();
			if (deptId == null) {
				logger.debug("企业微信回调删除部门的Id为null，直接退出无法执行删除操作");
				return;
			}
			String ekpDeptId = getEkpDeptFdId(deptId);
			SysOrgDept dept = null;
			if (StringUtil.isNotNull(ekpDeptId)) {
				dept = (SysOrgDept) sysOrgDeptService.findByPrimaryKey(ekpDeptId,
						SysOrgDept.class, true);
			} else {
				logger.warn(
						"企业微信删除部门回调EKP时在中间表和通过关键标识找不到对应的部门，无法置为无效，id=" + deptId);
				throw new Exception(
						"企业微信删除部门回调EKP时在中间表和通过关键标识找不到对应的部门，无法置为无效，id=" + deptId);
			}

			if (dept != null) {
				if (UserOperHelper.allowLogOper("deleteDept", "*")) {
					UserOperContentHelper.putUpdate(dept)
							.putSimple("fdIsAvailable", true, false);
				}
				// 如果当前部门下有存在多余的子部门/人员和岗位则移到根目录
				String leaderPost = importInfoPre + "_post_2_" + deptId;
				String personPost = importInfoPre + "_post_8_" + deptId;
				List<SysOrgElement> subEles = sysOrgElementService.findList(
						"hbmParent.fdId='" + dept.getFdId() + "'",
						null);
				for (SysOrgElement ele : subEles) {
					if (ele.getFdOrgType() == 4 && (leaderPost
							.equalsIgnoreCase(ele.getFdImportInfo())
							|| personPost
							.equalsIgnoreCase(ele.getFdImportInfo()))) {
						ele.setFdIsAvailable(false);
						if (ele.getHbmPersons() != null) {
							ele.getHbmPersons().clear();
						}
						sysOrgElementService.update(ele);
					} else {
						ele.setFdParent(null);
						sysOrgElementService.update(ele);
					}
				}
				dept.setFdIsAvailable(false);
				sysOrgElementService.update(dept);
				wxworkOmsRelationService.deleteByKey(dept.getFdId(),
						getAppKey());
			}
			logger.debug("企业微信回调删除部门共耗时:"
					+ ((System.currentTimeMillis() - time) / 1000) + " s");
		} catch (ConcurrencyException e) {
			logger.warn("---定时任务正在执行，回调将被过滤掉，以定时任务为准----部门id:"+deptId);
			throw new Exception("组织架构同步定时任务正在执行，回调将被过滤掉");
		} finally {
			getComponentLockService().unLock(model);
		}
	}

	private String getEkpDeptFdId(Integer deptId) throws Exception {
		
		//先根据对照表找
		 String fdId = wxworkOmsRelationService.findEkpfdIdByWxId(String.valueOf(deptId), "dept");
		 if(StringUtil.isNotNull(fdId)){
			//根据关键字找
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("fdId");
			hqlInfo.setWhereBlock("fdOrgType in(1,2) and fdImportInfo=:info");
			hqlInfo.setParameter("info", importInfoPre + "_dept_" + deptId);
			fdId = (String) sysOrgElementService.findFirstOne(hqlInfo);
		 }
		return fdId;
	}

	@Override
	public void saveOrUpdateCallbackUser(JSONObject element, boolean isAddFlag)
			throws Exception {

		WxSynchroInModel model = new WxSynchroInModel();
		try {
			getComponentLockService().tryLock(model, "omsIn");
			if (element != null && element.containsKey("Status")
					&& "2".equals(element.getString("Status"))) {
				// 禁用人员
				deleteCallbackUser(element.getString("UserID"));
				return;
			}
			logger.debug("【企业微信回调】人员新增、更新：" + element + "  isAddFlag:" + isAddFlag);
			init(true); // 初始化
			String result = wxworkApiService.userGet(element.getString("UserID"));
			logger.warn("result:"+result);
			WxUser wxuser = com.alibaba.fastjson.JSONObject.parseObject(result,
					WxUser.class);

			String msg = "\t【企业微信回调】开始同步用户${name}(userid=${userid},mobile=${mobileNo})..."
					.replace("${name}", wxuser.getName())
					.replace("${userid}", wxuser.getUserId())
					.replace("${mobileNo}", wxuser.getMobile());
			logger.debug(msg);
			SysOrgPerson person = getSysOrgPerson(wxuser.getUserId(),
					wxuser.getName(), wxuser.getMobile());
			String addOrUpdate = "新增";
			if (person != null) {
				// 更新
				addOrUpdate = "更新";
				setUserInfo(person, wxuser, false);
			}

			if (person == null) {
				msg += "根据匹配原则(企业微信映射信息/EKP人员同步信息/手机号)无法匹配对应人员，直接新增人员${name}"
						.replace("${name}", wxuser.getName());
				logger.debug(msg);
				person = new SysOrgPerson();
				setUserInfo(person, wxuser, true);
			}

			if (person != null) {
				logger.warn(addOrUpdate + " 人员:" + person.getFdName() + " ok");
				sysOrgPersonService.getBaseDao().getHibernateSession()
						.merge(person);
				// 建立人员映射关系
				updateRelation(wxuser.getUserId(), person.getFdId(), "8");
			}

			updatePersons();

			clearDataMap();
		} catch (ConcurrencyException e) {
			logger.warn("---定时任务正在执行，回调将被过滤掉，以定时任务为准----用户信息:"+element);
			throw new Exception("组织架构同步定时任务正在执行，回调将被过滤掉");
		} finally {
			getComponentLockService().unLock(model);
		}
	}

	@Override
	public void deleteCallbackUser(String userid) throws Exception {
		logger.warn("【企业微信回调】删除用户：" + userid);
		long time = System.currentTimeMillis();
		if (StringUtil.isNull(userid)) {
			logger.warn("企业微信回调删除人员的Id为null，直接退出无法执行删除操作");
			return;
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdOrgType=8 and fdImportInfo=:info");
		hqlInfo.setParameter("info", importInfoPre + "_person_" + userid);
		List<SysOrgPerson> persons = sysOrgPersonService.findList(hqlInfo);
		if (persons != null && persons.size() > 0) {
			for (int i = 0; i < persons.size(); i++) {
				SysOrgPerson person = persons.get(i);
				if (person != null) {
					person.setFdIsAvailable(new Boolean(false));
					if (UserOperHelper.allowLogOper("deleteUser", "*")) {
						UserOperContentHelper.putUpdate(person)
								.putSimple("fdIsAvailable", true, false);
					}
					if (i > 0) {
						person.setFdImportInfo("");
					}
					sysOrgPersonService.update(person);
					wxworkOmsRelationService.deleteByKey(person.getFdId(),
							getAppKey());
				} else {
					logger.debug(
							"企业微信删除人员回调EKP时找不到对应的人员，无法置为无效，id=" + userid);
				}
			}
		} else {
			// 手动映射的人员信息
			WxworkOmsRelationModel model = wxworkOmsRelationService
					.findByUserId(userid);
			if (model == null) {
				throw new Exception("映射表没有对应的记录，userid:" + userid);
			}
			SysOrgPerson person = (SysOrgPerson) sysOrgPersonService
					.findByPrimaryKey(model.getFdEkpId(), SysOrgPerson.class,
							true);
			if (person != null) {
				person.setFdIsAvailable(new Boolean(false));
				if (UserOperHelper.allowLogOper("deleteUser", "*")) {
					UserOperContentHelper.putUpdate(person)
							.putSimple("fdIsAvailable", true, false);
				}
				sysOrgPersonService.update(person);
				wxworkOmsRelationService.deleteByKey(person.getFdId(),
						getAppKey());
			} else {
				logger.debug(
						"企业微信删除人员回调EKP时找不到对应的人员，无法置为无效，id=" + userid);
			}
		}

		logger.debug("企业微信回调删除人员共耗时:"
				+ ((System.currentTimeMillis() - time) / 1000) + " s");

	}

}
