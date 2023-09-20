package com.landray.kmss.sys.oms.forms;

import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.oms.model.SysOmsTempConfig;

/**
  * 同步配置表
  */
public class SysOmsTempConfigForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdSynStatus;

    private String fdSynTimestamp;

    private String fdExtra;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdSynStatus = null;
        fdSynTimestamp = null;
        fdExtra = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<SysOmsTempConfig> getModelClass() {
        return SysOmsTempConfig.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdSynTimestamp", new FormConvertor_Common("fdSynTimestamp").setDateTimeType(DateUtil.TYPE_DATE));
        }
        return toModelPropertyMap;
    }

    /**
     * 同步状态
     */
    public String getFdSynStatus() {
        return this.fdSynStatus;
    }

    /**
     * 同步状态
     */
    public void setFdSynStatus(String fdSynStatus) {
        this.fdSynStatus = fdSynStatus;
    }

    /**
     * 同步时间戳
     */
    public String getFdSynTimestamp() {
        return this.fdSynTimestamp;
    }

    /**
     * 同步时间戳
     */
    public void setFdSynTimestamp(String fdSynTimestamp) {
        this.fdSynTimestamp = fdSynTimestamp;
    }

    /**
     * 扩展字段
     */
    public String getFdExtra() {
        return this.fdExtra;
    }

    /**
     * 扩展字段
     */
    public void setFdExtra(String fdExtra) {
        this.fdExtra = fdExtra;
    }
}
