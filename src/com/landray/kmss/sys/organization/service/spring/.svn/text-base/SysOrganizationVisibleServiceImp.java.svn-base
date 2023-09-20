package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.appconfig.model.SysAppConfig;
import com.landray.kmss.sys.authentication.intercept.RoleValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.authorization.service.ISysAuthAreaService;
import com.landray.kmss.sys.cluster.interfaces.MessageCenter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.IUserUpdateDetailOper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.forms.SysOrganizationVisibleForm;
import com.landray.kmss.sys.organization.forms.SysOrganizationVisibleListForm;
import com.landray.kmss.sys.organization.model.SysOrgConfig;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrganizationVisible;
import com.landray.kmss.sys.organization.service.ISysOrgElementHideRangeService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrganizationVisibleService;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.sso.client.util.StringUtil;
import edu.emory.mathcs.backport.java.util.Arrays;

import java.util.*;

/**
 * 组织可见性业务接口实现
 *
 * @author
 * @version 1.0 2015-06-16
 */
public class SysOrganizationVisibleServiceImp extends BaseServiceImp implements
		ISysOrganizationVisibleService {

	private ISysAuthAreaService sysAuthAreaService;

	/**
	 * 组织架构部门隐藏
	 */
	private ISysOrgElementHideRangeService sysOrgElementHideRangeService;

	public void setSysOrgElementHideRangeService(ISysOrgElementHideRangeService sysOrgElementHideRangeService) {
		this.sysOrgElementHideRangeService = sysOrgElementHideRangeService;
	}

	public void setSysAuthAreaService(ISysAuthAreaService sysAuthAreaService) {
		this.sysAuthAreaService = sysAuthAreaService;
	}

	private ISysOrgElementService sysOrgElementService;

	private static SysOrganizationVisibleCache cache = null;

	public SysOrganizationVisibleCache getOrganizationVisibleCache() {
		if (cache == null) {
			cache = new SysOrganizationVisibleCache();
			try {
				updateCacheLocal(null);
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
		}

		if (cache.isOrgAeraEnable()) {
			// 启用地址本数据在用户场景下隔离,与启用组织可见性过滤互斥
			try {
				setCache4Area();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return cache;
	}

	public static void setCache(SysOrganizationVisibleCache cache) {
		SysOrganizationVisibleServiceImp.cache = cache;
	}

	public ISysOrgElementService getSysOrgElementService() {
		return sysOrgElementService;
	}

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		String id = super.add(modelObj);
		// updateCache((SysOrganizationVisible)modelObj);
		return id;
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		super.update(modelObj);
		// updateCache((SysOrganizationVisible)modelObj);
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		super.delete(modelObj);
		// updateCache((SysOrganizationVisible)modelObj);
	}

	/**
	 * 更新缓存
	 *
	 * @param sysOrganizationVisible
	 * @throws Exception
	 */
	@Deprecated
	private void updateCacheOld(SysOrganizationVisible sysOrganizationVisible)
			throws Exception {
		List<SysOrgElement> principals = sysOrganizationVisible
				.getVisiblePrincipals();
		SysOrganizationVisibleCache cache = getOrganizationVisibleCache();
		MessageCenter.getInstance()
				.sendToOther(
						new SysOrgMessage(
								SysOrgMessageType.ORG_MESSAGE_VISIBLE_UPDATE));
		cache.clearOrganizationVisibleMap();

		for (SysOrgElement principal : principals) {
			cache
					.put(principal.getFdId(), getSubordinates(principal
							.getFdId()));
		}
	}

	private boolean getOrgAeraEnableFromDB(SysOrgConfig orgConfig)
			throws Exception {
		boolean isOrgAeraEnable = false;
		if (orgConfig == null) {
			orgConfig = new SysOrgConfig();
		}
		String orgAeraEnable_str = orgConfig.getOrgAeraEnable();
		if (StringUtil.isNotNull(orgAeraEnable_str)) {
			isOrgAeraEnable = Boolean.parseBoolean(orgAeraEnable_str);
		}
		return isOrgAeraEnable;
	}

	private boolean getOrgVisibleEnableFromDB(SysOrgConfig orgConfig)
			throws Exception {
		boolean isOrgVisibleEnable = false;
		if (orgConfig == null) {
			orgConfig = new SysOrgConfig();
		}
		String orgVisibleEnable_str = orgConfig.getOrgVisibleEnable();
		if (StringUtil.isNotNull(orgVisibleEnable_str)) {
			isOrgVisibleEnable = Boolean.parseBoolean(orgVisibleEnable_str);
		}
		return isOrgVisibleEnable;
	}

	private int getDefaultVisibleLevelFromDB(SysOrgConfig orgConfig)
			throws Exception {
		int defaultVisibleLevel = 0;
		if (orgConfig == null) {
			orgConfig = new SysOrgConfig();
		}
		String defaultVisibleLevel_str = orgConfig.getDefaultVisibleLevel();
		if (StringUtil.isNotNull(defaultVisibleLevel_str)) {
			defaultVisibleLevel = Integer.parseInt(defaultVisibleLevel_str);
		}
		return defaultVisibleLevel;
	}

	@Override
	public void updateCacheAll(SysOrgConfig orgConfig) throws Exception {
		MessageCenter.getInstance()
				.sendToOther(
						new SysOrgMessage(
								SysOrgMessageType.ORG_MESSAGE_VISIBLE_UPDATE));
		updateCacheLocal(orgConfig);
	}

	private void setCache4Area() throws Exception {
		String userId = UserUtil.getUser().getFdId();
		String currAreaId = UserUtil.getKMSSUser().getAuthAreaId();
		if (StringUtil.isNull(currAreaId)) {
			return;
		}
		boolean isSwitchedArea = false;
		if (cache.getUserCurrAreaId().containsKey(userId)) {
			if (!currAreaId.equals(cache.getUserCurrAreaId().get(userId))) {
				isSwitchedArea = true;
				cache.getUserCurrAreaId().put(userId, currAreaId);
			}
		} else {
			cache.getUserCurrAreaId().put(userId, currAreaId);
			isSwitchedArea = true;
		}
		if (isSwitchedArea) {
			Set<String> subordinates = new HashSet<String>();
			SysAuthArea sysAuthArea = (SysAuthArea) sysAuthAreaService
					.findByPrimaryKey(currAreaId);
			for (SysOrgElement el : sysAuthArea.getAuthAreaOrg()) {
				subordinates.add(el.getFdHierarchyId());
			}
			cache.put(userId, subordinates);
		}
	}

	@Override
	public void updateCacheLocal(SysOrgConfig orgConfig) throws Exception {
		SysOrganizationVisibleCache cache = getOrganizationVisibleCache();

		boolean isOrgAeraEnable = getOrgAeraEnableFromDB(orgConfig);
		boolean isOrgVisibleEnable = getOrgVisibleEnableFromDB(orgConfig);
		int defaultVisibleLevel = getDefaultVisibleLevelFromDB(orgConfig);

		if (isOrgAeraEnable) {
			// 启用地址本数据在用户场景下隔离,与启用组织可见性过滤互斥
			isOrgVisibleEnable = true;// 借用组织可见性过滤的处理逻辑，只换取获取组织数据接口
			defaultVisibleLevel = -1;// 不使用普通用户的层级过滤
		}

		cache.setOrgAeraEnable(isOrgAeraEnable);
		cache.setOrgVisibleEnable(isOrgVisibleEnable);
		cache.setDefaultVisibleLevel(defaultVisibleLevel);
		cache.clearOrganizationVisibleMap();
		if (isOrgAeraEnable) {
			// 启用地址本数据在用户场景下隔离,与启用组织可见性过滤互斥
			setCache4Area();
			return;
		}

		if (!isOrgVisibleEnable) {
			return;
		}

		List exist = findList(new HQLInfo());
		if (exist != null && !exist.isEmpty()) {
			for (int i = 0; i < exist.size(); i++) {
				SysOrganizationVisible sysOrganizationVisible = (SysOrganizationVisible) exist
						.get(i);
				List<SysOrgElement> principals = sysOrganizationVisible
						.getVisiblePrincipals();
				for (SysOrgElement principal : principals) {
					cache.put(principal.getFdId(), getSubordinates(principal
							.getFdId()));
				}
			}
		}
	}

	@Deprecated
	private int getOrgLevel(String[] ids, String orgElementId) {
		for (int i = 0; i < ids.length; i++) {
			if (ids[i].equals(orgElementId)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 可视组织列表中的数据可能本身有层级关系，这里过滤点子孙节点，只返回顶级节点
	 *
	 * @param visibleOrgIds
	 * @return
	 * @throws Exception
	 */
	private Set<String> getPersonRootVisibleOrgs(Set<String> visibleOrgIds)
			throws Exception {
		// Set<SysOrgElement> visibleOrgs = new HashSet<SysOrgElement>();
		if (visibleOrgIds == null || visibleOrgIds.size() <= 0) {
			return null;
		}
		// String[] visibleOrgIds_array = new String[visibleOrgIds.size()];
		// visibleOrgIds.toArray(visibleOrgIds_array);
		// List<SysOrgElement> visibleOrgs = sysOrgElementService
		// .findByPrimaryKeys(visibleOrgIds_array);
		return filterSubordinateOrgId(visibleOrgIds);

	}

	@Deprecated
	private Set<SysOrgElement> getPersonVisibleOrgs(
			List<SysOrganizationVisible> sysOrganizationVisibles,
			String hierarchyId_person) {
		Set<String> hiras = new HashSet<String>();
		Integer biggestLevel = 0;
		String[] ids = hierarchyId_person.substring(1).split("x");
		Set<SysOrganizationVisible> sysOrganizationVisible_set = new HashSet<SysOrganizationVisible>();
		for (SysOrganizationVisible v : sysOrganizationVisibles) {
			List<SysOrgElement> list = v.getVisiblePrincipals();
			for (SysOrgElement e : list) {
				int level = getOrgLevel(ids, e.getFdId());
				if (level > biggestLevel) {
					biggestLevel = level;
					sysOrganizationVisible_set.clear();
					sysOrganizationVisible_set.add(v);
				} else if (level == biggestLevel) {
					sysOrganizationVisible_set.add(v);
				}
			}
		}
		List<SysOrgElement> visibleOrgs = new ArrayList<SysOrgElement>();
		for (SysOrganizationVisible v : sysOrganizationVisible_set) {
			List<SysOrgElement> list = v.getVisibleSubordinates();
			for (SysOrgElement e : list) {
				visibleOrgs.add(e);
			}
			// todo:如果可见组织有上下级关系，怎么处理？
		}
		return filterSubordinateOrg_old(visibleOrgs);

	}

	/**
	 * 过滤可见组织中的子节点，比如可见组织有A、B,如果B属于A的子孙节点，那么可见性以A为标准(不包含上下级机构类型的过滤)
	 *
	 * @param visibleOrgHierarchyIds
	 * @return
	 */
	private Set<String> filterSubordinateOrgId(
			Set<String> visibleOrgHierarchyIds) {
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
		Set<String> visibleOrgs_result = new HashSet<String>();
		for (String id : visibleOrgs_result_tmp) {
			id = id.substring(0, id.length() - 1);
			id = id.substring(id.lastIndexOf("x") + 1);
			visibleOrgs_result.add(id);
		}
		return visibleOrgs_result;

	}

	/**
	 * 过滤可见组织中的子节点，比如可见组织有A、B,如果B属于A的子孙节点，那么可见性以A为标准
	 *
	 * @param visibleOrgs
	 * @return
	 */
	private Set<SysOrgElement> filterSubordinateOrg_old(
			List<SysOrgElement> visibleOrgs) {
		if (visibleOrgs == null) {
			return null;
		}
		Set<SysOrgElement> visibleOrgs_result = new HashSet<SysOrgElement>();
		Set<SysOrgElement> visibleOrgs_add = new HashSet<SysOrgElement>();
		Set<SysOrgElement> visibleOrgs_del = new HashSet<SysOrgElement>();
		String hierarchyId1 = null;
		for (SysOrgElement ele1 : visibleOrgs) {
			boolean add = true;
			hierarchyId1 = ele1.getFdHierarchyId();
			String hierarchyId2 = null;
			for (SysOrgElement ele2 : visibleOrgs_result) {
				hierarchyId2 = ele2.getFdHierarchyId();
				if (hierarchyId2.startsWith(hierarchyId1)) {
					add = false;
					visibleOrgs_del.add(ele2);
					visibleOrgs_add.add(ele1);
					continue;
				} else if (hierarchyId1.startsWith(hierarchyId2)) {
					add = false;
					break;
				}
			}
			if (add) {
				visibleOrgs_result.add(ele1);
			} else {
				visibleOrgs_result.removeAll(visibleOrgs_del);
				visibleOrgs_result.addAll(visibleOrgs_add);
				visibleOrgs_del.clear();
				visibleOrgs_add.clear();
			}
		}
		return visibleOrgs_result;

	}

	public static void main(String[] args) {
		String s = "x1x2x".substring(1);
		System.out.println(s.split("x").length);
	}

	/**
	 * 获取用户可见的顶级组织数据（会过滤上下级组织类型的数据，在地址本中保留上下级组织的层级关系）
	 *
	 * @param person
	 * @return
	 * @throws Exception
	 */
	@Override
	public Set<String> getPersonRootVisibleOrgIds(SysOrgPerson person)
			throws Exception {
		// 先获取所属部门的成员查看组织范围
		Set<String> visibleIds = getPersonVisibleOrgIds(person);
		return getPersonRootVisibleOrgIds(visibleIds);
	}

	@Override
	public Set<String> getPersonRootVisibleOrgIds(KMSSUser kmssUser) throws Exception {
		// 先获取所属部门的成员查看组织范围
		Set<String> visibleIds = getPersonVisibleOrgIds(kmssUser);
		// 组织查看范围对机构的层级会隔离，这里就不合并层级机构了
//		return getPersonRootVisibleOrgIds(visibleIds);
		return visibleIds;
	}

	private Set<String> getPersonRootVisibleOrgIds(Set<String> visibleIds) throws Exception {
		if (visibleIds == null) {
			return null;
		}
		Set<String> visibleIds_not_org = new HashSet<String>();
		Set<String> visibleIds_org = new HashSet<String>();
		for (String id : visibleIds) {
			if (StringUtil.isNull(id)) {
                continue;
            }
			SysOrgElement e = (SysOrgElement) sysOrgElementService
					.findByPrimaryKey(id, null, true);
			if (e == null) {
                continue;
            }
			if (e.getFdOrgType() == 1) {
				visibleIds_org.add(SysOrgUtil.buildHierarchyIdIncludeOrg(e));
			} else {
				visibleIds_not_org.add(e.getFdId());
			}
		}
		Set<String> ids = filterSubordinateOrgIdHandleBrokenLink(visibleIds_org);
		if (ids != null) {
			visibleIds_not_org.addAll(ids);
		}
		return visibleIds_not_org;
	}

	/**
	 * 过滤可见组织中的子节点，比如可见组织有A、B,如果B属于A的子孙节点，那么可见性以A为标准(同时处理断链的情况)
	 *
	 * @param visibleOrgs
	 * @return
	 */
	private Set<String> filterSubordinateOrgIdHandleBrokenLink(
			Set<String> visibleOrgHierarchyIds) {
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
			String parentIdsStr = hierarchyId2.substring(0, hierarchyId2
					.length() - 1);
			parentIdsStr = parentIdsStr.substring(0, parentIdsStr
					.lastIndexOf("x") + 1);
			// 判断上级节点是否在可见组织里面，如果有，表示不会断链，如果没有则表示断链
			if (!subLinkIds.contains(parentIdsStr)) {
				// 确认断链
				visibleOrgs_result_tmp.add(hierarchyId2);
			}
			subLinkIds.add(hierarchyId2);
		}

		Set<String> visibleOrgs_result = new HashSet<String>();
		for (String id : visibleOrgs_result_tmp) {
			id = id.substring(0, id.length() - 1);
			id = id.substring(id.lastIndexOf("x") + 1);
			visibleOrgs_result.add(id);
		}
		return visibleOrgs_result;

	}

	@Deprecated
	private Set<String> filterSubordinateOrgIdHandleBrokenLink_old2(
			Set<String> visibleOrgHierarchyIds) {
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
			boolean add = true;
			for (String hierarchyId1 : visibleOrgs_result_tmp) {
				// 如果是下级节点，需要判断，如果断链则需要添加，非断链则不需要添加
				if (hierarchyId2.startsWith(hierarchyId1)) {
					String id_sub = hierarchyId2.substring(hierarchyId1
							.length());
					String[] id_subs = id_sub.split("x");
					// 如果断链(可能存在这种情况，x1x2x3，1已经添加，2判断非断链从而不添加，3跟1对比判断为断链，但3实际上是没有断链的)
					if (id_subs.length > 1) {
						add = false;
						String parentIdsStr = hierarchyId2.substring(0,
								hierarchyId2.length() - 1);
						parentIdsStr = parentIdsStr.substring(0, parentIdsStr
								.lastIndexOf("x") + 1);
						if (!subLinkIds.contains(parentIdsStr)) {
							// 确认断链
							add = true;
							break;
						}

					} else if (id_subs.length == 1) {
						add = false;
					}
					break;
				}
			}
			if (add) {
				visibleOrgs_result_tmp.add(hierarchyId2);
			}
			subLinkIds.add(hierarchyId2);
		}

		Set<String> visibleOrgs_result = new HashSet<String>();
		for (String id : visibleOrgs_result_tmp) {
			id = id.substring(0, id.length() - 1);
			id = id.substring(id.lastIndexOf("x") + 1);
			visibleOrgs_result.add(id);
		}
		return visibleOrgs_result;

	}

	@Deprecated
	private Set<String> filterSubordinateOrgIdHandleBrokenLink_old(
			Set<String> visibleOrgHierarchyIds) {
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
			boolean add = true;
			for (String hierarchyId1 : visibleOrgs_result_tmp) {
				// 如果是下级节点，需要判断，如果断链则需要添加，非断链则不需要添加
				if (hierarchyId2.startsWith(hierarchyId1)) {
					String id_sub = hierarchyId2.substring(hierarchyId1
							.length());
					String[] id_subs = id_sub.split("x");
					// 如果断链(可能存在这种情况，x1x2x3，1已经添加，2判断非断链从而不添加，3跟1对比判断为断链，但3实际上是没有断链的)
					if (id_subs.length > 1) {
						add = false;
						for (int i = 0; i < id_subs.length - 1; i++) {
							if (!subLinkIds.contains(id_subs[i])) {
								// 确认断链
								add = true;
								break;
							}
						}
						subLinkIds.addAll(Arrays.asList(id_subs));

					} else if (id_subs.length == 1) {
						add = false;
						subLinkIds.add(id_subs[0]);
					}
					break;
				}
			}
			if (add) {
				visibleOrgs_result_tmp.add(hierarchyId2);
			}
		}

		Set<String> visibleOrgs_result = new HashSet<String>();
		for (String id : visibleOrgs_result_tmp) {
			id = id.substring(0, id.length() - 1);
			id = id.substring(id.lastIndexOf("x") + 1);
			visibleOrgs_result.add(id);
		}
		return visibleOrgs_result;

	}

	@Override
	public Set<String> getPersonVisibleOrgIds(SysOrgPerson person) {
		if (!SysOrgUtil.isOrgVisibleEnabled()) {
			return null;
		}

		try {
			Set<String> visibleIds = getPersonVisibleIds(person);

			return visibleIds;

		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Set<String> getPersonVisibleOrgIds(KMSSUser kmssUser) {
		if (!SysOrgUtil.isOrgVisibleEnabled()) {
			return null;
		}

		try {
			Set<String> visibleIds = getPersonVisibleIds(kmssUser);

			return visibleIds;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从缓存中获取用户的可视部门的id
	 *
	 * @param person
	 * @return
	 * @throws Exception
	 */
	@Override
	public Set<String> getPersonVisibleIds(SysOrgPerson person)
			throws Exception {
		List<String> principalIds = SysOrgUtil.getPersonRelatedIds();
		return getPersonVisibleIds(person, principalIds);
	}

	@Override
	public Set<String> getPersonVisibleIds(KMSSUser kmssUser) throws Exception {
		List<String> principalIds = kmssUser.getRawUserAuthInfo().getAuthOrgIds();
		return getPersonVisibleIds(kmssUser.getPerson(), principalIds);
	}

	private Set<String> getPersonVisibleIds(SysOrgPerson person, List<String> principalIds) throws Exception {
		SysOrganizationVisibleCache cache = getOrganizationVisibleCache();
		if (principalIds == null || principalIds.size() <= 0) {
			return null;
		}
		Set<String> visibleIds_all = null;
		for (String principalId : principalIds) {
			Set<String> visibleIds = cache.get(principalId);
			if (visibleIds == null) {
				visibleIds = getSubordinates(principalId);
				cache.put(principalId, visibleIds);
			}
			if (visibleIds.size() > 0) {
				if (visibleIds_all == null) {
					visibleIds_all = new HashSet<String>();
				}
				visibleIds_all.addAll(visibleIds);
			}
		}

		if (visibleIds_all != null) {


			if (!cache.isOrgAeraEnable()) {
				// 在不启用地址本数据在用户场景下,但启用了组织可见性过滤时才把当前用户的部门加入
				SysOrgElement parent = person.getFdParent();
				if (parent != null) {
					visibleIds_all.add(parent.getFdHierarchyId());
				}
			}

			return getPersonRootVisibleOrgs(visibleIds_all);
		}

		Set<String> visibleIds = getDefaultVisibleOrgIds(person);

		return visibleIds;
	}

	@Override
	public boolean isOrgVisibleEnable() throws Exception {
		SysOrganizationVisibleCache cache = getOrganizationVisibleCache();

		boolean isOrgVisibleEnable = cache.isOrgVisibleEnable();

		return isOrgVisibleEnable;
	}

	@Override
	public boolean isOrgAeraEnable() throws Exception {
		SysOrganizationVisibleCache cache = getOrganizationVisibleCache();
		boolean isOrgAeraEnable = cache.isOrgAeraEnable();
		return isOrgAeraEnable;
	}

	@Override
	public int getDefaultVisibleLevel() throws Exception {
		SysOrganizationVisibleCache cache = getOrganizationVisibleCache();

		int defaultVisibleLevel = cache.getDefaultVisibleLevel();

		return defaultVisibleLevel;
	}

	/**
	 * 获取某个 部门/机构 的所有可视组织
	 *
	 * @param principalId
	 * @return
	 * @throws Exception
	 */
	private Set<String> getSubordinates_old(String principalId)
			throws Exception {
		Set<String> subordinates = new HashSet<String>();
		HQLInfo info = new HQLInfo();
		info.setSelectBlock("sysOrganizationVisible.visibleSubordinates.fdId");
		info
				.setJoinBlock("left join sysOrganizationVisible.visiblePrincipals as visiblePrincipal");
		info.setWhereBlock("visiblePrincipal.fdId = :principalId");
		info.setParameter("principalId", principalId);
		List<String> list = findList(info);
		for (String subordinateId : list) {
			subordinates.add(subordinateId);
		}

		return subordinates;
	}

	/**
	 * 获取某个 部门/机构 的所有可视组织
	 *
	 * @param principalId
	 * @return
	 * @throws Exception
	 */
	private Set<String> getSubordinates(String principalId) throws Exception {

		Set<String> subordinates = new HashSet<String>();
		HQLInfo info = new HQLInfo();
		info
				.setSelectBlock("sysOrganizationVisible.visibleSubordinates.fdHierarchyId");
		info
				.setJoinBlock("left join sysOrganizationVisible.visiblePrincipals as visiblePrincipal");
		info.setWhereBlock("visiblePrincipal.fdId = :principalId");
		info.setParameter("principalId", principalId);
		List<String> list = findList(info);
		for (String subordinateId : list) {
			subordinates.add(subordinateId);
		}

		return subordinates;
	}

	@Override
	public List<SysOrganizationVisible> findByPrincipals(String[] principalIds)
			throws Exception {
		if (principalIds == null || principalIds.length <= 0) {
			return null;
		}

		HQLInfo info = new HQLInfo();
		info
				.setJoinBlock("left join sysOrganizationVisible.visiblePrincipals as visiblePrincipal");
		String whereBlock = HQLUtil.buildLogicIN("visiblePrincipal.fdId",
				Arrays.asList(principalIds));
		info.setWhereBlock(whereBlock);
		List<SysOrganizationVisible> list = findList(info);
		return list;

	}

	@Override
	public List<SysOrganizationVisible> findBySubordinates(String[] subordinates)
			throws Exception {
		if (subordinates == null || subordinates.length <= 0) {
			return null;
		}

		HQLInfo info = new HQLInfo();
		info
				.setJoinBlock("left join sysOrganizationVisible.visibleSubordinates as visibleSubordinate");
		String whereBlock = HQLUtil.buildLogicIN("visibleSubordinate.fdId",
				Arrays.asList(subordinates));
		info.setWhereBlock(whereBlock);
		List<SysOrganizationVisible> list = findList(info);
		return list;

	}

	@Override
	public Set<String> getPersonVisibleAllOrgIds(SysOrgPerson person) {
		// 判断所属部门
		Set<String> elementIds = getPersonVisibleOrgIds(person);
		if (elementIds == null) {
			try {
				elementIds = getDefaultVisibleOrgIds(person);
				if (elementIds == null) {
					return null;
				}

			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
		}

		return elementIds;
	}

	@Deprecated
	public SysOrgElement getDefaultVisibleOrg_old(SysOrgPerson person)
			throws Exception {
		int defaultVisibleLevel = getDefaultVisibleLevel();
		if (defaultVisibleLevel <= 0) {
			return null;
		}
		return getElementByLevel_old(person.getFdHierarchyId(),
				defaultVisibleLevel);

	}

	public Set<String> getDefaultVisibleOrgIds(SysOrgPerson person)
			throws Exception {
		int defaultVisibleLevel = getDefaultVisibleLevel();
		if (defaultVisibleLevel <= 0) {
			return null;
		}

		Set<String> result = new HashSet<String>();
		result.add(getElementIdByLevel(person.getFdHierarchyId(),
				defaultVisibleLevel));
		List<SysOrgElement> posts = person.getFdPosts();
		for (SysOrgElement e : posts) {
			result.add(getElementIdByLevel(e.getFdHierarchyId(),
					defaultVisibleLevel));
		}

		return result;

	}

	public String getElementIdByLevel(String hierarchyId, int level)
			throws Exception {
		String[] ids = hierarchyId.split("x");
		int level_biggest = ids.length - 2;
		if (level_biggest < 0) {
			throw new Exception("层级id不正确：" + hierarchyId);
		} else if (level_biggest == 0) {
			// 用户没有上级，返回自身
			return ids[1];
		}
		if (level > level_biggest) {
			level = level_biggest;
		}
		return ids[level];
	}

	public SysOrgElement getElementByLevel_old(String hierarchyId, int level)
			throws Exception {
		String[] ids = hierarchyId.split("x");
		int level_biggest = ids.length - 2;
		if (level_biggest < 0) {
			throw new Exception("层级id不正确：" + hierarchyId);
		} else if (level_biggest == 0) {
			// 用户没有上级，返回自身
			return (SysOrgElement) sysOrgElementService
					.findByPrimaryKey(ids[1]);
		}
		if (level > level_biggest) {
			level = level_biggest;
		}
		return (SysOrgElement) sysOrgElementService
				.findByPrimaryKey(ids[level]);
	}

	/**
	 * 合并机构的所有下属机构
	 *
	 * @param childOrgs
	 * @param parentOrgs
	 */
	@Deprecated
	private void addChildOrg(Set<SysOrgElement> childOrgs,
							 List<SysOrgElement> parentOrgs) {
		for (SysOrgElement ele : parentOrgs) {
			if (ele.getFdOrgType() == SysOrgConstant.ORG_TYPE_ORG) {
				childOrgs.add(ele);
				addChildOrg(childOrgs, ele.getFdChildren());
			}
		}

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<SysOrganizationVisible> getAllOrganizationVisible() {
		try {
			return findList(null, null);
		} catch (Exception e) {
			throw new KmssRuntimeException(e);
		}
	}

	public void updateVisibleList(List<SysOrganizationVisibleForm> list,
								  RequestContext requestContext) throws Exception {
		List exist = findList(new HQLInfo());
		IUserUpdateDetailOper oper = null;
		if (UserOperHelper.allowLogOper("sysAppConfigUpdate", getModelName())) {
			oper = UserOperContentHelper.putUpdate(SysAppConfig.class.getName(),
					"com.landray.kmss.sys.organization.model.SysOrganizationVisible",
					"").createOper4Detail("visible");
		}
		if (exist != null && !exist.isEmpty()) {
			for (int i = 0; i < exist.size(); i++) {
				SysOrganizationVisible visible = (SysOrganizationVisible) exist.get(i);
				if (oper != null) {
					oper.putDelete(visible).createOper4Detail("visiblePrincipals")
							.putDeletes(visible.getVisiblePrincipals());
					oper.putDelete(visible).createOper4Detail("visibleSubordinates")
							.putDeletes(visible.getVisibleSubordinates());
				}
				delete(visible);
			}
		}
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				SysOrganizationVisible model = (SysOrganizationVisible)convertFormToModel(list.get(i), null,
						requestContext);
				if (model == null) {
                    throw new NoRecordException();
                }
				update(model);
				if (oper != null) {
					oper.putUpdate(model).createOper4Detail("visiblePrincipals")
							.putDeletes(model.getVisiblePrincipals());
					oper.putUpdate(model).createOper4Detail("visibleSubordinates")
							.putDeletes(model.getVisibleSubordinates());
				}
			}
		}
		// updateCacheAll();
	}

	@Override
	public void updateAll(
			SysOrganizationVisibleListForm sysOrganizationVisibleListForm,
			RequestContext requestContext) throws Exception {
		// TODO 自动生成的方法存根
		if ("true".equals(sysOrganizationVisibleListForm.getIsOrgAeraEnable())) {
			updateVisibleList(null, requestContext);
		} else {
			List<SysOrganizationVisibleForm> list = sysOrganizationVisibleListForm
					.getSysOrganizationVisibleFormList();
			updateVisibleList(list, requestContext);
		}

		SysOrgConfig orgConfig = new SysOrgConfig();
		orgConfig.setFdType(1);
		orgConfig.setOrgAeraEnable(sysOrganizationVisibleListForm
				.getIsOrgAeraEnable());
		orgConfig.setOrgVisibleEnable(sysOrganizationVisibleListForm
				.getIsOrgVisibleEnable());
		orgConfig.setDefaultVisibleLevel(sysOrganizationVisibleListForm
				.getDefaultVisibleLevel());
		orgConfig.save();

		updateCacheAll(orgConfig);
	}

	@Override
	public Set<String> getDialogRootVisibleOrgIds() {
		if (!SysOrgUtil.isOrgVisibleEnabled()) {
			return null;
		}

		KMSSUser user = UserUtil.getKMSSUser();
		if (user != null && user.isAdmin()) {
			return null;
		}

		ValidatorRequestContext context = new ValidatorRequestContext();
		context.setUser(user);
		context.addValidatorParas("role=ROLE_SYSORG_ORG_ADMIN");
		RoleValidator roleValidator = (RoleValidator) SpringBeanUtil
				.getBean("roleValidator");
		if (roleValidator.validate(context)) {
			return null;
		}

		return getPersonVisibleOrgIds(UserUtil.getUser());
	}

	private RoleValidator roleValidator;

	public void setRoleValidator(RoleValidator roleValidator) {
		this.roleValidator = roleValidator;
	}

	public RoleValidator getRoleValidator() {
		return roleValidator;
	}

	@Override
	public Set<String> getPersonAuthVisibleOrgIds(KMSSUser user) {
		// TODO 自动生成的方法存根
		if (user == null) {
			return new HashSet<String>();
		}
		if (user.isAdmin()) {
			return null;
		}
		String validatorParas = "role=ROLE_SYSORG_DIALOG_USER";

		ValidatorRequestContext context = new ValidatorRequestContext();
		context.setUser(user);
		context.addValidatorParas(validatorParas);
		if (roleValidator.validate(context)) {
			return null;
		}

		Set<String> elementIds = getPersonVisibleOrgIds(user.getPerson());

		if (elementIds == null || elementIds.size() == 0) {
			return null;
		}
		return elementIds;
	}

}
