package com.landray.kmss.fssc.fee.model;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.IBaseTemplateModel;
import com.landray.kmss.fssc.fee.forms.FsscFeeTemplateForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.model.SysCategoryProperty;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmTemplateModel;
import com.landray.kmss.sys.number.interfaces.ISysNumberModel;
import com.landray.kmss.sys.number.model.SysNumberMainMapp;
import com.landray.kmss.sys.print.interfaces.ISysPrintTemplateModel;
import com.landray.kmss.sys.print.model.SysPrintTemplate;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainModel;
import com.landray.kmss.sys.relation.model.SysRelationMain;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpModel;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;
import com.landray.kmss.sys.xform.interfaces.ISysFormTemplateModel;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

/**
  * 模板设置
  */
public class FsscFeeTemplate extends ExtendAuthTmpModel implements IBaseTemplateModel,InterceptFieldEnabled, ISysLbpmTemplateModel, ISysFormTemplateModel, ISysNumberModel, ISysRelationMainModel, ISysPrintTemplateModel, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;
    
    private Boolean fdIsMobile;

    private String fdName;
    
    private String fdForbid;

    private Integer fdOrder;

    private String docXform;

    private Boolean docUseXform;
    
    private String fdSubjectRule;
    
    private String fdSubjectRuleText;
    
    private String fdSubjectType;
    
    //携程商旅字段
    private String fdServiceType;

    private SysCategoryMain docCategory;

    private List<SysCategoryProperty> docProperties = new ArrayList<SysCategoryProperty>();

    private List sysWfTemplateModels;

    private List sysFormTemplateModels;
    
    private List<FsscFeeMobileConfig> fdConfig;

    private SysNumberMainMapp sysNumberMainMappModel = new SysNumberMainMapp();

    private SysRelationMain sysRelationMain = null;

    private String relationSeparate = null;
    
    private SysPrintTemplate sysPrintTemplate = new SysPrintTemplate();

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<FsscFeeTemplateForm> getFormClass() {
        return FsscFeeTemplateForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCategory.fdName", "docCategoryName");
            toFormPropertyMap.put("docCategory.fdId", "docCategoryId");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("authEditors", new ModelConvertor_ModelListToString("authEditorIds:authEditorNames", "fdId:fdName"));
            toFormPropertyMap.put("authTmpReaders", new ModelConvertor_ModelListToString("authTmpReaderIds:authTmpReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("docProperties", new ModelConvertor_ModelListToString("docPropertieIds:docPropertieNames", "fdId:fdName"));
            toFormPropertyMap.put("fdConfig", new ModelConvertor_ModelListToFormList("fdConfig_Form"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 排序号
     */
    public Integer getFdOrder() {
        return this.fdOrder;
    }

    /**
     * 排序号
     */
    public void setFdOrder(Integer fdOrder) {
        this.fdOrder = fdOrder;
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

    /**
     * 所属分类
     */
    @Override
    public SysCategoryMain getDocCategory() {
        return this.docCategory;
    }

    /**
     * 所属分类
     */
    public void setDocCategory(SysCategoryMain docCategory) {
        this.docCategory = docCategory;
    }

    /**
     * 辅分类
     */
    public List<SysCategoryProperty> getDocProperties() {
        return this.docProperties;
    }

    /**
     * 辅分类
     */
    public void setDocProperties(List<SysCategoryProperty> docProperties) {
        this.docProperties = docProperties;
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
    
    @Override
    public SysPrintTemplate getSysPrintTemplate() {
        return this.sysPrintTemplate;
    }

    @Override
    public void setSysPrintTemplate(SysPrintTemplate sysPrintTemplate) {
        this.sysPrintTemplate = sysPrintTemplate;
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
	//携程商旅移动化
	public String getFdServiceType() {
		return fdServiceType;
	}

	public void setFdServiceType(String fdServiceType) {
		this.fdServiceType = fdServiceType;
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

	public List<FsscFeeMobileConfig> getFdConfig() {
		return fdConfig;
	}

	public void setFdConfig(List<FsscFeeMobileConfig> fdConfig) {
		this.fdConfig = fdConfig;
	}
}
