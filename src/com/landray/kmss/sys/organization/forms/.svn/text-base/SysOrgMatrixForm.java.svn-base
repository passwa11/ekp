package com.landray.kmss.sys.organization.forms;

import com.landray.kmss.common.convertor.*;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgMatrix;
import com.landray.kmss.sys.organization.model.SysOrgMatrixCate;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import org.apache.commons.collections.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 组织矩阵
 *
 * @author 潘永辉 2019年6月4日
 */
public class SysOrgMatrixForm extends ExtendForm {
    private static final long serialVersionUID = 1L;

    /**
     * 矩阵名称
     */
    private String fdName;

    /**
     * 1 内置生成的
     * 2 人工创建的
     */
    private String matrixType;

    /**
     * 矩阵描述
     */
    private String fdDesc;

    /**
     * 排序号
     */
    private Integer fdOrder;

    /**
     * 是否禁用
     */
    private Boolean fdIsAvailable;

    /**
     * 子表名
     */
    private String fdSubTable;

    /**
     * 群组类别
     */
    private String fdCategoryId;

    private String fdCategoryName;

    /**
     * 可阅读者
     */
    protected String authReaderIds;

    /**
     * 可阅读者名称
     */
    protected String authReaderNames;

    /**
     * 可编辑者
     */
    protected String authEditorIds;

    /**
     * 可编辑者名称
     */
    protected String authEditorNames;

    /**
     * 创建时间
     */
    private Date fdCreateTime = new Date();

    /**
     * 修改时间
     */
    private Date fdAlterTime = new Date();

    /**
     * 关系信息(条件数据)
     */
    private AutoArrayList fdRelationConditionals = new AutoArrayList(
            SysOrgMatrixRelationForm.class);

    /**
     * 关系信息(结果数据)
     */
    private AutoArrayList fdRelationResults = new AutoArrayList(
            SysOrgMatrixRelationForm.class);

    /**
     * 矩阵数据分组
     */
    private AutoArrayList fdDataCates = new AutoArrayList(SysOrgMatrixDataCateForm.class);

    /**
     * 矩阵版本
     */
    private AutoArrayList fdVersions = new AutoArrayList(SysOrgMatrixVersionForm.class);

    /**
     * 是否开启分组
     */
    private Boolean fdIsEnabledCate;

    public Boolean getFdIsEnabledCate() {
        initIsEnabledCate();
        return fdIsEnabledCate;
    }

    public void setFdIsEnabledCate(Boolean fdIsEnabledCate) {
        this.fdIsEnabledCate = fdIsEnabledCate;
    }

    private void initIsEnabledCate() {
        if (fdIsEnabledCate == null) {
            if (CollectionUtils.isEmpty(fdDataCates)) {
                fdIsEnabledCate = false;
            } else {
                fdIsEnabledCate = true;
            }
        }
    }

    // 上传的文件
    private FormFile file;

    private Boolean append;

    //列宽
    private String width;

    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    public String getMatrixType() {
        return matrixType;
    }

    public void setMatrixType(String matrixType) {
        this.matrixType = matrixType;
    }

    public String getFdDesc() {
        return fdDesc;
    }

    public void setFdDesc(String fdDesc) {
        this.fdDesc = fdDesc;
    }

    public Integer getFdOrder() {
        return fdOrder;
    }

    public void setFdOrder(Integer fdOrder) {
        this.fdOrder = fdOrder;
    }

    public Boolean getFdIsAvailable() {
        return fdIsAvailable;
    }

    public void setFdIsAvailable(Boolean fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
    }

    public String getFdSubTable() {
        return fdSubTable;
    }

    public void setFdSubTable(String fdSubTable) {
        this.fdSubTable = fdSubTable;
    }

    public String getFdCategoryId() {
        return fdCategoryId;
    }

    public void setFdCategoryId(String fdCategoryId) {
        this.fdCategoryId = fdCategoryId;
    }

    public String getFdCategoryName() {
        return fdCategoryName;
    }

    public void setFdCategoryName(String fdCategoryName) {
        this.fdCategoryName = fdCategoryName;
    }

    public String getAuthReaderIds() {
        return authReaderIds;
    }

    public void setAuthReaderIds(String authReaderIds) {
        this.authReaderIds = authReaderIds;
    }

    public String getAuthReaderNames() {
        return authReaderNames;
    }

    public void setAuthReaderNames(String authReaderNames) {
        this.authReaderNames = authReaderNames;
    }

    public String getAuthEditorIds() {
        return authEditorIds;
    }

    public void setAuthEditorIds(String authEditorIds) {
        this.authEditorIds = authEditorIds;
    }

    public String getAuthEditorNames() {
        return authEditorNames;
    }

    public void setAuthEditorNames(String authEditorNames) {
        this.authEditorNames = authEditorNames;
    }

    public Date getFdCreateTime() {
        return fdCreateTime;
    }

    public void setFdCreateTime(Date fdCreateTime) {
        this.fdCreateTime = fdCreateTime;
    }

    public Date getFdAlterTime() {
        return fdAlterTime;
    }

    public void setFdAlterTime(Date fdAlterTime) {
        this.fdAlterTime = fdAlterTime;
    }

    public AutoArrayList getFdRelationConditionals() {
        return fdRelationConditionals;
    }

    public void
    setFdRelationConditionals(AutoArrayList fdRelationConditionals) {
        this.fdRelationConditionals = fdRelationConditionals;
    }

    public AutoArrayList getFdRelationResults() {
        return fdRelationResults;
    }

    public void setFdRelationResults(AutoArrayList fdRelationResults) {
        this.fdRelationResults = fdRelationResults;
    }

    public AutoArrayList getFdDataCates() {
        return fdDataCates;
    }

    public void setFdDataCates(AutoArrayList fdDataCates) {
        this.fdDataCates = fdDataCates;
    }

    public AutoArrayList getFdVersions() {
        return fdVersions;
    }

    public void setFdVersions(AutoArrayList fdVersions) {
        this.fdVersions = fdVersions;
    }

    public FormFile getFile() {
        return file;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }

    public Boolean getAppend() {
        return append;
    }

    public void setAppend(Boolean append) {
        this.append = append;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    @Override
    public Class<?> getModelClass() {
        return SysOrgMatrix.class;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        matrixType = null;
        fdDesc = null;
        fdOrder = null;
        fdIsAvailable = null;
        fdCategoryId = null;
        fdCategoryName = null;
        authReaderIds = null;
        authReaderNames = null;
        authEditorIds = null;
        authEditorNames = null;
        file = null;
        append = null;
        width = null;
        fdCreateTime = new Date();
        fdAlterTime = new Date();
        fdRelationConditionals.clear();
        fdRelationResults.clear();
        fdDataCates.clear();
        fdVersions.clear();
        initIsEnabledCate();
        super.reset(mapping, request);
    }

    private static FormToModelPropertyMap toModelPropertyMap;

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.put("fdCategoryId", new FormConvertor_IDToModel("fdCategory", SysOrgMatrixCate.class));
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("authEditorIds", new FormConvertor_IDsToModelList("authEditors", SysOrgElement.class));
            toModelPropertyMap.put("fdRelationConditionals", new FormConvertor_FormListToModelList("fdRelationConditionals", "fdMatrix"));
            toModelPropertyMap.put("fdRelationResults", new FormConvertor_FormListToModelList("fdRelationResults", "fdMatrix"));
            toModelPropertyMap.put("fdDataCates", new FormConvertor_FormListToModelList("fdDataCates", "fdMatrix"));
            // toModelPropertyMap.put("fdVersions", new FormConvertor_FormListToModelList("fdVersions", "fdMatrix"));
            toModelPropertyMap.put("fdVersions", new FormConvertor_Empty());
        }
        return toModelPropertyMap;
    }
}
