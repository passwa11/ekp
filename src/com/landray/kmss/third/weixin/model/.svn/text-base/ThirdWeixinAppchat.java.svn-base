package com.landray.kmss.third.weixin.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.third.weixin.forms.ThirdWeixinAppchatForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.util.DateUtil;

/**
  * 群聊会话
  */
public class ThirdWeixinAppchat extends BaseModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdChatName;

    private String fdChatId;

    private String fdChatMsg;

    private String fdOwnerId;

    private String fdUserList;

    private Boolean fdIsDissolve;

    private Date docCreateTime;

    private Date docAlterTime;

    private String fdOwnerFdid;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdWeixinAppchatForm> getFormClass() {
        return ThirdWeixinAppchatForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
        return (String) readLazyField("fdUserList", this.fdUserList);
    }

    /**
     * 成员列表
     */
    public void setFdUserList(String fdUserList) {
        this.fdUserList = (String) writeLazyField("fdUserList", this.fdUserList, fdUserList);
    }

    /**
     * 是否解散
     */
    public Boolean getFdIsDissolve() {
        return this.fdIsDissolve;
    }

    /**
     * 是否解散
     */
    public void setFdIsDissolve(Boolean fdIsDissolve) {
        this.fdIsDissolve = fdIsDissolve;
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
     * 更新时间
     */
    public Date getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(Date docAlterTime) {
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
