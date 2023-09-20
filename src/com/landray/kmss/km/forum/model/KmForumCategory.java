package com.landray.kmss.km.forum.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.forum.forms.KmForumCategoryForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2006-Aug-31
 * 
 * @author 吴兵 版块设置
 */
public class KmForumCategory extends SysSimpleCategoryAuthTmpModel {

	/*
	 * 版块描述
	 */
	protected String fdDescription;

	/*
	 * 发表文章得分
	 */
	protected Integer fdMainScore = new Integer(10);

	/*
	 * 回复文章得分
	 */
	protected Integer fdResScore = new Integer(5);

	/*
	 * 至为精华得分
	 */
	protected Integer fdPinkScore = new Integer(5);

	/*
	 * 允许匿名
	 */
	protected Boolean fdAnonymous = new Boolean(false);

	/*
	 * 创建时间
	 */
	protected Date docCreateTime = null;

	/*
	 * 该论坛帖子总数
	 */
	protected Integer fdPostCount;

	/*
	 * 该论坛话题总数
	 */
	protected Integer fdTopicCount;

	/*
	 * 最后修改人
	 */
	protected SysOrgElement docAlter = null;
	

	public KmForumCategory() {
		super();
	}

	/**
	 * @return 返回 版块描述
	 */
	public String getFdDescription() {
		return fdDescription;
	}

	/**
	 * @param fdDescription
	 *            要设置的 版块描述
	 */
	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

	/**
	 * @return 返回 发表文章得分
	 */
	public Integer getFdMainScore() {
		return fdMainScore;
	}

	/**
	 * @param fdMainScore
	 *            要设置的 发表文章得分
	 */
	public void setFdMainScore(Integer fdMainScore) {
		this.fdMainScore = fdMainScore;
	}

	/**
	 * @return 返回 回复文章得分
	 */
	public Integer getFdResScore() {
		return fdResScore;
	}

	/**
	 * @param fdResScore
	 *            要设置的 回复文章得分
	 */
	public void setFdResScore(Integer fdResScore) {
		this.fdResScore = fdResScore;
	}

	/**
	 * @return 返回 至为精华得分
	 */
	public Integer getFdPinkScore() {
		return fdPinkScore;
	}

	/**
	 * @param fdPinkScore
	 *            要设置的 至为精华得分
	 */
	public void setFdPinkScore(Integer fdPinkScore) {
		this.fdPinkScore = fdPinkScore;
	}

	/**
	 * @return 返回 允许匿名
	 */
	public Boolean getFdAnonymous() {
		return fdAnonymous;
	}

	/**
	 * @param fdAnonymous
	 *            要设置的 允许匿名
	 */
	public void setFdAnonymous(Boolean fdAnonymous) {
		this.fdAnonymous = fdAnonymous;
	}

	/**
	 * @return 返回 创建时间
	 */
	@Override
	public Date getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            要设置的 创建时间
	 */
	@Override
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * @return 返回 该论坛帖子总数
	 */
	public Integer getFdPostCount() {
		return fdPostCount;
	}

	/**
	 * @param fdPostCount
	 *            要设置的 该论坛帖子总数
	 */
	public void setFdPostCount(Integer fdPostCount) {
		this.fdPostCount = fdPostCount;
	}

	/**
	 * @return 返回 该论坛话题总数
	 */
	public Integer getFdTopicCount() {
		return fdTopicCount;
	}

	/**
	 * @param fdTopicCount
	 *            要设置的 该论坛话题总数
	 */
	public void setFdTopicCount(Integer fdTopicCount) {
		this.fdTopicCount = fdTopicCount;
	}

	/**
	 * @return 返回 最后修改人
	 */
	public SysOrgElement getDocAlter() {
		return docAlter;
	}

	/**
	 * @param docAlter
	 *            要设置的 最后修改人
	 */
	public void setDocAlter(SysOrgElement docAlter) {
		this.docAlter = docAlter;
	}

	@Override
	public Boolean getAuthReaderFlag() {
		if (getAuthAllReaders() == null || getAuthAllReaders().isEmpty()) {
            return new Boolean(true);
        }
		return new Boolean(false);
	}

	@Override
	public void setAuthReaderFlag(Boolean authReaderFlag) {
	}

	/*
	 * 所有人可访问标记
	 */
	protected Boolean authVisitFlag;

	public Boolean getAuthVisitFlag() {
		if (ArrayUtil.isEmpty(getAuthReaders())) {
            return new Boolean(true);
        } else {
            return new Boolean(false);
        }
	}

	public void setAuthVisitFlag(Boolean authVisitFlag) {
	}

	/*
	 * 一对多关联 该版块下的所有主题
	 */
	protected List forumTopics = new ArrayList();

	/**
	 * @return 返回一对多关联 该版块下的所有主题
	 */
	public List getForumTopics() {
		return forumTopics;
	}

	/**
	 * @param forumTopics
	 *            要设置的 一对多关联 该版块下的所有主题
	 */
	public void setForumTopics(List forumTopics) {
		this.forumTopics = forumTopics;
	}

	@Override
	public Class getFormClass() {
		return KmForumCategoryForm.class;
	}

	protected List fdChildren = null;

	public List getFdChildren() {
		return fdChildren;
	}

	public void setFdChildren(List fdChildren) {
		this.fdChildren = fdChildren;
	}
	
	@Override
	protected void recalculateEditorField() {
		// 部署简单分类不完整，不能计算
	}

	@Override
	protected void recalculateReaderField() {
		// 内外组织权限隔离
		if (CollectionUtils.isEmpty(getAuthReaders())) {
			SysOrgPerson creator = getDocCreator();
			if (creator == null) {
				creator = UserUtil.getUser();
			}
			if (BooleanUtils.isTrue(creator.getFdIsExternal())) {
				SysOrgElement parent = creator.getFdParent();
				if (parent != null) {
					getAuthReaders().add(parent);
				}
			}
		}
	}
	
	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docAlter.fdName", "docAlterName");
			toFormPropertyMap.put("fdParent.fdId", "fdParentId");
			toFormPropertyMap.put("fdParent.fdName", "fdParentName");

			toFormPropertyMap.put("authAllEditors",
					new ModelConvertor_ModelListToString(
							"fdManagerIds:fdManagerNames", "fdId:deptLevelNames"));
			toFormPropertyMap.put("authAllReaders",
					new ModelConvertor_ModelListToString(
							"fdPosterIds:fdPosterNames", "fdId:deptLevelNames"));
			toFormPropertyMap.put("fdTimelinessDate",
					new ModelConvertor_Common("fdTimelinessDate"));
		}
		return toFormPropertyMap;
	}

	/**
	 * 是否开启失效性设置
	 */
	protected Boolean fdTimeliness = new Boolean(false);

	public Boolean getFdTimeliness() {
		if (fdTimeliness == null) {
            fdTimeliness = Boolean.FALSE;
        }
		return fdTimeliness;
	}

	public void setFdTimeliness(Boolean fdTimeliness) {
		this.fdTimeliness = fdTimeliness;
	}

	/**
	 * 设置结贴时长
	 */
	protected Integer fdTimelinessDate;

	public Integer getFdTimelinessDate() {
		return fdTimelinessDate;
	}

	public void setFdTimelinessDate(Integer fdTimelinessDate) {
		this.fdTimelinessDate = fdTimelinessDate;
	}
}
