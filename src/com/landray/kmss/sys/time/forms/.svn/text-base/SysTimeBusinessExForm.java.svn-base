package com.landray.kmss.sys.time.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.model.SysTimeBusinessEx;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;

/**
  * 换班流程信息
  */
public class SysTimeBusinessExForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String fdReturnDate;

    private String fdExchangeDate;

    private String fdStatus;

    private String fdMsg;

    private String fdFlowId;

    private String fdFlowName;

    private String fdChangeType;

    private String docCreatorId;

    private String docCreatorName;

    private String fdApplyPersonId;

    private String fdApplyPersonName;

    private String fdExchangePersonId;

    private String fdExchangePersonName;
    
    private String fdDocUrl;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        docCreateTime = null;
        fdReturnDate = null;
        fdExchangeDate = null;
        fdStatus = null;
        fdMsg = null;
        fdFlowId = null;
        fdFlowName = null;
        fdChangeType = null;
        docCreatorId = null;
        docCreatorName = null;
        fdApplyPersonId = null;
        fdApplyPersonName = null;
        fdExchangePersonId = null;
        fdExchangePersonName = null;
        fdDocUrl = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<SysTimeBusinessEx> getModelClass() {
        return SysTimeBusinessEx.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdReturnDate", new FormConvertor_Common("fdReturnDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdExchangeDate", new FormConvertor_Common("fdExchangeDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdApplyPersonId", new FormConvertor_IDToModel("fdApplyPerson", SysOrgElement.class));
            toModelPropertyMap.put("fdExchangePersonId", new FormConvertor_IDToModel("fdExchangePersonId", SysOrgElement.class));
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
     * 还班日期
     */
    public String getFdReturnDate() {
        return this.fdReturnDate;
    }

    /**
     * 还班日期
     */
    public void setFdReturnDate(String fdReturnDate) {
        this.fdReturnDate = fdReturnDate;
    }

    /**
     * 换班时间
     */
    public String getFdExchangeDate() {
        return this.fdExchangeDate;
    }

    /**
     * 换班时间
     */
    public void setFdExchangeDate(String fdExchangeDate) {
        this.fdExchangeDate = fdExchangeDate;
    }

    /**
     * 状态
     */
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态
     */
    public void setFdStatus(String fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 备注
     */
    public String getFdMsg() {
        return this.fdMsg;
    }

    /**
     * 备注
     */
    public void setFdMsg(String fdMsg) {
        this.fdMsg = fdMsg;
    }

    /**
     * 流程id
     */
    public String getFdFlowId() {
        return this.fdFlowId;
    }

    /**
     * 流程id
     */
    public void setFdFlowId(String fdFlowId) {
        this.fdFlowId = fdFlowId;
    }

    /**
     * 流程名称
     */
    public String getFdFlowName() {
        return this.fdFlowName;
    }

    /**
     * 流程名称
     */
    public void setFdFlowName(String fdFlowName) {
        this.fdFlowName = fdFlowName;
    }

    /**
     * 类型
     */
    public String getFdChangeType() {
        return this.fdChangeType;
    }

    /**
     * 类型
     */
    public void setFdChangeType(String fdChangeType) {
        this.fdChangeType = fdChangeType;
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

    /**
     * 申请人
     */
    public String getFdApplyPersonId() {
        return this.fdApplyPersonId;
    }

    /**
     * 申请人
     */
    public void setFdApplyPersonId(String fdApplyPersonId) {
        this.fdApplyPersonId = fdApplyPersonId;
    }

    /**
     * 申请人
     */
    public String getFdApplyPersonName() {
        return this.fdApplyPersonName;
    }

    /**
     * 申请人
     */
    public void setFdApplyPersonName(String fdApplyPersonName) {
        this.fdApplyPersonName = fdApplyPersonName;
    }

    /**
     * 替班人
     */
    public String getFdExchangePersonId() {
        return this.fdExchangePersonId;
    }

    /**
     * 替班人
     */
    public void setFdExchangePersonId(String fdExchangePersonId) {
        this.fdExchangePersonId = fdExchangePersonId;
    }

    /**
     * 替班人
     */
    public String getFdExchangePersonName() {
        return this.fdExchangePersonName;
    }

    /**
     * 替班人
     */
    public void setFdExchangePersonName(String fdExchangePersonName) {
        this.fdExchangePersonName = fdExchangePersonName;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

	public String getFdDocUrl() {
		return fdDocUrl;
	}

	public void setFdDocUrl(String fdDocUrl) {
		this.fdDocUrl = fdDocUrl;
	}
}
