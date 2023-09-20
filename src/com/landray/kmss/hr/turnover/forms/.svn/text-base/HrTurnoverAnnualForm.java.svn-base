package com.landray.kmss.hr.turnover.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.hr.turnover.model.HrTurnoverAnnual;

/**
  * 年度离职率目标值
  */
public class HrTurnoverAnnualForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String fdYear;

    private String fdRateO;

    private String fdDesc;

    private String fdRateP;

    private String fdRateS;

    private String fdRateM;

    private String docCreatorId;

    private String docCreatorName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        docCreateTime = null;
        fdYear = null;
        fdRateO = null;
        fdDesc = null;
        fdRateP = null;
        fdRateS = null;
        fdRateM = null;
        docCreatorId = null;
        docCreatorName = null;
        super.reset(mapping, request);
    }

    public Class<HrTurnoverAnnual> getModelClass() {
        return HrTurnoverAnnual.class;
    }

    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
        }
        return toModelPropertyMap;
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
     * 年度
     */
    public String getFdYear() {
        return this.fdYear;
    }

    /**
     * 年度
     */
    public void setFdYear(String fdYear) {
        this.fdYear = fdYear;
    }

    /**
     * O类离职率目标值
     */
    public String getFdRateO() {
        return this.fdRateO;
    }

    /**
     * O类离职率目标值
     */
    public void setFdRateO(String fdRateO) {
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
    public String getFdRateP() {
        return this.fdRateP;
    }

    /**
     * P类离职率目标值
     */
    public void setFdRateP(String fdRateP) {
        this.fdRateP = fdRateP;
    }

    /**
     * S类离职率目标值
     */
    public String getFdRateS() {
        return this.fdRateS;
    }

    /**
     * S类离职率目标值
     */
    public void setFdRateS(String fdRateS) {
        this.fdRateS = fdRateS;
    }

    /**
     * M类离职率目标值
     */
    public String getFdRateM() {
        return this.fdRateM;
    }

    /**
     * M类离职率目标值
     */
    public void setFdRateM(String fdRateM) {
        this.fdRateM = fdRateM;
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

    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
