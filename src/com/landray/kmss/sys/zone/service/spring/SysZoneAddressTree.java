package com.landray.kmss.sys.zone.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.eco.AuthOrgRange;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrganizationConfig;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.service.spring.OrgDialogUtil;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.sys.person.interfaces.PersonImageService;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.person.service.plugin.PersonZoneHelp;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.Collections;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;

import java.util.*;

public class SysZoneAddressTree implements IXMLDataBean, SysOrgConstant {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysZoneAddressTree.class);

	private ISysOrgElementService sysOrgElementService;

	private ISysOrgCoreService sysOrgCoreService;

	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	public void setSysOrganizationStaffingLevelService(ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}

	/**
	 * 取名称，如果不是本部门人员，需要加上其部门名称
	 *
	 * @param elem
	 * @param parentId
	 * @return
	 */
	private String buildTextAndName(SysOrgElement elem, String parentId) {
		String __name = elem.getFdName();
		if (elem.getFdParent() != null
				&& !elem.getFdParent().getFdId().equals(parentId)) {
			// 如果部门领导不是此部门的直属人员，应该加入其部门名称
			__name += " - " + elem.getFdParent().getFdName();
		}
		return __name;
	}

	private SysOrgElement navigateTo(SysOrgElement element, String navigateTo, String top) {
		if (StringUtil.isNotNull(navigateTo) && !"0".equals(navigateTo)) {
			HashMap<Integer, SysOrgElement> map = new HashMap<Integer, SysOrgElement>();
			int i = 0;
			for (element = element.getFdParent(); element != null; element = element.getFdParent()) {
				i++;
				map.put(i, element);// 设定实际部门级数：“蓝凌机构(4)/深圳蓝凌(3)/研发中心(2)/产品部(1)”
			}
			int location = Integer.parseInt(navigateTo);
			if (location > 0) {
				// 当设置的级数大于部门实际级数时，定位到本部门，否则定位到(i + 1 - location)
				element = (i + 1 - location) <= 0 ? map.get(1) : map.get(i + 1 - location);
			} else {
				location = Math.abs(location);
				// 当设置的级数绝对值大于部门实际级数时，定位到最高一级部门，否则定位到location
				element = location > i ? map.get(i) : map.get(location);
			}
		} else {
			element = element.getFdParent();// 如果没有配置或配置为0，默认定位到本部门
		}
		//add #53554 实现URL带参可默认定位到顶级或默认定位到当前部门
		if (StringUtil.isNotNull(top) && "true".equals(top)) {
			HashMap<Integer, SysOrgElement> map = new HashMap<Integer, SysOrgElement>();
			int i = 0;
			for (element = element.getFdParent(); element != null; element = element.getFdParent()) {
				i++;
				map.put(i, element);// 设定实际部门级数：“蓝凌机构(4)/深圳蓝凌(3)/研发中心(2)/产品部(1)”
			}
			element = map.get(i);
		}
		return element;
	}

	@Override
	public List<Map<String, String>> getDataList(RequestContext xmlContext)
			throws Exception {
		// 以下管理员，可以看到所有数据
		KMSSUser user = UserUtil.getKMSSUser();
		// 1. 超级管理员、系统管理员、安全管理员
		if (user != null && user.isAdmin()) {
			return getDataListAll(xmlContext);
		}
		String parentId = xmlContext.getParameter("parent");
		if (StringUtil.isNotNull(parentId) && !"null".equals(parentId) && UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER")) {
			if (UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER")) {
				return getDataListAll(xmlContext);
			}
		}

		ArrayList<Map<String, String>> rtnMapList = new ArrayList<Map<String, String>>();
		HashMap<String, String> resultDataRow = null;

		String top = xmlContext.getParameter("top");
		OrgTreeQuery treeQuery = buildOrgTreeQuery(xmlContext);
		HQLInfo findInfo = treeQuery.hqlInfo;
		boolean isNullNode = treeQuery.isNullNode;
		int orgType = treeQuery.orgType;

		findInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, "DIALOG_READER");
		if (StringUtil.isNull(parentId)) {
			SysOrgElement ele = sysOrgElementService.format(UserUtil.getUser());
			ele = navigateTo(ele, null, top);
			String curDeptId = "";
			StringBuffer parentIds = new StringBuffer();
			if (ele != null) {
				curDeptId = ele.getFdId();
				for (; ele != null; ele = ele.getFdParent()) {
					parentIds.append(ele.getFdId()).append(";");
				}
			}
			parentIds.append("root;");// 把顶级加入进来
			String[] parentIdArrays = parentIds.toString().split(";");
			int index = parentIdArrays.length > 1 ? 1 : 0;
			for (int i = parentIdArrays.length - 1; i >= index; i--) {
				if (StringUtil.isNotNull(parentIdArrays[i]) && (!"root".equals(parentIdArrays[i]))) {
					findInfo.getParameterList().clear();
					findInfo.setParameter("hbmParentId", parentIdArrays[i]);
					findInfo.setWhereBlock("sysOrgElement.hbmParent.fdId = :hbmParentId and (sysOrgElement.fdOrgType=1 or sysOrgElement.fdOrgType=2 or sysOrgElement.fdOrgType=8) and sysOrgElement.fdIsAvailable = :fdIsAvailable and sysOrgElement.fdIsBusiness = :fdIsBusiness");
					findInfo.setParameter("fdIsAvailable", true);
					findInfo.setParameter("fdIsBusiness", true);
				}
				rtnMapList.addAll(getDetailListByParentAndOrgType(curDeptId,
						parentIdArrays[i], orgType, findInfo,
						xmlContext, Arrays.asList(parentIdArrays),  false));
			}
		} else {
			rtnMapList.addAll(getDetailListByParentAndOrgType("", parentId,
					orgType, findInfo, xmlContext, null, false));
		}
		if (isNullNode && (orgType & ORG_TYPE_POSTORPERSON) > 0) {
			resultDataRow = new HashMap<String, String>();
			resultDataRow.put("id", "null");
			resultDataRow.put("text",StringEscapeUtils.escapeHtml(ResourceUtil.getString(
					"sys-organization:sysOrg.address.parentNotAssign", xmlContext.getLocale()))
			);
			resultDataRow.put("href", "");
			rtnMapList.add(resultDataRow);
		}
		return rtnMapList;
	}

	private List<Map<String, String>> getDataListAll(RequestContext xmlContext)
			throws Exception {
		ArrayList<Map<String, String>> rtnMapList = new ArrayList<Map<String, String>>();
		HashMap<String, String> resultDataRow = null;

		String parentId = xmlContext.getParameter("parent");
		String top = xmlContext.getParameter("top");
		OrgTreeQuery treeQuery = buildOrgTreeQuery(xmlContext);
		HQLInfo findInfo = treeQuery.hqlInfo;
		int orgType = treeQuery.orgType;
		String whereBlock = findInfo.getWhereBlock();

		// 查询所有数据时不需要过滤权限
		findInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
		if (StringUtil.isNull(parentId)) {
			SysOrgElement ele = sysOrgElementService.format(UserUtil.getUser());
			ele = navigateTo(ele, null, top);
			String curDeptId = "";
			StringBuffer parentIds = new StringBuffer();
			if (ele != null) {
				curDeptId = ele.getFdId();
				for (; ele != null; ele = ele.getFdParent()) {
					parentIds.append(ele.getFdId()).append(";");
				}
			}
			parentIds.append("root;");// 把顶级加入进来
			String[] parentIdArrays = parentIds.toString().split(";");
			int index = parentIdArrays.length > 1 ? 1 : 0;
			for (int i = parentIdArrays.length - 1; i >= index; i--) {
				if (StringUtil.isNotNull(parentIdArrays[i]) && (!"root".equals(parentIdArrays[i]))) {
					if (whereBlock.length() > 0) {
						whereBlock += " and ";
					}
					whereBlock += "sysOrgElement.hbmParent.fdId=:hbmParentId";
					findInfo.setWhereBlock(whereBlock);
					findInfo.setParameter("hbmParentId", parentIdArrays[i]);
				}
				rtnMapList.addAll(getDetailListByParentAndOrgType(curDeptId,
						parentIdArrays[i], orgType, findInfo,
						xmlContext, Arrays.asList(parentIdArrays), true));
			}
		} else {
			if (whereBlock.length() > 0) {
				whereBlock += " and ";
			}
			whereBlock += "sysOrgElement.hbmParent.fdId=:hbmParentId";
			findInfo.setWhereBlock(whereBlock);
			findInfo.setParameter("hbmParentId", parentId);

			rtnMapList.addAll(getDetailListByParentAndOrgType("", parentId,
					orgType, findInfo, xmlContext, null, true));
		}
		return rtnMapList;

	}

	private OrgTreeQuery buildOrgTreeQuery(RequestContext xmlContext) {
		HQLInfo hqlInfo = new HQLInfo();
		boolean isBaseNode = false;
		boolean isNullNode = false;
		StringBuffer whereBlock = new StringBuffer();

		String deptLimit = xmlContext.getParameter("deptLimit");
		String parentId = xmlContext.getParameter("parent");
		String orgTypeStr = xmlContext.getParameter("orgType");
		int orgType = StringUtil.getIntFromString(orgTypeStr, ORG_TYPE_ORGORDEPT);
		OrgTreeQuery treeQuery = OrgTreeQuery.getInstance(hqlInfo);

		if (StringUtil.isNull(parentId)) {
			if (StringUtil.isNotNull(deptLimit) && !"undefined".equals(deptLimit)) {
				Set<String> elementIds = OrgDialogUtil.getRootOrgIds(deptLimit);
				if (elementIds == null || elementIds.isEmpty()) {
					// 有限制的查询
					hqlInfo.setWhereBlock("1 = 2");
					return treeQuery;
				} else {
					whereBlock.append("sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(elementIds)).append(")");
				}
			} else {
				AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
				if (UserUtil.checkRole("ROLE_SYSORG_ORG_ADMIN") || UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER")) {
					whereBlock.append("sysOrgElement.hbmParent = null");
					if (UserUtil.checkRole("ROLE_SYSORG_ORG_ADMIN") && orgRange != null && CollectionUtils.isNotEmpty(orgRange.getAuthOtherRootIds())) {
						whereBlock.append(" or sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(orgRange.getAuthOtherRootIds())).append(")");
					}
				} else {
					if (StringUtil.isNull(parentId) && orgRange != null && (orgRange.isShowMyDept() || CollectionUtils.isNotEmpty(orgRange.getAuthOtherRootIds()))) {
						whereBlock.append("(");
					}
					// 如果有查看范围限制，就取查看范围的根组织
					if (orgRange != null && CollectionUtils.isNotEmpty(orgRange.getRootDeptIds())) {
						whereBlock.append("sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(orgRange.getRootDeptIds())).append(")");
					} else {
						whereBlock.append("sysOrgElement.hbmParent = null");
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
				isBaseNode = true;
			}
		} else if ("null".equals(parentId)) {
			// 未指定部门/机构的节点
			// 如果有查看范围限制，获取根组织
			AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
			if (orgRange != null && CollectionUtils.isNotEmpty(orgRange.getRootDeptIds())) {
				whereBlock.append("sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(orgRange.getRootDeptIds())).append(")");
			} else {
				// 未指定部门/机构的节点
				whereBlock.append("sysOrgElement.hbmParent=null");
			}
			isNullNode = true;
		} else {
			// 其他普通节点
			whereBlock.append("sysOrgElement.hbmParent.fdId = :hbmParentId");
			hqlInfo.setParameter("hbmParentId", parentId);
		}

		// 通讯录只能查询内部组织
		whereBlock.append(" and (sysOrgElement.fdIsExternal is null or sysOrgElement.fdIsExternal = :fdIsExternal)");
		hqlInfo.setParameter("fdIsExternal", Boolean.FALSE);

		int treeOrgType = orgType;
		// 架构树中不展现群组信息，去除群组
		treeOrgType &= ~ORG_TYPE_GROUP;
		// 架构树中不展现角色信息，去除角色
		treeOrgType &= ~ORG_TYPE_ROLE;
		if ((treeOrgType & ORG_TYPE_POSTORPERSON) > 0) {
			// 若需要选择个人或岗位，机构和部门必须出现
			treeOrgType |= ORG_TYPE_ORGORDEPT;
		} else if ((treeOrgType & ORG_TYPE_DEPT) == ORG_TYPE_DEPT) {
			// 若需要选择部门，机构必须出现
			treeOrgType |= ORG_TYPE_ORG;
		}
		if (isBaseNode) {
			// 根节点不出现个人和岗位信息
			treeOrgType &= ~ORG_TYPE_POSTORPERSON;
		} else if (isNullNode) {
			// 未指定部门/机构的节点不出现机构和部门信息
			treeOrgType &= ~ORG_TYPE_ORGORDEPT;
		}
		hqlInfo.setWhereBlock(SysOrgHQLUtil.buildWhereBlock(treeOrgType, whereBlock.toString(), "sysOrgElement"));
		// 多语言
		hqlInfo.setOrderBy("sysOrgElement.fdOrgType desc, sysOrgElement.fdOrder, sysOrgElement." + SysLangUtil.getLangFieldName("fdName"));

		treeQuery.hqlInfo = hqlInfo;
		treeQuery.orgType = orgType;
		treeQuery.isBaseNode = isBaseNode;
		treeQuery.isNullNode = isNullNode;
		return treeQuery;
	}

	private ArrayList<Map<String, String>> getDetailListByParentAndOrgType(
			String curDeptId, String parentId, int treeOrgType,
			HQLInfo findInfo, RequestContext xmlContext, List<String> parentIds,boolean isAdmin) throws Exception {
		ArrayList<Map<String, String>> rtnMapList = new ArrayList<Map<String, String>>();
		// 岗位人员列表
		ArrayList<Map<String, String>> postMapList = new ArrayList<Map<String, String>>();
		HashMap<String, String> resultDataRow = null;
		// 因为岗位需要解析成人员，那么可能会存在人员重复，这里也需要做过滤
		List<String> personIds = new ArrayList<String>();
		long time = System.currentTimeMillis();
		boolean hasAuth = true;
		// 先处理部门/机构负责人
		if (!"root".equals(parentId)) {
			// 获取点击的部门/机构（就是上级部门）
			SysOrgElement parentElem = (SysOrgElement) sysOrgElementService.findByPrimaryKey(parentId);
			// 判断是否是部门/机构
			if (hasAuth && (parentElem.getFdOrgType().equals(SysOrgConstant.ORG_TYPE_ORG)
					|| parentElem.getFdOrgType().equals(SysOrgConstant.ORG_TYPE_DEPT))) {
				if (logger.isInfoEnabled()) {
					logger.info("准备查询机构/部门领导");
					time = System.currentTimeMillis();
				}
				// 取部门领导
				SysOrgElement deptLeaderOrg = parentElem.getHbmThisLeader();
				if (deptLeaderOrg == null) { // 如果本部门领导为空，则取上级领导
					deptLeaderOrg = parentElem.getHbmSuperLeader();
				}
				if (deptLeaderOrg != null && deptLeaderOrg.getFdIsBusiness()) {
					List<SysOrgElement> orgLeader = new ArrayList<SysOrgElement>();
					if (deptLeaderOrg.getFdOrgType().equals(SysOrgConstant.ORG_TYPE_PERSON)) { // 部门领导是“人员”
						// 过滤重复人员
						if (!personIds.contains(deptLeaderOrg.getFdId())) {
							orgLeader.add(deptLeaderOrg);
							personIds.add(deptLeaderOrg.getFdId());
						}
					} else if (deptLeaderOrg.getFdOrgType().equals(SysOrgConstant.ORG_TYPE_POST)) { // 部门领导是“岗位”
						List<SysOrgPerson> postPersons = deptLeaderOrg.getFdPersons();
						for (SysOrgPerson postPerson : postPersons) {
							// 过滤“与业务无关”的人员
							if (!deptLeaderOrg.getFdIsBusiness().booleanValue()) {
								continue;
							}
							// 过滤重复人员
							if (personIds.contains(postPerson.getFdId())) {
								continue;
							}
							SysOrgElement _postPerson = new SysOrgElement();
							cloneOrg(_postPerson, postPerson);
							// 岗位解析出来的人员排序使用当前岗位的排序号
							if(!parentId.equals(postPerson.getFdParent() == null ? "" : postPerson.getFdParent().getFdId())) {
								_postPerson.setFdOrder(deptLeaderOrg.getFdOrder());
							}
							orgLeader.add(_postPerson);
							personIds.add(_postPerson.getFdId());
						}
					}

					// 部门领导不过滤
					for (SysOrgElement leader : orgLeader) {
						rtnMapList.add(pullPerson(leader, parentId, xmlContext, true));
					}
				}
				if (logger.isInfoEnabled()) {
					logger.info("查询查询机构/部门领导结束，用时：" + (System.currentTimeMillis() - time) / 1000.0F + "秒");
				}
			}
			if (hasAuth) {
				if (logger.isInfoEnabled()) {
					logger.info("准备查询岗位人员");
					time = System.currentTimeMillis();
				}
				// 增加岗位人员
				HQLInfo info = new HQLInfo();
				String _whereBlock = SysOrgHQLUtil.buildWhereBlock(ORG_TYPE_POST,
						"sysOrgElement.hbmParent.fdId=:hbmParentId",
						"sysOrgElement");
				info.setParameter("hbmParentId", parentId);
				info.setWhereBlock(_whereBlock);
				info.setOrderBy("sysOrgElement.fdOrder");
				info.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
				List<SysOrgElement> postList = sysOrgElementService.findList(info);
				for (SysOrgElement post : postList) {
					List<?> ss = post.getFdPersons();
					// 岗位人员过滤
					ss = sysOrganizationStaffingLevelService.getStaffingLevelFilterResult((List<SysOrgElement>) ss);
					for (int i = 0; i < ss.size(); i++) {
						SysOrgElement soe = ((SysOrgElement) ss.get(i));
						if (BooleanUtils.isTrue(soe.getFdIsExternal())) {
							// 过滤生态组织
							continue;
						}
						if (soe.getFdIsAvailable() && soe.getFdIsBusiness()
								&& !personIds.contains(soe.getFdId())
								&& !parentId.equals(soe.getFdParent() == null ? "" : soe.getFdParent().getFdId())) {
							SysOrgElement _soe = new SysOrgElement();
							cloneOrg(_soe, soe);
							// 岗位解析出来的人员排序使用当前岗位的排序号
							_soe.setFdOrder(post.getFdOrder());
							postMapList.add(pullPerson(soe, parentId, xmlContext, false));
							personIds.add(_soe.getFdId());
						}
					}
				}
				if (logger.isInfoEnabled()) {
					logger.info("查询岗位人员结束，用时：" + (System.currentTimeMillis() - time) / 1000.0F + "秒");
				}
			}
		}
		// 如果是root节点，在前端会忽略人员，这里如果搜索了人员，会增加响应时间，所以仅查询机构/部门
		if ("root".equals(parentId)) {
			treeOrgType = 3;
		}
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			findInfo.setOrderBy(
					"sysOrgElement.fdIsExternal,sysOrgElement.fdOrgType desc, sysOrgElement.fdOrder, sysOrgElement."
							+ SysLangUtil.getLangFieldName("fdName"));
		} else {
			findInfo.setOrderBy(
					"sysOrgElement.fdOrgType desc, sysOrgElement.fdOrder, sysOrgElement."
							+ SysLangUtil.getLangFieldName("fdName"));
		}
		if(!isAdmin) {
			findInfo.setAuthCheckType("DIALOG_READER");
		}
		if (StringUtil.isNull(findInfo.getWhereBlock()) || !findInfo.getWhereBlock().contains("fdIsExternal")) {
			findInfo.setWhereBlock(StringUtil.linkString(findInfo.getWhereBlock(), " and ", "(sysOrgElement.fdIsExternal is null or sysOrgElement.fdIsExternal = false)"));
		}
		// 在同一级目录下最多只能查询500个元素，否则会引发性能问题
		findInfo.setRowSize(SysOrgUtil.LIMIT_RESULT_SIZE);
		findInfo.setGetCount(false);
		if (logger.isInfoEnabled()) {
			logger.info("查询数据：" + findInfo.getWhereBlock());
			time = System.currentTimeMillis();
		}
		List<SysOrgElement> findList = sysOrgElementService.findPage(findInfo).getList();
		if (logger.isInfoEnabled()) {
			logger.info("查询数据结束，用时：" + (System.currentTimeMillis() - time) / 1000.0F + "秒");
			time = System.currentTimeMillis();
			logger.info("准备进行 职级过滤，数量：" + findList.size());
		}
		if(!isAdmin) {
			// 职级过滤
			findList = sysOrganizationStaffingLevelService.getStaffingLevelFilterResult(findList);
			if (logger.isInfoEnabled()) {
				logger.info("职级过滤结束，用时：" + (System.currentTimeMillis() - time) / 1000.0F + "秒，过滤后数量：" + findList.size());
				time = System.currentTimeMillis();
				logger.info("准备进行 数据构建，数量：" + findList.size());
			}
		}
		PersonImageService info = PersonZoneHelp.getPersonImageService();
		String showStaffingLevel = new SysOrganizationConfig().getShowStaffingLevel();
		for (int i = 0; i < findList.size(); i++) {
			SysOrgElement elem = findList.get(i);
			resultDataRow = new HashMap<String, String>();
			if (elem.getFdOrgType().equals(ORG_TYPE_PERSON)) {
				// 过滤重复人员
				if (personIds.contains(elem.getFdId())) {
					continue;
				}
				// 将获取人员属性的方法抽取到这里，可以减少一些不必要的处理
				String img = null;
				SysOrgPerson person = null;
				if (elem instanceof SysOrgPerson) {
					person = (SysOrgPerson) elem;
				} else {
					person = (SysOrgPerson) sysOrgCoreService.format(elem);
				}
				if (info != null) {
					img = info.getHeadimage(elem.getFdId());
				}
				if (img == null) {
					String sex = person.getFdSex();
					if (StringUtil.isNotNull(sex)) {
						if ("M".equals(sex)) {
							img = PersonInfoServiceGetter.DEFAULT_MAN_IMG; // 男士
						} else if ("F".equals(sex)) {
							img = PersonInfoServiceGetter.DEFAULT_LADY_IMG; // 女士
						}
					} else {
						img = PersonInfoServiceGetter.DEFAULT_IMG;	// 性别为空，显示默认头像
					}
				}

				if (!PersonInfoServiceGetter.isFullPath(img)) {
					img = xmlContext.getContextPath() + img;
				}
				resultDataRow.put("img", img);
				String staffingLevel = "";
				// 智能应用组件选择组织架构人员使用此参数
				resultDataRow.put("uid", person.getFdLoginName());
				if ("true".equals(showStaffingLevel)) {
					SysOrganizationStaffingLevel sysOrganizationStaffingLevel = person.getFdStaffingLevel();
					if (sysOrganizationStaffingLevel != null) {
						staffingLevel = sysOrganizationStaffingLevel.getFdName();
					}
				}
				resultDataRow.put("staffingLevel", staffingLevel);
				resultDataRow.put("isLeader", "false");
				resultDataRow.put("personType", "inner");
				personIds.add(elem.getFdId());
			}
			// 用于排序
			resultDataRow.put("oriParentId", elem.getFdParent() != null ? elem.getFdParent().getFdId() : ""); // 原部门ID
			resultDataRow.put("order", elem.getFdOrder() != null ? elem.getFdOrder().toString() : ""); // 排序号
			resultDataRow.put("name", elem.getFdName()); // 名称

			if (SysOrgEcoUtil.IS_ENABLED_ECO) {
				resultDataRow.put("fdIsExternal", elem.getFdIsExternal() != null ? elem.getFdIsExternal().toString() : "false");
			}
			resultDataRow.put("parentId", StringUtil.isNull(parentId) ? "root" : parentId);
			resultDataRow.put("id", elem.getFdId());
			resultDataRow.put("nodeType", elem.getFdOrgType().toString());
			resultDataRow.put("isAvailable", elem.getFdIsAvailable().toString());
			resultDataRow.put("text", elem.getFdName());
			resultDataRow.put("title", elem.getFdName());
			if ((elem.getFdOrgType().intValue() & treeOrgType) == 0) {
				resultDataRow.put("href", "");
			}
			/** 以下为获取部门人数逻辑 */
			if (!"root".equals(parentId)) {
				resultDataRow.put("parentsName", elem.getFdParentsName());
				if (elem.getFdOrgType().equals(ORG_TYPE_ORG)
						|| elem.getFdOrgType().equals(ORG_TYPE_DEPT)) {
					if (StringUtil.isNull(curDeptId) || parentIds.contains(parentId)) {
						int num = getOrgPersonCountByOrg(elem);
						resultDataRow.put("personNum", String.valueOf(num));
					}

					if (elem.getFdId().equals(curDeptId)) {
						resultDataRow.put("isCurrent", "true");
					} else {
						resultDataRow.put("isCurrent", "false");
					}
				}
			}
			rtnMapList.add(resultDataRow);
		}
		// 追加岗位人员列表
		rtnMapList.addAll(postMapList);
		personIds = null;

		if (logger.isInfoEnabled()) {
			logger.info("数据构建结束，用时：" + (System.currentTimeMillis() - time) / 1000.0F + "秒");
			time = System.currentTimeMillis();
			logger.info("准备进行 数据排序，数量：" + findList.size());
		}

		// 人员需要重新排序
		ArrayList<Map<String, String>> orderMapList = new ArrayList<Map<String, String>>();
		Iterator<Map<String, String>> iter = rtnMapList.iterator();
		while (iter.hasNext()) {
			Map<String, String> data = iter.next();
			if (StringUtil.getIntFromString(data.get("nodeType"), 8) == 8) {
				orderMapList.add(data);
				iter.remove();
			}
		}

		// 数据排序
		Collections.sort(orderMapList, new Comparator<Map<String, String>>() {
			@Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
				Integer t1 = StringUtil.getIntFromString(o1.get("nodeType"), 8);
				Integer t2 = StringUtil.getIntFromString(o2.get("nodeType"), 8);

				Integer i1 = StringUtil.getIntFromString(o1.get("order"), Integer.MAX_VALUE);
				Integer i2 = StringUtil.getIntFromString(o2.get("order"), Integer.MAX_VALUE);

				if (t1.equals(t2)) {
					if (i1.equals(i2)) {
						if (t1.equals(8)) {
							return 0;
						} else {
							return o1.get("name").compareTo(o2.get("name"));
						}
					} else if (i1 > i2) {
						return 1;
					} else {
						return -1;
					}
				} else if (t1 > t2) {
					return -1;
				} else {
					return 1;
				}
			}
		});

		rtnMapList.addAll(orderMapList);

		if (logger.isInfoEnabled()) {
			logger.info("数据排序结束，用时：" + (System.currentTimeMillis() - time) / 1000.0F + "秒");
		}

		return rtnMapList;
	}

	private int getOrgPersonCountByOrg(SysOrgElement element) throws Exception {
		HQLInfo personNumInfo = new HQLInfo();
		personNumInfo.setSelectBlock("count(*)");
		personNumInfo.setWhereBlock(
				SysOrgHQLUtil.buildWhereBlock(ORG_TYPE_PERSON,
						"sysOrgElement.fdHierarchyId like :fdHierarchyId",
						"sysOrgElement"));
		personNumInfo.setParameter("fdHierarchyId",
				element.getFdHierarchyId() + "%");
		Object obj = sysOrgElementService.findFirstOne(personNumInfo);
		return  Integer.parseInt(obj.toString());
	}

	private HashMap<String, String> pullPerson(SysOrgElement person, String parentId,
											   RequestContext xmlContext, boolean isLeader) throws Exception {
		HashMap<String, String> resultDataRow = new HashMap<String, String>();
		// 用于排序
		resultDataRow.put("oriParentId", person.getFdParent() != null ? person.getFdParent().getFdId() : ""); // 原部门ID
		resultDataRow.put("order", person.getFdOrder() != null ? person.getFdOrder().toString() : ""); // 排序号
		resultDataRow.put("name", person.getFdName()); // 名称

		resultDataRow.put("parentId", parentId);
		resultDataRow.put("nodeType", person.getFdOrgType().toString());
		resultDataRow.put("id", person.getFdId());
		String __name = buildTextAndName(person, parentId);
		resultDataRow.put("text", StringEscapeUtils.escapeHtml(__name));
		resultDataRow.put("title", StringEscapeUtils.escapeHtml(__name));
		resultDataRow.put("isAvailable", person.getFdIsAvailable().toString());
		resultDataRow.put("isLeader", String.valueOf(isLeader));
		resultDataRow.put("personType", "inner");
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			resultDataRow.put("fdIsExternal", person.getFdIsExternal() != null ? person.getFdIsExternal().toString() : "false");
		}
		// 取岗位信息
		// resultDataRow.put("postName", buildPostNameByPerson(postPerson));
		SysOrgPerson _person = (SysOrgPerson) sysOrgElementService.findByPrimaryKey(person.getFdId(), SysOrgPerson.class, true);
		SysOrganizationStaffingLevel staffingLevel = _person.getFdStaffingLevel();
		resultDataRow.put("staffingLevelName", staffingLevel == null ? "" : staffingLevel.getFdName());
		resultDataRow.put("parentsName", person.getFdParentsName());
		OrgDialogUtil.setPersonAttrs(person, xmlContext.getContextPath(), resultDataRow);
		return resultDataRow;
	}

	private static class OrgTreeQuery {
		/**
		 * 查询信息
		 */
		private HQLInfo hqlInfo;
		/**
		 * 组织类型
		 */
		private int orgType;
		/**
		 * 是否无部门节点
		 */
		private boolean isNullNode;
		/**
		 * 是否根节点
		 */
		private boolean isBaseNode;

		private OrgTreeQuery() {
		}

		public static OrgTreeQuery getInstance(HQLInfo hqlInfo) {
			OrgTreeQuery query = new OrgTreeQuery();
			query.hqlInfo = hqlInfo;
			return query;
		}

	}

	/**
	 * 克隆所需的属性
	 *
	 * @param newOrg
	 * @param oldOrg
	 */
	private void cloneOrg(SysOrgElement newOrg, SysOrgElement oldOrg) {
		newOrg.setFdId(oldOrg.getFdId());
		newOrg.setFdName(oldOrg.getFdName());
		newOrg.setFdOrgType(oldOrg.getFdOrgType());
		newOrg.setFdIsAvailable(oldOrg.getFdIsAvailable());
		newOrg.setFdHierarchyId(oldOrg.getFdHierarchyId());
		newOrg.setFdParent(oldOrg.getFdParent());
		newOrg.setFdIsBusiness(oldOrg.getFdIsBusiness());
		newOrg.setFdPersons(oldOrg.getFdPersons());
		newOrg.setFdMemo(oldOrg.getFdMemo());
		newOrg.setFdOrder(oldOrg.getFdOrder());
	}

}
