package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingDtemplateXform;
import com.landray.kmss.third.ding.model.ThirdDingTemplateXDetail;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
  * 流程模板明细
  */
public class ThirdDingTemplateXDetailForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdType;



    private String fdDocIndex;

    private String docMainId;

    private String docMainName;

    private String docIndex;

    private FormFile file;

    private String fdImportType;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdType = null;
        docMainId = null;
        docMainName = null;
        fdDocIndex = null;
        docIndex = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingTemplateXDetail> getModelClass() {
        return ThirdDingTemplateXDetail.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel("docMain", ThirdDingDtemplateXform.class));
            toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel("docMain", ThirdDingDtemplateXform.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 类型
     */
    public String getFdType() {
        return this.fdType;
    }

    /**
     * 类型
     */
    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

    /**
     * doc_main_id
     */
    public String getDocMainId() {
        return this.docMainId;
    }

    /**
     * doc_main_id
     */
    public void setDocMainId(String docMainId) {
        this.docMainId = docMainId;
    }

    /**
     * doc_main_id
     */
    public String getDocMainName() {
        return this.docMainName;
    }

    /**
     * doc_main_id
     */
    public void setDocMainName(String docMainName) {
        this.docMainName = docMainName;
    }

    /**
     * 排序
     */
    public String getFdDocIndex() {
        return this.fdDocIndex;
    }

    /**
     * 排序
     */
    public void setFdDocIndex(String fdDocIndex) {
        this.fdDocIndex = fdDocIndex;
    }

    public String getDocIndex() {
        return this.docIndex;
    }

    public void setDocIndex(String docIndex) {
        this.docIndex = docIndex;
    }

    public FormFile getFile() {
        return this.file;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }

    public String getFdImportType() {
        return this.fdImportType;
    }

    public void setFdImportType(String fdImportType) {
        this.fdImportType = fdImportType;
    }
}
