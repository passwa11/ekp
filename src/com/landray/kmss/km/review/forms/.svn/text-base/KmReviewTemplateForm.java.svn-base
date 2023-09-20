package com.landray.kmss.km.review.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormConvertor_NamesToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.review.model.KmReviewTemplate;
import com.landray.kmss.km.review.model.KmReviewTemplateKeyword;
import com.landray.kmss.kms.multidoc.forms.KmsMultidocSubsideForm;
import com.landray.kmss.kms.multidoc.interfaces.IKmsMultidocSubsideForm;
import com.landray.kmss.sys.agenda.forms.SysAgendaCategoryForm;
import com.landray.kmss.sys.agenda.interfaces.ISysAgendaCategoryForm;
import com.landray.kmss.sys.archives.forms.SysArchivesFileTemplateForm;
import com.landray.kmss.sys.archives.interfaces.ISysArchivesFileTemplateForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.model.SysCategoryProperty;
import com.landray.kmss.sys.iassister.forms.ISysIassisterTemplateForm;
import com.landray.kmss.sys.notify.interfaces.SysNotifyRemindCategoryContextForm;
import com.landray.kmss.sys.number.forms.SysNumberMainMappForm;
import com.landray.kmss.sys.number.interfaces.ISysNumberForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.print.forms.SysPrintTemplateForm;
import com.landray.kmss.sys.print.interfaces.ISysPrintTemplateForm;
import com.landray.kmss.sys.relation.forms.SysRelationMainForm;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpForm;
import com.landray.kmss.sys.rule.forms.ISysRuleTemplateForm;
import com.landray.kmss.sys.rule.forms.SysRuleTemplateForm;
import com.landray.kmss.sys.workflow.base.forms.SysWfTemplateForm;
import com.landray.kmss.sys.workflow.interfaces.ISysWfTemplateForm;
import com.landray.kmss.sys.xform.base.forms.SysFormTemplateForm;
import com.landray.kmss.sys.xform.interfaces.ISysFormTemplateForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建日期 2007-Aug-30
 * 
 * @author 舒斌
 */
public class KmReviewTemplateForm extends ExtendAuthTmpForm implements
		IAttachmentForm, ISysRelationMainForm, ISysWfTemplateForm,
		ISysFormTemplateForm
		// ,ISysNotifyRemindCategoryForm
		, ISysAgendaCategoryForm, ISysNumberForm, ISysPrintTemplateForm, IKmsMultidocSubsideForm,
		ISysRuleTemplateForm, ISysIassisterTemplateForm , ISysArchivesFileTemplateForm {
	/*
	 * 模板名称
	 */
	private String fdName = null;
	
	/*
	 * 模板描述
	 */
	private String fdDesc = null;
	
	/*
	 * 是否有效
	 */
	private String fdIsAvailable = "true";

	/*
	 * 编号前缀
	 */
	private String fdNumberPrefix = null;

	/*
	 * 排序号
	 */
	private String fdOrder = null;

	/*
	 * 当前流水号
	 */
	private String fdNumber = null;

	/*
	 * 流程标签可见
	 */
	private String fdLableVisiable = null;

	/*
	 * 允许修改反馈人
	 */
	private String fdFeedbackModify = null;

	/*
	 * 内容
	 */
	private String docContent = null;

	/*
	 * 创建人
	 */
	private String docCreatorId = null;

	private String docCreatorName = null;

	/*
	 * 创建时间
	 */
	private String docCreateTime = null;

	/*
	 * 相关岗位
	 */
	private String fdPostNames = null;

	private String fdPostIds = null;

	/*
	 * 分类
	 */
	private String fdCategoryName = null;

	private String fdCategoryId = null;

	/*
	 * 相关属性
	 */
	private String docPropertyNames = null;

	private String docPropertyIds = null;

	/*
	 * 流程标签可阅读者
	 */
	private String fdLabelReaderNames = null;

	private String fdLabelReaderIds = null;

	/*
	 * 实施反馈人
	 */
	private String fdFeedbackNames = null;

	private String fdFeedBackIds = null;

	/*
	 * 关键字
	 */
	private String fdKeywordNames = null;

	private String fdKeywordIds = null;
	
	/*
	 * 是否允许传阅
	 */
	private String fdCanCircularize;

	/*
	 * 是否外部流程模板和外部URL
	 */
	public String fdIsExternal = null;
	public String fdExternalUrl = null;

	/*
	 * 模板图标
	 */
	public String fdIcon;

	public String getFdIcon() {
		return fdIcon;
	}

	public void setFdIcon(String fdIcon) {
		this.fdIcon = fdIcon;
	}
	
	public String getFdCanCircularize() {
		return fdCanCircularize;
	}

	public void setFdCanCircularize(String fdCanCircularize) {
		this.fdCanCircularize = fdCanCircularize;
	}

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

	// 标题规则
	private String titleRegulation = null;
	//编辑主题
	private String editDocSubject = null;
	
	public String getEditDocSubject() {
		return editDocSubject;
	}

	public void setEditDocSubject(String editDocSubject) {
		this.editDocSubject = editDocSubject;
	}
	
	/**
	 * 表单数据导入
	 */
	public String fdIsImport = null;
	
	/**
	 * 不需要导入的字段id
	 */
	public String fdUnImportFieldIds = null;
	
	/**
	 * 不需要导入的字段name
	 */
	public String fdUnImportFieldNames = null;
	
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

	public String getFdIsImport() {
		return fdIsImport;
	}

	public void setFdIsImport(String fdIsImport) {
		this.fdIsImport = fdIsImport;
	}

	public String getTitleRegulation() {
		return titleRegulation;
	}

	public void setTitleRegulation(String titleRegulation) {
		this.titleRegulation = titleRegulation;
	}

	public String getTitleRegulationName() {
		return titleRegulationName;
	}

	public void setTitleRegulationName(String titleRegulationName) {
		this.titleRegulationName = titleRegulationName;
	}

	private String titleRegulationName = null;

	private static FormToModelPropertyMap formToModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {

		if (formToModelPropertyMap == null) {
			formToModelPropertyMap = new FormToModelPropertyMap();
			formToModelPropertyMap.putAll(super.getToModelPropertyMap());
			formToModelPropertyMap.addNoConvertProperty("checkTemplates");
			formToModelPropertyMap.addNoConvertProperty("checkGroups");
			// 类别
			formToModelPropertyMap.put("fdCategoryId",
					new FormConvertor_IDToModel("docCategory",
							SysCategoryMain.class));
			// 相关岗位
			formToModelPropertyMap.put("fdPostIds",
					new FormConvertor_IDsToModelList("fdPosts",
							SysOrgElement.class));
			// 相关属性
			formToModelPropertyMap.put("docPropertyIds",
					new FormConvertor_IDsToModelList("docProperties",
							SysCategoryProperty.class));
			// 流程标签可阅读者
			formToModelPropertyMap.put("fdLabelReaderIds",
					new FormConvertor_IDsToModelList("fdLabelReaders",
							SysOrgElement.class));
			// 反馈人
			formToModelPropertyMap.put("fdFeedBackIds",
					new FormConvertor_IDsToModelList("fdFeedback",
							SysOrgElement.class));
			// 关键字
			formToModelPropertyMap.put("fdKeywordNames",
					new FormConvertor_NamesToModelList("docKeyword",
							"kmReviewTemplate", KmReviewTemplate.class,
							"docKeyword", KmReviewTemplateKeyword.class));
		}
		return formToModelPropertyMap;
	}

	@Override
    public String getAuthReaderNoteFlag() {
		return "2";
	}

	/**
	 * @return 返回 模板名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            要设置的 模板名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdDesc() {
		return fdDesc;
	}

	public void setFdDesc(String fdDesc) {
		this.fdDesc = fdDesc;
	}
	
	public String getFdIsAvailable() {
		return fdIsAvailable;
	}

	public void setFdIsAvailable(String fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}

	/**
	 * @return 返回 编号前缀
	 */
	public String getFdNumberPrefix() {
		return fdNumberPrefix;
	}

	/**
	 * @param fdNumberPrefix
	 *            要设置的 编号前缀
	 */
	public void setFdNumberPrefix(String fdNumberPrefix) {
		this.fdNumberPrefix = fdNumberPrefix;
	}

	/**
	 * @return 返回 当前流水号
	 */
	public String getFdNumber() {
		return fdNumber;
	}

	/**
	 * @param fdNumber
	 *            要设置的 当前流水号
	 */
	public void setFdNumber(String fdNumber) {
		this.fdNumber = fdNumber;
	}

	/**
	 * @return 返回 流程标签可见
	 */
	public String getFdLableVisiable() {
		return fdLableVisiable;
	}

	/**
	 * @param fdLableVisiable
	 *            要设置的 流程标签可见
	 */
	public void setFdLableVisiable(String fdLableVisiable) {
		this.fdLableVisiable = fdLableVisiable;
	}

	/**
	 * @return 返回 允许修改反馈人
	 */
	public String getFdFeedbackModify() {
		return fdFeedbackModify;
	}

	/**
	 * @param fdFeedbackModify
	 *            要设置的 允许修改反馈人
	 */
	public void setFdFeedbackModify(String fdFeedbackModify) {
		this.fdFeedbackModify = fdFeedbackModify;
	}

	/**
	 * @return 返回 内容
	 */
	public String getDocContent() {
		return docContent;
	}

	/**
	 * @param docContent
	 *            要设置的 内容
	 */
	public void setDocContent(String docContent) {
		this.docContent = docContent;
	}

	/**
	 * @return 返回 创建人
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}

	/**
	 * @param docCreatorId
	 *            要设置的 创建人
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * @return 返回 创建时间
	 */
	public String getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            要设置的 创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getDocPropertyIds() {
		return docPropertyIds;
	}

	public void setDocPropertyIds(String docPropertyIds) {
		this.docPropertyIds = docPropertyIds;
	}

	public String getDocPropertyNames() {
		return docPropertyNames;
	}

	public void setDocPropertyNames(String docPropertyNames) {
		this.docPropertyNames = docPropertyNames;
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

	public String getFdLabelReaderIds() {
		return fdLabelReaderIds;
	}

	public void setFdLabelReaderIds(String fdLabelReaderIds) {
		this.fdLabelReaderIds = fdLabelReaderIds;
	}

	public String getFdLabelReaderNames() {
		return fdLabelReaderNames;
	}

	public void setFdLabelReaderNames(String fdLabelReaderNames) {
		this.fdLabelReaderNames = fdLabelReaderNames;
	}

	public String getFdPostIds() {
		return fdPostIds;
	}

	public void setFdPostIds(String fdPostIds) {
		this.fdPostIds = fdPostIds;
	}

	public String getFdPostNames() {
		return fdPostNames;
	}

	public void setFdPostNames(String fdPostNames) {
		this.fdPostNames = fdPostNames;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @seecom.landray.kmss.web.action.ActionForm#reset(org.apache.struts.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdNumberPrefix = null;
		fdNumber = null;
		fdLableVisiable = null;
		fdFeedbackModify = null;
		docContent = null;
		docCreatorId = null;
		docCreatorName = null;
		docCreateTime = null;
		fdPostNames = null;
		fdCategoryId = null;
		fdCategoryName = null;
		docPropertyNames = null;
		fdLabelReaderNames = null;
		fdFeedbackNames = null;
		fdKeywordNames = null;
		fdPostIds = null;
		docPropertyIds = null;
		fdLabelReaderIds = null;
		fdFeedBackIds = null;
		fdKeywordIds = null;
		fdOrder = null;
		fdIcon = null;
		fdUseForm = "false";
		fdUseWord = "false";
		fdDisableMobileForm="false";
		fdIsExternal = "fasle";
		fdExternalUrl = null;
		sysWfTemplateForms.clear();
		sysRelationMainForm = new SysRelationMainForm();
		sysNotifyRemindCategoryContextForm = new SysNotifyRemindCategoryContextForm();
		sysAgendaCategoryForm = new SysAgendaCategoryForm();
		sysRuleTemplateForms.clear();
		fdIsAvailable = "true";
		fdIsMobileCreate = null;
		fdIsMobileApprove = null;

		titleRegulation = null;
		titleRegulationName = null;
		sysRemind.clear();
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmReviewTemplate.class;
	}

	/**
	 * 附件实现
	 */
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

	public String getFdCategoryId() {
		return fdCategoryId;
	}

	public void setFdCategoryId(String fdCategoryId) {
		this.fdCategoryId = fdCategoryId;
	}

	public String getFdCategoryName() {
		return fdCategoryName;
	}

	public void setFdCategoryName(String fdCategoryName) {
		this.fdCategoryName = fdCategoryName;
	}

	public String getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	/*
	 * 关联机制
	 */
	private SysRelationMainForm sysRelationMainForm = new SysRelationMainForm();

	@Override
    public SysRelationMainForm getSysRelationMainForm() {
		return sysRelationMainForm;
	}

	public void setSysRelationMainForm(SysRelationMainForm sysRelationMainForm) {
		this.sysRelationMainForm = sysRelationMainForm;
	}

	private AutoHashMap sysWfTemplateForms = new AutoHashMap(
			SysWfTemplateForm.class);

	@Override
    public AutoHashMap getSysWfTemplateForms() {
		return sysWfTemplateForms;
	}

	/*
	 * 表单
	 */
	private AutoHashMap sysFormTemplateForms = new AutoHashMap(
			SysFormTemplateForm.class);

	@Override
    public AutoHashMap getSysFormTemplateForms() {
		return sysFormTemplateForms;
	}

	/*
	 * 文件地址
	 */
	private String fdExtFilePath;

	@Override
    public String getFdExtFilePath() {
		return fdExtFilePath;
	}

	@Override
    public void setFdExtFilePath(String filePath) {
		fdExtFilePath = filePath;
	}

	private String fdUseForm = "false";

	/**
	 * 是否使用表单
	 * 
	 * @return fdUseForm
	 */
	public String getFdUseForm() {
		return fdUseForm;
	}

	/**
	 * 是否使用表单
	 * 
	 * @param fdUseForm
	 *            要设置的 fdUseForm
	 */
	public void setFdUseForm(String fdUseForm) {
		this.fdUseForm = fdUseForm;
	}

	private String fdDisableMobileForm="false";
	
	public String getFdDisableMobileForm() {
		return fdDisableMobileForm;
	}

	public void setFdDisableMobileForm(String fdDisableMobileForm) {
		this.fdDisableMobileForm = fdDisableMobileForm;
	}
	
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

	// =======提醒机制开始(分类) 开始===========
	/**
	 * private SysNotifyRemindCategoryContextForm
	 * sysNotifyRemindCategoryContextForm = new
	 * SysNotifyRemindCategoryContextForm();
	 * 
	 * public SysNotifyRemindCategoryContextForm
	 * getSysNotifyRemindCategoryContextForm() { return
	 * sysNotifyRemindCategoryContextForm; }
	 * 
	 * public void setSysNotifyRemindCategoryContextForm(
	 * SysNotifyRemindCategoryContextForm sysNotifyRemindCategoryContextForm) {
	 * this.sysNotifyRemindCategoryContextForm =
	 * sysNotifyRemindCategoryContextForm; }
	 **/
	// =======提醒机制结束(分类) 结束===========
	// =======日程机制开始(分类) 开始===========
	private SysNotifyRemindCategoryContextForm sysNotifyRemindCategoryContextForm = new SysNotifyRemindCategoryContextForm();

	@Override
    public SysNotifyRemindCategoryContextForm getSysNotifyRemindCategoryContextForm() {
		return sysNotifyRemindCategoryContextForm;
	}

	public void setSysNotifyRemindCategoryContextForm(
			SysNotifyRemindCategoryContextForm sysNotifyRemindCategoryContextForm) {
		this.sysNotifyRemindCategoryContextForm = sysNotifyRemindCategoryContextForm;
	}

	private SysAgendaCategoryForm sysAgendaCategoryForm = new SysAgendaCategoryForm();

	@Override
    public SysAgendaCategoryForm getSysAgendaCategoryForm() {
		return sysAgendaCategoryForm;
	}

	public void setSysAgendaCategoryForm(
			SysAgendaCategoryForm sysAgendaCategoryForm) {
		this.sysAgendaCategoryForm = sysAgendaCategoryForm;
	}

	// =======日程机制(分类) 结束=============

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

	// 编号机制

	private SysNumberMainMappForm sysNumberMainMappForm = new SysNumberMainMappForm();

	@Override
    public SysNumberMainMappForm getSysNumberMainMappForm() {
		return sysNumberMainMappForm;
	}

	@Override
    public void setSysNumberMainMappForm(SysNumberMainMappForm frm) {
		sysNumberMainMappForm = frm;
	}

	// 打印机制
	private SysPrintTemplateForm sysPrintTemplateForm = new SysPrintTemplateForm();

	@Override
	public SysPrintTemplateForm getSysPrintTemplateForm() {
		return sysPrintTemplateForm;
	}

	@Override
	public void setSysPrintTemplateForm(SysPrintTemplateForm form) {
		this.sysPrintTemplateForm = form;
	}
	
	/**
	 * 支持移动端新建
	 */
	private String fdIsMobileCreate;
	
	public String getFdIsMobileCreate() {
		return fdIsMobileCreate;
	}

	public void setFdIsMobileCreate(String fdIsMobileCreate) {
		this.fdIsMobileCreate = fdIsMobileCreate;
	}

	/**
	 * 支持移动端审批
	 */
	private String fdIsMobileApprove;

	public String getFdIsMobileApprove() {
		return fdIsMobileApprove;
	}

	public void setFdIsMobileApprove(String fdIsMobileApprove) {
		this.fdIsMobileApprove = fdIsMobileApprove;
	}
	
	/**
	 * 支持移动端查阅
	 */
	private String fdIsMobileView;

	public String getFdIsMobileView() {
		return fdIsMobileView;
	}

	public void setFdIsMobileView(String fdIsMobileView) {
		this.fdIsMobileView = fdIsMobileView;
	}

	/**
	 * 是否允许复制流程（为空或true时，允许复制流程。为false时禁止复制流程）
	 */
	private String fdIsCopyDoc;

	public String getFdIsCopyDoc() {
		return fdIsCopyDoc;
	}

	public void setFdIsCopyDoc(String fdIsCopyDoc) {
		this.fdIsCopyDoc = fdIsCopyDoc;
	}

	// Add By WUZB
	private String fdUseWord = "false";

	/**
	 * 是否使用Word编辑器
	 * 
	 * @return fdUseWord
	 */
	public String getFdUseWord() {
		return fdUseWord;
	}

	/**
	 * 是否使用Word编辑器
	 * 
	 * @param fdUseWord
	 */
	public void setFdUseWord(String fdUseWord) {
		this.fdUseWord = fdUseWord;
	}

	/******* 知识沉淀 *******/
	private KmsMultidocSubsideForm kmsMultidocSubsideForm = new KmsMultidocSubsideForm();

	@Override
	public KmsMultidocSubsideForm getKmsMultidocSubsideForm() {
		return kmsMultidocSubsideForm;
	}

	@Override
	public void setKmsMultidocSubsideForm(
			KmsMultidocSubsideForm kmsMultidocSubsideForm) {
		this.kmsMultidocSubsideForm = kmsMultidocSubsideForm;
	}
	/******* 知识沉淀 *******/

	/* 规则机制start */
	private AutoHashMap sysRuleTemplateForms = new AutoHashMap(
			SysRuleTemplateForm.class);

	@Override
	public AutoHashMap getSysRuleTemplateForms() {
		return sysRuleTemplateForms;
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

	private String checkTemplates;
	private String checkGroups;

	@Override
	public void setCheckTemplates(String checkTemplates) {
		this.checkTemplates = checkTemplates;
	}

	@Override
	public String getCheckTemplates() {
		return checkTemplates;
	}

	@Override
    public String getCheckGroups() {
		return checkGroups;
	}

	@Override
    public void setCheckGroups(String checkGroups) {
		this.checkGroups = checkGroups;
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

}
