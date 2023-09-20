package com.landray.kmss.third.weixin.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.third.weixin.forms.ThirdWeixinChatDataTempForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.util.DateUtil;

/**
  * 会话同步临时表
  */
public class ThirdWeixinChatDataTemp extends BaseModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Long fdSeq;

    private String fdMsgId;

    private Integer fdPublickeyVer;

    private String fdEncryptRandomKey;

    private String fdEncryptChatMsg;

    private Integer fdHandleStatus;

    private Integer fdErrTimes;

    private String fdErrMsg;

    private Date docCreateTime;

    private Date docAlterTime;

    private String fdCorpId;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdWeixinChatDataTempForm> getFormClass() {
        return ThirdWeixinChatDataTempForm.class;
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
     * 公钥版本
     */
    public Integer getFdPublickeyVer() {
        return this.fdPublickeyVer;
    }

    /**
     * 公钥版本
     */
    public void setFdPublickeyVer(Integer fdPublickeyVer) {
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
        return (String) readLazyField("fdEncryptChatMsg", this.fdEncryptChatMsg);
    }

    /**
     * 密文
     */
    public void setFdEncryptChatMsg(String fdEncryptChatMsg) {
        this.fdEncryptChatMsg = (String) writeLazyField("fdEncryptChatMsg", this.fdEncryptChatMsg, fdEncryptChatMsg);
    }

    /**
     * 处理状态
     */
    public Integer getFdHandleStatus() {
        return this.fdHandleStatus;
    }

    /**
     * 处理状态
     */
    public void setFdHandleStatus(Integer fdHandleStatus) {
        this.fdHandleStatus = fdHandleStatus;
    }

    /**
     * 错误次数
     */
    public Integer getFdErrTimes() {
        return this.fdErrTimes;
    }

    /**
     * 错误次数
     */
    public void setFdErrTimes(Integer fdErrTimes) {
        this.fdErrTimes = fdErrTimes;
    }

    /**
     * 错误信息
     */
    public String getFdErrMsg() {
        return (String) readLazyField("fdErrMsg", this.fdErrMsg);
    }

    /**
     * 错误信息
     */
    public void setFdErrMsg(String fdErrMsg) {
        this.fdErrMsg = (String) writeLazyField("fdErrMsg", this.fdErrMsg, fdErrMsg);
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
