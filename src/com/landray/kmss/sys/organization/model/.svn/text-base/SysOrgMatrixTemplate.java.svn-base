package com.landray.kmss.sys.organization.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.forms.SysOrgMatrixTemplateForm;

/**
 * 矩阵模板关联信息（记录矩阵与模板的关联关系）
 *
 * @author 潘永辉
 * @dataTime 2021年3月29日 下午3:20:38
 */
public class SysOrgMatrixTemplate extends BaseModel {

    /**
     * 流程模板ID
     */
    private String fdTemplateId;

    /**
     * 流程模板名称
     */
    private String fdTemplateName;

    /**
     * 流程模板KEY
     */
    private String fdKey;

    /**
     * 流程定义ID
     */
    private String fdProcessId;

    /**
     * 流程节点ID
     */
    private String fdNodeId;

    /**
     * 流程节点名称
     */
    private String fdNodeName;

    /**
     * 矩阵ID
     */
    private String fdMatrixId;

    /**
     * 矩阵版本
     */
    private String fdMatrixVersion;

    /**
     * 修改人
     */
    private SysOrgPerson fdModifier;

    /**
     * 修改时间
     */
    private Date fdModifyTime;

    @Override
    public Class getFormClass() {
        return SysOrgMatrixTemplateForm.class;
    }

    private static ModelToFormPropertyMap toFormPropertyMap;

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdModifier.fdId", "fdModifierId");
            toFormPropertyMap.put("fdModifier.fdName", "fdModifierName");
        }
        return toFormPropertyMap;
    }

    public String getFdTemplateId() {
        return fdTemplateId;
    }

    public void setFdTemplateId(String fdTemplateId) {
        this.fdTemplateId = fdTemplateId;
    }

    public String getFdTemplateName() {
        return fdTemplateName;
    }

    public void setFdTemplateName(String fdTemplateName) {
        this.fdTemplateName = fdTemplateName;
    }

    public String getFdKey() {
        return fdKey;
    }

    public void setFdKey(String fdKey) {
        this.fdKey = fdKey;
    }

    public String getFdProcessId() {
        return fdProcessId;
    }

    public void setFdProcessId(String fdProcessId) {
        this.fdProcessId = fdProcessId;
    }

    public String getFdNodeId() {
        return fdNodeId;
    }

    public void setFdNodeId(String fdNodeId) {
        this.fdNodeId = fdNodeId;
    }

    public String getFdNodeName() {
        return fdNodeName;
    }

    public void setFdNodeName(String fdNodeName) {
        this.fdNodeName = fdNodeName;
    }

    public String getFdMatrixId() {
        return fdMatrixId;
    }

    public void setFdMatrixId(String fdMatrixId) {
        this.fdMatrixId = fdMatrixId;
    }

    public String getFdMatrixVersion() {
        return fdMatrixVersion;
    }

    public void setFdMatrixVersion(String fdMatrixVersion) {
        this.fdMatrixVersion = fdMatrixVersion;
    }

    public SysOrgPerson getFdModifier() {
        return fdModifier;
    }

    public void setFdModifier(SysOrgPerson fdModifier) {
        this.fdModifier = fdModifier;
    }

    public Date getFdModifyTime() {
        return fdModifyTime;
    }

    public void setFdModifyTime(Date fdModifyTime) {
        this.fdModifyTime = fdModifyTime;
    }

}
