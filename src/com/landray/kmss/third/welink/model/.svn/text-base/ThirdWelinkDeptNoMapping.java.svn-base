package com.landray.kmss.third.welink.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.third.welink.forms.ThirdWelinkDeptNoMappingForm;
import com.landray.kmss.common.model.BaseModel;

/**
  * 部门未匹配数据
  */
public class ThirdWelinkDeptNoMapping extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdWelinkPath;

    private String fdWelinkName;

    private String fdWelinkId;

    private Date docAlterTime;

    @Override
    public Class<ThirdWelinkDeptNoMappingForm> getFormClass() {
        return ThirdWelinkDeptNoMappingForm.class;
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
