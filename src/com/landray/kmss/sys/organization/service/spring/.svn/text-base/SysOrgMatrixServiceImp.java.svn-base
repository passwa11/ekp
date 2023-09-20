package com.landray.kmss.sys.organization.service.spring;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.admin.dbchecker.metadata.util.MetadataUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.lbpm.engine.support.def.XMLUtils;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.dao.ISysOrgMatrixDao;
import com.landray.kmss.sys.organization.forms.SysOrgMatrixDataCateForm;
import com.landray.kmss.sys.organization.forms.SysOrgMatrixForm;
import com.landray.kmss.sys.organization.forms.SysOrgMatrixRelationForm;
import com.landray.kmss.sys.organization.forms.SysOrgMatrixVersionForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgMatrix;
import com.landray.kmss.sys.organization.model.SysOrgMatrixCate;
import com.landray.kmss.sys.organization.model.SysOrgMatrixDataCate;
import com.landray.kmss.sys.organization.model.SysOrgMatrixRelation;
import com.landray.kmss.sys.organization.model.SysOrgMatrixVersion;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixCateService;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixDataCateService;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixService;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixVersionService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.organization.util.SysOrgMatrixUtil;
import com.landray.kmss.sys.xform.maindata.model.SysFormMainDataCustomList;
import com.landray.kmss.sys.xform.maindata.model.SysFormMainDataInsystem;
import com.landray.kmss.sys.xform.maindata.service.ISysFormMainDataCustomListService;
import com.landray.kmss.sys.xform.maindata.service.ISysFormMainDataInsystemService;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.HibernateUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.DoubleType;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 组织矩阵
 *
 * @author 潘永辉 2019年6月4日
 */
public class SysOrgMatrixServiceImp extends BaseServiceImp
        implements ISysOrgMatrixService, SysOrgConstant, IXMLDataBean {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgMatrixServiceImp.class);

    private ISysOrgMatrixCateService sysOrgMatrixCateService;

    private ISysOrgElementService sysOrgElementService;

    private ISysOrgPersonService sysOrgPersonService;

    private ISysOrgMatrixVersionService sysOrgMatrixVersionService;

    private ISysOrgMatrixDataCateService sysOrgMatrixDataCateService;

    // 系统内数据
    private ISysFormMainDataInsystemService sysFormMainDataInsystemService;

    // 自定义数据（列表）
    private ISysFormMainDataCustomListService sysFormMainDataCustomListService;

    public void setSysOrgMatrixCateService(
            ISysOrgMatrixCateService sysOrgMatrixCateService) {
        this.sysOrgMatrixCateService = sysOrgMatrixCateService;
    }

    public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
        this.sysOrgElementService = sysOrgElementService;
    }

    public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
        this.sysOrgPersonService = sysOrgPersonService;
    }

    public void setSysFormMainDataInsystemService(
            ISysFormMainDataInsystemService sysFormMainDataInsystemService) {
        this.sysFormMainDataInsystemService = sysFormMainDataInsystemService;
    }

    public void setSysFormMainDataCustomListService(
            ISysFormMainDataCustomListService sysFormMainDataCustomListService) {
        this.sysFormMainDataCustomListService = sysFormMainDataCustomListService;
    }

    public void setSysOrgMatrixVersionService(ISysOrgMatrixVersionService sysOrgMatrixVersionService) {
        this.sysOrgMatrixVersionService = sysOrgMatrixVersionService;
    }

    public void setSysOrgMatrixDataCateService(ISysOrgMatrixDataCateService sysOrgMatrixDataCateService) {
        this.sysOrgMatrixDataCateService = sysOrgMatrixDataCateService;
    }

    @Override
    public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model,
                                         RequestContext requestContext) throws Exception {
        // 过滤分组名称为空的分组
        SysOrgMatrixForm matrixForm = (SysOrgMatrixForm) form;
        if (CollectionUtils.isNotEmpty(matrixForm.getFdDataCates())) {
            Iterator<SysOrgMatrixDataCateForm> iter = matrixForm.getFdDataCates().iterator();
            while (iter.hasNext()) {
                SysOrgMatrixDataCateForm cate = iter.next();
                if (StringUtil.isNull(cate.getFdName())) {
                    iter.remove();
                }
            }
        }

        SysOrgMatrix matrix = (SysOrgMatrix) super.convertFormToModel(form, model, requestContext);
        List<SysOrgMatrixRelation> relations = new ArrayList<SysOrgMatrixRelation>();

        // 条件字段
        if (matrix.getFdRelationConditionals() != null
                && !matrix.getFdRelationConditionals().isEmpty()) {
            // 字段不能超地20个
            if (matrix.getFdRelationConditionals().size() > 20) {
                throw new RuntimeException(
                        ResourceUtil.getString("sysOrgMatrix.import.file.more.than",
                                "sys-organization", null, new Object[]{20, 20}));
            }
            for (int i = 0; i < matrix.getFdRelationConditionals().size(); i++) {
                SysOrgMatrixRelation relation = matrix.getFdRelationConditionals().get(i);
                relation.setFdOrder(i + 1);
                relation.setFdIsResult(false);
                if ("sys".equals(relation.getFdType())) {
                    // 主数据（系统内数据）
                    relation.setFdType(relation.getFdMainDataType());
                    relation.setFdMainDataType("sys");
                } else if ("cust".equals(relation.getFdType())) {
                    // 主数据（自定义数据）
                    relation.setFdType(relation.getFdMainDataType());
                    relation.setFdMainDataType("cust");
                } else {
                    relation.setFdMainDataType(null);
                }
            }
            relations.addAll(matrix.getFdRelationConditionals());
        }

        // 结果字段
        if (matrix.getFdRelationResults() != null
                && !matrix.getFdRelationResults().isEmpty()) {
            // 字段不能超地20个
            if (matrix.getFdRelationResults().size() > 20) {
                throw new RuntimeException(
                        ResourceUtil.getString("sysOrgMatrix.import.file.more.than",
                                "sys-organization", null, new Object[]{20, 20}));
            }
            for (int i = 0; i < matrix.getFdRelationResults().size(); i++) {
                SysOrgMatrixRelation relation = matrix.getFdRelationResults().get(i);
                relation.setFdOrder(i + 1);
                relation.setFdIsResult(true);
            }
            relations.addAll(matrix.getFdRelationResults());
        }

        for (SysOrgMatrixRelation relation : relations) {
            if (StringUtil.isNull(relation.getFdFieldName())) {
                // 设置字段名称
                relation.setFdMatrix(matrix);
                relation.setFdFieldName(getFieldName(BooleanUtils.isTrue(relation.getFdIsResult())));
            }
            // 区间类型的结束字段
            if (relation.isRange() && StringUtil.isNull(relation.getFdFieldName2())) {
                relation.setFdFieldName2(getFieldName(false));
            }
        }

        matrix.setFdRelations(relations);
        // 设置一个子表表名
        if (StringUtil.isNull(matrix.getFdSubTable())) {
            matrix.setFdSubTable(getSubTable());
        }
        return matrix;
    }

    @Override
    public IExtendForm convertModelToForm(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        IExtendForm _form = super.convertModelToForm(form, model, requestContext);
        // 过滤已删除的版本
        if (_form instanceof SysOrgMatrixForm) {
            SysOrgMatrixForm matrixForm = (SysOrgMatrixForm) _form;
            List<SysOrgMatrixVersionForm> versions = matrixForm.getFdVersions();
            if (CollectionUtils.isNotEmpty(versions)) {
                Iterator<SysOrgMatrixVersionForm> iterator = versions.iterator();
                while (iterator.hasNext()) {
                    SysOrgMatrixVersionForm version = iterator.next();
                    if (BooleanUtils.isTrue(version.getFdIsDelete())) {
                        iterator.remove();
                    }
                }
            }
        }
        return _form;
    }

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        // 判断是否存在，如果存在就更新
        if (getBaseDao().isExist(getModelName(), modelObj.getFdId())) {
            super.update(modelObj);
            return modelObj.getFdId();
        }
        String id = super.add(modelObj);
        // 记录增加成功，创建子表
        createSubTable((SysOrgMatrix) modelObj);
        // 默认增加一个版本
        addVersion(modelObj.getFdId(), "V1");
        return id;
    }

    /**
     * 打印非法请求日志
     *
     * @param request
     */
    private void errorLog(HttpServletRequest request) {
        if (logger.isErrorEnabled()) {
            logger.error("修改矩阵信息，缺少字段关系，以下为请求提交参数：");
            if (request == null) {
                request = Plugin.currentRequest();
            }
            if (request != null) {
                String fromURL = request.getHeader("Referer");
                logger.error("请求来源：" + fromURL);
                logger.error("请求IP：" + request.getRemoteAddr());
                logger.error("请求参数：");
                Enumeration<String> names = request.getParameterNames();
                while (names.hasMoreElements()) {
                    String key = names.nextElement();
                    logger.error(key + " --> " + request.getParameter(key));
                }
                logger.error("请求头：");
                names = request.getHeaderNames();
                while (names.hasMoreElements()) {
                    String key = names.nextElement();
                    logger.error(key + " --> " + request.getHeader(key));
                }
            } else {
                logger.error("request is null");
            }
            logger.error("================(end)================");
        }
    }

    @Override
    public void update(IExtendForm form, RequestContext requestContext) throws Exception {
        SysOrgMatrixForm matrixForm = (SysOrgMatrixForm) form;
        if (CollectionUtils.isEmpty(matrixForm.getFdRelationResults())
                || CollectionUtils.isEmpty(matrixForm.getFdRelationConditionals())) {
            errorLog(requestContext.getRequest());
            // 字段关系为空，不允许修改
            throw new RuntimeException("非法操作，矩阵字段为空！");
        }
        if (!"saveMatrixField".equals(requestContext.getParameter("method"))) {
            SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(form.getFdId());
            SysOrgMatrixForm tempForm = new SysOrgMatrixForm();
            tempForm = (SysOrgMatrixForm) convertModelToForm(tempForm, matrix, requestContext);
            // 以下信息保留原来的
            matrixForm.setMatrixType(tempForm.getMatrixType());
            matrixForm.setFdSubTable(tempForm.getFdSubTable());
            matrixForm.setFdRelationConditionals(tempForm.getFdRelationConditionals());
            matrixForm.setFdRelationResults(tempForm.getFdRelationResults());
        }
        super.update(matrixForm, requestContext);
    }

    @Override
    public void update(IBaseModel modelObj) throws Exception {
        SysOrgMatrix matrix = (SysOrgMatrix) modelObj;
        if (CollectionUtils.isEmpty(matrix.getFdRelationResults())
                || CollectionUtils.isEmpty(matrix.getFdRelationConditionals())) {
            errorLog(null);
            // 字段关系为空，不允许修改
            throw new RuntimeException("非法操作，矩阵字段为空！");
        }
        String columnType = getColumnType();

        // 获取原来的关系ID
        JSONObject oriRelations = getOriRelations(matrix.getFdId());
        for (SysOrgMatrixRelation relation : matrix.getFdRelations()) {
            // 判断是否新增
            if (!oriRelations.containsKey(relation.getFdId())) {
                String constantType = "VARCHAR(1000)";
                if (!BooleanUtils.isTrue(relation.getFdIsResult())) {
                    // 常量和自定义主数据需要处理乱码
                    constantType = ("constant".equals(relation.getFdType()) || "cust".equals(relation.getFdMainDataType()))
                            ? columnType + "(200)" : "VARCHAR(36)";
                }
                if ("numRange".equals(relation.getFdType())) {
                    String colType = HibernateUtil.getColumnType(Types.DOUBLE);
                    // 数值区间（这里的数值是2个Double）
                    executeUpdate("ALTER TABLE " + matrix.getFdSubTable() + " ADD " + relation.getFdFieldName() + " " + colType, null);
                    executeUpdate("ALTER TABLE " + matrix.getFdSubTable() + " ADD " + relation.getFdFieldName2() + " " + colType, null);
                } else {
                    // 新增字段
                    // ALTER TABLE table_name ADD column_name VARCHAR(20) NULL
                    executeUpdate("ALTER TABLE " + matrix.getFdSubTable() + " ADD " + relation.getFdFieldName() + " " + constantType + " NULL", null);
                }
            } else {
                //#164646 条件字段 非新增需要校验  是否为 其他类型转变为numRange
                if(!BooleanUtils.isTrue(relation.getFdIsResult())){
                    String[] fieldNameAndType = oriRelations.get(relation.getFdId()).toString().split("\\|");
                    String fdFieldName = fieldNameAndType[0];
                    String fdType = fieldNameAndType[1];
                    String fdFieldName2 = fieldNameAndType[2];
                    if("numRange".equalsIgnoreCase(relation.getFdType())){
                        if(!"numRange".equalsIgnoreCase(fdType)){
                            //先删除原有的列，再新增数值区间列
                            executeUpdate("ALTER TABLE " + matrix.getFdSubTable() + " drop column " + relation.getFdFieldName(), null);
                            String colType = HibernateUtil.getColumnType(Types.DOUBLE);
                            // 数值区间（这里的数值是2个Double）
                            executeUpdate("ALTER TABLE " + matrix.getFdSubTable() + " ADD " + relation.getFdFieldName() + " " + colType, null);
                            executeUpdate("ALTER TABLE " + matrix.getFdSubTable() + " ADD " + relation.getFdFieldName2() + " " + colType, null);
                        }
                    }
                    //#164646 非新增需要校验  是否为numRange转变为 其他类型
                    if(!"numRange".equalsIgnoreCase(relation.getFdType())){
                        if("numRange".equalsIgnoreCase(fdType)){
                            //先删除原有的数值区间列，再新增其他类型列
                            executeUpdate("ALTER TABLE " + matrix.getFdSubTable() + " drop column " + fdFieldName, null);
                            executeUpdate("ALTER TABLE " + matrix.getFdSubTable() + " drop column " + fdFieldName2, null);
                            relation.setFdFieldName2(null);
                            // 新增其他类型列
                            // 常量和自定义主数据需要处理乱码
                            String constantType = ("constant".equals(relation.getFdType()) || "cust".equals(relation.getFdMainDataType()))
                                    ? columnType + "(200)" : "VARCHAR(36)";
                            executeUpdate("ALTER TABLE " + matrix.getFdSubTable() + " ADD " + relation.getFdFieldName() + " " + constantType + " NULL", null);
                        }
                    }
                }
                oriRelations.remove(relation.getFdId());
            }
        }

        if (matrix.getMatrixType() == null || "".equals(matrix.getMatrixType())) {
            matrix.setMatrixType("2");//为空的话设置为人工创建类型为2
        }
        // 删除不存在的分组数据（开启分组时，如果删除了某些分组，需要把分组的数据也删除）
        if (BooleanUtils.isTrue(matrix.getFdIsEnabledCate())) {
            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM ").append(matrix.getFdSubTable());
            List<SysOrgMatrixDataCate> list = matrix.getFdDataCates();
            List<String> cateIds = null;
            if (CollectionUtils.isNotEmpty(list)) {
                cateIds = new ArrayList<String>();
                sql.append(" WHERE fd_cate_id NOT IN (:cateIds)");
                for (SysOrgMatrixDataCate cate : list) {
                    cateIds.add(cate.getFdId());
                }
            }
            NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
            query.addSynchronizedQuerySpace(matrix.getFdSubTable());
            if (cateIds != null) {
                query.setParameterList("cateIds", cateIds);
            }
            int count = query.executeUpdate();
            logger.info("删除矩阵数据：" + count + "条。");
            // 删除分组
            sysOrgMatrixDataCateService.deleteByNotMatrix();
        }
        super.update(matrix);
    }

    /**
     * 获取原来的矩阵关系ID集合
     *
     * @param matrixId
     * @return
     * @throws Exception
     */
    private JSONObject getOriRelations(String matrixId) throws Exception {
        String sql = "SELECT fd_id, fd_field_name, fd_type, fd_field_name2 FROM sys_org_matrix_relation WHERE fd_matrix_id = ?";
        List<String> params = new ArrayList<String>();
        params.add(matrixId);
        List<Object> list = executeQuery(sql, params);
        JSONObject json = new JSONObject();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                Object[] objs = (Object[]) list.get(i);
                json.put(objs[0].toString(), objs[1]+ "|" + objs[2]+ "|" + objs[3]);
            }
        }
        return json;
    }

    @Override
    public void delete(IBaseModel modelObj) throws Exception {
        // 系统内置的矩阵不能删除
        if ("1".equals(((SysOrgMatrix) modelObj).getMatrixType())) {
            return;
        }
        // 删除子表
        deleteSubTable((SysOrgMatrix) modelObj);
        super.delete(modelObj);
    }

    /**
     * 执行更新
     *
     * @param sql
     * @throws Exception
     */
    private void executeUpdate(String sql, List<Object> params) throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info("执行SQL：" + sql);
        }
        NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                query.setParameter(i, param);
            }
        }
        String tableName = ModelUtil.getModelTableName(getBaseDao().getModelName());
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
     * 执行查询
     *
     * @param sql
     * @param params
     * @return
     * @throws Exception
     */
    private List<Object> executeQuery(String sql, List<String> params)
            throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info("执行SQL：" + sql);
            logger.info("执行SQL参数：" + params);
        }
        NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                String param = params.get(i);
                query.setParameter(i, param);
            }
        }
        return query.list();
    }

    /**
     * 获取建表语句
     *
     * @param matrix
     * @return
     */
    private String getCreateTableSql(SysOrgMatrix matrix) {
        StringBuilder sql = new StringBuilder();
        // 子表字段总长度（数据库中字段总长度最大不能超过65535）
        int totalLength = 36;
        String columnType = getColumnType();

        sql.append("CREATE TABLE ").append(matrix.getFdSubTable()).append(" (").append("fd_id VARCHAR(36) NOT NULL, ")
                .append("fd_cate_id VARCHAR(36) , ").append("fd_version ").append(columnType).append("(")
                .append(5).append("), ");
        for (SysOrgMatrixRelation relation : matrix.getFdRelations()) {
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
     * 创建子表
     *
     * @param matrix
     */
    private void createSubTable(SysOrgMatrix matrix) throws Exception {
        executeUpdate(getCreateTableSql(matrix), null);
    }

    /**
     * 清空矩阵数据，一般用于导入前的操作
     *
     * @param matrix
     * @throws Exception
     */
    private void clearMatrixData(SysOrgMatrix matrix) throws Exception {
        executeUpdate("DELETE FROM " + matrix.getFdSubTable(), null);
    }

    /**
     * 删除子表
     *
     * @param matrix
     * @throws Exception
     */
    private void deleteSubTable(SysOrgMatrix matrix) throws Exception {
        executeUpdate("DROP TABLE " + matrix.getFdSubTable(), null);
    }

    /**
     * 获取一个不重复的子表名称
     *
     * @return
     * @throws Exception
     */
    private String getSubTable() throws Exception {
        String subTable = "sys_org_matrix_";
        while (true) {
            String id = IDGenerator.generateID();
            String temp = subTable + id.substring(id.length() - 15);

            // 查询子表是否有重复
            List<?> list = findList("fdSubTable = '" + temp + "'", null);
            if (list == null || list.isEmpty()) {
                subTable = temp;
                break;
            }
        }
        return subTable;
    }

    /**
     * 获取字段名称
     *
     * @return
     */
    private String getFieldName(boolean isResult) {
        String id = IDGenerator.generateID();
        return "fd_" + (isResult ? "r_" : "c_") + id.substring(id.length() - 22);
    }

    /**
     * 批量置为无效
     *
     * @param ids
     * @param request
     * @throws Exception
     */
    @Override
    public void updateInvalidated(String[] ids, RequestContext request)
            throws Exception {
        for (int i = 0; i < ids.length; i++) {
            this.updateInvalidated(ids[i], request);
        }
    }

    @Override
    public void updateInvalidated(String id, RequestContext request)
            throws Exception {
        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(id);

        //1 内置生成的
        if (matrix.getMatrixType() != null && "1".equals(matrix.getMatrixType())) {
            logger.info("内置生成的矩阵不允许禁用");
            throw new KmssRuntimeException(new KmssMessage("sys-organization:init.sysOrgMatrix.not.invalidated.message"));
        }

        // 记录日志
        if (UserOperHelper.allowLogOper("invalidated", SysOrgMatrix.class.getName())) {
            UserOperContentHelper.putUpdate(matrix);
        }

        if (matrix != null) {
            boolean available = "true".equals(request.getParameter("available"));
            matrix.setFdIsAvailable(available);
        }
        update(matrix);
    }

    /**
     * 保存矩阵信息
     *
     * @param tableName
     * @param version
     * @param relations
     * @throws Exception
     */
    public void saveMatrixData(String tableName, String version, List<SysOrgMatrixRelation> relations) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(tableName).append(" (").append("fd_id");
        StringBuilder paramSql = new StringBuilder();
        paramSql.append("?");
        List<Object> params = new ArrayList<Object>();
        params.add(IDGenerator.generateID());
        for (SysOrgMatrixRelation relation : relations) {
            sql.append(", ").append(relation.getFdFieldName());
            if (BooleanUtils.isTrue(relation.getFdIsResult())) {
                String ids = relation.getFdResultValueIds();
                if (StringUtil.isNotNull(ids)) {
                    params.add(ids);
                    paramSql.append(", ?");
                } else {
                    paramSql.append(", null");
                }
            } else {
                Object value = relation.getFdConditionalValue();
                if (value != null) {
                    params.add(value);
                    paramSql.append(", ?");
                } else {
                    paramSql.append(", null");
                }
                if (relation.isRange()) {
                    sql.append(", ").append(relation.getFdFieldName2());
                    Object value2 = relation.getFdConditionalValue2();
                    if (value2 != null) {
                        params.add(value2);
                        paramSql.append(", ?");
                    } else {
                        paramSql.append(", null");
                    }
                }
            }
        }
        paramSql.append(", ?");
        params.add(version);
        sql.append(", fd_version) VALUES (").append(paramSql).append(")");
        if (logger.isInfoEnabled()) {
            logger.info("插入数据语句：" + sql);
        }
        executeUpdate(sql.toString(), params);
    }

    /**
     * 获取矩阵数据（分页）
     */
    @Override
    public Page findMatrixPage(String fdMatrixId, String fdVersion, int pageno, int rowsize, String filter)
            throws Exception {
        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(fdMatrixId);
        List<SysOrgMatrixRelation> listRelation = new ArrayList<SysOrgMatrixRelation>();
        List<SysOrgMatrixRelation> listConditional = matrix.getFdRelationConditionals();
        Collections.sort(listConditional);
        listRelation.addAll(listConditional);
        List<SysOrgMatrixRelation> listResult = matrix.getFdRelationResults();
        Collections.sort(listResult);
        listRelation.addAll(listResult);

        Page page = null;
        HttpServletRequest request = Plugin.currentRequest();
        String method = request.getParameter("method_GET");
        // 分组
        boolean isCate = StringUtil.isNull(method) && BooleanUtils.isTrue(matrix.getFdIsEnabledCate());
        if (isCate) {
            SysOrgMatrixRelation byCate = new SysOrgMatrixRelation();
            listRelation.add(byCate);
            page = ((ISysOrgMatrixDao) getBaseDao()).findMatrixPageByType(matrix, fdVersion, pageno, rowsize, null, filter);
        } else {
            page = ((ISysOrgMatrixDao) getBaseDao()).findMatrixPage(matrix, fdVersion, pageno, rowsize, filter);
        }
        List<Object[]> list = page.getList();
        List<List<SysOrgMatrixRelation>> relations = new ArrayList<List<SysOrgMatrixRelation>>();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                List<SysOrgMatrixRelation> temps = new ArrayList<SysOrgMatrixRelation>();
                Object[] objs = list.get(i);
                int idx = 0;
                for (int j = 0; j <= listRelation.size(); j++) {
                    Object obj = objs[idx];
                    SysOrgMatrixRelation temp = new SysOrgMatrixRelation();
                    if (j == 0) {
                        // 主键
                        temp.setFdIsPrimary(true);
                        temp.setFdId(obj.toString());
                    } else {
                        SysOrgMatrixRelation relation = listRelation.get(j - 1);
                        // 普通字段
                        temp.setFdWidth(relation.getFdWidth());
                        temp.setFdName(relation.getFdName());
                        temp.setFdMatrix(relation.getFdMatrix());
                        temp.setFdFieldName(relation.getFdFieldName());
                        temp.setFdFieldName2(relation.getFdFieldName2());
                        temp.setFdType(relation.getFdType());
                        temp.setFdIsResult(relation.getFdIsResult());
                        temp.setFdOrder(relation.getFdOrder());
                        temp.setFdIsPrimary(false);
                        temp.setFdMainDataType(relation.getFdMainDataType());
                        if (relation.isRange()) {
                            // 开始区间
                            obj = objs[idx];
                            temp.setFdConditionalValue(obj);
                            // 结束区间
                            idx++;
                            Object obj2 = objs[idx];
                            temp.setFdConditionalValue2(obj2);
                        } else {
                            if (obj != null) {
                                String val = obj.toString();
                                if (BooleanUtils.isTrue(relation.getFdIsResult())) {
                                    // 结果可能是多值
                                    try {
                                        if (StringUtil.isNotNull(val)) {
                                            if (val.startsWith("{") && val.endsWith("}")) {
                                                JSONObject json = JSONObject.parseObject(val);
                                                if (!json.isEmpty()) {
                                                    temp.setFdResultValues(getElementByJson(json));
                                                }
                                            } else {
                                                temp.setFdResultValues(getElementByIds(val));
                                            }
                                        }
                                    } catch (Exception e) {
                                        logger.info("没有对应的组织架构id：" + obj, e);
                                        List<SysOrgElement> resultList = new ArrayList<>();
                                        SysOrgElement o = new SysOrgElement();
                                        o.setFdName(ResourceUtil.getString("sys-organization:sysOrgMatrix.data.unusual"));
                                        resultList.add(o);
                                        temp.setFdResultValues(resultList);
                                    }
                                } else {
                                    String name = getConditionalName(temp, val);
                                    temp.setFdConditionalValue(name);
                                    temp.setFdConditionalId(val);
                                }
                            }
                        }
                    }
                    temps.add(temp);
                    idx++;
                }
                relations.add(temps);
            }
            page.setList(relations);
        }

        return page;
    }

    /**
     * 获取多个组织元素(ID)
     *
     * @param ids
     * @return
     */
    private List<SysOrgElement> getElementByIds(String ids) throws Exception {
        String[] fdIds = ids.split("[;,]");
        List<String> _ids = new ArrayList<String>();
        for (String fdId : fdIds) {
            if (StringUtil.isNotNull(fdId)) {
                _ids.add(fdId);
            }
        }
        if (CollectionUtils.isEmpty(_ids)) {
            return Collections.EMPTY_LIST;
        }
        List<SysOrgElement> list = sysOrgElementService.findByPrimaryKeys(_ids.toArray(new String[] {}));
        for (int i = 0; i < list.size(); i++) {
            SysOrgElement elem = list.get(i);
            if (elem.getFdOrgType() == 8) {
                list.set(i, sysOrgElementService.format(elem));
            }
        }
        return list;
    }

    /**
     * 根据JSON格式数据获取组织元素
     *
     * @param json
     * @return
     * @throws Exception
     */
    private List<SysOrgElement> getElementByJson(JSONObject json) throws Exception {
        List<String> ids = new ArrayList<String>();
        for (Object key : json.keySet()) {
            String id = json.get(key).toString();
            if (StringUtil.isNotNull(id)) {
                ids.add(id);
            }
        }
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        List<SysOrgElement> list = sysOrgElementService.findByPrimaryKeys(ids.toArray(new String[] {}));
        for (int i = 0; i < list.size(); i++) {
            SysOrgElement elem = list.get(i);
            if (elem.getFdOrgType() == 8) {
                list.set(i, sysOrgElementService.format(elem));
            }
        }
        return list;
    }

    /**
     * 根据条件值获取显示名称
     *
     * @param relation
     * @param value
     * @return
     * @throws Exception
     */
    private String getConditionalName(SysOrgMatrixRelation relation, String value) throws Exception {
        String type = relation.getFdType();
        if ("constant".equals(type)) {
            // 常量，直接保存
            return value;
        } else if ("org".equals(type) || "dept".equals(type) || "post".equals(type) || "person".equals(type) || "group".equals(type)) {
            if (StringUtil.isNull(value) || "undefined".equalsIgnoreCase(value) || "null".equalsIgnoreCase(value)) {
                // 某个时期出现保存的数据为undefined或null
                return null;
            }
            //避免外系统导入时，不存在对应组织架构id查询错误
            try{
                SysOrgElement temp = (SysOrgElement) sysOrgElementService.findByPrimaryKey(value);
                if (temp != null) {
                    if (temp.getFdOrgType() == 8) {
                        temp = sysOrgElementService.format(temp);
                    }
                    return SysOrgMatrixUtil.getElementName(temp);
                }
            }catch (Exception e){
                logger.info("没有对应的组织架构id："+value,e);
                return ResourceUtil.getString("sys-organization:sysOrgMatrix.data.unusual");
            }
        } else if (StringUtil.isNotNull(value)) {
            if ("sys".equals(relation.getFdMainDataType())) {
                // 系统内数据
                SysFormMainDataInsystem model = (SysFormMainDataInsystem) sysFormMainDataInsystemService.findByPrimaryKey(type, null, true);
                if (model != null) {
                    String modelName = model.getFdModelName();
                    // 获取文档名称
                    return getDisplayValue(modelName, value);
                }
            } else if ("cust".equals(relation.getFdMainDataType())) {
                // 自定义数据
                HQLInfo hqlInfo = new HQLInfo();
                hqlInfo.setModelName(SysFormMainDataCustomList.class.getName());
                hqlInfo.setSelectBlock("fdValueText");
                hqlInfo.setWhereBlock("sysFormMainDataCustom.fdId = :customId and fdValue = :value");
                hqlInfo.setParameter("customId", type);
                hqlInfo.setParameter("value", value);
                List<String> list = sysFormMainDataCustomListService.findValue(hqlInfo);
                if (list != null && !list.isEmpty()) {
                    return list.get(0);
                }
            }
        }
        return null;
    }

    /**
     * 根据数据字典获取显示属性名称
     *
     * @param modelName
     * @return
     */
    private String getDisplayProperty(String modelName) {
        SysDictModel model = SysDataDict.getInstance().getModel(modelName);
        if (model == null) {
            throw new RuntimeException(
                    ResourceUtil.getString(
                            "sysOrgMatrix.import.dict.non.existent", "sys-organization", null, modelName));
        }
        String displayProperty = model.getDisplayProperty();
        if (StringUtil.isNull(displayProperty)) {
            throw new RuntimeException(
                    ResourceUtil.getString(
                            "sysOrgMatrix.import.displayProperty.non.existent", "sys-organization", null, modelName));
        }
        return displayProperty;
    }

    /**
     * 获取文档标题
     *
     * @param modelName
     * @param modelId
     * @return
     */
    private String getDisplayValue(String modelName, String modelId)
            throws Exception {
        String displayProperty = getDisplayProperty(modelName);
        IBaseModel model = findByPrimaryKey(modelId, modelName, true);
        if (model != null) {
            return BeanUtils.getProperty(model, displayProperty);
        }
        return null;
    }

    /**
     * 获取多个结果组织元素
     * @param relation
     * @param names
     * @param ids
     * @return
     * @throws Exception
     */
    private List<SysOrgElement> getElementByResultNames(SysOrgMatrixRelation relation, String names, String ids) throws Exception {
        List<SysOrgElement> list = new ArrayList<SysOrgElement>();
        String[] fdNames = StringUtil.isNotNull(names) ? names.split("[;,]") : null;
        String[] fdIds = StringUtil.isNotNull(ids) ? ids.split("[;,]") : null;
        if (fdNames == null) {
            fdNames = new String[fdIds.length];
        }
        if (fdIds == null) {
            fdIds = new String[fdNames.length];
        }
        checkResult(relation, StringUtil.isNotNull(names) ? names : "");
        if (fdNames.length != fdIds.length) {
            throw new RuntimeException(ResourceUtil.getString("sysOrgMatrix.import.field.mismatching", "sys-organization", null, new Object[]{names, ids}));
        }
        for (int i = 0; i < fdNames.length; i++) {
            String sName = fdNames[i] != null ? fdNames[i].trim() : null;
            String sId = fdIds[i] != null ? fdIds[i].trim() : null;
            SysOrgElement elem = getElementByName(relation.getFdType(), sName, sId);
            if (!BooleanUtils.isTrue(elem.getFdIsAvailable())) {
                throw new RuntimeException(ResourceUtil.getString("sysOrgMatrix.import.disable", "sys-organization",
                        null, elem.getFdName()));
            }
            if ("person_post".equals(relation.getFdType())) {
                // 如果是人员+岗位，最多只能是2个值，并且只能是一人一岗
                if (list.size() > 1) {
                    throw new RuntimeException(ResourceUtil.getString("sysOrgMatrix.import.error.personPost", "sys-organization"));
                } else if (list.size() > 0) {
                    SysOrgElement _elem = list.get(0);
                    if (_elem.getFdOrgType().equals(elem.getFdOrgType())) {
                        throw new RuntimeException(ResourceUtil.getString("sysOrgMatrix.import.error.personPost", "sys-organization"));
                    }
                }
                list.add(elem);
            } else {
                //组织人员字段类型需要与导入数据人员字段相匹配
                if(!checkElementMatch(relation,elem)){
                    throw new RuntimeException(ResourceUtil.getString("sysOrgMatrix.import.error.orgDataNotMatch", "sys-organization"));
                }
                list.add(elem);
            }
        }
        return list;
    }

    /**
     * 校验人员字段类型与导入数据人员字段类型是否相匹配
     * @param relation
     * @param elem
     * @return false:不匹配，true:匹配
     */
    private Boolean checkElementMatch(SysOrgMatrixRelation relation,SysOrgElement elem){
        String attrType = relation.getFdType();
        switch (attrType){
            case "org":
                return elem.getFdOrgType() == 1;
            case "dept":
                return elem.getFdOrgType() == 2;
            case "post":
                return elem.getFdOrgType() == 4;
            case "person":
                return elem.getFdOrgType() == 8;
            case "group":
                return elem.getFdOrgType() == 16;
            default:
                break;
        }

        return false;
    }

    /**
     * 导入时根据特定的名称查询记录
     * @param type
     * @param name
     * @param id
     * @return
     * @throws Exception
     */
    private SysOrgElement getElementByName(String type, String name, String id) throws Exception {
        String temp = null;
        List<SysOrgElement> list = null;
        if (StringUtil.isNotNull(id)) { // 如果有ID，就直接用ID去查询，因为ID是绝对唯一的
            temp = StringUtil.linkString(temp, ", ", "id=" + id);
            SysOrgElement elem = (SysOrgElement) sysOrgElementService.findByPrimaryKey(id, null, true);
            list = new ArrayList<SysOrgElement>();
            if (elem != null) {
                list.add(elem);
            }
        } else if (StringUtil.isNotNull(name)) {
            temp = StringUtil.linkString(temp, ", ", "name=" + name);
            list = getElementByName(type, name);
        } else {
            return null;
        }

        // 没有找到记录
        if (list == null || list.isEmpty()) {
            throw new RuntimeException(
                    ResourceUtil.getString("sysOrgMatrix.import.field.empty", "sys-organization", null, temp));
        }
        // 找到多条记录
        if (list.size() > 1) {
            throw new RuntimeException(
                    ResourceUtil.getString("sysOrgMatrix.import.field.multiple", "sys-organization", null, temp));
        }
        SysOrgElement elem = list.get(0);
        if (!BooleanUtils.isTrue(elem.getFdIsAvailable())) {
            throw new RuntimeException(
                    ResourceUtil.getString("sysOrgMatrix.import.disable", "sys-organization", null, elem.getFdName()));
        }
        return elem;
    }

    /**
     * 根据名称查询记录
     * @param type
     * @param name
     * @return
     * @throws Exception
     */
    private List<SysOrgElement> getElementByName(String type, String name) throws Exception {
        if (StringUtil.isNull(name)) {
            // 名称不能为空
            throw new RuntimeException(ResourceUtil.getString("errors.required", null, null, ResourceUtil.getString("model.fdName")));
        }
        HQLInfo hqlInfo = new HQLInfo();
        StringBuilder where = new StringBuilder();
        String[] names = StringUtils.splitPreserveAllTokens(name, "/");
        if (StringUtil.isNotNull(names[0])) {
            where.append(" and fdName = :fdName ");
            hqlInfo.setParameter("fdName", names[0]);
        }
        List<SysOrgElement> list = null;
        if ("person_post".equals(type)) {
            // 人+岗位 类型，这里传进来的名称，可能是人员，也可能是岗位
            StringBuilder oriWhere = new StringBuilder();
            oriWhere.append(where.toString());
            // 先按人员查询，如果查询为空，再按岗位查询
            list = getPersonByName(hqlInfo, where, names);
            if (CollectionUtils.isEmpty(list)) {
                hqlInfo = new HQLInfo();
                if (oriWhere.indexOf("fdName") > -1) {
                    hqlInfo.setParameter("fdName", names[0]);
                }
                list = getElementByName(hqlInfo, oriWhere, names);
            }
        } else if ("person".equals(type)) {
            list = getPersonByName(hqlInfo, where, names);
        } else if ("org".equals(type) || "dept".equals(type) || "post".equals(type) || "group".equals(type)) {
            list = getElementByName(hqlInfo, where, names);
        }

        // 没有找到记录
        if (list == null || list.isEmpty()) {
            throw new RuntimeException(
                    ResourceUtil.getString("sysOrgMatrix.import.field.empty", "sys-organization", null, name));
        }
        return list;
    }

    private List<SysOrgElement> getElementByName(HQLInfo hqlInfo, StringBuilder where, String[] names)
            throws Exception {
        // 组织类型支持：名称/编号
        if (names.length > 1 && StringUtil.isNotNull(names[1])) {
            where.append(" and fdNo = :fdNo ");
            hqlInfo.setParameter("fdNo", names[1]);
        }
        if (where.length() < 1) {
            return null;
        }
        hqlInfo.setWhereBlock(where.toString().substring(4));
        return sysOrgElementService.findList(hqlInfo);

    }

    private List<SysOrgElement> getPersonByName(HQLInfo hqlInfo, StringBuilder where, String[] names) throws Exception {
        // 人员类型支持：名称/登录名/编号/手机号
        if (names.length > 1 && StringUtil.isNotNull(names[1])) {
            where.append(" and fdLoginName = :fdLoginName ");
            hqlInfo.setParameter("fdLoginName", names[1]);
        }
        if (names.length > 2 && StringUtil.isNotNull(names[2])) {
            where.append(" and fdNo = :fdNo ");
            hqlInfo.setParameter("fdNo", names[2]);
        }
        if (names.length > 3 && StringUtil.isNotNull(names[3])) {
            where.append(" and fdMobileNo = :fdMobileNo ");
            hqlInfo.setParameter("fdMobileNo", names[3]);
        }
        if (where.length() < 1) {
            return null;
        }
        hqlInfo.setWhereBlock(where.substring(4));
        return sysOrgPersonService.findList(hqlInfo);
    }

    /**
     * 获取所有父级部门
     *
     * @param element
     * @return
     * @throws Exception
     */
    private List<SysOrgElement> getAllParentDeptByElement(SysOrgElement element) throws Exception {
        if (element == null) {
            return null;
        }
        if (element.getFdOrgType() != SysOrgConstant.ORG_TYPE_DEPT) {
            return null;
        }

        return element.getAllParent(false);
    }

    /**
     * 根据名称查询具体的值
     *
     * @param name
     * @param id
     * @param type
     * @param mainDataType
     * @return
     * @throws Exception
     */
    private String getMainDataByName(String name, String id, String type, String mainDataType) throws Exception {
        List<String> list = null;
        String temp = null;
        if ("sys".equals(mainDataType)) {
            // 系统内数据
            SysFormMainDataInsystem model = (SysFormMainDataInsystem) sysFormMainDataInsystemService.findByPrimaryKey(type, null, true);
            if (model != null) {
                StringBuilder where = new StringBuilder();
                where.append("1 = 1 ");
                String modelName = model.getFdModelName();
                String displayProperty = getDisplayProperty(modelName);
                HQLInfo hqlInfo = new HQLInfo();
                hqlInfo.setModelName(modelName);
                hqlInfo.setSelectBlock("fdId");
                if (StringUtil.isNotNull(id)) {
                    where.append(" and fdId = :id");
                    hqlInfo.setParameter("id", id);
                    temp = StringUtil.linkString(temp, ", ", "id=" + id);
                } else if (StringUtil.isNotNull(name)) {
                    where.append(" and ").append(displayProperty).append(" = :name");
                    hqlInfo.setParameter("name", name);
                    temp = "name=" + name;
                }
                hqlInfo.setWhereBlock(where.toString());
                list = findValue(hqlInfo);
            }
        } else if ("cust".equals(mainDataType)) {
            // 自定义数据
            StringBuilder where = new StringBuilder();
            where.append("1 = 1 ");
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setModelName(SysFormMainDataCustomList.class.getName());
            hqlInfo.setSelectBlock("fdValue");
            if (StringUtil.isNotNull(id)) {
                where.append(" and fdValue = :id");
                hqlInfo.setParameter("id", id);
                temp = StringUtil.linkString(temp, ", ", "value=" + id);
            } else if (StringUtil.isNotNull(name)) {
                where.append(" and fdValueText = :text");
                hqlInfo.setParameter("text", name);
                temp = "text=" + name;
            }
            hqlInfo.setWhereBlock(where.toString());
            list = sysFormMainDataCustomListService.findValue(hqlInfo);
        }

        // 没有找到记录
        if (list == null || list.isEmpty()) {
            throw new RuntimeException(
                    ResourceUtil.getString("sysOrgMatrix.import.field.empty", "sys-organization", null, temp));
        }
        // 找到多条记录
        if (list.size() > 1) {
            throw new RuntimeException(
                    ResourceUtil.getString("sysOrgMatrix.import.field.multiple", "sys-organization", null, temp));
        }

        return list.get(0);
    }

    /**
     * 根据条件名称查询具体的数据
     *
     * @param relation
     * @param name
     * @param id
     * @return
     * @throws Exception
     */
    private String getConditionalValue(SysOrgMatrixRelation relation, String name, String id) throws Exception {
        String type = relation.getFdType();
        if ("constant".equals(type)) {
            // 常量，直接保存
            return name;
        } else if ("org".equals(type) || "dept".equals(type) || "post".equals(type)
                || "person".equals(type) || "group".equals(type)) {
            SysOrgElement temp = getElementByName(type, name, id);
            if (temp != null) {
                //组织人员字段类型需要与导入数据人员字段相匹配
                if(!checkElementMatch(relation,temp)){
                    throw new RuntimeException(ResourceUtil.getString("sysOrgMatrix.import.error.orgDataNotMatch", "sys-organization"));
                }
                return temp.getFdId();
            }
        } else {
            // 系统内数据
            // sysFormMainDataInsystemService
            // 自定义数据
            // sysFormMainDataCustomService
            return getMainDataByName(name, id, type, relation.getFdMainDataType());
        }
        return null;
    }

    /**
     * 下载模板文档
     */
    @Override
    public Workbook downloadTemplate(String fdId) throws Exception {
        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(fdId);
        // 第一步，创建一个webbook，对应一个Excel文件
        Workbook wb = new XSSFWorkbook();
        JSONArray versions = getVersions(fdId);
        for (int i = 0; i < versions.size(); i++) {
            String version = versions.getJSONObject(i).getString("fdName");
            buildSheet(wb, matrix, version);
        }
        return wb;
    }

    /**
     * 根据版本生成Sheet
     *
     * @param matrix
     * @param version
     * @return
     * @throws Exception
     */
    private void buildSheet(Workbook wb, SysOrgMatrix matrix, String version)
            throws Exception {
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        Sheet sheet = wb.createSheet(version);
        sheet.setDefaultColumnWidth(252 * 35 + 323); // 设置宽度
        // 第三步，在sheet中添加表头第0行
        Row row = null;
        // 第四步，创建单元格
        Cell cell = null;

        // 获取表头样式
        CellStyle conditionStyle = getTitleStyle(wb, false);
        CellStyle resultStyle = getTitleStyle(wb, true);

        // 字段注释
        CreationHelper factory = wb.getCreationHelper();
        Drawing drawing = sheet.createDrawingPatriarch();

        int rowIdx = 0;
        CellRangeAddress cellRangeAddress = null;
        // 第一行是字段信息
        int rangeLength = 0;
        boolean hasRange = false;
        row = sheet.createRow(rowIdx);
        row.setHeight((short) (20 * 25));
        // 条件字段
        Collections.sort(matrix.getFdRelationConditionals());
        List<SysOrgMatrixRelation> conditionals = matrix.getFdRelationConditionals();
        for (int i = 0; i < conditionals.size(); i++) {
            SysOrgMatrixRelation relation = conditionals.get(i);
            int length = 0;
            int lastRow = rowIdx;
            if ("constant".equals(relation.getFdType())) {
                length = 1;
                lastRow += 1;
            } else {
                length = 2;
            }
            if (i == 0) {
                cellRangeAddress = new CellRangeAddress(rowIdx, lastRow, 0, length - 1);
                cell = row.createCell(0);
                com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(sheet, cellRangeAddress);
            } else {
                cellRangeAddress = new CellRangeAddress(rowIdx, lastRow, rangeLength, rangeLength + length - 1);
                cell = row.createCell(rangeLength);
                com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(sheet, cellRangeAddress);
            }
            cell.setCellStyle(conditionStyle);
            String name = relation.getFdName();
            if (relation.isRange()) {
                hasRange = true;
                if ("numRange".equals(relation.getFdType())) {
                    name += "(" + ResourceUtil.getString("sys-organization:sysOrgMatrix.range.number") + ")";
                }
            }
            cell.setCellValue(name);
            cell.setCellComment(getFieldDesc(factory, drawing, relation));
            rangeLength += length;
        }

        // 结果字段
        Collections.sort(matrix.getFdRelationResults());
        for (SysOrgMatrixRelation relation : matrix.getFdRelationResults()) {
            cellRangeAddress = new CellRangeAddress(rowIdx, rowIdx, rangeLength, rangeLength + 1);
            cell = row.createCell(rangeLength);
            com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(sheet, cellRangeAddress);
            cell.setCellStyle(resultStyle);
            cell.setCellValue(relation.getFdName());
            cell.setCellComment(getFieldDesc(factory, drawing, relation));
            rangeLength += 2;
        }

        // 增加数据类别
        if (BooleanUtils.isTrue(matrix.getFdIsEnabledCate())) {
            cellRangeAddress = new CellRangeAddress(rowIdx, rowIdx, rangeLength, rangeLength + 1);
            cell = row.createCell(rangeLength);
            com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(sheet, cellRangeAddress);
            cell.setCellStyle(resultStyle);
            cell.setCellValue(ResourceUtil.getString("sys-organization:sysOrgMatrix.dataCate.note"));
        }

        // 第二行是名称+ID
        rowIdx = 1;
        int cellIdx = 0;
        row = sheet.createRow(rowIdx);
        if (hasRange) {
            row.setHeight((short) (20 * 45));
        } else {
            row.setHeight((short) (20 * 25));
        }
        // 条件字段
        Collections.sort(matrix.getFdRelationConditionals());
        for (SysOrgMatrixRelation relation : matrix.getFdRelationConditionals()) {
            if ("constant".equals(relation.getFdType())) {
                cell = createCell(sheet, row, cellIdx);
                cell.setCellStyle(conditionStyle);
                cell.setCellValue(relation.getFdName());
                cellIdx++;
            } else if (relation.isRange()) {
                cell = createCell(sheet, row, cellIdx, 20);
                cell.setCellStyle(conditionStyle);
                cell.setCellValue(ResourceUtil.getString("sys-organization:sysOrgMatrix.range.begin"));
                cellIdx++;
                cell = createCell(sheet, row, cellIdx, 20);
                cell.setCellStyle(conditionStyle);
                cell.setCellValue(ResourceUtil.getString("sys-organization:sysOrgMatrix.range.end"));
                cellIdx++;
            } else {
                cell = createCell(sheet, row, cellIdx);
                cell.setCellStyle(conditionStyle);
                String name = relation.getFdName() + "." + ResourceUtil.getString("model.fdName");
                if ("person".equals(relation.getFdType())) {
                    // 人员类型支持：名称/登录名/编号/手机号
                    name += "/" + ResourceUtil.getString("sys-organization:sysOrgPerson.fdLoginName");
                    name += "/" + ResourceUtil.getString("sys-organization:sysOrgOrg.fdNo");
                    name += "/" + ResourceUtil.getString("sys-organization:sysOrgPerson.fdMobileNo");
                } else
                if ("org".equals(relation.getFdType()) || "dept".equals(relation.getFdType())
                        || "post".equals(relation.getFdType()) || "group".equals(relation.getFdType())) {
                    // 组织类型支持：名称/编号
                    name += "/" + ResourceUtil.getString("sys-organization:sysOrgOrg.fdNo");
                }
                cell.setCellValue(name);
                cellIdx++;
                cell = createCell(sheet, row, cellIdx);
                cell.setCellStyle(conditionStyle);
                cell.setCellValue(relation.getFdName() + ".ID");
                cellIdx++;
            }
        }

        // 结果字段
        Collections.sort(matrix.getFdRelationResults());
        for (SysOrgMatrixRelation relation : matrix.getFdRelationResults()) {
            if ("person".equals(relation.getFdType()) || "person_post".equals(relation.getFdType())) {
                cell = createCell(sheet, row, cellIdx, 32);
            } else {
                cell = createCell(sheet, row, cellIdx);
            }
            cell.setCellStyle(resultStyle);
            String name = relation.getFdName() + "." + ResourceUtil.getString("model.fdName");

            if ("person".equals(relation.getFdType())) {
                // 人员类型支持：名称/登录名/编号/手机号
                name += "/" + ResourceUtil.getString("sys-organization:sysOrgPerson.fdLoginName");
                name += "/" + ResourceUtil.getString("sys-organization:sysOrgOrg.fdNo");
                name += "/" + ResourceUtil.getString("sys-organization:sysOrgPerson.fdMobileNo");
                cell.setCellValue(name);
            } else if ("org".equals(relation.getFdType()) || "dept".equals(relation.getFdType())
                    || "post".equals(relation.getFdType()) || "group".equals(relation.getFdType())) {
                // 组织类型支持：名称/编号
                name += "/" + ResourceUtil.getString("sys-organization:sysOrgOrg.fdNo");
                cell.setCellValue(name);
            } else if ("person_post".equals(relation.getFdType())) {
                name = ResourceUtil.getString("sys-organization:sysOrgElement.person") + "." + ResourceUtil.getString("model.fdName");
                name += "/" + ResourceUtil.getString("sys-organization:sysOrgPerson.fdLoginName");
                name += "/" + ResourceUtil.getString("sys-organization:sysOrgOrg.fdNo");
                name += "/" + ResourceUtil.getString("sys-organization:sysOrgPerson.fdMobileNo");
                name += "\r\n";
                name += ResourceUtil.getString("sys-organization:sysOrgElement.post") + "." + ResourceUtil.getString("model.fdName");
                name += "/" + ResourceUtil.getString("sys-organization:sysOrgOrg.fdNo");
                cell.setCellValue(new XSSFRichTextString(name));
            }
            cellIdx++;
            cell = createCell(sheet, row, cellIdx);
            cell.setCellStyle(resultStyle);
            cell.setCellValue(relation.getFdName() + ".ID");
            cellIdx++;
        }

        // 增加数据类别
        if (BooleanUtils.isTrue(matrix.getFdIsEnabledCate())) {
            cell = createCell(sheet, row, cellIdx);
            cell.setCellStyle(resultStyle);
            cell.setCellValue(ResourceUtil.getString("sys-organization:sysOrgMatrix.dataCate.note") + "."
                    + ResourceUtil.getString("model.fdName"));
            cellIdx++;
            cell = createCell(sheet, row, cellIdx);
            cell.setCellStyle(resultStyle);
            cell.setCellValue(ResourceUtil.getString("sys-organization:sysOrgMatrix.dataCate.note") + ".ID");
            cellIdx++;
        }
    }

    /**
     * 创建一个单元格，设置列宽
     *
     * @param sheet
     * @param row
     * @param cellIdx
     * @return
     */
    private Cell createCell(Sheet sheet, Row row, int cellIdx) {
        return createCell(sheet, row, cellIdx, 25);
    }

    private Cell createCell(Sheet sheet, Row row, int cellIdx, int width) {
        Cell cell = row.createCell(cellIdx);
        // 设置列宽
        sheet.setColumnWidth(cellIdx, 252 * width + 323);
        return cell;
    }

    // ============= 导入进度 ============

    private int current;
    private int total;
    private int state;

    /**
     * 导入矩阵数据
     */
    @Override
    public JSONObject saveMatrixData(SysOrgMatrixForm matrixForm) throws Exception {
        SysOrgMatrix matrix = null;
        Workbook wb = null;
        Sheet sheet = null;
        total = 0;
        current = 0;
        InputStream input = null;
        try {
            input = matrixForm.getFile().getInputStream();
            matrix = (SysOrgMatrix) findByPrimaryKey(matrixForm.getFdId());
            // 抽象类创建Workbook，适合excel 2003和2007以上
            wb = WorkbookFactory.create(input);
            sheet = wb.getSheetAt(0);
        } catch (Exception e) {
            state = 0;
            throw new RuntimeException(ResourceUtil.getString("sysOrgMatrix.import.error", "sys-organization"));
        } finally {
            IOUtils.closeQuietly(wb);
            IOUtils.closeQuietly(input);
        }

        // 是否追加
        boolean isAppend = BooleanUtils.isTrue(matrixForm.getAppend());
        if (!isAppend) {
            // 如果不是追加，需要清空原来的数据
            clearMatrixData(matrix);
        }

        // 一份标准的模板，最少包含3行，前二行是字段描述与名称，导入的数据应该是从第四行开始
        int rowNum = sheet.getLastRowNum();
        if (rowNum < 2) {
            state = 0;
            throw new RuntimeException(ResourceUtil.getString("sysOrgMatrix.import.empty", "sys-organization"));
        }

        // 导入结果详情
        JSONObject result = new JSONObject();
        JSONArray conditionalTitle = new JSONArray();
        JSONArray resultTitle = new JSONArray();
        List<SysOrgMatrixRelation> relations = new ArrayList<SysOrgMatrixRelation>();
        // 条件字段
        if (matrix.getFdRelationConditionals() != null && !matrix.getFdRelationConditionals().isEmpty()) {
            // 排序
            Collections.sort(matrix.getFdRelationConditionals());
            relations.addAll(matrix.getFdRelationConditionals());
        }

        // 结果字段
        if (matrix.getFdRelationResults() != null && !matrix.getFdRelationResults().isEmpty()) {
            // 排序
            Collections.sort(matrix.getFdRelationResults());
            relations.addAll(matrix.getFdRelationResults());
        }

        // 增加分组
        if (BooleanUtils.isTrue(matrix.getFdIsEnabledCate())) {
            SysOrgMatrixRelation typeRelation = new SysOrgMatrixRelation();
            typeRelation.setFdIsResult(false);
            typeRelation.setFdFieldName("fd_cate_id");
            typeRelation.setFdType("fd_cate_id");
            relations.add(typeRelation);
            result.put("cateTitle", ResourceUtil.getString("sys-organization:sysOrgMatrix.dataCate"));
        }

        int rowCount = 0;
        // 记录标题
        for (SysOrgMatrixRelation relation : relations) {
            if (BooleanUtils.isTrue(relation.getFdIsResult())) {
                resultTitle.add(relation.getFdName());
                rowCount += 2;
            } else {
                if ("constant".equals(relation.getFdType())) {
                    rowCount += 1;
                } else {
                    rowCount += 2;
                }
                if ("fd_cate_id".equals(relation.getFdFieldName())) {
                    continue;
                }
                conditionalTitle.add(relation.getFdName());
            }
        }
        JSONObject datas = new JSONObject();
        // 导入工作表
        JSONArray versions = getVersions(matrix.getFdId());
        List<String> __temp = new ArrayList<String>();
        for (int i = 0; i < versions.size(); i++) {
            String version = versions.getJSONObject(i).getString("fdName");
            __temp.add(version);
        }
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            sheet = wb.getSheetAt(i);
            // 判断名称
            if (__temp.contains(sheet.getSheetName())) {
                JSONObject json = saveMatrixData(sheet, sheet.getSheetName(), matrix, relations, rowCount);
                datas.put(sheet.getSheetName(), json);
            } else {
                JSONObject json = new JSONObject();
                json.put("status", 0);
                json.put("msg", ResourceUtil.getString("sysOrgMatrix.import.error.noVersion", "sys-organization", null, sheet.getSheetName()));
                datas.put(sheet.getSheetName(), json);
                //没有匹配上版本号 模板不正确
                throw new RuntimeException(ResourceUtil.getString("sysOrganizationStaffingLevel.import.errFile", "sys-organization", null, sheet.getSheetName()));
            }
        }

        result.put("datas", datas);
        result.put("conditionalTitle", conditionalTitle);
        result.put("resultTitle", resultTitle);
        result.put("matrixId", matrixForm.getFdId());
        state = 0;
        return result;
    }

    private JSONObject saveMatrixData(Sheet sheet, String version,
                                      SysOrgMatrix matrix, List<SysOrgMatrixRelation> relations, int rowCount) throws Exception {
        total = sheet.getLastRowNum();
        current = 2;
        state = 1;
        JSONObject result = new JSONObject();
        JSONArray datas = new JSONArray();
        int success = 0;
        boolean hasCate = BooleanUtils.isTrue(matrix.getFdIsEnabledCate());
        Row row = null;

        row = sheet.getRow(1);
        // 检查导入的文件列数是否与该矩阵的列表一致
        Map<String, String> cateMap = new HashMap<String, String>();
        if (hasCate) {
            List<SysOrgMatrixDataCateForm> list = sysOrgMatrixDataCateService.getDataCates(matrix.getFdId());
            if (CollectionUtils.isNotEmpty(list)) {
                for (SysOrgMatrixDataCateForm form : list) {
                    cateMap.put(form.getFdId(), form.getFdName());
                }
            }
            // 保存到页面
            result.put("cateMap", cateMap);
        }
        if (row.getLastCellNum() != rowCount) {
            result.put("status", 0);
            result.put("msg", ResourceUtil.getString("sysOrgMatrix.import.file.error", "sys-organization"));
            return result;
        }

        // 从第三行开始取数据
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            current++;
            row = sheet.getRow(i);
            if (row == null) { // 跳过空行
                continue;
            }
            // 清空原来的数据
            JSONArray dataRow = new JSONArray();
            Iterator<SysOrgMatrixRelation> iter = relations.iterator();
            while (iter.hasNext()) {
                SysOrgMatrixRelation relation = iter.next();
                relation.setFdResultValues(null);
                relation.setFdConditionalValue(null);
                relation.setFdConditionalValue2(null);
                JSONObject obj = new JSONObject();
                obj.put("fdId", relation.getFdId());
                obj.put("fdType", relation.getFdType());
                obj.put("fdFieldName", relation.getFdFieldName());
                obj.put("fdName", relation.getFdName());
                obj.put("fdMainDataType", relation.getFdMainDataType());
                if (BooleanUtils.isTrue(relation.getFdIsResult())) {
                    obj.put("isResult", true);
                } else {
                    obj.put("isResult", false);
                }
                dataRow.add(obj);
            }

            // 获取列数
            int cellNum = row.getLastCellNum();
            if (hasCate) {
                cellNum -= 2;
            }
            int idx = 0;
            // 记录异常信息
            JSONArray dataErr = new JSONArray();
            // 记录正确的数据
            JSONArray dataSuc = new JSONArray();
            for (int j = 0; j < cellNum; j++) {
                // 记录异常数据
                JSONObject error = new JSONObject();
                int cur = j - idx;
                SysOrgMatrixRelation relation = relations.get(cur);
                String value = getCellValue(row.getCell(j));
                String id = null;
                if (!"constant".equals(relation.getFdType())) {
                    j++;
                    idx++;
                    id = getCellValue(row.getCell(j));
                }
                // 跳过空数据
                if (StringUtil.isNull(value) && StringUtil.isNull(id)) {
                    relation.setFdResultValues(null);
                    relation.setFdConditionalValue(null);
                    relation.setFdConditionalValue2(null);
                    continue;
                }
                value = value != null ? value.trim() : "";
                id = id != null ? id.trim() : "";
                error.put("id", id);
                error.put("name", value);
                try {
                    JSONObject suc = new JSONObject();
                    suc.put("name", value);
                    suc.put("id", id);
                    if (BooleanUtils.isTrue(relation.getFdIsResult())) {
                        List<SysOrgElement> list = getElementByResultNames(relation, value, id);
                        relation.setFdResultValues(list);
                        suc.put("name", relation.getFdResultValueNames());
                        suc.put("id", relation.getFdResultValueIds());
                        if ("person_post".equals(relation.getFdType())) {
                            StringBuilder types = new StringBuilder();
                            if (CollectionUtils.isNotEmpty(list)) {
                                for (SysOrgElement elem : list) {
                                    types.append(";").append(elem.getFdOrgType());
                                }
                                types.deleteCharAt(0);
                            }
                            suc.put("type", types.toString());
                        }
                    } else {
                        String temp = null;
                        String text = value;
                        if (StringUtil.isNull(text)) {
                            text = id;
                        }
                        if ("constant".equals(relation.getFdType())) {
                            // 常量，只有value
                            temp = value;
                        } else if (relation.isRange()) {
                            // 区间
                            if ("numRange".equals(relation.getFdType())) {
                                checkRange(relation.getFdType(), value, id);
                                if (StringUtil.isNotNull(value)) {
                                    relation.setFdConditionalValue(parseDouble(value));
                                }
                                if (StringUtil.isNotNull(id)) {
                                    relation.setFdConditionalValue2(parseDouble(id));
                                }
                            }
                        } else {
                            // 非常量，包含value和id
                            temp = getConditionalValue(relation, value, id);
                        }
                        // 判断唯一性
                        if (!relation.isRange() && BooleanUtils.isTrue(relation.getFdIsUnique())
                                && StringUtil.isNotNull(temp)) {
                            List<Object> _list = checkUnique(matrix.getFdSubTable(), relation.getFdFieldName(), version, temp, null);
                            if (CollectionUtils.isNotEmpty(_list)) {
                                throw new RuntimeException(ResourceUtil.getString("sysOrgMatrixRelation.fdIsUnique.err.left", "sys-organization")
                                        + text +
                                        ResourceUtil.getString("sysOrgMatrixRelation.fdIsUnique.err.right", "sys-organization"));
                            }
                        }
                        if (!relation.isRange()) {
                            relation.setFdConditionalValue(temp);
                            suc.put("id", temp);
                        }
                    }
                    suc.put("index", cur);
                    dataSuc.add(suc);
                } catch (Exception e) {
                    error.put("index", cur);
                    // 异常信息
                    error.put("msg", e.getMessage());
                    dataErr.add(error);
                    logger.error("保存矩阵数据失败：", e);
                }
            }
            if (hasCate) {
                String name = getCellValue(row.getCell(cellNum));
                cellNum++;
                String id = getCellValue(row.getCell(cellNum));
                if (StringUtil.isNotNull(id) || StringUtil.isNotNull(name)) {
                    String _name = null;
                    String _id = null;
                    if (StringUtil.isNull(id)) {
                        for (String key : cateMap.keySet()) {
                            if (cateMap.get(key).equals(name)) {
                                _name = name;
                                _id = key;
                            }
                        }
                    } else {
                        String temp = cateMap.get(id);
                        if (StringUtil.isNotNull(temp)) {
                            _name = temp;
                            _id = id;
                        }
                    }
                    if (StringUtil.isNotNull(_id)) {
                        // 导入时增加数据类别信息
                        SysOrgMatrixRelation typeRelation = relations.get(relations.size() - 1);
                        typeRelation.setFdFieldName("fd_cate_id");
                        typeRelation.setFdName(_name);
                        typeRelation.setFdConditionalValue(_id);
                        JSONObject suc = new JSONObject();
                        suc.put("id", id);
                        suc.put("name", name);
                        suc.put("type", "fd_cate_id");
                        suc.put("index", relations.size() - 1);
                        dataSuc.add(suc);
                    } else {
                        JSONObject error = new JSONObject();
                        error.put("id", id);
                        error.put("name", name);
                        error.put("index", relations.size() - 1);
                        // 异常信息
                        error.put("msg", ResourceUtil.getString("sysOrgMatrix.import.field.empty", "sys-organization",
                                null, "[id=" + id + ", name=" + name + "]"));
                        dataErr.add(error);
                    }
                }
            }
            // 判断是否有异常信息
            if (dataErr.size() > 0) {
                JSONObject obj = new JSONObject();
                obj.put("dataRow", dataRow);
                obj.put("dataErr", dataErr);
                obj.put("dataSuc", dataSuc);
                obj.put("row", i + 1);
                datas.add(obj);
            } else {
                // 只导入没有异常的数据
                saveMatrixData(matrix.getFdSubTable(), version, relations);
                success++;
            }
        }

        result.put("datas", datas);
        result.put("success", success);
        result.put("status", 1);
        return result;
    }

    /**
     * 导出矩阵数据
     */
    @Override
    public Workbook exportMatrixData(String fdId) throws Exception {
        int pageno = 1;
        int rowsize = 50;
        Page page = null;
        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(fdId);
        boolean hasCate = BooleanUtils.isTrue(matrix.getFdIsEnabledCate());
        Map<String, String> cates = new HashMap<String, String>();
        if (hasCate) {
            for (SysOrgMatrixDataCate cate : matrix.getFdDataCates()) {
                cates.put(cate.getFdId(), cate.getFdName());
            }
        }
        // 获取模板
        Workbook wk = downloadTemplate(fdId);
        // 根据版本生成工作表
        JSONArray versions = getVersions(fdId);
        for (int j = 0; j < versions.size(); j++) {
            String version = versions.getJSONObject(j).getString("fdName");
            Sheet sheet = wk.getSheetAt(j);
            sheet.setDefaultColumnWidth(25); // 设置宽度
            // 第三步，在sheet中添加表头第0行
            Row row = null;
            // 第四步，创建单元格
            Cell cell = null;
            int rowIndex = 2;
            do {
                page = findMatrixPage(fdId, version, pageno, rowsize, null);
                List<List<SysOrgMatrixRelation>> relations = page.getList();
                for (List<SysOrgMatrixRelation> relation : relations) {
                    row = sheet.createRow(rowIndex);
                    int idx = 0;
                    int count = relation.size();
                    if (hasCate) {
                        count -= 1;
                    }
                    for (int i = 1; i < count; i++) {
                        cell = row.createCell(idx);
                        SysOrgMatrixRelation temp = relation.get(i);
                        if (BooleanUtils.isTrue(temp.getFdIsResult())) {
                            String[] values = temp.getFdResultValues(true);
                            cell.setCellValue(values[0]);
                            idx++;
                            cell = row.createCell(idx);
                            cell.setCellValue(values[1]);
                        } else {
                            cell.setCellValue(temp.getFdConditionalValue() != null ? temp.getFdConditionalValue().toString() : "");
                            if (temp.isRange()) {
                                idx++;
                                cell = row.createCell(idx);
                                cell.setCellValue(temp.getFdConditionalValue2() != null ? temp.getFdConditionalValue2().toString() : "");
                            } else if (!"constant".equals(temp.getFdType())) {
                                idx++;
                                cell = row.createCell(idx);
                                cell.setCellValue(temp.getFdConditionalId());
                            }
                        }
                        idx++;
                    }
                    if (hasCate) {
                        SysOrgMatrixRelation temp = relation.get(relation.size() - 1);
                        String cateId = temp.getFdConditionalId();
                        cell = row.createCell(idx);
                        cell.setCellValue(cates.get(cateId));
                        idx++;
                        cell = row.createCell(idx);
                        cell.setCellValue(cateId);
                    }
                    rowIndex++;
                }
                pageno++;
            } while (page.isHasNextPage());
        }

        return wk;
    }

    /**
     * 获取字段描述
     *
     * @param relation
     * @return
     */
    private Comment getFieldDesc(CreationHelper factory, Drawing drawing, SysOrgMatrixRelation relation) {
        String msg = ResourceUtil.getString("sysOrgMatrix.template.field.desc", "sys-organization");
        String type = relation.getFdType();
        if (BooleanUtils.isTrue(relation.getFdIsResult())) {
            // 结果
            msg += ResourceUtil.getString("sysOrgMatrix.template.field.model.desc", "sys-organization");
            if ("person_post".equals(relation.getFdType())) {
                msg += "\r\n" + ResourceUtil.getString("sysOrgMatrix.template.field.desc.personPost", "sys-organization");
            } else {
                msg += "\r\n" + ResourceUtil.getString("sysOrgMatrix.template.field.desc.multiple", "sys-organization");
            }
        } else {
            // 区间
            if ("numRange".equals(type)) {
                msg += ResourceUtil.getString("sysOrgMatrix.template.field.numRange.desc", "sys-organization");
            } else if ("constant".equals(type)) { // 条件
                msg += ResourceUtil.getString("sysOrgMatrix.template.field.constant.desc", "sys-organization");
            } else {
                if ("org".equals(type) || "dept".equals(type) || "post".equals(type) || "person".equals(type) || "group".equals(type)) {
                    msg += ResourceUtil.getString("sysOrgMatrix.template.field.model.desc", "sys-organization");
                } else {
                    // 主数据
                    if ("cust".equals(relation.getFdMainDataType())) {
                        msg += ResourceUtil.getString("sysOrgMatrix.template.field.cust.desc", "sys-organization");
                    } else {
                        msg += ResourceUtil.getString("sysOrgMatrix.template.field.model.desc", "sys-organization");
                    }
                }
                msg += "\r\n" + ResourceUtil.getString("sysOrgMatrix.template.field.desc.single", "sys-organization");
            }
        }
        ClientAnchor clientAnchor = factory.createClientAnchor();
        clientAnchor.setCol1(3);
        clientAnchor.setRow1(3);
        clientAnchor.setCol2(5);
        clientAnchor.setRow2(10);
        Comment comment = drawing.createCellComment(clientAnchor);
        RichTextString text = factory.createRichTextString(msg);
        comment.setString(text);
        comment.setAuthor("panyh");
        return comment;
    }

    /**
     * 获取表头样式
     *
     * @param wb
     * @return
     */
    private CellStyle getTitleStyle(Workbook wb, boolean isResult) {
        // 标题样式（居中，加粗）
        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER); // 水平居中
        titleStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
        titleStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND); // 背景
        if (isResult) {
            titleStyle.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.PALE_BLUE.index); // 浅蓝
        } else {
            titleStyle.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.GREY_25_PERCENT.index); // 灰色
        }
        // 边框
        titleStyle.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN); // 下边框
        titleStyle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN); // 左边框
        titleStyle.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN); // 上边框
        titleStyle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN); // 右边框
        Font ztFont = wb.createFont();
        ztFont.setBold(true); // 加粗
        titleStyle.setFont(ztFont);
        titleStyle.setWrapText(true); // 强制换行
        return titleStyle;
    }

    private DecimalFormat formatter = new DecimalFormat(
            "####################.#########");

    /**
     * 获取Excel单元格的字符串值
     *
     * @param cell
     * @return
     */
    public String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        String rtnStr;
        switch (cell.getCellType()) {
            case BOOLEAN:
                rtnStr = new Boolean(cell.getBooleanCellValue()).toString();
                break;
            case FORMULA: {
                rtnStr = formatter.format(cell.getNumericCellValue());
                break;
            }
            case NUMERIC: {
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) { // 处理日期、时间格式
                    SimpleDateFormat sdf = null;
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                    rtnStr = sdf.format(cell.getDateCellValue());
                } else {
                    Double d = cell.getNumericCellValue();
                    cell.setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
                    rtnStr = cell.getRichStringCellValue().getString();
                    cell.setCellValue(d);
                    cell.setCellType(org.apache.poi.ss.usermodel.CellType.NUMERIC);
                }
                break;
            }
            case BLANK:
            case ERROR:
                rtnStr = "";
                break;
            default:
                rtnStr = cell.getRichStringCellValue().getString();
        }
        return rtnStr.trim();
    }

    /**
     * 保存矩阵数据（一般用于导入失败后的重试）
     */
    @Override
    public JSONObject saveMatrixData(String fdMatrixId, JSONObject json) throws Exception {
        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(fdMatrixId);
        boolean hasCate = BooleanUtils.isTrue(matrix.getFdIsEnabledCate());

        // 导入结果详情
        JSONObject result = new JSONObject();
        JSONArray conditionalTitle = new JSONArray();
        JSONArray resultTitle = new JSONArray();
        // 检查导入的文件列数是否与该矩阵的列表一致
        Map<String, String> cateMap = new HashMap<String, String>();
        if (hasCate) {
            List<SysOrgMatrixDataCateForm> list = sysOrgMatrixDataCateService.getDataCates(matrix.getFdId());
            if (CollectionUtils.isNotEmpty(list)) {
                for (SysOrgMatrixDataCateForm form : list) {
                    cateMap.put(form.getFdId(), form.getFdName());
                }
            }
        }

        List<SysOrgMatrixRelation> relations = new ArrayList<SysOrgMatrixRelation>();
        // 条件字段
        if (matrix.getFdRelationConditionals() != null && !matrix.getFdRelationConditionals().isEmpty()) {
            // 排序
            Collections.sort(matrix.getFdRelationConditionals());
            relations.addAll(matrix.getFdRelationConditionals());
        }

        // 结果字段
        if (matrix.getFdRelationResults() != null && !matrix.getFdRelationResults().isEmpty()) {
            // 排序
            Collections.sort(matrix.getFdRelationResults());
            relations.addAll(matrix.getFdRelationResults());
        }

        // 记录标题
        for (SysOrgMatrixRelation relation : relations) {
            if (BooleanUtils.isTrue(relation.getFdIsResult())) {
                resultTitle.add(relation.getFdName());
            } else {
                conditionalTitle.add(relation.getFdName());
            }
        }
        // 增加分组
        if (BooleanUtils.isTrue(matrix.getFdIsEnabledCate())) {
            SysOrgMatrixRelation typeRelation = new SysOrgMatrixRelation();
            typeRelation.setFdIsResult(false);
            typeRelation.setFdFieldName("fd_cate_id");
            typeRelation.setFdType("fd_cate_id");
            relations.add(typeRelation);
            result.put("cateTitle", ResourceUtil.getString("sys-organization:sysOrgMatrix.dataCate"));
        }

        JSONObject datas = new JSONObject();
        if (json != null && !json.isEmpty()) {
            for (Object version : json.keySet()) {
                JSONObject __result = new JSONObject();
                __result.put("cateMap", cateMap);
                int success = 0;
                JSONArray __datas = new JSONArray();
                String _version = version.toString();
                JSONArray array = json.getJSONArray(_version);
                for (int i = 0; i < array.size(); i++) {
                    JSONObject row = array.getJSONObject(i);
                    int rowIndex = row.getIntValue("row");
                    JSONArray data = row.getJSONArray("dataSuc");
                    // 清空原来的数据
                    JSONArray dataRow = new JSONArray();
                    for (SysOrgMatrixRelation relation : relations) {
                        relation.setFdResultValues(null);
                        relation.setFdConditionalValue(null);
                        relation.setFdConditionalValue2(null);
                        JSONObject obj = new JSONObject();
                        obj.put("fdId", relation.getFdId());
                        obj.put("fdType", relation.getFdType());
                        obj.put("fdFieldName", relation.getFdFieldName());
                        obj.put("fdName", relation.getFdName());
                        obj.put("fdMainDataType", relation.getFdMainDataType());
                        if (BooleanUtils.isTrue(relation.getFdIsResult())) {
                            obj.put("isResult", true);
                        } else {
                            obj.put("isResult", false);
                        }
                        dataRow.add(obj);
                    }
                    // 记录异常信息
                    JSONArray dataErr = new JSONArray();
                    // 记录正确的数据
                    JSONArray dataSuc = new JSONArray();
                    for (int j = 0; j < data.size(); j++) {
                        JSONObject _json = data.getJSONObject(j);
                        // 记录异常数据
                        JSONObject error = new JSONObject();
                        int index = _json.getIntValue("index");
                        SysOrgMatrixRelation relation = relations.get(index);
                        String value = _json.getString("name");
                        String id = null;
                        if (!"constant".equals(relation.getFdType())) {
                            id = _json.getString("id");
                        }

                        // 跳过空数据
                        if (StringUtil.isNull(value) && StringUtil.isNull(id)) {
                            relation.setFdResultValues(null);
                            relation.setFdConditionalValue(null);
                            relation.setFdConditionalValue2(null);
                            continue;
                        }
                        value = value != null ? value.trim() : "";
                        id = id != null ? id.trim() : "";
                        error.put("id", id);
                        error.put("name", value);
                        try {
                            JSONObject suc = new JSONObject();
                            suc.put("name", value);
                            if ("fd_cate_id".equals(relation.getFdFieldName())) {
                                // 处理分组
                                if (!cateMap.containsKey(id)) {
                                    throw new RuntimeException(ResourceUtil.getString("sysOrgMatrix.import.field.empty",
                                            "sys-organization", null, "[id=" + id + ", name=" + value + "]"));
                                }
                                relation.setFdConditionalValue(id);
                            } else if (BooleanUtils.isTrue(relation.getFdIsResult())) {
                                List<SysOrgElement> list = getElementByResultNames(relation, value, id);
                                relation.setFdResultValues(list);
                                suc.put("name", relation.getFdResultValueNames());
                                suc.put("id", relation.getFdResultValueIds());
                                if ("person_post".equals(relation.getFdType())) {
                                    StringBuilder types = new StringBuilder();
                                    if (CollectionUtils.isNotEmpty(list)) {
                                        for (SysOrgElement elem : list) {
                                            types.append(";").append(elem.getFdOrgType());
                                        }
                                        types.deleteCharAt(0);
                                    }
                                    suc.put("type", types.toString());
                                }
                            } else {
                                String temp = id;
                                if (relation.isRange()) {
                                    // 区间
                                    if ("numRange".equals(relation.getFdType())) {
                                        checkRange(relation.getFdType(), value, id);
                                        if (StringUtil.isNotNull(value)) {
                                            relation.setFdConditionalValue(parseDouble(value));
                                        }
                                        if (StringUtil.isNotNull(id)) {
                                            relation.setFdConditionalValue2(parseDouble(id));
                                        }
                                    }
                                } else {
                                    temp = getConditionalValue(relation, value, id);
                                    String text = value;
                                    if (StringUtil.isNull(text)) {
                                        text = id;
                                    }
                                    // 判断唯一性
                                    if (BooleanUtils.isTrue(relation.getFdIsUnique()) && StringUtil.isNotNull(temp)) {
                                        List<Object> _list = checkUnique(matrix.getFdSubTable(), relation.getFdFieldName(), _version, temp, null);
                                        if (CollectionUtils.isNotEmpty(_list)) {
                                            throw new RuntimeException(ResourceUtil.getString(
                                                    "sysOrgMatrixRelation.fdIsUnique.err.left", "sys-organization") + text
                                                    + ResourceUtil.getString("sysOrgMatrixRelation.fdIsUnique.err.right", "sys-organization"));
                                        }
                                    }
                                    relation.setFdConditionalValue(temp);
                                }
                                suc.put("id", temp);
                            }
                            suc.put("index", index);
                            dataSuc.add(suc);
                        } catch (Exception e) {
                            error.put("index", index);
                            // 异常信息
                            error.put("msg", e.getMessage());
                            dataErr.add(error);
                        }
                    }
                    // 判断是否有异常信息
                    if (dataErr.size() > 0) {
                        JSONObject obj = new JSONObject();
                        obj.put("dataRow", dataRow);
                        obj.put("dataErr", dataErr);
                        obj.put("dataSuc", dataSuc);
                        obj.put("row", rowIndex);
                        __datas.add(obj);
                    } else {
                        // 只导入没有异常的数据
                        saveMatrixData(matrix.getFdSubTable(), _version, relations);
                        success++;
                    }
                }
                __result.put("datas", __datas);
                __result.put("success", success);
                datas.put(_version, __result);
            }
        }

        result.put("datas", datas);
        result.put("conditionalTitle", conditionalTitle);
        result.put("resultTitle", resultTitle);
        result.put("matrixId", matrix.getFdId());
        return result;
    }

    /**
     * 矩阵计算
     */
    @Override
    public List<SysOrgElement> matrixCalculation(JSONObject json) throws Exception {
        List<List<SysOrgElement>> list = matrixCalculationByGroup(json);
        Set<SysOrgElement> data = new HashSet<SysOrgElement>();
        for (List<SysOrgElement> temp : list) {
            data.addAll(temp);
        }
        return new ArrayList<SysOrgElement>(data);
    }

    @Override
    @Deprecated
    public List<SysOrgElement> matrixCalculation(net.sf.json.JSONObject json) throws Exception {
        return matrixCalculation((JSONObject) JSONObject.toJSON(json));
    }

    @Override
    @Deprecated
    public List<List<SysOrgElement>> matrixCalculationByGroup(net.sf.json.JSONObject json) throws Exception {
        return matrixCalculationByGroup((JSONObject) JSONObject.toJSON(json));
    }

    /**
     * 矩阵计算 按结果进行分组：
     * <p>
     * 结果1： 人员1，人员2，人员3
     * 结果2： 人员1，人员4
     *
     * <pre>
     * [
     *   [人员1, 人员2, 人员3],
     *   [人员1, 人员4]
     * ]
     * </pre>
     */
    @Override
    public List<List<SysOrgElement>> matrixCalculationByGroup(JSONObject json) throws Exception {
        // json数据格式如下：
        // {'id': '矩阵ID', 'version': 'V1', 'results': '结果1ID;结果2ID', 'option': 1, 'conditionals':
        // [{'id':'条件1ID', 'type': 'fdId/fdName', 'value': '条件值1'},
        // {'id':'条件2ID', 'type': 'fdId/fdName', 'value': '条件值2'}]}
        if (logger.isInfoEnabled()) {
            logger.info("矩阵计算：" + json);
        }
        // 返回所有匹配的组织元素
        List<List<String>> rtnIds = new ArrayList<List<String>>();
        String id = json.getString("id");
        String version = null;
        if (json.containsKey("version")) {
            version = json.getString("version");
        }
        // 没有版本号时，取最新版本
        if (StringUtil.isNull(version)) {
            JSONArray array = getVersions(id);
            if (array != null && !array.isEmpty()) {
                // 如果没有传版本，获取最新已激活版本
                for (int i = array.size() - 1; i >= 0; i--) {
                    JSONObject obj = array.getJSONObject(i);
                    if (obj.getBooleanValue("fdIsEnable")) {
                        version = obj.getString("fdName");
                        break;
                    }
                }
            }
        }
        // 使用默认版本
        if (StringUtil.isNull(version)) {
            version = "V1";
        }
        if (StringUtil.isNull(id)) {
            throw new RuntimeException(ResourceUtil.getString("sysOrgMatrix.calculation.id.empty", "sys-organization"));
        }
        String results = json.getString("results");
        if (StringUtil.isNull(results)) {
            throw new RuntimeException(ResourceUtil.getString("sysOrgMatrix.calculation.result.empty", "sys-organization"));
        }

        JSONArray conditionals = json.getJSONArray("conditionals");
        if (conditionals == null || conditionals.isEmpty()) {
            throw new RuntimeException(ResourceUtil.getString("sysOrgMatrix.calculation.conditional.empty", "sys-organization"));
        }
        // 检查版本是否激活
        if (!sysOrgMatrixVersionService.isEnable(id, version)) {
            throw new RuntimeException(ResourceUtil.getString("sysOrgMatrix.calculation.version.disable", "sys-organization", null, version));
        }

        List<String> resultIds = Arrays.asList(results.replaceAll(" ", "").split("[;,]"));
        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(id);
        Map<String, String> relationResults = new HashMap<String, String>();
        for (SysOrgMatrixRelation relation : matrix.getFdRelationResults()) {
            relationResults.put(relation.getFdId(), relation.getFdFieldName());
        }

        // 记录条件的位置
        List<String> relationConditionals = new ArrayList<String>();
        Map<String, Integer> conIdx = new HashMap<String, Integer>();
        for (int i = 0; i < matrix.getFdRelationConditionals().size(); i++) {
            SysOrgMatrixRelation relation = matrix.getFdRelationConditionals().get(i);
            relationConditionals.add(relation.getFdFieldName());
            conIdx.put(relation.getFdId(), i);
        }
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        // 条件也要拼接
        for (String conditional : relationConditionals) {
            sql.append("model.").append(conditional).append(",");
        }
        // 获取结果字段名
        for (String resultId : resultIds) {
            String fieldName = relationResults.get(resultId);
            if (fieldName != null) {
                sql.append("model.").append(fieldName).append(",");
            }
            // 初始化返回值容器
            rtnIds.add(new ArrayList<String>());
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" FROM ").append(matrix.getFdSubTable()).append(" model");

        StringBuilder where = new StringBuilder();
        StringBuilder join = new StringBuilder();

        where.append(" WHERE model.fd_version = :version ");

        // 获取条件字段名
        Map<String, SysOrgMatrixRelation> relationMap = getRelationMap(matrix);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("version", version);

        // 当部门选择向下匹配时，如果找到多条件记录，需要看最近原则取第一条
        // 这里的代码假设只有一个部门条件
        // 这里保存的部门是从最近到远的顺序
        List<String> deptIds = new ArrayList<String>();
        // 记录包含子部门最近取值的条件位置，查询结果需要根据此位置处理
        int deptIdx = 0;
        for (int i = 0; i < conditionals.size(); i++) {
            JSONObject conditional = conditionals.getJSONObject(i);
            SysOrgMatrixRelation relation = relationMap.get(conditional.getString("id"));
            if (relation != null) {
                String type = relation.getFdType();
                String param = relation.getFdId() + "_" + i;
                // 条件参数可能为空
                String value = conditional.getString("value");
                //是否部门且向下匹配
                Boolean deptFlag = ("dept".equals(type) && (relation.getFdIncludeSubDept() == null ? false : relation.getFdIncludeSubDept()));
                if (StringUtil.isNotNull(value) && !deptFlag) {
                    params.put(param, value.toLowerCase());
                } else {
                    param = null;
                }
                SysOrgElement element = null;
                if (relation.isRange()) {
                    if (StringUtil.isNotNull(value)) {
                        // 区间类型
                        if ("numRange".equals(relation.getFdType())) {
                            where.append(" AND ((model.").append(relation.getFdFieldName()).append(" IS NULL OR model.")
                                    .append(relation.getFdFieldName()).append(" <= :").append(param).append(")");

                            where.append(" AND (model.").append(relation.getFdFieldName2()).append(" IS NULL OR model.")
                                    .append(relation.getFdFieldName2()).append(" >= :").append(param).append("))");
                            try {
                                // 数值类型，需要转换
                                params.put(param, parseDouble(value));
                            } catch (Exception e) {
                                // 数值区间，只能接收数字类型，非数据类型会抛异常
                                throw new KmssRuntimeException(new KmssMessage("errors.number", relation.getFdName()));
                            }
                        }
                    } else {
                        // 区间类型，传入一个空值时，匹配2个字段都为空的数据
                        where.append(" AND (model.").append(relation.getFdFieldName()).append(" IS NULL AND model.")
                                .append(relation.getFdFieldName2()).append(" IS NULL)");
                    }
                } else
                    // 常量不需要匹配查询类型
                    if ("constant".equals(type) || "fdId".equals(conditional.getString("type"))) {
                        if (StringUtil.isNotNull(value)) {
                            element = (SysOrgElement) sysOrgElementService.findByPrimaryKey(value.toLowerCase());
                        } else {
                            // 如果传入的参数值为 null 或 空，也需要拼接条件
                            where.append(" AND (model.").append(relation.getFdFieldName()).append(" IS NULL or model.").append(relation.getFdFieldName()).append(" = '')");
                        }
                        if (element == null) {
                            continue;
                        }
                        //部门类型勾选了匹配时向下匹配部门
                        if (deptFlag && param == null) {
                            String[] split = element.getFdHierarchyId().split(BaseTreeConstant.HIERARCHY_ID_SPLIT);
                            List<String> __ids = new ArrayList<String>();
                            for (String __id : split) {
                                if (StringUtil.isNotNull(__id)) {
                                    __ids.add(__id);
                                }
                            }

                            // 记录该部门的层级
                            if (CollectionUtils.isEmpty(deptIds)) {
                                deptIdx = conIdx.get(relation.getFdId());
                                deptIds.addAll(__ids);
                                // 逆序
                                Collections.reverse(deptIds);
                            }
                            String paramKey = relation.getFdId() + "_" + i + "_ids";
                            where.append(" AND model.").append(relation.getFdFieldName()).append(" IN (:").append(paramKey).append(")");
                            params.put(paramKey, __ids);
                        } else {
                            // 矩阵表保存的是ID，如果要查询NAME，需要做表关联查询
                            where.append(" AND (lower(model.").append(relation.getFdFieldName()).append(")").append(param == null ? " IS NULL" : " = :" + param).append(")");
                        }
                    } else {
                        List<String> __names = new ArrayList<String>();
                        String paramKey = relation.getFdId() + "_" + i + "_names";
                        String mainDataType = relation.getFdMainDataType();
                        // 传入的参数可能是ID或NAME
                        if ("org".equals(type) || "dept".equals(type) || "post".equals(type) || "person".equals(type) || "group".equals(type)) {
                            join.append(" LEFT JOIN sys_org_element elem").append(i).append(" ON elem").append(i).append(".fd_id = model.").append(relation.getFdFieldName());
                            // 处理官方语言
                            String fdNameLang = SysLangUtil.getLangFieldName("fdName");
                            if (param == null && !deptFlag) {
                                where.append(" AND elem").append(i).append(".fd_name IS NULL");
                            } else {
                                // 传入名称时向下匹配
                                if (deptFlag) {
                                    List<SysOrgElement> nameList = getElementByName(type, value.toLowerCase());
                                    List<String> __ids = new ArrayList<String>();
                                    for (SysOrgElement ele : nameList) {
                                        List<SysOrgElement> parentList = getAllParentDeptByElement(ele);
                                        for (SysOrgElement el : parentList) {
                                            if (SysOrgConstant.ORG_TYPE_DEPT == el.getFdOrgType()) {
                                                __names.add(el.getFdName());
                                                __ids.add(el.getFdId());
                                            }
                                        }
                                    }

                                    // 记录该部门的层级
                                    if (CollectionUtils.isEmpty(deptIds)) {
                                        deptIds.addAll(__ids);
                                    }
                                    where.append(" AND (lower(elem").append(i).append(".fd_name) IN (:").append(paramKey).append(")");
                                } else {
                                    where.append(" AND (lower(elem").append(i).append(".fd_name) = :").append(param);
                                }
                            }
                            // 处理多语言
                            if (!"fdName".equals(fdNameLang)) {
                                if (param == null && !deptFlag) {
                                    where.append(" OR elem").append(i).append(".").append(toFieldName("fdName")).append(" IS NULL");
                                } else {
                                    // 部门向下匹配
                                    if (deptFlag) {
                                        where.append(" OR lower(elem").append(i).append(".").append(toFieldName("fdName")).append(") IN (:").append(paramKey).append(")");
                                    } else {
                                        where.append(" OR lower(elem").append(i).append(".").append(toFieldName("fdName")).append(") = :").append(param);
                                    }
                                }
                            }
                            where.append(")");
                            if (deptFlag) {
                                params.put(paramKey, __names);
                            }
                        } else if ("sys".equals(mainDataType)) {
                            // 获取主数据中的模块表名
                            SysFormMainDataInsystem model = (SysFormMainDataInsystem) sysFormMainDataInsystemService.findByPrimaryKey(type, null, true);
                            SysDictModel dictModel = SysDataDict.getInstance().getModel(model.getFdModelName());
                            String displayProperty = dictModel.getDisplayProperty();
                            if (StringUtil.isNull(displayProperty)) {
                                throw new RuntimeException(
                                        ResourceUtil.getString("sysOrgMatrix.import.displayProperty.non.existent", "sys-organization", null, model.getFdModelName()));
                            }
                            SysDictCommonProperty commonProperty = dictModel.getPropertyMap().get(displayProperty);
                            String table = dictModel.getTable();
                            if ("sys_org_person".equals(table)) {
                                table = "sys_org_element";
                            }
                            join.append(" LEFT JOIN ").append(table).append(" maindata").append(i).append(" ON maindata").append(i).append(".fd_id = model.").append(relation.getFdFieldName());
                            // 处理官方语言
                            String displayPropertyLang = SysLangUtil.getLangFieldName(displayProperty);
                            if (param == null) {
                                where.append(" AND (maindata").append(i).append(".").append(commonProperty.getColumn()).append(" IS NULL");
                            } else {
                                where.append(" AND (lower(maindata").append(i).append(".").append(commonProperty.getColumn()).append(") = :").append(param);
                            }
                            // 处理多语言
                            if (!displayProperty.equals(displayPropertyLang)) {
                                if (param == null) {
                                    where.append(" OR maindata").append(i).append(".").append(toFieldName(displayProperty)).append(" IS NULL");
                                } else {
                                    where.append(" OR lower(maindata").append(i).append(".").append(toFieldName(displayProperty)).append(") = :").append(param);
                                }
                            }
                            where.append(")");
                        } else if ("cust".equals(mainDataType)) {
                            where.append(" AND model.").append(relation.getFdFieldName()).append(" in (");
                            where.append("SELECT fd_value FROM sys_xform_main_data_cuslist WHERE sys_form_main_data_custom_id = '");
                            if (param == null) {
                                where.append(relation.getFdType()).append("' AND (fd_value_text IS NULL");
                            } else {
                                where.append(relation.getFdType()).append("' AND (lower(fd_value_text) = :").append(param);
                            }
                            // 处理官方语言
                            String fdValueTextLang = SysLangUtil.getLangFieldName("fdValueText");
                            // 处理多语言
                            if (!"fdValueText".equals(fdValueTextLang)) {
                                if (param == null) {
                                    where.append(" OR ").append(toFieldName("fdValueText")).append(" IS NULL");
                                } else {
                                    where.append(" OR lower(").append(toFieldName("fdValueText")).append(") = :").append(param);
                                }
                            }
                            where.append("))");
                        }
                    }
            }
        }
        sql.append(join).append(where);
        if (logger.isInfoEnabled()) {
            logger.info("矩阵计算SQL：" + sql);
            logger.info("矩阵计算SQL参数：" + params);
            logger.info("矩阵计算条件参数：" + conditionals);
        }
        NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
        try {
            for (String key : params.keySet()) {
                Object val = params.get(key);
                if (val instanceof Collection) {
                    query.setParameterList(key, (Collection) val);
                } else {
                    query.setParameter(key, val);
                }
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.debug("矩阵计算SQL：" + sql.toString());
        }

        List<Object> result = query.list();

        if (result != null && !result.isEmpty()) {
            // 1、取第1个；2、取全部；3、提示错误（抛异常）
            int option = 1;
            if (json.containsKey("option")) {
                option = json.getIntValue("option");
            }
            if (option == 3 && result.size() > 1) {
                // 发现多条记录，且功能设置为抛异常
                throw new KmssRuntimeException(new KmssMessage("sys-organization:error.sysOrgMatrix.multiple.records"));
            }
            // 如果只取第1条数据，并且查询的数据大于1条，并且部门开启了向下匹配，此时需要使用最近原则
            if (option == 1 && result.size() > 1 && !deptIds.isEmpty()) {
                // 为以部门条件为KEY，匹配好对应关系
                Map<String, Object[]> _result = new HashMap<String, Object[]>();
                for (int i = 0; i < result.size(); i++) {
                    Object objs = result.get(i);
                    if (objs instanceof Object[]) {
                        Object[] temps = (Object[]) objs;
                        _result.put(temps[deptIdx].toString(), temps);
                    }
                }
                // 从近到远按顺序取值
                for (String dept : deptIds) {
                    Object[] data = _result.get(dept);
                    if (data != null) {
                        // 取值的开始位置需要跳过条件字段
                        for (int j = relationConditionals.size(); j < data.length; j++) {
                            Object obj = data[j];
                            if (obj == null) {
                                continue;
                            }
                            // 取值的开始位置需要跳过条件字段
                            List<String> ids = rtnIds.get(j - relationConditionals.size());
                            String temp = obj.toString();
                            addResultId(ids, temp);
                        }
                        break;
                    }
                }
            } else {
                // 获取结果数据
                for (int i = 0; i < result.size(); i++) {
                    Object objs = result.get(i);
                    if (objs instanceof Object[]) {
                        Object[] temps = (Object[]) objs;
                        for (int j = relationConditionals.size(); j < temps.length; j++) {
                            Object obj = temps[j];
                            if (obj == null) {
                                continue;
                            }
                            List<String> ids = rtnIds.get(j - relationConditionals.size());
                            String temp = obj.toString();
                            addResultId(ids, temp);
                        }
                    } else {
                        List<String> ids = rtnIds.get(0);
                        String temp = (String) objs;
                        if (temp != null) {
                            addResultId(ids, temp);
                        }
                    }
                    // 只取第一条记录
                    if (option == 1) {
                        break;
                    }
                }
            }
        }
        // 返回所有结果
        List<List<SysOrgElement>> rtnList = new ArrayList<List<SysOrgElement>>(rtnIds.size());
        for (List<String> ids : rtnIds) {
            if (CollectionUtils.isNotEmpty(ids)) {
                rtnList.add(sysOrgElementService.findByPrimaryKeys(ids.toArray(new String[]{})));
            } else {
                rtnList.add(Collections.EMPTY_LIST);
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("矩阵计算结果：" + rtnList);
        }
        return rtnList;
    }

    private void addResultId(List<String> ids, String temp) {
        if (temp.startsWith("{") && temp.endsWith("}")) {
            JSONObject json = JSONObject.parseObject(temp);
            if (!json.isEmpty()) {
                for (Object key : json.keySet()) {
                    String val = json.getString(key.toString());
                    if (StringUtil.isNotNull(val) && !ids.contains(val)) {
                        ids.add(val);
                    }
                }
            }
        } else {
            for (String s : temp.split("[;,]")) {
                if (StringUtil.isNotNull(s) && !ids.contains(s)) {
                    ids.add(s);
                }
            }
        }
    }

    /**
     * 属性名称转数据库字段名
     * <p>
     * fdNameCn -> fd_name_cn
     *
     * @param name
     * @return
     */
    private String toFieldName(String name) {
        StringBuilder sb = new StringBuilder();
        if (StringUtil.isNotNull(name)) {
            char[] array = name.toCharArray();
            for (char c : array) {
                if (c >= 'A' && c <= 'Z') {
                    sb.append("_").append((char) (c + 32));
                } else {
                    sb.append(c);
                }
            }
            String curLocale = SysLangUtil.getCurrentLocaleCountry();
            sb.append("_").append(curLocale.toLowerCase());
        }
        return sb.toString();
    }

    private Map<String, SysOrgMatrixRelation> getRelationMap(SysOrgMatrix matrix) {
        Map<String, SysOrgMatrixRelation> map = new HashMap<String, SysOrgMatrixRelation>();
        for (SysOrgMatrixRelation relation : matrix.getFdRelationConditionals()) {
            map.put(relation.getFdId(), relation);
        }
        return map;
    }

    /**
     * 获取矩阵数据
     */
    @Override
    public List<Map<String, Object>> getDataList(RequestContext requestInfo)
            throws Exception {
        List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
        String type = requestInfo.getParameter("type");
        // 查看导入进度
        if ("progress".equals(type)) {
            Map<String, Object> node = new HashMap<String, Object>();
            node.put("current", current);
            node.put("total", total);
            node.put("state", state);
            rtnList.add(node);
            return rtnList;
        }
        // 校验唯一性
        if ("unique".equals(type)) {
            return checkUnique(requestInfo);
        }
        // 选择人员，需要把岗位带出来
        if ("get_post".equals(type)) {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, String> map = new HashMap<String, String>();
            // 单人员
            String person = requestInfo.getParameter("person");
            if (StringUtil.isNotNull(person)) {
                SysOrgElement _person = (SysOrgElement) sysOrgElementService.findByPrimaryKey(person);
                if (_person != null) {
                    getPost(list, _person.getFdPosts());
                }
            }
            // 多人员
            String persons = requestInfo.getParameter("persons");
            if (StringUtil.isNotNull(persons)) {
                List<SysOrgElement> _persons = sysOrgElementService.findByPrimaryKeys(persons.split("[;,]"));
                if (CollectionUtils.isNotEmpty(_persons)) {
                    for (SysOrgElement _person : _persons) {
                        getPost(list, _person.getFdPosts());
                    }
                }
            }
            // 过滤重复岗位
            if (CollectionUtils.isNotEmpty(list)) {
                for (Map<String, Object> temp : list) {
                    String postId = (String) temp.get("postId");
                    if (map.containsKey(postId)) {
                        // 重复
                        continue;
                    }
                    map.put(postId, postId);
                    rtnList.add(temp);
                }
            }
            return rtnList;
        }

        String parent = requestInfo.getParameter("parent");
        String id = requestInfo.getParameter("id");
        int rtnType = StringUtil.getIntFromString(requestInfo.getParameter("rtnType"), 3);

        // 如果ID为空，则获取矩阵分类与矩阵的信息
        if (StringUtil.isNull(id)) {
            // 获取矩阵分类
            List<SysOrgMatrixCate> cateList = sysOrgMatrixCateService.findByParent(parent);
            if (cateList != null && !cateList.isEmpty()) {
                for (SysOrgMatrixCate cate : cateList) {
                    // 取模块信息
                    Map<String, Object> node = new HashMap<String, Object>();
                    node.put("text", cate.getFdName());
                    node.put("nodeType", "CATEGORY");
                    node.put("value", cate.getFdId());
                    node.put("isShowCheckBox", "false");
                    rtnList.add(node);
                }
            }
            // 获取矩阵信息
            List<SysOrgMatrix> list = findByCate(parent);
            if (list != null && !list.isEmpty()) {
                for (SysOrgMatrix matrix : list) {
                    // 这里的数据是返回给流程使用，需要过滤没有可用版本的矩阵
                    boolean isOk = false;
                    List<SysOrgMatrixVersion> versions = matrix.getFdVersions();
                    if (CollectionUtils.isNotEmpty(versions)) {
                        for (SysOrgMatrixVersion version : versions) {
                            if (BooleanUtils.isTrue(version.getFdIsEnable())) {
                                isOk = true;
                                break;
                            }
                        }
                    }
                    if (!isOk) {
                        continue;
                    }
                    Map<String, Object> node = new HashMap<String, Object>();
                    node.put("text", matrix.getFdName());
                    node.put("value", matrix.getFdId());

                    rtnList.add(node);
                }
            }
        } else {
            // 如果ID不为空，则认为是获取矩阵关系信息
            // SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(id);
            // 懒加载返回代理对象会导致复制流程图时因为id所对应的矩阵不存在而导致调用对象发生异常
            SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(id,
                    SysOrgMatrix.class.getName(), true);
            if (matrix != null) {
                List<SysOrgMatrixRelation> relations = new ArrayList<SysOrgMatrixRelation>();
                if ((rtnType & 1) == 1) {
                    // 获取条件字段
                    List<SysOrgMatrixRelation> conditionals = matrix
                            .getFdRelationConditionals();
                    if (conditionals != null && !conditionals.isEmpty()) {
                        relations.addAll(conditionals);
                    }
                }
                if ((rtnType & 2) == 2) {
                    // 获取结果字段
                    List<SysOrgMatrixRelation> results = matrix
                            .getFdRelationResults();
                    if (results != null && !results.isEmpty()) {
                        relations.addAll(results);
                    }
                }
                Collections.sort(relations, new Comparator<SysOrgMatrixRelation>() {
                    @Override
                    public int compare(SysOrgMatrixRelation p1, SysOrgMatrixRelation p2) {
                        int t1 = p1.getFdOrder() == null ? 99 : p1.getFdOrder();
                        int t2 = p2.getFdOrder() == null ? 99 : p2.getFdOrder();
                        return t1 - t2;
                    }
                });
                for (SysOrgMatrixRelation relation : relations) {
                    Map<String, Object> node = new HashMap<String, Object>();
                    node.put("text", relation.getFdName());
                    node.put("value", relation.getFdId());
                    node.put("type", relation.getFdType());
                    node.put("mainDataType", relation.getFdMainDataType());
                    node.put("fieldName", relation.getFdFieldName());
                    node.put("rtnType",
                            BooleanUtils.isTrue(relation.getFdIsResult()) ? 2
                                    : 1);
                    node.put("isAutoFetch", 0);
                    rtnList.add(node);
                }
            }
        }
        return rtnList;
    }

    /**
     * 校验唯一性
     *
     * @param requestInfo
     * @return
     */
    private List<Map<String, Object>> checkUnique(RequestContext requestInfo) throws Exception {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String matrixId = requestInfo.getParameter("matrixId");
        String field = requestInfo.getParameter("field");
        String version = requestInfo.getParameter("version");
        String id = requestInfo.getParameter("id");
        String value = requestInfo.getParameter("value");
        value = URLDecoder.decode(value, "UTF-8");
        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(matrixId);
        String fieldName = null;
        if (matrix != null) {
            List<SysOrgMatrixRelation> temps = matrix.getFdRelationConditionals();
            if (CollectionUtils.isNotEmpty(temps)) {
                for (SysOrgMatrixRelation relation : temps) {
                    if (relation.getFdId().equals(field) && BooleanUtils.isTrue(relation.getFdIsUnique())) {
                        fieldName = relation.getFdFieldName();
                        break;
                    }
                }
            }
        }
        if (StringUtil.isNotNull(fieldName)) {
            List<Object> _list = checkUnique(matrix.getFdSubTable(), fieldName, version, value, id);
            if (CollectionUtils.isNotEmpty(_list)) {
                for (Object obj : _list) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", obj);
                    list.add(map);
                }
            }
        }
        return list;
    }

    private List<Object> checkUnique(String tableName, String fieldName, String version, String value, String id)
            throws Exception {
        String sql = "SELECT fd_id FROM " + tableName + " WHERE fd_version = ? AND " + fieldName + " = ?";
        List<String> params = new ArrayList<String>();
        params.add(version);
        params.add(value);
        if (StringUtil.isNotNull(id)) {
            sql += " AND fd_id != ?";
            params.add(id);
        }
        return executeQuery(sql, params);
    }

    private void getPost(List<Map<String, Object>> rtnList, List<SysOrgElement> posts) {
        if (CollectionUtils.isNotEmpty(posts)) {
            // 按“排序号/编号/名称”排序
            Collections.sort(posts, new Comparator<SysOrgElement>() {
                @Override
                public int compare(SysOrgElement p1, SysOrgElement p2) {
                    String t1 = p1.getFdOrder() == null ? "99" : p1.getFdOrder().toString();
                    String t2 = p2.getFdOrder() == null ? "99" : p2.getFdOrder().toString();
                    int c = t1.compareTo(t2);
                    if (c == 0) {
                        t1 = p1.getFdNo() == null ? "" : p1.getFdNo();
                        t2 = p2.getFdNo() == null ? "" : p2.getFdNo();
                        c = t1.compareTo(t2);
                        if (c == 0) {
                            t1 = p1.getFdName() == null ? "" : p1.getFdName();
                            t2 = p2.getFdName() == null ? "" : p2.getFdName();
                            c = t1.compareTo(t2);
                        }
                    }
                    return c;
                }
            });
            SysOrgElement post = posts.get(0);
            // 返回第1个岗位
            Map<String, Object> node = new HashMap<String, Object>();
            node.put("postId", post.getFdId());
            node.put("postName", post.getFdName());
            rtnList.add(node);
        }
    }

    @Override
    public List<SysOrgMatrix> findByCate(String cateId) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setRowSize(Integer.MAX_VALUE);
        StringBuilder where = new StringBuilder();
        where.append("sysOrgMatrix.fdIsAvailable is true");
        if (StringUtil.isNotNull(cateId)) {
            where.append(" and sysOrgMatrix.hbmCategory.fdId = :cateId");
            hqlInfo.setParameter("cateId", cateId);
        } else {
            where.append(" and sysOrgMatrix.hbmCategory.fdId is null");
        }
        // 如果是外部组织，需要判断是否有指定权限
        if (SysOrgEcoUtil.IS_ENABLED_ECO && SysOrgEcoUtil.isExternal()) {
            where.append(" and sysOrgMatrix.authReaders.fdId in (:orgIds)");
            hqlInfo.setParameter("orgIds", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
        }
        hqlInfo.setWhereBlock(where.toString());
        Page page = findPage(hqlInfo);
        return page.getList();
    }

    /**
     * 查询矩阵数据某一列是否有数据
     */
    @Override
    public int countByColumn(String tableName, String fieldName) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(fd_id) FROM ").append(tableName).append(" WHERE ").append(fieldName).append(" IS NOT NULL");
        List<Object> list = executeQuery(sql.toString(), null);
        return Integer.valueOf(list.get(0).toString());
    }

    /**
     * 查询矩阵中所有列表的记录数
     */
    @Override
    public JSONObject countByColumns(String fdMatrixId) throws Exception {
        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(fdMatrixId);
        JSONObject json = new JSONObject();
        for (SysOrgMatrixRelation relation : matrix.getFdRelations()) {
            int count = countByColumn(matrix.getFdSubTable(), relation.getFdFieldName());
            if (relation.isRange()) {
                count += countByColumn(matrix.getFdSubTable(), relation.getFdFieldName2());
            }
            json.put(relation.getFdId(), count);
        }
        return json;
    }

    /**
     * 获取某一条矩阵数据
     *
     * @param fdMatrixId
     * @param dataId
     * @return
     * @throws Exception
     */
    @Override
    public SysOrgMatrix getMatrixData(String fdMatrixId, String dataId) throws Exception {
        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(fdMatrixId);
        if (StringUtil.isNull(dataId)) {
            return matrix;
        }
        List<String> queryParams = new ArrayList<String>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        for (SysOrgMatrixRelation relation : matrix.getFdRelations()) {
            queryParams.add(relation.getFdId());
            sql.append(relation.getFdFieldName()).append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" FROM ").append(matrix.getFdSubTable()).append(" WHERE fd_id = ?");
        List<String> params = new ArrayList<String>();
        params.add(dataId);
        List<Object> list = executeQuery(sql.toString(), params);
        if (list != null && !list.isEmpty()) {
            Object[] objs = (Object[]) list.get(0);
            JSONObject json = new JSONObject();
            for (int i = 0; i < objs.length; i++) {
                json.put(queryParams.get(i), objs[i]);
            }

            for (SysOrgMatrixRelation relation : matrix.getFdRelations()) {
                if (!json.containsKey(relation.getFdId())) {
                    continue;
                }
                String value = json.getString(relation.getFdId());
                if (BooleanUtils.isTrue(relation.getFdIsResult())) {
                    // 结果可能是多值
                    if (StringUtil.isNotNull(value)) {
                        if (value.startsWith("{") && value.endsWith("}")) {
                            JSONObject temp = JSONObject.parseObject(value);
                            if (!temp.isEmpty()) {
                                relation.setFdResultValues(getElementByJson(temp));
                            }
                        } else {
                            relation.setFdResultValues(getElementByIds(value));
                        }
                    }
                } else {
                    String name = getConditionalName(relation, value);
                    relation.setFdConditionalValue(name);
                    relation.setFdConditionalId(value);
                }
            }
        }

        return matrix;
    }

    /**
     * 删除某一条矩阵数据
     *
     * @param fdMatrixId
     * @param dataId
     * @throws Exception
     */
    @Override
    public void deleteMatrixData(String fdMatrixId, String dataId) throws Exception {
        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(fdMatrixId);
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(matrix.getFdSubTable()).append(" WHERE fd_id = ?");
        List<Object> params = new ArrayList<Object>();
        params.add(dataId);
        executeUpdate(sql.toString(), params);
    }

    /**
     * 批量删除矩阵数据
     *
     * @param fdMatrixId
     * @param dataIds
     * @throws Exception
     */
    @Override
    public void deleteMatrixDatas(String fdMatrixId, String[] dataIds) throws Exception {
        for (String dataId : dataIds) {
            deleteMatrixData(fdMatrixId, dataId);
        }
    }

    /**
     * 新增一条矩阵数据
     *
     * @param fdMatrixId
     * @param fdVersion
     * @param data       {'id':'value', 'id':'value'}
     * @throws Exception
     */
    @Override
    public void addMatrixData(String fdMatrixId, String fdVersion, JSONObject data) throws Exception {
        addMatrixData(fdMatrixId, fdVersion, null, data);
    }

    public void addMatrixData(String fdMatrixId, String fdVersion, String fdCateId, JSONObject data) throws Exception {
        if (StringUtil.isNull(fdVersion)) {
            throw new RuntimeException("Version不能为空！");
        }
        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(fdMatrixId);
        checkRange(matrix.getFdRelations(), data);
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        sql.append("INSERT INTO ").append(matrix.getFdSubTable()).append(" (fd_id");
        temp.append("?");
        params.add(IDGenerator.generateID());
        if (StringUtil.isNotNull(fdCateId)) {
            sql.append(", fd_cate_id");
            temp.append(", ?");
            params.add(fdCateId);
        }
        for (SysOrgMatrixRelation relation : matrix.getFdRelations()) {
            String id = relation.getFdId();
            String value = null;
            if (data.containsKey(id)) {
                value = data.getString(id);
                checkResult(relation, value);
            }
            if ("person_post".equals(relation.getFdType()) && "{}".equals(value)) {
                // 如果是人+岗，且数据为空，则不保存
                continue;
            }
            if (StringUtil.isNotNull(value)) {
                sql.append(", ").append(relation.getFdFieldName());
                temp.append(", ?");
                params.add(value);
            }
            if (relation.isRange()) {
                // 区间类型
                id += "_2";
                String value2 = null;
                if (data.containsKey(id)) {
                    value2 = data.getString(id);
                    checkResult(relation, value2);
                }
                if (StringUtil.isNotNull(value2)) {
                    sql.append(", ").append(relation.getFdFieldName2());
                    temp.append(", ?");
                    params.add(value2);
                }
            }
        }
        temp.append(", ?");
        params.add(fdVersion);
        sql.append(", fd_version) VALUES (").append(temp.toString()).append(")");
        executeUpdate(sql.toString(), params);
    }

    /**
     * 更新一条矩阵数据
     *
     * @param fdMatrixId
     * @param dataId
     * @param data       {'id':'value', 'id':'value'}
     * @throws Exception
     */
    @Override
    public void updateMatrixData(String fdMatrixId, String dataId, JSONObject data) throws Exception {
        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(fdMatrixId);
        checkRange(matrix.getFdRelations(), data);
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(matrix.getFdSubTable()).append(" SET ");
        for (SysOrgMatrixRelation relation : matrix.getFdRelations()) {
            String id = relation.getFdId();
            String value = null;
            if (data.containsKey(id)) {
                value = data.getString(id);
                checkResult(relation, value);
            }
            if ("person_post".equals(relation.getFdType()) && "{}".equals(value)) {
                // 如果是人+岗，保存空
                sql.append(relation.getFdFieldName()).append(" = null ,");
                continue;
            }
            if (StringUtil.isNotNull(value)) {
                sql.append(relation.getFdFieldName()).append(" = ? ,");
                if ("numRange".equals(relation.getFdType())) {
                    // 数值区间
                    params.add(parseDouble(value));
                } else {
                    params.add(value);
                }
            } else {
                sql.append(relation.getFdFieldName()).append(" = null ,");
            }
            if (relation.isRange()) {
                // 区间类型
                id += "_2";
                String value2 = null;
                if (data.containsKey(id)) {
                    value2 = data.getString(id);
                    checkResult(relation, value2);
                }
                if (StringUtil.isNotNull(value2)) {
                    sql.append(relation.getFdFieldName2()).append(" = ? ,");
                    if ("numRange".equals(relation.getFdType())) {
                        // 数值区间
                        params.add(parseDouble(value2));
                    } else {
                        params.add(value2);
                    }
                } else {
                    sql.append(relation.getFdFieldName2()).append(" = null ,");
                }
            }
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" WHERE fd_id = ?");
        params.add(dataId);
        executeUpdate(sql.toString(), params);
    }

    /**
     * 校验区间是否合法
     *
     * @param relations
     * @param data
     */
    private void checkRange(List<SysOrgMatrixRelation> relations, JSONObject data) {
        // 区间校验，开始 < 结束
        for (SysOrgMatrixRelation relation : relations) {
            if (relation.isRange()) {
                Object o1 = null;
                if (data.containsKey(relation.getFdId())) {
                    o1 = data.get(relation.getFdId());
                }
                Object o2 = null;
                if (data.containsKey(relation.getFdId() + "_2")) {
                    o2 = data.get(relation.getFdId() + "_2");
                }
                checkRange(relation.getFdType(), o1, o2);
            }
        }
    }

    /**
     * 校验区间
     *
     * @param type
     * @param o1
     * @param o2
     */
    private void checkRange(String type, Object o1, Object o2) {
        if (o1 != null && o2 != null) {
            if ("numRange".equals(type)) {
                if (StringUtil.isNotNull(o1.toString()) && StringUtil.isNotNull(o2.toString())) {
                    Double d1 = parseDouble(o1);
                    Double d2 = parseDouble(o2);
                    if (d1 > d2) {
                        // 不能出现开始值大于结束值
                        throw new RuntimeException(ResourceUtil.getString("sysOrgMatrix.range.error",
                                "sys-organization", null, new Object[] { d1, d2 }));
                    }
                }
            }
        }
    }

    /**
     * 数字转换
     *
     * @param obj
     * @return
     */
    private Double parseDouble(Object obj) {
        if (obj != null) {
            try {
                return Double.parseDouble(obj.toString());
            } catch (Exception e) {
                throw new RuntimeException(ResourceUtil.getString("errors.number", null, null, obj));
            }
        }
        return null;
    }

    /**
     * 校验结果长度
     *
     * @param relation
     * @param value
     */
    private void checkResult(SysOrgMatrixRelation relation, String value) {
        if (BooleanUtils.isTrue(relation.getFdIsResult())) {
            // 结果字段长度为1000，因为只保存ID，每个ID长度为最大限定为36，因此每个结果字段最多只能保存27组元素
            String[] split = value.split("[;,]");
            if (split.length > 27) {
                throw new RuntimeException(
                        ResourceUtil.getString("sysOrgMatrix.import.length.exceeding.limit", "sys-organization", null, 27));
            }
        }
    }

    /**
     * 下载导入失败的数据
     */
    @Override
    public Workbook exportErrorMatrixData(String fdId, JSONObject datas) throws Exception {
        // 获取模板
        Workbook wb = new XSSFWorkbook();

        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(fdId);
        // 第一步，创建一个webbook，对应一个Excel文件
        int index = 0;
        for (Object key : datas.keySet()) {
            String version = key.toString();
            buildSheet(wb, matrix, version);
            // 填充数据
            JSONArray array = datas.getJSONArray(version);
            Sheet sheet = wb.getSheetAt(index);
            index++;
            sheet.setDefaultColumnWidth(25); // 设置宽度
            // 字段注释
            XSSFDrawing draw = (XSSFDrawing) sheet.createDrawingPatriarch();
            // 第三步，在sheet中添加表头第0行
            Row row = null;
            // 第四步，创建单元格
            Cell cell = null;
            int rowIndex = 2;
            List<SysOrgMatrixRelation> relations = new ArrayList<>();
            relations.addAll(matrix.getFdRelationConditionals());
            relations.addAll(matrix.getFdRelationResults());
            for (int i = 0; i < array.size(); i++) {
                row = sheet.createRow(rowIndex);
                int idx = 0;
                JSONArray dataSuc = array.getJSONObject(i).getJSONArray("dataSuc");
                for (int j = 0; j < relations.size(); j++) {
                    SysOrgMatrixRelation relation = relations.get(j);
                    JSONObject data = null;
                    for (int k = 0; k < dataSuc.size(); k++) {
                        JSONObject temp = dataSuc.getJSONObject(k);
                        if (temp.getIntValue("index") == j) {
                            data = temp;
                            break;
                        }
                    }
                    if (data == null) {
                        idx++;
                        if (BooleanUtils.isTrue(relation.getFdIsResult())
                                || !"constant".equals(relation.getFdType())) {
                            idx++;
                        }
                        continue;
                    }
                    cell = row.createCell(idx);
                    String msg = null;
                    if (data.containsKey("msg")) {
                        msg = data.getString("msg");
                    }
                    if (StringUtil.isNotNull(msg)
                            && StringUtil.isNotNull(data.getString("name"))) {
                        cell.setCellComment(getErrorComment(draw, msg));
                    }
                    if (BooleanUtils.isTrue(relation.getFdIsResult())) {
                        cell.setCellValue(data.getString("name"));
                        idx++;
                        cell = row.createCell(idx);
                        cell.setCellValue(data.getString("id"));
                        if (StringUtil.isNotNull(msg)
                                && StringUtil.isNotNull(data.getString("id"))) {
                            cell.setCellComment(getErrorComment(draw, msg));
                        }
                    } else {
                        cell.setCellValue(data.getString("name"));
                        if (!"constant".equals(relation.getFdType())) {
                            idx++;
                            cell = row.createCell(idx);
                            cell.setCellValue(data.getString("id"));
                            if (StringUtil.isNotNull(msg)
                                    && StringUtil.isNotNull(data.getString("id"))) {
                                cell.setCellComment(getErrorComment(draw, msg));
                            }
                        }
                    }
                    idx++;
                }
                rowIndex++;
            }
        }
        return wb;
    }

    /**
     * 获取异常描述
     *
     * @param draw
     * @param msg
     * @return
     */
    private Comment getErrorComment(XSSFDrawing draw, String msg) {
        XSSFComment comment = draw.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, 2, 2, 4, 5));
        comment.setString(new XSSFRichTextString(msg));
        comment.setAuthor("panyh");
        return comment;
    }

    /**
     * 有fdId为更新，没有是新增
     * <p>
     * {"V1": [{'id':'value', 'id':'value'}, {'fdId':'value', 'id':'value', 'id':'value'}]}
     */
    @Override
    public void saveAllMatrixData(String fdMatrixId, String cateId, JSONObject json) throws Exception {
        // addMatrixData(String fdMatrixId, JSONObject data)
        // updateMatrixData(String fdMatrixId, String dataId, JSONObject data)
        if (json != null && !json.isEmpty()) {
            for (Object version : json.keySet()) {
                String _version = version.toString();
                addVersion(fdMatrixId, _version);
                JSONArray array = json.getJSONArray(_version);
                if (array != null && array.size() > 0) {
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject data = array.getJSONObject(i);
                        if (data.containsKey("fdId")) {
                            String dataId = (String) data.remove("fdId");
                            if (dataId != null) {
                                updateMatrixData(fdMatrixId, dataId, data);
                            }
                        } else {
                            addMatrixData(fdMatrixId, _version, cateId, data);
                        }
                    }
                }
            }
        }
    }

    /**
     * 增加版本信息
     *
     * @param fdMatrixId
     * @param version
     * @throws Exception
     */
    private void addVersion(String fdMatrixId, String version) throws Exception {
        // 判断是否存在版本，如果不存在才会增加，存在就忽略
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock("fdId");
        hqlInfo.setWhereBlock("hbmMatrix.fdId = :fdMatrixId and fdName = :version");
        hqlInfo.setParameter("fdMatrixId", fdMatrixId);
        hqlInfo.setParameter("version", version);
        List<Object> list = sysOrgMatrixVersionService.findValue(hqlInfo);
        if (list == null || list.isEmpty()) {
            sysOrgMatrixVersionService.addVersion((SysOrgMatrix) findByPrimaryKey(fdMatrixId), version);
        }
    }

    /**
     * 删除矩阵版本
     */
    @Override
    public void deleteVersion(String fdMatrixId, String fdVersion) throws Exception {
        // 删除版本信息
        sysOrgMatrixVersionService.deleteVersion(fdMatrixId, fdVersion);
        // 删除版本数据
        SysOrgMatrix sysOrgMatrix = (SysOrgMatrix) findByPrimaryKey(fdMatrixId);
        String delDataSql = "DELETE FROM " + sysOrgMatrix.getFdSubTable() + " WHERE fd_version = :fdVersion";
        NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(delDataSql);
        query.addSynchronizedQuerySpace(sysOrgMatrix.getFdSubTable());
        query.setParameter("fdVersion", fdVersion).executeUpdate();
    }

    /**
     * 获取矩阵版本信息
     */
    @Override
    public JSONArray getVersions(String fdMatrixId) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("hbmMatrix.fdId = :fdMatrixId and (fdIsDelete IS NULL OR fdIsDelete = :fdIsDelete)");
        hqlInfo.setParameter("fdMatrixId", fdMatrixId);
        hqlInfo.setParameter("fdIsDelete", Boolean.FALSE);
        hqlInfo.setOrderBy("fdVersion");
        List<SysOrgMatrixVersion> list = sysOrgMatrixVersionService.findList(hqlInfo);
        JSONArray rtnList = new JSONArray();
        if (list != null && !list.isEmpty()) {
            for (SysOrgMatrixVersion temp : list) {
                JSONObject obj = new JSONObject();
                obj.put("fdId", temp.getFdId());
                obj.put("fdName", temp.getFdName());
                obj.put("fdVersion", temp.getFdVersion());
                obj.put("fdIsEnable", temp.getFdIsEnable());
                obj.put("fdCreateTime", temp.getFdCreateTime());
                rtnList.add(obj);
            }
        }
        return rtnList;
    }

    @Override
    public void saveInitMatrixData() throws Exception {
        HQLInfo info = new HQLInfo();
        info.setSelectBlock(" max(sysOrgMatrix.fdOrder) ");
        info.setPageNo(1);
        info.setRowSize(1);
        List<Object> queryList = this.findList(info);

        Integer fdOrder = 0;

        if (queryList.size() > 0) {
            Integer tempModel = (Integer) queryList.get(0);
            if (tempModel != null) {
                fdOrder = tempModel;
            }
        }

        String root = ConfigLocationsUtil.getWebContentPath();
        String nodePath = root + "/WEB-INF/KmssConfig/sys/organization/sys-init-matrix.xml";

        File file = new File(nodePath);
        if (!file.exists()) {
            logger.warn("init SysOrgMatrix data is not exists ");
            throw new RuntimeException("init SysOrgMatrix data is not exists");
        }

        FileInputStream inputStream = new FileInputStream(file);
        Document doucment = XMLUtils.parse(inputStream);
        Node node = doucment.getFirstChild();//得到根节点

        List<SysOrgMatrixForm> sysOrgMatrixFormList = new ArrayList<SysOrgMatrixForm>();

        if ("root".equals(node.getNodeName())) {//读取配置的矩阵数据

            NodeList sysOrgMatrixNodeList = node.getChildNodes();//获取子节点sysOrgMatrix

            for (int z = 0; z < sysOrgMatrixNodeList.getLength(); z++) {
                Node sysOrgMatrixItem = sysOrgMatrixNodeList.item(z);//遍历sysOrgMatrix
                if ("sysOrgMatrix".equals(sysOrgMatrixItem.getNodeName())) {
                    fdOrder = fdOrder + 1;
                    Node fdNameNode = getNodeByNodeName(sysOrgMatrixItem, "fdName");
                    Node fdIdNode = getNodeByNodeName(sysOrgMatrixItem, "fdId");

                    SysOrgMatrix model = (SysOrgMatrix) this.findByPrimaryKey(fdIdNode.getTextContent(), SysOrgMatrix.class, true);
                    if (model != null) {
                        // 默认激活V1版本
                        RequestContext request = new RequestContext();
                        request.setParameter("fdMatrixId", model.getFdId());
                        request.setParameter("fdVersion", "V1");
                        request.setParameter("fdIsEnable", "true");
                        sysOrgMatrixVersionService.updateEnable(request);
                        logger.info("init SysOrgMatrix error,SysOrgMatrix is exists");
                        continue;
                    }

                    SysOrgMatrixForm rtnForm = new SysOrgMatrixForm();
                    rtnForm.setFdIsAvailable(Boolean.TRUE);
                    rtnForm.setMatrixType("1");//1 内置生成的
                    rtnForm.setFdOrder(fdOrder);
                    if (fdNameNode == null || "".equals(fdNameNode.getTextContent())) {
                        logger.warn("init SysOrgMatrix error,SysOrgMatrix's fdNameNode is null");
                        throw new RuntimeException("init SysOrgMatrix error,SysOrgMatrix's fdNameNode is null");
                    }

                    if (fdIdNode == null || "".equals(fdIdNode.getTextContent())) {
                        logger.warn("init SysOrgMatrix error,SysOrgMatrix's fdIdNode is null");
                        throw new RuntimeException("init SysOrgMatrix error,SysOrgMatrix's fdIdNode is null");
                    }

                    rtnForm.setFdId(fdIdNode.getTextContent());
                    rtnForm.setFdName(fdNameNode.getTextContent());

                    AutoArrayList fdRelationConditionalsList = new AutoArrayList(SysOrgMatrixRelationForm.class);

                    Node fdRelationConditionalsItem = getNodeByNodeName(sysOrgMatrixItem, "fdRelationConditionals");
                    if (fdRelationConditionalsItem != null) {
                        NodeList sysOrgMatrixRelationItems = fdRelationConditionalsItem.getChildNodes();
                        for (int i = 0; i < sysOrgMatrixRelationItems.getLength(); i++) {
                            if ("sysOrgMatrixRelation".equals(sysOrgMatrixRelationItems.item(i).getNodeName())) {
                                SysOrgMatrixRelationForm sysOrgMatrixRelation = new SysOrgMatrixRelationForm();
                                sysOrgMatrixRelation.setFdIsResult(Boolean.FALSE);//条件

                                Node relationConditionalsFdId = getNodeByNodeName(sysOrgMatrixRelationItems.item(i), "fdId");

                                Node relationConditionalsFdName = getNodeByNodeName(sysOrgMatrixRelationItems.item(i), "fdName");

                                Node relationConditionalsFdType = getNodeByNodeName(sysOrgMatrixRelationItems.item(i), "fdType");

                                if (relationConditionalsFdId == null || "".equals(relationConditionalsFdId.getTextContent())) {
                                    logger.warn("init SysOrgMatrix error,fdRelationConditionals's SysOrgMatrixRelation's fdId is null");
                                    throw new RuntimeException("init SysOrgMatrix error,fdRelationConditionals's SysOrgMatrixRelation's fdId is null");
                                }

                                if (relationConditionalsFdName == null || "".equals(relationConditionalsFdName.getTextContent())) {
                                    logger.warn("init SysOrgMatrix error,fdRelationConditionals's SysOrgMatrixRelation's fdNameNode is null");
                                    throw new RuntimeException("init SysOrgMatrix error,fdRelationConditionals's SysOrgMatrixRelation's fdNameNode is null");
                                }

                                if (relationConditionalsFdType == null || "".equals(relationConditionalsFdType.getTextContent())) {
                                    logger.warn("init SysOrgMatrix error,fdRelationConditionals's SysOrgMatrixRelation's fdType is null");
                                    throw new RuntimeException("init SysOrgMatrix error,fdRelationConditionals's SysOrgMatrixRelation's fdType is null");
                                }

                                if (!"constant".equals(relationConditionalsFdType.getTextContent())
                                        && !"org".equals(relationConditionalsFdType.getTextContent())
                                        && !"dept".equals(relationConditionalsFdType.getTextContent())
                                        && !"post".equals(relationConditionalsFdType.getTextContent())
                                        && !"person".equals(relationConditionalsFdType.getTextContent())
                                        && !"group".equals(relationConditionalsFdType.getTextContent())) {
                                    logger.warn("init SysOrgMatrix error,fdRelationConditionals's fdType is not constant,org,dept,post,person,group");
                                    throw new RuntimeException("init SysOrgMatrix error,fdRelationConditionals's fdType is not constant,org,dept,post,person,group");
                                }
                                sysOrgMatrixRelation.setFdId(relationConditionalsFdId.getTextContent());
                                sysOrgMatrixRelation.setFdName(relationConditionalsFdName.getTextContent());
                                sysOrgMatrixRelation.setFdType(relationConditionalsFdType.getTextContent());
                                fdRelationConditionalsList.add(sysOrgMatrixRelation);
                            }
                        }
                        rtnForm.setFdRelationConditionals(fdRelationConditionalsList);
                    }

                    AutoArrayList fdRelationResultsList = new AutoArrayList(SysOrgMatrixRelationForm.class);
                    Node fdRelationResultsItem = getNodeByNodeName(sysOrgMatrixItem, "fdRelationResults");
                    if (fdRelationResultsItem != null) {
                        NodeList sysOrgMatrixRelationItems = fdRelationResultsItem.getChildNodes();
                        for (int i = 0; i < sysOrgMatrixRelationItems.getLength(); i++) {
                            if ("sysOrgMatrixRelation".equals(sysOrgMatrixRelationItems.item(i).getNodeName())) {
                                SysOrgMatrixRelationForm sysOrgMatrixRelation = new SysOrgMatrixRelationForm();
                                sysOrgMatrixRelation.setFdIsResult(Boolean.TRUE);//条件

                                Node relationConditionalsFdId = getNodeByNodeName(sysOrgMatrixRelationItems.item(i), "fdId");

                                Node relationConditionalsFdName = getNodeByNodeName(sysOrgMatrixRelationItems.item(i), "fdName");

                                Node relationConditionalsFdType = getNodeByNodeName(sysOrgMatrixRelationItems.item(i), "fdType");

                                if (relationConditionalsFdId == null || "".equals(relationConditionalsFdId.getTextContent())) {
                                    logger.warn("init SysOrgMatrix error,fdRelationConditionals's SysOrgMatrixRelation's fdId is null");
                                    throw new RuntimeException("init SysOrgMatrix error,fdRelationConditionals's SysOrgMatrixRelation's fdId is null");
                                }

                                if (relationConditionalsFdName == null || "".equals(relationConditionalsFdName.getTextContent())) {
                                    logger.warn("init SysOrgMatrix error,fdRelationResults's SysOrgMatrixRelation's fdNameNode is null");
                                    throw new RuntimeException("init SysOrgMatrix error,fdRelationResults's SysOrgMatrixRelation's fdNameNode is null");
                                }

                                if (relationConditionalsFdType == null || "".equals(relationConditionalsFdType.getTextContent())) {
                                    logger.warn("init SysOrgMatrix error,fdRelationResults's SysOrgMatrixRelation's fdType is null");
                                    throw new RuntimeException("init SysOrgMatrix error,fdRelationResults's SysOrgMatrixRelation's fdType is null");
                                }

                                if (!"post".equals(relationConditionalsFdType.getTextContent())
                                        && !"person".equals(relationConditionalsFdType.getTextContent())) {
                                    logger.warn("init SysOrgMatrix error,fdRelationResults's FdType is not post,person");
                                    throw new RuntimeException("init SysOrgMatrix error,fdRelationResults's FdType is not post,person");
                                }
                                sysOrgMatrixRelation.setFdId(relationConditionalsFdId.getTextContent());
                                sysOrgMatrixRelation.setFdName(relationConditionalsFdName.getTextContent());
                                sysOrgMatrixRelation.setFdType(relationConditionalsFdType.getTextContent());
                                fdRelationResultsList.add(sysOrgMatrixRelation);

                            }
                        }
                        rtnForm.setFdRelationResults(fdRelationResultsList);
                    }
                    sysOrgMatrixFormList.add(rtnForm);
                }
            }
        }
        if (!CollectionUtils.isEmpty(sysOrgMatrixFormList)) {
            for (int x = 0; x < sysOrgMatrixFormList.size(); x++) {
                this.add(sysOrgMatrixFormList.get(x), new RequestContext());
            }
        }


    }

    /**
     * 获取当前节点子节点值
     *
     * @param node          当前节点
     * @param childNodeName 当前节点的子节点名称
     * @return
     */
    private Node getNodeByNodeName(Node node, String childNodeName) {

        NodeList nodeItem = node.getChildNodes();
        for (int i = 0; i < nodeItem.getLength(); i++) {
            Node nodeChildItem = nodeItem.item(i);
            if (nodeChildItem.getNodeName().equals(childNodeName)) {
                return nodeChildItem;
            }
        }

        return null;
    }

    /**
     * 批量替换矩阵数据
     */
    @Override
    public void batchReplace(String fdId, String version, JSONObject json) throws Exception {
        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(fdId);
        if (matrix == null) {
            throw new NoRecordException();
        }
        // 列名
        String field = json.getString("field");
        // 原数据
        String ori = json.getString("ori");
        // 新数据
        String target = json.getString("target");
        // 拼接替换SQL
        String sql = "UPDATE " + matrix + " SET " + field + " = ? WHERE fd_version = ? AND " + field + " = ?";
        List<Object> params = new ArrayList<Object>();
        params.add(target);
        params.add(version);
        params.add(ori);
        executeUpdate(sql, params);
    }

    /**
     * 获取矩阵数据（分页）新增方法,查询关联数据类别的矩阵信息
     */
    @Override
    public Page findMatrixPageByType(String fdMatrixId, String fdVersion, int pageno, int rowsize, String fdDataCateId,
                                     String filter) throws Exception {
        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(fdMatrixId, null, true);
        List<SysOrgMatrixRelation> listRelation = new ArrayList<SysOrgMatrixRelation>();
        List<SysOrgMatrixRelation> listConditional = matrix.getFdRelationConditionals();
        Collections.sort(listConditional);
        listRelation.addAll(listConditional);
        List<SysOrgMatrixRelation> listResult = matrix.getFdRelationResults();
        Collections.sort(listResult);
        listRelation.addAll(listResult);
        int size = 0;
        for (SysOrgMatrixRelation relation : listRelation) {
            if (relation.isRange()) {
                // 区间类型，有2列
                size += 2;
            } else {
                size += 1;
            }
        }
        // 分组
        Map<String, String> cates = new HashMap<String, String>();
        HttpServletRequest request = Plugin.currentRequest();
        String method = request.getParameter("method_GET");
        boolean hasCate = StringUtil.isNull(method) && BooleanUtils.isTrue(matrix.getFdIsEnabledCate());
        if (hasCate) {
            List<SysOrgMatrixDataCateForm> cateList = sysOrgMatrixDataCateService.getDataCates(matrix.getFdId());
            if (CollectionUtils.isNotEmpty(cateList)) {
                for (SysOrgMatrixDataCateForm cateForm : cateList) {
                    cates.put(cateForm.getFdId(), cateForm.getFdName());
                }
            }
            size += 1;
        }

        Page page = ((ISysOrgMatrixDao) getBaseDao()).findMatrixPageByType(matrix, fdVersion, pageno, rowsize,
                fdDataCateId, filter);
        List<Object[]> list = page.getList();
        List<List<SysOrgMatrixRelation>> relations = new ArrayList<List<SysOrgMatrixRelation>>();
        //取消科学计数法
        NumberFormat nf = NumberFormat.getInstance();
        //设置保留多少位小数
        //nf.setMaximumFractionDigits(20);
        // 取消科学计数法
        nf.setGroupingUsed(false);
        if (list != null && !list.isEmpty()) {
            //分组字段索引
            int cateIndex = 0;
            //数据库类型
            String dialect = ResourceUtil.getKmssConfigString("hibernate.dialect");
            boolean isOracle = false;
            if (MetadataUtil.isOracle(dialect)) {
                isOracle = true;
            }
            for (int i = 0; i < list.size(); i++) {
                List<SysOrgMatrixRelation> temps = new ArrayList<SysOrgMatrixRelation>();
                Object[] objs = list.get(i);
                if(cateIndex == 0){
                    cateIndex = objs.length;
                    if(isOracle && pageno > 1){
                        //oracle分页会在查询出来的字段中末尾携带rownum,故原总长度基础上-1
                        cateIndex = cateIndex - 1;
                    }
                }
                int idx = 0;
                for (int j = 0; j <= size; j++) {
                    SysOrgMatrixRelation temp = new SysOrgMatrixRelation();
                    if (j == 0) {
                        String obj = (String) objs[j];
                        // 主键
                        temp.setFdIsPrimary(true);
                        temp.setFdId(obj);
                    } else if (hasCate && j == cateIndex - 1) {
                        String obj = (String) objs[j];
                        // 分组ID
                        temp.setFdIsPrimary(false);
                        temp.setFdType("fd_cate_id");
                        temp.setFdFieldName("fd_cate_id");
                        temp.setFdId(obj);
                        if (StringUtil.isNotNull(obj)) {
                            temp.setFdName(cates.get(obj));
                        } else {
                            temp.setFdName("");
                        }
                    } else {
                        //只取索引范围内的字段数据
                        if(idx < listRelation.size()){
                            SysOrgMatrixRelation relation = listRelation.get(idx);
                            // 普通字段
                            temp.setFdName(relation.getFdName());
                            temp.setFdMatrix(relation.getFdMatrix());
                            temp.setFdFieldName(relation.getFdFieldName());
                            temp.setFdFieldName2(relation.getFdFieldName2());
                            temp.setFdType(relation.getFdType());
                            temp.setFdIsResult(relation.getFdIsResult());
                            temp.setFdOrder(relation.getFdOrder());
                            temp.setFdIsPrimary(false);
                            temp.setFdMainDataType(relation.getFdMainDataType());
                            if (relation.isRange()) {
                                // 开始区间
                                Object obj = objs[j];
                                if(obj==null){
                                    temp.setFdConditionalValue(obj);
                                }else{
                                    temp.setFdConditionalValue(nf.format(obj));
                                }
                                // 结束区间
                                j++;
                                Object obj2 = objs[j];
                                if(obj2==null){
                                    temp.setFdConditionalValue2(obj2);
                                }else{
                                    temp.setFdConditionalValue2(nf.format(obj2));
                                }
                            } else {
                                String obj = (String) objs[j];
                                if (StringUtil.isNotNull(obj)) {
                                    if (BooleanUtils.isTrue(relation.getFdIsResult())) {
                                        // 结果可能是多值
                                        try{
                                            if (StringUtil.isNotNull(obj)) {
                                                if (obj.startsWith("{") && obj.endsWith("}")) {
                                                    JSONObject json = JSONObject.parseObject(obj);
                                                    if (!json.isEmpty()) {
                                                        temp.setFdResultValues(getElementByJson(json));
                                                    }
                                                } else {
                                                    temp.setFdResultValues(getElementByIds(obj));
                                                }
                                            }
                                        }catch (Exception e){
                                            logger.info("没有对应的组织架构id："+obj,e);
                                            List<SysOrgElement> resultList = new ArrayList<>();
                                            SysOrgElement o = new SysOrgElement();
                                            o.setFdName(ResourceUtil.getString("sys-organization:sysOrgMatrix.data.unusual"));
                                            resultList.add(o);
                                            temp.setFdResultValues(resultList);
                                        }
                                    } else {
                                        String name = getConditionalName(temp, obj);
                                        temp.setFdConditionalValue(name);
                                        temp.setFdConditionalId(obj);
                                    }
                                }
                            }
                            idx++;
                        }
                    }
                    temps.add(temp);
                }
                relations.add(temps);
            }
            page.setList(relations);
        }
        return page;
    }

    @Override
    public long countByType(String fdMatrixId, String fdDataCateId) throws Exception {
        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(fdMatrixId);
        List<String> params = new ArrayList<String>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(fd_id) FROM ").append(matrix.getFdSubTable()).append(" WHERE fd_cate_id = ?");
        params.add(fdDataCateId);
        List<Object> list = executeQuery(sql.toString(), params);
        return Long.parseLong(list.get(0).toString());
    }

    @Override
    public void saveMatrixDataByCate(String matrixId, String version, String cateId, JSONArray matrixDatas)
            throws Exception {
        // 保存Version
        addVersion(matrixId, version);
        // 保存数据
        for (int i = 0; i < matrixDatas.size(); i++) {
            JSONObject data = matrixDatas.getJSONObject(i);
            if (data.containsKey("fdId")) {
                String fdId = (String) data.remove("fdId");
                if (StringUtil.isNotNull(fdId)) {
                    updateMatrixData(matrixId, fdId, data);
                }
            } else {
                addMatrixData(matrixId, version, cateId, data);
            }
        }
    }

    @Override
    public void updateDefDataCate(String matrixId) throws Exception {
        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(matrixId);
        if (BooleanUtils.isTrue(matrix.getFdIsEnabledCate())) {
            List<SysOrgMatrixDataCate> list = matrix.getFdDataCates();
            if (CollectionUtils.isNotEmpty(list)) {
                String cateId = list.get(0).getFdId();
                StringBuilder sql = new StringBuilder();
                sql.append("UPDATE ").append(matrix.getFdSubTable()).append(" SET fd_cate_id = ?");
                List<Object> params = new ArrayList<Object>();
                params.add(cateId);
                executeUpdate(sql.toString(), params);
            }
        } else {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ").append(matrix.getFdSubTable()).append(" SET fd_cate_id = null");
            executeUpdate(sql.toString(), null);
        }
    }

    @Override
    public JSONObject filterData(RequestContext request) throws Exception {
        JSONObject data = new JSONObject();
        // 矩阵ID
        String matrixId = request.getParameter("matrixId");
        // 矩阵版本
        String version = request.getParameter("version");
        // 字段ID
        String fieldId = request.getParameter("fieldId");
        // 分组ID（可选）
        String cateId = request.getParameter("cateId");
        // 关键字（可选）
        String keyword = request.getParameter("keyword");

        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(matrixId);
        if (matrix == null) {
            data.put("success", false);
            data.put("msg", "该矩阵不存在，id=" + matrixId);
            return data;
        }
        SysOrgMatrixRelation relation = null;
        // 数据筛选仅支持条件列，结果列使用地址本方式查询
        List<SysOrgMatrixRelation> relations = matrix.getFdRelationConditionals();
        for (SysOrgMatrixRelation temp : relations) {
            if (temp.getFdId().equals(fieldId)) {
                relation = temp;
                break;
            }
        }
        if (relation == null) {
            data.put("success", false);
            data.put("msg", "该字段不存在，id=" + fieldId);
            return data;
        }
        StringBuffer sql = new StringBuffer();
        StringBuffer select = new StringBuffer();
        StringBuffer where = new StringBuffer();
        StringBuffer join = new StringBuffer();
        sql.append("select model.").append(relation.getFdFieldName()).append("%s from ").append(matrix.getFdSubTable()).append(" model");
        where.append(" where model.fd_version = :version");
        if (StringUtil.isNotNull(cateId)) {
            where.append(" AND model.fd_cate_id = :cateId");
        }
        String type = relation.getFdType();
        // 常量不需要匹配查询类型
        if ("constant".equals(type)) {
            where.append(" AND lower(model.").append(relation.getFdFieldName()).append(")").append(" like :keyword");
        } else {
            String mainDataType = relation.getFdMainDataType();
            if ("org".equals(type) || "dept".equals(type) || "post".equals(type) || "person".equals(type) || "group".equals(type)) {
                join.append(" LEFT JOIN sys_org_element elem").append(" ON elem").append(".fd_id = model.").append(relation.getFdFieldName());
                select.append(", elem.fd_name ");
                // 处理官方语言
                String fdNameLang = SysLangUtil.getLangFieldName("fdName");
                where.append(" AND (lower(elem").append(".fd_name) like :keyword");
                // 处理多语言
                if (!"fdName".equals(fdNameLang)) {
                    where.append(" OR lower(elem").append(".").append(toFieldName("fdName")).append(") like :keyword");
                }
                where.append(")");
            } else if ("sys".equals(mainDataType)) {
                // 获取主数据中的模块表名
                SysFormMainDataInsystem model = (SysFormMainDataInsystem) sysFormMainDataInsystemService.findByPrimaryKey(type, null, true);
                SysDictModel dictModel = SysDataDict.getInstance().getModel(model.getFdModelName());
                String displayProperty = dictModel.getDisplayProperty();
                if (StringUtil.isNull(displayProperty)) {
                    throw new RuntimeException(
                            ResourceUtil.getString("sysOrgMatrix.import.displayProperty.non.existent", "sys-organization", null, model.getFdModelName()));
                }
                SysDictCommonProperty commonProperty = dictModel.getPropertyMap().get(displayProperty);
                String table = dictModel.getTable();
                // 人员表没有fdName
                if ("sys_org_person".equals(table)) {
                    table = "sys_org_element";
                }
                join.append(" LEFT JOIN ").append(table).append(" maindata").append(" ON maindata")
                        .append(".fd_id = model.").append(relation.getFdFieldName());
                // 处理官方语言
                String displayPropertyLang = SysLangUtil.getLangFieldName(displayProperty);
                where.append(" AND (lower(maindata.").append(commonProperty.getColumn()).append(") like :keyword");
                select.append(", maindata.").append(commonProperty.getColumn());
                // 处理多语言
                if (!displayProperty.equals(displayPropertyLang)) {
                    where.append(" OR lower(maindata.").append(toFieldName(displayProperty))
                            .append(") like :keyword");
                }
                where.append(")");
            } else if ("cust".equals(mainDataType)) {
                join.append(" LEFT JOIN sys_xform_main_data_cuslist cust").append(" ON cust").append(".fd_value = model.").append(relation.getFdFieldName());
                where.append(" AND (lower(cust.fd_value_text) like :keyword");
                select.append(", cust.fd_value_text");
                // 处理官方语言
                String fdValueTextLang = SysLangUtil.getLangFieldName("fdValueText");
                // 处理多语言
                if (!"fdValueText".equals(fdValueTextLang)) {
                    where.append(" OR lower(cust.").append(toFieldName("fdValueText")).append(") like :keyword");
                }
                where.append(")");
            }
        }
        sql.append(join.toString()).append(where.toString()).append(" group by model.").append(relation.getFdFieldName()).append(select.toString());
        String querySql = String.format(sql.toString(), select.toString());
        if (logger.isDebugEnabled()) {
            logger.debug(querySql);
        }
        NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(querySql);
        query.setParameter("version", version);
        if (StringUtil.isNotNull(cateId)) {
            query.setParameter("cateId", cateId);
        }
        if (StringUtil.isNotNull(keyword)) {
            query.setParameter("keyword", "%" + keyword + "%");
        } else {
            query.setParameter("keyword", "%");
        }
        // 设置最大11条
        query.setMaxResults(11);
        List<Object> list = query.list();
        int max = 10;
        // 如果数量大于10，显示“更多”
        if (list.size() > max) {
            data.put("more", true);
        }
        JSONArray array = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            if (i >= max) {
                break;
            }
            JSONObject temp = new JSONObject();
            Object obj = list.get(i);
            if (obj instanceof Object[]) {
                Object[] _obj = (Object[]) obj;
                temp.put("value", _obj[0]);
                temp.put("text", _obj[1]);
            } else {
                temp.put("value", obj);
                temp.put("text", obj);
            }
            array.add(temp);
        }
        data.put("data", array);
        data.put("success", true);
        return data;
    }

    @Override
    public SysOrgMatrixVersion copyMatrixData(String fdMatrixId, String version) throws Exception {
        // 检查版本是否存在
        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(fdMatrixId);
        SysOrgMatrixVersion newVersion = sysOrgMatrixVersionService.addVersion(matrix);
        // 批量复制数据
        List<SysOrgMatrixRelation> relations = matrix.getFdRelations();
        StringBuffer cols = new StringBuffer();
        StringBuffer params = new StringBuffer();
        // 固定列
        cols.append("fd_cate_id");
        List<String> types = new ArrayList<>();
        types.add("cate");
        for (SysOrgMatrixRelation relation : relations) {
            cols.append(",").append(relation.getFdFieldName());
            params.append(",?");
            types.add(relation.getFdType());
            if (relation.isRange()) {
                cols.append(",").append(relation.getFdFieldName2());
                params.append(",?");
                types.add(relation.getFdType());
            }
        }
        String selectSql = "select " + cols.toString() + " from " + matrix.getFdSubTable() + " where fd_version = :version";
        String insertSql = "insert into " + matrix.getFdSubTable() + "(fd_id,fd_version," + cols.toString()
                + ") values (?,?,?" + params.toString() + ")";
        int page = 1;
        int rows = 100;
        NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(selectSql).setParameter("version", version).setMaxResults(rows);
        while (true) {
            Session session = getBaseDao().getHibernateSession();
            List<Object[]> list = query.setFirstResult((page - 1) * rows).list();
            if (CollectionUtils.isEmpty(list)) {
                break;
            }
            for (Object[] objs : list) {
                NativeQuery insertQuery = session.createNativeQuery(insertSql);
                insertQuery.addSynchronizedQuerySpace(matrix.getFdSubTable());
                insertQuery.setParameter(0, IDGenerator.generateID());
                insertQuery.setParameter(1, newVersion.getFdName());
                for (int i = 0; i < objs.length; i++) {
                    Object obj = objs[i];
                    if ("numRange".equals(types.get(i))) {
                        Double val = obj != null ? Double.parseDouble(obj.toString()) : null;
                        insertQuery.setParameter(i + 2, val, DoubleType.INSTANCE);
                    } else {
                        insertQuery.setParameter(i + 2, obj);
                    }
                }
                insertQuery.executeUpdate();
            }
            session.flush();
            page++;
        }
        return newVersion;
    }

    @Override
    public Page findMatrixPageToExport(String fdMatrixId, String fdVersion, int pageno, int rowsize, String filter, Map<Integer,String> columnMap) throws Exception {
        SysOrgMatrix matrix = (SysOrgMatrix) findByPrimaryKey(fdMatrixId);
        Page page = null;
        // 分组
        if (BooleanUtils.isTrue(matrix.getFdIsEnabledCate())) {
            page = ((ISysOrgMatrixDao) getBaseDao()).findMatrixPageByTypeToExport(matrix, fdVersion, pageno, rowsize, null, filter, columnMap);
        } else {
            page = ((ISysOrgMatrixDao) getBaseDao()).findMatrixPageToExport(matrix, fdVersion, pageno, rowsize, filter, columnMap);
        }
        return page;
    }
}
