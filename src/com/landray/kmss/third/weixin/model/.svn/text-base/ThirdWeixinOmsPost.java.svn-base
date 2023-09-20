package com.landray.kmss.third.weixin.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.weixin.forms.ThirdWeixinOmsPostForm;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
 * 岗位同步
 * 
 * @author chenl
 * @version 1.0 2018-03-27
 */
public class ThirdWeixinOmsPost extends BaseModel implements InterceptFieldEnabled{

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
	private Boolean fdWxHandler = new Boolean(false);

	/**
	 * @return 微信
	 */
	public Boolean getFdWxHandler() {
		if (fdWxHandler == null) {
            fdWxHandler = new Boolean(false);
        }
		return this.fdWxHandler;
	}

	/**
	 * @param fdWxHandler
	 *            微信
	 */
	public void setFdWxHandler(Boolean fdWxHandler) {
		this.fdWxHandler = fdWxHandler;
	}

	/**
	 * 企业微信
	 */
	private Boolean fdWxworkHandler = new Boolean(false);

	/**
	 * @return 企业微信
	 */
	public Boolean getFdWxworkHandler() {
		if (fdWxworkHandler == null) {
            fdWxworkHandler = new Boolean(false);
        }
		return this.fdWxworkHandler;
	}

	/**
	 * @param fdWxworkHandler
	 *            企业微信
	 */
	public void setFdWxworkHandler(Boolean fdWxworkHandler) {
		this.fdWxworkHandler = fdWxworkHandler;
	}

	protected String docContent;

	/**
	 * @return 返回 文档内容
	 */
	public java.lang.String getDocContent() {
		return (String) readLazyField("docContent", docContent);
	}

	/**
	 * @param docContent
	 *            要设置的 文档内容
	 */
	public void setDocContent(java.lang.String docContent) {
		this.docContent = (String) writeLazyField("docContent", this.docContent,
				docContent);
	}

	// 机制开始
	// 机制结束

	@Override
    public Class<ThirdWeixinOmsPostForm> getFormClass() {
		return ThirdWeixinOmsPostForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}
}
