package com.landray.kmss.third.weixin.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.third.weixin.forms.ThirdWeixinPayOrderForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;

/**
  * 支付订单
  */
public class ThirdWeixinPayOrder extends BaseModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdAppId;

    private String fdMchId;

    private String fdDeviceInfo;

    private String fdNonceStr;

    private String fdSign;

    private String fdSignType;

    private String fdBody;

    private String fdDetail;

    private String fdAttach;

    private String fdFeeType;

    private Integer fdTotalFee;

    private String fdSpbillCreateIp;

    private String fdTimeStart;

    private String fdTimeExpire;

    private String fdGoodsTag;

    private String fdTradeType;

    private String fdProductId;

    private String fdLimitPay;

    private String fdOpenid;

    private String fdProfitSharing;

    private String fdSceneInfo;

    private String fdTradeTypeReturn;

    private String fdPrepayId;

    private String fdCodeUrl;

    private Boolean fdIsSubscribe;

    private String fdTradeState;

    private String fdBankType;

    private Integer fdTotalFeeReturn;

    private Integer fdSettlementTotalFee;

    private String fdFeeTypeReturn;

    private Integer fdCashFee;

    private String fdCashFeeType;

    private Integer fdCouponFee;

    private Integer fdCouponCount;

    private String fdCouponType;

    private String fdCouponId;

    private Integer fdCouponFeeOne;

    private String fdTransactionId;

    private String fdTimeEnd;

    private String fdTradeStateDesc;

    private Date docCreateTime;

    private Date docAlterTime;

    private String fdModelName;

    private String fdModelId;

    private String fdKey;

    private SysOrgPerson docCreator;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdWeixinPayOrderForm> getFormClass() {
        return ThirdWeixinPayOrderForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 公众账号ID
     */
    public String getFdAppId() {
        return this.fdAppId;
    }

    /**
     * 公众账号ID
     */
    public void setFdAppId(String fdAppId) {
        this.fdAppId = fdAppId;
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
     * 商品描述
     */
    public String getFdBody() {
        return this.fdBody;
    }

    /**
     * 商品描述
     */
    public void setFdBody(String fdBody) {
        this.fdBody = fdBody;
    }

    /**
     * 商品详情
     */
    public String getFdDetail() {
        return (String) readLazyField("fdDetail", this.fdDetail);
    }

    /**
     * 商品详情
     */
    public void setFdDetail(String fdDetail) {
        this.fdDetail = (String) writeLazyField("fdDetail", this.fdDetail, fdDetail);
    }

    /**
     * 附加数据
     */
    public String getFdAttach() {
        return this.fdAttach;
    }

    /**
     * 附加数据
     */
    public void setFdAttach(String fdAttach) {
        this.fdAttach = fdAttach;
    }

    /**
     * 标价币种
     */
    public String getFdFeeType() {
        return this.fdFeeType;
    }

    /**
     * 标价币种
     */
    public void setFdFeeType(String fdFeeType) {
        this.fdFeeType = fdFeeType;
    }

    /**
     * 标价金额
     */
    public Integer getFdTotalFee() {
        return this.fdTotalFee;
    }

    /**
     * 标价金额
     */
    public void setFdTotalFee(Integer fdTotalFee) {
        this.fdTotalFee = fdTotalFee;
    }

    /**
     * 终端IP
     */
    public String getFdSpbillCreateIp() {
        return this.fdSpbillCreateIp;
    }

    /**
     * 终端IP
     */
    public void setFdSpbillCreateIp(String fdSpbillCreateIp) {
        this.fdSpbillCreateIp = fdSpbillCreateIp;
    }

    /**
     * 交易起始时间
     */
    public String getFdTimeStart() {
        return this.fdTimeStart;
    }

    /**
     * 交易起始时间
     */
    public void setFdTimeStart(String fdTimeStart) {
        this.fdTimeStart = fdTimeStart;
    }

    /**
     * 交易结束时间
     */
    public String getFdTimeExpire() {
        return this.fdTimeExpire;
    }

    /**
     * 交易结束时间
     */
    public void setFdTimeExpire(String fdTimeExpire) {
        this.fdTimeExpire = fdTimeExpire;
    }

    /**
     * 订单优惠标记
     */
    public String getFdGoodsTag() {
        return this.fdGoodsTag;
    }

    /**
     * 订单优惠标记
     */
    public void setFdGoodsTag(String fdGoodsTag) {
        this.fdGoodsTag = fdGoodsTag;
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
     * 商品ID
     */
    public String getFdProductId() {
        return this.fdProductId;
    }

    /**
     * 商品ID
     */
    public void setFdProductId(String fdProductId) {
        this.fdProductId = fdProductId;
    }

    /**
     * 指定支付方式
     */
    public String getFdLimitPay() {
        return this.fdLimitPay;
    }

    /**
     * 指定支付方式
     */
    public void setFdLimitPay(String fdLimitPay) {
        this.fdLimitPay = fdLimitPay;
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
     * 电子发票入口开放标识
     */
    public String getFdProfitSharing() {
        return this.fdProfitSharing;
    }

    /**
     * 电子发票入口开放标识
     */
    public void setFdProfitSharing(String fdProfitSharing) {
        this.fdProfitSharing = fdProfitSharing;
    }

    /**
     * 场景信息
     */
    public String getFdSceneInfo() {
        return this.fdSceneInfo;
    }

    /**
     * 场景信息
     */
    public void setFdSceneInfo(String fdSceneInfo) {
        this.fdSceneInfo = fdSceneInfo;
    }

    /**
     * 交易类型-响应
     */
    public String getFdTradeTypeReturn() {
        return this.fdTradeTypeReturn;
    }

    /**
     * 交易类型-响应
     */
    public void setFdTradeTypeReturn(String fdTradeTypeReturn) {
        this.fdTradeTypeReturn = fdTradeTypeReturn;
    }

    /**
     * 预支付交易会话标识
     */
    public String getFdPrepayId() {
        return this.fdPrepayId;
    }

    /**
     * 预支付交易会话标识
     */
    public void setFdPrepayId(String fdPrepayId) {
        this.fdPrepayId = fdPrepayId;
    }

    /**
     * 二维码链接
     */
    public String getFdCodeUrl() {
        return this.fdCodeUrl;
    }

    /**
     * 二维码链接
     */
    public void setFdCodeUrl(String fdCodeUrl) {
        this.fdCodeUrl = fdCodeUrl;
    }

    /**
     * 是否关注公众账号
     */
    public Boolean getFdIsSubscribe() {
        return this.fdIsSubscribe;
    }

    /**
     * 是否关注公众账号
     */
    public void setFdIsSubscribe(Boolean fdIsSubscribe) {
        this.fdIsSubscribe = fdIsSubscribe;
    }

    /**
     * 交易状态
     */
    public String getFdTradeState() {
        return this.fdTradeState;
    }

    /**
     * 交易状态
     */
    public void setFdTradeState(String fdTradeState) {
        this.fdTradeState = fdTradeState;
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
     * 标价金额-响应
     */
    public Integer getFdTotalFeeReturn() {
        return this.fdTotalFeeReturn;
    }

    /**
     * 标价金额-响应
     */
    public void setFdTotalFeeReturn(Integer fdTotalFeeReturn) {
        this.fdTotalFeeReturn = fdTotalFeeReturn;
    }

    /**
     * 应结订单金额
     */
    public Integer getFdSettlementTotalFee() {
        return this.fdSettlementTotalFee;
    }

    /**
     * 应结订单金额
     */
    public void setFdSettlementTotalFee(Integer fdSettlementTotalFee) {
        this.fdSettlementTotalFee = fdSettlementTotalFee;
    }

    /**
     * 标价币种-响应
     */
    public String getFdFeeTypeReturn() {
        return this.fdFeeTypeReturn;
    }

    /**
     * 标价币种-响应
     */
    public void setFdFeeTypeReturn(String fdFeeTypeReturn) {
        this.fdFeeTypeReturn = fdFeeTypeReturn;
    }

    /**
     * 现金支付金额
     */
    public Integer getFdCashFee() {
        return this.fdCashFee;
    }

    /**
     * 现金支付金额
     */
    public void setFdCashFee(Integer fdCashFee) {
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
    public Integer getFdCouponFee() {
        return this.fdCouponFee;
    }

    /**
     * 代金券金额
     */
    public void setFdCouponFee(Integer fdCouponFee) {
        this.fdCouponFee = fdCouponFee;
    }

    /**
     * 代金券使用数量
     */
    public Integer getFdCouponCount() {
        return this.fdCouponCount;
    }

    /**
     * 代金券使用数量
     */
    public void setFdCouponCount(Integer fdCouponCount) {
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
    public Integer getFdCouponFeeOne() {
        return this.fdCouponFeeOne;
    }

    /**
     * 单个代金券支付金额
     */
    public void setFdCouponFeeOne(Integer fdCouponFeeOne) {
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
     * 交易状态描述
     */
    public String getFdTradeStateDesc() {
        return (String) readLazyField("fdTradeStateDesc", this.fdTradeStateDesc);
    }

    /**
     * 交易状态描述
     */
    public void setFdTradeStateDesc(String fdTradeStateDesc) {
        this.fdTradeStateDesc = (String) writeLazyField("fdTradeStateDesc", this.fdTradeStateDesc, fdTradeStateDesc);
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
     * 主文档类名
     */
    public String getFdModelName() {
        return this.fdModelName;
    }

    /**
     * 主文档类名
     */
    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
    }

    /**
     * 主文档ID
     */
    public String getFdModelId() {
        return this.fdModelId;
    }

    /**
     * 主文档ID
     */
    public void setFdModelId(String fdModelId) {
        this.fdModelId = fdModelId;
    }

    /**
     * 主文档关键字
     */
    public String getFdKey() {
        return this.fdKey;
    }

    /**
     * 主文档关键字
     */
    public void setFdKey(String fdKey) {
        this.fdKey = fdKey;
    }

    /**
     * 创建人
     */
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 创建人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
