package com.landray.kmss.fssc.budget.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.fssc.budget.forms.FsscBudgetAdjustCategoryForm;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmTemplateModel;
import com.landray.kmss.sys.number.interfaces.ISysNumberModel;
import com.landray.kmss.sys.number.model.SysNumberMainMapp;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;
import com.landray.kmss.util.DateUtil;

import java.util.List;

/**
  * 预算调整分类
  */
public class FsscBudgetAdjustCategory extends SysSimpleCategoryAuthTmpModel implements ISysLbpmTemplateModel, ISysNumberModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdAdjustType;

    private String fdSubjectType;

    private String fdSubjectRule;

    private String fdSubjectRuleText;

    private List sysWfTemplateModels;

    private SysNumberMainMapp sysNumberMainMappModel = new SysNumberMainMapp();

    @Override
    public Class<FsscBudgetAdjustCategoryForm> getFormClass() {
        return FsscBudgetAdjustCategoryForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.addNoConvertProperty("fdHierarchyId");
            toFormPropertyMap.addNoConvertProperty("authReaderFlag");
            toFormPropertyMap.put("fdParent.fdName", "fdParentName");
            toFormPropertyMap.put("fdParent.fdId", "fdParentId");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("authEditors", new ModelConvertor_ModelListToString("authEditorIds:authEditorNames", "fdId:fdName"));
            toFormPropertyMap.put("authTmpReaders", new ModelConvertor_ModelListToString("authTmpReaderIds:authTmpReaderNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 预算调整类型
     */
    public String getFdAdjustType() {
        return this.fdAdjustType;
    }

    /**
     * 预算调整类型
     */
    public void setFdAdjustType(String fdAdjustType) {
        this.fdAdjustType = fdAdjustType;
    }

    /**
     * 主题生成方式
     */
    public String getFdSubjectType() {
        return this.fdSubjectType;
    }

    /**
     * 主题生成方式
     */
    public void setFdSubjectType(String fdSubjectType) {
        this.fdSubjectType = fdSubjectType;
    }

    /**
     * 主题规则
     */
    public String getFdSubjectRule() {
        return this.fdSubjectRule;
    }

    /**
     * 主题规则
     */
    public void setFdSubjectRule(String fdSubjectRule) {
        this.fdSubjectRule = fdSubjectRule;
    }

    /**
     * 主题规则显示
     */
    public String getFdSubjectRuleText() {
        return this.fdSubjectRuleText;
    }

    /**
     * 主题规则显示
     */
    public void setFdSubjectRuleText(String fdSubjectRuleText) {
        this.fdSubjectRuleText = fdSubjectRuleText;
    }

    @Override
    public List getSysWfTemplateModels() {
        return this.sysWfTemplateModels;
    }

    @Override
    public void setSysWfTemplateModels(List sysWfTemplateModels) {
        this.sysWfTemplateModels = sysWfTemplateModels;
    }

    @Override
    public SysNumberMainMapp getSysNumberMainMappModel() {
        return this.sysNumberMainMappModel;
    }

    @Override
    public void setSysNumberMainMappModel(SysNumberMainMapp sysNumberMainMappModel) {
        this.sysNumberMainMappModel = sysNumberMainMappModel;
    }
}
