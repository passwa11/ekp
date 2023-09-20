package com.landray.kmss.third.weixin.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.weixin.model.ThirdWeixinAppchat;

/**
  * 群聊会话
  */
public class ThirdWeixinAppchatForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdChatName;

    private String fdChatId;

    private String fdChatMsg;

    private String fdOwnerId;

    private String fdUserList;

    private String fdIsDissolve;

    private String docCreateTime;

    private String docAlterTime;

    private String fdOwnerFdid;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdChatName = null;
        fdChatId = null;
        fdChatMsg = null;
        fdOwnerId = null;
        fdUserList = null;
        fdIsDissolve = null;
        docCreateTime = null;
        docAlterTime = null;
        fdOwnerFdid = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinAppchat> getModelClass() {
        return ThirdWeixinAppchat.class;
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
     * 群聊名
     */
    public String getFdChatName() {
        return this.fdChatName;
    }

    /**
     * 群聊名
     */
    public void setFdChatName(String fdChatName) {
        this.fdChatName = fdChatName;
    }

    /**
     * 群ID
     */
    public String getFdChatId() {
        return this.fdChatId;
    }

    /**
     * 群ID
     */
    public void setFdChatId(String fdChatId) {
        this.fdChatId = fdChatId;
    }

    /**
     * 群信息
     */
    public String getFdChatMsg() {
        return this.fdChatMsg;
    }

    /**
     * 群信息
     */
    public void setFdChatMsg(String fdChatMsg) {
        this.fdChatMsg = fdChatMsg;
    }

    /**
     * 群主id
     */
    public String getFdOwnerId() {
        return this.fdOwnerId;
    }

    /**
     * 群主id
     */
    public void setFdOwnerId(String fdOwnerId) {
        this.fdOwnerId = fdOwnerId;
    }

    /**
     * 成员列表
     */
    public String getFdUserList() {
        return this.fdUserList;
    }

    /**
     * 成员列表
     */
    public void setFdUserList(String fdUserList) {
        this.fdUserList = fdUserList;
    }

    /**
     * 是否解散
     */
    public String getFdIsDissolve() {
        return this.fdIsDissolve;
    }

    /**
     * 是否解散
     */
    public void setFdIsDissolve(String fdIsDissolve) {
        this.fdIsDissolve = fdIsDissolve;
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
     * 群主FdId
     */
    public String getFdOwnerFdid() {
        return this.fdOwnerFdid;
    }

    /**
     * 群主FdId
     */
    public void setFdOwnerFdid(String fdOwnerFdid) {
        this.fdOwnerFdid = fdOwnerFdid;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    public String getFdCorpId() {
        return fdCorpId;
    }

    public void setFdCorpId(String fdCorpId) {
        this.fdCorpId = fdCorpId;
    }

    private String fdCorpId;
}
