package com.landray.kmss.sys.attachment.integrate.wps.model;

import com.landray.kmss.common.model.BaseModel;

/**
  * wps加载项令牌
  */
@SuppressWarnings("serial")
public class ThirdWpsOfficeToken extends BaseModel {
   
	// 名称
    private String fdTokenName;
    // token信息
    private String fdToken;

    @Override
    public Class<ThirdWpsOfficeTokenForm> getFormClass() {
        return ThirdWpsOfficeTokenForm.class;
    }

 
    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 令牌
     */
    public String getFdToken() {
        return this.fdToken;
    }

    /**
     * 令牌
     */
    public void setFdToken(String fdToken) {
        this.fdToken = fdToken;
    }


	public String getFdTokenName() {
		return fdTokenName;
	}


	public void setFdTokenName(String fdTokenName) {
		this.fdTokenName = fdTokenName;
	}

   
}
