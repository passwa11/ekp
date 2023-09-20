package com.landray.kmss.sys.organization.dao.hibernate;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.organization.dao.ISysOrgMatrixDao;
import com.landray.kmss.sys.organization.forms.SysOrgMatrixDataCateForm;
import com.landray.kmss.sys.organization.model.SysOrgMatrix;
import com.landray.kmss.sys.organization.model.SysOrgMatrixRelation;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixDataCateService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.CacheMode;
import org.hibernate.query.Query;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 组织矩阵
 *
 * @author 潘永辉 2019年6月4日
 */
public class SysOrgMatrixDaoImp extends BaseDaoImp implements ISysOrgMatrixDao {

    private ISysOrgMatrixDataCateService sysOrgMatrixDataCateService;

    public ISysOrgMatrixDataCateService getSysOrgMatrixDataCateService() {
        if (sysOrgMatrixDataCateService == null) {
            sysOrgMatrixDataCateService = (ISysOrgMatrixDataCateService) SpringBeanUtil
                    .getBean("sysOrgMatrixDataCateService");
        }
        return sysOrgMatrixDataCateService;
    }

    /**
     * 获取矩阵数据（分页）
     */
    @Override
    public Page findMatrixPage(SysOrgMatrix matrix, String fdVersion, int pageno, int rowsize, String filter)
            throws Exception {
        Page page = new Page();

        String countSql = "SELECT COUNT(fd_id) FROM " + matrix.getFdSubTable() + " WHERE fd_version = :version";
        Query query = getQuery(matrix, countSql, filter);
        query.setParameter("version", fdVersion);
        List<Number> total = query.list();
        page.setTotalrows(total.get(0).intValue());
        if (page.getTotalrows() > 0) {
            page.setRowsize(rowsize);
            page.setPageno(pageno);
            page.excecute();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT fd_id");
            // 条件字段
            if (matrix.getFdRelationConditionals() != null
                    && !matrix.getFdRelationConditionals().isEmpty()) {
                // 排序
                Collections.sort(matrix.getFdRelationConditionals());
                for (SysOrgMatrixRelation relation : matrix.getFdRelationConditionals()) {
                    sql.append(", ").append(relation.getFdFieldName());
                    if (relation.isRange()) {
                        sql.append(", ").append(relation.getFdFieldName2());
                    }
                }
            }
            // 结果字段
            if (matrix.getFdRelationResults() != null
                    && !matrix.getFdRelationResults().isEmpty()) {
                // 排序
                Collections.sort(matrix.getFdRelationResults());
                for (SysOrgMatrixRelation relation : matrix.getFdRelationResults()) {
                    sql.append(", ").append(relation.getFdFieldName());
                }
            }
            sql.append(" FROM ").append(matrix.getFdSubTable());
            if (StringUtil.isNull(fdVersion)) {
                fdVersion = "V1";
            }
            sql.append(" WHERE fd_version = :version");
            query = getQuery(matrix, sql.toString(), filter);
            query.setParameter("version", fdVersion);
            query.setFirstResult(page.getStart());
            query.setMaxResults(page.getRowsize());
            page.setList(query.list());
        } else {
            page = Page.getEmptyPage();
        }
        return page;
    }

    /**
     * 获取矩阵数据（分页）关联数据类别
     */
    @Override
    public Page findMatrixPageByType(SysOrgMatrix matrix, String fdVersion, int pageno, int rowsize,
                                     String fdDataCateId, String filter) throws Exception {
        // 如果没有分组数据，表示未开启分组功能
        if (!BooleanUtils.isTrue(matrix.getFdIsEnabledCate())) {
            return findMatrixPage(matrix, fdVersion, pageno, rowsize, filter);
        }
        Set<String> fdDataCateIds = new HashSet<String>();
        if (StringUtil.isNull(fdDataCateId)) {
            List<SysOrgMatrixDataCateForm> list = getSysOrgMatrixDataCateService().getDataCates(matrix.getFdId());
            for (SysOrgMatrixDataCateForm cateForm : list) {
                fdDataCateIds.add(cateForm.getFdId());
            }
            if (CollectionUtils.isEmpty(fdDataCateIds)) {
                return Page.getEmptyPage();
            }
        }
        Page page = new Page();
        String countSql = "SELECT COUNT(fd_id) FROM " + matrix.getFdSubTable() + " WHERE fd_version = :version";
        if (StringUtil.isNotNull(fdDataCateId)) {
            countSql += " and fd_cate_id = :fdDataCateId";
        }
        if (CollectionUtils.isNotEmpty(fdDataCateIds)) {
            countSql += " and fd_cate_id in (:fdDataCateIds)";
        }
        Query query = getQuery(matrix, countSql, filter);
        query.setParameter("version", fdVersion);
        if (StringUtil.isNotNull(fdDataCateId)) {
            query.setParameter("fdDataCateId", fdDataCateId);
        }
        if (CollectionUtils.isNotEmpty(fdDataCateIds)) {
            query.setParameterList("fdDataCateIds", fdDataCateIds);
        }
        List<Number> total = query.list();
        page.setTotalrows(total.get(0).intValue());
        if (page.getTotalrows() > 0) {
            page.setRowsize(rowsize);
            page.setPageno(pageno);
            page.excecute();
            HttpServletRequest request = Plugin.currentRequest();
            String method = request.getParameter("method_GET");
            StringBuilder sql = new StringBuilder();
            // 矩阵数据ID，分组ID
            sql.append("SELECT fd_id");
            // 条件字段
            if (matrix.getFdRelationConditionals() != null && !matrix.getFdRelationConditionals().isEmpty()) {
                // 排序
                Collections.sort(matrix.getFdRelationConditionals());
                for (SysOrgMatrixRelation relation : matrix.getFdRelationConditionals()) {
                    sql.append(", ").append(relation.getFdFieldName());
                    if (relation.isRange()) {
                        sql.append(", ").append(relation.getFdFieldName2());
                    }
                }
            }
            // 结果字段
            if (matrix.getFdRelationResults() != null && !matrix.getFdRelationResults().isEmpty()) {
                // 排序
                Collections.sort(matrix.getFdRelationResults());
                for (SysOrgMatrixRelation relation : matrix.getFdRelationResults()) {
                    sql.append(", ").append(relation.getFdFieldName());
                }
            }
            if (StringUtil.isNull(method)) {
                sql.append(", fd_cate_id");
            }
            sql.append(" FROM ").append(matrix.getFdSubTable());
            if (StringUtil.isNull(fdVersion)) {
                fdVersion = "V1";
            }
            sql.append(" WHERE fd_version = :version");
            if (StringUtil.isNotNull(fdDataCateId)) {
                sql.append(" and fd_cate_id = :fdDataCateId");
            }
            if (CollectionUtils.isNotEmpty(fdDataCateIds)) {
                sql.append(" and fd_cate_id in (:fdDataCateIds)");
            }
            query = getQuery(matrix, sql.toString(), filter);
            query.setParameter("version", fdVersion);
            if (StringUtil.isNotNull(fdDataCateId)) {
                query.setParameter("fdDataCateId", fdDataCateId);
            }
            if (CollectionUtils.isNotEmpty(fdDataCateIds)) {
                query.setParameterList("fdDataCateIds", fdDataCateIds);
            }
            query.setFirstResult(page.getStart());
            query.setMaxResults(page.getRowsize());
            page.setList(query.list());
        } else {
            page = Page.getEmptyPage();
        }
        return page;
    }

    @Override
    public Page findMatrixPageToExport(SysOrgMatrix matrix, String fdVersion, int pageno, int rowsize, String filter, Map<Integer,String> columnMap) throws Exception {
        Page page = new Page();

        String countSql = "SELECT COUNT(fd_id) FROM " + matrix.getFdSubTable() + " WHERE fd_version = :version";
        Query query = getQuery(matrix, countSql, filter);
        query.setParameter("version", fdVersion);
        List<Number> total = query.list();
        page.setTotalrows(total.get(0).intValue());
        if (page.getTotalrows() > 0) {
            page.setRowsize(rowsize);
            page.setPageno(pageno);
            page.excecute();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT fd_id,fd_cate_id,fd_version");
            int i = 0;
            columnMap.put(i++,"fd_id");
            columnMap.put(i++,"fd_cate_id");
            columnMap.put(i++,"fd_version");
            // 条件字段
            if (matrix.getFdRelationConditionals() != null
                    && !matrix.getFdRelationConditionals().isEmpty()) {
                // 排序
                Collections.sort(matrix.getFdRelationConditionals());
                for (SysOrgMatrixRelation relation : matrix.getFdRelationConditionals()) {
                    sql.append(", ").append(relation.getFdFieldName());
                    columnMap.put(i++,relation.getFdFieldName());
                    if (relation.isRange()) {
                        sql.append(", ").append(relation.getFdFieldName2());
                        columnMap.put(i++,relation.getFdFieldName2());
                    }
                }
            }
            // 结果字段
            if (matrix.getFdRelationResults() != null
                    && !matrix.getFdRelationResults().isEmpty()) {
                // 排序
                Collections.sort(matrix.getFdRelationResults());
                for (SysOrgMatrixRelation relation : matrix.getFdRelationResults()) {
                    sql.append(", ").append(relation.getFdFieldName());
                    columnMap.put(i++,relation.getFdFieldName());
                }
            }
            sql.append(" FROM ").append(matrix.getFdSubTable());
            if (StringUtil.isNull(fdVersion)) {
                fdVersion = "V1";
            }
            sql.append(" WHERE fd_version = :version");
            query = getQuery(matrix, sql.toString(), filter);
            query.setParameter("version", fdVersion);
            query.setFirstResult(page.getStart());
            query.setMaxResults(page.getRowsize());
            page.setList(query.list());
        } else {
            page = Page.getEmptyPage();
        }
        return page;
    }

    @Override
    public Page findMatrixPageByTypeToExport(SysOrgMatrix matrix, String fdVersion, int pageno, int rowsize, String fdDataCateId, String filter, Map<Integer,String> columnMap) throws Exception {
        // 如果没有分组数据，表示未开启分组功能
        if (!BooleanUtils.isTrue(matrix.getFdIsEnabledCate())) {
            return findMatrixPageToExport(matrix, fdVersion, pageno, rowsize, filter,columnMap);
        }
        Set<String> fdDataCateIds = new HashSet<String>();
        if (StringUtil.isNull(fdDataCateId)) {
            List<SysOrgMatrixDataCateForm> list = getSysOrgMatrixDataCateService().getDataCates(matrix.getFdId());
            for (SysOrgMatrixDataCateForm cateForm : list) {
                fdDataCateIds.add(cateForm.getFdId());
            }
            if (CollectionUtils.isEmpty(fdDataCateIds)) {
                return Page.getEmptyPage();
            }
        }
        Page page = new Page();
        String countSql = "SELECT COUNT(fd_id) FROM " + matrix.getFdSubTable() + " WHERE fd_version = :version";
        if (StringUtil.isNotNull(fdDataCateId)) {
            countSql += " and fd_cate_id = :fdDataCateId";
        }
        if (CollectionUtils.isNotEmpty(fdDataCateIds)) {
            countSql += " and fd_cate_id in (:fdDataCateIds)";
        }
        Query query = getQuery(matrix, countSql, filter);
        query.setParameter("version", fdVersion);
        if (StringUtil.isNotNull(fdDataCateId)) {
            query.setParameter("fdDataCateId", fdDataCateId);
        }
        if (CollectionUtils.isNotEmpty(fdDataCateIds)) {
            query.setParameterList("fdDataCateIds", fdDataCateIds);
        }
        List<Number> total = query.list();
        page.setTotalrows(total.get(0).intValue());
        if (page.getTotalrows() > 0) {
            page.setRowsize(rowsize);
            page.setPageno(pageno);
            page.excecute();
            StringBuilder sql = new StringBuilder();
            // 矩阵数据ID，分组ID
            sql.append("SELECT fd_id,fd_cate_id,fd_version");
            int i = 0;
            columnMap.put(i++,"fd_id");
            columnMap.put(i++,"fd_cate_id");
            columnMap.put(i++,"fd_version");
            // 条件字段
            if (matrix.getFdRelationConditionals() != null && !matrix.getFdRelationConditionals().isEmpty()) {
                // 排序
                Collections.sort(matrix.getFdRelationConditionals());
                for (SysOrgMatrixRelation relation : matrix.getFdRelationConditionals()) {
                    sql.append(", ").append(relation.getFdFieldName());
                    columnMap.put(i++,relation.getFdFieldName());
                    if (relation.isRange()) {
                        sql.append(", ").append(relation.getFdFieldName2());
                        columnMap.put(i++,relation.getFdFieldName2());
                    }
                }
            }
            // 结果字段
            if (matrix.getFdRelationResults() != null && !matrix.getFdRelationResults().isEmpty()) {
                // 排序
                Collections.sort(matrix.getFdRelationResults());
                for (SysOrgMatrixRelation relation : matrix.getFdRelationResults()) {
                    sql.append(", ").append(relation.getFdFieldName());
                    columnMap.put(i++,relation.getFdFieldName());
                }
            }
            sql.append(" FROM ").append(matrix.getFdSubTable());
            if (StringUtil.isNull(fdVersion)) {
                fdVersion = "V1";
            }
            sql.append(" WHERE fd_version = :version");
            if (StringUtil.isNotNull(fdDataCateId)) {
                sql.append(" and fd_cate_id = :fdDataCateId");
            }
            if (CollectionUtils.isNotEmpty(fdDataCateIds)) {
                sql.append(" and fd_cate_id in (:fdDataCateIds)");
            }
            query = getQuery(matrix, sql.toString(), filter);
            query.setParameter("version", fdVersion);
            if (StringUtil.isNotNull(fdDataCateId)) {
                query.setParameter("fdDataCateId", fdDataCateId);
            }
            if (CollectionUtils.isNotEmpty(fdDataCateIds)) {
                query.setParameterList("fdDataCateIds", fdDataCateIds);
            }
            query.setFirstResult(page.getStart());
            query.setMaxResults(page.getRowsize());
            page.setList(query.list());
        } else {
            page = Page.getEmptyPage();
        }
        return page;
    }

    /**
     * 构造数据筛选的Query
     *
     * @param matrix
     * @param sql
     * @param filter 数据筛选，{'列ID':['abc','def'],'列ID':'123'}
     * @return
     * @throws Exception
     */
    private Query getQuery(SysOrgMatrix matrix, String sql, String filter) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(sql);
        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtil.isNotNull(filter)) {
            try {
                JSONObject object = (JSONObject) JSONObject.parse(filter);
                List<SysOrgMatrixRelation> relations = matrix.getFdRelations();
                for (SysOrgMatrixRelation relation : relations) {
                    Object value = object.get(relation.getFdId());
                    if (value == null) {
                        continue;
                    }
                    if (BooleanUtils.isTrue(relation.getFdIsResult())) {
                        // 结果是单选，只能模糊搜索
                        sb.append(" and ").append(relation.getFdFieldName()).append(" like :")
                                .append(relation.getFdFieldName());
                        params.put(relation.getFdFieldName(), "%" + value + "%");
                    } else {
                        // 条件可以单选和多选
                        if (value instanceof String) {
                            sb.append(" and ").append(relation.getFdFieldName()).append(" = :")
                                    .append(relation.getFdFieldName());
                        } else if (value instanceof Collection) {
                            sb.append(" and ").append(relation.getFdFieldName()).append(" in (:")
                                    .append(relation.getFdFieldName()).append(")");
                        }
                        params.put(relation.getFdFieldName(), value);
                    }
                }
            } catch (Exception e) {
                logger.error("数据筛选转换失败：", e);
            }
        }
        Query query = getHibernateSession().createNativeQuery(sb.toString());
        if (!params.isEmpty()) {
            for (Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query;
    }

}
