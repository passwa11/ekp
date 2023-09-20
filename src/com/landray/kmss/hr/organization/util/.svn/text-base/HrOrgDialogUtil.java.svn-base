package com.landray.kmss.hr.organization.util;

import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.hr.organization.constant.HrOrgConstant;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.organization.model.SysOrganizationConfig;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HrOrgDialogUtil implements HrOrgConstant {

	private static IHrStaffPersonInfoService hrStaffPersonInfoService = (IHrStaffPersonInfoService) SpringBeanUtil
			.getBean("hrStaffPersonInfoService");

	public static void setPersonAttrs(HrOrganizationElement elem,
			String contextPath, HashMap map) throws Exception {
		String img = PersonInfoServiceGetter
				.getPersonHeadimageUrl(elem.getFdId());
		if (!PersonInfoServiceGetter.isFullPath(img)) {
			img = contextPath + img;
		}
		map.put("img", img);

		String staffingLevel = "";

		HrStaffPersonInfo person = (HrStaffPersonInfo) hrStaffPersonInfoService
				.findByPrimaryKey(elem.getFdId());

		// 智能应用组件选择组织架构人员使用此参数
		map.put("uid", person.getFdLoginName());

		SysOrganizationStaffingLevel sysOrganizationStaffingLevel = person
				.getFdStaffingLevel();
		if (sysOrganizationStaffingLevel != null) {
			staffingLevel = sysOrganizationStaffingLevel.getFdName();
		}
		map.put("staffingLevel", staffingLevel);

		// 针对无效的组织，避免出现重名无法区分，需要返回一些额外的信息（如编号，登录名），由于地址本原因，这里的额外信息使用parentName属性（原无效组织不存在parent信息）
		if (person.getFdIsAvailable() == null || !person.getFdIsAvailable()) {
			map.put("staffingLevel", person.getFdNo());
			map.put("parentName", person.getFdLoginName());
		}
	}

	public static List getResultEntries(List elemList, String contextPath) throws Exception {
		List entries = new ArrayList();
		for (int i = 0; i < elemList.size(); i++) {
			entries.add(getResultEntry((HrOrganizationElement) elemList.get(i), contextPath));
		}
		return entries;
	}

	/**
	 * 获取单项返回值
	 * 
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getResultEntry(HrOrganizationElement elem) throws Exception {
		Map<String, String> entry = new HashMap<String, String>();
		entry.put("id", elem.getFdId());
		entry.put("name", elem.getFdName());
		entry.put("info", getDescriptInfo(elem));
		entry.put("orgType", elem.getFdOrgType().toString());
		return entry;
	}

	public static Map<String, String> getResultEntry(HrOrganizationElement elem, String contextPath) throws Exception {
		return getResultEntry(elem, contextPath, false);
	}

	public static Map<String, String> getResultEntry(HrOrganizationElement elem, String contextPath,
			boolean returnHierarchyId) throws Exception {
		HashMap<String, String> entry = new HashMap<String, String>();
		entry.put("id", elem.getFdId());
		entry.put("name", elem.getFdName());
		entry.put("info", getDescriptInfo(elem));
		entry.put("orgType", elem.getFdOrgType().toString());
		entry.put("isAvailable", elem.getFdIsAvailable().toString());
		if (returnHierarchyId) {
			entry.put("hierarchyId", elem.getFdHierarchyId());
			HrOrganizationElement parent = elem.getFdParent();
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
		if (elem.getFdOrgType().equals(HR_TYPE_PERSON) || elem.getFdOrgType().equals(HR_TYPE_POST)
				|| elem.getFdOrgType().equals(HR_TYPE_ORG) || elem.getFdOrgType().equals(HR_TYPE_DEPT)) {
			entry.put("parentNames",
					StringUtil.isNotNull(elem.getFdParentsName("_")) ? elem.getFdParentsName("_") : "");
		}

		if (elem.getFdOrgType().equals(HR_TYPE_PERSON)) {
			setPersonAttrs(elem, contextPath, entry);
		}

		return entry;
	}
	
	
	/**
	 * 获取一个组织架构元素的描述信息
	 * 
	 * @param elem
	 * @return
	 * @throws Exception
	 */
	public static String getDescriptInfo(HrOrganizationElement elem) throws Exception {
		if (elem == null) {
			return ResourceUtil.getString("sysOrg.address.info.noFound", "sys-organization");
		}
		int orgType = elem.getFdOrgType().intValue();
		StringBuffer rtnVal = new StringBuffer();
		if (StringUtil.isNotNull(elem.getFdName())) {
			rtnVal.append(elem.getFdName());
		}
		String path = null;
		switch (orgType) {
		case HR_TYPE_PERSON:
			if (!elem.getFdIsAvailable()) { // 无效人员，提示格式：名称（登录名）_编号（人员）
				HrStaffPersonInfo person = (HrStaffPersonInfo) hrStaffPersonInfoService.findByPrimaryKey(elem.getFdId());
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
		case HR_TYPE_POST:
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
				List<HrStaffPersonInfo> personInfos =new ArrayList<HrStaffPersonInfo>();
				if(CollectionUtils.isNotEmpty(elem.getFdPersons())){
					for (Object personObj:elem.getFdPersons() ) {
						if(personObj instanceof HrStaffPersonInfo) {
							HrStaffPersonInfo personInfo =((HrStaffPersonInfo) personObj);
							if (Boolean.TRUE.equals(personInfo.getFdIsAvailable())) {
								personInfos.add(personInfo);
							}
						}
					}
				}
				rtnVal.append("（").append(ArrayUtil.joinProperty(personInfos, "fdName", "; ")[0]).append("）");
			}
			break;
		case HR_TYPE_DEPT:
			if (!elem.getFdIsAvailable() && StringUtil.isNotNull(elem.getFdNo())) {
				rtnVal.append(" _ ").append(elem.getFdNo());
			}
			rtnVal.append("（").append(ResourceUtil.getString("sysOrgElement.dept", "sys-organization")).append("）");
			path = elem.getFdParentsName("_");
			if (StringUtil.isNotNull(path)) {
				rtnVal.append(" - ").append(path);
			}
			break;
		case HR_TYPE_ORG:
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

	public static List getSearchResultEntries(List elemList, String contextPath, boolean returnHierarchyId)
			throws Exception {
		List entries = new ArrayList();
		for (int i = 0; i < elemList.size(); i++) {
			entries.add(getSearchResultEntry((HrOrganizationElement) elemList.get(i), contextPath, returnHierarchyId));
		}
		return entries;
	}

	public static Map<String, String> getSearchResultEntry(HrOrganizationElement elem, String contextPath,
			boolean returnHierarchyId) throws Exception {
		HashMap<String, String> entry = new HashMap<String, String>();
		entry.put("id", elem.getFdId());
		entry.put("name", elem.getDeptLevelNames());
		entry.put("info", getDescriptInfo(elem));
		entry.put("orgType", elem.getFdOrgType().toString());
		entry.put("isAvailable", elem.getFdIsAvailable().toString());
		if (returnHierarchyId) {
			entry.put("hierarchyId", elem.getFdHierarchyId());
			HrOrganizationElement parent = elem.getFdParent();
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

		if (elem.getFdOrgType().equals(HR_TYPE_PERSON)) {
			setPersonAttrs(elem, contextPath, entry);
		}

		return entry;
	}

	public static Set<HrOrganizationElement> getDeptLimitOrg(String deptLimit) throws Exception {
		Set<HrOrganizationElement> set = new HashSet<HrOrganizationElement>();
		/*SysOrgPerson user = UserUtil.getUser();
		if (user == null) {
			return set;
		}
		List<SysOrgElement> posts = user.getFdPosts();*/
		HrOrganizationElement element = null;
		/*if ("myDept".equals(deptLimit)) {
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
		} else {*/
			IHrOrganizationElementService hrOrganizationElementService = (IHrOrganizationElementService) SpringBeanUtil
					.getBean("hrOrganizationElementService");
			element = (HrOrganizationElement) hrOrganizationElementService.findByPrimaryKey(deptLimit);
		set.add(element);
		/*}*/
		return removeDeptChildren(set);
	}
	
	private static Set<HrOrganizationElement> removeDeptChildren(Set<HrOrganizationElement> set) {
		if (set == null) {
			return null;
		}
		Map<String, HrOrganizationElement> map = new HashMap<String, HrOrganizationElement>();
		for (HrOrganizationElement e : set) {
			map.put(e.getFdHierarchyId(), e);
		}
		Set<HrOrganizationElement> result_ele = new HashSet<HrOrganizationElement>();
		Set<String> hIds = filterSubordinateOrgId(map.keySet());
		for (String hId : hIds) {
			result_ele.add(map.get(hId));
		}

		return result_ele;
	}

	/**
	 * 获取不显示的组织架构ID列表
	 * 
	 * @return
	 */

	public static List getExceptIdList() {
		return new ArrayList();
	}

	public static List getResultEntries(List elemList, String contextPath, boolean returnHierarchyId) throws Exception {
		List entries = new ArrayList();
		for (int i = 0; i < elemList.size(); i++) {
			entries.add(getResultEntry((HrOrganizationElement) elemList.get(i), contextPath, returnHierarchyId));
		}
		return entries;
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

	public static String getDeptLevelNames(HrOrganizationElement orgElem) {
		// 获取系统组织架构基本设置信息
		BaseAppConfig sysOrgCon = null;
		try {
			sysOrgCon = new SysOrganizationConfig();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String display = sysOrgCon.getDataMap()
				.get("kmssOrgAddressDeptLevelDisplay");
		// 显示最近的N级部门
		String displayLength = sysOrgCon.getDataMap()
				.get("kmssOrgAddressDeptLevelDisplayLength");

		return orgElem.getDeptLevelNames(Integer.parseInt(display),
				Integer.parseInt(displayLength));
	}
}
