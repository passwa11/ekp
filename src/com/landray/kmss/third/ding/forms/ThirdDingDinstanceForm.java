package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.third.ding.model.ThirdDingDtemplate;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingDinstance;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
  * 钉钉待办实例
  */
public class ThirdDingDinstanceForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdInstanceId;

    private String fdDingUserId;

    private String fdEkpInstanceId;

    private String fdUrl;

    private String docCreateTime;

    private String fdTemplateId;

    private String fdTemplateName;

    private String fdCreatorId;

    private String fdCreatorName;

    private AutoArrayList fdDetail_Form = new AutoArrayList(ThirdDingInstanceDetailForm.class);

    private String fdDetail_Flag = "0";

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdInstanceId = null;
        fdDingUserId = null;
        fdEkpInstanceId = null;
        fdUrl = null;
        docCreateTime = null;
        fdTemplateId = null;
        fdTemplateName = null;
        fdCreatorId = null;
        fdCreatorName = null;
        fdDetail_Form = new AutoArrayList(ThirdDingInstanceDetailForm.class);
        fdDetail_Flag = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingDinstance> getModelClass() {
        return ThirdDingDinstance.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdTemplateId", new FormConvertor_IDToModel("fdTemplate", ThirdDingDtemplate.class));
            toModelPropertyMap.put("fdCreatorId", new FormConvertor_IDToModel("fdCreator", SysOrgElement.class));
            toModelPropertyMap.put("fdDetail_Form", new FormConvertor_FormListToModelList("fdDetail", "docMain", "fdDetail_Flag"));
        }
        return toModelPropertyMap;
    }

    /**
     * 名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 实例Id
     */
    public String getFdInstanceId() {
        return this.fdInstanceId;
    }

    /**
     * 实例Id
     */
    public void setFdInstanceId(String fdInstanceId) {
        this.fdInstanceId = fdInstanceId;
    }

    /**
     * 钉钉接收人
     */
    public String getFdDingUserId() {
        return this.fdDingUserId;
    }

    /**
     * 钉钉接收人
     */
    public void setFdDingUserId(String fdDingUserId) {
        this.fdDingUserId = fdDingUserId;
    }

    /**
     * 文档Id
     */
    public String getFdEkpInstanceId() {
        return this.fdEkpInstanceId;
    }

    /**
     * 文档Id
     */
    public void setFdEkpInstanceId(String fdEkpInstanceId) {
        this.fdEkpInstanceId = fdEkpInstanceId;
    }

    /**
     * 文档地址
     */
    public String getFdUrl() {
        return this.fdUrl;
    }

    /**
     * 文档地址
     */
    public void setFdUrl(String fdUrl) {
        this.fdUrl = fdUrl;
    }

    /**
     * 创建时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 所属模板
     */
    public String getFdTemplateId() {
        return this.fdTemplateId;
    }

    /**
     * 所属模板
     */
    public void setFdTemplateId(String fdTemplateId) {
        this.fdTemplateId = fdTemplateId;
    }

    /**
     * 所属模板
     */
    public String getFdTemplateName() {
        return this.fdTemplateName;
    }

    /**
     * 所属模板
     */
    public void setFdTemplateName(String fdTemplateName) {
        this.fdTemplateName = fdTemplateName;
    }

    /**
     * 发起人
     */
    public String getFdCreatorId() {
        return this.fdCreatorId;
    }

    /**
     * 发起人
     */
    public void setFdCreatorId(String fdCreatorId) {
        this.fdCreatorId = fdCreatorId;
    }

    /**
     * 发起人
     */
    public String getFdCreatorName() {
        return this.fdCreatorName;
    }

    /**
     * 发起人
     */
    public void setFdCreatorName(String fdCreatorName) {
        this.fdCreatorName = fdCreatorName;
    }

    /**
     * 钉钉实例明细表
     */
    public AutoArrayList getFdDetail_Form() {
        return this.fdDetail_Form;
    }

    /**
     * 钉钉实例明细表
     */
    public void setFdDetail_Form(AutoArrayList fdDetail_Form) {
        this.fdDetail_Form = fdDetail_Form;
    }

    /**
     * 钉钉实例明细表
     */
    public String getFdDetail_Flag() {
        return this.fdDetail_Flag;
    }

    /**
     * 钉钉实例明细表
     */
    public void setFdDetail_Flag(String fdDetail_Flag) {
        this.fdDetail_Flag = fdDetail_Flag;
    }
}
