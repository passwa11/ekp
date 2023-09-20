package com.landray.kmss.hr.organization.service.spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.hr.organization.constant.HrOrgConstant;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.organization.util.HrOrgDialogUtil;
import com.landray.kmss.sys.authentication.intercept.RoleValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.service.ISysOrganizationVisibleService;
import com.landray.kmss.sys.organization.service.spring.OrgDialogUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class HrOrgDialogSearch implements IXMLDataBean, HrOrgConstant {

	private IHrOrganizationElementService hrOrganizationElementService;

	public IHrOrganizationElementService getHrOrganizationElementService() {
		return hrOrganizationElementService;
	}

	public void setHrOrganizationElementService(IHrOrganizationElementService hrOrganizationElementService) {
		this.hrOrganizationElementService = hrOrganizationElementService;
	}

	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	public ISysOrganizationStaffingLevelService getSysOrganizationStaffingLevelService() {
		return sysOrganizationStaffingLevelService;
	}

	public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}

	private ISysOrganizationVisibleService sysOrganizationVisibleService;

	public void setSysOrganizationVisibleService(ISysOrganizationVisibleService sysOrganizationVisibleService) {
		this.sysOrganizationVisibleService = sysOrganizationVisibleService;
	}

	private RoleValidator roleValidator;

	public void setRoleValidator(RoleValidator roleValidator) {
		this.roleValidator = roleValidator;
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
		// #44956范围搜索
		String range = xmlContext.getParameter("range");
		if (StringUtil.isNotNull(id)) {
			// 根据ID表查找
			HrOrganizationElement elem = (HrOrganizationElement) hrOrganizationElementService.findByPrimaryKey(id, null,
					true);
			List rtnMapList = new ArrayList();
			HashMap node = new HashMap();
			node.put("id", id);
			node.put("info", HrOrgDialogUtil.getDescriptInfo(elem));
			node.put("isAvailable", elem.getFdIsAvailable().toString());

			if (elem.getFdOrgType().equals(HR_TYPE_PERSON)) {
				HrOrgDialogUtil.setPersonAttrs(elem, xmlContext.getContextPath(), node);
			}

			rtnMapList.add(node);
			return rtnMapList;
		} else if (StringUtil.isNotNull(ids)) {
			String[] ids_array = ids.trim().split(";");
			List elemList = hrOrganizationElementService.findByPrimaryKeys(ids_array);
			return HrOrgDialogUtil.getResultEntries(elemList, xmlContext.getContextPath());
		} else if ("true".equals(range)) {
			// #44956范围搜索
			return getDataListByRange(xmlContext);
		} else {
			// 根据关键字查找
			return getDataListByKey(xmlContext);
		}
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
			startList = hrOrganizationElementService.findByPrimaryKeys(para.split(";"));
		}

		String deptLimit = xmlContext.getParameter("deptLimit");
		if (StringUtil.isNotNull(deptLimit) && !"undefined".equals(deptLimit)) {
			Set<HrOrganizationElement> set = HrOrgDialogUtil.getDeptLimitOrg(deptLimit);
			if (set != null && !set.isEmpty()) {
				startList = new ArrayList<HrOrganizationElement>();
				for (HrOrganizationElement e : set) {
					startList.add(e);
				}
			} else {
				return new ArrayList();
			}
		}

		int orgType = HR_TYPE_ALLORG;
		para = xmlContext.getParameter("orgType");
		if (StringUtil.isNotNull(para)) {
			try {
				orgType = Integer.parseInt(para);
			} catch (NumberFormatException e) {
			}
		}

		String[] keys = xmlContext.getParameter("key").split("\\s*[;；]\\s*");

		String modelTable = "hrOrganizationElement";
		String authCheckType = "DIALOG_READER";
		IHrOrganizationElementService service = hrOrganizationElementService;

		boolean isReturnHierarchyId = false;
		String returnHierarchyId = xmlContext.getParameter("returnHierarchyId");
		if ("true".equals(returnHierarchyId)) {
			isReturnHierarchyId = true;
		}

		Set<String> elementIds = getLimitIds(xmlContext);
		boolean visible_role = checkVisibleRole();

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
				whereBf.append(" or lower(" + modelTable + ".fdName) like :key" + i);
				if ((orgType & HR_TYPE_PERSON) == HR_TYPE_PERSON) {
					whereBf.append(" or lower(" + modelTable + ".fdLoginName) like :key" + i
							/*"+ or lower(" + modelTable
								+ ".fdNickName) like :key" + i +*/
							+" or lower(staffinglevel.fdName) like :key" + i);
					hqlInfo.setJoinBlock("left join " + modelTable + ".fdStaffingLevel staffinglevel");
				}
				whereBf.append(" or " + modelTable + ".fdNamePinYin like :key" + i);
				// 简拼搜索
				whereBf.append(" or " + modelTable + ".fdNameSimplePinyin like :key" + i);
				String fdName_lang = SysLangUtil.getLangFieldName("fdName");
				if (!"fdName".equals(fdName_lang)) {
					whereBf.append(" or lower(" + modelTable + "." + fdName_lang + ") like :key" + i);
				}
				hqlInfo.setParameter("key" + i, "%" + key.toLowerCase() + "%");
				info.setParameter("key" + i, "%" + key.toLowerCase() + "%");
				i++;
			}
			if (whereBf.length() == 0) {
				return new ArrayList();
			}

			String where = whereBf.substring(4);

			buildSearchHQLInfo(hqlInfo, where, startList, orgType, modelTable);
			// 性能优化，这里只查询100条数据
			hqlInfo.setRowSize(101);
			hqlInfo.setCacheable(true);
			hqlInfo.setGetCount(false);
			hqlInfo.setAuthCheckType(authCheckType);
			List elemList = service.findPage(hqlInfo).getList();
			// 本部门搜索时支持搜索部门下岗位人员
			if ("myDept".equals(deptLimit) && !ArrayUtil.isEmpty(startList)
					&& (orgType & HR_TYPE_PERSON) == HR_TYPE_PERSON) {
				List hierarchyIds = new ArrayList();
				for (int j = 0; j < startList.size(); j++) {
					HrOrganizationElement element = (HrOrganizationElement) startList.get(j);
					int orgtype = element.getFdOrgType().intValue();
					if (orgtype == HR_TYPE_DEPT) {
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
					bf.append(" or sysOrgPost.fdHierarchyId like '" + hId + "%'");
				}
				String deptWhere = StringUtil.linkString(StringUtil.isNull(where) ? null : "(" + where + ")", " and ",
						bf.length() > 0 ? "(" + bf.substring(4) + ")" : null);
				info.setJoinBlock("left join " + modelTable + ".hbmPosts sysOrgPost left join " + modelTable
						+ ".fdStaffingLevel staffinglevel");
				info.setWhereBlock(SysOrgHQLUtil.buildWhereBlock(orgType, deptWhere, modelTable));
				info.setAuthCheckType(authCheckType);
				List list = service.findPage(info).getList();
				for (int j = 0; j < list.size(); j++) {
					HrOrganizationElement org = ((HrOrganizationElement) list.get(j));
					if (org.getFdIsAvailable() && org.getFdIsBusiness() && elemList != null && elemList.size() < 100
							&& !elemList.contains(org)) {
						elemList.add(org);
					}
				}
			}
			if (SysOrgUtil.isOrgAeraEnable()) {
				elemList = filter(elementIds, elemList, visible_role);
			}

			elemList = sysOrganizationStaffingLevelService.getStaffingLevelFilterResult(elemList);
			// elemList = sysOrgElementService.findList(hqlInfo);
			return HrOrgDialogUtil.getSearchResultEntries(elemList, xmlContext.getContextPath(), isReturnHierarchyId);
		}

		// 精确查询
		ArrayList rtnMapList = new ArrayList();
		int j = 0;
		for (String key : keys) {
			if (StringUtil.isNull(key)) {
				continue;
			}
			HQLInfo hqlInfo = new HQLInfo();
			String whereBlock = modelTable + ".fdName = :key" + j + " or " + modelTable + ".fdNamePinYin = :key" + j
					+ " or " + modelTable + ".fdNameSimplePinyin = :key" + j; // 简拼搜索
			String fdName_lang = SysLangUtil.getLangFieldName("fdName");
			if (!"fdName".equals(fdName_lang)) {
				whereBlock += " or " + modelTable + "." + fdName_lang + " like :key" + j;
			}
			if ((orgType & HR_TYPE_PERSON) == HR_TYPE_PERSON) {
				whereBlock += " or " + modelTable + ".fdLoginName = :key" + j
						+ /*" or " + modelTable
							+ ".fdNickName = :key" + j +*/
						" or lower(staffinglevel.fdName) = :key" + j;

				hqlInfo.setJoinBlock("left join " + modelTable + ".fdStaffingLevel staffinglevel");
			}

			buildSearchHQLInfo(hqlInfo, whereBlock, startList, orgType, modelTable);
			hqlInfo.setParameter("key" + j, key);
			j++;
			hqlInfo.setAuthCheckType(authCheckType);
			List result = service.findList(hqlInfo);
			if (SysOrgUtil.isOrgAeraEnable()) {
				result = filter(elementIds, result, visible_role);
			}

			result = sysOrganizationStaffingLevelService.getStaffingLevelFilterResult(result);
			if (result.isEmpty()) {
				Map entry = new HashMap();
				entry.put("key", key);
				rtnMapList.add(entry);
			} else {
				for (int i = 0; i < result.size(); i++) {
					HrOrganizationElement elem = (HrOrganizationElement) result.get(i);
					if (checkExceptIds(elem.getFdHierarchyId())) {
						continue;
					}
					Map entry = HrOrgDialogUtil.getResultEntry(elem, xmlContext.getContextPath(), isReturnHierarchyId);
					entry.put("key", key);
					rtnMapList.add(entry);
				}
			}
		}
		return rtnMapList;
	}

	private boolean checkExceptIds(String hId) {
		return ArrayUtil.isListIntersect(HrOrgDialogUtil.getExceptIdList(),
				Arrays.asList(hId.split(BaseTreeConstant.HIERARCHY_ID_SPLIT)));
	}

	private List filter(Set<String> elementIds, List list, boolean visible_role) {
		if (elementIds == null) {
			return list;
		}
		List result = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			HrOrganizationElement el = (HrOrganizationElement) list.get(i);
			if (el.getFdIsAvailable() && el.getFdIsBusiness()) {
				if (visible_role || checkVisibleData(elementIds, el)) {
					result.add(el);
				}
			}
		}
		return result;
	}

	private boolean checkVisibleRole() {
		String[] roles = new String[1];
		roles[0] = "ROLE_SYSORG_ORG_ADMIN";
		boolean result = ArrayUtil.isListIntersect(UserUtil.getKMSSUser().getUserAuthInfo().getAuthRoleAliases(),
				Arrays.asList(roles));
		return result;
	}

	private boolean checkVisibleData(Set<String> visibleOrgIds, HrOrganizationElement element) {

		if (visibleOrgIds == null || visibleOrgIds.size() == 0) {
			return true;
		}
		for (String visibleOrgId : visibleOrgIds) {
			if (element.getFdHierarchyId().startsWith("x" + visibleOrgId)) {
				return true;
			}
		}
		return false;

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
			IHrOrganizationElementService service = hrOrganizationElementService;
			int orgType = HR_TYPE_ALLORG;
			String para = xmlContext.getParameter("orgType");
			try {
				orgType = Integer.parseInt(para);
			} catch (NumberFormatException e) {

			}

			StringBuffer whereBlock = new StringBuffer();
			whereBlock.append("(lower(" + modelTable + ".fdName) like :fdName" + " or " + modelTable
					+ ".fdNamePinYin like :fdName" + " or " + modelTable + ".fdNameSimplePinyin like :fdName");
			if ((orgType & HR_TYPE_PERSON) == HR_TYPE_PERSON) {
				whereBlock.append(" or lower(" + modelTable + ".fdLoginName) like :fdName"
						+ /*" or lower(" + modelTable
							+ ".fdNickName) like :fdName" +*/ " or lower(staffinglevel.fdName) like :fdName");

				hqlInfo.setJoinBlock("left join " + modelTable + ".fdStaffingLevel staffinglevel");
			}
			String fdName_lang = SysLangUtil.getLangFieldName("fdName");
			if (!"fdName".equals(fdName_lang)) {
				whereBlock.append(" or lower(" + modelTable + "." + fdName_lang + ") like :fdName");
			}
			if (StringUtil.isNotNull(fdDeptName)) {
				whereBlock.append(") and (" + modelTable + ".hbmParent.fdId is not null and ");
			} else {
				whereBlock.append(") and (" + modelTable + ".hbmParent.fdId is null or ");
			}
			whereBlock.append(modelTable
					+ ".hbmParent.fdId in (Select fdId from com.landray.kmss.sys.organization.model.SysOrgElement sysOrgElement where sysOrgElement.fdName like :fdDeptName))");
			hqlInfo.setParameter("fdDeptName", "%" + fdDeptName + "%");
			buildSearchHQLInfo(hqlInfo, whereBlock.toString(), null, orgType, modelTable);
			if ((StringUtil.isNotNull(fdDeptName) && "true".equals(fdHasChild))) {
				//开启搜索子部门或子机构
				hqlInfo.setParameter("fdDeptName", "%" + "" + "%");
			} else {
				hqlInfo.setParameter("fdDeptName", "%" + fdDeptName + "%");
			}
			hqlInfo.setParameter("fdName", "%" + fdName.toLowerCase() + "%");
			hqlInfo.setAuthCheckType(authCheckType);
			boolean isReturnHierarchyId = false;
			String returnHierarchyId = xmlContext.getParameter("returnHierarchyId");
			if ("true".equals(returnHierarchyId)) {
				isReturnHierarchyId = true;
			}
			elemList = sysOrganizationStaffingLevelService.getStaffingLevelFilterResult(service.findList(hqlInfo));
			elemList = HrOrgDialogUtil.getResultEntries(elemList, xmlContext.getContextPath(), isReturnHierarchyId);
			if ((StringUtil.isNotNull(fdDeptName) && "true".equals(fdHasChild))) {
				//开启搜索子部门或子机构
				List rangeList = new ArrayList();
				for (int i = 0; i < elemList.size(); i++) {
					HashMap<String, String> elem = (HashMap<String, String>) elemList.get(i);
					String info = elem.get("info");
					if (StringUtil.isNotNull(info)) {
						String[] infoChls = info.split(" - ");
						if (StringUtil.isNotNull(infoChls[1])) {
							if (infoChls[1].indexOf(fdDeptName) != -1) {
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
	 * 构造HQLInfo的where与order by
	 * 
	 * @param whereBlock
	 * @param startList
	 * @param orgType
	 * @return
	 */
	private void buildSearchHQLInfo(HQLInfo hqlInfo, String whereBlock, List startList, int orgType,
			String modelTable) {
		if (startList != null) {
			whereBlock = SysOrgHQLUtil.buildAllChildrenWhereBlock(startList, whereBlock, modelTable);
		}
		whereBlock = SysOrgHQLUtil.buildWhereBlock(orgType, whereBlock, modelTable);
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(
				modelTable + ".fdOrgType desc, " + modelTable + "." + SysLangUtil.getLangFieldName("fdName"));
	}

	private Set<String> getLimitIds(RequestContext xmlContext) throws Exception {
		KMSSUser user = UserUtil.getKMSSUser();
		if (user != null && user.isAdmin()) {
			return null;
		}

		String sys_page = xmlContext.getParameter("sys_page");
		// 如果是管理页面
		String validatorParas = "role=ROLE_SYSORG_DIALOG_USER";
		if ("true".equals(sys_page)) {
			validatorParas = "role=ROLE_SYSORG_ORG_ADMIN";
		}
		ValidatorRequestContext context = new ValidatorRequestContext();
		context.setUser(UserUtil.getKMSSUser());
		context.addValidatorParas(validatorParas);
		if (roleValidator.validate(context)) {
			return null;
		}

		Set<String> elementIds = sysOrganizationVisibleService.getPersonVisibleOrgIds(UserUtil.getUser());

		if (elementIds == null || elementIds.size() == 0) {
			return null;
		}
		return elementIds;
	}
}
