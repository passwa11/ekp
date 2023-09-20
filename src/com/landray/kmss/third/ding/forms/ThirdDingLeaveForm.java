package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.third.ding.model.ThirdDingLeavelog;
import com.landray.kmss.web.upload.FormFile;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingLeave;

/**
  * 请假明细
  */
public class ThirdDingLeaveForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdDate;

    private String fdDuration;

    private String docMainId;

    private String docMainName;

    private String docIndex;

    private FormFile file;

    private String fdImportType;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdDate = null;
        fdDuration = null;
        docIndex = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingLeave> getModelClass() {
        return ThirdDingLeave.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel("docMain", ThirdDingLeavelog.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 日期
     */
    public String getFdDate() {
        return this.fdDate;
    }

    /**
     * 日期
     */
    public void setFdDate(String fdDate) {
        this.fdDate = fdDate;
    }

    /**
     * 天数
     */
    public String getFdDuration() {
        return this.fdDuration;
    }

    /**
     * 天数
     */
    public void setFdDuration(String fdDuration) {
        this.fdDuration = fdDuration;
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
