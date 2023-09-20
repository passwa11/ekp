package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.ding.model.ThirdDingDinstanceXform;
import com.landray.kmss.third.ding.model.ThirdDingDtemplateXform;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 钉钉流程实例
  */
public class ThirdDingDinstanceXformForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdInstanceId;

    private String fdDingUserId;

    private String fdEkpInstanceId;

    private String fdUrl;

    private String docCreateTime;

    private String fdConfig;

    private String fdTemplateId;

    private String fdTemplateName;

    private String fdEkpUserId;

    private String fdEkpUserName;

	private String fdStatus;

	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	private AutoArrayList fdDetail_Form = new AutoArrayList(
			ThirdDingIndanceXDetailForm.class);

    private String fdDetail_Flag = "0";

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdInstanceId = null;
        fdDingUserId = null;
        fdEkpInstanceId = null;
        fdUrl = null;
        docCreateTime = null;
        fdConfig = null;
        fdTemplateId = null;
        fdTemplateName = null;
        fdEkpUserId = null;
        fdEkpUserName = null;
        fdDetail_Form = new AutoArrayList(ThirdDingIndanceXDetailForm.class);
        fdDetail_Flag = null;
		fdStatus = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingDinstanceXform> getModelClass() {
        return ThirdDingDinstanceXform.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdTemplateId", new FormConvertor_IDToModel("fdTemplate", ThirdDingDtemplateXform.class));
            toModelPropertyMap.put("fdEkpUserId", new FormConvertor_IDToModel("fdEkpUser", SysOrgElement.class));
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
     * 发起人dingId
     */
    public String getFdDingUserId() {
        return this.fdDingUserId;
    }

    /**
     * 发起人dingId
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
     * 配置信息
     */
    public String getFdConfig() {
        return this.fdConfig;
    }

    /**
     * 配置信息
     */
    public void setFdConfig(String fdConfig) {
        this.fdConfig = fdConfig;
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
    public String getFdEkpUserId() {
        return this.fdEkpUserId;
    }

    /**
     * 发起人
     */
    public void setFdEkpUserId(String fdEkpUserId) {
        this.fdEkpUserId = fdEkpUserId;
    }

    /**
     * 发起人
     */
    public String getFdEkpUserName() {
        return this.fdEkpUserName;
    }

    /**
     * 发起人
     */
    public void setFdEkpUserName(String fdEkpUserName) {
        this.fdEkpUserName = fdEkpUserName;
    }

    /**
     * 流程实例明细表
     */
    public AutoArrayList getFdDetail_Form() {
        return this.fdDetail_Form;
    }

    /**
     * 流程实例明细表
     */
    public void setFdDetail_Form(AutoArrayList fdDetail_Form) {
        this.fdDetail_Form = fdDetail_Form;
    }

    /**
     * 流程实例明细表
     */
    public String getFdDetail_Flag() {
        return this.fdDetail_Flag;
    }

    /**
     * 流程实例明细表
     */
    public void setFdDetail_Flag(String fdDetail_Flag) {
        this.fdDetail_Flag = fdDetail_Flag;
    }
}
