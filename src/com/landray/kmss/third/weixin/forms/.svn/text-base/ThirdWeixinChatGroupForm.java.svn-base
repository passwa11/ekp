package com.landray.kmss.third.weixin.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatGroup;

/**
  * 会话分组
  */
public class ThirdWeixinChatGroupForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdRelateUserId;

    private String fdRoomId;

    private String fdMd5;

    private String fdIsOut;

    private String fdNewestMsgId;

    private String fdNewestMsgTime;

    private String docCreateTime;

    private String docAlterTime;

    private String fdChatGroupName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdRelateUserId = null;
        fdRoomId = null;
        fdMd5 = null;
        fdIsOut = null;
        fdNewestMsgId = null;
        fdNewestMsgTime = null;
        docCreateTime = null;
        docAlterTime = null;
        fdChatGroupName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinChatGroup> getModelClass() {
        return ThirdWeixinChatGroup.class;
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
     * 相关人ID
     */
    public String getFdRelateUserId() {
        return this.fdRelateUserId;
    }

    /**
     * 相关人ID
     */
    public void setFdRelateUserId(String fdRelateUserId) {
        this.fdRelateUserId = fdRelateUserId;
    }

    /**
     * 群ID
     */
    public String getFdRoomId() {
        return this.fdRoomId;
    }

    /**
     * 群ID
     */
    public void setFdRoomId(String fdRoomId) {
        this.fdRoomId = fdRoomId;
    }

    /**
     * MD5
     */
    public String getFdMd5() {
        return this.fdMd5;
    }

    /**
     * MD5
     */
    public void setFdMd5(String fdMd5) {
        this.fdMd5 = fdMd5;
    }

    /**
     * 是否外部
     */
    public String getFdIsOut() {
        return this.fdIsOut;
    }

    /**
     * 是否外部
     */
    public void setFdIsOut(String fdIsOut) {
        this.fdIsOut = fdIsOut;
    }

    /**
     * 最新消息ID
     */
    public String getFdNewestMsgId() {
        return this.fdNewestMsgId;
    }

    /**
     * 最新消息ID
     */
    public void setFdNewestMsgId(String fdNewestMsgId) {
        this.fdNewestMsgId = fdNewestMsgId;
    }

    /**
     * 最新消息时间
     */
    public String getFdNewestMsgTime() {
        return this.fdNewestMsgTime;
    }

    /**
     * 最新消息时间
     */
    public void setFdNewestMsgTime(String fdNewestMsgTime) {
        this.fdNewestMsgTime = fdNewestMsgTime;
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
     * 会话分组名称
     */
    public String getFdChatGroupName() {
        return this.fdChatGroupName;
    }

    /**
     * 会话分组名称
     */
    public void setFdChatGroupName(String fdChatGroupName) {
        this.fdChatGroupName = fdChatGroupName;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
