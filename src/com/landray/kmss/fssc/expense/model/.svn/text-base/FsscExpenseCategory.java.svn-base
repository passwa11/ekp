package com.landray.kmss.fssc.expense.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.fssc.expense.forms.FsscExpenseCategoryForm;
import com.landray.kmss.sys.archives.interfaces.ISysArchivesFileTemplateModel;
import com.landray.kmss.sys.archives.model.SysArchivesFileTemplate;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmTemplateModel;
import com.landray.kmss.sys.number.interfaces.ISysNumberModel;
import com.landray.kmss.sys.number.model.SysNumberMainMapp;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainModel;
import com.landray.kmss.sys.relation.model.SysRelationMain;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;
import com.landray.kmss.sys.xform.interfaces.ISysFormTemplateModel;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
  * 报销类别
  */
public class FsscExpenseCategory extends SysSimpleCategoryAuthTmpModel implements InterceptFieldEnabled, IAttachment,ISysLbpmTemplateModel, ISysFormTemplateModel, ISysNumberModel, ISysRelationMainModel, ISysArchivesFileTemplateModel {

    private static ModelToFormPropertyMap toFormPropertyMap;
    
    private Boolean fdIsProjectShare;
    
    private Boolean fdIsProapp;
    
    private Boolean fdIsTravelAlone;
    
    private Boolean fdIsMobile;
    
    private Boolean fdIsForeign;
    
    private Boolean fdIsFee;
    
    private String fdFeeTemplateId;
    
    private String fdFeeTemplateName;
    
    private Boolean fdIsAmortize;
    
    private String fdExtendFields;
    
    private String fdBudgetShowType;

    private String fdSubjectRule;
    
    private String fdSubjectRuleText;

    private String fdExpenseType;

    private String fdAllocType;

    private String fdSubjectType;

    private Boolean fdIsProject;

    private String fdCode;

    private String docXform;

    private Boolean docUseXform;

    private List sysWfTemplateModels;

    private List sysFormTemplateModels;

    private SysNumberMainMapp sysNumberMainMappModel = new SysNumberMainMapp();
    
    private SysRelationMain sysRelationMain = null;

    private String relationSeparate = null;

    @Override
    public Class<FsscExpenseCategoryForm> getFormClass() {
        return FsscExpenseCategoryForm.class;
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

    public Boolean getFdIsProjectShare() {
		return fdIsProjectShare;
	}

	public void setFdIsProjectShare(Boolean fdIsProjectShare) {
		this.fdIsProjectShare = fdIsProjectShare;
	}

	public Boolean getFdIsTravelAlone() {
		return fdIsTravelAlone;
	}

	public void setFdIsTravelAlone(Boolean fdIsTravelAlone) {
		this.fdIsTravelAlone = fdIsTravelAlone;
	}

	public Boolean getFdIsProapp() {
		return fdIsProapp;
	}

	public void setFdIsProapp(Boolean fdIsProapp) {
		this.fdIsProapp = fdIsProapp;
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
    public Boolean getFdIsProject() {
        return this.fdIsProject;
    }

    /**
     * 是否项目费用
     */
    public void setFdIsProject(Boolean fdIsProject) {
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
        return (String) readLazyField("docXform", this.docXform);
    }

    /**
     * 扩展属性
     */
    public void setDocXform(String docXform) {
        this.docXform = (String) writeLazyField("docXform", this.docXform, docXform);
    }

    /**
     * 是否使用表单
     */
    public Boolean getDocUseXform() {
        return this.docUseXform;
    }

    /**
     * 是否使用表单
     */
    public void setDocUseXform(Boolean docUseXform) {
        this.docUseXform = docUseXform;
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
    public List getSysFormTemplateModels() {
        return this.sysFormTemplateModels;
    }

    @Override
    public void setSysFormTemplateModels(List sysFormTemplateModels) {
        this.sysFormTemplateModels = sysFormTemplateModels;
    }

    @Override
    public SysNumberMainMapp getSysNumberMainMappModel() {
        return this.sysNumberMainMappModel;
    }

    @Override
    public void setSysNumberMainMappModel(SysNumberMainMapp sysNumberMainMappModel) {
        this.sysNumberMainMappModel = sysNumberMainMappModel;
    }

    /**
     * 预算显示类型（1：图标，2：数字）
     * @return
     */
	public String getFdBudgetShowType() {
		return fdBudgetShowType;
	}

	/**
	 * 预算显示类型（1：图标，2：数字）
	 * @param fdBudgetShowType
	 */
	public void setFdBudgetShowType(String fdBudgetShowType) {
		this.fdBudgetShowType = fdBudgetShowType;
	}

	public String getFdExtendFields() {
		return fdExtendFields;
	}

	public void setFdExtendFields(String fdExtendFields) {
		this.fdExtendFields = fdExtendFields;
	}

	public Boolean getFdIsAmortize() {
		return fdIsAmortize;
	}

	public void setFdIsAmortize(Boolean fdIsAmortize) {
		this.fdIsAmortize = fdIsAmortize;
	}

	public Boolean getFdIsFee() {
		return fdIsFee;
	}

	public void setFdIsFee(Boolean fdIsFee) {
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

	/**
	 * 是否涉及外币
	 * @return
	 */
	public Boolean getFdIsForeign() {
		return fdIsForeign;
	}

	/**
	 * 是否涉及外币
	 * @param fdIsForeign
	 */
	public void setFdIsForeign(Boolean fdIsForeign) {
		this.fdIsForeign = fdIsForeign;
	}

	/**
	 * 是否移动端使用
	 * @return
	 */
	public Boolean getFdIsMobile() {
		return fdIsMobile;
	}
	/**
	 * 是否移动端使用
	 * @param fdIsMobile
	 */
	public void setFdIsMobile(Boolean fdIsMobile) {
		this.fdIsMobile = fdIsMobile;
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
    
    /**
	 * 附件实现
	 */
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

    /***** 归档信息 ******/
    private SysArchivesFileTemplate sysArchivesFileTemplate = null;
    @Override
    public SysArchivesFileTemplate getSysArchivesFileTemplate() {
        return sysArchivesFileTemplate;
    }
    /***** 归档信息 ******/
    @Override
    public void setSysArchivesFileTemplate(SysArchivesFileTemplate sysArchivesFileTemplate) {
        this.sysArchivesFileTemplate=sysArchivesFileTemplate;
    }

}
