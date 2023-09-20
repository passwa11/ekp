package com.landray.kmss.sys.organization.filter;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.authentication.filter.FilterContext;
import com.landray.kmss.sys.authentication.filter.HQLFragment;
import com.landray.kmss.sys.authentication.filter.IAuthenticationFilter;
import com.landray.kmss.sys.organization.eco.AuthOrgRange;
import com.landray.kmss.sys.organization.eco.SysOrgShowRange;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.collections.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthOrgAdminFilter implements IAuthenticationFilter {

    private ISysOrgCoreService sysOrgCoreService;

    public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
        this.sysOrgCoreService = sysOrgCoreService;
    }

    private ISysOrgElementService sysOrgElementService;

    public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
        this.sysOrgElementService = sysOrgElementService;
    }

    @Override
    public int getAuthHQLInfo(FilterContext ctx) throws Exception {
        List<String> elementIds = sysOrgCoreService.findOrgAdminByElem(UserUtil.getUser());
        if (elementIds == null || elementIds.size() == 0) {
            ctx.setHqlFragment(new HQLFragment("1=2"));
            return FilterContext.RETURN_VALUE;
        }
        List<String> hierarchyIds = sysOrgCoreService.getHierarchyIdsByIds(elementIds);
        // 生态组织管理员
        AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
        Set<String> ids = new HashSet<>();
        Set<String> authIds = new HashSet<>();
        if (orgRange != null && CollectionUtils.isNotEmpty(orgRange.getAdminRanges())) {
            for (SysOrgShowRange range : orgRange.getAdminRanges()) {
                if (range.getAdminType() == 1 || range.getAdminType() == 3) {
                    hierarchyIds.add(range.getFdHierarchyId());
                    if (range.getAdminType() == 3) {
                        authIds.add(range.getFdId());
                    }
                } else {
                    ids.add(range.getFdId());
                }
            }
        }
        HttpServletRequest request = Plugin.currentRequest();
        if (CollectionUtils.isNotEmpty(authIds) && request != null) {
            String parentId = request.getParameter("parent");
            if (StringUtil.isNull(parentId)) {
                parentId = request.getParameter("parentId");
            }
            if (StringUtil.isNull(parentId)) {
                List<String> orgIds = getOrgIds(authIds);
                if (CollectionUtils.isNotEmpty(orgIds)) {
                    ids.addAll(orgIds);
                }
            }
        }
        HQLFragment hqlFragment = new HQLFragment();
        StringBuffer whereBlock = new StringBuffer();
        for (String id : hierarchyIds) {
            if (whereBlock.length() > 0) {
                whereBlock.append(" or ");
            }
            whereBlock.append(ctx.getModelTable()).append(".fdHierarchyId like '").append(id).append("%'");
        }
        if (CollectionUtils.isNotEmpty(ids)) {
            if (whereBlock.length() > 0) {
                whereBlock.append(" or ");
            }
            whereBlock.append(ctx.getModelTable()).append(".fdId in (").append(SysOrgUtil.buildInBlock(ids)).append(")");
        }
        hqlFragment.setWhereBlock(whereBlock.toString());
        ctx.setHqlFragment(hqlFragment);
        return FilterContext.RETURN_VALUE;
    }

    private List<String> getOrgIds(Set<String> ids) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock("sysOrgElement.hbmParentOrg.fdId");
        hqlInfo.setWhereBlock("sysOrgElement.fdId in (:ids)");
        hqlInfo.setParameter("ids", ids);
        return sysOrgElementService.findList(hqlInfo);
    }

}
