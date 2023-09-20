package com.landray.kmss.third.im.kk.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.im.kk.forms.ThirdImLoginForm;

/**
  * kk扫码登陆表
  */
public class ThirdImLogin extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;


    private String fdUuid;

    private String fdToken;

    private String fdLoginName;
    
    private Date docCreateTime;
    
    private Integer fdFlag;

    @Override
    public Class<ThirdImLoginForm> getFormClass() {
        return ThirdImLoginForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }


    /**
     * uuid
     */
    public String getFdUuid() {
        return this.fdUuid;
    }

    /**
     * uuid
     */
    public void setFdUuid(String fdUuid) {
        this.fdUuid = fdUuid;
    }

    /**
     * token
     */
    public String getFdToken() {
        return this.fdToken;
    }

    /**
     * token
     */
    public void setFdToken(String fdToken) {
        this.fdToken = fdToken;
    }

    /**
     * name
     */
    public String getFdLoginName() {
        return this.fdLoginName;
    }

    /**
     * name
     */
    public void setFdLoginName(String fdLoginName) {
        this.fdLoginName = fdLoginName;
    }

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public Integer getFdFlag() {
		return fdFlag;
	}

	public void setFdFlag(Integer fdFlag) {
		this.fdFlag = fdFlag;
	}

	
	
    
}
