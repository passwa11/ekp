package com.landray.kmss.third.weixin.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataTemp;

/**
  * 会话同步临时表
  */
public class ThirdWeixinChatDataTempForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdSeq;

    private String fdMsgId;

    private String fdPublickeyVer;

    private String fdEncryptRandomKey;

    private String fdEncryptChatMsg;

    private String fdHandleStatus;

    private String fdErrTimes;

    private String fdErrMsg;

    private String docCreateTime;

    private String docAlterTime;

    private String fdCorpId;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdSeq = null;
        fdMsgId = null;
        fdPublickeyVer = null;
        fdEncryptRandomKey = null;
        fdEncryptChatMsg = null;
        fdHandleStatus = null;
        fdErrTimes = null;
        fdErrMsg = null;
        docCreateTime = null;
        docAlterTime = null;
        fdCorpId = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinChatDataTemp> getModelClass() {
        return ThirdWeixinChatDataTemp.class;
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
     * 序号
     */
    public String getFdSeq() {
        return this.fdSeq;
    }

    /**
     * 序号
     */
    public void setFdSeq(String fdSeq) {
        this.fdSeq = fdSeq;
    }

    /**
     * 消息id
     */
    public String getFdMsgId() {
        return this.fdMsgId;
    }

    /**
     * 消息id
     */
    public void setFdMsgId(String fdMsgId) {
        this.fdMsgId = fdMsgId;
    }

    /**
     * 公钥版本
     */
    public String getFdPublickeyVer() {
        return this.fdPublickeyVer;
    }

    /**
     * 公钥版本
     */
    public void setFdPublickeyVer(String fdPublickeyVer) {
        this.fdPublickeyVer = fdPublickeyVer;
    }

    /**
     * 秘钥
     */
    public String getFdEncryptRandomKey() {
        return this.fdEncryptRandomKey;
    }

    /**
     * 秘钥
     */
    public void setFdEncryptRandomKey(String fdEncryptRandomKey) {
        this.fdEncryptRandomKey = fdEncryptRandomKey;
    }

    /**
     * 密文
     */
    public String getFdEncryptChatMsg() {
        return this.fdEncryptChatMsg;
    }

    /**
     * 密文
     */
    public void setFdEncryptChatMsg(String fdEncryptChatMsg) {
        this.fdEncryptChatMsg = fdEncryptChatMsg;
    }

    /**
     * 处理状态
     */
    public String getFdHandleStatus() {
        return this.fdHandleStatus;
    }

    /**
     * 处理状态
     */
    public void setFdHandleStatus(String fdHandleStatus) {
        this.fdHandleStatus = fdHandleStatus;
    }

    /**
     * 错误次数
     */
    public String getFdErrTimes() {
        return this.fdErrTimes;
    }

    /**
     * 错误次数
     */
    public void setFdErrTimes(String fdErrTimes) {
        this.fdErrTimes = fdErrTimes;
    }

    /**
     * 错误信息
     */
    public String getFdErrMsg() {
        return this.fdErrMsg;
    }

    /**
     * 错误信息
     */
    public void setFdErrMsg(String fdErrMsg) {
        this.fdErrMsg = fdErrMsg;
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
     * 组织ID
     */
    public String getFdCorpId() {
        return this.fdCorpId;
    }

    /**
     * 组织ID
     */
    public void setFdCorpId(String fdCorpId) {
        this.fdCorpId = fdCorpId;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
