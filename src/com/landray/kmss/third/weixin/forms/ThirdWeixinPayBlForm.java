package com.landray.kmss.third.weixin.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.weixin.model.ThirdWeixinPayBl;

/**
  * 支付业务调用日志
  */
public class ThirdWeixinPayBlForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String fdBody;

    private String fdDetail;

    private String fdAttach;

    private String fdFeeType;

    private String fdTotalFee;

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

    private String docCreatorId;

    private String docCreatorName;

    private String fdPayPersonId;

    private String fdPayPersonName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        docCreateTime = null;
        fdBody = null;
        fdDetail = null;
        fdAttach = null;
        fdFeeType = null;
        fdTotalFee = null;
        fdTimeStart = null;
        fdTimeExpire = null;
        fdGoodsTag = null;
        fdTradeType = null;
        fdProductId = null;
        fdLimitPay = null;
        fdTransactionId = null;
        fdModelName = null;
        fdModelId = null;
        fdKey = null;
        fdOtherData = null;
        fdReturnData = null;
        fdAppid = null;
        docCreatorId = null;
        docCreatorName = null;
        fdPayPersonId = null;
        fdPayPersonName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinPayBl> getModelClass() {
        return ThirdWeixinPayBl.class;
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
        return this.fdDetail;
    }

    /**
     * 商品详情
     */
    public void setFdDetail(String fdDetail) {
        this.fdDetail = fdDetail;
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
    public String getFdTotalFee() {
        return this.fdTotalFee;
    }

    /**
     * 标价金额
     */
    public void setFdTotalFee(String fdTotalFee) {
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
        return this.fdOtherData;
    }

    /**
     * 其它信息
     */
    public void setFdOtherData(String fdOtherData) {
        this.fdOtherData = fdOtherData;
    }

    /**
     * 响应信息
     */
    public String getFdReturnData() {
        return this.fdReturnData;
    }

    /**
     * 响应信息
     */
    public void setFdReturnData(String fdReturnData) {
        this.fdReturnData = fdReturnData;
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
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 创建人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 创建人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 创建人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    /**
     * 支付人
     */
    public String getFdPayPersonId() {
        return this.fdPayPersonId;
    }

    /**
     * 支付人
     */
    public void setFdPayPersonId(String fdPayPersonId) {
        this.fdPayPersonId = fdPayPersonId;
    }

    /**
     * 支付人
     */
    public String getFdPayPersonName() {
        return this.fdPayPersonName;
    }

    /**
     * 支付人
     */
    public void setFdPayPersonName(String fdPayPersonName) {
        this.fdPayPersonName = fdPayPersonName;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
