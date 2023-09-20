package com.landray.kmss.fssc.budget.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustCategory;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmTemplateForm;
import com.landray.kmss.sys.lbpmservice.support.forms.LbpmTemplateForm;
import com.landray.kmss.sys.number.forms.SysNumberMainMappForm;
import com.landray.kmss.sys.number.interfaces.ISysNumberForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.simplecategory.forms.SysSimpleCategoryAuthTmpForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
  * 预算调整分类
  */
public class FsscBudgetAdjustCategoryForm extends SysSimpleCategoryAuthTmpForm implements ISysLbpmTemplateForm, ISysNumberForm {

	private static final long serialVersionUID = 2331109471747567145L;

	private static FormToModelPropertyMap toModelPropertyMap;

    private String fdAdjustType;

    private String fdSubjectType;

    private String fdSubjectRule;

    private String fdSubjectRuleText;

    private String docCreatorId;

    private AutoHashMap sysWfTemplateForms = new AutoHashMap(LbpmTemplateForm.class);

    private SysNumberMainMappForm sysNumberMainMappForm = new SysNumberMainMappForm();

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdAdjustType = null;
        fdSubjectType = null;
        fdSubjectRule = null;
        fdSubjectRuleText = null;
        docCreatorId = null;
        sysWfTemplateForms.clear();
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscBudgetAdjustCategory> getModelClass() {
        return FsscBudgetAdjustCategory.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel("fdParent", FsscBudgetAdjustCategory.class));
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("authEditorIds", new FormConvertor_IDsToModelList("authEditors", SysOrgElement.class));
            toModelPropertyMap.put("authTmpReaderIds", new FormConvertor_IDsToModelList("authTmpReaders", SysOrgElement.class));
        }
        return toModelPropertyMap;
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

    /**
     * 创建人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 创建人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    @Override
    public String getAuthReaderNoteFlag() {
        return "2";
    }

    @Override
    public AutoHashMap getSysWfTemplateForms() {
        return sysWfTemplateForms;
    }

    @Override
    public SysNumberMainMappForm getSysNumberMainMappForm() {
        return this.sysNumberMainMappForm;
    }

    @Override
    public void setSysNumberMainMappForm(SysNumberMainMappForm sysNumberMainMappForm) {
        this.sysNumberMainMappForm = sysNumberMainMappForm;
    }
}
