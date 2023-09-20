package com.landray.kmss.hr.organization.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.hr.organization.constant.HrOrgConstant;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.service.IHrOrgFileAuthorService;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.organization.util.HrOrgAuthorityUtil;
import com.landray.kmss.hr.organization.util.HrOrgDialogUtil;
import com.landray.kmss.hr.organization.util.HrOrgHQLUtil;
import com.landray.kmss.hr.organization.validator.HrOrgCompileValidator;
import com.landray.kmss.hr.staff.service.IHrStaffFileAuthorService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;

public class HrOrganizationTree implements IXMLDataBean, HrOrgConstant {

	private IHrStaffFileAuthorService hrStaffFileAuthorService;

	public IHrStaffFileAuthorService getHrStaffFileAuthorService() {
		if (this.hrStaffFileAuthorService == null) {
			this.hrStaffFileAuthorService = (IHrStaffFileAuthorService) SpringBeanUtil
					.getBean("hrStaffFileAuthorService");
		}
		return hrStaffFileAuthorService;
	}

	private static IHrOrgFileAuthorService hrOrgFileAuthorService;

	protected static IHrOrgFileAuthorService getHrOrgFileAuthorServiceImp() {
		if (hrOrgFileAuthorService == null) {
			hrOrgFileAuthorService = (IHrOrgFileAuthorService) SpringBeanUtil
					.getBean("hrOrgFileAuthorService");
		}
		return hrOrgFileAuthorService;
	}

	private IHrOrganizationElementService hrOrganizationElementService;

	public IHrOrganizationElementService getHrOrganizationElementService() {
		return hrOrganizationElementService;
	}

	public void setHrOrganizationElementService(
			IHrOrganizationElementService hrOrganizationElementService) {
		this.hrOrganizationElementService = hrOrganizationElementService;
	}

	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	public IHrStaffPersonInfoService getHrStaffPersonInfoService() {
		if (hrStaffPersonInfoService == null) {
			hrStaffPersonInfoService = (IHrStaffPersonInfoService) SpringBeanUtil
					.getBean("hrStaffPersonInfoService");
		}
		return hrStaffPersonInfoService;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		// 查找组织架构树数据
		String whereBlock = "";
		int orgType = HR_TYPE_HRORDEPT;
		boolean isNullNode = false;
		boolean isBaseNode = false;
		HQLInfo hqlInfo = new HQLInfo();
		String isAuth = requestInfo.getParameter("isAuth");
		String para = requestInfo.getParameter("parent");
		String isCompile = requestInfo.getParameter("isCompile");
		String filterId = requestInfo.getParameter("filterId");
		String isTree = requestInfo.getParameter("isTree");
		String pageNo = requestInfo.getParameter("pageNo");
		String pageSize = requestInfo.getParameter("pageSize");

		if (StringUtil.isNull(isAuth)) {
			isAuth = "false";
		}
		if (para == null || "".equals(para) || "null".equals(para)) {
			// 根节点
			if (UserUtil.getKMSSUser().isAdmin() || UserUtil.checkRole("ROLE_HRORGANIZATION_ORG_ADMIN")
					|| UserUtil.checkRole("ROLE_HRORGANIZATION_ORG_COMPILE")
					|| UserUtil.checkRole("ROLE_HRORGANIZATION_ORG_COMPILE_SCOPE")
					|| !"true".equals(isAuth)) {
				whereBlock = "hrOrganizationElement.hbmParent is null";
			} else {
				whereBlock = "1=2";
			}
			isBaseNode = true;
		} else {
			whereBlock = "hrOrganizationElement.hbmParent.fdId=:hbmParentId";
			hqlInfo.setParameter("hbmParentId", para);
		}
		// 过滤指定节点及其子节点

		String orgTypeStr = requestInfo.getParameter("orgType");
		if (orgTypeStr != null && !"".equals(orgTypeStr)) {
			try {
				orgType = Integer.parseInt(orgTypeStr);
			} catch (NumberFormatException e) {
			}
		}

		int treeOrgType = orgType;
		if ((treeOrgType & HR_TYPE_POSTORPERSON) > 0)
			// 若需要选择个人或岗位，机构和部门必须出现
		{
			treeOrgType |= HR_TYPE_HRORDEPT;
		} else if ((treeOrgType & HR_TYPE_DEPT) == HR_TYPE_DEPT)
			// 若需要选择部门，机构必须出现
		{
			treeOrgType |= HR_TYPE_ORG;
		}
		if (isBaseNode) {
			// 根节点不出现个人和岗位信息
			treeOrgType &= ~HR_TYPE_POSTORPERSON;
		} else if (isNullNode) {
			// 未指定部门/机构的节点不出现机构和部门信息
			treeOrgType &= ~HR_TYPE_HRORDEPT;
		}
		whereBlock = HrOrgHQLUtil.buildWhereBlock(treeOrgType,
				whereBlock, "hrOrganizationElement");
		StringBuffer sbf = new StringBuffer(whereBlock);
		//权限过滤
			if (UserUtil.checkRole("ROLE_HRORGANIZATION_ORG_COMPILE")
					|| UserUtil
							.checkRole("ROLE_HRORGANIZATION_ORG_COMPILE_SCOPE")
					|| UserUtil.checkRole("ROLE_HRORGANIZATION_ORG_ADMIN")) {
				sbf = HrOrgAuthorityUtil.builtWhereBlock(sbf,
						"hrOrganizationElement", hqlInfo);
			}
		hqlInfo.setWhereBlock(sbf.toString());
		// 多语言
		hqlInfo.setOrderBy(
				"hrOrganizationElement.fdOrgType desc, hrOrganizationElement.fdOrder, hrOrganizationElement."
						+ SysLangUtil.getLangFieldName("fdName"));
		hqlInfo.setAuthCheckType("DIALOG_READER");
		
		List findList;
		if (StringUtil.isNotNull(pageNo)) {
			hqlInfo.setPageNo(Integer.parseInt(pageNo));
			hqlInfo.setRowSize(Integer.parseInt(pageSize));
			Page page = hrOrganizationElementService.findPage(hqlInfo);
			int maxPage = page.getTotalrows() / hqlInfo.getRowSize() + (page.getTotal() % hqlInfo.getRowSize() == 0 ? 0 : 1);
			if (maxPage < hqlInfo.getPageNo()) {
				findList = new ArrayList();
			} else {
				findList = page.getList();
			}
		} else {
			findList = hrOrganizationElementService.findList(hqlInfo);
		}
		if (!"true".equals(isTree)) {
			// 给子节点授权父节点看不到的情况
			findList = this.getAuthParentNodes(findList, para);
		}
		// //根节点权限数据过滤
		// if (StringUtil.isNull(para) && isAuth.equals("true")) {
		// findList = filterOrgAuth(findList, hqlInfo);
		// }

		//过滤生态组织
		List<String> externalList = new ArrayList<>();
		if (isBaseNode) {
			externalList = filterOrgExternal(hqlInfo);
		}

		ArrayList rtnMapList = new ArrayList();
		for (int i = 0; i < findList.size(); i++) {
			HrOrganizationElement elem = (HrOrganizationElement) findList
					.get(i);
			//过滤生态组织
			if(externalList.contains(elem.getFdId())){
				continue;
			}
			int curType = elem.getFdOrgType().intValue();
			if (StringUtil.isNotNull(filterId)) {
				if (elem.getFdId().equals(filterId)) {
					continue;
				}
			}
			HashMap map = new HashMap();
			map.put("value", elem.getFdId());
			map.put("nodeType", elem.getFdOrgType());
			map.put("isAvailable", elem.getFdIsAvailable().toString());
			map.put("fdIsVirOrg", StringUtil.isNull(elem.getFdIsVirOrg()) ? false : elem.getFdIsVirOrg());


			/*List elemList = elem.getFdChildren();
			for (int j = 0; j < elemList.size(); j++) {
				HrOrganizationElement elemChild = (HrOrganizationElement) elemList
						.get(j);
				if (elemChild.getFdOrgType() == HrOrgConstant.HR_TYPE_DEPT
						|| elemChild
								.getFdOrgType() == HrOrgConstant.HR_TYPE_ORG) {
					if (elemChild.getFdIsAvailable()) {
						hasChildren = "true";
						break;
					}
				}
			}*/
			//如果数量超过100那就认为都有子，为了缓解大数据
			if (findList.size() > 100){
					map.put("hasChildren", true);
			} else {
				map.put("hasChildren", checkhasChildren(elem.getFdId()));
			}
			String text = HrOrgDialogUtil.getDeptLevelNames(elem);
			map.put("text", text);
			map.put("title", text);
			if ((curType & orgType) == 0) {
				map.put("href", "");
			}

			if (elem.getFdOrgType().equals(HR_TYPE_PERSON)) {
				HrOrgDialogUtil.setPersonAttrs(elem,
						requestInfo.getContextPath(),
						map);
			}
			// 设置编制信息
			if (StringUtil.isNotNull(isCompile)) {
				map.put("fdCompileNum", elem.getFdCompileNum());
				map.put("fdIsCompileOpen", elem.getFdIsCompileOpen());
				map.put("fdIsLimitNum",elem.getFdIsLimitNum());
				JSONObject staffStat = getHrStaffPersonInfoService()
						.getPersonStat(elem.getFdId());
				map.put("onAllPost", staffStat.get("onAllPost"));
				map.put("onCompiling", staffStat.get("onCompiling"));
				//是否有设置编制权限
				map.put("fdCompileAuth", HrOrgCompileValidator.validateCompileRole(elem.getFdId()));
			}
			rtnMapList.add(map);
		}
		if (isBaseNode && (orgType & HR_TYPE_POSTORPERSON) > 0) {
			HashMap map = new HashMap();
			map.put("value", "null");
			map.put("text", ResourceUtil.getString(
					"sys-organization:sysOrg.address.parentNotAssign",
					requestInfo.getLocale()));
			map.put("href", "");
			rtnMapList.add(map);
		}
		return rtnMapList;
	}

	//过滤生态组织
	private List<String> filterOrgExternal(HQLInfo hqlInfo) throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setSelectBlock("hrOrganizationElement.fdId");
		hql.setJoinBlock(", com.landray.kmss.sys.organization.model.SysOrgElement sysOrgElement");
		hql.setWhereBlock(hqlInfo.getWhereBlock() + " and sysOrgElement.fdId = hrOrganizationElement.fdId and sysOrgElement.fdIsExternal = :fdIsExternal");
		hql.setParameter("fdIsExternal", Boolean.TRUE);

		if(CollectionUtils.isNotEmpty(hqlInfo.getParameterList())){
			for (HQLParameter hqlParameter : hqlInfo.getParameterList()) {
				hql.setParameter(hqlParameter.getName(),hqlParameter.getValue());
			}
		}

		return hrOrganizationElementService.findList(hql);
	}

	// 拿到子节点的父节点
	private List<HrOrganizationElement> getAuthParentNodes(List findList,
			String parent)
			throws Exception {
		if (findList == null) {
			findList = new ArrayList();
		}
		List<HrOrganizationElement> rtnList = new ArrayList<HrOrganizationElement>();
		// 1、获取有权限的所有节点
		List<String> orgIds = HrOrgAuthorityUtil.getOrgIds();
		// 2、查询部门的层级
		List<HrOrganizationElement> lists = hrOrganizationElementService
				.findByPrimaryKeys(ArrayUtil.toStringArray(orgIds.toArray()));
		for (HrOrganizationElement hrOrganizationElement : lists) {
			if (hrOrganizationElement == null
					|| StringUtil.isNull(hrOrganizationElement.getFdId())) {
				continue;
			}
			if (StringUtil.isNull(parent)) {
				// 这种情况是顶级情况
				String hierarchyId = hrOrganizationElement.getFdHierarchyId();
				hierarchyId = hierarchyId.substring(1);
				String[] parentids = hierarchyId.split("x");
				if (parentids.length > 0) {
					HrOrganizationElement pNode = (HrOrganizationElement) hrOrganizationElementService.findByPrimaryKey(parentids[0], HrOrganizationElement.class, true);
					//递归找到最顶级机构，机构的判定需要通过getHbmParent
					HrOrganizationElement topParent = getTopParent(pNode);
					if (topParent != null && StringUtil.isNotNull(topParent.getFdId())) {
						findList.add(topParent);
					}
				}
			} else {
				// 非顶级情况
				String hierarchyId = hrOrganizationElement.getFdHierarchyId();
				if (!hierarchyId.contains(parent)) {
					continue;
				}
				String[] hierarchys = hierarchyId.split(parent);
				if (hierarchys.length > 0) {
					hierarchyId = hierarchys[1];
				}
				hierarchyId = hierarchyId.substring(1);
				String[] parentids = hierarchyId.split("x");
				if (parentids.length > 0) {
					HrOrganizationElement pNode = (HrOrganizationElement) hrOrganizationElementService.findByPrimaryKey(parentids[0], HrOrganizationElement.class, true);
					if (pNode != null && StringUtil.isNotNull(pNode.getFdId())) {
						findList.add(pNode);
					}
				}
			}
		}

		// 去重
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < findList.size(); i++) {
			HrOrganizationElement elem = (HrOrganizationElement) findList
					.get(i);
			if (ids.contains(elem.getFdId())) {
				continue;
			}
			rtnList.add(elem);
			ids.add(elem.getFdId());
		}
		return rtnList;
	}

	/**
	 * 获取顶级父节点
	 * @param childrenNode
	 * @return: com.landray.kmss.hr.organization.model.HrOrganizationElement
	 * @author: wangjf
	 * @time: 2022/7/23 7:31 下午
	 */
	private HrOrganizationElement getTopParent(HrOrganizationElement childrenNode) throws Exception{

		if(childrenNode.getHbmParent() == null){
			return childrenNode;
		}
		HrOrganizationElement parentNode = (HrOrganizationElement) hrOrganizationElementService
				.findByPrimaryKey(childrenNode.getHbmParent().getFdId(), HrOrganizationElement.class, true);
		return getTopParent(parentNode);
	}

	private String checkhasChildren(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"hrOrganizationElement.hbmParent.fdId=:hbmParentId and hrOrganizationElement.fdOrgType in(1,2) and hrOrganizationElement.fdIsAvailable = :fdIsAvailable");
		hqlInfo.setParameter("hbmParentId", fdId);
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		List list = hrOrganizationElementService.findList(hqlInfo);
		if (ArrayUtil.isEmpty(list)) {
			return "false";
		}
		return "true";
	}

	private List filterOrgAuth(List<HrOrganizationElement> list, HQLInfo hqlInfo) throws Exception {
		List filterList = new ArrayList();
		hqlInfo.setSelectBlock("fdId");
		List<String> findList = hrOrganizationElementService.findList(hqlInfo);
		for (HrOrganizationElement element : list) {
			if (null == element.getFdParent()) {
				filterList.add(element);
				continue;
			}
			if (!findList.contains(element.getFdParent().getFdId())) {
				filterList.add(element);
			}
		}
		return filterList;
	}
}
