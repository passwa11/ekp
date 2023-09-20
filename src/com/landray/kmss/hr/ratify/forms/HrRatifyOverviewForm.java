package com.landray.kmss.hr.ratify.forms;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.ratify.model.HrRatifyOverview;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 流程分类概览
  */
public class HrRatifyOverviewForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdPreContent;

    private String docAlterTime;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdPreContent = null;
        docAlterTime = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<HrRatifyOverview> getModelClass() {
        return HrRatifyOverview.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
        }
        return toModelPropertyMap;
    }

    /**
     * 概览内容
     */
    public String getFdPreContent() {
        return this.fdPreContent;
    }

    /**
     * 概览内容
     */
    public void setFdPreContent(String fdPreContent) {
        this.fdPreContent = fdPreContent;
    }

    /**
     * 更新时间
     */
    public String getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(String docAlterTime) {
        this.docAlterTime = docAlterTime;
    }
}
