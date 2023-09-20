package com.landray.kmss.hr.ratify.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormConvertor_NamesToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.ratify.model.HrRatifyTKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.sys.archives.forms.SysArchivesFileTemplateForm;
import com.landray.kmss.sys.archives.interfaces.ISysArchivesFileTemplateForm;
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
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpForm;
import com.landray.kmss.sys.xform.base.forms.SysFormTemplateForm;
import com.landray.kmss.sys.xform.interfaces.ISysFormTemplateForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
  * 人事流程模板
  */
public class HrRatifyTemplateForm extends ExtendAuthTmpForm
		implements ISysLbpmTemplateForm, ISysFormTemplateForm, ISysNumberForm,
		ISysRelationMainForm, ISysPrintTemplateForm, IAttachmentForm,
        ISysArchivesFileTemplateForm{

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdOrder;

	private String fdType;

	/**
	 * 模板key值，流程中用，不做页面展示
	 */
	protected String fdTempKey;

    private String docCreateTime;

    private String fdDesc;

    private String fdIsAvailable;

	private String titleRegulation;

	private String titleRegulationName;

    private String docXform;

    private String docUseXform;

    private String docCategoryId;

    private String docCategoryName;

    private String docCreatorId;

    private String docCreatorName;

    private String docPropertieIds;

    private String docPropertieNames;

    private AutoHashMap sysWfTemplateForms = new AutoHashMap(LbpmTemplateForm.class);

    private AutoHashMap sysFormTemplateForms = new AutoHashMap(SysFormTemplateForm.class);

    private String fdExtFilePath;

    private SysNumberMainMappForm sysNumberMainMappForm = new SysNumberMainMappForm();

    private SysRelationMainForm sysRelationMainForm = new SysRelationMainForm();

    private SysPrintTemplateForm sysPrintTemplateForm = new SysPrintTemplateForm();

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

	/*
	 * 关键字
	 */
	private String fdKeywordNames = null;

	private String fdKeywordIds = null;

	/*
	 * 实施反馈人
	 */
	private String fdFeedbackNames = null;

	private String fdFeedBackIds = null;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdOrder = null;
		fdType = null;
		fdTempKey = null;
        docCreateTime = null;
        fdDesc = null;
		fdIsAvailable = "true";
		titleRegulation = null;
		titleRegulationName = null;
        docXform = null;
        docUseXform = null;
        docCategoryId = null;
        docCategoryName = null;
        docCreatorId = null;
        docCreatorName = null;
        docPropertieIds = null;
        docPropertieNames = null;
        sysWfTemplateForms.clear();
        fdExtFilePath = null;
        sysFormTemplateForms.clear();
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
		fdKeywordNames = null;
		fdKeywordIds = null;
		fdFeedbackNames = null;
		fdFeedBackIds = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<HrRatifyTemplate> getModelClass() {
        return HrRatifyTemplate.class;
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
			toModelPropertyMap.put("authTmpAttCopyIds",
					new FormConvertor_IDsToModelList("authTmpAttCopys",
							SysOrgElement.class));
			toModelPropertyMap.put("authTmpAttDownloadIds",
					new FormConvertor_IDsToModelList("authTmpAttDownloads",
							SysOrgElement.class));
			toModelPropertyMap.put("authTmpAttPrintIds",
					new FormConvertor_IDsToModelList("authTmpAttPrints",
							SysOrgElement.class));
            toModelPropertyMap.put("docPropertieIds", new FormConvertor_IDsToModelList("docProperties", SysCategoryProperty.class));
			// 关键字
			toModelPropertyMap.put("fdKeywordNames",
					new FormConvertor_NamesToModelList("docKeyword", "fdObject",
							HrRatifyTemplate.class, "docKeyword",
							HrRatifyTKeyword.class).setSplitStr(" "));
			// 反馈人
			toModelPropertyMap.put("fdFeedBackIds",
					new FormConvertor_IDsToModelList("fdFeedback",
							SysOrgElement.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 名称
     */
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
    public String getFdOrder() {
        return this.fdOrder;
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
	 * 排序号
	 */
    public void setFdOrder(String fdOrder) {
        this.fdOrder = fdOrder;
    }

    /**
     * 创建时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
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
    public String getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    /**
     * 模板开启/关闭状态
     */
    public void setFdIsAvailable(String fdIsAvailable) {
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
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 创建人
     */
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

	public String getFdKeywordIds() {
		return fdKeywordIds;
	}

	public void setFdKeywordIds(String fdKeywordIds) {
		this.fdKeywordIds = fdKeywordIds;
	}

	public String getFdKeywordNames() {
		return fdKeywordNames;
	}

	public void setFdKeywordNames(String fdKeywordNames) {
		this.fdKeywordNames = fdKeywordNames;
	}

	public String getFdFeedBackIds() {
		return fdFeedBackIds;
	}

	public void setFdFeedBackIds(String fdFeedBackIds) {
		this.fdFeedBackIds = fdFeedBackIds;
	}

	public String getFdFeedbackNames() {
		return fdFeedbackNames;
	}

	public void setFdFeedbackNames(String fdFeedbackNames) {
		this.fdFeedbackNames = fdFeedbackNames;
	}

    /***** 归档信息 ******/
    private SysArchivesFileTemplateForm sysArchivesFileTemplateForm = new SysArchivesFileTemplateForm();

    @Override
    public SysArchivesFileTemplateForm getSysArchivesFileTemplateForm() {
        return sysArchivesFileTemplateForm;
    }
    @Override
    public void setSysArchivesFileTemplateForm(SysArchivesFileTemplateForm form) {
        this.sysArchivesFileTemplateForm=form;
    }
    /***** 归档信息 ******/


    private String docAlterorName;

	/**
	 * @return docAlterorName
	 */
	public String getDocAlterorName() {
		return docAlterorName;
	}

	/**
	 * @param docAlterorName
	 *            要设置的 docAlterorName
	 */
	public void setDocAlterorName(String docAlterorName) {
		this.docAlterorName = docAlterorName;
	}

	private String docAlterTime;

	/**
	 * @return docAlterTime
	 */
	public String getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            要设置的 docAlterTime
	 */
	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 是否外部流程模板和外部URL
	 */
	public String fdIsExternal = null;
	public String fdExternalUrl = null;

	public String getFdIsExternal() {
		return fdIsExternal;
	}

	public void setFdIsExternal(String fdIsExternal) {
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
	private String fdSignEnable;

	/**
	 * 是否启用电子签章
	 */
	public String getFdSignEnable() {
		return fdSignEnable;
	}

	/**
	 * 是否启用电子签章
	 */
	public void setFdSignEnable(String fdSignEnable) {
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
