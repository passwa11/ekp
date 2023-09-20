package com.landray.kmss.km.forum.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.LastModifiedTimeModel;
import com.landray.kmss.km.forum.forms.KmForumTopicForm;
import com.landray.kmss.sys.bookmark.interfaces.ISysBookmarkModel;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 创建日期 2006-Sep-05
 * 
 * @author 吴兵 论坛话题
 */
public class KmForumTopic extends LastModifiedTimeModel implements
		ISysNotifyModel,ISysBookmarkModel{

	/*
	 * 话题标题
	 */
	protected String docSubject;

	/*
	 *话题概要 
	 */
	protected String docSummary;
	
	/*
	 * 缩略图
	 */
	protected String fdThumbInfo;
	
	/*
	 * 是否匿名
	 */
	protected Boolean fdIsAnonymous = new Boolean(false);

	/*
	 * 创建时间
	 */
	protected Date docCreateTime = new Date();

	/*
	 * 最后修改时间
	 */
	protected Date docAlterTime;

	/*
	 * 该话题最后回复时间
	 */
	protected Date fdLastPostTime;

	/*
	 * 该话题回复数
	 */
	protected Integer fdReplyCount;

	/*
	 * 话题点击数
	 */
	protected Integer fdHitCount;

	/*
	 * 话题是否被置顶
	 */
	protected Boolean fdSticked;

	/*
	 * 话题是否精华
	 */
	protected Boolean fdPinked;

	/*
	 * 版块设置
	 */
	protected KmForumCategory kmForumCategory = null;

	/*
	 * 该话题的发起者
	 */
	protected SysOrgElement fdPoster = null;

	/*
	 * 该话题的最后回复者
	 */
	protected SysOrgElement fdLastPoster = null;

	/*
	 * 该话题的最后回复者名称
	 */
	protected String fdLastPosterName = null;

	/*
	 * 是否通知
	 */
	protected String fdIsNotify = null;

	/*
	 * 是否通知
	 */
	protected String fdNotifyType = null;

	/*
	 * 状态
	 */
	protected String fdStatus = null;
	
	/*
	 * 置顶时间
	 */
	protected java.util.Date fdTopTime;
	
	/*
	 * 置顶失效时间
	 */
	protected java.util.Date fdTopEndTime;
	
	public java.util.Date getFdTopTime() {
		return fdTopTime;
	}

	public void setFdTopTime(java.util.Date fdTopTime) {
		this.fdTopTime = fdTopTime;
	}

	public java.util.Date getFdTopEndTime() {
		return fdTopEndTime;
	}

	public void setFdTopEndTime(java.util.Date fdTopEndTime) {
		this.fdTopEndTime = fdTopEndTime;
	}


	public KmForumTopic() {
		super();
	}

	/**
	 * @return 返回 话题标题
	 */
	public String getDocSubject() {
		return docSubject;
	}

	/**
	 * @param docSubject
	 *            要设置的 话题标题
	 */
	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
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
	 * @return 返回 该话题最后回复时间
	 */
	public Date getFdLastPostTime() {
		return fdLastPostTime;
	}

	/**
	 * @param fdLastPostTime
	 *            要设置的 该话题最后回复时间
	 */
	public void setFdLastPostTime(Date fdLastPostTime) {
		this.fdLastPostTime = fdLastPostTime;
	}

	/**
	 * @return 返回 该话题回复数
	 */
	public Integer getFdReplyCount() {
		return fdReplyCount;
	}

	/**
	 * @param fdReplyCount
	 *            要设置的 该话题回复数
	 */
	public void setFdReplyCount(Integer fdReplyCount) {
		this.fdReplyCount = fdReplyCount;
	}

	/**
	 * @return 返回 话题点击数
	 */
	public Integer getFdHitCount() {
		return fdHitCount;
	}

	/**
	 * @param fdHitCount
	 *            要设置的 话题点击数
	 */
	public void setFdHitCount(Integer fdHitCount) {
		this.fdHitCount = fdHitCount;
	}

	/**
	 * @return 返回 话题是否被置顶
	 */
	public Boolean getFdSticked() {
		return fdSticked;
	}

	/**
	 * @param fdSticked
	 *            要设置的 话题是否被置顶
	 */
	public void setFdSticked(Boolean fdSticked) {
		this.fdSticked = fdSticked;
	}

	/**
	 * @return 返回 话题是否精华
	 */
	public Boolean getFdPinked() {
		return fdPinked;
	}

	/**
	 * @param fdPinked
	 *            要设置的 话题是否精华
	 */
	public void setFdPinked(Boolean fdPinked) {
		this.fdPinked = fdPinked;
	}

	/**
	 * @return 返回 版块设置
	 */
	public KmForumCategory getKmForumCategory() {
		return kmForumCategory;
	}

	/**
	 * @param kmForumCategory
	 *            要设置的 版块设置
	 */
	public void setKmForumCategory(KmForumCategory kmForumCategory) {
		this.kmForumCategory = kmForumCategory;
	}

	/**
	 * @return 返回 该话题的发起者
	 */
	public SysOrgElement getFdPoster() {
		return fdPoster;
	}

	/**
	 * @param fdPoster
	 *            要设置的 该话题的发起者
	 */
	public void setFdPoster(SysOrgElement fdPoster) {
		this.fdPoster = fdPoster;
	}

	/**
	 * @return 返回 该话题的最后回复者
	 */
	public SysOrgElement getFdLastPoster() {
		return fdLastPoster;
	}

	/**
	 * @param fdLastPoster
	 *            要设置的 该话题的最后回复者
	 */
	public void setFdLastPoster(SysOrgElement fdLastPoster) {
		this.fdLastPoster = fdLastPoster;
	}

	/**
	 * @return 返回 该话题的最后回复者名称
	 */
	public String getFdLastPosterName() {
		return fdLastPosterName;
	}

	/**
	 * @param fdLastPosterName
	 *            要设置的 该话题的最后回复者名称
	 */
	public void setFdLastPosterName(String fdLastPosterName) {
		this.fdLastPosterName = fdLastPosterName;
	}

	/**
	 * 一对多关联,该话题下所有帖子列表
	 */
	private List forumPosts = new ArrayList();

	/**
	 * @return 返回一对多关联 该话题下所有帖子列表
	 */
	public List getForumPosts() {
		return forumPosts;
	}

	/**
	 * @param forumPosts
	 *            要设置的 一对多关联 该话题下所有帖子列表
	 */
	public void setForumPosts(List forumPosts) {
		this.forumPosts = forumPosts;
	}

	private String fdImportInfo;

	public String getFdImportInfo() {
		return fdImportInfo;
	}

	public void setFdImportInfo(String fdImportInfo) {
		this.fdImportInfo = fdImportInfo;
	}

	private String fdConcludeInfo; //结贴信息
	
	public String getFdConcludeInfo() {
		return fdConcludeInfo;
	}

	public void setFdConcludeInfo(String fdConcludeInfo) {
		this.fdConcludeInfo = fdConcludeInfo;
	}

	@Override
    public Class getFormClass() {
		return KmForumTopicForm.class;
	}

	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("kmForumCategory.fdId", "fdForumId");
			toFormPropertyMap.put("kmForumCategory.fdName", "fdForumName");
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

	@Override
    public Integer getDocMarkCount() {
		return null;
	}

	@Override
    public void setDocMarkCount(Integer count) {
	}
	
	public String getDocSummary() {
		return docSummary;
	}

	public void setDocSummary(String docSummary) {
		this.docSummary = docSummary;
	}

	public String getFdThumbInfo() {
		return fdThumbInfo;
	}

	public void setFdThumbInfo(String fdThumbInfo) {
		this.fdThumbInfo = fdThumbInfo;
	}

}
