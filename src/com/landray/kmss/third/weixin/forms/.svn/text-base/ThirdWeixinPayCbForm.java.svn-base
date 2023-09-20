package com.landray.kmss.third.weixin.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.weixin.model.ThirdWeixinPayCb;

/**
  * 支付回调日志
  */
public class ThirdWeixinPayCbForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String fdMchId;

    private String fdDeviceInfo;

    private String fdNonceStr;

    private String fdSign;

    private String fdSignType;

    private String fdTradeType;

    private String fdOpenid;

    private String fdIsSubscribe;

    private String fdBankType;

    private String fdSettlementTotalFee;

    private String fdCashFee;

    private String fdCashFeeType;

    private String fdCouponFee;

    private String fdCouponCount;

    private String fdCouponType;

    private String fdCouponId;

    private String fdCouponFeeOne;

    private String fdTransactionId;

    private String fdTimeEnd;

    private String fdAppid;

    private String fdResultCode;

    private String fdErrCode;

    private String fdErrCodeDes;

    private String fdTotalFee;

    private String fdOutTradeNo;

    private String fdAttach;

    private String fdReturnCode;

    private String fdReturnMsg;

    private String fdReqData;

    private String fdResData;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        docCreateTime = null;
        fdMchId = null;
        fdDeviceInfo = null;
        fdNonceStr = null;
        fdSign = null;
        fdSignType = null;
        fdTradeType = null;
        fdOpenid = null;
        fdIsSubscribe = null;
        fdBankType = null;
        fdSettlementTotalFee = null;
        fdCashFee = null;
        fdCashFeeType = null;
        fdCouponFee = null;
        fdCouponCount = null;
        fdCouponType = null;
        fdCouponId = null;
        fdCouponFeeOne = null;
        fdTransactionId = null;
        fdTimeEnd = null;
        fdAppid = null;
        fdResultCode = null;
        fdErrCode = null;
        fdErrCodeDes = null;
        fdTotalFee = null;
        fdOutTradeNo = null;
        fdAttach = null;
        fdReturnCode = null;
        fdReturnMsg = null;
        fdReqData = null;
        fdResData = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinPayCb> getModelClass() {
        return ThirdWeixinPayCb.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
        }
        return toModelPropertyMap;
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
     * 商户号
     */
    public String getFdMchId() {
        return this.fdMchId;
    }

    /**
     * 商户号
     */
    public void setFdMchId(String fdMchId) {
        this.fdMchId = fdMchId;
    }

    /**
     * 设备号
     */
    public String getFdDeviceInfo() {
        return this.fdDeviceInfo;
    }

    /**
     * 设备号
     */
    public void setFdDeviceInfo(String fdDeviceInfo) {
        this.fdDeviceInfo = fdDeviceInfo;
    }

    /**
     * 随机字符串
     */
    public String getFdNonceStr() {
        return this.fdNonceStr;
    }

    /**
     * 随机字符串
     */
    public void setFdNonceStr(String fdNonceStr) {
        this.fdNonceStr = fdNonceStr;
    }

    /**
     * 签名
     */
    public String getFdSign() {
        return this.fdSign;
    }

    /**
     * 签名
     */
    public void setFdSign(String fdSign) {
        this.fdSign = fdSign;
    }

    /**
     * 签名类型
     */
    public String getFdSignType() {
        return this.fdSignType;
    }

    /**
     * 签名类型
     */
    public void setFdSignType(String fdSignType) {
        this.fdSignType = fdSignType;
    }

    /**
     * 交易类型
     */
    public String getFdTradeType() {
        return this.fdTradeType;
    }

    /**
     * 交易类型
     */
    public void setFdTradeType(String fdTradeType) {
        this.fdTradeType = fdTradeType;
    }

    /**
     * 用户标识
     */
    public String getFdOpenid() {
        return this.fdOpenid;
    }

    /**
     * 用户标识
     */
    public void setFdOpenid(String fdOpenid) {
        this.fdOpenid = fdOpenid;
    }

    /**
     * 是否关注公众账号
     */
    public String getFdIsSubscribe() {
        return this.fdIsSubscribe;
    }

    /**
     * 是否关注公众账号
     */
    public void setFdIsSubscribe(String fdIsSubscribe) {
        this.fdIsSubscribe = fdIsSubscribe;
    }

    /**
     * 付款银行
     */
    public String getFdBankType() {
        return this.fdBankType;
    }

    /**
     * 付款银行
     */
    public void setFdBankType(String fdBankType) {
        this.fdBankType = fdBankType;
    }

    /**
     * 应结订单金额
     */
    public String getFdSettlementTotalFee() {
        return this.fdSettlementTotalFee;
    }

    /**
     * 应结订单金额
     */
    public void setFdSettlementTotalFee(String fdSettlementTotalFee) {
        this.fdSettlementTotalFee = fdSettlementTotalFee;
    }

    /**
     * 现金支付金额
     */
    public String getFdCashFee() {
        return this.fdCashFee;
    }

    /**
     * 现金支付金额
     */
    public void setFdCashFee(String fdCashFee) {
        this.fdCashFee = fdCashFee;
    }

    /**
     * 现金支付币种
     */
    public String getFdCashFeeType() {
        return this.fdCashFeeType;
    }

    /**
     * 现金支付币种
     */
    public void setFdCashFeeType(String fdCashFeeType) {
        this.fdCashFeeType = fdCashFeeType;
    }

    /**
     * 代金券金额
     */
    public String getFdCouponFee() {
        return this.fdCouponFee;
    }

    /**
     * 代金券金额
     */
    public void setFdCouponFee(String fdCouponFee) {
        this.fdCouponFee = fdCouponFee;
    }

    /**
     * 代金券使用数量
     */
    public String getFdCouponCount() {
        return this.fdCouponCount;
    }

    /**
     * 代金券使用数量
     */
    public void setFdCouponCount(String fdCouponCount) {
        this.fdCouponCount = fdCouponCount;
    }

    /**
     * 代金券类型
     */
    public String getFdCouponType() {
        return this.fdCouponType;
    }

    /**
     * 代金券类型
     */
    public void setFdCouponType(String fdCouponType) {
        this.fdCouponType = fdCouponType;
    }

    /**
     * 代金券ID
     */
    public String getFdCouponId() {
        return this.fdCouponId;
    }

    /**
     * 代金券ID
     */
    public void setFdCouponId(String fdCouponId) {
        this.fdCouponId = fdCouponId;
    }

    /**
     * 单个代金券支付金额
     */
    public String getFdCouponFeeOne() {
        return this.fdCouponFeeOne;
    }

    /**
     * 单个代金券支付金额
     */
    public void setFdCouponFeeOne(String fdCouponFeeOne) {
        this.fdCouponFeeOne = fdCouponFeeOne;
    }

    /**
     * 微信支付订单号
     */
    public String getFdTransactionId() {
        return this.fdTransactionId;
    }

    /**
     * 微信支付订单号
     */
    public void setFdTransactionId(String fdTransactionId) {
        this.fdTransactionId = fdTransactionId;
    }

    /**
     * 支付完成时间
     */
    public String getFdTimeEnd() {
        return this.fdTimeEnd;
    }

    /**
     * 支付完成时间
     */
    public void setFdTimeEnd(String fdTimeEnd) {
        this.fdTimeEnd = fdTimeEnd;
    }

    /**
     * 小程序ID
     */
    public String getFdAppid() {
        return this.fdAppid;
    }

    /**
     * 小程序ID
     */
    public void setFdAppid(String fdAppid) {
        this.fdAppid = fdAppid;
    }

    /**
     * 业务结果
     */
    public String getFdResultCode() {
        return this.fdResultCode;
    }

    /**
     * 业务结果
     */
    public void setFdResultCode(String fdResultCode) {
        this.fdResultCode = fdResultCode;
    }

    /**
     * 错误代码
     */
    public String getFdErrCode() {
        return this.fdErrCode;
    }

    /**
     * 错误代码
     */
    public void setFdErrCode(String fdErrCode) {
        this.fdErrCode = fdErrCode;
    }

    /**
     * 错误代码描述
     */
    public String getFdErrCodeDes() {
        return this.fdErrCodeDes;
    }

    /**
     * 错误代码描述
     */
    public void setFdErrCodeDes(String fdErrCodeDes) {
        this.fdErrCodeDes = fdErrCodeDes;
    }

    /**
     * 订单金额
     */
    public String getFdTotalFee() {
        return this.fdTotalFee;
    }

    /**
     * 订单金额
     */
    public void setFdTotalFee(String fdTotalFee) {
        this.fdTotalFee = fdTotalFee;
    }

    /**
     * 商户订单号
     */
    public String getFdOutTradeNo() {
        return this.fdOutTradeNo;
    }

    /**
     * 商户订单号
     */
    public void setFdOutTradeNo(String fdOutTradeNo) {
        this.fdOutTradeNo = fdOutTradeNo;
    }

    /**
     * 商家数据包
     */
    public String getFdAttach() {
        return this.fdAttach;
    }

    /**
     * 商家数据包
     */
    public void setFdAttach(String fdAttach) {
        this.fdAttach = fdAttach;
    }

    /**
     * 返回状态码
     */
    public String getFdReturnCode() {
        return this.fdReturnCode;
    }

    /**
     * 返回状态码
     */
    public void setFdReturnCode(String fdReturnCode) {
        this.fdReturnCode = fdReturnCode;
    }

    /**
     * 返回信息
     */
    public String getFdReturnMsg() {
        return this.fdReturnMsg;
    }

    /**
     * 返回信息
     */
    public void setFdReturnMsg(String fdReturnMsg) {
        this.fdReturnMsg = fdReturnMsg;
    }

    /**
     * 请求报文
     */
    public String getFdReqData() {
        return this.fdReqData;
    }

    /**
     * 请求报文
     */
    public void setFdReqData(String fdReqData) {
        this.fdReqData = fdReqData;
    }

    /**
     * 响应报文
     */
    public String getFdResData() {
        return this.fdResData;
    }

    /**
     * 响应报文
     */
    public void setFdResData(String fdResData) {
        this.fdResData = fdResData;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
