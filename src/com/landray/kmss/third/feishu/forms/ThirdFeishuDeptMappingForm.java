package com.landray.kmss.third.feishu.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.feishu.model.ThirdFeishuDeptMapping;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 部门映射
  */
public class ThirdFeishuDeptMappingForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdFeishuId;

    private String fdFeishuName;

    private String docAlterTime;

    private String fdEkpId;

    private String fdEkpName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdFeishuId = null;
        fdFeishuName = null;
        docAlterTime = null;
        fdEkpId = null;
        fdEkpName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdFeishuDeptMapping> getModelClass() {
        return ThirdFeishuDeptMapping.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
			toModelPropertyMap.put("fdEkpId",
					new FormConvertor_IDToModel("fdEkp",
							SysOrgElement.class));
        }
        return toModelPropertyMap;
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
    public String getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(String docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    /**
     * ekp组织
     */
    public String getFdEkpId() {
        return this.fdEkpId;
    }

    /**
     * ekp组织
     */
    public void setFdEkpId(String fdEkpId) {
        this.fdEkpId = fdEkpId;
    }

    /**
     * ekp组织
     */
    public String getFdEkpName() {
        return this.fdEkpName;
    }

    /**
     * ekp组织
     */
    public void setFdEkpName(String fdEkpName) {
        this.fdEkpName = fdEkpName;
    }
}
