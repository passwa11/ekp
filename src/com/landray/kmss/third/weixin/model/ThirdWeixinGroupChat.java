package com.landray.kmss.third.weixin.model;

import java.util.ArrayList;
import java.util.List;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import java.util.Date;
import com.landray.kmss.third.weixin.forms.ThirdWeixinGroupChatForm;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.common.model.BaseModel;

/**
  * 内部群信息
  */
public class ThirdWeixinGroupChat extends BaseModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdRoomName;

    private String fdRoomCreator;

    private Long fdRoomCreateTime;

    private String fdRoomNotice;

    private String fdCorpId;

    private Date docCreateTime;

    private Date docAlterTime;

    private Boolean fdIsDissolve;

    private String fdOwnerFdid;

    private String fdRoomId;

    private List<ThirdWeixinAccount> fdMember = new ArrayList<ThirdWeixinAccount>();

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdWeixinGroupChatForm> getFormClass() {
        return ThirdWeixinGroupChatForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdMember", new ModelConvertor_ModelListToString("fdMemberIds:fdMemberNames", "fdId:fdAccountId"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
    public Long getFdRoomCreateTime() {
        return this.fdRoomCreateTime;
    }

    /**
     * 群创建时间
     */
    public void setFdRoomCreateTime(Long fdRoomCreateTime) {
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
    public List<ThirdWeixinAccount> getFdMember() {
        return this.fdMember;
    }

    /**
     * 群成员
     */
    public void setFdMember(List<ThirdWeixinAccount> fdMember) {
        this.fdMember = fdMember;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
