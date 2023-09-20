package com.landray.kmss.sys.filestore.forms;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.filestore.model.SysFileViewerParam;

public class SysFileViewerParamForm extends ExtendForm {

	private static final long serialVersionUID = 2659845122765666195L;
	private String fdFileId;
	private String fdConverterKey;

	public String getFdConverterKey() {
		return fdConverterKey;
	}

	private String fdViewerKey;

	//此属性后续设计代码废弃，请使用fdParameterLong
	private String fdParameter;
	
	private String fdParameterLong;

	public String getFdFileId() {
		return fdFileId;
	}

	public void setFdFileId(String fdFileId) {
		this.fdFileId = fdFileId;
	}

	public String getFdViewerKey() {
		return fdViewerKey;
	}

	public void setFdViewerKey(String fdViewerKey) {
		this.fdViewerKey = fdViewerKey;
	}

	//此方法后续设计代码废弃，请使用getFdParameterLong
	public String getFdParameter() {
		return fdParameter;
	}

	//此方法后续设计代码废弃，请使用setFdParameterLong
	public void setFdParameter(String fdParameter) {
		this.fdParameter = fdParameter;
	}
	
	public String getFdParameterLong() {
		return fdParameterLong;
	}

	public void setFdParameterLong(String fdParameterLong) {
		this.fdParameterLong = fdParameterLong;
	}

	public void setFdConverterKey(String fdConverterKey) {
		this.fdConverterKey = fdConverterKey;
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

	@Override
	public Class getModelClass() {
		return SysFileViewerParam.class;
	}

}
