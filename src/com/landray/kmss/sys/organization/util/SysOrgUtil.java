package com.landray.kmss.sys.organization.util;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.util.TripartiteAdminUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.model.SysLogOrganization;
import com.landray.kmss.sys.log.util.UserAgentUtil;
import com.landray.kmss.sys.organization.dao.ISysOrgElementDao;
import com.landray.kmss.sys.organization.forms.SysOrgElementForm;
import com.landray.kmss.sys.organization.forms.SysOrgElementHideRangeForm;
import com.landray.kmss.sys.organization.forms.SysOrgElementRangeForm;
import com.landray.kmss.sys.organization.forms.SysOrgPersonForm;
import com.landray.kmss.sys.organization.model.SysOrgDept;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgElementHideRange;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.model.SysOrgOrg;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrgRole;
import com.landray.kmss.sys.organization.service.ISysOrganizationVisibleService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ClassUtils;
import com.landray.kmss.util.ObjectUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.comparator.ChinesePinyinComparator;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 创建日期 2010-九月-27
 *
 * @author zhuangwl
 */
public class SysOrgUtil implements SysOrgConstant {
    protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgUtil.class);
    public static final int LIMIT_RESULT_SIZE = 500;

    /**
     * 生态组织变更日志
     */
    public static ThreadLocal<String> ecoChangeFields = new ThreadLocal<String>();
    /**
     * 其它操作
     */
    public static ThreadLocal<String> paraMethod = new ThreadLocal<String>();

    /**
     * 比较两个组织架构类里面相同属性的值，并返回有相同属性值有差异的字段名称
     *
     * @param fdOption
     * @return changesField
     * @throws Exception
     */
    public static String compare(
            SysOrgElementForm formOld, SysOrgElementForm formNew)
            throws Exception {

        String changesField = "";

        if (formNew instanceof SysOrgPersonForm) {
            // 只有人员信息才会增加此校验
            changesField = StringUtil.linkString(changesField, " 、",
                    compare(formOld, formNew, SysOrgPersonForm.class
                            .getName()));
        }

        changesField = StringUtil.linkString(changesField, " 、", compare(
                formOld, formNew, SysOrgElementForm.class.getName()));

        // 记录生态组织变更日志
        if (StringUtil.isNotNull(ecoChangeFields.get())) {
            changesField = StringUtil.linkString(changesField, " 、", ecoChangeFields.get());
        }

        if (PropertyUtils.isReadable(formOld, "dynamicMap") && PropertyUtils.isReadable(formNew, "dynamicMap")) {
            Map nameOld = (Map) PropertyUtils.getProperty(formOld, "dynamicMap");
            Map nameNew = (Map) PropertyUtils.getProperty(formNew, "dynamicMap");

            for (Object keyOld : nameOld.keySet()) {
                for (Object keyNew : nameNew.keySet()) {
                    if (keyOld.equals(keyNew)) {
                        if (!(StringUtil.isNull((String) nameOld.get(keyOld)) && StringUtil.isNull((String) nameNew.get(keyNew)))) {
                            if (StringUtil.isNotNull((String) nameOld.get(keyOld)) && StringUtil.isNotNull((String) nameNew.get(keyNew))) {
                                if (!(nameOld.get(keyOld).equals(nameNew.get(keyNew)))) {
                                    String langName = SysLangUtil.getLangDisplayName(keyOld.toString().substring(6));
                                    if(StringUtil.isNotNull(langName)){
                                        changesField = StringUtil.linkString(changesField, " 、", langName + ResourceUtil.getString("sysOrgRoleConf.fdName", "sys-organization"));
                                    }
                                }
                            } else {
                                String langName = SysLangUtil.getLangDisplayName(keyOld.toString().substring(6));
                                changesField = StringUtil.linkString(changesField, " 、", langName + ResourceUtil.getString("sysOrgRoleConf.fdName", "sys-organization"));
                            }
                        }
                    }
                }
            }
        }
        if (StringUtil.isNotNull(changesField)) {
            changesField += "。";
        }
        return changesField;
    }

    /**
     * 比较两个类里面相同属性的值，并返回有相同属性值有差异的字段名称
     *
     * @param objOld
     * @param objNew
     * @param clsName
     * @return changesField
     * @throws Exception
     */
    public static String compare(Object objOld, Object objNew,
                                 String clsName) throws Exception {

        boolean flag = false;
        String changesField = "";
        java.lang.reflect.Field[] fields = null;
        if (objNew instanceof SysOrgPersonForm) {
            fields = ClassUtils.forName(clsName).getDeclaredFields();
        } else {
            java.lang.reflect.Field[] elementFields = ClassUtils.forName(clsName).getDeclaredFields();
            java.lang.reflect.Field[] moreFields = ClassUtils.forName(objOld.getClass().getName()).getDeclaredFields();
            fields = (java.lang.reflect.Field[]) ArrayUtils.addAll(elementFields, moreFields);
        }

        for (int i = 0; i < fields.length; i++) {
            java.lang.reflect.Field fld = fields[i];
            String fieldName = fld.getName();

            // 生态组织人员忽略“双因子验证”
            if (objOld instanceof SysOrgElementForm && "fdDoubleValidation".equals(fieldName)
                    && "true".equals(((SysOrgElementForm) objOld).getFdIsExternal())) {
                continue;
            }
            Object valOld = null;
            Object valNew = null;
            try {
                valOld = BeanUtils.getProperty(objOld, fieldName);
                valNew = BeanUtils.getProperty(objNew, fieldName);
                if ("".equals(valNew)) {
                    valNew = null;
                }
                if ("".equals(valOld)) {
                    valOld = null;
                }
                if ("fdNewPassword".equals(fieldName) && (valOld == valNew)) {
                    flag = true;
                }
                if (flag == true && "fdLastChangePwd".equals(fieldName)) {
                    valNew = valOld;
                }
                if ("fdDoubleValidation".equals(fieldName)) {
                    // 双因子校验，为空和disable都是未开启，不属于修改
                    if (("disable".equals(valNew) || "disable".equals(valOld)) && (valNew == null || valOld == null)) {
                        continue;
                    }
                }
                if("fdRange".equals(fieldName)){
                    SysOrgElementForm oldForm = (SysOrgElementForm) objOld;
                    SysOrgElementForm newForm = (SysOrgElementForm) objNew;
                    SysOrgElementRangeForm oldRange = oldForm.getFdRange();
                    SysOrgElementRangeForm newRange = newForm.getFdRange();
                    if(oldRange.equals(newRange)){
                        continue;
                    }
                }
                if("fdHideRange".equals(fieldName)){
                    SysOrgElementForm oldForm = (SysOrgElementForm) objOld;
                    SysOrgElementForm newForm = (SysOrgElementForm) objNew;
                    SysOrgElementHideRangeForm oldRange = oldForm.getFdHideRange();
                    SysOrgElementHideRangeForm newRange = newForm.getFdHideRange();
                    if(oldRange.equals(newRange)){
                        continue;
                    }
                }
            } catch (Exception e) {
                if (e instanceof NoSuchMethodException) {
                    continue;
                }
            }
            if (!ObjectUtil.equals(valOld, valNew)) {
                /* 取得数据字典 中的messageKey */
                SysDataDict dataDict = SysDataDict.getInstance();
                // 需要获取新模块的数据字典
                String newModelName = ((SysOrgElementForm) objNew).getModelClass().getName();
                if (newModelName.contains("$$")) {
                    newModelName = newModelName.substring(0, newModelName
                            .indexOf("$$"));
                }

                SysDictModel dictModel = dataDict.getModel(newModelName);

                Map<String, SysDictCommonProperty> propertyMap = dictModel
                        .getPropertyMap();
                if ("fdPersonIds".equals(fieldName)) {
                    fieldName = "hbmPersons";
                } else if ("fdParentId".equals(fieldName)) {
                    fieldName = "hbmParent";
                } else if ("fdThisLeaderId".equals(fieldName)) {
                    fieldName = "hbmThisLeader";
                } else if ("fdSuperLeaderId".equals(fieldName)) {
                    fieldName = "hbmSuperLeader";
                } else if ("fdPostIds".equals(fieldName)) {
                    fieldName = "hbmPosts";
                } else if ("fdMemberIds".equals(fieldName)) {
                    fieldName = "hbmMembers";
                } else if ("fdGroupCateId".equals(fieldName)) {
                    fieldName = "hbmGroupCate";
                } else if ("fdStaffingLevelId".equals(fieldName)) {
                    fieldName = "fdStaffingLevel";
                } else if ("authElementAdminIds".equals(fieldName)) {
                    fieldName = "authElementAdmins";
                }
                SysDictCommonProperty commonProperty = propertyMap
                        .get(fieldName);
                if (commonProperty == null) {
                    continue;
                }
                String messageKey = commonProperty.getMessageKey();
                if (messageKey == null) {
                    continue;
                }
                String[] bundleAndKey = messageKey.split(":");
                // 返回有改动的字段名称
                changesField = StringUtil.linkString(changesField, " 、",
                        bundleAndKey.length > 1 ? ResourceUtil.getString(
                                bundleAndKey[1], bundleAndKey[0])
                                : ResourceUtil.getString(bundleAndKey[0]));
            }
        }
        return changesField;
    }

    /**
     * 构建一条日志信息
     *
     * @param requestContext
     * @return log
     * @throws Exception
     */
    public static SysLogOrganization buildSysLog(RequestContext requestContext) {
        SysLogOrganization log = new SysLogOrganization();
        log.setFdCreateTime(new Date());
        KMSSUser user = null;
        if (requestContext.getRequest() != null) {
            log.setFdIp(requestContext.getRemoteAddr());
            log.setFdMethod(requestContext.getMethod());
            log.setFdParaMethod(requestContext.getParameter("method"));
            log.setFdTargetId(requestContext.getParameter("fdId"));
            log.setFdUrl(requestContext.getRequest().getRequestURI() + "?" + requestContext.getRequest().getQueryString());
            user = UserUtil.getKMSSUser(requestContext.getRequest());
            if (user != null) {
                log.setFdOperator(user.getUserName());
                log.setFdOperatorId(user.getUserId());
            }
            log.setFdBrowser(UserAgentUtil.getUserAgent(requestContext.getRequest()).getBrowser().getName());
            log.setFdEquipment(UserAgentUtil.getOperatingSystem(requestContext.getRequest()));
        }
        String outToIn = requestContext.getParameter("outToIn");
        // 外转内操作
        if (StringUtil.isNotNull(outToIn)) {
            log.setFdParaMethod(ResourceUtil.getString("sys-organization:sysOrgElementExternal.outToIn"));
        }
        // 其它操作
        if (StringUtil.isNotNull(paraMethod.get())) {
            log.setFdParaMethod(paraMethod.get());
        }
        if (StringUtil.isNull(log.getFdOperatorId())) {
            SysOrgPerson operator = UserUtil.getUser();
            if (operator == null) {
                log.setFdOperator(ResourceUtil.getString("sysLogOaganization.system", "sys-log"));
                log.setFdOperatorId("");
            } else {
                log.setFdOperator(operator.getFdName());
                log.setFdOperatorId(operator.getFdId());
            }
        }
        return log;
    }

    /**
     * 构建一条操作者为admin的日志信息
     *
     * @param requestContext
     * @return log
     * @throws Exception
     */
    public static SysLogOrganization buildAdminSysLog(RequestContext requestContext) {
        SysLogOrganization log = new SysLogOrganization();
        log.setFdCreateTime(new Date());
        log.setFdIp(requestContext.getRemoteAddr());
        log.setFdMethod(requestContext.getMethod());
        log.setFdParaMethod(ResourceUtil.getString("sys-organization:sysOrgLog.oms.synchronization"));
        log.setFdTargetId(requestContext.getParameter("fdId"));
        log.setFdBrowser("-");
        log.setFdEquipment("-");
        //接口调用日志，写死为admin为操作者
        log.setFdOperator(ResourceUtil.getString("sys-organization:sysOrgElement.authElementAdmins"));
        log.setFdOperatorId("1183b0b84ee4f581bba001c47a78b2d9");
        String outToIn = requestContext.getParameter("outToIn");
        // 外转内操作
        if (StringUtil.isNotNull(outToIn)) {
            log.setFdParaMethod(ResourceUtil.getString("sys-organization:sysOrgElementExternal.outToIn"));
        }
        // 其它操作
        if (StringUtil.isNotNull(paraMethod.get())) {
            log.setFdParaMethod(paraMethod.get());
        }
        return log;
    }

    /**
     * 返回日志里面的详细的信息
     *
     * @param elemOld
     * @param elemNew
     * @return
     * @throws Exception
     */
    public static String getDetails(SysOrgElement elemOld,
                                    SysOrgElement elemNew, SysOrgElementForm formOld,
                                    SysOrgElementForm formNew) {
        StringBuffer sb = new StringBuffer();
        if (elemOld == null) {
            sb.append(ResourceUtil.getString("sysLogOaganization.operate.add",
                    "sys-log"));
            sb.append(SysOrgUtil.getOrgTypeInfo(elemNew));
            sb.append(elemNew.getFdName());
        } else {
            try {
                String updateDetail = compare(formOld, formNew);
                if(StringUtil.isNotNull(updateDetail)){
                    sb.append(ResourceUtil.getString(
                            "sysLogOaganization.operate.modify", "sys-log"));
                    sb.append("'" + elemNew.getFdName() + "'");
                    sb.append(getOrgTypeInfo(elemNew));
                    sb.append(updateDetail);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return sb.toString();
            }
        }
        return sb.toString();
    }

    /**
     * 返回组织架构类别信息
     *
     * @param elem
     * @return
     */
    public static String getOrgTypeInfo(SysOrgElement elem) {
        if (elem instanceof SysOrgDept) {
            return ResourceUtil.getString("sysLogOaganization.info.dept",
                    "sys-log");
        } else if (elem instanceof SysOrgOrg) {
            return ResourceUtil.getString("sysLogOaganization.info.org",
                    "sys-log");
        } else if (elem instanceof SysOrgGroup) {
            return ResourceUtil.getString("sysLogOaganization.info.group",
                    "sys-log");
        } else if (elem instanceof SysOrgPerson) {
            return ResourceUtil.getString("sysLogOaganization.info.person",
                    "sys-log");
        } else if (elem instanceof SysOrgPost) {
            return ResourceUtil.getString("sysLogOaganization.info.post",
                    "sys-log");
        } else if (elem instanceof SysOrgRole) {
            return ResourceUtil.getString("sysLogOaganization.info.role",
                    "sys-log");
        }
        return "";
    }

    @Deprecated
    public static String buildHierarchyIdIncludeOrg(SysOrgElement element) {
        String hierarchyIdIncludeOrg = element.getFdHierarchyId();
        if (element.getFdOrgType().equals(ORG_TYPE_ORG)) {
            SysOrgElement parent = element.getFdParent();
            while (parent != null) {
                hierarchyIdIncludeOrg = "x" + parent.getFdId()
                        + hierarchyIdIncludeOrg;
                parent = parent.getFdParent();

            }
        } else {
            SysOrgElement org = element.getFdParentOrg();
            if (org != null) {
                org = org.getFdParent();
                while (org != null) {
                    hierarchyIdIncludeOrg = "x" + org.getFdId()
                            + hierarchyIdIncludeOrg;
                    org = org.getFdParent();

                }
            }
        }
        return hierarchyIdIncludeOrg;

    }

    /**
     * 获取人员所有父节点的id列表 排列顺序为，从最近的节点一直到顶级节点
     *
     * @param person
     * @return
     */
    public static List<String> getPersonRelatedIds() {
        KMSSUser kmssUser = UserUtil.getKMSSUser();
        if (kmssUser == null) {
            return null;
        }
        return UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();

    }

    /**
     * 获取人员所有父节点的id列表 排列顺序为，从最近的节点一直到顶级节点
     *
     * @param person
     * @return
     */
    public static List<String> getPersonParentIds2(SysOrgPerson person) {
        String hierarchyId = person.getFdHierarchyId();
        String[] ids = hierarchyId.split("x");
        List<String> ids_parent = new ArrayList<String>();
        for (int i = ids.length - 1; i >= 0; i--) {
            if (StringUtil.isNotNull(ids[i])) {
                ids_parent.add(ids[i]);
            }
        }

        if (ids_parent.size() > 0) {
            ids_parent.remove(0);
        }
        return ids_parent;

    }

    public static List<String> getPostParentIds(SysOrgPost post) {

        String hierarchyId = post.getFdHierarchyId();
        String[] ids = hierarchyId.split("x");
        List<String> ids_parent = new ArrayList<String>();
        for (int i = ids.length - 1; i >= 0; i--) {
            if (StringUtil.isNotNull(ids[i])) {
                ids_parent.add(ids[i]);
            }
        }
        return ids_parent;
    }

    public static String buildInBlock(Set<String> elements) {
        String idStr = "";
        for (String fdId : elements) {
            idStr += "'" + fdId + "',";
        }
        return idStr.substring(0, idStr.length() - 1);
    }

    public static String buildInBlock_old(Set<SysOrgElement> elements) {
        String idStr = "";
        for (SysOrgElement ele : elements) {
            idStr += "'" + ele.getFdId() + "',";
        }
        return idStr.substring(0, idStr.length() - 1);
    }

    public static boolean isOrgVisibleEnabled() {
        ISysOrganizationVisibleService sysOrganizationVisibleService = (ISysOrganizationVisibleService) SpringBeanUtil
                .getBean("sysOrganizationVisibleService");

        try {
            return sysOrganizationVisibleService.isOrgVisibleEnable();
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return false;
    }

    public static boolean isOrgAeraEnable() {
        ISysOrganizationVisibleService sysOrganizationVisibleService = (ISysOrganizationVisibleService) SpringBeanUtil
                .getBean("sysOrganizationVisibleService");
        try {
            return sysOrganizationVisibleService.isOrgAeraEnable();
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return false;
    }

    /**
     * 根据机构或部门获取用户数
     *
     * @param parentId
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String getPersonCountByOrgDept(SysOrgElement parent) {
        if (parent == null
                || !parent.getFdIsAvailable()) {
            //失效组织显示数目为0
            return "0";
        }
        long count = 0;
        try {
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setSelectBlock("count(*)");
            hqlInfo.setWhereBlock("sysOrgElement.fdOrgType = 8 and sysOrgElement.fdIsAvailable is true and sysOrgElement.fdHierarchyId like :fdHierarchyId");
            hqlInfo.setParameter("fdHierarchyId", parent.getFdHierarchyId() + "%");
            List<Long> list = getSysOrgElementDao().findValue(hqlInfo);
            count = list.get(0);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return count + "";
    }

    /**
     * 获取外部组织人员统计
     *
     * @param parent
     * @description:
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/9/25 3:02 下午
     */
    public static String getPersonCountByOutOrgDept(SysOrgElement parent) {
        Map<String, Date> personMapByOrgDept = getPersonMapByOrgDept(parent);
        return personMapByOrgDept.size()+"";
    }

    /**
     * 根据部门查询部门下面的人员和岗位下面的人员并集
     *
     * @param parent
     * @description:
     * @return: java.util.Map<java.lang.String, java.util.Date>
     * @author: wangjf
     * @time: 2021/9/25 1:39 下午
     */
    public static Map<String, Date> getPersonMapByOrgDept(SysOrgElement parent) {
        if (parent == null || !parent.getFdIsAvailable()) {
            //失效组织显示数目为0
            return new HashMap<>();
        }
        Map<String, Date> resultMap = new HashMap<>();
        try {
            //查询组织下面的所有人员
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setSelectBlock("sysOrgElement.fdId,sysOrgElement.fdCreateTime");
            hqlInfo.setWhereBlock("sysOrgElement.fdOrgType =:fdOrgType  and sysOrgElement.fdIsAvailable =:fdIsAvailable and sysOrgElement.fdIsExternal =:fdIsExternal and sysOrgElement.fdHierarchyId like :fdHierarchyId");
            hqlInfo.setParameter("fdOrgType", SysOrgConstant.ORG_TYPE_PERSON);
            hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
            hqlInfo.setParameter("fdIsExternal", Boolean.TRUE);
            hqlInfo.setParameter("fdHierarchyId", "%"+parent.getFdHierarchyId() + "%");
            List<Object[]> orgPersonList = getSysOrgElementDao().findValue(hqlInfo);
            //查询组织下面所有的岗位
            HQLInfo postHqlInfo = new HQLInfo();
            postHqlInfo.setSelectBlock("fdId");
            postHqlInfo.setWhereBlock("sysOrgElement.fdOrgType =:fdOrgType  and sysOrgElement.fdIsAvailable =:fdIsAvailable and sysOrgElement.fdIsExternal =:fdIsExternal and sysOrgElement.fdHierarchyId like :fdHierarchyId");
            postHqlInfo.setParameter("fdOrgType", SysOrgConstant.ORG_TYPE_POST);
            postHqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
            postHqlInfo.setParameter("fdIsExternal", Boolean.TRUE);
            postHqlInfo.setParameter("fdHierarchyId","%"+ parent.getFdHierarchyId() + "%");
            List<String> orgPostList = getSysOrgElementDao().findValue(postHqlInfo);
            if (!CollectionUtils.isEmpty(orgPostList)) {
                //根据岗位查询出人员
                HQLInfo personHqlInfo = new HQLInfo();
                personHqlInfo.setSelectBlock("sysOrgElement.fdId,sysOrgElement.fdCreateTime");
                String where = "sysOrgElement.fdOrgType =:fdOrgType and sysOrgElement.fdIsAvailable =:fdIsAvailable and sysOrgElement.fdIsExternal =:fdIsExternal and fdPost.fdId in(:fdPostList)";
                personHqlInfo.setWhereBlock(where);
                personHqlInfo.setJoinBlock("INNER JOIN sysOrgElement.hbmPosts as fdPost");
                personHqlInfo.setParameter("fdOrgType", SysOrgConstant.ORG_TYPE_PERSON);
                personHqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
                personHqlInfo.setParameter("fdIsExternal", Boolean.TRUE);
                //分组查询
                List<List<String>> orgPostLists = ArrayUtil.averageAssign(orgPostList, 1000);
                for(List<String> orgPostListTemp:orgPostLists){
                    personHqlInfo.setParameter("fdPostList", orgPostListTemp);
                    List<Object[]> orgPostPersonList = getSysOrgElementDao().findValue(personHqlInfo);
                    orgPersonList.addAll(orgPostPersonList);
                }
            }
            if (!CollectionUtils.isEmpty(orgPersonList)) {
                for (Object[] obj : orgPersonList) {
                    resultMap.put((String) obj[0], (Date) obj[1]);
                }
            }
        } catch (Exception e) {
            logger.error("查询部门总人数出错", e);
        }
        return resultMap;
    }

    private static ISysOrgElementDao sysOrgElementDao;

    public static ISysOrgElementDao getSysOrgElementDao() {
        if (sysOrgElementDao == null) {
            sysOrgElementDao = (ISysOrgElementDao) SpringBeanUtil.getBean("sysOrgElementDao");
        }
        return sysOrgElementDao;
    }

    /**
     * 在view页面通过ID获取所有上级的名称
     *
     * @param id
     * @return
     */
    public static String getFdParentsNameByForm(SysOrgElementForm elementForm) {
        SysOrgElement element = null;
        try {
            element = (SysOrgElement) getSysOrgElementDao().findByPrimaryKey(elementForm.getFdId());
        } catch (Exception e) {
            logger.error(e.toString());
        }
        if (element != null) {
            return element.getFdParentsName();
        } else {
            return "";
        }
    }

    /**
     * 在view页面通过ID获取离职人员的上级名称
     *
     * @param elementForm
     * @return
     */
    public static String getLeavePersonParentNameByForm(SysOrgElementForm elementForm) {
        SysOrgElement element = null;
        try {
            element = (SysOrgElement) getSysOrgElementDao().findByPrimaryKey(elementForm.getFdPreDeptId());
        } catch (Exception e) {
            logger.error(e.toString());
        }
        if (element != null) {
            return element.getFdName();
        } else {
            return "";
        }
    }

    /**
     * 禁用“匿名”和“EveryOne”用户的操作（如：编辑，禁用）
     * <p>
     * 三员管理员也需要处理
     *
     * @param obj 这里可以传入model或form对象
     * @return
     */
    public static boolean isAnonymousOrEveryOne(Object obj) {
        boolean isAnonymousOrEveryOne = false;
        String fdId = "";
        String fdLoginName = "";
        try {
            fdId = BeanUtils.getProperty(obj, "fdId");
            fdLoginName = BeanUtils.getProperty(obj, "fdLoginName");
            isAnonymousOrEveryOne = "true".equals(BeanUtils.getProperty(obj, "anonymous"));
        } catch (Exception e) {
            logger.error(e.toString());
        }

        if (TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) {
            return isAnonymousOrEveryOne
                    || fdId.equals(SysOrgConstant.ORG_PERSON_EVERYONE_ID)
                    || (StringUtil.isNotNull(fdLoginName)
                    && !TripartiteAdminUtil.isGeneralUser(fdLoginName));
        } else {
            return isAnonymousOrEveryOne
                    || fdId.equals(SysOrgConstant.ORG_PERSON_EVERYONE_ID);
        }
    }

    /**
     * 判断用户是否是三员管理模式下的待激活人员
     * <p>
     * 判断的标准是fdIsAvailable=false, fdIsBusiness=false, fdParentId=
     *
     * @param obj
     * @return
     */
    public static boolean isTripartiteActivation(Object obj) {
        // 默认是已激活
        boolean isTripartiteActivation = true;
        if (obj instanceof SysOrgPersonForm) {
            SysOrgPersonForm form = (SysOrgPersonForm) obj;
            isTripartiteActivation = ((SysOrgPersonForm) obj).isAnonymous();
        } else if (obj instanceof SysOrgPerson) {
            SysOrgPerson person = (SysOrgPerson) obj;
            isTripartiteActivation = ((SysOrgPerson) obj).isAnonymous();
        }
        return isTripartiteActivation;
    }

    /**
     * 获取全拼
     *
     * @param name
     * @return
     */
    public static String getFullPinyin(String name) {
        return ChinesePinyinComparator.getPinyinStringWithDefaultFormat(name)
                .toLowerCase();
    }

    /**
     * 获取简拼
     *
     * @param name
     * @return
     */
    public static String getSimplePinyin(String name) {
        if (StringUtil.isNull(name)) {
            return "";
        }
        StringBuffer buf = new StringBuffer();
        for (char c : name.toCharArray()) {
            String pin = ChinesePinyinComparator.getPinyinStringWithDefaultFormat(c + "");
            if (pin != null && pin.length() > 0) {
                buf.append(pin.charAt(0));
            }
        }
        return buf.toString().toLowerCase();
    }

    /**
     * 模板下载时的文件名如果是中文，需要转码
     *
     * @param request
     * @param oldFileName
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encodeFileName(HttpServletRequest request,
                                        String oldFileName)
            throws UnsupportedEncodingException {
        String userAgent = request.getHeader("User-Agent").toUpperCase();
        if (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("TRIDENT") > -1
                || userAgent.indexOf("EDGE") > -1) {// ie情况处理
            oldFileName = URLEncoder.encode(oldFileName, "UTF-8");
            // 这里的编码后，空格会被解析成+，需要重新处理
            oldFileName = oldFileName.replace("+", "%20");
        } else {
            oldFileName = new String(oldFileName.getBytes("UTF-8"),
                    "ISO8859-1");
        }
        return oldFileName;
    }

    /**
     * 系统是否含有生态组织代码
     *
     * @return
     */
    public static boolean isEcoSystem() {
        SysDictModel modelDic = SysDataDict.getInstance().getModel(SysOrgElement.class.getName());
        if (modelDic != null) {
            for (SysDictCommonProperty property : modelDic.getPropertyList()) {
                if ("fdIsExternal".equalsIgnoreCase(property.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取层级ID
     *
     * @param element
     * @return
     */
    public static String getTreeHierarchyId(SysOrgElement element) {
        if (element.getFdOrgType().intValue() == ORG_TYPE_ORG
                || element.getFdParent() == null) {
            return BaseTreeConstant.HIERARCHY_ID_SPLIT + element.getFdId() + BaseTreeConstant.HIERARCHY_ID_SPLIT;
        } else {
            return element.getFdParent().getFdHierarchyId() + element.getFdId()
                    + BaseTreeConstant.HIERARCHY_ID_SPLIT;
        }
    }

    /**
     * 根据排序-名称的顺序排序，先按序号排序，序号相同按名称拼音进行排序
     *
     * @param list
     */
    public static void sortByOrderAndName(List<SysOrgElement> list) {
        //名称排序器
        Comparator<SysOrgElement> nameComparator = new Comparator<SysOrgElement>() {

            @Override
            public int compare(SysOrgElement o1, SysOrgElement o2) {
                Integer fdOrder1 = o1.getFdOrder();
                Integer fdOrder2 = o2.getFdOrder();

                //序号相同的情况下才进行名字的排序
                if ((fdOrder1 != null && fdOrder2 == null)
                        || (fdOrder1 == null && fdOrder2 != null)
                        || (fdOrder1 != null && fdOrder2 != null && fdOrder1.compareTo(fdOrder2) != 0)) {
                    return 0;
                }

                String fdNamePinYin1 = o1.getFdNamePinYin();
                String fdNamePinYin2 = o2.getFdNamePinYin();

                if (fdNamePinYin1 == null && fdNamePinYin2 == null) {
                    return 0;
                } else if (fdNamePinYin1 != null && fdNamePinYin2 == null) {
                    return 1;
                } else if (fdNamePinYin1 == null && fdNamePinYin2 != null) {
                    return -1;
                } else {
                    return fdNamePinYin1.compareTo(fdNamePinYin2);
                }
            }
        };
        //排序-名称排序器
        Comparator<SysOrgElement> ordeComparator = new Comparator<SysOrgElement>() {

            @Override
            public int compare(SysOrgElement o1, SysOrgElement o2) {
                Integer fdOrder1 = o1.getFdOrder();
                Integer fdOrder2 = o2.getFdOrder();

                if (fdOrder1 == null && fdOrder2 == null) {
                    return 0;
                } else if (fdOrder1 == null && fdOrder2 != null) {
                    return 1;
                } else if (fdOrder1 != null && fdOrder2 == null) {
                    return -1;
                } else {
                    return fdOrder1.compareTo(fdOrder2);
                }
            }
        };

        Collections.sort(list, ordeComparator);
        Collections.sort(list, nameComparator);
    }

    /**
     * 判断父级是否有开启"隐藏设置"
     *
     * @param request
     * @param parent
     * @throws Exception
     */
    public static void getHideRangeTip(HttpServletRequest request, SysOrgElement parent) {
        if (parent == null) {
            return;
        }
        SysOrgElementHideRange hideRange = null;
        String parentName = null;
        int type = 0;
        while (parent != null) {
            SysOrgElementHideRange range = parent.getFdHideRange();
            if (range != null && BooleanUtils.isTrue(range.getFdIsOpenLimit()) && range.getFdViewType() == 0) {
                type = parent.getFdOrgType();
                hideRange = range;
                if (BooleanUtils.isTrue(parent.getFdIsExternal())) {
                    type = 3;
                }
                parentName = parent.getFdName();
            }
            parent = parent.getFdParent();
        }
        if (hideRange != null) {
            String messageKey;
            switch (type) {
                case 1:
                    messageKey = "sysOrgEco.hide.type.parent.in1";    // 上级机构
                    break;
                case 2:
                    messageKey = "sysOrgEco.hide.type.parent.in2";    // 上级部门
                    break;
                default:
                    messageKey = "sysOrgEco.hide.type.parent.eco";    // 上级组织
            }
            String tip = ResourceUtil.getString(messageKey, "sys-organization", null, parentName);
            String msg;
            if (type < 3) {
                msg = ResourceUtil.getString("sysOrgEco.hide.type.all.in", "sys-organization");
            } else {
                msg = ResourceUtil.getString("sysOrgEco.hide.type.all.eco", "sys-organization");
            }
            request.setAttribute("hideRangeTip", tip + msg + ResourceUtil.getString("sysOrgEco.hide.ignore", "sys-organization"));
        }
    }
}
