package com.landray.kmss.third.ding.service.spring;

import com.dingtalk.api.response.OapiUserListbypageResponse.Userlist;
import com.dingtalk.api.response.OapiV2UserListResponse;
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
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.third.ding.model.ThirdDingOmsInit;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingOmsInitService;
import com.landray.kmss.third.ding.util.DingHttpClientUtil;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.EmojiFilter;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.util.*;

/**
 * 组织初始化业务接口实现
 * 
 * @author
 * @version 1.0 2017-06-14
 */
public class ThirdDingOmsInitServiceImp extends ExtendDataServiceImp
		implements SysOrgConstant, DingConstant, IXMLDataBean, IThirdDingOmsInitService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingOmsInitServiceImp.class);

	private static KmssCache omsInitCache = new KmssCache(ThirdDingOmsInitServiceImp.class);

	private ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	private ISysOrgPersonService sysOrgPersonService;

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	private IOmsRelationService omsRelationService;

	public void setOmsRelationService(IOmsRelationService omsRelationService) {
		this.omsRelationService = omsRelationService;
	}

	private DingApiService dingApiService = null;
	private Map<String, OmsRelationModel> relationMap = null;
	private Map<String, String> relationKeyMap = null;

	/**
	 * 是否专属账号优先
	 */
	private boolean exclusiveAccountEnable=false;

	@Override
	public void getDingAllPersons(DingApiService dingApiService) throws Exception {
		JSONArray departs = (JSONArray) omsInitCache.get("dingdp");
		if (departs == null) {
			getDingAllDeparts(dingApiService);
			departs = (JSONArray) omsInitCache.get("dingdp");
		}
		fdCheckAllPersonCount = departs.size();
		List<OapiV2UserListResponse.ListUserResponse> users = new ArrayList<OapiV2UserListResponse.ListUserResponse>();
		for (int i = 0; i < departs.size(); i++) {
			dingApiService.userList_v2(users, departs.getJSONObject(i).getLong("id"), 0L);
			fdCheckPersonCurrentCount++;
		}
		if (users == null || users.size() == 0) {
			logger.warn("无法获取钉钉人员数据");
		}
		Map<String, String> nmap = new HashMap<String, String>(users.size());
		Map<String, String> dingExclusiveMap = new HashMap<String, String>();
		Map<String, String> dtparent = (Map<String, String>) omsInitCache.get("dingdeptid");
		JSONObject json = null;
		List<Long> dpPid = null;

		//判断专属账号优先
		Map<String, String> exclusiveAccountMap = new HashMap<String, String>();


		fdCheckAllPersonCount = users.size();
		for (OapiV2UserListResponse.ListUserResponse user:users) {
			json = new JSONObject();
			dpPid = user.getDeptIdList();
			logger.debug("dpPid:" + dpPid);
			json.put("dept", "");
			if (dpPid != null && dpPid.size() > 0) {
				json.put("dept", dtparent.get(dpPid.get(0) + ""));
				logger.debug("ding dept:" + dpPid.get(0) + "  dtparent.get:"
						+ dtparent.get(dpPid.get(0) + ""));
			}
			json.put("name", user.getName());
			json.put("userid", user.getUserid());
			json.put("avatar", user.getAvatar());
			json.put("unionid", user.getUnionid());
			json.put("exclusiveAccount", user.getExclusiveAccount());
			json.put("exclusiveAccountType", user.getExclusiveAccountType());
			json.put("mobile", "");
			if (StringUtil.isNotNull(user.getMobile())) {
                json.put("mobile", user.getMobile());
            }
			json.put("email", "");
			if (StringUtil.isNotNull(user.getEmail())) {
                json.put("email", user.getEmail());
            }
			json.put("jobnumber", "");
			if (StringUtil.isNotNull(user.getJobNumber())) {
                json.put("jobnumber", user.getJobNumber());
            }
			if(json.get("dept")!=null){
				json.put("pname", json.get("dept")+">>"+json.getString("name"));
			}else{
				json.put("pname", json.getString("name"));
			}
			if(this.exclusiveAccountEnable && user.getExclusiveAccount()){
				if(StringUtil.isNull(user.getLoginId())){
					dingExclusiveMap.put(user.getUserid(),json.toString());
				}else {
					dingExclusiveMap.put(user.getLoginId(),json.toString());
				}
			}
			nmap.put(json.getString("userid"), json.toString());

			fdCheckPersonCurrentCount++;
		}
		omsInitCache.put("dingperson", nmap);
		omsInitCache.put("dingExclusiveperson", dingExclusiveMap);
		nmap = null;
		users = null;
		dtparent = null;
		departs = null;
	}

	@Override
	public void getDingAllDeparts(DingApiService dingApiService) throws Exception {
		String rootId = DingConfig.newInstance().getDingDeptid();
		JSONArray departs = new JSONArray();
		JSONObject depart = null;
		if (StringUtil.isNotNull(rootId)) {
			depart = dingApiService.departsGet(rootId);
		} else {
			depart = dingApiService.departGet();
		}
		if (depart != null && depart.getInt("errcode") == 0) {
			departs = depart.getJSONArray("department");
			if (StringUtil.isNotNull(rootId)) {
				depart = dingApiService.departGet(Long.parseLong(rootId));
				departs.add(depart);
			}
		}
		fdCheckAllDeptCount = departs.size() * 2;
		if (departs == null || departs.size() == 0) {
			logger.debug("无法获取钉钉部门数据");
		}
		omsInitCache.put("dingdp", departs);
		// 组装钉钉的部门层级架构
		Map<String, String> nmap = new HashMap<String, String>(departs.size());
		Map<String, String> pmap = new HashMap<String, String>(departs.size());
		for (int i = 0; i < departs.size(); i++) {
			nmap.put(departs.getJSONObject(i).getInt("id") + "", departs.getJSONObject(i).getString("name"));
			if (departs.getJSONObject(i).containsKey("parentid")) {
                pmap.put(departs.getJSONObject(i).getInt("id") + "", departs.getJSONObject(i).getInt("parentid") + "");
            }
			fdCheckDeptCurrentCount++;
		}
		Map<String, String> map = new HashMap<String, String>(departs.size());
		Map<String, String> imap = new HashMap<String, String>(departs.size());
		// 剔除根目录
		if (StringUtil.isNotNull(rootId)) {
			rootId = DingConfig.newInstance().getDingDeptid();
		} else {
			rootId = "1";
		}
		for (int i = 0; i < departs.size(); i++) {
			if ((departs.getJSONObject(i).getInt("id") + "").equals(rootId)) {
                continue;
            }
			String pname = "";
			String pid = "";
			if (departs.getJSONObject(i).containsKey("parentid")) {
				pid = departs.getJSONObject(i).getInt("parentid") + "";
			} else {
				continue;
			}
			do {
				if (StringUtil.isNull(pname)) {
                    pname = departs.getJSONObject(i).getString("name");
                }
				if (StringUtil.isNotNull(nmap.get(pid))) {
					if (!"1".equals(rootId) && pid.equals(rootId)) {
						pname = nmap.get(pid) + ">>" + pname;
						break;
					}
					if (!pid.equals(rootId)) {
						pname = nmap.get(pid) + ">>" + pname;
						pid = pmap.get(pid);
						if (StringUtil.isNull(pid)) {
                            break;
                        }
					} else {
						break;
					}
				} else {
					break;
				}
			} while (StringUtil.isNotNull(pid) || pid.equals(rootId));
			map.put(pname, departs.getJSONObject(i).getInt("id") + "");
			imap.put(departs.getJSONObject(i).getInt("id") + "", pname);
			fdCheckDeptCurrentCount++;
		}
		omsInitCache.put("dingdept", map);
		omsInitCache.put("dingdeptid", imap);
		map = null;
		imap = null;
		nmap = null;
		pmap = null;
		departs = null;
	}

	@Override
	public void getEKPAllPersons() throws Exception {
		Map<String, String> iddept = (Map<String, String>) omsInitCache.get("iddept");
		if(iddept==null||iddept.isEmpty()){
			getEKPAllDeparts();
			iddept = (Map<String, String>) omsInitCache.get("iddept");
		}
		String wb = "fdOrgType=" + ORG_TYPE_PERSON + "and fdIsAvailable=1";
		List<SysOrgPerson> list = sysOrgPersonService.findList(wb, null);
		fdCheckAllPersonCount = list.size();
		Map<String, String> map = new HashMap<String, String>(list.size());
		Map<String, String> mbMap = new HashMap<String, String>(list.size());
		Map<String, String> emMap = new HashMap<String, String>(list.size());
		Map<String, String> nomMap = new HashMap<String, String>(list.size());
		Map<String, String> nMap = new HashMap<String, String>(list.size());
		Map<String, String> exclusiveFiledMap = new HashMap<String, String>(); //专属账号字段

		//配置的字段是否常规的配置字段里：手机号、登录名、邮箱、编号
		boolean inCommonFiled = false;
		String exclusiveFiled  = DingConfig.newInstance().getOrg2dingIsExclusiveAccountLoginName();
		if(this.exclusiveAccountEnable){
			//专属账号字段
			inCommonFiled = checkExclusiveFiled(exclusiveFiled);
		}

		if(inCommonFiled){
			for (int i = 0; i < list.size(); i++) {
				map.put(list.get(i).getFdLoginName(), list.get(i).getFdId());
				mbMap.put(list.get(i).getFdMobileNo(), list.get(i).getFdId());
				emMap.put(list.get(i).getFdEmail(), list.get(i).getFdId());
				nomMap.put(list.get(i).getFdNo(), list.get(i).getFdId());
				if(list.get(i).getFdParent()!=null){
					nMap.put(iddept.get(list.get(i).getFdParent().getFdId())+">>"+list.get(i).getFdName(), list.get(i).getFdId());
				}else{
					nMap.put(list.get(i).getFdName(), list.get(i).getFdId());
				}
				fdCheckPersonCurrentCount++;
			}
			//遍历完之后，把之前配置的字段的值引用
			switch (exclusiveFiled){
				case "fdMobileNo" :
					exclusiveFiledMap=mbMap;
					break;
				case "fdEmail" :
					exclusiveFiledMap=emMap;
					break;
				case "fdLoginName" :
					exclusiveFiledMap=map;
					break;
				case "fdNo" :
					exclusiveFiledMap=nomMap;
					break;
			}
		}else{
			for (int i = 0; i < list.size(); i++) {
				if(this.exclusiveAccountEnable&&StringUtil.isNotNull(exclusiveFiled)){
					String value = getPropertyValue(exclusiveFiled,list.get(i));
					logger.warn("用户：{} 的专属字段：{} 的专属登录名的值为：{}",list.get(i).getFdName(),exclusiveFiled,value);
					if(StringUtil.isNotNull(value)) {
                        exclusiveFiledMap.put(value,list.get(i).getFdId());
                    }
				}
				map.put(list.get(i).getFdLoginName(), list.get(i).getFdId());
				mbMap.put(list.get(i).getFdMobileNo(), list.get(i).getFdId());
				emMap.put(list.get(i).getFdEmail(), list.get(i).getFdId());
				nomMap.put(list.get(i).getFdNo(), list.get(i).getFdId());
				if(list.get(i).getFdParent()!=null){
					nMap.put(iddept.get(list.get(i).getFdParent().getFdId())+">>"+list.get(i).getFdName(), list.get(i).getFdId());
				}else{
					nMap.put(list.get(i).getFdName(), list.get(i).getFdId());
				}
				fdCheckPersonCurrentCount++;
			}
		}

		omsInitCache.put("personln", map);
		omsInitCache.put("personmb", mbMap);
		omsInitCache.put("personem", emMap);
		omsInitCache.put("personno", nomMap);
		omsInitCache.put("personname", nMap);
		if(this.exclusiveAccountEnable){
			omsInitCache.put("exclusiveFiled", exclusiveFiledMap);
		}
		map = null;
		mbMap = null;
		emMap = null;
		list = null;
	}

	/**
	 * 获取组织属性值
	 * @param key 属性名称
	 * @param element 组织对象
	 * @return
	 */
	private String getPropertyValue(String key, SysOrgElement element) {
		try {
			//logger.debug("key:" + key + " ,name:" + element.getFdName());
			if (StringUtil.isNull(key)) {
                return null;
            }
			Map<String, Object> customMap = element.getCustomPropMap();
			Object obj = "";
			if (customMap != null && customMap.containsKey(key)) {
				if (customMap.get(key) == null) {
					return null;
				}
				obj = customMap.get(key);
			} else {
				obj = PropertyUtils.getProperty(element,key);
			}
			if (obj!=null && ("fdStaffingLevel".equals(key) || "hbmParent".equals(key) ||  "fdParent".equals(key))) {
				obj = PropertyUtils.getProperty(obj,"fdName");
			}
			if(obj==null){
				return null;
			}
			String v = obj.toString();
			if(obj instanceof Date){
				v = ((Date)obj).getTime()+"";
			}
			//logger.debug("element("+element.getFdId()+","+element.getFdName()+"), key:"+key+"，value:" + v);
			return v;
		} catch (Exception e) {
			logger.error("根据字段获取部门的值过程中发生了异常",e);
		}
		return null;
	}

	private boolean checkExclusiveFiled(String exclusiveAccountLoginName) {
		return "fdMobileNo".equalsIgnoreCase(exclusiveAccountLoginName)||
				"fdEmail".equalsIgnoreCase(exclusiveAccountLoginName)||
				"fdLoginName".equalsIgnoreCase(exclusiveAccountLoginName)||
				"fdNo".equalsIgnoreCase(exclusiveAccountLoginName);
	}

	private List<SysOrgElement> getAllOrgByRootOrg() throws Exception {
		List<SysOrgElement> allOrgInRootOrg = new ArrayList<SysOrgElement>();
		String rootid = DingConfig.newInstance().getDingOrgId();
		SysOrgElement ele = null;
		if (StringUtil.isNull(rootid)) {
			return allOrgInRootOrg;
		} else {
			List allOrgChildren = sysOrgElementService.findList("fdOrgType=1 and fdIsAvailable=1", null);
			for (int i = 0; i < allOrgChildren.size(); i++) {
				SysOrgElement org = (SysOrgElement) allOrgChildren.get(i);
				if (rootid.indexOf(org.getFdId()) != -1) {
					allOrgInRootOrg.add(org);
				} else {
					SysOrgElement parent = org.getFdParent();
					while (parent != null) {
						if (rootid.indexOf(parent.getFdId()) != -1) {
							allOrgInRootOrg.add(org);
							break;
						}
						parent = parent.getFdParent();
					}
				}
			}
			for (String id : rootid.split(";")) {
				ele = (SysOrgElement) sysOrgElementService.findByPrimaryKey(id);
				if (!allOrgInRootOrg.contains(ele)) {
                    allOrgInRootOrg.add(ele);
                }
			}
		}
		return allOrgInRootOrg;
	}

	@Override
	public void getEKPAllDeparts() throws Exception {
		String erootId = DingConfig.newInstance().getDingDeptid();
		String rootid = DingConfig.newInstance().getDingOrgId();
		String wb = "fdOrgType in (" + ORG_TYPE_ORG + "," + ORG_TYPE_DEPT + ") and fdIsAvailable=1 ";
		List<String> rootnames = new ArrayList<String>();
		List<SysOrgElement> orgs = getAllOrgByRootOrg();
		String where = "";
		if (StringUtil.isNotNull(rootid)) {
			String ekppn = null;
			for (SysOrgElement org : orgs) {
				 ekppn = org.getFdParentsName(">>");
				//ekppn=org.getFdName();
				if (StringUtil.isNotNull(erootId) && !rootnames.contains(ekppn)) {
					rootnames.add(ekppn);
				}
				if (StringUtil.isNull(where)) {
					where += " fdHierarchyId like '" + org.getFdHierarchyId() + "%'";
				} else {
					where += " or fdHierarchyId like '" + org.getFdHierarchyId() + "%'";
				}
			}
			if (StringUtil.isNotNull(where)) {
                wb += " and (" + where + ") ";
            }
		}
		List<SysOrgElement> list = sysOrgElementService.findList(wb, "fdHierarchyId desc");
		fdCheckAllDeptCount = list.size();
		Map<String, String> map = new HashMap<String, String>(list.size());
		Map<String, String> idmap = new HashMap<String, String>(list.size());
		String pname = null;
		for (int i = 0; i < list.size(); i++) {
			pname = list.get(i).getFdParentsName(">>");
			if (StringUtil.isNotNull(pname)) {
				pname = pname + ">>" + list.get(i).getFdName();
				for (String rootname : rootnames) {
					if (StringUtil.isNotNull(rootname)) {
                        pname = pname.replace(rootname + ">>", "");
                    }
				}
				map.put(pname, list.get(i).getFdId());
				idmap.put(list.get(i).getFdId(), pname);
			} else {
				map.put(list.get(i).getFdName(), list.get(i).getFdId());
				idmap.put(list.get(i).getFdId(), list.get(i).getFdName());
			}
			fdCheckDeptCurrentCount++;
		}
		omsInitCache.put("dept", map);
		omsInitCache.put("iddept", idmap);
		map = null;
		list = null;
	}

	private String getAppKey() {
		return StringUtil.isNull(DING_OMS_APP_KEY) ? "default" : DING_OMS_APP_KEY;
	}

	@Override
	public Map<String, List> deptMatch() throws Exception {
		Map<String, List> rtnMap = new HashMap<String, List>();
		Map<String, String> dingDeptMap = (Map<String, String>) omsInitCache.get("dingdept");
		Map<String, String> ekpDept = (Map<String, String>) omsInitCache.get("dept");
		List<String> dingdpkeys = new ArrayList<String>(dingDeptMap.keySet());
		List<OmsRelationModel> savedepts = new ArrayList<OmsRelationModel>();
		List<ThirdDingOmsInit> errordepts = new ArrayList<ThirdDingOmsInit>();
		OmsRelationModel omsdept = null;
		ThirdDingOmsInit omsinit = null;
		fdCheckAllDeptCount = dingdpkeys.size();
		for (String dingpn : dingdpkeys) {
			if (ekpDept.containsKey(dingpn)) {
				omsdept = new OmsRelationModel();
				omsdept.setFdAppKey(getAppKey());
				omsdept.setFdEkpId(ekpDept.get(dingpn));
				omsdept.setFdAppPkId(dingDeptMap.get(dingpn));
				omsdept.setFdType("2");
				savedepts.add(omsdept);
			} else if (!relationKeyMap.containsKey(dingDeptMap.get(dingpn))) {
				omsinit = new ThirdDingOmsInit();
				omsinit.setFdEkpStatus("0");
				omsinit.setFdDingStatus("0");
				omsinit.setFdHandleStatus("0");
				if (dingpn.indexOf(">>") > -1) {
					omsinit.setFdName(dingpn.substring(dingpn.lastIndexOf(">>") + 2));
				} else {
					omsinit.setFdName(dingpn);
				}
				omsinit.setFdPath(dingpn);
				omsinit.setFdIsOrg(true);
				omsinit.setFdDingId(dingDeptMap.get(dingpn));
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
		Map<String, String> dingpersonMap = (Map<String, String>) omsInitCache.get("dingperson");
		Map<String, String> personln = (Map<String, String>) omsInitCache.get("personln");
		Map<String, String> personmb = (Map<String, String>) omsInitCache.get("personmb");
		Map<String, String> personem = (Map<String, String>) omsInitCache.get("personem");
		Map<String, String> personno = (Map<String, String>) omsInitCache.get("personno");
		Map<String, String> personname = (Map<String, String>) omsInitCache.get("personname");

		//专属账号信息
		Map<String, String> ekp_exclusiveFiled = new HashMap<String, String> ();
		Map<String, String> dingExclusiveperson = new HashMap<String, String> ();
		if(this.exclusiveAccountEnable){
			ekp_exclusiveFiled = (Map<String, String>) omsInitCache.get("exclusiveFiled");
			dingExclusiveperson = (Map<String, String>) omsInitCache.get("dingExclusiveperson");
		}
		List<OmsRelationModel> savePersons = new ArrayList<OmsRelationModel>();
		List<ThirdDingOmsInit> errorPersons = new ArrayList<ThirdDingOmsInit>();
		List<String> dingpkeys = new ArrayList<String>(dingpersonMap.keySet());
		OmsRelationModel omsdept = null;
		ThirdDingOmsInit omsinit = null;
		JSONObject jo = null;
		String appid = null;
		fdCheckAllPersonCount = dingpkeys.size();

		//存放已经匹配上的专属账号
		Set<String> hadMatchData = new HashSet<String>();
		//先匹配专属账号
		if(this.exclusiveAccountEnable && dingExclusiveperson!=null && !dingExclusiveperson.isEmpty()){
			logger.warn("-----优先匹配专属账号--------");
			String ekp_fdId = null;
			for(String loginId : dingExclusiveperson.keySet()){
				jo = JSONObject.fromObject(dingExclusiveperson.get(loginId));
				ekp_fdId = null;
				if (ekp_exclusiveFiled.containsKey(loginId)) {// 根据字段匹配
					logger.warn("根据 字段 配上了专属账号{}",loginId);
					ekp_fdId = ekp_exclusiveFiled.get(loginId);
				}else if (personln.containsKey(loginId)) {// 根据登录名匹配
					logger.warn("根据 登录名 配上了专属账号{}",loginId);
					ekp_fdId = personln.get(loginId);
				}
				if(StringUtil.isNotNull(ekp_fdId)){
					omsdept = new OmsRelationModel(ekp_fdId,
							getAppKey(),jo.getString("userid"),"8",
							jo.getString("avatar"),jo.getString("unionid"),
							jo.getString("exclusiveAccountType"));
					savePersons.add(omsdept);
					hadMatchData.add(jo.getString("userid"));
				}
			}
		}

		for (String dingpkey : dingpkeys) {
			//如果专属账号匹配上了，则忽略匹配
			if(hadMatchData.contains(dingpkey)){
				logger.warn("专属账号已匹配该账号：{}，匹配跳过！！！",dingpkey);
				continue;
			}

			jo = JSONObject.fromObject(dingpersonMap.get(dingpkey));
			logger.debug("dingpkey:" + dingpkey);
			appid = jo.getString("userid");
			String accountType = jo.getBoolean("exclusiveAccount")?jo.getString("exclusiveAccountType"):"common";
			if (personln.containsKey(dingpkey)) {// 根据登录名匹配
				logger.debug("根据登录名匹配="+jo.getString("pname"));
				omsdept = new OmsRelationModel(personln.get(dingpkey),
						getAppKey(),appid,"8",jo.getString("avatar"),jo.getString("unionid"),accountType);
				savePersons.add(omsdept);
			} else if (jo.containsKey("mobile") && StringUtil.isNotNull(jo.getString("mobile"))
					&& personmb.get(jo.getString("mobile")) != null) {// 根据手机号码匹配
				logger.debug("根据手机号码匹配="+jo.getString("pname"));
				omsdept = new OmsRelationModel(personmb.get(jo.getString("mobile")),
						getAppKey(),appid,"8",jo.getString("avatar"),jo.getString("unionid"),accountType);
				savePersons.add(omsdept);
			} else if (jo.containsKey("email") && StringUtil.isNotNull(jo.getString("email"))
					&& personem.get(jo.getString("email")) != null) {// 根据邮件地址匹配
				logger.debug("根据邮件地址匹配="+jo.getString("pname"));
				omsdept = new OmsRelationModel(personem.get(jo.getString("email")),
						                       getAppKey(),appid,"8",jo.getString("avatar"),jo.getString("unionid"),accountType);
				savePersons.add(omsdept);
			} else if (jo.containsKey("jobnumber") && StringUtil.isNotNull(jo.getString("jobnumber"))
					&& personno.get(jo.getString("jobnumber")) != null) {// 根据工号匹配
				logger.debug("根据工号匹配="+jo.getString("pname"));
				omsdept = new OmsRelationModel(personno.get(jo.getString("jobnumber")),
						getAppKey(),appid,"8",jo.getString("avatar"),jo.getString("unionid"),accountType);
				savePersons.add(omsdept);
			} else if (jo.containsKey("pname") && StringUtil.isNotNull(jo.getString("pname"))
					&& personname.get(jo.getString("pname")) != null) {// 根据完整部门路径下人名进行匹配
				logger.debug("根据完整部门路径下人名进行匹配="+jo.getString("pname"));
				omsdept = new OmsRelationModel(personname.get(jo.getString("pname")),
						getAppKey(),appid,"8",jo.getString("avatar"),jo.getString("unionid"),accountType);
				savePersons.add(omsdept);
			} else if (jo.containsKey("jobnumber")
					&& StringUtil.isNotNull(jo.getString("jobnumber"))
					&& personln.get(jo.getString("jobnumber")) != null) {// 根据工号匹配登录名
																			// F4
				logger.debug("根据工号匹配登录名=" + jo.getString("pname"));
				omsdept = new OmsRelationModel(personln.get(jo.getString("jobnumber")),
						getAppKey(),appid,"8",jo.getString("avatar"),jo.getString("unionid"),accountType);
				savePersons.add(omsdept);

			} else if (!relationKeyMap.containsKey(appid)) {
				logger.debug("无法匹配人员="+jo.getString("pname"));
				omsinit = new ThirdDingOmsInit();
				omsinit.setFdEkpStatus("0");
				omsinit.setFdDingStatus("0");
				omsinit.setFdHandleStatus("0");
				if (jo.containsKey("name")) {
                    omsinit.setFdName(EmojiFilter.filterEmoji(jo.getString("name")));
                }
				if (jo.containsKey("dept")) {
                    omsinit.setFdPath(jo.getString("dept"));
                }
				omsinit.setFdAccountType(jo.getBoolean("exclusiveAccount")?jo.getString("exclusiveAccountType"):"common");
				omsinit.setFdIsOrg(false);
				omsinit.setFdDingId(appid);
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
		List<OmsRelationModel> savedepts = map.get("savedepts");
		List<ThirdDingOmsInit> errordepts = map.get("errordepts");
		fdCheckAllDeptCount = savedepts.size() + errordepts.size();
		for (OmsRelationModel model : savedepts) {
			if (relationMap.get(model.getFdEkpId()) == null) {
				if (UserOperHelper.allowLogOper("updateDept", getModelName())) {
					UserOperContentHelper.putAdd(model);
				}
				omsRelationService.add(model);
				relationMap.put(model.getFdEkpId(), model);
			}
			fdCheckDeptCurrentCount++;
		}
		for (ThirdDingOmsInit model : errordepts) {
			if (StringUtil.isNull(model.getFdName()) && StringUtil.isNotNull(model.getFdPath())) {
                model.setFdName(model.getFdPath());
            }
			if (UserOperHelper.allowLogOper("updateDept", getModelName())) {
				UserOperContentHelper.putAdd(model, "fdName");
			}
			add(model);
			fdCheckDeptCurrentCount++;
		}
	}

	@Override
	public void personSave(Map<String, List> map) throws Exception {
		// 人员数据的保存
		List<OmsRelationModel> savePersons = map.get("savePersons");
		List<ThirdDingOmsInit> errorPersons = map.get("errorPersons");
		fdCheckAllPersonCount = savePersons.size() + errorPersons.size();
		for (OmsRelationModel model : savePersons) {
			if (relationMap.get(model.getFdEkpId()) == null) {
				if (UserOperHelper.allowLogOper("updatePerson", getModelName())) {
					UserOperContentHelper.putAdd(model);
				}
				omsRelationService.add(model);
				relationMap.put(model.getFdEkpId(), model);
			} else {
				//钉钉同步到ekp，添加头像信息
				if ("2".equals(DingConfig.newInstance().getSyncSelection())) {
					// 人员已经存在，添加头像信息
					OmsRelationModel per = (OmsRelationModel) omsRelationService
							.findFirstOne("fdEkpId='" + model.getFdEkpId()
									+ "' and fdAppKey='"
									+ getAppKey() + "'", null);
					if (per != null) {
						per.setFdAvatar(model.getFdAvatar());
						omsRelationService.update(per);
					}
				}

				//更新unionId
				OmsRelationModel temp_oriModel = relationMap.get(model.getFdEkpId());
				if(StringUtil.isNull(temp_oriModel.getFdUnionId())||StringUtil.isNull(temp_oriModel.getFdAccountType())){
					OmsRelationModel oriModel = (OmsRelationModel) omsRelationService.findByPrimaryKey(temp_oriModel.getFdId(),OmsRelationModel.class,true);
					if (oriModel != null) {
						oriModel.setFdUnionId(model.getFdUnionId());
						oriModel.setFdAccountType(model.getFdAccountType());
						omsRelationService.update(oriModel);
					}
				}
			}
			fdCheckPersonCurrentCount++;
		}
		for (ThirdDingOmsInit model : errorPersons) {
			if (UserOperHelper.allowLogOper("updatePerson", getModelName())) {
				UserOperContentHelper.putAdd(model);
			}
			add(model);
			fdCheckPersonCurrentCount++;
		}
	}

	private void initData(String type) throws Exception {
		// 清洗重复的数据
		updateHandlerRepeatData();	
		updateRelationType();
        //专属账号开关
		checkExclusiveAccountEnable(type);

		relationMap = new HashMap<String, OmsRelationModel>();
		relationKeyMap = new HashMap<String, String>();
		dingApiService = DingUtils.getDingApiService();
		// 获取中间表的数据
		List relationlist = omsRelationService.findList("fdAppKey='" + getAppKey() + "'", null);
		fdCheckAllDeptCount = relationlist.size() + 1;
		fdCheckAllPersonCount = relationlist.size() + 1;
		for (int i = 0; i < relationlist.size(); i++) {
			OmsRelationModel model = (OmsRelationModel) relationlist.get(i);
			relationMap.put(model.getFdEkpId(), model);
			relationKeyMap.put(model.getFdAppPkId(), model.getFdEkpId());
			fdCheckDeptCurrentCount++;
			fdCheckPersonCurrentCount++;
		}
		String sql = "delete from ThirdDingOmsInit";
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

	private void checkExclusiveAccountEnable(String type) {
		if ("person".equals(type)) {
			DingConfig dingConfig = DingConfig.newInstance();
			String syncSelection = dingConfig.getSyncSelection();
			if("1".equals(syncSelection) || "3".equals(syncSelection)){
				String _exclusiveAccountEnable = dingConfig.getExclusiveAccountEnable();
				if(StringUtil.isNotNull(_exclusiveAccountEnable)&&"true".equalsIgnoreCase(_exclusiveAccountEnable)){
					this.exclusiveAccountEnable=true;
					return;
				}
			}
		}
		this.exclusiveAccountEnable=false;
	}

	@Override
	public void updateDept(JSONObject json) throws Exception {
		Map<String, List> map = null;
		try {
			if (!checkRoot()) {
                logger.info("钉钉集成中的根目录配置错误，请及时修正！");
            }
			fdDept = true;
			// 初始化数据
			fdCheckDeptState = 0;
			fdDeptStatusMessage = "初始化数据...";
			fdCheckDeptCurrentCount = 0;
			fdCheckAllDeptCount = 0;
			long time = System.currentTimeMillis();
			initData("org");
			logger.info(fdDeptStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 获取钉钉的组织数据
			fdDeptStatusMessage = "获取钉钉的组织数据...";
			fdCheckDeptCurrentCount = 0;
			fdCheckAllDeptCount = 0;
			getDingAllDeparts(dingApiService);
			logger.info(fdDeptStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 获取EKP的组织数据
			fdDeptStatusMessage = "获取EKP的组织数据...";
			fdCheckDeptCurrentCount = 0;
			fdCheckAllDeptCount = 0;
			getEKPAllDeparts();
			logger.info(fdDeptStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 数据匹配
			fdDeptStatusMessage = "数据匹配...";
			fdCheckDeptCurrentCount = 0;
			fdCheckAllDeptCount = 0;
			map = deptMatch();
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
				List<ThirdDingOmsInit> errordepts = map.get("errordepts");
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

	@Override
	public void updatePerson(JSONObject json) throws Exception {
		Map<String, List> map = null;
		try {
			fdPerson = true;
			// 初始化数据
			fdCheckPersonState = 0;
			fdPersonStatusMessage = "初始化数据...";
			fdCheckPersonCurrentCount = 0;
			fdCheckAllPersonCount = 0;
			long time = System.currentTimeMillis();
			initData("person");
			logger.info(fdPersonStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 获取钉钉的组织数据
			fdPersonStatusMessage = "获取钉钉的人员数据...";
			fdCheckPersonCurrentCount = 0;
			fdCheckAllPersonCount = 0;
			getDingAllPersons(dingApiService);
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
			map = personMatch();
			logger.info(fdPersonStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 数据处理
			fdPersonStatusMessage = "数据处理...";
			fdCheckPersonCurrentCount = 0;
			fdCheckAllPersonCount = 0;
			personSave(map);
			logger.info(fdDeptStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();

			// 映射表数据优化
			updateOmsDuplicate();
		} catch (Exception e) {
			fdCheckPersonState = 1;
			Thread.sleep(2000);
			throw new RuntimeException(e);
		} finally {
			if (map != null) {
				List<ThirdDingOmsInit> errorPersons = map.get("errorPersons");
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

	private static boolean locked = false;

	@Override
	public void updatePerson(SysQuartzJobContext context) throws Exception {
		Map<String, List> map = null;
		if (locked) {
			logger.error("存在运行中的钉钉人员同步任务，当前任务中断...");
			context.logMessage("存在运行中的钉钉人员同步任务，当前任务中断...");
			return;
		}
		if (!"true".equals(DingConfig.newInstance().getDingEnabled())) {
			logger.info("钉钉集成已经关闭，故不同步数据");
			context.logMessage("钉钉集成已经关闭，故不同步数据");
			return;
		}

		if (StringUtil.isNull(DingConfig.newInstance().getDingOmsOutEnabled())) {
			if (StringUtil.isNotNull(DingConfig.newInstance().getSyncSelection()) && !"3".equals(DingConfig.newInstance().getSyncSelection())) {
				logger.info("钉钉集成-通讯录配置-同步选择-仅从钉钉获取人员对应关系未开启， 所以不执行更新人员对照表的定时任务");
				context.logMessage("钉钉集成-通讯录配置-同步选择-仅从钉钉获取人员对应关系未开启， 所以不执行更新人员对照表的定时任务");
				return;
			}
		}
		try {
			locked = true;
			fdPerson = true;
			// 初始化数据
			fdCheckPersonState = 0;
			fdPersonStatusMessage = "初始化数据...";
			fdCheckPersonCurrentCount = 0;
			fdCheckAllPersonCount = 0;
			long time = System.currentTimeMillis();
			initData("person");
			logger.info(fdPersonStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 获取钉钉的组织数据
			fdPersonStatusMessage = "获取钉钉的人员数据...";
			fdCheckPersonCurrentCount = 0;
			fdCheckAllPersonCount = 0;
			getDingAllPersons(dingApiService);
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
			map = personMatch();
			logger.info(fdPersonStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();
			// 数据处理
			fdPersonStatusMessage = "数据处理...";
			fdCheckPersonCurrentCount = 0;
			fdCheckAllPersonCount = 0;
			personSave(map);
			logger.info(fdDeptStatusMessage + "耗时：" + (System.currentTimeMillis() - time) / 1000);
			time = System.currentTimeMillis();

			// 映射表数据优化
			updateOmsDuplicate();
		} catch (Exception e) {
			fdCheckPersonState = 1;
			Thread.sleep(2000);
			throw new RuntimeException(e);
		} finally {
			locked = false;
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
	public void updateDing(String fdId, String dingId, String type) throws Exception {
		if (dingApiService == null) {
            dingApiService = DingUtils.getDingApiService();
        }
		if (StringUtil.isNull(dingId) || StringUtil.isNull(fdId)) {
			logger.debug("要删除的部门ID为空");
			return;
		}
		ThirdDingOmsInit init = (ThirdDingOmsInit) findByPrimaryKey(fdId);
		if (init == null || "1".equals(init.getFdDingStatus())) {
            return;
        }
		String temp = null;
		try {
			if ("0".equals(type)) {
				// 删除钉钉的人员
				temp = dingApiService.userDelete(dingId);
				logger.debug("删除" + init.getFdName() + "人员，钉钉返回信息：" + temp);
				// 删除EKP中间异常表的映射数据
				if ("ok".equals(temp) || (parseJsonStr(temp) && JSONObject.fromObject(temp).getInt("errcode") == 60121)){
					UserOperHelper.logDelete(init);// 记录日志信息
					delete(init);
				}
			} else {
				// 获取部门及部门下所有的子部门并删除
				JSONArray departs = null;
				int departId;
				String url = DingConstant.DING_PREFIX + "/department/list?access_token=" + dingApiService.getAccessToken()
						+ "&id=" + dingId
						+ DingUtil.getDingAppKeyByEKPUserId("&", null);
				JSONObject depart = DingHttpClientUtil.httpGet(url, null, JSONObject.class);
				// 删除当前选择的子部门
				if (depart != null && depart.getInt("errcode") == 0) {
					departs = depart.getJSONArray("department");
					if (departs != null && departs.size() > 0) {
						for (int i = departs.size() - 1; i >= 0; i--) {
							departId = departs.getJSONObject(i).getInt("id");
							// 把部门下的人员更新到根目录
							delDingPerson(departId);
							// 删除当前部门
							temp = dingApiService.departDelete((departId) + "");
							// 删除中间表
							if ("ok".equals(temp)
									|| (parseJsonStr(temp) && JSONObject.fromObject(temp).getInt("errcode") == 60003)) {
								List<ThirdDingOmsInit> omslist = findList("fdDingId='" + departId + "'", null);
								for (ThirdDingOmsInit omsinit : omslist) {
									UserOperHelper.logDelete(init);// 记录日志信息
									delete(omsinit);
								}
							}
						}
					}
				}
				// 删除当前选择的部门
				// 把部门下的人员更新到根目录
				delDingPerson(Long.parseLong(dingId));
				// 删除当前部门
				temp = dingApiService.departDelete(dingId + "");
				// 删除中间表
				if ("ok".equals(temp)
						|| (parseJsonStr(temp) && JSONObject.fromObject(temp).getInt("errcode") == 60003)) {
					List<ThirdDingOmsInit> omslist = findList("fdDingId='" + dingId + "'", null);
					for (ThirdDingOmsInit omsinit : omslist) {
						UserOperHelper.logDelete(init);// 记录日志信息
						delete(omsinit);
					}
				}
			}
		} catch (Exception e) {
			logger.debug("钉钉处理出错：errorCode=" + e.getMessage());
			throw new KmssRuntimeException(e);
		}
	}

	private boolean parseJsonStr(String json) {
		if (StringUtil.isNull(json)) {
            return false;
        }
		if (json.startsWith("{") && json.endsWith("}")) {
			return true;
		} else {
			return false;
		}
	}

	private void delDingPerson(long deptId) throws Exception {
		String rootId = DingConfig.newInstance().getDingDeptid();
		if (!checkRoot()) {
            rootId = "1";
        }
		List<Userlist> users = new ArrayList<Userlist>();
		dingApiService.userList(users, Long.parseLong(deptId+""), 0L);
		JSONArray rootIds = new JSONArray();
		rootIds.add(1);
		if (StringUtil.isNotNull(rootId) && !"1".equals(rootId)) {
			rootIds.add(Long.parseLong(rootId));
		}
		JSONObject json = null;
		String temp = null;
		for (Userlist user:users) {
			json = new JSONObject();
			json.put("department", rootIds);
			json.put("userid", user.getUserid());
			temp = dingApiService.userUpdate(json);
			logger.debug("更新" + user.getName() + "人员，钉钉返回信息：" + temp);
		}
	}

	@Override
	public boolean updateEKP(String fdId, String fdEKPId, String type) throws Exception {
		boolean flag = true;
		ThirdDingOmsInit init = (ThirdDingOmsInit) findByPrimaryKey(fdId);
		// 更新中间表
		OmsRelationModel model = (OmsRelationModel) omsRelationService.findFirstOne("fdAppKey='" + getAppKey() + "' and fdEkpId='" + fdEKPId + "'",
				null);
		if (model == null) {
			model = (OmsRelationModel) omsRelationService
					.findFirstOne("fdAppKey='" + getAppKey() + "' and fdAppPkId='" + init.getFdDingId() + "'", null);
			if (model == null) {
				model = new OmsRelationModel();
			}
			// 验证用户账号是否还存在
			if("0".equals(type)){
				DingApiService dingApiService = DingUtils.getDingApiService();
				JSONObject rs = dingApiService.userGet(init.getFdDingId(),null);
				String unionId=null;
				if(rs!=null&&rs.containsKey("errcode")&&rs.getInt("errcode")==0){
					unionId=rs.getString("unionid");
					model.setFdUnionId(unionId);
				}else{
					logger.warn("获取用户unionId失败！！！"+rs);
					return false;
				}
			}
			String oldEKPId = model.getFdEkpId();
			String oldAppKey = model.getFdAppKey();
			String oldAppPkId = model.getFdAppPkId();
			model.setFdEkpId(fdEKPId);
			model.setFdAppKey(getAppKey());
			model.setFdAppPkId(init.getFdDingId());
			model.setFdType("8");
			if("dept".equals(type)){
				model.setFdType("2");
			}
			if (UserOperHelper.allowLogOper("updateEKP", getModelName())) {
				UserOperContentHelper.putUpdate(model).putSimple("fdEkpId", oldEKPId, model.getFdEkpId())
						.putSimple("fdAppKey", oldAppKey, model.getFdAppKey())
						.putSimple("fdAppPkId", oldAppPkId, model.getFdAppPkId());
			}
			omsRelationService.update(model);
			// 删除EKP中间异常表的映射数据
			delete(init);
		} else {
			flag = false;
		}
		return flag;
	}

	private boolean checkRoot() throws Exception {
		boolean rtn = true;
		String rootId = DingConfig.newInstance().getDingDeptid();
		if ("1".equals(rootId) || StringUtil.isNull(rootId)) {
			return rtn;
		} else {
			if (dingApiService == null) {
                dingApiService = DingUtils.getDingApiService();
            }
			JSONObject depart = dingApiService.departGet(Long.parseLong(rootId));
			if (depart != null && depart.getInt("errcode") == 0) {
				return rtn;
			} else {
				return false;
			}
		}
	}

	/**
	 * 人员中间表数据去重
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean updateOmsDuplicate() throws Exception {
		if (!"true".equals(DingConfig.newInstance().getDingEnabled())) {
			logger.info("钉钉集成已经关闭，故不同步数据");
			return false;
		}
		List<String> ormToDelList = new ArrayList<>();
		try {
			long time = System.currentTimeMillis();
			// 查询重复数据并去重
			logger.info("查询重复数据并去重");
			// 初始化钉钉api
			dingApiService = DingUtils.getDingApiService();
			logger.info("处理不匹配数据");
			// 查询中间表数据
			HQLInfo hqlInfo = new HQLInfo();
			List<OmsRelationModel> ormList = (List<OmsRelationModel>) omsRelationService
					.findList(hqlInfo);
			Map<String, OmsRelationModel> omsMap = new HashMap<String, OmsRelationModel>();
			String key = null;
			for (OmsRelationModel model : ormList) {
				key = model.getFdAppPkId() + model.getFdEkpId();
				if (omsMap.containsKey(key)) {
					ormToDelList.add(model.getFdId());
				} else {
					omsMap.put(key, model);
				}
			}

			// 遍历中间表，如果ekp不存在，删除映射，如果钉钉不存在删除映射
			List<OmsRelationModel> ormLists = new ArrayList<OmsRelationModel>(
					omsMap.values());
			for (OmsRelationModel orm : ormLists) {
				String ekpId = orm.getFdEkpId();
				String dingId = orm.getFdAppPkId();
				SysOrgElement ekpResult = (SysOrgElement) sysOrgElementService
						.findByPrimaryKey(ekpId, null, true);
				if (null != ekpResult && ekpResult.getFdIsAvailable()) {
					// 部门
					if (ekpResult.getFdOrgType().intValue()==2
							|| ekpResult.getFdOrgType().intValue()==1) {
						JSONObject dingDepartResult = dingApiService
								.departGet(Long.parseLong(dingId));
						if (null != dingDepartResult
								&& dingDepartResult.getInt("errcode") == 60003) {
							ormToDelList.add(orm.getFdId());
						}
					} else if (ekpResult.getFdOrgType().intValue() == 8) {
						JSONObject dingUserResult = dingApiService
								.userGet(dingId, ekpResult.getFdId());
						if (null != dingUserResult
								&& (dingUserResult.getInt("errcode") == 60121 || dingUserResult.getInt("errcode") ==60111)) {
							ormToDelList.add(orm.getFdId());
						}
					}
				} else {
					ormToDelList.add(orm.getFdId());
				}
			}

			// 删除操作
			if (null != ormToDelList && ormToDelList.size() > 0) {
				int toDelSize = ormToDelList.size();
				logger.debug("total=>" + toDelSize);
				int perSize = 100;
				int pg = (int) Math.ceil(toDelSize / (double) perSize);
				logger.debug("pg=>" + pg);
				// 分批次删除
				for (int k = 0; k < pg; k++) {
					logger.debug("k=>" + k);
					int st = k * perSize;
					int last = toDelSize % perSize;
					last = (((k + 1) * perSize) > toDelSize) ? last : perSize;
					int ed = st + last;
					logger.debug("st=>" + st);
					logger.debug("ed=>" + ed);
					List<String> subIds = ormToDelList.subList(st, ed);
					// 操作删除
					delNotMatch(subIds);
					logger.info("第" + k + "次删除操作,删除的关联数据id=>" + subIds);
				}
			}

			logger.info(
					"删除不匹配数据耗时：" + (System.currentTimeMillis() - time) / 1000);

			return true;
		} catch (Exception e) {
			Thread.sleep(2000);
			throw new RuntimeException(e);
		} finally {
			ormToDelList = null;
		}
	}

	/**
	 * 删除不匹配的
	 * 
	 * @param idList
	 */
	private void delNotMatch(List idList) {
		Session session = getBaseDao().getHibernateSession();
		String sql = "delete from OmsRelationModel where fdId in (:ids)";
		Query query = session.createQuery(sql);
		query.setParameterList("ids", idList);
		int result = query.executeUpdate();
		logger.info("查询重复数据,list=>" + JSONUtils.valueToString(result));
		session.flush();
		session.clear();
	}
	
	private void updateHandlerRepeatData() throws Exception{
		TransactionStatus status = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			String sql = "select fd_import_info from sys_org_element GROUP BY fd_import_info having count(fd_id)>1";
			List<Object[]> repeats = sysOrgElementService.getBaseDao().getHibernateSession().createNativeQuery(sql).list();
			if (repeats != null && repeats.size() > 0) {
				List<SysOrgElement> eles = null;
				List<OmsRelationModel> models = null;
				for(Object obj:repeats){
					if(obj==null || StringUtil.isNull(obj.toString())) {
                        continue;
                    }
					eles = sysOrgElementService.findList("fdImportInfo='" + obj.toString() + "'", null);
					for(SysOrgElement ele:eles){
						if(!ele.getFdIsAvailable()){
							ele.setFdImportInfo(null);
							sysOrgElementService.update(ele);
							models = omsRelationService.findList("fdEkpId='"+ele.getFdId()+"'", null);
							for(OmsRelationModel model:models){
								omsRelationService.delete(model);
							}
						}
					}
				}
			}
			TransactionUtils.commit(status);
		} catch (Exception e) {
			logger.error("处理人员/部门失败:", e);
			if (status != null) {
				try {
					TransactionUtils.rollback(status);
				} catch (Exception ex) {
					logger.error("---事务回滚出错---", ex);
				}
			}
		}
	}
	
	/**
	 * 初始化映射表关系的类型
	 */
	private void updateRelationType(){
		TransactionStatus status = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			List<OmsRelationModel> list = omsRelationService
					.findList("fdAppKey='" + getAppKey() + "' and fdType is null", null);
			for (OmsRelationModel model : list) {
				SysOrgElement ele = (SysOrgElement) sysOrgElementService.findByPrimaryKey(model.getFdEkpId());
				if (ele != null) {
					if (8 == ele.getFdOrgType()) {
						model.setFdType("8");
					} else if (2 == ele.getFdOrgType() || 1 == ele.getFdOrgType()) {
						model.setFdType("2");
					}
					omsRelationService.update(model);
				}
			}
			TransactionUtils.commit(status);
		} catch (Exception e) {
			logger.error("更新映射类型失败:", e);
			if (status != null) {
				try {
					TransactionUtils.rollback(status);
				} catch (Exception ex) {
					logger.error("---事务回滚出错---", ex);
				}
			}
		}
	}
}
