package com.landray.kmss.sys.organization.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.sys.authentication.filter.FilterContext;
import com.landray.kmss.sys.authentication.filter.HQLFragment;
import com.landray.kmss.sys.authentication.filter.IAuthenticationFilter;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgElementExternal;
import com.landray.kmss.sys.organization.service.ISysOrgElementExternalService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 可使用人员过滤器
 * 可使用人员的权限为：可以管理和查看自己创建的组织/自己创建的组织的子组织/人员
 * @author zby
 *
 */
public class AuthOrgCreatorFilter implements IAuthenticationFilter{
	private ISysOrgCoreService sysOrgCoreService;
	
	private ISysOrgElementService sysOrgElementService;
	
	private ISysOrgElementExternalService sysOrgElementExternalService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}
	
	public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}
	
	public void setSysOrgElementExternalService(ISysOrgElementExternalService sysOrgElementExternalService) {
		this.sysOrgElementExternalService = sysOrgElementExternalService;
	}

	@Override
	public int getAuthHQLInfo(FilterContext ctx) throws Exception {
		if (!SysOrgEcoUtil.IS_ENABLED_ECO) {
			ctx.setHqlFragment(new HQLFragment("1=2"));
			return FilterContext.RETURN_VALUE;
		}
		// 获取当前登陆人创建的组织
		List<String> elementIds = sysOrgCoreService.findByCreator(UserUtil.getUser());
		if (elementIds == null || elementIds.size() == 0) {
			ctx.setHqlFragment(new HQLFragment("1=2"));
			return FilterContext.RETURN_VALUE;
		}
		HQLFragment hqlFragment = new HQLFragment();
		
		String[] idArr = new String[] {};
		idArr = elementIds.toArray(idArr);
		List<SysOrgElement> elems = sysOrgElementService.findByPrimaryKeys(idArr);

		// 获取上面组织所属的组织类型
		List<String> topOrgIds = new ArrayList<String>();
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (SysOrgElement elem : elems) {
			// 过滤非生态组织
			if (!BooleanUtils.isTrue(elem.getFdIsExternal())) {
				elementIds.remove(elem.getFdId());
				continue;
			}
			String hierarchyId = elem.getFdHierarchyId();
			if (BaseTreeConstant.HIERARCHY_INVALID_FLAG.equals(hierarchyId)) {
				elementIds.remove(elem.getFdId());
				continue;
			}
			String topOrgId = hierarchyId.split(BaseTreeConstant.HIERARCHY_ID_SPLIT)[1];
			topOrgIds.add(topOrgId);
			List<String> ids = map.get(topOrgId);
			if (CollectionUtils.isEmpty(ids)) {
				ids = new ArrayList<String>();
				map.put(topOrgId, ids);
			}
			ids.add(elem.getFdId());
		}
		
		if (CollectionUtils.isEmpty(topOrgIds)) {
			ctx.setHqlFragment(new HQLFragment("1=2"));
			return FilterContext.RETURN_VALUE;
		}
		
		String[] topArr = new String[] {};
		topArr = topOrgIds.toArray(topArr);
		List<SysOrgElementExternal> topOrgs = sysOrgElementExternalService.findByPrimaryKeys(topArr);
		
		//从自己创建的组织中移除自己没在所属组织类型的可读人员的组织
		for (SysOrgElementExternal external : topOrgs) {
			List<SysOrgElement> authReaders = external.getAuthReaders();
			if (CollectionUtils.isEmpty(authReaders)) {
				elementIds.removeAll(map.get(external.getFdId()));
			} else {
				// 展开岗位
				List<String> readerIds = sysOrgCoreService.expandToPersonIds(authReaders);
				if (!readerIds.contains(UserUtil.getUser().getFdId())) {
					elementIds.removeAll(map.get(external.getFdId()));
				}
			}
		}
		
		if (CollectionUtils.isEmpty(elementIds)) {
			ctx.setHqlFragment(new HQLFragment("1=2"));
			return FilterContext.RETURN_VALUE;
		}
		List<String> hierarchyIds = sysOrgCoreService.getHierarchyIdsByIds(elementIds);
		String whereBlock = "";
		for (String id : hierarchyIds) {
			whereBlock += "(" + ctx.getModelTable() + ".fdHierarchyId like '" + id + "%') or ";
		}
		if (whereBlock.length() > 0) {
			whereBlock = whereBlock.substring(0, whereBlock.length() - 4);
		}
		
		hqlFragment.setWhereBlock(whereBlock.toString());
		
		ctx.setHqlFragment(hqlFragment);
		
		return FilterContext.RETURN_VALUE;
	}
}
