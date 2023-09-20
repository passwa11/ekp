package com.landray.kmss.km.forum.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.forum.model.KmForumPost;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.praise.forms.SysPraiseForm;
import com.landray.kmss.sys.praise.interfaces.ISysPraiseForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2006-Sep-05
 * 
 * @author 吴兵
 */
public class KmForumPostForm extends ExtendForm implements IAttachmentForm,
		ISysPraiseForm

{
	/*
	 * 版块ID
	 */
	private String fdForumId = null;
	private String fdForumName = null;

	/*
	 * 话题ID
	 */
	private String fdTopicId = null;
	private String topicDocSubject = null;
	private String fdImportInfo = null;
	private String fdConcludeInfo = null; //结贴信息
	/*
	 * 标题
	 */
	private String docSubject = null;

	/*
	 * 该话题的发起者
	 */
	private String fdPosterName = null;

	private String fdPosterId = null;
	/*
	 * 该话题的修改者id
	 */
	private String fdAlterorId = null;
	/*
	 * 该话题的修改者name
	 */
	private String fdAlterorName = null;

	protected Integer fdFloor;

	/*
	 * 创建时间
	 */
	private String docCreateTime = null;

	/*
	 * 最后修改时间
	 */
	private String docAlterTime = null;

	/*
	 * 是否匿名
	 */
	private String fdIsAnonymous = null;
	/*
	 * 是否回帖仅作者可见
	 */
	protected String fdIsOnlyView = null;
	/*
	 * 帖子内容
	 */
	private String docContent = null;

	/*
	 * 引用信息
	 */
	private String fdQuoteMsg = null;

	/*
	 * 引用内容
	 */
	private String quoteMsg = null;

	/*
	 * 是否回复通知
	 */
	private String fdIsNotify = null;

	private String fdNotifyType = null;

	/*
	 * 父引用
	 */
	public String fdParentId = null;
	/*
	 * 被赞数
	 */
	protected Integer fdSupportCount;;

	public Integer getFdSupportCount() {
		return fdSupportCount;
	}

	public void setFdSupportCount(Integer fdSupportCount) {
		this.fdSupportCount = fdSupportCount;
	}

	/**
	 * @return 返回 版块ID
	 */
	public String getFdForumId() {
		return fdForumId;
	}

	public String getFdForumName() {
		return fdForumName;
	}

	/**
	 * @param fdTopicId
	 *            要设置的 版块ID
	 */
	public void setFdForumId(String fdForumId) {
		this.fdForumId = fdForumId;
	}

	public void setFdForumName(String fdForumName) {
		this.fdForumName = fdForumName;
	}

	/**
	 * @return 返回 话题ID
	 */
	public String getFdTopicId() {
		return fdTopicId;
	}

	public String getTopicDocSubject() {
		return topicDocSubject;
	}

	public String getFdImportInfo() {
		return fdImportInfo;
	}

	/**
	 * @param fdTopicId
	 *            要设置的 话题ID
	 */
	public void setFdTopicId(String fdTopicId) {
		this.fdTopicId = fdTopicId;
	}

	public void setTopicDocSubject(String topicDocSubject) {
		this.topicDocSubject = topicDocSubject;
	}

	public void setFdImportInfo(String fdImportInfo) {
		this.fdImportInfo = fdImportInfo;
	}
    
	public String getFdConcludeInfo() {
		return fdConcludeInfo;
	}

	public void setFdConcludeInfo(String fdConcludeInfo) {
		this.fdConcludeInfo = fdConcludeInfo;
	}

	/**
	 * @return 返回 标题
	 */
	public String getDocSubject() {
		return docSubject;
	}

	/**
	 * @param docSubject
	 *            要设置的 标题
	 */
	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	/**
	 * @return 返回 是否匿名
	 */
	public String getFdIsAnonymous() {
		return fdIsAnonymous;
	}

	/**
	 * @param fdIsAnonymous
	 *            要设置的 是否匿名
	 */
	public void setFdIsAnonymous(String fdIsAnonymous) {
		this.fdIsAnonymous = fdIsAnonymous;
	}

	public String getFdIsOnlyView() {
		return fdIsOnlyView;
	}

	public void setFdIsOnlyView(String fdIsOnlyView) {
		this.fdIsOnlyView = fdIsOnlyView;
	}

	/**
	 * @return 返回 该话题的发起者
	 */
	public String getFdPosterName() {
		return fdPosterName;
	}

	/**
	 * @param fdPosterName
	 *            要设置的 该话题的发起者
	 */
	public void setFdPosterName(String fdPosterName) {
		this.fdPosterName = fdPosterName;
	}

	public String getFdPosterId() {
		return fdPosterId;
	}

	public void setFdPosterId(String fdPosterId) {
		this.fdPosterId = fdPosterId;
	}

	public String getFdAlterorName() {
		return fdAlterorName;
	}

	public void setFdAlterorName(String fdAlterorName) {
		this.fdAlterorName = fdAlterorName;
	}

	public String getFdAlterorId() {
		return fdAlterorId;
	}

	public void setFdAlterorId(String fdAlterorId) {
		this.fdAlterorId = fdAlterorId;
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
	 * @return 返回 最后修改时间
	 */
	public String getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            要设置的 最后修改时间
	 */
	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * @return 返回 帖子内容
	 */
	public String getDocContent() {
		return docContent;
	}

	/**
	 * @param docContent
	 *            要设置的 帖子内容
	 */
	public void setDocContent(String docContent) {
		this.docContent = docContent;
	}

	/**
	 * @return 返回 引用信息
	 */
	public String getFdQuoteMsg() {
		return fdQuoteMsg;
	}

	/**
	 * @param fdNote
	 *            要设置的 引用信息
	 */
	public void setFdQuoteMsg(String fdQuoteMsg) {
		this.fdQuoteMsg = fdQuoteMsg;
	}

	/**
	 * @return 返回 引用内容
	 */
	public String getQuoteMsg() {
		return quoteMsg;
	}

	/**
	 * @param fdNote
	 *            要设置的 引用内容
	 */
	public void setQuoteMsg(String quoteMsg) {
		this.quoteMsg = quoteMsg;
	}

	public String getFdParentId() {
		return fdParentId;
	}

	public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}

	/**
	 * pda客户端类型
	 */
	protected Integer fdPdaType;

	/**
	 * @return pda客户端类型
	 */
	public Integer getFdPdaType() {
		return fdPdaType;
	}

	/**
	 * @param fdPdaType
	 *            pda客户端类型
	 */
	public void setFdPdaType(Integer fdPdaType) {
		this.fdPdaType = fdPdaType;
	}

	public Integer getFdFloor() {
		return fdFloor;
	}

	public void setFdFloor(Integer fdFloor) {
		this.fdFloor = fdFloor;
	}

	/**
	 * @通知人的ID
	 */
	protected String fdPostNotifierIds;

	public String getFdPostNotifierIds() {
		return fdPostNotifierIds;
	}

	public void setFdPostNotifierIds(String fdPostNotifierIds) {
		this.fdPostNotifierIds = fdPostNotifierIds;
	}

	/**
	 * @通知人的姓名
	 */
	protected String fdPostNotifierNames;

	public String getFdPostNotifierNames() {
		return fdPostNotifierNames;
	}

	public void setFdPostNotifierNames(String fdPostNotifierNames) {
		this.fdPostNotifierNames = fdPostNotifierNames;
	}
	
	/**
	 * 附件实现
	 */
	AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return attachmentForms;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdTopicId = null;
		topicDocSubject = null;
		fdImportInfo = null;
		fdConcludeInfo = null;
		docSubject = null;
		fdFloor = null;
		fdPosterId = null;
		quoteMsg = null;
		fdQuoteMsg = null;
		fdPosterName = UserUtil.getUser().getFdName();
		fdAlterorId = null;
		fdAlterorName = null;
		docCreateTime = null;
		docAlterTime = DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, request.getLocale());
		fdIsAnonymous = "false";
		fdIsOnlyView = "false";
		docContent = null;
		fdIsNotify = "1";
		fdSupportCount = 0;
		fdPostNotifierIds = null;
		fdPostNotifierNames = null;
		super.reset(mapping, request);

	}

	FormToModelPropertyMap formToModelPropertyMap = null;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (formToModelPropertyMap == null) {
			formToModelPropertyMap = new FormToModelPropertyMap();
			formToModelPropertyMap.putAll(super.getToModelPropertyMap());
			// 创建者
			formToModelPropertyMap.put("fdParentId",
					new FormConvertor_IDToModel("fdParent", KmForumPost.class));
			// 修改者
			formToModelPropertyMap.put("fdAlterorId",
					new FormConvertor_IDToModel("fdAlteror",
							SysOrgElement.class));

			formToModelPropertyMap
					.put("fdPosterId", new FormConvertor_IDToModel("fdPoster",
							SysOrgElement.class));
			formToModelPropertyMap.put("fdPostNotifierIds",
					new FormConvertor_IDsToModelList("fdPostNotifier",
							SysOrgElement.class));
		}
		return formToModelPropertyMap;
	}

	@Override
    public Class getModelClass() {
		return KmForumPost.class;
	}

	public String getFdIsNotify() {
		return fdIsNotify;
	}

	public void setFdIsNotify(String fdIsNotify) {
		this.fdIsNotify = fdIsNotify;
	}

	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}

	/*
	 * 点赞
	 */
	protected SysPraiseForm sysPraiseForm = new SysPraiseForm();

	@Override
    public SysPraiseForm getPraiseForm() {
		return sysPraiseForm;
	}

	public void setPraiseForm(SysPraiseForm sysPraiseForm) {
		this.sysPraiseForm = sysPraiseForm;
	}

}
