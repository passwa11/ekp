package com.landray.kmss.km.forum.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.forum.model.KmForumCategory;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.simplecategory.forms.SysSimpleCategoryAuthTmpForm;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2006-Aug-31
 * 
 * @author 吴兵
 */
public class KmForumCategoryForm extends SysSimpleCategoryAuthTmpForm

{
	/*
	 * 上级版块ID
	 */
	private String fdParentId = null;

	/*
	 * 上级版块名称
	 */
	private String fdParentName = null;

	/*
	 * 版块名称
	 */
	private String fdName = null;

	/*
	 * 版块描述
	 */
	private String fdDescription = null;

	/*
	 * 发表文章得分
	 */
	private String fdMainScore = null;

	/*
	 * 回复文章得分
	 */
	private String fdResScore = null;

	/*
	 * 至为精华得分
	 */
	protected String fdPinkScore = null;

	/*
	 * 允许匿名
	 */
	private String fdAnonymous = "false";

	/*
	 * 创建时间
	 */
	private String docCreateTime = null;

	/*
	 * 最后修改时间
	 */
	private String docAlterTime = null;

	/*
	 * 版块创建人
	 */
	protected String docCreatorId = null;

	protected String docCreatorName = null;

	/*
	 * 最后修改人
	 */
	protected String docAlterName = null;

	/**
	 * @return 返回 版块名称
	 */
	@Override
    public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            要设置的 版块名称
	 */
	@Override
    public void setFdName(String fdName) {
		this.fdName = fdName;
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
	public String getFdMainScore() {
		return fdMainScore;
	}

	/**
	 * @param fdMainScore
	 *            要设置的 发表文章得分
	 */
	public void setFdMainScore(String fdMainScore) {
		this.fdMainScore = fdMainScore;
	}

	/**
	 * @return 返回 回复文章得分
	 */
	public String getFdResScore() {
		return fdResScore;
	}

	/**
	 * @param fdResScore
	 *            要设置的 回复文章得分
	 */
	public void setFdResScore(String fdResScore) {
		this.fdResScore = fdResScore;
	}

	/**
	 * @return 返回 至为精华得分
	 */
	public String getFdPinkScore() {
		return fdPinkScore;
	}

	/**
	 * @param fdPinkScore
	 *            要设置的 至为精华得分
	 */
	public void setFdPinkScore(String fdPinkScore) {
		this.fdPinkScore = fdPinkScore;
	}	
	/**
	 * @return 返回 允许匿名
	 */
	public String getFdAnonymous() {
		return fdAnonymous;
	}

	/**
	 * @param fdAnonymous
	 *            要设置的 允许匿名
	 */
	public void setFdAnonymous(String fdAnonymous) {
		this.fdAnonymous = fdAnonymous;
	}


	/**
	 * @return 返回 创建时间
	 */
	@Override
    public String getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            要设置的 创建时间
	 */
	@Override
    public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * @return 返回 最后修改时间
	 */
	@Override
    public String getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            要设置的 最后修改时间
	 */
	@Override
    public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * @return 返回 版块创建人
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}

	/**
	 * @param docCreaterId
	 *            要设置的 版块创建人
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * @return 返回 版块创建人
	 */
	@Override
    public String getDocCreatorName() {
		return docCreatorName;
	}

	/**
	 * @param docCreaterName
	 *            要设置的 版块创建人
	 */
	@Override
    public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	/**
	 * @return 返回 最后修改人docAlterName
	 */
	public String getDocAlterName() {
		return docAlterName;
	}

	/**
	 * @param docAlter
	 *            要设置的 最后修改人
	 */
	public void setDocAlterName(String docAlterName) {
		this.docAlterName = docAlterName;
	}

	/*
	 * 版主列表
	 */
	private String fdManagerIds = null;

	private String fdManagerNames = null;

	public String getFdManagerIds() {
		return fdManagerIds;
	}

	public void setFdManagerIds(String fdManagerIds) {
		this.fdManagerIds = fdManagerIds;
	}

	public String getFdManagerNames() {
		return fdManagerNames;
	}

	public void setFdManagerNames(String fdManagerNames) {
		this.fdManagerNames = fdManagerNames;
	}

	/*
	 * 充许发文人员列表
	 */
	private String fdPosterIds = null;

	private String fdPosterNames = null;

	public String getFdPosterIds() {
		return fdPosterIds;
	}

	public void setFdPosterIds(String fdPosterIds) {
		this.fdPosterIds = fdPosterIds;
	}

	public String getFdPosterNames() {
		return fdPosterNames;
	}

	public void setFdPosterNames(String fdPosterNames) {
		this.fdPosterNames = fdPosterNames;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.landray.kmss.web.action.ActionForm#reset(com.landray.kmss.web.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdDescription = null;
		fdMainScore = "10";
		fdResScore = "5";
		fdPinkScore = "100";
		fdAnonymous = "false";
		docCreatorId = UserUtil.getUser().getFdId().toString();
		docCreatorName = UserUtil.getUser().getFdName();
		docCreateTime = DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, request.getLocale());
		docAlterName = null;
		docAlterTime = null;

		fdManagerIds = null;
		fdManagerNames = null;
		fdPosterIds = null;
		fdPosterNames = null;
		fdTimeliness = "false";
		fdTimelinessDate = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmForumCategory.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());

			toModelPropertyMap.put("fdManagerIds",
					new FormConvertor_IDsToModelList("authAllEditors",
							SysOrgElement.class));
			toModelPropertyMap.put("fdPosterIds",
					new FormConvertor_IDsToModelList("authAllReaders",
							SysOrgElement.class));
			toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel(
					"fdParent", KmForumCategory.class));
			toModelPropertyMap.put("fdTimelinessDate",
					new FormConvertor_Common("fdTimelinessDate"));
		}
		return toModelPropertyMap;
	}

	@Override
    public String getFdParentId() {
		return fdParentId;
	}

	@Override
    public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}

	@Override
    public String getFdParentName() {
		return fdParentName;
	}

	@Override
    public void setFdParentName(String fdParentName) {
		this.fdParentName = fdParentName;
	}

	private String fdTimeliness = "false";

	public String getFdTimeliness() {
		return fdTimeliness;
	}

	public void setFdTimeliness(String fdTimeliness) {
		this.fdTimeliness = fdTimeliness;
	}

	private String fdTimelinessDate;

	public String getFdTimelinessDate() {
		return fdTimelinessDate;
	}

	public void setFdTimelinessDate(String fdTimelinessDate) {
		this.fdTimelinessDate = fdTimelinessDate;
	}
}
