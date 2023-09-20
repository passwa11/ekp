package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.intercept.RoleValidator;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.eco.AuthOrgRange;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.eco.SysOrgShowRange;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.RestResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "/data/sys-organization/organizationTree", method = RequestMethod.POST)
public class OrganizationTree implements IXMLDataBean, SysOrgConstant {
    private ISysOrgElementService sysOrgElementService;

    private ISysOrgPersonService sysOrgPersonService;

    private ISysOrgCoreService sysOrgCoreService;

    private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

    public ISysOrgPersonService getSysOrgPersonService() {
        return sysOrgPersonService;
    }

    public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
        this.sysOrgPersonService = sysOrgPersonService;
    }

    public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
        this.sysOrgElementService = sysOrgElementService;
    }

    private RoleValidator roleValidator;

    private IOrgRangeService orgRangeService;

    public void setOrgRangeService(IOrgRangeService orgRangeService) {
        this.orgRangeService = orgRangeService;
    }

    @ResponseBody
    @RequestMapping("getDataList")
    public RestResponse<?> getDataList(@RequestBody Map<String, Object> vo, HttpServletRequest request) throws Exception {
        HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
        return RestResponse.ok(getDataList(new RequestContext(wrapper)));
    }

    /**
     * 查询所有数据
     *
     * @param xmlContext
     * @return
     * @throws Exception
     */
    public List getDataListAll(RequestContext xmlContext) throws Exception {
        // 获取查询条件
        OrgTreeQuery treeQuery = buildOrgTreeQuery(xmlContext);
        HQLInfo hqlInfo = treeQuery.hqlInfo;
        // 查询数据
        hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
        List findList = sysOrgElementService.findList(hqlInfo);
        // 格式化结果集
        return result(xmlContext, findList, treeQuery, false);
    }

    /**
     * 根据组织管理员查询数据
     *
     * @param xmlContext
     * @return
     * @throws Exception
     */
    public List getDataListByAdmin(RequestContext xmlContext) throws Exception {
        String fdIsExternal = xmlContext.getParameter("fdIsExternal");
        // 校验管理员
        xmlContext.setParameter("checkAdmin", "true");
        OrgTreeQuery treeQuery = buildOrgTreeQuery(xmlContext);
        HQLInfo hqlInfo = treeQuery.hqlInfo;
        // 查看范围、隐藏过滤
        if ("true".equals(fdIsExternal)) {
            hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, "EXTERNAL_READER");
        } else {
            hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, "DIALOG_READER");
        }
        List<SysOrgElement> findList = sysOrgElementService.findList(hqlInfo);
        // 管理员过滤
        findList = orgRangeService.authFilterAdmin(findList);
        return result(xmlContext, findList, treeQuery, false);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List getDataList(RequestContext xmlContext) throws Exception {
        // 判断是否有扩充的配置，注意扩充时必须将该部分代码删除
        IXMLDataBean extension = OrgDialogUtil.getExtension("orgTree");
        if (extension != null && extension != this) {
            return extension.getDataList(xmlContext);
        }
        // 是否查询生态组织
        String fdIsExternal = xmlContext.getParameter("fdIsExternal");
        // 以下管理员，可以看到所有数据
        KMSSUser user = UserUtil.getKMSSUser();
        // 1. 超级管理员、系统管理员、安全管理员
        if (user != null && user.isAdmin()) {
            return getDataListAll(xmlContext);
        }
        if (UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER") && UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_READER")) {
            return getDataListAll(xmlContext);
        }
        // 判断是否后台管理页面
        String sys_page = xmlContext.getParameter("sys_page");
        String eco_type = xmlContext.getParameter("eco_type");
        if ("true".equals(sys_page)) {
            xmlContext.getParameterMap().remove("fdIsExternal");
            // 2. 内部：使用所有组织（仅内部组织）
            if (UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER")) {
                if (StringUtil.isNull(fdIsExternal) || !"true".equals(fdIsExternal)) {
                    xmlContext.setParameter("fdIsExternal", "false");
                    return getDataListAll(xmlContext);
                }
            }
            // 3. 生态：使用所有组织（仅生态组织）
            if (UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_READER")) {
                if (StringUtil.isNull(fdIsExternal) || "true".equals(fdIsExternal)) {
                    xmlContext.setParameter("fdIsExternal", "true");
                    return getDataListAll(xmlContext);
                }
            }
            // 后台管理，如果是后台管理员，可以查看所有数据
            if ("inner".equals(eco_type) && UserUtil.checkRole("ROLE_SYSORG_ORG_ADMIN")) {
                // 内部：机构管理员，可以在后台页面维护整个内部组织架构
                xmlContext.setParameter("fdIsExternal", "false");
                return getDataListAll(xmlContext);
            }
            if ("outer".equals(eco_type) && (UserUtil.checkRole("ROLE_SYSORG_ECO_ADMIN") || UserUtil.checkRole("ROLE_SYSORG_ECO_ORG_ADMIN"))) {
                // 生态：生态管理员/组织类型管理员，可以在后台页面维护整个生态组织架构
                xmlContext.setParameter("fdIsExternal", "true");
                return getDataListAll(xmlContext);
            }
            if ("outer".equals(eco_type) && UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_ADMIN")) {
                // 生态：生态管理员/组织类型管理员，可以在后台页面维护整个生态组织架构
                xmlContext.setParameter("fdIsExternal", "true");
                return getDataListAll(xmlContext);
            }
            if ("true".equals(fdIsExternal)) {
                xmlContext.setParameter("fdIsExternal", "true");
                // 后台查询生态组织，如果不是管理员，那么需要校验是否组织管理员
                AuthOrgRange range = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
                if (range != null && CollectionUtils.isNotEmpty(range.getAdminRanges())) {
                    return getDataListByAdmin(xmlContext);
                }
            }
            xmlContext.setParameter("fdIsExternal", fdIsExternal);
        }

        // 除了上面的角色权限外，剩下的就是普通用户了，需要对前后端进行权限过滤
        OrgTreeQuery treeQuery = buildOrgTreeQuery(xmlContext);
        HQLInfo hqlInfo = treeQuery.hqlInfo;
        // 查看范围、隐藏过滤
        hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, "DIALOG_READER");
        List<SysOrgElement> findList = sysOrgElementService.findList(hqlInfo);
        // 职级过滤
        findList = sysOrganizationStaffingLevelService.getStaffingLevelFilterResult(findList);
        return result(xmlContext, findList, treeQuery, true);
    }

    /**
     * 构建查询语句
     *
     * @param xmlContext
     * @return
     */
    private OrgTreeQuery buildOrgTreeQuery(RequestContext xmlContext) {
        HQLInfo hqlInfo = new HQLInfo();
        boolean isBaseNode = false;
        boolean isNullNode = false;
        StringBuffer whereBlock = new StringBuffer();

        String deptLimit = xmlContext.getParameter("deptLimit");
        String parentId = xmlContext.getParameter("parent");
        String fdIsExternal = xmlContext.getParameter("fdIsExternal");
        String orgTypeStr = xmlContext.getParameter("orgType");
        int orgType = StringUtil.getIntFromString(orgTypeStr, ORG_TYPE_ORGORDEPT);
        OrgTreeQuery treeQuery = OrgTreeQuery.getInstance(hqlInfo);
        if (StringUtil.isNotNull(fdIsExternal)) {
            hqlInfo.setExternal("true".equals(fdIsExternal));
        }
        AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
        if (StringUtil.isNull(parentId)) {
            String sys_page = xmlContext.getParameter("sys_page");
            String eco_type = xmlContext.getParameter("eco_type");
            if (StringUtil.isNotNull(deptLimit) && !"undefined".equals(deptLimit)) {
                Set<String> elementIds = OrgDialogUtil.getRootOrgIds(deptLimit);
                if (elementIds == null || elementIds.isEmpty()) {
                    // 有限制的查询
                    hqlInfo.setWhereBlock("1 = 2");
                    return treeQuery;
                } else {
                    if ("true".equals(sys_page) && "outer".equals(eco_type)) {
                        try {
                            // 生态组织后台配置
                            List<String> hierarchyIds = sysOrgCoreService.getHierarchyIdsByIds(new ArrayList<>(elementIds));
                            StringBuffer hierarchyBlock = new StringBuffer();
                            if (CollectionUtils.isNotEmpty(hierarchyIds)) {
                                for (String hierarchyId : hierarchyIds) {
                                    if (hierarchyBlock.length() > 0) {
                                        hierarchyBlock.append(" or ");
                                    }
                                    hierarchyBlock.append("sysOrgElement.fdHierarchyId like '").append(hierarchyId).append("%'");
                                }
                            }
                            if (hierarchyBlock.length() > 0) {
                                whereBlock.append("(").append(hierarchyBlock).append(")");
                            }
                        } catch (Exception e) {
                        }
                    } else {
                        // 检查这里限制的组织是否有查看权限，如果可查看的是子组织，这里限制的是父组织，需要把查询的ID更换为子组织
                        if (orgRange != null && CollectionUtils.isNotEmpty(orgRange.getAuthRanges())) {
                            Iterator<String> iterator = elementIds.iterator();
                            Set<String> newIds = new HashSet<>();
                            while (iterator.hasNext()) {
                                String id = iterator.next();
                                for (SysOrgShowRange range : orgRange.getAuthRanges()) {
                                    if (range.getFdHierarchyId().contains(id)) {
                                        iterator.remove();
                                        newIds.add(range.getFdId());
                                        break;
                                    }
                                }
                            }
                            elementIds.addAll(newIds);
                        }
                        if (CollectionUtils.isEmpty(elementIds)) {
                            // 有限制的查询
                            hqlInfo.setWhereBlock("1 = 2");
                            return treeQuery;
                        } else {
                            whereBlock.append("sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(elementIds)).append(")");
                        }
                    }
                }
            } else if ("true".equals(sys_page)) {
                // 后台配置入口
                if ("outer".equals(eco_type)) {
                    // 生态组织
                    if (UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_READER") || UserUtil.checkRole("ROLE_SYSORG_ECO_ADMIN") || UserUtil.checkRole("ROLE_SYSORG_ECO_ORG_ADMIN") || UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_ADMIN")) {
                        whereBlock.append("sysOrgElement.hbmParent = null");
                    } else {
                        if ("true".equals(xmlContext.getParameter("checkAdmin"))) {
                            if (orgRange != null) {
                                Set<String> adminIds = new HashSet<>();
                                if (CollectionUtils.isNotEmpty(orgRange.getAuthOuterRanges())) {
                                    for (SysOrgShowRange adminRange : orgRange.getAuthOuterRanges()) {
                                        adminIds.add(adminRange.getFdId());
                                    }
                                }
                                if (CollectionUtils.isNotEmpty(orgRange.getAdminRanges())) {
                                    for (SysOrgShowRange adminRange : orgRange.getAdminRanges()) {
                                        if (adminRange.getAdminType() == 3) {
                                            boolean isIn = false;
                                            for (SysOrgShowRange authOuterRange : orgRange.getAuthOuterRanges()) {
                                                if (adminRange.getFdHierarchyId().contains(authOuterRange.getFdId())) {
                                                    isIn = true;
                                                    break;
                                                }
                                            }
                                            if (!isIn) {
                                                adminIds.add(adminRange.getFdId());
                                            }
                                        } else {
                                            adminIds.add(adminRange.getFdId());
                                        }
                                    }
                                }
                                whereBlock.append("sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(adminIds)).append(")");
                            }
                        } else {
                            // 对于生态组织，如果没有管理员角色，啥也不显示
                            whereBlock.append(" 1 = 2");
                        }
                    }
                } else {
                    // 内部组织，如果有管理员角色，可以查看所有，否则按需查看
                    if (UserUtil.checkRole("ROLE_SYSORG_ORG_ADMIN") || UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER")) {
                        whereBlock.append("sysOrgElement.hbmParent = null");
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
                }
            } else {
                // 内部组织，如果有管理员角色，可以查看所有，否则按需查看
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
                // 查看所有生态组织时，特殊处理
                if (UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_READER")) {
                    whereBlock.insert(0, "(").append(") or (sysOrgElement.hbmParent = null and sysOrgElement.fdIsExternal = true)");
                }
                isBaseNode = true;
            }
            if (StringUtil.isNotNull(fdIsExternal)) {
                if (whereBlock.length() > 0) {
                    whereBlock.append(" and ");
                }
                whereBlock.append("sysOrgElement.fdIsExternal = :fdIsExternal");
                hqlInfo.setParameter("fdIsExternal", BooleanUtils.toBoolean(fdIsExternal));
            }
        } else if (SysOrgEcoUtil.IS_ENABLED_ECO && "eco_org".equals(parentId)) {
            if (UserUtil.getKMSSUser().isAdmin() || UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_READER")) {
                whereBlock.append("sysOrgElement.hbmParent = null");
            } else {
                if (StringUtil.isNull(parentId) && orgRange != null && orgRange.isShowMyDept()) {
                    whereBlock.append("(");
                }
                // 如果有查看范围限制，就取查看范围的根组织
                if (orgRange != null && CollectionUtils.isNotEmpty(orgRange.getRootDeptIds())) {
                    whereBlock.append("sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(orgRange.getRootDeptIds())).append(")");
                } else {
                    whereBlock.append("sysOrgElement.hbmParent = null");
                }
                if (StringUtil.isNull(parentId) && orgRange != null && orgRange.isShowMyDept()) {
                    whereBlock.append(" or sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(orgRange.getMyDeptIds())).append("))");
                }
            }
            whereBlock.append(" and sysOrgElement.fdIsExternal = :fdIsExternal");
            hqlInfo.setParameter("fdIsExternal", Boolean.TRUE);
        } else if ("null".equals(parentId)) {
            if (SysOrgEcoUtil.IS_ENABLED_ECO && StringUtil.isNotNull(fdIsExternal)) {
                whereBlock.append("sysOrgElement.hbmParent=null and sysOrgElement.fdIsExternal = :fdIsExternal");
                hqlInfo.setParameter("fdIsExternal", BooleanUtils.toBoolean(fdIsExternal));
            } else {
                // 如果有查看范围限制，获取根组织
                if (orgRange != null && (CollectionUtils.isNotEmpty(orgRange.getRootDeptIds()) || CollectionUtils.isNotEmpty(orgRange.getRootPersonIds()))) {
                    Set<String> rootIds = new HashSet<>();
                    rootIds.addAll(orgRange.getRootDeptIds());
                    rootIds.addAll(orgRange.getRootPersonIds());
                    whereBlock.append("sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(rootIds)).append(") ");
                } else {
                    // 未指定部门/机构的节点
                    whereBlock.append("sysOrgElement.hbmParent=null ");
                }
                if (StringUtil.isNotNull(fdIsExternal)) {
                    whereBlock.append(" and sysOrgElement.fdIsExternal = :fdIsExternal");
                    hqlInfo.setParameter("fdIsExternal", BooleanUtils.toBoolean(fdIsExternal));
                }
            }
            isNullNode = true;
        } else {
            // 其他普通节点
            whereBlock.append("sysOrgElement.hbmParent.fdId = :hbmParentId");
            hqlInfo.setParameter("hbmParentId", parentId);
        }

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

    /**
     * 格式化结果集
     *
     * @param xmlContext
     * @param list
     * @param treeQuery
     * @param checkAuth
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> result(RequestContext xmlContext, List<SysOrgElement> list, OrgTreeQuery treeQuery, boolean checkAuth) throws Exception {
        // 页面参数
        String sysPage = xmlContext.getParameter("sys_page");
        String parentId = xmlContext.getParameter("parent");
        String fdIsExternal = xmlContext.getParameter("fdIsExternal");
        String useDept = xmlContext.getParameter("useDept");
        // 计算参数
        int orgType = treeQuery.orgType;
        boolean isNullNode = treeQuery.isNullNode;
        boolean isBaseNode = treeQuery.isBaseNode;

        ArrayList<Map<String, Object>> rtnMapList = new ArrayList<>();
        boolean hasEco = false;
        ArrayList rtnEcoList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            SysOrgElement elem = list.get(i);
            if (OrgDialogUtil.getExceptIdList().contains(elem.getFdId())) {
                continue;
            }
            int curType = elem.getFdOrgType().intValue();
            HashMap map = new HashMap();
            map.put("value", elem.getFdId());
            map.put("nodeType", elem.getFdOrgType());
            map.put("isAvailable", elem.getFdIsAvailable().toString());
            map.put("isExternal", elem.getFdIsExternal().toString());
            String name = OrgDialogUtil.getDeptLevelNames(elem);
            map.put("text", name);
            map.put("title", name);
            if ((curType & orgType) == 0) {
                map.put("href", "");
            }
            // 某些情况下，机构不能出现选择框（生态场景中，人员和岗位不能直接挂在机构下）
            if ("true".equals(useDept) && elem.getFdOrgType() == ORG_TYPE_ORG) {
                // 如果是组织类型，不允许出现选择框
                map.put("isShowCheckBox", "0");
                map.put("href", "");
            }
            if (elem.getFdOrgType().equals(ORG_TYPE_PERSON)) {
                OrgDialogUtil.setPersonAttrs(elem, xmlContext.getContextPath(), map);
            }
            // 外部组织另外用节点展示
            if (SysOrgEcoUtil.IS_ENABLED_ECO && isBaseNode && BooleanUtils.isTrue(elem.getFdIsExternal())) {
                hasEco = true;
                rtnEcoList.add(map);
            } else {
                rtnMapList.add(map);
            }
        }
        // 生态组织
        if (SysOrgEcoUtil.IS_ENABLED_ECO) {
            if (StringUtil.isNull(parentId)) {
                // 追加生态组织
                if ((StringUtils.isBlank(sysPage) || "false".equals(sysPage)) && (hasEco
                        || (isNullNode && "true".equals(fdIsExternal)))) {
                    HashMap map = new HashMap();
                    map.put("value", "eco_org");
                    map.put("text", ResourceUtil.getString("sys-organization:sysOrgEco.name"));
                    map.put("href", "");
                    map.put("isExternal", "true");
                    rtnMapList.add(map);
                } else {
                    rtnMapList.addAll(rtnEcoList);
                }
            } else {
                rtnMapList.addAll(rtnEcoList);
            }
        }
        // 未指定机构/部门
        if (isBaseNode && !"eco_org".equals(parentId) && (orgType & ORG_TYPE_POSTORPERSON) > 0) {
            HashMap map = new HashMap();
            map.put("value", "null");
            map.put("text", ResourceUtil.getString("sys-organization:sysOrg.address.parentNotAssign", xmlContext.getLocale()));
            map.put("href", "");
            map.put("isExternal", "");
            map.put("isExpanded", true);
            rtnMapList.add(map);
        }
        return rtnMapList;
    }

    public void setRoleValidator(RoleValidator roleValidator) {
        this.roleValidator = roleValidator;
    }

    public RoleValidator getRoleValidator() {
        return roleValidator;
    }

    public void setSysOrganizationStaffingLevelService(
            ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
        this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
    }

    public ISysOrganizationStaffingLevelService getSysOrganizationStaffingLevelService() {
        return sysOrganizationStaffingLevelService;
    }

    public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
        this.sysOrgCoreService = sysOrgCoreService;
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

}
