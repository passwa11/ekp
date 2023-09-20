package com.landray.kmss.third.feishu.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.third.feishu.forms.ThirdFeishuDeptMappingForm;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
  * 部门映射
  */
public class ThirdFeishuDeptMapping extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdFeishuId;

    private String fdFeishuName;

    private Date docAlterTime;

    private SysOrgElement fdEkp;

    @Override
    public Class<ThirdFeishuDeptMappingForm> getFormClass() {
        return ThirdFeishuDeptMappingForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdEkp.fdName", "fdEkpName");
            toFormPropertyMap.put("fdEkp.fdId", "fdEkpId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 飞书ID
     */
    public String getFdFeishuId() {
        return this.fdFeishuId;
    }

    /**
     * 飞书ID
     */
    public void setFdFeishuId(String fdFeishuId) {
        this.fdFeishuId = fdFeishuId;
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

    /**
     * ekp组织
     */
    public SysOrgElement getFdEkp() {
        return this.fdEkp;
    }

    /**
     * ekp组织
     */
    public void setFdEkp(SysOrgElement fdEkp) {
        this.fdEkp = fdEkp;
    }
}
