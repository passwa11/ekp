package com.landray.kmss.util;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.admin.dbchecker.metadata.util.MetadataUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.loader.HibernateConfiguration;
import com.landray.kmss.sys.config.loader.KmssHibernateLocalSessionFactoryBean;
import com.landray.kmss.sys.hibernate.spi.ConnectionWrapper;
import com.landray.kmss.sys.hibernate.spi.HibernateWrapper;
import com.landray.kmss.sys.hibernate.spi.KmssMetadataSources;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Index;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Table;
import org.hibernate.query.NativeQuery;
import org.hibernate.tool.schema.internal.StandardIndexExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * 根据型模型Class获取表名与属性对的字段名
 *
 * @author 潘永辉
 */
public class HibernateUtil {

    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    private static Configuration hibernateConf;

    private static Configuration getHibernateConf() {
        if (hibernateConf == null) {
            return new Configuration();
        }
        return hibernateConf;
    }

    public static PersistentClass getPersistentClass(Class<?> clazz) {
        synchronized (HibernateUtil.class) {
            PersistentClass pc = HibernateWrapper.getClassMapping(clazz.getName());
            if (pc == null) {
                hibernateConf = getHibernateConf().addClass(clazz);
                pc = HibernateWrapper.getClassMapping(clazz.getName());
            }
            return pc;
        }
    }

    /**
     * 获取表名
     *
     * @param clazz
     * @return
     */
    public static String getTableName(Class<?> clazz) {
        return getPersistentClass(clazz).getTable().getName();
    }

    /**
     * 根据属性名称获取字段名
     *
     * @param clazz
     * @param propertyName
     * @return
     */
    public static String getColumnName(Class<?> clazz, String propertyName) {
        Column column = getColumn(clazz, propertyName);
        if (column != null) {
            return column.getName();
        }
        return null;
    }

    /**
     * 获取数据库字段
     *
     * @param clazz
     * @param propertyName
     * @return
     */
    public static Column getColumn(Class<?> clazz, String propertyName) {
        PersistentClass persistentClass = getPersistentClass(clazz);
        Property property = persistentClass.getProperty(propertyName);
        Iterator<?> it = property.getColumnIterator();
        if (it.hasNext()) {
            Column column = (Column) it.next();
            return column;
        }
        return null;
    }

    private static Dialect cachedDialect = null;

    private static Dialect getCurDialect() {
        if (cachedDialect == null) {
            KmssHibernateLocalSessionFactoryBean bean =
                    (KmssHibernateLocalSessionFactoryBean) SpringBeanUtil.getBean("&sessionFactory");
            Configuration configuration = bean.getConfiguration();
            cachedDialect = Dialect.getDialect(configuration.getProperties());
        }
        return cachedDialect;
    }

    /**
     * 获取boolean值对应的sql、hql语句中使用的字符串表达，某些数据库使用0|1，某些数据库使用true|false
     *
     * @param value
     * @return
     */
    public static String toBooleanValueString(boolean value) {
        if (cachedDialect == null) {
            getCurDialect();
        }
        return cachedDialect.toBooleanValueString(value);
    }

    /**
     * @param sqlType java.sql.Types.*
     * @return
     */
    public static String getColumnType(int sqlType) {
        if (cachedDialect == null) {
            getCurDialect();
        }
        String typeName = cachedDialect.getTypeName(sqlType);
        return typeName;
    }

    /**
     * @param sqlType   java.sql.Types.*
     * @param length    The datatype length
     * @param precision The datatype precision
     * @param scale     The datatype scale
     * @return
     */
    public static String getColumnType(int sqlType, int length, int precision, int scale) {
        if (cachedDialect == null) {
            getCurDialect();
        }
        String typeName = cachedDialect.getTypeName(sqlType, length, precision, scale);
        return typeName;
    }

    // ================== 以下为生成复合索引功能 ====================

    private static KmssHibernateLocalSessionFactoryBean sessionFactory;

    private static KmssMetadataSources metadataSources;

    private static StandardServiceRegistryImpl serviceRegistry;

    private static StandardIndexExporter indexExporter;

    private static IBaseDao baseDao;

    private static HibernateConfiguration hibernateConfiguration;

    private static HibernateConfiguration getHibernateConfiguration() {
        if (hibernateConfiguration == null) {
            synchronized (HibernateUtil.class) {
                if (hibernateConfiguration == null) {
                    hibernateConfiguration = new HibernateConfiguration();
                    // 数据库连接信息
                    hibernateConfiguration.setProperty(Environment.DIALECT, ResourceUtil.getKmssConfigString(Environment.DIALECT));
                    hibernateConfiguration.setProperty(Environment.URL, ResourceUtil.getKmssConfigString(Environment.URL));
                    hibernateConfiguration.setProperty(Environment.USER, ResourceUtil.getKmssConfigString("hibernate.connection.userName"));
                    // Oracle数据库
                    if (MetadataUtil.isOracle(hibernateConfiguration.getProperty(Environment.DIALECT))) {
                        String url = hibernateConfiguration.getProperty(Environment.URL);
                        String user = hibernateConfiguration.getProperty(Environment.USER);
                        if (StringUtil.isNotNull(url) && StringUtil.isNotNull(user)) {
                            hibernateConfiguration.setProperty(Environment.DEFAULT_SCHEMA, user);
                        }
                    }
                }
            }
        }
        return hibernateConfiguration;
    }


    /**
     * 获取SessionFactory
     *
     * @return
     */
    public static KmssHibernateLocalSessionFactoryBean getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (HibernateUtil.class) {
                if (sessionFactory == null) {
                    sessionFactory = (KmssHibernateLocalSessionFactoryBean) SpringBeanUtil.getBean("&sessionFactory");
                }
            }
        }
        return sessionFactory;
    }

    /**
     * 获取注册服务
     *
     * @return
     */
    public static StandardServiceRegistryImpl getServiceRegistry() {
        if (serviceRegistry == null) {
            synchronized (HibernateUtil.class) {
                if (serviceRegistry == null) {
                    // 构建Metadata
                    Properties properties = getSessionFactory().getConfiguration().getProperties();
                    BootstrapServiceRegistryBuilder builder = new BootstrapServiceRegistryBuilder();
                    StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder(builder.build()).applySettings(properties);
                    serviceRegistry = (StandardServiceRegistryImpl) registryBuilder.build();
                }
            }
        }
        return serviceRegistry;
    }

    /**
     * 获取MetadataSources
     *
     * @return
     */
    public static KmssMetadataSources getMetadataSources() {
        if (metadataSources == null) {
            synchronized (HibernateUtil.class) {
                if (metadataSources == null) {
                    // 构建MetadataSources
                    metadataSources = new KmssMetadataSources(getServiceRegistry());
                }
            }
        }
        return metadataSources;
    }

    /**
     * 构建索引生成器
     *
     * @return
     */
    public static StandardIndexExporter getIndexExporter() {
        if (indexExporter == null) {
            synchronized (HibernateUtil.class) {
                if (indexExporter == null) {
                    // 构建索引生成器
                    indexExporter = new StandardIndexExporter(Dialect.getDialect(getSessionFactory().getConfiguration().getProperties()));
                }
            }
        }
        return indexExporter;
    }

    /**
     * 获取BaseDao
     *
     * @return
     */
    public static IBaseDao getBaseDao() {
        if (baseDao == null) {
            synchronized (HibernateUtil.class) {
                if (baseDao == null) {
                    baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
                }
            }
        }
        return baseDao;
    }

    /**
     * 生成创建索引SQL（同时支持"表名与字段名" 和 "全类名与属性名"，后者依赖数据字典）
     *
     * @param tableName   表名（或 ModelName）
     * @param columnNames 多个字段名（或 属性名）
     * @return 返回创建索引的SQL
     * @throws Exception
     */
    public static String genIndexSql(String tableName, String... columnNames) {
        if (StringUtil.isNull(tableName) || columnNames == null || columnNames.length < 1) {
            return null;
        }
        // 如果是全类名，需要做一次转换
        Map<String, String[]> map = convertTableName(tableName, columnNames);
        Map.Entry<String, String[]> entry = map.entrySet().iterator().next();
        tableName = entry.getKey();
        columnNames = entry.getValue();
        // 获取随机字符
        String random = UUID.randomUUID().toString().replaceAll("-", "");
        Index index = new Index();
        // 设置一个随机索引名称
        if (columnNames.length > 1) {
            // 复合索引
            index.setName("IDX_COMPLE_" + random.substring(0, 18));
        } else {
            // 单例索引
            index.setName("IDX_" + random.substring(0, 25));
        }
        index.setTable(new Table(tableName));
        boolean hasColumn = false;
        for (String columnName : columnNames) {
            if (StringUtil.isNull(columnName)) {
                continue;
            }
            index.addColumn(new Column(columnName));
            hasColumn = true;
        }
        if (!hasColumn) {
            if (logger.isWarnEnabled()) {
                logger.warn("无法获取字段，tableName: {}， columnNames: {}", tableName, ArrayUtil.toStringArray(columnNames));
            }
            return null;
        }
        String[] sqls = getIndexExporter().getSqlCreateStrings(index, getMetadataSources().buildMetadata());
        if (sqls != null && sqls.length > 0) {
            return sqls[0];
        }
        if (logger.isWarnEnabled()) {
            logger.warn("无法生成索引SQL，tableName: {}， columnNames: {}", tableName, ArrayUtil.toStringArray(columnNames));
        }
        return null;
    }

    /**
     * 创建索引（同时支持"表名与字段名" 和 "全类名与属性名"，后者依赖数据字典）
     *
     * @param tableName   表名（或 ModelName）
     * @param columnNames 多个字段名（或 属性名）
     */
    public static void createIndexSql(String tableName, String... columnNames) {
        String sql = null;
        try {
            // 如果是全类名，需要做一次转换
            Map<String, String[]> map = convertTableName(tableName, columnNames);
            Map.Entry<String, String[]> entry = map.entrySet().iterator().next();
            tableName = entry.getKey();
            columnNames = entry.getValue();
            // 判断索引是否存在，不需要重复创建索引
            if (isExists(tableName, columnNames)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("索引已存在，无需创建【tableName: {}， columnNames: {}】", tableName, ArrayUtil.toStringArray(columnNames));
                }
                return;
            }
            sql = genIndexSql(tableName, columnNames);
            if (StringUtil.isNotNull(sql)) {
                executeUpdate(sql, null);
                if (logger.isDebugEnabled()) {
                    logger.debug("索引创建成功：{}", sql);
                }
            } else {
                logger.error("无法创建索引，请检查参数是否正确【tableName: {}， columnNames: {}】", tableName, ArrayUtil.toStringArray(columnNames));
            }
        } catch (Exception e) {
            if (StringUtil.isNotNull(sql)) {
                logger.error("创建索引失败：" + sql, e);
            } else {
                logger.error("创建索引失败", e);
            }
        }
    }

    /**
     * 全类名与表名（属性名与字段名）转换
     *
     * @param tableName   表名（或 ModelName）
     * @param columnNames 多个字段名（或 属性名）
     * @return
     */
    private static Map<String, String[]> convertTableName(String tableName, String... columnNames) {
        // 先查询数据字典（兼容模式）
        SysDictModel model = SysDataDict.getInstance().getModel(tableName);
        final String _tableName;
        final String[] _columnNames;
        if (model != null) {
            _tableName = model.getTable();
            String[] props = new String[columnNames.length];
            for (int i = 0; i < columnNames.length; i++) {
                String columnName = columnNames[i];
                if (StringUtil.isNull(columnName)) {
                    continue;
                }
                SysDictCommonProperty property = model.getPropertyMap().get(columnName);
                if (property == null || StringUtil.isNull(property.getColumn())) {
                    throw new RuntimeException("属性[" + columnName + "]未找到或未配置column属性，请检查数据字典：" + tableName);
                }
                props[i] = property.getColumn();
            }
            _columnNames = props;
        } else {
            _tableName = tableName;
            _columnNames = columnNames;
        }
        return new HashMap<String, String[]>(1) {{
            put(_tableName, _columnNames);
        }};
    }

    /**
     * 批量判断索引是否存在
     *
     * @param tables 格式为： {"sys_news_main": ["docSubject", "fd_description"]}
     * @return 返回判断结果，格式为：{"sys_news_main": true}
     */
    public static Map<String, Boolean> isExists(Map<String, String[]> tables) {
        Map<String, Boolean> result = new HashMap<>(16);
        Connection connection = null;
        try {
            String catalog = getHibernateConfiguration().getProperty(Environment.DEFAULT_CATALOG);
            String schema = getHibernateConfiguration().getProperty(Environment.DEFAULT_SCHEMA);
            SessionFactoryImplementor sessionFactoryWraper = (SessionFactoryImplementor) getSessionFactory().getObject();
            connection = ConnectionWrapper.getInstance().getConnection(sessionFactoryWraper.openSession());
            DatabaseMetaData metaData = connection.getMetaData();
            if (metaData.storesUpperCaseIdentifiers()) {
                // 全大写
                catalog = catalog != null ? catalog.toUpperCase() : null;
                schema = schema != null ? schema.toUpperCase() : null;
            } else if (metaData.storesLowerCaseIdentifiers()) {
                // 全小写
                catalog = catalog != null ? catalog.toLowerCase() : null;
                schema = schema != null ? schema.toLowerCase() : null;
            }
            ResultSet rs = null;
            for (String table : tables.keySet()) {
                String tableName = table;
                String[] columnNames = tables.get(table);
                // 如果是全类名，需要做一次转换
                Map<String, String[]> map = convertTableName(tableName, columnNames);
                Map.Entry<String, String[]> entry = map.entrySet().iterator().next();
                tableName = entry.getKey();
                columnNames = entry.getValue();
                if (StringUtil.isNull(tableName) || columnNames == null || columnNames.length < 1) {
                    result.put(table, false);
                } else {
                    try {
                        if (metaData.storesUpperCaseIdentifiers()) {
                            // 全大写
                            tableName = tableName.toUpperCase();
                        } else if (metaData.storesLowerCaseIdentifiers()) {
                            // 全小写
                            tableName = tableName.toLowerCase();
                        }
                        rs = metaData.getIndexInfo(catalog, schema, tableName, false, false);
                        List<String> columnList = Arrays.asList(columnNames);
                        Map<String, List<String>> mapping = new HashMap<>(16);
                        while (rs.next()) {
                            String index_name = rs.getString("INDEX_NAME");
                            String column_name = rs.getString("COLUMN_NAME");
                            index_name = index_name != null ? index_name.toLowerCase() : null;
                            column_name = column_name != null ? column_name.toLowerCase() : null;
                            if (!"PRIMARY".equals(index_name) && columnList.contains(column_name)) {
                                // 找到索引字段
                                if (!mapping.containsKey(index_name)) {
                                    mapping.put(index_name, new ArrayList<String>());
                                }
                                List<String> list = mapping.get(index_name);
                                list.add(column_name);
                            }
                        }
                        // 解析索引映射关系，判断该索引是否存在
                        boolean exists = false;
                        for (String indexName : mapping.keySet()) {
                            List<String> list = mapping.get(indexName);
                            // 索引的字段数量及顺序完全匹配
                            if (isEqualCollection(list, columnList)) {
                                exists = true;
                                break;
                            }
                        }
                        result.put(table, exists);
                    } finally {
                        JdbcUtils.closeResultSet(rs);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("查询索引失败：", e);
        } finally {
            JdbcUtils.closeConnection(connection);
        }
        return result;
    }

    /**
     * 判断索引是否存在
     *
     * @param tableName   表名（或 ModelName）
     * @param columnNames 多个字段名（或 属性名）
     * @return
     */
    public static boolean isExists(String tableName, String... columnNames) {
        Map<String, Boolean> result = isExists(new HashMap<String, String[]>(1) {{
            put(tableName, columnNames);
        }});
        return BooleanUtils.isTrue(result.get(tableName));
    }

    /**
     * 比较2个集合是否一致，顺序也要保持一致
     *
     * @param list1
     * @param list2
     * @return
     */
    private static boolean isEqualCollection(List<String> list1, List<String> list2) {
        boolean equal = true;
        if (list1.size() == list2.size()) {
            for (int i = 0; i < list1.size(); i++) {
                if (!list1.get(i).equals(list2.get(i))) {
                    equal = false;
                    break;
                }
            }
        } else {
            equal = false;
        }
        return equal;
    }

    /**
     * 执行SQL
     *
     * @param sql    待执行的SQL（参数通过?占位）
     * @param params 执行SQL的参数（参数顺序与SQL中的?保持一致）
     * @return 返回影响的行数
     * @throws Exception
     */
    public static int executeUpdate(String sql, Object... params) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("执行SQL：{}", sql);
        }
        NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(sql);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                Object param = params[i];
                query.setParameter(i, param);
            }
        }
        int count = query.executeUpdate();
        if (logger.isDebugEnabled()) {
            logger.debug("影响行数：{}", count);
        }
        return count;
    }

}
