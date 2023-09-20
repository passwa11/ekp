/**
 *
 */
package com.landray.kmss.sys.zone.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.authentication.identity.util.SysAuthenUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.ftsearch.config.LksField;
import com.landray.kmss.sys.ftsearch.db.service.SearchBuilder;
import com.landray.kmss.sys.ftsearch.search.LksHit;
import com.landray.kmss.sys.ftsearch.search.facet.FacetSearchResult;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.eco.AuthOrgRange;
import com.landray.kmss.sys.organization.eco.SysOrgBaseRange;
import com.landray.kmss.sys.organization.eco.SysOrgMyDeptRange;
import com.landray.kmss.sys.organization.eco.SysOrgShowRange;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.profile.model.PasswordSecurityConfig;
import com.landray.kmss.sys.readlog.service.ISysReadLogService;
import com.landray.kmss.sys.tag.model.SysTagMain;
import com.landray.kmss.sys.tag.model.SysTagMainRelation;
import com.landray.kmss.sys.tag.service.ISysTagMainService;
import com.landray.kmss.sys.tag.service.ISysTagTagsService;
import com.landray.kmss.sys.zone.constant.SysZoneConstant;
import com.landray.kmss.sys.zone.forms.SysZonePersonInfoForm;
import com.landray.kmss.sys.zone.model.*;
import com.landray.kmss.sys.zone.service.ISysZoneNavigationService;
import com.landray.kmss.sys.zone.service.ISysZonePersonDataCateService;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.sys.zone.util.SysZonePrivateUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.filter.security.ValidatorRule;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 傅游翔
 *
 */
public class SysZonePersonInfoServiceImp extends BaseServiceImp implements ISysZonePersonInfoService, IXMLDataBean {

    private static Logger log = org.slf4j.LoggerFactory.getLogger(SysZonePersonInfoServiceImp.class);

    private ISysOrgPersonService sysOrgPersonService;
    private ISysTagTagsService sysTagTagsService;
    private ISysTagMainService sysTagMainService;
    private ISysZoneNavigationService sysZoneNavigationService;
    private ICoreOuterService sysTagMainCoreService;
    protected ISysAttMainCoreInnerService sysAttMainService;
    private ISysOrgCoreService sysOrgCoreService;
    private ISysReadLogService sysReadLogService;

    private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

    public void setSysOrganizationStaffingLevelService(
            ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
        this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
    }

    public void setSysAttMainService(ISysAttMainCoreInnerService sysAttMainService) {
        this.sysAttMainService = sysAttMainService;
    }

    public void setSysTagMainCoreService(ICoreOuterService sysTagMainCoreService) {
        this.sysTagMainCoreService = sysTagMainCoreService;
    }

    public void setSysTagMainService(ISysTagMainService sysTagMainService) {
        this.sysTagMainService = sysTagMainService;
    }

    public void setSysZoneNavigationService(ISysZoneNavigationService sysZoneNavigationService) {
        this.sysZoneNavigationService = sysZoneNavigationService;
    }

    public void setSysTagTagsService(ISysTagTagsService sysTagTagsService) {
        this.sysTagTagsService = sysTagTagsService;
    }

    public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
        this.sysOrgPersonService = sysOrgPersonService;
    }

    protected ISysZonePersonDataCateService sysZonePersonDataCateService;

    public void setSysZonePersonDataCateService(ISysZonePersonDataCateService sysZonePersonDataCateService) {
        this.sysZonePersonDataCateService = sysZonePersonDataCateService;
    }

    public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
        this.sysOrgCoreService = sysOrgCoreService;
    }

    public void setSysReadLogService(ISysReadLogService sysReadLogService) {
        this.sysReadLogService = sysReadLogService;
    }

    /**
     * 员工黄页：搜索查询
     */
    @Override
    public Page queryPersonInfo(int pageno, int rowsize, String searchValue, String tagNames, RequestContext request)
            throws Exception {

        if (StringUtil.isNotNull(searchValue)) {
            searchValue = searchValue.trim();
            searchValue = searchValue.replaceAll("\\s+", "");
        }

        Page page = new Page();
        page.setRowsize(rowsize);
        page.setPageno(pageno);
        Long total = (Long) buildQueryPerson(true, searchValue, tagNames, request).uniqueResult();
        page.setTotalrows(total.intValue());
        page.excecute();
        Query query = buildQueryPerson(false, searchValue, tagNames, request);
        query.setFirstResult(page.getStart());
        query.setMaxResults(page.getRowsize());
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = query.list();
        setOtherProperties(list);
        page.setList(list);
        return page;
    }

    private void setOtherProperties(List<Map<String, Object>> list) throws Exception {
        for (Map<String, Object> map : list) {
            if (map.containsKey("fdNameLang")) {
                String value = (String) map.get("fdNameLang");
                if (StringUtil.isNotNull(value)) {
                    map.put("fdName", value);
                }
            }
            if (null == map.get("fdAttentionNum")) {
                map.put("fdAttentionNum", 0);
            }
            if (null == map.get("fdFansNum")) {
                map.put("fdFansNum", 0);
            }
            String fdId = (String) map.get("fdId");
            // 隐私设置过滤
            if (SysZonePrivateUtil.isContactPrivate(fdId)) {
                map.remove("fdMobileNo");
                map.remove("fdEmail");
            }

            map.put("isSelf", isSelfNoPower(fdId));
            // map.put("hasAtten", isAttenFan(fdId));
            // 单独查询每个人的标签与部门
            map.put("fdTags", getTagNamesByPersonId(fdId));
            String deptId = (String) map.get("parentId");
            if (StringUtil.isNull(deptId)) {
                continue;
            }
            SysOrgElement dept = (SysOrgElement) this.findByPrimaryKey(deptId, SysOrgElement.class, true);
            if (null != dept) {
                map.put("fdDept", dept.getFdName());
            }
        }
    }

    @Override
    public String getTagNamesByPersonId(String id) throws Exception {
        StringBuffer hql = new StringBuffer();
        hql.append("select sysrelation_.fdTagName from ");
        hql.append(SysTagMain.class.getName()).append(" systagmain_ ").append("left join ");
        hql.append(" systagmain_.sysTagMainRelationList sysrelation_ ");
        hql.append("where systagmain_.fdModelId = :modelId ");
        hql.append("and systagmain_.fdModelName = '");
        hql.append(SysZonePersonInfo.class.getName()).append("'")
                .append(" and sysrelation_.docIsDelete = :docIsDelete");
        @SuppressWarnings("unchecked")
        List<String> tagNameList = (List<String>) this.getBaseDao().getHibernateSession().createQuery(hql.toString())
                .setParameter("modelId", id)
                .setParameter("docIsDelete", false)
                .list();
        StringBuffer tagNames = new StringBuffer();
        for (String tagName : tagNameList) {
            tagNames.append(" ").append(tagName);
        }
        if (tagNames.length() > 0) {
            tagNames.deleteCharAt(0);
        }
        return tagNames.toString();
    }

    private Query buildQueryPerson(boolean getTotal, String searchValue, String tagNames, RequestContext request)
            throws Exception {
        StringBuffer hql = new StringBuffer();
        if (getTotal) {
            // 查询总数
            hql.append("select count(personinfo_.fdId) from ");
        } else {
            String select_lang = "";
            if (SysLangUtil.isLangEnabled()) {
                String fdNameLang = SysLangUtil.getLangFieldName(SysOrgPerson.class.getName(), "fdName");
                if (StringUtil.isNotNull(fdNameLang)) {
                    select_lang = ", person_." + fdNameLang + " as fdNameLang";
                }
            }
            // 查询各个结果列，组成map
            hql.append(
                    "select new map(personinfo_.fdId as fdId, personinfo_.fdSignature as fdSignature, personinfo_.fdAttentionNum as fdAttentionNum, ");
            hql.append(
                    "personinfo_.fdFansNum as fdFansNum, person_.fdName as fdName, person_.fdLoginName as fdLoginName, person_.fdMobileNo as fdMobileNo, ");
            hql.append("person_.fdEmail as fdEmail, person_.fdSex as fdSex, person_.hbmParent.fdId as parentId"
                    + select_lang + ") from ");
        }
        // 查询条件 组织架构人员的条件
        hql.append(SysZonePersonInfo.class.getName()).append(" personinfo_ ");
        hql.append("left join personinfo_.person person_ ");
        // 职级过滤使用
        Boolean isStaffingLevel = sysOrganizationStaffingLevelService.isStaffingLevelFilter();
        if (isStaffingLevel) {
            hql.append("left join personinfo_.person.fdStaffingLevel staffingLevel ");
        }

        hql.append(
                " where person_.fdIsAvailable=:fdIsAvailable and person_.fdOrgType=:personType and person_.fdIsBusiness=:fdIsBusiness  ");
        if (StringUtil.isNotNull(searchValue)) {
            hql.append("and (person_.fdName like:searchValue or person_.fdMobileNo like:searchValue ");
            // 拼音或简拼
            hql.append("or person_.fdNamePinYin like:searchValue or person_.fdNameSimplePinyin like:searchValue ");
            hql.append("or person_.fdEmail like:searchValue or person_.fdLoginName like:searchValue) ");
        }

        // 查看范围
        hql.append(getRange());

        // 职级过滤使用
        int level = 0;
        if (isStaffingLevel) {
            level = sysOrganizationStaffingLevelService.buildStaffingLevelWhereBlock(hql);
        }

        // 子查询 fdId 必须在标签机制的结果fdModelId内
        HashMap<String, String> params = null;
        if (StringUtil.isNotNull(tagNames)) {
            String[] tags = tagNames.split("[\\s+;,]");
            params = new HashMap<String, String>();
            String tagModelName = SysTagMain.class.getName();
            for (int i = 0; i < tags.length; i++) {
                StringBuffer tagHql = new StringBuffer();
                tagHql.append("select distinct systagmain_.fdModelId from ");
                tagHql.append(tagModelName).append(" systagmain_ ").append(" inner join ");
                tagHql.append(" systagmain_.sysTagMainRelationList sysrelation_ ");
                tagHql.append("where sysrelation_.fdTagName =:tagNames").append(i);
                tagHql.append(" and systagmain_.fdModelName = '");
                tagHql.append(SysZonePersonInfo.class.getName()).append("'");
                tagHql.append(" and sysrelation_.docIsDelete = ").append(HibernateUtil.toBooleanValueString(false));
                params.put("tagNames" + i, tags[i]);
                hql.append(" and  personinfo_.fdId in (" + tagHql + ")");
            }
        }
        if (!getTotal) {
            String _orderby = "";
            String orderby = request.getParameter("orderby");
            String ordertype = request.getParameter("ordertype");
            if (!ValidatorRule.validationSQL(orderby, "orderby")) {
                throw new RuntimeException("SQL参数orderby中包括非法值");
            }
            /* 保持跟后台组织架构人员排序一致 #54342 */
            String currentLocaleCountry = null;
            if (SysLangUtil.isLangEnabled()) {
                currentLocaleCountry = SysLangUtil.getCurrentLocaleCountry();
                if (StringUtil.isNotNull(currentLocaleCountry)
                        && currentLocaleCountry.equals(SysLangUtil.getOfficialLang())) {
                    currentLocaleCountry = null;
                }
            }
            if (StringUtil.isNull(orderby)) {
                _orderby = "person_.fdOrder,person_.fdName";
                if (StringUtil.isNotNull(currentLocaleCountry)) {
                    _orderby += currentLocaleCountry;
                }
            } else {
                _orderby = "person_." + orderby;
                if (StringUtil.isNotNull(currentLocaleCountry) && "fdName".equals(orderby)) {
                    _orderby += currentLocaleCountry;
                }
                if (StringUtil.isNotNull(ordertype) && "down".equalsIgnoreCase(ordertype)) {
                    _orderby = _orderby + " desc";
                }
                // BaseDaoImp.findPage中强制加上了fdId排序，这里保持一致
                _orderby += ",person_.fdId desc";
            }
            hql.append(" order by " + _orderby);
        }
        Query query = this.getBaseDao().getHibernateSession().createQuery(hql.toString());
        query.setParameter("fdIsAvailable", Boolean.TRUE).setParameter("fdIsBusiness", Boolean.TRUE)
                .setParameter("personType", SysOrgConstant.ORG_TYPE_PERSON);
        if (StringUtil.isNotNull(searchValue)) {
            query.setParameter("searchValue", "%" + searchValue + "%");
        }
        if (StringUtil.isNotNull(tagNames)) {
            query.setProperties(params);
        }
        // 职级过滤使用
        if (isStaffingLevel) {
            query.setInteger("level", level);
        }
        return query;
    }

    private String getRange() {
        // 组织查看/隐藏过滤
        if (UserUtil.getKMSSUser().isAdmin() || UserUtil.checkRole("ROLE_SYSORG_DIALOG_USER")) {
            return "";
        } else {
            String whereBlock = "";
            AuthOrgRange orgRange = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgRange();
            if (orgRange != null) {
                // 仅自己
                if (orgRange.isSelf()) {
                    return " and person_.fdId = '" + UserUtil.getKMSSUser().getUserId() + "'";
                } else {
                    Set<SysOrgShowRange> authRanges = orgRange.getAuthRanges();
                    StringBuffer authBlock = new StringBuffer();
                    Set<String> authHids = new HashSet<>();
                    if (CollectionUtils.isNotEmpty(authRanges)) {
                        for (SysOrgShowRange range : authRanges) {
                            if (range.isExternal()) {
                                // 忽略生态条件
                                continue;
                            }
                            if (authBlock.length() > 0) {
                                authBlock.append(" or ");
                            }
                            authHids.add(range.getFdHierarchyId());
                            authBlock.append("person_.fdHierarchyId like '").append(range.getFdHierarchyId()).append("%'");
                        }
                    }
                    if (authBlock.length() > 0) {
                        authBlock.insert(0, "(").append(")");
                    }
                    // 隐藏组织
                    Set<SysOrgBaseRange> hideRanges = orgRange.getHideRanges();
                    StringBuffer hideBlock = new StringBuffer();
                    Set<SysOrgMyDeptRange> myDepts = orgRange.getMyDepts();
                    if (CollectionUtils.isNotEmpty(hideRanges)) {
                        for (SysOrgBaseRange range : hideRanges) {
                            if (range.isExternal()) {
                                // 忽略生态条件
                                continue;
                            }
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
                                        myDeptBlock.append(" or person_.fdHierarchyId like '").append(deptRange.getFdHierarchyId()).append("%'");
                                    }
                                }
                            }
                            if (myDeptBlock.length() > 0) {
                                hideBlock.append("(");
                            }
                            hideBlock.append("person_.fdHierarchyId not like '").append(range.getFdHierarchyId()).append("%'");
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
                                    subHide.append(" and ").append("person_.fdHierarchyId not like '").append(sub).append("%'");
                                }
                            }
                            for (SysOrgMyDeptRange range : myDepts) {
                                if (range.isExternal()) {
                                    // 忽略生态条件
                                    continue;
                                }
                                if (deptBlock.length() > 0) {
                                    deptBlock.append(" or ");
                                }
                                if (subHide.length() > 0) {
                                    deptBlock.append("(");
                                }
                                deptBlock.append("person_.fdHierarchyId like '").append(range.getFdHierarchyId()).append("%'");
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
                    whereBlock = StringUtil.linkString(authBlock.toString(), " and ", hideBlock.toString());
                    if (!orgRange.isSelf()) {
                        whereBlock = StringUtil.linkString(whereBlock, " or ", deptBlock.toString());
                    }
                }
            }
            if (whereBlock.length() > 0) {
                whereBlock = " and " + whereBlock;
            }
            return whereBlock;
        }
    }

    /**
     * 处理排序号为空时在mysql和sqlserver里面的策略和oracle不一样的问题
     *
     * @param oriStr 原有片段
     * @param proName 排序字段名
     * @param down 是否倒序
     * @return
     */
    @Override
    public String nullLastHQL(String oriStr, String proName, Boolean down) {
        String rtn = oriStr;
        String dialect = ResourceUtil.getKmssConfigString("hibernate.dialect").toLowerCase();
        // 空值都默认为是大值
        if (dialect.contains("mysql") || dialect.contains("sqlserver")) {
            if (down) {
                rtn = " case when " + proName + " is null then 0 else 1 end, " + oriStr;
            } else {
                rtn = " case when " + proName + " is null then 1 else 0 end, " + oriStr;
            }

        }
        return rtn;
    }

    /* 个人黄页：是不是本人 */
    @Override
    public boolean isSelf(String zonePersonId) throws Exception {
        if (UserUtil.checkRole("ROLE_SYSZONE_ADMIN")) {
            return true;
        }
        if (zonePersonId.equals(UserUtil.getUser().getFdId())) {
            return true;
        } else {
            return false;
        }
    }

    /* 得到黄页搜索标签 */
    @Override
    public List getTagList() throws Exception {
        HQLInfo hql = new HQLInfo();
        hql.setSelectBlock(
                "new map(fdId as fdId,fdName as fdName,fdCategory.fdId as categoryId,fdCategory.fdName as categoryName)");
        hql.setWhereBlock("sysTagTags.fdIsPrivate=:fdIsPrivate and sysTagTags.fdStatus=:fdStatus");
        hql.setParameter("fdIsPrivate", 2);
        hql.setParameter("fdStatus", 1);
        List<Map<String, String>> list = sysTagTagsService.findList(hql);
        List<String> categroyList = categroyList(list);// 得到标签分类
        List<Map<String, Object>> tags = putTagsInCategoryList(categroyList, list);// 根据标签分类，封装标签
        return tags;
    }

    /* 把每个分类下的标签封装到该分类下 */
    public List<Map<String, Object>> putTagsInCategoryList(List<String> categroyList,
                                                           List<Map<String, String>> tagAllList) {
        List<Map<String, Object>> categorylist = new ArrayList<Map<String, Object>>();
        for (String category : categroyList) {
            List<Map<String, String>> tagList = new ArrayList<Map<String, String>>();
            Map<String, Object> tagMap = new HashMap<String, Object>();
            String categoryName = null;
            for (int i = 0, j = tagAllList.size(); i < j; i++) {
                Map<String, String> obj = (Map<String, String>) tagAllList.get(i);
                String categoryId = (String) obj.get("categoryId");
                if (category.equals(categoryId)) {
                    categoryName = (String) obj.get("categoryName");
                    tagList.add(obj);
                }
            }
            tagMap.put("categoryName", categoryName);
            tagMap.put("tags", tagList);
            categorylist.add(tagMap);
        }
        return categorylist;
    }

    /* 根据标签得到所有标签分类 */
    public List<String> categroyList(List<Map<String, String>> tagList) {
        List<String> categroyList = new ArrayList<String>();
        for (int i = 0, j = tagList.size(); i < j; i++) {
            Map<String, String> obj = (Map<String, String>) tagList.get(i);
            String categoryId = (String) obj.get("categoryId");
            if (!categroyList.contains(categoryId)) {
                categroyList.add(categoryId);
            }
        }
        return categroyList;
    }

    @Override
    public Page getNewPersonInfo(HttpServletRequest request) throws Exception {
        String spageno = request.getParameter("pageno");
        String srowsize = request.getParameter("rowsize");
        int pageno = 0;
        int rowsize = SysConfigParameters.getRowSize();
        if (spageno != null && spageno.length() > 0) {
            pageno = Integer.parseInt(spageno);
        }
        if (srowsize != null && srowsize.length() > 0) {
            rowsize = Integer.parseInt(srowsize);
        }
        String orderby = "sysOrgPerson.fdCreateTime desc";
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setOrderBy(orderby);
        hqlInfo.setPageNo(pageno);
        hqlInfo.setRowSize(rowsize);
        hqlInfo.setWhereBlock(
                " sysOrgPerson.fdOrgType=:_person and  sysOrgPerson.fdIsAvailable=:_available and sysOrgPerson.fdIsBusiness=:_isBusiness");
        hqlInfo.setParameter("_person", SysOrgConstant.ORG_TYPE_PERSON);
        hqlInfo.setParameter("_isBusiness", Boolean.TRUE);
        hqlInfo.setParameter("_available", Boolean.TRUE);

        // 职级可见性过滤
        sysOrganizationStaffingLevelService.getPersonStaffingLevelFilterHQLInfo(hqlInfo);
        Page page = sysOrgPersonService.findPage(hqlInfo);

        List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < page.getList().size(); i++) {
            SysOrgPerson info = (SysOrgPerson) page.getList().get(i);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("fdId", info.getFdId());
            map.put("fdName", info.getFdName());
            if (info.getFdParent() != null) {
                map.put("fdDept", info.getFdParent().getFdName());
            }
            newList.add(map);
        }
        page.setList(newList);
        return page;
    }

    @Override
    public JSONObject updateOrgLang(HttpServletRequest reuest) throws Exception {
        String personId = UserUtil.getUser().getFdId();
        String fdDefaultLang = reuest.getParameter("fdDefaultLang");
        SysOrgPerson person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(personId);
        person.setFdDefaultLang(fdDefaultLang);
        this.sysOrgPersonService.update(person);
        JSONObject json = new JSONObject();
        json.put("langText", SysZonePersonInfoForm.getLangDisplayName(reuest, fdDefaultLang));
        return json;
    }

    @Override
    public void updateOrgInfo(SysOrgPerson sysOrgPerson) throws Exception {
        this.sysOrgPersonService.checkMobileNo(sysOrgPerson);
        this.sysOrgPersonService.update(sysOrgPerson);
        SysZonePersonInfo info = updateGetPerson(sysOrgPerson.getFdId());
        info.setFdLastModifiedTime(new Date());
        this.update(info);
    }

    @Override
    public void updatePersonInfo(RequestContext request) throws Exception {

        String mobileNo = request.getParameter("mobilPhone");

        if (StringUtil.isNotNull(mobileNo)) {
            if (!isMobileNO(mobileNo)) {
                throw new Exception(ResourceUtil.getString("sysZonePersonInfo.rightTel", "sys-zone"));
            }
        }

        String fdEmail = request.getParameter("email");

        if (StringUtil.isNotNull(fdEmail)) {
            if (!isEmail(fdEmail)) {
                throw new Exception(ResourceUtil.getString("sysZonePersonInfo.rightEmail", "sys-zone"));
            }
        }

        String fdShortNo = request.getParameter("fdShortNo");
        String workphone = request.getParameter("fdCompanyPhone");
        String fdSex = request.getParameter("fdSex");
        String fdDefaultLang = request.getParameter("fdDefaultLang");

        String isContactPrivate = request.getParameter("isContactPrivate");
        String isDepInfoPrivate = request.getParameter("isDepInfoPrivate");

        // String personId = request.getParameter("fdId");
        // modify @linxiuxian 2016.6.28
        String personId = UserUtil.getUser().getFdId();

        SysOrgPerson orgPerson = (SysOrgPerson) this.sysOrgPersonService.findByPrimaryKey(personId);
        this.sysOrgPersonService.checkMobileNo(orgPerson);
        orgPerson.setFdSex(fdSex);
        orgPerson.setFdEmail(fdEmail);
        orgPerson.setFdShortNo(fdShortNo);
        orgPerson.setFdWorkPhone(workphone);

        orgPerson.setFdDefaultLang(fdDefaultLang);

        PasswordSecurityConfig config = new PasswordSecurityConfig();
        String isEnable = "false";
        if (!mobileNo.equals(orgPerson.getFdMobileNo())) {
            isEnable = config.getMobileNoUpdateCheckEnable();
        }
        if ("true".equals(isEnable)) {
            if (!StringUtil.isNull(mobileNo)) {
                String validatePass = (String) request.getRequest().getSession().getAttribute("validatePass");
                if (!"true".equals(validatePass)) {
                    log.error("没有先验证密码，无法修改手机号");
                    throw new Exception("没有先验证密码，无法修改手机号");
                } else {
                    request.getRequest().getSession().removeAttribute("validatePass");
                }
                orgPerson.setFdMobileNo(mobileNo);
            }
        } else {
            orgPerson.setFdMobileNo(mobileNo);
        }
        this.sysOrgPersonService.update(orgPerson);

        SysZonePersonInfo info = updateGetPerson(orgPerson.getFdId());
        info.setFdDefaultLang(fdDefaultLang);
        info.setIsContactPrivate("1".equals(isContactPrivate) ? true : false);
        info.setIsDepInfoPrivate("1".equals(isDepInfoPrivate) ? true : false);
        info.setFdLastModifiedTime(new Date());
        this.update(info);
    }

    private boolean isMobileNO(String mobiles) {
        return SysAuthenUtil.isMobileNO(mobiles);
    }

    private boolean isEmail(String email) {
        //与前端邮箱正则保持一致
        ///^([a-z0-9A-Z]+[-|\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z-]+\.)+[a-zA-Z]{2,}$/.test(v)
        Pattern p = Pattern.compile(
                "^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z-]+\\.)+[a-zA-Z]{2,}$");
        Matcher m = p.matcher(email);
        return m.matches();
        //return SysAuthenUtil.isEmail(email);
    }

    @Override
    public void updateFdSignature(String fdId, String fdSignature) throws Exception {
        SysZonePersonInfo zonePerson = updateGetPerson(fdId);
        zonePerson.setFdSignature(fdSignature);
        zonePerson.setFdLastModifiedTime(new Date());
        update(zonePerson);
    }

    /* 他的团队 */
    @Override
    public JSONArray updateOfGetTeam(String personId) throws Exception {

        // 隐私设置判断^([a-z0-9A-Z]+[-|\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z-]+\.)+[a-zA-Z]{2,}$
        if (SysZonePrivateUtil.isWorkmatePrivate(personId)) {
            return new JSONArray();
        }

        SysOrgPerson person = (SysOrgPerson) this.sysOrgPersonService.findByPrimaryKey(personId);
        List<SysOrgElement> teams = null;
        if (person.getFdParent() != null) {
            teams = person.getFdParent().getFdChildren();
        }
        List<SysOrgElement> removeList = new ArrayList<SysOrgElement>();
        if (!ArrayUtil.isEmpty(teams)) {
            for (SysOrgElement personElement : teams) {
                if (personElement.getFdId().equals(person.getFdId()) || personElement.getFdOrgType() != 8) {
                    removeList.add(personElement);
                }
            }
        }
        if (!ArrayUtil.isEmpty(removeList)) {
            teams.removeAll(removeList);
        }
        JSONArray rtnArray = new JSONArray();
        if (!ArrayUtil.isEmpty(teams)) {
            for (SysOrgElement org : teams) {
                JSONObject obj = new JSONObject();
                obj.put("fdId", org.getFdId());
                obj.put("fdName", org.getFdName());
                obj.put("imgUrl", PersonInfoServiceGetter.getPersonHeadimageUrl(org.getFdId(), "m"));
                rtnArray.add(obj);
            }
        }

        return rtnArray;
    }

    /* 他的团队 */
    @Override
    public JSONArray getTeam(String personId) throws Exception {

        // 隐私设置判断
        if (SysZonePrivateUtil.isWorkmatePrivate(personId)) {
            return new JSONArray();
        }

        SysOrgPerson person = (SysOrgPerson) this.sysOrgPersonService.findByPrimaryKey(personId);
        List<SysOrgElement> teams = null;
        if (person.getFdParent() != null) {
            teams = person.getFdParent().getFdChildren();
        }
        List<SysOrgElement> removeList = new ArrayList<SysOrgElement>();
        if (!ArrayUtil.isEmpty(teams)) {
            for (SysOrgElement personElement : teams) {
                if (personElement.getFdId().equals(person.getFdId()) || personElement.getFdOrgType() != 8) {
                    removeList.add(personElement);
                }
            }
        }
        if (!ArrayUtil.isEmpty(removeList)) {
            teams.removeAll(removeList);
        }
        JSONArray rtnArray = new JSONArray();
        if (!ArrayUtil.isEmpty(teams)) {
            for (SysOrgElement org : teams) {
                JSONObject obj = new JSONObject();
                obj.put("fdId", org.getFdId());
                obj.put("fdName", org.getFdName());
                obj.put("imgUrl", PersonInfoServiceGetter.getPersonHeadimageUrl(org.getFdId(), "m"));
                rtnArray.add(obj);
            }
        }

        return rtnArray;
    }

    @Override
    public JSONArray updateOfGetTeam(String personId, HttpServletRequest request) throws Exception {
        // 隐私设置判断
        if (SysZonePrivateUtil.isWorkmatePrivate(personId)) {
            return new JSONArray();
        }

        SysOrgPerson person = (SysOrgPerson) this.sysOrgPersonService.findByPrimaryKey(personId);
        List<SysOrgElement> teams = null;
        String deptId = null;
        if (person.getFdParent() != null) {
            deptId = person.getFdParent().getFdId();
        }
        if (StringUtil.isNotNull(deptId)) {
            String srowsize = request.getParameter("rowsize");
            int rowsize = SysConfigParameters.getRowSize();
            if (StringUtil.isNotNull(srowsize)) {
                rowsize = Integer.parseInt(srowsize);
            }

            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setRowSize(rowsize);
            hqlInfo.setWhereBlock(
                    "sysOrgPerson.fdIsAvailable="+HibernateUtil.toBooleanValueString(true)+" and sysOrgPerson.fdOrgType=8 and sysOrgPerson.fdId !=:fdId and sysOrgPerson.hbmParent.fdId =:fdParentId");
            hqlInfo.setParameter("fdId", personId);
            hqlInfo.setParameter("fdParentId", deptId);
            hqlInfo.setOrderBy("sysOrgPerson.fdOrder asc");
            Page page = sysOrgPersonService.findPage(hqlInfo);
            teams = page.getList();
            request.setAttribute("totalSize", page.getTotalrows());
        }
        JSONArray rtnArray = new JSONArray();
        if (!ArrayUtil.isEmpty(teams)) {
            for (SysOrgElement org : teams) {
                JSONObject obj = new JSONObject();
                obj.put("fdId", org.getFdId());
                obj.put("fdName", org.getFdName());
                String postName = ArrayUtil.joinProperty(org.getFdPosts(), "fdName", ";")[0];
                obj.put("postName", postName);
                obj.put("imgUrl", PersonInfoServiceGetter.getPersonHeadimageUrl(org.getFdId(), "m"));
                rtnArray.add(obj);
            }
        }

        return rtnArray;
    }

    @Override
    public JSONArray getTeam(String personId, HttpServletRequest request) throws Exception {
        // 隐私设置判断
        if (SysZonePrivateUtil.isWorkmatePrivate(personId)) {
            return new JSONArray();
        }

        SysOrgPerson person = (SysOrgPerson) this.sysOrgPersonService.findByPrimaryKey(personId);
        List<SysOrgElement> teams = null;
        String deptId = null;
        if (person.getFdParent() != null) {
            deptId = person.getFdParent().getFdId();
        }
        if (StringUtil.isNotNull(deptId)) {
            String srowsize = request.getParameter("rowsize");
            int rowsize = SysConfigParameters.getRowSize();
            if (StringUtil.isNotNull(srowsize)) {
                rowsize = Integer.parseInt(srowsize);
            }

            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setRowSize(rowsize);
            hqlInfo.setWhereBlock(
                    "sysOrgPerson.fdIsAvailable="+HibernateUtil.toBooleanValueString(true)+" and sysOrgPerson.fdOrgType=8 and sysOrgPerson.fdId !=:fdId and sysOrgPerson.hbmParent.fdId =:fdParentId");
            hqlInfo.setParameter("fdId", personId);
            hqlInfo.setParameter("fdParentId", deptId);
            hqlInfo.setOrderBy("sysOrgPerson.fdOrder asc");
            Page page = sysOrgPersonService.findPage(hqlInfo);
            teams = page.getList();
            request.setAttribute("totalSize", page.getTotalrows());
        }
        JSONArray rtnArray = new JSONArray();
        if (!ArrayUtil.isEmpty(teams)) {
            for (SysOrgElement org : teams) {
                JSONObject obj = new JSONObject();
                obj.put("fdId", org.getFdId());
                obj.put("fdName", org.getFdName());
                String postName = ArrayUtil.joinProperty(org.getFdPosts(), "fdName", ";")[0];
                obj.put("postName", postName);
                obj.put("imgUrl", PersonInfoServiceGetter.getPersonHeadimageUrl(org.getFdId(), "m"));
                rtnArray.add(obj);
            }
        }

        return rtnArray;
    }

    /* 员工机构 */
    @Override
    public String getoOrganization(SysOrgPerson person) throws Exception {
        SysOrgElement fdParent = person.getFdParent();
        String organizationName = "";
        if (fdParent != null) {
            for (SysOrgElement parent = fdParent; parent != null; parent = parent.getFdParent()) {
                if (parent.getFdOrgType() == 1) {
                    organizationName = parent.getFdName();
                    break;
                }
            }
        }
        return organizationName;
    }

    @Override
    public List<SysZoneNavigation> getZonePersonNav(String fdShowType) throws Exception {
        HQLInfo hql = new HQLInfo();
        hql.setOrderBy("sysZoneNavigation.docCreateTime desc");
        hql.setWhereBlock("sysZoneNavigation.fdStatus=2 and sysZoneNavigation.fdShowType =:showType");
        hql.setParameter("showType", fdShowType);
        List<SysZoneNavigation> navs = sysZoneNavigationService.findList(hql);
        return navs;
    }

    @Override
    public String getPersonTagMianId(String personId) throws Exception {
        HQLInfo hql = new HQLInfo();
        hql.setSelectBlock("fdId");
        hql.setWhereBlock("sysTagMain.fdModelId=:personId ");
        hql.setParameter("personId", personId);
        Object obj = sysTagMainService.findFirstOne(hql);
        String id = "";
        if (obj!=null) {
            id = (String) obj;
        }
        return id;
    }

    @Override
    public Page getPersonInfoByTags(Map<String, String> parameters, String personId) throws Exception {
        SysZonePersonInfo person = (SysZonePersonInfo) this.findByPrimaryKey(personId, null, true);
        if (null == person) {
            return null;
        }
        SysZonePersonInfoForm sysZonePersonForm = (SysZonePersonInfoForm) this.convertModelToForm(null, person,
                new RequestContext());
        if (null == sysZonePersonForm) {
            return null;
        }
        String tagNames = sysZonePersonForm.getSysTagMainForm().getFdTagNames();
        if (StringUtil.isNull(tagNames)) {
            return null;
        }
        parameters.put("searchFields", "keyword");
        parameters.put("modelNamex", SysZonePersonInfo.class.getName());
        parameters.put("modelName", SysZonePersonInfo.class.getName());
        parameters.put("filterString", null);
        parameters.put("notLocalFlag", "false");
        parameters.put("queryString", tagNames);
        parameters.put("srcQueryString", tagNames);
        parameters.put("filterFields", null);
        parameters.put("facetString", "");
        parameters.put("sortType", "score");
        parameters.put("facetFields", "modelName");
        parameters.put("exceptKey", "com.landray.kmss.sys.zone.model.SysZonePersonInfo_" + personId);
        SearchBuilder searchBuilder = (SearchBuilder) SpringBeanUtil.getBean("searchBuilder");
        FacetSearchResult fsr = searchBuilder.facetSearch(parameters);
        Page page = fsr.getPage();
        setPersonInfoBySimilarTag(page, personId);
        return page;
    }

    @SuppressWarnings("unchecked")
    private void setPersonInfoBySimilarTag(Page page, String personId) throws Exception {
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        boolean resultContains = false;
        char preChar = '_';
        for (LksHit lksHit : (List<LksHit>) page.getList()) {
            List<LksField> fieldList = lksHit.getLksFields();
            for (LksField lksField : fieldList) {
                if ("docKey".equals(lksField.getName())) {
                    String docInfo = lksField.getValue();
                    String id = docInfo.substring(docInfo.lastIndexOf(preChar) + 1);
                    JSONObject jsonPerson = new JSONObject();
                    jsonPerson.accumulate("fdId", id);
                    SysOrgPerson person = (SysOrgPerson) this.findByPrimaryKey(id, SysOrgPerson.class, true);
                    if (null != person) {
                        jsonPerson.accumulate("fdName", person.getFdName());
                        jsonPerson.accumulate("isLoad", isSelfNoPower(person.getFdId()));// 此人是否登陆人
                        jsonPerson.accumulate("personTags", getTagsByPersonId(person.getFdId()));// 此人的标签
                    }
                    jsonList.add(jsonPerson);
                    break;
                }
            }
        }
        if (resultContains) {
            page.setTotalrows(page.getTotalrows() - 1);
            page.excecute();
        }
        page.setList(jsonList);
    }

    /* 根据personId得到这个人的个人标签 */
    public String getTagsByPersonId(String personId) throws Exception {
        SysZonePersonInfo person = (SysZonePersonInfo) findByPrimaryKey(personId);
        SysZonePersonInfoForm sysZonePersonForm = (SysZonePersonInfoForm) this.convertModelToForm(null, person,
                new RequestContext());
        String tagsNames = "";
        if (sysZonePersonForm.getSysTagMainForm() != null) {
            tagsNames = sysZonePersonForm.getSysTagMainForm().getFdTagNames();
        }
        return tagsNames;
    }

    @Override
    public void updatePersonTags(IBaseModel model) throws Exception {
        updateGetPerson(model.getFdId());
        // update(model);
        sysTagMainCoreService.update(model);
    }

    /* 是不是本人 不带权限 */
    @Override
    public boolean isSelfNoPower(String zonePersonId) throws Exception {
        if (zonePersonId.equals(UserUtil.getUser().getFdId())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Map<String, Object> getPersonDatas(String personId) throws Exception {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        // 是否填过个人资料
        Boolean isSelfData = Boolean.FALSE;

        // 个人资料目录，用于方法返回信息
        List<SysZonePersonData> rtnPersonDatas = new ArrayList<SysZonePersonData>();
        SysZonePersonInfo zonePerson = (SysZonePersonInfo) this.findByPrimaryKey(personId, null, true);
        // 个人资料目录
        List<SysZonePersonData> personDatas = null;
        if (null != zonePerson) {
            personDatas = zonePerson.getFdDatas();
        }
        // 目录模板
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock(" sysZonePersonDataCate.docStatus=:docStatus ");
        hqlInfo.setParameter("docStatus", "1");
        @SuppressWarnings("unchecked")
        Object obj = this.sysZonePersonDataCateService.findFirstOne(hqlInfo);
        if (ArrayUtil.isEmpty(personDatas) && obj==null) {
            rtnMap.put("datas", rtnPersonDatas);
            rtnMap.put("isSelfData", isSelfData);
            return rtnMap;
        }
        // 获取目录模版
        SysZonePersonDataCate cate = (SysZonePersonDataCate)obj;
        if(cate==null){
            return rtnMap;
        }
        List<SysZonePerDataTempl> dataTempls = cate.getFdDataCateTempls();
        // 对目录模版排序
        Collections.sort(dataTempls, new Comparator<SysZonePerDataTempl>() {
            @Override
            public int compare(SysZonePerDataTempl dataTempl1, SysZonePerDataTempl dataTempl2) {
                return (dataTempl1.getFdOrder() - dataTempl2.getFdOrder());
            }

        });
        // 首先从个人资料目录取数据
        if (!ArrayUtil.isEmpty(personDatas)) {
            rtnPersonDatas.addAll(personDatas);
            isSelfData = Boolean.TRUE;
        }

        if (ArrayUtil.isEmpty(rtnPersonDatas)) {
            // 个人资料目录中没有数据
            if (ArrayUtil.isEmpty(dataTempls)) {
                rtnMap.put("datas", rtnPersonDatas);
                rtnMap.put("isSelfData", isSelfData);
                return rtnMap;
            }
            for (SysZonePerDataTempl dataTempl : dataTempls) {
                SysZonePersonData personData = new SysZonePersonData();
                personData.setFdName(dataTempl.getFdName());
                personData.setDocContent(dataTempl.getDocContent());
                personData.setFdDataCate(cate);
                personData.setFdOrder(dataTempl.getFdOrder());
                rtnPersonDatas.add(personData);
            }
            rtnMap.put("datas", rtnPersonDatas);
            rtnMap.put("isSelfData", isSelfData);
            return rtnMap;
        }
        // 目录模版中的数据
        List<SysZonePerDataTempl> delDataTempls = new ArrayList<SysZonePerDataTempl>();
        // 个人资料中有数据，目录模板一定有数据。如果双方都有数据，则对于目录名相同的优先取个人资料中的数据（已经保证了个人资料的目录和目录模版数量一致）
        for (SysZonePersonData personData : rtnPersonDatas) {
            for (SysZonePerDataTempl dataTempl : dataTempls) {
                if (personData.getFdName().equals(dataTempl.getFdName())) {
                    delDataTempls.add(dataTempl);
                    break;
                }
            }
        }
        // dataTempls此时处于持久状态，对其操作会发生数据库操作
        List<SysZonePerDataTempl> newDataTempls = new ArrayList<SysZonePerDataTempl>();
        newDataTempls.addAll(dataTempls);
        newDataTempls.removeAll(delDataTempls);
        for (SysZonePerDataTempl dataTempl : newDataTempls) {
            SysZonePersonData personData = new SysZonePersonData();
            personData.setFdName(dataTempl.getFdName());
            personData.setDocContent(dataTempl.getDocContent());
            personData.setFdDataCate(cate);
            personData.setFdOrder(dataTempl.getFdOrder());
            rtnPersonDatas.add(personData);
        }
        // 对rtnPersonDatas排序
        Collections.sort(rtnPersonDatas, new Comparator<SysZonePersonData>() {
            @Override
            public int compare(SysZonePersonData data1, SysZonePersonData data2) {
                return (data1.getFdOrder() - data2.getFdOrder());
            }

        });
        rtnMap.put("datas", rtnPersonDatas);
        rtnMap.put("isSelfData", isSelfData);
        return rtnMap;
    }

    @Override
    public String saveResume(String personId, String fileName, String fileId) throws Exception {
        updateGetPerson(personId);
        // 查找该用户是否已经有简历了
        @SuppressWarnings("unchecked")
        List<SysAttMain> attMains = (List<SysAttMain>) sysAttMainService.findByModelKey(SysZoneConstant.MODEL_NAME,
                personId, SysZoneConstant.RESUME_KEY);
        if (!attMains.isEmpty() && StringUtil.isNull(fileId)) {
            SysAttMain oldAttMain = attMains.get(0);
            // 删除
            sysAttMainService.delete(oldAttMain);
            if (UserOperHelper.allowLogOper("saveResume", getModelName())) {
                UserOperHelper.setModelNameAndModelDesc("com.landray.kmss.sys.attachment.model.SysAttMain");
                UserOperContentHelper.putDelete(oldAttMain.getFdId(), oldAttMain.getFdFileName(), getModelName());
            }
            return null;
        }
        for (SysAttMain sysAttMain : attMains) {
            sysAttMainService.delete(sysAttMain);
            if (UserOperHelper.allowLogOper("saveResume", getModelName())) {
                UserOperHelper.setModelNameAndModelDesc("com.landray.kmss.sys.attachment.model.SysAttMain");
                UserOperContentHelper.putDelete(sysAttMain.getFdId(), sysAttMain.getFdFileName(), getModelName());
            }
        }
        SysAttMain sysAttMain = new SysAttMain();
        sysAttMain.setFdFileId(fileId);
        sysAttMain.setDocCreateTime(new Date());
        sysAttMain.setFdFileName(fileName);
        sysAttMain.setFdKey(SysZoneConstant.RESUME_KEY);
        sysAttMain.setFdModelId(personId);
        sysAttMain.setFdModelName(getModelName());
        sysAttMain.setFdAttType("byte");
        sysAttMain.setFdContentType(FileMimeTypeUtil.getContentType(fileName));
        if (UserOperHelper.allowLogOper("saveResume", getModelName())) {
            UserOperHelper.setModelNameAndModelDesc("com.landray.kmss.sys.attachment.model.SysAttMain");
            UserOperContentHelper.putAdd(sysAttMain.getFdId(), sysAttMain.getFdFileName(), sysAttMain.getFdModelName());
        }
        return this.sysAttMainService.add(sysAttMain);
    }

    /**
     * 获取汇报关系
     *
     * @param personId
     * @return 汇报关系由高到低的排序
     * @throws Exception
     */
    @Override
    public JSONArray updateOfGetLeaders(String personId) throws Exception {
        // 汇报关系隐私判断
        if (SysZonePrivateUtil.isRelationshipPrivate(personId)) {
            return new JSONArray();
        }

        SysOrgPerson person = (SysOrgPerson) this.sysOrgPersonService.findByPrimaryKey(personId, SysOrgPerson.class, true);
        JSONArray rtnArray = new JSONArray();
        // 上一次循环的id，在连续两级是同一个人的时候只显示一个
        String lastId = null;
        if (person != null) {
            List<SysOrgElement> leaders = null;
            try {
                leaders = person.getAllMyLeader();
            }catch (Exception e){
                log.error("获取所有领导错误:", e);
            }
            if(CollectionUtils.isEmpty(leaders)){
                return rtnArray;
            }
            for (SysOrgElement org : leaders) {
                SysOrgElement leader = null;
                String postName = null;
                if (org.getFdOrgType() == SysOrgElement.ORG_TYPE_PERSON) {
                    leader = org;
                    postName = ArrayUtil.joinProperty(leader.getFdPosts(), "fdName", ";")[0];
                } else if (org.getFdOrgType() == SysOrgElement.ORG_TYPE_POST) {
                    List tmp = org.getFdPersons();
                    if (!ArrayUtil.isEmpty(tmp)) {
                        leader = (SysOrgElement) tmp.get(0);
                        postName = org.getFdName();
                    }
                }
                if (leader != null) {
                    if (leader.getFdId().equals(lastId)) {
                        // 连续一样的人就只显示一个
                        continue;
                    }
                    lastId = leader.getFdId();
                    JSONObject json = new JSONObject();
                    json.put("postName", postName);
                    json.put("leaderName", leader.getFdName());
                    json.put("homePagePath",
                            "/sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId=" + leader.getFdId());
                    json.put("homeImgPath", PersonInfoServiceGetter.getPersonHeadimageUrl(leader.getFdId(), "m"));
                    rtnArray.add(json);
                }

            }
            Collections.reverse(rtnArray);
        }
        return rtnArray;
    }

    /**
     * 获取汇报关系
     *
     * @param personId
     * @return 汇报关系由高到低的排序
     * @throws Exception
     */
    @Override
    public JSONArray getLeaders(String personId) throws Exception {
        // 汇报关系隐私判断
        if (SysZonePrivateUtil.isRelationshipPrivate(personId)) {
            return new JSONArray();
        }

        SysOrgPerson person = (SysOrgPerson) this.sysOrgPersonService.findByPrimaryKey(personId);
        JSONArray rtnArray = new JSONArray();
        // 上一次循环的id，在连续两级是同一个人的时候只显示一个
        String lastId = null;
        if (person != null) {
            List<SysOrgElement> leaders = person.getAllMyLeader();
            for (SysOrgElement org : leaders) {
                SysOrgElement leader = null;
                String postName = null;
                if (org.getFdOrgType() == SysOrgElement.ORG_TYPE_PERSON) {
                    leader = org;
                    postName = ArrayUtil.joinProperty(leader.getFdPosts(), "fdName", ";")[0];
                } else if (org.getFdOrgType() == SysOrgElement.ORG_TYPE_POST) {
                    List tmp = org.getFdPersons();
                    if (!ArrayUtil.isEmpty(tmp)) {
                        leader = (SysOrgElement) tmp.get(0);
                        postName = org.getFdName();
                    }
                }
                if (leader != null) {
                    if (leader.getFdId().equals(lastId)) {
                        // 连续一样的人就只显示一个
                        continue;
                    }
                    lastId = leader.getFdId();
                    JSONObject json = new JSONObject();
                    json.put("postName", postName);
                    json.put("leaderName", leader.getFdName());
                    json.put("homePagePath",
                            "/sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId=" + leader.getFdId());
                    json.put("homeImgPath", PersonInfoServiceGetter.getPersonHeadimageUrl(leader.getFdId(), "m"));
                    rtnArray.add(json);
                }

            }
            Collections.reverse(rtnArray);
        }
        return rtnArray;
    }

    @Override
    public SysZonePersonInfo updateGetPerson(String fdId) throws Exception {
        SysZonePersonInfo person = (SysZonePersonInfo) this.findByPrimaryKey(fdId, null, true);
        if (null == person) {
            synchronized (this) {
                if (null == person) {
                    String sql = "insert into sys_zone_person_info (fd_id, fd_attention_nums,"
                            + " fd_fans_nums,fd_last_modified_time) values(:id, :nums, :fans, :time)";
                    NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(sql).addSynchronizedQuerySpace("sys_zone_person_info");
                    query.setParameter("id", fdId);
                    query.setParameter("nums", 0);
                    query.setParameter("fans", 0);
                    query.setParameter("time", new Date());
                    query.executeUpdate();
                    person = (SysZonePersonInfo) this.findByPrimaryKey(fdId,
                            null, true);
                }
            }
        }
        return person;
    }

    /**
     * 访问记录
     *
     * @param request
     * @param personId
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public Page listReadLog(HttpServletRequest request, String personId, Integer type) throws Exception {
        String s_pageno = request.getParameter("pageno");
        String s_rowsize = request.getParameter("rowsize");
        String orderby = request.getParameter("orderby");
        String ordertype = request.getParameter("ordertype");
        boolean isReserve = false;
        if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
            isReserve = true;
        }
        int pageno = 0;
        int rowsize = 32;
        if (s_pageno != null && s_pageno.length() > 0) {
            pageno = Integer.parseInt(s_pageno);
        }
        if (s_rowsize != null && s_rowsize.length() > 0) {
            // rowsize = Integer.parseInt(s_rowsize);
        }
        if (isReserve) {
            orderby += " desc";
        }

        HQLInfo hqlInfo = new HQLInfo();
        if (StringUtil.isNull(orderby)) {
            orderby = "sysReadLog.fdReadTime desc";
        }
        hqlInfo.setOrderBy(orderby);
        hqlInfo.setPageNo(pageno);
        hqlInfo.setRowSize(rowsize);

        if (type == null) {
            return null;
        }
        String whereBlock = "";
        String selectBlock = "";
        // 我看过谁
        if (type.equals(SysZoneConstant.ZONE_VISITOR_ME)) {
            selectBlock = "  sysReadLog.fdModelId ";
            whereBlock = " sysReadLog.fdReader.fdId=:fdId and sysReadLog.fdModelName=:fdModelName ";

        }
        // 谁看过我
        else if (type.equals(SysZoneConstant.ZONE_VISITOR_OTHER)) {
            selectBlock = "  sysReadLog.fdReader.fdId,sysReadLog.fdReader.fdName  ";
            whereBlock = " sysReadLog.fdModelId =:fdId and sysReadLog.fdModelName=:fdModelName ";
        }
        hqlInfo.setWhereBlock(whereBlock);
        hqlInfo.setParameter("fdId", personId);
        hqlInfo.setParameter("fdModelName", "com.landray.kmss.sys.zone.model.SysZonePersonInfo");
        hqlInfo.setSelectBlock(selectBlock);
        Page page = sysReadLogService.findPage(hqlInfo);
        return page;
    }

    @Override
    public Page obtainPersons(HQLInfo hqlInfo, String parentId, String fdSearchName) throws Exception {
        String where = "sysOrgPerson.fdIsAvailable= "+HibernateUtil.toBooleanValueString(true)+" and "
                + "(sysOrgPerson.fdIsAbandon = "+HibernateUtil.toBooleanValueString(false)+" or sysOrgPerson.fdIsAbandon is null) and sysOrgPerson.fdIsBusiness = "+HibernateUtil.toBooleanValueString(true);
        if (StringUtil.isNotNull(parentId)) {
            SysOrgElement element = sysOrgCoreService.findByPrimaryKey(parentId);
            where += " and sysOrgPerson.fdHierarchyId like '" + element.getFdHierarchyId() + "%'";
        }
        if (StringUtil.isNotNull(fdSearchName)) {
            StringBuffer sb = new StringBuffer();
            sb.append(" and (sysOrgPerson.fdName like :searchName")
                    .append(" or sysOrgPerson.fdNamePinYin like :searchName")
                    .append(" or sysOrgPerson.fdLoginName like :searchName")
                    .append(" or sysOrgPerson.fdEmail like :searchName")
                    .append(" or sysOrgPerson.fdMobileNo like :searchName)");
            where += sb.toString();
            hqlInfo.setParameter("searchName", "%" + fdSearchName + "%");
        }
        hqlInfo.setWhereBlock(where);
        return sysOrgPersonService.findPage(hqlInfo);
    }

    @Override
    public JSONArray getTagsByUserId(String userId) throws Exception {
        return getTagsByUserId(userId, null);
    }

    private JSONArray getTagsByUserId(String userId, Integer num) throws Exception {

        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fdModelName=:fdModelName and fdModelId=:fdModelId");
        hqlInfo.setParameter("fdModelId", userId);
        hqlInfo.setParameter("fdModelName", "com.landray.kmss.sys.zone.model.SysZonePersonInfo");
        Object objTag = sysTagMainService.findFirstOne(hqlInfo);
        JSONArray rtn = new JSONArray();
        if (objTag!=null) {
            SysTagMain tagMain = (SysTagMain)objTag;
            List relaList = new ArrayList<>();
            if (num != null && tagMain.getSysTagMainRelationList().size() >= num) {
                for (Object obj : tagMain.getSysTagMainRelationList()) {
                    SysTagMainRelation relation = (SysTagMainRelation) obj;
                    if (relaList.size() < num && Boolean.FALSE.equals(relation.getDocIsDelete())) {
                        relaList.add(obj);
                    } else {
                        break;
                    }
                }
            } else {
                relaList = tagMain.getSysTagMainRelationList();
            }

            if (!ArrayUtil.isEmpty(relaList)) {
                for (Object obj : relaList) {
                    SysTagMainRelation relation = (SysTagMainRelation) obj;
                    if (Boolean.FALSE.equals(relation.getDocIsDelete())) {
                        rtn.add(relation.getFdTagName());
                    }
                }
            }
        }
        return rtn;
    }

    /**
     * 分类搜索：搜索查询
     */
    @Override
    public Page querySearchPersonInfo(int pageno, int rowsize, String searchValue, String tagNames,
                                      RequestContext request) throws Exception {
        Page page = new Page();
        page.setRowsize(rowsize);
        page.setPageno(pageno);
        Long total = (Long) buildQuerySearchPerson(true, searchValue, tagNames, request).uniqueResult();
        page.setTotalrows(total.intValue());
        page.excecute();
        Query query = buildQuerySearchPerson(false, searchValue, tagNames, request);
        query.setFirstResult(page.getStart());
        query.setMaxResults(page.getRowsize());
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = query.list();
        setOtherProperties(list);
        page.setList(list);
        return page;
    }

    private Query buildQuerySearchPerson(boolean getTotal, String searchValue, String tagNames, RequestContext request)
            throws Exception {
        StringBuffer hql = new StringBuffer();
        if (getTotal) {
            // 查询总数
            hql.append("select count(personinfo_.fdId) from ");
        } else {
            // 查询各个结果列，组成map
            hql.append(
                    "select new map(personinfo_.fdId as fdId, personinfo_.fdSignature as fdSignature, personinfo_.fdAttentionNum as fdAttentionNum, ");
            if (SysLangUtil.isLangEnabled()) {
                if (!"CN".equals(SysLangUtil.getCurrentLocaleCountry())) {
                    hql.append("personinfo_.person.fdName" + SysLangUtil.getCurrentLocaleCountry() + " as fdName"
                            + SysLangUtil.getCurrentLocaleCountry() + ", ");
                }
            }
            hql.append(
                    "personinfo_.fdFansNum as fdFansNum, person_.fdName as fdName, person_.fdLoginName as fdLoginName, person_.fdMobileNo as fdMobileNo, ");
            hql.append("person_.fdEmail as fdEmail, person_.fdSex as fdSex, person_.hbmParent.fdId as parentId) from ");
        }
        // 查询条件 组织架构人员的条件
        hql.append(SysZonePersonInfo.class.getName()).append(" personinfo_ ");
        hql.append("left join personinfo_.person person_ ");
        // 职级过滤使用
        Boolean isStaffingLevel = sysOrganizationStaffingLevelService.isStaffingLevelFilter();
        if (isStaffingLevel) {
            hql.append("left join personinfo_.person.fdStaffingLevel staffingLevel ");
        }

        hql.append(
                " where person_.fdIsAvailable=:fdIsAvailable and person_.fdOrgType=:personType and person_.fdIsBusiness=:fdIsBusiness  ");
        if (StringUtil.isNotNull(searchValue)) {
            hql.append("and (lower(person_.fdName) like:searchValue or lower(person_.fdMobileNo) like:searchValue ");
            // 拼音或简拼
            hql.append(
                    "or lower(person_.fdNamePinYin) like:searchValue or lower(person_.fdNameSimplePinyin) like:searchValue ");
            if (SysLangUtil.isLangEnabled()) {
                if (!"CN".equals(SysLangUtil.getCurrentLocaleCountry())) {
                    hql.append(
                            "or lower(person_.fdName" + SysLangUtil.getCurrentLocaleCountry() + ") like:searchValue ");
                }
            }
            hql.append("or lower(person_.fdEmail) like:searchValue or lower(person_.fdLoginName) like:searchValue) ");

        }

        // 职级过滤使用
        int level = 0;
        if (isStaffingLevel) {
            level = sysOrganizationStaffingLevelService.buildStaffingLevelWhereBlock(hql);
        }

        // 可见性过滤
        hql.append(getRange());

        // 子查询 fdId 必须在标签机制的结果fdModelId内
        HashMap<String, String> params = null;
        if (StringUtil.isNotNull(tagNames)) {
            String[] tags = tagNames.split("[\\s+;,]");
            params = new HashMap<String, String>();
            String tagModelName = SysTagMain.class.getName();
            for (int i = 0; i < tags.length; i++) {
                StringBuffer tagHql = new StringBuffer();
                tagHql.append("select distinct systagmain_.fdModelId from ");
                tagHql.append(tagModelName).append(" systagmain_ ").append(" inner join ");
                tagHql.append(" systagmain_.sysTagMainRelationList sysrelation_ ");
                tagHql.append("where sysrelation_.fdTagName =:tagNames").append(i);
                tagHql.append(" and systagmain_.fdModelName = '");
                tagHql.append(SysZonePersonInfo.class.getName()).append("'");
                params.put("tagNames" + i, tags[i]);
                hql.append(" and  personinfo_.fdId in (" + tagHql + ")");
            }
        }
        if (!getTotal) {
            String _orderby = "";
            String orderby = request.getParameter("orderby");
            String ordertype = request.getParameter("ordertype");
            if (!ValidatorRule.validationSQL(orderby, "orderby")) {
                throw new RuntimeException("SQL参数orderby中包括非法值");
            }
            if (StringUtil.isNotNull(orderby) || "fdOrder".equals(orderby) || "fdCreateTime".equals(orderby)
                    || "fdNamePinYin".equals(orderby)) {
                _orderby = "person_." + orderby;
            }
            if (StringUtil.isNotNull(ordertype) && "down".equalsIgnoreCase(ordertype)) {
                _orderby = _orderby + " desc";
            }
            if (StringUtil.isNotNull(_orderby)) {
                if (_orderby.trim().startsWith("person_.fdOrder")) {
                    _orderby = this.nullLastHQL(_orderby, "person_.fdOrder", _orderby.trim().endsWith("desc"));
                    _orderby += " ,person_.fdNamePinYin asc, person_.fdCreateTime desc";
                } else {
                    _orderby += " ,person_.fdOrder asc";
                }
                _orderby += " ,person_.fdId desc";
                hql.append(" order by " + _orderby);
            }
        }
        Query query = this.getBaseDao().getHibernateSession().createQuery(hql.toString());
        query.setParameter("fdIsAvailable", Boolean.TRUE).setParameter("fdIsBusiness", Boolean.TRUE)
                .setParameter("personType", SysOrgConstant.ORG_TYPE_PERSON);
        if (StringUtil.isNotNull(searchValue)) {
            query.setParameter("searchValue", "%" + searchValue.toLowerCase() + "%");
        }
        if (StringUtil.isNotNull(tagNames)) {
            query.setProperties(params);
        }
        // 职级过滤使用
        if (isStaffingLevel) {
            query.setInteger("level", level);
        }
        return query;
    }

    // 黄页信息最后更新时间更新
    @Override
    public void updatePersonLastModifyTime(SysOrgPerson person) {
        SysZonePersonInfo sysZonePersonInfo = null;
        try {
            sysZonePersonInfo = (SysZonePersonInfo) this.findByPrimaryKey(person.getFdId(), null, true);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (sysZonePersonInfo == null) {
            sysZonePersonInfo = new SysZonePersonInfo();
            sysZonePersonInfo.setFdId(person.getFdId());
            sysZonePersonInfo.setPerson(person);
            sysZonePersonInfo.setFdLastModifiedTime(new Date());
            try {
                this.add(sysZonePersonInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            sysZonePersonInfo.setFdLastModifiedTime(new Date());
            try {
                // 这里只更新"最后修改时间"，不需要处理其它业务，直接调用Dao层去保存
                this.getBaseDao().update(sysZonePersonInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List getDataList(RequestContext request) throws Exception {
        List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
        String rowSize = request.getParameter("rowsize");
        if (StringUtil.isNull(rowSize)) {
            rowSize = "6";
        }
        String deptId = request.getParameter("deptId");
        HQLInfo hqlInfo = new HQLInfo();
        String whereBlock = "sysZonePersonInfo.person.fdIsAvailable=:fdIsAvailable and sysZonePersonInfo.person.fdIsBusiness=:fdIsBusiness";
        if (StringUtil.isNotNull(deptId)) {
            whereBlock += " and sysZonePersonInfo.person.fdHierarchyId like :fdHierarchyId ";
            hqlInfo.setParameter("fdHierarchyId", "%" + deptId + "%");
        }
        hqlInfo.setWhereBlock(whereBlock);
        hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
        hqlInfo.setParameter("fdIsBusiness", Boolean.TRUE);
        hqlInfo.setRowSize(Integer.parseInt(rowSize));
        hqlInfo.setPageNo(0);
        List<SysZonePersonInfo> list = findPage(hqlInfo).getList();
        SysOrgPerson person = null;
        Boolean hasKK = hasModule("/third/im/kk");
        for (SysZonePersonInfo usr : list) {
            Map<String, Object> obj = new HashMap<String, Object>();
            person = usr.getPerson();
            obj.put("name", person.getFdName());
            obj.put("usrid", person.getFdId());
            obj.put("imgurl",
                    request.getContextPath() + PersonInfoServiceGetter.getPersonHeadimageUrl(person.getFdId(), "m"));
            obj.put("telinfo", person.getFdMobileNo());
            obj.put("showextend", hasKK);
            obj.put("fdcontentlink", request.getContextPath()
                    + "/sys/zone/index.do?userid=" + person.getFdId());
            obj.put("deptinfo", person.getFdParent() != null ? person.getFdParent().getFdName() : "");
            if (person.getFdPosts().isEmpty()) {
                obj.put("postinfo", "");
            } else {
                obj.put("postinfo", ((SysOrgElement) person.getFdPosts().get(0)).getFdName());
            }
            obj.put("showextend", Boolean.TRUE);
            obj.put("fdextendlink",
                    request.getContextPath()
                            + "/kms/ask/kms_ask_topic/kmsAskTopic.do?method=add&fdPosterType=3&fdPosterTypeListId="
                            + person.getFdId());
            obj.put("taginfo", convertToString(getTagsByUserId(person.getFdId(), 2)));
            rtnList.add(obj);
        }

        return rtnList;
    }

    private String convertToString(JSONArray tags) {
        String rtn = "";
        if (tags != null && tags.size() > 0) {
            for (Object str : tags) {
                rtn += "、" + String.valueOf(str);
            }
        }
        return rtn.length() > 0 ? rtn.substring(1) : rtn;
    }

    private Boolean hasModule(String prefix) {
        return new File(PluginConfigLocationsUtil.getKmssConfigPath() + prefix)
                .exists();
    }

}
