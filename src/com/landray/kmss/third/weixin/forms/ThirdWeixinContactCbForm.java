package com.landray.kmss.third.weixin.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.weixin.model.ThirdWeixinContactCb;

/**
  * 客户回调日志
  */
public class ThirdWeixinContactCbForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdEventType;

    private String fdEventContent;

    private String fdRecordId;

    private String fdHandleResult;

    private String docCreateTime;

    private String docAlterTime;

    private String fdErrorMsg;

    private String fdCorpId;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdEventType = null;
        fdEventContent = null;
        fdRecordId = null;
        fdHandleResult = null;
        docCreateTime = null;
        docAlterTime = null;
        fdErrorMsg = null;
        fdCorpId = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinContactCb> getModelClass() {
        return ThirdWeixinContactCb.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
        }
        return toModelPropertyMap;
    }

    /**
     * 事件类型
     */
    public String getFdEventType() {
        return this.fdEventType;
    }

    /**
     * 事件类型
     */
    public void setFdEventType(String fdEventType) {
        this.fdEventType = fdEventType;
    }

    /**
     * 报文
     */
    public String getFdEventContent() {
        return this.fdEventContent;
    }

    /**
     * 报文
     */
    public void setFdEventContent(String fdEventContent) {
        this.fdEventContent = fdEventContent;
    }

    /**
     * 记录ID
     */
    public String getFdRecordId() {
        return this.fdRecordId;
    }

    /**
     * 记录ID
     */
    public void setFdRecordId(String fdRecordId) {
        this.fdRecordId = fdRecordId;
    }

    /**
     * 处理结果
     */
    public String getFdHandleResult() {
        return this.fdHandleResult;
    }

    /**
     * 处理结果
     */
    public void setFdHandleResult(String fdHandleResult) {
        this.fdHandleResult = fdHandleResult;
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
     * 错误信息
     */
    public String getFdErrorMsg() {
        return this.fdErrorMsg;
    }

    /**
     * 错误信息
     */
    public void setFdErrorMsg(String fdErrorMsg) {
        this.fdErrorMsg = fdErrorMsg;
    }

    /**
     * 微信CorpID
     */
    public String getFdCorpId() {
        return this.fdCorpId;
    }

    /**
     * 微信CorpID
     */
    public void setFdCorpId(String fdCorpId) {
        this.fdCorpId = fdCorpId;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
