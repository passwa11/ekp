package com.landray.kmss.third.feishu.service.spring;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.feishu.constant.ThirdFeishuConstant;
import com.landray.kmss.third.feishu.interfaces.ApiException;
import com.landray.kmss.third.feishu.interfaces.TokenException;
import com.landray.kmss.third.feishu.model.*;
import com.landray.kmss.third.feishu.service.IThirdFeishuDeptMappingService;
import com.landray.kmss.third.feishu.service.IThirdFeishuPersonMappingService;
import com.landray.kmss.third.feishu.service.IThirdFeishuService;
import com.landray.kmss.third.feishu.util.ThirdFeishuApiUtil;
import com.landray.kmss.third.feishu.util.ThirdFeishuUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ThirdFeishuServiceImp
		implements IThirdFeishuService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdFeishuServiceImp.class);

	IThirdFeishuDeptMappingService thirdFeishuDeptMappingService = null;

	public IThirdFeishuDeptMappingService getThirdFeishuDeptMappingService() {
		return thirdFeishuDeptMappingService;
	}

	public void setThirdFeishuDeptMappingService(
			IThirdFeishuDeptMappingService thirdFeishuDeptMappingService) {
		this.thirdFeishuDeptMappingService = thirdFeishuDeptMappingService;
	}

	IThirdFeishuPersonMappingService thirdFeishuPersonMappingService = null;

	public IThirdFeishuPersonMappingService
			getThirdFeishuPersonMappingService() {
		return thirdFeishuPersonMappingService;
	}

	public void setThirdFeishuPersonMappingService(
			IThirdFeishuPersonMappingService thirdFeishuPersonMappingService) {
		this.thirdFeishuPersonMappingService = thirdFeishuPersonMappingService;
	}

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	private ISysOrgElementService sysOrgElementService;

	private static ThirdFeishuToken appToken;

	private static ThirdFeishuToken tenantToken;

	@Override
	public void resetToken() throws Exception {
		appToken = null;
		tenantToken = null;
	}

	@Override
	public ThirdFeishuToken getToken(int tokenType) throws Exception {
		ThirdFeishuToken token = null;
		if (tokenType == 1) {
			token = appToken;
		} else if (tokenType == 2) {
			token = tenantToken;
		}
		if (token == null || token.isExpired()) {
			token = updateToken(tokenType);
		}
		return token;
	}

	public ThirdFeishuToken updateToken(int tokenType) throws Exception {
		if (tokenType == 1) {
			return updateAppToken();
		} else if (tokenType == 2) {
			return updateTenantToken();
		}
		return null;

	}

	private ThirdFeishuToken updateAppToken() throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/auth/v3/app_access_token/internal/";
		ThirdFeishuConfig config = ThirdFeishuConfig.newInstance();
		JSONObject body = new JSONObject();
		body.put("app_id", config.getFeishuAppid());
		body.put("app_secret", config.getFeishuAppsecret());
		String result = ThirdFeishuApiUtil.execHttpPost(url, body.toString(),
				null);
		JSONObject resultObj = JSONObject.fromObject(result);
		if ("0".equals(resultObj.getString("code"))) {
			String tokenStr = resultObj.getString("app_access_token");
			long expires_in = resultObj.getLong("expire");
			appToken = new ThirdFeishuToken(tokenStr, expires_in, 1);
			return appToken;
		} else {
			throw new TokenException("获取token失败，" + result);
		}
	}

	private ThirdFeishuToken updateTenantToken() throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/auth/v3/tenant_access_token/internal/";
		ThirdFeishuConfig config = ThirdFeishuConfig.newInstance();
		JSONObject body = new JSONObject();
		body.put("app_id", config.getFeishuAppid());
		body.put("app_secret", config.getFeishuAppsecret());
		String result = ThirdFeishuApiUtil.execHttpPost(url, body.toString(),
				null);
		JSONObject resultObj = JSONObject.fromObject(result);
		if ("0".equals(resultObj.getString("code"))) {
			String tokenStr = resultObj.getString("tenant_access_token");
			long expires_in = resultObj.getLong("expire");
			appToken = new ThirdFeishuToken(tokenStr, expires_in, 2);
			return appToken;
		} else {
			throw new TokenException("获取token失败，" + result);
		}
	}

	@Override
	public String getTokenStr(int tokenType) throws Exception {
		return getToken(tokenType).getTokenStr();
	}

	private String getParentId(SysOrgElement ele,
			Map<String, String> deptMapping) throws Exception {
		SysOrgElement parent = ele.getFdParent();
		if (parent == null) {
			return getFeishuRootDeptId();
		}
		if (deptMapping != null) {
			String feishuId = deptMapping.get(parent.getFdId());
			if (StringUtil.isNotNull(feishuId)) {
				return feishuId;
			}
		}
		ThirdFeishuDeptMapping mapping = thirdFeishuDeptMappingService
				.findByEkpId(parent.getFdId());
		if (mapping != null) {
			return mapping.getFdFeishuId();
		}
		return getFeishuRootDeptId();
	}

	private String getValid(SysOrgElement ele) {
		boolean available = ele.getFdIsAvailable();
		if (available) {
			return "1";
		} else {
			return "0";
		}
	}

	private JSONObject buildDeptObj(SysOrgElement dept,
			ThirdFeishuDeptMapping mapping, boolean isAdd,
			Map<String, String> deptMapping) throws Exception {
		JSONObject o = new JSONObject();
		if (isAdd) {
			if (mapping == null) {
				o.put("department_id", dept.getFdId());
			} else {
				o.put("department_id", mapping.getFdFeishuId());
			}
			o.put("parent_department_id", "0");
			o.put("name", dept.getFdName() + dept.getFdId());
		} else {
			o.put("parent_department_id", getParentId(dept, deptMapping));
			SysOrgElement leader = dept.getHbmThisLeader();
			if (leader != null && leader.getFdOrgType() == 8) {
				ThirdFeishuPersonMapping person_mapping = thirdFeishuPersonMappingService
						.findByEkpId(leader.getFdId());
				if (person_mapping != null) {
					o.put("leader_user_id",
							person_mapping.getFdOpenId());
				}
			}
			o.put("name", dept.getFdName());
		}

		return o;
	}

	private Integer sexConvert(String sex) {
		if (StringUtil.isNull(sex)) {
			return null;
		}
		if ("M".equals(sex)) {
			return 1;
		} else if ("F".equals(sex)) {
			return 2;
		}
		return null;
	}

	private JSONObject buildPersonObj(SysOrgPerson person,
			ThirdFeishuPersonMapping mapping,
			Map<String, String> deptMapping) throws Exception {
		Object synVal = null;
		boolean isNew = (mapping == null);
		ThirdFeishuConfig config = ThirdFeishuConfig.newInstance();
		JSONObject o = new JSONObject();
		//是否禁用/删除飞书端用户
		if (isNew) {
			o.put("is_frozen", !person.getFdIsAvailable());
		}
		else if (!person.getFdIsAvailable()) {
			o.put("is_frozen", true);
		}

		//员工UserId
		synVal = ThirdFeishuUtil.getSynValue(config.getOrg2FeishuUseridSynWay(), config.getOrg2FeishuUserid(), person, isNew);
		ThirdFeishuUtil.setSynProperty(o, "user_id", synVal, config.getOrg2FeishuUseridSynWay(), isNew);

		//姓名
		synVal = ThirdFeishuUtil.getSynValue(config.getOrg2FeishuNameSynWay(), config.getOrg2FeishuName(), person, isNew);
		ThirdFeishuUtil.setSynProperty(o, "name", synVal, config.getOrg2FeishuNameSynWay(), isNew);

		//手机
		synVal = ThirdFeishuUtil.getSynValue(config.getOrg2FeishuMobileSynWay(), config.getOrg2FeishuMobile(), person, isNew);
		ThirdFeishuUtil.setSynProperty(o, "mobile", synVal, config.getOrg2FeishuMobileSynWay(), isNew);

		//工号
		synVal = ThirdFeishuUtil.getSynValue(config.getOrg2FeishuJobnumberSynWay(), config.getOrg2FeishuJobnumber(), person, isNew);
		ThirdFeishuUtil.setSynProperty(o, "employee_no", synVal, config.getOrg2FeishuJobnumberSynWay(), isNew);

		//部门
		JSONArray feishuDepartmentIds = null;
		if(person.getFdIsAvailable()==true){
			synVal = feishuDepartmentIds = getDeptartmentIds(person, deptMapping, isNew);
			ThirdFeishuUtil.setSynProperty(o, "department_ids", synVal, config.getOrg2FeishuDepartmentSynWay(), isNew);
		}

		o.put("need_send_notification", true);
		o.put("employee_type", 1);
		//排序号
		setOrders(person, isNew, config, o, feishuDepartmentIds);
		//别名
		synVal = ThirdFeishuUtil.getSynValue(config.getOrg2FeishuAliasSynWay(), config.getOrg2FeishuAlias(), person, isNew);
		ThirdFeishuUtil.setSynProperty(o, "nickname", synVal, config.getOrg2FeishuAliasSynWay(), isNew);
		//英文名
		synVal = ThirdFeishuUtil.getSynValue(config.getOrg2FeishuEnglishNameSynWay(), config.getOrg2FeishuEnglishName(), person, isNew);
		ThirdFeishuUtil.setSynProperty(o, "en_name", synVal, config.getOrg2FeishuEnglishNameSynWay(), isNew);
		//职务
		synVal = ThirdFeishuUtil.getSynValue(config.getOrg2FeishuPositionSynWay(), config.getOrg2FeishuPosition(), person, isNew);
		ThirdFeishuUtil.setSynProperty(o, "job_title", synVal, config.getOrg2FeishuPositionSynWay(), isNew);
		//邮箱
		synVal = ThirdFeishuUtil.getSynValue(config.getOrg2FeishuEmailSynWay(), config.getOrg2FeishuEmailName(), person, isNew);
		ThirdFeishuUtil.setSynProperty(o, "email", synVal, config.getOrg2FeishuEmailSynWay(), isNew);
		//性别
		synVal = ThirdFeishuUtil.getSynValue(config.getOrg2FeishuSexSynWay(), config.getOrg2FeishuSexName(), person, isNew);
		synVal = sexConvert((String) synVal);
		ThirdFeishuUtil.setSynProperty(o, "gender", synVal, config.getOrg2FeishuSexSynWay(), isNew);
		//工位
		synVal = ThirdFeishuUtil.getSynValue(config.getOrg2FeishuWorkPlaceSynWay(), config.getOrg2FeishuWorkPlace(), person, isNew);
		ThirdFeishuUtil.setSynProperty(o, "work_station", synVal, config.getOrg2FeishuWorkPlaceSynWay(), isNew);
		//入职时间
		synVal = ThirdFeishuUtil.getSynValue(config.getOrg2FeishuHiredDateSynWay(), config.getOrg2FeishuHiredDate(), person, isNew);
		ThirdFeishuUtil.setSynProperty(o, "join_time", synVal, config.getOrg2FeishuHiredDateSynWay(), isNew);
		//企业邮箱
		synVal = ThirdFeishuUtil.getSynValue(config.getOrg2FeishuOrgEmailSynWay(), config.getOrg2FeishuOrgEmail(), person, isNew);
		ThirdFeishuUtil.setSynProperty(o, "enterprise_email", synVal, config.getOrg2FeishuOrgEmailSynWay(), isNew);
		//号码隐藏
		setMobileVisible(person, synVal, isNew, config, o);
		if(logger.isDebugEnabled()){
			logger.debug("构建同步人员数据结构:"+o.toString());
		}
		return o;
	}

	/**
	 * 设置排序号同步规则
	 * @param person
	 * @param isNew
	 * @param config
	 * @param o
	 * @param feishuDepartmentIds
	 * @throws Exception
	 */
	private void setOrders(SysOrgPerson person, boolean isNew, ThirdFeishuConfig config, JSONObject o, JSONArray feishuDepartmentIds) throws Exception {
		Object synVal = ThirdFeishuUtil.getSynValue(config.getOrg2FeishuOrderInDeptsSynWay(), "fdOrder", person, isNew);
		if(synVal != null){
			String o2d_order = config.getOrg2FeishuOrderInDepts();
			if (StringUtil.isNotNull(o2d_order)
					&& "desc".equals(o2d_order)) {
				synVal = Integer.MAX_VALUE - ((Integer) synVal);
			}
			JSONArray orderArray = new JSONArray();
			if(feishuDepartmentIds != null){
				for(int index = 0; index < feishuDepartmentIds.size(); index++){
					String feishuDepartmentId = feishuDepartmentIds.getString(index);
					Integer departmentOrder = getThirdFeishuDeptMappingService().findByFeishuId((String) feishuDepartmentId).getFdEkp().getFdOrder();
					JSONObject orderObject = new JSONObject();
					orderObject.put("department_id", feishuDepartmentId);
					orderObject.put("user_order", synVal);
					orderObject.put("department_order", departmentOrder);
					orderArray.add(orderObject);
				}
			}
			o.put("orders", orderArray);
		}
	}

	/**
	 * 设置号码隐藏
	 * @param person
	 * @param synVal
	 * @param isNew
	 * @param config
	 * @param o
	 * @throws Exception
	 */
	private void setMobileVisible(SysOrgPerson person, Object synVal, boolean isNew, ThirdFeishuConfig config, JSONObject o) throws Exception {
		if(ThirdFeishuUtil.isSyn(config.getOrg2FeishuIsHideSynWay())
		   || (isNew && ThirdFeishuUtil.isAddSyn(config.getOrg2FeishuIsHideSynWay()))){
			if("true".equals(config.getOrg2FeishuIsHideAll())){
				o.put("mobile_visible", Boolean.FALSE);
			}
			else{
				// 从字段同步
				if (config.getOrg2FeishuIsHide() != null) {
					if ("isContactPrivate".equals(config.getOrg2FeishuIsHide())) {
						IBaseService sysZonePersonInfoService  = (IBaseService) SpringBeanUtil.getBean("sysZonePersonInfoService");
						Object object = sysZonePersonInfoService.findByPrimaryKey(person.getFdId(),null, true);
						if (object != null) {
							synVal = PropertyUtils.getProperty(object,"getIsContactPrivate");
						}
						if (synVal != null) {
							String hideObject = synVal.toString().trim();
							if ("true".equals(hideObject)
									|| "1".equals(hideObject)
									|| "是".equals(hideObject)) {
								o.put("mobile_visible", Boolean.FALSE);
							}
							else {
								o.put("mobile_visible", Boolean.TRUE);
							}
						} else {
							ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
							Map orgMap = sysAppConfigService.findByKey("com.landray.kmss.sys.zone.model.SysZonePrivateConfig");
							if (orgMap != null
									&& orgMap.containsKey("isContactPrivate")) {
								synVal = orgMap.get("isContactPrivate");
								if (synVal != null && "1".equals(synVal.toString())) {
									o.put("mobile_visible", Boolean.FALSE);
								} else {
									o.put("mobile_visible", Boolean.TRUE);
								}
							} else {
								o.put("mobile_visible", Boolean.TRUE);
							}
						}
					} else {
						// 从字段同步
						synVal = ThirdFeishuUtil.getPersonProperty(config.getOrg2FeishuIsHide(), person);
						if (synVal != null) {
							if ("true".equals(synVal.toString())
									|| "1".equals(synVal.toString())
									|| "是".equals(synVal.toString())) {
								o.put("mobile_visible", Boolean.FALSE);
							} else {
								o.put("mobile_visible", Boolean.TRUE);
							}
						}
						else {
							o.put("mobile_visible", Boolean.TRUE);
						}
					}
				}
			}
		}
	}

	@Override
	public void syncDept(SysOrgElement dept, boolean isAdd,
			Map<String, String> deptMapping)
			throws Exception {
		// System.out.println(dept.getFdId());
		TransactionStatus status = null;
		try {
			status = TransactionUtils
					.beginNewTransaction();
			ThirdFeishuDeptMapping mapping = thirdFeishuDeptMappingService
					.findByEkpId(dept.getFdId());
			if (!dept.getFdIsAvailable()) {
				TransactionUtils.commit(status);
				return;
			}
			if (isAdd && mapping != null) {
				logger.warn("该部门已经同步过，无需新增，ID:" + dept.getFdId() + "，名称:"
						+ dept.getFdName());
				TransactionUtils.commit(status);
				return;
			}
			if (isAdd) {
				logger.debug("新增部门，ID:" + dept.getFdId() + "，名称:"
					+ dept.getFdName());
				addDept(dept, deptMapping);
				// TransactionStatus status = TransactionUtils
				// .beginNewTransaction();
				// thirdFeishuDeptMappingService.addMapping(dept);
				// TransactionUtils.commit(status);
			} else {
				logger.debug("更新部门，ID:" + dept.getFdId() + "，名称:"
						+ dept.getFdName());
				updateDept(dept, mapping, deptMapping);
			}
			TransactionUtils.commit(status);
		} catch (Exception e) {
			TransactionUtils.rollback(status);
			throw e;
		}
	}

	private String getDepartmentIdType(String deptId) {
		if (deptId == null) {
			return null;
		}
		if (deptId.startsWith("od-")) {
			return "open_department_id";
		} else {
			return "department_id";
		}
	}

	@Override
	public void addDept(SysOrgElement dept, Map<String, String> deptMapping)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/contact/v3/departments";

		JSONObject dept_req = buildDeptObj(dept, null, true, deptMapping);
		String parentId = dept_req.getString("parent_department_id");
		String department_id_type = getDepartmentIdType(parentId);
		if (department_id_type != null) {
			url += "?department_id_type=" + department_id_type;
		}
		String tokenStr = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpPost(url,
				dept_req.toString(), tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
			String open_department_id = resultObj.getJSONObject("data")
					.getJSONObject("department")
					.getString("open_department_id");
			thirdFeishuDeptMappingService.addMapping(dept, open_department_id);
		} else {
			throw new ApiException(
					"新增部门出错，请求信息:" + dept_req.toString() + ",结果:" + result);
		}
	}

	@Override
	public void updateDept(SysOrgElement dept, ThirdFeishuDeptMapping mapping,
			Map<String, String> deptMapping)
			throws Exception {
		String department_id = null;
		if (mapping == null) {
			addDept(dept, deptMapping);
			return;
		} else {
			department_id = mapping.getFdFeishuId();
		}
		String url = ThirdFeishuConstant.API_URL
				+ "/contact/v3/departments/" + department_id;
		String department_id_type = getDepartmentIdType(department_id);
		if (department_id_type != null) {
			url += "?department_id_type=" + department_id_type;
		}

		JSONObject dept_req = buildDeptObj(dept, mapping, false, deptMapping);
		String tokenStr = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpPatch(url,
				dept_req.toString(), tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
		} else {
			throw new ApiException(
					"更新部门出错，请求信息:" + dept_req.toString() + ",结果:" + result);
		}
	}

	@Override
	public void delDept(SysOrgElement dept)
			throws Exception {
		TransactionStatus status = null;
		try {
			status = TransactionUtils
					.beginNewTransaction();
			ThirdFeishuDeptMapping mapping = thirdFeishuDeptMappingService
					.findByEkpId(dept.getFdId());
			if (mapping == null) {
				TransactionUtils.commit(status);
				return;
			}
			delDept(mapping);
			// thirdFeishuDeptMappingService.delete(mapping);
			TransactionUtils.commit(status);
		} catch (Exception e) {
			TransactionUtils.rollback(status);
			throw e;
		}
	}

	@Override
	public void delDept(ThirdFeishuDeptMapping mapping)
			throws Exception {
		String department_id = mapping.getFdFeishuId();
		String url = ThirdFeishuConstant.API_URL
				+ "/contact/v3/departments/" + department_id;
		String department_id_type = getDepartmentIdType(department_id);
		if (department_id_type != null) {
			url += "?department_id_type=" + department_id_type;
		}
		String tokenStr = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpDelete(url,
				tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
			thirdFeishuDeptMappingService.delete(mapping);
		} else {
			throw new ApiException(
					"删除部门出错，请求信息:" + url + ",结果:" + result);
		}
	}

	private void handleDisabledPerson(SysOrgPerson person, ThirdFeishuPersonMapping mapping, Map<String, String> deptMapping) throws Exception {
		ThirdFeishuConfig config = ThirdFeishuConfig.newInstance();
		String handleType = config.getSynchroOrg2FeishuDisablePersonHandle();
		if("2".equals(handleType)){
			//删除
			delPerson(mapping);
			thirdFeishuPersonMappingService.delete(mapping);
		}else{
			//禁用
			updatePerson(person, mapping, deptMapping);
		}
	}

	@Override
	public void syncPerson(SysOrgPerson person, Map<String, String> deptMapping)
			throws Exception {
		TransactionStatus status = null;
		try{
			System.out.println("----------"+person.getFdName());
			logger.debug("同步人员，ID:" + person.getFdId() + "，名称:"
					+ person.getFdName());
			status = TransactionUtils
					.beginNewTransaction();
			ThirdFeishuPersonMapping mapping = thirdFeishuPersonMappingService
					.findByEkpId(person.getFdId());
			if (!person.getFdIsAvailable()) {
				if (mapping == null) {
					logger.info("无效人员，且没有同步过，ID:" + person.getFdId() + "，名称:"
							+ person.getFdName());
				} else {
					handleDisabledPerson(person,mapping,deptMapping);
				}
			}else{
				if (mapping != null) {
					updatePerson(person, mapping, deptMapping);
				} else {
					String open_id = addPerson(person, deptMapping);
					if(logger.isDebugEnabled()) {
						logger.debug("新增飞书人员:"+open_id);
					}
				}
			}
			TransactionUtils.commit(status);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			TransactionUtils.rollback(status);
		}
	}

	private String addPerson(SysOrgPerson person,
			Map<String, String> deptMapping)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/contact/v3/users";

		JSONObject user_req = buildPersonObj(person, null, deptMapping);
		if (user_req.containsKey("department_ids")) {
			JSONArray array = user_req.getJSONArray("department_ids");
			if (array != null && array.size() > 0) {
				String department_id = array.getString(0);
				String department_id_type = getDepartmentIdType(department_id);
				if (department_id_type != null) {
					url += "?department_id_type=" + department_id_type;
				}
			}
		}

		String tokenStr = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpPost(url,
				user_req.toString(), tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
			String open_id = resultObj.getJSONObject("data")
					.getJSONObject("user")
					.getString("open_id");
			thirdFeishuPersonMappingService.addMapping(person, open_id);
			return open_id;
		} else {
			throw new ApiException(
					"新增人员出错，请求信息:" + user_req.toString() + ",结果:" + result);
		}
	}

	private void updatePerson(SysOrgPerson person,
			ThirdFeishuPersonMapping mapping, Map<String, String> deptMapping)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/contact/v3/users/" + mapping.getFdOpenId();

		JSONObject user_req = buildPersonObj(person, mapping, deptMapping);
		if (user_req.containsKey("department_ids")) {
			JSONArray array = user_req.getJSONArray("department_ids");
			if (array != null && array.size() > 0) {
				String department_id = array.getString(0);
				String department_id_type = getDepartmentIdType(department_id);
				if (department_id_type != null) {
					url += "?department_id_type=" + department_id_type;
				}
			}
		}
		String tokenStr = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpPatch(url,
				user_req.toString(), tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
			String mobileNoOld = mapping.getFdMobileNo();
			String mobileNoNew = person.getFdMobileNo();
			mobileNoOld = StringUtil.getString(mobileNoOld);
			mobileNoNew = StringUtil.getString(mobileNoNew);
			if (!mobileNoNew.equals(mobileNoOld)) {
				mapping.setFdMobileNo(person.getFdMobileNo());
				thirdFeishuPersonMappingService.update(mapping);
			}
		} else {
			throw new ApiException(
					"更新人员出错，请求信息:" + user_req.toString() + ",结果:" + result);
		}
	}

	@Override
	public void delPerson(ThirdFeishuPersonMapping mapping)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/contact/v3/users/"
				+ mapping.getFdOpenId()
				+ "?department_id_type=open_department_id";
		String tokenStr = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpDelete(url,
				tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
			thirdFeishuPersonMappingService.delete(mapping);
		} else {
			throw new ApiException(
					"删除人员出错，请求信息:" + url + ",结果:" + result);
		}
	}


	@Override
	public JSONArray getDeptsChildren(String department_id, boolean fetch_child)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/contact/v3/departments";
		url += "?page_size=50&fetch_child="+(fetch_child?true:false);
		if (StringUtil.isNotNull(department_id)) {
			url += "&parent_department_id=" + department_id;
		}
		JSONArray depts = new JSONArray();
		getDepts(depts, url, null);
		return depts;
	}

	@Override
	public String syncApproval(String requestBody) throws Exception {
		ThirdFeishuConfig config = new ThirdFeishuConfig();
		String url = config.getFeishuApprovalUrl();
		if(StringUtils.isBlank(url)){
			throw new Exception("请配置飞书审批中心api地址!");
		}
		String accessToken = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpPost(
				url,
				requestBody,
				accessToken);
		if (logger.isDebugEnabled()) {
			logger.debug("飞书审批同步响应：" + result);
		}
		if("success".equalsIgnoreCase(result)){
			return result;
		}
		throw new Exception("请求飞书审批中心接口失败");
	}

	/**
	 * 
	 * @param department_id
	 * @param fetch_child
	 *            false ：查询下级部门信息 true ：查询递归获取所有子部门
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONArray getDepts(String department_id, boolean fetch_child)
			throws Exception {

		String url = ThirdFeishuConstant.API_URL
				+ "/contact/v3/departments";
		url += "?page_size=50&fetch_child=false";
		if (StringUtil.isNotNull(department_id)) {
			url += "&parent_department_id=" + department_id;
		}

		JSONArray depts = new JSONArray();
		getDepts(depts, url, null);
		if (fetch_child) {
			getDeptsRecursion(depts);
		}
		return depts;
	}

	public void getDeptsRecursion(JSONArray depts)
			throws Exception {
		String url_pre = ThirdFeishuConstant.API_URL
				+ "/contact/v3/departments";
		if (depts == null || depts.isEmpty()) {
			return;
		}
		for (int i = 0; i < depts.size(); i++) {
			JSONObject o = depts.getJSONObject(i);
			String department_id = o.getString("open_department_id");
			String url = url_pre + "?page_size=50&fetch_child=false"
					+ "&parent_department_id=" + department_id;
			JSONArray depts_child = new JSONArray();
			getDepts(depts_child, url, null);
			depts.addAll(depts_child);
			getDeptsRecursion(depts_child);
		}

	}

	public void getDepts(JSONArray depts, String urlPre, String page_token)
			throws Exception {
		String tokenStr = getTokenStr(1);
		String url = urlPre;
		if (StringUtil.isNotNull(page_token)) {
			url = url + "&page_token=" + page_token;
		}
		String result = ThirdFeishuApiUtil.execHttpGet(
				url,
				tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
			JSONObject data = resultObj.getJSONObject("data");
			boolean has_more = data.getBoolean("has_more");
			if (data.containsKey("items")) {
				JSONArray datas = data.getJSONArray("items");
				depts.addAll(datas);
			}
			if (has_more) {
				String page_token_return = data.getString("page_token");
				getDepts(depts, urlPre, page_token_return);
			}
		} else {
			throw new ApiException(
					"获取部门列表出错，请求信息:" + url + ",结果:" + result);
		}
	}

	@Override
	public JSONArray getUsers(String department_id, boolean fetch_child)
			throws Exception {
		JSONArray users = new JSONArray();
		String url = ThirdFeishuConstant.API_URL
				+ "/contact/v3/users";
		String getUserUrl = url + "?page_size=50";
		if (StringUtil.isNotNull(department_id)) {
			getUserUrl += "&department_id=" + department_id;
		}
		getUsers(users, getUserUrl, null);
		if (fetch_child) {
			JSONArray depts = getDepts(department_id, true);
			for (int i = 0; i < depts.size(); i++) {
				String deptId = depts.getJSONObject(i)
						.getString("open_department_id");
				getUserUrl = url + "?page_size=50&fetch_child=false"
						+ "&department_id=" + deptId;
				getUsers(users, getUserUrl, null);
			}
		}
		return users;
	}

	public void getUsers(JSONArray users, String urlPre, String page_token)
			throws Exception {
		String tokenStr = getTokenStr(1);
		String url = urlPre;
		if (StringUtil.isNotNull(page_token)) {
			url = url + "&page_token=" + page_token;
		}
		String result = ThirdFeishuApiUtil.execHttpGet(
				url,
				tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
			JSONObject data = resultObj.getJSONObject("data");
			boolean has_more = data.getBoolean("has_more");
			if (data.containsKey("items")) {
				JSONArray datas = data.getJSONArray("items");
				users.addAll(datas);
			}
			if (has_more) {
				String page_token_return = data.getString("page_token");
				getUsers(users, urlPre, page_token_return);
			}
		} else {
			throw new ApiException(
					"获取人员列表出错，请求信息:" + url + ",结果:" + result);
		}
	}


	@Override
	public JSONObject getUser(String employee_id)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/contact/v3/user/" + employee_id;

		String tokenStr = getTokenStr(1);

		String result = ThirdFeishuApiUtil.execHttpGet(
				url,
				tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
			return resultObj;
		} else {
			throw new ApiException(
					"获取人员出错，请求信息:" + url + ",结果:" + result);
		}
	}

	public JSONArray getUsersDeprecated(List<String> list)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/contact/v3/users";
		for (int i = 0; i < list.size(); i++) {
			url += "&employee_ids=" + list.get(i);
		}
		url = url.replaceFirst("&", "?");
		String tokenStr = getTokenStr(1);

		String result = ThirdFeishuApiUtil.execHttpGet(
				url,
				tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
			return resultObj.getJSONObject("data").getJSONArray("user_infos");
		} else {
			throw new ApiException(
					"获取人员出错，请求信息:" + url + ",结果:" + result);
		}
	}



	@Override
	public String getSsoOpenId(String code)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/authen/v1/access_token";

		JSONObject obj = new JSONObject();
		obj.put("app_access_token", getTokenStr(1));
		obj.put("grant_type", "authorization_code");
		obj.put("code", code);
		String result = ThirdFeishuApiUtil.execHttpPost(
				url,
				obj.toString(),
				null);
		JSONObject resultObj = JSONObject.fromObject(result);
		String result_code = resultObj.getString("code");
		if ("0".equals(result_code)) {
			return resultObj.getJSONObject("data").getString("open_id");
		} else {
			throw new ApiException(
					"根据code获取open_id出错，请求信息:" + url + ",结果:" + result);
		}
	}

	@Override
	public String getSsoUserId(String code)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/authen/v1/access_token";

		JSONObject obj = new JSONObject();
		obj.put("app_access_token", getTokenStr(1));
		obj.put("grant_type", "authorization_code");
		obj.put("code", code);
		String result = ThirdFeishuApiUtil.execHttpPost(
				url,
				obj.toString(),
				null);
		JSONObject resultObj = JSONObject.fromObject(result);
		String result_code = resultObj.getString("code");
		if ("0".equals(result_code)) {
			return resultObj.getJSONObject("data").getString("user_id");
		} else {
			throw new ApiException(
					"根据code获取user_id出错，请求信息:" + url + ",结果:" + result);
		}
	}

	public void sendMessage(String subject, String content, String urlPath,
			String owner, String employeeId, ThirdFeishuNotifyLog log)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/message/v4/send/";
		log.setFdUrl(url);
		String accessToken = getTokenStr(2);
		JSONObject msg = new JSONObject();
		msg.put("msg_type", "post");
		msg.put("user_id", employeeId);
		JSONObject contentObj = new JSONObject();
		JSONObject postObj = new JSONObject();
		JSONObject zhcnObj = new JSONObject();
		zhcnObj.put("title", subject);
		JSONArray contentArray = new JSONArray();
		JSONObject content_inner = new JSONObject();
		content_inner.put("tag", "a");
		content_inner.put("text", content);
		content_inner.put("href", urlPath);
		contentArray.add(content_inner);
		zhcnObj.put("content", contentArray);
		postObj.put("zh_cn", zhcnObj);
		contentObj.put("post", postObj);
		msg.put("content", contentObj);
		log.setFdReqData(msg.toString());

		String result = ThirdFeishuApiUtil.execHttpPost(
				url,
				msg.toString(),
				accessToken);
		log.setFdResData(result);

		JSONObject resultObj = JSONObject.fromObject(result);

		String result_code = resultObj.getString("code");
		if ("0".equals(result_code)) {
			return;
		} else {
			throw new ApiException(
					"发送消息异常，请求地址:" + url + ",请求内容:" + msg.toString() + ",结果:"
							+ result);
		}
	}

	@Override
	public void delPerson(String employee_id)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/contact/v3/users/" + employee_id;
		String tokenStr = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpDelete(url,
				tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
		} else {
			throw new ApiException(
					"删除人员出错，请求信息:" + url + ",结果:" + result);
		}
	}

	@Override
	public void delDept(String feishDdeptId)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/contact/v3/departments/" + feishDdeptId;
		String tokenStr = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpDelete(url,
				tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
		} else {
			throw new ApiException(
					"删除部门出错，请求信息:" + url + ",结果:" + result);
		}
	}

	public JSONObject buildContentObj(String subject, String docSubject,
			String urlPath, String modelname, String creator, String createTime,
			Locale locale) {
		JSONArray content_inner = new JSONArray();

		JSONArray row1 = new JSONArray();
		JSONObject row1_title = new JSONObject();
		row1_title.put("tag", "text");
		row1_title.put("un_escape", true);
		row1_title.put("text", ResourceUtil.getStringValue("msg.title",
				"third-feishu", locale));
		JSONObject row1_content = new JSONObject();
		row1_content.put("tag", "a");
		row1_content.put("href", urlPath);
		row1_content.put("text", docSubject);
		row1.add(row1_title);
		row1.add(row1_content);
		content_inner.add(row1);

		if (StringUtil.isNotNull(modelname)) {
			JSONArray row2 = new JSONArray();
			JSONObject row2_title = new JSONObject();
			row2_title.put("tag", "text");
			row2_title.put("un_escape", true);
			row2_title.put("text", ResourceUtil.getStringValue("msg.modelname",
					"third-feishu", locale));
			JSONObject row2_content = new JSONObject();
			row2_content.put("tag", "text");
			row2_content.put("un_escape", true);
			row2_content.put("text", modelname);
			row2.add(row2_title);
			row2.add(row2_content);
			content_inner.add(row2);
		}

		if (StringUtil.isNotNull(creator)) {
			JSONArray row3 = new JSONArray();
			JSONObject row3_title = new JSONObject();
			row3_title.put("tag", "text");
			row3_title.put("un_escape", true);
			row3_title.put("text", ResourceUtil.getStringValue("msg.creator",
					"third-feishu", locale));
			JSONObject row3_content = new JSONObject();
			row3_content.put("tag", "text");
			row3_content.put("un_escape", true);
			row3_content.put("text", creator);
			row3.add(row3_title);
			row3.add(row3_content);
			content_inner.add(row3);
		}

		if (StringUtil.isNotNull(createTime)) {
			JSONArray row4 = new JSONArray();
			JSONObject row4_title = new JSONObject();
			row4_title.put("tag", "text");
			row4_title.put("un_escape", true);
			row4_title.put("text", ResourceUtil.getStringValue("msg.createTime",
					"third-feishu", locale));
			JSONObject row4_content = new JSONObject();
			row4_content.put("tag", "text");
			row4_content.put("un_escape", true);
			row4_content.put("text", createTime);
			row4.add(row4_title);
			row4.add(row4_content);
			content_inner.add(row4);
		}

		JSONObject contentObj = new JSONObject();
		JSONObject postObj = new JSONObject();
		JSONObject zh_cn = new JSONObject();

		zh_cn.put("content", content_inner);
		zh_cn.put("title", subject);
		postObj.put("zh-cn", zh_cn);

		contentObj.put("post", postObj);

		return contentObj;
	}

	@Override
	public void sendMessage(String subject, String docSubject, String content,
							String urlPath,
							String modelname, String creator, String createTime,
							JSONArray toUserList, Locale locale, ThirdFeishuNotifyLog log)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/message/v4/batch_send/";
		log.setFdUrl(url);
		JSONObject msg = new JSONObject();
		msg.put("msg_type", "post");
		msg.put("user_ids", toUserList);
		msg.put("content",
				buildContentObj(subject, docSubject, urlPath, modelname,
				creator, createTime, locale));

		log.setFdReqData(msg.toString());

		String accessToken = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpPost(
				url,
				msg.toString(),
				accessToken);
		log.setFdResData(result);

		JSONObject resultObj = JSONObject.fromObject(result);
		try {
			log.setFdMessageId(resultObj.getJSONObject("data")
					.getString("message_id"));
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		String result_code = resultObj.getString("code");
		if ("0".equals(result_code)) {
			return;
		} else {
			throw new ApiException(
					"发送消息异常，请求地址:" + url + ",请求内容:" + msg.toString() + ",结果:"
							+ result);
		}
	}

	@Override
	public void sendMessage(JSONObject msg, ThirdFeishuNotifyLog log)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/message/v4/batch_send/";
		log.setFdUrl(url);
		log.setFdReqData(msg.toString());

		String accessToken = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpPost(
				url,
				msg.toString(),
				accessToken);
		log.setFdResData(result);

		JSONObject resultObj = JSONObject.fromObject(result);

		String result_code = resultObj.getString("code");
		try {
			log.setFdMessageId(resultObj.getJSONObject("data")
					.getString("message_id"));
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		if ("0".equals(result_code)) {
			return;
		} else {
			throw new ApiException(
					"发送消息异常，请求地址:" + url + ",请求内容:" + msg.toString() + ",结果:"
							+ result);
		}
	}

	@Deprecated
	private void getUsersDetailBatchDeprecated(String tokenStr,
			JSONArray open_ids,
			JSONArray users) throws Exception {
		String open_idsStr = "";
		for (int i = 0; i < open_ids.size(); i++) {
			open_idsStr += "&open_ids=" + open_ids.getString(i);
		}
		String url = ThirdFeishuConstant.API_URL
				+ "/contact/v1/user/batch_get"
				+ open_idsStr.replaceFirst("&", "?");

		String result = ThirdFeishuApiUtil.execHttpGet(
				url,
				tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
			JSONObject data = resultObj.getJSONObject("data");
			if (data.containsKey("user_infos")) {
				users.addAll(data.getJSONArray("user_infos"));
			}
		}
	}

	@Override
	public JSONArray getUserDetails(String department_id, boolean fetch_child)
			throws Exception {
		JSONArray users = new JSONArray();
		String url = ThirdFeishuConstant.API_URL
				+ "/contact/v3/users";
		String getUserUrl = url + "?page_size=50&fetch_child=false"
				+ "&department_id_type=open_department_id&department_id="
				+ department_id;
		// getTopUsers(users);
		getUsers(users, getUserUrl, null);
		if (fetch_child) {
			// String getUserUrl = url + "?page_size=100&fetch_child=false"
			// + "&department_id=0";
			// getUsers(users, getUserUrl, null);
			// users.addAll(getUserDetailsTop());

			JSONArray depts = getDeptsChildren(department_id, true);
			logger.debug("depts:" + depts);
			for (int i = 0; i < depts.size(); i++) {
				String deptId = depts.getJSONObject(i)
						.getString("open_department_id");
				getUserUrl = url + "?page_size=50&fetch_child=false"
						+ "&department_id_type=open_department_id&department_id="
						+ deptId;
				getUsers(users, getUserUrl, null);
			}
		}
		logger.debug("users:" + users);
		return users;
	}

	// public JSONArray getUserDetailsTop()
	// throws Exception {
	// JSONArray users = new JSONArray();
	// String url = ThirdFeishuConstant.API_URL
	// + "/contact/v1/scope/get";
	// String tokenStr = getTokenStr(1);
	// String result = ThirdFeishuApiUtil.execHttpGet(
	// url,
	// tokenStr);
	//
	// JSONObject resultObj = JSONObject.fromObject(result);
	//
	// String result_code = resultObj.getString("code");
	// if ("0".equals(result_code)) {
	//
	// JSONObject data = resultObj.getJSONObject("data");
	// if (data.containsKey("authed_employee_ids")) {
	// JSONArray employeeIds = data
	// .getJSONArray("authed_employee_ids");
	// if (employeeIds == null || employeeIds.isEmpty()) {
	// return users;
	// }
	// int pageSize = employeeIds.size() / 100;
	// if (employeeIds.size() % 100 != 0) {
	// pageSize++;
	// }
	// for (int i = 0; i < pageSize - 1; i++) {
	// List<String> list = employeeIds.subList(i * 100,
	// (i + 1) * 100);
	// users.addAll(getUsers(list));
	// }
	// List<String> list = employeeIds.subList((pageSize - 1) * 100,
	// employeeIds.size());
	// users.addAll(getUsers(list));
	// }
	// } else {
	// throw new ApiException(
	// "获取根部门下的用户失败，请求地址:" + url + ",结果:"
	// + result);
	// }
	// return users;
	// }

	@Override
	public void updatePersonDepartment(String employee_id, String departmentId)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/contact/v3/users/" + employee_id
				+ "?department_id_type=open_department_id";

		JSONObject user_req = new JSONObject();
		JSONArray array = new JSONArray();
		array.add(departmentId);
		user_req.put("department_ids", array);
		String tokenStr = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpPatch(url,
				user_req.toString(), tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
		} else {
			throw new ApiException(
					"更新人员所属部门出错，请求信息:" + user_req.toString() + ",结果:" + result);
		}
	}

	@Override
	public void updateCardMessage(String message_id, String content,
								  int notifyType,
								  Locale locale,
								  ThirdFeishuNotifyLog log)
			throws Exception{
		updateCardMessageV1(message_id,content,notifyType,locale,log,null);
	}

	@Override
	public void updateCardMessageV1(String message_id, String content,
									int notifyType,
									Locale locale,
									ThirdFeishuNotifyLog log, String status)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/im/v1/messages/" + message_id;
		log.setFdUrl(url);

		JSONObject msg = new JSONObject();
		JSONObject contentObj = JSONObject.fromObject(content);
		JSONArray elements = contentObj.getJSONArray("elements");
		JSONObject element = elements.getJSONObject(elements.size() - 1);
		if(StringUtil.isNull(status)){
			status = getTodoStatus(locale, notifyType);
		}
		element.getJSONObject("text").put("content",status);
		contentObj.put("elements", elements);
		msg.put("content", contentObj.toString());

		log.setFdReqData(msg.toString());

		String accessToken = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpPatch(
				url,
				msg.toString(),
				accessToken);
		log.setFdResData(result);

		JSONObject resultObj = JSONObject.fromObject(result);
		try {
			log.setFdMessageId(message_id);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		String result_code = resultObj.getString("code");
		if ("0".equals(result_code)) {
			return;
		} else {
			throw new ApiException(
					"发送消息异常，请求地址:" + url + ",请求内容:" + msg.toString() + ",结果:"
							+ result);
		}
	}

	@Override
	public void disablePerson(ThirdFeishuPersonMapping mapping) throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/contact/v3/users/" + mapping.getFdOpenId();
		JSONObject o = new JSONObject();
		o.put("user_id", mapping.getFdOpenId());
		o.put("is_frozen", true);
		String tokenStr = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpPatch(url,
				o.toString(), tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {

		} else {
			throw new ApiException(
					"禁用人员出错，请求信息:" + o.toString() + ",结果:" + result);
		}
	}

	private String getTodoStatus(Locale locale, int todoType) {
		String type = "todo";
		switch (todoType){
			case 2:
				type = "toread";
				break;
			case 3:
				type = "suspend";
				break;
		}
		return ResourceUtil.getStringValue("msg.status",
				"third-feishu", locale)
				+ ResourceUtil.getStringValue("msg.status." + type,
						"third-feishu", locale);
	}

	public JSONObject buildCardObj(String subject, String docSubject,
			String urlPath, String modelname, String creator, String createTime,
			int todoType,
			Locale locale) {
		JSONArray elements = new JSONArray();
		JSONObject element1 = new JSONObject();
		element1.put("tag", "div");
		JSONObject text1 = new JSONObject();
		text1.put("tag", "plain_text");
		text1.put("content", ResourceUtil.getStringValue("msg.title",
				"third-feishu", locale) + docSubject);
		element1.put("text", text1);
		elements.add(element1);

		if (StringUtil.isNotNull(modelname)) {
			JSONObject element2 = new JSONObject();
			element2.put("tag", "div");
			JSONObject text2 = new JSONObject();
			text2.put("tag", "plain_text");
			text2.put("content", ResourceUtil.getStringValue("msg.modelname",
					"third-feishu", locale) + modelname);
			element2.put("text", text2);
			elements.add(element2);
		}

		if (StringUtil.isNotNull(creator)) {
			JSONObject element2 = new JSONObject();
			element2.put("tag", "div");
			JSONObject text2 = new JSONObject();
			text2.put("tag", "plain_text");
			text2.put("content", ResourceUtil.getStringValue("msg.creator",
					"third-feishu", locale) + creator);
			element2.put("text", text2);
			elements.add(element2);
		}

		if (StringUtil.isNotNull(createTime)) {
			JSONObject element2 = new JSONObject();
			element2.put("tag", "div");
			JSONObject text2 = new JSONObject();
			text2.put("tag", "plain_text");
			text2.put("content", ResourceUtil.getStringValue("msg.createTime",
					"third-feishu", locale) + createTime);
			element2.put("text", text2);
			elements.add(element2);
		}

		JSONObject element2 = new JSONObject();
		element2.put("tag", "div");
		JSONObject text2 = new JSONObject();
		text2.put("tag", "plain_text");
		text2.put("content",
				getTodoStatus(locale, todoType));
		element2.put("text", text2);
		elements.add(element2);

		JSONObject cardObj = new JSONObject();
		cardObj.put("elements", elements);

		JSONObject config = new JSONObject();
		config.put("wide_screen_mode", true);
		cardObj.put("config", config);
		JSONObject header = new JSONObject();
		JSONObject header_title = new JSONObject();
		header_title.put("tag", "plain_text");
		header_title.put("content", subject);
		header.put("title", header_title);
		cardObj.put("header", header);

		JSONObject card_link = new JSONObject();
		card_link.put("url", urlPath);
		cardObj.put("card_link", card_link);

		return cardObj;
	}

	@Override
	public void sendCardMessage(String subject, String docSubject,
								String urlPath,
								String modelname, String creator, String createTime,
								String toUser, Locale locale, int todoType,
								ThirdFeishuNotifyLog log)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/message/v4/send/";
		log.setFdUrl(url);
		JSONObject msg = new JSONObject();
		msg.put("msg_type", "interactive");
		msg.put("user_id", toUser);
		msg.put("card",
				buildCardObj(subject, docSubject, urlPath, modelname,
						creator, createTime, todoType, locale));

		log.setFdReqData(msg.toString());

		String accessToken = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpPost(
				url,
				msg.toString(),
				accessToken);
		log.setFdResData(result);

		JSONObject resultObj = JSONObject.fromObject(result);
		try {
			log.setFdMessageId(resultObj.getJSONObject("data")
					.getString("message_id"));
		} catch (Exception e) {
			logger.info(resultObj.toString(), e);
		}
		String result_code = resultObj.getString("code");
		if ("0".equals(result_code)) {
			return;
		} else {
			throw new ApiException(
					"发送消息异常，请求地址:" + url + ",请求内容:" + msg.toString() + ",结果:"
							+ result);
		}
	}

	@Override
	public void sendCardMessage(JSONObject msg, ThirdFeishuNotifyLog log)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/message/v4/send/";
		log.setFdUrl(url);
		log.setFdReqData(msg.toString());

		String accessToken = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpPost(
				url,
				msg.toString(),
				accessToken);
		log.setFdResData(result);

		JSONObject resultObj = JSONObject.fromObject(result);

		String result_code = resultObj.getString("code");
		try {
			log.setFdMessageId(resultObj.getJSONObject("data")
					.getString("message_id"));
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		if ("0".equals(result_code)) {
			return;
		} else {
			throw new ApiException(
					"发送消息异常，请求地址:" + url + ",请求内容:" + msg.toString() + ",结果:"
							+ result);
		}
	}

	@Override
	public void updateCardMessage(String url, JSONObject msg,
								  ThirdFeishuNotifyLog log)
			throws Exception {
		log.setFdUrl(url);
		log.setFdReqData(msg.toString());
		String accessToken = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpPatch(
				url,
				msg.toString(),
				accessToken);
		log.setFdResData(result);

		JSONObject resultObj = JSONObject.fromObject(result);
		try {
			log.setFdMessageId(url.substring(url.lastIndexOf("/")));
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		String result_code = resultObj.getString("code");
		if ("0".equals(result_code)) {
			return;
		} else {
			throw new ApiException(
					"发送消息异常，请求地址:" + url + ",请求内容:" + msg.toString() + ",结果:"
							+ result);
		}
	}

	private static String jsapiTicket = null;
	private static Long jsapiTicketExpiresTime = null;

	private boolean isJsapiTicketExpired() {
		if (jsapiTicketExpiresTime == null) {
			return true;
		}
		return System.currentTimeMillis() > jsapiTicketExpiresTime;
	}

	private void expireJsapiTicket() {
		jsapiTicketExpiresTime = 0L;
	}

	public synchronized void updateJsapiTicket(String jsapiTicket,
			int expiresInSeconds) {
		ThirdFeishuServiceImp.jsapiTicket = jsapiTicket;
		jsapiTicketExpiresTime = System.currentTimeMillis()
				+ (expiresInSeconds - 200) * 1000L;
	}

	public String getJsapiTicket() throws Exception {
		return getJsapiTicket(false);
	}

	protected final Object globalJsapiTicketRefreshLock = new Object();

	private JSONObject doGetJsapiTicket() throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/jssdk/ticket/get";
		String tokenStr = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpGet(url,
				tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
			return resultObj.getJSONObject("data");
		} else {
			throw new ApiException(
					"获取JsApiTicket出错，请求信息:" + url + ",结果:" + result);
		}
	}

	@Override
	public String getJsapiTicket(boolean forceRefresh) throws Exception {
		if (forceRefresh) {
			expireJsapiTicket();
		}
		if (isJsapiTicketExpired()) {
			synchronized (globalJsapiTicketRefreshLock) {
				if (isJsapiTicketExpired()) {
					JSONObject data = doGetJsapiTicket();
					jsapiTicket = data.getString("ticket");
					updateJsapiTicket(jsapiTicket,
							data.getInt("expire_in"));
				}
			}
		}
		return jsapiTicket;
	}

	public String sign(String ticket, String nonceStr, long timeStamp,
			String url) throws Exception {
		String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr
				+ "&timestamp=" + String.valueOf(timeStamp) + "&url=" + url;
		try {
			logger.debug("plain:" + plain);
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			sha1.reset();
			sha1.update(plain.getBytes("UTF-8"));
			return bytesToHex(sha1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new Exception(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new Exception(e.getMessage());
		}
	}

	private String bytesToHex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	@Override
	public String getConfig(String urlString, String queryString) {
		String url = null;
		try {
			if (queryString != null) {
				String _refererce = StringUtil.getParameter(queryString,
						"_referer");
				if (StringUtil.isNotNull(_refererce)) {
					queryString = queryString.replace(_refererce,
							URLDecoder.decode(_refererce, "UTF-8"));
				}
				url = URLDecoder.decode(urlString, "UTF-8") + "?"
						+ URLDecoder.decode(queryString, "UTF-8");
			} else {
				url = URLDecoder.decode(urlString, "UTF-8");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		String nonceStr = "abcdefg";
		long timeStamp = System.currentTimeMillis() / 1000;
		String signedUrl = url;
		String ticket = null;
		String signature = null;
		try {
			ticket = getJsapiTicket();
			signature = sign(ticket, nonceStr, timeStamp, signedUrl);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		JSONObject result = new JSONObject();

		try {
			ThirdFeishuConfig config = new ThirdFeishuConfig();
			result.put("appId", config.getFeishuAppid());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		result.put("nonceStr", nonceStr);
		result.put("signature", signature);
		result.put("timeStamp", timeStamp);
		result.put("url", signedUrl);
		logger.info(result.toString());
		return result.toString();
	}

	@Override
	public void sendCardMessageV1(String subject, String docSubject,
								  String urlPath,
								  String modelname, String creator, String createTime,
								  String toUser, Locale locale, int todoType,
								  ThirdFeishuNotifyLog log)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/im/v1/messages?receive_id_type=user_id";
		log.setFdUrl(url);
		JSONObject msg = new JSONObject();
		msg.put("msg_type", "interactive");
		msg.put("receive_id", toUser);
		msg.put("content",
				buildCardObj(subject, docSubject, urlPath, modelname,
						creator, createTime, todoType, locale).toString());

		log.setFdReqData(msg.toString());

		String accessToken = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpPost(
				url,
				msg.toString(),
				accessToken);
		log.setFdResData(result);

		JSONObject resultObj = JSONObject.fromObject(result);
		try {
			log.setFdMessageId(resultObj.getJSONObject("data")
					.getString("message_id"));
		} catch (Exception e) {
			logger.info(resultObj.toString(), e);
		}
		String result_code = resultObj.getString("code");
		if ("0".equals(result_code)) {
			return;
		} else {
			throw new ApiException(
					"发送消息异常，请求地址:" + url + ",请求内容:" + msg.toString() + ",结果:"
							+ result);
		}
	}

	@Override
	public void sendCardMessageV1(JSONObject msg, ThirdFeishuNotifyLog log)
			throws Exception {
		String url = ThirdFeishuConstant.API_URL
				+ "/im/v1/messages?receive_id_type=user_id";
		log.setFdUrl(url);
		log.setFdReqData(msg.toString());

		String accessToken = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpPost(
				url,
				msg.toString(),
				accessToken);
		log.setFdResData(result);

		JSONObject resultObj = JSONObject.fromObject(result);

		String result_code = resultObj.getString("code");
		try {
			log.setFdMessageId(resultObj.getJSONObject("data")
					.getString("message_id"));
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		if ("0".equals(result_code)) {
			return;
		} else {
			throw new ApiException(
					"发送消息异常，请求地址:" + url + ",请求内容:" + msg.toString() + ",结果:"
							+ result);
		}
	}

	@Override
	public void updateCardMessageV1(String url, JSONObject msg,
									ThirdFeishuNotifyLog log)
			throws Exception {
		log.setFdUrl(url);
		log.setFdReqData(msg.toString());
		String accessToken = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpPatch(
				url,
				msg.toString(),
				accessToken);
		log.setFdResData(result);

		JSONObject resultObj = JSONObject.fromObject(result);
		try {
			log.setFdMessageId(url.substring(url.lastIndexOf("/")));
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		String result_code = resultObj.getString("code");
		if ("0".equals(result_code)) {
			return;
		} else {
			throw new ApiException(
					"发送消息异常，请求地址:" + url + ",请求内容:" + msg.toString() + ",结果:"
							+ result);
		}
	}


	private JSONArray getDeptartmentIds(SysOrgPerson person,
										Map<String, String> deptMapping,
										boolean isNew) throws Exception {
		ThirdFeishuConfig config = new ThirdFeishuConfig();
		if(ThirdFeishuUtil.isSyn(config.getOrg2FeishuDepartmentSynWay())
				|| (isNew && ThirdFeishuUtil.isAddSyn(config.getOrg2FeishuDepartmentSynWay()))){
			Set<String> parentIdSet = new HashSet<>();
			JSONArray parentIdArray = new JSONArray();
			String parentId = getParentId(person, deptMapping);
			if(StringUtil.isNotNull(parentId) && !"0".equals(parentId)) {
				parentIdSet.add(parentId);
			}
			if("fdDept".equals(config.getOrg2FeishuDepartment())){
				parentIdArray.addAll(parentIdSet);
			}
			else if("fdMuilDept".equals(config.getOrg2FeishuDepartment())){
				List<SysOrgPost> posts = person.getFdPosts();
				if (posts != null && !posts.isEmpty()) {
					for (SysOrgPost post : posts) {
						String feishuDeptId = getParentId(post, deptMapping);
						if (StringUtil.isNotNull(feishuDeptId)
								&& !"0".equals(feishuDeptId)) {
							parentIdSet.add(feishuDeptId);
						}
					}
				}
				parentIdArray.addAll(parentIdSet);
			}
			return parentIdArray;
		}
		return null;
	}

	private String getFeishuRootDeptOpenId(){
		ThirdFeishuConfig config = ThirdFeishuConfig.newInstance();
		String feishuRootOpenId = config.getSynchroOrg2FeishuFeishuRootOpenId();
		if(StringUtil.isNull(feishuRootOpenId)){
			String feishuRootId = config.getSynchroOrg2FeishuFeishuRootId();
			try {
				JSONObject department = getDept(feishuRootId.trim(), "department_id");
				if(department!=null){
					String open_department_id = department.getString("open_department_id");
					config.setSynchroOrg2FeishuFeishuRootOpenId(open_department_id);
					config.save();
					return open_department_id;
				}
			}catch (Exception e){
				logger.error(e.getMessage(),e);
			}
		}else{
			return feishuRootOpenId;
		}
		return "0";
	}

	private String getFeishuRootDeptId() {
		String feishuRootId = ThirdFeishuConfig.newInstance().getSynchroOrg2FeishuFeishuRootId();
		return StringUtil.isNull(feishuRootId) ? "0"
				: getFeishuRootDeptOpenId();
	}

	/**
	 * 获取需要同步的顶级组织，包括机构的下级机构
	 * 如果没有设置同步范围，则返回null
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String,String> getAllSyncRootIdsMap() throws Exception {
		String rootId = ThirdFeishuConfig.newInstance().getSynchroOrg2FeishuEkpRootId();
		if(StringUtil.isNull(rootId)) {
			return null;
		}
		HashMap syncRootIdsMap = new HashMap<>();
		Set<String> ekpIdSet = new HashSet<>(Arrays.asList(rootId.split(";")));
		for(String fdId:ekpIdSet){
			SysOrgElement element = (SysOrgElement)sysOrgElementService.findByPrimaryKey(fdId);
			if(element!=null){
				syncRootIdsMap.put(element.getFdId(),element.getFdHierarchyId());
			}
		}
		List allOrgChildren = sysOrgElementService.findList("fdOrgType=1 and fdIsAvailable=1", null);
		for (int i = 0; i < allOrgChildren.size(); i++) {
			SysOrgElement org = (SysOrgElement) allOrgChildren.get(i);
			SysOrgElement parent = org.getFdParent();
			while (parent != null) {
				if (ekpIdSet.contains(parent.getFdId())) {
					syncRootIdsMap.put(org.getFdId(),org.getFdHierarchyId());
					break;
				}
				parent = parent.getFdParent();
			}
		}
		return syncRootIdsMap;
	}

	@Override
	public JSONObject getDept(String department_id, String department_id_type)
			throws Exception {
		if(StringUtil.isNull(department_id_type)){
			department_id_type = "open_department_id";
		}
		String url = ThirdFeishuConstant.API_URL
				+ "/contact/v3/departments/"+department_id+"?department_id_type="+department_id_type;
		String tokenStr = getTokenStr(1);
		String result = ThirdFeishuApiUtil.execHttpGet(
				url,
				tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
			JSONObject data = resultObj.getJSONObject("data");
			if (data.containsKey("department")) {
				return data.getJSONObject("department");
			}else{
				throw new ApiException(
						"获取部门信息出错，请求信息:" + url + ",结果:" + result);
			}
		} else {
			throw new ApiException(
					"获取部门信息出错，请求信息:" + url + ",结果:" + result);
		}
	}

	public String getTokenByCode(String code)
			throws Exception {
		ThirdFeishuConfig config = ThirdFeishuConfig.newInstance();
		String redirect_uri = ResourceUtil.getKmssConfigString("kmss.urlPrefix") + "/third/feishu/ssoRedirect.jsp";
		String url = "https://passport.feishu.cn/suite/passport/oauth/token?grant_type=authorization_code&client_id="
				+config.getFeishuAppid()+"&client_secret="+config.getFeishuAppsecret()+"&redirect_uri="+redirect_uri
				+"&code="+code;
		JSONObject reqObj = new JSONObject();
		reqObj.put("grant_type", "authorization_code");
		reqObj.put("client_id", config.getFeishuAppid());
		reqObj.put("client_secret",config.getFeishuAppsecret());
		reqObj.put("code",code);
		reqObj.put("redirect_uri",redirect_uri);
		String result = ThirdFeishuApiUtil.execHttpPost(
				url,
				reqObj.toString(),
				null);
		JSONObject resultObj = JSONObject.fromObject(result);
		logger.warn(resultObj.toString());
		if(resultObj.containsKey("access_token")){
			return resultObj.getString("access_token");
		} else {
			throw new ApiException(
					"根据code获取token出错，请求信息:" + url +",reqObj:"+reqObj.toString()+ ",结果:" + result);
		}
	}

	@Override
	public String getPcScanUserId(String code)
			throws Exception {
		String url = "https://passport.feishu.cn/suite/passport/oauth/userinfo";
		String result = ThirdFeishuApiUtil.execHttpGet(
				url,
				getTokenByCode(code));
		JSONObject resultObj = JSONObject.fromObject(result);
		if (resultObj.containsKey("user_id")) {
			return resultObj.getString("user_id");
		} else {
			throw new ApiException(
					"根据扫码登录用户出错，请求信息:" + url + ",结果:" + result);
		}
	}
}
