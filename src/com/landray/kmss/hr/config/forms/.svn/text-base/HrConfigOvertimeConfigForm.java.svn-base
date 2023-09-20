package com.landray.kmss.hr.config.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.config.model.HrConfigOvertimeConfig;
import com.landray.kmss.hr.organization.model.HrOrganizationRank;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 加班规则配置
  */
public class HrConfigOvertimeConfigForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdIsAvailable;

    private String docCreateTime;

    private String fdOvertimeType;

    private String fdOvertimeWelfare;

    private String fdWorkTime;

    private String docCreatorId;

    private String docCreatorName;

    private String fdOrgIds;

    private String fdOrgNames;
    
    private String fdRankIds;

    private String fdRankNames;
    

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdName = null;
        fdIsAvailable = null;
        docCreateTime = null;
        fdOvertimeType = null;
        fdOvertimeWelfare = null;
        fdWorkTime = null;
        docCreatorId = null;
        docCreatorName = null;
        fdOrgIds = null;
        fdOrgNames = null;
        fdRankIds=null;
        fdRankNames=null;
        super.reset(mapping, request);
    }

    public Class<HrConfigOvertimeConfig> getModelClass() {
        return HrConfigOvertimeConfig.class;
    }

    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdRankIds", new FormConvertor_IDsToModelList("fdRank", HrOrganizationRank.class));
            toModelPropertyMap.put("fdOrgIds", new FormConvertor_IDsToModelList("fdOrg", SysOrgElement.class));


        }
        return toModelPropertyMap;
    }

    /**
     * 规则名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 规则名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 是否有效
     */
    public String getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    /**
     * 是否有效
     */
    public void setFdIsAvailable(String fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
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
     * 加班类别
     */
    public String getFdOvertimeType() {
        return this.fdOvertimeType;
    }

    /**
     * 加班类别
     */
    public void setFdOvertimeType(String fdOvertimeType) {
        this.fdOvertimeType = fdOvertimeType;
    }

    /**
     * 加班补偿
     */
    public String getFdOvertimeWelfare() {
        return this.fdOvertimeWelfare;
    }

    /**
     * 加班补偿
     */
    public void setFdOvertimeWelfare(String fdOvertimeWelfare) {
        this.fdOvertimeWelfare = fdOvertimeWelfare;
    }

    /**
     * 标准工作时长
     */
    public String getFdWorkTime() {
        return this.fdWorkTime;
    }

    /**
     * 标准工作时长
     */
    public void setFdWorkTime(String fdWorkTime) {
        this.fdWorkTime = fdWorkTime;
    }


    public String getFdRankIds() {
		return fdRankIds;
	}

	public void setFdRankIds(String fdRankIds) {
		this.fdRankIds = fdRankIds;
	}

	public String getFdRankNames() {
		return fdRankNames;
	}

	public void setFdRankNames(String fdRankNames) {
		this.fdRankNames = fdRankNames;
	}

	/**
     * 创建人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 创建人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 创建人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 创建人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }


    public String getFdOrgIds() {
		return fdOrgIds;
	}

	public void setFdOrgIds(String fdOrgIds) {
		this.fdOrgIds = fdOrgIds;
	}

	public String getFdOrgNames() {
		return fdOrgNames;
	}

	public void setFdOrgNames(String fdOrgNames) {
		this.fdOrgNames = fdOrgNames;
	}

	public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
