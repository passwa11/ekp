package com.landray.kmss.third.weixin.service.spring;

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
import com.landray.kmss.third.weixin.api.WxApiService;
import com.landray.kmss.third.weixin.constant.WxConstant;
import com.landray.kmss.third.weixin.model.ThirdWxOmsInit;
import com.landray.kmss.third.weixin.model.WeixinConfig;
import com.landray.kmss.third.weixin.model.api.WxDepart;
import com.landray.kmss.third.weixin.model.api.WxUser;
import com.landray.kmss.third.weixin.service.IThirdWxOmsInitService;
import com.landray.kmss.third.weixin.spi.model.WxOmsRelationModel;
import com.landray.kmss.third.weixin.spi.service.IWxOmsRelationService;
import com.landray.kmss.third.weixin.util.WxUtils;
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
public class ThirdWxOmsInitServiceImp extends ExtendDataServiceImp
		implements
			SysOrgConstant,
			WxConstant,
			IXMLDataBean,
			IThirdWxOmsInitService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdWxOmsInitServiceImp.class);

	private static KmssCache omsInitCache = new KmssCache(
			ThirdWxOmsInitServiceImp.class);

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

	private IWxOmsRelationService wxOmsRelationService;

	public void setWxOmsRelationService(
			IWxOmsRelationService wxOmsRelationService) {
		this.wxOmsRelationService = wxOmsRelationService;
	}

	private WxApiService wxApiService = null;
	private Map<String, WxOmsRelationModel> relationMap = null;
	private Map<String, String> relationKeyMap = null;

	@Override
	public void getWxAllPersons(WxApiService wxApiService) throws Exception {
		List<WxUser> users = new ArrayList<WxUser>();
		List<WxDepart> departs = (List<WxDepart>) omsInitCache.get("wxdp");
		if (departs == null) {
			getWxAllDeparts(wxApiService);
			departs = (List<WxDepart>) omsInitCache.get("wxdp");
		}
		fdCheckAllPersonCount = departs.size();
		for (int i = 0; i < departs.size(); i++) {
			users.addAll(
					wxApiService.userList(departs.get(i).getId(), false, 0));
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
	public void getWxAllDeparts(WxApiService wxApiService) throws Exception {
		String rootId = WeixinConfig.newInstance().getWxRootId();
		List<WxDepart> departs = new ArrayList<WxDepart>();
		if (StringUtil.isNull(rootId)) {
			departs = wxApiService.departGet();
		} else {
			departs = wxApiService.departGet(rootId);
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
					departs.get(i).getParentId() + "");
			fdCheckDeptCurrentCount++;
		}
		Map<String, String> map = new HashMap<String, String>(departs.size());
		Map<String, String> imap = new HashMap<String, String>(departs.size());
		// 剔除根目录
		if (StringUtil.isNotNull(rootId)) {
			rootId = WeixinConfig.newInstance().getWxRootId();
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
		Map<String, List> rtnMap = new HashMap<String, List>();
		Map<String, String> wxDeptMap = (Map<String, String>) omsInitCache
				.get("wxdept");
		Map<String, String> ekpDept = (Map<String, String>) omsInitCache
				.get("dept");
		List<String> wxdpkeys = new ArrayList<String>(wxDeptMap.keySet());
		List<WxOmsRelationModel> savedepts = new ArrayList<WxOmsRelationModel>();
		List<ThirdWxOmsInit> errordepts = new ArrayList<ThirdWxOmsInit>();
		WxOmsRelationModel omsdept = null;
		ThirdWxOmsInit omsinit = null;
		fdCheckAllDeptCount = wxdpkeys.size();
		for (String wxpn : wxdpkeys) {
			if (ekpDept.containsKey(wxpn)) {
				omsdept = new WxOmsRelationModel();
				omsdept.setFdAppKey(getAppKey());
				omsdept.setFdEkpId(ekpDept.get(wxpn));
				omsdept.setFdAppPkId(wxDeptMap.get(wxpn));
				savedepts.add(omsdept);
			} else if (!relationKeyMap.containsKey(wxDeptMap.get(wxpn))) {
				omsinit = new ThirdWxOmsInit();
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
			}
			fdCheckDeptCurrentCount++;
		}
		rtnMap.put("savedepts", savedepts);
		rtnMap.put("errordepts", errordepts);
		return rtnMap;
	}

	@Override
	public Map<String, List> personMatch() throws Exception {
		Map<String, List> rtnMap = new HashMap<String, List>();
		Map<String, String> wxpersonMap = (Map<String, String>) omsInitCache
				.get("wxperson");
		Map<String, String> personln = (Map<String, String>) omsInitCache
				.get("personln");
		Map<String, String> personmb = (Map<String, String>) omsInitCache
				.get("personmb");
		Map<String, String> personem = (Map<String, String>) omsInitCache
				.get("personem");
		List<WxOmsRelationModel> savePersons = new ArrayList<WxOmsRelationModel>();
		List<ThirdWxOmsInit> errorPersons = new ArrayList<ThirdWxOmsInit>();
		List<String> wxpkeys = new ArrayList<String>(wxpersonMap.keySet());
		WxOmsRelationModel omsdept = null;
		ThirdWxOmsInit omsinit = null;
		JSONObject jo = null;
		String appid = null;
		fdCheckAllPersonCount = wxpkeys.size();
		for (String wxpkey : wxpkeys) {
			jo = JSONObject.fromObject(wxpersonMap.get(wxpkey));
			appid = jo.getString("userid");
			// 根据登录名匹配
			if (personln.containsKey(wxpkey)) {
				omsdept = new WxOmsRelationModel();
				omsdept.setFdAppKey(getAppKey());
				omsdept.setFdEkpId(personln.get(wxpkey));
				omsdept.setFdAppPkId(appid);
				savePersons.add(omsdept);
			} else if (jo.containsKey("mobile")
					&& StringUtil.isNotNull(jo.getString("mobile"))
					&& personmb.get(jo.getString("mobile")) != null) {// 根据手机号码匹配
				omsdept = new WxOmsRelationModel();
				omsdept.setFdAppKey(getAppKey());
				omsdept.setFdEkpId(personmb.get(jo.getString("mobile")));
				omsdept.setFdAppPkId(appid);
				savePersons.add(omsdept);
			} else if (jo.containsKey("email")
					&& StringUtil.isNotNull(jo.getString("email"))
					&& getValueIgnoreCase(personem,
							jo.getString("email")) != null) {// 根据邮件地址匹配
				omsdept = new WxOmsRelationModel();
				omsdept.setFdAppKey(getAppKey());
				omsdept.setFdEkpId(
						getValueIgnoreCase(personem, jo.getString("email")));
				omsdept.setFdAppPkId(appid);
				savePersons.add(omsdept);
			} else if (!relationKeyMap.containsKey(appid)) {
				omsinit = new ThirdWxOmsInit();
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
		List<WxOmsRelationModel> savedepts = map.get("savedepts");
		List<ThirdWxOmsInit> errordepts = map.get("errordepts");
		fdCheckAllDeptCount = savedepts.size() + errordepts.size();
		for (WxOmsRelationModel model : savedepts) {
			if (relationMap.get(model.getFdEkpId()) == null) {

				if (UserOperHelper.allowLogOper("updateDept", "*")) {
					UserOperContentHelper.putAdd(model, "fdAppKey", "fdEkpId",
							"fdAppPkId");
				}
				wxOmsRelationService.add(model);
				relationMap.put(model.getFdEkpId(), model);
			}
			fdCheckDeptCurrentCount++;
		}
		for (ThirdWxOmsInit model : errordepts) {
			if (StringUtil.isNull(model.getFdName())
					&& StringUtil.isNotNull(model.getFdPath())) {
                model.setFdName(model.getFdPath());
            }
			add(model);
			if (UserOperHelper.allowLogOper("updateDept", "*")) {
				UserOperContentHelper.putAdd(model, "fdEkpStatus", "fdWxStatus",
						"fdHandleStatus", "fdName", "FdPath", "fdIsWx",
						"fdIsOrg", "fdWeixinId");
			}
			fdCheckDeptCurrentCount++;
		}
	}

	@Override
	public void personSave(Map<String, List> map) throws Exception {
		// 人员数据的保存
		List<WxOmsRelationModel> savePersons = map.get("savePersons");
		List<ThirdWxOmsInit> errorPersons = map.get("errorPersons");
		fdCheckAllPersonCount = savePersons.size() + errorPersons.size();
		for (WxOmsRelationModel model : savePersons) {
			if (relationMap.get(model.getFdEkpId()) == null) {
				wxOmsRelationService.add(model);
				if (UserOperHelper.allowLogOper("updatePerson", "*")) {
					UserOperContentHelper.putAdd(model, "fdAppKey", "fdEkpId",
							"fdAppPkId");
				}
				relationMap.put(model.getFdEkpId(), model);
			}
			fdCheckPersonCurrentCount++;
		}
		for (ThirdWxOmsInit model : errorPersons) {
			if (UserOperHelper.allowLogOper("updatePerson", "*")) {
				UserOperContentHelper.putAdd(model, "fdEkpStatus", "fdWxStatus",
						"fdHandleStatus", "fdName", "fdStatus", "FdPath",
						"fdIsWx", "fdIsOrg", "fdWeixinId");
			}
			add(model);
			fdCheckPersonCurrentCount++;
		}
	}

	private void initData(String type) throws Exception {
		relationMap = new HashMap<String, WxOmsRelationModel>();
		relationKeyMap = new HashMap<String, String>();
		wxApiService = WxUtils.getWxApiService();
		// 获取中间表的数据
		List relationlist = wxOmsRelationService
				.findList("fdAppKey='" + getAppKey() + "'", null);
		fdCheckAllDeptCount = relationlist.size() + 1;
		fdCheckAllPersonCount = relationlist.size() + 1;
		for (int i = 0; i < relationlist.size(); i++) {
			WxOmsRelationModel model = (WxOmsRelationModel) relationlist.get(i);
			relationMap.put(model.getFdEkpId(), model);
			relationKeyMap.put(model.getFdAppPkId(), model.getFdEkpId());
			fdCheckDeptCurrentCount++;
			fdCheckPersonCurrentCount++;
		}
		String sql = "delete from ThirdWxOmsInit";
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
				logger.info("微信集成中的根目录配置错误，请及时修正！");
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
			fdDeptStatusMessage = "获取微信企业号的组织数据...";
			fdCheckDeptCurrentCount = 0;
			fdCheckAllDeptCount = 0;
			getWxAllDeparts(wxApiService);
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
				List<ThirdWxOmsInit> errordepts = map.get("errordepts");
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
			fdPersonStatusMessage = "获取微信企业号的人员数据...";
			fdCheckPersonCurrentCount = 0;
			fdCheckAllPersonCount = 0;
			getWxAllPersons(wxApiService);
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
			fdPersonStatusMessage = "获取微信企业号的人员数据...";
			fdCheckPersonCurrentCount = 0;
			fdCheckAllPersonCount = 0;
			getWxAllPersons(wxApiService);
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
				List<ThirdWxOmsInit> errorPersons = map.get("errorPersons");
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
		if (wxApiService == null) {
            wxApiService = WxUtils.getWxApiService();
        }
		if (StringUtil.isNull(wxId) || StringUtil.isNull(fdId)) {
			logger.debug("要删除的部门ID为空");
			return;
		}
		ThirdWxOmsInit init = (ThirdWxOmsInit) findByPrimaryKey(fdId);
		if (init == null || "1".equals(init.getFdWxStatus())) {
            return;
        }
		boolean flag = true;
		if ("0".equals(type)) {
			try {
				// 删除微信企业号的人员
				com.alibaba.fastjson.JSONObject result = wxApiService
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
			String rootId = WeixinConfig.newInstance().getWxRootId();
			try {
				List<WxUser> users = wxApiService
						.userList(Long.parseLong(wxId), true, 0);
				Long[] rootIds = {1L};
				if (StringUtil.isNotNull(rootId) && !"1".equals(rootId)) {
					rootIds[0] = Long.parseLong(rootId);
				}
				if (users != null && users.size() > 0) {
					for (WxUser user : users) {
						user.setDepartIds(rootIds);
						wxApiService.userUpdate(user);
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
				List<WxDepart> departs = wxApiService.departGet(wxId);

				if (departs != null && departs.size() > 0) {
					List<WxDepart> superDeparts = new ArrayList();
					int count = 0;
					while (departs.size() > 0) {
						count++;
						WxDepart depart = null;
						for (int i = departs.size() - 1; i >= 0; i--) {
							depart = departs.get(i);
							com.alibaba.fastjson.JSONObject result = wxApiService
									.departDelete(depart.getId());

							List<ThirdWxOmsInit> omslist = findList(
									"fdWeixinId='" + depart.getId() + "'",
									null);
							for (ThirdWxOmsInit omsinit : omslist) {
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
				logger.debug(" 微信处理部门出错：" + e.getMessage());
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
		ThirdWxOmsInit init = (ThirdWxOmsInit) findByPrimaryKey(fdId);
		WxOmsRelationModel model = (WxOmsRelationModel) wxOmsRelationService
				.findFirstOne("fdAppKey='" + getAppKey() + "' and fdEkpId='"
						+ fdEKPId + "'", null);
		if(model == null){
			model = (WxOmsRelationModel) wxOmsRelationService
					.findFirstOne("fdAppKey='" + getAppKey() + "' and fdAppPkId='"
							+ init.getFdWeixinId() + "'", null);
			if (model == null) {
				model = new WxOmsRelationModel();
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
			wxOmsRelationService.update(model);
			// 删除EKP中间异常表的映射数据
			delete(init);
		}else{
			flag = false;
		}
		return flag;
	}

	private boolean checkRoot() throws Exception {
		boolean rtn = true;
		String rootId = WeixinConfig.newInstance().getWxRootId();
		if (!"true".equals(WeixinConfig.newInstance().getWxEnabled())) {
			logger.info("微信企业号集成已经关闭");
			return false;
		}
		if ("1".equals(rootId) || StringUtil.isNull(rootId)) {
			return rtn;
		} else {
			try {
				wxApiService.departGet(rootId);
				return rtn;
			} catch (Exception e) {
				logger.info(e.getMessage());
				logger.info("企业微信集成中的根目录配置错误，请及时修正！");
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
