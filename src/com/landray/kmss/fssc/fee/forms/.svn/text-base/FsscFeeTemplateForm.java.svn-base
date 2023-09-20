package com.landray.kmss.fssc.fee.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.model.SysCategoryProperty;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmTemplateForm;
import com.landray.kmss.sys.lbpmservice.support.forms.LbpmTemplateForm;
import com.landray.kmss.sys.number.forms.SysNumberMainMappForm;
import com.landray.kmss.sys.number.interfaces.ISysNumberForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.print.forms.SysPrintTemplateForm;
import com.landray.kmss.sys.print.interfaces.ISysPrintTemplateForm;
import com.landray.kmss.sys.relation.forms.SysRelationMainForm;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainForm;
import com.landray.kmss.sys.simplecategory.forms.SysSimpleCategoryAuthTmpForm;
import com.landray.kmss.sys.xform.base.forms.SysFormTemplateForm;
import com.landray.kmss.sys.xform.interfaces.ISysFormTemplateForm;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 模板设置
  */
public class FsscFeeTemplateForm extends SysSimpleCategoryAuthTmpForm implements ISysLbpmTemplateForm, ISysFormTemplateForm, ISysNumberForm, ISysRelationMainForm, ISysPrintTemplateForm, IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;
    
    private Boolean fdIsMobile;

    private String fdName;
    
    private String fdForbid;

    private String fdOrder;

    private String docCreateTime;
    
    private String fdSubjectRule;
    
    private String fdSubjectRuleText;
    
    private String fdSubjectType;

    private String docXform;

    private String docUseXform;

    private String docCategoryId;

    private String docCategoryName;

    private String docCreatorId;

    private String docCreatorName;

    private String docPropertieIds;

    private String docPropertieNames;
    //携程商旅字段
    private String fdServiceType;
    
    private AutoArrayList fdConfig_Form = new AutoArrayList(FsscFeeMobileConfigForm.class);

    private String fdConfig_Flag = "0";

    private AutoHashMap sysWfTemplateForms = new AutoHashMap(LbpmTemplateForm.class);

    private AutoHashMap sysFormTemplateForms = new AutoHashMap(SysFormTemplateForm.class);

    private String fdExtFilePath;

    private SysNumberMainMappForm sysNumberMainMappForm = new SysNumberMainMappForm();

    private SysRelationMainForm sysRelationMainForm = new SysRelationMainForm();
    
    private SysPrintTemplateForm sysPrintTemplateForm = new SysPrintTemplateForm();

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	fdConfig_Form = new AutoArrayList(FsscFeeMobileConfigForm.class);
        fdConfig_Flag = null;
    	fdIsMobile = true;
    	fdForbid = null;
        fdName = null;
        fdOrder = null;
        docCreateTime = null;
        docXform = null;
        fdSubjectRule = null;
        fdSubjectRuleText = null;
        fdSubjectType = null;
        docUseXform = null;
        docCategoryId = null;
        docCategoryName = null;
        docCreatorId = null;
        docCreatorName = null;
        docPropertieIds = null;
        docPropertieNames = null;
        fdServiceType=null;
        sysWfTemplateForms.clear();
        fdExtFilePath = null;
        sysFormTemplateForms.clear();
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscFeeTemplate> getModelClass() {
        return FsscFeeTemplate.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("docCategoryId", new FormConvertor_IDToModel("docCategory", SysCategoryMain.class));
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("authEditorIds", new FormConvertor_IDsToModelList("authEditors", SysOrgElement.class));
            toModelPropertyMap.put("authTmpReaderIds", new FormConvertor_IDsToModelList("authTmpReaders", SysOrgElement.class));
            toModelPropertyMap.put("docPropertieIds", new FormConvertor_IDsToModelList("docProperties", SysCategoryProperty.class));
            toModelPropertyMap.put("fdConfig_Form", new FormConvertor_FormListToModelList("fdConfig", "docMain", "fdConfig_Flag"));
        }
        return toModelPropertyMap;
    }

    /**
     * 名称
     */
    @Override
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 名称
     */
    @Override
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 排序号
     */
    @Override
    public String getFdOrder() {
        return this.fdOrder;
    }

    /**
     * 排序号
     */
    @Override
    public void setFdOrder(String fdOrder) {
        this.fdOrder = fdOrder;
    }

    /**
     * 创建时间
     */
    @Override
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    @Override
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
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
     * 所属分类
     */
    public String getDocCategoryId() {
        return this.docCategoryId;
    }

    /**
     * 所属分类
     */
    public void setDocCategoryId(String docCategoryId) {
        this.docCategoryId = docCategoryId;
    }

    /**
     * 所属分类
     */
    public String getDocCategoryName() {
        return this.docCategoryName;
    }

    /**
     * 所属分类
     */
    public void setDocCategoryName(String docCategoryName) {
        this.docCategoryName = docCategoryName;
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
     * 创建人
     */
    @Override
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 创建人
     */
    @Override
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    /**
     * 辅分类
     */
    public String getDocPropertieIds() {
        return this.docPropertieIds;
    }

    /**
     * 辅分类
     */
    public void setDocPropertieIds(String docPropertieIds) {
        this.docPropertieIds = docPropertieIds;
    }

    /**
     * 辅分类
     */
    public String getDocPropertieNames() {
        return this.docPropertieNames;
    }

    /**
     * 辅分类
     */
    public void setDocPropertieNames(String docPropertieNames) {
        this.docPropertieNames = docPropertieNames;
    }
    //携程商旅字段
    
    public String getFdServiceType() {
		return fdServiceType;
	}

	public void setFdServiceType(String fdServiceType) {
		this.fdServiceType = fdServiceType;
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

    @Override
    public SysRelationMainForm getSysRelationMainForm() {
        return sysRelationMainForm;
    }
    
    @Override
    public SysPrintTemplateForm getSysPrintTemplateForm() {
        return this.sysPrintTemplateForm;
    }

    @Override
    public void setSysPrintTemplateForm(SysPrintTemplateForm sysPrintTemplateForm) {
        this.sysPrintTemplateForm = sysPrintTemplateForm;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

	public String getFdSubjectRule() {
		return fdSubjectRule;
	}

	public void setFdSubjectRule(String fdSubjectRule) {
		this.fdSubjectRule = fdSubjectRule;
	}

	public String getFdSubjectRuleText() {
		return fdSubjectRuleText;
	}

	public void setFdSubjectRuleText(String fdSubjectRuleText) {
		this.fdSubjectRuleText = fdSubjectRuleText;
	}

	public String getFdSubjectType() {
		return fdSubjectType;
	}

	public void setFdSubjectType(String fdSubjectType) {
		this.fdSubjectType = fdSubjectType;
	}

	public String getFdForbid() {
		return fdForbid;
	}

	public void setFdForbid(String fdForbid) {
		this.fdForbid = fdForbid;
	}

	public Boolean getFdIsMobile() {
		return fdIsMobile;
	}

	public void setFdIsMobile(Boolean fdIsMobile) {
		this.fdIsMobile = fdIsMobile;
	}

	public AutoArrayList getFdConfig_Form() {
		return fdConfig_Form;
	}

	public void setFdConfig_Form(AutoArrayList fdConfig_Form) {
		this.fdConfig_Form = fdConfig_Form;
	}

	public String getFdConfig_Flag() {
		return fdConfig_Flag;
	}

	public void setFdConfig_Flag(String fdConfig_Flag) {
		this.fdConfig_Flag = fdConfig_Flag;
	}
}
