package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.model.*;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.xform.util.SysFormDingUtil;
import com.landray.kmss.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;

import java.util.*;

@SuppressWarnings("unchecked")
public class OrgDialogUtil implements SysOrgConstant {
	private static final String EXTENSION_POINT = "com.landray.kmss.sys.organization.address";

	private static final String EXTENSION_PARAM_NAME = "beanName";

	private static IOrgRangeService orgRangeService;

	private static ISysOrgElementService sysOrgElementService;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(OrgDialogUtil.class);

	private static ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	public static IOrgRangeService getOrgRangeService() {
		if (orgRangeService == null) {
			orgRangeService = (IOrgRangeService) SpringBeanUtil.getBean("orgRangeService");
		}
		return orgRangeService;
	}

	/**
	 * 获取地址本相关的扩展点
	 *
	 * @param item
	 * @return
	 */
	public static IXMLDataBean getExtension(String item) {
		IExtension extension = Plugin.getExtension(EXTENSION_POINT, "*", item);
		if (extension == null) {
			return null;
		}
		return (IXMLDataBean) Plugin.getParamValue(extension, EXTENSION_PARAM_NAME);
	}

	/**
	 * 获取不显示的组织架构ID列表
	 *
	 * @return
	 */

	public static List getExceptIdList() {
		return new ArrayList();
		// if (exceptIdList == null) {
		// String exceptIdStr = ResourceUtil
		// .getKmssConfigString("kmss.org.notDisplayIds");
		// if (StringUtil.isNull(exceptIdStr)) {
		// exceptIdList = new ArrayList();
		// } else {
		// exceptIdList = Arrays.asList(exceptIdStr.split("\\s*[,;]\\s*"));
		// }
		// }
		// return exceptIdList;
	}

	/**
	 * 获取一个组织架构元素的描述信息
	 *
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	public static String getDescriptInfo(SysOrgElement elem) throws Exception {
		if (elem == null) {
			return ResourceUtil.getString("sysOrg.address.info.noFound", "sys-organization");
		}
		int orgType = elem.getFdOrgType().intValue();
		StringBuffer rtnVal = new StringBuffer();
		// 之前的初始化方法会出现空指针异常
		rtnVal.append(elem.getFdName());
		String path = null;
		switch (orgType) {
			case ORG_TYPE_ROLE:
				SysOrgRole role = (SysOrgRole) getSysOrgElementService().format(elem);
				if (role.getFdRoleConf() != null) {
					rtnVal.append("（").append(ResourceUtil.getString("sysOrgElement.roleLine", "sys-organization") + "："
							+ role.getFdRoleConf().getFdName() + "）");
				} else {
					rtnVal.append("（").append(ResourceUtil.getString("sysOrgElement.role", "sys-organization") + "）");
				}
				if (StringUtil.isNotNull(elem.getFdMemo())) {
					rtnVal.append(" - ").append(elem.getFdMemo());
				}
				break;
			case ORG_TYPE_GROUP:
				rtnVal.append("（").append(ResourceUtil.getString("sysOrgElement.group", "sys-organization") + "）");
				if (StringUtil.isNotNull(elem.getFdMemo())) {
					rtnVal.append(" - ").append(elem.getFdMemo());
				}
				break;
			case ORG_TYPE_PERSON:
				if (!elem.getFdIsAvailable()) { // 无效人员，提示格式：名称（登录名）_编号（人员）
					SysOrgPerson person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(elem.getFdId());
					// 登录名
					rtnVal.append("（").append(person.getFdLoginName()).append("）_ ");
					// 编号
					if (StringUtil.isNotNull(person.getFdNo())) {
						rtnVal.append(person.getFdNo());
					}
					rtnVal.append("（").append(ResourceUtil.getString("sysOrgElement.person", "sys-organization"))
							.append("）");
				} else { // 有效人员
					rtnVal.append("（").append(ResourceUtil.getString("sysOrgElement.person", "sys-organization"))
							.append("） - ");
					// 部门
					path = elem.getFdParentsName("_");
					if (StringUtil.isNotNull(path)) {
						rtnVal.append(path);
					}
				}
				break;
			case ORG_TYPE_POST:
				if (!elem.getFdIsAvailable() && StringUtil.isNotNull(elem.getFdNo())) {
					rtnVal.append(" _ ").append(elem.getFdNo());
				}
				rtnVal.append("（").append(ResourceUtil.getString("sysOrgElement.post", "sys-organization")).append("） - ");
				path = elem.getFdParentsName("_");
				if (StringUtil.isNotNull(path)) {
					rtnVal.append(path);
				}
				if (elem.getFdPersons().isEmpty()) {
					rtnVal.append("（").append(ResourceUtil.getString("sysOrg.address.info.postIsEmpty", "sys-organization"))
							.append("）");
				} else {
					rtnVal.append("（").append(ArrayUtil.joinProperty(elem.getFdPersons(), "fdName", "; ")[0]).append("）");
				}
				break;
			case ORG_TYPE_DEPT:
				if (!elem.getFdIsAvailable() && StringUtil.isNotNull(elem.getFdNo())) {
					rtnVal.append(" _ ").append(elem.getFdNo());
				}
				rtnVal.append("（").append(ResourceUtil.getString("sysOrgElement.dept", "sys-organization")).append("）");
				path = elem.getFdParentsName("_");
				if (StringUtil.isNotNull(path)) {
					rtnVal.append(" - ").append(path);
				}
				break;
			case ORG_TYPE_ORG:
				rtnVal.append("（").append(ResourceUtil.getString("sysOrgElement.org", "sys-organization")).append("）");
				path = elem.getFdParentsName("_");
				if (StringUtil.isNotNull(path)) {
					rtnVal.append(" - ").append(path);
				}
				break;
		}
		if (!elem.getFdIsAvailable()) {
			rtnVal.append(" - ").append(ResourceUtil.getString("sysOrg.address.info.disable", "sys-organization"));
		}
		return rtnVal.toString();
	}

	/**
	 * 获取单项返回值
	 *
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getResultEntry(SysOrgElement elem) throws Exception {
		Map<String, String> entry = new HashMap<String, String>();
		entry.put("id", elem.getFdId());
		entry.put("name", elem.getFdName());
		entry.put("info", getDescriptInfo(elem));
		entry.put("orgType", elem.getFdOrgType().toString());
		entry.put("dingImg", SysFormDingUtil.getEnableDing());
		return entry;
	}

	public static Map<String, String> getResultEntry(SysOrgElement elem, String contextPath) throws Exception {
		return getResultEntry(elem, contextPath, false);
	}

	/**
	 * 获取返回值列表
	 *
	 * @param elemList
	 * @return
	 * @throws Exception
	 */
	public static List getResultEntries(List elemList) throws Exception {
		List entries = new ArrayList();
		for (int i = 0; i < elemList.size(); i++) {
			entries.add(getResultEntry((SysOrgElement) elemList.get(i)));
		}
		return entries;
	}

	public static List getResultEntries(List elemList, String contextPath) throws Exception {
		List entries = new ArrayList();
		for (int i = 0; i < elemList.size(); i++) {
			entries.add(getResultEntry((SysOrgElement) elemList.get(i), contextPath));
		}
		return entries;
	}

	public static Map<String, String> getResultEntry(SysOrgElement elem, String contextPath, boolean returnHierarchyId)
			throws Exception {
		HashMap<String, String> entry = new HashMap<String, String>();
		entry.put("id", elem.getFdId());
		entry.put("name", OrgDialogUtil.getDeptLevelNames(elem));
		entry.put("info", getDescriptInfo(elem));
		entry.put("orgType", elem.getFdOrgType().toString());
		entry.put("isAvailable", elem.getFdIsAvailable().toString());
		entry.put("isExternal", elem.getFdIsExternal().toString());
		//钉钉端打开并且开启了钉钉开关
		entry.put("dingImg", SysFormDingUtil.getEnableDing());
		if (returnHierarchyId) {
			entry.put("hierarchyId", elem.getFdHierarchyId());
			SysOrgElement parent = elem.getFdParent();
			String hierarchyIdComplete = BaseTreeConstant.HIERARCHY_ID_SPLIT + elem.getFdId()
					+ BaseTreeConstant.HIERARCHY_ID_SPLIT;
			while (parent != null) {
				hierarchyIdComplete = BaseTreeConstant.HIERARCHY_ID_SPLIT + parent.getFdId() + hierarchyIdComplete;
				parent = parent.getFdParent();
			}
			entry.put("hierarchyIdComplete", hierarchyIdComplete);
		}
		String parentName = elem.getFdParent() == null ? "" : elem.getFdParent().getDeptLevelNames();
		entry.put("parentName", parentName);

		// 针对无效的组织，避免出现重名无法区分，需要返回一些额外的信息（如编号），由于地址本原因，这里的额外信息使用parentName属性（原无效组织不存在parent信息）
		if (elem.getFdIsAvailable() == null || !elem.getFdIsAvailable()) {
			entry.put("parentName", elem.getFdNo());
		}

		// 部门全路径
		if (elem.getFdOrgType().equals(ORG_TYPE_PERSON)
				|| elem.getFdOrgType().equals(ORG_TYPE_POST)
				|| elem.getFdOrgType().equals(ORG_TYPE_ORG)
				|| elem.getFdOrgType().equals(ORG_TYPE_DEPT)) {
			entry.put("parentNames",
					StringUtil.isNotNull(elem.getFdParentsName("_"))
							? elem.getFdParentsName("_") : "");
		}

		if (elem.getFdOrgType().equals(ORG_TYPE_PERSON)) {
			setPersonAttrs(elem, contextPath, entry);
		}

		return entry;
	}

	public static List getResultEntries(List elemList, String contextPath, boolean returnHierarchyId) throws Exception {
		List entries = new ArrayList();
		for (int i = 0; i < elemList.size(); i++) {
			entries.add(getResultEntry((SysOrgElement) elemList.get(i), contextPath, returnHierarchyId));
		}
		return entries;
	}

	private static ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
			.getBean("sysOrgPersonService");

	public static void setPersonAttrs(SysOrgElement elem, String contextPath, HashMap map) throws Exception {
		String img = PersonInfoServiceGetter.getPersonHeadimageUrl(elem.getFdId());
		if (!PersonInfoServiceGetter.isFullPath(img)) {
			img = contextPath + img;
		}
		map.put("img", img);

		// 钉钉头像
		if (elem.getFdOrgType() == SysOrgConstant.ORG_TYPE_PERSON
				&& "true".equals(SysFormDingUtil.getEnableDing())) {
			String personDingHeadimage = PersonInfoServiceGetter
					.getPersonDingHeadimage(elem.getFdId(),
							null);
			map.put("img", personDingHeadimage);
		}

		String staffingLevel = "";
		String showStaffingLevel = new SysOrganizationConfig().getShowStaffingLevel();

		SysOrgPerson person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(elem.getFdId());

		// 智能应用组件选择组织架构人员使用此参数
		map.put("uid", person.getFdLoginName());

		if ("true".equals(showStaffingLevel)) {
			SysOrganizationStaffingLevel sysOrganizationStaffingLevel = person.getFdStaffingLevel();
			if (sysOrganizationStaffingLevel != null) {
				staffingLevel = sysOrganizationStaffingLevel.getFdName();
			}
		}
		map.put("staffingLevel", staffingLevel);

		// 针对无效的组织，避免出现重名无法区分，需要返回一些额外的信息（如编号，登录名），由于地址本原因，这里的额外信息使用parentName属性（原无效组织不存在parent信息）
		if (person.getFdIsAvailable() == null || !person.getFdIsAvailable()) {
			map.put("staffingLevel", person.getFdNo());
			map.put("parentName", person.getFdLoginName());
		}
	}

	private static Set<String> filterSubordinateOrgId(Set<String> visibleOrgHierarchyIds) {
		if (visibleOrgHierarchyIds == null) {
			return null;
		}
		Set<String> visibleOrgs_result_tmp = new HashSet<String>();
		Set<String> visibleOrgs_add = new HashSet<String>();
		Set<String> visibleOrgs_del = new HashSet<String>();
		for (String hierarchyId1 : visibleOrgHierarchyIds) {
			boolean add = true;
			for (String hierarchyId2 : visibleOrgs_result_tmp) {
				if (hierarchyId2.startsWith(hierarchyId1)) {
					add = false;
					visibleOrgs_del.add(hierarchyId2);
					visibleOrgs_add.add(hierarchyId1);
					continue;
				} else if (hierarchyId1.startsWith(hierarchyId2)) {
					add = false;
					break;
				}
			}
			if (add) {
				visibleOrgs_result_tmp.add(hierarchyId1);
			} else {
				visibleOrgs_result_tmp.removeAll(visibleOrgs_del);
				visibleOrgs_result_tmp.addAll(visibleOrgs_add);
				visibleOrgs_del.clear();
				visibleOrgs_add.clear();
			}
		}
		return visibleOrgs_result_tmp;

	}

	public static Set<String> getRootOrgIds(String deptLimit) {
		try {
			Set<SysOrgElement> set = getDeptLimitOrg(deptLimit);
			Set<String> result_hId = getRootOrgIds(set);
			Set<String> result_id = new HashSet<String>();
			for (String id : result_hId) {
				if (!id.contains("x")) {
					result_id.add(id);
					continue;
				}
				id = id.substring(0, id.length() - 1);
				id = id.substring(id.lastIndexOf("x") + 1);
				result_id.add(id);
			}
			return result_id;
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
	}

	private static Set<SysOrgElement> removeDeptChildren(Set<SysOrgElement> set) {
		if (set == null) {
			return null;
		}
		// Set<SysOrgElement> result = new HashSet<SysOrgElement>();
		// Set<String> hireIds = new HashSet<String>();
		Map<String, SysOrgElement> map = new HashMap<String, SysOrgElement>();
		for (SysOrgElement e : set) {
			map.put(e.getFdHierarchyId(), e);
		}
		Set<SysOrgElement> result_ele = new HashSet<SysOrgElement>();
		Set<String> hIds = filterSubordinateOrgId(map.keySet());
		for (String hId : hIds) {
			result_ele.add(map.get(hId));
		}

		return result_ele;
	}

	private static Set<String> getRootOrgIds(Set<SysOrgElement> set) {
		if (set == null) {
			return null;
		}
		Set<String> visibleIds_not_org = new HashSet<String>();
		Set<String> visibleIds_org = new HashSet<String>();
		for (SysOrgElement e : set) {
			// if (e.getFdOrgType() == 1) {
			// visibleIds_org.add(SysOrgUtil.buildHierarchyIdIncludeOrg(e));
			// } else {
			// visibleIds_not_org.add(e.getFdId());
			// }
			visibleIds_org.add(SysOrgUtil.buildHierarchyIdIncludeOrg(e));
		}
		Set<String> ids = filterSubordinateOrgIdHandleBrokenLink(visibleIds_org);
		if (ids != null) {
			visibleIds_not_org.addAll(ids);
		}

		return visibleIds_not_org;
	}

	public static Set<SysOrgElement> getDeptLimitOrg(String deptLimit) throws Exception {
		Set<SysOrgElement> set = new HashSet<SysOrgElement>();
		SysOrgPerson user = UserUtil.getUser();
		if (user == null) {
			return set;
		}
		List<SysOrgElement> posts = user.getFdPosts();
		SysOrgElement element = null;
		if ("myDept".equals(deptLimit)) {
			element = user.getFdParent();
			if (element != null) {
				set.add(element);
			}
			if (posts != null && !posts.isEmpty()) {
				for (SysOrgElement post : posts) {
					SysOrgElement post_parent = post.getFdParent();
					if (post_parent != null) {
						set.add(post_parent);
					}
				}
			}
		} else if ("myOrg".equals(deptLimit)) {
			element = user.getFdParentOrg();
			if (element != null) {
				set.add(element);
			}
			if (posts != null && !posts.isEmpty()) {
				for (SysOrgElement post : posts) {
					SysOrgElement post_parent = post.getFdParentOrg();
					if (post_parent != null) {
						set.add(post_parent);
					}
				}
			}
		} else if(StringUtil.isNotNull(deptLimit) && deptLimit.startsWith("otherOrgDept")) {//其他机构或者部门，格式是otherOrgDept#id1;id2...
			ISysOrgElementService sysOrgElementService = (ISysOrgElementService) SpringBeanUtil
					.getBean("sysOrgElementService");
			//解析deptLimit，获取id数组
			String prefix = "otherOrgDept-";
			deptLimit = deptLimit.substring(prefix.length());
			String[] ids = deptLimit.split(";");
			List parentIds = getCurUserOrgDeptIds();
			//遍历
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				if(parentIds.contains(id)) {//排除本部门和本机构
					continue;
				}
				// 排除自己(当限定的组织中包含!号时，表示排除自己，只取子组织)
				boolean excludeSelf = id.indexOf("!") > -1;
				if (excludeSelf) {
					id = id.replaceAll("!", "");
				}
				element = (SysOrgElement) sysOrgElementService.findByPrimaryKey(id,null,true);
				if(element != null){
					if (excludeSelf) {
						List<SysOrgElement> children = element.getFdChildren();
						if (CollectionUtils.isNotEmpty(children)) {
							for (SysOrgElement child : children) {
								// 获取有效且与业务相关的组织
								if (BooleanUtils.isTrue(child.getFdIsAvailable())
										&& BooleanUtils.isTrue(child.getFdIsBusiness())) {
									set.add(child);
								}
							}
						}
					} else {
						set.add(element);
					}
				}
			}
			if (set.isEmpty()) {
				// 如果有限制范围，但是又找到限制组织，只能限制到自己
				set.add(user);//#161615 无论怎么限制组织，自己都是可以看到自己的
			}
		} else {
			// 排除自己(当限定的组织中包含!号时，表示排除自己，只取子组织)
			boolean excludeSelf = deptLimit.indexOf("!") > -1;
			if (excludeSelf) {
				deptLimit = deptLimit.replaceAll("!", "");
			}
			ISysOrgElementService sysOrgElementService = (ISysOrgElementService) SpringBeanUtil
					.getBean("sysOrgElementService");
			element = (SysOrgElement) sysOrgElementService.findByPrimaryKey(deptLimit);
			if (excludeSelf) {
				List<SysOrgElement> children = element.getFdChildren();
				if (CollectionUtils.isNotEmpty(children)) {
					for (SysOrgElement child : children) {
						// 获取有效且与业务相关的组织
						if (BooleanUtils.isTrue(child.getFdIsAvailable())
								&& BooleanUtils.isTrue(child.getFdIsBusiness())) {
							set.add(child);
						}
					}
				}
			} else {
				set.add(element);
			}
		}
		return removeDeptChildren(set);
	}

	public static List<String> getCurUserOrgDeptIds(){
		//获取当前用户部门的ids
		List<SysOrgElement> posts = UserUtil.getUser().getFdPosts();
		SysOrgElement element = UserUtil.getUser().getFdParent();
		List<String> parentIds = new ArrayList<String>();
		if (element != null) {
			parentIds.add(element.getFdId());
		}
		if (posts != null && !posts.isEmpty()) {
			for (SysOrgElement post : posts) {
				SysOrgElement post_parent = post.getFdParent();
				if (post_parent != null) {
					parentIds.add(post_parent.getFdId());
				}
			}
		}
		//获取当前用户机构id
		SysOrgElement org = UserUtil.getUser().getFdParentOrg();
		if(org != null) {
			parentIds.add(org.getFdId());
		}
		if (posts != null && !posts.isEmpty()) {
			for (SysOrgElement post : posts) {
				SysOrgElement post_parent = post.getFdParentOrg();
				if (post_parent != null) {
					parentIds.add(post_parent.getFdId());
				}
			}
		}
		return parentIds;
	}

	public static Set<String> getIntersection(String deptLimit, Set<String> visibleIds) throws Exception {
		SysOrgPerson user = UserUtil.getUser();
		if (user == null) {
			return null;
		}
		Set<SysOrgElement> set = getDeptLimitOrg(deptLimit);
		Set<String> limit_hId = getRootOrgIds(set);
		if (limit_hId == null || limit_hId.isEmpty()) {
			return visibleIds;
		}

		// 如果当前用户是内部或外部管理员，需要将限定的组织相应的加入到可见的组织ID中
		for (SysOrgElement e : set) {
			if (SysOrgEcoUtil.isExternal(e)) {
				visibleIds.add(e.getFdId());
			}
		}

		if (visibleIds == null) {
			Set<String> result_id = new HashSet<String>();
			for (String id : limit_hId) {
				if (!id.contains("x")) {
					result_id.add(id);
					continue;
				}
				id = id.substring(0, id.length() - 1);
				id = id.substring(id.lastIndexOf("x") + 1);
				result_id.add(id);
			}
			return result_id;
		}

		Set<String> visible_hId = new HashSet<String>();
		for (String id : visibleIds) {
			SysOrgElement e = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(id, null, true);
			visible_hId.add(SysOrgUtil.buildHierarchyIdIncludeOrg(e));
		}

		Set<String> result_hId = new HashSet<String>();
		for (String v_hId : visible_hId) {
			for (String l_hId : limit_hId) {
				if (v_hId.equals(l_hId)) {
					result_hId.add(v_hId);
					break;
				} else if (v_hId.startsWith(l_hId)) {
					result_hId.add(v_hId);
					break;
				} else if (l_hId.startsWith(v_hId)) {
					result_hId.add(l_hId);
					continue;
				}
			}
		}

		Set<String> result_id = new HashSet<String>();
		for (String id : result_hId) {
			if (!id.contains("x")) {
				result_id.add(id);
				continue;
			}
			id = id.substring(0, id.length() - 1);
			id = id.substring(id.lastIndexOf("x") + 1);
			result_id.add(id);
		}
		return result_id;
	}

	private static Set<String> filterSubordinateOrgIdHandleBrokenLink(Set<String> visibleOrgHierarchyIds) {
		if (visibleOrgHierarchyIds == null) {
			return null;
		}
		List<String> visibleOrgHierarchyIdsList = new ArrayList<String>();
		visibleOrgHierarchyIdsList.addAll(visibleOrgHierarchyIds);
		// 按字符串长度排序，确保父级节点在前面
		Collections.sort(visibleOrgHierarchyIdsList, new Comparator() {

			@Override
			public int compare(Object arg0, Object arg1) {
				String s1 = (String) arg0;
				String s2 = (String) arg1;
				if (s1.length() > s2.length()) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		Set<String> visibleOrgs_result_tmp = new HashSet<String>();
		Set<String> subLinkIds = new HashSet<String>();
		// Set<String> visibleOrgs_del = new HashSet<String>();
		while (visibleOrgHierarchyIdsList.size() > 0) {
			String hierarchyId2 = visibleOrgHierarchyIdsList.remove(0);
			String parentIdsStr = hierarchyId2.substring(0, hierarchyId2.length() - 1);
			parentIdsStr = parentIdsStr.substring(0, parentIdsStr.lastIndexOf("x") + 1);
			// 判断上级节点是否在可见组织里面，如果有，表示不会断链，如果没有则表示断链
			if (!subLinkIds.contains(parentIdsStr)) {
				// 确认断链
				visibleOrgs_result_tmp.add(hierarchyId2);
			}
			subLinkIds.add(hierarchyId2);
		}

		// Set<String> visibleOrgs_result = new HashSet<String>();
		// for (String id : visibleOrgs_result_tmp) {
		// id = id.substring(0, id.length() - 1);
		// id = id.substring(id.lastIndexOf("x") + 1);
		// visibleOrgs_result.add(id);
		// }
		return visibleOrgs_result_tmp;

	}

	public static String getDeptLevelNames(SysOrgElement orgElem) {
		// 获取系统组织架构基本设置信息
		BaseAppConfig sysOrgCon = null;
		try {
			sysOrgCon = new SysOrganizationConfig();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String display = sysOrgCon.getDataMap().get("kmssOrgAddressDeptLevelDisplay");
		// 显示最近的N级部门
		String displayLength = sysOrgCon.getDataMap().get("kmssOrgAddressDeptLevelDisplayLength");

		return orgElem.getDeptLevelNames(Integer.parseInt(display), Integer.parseInt(displayLength));
	}

	public static List getSearchResultEntries(List elemList, String contextPath, boolean returnHierarchyId)
			throws Exception {
		List entries = new ArrayList();
		for (int i = 0; i < elemList.size(); i++) {
			entries.add(getSearchResultEntry((SysOrgElement) elemList.get(i), contextPath, returnHierarchyId));
		}
		return entries;
	}

	public static Map<String, String> getSearchResultEntry(SysOrgElement elem, String contextPath,
														   boolean returnHierarchyId) throws Exception {
		HashMap<String, String> entry = new HashMap<String, String>();
		entry.put("id", elem.getFdId());
		entry.put("name", elem.getDeptLevelNames());
		entry.put("info", getDescriptInfo(elem));
		entry.put("orgType", elem.getFdOrgType().toString());
		entry.put("isAvailable", elem.getFdIsAvailable().toString());
		entry.put("isExternal", elem.getFdIsExternal().toString());
		entry.put("dingImg", SysFormDingUtil.getEnableDing());
		if (returnHierarchyId) {
			entry.put("hierarchyId", elem.getFdHierarchyId());
			SysOrgElement parent = elem.getFdParent();
			String hierarchyIdComplete = BaseTreeConstant.HIERARCHY_ID_SPLIT + elem.getFdId()
					+ BaseTreeConstant.HIERARCHY_ID_SPLIT;
			while (parent != null) {
				hierarchyIdComplete = BaseTreeConstant.HIERARCHY_ID_SPLIT + parent.getFdId() + hierarchyIdComplete;
				parent = parent.getFdParent();
			}
			entry.put("hierarchyIdComplete", hierarchyIdComplete);
		}
		String parentName = elem.getFdParent() == null ? "" : elem.getFdParent().getDeptLevelNames();
		entry.put("parentName", parentName);

		// 针对无效的组织，避免出现重名无法区分，需要返回一些额外的信息（如编号），由于地址本原因，这里的额外信息使用parentName属性（原无效组织不存在parent信息）
		if (elem.getFdIsAvailable() == null || !elem.getFdIsAvailable()) {
			entry.put("parentName", elem.getFdNo());
		}

		if (elem.getFdOrgType().equals(ORG_TYPE_PERSON)) {
			setPersonAttrs(elem, contextPath, entry);
		}

		return entry;
	}

}
