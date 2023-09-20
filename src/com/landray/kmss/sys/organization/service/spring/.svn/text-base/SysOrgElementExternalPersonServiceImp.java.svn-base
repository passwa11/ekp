package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.organization.forms.SysOrgPersonForm;
import com.landray.kmss.sys.organization.model.SysOrgDept;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgElementExtProp;
import com.landray.kmss.sys.organization.model.SysOrgElementExtPropEnum;
import com.landray.kmss.sys.organization.model.SysOrgElementExternal;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgDeptService;
import com.landray.kmss.sys.organization.service.ISysOrgElementExternalPersonService;
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
 * 外部人员扩展
 *
 * @author 潘永辉 Mar 17, 2020
 */
public class SysOrgElementExternalPersonServiceImp extends SysOrgPersonServiceImp
        implements ISysOrgElementExternalPersonService {
    private ISysOrgElementExternalService sysOrgElementExternalService;
    private ISysOrgDeptService sysOrgDeptService;

    public void setSysOrgElementExternalService(ISysOrgElementExternalService sysOrgElementExternalService) {
        this.sysOrgElementExternalService = sysOrgElementExternalService;
    }

    public void setSysOrgDeptService(ISysOrgDeptService sysOrgDeptService) {
        this.sysOrgDeptService = sysOrgDeptService;
    }

    public ISysOrgElementService getSysOrgElementService() {
        ISysOrgElementService sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
        return sysOrgElementService;
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
        SysOrgElementExtPropUtil.checkPorp(external.getFdPersonProps(), requestContext);

        // 保存外部扩展信息
        String fdId = IDGenerator.generateID();
        String tableName = external.getFdPersonTable();
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        sql.append("INSERT INTO ").append(tableName).append(" (");
        StringBuilder sb = new StringBuilder();
        // 拼接SQL
        for (SysOrgElementExtProp prop : external.getFdPersonProps()) {
            // 拼接参数值
            String value = requestContext.getParameter(prop.getFdFieldName());
            // 过滤禁用属性，过滤空值属性
            if (!BooleanUtils.isTrue(prop.getFdStatus()) || StringUtil.isNull(value)) {
                continue;
            }

            sql.append(prop.getFdColumnName()).append(", ");
            sb.append("?, ");
            if ("java.util.Date".equals(prop.getFdFieldType())) {
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
        SysOrgPersonForm personForm = (SysOrgPersonForm) form;
        personForm.setFdId(fdId);
        personForm.setFdIsExternal("true");

        TransactionStatus status = TransactionUtils.beginNewTransaction();
        try {
            // 生态组织需要发送实时同步事件
            setEventEco(true);
            // 记录变更日志
            SysOrgUtil.ecoChangeFields.set(
                    SysOrgElementExtPropUtil.compare(tableName, fdId, requestContext, external.getFdPersonProps(),
                            true));
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
            // 保存人员信息
            super.add(personForm, requestContext);
            TransactionUtils.commit(status);
        } catch (Exception e) {
            TransactionUtils.rollback(status);
            throw e;
        } finally {
            removeEventEco();
            SysOrgUtil.ecoChangeFields.remove();
        }
        return fdId;
    }

    @Override
    public void update(IExtendForm form, RequestContext requestContext) throws Exception {
        String deptId = requestContext.getParameter("deptId");
        String parentId = requestContext.getParameter("fdParentId");
        String cateId = requestContext.getParameter("cateId");
        if (StringUtil.isNull(parentId)) {
            parentId = deptId;
        }
        if (StringUtil.isNull(parentId)) {
            throw new RuntimeException("上级组织不能为空！");
        }
        SysOrgDept dept = (SysOrgDept) sysOrgDeptService.findByPrimaryKey(parentId);
        if (dept == null) {
            throw new RuntimeException("上级组织不存在！");
        }
        // 获取外部组织类型
        SysOrgElementExternal external = (SysOrgElementExternal) sysOrgElementExternalService.findByPrimaryKey(dept.getFdParentOrg().getFdId());
        // 检查扩展属性
        SysOrgElementExtPropUtil.checkPorp(external.getFdPersonProps(), requestContext);

        if (!dept.getFdParentOrg().getFdId().equals(cateId)) {
            throw new RuntimeException("请选择本组织类型下的组织！");
        }
        // 保存外部扩展信息
        String fdId = form.getFdId();
        String tableName = external.getFdPersonTable();
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();

        String tempSql = "select count(*) from " + tableName + " where fd_id = '" + fdId + "'";
        Query propertyQuery = getBaseDao().getHibernateSession().createNativeQuery(tempSql);
        int total = Integer.parseInt(propertyQuery.list().get(0).toString());
        if (total == 0) {
            sql.append("INSERT INTO ").append(tableName).append(" (");
            StringBuilder sb = new StringBuilder();
            // 拼接SQL
            for (SysOrgElementExtProp prop : external.getFdPersonProps()) {
                // 拼接参数值
                String value = requestContext.getParameter(prop.getFdFieldName());
                // 过滤禁用属性，过滤空值属性
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
            for (SysOrgElementExtProp prop : external.getFdPersonProps()) {
                sql.append(", ").append(prop.getFdColumnName()).append(" = ?");
                // 拼接参数值
                String value = requestContext.getParameter(prop.getFdFieldName());
                if ("java.util.Date".equals(prop.getFdFieldType())) {
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
                    external.getFdPersonProps(), false));
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
            SysOrgPersonForm personForm = (SysOrgPersonForm) form;
            personForm.setFdIsExternal("true");
            super.update(personForm, requestContext);
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
        SysOrgPersonForm personForm = (SysOrgPersonForm) super.convertModelToForm(form, model, requestContext);
        SysOrgPerson person = (SysOrgPerson) model;
        String method = requestContext.getParameter("method");
        String cateId = person.getFdParentOrg().getFdId();
        // 获取外部组织类型
        SysOrgElementExternal external = (SysOrgElementExternal) sysOrgElementExternalService.findByPrimaryKey(cateId);
        requestContext.setParameter("personId", person.getFdId());
        Map<String, String> map = getExtProp(external, person.getFdId(), "view".equals(method));
        personForm.setDynamicMap(map);
        // 排序
        List<SysOrgElementExtProp> list = external.getFdPersonProps();
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
        requestContext.setAttribute("personProps", external.getFdPersonProps());
        requestContext.setAttribute("deptName", person.getFdParent().getFdName());
        // 获取人员扩展属性
        return personForm;
    }

    @Override
    public Map<String, String> getExtProp(SysOrgElementExternal external, String fdId, boolean isView)
            throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        if (external == null || external.getFdPersonProps() == null || external.getFdPersonProps().isEmpty()) {
            return map;
        }

        // 通过组织类型找到扩展表并获取扩展数据
        String tableName = external.getFdPersonTable();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        // 拼接SQL
        for (SysOrgElementExtProp prop : external.getFdPersonProps()) {
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
                SysOrgElementExtProp prop = external.getFdPersonProps().get(i);
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
    public void save(SysOrgPerson person, String tableName, List<SysEcoExtPorp> props, boolean isAdd) throws Exception {
        if (isAdd) {
            super.add(person);
        } else {
            super.update(person);
        }
    }

    @Override
    public void updateTransformOut(SysOrgElement outParent, List<SysOrgElement> sysOrgElementList) throws Exception {

        for (SysOrgElement sysOrgElement : sysOrgElementList) {
            sysOrgElement.setFdIsExternal(true);
            if (outParent != null) {
                sysOrgElement.setHbmParent(outParent);
            }
            getSysOrgElementService().update(sysOrgElement);
        }
    }

}
