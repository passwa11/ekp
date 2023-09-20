package com.landray.kmss.sys.filestore.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.filestore.forms.SysFileViewerParamForm;

public class SysFileViewerParam extends BaseModel {

	private static final long serialVersionUID = 8704750256297340219L;

	private String fdFileId;
	private String fdAttMainId;
	private String fdConverterKey;
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

	public String getFdAttMainId() {
		return fdAttMainId;
	}

	public void setFdAttMainId(String fdAttMainId) {
		this.fdAttMainId = fdAttMainId;
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

	public String getFdConverterKey() {
		return fdConverterKey;
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

	@Override
	public Class getFormClass() {
		return SysFileViewerParamForm.class;
	}

}
