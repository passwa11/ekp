package com.landray.kmss.km.review.restservice.dto;

import com.landray.kmss.constant.SysDocConstant;

import java.util.Date;
import java.util.List;

public class KmReviewInstanceRestBaseDTO extends IdProperty {

	/*
	 * 主题
	 */
	protected String docSubject;

	/*
	 * 申请单编号
	 */
	protected String fdNumber;

	/*
	 * 创建时间
	 */
	protected Date docCreateTime;

	/*
	 * 创建人
	 */
	protected IdNameProperty docCreator;

	/*
	 * 发布时间
	 */
	protected Date docPublishTime;

	/*
	 * 文档状态
	 */
	protected String docStatus = SysDocConstant.DOC_STATUS_DRAFT;

	/*
	 * 当前环节
	 */
	private String wfNode;

	/*
	 * 当前处理人
	 */
	private List<IdNameProperty> wfHandler;

	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	public String getFdNumber() {
		return fdNumber;
	}

	public void setFdNumber(String fdNumber) {
		this.fdNumber = fdNumber;
	}

	public Date getDocPublishTime() {
		return docPublishTime;
	}

	public void setDocPublishTime(Date docPublishTime) {
		this.docPublishTime = docPublishTime;
	}

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public IdNameProperty getDocCreator() {
		return docCreator;
	}

	public void setDocCreator(IdNameProperty docCreator) {
		this.docCreator = docCreator;
	}

	public String getDocStatus() {
		return docStatus;
	}

	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}

	public String getWfNode() {
		return wfNode;
	}

	public void setWfNode(String wfNode) {
		this.wfNode = wfNode;
	}

	public List<IdNameProperty> getWfHandler() {
		return wfHandler;
	}

	public void setWfHandler(List<IdNameProperty> wfHandler) {
		this.wfHandler = wfHandler;
	}
}
