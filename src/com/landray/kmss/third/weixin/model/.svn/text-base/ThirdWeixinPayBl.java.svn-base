package com.landray.kmss.third.weixin.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.weixin.forms.ThirdWeixinPayBlForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.model.IAttachment;

/**
  * 支付业务调用日志
  */
public class ThirdWeixinPayBl extends BaseModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date docCreateTime;

    private String fdBody;

    private String fdDetail;

    private String fdAttach;

    private String fdFeeType;

    private Integer fdTotalFee;

    private String fdTimeStart;

    private String fdTimeExpire;

    private String fdGoodsTag;

    private String fdTradeType;

    private String fdProductId;

    private String fdLimitPay;

    private String fdTransactionId;

    private String fdModelName;

    private String fdModelId;

    private String fdKey;

    private String fdOtherData;

    private String fdReturnData;

    private String fdAppid;

    private SysOrgPerson docCreator;

    private SysOrgElement fdPayPerson;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdWeixinPayBlForm> getFormClass() {
        return ThirdWeixinPayBlForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdPayPerson.fdName", "fdPayPersonName");
            toFormPropertyMap.put("fdPayPerson.fdId", "fdPayPersonId");
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
     * 其它信息
     */
    public String getFdOtherData() {
        return (String) readLazyField("fdOtherData", this.fdOtherData);
    }

    /**
     * 其它信息
     */
    public void setFdOtherData(String fdOtherData) {
        this.fdOtherData = (String) writeLazyField("fdOtherData", this.fdOtherData, fdOtherData);
    }

    /**
     * 响应信息
     */
    public String getFdReturnData() {
        return (String) readLazyField("fdReturnData", this.fdReturnData);
    }

    /**
     * 响应信息
     */
    public void setFdReturnData(String fdReturnData) {
        this.fdReturnData = (String) writeLazyField("fdReturnData", this.fdReturnData, fdReturnData);
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

    /**
     * 支付人
     */
    public SysOrgElement getFdPayPerson() {
        return this.fdPayPerson;
    }

    /**
     * 支付人
     */
    public void setFdPayPerson(SysOrgElement fdPayPerson) {
        this.fdPayPerson = fdPayPerson;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

}
