package com.landray.kmss.third.welink.forms;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.welink.model.ThirdWelinkDeptNoMapping;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 部门未匹配数据
  */
public class ThirdWelinkDeptNoMappingForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdWelinkPath;

    private String fdWelinkName;

    private String fdWelinkId;

    private String docAlterTime;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdWelinkPath = null;
        fdWelinkName = null;
        fdWelinkId = null;
        docAlterTime = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWelinkDeptNoMapping> getModelClass() {
        return ThirdWelinkDeptNoMapping.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("fdWelinkName");
            toModelPropertyMap.addNoConvertProperty("fdWelinkId");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
        }
        return toModelPropertyMap;
    }

    /**
     * welink部门路径
     */
    public String getFdWelinkPath() {
        return this.fdWelinkPath;
    }

    /**
     * welink部门路径
     */
    public void setFdWelinkPath(String fdWelinkPath) {
        this.fdWelinkPath = fdWelinkPath;
    }

    /**
     * welink部门名称
     */
    public String getFdWelinkName() {
        return this.fdWelinkName;
    }

    /**
     * welink部门名称
     */
    public void setFdWelinkName(String fdWelinkName) {
        this.fdWelinkName = fdWelinkName;
    }

    /**
     * welink部门ID
     */
    public String getFdWelinkId() {
        return this.fdWelinkId;
    }

    /**
     * welink部门ID
     */
    public void setFdWelinkId(String fdWelinkId) {
        this.fdWelinkId = fdWelinkId;
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
