package com.landray.kmss.common.model;

import com.landray.kmss.util.ModelUtil;

public abstract class BaseCoreInnerModel extends BaseModel implements IBaseCoreInnerModel {
	/**
     * 
     */
    private static final long serialVersionUID = 8465917385619356L;
    /*
	 * 主表域模型
	 */
	private String fdModelName;

	@Override
    public String getFdModelName() {
		return fdModelName;
	}

	@Override
    public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	/*
	 * 主表ID
	 */
	private String fdModelId;

	@Override
    public String getFdModelId() {
		return fdModelId;
	}

	@Override
    public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	/*
	 * 关键字
	 */
	private String fdKey;

	@Override
    public String getFdKey() {
		return fdKey;
	}

	@Override
    public void setFdKey(String fdKey) {
		this.fdKey = fdKey;
	}
	
	@Override
	public String getMechanismName(){
	    String modelClassName = ModelUtil.getModelClassName(this);
        int lastIndexOf = modelClassName.lastIndexOf('.');
        modelClassName = modelClassName.substring(lastIndexOf+1);
	    return modelClassName; 
	}
}
