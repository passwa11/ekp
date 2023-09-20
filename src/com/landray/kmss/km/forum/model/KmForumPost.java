package com.landray.kmss.km.forum.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.forum.forms.KmForumPostForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.praise.interfaces.ISysPraiseMain;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
 * 创建日期 2006-Sep-05
 * 
 * @author 吴兵 帖子
 */
public class KmForumPost extends BaseModel implements IAttachment,
		InterceptFieldEnabled, ISysNotifyModel, ISysPraiseMain {

	/*
	 * 标题
	 */
	protected String docSubject;

	/*
	 * 概要
	 */
	protected String docSummary;

	/*
	 * 创建时间
	 */
	protected Date docCreateTime = new Date();

	/*
	 * 最后修改时间
	 */
	protected Date docAlterTime;

	/*
	 * 是否匿名
	 */
	protected Boolean fdIsAnonymous = new Boolean(false);

	/*
	 * 父引用是否被删除
	 */
	protected Boolean fdIsParentDelete = new Boolean(false);
	/*
	 * 是否回帖仅作者可见
	 */
	protected Boolean fdIsOnlyView = new Boolean(false);

	/*
	 * 帖子内容
	 */
	protected String docContent;

	/*
	 * 楼号
	 */
	protected Integer fdFloor;

	/*
	 * 注释
	 */
	protected String fdNote;

	/*
	 * 引用信息
	 */
	protected String fdQuoteMsg;

	/*
	 * 引用内容
	 */
	protected String quoteMsg;

	/*
	 * 论坛话题
	 */
	protected KmForumTopic kmForumTopic = null;

	/*
	 * 帖子回复者
	 */
	protected SysOrgPerson fdPoster = null;

	/*
	 * add by tany 2014-6-15 帖子修改者
	 */
	protected SysOrgPerson fdAlteror = null;
	/*
	 * 用户积分
	 */
	protected KmForumScore posterScore = null;

	protected String fdIsNotify = null;

	protected String fdNotifyType = null;

	/*
	 * 引用父回复
	 */
	protected KmForumPost fdParent = null;

	/*
	 * 被赞数
	 */
	protected Integer fdSupportCount = 0;

	public Integer getFdSupportCount() {
		return fdSupportCount;
	}

	public void setFdSupportCount(Integer fdSupportCount) {
		this.fdSupportCount = fdSupportCount;
	}

	public KmForumPost() {
		super();
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
	 * @return 返回 创建时间
	 */
	public Date getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            要设置的 创建时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * @return 返回 最后修改时间
	 */
	public Date getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            要设置的 最后修改时间
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * @return 返回 是否匿名
	 */
	public Boolean getFdIsAnonymous() {
		return fdIsAnonymous;
	}

	/**
	 * @param fdIsAnonymous
	 *            要设置的 是否匿名
	 */
	public void setFdIsAnonymous(Boolean fdIsAnonymous) {
		this.fdIsAnonymous = fdIsAnonymous;
	}

	public Boolean getFdIsOnlyView() {
		return fdIsOnlyView;
	}

	public void setFdIsOnlyView(Boolean fdIsOnlyView) {
		this.fdIsOnlyView = fdIsOnlyView;
	}

	/**
	 * @return 返回 帖子内容
	 */
	public String getDocContent() {
		return (String) readLazyField("docContent", docContent);
	}

	/**
	 * @param docContent
	 *            要设置的 帖子内容
	 */
	public void setDocContent(String docContent) {
		this.docContent = (String) writeLazyField("docContent",
				this.docContent, docContent);
	}

	/**
	 * @return 返回 楼号
	 */
	public Integer getFdFloor() {
		return fdFloor;
	}

	/**
	 * @param fdFloor
	 *            要设置的 楼号
	 */
	public void setFdFloor(Integer fdFloor) {
		this.fdFloor = fdFloor;
	}

	/**
	 * @return 返回 注释
	 */
	public String getFdNote() {
		return fdNote;
	}

	/**
	 * @param fdNote
	 *            要设置的 注释
	 */
	public void setFdNote(String fdNote) {
		this.fdNote = fdNote;
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
		return (String) readLazyField("quoteMsg", quoteMsg);
	}

	/**
	 * @param fdNote
	 *            要设置的 引用内容
	 */
	public void setQuoteMsg(String quoteMsg) {
		this.quoteMsg = (String) writeLazyField("quoteMsg", this.quoteMsg,
				quoteMsg);
	}

	/**
	 * @return 返回 论坛话题
	 */
	public KmForumTopic getKmForumTopic() {
		return kmForumTopic;
	}

	/**
	 * @param kmForumTopic
	 *            要设置的 论坛话题
	 */
	public void setKmForumTopic(KmForumTopic kmForumTopic) {
		this.kmForumTopic = kmForumTopic;
	}

	/**
	 * @return 返回 帖子回复者
	 */
	public SysOrgPerson getFdPoster() {
		return fdPoster;
	}

	/**
	 * @param fdPoster
	 *            要设置的 帖子回复者
	 */
	public void setFdPoster(SysOrgPerson fdPoster) {
		this.fdPoster = fdPoster;
	}

	/**
	 * @return 返回 用户积分
	 */
	public KmForumScore getPosterScore() {
		return posterScore;
	}

	/**
	 * @param posterScore
	 *            要设置的 用户积分
	 */
	public void setPosterScore(KmForumScore posterScore) {
		this.posterScore = posterScore;
	}

	public SysOrgPerson getFdAlteror() {
		return fdAlteror;
	}

	public void setFdAlteror(SysOrgPerson fdAlteror) {
		this.fdAlteror = fdAlteror;
	}

	public Boolean getFdIsParentDelete() {
		return fdIsParentDelete;
	}

	public void setFdIsParentDelete(Boolean fdIsParentDelete) {
		this.fdIsParentDelete = fdIsParentDelete;
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
	
	protected List fdPostNotifier = new ArrayList();

	public List getFdPostNotifier() {
		return fdPostNotifier;
	}

	public void setFdPostNotifier(List fdPostNotifier) {
		this.fdPostNotifier = fdPostNotifier;
	}

	/**
	 * 附件实现
	 */
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

	@Override
    public Class getFormClass() {
		return KmForumPostForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdPoster.fdId", "fdPosterId");
			toFormPropertyMap.put("fdPoster.fdName", "fdPosterName");
			toFormPropertyMap.put("fdAlteror.fdName", "fdAlterorName");
			toFormPropertyMap.put("kmForumTopic.fdId", "fdTopicId");
			toFormPropertyMap.put("kmForumTopic.docSubject", "topicDocSubject");
			toFormPropertyMap.put("kmForumTopic.fdImportInfo", "fdImportInfo");
			toFormPropertyMap.put("kmForumTopic.fdConcludeInfo", "fdConcludeInfo");
			toFormPropertyMap.put("kmForumTopic.kmForumCategory.fdId",
					"fdForumId");
			toFormPropertyMap.put("kmForumTopic.kmForumCategory.fdName",
					"fdForumName");
			toFormPropertyMap.put("fdParent.fdId", "fdParentId");
		}
		return toFormPropertyMap;
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

	public KmForumPost getFdParent() {
		return fdParent;
	}

	public void setFdParent(KmForumPost fdParent) {
		this.fdParent = fdParent;
	}

	protected Integer docPraiseCount = Integer.valueOf(0);

	@Override
    public Integer getDocPraiseCount() {
		return docPraiseCount;
	}

	@Override
    public void setDocPraiseCount(Integer docPraiseCount) {
		this.docPraiseCount = docPraiseCount;
	}

	public String getDocSummary() {
		return docSummary;
	}

	public void setDocSummary(String docSummary) {
		this.docSummary = docSummary;
	}

}
