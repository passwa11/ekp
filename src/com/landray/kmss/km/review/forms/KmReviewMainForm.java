package com.landray.kmss.km.review.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormConvertor_NamesToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.component.locker.forms.ComponentLockerVersionForm;
import com.landray.kmss.component.locker.interfaces.IComponentLockerVersionForm;
import com.landray.kmss.km.review.model.KmReviewDocKeyword;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.model.KmReviewTemplate;
import com.landray.kmss.sys.agenda.forms.SysAgendaMainForm;
import com.landray.kmss.sys.agenda.interfaces.ISysAgendaMainForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.bookmark.interfaces.ISysBookmarkForm;
import com.landray.kmss.sys.category.model.SysCategoryProperty;
import com.landray.kmss.sys.circulation.forms.CirculationForm;
import com.landray.kmss.sys.circulation.interfaces.ISysCirculationForm;
import com.landray.kmss.sys.metadata.forms.ExtendDataFormInfo;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataForm;
import com.landray.kmss.sys.notify.interfaces.SysNotifyRemindMainContextForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.print.forms.SysPrintLogForm;
import com.landray.kmss.sys.print.interfaces.ISysPrintLogForm;
import com.landray.kmss.sys.readlog.forms.ReadLogForm;
import com.landray.kmss.sys.readlog.interfaces.ISysReadLogForm;
import com.landray.kmss.sys.recycle.forms.ISysRecycleModelForm;
import com.landray.kmss.sys.relation.forms.SysRelationMainForm;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.sys.workflow.interfaces.ISysWfMainForm;
import com.landray.kmss.sys.workflow.interfaces.SysWfBusinessForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 创建日期 2007-Aug-30
 * 
 * @author 舒斌
 */
public class KmReviewMainForm extends ExtendAuthForm implements
		IAttachmentForm, ISysRelationMainForm, ISysReadLogForm, ISysWfMainForm,
		ISysBookmarkForm, ISysCirculationForm, IExtendDataForm, ISysPrintLogForm
		// ,ISysNotifyRemindMainForm
		, ISysAgendaMainForm, ISysRecycleModelForm,
		IComponentLockerVersionForm {
	// 编辑主题
	private Boolean editDocSubject = null;

	public Boolean getEditDocSubject() {
		return editDocSubject;
	}

	public void setEditDocSubject(Boolean editDocSubject) {
		this.editDocSubject = editDocSubject;
	}
	private String fdWorkId = null;
	private String fdPhaseId = null;

	private Boolean fdIsFiling = Boolean.FALSE;
	
	/**
	 * @return 是否归档 默认为未归档 false
	 */
	public Boolean getFdIsFiling() {
		return fdIsFiling;
	}

	/**
	 * @param 设置是否归档
	 */
	public void setFdIsFiling(Boolean fdIsFiling) {
		this.fdIsFiling = fdIsFiling;
	}

	public String getFdPhaseId() {
		return fdPhaseId;
	}

	public void setFdPhaseId(String fdPhaseId) {
		this.fdPhaseId = fdPhaseId;
	}

	private String fdModelId = null;
	private String fdModelName = null;

	public String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	public String getFdWorkId() {
		return fdWorkId;
	}

	public void setFdWorkId(String fdWorkId) {
		this.fdWorkId = fdWorkId;
	}

	/*
	 * 模板
	 */
	private String fdTemplateName = null;

	private String fdTemplateId = null;
	private String titleRegulation = null;

	public String getTitleRegulation() {
		return titleRegulation;
	}

	public void setTitleRegulation(String titleRegulation) {
		this.titleRegulation = titleRegulation;
	}

	private String fdDocCategoryId = null;

	public String getFdDocCategoryId() {
		return fdDocCategoryId;
	}

	public void setFdDocCategoryId(String fdDocCategoryId) {
		this.fdDocCategoryId = fdDocCategoryId;
	}

	/*
	 * 可以允许修改反馈人
	 */
	private String fdFeedbackModify = null;

	/*
	 * 文档在发布状态下是否指定过反馈人标记
	 */
	private String fdFeedbackExecuted = null;

	/*
	 * 主题
	 */
	private String docSubject = null;
	
	/*
	 * 是否可传阅,1为可以传阅，0为不可传阅
	 */
	protected Boolean fdCanCircularize = true;



	public Boolean getFdCanCircularize() {
		return fdCanCircularize;
	}

	public void setFdCanCircularize(Boolean fdCanCircularize) {
		this.fdCanCircularize = fdCanCircularize;
	}

	/*
	 * 申请单编号
	 */
	private String fdNumber = null;

	/*
	 * 申请人ID
	 */
	private String docCreatorId = null;

	private String docCreatorName = null;

	/*
	 * 申请人所在部门
	 */
	private String fdDepartmentName = null;

	/*
	 * 创建时间
	 */
	private String docCreateTime = null;

	/*
	 * 内容
	 */
	private String docContent = "10";

	/*
	 * 所有人可阅读标记
	 */
	private String authReaderFlag = null;

	/*
	 * 相关岗位
	 */
	private String fdPostNames = null;

	private String fdPostIds = null;

	/*
	 * 流程标签可阅读者
	 */
	private String fdLableVisiable = null;

	private String fdLableReaderNames = null;

	private String fdLableReaderIds = null;

	/*
	 * 可反馈者
	 */
	private String fdFeedbackNames = null;

	private String fdFeedbackIds = null;

	/*
	 * 相关属性
	 */
	private String docPropertyNames = null;

	private String docPropertyIds = null;

	/*
	 * 关键字
	 */
	private String fdKeywordNames = null;

	private String fdKeywordIds = null;

	/*
	 * 附件实现
	 */
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	/*
	 * 阅读次数
	 */
	private String docReadCount = null;

	public String getDocReadCount() {
		return docReadCount;
	}

	public void setDocReadCount(String docReadCount) {
		this.docReadCount = docReadCount;
	}

	/**
	 * @return 返回 主题
	 */
	public String getDocSubject() {
		return docSubject;
	}

	/**
	 * @param docSubject
	 *            要设置的 主题
	 */
	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	/**
	 * @return 返回 申请单编号
	 */
	public String getFdNumber() {
		return fdNumber;
	}

	/**
	 * @param fdNumber
	 *            要设置的 申请单编号
	 */
	public void setFdNumber(String fdNumber) {
		this.fdNumber = fdNumber;
	}

	/**
	 * @return 返回 申请人ID
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}

	public String getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	/**
	 * @param docCreatorId
	 *            要设置的 申请人ID
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
	 * @return 返回 所有人可阅读标记
	 */
	public String getAuthReaderFlag() {
		return authReaderFlag;
	}

	/**
	 * @param authReaderFlag
	 *            要设置的 所有人可阅读标记
	 */
	public void setAuthReaderFlag(String authReaderFlag) {
		this.authReaderFlag = authReaderFlag;
	}

	private static FormToModelPropertyMap formToModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (formToModelPropertyMap == null) {
			formToModelPropertyMap = new FormToModelPropertyMap();
			formToModelPropertyMap.putAll(super.getToModelPropertyMap());
			// 模板
			formToModelPropertyMap.put("fdTemplateId",
					new FormConvertor_IDToModel("fdTemplate",
							KmReviewTemplate.class));

			// 相关岗位
			formToModelPropertyMap.put("fdPostIds",
					new FormConvertor_IDsToModelList("fdPosts",
							SysOrgElement.class));
			// 流程标签可阅读者
			formToModelPropertyMap.put("fdLableReaderIds",
					new FormConvertor_IDsToModelList("fdLableReaders",
							SysOrgElement.class));
			// 可反馈者
			formToModelPropertyMap.put("fdFeedbackIds",
					new FormConvertor_IDsToModelList("fdFeedback",
							SysOrgElement.class));
			// 相关属性
			formToModelPropertyMap.put("docPropertyIds",
					new FormConvertor_IDsToModelList("docProperties",
							SysCategoryProperty.class));
			// 关键字
			formToModelPropertyMap.put("fdKeywordNames",
					new FormConvertor_NamesToModelList("docKeyword",
							"kmReviewMain", KmReviewMain.class, "docKeyword",
							KmReviewDocKeyword.class).setSplitStr(" "));
		}
		return formToModelPropertyMap;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @seecom.landray.kmss.web.action.ActionForm#reset(org.apache.struts.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docSubject = null;
		fdCanCircularize = null;
		fdNumber = null;
		docCreatorId = null;
		docCreateTime = null;
		docContent = null;
		docStatus = "10";
		authReaderFlag = null;
		fdFeedbackModify = null;
		fdLableVisiable = null;
		fdPostNames = null;
		fdPostIds = null;
		fdLableReaderNames = null;
		fdLableReaderIds = null;
		fdFeedbackNames = null;
		fdFeedbackIds = null;
		docPropertyNames = null;
		docPropertyIds = null;
		fdKeywordNames = null;
		fdKeywordIds = null;
		fdTemplateId = null;
		fdTemplateName = null;
		titleRegulation = null;
		fdDepartmentName = null;
		docReadCount = null;
		fdIsMobileApprove = null;
		fdIsMobileCreate = null;
		fdReviewFeedbackInfoCount = null;
		fdIsFiling = Boolean.FALSE;
		fdUseWord = null;
		fdIsImportXFormData = null;
		sysWfBusinessForm = new SysWfBusinessForm();
		sysRelationMainForm = new SysRelationMainForm();
		sysAgendaMainForm = new SysAgendaMainForm();
		sysNotifyRemindMainContextForm = new SysNotifyRemindMainContextForm();
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmReviewMain.class;
	}

	@Override
    public String getAuthReaderNoteFlag() {
		return "2";
	}

	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
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

	public String getFdFeedbackIds() {
		return fdFeedbackIds;
	}

	public void setFdFeedbackIds(String fdFeedbackIds) {
		this.fdFeedbackIds = fdFeedbackIds;
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

	public String getFdLableReaderIds() {
		return fdLableReaderIds;
	}

	public void setFdLableReaderIds(String fdLableReaderIds) {
		this.fdLableReaderIds = fdLableReaderIds;
	}

	public String getFdLableReaderNames() {
		return fdLableReaderNames;
	}

	public void setFdLableReaderNames(String fdLableReaderNames) {
		this.fdLableReaderNames = fdLableReaderNames;
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

	public String getFdTemplateId() {
		return fdTemplateId;
	}

	public void setFdTemplateId(String fdTemplateId) {
		this.fdTemplateId = fdTemplateId;
	}

	public String getFdTemplateName() {
		return fdTemplateName;
	}

	public void setFdTemplateName(String fdTemplateName) {
		this.fdTemplateName = fdTemplateName;
	}

	public String getFdDepartmentName() {
		return fdDepartmentName;
	}

	public void setFdDepartmentName(String fdDepartmentName) {
		this.fdDepartmentName = fdDepartmentName;
	}

	public String getFdFeedbackModify() {
		return fdFeedbackModify;
	}

	public void setFdFeedbackModify(String fdFeedbackModify) {
		this.fdFeedbackModify = fdFeedbackModify;

	}

	public String getFdLableVisiable() {
		return fdLableVisiable;
	}

	public void setFdLableVisiable(String fdLableVisiable) {
		this.fdLableVisiable = fdLableVisiable;
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

	/*
	 * 阅读机制
	 */
	protected ReadLogForm readLogForm = new ReadLogForm();

	@Override
    public ReadLogForm getReadLogForm() {
		return readLogForm;
	}

	// public void setReadLogForm(ReadLogForm readLogForm) {
	// this.readLogForm = readLogForm;
	// }

	public String getFdFeedbackExecuted() {
		return fdFeedbackExecuted;
	}

	public void setFdFeedbackExecuted(String fdFeedbackExecuted) {
		this.fdFeedbackExecuted = fdFeedbackExecuted;
	}

	/*
	 * 流程机制
	 */
	private SysWfBusinessForm sysWfBusinessForm = new SysWfBusinessForm();

	@Override
    public SysWfBusinessForm getSysWfBusinessForm() {
		return sysWfBusinessForm;
	}

	/*
	 * 通知机制
	 */
	private String fdNotifyType = null;

	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}

	// *********************收藏机制(开始)*********************************//
	private String docMarkCount;

	@Override
    public String getDocMarkCount() {
		return docMarkCount;
	}

	@Override
    public void setDocMarkCount(String count) {
		this.docMarkCount = count;
	}

	// *********************收藏机制(结束)***************************//

	// 将原有传阅改为传阅机制
	// ===============传阅机制开始============================
	public CirculationForm circulationForm = new CirculationForm();

	@Override
    public CirculationForm getCirculationForm() {
		return circulationForm;
	}

	// ===============传阅机制结束============================

	/*
	 * 表单扩展数据
	 */
	private ExtendDataFormInfo extendDataFormInfo = new ExtendDataFormInfo();

	@Override
    public ExtendDataFormInfo getExtendDataFormInfo() {
		return extendDataFormInfo;
	}

	private String fdUseForm;

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

	private String fdDisableMobileForm;
	
	public String getFdDisableMobileForm() {
		return fdDisableMobileForm;
	}

	public void setFdDisableMobileForm(String fdDisableMobileForm) {
		this.fdDisableMobileForm = fdDisableMobileForm;
	}

	private String docPublishTime;

	/**
	 * 发布时间
	 * 
	 * @return
	 */
	public String getDocPublishTime() {
		return docPublishTime;
	}

	/**
	 * 发布时间
	 * 
	 * @param docPublishTime
	 */
	public void setDocPublishTime(String docPublishTime) {
		this.docPublishTime = docPublishTime;
	}

	// ==============提醒机制(主文档) 开始====================
	/**
	 * private SysNotifyRemindMainContextForm sysNotifyRemindMainContextForm =
	 * new SysNotifyRemindMainContextForm();
	 * 
	 * public SysNotifyRemindMainContextForm getSysNotifyRemindMainContextForm()
	 * { return sysNotifyRemindMainContextForm; }
	 * 
	 * public void
	 * setSysNotifyRemindMainContextForm(SysNotifyRemindMainContextForm
	 * sysNotifyRemindMainContextForm) { this.sysNotifyRemindMainContextForm =
	 * sysNotifyRemindMainContextForm; }
	 **/
	// ==============提醒机制(主文档) 结束====================
	// ==============日程机制开始(主文档) 开始=================
	private SysNotifyRemindMainContextForm sysNotifyRemindMainContextForm = new SysNotifyRemindMainContextForm();

	@Override
    public SysNotifyRemindMainContextForm getSysNotifyRemindMainContextForm() {
		return sysNotifyRemindMainContextForm;
	}

	public void setSysNotifyRemindMainContextForm(
			SysNotifyRemindMainContextForm sysNotifyRemindMainContextForm) {
		this.sysNotifyRemindMainContextForm = sysNotifyRemindMainContextForm;
	}

	private SysAgendaMainForm sysAgendaMainForm = new SysAgendaMainForm();

	@Override
    public SysAgendaMainForm getSysAgendaMainForm() {
		return sysAgendaMainForm;
	}

	public void setSysAgendaMainForm(SysAgendaMainForm sysAgendaMainForm) {
		this.sysAgendaMainForm = sysAgendaMainForm;
	}

	// ==============日程机制结束(主文档) 结束=================

	/**
	 * 日程机制从当前业务模块同步数据到时间管理模块同步时机
	 */
	private String syncDataToCalendarTime;

	public String getSyncDataToCalendarTime() {
		return syncDataToCalendarTime;
	}

	public void setSyncDataToCalendarTime(String syncDataToCalendarTime) {
		this.syncDataToCalendarTime = syncDataToCalendarTime;
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
	
	/**
	 * 是否允许导入表单数据
	 */
	private String fdIsImportXFormData;
	
	public String getFdIsImportXFormData() {
		return fdIsImportXFormData;
	}

	public void setFdIsImportXFormData(String fdIsImportXFormData) {
		this.fdIsImportXFormData = fdIsImportXFormData;
	}

	// 打印机制日志
	private SysPrintLogForm sysPrintLogForm = new SysPrintLogForm();

	@Override
	public SysPrintLogForm getSysPrintLogForm() {
		return sysPrintLogForm;
	}

	@Override
	public void setSysPrintLogForm(SysPrintLogForm sysPrintLogForm) {
		this.sysPrintLogForm = sysPrintLogForm;
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

	private Integer docDeleteFlag;

	@Override
    public Integer getDocDeleteFlag() {
		return docDeleteFlag;
	}

	@Override
    public void setDocDeleteFlag(Integer docDeleteFlag) {
		this.docDeleteFlag = docDeleteFlag;
	}

	private ComponentLockerVersionForm componentLockerVersionForm = new ComponentLockerVersionForm();

	@Override
    public ComponentLockerVersionForm getComponentLockerVersionForm() {
		return componentLockerVersionForm;
	}

	@Override
    public void setComponentLockerVersionForm(
			ComponentLockerVersionForm componentLockerVersionForm) {
		this.componentLockerVersionForm = componentLockerVersionForm;
	}

	/*
	 * 反馈数
	 */
	private String fdReviewFeedbackInfoCount = null;

	public String getFdReviewFeedbackInfoCount() {
		return fdReviewFeedbackInfoCount;
	}

	public void setFdReviewFeedbackInfoCount(String fdReviewFeedbackInfoCount) {
		this.fdReviewFeedbackInfoCount = fdReviewFeedbackInfoCount;
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
	
}
