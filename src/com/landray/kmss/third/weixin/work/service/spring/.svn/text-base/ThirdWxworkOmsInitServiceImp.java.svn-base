package com.landray.kmss.third.weixin.work.service.spring;

import com.landray.kmss.common.actions.RequestContext;
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
import com.landray.kmss.third.weixin.constant.WxConstant;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.model.ThirdWxworkOmsInit;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.model.api.WxDepart;
import com.landray.kmss.third.weixin.work.model.api.WxUser;
import com.landray.kmss.third.weixin.work.service.IThirdWxworkOmsInitService;
import com.landray.kmss.third.weixin.work.spi.model.WxworkOmsRelationModel;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.work.util.EmojiFilter;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	private IWxworkOmsRelationService wxworkOmsRelationService;

	public void setWxworkOmsRelationService(
			IWxworkOmsRelationService wxworkOmsRelationService) {
		this.wxworkOmsRelationService = wxworkOmsRelationService;
	}

	private WxworkApiService wxworkApiService = null;
	private Map<String, WxworkOmsRelationModel> relationMap = null;
	private Map<String, String> relationKeyMap = null;

	@Override
	public void getWxAllPersons(WxworkApiService wxworkApiService)
			throws Exception {
		List<WxUser> users = new ArrayList<WxUser>();
		List<WxDepart> departs = (List<WxDepart>) omsInitCache.get("wxdp");
		if (departs == null) {
			getWxAllDeparts(wxworkApiService);
			departs = (List<WxDepart>) omsInitCache.get("wxdp");
		}
		fdCheckAllPersonCount = departs.size();
		for (int i = 0; i < departs.size(); i++) {
			users.addAll(
					wxworkApiService.userList(departs.get(i).getId(), false,
							0));
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
	public void getWxAllDeparts(WxworkApiService wxworkApiService)
			throws Exception {
		String rootId = WeixinWorkConfig.newInstance().getWxRootId();
		List<WxDepart> departs = new ArrayList<WxDepart>();
		if (StringUtil.isNull(rootId)) {
			departs = wxworkApiService.departGet();
		} else {
			departs = wxworkApiService.departGet(rootId);
		}
		fdCheckAllDeptCount = departs.size() * 2;
		if (departs == null || departs.size() == 0) {
			logger.debug("无法获取微信部门数据");
			return;
		}
		omsInitCache.put("wxdp", departs);
		// 组装微信的部门层级架构
		Map<String, String> nmap = new HashMap<String, String>(departs.size());
		Map<String, String> pmap = new HashMap<String, String>(departs.size());
		for (int i = 0; i < departs.size(); i++) {
			nmap.put(departs.get(i).getId() + "", departs.get(i).getName());
			pmap.put(departs.get(i).getId() + "",
					departs.get(i).getParentid() + "");
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
			String pid = departs.get(i).getParentid() + "";
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
		omsInitCache.put("wxdept", map);
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
	public void getEKPAllDeparts() throws Exception {
		String wb = "fdOrgType in (" + ORG_TYPE_ORG + "," + ORG_TYPE_DEPT
				+ ") and fdIsAvailable=1";
		List<SysOrgElement> list = sysOrgElementService.findList(wb,
				"fdHierarchyId desc");
		fdCheckAllDeptCount = list.size();
		Map<String, String> map = new HashMap<String, String>(list.size());
		String pname = null;
		for (int i = 0; i < list.size(); i++) {
			pname = list.get(i).getFdParentsName(">>");
			if (StringUtil.isNotNull(pname)) {
				map.put(pname + ">>" + list.get(i).getFdName(),
						list.get(i).getFdId());
			} else {
				map.put(list.get(i).getFdName(), list.get(i).getFdId());
			}
			fdCheckDeptCurrentCount++;
		}
		omsInitCache.put("dept", map);
		map = null;
		list = null;
	}
	private String getAppKey() {
		return StringUtil.isNull(WX_OMS_APP_KEY) ? "default" : WX_OMS_APP_KEY;
	}

	@Override
	public Map<String, List> deptMatch() throws Exception {
		logger.debug("部门匹配开始...");
		Map<String, List> rtnMap = new HashMap<String, List>();
		Map<String, String> wxDeptMap = (Map<String, String>) omsInitCache
				.get("wxdept");
		if(wxDeptMap==null){
			//取不到企业微信部门信息
			throw new RuntimeException("取不到企业微信部门信息,匹配失败");
		}
		Map<String, String> ekpDept = (Map<String, String>) omsInitCache
				.get("dept");
		List<String> wxdpkeys = new ArrayList<String>(wxDeptMap.keySet());
		List<WxworkOmsRelationModel> savedepts = new ArrayList<WxworkOmsRelationModel>();
		List<ThirdWxworkOmsInit> errordepts = new ArrayList<ThirdWxworkOmsInit>();
		WxworkOmsRelationModel omsdept = null;
		ThirdWxworkOmsInit omsinit = null;
		fdCheckAllDeptCount = wxdpkeys.size();
		for (String wxpn : wxdpkeys) {
			logger.debug("匹配企业微信部门：" + wxpn + "...");
			String _key = matchDept(wxpn, ekpDept);
			if (StringUtil.isNotNull(_key)) {
				omsdept = new WxworkOmsRelationModel();
				omsdept.setFdAppKey(getAppKey());
				omsdept.setFdEkpId(ekpDept.get(_key));
				omsdept.setFdAppPkId(wxDeptMap.get(wxpn));
				savedepts.add(omsdept);
				logger.debug("匹配上了企业微信部门：" + wxpn);
			} else if (!relationKeyMap.containsKey(wxDeptMap.get(wxpn))) {
				omsinit = new ThirdWxworkOmsInit();
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
				errordepts.add(omsinit);
				logger.debug("没有匹配上企业微信部门：" + wxpn);
			}
			fdCheckDeptCurrentCount++;
		}
		rtnMap.put("savedepts", savedepts);
		rtnMap.put("errordepts", errordepts);
		return rtnMap;
	}

	// 匹配部门
	private String matchDept(String wxpn, Map<String, String> ekpDept) {
		for (String key : ekpDept.keySet()) {
			if (key.endsWith(wxpn)) {
				return key;
			}
		}
		return null;
	}

	@Override
	public Map<String, List> personMatch() throws Exception {
		logger.debug("企业微信人员匹配开始...");
		Map<String, List> rtnMap = new HashMap<String, List>();
		Map<String, String> wxpersonMap = (Map<String, String>) omsInitCache
				.get("wxperson");
		Map<String, String> personln = (Map<String, String>) omsInitCache
				.get("personln");
		Map<String, String> personmb = (Map<String, String>) omsInitCache
				.get("personmb");
		Map<String, String> personem = (Map<String, String>) omsInitCache
				.get("personem");
		List<WxworkOmsRelationModel> savePersons = new ArrayList<WxworkOmsRelationModel>();
		List<ThirdWxworkOmsInit> errorPersons = new ArrayList<ThirdWxworkOmsInit>();
		List<String> wxpkeys = new ArrayList<String>(wxpersonMap.keySet());
		WxworkOmsRelationModel omsdept = null;
		ThirdWxworkOmsInit omsinit = null;
		JSONObject jo = null;
		String appid = null;
		fdCheckAllPersonCount = wxpkeys.size();
		for (String wxpkey : wxpkeys) {
			logger.debug("人员：" + wxpersonMap.get(wxpkey) + "匹配中...");
			jo = JSONObject.fromObject(wxpersonMap.get(wxpkey));
			appid = jo.getString("userid");
			// 根据登录名匹配
			if (personln.containsKey(wxpkey)) {
				omsdept = new WxworkOmsRelationModel();
				omsdept.setFdAppKey(getAppKey());
				omsdept.setFdEkpId(personln.get(wxpkey));
				omsdept.setFdAppPkId(appid);
				savePersons.add(omsdept);
				logger.debug("人员：" + wxpkey + "根据登录名匹配上了");
			} else if (jo.containsKey("mobile")
					&& StringUtil.isNotNull(jo.getString("mobile"))
					&& personmb.get(jo.getString("mobile")) != null) {// 根据手机号码匹配
				omsdept = new WxworkOmsRelationModel();
				omsdept.setFdAppKey(getAppKey());
				omsdept.setFdEkpId(personmb.get(jo.getString("mobile")));
				omsdept.setFdAppPkId(appid);
				savePersons.add(omsdept);
				logger.debug("人员：" + wxpkey + "根据手机号匹配上了");
			} else if (jo.containsKey("email")
					&& StringUtil.isNotNull(jo.getString("email"))
					&& getValueIgnoreCase(personem,
							jo.getString("email")) != null) {// 根据邮件地址匹配
				omsdept = new WxworkOmsRelationModel();
				omsdept.setFdAppKey(getAppKey());
				omsdept.setFdEkpId(
						getValueIgnoreCase(personem, jo.getString("email")));
				omsdept.setFdAppPkId(appid);
				savePersons.add(omsdept);
				logger.debug("人员：" + wxpkey + "根据邮件地址匹配上了");
			} else if (!relationKeyMap.containsKey(appid)) {
				logger.debug("人员：" + wxpkey + "没有匹配上");
				omsinit = new ThirdWxworkOmsInit();
				omsinit.setFdEkpStatus("0");
				omsinit.setFdWxStatus("0");
				omsinit.setFdHandleStatus("0");
				if (jo.containsKey("name")){
					String name = EmojiFilter.filterEmoji(jo.getString("name"));
					omsinit.setFdName(name);
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
		List<WxworkOmsRelationModel> savedepts = map.get("savedepts");
		List<ThirdWxworkOmsInit> errordepts = map.get("errordepts");
		fdCheckAllDeptCount = savedepts.size() + errordepts.size();
		for (WxworkOmsRelationModel model : savedepts) {
			if (relationMap.get(model.getFdEkpId()) == null) {
				wxworkOmsRelationService.add(model);
				relationMap.put(model.getFdEkpId(), model);
			}
			fdCheckDeptCurrentCount++;
		}
		for (ThirdWxworkOmsInit model : errordepts) {
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
		List<WxworkOmsRelationModel> savePersons = map.get("savePersons");
		List<ThirdWxworkOmsInit> errorPersons = map.get("errorPersons");
		fdCheckAllPersonCount = savePersons.size() + errorPersons.size();
		for (WxworkOmsRelationModel model : savePersons) {
			if (relationMap.get(model.getFdEkpId()) == null) {
				wxworkOmsRelationService.add(model);
				relationMap.put(model.getFdEkpId(), model);
			}
			fdCheckPersonCurrentCount++;
		}
		for (ThirdWxworkOmsInit model : errorPersons) {
			add(model);
			fdCheckPersonCurrentCount++;
		}
	}

	private void initData(String type) throws Exception {
		relationMap = new HashMap<String, WxworkOmsRelationModel>();
		relationKeyMap = new HashMap<String, String>();
		wxworkApiService = WxworkUtils.getWxworkApiService();
		// 获取中间表的数据
		List relationlist = wxworkOmsRelationService
				.findList("fdAppKey='" + getAppKey() + "'", null);
		fdCheckAllDeptCount = relationlist.size() + 1;
		fdCheckAllPersonCount = relationlist.size() + 1;
		for (int i = 0; i < relationlist.size(); i++) {
			WxworkOmsRelationModel model = (WxworkOmsRelationModel) relationlist.get(i);
			relationMap.put(model.getFdEkpId(), model);
			relationKeyMap.put(model.getFdAppPkId(), model.getFdEkpId());
			fdCheckDeptCurrentCount++;
			fdCheckPersonCurrentCount++;
		}
		String sql = "delete from ThirdWxworkOmsInit";
		if ("org".equals(type)) {
			sql += " where fdIsOrg = 1";
		} else {
			sql += " where fdIsOrg = 0";
		}
		getBaseDao().getHibernateSession().createQuery(sql).executeUpdate();
		getBaseDao().getHibernateSession().flush();
		getBaseDao().getHibernateSession().clear();
		fdCheckDeptCurrentCount++;
		fdCheckPersonCurrentCount++;
		// 初始化缓存数据
		omsInitCache.clear();
	}

	@Override
	public void updateDept(JSONObject json) throws Exception {
		Map<String, List> map = null;
		try {
			if (!checkRoot()) {
				logger.info("企业微信集成中的根目录配置错误，请及时修正！");
				return;
			}
			fdDept = true;
			// 初始化数据
			fdCheckDeptState = 0;
			fdDeptStatusMessage = "初始化数据...";
			fdCheckDeptCurrentCount = 0;
			fdCheckAllDeptCount = 0;
			long time = System.currentTimeMillis();
			initData("org");
			logger.info(fdDeptStatusMessage + "耗时："
					+ (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 获取微信企业号的组织数据
			fdDeptStatusMessage = "获取企业微信的组织数据...";
			fdCheckDeptCurrentCount = 0;
			fdCheckAllDeptCount = 0;
			getWxAllDeparts(wxworkApiService);
			logger.info(fdDeptStatusMessage + "耗时："
					+ (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 获取EKP的组织数据
			fdDeptStatusMessage = "获取EKP的组织数据...";
			fdCheckDeptCurrentCount = 0;
			fdCheckAllDeptCount = 0;
			getEKPAllDeparts();
			logger.info(fdDeptStatusMessage + "耗时："
					+ (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 数据匹配
			fdDeptStatusMessage = "数据匹配...";
			fdCheckDeptCurrentCount = 0;
			fdCheckAllDeptCount = 0;
			map = deptMatch();
			logger.info(fdDeptStatusMessage + "耗时："
					+ (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 数据处理
			fdDeptStatusMessage = "数据处理...";
			fdCheckDeptCurrentCount = 0;
			fdCheckAllDeptCount = 0;
			deptSave(map);
			logger.info(fdDeptStatusMessage + "耗时："
					+ (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
		} catch (Exception e) {
			fdCheckDeptState = 1;
			Thread.sleep(2000);
			throw new RuntimeException(e);
		} finally {
			if(map!=null){
				List<ThirdWxworkOmsInit> errordepts = map.get("errordepts");
				if(errordepts!=null&&errordepts.size()>0){
					json.put("errors", "1");
				}else{
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

	@Override
	public void updatePerson() throws Exception {
		Map<String, List> map = null;
		try {
			if (!checkRoot()) {
				logger.info("企业微信集成中的根目录配置错误，请及时修正！");
				return;
			}
			fdPerson = true;
			// 初始化数据
			fdCheckPersonState = 0;
			fdPersonStatusMessage = "初始化数据...";
			fdCheckPersonCurrentCount = 0;
			fdCheckAllPersonCount = 0;
			long time = System.currentTimeMillis();
			initData("person");
			logger.info(fdPersonStatusMessage + "耗时："
					+ (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 获取微信企业号的组织数据
			fdPersonStatusMessage = "获取企业微信的人员数据...";
			fdCheckPersonCurrentCount = 0;
			fdCheckAllPersonCount = 0;
			getWxAllPersons(wxworkApiService);
			logger.info(fdPersonStatusMessage + "耗时："
					+ (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 获取EKP的组织数据
			fdPersonStatusMessage = "获取EKP的人员数据...";
			fdCheckPersonCurrentCount = 0;
			fdCheckAllPersonCount = 0;
			getEKPAllPersons();
			logger.info(fdPersonStatusMessage + "耗时："
					+ (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 数据匹配
			fdPersonStatusMessage = "数据匹配...";
			fdCheckPersonCurrentCount = 0;
			fdCheckAllPersonCount = 0;
			map = personMatch();
			logger.info(fdPersonStatusMessage + "耗时："
					+ (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 数据处理
			fdPersonStatusMessage = "数据处理...";
			fdCheckPersonCurrentCount = 0;
			fdCheckAllPersonCount = 0;
			personSave(map);
			logger.info(fdDeptStatusMessage + "耗时："
					+ (System.currentTimeMillis() - time) / 1000);
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
	
	@Override
	public void updatePerson(JSONObject json) throws Exception {
		Map<String, List> map = null;
		try {
			if (!checkRoot()) {
				logger.info("企业微信集成中的根目录配置错误，请及时修正！");
				return;
			}
			fdPerson = true;
			// 初始化数据
			fdCheckPersonState = 0;
			fdPersonStatusMessage = "初始化数据...";
			fdCheckPersonCurrentCount = 0;
			fdCheckAllPersonCount = 0;
			long time = System.currentTimeMillis();
			initData("person");
			logger.info(fdPersonStatusMessage + "耗时："
					+ (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 获取微信企业号的组织数据
			fdPersonStatusMessage = "获取企业微信的人员数据...";
			fdCheckPersonCurrentCount = 0;
			fdCheckAllPersonCount = 0;
			getWxAllPersons(wxworkApiService);
			logger.info(fdPersonStatusMessage + "耗时："
					+ (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 获取EKP的组织数据
			fdPersonStatusMessage = "获取EKP的人员数据...";
			fdCheckPersonCurrentCount = 0;
			fdCheckAllPersonCount = 0;
			getEKPAllPersons();
			logger.info(fdPersonStatusMessage + "耗时："
					+ (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 数据匹配
			fdPersonStatusMessage = "数据匹配...";
			fdCheckPersonCurrentCount = 0;
			fdCheckAllPersonCount = 0;
			map = personMatch();
			logger.info(fdPersonStatusMessage + "耗时："
					+ (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 数据处理
			fdPersonStatusMessage = "数据处理...";
			fdCheckPersonCurrentCount = 0;
			fdCheckAllPersonCount = 0;
			personSave(map);
			logger.info(fdDeptStatusMessage + "耗时："
					+ (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
		} catch (Exception e) {
			fdCheckPersonState = 1;
			Thread.sleep(2000);
			throw new RuntimeException(e);
		} finally {
			if(map!=null){
				List<ThirdWxworkOmsInit> errorPersons = map.get("errorPersons");
				if(errorPersons!=null&&errorPersons.size()>0){
					json.put("errors", "1");
				}else{
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
	public void updateWx(String fdId, String wxId, String type)
			throws Exception {
		if (wxworkApiService == null) {
            wxworkApiService = WxworkUtils.getWxworkApiService();
        }
		if (StringUtil.isNull(wxId) || StringUtil.isNull(fdId)) {
			logger.debug("要删除的部门ID为空");
			return;
		}
		ThirdWxworkOmsInit init = (ThirdWxworkOmsInit) findByPrimaryKey(fdId);
		if (init == null || "1".equals(init.getFdWxStatus())) {
            return;
        }
		boolean flag = true;
		if ("0".equals(type)) {
			try {
				// 删除微信企业号的人员
				com.alibaba.fastjson.JSONObject result = wxworkApiService
						.userDelete(wxId);
				if (result.getIntValue("errcode") > 0) {
					logger.debug(
							" 微信处理人员出错：errorCode="
									+ result.getIntValue("errcode")
									+ ",errorMsg="
									+ result.getString("errmsg"));
				}

				// 删除EKP中间异常表的映射数据
				delete(init);
			} catch (Exception e) {
				flag = false;
				logger.debug(
						" 微信处理人员出错：" + e.getMessage());
			}
		} else {
			// 把部门下面的人员移动到根目录下
			String rootId = WeixinWorkConfig.newInstance().getWxRootId();
			try {
				List<WxUser> users = wxworkApiService
						.userList(Long.parseLong(wxId), true, 0);
				Long[] rootIds = {1L};
				if (StringUtil.isNotNull(rootId) && !"1".equals(rootId)) {
					rootIds[0] = Long.parseLong(rootId);
				}
				if (users != null && users.size() > 0) {
					for (WxUser user : users) {
						user.setDepartIds(rootIds);
						wxworkApiService.userUpdate(user);
								}
							}
			} catch (Exception e) {
				flag = false;
				logger.debug(
						" 微信处理人员出错：" + e.getMessage());
				throw new KmssRuntimeException(e);
			}
			// 获取部门及部门下所有的子部门并删除
			try {
				List<WxDepart> departs = wxworkApiService.departGet(wxId);
				if (departs != null && departs.size() > 0) {
					List<WxDepart> superDeparts = new ArrayList();
					int count = 0;
					while (departs.size() > 0) {
						count++;
						WxDepart depart = null;
						for (int i = departs.size() - 1; i >= 0; i--) {
							depart = departs.get(i);
							com.alibaba.fastjson.JSONObject result = wxworkApiService
									.departDelete(depart.getId());
							List<ThirdWxworkOmsInit> omslist = findList(
									"fdWeixinId='" + depart.getId() + "'",
									null);
							for (ThirdWxworkOmsInit omsinit : omslist) {
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
		if (!flag) {
			logger.error("微信处理人员出错");
		}
	}

	@Override
	public boolean updateEKP(String fdId, String fdEKPId, String type)
			throws Exception {
		boolean flag = true;
		// 更新中间表
		ThirdWxworkOmsInit init = (ThirdWxworkOmsInit) findByPrimaryKey(fdId);
		WxworkOmsRelationModel model = (WxworkOmsRelationModel) wxworkOmsRelationService
				.findFirstOne("fdAppKey='" + getAppKey() + "' and fdEkpId='"
						+ fdEKPId + "'", null);
		if(model == null){
			model = (WxworkOmsRelationModel) wxworkOmsRelationService
					.findFirstOne("fdAppKey='" + getAppKey() + "' and fdAppPkId='"
							+ init.getFdWeixinId() + "'", null);
			if (model == null) {
				model = new WxworkOmsRelationModel();
			}
			String oldEkpId = model.getFdEkpId();
			String oldAppKey = model.getFdAppKey();
			String oldAppPkId = model.getFdAppPkId();
			model.setFdEkpId(fdEKPId);
			model.setFdAppKey(getAppKey());
			model.setFdAppPkId(init.getFdWeixinId());
			if (UserOperHelper.allowLogOper("updateEKP", "*")) {
				UserOperContentHelper.putUpdate(model)
						.putSimple("fdEKPId", oldEkpId, model.getFdEkpId())
						.putSimple("fdAppKey", oldAppKey, model.getFdAppKey())
						.putSimple("fdAppPkId", oldAppPkId,
								model.getFdAppPkId());
			}
			wxworkOmsRelationService.update(model);
			// 删除EKP中间异常表的映射数据
			delete(init);
		}else{
			flag = false;
		}
		return flag;
	}

	private boolean checkRoot() throws Exception {
		boolean rtn = true;
		if (!"true".equals(WeixinWorkConfig.newInstance().getWxEnabled())) {
			logger.info("企业微信集成已经关闭");
			return false;
		}
		String rootId = WeixinWorkConfig.newInstance().getWxRootId();
		if ("1".equals(rootId) || StringUtil.isNull(rootId)) {
			return rtn;
		} else {
			try {
				List<WxDepart> list = WxworkUtils.getWxworkApiService()
						.departGet(rootId);
				if (list == null || list.size() == 0) {
					return false;
				}
				return rtn;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			}
		}
	}

	/**
	 * map忽略key的大小写
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	private String getValueIgnoreCase(Map<String, String> map, String key) {
		for (String k : map.keySet()) {
			// 空值跳过
			if (null == k || null == key) {
				continue;
			}
			if (k.equalsIgnoreCase(key)) {
				return map.get(k);
			}
		}
		return null;
	}
}
