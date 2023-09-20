package com.landray.kmss.sys.filestore.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;

public class SysFileConvertQueueForm extends ExtendForm {

	private static final long serialVersionUID = 3103821313119977179L;

	private String fdFileId;

	private String fdAttMainId;
	
	private String fdAttMainModelName;

	private String fdFileExtName;

	private String fdConverterKey;
	private String fdConverterType;

	private String fdConverterParam;

	private String fdConvertNumber;

	private String fdIsFinish;

	private String fdConvertStatus;

	private String fdDispenser;

	private String fdStatusTime;

	private String fdCreateTime;
	
	private String fdClientId;

	public String getFdDispenser() {
		return fdDispenser;
	}

	public void setFdDispenser(String fdDispenser) {
		this.fdDispenser = fdDispenser;
	}

	public String getFdFileId() {
		return fdFileId;
	}

	public void setFdFileId(String fdFileId) {
		this.fdFileId = fdFileId;
	}

	public String getFdAttMainId() {
		return fdAttMainId;
	}

	public void setFdAttMainId(String fdAttMainId) {
		this.fdAttMainId = fdAttMainId;
	}
		

	public String getFdAttMainModelName() {
		return fdAttMainModelName;
	}

	public void setFdAttMainModelName(String fdAttMainModelName) {
		this.fdAttMainModelName = fdAttMainModelName;
	}

	public String getFdFileExtName() {
		return fdFileExtName;
	}

	public void setFdFileExtName(String fdFileExtName) {
		this.fdFileExtName = fdFileExtName;
	}

	public String getFdConverterKey() {
		return fdConverterKey;
	}

	public void setFdConverterKey(String fdConverterKey) {
		this.fdConverterKey = fdConverterKey;
	}

	public String getFdConverterParam() {
		if (this.fdConverterParam == null) {
			this.fdConverterParam = "";
		}
		return this.fdConverterParam;
	}

	public void setFdConverterParam(String fdConverterParam) {
		this.fdConverterParam = fdConverterParam;
	}

	public String getFdConvertNumber() {
		return fdConvertNumber;
	}

	public void setFdConvertNumber(String fdConvertNumber) {
		this.fdConvertNumber = fdConvertNumber;
	}

	public String getFdIsFinish() {
		return fdIsFinish;
	}

	public void setFdIsFinish(String fdIsFinish) {
		this.fdIsFinish = fdIsFinish;
	}

	public String getFdConvertStatus() {
		return fdConvertStatus;
	}

	public void setFdConvertStatus(String fdConvertStatus) {
		this.fdConvertStatus = fdConvertStatus;
	}

	public String getFdStatusTime() {
		return fdStatusTime;
	}

	public void setFdStatusTime(String fdStatusTime) {
		this.fdStatusTime = fdStatusTime;
	}

	public String getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(String fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class getModelClass() {
		return SysFileConvertQueue.class;
	}

	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.fdAttMainId=null;
		this.fdAttMainModelName = null;
		this.fdConverterKey=null;
		this.fdConverterParam=null;
		this.fdConvertNumber=null;
		this.fdConvertStatus=null;
		this.fdCreateTime=null;
		this.fdDispenser=null;
		this.fdFileExtName=null;
		this.fdFileId=null;
		this.fdIsFinish=null;
		this.fdStatusTime=null;
		super.reset(mapping, request);
	}

	public String getFdClientId() {
		return fdClientId;
	}

	public void setFdClientId(String fdClientId) {
		this.fdClientId = fdClientId;
	}

	public String getFdConverterType() {
		return fdConverterType;
	}

	public void setFdConverterType(String fdConverterType) {
		this.fdConverterType = fdConverterType;
	}


}
