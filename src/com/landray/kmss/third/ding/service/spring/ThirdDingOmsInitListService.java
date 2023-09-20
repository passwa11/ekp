package com.landray.kmss.third.ding.service.spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.intercept.RoleValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.service.ISysOrganizationVisibleService;
import com.landray.kmss.sys.organization.service.spring.OrgDialogUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class ThirdDingOmsInitListService implements IXMLDataBean, DingConstant,SysOrgConstant {
	private ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	private IOmsRelationService omsRelationService;

	public void setomsRelationService(IOmsRelationService omsRelationService) {
		this.omsRelationService = omsRelationService;
	}

	private String getAppKey() {
		return StringUtil.isNull(DING_OMS_APP_KEY)
				? "default"
				: DING_OMS_APP_KEY;
	}

	private Map<String, String> relationMap = null;

	public List getDataListAllOP(RequestContext requestInfo) throws Exception {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		relationMap = new HashMap<String, String>();
		String type = requestInfo.getParameter("type");
		List<OmsRelationModel> relationlist = omsRelationService
				.findList("fdAppKey='" + getAppKey() + "'", null);
		for (int i = 0; i < relationlist.size(); i++) {
			relationMap.put(relationlist.get(i).getFdEkpId(),
					relationlist.get(i).getFdId());
		}
		String search = requestInfo.getParameter("search");
		String where = "fdOrgType in (1,2)";
		if ("0".equals(type)) {
			where = " fdOrgType=8 and fdIsAvailable=1 and fdLoginName not in ('admin','everyone','anonymous')";
		}
		if (StringUtil.isNotNull(search)) {
			where += " and fdIsAvailable=1 and fdName like '%" + search + "%'";
		}
		List<SysOrgElement> elements = sysOrgElementService.findList(where,
				null);
		String parent = "";
		for (SysOrgElement ele : elements) {
			if (relationMap.get(ele.getFdId()) == null) {
				HashMap<String, String> node = new HashMap<String, String>();
				node.put("id", ele.getFdId());
				node.put("name", ele.getFdName());
				//考虑性能问题暂不开启
				parent = ele.getFdParentsName("<<");
				if (StringUtil.isNull(parent)) {
					node.put("info", ele.getFdName());
				} else {
					node.put("info", ele.getFdParentsName("<<") + "<<"
							+ ele.getFdName());
				}
				rtnList.add(node);
			}
		}
		return rtnList;
	}
	
	private ISysOrganizationVisibleService sysOrganizationVisibleService;

	private RoleValidator roleValidator;

	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	public ISysOrganizationStaffingLevelService getSysOrganizationStaffingLevelService() {
		return sysOrganizationStaffingLevelService;
	}

	public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}

	/**
	 * 不做组织过滤
	 * 
	 * @param xmlContext
	 * @return
	 * @throws Exception
	 */
	public List getDataListAll(RequestContext xmlContext) throws Exception {

		// 查找组织架构列表数据
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock;
		int orgType = ORG_TYPE_DEFAULT;
		String para = xmlContext.getParameter("parent");
		String fdParentId = para;
		if (StringUtil.isNull(para)) {
            whereBlock = "sysOrgElement.hbmParent=null";
        } else {
			whereBlock = "sysOrgElement.hbmParent.fdId=:hbmParentId";
			hqlInfo.setParameter("hbmParentId", para);
		}
		para = xmlContext.getParameter("orgType");
		if (para != null && !"".equals(para)) {
			try {
				orgType = Integer.parseInt(para);
			} catch (NumberFormatException e) {
			}
		}
		// 如果类型单独为群组和角色，则不进行查询
		if (orgType == ORG_TYPE_GROUP || orgType == ORG_TYPE_ROLE
				|| orgType == (ORG_TYPE_GROUP | ORG_TYPE_ROLE)) {
			return null;
		}
		orgType = orgType
				& (ORG_TYPE_ALLORG | ORG_FLAG_AVAILABLEALL | ORG_FLAG_BUSINESSALL);
		whereBlock = SysOrgHQLUtil.buildWhereBlock(orgType, whereBlock,
				"sysOrgElement");
		hqlInfo.setWhereBlock(whereBlock);
		// 多语言
		hqlInfo
				.setOrderBy(
						"sysOrgElement.fdOrgType desc, sysOrgElement.fdOrder, sysOrgElement."
								+ SysLangUtil.getLangFieldName("fdName"));

		hqlInfo.setRowSize(SysOrgUtil.LIMIT_RESULT_SIZE + 1);
		hqlInfo.setGetCount(false);
		hqlInfo.setAuthCheckType("DIALOG_READER");
		List<SysOrgElement> elemList = new ArrayList<SysOrgElement>();
		List<SysOrgElement> elements = sysOrgElementService.findPage(hqlInfo)
				.getList();
		for (SysOrgElement ele : elements) {
			if(relationMap.get(ele.getFdId()) == null){
				elemList.add(ele);
			}
		}
		elemList = sysOrganizationStaffingLevelService
				.getStaffingLevelFilterResult(elemList);

		if (elemList.size() > SysOrgUtil.LIMIT_RESULT_SIZE) {
			return OrgDialogUtil.getResultEntries(elemList, xmlContext
					.getContextPath());
		}

		// 点击部门或机构时，把这部门或机构下的所有岗位中的人员，加到列表中去
		/*if ((orgType & ORG_TYPE_PERSON) == ORG_TYPE_PERSON
				&& StringUtil.isNotNull(fdParentId)) {
			HQLInfo info = new HQLInfo();
			whereBlock = SysOrgHQLUtil.buildWhereBlock(ORG_TYPE_POST,
					"sysOrgElement.hbmParent.fdId=:hbmParentId",
					"sysOrgElement");
			info.setParameter("hbmParentId", fdParentId);
			info.setWhereBlock(whereBlock);
			info.setOrderBy("sysOrgElement.fdOrder");
			List<SysOrgElement> postList = sysOrgElementService.findList(info);
			for (SysOrgElement post : postList) {
				List<?> ss = post.getFdPersons();
				ss = sysOrganizationStaffingLevelService
						.getStaffingLevelFilterResult((List<SysOrgElement>) ss);
				for (int i = 0; i < ss.size(); i++) {
					SysOrgElement soe = ((SysOrgElement) BeanUtils.cloneBean(ss.get(i)));
					
					if (soe.getFdIsAvailable() && soe.getFdIsBusiness()
							&& elemList != null
							&& !elemList.contains(soe)) {
						soe.setFdOrder(post.getFdOrder());
						elemList.add(soe);
						if (elemList.size() > SysOrgUtil.LIMIT_RESULT_SIZE) {
							return OrgDialogUtil.getResultEntries(elemList,
									xmlContext.getContextPath());
						}
					}
				}
			}

			Collections.sort(elemList, new Comparator() {

				public int compare(Object o1, Object o2) {
					SysOrgElement org1 = (SysOrgElement) o1;
					SysOrgElement org2 = (SysOrgElement) o2;
					Integer i1 = org1.getFdOrder() == null ? Integer.MAX_VALUE
							: org1.getFdOrder();
					Integer i2 = org2.getFdOrder() == null ? Integer.MAX_VALUE
							: org2.getFdOrder();
					if (org1.getFdOrgType().equals(org2.getFdOrgType())) {
						if (i1.equals(i2)) {
							return 0;
						} else if (i1 > i2) {
							return 1;
						} else {
							return -1;
						}
					} else if (org1.getFdOrgType() > org2.getFdOrgType()) {
						return -1;
					} else {
						return 1;
					}
				}
			});
		}*/
		return OrgDialogUtil.getResultEntries(elemList, xmlContext
				.getContextPath());
	}

	@Override
    @SuppressWarnings("unchecked")
	public List getDataList(RequestContext xmlContext) throws Exception {
		// 判断是否有扩充的配置，注意扩充时必须将该部分代码删除
		/*IXMLDataBean extension = OrgDialogUtil.getExtension("orgList");
		if (extension != null && extension != this)
			return extension.getDataList(xmlContext);*/
		
		/*String type = xmlContext.getParameter("type");
		if(StringUtil.isNotNull(type)){
			return getDataListAllOP(xmlContext);
		}*/
		
		relationMap = new HashMap<String, String>();
		List<OmsRelationModel> relationlist = omsRelationService
				.findList("fdAppKey='" + getAppKey() + "'", null);
		for (int i = 0; i < relationlist.size(); i++) {
			relationMap.put(relationlist.get(i).getFdEkpId(),
					relationlist.get(i).getFdId());
		}
		
		KMSSUser user = UserUtil.getKMSSUser();
		if (user != null && user.isAdmin()) {
			return getDataListAll(xmlContext);
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
			return getDataListAll(xmlContext);
		}

		Set<String> elementIds = sysOrganizationVisibleService
				.getPersonVisibleOrgIds(UserUtil.getUser());

		if (elementIds == null || elementIds.size() == 0) {
			return getDataListAll(xmlContext);
		}

		// 查找组织架构列表数据
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock;
		int orgType = ORG_TYPE_DEFAULT;
		String para = xmlContext.getParameter("parent");
		String fdParentId = para;
		if (StringUtil.isNull(para)) {
            whereBlock = "sysOrgElement.fdId in ("
                    + SysOrgUtil.buildInBlock(elementIds) + ")";
        } else {
			whereBlock = "sysOrgElement.hbmParent.fdId=:hbmParentId and sysOrgElement.fdOrgType!=1";
			hqlInfo.setParameter("hbmParentId", para);
		}
		para = xmlContext.getParameter("orgType");
		if (para != null && !"".equals(para)) {
			try {
				orgType = Integer.parseInt(para);
			} catch (NumberFormatException e) {
			}
		}
		// 如果类型单独为群组和角色，则不进行查询
		if (orgType == ORG_TYPE_GROUP || orgType == ORG_TYPE_ROLE
				|| orgType == (ORG_TYPE_GROUP | ORG_TYPE_ROLE)) {
			return null;
		}
		orgType = orgType
				& (ORG_TYPE_ALLORG | ORG_FLAG_AVAILABLEALL | ORG_FLAG_BUSINESSALL);
		whereBlock = SysOrgHQLUtil.buildWhereBlock(orgType, whereBlock,
				"sysOrgElement");
		hqlInfo.setWhereBlock(whereBlock);
		// 多语言
		hqlInfo
				.setOrderBy(
						"sysOrgElement.fdOrgType desc, sysOrgElement.fdOrder, sysOrgElement."
								+ SysLangUtil.getLangFieldName("fdName"));
		hqlInfo.setAuthCheckType("DIALOG_READER");
		hqlInfo.setRowSize(SysOrgUtil.LIMIT_RESULT_SIZE + 1);
		hqlInfo.setGetCount(false);
		
		List<SysOrgElement> elemList = new ArrayList<SysOrgElement>();
		List<SysOrgElement> elements = sysOrgElementService.findPage(hqlInfo)
				.getList();
		for (SysOrgElement ele : elements) {
			if(relationMap.get(ele.getFdId()) == null){
				elemList.add(ele);
			}
		}
		
		elemList = sysOrganizationStaffingLevelService
				.getStaffingLevelFilterResult(elemList);
		if (elemList.size() > SysOrgUtil.LIMIT_RESULT_SIZE) {
			return OrgDialogUtil.getResultEntries(elemList, xmlContext
					.getContextPath());
		}

		// 点击部门或机构时，把这部门或机构下的所有岗位中的人员，加到列表中去
		if ((orgType & ORG_TYPE_PERSON) == ORG_TYPE_PERSON
				&& StringUtil.isNotNull(fdParentId)) {
			HQLInfo info = new HQLInfo();
			whereBlock = SysOrgHQLUtil.buildWhereBlock(ORG_TYPE_POST,
					"sysOrgElement.hbmParent.fdId=:hbmParentId",
					"sysOrgElement");
			info.setParameter("hbmParentId", fdParentId);
			info.setWhereBlock(whereBlock);
			info.setOrderBy("sysOrgElement.fdOrder");
			List<SysOrgElement> postList = sysOrgElementService.findList(info);

			boolean visible_role = checkVisibleRole();

			for (SysOrgElement post : postList) {
				List<?> ss = post.getFdPersons();
				ss = sysOrganizationStaffingLevelService
						.getStaffingLevelFilterResult((List<SysOrgElement>) ss);
				for (int i = 0; i < ss.size(); i++) {
					SysOrgElement soe = cloneBean((SysOrgElement) ss.get(i));
					if (soe.getFdIsAvailable() && soe.getFdIsBusiness()
							&& elemList != null
							&& !elemList.contains(soe)) {
						soe.setFdOrder(post.getFdOrder());
						elemList.add(soe);
						if (elemList.size() > SysOrgUtil.LIMIT_RESULT_SIZE) {
							return OrgDialogUtil.getResultEntries(elemList,
									xmlContext.getContextPath());
						}
					}
				}
			}

			Collections.sort(elemList, new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					SysOrgElement org1 = (SysOrgElement) o1;
					SysOrgElement org2 = (SysOrgElement) o2;
					Integer i1 = org1.getFdOrder() == null ? Integer.MAX_VALUE
							: org1.getFdOrder();
					Integer i2 = org2.getFdOrder() == null ? Integer.MAX_VALUE
							: org2.getFdOrder();
					if (org1.getFdOrgType().equals(org2.getFdOrgType())) {
						if (i1.equals(i2)) {
							return 0;
						} else if (i1 > i2) {
							return 1;
						} else {
							return -1;
						}
					} else if (org1.getFdOrgType() > org2.getFdOrgType()) {
						return -1;
					} else {
						return 1;
					}
				}
			});
		}
		return OrgDialogUtil.getResultEntries(elemList, xmlContext
				.getContextPath());
	}

	private boolean checkVisibleRole() {
		String[] roles = new String[1];
		roles[0] = "ROLE_SYSORG_ORG_ADMIN";
		boolean result = ArrayUtil.isListIntersect(UserUtil.getKMSSUser()
				.getUserAuthInfo().getAuthRoleAliases(), Arrays.asList(roles));
		return result;
	}

	private boolean checkVisibleData(Set<String> visibleOrgIds,
			SysOrgElement element) {

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

	public void setSysOrganizationVisibleService(
			ISysOrganizationVisibleService sysOrganizationVisibleService) {
		this.sysOrganizationVisibleService = sysOrganizationVisibleService;
	}

	public void setRoleValidator(RoleValidator roleValidator) {
		this.roleValidator = roleValidator;
	}

	public RoleValidator getRoleValidator() {
		return roleValidator;
	}

	private SysOrgElement cloneBean(SysOrgElement ele) {
		SysOrgElement ele2 = new SysOrgElement();
		if (ele instanceof SysOrgPerson) {
			SysOrgPerson person2 = new SysOrgPerson();
			SysOrgPerson person = (SysOrgPerson) ele;
			person2.setFdDefaultLang(person.getFdDefaultLang());
			person2.setFdEmail(person.getFdEmail());
			person2.setFdHiredate(person.getFdHiredate());
			person2.setFdIsActivated(person.getFdIsActivated());
			person2.setFdLoginName(person.getFdLoginName());
			person2.setFdMobileNo(person.getFdMobileNo());
			person2.setFdNickName(person.getFdNickName());
			person2.setFdSex(person.getFdSex());
			person2.setFdShortNo(person.getFdShortNo());
			person2.setFdStaffingLevel(person.getFdStaffingLevel());
			person2.setFdUserType(person.getFdUserType());
			person2.setFdWechatNo(person.getFdWechatNo());
			person2.setFdWorkPhone(person.getFdWorkPhone());
			ele2 = person2;
		}

		ele2.setFdId(ele.getFdId());
		ele2.setFdName(ele.getFdName());
		ele2.setCustomPropMap(ele.getCustomPropMap());
		ele2.setDynamicMap(ele.getDynamicMap());
		ele2.setFdAlterTime(ele.getFdAlterTime());
		ele2.setFdCreateTime(ele.getFdCreateTime());
		ele2.setFdFlagDeleted(ele.getFdFlagDeleted());
		ele2.setFdHierarchyId(ele.getFdHierarchyId());
		ele2.setFdGroups(ele.getFdGroups());
		ele2.setFdIsAbandon(ele.getFdIsAbandon());
		ele2.setFdIsAvailable(ele.getFdIsAvailable());
		ele2.setFdIsBusiness(ele.getFdIsBusiness());
		ele2.setFdKeyword(ele.getFdKeyword());
		ele2.setFdMemo(ele.getFdMemo());
		ele2.setFdNamePinYin(ele.getFdNamePinYin());
		ele2.setFdNameSimplePinyin(ele.getFdNameSimplePinyin());
		ele2.setFdNo(ele.getFdNo());
		ele2.setFdOrder(ele.getFdOrder());
		ele2.setFdOrgEmail(ele.getFdOrgEmail());
		ele2.setFdOrgType(ele.getFdOrgType());
		ele2.setFdParent(ele.getFdParent());
		ele2.setFdPersons(ele.getFdPersons());
		ele2.setFdPosts(ele.getFdPosts());
		return ele2;
	}
}
