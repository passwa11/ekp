package com.landray.kmss.third.weixin.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.weixin.model.ThirdWeixinOmsPost;

/**
 * 岗位同步 Form
 * 
 * @author chenl
 * @version 1.0 2018-03-27
 */
public class ThirdWeixinOmsPostForm extends ExtendForm {

	/**
	 * 岗位
	 */
	private String fdPostId;

	/**
	 * @return 岗位
	 */
	public String getFdPostId() {
		return this.fdPostId;
	}

	/**
	 * @param fdPostId
	 *            岗位
	 */
	public void setFdPostId(String fdPostId) {
		this.fdPostId = fdPostId;
	}

	/**
	 * 人员
	 */
	private String fdPersonIds;

	/**
	 * @return 人员
	 */
	public String getFdPersonIds() {
		return this.fdPersonIds;
	}

	/**
	 * @param fdPersonIds
	 *            人员
	 */
	public void setFdPersonIds(String fdPersonIds) {
		this.fdPersonIds = fdPersonIds;
	}

	/**
	 * 微信
	 */
	private String fdWxHandler;

	/**
	 * @return 微信
	 */
	public String getFdWxHandler() {
		return this.fdWxHandler;
	}

	/**
	 * @param fdWxHandler
	 *            微信
	 */
	public void setFdWxHandler(String fdWxHandler) {
		this.fdWxHandler = fdWxHandler;
	}

	/**
	 * 企业微信
	 */
	private String fdWxworkHandler;

	/**
	 * @return 企业微信
	 */
	public String getFdWxworkHandler() {
		return this.fdWxworkHandler;
	}

	/**
	 * @param fdWxworkHandler
	 *            企业微信
	 */
	public void setFdWxworkHandler(String fdWxworkHandler) {
		this.fdWxworkHandler = fdWxworkHandler;
	}

	private String docContent = null;
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

	// 机制开始
	// 机制结束

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdPostId = null;
		fdPersonIds = null;
		fdWxHandler = "false";
		fdWxworkHandler = "false";
		docContent = null;

		super.reset(mapping, request);
	}

	@Override
    public Class<ThirdWeixinOmsPost> getModelClass() {
		return ThirdWeixinOmsPost.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}
}
