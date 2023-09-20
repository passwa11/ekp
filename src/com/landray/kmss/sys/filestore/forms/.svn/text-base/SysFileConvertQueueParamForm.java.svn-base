package com.landray.kmss.sys.filestore.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.util.StringUtil;

public class SysFileConvertQueueParamForm extends ExtendForm {
	private static final long serialVersionUID = -1183881295282455377L;
	private String picResolution;
	private String picRectangle;
	private String converterType;
	private String highFidelity;
	private String queueIds = "";
	private String containsHighFidelity = "false";

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getModelClass() {
		return null;
	}

	public String getPicResolution() {
		return StringUtil.isNotNull(picResolution) ? picResolution : "96";
	}

	public void setPicResolution(String picResolution) {
		this.picResolution = picResolution;
	}

	public String getQueueIds() {
		return queueIds;
	}

	public void setQueueIds(String queueIds) {
		this.queueIds = queueIds;
	}

	public String getConverterType() {
		return StringUtil.isNotNull(converterType) ? converterType : "aspose";
	}

	public void setConverterType(String converterType) {
		this.converterType = converterType;
	}

	public String getHighFidelity() {
		return highFidelity;
	}

	public void setHighFidelity(String highFidelity) {
		this.highFidelity = highFidelity;
	}

	public String getContainsHighFidelity() {
		return containsHighFidelity;
	}

	public void setContainsHighFidelity(String containsHighFidelity) {
		this.containsHighFidelity = containsHighFidelity;
	}

	public String getPicRectangle() {
		return StringUtil.isNotNull(picRectangle) ? picRectangle : "A3";
	}

	public void setPicRectangle(String picRectangle) {
		this.picRectangle = picRectangle;
	}

}
