package com.landray.kmss.sys.organization.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgAreaVisibleService;
import com.landray.kmss.sys.organization.service.ISysOrganizationVisibleService;
import com.landray.kmss.util.UserUtil;

/**
 * 组织根据场景隔离业务对象接口实现
 * 
 * @author
 * @version 1.0 2018-05-21
 */
public class SysOrgAreaVisibleServiceImp implements ISysOrgAreaVisibleService {
	private ISysOrganizationVisibleService sysOrganizationVisibleService;

	public ISysOrganizationVisibleService getSysOrganizationVisibleService() {
		return sysOrganizationVisibleService;
	}

	public void setSysOrganizationVisibleService(
			ISysOrganizationVisibleService sysOrganizationVisibleService) {
		this.sysOrganizationVisibleService = sysOrganizationVisibleService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public ISysOrgCoreService getSysOrgCoreService() {
		return sysOrgCoreService;
	}

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	/**
	 * 流程身份切换时，根据当前用户获取该场所下的角色
	 * 
	 * @param person
	 * @return
	 */
	@Override
    public List<Map<String, String>> getOrgProcessByArea(RequestContext context)
			throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Set<String> elementIds = sysOrganizationVisibleService
				.getPersonVisibleOrgIds(UserUtil.getUser());
		if (elementIds == null || elementIds.size() == 0) {
			// 找不到场合限制，只返回当前用户
			Map<String, String> user = new HashMap<String, String>();
			user.put("id", UserUtil.getUser().getFdId());
			user.put("name", UserUtil.getUser().getFdName());
			list.add(user);
			return list;
		}

		Set<String> currUserAllParentIds = new HashSet<String>();
		SysOrgElement parent = UserUtil.getUser().getFdParent();
		while (parent != null) {
			currUserAllParentIds.add(parent.getFdId());
			parent = parent.getFdParent();
		}
		boolean findUser = false;
		for (String limitId : elementIds) {
			if (currUserAllParentIds.contains(limitId)) {
				findUser = true;
			}
		}

		List<SysOrgPost> pp = new ArrayList<SysOrgPost>();
		String where = "sysOrgPost.hbmPersons.fdId='"
				+ UserUtil.getUser().getFdId() + "'";
		for (String limitId : elementIds) {
			SysOrgElement el = sysOrgCoreService.findByPrimaryKey(limitId);
			List posts = sysOrgCoreService.findAllChildren(el,
					SysOrgConstant.ORG_TYPE_POST, where);
			for (int i = 0; i < posts.size(); i++) {
				SysOrgPost p = (SysOrgPost) posts.get(i);
				pp.add(p);
			}
		}

		if (findUser) {
			Map<String, String> user = new HashMap<String, String>();
			user.put("id", UserUtil.getUser().getFdId());
			user.put("name", UserUtil.getUser().getFdName());
			list.add(user);
		}
		for (SysOrgPost p : pp) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", p.getFdId());
			map.put("name", p.getFdName());
			list.add(map);
		}
		if (list.isEmpty()) {
			Map<String, String> user = new HashMap<String, String>();
			user.put("id", UserUtil.getUser().getFdId());
			user.put("name", UserUtil.getUser().getFdName());
			list.add(user);
		}
		return list;
	}
}
