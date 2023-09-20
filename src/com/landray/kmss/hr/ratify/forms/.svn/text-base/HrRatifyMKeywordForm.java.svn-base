package com.landray.kmss.hr.ratify.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.ratify.model.HrRatifyMKeyword;

/**
  * 人事流程文档关键字
  */
public class HrRatifyMKeywordForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docKeyword;

    private String fdObjectId;

    private String fdObjectName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docKeyword = null;
        fdObjectId = null;
        fdObjectName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<HrRatifyMKeyword> getModelClass() {
        return HrRatifyMKeyword.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdObjectId", new FormConvertor_IDToModel("fdObject", HrRatifyMain.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 关键字
     */
    public String getDocKeyword() {
        return this.docKeyword;
    }

    /**
     * 关键字
     */
    public void setDocKeyword(String docKeyword) {
        this.docKeyword = docKeyword;
    }

    /**
     * 审批文档基本信息
     */
    public String getFdObjectId() {
        return this.fdObjectId;
    }

    /**
     * 审批文档基本信息
     */
    public void setFdObjectId(String fdObjectId) {
        this.fdObjectId = fdObjectId;
    }

    /**
     * 审批文档基本信息
     */
    public String getFdObjectName() {
        return this.fdObjectName;
    }

    /**
     * 审批文档基本信息
     */
    public void setFdObjectName(String fdObjectName) {
        this.fdObjectName = fdObjectName;
    }
}
