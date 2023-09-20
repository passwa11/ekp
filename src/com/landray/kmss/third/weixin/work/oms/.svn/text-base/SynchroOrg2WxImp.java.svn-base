package com.landray.kmss.third.weixin.work.oms;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import com.landray.kmss.util.*;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.sys.organization.webservice.out.ISysSynchroGetOrgWebService;
import com.landray.kmss.sys.organization.webservice.out.SysSynchroGetOrgInfoContext;
import com.landray.kmss.sys.organization.webservice.out.SysSynchroOrgResult;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.weixin.model.ThirdWeixinOmsPost;
import com.landray.kmss.third.weixin.service.IThirdWeixinOmsPostService;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.constant.WxworkConstant;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinConfigCustom;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.model.api.WxDepart;
import com.landray.kmss.third.weixin.work.model.api.WxUser;
import com.landray.kmss.third.weixin.work.spi.model.WxworkOmsRelationModel;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;

import net.sf.json.JSONArray;
import org.springframework.transaction.TransactionStatus;

public class SynchroOrg2WxImp
		implements
			SynchroOrg2Wxwork,
			WxworkConstant,
			SysOrgConstant {

	private long WX_ROOT_DEPT_ID = 1;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SynchroOrg2WxImp.class);

	private SysQuartzJobContext jobContext = null;

	private static boolean locked = false;

	private int preCount = 1000;

	private Set<String> newSynDeptIds = null;

	private Set<String> notSynPersonIds = null;

	private CountDownLatch countDownLatch;

	private String lastUpdateTime = null;
	private String roleLastUpdateTime = null;

	private Long synchroStartTime = null;

	private List<Object> rootOrgChildren = null;
	private Map<String, String> relationMap = new HashMap<String, String>();

	private WxworkApiService wxworkApiService = null;

	private ISysSynchroGetOrgWebService sysSynchroGetOrgWebService;

	private ThreadPoolTaskExecutor wxWorkTaskExecutor;

	private Map<String, Map<String, String>> ppMap = null;
	//private Session session = null;

	public ThreadPoolTaskExecutor getWxWorkTaskExecutor() {
		return wxWorkTaskExecutor;
	}

	public void
			setWxWorkTaskExecutor(ThreadPoolTaskExecutor wxWorkTaskExecutor) {
		this.wxWorkTaskExecutor = wxWorkTaskExecutor;
	}

	public ISysSynchroGetOrgWebService getSysSynchroGetOrgWebService() {
		if (sysSynchroGetOrgWebService == null) {
			sysSynchroGetOrgWebService = (ISysSynchroGetOrgWebService) SpringBeanUtil
					.getBean("sysSynchroGetOrgWebService");
		}
		return sysSynchroGetOrgWebService;
	}

	private IWxworkOmsRelationService wxworkOmsRelationService;

	public void setWxworkOmsRelationService(
			IWxworkOmsRelationService wxworkOmsRelationService) {
		this.wxworkOmsRelationService = wxworkOmsRelationService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	private ISysOrgPostService sysOrgPostService = null;

	public ISysOrgPostService getSysOrgPostService() {
		if (sysOrgPostService == null) {
			sysOrgPostService = (ISysOrgPostService) SpringBeanUtil
					.getBean("sysOrgPostService");
		}
		return sysOrgPostService;
	}

	private ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	private ISysOrgPersonService sysOrgPersonService;

	public void setSysOrgPersonService(
			ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	private IThirdWeixinOmsPostService thirdWeixinOmsPostService;
	
	public void setThirdWeixinOmsPostService(
			IThirdWeixinOmsPostService thirdWeixinOmsPostService) {
		this.thirdWeixinOmsPostService = thirdWeixinOmsPostService;
	}
	
	@Override
	public void triggerSynchro(SysQuartzJobContext context) {
		this.jobContext = context;

		if (locked) {
            return;
        }
		if (!"true".equals(WeixinWorkConfig.newInstance().getWxEnabled())) {
			logger.info("企业微信集成已经关闭，故不同步数据");
			context.logMessage("企业微信集成已经关闭，故不同步数据");
			return;
		}
		if (!"true".equals(WeixinWorkConfig.newInstance().getWxOmsOutEnabled())) {
			logger.info("企业微信组织架构接出已经关闭，故不同步数据");
			context.logMessage("企业微信组织架构接出已经关闭，故不同步数据");
			return;
		}
		locked = true;
		synchroStartTime = System.currentTimeMillis();
		try {
			long time = System.currentTimeMillis();
			init();
			long init_end = System.currentTimeMillis();
			logger.debug("初始化耗时，" + (init_end - time));
			context.logMessage("上次同步时间：" + lastUpdateTime);
			logger.debug("上次同步时间：" + lastUpdateTime);

			long getSyncData_start = System.currentTimeMillis();
			getSyncData();  //查询需要同步的数据
			long getSyncData_end = System.currentTimeMillis();
			logger.debug("准备数据耗时，" + (getSyncData_end - getSyncData_start));

			logCount();
			handleDept(syncDepts);
			handlePerson(syncPersons);
			deleteDept(syncDepts);// 先迁移人员，后删除部门，部门中有人员，不能删除
			// 0不处理，1禁用，2删除
			String personHandle = WeixinWorkConfig.newInstance()
					.getWxOmsOrgPersonHandle();
			if ("1".equals(personHandle)) {
				deleteDeptAndPerson(false);
			} else if ("2".equals(personHandle)) {
				deleteDeptAndPerson(true);
			}
			terminate();
			if (logger.isDebugEnabled()) {
				logger.debug("cost time:"
						+ ((System.currentTimeMillis() - time) / 1000) + " s");
			}
			context.logMessage("cost time:"
					+ ((System.currentTimeMillis() - time) / 1000) + " s");
			context.logMessage("本次同步时间：" + lastUpdateTime);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (context != null) {
				context.logError(ex);
			}
		} finally {
			locked = false;
			if(relationMap!=null) {
                relationMap.clear();
            }
			rootOrgChildren = null;
			syncDepts = null;
			deleteDepts = null;
			syncPersons = null;
			deletePersons = null;
			newSynDeptIds = null;
			notSynPersonIds = null;
			ppMap = null;
		}
	}

	private boolean exist(SysOrgElement dept) {
		if (rootOrgChildren == null || rootOrgChildren.isEmpty()) {
			return true;
		}
		return rootOrgChildren.contains(dept.getFdId());
	}

	private String updateDept(SysOrgElement element) throws Exception {
		logger.debug("--------更新部门(包括本次新增的)--------：" + element.getFdName());
		boolean isCreateFlag = false;
		if (newSynDeptIds.contains(element.getFdId())) {
			logger.debug("属于新增的部门：" + element.getFdName());
			isCreateFlag = true;
		} else {
			logger.debug("属于更新的部门：" + element.getFdName());
		}

		if (relationMap.get(element.getFdId()) == null) {
			return "";
		}
		WxDepart dept = new WxDepart();
		WeixinWorkConfig config = WeixinWorkConfig.newInstance();
		// 部门名称
		String dnameSynWay = config.getOrg2wxWorkDeptNameSynWay();
		if (StringUtil.isNull(dnameSynWay)
				|| ("syn".equalsIgnoreCase(dnameSynWay)
						|| ("addSyn".equalsIgnoreCase(dnameSynWay)
								&& isCreateFlag))) {
			dept.setName(element.getFdName());
		}

		// 上级部门
		String dparentDeptSynWay = config.getOrg2wxWorkDeptParentDeptSynWay();
		if (StringUtil.isNull(dparentDeptSynWay)
				|| ("syn".equalsIgnoreCase(dparentDeptSynWay)
						|| ("addSyn".equalsIgnoreCase(dparentDeptSynWay)
								&& isCreateFlag))) {
			String parentId = null;
			if (element.getFdParent() != null) {
				parentId = relationMap.get(element.getFdParent().getFdId());
			}
			if (StringUtil.isNull(parentId)) {
				parentId = "" + WX_ROOT_DEPT_ID;
			}
			dept.setParentid(Long.parseLong(parentId));
		}

		// 排序号
		String dOrderSynWay = config.getOrg2wxWorkDeptOrderSynWay();
		if (StringUtil.isNotNull(dOrderSynWay)) {
			if ("syn".equalsIgnoreCase(dOrderSynWay)
					|| ("addSyn".equalsIgnoreCase(dOrderSynWay)
							&& isCreateFlag)) {
				Integer order = element.getFdOrder();
				Long deptOrder = 0L;
				if (order == null) {
                    order = 0;
                }
				String dOrderSynKey = config.getOrg2wxWorkDeptOrder();
				logger.debug("同步部门排序的key:" + dOrderSynKey);
				if ("desc".equals(dOrderSynKey)) { // 逆序
					deptOrder = 2147483647L - order; // 避免排序号有负数，出现异常
				} else {
					deptOrder = order.longValue();
				}
				dept.setOrder(deptOrder);
			}

		} else {
			if (element.getFdOrder() != null) {
				Integer order = element.getFdOrder();
				if ("1".equals(
						WeixinWorkConfig.newInstance().getWxDeptOrder())) {
					order = 2147483647 - order;
				}
				dept.setOrder(order.longValue());
			}
		}

		dept.setId(Long.parseLong(relationMap.get(element.getFdId())));

		// 英文名
		String usNameSynWay = config.getOrg2wxWorkDeptUSNameSynWay();
		if ("syn".equalsIgnoreCase(usNameSynWay)
				|| ("addSyn".equalsIgnoreCase(usNameSynWay)
						&& isCreateFlag)) {
			String usNameSynKey = config.getOrg2wxWorkDeptUSName();
			logger.debug("部门英文名key：" + usNameSynKey);
			String val = getProperty(usNameSynKey, element);
			dept.setNameEn(val);
		}
		boolean flag = true;
		if ("null".equals(dept.getName()) || dept.getParentid() == null
				|| dept.getId() == null) {
			logger.error("dept has null::" + dept);
			flag = false;
		}
		if (flag) {
			String logInfo = "";
			try {
				JSONObject result = wxworkApiService.departUpdate(dept);
				logInfo += " ,retmsg:updated,微信对应ID:" + dept.getId();

				if (result.getIntValue("errcode") > 0) {
					logInfo += " 失败,出错信息：errorCode="
							+ result.getIntValue("errcode")
							+ ",errorMsg=" + result.getString("errmsg");
					syncCountLimit(result);
				}
			} catch (Exception e) {
				logInfo += " 失败,出错信息：errorCode=" + e.getMessage();
			}
			return logInfo;
		} else {
			return " error has null ::" + dept.toString();
		}
	}

	private void handleDept(List<SysOrgElement> depts) throws Exception {
		// 先增加所有部门，且先挂到根机构下
		int n = 0;
		String logInfo = null;
		SysOrgElement dept = null;
		// 处理新增的部门
		if (depts != null && !depts.isEmpty()) {
			for (int i = 0; i < depts.size(); i++) {
				dept = depts.get(i);
				// 判断是否在同步范围内
				if (!exist(dept)) {
					if (relationMap.containsKey(dept.getFdId())) {
						// 如果不是在范围内而且以前同步过，则删除
						deleteDepts.add(dept);
					}
					// 不在范围内而且之前没有同步过
					continue;
				}
				// 是否同步根组织而且本部门是根组织
				if (!isOmsRootOrg(dept.getFdId())) {
					continue;
				}

				if (dept.getFdOrgType() == ORG_TYPE_ORG
						|| dept.getFdOrgType() == ORG_TYPE_DEPT) {
					if (dept.getFdIsAvailable()) {
						// 以前没有同步过，调用新增接口增加部门到顶级
						if (!relationMap.keySet().contains(dept.getFdId())) {
							newSynDeptIds.add(dept.getFdId()); // 将新增的部门id记录起来，更新部门信息时需要
							WxDepart wxdept = new WxDepart();
							wxdept.setName(dept.getFdName() + "_" + n);// 增加部门挂在根下时，会出现重复名，在此加ID区分，会在更新操作时修改正确
							wxdept.setParentid(WX_ROOT_DEPT_ID);
							if (dept.getFdOrder() != null) {
								Integer order = dept.getFdOrder();
								if("1".equals(WeixinWorkConfig.newInstance().getWxDeptOrder())){
									order = 2147483647 - order;
								}
								wxdept.setOrder(order.longValue());
							}
							n++;
							logInfo = "增加部门到微信 " + dept.getFdName() + ", "
									+ dept.getFdId();
							try {
								JSONObject result = wxworkApiService
										.departCreate(wxdept);

								if (result.getIntValue("errcode") == 0) {
									Long deptId = result.getLong("id");
									logInfo += " created,微信对应ID:" + deptId;
									// 增加映射关系
									addRelation(dept, "" + deptId.longValue());
								} else {
									logInfo += " 失败,出错信息：errorCode="
											+ result.getIntValue("errcode")
											+ ",errorMsg="
											+ result.getString("errmsg");
									syncCountLimit(result);
								}
							} catch (Exception e) {
								logInfo += " 失败,出错信息：error=" + e.getMessage();
							}
							logger.debug(logInfo);
							jobContext.logMessage(logInfo);
						}
					}
				}
			}
		}

		logInfo = "增加部门同步到微信的个数为:" + n + "条";
		logger.debug(logInfo);
		jobContext.logMessage(logInfo);

		// 更新所有部门，主要是更新关系
		n = 0;
		if (depts != null && !depts.isEmpty()) {
			for (int i = 0; i < depts.size(); i++) {
				dept = depts.get(i);
				if (!exist(dept)) {
					continue;
				}
				if (!isOmsRootOrg(dept.getFdId())) {
					continue;
				}
				if (dept.getFdOrgType() == ORG_TYPE_ORG
						|| dept.getFdOrgType() == ORG_TYPE_DEPT) {
					if (dept.getFdIsAvailable()) {
						n++;
						if (dept.getFdParent() == null) {
							// 无父部们，则挂到根部门上1
							updateDept(dept);
							continue;
						}
						if (dept.getFdParent() != null && !relationMap.keySet()
								.contains(dept.getFdParent().getFdId())) {
							logInfo = "警告：从关系中找不到微信所对应的父ID，则移到根部门下，当前部门: "
									+ dept.getFdName() + " " + dept.getFdId()
									+ ",父ID：" + dept.getFdParent().getFdId();
							logInfo += updateDept(dept);
							logger.debug(logInfo);
							jobContext.logMessage(logInfo);
						} else {
							logInfo = "新增后或更新微信的父部门ID ," + dept.getFdName()
									+ "," + dept.getFdId() + ",对应父微信ID:"
									+ (dept.getFdParent() != null
											? relationMap.get(dept.getFdParent()
													.getFdId())
											: "");
							logInfo += updateDept(dept);
							logger.debug(logInfo);
							jobContext.logMessage(logInfo);
						}
					}
				}
			}
		}

		logInfo = "修改部门同步到微信的个数为:" + n + "条";
		logger.debug(logInfo);
		jobContext.logMessage(logInfo);

	}

	private void deleteDept(List<SysOrgElement> depts) throws Exception {
		int n = 0;
		String logInfo = null;
		if (depts == null || depts.isEmpty()) {
			logInfo = "删除部门同步到微信的个数为:" + n + "条";
			logger.debug(logInfo);
			jobContext.logMessage(logInfo);
			return;
		}

		List<SysOrgElement> depts_del_fail = depts;
		int loop = 0;
		// 防止因为部门层级删除先后顺序不正确的原因，进行遍历删除，最多10个循环
		while (loop < 10 && !depts_del_fail.isEmpty()) {
			loop++;
			List<SysOrgElement> depts_del_fail_new = new ArrayList<SysOrgElement>();
			SysOrgElement dept = null;
			for (int i = 0; i < depts_del_fail.size(); i++) {
				dept = depts_del_fail.get(i);
				if (dept.getFdOrgType() == ORG_TYPE_ORG
						|| dept.getFdOrgType() == ORG_TYPE_DEPT) {
					if (!dept.getFdIsAvailable()) {
						if (!relationMap.keySet().contains(dept.getFdId())) {
							logInfo = "警告：从关系中找不到微信对应的ID，当前部门 ："
									+ dept.getFdName() + "," + dept.getFdId()
									+ ",删除忽略";
							logger.debug(logInfo);
							jobContext.logMessage(logInfo);
						} else {
							logInfo = "删除微信中的部门 " + dept.getFdName() + ", "
									+ dept.getFdId() + ",微信中ID："
									+ relationMap.get(dept.getFdId());
							try {
								String departId = relationMap
										.get(dept.getFdId());
								JSONObject result = wxworkApiService
										.departDelete(
												Long.parseLong(departId));

								logInfo += " ,retmsg," + departId + " deleted";

								if (result.getIntValue("errcode") > 0) {
									if (result
											.getIntValue("errcode") != 60006) {
										logInfo += " 失败,出错信息：errorCode="
												+ result.getIntValue("errcode")
												+ ",errorMsg="
												+ result.getString("errmsg");
										syncCountLimit(result);
									} else {
										logger.info(
												logInfo + " 失败,出错信息：errorCode="
														+ result.getIntValue(
														"errcode")
														+ ",errorMsg="
														+ result.getString(
														"errmsg"));
									}
									depts_del_fail_new.add(dept);
								} else {
									n++;
									wxworkOmsRelationService.deleteByKey(
											dept.getFdId(),
											getAppKey());
									relationMap.remove(dept.getFdId());
								}
							} catch (Exception e) {
								logInfo += " 失败,出错信息：errorCode="
										+ e.getMessage();
								depts_del_fail_new.add(dept);
							}
							logger.debug(logInfo);
							jobContext.logMessage(logInfo);
						}
					}
				}
			}
			depts_del_fail = depts_del_fail_new;
		}

		logInfo = "删除部门同步到微信的个数为:" + n + "条";
		logger.debug(logInfo);
		jobContext.logMessage(logInfo);

	}

	private void log(String info) {
		if (jobContext != null) {
			jobContext.logMessage(info);
		}
	}

	/**
	 * 获取同步字段信息
	 * 
	 * @param element
	 * @param isCreate
	 *            是否是新增
	 * @return
	 * @throws Exception
	 */
	private WxUser getUser(SysOrgPerson element, boolean isCreate)
			throws Exception {
		// 获取同步的字段信息
		if (isCreate) {
			logger.debug("---新增用户：" + element.getFdName());
		} else {
			logger.debug("---更新用户：" + element.getFdName());
		}
		WxUser user = new WxUser();

		WeixinWorkConfig config = WeixinWorkConfig.newInstance();

		// 同步性别
		String sexSynWay = config.getOrg2wxWorkSexSynWay();
		logger.debug("性别同步方式");
		if (StringUtil.isNotNull(sexSynWay)) {
			// 配置方式
			if ("syn".equalsIgnoreCase(sexSynWay)
					|| ("addSyn".equalsIgnoreCase(sexSynWay)
							&& isCreate)) {
				String sexSynKey = config.getOrg2wxWorkSex();
				logger.debug("同步性别的key:" + sexSynKey);
				String val = getProperty(sexSynKey, element);
				logger.debug("获取同步的值为：" + val);
				if ("M".equalsIgnoreCase(val) || "1".equalsIgnoreCase(val)) {
					user.setGender("1");
				} else if ("F".equalsIgnoreCase(val)
						|| "2".equalsIgnoreCase(val)) {
					user.setGender("2");
				}
			}
		} else {
			String sex = element.getFdSex();
			if ("M".equalsIgnoreCase(sex)) {
				user.setGender("1");
			} else if ("F".equalsIgnoreCase(sex)) {
				user.setGender("2");
			}
		}

		// 根据配置来确定是选择那种作为企业号的userid，默认是登录名 ,接口为必填项
		String useridSynKey = config.getOrg2wxWorkUserid();
		String wxln = config.getWxLoginName();
		if ("id".equalsIgnoreCase(wxln)
				|| "fdId".equalsIgnoreCase(useridSynKey)) {
			user.setUserId(element.getFdId());
		} else {
			user.setUserId(element.getFdLoginName());
		}

		// 名称
		String nameSynWay = config.getOrg2wxWorkNameSynWay();
		if (StringUtil.isNotNull(nameSynWay)) {
			// 配置方式
			if ("syn".equalsIgnoreCase(nameSynWay)
					|| ("addSyn".equalsIgnoreCase(nameSynWay)
							&& isCreate)) {
				user.setName(element.getFdName());
			} else {
				logger.debug("---------人员名称配置为仅新增时同步，此更新不同步名称---------"
						+ element.getFdName());
			}
		} else {
			user.setName(element.getFdName());
		}

		// 一人多部门
		List<String> wxId_ekpIdList = new ArrayList<String>(); // 部门wxid-ekpid
		List<Long> deptIds = getUserDepartment(element, isCreate,
				wxId_ekpIdList);
		Long[] dIdsArr = null;
		if(deptIds!=null&&deptIds.size()>0) {
			dIdsArr = deptIds.toArray(new Long[deptIds.size()]);
		}
		logger.debug("人员的企业微信部门Id:" + dIdsArr);
		if (dIdsArr != null) {
			user.setDepartIds(dIdsArr);
		} else {
			logger.debug("不需要同步该人员部门信息");
		}

		// 身份 ，必须同步部门时才需要传，个数和部门个数一致
		String isLeaderSynWay = config.getOrg2wxWorkIsLeaderInDeptSynWay();
		if (StringUtil.isNotNull(isLeaderSynWay) && dIdsArr != null) {
			// 配置方式
			if ("syn".equalsIgnoreCase(isLeaderSynWay)
					|| ("addSyn".equalsIgnoreCase(isLeaderSynWay)
							&& isCreate)) {
				logger.debug(
						"-----------根据部门领导和上级领导计算所在的部门内是否为上级-------------");
				List<Long> isLeaders = getLeaderInfoTransation(wxId_ekpIdList,
						element.getFdId());
				if (isLeaders != null && isLeaders.size() > 0) {
					user.setIsLeaderInDept(
							isLeaders.toArray(new Long[isLeaders.size()]));
				}
			}
		}

		// 排序，必须同步部门时才需要传，个数和部门个数一致
		String orderSynWay = config.getOrg2wxWorkOrderInDeptsSynWay();
		if (StringUtil.isNotNull(orderSynWay) && dIdsArr != null) {
			// 配置方式
			if ("syn".equalsIgnoreCase(orderSynWay)
					|| ("addSyn".equalsIgnoreCase(orderSynWay)
							&& isCreate)) {
				List<Long> deptOders = getOrderInfo(wxId_ekpIdList, element); // 获取部门排序
				if (deptOders != null && deptOders.size() > 0) {
					String orderSynKey = config.getOrg2wxWorkOrderInDepts();
					logger.debug("同步排序的key:" + orderSynKey);
					if ("desc".equals(orderSynKey)) { // 逆序
						Long[] newOrder = new Long[deptOders.size()];
						for (int k = 0; k < deptOders.size(); k++) {
							newOrder[k] = 2147483647L - deptOders.get(k);
						}
						user.setOrder(newOrder);

					} else {
						user.setOrder(
								deptOders.toArray(new Long[deptOders.size()]));
					}

				}
			}
		} else {
			if (element.getFdOrder() != null) {
				Integer order = element.getFdOrder();
				if ("1".equals(
						WeixinWorkConfig.newInstance().getWxPersonOrder())) { // 0表示正序，1表示逆序
					order = 2147483647 - order;

				}
				user.setOrder(new Long[] { order.longValue() });
			}
		}

		// 手机号码
		if (StringUtil.isNotNull(element.getFdMobileNo())) {
			user.setMobile(element.getFdMobileNo());
		}

		// 邮箱同步
		String emailSynWay = config.getOrg2wxWorkEmailSynWay();
		if (StringUtil.isNotNull(emailSynWay)) {
			// 配置方式
			if ("syn".equalsIgnoreCase(emailSynWay)
					|| ("addSyn".equalsIgnoreCase(emailSynWay)
							&& isCreate)) {
				String emailSynKey = config.getOrg2wxWorkEmail();
				logger.debug("同步邮箱的key:" + emailSynKey);
				String val = getProperty(emailSynKey, element);
				logger.debug("获取同步邮箱的值为：" + val);
				user.setEmail(val);
			}
		} else {
			user.setEmail(element.getFdEmail());
		}

		// 企业邮箱同步
		String bizEmailSynWay = config.getOrg2wxWorkBizEmailSynWay();
		if (StringUtil.isNotNull(bizEmailSynWay)) {
			// 配置方式
			if ("addSyn".equalsIgnoreCase(bizEmailSynWay) && isCreate){
				String bizEmailSynKey = config.getOrg2wxWorkBizEmail();
				logger.debug("同步企业邮箱的key:" + bizEmailSynKey);
				String val = getProperty(bizEmailSynKey, element);
				logger.debug("获取同步的值为：" + val);
				user.setBizMail(val);
			}
		}

		// 岗位同步
		String positionSynWay = config.getOrg2wxWorkPositionSynWay();
		if (StringUtil.isNotNull(positionSynWay)) {
			// 配置方式
			if ("syn".equalsIgnoreCase(positionSynWay)
					|| ("addSyn".equalsIgnoreCase(positionSynWay)
							&& isCreate)) {
				String positionSynKey = config.getOrg2wxWorkPosition();
				logger.debug("同步岗位的key:" + positionSynKey);
				if ("hbmPosts".equals(positionSynKey)) {
					List<SysOrgElement> posts = element.getHbmPosts();
					if (posts != null && !posts.isEmpty()) {
						String position = ArrayUtil.joinProperty(posts,
								"fdName",
								";")[0];
						if (StringUtil.isNotNull(position)) {
							if (position.length() > 40) {
                                position = position.substring(0, 40) + "...";
                            }
							user.setPosition(position);
						}
					} else {
						user.setPosition("");
					}
				} else {
					String val = getProperty(positionSynKey, element);
					logger.debug("获取同步的值为：" + val);
					if (StringUtil.isNull(val)) {
                        val = "";
                    }
					user.setPosition(val);
				}

			}
		} else {
			if ("true".equals(
					WeixinWorkConfig.newInstance().getWxPostEnabled())) {
				List<SysOrgElement> posts = element.getHbmPosts();
				if (posts != null && !posts.isEmpty()) {
					String position = ArrayUtil.joinProperty(posts, "fdName",
							";")[0];
					if (StringUtil.isNotNull(position)) {
						if (position.length() > 40) {
                            position = position.substring(0, 40) + "...";
                        }
						user.setPosition(position);
					}
				} else {
					user.setPosition("");
				}
			}
		}



		// 办公电话
		String telSynWay = config.getOrg2wxWorkTelSynWay();
		if (StringUtil.isNotNull(telSynWay)) {
			// 配置方式
			if ("syn".equalsIgnoreCase(telSynWay)
					|| ("addSyn".equalsIgnoreCase(telSynWay)
							&& isCreate)) {
				String telSynKey = config.getOrg2wxWorkTel();
				logger.debug("同步座机的key:" + telSynKey);
				String val = getProperty(telSynKey, element);
				logger.debug("获取同步的值为：" + val);
				user.setTelephone(val);
			}
		} else {
			if ("true".equals(
					WeixinWorkConfig.newInstance().getWxOfficePhone())) {
				user.setTelephone(element.getFdWorkPhone());
			}
		}

		// 昵称,别名
		String aliasSynWay = config.getOrg2wxWorkAliasSynWay();
		if ("syn".equalsIgnoreCase(aliasSynWay)
				|| ("addSyn".equalsIgnoreCase(aliasSynWay)
						&& isCreate)) {
			String aliasSynKey = config.getOrg2wxWorkAlias();
			logger.debug("同步别名的key:" + aliasSynKey);
			String val = getProperty(aliasSynKey, element);
			logger.debug("获取同步的值为：" + val);
			user.setAlias(val);
		}

		// 地址
		String addressSynWay = config.getOrg2wxWorkWorkPlaceSynWay();
		if ("syn".equalsIgnoreCase(addressSynWay)
				|| ("addSyn".equalsIgnoreCase(addressSynWay)
						&& isCreate)) {
			String addressSynKey = config.getOrg2wxWorkWorkPlace();
			logger.debug("同步地址的key:" + addressSynKey);
			String val = getProperty(addressSynKey, element);
			logger.debug(val);
			if (StringUtil.isNotNull(val)) {
				if (val.length() > 128) {
					val = val.substring(0, 123) + "...";
				}
				user.setAddress(val);
			} else {
				user.setAddress("");
			}
		}

		// 拓展字段
		List<ThirdWeixinConfigCustom> customData = WeixinWorkConfig.getCustomData();
		if (customData != null && customData.size() > 0) {
			for (ThirdWeixinConfigCustom custom : customData) {
				String synWay = custom.getSynWay();
				logger.debug("-----自定义名称：" + custom.getTitle() + "  同步方式："
						+ custom.getSynWay() + "  对象字段：" + custom.getTarget());
				if ("syn".equalsIgnoreCase(synWay)
						|| ("addSyn".equalsIgnoreCase(synWay)
								&& isCreate)) {
					String val = getProperty(custom.getTarget(), element);
					user.addExtAttr(custom.getTitle(), val);
				}
			}
		}

		return user;
	}


	/*
	 * 获取人员排序
	 */
	private List<Long> getOrderInfo(List<String> ekpDeptIds,
			SysOrgPerson element) {

		List<Long> list = new ArrayList<Long>();
		if (ekpDeptIds == null || ekpDeptIds.isEmpty()) {
            return list;
        }

		// 所属岗位的部门，排序号取岗位的排序号
		Map<String, Long> postOrdersMap = new HashMap<String, Long>();
		if (ppMap.containsKey(element.getFdId())) {
			String[] postids = ppMap.get(element.getFdId())
					.get("postids").split("[,;]");
			if (postids != null && postids.length > 0) {
				for (int i = 0; i < postids.length; i++) {
					SysOrgPost post;
					try {
						post = (SysOrgPost) getSysOrgPostService()
								.findByPrimaryKey(postids[i], SysOrgPost.class,
										true);
						if (post.getFdParent() == null) {
                            continue;
                        }
						Integer fdOrder = post.getFdOrder();
						if (fdOrder == null) {
							fdOrder = 0;
						}
						postOrdersMap.put(post.getFdParent().getFdId(),
								fdOrder.longValue());
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}

				}
			}
		}

		for (String dingId_ekpId : ekpDeptIds) {
			logger.debug("dingId_ekpId:" + dingId_ekpId);
			if (!dingId_ekpId.contains("-")) {
				list.add(0L);
			} else {
				String[] ids = dingId_ekpId.split("-");
				if ((WX_ROOT_DEPT_ID + "").equals(ids[0])) {
					list.add(0L);
				} else {
					String parentDeptid = null;
					if (element.getFdParent() != null) {
						parentDeptid = element.getFdParent().getFdId();
					}
					if (ids[1].equals(parentDeptid)) {
						// 所属部门
						Integer fdOrder = element.getFdOrder();
						if (fdOrder != null) {
							list.add(fdOrder.longValue());
						} else {
							// 没有排序号
							list.add(0L);
						}
					} else {
						// 所属岗位的部门的排序，取岗位的排序号
						if (postOrdersMap.containsKey(ids[1])) {
							list.add(postOrdersMap.get(ids[1]));
						} else {
							list.add(0L);
						}
					}
				}
			}
		}
		return list;
	}

	private List<Long> getLeaderInfoTransation(List<String> ekpDeptIds,
											   String elementId) {
		TransactionStatus status = null;
		try {
			status = TransactionUtils
					.beginNewTransaction();
			List<Long> list = getLeaderInfo(ekpDeptIds, elementId);
			TransactionUtils.getTransactionManager().commit(status);
			return list;
		} catch (Exception e) {
			if (status != null) {
				TransactionUtils.getTransactionManager().rollback(status);
			}
			throw e;
		}
	}

	/*
	 * 获取人员的上级领导信息
	 */
	private List<Long> getLeaderInfo(List<String> ekpDeptIds,
									 String elementId) {
		List<Long> list = new ArrayList<Long>();
		if(ekpDeptIds==null||ekpDeptIds.size()==0) {
            return list;
        }
	    for(String dingId_ekpId:ekpDeptIds) {
	    	logger.debug("dingId_ekpId:"+dingId_ekpId);
			if (!dingId_ekpId.contains("-")) {
	    		list.add(0L);
	    	}else {
				String[] ids = dingId_ekpId.split("-");
				try {
					if ((WX_ROOT_DEPT_ID + "").equals(ids[0])) {
						list.add(0L);
						continue;
					}
					SysOrgElement dept = (SysOrgElement) sysOrgCoreService
							.findByPrimaryKey(ids[1], null, true);
					SysOrgElement hbmThisLeader = dept.getHbmThisLeader();
					if (hbmThisLeader != null) {
						String leadId = hbmThisLeader.getFdId();
						SysOrgElement leader = (SysOrgElement) sysOrgCoreService
								.findByPrimaryKey(leadId, null, true);
						boolean isFlag = hasContain(leader, elementId);
						if (isFlag) {
							list.add(1L);
							continue;
						}
					}
					SysOrgElement hbmSuperLeader = dept.getHbmSuperLeader();
					if (hbmSuperLeader != null) {
						String leadId = hbmSuperLeader.getFdId();
						SysOrgElement leader = (SysOrgElement) sysOrgCoreService
								.findByPrimaryKey(leadId, null, true);
						boolean isFlag = hasContain(leader, elementId);
						if (isFlag) {
							list.add(1L);
							continue;
						}
					}
					list.add(0L);

				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

	    	}
	    }
		return list;
	}

	private boolean hasContain(SysOrgElement leader,
							   String elementId) {
		try {
		if (leader != null) {
			if (ORG_TYPE_PERSON == leader.getFdOrgType()) {
				if (leader.getFdId()
						.equals(elementId)) {
					return true;
				}
			} else if (ORG_TYPE_POST == leader
					.getFdOrgType()) {
					List<Object[]> personPosts = sysOrgPersonService.getBaseDao()
							.getHibernateSession().createNativeQuery(
							"select * from sys_org_post_person pp where pp.fd_postid='"
									+ leader.getFdId()
									+ "' and pp.fd_personid='"
									+ elementId + "'")
							.list();
					if (personPosts != null && personPosts.size() > 0) {
						return true;
					}
			}
		}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	/*
	 * 获取用户的部门信息
	 */
	private List<Long> getUserDepartment(SysOrgPerson element,
			boolean isCreate, List<String> ekpDeptIds) {

		List<Long> deptList = new ArrayList<Long>();
		WeixinWorkConfig config = WeixinWorkConfig.newInstance();
		String departmentSynWay = config.getOrg2wxWorkDepartmentSynWay();
		if (StringUtil.isNotNull(departmentSynWay)) {
			// 配置方式
			if ("syn".equalsIgnoreCase(departmentSynWay)
					|| ("addSyn".equalsIgnoreCase(departmentSynWay)
							&& isCreate)) {
				String departmentSynKey = config.getOrg2wxWorkDepartment();
				logger.debug("同步部门的key:" + departmentSynKey);

				// 单部门
				String parentId = null;
				if (element.getFdParent() != null) {
					parentId = relationMap
							.get(element.getFdParent().getFdId());
					if (StringUtil.isNull(parentId)) {
						parentId = "" + WX_ROOT_DEPT_ID;
					}
					ekpDeptIds.add(parentId + "-"
							+ element.getFdParent().getFdId());
				} else {
					ekpDeptIds.add(WX_ROOT_DEPT_ID + "-1");
					parentId = "" + WX_ROOT_DEPT_ID;
				}
				Long pId = Long.parseLong(parentId);
				deptList.add(pId);

				if ("fdMuilDept".equals(departmentSynKey)) {
					// 主部门设置main_department,暂时不设置
					logger.debug("-------一人多部门处理--------");
					if (ppMap.containsKey(element.getFdId())) {
						String[] parentids = ppMap.get(element.getFdId())
								.get("parentids").split("[,;]");
						logger.debug("parentids:" + parentids);
						for (String pid : parentids) {
							if (StringUtil.isNull(pid)) {
                                continue;
                            }
							if (relationMap.get(pid) != null
									&& !deptList
											.contains(Long.parseLong(relationMap.get(pid)))) {
								ekpDeptIds
										.add(relationMap.get(pid) + "-" + pid);
								deptList.add(
										Long.parseLong(relationMap.get(pid)));
							}
						}
					}

				}
			} else {
				logger.debug("配置人员部门为仅新增时同步，更新时不同步人员部门");
			}
		} else {
			// 旧的配置方式，单部门
			String parentId = null;
			if (element.getFdParent() != null) {
				parentId = relationMap
						.get(element.getFdParent().getFdId());
				if (StringUtil.isNull(parentId)) {
					parentId = "" + WX_ROOT_DEPT_ID;
				}
				ekpDeptIds
						.add(parentId + "-" + element.getFdParent().getFdId());
			} else {
				parentId = "" + WX_ROOT_DEPT_ID;
				ekpDeptIds.add(parentId + "-" + WX_ROOT_DEPT_ID);
			}
			Long pId = Long.parseLong(parentId);
			deptList.add(pId);
		}
		return deptList;
	}

	// 根据字段获取组织架构对应的值
	private String getProperty(String key, SysOrgElement element) {
		try {
			logger.debug("key:" + key + " ,name:" + element.getFdName());
			if (StringUtil.isNull(key)) {
                return null;
            }
			Map<String, Object> customMap = element.getCustomPropMap();
			if (customMap != null && customMap.containsKey(key)) {
				if (customMap.get(key) == null) {
					return null;
				}
				String v = customMap.get(key).toString();
				logger.debug("value:" + v);
				return v;
			} else {
				logger.debug("非自定义字段");
				String _key = "get" + key.substring(0, 1).toUpperCase()
						+ key.substring(1);
				logger.debug("_key:" + _key);
				Class clazz = element.getClass();
				Method method = clazz.getMethod(_key.trim());
				Object obj = method.invoke(element);
				if ("fdStaffingLevel".equals(key) || "hbmParent".equals(key)) { // 对职务特殊处理
					if (obj != null) {
						clazz = obj.getClass();
						method = clazz.getMethod("getFdName");
						obj = method.invoke(obj);
					}
				}
				logger.debug("obj:" + obj);
				return obj == null ? null : obj.toString();
			}

		} catch (Exception e) {
			logger.error("根据字段获取部门的值过程中发生了异常");
			logger.error("", e);
		}
		return null;
	}

	private void handlePerson(List<SysOrgPerson> persons) throws Exception {

		String size = WeixinWorkConfig.newInstance().getWxworkSize();
		if (StringUtil.isNull(size)) {
            size = "2000";
        }
		int rowsize = Integer.parseInt(size);
		int count = persons.size() % rowsize == 0 ? persons.size() / rowsize
				: persons.size() / rowsize + 1;
		countDownLatch = new CountDownLatch(count);
		logger.warn("人员总数据:" + persons.size() + "条,将执行" + count + "次人员分批同步,每次"
				+ size + "条");

		jobContext.logMessage(
				"人员总数据:" + persons.size() + "条,将执行" + count + "次人员分批同步,每次"
						+ size + "条");
		List<SysOrgPerson> temppersons = null;
		for (int i = 0; i < count; i++) {
			logger.warn("执行第" + (i + 1) + "批");
			if (persons.size() > rowsize * (i + 1)) {
				temppersons = persons.subList(rowsize * i, rowsize * (i + 1));
			} else {
				temppersons = persons.subList(rowsize * i, persons.size());
			}
			wxWorkTaskExecutor.execute(new PersonRunner(temppersons));
		}
		try {
			countDownLatch.await(3, TimeUnit.HOURS);
		} catch (InterruptedException exc) {
			exc.printStackTrace();
			logger.error(exc.getMessage(), exc);
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
				handlePersonByRunnble(persons);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally {
				countDownLatch.countDown();
			}
		}
	}

	private void handlePersonByRunnble(List<SysOrgPerson> persons)
			throws Exception {
		// logger.warn("多线程处理人员信息：" + persons);
		String logInfo = null;
		if (persons != null && !persons.isEmpty()) {
			SysOrgPerson person = null;
			for (int i = 0; i < persons.size(); i++) {
				person = persons.get(i);
				if (ORG_TYPE_PERSON == person.getFdOrgType()) {
					if (notSynPersonIds.contains(person.getFdId())) {
						logger.warn("【不同步人员】" + person.getFdName() + ",id"
								+ person.getFdId() + " 同步忽略");
						log("【不同步人员】" + person.getFdName() + ",id"
								+ person.getFdId() + " 同步忽略");
						continue;
					}
					if (person.getFdIsAvailable()) {
						if (!exist(person)) {
							// 不在同步范围内
							if (relationMap.containsKey(person.getFdId())) {
								// 之前有同步过，需要删除
								deletePersons.add(person);
							}
							// 之前没同步到微信
							continue;
						}
						if (person.getFdParent() == null) {
							// 无父部们，则挂到根部门上1
							continue;
						}

						if (!relationMap.keySet().contains(person.getFdId())) {
							// 如果之前没同步过
							addPerson(person);
						} else {
							String wxln = WeixinWorkConfig.newInstance()
									.getWxLoginName();
							if ("id".equalsIgnoreCase(wxln)) {
								updatePerson(person);
							} else {
								/*
								 * if (!person.getFdLoginName().equals(
								 * relationMap.get(person.getFdId()))) { //
								 * 个人loginName有变化 // 用旧的loginName去删除微信中的用户
								 * addNewAnddelOld(person); } else {
								 */
								// TODO 判断手机号有没有变化，如有变化先删和增加，但不能获取手机号
								updatePerson(person);
								// }
							}
						}
					} else {
						if (!relationMap.keySet().contains(person.getFdId())) {
							logInfo = "警告：从关系中找不到微信对应的ID，当前个人 "
									+ person.getFdName() + ", " + ",loginName:"
									+ (person.getFdLoginName() == null
											? ""
											: person.getFdLoginName())
									+ ",id:" + person.getFdId() + ",删除忽略";
							logger.warn(logInfo);
							jobContext.logMessage(logInfo);
						} else {
							String delectOrDisable = WeixinWorkConfig
									.newInstance()
									.getWxOmsPersonDisableHandle();
							logger.warn("删除/禁用人员：" + person.getFdName()
									+ "  处理方式：" + delectOrDisable);

							if (StringUtil.isNotNull(delectOrDisable)
									&& "1".equals(delectOrDisable)) {
								logger.debug(
										"------------------禁用---------------------");
								WxUser wxuser = getUser(person, false);
								try {
									// 禁用人员
									if (relationMap
											.get(person.getFdId()) != null) {
                                        wxuser.setUserId(relationMap
                                                .get(person.getFdId()));
                                    }
									wxuser.setEnable(0);
									wxuser.setDepartIds(null);
									wxuser.setOrder(null);
									wxuser.setIsLeaderInDept(null);
									JSONObject result = wxworkApiService.userUpdate(wxuser);
									logger.debug("result:" + result);
									if (result.getIntValue("errcode") > 0) {
										jobContext.logMessage(
												"禁用人员" + person.getFdId()
														+ "失败！微信异常："
														+ result.getString(
																"errmsg"));
										logger.error(
												"禁用人员" + person.getFdId()
														+ "失败！微信异常："
														+ result.getString(
																"errmsg"));
									} else if (result
											.getIntValue("errcode") == 0) {
										jobContext.logMessage("禁用人员 "
												+ person.getFdName() + "("
												+ person.getFdId() + ") 成功");
										logger.debug("禁用人员 "
												+ person.getFdName() + "("
												+ person.getFdId() + ") 成功");
										wxworkOmsRelationService.deleteByKey(
												person.getFdId(),
												getAppKey());
										relationMap.remove(person.getFdId());
									}
								} catch (Exception e) {
									jobContext.logMessage("禁用人员"
											+ person.getFdId()
											+ "失败！");
									logger.error("禁用人员"
											+ person.getFdId()
											+ "失败！", e);
								}
							} else {
								logger.debug(
										"------------------删除---------------------");
								deletePerson(person);
							}

						}
					}
				}
			}
		}
	}

	private void addPerson(SysOrgPerson element) throws Exception {
		String logInfo = "增加个人到微信 " + element.getFdName() + ", "
				+ element.getFdId() + ",对应父微信ID:"
				+ (element.getFdParent() != null
						? relationMap.get(element.getFdParent().getFdId())
						: "");

		try {
			JSONObject result = wxworkApiService
					.userCreate(getUser(element, true));
			logInfo += " ,created";
			int errcode = result.getIntValue("errcode");
			if (errcode > 0) {
				//企业微信中已存在该手机号
				if(errcode==60104){
					try {
						String userid = wxworkApiService.getUserid(element.getFdMobileNo());
						WxUser wxUser = getUser(element, false);
						wxUser.setEnable(1);
						JSONObject result_update_user = wxworkApiService.userUpdate(wxUser);
						if(result_update_user.getIntValue("errcode")!=0){
							throw new Exception("更新用户失败，返回信息："+result_update_user.toString());
						}
						addRelation(element, userid);
						logger.info("企业微信中已存在该手机号（{}），直接建立映射关系",element.getFdMobileNo());
					}catch (Exception e){
						logger.error(e.getMessage(),e);
					}
				} else {
					logInfo += " 失败,出错信息：errorCode="
							+ result.getIntValue("errcode")
							+ ",errorMsg="
							+ result.getString("errmsg");
					syncCountLimit(result);
					logger.warn(logInfo);
					jobContext.logMessage(logInfo);
				}
			} else if (errcode == 0) {
				String wxln = WeixinWorkConfig.newInstance().getWxLoginName();
				if ("id".equalsIgnoreCase(wxln)) {
					addRelation(element, element.getFdId());
				} else {
					addRelation(element, element.getFdLoginName());
				}
				logger.debug(logInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logInfo += " 失败,出错信息：errorCode=" + e.getMessage();
			logger.warn(logInfo);
			jobContext.logMessage(logInfo);
		}

	}

	private void updatePerson(SysOrgPerson element) throws Exception {
		String logInfo = "更新个人到微信 " + element.getFdName() + ",loginName:"
				+ element.getFdLoginName() + ", id=" + element.getFdId()
				+ ",对应父微信ID:"
				+ (element.getFdParent() != null
						? relationMap.get(element.getFdParent().getFdId())
						: "");
		logInfo += " ,retmsg:";
		String errorCode = "";
		WxUser wxuser = getUser(element, false);
		try {
			if (relationMap.get(element.getFdId()) != null) {
                wxuser.setUserId(relationMap.get(element.getFdId()));
            }
			JSONObject result = wxworkApiService.userUpdate(wxuser);
			logInfo += " updated";

			if (result.getIntValue("errcode") > 0) {
				logInfo += " 失败,出错信息：errorCode="
						+ result.getIntValue("errcode")
						+ ",errorMsg="
						+ result.getString("errmsg");
				syncCountLimit(result);
				logger.warn(logInfo);
				jobContext.logMessage(logInfo);
			} else {
				logger.debug(logInfo);
			}
		} catch (Exception e) {
			logInfo += " 失败,出错信息：errorCode=" + e.getMessage();
			logger.warn(logInfo);
			jobContext.logMessage(logInfo);
		}


		if (StringUtil.isNotNull(errorCode)) {
			// 更新人员到企业号时，如果找不到人则自动新增
			// 找不到该成员,也自动新增60121
			if ("60121".equals(errorCode) || "60111".equals(errorCode)) {
				logInfo = "补增个人到微信 " + element.getFdName() + ",loginName:"
						+ element.getFdLoginName() + ", id=" + element.getFdId()
						+ ",对应父微信ID:"
						+ (element.getFdParent() != null
								? relationMap
										.get(element.getFdParent().getFdId())
								: "");
				logInfo += " ,retmsg:";

				try {
					JSONObject result = wxworkApiService.userCreate(wxuser);
					logInfo += " created";

					if (result.getIntValue("errcode") > 0) {
						logInfo += " 失败,出错信息：errorCode="
								+ result.getIntValue("errcode")
								+ ",errorMsg="
								+ result.getString("errmsg");
						syncCountLimit(result);
						logger.warn(logInfo);
						jobContext.logMessage(logInfo);
					} else {
						logger.debug(logInfo);
					}
				} catch (Exception e) {
					logInfo += " 失败,出错信息：errorCode=" + e.getMessage();
					logger.warn(logInfo);
					jobContext.logMessage(logInfo);
				}

			}
		}

	}

	private void addNewAnddelOld(SysOrgPerson element) throws Exception {
		// 用旧的loginName去删除微信中的用户
		String oldLoginName = relationMap.get(element.getFdId());
		String logInfo = "删除微信中旧的用户 " + element.getFdName() + ","
				+ element.getFdId() + ",loginName:" + oldLoginName;

		try {
			JSONObject result = wxworkApiService.userDelete(oldLoginName);
			logInfo += " ,deleted";

			if (result.getIntValue("errcode") > 0) {
				logInfo += " 失败,出错信息：errorCode="
						+ result.getIntValue("errcode")
						+ ",errorMsg="
						+ result.getString("errmsg");
				syncCountLimit(result);
			}
		} catch (Exception e) {
			logInfo += " 失败,出错信息：errorCode=" + e.getMessage();
		}
		logInfo += "\n";
		wxworkOmsRelationService.deleteByKey(element.getFdId(), getAppKey());
		relationMap.remove(element.getFdId());
		// 增加新的微信用户
		logInfo += "增加新的微信用户 " + element.getFdName() + " " + element.getFdId()
				+ ",loginName:" + element.getFdLoginName();

		try {
			JSONObject result = wxworkApiService
					.userCreate(getUser(element, true));
			logInfo += " ,created";
			String wxln = WeixinWorkConfig.newInstance().getWxLoginName();
			if ("id".equalsIgnoreCase(wxln)) {
				addRelation(element, element.getFdId());
			} else {
				addRelation(element, element.getFdLoginName());
			}

			if (result.getIntValue("errcode") > 0) {
				logInfo += " 失败,出错信息：errorCode="
						+ result.getIntValue("errcode")
						+ ",errorMsg="
						+ result.getString("errmsg");
				syncCountLimit(result);
			}
		} catch (Exception e) {
			logInfo += " 失败,出错信息：errorCode=" + e.getMessage();
		}
		logger.debug(logInfo);
		jobContext.logMessage(logInfo);
	}

	// 单独删除企业微信用户
	@Override
	public String deleteWxUserByEkpId(String fdId) {
		String logInfo = "删除人员的fdId:" + fdId;
		try {
			List<WxworkOmsRelationModel> list = wxworkOmsRelationService
					.findList("fd_ekp_id='" + fdId + "'", null);
			if (list != null && list.size() > 0) {
				WxworkOmsRelationModel model = list.get(0);
				JSONObject result = WxworkUtils.getWxworkApiService()
						.userDelete(model.getFdAppPkId());
				wxworkOmsRelationService.deleteByKey(fdId, getAppKey());
				if (result.getIntValue("errcode") == 0) {
					logInfo += " 成功";
					logger.warn(logInfo);
					return "ok";
				} else {
					logInfo += " 失败,出错信息：errorCode="
							+ result.getIntValue("errcode")
							+ ",errorMsg="
							+ result.getString("errmsg");
				}
			} else {
				logInfo += " 映射表中不存在该用户信息";
			}

		} catch (Exception e) {
			logInfo += " 失败,出错信息：errorCode=" + e.getMessage();
		}
		logger.warn(logInfo);
		return logInfo;
	}

	private void deletePerson(SysOrgPerson element) throws Exception {
		String logInfo = "删除微信中的个人ID " + element.getFdName() + " "
				+ element.getFdId() + "," + relationMap.get(element.getFdId());
		try {
			JSONObject result = wxworkApiService
					.userDelete(relationMap.get(element.getFdId()));
			logInfo += " ,deleted";
			wxworkOmsRelationService.deleteByKey(element.getFdId(),
					getAppKey());
			relationMap.remove(element.getFdId());

			if (result.getIntValue("errcode") > 0) {
				logInfo += " 失败,出错信息：errorCode="
						+ result.getIntValue("errcode")
						+ ",errorMsg="
						+ result.getString("errmsg");
				syncCountLimit(result);
			}
		} catch (Exception e) {
			logInfo += " 失败,出错信息：errorCode=" + e.getMessage();
		}
		logger.debug(logInfo);
		jobContext.logMessage(logInfo);
	}

	private void addRelation(SysOrgElement element, String appPkId)
			throws Exception {
		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(
					"insert into wxwork_oms_relation_model(fd_id,fd_ekp_id,fd_app_pk_id,fd_app_key) values(?,?,?,?)");
			ps.setString(1, IDGenerator.generateID());
			ps.setString(2, element.getFdId());
			ps.setString(3, appPkId);
			ps.setString(4, getAppKey());
			ps.executeUpdate();
			conn.commit();
			// 关系添加到缓存中
			relationMap.put(element.getFdId(), appPkId);
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(conn);
		}
	}

	private String getAppKey() {
		return StringUtil.isNull(WXWORK_OMS_APP_KEY)
				? "default"
				: WXWORK_OMS_APP_KEY;
	}

	private boolean isOmsRootOrg(String rootId) {
		if ("true".equals(WeixinWorkConfig.newInstance().getWxOmsRootFlag())) {
			return true;
		} else if (WeixinWorkConfig.newInstance().getWxOrgId()
				.indexOf(rootId) != -1) {
			return false;
		}
		return true;
	}

	private void logCount() {
		int count = syncPersons.size() + syncDepts.size();
		if (logger.isDebugEnabled()) {
			logger.debug("本次从EKP获取同步组织机构的记录数为 " + count + " 条");
		}
		jobContext.logMessage("本次从EKP获取同步组织机构的记录数为 " + count + " 条");
	}

	private List<SysOrgElement> getAllOrgByRootOrg() throws Exception {
		List<SysOrgElement> allOrgInRootOrg = new ArrayList<SysOrgElement>();
		List allOrgChildren = sysOrgElementService
				.findList("(fdOrgType=1) and fdIsAvailable=1", null);
		for (int i = 0; i < allOrgChildren.size(); i++) {
			SysOrgElement org = (SysOrgElement) allOrgChildren.get(i);
			if (StringUtil
					.isNotNull(WeixinWorkConfig.newInstance().getWxOrgId())) {
				SysOrgElement parent = org.getFdParent();
				while (parent != null) {
					if (WeixinWorkConfig.newInstance().getWxOrgId()
							.indexOf(parent.getFdId()) != -1) {
						allOrgInRootOrg.add(org);
						break;
					}
					parent = parent.getFdParent();
				}
			} else {
				allOrgInRootOrg.add(org);
			}
		}
		return allOrgInRootOrg;
	}

	@SuppressWarnings("unchecked")
	private void init() throws Exception {
		logger.debug("init start");
		wxworkApiService = WxworkUtils.getWxworkApiService();
		WxOmsConfig wxOmsConfig = new WxOmsConfig();
		lastUpdateTime = wxOmsConfig.getLastUpdateTime();
		roleLastUpdateTime = wxOmsConfig.getRoleLastUpdateTime();
		ppMap = new HashMap<String, Map<String, String>>();
		newSynDeptIds = new HashSet<String>();

		// 初始化不需要同步的人员id
		String perIds = WeixinWorkConfig.newInstance()
				.getEkp2wxNoSynPersonIds();
		if (StringUtil.isNotNull(perIds)) {
			notSynPersonIds = new HashSet<>(Arrays.asList(perIds.split(";")));
		} else {
			notSynPersonIds = new HashSet<String>();
		}

		// 获取需要同步的机构
		List<SysOrgElement> allOrgChildrn = getAllOrgByRootOrg();

		if (StringUtil.isNotNull(WeixinWorkConfig.newInstance().getWxRootId())) {
            WX_ROOT_DEPT_ID = Long
                    .parseLong(WeixinWorkConfig.newInstance().getWxRootId());
        }
		String WX_OMS_ROOT_ORG_ID = WeixinWorkConfig.newInstance().getWxOrgId();

		logger.debug("build rootOrgChildren");
		if (StringUtil.isNotNull(WX_OMS_ROOT_ORG_ID)) {
			rootOrgChildren = new ArrayList<Object>();
			String[] orgIds = WX_OMS_ROOT_ORG_ID.split(";");
			for (String orgId : orgIds) {
				SysOrgElement rootOrg = sysOrgCoreService
						.findByPrimaryKey(orgId);
				if (!allOrgChildrn.contains(rootOrg)) {
					allOrgChildrn.add(rootOrg);
				}
				for (SysOrgElement org : allOrgChildrn) {
					rootOrgChildren
							.addAll(sysOrgCoreService.findAllChildrenItem(org,
									SysOrgElement.ORG_TYPE_ORGORDEPT
											| SysOrgElement.ORG_TYPE_PERSON,
									"fdId"));
				}
				}
		}

		List list = wxworkOmsRelationService
				.findList("fdAppKey='" + getAppKey() + "'", null);
		for (int i = 0; i < list.size(); i++) {
			WxworkOmsRelationModel model = (WxworkOmsRelationModel) list.get(i);
			relationMap.put(model.getFdEkpId(), model.getFdAppPkId());
		}

		syncDepts = new ArrayList<SysOrgElement>(500);
		deleteDepts = new ArrayList<SysOrgElement>(50);
		syncPersons = new ArrayList<SysOrgPerson>(2000);
		deletePersons = new ArrayList<SysOrgPerson>(200);
		postMap = new HashMap<String, ThirdWeixinOmsPost>(200);
		
		logger.debug("delete omsPost");
		List<ThirdWeixinOmsPost> plist = thirdWeixinOmsPostService.findList("fdWxHandler=1 and fdWxworkHandler=1", null);
		for(ThirdWeixinOmsPost post:plist){
			thirdWeixinOmsPostService.delete(post);
		}

		logger.debug("update omsPost");
		plist = thirdWeixinOmsPostService.findList(null, null);
		for(ThirdWeixinOmsPost post:plist){
			if(StringUtil.isNotNull(post.getFdPersonIds())){
				post.setDocContent(post.getFdPersonIds());
				post.setFdPersonIds("");
				thirdWeixinOmsPostService.update(post);
			}
			postMap.put(post.getFdPostId(), post);
		}

		// 岗位数据的缓存
		//session = sysOrgPersonService.getBaseDao().getHibernateSession();
		List<Object[]> personPosts = sysOrgPersonService.getBaseDao().getHibernateSession().createNativeQuery(
				"select pp.fd_personid,pp.fd_postid,ele.fd_name,ele.fd_parentid from sys_org_post_person pp,sys_org_element ele where pp.fd_postid=ele.fd_id")
				.list();
		Map<String, String> tempMap = null;
		for (Object[] pps : personPosts) {
			if (pps[0] != null && pps[1] != null && pps[2] != null) {
				// 以人为角度构建的人与岗位关系
				if (ppMap.containsKey(pps[0].toString())) {
					tempMap = ppMap.get(pps[0].toString());
					tempMap.put("postids",
							tempMap.get("postids") + ";" + pps[1].toString());
					tempMap.put("names",
							tempMap.get("names") + ";" + pps[2].toString());
					if (pps[3] == null) {
						tempMap.put("parentids", tempMap.get("parentids"));
					} else {
						tempMap.put("parentids", tempMap.get("parentids") + ";"
								+ pps[3].toString());
					}
				} else {
					tempMap = new HashMap<String, String>();
					tempMap.put("postids", pps[1].toString());
					tempMap.put("names", pps[2].toString());
					if (pps[3] == null) {
						tempMap.put("parentids", "");
					} else {
						tempMap.put("parentids", pps[3].toString());
					}
				}
				ppMap.put(pps[0].toString(), tempMap);
			}

		}

	}

	private void terminate() throws Exception {

		TransactionStatus status = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			WxOmsConfig wxOmsConfig = new WxOmsConfig();
			if (StringUtil.isNotNull(lastUpdateTime)) {
				wxOmsConfig.setLastUpdateTime(lastUpdateTime);
				wxOmsConfig.save();
			}
			if (StringUtil.isNotNull(roleLastUpdateTime)) {
				wxOmsConfig.setRoleLastUpdateTime(roleLastUpdateTime);
				wxOmsConfig.save();
			}
			TransactionUtils.getTransactionManager().commit(status);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (status != null) {
				TransactionUtils.getTransactionManager().rollback(status);
			}
		}

	}

	private List<JSONArray> getSyncOrgElements() throws Exception {
		SysSynchroGetOrgInfoContext infoContext = new SysSynchroGetOrgInfoContext();
		infoContext.setReturnOrgType(
				"[{\"type\":\"org\"},{\"type\":\"dept\"},{\"type\":\"person\"}]");
		infoContext.setCount(preCount);
		List<JSONArray> resultList = new ArrayList<JSONArray>();
		while (true) {
			infoContext.setBeginTimeStamp(lastUpdateTime);
			SysSynchroOrgResult orgResult = getSysSynchroGetOrgWebService()
					.getUpdatedElements(infoContext);
			if (orgResult.getReturnState() == 2) {
				String resultStr = orgResult.getMessage();
				if (StringUtil.isNotNull(resultStr)) {
					resultList.add((JSONArray) JSONArray.fromObject(resultStr));
					lastUpdateTime = orgResult.getTimeStamp();
					roleLastUpdateTime = lastUpdateTime;
				}
				if (orgResult.getCount() < preCount) {
                    break;
                }
			} else {
				logger.error("分批获取组织架构所有信息出错,返回状态值为:" + orgResult.getCount()
						+ ",错误信息为:" + orgResult.getMessage());
				break;
			}
		}
		return resultList;
	}

	// =================================陈亮微信组织架构数据获取性能优化开始=================================
	private long syncTime = 0L;
	private List<SysOrgElement> syncDepts = null;
	private List<SysOrgElement> deleteDepts = null;
	private List<SysOrgPerson> syncPersons = null;
	private List<SysOrgPerson> deletePersons = null;
	private Map<String, ThirdWeixinOmsPost> postMap = null;

	private void syncCountLimit(JSONObject result) {
		if (45009 == result.getIntValue("errcode")) {
			try {
				Thread.sleep(10000);
				logger.debug("接口调用次数超过限制，系统强制线程睡眠10秒！");
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * @throws Exception
	 *             平级部门或机构调整但不在同步的组织架构范围中时，删除调整到平级的部门和机构
	 *             personFlag=true删除，personFlag=false禁用
	 */
	private void deleteDeptAndPerson(boolean personFlag) throws Exception {
		// 删除人员
		if (deletePersons != null && !deletePersons.isEmpty()) {
			for (int i = 0; i < deletePersons.size(); i++) {
				if (personFlag) {
					logger.debug("删除人员：" + deletePersons.get(i).getFdName());
					deletePerson(deletePersons.get(i));
				} else {
					WxUser wxuser = getUser(deletePersons.get(i), false);
					try {
						//禁用人员TODO
						if (relationMap
								.get(deletePersons.get(i).getFdId()) != null) {
                            wxuser.setUserId(relationMap
                                    .get(deletePersons.get(i).getFdId()));
                        }
						wxuser.setEnable(0);
						JSONObject result = wxworkApiService.userUpdate(wxuser);

						if (result.getIntValue("errcode") > 0) {
							jobContext.logMessage(
									"禁用人员" + deletePersons.get(i).getFdId()
											+ "失败！微信异常："
											+ result.getString("errmsg"));
							logger.warn(
									"禁用人员" + deletePersons.get(i).getFdId()
											+ "失败！微信异常："
											+ result.getString("errmsg"));
						} else if (result.getIntValue("errcode") == 0) {
							jobContext.logMessage(
									"禁用人员" + deletePersons.get(i).getFdName()
											+ "  "
											+ deletePersons.get(i).getFdId()
											+ "成功：");
							logger.warn(
									"禁用人员" + deletePersons.get(i).getFdName()
											+ "  "
											+ deletePersons.get(i).getFdId()
											+ "成功：");
							wxworkOmsRelationService.deleteByKey(
									deletePersons.get(i).getFdId(),
									getAppKey());
							relationMap.remove(deletePersons.get(i).getFdId());
						}
					} catch (Exception e) {
						jobContext.logMessage("禁用人员"
								+ deletePersons.get(i).getFdId() + "失败！");
					}
				}
			}
		}
		// 删除部门
		if (deleteDepts != null && !deleteDepts.isEmpty()&&personFlag) {
			HierarchyComparator comparator = new HierarchyComparator();
			Collections.sort(deleteDepts, comparator);
			String departId = null;
			boolean flag = true;
			for (int i = 0; i < deleteDepts.size(); i++) {
				try {
					departId = relationMap.get(deleteDepts.get(i).getFdId());
					JSONObject result = wxworkApiService
							.departDelete(Long.parseLong(departId));
					wxworkOmsRelationService.deleteByKey(
							deleteDepts.get(i).getFdId(), getAppKey());
					relationMap.remove(deleteDepts.get(i).getFdId());

					if (result.getIntValue("errcode") > 0) {
						jobContext.logMessage(
								"删除" + departId + "报错！微信异常："
										+ result.getString("errmsg"));
						syncCountLimit(result);
						flag = false;
					}
				} catch (Exception e) {
					jobContext.logMessage("删除" + departId + "报错！");
				}
			}
			if (flag) {
				logger.debug("删除平移的部门同步到微信的个数为:" + deleteDepts.size() + "条");
				jobContext.logMessage(
						"删除平移的部门同步到微信的个数为:" + deleteDepts.size() + "条");
			}
		}
	}

	/**
	 * @return
	 * @throws Exception
	 *             获取数据库的同步时间，不使用缓存
	 */
	private String getWxSyncTime() throws Exception {
		String time = null;
		String sql = "select fd_value from sys_app_config where fd_key='com.landray.kmss.third.weixin.work.oms.WxOmsConfig' and fd_field='lastUpdateTime'";
		List list = sysOrgPersonService.getBaseDao().getHibernateSession().createNativeQuery(sql).setMaxResults(1).list();
		if (list != null && !list.isEmpty()) {
			time = list.get(0).toString();
		}
		return time;
	}

	/**
	 * @throws Exception
	 *             获取数据库中的需要同步的机构、部门和人员
	 */
	private void getSyncData() throws Exception {
		syncTime = 0L;
		if (StringUtil.isNotNull(lastUpdateTime)) {
			syncTime = DateUtil.convertStringToDate(lastUpdateTime,
					"yyyy-MM-dd HH:mm:ss.SSS").getTime();
		}
		logger.debug("获取人员");
		// 获取人员
		List<SysOrgPerson> persons = getData(ORG_TYPE_PERSON);
		if (persons != null && !persons.isEmpty()) {
			syncPersons.addAll(persons);
			if (persons.get(0) != null
					&& persons.get(0).getFdAlterTime() != null) {
				if (syncTime < persons.get(0).getFdAlterTime().getTime())
					// 取第一条记录的更新时间，因为结果集是按更新时间倒序排序的
                {
                    syncTime = persons.get(0).getFdAlterTime().getTime();
                }
			} else {
				// 因为数据库NULL数据排序顺序的问题,当取到的数据中有更新时间为空的情况下，会有问题
				if (syncTime < synchroStartTime) {
					syncTime = synchroStartTime;
				}
			}
		}
		logger.debug("获取部门");
		// 获取部门
		List<SysOrgElement> depts = getData(ORG_TYPE_DEPT);
		if (depts != null && !depts.isEmpty()) {
			syncDepts.addAll(depts);
			if (depts.get(0) != null && depts.get(0).getFdAlterTime() != null) {
				if (syncTime < depts.get(0).getFdAlterTime().getTime()) {
                    syncTime = depts.get(0).getFdAlterTime().getTime();
                }
			} else {
				// 因为数据库NULL数据排序顺序的问题,当取到的数据中有更新时间为空的情况下，会有问题
				if (syncTime < synchroStartTime) {
					syncTime = synchroStartTime;
				}
			}
		}
		logger.debug("获取岗位");
		// 获取岗位
		List<SysOrgElement> posts = getData(ORG_TYPE_POST);
		if (posts != null && !posts.isEmpty()) {
			if (posts.get(0) != null && posts.get(0).getFdAlterTime() != null) {
				if (syncTime < posts.get(0).getFdAlterTime().getTime()) {
                    syncTime = posts.get(0).getFdAlterTime().getTime();
                }
			} else {
				// 因为数据库NULL数据排序顺序的问题,当取到的数据中有更新时间为空的情况下，会有问题
				if (syncTime < synchroStartTime) {
					syncTime = synchroStartTime;
				}
			}
			Map<String,SysOrgPerson> pid = null;
			ThirdWeixinOmsPost omsPost = null;
			for(SysOrgElement post:posts){
				logger.debug("处理岗位，id:" + post.getFdId() + "，名称:"
						+ post.getFdName());
				pid = new HashMap<String,SysOrgPerson>();
				//加载数据库中的数据
				List<SysOrgPerson> list = post.getFdPersons();
				if(list!=null&&list.size()>0){
					for(SysOrgPerson person:list){
						pid.put(person.getFdId(), person);
					}
				}			
				//加载以前的数据
				if(postMap.containsKey(post.getFdId())){
					omsPost = postMap.get(post.getFdId());
					if(StringUtil.isNotNull(omsPost.getDocContent())){
						String[] persionIds = omsPost.getDocContent().split("[,;]");
						SysOrgPerson person = null;
						for(String persionId:persionIds){
							//1 优化，应该是并集减去交集
							if(StringUtil.isNotNull(persionId)){
								person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(persionId,SysOrgPerson.class,false);
								if(person!=null) {
                                    pid.put(person.getFdId(), person);
                                }
							}
						}
					}
					String personIds_new = ArrayUtil
							.joinProperty(post.getFdPersons(), "fdId", ";")[0];
					String personIds_old = omsPost.getDocContent();
					if (personIds_new.equals(personIds_old)) {
						logger.debug("岗位成员没变，无需更新");
					} else {
						logger.debug("更新omsPost");
						omsPost.setDocContent(personIds_new);
						thirdWeixinOmsPostService.update(omsPost);
						logger.debug("更新omsPost成功");
					}
				}else{
					logger.debug("新增omsPost");
					//保存当前的岗位信息
					// 新增岗位时，岗位对应的人员没有放到同步列表里面去
					omsPost = new ThirdWeixinOmsPost();
					omsPost.setFdPostId(post.getFdId());
					omsPost.setDocContent(ArrayUtil.joinProperty(post.getFdPersons(), "fdId", ";")[0]);
					omsPost.setFdWxHandler(false);
					omsPost.setFdWxworkHandler(false);
					thirdWeixinOmsPostService.add(omsPost);
					postMap.put(post.getFdId(), omsPost);
					logger.debug("新增omsPost成功");
				}
				//添加岗位调整的人员
				syncPersons.addAll(pid.values());
			}
		}
		logger.debug("获取岗位结束");
		if (syncTime != 0) {
			lastUpdateTime = DateUtil.convertDateToString(new Date(syncTime),
					"yyyy-MM-dd HH:mm:ss.SSS");
			roleLastUpdateTime = lastUpdateTime;
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
		String sql = "fdIsExternal=false";
		if (type == 4) {
			if (StringUtil.isNotNull(roleLastUpdateTime)) {
				Date date = DateUtil.convertStringToDate(roleLastUpdateTime,
						"yyyy-MM-dd HH:mm:ss.SSS");
				sql += " and fdAlterTime>:beginTime";
				info.setParameter("beginTime", date);
			}
		} else {
		if (StringUtil.isNotNull(lastUpdateTime)) {
			Date date = DateUtil.convertStringToDate(lastUpdateTime,
					"yyyy-MM-dd HH:mm:ss.SSS");
			sql += " and fdAlterTime>:beginTime";
			info.setParameter("beginTime", date);
		}
		}
		info.setOrderBy("fdAlterTime desc");
		if (type == ORG_TYPE_PERSON) {
			info.setWhereBlock(sql + " and fdOrgType=" + ORG_TYPE_PERSON);
			rtnList = sysOrgPersonService.findList(info);
		} else if (type == ORG_TYPE_ORG || type == ORG_TYPE_DEPT) {
			info.setWhereBlock(sql + " and fdOrgType in (" + ORG_TYPE_ORG + ","
					+ ORG_TYPE_DEPT + ")");
			info.setOrderBy("LENGTH(fdHierarchyId) ");
			rtnList = sysOrgElementService.findList(info);
		} else if (type == ORG_TYPE_POST) {
			info.setWhereBlock(sql + " and fdOrgType=" + ORG_TYPE_POST);
			rtnList = sysOrgElementService.findList(info);
		}
		return rtnList;
	}

}
class HierarchyComparator implements Comparator {
	@Override
	public int compare(Object arg0, Object arg1) {
		SysOrgElement dept0 = (SysOrgElement) arg0;
		SysOrgElement dept1 = (SysOrgElement) arg1;
		if (dept0.getFdHierarchyId().length() < dept1.getFdHierarchyId()
				.length()) {
			return 1;
		} else {
			return 0;
		}
	}
}
// =================================陈亮微信组织架构数据获取性能优化结束=================================
