package com.landray.kmss.third.weixin.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.weixin.forms.ThirdWeixinChatGroupForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.util.DateUtil;

/**
  * 会话分组
  */
public class ThirdWeixinChatGroup extends BaseModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdRelateUserId;

    private String fdUserIdFir;

    public String getFdUserIdFir() {
        return fdUserIdFir;
    }

    public void setFdUserIdFir(String fdUserIdFir) {
        this.fdUserIdFir = fdUserIdFir;
    }

    public String getFdUserIdSec() {
        return fdUserIdSec;
    }

    public void setFdUserIdSec(String fdUserIdSec) {
        this.fdUserIdSec = fdUserIdSec;
    }

    private String fdUserIdSec;

    private String fdRoomId;

    private String fdMd5;

    private Boolean fdIsOut;

    private String fdNewestMsgId;

    private Long fdNewestMsgTime;

    private Date docCreateTime;

    private Date docAlterTime;

    private String fdChatGroupName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdWeixinChatGroupForm> getFormClass() {
        return ThirdWeixinChatGroupForm.class;
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
    public Boolean getFdIsOut() {
        return this.fdIsOut;
    }

    /**
     * 是否外部
     */
    public void setFdIsOut(Boolean fdIsOut) {
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
    public Long getFdNewestMsgTime() {
        return this.fdNewestMsgTime;
    }

    /**
     * 最新消息时间
     */
    public void setFdNewestMsgTime(Long fdNewestMsgTime) {
        this.fdNewestMsgTime = fdNewestMsgTime;
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

    public String getNewestMsgTime() {
        return newestMsgTime;
    }

    public void setNewestMsgTime(String newestMsgTime) {
        this.newestMsgTime = newestMsgTime;
    }

    public String getNewestMsg() {
        return newestMsg;
    }

    public void setNewestMsg(String newestMsg) {
        this.newestMsg = newestMsg;
    }

    private String newestMsgTime;
    private String newestMsg;

    public String getGroupNameImage() {
        return groupNameImage;
    }

    public void setGroupNameImage(String groupNameImage) {
        this.groupNameImage = groupNameImage;
    }

    private String groupNameImage;

    public Date getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Date msgTime) {
        this.msgTime = msgTime;
    }

    public SysOrgElement getRelateOrg() {
        return relateOrg;
    }

    public void setRelateOrg(SysOrgElement relateOrg) {
        this.relateOrg = relateOrg;
    }

    private Date msgTime;
    private SysOrgElement relateOrg;

    public Long getMsgSeq() {
        return msgSeq;
    }

    public void setMsgSeq(Long msgSeq) {
        this.msgSeq = msgSeq;
    }

    private Long msgSeq;

}
