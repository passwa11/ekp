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
import com.landray.kmss.sys.organization.filter.AuthOrgVisibleFilter;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgConfig;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.service.ISysOrganizationVisibleService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.RestResponse;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "/data/sys-organization/organizationDialogList", method = RequestMethod.POST)
public class OrgDialogList implements IXMLDataBean, SysOrgConstant {

    private ISysOrgElementService sysOrgElementService;

    private ISysOrganizationVisibleService sysOrganizationVisibleService;

    private RoleValidator roleValidator;

    private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

    // 可见性过滤
    private AuthOrgVisibleFilter authOrgVisibleFilter;

    public ISysOrganizationStaffingLevelService getSysOrganizationStaffingLevelService() {
        return sysOrganizationStaffingLevelService;
    }

    public void setSysOrganizationStaffingLevelService(
            ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
        this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
    }

    private IOrgRangeService orgRangeService;

    public void setOrgRangeService(IOrgRangeService orgRangeService) {
        this.orgRangeService = orgRangeService;
    }

    public AuthOrgVisibleFilter getAuthOrgVisibleFilter() {
        if (this.authOrgVisibleFilter == null) {
            this.authOrgVisibleFilter = (AuthOrgVisibleFilter) SpringBeanUtil.getBean("authOrgVisibleFilter");
        }
        return this.authOrgVisibleFilter;
    }

    @ResponseBody
    @RequestMapping("getDataList")
    public RestResponse<?> getDataList(@RequestBody Map<String, Object> vo, HttpServletRequest request) throws Exception {
        HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
        return RestResponse.ok(getDataList(new RequestContext(wrapper)));
    }

    /**
     * 不做组织过滤
     *
     * @param xmlContext
     * @return
     * @throws Exception
     */
    public List getDataListAll(RequestContext xmlContext) throws Exception {
        OrgListQuery listQuery = buildOrgListQuery(xmlContext);
        HQLInfo hqlInfo = listQuery.hqlInfo;
        hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
        List<SysOrgElement> elemList = sysOrgElementService.findPage(hqlInfo).getList();
        // 管理员不需要过滤权限
        return result(xmlContext, elemList, listQuery, false);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List getDataList(RequestContext xmlContext) throws Exception {
        // 判断是否有扩充的配置，注意扩充时必须将该部分代码删除
        IXMLDataBean extension = OrgDialogUtil.getExtension("orgList");
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
            if (UserUtil.checkRole("ROLE_SYSORG_ORG_ADMIN")) {
                // 内部：机构管理员，可以在后台页面维护整个内部组织架构
                xmlContext.setParameter("fdIsExternal", "false");
                return getDataListAll(xmlContext);
            }
            if (UserUtil.checkRole("ROLE_SYSORG_ECO_ADMIN") || UserUtil.checkRole("ROLE_SYSORG_ECO_ORG_ADMIN")) {
                // 生态：生态管理员/组织类型管理员，可以在后台页面维护整个生态组织架构
                xmlContext.setParameter("fdIsExternal", "true");
                return getDataListAll(xmlContext);
            }
            if (UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_ADMIN")) {
                // TODO ... 这里的逻辑还没有优化
                // 生态：组织管理员，可以在后台页面维护所有组织、岗位、人员
                xmlContext.setParameter("fdIsExternal", "true");
                return getDataListAll(xmlContext);
            }
            xmlContext.setParameter("fdIsExternal", fdIsExternal);
        }

        OrgListQuery listQuery = buildOrgListQuery(xmlContext);
        HQLInfo hqlInfo = listQuery.hqlInfo;
        // 查看范围、隐藏过滤
        hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, "DIALOG_READER");
        List<SysOrgElement> elemList = sysOrgElementService.findPage(hqlInfo).getList();
        // 普通用户需要过滤权限
        return result(xmlContext, elemList, listQuery, true);
    }

    /**
     * 构建查询条件
     *
     * @param xmlContext
     * @return
     */
    private OrgListQuery buildOrgListQuery(RequestContext xmlContext) {
        HQLInfo hqlInfo = new HQLInfo();
        OrgListQuery listQuery = OrgListQuery.getInstance(hqlInfo);
        StringBuffer whereBlock = new StringBuffer();

        String deptLimit = xmlContext.getParameter("deptLimit");
        String fdParentId = xmlContext.getParameter("parent");
        String orgTypeStr = xmlContext.getParameter("orgType");
        String fdIsExternal = xmlContext.getParameter("fdIsExternal");
        if (StringUtil.isNotNull(fdIsExternal)) {
            hqlInfo.setExternal("true".equals(fdIsExternal));
        }
        int orgType = StringUtil.getIntFromString(orgTypeStr, ORG_TYPE_DEFAULT);
        AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
        if (StringUtil.isNull(fdParentId)) {
            if (StringUtil.isNotNull(deptLimit) && !"undefined".equals(deptLimit)) {
                Set<String> elementIds = OrgDialogUtil.getRootOrgIds(deptLimit);
                if (elementIds == null || elementIds.isEmpty()) {
                    // 有限制的查询
                    hqlInfo.setWhereBlock("1 = 2");
                    return listQuery;
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
                        return listQuery;
                    } else {
                        whereBlock.append("sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(elementIds)).append(")");
                    }
                }
            } else {
                // 查看所有生态组织时，特殊处理
                if (UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_READER")) {
                    whereBlock.append("sysOrgElement.hbmParent = null");
                } else {
                    if (StringUtil.isNull(fdParentId) && orgRange != null && orgRange.isShowMyDept()) {
                        whereBlock.append("(");
                    }
                    if (orgRange != null && (CollectionUtils.isNotEmpty(orgRange.getRootDeptIds()) || CollectionUtils.isNotEmpty(orgRange.getRootPersonIds()))) {
                        Set<String> rootIds = new HashSet<>();
                        rootIds.addAll(orgRange.getRootDeptIds());
                        rootIds.addAll(orgRange.getRootPersonIds());
                        whereBlock.append("sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(rootIds)).append(")");
                    } else {
                        whereBlock.append("sysOrgElement.hbmParent = null");
                    }
                    if (StringUtil.isNull(fdParentId) && orgRange != null && orgRange.isShowMyDept()) {
                        whereBlock.append(" or sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(orgRange.getMyDeptIds())).append("))");
                    }
                }
            }
        } else if (SysOrgEcoUtil.IS_ENABLED_ECO && "eco_org".equals(fdParentId)) {
            if (UserUtil.getKMSSUser().isAdmin() || UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_READER") || UserUtil.checkRole("ROLE_SYSORG_ECO_ADMIN") || UserUtil.checkRole("ROLE_SYSORG_ECO_ORG_ADMIN") || UserUtil.checkRole("ROLE_SYSORG_ECO_DEPT_ADMIN")) {
                whereBlock.append("sysOrgElement.hbmParent = null");
            } else {
                if (orgRange != null && orgRange.isShowMyDept()) {
                    whereBlock.append("(");
                }
                // 如果有查看范围限制，就取查看范围的根组织
                if (orgRange != null && CollectionUtils.isNotEmpty(orgRange.getRootDeptIds())) {
                    whereBlock.append("sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(orgRange.getRootDeptIds())).append(")");
                } else {
                    whereBlock.append("sysOrgElement.hbmParent = null");
                }
                if (orgRange != null && orgRange.isShowMyDept()) {
                    whereBlock.append(" or sysOrgElement.fdId in (").append(SysOrgUtil.buildInBlock(orgRange.getMyDeptIds())).append("))");
                }
            }
            whereBlock.append(" and sysOrgElement.fdIsExternal = :fdIsExternal");
            hqlInfo.setParameter("fdIsExternal", Boolean.TRUE);
        } else {
            whereBlock.append("sysOrgElement.hbmParent.fdId = :hbmParentId");
            hqlInfo.setParameter("hbmParentId", fdParentId);
        }
        // 如果类型单独为群组和角色，则不进行查询
        if (orgType == ORG_TYPE_GROUP || orgType == ORG_TYPE_ROLE || orgType == (ORG_TYPE_GROUP | ORG_TYPE_ROLE)) {
            return null;
        }
        orgType = orgType & (ORG_TYPE_ALLORG | ORG_FLAG_AVAILABLEALL | ORG_FLAG_BUSINESSALL);
        hqlInfo.setWhereBlock(SysOrgHQLUtil.buildWhereBlock(orgType, whereBlock.toString(), "sysOrgElement"));
        // 多语言
        hqlInfo.setOrderBy("sysOrgElement.fdOrgType desc, sysOrgElement.fdOrder, sysOrgElement." + SysLangUtil.getLangFieldName("fdName"));
        hqlInfo.setRowSize(SysOrgUtil.LIMIT_RESULT_SIZE + 1);
        hqlInfo.setGetCount(false);

        listQuery.hqlInfo = hqlInfo;
        listQuery.orgType = orgType;
        return listQuery;
    }

    /**
     * 格式化结果集
     *
     * @param xmlContext
     * @param elemList
     * @param listQuery
     * @param authCheck
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> result(RequestContext xmlContext, List<SysOrgElement> elemList, OrgListQuery listQuery, boolean authCheck) throws Exception {
        String fdParentId = xmlContext.getParameter("parent");
        int orgType = listQuery.orgType;
        if (authCheck) {
            // 职级过滤
            elemList = sysOrganizationStaffingLevelService.getStaffingLevelFilterResult(elemList);
        }
        // 返回到地址本时，如果不是"仅自己"时，过滤根组织下的"自己"
        AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
        if (!orgRange.isSelf() && StringUtil.isNull(fdParentId)) {
            String userId = UserUtil.getKMSSUser().getUserId();
            for (SysOrgElement element : elemList) {
                if (element.getFdId().equals(userId) && element.getFdParent() != null) {
                    elemList.remove(element);
                }
            }
        }
        if (elemList.size() > SysOrgUtil.LIMIT_RESULT_SIZE) {
            return OrgDialogUtil.getResultEntries(elemList, xmlContext.getContextPath());
        }
        // 点击部门或机构时，把这部门或机构下的所有岗位中的人员，加到列表中去
        if ((orgType & ORG_TYPE_PERSON) == ORG_TYPE_PERSON && StringUtil.isNotNull(fdParentId)) {
            HQLInfo info = new HQLInfo();
            info.setParameter("hbmParentId", fdParentId);
            info.setWhereBlock(SysOrgHQLUtil.buildWhereBlock(ORG_TYPE_POST, "sysOrgElement.hbmParent.fdId = :hbmParentId", "sysOrgElement"));
            info.setOrderBy("sysOrgElement.fdOrder");
            List<SysOrgElement> postList = sysOrgElementService.findList(info);
            for (SysOrgElement post : postList) {
                List<SysOrgElement> ss = post.getFdPersons();
                ss = sysOrganizationStaffingLevelService.getStaffingLevelFilterResult(ss);
                for (int i = 0; i < ss.size(); i++) {
                    SysOrgElement soeOri = ss.get(i);
                    SysOrgElement soe = new SysOrgElement();
                    cloneOrg(soe, soeOri);
                    if (soe.getFdIsAvailable() && soe.getFdIsBusiness() && elemList != null && !elemList.contains(soe)) {
                        soe.setFdOrder(post.getFdOrder());
                        elemList.add(soe);
                        if (elemList.size() > SysOrgUtil.LIMIT_RESULT_SIZE) {
                            return OrgDialogUtil.getResultEntries(elemList, xmlContext.getContextPath());
                        }
                    }
                }
            }

            Collections.sort(elemList, new Comparator<SysOrgElement>() {
                @Override
                public int compare(SysOrgElement org1, SysOrgElement org2) {
                    Integer i1 = org1.getFdOrder() == null ? Integer.MAX_VALUE : org1.getFdOrder();
                    Integer i2 = org2.getFdOrder() == null ? Integer.MAX_VALUE : org2.getFdOrder();
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
        return OrgDialogUtil.getResultEntries(elemList, xmlContext.getContextPath());
    }

    public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
        this.sysOrgElementService = sysOrgElementService;
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

    public void setSysOrganizationVisibleService(ISysOrganizationVisibleService sysOrganizationVisibleService) {
        this.sysOrganizationVisibleService = sysOrganizationVisibleService;
    }

    public void setRoleValidator(RoleValidator roleValidator) {
        this.roleValidator = roleValidator;
    }

    public RoleValidator getRoleValidator() {
        return roleValidator;
    }

    private SysOrgConfig orgConfig = null;

    private boolean getOrgAeraEnableFromDB() {
        boolean isOrgAeraEnable = false;
        try {
            if (this.orgConfig == null) {
                this.orgConfig = new SysOrgConfig();
            }
            String orgAeraEnable_str = this.orgConfig.getOrgAeraEnable();
            if (StringUtil.isNotNull(orgAeraEnable_str)) {
                isOrgAeraEnable = Boolean.parseBoolean(orgAeraEnable_str);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return isOrgAeraEnable;
    }

    private static class OrgListQuery {
        /**
         * 查询信息
         */
        private HQLInfo hqlInfo;
        /**
         * 组织类型
         */
        private int orgType;

        private OrgListQuery() {
        }

        public static OrgListQuery getInstance(HQLInfo hqlInfo) {
            OrgListQuery query = new OrgListQuery();
            query.hqlInfo = hqlInfo;
            return query;
        }

    }

}
