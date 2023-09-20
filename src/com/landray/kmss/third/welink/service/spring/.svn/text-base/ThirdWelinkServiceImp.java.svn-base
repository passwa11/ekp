package com.landray.kmss.third.welink.service.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.welink.constant.ThirdWelinkConstant;
import com.landray.kmss.third.welink.interfaces.ApiException;
import com.landray.kmss.third.welink.interfaces.TokenException;
import com.landray.kmss.third.welink.model.ThirdWelinkConfig;
import com.landray.kmss.third.welink.model.ThirdWelinkDeptMapping;
import com.landray.kmss.third.welink.model.ThirdWelinkNotifyLog;
import com.landray.kmss.third.welink.model.ThirdWelinkPersonMapping;
import com.landray.kmss.third.welink.model.ThirdWelinkToken;
import com.landray.kmss.third.welink.service.IThirdWelinkDeptMappingService;
import com.landray.kmss.third.welink.service.IThirdWelinkPersonMappingService;
import com.landray.kmss.third.welink.service.IThirdWelinkService;
import com.landray.kmss.third.welink.service.IThirdWelinkTodoTaskMappService;
import com.landray.kmss.third.welink.util.ThirdWelinkApiUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdWelinkServiceImp
		implements IThirdWelinkService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdWelinkServiceImp.class);

	private IThirdWelinkDeptMappingService thirdWelinkDeptMappingService = null;

	private IThirdWelinkTodoTaskMappService thirdWelinkTodoTaskMappService = null;

	public IThirdWelinkDeptMappingService getThirdWelinkDeptMappingService() {
		return thirdWelinkDeptMappingService;
	}

	public void setThirdWelinkDeptMappingService(
			IThirdWelinkDeptMappingService thirdWelinkDeptMappingService) {
		this.thirdWelinkDeptMappingService = thirdWelinkDeptMappingService;
	}

	IThirdWelinkPersonMappingService thirdWelinkPersonMappingService = null;

	public IThirdWelinkPersonMappingService
			getThirdWelinkPersonMappingService() {
		return thirdWelinkPersonMappingService;
	}

	public void setThirdWelinkPersonMappingService(
			IThirdWelinkPersonMappingService thirdWelinkPersonMappingService) {
		this.thirdWelinkPersonMappingService = thirdWelinkPersonMappingService;
	}

	private static ThirdWelinkToken token;

	@Override
	public ThirdWelinkToken getToken() throws Exception {
		if (token == null || token.isExpired()) {
			updateToken();
		}
		return token;
	}

	public void updateToken() throws Exception {
		String url = ThirdWelinkConstant.API_URL + "/auth/v2/tickets";
		ThirdWelinkConfig config = ThirdWelinkConfig.newInstance();
		JSONObject body = new JSONObject();
		body.put("client_id", config.getWelinkClientid());
		body.put("client_secret", config.getWelinkClientsecret());
		String result = ThirdWelinkApiUtil.execHttpPost(url, body.toString(),
				null);
		JSONObject resultObj = JSONObject.fromObject(result);
		if ("0".equals(resultObj.getString("code"))) {
			String tokenStr = resultObj.getString("access_token");
			long expires_in = resultObj.getLong("expires_in");
			token = new ThirdWelinkToken(tokenStr, expires_in);
		} else {
			throw new TokenException("获取token失败，" + result);
		}
	}

	@Override
	public String getTokenStr() throws Exception {
		return getToken().getToken();
	}

	private String getParentId(SysOrgElement ele) throws Exception {
		SysOrgElement parent = ele.getFdParent();
		if (parent == null) {
			return "0";
		}
		ThirdWelinkDeptMapping mapping = thirdWelinkDeptMappingService
				.findByEkpId(parent.getFdId());
		if (mapping != null) {
			return mapping.getFdWelinkId();
		}
		return parent.getFdId();
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
			ThirdWelinkDeptMapping mapping, boolean isAdd) throws Exception {
		JSONObject o = new JSONObject();
		if (mapping == null) {
			o.put("corpDeptCode", dept.getFdId());
		} else {
			o.put("corpDeptCode", mapping.getFdWelinkId());
		}
		if (!isAdd) {
			SysOrgElement leader = dept.getHbmThisLeader();
			if (leader != null && leader.getFdOrgType() == 8) {
				ThirdWelinkPersonMapping person_mapping = thirdWelinkPersonMappingService
						.findByEkpId(leader.getFdId());
				if (person_mapping != null) {
					o.put("managerId", person_mapping.getFdWelinkUserId());
				}
			}
		}
		o.put("corpParentCode", getParentId(dept));
		o.put("deptNameCn", dept.getFdName("zh-CN"));
		o.put("deptNameEn", getNameEn(dept));
		o.put("valid", getValid(dept));
		return o;
	}

	private String getNameEn(SysOrgElement e) {
		Locale locale = ResourceUtil.getLocale("en-US");
		String localeCountry = SysLangUtil.getLocaleShortName(locale);
		String value_lang = e.getDynamicMap().get("fdName" + localeCountry);
		return value_lang;
	}

	private JSONObject buildPersonObj(SysOrgPerson person,
			ThirdWelinkPersonMapping mapping) throws Exception {
		JSONObject o = new JSONObject();
		if (mapping == null) {
			o.put("corpUserId", person.getFdId());
		} else {
			o.put("corpUserId", mapping.getFdWelinkId());
		}
		o.put("userNameCn", person.getFdName());
		o.put("userNameEn", getNameEn(person));

		o.put("sex", person.getFdSex());
		o.put("mobileNumber", person.getFdMobileNo());
		o.put("phoneNumber", person.getFdWorkPhone());
		String parentId = getParentId(person);
		if ("0".equals(parentId)) {
			o.put("deptCode", getParentId(person));
		} else {
			o.put("corpDeptCode", getParentId(person));
		}
		o.put("userEmail", person.getFdEmail());
		o.put("isOpenAccount", "1");
		o.put("remark", person.getFdName());
		o.put("valid", getValid(person));
		return o;
	}

	@Override
	public void syncDepts(List<SysOrgElement> depts) throws Exception {
		String url = ThirdWelinkConstant.API_URL
				+ "/contact/v2/departments/bulk";
		List<String> mapping2add = new ArrayList<String>();

		JSONArray deptsArray = new JSONArray();
		for (SysOrgElement dept : depts) {
			ThirdWelinkDeptMapping mapping = thirdWelinkDeptMappingService
					.findByEkpId(dept.getFdId());
			deptsArray.add(buildDeptObj(dept, mapping, true));
			if (mapping == null) {
				mapping2add.add(dept.getFdId());
			}
		}
		JSONObject deptInfo = new JSONObject();
		deptInfo.put("deptInfo", depts);
		String tokenStr = getTokenStr();
		String result = ThirdWelinkApiUtil.execHttpPost(url,
				deptInfo.toString(), tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
			JSONArray datas = resultObj.getJSONArray("data");
			for (int i = 0; i < datas.size(); i++) {
				JSONObject data = datas.getJSONObject(i);
				String dcode = data.getString("code");
				if ("0".equals(dcode) || "47100".equals(dcode)
						|| "47104".equals(dcode)
						|| "47107".equals(dcode) || "47108".equals(dcode)
						|| "47109".equals(dcode) || "47112".equals(dcode)
						|| "47113".equals(dcode)) {
					logger.info("同步部门成功：" + result);
				} else {
					throw new ApiException(
							"同步部门出错，请求信息:" + deptInfo.toString() + ",结果:"
									+ result);
				}
			}
		} else if ("47101".equals(code) || "47103".equals(code)) {
			throw new TokenException(
					"token出错，token:" + tokenStr + "," + result);
		} else {
			throw new ApiException(
					"同步部门出错，请求信息:" + deptInfo.toString() + ",结果:" + result);
		}
	}

	@Override
    public String syncDept(SysOrgElement dept, boolean isAdd)
			throws Exception {
		TransactionStatus status = TransactionUtils
				.beginNewTransaction();
		try {
			String result = syncDeptInner(dept, isAdd);
			TransactionUtils.commit(status);
			return result;
		} catch (Exception e) {
			TransactionUtils.rollback(status);
		}
		return null;
	}

	public String syncDeptInner(SysOrgElement dept, boolean isAdd)
			throws Exception {
		String url = ThirdWelinkConstant.API_URL
				+ "/contact/v2/departments/bulk";

		try {
			ThirdWelinkDeptMapping mapping = thirdWelinkDeptMappingService
					.findByEkpId(dept.getFdId());
			if (!dept.getFdIsAvailable() && mapping == null) {
				logger.debug("无效部门，且没有同步过，ID:" + dept.getFdId() + "，名称:"
						+ dept.getFdName());
				return null;
			}
			if (isAdd && mapping != null) {
				logger.warn("该部门已经同步过，无需新增，ID:" + dept.getFdId() + "，名称:"
						+ dept.getFdName());
				return null;
			}
			if (isAdd) {
				logger.debug("新增部门，ID:" + dept.getFdId() + "，名称:"
					+ dept.getFdName());
			} else {
				logger.debug("更新部门，ID:" + dept.getFdId() + "，名称:"
						+ dept.getFdName());
			}

			JSONArray depts = new JSONArray();
			JSONObject dept_req = buildDeptObj(dept, mapping, isAdd);
			depts.add(dept_req);
			JSONObject deptInfo = new JSONObject();
			deptInfo.put("deptInfo", depts);
			String tokenStr = getTokenStr();
			String result = ThirdWelinkApiUtil.execHttpPost(url,
					deptInfo.toString(), tokenStr);
			JSONObject resultObj = JSONObject.fromObject(result);
			String code = resultObj.getString("code");
			if ("0".equals(code)) {
				JSONArray datas = resultObj.getJSONArray("data");
				JSONObject data = datas.getJSONObject(0);
				String dcode = data.getString("code");
				if ("0".equals(dcode) || "47100".equals(dcode)
						|| "47104".equals(dcode)
						|| "47107".equals(dcode) || "47108".equals(dcode)
						|| "47109".equals(dcode) || "47112".equals(dcode)
						|| "47113".equals(dcode)) {
					logger.info("同步部门成功：" + result);
					if (mapping == null) {
						thirdWelinkDeptMappingService.addMapping(dept);
					}
					return dept_req.getString("corpDeptCode");
				} else {
					throw new ApiException(
							"同步部门出错，请求信息:" + deptInfo.toString() + ",结果:"
									+ result);
				}
			} else if ("47101".equals(code) || "47103".equals(code)) {
				throw new TokenException(
						"token出错，token:" + tokenStr + "," + result);
			} else {
				throw new ApiException(
						"同步部门出错，请求信息:" + deptInfo.toString() + ",结果:" + result);
			}
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}

	@Override
	public String syncPerson(SysOrgPerson person) throws Exception {
		String url = ThirdWelinkConstant.API_URL
				+ "/contact/v1/users/bulk";
		ThirdWelinkPersonMapping mapping = thirdWelinkPersonMappingService
				.findByEkpId(person.getFdId());

		if (!person.getFdIsAvailable() && mapping == null) {
			logger.info("无效人员，且没有同步过，ID:" + person.getFdId() + "，名称:"
					+ person.getFdName());
			return null;
		}

		if (StringUtil.isNull(person.getFdMobileNo())) {
			logger.warn("人员手机号为空，不进行同步，ID:" + person.getFdId() + "，名称:"
					+ person.getFdName());
			return null;
		}

		logger.debug("同步人员，ID:" + person.getFdId() + "，名称:"
				+ person.getFdName());

		JSONArray persons = new JSONArray();
		JSONObject person_req = buildPersonObj(person, mapping);
		persons.add(person_req);
		JSONObject personInfo = new JSONObject();
		personInfo.put("personInfo", persons);
		String tokenStr = getTokenStr();
		String result = ThirdWelinkApiUtil.execHttpPost(url,
				personInfo.toString(), tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
			JSONArray datas = resultObj.getJSONArray("data");
			JSONObject data = datas.getJSONObject(0);
			String dcode = data.getString("code");
			if ("0".equals(dcode) || "47100".equals(dcode)
					|| "47104".equals(dcode)
					|| "47107".equals(dcode) || "47108".equals(dcode)
					|| "47109".equals(dcode) || "47112".equals(dcode)
					|| "47113".equals(dcode)) {
				logger.info("同步人员成功，ID：" + person.getFdId() + ",名称："
						+ person.getFdName() + result);
				if (mapping == null) {
					TransactionStatus status = null;
					try{
						status = TransactionUtils.beginNewTransaction();
						thirdWelinkPersonMappingService.addMapping(person);
						TransactionUtils.commit(status);
					}
					catch (Exception e){
						if(status != null){
							TransactionUtils.rollback(status);
						}
						throw e;
					}
				}
				return person_req.getString("corpUserId");
			} else {
				throw new ApiException(
						"同步人员出错，请求信息:" + personInfo.toString() + ",结果:"
								+ result);
			}
		} else if ("47101".equals(code) || "47103".equals(code)) {
			throw new TokenException(
					"token出错，token:" + tokenStr + "," + result);
		} else {
			throw new ApiException(
					"同步人员失败，请求信息:" + personInfo.toString() + ",结果:" + result);
		}
	}

	/**
	 * 
	 * @param deptCode
	 * @param recursiveflag
	 *            false ：查询下级部门信息 true ：查询递归获取所有子部门
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONArray getDepts(String deptCode, boolean recursiveflag)
			throws Exception {
		String url = ThirdWelinkConstant.API_URL
				+ "/contact/v2/departments/list";
		if (StringUtil.isNull(deptCode)) {
			deptCode = "0";
			if (recursiveflag) {
				url += "?recursiveflag=0" + "&deptCode=" + deptCode;
				JSONArray topDepts = new JSONArray();
				JSONArray totalDepts = new JSONArray();
				getDepts(topDepts, url, 1);
				for (int i = 0; i < topDepts.size(); i++) {
					JSONObject deptObj = topDepts.getJSONObject(i);
					String deptCode_cur = deptObj.getString("deptCode");
					url = ThirdWelinkConstant.API_URL
							+ "/contact/v2/departments/list?recursiveflag=1"
							+ "&deptCode=" + deptCode_cur;
					JSONArray depts = new JSONArray();
					getDepts(depts, url, 1);
					totalDepts.addAll(depts);
				}
				totalDepts.addAll(topDepts);
				return totalDepts;
			}
		} else {
			url += "?recursiveflag=" + (recursiveflag ? "1" : "0")
					+ "&deptCode=" + deptCode;
		}
		JSONArray depts = new JSONArray();
		getDepts(depts, url, 1);
		return depts;
	}

	public void getDepts(JSONArray depts, String url, long offset)
			throws Exception {
		String tokenStr = getTokenStr();
		String result = ThirdWelinkApiUtil.execHttpGet(
				url + "&offset=" + offset,
				tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
			long offset_cur = resultObj.getLong("offset");
			long limit = resultObj.getLong("limit");
			long totalCount = resultObj.getLong("totalCount");
			JSONArray datas = resultObj.getJSONArray("departmentInfo");
			depts.addAll(datas);
			if (offset_cur * limit < totalCount) {
				getDepts(depts, url, offset_cur + 1);
			}
		} else {
			throw new ApiException(
					"获取部门出错，请求信息:" + url + ",结果:" + result);
		}
	}

	@Override
	public JSONArray getUsers(String deptCode)
			throws Exception {
		String url = ThirdWelinkConstant.API_URL
				+ "/contact/v1/user/users?pageSize=50";
		if (StringUtil.isNull(deptCode)) {
			deptCode = "0";
		}
		url += "&deptCode=" + deptCode;

		JSONArray users = new JSONArray();
		getUsers(users, url, 1);
		return users;
	}

	public void getUsers(JSONArray users, String url, long pageNo)
			throws Exception {
		String tokenStr = getTokenStr();
		String result = ThirdWelinkApiUtil.execHttpGet(
				url + "&pageNo=" + pageNo,
				tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
			long pageNo_cur = resultObj.getLong("pageNo");
			// long pageSize = resultObj.getLong("pageSize");
			long pages = resultObj.getLong("pages");
			JSONArray datas = resultObj.getJSONArray("data");
			users.addAll(datas);
			if (pageNo_cur < pages) {
				getUsers(users, url, pageNo_cur + 1);
			}
		} else {
			throw new ApiException(
					"获取人员出错，请求信息:" + url + ",结果:" + result);
		}
	}

	@Override
	public JSONObject getUser(String corpUserId)
			throws Exception {
		String url = ThirdWelinkConstant.API_URL
				+ "/contact/v1/users";
		url += "?corpUserId=" + corpUserId;

		String tokenStr = getTokenStr();

		String result = ThirdWelinkApiUtil.execHttpGet(
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

	public void syncPersons(List<SysOrgPerson> persons) throws Exception {
		String url = ThirdWelinkConstant.API_URL
				+ "/contact/v1/users/bulk";
		JSONArray personArray = new JSONArray();
		Map<String, SysOrgPerson> personsMap = new HashMap<String, SysOrgPerson>();
		for (SysOrgPerson person : persons) {
			ThirdWelinkPersonMapping mapping = thirdWelinkPersonMappingService
					.findByEkpId(person.getFdId());
			if (mapping == null) {
				personsMap.put(person.getFdId(), person);
			}
			if (!person.getFdIsAvailable() && mapping == null) {
				logger.info("无效人员，且没有同步过，ID:" + person.getFdId() + "，名称:"
						+ person.getFdName());
				continue;
			}
			if (StringUtil.isNull(person.getFdMobileNo())) {
				logger.warn("人员手机号为空，不进行同步，ID:" + person.getFdId() + "，名称:"
						+ person.getFdName());
				continue;
			}
			personArray.add(buildPersonObj(person, mapping));
		}
		JSONObject personInfo = new JSONObject();
		personInfo.put("personInfo", persons);
		String tokenStr = getTokenStr();
		String result = ThirdWelinkApiUtil.execHttpPost(url,
				personInfo.toString(), tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
			JSONArray datas = resultObj.getJSONArray("data");
			for (int i = 0; i < datas.size(); i++) {
				JSONObject data = datas.getJSONObject(i);
				String dcode = data.getString("code");
				if ("0".equals(dcode) || "47100".equals(dcode)
						|| "47104".equals(dcode)
						|| "47107".equals(dcode) || "47108".equals(dcode)
						|| "47109".equals(dcode) || "47112".equals(dcode)
						|| "47113".equals(dcode)) {
					logger.info(
							"同步人员成功，corpUserId：" + data.getString("corpUserId")
									+ ",结果：" + data.toString());
					if (personsMap.get(data.getString("corpUserId")) != null) {
						thirdWelinkPersonMappingService.addMapping(
								personsMap.get(data.getString("corpUserId")));
					}
				} else {
					logger.error(
							"同步人员失败，corpUserId：" + data.getString("corpUserId")
									+ ",结果：" + data.toString());
				}
			}
		} else if ("47101".equals(code) || "47103".equals(code)) {
			throw new TokenException(
					"token出错，token:" + tokenStr + "," + result);
		} else {
			throw new ApiException(
					"同步人员失败，请求信息:" + personInfo.toString() + ",结果:" + result);
		}
	}

	public static void main(String[] args) {
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < 201; i++) {
			ids.add(i + "");
		}
		for (int i = 0; i < Math.ceil(ids.size() / 50f); i++) {
			JSONArray corpIds = new JSONArray();
			int toIndex = (i + 1) * 50;
			if (toIndex > ids.size()) {
				toIndex = ids.size();
			}
			System.out.println(i);
			corpIds.addAll(ids.subList(i * 50, toIndex));
			System.out.println(corpIds.toString());
		}
	}

	public JSONArray getUserIds(List<String> corpUserIds)
			throws Exception {
		String url = ThirdWelinkConstant.API_URL
				+ "/contact/v1/users/accounts";

		String tokenStr = getTokenStr();
		JSONArray userIdObjs = new JSONArray();

		JSONObject corpUserId = new JSONObject();
		for (int i = 0; i < Math.ceil(corpUserIds.size() / 50f); i++) {

			JSONArray corpIds = new JSONArray();
			int toIndex = (i + 1) * 50;
			if (toIndex > corpUserIds.size()) {
				toIndex = corpUserIds.size();
			}
			corpIds.addAll(corpUserIds.subList(i * 50, toIndex));
			corpUserId.put("corpUserId", corpIds);
			String result = ThirdWelinkApiUtil.execHttpPost(
					url,
					corpUserId.toString(),
					tokenStr);
			JSONObject resultObj = JSONObject.fromObject(result);
			String code = resultObj.getString("code");
			if ("0".equals(code)) {
				userIdObjs.addAll(resultObj.getJSONArray("userInfo"));
			} else {
				throw new ApiException(
						"获取人员userid出错，请求地址:" + url + ",请求报文:" + corpUserId
								+ ",结果:" + result);
			}
		}
		return userIdObjs;
	}

	@Override
    public void updateWelinkUserIds() throws Exception {
		// List<String> corpIds = thirdWelinkPersonMappingService
		// .getCorpUseridsWithoutUserId();
		// JSONArray userIdObjs = getUserIds(corpIds);

		DataSource dataSource = (DataSource) SpringBeanUtil
				.getBean("dataSource");
		Connection conn = null;
		PreparedStatement person_userid_update = null;
		PreparedStatement person_userid_sqlect = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);

			person_userid_sqlect = conn.prepareStatement(
					"select fd_welink_id from third_welink_person_mapping where fd_welink_user_id is null or fd_welink_user_id = ''");
			person_userid_update = conn
					.prepareStatement(
							"update third_welink_person_mapping set fd_welink_user_id = ? where fd_welink_id = ?");

			rs = person_userid_sqlect.executeQuery();
			List<String> corpIds = new ArrayList<String>();
			while (rs.next()) {
				String fd_welink_id = rs.getString(1);
				corpIds.add(fd_welink_id);
			}
			JSONArray userIdObjs = getUserIds(corpIds);
			if (userIdObjs == null || userIdObjs.isEmpty()) {
				return;
			}
			for (int i = 0; i < userIdObjs.size(); i++) {
				JSONObject userIdObj = userIdObjs.getJSONObject(i);
				String userId = userIdObj.getString("userId");
				String corpUserId = userIdObj.getString("corpUserId");
				if (i != 0 && i % 200 == 0) {
					person_userid_update.executeBatch();
					conn.commit();
				}
				person_userid_update.setString(1, userId);
				person_userid_update.setString(2, corpUserId);
				person_userid_update.addBatch();

			}
			person_userid_update.executeBatch();
			conn.commit();
		} catch (Exception e) {
			logger.error("更新userid失败", e);
			throw e;
		} finally {
			JdbcUtils.closeStatement(person_userid_sqlect);
			JdbcUtils.closeStatement(person_userid_update);
			JdbcUtils.closeConnection(conn);
		}
	}

	@Override
    public JSONArray getUserSyncStatus(List<String> corpUserIds)
			throws Exception {
		List<String> corpUserIds_patch = new ArrayList<String>();
		JSONArray result = new JSONArray();
		for (int i = 0; i < corpUserIds.size(); i++) {
			if (i > 0 && i % 100 == 0) {
				JSONArray array = getUserSyncStatusPatch(corpUserIds_patch);
				result.addAll(array);
				corpUserIds_patch.clear();
			}
			corpUserIds_patch.add(corpUserIds.get(i));
		}
		return result;
	}

	public JSONArray getUserSyncStatusPatch(List<String> corpUserIds)
			throws Exception {
		String url = ThirdWelinkConstant.API_URL
				+ "/contact/v1/users/status";

		String tokenStr = getTokenStr();
		JSONObject personInfo = new JSONObject();
		JSONArray personArray = new JSONArray();
		for (int i = 0; i < corpUserIds.size(); i++) {
			JSONObject corpUserId = new JSONObject();
			corpUserId.put("corpUserId", corpUserIds.get(i));
			personArray.add(corpUserId);
		}
		personInfo.put("personInfo", personArray);
		String result = ThirdWelinkApiUtil.execHttpPost(
				url,
				personInfo.toString(),
				tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
			if (resultObj.containsKey("data")
					&& resultObj.get("data") instanceof JSONArray) {
				return resultObj.getJSONArray("data");
			} else {
				throw new ApiException(
						"获取人员同步结果出错，请求信息:" + url + ",结果:" + result);
			}
		} else {
			throw new ApiException(
					"获取人员同步结果出错，请求信息:" + url + ",结果:" + result);
		}
	}

	@Override
    public JSONArray getDeptSyncStatus(List<String> corpDeptIds)
			throws Exception {
		String url = ThirdWelinkConstant.API_URL
				+ "/contact/v2/departments/status";

		String tokenStr = getTokenStr();
		JSONObject deptInfo = new JSONObject();
		JSONArray deptArray = new JSONArray();

		for (int i = 0; i < corpDeptIds.size(); i++) {
			JSONObject corpDeptCode = new JSONObject();
			corpDeptCode.put("corpDeptCode", corpDeptIds.get(i));
			deptArray.add(corpDeptCode);
		}
		deptInfo.put("deptInfo", deptArray);
		String result = ThirdWelinkApiUtil.execHttpPost(
				url,
				deptInfo.toString(),
				tokenStr);
		JSONObject resultObj = JSONObject.fromObject(result);
		String code = resultObj.getString("code");
		if ("0".equals(code)) {
			return resultObj.getJSONArray("data");
		} else {
			throw new ApiException(
					"获取部门同步结果出错，请求信息:" + url + ",结果:" + result);
		}
	}

	@Override
    public String getSsoAccessToken(String code, String redirect_uri,
                                    String state)
			throws Exception {
		ThirdWelinkConfig config = ThirdWelinkConfig.newInstance();
		String url = ThirdWelinkConstant.API_URL
				+ "/oauth2/v1/token";
		url += "?grant_type=authorization_code&code=" + code + "&client_id="
				+ config.getWelinkClientid() + "&client_secret="
				+ config.getWelinkClientsecret() + "&redirect_uri="
				+ redirect_uri;
		if (StringUtil.isNotNull(state)) {
			url += "&state=" + state;
		}
		String result = ThirdWelinkApiUtil.execHttpGet(
				url,
				null);
		JSONObject resultObj = JSONObject.fromObject(result);
		String result_code = resultObj.getString("code");
		if ("60001".equals(result_code)) {
			return resultObj.getString("access_token");
		} else {
			throw new ApiException(
					"根据code获取access token出错，请求信息:" + url + ",结果:" + result);
		}
	}

	@Override
    public String getSsoUserId(String accessToken)
			throws Exception {
		String url = ThirdWelinkConstant.API_URL
				+ "/auth/v1/userid";

		String result = ThirdWelinkApiUtil.execHttpGet(
				url,
				accessToken);
		JSONObject resultObj = JSONObject.fromObject(result);
		String result_code = resultObj.getString("code");
		if ("0".equals(result_code)) {
			return resultObj.getString("userId");
		} else {
			throw new ApiException(
					"根据access token获取用户出错，请求信息:" + url + ",结果:" + result);
		}
	}

	@Override
    public String getClientUserId(String code)
			throws Exception {
		String url = ThirdWelinkConstant.API_URL
				+ "/auth/v2/userid?code=" + code;
		String accessToken = getTokenStr();
		String result = ThirdWelinkApiUtil.execHttpGet(
				url,
				accessToken);
		JSONObject resultObj = JSONObject.fromObject(result);
		String result_code = resultObj.getString("code");
		if ("0".equals(result_code)) {
			return resultObj.getString("userId");
		} else {
			throw new ApiException(
					"根据code获取用户出错，请求信息:" + url + ",结果:" + result);
		}
	}

	@Override
    public void sendMessage(String subject, String content, String urlPath,
                            String owner, JSONArray toUserList, ThirdWelinkNotifyLog log)
			throws Exception {
		String url = ThirdWelinkConstant.API_URL
				+ "/messages/v3/send";
		log.setFdUrl(url);

		JSONObject msg = new JSONObject();
		msg.put("msgRange", 0);
		msg.put("toUserList", toUserList);
		msg.put("urlType", "html");
		msg.put("msgTitle", subject);
		msg.put("msgContent", content);
		msg.put("urlPath", urlPath);
		msg.put("msgOwner", owner);
		ThirdWelinkConfig config = ThirdWelinkConfig.newInstance();
		if ("true".equals(config.getWelinkMsgMobileOnly())) {
			msg.put("msgDisplayMode", 1);
		}
		log.setFdReqData(msg.toString());

		String accessToken = getTokenStr();
		String result = ThirdWelinkApiUtil.execHttpPost(
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
					"发送公众号消息异常，请求地址:" + url + ",请求内容:" + msg.toString() + ",结果:"
							+ result);
		}
	}

	@Override
    public void sendMessage(JSONObject msg, ThirdWelinkNotifyLog log)
			throws Exception {
		String url = ThirdWelinkConstant.API_URL
				+ "/messages/v3/send";
		log.setFdUrl(url);
		String accessToken = getTokenStr();
		log.setFdReqData(msg.toString());

		String result = ThirdWelinkApiUtil.execHttpPost(
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
					"发送公众号消息异常，请求地址:" + url + ",请求内容:" + msg.toString() + ",结果:"
							+ result);
		}
	}


	private Map<String, String> getWelinkNotifyTaskApplicant(
			SysOrgPerson fromUser) throws Exception {
		Map<String, String> welinkNotifyTaskApplicant = new HashMap<String, String>();
		if (fromUser != null) {
			ThirdWelinkPersonMapping fromUser_mapping = thirdWelinkPersonMappingService
					.findByEkpId(fromUser.getFdId());
			if (fromUser_mapping != null) {
				String applicantUserId = fromUser_mapping.getFdWelinkUserId();
				if (StringUtil.isNotNull(applicantUserId)) {
					String applicantUserNameCn = fromUser.getFdName();
					String applicantUserNameEn = getNameEn(fromUser);
					welinkNotifyTaskApplicant.put("applicantUserId",
							applicantUserId);
					welinkNotifyTaskApplicant.put("applicantUserNameCn",
							applicantUserNameCn);
					welinkNotifyTaskApplicant.put("applicantUserNameEn",
							applicantUserNameEn);
					return welinkNotifyTaskApplicant;
				}
			}
		}
		ThirdWelinkConfig config = new ThirdWelinkConfig();
		String applicantUserId = config.getWelinkNotifyTaskApplicantUserId();
		String applicantUserNameCn = config
				.getWelinkNotifyTaskApplicantUserNameCn();
		String applicantUserNameEn = config
				.getWelinkNotifyTaskApplicantUserNameEn();
		if (StringUtil.isNotNull(applicantUserId)
				&& StringUtil.isNotNull(applicantUserNameCn)
				&& StringUtil.isNotNull(applicantUserNameEn)) {
			welinkNotifyTaskApplicant.put("applicantUserId",
					applicantUserId);
			welinkNotifyTaskApplicant.put("applicantUserNameCn",
					applicantUserNameCn);
			welinkNotifyTaskApplicant.put("applicantUserNameEn",
					applicantUserNameEn);
			return welinkNotifyTaskApplicant;
		}
		return null;
	}

	@Override
    public void addTodoTask(String notifyId, String subject,
                            SysOrgPerson toUser,
                            SysOrgPerson fromUser, String urlPath,
                            String modelName, ThirdWelinkNotifyLog log) throws Exception {

		Map<String, String> welinkNotifyTaskApplicant = getWelinkNotifyTaskApplicant(
				fromUser);

		if (welinkNotifyTaskApplicant == null
				|| welinkNotifyTaskApplicant.isEmpty()) {
			log.setFdResult(2);
			logger.error(
					"获取不到待办发送人，不进行待办同步，待办标题：" + subject + ",待办ID：" + notifyId);
			log.setFdErrMsg(
					"获取不到待办发送人，不进行待办同步，待办标题：" + subject + ",待办ID：" + notifyId);
			return;
		}

		ThirdWelinkPersonMapping toUser_mapping = thirdWelinkPersonMappingService
				.findByEkpId(toUser.getFdId());
		if (toUser_mapping != null) {
			String welinkUserId = toUser_mapping.getFdWelinkUserId();
			if (StringUtil.isNull(welinkUserId)) {
				log.setFdResult(2);
				logger.error(
						"待办接收人（ID:" + toUser.getFdId() + "名称:"
								+ toUser.getFdName() + ",登录名:"
								+ toUser.getFdLoginName()
								+ "）映射关系中的welinkUserId为空，可能是该用户同步到welink失败。不进行待办同步，待办标题："
								+ subject + ",待办ID：" + notifyId);

				log.setFdErrMsg(
						"待办接收人（ID:" + toUser.getFdId() + "名称:"
								+ toUser.getFdName() + ",登录名:"
								+ toUser.getFdLoginName()
								+ "）映射关系中的welinkUserId为空，可能是该用户同步到welink失败。不进行待办同步，待办标题："
								+ subject + ",待办ID：" + notifyId);
				log.setFdResult(2);
				return;
			}
		} else {
			log.setFdResult(2);
			logger.error(
					"待办发送人（ID:" + toUser.getFdId() + "名称:"
							+ toUser.getFdName() + ",登录名:"
							+ toUser.getFdLoginName()
							+ "）找不到对应的映射关系，可能是该用户没有同步到welink中。不进行待办同步，待办标题："
							+ subject + ",待办ID：" + notifyId);

			log.setFdErrMsg(
					"待办发送人（ID:" + toUser.getFdId() + "名称:"
							+ toUser.getFdName() + ",登录名:"
							+ toUser.getFdLoginName()
							+ "）找不到对应的映射关系，可能是该用户没有同步到welink中。不进行待办同步，待办标题："
							+ subject + ",待办ID：" + notifyId);
			return;
		}

		String applicantUserId = welinkNotifyTaskApplicant
				.get("applicantUserId");
		String applicantUserNameCn = welinkNotifyTaskApplicant
				.get("applicantUserNameCn");
		String applicantUserNameEn = welinkNotifyTaskApplicant
				.get("applicantUserNameEn");

		String url = ThirdWelinkConstant.API_URL
				+ "/todo/v1/addtask";
		log.setFdUrl(url);

		JSONObject msg = new JSONObject();
		String taskId = notifyId + "X" + toUser.getFdId();
		msg.put("taskId", taskId);
		msg.put("taskTitle", subject);
		msg.put("taskDesc", subject);
		msg.put("userId", toUser_mapping.getFdWelinkUserId());
		msg.put("detailsUrl", urlPath);
		// modelName = "a b";
		if (StringUtil.isNotNull(modelName)) {
			modelName = modelName.replaceAll(" ", "-");
		}
		msg.put("appName", modelName);
		msg.put("applicantUserId", applicantUserId);
		msg.put("applicantUserNameCn", applicantUserNameCn);
		msg.put("applicantUserNameEn", applicantUserNameEn);
		log.setFdReqData(msg.toString());

		String accessToken = getTokenStr();
		String result = ThirdWelinkApiUtil.execHttpPost(
				url,
				msg.toString(),
				accessToken);
		log.setFdResData(result);

		JSONObject resultObj = JSONObject.fromObject(result);

		String result_code = resultObj.getString("code");
		if ("0".equals(result_code)) {
			thirdWelinkTodoTaskMappService.addMapp(notifyId, subject,
					toUser.getFdId(), taskId,
					toUser_mapping.getFdWelinkUserId());
			return;
		} else {
			throw new ApiException(
					"新增待办任务异常，请求地址:" + url + ",请求内容:" + msg.toString() + ",结果:"
							+ result);
		}
	}

	/**
	 * 更新待办处理人，暂时用不上
	 */
	@Override
    public void updateTodoTask(String notifyId, String toUserId,
                               ThirdWelinkNotifyLog log)
			throws Exception {
		String url = ThirdWelinkConstant.API_URL
				+ "/todo/v1/updatetask";
		url += "?taskId=" + notifyId + "&userId=" + toUserId;

		log.setFdUrl(url);
		String accessToken = getTokenStr();
		String result = ThirdWelinkApiUtil.execHttpPut(
				url,
				null,
				accessToken);
		log.setFdResData(result);

		JSONObject resultObj = JSONObject.fromObject(result);

		String result_code = resultObj.getString("code");
		if ("0".equals(result_code)) {
			return;
		} else {
			throw new ApiException(
					"更新待办任务异常，请求地址:" + url + ",结果:"
							+ result);
		}
	}

	@Override
    public void delTodoTask(String taskId,
                            ThirdWelinkNotifyLog log)
			throws Exception {
		String url = ThirdWelinkConstant.API_URL
				+ "/todo/v1/deltask";
		url += "?taskId=" + taskId;

		log.setFdUrl(url);
		String accessToken = getTokenStr();
		String result = ThirdWelinkApiUtil.execHttpDel(
				url,
				accessToken);
		log.setFdResData(result);

		JSONObject resultObj = JSONObject.fromObject(result);

		String result_code = resultObj.getString("code");
		if ("0".equals(result_code)) {
			thirdWelinkTodoTaskMappService.deleteByTaskId(taskId);
			return;
		} else {
			throw new ApiException(
					"删除待办任务异常，请求地址:" + url + ",结果:"
							+ result);
		}
	}

	@Override
    public void addTodoTask(JSONObject o, ThirdWelinkNotifyLog log)
			throws Exception {

		String url = o.getString("url");
		log.setFdUrl(url);
		JSONObject msg = o.getJSONObject("reqData");
		log.setFdReqData(msg.toString());
		String accessToken = getTokenStr();
		String result = ThirdWelinkApiUtil.execHttpPost(
				url,
				msg.toString(),
				accessToken);
		log.setFdResData(result);

		JSONObject resultObj = JSONObject.fromObject(result);
		String result_code = resultObj.getString("code");
		if ("0".equals(result_code)) {
			thirdWelinkTodoTaskMappService.addMapp(
					o.getJSONObject("todo").getString("id"),
					o.getJSONObject("todo").getString("subject"),
					o.getString("toUser"), msg.getString("taskId"),
					msg.getString("userId"));
			return;
		} else {
			throw new ApiException(
					"新增待办任务异常，请求地址:" + url + ",请求内容:" + msg.toString() + ",结果:"
							+ result);
		}
	}

	@Override
    public void updateTodoTask(JSONObject o,
                               ThirdWelinkNotifyLog log)
			throws Exception {
		String url = o.getString("url");
		String accessToken = getTokenStr();
		log.setFdUrl(url);
		String result = ThirdWelinkApiUtil.execHttpPut(
				url,
				null,
				accessToken);
		log.setFdResData(result);
		JSONObject resultObj = JSONObject.fromObject(result);
		log.setFdResData(resultObj.toString());
		String result_code = resultObj.getString("code");
		if ("0".equals(result_code)) {
			return;
		} else {
			throw new ApiException(
					"更新待办任务异常，请求地址:" + url + ",结果:"
							+ result);
		}
	}

	@Override
    public void delTodoTask(JSONObject o,
                            ThirdWelinkNotifyLog log)
			throws Exception {
		String url = o.getString("url");
		String accessToken = getTokenStr();
		log.setFdUrl(url);
		String result = ThirdWelinkApiUtil.execHttpDel(
				url,
				accessToken);
		log.setFdResData(result);
		JSONObject resultObj = JSONObject.fromObject(result);
		String result_code = resultObj.getString("code");
		if ("0".equals(result_code)) {
			String todoId = o.getJSONObject("todo").getString("id");
			String userId = o.getString("toUser");
			thirdWelinkTodoTaskMappService
					.deleteByTaskId(todoId + "X" + userId);
			return;
		} else {
			throw new ApiException(
					"删除待办任务异常，请求地址:" + url + ",结果:"
							+ result);
		}
	}

	public IThirdWelinkTodoTaskMappService getThirdWelinkTodoTaskMappService() {
		return thirdWelinkTodoTaskMappService;
	}

	public void setThirdWelinkTodoTaskMappService(
			IThirdWelinkTodoTaskMappService thirdWelinkTodoTaskMappService) {
		this.thirdWelinkTodoTaskMappService = thirdWelinkTodoTaskMappService;
	}

	@Override
	public JSONObject getUserByMobileno(String mobileNo)
			throws Exception {
		if (!mobileNo.startsWith("+")) {
			mobileNo = "+86-" + mobileNo;
		}
		String url = ThirdWelinkConstant.API_URL
				+ "/contact/v1/users";
		url += "?mobileNumber=" + mobileNo;

		String tokenStr = getTokenStr();

		String result = ThirdWelinkApiUtil.execHttpGet(
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
}
