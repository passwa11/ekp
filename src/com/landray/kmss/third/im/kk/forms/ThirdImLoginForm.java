package com.landray.kmss.third.im.kk.forms;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.im.kk.model.ThirdImLogin;
import com.landray.kmss.web.action.ActionMapping;

/**
  * kk扫码登陆表
  */
public class ThirdImLoginForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdId;

    private String fdUuid;

    private String fdToken;

    private String fdLoginName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdId = null;
        fdUuid = null;
        fdToken = null;
        fdLoginName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdImLogin> getModelClass() {
        return ThirdImLogin.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
        }
        return toModelPropertyMap;
    }

    /**
     * id
     */
    @Override
    public String getFdId() {
        return this.fdId;
    }

    /**
     * id
     */
    @Override
    public void setFdId(String fdId) {
        this.fdId = fdId;
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
}
