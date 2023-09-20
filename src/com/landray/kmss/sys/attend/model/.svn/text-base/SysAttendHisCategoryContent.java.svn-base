package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;

/**
  * 考勤组配置内容表
 * @author wj
  */
public class SysAttendHisCategoryContent extends BaseModel {
    /**
     * 考勤组配置内容
     */
    private String fdCategoryContent;

    private static ModelToFormPropertyMap toFormPropertyMap;

    @Override
    public Class getFormClass() {
        return null;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
        }
        return toFormPropertyMap;
    }

    /**
     * 配置内容
     */
    public String getFdCategoryContent() {
        return (String) readLazyField("fdCategoryContent", this.fdCategoryContent);
    }

    /**
     * 配置内容
     */
    public void setFdCategoryContent(String fdCategoryContent) {
        this.fdCategoryContent = (String) writeLazyField("fdCategoryContent", this.fdCategoryContent, fdCategoryContent);
    }
}
