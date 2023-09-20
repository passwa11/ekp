package com.landray.kmss.fssc.expense.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.fssc.expense.model.FsscExpenseCategory;
import com.landray.kmss.sys.archives.forms.SysArchivesFileTemplateForm;
import com.landray.kmss.sys.archives.interfaces.ISysArchivesFileTemplateForm;
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
import com.landray.kmss.sys.xform.interfaces.ISysFormTemplateForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
  * 报销类别
  */
public class FsscExpenseCategoryForm extends SysSimpleCategoryAuthTmpForm implements ISysLbpmTemplateForm, ISysFormTemplateForm, ISysNumberForm, ISysRelationMainForm,IAttachmentForm, ISysArchivesFileTemplateForm {

    private static FormToModelPropertyMap toModelPropertyMap;
    
    private String fdIsProjectShare;
    
    private String fdIsProapp;
    
    private String fdIsTravelAlone;
    
    private String fdIsMobile;
    
    private String fdIsForeign;
    
    private String fdIsFee;
    
    private String fdFeeTemplateId;
    
    private String fdFeeTemplateName;
    
    private String fdIsAmortize;
    
    private String fdExtendFields;
    
    private String fdBudgetShowType;

    private String fdSubjectRule;
    
    private String fdSubjectRuleText;

    private String fdExpenseType;

    private String fdAllocType;

    private String fdSubjectType;

    private String fdIsProject;

    private String fdCode;

    private String docXform;

    private String docUseXform;

    private String docCreatorId;

    private AutoHashMap sysWfTemplateForms = new AutoHashMap(LbpmTemplateForm.class);

    private AutoHashMap sysFormTemplateForms = new AutoHashMap(SysFormTemplateForm.class);

    private String fdExtFilePath;

    private SysNumberMainMappForm sysNumberMainMappForm = new SysNumberMainMappForm();
    
    private SysRelationMainForm sysRelationMainForm = new SysRelationMainForm();

    private SysArchivesFileTemplateForm sysArchivesFileTemplateForm = new SysArchivesFileTemplateForm();

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	fdIsProjectShare = null;
		fdIsProapp = null;
    	fdIsTravelAlone = null;
    	fdIsMobile = null;
    	fdIsForeign = null;
    	fdIsFee = null;
    	fdFeeTemplateId = null;
    	fdFeeTemplateName = null;
    	fdIsAmortize = null;
    	fdExtendFields = null;
    	fdBudgetShowType = null;
        fdSubjectRule = null;
        fdSubjectRuleText = null;
        fdExpenseType = null;
        fdAllocType = null;
        fdSubjectType = null;
        fdIsProject = null;
        fdCode = null;
        docXform = null;
        docUseXform = null;
        docCreatorId = null;
        sysWfTemplateForms.clear();
        fdExtFilePath = null;
        sysFormTemplateForms.clear();
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscExpenseCategory> getModelClass() {
        return FsscExpenseCategory.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel("fdParent", FsscExpenseCategory.class));
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

    public String getFdIsProjectShare() {
		return fdIsProjectShare;
	}

	public void setFdIsProjectShare(String fdIsProjectShare) {
		this.fdIsProjectShare = fdIsProjectShare;
	}

	public String getFdIsProapp() {
		return fdIsProapp;
	}

	public void setFdIsProapp(String fdIsProapp) {
		this.fdIsProapp = fdIsProapp;
	}

	public String getFdIsTravelAlone() {
		return fdIsTravelAlone;
	}

	public void setFdIsTravelAlone(String fdIsTravelAlone) {
		this.fdIsTravelAlone = fdIsTravelAlone;
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
     * 报销类型
     */
    public String getFdExpenseType() {
        return this.fdExpenseType;
    }

    /**
     * 报销类型
     */
    public void setFdExpenseType(String fdExpenseType) {
        this.fdExpenseType = fdExpenseType;
    }

    /**
     * 报销模式
     */
    public String getFdAllocType() {
        return this.fdAllocType;
    }
    
    /**
	 * 附件实现
	 */
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

    /**
     * 报销模式
     */
    public void setFdAllocType(String fdAllocType) {
        this.fdAllocType = fdAllocType;
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
     * 是否项目费用
     */
    public String getFdIsProject() {
        return this.fdIsProject;
    }

    /**
     * 是否项目费用
     */
    public void setFdIsProject(String fdIsProject) {
        this.fdIsProject = fdIsProject;
    }

    /**
     * 编码
     */
    public String getFdCode() {
        return this.fdCode;
    }

    /**
     * 编码
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }

    /**
     * 扩展属性
     */
    public String getDocXform() {
        return this.docXform;
    }

    /**
     * 扩展属性
     */
    public void setDocXform(String docXform) {
        this.docXform = docXform;
    }

    /**
     * 是否使用表单
     */
    public String getDocUseXform() {
        return this.docUseXform;
    }

    /**
     * 是否使用表单
     */
    public void setDocUseXform(String docUseXform) {
        this.docUseXform = docUseXform;
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
    public AutoHashMap getSysWfTemplateForms() {
        return sysWfTemplateForms;
    }

    @Override
    public AutoHashMap getSysFormTemplateForms() {
        return sysFormTemplateForms;
    }

    @Override
    public String getFdExtFilePath() {
        return this.fdExtFilePath;
    }

    @Override
    public void setFdExtFilePath(String fdExtFilePath) {
        this.fdExtFilePath = fdExtFilePath;
    }

    @Override
    public SysNumberMainMappForm getSysNumberMainMappForm() {
        return this.sysNumberMainMappForm;
    }

    @Override
    public void setSysNumberMainMappForm(SysNumberMainMappForm sysNumberMainMappForm) {
        this.sysNumberMainMappForm = sysNumberMainMappForm;
    }

	public String getFdBudgetShowType() {
		return fdBudgetShowType;
	}

	public void setFdBudgetShowType(String fdBudgetShowType) {
		this.fdBudgetShowType = fdBudgetShowType;
	}

	public String getFdExtendFields() {
		return fdExtendFields;
	}

	public void setFdExtendFields(String fdExtendFields) {
		this.fdExtendFields = fdExtendFields;
	}

	public String getFdIsAmortize() {
		return fdIsAmortize;
	}

	public void setFdIsAmortize(String fdIsAmortize) {
		this.fdIsAmortize = fdIsAmortize;
	}

	public String getFdIsFee() {
		return fdIsFee;
	}

	public void setFdIsFee(String fdIsFee) {
		this.fdIsFee = fdIsFee;
	}

	public String getFdFeeTemplateId() {
		return fdFeeTemplateId;
	}

	public void setFdFeeTemplateId(String fdFeeTemplateId) {
		this.fdFeeTemplateId = fdFeeTemplateId;
	}

	public String getFdFeeTemplateName() {
		return fdFeeTemplateName;
	}

	public void setFdFeeTemplateName(String fdFeeTemplateName) {
		this.fdFeeTemplateName = fdFeeTemplateName;
	}

	public String getFdIsForeign() {
		return fdIsForeign;
	}

	public void setFdIsForeign(String fdIsForeign) {
		this.fdIsForeign = fdIsForeign;
	}

	public String getFdIsMobile() {
		return fdIsMobile;
	}

	public void setFdIsMobile(String fdIsMobile) {
		this.fdIsMobile = fdIsMobile;
	}
	
	@Override
    public SysRelationMainForm getSysRelationMainForm() {
        return sysRelationMainForm;
    }

    @Override
    public SysArchivesFileTemplateForm getSysArchivesFileTemplateForm() {
        return sysArchivesFileTemplateForm;
    }
    @Override
    public void setSysArchivesFileTemplateForm(SysArchivesFileTemplateForm form) {
        this.sysArchivesFileTemplateForm=form;
    }
}
