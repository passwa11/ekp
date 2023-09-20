package com.landray.kmss.hr.config.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.config.forms.HrConfigOvertimeConfigForm;
import com.landray.kmss.hr.organization.model.HrOrganizationRank;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

/**
  * 加班规则配置
  */
public class HrConfigOvertimeConfig extends BaseModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private Boolean fdIsAvailable;

    private Date docCreateTime;

    private String fdOvertimeType;

    private String fdOvertimeWelfare;

    private Integer fdWorkTime;

    private List<HrOrganizationRank> fdRank;

    private SysOrgPerson docCreator;

    private List<SysOrgElement> fdOrg = new ArrayList<SysOrgElement>();

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    public Class<HrConfigOvertimeConfigForm> getFormClass() {
        return HrConfigOvertimeConfigForm.class;
    }

    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdRank", new ModelConvertor_ModelListToString("fdRankds:fdRankNames", "fdId:fdName"));
            toFormPropertyMap.put("fdOrg", new ModelConvertor_ModelListToString("fdOrgIds:fdOrgNames", "fdId:fdName"));


        }
        return toFormPropertyMap;
    }

    public void recalculateFields() {
        super.recalculateFields();
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
    public Boolean getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    /**
     * 是否有效
     */
    public void setFdIsAvailable(Boolean fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
    }

    /**
     * 创建时间
     */
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(Date docCreateTime) {
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
    public Integer getFdWorkTime() {
        return this.fdWorkTime;
    }

    /**
     * 标准工作时长
     */
    public void setFdWorkTime(Integer fdWorkTime) {
        this.fdWorkTime = fdWorkTime;
    }


    public List<HrOrganizationRank> getFdRank() {
		return fdRank;
	}

	public void setFdRank(List<HrOrganizationRank> fdRank) {
		this.fdRank = fdRank;
	}

	/**
     * 创建人
     */
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 创建人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

    /**
     * 所属组织
     */
    public List<SysOrgElement> getFdOrg() {
		return fdOrg;
	}
    /**
     * 所属组织
     */
	public void setFdOrg(List<SysOrgElement> fdOrg) {
		this.fdOrg = fdOrg;
	}

	public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
