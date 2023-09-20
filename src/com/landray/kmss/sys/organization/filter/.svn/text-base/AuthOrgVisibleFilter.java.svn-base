package com.landray.kmss.sys.organization.filter;

import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.authentication.filter.FilterContext;
import com.landray.kmss.sys.authentication.filter.HQLFragment;
import com.landray.kmss.sys.authentication.filter.IAuthenticationFilter;
import com.landray.kmss.sys.organization.eco.AuthOrgRange;
import com.landray.kmss.sys.organization.eco.SysOrgBaseRange;
import com.landray.kmss.sys.organization.eco.SysOrgMyDeptRange;
import com.landray.kmss.sys.organization.eco.SysOrgShowRange;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * 组织可见性过滤（组织查看范围）
 * 1、普通用户
 * 1.1、内部用户：
 * 1.1.1、有查看范围：按查看范围 + 隐藏过滤
 * 1.1.2、没有查看范围：仅查看内部组织 + 隐藏过滤
 * 1.2、生态用户：
 * 1.2.1、有查看范围：按查看范围 + 隐藏过滤
 * <p>
 * 2、使用所有组织
 * 2.1、内部用户：查看所有内部组织 + （查看生态范围 + 隐藏过滤）
 * 2.2、生态用户：查看所有生态组织 + （查看内部范围 + 隐藏过滤）
 * <p>
 * 按父ID查看子组织：
 * 3、判断父组织是否在查看范围
 * 3.1、不在查看范围（或隐藏）：返回：1=2
 * 3.2、在查看范围：只增该组织下的隐藏过滤（非该组织下的隐藏不需要过滤-没意义）
 */
public class AuthOrgVisibleFilter implements IAuthenticationFilter, SysOrgConstant {
    private static final Logger logger = LoggerFactory.getLogger(AuthOrgVisibleFilter.class);

    private ISysOrgElementService sysOrgElementService;

    public ISysOrgElementService getSysOrgElementService() {
        if (sysOrgElementService == null) {
            sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
        }
        return sysOrgElementService;
    }

    @Override
    public int getAuthHQLInfo(FilterContext ctx) throws Exception {
        // 管理员，有所有组织使用权限不需要过滤
        if (UserUtil.getKMSSUser().isAdmin() || (UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER") && UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_READER"))) {
            return FilterContext.RETURN_IGNOREME;
        }
        // 如果没有查看范围，也没有隐藏组织，不需要过滤
        AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
        if (orgRange == null) {
            return FilterContext.RETURN_IGNOREME;
        }
        Set<SysOrgShowRange> authRanges = orgRange.getAuthRanges();
        Set<SysOrgBaseRange> hideRanges = orgRange.getHideRanges();
        if (CollectionUtils.isEmpty(authRanges) && CollectionUtils.isEmpty(hideRanges)) {
            return FilterContext.RETURN_IGNOREME;
        }

        // 如果有内部/生态使用角色，单独处理
        if (UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER") || UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_READER")) {
            return filterRole(ctx, orgRange);
        }

        String modelTable = ctx.getModelTable();
        // 仅查看自己
        if (orgRange.isSelf()) {
            HQLFragment hqlFragment = new HQLFragment();
            hqlFragment.setWhereBlock(modelTable + ".fdId = :fdId");
            hqlFragment.setParameter(new HQLParameter("fdId", UserUtil.getKMSSUser().getUserId()));
            ctx.setHqlFragment(hqlFragment);
            return FilterContext.RETURN_VALUE;
        }

        // 按上级组织过滤
        HttpServletRequest request = Plugin.currentRequest();
        String parentId = null;
        if (request != null) {
            parentId = request.getParameter("parent");
            if (StringUtil.isNull(parentId)) {
                parentId = request.getParameter("parentId");
            }
        }
        if (StringUtil.isNotNull(parentId) && !"null".equals(parentId) && !"eco_org".equals(parentId)) {
            return filterParent(ctx, orgRange, parentId);
        }

        // 这里过滤的逻辑有以下2个
        // 1. 如果有查看范围，查询时只能看到在范围内的数据
        // 2. 如果有隐藏设置，查询时需要过滤隐藏的组织
        StringBuffer authBlock = new StringBuffer();
        Set<String> authHids = new HashSet<>();
        if (CollectionUtils.isNotEmpty(authRanges)) {
            for (SysOrgShowRange range : authRanges) {
                if (authBlock.length() > 0) {
                    authBlock.append(" or ");
                }
                authHids.add(range.getFdHierarchyId());
                authBlock.append(modelTable).append(".fdHierarchyId like '").append(range.getFdHierarchyId()).append("%'");
            }
        } else if (UserUtil.getKMSSUser().getFdIsExternal()) {
            // 如果是生态人员，没有查看范围限制时，只能看生态组织。正常的生态人员，一定会有查看范围限制，除非是生态角色
            authBlock.append(modelTable).append(".fdIsExternal = true");
        }
        if (authBlock.length() > 0) {
            authBlock.insert(0, "(").append(")");
        }
        // 隐藏组织
        StringBuffer hideBlock = new StringBuffer();
        Set<SysOrgMyDeptRange> myDepts = orgRange.getMyDepts();
        if (CollectionUtils.isNotEmpty(hideRanges)) {
            for (SysOrgBaseRange range : hideRanges) {
                // 如果隐藏就是可查看组织，不需要处理
                if (authHids.contains(range.getFdHierarchyId())) {
                    continue;
                }
                if (hideBlock.length() > 0) {
                    hideBlock.append(" and ");
                }
                StringBuffer myDeptBlock = new StringBuffer();
                if (CollectionUtils.isNotEmpty(myDepts)) {
                    for (SysOrgMyDeptRange deptRange : myDepts) {
                        if (deptRange.getFdHierarchyId().startsWith(range.getFdHierarchyId())) {
                            myDeptBlock.append(" or ").append(modelTable).append(".fdHierarchyId like '").append(deptRange.getFdHierarchyId()).append("%'");
                        }
                    }
                }
                if (myDeptBlock.length() > 0) {
                    hideBlock.append("(");
                }
                hideBlock.append(modelTable).append(".fdHierarchyId not like '").append(range.getFdHierarchyId()).append("%'");
                if (myDeptBlock.length() > 0) {
                    hideBlock.append(myDeptBlock).append(")");
                }
            }
        }
        if (hideBlock.length() > 0) {
            hideBlock.insert(0, "(").append(")");
        }

        // 有隐藏，但是不能隐藏我的组织(内部组织)
        StringBuffer deptBlock = new StringBuffer();
        if (!UserUtil.getKMSSUser().getFdIsExternal() && orgRange.isShowMyDept()) {
            if (CollectionUtils.isNotEmpty(myDepts)) {
                // 过滤隐藏子部门
                StringBuffer subHide = new StringBuffer();
                if (CollectionUtils.isNotEmpty(orgRange.getSubHideHids())) {
                    for (String sub : orgRange.getSubHideHids()) {
                        subHide.append(" and ").append(modelTable).append(".fdHierarchyId not like '").append(sub).append("%'");
                    }
                }
                for (SysOrgMyDeptRange range : myDepts) {
                    if (deptBlock.length() > 0) {
                        deptBlock.append(" or ");
                    }
                    if (subHide.length() > 0) {
                        deptBlock.append("(");
                    }
                    deptBlock.append(modelTable).append(".fdHierarchyId like '").append(range.getFdHierarchyId()).append("%'");
                    if (subHide.length() > 0) {
                        deptBlock.append(subHide).append(")");
                    }
                }
            }
            if (deptBlock.length() > 0) {
                deptBlock.insert(0, "(").append(")");
            }
        }

        // 拼接条件：可查看范围 and 隐藏组织 or 我的组织
        String whereBlock = StringUtil.linkString(authBlock.toString(), " and ", hideBlock.toString());
        if (!orgRange.isSelf()) {
            whereBlock = StringUtil.linkString(whereBlock, " or ", deptBlock.toString());
        }
        // 没有查询条件，不需要过滤
        if (whereBlock.length() < 1) {
            return FilterContext.RETURN_IGNOREME;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("组织可见性过滤 - AuthOrgVisibleFilter：{}", whereBlock);
        }
        HQLFragment hqlFragment = new HQLFragment();
        hqlFragment.setWhereBlock(whereBlock);
        ctx.setHqlFragment(hqlFragment);
        return FilterContext.RETURN_VALUE;
    }

    /**
     * 按相关角色过滤
     *
     * @param ctx
     * @param orgRange
     * @return
     * @throws Exception
     */
    private int filterRole(FilterContext ctx, AuthOrgRange orgRange) {
        String modelTable = ctx.getModelTable();
        Set<SysOrgShowRange> authRanges = orgRange.getAuthRanges();
        Set<SysOrgBaseRange> hideRanges = orgRange.getHideRanges();
        Set<SysOrgMyDeptRange> myDepts = orgRange.getMyDepts();
        StringBuffer authBlock = new StringBuffer();
        StringBuffer hideBlock = new StringBuffer();
        if (UserUtil.getKMSSUser().getFdIsExternal()) {
            // 生态用户
            if (UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER")) {
                // 可查看所有内部组织，需要对生态组织进行过滤
                if (CollectionUtils.isNotEmpty(authRanges)) {
                    for (SysOrgShowRange range : authRanges) {
                        if (range.isExternal()) {
                            if (authBlock.length() > 0) {
                                authBlock.append(" or ");
                            }
                            authBlock.append(modelTable).append(".fdHierarchyId like '").append(range.getFdHierarchyId()).append("%'");
                        }
                    }
                }
                // 过滤隐藏的生态组织
                if (CollectionUtils.isNotEmpty(hideRanges)) {
                    for (SysOrgBaseRange range : hideRanges) {
                        if (range.isExternal()) {
                            if (hideBlock.length() > 0) {
                                hideBlock.append(" and ");
                            }
                            StringBuffer myDeptBlock = new StringBuffer();
                            if (CollectionUtils.isNotEmpty(myDepts)) {
                                for (SysOrgMyDeptRange deptRange : myDepts) {
                                    if (deptRange.getFdHierarchyId().startsWith(range.getFdHierarchyId())) {
                                        myDeptBlock.append(" or ").append(modelTable).append(".fdHierarchyId like '").append(deptRange.getFdHierarchyId()).append("%'");
                                    }
                                }
                            }
                            if (myDeptBlock.length() > 0) {
                                hideBlock.append("(");
                            }
                            hideBlock.append(modelTable).append(".fdHierarchyId not like '").append(range.getFdHierarchyId()).append("%'");
                            if (myDeptBlock.length() > 0) {
                                hideBlock.append(myDeptBlock).append(")");
                            }
                        }
                    }
                }
            } else if (UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_READER")) {
                // 可查看所有生态组织，需要对内部组织进行过滤
                if (CollectionUtils.isNotEmpty(authRanges)) {
                    for (SysOrgShowRange range : authRanges) {
                        if (!range.isExternal()) {
                            if (authBlock.length() > 0) {
                                authBlock.append(" or ");
                            }
                            authBlock.append(modelTable).append(".fdHierarchyId like '").append(range.getFdHierarchyId()).append("%'");
                        }
                    }
                }
                if (authBlock.length() < 1) {
                    // 对于生态人员来说，如果没有内部查看限制，实际上是不能查看内部组织，所以这里强制只能查看生态组织
                    authBlock.append(modelTable).append(".fdIsExternal = true");
                } else {
                    authBlock.append(" or ").append(modelTable).append(".fdIsExternal = true");
                    // 过滤隐藏的内部组织
                    if (CollectionUtils.isNotEmpty(hideRanges) && CollectionUtils.isNotEmpty(authRanges)) {
                        for (SysOrgBaseRange range : hideRanges) {
                            if (!range.isExternal()) {
                                // 避免加入太多无用的参数，这里只过滤在内部查看范围里的组织
                                boolean inRange = false;
                                for (SysOrgShowRange showRange : authRanges) {
                                    if (range.getFdHierarchyId().startsWith(showRange.getFdHierarchyId())) {
                                        inRange = true;
                                        break;
                                    }
                                }
                                if (!inRange) {
                                    continue;
                                }
                                if (hideBlock.length() > 0) {
                                    hideBlock.append(" and ");
                                }
                                hideBlock.append(modelTable).append(".fdHierarchyId not like '").append(range.getFdHierarchyId()).append("%'");
                            }
                        }
                    }
                }
            }
        } else {
            // 内部用户
            if (UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER")) {
                // 可查看所有内部组织，需要对生态组织进行过滤
                if (CollectionUtils.isNotEmpty(authRanges)) {
                    for (SysOrgShowRange range : orgRange.getAuthRanges()) {
                        if (range.isExternal()) {
                            if (authBlock.length() > 0) {
                                authBlock.append(" or ");
                            }
                            authBlock.append(modelTable).append(".fdHierarchyId like '").append(range.getFdHierarchyId()).append("%'");
                        }
                    }
                } else {
                    // 如果没有查看限制，则无法查看生态组织
                    if (CollectionUtils.isNotEmpty(orgRange.getAuthOuterRanges())) {
                        authBlock.append("(").append(modelTable).append(".fdIsExternal = false ");
                        for (SysOrgShowRange range : orgRange.getAuthOuterRanges()) {
                            authBlock.append(" or ").append(modelTable).append(".fdHierarchyId like '").append(range.getFdHierarchyId()).append("%'");
                        }
                        authBlock.append(")");
                    } else {
                        authBlock.append(modelTable).append(".fdIsExternal = false ");
                    }
                }
                // 过滤隐藏的生态组织
                if (CollectionUtils.isNotEmpty(orgRange.getHideRanges())) {
                    for (SysOrgBaseRange range : orgRange.getHideRanges()) {
                        if (range.isExternal()) {
                            if (hideBlock.length() > 0) {
                                hideBlock.append(" and ");
                            }
                            StringBuffer myDeptBlock = new StringBuffer();
                            if (CollectionUtils.isNotEmpty(myDepts)) {
                                for (SysOrgMyDeptRange deptRange : myDepts) {
                                    if (deptRange.getFdHierarchyId().startsWith(range.getFdHierarchyId())) {
                                        myDeptBlock.append(" or ").append(modelTable).append(".fdHierarchyId like '").append(deptRange.getFdHierarchyId()).append("%'");
                                    }
                                }
                            }
                            if (myDeptBlock.length() > 0) {
                                hideBlock.append("(");
                            }
                            hideBlock.append(modelTable).append(".fdHierarchyId not like '").append(range.getFdHierarchyId()).append("%'");
                            if (myDeptBlock.length() > 0) {
                                hideBlock.append(myDeptBlock).append(")");
                            }
                        }
                    }
                }
            } else if (UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_READER")) {
                // 可查看所有生态组织，需要对内部组织进行过滤
                authBlock.append(modelTable).append(".fdIsExternal = true ");
                if (CollectionUtils.isNotEmpty(authRanges)) {
                    for (SysOrgShowRange range : orgRange.getAuthRanges()) {
                        if (!range.isExternal()) {
                            if (authBlock.length() > 0) {
                                authBlock.append(" or ");
                            }
                            authBlock.append(modelTable).append(".fdHierarchyId like '").append(range.getFdHierarchyId()).append("%'");
                        }
                    }
                } else {
                    authBlock.append(" or ").append(modelTable).append(".fdIsExternal = false ");
                }
                // 过滤隐藏的内部组织
                if (CollectionUtils.isNotEmpty(orgRange.getHideRanges())) {
                    for (SysOrgBaseRange range : orgRange.getHideRanges()) {
                        if (!range.isExternal()) {
                            if (hideBlock.length() > 0) {
                                hideBlock.append(" and ");
                            }
                            StringBuffer myDeptBlock = new StringBuffer();
                            if (CollectionUtils.isNotEmpty(myDepts)) {
                                for (SysOrgMyDeptRange deptRange : myDepts) {
                                    if (deptRange.getFdHierarchyId().startsWith(range.getFdHierarchyId())) {
                                        myDeptBlock.append(" or ").append(modelTable).append(".fdHierarchyId like '").append(deptRange.getFdHierarchyId()).append("%'");
                                    }
                                }
                            }
                            if (myDeptBlock.length() > 0) {
                                hideBlock.append("(");
                            }
                            hideBlock.append(modelTable).append(".fdHierarchyId not like '").append(range.getFdHierarchyId()).append("%'");
                            if (myDeptBlock.length() > 0) {
                                hideBlock.append(myDeptBlock).append(")");
                            }
                        }
                    }
                }
            }
        }
        if (authBlock.length() > 0) {
            authBlock.insert(0, "(").append(")");
        }
        if (hideBlock.length() > 0) {
            hideBlock.insert(0, "(").append(")");
        }
        // 拼接条件：可查看范围 and 隐藏组织 or 我的组织
        String whereBlock = StringUtil.linkString(authBlock.toString(), " and ", hideBlock.toString());
        // 没有查询条件，不需要过滤
        if (whereBlock.length() < 1) {
            return FilterContext.RETURN_IGNOREME;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("组织可见性过滤 - AuthOrgVisibleFilter：{}", whereBlock);
        }
        HQLFragment hqlFragment = new HQLFragment();
        hqlFragment.setWhereBlock(whereBlock);
        ctx.setHqlFragment(hqlFragment);
        return FilterContext.RETURN_VALUE;
    }

    /**
     * 按上级组织过滤
     *
     * @param ctx
     * @param orgRange
     * @param parentId
     * @return
     * @throws Exception
     */
    private int filterParent(FilterContext ctx, AuthOrgRange orgRange, String parentId) throws Exception {
        String modelTable = ctx.getModelTable();
        if ("sysOrgGroup".equals(modelTable)) {
            return FilterContext.RETURN_IGNOREME;
        }
        SysOrgElement element = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(parentId);
        String hierarchyId = null;
        // 判断父组织是否在查看范围内
        Set<SysOrgShowRange> ranges = orgRange.getAuthRanges();
        boolean inRange = true;
        if (element != null) {
            hierarchyId = element.getFdHierarchyId();
//            hierarchyId = SysOrgUtil.buildHierarchyIdIncludeOrg(element);
            if (CollectionUtils.isNotEmpty(ranges)) {
                inRange = checkVisible(hierarchyId, ranges);
            }
        }
        Set<SysOrgMyDeptRange> myDepts = orgRange.getMyDepts();
        // 如果查看的父组织不在范围内，无权限查看
        if (StringUtil.isNull(hierarchyId) || !inRange) {
            String myDeptId = null;
            for (SysOrgMyDeptRange deptRange : myDepts) {
                if (deptRange.getFdHierarchyId().contains(parentId)) {
                    myDeptId = deptRange.getFdId();
                    break;
                }
            }
            if (StringUtil.isNotNull(myDeptId)) {
                HQLFragment hqlFragment = new HQLFragment();
                hqlFragment.setWhereBlock(modelTable + ".fdId = :fdId");
                hqlFragment.setParameter(new HQLParameter("fdId", myDeptId));
                ctx.setHqlFragment(hqlFragment);
                return FilterContext.RETURN_VALUE;
            }
            HQLFragment hqlFragment = new HQLFragment();
            hqlFragment.setWhereBlock("1 = 2");
            ctx.setHqlFragment(hqlFragment);
            return FilterContext.RETURN_VALUE;
        }

        // 如果有查看范围，查询的下组织也必须在范围内
        Set<SysOrgShowRange> authRanges = orgRange.getAuthRanges();
        StringBuffer authBlock = new StringBuffer();
        if (element.getFdOrgType() == 1 && CollectionUtils.isNotEmpty(authRanges)) {
            // 主要解决指定查看父机构时，不可查看子机构
            for (SysOrgShowRange range : authRanges) {
                if (range.getFdOrgType() == 1) {
                    if (authBlock.length() > 0) {
                        authBlock.append(" or ");
                    }
                    authBlock.append(modelTable).append(".fdHierarchyId like '").append(range.getFdHierarchyId()).append("%'");
                }
            }
        }
        if (authBlock.length() > 0) {
            authBlock.insert(0, "(").append(")");
        }
        // 增加隐藏组织过滤（为了提高SQL的查询性能，这里增加的隐藏组织仅仅是与该父组织相关的）
        Set<SysOrgBaseRange> hideRanges = orgRange.getHideRanges();
        // 隐藏组织
        StringBuffer hideBlock = new StringBuffer();
        // 有隐藏，但是不能隐藏我的组织(内部组织)
        StringBuffer deptBlock = new StringBuffer();
        if (CollectionUtils.isNotEmpty(hideRanges)) {
            // 过滤隐藏子部门
            StringBuffer subHide = new StringBuffer();
            if (CollectionUtils.isNotEmpty(orgRange.getSubHideHids())) {
                for (String sub : orgRange.getSubHideHids()) {
                    subHide.append(" and ").append(modelTable).append(".fdHierarchyId not like '").append(sub).append("%'");
                }
            }
            for (SysOrgBaseRange range : hideRanges) {
                if (range.getFdHierarchyId().startsWith(element.getFdHierarchyId()) || range.getFdOrgType() == 1) {
                    if (hideBlock.length() > 0) {
                        hideBlock.append(" and ");
                    }
                    hideBlock.append(modelTable).append(".fdHierarchyId not like '").append(range.getFdHierarchyId()).append("%'");
                    // 如果隐藏的组织包含我所属组织，需要放行
                    if (CollectionUtils.isNotEmpty(myDepts)) {
                        for (SysOrgMyDeptRange deptRange : myDepts) {
                            if (deptRange.getFdHierarchyId().startsWith(range.getFdHierarchyId())) {
                                if (deptBlock.length() > 0) {
                                    deptBlock.append(" or ");
                                }
                                if (subHide.length() > 0) {
                                    deptBlock.append("(");
                                }
                                deptBlock.append(modelTable).append(".fdHierarchyId like '").append(deptRange.getFdHierarchyId()).append("%'");
                                if (subHide.length() > 0) {
                                    deptBlock.append(subHide).append(")");
                                }
                            }
                        }
                    }
                }
            }
        }
        if (deptBlock.length() > 0) {
            deptBlock.insert(0, "(").append(")");
        }
        // 拼接条件：可查看范围 and 隐藏组织 or 我的组织
        String whereBlock = StringUtil.linkString(authBlock.toString(), " and ", hideBlock.toString());
        if (deptBlock.length() > 0) {
            whereBlock = StringUtil.linkString(whereBlock, " or ", deptBlock.toString());
        }
        // 没有查询条件，不需要过滤
        if (whereBlock.length() < 1) {
            return FilterContext.RETURN_IGNOREME;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("组织可见性过滤 - AuthOrgVisibleFilter：{}", whereBlock);
        }
        HQLFragment hqlFragment = new HQLFragment();
        hqlFragment.setWhereBlock(whereBlock);
        ctx.setHqlFragment(hqlFragment);
        return FilterContext.RETURN_VALUE;
    }

    /**
     * 判断父组织是否在查看范围内
     *
     * @param hierarchyId
     * @param ranges
     * @return
     * @throws Exception
     */
    private boolean checkVisible(String hierarchyId, Set<SysOrgShowRange> ranges) {
        for (SysOrgShowRange range : ranges) {
            if (hierarchyId.contains(range.getFdId())) {
                return true;
            }
        }
        return false;
    }

}
