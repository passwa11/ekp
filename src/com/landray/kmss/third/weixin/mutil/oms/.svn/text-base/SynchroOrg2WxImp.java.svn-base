package com.landray.kmss.third.weixin.mutil.oms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.webservice.out.ISysSynchroGetOrgWebService;
import com.landray.kmss.sys.organization.webservice.out.SysSynchroGetOrgInfoContext;
import com.landray.kmss.sys.organization.webservice.out.SysSynchroOrgResult;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.weixin.model.ThirdWeixinOmsPost;
import com.landray.kmss.third.weixin.mutil.api.WxmutilApiService;
import com.landray.kmss.third.weixin.mutil.constant.WxworkConstant;
import com.landray.kmss.third.weixin.mutil.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.mutil.model.api.WxDepart;
import com.landray.kmss.third.weixin.mutil.model.api.WxUser;
import com.landray.kmss.third.weixin.mutil.spi.model.WxworkOmsRelationMutilModel;
import com.landray.kmss.third.weixin.mutil.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.mutil.util.WxmutilUtils;
import com.landray.kmss.third.weixin.service.IThirdWeixinOmsPostService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;

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

	private static WxOmsConfig wxOmsConfig = null;

	private String lastUpdateTime = null;

	private List<Object> rootOrgChildren = null;
	private Map<String, String> relationMap = new HashMap<String, String>();

	private WxmutilApiService wxCpService = null;

	private ISysSynchroGetOrgWebService sysSynchroGetOrgWebService;

	public ISysSynchroGetOrgWebService getSysSynchroGetOrgWebService() {
		if (sysSynchroGetOrgWebService == null) {
			sysSynchroGetOrgWebService = (ISysSynchroGetOrgWebService) SpringBeanUtil
					.getBean("sysSynchroGetOrgWebService");
		}
		return sysSynchroGetOrgWebService;
	}

	private IWxworkOmsRelationService mutilWxworkOmsRelationService;

	public void setMutilWxworkOmsRelationService(IWxworkOmsRelationService mutilWxworkOmsRelationService) {
		this.mutilWxworkOmsRelationService = mutilWxworkOmsRelationService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
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
	
	public void setThirdWeixinOmsPostService(IThirdWeixinOmsPostService thirdWeixinOmsPostService) {
		this.thirdWeixinOmsPostService = thirdWeixinOmsPostService;
	}
	
	// 单个定时任务同步
	public void synchro(SysQuartzJobContext context) {
		this.jobContext = context;
		String key = context.getParameter();
		if (StringUtil.isNull(key)) {
			logger.warn("多企业微信标识为空，不执行组织架构同步");
		}
		logger.debug("多企业微信：" + key + " 组织架构同步到企业微信中...");
		synchronized (this) {
			try {
				context.logMessage(
						"-----------------------------------开始同步name为："
								+ WeixinWorkConfig.newInstance(key).getWxName()
								+ ",key为：" + key
								+ "的企业微信配置----------------------------------");
				if (!"true".equals(
						WeixinWorkConfig.newInstance(key).getWxEnabled())) {
					logger.info("企业微信集成已经关闭，故不同步数据");
					context.logMessage("企业微信集成已经关闭，故不同步数据");
					return;
				}
				if (!"true".equals(WeixinWorkConfig.newInstance(key)
						.getWxOmsOutEnabled())) {
					logger.info("企业微信组织架构接出已经关闭，故不同步数据");
					context.logMessage("企业微信组织架构接出已经关闭，故不同步数据");
					return;
				}
				locked = true;
				try {
					long time = System.currentTimeMillis();
					init(key);
					context.logMessage("上次同步时间：" + lastUpdateTime);
					getSyncData();
					logCount();
					handleDept(key, syncDepts);
					handlePerson(key, syncPersons);
					deleteDept(key, syncDepts);// 先迁移人员，后删除部门，部门中有人员，不能删除
					// 0不处理，1禁用，2删除
					String personHandle = WeixinWorkConfig.newInstance(key)
							.getWxOmsOrgPersonHandle();
					if ("1".equals(personHandle)) {
						deleteDeptAndPerson(key, false);
					} else if ("2".equals(personHandle)) {
						deleteDeptAndPerson(key, true);
					}
					terminate(key);
					if (logger.isDebugEnabled()) {
						logger.debug("cost time:"
								+ ((System.currentTimeMillis() - time) / 1000)
								+ " s");
					}
					context.logMessage("cost time:"
							+ ((System.currentTimeMillis() - time) / 1000)
							+ " s");
					context.logMessage("本次同步时间：" + lastUpdateTime);
				} catch (Exception ex) {
					ex.printStackTrace();
					if (context != null) {
						context.logError(ex);
					}
				} finally {
					locked = false;
					if (relationMap != null) {
                        relationMap.clear();
                    }
					rootOrgChildren = null;
					syncDepts = null;
					deleteDepts = null;
					syncPersons = null;
					deletePersons = null;
				}
			} catch (Exception e) {
				logger.error(key + " 组织架构同步失败！");
				logger.error("", e);
			}
		}
	}

	@Override
	public void triggerSynchro(SysQuartzJobContext context) {
		this.jobContext = context;

		if (locked) {
            return;
        }

		//多企业微信同步组织架构
		Map<String, Map<String, String>> map = WeixinWorkConfig.getWxConfigDataMap();
		for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) {
			String key = entry.getKey();
			context.logMessage("-----------------------------------开始同步name为：" + WeixinWorkConfig.newInstance(key).getWxName() + ",key为：" + key + "的企业微信配置----------------------------------");
			if (!"true".equals(WeixinWorkConfig.newInstance(key).getWxEnabled())) {
				logger.info("企业微信集成已经关闭，故不同步数据");
				context.logMessage("企业微信集成已经关闭，故不同步数据");
				return;
			}
			if (!"true".equals(WeixinWorkConfig.newInstance(key).getWxOmsOutEnabled())) {
				logger.info("企业微信组织架构接出已经关闭，故不同步数据");
				context.logMessage("企业微信组织架构接出已经关闭，故不同步数据");
				return;
			}
			locked = true;
			try {
				long time = System.currentTimeMillis();
				init(key);
				context.logMessage("上次同步时间：" + lastUpdateTime);
				getSyncData();
				logCount();
				handleDept(key, syncDepts);
				handlePerson(key, syncPersons);
				deleteDept(key, syncDepts);// 先迁移人员，后删除部门，部门中有人员，不能删除
				// 0不处理，1禁用，2删除
				String personHandle = WeixinWorkConfig.newInstance(key).getWxOmsOrgPersonHandle();
				if ("1".equals(personHandle)) {
					deleteDeptAndPerson(key, false);
				} else if ("2".equals(personHandle)) {
					deleteDeptAndPerson(key, true);
				}
				terminate(key);
				if (logger.isDebugEnabled()) {
					logger.debug("cost time:" + ((System.currentTimeMillis() - time) / 1000) + " s");
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
				if (relationMap != null) {
                    relationMap.clear();
                }
				rootOrgChildren = null;
				syncDepts = null;
				deleteDepts = null;
				syncPersons = null;
				deletePersons = null;
			}
		}
		locked = false;
	}

	private boolean exist(SysOrgElement dept) {
		if (rootOrgChildren == null) {
			return true;
		}
		return rootOrgChildren.contains(dept.getFdId());
	}

	private String updateDept(String key, SysOrgElement element) throws Exception {
		if (relationMap.get(element.getFdId()) == null) {
			return "";
		}
		// 根目录
		String orgId = WeixinWorkConfig.newInstance(key).getWxOrgId();
		String wxOrgId = WeixinWorkConfig.newInstance(key).getWxRootId();
		WxDepart dept = new WxDepart();
		dept.setName(element.getFdName());
		String parentId = null;

		// 整合组织架构变小，根部门变小
		if (orgId.contains(element.getFdId())) {
			logger.debug(
					element.getFdId() + " " + element.getFdName() + " 是根部门");
			if (StringUtil.isNull(wxOrgId)) {
				parentId = "" + WX_ROOT_DEPT_ID;
			} else {
				parentId = wxOrgId;
			}
		} else {
			if (element.getFdParent() != null) {
				parentId = relationMap.get(element.getFdParent().getFdId());
			}
			if (StringUtil.isNull(parentId)) {
				parentId = "" + WX_ROOT_DEPT_ID;
			}
		}

		dept.setParentId(Long.parseLong(parentId));
		if (element.getFdOrder() != null) {
			Long order = element.getFdOrder().longValue();
			if ("1".equals(WeixinWorkConfig.newInstance(key).getWxDeptOrder())) {
				order = 10000 - order;
			}
			dept.setOrder(order);
		}
		dept.setId(Long.parseLong(relationMap.get(element.getFdId())));
		boolean flag = true;
		if ("null".equals(dept.getName()) || dept.getParentId() == null
				|| dept.getId() == null) {
			logger.error("dept has null::" + dept);
			flag = false;
		}
		if (flag) {
			String logInfo = "";
			try {
				JSONObject result = wxCpService.departUpdate(dept);
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

	private void handleDept(String key, List<SysOrgElement> depts) throws Exception {
		// 先增加所有部门，且先挂到根机构下
		int n = 0;
		String logInfo = null;
		SysOrgElement dept = null;
		if (depts != null && !depts.isEmpty()) {
			for (int i = 0; i < depts.size(); i++) {
				dept = depts.get(i);
				if (!exist(dept)) {
					if (relationMap.containsKey(dept.getFdId())) {
						deleteDepts.add(dept);
					}
					continue;
				}
				if (!isOmsRootOrg(key, dept.getFdId())) {
					continue;
				}
				if (dept.getFdOrgType() == ORG_TYPE_ORG
						|| dept.getFdOrgType() == ORG_TYPE_DEPT) {
					if (dept.getFdIsAvailable()) {
						if (!relationMap.keySet().contains(dept.getFdId())) {

							WxDepart wxdept = new WxDepart();
							wxdept.setName(dept.getFdName() + "_" + n);// 增加部门挂在根下时，会出现重复名，在此加ID区分，会在更新操作时修改正确
							wxdept.setParentId(WX_ROOT_DEPT_ID);
							if (dept.getFdOrder() != null) {
								Long order = dept.getFdOrder().longValue();
								if ("1".equals(WeixinWorkConfig.newInstance(key).getWxDeptOrder())) {
									order = 10000 - order;
								}
								wxdept.setOrder(order);
							}
							n++;
							logInfo = "增加部门到微信 " + dept.getFdName() + ", "
									+ dept.getFdId();
							try {
								JSONObject result = wxCpService
										.departCreate(wxdept);

								if (result.getIntValue("errcode") == 0) {
									Long deptId = result.getLong("id");
									logInfo += " created,微信对应ID:" + deptId;
									addRelation(key, dept,
											"" + deptId.longValue());
								} else {
									logInfo += " 失败,出错信息：errorCode="
											+ result.getIntValue("errcode")
											+ ",errorMsg="
											+ result.getString("errmsg");
									syncCountLimit(result);
								}
							} catch (Exception e) {
								logInfo += " 失败,出错信息：errorCode="
										+ e.getMessage();
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
				if (!isOmsRootOrg(key, dept.getFdId())) {
					continue;
				}
				if (dept.getFdOrgType() == ORG_TYPE_ORG
						|| dept.getFdOrgType() == ORG_TYPE_DEPT) {
					if (dept.getFdIsAvailable()) {
						n++;
						if (dept.getFdParent() == null) {
							// 无父部们，则挂到根部门上1
							updateDept(key, dept);
							continue;
						}
						String orgId = WeixinWorkConfig.newInstance(key)
								.getWxOrgId();
						String _wxOrgId = WeixinWorkConfig.newInstance(key)
								.getWxRootId();
						if (StringUtil.isNull(_wxOrgId)) {
							_wxOrgId = "" + WX_ROOT_DEPT_ID;
						}
						if (orgId.contains(dept.getFdId())) {
							logInfo = (dept.getFdName() + " 是根部门，对应父微信ID:"
									+ _wxOrgId);
							logInfo += updateDept(key, dept);
							jobContext.logMessage(logInfo);
						} else {
							if (dept.getFdParent() != null
									&& !relationMap.keySet()
											.contains(dept.getFdParent()
													.getFdId())) {
								logInfo = "警告：从关系中找不到微信所对应的父ID，则移到根部门下，当前部门: "
										+ dept.getFdName() + " "
										+ dept.getFdId()
										+ ",父ID："
										+ dept.getFdParent().getFdId();
								logInfo += updateDept(key, dept);
								logger.debug(logInfo);
								jobContext.logMessage(logInfo);
							} else {
								logInfo = "新增后或更新微信的父部门ID ," + dept.getFdName()
										+ "," + dept.getFdId() + ",对应父微信ID:"
										+ (dept.getFdParent() != null
												? relationMap
														.get(dept.getFdParent()
																.getFdId())
												: "");
								logInfo += updateDept(key, dept);
								logger.debug(logInfo);
								jobContext.logMessage(logInfo);
							}
						}

					}
				}
			}
		}

		logInfo = "修改部门同步到微信的个数为:" + n + "条";
		logger.debug(logInfo);
		jobContext.logMessage(logInfo);

	}

	private void deleteDept(String key, List<SysOrgElement> depts) throws Exception {
		int n = 0;
		String logInfo = null;

		// 对无效部门进行删除
		n = 0;
		if (depts != null && !depts.isEmpty()) {
			SysOrgElement dept = null;
			for (int i = 0; i < depts.size(); i++) {
				dept = depts.get(i);
				if (dept.getFdOrgType() == ORG_TYPE_ORG
						|| dept.getFdOrgType() == ORG_TYPE_DEPT) {
					if (!dept.getFdIsAvailable()) {
						n++;
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
								JSONObject result = wxCpService
										.departDelete(
										Long.parseLong(departId));

								logInfo += " ,retmsg," + departId + " deleted";

								if (result.getIntValue("errcode") > 0) {
									logInfo += " 失败,出错信息：errorCode="
											+ result.getIntValue("errcode")
											+ ",errorMsg="
											+ result.getString("errmsg");
									syncCountLimit(result);
								}
							} catch (Exception e) {
								logInfo += " 失败,出错信息：errorCode="
										+ e.getMessage();
							}

							logger.debug(logInfo);
							jobContext.logMessage(logInfo);

							mutilWxworkOmsRelationService.deleteByKey(dept.getFdId(),
									getAppKey(), key);
							relationMap.remove(dept.getFdId());
						}
					}
				}
			}
		}

		logInfo = "删除部门同步到微信的个数为:" + n + "条";
		logger.debug(logInfo);
		jobContext.logMessage(logInfo);

	}

	private WxUser getUser(String key, SysOrgPerson element) throws Exception {
		WxUser user = new WxUser();
		String sex = element.getFdSex();
		if ("M".equalsIgnoreCase(sex)) {
			user.setGender("1");
		} else if ("F".equalsIgnoreCase(sex)) {
			user.setGender("2");
		}
		// 根据配置来确定是选择那种作为企业号的userid，默认是登录名
		String wxln = WeixinWorkConfig.newInstance(key).getWxLoginName();
		if ("id".equalsIgnoreCase(wxln)) {
			user.setUserId(element.getFdId());
		} else {
			user.setUserId(element.getFdLoginName());
		}
		user.setName(element.getFdName());
		String parentId = null;
		if (element.getFdParent() != null) {
			parentId = relationMap.get(element.getFdParent().getFdId());
			if (StringUtil.isNull(parentId)) {
				parentId = "" + WX_ROOT_DEPT_ID;
			}
		} else {
			parentId = "" + WX_ROOT_DEPT_ID;
		}
		Long pId = Long.parseLong(parentId);
		user.setDepartIds(new Long[]{pId});
		if (StringUtil.isNotNull(element.getFdMobileNo())) {
			user.setMobile(element.getFdMobileNo());
		}
		if (StringUtil.isNotNull(element.getFdEmail())) {
			user.setEmail(element.getFdEmail());
		}
		if ("true"
				.equals(WeixinWorkConfig.newInstance(key).getWxPostEnabled())) {
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
		}else{
			user.setPosition("");
		}
		if (element.getFdOrder() != null) {
			Integer order = element.getFdOrder();
			if ("1".equals(WeixinWorkConfig.newInstance(key).getWxPersonOrder())) {
				order = 10000 - order;
			}
			user.setOrder(new Long[] { order.longValue() });
		}
		if ("true".equals(WeixinWorkConfig.newInstance(key).getWxOfficePhone())) {
			user.setTelephone(element.getFdWorkPhone());
		}
		return user;
	}

	private void handlePerson(String key, List<SysOrgPerson> persons) throws Exception {
		int n = 0;
		String logInfo = null;
		if (persons != null && !persons.isEmpty()) {
			SysOrgPerson person = null;
			for (int i = 0; i < persons.size(); i++) {
				person = persons.get(i);
				if (ORG_TYPE_PERSON == person.getFdOrgType()) {
					if (person.getFdIsAvailable()) {
						if (!exist(person)) {
							if (relationMap.containsKey(person.getFdId())) {
								deletePersons.add(person);
							}
							continue;
						}
						n++;
						if (person.getFdParent() == null) {
							// 无父部们，则挂到根部门上1
							continue;
						}

						if (!relationMap.keySet().contains(person.getFdId())) {
							addPerson(key, person);
						} else {
							String wxln = WeixinWorkConfig.newInstance(key)
									.getWxLoginName();
							if ("id".equalsIgnoreCase(wxln)) {
								updatePerson(key, person);
							} else {
								/*
								 * if (!person.getFdLoginName().equals(
								 * relationMap.get(person.getFdId()))) { //
								 * 个人loginName有变化 // 用旧的loginName去删除微信中的用户
								 * addNewAnddelOld(person); } else {
								 */
								// TODO 判断手机号有没有变化，如有变化先删和增加，但不能获取手机号
								updatePerson(key, person);
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
							logger.debug(logInfo);
						} else {
							deletePerson(key, person);
						}
					}
				}
			}
		}

		logInfo = "同步个人到微信的个数为:" + n + "条";
		logger.debug(logInfo);
		jobContext.logMessage(logInfo);

	}

	private void addPerson(String key, SysOrgPerson element) throws Exception {
		String logInfo = "增加个人到微信 " + element.getFdName() + ", "
				+ element.getFdId() + ",对应父微信ID:"
				+ (element.getFdParent() != null
						? relationMap.get(element.getFdParent().getFdId())
						: "");

		try {
			
			JSONObject result = wxCpService.userCreate(getUser(key, element));
			logInfo += " ,created";
			String wxln = WeixinWorkConfig.newInstance(key).getWxLoginName();
			if ("id".equalsIgnoreCase(wxln)) {
				addRelation(key, element, element.getFdId());
			} else {
				addRelation(key, element, element.getFdLoginName());
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

	private void updatePerson(String key, SysOrgPerson element) throws Exception {
		String logInfo = "更新个人到微信 " + element.getFdName() + ",loginName:"
				+ element.getFdLoginName() + ", id=" + element.getFdId()
				+ ",对应父微信ID:"
				+ (element.getFdParent() != null
						? relationMap.get(element.getFdParent().getFdId())
						: "");
		logInfo += " ,retmsg:";
		String errorCode = "";
		WxUser wxuser = getUser(key, element);
		try {
			if (relationMap.get(element.getFdId()) != null) {
                wxuser.setUserId(relationMap.get(element.getFdId()));
            }
			
			JSONObject result = wxCpService.userUpdate(wxuser);
			logInfo += " updated";
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
					JSONObject result = wxCpService.userCreate(wxuser);
					logInfo += " created";
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
		}

	}

	private void addNewAnddelOld(String key, SysOrgPerson element) throws Exception {
		// 用旧的loginName去删除微信中的用户
		String oldLoginName = relationMap.get(element.getFdId());
		String logInfo = "删除微信中旧的用户 " + element.getFdName() + ","
				+ element.getFdId() + ",loginName:" + oldLoginName;

		try {
			
			JSONObject result = wxCpService.userDelete(oldLoginName);
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
		mutilWxworkOmsRelationService.deleteByKey(element.getFdId(), getAppKey(), key);
		relationMap.remove(element.getFdId());
		// 增加新的微信用户
		logInfo += "增加新的微信用户 " + element.getFdName() + " " + element.getFdId()
				+ ",loginName:" + element.getFdLoginName();

		try {
			
			JSONObject result = wxCpService.userCreate(getUser(key, element));
			logInfo += " ,created";
			String wxln = WeixinWorkConfig.newInstance(key).getWxLoginName();
			if ("id".equalsIgnoreCase(wxln)) {
				addRelation(key, element, element.getFdId());
			} else {
				addRelation(key, element, element.getFdLoginName());
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

	private void deletePerson(String key, SysOrgPerson element) throws Exception {
		String logInfo = "删除微信中的个人ID " + element.getFdName() + " "
				+ element.getFdId() + "," + relationMap.get(element.getFdId());
		try {
			
			JSONObject result = wxCpService.userDelete(relationMap.get(element.getFdId()));
			logInfo += " ,deleted";
			mutilWxworkOmsRelationService.deleteByKey(element.getFdId(),
					getAppKey(), key);
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

	private void addRelation(String key, SysOrgElement element, String appPkId)
			throws Exception {
		WxworkOmsRelationMutilModel model = new WxworkOmsRelationMutilModel();
		model.setFdEkpId(element.getFdId());
		model.setFdAppPkId(appPkId);
		model.setFdAppKey(getAppKey());
		model.setFdWxKey(key);
		mutilWxworkOmsRelationService.add(model);
		relationMap.put(model.getFdEkpId(), model.getFdAppPkId());
	}

	private String getAppKey() {
		return StringUtil.isNull(WXWORK_OMS_APP_KEY)
				? "default"
				: WXWORK_OMS_APP_KEY;
	}

	private boolean isOmsRootOrg(String key, String rootId) {
		if ("true".equals(WeixinWorkConfig.newInstance(key).getWxOmsRootFlag())) {
			return true;
		} else if (WeixinWorkConfig.newInstance(key).getWxOrgId()
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

	private List<SysOrgElement> getAllOrgByRootOrg(String key) throws Exception {
		List<SysOrgElement> allOrgInRootOrg = new ArrayList<SysOrgElement>();
		List allOrgChildren = sysOrgElementService
				.findList("(fdOrgType=1) and fdIsAvailable=1", null);
		for (int i = 0; i < allOrgChildren.size(); i++) {
			SysOrgElement org = (SysOrgElement) allOrgChildren.get(i);
			if (StringUtil
					.isNotNull(WeixinWorkConfig.newInstance(key).getWxOrgId())) {
				SysOrgElement parent = org.getFdParent();
				while (parent != null) {
					if (WeixinWorkConfig.newInstance(key).getWxOrgId()
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

	private void init(String key) throws Exception {
		wxCpService = WxmutilUtils.getWxmutilApiServiceList().get(key);
		if (wxOmsConfig == null) {
			wxOmsConfig = new WxOmsConfig();
		}
		lastUpdateTime = new WxOmsConfig().getLastUpdateTime(key);

		List<SysOrgElement> allOrgChildrn = getAllOrgByRootOrg(key);
		rootOrgChildren = new ArrayList<Object>();
		if (StringUtil
				.isNotNull(WeixinWorkConfig.newInstance(key).getWxRootId())) {
			WX_ROOT_DEPT_ID = Long
					.parseLong(WeixinWorkConfig.newInstance(key).getWxRootId());
		} else {
			WX_ROOT_DEPT_ID = 1;
		}

		String WX_OMS_ROOT_ORG_ID = WeixinWorkConfig.newInstance(key).getWxOrgId();
		if (StringUtil.isNotNull(WX_OMS_ROOT_ORG_ID)) {
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

		List list = mutilWxworkOmsRelationService
				.findList("fdAppKey='" + getAppKey() + "' and fdWxKey='" + key + "'", null);
		for (int i = 0; i < list.size(); i++) {
			WxworkOmsRelationMutilModel model = (WxworkOmsRelationMutilModel) list.get(i);
			relationMap.put(model.getFdEkpId(), model.getFdAppPkId());
		}

		syncDepts = new ArrayList<SysOrgElement>(500);
		deleteDepts = new ArrayList<SysOrgElement>(50);
		syncPersons = new ArrayList<SysOrgPerson>(2000);
		deletePersons = new ArrayList<SysOrgPerson>(200);
		postMap = new HashMap<String, ThirdWeixinOmsPost>(200);
		
		List<ThirdWeixinOmsPost> plist = thirdWeixinOmsPostService.findList("fdWxHandler=1 and fdWxworkHandler=1",
				null);
		for(ThirdWeixinOmsPost post:plist){
			thirdWeixinOmsPostService.delete(post);
		}
		plist = thirdWeixinOmsPostService.findList(null, null);
		for(ThirdWeixinOmsPost post:plist){
			if(StringUtil.isNotNull(post.getFdPersonIds())){
				post.setDocContent(post.getFdPersonIds());
				post.setFdPersonIds("");
				thirdWeixinOmsPostService.update(post);
			}
			postMap.put(post.getFdPostId(), post);
		}
	}

	private void terminate(String key) throws Exception {
		if (StringUtil.isNotNull(lastUpdateTime)) {
			wxOmsConfig.setLastUpdateTime(key, lastUpdateTime);
			wxOmsConfig.save();
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
	private void deleteDeptAndPerson(String key, boolean personFlag) throws Exception {
		// 删除人员
		if (deletePersons != null && !deletePersons.isEmpty()) {
			for (int i = 0; i < deletePersons.size(); i++) {
				if (personFlag) {
					deletePerson(key, deletePersons.get(i));
				} else {
					WxUser wxuser = getUser(key, deletePersons.get(i));
					try {
						//禁用人员TODO
						if (relationMap
								.get(deletePersons.get(i).getFdId()) != null) {
                            wxuser.setUserId(relationMap
                                    .get(deletePersons.get(i).getFdId()));
                        }
						wxuser.setEnable(0);
						JSONObject result = wxCpService.userUpdate(wxuser);

						if (result.getIntValue("errcode") > 0) {
							jobContext.logMessage(
									"禁用人员" + deletePersons.get(i).getFdId()
											+ "失败！微信异常："
											+ result.getString("errmsg"));
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
			for (int i = deleteDepts.size() - 1; i >= 0; i--) {
				try {
					departId = relationMap.get(deleteDepts.get(i).getFdId());
					JSONObject result = wxCpService.departDelete(Long.parseLong(departId));
					mutilWxworkOmsRelationService.deleteByKey(
							deleteDepts.get(i).getFdId(), getAppKey(), key);
					relationMap.remove(deleteDepts.get(i).getFdId());
					if (result.getIntValue("errcode") > 0) {
						jobContext.logMessage(
								"删除 id为：" + departId + " 的部门报错！微信异常："
										+ result.getString("errmsg"));
						syncCountLimit(result);
						flag = false;
					}
				} catch (Exception e) {
					jobContext.logMessage("删除id为：" + departId + "的部门报错！");
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
		String sql = "select fd_value from sys_app_config where fd_key='com.landray.kmss.third.weixin.mutil.oms.WxOmsConfig' and fd_field='lastUpdateTime'";
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
		// 获取人员
		List<SysOrgPerson> persons = getData(ORG_TYPE_PERSON);
		if (persons != null && !persons.isEmpty()) {
			syncPersons.addAll(persons);
			if (persons.get(0) != null
					&& persons.get(0).getFdAlterTime() != null) {
				if (syncTime < persons.get(0).getFdAlterTime().getTime()) {
                    syncTime = persons.get(0).getFdAlterTime().getTime();
                }
			}
		}
		// 获取部门
		List<SysOrgElement> depts = getData(ORG_TYPE_DEPT);
		if (depts != null && !depts.isEmpty()) {
			syncDepts.addAll(depts);
			if (depts.get(0) != null && depts.get(0).getFdAlterTime() != null) {
				if (syncTime < depts.get(0).getFdAlterTime().getTime()) {
                    syncTime = depts.get(0).getFdAlterTime().getTime();
                }
			}
		}

		// 获取岗位
		List<SysOrgElement> posts = getData(ORG_TYPE_POST);
		if (posts != null && !posts.isEmpty()) {
			if (posts.get(0) != null && posts.get(0).getFdAlterTime() != null) {
				if (syncTime < posts.get(0).getFdAlterTime().getTime()) {
                    syncTime = posts.get(0).getFdAlterTime().getTime();
                }
			}
			Map<String,SysOrgPerson> pid = null;
			ThirdWeixinOmsPost omsPost = null;
			for(SysOrgElement post:posts){
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
							if(StringUtil.isNotNull(persionId)){
								person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(persionId,SysOrgPerson.class,false);
								if(person!=null) {
                                    pid.put(person.getFdId(), person);
                                }
							}
						}
					}
					omsPost.setDocContent(ArrayUtil.joinProperty(post.getFdPersons(), "fdId", ";")[0]);
					thirdWeixinOmsPostService.update(omsPost);
				}else{
					//保存当前的岗位信息
					omsPost = new ThirdWeixinOmsPost();
					omsPost.setFdPostId(post.getFdId());
					omsPost.setDocContent(ArrayUtil.joinProperty(post.getFdPersons(), "fdId", ";")[0]);
					omsPost.setFdWxHandler(false);
					omsPost.setFdWxworkHandler(false);
					thirdWeixinOmsPostService.add(omsPost);
					postMap.put(post.getFdId(), omsPost);
				}
				//添加岗位调整的人员
				syncPersons.addAll(pid.values());
			}
		}
		if (syncTime != 0) {
			lastUpdateTime = DateUtil.convertDateToString(new Date(syncTime),
					"yyyy-MM-dd HH:mm:ss.SSS");
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
		if (StringUtil.isNotNull(lastUpdateTime)) {
			Date date = DateUtil.convertStringToDate(lastUpdateTime,
					"yyyy-MM-dd HH:mm:ss.SSS");
			sql += " and fdAlterTime>:beginTime";
			info.setParameter("beginTime", date);
		}
		info.setOrderBy("fdAlterTime desc");
		if (type == ORG_TYPE_PERSON) {
			info.setWhereBlock(sql + " and fdOrgType=" + ORG_TYPE_PERSON);
			rtnList = sysOrgPersonService.findList(info);
		} else if (type == ORG_TYPE_ORG || type == ORG_TYPE_DEPT) {
			info.setWhereBlock(sql + " and fdOrgType in (" + ORG_TYPE_ORG + ","
					+ ORG_TYPE_DEPT + ")");
			info.setOrderBy("length(fdHierarchyId)");
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
