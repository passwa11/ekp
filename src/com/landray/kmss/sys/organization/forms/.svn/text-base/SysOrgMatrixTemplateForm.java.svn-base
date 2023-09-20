package com.landray.kmss.sys.organization.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgMatrixTemplate;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 矩阵模板关联信息（记录矩阵与模板的关联关系）
 *
 * @author 潘永辉
 * @dataTime 2021年3月29日 下午3:30:29
 */
public class SysOrgMatrixTemplateForm extends ExtendForm {

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
    private String fdModifierId;
    private String fdModifierName;

    /**
     * 修改时间
     */
    private Date fdModifyTime;

    @Override
    public Class getModelClass() {
        return SysOrgMatrixTemplate.class;
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

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdTemplateId = null;
        fdTemplateName = null;
        fdKey = null;
        fdProcessId = null;
        fdNodeId = null;
        fdNodeName = null;
        fdMatrixId = null;
        fdMatrixVersion = null;
        fdModifierId = null;
        fdModifierName = null;
        fdModifyTime = null;
        super.reset(mapping, request);
    }

    private static FormToModelPropertyMap toModelPropertyMap;

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdModifierId", new FormConvertor_IDToModel("fdModifier", SysOrgPerson.class));
        }
        return toModelPropertyMap;
    }

}
