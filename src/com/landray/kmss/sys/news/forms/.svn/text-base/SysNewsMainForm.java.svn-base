package com.landray.kmss.sys.news.forms;

import javax.servlet.http.HttpServletRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_NamesToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.plugins.interfaces.IKmCkoModifyLogForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.evaluation.forms.EvaluationForm;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationForm;
import com.landray.kmss.sys.news.constant.SysNewsConstant;
import com.landray.kmss.sys.news.model.SysNewsMain;
import com.landray.kmss.sys.news.model.SysNewsMainKeyword;
import com.landray.kmss.sys.news.model.SysNewsTemplate;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.readlog.forms.ReadLogForm;
import com.landray.kmss.sys.readlog.interfaces.ISysReadLogForm;
import com.landray.kmss.sys.recycle.forms.ISysRecycleModelForm;
import com.landray.kmss.sys.relation.forms.SysRelationMainForm;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.sys.tag.forms.SysTagMainForm;
import com.landray.kmss.sys.tag.interfaces.ISysTagMainForm;
import com.landray.kmss.sys.workflow.interfaces.ISysWfMainForm;
import com.landray.kmss.sys.workflow.interfaces.SysWfBusinessForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 创建日期 2007-Sep-17
 * 
 * @author 舒斌
 */
public class SysNewsMainForm extends ExtendAuthForm implements IAttachmentForm,
		ISysRelationMainForm, ISysEvaluationForm, ISysReadLogForm,
		ISysWfMainForm, IKmCkoModifyLogForm, ISysTagMainForm,
		ISysRecycleModelForm {
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	/*
	 * 是否允许点评
	 */
	private String fdCanComment;

	public String getFdCanComment() {
		return fdCanComment;
	}

	public void setFdCanComment(String fdCanComment) {
		this.fdCanComment = fdCanComment;
	}

	/*
	 * 过期时间
	 */
	private String docOverdueTime = null;


	public String getDocOverdueTime() {
		return docOverdueTime;
	}

	public void setDocOverdueTime(String docOverdueTime) {
		this.docOverdueTime = docOverdueTime;
	}

	private String fdStyle = null;
	/*
	 * 域模型名称
	 */
	protected java.lang.String fdModelName;

	/*
	 * 域模型ID
	 */
	protected java.lang.String fdModelId;

	/*
	 * 域关键字
	 */
	protected java.lang.String fdKey;
	/*
	 * 模板ID
	 */
	private String fdTemplateId = null;

	protected String fdTemplateName = null;

	/*
	 * 主题
	 */
	private String docSubject = null;
	/*
	 * 新闻简要
	 */
	private String fdDescription = null;
	/*
	 * 新闻来源
	 */
	private String fdNewsSource = null;

	/*
	 * 创建时间
	 */
	private String docCreateTime = null;

	/*
	 * 修改时间
	 */
	private String docAlterTime = null;

	/*
	 * 发布时间
	 */
	private String docPublishTime = null;

	/*
	 * 文档内容
	 */
	private String docContent = null;

	/*
	 * 新闻重要度
	 */
	private String fdImportance = null;

	/*
	 * 标题图片
	 */
	private String fdMainPicture = null;

	/*
	 * 内容简介
	 */
	private String fdSummary = null;

	/*
	 * 是否链接新闻
	 */
	private String fdIsLink = null;

	/*
	 * 新闻链接
	 */
	private String fdLinkUrl = null;

	/*
	 * 滚动新闻标记
	 */
	private String fdIsRolls = null;

	/*
	 * 创建者
	 */
	protected String fdCreatorId = null;

	protected String fdCreatorName = null;

	/*
	 * 修改者
	 */
	protected String fdModifyId = null;

	protected String fdModifyName = null;

	/*
	 * 作者
	 */
	protected String fdAuthorId = null;

	protected String fdAuthorName = null;

	/*
	 * 所属部门
	 */
	protected String fdDepartmentId = null;

	protected String fdDepartmentName = null;

	/*
	 * 文档关键字
	 */
	private String docKeywordIds = null;

	private String docKeywordNames = null;

	private String docReadCount = null;

	protected String fdTopTime = null;

	protected String fdTopEndTime = null;
	
	protected Boolean fdIsTop=null;
	
	/*
	 * 通知方式
	 */
	private String fdNotifyType = null;
	
	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}

	/*
	 * 文档内容的编辑方式
	 */
	private String fdContentType;

	public String getFdContentType() {
		return fdContentType;
	}

	public void setFdContentType(String fdContentType) {
		this.fdContentType = fdContentType;
	}

	/*
	 * 文档内容的HTML
	 */
	private String fdHtmlContent;

	public String getFdHtmlContent() {
		return fdHtmlContent;
	}

	public void setFdHtmlContent(String fdHtmlContent) {
		this.fdHtmlContent = fdHtmlContent;
	}

	/**
	 * @return 返回 模板ID
	 */
	public String getFdTemplateId() {
		return fdTemplateId;
	}

	/**
	 * @param fdTemplateId
	 *            要设置的 模板ID
	 */
	public void setFdTemplateId(String fdTemplateId) {
		this.fdTemplateId = fdTemplateId;
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

	public String getFdDescription() {
		return fdDescription;
	}

	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
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
	 * @return 返回 修改时间
	 */
	public String getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            要设置的 修改时间
	 */
	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * @return 返回 发布时间
	 */
	public String getDocPublishTime() {
		return docPublishTime;
	}

	/**
	 * @param docPublishTime
	 *            要设置的 发布时间
	 */
	public void setDocPublishTime(String docPublishTime) {
		this.docPublishTime = docPublishTime;
	}

	/**
	 * @return 返回 文档内容
	 */
	public String getDocContent() {
		return docContent;
	}

	/**
	 * @param docContent
	 *            要设置的 文档内容
	 */
	public void setDocContent(String docContent) {
		this.docContent = docContent;
	}

	/**
	 * @return 返回 新闻重要度
	 */
	public String getFdImportance() {
		return fdImportance;
	}

	/**
	 * @param fdImportance
	 *            要设置的 新闻重要度
	 */
	public void setFdImportance(String fdImportance) {
		this.fdImportance = fdImportance;
	}

	/**
	 * @return 返回 标题图片
	 */
	public String getFdMainPicture() {
		return fdMainPicture;
	}

	/**
	 * @param fdMainPicture
	 *            要设置的 标题图片
	 */
	public void setFdMainPicture(String fdMainPicture) {
		this.fdMainPicture = fdMainPicture;
	}

	/**
	 * @return 返回 内容简介
	 */
	public String getFdSummary() {
		return fdSummary;
	}

	/**
	 * @param fdSummary
	 *            要设置的 内容简介
	 */
	public void setFdSummary(String fdSummary) {
		this.fdSummary = fdSummary;
	}

	/**
	 * @return 返回 是否链接新闻
	 */
	public String getFdIsLink() {
		return fdIsLink;
	}

	/**
	 * @param fdIsLink
	 *            要设置的 是否链接新闻
	 */
	public void setFdIsLink(String fdIsLink) {
		this.fdIsLink = fdIsLink;
	}

	/**
	 * @return 返回 新闻链接
	 */
	public String getFdLinkUrl() {
		return fdLinkUrl;
	}

	/**
	 * @param fdLinkUrl
	 *            要设置的 新闻链接
	 */
	public void setFdLinkUrl(String fdLinkUrl) {
		this.fdLinkUrl = fdLinkUrl;
	}

	/**
	 * @return 返回 滚动新闻标记
	 */
	public String getFdIsRolls() {
		return fdIsRolls;
	}

	/**
	 * @param fdIsRolls
	 *            要设置的 滚动新闻标记
	 */
	public void setFdIsRolls(String fdIsRolls) {
		this.fdIsRolls = fdIsRolls;
	}

	/**
	 * @param docStatus
	 *            要设置的 文档状态
	 */
	@Override
    public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}

	@Override
    public String getAuthEditorIds() {
		return authEditorIds;
	}

	@Override
    public void setAuthEditorIds(String authEditorIds) {
		this.authEditorIds = authEditorIds;
	}

	@Override
    public String getAuthEditorNames() {
		return authEditorNames;
	}

	@Override
    public void setAuthEditorNames(String authEditorNames) {
		this.authEditorNames = authEditorNames;
	}

	@Override
    public String getAuthReaderIds() {
		return authReaderIds;
	}

	@Override
    public void setAuthReaderIds(String authReaderIds) {
		this.authReaderIds = authReaderIds;
	}

	@Override
    public String getAuthReaderNames() {
		return authReaderNames;
	}

	@Override
    public void setAuthReaderNames(String authReaderNames) {
		this.authReaderNames = authReaderNames;
	}

	public String getDocKeywordIds() {
		return docKeywordIds;
	}

	public void setDocKeywordIds(String docKeywordIds) {
		this.docKeywordIds = docKeywordIds;
	}

	public String getDocKeywordNames() {
		return docKeywordNames;
	}

	public void setDocKeywordNames(String docKeywordNames) {
		this.docKeywordNames = docKeywordNames;
	}

	public String getFdAuthorId() {
		return fdAuthorId;
	}

	public void setFdAuthorId(String fdAuthorId) {
		this.fdAuthorId = fdAuthorId;
	}

	public String getFdAuthorName() {
		return fdAuthorName;
	}

	public void setFdAuthorName(String fdAuthorName) {
		this.fdAuthorName = fdAuthorName;
	}

	public String getFdCreatorId() {
		return fdCreatorId;
	}

	public void setFdCreatorId(String fdCreatorId) {
		this.fdCreatorId = fdCreatorId;
	}

	public String getFdCreatorName() {
		return fdCreatorName;
	}

	public void setFdCreatorName(String fdCreatorName) {
		this.fdCreatorName = fdCreatorName;
	}

	public String getFdDepartmentId() {
		return fdDepartmentId;
	}

	public void setFdDepartmentId(String fdDepartmentId) {
		this.fdDepartmentId = fdDepartmentId;
	}

	public String getFdDepartmentName() {
		return fdDepartmentName;
	}

	public void setFdDepartmentName(String fdDepartmentName) {
		this.fdDepartmentName = fdDepartmentName;
	}

	public String getFdModifyId() {
		return fdModifyId;
	}

	public void setFdModifyId(String fdModifyId) {
		this.fdModifyId = fdModifyId;
	}

	public String getFdModifyName() {
		return fdModifyName;
	}

	public void setFdModifyName(String fdModifyName) {
		this.fdModifyName = fdModifyName;
	}

	public String getFdTemplateName() {
		return fdTemplateName;
	}

	public void setFdTemplateName(String fdTemplateName) {
		this.fdTemplateName = fdTemplateName;
	}
	
	@Override
    public String getAuthReaderNoteFlag() {
		return "1";
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @seecom.landray.kmss.web.action.ActionForm#reset(org.apache.struts.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdTemplateId = null;
		docSubject = null;
		fdDescription = null;
		docCreateTime = null;
		docAlterTime = null;
		docPublishTime = null;
		docContent = null;
		fdImportance = null;
		fdMainPicture = null;
		fdSummary = null;
		fdIsLink = null;
		fdLinkUrl = null;
		fdIsRolls = null;
		docStatus = "10";
		fdCreatorId = null;
		fdCreatorName = null;
		fdModifyId = null;
		fdModifyName = null;
		fdAuthorId = null;
		fdAuthorName = null;
		fdDepartmentId = null;
		fdDepartmentName = null;
		docKeywordIds = null;
		docKeywordNames = null;
		docReadCount = null;
		fdStyle = null;
		fdTopTime = null;
		fdTopEndTime = null;
		fdIsTop=null;
		fdNotifyType = null;
		sysWfBusinessForm = new SysWfBusinessForm();
		sysRelationMainForm = new SysRelationMainForm();
		readLogForm = new ReadLogForm();
		fdWriter = null;
		fdCanComment = "true";
		docOverdueTime = null;
		fdIsHideSubject = Boolean.FALSE;
		fdContentType = SysNewsConstant.FDCONTENTTYPE_RTF;
		fdHtmlContent = null;
		super.reset(mapping, request);
	}

	@Override
    @JsonIgnore
	public Class getModelClass() {
		return SysNewsMain.class;
	}

	FormToModelPropertyMap formToModelPropertyMap = null;

	@Override
    @JsonIgnore
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (formToModelPropertyMap == null) {
			formToModelPropertyMap = new FormToModelPropertyMap();
			formToModelPropertyMap.putAll(super.getToModelPropertyMap());
			// 作者
			formToModelPropertyMap
					.put("fdAuthorId", new FormConvertor_IDToModel("fdAuthor",
							SysOrgElement.class));
			// 所属部门
			formToModelPropertyMap.put("fdDepartmentId",
					new FormConvertor_IDToModel("fdDepartment",
							SysOrgElement.class));
			// 新闻模板
			formToModelPropertyMap.put("fdTemplateId",
					new FormConvertor_IDToModel("fdTemplate",
							SysNewsTemplate.class));
			// 文档关键字
			formToModelPropertyMap.put("docKeywordNames",
					new FormConvertor_NamesToModelList("docKeyword",
							"sysNewsMain", SysNewsMain.class, "docKeyword",
							SysNewsMainKeyword.class).setSplitStr(" "));
			// 创建者
			formToModelPropertyMap.put("fdCreatorId",
					new FormConvertor_IDToModel("docCreator",
							SysOrgPerson.class));
		}
		return formToModelPropertyMap;
	}

	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

	
	public AutoHashMap getAutoHashMap() {
		return autoHashMap;
	}

	public void setAutoHashMap(AutoHashMap autoHashMap) {
		this.autoHashMap = autoHashMap;
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
	 * 点评机制
	 */
	protected EvaluationForm evaluationForm = new EvaluationForm();

	@Override
    public EvaluationForm getEvaluationForm() {
		return evaluationForm;
	}

	public void setEvaluationForm(EvaluationForm evaluationForm) {
		this.evaluationForm = evaluationForm;
	}

	/*
	 * 阅读机制
	 */
	protected ReadLogForm readLogForm = new ReadLogForm();

	@Override
    public ReadLogForm getReadLogForm() {
		return readLogForm;
	}

	public void setReadLogForm(ReadLogForm readLogForm) {
		this.readLogForm = readLogForm;
	}

	// ********** 以下的代码为流程需要的代码，请直接拷贝 **********
	private SysWfBusinessForm sysWfBusinessForm = new SysWfBusinessForm();

	@Override
    public SysWfBusinessForm getSysWfBusinessForm() {
		return sysWfBusinessForm;
	}

	// ********** 以上的代码为流程需要的代码，请直接拷贝 **********

	public String getDocReadCount() {
		return docReadCount;
	}

	public void setDocReadCount(String docReadCount) {
		this.docReadCount = docReadCount;
	}

	public String getFdStyle() {
		if (fdStyle == null) {
			return "default";
		}
		return fdStyle;
	}

	public void setFdStyle(String fdStyle) {
		this.fdStyle = fdStyle;
	}

	public String getFdTopEndTime() {
		return fdTopEndTime;
	}

	public void setFdTopEndTime(String fdTopEndTime) {
		this.fdTopEndTime = fdTopEndTime;
	}

	public String getFdTopTime() {
		return fdTopTime;
	}

	public void setFdTopTime(String fdTopTime) {
		this.fdTopTime = fdTopTime;
	}
	
	public Boolean getFdIsTop() {
		return fdIsTop;
	}

	public void setFdIsTop(Boolean fdIsTop) {
		this.fdIsTop = fdIsTop;
	}


	public String getFdNewsSource() {
		return fdNewsSource;
	}

	public void setFdNewsSource(String fdNewsSource) {
		this.fdNewsSource = fdNewsSource;
	}

	/*
	 * 作者，IT架构外人员，直接手工录入
	 */
	private String fdWriter;

	/**
	 * @return 作者，IT架构外人员，直接手工录入
	 */
	public String getFdWriter() {
		return fdWriter;
	}

	/**
	 * @param fdWriter
	 *            作者，IT架构外人员，直接手工录入
	 */
	public void setFdWriter(String fdWriter) {
		this.fdWriter = fdWriter;
	}
	private boolean fdIsWriter = false;

	public boolean isFdIsWriter() {
		if (null == fdAuthorId) {
			fdIsWriter = true;
		} else {
			fdIsWriter = false;
		}
		return fdIsWriter;
	}

	public void setFdIsWriter(boolean fdIsWriter) {
		this.fdIsWriter = fdIsWriter;		
	}

	/*
	 * 是否隐藏标题
	 */
	private Boolean fdIsHideSubject = Boolean.FALSE;

	public Boolean getFdIsHideSubject() {
		return fdIsHideSubject;
	}

	public void setFdIsHideSubject(Boolean fdIsHideSubject) {
		this.fdIsHideSubject = fdIsHideSubject;
	}

	public java.lang.String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(java.lang.String fdModelName) {
		this.fdModelName = fdModelName;
	}

	public java.lang.String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(java.lang.String fdModelId) {
		this.fdModelId = fdModelId;
	}

	public java.lang.String getFdKey() {
		return fdKey;
	}

	public void setFdKey(java.lang.String fdKey) {
		this.fdKey = fdKey;
	}

	// ==============标签机制 ==================
	SysTagMainForm sysTagMainForm = new SysTagMainForm();

	@Override
    public SysTagMainForm getSysTagMainForm() {

		return sysTagMainForm;
	}

	private String docCreatorDeptId = null;

	public String getDocCreatorDeptId() {
		return docCreatorDeptId;
	}

	public void setDocCreatorDeptId(String docCreatorDeptId) {
		this.docCreatorDeptId = docCreatorDeptId;
	}
	// ==============标签机制 结束====================

	private Integer docDeleteFlag;

	@Override
    public Integer getDocDeleteFlag() {
		return docDeleteFlag;
	}

	@Override
    public void setDocDeleteFlag(Integer docDeleteFlag) {
		this.docDeleteFlag = docDeleteFlag;
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
