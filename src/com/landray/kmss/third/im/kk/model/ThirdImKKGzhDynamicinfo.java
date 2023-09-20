package com.landray.kmss.third.im.kk.model;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;

/**
 * 动态信息
 * 
 * @author 李显鹏
 * @version 1.0 2013-10-24
 */
public class ThirdImKKGzhDynamicinfo extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5380931707806549884L;

	/**
	 * 标题
	 */
	private String docTitle;

	/**
	 * @return 标题
	 */
	public String getDocTitle() {
		return docTitle;
	}

	/**
	 * @param docTitle
	 *            标题
	 */
	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	/**
	 * 标题
	 */
	private String docDescription;

	/**
	 * @return 标题
	 */
	public String getDocDescription() {
		return docDescription;
	}

	/**
	 * @param docTitle
	 *            标题
	 */
	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}

	/**
	 * 链接
	 */
	private String docUrl;

	/**
	 * @return 链接
	 */
	public String getDocUrl() {
		return docUrl;
	}

	/**
	 * @param docUrl
	 *            链接
	 */
	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	private String picUrl;
	
	public String getPicUrl() {
		return picUrl;
	}
	
	public void setPicUrl(String picUrl) {
		this.picUrl=picUrl;
	}
	/**
	 * 企业代码
	 */
	private String corpId;

	/**
	 * 服务号
	 */
	private String serviceCode;

	/**
	 * 流程ID
	 */
	private String fdProcessId;

	/**
	 * @return 流程ID
	 */
	public String getFdProcessId() {
		return fdProcessId;
	}

	/**
	 * @param fdProcessId
	 *            流程ID
	 */
	public void setFdProcessId(String fdProcessId) {
		this.fdProcessId = fdProcessId;
	}

	/**
	 * 形成时间
	 */
	private Date docCreateTime;

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	/**
	 * @return 形成时间
	 */
	public Date getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            形成时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	@Override
    @SuppressWarnings("unchecked")
	public Class getFormClass() {
		return null;
	}

	
}
