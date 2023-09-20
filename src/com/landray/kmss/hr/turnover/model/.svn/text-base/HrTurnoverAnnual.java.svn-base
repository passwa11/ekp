package com.landray.kmss.hr.turnover.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.hr.turnover.forms.HrTurnoverAnnualForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;

/**
  * 年度离职率目标值
  */
public class HrTurnoverAnnual extends BaseModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date docCreateTime;

    private Integer fdYear;

    private Double fdRateO;

    private String fdDesc;

    private Double fdRateP;

    private Double fdRateS;

    private Double fdRateM;

    private SysOrgPerson docCreator;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    public Class<HrTurnoverAnnualForm> getFormClass() {
        return HrTurnoverAnnualForm.class;
    }

    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
        }
        return toFormPropertyMap;
    }

    public void recalculateFields() {
        super.recalculateFields();
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
     * 年度
     */
    public Integer getFdYear() {
        return this.fdYear;
    }

    /**
     * 年度
     */
    public void setFdYear(Integer fdYear) {
        this.fdYear = fdYear;
    }

    /**
     * O类离职率目标值
     */
    public Double getFdRateO() {
        return this.fdRateO;
    }

    /**
     * O类离职率目标值
     */
    public void setFdRateO(Double fdRateO) {
        this.fdRateO = fdRateO;
    }

    /**
     * 描述
     */
    public String getFdDesc() {
        return this.fdDesc;
    }

    /**
     * 描述
     */
    public void setFdDesc(String fdDesc) {
        this.fdDesc = fdDesc;
    }

    /**
     * P类离职率目标值
     */
    public Double getFdRateP() {
        return this.fdRateP;
    }

    /**
     * P类离职率目标值
     */
    public void setFdRateP(Double fdRateP) {
        this.fdRateP = fdRateP;
    }

    /**
     * S类离职率目标值
     */
    public Double getFdRateS() {
        return this.fdRateS;
    }

    /**
     * S类离职率目标值
     */
    public void setFdRateS(Double fdRateS) {
        this.fdRateS = fdRateS;
    }

    /**
     * M类离职率目标值
     */
    public Double getFdRateM() {
        return this.fdRateM;
    }

    /**
     * M类离职率目标值
     */
    public void setFdRateM(Double fdRateM) {
        this.fdRateM = fdRateM;
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

    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
