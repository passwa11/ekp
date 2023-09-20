package com.landray.kmss.third.weixin.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatGroup;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataBak;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;

/**
  * 会话内容备份
  */
public class ThirdWeixinChatDataBakForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdSeq;

    private String fdMsgId;

    private String fdMsgType;

    private String fdMsgAction;

    private String fdFrom;

    private String fdToList;

    private String fdRoomId;

    private String fdMsgTime;

    private String fdContent;

    private String fdEncryType;

    private String fdFileId;

    private String fdFileMd5;

    private String fdFileSize;

    private String fdPreMsgId;

    private String fdAgreeUserid;

    private String fdAgreeTime;

    private String fdPlayLength;

    private String fdCorpName;

    private String fdUserId;

    private String fdLongitude;

    private String fdLatitude;

    private String fdAddress;

    private String fdTitle;

    private String fdZoom;

    private String fdEmotionType;

    private String fdWidth;

    private String fdHeight;

    private String fdTo;

    private String fdFileExt;

    private String fdLinkUrl;

    private String fdImageUrl;

    private String fdUsername;

    private String fdDisplayName;

    private String fdVoteType;

    private String fdVoteId;

    private String fdExtendContent;

    private String fdMeetingId;

    private String docCreateTime;

    private String fdCorpId;

    private String fdFromName;

    private String fdChatGroupId;

    private String fdChatGroupName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdSeq = null;
        fdMsgId = null;
        fdMsgType = null;
        fdMsgAction = null;
        fdFrom = null;
        fdToList = null;
        fdRoomId = null;
        fdMsgTime = null;
        fdContent = null;
        fdEncryType = null;
        fdFileId = null;
        fdFileMd5 = null;
        fdFileSize = null;
        fdPreMsgId = null;
        fdAgreeUserid = null;
        fdAgreeTime = null;
        fdPlayLength = null;
        fdCorpName = null;
        fdUserId = null;
        fdLongitude = null;
        fdLatitude = null;
        fdAddress = null;
        fdTitle = null;
        fdZoom = null;
        fdEmotionType = null;
        fdWidth = null;
        fdHeight = null;
        fdTo = null;
        fdFileExt = null;
        fdLinkUrl = null;
        fdImageUrl = null;
        fdUsername = null;
        fdDisplayName = null;
        fdVoteType = null;
        fdVoteId = null;
        fdExtendContent = null;
        fdMeetingId = null;
        docCreateTime = null;
        fdCorpId = null;
        fdFromName = null;
        fdChatGroupId = null;
        fdChatGroupName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinChatDataBak> getModelClass() {
        return ThirdWeixinChatDataBak.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdChatGroupId", new FormConvertor_IDToModel("fdChatGroup", ThirdWeixinChatGroup.class));
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
     * 消息类型
     */
    public String getFdMsgType() {
        return this.fdMsgType;
    }

    /**
     * 消息类型
     */
    public void setFdMsgType(String fdMsgType) {
        this.fdMsgType = fdMsgType;
    }

    /**
     * 消息动作
     */
    public String getFdMsgAction() {
        return this.fdMsgAction;
    }

    /**
     * 消息动作
     */
    public void setFdMsgAction(String fdMsgAction) {
        this.fdMsgAction = fdMsgAction;
    }

    /**
     * 发送方id
     */
    public String getFdFrom() {
        return this.fdFrom;
    }

    /**
     * 发送方id
     */
    public void setFdFrom(String fdFrom) {
        this.fdFrom = fdFrom;
    }

    /**
     * 接收方列表
     */
    public String getFdToList() {
        return this.fdToList;
    }

    /**
     * 接收方列表
     */
    public void setFdToList(String fdToList) {
        this.fdToList = fdToList;
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
     * 发送时间戳
     */
    public String getFdMsgTime() {
        return this.fdMsgTime;
    }

    /**
     * 发送时间戳
     */
    public void setFdMsgTime(String fdMsgTime) {
        this.fdMsgTime = fdMsgTime;
    }

    /**
     * 消息内容
     */
    public String getFdContent() {
        return this.fdContent;
    }

    /**
     * 消息内容
     */
    public void setFdContent(String fdContent) {
        this.fdContent = fdContent;
    }

    /**
     * 加密类型
     */
    public String getFdEncryType() {
        return this.fdEncryType;
    }

    /**
     * 加密类型
     */
    public void setFdEncryType(String fdEncryType) {
        this.fdEncryType = fdEncryType;
    }

    /**
     * 文件ID
     */
    public String getFdFileId() {
        return this.fdFileId;
    }

    /**
     * 文件ID
     */
    public void setFdFileId(String fdFileId) {
        this.fdFileId = fdFileId;
    }

    /**
     * 文件MD5
     */
    public String getFdFileMd5() {
        return this.fdFileMd5;
    }

    /**
     * 文件MD5
     */
    public void setFdFileMd5(String fdFileMd5) {
        this.fdFileMd5 = fdFileMd5;
    }

    /**
     * 文件大小
     */
    public String getFdFileSize() {
        return this.fdFileSize;
    }

    /**
     * 文件大小
     */
    public void setFdFileSize(String fdFileSize) {
        this.fdFileSize = fdFileSize;
    }

    /**
     * 撤回消息的ID
     */
    public String getFdPreMsgId() {
        return this.fdPreMsgId;
    }

    /**
     * 撤回消息的ID
     */
    public void setFdPreMsgId(String fdPreMsgId) {
        this.fdPreMsgId = fdPreMsgId;
    }

    /**
     * 同意会话用户ID
     */
    public String getFdAgreeUserid() {
        return this.fdAgreeUserid;
    }

    /**
     * 同意会话用户ID
     */
    public void setFdAgreeUserid(String fdAgreeUserid) {
        this.fdAgreeUserid = fdAgreeUserid;
    }

    /**
     * 同意会话时间
     */
    public String getFdAgreeTime() {
        return this.fdAgreeTime;
    }

    /**
     * 同意会话时间
     */
    public void setFdAgreeTime(String fdAgreeTime) {
        this.fdAgreeTime = fdAgreeTime;
    }

    /**
     * 播放长度
     */
    public String getFdPlayLength() {
        return this.fdPlayLength;
    }

    /**
     * 播放长度
     */
    public void setFdPlayLength(String fdPlayLength) {
        this.fdPlayLength = fdPlayLength;
    }

    /**
     * 企业名称
     */
    public String getFdCorpName() {
        return this.fdCorpName;
    }

    /**
     * 企业名称
     */
    public void setFdCorpName(String fdCorpName) {
        this.fdCorpName = fdCorpName;
    }

    /**
     * 用户ID
     */
    public String getFdUserId() {
        return this.fdUserId;
    }

    /**
     * 用户ID
     */
    public void setFdUserId(String fdUserId) {
        this.fdUserId = fdUserId;
    }

    /**
     * 经度
     */
    public String getFdLongitude() {
        return this.fdLongitude;
    }

    /**
     * 经度
     */
    public void setFdLongitude(String fdLongitude) {
        this.fdLongitude = fdLongitude;
    }

    /**
     * 纬度
     */
    public String getFdLatitude() {
        return this.fdLatitude;
    }

    /**
     * 纬度
     */
    public void setFdLatitude(String fdLatitude) {
        this.fdLatitude = fdLatitude;
    }

    /**
     * 地址信息
     */
    public String getFdAddress() {
        return this.fdAddress;
    }

    /**
     * 地址信息
     */
    public void setFdAddress(String fdAddress) {
        this.fdAddress = fdAddress;
    }

    /**
     * 标题（名称）
     */
    public String getFdTitle() {
        return this.fdTitle;
    }

    /**
     * 标题（名称）
     */
    public void setFdTitle(String fdTitle) {
        this.fdTitle = fdTitle;
    }

    /**
     * 缩放比例
     */
    public String getFdZoom() {
        return this.fdZoom;
    }

    /**
     * 缩放比例
     */
    public void setFdZoom(String fdZoom) {
        this.fdZoom = fdZoom;
    }

    /**
     * 表情类型
     */
    public String getFdEmotionType() {
        return this.fdEmotionType;
    }

    /**
     * 表情类型
     */
    public void setFdEmotionType(String fdEmotionType) {
        this.fdEmotionType = fdEmotionType;
    }

    /**
     * 宽度
     */
    public String getFdWidth() {
        return this.fdWidth;
    }

    /**
     * 宽度
     */
    public void setFdWidth(String fdWidth) {
        this.fdWidth = fdWidth;
    }

    /**
     * 高度
     */
    public String getFdHeight() {
        return this.fdHeight;
    }

    /**
     * 高度
     */
    public void setFdHeight(String fdHeight) {
        this.fdHeight = fdHeight;
    }

    /**
     * 接收方ID
     */
    public String getFdTo() {
        return this.fdTo;
    }

    /**
     * 接收方ID
     */
    public void setFdTo(String fdTo) {
        this.fdTo = fdTo;
    }

    /**
     * 文件类型后缀
     */
    public String getFdFileExt() {
        return this.fdFileExt;
    }

    /**
     * 文件类型后缀
     */
    public void setFdFileExt(String fdFileExt) {
        this.fdFileExt = fdFileExt;
    }

    /**
     * 链接
     */
    public String getFdLinkUrl() {
        return this.fdLinkUrl;
    }

    /**
     * 链接
     */
    public void setFdLinkUrl(String fdLinkUrl) {
        this.fdLinkUrl = fdLinkUrl;
    }

    /**
     * 链接图片
     */
    public String getFdImageUrl() {
        return this.fdImageUrl;
    }

    /**
     * 链接图片
     */
    public void setFdImageUrl(String fdImageUrl) {
        this.fdImageUrl = fdImageUrl;
    }

    /**
     * 用户名称
     */
    public String getFdUsername() {
        return this.fdUsername;
    }

    /**
     * 用户名称
     */
    public void setFdUsername(String fdUsername) {
        this.fdUsername = fdUsername;
    }

    /**
     * 小程序名称
     */
    public String getFdDisplayName() {
        return this.fdDisplayName;
    }

    /**
     * 小程序名称
     */
    public void setFdDisplayName(String fdDisplayName) {
        this.fdDisplayName = fdDisplayName;
    }

    /**
     * 投票类型
     */
    public String getFdVoteType() {
        return this.fdVoteType;
    }

    /**
     * 投票类型
     */
    public void setFdVoteType(String fdVoteType) {
        this.fdVoteType = fdVoteType;
    }

    /**
     * 投票ID
     */
    public String getFdVoteId() {
        return this.fdVoteId;
    }

    /**
     * 投票ID
     */
    public void setFdVoteId(String fdVoteId) {
        this.fdVoteId = fdVoteId;
    }

    /**
     * 扩展信息
     */
    public String getFdExtendContent() {
        return this.fdExtendContent;
    }

    /**
     * 扩展信息
     */
    public void setFdExtendContent(String fdExtendContent) {
        this.fdExtendContent = fdExtendContent;
    }

    /**
     * 会议ID
     */
    public String getFdMeetingId() {
        return this.fdMeetingId;
    }

    /**
     * 会议ID
     */
    public void setFdMeetingId(String fdMeetingId) {
        this.fdMeetingId = fdMeetingId;
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

    /**
     * 发送人名称
     */
    public String getFdFromName() {
        return this.fdFromName;
    }

    /**
     * 发送人名称
     */
    public void setFdFromName(String fdFromName) {
        this.fdFromName = fdFromName;
    }

    /**
     * 所属会话分组
     */
    public String getFdChatGroupId() {
        return this.fdChatGroupId;
    }

    /**
     * 所属会话分组
     */
    public void setFdChatGroupId(String fdChatGroupId) {
        this.fdChatGroupId = fdChatGroupId;
    }

    /**
     * 所属会话分组
     */
    public String getFdChatGroupName() {
        return this.fdChatGroupName;
    }

    /**
     * 所属会话分组
     */
    public void setFdChatGroupName(String fdChatGroupName) {
        this.fdChatGroupName = fdChatGroupName;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
