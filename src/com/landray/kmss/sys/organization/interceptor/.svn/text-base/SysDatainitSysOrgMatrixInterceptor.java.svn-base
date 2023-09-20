package com.landray.kmss.sys.organization.interceptor;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.admin.dbchecker.metadata.util.MetadataUtil;
import com.landray.kmss.sys.datainit.service.spring.SysDatainitDefaultInterceptor;
import com.landray.kmss.sys.organization.model.SysOrgMatrix;
import com.landray.kmss.sys.organization.model.SysOrgMatrixDataCate;
import com.landray.kmss.sys.organization.model.SysOrgMatrixRelation;
import com.landray.kmss.sys.organization.model.SysOrgMatrixVersion;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HibernateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysDatainitSysOrgMatrixInterceptor extends SysDatainitDefaultInterceptor {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(SysDatainitSysOrgMatrixInterceptor.class);

    private ISysOrgMatrixService sysOrgMatrixService;

    private ISysOrgMatrixService getSysOrgMatrixService() {
        if(sysOrgMatrixService == null) {
            sysOrgMatrixService = (ISysOrgMatrixService)SpringBeanUtil.getBean("sysOrgMatrixService");
        }
        return sysOrgMatrixService;
    }

    @Override
    public IBaseModel beforeSave(IBaseDao baseDao, IBaseModel baseModel) {
        if(baseModel != null && baseModel instanceof SysOrgMatrix){
            try {
                SysOrgMatrix sysOrgMatrix = (SysOrgMatrix) baseModel;
                SysOrgPerson user = UserUtil.getUser();
                List<String> matrixList = (List<String>) user.getCustomPropMap().get("matrixList");
                Map<Integer,String> columnMap = (Map<Integer,String>) user.getCustomPropMap().get("columnMap");
                user.getCustomPropMap().remove("matrixList");
                user.getCustomPropMap().remove("columnMap");
                baseDao.getHibernateTemplate().update(user);
                if(!CollectionUtils.isEmpty(matrixList) && !CollectionUtils.isEmpty(columnMap)){
                    createSubTable(sysOrgMatrix,baseDao);
                    for(String str : matrixList){
                        addMatrixData(sysOrgMatrix,str,columnMap,baseDao);
                    }
                }
            }catch (SQLException e){
                logger.error("插入矩阵数据错误", e);
            }catch (Exception e){
                logger.error("插入矩阵数据错误", e);
            }
        }
        return baseModel;
    }

    @Override
    public IBaseModel beforeUpdate(IBaseDao baseDao, IBaseModel baseModel) {
        if(baseModel != null && baseModel instanceof SysOrgMatrix){
            try {
                SysOrgMatrix sysOrgMatrix = (SysOrgMatrix) baseModel;
                //按照简单需求做，后期可能会加这些功能，代码先保留
                //SysOrgMatrix exist = getExistMatrix(sysOrgMatrix.getFdId());
                //checkDataCate(sysOrgMatrix.getFdDataCates(),exist.getFdDataCates());
                //checkRelation(sysOrgMatrix.getFdRelations(),exist.getFdRelations());
                //checkVersion(sysOrgMatrix.getFdVersions(),exist.getFdVersions());
                SysOrgPerson user = UserUtil.getUser();
                List<String> matrixList = (List<String>) user.getCustomPropMap().get("matrixList");
                Map<Integer,String> columnMap = (Map<Integer,String>) user.getCustomPropMap().get("columnMap");
                user.getCustomPropMap().remove("matrixList");
                user.getCustomPropMap().remove("columnMap");
                baseDao.getHibernateTemplate().update(user);
                if(!CollectionUtils.isEmpty(matrixList) && !CollectionUtils.isEmpty(columnMap)){
                    //updateTable(sysOrgMatrix,exist,baseDao);
                    //baseDao.getHibernateTemplate().merge(exist);
                    createSubTable(sysOrgMatrix,baseDao);
                    for(String str : matrixList){
                        //addOrUpdateMatrixData(sysOrgMatrix,str,columnMap,baseDao);
                        addMatrixData(sysOrgMatrix,str,columnMap,baseDao);
                    }
                }
            }catch (SQLException e){
                logger.error("插入矩阵数据错误", e);
            }catch (Exception e){
                logger.error("插入矩阵数据错误", e);
            }
        }
        return baseModel;
    }

    private void createSubTable(SysOrgMatrix sysOrgMatrix,IBaseDao baseDao) throws SQLException {
        DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
        Connection conn = dataSource.getConnection();
        ResultSet rs = null;
        try {
            String tableName = sysOrgMatrix.getFdSubTable();
            rs = conn.getMetaData().getTables(null, null, tableName, null);
            if(rs != null && rs.next()){
                //更新操作，由于需要更新表结构，新增或者删除列，还需要更新数据，直接删除表，再新增表重新插入最新的数据
                String dropSql = "drop table if exists " + tableName;
                executeUpdate(dropSql,null,baseDao);
            }
            executeUpdate(getCreateTableSql(sysOrgMatrix,baseDao),null,baseDao);
        }catch (SQLException e) {
            logger.error("创建矩阵数据表错误", e);
            conn.rollback();
        }catch (Exception e){
            logger.error("创建矩阵数据表错误", e);
            conn.rollback();
            e.printStackTrace();
        }finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeConnection(conn);
        }
    }

    private void addMatrixData(SysOrgMatrix sysOrgMatrix, String sqlValue, Map<Integer,String> columnMap,IBaseDao baseDao) throws SQLException {
        DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
        Connection conn = dataSource.getConnection();
        try {
            String tableName = sysOrgMatrix.getFdSubTable();
            StringBuilder sql = new StringBuilder();
            List<Object> list = ArrayUtil.asList(sqlValue.split("\\|"));
            Map<String,String> map = changeMapToString(columnMap,list);
            int paramSize = Integer.parseInt(map.get("paramSize"));
            String columnName = map.get("columnName");
            sql.append(" insert into ").append(tableName);
            if(StringUtil.isNotNull(columnName)){
                sql.append(" ( ").append(columnName).append(" ) ");
            }
            sql.append(" values( ");
            for (int i = 0; i < paramSize - 1; i++) {
                sql.append("?,");
            }
            sql.append("? )");
            executeUpdate(sql.toString(),list,baseDao);
        }catch (SQLException e) {
            logger.error("插入矩阵数据错误", e);
            conn.rollback();
        }catch (Exception e){
            logger.error("插入矩阵数据错误", e);
            conn.rollback();
            e.printStackTrace();
        }finally {
            JdbcUtils.closeConnection(conn);
        }
    }

    /**
     * 获取建表语句
     *
     * @param matrix
     * @return
     */
    private String getCreateTableSql(SysOrgMatrix matrix, IBaseDao baseDao) throws Exception{
        StringBuilder sql = new StringBuilder();
        // 子表字段总长度（数据库中字段总长度最大不能超过65535）
        int totalLength = 36;
        String columnType = getColumnType();

        sql.append("CREATE TABLE ").append(matrix.getFdSubTable()).append(" (").append("fd_id VARCHAR(36) NOT NULL, ")
                .append("fd_cate_id VARCHAR(36) , ").append("fd_version ").append(columnType).append("(")
                .append(5).append("), ");
        List<SysOrgMatrixRelation> list = matrix.getFdRelations();
        Collections.sort(list);
        for (SysOrgMatrixRelation relation : list) {
            if ("numRange".equals(relation.getFdType())) {
                String colType = HibernateUtil.getColumnType(Types.DOUBLE);
                // 数值区间（这里的数值是2个Double）
                // 开始
                sql.append(relation.getFdFieldName()).append(" ").append(colType).append(", ");
                // 结束
                sql.append(relation.getFdFieldName2()).append(" ").append(colType).append(", ");
            } else {
                // 常量类型，因为常量可以是任意字符，避免出现乱码，这里的类型需要根据数据库来决定
                String constantType = "constant".equals(relation.getFdType()) ? columnType : "VARCHAR";
                // 条件字段长度，默认为36个字符（条件字段只能存在一个元素的ID）
                int length = 36;
                if ("constant".equals(relation.getFdType()) || "cust".equals(relation.getFdMainDataType())) {
                    // 如果是常量，需要适当放宽一些，因为一个全角字符会占用6个字符
                    length = 200;
                }
                if (BooleanUtils.isTrue(relation.getFdIsResult())) {
                    // 结果字段，因为是多值，需要更多字符数
                    length = 1000;
                } else if ("cust".equals(relation.getFdMainDataType())) {
                    // 自定义主数据，数据是由页面输入，因此需要考虑乱码
                    constantType = columnType;
                }
                sql.append(relation.getFdFieldName()).append(" ").append(constantType).append("(").append(length).append("), ");
                totalLength += length;
            }
        }
        sql.append("PRIMARY KEY (fd_id))");
        // 如果字段总长度超过65535，是不能创建表的
        if (totalLength >= 65535) {
            throw new RuntimeException(
                    ResourceUtil.getString("sysOrgMatrix.create.table.excessive.length", "sys-organization"));
        }
        if (logger.isInfoEnabled()) {
            logger.info("创建矩阵数据表：" + sql.toString());
        }
        return sql.toString();
    }

    private String getColumnType() {
        String columnType = "VARCHAR";
        // 获取数据库方言
        String dialect = ResourceUtil.getKmssConfigString("hibernate.dialect");
        if (MetadataUtil.isSQLServer(dialect)) {
            columnType = "NVARCHAR";
        } else if (MetadataUtil.isOracle(dialect)) {
            columnType = "VARCHAR2";
        }
        return columnType;
    }

    /**
     * 执行更新
     *
     * @param sql
     * @throws Exception
     */
    private void executeUpdate(String sql, List<Object> params,IBaseDao baseDao) throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info("执行SQL：" + sql);
        }
        NativeQuery query = baseDao.getHibernateSession().createNativeQuery(sql.toString());
        int num = 0;
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if(param != null && !"null".equalsIgnoreCase(param.toString())){
                    query.setParameter(num, param.toString());
                    num++;
                }
            }
        }
        String tableName = ModelUtil.getModelTableName(baseDao.getModelName());
        if("sys_org_person".equalsIgnoreCase(tableName)){
            query.addSynchronizedQuerySpace("sys_org_person", "sys_org_element");
        }else{
            query.addSynchronizedQuerySpace(tableName);
        }
        int count = query.executeUpdate();
        if (logger.isInfoEnabled()) {
            logger.info("影响行数：" + count);
        }
    }

    /**
     * sql查询列map按照顺序转换成string
     * @param columnMap
     * @return
     */
    private Map<String,String> changeMapToString(Map<Integer,String> columnMap,List<Object> list){
        Map<String,String> map = new HashMap<>();
        int paramSize = 0;
        StringBuilder stringBuilder = new StringBuilder();
        if(!CollectionUtils.isEmpty(columnMap)){
            int size = columnMap.size();
            for (int i = 0; i < size; i++) {
                Object o = list.get(i);
                if(o != null && !"null".equalsIgnoreCase(o.toString())){
                    stringBuilder.append(columnMap.get(i)).append(",");
                    paramSize++;
                }
            }
        }
        String result = stringBuilder.toString();
        if(StringUtil.isNotNull(result)){
            result = result.substring(0,result.length()-1);
        }
        map.put("paramSize",String.valueOf(paramSize));
        map.put("columnName",result);
        return map;
    }

    /**
     * 新开事务，从数据库查询，避免缓存影响
     * @param matrixId
     * @return
     */
    private SysOrgMatrix getExistMatrix(String matrixId){
        SysOrgMatrix sysOrgMatrix = null;
        TransactionStatus status = TransactionUtils.beginNewTransaction();
        boolean commitFlag = true;
        try{
            sysOrgMatrix = (SysOrgMatrix) getSysOrgMatrixService().findByPrimaryKey(matrixId,SysOrgMatrix.class,true);
        }catch (Exception e){
            TransactionUtils.rollback(status);
            commitFlag = false;
        }finally {
            if(commitFlag){
                TransactionUtils.commit(status);
            }
        }
        return sysOrgMatrix;
    }

    /**
     * 追加数据，避免被删除
     * @param newList
     * @param oldList
     */
    private void checkRelation(List<SysOrgMatrixRelation> newList, List<SysOrgMatrixRelation> oldList){
        for (SysOrgMatrixRelation oldRelation : oldList) {
            boolean flag = false;
            for(SysOrgMatrixRelation newRelation : newList){
                if(newRelation.getFdId().equals(oldRelation.getFdId())){
                    flag = true;
                }
            }
            if(!flag){
                newList.add(oldRelation);
            }
        }
    }

    /**
     * 追加数据，避免被删除
     * @param newList
     * @param oldList
     */
    private void checkDataCate(List<SysOrgMatrixDataCate> newList, List<SysOrgMatrixDataCate> oldList){
        for (SysOrgMatrixDataCate oldDataCate : oldList) {
            boolean flag = false;
            for(SysOrgMatrixDataCate newDatacate : newList){
                if(newDatacate.getFdId().equals(oldDataCate.getFdId())){
                    flag = true;
                }
            }
            if(!flag){
                newList.add(oldDataCate);
            }
        }
    }

    /**
     * 追加数据，避免被删除
     * @param newList
     * @param oldList
     */
    private void checkVersion(List<SysOrgMatrixVersion> newList, List<SysOrgMatrixVersion> oldList){
        for (SysOrgMatrixVersion oldVersion : oldList) {
            boolean flag = false;
            for(SysOrgMatrixVersion newVersion : newList){
                if(newVersion.getFdName().equals(oldVersion.getFdName())){
                    if(!newVersion.getFdId().equals(oldVersion.getFdId())){
                        newVersion.setFdId(oldVersion.getFdId());
                    }
                    flag = true;
                }
            }
            if(!flag){
                newList.add(oldVersion);
            }
        }
    }

    /**
     * 更新表结果
     * @param newMatrix
     * @param oldMatrix
     * @param baseDao
     * @throws Exception
     */
    private void updateTable(SysOrgMatrix newMatrix,SysOrgMatrix oldMatrix,IBaseDao baseDao) throws Exception{
        String columnType = getColumnType();
        for (SysOrgMatrixRelation newRelation : newMatrix.getFdRelations()) {
            boolean flag = false;
            for(SysOrgMatrixRelation oldRelation : oldMatrix.getFdRelations()){
                if(oldRelation.getFdId().equals(newRelation.getFdId())){
                    flag = true;
                }
            }
            if(!flag){
                String constantType = "VARCHAR(1000)";
                if (!BooleanUtils.isTrue(newRelation.getFdIsResult())) {
                    // 常量和自定义主数据需要处理乱码
                    constantType = ("constant".equals(newRelation.getFdType()) || "cust".equals(newRelation.getFdMainDataType()))
                            ? columnType + "(200)" : "VARCHAR(36)";
                }
                if ("numRange".equals(newRelation.getFdType())) {
                    String colType = HibernateUtil.getColumnType(Types.DOUBLE);
                    // 数值区间（这里的数值是2个Double）
                    executeUpdate("ALTER TABLE " + newMatrix.getFdSubTable() + " ADD " + newRelation.getFdFieldName() + " " + colType, null,baseDao);
                    executeUpdate("ALTER TABLE " + newMatrix.getFdSubTable() + " ADD " + newRelation.getFdFieldName2() + " " + colType, null,baseDao);
                } else {
                    // 新增字段
                    // ALTER TABLE table_name ADD column_name VARCHAR(20) NULL
                    executeUpdate("ALTER TABLE " + newMatrix.getFdSubTable() + " ADD " + newRelation.getFdFieldName() + " " + constantType + " NULL", null,baseDao);
                }
            }
        }
    }

    private void addOrUpdateMatrixData(SysOrgMatrix sysOrgMatrix, String sqlValue, Map<Integer,String> columnMap,IBaseDao baseDao) throws SQLException {
        DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
        Connection conn = dataSource.getConnection();
        try {
            String tableName = sysOrgMatrix.getFdSubTable();
            StringBuilder sql = new StringBuilder();
            List<Object> list = new ArrayList<>(ArrayUtil.asList(sqlValue.split("\\|")));
            String fdId = list.get(0).toString();
            Map<String,String> map = changeMapToString(columnMap,list);
            int paramSize = Integer.parseInt(map.get("paramSize"));
            String columnName = map.get("columnName");
            boolean updateFlag = checkExistMatrixData(fdId,tableName,baseDao);
            if(updateFlag){
                //更新
                String[] column = columnName.split(",");
                sql.append(" update ").append(tableName).append(" set ");
                for (int i = 0; i < column.length - 1; i++) {
                    sql.append(column[i]).append(" = ? ,");
                }
                sql.append(column[column.length-1]).append(" = ? ").append(" where fd_id = ?");
                list.add(fdId);
            }else{
                //新增
                sql.append(" insert into ").append(tableName);
                if(StringUtil.isNotNull(columnName)){
                    sql.append(" ( ").append(columnName).append(" ) ");
                }
                sql.append(" values( ");
                for (int i = 0; i < paramSize - 1; i++) {
                    sql.append("?,");
                }
                sql.append("? )");
            }
            executeUpdate(sql.toString(),list,baseDao);
        }catch (SQLException e) {
            logger.error("插入矩阵数据错误", e);
            conn.rollback();
        }catch (Exception e){
            logger.error("插入矩阵数据错误", e);
            conn.rollback();
            e.printStackTrace();
        }finally {
            JdbcUtils.closeConnection(conn);
        }
    }

    /**
     * 获取原来的矩阵关系ID集合
     *
     * @param fdId
     * @param tableName
     * @return
     * @throws Exception
     */
    private boolean checkExistMatrixData(String fdId,String tableName,IBaseDao baseDao) throws Exception {
        boolean flag = false;
        TransactionStatus status = TransactionUtils.beginNewTransaction();
        boolean commitFlag = true;
        try{
            String sql = "SELECT fd_id FROM " + tableName + " WHERE fd_id = ?";
            List<String> params = new ArrayList<String>();
            params.add(fdId);
            List<Object> list = executeQuery(sql, params,baseDao);
            if (list != null && !list.isEmpty()) {
                flag = true;
            }
        }catch (Exception e){
            TransactionUtils.rollback(status);
            commitFlag = false;
        }finally {
            if(commitFlag){
                TransactionUtils.commit(status);
            }
        }
        return flag;
    }

    /**
     * 执行查询
     *
     * @param sql
     * @param params
     * @return
     * @throws Exception
     */
    private List<Object> executeQuery(String sql, List<String> params,IBaseDao baseDao)
            throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info("执行SQL：" + sql);
            logger.info("执行SQL参数：" + params);
        }
        NativeQuery query = baseDao.getHibernateSession().createNativeQuery(sql.toString());
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                String param = params.get(i);
                query.setParameter(i, param);
            }
        }
        return query.list();
    }
}
