package com.landray.kmss.third.weixin.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.third.weixin.forms.ThirdWeixinChatDataBakForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.util.DateUtil;

/**
  * 会话内容备份
  */
public class ThirdWeixinChatDataBak extends BaseModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Long fdSeq;

    private String fdMsgId;

    private String fdMsgType;

    private String fdMsgAction;

    private String fdFrom;

    private String fdToList;

    private String fdRoomId;

    private Long fdMsgTime;

    private String fdContent;

    private Integer fdEncryType;

    private String fdFileId;

    private String fdFileMd5;

    private Integer fdFileSize;

    private String fdPreMsgId;

    private String fdAgreeUserid;

    private Long fdAgreeTime;

    private Integer fdPlayLength;

    private String fdCorpName;

    private String fdUserId;

    private Double fdLongitude;

    private Double fdLatitude;

    private String fdAddress;

    private String fdTitle;

    private Integer fdZoom;

    private Integer fdEmotionType;

    private Integer fdWidth;

    private Integer fdHeight;

    private String fdTo;

    private String fdFileExt;

    private String fdLinkUrl;

    private String fdImageUrl;

    private String fdUsername;

    private String fdDisplayName;

    private Integer fdVoteType;

    private String fdVoteId;

    private String fdExtendContent;

    private String fdMeetingId;

    private Date docCreateTime;

    private String fdCorpId;

    private String fdFromName;

    private ThirdWeixinChatGroup fdChatGroup;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdWeixinChatDataBakForm> getFormClass() {
        return ThirdWeixinChatDataBakForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdChatGroup.fdRelateUserId", "fdChatGroupName");
            toFormPropertyMap.put("fdChatGroup.fdId", "fdChatGroupId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 序号
     */
    public Long getFdSeq() {
        return this.fdSeq;
    }

    /**
     * 序号
     */
    public void setFdSeq(Long fdSeq) {
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
        return (String) readLazyField("fdToList", this.fdToList);
    }

    /**
     * 接收方列表
     */
    public void setFdToList(String fdToList) {
        this.fdToList = (String) writeLazyField("fdToList", this.fdToList, fdToList);
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
    public Long getFdMsgTime() {
        return this.fdMsgTime;
    }

    /**
     * 发送时间戳
     */
    public void setFdMsgTime(Long fdMsgTime) {
        this.fdMsgTime = fdMsgTime;
    }

    /**
     * 消息内容
     */
    public String getFdContent() {
        return (String) readLazyField("fdContent", this.fdContent);
    }

    /**
     * 消息内容
     */
    public void setFdContent(String fdContent) {
        this.fdContent = (String) writeLazyField("fdContent", this.fdContent, fdContent);
    }

    /**
     * 加密类型
     */
    public Integer getFdEncryType() {
        return this.fdEncryType;
    }

    /**
     * 加密类型
     */
    public void setFdEncryType(Integer fdEncryType) {
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
    public Integer getFdFileSize() {
        return this.fdFileSize;
    }

    /**
     * 文件大小
     */
    public void setFdFileSize(Integer fdFileSize) {
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
    public Long getFdAgreeTime() {
        return this.fdAgreeTime;
    }

    /**
     * 同意会话时间
     */
    public void setFdAgreeTime(Long fdAgreeTime) {
        this.fdAgreeTime = fdAgreeTime;
    }

    /**
     * 播放长度
     */
    public Integer getFdPlayLength() {
        return this.fdPlayLength;
    }

    /**
     * 播放长度
     */
    public void setFdPlayLength(Integer fdPlayLength) {
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
    public Double getFdLongitude() {
        return this.fdLongitude;
    }

    /**
     * 经度
     */
    public void setFdLongitude(Double fdLongitude) {
        this.fdLongitude = fdLongitude;
    }

    /**
     * 纬度
     */
    public Double getFdLatitude() {
        return this.fdLatitude;
    }

    /**
     * 纬度
     */
    public void setFdLatitude(Double fdLatitude) {
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
    public Integer getFdZoom() {
        return this.fdZoom;
    }

    /**
     * 缩放比例
     */
    public void setFdZoom(Integer fdZoom) {
        this.fdZoom = fdZoom;
    }

    /**
     * 表情类型
     */
    public Integer getFdEmotionType() {
        return this.fdEmotionType;
    }

    /**
     * 表情类型
     */
    public void setFdEmotionType(Integer fdEmotionType) {
        this.fdEmotionType = fdEmotionType;
    }

    /**
     * 宽度
     */
    public Integer getFdWidth() {
        return this.fdWidth;
    }

    /**
     * 宽度
     */
    public void setFdWidth(Integer fdWidth) {
        this.fdWidth = fdWidth;
    }

    /**
     * 高度
     */
    public Integer getFdHeight() {
        return this.fdHeight;
    }

    /**
     * 高度
     */
    public void setFdHeight(Integer fdHeight) {
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
    public Integer getFdVoteType() {
        return this.fdVoteType;
    }

    /**
     * 投票类型
     */
    public void setFdVoteType(Integer fdVoteType) {
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
        return (String) readLazyField("fdExtendContent", this.fdExtendContent);
    }

    /**
     * 扩展信息
     */
    public void setFdExtendContent(String fdExtendContent) {
        this.fdExtendContent = (String) writeLazyField("fdExtendContent", this.fdExtendContent, fdExtendContent);
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
    public ThirdWeixinChatGroup getFdChatGroup() {
        return this.fdChatGroup;
    }

    /**
     * 所属会话分组
     */
    public void setFdChatGroup(ThirdWeixinChatGroup fdChatGroup) {
        this.fdChatGroup = fdChatGroup;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
