package com.landray.kmss.sys.praise.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.praise.model.SysPraiseInfo;
import com.landray.kmss.sys.praise.model.SysPraiseInfoCategory;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 赞赏信息表
 */
public class SysPraiseInfoForm extends ExtendForm {

	private static FormToModelPropertyMap toModelPropertyMap;

	private String docSubject = null;

	private String docCreateTime = null;

	private String fdTargetId = null;

	private String fdTargetName = null;

	private String fdReason = null;

	private String fdRich = null;

	private String fdType = null;

	private String fdSourceId = null;

	private String fdSourceName = null;

	private String fdSourceTitle = null;

	private String fdTargetPersonId = null;

	private String fdTargetPersonName = null;

	private String fdPraisePersonId = null;

	private String fdPraisePersonName = null;

	private String fdIsHideName = null;

	private String docCategoryId = null;

	private String docCategoryName = null;

	private String isReply = null;

	private String replyContent = null;

	private String replyTime = null;

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdTargetId = null;
		fdTargetName = null;
		fdReason = null;
		fdRich = null;
		fdType = null;
		docCategoryId = null;
		docCategoryName = null;
		fdSourceId = null;
		fdSourceName = null;
		fdSourceTitle = null;
		fdTargetPersonId = null;
		fdTargetPersonName = null;
		fdPraisePersonId = UserUtil.getUser().getFdId();
		fdPraisePersonName = UserUtil.getUser().getFdName();
		docSubject = null;
		docCreateTime = null;
		fdIsHideName = null;
		isReply = null;
		replyContent = null;
		replyTime = null;
		fdNotifyType = null;
		super.reset(mapping, request);
	}

	@Override
    public Class<SysPraiseInfo> getModelClass() {
		return SysPraiseInfo.class;
	}

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdTargetPersonId",
					new FormConvertor_IDToModel("fdTargetPerson", SysOrgElement.class));
			toModelPropertyMap.put("fdPraisePersonId",
					new FormConvertor_IDToModel("fdPraisePerson", SysOrgElement.class));
			toModelPropertyMap.put("docCategoryId",
					new FormConvertor_IDToModel("docCategory", SysPraiseInfoCategory.class));
		}
		return toModelPropertyMap;
	}

	/**
	 * 目标id
	 */
	public String getFdTargetId() {
		return this.fdTargetId;
	}

	/**
	 * 目标id
	 */
	public void setFdTargetId(String fdTargetId) {
		this.fdTargetId = fdTargetId;
	}

	/**
	 * 目标模块名
	 */
	public String getFdTargetName() {
		return this.fdTargetName;
	}

	/**
	 * 目标模块名
	 */
	public void setFdTargetName(String fdTargetName) {
		this.fdTargetName = fdTargetName;
	}

	/**
	 * 赞赏原因
	 */
	public String getFdReason() {
		return this.fdReason;
	}

	/**
	 * 赞赏原因
	 */
	public void setFdReason(String fdReason) {
		this.fdReason = fdReason;
	}

	/**
	 * 财富值
	 */
	public String getFdRich() {
		return this.fdRich;
	}

	/**
	 * 财富值
	 */
	public void setFdRich(String fdRich) {
		this.fdRich = fdRich;
	}

	/**
	 * 类型
	 */
	public String getFdType() {
		return this.fdType;
	}

	/**
	 * 类型
	 */
	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	/**
	 * 目标来源id
	 */
	public String getFdSourceId() {
		return this.fdSourceId;
	}

	/**
	 * 目标来源id
	 */
	public void setFdSourceId(String fdSourceId) {
		this.fdSourceId = fdSourceId;
	}

	/**
	 * 目标来源模块名
	 */
	public String getFdSourceName() {
		return this.fdSourceName;
	}

	/**
	 * 目标来源模块名
	 */
	public void setFdSourceName(String fdSourceName) {
		this.fdSourceName = fdSourceName;
	}

	/**
	 * 目标来源标题
	 */
	public String getFdSourceTitle() {
		return this.fdSourceTitle;
	}

	/**
	 * 目标来源标题
	 */
	public void setFdSourceTitle(String fdSourceTitle) {
		this.fdSourceTitle = fdSourceTitle;
	}

	/**
	 * 目标人员
	 */
	public String getFdTargetPersonId() {
		return this.fdTargetPersonId;
	}

	/**
	 * 目标人员
	 */
	public void setFdTargetPersonId(String fdTargetPersonId) {
		this.fdTargetPersonId = fdTargetPersonId;
	}

	/**
	 * 目标人员
	 */
	public String getFdTargetPersonName() {
		return this.fdTargetPersonName;
	}

	/**
	 * 目标人员
	 */
	public void setFdTargetPersonName(String fdTargetPersonName) {
		this.fdTargetPersonName = fdTargetPersonName;
	}

	/**
	 * 赞赏人
	 */
	public String getFdPraisePersonId() {
		return this.fdPraisePersonId;
	}

	/**
	 * 赞赏人
	 */
	public void setFdPraisePersonId(String fdPraisePersonId) {
		this.fdPraisePersonId = fdPraisePersonId;
	}

	/**
	 * 赞赏人
	 */
	public String getFdPraisePersonName() {
		return this.fdPraisePersonName;
	}

	/**
	 * 赞赏人
	 */
	public void setFdPraisePersonName(String fdPraisePersonName) {
		this.fdPraisePersonName = fdPraisePersonName;
	}

	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getFdIsHideName() {
		return fdIsHideName;
	}

	public void setFdIsHideName(String fdIsHideName) {
		this.fdIsHideName = fdIsHideName;
	}

	public String getDocCategoryId() {
		return docCategoryId;
	}

	public void setDocCategoryId(String docCategoryId) {
		this.docCategoryId = docCategoryId;
	}

	public String getDocCategoryName() {
		return docCategoryName;
	}

	public void setDocCategoryName(String docCategoryName) {
		this.docCategoryName = docCategoryName;
	}

	public String getIsReply() {
		return isReply;
	}

	public void setIsReply(String isReply) {
		this.isReply = isReply;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	/**
	 * 通知类型
	 */
	private String fdNotifyType;

	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}
}
