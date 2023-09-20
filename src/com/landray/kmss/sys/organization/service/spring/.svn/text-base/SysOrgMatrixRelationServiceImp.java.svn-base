package com.landray.kmss.sys.organization.service.spring;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgMatrix;
import com.landray.kmss.sys.organization.model.SysOrgMatrixRelation;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixRelationService;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;

/**
 * 矩阵关系
 *
 * @author 潘永辉 2019年6月6日
 */
public class SysOrgMatrixRelationServiceImp extends BaseServiceImp
        implements ISysOrgMatrixRelationService {

    private String updateHql = "update com.landray.kmss.sys.organization.model.SysOrgMatrixRelation set fdWidth = :fdWidth where fdId = :fdId";

    // 忽略检测的列
    private String[] ignoreColumns = new String[]{"fd_id", "fd_cate_id", "fd_version"};

    private ISysOrgMatrixService sysOrgMatrixService;

    public void setSysOrgMatrixService(ISysOrgMatrixService sysOrgMatrixService) {
        this.sysOrgMatrixService = sysOrgMatrixService;
    }

    private DataSource dataSource;

    private DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
        }
        return dataSource;
    }

    /**
     * 更新列宽，数据格式如下：
     *
     * <pre>
     * {
     * 		"列ID": "列宽"
     * }
     * </pre>
     */
    @Override
    public void updateWidth(RequestContext request) throws Exception {
        // 获取列宽定义数据
        String widths = request.getParameter("widths");
        if (StringUtil.isNotNull(widths)) {
            JSONObject object = JSONObject.parseObject(widths);
            for (Entry<String, Object> entry : object.entrySet()) {
                getBaseDao().getHibernateSession().createQuery(updateHql).setParameter("fdWidth", entry.getValue())
                        .setParameter("fdId", entry.getKey()).executeUpdate();
            }
        }
    }

    @Override
    public JSONArray checkField(String matrixId) throws Exception {
        SysOrgMatrix matrix = (SysOrgMatrix) sysOrgMatrixService.findByPrimaryKey(matrixId);
        // 获取当前正在使用的字段
        List<SysOrgMatrixRelation> relations = matrix.getFdRelations();
        Map<String, SysOrgMatrixRelation> map = new HashMap<String, SysOrgMatrixRelation>();
        for (SysOrgMatrixRelation relation : relations) {
            map.put(relation.getFdFieldName(), relation);
            if (StringUtil.isNotNull(relation.getFdFieldName2())) {
                map.put(relation.getFdFieldName2(), relation);
            }
        }

        // 获取矩阵数据表的字段
        JSONArray columns = getColumns(matrix.getFdSubTable());

        // 正常来说，数据库表字段是最全的，因为编辑矩阵时，字段会实时创建
        for (int i = 0; i < columns.size(); i++) {
            JSONObject obj = columns.getJSONObject(i);
            String name = obj.getString("name");
            if (ArrayUtils.contains(ignoreColumns, name)) {
                // 逻辑列，忽略
                obj.put("ignore", true);
                continue;
            }
            SysOrgMatrixRelation relation = map.get(name);
            if (relation == null) {
                // 数据库有字段，但是矩阵没有，说明在矩阵中该字段已删除，需要在页面上标注可以删除
                obj.put("isDel", true);
                obj.put("order", Integer.MAX_VALUE);
            } else {
                obj.put("isDel", false);
                if (StringUtil.isNotNull(relation.getFdFieldName2())) {
                    if (name.equals(relation.getFdFieldName2())) {
                        obj.put("label", relation.getFdName() + "(" + ResourceUtil.getString("sys-organization:sysOrgMatrix.range.end") + ")");
                    } else {
                        obj.put("label", relation.getFdName() + "(" + ResourceUtil.getString("sys-organization:sysOrgMatrix.range.begin") + ")");
                    }
                } else {
                    obj.put("label", relation.getFdName());
                }
                obj.put("order", relation.getFdOrder());
                obj.put("isResult", relation.getFdIsResult());
            }
        }

        return columns;
    }

    /**
     * 获取表字段
     *
     * @param tableName
     * @return
     * @throws Exception
     */
    private JSONArray getColumns(String tableName) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        JSONArray array = new JSONArray();
        try {
            conn = getDataSource().getConnection();
            DatabaseMetaData dmd = conn.getMetaData();
            rs = dmd.getColumns(null, null, tableName, "%");
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("name", rs.getString("COLUMN_NAME"));
                obj.put("type", rs.getString("TYPE_NAME"));
                array.add(obj);
            }
            // 查询不为空的数据量
            if (CollectionUtils.isNotEmpty(array)) {
                // 查询该列的数据量
                for (int i = 0; i < array.size(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    String name = obj.getString("name");
                    if (ArrayUtils.contains(ignoreColumns, name)) {
                        continue;
                    }
                    obj.put("count", getCount(tableName, name));
                }
            }
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeConnection(conn);
        }
        return array;
    }

    /**
     * 获取字段数据量
     *
     * @param tableName
     * @param field
     * @return
     * @throws Exception
     */
    private int getCount(String tableName, String field) throws Exception {
        // 查询该列的数据量
        String sql = "select count(fd_id) from %s where %s is not null or %s <> ''";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet query = null;
        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(String.format(sql, tableName, field, field));
            query = ps.executeQuery();
            if (query.next()) {
                return Integer.parseInt(query.getObject(1).toString());
            }
        } finally {
            JdbcUtils.closeResultSet(query);
            JdbcUtils.closeStatement(ps);
            JdbcUtils.closeConnection(conn);
        }
        return 0;
    }

    @Override
    public void deleteField(String matrixId, String field) throws Exception {
        SysOrgMatrix matrix = (SysOrgMatrix) sysOrgMatrixService.findByPrimaryKey(matrixId);
        // 判断字段是否存在
        if (isColumnExist(matrix.getFdSubTable(), field)) {
            // 判断是否有数据
            int count = getCount(matrix.getFdSubTable(), field);
            if (count > 0) {
                throw new RuntimeException(ResourceUtil.getString("sysOrgMatrix.field.delete.error", "sys-organization",
                        null, new Object[]{count, matrix.getFdSubTable(), field}));
            }
            String sql = "ALTER TABLE " + matrix.getFdSubTable() + " DROP COLUMN " + field;
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = getDataSource().getConnection();
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
            } finally {
                JdbcUtils.closeStatement(ps);
                JdbcUtils.closeConnection(conn);
            }
        }
    }

    /**
     * 判断字段是否存在
     *
     * @param tableName
     * @param field
     * @return
     * @throws Exception
     */
    private boolean isColumnExist(String tableName, String field) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        boolean isExist = false;
        try {
            conn = getDataSource().getConnection();
            DatabaseMetaData dmd = conn.getMetaData();
            rs = dmd.getColumns(null, null, tableName, field);
            isExist = rs.next();
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeConnection(conn);
        }
        return isExist;
    }

    /**
     * 深度检测数量，有性能损耗
     */
    @Override
    public int depthCheck(String matrixId, String field) throws Exception {
        SysOrgMatrix matrix = (SysOrgMatrix) sysOrgMatrixService.findByPrimaryKey(matrixId);
        // 判断字段是否存在
        if (isColumnExist(matrix.getFdSubTable(), field)) {
            String sql = "SELECT " + field + " FROM " + matrix.getFdSubTable();
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            int count = 0;
            try {
                conn = getDataSource().getConnection();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Object obj = rs.getObject(1);
                    if (obj != null && StringUtil.isNotNull(obj.toString())) {
                        count++;
                    }
                }
            } finally {
                JdbcUtils.closeResultSet(rs);
                JdbcUtils.closeStatement(ps);
                JdbcUtils.closeConnection(conn);
            }
            return count;
        }
        return 0;
    }


}
