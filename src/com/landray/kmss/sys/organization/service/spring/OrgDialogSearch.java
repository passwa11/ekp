package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.intercept.RoleValidator;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.eco.AuthOrgRange;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.eco.SysOrgShowRange;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.*;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.RestResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@SuppressWarnings("unchecked")
@Controller
@RequestMapping(value = "/data/sys-organization/organizationDialogSearch", method = RequestMethod.POST)
public class OrgDialogSearch implements IXMLDataBean, SysOrgConstant {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/**
	 * 组织架构搜索限制返回条数
	 */
	public static int LIMIT_SEARCH_RESULT_SIZE = SysOrgUtil.LIMIT_RESULT_SIZE;

	private ISysOrgElementService sysOrgElementService;

	private ISysOrgGroupService sysOrgGroupService;

	public ISysOrgGroupService getSysOrgGroupService() {
		return sysOrgGroupService;
	}

	public void setSysOrgGroupService(ISysOrgGroupService sysOrgGroupService) {
		this.sysOrgGroupService = sysOrgGroupService;
	}

	private ISysOrgRoleService sysOrgRoleService;

	public ISysOrgRoleService getSysOrgRoleService() {
		return sysOrgRoleService;
	}

	public void setSysOrgRoleService(ISysOrgRoleService sysOrgRoleService) {
		this.sysOrgRoleService = sysOrgRoleService;
	}

	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	public ISysOrganizationStaffingLevelService getSysOrganizationStaffingLevelService() {
		return sysOrganizationStaffingLevelService;
	}

	public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}

	private IOrgRangeService orgRangeService;

	public void setOrgRangeService(IOrgRangeService orgRangeService) {
		this.orgRangeService = orgRangeService;
	}

	@ResponseBody
	@RequestMapping("getDataList")
	public RestResponse<?> getDataList(@RequestBody Map<String, Object> vo, HttpServletRequest request) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
		return RestResponse.ok(getDataList(new RequestContext(wrapper)));
	}

	@Override
	public List getDataList(RequestContext xmlContext) throws Exception {
		// 判断是否有扩充的配置，注意扩充时必须将该部分代码删除
		IXMLDataBean extension = OrgDialogUtil.getExtension("search");
		if (extension != null && extension != this) {
			return extension.getDataList(xmlContext);
		}

		String id = xmlContext.getParameter("id");
		String ids = xmlContext.getParameter("ids");

		//#122853 业务建模模块，表单配置地址本如果多选，映射到系统内数据，ids格式是["","",""]和其他格式不匹配，导致打开地址本失败
		if(ids!=null&&ids.contains("[")&&ids.contains("]")) {
			ids=ids.replace("[", "").replace("]", "").replace(", ", ";");
		}

		// #44956范围搜索
		String range = xmlContext.getParameter("range");
		if (StringUtil.isNotNull(id)) {
			// 根据ID表查找
			SysOrgElement elem = (SysOrgElement) sysOrgElementService
					.findByPrimaryKey(id, null, true);
			List rtnMapList = new ArrayList();
			HashMap node = new HashMap();
			node.put("id", id);
			node.put("info", OrgDialogUtil.getDescriptInfo(elem));
			node.put("isAvailable", elem.getFdIsAvailable().toString());
			node.put("isExternal", elem.getFdIsExternal().toString());

			if (elem.getFdOrgType().equals(ORG_TYPE_PERSON)) {
				OrgDialogUtil.setPersonAttrs(elem, xmlContext.getContextPath(),
						node);
			}

			rtnMapList.add(node);
			return rtnMapList;
		} else if (StringUtil.isNotNull(ids)) {
			String[] ids_array = ids.trim().split(";");
			List elemList = new ArrayList();
			if (ids_array != null && ids_array.length > 0) {
				List<String> list = Arrays.asList(ids_array);
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setModelName("com.landray.kmss.sys.organization.model.SysOrgElement");
				hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("sysOrgElement.fdId",list));
				elemList = sysOrgElementService.findList(hqlInfo);
			}
			if (elemList.size() == 0) {
				Map<String, String> temp = new HashMap();
				elemList.add(temp);
				return elemList;
			}
			// #168003 按传入的ID顺序进行排序
			Map<String, Object> temp = new HashMap<>(16);
			for(Object obj : elemList) {
				SysOrgElement elem = (SysOrgElement) obj;
				temp.put(elem.getFdId(), elem);
			}
			String contextPath = xmlContext.getContextPath();
			List list = new ArrayList();
			for (String fdId : ids_array) {
				Object obj = temp.get(fdId);
				if (obj != null) {
					list.add(OrgDialogUtil.getResultEntry((SysOrgElement) obj, contextPath));
				}
			}
			return list;
		} else if ("true".equals(range)) {
			// #44956范围搜索
			return getDataListByRange(xmlContext);
		} else {
			// 根据关键字查找
			return getDataListByKey(xmlContext);
		}
	}

	/**
	 * 范围搜索
	 *
	 * @param xmlContext
	 * @return
	 * @throws Exception
	 */
	private List getDataListByRange(RequestContext xmlContext) throws Exception {
		List elemList = new ArrayList();
		String fdName = xmlContext.getParameter("fdName");
		String fdDeptName = xmlContext.getParameter("fdDeptName");
		String fdHasChild = xmlContext.getParameter("fdHasChild");
		if (StringUtil.isNotNull(fdName) || StringUtil.isNotNull(fdDeptName)) {
			HQLInfo hqlInfo = new HQLInfo();
			String modelTable = "sysOrgElement";
			String authCheckType = "DIALOG_READER";
			ISysOrgElementService service = sysOrgElementService;
			int orgType = ORG_TYPE_ALLORG;
			String para = xmlContext.getParameter("orgType");
			try {
				orgType = Integer.parseInt(para);
			} catch (NumberFormatException e) {

			}
			boolean isFilter = false;
			if ((orgType & ORG_TYPE_ROLE) == ORG_TYPE_ROLE) {
				orgType = orgType & (ORG_TYPE_ROLE | ORG_FLAG_ALL);
				service = sysOrgRoleService;
				modelTable = "sysOrgRole";
				authCheckType = "DIALOG_ROLE_READER";
				isFilter = true;
			} else if ((orgType & ORG_TYPE_GROUP) == ORG_TYPE_GROUP) {
				orgType = orgType & (ORG_TYPE_GROUP | ORG_FLAG_ALL);
				service = sysOrgGroupService;
				modelTable = "sysOrgGroup";
				authCheckType = "DIALOG_GROUP_READER";
				isFilter = true;
			}
			StringBuffer whereBlock = new StringBuffer();
			whereBlock.append("(lower(" + modelTable + ".fdName) like :fdName"
					+ " or " + modelTable + ".fdNamePinYin like :fdName"
					+ " or " + modelTable + ".fdNameSimplePinyin like :fdName");
			if ((orgType & ORG_TYPE_PERSON) == ORG_TYPE_PERSON) {
				whereBlock.append(" or lower(" + modelTable
						+ ".fdLoginName) like :fdName" + " or lower("
						+ modelTable + ".fdNickName) like :fdName"
						+ " or (lower(fdStaffingLevel.fdName) like :fdName"
						+ " )");

				hqlInfo.setJoinBlock("left join " + modelTable
						+ ".fdStaffingLevel fdStaffingLevel");
			}
			String fdName_lang = SysLangUtil.getLangFieldName("fdName");
			if (!"fdName".equals(fdName_lang)) {
				whereBlock.append(" or lower(" + modelTable + "." + fdName_lang
						+ ") like :fdName");
			}
			if (StringUtil.isNotNull(fdDeptName)) {
				whereBlock.append(") and (" + modelTable
						+ ".hbmParent.fdId is not null and ");
			} else {
				whereBlock.append(") and (" + modelTable
						+ ".hbmParent.fdId is null or ");
			}
			whereBlock
					.append(modelTable
							+ ".hbmParent.fdId in (Select fdId from com.landray.kmss.sys.organization.model.SysOrgElement sysOrgElement where sysOrgElement.fdName like :fdDeptName))");
			hqlInfo.setParameter("fdDeptName", "%" + fdDeptName + "%");
			// 过滤内外组织
			String isExternal = xmlContext.getParameter("isExternal");
			buildSearchHQLInfo(hqlInfo, whereBlock.toString(), null, orgType, modelTable, isExternal);
			if((StringUtil.isNotNull(fdDeptName)&& "true".equals(fdHasChild))){
				//开启搜索子部门或子机构
				hqlInfo.setParameter("fdDeptName", "%" + "" + "%");
			}else{
				hqlInfo.setParameter("fdDeptName", "%" + fdDeptName + "%");
			}
			hqlInfo.setParameter("fdName", "%" + fdName.toLowerCase() + "%");
			hqlInfo.setAuthCheckType(authCheckType);
			boolean isReturnHierarchyId = false;
			String returnHierarchyId = xmlContext
					.getParameter("returnHierarchyId");
			if ("true".equals(returnHierarchyId)) {
				isReturnHierarchyId = true;
			}
			elemList = sysOrganizationStaffingLevelService
					.getStaffingLevelFilterResult(service.findList(hqlInfo));
			elemList = OrgDialogUtil.getResultEntries(elemList, xmlContext.getContextPath(), isReturnHierarchyId);
			if((StringUtil.isNotNull(fdDeptName)&& "true".equals(fdHasChild))){
				//开启搜索子部门或子机构
				List rangeList = new ArrayList();
				for (int i = 0; i < elemList.size(); i++) {
					HashMap<String, String> elem = (HashMap<String, String>) elemList.get(i);
					String info = elem.get("info");
					if(StringUtil.isNotNull(info)){
						String[] infoChls = info.split(" - ");
						if(StringUtil.isNotNull(infoChls[1])){
							if(infoChls[1].indexOf(fdDeptName)!=-1){
								rangeList.add(elem);
							}
						}
					}
				}
				return rangeList;
			}
		}
		return elemList;
	}

	/**
	 * 根据关键字搜索
	 *
	 * @param xmlContext
	 * @return
	 * @throws Exception
	 */
	private List getDataListByKey(RequestContext xmlContext) throws Exception {
		// 准备参数
		String para = xmlContext.getParameter("startWith");
		List startList = null;
		if (StringUtil.isNotNull(para)) {
			startList = sysOrgElementService.findByPrimaryKeys(para.split(";"));
		}

		String deptLimit = xmlContext.getParameter("deptLimit");
		if (StringUtil.isNotNull(deptLimit) && !"undefined".equals(deptLimit)) {
			Set<SysOrgElement> set = OrgDialogUtil.getDeptLimitOrg(deptLimit);
			if (set != null && !set.isEmpty()) {
				startList = new ArrayList<SysOrgElement>();
				for (SysOrgElement e : set) {
					startList.add(e);
				}
			} else {
				return new ArrayList();
			}
		}

		int orgType = ORG_TYPE_ALLORG;
		para = xmlContext.getParameter("orgType");
		if (StringUtil.isNotNull(para)) {
			try {
				orgType = Integer.parseInt(para);
			} catch (NumberFormatException e) {
			}
		}

		String[] keys = xmlContext.getParameter("key").split("\\s*[;；]\\s*");

		String modelTable = "sysOrgElement";
		String authCheckType = "DIALOG_READER";
		boolean isFilter = false;
		ISysOrgElementService service = sysOrgElementService;
		if ((orgType & ORG_TYPE_ROLE) == ORG_TYPE_ROLE) {
			orgType = orgType & (ORG_TYPE_ROLE | ORG_FLAG_ALL);
			service = sysOrgRoleService;
			modelTable = "sysOrgRole";
			authCheckType = "DIALOG_ROLE_READER";
			isFilter = true;
		} else if ((orgType & ORG_TYPE_GROUP) == ORG_TYPE_GROUP) {
			orgType = orgType & (ORG_TYPE_GROUP | ORG_FLAG_ALL);
			service = sysOrgGroupService;
			modelTable = "sysOrgGroup";
			authCheckType = "DIALOG_GROUP_READER";
			isFilter = true;
		}

		boolean isReturnHierarchyId = false;
		String returnHierarchyId = xmlContext.getParameter("returnHierarchyId");
		if ("true".equals(returnHierarchyId)) {
			isReturnHierarchyId = true;
		}

		if (!"true".equals(xmlContext.getParameter("accurate"))) {
			// 模糊查询
			StringBuffer whereBf = new StringBuffer();
			HQLInfo hqlInfo = new HQLInfo();
			HQLInfo info = new HQLInfo();
			int i = 0;
			for (String key : keys) {
				if (StringUtil.isNull(key)) {
					continue;
				}
				whereBf.append(" or lower(" + modelTable + ".fdName) like :key"
						+ i);
				if ((orgType & ORG_TYPE_PERSON) == ORG_TYPE_PERSON) {
					whereBf.append(" or lower(" + modelTable
							+ ".fdLoginName) like :key" + i + " or lower("
							+ modelTable + ".fdNickName) like :key" + i
							+ " or (" +modelTable + ".fdMobileNo) like :key"+i
							+ " or (" +modelTable + ".fdNo) like :key"+i
							+ " or (lower(fdStaffingLevel.fdName) like :key" + i
							+ " )");
					hqlInfo.setJoinBlock("left join " + modelTable
							+ ".fdStaffingLevel fdStaffingLevel");
				}
				whereBf.append(" or " + modelTable + ".fdNamePinYin like :key"
						+ i);
				// 简拼搜索
				whereBf.append(" or " + modelTable
						+ ".fdNameSimplePinyin like :key" + i);
				String fdName_lang = SysLangUtil.getLangFieldName("fdName");
				if (!"fdName".equals(fdName_lang)) {
					whereBf.append(" or lower(" + modelTable + "."
							+ fdName_lang + ") like :key" + i);
				}
				hqlInfo.setParameter("key" + i, "%" + key.toLowerCase() + "%");
				info.setParameter("key" + i, "%" + key.toLowerCase() + "%");
				i++;
			}
			if (whereBf.length() == 0) {
				return new ArrayList();
			}

			String where = whereBf.substring(4);

			// 过滤内外组织
			String isExternal = xmlContext.getParameter("isExternal");
			if (isFilter) {
				isExternal = null;
			}
			buildSearchHQLInfo(hqlInfo, where, startList, orgType, modelTable, isExternal);
			// #102613 地址本优化
			hqlInfo.setRowSize(SysOrgUtil.LIMIT_RESULT_SIZE + 1);
			hqlInfo.setCacheable(true);
			hqlInfo.setGetCount(false);
			hqlInfo.setAuthCheckType(authCheckType);
			List elemList = service.findPage(hqlInfo).getList();
			// 本部门搜索时支持搜索部门下岗位人员
			if ("myDept".equals(deptLimit) && !ArrayUtil.isEmpty(startList)
					&& (orgType & ORG_TYPE_PERSON) == ORG_TYPE_PERSON) {
				List hierarchyIds = new ArrayList();
				for (int j = 0; j < startList.size(); j++) {
					SysOrgElement element = (SysOrgElement) startList.get(j);
					int orgtype = element.getFdOrgType().intValue();
					if (orgtype == ORG_TYPE_DEPT) {
						hierarchyIds.add(element.getFdHierarchyId());
					}
				}
				hierarchyIds = SysOrgHQLUtil.formatHierarchyIdList(hierarchyIds);
				info.setRowSize(50);
				info.setCacheable(true);
				info.setGetCount(false);
				StringBuffer bf = new StringBuffer();
				for (int j = 0; j < hierarchyIds.size(); j++) {
					String hId = hierarchyIds.get(j).toString();
					bf.append(
							" or sysOrgPost.fdHierarchyId like '" + hId + "%'");
				}
				String deptWhere = StringUtil
						.linkString(StringUtil.isNull(where) ? null : "("
								+ where + ")", " and ", bf.length() > 0 ? "("
								+ bf.substring(4) + ")" : null);
				info.setJoinBlock("left join " + modelTable
						+ ".hbmPosts sysOrgPost left join " + modelTable
						+ ".fdStaffingLevel fdStaffingLevel");
				info.setWhereBlock(
						SysOrgHQLUtil.buildWhereBlock(orgType, deptWhere,
								modelTable));
				info.setAuthCheckType(authCheckType);
				List list = service.findPage(info).getList();
				for (int j = 0; j < list.size(); j++) {
					SysOrgElement org = ((SysOrgElement) list.get(j));
					if (org.getFdIsAvailable() && org.getFdIsBusiness()
							&& elemList != null && elemList.size() < 100
							&& !elemList.contains(org)) {
						elemList.add(org);
					}
				}
			}

			elemList = sysOrganizationStaffingLevelService
					.getStaffingLevelFilterResult(elemList);
			if (CollectionUtils.isEmpty(elemList)) {
				return new ArrayList();
			}
			// 返回到地址本时，如果不是"仅自己"时，且可见范围不包含"自己"时，需要过滤
			AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
			if (!orgRange.isSelf()) {
				String userId = UserUtil.getKMSSUser().getUserId();
				boolean isIgnore = true;
				// 可见人员是否包含自己
				Set<String> rootPersonIds = orgRange.getRootPersonIds();
				if (!rootPersonIds.contains(userId)) {
					String hierarchyId = UserUtil.getKMSSUser().getFdHierarchyId();
					// 可见组织是否包含自己
					Set<SysOrgShowRange> authRanges = new HashSet<>(orgRange.getAuthRanges());
					authRanges.addAll(orgRange.getAuthOtherRanges());
					if (authRanges.isEmpty()) {
						isIgnore = false;
					} else {
						for (SysOrgShowRange range : authRanges) {
							if (hierarchyId.startsWith(range.getFdHierarchyId())) {
								// 可见组织里，有包含我的组织
								isIgnore = false;
								break;
							}
						}
					}
				}
				if (isIgnore) {
					for (int j = 0; j < elemList.size(); j++) {
						SysOrgElement element = (SysOrgElement) elemList.get(j);
						if (element.getFdId().equals(userId) && element.getFdParent() != null) {
							elemList.remove(element);
						}
					}
				}
			}
			return OrgDialogUtil.getSearchResultEntries(elemList, xmlContext
					.getContextPath(), isReturnHierarchyId);
		}

		// 精确查询
		ArrayList rtnMapList = new ArrayList();
		int j = 0;
		for (String key : keys) {
			if (StringUtil.isNull(key)) {
				continue;
			}
			HQLInfo hqlInfo = new HQLInfo();
			String whereBlock = modelTable + ".fdName = :key" + j + " or "
					+ modelTable + ".fdNamePinYin = :key" + j + " or "
					+ modelTable + ".fdNameSimplePinyin = :key" + j; // 简拼搜索
			String fdName_lang = SysLangUtil.getLangFieldName("fdName");
			if (!"fdName".equals(fdName_lang)) {
				whereBlock += " or " + modelTable + "." + fdName_lang
						+ " like :key" + j;
			}
			if ((orgType & ORG_TYPE_PERSON) == ORG_TYPE_PERSON) {
				whereBlock += " or " + modelTable + ".fdLoginName = :key" + j
						+ " or " + modelTable + ".fdNickName = :key" + j
						+ " or (lower(fdStaffingLevel.fdName) = :key" + j
						+ " )";

				hqlInfo.setJoinBlock("left join " + modelTable
						+ ".fdStaffingLevel fdStaffingLevel");
			}

			// 过滤内外组织
			String isExternal = xmlContext.getParameter("isExternal");
			buildSearchHQLInfo(hqlInfo, whereBlock, startList, orgType,
					modelTable, isExternal);
			hqlInfo.setParameter("key" + j, key);
			j++;
			hqlInfo.setAuthCheckType(authCheckType);
			List result = service.findList(hqlInfo);

			result = sysOrganizationStaffingLevelService
					.getStaffingLevelFilterResult(result);
			if (result.isEmpty()) {
				Map entry = new HashMap();
				entry.put("key", key);
				rtnMapList.add(entry);
			} else {
				for (int i = 0; i < result.size(); i++) {
					SysOrgElement elem = (SysOrgElement) result.get(i);
					if (checkExceptIds(elem.getFdHierarchyId())) {
						continue;
					}
					Map entry = OrgDialogUtil.getResultEntry(elem, xmlContext
							.getContextPath(), isReturnHierarchyId);
					entry.put("key", key);
					rtnMapList.add(entry);
				}
			}
		}
		return rtnMapList;
	}

	/**
	 * 构造HQLInfo的where与order by
	 *
	 * @param whereBlock
	 * @param startList
	 * @param orgType
	 * @return
	 */
	private void buildSearchHQLInfo(HQLInfo hqlInfo, String whereBlock,
									List startList, int orgType, String modelTable, String isExternal) {
		if (startList != null) {
			whereBlock = SysOrgHQLUtil.buildAllChildrenWhereBlock(startList,
					whereBlock, modelTable);
		}
		whereBlock = SysOrgHQLUtil.buildWhereBlock(orgType, whereBlock,
				modelTable);
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			if (StringUtil.isNotNull(isExternal) && !"null".equals(isExternal)) {
				whereBlock = StringUtil.linkString(StringUtil.isNull(whereBlock) ? null : "(" + whereBlock + ")",
						" and ", modelTable + ".fdIsExternal = :fdIsExternal");
				hqlInfo.setParameter("fdIsExternal", BooleanUtils.toBoolean(isExternal));
			}
		} else {
			// 未开启生态组织，使用默认值
			whereBlock = StringUtil.linkString(StringUtil.isNull(whereBlock) ? null : "(" + whereBlock + ")", " and ",
					"(" + modelTable + ".fdIsExternal is null or " + modelTable + ".fdIsExternal = false)");
		}
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(modelTable + ".fdOrgType desc, " + modelTable + "."
				+ SysLangUtil.getLangFieldName("fdName"));
	}

	private boolean checkExceptIds(String hId) {
		return ArrayUtil.isListIntersect(OrgDialogUtil.getExceptIdList(),
				Arrays.asList(hId.split(BaseTreeConstant.HIERARCHY_ID_SPLIT)));
	}

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	private ISysOrganizationVisibleService sysOrganizationVisibleService;

	public ISysOrganizationVisibleService getSysOrganizationVisibleService() {
		if (sysOrganizationVisibleService == null) {
			sysOrganizationVisibleService = (ISysOrganizationVisibleService) SpringBeanUtil
					.getBean("sysOrganizationVisibleService");
		}
		return sysOrganizationVisibleService;
	}

	private RoleValidator roleValidator;

	public RoleValidator getRoleValidator() {
		if (roleValidator == null) {
			roleValidator = (RoleValidator) SpringBeanUtil
					.getBean("roleValidator");
		}
		return roleValidator;
	}

}
