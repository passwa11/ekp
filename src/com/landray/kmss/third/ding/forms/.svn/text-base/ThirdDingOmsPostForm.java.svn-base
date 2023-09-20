package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingOmsPost;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 岗位 Form
 * 
 * @author chenl
 * @version 1.0 2018-02-06
 */
public class ThirdDingOmsPostForm extends ExtendForm {

	/**
	 * 名称
	 */
	private String fdName;

	/**
	 * @return 名称
	 */
	public String getFdName() {
		return this.fdName;
	}

	/**
	 * @param fdName
	 *            名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 内容
	 */
	private String fdContent;

	/**
	 * @return 内容
	 */
	public String getFdContent() {
		return this.fdContent;
	}

	/**
	 * @param fdContent
	 *            内容
	 */
	public void setFdContent(String fdContent) {
		this.fdContent = fdContent;
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
		fdName = null;
		fdContent = null;
		docContent = null;

		super.reset(mapping, request);
	}

	@Override
    public Class<ThirdDingOmsPost> getModelClass() {
		return ThirdDingOmsPost.class;
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
