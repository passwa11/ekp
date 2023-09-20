package com.landray.kmss.third.weixin.mutil.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.weixin.constant.WxConstant;
import com.landray.kmss.third.weixin.mutil.api.WxmutilApiService;
import com.landray.kmss.third.weixin.mutil.model.ThirdWxworkOmsMutilInit;
import com.landray.kmss.third.weixin.mutil.model.WeixinMutilConfig;
import com.landray.kmss.third.weixin.mutil.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.mutil.model.api.WxDepart;
import com.landray.kmss.third.weixin.mutil.model.api.WxUser;
import com.landray.kmss.third.weixin.mutil.service.IThirdWxworkOmsInitService;
import com.landray.kmss.third.weixin.mutil.spi.model.WxworkOmsRelationMutilModel;
import com.landray.kmss.third.weixin.mutil.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.mutil.util.WxmutilUtils;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import java.util.*;
import java.util.Map.Entry;
/**
 * 组织初始化业务接口实现
 * 
 * @author
 * @version 1.0 2017-06-08
 */
public class ThirdWxworkOmsInitServiceImp extends ExtendDataServiceImp
		implements
			SysOrgConstant,
			WxConstant,
			IXMLDataBean,
			IThirdWxworkOmsInitService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdWxworkOmsInitServiceImp.class);

	private static KmssCache omsInitCache = new KmssCache(
			ThirdWxworkOmsInitServiceImp.class);

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

	private IWxworkOmsRelationService mutilWxworkOmsRelationService;

	public void setMutilWxworkOmsRelationService(IWxworkOmsRelationService mutilWxworkOmsRelationService) {
		this.mutilWxworkOmsRelationService = mutilWxworkOmsRelationService;
	}

	private WxmutilApiService wxCpService = null;
	private Map<String, WxworkOmsRelationMutilModel> relationMap = null;
	private Map<String, String> relationKeyMap = null;

	@Override
	public void getWxAllPersons(WxmutilApiService wxCpService, String fdWxKey) throws Exception {
		List<WxUser> users = new ArrayList<WxUser>();
		List<WxDepart> departs = (List<WxDepart>) omsInitCache.get("wxdp_" + fdWxKey);
		if (departs == null) {
			getWxAllDeparts(wxCpService, fdWxKey);
			departs = (List<WxDepart>) omsInitCache.get("wxdp_" + fdWxKey);
		}
		fdCheckAllPersonCount = departs.size();
		for (int i = 0; i < departs.size(); i++) {
			users.addAll(
					wxCpService.userList(departs.get(i).getId(), false, 0));
			fdCheckPersonCurrentCount++;
		}
		if (users == null || users.size() == 0) {
			logger.debug("无法获取微信人员数据");
			return;
		}
		Map<String, String> nmap = new HashMap<String, String>(users.size());
		Map<String, String> dtparent = (Map<String, String>) omsInitCache
				.get("wxdeptid");
		JSONObject json = null;
		Long[] dpPid = null;
		fdCheckAllPersonCount = users.size();
		for (int i = 0; i < users.size(); i++) {
			logger.debug("微信人员数据：" + users.get(i).getName() + ",userid:"
					+ users.get(i).getUserId() + ",mobile:"
					+ users.get(i).getMobile() + ",status:"
					+ users.get(i).getStatus() + ",email:"
					+ users.get(i).getEmail());
			json = new JSONObject();
			dpPid = users.get(i).getDepartIds();
			if (dpPid.length > 0) {
                json.put("dept", dtparent.get(dpPid[0] + ""));
            }
			json.put("name", users.get(i).getName());
			json.put("userid", users.get(i).getUserId());
			json.put("status", users.get(i).getStatus());
			json.put("mobile", users.get(i).getMobile());
			json.put("email", users.get(i).getEmail());
			nmap.put(users.get(i).getUserId() + "", json.toString());
			fdCheckPersonCurrentCount++;
		}
		omsInitCache.put("wxperson", nmap);
		nmap = null;
		users = null;
		dtparent = null;
		departs = null;
	}

	@Override
	public void getWxAllDeparts(WxmutilApiService wxCpService, String fdWxKey) throws Exception {
		String rootId = WeixinWorkConfig.newInstance(fdWxKey).getWxRootId();
		List<WxDepart> departs = new ArrayList<WxDepart>();
		if (StringUtil.isNull(rootId)) {
			departs = wxCpService.departGet();
		} else {
			departs = wxCpService.departGet(rootId);
		}
		fdCheckAllDeptCount = departs.size() * 2;
		if (departs == null || departs.size() == 0) {
			logger.debug("无法获取微信部门数据");
			return;
		}
		omsInitCache.put("wxdp_" + fdWxKey, departs);
		// 组装微信的部门层级架构
		Map<String, String> nmap = new HashMap<String, String>(departs.size());
		Map<String, String> pmap = new HashMap<String, String>(departs.size());
		for (int i = 0; i < departs.size(); i++) {
			nmap.put(departs.get(i).getId() + "", departs.get(i).getName());
			pmap.put(departs.get(i).getId() + "",
					departs.get(i).getParentId() + "");
			fdCheckDeptCurrentCount++;
		}
		Map<String, String> map = new HashMap<String, String>(departs.size());
		Map<String, String> imap = new HashMap<String, String>(departs.size());
		// 剔除根目录
		if (StringUtil.isNotNull(rootId)) {
			rootId = WeixinWorkConfig.newInstance().getWxRootId();
		} else {
			rootId = "1";
		}
		for (int i = 0; i < departs.size(); i++) {
			if ((departs.get(i).getId() + "").equals(rootId)) {
                continue;
            }
			String pname = "";
			String pid = departs.get(i).getParentId() + "";
			do {
				if (StringUtil.isNull(pname)) {
                    pname = departs.get(i).getName();
                }
				if (StringUtil.isNotNull(nmap.get(pid))
						&& !pid.equals(rootId)) {
					pname = nmap.get(pid) + ">>" + pname;
					pid = pmap.get(pid);
					if (StringUtil.isNull(pid)) {
                        break;
                    }
				} else {
					break;
				}
			} while (StringUtil.isNotNull(pid) || pid.equals(rootId));
			map.put(pname, departs.get(i).getId() + "");
			imap.put(departs.get(i).getId() + "", pname);
			fdCheckDeptCurrentCount++;
		}
		omsInitCache.put("wxdept_" + fdWxKey, map);
		omsInitCache.put("wxdeptid", imap);
		map = null;
		imap = null;
		nmap = null;
		pmap = null;
		departs = null;
	}

	@Override
	public void getEKPAllPersons() throws Exception {
		String wb = "fdOrgType=" + ORG_TYPE_PERSON + "and fdIsAvailable=1";
		List<SysOrgPerson> list = sysOrgPersonService.findList(wb, null);
		fdCheckAllPersonCount = list.size();
		Map<String, String> map = new HashMap<String, String>(list.size());
		Map<String, String> mbMap = new HashMap<String, String>(list.size());
		Map<String, String> emMap = new HashMap<String, String>(list.size());
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i).getFdLoginName(), list.get(i).getFdId());
			mbMap.put(list.get(i).getFdMobileNo(), list.get(i).getFdId());
			emMap.put(list.get(i).getFdEmail(), list.get(i).getFdId());
			fdCheckPersonCurrentCount++;
		}
		omsInitCache.put("personln", map);
		omsInitCache.put("personmb", mbMap);
		omsInitCache.put("personem", emMap);
		map = null;
		mbMap = null;
		emMap = null;
		list = null;
	}

	@Override
	public void getEKPAllDeparts(String wxKey) throws Exception {
		WeixinMutilConfig weixinMutilConfig = WeixinMutilConfig
				.newInstance(wxKey);
		String wxOmsRootFlag = weixinMutilConfig.getWxOmsRootFlag(); // 是否同步根目录
		logger.debug("是否开启同步根目录：" + wxOmsRootFlag);
		String orgids = weixinMutilConfig.getWxOrgId();
		String orgNames = weixinMutilConfig.getWxOrgName();
		String orgSqlWhere = "";
		if (orgids.contains(";")) {
			String[] orgArray = orgids.split(";");
			for (int k = 0; k < orgArray.length; k++) {
				if (k != 0) {
					orgSqlWhere += " or ";
				}
				orgSqlWhere += "fdHierarchyId like '%" + orgArray[k] + "%'";
			}
		} else {
			// 单个顶层部门
			orgSqlWhere = " fdHierarchyId like '%" + orgids + "%'";
		}
		String wb = "fdOrgType in (" + ORG_TYPE_ORG + "," + ORG_TYPE_DEPT
				+ ") and fdIsAvailable=1 and (" + orgSqlWhere + ")";
		List<SysOrgElement> list = sysOrgElementService.findList(wb,
				"fdHierarchyId desc");
		fdCheckAllDeptCount = list.size();
		String[] orgNameArray = orgNames.split(";");

		Map<String, String> map = new HashMap<String, String>(list.size());
		String pname = null;
		for (int i = 0; i < list.size(); i++) {
			pname = list.get(i).getFdParentsName(">>");
			for (int j = 0; j < orgNameArray.length; j++) {
				if (pname.contains(orgNameArray[j])) {
					if ("true".equals(wxOmsRootFlag)) { // 同步根目录
						if (pname.equals(orgNameArray[j])) {
                            break;
                        }
						pname = pname.substring(pname.indexOf(orgNameArray[j]),
								pname.length());
						break;
					} else {
						// 仅同步子部门，根部门不同步
						if (pname.equals(orgNameArray[j])) {
							pname = "";
							break;
						}
						pname = pname.substring(pname.indexOf(orgNameArray[j])
								+ orgNameArray[j].length(),
								pname.length());
					}

				}
				if (j + 1 == orgNameArray.length) {
					pname = "";
				}
			}
			if (StringUtil.isNotNull(pname)) {
				String path = pname + ">>" + list.get(i).getFdName();
				logger.debug(pname + " 部门的path:" + path);
				map.put(path, list.get(i).getFdId());
			} else {
				logger.debug(list.get(i).getFdName() + " 部门的path2:"
						+ list.get(i).getFdName());
				map.put(list.get(i).getFdName(), list.get(i).getFdId());
			}
			fdCheckDeptCurrentCount++;
		}
		omsInitCache.put("dept_"+wxKey, map);
		map = null;
		list = null;
	}
	private String getAppKey() {
		return StringUtil.isNull(WX_OMS_APP_KEY) ? "default" : WX_OMS_APP_KEY;
	}

	@Override
	public Map<String, List> deptMatch(String fdWxKey) throws Exception {
		logger.debug("部门匹配中");
		Map<String, List> rtnMap = new HashMap<String, List>();
		Map<String, String> wxDeptMap = (Map<String, String>) omsInitCache
				.get("wxdept_" + fdWxKey);
		Map<String, String> ekpDept = (Map<String, String>) omsInitCache
				.get("dept_"+ fdWxKey);
		List<String> wxdpkeys = new ArrayList<String>(wxDeptMap.keySet());
		List<WxworkOmsRelationMutilModel> savedepts = new ArrayList<WxworkOmsRelationMutilModel>();
		List<ThirdWxworkOmsMutilInit> errordepts = new ArrayList<ThirdWxworkOmsMutilInit>();
		WxworkOmsRelationMutilModel omsdept = null;
		ThirdWxworkOmsMutilInit omsinit = null;
		fdCheckAllDeptCount = wxdpkeys.size();
		for (String wxpn : wxdpkeys) {
			logger.debug("部门:" + wxpn + "匹配中");
			if (ekpDept.containsKey(wxpn)) {
				omsdept = new WxworkOmsRelationMutilModel();
				omsdept.setFdAppKey(getAppKey());
				omsdept.setFdEkpId(ekpDept.get(wxpn));
				omsdept.setFdAppPkId(wxDeptMap.get(wxpn));
				omsdept.setFdWxKey(fdWxKey);
				savedepts.add(omsdept);
				logger.debug("部门:" + wxpn + "匹配成功");
			}
			else if (!relationKeyMap
					.containsKey(wxDeptMap.get(wxpn) + "_" + fdWxKey)) {
				// else {
				logger.debug("部门:" + wxpn + "没有对应匹配部门");
				omsinit = new ThirdWxworkOmsMutilInit();
				omsinit.setFdEkpStatus("0");
				omsinit.setFdWxStatus("0");
				omsinit.setFdHandleStatus("0");
				if (wxpn.indexOf(">>") > -1) {
                    omsinit.setFdName(
                            wxpn.substring(wxpn.lastIndexOf(">>") + 2));
                }
				omsinit.setFdPath(wxpn);
				omsinit.setFdIsWx(true);
				omsinit.setFdIsOrg(true);
				omsinit.setFdWeixinId(wxDeptMap.get(wxpn));
				omsinit.setFdWxKey(fdWxKey);
				errordepts.add(omsinit);
			}
			fdCheckDeptCurrentCount++;
		}
		rtnMap.put("savedepts", savedepts);
		rtnMap.put("errordepts", errordepts);
		return rtnMap;
	}

	@Override
	public Map<String, List> personMatch(String fdWxKey) throws Exception {
		logger.debug(fdWxKey + "：人员开始匹配");
		Map<String, List> rtnMap = new HashMap<String, List>();
		Map<String, String> wxpersonMap = (Map<String, String>) omsInitCache
				.get("wxperson");
		Map<String, String> personln = (Map<String, String>) omsInitCache
				.get("personln");
		Map<String, String> personmb = (Map<String, String>) omsInitCache
				.get("personmb");
		Map<String, String> personem = (Map<String, String>) omsInitCache
				.get("personem");
		List<WxworkOmsRelationMutilModel> savePersons = new ArrayList<WxworkOmsRelationMutilModel>();
		List<ThirdWxworkOmsMutilInit> errorPersons = new ArrayList<ThirdWxworkOmsMutilInit>();
		if (wxpersonMap == null || wxpersonMap.isEmpty()) {
			logger.debug("微信人员为空，或者获取微信人员列表失败！");
			return rtnMap;
		}
		List<String> wxpkeys = new ArrayList<String>(wxpersonMap.keySet());
		WxworkOmsRelationMutilModel omsdept = null;
		ThirdWxworkOmsMutilInit omsinit = null;
		JSONObject jo = null;
		String appid = null;
		fdCheckAllPersonCount = wxpkeys.size();
		for (String wxpkey : wxpkeys) {
			jo = JSONObject.fromObject(wxpersonMap.get(wxpkey));
			appid = jo.getString("userid");
			logger.debug("正在匹配微信人员：" + jo.getString("name") + ":" + wxpkey);
			// 根据登录名匹配
			if (personln.containsKey(wxpkey)) {
				omsdept = new WxworkOmsRelationMutilModel();
				omsdept.setFdAppKey(getAppKey());
				omsdept.setFdEkpId(personln.get(wxpkey));
				omsdept.setFdAppPkId(appid);
				omsdept.setFdWxKey(fdWxKey);
				savePersons.add(omsdept);
				logger.debug("登录名匹配成功：" + wxpkey);
			} else if (jo.containsKey("mobile")
					&& StringUtil.isNotNull(jo.getString("mobile"))
					&& personmb.get(jo.getString("mobile")) != null) {// 根据手机号码匹配
				omsdept = new WxworkOmsRelationMutilModel();
				omsdept.setFdAppKey(getAppKey());
				omsdept.setFdEkpId(personmb.get(jo.getString("mobile")));
				omsdept.setFdAppPkId(appid);
				omsdept.setFdWxKey(fdWxKey);
				savePersons.add(omsdept);
				logger.debug("手机号码匹配成功：" + jo.getString("mobile"));
			} else if (jo.containsKey("email")
					&& StringUtil.isNotNull(jo.getString("email"))
					&& personem.get(jo.getString("email")) != null) {// 根据邮件地址匹配
				omsdept = new WxworkOmsRelationMutilModel();
				omsdept.setFdAppKey(getAppKey());
				omsdept.setFdEkpId(personem.get(jo.getString("email")));
				omsdept.setFdAppPkId(appid);
				omsdept.setFdWxKey(fdWxKey);
				savePersons.add(omsdept);
				logger.debug("email码匹配成功：" + jo.getString("email"));
			} else if (!relationKeyMap
					.containsKey(appid + "_" + fdWxKey)) {
				logger.debug("未匹配成功用户：" + wxpersonMap.get(wxpkey));
				omsinit = new ThirdWxworkOmsMutilInit();
				// omsdept = new WxworkOmsRelationMutilModel();
				omsinit.setFdEkpStatus("0");
				omsinit.setFdWxStatus("0");
				omsinit.setFdHandleStatus("0");
				if (jo.containsKey("name")) {
                    omsinit.setFdName(jo.getString("name"));
                }
				if (jo.containsKey("status")) {
                    omsinit.setFdStatus(jo.getString("status"));
                }
				if (jo.containsKey("dept")) {
                    omsinit.setFdPath(jo.getString("dept"));
                }
				omsinit.setFdIsWx(true);
				omsinit.setFdIsOrg(false);
				omsinit.setFdWeixinId(appid);
				omsinit.setFdWxKey(fdWxKey);
				// omsdept.setFdWxKey(fdWxKey);
				errorPersons.add(omsinit);
			}
			fdCheckPersonCurrentCount++;
		}
		rtnMap.put("savePersons", savePersons);
		rtnMap.put("errorPersons", errorPersons);
		return rtnMap;
	}

	@Override
	public void deptSave(Map<String, List> map) throws Exception {
		// 部门数据的保存
		List<WxworkOmsRelationMutilModel> savedepts = map.get("savedepts");
		List<ThirdWxworkOmsMutilInit> errordepts = map.get("errordepts");
		fdCheckAllDeptCount = savedepts.size() + errordepts.size();
		for (WxworkOmsRelationMutilModel model : savedepts) {
			// System.out.println("save_key:" + model.getFdWxKey() + ";fdId:"
			// + model.getFdId() + ";name:" + model.getFdEkpId());
			if (relationMap.get(model.getFdEkpId()) == null) {
				mutilWxworkOmsRelationService.add(model);
				relationMap.put(model.getFdEkpId(), model);
			}
			fdCheckDeptCurrentCount++;
		}
		IThirdWxworkOmsInitService thirdMutilWxworkOmsInitService = (IThirdWxworkOmsInitService) SpringBeanUtil
				.getBean("thirdMutilWxworkOmsInitService");
		HQLInfo info = new HQLInfo();
		info.setSelectBlock("fdName");
		info.setWhereBlock("fdWeixinId=:fdWeixinId and fdWxKey=:fdWxKey");
		for (ThirdWxworkOmsMutilInit model : errordepts) {
			info.setParameter("fdWeixinId", model.getFdWeixinId());
			info.setParameter("fdWxKey", model.getFdWxKey());
			String fdName = (String) thirdMutilWxworkOmsInitService
					.findFirstOne(info);
			if (StringUtil.isNotNull(fdName)) {
				logger.debug("third_wxwork_oms_mutil_init表里已经存在该未匹配部门：" + fdName);
				continue;
			}
			if (StringUtil.isNull(model.getFdName())
					&& StringUtil.isNotNull(model.getFdPath())) {
                model.setFdName(model.getFdPath());
            }
			add(model);
			fdCheckDeptCurrentCount++;
		}
	}

	@Override
	public void personSave(Map<String, List> map) throws Exception {
		// 人员数据的保存
		List<WxworkOmsRelationMutilModel> savePersons = map.get("savePersons");
		List<ThirdWxworkOmsMutilInit> errorPersons = map.get("errorPersons");
		fdCheckAllPersonCount = savePersons.size() + errorPersons.size();
		for (WxworkOmsRelationMutilModel model : savePersons) {
			if (relationMap.get(model.getFdEkpId()) == null) {
				mutilWxworkOmsRelationService.add(model);
				relationMap.put(model.getFdEkpId(), model);
			}
			fdCheckPersonCurrentCount++;
		}
		IThirdWxworkOmsInitService thirdMutilWxworkOmsInitService = (IThirdWxworkOmsInitService) SpringBeanUtil
				.getBean("thirdMutilWxworkOmsInitService");
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdWeixinId=:fdWeixinId and fdWxKey=:fdWxKey");
		for (ThirdWxworkOmsMutilInit model : errorPersons) {
            info.setParameter("fdWeixinId", model.getFdWeixinId());
            info.setParameter("fdWxKey", model.getFdWxKey());
            List<ThirdWxworkOmsMutilInit> thirdWxworkOmsMutilInit = thirdMutilWxworkOmsInitService.findList(info);
            if(thirdWxworkOmsMutilInit.size()>0){
				logger.debug(
						"third_wxwork_oms_mutil_init表里已经存在该未匹配用户："
								+ model.getFdName());
				continue;
            }
			add(model);
			fdCheckPersonCurrentCount++;
		}
	}

	private void initData(String type, String fdWxKey) throws Exception {
		relationMap = new HashMap<String, WxworkOmsRelationMutilModel>();
		relationKeyMap = new HashMap<String, String>();
		wxCpService = WxmutilUtils.getWxmutilApiServiceList().get(fdWxKey);
		// 获取中间表的数据
		List relationlist = mutilWxworkOmsRelationService
				.findList("fdAppKey='" + getAppKey() + "' and fdWxKey='"
						+ fdWxKey + "'", null);
		fdCheckAllDeptCount = relationlist.size() + 1;
		fdCheckAllPersonCount = relationlist.size() + 1;
		for (int i = 0; i < relationlist.size(); i++) {
			WxworkOmsRelationMutilModel model = (WxworkOmsRelationMutilModel) relationlist.get(i);
			relationMap.put(model.getFdEkpId(), model);
			relationKeyMap.put(model.getFdAppPkId() + "_" + fdWxKey,
					model.getFdEkpId());
			fdCheckDeptCurrentCount++;
			fdCheckPersonCurrentCount++;
		}
		String sql = "delete from ThirdWxworkOmsMutilInit";
		if ("org".equals(type)) {
			sql += " where fdIsOrg = 1 and fdWxKey='" + fdWxKey + "'";
		} else {
			sql += " where fdIsOrg = 0 and fdWxKey='" + fdWxKey + "'";
		}
		logger.debug("清理未匹配数据(ThirdWxworkOmsMutilInit)....");
		logger.debug("清理sql:" + sql);
		getBaseDao().getHibernateSession().createQuery(sql).executeUpdate();
		getBaseDao().getHibernateSession().flush();
		getBaseDao().getHibernateSession().clear();
		fdCheckDeptCurrentCount++;
		fdCheckPersonCurrentCount++;
		// 初始化缓存数据
		omsInitCache.clear();
	}

	@Override
	public void updateDept(JSONObject json, String wxKey) throws Exception {
		Map<String, List> map = null;
		Map<String, Map<String, String>> wxWorkConfig = WeixinWorkConfig.getWxConfigDataMap();
		for (Entry<String, Map<String, String>> entry : wxWorkConfig.entrySet()) {
			String fdWxKey = entry.getKey();
			try {
				if (!checkRoot(fdWxKey)) {
					logger.info("企业微信集成中的根目录配置错误，请及时修正！");
					return;
				}
				if (StringUtil.isNotNull(wxKey) && !wxKey.equals(fdWxKey)) {
					logger.info("当前企业微信仅初始化key:" + wxKey + " 的部门数据，" + fdWxKey
							+ " 企业微信跳过");
					continue;
				}
				fdDept = true;
				// 初始化数据
				fdCheckDeptState = 0;
				fdDeptStatusMessage = "初始化数据...";
				fdCheckDeptCurrentCount = 0;
				fdCheckAllDeptCount = 0;
				long time = System.currentTimeMillis();
				initData("org", fdWxKey);
				logger.info(fdDeptStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
				time = System.currentTimeMillis();
				// 获取微信企业号的组织数据
				fdDeptStatusMessage = "获取企业微信的组织数据...";
				fdCheckDeptCurrentCount = 0;
				fdCheckAllDeptCount = 0;
				getWxAllDeparts(wxCpService, fdWxKey);
				logger.info(fdDeptStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
				time = System.currentTimeMillis();
				// 获取EKP的组织数据
				fdDeptStatusMessage = "获取EKP的组织数据...";
				fdCheckDeptCurrentCount = 0;
				fdCheckAllDeptCount = 0;
				getEKPAllDeparts(fdWxKey);
				logger.info(fdDeptStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
				time = System.currentTimeMillis();
				// 数据匹配
				fdDeptStatusMessage = "数据匹配...";
				fdCheckDeptCurrentCount = 0;
				fdCheckAllDeptCount = 0;
				map = deptMatch(fdWxKey);
				logger.info(fdDeptStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
				time = System.currentTimeMillis();
				// 数据处理
				fdDeptStatusMessage = "数据处理...";
				fdCheckDeptCurrentCount = 0;
				fdCheckAllDeptCount = 0;
				deptSave(map);
				logger.info(fdDeptStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
				time = System.currentTimeMillis();
			} catch (Exception e) {
				fdCheckDeptState = 1;
				Thread.sleep(2000);
				throw new RuntimeException(e);
			} finally {
				if (map != null) {
					List<ThirdWxworkOmsMutilInit> errordepts = map.get("errordepts");
					if (errordepts != null && errordepts.size() > 0) {
						json.put("errors", "1");
					} else {
						json.put("errors", "0");
					}
				}
				fdCheckDeptState = 1;
				fdDept = false;
				relationMap = null;
				relationKeyMap = null;
				map = null;
			}
		}
	}

	/**
	 * 多企业微信人员对照表更新拆分
	 * 
	 * @author 陈火旺
	 * @param sysQuartzJobContext
	 * @throws Exception
	 */
	public void updatePerson2(SysQuartzJobContext sysQuartzJobContext)
			throws Exception {

		// Boolean lock = true;
		String fdWxKey = sysQuartzJobContext.getParameter();
		if (StringUtil.isNull(fdWxKey)) {
			logger.warn("多企业微信标识为空，不执行人员对照任务");
		}
		logger.debug("多企业微信：" + fdWxKey + " 人员对照表更新执行中！");
		synchronized (this) {
			try {
				updatePerson(new JSONObject(), fdWxKey);
			} catch (Exception e) {
				logger.error(fdWxKey + " 更新人员对照表失败！");
				logger.error("", e);
			}
		}

	}
	@Override
	public void updatePerson() throws Exception {
		omsInitCache.clear();
		Map<String, List> map = null;
		Map<String, Map<String, String>> wxWorkConfig = WeixinWorkConfig.getWxConfigDataMap();
		for (Entry<String, Map<String, String>> entry : wxWorkConfig.entrySet()) {
			String fdWxKey = entry.getKey();
			try {
				if (!checkRoot(fdWxKey)) {
					logger.info(fdWxKey + " 企业微信集成中的根目录配置错误，请及时修正！");
					return;
				}
				fdPerson = true;
				// 初始化数据
				fdCheckPersonState = 0;
				fdPersonStatusMessage = "初始化数据...";
				fdCheckPersonCurrentCount = 0;
				fdCheckAllPersonCount = 0;
				long time = System.currentTimeMillis();
				initData("person", fdWxKey);
				logger.info(fdPersonStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
				time = System.currentTimeMillis();
				// 获取微信企业号的组织数据
				fdPersonStatusMessage = "获取企业微信的人员数据...";
				fdCheckPersonCurrentCount = 0;
				fdCheckAllPersonCount = 0;
				getWxAllPersons(wxCpService, fdWxKey);
				logger.info(fdPersonStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
				time = System.currentTimeMillis();
				// 获取EKP的组织数据
				fdPersonStatusMessage = "获取EKP的人员数据...";
				fdCheckPersonCurrentCount = 0;
				fdCheckAllPersonCount = 0;
				getEKPAllPersons();
				logger.info(fdPersonStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
				time = System.currentTimeMillis();
				// 数据匹配
				fdPersonStatusMessage = "数据匹配...";
				fdCheckPersonCurrentCount = 0;
				fdCheckAllPersonCount = 0;
				map = personMatch(fdWxKey);
				logger.info(fdPersonStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
				time = System.currentTimeMillis();
				// 数据处理
				fdPersonStatusMessage = "数据处理...";
				fdCheckPersonCurrentCount = 0;
				fdCheckAllPersonCount = 0;
				personSave(map);
				logger.info(fdDeptStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
				time = System.currentTimeMillis();
			} catch (Exception e) {
				fdCheckPersonState = 1;
				Thread.sleep(2000);
				throw new RuntimeException(e);
			} finally {
				fdCheckPersonState = 1;
				fdPerson = false;
				relationMap = null;
				relationKeyMap = null;
				map = null;
			}
		}
	}
	
	@Override
	public void updatePerson(JSONObject json, String wxKey) throws Exception {
		omsInitCache.clear();
		Map<String, List> map = null;
		Map<String, Map<String, String>> wxWorkConfig = WeixinWorkConfig.getWxConfigDataMap();
		for (Entry<String, Map<String, String>> entry : wxWorkConfig.entrySet()) {
			String fdWxKey = entry.getKey();
			try {
				if (!checkRoot(fdWxKey)) {
					logger.info("企业微信集成中的根目录配置错误，请及时修正！");
					return;
				}
				if (StringUtil.isNotNull(wxKey) && !wxKey.equals(fdWxKey)) {
					logger.info("当前企业微信仅初始化key:" + wxKey + " 的人员数据，" + fdWxKey
							+ " 企业微信跳过");
					continue;
				}
				fdPerson = true;
				// 初始化数据
				fdCheckPersonState = 0;
				fdPersonStatusMessage = "初始化数据...";
				fdCheckPersonCurrentCount = 0;
				fdCheckAllPersonCount = 0;
				long time = System.currentTimeMillis();
				initData("person", fdWxKey);
				logger.info(fdPersonStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
				time = System.currentTimeMillis();
				// 获取微信企业号的组织数据
				fdPersonStatusMessage = "获取企业微信的人员数据...";
				fdCheckPersonCurrentCount = 0;
				fdCheckAllPersonCount = 0;
				getWxAllPersons(wxCpService, fdWxKey);
				logger.info(fdPersonStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
				time = System.currentTimeMillis();
				// 获取EKP的组织数据
				fdPersonStatusMessage = "获取EKP的人员数据...";
				fdCheckPersonCurrentCount = 0;
				fdCheckAllPersonCount = 0;
				getEKPAllPersons();
				logger.info(fdPersonStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
				time = System.currentTimeMillis();
				// 数据匹配
				fdPersonStatusMessage = "数据匹配...";
				fdCheckPersonCurrentCount = 0;
				fdCheckAllPersonCount = 0;
				map = personMatch(fdWxKey);
				logger.info(fdPersonStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
				time = System.currentTimeMillis();
				// 数据处理
				fdPersonStatusMessage = "数据处理...";
				fdCheckPersonCurrentCount = 0;
				fdCheckAllPersonCount = 0;
				if (!(map == null || map.isEmpty())) {
					personSave(map);
				}
				logger.info(fdDeptStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
				time = System.currentTimeMillis();
			} catch (Exception e) {
				fdCheckPersonState = 1;
				Thread.sleep(2000);
				throw new RuntimeException(e);
			} finally {
				if (map != null) {
					List<ThirdWxworkOmsMutilInit> errorPersons = map.get("errorPersons");
					if (errorPersons != null && errorPersons.size() > 0) {
						json.put("errors", "1");
					} else {
						json.put("errors", "0");
					}
				}
				fdCheckPersonState = 1;
				fdPerson = false;
				relationMap = null;
				relationKeyMap = null;
				map = null;
			}
		}
	}

	public static String fdPersonStatusMessage = "";
	public static String fdDeptStatusMessage = "";
	public static int fdCheckPersonState = 0; // 检查状态
	public static int fdCheckDeptState = 0; // 检查状态
	public static long fdCheckPersonCurrentCount = 0; // 人员的当前数量
	public static long fdCheckDeptCurrentCount = 0; // 部门的当前数量
	public static long fdCheckAllPersonCount = 0; // 人员的总数量
	public static long fdCheckAllDeptCount = 0; // 部门的总数量
	public static boolean fdDept = false;
	public static boolean fdPerson = false;
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
		String isorg = requestInfo.getParameter("fdIsOrg");
		Map<String, Object> data = new HashMap<String, Object>();
		if ("1".equals(isorg) && fdDept) {
			data.put("checkAllCount", fdCheckAllDeptCount);
			data.put("checkCurrentCount", fdCheckDeptCurrentCount);
			data.put("checkMessage", fdDeptStatusMessage);
			data.put("checkState", fdCheckDeptState);
		} else if (fdPerson) {
			data.put("checkAllCount", fdCheckAllPersonCount);
			data.put("checkCurrentCount", fdCheckPersonCurrentCount);
			data.put("checkMessage", fdPersonStatusMessage);
			data.put("checkState", fdCheckPersonState);
		} else {
			data.put("checkAllCount", 0);
			data.put("checkCurrentCount", 0);
			data.put("checkMessage", "");
			data.put("checkState", 0);
		}
		rtnList.add(data);
		return rtnList;
	}

	@Override
	public void updateWx(String fdId, String wxId, String type, String fdWxKey)
			throws Exception {
		// if (wxCpService == null)
			wxCpService = WxmutilUtils.getWxmutilApiServiceList().get(fdWxKey);
		if (StringUtil.isNull(wxId) || StringUtil.isNull(fdId)) {
			logger.debug("要删除的部门ID为空");
			return;
		}
		ThirdWxworkOmsMutilInit init = (ThirdWxworkOmsMutilInit) findByPrimaryKey(fdId);
		if (init == null || "1".equals(init.getFdWxStatus())) {
            return;
        }
		boolean flag = true;
		if ("0".equals(type)) {
			try {
				// wxCpService = WxmutilUtils.getWxmutilApiServiceList()
				// .get(fdWxKey);
				// 删除微信企业号的人员
				wxCpService.userDelete(wxId);
				// 删除EKP中间异常表的映射数据
				delete(init);
			} catch (Exception wxe) {
				flag = false;
				
				throw new KmssRuntimeException(wxe);
			}
		} else {
			// 把部门下面的人员移动到根目录下
			String rootId = WeixinWorkConfig.newInstance(fdWxKey).getWxRootId();
			try {
				List<WxUser> users = wxCpService
						.userList(Long.parseLong(wxId), true, 0);
				Long[] rootIds = {1L};
				if (StringUtil.isNotNull(rootId) && !"1".equals(rootId)) {
					rootIds[0] = Long.parseLong(rootId);
				}
				if (users != null && users.size() > 0) {
					for (WxUser user : users) {
						Long[] oldDepartIds = user.getDepartIds();
						user.setDepartIds(rootIds);
						if (UserOperHelper.allowLogOper("updateWx", "*")) {
							UserOperContentHelper.putUpdate(user.getWeiXinId(),
									user.getName(), null)
									.putSimple("departIds",
											Arrays.toString(oldDepartIds),
											Arrays.toString(rootIds));
						}
						wxCpService.userUpdate(user);
					}
				}
			} catch (Exception wxe) {
				flag = false;
				throw new KmssRuntimeException(wxe);
			}
			// 获取部门及部门下所有的子部门并删除
			try {
				List<WxDepart> departs = wxCpService.departGet(wxId);
				if (departs != null && departs.size() > 0) {
					List<WxDepart> superDeparts = new ArrayList();
					int count = 0;
					while (departs.size() > 0) {
						count++;
						WxDepart depart = null;
						for (int i = departs.size() - 1; i >= 0; i--) {
							depart = departs.get(i);
							com.alibaba.fastjson.JSONObject result = wxCpService
									.departDelete(depart.getId());
							List<ThirdWxworkOmsMutilInit> omslist = findList(
									"fdWeixinId='" + depart.getId() + "'",
									null);
							for (ThirdWxworkOmsMutilInit omsinit : omslist) {
								delete(omsinit);
							}
							superDeparts.remove(depart);

							if (result.getIntValue("errcode") > 0) {
								if (result.getIntValue("errcode") == 60006
										&& depart != null
										&& !superDeparts.contains(depart)) {
									superDeparts.add(depart);
								} else if (result
										.getIntValue("errcode") == 60123
										&& depart != null) {
									superDeparts.remove(depart);
								}
							}
						}
						departs = superDeparts;
						// 如果存在异常，超过1000次后强制跳出
						if (count > 20) {
							departs = new ArrayList();
						}
					}
				}
			} catch (Exception e) {
				flag = false;
				logger.debug(
						"微信处理部门出错", e);
				throw new KmssRuntimeException(e);
			}
		}
	}

	@Override
	public boolean updateEKP(String fdId, String fdEKPId, String type)
			throws Exception {
		boolean flag = true;
		// 更新中间表
		ThirdWxworkOmsMutilInit init = (ThirdWxworkOmsMutilInit) findByPrimaryKey(fdId);
		WxworkOmsRelationMutilModel model = (WxworkOmsRelationMutilModel) mutilWxworkOmsRelationService
				.findFirstOne("fdAppKey='" + getAppKey() + "' and fdEkpId='"
						+ fdEKPId + "' and fdWxKey='" + init.getFdWxKey() + "'",
						null);
		if(model == null){
			model = (WxworkOmsRelationMutilModel) mutilWxworkOmsRelationService
					.findFirstOne("fdAppKey='" + getAppKey() + "' and fdAppPkId='"
							+ init.getFdWeixinId() + "'", null);
			if (model == null) {
				model = new WxworkOmsRelationMutilModel();
			}
			String oldEkpId = model.getFdEkpId();
			String oldAppKey = model.getFdAppKey();
			String oldAppPkId = model.getFdAppPkId();
			String oldWxKey = model.getFdWxKey();
			model.setFdEkpId(fdEKPId);
			model.setFdAppKey(getAppKey());
			model.setFdAppPkId(init.getFdWeixinId());
			model.setFdWxKey(init.getFdWxKey());
			if (UserOperHelper.allowLogOper("updateEKP", "*")) {
				UserOperContentHelper.putUpdate(model)
						.putSimple("fdEKPId", oldEkpId, model.getFdEkpId())
						.putSimple("fdAppKey", oldAppKey, model.getFdAppKey())
						.putSimple("fdAppPkId", oldAppPkId,
								model.getFdAppPkId())
						.putSimple("fdWxKey", oldWxKey, model.getFdWxKey());
			}
			mutilWxworkOmsRelationService.update(model);
			// 删除EKP中间异常表的映射数据
			delete(init);
		}else{
			flag = false;
		}
		return flag;
	}

	private boolean checkRoot(String fdWxKey) throws Exception {
		boolean rtn = true;
		if (!"true".equals(WeixinWorkConfig.newInstance(fdWxKey).getWxEnabled())) {
			logger.info(fdWxKey + " 企业微信集成已经关闭");
			return false;
		}
		String rootId = WeixinWorkConfig.newInstance(fdWxKey).getWxRootId();
		if ("1".equals(rootId) || StringUtil.isNull(rootId)) {
			return rtn;
		} else {
			try {
				wxCpService = WxmutilUtils.getWxmutilApiServiceList()
							.get(fdWxKey);
				List depts = wxCpService.departGet(rootId);
				if (depts.size() > 0) {
					return rtn;
				} else {
					logger.info(fdWxKey + " rootId:" + rootId
							+ " ,企业微信集成中的根目录配置错误，根id可能不存在于企业微信，请检查根id！");
					return false;
				}

			} catch (Exception e) {
				logger.info(e.toString());
				logger.info(fdWxKey + " rootId:" + rootId
						+ " ,企业微信集成中的根目录配置错误，请及时修正！");
				return false;
			}
		}
	}
}
