package com.landray.kmss.third.weixin.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.third.weixin.model.ThirdWeixinAccount;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.weixin.model.ThirdWeixinGroupChat;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;

/**
  * 内部群信息
  */
public class ThirdWeixinGroupChatForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdRoomName;

    private String fdRoomCreator;

    private String fdRoomCreateTime;

    private String fdRoomNotice;

    private String fdCorpId;

    private String docCreateTime;

    private String docAlterTime;

    private String fdIsDissolve;

    private String fdOwnerFdid;

    private String fdRoomId;

    private String fdMemberIds;

    private String fdMemberNames;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdRoomName = null;
        fdRoomCreator = null;
        fdRoomCreateTime = null;
        fdRoomNotice = null;
        fdCorpId = null;
        docCreateTime = null;
        docAlterTime = null;
        fdIsDissolve = null;
        fdOwnerFdid = null;
        fdRoomId = null;
        fdMemberIds = null;
        fdMemberNames = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinGroupChat> getModelClass() {
        return ThirdWeixinGroupChat.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdMemberIds", new FormConvertor_IDsToModelList("fdMember", ThirdWeixinAccount.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 群名称
     */
    public String getFdRoomName() {
        return this.fdRoomName;
    }

    /**
     * 群名称
     */
    public void setFdRoomName(String fdRoomName) {
        this.fdRoomName = fdRoomName;
    }

    /**
     * 群创建者
     */
    public String getFdRoomCreator() {
        return this.fdRoomCreator;
    }

    /**
     * 群创建者
     */
    public void setFdRoomCreator(String fdRoomCreator) {
        this.fdRoomCreator = fdRoomCreator;
    }

    /**
     * 群创建时间
     */
    public String getFdRoomCreateTime() {
        return this.fdRoomCreateTime;
    }

    /**
     * 群创建时间
     */
    public void setFdRoomCreateTime(String fdRoomCreateTime) {
        this.fdRoomCreateTime = fdRoomCreateTime;
    }

    /**
     * 群公告
     */
    public String getFdRoomNotice() {
        return this.fdRoomNotice;
    }

    /**
     * 群公告
     */
    public void setFdRoomNotice(String fdRoomNotice) {
        this.fdRoomNotice = fdRoomNotice;
    }

    /**
     * 所属组织
     */
    public String getFdCorpId() {
        return this.fdCorpId;
    }

    /**
     * 所属组织
     */
    public void setFdCorpId(String fdCorpId) {
        this.fdCorpId = fdCorpId;
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
     * 群成员
     */
    public String getFdMemberIds() {
        return this.fdMemberIds;
    }

    /**
     * 群成员
     */
    public void setFdMemberIds(String fdMemberIds) {
        this.fdMemberIds = fdMemberIds;
    }

    /**
     * 群成员
     */
    public String getFdMemberNames() {
        return this.fdMemberNames;
    }

    /**
     * 群成员
     */
    public void setFdMemberNames(String fdMemberNames) {
        this.fdMemberNames = fdMemberNames;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
