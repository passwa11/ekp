package com.landray.kmss.sys.attachment.forms;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.util.ImageCropUtil;

public class SysAttImageCropForm extends ExtendForm {

	private static final long serialVersionUID = -2862696251209993140L;

	/**
	 * 起始x坐标
	 */
	private Integer fdStartX;

	public Integer getFdStartX() {
		return fdStartX;
	}

	public void setFdStartX(Integer fdStartX) {
		this.fdStartX = fdStartX;
	}

	/**
	 * 起始y坐标
	 */
	private Integer fdStartY;

	public Integer getFdStartY() {
		return fdStartY;
	}

	public void setFdStartY(Integer fdStartY) {
		this.fdStartY = fdStartY;
	}

	/**
	 * 所选区域宽
	 */
	private Integer fdCropWidth;

	public Integer getFdCropWidth() {
		return fdCropWidth;
	}

	public void setFdCropWidth(Integer fdCropWidth) {
		this.fdCropWidth = fdCropWidth;
	}
	
	/**
	 * 所选区域高
	 */
	private Integer fdCropHeight;

	public Integer getFdCropHeight() {
		return fdCropHeight;
	}

	public void setFdCropHeight(Integer fdCropHeight) {
		this.fdCropHeight = fdCropHeight;
	}

	/**
	 * 附件(图片)id
	 */
	private String fdCropId;

	public String getFdCropId() {
		return fdCropId;
	}

	public void setFdCropId(String fdCropId) {
		this.fdCropId = fdCropId;
	}
	
	private String fdModelName;

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}
	
	private String fdModelId;

	public String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	private Integer fdBigWidth;

	private Integer fdBigHeight;

	private Integer fdMediumWidth;

	private Integer fdMediumHeight;

	private Integer fdSmallWidth;

	private Integer fdSmallHeight;

	public void setFdBigWidth(Integer fdBigWidth) {
		this.fdBigWidth = fdBigWidth;
	}

	public void setFdBigHeight(Integer fdBigHeight) {
		this.fdBigHeight = fdBigHeight;
	}

	public void setFdMediumWidth(Integer fdMediumWidth) {
		this.fdMediumWidth = fdMediumWidth;
	}

	public void setFdMediumHeight(Integer fdMediumHeight) {
		this.fdMediumHeight = fdMediumHeight;
	}

	public void setFdSmallWidth(Integer fdSmallWidth) {
		this.fdSmallWidth = fdSmallWidth;
	}

	public void setFdSmallHeight(Integer fdSmallHeight) {
		this.fdSmallHeight = fdSmallHeight;
	}

	public Integer[] getBigWH() {
		if (null == fdBigWidth || null == fdBigHeight) {
			return ImageCropUtil.B_SIZE;
		}
		return new Integer[] { fdBigWidth, fdBigHeight };
	}

	public Integer[] getMediumWH() {
		if (null == fdMediumWidth || null == fdMediumHeight) {
			return ImageCropUtil.M_SIZE;
		}
		return new Integer[] { fdMediumWidth, fdMediumHeight };
	}

	public Integer[] getSmallWH() {
		if (null == fdSmallWidth || null == fdSmallHeight) {
			return ImageCropUtil.S_SIZE;
		}
		return new Integer[] { fdSmallWidth, fdSmallHeight };
	}
	
	private String fdCropKeys;

	public String getFdCropKeys() {
		return fdCropKeys;
	}

	public void setFdCropKeys(String fdCropKeys) {
		this.fdCropKeys = fdCropKeys;
	}

	@Override
	public Class<?> getModelClass() {
		return null;
	}

}
