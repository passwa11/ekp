package com.landray.kmss.fssc.expense.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.fssc.expense.forms.FsscExpenseShareCategoryForm;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmTemplateModel;
import com.landray.kmss.sys.number.interfaces.ISysNumberModel;
import com.landray.kmss.sys.number.model.SysNumberMainMapp;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainModel;
import com.landray.kmss.sys.relation.model.SysRelationMain;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;
import com.landray.kmss.util.DateUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
  * 分摊类别
  */
public class FsscExpenseShareCategory extends SysSimpleCategoryAuthTmpModel  implements InterceptFieldEnabled, ISysLbpmTemplateModel, ISysNumberModel, ISysRelationMainModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdNumber;

    private String fdShareType;	//分摊类型

    private String fdCateIds;	//类别id

    private String fdCateNames;	//类别
    
    private List sysWfTemplateModels;

    private SysNumberMainMapp sysNumberMainMappModel = new SysNumberMainMapp();
    
    private SysRelationMain sysRelationMain = null;

    private String relationSeparate = null;
    
    private String fdSubjectRule;
    
    private String fdSubjectRuleText;
    
    private String fdSubjectType;

    @Override
    public Class<FsscExpenseShareCategoryForm> getFormClass() {
        return FsscExpenseShareCategoryForm.class;
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
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 编号
     */
    public String getFdNumber() {
        return this.fdNumber;
    }

    /**
     * 编号
     */
    public void setFdNumber(String fdNumber) {
        this.fdNumber = fdNumber;
    }

    /**
     * 分摊类型
     * @return
     */
    public String getFdShareType() {
        return this.fdShareType;
    }

    /**
     * 分摊类型
     * @param fdShareType
     */
    public void setFdShareType(String fdShareType) {
        this.fdShareType = fdShareType;
    }

    /**
     * 类别id
     * @return
     */
    public String getFdCateIds() {
        return this.fdCateIds;
    }

    /**
     * 类别id
     * @param fdCateIds
     */
    public void setFdCateIds(String fdCateIds) {
        this.fdCateIds = fdCateIds;
    }

    /**
     * 类别
     * @return
     */
    public String getFdCateNames() {
        return this.fdCateNames;
    }

    /**
     * 类别
     * @param fdCateNames
     */
    public void setFdCateNames(String fdCateNames) {
        this.fdCateNames = fdCateNames;
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
     * 主题规则
     */
    public String getFdSubjectRuleText() {
		return fdSubjectRuleText;
	}

    /**
     * 主题规则
     */
	public void setFdSubjectRuleText(String fdSubjectRuleText) {
		this.fdSubjectRuleText = fdSubjectRuleText;
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
    
    @Override
    public SysRelationMain getSysRelationMain() {
        return this.sysRelationMain;
    }

    @Override
    public void setSysRelationMain(SysRelationMain sysRelationMain) {
        this.sysRelationMain = sysRelationMain;
    }

    public String getRelationSeparate() {
        return this.relationSeparate;
    }

    public void setRelationSeparate(String relationSeparate) {
        this.relationSeparate = relationSeparate;
    }
}
