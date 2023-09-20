package com.landray.kmss.sys.organization.webservice.eco;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.identity.util.SysAuthenUtil;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgDefaultConfig;
import com.landray.kmss.sys.organization.model.SysOrgDept;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgElementExtProp;
import com.landray.kmss.sys.organization.model.SysOrgElementExtPropEnum;
import com.landray.kmss.sys.organization.model.SysOrgElementExternal;
import com.landray.kmss.sys.organization.model.SysOrgElementHideRange;
import com.landray.kmss.sys.organization.model.SysOrgElementRange;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrganizationConfig;
import com.landray.kmss.sys.organization.service.ISysOrgElementExternalDeptService;
import com.landray.kmss.sys.organization.service.ISysOrgElementExternalPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgElementExternalPostService;
import com.landray.kmss.sys.organization.service.ISysOrgElementExternalService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.SysOrgElementExtPropUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.sys.organization.webservice.SysOrgWebserviceConstant;
import com.landray.kmss.sys.organization.webservice.eco.org.SysEcoExtPorp;
import com.landray.kmss.sys.organization.webservice.eco.org.SysEcoExtPorpEnum;
import com.landray.kmss.sys.organization.webservice.eco.org.SysEcoOrg;
import com.landray.kmss.sys.organization.webservice.eco.org.SysEcoRange;
import com.landray.kmss.sys.organization.webservice.eco.org.SysOrgObject;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.annotation.RestApi;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生态组织WebService接口实现
 * 
 * @author panyh
 *
 *         2020年9月7日 下午3:05:50
 */
@Controller
@RequestMapping(value = "/api/sys-organization/sysSynchroEco", method = RequestMethod.POST)
@RestApi(docUrl = "/sys/organization/rest/sysOrg_eco_help.jsp", name = "sysSynchroEcoWebService", resourceKey = "sys-organization:sysSynchroEco.title")
public class SysSynchroEcoWebServiceImp implements ISysSynchroEcoWebService, SysOrgConstant, SysOrgWebserviceConstant {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysSynchroEcoWebServiceImp.class);

	private ISysOrgElementService sysOrgElementService;
	private ISysOrgPersonService sysOrgPersonService;
	private ISysOrgElementExternalService sysOrgElementExternalService;
	private ISysOrgElementExternalDeptService sysOrgElementExternalDeptService;
	private ISysOrgElementExternalPostService sysOrgElementExternalPostService;
	private ISysOrgElementExternalPersonService sysOrgElementExternalPersonService;

	public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	public void setSysOrgElementExternalService(ISysOrgElementExternalService sysOrgElementExternalService) {
		this.sysOrgElementExternalService = sysOrgElementExternalService;
	}

	public void setSysOrgElementExternalDeptService(
			ISysOrgElementExternalDeptService sysOrgElementExternalDeptService) {
		this.sysOrgElementExternalDeptService = sysOrgElementExternalDeptService;
	}

	public void setSysOrgElementExternalPostService(
			ISysOrgElementExternalPostService sysOrgElementExternalPostService) {
		this.sysOrgElementExternalPostService = sysOrgElementExternalPostService;
	}

	public void setSysOrgElementExternalPersonService(
			ISysOrgElementExternalPersonService sysOrgElementExternalPersonService) {
		this.sysOrgElementExternalPersonService = sysOrgElementExternalPersonService;
	}

	/**
	 * 新增(更新)生态组织
	 */
	@ResponseBody
	@RequestMapping(value = "/saveEcoElement", method = RequestMethod.POST)
	@Override
	public SysSynchroEcoResult saveEcoElement(@RequestBody SysSynchroEcoContext context) throws Exception {
		logger.debug("新增(更新)生态组织");
		SysSynchroEcoResult result = new SysSynchroEcoResult();
		try {
			List<SysEcoOrg> orgs = context.getOrgs();
			JSONArray errors = new JSONArray();
			JSONArray success = new JSONArray();
			if (CollectionUtils.isNotEmpty(orgs)) {
				for (SysEcoOrg org : orgs) {
					// 检查数据合法性
					JSONObject error = check(org);
					if (error.isEmpty()) {
						// 保存数据
						save(org, success, errors);
					} else {
						errors.add(error);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(errors)) {
				result.setMessage(errors.toString());
			}
			result.setCount(success.size());
			if (success.size() > 0) {
				if (CollectionUtils.isNotEmpty(errors)) {
					// 部分成功
					result.setReturnState(3);
				} else {
					result.setReturnState(OPT_ORG_STATUS_SUCCESS);
				}
				result.setSuccess(success.toString());
			} else {
				result.setReturnState(OPT_ORG_STATUS_FAIL);
			}
		} catch (Exception e) {
			logger.error("保存生态组织失败：", e);
			result.setMessage(getErrorMessage(e));
			result.setCount(0);
			result.setReturnState(OPT_ORG_STATUS_FAIL);
		}

		return result;
	}

	/**
	 * 获取生态组织
	 */
	@ResponseBody
	@RequestMapping(value = "/findEcoElement", method = RequestMethod.POST)
	@Override
	public SysSynchroEcoResult findEcoElement(@RequestBody SysSynchroEcoContext context) throws Exception {
		logger.debug("获取生态组织");
		SysSynchroEcoResult result = new SysSynchroEcoResult();

		try {
			// 按ID查询
			if (StringUtil.isNotNull(context.getId())) {
				JSONObject obj = getById(context.getId());
				result.setMessage(obj.toString());
				result.setCount(1);
				result.setReturnState(OPT_ORG_STATUS_SUCCESS);
			} else {
				String search = context.getSearch();
				JSONArray array;
				if (StringUtil.isNotNull(context.getParent())) {
					// 按上级ID查询
					array = findByParent(context);
				} else if (StringUtil.isNotNull(search)) {
					array = search(context);
				} else {
					// 查询所有组织类型
					array = findAllCate(context);
				}
				result.setMessage(array.toString());
				result.setCount(array.size());
				result.setReturnState(OPT_ORG_STATUS_SUCCESS);
			}
		} catch (Exception e) {
			logger.error("获取生态组织失败：", e);
			result.setMessage(getErrorMessage(e));
			result.setCount(0);
			result.setReturnState(OPT_ORG_STATUS_FAIL);
		}

		return result;
	}

	/**
	 * 保存数据
	 * 
	 * @param orgs
	 * @return
	 */
	private void save(SysEcoOrg org, JSONArray success, JSONArray errors) throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("name", org.getName());
		obj.put("type", org.getType());
		try {
			String id = null;
			if (ORG_WEB_TYPE_DEPT.equals(org.getType())) { // 保存组织
				id = saveDept(org);
			} else if (ORG_WEB_TYPE_POST.equals(org.getType())) { // 保存岗位
				id = savePost(org);
			} else if (ORG_WEB_TYPE_PERSON.equals(org.getType())) { // 保存人员
				id = savePerson(org);
			}
			obj.put("id", id);
			success.add(obj);
		} catch (Exception e) {
			logger.warn("", e);
			obj.put("error", e.getMessage());
			errors.add(obj);
		}
	}

	/**
	 * 保存组织
	 * 
	 * @param org
	 * @return
	 * @throws Exception
	 */
	private String saveDept(SysEcoOrg org) throws Exception {
		SysOrgDept dept = null;
		SysOrgDept old = null;
		boolean isAdd = false;
		if (StringUtil.isNotNull(org.getId())) {
			dept = (SysOrgDept) sysOrgElementExternalDeptService.findByPrimaryKey(org.getId(), null, true);
			if (dept != null) {
				old = SysOrgEcoUtil.cloneEcoOrg(dept);
			}
		}
		SysOrgElement parent = null;
		SysOrgElementRange range = null;
		if (org.getParent() != null) {
			parent = convertObj(org.getParent());
		}
		if (dept == null) {
			isAdd = true;
			dept = new SysOrgDept();
			dept.setFdId(org.getId());
			// 是否有效
			dept.setFdIsAvailable(true);
			// 设置为生态组织
			dept.setFdIsExternal(true);
			range = new SysOrgElementRange();
		} else {
			// 是否有效
			dept.setFdIsAvailable(org.getIsAvailable());
			range = dept.getFdRange();
		}
		// 名称
		dept.setFdName(org.getName());
		// 编号
		dept.setFdNo(org.getNo());
		// 排序号
		dept.setFdOrder(org.getOrder());
		// 所属组织
		if (parent != null) {
			dept.setFdParent(parent);
		}
		// 负责人
		if (CollectionUtils.isNotEmpty(org.getAdmins())) {
			dept.setAuthElementAdmins(convertList(org.getAdmins()));
		} else {
			dept.setAuthElementAdmins(null);
		}
		// 组织范围
		// 生态组织强制开启
		range.setFdIsOpenLimit(true);
		if (org.getRange() != null) {
			SysEcoRange temp = org.getRange();
			if (temp != null) {
				int viewType = 1;
				if (temp.getType() != null && temp.getType() >= 0 && temp.getType() <= 2) {
					viewType = temp.getType();
				}
				range.setFdViewType(viewType);
				String subType = "1";
				if (StringUtil.isNotNull(temp.getSubType())) {
					StringBuilder sb = new StringBuilder();
					List<String> subTypes = Arrays.asList(temp.getSubType().split("[,;]"));
					for (String st : subTypes) {
						if ("1".equals(st) || "2".equals(st)) {
							if (sb.length() > 0) {
								sb.append(";");
							}
							sb.append(st);
						}
					}
					if (sb.length() > 0) {
						subType = sb.toString();
					}
				}
				range.setFdViewSubType(subType);
				if (CollectionUtils.isNotEmpty(temp.getOthers())) {
					range.setFdOthers(convertList(temp.getOthers()));
				}
			}
		} else {
			range.setFdViewType(1);
		}
		dept.setFdRange(range);

		// 部门扩展属性
		List<SysEcoExtPorp> extProps = org.getExtProps();
		String tableName = org.getTable();
		// 保存变更日志
		SysOrgEcoUtil.addOrgModifyLog(old, dept, tableName, extProps, isAdd);
		if (CollectionUtils.isNotEmpty(extProps)) {
			// 保存扩展属性
			saveProp(extProps, org.getTable(), dept.getFdId());
		}
		sysOrgElementExternalDeptService.save(dept, tableName, extProps, isAdd);
		return dept.getFdId();
	}

	/**
	 * 保存岗位
	 * 
	 * @param org
	 * @return
	 * @throws Exception
	 */
	private String savePost(SysEcoOrg org) throws Exception {
		SysOrgPost post = null;
		SysOrgPost old = null;
		boolean isAdd = false;
		if (StringUtil.isNotNull(org.getId())) {
			post = (SysOrgPost) sysOrgElementExternalPostService.findByPrimaryKey(org.getId(), null, true);
			if (post != null) {
				old = SysOrgEcoUtil.cloneEcoOrg(post);
			}
		}
		SysOrgElement parent = null;
		if (org.getParent() != null) {
			parent = convertObj(org.getParent());
		}
		if (post == null) {
			isAdd = true;
			post = new SysOrgPost();
			post.setFdId(org.getId());
			// 是否有效
			post.setFdIsAvailable(true);
			// 设置为生态组织
			post.setFdIsExternal(true);
		} else {
			// 是否有效
			post.setFdIsAvailable(org.getIsAvailable());
		}
		// 名称
		post.setFdName(org.getName());
		// 编号
		post.setFdNo(org.getNo());
		// 排序号
		post.setFdOrder(org.getOrder());
		// 所属组织
		if (parent != null) {
			post.setFdParent(parent);
		}
		// 岗位领导
		if (org.getThisLeader() != null) {
			SysOrgElement leader = convertObj(org.getThisLeader());
			if (leader != null) {
				post.setHbmThisLeader(leader);
			}
		} else {
			post.setHbmThisLeader(null);
		}
		// 人员列表
		if (CollectionUtils.isNotEmpty(org.getPersons())) {
			post.setFdPersons(convertList(org.getPersons()));
		} else {
			post.setFdPersons(null);
		}
		// 备注
		post.setFdMemo(org.getMemo());
		// 保存变更日志
		SysOrgEcoUtil.addOrgModifyLog(old, post, null, null, isAdd);
		sysOrgElementExternalPostService.save(post, isAdd);
		return post.getFdId();
	}

	/**
	 * 保存人员
	 * 
	 * @param org
	 * @return
	 * @throws Exception
	 */
	private String savePerson(SysEcoOrg org) throws Exception {
		SysOrgPerson person = null;
		SysOrgPerson old = null;
		boolean isAdd = false;
		if (StringUtil.isNotNull(org.getId())) {
			person = (SysOrgPerson) sysOrgElementExternalPersonService.findByPrimaryKey(org.getId(), null, true);
			if (person != null) {
				old = SysOrgEcoUtil.cloneEcoOrg(person);
			}
		}
		SysOrgElement parent = null;
		if (org.getParent() != null) {
			parent = convertObj(org.getParent());
		}
		if (person == null) {
			isAdd = true;
			person = new SysOrgPerson();
			person.setFdId(org.getId());
			// 是否有效
			person.setFdIsAvailable(true);
			// 设置为生态组织
			person.setFdIsExternal(true);
			// 密码
			if (StringUtil.isNotNull(org.getPassword())) {
				person.setFdNewPassword(org.getPassword());
			} else {
				// 密码为空时，使用默认密码
				person.setFdNewPassword(new SysOrgDefaultConfig().getOrgDefaultPassword());
			}
		} else {
			// 是否有效
			person.setFdIsAvailable(org.getIsAvailable());
		}
		// 名称
		person.setFdName(org.getName());
		// 编号
		person.setFdNo(org.getNo());
		// 排序号
		person.setFdOrder(org.getOrder());
		// 所属组织
		if (parent != null) {
			person.setFdParent(parent);
		}
		// 登录名
		person.setFdLoginName(org.getLoginName());
		// 手机号码
		person.setFdMobileNo(org.getMobileNo());
		// 邮件地址
		person.setFdEmail(org.getEmail());
		// 所属岗位
		if (CollectionUtils.isNotEmpty(org.getPosts())) {
			person.setFdPosts(convertList(org.getPosts()));
		} else {
			person.setFdPosts(Collections.EMPTY_LIST);
		}

		// 人员扩展属性
		List<SysEcoExtPorp> extProps = org.getExtProps();
		String tableName = org.getTable();
		// 保存变更日志
		SysOrgEcoUtil.addOrgModifyLog(old, person, tableName, extProps, isAdd);
		if (CollectionUtils.isNotEmpty(extProps)) {
			saveProp(extProps, org.getTable(), person.getFdId());
		}
		sysOrgElementExternalPersonService.save(person, tableName, extProps, isAdd);
		return person.getFdId();
	}

	/**
	 * 保存扩展属性
	 * 
	 * @param session
	 * @param extProps
	 * @param tableName
	 * @param id
	 * @throws Exception
	 */
	private void saveProp(List<SysEcoExtPorp> extProps, String tableName, String id) throws Exception {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();

		String tempSql = "select count(*) from " + tableName + " where fd_id = '" + id + "'";
		Query propertyQuery = sysOrgElementService.getBaseDao().getHibernateSession().createNativeQuery(tempSql);
		int total = Integer.parseInt(propertyQuery.list().get(0).toString());
		boolean isAdd;
		if (total == 0) {
			isAdd = true;
			sql.append("INSERT INTO ").append(tableName).append(" (");
			StringBuilder sb = new StringBuilder();
			// 拼接SQL
			for (SysEcoExtPorp prop : extProps) {
				// 拼接参数值
				String value = prop.getValue();
				// 过滤禁用属性
				if (!BooleanUtils.isTrue(prop.getStatus())) {
					continue;
				}

				sql.append(prop.getColumn()).append(", ");
				sb.append("?, ");
				if ("java.util.Date".equals(prop.getFieldType()) && StringUtil.isNotNull(value)) {
					String pattern = ResourceUtil.getString("date.format." + prop.getDisplayType());
					Date date = DateUtil.convertStringToDate(value, pattern);
					params.add(date);
				} else if (StringUtil.isNull(value)) {
					params.add("");
				} else {
					params.add(value);
				}
			}
			sql.append("fd_id) VALUES (").append(sb.toString()).append("?)");
			params.add(id);
		} else {
			isAdd = false;
			sql.append("UPDATE ").append(tableName).append(" SET fd_id = fd_id");
			// 拼接SQL
			for (SysEcoExtPorp prop : extProps) {
				// 拼接参数值
				String value = prop.getValue();
				// 未传入的属性，不更新
				if (value == null) {
					continue;
				}
				sql.append(", ").append(prop.getColumn()).append(" = ?");
				if ("java.util.Date".equals(prop.getFieldType())) {
					String pattern = ResourceUtil.getString("date.format." + prop.getDisplayType());
					Date date = DateUtil.convertStringToDate(value, pattern);
					params.add(date);
				} else {
					params.add(value);
				}
			}
			sql.append(" WHERE fd_id = ?");
			params.add(id);
		}
		// 记录变更日志
		SysOrgUtil.ecoChangeFields.set(SysOrgElementExtPropUtil.compare(tableName, id, extProps, isAdd));
		NativeQuery query = sysOrgElementService.getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
		if("sys_org_person".equalsIgnoreCase(tableName)){
			query.addSynchronizedQuerySpace("sys_org_person", "sys_org_element");
        }else{
			query.addSynchronizedQuerySpace(tableName);
		}
		// 设置参数
		for (int i = 0; i < params.size(); i++) {
			query.setParameter(i, params.get(i));
		}
		// 保存扩展数据
		query.executeUpdate();
	}

	/**
	 * 检查数据合法性
	 * 
	 * @param context
	 */
	private JSONObject check(SysEcoOrg org) throws Exception {
		JSONObject error = new JSONObject();
		// 检查上级组织
		SysOrgElementExternal external = checkParent(org, error);
		// 检查通用属性
		checkGeneral(org, error);

		// 扩展属性合并
		List<SysEcoExtPorp> extProps = org.getExtProps();
		if (external != null && CollectionUtils.isNotEmpty(extProps)) {
			List<SysOrgElementExtProp> infoProps = null;
			if (ORG_WEB_TYPE_DEPT.equals(org.getType())) { // 组织
				infoProps = external.getFdDeptProps();
				org.setTable(external.getFdDeptTable());
			} else if (ORG_WEB_TYPE_PERSON.equals(org.getType())) { // 人员
				infoProps = external.getFdPersonProps();
				org.setTable(external.getFdPersonTable());
			}
			// 合并
			extProps = merge(extProps, infoProps);
			org.setExtProps(extProps);
		}

		// 检查扩展属性
		checkExt(org, error);
		JSONObject json = new JSONObject();
		if (!error.isEmpty()) {
			json.put("id", org.getId());
			json.put("name", org.getName());
			json.put("error", error);
		}
		return json;
	}

	/**
	 * 检查上级组织
	 * 
	 * @param org
	 * @param error
	 */
	private SysOrgElementExternal checkParent(SysEcoOrg org, JSONObject error) throws Exception {
		// 岗位和人员的上级只能是组织（部门）
		if (org.getParent() != null) {
			SysOrgElement parent = convertObj(org.getParent());
			if (parent == null) {
				error.put("parent", "上级组织不存在");
				return null;
			} else {
				// 所在组织只能是外部组织
				if (!BooleanUtils.isTrue(parent.getFdIsExternal())) {
					error.put("parent", "上级组织只能是生态组织，不允许设置为内部组织。");
					return null;
				}
				if (ORG_WEB_TYPE_POST.equals(org.getType()) || ORG_WEB_TYPE_PERSON.equals(org.getType())) {
					// 岗位和人员的所在组织只能是部门
					if (parent.getFdOrgType() != ORG_TYPE_DEPT) {
						error.put("parent", "上级组织不合法");
						return null;
					}
				}
			}
		}
		// 检查组织类型是否正确（新增组织必须要传入parent，更新组织时parent不能跨组织类型）
		SysOrgElementExternal external = getElementExternal(org);
		if (external == null) {
			// 无法找到组织类型
			error.put("parent", ResourceUtil.getString("errors.required", null, null, "parent"));
			return null;
		}
		// 判断组织类型是否有变更
		if (StringUtil.isNotNull(org.getId())) {
			// 组织和人员的组织类型不能变更
			if (ORG_WEB_TYPE_DEPT.equals(org.getType()) || ORG_WEB_TYPE_PERSON.equals(org.getType())) {
				SysOrgElement element = (SysOrgElement) sysOrgElementService.findByPrimaryKey(org.getId(), null, true);
				if (element != null) {
					if (!element.getFdParentOrg().getFdId().equals(external.getFdElement().getFdId())) {
						// 组织类型发生变更
						error.put("parent", "组织类型发生变更");
					}
				}
			}
		}
		return external;
	}

	/**
	 * 检查通用属性
	 * 
	 * @param org
	 * @param error
	 */
	private void checkGeneral(SysEcoOrg org, JSONObject error) throws Exception {
		// 检查名称是否必填
		if (StringUtil.isNull(org.getName())) {
			// 名称为空
			error.put("name", ResourceUtil.getString("errors.required", null, null, "name"));
		} else {
			// 检查名称是否唯一
			if (!checkUniqueName(org)) {
				error.put("name", ResourceUtil.getString("sys-organization:sys.organization.mustUnique.error", null, null, org.getName()));
			}
		}
		// 组织属性校验
		if (ORG_WEB_TYPE_DEPT.equals(org.getType())) {
			// 检查负责人，只能在本组织内
			List<SysOrgElement> admins = convertList(org.getAdmins());
			if (CollectionUtils.isNotEmpty(admins)) {
				SysOrgElement parent = convertObj(org.getParent());
				JSONArray ayyay = new JSONArray();
				for (SysOrgElement admin : admins) {
					if (!admin.getFdHierarchyId().startsWith(parent.getFdHierarchyId())) {
						JSONObject obj = new JSONObject();
						obj.put("id", admin.getFdId());
						obj.put("name", admin.getFdName());
						ayyay.add(obj);
					}
				}
				if (!ayyay.isEmpty()) {
					error.put("admins", "负责人不在本组织中（" + ayyay.toString() + "）");
				}
			}
			// 组织置为无效时，需要检查所有子组织、岗位、人员是否包含有效
			if (!BooleanUtils.isTrue(org.getIsAvailable())) {
				SysOrgElement element = (SysOrgElement) sysOrgElementService.findByPrimaryKey(org.getId(), null, true);
				if (element != null) {
					List<Object> list = sysOrgElementService.findValue("count(sysOrgElement.fdId)", "sysOrgElement.fdHierarchyId like '" + element.getFdHierarchyId() + "%' and sysOrgElement.fdIsAvailable = true and sysOrgElement.fdId != '" + element .getFdId() + "'", null);
					if (Integer.parseInt(list.get(0).toString()) > 0) {
						error.put("isAvailable", ResourceUtil.getString("sys-organization:sysOrgElementExternal.disable.err"));
					}
				}
			}
		}
		// 岗位属性校验
		if (ORG_WEB_TYPE_POST.equals(org.getType())) {
			// 岗位领导
			SysOrgElement thisLeader = convertObj(org.getThisLeader());
			if (thisLeader != null) {
				// 岗位领导只能是该岗位所在组织下的人员或岗位
				SysOrgElement parent = convertObj(org.getParent());
				if (!thisLeader.getFdHierarchyId().startsWith(parent.getFdHierarchyId())) {
					error.put("thisLeader", "岗位领导不在本组织中（" + org.getThisLeader().toString() + "）");
				}
			}
		}
		// 人员属性校验
		if (ORG_WEB_TYPE_PERSON.equals(org.getType())) {
			// 检查登录名不能为空
			if (StringUtil.isNull(org.getLoginName())) {
				// 名称为空
				error.put("loginName", ResourceUtil.getString("errors.required", null, null, "loginName"));
			} else {
				// 检查登录名和手机号是否唯一
				if (!checkUniqueLoginName(org)) {
					error.put("loginName", ResourceUtil.getString("sys-organization:sysOrgPerson.error.loginName.notUnique", null, null, org.getLoginName()));
				}
				// 检查登录名是否合法
				if ("true".equals(new SysOrganizationConfig().getIsLoginSpecialChar())) {
					// 允许包含特殊字符
					if (!org.getLoginName().matches("^[A-Za-z0-9_@#$%^&()={}:;\'?/<>,.\"\\[\\]|\\-\\+ ]+$")) {
						error.put("loginName", ResourceUtil.getString("sys-organization:sysOrgElementExternal.person.loginSpecialChar"));
					}
				} else {
					if (!org.getLoginName().matches("^[A-Za-z0-9_]+$")) {
						error.put("loginName", ResourceUtil.getString("sys-organization:sysOrgPerson.error.loginName.abnormal"));
					}
				}
			}
			if (StringUtil.isNull(org.getMobileNo())) {
				// 名称为空
				error.put("mobileNo", ResourceUtil.getString("errors.required", null, null, "mobileNo"));
			} else {
				String mobileNo = org.getMobileNo();
				String pattern;
				if (mobileNo.startsWith("+")) {
					if (mobileNo.startsWith("+86")) {
						pattern = "^(\\+86)-?(\\d{11})$";
					} else {
						pattern = "^((\\+?\\d{1,5})|(\\(\\+?\\d{1,5}\\)))?-?(\\d{6,11})$";
					}
				} else {
					// 没有带+号开头，默认是国内手机号
					pattern = "^(\\d{11})$";
				}
				// 手机号格式校验
				if (mobileNo.matches(pattern)) {
					// 检查手机号不能为空
					if (!checkUniqueMobileNo(org)) {
						error.put("mobileNo", "手机号已被使用，请重新输入(" + org.getMobileNo() + ")");
					}
				} else {
					error.put("mobileNo", ResourceUtil.getString("sys-organization:sysOrgPerson.error.newMoblieNoError")
							+ ResourceUtil.getString("sys-organization:sysOrgPerson.moblieNo.tips"));
				}
			}
			// 邮箱格式校验
			if (StringUtil.isNotNull(org.getEmail())) {
				if (!SysAuthenUtil.isEmail(org.getEmail())) {
					error.put("email", "邮箱格式不正确，请重新输入(" + org.getEmail() + ")");
				}
			}
		}
		// 类型必填
		if (StringUtil.isNull(org.getType())) {
			error.put("type", ResourceUtil.getString("errors.required", null, null, "type"));
		} else {
			if (!ORG_WEB_TYPE_DEPT.equals(org.getType()) && !ORG_WEB_TYPE_POST.equals(org.getType())
					&& !ORG_WEB_TYPE_PERSON.equals(org.getType())) {
				error.put("type", ResourceUtil.getString("errors.required", null, null, "type"));
			}
		}
		// 检查编号是否唯一
		checkUniqueNo(org, error);
	}

	/**
	 * 检查名称是否唯一
	 * 
	 * @param modelObj
	 * @return
	 * @throws Exception
	 */
	private boolean checkUniqueName(SysEcoOrg org) throws Exception {
		int type = 0;
		if (ORG_WEB_TYPE_DEPT.equals(org.getType())) { // 组织
			type = ORG_TYPE_DEPT;
		} else if (ORG_WEB_TYPE_POST.equals(org.getType())) { // 岗位
			type = ORG_TYPE_POST;
		} else if (ORG_WEB_TYPE_PERSON.equals(org.getType())) { // 人员
			type = ORG_TYPE_PERSON;
		}
		boolean keepGroupUnique = false;
		try {
			keepGroupUnique = new SysOrganizationConfig().isKeepGroupUnique();
		} catch (Exception e) {
			logger.error(e.toString());
		}
		if (keepGroupUnique && (type & ~(ORG_TYPE_PERSON | ORG_TYPE_ROLE)) > 0) {
			String fdId = org.getId();
			String fdName = org.getName();
			HQLInfo hqlInfo = new HQLInfo();
			String hql = " sysOrgElement.fdName=:fdName ";
			hqlInfo.setParameter("fdName", fdName);
			if (StringUtil.isNotNull(fdId)) {
				hql += " and sysOrgElement.fdId!=:fdId ";
				hqlInfo.setParameter("fdId", fdId);
			}
			if ((type == 4) || (type == 16)) {// 这里是对岗位或群组名称的重复检测
				hql += " and sysOrgElement.fdOrgType!=8 and sysOrgElement.fdOrgType!=32 ";
			}
			hql = hql + " and sysOrgElement.fdIsAvailable=" + SysOrgHQLUtil.toBooleanValueString(true) + " "; // 1 表示有效的登录名 检测有效部分是否重名
			hqlInfo.setWhereBlock(hql);
			List list = sysOrgElementService.findList(hqlInfo);
			if ((list != null) && (list.size() > 0)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查编号是否唯一
	 * 
	 * @param fdId
	 * @param fdOrgType
	 * @param fdNo
	 * @throws Exception
	 */
	public void checkUniqueNo(SysEcoOrg org, JSONObject error) throws Exception {
		int type = 0;
		if (ORG_WEB_TYPE_DEPT.equals(org.getType())) { // 组织
			type = ORG_TYPE_DEPT;
		} else if (ORG_WEB_TYPE_POST.equals(org.getType())) { // 岗位
			type = ORG_TYPE_POST;
		} else if (ORG_WEB_TYPE_PERSON.equals(org.getType())) { // 人员
			type = ORG_TYPE_PERSON;
		}

		boolean noRequired = new SysOrganizationConfig().isNoRequired();
		if (noRequired) {
			if (StringUtil.isNull(org.getNo())) {
				error.put("no", ResourceUtil.getString("errors.required", null, null, "no"));
			} else {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("fdId != :fdId and fdOrgType = :fdOrgType and fdNo = :fdNo");
				hqlInfo.setParameter("fdId", org.getId());
				hqlInfo.setParameter("fdOrgType", type);
				hqlInfo.setParameter("fdNo", org.getNo());
				List list = sysOrgElementService.findList(hqlInfo);
				if (CollectionUtils.isNotEmpty(list)) {
					error.put("no", ResourceUtil.getString("sys-organization:organization.error.fdNo.mustUnique.forImport", null, null, org.getNo()));
				}
			}
		}
	}

	/**
	 * 检查登录名是否唯一
	 * 
	 * @param org
	 * @return
	 * @throws Exception
	 */
	private boolean checkUniqueLoginName(SysEcoOrg org) throws Exception {
		List list = sysOrgElementExternalPersonService.findValue("sysOrgPerson.fdId", "sysOrgPerson.fdLoginName='"
				+ StringUtil.replace(org.getLoginName(), "'", "''") + "' and sysOrgPerson.fdIsAvailable=" + SysOrgHQLUtil.toBooleanValueString(true), null);
		if (CollectionUtils.isNotEmpty(list)) {
			for (Object obj : list) {
				if (!obj.equals(org.getId())) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 检查手机号是否唯一
	 * 
	 * @param org
	 * @return
	 * @throws Exception
	 */
	private boolean checkUniqueMobileNo(SysEcoOrg org) throws Exception {
		String mobileNo = org.getMobileNo();
		// 如果手机号是以+86开头，则强制去掉+86
		if (mobileNo.startsWith("+86")) { // 国内手机号去掉国际区号
			mobileNo = mobileNo.substring(3).replaceAll("-", "");
			HQLInfo hqlInfo = new HQLInfo();
			String hql = " sysOrgPerson.fdMobileNo=:fdMobileNo and sysOrgPerson.fdId!=:fdId and sysOrgPerson.fdIsAvailable=" + SysOrgHQLUtil.toBooleanValueString(true);
			hqlInfo.setWhereBlock(hql);
			hqlInfo.setParameter("fdMobileNo", mobileNo);
			hqlInfo.setParameter("fdId", org.getId());
			List<SysOrgPerson> list = sysOrgElementExternalPersonService.findList(hqlInfo);
			if (CollectionUtils.isNotEmpty(list)) {
				return false;
			}
		} else if (mobileNo.length() > 6) { // 国外手机号需要单独处理
			String temp = mobileNo.substring(mobileNo.length() - 6);
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("sysOrgPerson.fdId, sysOrgPerson.fdMobileNo, sysOrgPerson.fdLoginName");
			hqlInfo.setWhereBlock(" sysOrgPerson.fdMobileNo like :temp and sysOrgPerson.fdIsAvailable=" + SysOrgHQLUtil.toBooleanValueString(true));
			hqlInfo.setParameter("temp", "%" + temp);
			List<Object[]> list = sysOrgElementExternalPersonService.findList(hqlInfo);
			if (CollectionUtils.isNotEmpty(list)) {
				mobileNo = mobileNo.replaceAll("-", "");
				for (Object[] obj : list) {
					String id = String.valueOf(obj[0]);
					String mno = String.valueOf(obj[1]).replaceAll("-", "");
					if (mno.equals(mobileNo) && !id.equals(org.getId())) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 检查扩展属性
	 * 
	 * @param org
	 * @param error
	 */
	private void checkExt(SysEcoOrg org, JSONObject error) throws Exception {
		// 检查扩展属性（如有）
		List<SysEcoExtPorp> props = org.getExtProps();
		if (CollectionUtils.isNotEmpty(props)) {
			JSONArray array = new JSONArray();
			for (SysEcoExtPorp prop : props) {
				// 排除禁用的属性
				if (!BooleanUtils.isTrue(prop.getStatus())) {
					continue;
				}
				// 获取参数值
				String value = prop.getValue();
				// 判断必填
				if (BooleanUtils.isTrue(prop.getRequired()) && StringUtil.isNull(value)) {
					array.add(ResourceUtil.getString("errors.required", null, null, prop.getName()));
					continue;
				}
				// 下面的校验只针对有参数值的属性
				if (StringUtil.isNull(value)) {
					continue;
				}
				// 判断数据类型
				if ("java.lang.Integer".equalsIgnoreCase(prop.getFieldType())) {
					try {
						Integer.parseInt(value);
					} catch (Exception e) {
						array.add(ResourceUtil.getString("errors.integer", null, null, prop.getName()));
						continue;
					}
				} else if ("java.lang.Double".equalsIgnoreCase(prop.getFieldType())) {
					try {
						Double.parseDouble(value);
					} catch (Exception e) {
						array.add(ResourceUtil.getString("errors.double", null, null, prop.getName()));
						continue;
					}
					// 精度
					if (!value.matches("\\d+.\\d{0," + prop.getScale().toString() + "}")) {
						array.add(prop.getName() + "：精度不能超过" + prop.getScale().toString() + "位");
						continue;
					}
				} else if ("java.util.Date".equalsIgnoreCase(prop.getFieldType())) {
					try {
						String pattern = ResourceUtil.getString("date.format." + prop.getDisplayType());
						DateUtil.convertStringToDate(value, pattern);
					} catch (Exception e) {
						array.add(ResourceUtil.getString("errors.date", null, null, prop.getName()));
						continue;
					}
				}
				// 判断字符长度
				if ("java.lang.String".equalsIgnoreCase(prop.getFieldType()) && StringUtil.isNotNull(value)
						&& value.length() > prop.getFieldLength()) {
					array.add(ResourceUtil.getString("errors.maxLength.simple", null, null,
							new Object[] { prop.getName(), prop.getFieldLength() }));
					continue;
				}
			}
			if (array.size() > 0) {
				error.put("extProp", array);
			}
		}
	}

	/**
	 * 获取异常堆栈信息
	 * 
	 * @param e
	 * @return
	 */
	private String getErrorMessage(Exception e) {
		if (null == e) {
			return "";
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	/**
	 * 查询所有组织类型
	 * 
	 * @return
	 * @throws Exception
	 */
	private JSONArray findAllCate(SysSynchroEcoContext context) throws Exception {
		JSONArray array = new JSONArray();
		String where = "";
		if (context.getIsAvailable() != null) {
			if (BooleanUtils.isTrue(context.getIsAvailable())) {
				where = "sysOrgElementExternal.fdElement.fdIsAvailable = true";
			} else {
				where = "sysOrgElementExternal.fdElement.fdIsAvailable = false";
			}
		}
		List<SysOrgElementExternal> list = sysOrgElementExternalService.findList(
				where, "sysOrgElementExternal.fdElement.fdOrder, sysOrgElementExternal.fdElement.fdNamePinYin");
		if (CollectionUtils.isNotEmpty(list)) {
			for (SysOrgElementExternal ext : list) {
				array.add(parseElementExternal(ext.getFdElement()));
			}
		}
		return array;
	}

	/**
	 * 根据上级ID查询所有子级
	 * 
	 * @param parent
	 * @return
	 * @throws Exception
	 */
	private JSONArray findByParent(SysSynchroEcoContext context) throws Exception {
		JSONArray array = new JSONArray();
		HQLInfo hqlInfo = new HQLInfo();

		StringBuilder where = new StringBuilder();
		where.append("sysOrgElement.hbmParent.fdId = :parent");
		if (context.getIsAvailable() != null) {
			if (BooleanUtils.isTrue(context.getIsAvailable())) {
				where.append(" and sysOrgElement.fdIsAvailable = true");
			} else {
				where.append(" and sysOrgElement.fdIsAvailable = false");
			}
		}
		hqlInfo.setWhereBlock(StringUtil.linkString(where.toString(), " and ", buildRtnType(context)));
		hqlInfo.setParameter("parent", context.getParent());
		List<SysOrgElement> list = sysOrgElementService.findList(hqlInfo);
		if (CollectionUtils.isNotEmpty(list)) {
			for (SysOrgElement elem : list) {
				array.add(parseElement(elem));
			}
		}
		return array;
	}

	private String buildRtnType(SysSynchroEcoContext context) throws Exception {
		StringBuilder where = new StringBuilder();
		// [{"type":"person"},{"type":"post"},{"type":"dept"}]
		if (StringUtil.isNotNull(context.getReturnOrgType())) {
			try {
				JSONArray types = JSONArray.parseArray(context.getReturnOrgType());
				if (CollectionUtils.isNotEmpty(types)) {
					where.append("sysOrgElement.fdOrgType in (");
					for (int i = 0; i < types.size(); i++) {
						if (i > 0) {
							where.append(", ");
						}
						JSONObject type = types.getJSONObject(i);
						String val = type.getString("type");
						if ("person".equals(val)) {
							where.append("8");
						} else if ("post".equals(val)) {
							where.append("4");
						} else if ("dept".equals(val)) {
							where.append("2");
						}
					}
					where.append(")");
				}
			} catch (Exception e) {
				logger.error("解析返回类型失败：", e);
			}
		}
		return where.toString();
	}

	/**
	 * 构建查询语句
	 * 
	 * <pre>
	 * {
	 *	    "search": {
	 *	        "fdName": "精确搜索字符串",	// 精确
	 *	         "fdName": {
	 *	            "opt": "eq",         		// 精确或模糊：eq/like
	 *	            "value": "搜索内容"      // 查询内容
	 *	        },
	 *	        ......
	 *	    }
	 *	}
	 * </pre>
	 * 
	 * 暂时支持以下属性：
	 * <ul>
	 * <li>fdName：名称（组织、岗位、人员）</li>
	 * <li>fdNo：编号（组织、岗位、人员）</li>
	 * <li>fdLoginName：登录名（人员）</li>
	 * <li>fdMobileNo：手机号（人员）</li>
	 * <li>fdEmail：邮箱（人员）</li>
	 * </ul>
	 * 
	 * @param context
	 * @throws Exception
	 */
	private JSONArray search(SysSynchroEcoContext context) throws Exception {
		JSONArray array = new JSONArray();
		JSONObject search = JSONObject.parseObject(context.getSearch());
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer pWhere = new StringBuffer();
		StringBuffer eWhere = new StringBuffer();
		int index = 0;
		for (String key : search.keySet()) {
			if (eWhere.length() > 0) {
				eWhere.append(" and ");
			}
			if (pWhere.length() > 0) {
				pWhere.append(" and ");
			}
			String param = "param_" + index;
			Object val = search.get(key);
			StringBuffer where = new StringBuffer();
			if (val instanceof Map) {
				Map<String, String> par = (Map) val;
				String opt = par.get("opt");
				String value = par.get("value");
				if ("like".equalsIgnoreCase(opt)) {
					where.append(key).append(" like :").append(param);
					hqlInfo.setParameter(param, "%" + value + "%");
				} else {
					val = value;
				}
			}
			if (where.length() < 1) {
				where.append(key).append(" = :").append(param);
				hqlInfo.setParameter(param, val.toString());
			}
			if ("fdLoginName".equalsIgnoreCase(key) || "fdMobileNo".equalsIgnoreCase(key)
					|| "fdEmail".equalsIgnoreCase(key)) {
				// 人员
				pWhere.append("person.").append(where.toString());
			} else {
				// 通用
				eWhere.append("sysOrgElement.").append(where.toString());
			}
			index++;
		}
		StringBuffer where = new StringBuffer();
		if (eWhere.length() > 0) {
			where.append(eWhere.toString());
		}
		if (pWhere.length() > 0) {
			where.append("sysOrgElement.fdId in (select person.fdId from SysOrgPerson person where ").append(pWhere.toString()).append(")");
		}
		hqlInfo.setWhereBlock(StringUtil.linkString(where.toString(), " and ", buildRtnType(context)));
		List<SysOrgElement> list = sysOrgElementService.findList(hqlInfo);
		if (CollectionUtils.isNotEmpty(list)) {
			for (SysOrgElement elem : list) {
				array.add(parseElement(elem));
			}
		}
		return array;
	}

	/**
	 * 按ID查询
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private JSONObject getById(String id) throws Exception {
		SysOrgElement element = (SysOrgElement) sysOrgElementService.findByPrimaryKey(id, null, true);
		if (element == null) {
			return null;
		}
		if (element.getFdOrgType() == ORG_TYPE_ORG) {
			return parseElementExternal(element);
		} else {
			return parseElement(element);
		}
	}


	/**
	 * 转换组织（组织、岗位、人员）数据
	 * 
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	private JSONObject parseElement(SysOrgElement elem) throws Exception {
		JSONObject data = parseCommonElem(elem);
		if (elem.getFdOrgType() == ORG_TYPE_DEPT) {
			// 负责人
			addArray(data, "admins", elem.getAuthElementAdmins());
			// 组织范围
			addRange(data, "range", elem.getFdRange());
			//组织隐藏范围
			addHideRange(data,"hideRange",elem.getFdHideRange());
			// 扩展属性
			SysOrgElement cate = elem.getFdParentOrg();
			if (cate != null) {
				Map<String, String> dynamicMap = sysOrgElementExternalDeptService.getExtProp(cate.getFdExternal(), elem.getFdId(), false);
				if (cate.getFdExternal() != null) {
					addExtPorps(data, dynamicMap, cate.getFdExternal().getFdDeptProps());
				}
			}
		} else if (elem.getFdOrgType() == ORG_TYPE_POST) {
			// 岗位领导
			data.put(THIS_LEADER, addObject(elem.getHbmThisLeader()));
			// 人员列表
			addArray(data, PERSONS, elem.getFdPersons());
			// 备注
			data.put(MEMO, elem.getFdMemo());
		} else if (elem.getFdOrgType() == ORG_TYPE_PERSON) {
			SysOrgPerson person = (SysOrgPerson) sysOrgElementExternalPersonService.findByPrimaryKey(elem.getFdId());
			// 登录名
			data.put(LOGIN_NAME, person.getFdLoginName());
			// 手机号码
			data.put(MOBILE_NO, person.getFdMobileNo());
			// 邮件地址
			data.put(EMAIL, person.getFdEmail());
			// 所属岗位
			addArray(data, POSTS, person.getFdPosts());
			// 扩展属性
			SysOrgElement cate = elem.getFdParentOrg();
			if (cate != null) {
				Map<String, String> dynamicMap = sysOrgElementExternalPersonService.getExtProp(cate.getFdExternal(), elem.getFdId(), false);
				if (cate.getFdExternal() != null) {
					addExtPorps(data, dynamicMap, cate.getFdExternal().getFdPersonProps());
				}
			}
		}
		return data;
	}

	/**
	 * 转换公共的组织数据
	 * 
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	private JSONObject parseCommonElem(SysOrgElement elem) throws Exception {
		JSONObject json = new JSONObject();
		json.put(ID, elem.getFdId());
		// 类型
		json.put(TYPE, getOrgType(elem.getFdOrgType()));
		// 名称
		json.put(NAME, elem.getFdName());
		// 编号
		json.put(NO, elem.getFdNo());
		// 排序号
		json.put(ORDER, elem.getFdOrder());
		// 所属组织
		json.put(PARENT, addObject(elem.getFdParent()));
		// 所属组织类型
		json.put("cate", addObject(elem.getFdParentOrg()));
		// 是否有效
		json.put(IS_AVAILABLE, elem.getFdIsAvailable());
		// 是否生态
		json.put("isEco", elem.getFdIsExternal());
		return json;
	}

	private String getOrgType(int orgType) {
		String orgWebType = null;
		switch (orgType) {
		case ORG_TYPE_ORG:
			orgWebType = ORG_WEB_TYPE_ORG;
			break;
		case ORG_TYPE_DEPT:
			orgWebType = ORG_WEB_TYPE_DEPT;
			break;
		case ORG_TYPE_GROUP:
			orgWebType = ORG_WEB_TYPE_GROUP;
			break;
		case ORG_TYPE_POST:
			orgWebType = ORG_WEB_TYPE_POST;
			break;
		case ORG_TYPE_PERSON:
			orgWebType = ORG_WEB_TYPE_PERSON;
			break;
		}
		return orgWebType;
	}

	/**
	 * 增加扩展属性值
	 * 
	 * @param json
	 * @param key
	 * @param props
	 */
	private void addExtPorps(JSONObject json, Map<String, String> dynamicMap, List<SysOrgElementExtProp> props) {
		if (CollectionUtils.isNotEmpty(props)) {
			// 属性排序
			Collections.sort(props, new Comparator<SysOrgElementExtProp>() {
				@Override
				public int compare(SysOrgElementExtProp o1, SysOrgElementExtProp o2) {
					Integer num1 = o1.getFdOrder();
					Integer num2 = o2.getFdOrder();
					if (num2 == null) {
                        return 0;
                    }
					if (num1 == null) {
                        return -1;
                    }
					if (num2 < num1) {
                        return 0;
                    } else if (num1.equals(num2)) {
                        return 0;
                    } else {
                        return -1;
                    }
				}
			});
			JSONArray array = new JSONArray();
			for (SysOrgElementExtProp prop : props) {
				if (BooleanUtils.isTrue(prop.getFdStatus())) {
					JSONObject data = new JSONObject();
					// 名称
					data.put("name", prop.getFdName());
					// 属性名
					data.put("field", prop.getFdFieldName());
					// 属性值
					data.put("value", dynamicMap.get(prop.getFdFieldName()));
					if (CollectionUtils.isNotEmpty(prop.getFdFieldEnums()) && data.get("value") != null) {
						StringBuilder sb = new StringBuilder();
						List<String> values = Arrays.asList(data.get("value").toString().split(";"));
						for (SysOrgElementExtPropEnum temp : prop.getFdFieldEnums()) {
							if (values.contains(temp.getFdValue())) {
								if (sb.length() > 0) {
									sb.append(";");
								}
								sb.append(temp.getFdName());
							}
						}
						// 枚举标题
						data.put("text", sb.toString());
					}
					array.add(data);
				}
			}
			json.put("extProps", array);
		}
	}

	/**
	 * 转换组织类型
	 * 
	 * @param external
	 * @return
	 * @throws Exception
	 */
	private JSONObject parseElementExternal(SysOrgElement elem) throws Exception {
		JSONObject json = new JSONObject();
		SysOrgElementExternal external = elem.getFdExternal();
		json.put(ID, elem.getFdId());
		// 名称
		json.put(NAME, elem.getFdName());
		// 是否有效
		json.put(IS_AVAILABLE, elem.getFdIsAvailable());
		// 编号
		json.put(NO, elem.getFdNo());
		// 排序号
		json.put(ORDER, elem.getFdOrder());
		// 管理员
		addArray(json, "admins", elem.getAuthElementAdmins());
		// 组织管理员
		addArray(json, "readers", external.getAuthReaders());
		// 组织范围
		addRange(json, "range", elem.getFdRange());
		// 组织隐藏范围
		addHideRange(json,"hideRange",elem.getFdHideRange());
		// 部门扩展属性
		addExtPorps(json, "deptProps", external.getFdDeptProps());
		// 人员扩展属性
		addExtPorps(json, "personProps", external.getFdPersonProps());
		return json;
	}

	/**
	 * 增加扩展属性
	 * 
	 * @param json
	 * @param key
	 * @param props
	 * @throws Exception
	 */
	private void addExtPorps(JSONObject json, String key, List<SysOrgElementExtProp> props) throws Exception {
		if (CollectionUtils.isNotEmpty(props)) {
			JSONArray array = new JSONArray();
			for (SysOrgElementExtProp prop : props) {
				if (!BooleanUtils.isTrue(prop.getFdStatus())) {
					// 过滤无效属性
					continue;
				}
				JSONObject data = new JSONObject();
				// 属性类型（部门/人员）(dept/person)
				data.put("type", prop.getFdType());
				// 显示名称
				data.put(NAME, prop.getFdName());
				// 排序号
				data.put(ORDER, prop.getFdOrder());
				// 属性名称
				data.put("field", prop.getFdFieldName());
				// 字段名称
				data.put("column", prop.getFdColumnName());
				// 对应数据类型：字符串(java.lang.String)、整数(java.lang.Integer)、浮点(java.lang.Double)、日期(java.util.Date)
				data.put("fieldType", prop.getFdFieldType());
				// 字段长度
				data.put("fieldLength", prop.getFdFieldLength());
				if ("java.lang.Double".equals(prop.getFdFieldType())) {
					// 精度，适用于浮点类型
					data.put("scale", prop.getFdScale());
				}
				// 是否必填
				data.put("required", prop.getFdRequired());
				// 是否启用，默认：启用
				data.put("status", prop.getFdStatus());
				// 是否列表展示
				data.put("showList", prop.getFdShowList());
				// 显示的类型：
				// 字符串：单行文本框(text)、多行文本框(textarea)、单选按钮(radio)、复选框(checkbox)、下拉列表(select)
				// 整数: 单行文本框(text)、单选按钮(radio)、下拉列表(select)
				// 浮点: 单行文本框(text)
				// 日期: 日期时间(datetime)、日期(date)、时间(time)
				data.put("displayType", prop.getFdDisplayType());
				// 枚举集合
				List<SysOrgElementExtPropEnum> enums = prop.getFdFieldEnums();
				if (CollectionUtils.isNotEmpty(enums)) {
					JSONArray _array = new JSONArray();
					for (SysOrgElementExtPropEnum temp : enums) {
						JSONObject _data = new JSONObject();
						// 排序号
						_data.put(ORDER, temp.getFdOrder());
						// 显示名称
						_data.put(NAME, temp.getFdName());
						// 枚举值
						_data.put("value", temp.getFdValue());
						_array.add(_data);
					}
					data.put("fieldEnums", _array);
				}
				array.add(data);
			}
			json.put(key, array);
		}
	}

	/**
	 * 增加查看范围权限
	 * 
	 * @param json
	 * @param prop
	 * @param range
	 * @throws Exception
	 */
	private void addRange(JSONObject json, String key, SysOrgElementRange range) throws Exception {
		if (range != null) {
			JSONObject data = new JSONObject();
			// 状态（是否开启组织成员查看组织范围）
			data.put("state", range.getFdIsOpenLimit());
			// 查看类型
			data.put("type", range.getFdViewType());
			if (2 == range.getFdViewType()) {
				// 查看子类型
				data.put("subType", range.getFdViewSubType());
				// 组织下成员查看范围
				addArray(data, "others", range.getFdOthers());
			}
			// 钉钉邀请成员时地址
			data.put("url", range.getFdInviteUrl());
			json.put(key, data);
		}
	}

	/**
	 * 隐藏范围
	 * @description:
	 * @param json
	 * @param key
	 * @param range
	 * @return: void
	 * @author: wangjf
	 * @time: 2021/9/28 4:24 下午
	 */
	private void addHideRange(JSONObject json, String key, SysOrgElementHideRange range){
		if (range != null) {
			JSONObject data = new JSONObject();
			// 状态（是否开启组织成员查看组织范围）
			data.put("state", range.getFdIsOpenLimit());
			// 查看类型
			data.put("type", range.getFdViewType());
			if (2 == range.getFdViewType()) {
				// 组织下成员查看范围
				addArray(data, "others", range.getFdOthers());
			}
			json.put(key, data);
		}
	}

	/**
	 * 增加列表
	 * 
	 * @param json
	 * @param prop
	 * @param list
	 * @throws Exception
	 */
	private void addArray(JSONObject json, String key, List<SysOrgElement> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			JSONArray array = new JSONArray();
			for (SysOrgElement elem : list) {
				array.add(addObject(elem));
			}
			json.put(key, array);
		}
	}

	/**
	 * 增加对象
	 * 
	 * @param elem
	 * @return
	 */
	private JSONObject addObject(SysOrgElement elem) {
		JSONObject data = new JSONObject();
		if (elem != null) {
			data.put(ID, elem.getFdId());
			data.put(NAME, elem.getFdName());
		}
		return data;
	}

	/**
	 * 获取组织类型
	 * 
	 * @param org
	 * @return
	 * @throws Exception
	 */
	private SysOrgElementExternal getElementExternal(SysEcoOrg org) throws Exception {
		String orgId = null;
		// 查询父组织
		if (org.getParent() != null) {
			SysOrgElement parent = convertObj(org.getParent());
			if (parent != null) {
				if (parent.getFdOrgType() == ORG_TYPE_ORG) {
					orgId = parent.getFdId();
				} else if (parent.getFdParentOrg() != null) {
					orgId = parent.getFdParentOrg().getFdId();
				}
			}
		}
		// 查询本组织
		if (StringUtil.isNull(orgId) && StringUtil.isNotNull(org.getId())) {
			SysOrgElement elem = (SysOrgElement) sysOrgElementService.findByPrimaryKey(org.getId(), null, true);
			if (elem != null && elem.getFdParentOrg() != null) {
				orgId = elem.getFdParentOrg().getFdId();
			}
		}
		if (StringUtil.isNull(orgId)) {
			return null;
		}
		return (SysOrgElementExternal) sysOrgElementExternalService.findByPrimaryKey(orgId);
	}

	/**
	 * 属性合并
	 * 
	 * @param props
	 * @param infos
	 * @return
	 */
	private List<SysEcoExtPorp> merge(List<SysEcoExtPorp> props, List<SysOrgElementExtProp> infos) {
		if (CollectionUtils.isEmpty(props) || CollectionUtils.isEmpty(infos)) {
			return props;
		}
		Map<String, SysOrgElementExtProp> map = new HashMap<String, SysOrgElementExtProp>();
		for (SysOrgElementExtProp prop : infos) {
			map.put(prop.getFdFieldName(), prop);
		}
		// 合并（将属性定义和属性值合并到一起）
		for (SysEcoExtPorp prop : props) {
			SysOrgElementExtProp info = map.get(prop.getField());
			if (info == null) {
				continue;
			}
			prop.setName(info.getFdName());
			prop.setOrder(info.getFdOrder());
			prop.setField(info.getFdFieldName());
			prop.setColumn(info.getFdColumnName());
			prop.setFieldType(info.getFdFieldType());
			prop.setFieldLength(info.getFdFieldLength());
			prop.setScale(info.getFdScale());
			prop.setRequired(info.getFdRequired());
			prop.setStatus(info.getFdStatus());
			prop.setDisplayType(info.getFdDisplayType());

			List<SysOrgElementExtPropEnum> infoEnums = info.getFdFieldEnums();
			if (CollectionUtils.isNotEmpty(infoEnums)) {
				List<SysEcoExtPorpEnum> enums = new ArrayList<SysEcoExtPorpEnum>();
				for (SysOrgElementExtPropEnum infoEnum : infoEnums) {
					SysEcoExtPorpEnum temp = new SysEcoExtPorpEnum();
					temp.setOrder(infoEnum.getFdOrder());
					temp.setName(infoEnum.getFdName());
					temp.setValue(infoEnum.getFdValue());
					enums.add(temp);
				}
				prop.setEnums(enums);
			}
		}
		return props;
	}

	private SysOrgElement convertObj(SysOrgObject obj) throws Exception {
		try {
			if (obj != null && StringUtil.isNotNull(obj.getId())) {
				SysOrgElement elem = (SysOrgElement) sysOrgElementService.findByPrimaryKey(obj.getId());
				if (elem != null && StringUtil.isNotNull(elem.getFdHierarchyId())) {
					return elem;
				}
			}
		} catch (Exception e) {
			logger.warn("获取组织元素失败：", e);
		}
		return null;
	}

	private List<SysOrgElement> convertList(List<SysOrgObject> array) throws Exception {
		List<SysOrgElement> list = new ArrayList<SysOrgElement>();
		if (CollectionUtils.isNotEmpty(array)) {
			for (SysOrgObject obj : array) {
				SysOrgElement elem = convertObj(obj);
				if (elem != null && !list.contains(elem)) {
					list.add(elem);
				}
			}
		}
		return list;
	}

	/**
	 * 外转内
	 * 
	 * 接收参数：
	 * 
	 * <pre>
	 * {
	 *     "orgs": [
	 *         {
	 *             "id": "人员ID",
	 *             "parent": {
	 *                 "id": "上级/所属组织ID（优先级更高，如果该人员没有parent属性，则取公共的parent）"
	 *             }
	 *         }
	 *     ],
	 *     "parent": "上级/所属组织ID（公共，人员列表中未指定具体parent时，统一取该属性）"
	 * }
	 * </pre>
	 * 
	 * 招聘场景中，当应聘人员的入职流程通过后，确认入职就可以将人员由外部转内部，该人员为内部人员。
	 */
	@ResponseBody
	@RequestMapping(value = "/updateOutToIn", method = RequestMethod.POST)
	@Override
	public SysSynchroEcoResult updateOutToIn(@RequestBody SysSynchroEcoContext context) throws Exception {
		logger.debug("生态组织：外转内");
		return convertOrg(context, 1);
	}

	/**
	 * 内转外
	 * 
	 * <pre>
	 * {
	 *     "orgs": [
	 *         {
	 *             "id": "人员ID",
	 *             "parent": {
	 *                 "id": "上级/所属组织ID（优先级更高，如果该人员没有parent属性，则取公共的parent）"
	 *             }
	 *         }
	 *     ],
	 *     "parent": "上级/所属组织ID（公共，人员列表中未指定具体parent时，统一取该属性）"
	 * }
	 * </pre>
	 * 
	 * 伙伴场景中，内部人员离职转做外部合伙人。由此人员需要由内部人员转为外部人员，但人员离职后原提交的、审批的文档记录不可再查看，因此将原人员禁用后，再在新的组织下新建一个相同信息的人员。
	 * 本功能比较慎用，仅提供数据接口给人事业务使用。
	 */
	@ResponseBody
	@RequestMapping(value = "/updateInToOut", method = RequestMethod.POST)
	@Override
	public SysSynchroEcoResult updateInToOut(@RequestBody SysSynchroEcoContext context) throws Exception {
		logger.debug("生态组织：内转外");
		return convertOrg(context, 2);
	}

	/**
	 * 外转外
	 * 
	 * <pre>
	 * {
	 *     "orgs": [
	 *         {
	 *             "id": "人员ID",
	 *             "parent": {
	 *                 "id": "上级/所属组织ID（优先级更高，如果该人员没有parent属性，则取公共的parent）"
	 *             }
	 *         }
	 *     ],
	 *     "parent": "上级/所属组织ID（公共，人员列表中未指定具体parent时，统一取该属性）"
	 * }
	 * </pre>
	 * 
	 * 伙伴也可以转为做外包。
	 */
	@ResponseBody
	@RequestMapping(value = "/updateOutToOut", method = RequestMethod.POST)
	@Override
	public SysSynchroEcoResult updateOutToOut(@RequestBody SysSynchroEcoContext context) throws Exception {
		logger.debug("生态组织：外转外");
		return convertOrg(context, 3);
	}

	/**
	 * 转换处理
	 * 
	 * @param context
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private SysSynchroEcoResult convertOrg(SysSynchroEcoContext context, int type) throws Exception {
		SysSynchroEcoResult result = new SysSynchroEcoResult();
		try {
			List<SysEcoOrg> orgs = context.getOrgs();
			String pid = context.getParent();
			JSONArray errors = new JSONArray();
			JSONArray success = new JSONArray();
			if (CollectionUtils.isNotEmpty(orgs)) {
				for (SysEcoOrg org : orgs) {
					// 检查数据合法性
					JSONObject error = checkProp(org, pid);
					if (error.isEmpty()) {
						// 保存数据
						JSONObject obj = new JSONObject();
						obj.put("id", org.getId());
						obj.put("name", org.getName());
						obj.put("type", org.getType());
						try {
							switch (type) {
							case 1: {
								outToInOrg(org, pid);
								break;
							}
							case 2: {
								inToOutOrg(org, pid);
								break;
							}
							case 3: {
								outToOutOrg(org, pid);
								break;
							}
							}
							success.add(obj);
						} catch (Exception e) {
							logger.warn("", e);
							obj.put("error", e.getMessage());
							errors.add(obj);
						}
					} else {
						errors.add(error);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(errors)) {
				result.setMessage(errors.toString());
			}
			result.setCount(success.size());
			if (success.size() > 0) {
				if (CollectionUtils.isNotEmpty(errors)) {
					// 部分成功
					result.setReturnState(3);
				} else {
					result.setReturnState(OPT_ORG_STATUS_SUCCESS);
				}
				result.setSuccess(success.toString());
			} else {
				result.setReturnState(OPT_ORG_STATUS_FAIL);
			}
		} catch (Exception e) {
			logger.error("生态组织：外转内失败：", e);
			result.setMessage(getErrorMessage(e));
			result.setCount(0);
			result.setReturnState(OPT_ORG_STATUS_FAIL);
		}

		return result;
	}

	/**
	 * 外转内（单人）
	 * 
	 * @param org
	 * @param pid
	 * @throws Exception
	 */
	private void outToInOrg(SysEcoOrg org, String pid) throws Exception {
		SysOrgPerson person = (SysOrgPerson) sysOrgElementExternalPersonService.findByPrimaryKey(org.getId(), null,
				true);
		if (person != null) {
			SysOrgPerson old = SysOrgEcoUtil.cloneEcoOrg(person);
			// 设置为内部
			person.setFdIsExternal(false);
			// 设置有效
			person.setFdIsAvailable(true);
			// 设置所属部门
			person.setFdParent(getParent(org, pid));
			SysOrgUtil.paraMethod.set(ResourceUtil.getString("sys-organization:sysOrgElementExternal.outToIn"));
			// 保存变更日志
			SysOrgEcoUtil.addOrgModifyLog(old, person, null, null, false);
			sysOrgPersonService.update(person);
		} else {
			throw new Exception("人员不存在[id=" + org.getId() + "]");
		}
	}

	/**
	 * 内转外（单人）
	 * 
	 * 先禁用原来的内部账号，再“克隆”一个外部账号
	 * 
	 * @param org
	 * @param pid
	 * @throws Exception
	 */
	private void inToOutOrg(SysEcoOrg org, String pid) throws Exception {
		SysOrgPerson person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(org.getId(), null, true);
		if (person != null) {
			if (BooleanUtils.isTrue(person.getFdIsExternal())) {
				throw new Exception("该人员已经属于外部人员，不能重复调整[id=" + org.getId() + ", name=" + person.getFdName() + "]");
			}
			String loginName = person.getFdLoginName();
			List<SysOrgElement> list = sysOrgElementExternalPersonService.findList(
					"fdIsAvailable = true and fdIsExternal = true and fdLoginName = '" + loginName + "'", null);
			SysEcoOrg newPerson = new SysEcoOrg();
			if (CollectionUtils.isNotEmpty(list)) {
				SysOrgElement elem = list.get(0);
				newPerson.setId(elem.getFdId());
			}
			// 复制原来的基础信息
			newPerson.setType("person");
			newPerson.setName(person.getFdName());
			newPerson.setNo(person.getFdNo());
			newPerson.setOrder(person.getFdOrder());
			newPerson.setIsAvailable(true);
			newPerson.setLoginName(person.getFdLoginName());
			newPerson.setMobileNo(person.getFdMobileNo());
			newPerson.setEmail(person.getFdEmail());
			newPerson.setParent(org.getParent());

			// 先禁用原来的内部账号
			if (BooleanUtils.isTrue(person.getFdIsAvailable())) {
				// 设置无效
				person.setFdIsAvailable(false);
				sysOrgPersonService.update(person);
			}
			SysOrgUtil.paraMethod.set(ResourceUtil.getString("sys-organization:sysOrgElementExternal.inToOut"));
			// 保存用户
			savePerson(newPerson);
			// 更新密码(复制原账号密码，只能通过SQL修复)
			String updatePwd = "update sys_org_person set fd_password = :password, fd_init_password = :initPassword where fd_id = :id";
			sysOrgPersonService.getBaseDao().getHibernateSession().createNativeQuery(updatePwd)
					.setParameter("password", person.getFdPassword())
					.setParameter("initPassword", person.getFdInitPassword()).setParameter("id", newPerson.getId())
					.executeUpdate();
		} else {
			throw new Exception("人员不存在[id=" + org.getId() + "]");
		}
	}

	/**
	 * 外转外（单人）
	 * 
	 * @param org
	 * @param pid
	 * @throws Exception
	 */
	private void outToOutOrg(SysEcoOrg org, String pid) throws Exception {
		// 查询人员原始信息
		SysOrgPerson person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(org.getId(), null, true);
		if (person != null) {
			// 设置所属组织
			if (org.getParent() == null || StringUtil.isNull(org.getParent().getId())) {
				org.setParent(new SysOrgObject(pid));
			}
			// 保留以下原始信息
			org.setIsAvailable(true);
			org.setName(person.getFdName());
			org.setNo(person.getFdNo());
			org.setOrder(person.getFdOrder());
			org.setLoginName(person.getFdLoginName());
			org.setMobileNo(person.getFdMobileNo());
			org.setEmail(person.getFdEmail());
			// 调用外部人员保存接口
			SysOrgUtil.paraMethod.set(ResourceUtil.getString("sys-organization:sysOrgElementExternal.outToOut"));
			savePerson(org);
		} else {
			throw new Exception("人员不存在[id=" + org.getId() + "]");
		}
	}

	private JSONObject checkProp(SysEcoOrg org, String pid) throws Exception {
		JSONObject error = new JSONObject();
		// 检查上级组织
		SysOrgElement parent = getParent(org, pid);
		if (parent == null) {
			error.put("parent", "上级组织不存在");
			return error;
		}
		JSONObject json = new JSONObject();
		if (!error.isEmpty()) {
			json.put("id", org.getId());
			json.put("name", org.getName());
			json.put("error", error);
		}
		return json;
	}

	private SysOrgElement getParent(SysEcoOrg org, String pid) throws Exception {
		SysOrgObject parentObj = org.getParent();
		if (parentObj == null && StringUtil.isNotNull(pid)) {
			parentObj = new SysOrgObject(pid);
		}
		if (parentObj != null) {
			return convertObj(parentObj);
		}
		return null;
	}

}
