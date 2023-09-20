package com.landray.kmss.fssc.expense.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.fssc.expense.model.FsscExpenseShareCategory;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmTemplateForm;
import com.landray.kmss.sys.lbpmservice.support.forms.LbpmTemplateForm;
import com.landray.kmss.sys.number.forms.SysNumberMainMappForm;
import com.landray.kmss.sys.number.interfaces.ISysNumberForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.relation.forms.SysRelationMainForm;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainForm;
import com.landray.kmss.sys.simplecategory.forms.SysSimpleCategoryAuthTmpForm;
import com.landray.kmss.sys.xform.base.forms.SysFormTemplateForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
  * 分摊类别
  */
public class FsscExpenseShareCategoryForm extends SysSimpleCategoryAuthTmpForm  implements ISysLbpmTemplateForm, ISysNumberForm, ISysRelationMainForm,IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdNumber;

    private String docCreatorId;

    private String fdShareType;	//分摊类型

    private String fdCateIds;	//类别id

    private String fdCateNames;	//类别
    
    private String fdSubjectRule;
    
    private String fdSubjectRuleText;
    
    private String fdSubjectType;
    
    private AutoHashMap sysWfTemplateForms = new AutoHashMap(LbpmTemplateForm.class);

    private AutoHashMap sysFormTemplateForms = new AutoHashMap(SysFormTemplateForm.class);
    
    private SysNumberMainMappForm sysNumberMainMappForm = new SysNumberMainMappForm();
    
    private SysRelationMainForm sysRelationMainForm = new SysRelationMainForm();

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdNumber = null;
        docCreatorId = null;
        fdShareType = null;
        fdCateIds = null;
        fdCateNames = null;
        fdSubjectRule = null;
        fdSubjectRuleText = null;
        fdSubjectType = null;
        sysWfTemplateForms.clear();
        sysFormTemplateForms.clear();
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscExpenseShareCategory> getModelClass() {
        return FsscExpenseShareCategory.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel("fdParent", FsscExpenseShareCategory.class));
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("authEditorIds", new FormConvertor_IDsToModelList("authEditors", SysOrgElement.class));
        }
        return toModelPropertyMap;
    }

    /**
     * (为空则除了作者和相关人员，其他人不能阅读)
     */
    public String getAuthReaderNotFlag(){
        return "2";
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
    public AutoHashMap getSysWfTemplateForms() {
        return sysWfTemplateForms;
    }

    public AutoHashMap getSysFormTemplateForms() {
        return sysFormTemplateForms;
    }
    @Override
    public SysNumberMainMappForm getSysNumberMainMappForm() {
        return this.sysNumberMainMappForm;
    }

    @Override
    public void setSysNumberMainMappForm(SysNumberMainMappForm sysNumberMainMappForm) {
        this.sysNumberMainMappForm = sysNumberMainMappForm;
    }
    
    @Override
    public SysRelationMainForm getSysRelationMainForm() {
        return sysRelationMainForm;
    }
    
    /**
	 * 附件实现
	 */
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
	public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}
   
}
