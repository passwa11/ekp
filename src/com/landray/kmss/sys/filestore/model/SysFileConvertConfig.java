package com.landray.kmss.sys.filestore.model;

import com.landray.kmss.common.model.BaseModel;

public class SysFileConvertConfig extends BaseModel {
	private static final long serialVersionUID = -4888209231404785939L;

	private String fdFileExtName;

	private String fdModelName;

	private String fdConverterKey;

	private String fdDispenser;

	private String fdStatus;

	private String fdConverterType;

	private String fdHighFidelity;

	private String fdPicResolution;

	private String fdPicRectangle;

	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	public String getFdDispenser() {
		return fdDispenser;
	}

	public void setFdDispenser(String fdDispenser) {
		this.fdDispenser = fdDispenser;
	}

	public String getFdFileExtName() {
		return fdFileExtName;
	}

	public void setFdFileExtName(String fdFileExtName) {
		this.fdFileExtName = fdFileExtName;
	}

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	public String getFdConverterKey() {
		return fdConverterKey;
	}

	public void setFdConverterKey(String fdConverterKey) {
		this.fdConverterKey = fdConverterKey;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getFormClass() {
		return null;
	}

	public String getFdConverterType() {
		return fdConverterType;
	}

	public void setFdConverterType(String fdConverterType) {
		this.fdConverterType = fdConverterType;
	}

	public String getFdHighFidelity() {
		return fdHighFidelity;
	}

	public void setFdHighFidelity(String fdHighFidelity) {
		this.fdHighFidelity = fdHighFidelity;
	}

	public String getFdPicResolution() {
		return fdPicResolution;
	}

	public void setFdPicResolution(String fdPicResolution) {
		this.fdPicResolution = fdPicResolution;
	}

	public String getFdPicRectangle() {
		return fdPicRectangle;
	}

	public void setFdPicRectangle(String fdPicRectangle) {
		this.fdPicRectangle = fdPicRectangle;
	}

}
