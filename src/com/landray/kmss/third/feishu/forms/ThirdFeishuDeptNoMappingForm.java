package com.landray.kmss.third.feishu.forms;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.feishu.model.ThirdFeishuDeptNoMapping;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 部门未匹配数据
  */
public class ThirdFeishuDeptNoMappingForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdFeishuPath;

    private String fdFeishuName;

    private String fdFeishuId;

    private String docAlterTime;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdFeishuPath = null;
        fdFeishuName = null;
        fdFeishuId = null;
        docAlterTime = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdFeishuDeptNoMapping> getModelClass() {
        return ThirdFeishuDeptNoMapping.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("fdFeishuPath");
            toModelPropertyMap.addNoConvertProperty("fdFeishuName");
            toModelPropertyMap.addNoConvertProperty("fdFeishuId");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
        }
        return toModelPropertyMap;
    }

    /**
     * 飞书部门路径
     */
    public String getFdFeishuPath() {
        return this.fdFeishuPath;
    }

    /**
     * 飞书部门路径
     */
    public void setFdFeishuPath(String fdFeishuPath) {
        this.fdFeishuPath = fdFeishuPath;
    }

    /**
     * 飞书部门名称
     */
    public String getFdFeishuName() {
        return this.fdFeishuName;
    }

    /**
     * 飞书部门名称
     */
    public void setFdFeishuName(String fdFeishuName) {
        this.fdFeishuName = fdFeishuName;
    }

    /**
     * 飞书部门ID
     */
    public String getFdFeishuId() {
        return this.fdFeishuId;
    }

    /**
     * 飞书部门ID
     */
    public void setFdFeishuId(String fdFeishuId) {
        this.fdFeishuId = fdFeishuId;
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
