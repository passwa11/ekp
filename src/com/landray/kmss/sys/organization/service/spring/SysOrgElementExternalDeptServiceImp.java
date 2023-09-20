package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.organization.forms.SysOrgDeptForm;
import com.landray.kmss.sys.organization.forms.SysOrgElementHideRangeForm;
import com.landray.kmss.sys.organization.forms.SysOrgElementRangeForm;
import com.landray.kmss.sys.organization.model.SysOrgDept;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgElementExtProp;
import com.landray.kmss.sys.organization.model.SysOrgElementExtPropEnum;
import com.landray.kmss.sys.organization.model.SysOrgElementExternal;
import com.landray.kmss.sys.organization.service.ISysOrgElementExternalDeptService;
import com.landray.kmss.sys.organization.service.ISysOrgElementExternalService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.util.SysOrgElementExtPropUtil;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.sys.organization.webservice.eco.org.SysEcoExtPorp;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;
import edu.emory.mathcs.backport.java.util.Collections;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.transaction.TransactionStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 外部组织扩展
 *
 * @author 潘永辉 Mar 17, 2020
 */
public class SysOrgElementExternalDeptServiceImp extends SysOrgDeptServiceImp
        implements ISysOrgElementExternalDeptService {
    private ISysOrgElementExternalService sysOrgElementExternalService;

    public void setSysOrgElementExternalService(ISysOrgElementExternalService sysOrgElementExternalService) {
        this.sysOrgElementExternalService = sysOrgElementExternalService;
    }

    public ISysOrgElementService getSysOrgElementService() {
        ISysOrgElementService sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
        return sysOrgElementService;
    }

    @Override
    public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model, RequestContext requestContext)
            throws Exception {
        // 检查组织查看权限
        SysOrgDeptForm deptForm = (SysOrgDeptForm) form;
        if (deptForm.getFdRange() == null || !"true".equals(deptForm.getFdRange().getFdIsOpenLimit())) {
            // 外部组织强制“开启限制组织下成员查看组织范围”
            SysOrgElementRangeForm rangeForm = new SysOrgElementRangeForm();
            // 开启权限范围
            rangeForm.setFdIsOpenLimit("true");
            // 设置为“仅所在组织及下级组织/人员”
            rangeForm.setFdViewType("1");
            deptForm.setFdRange(rangeForm);
        }
        // 隐藏属性(可以关闭，默认开启，对所有人隐藏)
        if (deptForm.getFdHideRange() == null) {
            SysOrgElementHideRangeForm rangeForm = new SysOrgElementHideRangeForm();
            rangeForm.setFdIsOpenLimit("true");
            rangeForm.setFdViewType("0");
            deptForm.setFdHideRange(rangeForm);
        }
        return super.convertFormToModel(form, model, requestContext);
    }

    @Override
    public String add(IExtendForm form, RequestContext requestContext) throws Exception {
        String cateId = requestContext.getParameter("cateId");
        if (StringUtil.isNull(cateId)) {
            throw new RuntimeException("组织类型不能为空！");
        }
        // 获取外部组织类型
        SysOrgElementExternal external = (SysOrgElementExternal) sysOrgElementExternalService.findByPrimaryKey(cateId);
        // 检查扩展属性
        SysOrgElementExtPropUtil.checkPorp(external.getFdDeptProps(), requestContext);

        // 校验上级组织
        SysOrgDeptForm dept = (SysOrgDeptForm) form;
        String parentId = dept.getFdParentId();
        SysOrgElement parent = (SysOrgElement) sysOrgElementService.findByPrimaryKey(parentId);
        String tempCateId = parent.getFdHierarchyId().split("x")[1];
        if (!cateId.equals(tempCateId)) {
            throw new RuntimeException("请选择之前所属组织类型或其子组织！");
        }
        // 保存外部扩展信息
        String fdId = IDGenerator.generateID();
        String tableName = external.getFdDeptTable();
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        sql.append("INSERT INTO ").append(tableName).append(" (");
        StringBuilder sb = new StringBuilder();
        // 拼接SQL
        for (SysOrgElementExtProp prop : external.getFdDeptProps()) {
            // 拼接参数值
            String value = requestContext.getParameter(prop.getFdFieldName());
            // 过滤禁用属性，过滤空值属性
            if (!BooleanUtils.isTrue(prop.getFdStatus()) || StringUtil.isNull(value)) {
                continue;
            }

            sql.append(prop.getFdColumnName()).append(", ");
            sb.append("?, ");

            if ("java.util.Date".equals(prop.getFdFieldType()) && StringUtil.isNotNull(value)) {
                String pattern = ResourceUtil.getString("date.format." + prop.getFdDisplayType());
                Date date = DateUtil.convertStringToDate(value, pattern);
                params.add(date);
            } else {
                params.add(value);
            }
        }
        sql.append("fd_id) VALUES (").append(sb.toString()).append("?)");
        params.add(fdId);

        // 设置为外部组织
        SysOrgDeptForm deptForm = (SysOrgDeptForm) form;
        deptForm.setFdId(fdId);
        deptForm.setFdIsExternal("true");
        // 设置查看范围
        deptForm.getFdRange().setFdId(fdId);
        deptForm.getFdRange().setFdElementId(fdId);

        TransactionStatus status = TransactionUtils.beginNewTransaction();
        try {
            // 生态组织需要发送实时同步事件
            setEventEco(true);
            // 记录变更日志
            SysOrgUtil.ecoChangeFields.set(
                    SysOrgElementExtPropUtil.compare(tableName, fdId, requestContext, external.getFdDeptProps(), true));
            NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
            if("sys_org_person".equalsIgnoreCase(tableName)){
                query.addSynchronizedQuerySpace("sys_org_person", "sys_org_element");
            }else{
                query.addSynchronizedQuerySpace(tableName);
            }
            // 设置参数
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, params.get(i));
            }
            // 保存扩展数据
            query.executeUpdate();
            // 保存部门信息
            super.add(deptForm, requestContext);
            TransactionUtils.commit(status);
        } catch (Exception e) {
            TransactionUtils.rollback(status);
            throw e;
        } finally {
            removeEventEco();
            SysOrgUtil.ecoChangeFields.remove();
        }

        // 清除用户过滤器缓存，否则创建的组织可能看不到
        Map userHqlInfoMap = UserUtil.getKMSSUser().getUserAuthInfo().getHqlInfoModelMap();
        userHqlInfoMap.remove(SysAuthConstant.AUTH_CHECK_READER + "_" + deptForm.getModelClass().getName());

        return fdId;
    }

    @Override
    public void update(IExtendForm form, RequestContext requestContext) throws Exception {
        String cateId = requestContext.getParameter("cateId");
        SysOrgElementExternal external = (SysOrgElementExternal) sysOrgElementExternalService.findByPrimaryKey(cateId);
        // 检查扩展属性
        SysOrgElementExtPropUtil.checkPorp(external.getFdDeptProps(), requestContext);

        // 校验上级组织
        SysOrgDeptForm deptForm = (SysOrgDeptForm) form;
        String parentId = deptForm.getFdParentId();
        SysOrgElement parent = (SysOrgElement) sysOrgElementService.findByPrimaryKey(parentId);
        String tempCateId = parent.getFdHierarchyId().split("x")[1];
        if (!cateId.equals(tempCateId)) {
            throw new RuntimeException("请选择之前所属组织类型或其子组织！");
        }

        // 保存外部扩展信息
        String fdId = form.getFdId();
        String tableName = external.getFdDeptTable();
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();

        String tempSql = "select count(*) from " + tableName + " where fd_id = '" + fdId + "'";
        Query propertyQuery = getBaseDao().getHibernateSession().createNativeQuery(tempSql);
        int total = Integer.parseInt(propertyQuery.list().get(0).toString());
        if (total == 0) {
            sql.append("INSERT INTO ").append(tableName).append(" (");
            StringBuilder sb = new StringBuilder();
            // 拼接SQL
            for (SysOrgElementExtProp prop : external.getFdDeptProps()) {
                // 拼接参数值
                String value = requestContext.getParameter(prop.getFdFieldName());
                // 过滤禁用属性
                if (!BooleanUtils.isTrue(prop.getFdStatus())) {
                    continue;
                }

                sql.append(prop.getFdColumnName()).append(", ");
                sb.append("?, ");
                if ("java.util.Date".equals(prop.getFdFieldType()) && StringUtil.isNotNull(value)) {
                    String pattern = ResourceUtil.getString("date.format." + prop.getFdDisplayType());
                    Date date = DateUtil.convertStringToDate(value, pattern);
                    params.add(date);
                } else if (StringUtil.isNull(value)) {
                    params.add("");
                } else {
                    params.add(value);
                }
            }
            sql.append("fd_id) VALUES (").append(sb.toString()).append("?)");
            params.add(fdId);
        } else {
            sql.append("UPDATE ").append(tableName).append(" SET fd_id = fd_id");
            // 拼接SQL
            for (SysOrgElementExtProp prop : external.getFdDeptProps()) {
                sql.append(", ").append(prop.getFdColumnName()).append(" = ?");
                // 拼接参数值
                String value = requestContext.getParameter(prop.getFdFieldName());
                if ("java.util.Date".equals(prop.getFdFieldType()) && StringUtil.isNotNull(value)) {
                    String pattern = ResourceUtil.getString("date.format." + prop.getFdDisplayType());
                    Date date = DateUtil.convertStringToDate(value, pattern);
                    params.add(date);
                } else {
                    params.add(value);
                }
            }
            sql.append(" WHERE fd_id = ?");
            params.add(fdId);
        }

        TransactionStatus status = TransactionUtils.beginNewTransaction();
        try {
            // 生态组织需要发送实时同步事件
            setEventEco(true);
            // 记录变更日志
            SysOrgUtil.ecoChangeFields.set(SysOrgElementExtPropUtil.compare(tableName, fdId, requestContext,
                    external.getFdDeptProps(), false));
            NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
            if("sys_org_person".equalsIgnoreCase(tableName)){
                query.addSynchronizedQuerySpace("sys_org_person", "sys_org_element");
            }else{
                query.addSynchronizedQuerySpace(tableName);
            }
            // 设置参数
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i, params.get(i));
            }
            // 保存扩展数据
            query.executeUpdate();
            deptForm.setFdIsExternal("true");
            super.update(deptForm, requestContext);
            TransactionUtils.commit(status);
        } catch (Exception e) {
            TransactionUtils.rollback(status);
            throw e;
        } finally {
            removeEventEco();
            SysOrgUtil.ecoChangeFields.remove();
        }
    }

    @Override
    public IExtendForm convertModelToForm(IExtendForm form, IBaseModel model, RequestContext requestContext)
            throws Exception {
        SysOrgDept dept = (SysOrgDept) model;
        String method = requestContext.getParameter("method");
        String cateId = dept.getFdParentOrg().getFdId();
        SysOrgDeptForm deptForm = (SysOrgDeptForm) super.convertModelToForm(form, model, requestContext);
        // 获取外部组织类型
        SysOrgElementExternal external = (SysOrgElementExternal) sysOrgElementExternalService.findByPrimaryKey(cateId);
        Map<String, String> map = getExtProp(external, deptForm.getFdId(), "view".equals(method));
        deptForm.setDynamicMap(map);

        // 排序
        List<SysOrgElementExtProp> list = external.getFdDeptProps();
        Collections.sort(list, new Comparator<SysOrgElementExtProp>() {
            @Override
            public int compare(SysOrgElementExtProp o1, SysOrgElementExtProp o2) {
                Integer num1 = o1.getFdOrder();
                Integer num2 = o2.getFdOrder();
                if (num2 == null) {
                    return 0;
                }
                if (num1 == null) {
                    return -1;
                }
                if (num2 < num1) {
                    return 0;
                } else if (num1.equals(num2)) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        requestContext.setAttribute("deptProps", external.getFdDeptProps());
        requestContext.setAttribute("cateName", external.getFdElement().getFdName());
        // 获取外部扩展属性
        return deptForm;
    }

    @Override
    public Map<String, String> getExtProp(SysOrgElementExternal external, String fdId, boolean isView)
            throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        if (external.getFdDeptProps() == null || external.getFdDeptProps().isEmpty()) {
            return map;
        }

        // 通过组织类型找到扩展表并获取扩展数据
        String tableName = external.getFdDeptTable();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        // 拼接SQL
        for (SysOrgElementExtProp prop : external.getFdDeptProps()) {
            sql.append(prop.getFdColumnName()).append(", ");
        }
        sql.append("fd_id").append(" FROM ").append(tableName).append(" WHERE fd_id = ?");
        NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
        query.setParameter(0, fdId);
        List<Object[]> list = query.list();
        if (CollectionUtils.isNotEmpty(list)) {
            Object[] objs = list.get(0);
            for (int i = 0; i < objs.length - 1; i++) {
                Object obj = objs[i];
                if (obj == null) {
                    continue;
                }
                String value = obj.toString();
                if (StringUtil.isNull(value)) {
                    continue;
                }
                SysOrgElementExtProp prop = external.getFdDeptProps().get(i);
                // 处理日期
                if ("java.util.Date".equals(prop.getFdFieldType()) && obj instanceof Date) {
                    String pattern = ResourceUtil.getString("date.format." + prop.getFdDisplayType());
                    value = DateUtil.convertDateToString((Date) obj, pattern);
                }
                // 处理枚举（View页面）
                if (isView) {
                    if ("radio".equals(prop.getFdDisplayType()) || "select".equals(prop.getFdDisplayType())) {
                        String _value = "";
                        List<SysOrgElementExtPropEnum> enums = prop.getFdFieldEnums();
                        for (SysOrgElementExtPropEnum temp : enums) {
                            if (value.equals(temp.getFdValue())) {
                                _value = temp.getFdName();
                                break;
                            }
                        }
                        value = _value;
                    } else if ("checkbox".equals(prop.getFdDisplayType())) {
                        String[] split = value.split("[;,]");
                        List<String> vals = Arrays.asList(split);
                        List<String> labels = new ArrayList<String>();
                        List<SysOrgElementExtPropEnum> enums = prop.getFdFieldEnums();
                        for (SysOrgElementExtPropEnum temp : enums) {
                            if (vals.contains(temp.getFdValue())) {
                                labels.add(temp.getFdName());
                            }
                        }
                        if (CollectionUtils.isNotEmpty(labels)) {
                            value = StringUtils.join(labels, ";");
                        } else {
                            value = "";
                        }
                    }
                }
                // 处理浮点型展现
                if ("java.lang.Double".equals(prop.getFdFieldType())) {
                    BigDecimal bg = new BigDecimal(value);
                    map.put(prop.getFdFieldName(), bg.toString());
                    continue;
                }

                map.put(prop.getFdFieldName(), value);
            }
        }
        return map;
    }

    @Override
    public void updateInvalidated(String id, RequestContext requestContext) throws Exception {
        try {
            // 生态组织需要发送实时同步事件
            setEventEco(true);
            super.updateInvalidated(id, requestContext);
        } finally {
            removeEventEco();
        }
    }

    @Override
    public void updateInvalidated(String[] ids, RequestContext requestContext) throws Exception {
        try {
            // 生态组织需要发送实时同步事件
            setEventEco(true);
            super.updateInvalidated(ids, requestContext);
        } finally {
            removeEventEco();
        }
    }

    @Override
    public void save(SysOrgDept dept, String tableName, List<SysEcoExtPorp> props, boolean isAdd) throws Exception {
        try {
            // 生态组织需要发送实时同步事件
            setEventEco(true);
            if (isAdd) {
                super.add(dept);
            } else {
                super.update(dept);
            }
        } finally {
            removeEventEco();
        }
    }

    @Override
    public void updateTransformOut(SysOrgElement outParent, SysOrgElement inSysOrgElement) throws Exception {
        /**
         * 1、更新除自身外的所有组织、岗位和人员（采用hql是基于性能的考虑）
         * 2、更新自身组织状态并建立父级关系
         */
        String hql = "update SysOrgElement  set fdIsExternal=:fdIsExternal,fdAlterTime=:fdAlterTime where fdHierarchyId like :fdHierarchyId";

        Query query = this.getBaseDao().getHibernateSession().createQuery(hql).setParameter("fdIsExternal", Boolean.TRUE).setParameter("fdAlterTime", new Date())
                .setParameter("fdHierarchyId", inSysOrgElement.getFdHierarchyId() + "%");
        query.executeUpdate();

        if (inSysOrgElement.getFdRange() != null) {
            //更新可见范围
            inSysOrgElement.getFdRange().setFdViewType(1);
            inSysOrgElement.getFdRange().setFdIsOpenLimit(Boolean.TRUE);
        }
        //更新组织
        inSysOrgElement.setFdIsExternal(true);
        inSysOrgElement.setHbmParent(outParent);
        getSysOrgElementService().update(inSysOrgElement);
    }

}
