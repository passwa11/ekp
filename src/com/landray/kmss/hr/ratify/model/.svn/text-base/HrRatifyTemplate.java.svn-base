package com.landray.kmss.hr.ratify.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.IBaseTemplateModel;
import com.landray.kmss.hr.ratify.forms.HrRatifyTemplateForm;
import com.landray.kmss.sys.archives.interfaces.ISysArchivesFileTemplateModel;
import com.landray.kmss.sys.archives.model.SysArchivesFileTemplate;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.model.SysCategoryProperty;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmTemplateModel;
import com.landray.kmss.sys.number.interfaces.ISysNumberModel;
import com.landray.kmss.sys.number.model.SysNumberMainMapp;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.print.interfaces.ISysPrintTemplateModel;
import com.landray.kmss.sys.print.model.SysPrintTemplate;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainModel;
import com.landray.kmss.sys.relation.model.SysRelationMain;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpModel;
import com.landray.kmss.sys.xform.interfaces.ISysFormTemplateModel;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * 人事流程模板
  */
public class HrRatifyTemplate extends ExtendAuthTmpModel implements
		InterceptFieldEnabled, IBaseTemplateModel, ISysLbpmTemplateModel,
		ISysFormTemplateModel, ISysNumberModel, ISysRelationMainModel,
		ISysPrintTemplateModel, IAttachment, ISysArchivesFileTemplateModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private Integer fdOrder;

	private String fdType;
	/**
	 * 模板key值，流程中用，不做页面展示
	 */
	protected String fdTempKey;

    private String fdDesc;

    private Boolean fdIsAvailable;

	private String titleRegulation;

	private String titleRegulationName;

    private String docXform;

    private Boolean docUseXform;

    private SysCategoryMain docCategory;

    private List<SysCategoryProperty> docProperties = new ArrayList<SysCategoryProperty>();

    private List sysWfTemplateModels;

    private List sysFormTemplateModels;

    private SysNumberMainMapp sysNumberMainMappModel = new SysNumberMainMapp();

    private SysRelationMain sysRelationMain = null;

    private String relationSeparate = null;

    private SysPrintTemplate sysPrintTemplate = new SysPrintTemplate();

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

	/*
	 * 多对多关联 审批流程模板关键字
	 */
	protected List docKeyword = new ArrayList();
	/*
	 * 多对多关联 反馈者
	 */
	protected List fdFeedback = new ArrayList();

    @Override
    public Class<HrRatifyTemplateForm> getFormClass() {
        return HrRatifyTemplateForm.class;
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
			toFormPropertyMap.put("authTmpAttCopys",
					new ModelConvertor_ModelListToString(
							"authTmpAttCopyIds:authTmpAttCopyNames",
							"fdId:fdName"));
			toFormPropertyMap.put("authTmpAttDownloads",
					new ModelConvertor_ModelListToString(
							"authTmpAttDownloadIds:authTmpAttDownloadNames",
							"fdId:fdName"));
			toFormPropertyMap.put("authTmpAttPrints",
					new ModelConvertor_ModelListToString(
							"authTmpAttPrintIds:authTmpAttPrintNames",
							"fdId:fdName"));
            toFormPropertyMap.put("docProperties", new ModelConvertor_ModelListToString("docPropertieIds:docPropertieNames", "fdId:fdName"));
			// 关键字
			toFormPropertyMap.put("docKeyword",
					new ModelConvertor_ModelListToString(
							"fdKeywordIds:fdKeywordNames", "fdId:docKeyword")
									.setSplitStr(" "));
			// 可反馈者
			toFormPropertyMap.put("fdFeedback",
					new ModelConvertor_ModelListToString(
							"fdFeedBackIds:fdFeedbackNames",
							"fdId:deptLevelNames"));
			// 模板修改者
			toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
			// 修改时间
			toFormPropertyMap.put("docAlterTime",
					new ModelConvertor_Common("docAlterTime")
							.setDateTimeType(DateUtil.TYPE_DATETIME));
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

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public String getFdTempKey() {
		return fdTempKey;
	}

	public void setFdTempKey(String fdTempKey) {
		this.fdTempKey = fdTempKey;
	}

	/**
	 * 模板描述
	 */
    public String getFdDesc() {
        return this.fdDesc;
    }

    /**
     * 模板描述
     */
    public void setFdDesc(String fdDesc) {
        this.fdDesc = fdDesc;
    }

    /**
     * 模板开启/关闭状态
     */
    public Boolean getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    /**
     * 模板开启/关闭状态
     */
    public void setFdIsAvailable(Boolean fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
    }

    /**
     * 主题自动生成规则
     */
	public String getTitleRegulation() {
		return this.titleRegulation;
    }

    /**
     * 主题自动生成规则
     */
	public void setTitleRegulation(String titleRegulation) {
		this.titleRegulation = titleRegulation;
    }

    /**
     * 主题自动生成规则
     */
	public String getTitleRegulationName() {
		return this.titleRegulationName;
    }

    /**
     * 主题自动生成规则
     */
	public void setTitleRegulationName(String titleRegulationName) {
		this.titleRegulationName = titleRegulationName;
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

	public List getDocKeyword() {
		return docKeyword;
	}

	public void setDocKeyword(List docKeyword) {
		this.docKeyword = docKeyword;
	}

	public List getFdFeedback() {
		return fdFeedback;
	}

	public void setFdFeedback(List fdFeedback) {
		this.fdFeedback = fdFeedback;
	}

	/***** 归档信息 ******/
	private SysArchivesFileTemplate  sysArchivesFileTemplate = null;
	@Override
	public SysArchivesFileTemplate getSysArchivesFileTemplate() {
		return sysArchivesFileTemplate;
	}
	@Override
	public void setSysArchivesFileTemplate(SysArchivesFileTemplate sysArchivesFileTemplate) {
		this.sysArchivesFileTemplate=sysArchivesFileTemplate;
	}
	/***** 归档信息 ******/


	private SysOrgPerson docAlteror;

	/**
	 * 修改者
	 * 
	 * @return docAlteror
	 */
	public SysOrgPerson getDocAlteror() {
		return docAlteror;
	}

	/**
	 * @param docAlteror
	 *            要设置的 docAlteror
	 */
	public void setDocAlteror(SysOrgPerson docAlteror) {
		this.docAlteror = docAlteror;
	}

	private Date docAlterTime;

	/**
	 * 修改时间
	 * 
	 * @return docAlterTime
	 */
	public Date getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            要设置的 docAlterTime
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 是否外部流程模板
	 */
	public Boolean fdIsExternal;

	/**
	 * 外部流程模板url
	 */
	public String fdExternalUrl;

	public Boolean getFdIsExternal() {
		return fdIsExternal;
	}

	public void setFdIsExternal(Boolean fdIsExternal) {
		this.fdIsExternal = fdIsExternal;
	}

	public String getFdExternalUrl() {
		return fdExternalUrl;
	}

	public void setFdExternalUrl(String fdExternalUrl) {
		this.fdExternalUrl = fdExternalUrl;
	}
	
	/**
	 * 是否启用电子签章
	 */

	private Boolean fdSignEnable;

	/**
	 * 是否启用电子签章
	 */
	public Boolean getFdSignEnable() {
		if (fdSignEnable == null) {
			return false;
		}
		return fdSignEnable;
	}

	/**
	 * 是否启用电子签章
	 */
	public void setFdSignEnable(Boolean fdSignEnable) {
		this.fdSignEnable = fdSignEnable;
	}

	/**
	 * 提醒中心
	 */
	private Map<String, Object> sysRemind = new HashMap<String, Object>();

	public Map<String, Object> getSysRemind() {
		return sysRemind;
	}

	public void setSysRemind(Map<String, Object> sysRemind) {
		this.sysRemind = sysRemind;
	}

}
