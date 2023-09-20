package com.landray.kmss.third.wechat.forms;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.webservice2.forms.AttachmentForm;

public class WeChatParamterForm {

	// 回复标题
	private String docSubject;

	// 内容文本
	private String docContent;

	// 主贴id
	private String fdTopicId;

	// 回复id
	private String fdPostId;
	
	//附件关联id
	private String modelId;
	
	//附件模块
	private String modelName;

	// 附件
	private List<AttachmentForm> attachmentForms = new ArrayList<AttachmentForm>();
	
	//查询类型 - 1部门  2个人
	private String utype;
	//参数
	private String uparam;
	//返回条目控制
	private int unum;
	
	public int getUnum() {
		return unum;
	}

	public void setUnum(int unum) {
		this.unum = unum;
	}

	public String getUtype() {
		return utype;
	}

	public void setUtype(String utype) {
		this.utype = utype;
	}

	public String getUparam() {
		return uparam;
	}

	public void setUparam(String uparam) {
		this.uparam = uparam;
	}

	public List<AttachmentForm> getAttachmentForms() {
		return attachmentForms;
	}
	
	public void setAttachmentForms(List<AttachmentForm> attachmentForms) {
		this.attachmentForms = attachmentForms;
	}

	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	public String getDocContent() {
		return docContent;
	}

	public void setDocContent(String docContent) {
		this.docContent = docContent;
	}

	public String getFdTopicId() {
		return fdTopicId;
	}

	public void setFdTopicId(String fdTopicId) {
		this.fdTopicId = fdTopicId;
	}

	public String getFdPostId() {
		return fdPostId;
	}

	public void setFdPostId(String fdPostId) {
		this.fdPostId = fdPostId;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	

}
