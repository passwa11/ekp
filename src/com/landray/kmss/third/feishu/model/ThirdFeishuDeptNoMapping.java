package com.landray.kmss.third.feishu.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.third.feishu.forms.ThirdFeishuDeptNoMappingForm;
import com.landray.kmss.common.model.BaseModel;

/**
  * 部门未匹配数据
  */
public class ThirdFeishuDeptNoMapping extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdFeishuPath;

    private String fdFeishuName;

    private String fdFeishuId;

    private Date docAlterTime;

    @Override
    public Class<ThirdFeishuDeptNoMappingForm> getFormClass() {
        return ThirdFeishuDeptNoMappingForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
    public Date getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
    }
}
