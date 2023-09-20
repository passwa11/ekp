package com.landray.kmss.sys.organization.eco;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.util.TripartiteAdminUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgElementRange;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrganizationVisibleService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.landray.kmss.sys.organization.eco.SysOrgShowRange.ECO_ADMIN_TYPE_1;
import static com.landray.kmss.sys.organization.eco.SysOrgShowRange.ECO_ADMIN_TYPE_2;
import static com.landray.kmss.sys.organization.eco.SysOrgShowRange.ECO_ADMIN_TYPE_3;

/**
 * 组织查看范围服务，计算当前用户能查询的组织范围
 * <p>
 * 查看组织范围：
 * 1. 组织查看范围
 * 2. 全局可见性过滤
 * 以上2种配置仅计算有权限查看的组织，无权限查看的将被过滤掉
 * <p>
 * 隐藏组织设置
 * 1. 如果没有查看范围限制，需要记录所有隐藏的组织，在查询数据时过滤
 * 2. 如果有查看范围限制，仅记录在查看范围内的隐藏组织，在查询数据时过滤
 * 3. 如果隐藏组织是"我"所在的组织，不隐藏
 * 4. 如果隐藏组织是"我"的父级组织，需要将"我"所有的组织提取到根组织
 * <p>
 * 计算结果：
 * 1. 我所在的组织
 * 2. 内部组织：查看范围 + 隐藏设置
 * 3. 生态组织：查看范围 + 隐藏设置
 * <p>
 * 特殊场景：
 * 对于机构有层级的场景，不管是查看范围，还是隐藏设置，需要把所有层级的机构计算出来，不然在搜索时，无法穿透层级机构
 *
 * @author 潘永辉 2021-11-08
 */
public class OrgRangeServiceImp implements IOrgRangeService, SysOrgConstant {
    private static final Logger logger = LoggerFactory.getLogger(OrgRangeServiceImp.class);

    /**
     * 内部组织
     */
    private ISysOrgElementService sysOrgElementService;

    /**
     * 地址本隔离全局配置
     */
    private ISysOrganizationVisibleService sysOrganizationVisibleService;

    public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
        this.sysOrgElementService = sysOrgElementService;
    }

    public void setSysOrganizationVisibleService(ISysOrganizationVisibleService sysOrganizationVisibleService) {
        this.sysOrganizationVisibleService = sysOrganizationVisibleService;
    }

    /**
     * 获取查看范围权限（入口方法，只在登录成功后调用）
     *
     * @return
     */
    @Override
    public AuthOrgRange getAuthOrgRange(KMSSUser kmssUser) {
        // 匿名用户和管理员不处理
        if (kmssUser.isAnonymous() || kmssUser.isEveryone() || kmssUser.isAdmin()) {
            return null;
        }
        // 如果开启三员管理时，系统管理员和安全管理员不处理
        if (TripartiteAdminUtil.isSysadmin() || TripartiteAdminUtil.isSecurity() || TripartiteAdminUtil.isAuditor()) {
            return null;
        }

        // 普通用户才需要计算查看范围
        try {
            SysOrgPerson person = kmssUser.getPerson();
            // 获取"我"的权限ID
            List<String> authOrgIds = kmssUser.getUserAuthInfo().getAuthOrgIds();
            AuthOrgRange orgRange = new AuthOrgRange();
            orgRange.setExternal(person.getFdIsExternal());
            // 获取我的所有组织
            orgRange.setMyDepts(findMyDepts(person));
            // 获取所有部门的查看范围限制
            Set<SysOrgShowRange> ranges = findMyRanges(person);
            if (CollectionUtils.isNotEmpty(ranges)) {
                boolean isSelf = true;
                for (SysOrgShowRange range : ranges) {
                    if (!range.isSelf()) {
                        isSelf = false;
                        break;
                    }
                }
                orgRange.setSelf(isSelf);
            }
            // 如果部门层级没有限制，需要获取全局过滤
            if (CollectionUtils.isEmpty(ranges)) {
                Set<String> ids = sysOrganizationVisibleService.getPersonRootVisibleOrgIds(kmssUser);
                if (CollectionUtils.isNotEmpty(ids)) {
                    List<SysOrgElement> elems = sysOrgElementService.findByPrimaryKeys(ids.toArray(new String[]{}));
                    if (CollectionUtils.isNotEmpty(elems)) {
                        // 加入全局可见性配置
                        for (SysOrgElement elem : elems) {
                            ranges.add(convertRange(elem, false));
                        }
                    }
                }
            }
            if (logger.isDebugEnabled()) {
                logger.debug("查看范围限制：{}", ranges);
            }
            if (CollectionUtils.isEmpty(ranges)) {
                // 无查看范围限制（可以查看无部门人员和岗位）
                orgRange.setUnlimited(true);
            }
            if (CollectionUtils.isNotEmpty(ranges)) {
                // 过滤有角色权限的范围
                filterUse(kmssUser, ranges);
                // 如果查看范围不为空，需要对范围进行上下级过滤处理
                // 比如：在范围里，有"A部门"的使用权限，也有"B部门"的查看权限（B部门是A部门的子节点），那么需要把"B部门"过滤掉，只保留"A部门"
                ranges = filterSubRange(ranges);
                // 机构ID，因为机构权限不继承，对于层级ID来说，无法处理多层级机构（以下代码会引起机构权限穿透，暂时注释）
//                List<String> _orgIds = new ArrayList<>();
//                if (CollectionUtils.isNotEmpty(ranges)) {
//                    for (SysOrgShowRange range : ranges) {
//                        if (range.getFdOrgType() == 1) {
//                            _orgIds.add(range.getFdId());
//                        }
//                    }
//                }
//                // 获取在查看范围内的所有机构
//                List<String> orgIds = new ArrayList<>();
//                if (CollectionUtils.isNotEmpty(_orgIds)) {
//                    getOrgIdsByParents(orgIds, _orgIds);
//                }
//                if (CollectionUtils.isNotEmpty(orgIds)) {
//                    List<SysOrgElement> elems = sysOrgElementService.findByPrimaryKeys(orgIds.toArray(new String[]{}));
//                    if (CollectionUtils.isNotEmpty(elems)) {
//                        for (SysOrgElement elem : elems) {
//                            ranges.add(convertRange(elem, false));
//                        }
//                    }
//                }
            }
            Set<SysOrgBaseRange> hideRanges = null;
            // 如果是仅自己，就不需要查看隐藏组织了
            if (!orgRange.isSelf()) {
                // 获取隐藏组织
                hideRanges = findHideRange(orgRange, authOrgIds, ranges);
                if (logger.isDebugEnabled()) {
                    logger.debug("隐藏组织：{}", hideRanges);
                }
                // 处理我的父组织隐藏时的逻辑
                handleShowRangeAndHideRange(orgRange, ranges, hideRanges, person);
                // 处理机构层级，对于多层级机构来说，父机构隐藏，子机构如果不隐藏，需要显示
                List<String> _orgIds = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(hideRanges)) {
                    for (SysOrgBaseRange range : hideRanges) {
                        if (range.getFdOrgType() == 1) {
                            _orgIds.add(range.getFdId());
                        }
                    }
                }
                // 获取隐藏的所有机构
                List<String> orgIds = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(_orgIds)) {
                    getOrgIdsByParents(orgIds, _orgIds);
                    if (CollectionUtils.isNotEmpty(orgIds)) {
                        orgIds.removeAll(_orgIds);
                    }
                }
                if (CollectionUtils.isNotEmpty(orgIds)) {
                    List<SysOrgElement> elems = sysOrgElementService.findByPrimaryKeys(orgIds.toArray(new String[]{}));
                    if (CollectionUtils.isNotEmpty(elems)) {
                        for (SysOrgElement elem : elems) {
                            orgRange.getAuthOtherRanges().add(convertRange(elem, false));
                            if (!orgIds.contains(elem.getFdParent().getFdId())) {
                                orgRange.getAuthOtherRootIds().add(elem.getFdId());
                            }
                        }
                    }
                }
            }
            // 所属组织ID：myDeptIds
            if (CollectionUtils.isNotEmpty(orgRange.getMyDepts())) {
                // 这里有一种场景，有多级机构，父机构隐藏，子机构不隐藏时，且没有查看限制时，需要把子机构加入到根节点（但是需要剔除所在部门ID，不然会重复）
                String parentOrgId = null;
                if (orgRange.isShowMyDept() && CollectionUtils.isEmpty(ranges) && CollectionUtils.isNotEmpty(hideRanges)) {
                    Set<String> hideIds = new HashSet<>();
                    for (SysOrgBaseRange hideRange : hideRanges) {
                        hideIds.add(hideRange.getFdId());
                    }
                    SysOrgElement parentOrg = person.getFdParentOrg();
                    if (parentOrg != null) {
                        parentOrg = parentOrg.getFdParent();
                        while (parentOrg != null) {
                            if (hideIds.contains(parentOrg.getFdId())) {
                                parentOrgId = person.getFdParentOrg().getFdId();
                                break;
                            }
                            parentOrg = parentOrg.getFdParent();
                        }
                    }
                }
                if (StringUtil.isNotNull(parentOrgId)) {
                    orgRange.getMyDeptIds().add(parentOrgId);
                } else {
                    for (SysOrgMyDeptRange deptRange : orgRange.getMyDepts()) {
                        orgRange.getMyDeptIds().add(deptRange.getFdId());
                    }
                }
            }
            if (!orgRange.isSelf() && person.getFdParent() != null) {
                // 如果需要显示所属组织，说明父组织有隐藏，此时，还需要处理一种场景，子组织有隐藏时，需要过滤
                Set<SysOrgBaseRange> hideRangeBy = findHideRangeBy(authOrgIds, person.getFdParent().getFdHierarchyId());
                if (CollectionUtils.isNotEmpty(hideRangeBy)) {
                    // 在过滤本部门时，需要再次过滤子隐藏组织
                    for (SysOrgBaseRange range : hideRangeBy) {
                        orgRange.getSubHideHids().add(range.getFdHierarchyId());
                    }
                    hideRanges.addAll(hideRangeBy);
                }
            }
            // 保存根组织ID
            if (CollectionUtils.isNotEmpty(ranges)) {
                // 对于机构的层级，这里需要过滤
                Set<String> ids = new HashSet<>();
                for (SysOrgShowRange range : ranges) {
                    ids.add(range.getFdId());
                }
                // 在显示根机构时，需要过滤有上级机构的节点
                for (SysOrgShowRange range : ranges) {
                    if ((range.getFdOrgType() == 1 || range.getFdOrgType() == 2) && !ids.contains(range.getFdParentId())) {
                        orgRange.getRootDeptIds().add(range.getFdId());
                    } else if (range.getFdOrgType() == 4 || range.getFdOrgType() == 8) {
                        orgRange.getRootPersonIds().add(range.getFdId());
                    }
                }
            } else if (SysOrgEcoUtil.IS_ENABLED_ECO && BooleanUtils.isNotTrue(person.getFdIsExternal())) {
                // 对于普通内部用户，如果没有查看范围限制时，可以查看没有隐藏的生态组织
                findNoHideOuter(orgRange, authOrgIds);
                // 如果没有查看范围，但是有隐藏组织时，需要处理另一种场景，如：父机构隐藏，子机构不隐藏，"我"在子机构里。在上面的计算中由于机构的层级不体现在层级ID中，所以无法被计算到
                SysOrgElement parentOrg = UserUtil.getUser().getFdParentOrg();
                while (parentOrg != null) {
                    parentOrg = parentOrg.getFdParent();
                }
            }
            // 获取生态组织管理员
            if (SysOrgEcoUtil.IS_ENABLED_ECO) {
                // 加入组织管理员
                orgRange.setAdminRanges(addAuthAdmin(person));
            }
            // 设置查看范围
            orgRange.setAuthRanges(ranges);
            // 设置隐藏组织
            orgRange.setHideRanges(hideRanges);
            return orgRange;
        } catch (Exception e) {
            logger.error("获取组织可见性范围失败：", e);
        }
        return null;
    }

    /**
     * 根据父ID获取子机构ID
     *
     * @param orgIds
     * @param pids
     * @throws Exception
     */
    private void getOrgIdsByParents(List<String> orgIds, List<String> pids) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock("fdId");
        hqlInfo.setWhereBlock("fdOrgType = :fdOrgType and fdIsAvailable = :fdIsAvailable and fdIsBusiness = :fdIsBusiness and hbmParent.fdId in (:pids)");
        hqlInfo.setParameter("fdOrgType", 1);
        hqlInfo.setParameter("fdIsAvailable", true);
        hqlInfo.setParameter("fdIsBusiness", true);
        hqlInfo.setParameter("pids", pids);
        List<String> _ids = sysOrgElementService.findList(hqlInfo);
        if (CollectionUtils.isNotEmpty(_ids)) {
            orgIds.addAll(_ids);
            getOrgIdsByParents(orgIds, _ids);
        }
    }

    /**
     * 过滤有角色权限的范围
     *
     * @param kmssUser
     * @param ranges
     */
    private void filterUse(KMSSUser kmssUser, Set<SysOrgShowRange> ranges) {
        // 拥有使用所有组织角色
        boolean isALLInner = false;
        boolean isAllOuter = false;
        List<String> roleAliases = kmssUser.getUserAuthInfo().getAuthRoleAliases();
        // 使用所有内部组织
        if (roleAliases.contains("ROLE_SYSORG_DIALOG_USER")) {
            isALLInner = true;
        }
        // 使用所有生态组织
        if (roleAliases.contains("ROLE_SYSORG_ECO_DEPT_READER")) {
            isAllOuter = true;
        }
        if (isALLInner || isAllOuter) {
            Iterator<SysOrgShowRange> iterator = ranges.iterator();
            while (iterator.hasNext()) {
                SysOrgShowRange range = iterator.next();
                if (isALLInner && !range.isExternal()) {
                    iterator.remove();
                }
                if (isAllOuter && range.isExternal()) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 组织管理员权限过滤
     *
     * @param list
     * @return
     */
    @Override
    public List<SysOrgElement> authFilterAdmin(List<SysOrgElement> list) {
        if (CollectionUtils.isEmpty(list) || UserUtil.getKMSSUser().isAdmin()
                || UserUtil.checkRole("ROLE_SYSORG_ORG_ADMIN") || UserUtil.checkRole("ROLE_SYSORG_ECO_ORG_ADMIN")) {
            return list;
        }
        AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
        if (orgRange == null) {
            return list;
        }
        Set<SysOrgShowRange> adminRanges = orgRange.getAdminRanges();
        if (CollectionUtils.isEmpty(adminRanges)) {
            // 当前用户没有任何管辖的组织
            return Collections.EMPTY_LIST;
        }
        // 管理员权限过滤
        Iterator<SysOrgElement> iterator = list.iterator();
        while (iterator.hasNext()) {
            SysOrgElement element = iterator.next();
            // 未开启隐藏的组织，不需要过滤
            if (element.getFdHideRange() == null || !BooleanUtils.isTrue(element.getFdHideRange().getFdIsOpenLimit())) {
                continue;
            }
            // 非管理员管辖的组织，需要去除
            boolean isDel = true;
            for (SysOrgShowRange range : adminRanges) {
                switch (range.getAdminType()) {
                    case ECO_ADMIN_TYPE_1: {
                        // 类型管理员，可以维护组织类型及所有子组织
                        if (element.getFdId().equals(range.getFdId()) || element.getFdHierarchyId().startsWith(range.getFdHierarchyId())) {
                            isDel = false;
                        }
                        break;
                    }

                    case ECO_ADMIN_TYPE_2: {
                        // 组织管理员，不能维护组织类型，只能维护自己创建的组织/岗位/人员
                        if (element.getFdId().equals(range.getFdId()) || (element.getDocCreator() != null && element.getDocCreator().getFdId().equals(UserUtil.getKMSSUser().getUserId()))) {
                            isDel = false;
                        }
                        break;
                    }

                    case ECO_ADMIN_TYPE_3: {
                        // 组织负责人，可以维护管理该组织及子组织/岗位/人员
                        if (range.getFdHierarchyId().startsWith(element.getFdHierarchyId()) || element.getFdHierarchyId().startsWith(range.getFdHierarchyId())) {
                            isDel = false;
                        }
                        break;
                    }
                }
                if (!isDel) {
                    break;
                }
            }
            // 非管辖的组织，需要删除
            if (isDel) {
                iterator.remove();
            }
        }
        return list;
    }

    /**
     * 获取管辖的组织层级ID，在所管辖的组织里，有维护权限
     *
     * @return
     */
    @Override
    public Set<String> getAdminHids() {
        AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
        if (orgRange != null) {
            Set<SysOrgShowRange> adminRanges = orgRange.getAdminRanges();
            if (CollectionUtils.isNotEmpty(adminRanges)) {
                Set<String> adminHids = new HashSet<>();
                for (SysOrgShowRange adminRange : adminRanges) {
                    adminHids.add(adminRange.getFdHierarchyId());
                }
            }
        }
        return Collections.EMPTY_SET;
    }

    /**
     * 获取隐藏名称
     * 1. 当名称小于2个字符时，显示全部
     * 2. 当名称等于2个字符时，显示第1个，隐藏第2个
     * 3. 当名称大于2个字符时，显示第1个和最后1个，中间所有字符隐藏
     *
     * @param name
     * @return
     */
    private String getHideName(String name) {
        if (StringUtil.isNotNull(name) && name.length() > 1) {
            String first = name.substring(0, 1);
            if (name.length() == 2) {
                return first + "*";
            } else {
                return first + "*****" + name.substring(name.length() - 1);
            }
        }
        return name;
    }

    /**
     * 获取"我"所属的部门
     *
     * @param person
     * @return
     */
    private Set<SysOrgMyDeptRange> findMyDepts(SysOrgPerson person) {
        Set<SysOrgMyDeptRange> myDepts = new HashSet<>();
        // 获取我直属部门的查看范围
        if (person.getFdParent() != null) {
            myDepts.add(convertMyDeptRange(person.getFdParent(), person.getFdOrgType(), person.getFdName()));
        }
        List<SysOrgElement> posts = person.getFdPosts();
        if (CollectionUtils.isNotEmpty(posts)) {
            // 获取岗位的查看范围
            for (SysOrgElement post : posts) {
                if (post.getFdParent() != null) {
                    myDepts.add(convertMyDeptRange(post.getFdParent(), post.getFdOrgType(), post.getFdName()));
                }
            }
        }
        return myDepts;
    }

    /**
     * 获取所有我能查看的组织范围
     *
     * @param person
     * @return
     */
    private Set<SysOrgShowRange> findMyRanges(SysOrgPerson person) {
        Set<SysOrgShowRange> ranges = new HashSet<>();
        Map<String, SysOrgRangeMapping> mapping = null;
        // 获取我直属部门的查看范围
        if (person.getFdParent() != null) {
            mapping = getRangeByDept(person);
        }
        // 如果直属部门没有限制，那就不需要查询岗位
        if (mapping != null && !mapping.isEmpty()) {
            // 如果直属部门有限制，需要查询岗位
            List<SysOrgElement> posts = person.getFdPosts();
            if (CollectionUtils.isNotEmpty(posts)) {
                // 获取岗位的查看范围
                for (SysOrgElement post : posts) {
                    // 获取岗位所属部门的查看范围限制
                    Map<String, SysOrgRangeMapping> map = null;
                    if (post.getFdParent() != null) {
                        map = getRangeByDept(post);
                    }
                    if (map == null || map.isEmpty()) {
                        // 找到一个没有限制的层级
                        // 如果人员有岗位，且访岗位没有层级查看限制，那么之前所属部门的查看限制也不需要了
                        mapping.clear();
                        break;
                    } else {
                        mapping.putAll(map);
                    }
                }
            }
        }
        if (mapping != null && !mapping.isEmpty()) {
            // 找到部门的查看范围限制
            parseRanges(person, mapping, ranges);
        }
        return ranges;
    }

    /**
     * 从部门层级获取查看范围
     * <p>
     * 如果所属部门没有开启查看范围，则获取上级部门，以此类推
     *
     * @param personPost 人员或岗位
     * @return
     */
    private Map<String, SysOrgRangeMapping> getRangeByDept(SysOrgElement personPost) {
        SysOrgElement parent = personPost.getFdParent();
        String hid = parent.getFdHierarchyId();
        Map<String, SysOrgRangeMapping> map = new HashMap<String, SysOrgRangeMapping>();
        try {
            if (hid != null) {
                SysOrgElementRange range = null;
                if (StringUtil.isNotNull(hid)) {
                    // 解析层级ID
                    String[] ids = hid.split(BaseTreeConstant.HIERARCHY_ID_SPLIT);
                    if (ids != null && ids.length > 0) {
                        List<SysOrgElement> parents = sysOrgElementService.findByPrimaryKeys(ids);
                        // 从最近的部门向上取权限设置
                        for (int i = ids.length - 1; i >= 0; i--) {
                            if (StringUtil.isNull(ids[i])) {
                                continue;
                            }
                            SysOrgElement elem = parents.get(i);
                            if (elem != null) {
                                range = elem.getFdRange();
                                // 有配置权限，并开启
                                if (range != null && BooleanUtils.isTrue(range.getFdIsOpenLimit())) {
                                    if (personPost.getFdOrgType() == SysOrgConstant.ORG_TYPE_POST) {
                                        map.put(elem.getFdId(), SysOrgRangeMapping.build(elem, personPost, range));
                                    } else {
                                        map.put(elem.getFdId(), SysOrgRangeMapping.build(elem, range));
                                    }
                                    return map;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取组织可见范围失败：", e);
        }
        return map;
    }

    private void parseRanges(SysOrgPerson person, Map<String, SysOrgRangeMapping> map, Set<SysOrgShowRange> ranges) {
        if (map != null && !map.isEmpty()) {
            for (String key : map.keySet()) {
                parseRange(person, map.get(key), ranges);
            }
            // 无论在什么情况下，都应该要看到自己，这里需要判断处理好的可见范围是否包含“自己”，如果不包含需要增加
            List<SysOrgElement> posts = person.getFdPosts();
            List<SysOrgElement> selfs = new ArrayList<SysOrgElement>();
            selfs.add(person);
            if (CollectionUtils.isNotEmpty(posts)) {
                selfs.addAll(posts);
            }
            for (SysOrgElement self : selfs) {
                boolean exist = false;
                for (SysOrgShowRange range : ranges) {
                    if (self.getFdId().equals(range.getFdId())
                            || self.getFdHierarchyId().startsWith(range.getFdHierarchyId())) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    // 增加“仅自己”
                    ranges.add(convertRange(self, true));
                }
            }
        }
    }

    /**
     * 解析查看范围
     *
     * @param person
     * @param mapping
     * @param ranges
     */
    private void parseRange(SysOrgPerson person, SysOrgRangeMapping mapping, Set<SysOrgShowRange> ranges) {
        SysOrgElement elem = mapping.getElement();
        SysOrgElementRange range = mapping.getRange();
        if (BooleanUtils.isTrue(range.getFdIsOpenLimit())) {
            Integer type = range.getFdViewType();
            if (type != null) {
                if (type == 0) {
                    // 仅自己
                    if (mapping.post != null) {
                        parseRangeByPerson(mapping.post, ranges, true);
                    } else {
                        parseRangeByPerson(person, ranges, true);
                    }
                } else if (type == 1) {
                    // 仅所在组织及下级组织/人员
                    ranges.add(convertRange(elem, false));
                } else if (type == 2) {
                    // 指定组织/人员
                    String subType = range.getFdViewSubType();
                    if (StringUtil.isNotNull(subType)) {
                        if (subType.contains("1")) {
                            // 仅所在组织及下级组织/人员
                            ranges.add(convertRange(elem, false));
                        } else {
                            // 如果不能查看所有组织，需要把自己加上
                            if (mapping.post != null) {
                                parseRangeByPerson(mapping.post, ranges, true);
                            } else {
                                parseRangeByPerson(person, ranges, true);
                            }
                        }
                        if (subType.contains("2")) {
                            List<SysOrgElement> others = range.getFdOthers();
                            if (CollectionUtils.isNotEmpty(others)) {
                                for (SysOrgElement other : others) {
                                    if (other.getFdOrgType() == ORG_TYPE_ORG || other.getFdOrgType() == ORG_TYPE_DEPT) {
                                        // 机构或部门
                                        ranges.add(convertRange(other, false));
                                    } else {
                                        // 人员或岗位
                                        parseRangeByPerson(other, ranges, false);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 解析"仅自己"或"指定查看人员/岗位"
     *
     * @param elem
     * @param ranges
     * @param isSelf
     */
    private void parseRangeByPerson(SysOrgElement elem, Set<SysOrgShowRange> ranges, boolean isSelf) {
        // 人员或岗位
        ranges.add(convertRange(elem, isSelf));
    }

    /**
     * 组织架构转换为查看范围
     *
     * @param elem   组织架构对象
     * @param isSelf 是否仅自己
     * @return
     */
    private SysOrgShowRange convertRange(SysOrgElement elem, boolean isSelf) {
        return convertRange(elem, isSelf, 0);
    }

    /**
     * 组织架构转换为查看范围
     *
     * @param elem      组织架构对象
     * @param isSelf    是否仅自己
     * @param adminType 组织管理员类型：（1：管理员，可以维护组织类型及所有子组织。2：组织管理员，不能维护组织类型，只能维护自己创建的组织/岗位/人员。3：负责人，可以维护管理该组织及子组织/岗位/人员，4：内部组织管理员。）
     * @return
     */
    private SysOrgShowRange convertRange(SysOrgElement elem, boolean isSelf, int adminType) {
        SysOrgShowRange range = new SysOrgShowRange();
        range.setSelf(isSelf);
        range.setAdminType(adminType);
        range.setExternal(elem.getFdIsExternal());
        range.setFdId(elem.getFdId());
        range.setFdName(elem.getFdName());
        range.setFdOrgType(elem.getFdOrgType());
        range.setFdHierarchyId(elem.getFdHierarchyId());
        if (elem.getFdParent() != null) {
            range.setFdParentId(elem.getFdParent().getFdId());
        }
        return range;
    }

    /**
     * 转换我所属部门范围
     *
     * @param elem
     * @param type
     * @param name
     * @return
     */
    private SysOrgMyDeptRange convertMyDeptRange(SysOrgElement elem, int type, String name) {
        SysOrgMyDeptRange myDept = new SysOrgMyDeptRange();
        myDept.setFdId(elem.getFdId());
        myDept.setFdName(elem.getFdName());
        myDept.setFdOrgType(elem.getFdOrgType());
        myDept.setFdHierarchyId(elem.getFdHierarchyId());
        myDept.setExternal(elem.getFdIsExternal());
        myDept.setType(type);
        myDept.setName(name);
        return myDept;
    }

    private final String findHideBaseSql = "select distinct elem.fd_id, elem.fd_hierarchy_id, elem.fd_org_type, " +
            "elem.fd_is_external, elem.fd_name, hide.fd_id as hideid, elem.fd_parentorgid, elem.fd_parentid from sys_org_element elem " +
            "left join sys_org_element_hide_range hide on hide.fd_element_id = elem.fd_id " +
            "where elem.fd_org_type in (1, 2) and elem.fd_is_available = :available " +
            "and hide.fd_is_open_limit = :openLimit and hide.fd_view_type = :viewType";
    /**
     * 查询对所有人隐藏的组织
     *
     * @param viewType
     * @param baseHid
     * @return
     * @throws Exception
     */
    private List<Object[]> findHide(int viewType, String baseHid) throws Exception {
        // 查询对所有人隐藏的组织
        String sql = findHideBaseSql;
        if (StringUtil.isNotNull(baseHid)) {
            sql += " and elem.fd_hierarchy_id like :baseHid and elem.fd_hierarchy_id != :baseHid2 ";
        }
        // 如果没有开启生态，不查询生态组织
        if (!SysOrgEcoUtil.IS_ENABLED_ECO) {
            sql += " and (elem.fd_is_external is null or elem.fd_is_external = :external)";
        }
        // 查询对"我"隐藏的组织
        NativeQuery hideQuery = sysOrgElementService.getBaseDao().getHibernateSession().createNativeQuery(sql)
                .setParameter("available", Boolean.TRUE)
                .setParameter("openLimit", Boolean.TRUE).setParameter("viewType", viewType);
        hideQuery.setCacheable(true);
        hideQuery.setCacheMode(CacheMode.NORMAL);
        hideQuery.setCacheRegion("sys-organization");
        hideQuery.addScalar("fd_id", StandardBasicTypes.STRING)
                .addScalar("fd_hierarchy_id",StandardBasicTypes.STRING)
                .addScalar("fd_org_type",StandardBasicTypes.INTEGER)
                .addScalar("fd_is_external",StandardBasicTypes.BOOLEAN)
                .addScalar("fd_name",StandardBasicTypes.STRING)
                .addScalar("fd_id",StandardBasicTypes.STRING)
                .addScalar("hideid",StandardBasicTypes.STRING)
                .addScalar("fd_parentorgid",StandardBasicTypes.STRING)
                .addScalar("fd_parentid",StandardBasicTypes.STRING);
        if (!SysOrgEcoUtil.IS_ENABLED_ECO) {
            hideQuery.setParameter("external", Boolean.FALSE);
        }
        if (StringUtil.isNotNull(baseHid)) {
            hideQuery.setParameter("baseHid", baseHid + "%");
            hideQuery.setParameter("baseHid2", baseHid);
        }
        return hideQuery.list();
    }

    /**
     * 查询对所有人隐藏的组织
     *
     * @return
     * @throws Exception
     */
    private List<Object[]> findHide(int viewType) throws Exception {
        return findHide(viewType, null);
    }

    /**
     * 查询部分隐藏，但对我不可见的组织
     *
     * @param authOrgIds
     * @param baseHid
     * @return
     * @throws Exception
     */
    private List<Object[]> findPartHide(List<String> authOrgIds, String baseHid) throws Exception {
        List<Object[]> list = findHide(1, baseHid);
        Set<String> rangeIds = new HashSet<>();
        for (Object[] objs : list) {
            rangeIds.add((String) objs[5]);
        }
        // 查询有权限的组织
        String sql = "select distinct other.fd_hideid from sys_org_element_hide_other other where other.fd_otherid in (:authOrgIds)";
        NativeQuery hideQuery = sysOrgElementService.getBaseDao().getHibernateSession().createNativeQuery(sql)
                .setParameter("authOrgIds", authOrgIds);
        hideQuery.setCacheable(true);
        hideQuery.setCacheMode(CacheMode.NORMAL);
        hideQuery.setCacheRegion("sys-organization");
        hideQuery.addScalar("fd_hideid",StandardBasicTypes.STRING);
        List<String> authIds = hideQuery.list();
        // 遍历一下部分隐藏的组织，如果不在有权限的组织里，那就是无仅限了
        Iterator<Object[]> iterator = list.iterator();
        while (iterator.hasNext()) {
            Object[] next = iterator.next();
            String hideId = (String) next[5];
            // 如果有权限，就不隐藏
            if (authIds.contains(hideId)) {
                iterator.remove();
            }
        }
        return list;
    }

    /**
     * 查询部分隐藏，但对我不可见的组织
     *
     * @param authOrgIds
     * @return
     * @throws Exception
     */
    private List<Object[]> findPartHide(List<String> authOrgIds) throws Exception {
        return findPartHide(authOrgIds, null);
    }

    /**
     * 查询该组织下的隐藏组织
     *
     * @param baseHid
     * @return
     */
    private Set<SysOrgBaseRange> findHideRangeBy(List<String> authOrgIds, String baseHid) {
        Set<SysOrgBaseRange> hideRanges = new HashSet<>();
        try {
            // 查询对所有人隐藏的组织
            List<Object[]> list = findHide(0, baseHid);
            // 查询对部分人隐藏，但我没有不可查看的组织
            list.addAll(findPartHide(authOrgIds, baseHid));
            if (CollectionUtils.isNotEmpty(list)) {
                Set<String> hideHids = new HashSet<>();
                Map<String, SysOrgBaseRange> hideMap = new HashMap<>();
                for (Object[] obj : list) {
                    String id = String.valueOf(obj[0]);
                    SysOrgBaseRange hideRange = new SysOrgBaseRange();
                    hideRange.setFdId(id);
                    hideRange.setFdHierarchyId(String.valueOf(obj[1]));
                    hideRange.setFdOrgType(Integer.parseInt(String.valueOf(obj[2])));
                    hideRange.setExternal(Boolean.parseBoolean(String.valueOf(obj[3])));
                    // 处理隐藏名称
                    hideRange.setFdName(getHideName(String.valueOf(obj[4])));
                    hideMap.put(hideRange.getFdHierarchyId(), hideRange);
                    hideHids.add(hideRange.getFdHierarchyId());

                }
                // 过滤隐藏子组织
                hideHids = filterSubHierarchy(hideHids);
                for (String hid : hideHids) {
                    hideRanges.add(hideMap.get(hid));
                }
            }
        } catch (Exception e) {
            logger.error("获取隐藏组织失败：", e);
        }
        return hideRanges;
    }

    /**
     * 获取所有对"我"隐藏的组织
     *
     * @param authRange
     * @param authOrgIds
     * @param ranges
     * @return
     */
    private Set<SysOrgBaseRange> findHideRange(AuthOrgRange authRange, List<String> authOrgIds, Set<SysOrgShowRange> ranges) {
        Set<SysOrgBaseRange> hideRanges = new HashSet<>();
        try {
            // 查询对所有人隐藏的组织
            List<Object[]> list = findHide(0);
            // 查询对部分人隐藏，但我没有不可查看的组织
            list.addAll(findPartHide(authOrgIds));
            if (CollectionUtils.isNotEmpty(list)) {
                Set<String> hids = new HashSet<>();
                if (CollectionUtils.isNotEmpty(ranges)) {
                    for (SysOrgShowRange range : ranges) {
                        hids.add(range.getFdHierarchyId());
                    }
                }
                // 我所属的组织
                Set<String> myDeptIds = new HashSet<>();
                for (SysOrgMyDeptRange deptRange : authRange.getMyDepts()) {
                    myDeptIds.add(deptRange.getFdId());
                }
                Set<String> hideHids = new HashSet<>();
                Map<String, SysOrgBaseRange> hideMap = new HashMap<>();
                for (Object[] obj : list) {
                    String id = String.valueOf(obj[0]);
                    String hid = String.valueOf(obj[1]);
                    // 如果隐藏的组织是我所属的组织，需要跳过
                    if (myDeptIds.contains(id)) {
                        continue;
                    }
                    // 如果查看范围不为空，那么待处理的隐藏组织需要在查看范围内
                    if (CollectionUtils.isNotEmpty(hids)) {
                        boolean isInclude = false;
                        for (String _hid : hids) {
                            // 判断查看范围的父组织是否包含隐藏组织
                            if (_hid.contains(BaseTreeConstant.HIERARCHY_ID_SPLIT + id + BaseTreeConstant.HIERARCHY_ID_SPLIT)) {
                                isInclude = true;
                                break;
                            }
                            // 判断隐藏组织是否在查看范围里
                            if (hid.startsWith(_hid)) {
                                isInclude = true;
                                break;
                            }
                        }
                        if (!isInclude) {
                            continue;
                        }
                    }
                    SysOrgBaseRange hideRange = new SysOrgBaseRange();
                    hideRange.setFdId(id);
                    hideRange.setFdHierarchyId(String.valueOf(obj[1]));
                    hideRange.setFdOrgType(Integer.parseInt(String.valueOf(obj[2])));
                    hideRange.setExternal(Boolean.parseBoolean(String.valueOf(obj[3])));
                    // 处理隐藏名称
                    hideRange.setFdName(getHideName(String.valueOf(obj[4])));
                    hideMap.put(hideRange.getFdHierarchyId(), hideRange);
                    hideHids.add(hideRange.getFdHierarchyId());

                }
                // 过滤隐藏子组织
                hideHids = filterSubHierarchy(hideHids);
                for (String hid : hideHids) {
                    hideRanges.add(hideMap.get(hid));
                }
            }
        } catch (Exception e) {
            logger.error("获取隐藏组织失败：", e);
        }
        return hideRanges;
    }

    /**
     * 查看不隐藏的生态组织
     *
     * @param authRange
     * @param authOrgIds
     * @throws Exception
     */
    private void findNoHideOuter(AuthOrgRange authRange, List<String> authOrgIds) throws Exception {
        // 查询对所有人隐藏的组织
        String sql = "select distinct elem.fd_id, elem.fd_hierarchy_id, elem.fd_org_type, elem.fd_is_external, elem.fd_name, hide.fd_id as hideid from sys_org_element elem " +
                "left join sys_org_element_hide_range hide on hide.fd_element_id = elem.fd_id " +
                "left join sys_org_element_hide_other other on other.fd_hideid = hide.fd_id " +
                "where elem.fd_org_type in (1) and elem.fd_is_available = :available " +
                "and elem.fd_is_external = :external " +
                "and (hide.fd_is_open_limit = :openLimit or (hide.fd_view_type = :viewType and other.fd_otherid in (:authIds)))";
        // 查询对"我"隐藏的组织
        NativeQuery hideQuery = sysOrgElementService.getBaseDao().getHibernateSession().createNativeQuery(sql)
                .setParameter("available", Boolean.TRUE)
                .setParameter("openLimit", Boolean.FALSE)
                .setParameter("external", Boolean.TRUE)
                .setParameter("viewType", 2)
                .setParameterList("authIds", authOrgIds);
        hideQuery.setCacheable(true);
        hideQuery.setCacheMode(CacheMode.NORMAL);
        hideQuery.setCacheRegion("sys-organization");
        hideQuery.addScalar("fd_id",StandardBasicTypes.STRING).addScalar("fd_hierarchy_id",StandardBasicTypes.STRING).addScalar("fd_org_type",StandardBasicTypes.INTEGER)
                .addScalar("fd_is_external",StandardBasicTypes.BOOLEAN).addScalar("fd_name",StandardBasicTypes.STRING).addScalar("fd_id",StandardBasicTypes.STRING);
        List<Object[]> list = hideQuery.list();
        if (CollectionUtils.isNotEmpty(list)) {
            Set<String> hids = new HashSet<>();
            Map<String, SysOrgShowRange> showMap = new HashMap<>();
            for (Object[] obj : list) {
                hids.add(String.valueOf(obj[1]));
                SysOrgShowRange range = new SysOrgShowRange();
                range.setFdId(String.valueOf(obj[0]));
                range.setFdHierarchyId(String.valueOf(obj[1]));
                range.setFdOrgType(Integer.parseInt(String.valueOf(obj[2])));
                range.setExternal(Boolean.parseBoolean(String.valueOf(obj[3])));
                range.setFdName(String.valueOf(obj[4]));
                showMap.put(String.valueOf(obj[1]), range);
            }
            // 过滤子组织
            hids = filterSubHierarchy(hids);
            for (String hid : hids) {
                authRange.getAuthOuterRanges().add(showMap.get(hid));
            }
        }
    }

    /**
     * 过滤有重复的子组织
     * <p>
     * 比如：在范围里，有"A部门"的使用权限，也有"B部门"的查看权限（B部门是A部门的子节点），那么需要把"B部门"过滤掉，只保留"A部门"
     *
     * @param ranges
     * @return
     */
    private Set<SysOrgShowRange> filterSubRange(Set<SysOrgShowRange> ranges) {
        Set<String> hids = new HashSet<>();
        Map<String, SysOrgShowRange> map = new HashMap<>();
        for (SysOrgShowRange range : ranges) {
            map.put(range.getFdHierarchyId(), range);
            hids.add(range.getFdHierarchyId());
        }
        // 过滤子组织
        hids = filterSubHierarchy(hids);
        // 保存有权限，且过滤子组织的查看范围
        Set<SysOrgShowRange> showRanges = new HashSet<>();
        for (String hid : hids) {
            showRanges.add(map.get(hid));
        }
        return showRanges;
    }

    /**
     * 处理查看范围和隐藏组织的交集
     *
     * @param authRange
     * @param ranges
     * @param hideRanges
     * @param person
     */
    private void handleShowRangeAndHideRange(AuthOrgRange authRange, Set<SysOrgShowRange> ranges, Set<SysOrgBaseRange> hideRanges, SysOrgPerson person) {
        // 处理之前，是否没有查看范围限制
        Set<String> hideHids = new HashSet<>();
        for (SysOrgBaseRange hideRange : hideRanges) {
            hideHids.add(hideRange.getFdHierarchyId());
        }
        // 所在部门的全路径
        String myDeptHids = null;
        if (person.getFdParent() != null) {
            myDeptHids = person.getFdParent().getFdHierarchyId();
            for (String hideHid : hideHids) {
                if (myDeptHids.startsWith(hideHid)) {
                    // 该隐藏组织包含我的组织，需要把我的组织提取到查看范围里
                    authRange.setShowMyDept(true);
                    break;
                }
            }
//            if (!authRange.isShowMyDept()) {
//                // 如果隐藏组织是我的父级组织，需要把我所在的组织放到查看范围（自己所在的组织对自己不隐藏）
//                String myDeptFullHids = SysOrgUtil.buildHierarchyIdIncludeOrg(person.getFdParent());
//                for (String hideHid : hideHids) {
//                    if (myDeptFullHids.startsWith(hideHid)) {
//                        // 该隐藏组织包含我的组织，需要把我的组织提取到查看范围里
//                        authRange.setShowMyDept(true);
//                        break;
//                    }
//                }
//            }
        }

        // 如果查看的范围在隐藏组织内，需要排除（无法查看）
        if (CollectionUtils.isNotEmpty(ranges)) {
            Iterator<SysOrgShowRange> iterator = ranges.iterator();
            while (iterator.hasNext()) {
                SysOrgShowRange range = iterator.next();
                boolean isMyDept = false;
                for (SysOrgMyDeptRange deptRange : authRange.getMyDepts()) {
                    // 如果是我所在组织，不能过滤
                    if (deptRange.getFdHierarchyId().startsWith(range.getFdHierarchyId())) {
                        isMyDept = true;
                        break;
                    }
                }
                if (isMyDept && !authRange.isShowMyDept()) {
                    continue;
                }
                for (String hideHid : hideHids) {
                    if (range.getFdHierarchyId().startsWith(hideHid)) {
                        iterator.remove();
                        break;
                    }
                }
            }
            // 对于生态组织来说，如果这里过滤完没有查看范围限制，需要限制为所在组织
            if (StringUtil.isNotNull(myDeptHids) && BooleanUtils.isTrue(person.getFdIsExternal())) {
                boolean addMyDept = true;
                for (SysOrgShowRange range : ranges) {
                    if (myDeptHids.startsWith(range.getFdHierarchyId())) {
                        addMyDept = false;
                        break;
                    }
                }
                if (addMyDept) {
                    ranges.add(convertRange(person.getFdParent(), false));
                }
            }
        }
    }

    /**
     * 根据层级ID过滤子组织
     *
     * @param hids
     * @return
     */
    public static Set<String> filterSubHierarchy(Set<String> hids) {
        if (CollectionUtils.isEmpty(hids)) {
            return Collections.EMPTY_SET;
        }
        Set<String> resultTemp = new HashSet<String>();
        Set<String> addTemp = new HashSet<String>();
        Set<String> delTemp = new HashSet<String>();
        for (String hid1 : hids) {
            boolean add = true;
            for (String hid2 : resultTemp) {
                if (hid2.startsWith(hid1)) {
                    add = false;
                    delTemp.add(hid2);
                    addTemp.add(hid1);
                    continue;
                } else if (hid1.startsWith(hid2)) {
                    add = false;
                    break;
                }
            }
            if (add) {
                resultTemp.add(hid1);
            } else {
                resultTemp.removeAll(delTemp);
                resultTemp.addAll(addTemp);
                delTemp.clear();
                addTemp.clear();
            }
        }
        return resultTemp;
    }

    /**
     * 获取生态组织管理员
     *
     * @param person
     * @throws Exception
     */
    private Set<SysOrgShowRange> addAuthAdmin(SysOrgPerson person) throws Exception {
        Set<SysOrgShowRange> adminRanges = new HashSet<>();
        List<String> orgIds = new ArrayList<>();
        orgIds.add(person.getFdId());
        List<SysOrgElement> posts = person.getFdPosts();
        if (CollectionUtils.isNotEmpty(posts)) {
            for (SysOrgElement post : posts) {
                orgIds.add(post.getFdId());
            }
        }

        // 管理员（管理员可维护此组织类型，也可以维护管理其组织和人员）
        String sql = "select elem.fd_id from sys_org_element_admins adm left join sys_org_element elem on elem.fd_id = adm.fd_element_id where elem.fd_org_type = 1 and adm.fd_admin_id in (:orgIds) and elem.fd_is_external = :external";
        addAuthAdmin(adminRanges, sql, orgIds, 1);

        // 组织管理员（组织管理员可以在此组织类型下新建和管理自己创建的组织/人员）
        sql = "select elem.fd_id from sys_org_element_external ext left join sys_org_element elem on elem.fd_id = ext.fd_element_id left join sys_org_element_ext_readers reader on reader.fd_external_id = ext.fd_id where reader.fd_reader_id in (:orgIds) and elem.fd_is_external = :external";
        addAuthAdmin(adminRanges, sql, orgIds, 2);

        // 负责人（负责人可以维护管理该组织及子组织/岗位/人员）
        sql = "select elem.fd_id from sys_org_element_admins adm left join sys_org_element elem on elem.fd_id = adm.fd_element_id where elem.fd_org_type = 2 and adm.fd_admin_id in (:orgIds) and elem.fd_is_external = :external";
        addAuthAdmin(adminRanges, sql, orgIds, 3);

        // 过滤子组织
        Set<String> hids = new HashSet<>();
        Map<String, SysOrgShowRange> map = new HashMap<>();
        for (SysOrgShowRange range : adminRanges) {
            hids.add(range.getFdHierarchyId());
            map.put(range.getFdHierarchyId(), range);
        }
        hids = filterSubHierarchy(hids);
        adminRanges.clear();
        for (String hid : hids) {
            adminRanges.add(map.get(hid));
        }
        return adminRanges;
    }

    /**
     * 查询生态组织管理员
     *
     * @param adminRanges
     * @param sql
     * @param personIds
     * @param adminType
     * @throws Exception
     */
    private void addAuthAdmin(Set<SysOrgShowRange> adminRanges, String sql, List<String> personIds, int adminType) throws Exception {
        // 管理员（管理员可维护此组织类型，也可以维护管理其组织和人员）
        List<Object> list = sysOrgElementService.getBaseDao().getHibernateSession().createNativeQuery(sql)
                .setParameter("orgIds", personIds).setParameter("external", Boolean.TRUE).list();
        if (CollectionUtils.isNotEmpty(list)) {
            String[] ids = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                ids[i] = list.get(i).toString();
            }
            List<SysOrgElement> elems = sysOrgElementService.findByPrimaryKeys(ids);
            if (CollectionUtils.isNotEmpty(elems)) {
                for (SysOrgElement elem : elems) {
                    SysOrgShowRange range = convertRange(elem, false, adminType);
                    if (!adminRanges.contains(range)) {
                        adminRanges.add(range);
                    }
                }
            }
        }
    }

    /**
     * 组织与查看范围映射
     *
     * @author panyh
     * @date Jul 10, 2020
     */
    static class SysOrgRangeMapping {
        private SysOrgElement element;
        private SysOrgElement post;
        private SysOrgElementRange range;

        public SysOrgRangeMapping(SysOrgElement element, SysOrgElementRange range) {
            this.element = element;
            this.range = range;
        }

        public SysOrgRangeMapping(SysOrgElement element, SysOrgElement post, SysOrgElementRange range) {
            this(element, range);
            this.post = post;
        }

        public SysOrgElement getElement() {
            return element;
        }

        public SysOrgElementRange getRange() {
            return range;
        }

        /**
         * 构建映射
         *
         * @param element
         * @param range
         * @return
         */
        public static SysOrgRangeMapping build(SysOrgElement element, SysOrgElementRange range) {
            return new SysOrgRangeMapping(element, range);
        }

        /**
         * 构建映射
         *
         * @param element
         * @param range
         * @return
         */
        public static SysOrgRangeMapping build(SysOrgElement element, SysOrgElement post, SysOrgElementRange range) {
            return new SysOrgRangeMapping(element, post, range);
        }

    }

}
