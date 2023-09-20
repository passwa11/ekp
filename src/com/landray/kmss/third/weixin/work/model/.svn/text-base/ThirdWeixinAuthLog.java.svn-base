package com.landray.kmss.third.weixin.work.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.third.weixin.work.forms.ThirdWeixinAuthLogForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;


/**
  * 登录认证日志
  */
public class ThirdWeixinAuthLog extends BaseModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date docCreateTime;

    private String fdUserId;

    private Long fdExpireTime;

    private String fdReqDataOri;

    private String fdReqDataDecyed;

    private String fdUrl;

    private Integer fdResult;

    private String fdErrMsg;

    private String fdResDataOri;

    private String fdResDataEncryed;

    private String fdLogType;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdWeixinAuthLogForm> getFormClass() {
        return ThirdWeixinAuthLogForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
     * 所属用户
     */
    public String getFdUserId() {
        return this.fdUserId;
    }

    /**
     * 所属用户
     */
    public void setFdUserId(String fdUserId) {
        this.fdUserId = fdUserId;
    }

    /**
     * 请求耗时
     */
    public Long getFdExpireTime() {
        return this.fdExpireTime;
    }

    /**
     * 请求耗时
     */
    public void setFdExpireTime(Long fdExpireTime) {
        this.fdExpireTime = fdExpireTime;
    }

    /**
     * 请求报文密文
     */
    public String getFdReqDataOri() {
        return (String) readLazyField("fdReqDataOri", this.fdReqDataOri);
    }

    /**
     * 请求报文密文
     */
    public void setFdReqDataOri(String fdReqDataOri) {
        this.fdReqDataOri = (String) writeLazyField("fdReqDataOri", this.fdReqDataOri, fdReqDataOri);
    }

    /**
     * 请求报文明文
     */
    public String getFdReqDataDecyed() {
		return (String) readLazyField("fdReqDataDecyed", this.fdReqDataDecyed);
    }

    /**
     * 请求报文明文
     */
    public void setFdReqDataDecyed(String fdReqDataDecyed) {
		this.fdReqDataDecyed = (String) writeLazyField("fdReqDataDecyed",
				this.fdReqDataDecyed, fdReqDataDecyed);
    }

    /**
     * URL地址
     */
    public String getFdUrl() {
        return this.fdUrl;
    }

    /**
     * URL地址
     */
    public void setFdUrl(String fdUrl) {
        this.fdUrl = fdUrl;
    }

    /**
     * 结果
     */
    public Integer getFdResult() {
        return this.fdResult;
    }

    /**
     * 结果
     */
    public void setFdResult(Integer fdResult) {
        this.fdResult = fdResult;
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
     * 响应报文明文
     */
    public String getFdResDataOri() {
        return (String) readLazyField("fdResDataOri", this.fdResDataOri);
    }

    /**
     * 响应报文明文
     */
    public void setFdResDataOri(String fdResDataOri) {
        this.fdResDataOri = (String) writeLazyField("fdResDataOri", this.fdResDataOri, fdResDataOri);
    }

    /**
     * 响应报文密文
     */
    public String getFdResDataEncryed() {
        return (String) readLazyField("fdResDataEncryed", this.fdResDataEncryed);
    }

    /**
     * 响应报文密文
     */
    public void setFdResDataEncryed(String fdResDataEncryed) {
        this.fdResDataEncryed = (String) writeLazyField("fdResDataEncryed", this.fdResDataEncryed, fdResDataEncryed);
    }

    /**
     * 日志类型
     */
    public String getFdLogType() {
        return this.fdLogType;
    }

    /**
     * 日志类型
     */
    public void setFdLogType(String fdLogType) {
        this.fdLogType = fdLogType;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
