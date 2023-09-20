package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.third.ding.model.ThirdDingDinstance;
import com.landray.kmss.web.upload.FormFile;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingInstanceDetail;

/**
  * 钉钉实例明细表
  */
public class ThirdDingInstanceDetailForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdType;

    private String fdValue;

    private String docMainId;

    private String docMainName;

    private String docIndex;

    private FormFile file;

    private String fdImportType;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdType = null;
        fdValue = null;
        docIndex = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingInstanceDetail> getModelClass() {
        return ThirdDingInstanceDetail.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel("docMain", ThirdDingDinstance.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 控件名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 控件名称
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
     * 控件值
     */
    public String getFdValue() {
        return this.fdValue;
    }

    /**
     * 控件值
     */
    public void setFdValue(String fdValue) {
        this.fdValue = fdValue;
    }

    public String getDocMainId() {
        return this.docMainId;
    }

    public void setDocMainId(String docMainId) {
        this.docMainId = docMainId;
    }

    public String getDocMainName() {
        return this.docMainName;
    }

    public void setDocMainName(String docMainName) {
        this.docMainName = docMainName;
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
