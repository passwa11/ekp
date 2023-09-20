package com.landray.kmss.util;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.admin.dbchecker.metadata.util.MetadataUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictListProperty;
import com.landray.kmss.sys.profile.model.SysCommonSensitiveConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.Session;
import org.hibernate.cfg.Environment;
import org.hibernate.query.Query;
import org.slf4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HQL语句拼装的常用类。<br>
 * 作用范围：所有代码，直接调用静态方法。
 *
 * @author 叶中奇
 * @version 1.0 2006-6-16
 */
public class HQLUtil {
    protected static Logger logger = org.slf4j.LoggerFactory.getLogger(HQLUtil.class);
    private static final AtomicLong atomicLong = new AtomicLong(0);

    /**
     * @return 自增的ID号
     */
    public static long getFieldIndex() {
        atomicLong.compareAndSet(10000, 0); // 如果累加到了10000，重置为0
        return atomicLong.getAndIncrement();
    }

    /**
     * 返回一个对象的hashCode的十六进制字符串形式。
     *
     * @param x
     * @return
     */
    public static String getIdentityHexString(String x) {
        return StringUtil.toHexString(x.getBytes());
    }

    /**
     * 获取自动fetch的信息，当使用order by时经常需要调用该函数fetch相关信息
     *
     * @param hqlInfo
     * @return
     */
    public static String getAutoFetchInfo(HQLInfo hqlInfo) {
        if (!hqlInfo.isAutoFetch()) {
            return "";
        }
        if (StringUtil.isNull(hqlInfo.getOrderBy())) {
            return "";
        }
        String[] info = hqlInfo.getOrderBy().split("[^A-Za-z0-9_\\.]+");
        StringBuffer rtnVal = new StringBuffer();
        for (int i = 0; i < info.length; i++) {
            info[i] = info[i].trim();
            if (info[i].length() == 0) {
                continue;
            }
            int j = info[i].indexOf(" ");
            if (j > -1) {
                info[i] = info[i].substring(0, j);
            }
            String[] infoArr = info[i].split("\\.");
            info[i] = infoArr[0];
            for (j = 1; j < infoArr.length - 1; j++) {
                info[i] = info[i] + "." + infoArr[j];
                if (rtnVal.indexOf("left join fetch " + info[i] + " ") >= 0) {
                    continue;
                }
                rtnVal.append("left join fetch " + info[i] + " ");
            }
        }
        return rtnVal.toString();
    }

    /**
     * 构造in 预编译语句，若valueList超过1000时，该函数会自动拆分成多个in语句
     *
     * @param item
     * @param valueList
     * @return item in (valueList)
     */
    @Deprecated
    public static HQLWrapper buildPreparedLogicIN(String item, List<?> valueList) {
        return buildPreparedLogicIN(item, String.valueOf(getFieldIndex()),
                valueList);
    }

    public static HQLWrapper buildPreparedLogicIN(String item, String alias,
                                                  List<?> valueList) {
        String param = alias;
        if (StringUtil.isNull(param)) {
            param = getIdentityHexString(item);
        }
        List<?> valueCopy = new ArrayList<Object>(valueList);
        HQLWrapper hqlWrapper = new HQLWrapper();
        int n = (valueCopy.size() - 1) / 1000;
        StringBuffer whereBlockTmp = new StringBuffer();
        List<?> tmpList;
        for (int k = 0; k <= n; k++) {
            int size = k == n ? valueCopy.size() : (k + 1) * 1000;
            if (k > 0) {
                whereBlockTmp.append(" or ");
            }
            String para = "kmss_in_" + param + "_" + k;
            whereBlockTmp.append(item + " in (:" + para + ")");
            tmpList = valueCopy.subList(k * 1000, size);
            HQLParameter hqlParameter = new HQLParameter(para, tmpList);
            hqlWrapper.setParameter(hqlParameter);
        }
        if (n > 0) {
            hqlWrapper.setHql("(" + whereBlockTmp.toString() + ")");
        } else {
            hqlWrapper.setHql(whereBlockTmp.toString());
        }
        return hqlWrapper;
    }

    /**
     * 构造in语句，若valueList超过1000时，该函数会自动拆分成多个in语句
     *
     * @param item
     * @param valueList
     * @return item in (valueList)
     */
    public static <V> String buildLogicIN(String item, List<V> valueList) {
        List<V> valueListCopy = new ArrayList<>();
        if (valueList.isEmpty()) {
            valueListCopy.add(null);
        } else {
            valueListCopy.addAll(valueList);
        }
        int n = (valueListCopy.size() - 1) / 1000;
        StringBuffer rtnStr = new StringBuffer();
        Object obj = valueListCopy.get(0);
        boolean isString = false;
        if (obj instanceof Character || obj instanceof String) {
            isString = true;
        }
        String tmpStr;
        for (int i = 0; i <= n; i++) {
            int size = i == n ? valueListCopy.size() : (i + 1) * 1000;
            if (i > 0) {
                rtnStr.append(" or ");
            }
            rtnStr.append(item + " in (");
            if (isString) {
                StringBuffer tmpBuf = new StringBuffer();
                for (int j = i * 1000; j < size; j++) {
                    tmpStr = valueListCopy.get(j).toString().replaceAll("'", "''");
                    tmpBuf.append(",'").append(tmpStr).append("'");
                }
                tmpStr = tmpBuf.substring(1);
            } else {
                tmpStr = valueListCopy.subList(i * 1000, size).toString();
                tmpStr = tmpStr.substring(1, tmpStr.length() - 1);
            }
            rtnStr.append(tmpStr);
            rtnStr.append(")");
        }
        if (n > 0) {
            return "(" + rtnStr.toString() + ")";
        } else {
            return rtnStr.toString();
        }
    }

    /**
     * 取得Model列表的HQL的In查询格式数据
     *
     * @param modelList
     * @return
     */
    public static String buildModelIds(List modelList) {
        if (modelList != null && !modelList.isEmpty()) {
            StringBuffer rtnVal = new StringBuffer();
            for (int i = 0; i < modelList.size(); i++) {
                IBaseModel baseModel = (IBaseModel) modelList.get(i);
                rtnVal.append("'").append(baseModel.getFdId());
                if (i != modelList.size() - 1) {
                    rtnVal.append("',");
                } else {
                    rtnVal.append("'");
                }
            }
            return rtnVal.toString();
        } else {
            return null;
        }
    }

    /**
     * 从数据库中读取ManyToMany的映射表
     *
     * @param hbmSession Hibernate的Session
     * @param sql        SQL语句
     * @return 映射表，可提供给fetchManyToManyIDList使用
     */
    public static Map buildManyToManyIDMap(Session hbmSession, String sql) {
        Map rtnMap = new HashMap();
        List rtnList = hbmSession.createNativeQuery(sql).list();
        for (int i = 0; i < rtnList.size(); i++) {
            Object[] ids = (Object[]) rtnList.get(i);
            if (rtnMap.containsKey(ids[0])) {
                List valueList = (List) rtnMap.get(ids[0]);
                if (!valueList.contains(ids[1])) {
                    valueList.add(ids[1]);
                }
            } else {
                List valueList = new ArrayList();
                valueList.add(ids[1]);
                rtnMap.put(ids[0], valueList);
            }
        }
        return rtnMap;
    }

    /**
     * 从数据库中读取ManyToMany的映射表，并构造双向的映射表
     *
     * @param hbmSession Hibernate的Session
     * @param sql        SQL语句
     * @return [0]正向映射表，[1]反向映射表
     */
    public static Map[] buildManyToManyIDBidirectionalMap(Session hbmSession,
                                                          String sql) {
        Map[] rtnMap = {new HashMap(), new HashMap()};
        List rtnList = hbmSession.createNativeQuery(sql).list();
        for (int i = 0; i < rtnList.size(); i++) {
            Object[] ids = (Object[]) rtnList.get(i);
            if (rtnMap[0].containsKey(ids[0])) {
                List valueList = (List) rtnMap[0].get(ids[0]);
                if (!valueList.contains(ids[1])) {
                    valueList.add(ids[1]);
                }
            } else {
                List valueList = new ArrayList();
                valueList.add(ids[1]);
                rtnMap[0].put(ids[0], valueList);
            }
            if (rtnMap[1].containsKey(ids[1])) {
                List valueList = (List) rtnMap[1].get(ids[1]);
                if (!valueList.contains(ids[0])) {
                    valueList.add(ids[0]);
                }
            } else {
                List valueList = new ArrayList();
                valueList.add(ids[0]);
                rtnMap[1].put(ids[1], valueList);
            }
        }
        return rtnMap;
    }

    /**
     * 将相关的ID从ManyToMany的映射表中读出，再添加到ID列表中
     *
     * @param idList 原ID列表
     * @param idMap  ManyToMany的映射表，可用buildManyToManyIDMap获取
     * @return 原ID列表+从ManyToMany映射表中扩充的ID列表
     */
    public static List fetchManyToManyIDList(List idList, Map idMap) {
        List results = new ArrayList();
        for (int i = 0; i < idList.size(); i++) {
            fetchManyToManyIDList((String) idList.get(i), results, idMap);
        }
        ArrayUtil.concatTwoList(idList, results);
        return results;
    }

    /**
     * 将id以及id相关的ID列表（从ManyToMany的映射表中查出）添加到results列表中
     *
     * @param id      需要添加的ID
     * @param results 已有的ID列表
     * @param idMap   ManyToMany的映射表，可用buildManyToManyIDMap获取
     */
    private static void fetchManyToManyIDList(String id, List results, Map idMap) {
        if (results.contains(id)) {
            return;
        }
        results.add(id);
        if (idMap.containsKey(id)) {
            List idList = (List) idMap.get(id);
            for (int i = 0; i < idList.size(); i++) {
                fetchManyToManyIDList((String) idList.get(i), results, idMap);
            }
        }
    }

    /**
     * 根据域模型名称和查询的属性，判断是否应该添加left join语句 <br>
     * 样例：输入<br>
     * modelName=com.landray.kmss.sys.organization.model.SysOrgPerson
     * propertyName=hbmPosts.fdName<br>
     * 返回：<br>
     * [0]="kmss_list_field_0.fdName";<br>
     * [1]=" left join sysOrgPerson.hbmPosts kmss_list_field_0"
     *
     * @param modelName
     * @param propertyName
     * @return 返回值中[0]是left join语句，[1]是封装后的属性
     */
    public static String[] formatPropertyWithJoin(String modelName,
                                                  String propertyName) {
        return formatPropertyWithJoin(modelName, propertyName, null);
    }

    /**
     * 根据域模型名称和查询的属性，判断是否应该添加left join语句 <br>
     * 样例：输入<br>
     * modelName=com.landray.kmss.sys.organization.model.SysOrgPerson
     * propertyName=hbmPosts.fdName<br>
     * startProperty=sysOrgPerson<br>
     * 返回：<br>
     * [0]="kmss_list_field_0.fdName";<br>
     * [1]=" left join sysOrgPerson.hbmPosts kmss_list_field_0"
     *
     * @param modelName
     * @param propertyName
     * @param startProperty
     * @return 返回值中[0]是left join语句，[1]是封装后的属性
     */
    public static String[] formatPropertyWithJoin(String modelName,
                                                  String propertyName, String startProperty) {
        String[] rtnVal = {"", ""};
        SysDataDict dataDict = SysDataDict.getInstance();
        String[] propertyArray = propertyName.split("\\.");
        String orgModelName = modelName;
        String tmpProperty = startProperty == null ? ModelUtil
                .getModelTableName(modelName) : startProperty;
        for (int i = 0; i < propertyArray.length; i++) {
            SysDictCommonProperty property = (SysDictCommonProperty) dataDict
                    .getModel(modelName).getPropertyMap().get(propertyArray[i]);
            if (property == null) {
                throw new KmssRuntimeException(new KmssMessage(
                        "error.datadict.propertyUndefined", orgModelName,
                        propertyName));
            }
            if (property instanceof SysDictListProperty) {
                long index = getFieldIndex();
                rtnVal[1] += " left join " + tmpProperty + "."
                        + propertyArray[i] + " kmss_list_field_" + index + " ";
                tmpProperty = "kmss_list_field_" + index;
            } else {
                tmpProperty += "." + propertyArray[i];
            }
            modelName = property.getType();
        }
        rtnVal[0] = tmpProperty;
        return rtnVal;
    }

    /**
     * 将“aaa,bbb,ccc”或“aaa;bbb;ccc”的字符串转换为SQL使用的“'aaa','bbb','ccc'”
     *
     * @param str
     * @return
     */
    public static String replaceToSQLString(String str) {
        if (str == null) {
            return null;
        }
        String rtnVal = str.trim();
        if (rtnVal.length() == 0) {
            return str;
        }
        rtnVal = rtnVal.replaceAll("\\s*[,;]\\s*", "','");
        return "'" + rtnVal + "'";
    }

    /**
     * 返回当前使用数据库的uuid生成函数
     *
     * @return
     */
    public static String getSqlIDGenerationFunction() {
        String retval = "sys_guid()"; // default oracle
        String sqlDialet = ResourceUtil
                .getKmssConfigString("hibernate.dialect");
        if (sqlDialet.contains("SQLServer")) {
            retval = "newid()";
        } else if (sqlDialet.contains("Oracle")) {
            retval = "sys_guid()";
        } else if (sqlDialet.contains("DB2")) {
            retval = "GENERATE_UNIQUE()";
        } else if (sqlDialet.contains("MySQL")) {
            retval = "UUID()";
        }
        return retval;
    }

    /**
     * 设置query的预编译参数
     *
     * @param query
     * @param parameterList
     */
    public static Query setParameters(Query query,
                                      List<HQLParameter> parameterList) {
        for (HQLParameter parameter : parameterList) {
            if (parameter.getType() == null) {
                if (parameter.getValue() instanceof Collection<?>) {
                    Collection<?> value = (Collection<?>) parameter.getValue();
                    query.setParameterList(parameter.getName(), value);
                } else {
                    query.setParameter(parameter.getName(),
                            parameter.getValue());
                }
            } else {
                if (parameter.getValue() instanceof Collection<?>) {
                    Collection<?> value = (Collection<?>) parameter.getValue();
                    query.setParameterList(parameter.getName(), value,
                            parameter.getType());
                } else {
                    query.setParameter(parameter.getName(),
                            parameter.getValue(), parameter.getType());
                }
            }
        }
        return query;
    }

    /**
     * 查询特殊字符转义
     * <p>
     * 在like查询中，如果有特殊字符：% _ [ ，会导致查询失效，需要做转义处理
     * <p>
     * 本方法将针对常用的数据库进行转义处理，对于未支持的数据库不作处理
     *
     * @param hql           查询的HQL语句
     * @param parameterList 查询参数（匹配的参数值会做转义处理）
     * @return 返回转义后的HQL
     */
    public static String escapeHql(String hql, List<HQLParameter> parameterList) {
        // 为了不影响全局查询，该功能默认关闭，可在：后台配置 -> 运维管理 -> 安全管控 -> 应用安全 中开启
        try {
            if (!new SysCommonSensitiveConfig().getSpecialSearchEnable()) {
                return hql;
            }
        } catch (Exception e) {
            return hql;
        }
        // 初始化数据库信息
        DBMetadata.init();
        // 由于各种数据库处理转义的语法不一样，这里只处理经过测试的数据库
        if (!DBMetadata.isSQLServer && !DBMetadata.isMySQL && !DBMetadata.isOracle) {
            if (logger.isDebugEnabled()) {
                logger.debug("暂不支持的数据库：" + ResourceUtil.getKmssConfigString(Environment.DIALECT));
            }
            return hql;
        }
        // 判断是否需要做转义处理（仅处理查询语句，且带有条件）
        if (hql.contains("select ") && hql.contains(" where ") && hql.contains(" like ") && CollectionUtils.isNotEmpty(parameterList)) {
            StringBuffer _hql = new StringBuffer(hql);
            int count = 0;
            long start = System.currentTimeMillis();
            StringBuffer _value = new StringBuffer();
            Set<String> keys = new HashSet<>();
            for (HQLParameter parameter : parameterList) {
                if (parameter.getValue() instanceof String) {
                    if (replaceHql(_hql, parameter, !keys.contains(parameter.getName()))) {
                        count++;
                        if (logger.isDebugEnabled()) {
                            // 这里的逻辑仅用于日志收集
                            if (_value.length() > 0) {
                                _value.append("; ");
                            }
                            _value.append(parameter.getName()).append(" --> ").append(parameter.getValue());
                        }
                    }
                    keys.add(parameter.getName());
                }
            }
            if (logger.isDebugEnabled() && count > 0) {
                long end = System.currentTimeMillis();
                logger.debug("特殊字符转义处理（耗时：{}毫秒）：\r\n \t原HQL：{}\r\n \t转义参数：{}个，参数内容：{}\r\n \t现HQL：{}", (end - start), hql, count, _value, _hql);
            }
            return _hql.toString();
        }
        return hql;
    }

    /**
     * 参数替换处理
     *
     * @param hql
     * @param parameter
     * @param isInsert
     * @return
     */
    public static boolean replaceHql(StringBuffer hql, HQLParameter parameter, boolean isInsert) {
        boolean escaped = false;
        boolean isEscaped = false;
        // 已经转义过的参数，就不再转义了
        if (BooleanUtils.isTrue(parameter.getEscaped())) {
            isEscaped = true;
        }
        // 获取匹配的片断
        List<Integer> patterns = findPattern(hql.toString(), parameter.getName());
        if (CollectionUtils.isNotEmpty(patterns)) {
            if (!isEscaped) {
                // 获取原始参数值
                String value = (String) parameter.getValue();
                // 替换参数值
                String newValue = replaceValue(value);
                // 如果替换后的参数值没有变化，则不处理
                if (!value.equals(newValue)) {
                    parameter.setValue(newValue);
                    // 标识该参数已处理过转义，如果参数没有变化时，不需要再次转义
                    parameter.setEscaped(Boolean.TRUE);
                    escaped = true;
                    if (logger.isDebugEnabled()) {
                        logger.debug("参数值替换，参数名：[{}]，原参数值：[{}]，现参数值：[{}]", parameter.getName(), value, newValue);
                    }
                } else {
                    isInsert = false;
                }
            }
            if (escaped || isInsert) {
                String escape;
                // 在指定片断后面增加转义词，目前处理的几种数据都一样，因数据库兼容的语法不一样，需要做区分
                if (DBMetadata.isMySQL) {
                    escape = " escape '\\\\'";
                } else {
                    escape = " escape '\\'";
                }
                int idx = 0;
                for (Integer index : patterns) {
                    // 某些查询条件存在一个参数，查询多个字段，如：sysOrgPerson.fdName like :fdName or sysOrgPerson.fdEmail like :fdName or sysOrgPerson.fdMobileNo like :fdName
                    // 这里对HQL插入关键字时，需要按数量递增
                    index += idx * escape.length();
                    if (index < hql.length() - 1) {
                        char c = hql.charAt(index);
                        // 如果匹配的字符后面不是空格或)号，则不处理
                        // 防止出现 :fdName 误替换 :fdName1 的情况
                        if (c != ' ' && c != ')') {
                            continue;
                        } else {
                            // 如果已经存在转义，则不需要再增加
                            String str = hql.substring(index + 1);
                            int _idx = str.indexOf("escape");
                            if (_idx > -1 && _idx <= 10) {
                                continue;
                            }
                        }
                    }
                    hql.insert(index, escape);
                    idx++;
                }
            }
        }
        return escaped;
    }

    /**
     * 参数值替换
     *
     * @param value
     * @return
     */
    private static String replaceValue(String value) {
        // like语法支持前后模糊，需要把前/后的%移除，再处理转义处理
        boolean isStart = false;
        boolean isEnd = false;
        if (value.startsWith("%")) {
            isStart = true;
            value = value.substring(1);
        }
        if (value.endsWith("%")) {
            isEnd = true;
            value = value.substring(0, value.length() - 1);
        }
        // 针对不同的数据库，需要转义的特殊字符不一样
        if (DBMetadata.isSQLServer) {
            // 在特殊字符前增加\，如：% --> \%，_ --> \_
            value = value.replaceAll("[\\[]", "\\\\[").replaceAll("[%]", "\\\\%").replaceAll("[_]", "\\\\_");
        } else if (DBMetadata.isMySQL || DBMetadata.isOracle) {
            value = value.replaceAll("[%]", "\\\\%").replaceAll("[_]", "\\\\_");
        }
        if (isStart) {
            value = "%" + value;
        }
        if (isEnd) {
            value += "%";
        }
        return value;
    }

    /**
     * 查询匹配的参数
     *
     * @param hql
     * @param name
     * @return
     */
    private static List<Integer> findPattern(String hql, String name) {
        // 查找模糊搜索的片断，如：like :fdName
        Matcher m = Pattern.compile("like(\\s*):" + name).matcher(hql);
        List<Integer> list = new ArrayList<>();
        while (m.find()) {
            list.add(m.end());
        }
        return list;
    }

    /**
     * 数据库信息
     */
    static class DBMetadata {
        public static boolean isOracle;
        public static boolean isPolarDB4Oracle;
        public static boolean isPolarDB;
        public static boolean isMySQL;
        public static boolean isSQLServer;
        public static boolean isShenTong;
        public static boolean isDB2;
        public static boolean isDM;
        public static boolean isKingbase;

        private static boolean isInit;

        public static void init() {
            if (!isInit) {
                isInit = true;
                String dialect = ResourceUtil.getKmssConfigString(Environment.DIALECT);
                isOracle = MetadataUtil.isOracle(dialect);
                isPolarDB4Oracle = MetadataUtil.isPolarDB4Oracle(dialect);
                isPolarDB = MetadataUtil.isPolarDB(dialect);
                isMySQL = MetadataUtil.isMySQL(dialect);
                isSQLServer = MetadataUtil.isSQLServer(dialect);
                isShenTong = MetadataUtil.isShenTong(dialect);
                isDB2 = MetadataUtil.isDB2(dialect);
                isDM = MetadataUtil.isDM(dialect);
                isKingbase = MetadataUtil.isKingbase(dialect);
            }
        }
    }

}
