package com.landray.kmss.sys.filestore.model;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;

public class SysFileConvertQueue extends BaseModel {

	private static final long serialVersionUID = -2625288107849826310L;

	private String fdFileId;

	private String fdAttMainId;
	
	private String fdAttMainModelName;

	private String fdAttModelId;

	private String fdFileName;

	private String fdFileExtName;

	private String fdModelUrl;
	
	private String fdFileDownUrl;

	private String fdConverterKey;

	private String fdConverterType;

	private String fdConverterParam;

	private Integer fdConvertNumber;

	private Boolean fdIsFinish;

	private Integer fdConvertStatus;

	private String fdDispenser;

	private Date fdStatusTime;

	private Date fdCreateTime;

	private String fdClientId;
	
	private Boolean fdIsLongQueue;

	private Long expireTime; // 回调时用的：转换没有回调的超时时间

	private Boolean fdIsPdfGenerated;

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

	public String getFdFileName() {
		return fdFileName;
	}

	public void setFdFileName(String fdFileName) {
		this.fdFileName = fdFileName;
	}

	public String getFdFileExtName() {
		return fdFileExtName;
	}

	public void setFdFileExtName(String fdFileExtName) {
		this.fdFileExtName = fdFileExtName;
	}

	public String getFdModelUrl() {
		return fdModelUrl;
	}

	public void setFdModelUrl(String fdModelUrl) {
		this.fdModelUrl = fdModelUrl;
	}

	public String getFdConverterKey() {
		return fdConverterKey;
	}

	public void setFdConverterKey(String fdConverterKey) {
		if("toJPG".equals(fdConverterKey))
		{
			fdConverterKey = "toHTML";
		}
		this.fdConverterKey = fdConverterKey;
	}

	public String getFdConverterParam() {
		return this.fdConverterParam;
	}

	public void setFdConverterParam(String fdConverterParam) {
		this.fdConverterParam = fdConverterParam;
	}

	public Integer getFdConvertNumber() {
		return fdConvertNumber;
	}

	public void setFdConvertNumber(Integer fdConvertNumber) {
		this.fdConvertNumber = fdConvertNumber;
	}

	public Boolean getFdIsFinish() {
		return fdIsFinish;
	}

	public void setFdIsFinish(Boolean fdIsFinish) {
		this.fdIsFinish = fdIsFinish;
	}

	public Integer getFdConvertStatus() {
		return fdConvertStatus;
	}

	public void setFdConvertStatus(Integer fdConvertStatus) {
		this.fdConvertStatus = fdConvertStatus;
	}

	public Date getFdStatusTime() {
		return fdStatusTime;
	}

	public void setFdStatusTime(Date fdStatusTime) {
		this.fdStatusTime = fdStatusTime;
	}

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getFormClass() {
		return null;
	}

	public String getFdClientId() {
		return fdClientId;
	}

	public void setFdClientId(String fdClientId) {
		this.fdClientId = fdClientId;
	}

	public String getFdAttModelId() {
		return fdAttModelId;
	}

	public void setFdAttModelId(String fdAttModelId) {
		this.fdAttModelId = fdAttModelId;
	}

	public String getFdConverterType() {
		return fdConverterType;
	}

	public void setFdConverterType(String fdConverterType) {
		this.fdConverterType = fdConverterType;
	}

	public Boolean getFdIsLongQueue() {
		return fdIsLongQueue;
	}

	public void setFdIsLongQueue(Boolean fdIsLongQueue) {
		this.fdIsLongQueue = fdIsLongQueue;
	}

	public String getFdFileDownUrl() {
		return fdFileDownUrl;
	}

	public void setFdFileDownUrl(String fdFileDownUrl) {
		this.fdFileDownUrl = fdFileDownUrl;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public Boolean getFdIsPdfGenerated() {
		return fdIsPdfGenerated;
	}

	public void setFdIsPdfGenerated(Boolean fdIsPdfGenerated) {
		this.fdIsPdfGenerated = fdIsPdfGenerated;
	}
}
