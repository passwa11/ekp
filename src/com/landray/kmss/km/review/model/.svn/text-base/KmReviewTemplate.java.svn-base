package com.landray.kmss.km.review.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.IBaseTemplateModel;
import com.landray.kmss.km.review.forms.KmReviewTemplateForm;
import com.landray.kmss.kms.multidoc.interfaces.IKmsMultidocSubsideModel;
import com.landray.kmss.kms.multidoc.model.KmsMultidocSubside;
import com.landray.kmss.sys.agenda.interfaces.ISysAgendaCategoryModel;
import com.landray.kmss.sys.agenda.model.SysAgendaCategory;
import com.landray.kmss.sys.archives.interfaces.ISysArchivesFileTemplateModel;
import com.landray.kmss.sys.archives.model.SysArchivesFileTemplate;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.sys.iassister.model.ISysIassisterTemplateModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.notify.interfaces.SysNotifyRemindCategoryContextModel;
import com.landray.kmss.sys.number.interfaces.ISysNumberModel;
import com.landray.kmss.sys.number.model.SysNumberMainMapp;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.print.interfaces.ISysPrintTemplateModel;
import com.landray.kmss.sys.print.model.SysPrintTemplate;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainModel;
import com.landray.kmss.sys.relation.model.SysRelationMain;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpModel;
import com.landray.kmss.sys.rule.model.ISysRuleTemplateModel;
import com.landray.kmss.sys.rule.model.SysRuleTemplate;
import com.landray.kmss.sys.workflow.interfaces.ISysWfTemplateModel;
import com.landray.kmss.sys.xform.interfaces.ISysFormTemplateModel;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建日期 2007-Aug-30
 * 
 * @author 舒斌 审批流程模板
 */
public class KmReviewTemplate extends ExtendAuthTmpModel implements
		IBaseTemplateModel, IAttachment, ISysRelationMainModel,
		ISysWfTemplateModel, InterceptFieldEnabled, ISysFormTemplateModel
		// ,ISysNotifyRemindCategoryModel
		, ISysAgendaCategoryModel, ISysNumberModel, ISysPrintTemplateModel, IKmsMultidocSubsideModel,
		ISysRuleTemplateModel, ISysIassisterTemplateModel, ISysArchivesFileTemplateModel {

	/*
	 * 模板名称
	 */
	protected java.lang.String fdName;
	
	/*
	 * 模板描述
	 */
	protected java.lang.String fdDesc;
	
	/*
	 * 是否有效
	 */
	private Boolean fdIsAvailable;

	/*
	 * 排序号
	 */
	protected java.lang.Long fdOrder;

	/*
	 * 编号前缀
	 */

	private java.lang.String fdNumberPrefix;
	/*
	 * 流程标签可见
	 */
	protected java.lang.Boolean fdLableVisiable;

	/*
	 * 允许修改反馈人
	 */
	protected java.lang.Boolean fdFeedbackModify;

	/*
	 * 内容
	 */
	protected java.lang.String docContent;

	/*
	 * 创建时间
	 */
	protected java.util.Date docCreateTime;

	/*
	 * 标题规则
	 */
	protected java.lang.String titleRegulation;
	protected java.lang.String titleRegulationName;
	/*
	 * 是否允许传阅
	 */
	protected Boolean fdCanCircularize;

	/*
	 * 是否外部流程模板
	 */
	public Boolean fdIsExternal;
	/*
	 * 外部流程模板url
	 */
	public String fdExternalUrl;
	
	
	/**
	 * 是否导入表单数据
	 */
	public Boolean fdIsImport;
	
	/**
	 * 不需要导入的字段id
	 */
	public String fdUnImportFieldIds = null;
	
	/**
	 * 不需要导入的字段name
	 */
	public String fdUnImportFieldNames = null;
	//编辑主题
	private Boolean editDocSubject = null;

	/**
	 * 模板图标
	 */
	protected String fdIcon;

	public String getFdIcon() {
		return fdIcon;
	}

	public void setFdIcon(String fdIcon) {
		this.fdIcon = fdIcon;
	}
	
	public Boolean getEditDocSubject() {
		return editDocSubject;
	}

	public void setEditDocSubject(Boolean editDocSubject) {
		this.editDocSubject = editDocSubject;
	}
	
	public String getFdUnImportFieldIds() {
		return fdUnImportFieldIds;
	}

	public void setFdUnImportFieldIds(String fdUnImportFieldIds) {
		this.fdUnImportFieldIds = fdUnImportFieldIds;
	}

	public String getFdUnImportFieldNames() {
		return fdUnImportFieldNames;
	}

	public void setFdUnImportFieldNames(String fdUnImportFieldNames) {
		this.fdUnImportFieldNames = fdUnImportFieldNames;
	}
	
	public Boolean getFdIsImport() {
		return fdIsImport;
	}

	public void setFdIsImport(Boolean fdIsImport) {
		this.fdIsImport = fdIsImport;
	}

	public Boolean getFdIsExternal() {
		return fdIsExternal;
	}

	public Boolean getFdCanCircularize() {
		return fdCanCircularize;
	}

	public void setFdCanCircularize(Boolean fdCanCircularize) {
		this.fdCanCircularize = fdCanCircularize;
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

	
	public java.lang.String getTitleRegulation() {
		return titleRegulation;
	}

	public void setTitleRegulation(java.lang.String titleRegulation) {
		this.titleRegulation = titleRegulation;
	}

	public java.lang.String getTitleRegulationName() {
		return titleRegulationName;
	}

	public void setTitleRegulationName(java.lang.String titleRegulationName) {
		this.titleRegulationName = titleRegulationName;
	}

	public KmReviewTemplate() {
		super();
	}

	/**
	 * @return 返回 模板名称
	 */
	@Override
	public java.lang.String getFdName() {
		// return fdName;
		return SysLangUtil.getPropValue(this, "fdName", this.fdName);
	}

	/**
	 * @param fdName
	 *            要设置的 模板名称
	 */
	public void setFdName(java.lang.String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}
	
	public java.lang.String getFdDesc() {
		return fdDesc;
	}

	public void setFdDesc(java.lang.String fdDesc) {
		this.fdDesc = fdDesc;
	}
	
	public Boolean getFdIsAvailable() {
		if (fdIsAvailable == null) {
            fdIsAvailable = Boolean.TRUE;
        }
		return fdIsAvailable;
	}

	public void setFdIsAvailable(Boolean fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}

	public java.lang.Boolean getFdLableVisiable() {
		return fdLableVisiable;
	}

	public void setFdLableVisiable(java.lang.Boolean fdLableVisiable) {
		this.fdLableVisiable = fdLableVisiable;
	}

	public java.lang.Boolean getFdFeedbackModify() {
		if (null == fdFeedbackModify) {
			fdFeedbackModify = new Boolean(false);
		}
		return fdFeedbackModify;
	}

	public void setFdFeedbackModify(java.lang.Boolean fdFeedbackModify) {
		this.fdFeedbackModify = fdFeedbackModify;
	}

	/**
	 * @return 返回 内容
	 */
	public java.lang.String getDocContent() {
		return (String) readLazyField("docContent", docContent);
	}

	/**
	 * @param docContent
	 *            要设置的 内容
	 */
	public void setDocContent(java.lang.String docContent) {
		this.docContent = (String) writeLazyField("docContent",
				this.docContent, docContent);
	}

	/**
	 * @return 返回 创建时间
	 */
	@Override
	public java.util.Date getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            要设置的 创建时间
	 */
	@Override
	public void setDocCreateTime(java.util.Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/*
	 * 多对多关联 模板相关岗位
	 */
	protected List fdPosts = new ArrayList();

	/*
	 * 分类
	 */
	protected SysCategoryMain docCategory = null;

	/*
	 * 多对多关联 模板相关属性
	 */
	protected List docProperties = new ArrayList();

	/*
	 * 多对多关联 流程标签可阅读者
	 */
	protected List fdLabelReaders = new ArrayList();

	/*
	 * 多对多关联 反馈者
	 */
	protected List fdFeedback = new ArrayList();

	/*
	 * 多对多关联 审批流程模板关键字
	 */
	protected List docKeyword = new ArrayList();

	@Override
	public Class getFormClass() {
		return KmReviewTemplateForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.addNoConvertProperty("checkTemplates");
			// 类别
			toFormPropertyMap.put("docCategory.fdName", "fdCategoryName");
			toFormPropertyMap.put("docCategory.fdId", "fdCategoryId");
			// 相关岗位
			toFormPropertyMap.put("fdPosts",
					new ModelConvertor_ModelListToString(
							"fdPostIds:fdPostNames", "fdId:fdName"));
			// 相关属性
			toFormPropertyMap.put("docProperties",
					new ModelConvertor_ModelListToString(
							"docPropertyIds:docPropertyNames", "fdId:fdName"));
			// 流程标签可阅读者
			toFormPropertyMap.put("fdLabelReaders",
					new ModelConvertor_ModelListToString(
							"fdLabelReaderIds:fdLabelReaderNames",
							"fdId:deptLevelNames"));
			// 可反馈者
			toFormPropertyMap.put("fdFeedback",
					new ModelConvertor_ModelListToString(
							"fdFeedBackIds:fdFeedbackNames", "fdId:deptLevelNames"));
			// 关键字
			toFormPropertyMap.put("docKeyword",
					new ModelConvertor_ModelListToString(
							"fdKeywordIds:fdKeywordNames", "fdId:docKeyword"));
			// 模板创建者
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			// 创建时间
			toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common(
					"docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));

			// 模板修改者
			toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
			// 修改时间
			toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common(
					"docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
		}
		return toFormPropertyMap;
	}

	public java.lang.Long getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(java.lang.Long fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 附件实现
	 */
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
	public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

	public List getDocProperties() {
		return docProperties;
	}

	public void setDocProperties(List docProperties) {
		this.docProperties = docProperties;
	}

	public List getFdFeedback() {
		return fdFeedback;
	}

	public void setFdFeedback(List fdFeedback) {
		this.fdFeedback = fdFeedback;
	}

	public List getDocKeyword() {
		return docKeyword;
	}

	public void setDocKeyword(List docKeyword) {
		this.docKeyword = docKeyword;
	}

	public List getFdLabelReaders() {
		return fdLabelReaders;
	}

	public void setFdLabelReaders(List fdLabelReaders) {
		this.fdLabelReaders = fdLabelReaders;
	}

	public List getFdPosts() {
		return fdPosts;
	}

	public void setFdPosts(List fdPosts) {
		this.fdPosts = fdPosts;
	}

	@Override
	public SysCategoryMain getDocCategory() {
		return docCategory;
	}

	public void setDocCategory(SysCategoryMain docCategory) {
		this.docCategory = docCategory;
	}

	public java.lang.String getFdNumberPrefix() {
		return fdNumberPrefix;
	}

	public void setFdNumberPrefix(java.lang.String fdNumberPrefix) {
		this.fdNumberPrefix = fdNumberPrefix;
	}
	
	/*
	 * 关联域模型信息
	 */
	private SysRelationMain sysRelationMain = null;

	@Override
	public SysRelationMain getSysRelationMain() {
		return sysRelationMain;
	}

	@Override
	public void setSysRelationMain(SysRelationMain sysRelationMain) {
		this.sysRelationMain = sysRelationMain;
	}

	String relationSeparate = null;

	public String getRelationSeparate() {
		return relationSeparate;
	}

	public void setRelationSeparate(String relationSeparate) {
		this.relationSeparate = relationSeparate;
	}

	/**
	 * 流程模板
	 */
	private List sysWfTemplateModels;

	@Override
	public List getSysWfTemplateModels() {
		return sysWfTemplateModels;
	}

	@Override
	public void setSysWfTemplateModels(List sysWfTemplateModels) {
		this.sysWfTemplateModels = sysWfTemplateModels;
	}

	/**
	 * 表单模板
	 */
	private List sysFormTemplateModels;

	@Override
	public List getSysFormTemplateModels() {
		return sysFormTemplateModels;
	}

	@Override
	public void setSysFormTemplateModels(List sysFormTemplateModels) {
		this.sysFormTemplateModels = sysFormTemplateModels;
	}

	// /*
	// * 文件地址
	// */
	// private String fdExtFilePath;
	//
	// public String getFdExtFilePath() {
	// return XFormUtil.getFileName(sysFormTemplateModels, "reviewMainDoc");
	// }
	//
	// public void setFdExtFilePath(String filePath) {
	// fdExtFilePath = filePath;
	// }

	/**
	 * 是否使用表单
	 */
	private Boolean fdUseForm = Boolean.FALSE;
	
	

	/**
	 * 是否使用表单
	 * 
	 * @return fdUseForm
	 */
	public Boolean getFdUseForm() {
		return fdUseForm;
	}

	/**
	 * 是否使用表单
	 * 
	 * @param fdUseForm
	 *            要设置的 fdUseForm
	 */
	public void setFdUseForm(Boolean fdUseForm) {
		this.fdUseForm = fdUseForm;
	}
	
	private Boolean fdDisableMobileForm = Boolean.FALSE;
	
	public Boolean getFdDisableMobileForm() {
		if (fdDisableMobileForm == null) {
            fdDisableMobileForm = Boolean.FALSE;
        }
		return fdDisableMobileForm;
	}

	public void setFdDisableMobileForm(Boolean fdDisableMobileForm) {
		this.fdDisableMobileForm = fdDisableMobileForm;
	}

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

	// ==============提醒机制(分类) 开始====================
	/**
	 * private SysNotifyRemindCategoryContextModel
	 * sysNotifyRemindCategoryContextModel = new
	 * SysNotifyRemindCategoryContextModel();
	 * 
	 * public SysNotifyRemindCategoryContextModel
	 * getSysNotifyRemindCategoryContextModel() { return
	 * sysNotifyRemindCategoryContextModel; }
	 * 
	 * public void setSysNotifyRemindCategoryContextModel(
	 * SysNotifyRemindCategoryContextModel sysNotifyRemindCategoryContextModel)
	 * { this.sysNotifyRemindCategoryContextModel =
	 * sysNotifyRemindCategoryContextModel; }
	 **/
	// ==============提醒机制(分类) 结束====================
	// ==============日程机制开始(分类) 开始=================
	private SysNotifyRemindCategoryContextModel sysNotifyRemindCategoryContextModel = new SysNotifyRemindCategoryContextModel();

	@Override
	public SysNotifyRemindCategoryContextModel getSysNotifyRemindCategoryContextModel() {
		return sysNotifyRemindCategoryContextModel;
	}

	@Override
	public void setSysNotifyRemindCategoryContextModel(
			SysNotifyRemindCategoryContextModel sysNotifyRemindCategoryContextModel) {
		this.sysNotifyRemindCategoryContextModel = sysNotifyRemindCategoryContextModel;
	}

	private SysAgendaCategory sysAgendaCategory = new SysAgendaCategory();

	@Override
	public SysAgendaCategory getSysAgendaCategory() {
		return sysAgendaCategory;
	}

	@Override
	public void setSysAgendaCategory(SysAgendaCategory sysAgendaCategory) {
		this.sysAgendaCategory = sysAgendaCategory;
	}

	// ==============日程机制结束(分类) 结束=================

	/**
	 * 日程机制从当前业务模块同步数据到时间管理模块同步时机
	 */
	private String syncDataToCalendarTime = "noSync";

	public String getSyncDataToCalendarTime() {
		return syncDataToCalendarTime;
	}

	public void setSyncDataToCalendarTime(String syncDataToCalendarTime) {
		this.syncDataToCalendarTime = syncDataToCalendarTime;
	}

	/**
	 * 编号机制
	 */
	private SysNumberMainMapp sysNumberMainMapp = new SysNumberMainMapp();

	@Override
	public SysNumberMainMapp getSysNumberMainMappModel() {
		return sysNumberMainMapp;
	}

	@Override
	public void setSysNumberMainMappModel(SysNumberMainMapp sysNumberMainMapp1) {
		this.sysNumberMainMapp = sysNumberMainMapp1;
	}
	
	/**
	 * 打印机制
	 */
	private SysPrintTemplate sysPrintTemplate=new SysPrintTemplate();

	@Override
	public SysPrintTemplate getSysPrintTemplate() {
		return sysPrintTemplate;
	}

	@Override
	public void setSysPrintTemplate(SysPrintTemplate sysPrintTemplate) {
		this.sysPrintTemplate = sysPrintTemplate;
	}


	/**
	 * 分类全名称，适用于模板搜索时显示其分类全路径
	 */
	private String creategoryFullName;

	public String getCreategoryFullName() {
		return creategoryFullName;
	}

	public void setCreategoryFullName(String creategoryFullName) {
		this.creategoryFullName = creategoryFullName;
	}
	
	/**
	 * 支持移动端新建
	 */
	private Boolean fdIsMobileCreate;
	
	public Boolean getFdIsMobileCreate() {
		return fdIsMobileCreate;
	}

	public void setFdIsMobileCreate(Boolean fdIsMobileCreate) {
		this.fdIsMobileCreate = fdIsMobileCreate;
	}

	/**
	 * 支持移动端审批
	 */
	private Boolean fdIsMobileApprove;

	public Boolean getFdIsMobileApprove() {
		return fdIsMobileApprove;
	}

	public void setFdIsMobileApprove(Boolean fdIsMobileApprove) {
		this.fdIsMobileApprove = fdIsMobileApprove;
	}
	
	/**
	 * 支持移动端查阅
	 */
	private Boolean fdIsMobileView;

	public Boolean getFdIsMobileView() {
		return fdIsMobileView;
	}

	public void setFdIsMobileView(Boolean fdIsMobileView) {
		this.fdIsMobileView = fdIsMobileView;
	}
	
	/**
	 * 是否允许复制流程（为空或true时，允许复制流程。为false时禁止复制流程）
	 */
	private Boolean fdIsCopyDoc;

	public Boolean getFdIsCopyDoc() {
		if (fdIsCopyDoc == null) {
            fdIsCopyDoc = Boolean.TRUE;
        }
		return fdIsCopyDoc;
	}

	public void setFdIsCopyDoc(Boolean fdIsCopyDoc) {
		this.fdIsCopyDoc = fdIsCopyDoc;
	}

	private Boolean fdUseWord = Boolean.FALSE;

	/**
	 * 是否使用Word
	 * 
	 * @return fdUseWord
	 */
	public Boolean getFdUseWord() {
		return fdUseWord;
	}

	/**
	 * 是否使用Word
	 * 
	 * @param fdUseWord
	 */
	public void setFdUseWord(Boolean fdUseWord) {
		this.fdUseWord = fdUseWord;
	}

	/***** 知识沉淀 *****/
	private KmsMultidocSubside kmsMultidocSubside = null;

	@Override
	public KmsMultidocSubside getKmsMultidocSubSide() {
		return kmsMultidocSubside;
	}

	@Override
	public void
			setKmsMultidocSubside(KmsMultidocSubside kmsMultidocSubside) {
		this.kmsMultidocSubside = kmsMultidocSubside;
	}
	/***** 知识沉淀 *****/

	/* 规则机制start */
	private List<SysRuleTemplate> sysRuleTemplates = null;

	@Override
	public List<SysRuleTemplate> getSysRuleTemplates() {
		return sysRuleTemplates;
	}

	@Override
	public void setSysRuleTemplates(List<SysRuleTemplate> sysRuleTemplates) {
		this.sysRuleTemplates = sysRuleTemplates;
	}
	/* 规则机制end */
	
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

	private JSONObject checkTemplates;
	private JSONArray checkGroups;

	@Override
	public JSONObject getCheckTemplates() {
		return checkTemplates;
	}

	@Override
	public void setCheckTemplates(JSONObject checkTemplates) {
		this.checkTemplates = checkTemplates;
	}

	@Override
	public JSONArray getCheckGroups() {
		return checkGroups;
	}

	@Override
	public void setCheckGroups(JSONArray checkGroups) {
		this.checkGroups = checkGroups;
	}

	/***** 归档信息 ******/
	private SysArchivesFileTemplate sysArchivesFileTemplate = null;
	@Override
	public SysArchivesFileTemplate getSysArchivesFileTemplate() {
		return sysArchivesFileTemplate;
	}
	@Override
	public void setSysArchivesFileTemplate(SysArchivesFileTemplate sysArchivesFileTemplate) {
		this.sysArchivesFileTemplate=sysArchivesFileTemplate;
	}
	/***** 归档信息 ******/


}
