package com.landray.kmss.sys.organization.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.intercept.RoleValidator;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.eco.AuthOrgRange;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.exception.ExistChildrenException;
import com.landray.kmss.sys.organization.filter.AuthOrgVisibleFilter;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.service.ISysOrganizationVisibleService;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.landray.kmss.constant.SysOrgConstant.*;

public class SysOrgElementCriteriaAction extends ExtendAction {

	private ISysOrgElementService sysOrgElementService;

	private ISysOrganizationVisibleService sysOrganizationVisibleService;

	private RoleValidator roleValidator;

	private IOrgRangeService orgRangeService;

	// 可见性过滤
	private AuthOrgVisibleFilter authOrgVisibleFilter;

	public RoleValidator getRoleValidator() {
		if (roleValidator == null) {
			roleValidator = (RoleValidator) getBean("roleValidator");
		}
		return roleValidator;
	}

	public IOrgRangeService getOrgRangeService() {
		if (this.orgRangeService == null) {
			this.orgRangeService = (IOrgRangeService) SpringBeanUtil.getBean("orgRangeService");
		}
		return this.orgRangeService;
	}

	public AuthOrgVisibleFilter getAuthOrgVisibleFilter() {
		if (this.authOrgVisibleFilter == null) {
			this.authOrgVisibleFilter = (AuthOrgVisibleFilter) SpringBeanUtil.getBean("authOrgVisibleFilter");
		}
		return this.authOrgVisibleFilter;
	}

	@Override
	protected ISysOrgElementService getServiceImp(HttpServletRequest request) {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	protected Object[] findElement(String id) throws Exception {
		String select_lang = "";
		String fdName_lang = SysLangUtil.getLangFieldName("fdName");
		if (StringUtil.isNotNull(fdName_lang)
				&& !"fdName".equals(fdName_lang)) {
			select_lang = ", sysOrgElement." + fdName_lang;

		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setSelectBlock(
						"sysOrgElement.fdName, sysOrgElement.fdId, sysOrgElement.fdHierarchyId"
								+ select_lang);
		hqlInfo.setWhereBlock("sysOrgElement.fdId = :id");
		hqlInfo.setParameter("id", id);
		Object[] result = (Object[]) getServiceImp(null).findValue(hqlInfo)
				.get(0);
		if (result != null && StringUtil.isNotNull(select_lang)) {
			String value = (String) result[3];
			if (StringUtil.isNotNull(value)) {
				result[0] = value;
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List<Object[]> findElements(String currId, String[] ids)
			throws Exception {
		if (ids == null || ids.length == 0) {
            return Collections.emptyList();
        }
		List<String> idList = new ArrayList<String>();
		for (String id : ids) {
			if (StringUtil.isNull(id) || currId.equals(id)) {
				continue;
			}
			idList.add(id);
		}
		if (idList.isEmpty()) {
			return Collections.emptyList();
		}
		String select_lang = "";
		String fdName_lang = SysLangUtil.getLangFieldName("fdName");
		if (StringUtil.isNotNull(fdName_lang)
				&& !"fdName".equals(fdName_lang)) {
			select_lang = ", sysOrgElement." + fdName_lang;

		}
		HQLInfo info = new HQLInfo();
		info.setSelectBlock(
				"sysOrgElement.fdName, sysOrgElement.fdId" + select_lang);
		info.setWhereBlock("sysOrgElement.fdId in (:ids) ");
		info.setParameter("ids", idList);
		info.setAuthCheckType("DIALOG_READER");
		List<Object[]> list = (List<Object[]>) getServiceImp(null)
				.findValue(info);
		if (CollectionUtils.isNotEmpty(list) && StringUtil.isNotNull(select_lang)) {
			for (Object[] o : list) {
				String value = (String) o[2];
				if (StringUtil.isNotNull(value)) {
					o[0] = value;
				}
			}
		}
		return list;
	}

	protected void loadAllElementInfo(String currId, JSONObject obj)
			throws Exception {
		if (StringUtil.isNull(currId)) {
			return;
		}
		Object[] currProp = findElement(currId);
		JSONObject currObj = new JSONObject();
		currObj.put("text", currProp[0]);
		currObj.put("value", currProp[1]);

		String[] ids = null;
		if (currProp[2] != null) {
			ids = currProp[2].toString().split("x");
		}
		JSONArray parentsObj = new JSONArray();
		List<Object[]> parents = findElements(currId, ids);

		for (Object[] parent : parents) {
			JSONObject pObj = new JSONObject();
			pObj.put("text", parent[0]);
			pObj.put("value", parent[1]);
			parentsObj.add(pObj);
		}

		obj.put("current", currObj);
		obj.put("parents", parentsObj);
	}

	@SuppressWarnings("unchecked")
	public ActionForward criteria(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TimeCounter.logCurrentTime("Action-criteria", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String userId = request.getParameter("userId");
			String searchText = request.getParameter("searchText");
			String orgType = request.getParameter("orgType");
			String parentId = request.getParameter("parentId");
			if (StringUtil.isNotNull(userId)) {
				SysOrgElement user = (SysOrgElement) getServiceImp(null)
						.findByPrimaryKey(userId);
				if (user != null && user.getFdParent() != null) {
					parentId = user.getFdParent().getFdId();
				} else if (user != null && user.getFdParentOrg() != null) {
					parentId = user.getFdParentOrg().getFdId();
				}
			}
			int type = ORG_TYPE_PERSON;
			if (StringUtil.isNotNull(orgType)) {
				type = Integer.parseInt(orgType);
			}
			HQLInfo hqlInfo = new HQLInfo();
			Boolean isHierarchyDept = type == ORG_TYPE_ORGORDEPT
					&& "true".equals(request.getParameter("__hierarchy"));
			// 按关键字搜索时，不考虑父组织
			if (StringUtil.isNotNull(searchText)) {
				StringBuffer searchWhere = new StringBuffer();
				// huangwq 按多种语言查询
				searchWhere.append("(sysOrgElement.fdName like :searchText");
				searchWhere
						.append(" or sysOrgElement.fdLoginName like :searchText");
				searchWhere
						.append(" or sysOrgElement.fdNamePinYin like :searchText)");
				String fdName_lang = SysLangUtil.getLangFieldName("fdName");
				if (StringUtil.isNotNull(fdName_lang)
						&& !"fdName".equals(fdName_lang)) {
					searchWhere
							.append(" or (sysOrgElement." + fdName_lang
									+ " like :searchText)");
				}
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo
						.getWhereBlock(), " and ", searchWhere.toString()));
				hqlInfo.setParameter("searchText", "%" + searchText + "%");
			} else {
				if (StringUtil.isNotNull(parentId)) {
					hqlInfo.setWhereBlock("sysOrgElement.hbmParent.fdId = :parentId");
					hqlInfo.setParameter("parentId", parentId);
				} else {
					AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
					StringBuffer whereBlock = new StringBuffer();
					// 内部组织，如果有管理员角色，可以查看所有，否则按需查看
					if (UserUtil.checkRole("ROLE_SYSORG_ORG_ADMIN") || UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER")) {
						whereBlock.append("sysOrgElement.hbmParent = null ");
						if (UserUtil.checkRole("ROLE_SYSORG_ORG_ADMIN") && orgRange != null && CollectionUtils.isNotEmpty(orgRange.getAuthOtherRootIds())) {
							whereBlock.append(" or sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(orgRange.getAuthOtherRootIds())).append(") ");
						}
					} else {
						if (StringUtil.isNull(parentId) && orgRange != null && (orgRange.isShowMyDept() || CollectionUtils.isNotEmpty(orgRange.getAuthOtherRootIds()))) {
							whereBlock.append("(");
						}
						// 如果有查看范围限制，就取查看范围的根组织
						if (orgRange != null && CollectionUtils.isNotEmpty(orgRange.getRootDeptIds())) {
							whereBlock.append("sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(orgRange.getRootDeptIds())).append(") ");
						} else {
							whereBlock.append("sysOrgElement.hbmParent = null ");
						}
						if (StringUtil.isNull(parentId) && orgRange != null) {
							Set<String> rootIds = new HashSet<>();
							if (orgRange.isShowMyDept()) {
								rootIds.addAll(orgRange.getMyDeptIds());
							}
							if (CollectionUtils.isNotEmpty(orgRange.getAuthOtherRootIds())) {
								rootIds.addAll(orgRange.getAuthOtherRootIds());
							}
							if (CollectionUtils.isNotEmpty(rootIds)) {
								whereBlock.append(" or sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(rootIds)).append("))");
							}
						}
					}
					// 查看所有生态组织时，特殊处理
					if (UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_READER")) {
						whereBlock.insert(0, "(").append(") or (sysOrgElement.hbmParent = null and sysOrgElement.fdIsExternal = true)");
					}
					hqlInfo.setWhereBlock(whereBlock.toString());
				}
			}
			hqlInfo.setWhereBlock(SysOrgHQLUtil.buildWhereBlock(type, hqlInfo.getWhereBlock(), "sysOrgElement"));
			// huangwq 按对应的语言排序
			// #166752 失效人员倒排
			hqlInfo.setOrderBy("sysOrgElement.fdIsAvailable desc,sysOrgElement.fdOrder,sysOrgElement."+ SysLangUtil.getLangFieldName("fdName"));
			hqlInfo.setAuthCheckType("DIALOG_READER");
			List<SysOrgElement> list = getServiceImp(request).findList(hqlInfo);
			list = getSysOrganizationStaffingLevelService().getStaffingLevelFilterResult(list);
			JSONArray array = new JSONArray();
			for (SysOrgElement element : list) {
				JSONObject row = new JSONObject();
				row.put("value", element.getFdId());
				row.put("title", element.getFdParentsName());
				row.put("fdIsAvailable", element.getFdIsAvailable() ? 1 : 0);
				String name = element.getFdName();
				if (!element.getFdIsAvailable()) {
					row.put("text", name
							+ "("
							+ ResourceUtil.getString(
							"sysOrg.address.info.disable",
							"sys-organization") + ")");
				} else {
					row.put("text", name);
				}
				array.add(row);
			}

			// 1、当前部门排最前
			if (isHierarchyDept
					&& StringUtil.isNotNull(UserUtil.getKMSSUser().getDeptId())) {
				Collections.sort(array, new Comparator<JSONObject>() {
					String __deptId = UserUtil.getKMSSUser().getDeptId();

					@Override
					public int compare(JSONObject m1, JSONObject m2) {
						if (m2.getString("value").equals(__deptId)) {
							return 1;
						}
						return 0;
					}
				});
			}

			if ("true".equals(request.getParameter("_all"))) {
				JSONObject obj = new JSONObject();
				obj.put("datas", array);
				loadAllElementInfo(parentId, obj);
				request.setAttribute("lui-source", obj);
			} else {
				request.setAttribute("lui-source", array);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-criteria", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	public ActionForward invalidatedAllOmsDeleted(ActionMapping mapping,
												  ActionForm form, HttpServletRequest request,
												  HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
		KmssMessages messages = new KmssMessages();
		String providerKey = request.getParameter("providerKey");

		try {
			HQLInfo info = new HQLInfo();
			info
					.setWhereBlock("sysOrgElement.fdIsAvailable = :fdIsAvailable and fdFlagDeleted = :fdFlagDeleted and fdImportInfo like '"
							+ providerKey + "%'");
			info.setParameter("fdIsAvailable", Boolean.TRUE);
			info.setParameter("fdFlagDeleted", Boolean.TRUE);
			List<SysOrgElement> elements = getServiceImp(null).findList(info);
			for (SysOrgElement sysOrgElement : elements) {
				if (sysOrgElement != null) {
					sysOrgElement.setFdIsAvailable(new Boolean(false));
					sysOrgElement.getHbmChildren().clear();
					sysOrgElement.setFdFlagDeleted(new Boolean(false));
					// 停用帐号与删除帐号时，都不要将fdImportInfo清空！不然停用后，再启用同一帐号会出现问题。
					// sysOrgElement.setFdImportInfo(null);
					getServiceImp(null).update(sysOrgElement);
				}
			}
			getServiceImp(null).flushHibernateSession();
		} catch (ExistChildrenException existChildren) {
			messages.addError(existChildren);
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	public ISysOrganizationVisibleService getSysOrganizationVisibleService() {
		if (sysOrganizationVisibleService == null) {
			sysOrganizationVisibleService = (ISysOrganizationVisibleService) getBean("sysOrganizationVisibleService");
		}
		return sysOrganizationVisibleService;
	}

	public ISysOrganizationStaffingLevelService getSysOrganizationStaffingLevelService() {
		if (sysOrganizationStaffingLevelService == null) {
			sysOrganizationStaffingLevelService = (ISysOrganizationStaffingLevelService) getBean("sysOrganizationStaffingLevelService");
		}
		return sysOrganizationStaffingLevelService;
	}

}
